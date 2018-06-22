package backend.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AtribuicaoCategoria {
	private String fornecedora;
	private String categoria;
	private String faixaPreco;
	
	public AtribuicaoCategoria(String fornecedora, String categoria, String faixaPreco) {
		this.fornecedora = fornecedora;
		this.categoria = categoria;
		this.faixaPreco = faixaPreco;
	}
	
	public String getFornecedora() {
		return fornecedora;
	}
	
	public void setFornecedora(String fornecedora) {
		this.fornecedora = fornecedora;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getFaixaPreco() {
		return faixaPreco;
	}
	
	public void setFaixaPreco(String faixaPreco) {
		this.faixaPreco = faixaPreco;
	}
	
	public static ObservableList<AtribuicaoCategoria> tableView(){
		ResultSet res;
		List<AtribuicaoCategoria> list = new ArrayList<AtribuicaoCategoria>();
		String sql ="select * from ATRIBUICAOCATEGORIA";
		try {
			res = ConnectionManager.query(sql);
			while(res.next())			
				list.add(new AtribuicaoCategoria( res.getString(1), res.getString(2), res.getString(3)));
				
			res.close();
			ConnectionManager.closeQuery();
			
			return FXCollections.observableList(list);
		} catch (SQLException e) {
            throw new RuntimeException(e);
		}
	}
	
	public static void insertAtribuicaoCategoria(AtribuicaoCategoria atribuicaoCategoria) {
		String sql = "insert into ATRIBUICAOCATEGORIA (FORNECEDORA, CATEGORIA, FAIXAPRECO) values("+atribuicaoCategoria+")";
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public static void updateAtribuicaoCategoria(AtribuicaoCategoria atribuicaoCategoria) {
		String sql = "update ATRIBUICAOCATEGORIA set"
				+ atribuicaoCategoria.toStringUpdates()
				+ " where FORNECEDORA = '"+atribuicaoCategoria.fornecedora+"'"
				+ "and CATEGORIA = '"+atribuicaoCategoria.categoria+"'";
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public static void deleteEmpresa(AtribuicaoCategoria atribuicaoCategoria) {
		String sql = "delete from ATRIBUICAOCATEGORIA"+atribuicaoCategoria.toStringRestritions();
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	private String toStringUpdates() {
		String res = "";
		if(faixaPreco.compareTo("") != 0) {
			res += " FAIXAPRECO = '"+this.faixaPreco+"'";
		}
		return res;
	}
	
	private String toStringRestritions() {
		String res = " where ";
		if(fornecedora.compareTo("") != 0) {
			res += " FORNECEDORA = '"+this.fornecedora+"'";
		}
		if(categoria.compareTo("") != 0) {
			if(res.compareTo(" where ") != 0)
				res += " and ";
			res += " CATEGORIA = '"+this.categoria+"'";
		}
		if(res.compareTo(" where ") == 0)
			res = " ";
		return res;
	}
	
	@Override
	public String toString() {
		return "'"+this.fornecedora+"','"+this.categoria+"','"+this.faixaPreco+"'";
	}
}
