package it.prova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/playManagement")
public class PlayManagementController {

	@Autowired
	private UtenteService utenteService;
	
	@PostMapping("/compraCredito/{credito}")
	public UtenteDTO compraCredito(@PathVariable(required = true) int credito) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		utenteLoggato.setCreditoAccumulato(utenteLoggato.getCreditoAccumulato() + credito);
		utenteService.aggiorna(utenteLoggato);
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteLoggato);
	}
	
	
}
