package fr.algav.matrice;

import fr.algav.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MatriceFile {

    private final int n;
    private final int p;
    private final int[][] matrice;

    public MatriceFile(String path) throws FileNotFoundException { //Builder creation matrice et des 2 premières var n et p
        File file = new File(path);
        Scanner lecteur = new Scanner(file);
        this.n = lecteur.nextInt();
        this.p = lecteur.nextInt();
        this.matrice = new int[n][n];

        for(int i = 0; i<n; i++) {
            for(int j = 0; j<n; j++) {
                matrice[i][j] = lecteur.nextInt();
            }
        }
    }

    public List<Integer> tabou(int max_depl, int k) { //Tabou QUESTION 7
        List<Integer> s = Utils.randomN(getN());
        Queue<List<Integer>> tabou = new LinkedList<>();
        int nb_depl = 0;
        List<Integer> mSol = s;
        boolean stop = false;
        List<Integer> sPrime = null;
        do {
            Map<List<Integer>, Integer> voisinNonTabou = voisinsNonTabou(s, tabou);
            if (!voisinNonTabou.isEmpty()) {
                sPrime = meilleureSolution(voisinNonTabou);
            } else {
                stop = true;
            }
            if (tabou.size() < k) {
                tabou.add(s);
            }
            if (f(sPrime) < f(mSol)) {
                mSol = sPrime;
            }
            nb_depl ++;
        } while (nb_depl != max_depl && !stop);
        System.out.println("Solution trouvée avec tabou :");
        System.out.println(mSol + " avec f = " + f(mSol));
        return mSol;
    }

    private Map<List<Integer>, Integer> voisinsNonTabou(List<Integer> s, Queue<List<Integer>> tabous) {
        Map<List<Integer>, Integer> listeVoisins = getVoisin(s, false);
        for(List<Integer> tabou : tabous) {
            listeVoisins.remove(tabou);
        }
        return listeVoisins;
    }

    public int steepestHillClimbing(int max_depl, int max_essais, boolean contrainte) throws FileNotFoundException { //Calcul Steepest Hill-Climbing nb_essais fois QUESTION 5
        List<Integer> bestRes = steepestHillClimbing(max_depl, contrainte);
        int bestF = f(bestRes);
        System.out.println("Solution initiale :");
        System.out.println(bestRes + " avec f = "+ bestF);

        for (int i = 0; i < max_essais-1; i++) {
            List<Integer> res = steepestHillClimbing(max_depl, contrainte);
            int f = f(res);
            if (f < bestF) {
                bestRes = res;
                bestF = f;
                System.out.println("Meilleure solution trouvée :");
                System.out.println(bestRes + " avec f = "+ bestF);
            }
        }
        return bestF;
    }

    public List<Integer> steepestHillClimbing(int max_depl, boolean contrainte) throws FileNotFoundException { //Calcul Steepest Hill-Climbing QUESTION 4
        List<Integer> s = Utils.randomN(getN());
        int nb_depl = 0;
        boolean stop = false;
        do {
            List<Integer> sPrime = meilleur_voisin(s, contrainte);
            if (sPrime != null && f(sPrime)<f(s)) {
                s = sPrime;
            } else {
                stop = true;
            }
            nb_depl++;
        } while (nb_depl != max_depl && !stop);
        return s;
    }


    public List<Integer> meilleur_voisin(List<Integer> x, boolean contrainte) { //meilleur voisin QUESTION 3
        Map<List<Integer>, Integer> listeVoisins = getVoisin(x, contrainte);
        return meilleureSolution(listeVoisins);
    }

    //Retourne le meilleur de la liste en param
    private List<Integer> meilleureSolution(Map<List<Integer>, Integer> listeSolution) {
        if (listeSolution.isEmpty()) {
            return null;
        }
        TreeSet<Integer> ts = new TreeSet<>(listeSolution.values());

        int min = ts.first();
        List<List<Integer>> listMeilleursVoisins = new ArrayList<>();
        for(List<Integer> key : listeSolution.keySet()) {
            if (listeSolution.get(key) == min) {
                listMeilleursVoisins.add(key);
            }
        }
        Random rd = new Random();
        return listMeilleursVoisins.get(rd.nextInt(listMeilleursVoisins.size()));
    }

    //Tous les voisins possibles avec leurs f()
    private Map<List<Integer>, Integer> getVoisin(List<Integer> x, boolean contrainte) {
        Map<List<Integer>, Integer> listeVoisins = new HashMap<>();
        for(int i = 0; i < getN(); i++) {

            List<Integer> xPrime = new ArrayList<>(x);
            xPrime.set(i, (xPrime.get(i) + 1) % 2);
            if(!contrainte || sommeBits(xPrime) >= p) { // Ajout de la contrainte somme des bits QUESTION 8
                listeVoisins.put(xPrime, f(xPrime));
            }
        }
        return listeVoisins;
    }

    private int sommeBits(List<Integer> xPrime) {
        int i = 0;
        for (Integer bit : xPrime) {
            i += bit;
        }
        return i;
    }

    public int f(List<Integer> x) { //Calcul f QUESTION 2
        int f = 0;
        for(int i = 0; i < getN(); i++) {
            for(int j = 0; j < getN(); j++) {
                f += getMatrice()[i][j]*x.get(i)*x.get(j);
            }
        }
        return f;

    }

    public int[][] getMatrice() {
        return matrice;
    }

    public int getP() {
        return p;
    }

    public int getN() {
        return n;
    }
}
