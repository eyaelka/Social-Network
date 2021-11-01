package me;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Membre {
	
	private int id;
	private String nom;
    private String prenom;
    private String datedenaissance;
    private String email;
    private String motdepasse;
    private  String ville;
	private ArrayList<Membre> listeAmis = new ArrayList<Membre>();
	private ArrayList<Invitation> listeInvitation = new ArrayList<Invitation>();
	private ArrayList<Groupe> listeGroupe = new ArrayList<Groupe>();
    private ArrayList<Page> listePage = new ArrayList<Page>();
	private List<Membre> shortestPath = new LinkedList<>();
	static Connection cnx;
    static Statement stm;
    static ResultSet rst;
    static PreparedStatement st;
	
	
	public ArrayList<Membre> getListeAmis() {
		return listeAmis;
	}
	public void setListeAmis(ArrayList<Membre> listeAmis) {
		this.listeAmis = listeAmis;
	}
	public ArrayList<Invitation> getListeInvitation() {
		return listeInvitation;
	}
	public void setListeInvitation(ArrayList<Invitation> listeInvitation) {
		this.listeInvitation = listeInvitation;
	}
	public ArrayList<Groupe> getListeGroupe() {
		return listeGroupe;
	}
	public void setListeGroupe(ArrayList<Groupe> listeGroupe) {
		this.listeGroupe = listeGroupe;
	}
	public ArrayList<Page> getListePage() {
		return listePage;
	}
	public void setListePage(ArrayList<Page> listePage) {
		this.listePage = listePage;
	}
	public Membre(String nom, String prenom, String datedenaissance, String email, String motdepasse, String ville) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.datedenaissance = datedenaissance;
		this.email = email;
		this.motdepasse = motdepasse;
		this.ville = ville;
	}
	public Membre() {
		super();

	}

	public void envoyerInvitation (String nomInv) {
		try{
		Program p=new Program();
		cnx=p.connectionDB();
		String sql1="select * from membre where prenom=? ;";
		st=cnx.prepareStatement(sql1);
		st.setString(1,nomInv);
		rst=st.executeQuery();
		if(!rst.next()){
			System.out.println("profil pas trouvé.");}
		else{
			 String query2="INSERT INTO invitation(sender,receiver)"+ " VALUES (?, ?)";
			 st=cnx.prepareStatement(query2);
				st.setString(1,this.nom);
				st.setString(2,nomInv);
				int rows= st.executeUpdate();
				if(rows>0){
	            System.out.println("invitation envoyée!");
	
		}}}catch (Exception e){e.printStackTrace();}
    }

    public void repondreInvitation() {
        Scanner sc = new Scanner(System.in);
        try{
    		Program p=new Program();
    		cnx=p.connectionDB();
    		String sql1="select * from invitation where receiver=?;";
    		st=cnx.prepareStatement(sql1);
    		st.setString(1,this.nom);
    		rst=st.executeQuery();
    		if(!rst.next()){
    			System.out.println("liste d'invitation vide!.");}
    		else{
    			while(rst.next()){
    				
    				System.out.println(rst.getString(1)+" vous a envoyé une invitation");
    				Invitation i = this.listeInvitation.get(0);
    				 System.out.println("tapez a si vous voulez accepter");
    		            System.out.println("\n");
    		            System.out.println("tapez r si vous voulez refuser");
    		            char answer = sc.nextLine().charAt(0);
    		            if (answer == 'a') {
    		                if(!this.listeAmis.contains(i.sender)){
    		                    this.getListeInvitation().remove(i);
    		                    this.getListeAmis().add(i.getSender());
    		                    i.getSender().getListeAmis().add(i.getReceiver());
    		                    System.out.println(i.sender.getNom() + " " + i.sender.getPrenom() + " est d�sormais votre amis");
    		                }else
    		                {
    		                    this.getListeInvitation().remove(i);
    		                    System.out.println("vous etes deja amis");
    		                }
    		            } else if (answer == 'r') {
    		                this.getListeInvitation().remove(i);
    		                System.out.println(i.sender + " invitation refus�e.\n");
    		            } else {
    		                System.out.println("caractere invalide.\n");
    		            }
    				
    			}
    		}}catch(Exception ex){
	    		 ex.printStackTrace();}}
    
    public void creerPage(Membre user)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom page : ");
        String nomPage = sc.nextLine();
        System.out.println("genre : ");
        String genre = sc.nextLine();

        // check if the page is already in your pages list
        Page page = new Page(genre,nomPage);
        if (listePage.contains(page))
        {
            System.out.println("Page existante");
        }
        else {
            listePage.add(page);
            try{
        		Program p=new Program();
       		 cnx=p.connectionDB();
	         String query="INSERT INTO page(nompage, genre,likes)"+ " VALUES (?, ?, ?)";
 				st=cnx.prepareStatement(query);
 				st.setString(1,nomPage);
 				st.setString(2, genre);
 				st.setInt(3, 0);
 				int rows= st.executeUpdate();
				if(rows>0){
	            System.out.println("Page created Successfully");
				}
        }catch(Exception ex){
   		 ex.printStackTrace();
   		 }}
    }
    public void creerGroupe()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom groupe: ");
        String nomg = sc.nextLine();
        System.out.println("genre: ");
        String genre = sc.nextLine();
        Groupe groupe = new Groupe(genre,nomg);
        if (listeGroupe.contains(groupe))
        {
            System.out.println("Groupe existant");
        }
        else
        {
            listeGroupe.add(groupe);
            try{
        		Program p=new Program();
       		 cnx=p.connectionDB();
	         String query="INSERT INTO groupe(nomg, genre,admin)"+ " VALUES (?, ?, ?)";
 				st=cnx.prepareStatement(query);
 				st.setString(1,nomg);
 				st.setString(2, genre);
 				st.setString(3, this.prenom);
 				int rows= st.executeUpdate();
				if(rows>0){
	            System.out.println("group created Successfully");
				}
        }catch(Exception ex){
   		 ex.printStackTrace();
   		 }}
            
        }
	public void aimerPage(String n) {
		try{
			Program pg=new Program();
			cnx=pg.connectionDB();
			String sql1=" update page set likes=likes+1 where  nompage=?;";
			st=cnx.prepareStatement(sql1);
			st.setString(1,n);
			int i =st.executeUpdate();
			if(i>0){
				System.out.println("page lik�e!.");}
			else{
		         System.out.println("page non trouv�e");
		          }
			
		}catch(Exception ex){
	    		 ex.printStackTrace();}
	}








	public List<Membre> rechercherAmis(Program prg) {
		try {
			Program p = new Program();
			cnx = p.connectionDB();
			System.out.println("entrer nom d'utilisateur cherché");
			Scanner sc = new Scanner(System.in);
			String nomAmiRecherche = sc.nextLine();
			String sql1 = "select * from membre where prenom='nomAmisRecherche' ;";
			st = cnx.prepareStatement(sql1);
			if (!rst.next()) {
				System.out.println("profil pas trouvé.");
			} else {
				List<Membre> amiReseau = new ArrayList<>();
				boolean visited[] = new boolean[amiReseau.size()];
				Arrays.fill(visited, false);

				if (amiReseau.contains(nomAmiRecherche)) {
					System.out.println("profil trouvé");
					backtracking(amiReseau, nomAmiRecherche, visited, amiReseau, false, amiReseau.size(), 0);
					return amiReseau;

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static void backtracking(Collection<Membre> users, String name, boolean[] visited, List<Membre> amiReseau, boolean q, int nbrTot, int index) {

		for (Membre p : users) {
			if (q) break;
			if (!visited[p.getId()])
			{
				visited[p.getId()] = true;
				if (nbrTot != index)
				{
					if (p.getNom().equals(name))
					{
						amiReseau.add(p);
					} else {
						backtracking(p.getListeAmis(), name, visited, amiReseau, q, nbrTot, index++);
						if (!q)
						{
							visited[p.getId()] = false;
						}
					}

				} else {
					q = true;
				}
			}

		}
	}

	public static ArrayList<Membre> ShortestPath(ArrayList<Membre> ALLusers, Membre me  )
	{
		int[] distance = new  int[ALLusers.size()];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[me.getId()] = 0;
		Set<Membre> settledNodes = new HashSet<>();
		Set<Membre> unsettledNodes = new HashSet<>();

		unsettledNodes.add(me);
		while (unsettledNodes.size() != 0)
		{
			Membre currentNode = getLowestDistanceNode(unsettledNodes, distance);
			unsettledNodes.remove(currentNode);
			for (Membre adjacencyPair: currentNode.getListeAmis())
			{
				Membre adjacentNode = adjacencyPair;
				Integer edgeWeight = 1;
				if (!settledNodes.contains(adjacentNode))
				{
					CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode, distance);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return ALLusers;
	}
	private static Membre getLowestDistanceNode(Set < Membre > unsettledNodes,int[] distance) {
		Membre lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (Membre p : unsettledNodes)
		{
			int nodeDistance = distance[p.getId()];
			if (nodeDistance < lowestDistance)
			{
				lowestDistance = nodeDistance;
				lowestDistanceNode = p;
			}
		}
		return lowestDistanceNode;
	}

	private static void CalculateMinimumDistance(Membre evaluationNode, Integer edgeWeigh, Membre sourceNode, int[] distance) {
		Integer sourceDistance = distance[sourceNode.getId()];
		if (sourceDistance + edgeWeigh < distance[evaluationNode.getId()])
		{
			distance[evaluationNode.getId()] = sourceDistance + edgeWeigh;
			if (sourceNode.getShortestPath() == null)
			{
				sourceNode.setShortestPath(new LinkedList<Membre>());
			}
			LinkedList<Membre> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}





	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDatedenaissance() {
		return datedenaissance;
	}
	public void setDatedenaissance(String datedenaissance) {
		this.datedenaissance = datedenaissance;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public List<Membre> getShortestPath() { return shortestPath; }
	public void setShortestPath(List<Membre> shortestPath) {this.shortestPath = shortestPath; }






}
