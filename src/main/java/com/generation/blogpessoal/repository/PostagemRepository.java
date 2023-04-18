package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;

                                      //parametro (Tipo: postagem) (ID: long)
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	//definindo método de consulta:
	//List= consulta / Colletciton= é a lista que armazena os objetos (postagens) / Containig= busca objeto de  um atributo especifico (ex: titulo) / Like= findAll (busca todos os objetos) / Ignorecase= ignorar letras minusculas ou maiusculas
	  List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String Titulo);
	  
	    //no SQL: SELECT * FROM tb_postagens WHERE titulo LIKE "texto que quero encontrar";
	
}
