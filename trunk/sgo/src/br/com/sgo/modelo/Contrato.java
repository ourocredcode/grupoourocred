package br.com.sgo.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@JoinColumn(name = "formulario_id", updatable = true, nullable = false)
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
	@JoinColumn(name = "seguro_id", updatable = true, nullable = true)
	private Seguro seguro;

	@ManyToOne
	@JoinColumn(name = "naturezaprofissional_id", updatable = true, nullable = true)
	private NaturezaProfissional naturezaProfissional;

	@ManyToOne
	@JoinColumn(name = "recompra_banco_id", updatable = true, nullable = true)
	private Banco recompraBanco;

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
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = true)
	private WorkflowEtapa workflowEtapa;

	@ManyToOne
	@JoinColumn(name = "usuario_id", updatable = true, nullable = true)
	private Usuario usuario;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

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

	@Column(name = "valorparcela")
	private Double valorParcela;

	@Column(name = "valormeta")
	private Double valorMeta;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "numerobeneficio")
	private String numeroBeneficio;

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

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public NaturezaProfissional getNaturezaProfissional() {
		return naturezaProfissional;
	}

	public void setNaturezaProfissional(
			NaturezaProfissional naturezaProfissional) {
		this.naturezaProfissional = naturezaProfissional;
	}

	public Banco getRecompraBanco() {
		return recompraBanco;
	}

	public void setRecompraBanco(Banco recompraBanco) {
		this.recompraBanco = recompraBanco;
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

	public WorkflowEtapa getWorkflowEtapa() {
		return workflowEtapa;
	}

	public void setWorkflowEtapa(WorkflowEtapa workflowEtapa) {
		this.workflowEtapa = workflowEtapa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(Double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(Double valorContrato) {
		this.valorContrato = valorContrato;
	}

	public Double getValorDivida() {
		return valorDivida;
	}

	public void setValorDivida(Double valorDivida) {
		this.valorDivida = valorDivida;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Double getValorMeta() {
		return valorMeta;
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

	public String getNumeroBeneficio() {
		return numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio) {
		this.numeroBeneficio = numeroBeneficio;
	}
}
