package fr.algav.matrice;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
public class MatriceApp
{
    public static void main( String[] args )  {

        steepestHillClimbingLoop("./partition6.txt", 1000, 100, false);
        //steepestHillClimbingLoop("./graphe12345.txt", 1000, 100, false);

        tabou("./partition6.txt", 1000, 1000);
        //tabou("./graphe12345.txt", 1000, 1000);

        steepestHillClimbingLoop("./partition6.txt", 10000, 10000, true);
        //steepestHillClimbingLoop("./graphe12345.txt", 1000, 100, true);

    }

    private static void tabou(String path, int max_depl, int k) { //Question 7
        System.out.println("============================================================");
        System.out.println("Execution de tabou taille "+ k + " sur "+ path);
        System.out.println("============================================================");
        try {
            MatriceFile matriceFile = new MatriceFile(path);
            matriceFile.tabou(max_depl, k);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier Introuvable");
        }

    }

    private static void steepestHillClimbingLoop(String path, int max_depl, int max_essais, boolean contrainte) { //Question 6
        System.out.println("============================================================");
        System.out.println("Execution de steepestHillClimbing "+max_essais + " fois contrainte = " + contrainte + " sur " + path);
        System.out.println("============================================================");
        try {
            MatriceFile matriceFile = new MatriceFile(path);
            matriceFile.steepestHillClimbing(max_depl, max_essais, contrainte);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier Introuvable");
        }

    }


}
