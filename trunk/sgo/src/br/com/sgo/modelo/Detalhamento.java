package br.com.sgo.modelo;

import java.util.Date;
import java.util.Map;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class Detalhamento {

	private Long id_extrato;
	private String matricula;
	private String competencia;
	private String nome;
	private String especie;
	private String descespecie;
	private Date periodoinicial;
	private Date periodofinal;
	private Date dispinicial;
	private Date dispfinal;
	private String contacorrente;
	private String banco;
	private String codbanco;
	private String agencia;
	private String enderecobanco;
	private String meio_pgtodesc;
	private String status;
	private Map<String, Double> debitos;
	private Map<String, Double> creditos;
	private Double valorBruto;
	private Double valorDescontos;
	private Double valorLiquido;

	public Long getId_extrato() {
		return id_extrato;
	}

	public void setId_extrato(Long id_extrato) {
		this.id_extrato = id_extrato;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getDescespecie() {
		return descespecie;
	}

	public void setDescespecie(String descespecie) {
		this.descespecie = descespecie;
	}

	public String getContacorrente() {
		return contacorrente;
	}

	public void setContacorrente(String contacorrente) {
		this.contacorrente = contacorrente;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getCodbanco() {
		return codbanco;
	}

	public void setCodbanco(String codbanco) {
		this.codbanco = codbanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getEnderecobanco() {
		return enderecobanco;
	}

	public void setEnderecobanco(String enderecobanco) {
		this.enderecobanco = enderecobanco;
	}

	public String getMeio_pgtodesc() {
		return meio_pgtodesc;
	}

	public void setMeio_pgtodesc(String meio_pgtodesc) {
		this.meio_pgtodesc = meio_pgtodesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Double> getDebitos() {
		return debitos;
	}

	public void setDebitos(Map<String, Double> debitos) {
		this.debitos = debitos;
	}

	public Map<String, Double> getCreditos() {
		return creditos;
	}

	public void setCreditos(Map<String, Double> creditos) {
		this.creditos = creditos;
	}

	public Double getValorBruto() {

		valorBruto = 0.0;

		for (Double d : this.creditos.values()) {
			valorBruto += d;
		}

		return valorBruto;

	}

	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Double getValorDescontos() {

		valorDescontos = 0.0;

		for (Double d : this.debitos.values()) {
			valorDescontos += d;
		}

		return valorDescontos;

	}

	public void setValorDescontos(Double valorDescontos) {
		this.valorDescontos = valorDescontos;
	}

	public Double getValorLiquido() {

		valorLiquido = valorBruto - valorDescontos;

		return valorLiquido;

	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Date getPeriodoinicial() {
		return periodoinicial;
	}

	public void setPeriodoinicial(Date periodoinicial) {
		this.periodoinicial = periodoinicial;
	}

	public Date getPeriodofinal() {
		return periodofinal;
	}

	public void setPeriodofinal(Date periodofinal) {
		this.periodofinal = periodofinal;
	}

	public Date getDispinicial() {
		return dispinicial;
	}

	public void setDispinicial(Date dispinicial) {
		this.dispinicial = dispinicial;
	}

	public Date getDispfinal() {
		return dispfinal;
	}

	public void setDispfinal(Date dispfinal) {
		this.dispfinal = dispfinal;
	}

}
