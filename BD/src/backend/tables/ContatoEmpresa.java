package backend.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContatoEmpresa {
	private String empresa;
	private String email;
	private String nome;
	private String telefone;
	
	public ContatoEmpresa(String empresa, String email, String nome, String telefone) {
		this.empresa = empresa;
		this.email = email;
		this.nome = nome;
		this.telefone = telefone;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public static ObservableList<ContatoEmpresa> tableView(){
		ResultSet res;
		List<ContatoEmpresa> list = new ArrayList<ContatoEmpresa>();
		try {
			res = ConnectionManager.query("select * from CONTATO_EMPRESA");
			while(res.next())
				list.add(new ContatoEmpresa(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
			res.close();
			ConnectionManager.closeQuery();
			
			return FXCollections.observableList(list);
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}