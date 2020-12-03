package fr.algav.tsp;

import fr.algav.Utils;

import java.io.FileNotFoundException;

public class TSPApp {

    public static void main(String[] args) throws FileNotFoundException {
        /*TSPFile tsp5 = new TSPFile("tsp5.txt");
        tsp5.steepestHillClimbing(1);
        tsp5.steepestHillClimbing(40, 2);*/

        TSPFile tsp101 = new TSPFile("tsp101.txt");
        //tsp101.steepestHillClimbing(100);
        tsp101.steepestHillClimbing(2000, 5000);
        //tsp101.tabou(2000, 2000);
    }
}
