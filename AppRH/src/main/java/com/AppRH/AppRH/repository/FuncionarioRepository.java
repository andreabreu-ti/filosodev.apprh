package com.AppRH.AppRH.repository;

import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

	Funcionario findById(long id);

	// BUsca
	Funcionario findByNome(String nome);

}
