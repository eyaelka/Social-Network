package me;



public class Page extends EntiteSociale {
	 protected String nompage;
	    protected  int likes;

	    public Page(String genre, String nompage) {
	        super(genre);
	        this.nompage = nompage;
	    }

	    public Page(int identitesocial, String genre, String datecreation, String nompage, int likes) {
	        super(identitesocial, genre, datecreation);
	        this.nompage = nompage;
	        this.likes = likes;
	    }

	    public String getNompage() {
	        return nompage;
	    }

	    public void setNompage(String nompage) {
	        this.nompage = nompage;
	    }

	    public int getLikes() {
	        return likes;
	    }

	    public void setLikes(int likes) {
	        this.likes = likes;
	    }


	
   
}
