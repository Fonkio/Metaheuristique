package fr.algav.tsp;

public class Ville {

    private int id;
    private int x;
    private int y;

    public Ville(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
