package fr.algav;

import fr.algav.tsp.Ville;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static List<Integer> randomN(int n) { //Solution de départ QUESTION 1

        List <Integer> nBits = new ArrayList<>();

        /*Random rd = new Random();
        for (int i = 0; i < n; i++) {
            nBits.add(rd.nextInt(2));
        }*/

        nBits.add(0);//Test
        nBits.add(0);
        nBits.add(1);
        nBits.add(1);
        nBits.add(0);
        nBits.add(0);

        return nBits;
    }
}
