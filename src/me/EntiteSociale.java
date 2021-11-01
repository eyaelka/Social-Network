package me;

public class EntiteSociale {
	protected int identitesocial;
    protected String genre ;
    protected String datecreation;


    public EntiteSociale(String genre) {
        this.genre = genre;
    }

    public EntiteSociale(int identitesocial, String genre, String datecreation) {
        this.identitesocial = identitesocial;
        this.genre = genre;
        this.datecreation = datecreation;
    }

    public int getIdentitesocial() {
        return identitesocial;
    }

    public void setIdentitesocial(int identitesocial) {
        this.identitesocial = identitesocial;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

}
