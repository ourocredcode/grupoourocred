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
import br.com.sgo.modelo.Menu;

@Component
public class MenuDao extends Dao<Menu> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsMenu;

	public MenuDao(Session session, ConnJDBC conexao) {
		super(session, Menu.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<Menu> buscaMenus(Long empresa_id, Long organizacao_id,
			String nome) {

		String sql = "select MENU.menu_id, MENU.nome from MENU (NOLOCK) WHERE MENU.empresa_id = ? AND MENU.organizacao_id = ? AND MENU.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Menu> menus = new ArrayList<Menu>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsMenu = this.stmt.executeQuery();

			while (rsMenu.next()) {
				Menu menu = new Menu();

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
