package engrais;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import config.Connectiondb;
import fournisseur.Fournisseur;
@Named
@ViewScoped
public class EngraisInfo implements Serializable {

	private static final long serialVersionUID = 300952144963411090L;

	private Engrais engrais;
	private List<EngraisInfo> listEngrais;
	
	public List<EngraisInfo> getListEngrais() {
		return listEngrais;
	}

	public void setListEngrais(List<EngraisInfo> listEngrais) {
		this.listEngrais = listEngrais;
	}

	private Date savedDate;
	public EngraisInfo() {
		
	}
	
	
	
	public EngraisInfo(Engrais engrais,Date savedDate) {
		this.engrais=engrais;
		this.savedDate=savedDate;
	}
	
	public Engrais getEngrais() {
		return engrais;
	}

	public void setEngrais(Engrais engrais) {
		this.engrais = engrais;
	}
/*
	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	*/
	
	/*==========================variable object=====================*/
	
	public Date getSavedDate() {
		return savedDate;
	}

	public void setSavedDate(Date savedDate) {
		this.savedDate = savedDate;
	}


	/*=======================Variable for retrieving data using connection connection==============================*/
		PreparedStatement ps;
		Connection con;
		Connectiondb cdb;
		ResultSet rs;
		 
	/*=====================================================*/
	
	
	/*==========================method to get engrais information=====================================================*/

	@PostConstruct
	public void getAllEngrais(){
		listEngrais=new ArrayList<>();
		cdb=new Connectiondb();
		con=cdb.createConnection();
		try {
			ps=con.prepareStatement("Select * from t_engrais,t_fournisseur where t_engrais.idFournisseur=t_fournisseur.idFournisseur");
			rs=ps.executeQuery();
			while(rs.next()) {
				listEngrais.add(new EngraisInfo(new Engrais(rs.getInt("idEngrais"),
						rs.getString("nomEngrais"), rs.getString("typeEngrais"),rs.getInt("prixUnitaire"),
						new Fournisseur(rs.getInt("idFournisseur"), rs.getString("nomFournisseur"), rs.getString("ville"),
								rs.getInt("tel"),rs.getString("mail"))),rs.getDate("dateEnregistre")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			cdb.closeResult(rs);
			cdb.closePrepareStatement(ps);
			cdb.closeConnection(con);
		}
	
		
	}
	
	

}
