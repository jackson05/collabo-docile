package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connectiondb {
	
	  static Connection con;
	
	//connexion imfasha kwi connecta muri base de donne ubwoko bwa connexion ni JDBC nkoresheje mysql
	
	public Connection createConnection()
	{
        
        try {
           
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dociledb","root", "");
            System.out.println("connection is ok");
           return con;
           
            
        } catch (ClassNotFoundException | SQLException ex) {
           
            Logger.getLogger(Connectiondb.class.getName()).log(Level.SEVERE, null, ex);
            return con;
        }  
        
    }
	
	// La methode qui sert le controle de la transaction des donn�es en cas d'erreur
	
	public void rollBack(Connection connexion) {
		 try {
			connexion.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//La methode qui sert a fermer la connexion apres l'operation
	
	public void closeConnection(Connection connexion)
	{
		try {
			if(!connexion.isClosed())
			{
				connexion.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeResult(ResultSet rs) {
		try {
			if(!rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closePrepareStatement(PreparedStatement ps) {
		try {
			if(!ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public static void main(String [] args ) {
		Connectiondb cdb=new Connectiondb();
		System.out.println(cdb.createConnection());
		cdb.closeConnection(con);
		
	}
	*/

	
	
	
}
