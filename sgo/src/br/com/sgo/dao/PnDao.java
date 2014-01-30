package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConexaoPN;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Convenio;
import br.com.sgo.modelo.Detalhamento;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.MeioPagamento;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.TipoContato;
import br.com.sgo.modelo.TipoEndereco;

@Component
public class PnDao {

	private final UsuarioInfo usuarioInfo;
	private ConexaoPN conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroNegocio;
	private ResultSet rsDetalhamento;

	private String sqlPN = " SELECT ent.id_ent, doc.str_nrdocumento as cpf, "
			+ "			ent.str_descricao as clienteNome,  ent.dt_nascimento as clienteNascimento, ent.str_nomebanco as clienteBanco, "
			+ "			ent.str_nomeagencia as clienteAgencia, ent.str_contacorrenteliquidacao as clienteConta, ent.str_observacaobanco as clienteTipoConta, "
			+ "			endereco.str_endereco as clienteEndereco,bairro.bairro as clienteBairro,endereco.str_complemento as clienteComplemento, "
			+ "			endereco.str_numero as clienteNumero, endereco.str_cep as clienteCep FROM public.tb_ent ent "
			+ "				LEFT JOIN public.tb_ent_doc doc ON ent.id_ent = doc.id_ent	"
			+ "				LEFT JOIN public.tb_ent_end endereco ON ent.id_ent = endereco.id_ent "
			+ "				LEFT JOIN public.bairros bairro ON bairro.dne = endereco.id_bairro "
			+ "				LEFT JOIN public.cidade cidade ON cidade.id_cidade = endereco.id_cidade ";

	public PnDao(ConexaoPN conexao,UsuarioInfo usuarioInfo) {
		this.conexao = conexao;
		this.usuarioInfo = usuarioInfo;
	}

	public ParceiroNegocio buscaParceiroNegocio(String cpf) {

		String sql = sqlPN;

		if (!cpf.equals(""))
			sql += " WHERE str_nrdocumento= ?  AND doc.id_tp_doc = 2 ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, cpf);

			this.rsParceiroNegocio = this.stmt.executeQuery();

			if (rsParceiroNegocio.next()) {

				try {

					Calendar cal = new GregorianCalendar();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd");

					if(rsParceiroNegocio.getDate("clienteNascimento") != null){
						String data = formater1.format(rsParceiroNegocio.getDate("clienteNascimento"));
						cal.setTime(new Date(formater1.parse(data).getTime()));
						parceiroNegocio.setDataNascimento(cal);
					}

					parceiroNegocio.setNome(rsParceiroNegocio.getString("clienteNome"));
					parceiroNegocio.setCpf(rsParceiroNegocio.getString("cpf"));
					parceiroNegocio.setPn_id(rsParceiroNegocio.getLong("id_ent"));
					parceiroNegocio.setIsCliente(true);

				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {

				return null;

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parceiroNegocio;

	}

	public ParceiroLocalidade buscaParceiroLocalidade(ParceiroNegocio parceiroNegocio) {

		String sql = sqlPN;

		if (parceiroNegocio != null)
			sql += " WHERE str_nrdocumento= ? AND doc.id_tp_doc = 2 ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		Localidade localidade = new Localidade();
		ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();

		TipoEndereco tipoEndereco = new TipoEndereco();

		tipoEndereco.setTipoEndereco_id(1L);

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, parceiroNegocio.getCpf());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				if (rsParceiroNegocio.getString("clienteCep") != null)
					localidade.setCep(rsParceiroNegocio.getString("clienteCep"));

				if (rsParceiroNegocio.getString("clienteEndereco") != null)
					localidade.setEndereco(rsParceiroNegocio.getString("clienteEndereco"));

				if (rsParceiroNegocio.getString("clienteBairro") != null)
					localidade.setBairro(rsParceiroNegocio.getString("clienteBairro"));

				if (rsParceiroNegocio.getString("clienteComplemento") != null)
					parceiroLocalidade.setComplemento(rsParceiroNegocio.getString("clienteComplemento"));

				if (rsParceiroNegocio.getString("clienteNumero") != null)
					parceiroLocalidade.setNumero(rsParceiroNegocio.getString("clienteNumero"));

				parceiroLocalidade.setLocalidade(localidade);
				parceiroLocalidade.setParceiroNegocio(parceiroNegocio);
				parceiroLocalidade.setTipoEndereco(tipoEndereco);

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parceiroLocalidade;

	}
	
	public String buscaCepByParceiroNegocio(ParceiroNegocio parceiroNegocio) {

		String sql = sqlPN;

		if (parceiroNegocio != null)
			sql += " WHERE str_nrdocumento= ?  AND doc.id_tp_doc = 2 ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());
		
		String cep = "";

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, parceiroNegocio.getCpf());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				if (rsParceiroNegocio.getString("clienteCep") != null)
					cep = rsParceiroNegocio.getString("clienteCep");
			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cep;

	}
	
	public String buscaNumeroByParceiroNegocio(ParceiroNegocio parceiroNegocio) {

		String sql = sqlPN;

		if (parceiroNegocio != null)
			sql += " WHERE str_nrdocumento= ? AND doc.id_tp_doc = 2  ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());
		
		String numero = "";

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, parceiroNegocio.getCpf());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				if (rsParceiroNegocio.getString("clienteNumero") != null)
					numero = rsParceiroNegocio.getString("clienteNumero");
			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return numero;

	}
	
	public String buscaComplementoByParceiroNegocio(ParceiroNegocio parceiroNegocio) {

		String sql = sqlPN;

		if (parceiroNegocio != null)
			sql += " WHERE str_nrdocumento= ?  AND doc.id_tp_doc = 2 ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());
		
		String complemento = "";

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, parceiroNegocio.getCpf());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				if (rsParceiroNegocio.getString("clienteComplemento") != null)
					complemento = rsParceiroNegocio.getString("clienteComplemento");
			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return complemento;

	}

	public ParceiroInfoBanco buscaParceiroInfoBanco(ParceiroNegocio parceiroNegocio) {

		String sql = sqlPN;

		if (parceiroNegocio != null)
			sql += " WHERE str_nrdocumento= ? AND doc.id_tp_doc = 2  ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		ParceiroInfoBanco parceiroInfoBanco = new ParceiroInfoBanco();
		Banco banco = new Banco();
		MeioPagamento meioPagamento = new MeioPagamento();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, parceiroNegocio.getCpf());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				if (rsParceiroNegocio.getString("clienteConta") != null) {

					String formatConta = rsParceiroNegocio.getString("clienteConta");

					while (formatConta.startsWith("0")) {
						formatConta = formatConta.replaceFirst("0", "");
					}

					parceiroInfoBanco.setContaCorrente(formatConta);

				}

				if (rsParceiroNegocio.getString("clienteTipoConta") != null)
					meioPagamento.setNome(rsParceiroNegocio.getString("clienteTipoConta").equals("CONTA CORRENTE") ? "TED" : "OP");

				String[] aux = rsParceiroNegocio.getString("clienteAgencia").split("-");

				banco.setNome(rsParceiroNegocio.getString("clienteBanco"));

				parceiroInfoBanco.setAgenciaNumero(aux[0]);
				parceiroInfoBanco.setBanco(banco);
				parceiroInfoBanco.setMeioPagamento(meioPagamento);
				parceiroInfoBanco.setParceiroNegocio(parceiroNegocio);

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parceiroInfoBanco;

	}

	public Collection<ParceiroContato> buscaParceiroContatos(
			ParceiroNegocio parceiroNegocio) {

		String sql = " SELECT str_ddd,str_fone FROM tb_ent_fone AS contato WHERE contato.str_ativo = 'A' AND contato.id_ent = ? ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		Collection<ParceiroContato> parceiroContatos = new ArrayList<ParceiroContato>();
		TipoContato tipoContato = new TipoContato();

		tipoContato.setTipoContato_id(1L);

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceiroNegocio.getPn_id());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				ParceiroContato parceiroContato = new ParceiroContato();

				parceiroContato.setNome(rsParceiroNegocio.getString("str_fone"));
				parceiroContato.setParceiroNegocio(parceiroNegocio);
				parceiroContato.setTipoContato(tipoContato);

				parceiroContatos.add(parceiroContato);

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parceiroContatos;

	}

	public Collection<ParceiroBeneficio> buscaParceiroBeneficios(ParceiroNegocio parceiroNegocio) {

		String sql = " SELECT doc.str_nrdocumento as beneficio, tipoent.str_descricao as tipo " +
						" FROM public.tb_ent ent  " +
						" LEFT JOIN public.tb_ent_doc doc ON ent.id_ent = doc.id_ent " +	
						" LEFT JOIN public.tb_ent_tipo tipo ON tipo.id_ent = ent.id_ent " +
						" LEFT JOIN public.tb_tp_ent tipoent on tipoent.id_tp_ent = tipo.id_tp_ent " +
						" WHERE doc.id_ent= ? and doc.id_tp_doc = 3 ";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		Collection<ParceiroBeneficio> parceiroBeneficios = new ArrayList<ParceiroBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			//System.out.println(sql);

			this.stmt.setLong(1, parceiroNegocio.getPn_id());

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				ParceiroBeneficio parceiroBeneficio = new ParceiroBeneficio();
				Convenio convenio = new Convenio();
				convenio.setNome(rsParceiroNegocio.getString("tipo"));

				if(rsParceiroNegocio.getString("tipo") == null){
					convenio.setConvenio_id(1L);
				} else {
					convenio.setConvenio_id(rsParceiroNegocio.getString("tipo").equals("PM_SP") ? 2L : rsParceiroNegocio.getString("tipo").equals("GOV_RJ") ? 3L : 1L);
				}

				parceiroBeneficio.setNumeroBeneficio(rsParceiroNegocio.getString("beneficio"));
				parceiroBeneficio.setConvenio(convenio);

				parceiroBeneficios.add(parceiroBeneficio);

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parceiroBeneficios;

	}

	public Detalhamento buscaDetalhamento(String beneficio) {

		Detalhamento detalhamento = new Detalhamento();
		Map<String, Double> debitos = new TreeMap<String, Double>();
		Map<String, Double> creditos = new TreeMap<String, Double>();

		String sql = "SELECT " + "d.id_extrato, " + "d.nr_matricula, "
				+ "d.str_competencia, " + "d.str_nome, " + "d.str_especie, "
				+ "d.str_descespecie, " + "d.dt_periodoinicial, "
				+ "d.dt_periodofinal, " + "d.str_meiopgto, "
				+ "d.dt_dispinicial, " + "d.dt_dispfinal, " + "d.str_banco, "
				+ "d.str_codbanco, " + "d.str_contacorrente, "
				+ "d.str_agencia, " + "d.str_enderecobanco, "
				+ "d.str_meio_pgtodesc, " + "d.str_status, " + "v.n_valor, "
				+ "v.str_descrubrica, " + "v.str_rubrica, " + "v.str_sinal "
				+ "FROM " + "dataprev_extrato d, "
				+ "dataprev_extrato_valor v " + "WHERE "
				+ "d.nr_matricula = ? and " + "d.str_status = 'A' and "
				+ "v.id_extrato = d.id_extrato";

		this.conn = this.conexao.getConexao(usuarioInfo.getOrganizacao());

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, Long.parseLong(beneficio));
			this.rsDetalhamento = this.stmt.executeQuery();

			Integer countDebito = 1;
			Integer countCredito = 1;

			while (rsDetalhamento.next()) {

				detalhamento.setMatricula(String.valueOf(rsDetalhamento.getLong("nr_matricula")));
				detalhamento.setCompetencia(rsDetalhamento.getString("str_competencia").substring(0, 2)+ "/"+ rsDetalhamento.getString("str_competencia").substring(2, 6));
				detalhamento.setNome(rsDetalhamento.getString("str_nome"));
				detalhamento.setEspecie(rsDetalhamento.getString("str_especie"));
				detalhamento.setDescespecie(rsDetalhamento.getString("str_descespecie"));
				detalhamento.setPeriodoinicial(rsDetalhamento.getDate("dt_periodoinicial"));
				detalhamento.setPeriodofinal(rsDetalhamento.getDate("dt_periodofinal"));
				detalhamento.setDispinicial(rsDetalhamento.getDate("dt_dispinicial"));
				detalhamento.setDispfinal(rsDetalhamento.getDate("dt_dispfinal"));
				detalhamento.setContacorrente(rsDetalhamento.getString("str_contacorrente"));
				detalhamento.setBanco(rsDetalhamento.getString("str_banco"));

				String[] aux = rsDetalhamento.getString("str_agencia").split("-");

				int s = aux.length;

				detalhamento.setCodbanco(aux[0]);

				if(s > 1)
					detalhamento.setAgencia(aux[1]);

				detalhamento.setEnderecobanco(rsDetalhamento.getString("str_enderecobanco"));
				detalhamento.setMeio_pgtodesc(rsDetalhamento.getString("str_meio_pgtodesc"));
				detalhamento.setStatus(rsDetalhamento.getString("str_status"));

				if(rsDetalhamento.getString("str_sinal").equals("-")) {
					debitos.put(countDebito + ": " + rsDetalhamento.getString("str_descrubrica"),rsDetalhamento.getDouble("n_valor"));
					countDebito++;
				}	

				if(rsDetalhamento.getString("str_sinal").equals("+")) {
					creditos.put(countCredito + ": " + rsDetalhamento.getString("str_descrubrica"),rsDetalhamento.getDouble("n_valor"));
					countCredito++;
				}

			}

			detalhamento.setDebitos(debitos);
			detalhamento.setCreditos(creditos);

			this.rsDetalhamento.close();
			this.stmt.close();
			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return detalhamento;
	}
}
