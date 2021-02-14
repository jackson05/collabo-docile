package demande;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import config.Connectiondb;
import config.SessionConfig;
import message.Message;

@Named
@ViewScoped
public class Demande implements Serializable {

	
	private static final long serialVersionUID = -1856167752091316674L;

	
	public Demande() {
		
	}
	
	private int idEngrais,idAdresse,codeClient,avance,quantite,annee;

	private String saison;
	
	
	public int getIdEngrais() {
		return idEngrais;
	}

	public void setIdEngrais(int idEngrais) {
		this.idEngrais = idEngrais;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public int getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(int codeClient) {
		this.codeClient = codeClient;
	}

	public int getAvance() {
		return avance;
	}

	public void setAvance(int avance) {
		this.avance = avance;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getSaison() {
		return saison;
	}

	public void setSaison(String saison) {
		this.saison = saison;
	}

	/*=======================
	 * la fonction qui valide la demande
	 ===============*/
	public boolean validateDemande() {
		if(this.getIdEngrais()>0 && this.getIdEngrais()>0 && this.getIdAdresse()>0 && 
				this.getAvance()>0 && this.getQuantite()>0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	/*========================
	 * les variables objet 
	====================================== */
	
	PreparedStatement ps;
	Connectiondb cdb;
	Connection con;
	/*====================*/
	public void saveDemande() {
		if(SessionConfig.getSession().getAttribute("idUser")!=null) {
			if(validateDemande()) {
				
			cdb=new Connectiondb();
			con=cdb.createConnection();
			}else {
				new Message().warnig("Votre demande est invalide");
			}
		}
		
	}

}
