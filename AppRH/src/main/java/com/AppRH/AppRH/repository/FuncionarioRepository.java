package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

	Funcionario findById(long id);

	// BUSCA
	Funcionario findByNome(String nome);
	
	//PARA BUSCA
	@Query(value = "select u from Funcionario u where u.nome like %?1%")
	List<Funcionario>findByNomes(String nome);

}
