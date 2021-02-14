package fournisseur;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import config.Connectiondb;
@ViewScoped
public class FournisseurInfo implements Serializable {

	private static final long serialVersionUID = 3392891465788156747L;

	
	public FournisseurInfo() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	private List<Fournisseur> listFournisseur;
	public List<Fournisseur> getListFournisseur() {
		return listFournisseur;
	}
	public void setListFournisseur(List<Fournisseur> listFournisseur) {
		this.listFournisseur = listFournisseur;
	}

	/*========================================================
	 section: les variables objet qui recupere les info dans la base
	 ========================================================*/
	
	Connectiondb cdb;
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	/*================================================================
	 section:	cette fonction recupere les info de tous les fournisseurs
	================================================================= */

	@PostConstruct
	public void getAllFournisseur() {
		cdb=new Connectiondb();
		con=cdb.createConnection();
		listFournisseur =new ArrayList<Fournisseur>();
		try {
			ps=con.prepareStatement("SELECT * FROM t_fournisseur,t_engrais where t_fournisseur.idFournisseur=t_engrais.idEngrais");
			rs=ps.executeQuery();
			while(rs.next()) {
				listFournisseur.add(new Fournisseur(rs.getInt("idFournisseur"),
						rs.getString("nomFournisseur"), rs.getString("ville"), rs.getInt("tel"), rs.getString("mail")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	

}
