package me;

import java.util.ArrayList;

public class Groupe extends EntiteSociale{
    protected String nomg;
    protected Membre admin;
    protected ArrayList<Membre> ListeMembreG ;


    public Groupe(String genre, String nomg) {
        super(genre);
        this.nomg = nomg;
    }

    public Groupe(int identitesocial, String genre, String datecreation, String nomg) {
        super(identitesocial, genre, datecreation);
        this.nomg = nomg;
    }

    public String toString()
    {
        return "Bienvenue au groupe " + this.nomg + " créee par " + this.admin;
    }
}
