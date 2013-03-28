package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConexaoPN;
import br.com.sgo.modelo.Detalhamento;
import br.com.sgo.modelo.ParceiroNegocio;


@Component
public class PnDao {

	private ConexaoPN conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCliente;
	private ResultSet rsDetalhamento;

	public PnDao(ConexaoPN conexao) {
		this.conexao = conexao;
	}

	public ParceiroNegocio buscaParceiroNegocioPN(String beneficio) {
		
		/* 
		String sql = "SELECT " +
							"ent.str_descricao as clienteNome, ent.dt_nascimento as clienteNascimento, " +
							"doc.str_nrdocumento as clienteCpf, " +
							"('(' || coalesce((fone.str_ddd),'') || ')' || '  ' || coalesce((fone.str_fone),'')) as clienteFone, " +
							"endereco.str_logradouro || ' ' || endereco.str_endereco as clienteEndereco, " +
							"endereco.str_numero as clienteNumero, " +
							"endereco.str_cep as clienteCep, " +
							"bairro.bairro as clienteBairro, " +
							"cidade.str_descricao as clienteCidade, " +
							"ent.str_nomebanco as clienteBanco, " +
							"ent.str_nomeagencia as clienteAgencia, " +
							"ent.str_contacorrenteliquidacao as clienteConta, " +
							"ent.str_observacaobanco as clienteTipoConta " +
						"FROM public.tb_ent ent " +
							"LEFT JOIN " +
								"public.tb_ent_doc doc ON ent.id_ent = doc.id_ent AND doc.id_tp_doc = 2 " +
							"INNER JOIN " +
								"public.tb_ent_doc ben ON ent.id_ent = ben.id_ent AND (ben.str_nrdocumento = ? OR ben.str_nrdocumento = ?) " +
							"LEFT JOIN " +
								"public.tb_ent_fone fone ON ent.id_ent = fone.id_ent " +
							"LEFT JOIN " +
								"public.tb_ent_end endereco ON ent.id_ent = endereco.id_ent " +
							"LEFT JOIN " +
								"public.bairros bairro ON bairro.dne = endereco.id_bairro " +
							"LEFT JOIN " +
								"public.cidade cidade ON cidade.id_cidade = endereco.id_cidade ";

		this.conn = this.conexao.getConexao();

		ParceiroNegocio c = new ParceiroNegocio();

		String b = "";

		if(beneficio.charAt(0) == '0')
			b = beneficio.substring(1,10);
		else 
			b = beneficio;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, beneficio);
			this.stmt.setString(2, b);

			this.rsCliente = this.stmt.executeQuery();

			if (rsCliente.next()) {

				c.setBeneficio(beneficio);
				c.setNome(rsCliente.getString("clienteNome"));

				try {

					Calendar cal = new GregorianCalendar();
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd");
					String data = formater1.format(rsCliente.getDate("clienteNascimento"));
					cal.setTime(new Date(formater1.parse(data).getTime()));
					c.setNascimento(cal);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				if(rsCliente.getString("clienteCpf") != null)
					c.setCpf(rsCliente.getString("clienteCpf"));

				if(rsCliente.getString("clienteFone") != null)
					c.setTelefoneRes(rsCliente.getString("clienteFone"));
				
				if(rsCliente.getString("clienteEndereco") != null)
					c.setEndereco(rsCliente.getString("clienteEndereco"));
				
				if(rsCliente.getString("clienteNumero") != null)
					c.setNumero(rsCliente.getString("clienteNumero"));

				if(rsCliente.getString("clienteCep") != null)
					c.setCep(rsCliente.getString("clienteCep"));
				
				if(rsCliente.getString("clienteBairro") != null)
					c.setBairro(rsCliente.getString("clienteBairro"));
				
				if(rsCliente.getString("clienteCidade") != null)
					c.setCidade(rsCliente.getString("clienteCidade"));

				if(rsCliente.getString("clienteBanco") != null)
					c.setBanco(rsCliente.getString("clienteBanco"));

				String formatConta = rsCliente.getString("clienteConta");

				while (formatConta.startsWith("0")) {
					formatConta = formatConta.replaceFirst("0","");
				}

				c.setConta(formatConta);

				if(rsCliente.getString("clienteTipoConta") != null)
					c.setTipoConta(rsCliente.getString("clienteTipoConta"));

				if (c.getTipoConta().equals("CONTA CORRENTE")) {
					c.setTipoPagamento("DOC");
				} else if (c.getTipoConta().equals("CARTAO MAGNETICO")) {
					c.setTipoPagamento("OP");
				}

			} else {

				return null;

			}

			this.conexao.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/

		return new ParceiroNegocio();

	}
	
	public Detalhamento buscaDetalhamento(String beneficio) {

		Detalhamento detalhamento = new Detalhamento();
		Map<String,Double> debitos = new TreeMap<String,Double>();
		Map<String,Double> creditos = new TreeMap<String,Double>();

		String sql = "SELECT " +
					  	"d.id_extrato, " +
					  	"d.nr_matricula, " +
					  	"d.str_competencia, " +
					  	"d.str_nome, " +
					  	"d.str_especie, " +
					  	"d.str_descespecie, " +
					  	"d.dt_periodoinicial, " +
					  	"d.dt_periodofinal, " +
					  	"d.str_meiopgto, " + 
					  	"d.dt_dispinicial, " +
					  	"d.dt_dispfinal, " + 
					  	"d.str_banco, " + 
					  	"d.str_codbanco, " +
					  	"d.str_contacorrente, " +
					  	"d.str_agencia, " +
					  	"d.str_enderecobanco, " +
					  	"d.str_meio_pgtodesc, " +
					  	"d.str_status, " +
					  	"v.n_valor, " +
					  	"v.str_descrubrica, " +
					  	"v.str_rubrica, " +
					  	"v.str_sinal " + 
					"FROM " + 
					  	"dataprev_extrato d, " +
					  	"dataprev_extrato_valor v " +
					"WHERE " +
						"d.nr_matricula = ? and " +
						"d.str_status = 'A' and " +
						"v.id_extrato = d.id_extrato";

		this.conn = this.conexao.getConexao();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, Long.parseLong(beneficio));
			this.rsDetalhamento = this.stmt.executeQuery();

			Integer countDebito = 1;
			Integer countCredito = 1;

			while (rsDetalhamento.next()){

				detalhamento.setMatricula(String.valueOf(rsDetalhamento.getLong("nr_matricula")));
				detalhamento.setCompetencia(rsDetalhamento.getString("str_competencia").substring(0,2) + "/" + rsDetalhamento.getString("str_competencia").substring(2,6));
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

				detalhamento.setCodbanco(aux[0]);
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
