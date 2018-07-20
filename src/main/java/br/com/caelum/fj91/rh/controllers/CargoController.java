package br.com.caelum.fj91.rh.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro.ValidadorCadastroCargo;
import br.com.caelum.fj91.rh.domain.validacao.cargo.exclusao.ValidadorExclusaoCargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	private final CargoRepository repository;
	private final List<ValidadorCadastroCargo> validadoresCadastro;
	private final List<ValidadorExclusaoCargo> validadoresExclusao;
	
	public CargoController(CargoRepository repository, List<ValidadorCadastroCargo> validadoresCadastro, List<ValidadorExclusaoCargo> validadoresExclusao) {
		this.repository = repository;
		this.validadoresCadastro = validadoresCadastro;
		this.validadoresExclusao = validadoresExclusao;
	}

	@GetMapping
	public String lista(Model model) {
		List<Cargo> todos = repository.findAll();
		model.addAttribute("cargos", todos);
		return "cargo/lista";
	}
	
	@GetMapping("/form")
	public String formulario() {
		return "cargo/form";
	}
	
	@PostMapping
	public String salvar(Cargo novo, Model model, RedirectAttributes attributes) {
		try {
			validadoresCadastro.stream().forEach(v -> v.valida(novo));
			this.repository.save(novo);
			attributes.addFlashAttribute("msgSucesso", "Cargo cadastrado!");
			return "redirect:/cargos";
		} catch (BusinessException e) {
			model.addAttribute("msgErro", e.getMessage());
			return "cargo/form";
		}
	}
	
	@DeleteMapping
	public String excluir(Long id, RedirectAttributes attributes) {
		try {
			Cargo selecionado = repository.getOne(id);
			validadoresExclusao.stream().forEach(v -> v.valida(selecionado));
			repository.delete(selecionado);
			attributes.addFlashAttribute("msgSucesso", "Cargo excluido!");
		} catch (BusinessException e) {
			attributes.addFlashAttribute("msgErro", e.getMessage());
		}
		
		return "redirect:/cargos";
	}

}
