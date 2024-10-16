package com.AppRH.AppRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
}
