package com.residencia.biblioteca.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.biblioteca.dto.EditoraDTO;
import com.residencia.biblioteca.dto.ReceitaWsDTO;
import com.residencia.biblioteca.entities.Editora;
import com.residencia.biblioteca.services.EditoraService;



@RestController
@RequestMapping("/editoras")


public class EditoraController {
	@Autowired
	EditoraService editoraService;
	
	@GetMapping
	public ResponseEntity <List <Editora>> listarEditoras(){
		return new ResponseEntity<> (editoraService.listarEditora(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Editora> buscarEditoraPorId(@PathVariable Integer id) {
	Editora editora = editoraService.buscarEditoraPorId(id);
		return new ResponseEntity<>(editora, HttpStatus.OK);
		
		/*if(editora == null)
		return new  ResponseEntity<>(editora, HttpStatus.NOT_FOUND);
	
		else
			return new ResponseEntity<>(editora, HttpStatus.OK); */
	}
	
	@GetMapping("/resumido/{id}")
	public ResponseEntity<EditoraDTO> getEditoraResumidoPorId(@PathVariable Integer id) {
	    EditoraDTO editoraDto = editoraService.getEditoraResumidoPorId(id);

	    if (editoraDto != null) {
	        return new ResponseEntity<>(editoraDto, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	@GetMapping("/consulta-cnpj/{cnpj}")
	public ResponseEntity<ReceitaWsDTO> consultaCnpj(@PathVariable String cnpj) {
		
		return new ResponseEntity<> (editoraService.consultaCnpj(cnpj), HttpStatus.OK);
		
	    }
	
	@PostMapping
	public ResponseEntity <Editora> salvarEditora(@RequestBody Editora editora){
		return new ResponseEntity<> (editoraService.salvarEditora(editora), HttpStatus.CREATED);
	}
	
	@PostMapping("/resumido/{id}")
	public ResponseEntity <EditoraDTO> salvarEditoraDTO(@RequestBody EditoraDTO editoraDto){
		return new ResponseEntity<> (editoraService.salvarEditoraDTO(editoraDto), HttpStatus.CREATED);
	}
	
	@PostMapping("/com-foto")
	public ResponseEntity<Editora> salvarComFoto(@RequestPart("edt") String strEditora, @RequestPart("img") MultipartFile arqImg ) throws IOException {
		
		return new ResponseEntity<>(editoraService.salvarComFoto(strEditora, arqImg), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity <Editora> atualizarEditora(@RequestBody Editora editora){
		return new ResponseEntity<> (editoraService.atualizarEditora(editora), HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deletarEditora(@RequestBody Editora editora) {
		
		if(editoraService.deletarEditora(editora))
			return new ResponseEntity <> ("Editora deletada", HttpStatus.OK);
		
		else 
			return new ResponseEntity <> ("Não foi possível deletar a editora.", HttpStatus.BAD_REQUEST);
	}
}
