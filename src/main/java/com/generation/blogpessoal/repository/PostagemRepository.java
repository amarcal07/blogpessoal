package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;

<<<<<<< HEAD
=======
@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
>>>>>>> 0609cded9ecba5a8f824016c8f94fc2fda256d6a

public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	//consulta,colletciton lista armazena postagem, containig = like %f%, ignorecase= ignorar letras minusculas ou maiusculas
	  List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String Titulo);
	    //no SQL: SELECT * FROM tb_postagens WHERE titulo LIKE "texto que quero encontrar";
	
}
