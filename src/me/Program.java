package me;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;





import me.Membre;
import com.mysql.jdbc.Driver;



public class Program {
     static Connection cnx;
     static Statement stm;
     static ResultSet rst;
     static PreparedStatement st;

     public static void main(String[] args) {
    	 try{
    		 cnx=connectionDB();
    		 Scanner input = new Scanner(System.in);
             int choix1;
             int choix2;
                 System.out.println("Bienvenue dans le Réseau Social , Voulez-vous :");
                 System.out.print("1: S'inscrire\n");
                 System.out.print("2: Se connecter\n");
                 choix1 = input.nextInt();
                 switch (choix1) {
                     case 1:
                    	 sinscrire();
                    	 break;
                     case 2:
                         Scanner sc2 = new Scanner(System.in);
                         System.out.print("email : ");
                         String email = sc2.nextLine();
                         System.out.print("mot de passe : ");
                         String mdp = sc2.nextLine();
                         Membre user = seConnecter(email, mdp);
                         if (user != null) {
                           
                                 Scanner sc3 = new Scanner(System.in);
                                 System.out.print("1: Envoyer invitation \n");
                                 System.out.print("2: Consulter invitation\n");
                                 System.out.print("3: Créer page\n");
                                 System.out.print("4: Aimer page \n");
                                 System.out.print("5: Créer groupe  \n");
                                 choix2 = sc3.nextInt();
                                 switch (choix2) {

                                     case 1:
                                         Scanner sc4 = new Scanner(System.in);
                                         System.out.println("Vous souhaitez envoyer une invitation à : ");
                                         String prenom = sc4.nextLine();
                                         user.envoyerInvitation(prenom);
                                         break;

                                     case 2:
                                         user.repondreInvitation();
                                         break;
                                     case 3:
                                         user.creerPage(user);
                                         break;
                                          case 4 :
                                         System.out.println("nom du page ");
                                         Scanner sc7 = new Scanner(System.in);
                                         String n = sc7.nextLine();
                                         user.aimerPage(n);
                                         break;
                                     case 5:
                                        user.creerGroupe();

                                         break;
                                     
                                 }}
                          break;   
                          default:
                                         System.out.println("une erreur s'est produite");
                         }
             }
    		 
             catch(Exception ex){
    		 ex.printStackTrace();}
    	 
         }
 
     
	public static  Connection connectionDB() {	
	try{		
	Driver dr=new Driver();
	Class.forName("com.mysql.jdbc.Driver");
	String url="jdbc:mysql://localhost:3306/reseaus";
    Connection cnx =DriverManager.getConnection(url,"root","eyaelkamel");
    return cnx;
	   }
    catch(Exception exp){
		System.out.println(exp);
		return null;
	  }
	}
	public static Membre seConnecter(String email_in, String password_in) throws SQLException {
		cnx=connectionDB();
		String sql1="select * from membre where email=?;";
		st=cnx.prepareStatement(sql1);
		st.setString(1, email_in);
		rst=st.executeQuery();
		if(!rst.next()){
			System.out.println("Incorrect email.");}
		else {
		String sql2="select * from membre where motdepasse=?;";
		st=cnx.prepareStatement(sql2);
		st.setString(1, password_in);
		rst=st.executeQuery();
		 if(!rst.next())System.out.println("Incorrect password.");
		 else {
			 String prenom=rst.getString(2);
			  Membre m1=new Membre(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
            System.out.println("Vous etes membre du réseau !");
            System.out.println("Bienvenue, " + prenom + "!");
            return m1;
		 }
        }
		return null;	
   
    }
	 public static void sinscrire(){
		 try{
    		 cnx=connectionDB();
    		Scanner sc1 = new Scanner(System.in);
    	        System.out.print("nom : ");
    	        String nm = sc1.nextLine();
    	        System.out.print("prenom : ");
    	        String pr = sc1.nextLine();
    	        System.out.print("date de naissance : ");
    	        String dd = sc1.nextLine();
    	        System.out.print("email : ");
    	        String em = sc1.nextLine();
    	        System.out.print("mot de passe : ");
    	        String mdp = sc1.nextLine();
    	        System.out.print("ville : ");
    	        String vi = sc1.nextLine(); 
    	        	String query1="select * from membre where email=?;";
    	            String query2="INSERT INTO membre(nom, prenom, datedenaissance, email, motdepasse, ville)"+ " VALUES (?, ?, ?, ?, ?, ?)";
    			st=cnx.prepareStatement(query1);
    			st.setString(1, em);
    			rst=st.executeQuery();
    			if(!rst.next()){
    				st=cnx.prepareStatement(query2);
    				st.setString(1, nm);
    				st.setString(2, pr);
    				st.setString(3, dd);
    				st.setString(4, em);
    				st.setString(5, mdp);
    				st.setString(6, vi);
    				int rows= st.executeUpdate();
    				if(rows>0){
    	            System.out.println("félicitation! vous venez de vous inscrire");
    				}
    			}else{
    				System.out.println("email déja utilisé");
    			}
    		 
    	 }catch(Exception ex){
    		 ex.printStackTrace();
    		 }
	    }



}
