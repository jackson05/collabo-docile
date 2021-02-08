package clients;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import config.Connectiondb;
import message.Message;

@Named
@ViewScoped
public class Client implements Serializable {

	
	private static final long serialVersionUID = -7597896235990759622L;

	
	private int telCli,codeCli;
	private String nomCli,prenomCli;
	
	public Client() {
		
	}
	
	public Client(int codeCli,String nomCli,String prenomCli,int telCli) {
		this.codeCli=codeCli;
		this.nomCli=nomCli;
		this.prenomCli=prenomCli;
		this.telCli=telCli;
	}
	
	public Client(String nomCli,String prenomCli,int telCli) {
		this.nomCli=nomCli;
		this.prenomCli=prenomCli;
		this.telCli=telCli;
	}
	
	
	public int getTelCli() {
		return telCli;
	}
	public void setTelCli(int telCli) {
		this.telCli = telCli;
	}
	public int getCodeCli() {
		return codeCli;
	}
	public void setCodeCli(int codeCli) {
		this.codeCli = codeCli;
	}
	public String getNomCli() {
		return nomCli;
	}
	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}
	public String getPrenomCli() {
		return prenomCli;
	}
	public void setPrenomCli(String prenomCli) {
		this.prenomCli = prenomCli;
	}

	
	/*=============variable object =======*/
		ResultSet rs;
		Connection con;
		PreparedStatement ps;
		Connectiondb cdb;
	/*==================*/
		
		public boolean isValidateClient() {
			if(this.getNomCli()!=null && this.getPrenomCli()!=null && String.valueOf(this.getTelCli()).length()==8) {
				return true;
			}
			return false;
		}
		
		/*======================================================
		 *			method to create client
		 * ====================================================*/
		
		
		public void saveClient() 
		{
			if(isValidateClient()) {
				cdb=new Connectiondb();
				con=cdb.createConnection();
				try {
					ps=con.prepareStatement("insert into t_clients(nomCli,prenomCli,tel) values(?,?,?)");
					ps.setString(1, this.getNomCli());
					ps.setString(2, this.getPrenomCli());
					ps.setInt(3, this.getTelCli());
					if(ps.executeUpdate()>0) {
						new Message().information("Enregister avec succes");
					}else {
						new Message().error("echec d'enregistement");
					}
				} catch (SQLException e) {
					new Message().errorFatal("echec d'ajout du client");
					
					e.printStackTrace();
				}finally {
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
				}
			}else {
				new Message().warnig("Les information du client est invalide");
			}
		}
	/*===============================================================================*/
		
		
		/*======================================================
		 *			method to edit client
		 * ====================================================*/
		
		public void editClient() 
		{
			if(isValidateClient()) {
				cdb=new Connectiondb();
				con=cdb.createConnection();
				try {
					ps=con.prepareStatement("insert into t_clients(nomCli,prenomCli,tel) values(?,?,?)");
					ps.setString(1, this.getNomCli());
					ps.setString(2, this.getPrenomCli());
					ps.setInt(3, this.getTelCli());
					if(ps.executeUpdate()>0) {
						new Message().information("Enregister avec succes");
					}else {
						new Message().error("echec d'enregistement");
					}
				} catch (SQLException e) {
					new Message().errorFatal("echec d'ajout du client");
					
					e.printStackTrace();
				}finally {
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
				}
			}else {
				new Message().warnig("Les information du client est invalide");
			}
		}
		
		
		
	
	/*===============================================================================*/
		
		
		/*======================================================
		 *			method to delete client
		 * ====================================================*/
		
		
		
		public void deleteClient() 
		{
			if(isValidateClient()) {
				cdb=new Connectiondb();
				con=cdb.createConnection();
				try {
					ps=con.prepareStatement("delete from t_clients where idClient=?");
					ps.setString(1, this.getNomCli());
					
					if(ps.executeUpdate()>0) {
						new Message().information("Supprime avec succes");
					}else {
						new Message().error("echec de suppresion");
					}
				} catch (SQLException e) {
					new Message().errorFatal("echec de suppression du client");
					
					e.printStackTrace();
				}finally {
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
				
				}
				
			}else {
				new Message().warnig("Les information du client est invalide");
			}
		}
		
	/*===============================================================================*/
	

}
