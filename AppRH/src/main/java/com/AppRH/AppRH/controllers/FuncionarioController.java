package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Dependente;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.repository.DependentesRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;

import jakarta.validation.Valid;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependentesRepository dr;

	// CHAMA O FORM DE CADASTRAR FUNCIONARIOS
	@GetMapping("/cadastrarFuncionario")
	public String form() {

		return "funcionario/form-funcionario";

	}

	// CADASTRA FUNCIONARIOS
	@PostMapping("/cadastrarFuncionario")
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}

		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Funcionario Cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";

	}

	// LISTAR FUNCIONÁRIOS
	@GetMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// LISTAR DEPENDENTES
	@GetMapping("/detalhes-funcionario/{id}")
	public ModelAndView detalhesFuncionario(@PathVariable("id") long id) {

		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-funcionario");
		mv.addObject("funcionarios", funcionario);

		// LISTA DE DEPENDENTES BASEADA NO FUNCINARIO
		Iterable<Dependente> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes", dependentes);
		return mv;

	}

	// ADICIONAR DEPENDENTES
	@PostMapping("/detalhes-funcionario/{id}")
	public String detalhesFuncionarioPost(@PathVariable("id") long id, Dependente dependentes, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhes-funcionario/{id}";
		}

		if (dr.findByCpf(dependentes.getCpf()) != null) {
			System.out.println("CPF duplicado");
			attributes.addFlashAttribute("mensagem_erro", "CPF Duplicado!");
			return "redirect:/detalhes-funcionario/{id}";
		}

		Funcionario funcionario = fr.findById(id);
		dependentes.setFuncionario(funcionario);
		dr.save(dependentes);

		System.out
				.println("Dependente salvo com sucesso: " + dependentes.getNome() + ", Data: " + dependentes.getData());

		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso!");
		return "redirect:/detalhes-funcionario/{id}";

	}

	// DELETA FUNCIONARIO
	@GetMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {

		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		return "redirect:/funcionarios";
	}

	// ATUALIZAR FUNCIONARIOS
	@GetMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long id) {

		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;

	}

	// UPDATE FUNCIONARIO
	@PostMapping("/editar-funcionario")
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
			RedirectAttributes attributes) {

		fr.save(funcionario);
		attributes.addFlashAttribute("success", "Funcionário adiciondo com sucesso");

		long idLong = funcionario.getId();
		String id = "" + idLong;
		return "redirect:/detalhes-funcionario/" + id;

	}

	// DELETAR DEPENDENTES
	@GetMapping("/deletarDependente")
	public String deletarDependente(String cpf) {

		Dependente dependente = dr.findByCpf(cpf);

		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();

		dr.delete(dependente);
		return "redirect:/detalhes-funcionario/" + codigo;

	}

}