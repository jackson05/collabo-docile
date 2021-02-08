package engrais;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import config.Connectiondb;
import config.SessionConfig;
import fournisseur.Fournisseur;
import message.Message;

@ViewScoped
public class Engrais implements Serializable {

	
	private static final long serialVersionUID = -2105971802222612965L;

	public Engrais() {
		
	}
	
	public Engrais(int idEngrais,String nomEngrais,String typeEngrais,int prixUnitaire,Fournisseur fournisseur) {
		
	}
	
	private int idEngrais,prixUnitaire;
	private int idfournisseur;
	private String nomEngrais,typeEngrais;
	private Fournisseur fournisseur;
	public int getIdEngrais() {
		return idEngrais;
	}
	public void setIdEngrais(int idEngrais) {
		this.idEngrais = idEngrais;
	}
	public int getIdFournisseur() {
		return idfournisseur;
	}
	public void setIdFournisseur(int idfournisseur) {
		this.idfournisseur = idfournisseur;
	}
	public String getNomEngrais() {
		return nomEngrais;
	}
	public void setNomEngrais(String nomEngrais) {
		this.nomEngrais = nomEngrais;
	}
	public String getTypeEngrais() {
		return typeEngrais;
	}
	public void setTypeEngrais(String typeEngrais) {
		this.typeEngrais = typeEngrais;
	}
	public int getPrixUnitaire() {
		return prixUnitaire;
	}
	public void setPrixUnitaire(int prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	
	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	
/*=============variable object =======*/
	ResultSet rs;
	Connection con;
	PreparedStatement ps;
	Connectiondb cdb;
/*====================================*/
	
	
/*==========================method add engrais=====================================================*/
		public void saveEngrais() throws IOException {
			
			
				if(this.getNomEngrais()!=null && this.getTypeEngrais()!=null && this.getIdFournisseur()>0) {
					if(SessionConfig.getSession().getAttribute("idUser")!=null) {
						
						cdb=new Connectiondb();
						con=cdb.createConnection();
						try {
							ps=con.prepareStatement("insert into t_engrais(idFournisseur,nomEngrais,typeEngrais,prixUnitaire,idUser)"
									+ " values(?,?,?,?,?)");
							ps.setInt(1, this.getIdFournisseur());
							ps.setString(2, this.getNomEngrais());
							ps.setString(3, this.getTypeEngrais());
							ps.setInt(4, this.getPrixUnitaire());
							ps.setInt(5, Integer.parseInt(SessionConfig.getSession().getAttribute("idUser").toString()));
							
							if(ps.executeUpdate()>0) {
								new Message().information("Enregistre avec succes");
							}else {
								new Message().error("votre information n'a pas ete enregistre");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
					}
				}else {
					new Message().warnig("Les info de l'engrais est invalide");
				}
		
		}
	
/*===============================================================================*/
		
		
		/*==========================method to edit engrais=====================================================*/
		public void editEngrais(int engraisId) {
			cdb=new Connectiondb();
			con=cdb.createConnection();
			try {
				
				if(this.getNomEngrais()!=null && this.getTypeEngrais()!=null && this.getIdFournisseur()>0) {
					if(SessionConfig.getSession().getAttribute("idUser")!=null) {
						
						ps=con.prepareStatement("update t_engrais set idFournisseur=?,nomEngrais=?,typeEngrais=?,prixUnitaire=?,idUser=?) where idEngrais =?");
						ps.setInt(1, this.getIdFournisseur());
						ps.setString(2, this.getNomEngrais());
						ps.setString(3, this.getTypeEngrais());
						ps.setInt(4, this.getPrixUnitaire());
						ps.setInt(5, Integer.parseInt(SessionConfig.getSession().getAttribute("idUser").toString()));
						ps.setInt(6, engraisId);	
						
						if(ps.executeUpdate()>0) {
							new Message().information("modifie avec succes");
						}else {
							new Message().error("votre information n'a pas ete modifie");
						}
					}else {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
					}
				}else {
					new Message().warnig("Les info de l'engrais est invalide");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				cdb.closeConnection(con);
			}
		 
		}
	
/*===============================================================================*/
		
		
		/*=================================================
		 * method add engrais
		 * ==========================================*/
		
		public void deleteEngrais(int engraisId) throws IOException {
			
				if(idEngrais>0) {
					if(SessionConfig.getSession().getAttribute("idUser")!=null) {
						
						cdb=new Connectiondb();
						con=cdb.createConnection();
						try {
							ps=con.prepareStatement("delete from t_engrais where idEngrais=?");
							ps.setInt(1, engraisId);
							if(ps.executeUpdate()>0) {
								new Message().information("Supprime avec succes");
							}else {
								new Message().error("echec de suppression");
							}
						} catch (SQLException e) {
							new Message().error("probleme inattendu");
							e.printStackTrace();
						}
						
					}else {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
					}
				}else {
					new Message().warnig("information d'engrais invalide");
				}
		
		}

		
/*===============================================================================*/

	
}
