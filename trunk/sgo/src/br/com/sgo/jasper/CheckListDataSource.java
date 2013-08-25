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

public class CheckListDataSource implements JRDataSource {

	private Object valorAtual;
	private Iterator<?> itrSolicitacao;
	private boolean irParaProximoRelatorio;
	private Formulario formulario;

	public CheckListDataSource(List<?> lista) {
		this.itrSolicitacao = lista.iterator();
	}

	@Override
	public Object getFieldValue(JRField campo) throws JRException {

		Object valor = null;
		formulario = (Formulario) valorAtual;

		Collection<Contrato> contratos = formulario.getContratos();

		for (Iterator<Contrato> it  = contratos.iterator(); it.hasNext();) {

				Contrato c = (Contrato) it.next();

				if("consultorNome".equals(campo.getName())){
					valor = c.getUsuario().getApelido();
				}else if("consultorSupervisor".equals(campo.getName())){
					valor = c.getUsuario().getSupervisorUsuario().getApelido();
				}else if("clienteNome".equals(campo.getName())){
					valor = formulario.getParceiroNegocio().getNome();
				}else if("clienteCpf".equals(campo.getName())){
					valor = formulario.getParceiroNegocio().getCpf();
				}else if("clienteTelRes".equals(campo.getName())){
					valor = "";
				}else if("clienteTelCel".equals(campo.getName())){
					valor = "";
				}else if("dataAssinatura".equals(campo.getName())){
					valor = new Timestamp(c.getLogistica().getDataAssinatura().getTime().getTime());
				}else if("clienteEndereco".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getLocalidade().getTipoLocalidade().getNome() + " " + formulario.getParceiroLocalidade().getLocalidade().getEndereco();
				}else if("clienteNumero".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getNumero();
				}else if("clienteCep".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getLocalidade().getCep();
				}else if("clienteBairro".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getLocalidade().getBairro();
				}else if("clienteCidade".equals(campo.getName())){
					valor = formulario.getParceiroLocalidade().getLocalidade().getCidade().getNome();
				}else if("periodoAssinatura".equals(campo.getName())){
					valor = c.getLogistica().getPeriodo().getNome();
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
