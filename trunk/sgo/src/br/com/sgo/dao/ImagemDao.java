package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Imagem;

@Component
public class ImagemDao extends Dao<Imagem> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsImagem;
	
	private final String sqlImagem= "SELECT IMAGEM.imagem_id, IMAGEM.nome, IMAGEM.empresa_id" +
			", IMAGEM.organizacao_id FROM IMAGEM (NOLOCK)";  
	
	public ImagemDao(Session session, ConnJDBC conexao) {
		super(session, Imagem.class);
		this.conexao = conexao;
	}

	public Collection<Imagem> buscaAllImagem(){
		String sql = sqlImagem;
		this.conn = this.conexao.getConexao();
		Collection<Imagem> imgs = new ArrayList<Imagem>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsImagem = this.stmt.executeQuery();
			while (rsImagem.next()) {				
				Imagem img = new Imagem();
				img.setImagem_id(rsImagem.getLong("imagem_id"));
				img.setNome(rsImagem.getString("nome"));
				imgs.add(img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsImagem, stmt, conn);
		return imgs;
	}

	public Collection<Imagem> buscaImagemByNome(String nome){
		String sql = sqlImagem;
		if(nome != null)
			sql += 	" WHERE IMAGEM.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Imagem> imgs = new ArrayList<Imagem>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsImagem = this.stmt.executeQuery();
			Imagem img = new Imagem();
			while (rsImagem.next()) {
				img.setImagem_id(rsImagem.getLong("imagem_id"));
				img.setNome(rsImagem.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsImagem, stmt, conn);
		return imgs;
	}
	
	public Imagem buscaImagemByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlImagem;
		if(empresa != null)
			sql += 	" AND IMAGEM.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND IMAGEM.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (IMAGEM.nome like ?)";
		this.conn = this.conexao.getConexao();		
		Imagem img = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsImagem = this.stmt.executeQuery();
			while (rsImagem.next()) {
				img = new Imagem();
				img.setImagem_id(rsImagem.getLong("imagem_id"));
				img.setNome(rsImagem.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsImagem, stmt, conn);
		return img;
	}

}