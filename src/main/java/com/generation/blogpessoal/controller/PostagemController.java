package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController // reponde todas as requisições referente a postagens
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") // permite requisções de outras origens (ex. nuvem)
public class PostagemController {

	@Autowired // injeção de dependencia (criando objeto que vem de postagemRepository)
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

	// CRUD (criar, ler, deletar e atualizar)
	@GetMapping // metodo de consulta
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
		// SELECT * FROM tb_postagens;
	}

	@GetMapping("/{id}") // metodo de consulta da chave primária
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		// No SQL: SELECT * FROM tb_postagens WHERE id = ?;
	}

	@GetMapping("/titulo/{titulo}") // metodo de consulta do titulo
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		// No SQL: SELECT * FROM tb_postagens WHERE titulo LIKE "%titulo%";
	}

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		return temaRepository.findById(postagem.getTema().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem))) 
			      .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());	
		// No SQL: INSERT INTO tb_postagens(data, titulo, texto) VALUES(?, ?, ?
	}

	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		return temaRepository.findById(postagem.getId())
				.map(resposta -> ResponseEntity.ok().body(postagemRepository.save(postagem)))
				.orElse(ResponseEntity.notFound().build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);
		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		postagemRepository.deleteById(id);
		// No SQL: DELETE FROM tb_postagens WHERE id = id
	}
}
