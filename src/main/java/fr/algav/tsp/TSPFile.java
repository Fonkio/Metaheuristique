package fr.algav.tsp;

import fr.algav.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TSPFile {

    private Set<Ville> villesInit = new HashSet<>();
    private int nbVilles;

    public TSPFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner lecteur = new Scanner(file);
        this.nbVilles = lecteur.nextInt();
        for(int i = 0; i<nbVilles; i++) {
            villesInit.add(new Ville(lecteur.nextInt(), lecteur.nextInt(), lecteur.nextInt()));
        }
    }

    public List<Ville> randomVilles() { //Question 1
        List<Ville> villes = new ArrayList<>(villesInit);
        List<Ville> randomVilles = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < nbVilles; i++) {
            int rdmInt = random.nextInt(villes.size());
            randomVilles.add(villes.get(rdmInt));
            villes.remove(rdmInt);
        }
        return randomVilles;
    }

    private double distance(List<Ville> villes) { //Question 2
        double distance = Math.sqrt((0 - villes.get(0).getX()) * (0 - villes.get(0).getX()) + (0 - villes.get(0).getY()) * (0 - villes.get(0).getY()));
        for (int i = 0; i < nbVilles; i++) {
            double x1 = villes.get(i).getX();
            double x2;
            double y1 = villes.get(i).getY();
            double y2;
            if (i != nbVilles-1) {
                x2 = villes.get(i+1).getX();
                y2 = villes.get(i+1).getY();
            } else {
                x2 = 0;
                y2 = 0;
            }
            distance += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        }
        return distance;
    }

    private List<Ville> meilleurVoisin(List<Ville> villes) { //Question 3
        Map<List<Ville>, Double>listeVoisin = getVoisin(villes);
        return meilleureSolution(listeVoisin);
    }

    private List<Ville> meilleureSolution(Map<List<Ville>, Double> listeVoisin) {
        TreeSet<Double> ts = new TreeSet<>(listeVoisin.values());
        double min = ts.first();
        List<List<Ville>> listMeilleursVoisins = new ArrayList<>();
        for(List<Ville> key : listeVoisin.keySet()) {
            if (listeVoisin.get(key) == min) {
                listMeilleursVoisins.add(key);
            }
        }
        Random rd = new Random();
        return listMeilleursVoisins.get(rd.nextInt(listMeilleursVoisins.size()));
    }

    private List<Ville> intervert(List<Ville> villes, int i1, int i2) {
        List<Ville> returnVille = new ArrayList<>(villes);
        returnVille.set(i1, villes.get(i2));
        returnVille.set(i2, villes.get(i1));
        return returnVille;
    }

    public List<Ville> steepestHillClimbing(int max_depl) throws FileNotFoundException { //Calcul Steepest Hill-Climbing QUESTION 4
        List<Ville> s = randomVilles();
        System.out.println("Solution initiale :");
        System.out.println(s);
        int nb_depl = 0;
        boolean stop = false;
        do {
            List<Ville> sPrime = meilleurVoisin(s);
            if (sPrime != null && distance(sPrime) < distance(s)) {
                s = sPrime;
            } else {
                stop = true;
            }
            nb_depl++;
        } while (nb_depl != max_depl && !stop);
        System.out.println("Nombre de déplacement : "+nb_depl);
        System.out.println("Solution : "+s + "\n=> dist = "+ distance(s));
        return s;
    }

    public double steepestHillClimbing(int max_depl, int max_essais) throws FileNotFoundException { //Calcul Steepest Hill-Climbing nb_essais fois QUESTION 4
        List<Ville> bestRes = steepestHillClimbing(max_depl);
        double bestDist = distance(bestRes);
        System.out.println("Solution initiale :");
        System.out.println(bestRes + " avec dist = "+ bestDist);

        for (int i = 0; i < max_essais; i++) {
            System.out.println("========Essais n°"+i+"/"+max_essais+"("+(((double)i)/((double)max_essais))*100L+"%)===============");
            List<Ville> res = steepestHillClimbing(max_depl);
            double dist = distance(res);
            if (dist < bestDist) {
                bestRes = res;
                bestDist = dist;
                System.out.println("Meilleure solution trouvée :");
                System.out.println(bestRes + " \navec dist = "+ bestDist);
            }
        }
        System.out.println("Meilleure solution trouvée :");
        System.out.println(bestRes + "\n avec dist = "+ bestDist);
        return bestDist;
    }

    public List<Ville> tabou(int max_depl, int k) { //Tabou QUESTION 7
        List<Ville> s = randomVilles();
        System.out.println("Solution initiale :");
        System.out.println(s + "\navec dist = "+ distance(s));
        Queue<List<Ville>> tabou = new LinkedList<>();
        int nb_depl = 0;
        List<Ville> mSol = s;
        boolean stop = false;
        List<Ville> sPrime = null;
        do {
            Map<List<Ville>, Double> voisinNonTabou = voisinsNonTabou(s, tabou);
            if (!voisinNonTabou.isEmpty()) {
                sPrime = meilleureSolution(voisinNonTabou);
                System.out.println(distance(sPrime));
            } else {
                stop = true;
            }
            if (tabou.size() < k) {
                tabou.add(s);
            } else {
                tabou.remove();
                tabou.add(s);
            }
            if (distance(sPrime) < distance(mSol)) {
                mSol = sPrime;
            }
            s = sPrime;
            nb_depl ++;
        } while (nb_depl != max_depl && !stop);
        System.out.println("Solution trouvée avec tabou :");
        System.out.println(mSol + "\navec dist = " + distance(mSol) + " et nb déplacement = "+nb_depl);
        return mSol;
    }
    private Map<List<Ville>, Double> voisinsNonTabou(List<Ville> villes, Queue<List<Ville>> tabous) {
        Map<List<Ville>, Double> listeVoisins = getVoisin(villes);
        System.out.println(listeVoisins.size());
        for(Iterator<List<Ville>> it = listeVoisins.keySet().iterator(); it.hasNext(); ){
            List<Ville> villeATester = it.next();
            for (List<Ville> tabou : tabous) {
                if (compare(villeATester, tabou)) {
                    it.remove();
                }
            }
        }
        System.out.println(listeVoisins.size());
        return listeVoisins;
    }

    private boolean compare(List<Ville> l1, List<Ville> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        for(int i = 0; i < l1.size(); i++) {
            if (l1.get(i).getId() != l2.get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    private Map<List<Ville>, Double> getVoisin(List<Ville> villes) {
        Map<List<Ville>, Double> listeVoisin = new HashMap();
        for (int i = 0; i < nbVilles - 1; i++) {
            for(int j = i+1; j < nbVilles; j++) {
                List<Ville> test = intervert(villes, i, j);
                listeVoisin.put(test, distance(test));
            }
        }
        return listeVoisin;
    }

}
