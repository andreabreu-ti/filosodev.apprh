package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.GET)
	public String form() {

		return "funcionario/formFuncionario";

	}

	// CADASTRA FUNCIONARIOS
	@RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
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
	@RequestMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// LISTAR DEPENDENTES
	@RequestMapping(value = "/dependentes/{id}", method = RequestMethod.GET)
	public ModelAndView dependentes(@PathVariable("id") long id) {

		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/dependentes");
		mv.addObject("funcionarios", funcionario);

		// LISTA DE DEPENDENTES BASEADA NO FUNCINARIO
		Iterable<Dependente> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes", dependentes);
		return mv;

	}

	// ADICIONAR DEPENDENTES
	@RequestMapping(value = "/dependentes/{id}", method = RequestMethod.POST)
	public String dependentesPost(@PathVariable("id") long id, Dependente dependentes, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/dependentes/{id}";
		}

		if (dr.findByCpf(dependentes.getCpf()) != null) {
			System.out.println("CPF duplicado");
			attributes.addFlashAttribute("mensagem_erro", "CPF Duplicado!");
			return "redirect:/dependentes/{id}";
		}

		Funcionario funcionario = fr.findById(id);
		dependentes.setFuncionario(funcionario);
		dr.save(dependentes);
		
		System.out.println("Dependente salvo com sucesso: " + dependentes.getNome() + ", Data: " + dependentes.getData());
		
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso!");
		return "redirect:/dependentes/{id}";

	}

	// DELETA FUNCIONARIO
	@RequestMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {

		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		return "redirect:/funcionarios";
	}

	// ATUALIZAR FUNCIONARIOS
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.GET)
	public ModelAndView editarFuncionario(long id) {

		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;

	}

	// UPDATE FUNCIONARIO
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
			RedirectAttributes attributes) {

		fr.save(funcionario);
		attributes.addFlashAttribute("success", "Funcionário adiciondo com sucesso");

		long idLong = funcionario.getId();
		String id = "" + idLong;
		return "redirect:/dependentes/" + id;

	}

	// DELETAR DEPENDENTES
	@RequestMapping(value = "/deletarDependente")
	public String deletarDependente(String cpf) {

		Dependente dependente = dr.findByCpf(cpf);

		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();

		dr.delete(dependente);
		return "redirect:/dependentes/" + codigo;

	}

}