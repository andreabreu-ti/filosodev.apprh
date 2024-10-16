package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.AppRH.AppRH.repository.CandidatoRepository;
import com.AppRH.AppRH.repository.DependentesRepository;
import com.AppRH.AppRH.repository.FuncionarioRepository;
import com.AppRH.AppRH.repository.VagaRepository;

@Controller
public class BuscaController {

	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private VagaRepository vr;

	@Autowired
	private DependentesRepository dr;

	@Autowired
	private CandidatoRepository cr;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView abrirIndex() {

		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	// POST
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {

		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultados da busca por " + buscar;

		if (nome.equals("nomeFuncionario")) {

			mv.addObject("funconarios", fr.findByNomes(buscar));

		} else if (nome.equals("nomeDependente")) {

			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));

		} else if (nome.equals("nomeCandidato")) {

			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));

		} else if (nome.equals("tituloVaga")) {

			mv.addObject("vagas", vr.findByNomesVaga(buscar));

		} else {

			mv.addObject("funconarios", fr.findByNomes(buscar));
			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));
			mv.addObject("vagas", vr.findByNomesVaga(buscar));

		}

		return mv;
	}

}
