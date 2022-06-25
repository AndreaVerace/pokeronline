package it.prova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.web.api.exception.IdNotNullForInsertException;
import it.prova.pokeronline.web.api.exception.TavoloNotFoundException;
import it.prova.pokeronline.web.api.exception.UtenteNotFoundException;

@RestController
@RequestMapping("/api/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public List<TavoloDTO> getAll() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		if(utenteLoggato.isSpecialPlayer()) {
			return TavoloDTO.createTavoloDTOListFromModelList
					(tavoloService.findtavoloDiUtente(Ruolo.ROLE_SPECIAL_PLAYER, utenteLoggato.getUsername()));
		}
		else {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoli());
		}
		
	}
		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
			//se mi viene inviato un id jpa lo interpreta come update ed a me (producer) non sta bene
			if(tavoloInput.getId() != null)
				throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
			
			Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel(true));
			return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito);
		}
	
		@PutMapping("/{id}")
		public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {
			Tavolo tavolo = tavoloService.caricaSingoloTavolo(id);

			if (tavolo == null)
				throw new TavoloNotFoundException("Tavolo not found con id: " + id);

			tavoloInput.setId(id);
			Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildTavoloModel(true));
			return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato);
		}
		
		@PostMapping("/search")
		public List<TavoloDTO> search(@RequestBody TavoloDTO example) {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(example.buildTavoloModel(true)));
		}
	
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.OK)
		public void delete(@PathVariable(required = true) Long id) {
			Tavolo tavolo = tavoloService.caricaSingoloTavolo(id);

			if (tavolo == null)
				throw new TavoloNotFoundException("tavolo not found con id: " + id);

			tavoloService.rimuovi(tavolo);
		}
		
}
