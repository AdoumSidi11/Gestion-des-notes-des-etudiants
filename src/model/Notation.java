package model;

public class Notation {


    private double note;

    private int coef;


    public Notation(double note, int coef) {

        this.note = note;

        this.coef = coef;
    }


    public int getCoef() {

        return this.coef;
    }


    public double getNote() {

        return this.note;
    }


    public void afficher() {
        System.out.println("\t- Note: " + this.note + "/20, Coefficient: " + this.coef);
    }
}