package fournisseur;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import config.Connectiondb;
import config.SessionConfig;
import message.Message;

@Named
@ViewScoped
public class Fournisseur implements Serializable {

	private static final long serialVersionUID = 7039950522573168132L;

	public Fournisseur() {
	
	}
	
	public Fournisseur(int idFourn, String nomFourn,String villeFourn,int telFourn , String mailFourn) {
		this.idFourn=idFourn;
		this.nomFourn=nomFourn;
		this.villeFourn=villeFourn;
		this.mailFourn=mailFourn;
		this.telFourn=telFourn;
		
	}
	
	private int idFourn;
	private String nomFourn;
	private String villeFourn;
	private String mailFourn;
	private int telFourn;
	
	
	public int getIdFourn() {
		return idFourn;
	}
	public void setIdFourn(int idFourn) {
		this.idFourn = idFourn;
	}
	public String getNomFourn() {
		return nomFourn;
	}
	public void setNomFourn(String nomFourn) {
		this.nomFourn = nomFourn;
	}
	public String getVilleFourn() {
		return villeFourn;
	}
	public void setVilleFourn(String villeFourn) {
		this.villeFourn = villeFourn;
	}
	public String getMailFourn() {
		return mailFourn;
	}
	public void setMailFourn(String mailFourn) {
		this.mailFourn = mailFourn;
	}
	public int getTelFourn() {
		return telFourn;
	}
	public void setTelFourn(int telFourn) {
		this.telFourn = telFourn;
	}

	public boolean isValidateFournisseur () {
		if(this.getNomFourn()!=null && this.getVilleFourn()!=null && String.valueOf(this.getTelFourn()).length()>7
				&& this.getMailFourn()!=null) {
			return true;
		}
		return false;
	}
	
	
	/*=====================================================*/
	PreparedStatement ps;
	Connection con;
	Connectiondb cdb;
	ResultSet rs;
		 
	/*=====================================================*/
	
	/*======================================================
	 *			method to save fournisseur
	 * ====================================================*/
	public void saveFournisseur() throws IOException {
		
		 if(isValidateFournisseur()) {
			 //verification des session
			if(SessionConfig.getSession().getAttribute("idUser")!=null) {
				 cdb=new Connectiondb();
				 con=cdb.createConnection();
				 try {
					ps=con.prepareStatement("insert into t_fournisseur (nomFournisseur,ville,tel,mail,idUser,) "
							+ "value (?,?,?,?,?)");
					ps.setString(1, this.getNomFourn());
					ps.setString(2, this.getVilleFourn());
					ps.setInt(3, this.getTelFourn());
					ps.setString(4, this.getMailFourn());
					ps.setInt(5, Integer.parseInt(SessionConfig.getSession().getAttribute("idUser").toString()));
					ps.executeUpdate();
					new Message().information("Le fournisseur a ete enregistre");
				} catch (SQLException e) {
					new Message().information(e.getMessage());
					e.printStackTrace();
				}finally {
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
					
				}
			}else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
			}
		 }else {
			 new Message().warnig("Les informatins du fournisseur est invalide");
		 }
	}
	
	/*======================================================
	 *			method to edit fournisseur
	 * ====================================================*/
	public void editFournisseur(int idFournisseur) throws IOException {
		
		 if(isValidateFournisseur()) {
			 //check sessions first
			if(SessionConfig.getSession().getAttribute("idUser")!=null) {
				 cdb=new Connectiondb();
				 con=cdb.createConnection();
				 try {
					ps=con.prepareStatement("update t_fournisseur set nomFournisseur=?,ville=?,tel=?,mail=?,idUser=? where idFournisseur=?");
					ps.setString(1, this.getNomFourn());
					ps.setString(2, this.getVilleFourn());
					ps.setInt(3, this.telFourn);
					ps.setString(4, this.getMailFourn());
					ps.setInt(6, Integer.parseInt(SessionConfig.getSession().getAttribute("idUser").toString()));
					ps.executeUpdate();
					new Message().information("Fournisseur modifie avec succes");
				} catch (SQLException e) {
					new Message().information(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
					
				}
			}else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
			}
		 }else {
			 new Message().warnig("Les informatins du fournisseur est invalide");
		 }
	}

	
	
	/*======================================================
	 *			method to delete fournisseur
	 * ====================================================*/
	public void deleteFournisseur(int idFournisseur) throws IOException {
		
		 if(idFournisseur>0) {
			 //check sessions first
			if(SessionConfig.getSession().getAttribute("idUser")!=null) {
				 cdb=new Connectiondb();
				 con=cdb.createConnection();
				 try {
					ps=con.prepareStatement("delete from t_fournisseur where idFournisseur=?");
					ps.setInt(1, idFournisseur);
					ps.executeUpdate();
					new Message().information("Fournisseur supprime avec succes");
				} catch (SQLException e) {
					new Message().information(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
					cdb.closePrepareStatement(ps);
					cdb.closeConnection(con);
					
				}
			}else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
			}
		 }else {
			 new Message().warnig("Les informations du fournisseur est invalide");
		 }
	}
/*==================================================================================================*/


	
	

}
