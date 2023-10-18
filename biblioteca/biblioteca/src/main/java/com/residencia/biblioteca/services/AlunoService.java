package com.residencia.biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.biblioteca.dto.AlunoResumidoDTO;
import com.residencia.biblioteca.entities.Aluno;
import com.residencia.biblioteca.repositories.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired // cria uma injeção de dependência
	AlunoRepository alunoRepo;
	
	public List <Aluno> listarAlunos(){
		return alunoRepo.findAll();
	}
	
	public Aluno buscarAlunoPorId(Integer id) {
		
		return alunoRepo.findById(id).orElse(null); 
	}
	
	public AlunoResumidoDTO getAlunoResumidoPorId(Integer id) {
		
		Aluno aluno =  alunoRepo.findById(id).orElse(null);
		AlunoResumidoDTO alunoResDTO = new AlunoResumidoDTO();
		
		alunoResDTO.setNumeroMatriculaAluno(id);
		alunoResDTO.setNome(aluno.getNome());
		alunoResDTO.setCpf(aluno.getCpf());
		
		return alunoResDTO;
		
		
	}
	
	public Aluno salvarAluno(Aluno aluno) {
		return alunoRepo.save(aluno);
	}
	
	public Aluno atualizarAluno(Aluno aluno) {
		return alunoRepo.save(aluno);
	}
	
	public Boolean deletarAluno(Aluno aluno) {
		if (aluno == null)
			return false;	
		
		Aluno alunoExistente = buscarAlunoPorId(aluno.getNumeroMatriculaAluno());
		if (alunoExistente == null)
			return false;
				
		alunoRepo.delete(aluno);
		
		Aluno alunoContinuaExistindo = buscarAlunoPorId(aluno.getNumeroMatriculaAluno());
		
		if(alunoContinuaExistindo == null)
			return true;
		
		return false;
		//Aluno confereAlunoDeletado = buscarAlunoPorId(aluno.getNumeroMatriculaAluno());
	}
}
