package br.com.sgo.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.caelum.vraptor.ioc.Component;

@Entity
@Component
@Table(name = "CONTRATO")
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "contrato_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long contrato_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "organizacaodigitacao_id", updatable = true, nullable = true)
	private Organizacao organizacaoDigitacao;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id", updatable = true, nullable = true)
	private Formulario formulario;

	@ManyToOne
	@JoinColumn(name = "coeficiente_id", updatable = true, nullable = true)
	private Coeficiente coeficiente;

	@ManyToOne
	@JoinColumn(name = "produto_id", updatable = true, nullable = true)
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "tabela_id", updatable = true, nullable = true)
	private Tabela tabela;
	
	@ManyToOne
	@JoinColumn(name = "banco_id", updatable = true, nullable = true)
	private Banco banco;

	@ManyToOne
	@JoinColumn(name = "recompra_banco_id", updatable = true, nullable = true)
	private Banco recompraBanco;

	@ManyToOne
	@JoinColumn(name = "seguro_id", updatable = true, nullable = true)
	private Seguro seguro;

	@ManyToOne
	@JoinColumn(name = "naturezaprofissional_id", updatable = true, nullable = true)
	private NaturezaProfissional naturezaProfissional;

	@ManyToOne
	@JoinColumn(name = "modalidade_id", updatable = true, nullable = true)
	private Modalidade modalidade;

	@ManyToOne
	@JoinColumn(name = "convenio_id", updatable = true, nullable = true)
	private Convenio convenio;

	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = true)
	private Workflow workflow;

	@ManyToOne
	@JoinColumn(name = "workflowpendencia_id", updatable = true, nullable = true)
	private Workflow workflowpendencia;

	@ManyToOne
	@JoinColumn(name = "etapa_id", updatable = true, nullable = true)
	private Etapa etapa;

	@ManyToOne
	@JoinColumn(name = "etapapendencia_id", updatable = true, nullable = true)
	private Etapa etapaPendencia;
	
	@ManyToOne
	@JoinColumn(name = "supervisor_statusfinal_id", updatable = true, nullable = true)
	private Usuario supervisorStatusFinal;

	@ManyToOne
	@JoinColumn(name = "usuario_id", updatable = true, nullable = true)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "meiopagamento_id", updatable = true, nullable = true)
	private MeioPagamento meioPagamento;
	
	@ManyToOne
	@JoinColumn(name = "operacao_id", updatable = true, nullable = true)
	private Operacao operacao;

	@ManyToOne
	@JoinColumn(name = "tiposaque_id", updatable = true, nullable = true)
	private TipoSaque tipoSaque;
	
	@ManyToOne
	@JoinColumn(name = "supervisor_createdby_id", updatable = true, nullable = true)
	private Usuario supervisorCreatedBy;

	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "observacao")
	private String observacao;

	@Column(name = "prazo")
	private Integer prazo;

	@Column(name = "qtdparcelasaberto")
	private Integer qtdParcelasAberto;

	@Column(name = "valorseguro")
	private Double valorSeguro;

	@Column(name = "desconto")
	private Double desconto;

	@Column(name = "valorcontrato")
	private Double valorContrato;

	@Column(name = "valordivida")
	private Double valorDivida;

	@Column(name = "valorliquido")
	private Double valorLiquido;
	
	@Column(name = "valorContratoLiquido")
	private Double valorContratoLiquido;

	@Column(name = "valorparcela")
	private Double valorParcela;

	@Column(name = "valormeta")
	private Double valorMeta;

	@Column(name = "isactive")
	private Boolean isActive;
	
	@Column(name = "issaqueefetuado")
	private Boolean isSaqueEfetuado;
	
	@Column(name = "isrepasse")
	private Boolean isRepasse;
	
	@Column(name="percentualrepasse")
	private Double percentualRepasse;

	@Column(name = "numerobeneficio")
	private String numeroBeneficio;
	
	@Column(name="dataagendado")
	private Calendar dataAgendado;
	
	@Column(name = "valorquitacao")
	private Double valorQuitacao;
	
	@Column(name="dataquitacao")
	private Calendar dataQuitacao;
	
	@Column(name="datadigitacao")
	private Calendar dataDigitacao;
	
	@Column(name="datasolicitacaosaldo")
	private Calendar dataSolicitacaoSaldo;
	
	@Column(name="contratobanco",length=100)
	private String contratoBanco;
	
	@Column(name="propostabanco",length=100)
	private String propostaBanco;
	
	@Column(name="numeroportabilidade",length=100)
	private String numeroPortabilidade;

	@Column(name="dataconclusao")
	private Calendar dataConcluido;
	
	@Column(name="datastatusfinal")
	private Calendar dataStatusFinal;

	@Transient
	private Logistica logistica;
	
	@Transient
	private Controle controle;

	public Long getContrato_id() {
		return contrato_id;
	}

	public void setContrato_id(Long contrato_id) {
		this.contrato_id = contrato_id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public Organizacao getOrganizacaoDigitacao() {
		return organizacaoDigitacao;
	}

	public void setOrganizacaoDigitacao(Organizacao organizacaoDigitacao) {
		this.organizacaoDigitacao = organizacaoDigitacao;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Coeficiente getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(Coeficiente coeficiente) {
		this.coeficiente = coeficiente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Tabela getTabela() {
		return tabela;
	}

	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Banco getRecompraBanco() {
		return recompraBanco;
	}

	public void setRecompraBanco(Banco recompraBanco) {
		this.recompraBanco = recompraBanco;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public NaturezaProfissional getNaturezaProfissional() {
		return naturezaProfissional;
	}

	public void setNaturezaProfissional(NaturezaProfissional naturezaProfissional) {
		this.naturezaProfissional = naturezaProfissional;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Workflow getWorkflowpendencia() {
		return workflowpendencia;
	}

	public void setWorkflowpendencia(Workflow workflowpendencia) {
		this.workflowpendencia = workflowpendencia;
	}

	public Etapa getEtapaPendencia() {
		return etapaPendencia;
	}

	public void setEtapaPendencia(Etapa etapaPendencia) {
		this.etapaPendencia = etapaPendencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoSaque getTipoSaque() {
		return tipoSaque;
	}

	public void setTipoSaque(TipoSaque tipoSaque) {
		this.tipoSaque = tipoSaque;
	}

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Usuario getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Usuario updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getPrazo() {
		return prazo;
	}

	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	public Integer getQtdParcelasAberto() {
		return qtdParcelasAberto;
	}

	public void setQtdParcelasAberto(Integer qtdParcelasAberto) {
		this.qtdParcelasAberto = qtdParcelasAberto;
	}

	public Double getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(Double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public Double getDesconto() {
		return desconto == null ? 0.0 : desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getValorContrato() {
		return valorContrato == null ? 0.0 : valorContrato;
	}

	public void setValorContrato(Double valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Double getValorDivida() {
		return valorDivida == null ? 0.0 : valorDivida;
	}

	public void setValorDivida(Double valorDivida) {
		this.valorDivida = valorDivida;
	}

	public Double getValorLiquido() {
		return valorLiquido == null ? 0.0 : valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorParcela() {
		return valorParcela == null ? 0.0 : valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Double getValorMeta() {
		return valorMeta == null ? 0.0 : valorMeta;
	}

	public void setValorMeta(Double valorMeta) {
		this.valorMeta = valorMeta;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsSaqueEfetuado() {
		return isSaqueEfetuado;
	}

	public void setIsSaqueEfetuado(Boolean isSaqueEfetuado) {
		this.isSaqueEfetuado = isSaqueEfetuado;
	}

	public String getNumeroBeneficio() {
		return numeroBeneficio == null ? "" : numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio) {
		this.numeroBeneficio = numeroBeneficio;
	}

	public Calendar getDataAgendado() {
		return dataAgendado;
	}

	public void setDataAgendado(Calendar dataAgendado) {
		this.dataAgendado = dataAgendado;
	}

	public Double getValorQuitacao() {
		return valorQuitacao == null ? 0.0 : valorQuitacao;
	}

	public void setValorQuitacao(Double valorQuitacao) {
		this.valorQuitacao = valorQuitacao;
	}

	public Calendar getDataQuitacao() {
		return dataQuitacao;
	}

	public void setDataQuitacao(Calendar dataQuitacao) {
		this.dataQuitacao = dataQuitacao;
	}

	public Calendar getDataDigitacao() {
		return dataDigitacao;
	}

	public void setDataDigitacao(Calendar dataDigitacao) {
		this.dataDigitacao = dataDigitacao;
	}

	public String getContratoBanco() {
		return contratoBanco == null ? "" : contratoBanco;
	}

	public void setContratoBanco(String contratoBanco) {
		this.contratoBanco = contratoBanco;
	}

	public String getPropostaBanco() {
		return propostaBanco == null ? "" : propostaBanco;
	}

	public void setPropostaBanco(String propostaBanco) {
		this.propostaBanco = propostaBanco;
	}

	public Calendar getDataConcluido() {
		return dataConcluido;
	}

	public void setDataConcluido(Calendar dataConcluido) {
		this.dataConcluido = dataConcluido;
	}

	public Calendar getDataStatusFinal() {
		return dataStatusFinal;
	}

	public void setDataStatusFinal(Calendar dataStatusFinal) {
		this.dataStatusFinal = dataStatusFinal;
	}

	public Logistica getLogistica() {
		return logistica;
	}

	public void setLogistica(Logistica logistica) {
		this.logistica = logistica;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Boolean getIsRepasse() {
		return isRepasse;
	}

	public void setIsRepasse(Boolean isRepasse) {
		this.isRepasse = isRepasse;
	}

	public Usuario getSupervisorStatusFinal() {
		return supervisorStatusFinal;
	}

	public void setSupervisorStatusFinal(Usuario supervisorStatusFinal) {
		this.supervisorStatusFinal = supervisorStatusFinal;
	}

	public Usuario getSupervisorCreatedBy() {
		return supervisorCreatedBy;
	}

	public void setSupervisorCreatedBy(Usuario supervisorCreatedBy) {
		this.supervisorCreatedBy = supervisorCreatedBy;
	}

	public Double getValorContratoLiquido() {
		return valorContratoLiquido == null ? 0.0 : valorContratoLiquido;
	}

	public void setValorContratoLiquido(Double valorContratoLiquido) {
		this.valorContratoLiquido = valorContratoLiquido;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public Controle getControle() {
		return controle;
	}

	public void setControle(Controle controle) {
		this.controle = controle;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Double getPercentualRepasse() {
		return percentualRepasse == null ? 0.0 : percentualRepasse;
	}

	public void setPercentualRepasse(Double percentualRepasse) {
		this.percentualRepasse = percentualRepasse;
	}

	public Calendar getDataSolicitacaoSaldo() {
		return dataSolicitacaoSaldo;
	}

	public void setDataSolicitacaoSaldo(Calendar dataSolicitacaoSaldo) {
		this.dataSolicitacaoSaldo = dataSolicitacaoSaldo;
	}

	public String getNumeroPortabilidade() {
		return numeroPortabilidade == null ? "" : numeroPortabilidade;
	}

	public void setNumeroPortabilidade(String numeroPortabilidade) {
		this.numeroPortabilidade = numeroPortabilidade;
	}
}
