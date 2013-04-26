package br.com.sgo.jasper;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;



public class FormularioDataSource implements JRDataSource {

	private Object valorAtual;
	private Iterator<?> itrSolicitacao;
	private boolean irParaProximoRelatorio;
	private Formulario formulario;

	public FormularioDataSource(List<?> lista) {
		this.itrSolicitacao = lista.iterator();
	}

	@Override
	public Object getFieldValue(JRField campo) throws JRException {

		Object valor = null;
		formulario = (Formulario) valorAtual;

		Collection<Contrato> contratos = formulario.getContratos();

		for (Iterator<Contrato> it  = contratos.iterator(); it.hasNext();) {

				Contrato c = (Contrato) it.next();

				if("formularioData".equals(campo.getName())) {
					valor = new Timestamp(formulario.getCreated().getTime().getTime());
				}else if("consultorNome".equals(campo.getName())){
					valor = c.getUsuario().getNome();
				}else if("consultorSupervisor".equals(campo.getName())){
					valor = c.getUsuario().getSupervisorUsuario();
				}else if("clienteNome".equals(campo.getName())){
					valor = formulario.getParceiroNegocio().getNome();
				}else if("clienteBeneficio".equals(campo.getName())){
					valor = c.getNumeroBeneficio();
				}else if("contratoProduto".equals(campo.getName())){
					valor = c.getProduto().getNome();
				}else if("contratoBanco".equals(campo.getName())){
					valor = c.getBanco().getNome();
				}else if("clienteCpf".equals(campo.getName())){
					valor = formulario.getParceiroNegocio().getCpf();
				}else if("clienteNascimento".equals(campo.getName())){
					valor = new Timestamp(formulario.getParceiroNegocio().getDataNascimento().getTime().getTime());
				}else if("clienteCep".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getLocalidade().getCep();
				}else if("clienteTelRes".equals(campo.getName())){
					valor = "";
				}else if("clienteTelCel".equals(campo.getName())){
					valor = "";
				}else if("clienteBanco".equals(campo.getName())){
					valor = "";
				}else if("clienteAgencia".equals(campo.getName())){
					valor = "";
				}else if("clienteConta".equals(campo.getName())){
					valor = "";
				}else if("clienteTipoConta".equals(campo.getName())){
					valor = "";
				}else if("clienteTipoPagamento".equals(campo.getName())){
					valor = "";
				}else if("clienteSenha".equals(campo.getName())){
					valor = "";
				}else if("contratoBancoComprado".equals(campo.getName())){
					valor = c.getRecompraBanco().getNome();
				}else if("contratoParcelaAberto".equals(campo.getName())){
					valor = c.getQtdParcelasAberto();
				}else if("contratoValor".equals(campo.getName())){
					valor = c.getValorContrato();
				}else if("contratoParcela".equals(campo.getName())){
					valor = c.getValorParcela();
				}else if("contratoPrazo".equals(campo.getName())){
					valor = c.getPrazo();
				}else if("contratoDivida".equals(campo.getName())){
					valor = c.getValorDivida();
				}else if("contratoDesconto".equals(campo.getName())){
					valor = c.getDesconto();
				}else if("contratoLiquido".equals(campo.getName())){
					valor = c.getValorLiquido();
				}else if("contratoCoeficiente".equals(campo.getName())){
					valor = c.getCoeficiente().getValor();
				}else if("contratoObs".equals(campo.getName())){
					valor = c.getObservacao();
				}		
		}

		return valor;
	}

	@Override
	public boolean next() throws JRException {

		valorAtual = itrSolicitacao.hasNext() ? itrSolicitacao.next() : null;
		irParaProximoRelatorio = (valorAtual != null);

		return irParaProximoRelatorio;
	}
	

}
