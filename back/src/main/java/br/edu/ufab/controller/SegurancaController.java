package br.edu.ufab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SegurancaController {

	@GetMapping("/")
	public String home() {
		return "Home Page";
	}
}
	