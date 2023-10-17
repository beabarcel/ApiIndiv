package com.residencia.biblioteca.dto;

public class AlunoResumidoDTO {

	private Integer numeroMatriculaAluno;
	private String nome;
	private String cpf;
	
	public Integer getNumeroMatriculaAluno() {
		return numeroMatriculaAluno;
	}
	
	public void setNumeroMatriculaAluno(Integer numeroMatriculaAluno) {
		this.numeroMatriculaAluno = numeroMatriculaAluno;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
