package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Dependentes;
import com.AppRH.AppRH.models.Funcionario;

public interface DependentesRepository extends CrudRepository<Dependentes, Long> {

	Iterable<Dependentes> findByFuncionario(Funcionario funcionario);

	// PENSANDO NO MÉTODO DELETE
	Dependentes findByCpf(String cpf);

	Dependentes findById(long id);

	// CRIADO PARA IMPLEMENTAR
	List<Dependentes> findByNome(String nome);

	// PARA BUSCA
	@Query(value = "select u from Dependentes u where u.nome like %?1%")
	List<Dependentes> findByNomesDependentes(String nome);

}
