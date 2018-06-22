package backend.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutosFornecidos {
	private String fornecedora;
	private int coquetel;
	private String nome;
	private int quantidade;
	
	public ProdutosFornecidos(String fornecedora, int coquetel, String nome, int quantidade) {
		this.fornecedora = fornecedora;
		this.coquetel = coquetel;
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	public String getFornecedora() {
		return fornecedora;
	}
	
	public void setFornecedora(String fornecedora) {
		this.fornecedora = fornecedora;
	}
	
	public int getCoquetel() {
		return coquetel;
	}
	
	public void setCoquetel(int coquetel) {
		this.coquetel = coquetel;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public static ObservableList<ProdutosFornecidos> tableView(){
		ResultSet res;
		List<ProdutosFornecidos> list = new ArrayList<ProdutosFornecidos>();
		String sql="select * from PRODUTOSFORNECIDOS";
		try {
			res = ConnectionManager.query(sql);
			while(res.next())			
				list.add(new ProdutosFornecidos(res.getString(1), res.getInt(2), res.getString(3), res.getInt(4)));
				
			res.close();
			ConnectionManager.closeQuery();
			
			return FXCollections.observableList(list);
		} catch (SQLException e) {
            throw new RuntimeException(e);
		}
	}
	
	public static void insertProdutosFornecidos(ProdutosFornecidos produtosFornecidos) {
		String sql = "insert into PRODUTOSFORNECIDOS (FORNECEDORA, COQUETEL, NOME, QUANTIDADE) values("+produtosFornecidos+")";
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public static void updateProdutosFornecidos(ProdutosFornecidos produtosFornecidos) {
		String sql = "update PRODUTOSFORNECIDOS set"
				+ produtosFornecidos.toStringUpdates()
				+ " where FORNECEDORA = '"+produtosFornecidos.fornecedora+"'"
				+ "and COQUETEl = "+produtosFornecidos.coquetel
				+ "and NOME ='"+produtosFornecidos.nome+"'";
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public static void deleteEmpresa(ProdutosFornecidos produtosFornecidos) {
		String sql = "delete from PRODUTOSFORNECIDOS"+produtosFornecidos.toStringRestritions();
		try {
			ConnectionManager.query(sql);
			ConnectionManager.closeQuery();
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	
	private String toStringUpdates() {
		String res = "";
		if(quantidade != 0) {
			res += " QUANTIDADE = "+this.quantidade;
		}
		return res;
	}
	
	private String toStringRestritions() {
		String res = " where ";
		if(fornecedora.compareTo("") != 0) {
			res += " FORNECEDORA = '"+this.fornecedora+"'";
		}
		if(coquetel != 0) {
			if(res.compareTo(" where ") != 0)
				res += " and ";
			res += " COQUETEL = "+this.coquetel;
		}
		if(nome.compareTo("") != 0) {
			if(res.compareTo(" where ") != 0)
				res += " and ";
			res += " NOME = '"+this.nome+"'";
		}
		if(res.compareTo(" where ") == 0)
			res = " ";
		return res;
	}
	
	@Override
	public String toString() {
		return "'"+this.fornecedora+"',"+this.coquetel+",'"+this.nome+"',"+this.quantidade;
	}
}
