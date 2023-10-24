package com.residencia.biblioteca.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.biblioteca.dto.EditoraDTO;
import com.residencia.biblioteca.dto.ReceitaWsDTO;
import com.residencia.biblioteca.entities.Editora;
import com.residencia.biblioteca.exceptions.NoSuchElementException;
import com.residencia.biblioteca.repositories.EditoraRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class EditoraService {

	@Autowired
	EditoraRepository editoraRepo;

	private ModelMapper modelMapper = new ModelMapper();

	public List<Editora> listarEditora() {
		return editoraRepo.findAll();
	}

	public Editora buscarEditoraPorId(Integer id) {
		//return editoraRepo.findById(id).orElse(null);
		
		return editoraRepo.findById(id)
		        .orElseThrow(() -> new NoSuchElementException("Editora", id));
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

	public ReceitaWsDTO consultaCnpj(String cnpj) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://receitaws.com.br/v1/cnpj/{cnpj}";

		Map<String, String> params = new HashMap<String, String>();

		params.put("cnpj", cnpj);

		ReceitaWsDTO receitaDto = restTemplate.getForObject(uri, ReceitaWsDTO.class, params);

		return receitaDto;
	}

	public Editora salvarComFoto(String strEditora, MultipartFile arqImg)
			throws java.io.IOException {
		Editora editora = new Editora();

		try {
			ObjectMapper objMap = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			editora = objMap.readValue(strEditora, Editora.class);
		} catch (IOException e) {
			System.out.print("Erro ao converter a string Editora: " + e.toString());
		}

		editora.setImagemFileName(arqImg.getBytes());

		return editoraRepo.save(editora);
	}
}
