package br.com.caelum.fj91.rh.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.Reajuste;
import br.com.caelum.fj91.rh.domain.validacao.reajuste.ValidadorReajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;
import br.com.caelum.fj91.rh.repositories.ReajusteRepository;
import br.com.caelum.fj91.rh.services.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	private final CargoRepository cargoRepository;
	private final ReajusteRepository reajusteRepository;
	private final FuncionarioService funcionarioService;
	private final List<ValidadorReajuste> validadoresReajuste;
	
	public FuncionarioController(CargoRepository cargoRepository, ReajusteRepository reajusteRepository, FuncionarioService funcionarioService, List<ValidadorReajuste> validadoresReajuste) {
		this.cargoRepository = cargoRepository;
		this.reajusteRepository = reajusteRepository;
		this.funcionarioService = funcionarioService;
		this.validadoresReajuste = validadoresReajuste;
	}

	@GetMapping
	public String lista(Model model) {
		List<Funcionario> todos = funcionarioService.listarTodos();
		model.addAttribute("funcionarios", todos);
		return "funcionario/lista";
	}
	
	@GetMapping("/form")
	public String formulario(Model model) {
		model.addAttribute("cargos", cargoRepository.findAll());
		return "funcionario/form";
	}
	
	@PostMapping
	public String salvar(Funcionario novo, Model model, RedirectAttributes attributes) {
		try {
			funcionarioService.salvar(novo);
			attributes.addFlashAttribute("msgSucesso", "Funcionario cadastrado!");
			return "redirect:/funcionarios";
		} catch (BusinessException e) {
			model.addAttribute("msgErro", e.getMessage());
			return formulario(model);
		}
	}
	
	@DeleteMapping
	public String excluir(Long id, RedirectAttributes attributes) {
		funcionarioService.excluir(id);
		attributes.addFlashAttribute("msgSucesso", "Funcionario excluido!");
		return "redirect:/funcionarios";
	}
	
	@GetMapping("/{id}/reajustes")
	public String reajustes(@PathVariable("id") Long id, Model model) {
		Funcionario selecionado = funcionarioService.buscarPorId(id);
		List<Reajuste> reajustesConcedidos = reajusteRepository.findAllByFuncionario(selecionado);
		model.addAttribute("funcionario", selecionado);
		model.addAttribute("reajustes", reajustesConcedidos);
		return "funcionario/reajuste";
	}
	
	@PostMapping("/{id}/reajustes")
	public String reajustar(@PathVariable("id") Long id, Reajuste novo, Model model, RedirectAttributes attributes) {
		Funcionario selecionado = funcionarioService.buscarPorId(id);
		novo.setFuncionario(selecionado);
		
		try {
			validadoresReajuste.forEach(v -> v.valida(novo));
			reajusteRepository.save(novo);
			attributes.addFlashAttribute("msgSucesso", "Reajuste cadastrado!");
			return "redirect:/funcionarios";
		} catch (BusinessException e) {
			model.addAttribute("msgErro", e.getMessage());
			return reajustes(id, model);
		}
	}

}
