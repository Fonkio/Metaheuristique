package fr.algav.tsp;

import fr.algav.Utils;

import java.io.FileNotFoundException;
import java.util.List;

public class TSPApp {

    public static void main(String[] args) throws FileNotFoundException {
        /*TSPFile tsp5 = new TSPFile("tsp5.txt");
        tsp5.steepestHillClimbing(1);
        tsp5.steepestHillClimbing(40, 2);*/

        TSPFile tsp101 = new TSPFile("tsp2.txt");
        List<Ville> v = tsp101.meilleurVoisin2Opt(tsp101.villesInit);
        System.out.println(v+ "dist " + tsp101.distance(v));
        //tsp101.steepestHillClimbing(10, 100);
        //tsp101.steepestHillClimbing(2000, 5000);


    }
}
