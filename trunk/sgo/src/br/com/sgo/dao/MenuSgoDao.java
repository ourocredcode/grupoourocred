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
import br.com.sgo.modelo.MenuSgo;

@Component
public class MenuSgoDao extends Dao<MenuSgo> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsMenu;

	public MenuSgoDao(Session session , ConnJDBC conexao) {
		super(session, MenuSgo.class);
		this.session = session;
		this.conexao =conexao;
	}
	
	public Collection<MenuSgo> buscaMenus(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select MENU.menu_id, MENU.nome from MENU (NOLOCK) WHERE MENU.empresa_id = ? AND MENU.organizacao_id = ? AND MENU.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<MenuSgo> menus = new ArrayList<MenuSgo>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsMenu = this.stmt.executeQuery();

			while (rsMenu.next()) {
				MenuSgo menu = new MenuSgo();

				menu.setMenu_id(rsMenu.getLong("menu_id"));				
				menu.setNome(rsMenu.getString("nome"));

				menus.add(menu);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsMenu, stmt, conn);

		return menus;

	}

}
