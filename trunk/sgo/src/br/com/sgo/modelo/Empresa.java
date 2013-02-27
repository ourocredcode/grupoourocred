package br.com.sgo.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;

@Entity
@Component
@Table(name="EMPRESA")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "empresa_id")  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long empresa_id;

	@Column(name="nome")
	private String nome;

	@Column(name="descricao")
	private String descricao;

	public Long getEmpresa_id() {
		return empresa_id;
	}
	public void setEmpresa_id(Long empresa_id) {
		this.empresa_id = empresa_id;
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
}
