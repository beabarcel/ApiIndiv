package com.residencia.biblioteca.services;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.residencia.biblioteca.dto.EditoraDTO;
import com.residencia.biblioteca.entities.Editora;
import com.residencia.biblioteca.repositories.EditoraRepository;

@Service
public class EditoraService {

	@Autowired
	EditoraRepository editoraRepo;

	private ModelMapper modelMapper = new ModelMapper();

	public List<Editora> listarEditora() {
		return editoraRepo.findAll();
	}

	public Editora buscarEditoraPorId(Integer id) {
		return editoraRepo.findById(id).orElse(null);
	}

	public Editora salvarEditora(Editora editora) {
		return editoraRepo.save(editora);
	}
	
	public EditoraDTO salvarEditoraDTO(EditoraDTO editoraDto) {
        Editora editora = convertToEntity(editoraDto);
        return convertToDto(editoraRepo.save(editora));

    }

	private EditoraDTO convertToDto(Editora editora) {
		EditoraDTO editoraDto = modelMapper.map(editora, EditoraDTO.class);
		return editoraDto;
	}

	private Editora convertToEntity(EditoraDTO editoraDto) {
		Editora editora = modelMapper.map(editoraDto, Editora.class);
		return editora;

	}
	
	public EditoraDTO getEditoraResumidoPorId(Integer id) {
	    Editora editora = editoraRepo.findById(id).orElse(null);
	    if (editora != null) {
	        return convertToDto(editora);
	    }
	    return null;
	}

	public Editora atualizarEditora(Editora editora) {
		return editoraRepo.save(editora);
	}

	public Boolean deletarEditora(Editora editora) {
		if (editora == null)
			return false;

		Editora editoraExistente = buscarEditoraPorId(editora.getCodigoEditora());
		if (editoraExistente == null)
			return false;

		editoraRepo.delete(editora);

		Editora editoraContinuaExistindo = buscarEditoraPorId(editora.getCodigoEditora());

		if (editoraContinuaExistindo == null)
			return true;

		return false;
	}
}
