package user;

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

@ViewScoped
@Named
public class User implements Serializable {

	private static final long serialVersionUID = -710605740454698863L;
	
	private int idUser;
	private String tel;
	private String nom,prenom,userName,password,secondPass,type;
	private boolean status;
	
	public User(int idUser, String nom,String prenom,String tel,String userName,String password,String type, boolean status) {
		this.idUser=idUser;
		this.nom=nom;
		this.prenom=prenom;
		this.tel=tel;
		this.userName=userName;
		this.password=password;
		this.type=type;
		this.status=status;
	}
	
	public User() {
	}
	
	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		if(nom.length()>2) {
			this.nom = nom;
		}
		else{
			System.out.println("invalid nom");
		}
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		if(prenom.length()>2) {
			this.prenom = prenom;
		}else {
			System.out.println("invalid prenom");
		}
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		if(userName!=null && userName.length()<=60) {
			this.userName = userName;
		}else {
			System.out.println("invalid userName");
		}
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		try {
			if(tel.length()==8) {
				this.tel = tel;
			}else {
				System.out.println("invalid Number");
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		if(password.length()>=6 && password.length()<=15) {
			this.password = password;
		}else {
			System.out.println("invalid password");
		}
	}


	public String getSecondPass() {
		return secondPass;
	}

	public void setSecondPass(String secondPass) {
		this.secondPass = secondPass;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public boolean validateUser(){
		if(this.getUserName()!=null && this.getNom()!=null && this.getPrenom()!=null
				&& this.getPrenom()!=null &&( this.getPassword()!=null &&  this.getPassword()==this.getSecondPass()) && 
				this.getType()!=null && this.getTel().trim()!=null && this.getTel().length()==8) {
			return true;
		
		}
		return false;
	}
	
	/*========================================
	 * variable object to connect on database
	 * ===================================*/
		Connectiondb cdb;
		PreparedStatement ps;
		Connection con ;
		ResultSet rs;
	/*=========================*/
	
	/*======================================
	 * 	this methode create user
	 * =================================*/
	
	public void createUser() {
		if(validateUser()) {
			
			cdb=new Connectiondb();
			con=cdb.createConnection();
			try {
				ps=con.prepareStatement("INSERT INTO t_user (nom,prenom,userName,pass,tel,type,status) values(?,?,?,?,?,?,?)");
				ps.setString(1, this.getNom());
				ps.setString(2, this.getPrenom());
				ps.setString(3, this.getUserName());
				ps.setString(4, this.getPassword());
				ps.setInt(5, Integer.parseInt(this.getTel().trim()));
				ps.setString(6, this.getType());
				ps.setBoolean(7, true);
				ps.executeUpdate();
				new Message().error("Fin d'ajout d'utilisateur");
			} catch (SQLException e) {
				new Message().error("Echec d'ajout d'utilisateur");
				e.printStackTrace();
			}finally {
				cdb.closePrepareStatement(ps);
				cdb.closeConnection(con);
			}
		}
		
	}
	
	/*==============================================================*/
	
	
	
	/*======================================
	 * 	this methode create user
	 * =================================*/
	
	public void editUser(int userId) {
		if(validateUser()) {
			
			cdb=new Connectiondb();
			con=cdb.createConnection();
			try {
				ps=con.prepareStatement("update t_user set nom=?,prenom=?,userName=?,pass=?,tel=?,type=? where=?");
				ps.setString(1, this.getNom());
				ps.setString(2, this.getPrenom());
				ps.setString(3, this.getUserName());
				ps.setString(4, this.getPassword());
				ps.setInt(5, Integer.parseInt(this.getTel().trim()));
				ps.setString(6, this.getType());
				ps.setInt(7,userId);
				ps.executeUpdate();
				new Message().information("modification d'utilisateur fini avec succes");
			} catch (SQLException e) {
				new Message().error("Echec de modification d'utilisateur");
				e.printStackTrace();
			}finally {
				cdb.closeConnection(con);
			}
		}
		
	}
	
	/*==============================================================*/
	
	/*===========================================
	 * methode d'authentification
	 * ========================================= */
	
	public void doLogin() {
		if(this.getUserName()!=null && this.getPassword()!=null) {
			cdb=new Connectiondb();
			con=cdb.createConnection();
			try {
				ps=con.prepareStatement("Select idUser,nom,prenom,userName,type,tel from t_user where userName=? and pass=? and status=?");
				ps.setString(1, this.getUserName());
				ps.setString(2, this.getPassword());
				if(rs.next()) {
					SessionConfig.getSession().setAttribute("idUser", rs.getInt("idUser"));
					SessionConfig.getSession().setAttribute("nom", rs.getString("nom"));
					SessionConfig.getSession().setAttribute("prenom", rs.getString("prenom"));
					SessionConfig.getSession().setAttribute("userName", rs.getString("userName"));
					SessionConfig.getSession().setAttribute("type", rs.getString("type"));
					SessionConfig.getSession().setAttribute("tel", rs.getString("tel"));
					FacesContext.getCurrentInstance().getExternalContext().redirect("view/welcome.xhtml");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				new Message().error("echec de connexion");
			}finally {
				cdb.closeConnection(con);
			}
		}else {
			new Message().warnig("Nom d'utilisateur ou mot de passe incorrecte");
		}
	}
	
	/*============================================*/
	


	
	

}
