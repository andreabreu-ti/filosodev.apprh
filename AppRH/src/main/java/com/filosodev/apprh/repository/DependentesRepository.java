package com.filosodev.apprh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.filosodev.apprh.models.Dependente;
import com.filosodev.apprh.models.Funcionario;

public interface DependentesRepository extends CrudRepository<Dependente, Long> {

	Iterable<Dependente> findByFuncionario(Funcionario funcionario);

	// para o método delete por CPF
	Dependente findByCpf(String cpf);
	
	Dependente findById(long id);
	List<Dependente> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Dependente u where u.nome like %?1%")
	List<Dependente> findByNomesDependentes(String nome);
}
