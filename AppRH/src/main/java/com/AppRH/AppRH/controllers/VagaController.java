package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;
import com.AppRH.AppRH.repository.CandidatoRepository;
import com.AppRH.AppRH.repository.VagaRepository;

import jakarta.validation.Valid;

@Controller
public class VagaController {

	@Autowired
	private VagaRepository vr;

	@Autowired
	private CandidatoRepository cr;

	// CADASTRAR VAGAS
	@GetMapping("/cadastrarVaga")
	public String form() {

		return "vaga/form-vaga";
	}

	@PostMapping("/cadastrarVaga")
	public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {

			attributes.addFlashAttribute("mensagem", "Verifique os campos...;");
			return "redirect:/cadastrarVaga";
		}

		vr.save(vaga);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso");
		return "redirect:/cadastrarVaga";
	}

	// LISTAR VAGAS
	@GetMapping("/vagas")
	public ModelAndView listaVagas() {

		ModelAndView mv = new ModelAndView("vaga/lista-vaga");
		Iterable<Vaga> vagas = vr.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}

	//
	@GetMapping("/vaga/{codigo}")
	public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {

		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/detalhes-vaga");
		mv.addObject("vaga", vaga);

		Iterable<Candidato> candidato = cr.findByVaga(vaga);
		mv.addObject("candidato", candidato);
		return mv;

	}

	// DELETAR VAGAS
	@GetMapping("/deletarVaga")
	public String deletarVaga(long codigo) {

		Vaga vaga = vr.findByCodigo(codigo);
		vr.delete(vaga);
		return "redirect:/vagas";
	}

	// ADICIONAR CANDIDATO
	@PostMapping("/vaga/{codigo}")
	public String detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
			BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/vaga/{codigo}";
		}

		// RG DUPLICADO

		if (cr.findByRg(candidato.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
			return "redirect:/vaga/{codigo}";
		}

		Vaga vaga = vr.findByCodigo(codigo);
		candidato.setVaga(vaga);
		cr.save(candidato);
		attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
		return "redirect:/vaga/{codigo}";
	}

	// DELETA CANDIDATOS PELO RG
	@GetMapping("/deletarCandidato")
	public String deletarCandidato(String rg) {

		Candidato candidato = cr.findByRg(rg);
		Vaga vaga = candidato.getVaga();
		String codigo = "" + vaga.getCodigo();

		cr.delete(candidato);
		return "redirect:/vaga/" + codigo;

	}

	// MÉTODOS QUE ATUALIZAM AS VAGAS
	// FORMULÁRIO DE EDIÇÃO DE VAGAS
	@GetMapping("/editar-vaga")
	public ModelAndView editarVaga(long codigo) {

		Vaga vaga = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vaga", vaga);
		return mv;

	}

	// UPDATE DA VAGA
	@PostMapping("/editar-vaga")
	public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

		vr.save(vaga);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long codigoLong = vaga.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/vaga/" + codigo;
	}

}
