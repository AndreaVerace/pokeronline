package it.prova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/playManagement")
public class PlayManagementController {

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private TavoloService tavoloService;
	
	@PostMapping("/compraCredito/{credito}")
	public UtenteDTO compraCredito(@PathVariable(required = true) int credito) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		utenteLoggato.setCreditoAccumulato(utenteLoggato.getCreditoAccumulato() + credito);
		utenteService.aggiorna(utenteLoggato);
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteLoggato);
	}
	
	
	@GetMapping("/lastGame")
	public TavoloDTO restituisciLastGame() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		for(Tavolo tavoloItem : tavoloService.listAllTavoli()) {
			if(tavoloItem.getUtentiTavolo().contains(utenteLoggato)) {
				return TavoloDTO.buildTavoloDTOFromModel(tavoloItem);
			} 
		}
		return null;
	}
	
	
	@GetMapping("/abbandona")
	public String abbandona() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		for(Tavolo tavoloItem : tavoloService.listAllTavoli()) {
			if(tavoloItem.getUtentiTavolo().contains(utenteLoggato)) {
				tavoloItem.getUtentiTavolo().remove(utenteLoggato);
				return "Hai abbandonato la partita.";
			}
		}
		return "Non sei presente in nessuna partita.";
	}
	
	@GetMapping("/ricercaPartite")
	public List<TavoloDTO> ricercaPartite(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findAllByEsperienzaMinimaLessThanEqual(utenteLoggato.getEsperienzaAccumulata()));
		
	}
	
	@GetMapping("/giocaPartita")
	public UtenteDTO giocaPartita() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato =  utenteService.findByUsername(auth.getName());
		int creditoTotale = utenteLoggato.getCreditoAccumulato();
		
		double segno = Math.random();
		
		if(segno >= 0.5) {
			utenteLoggato.setCreditoAccumulato(creditoTotale + (int)(segno*1000));
		}
		else {
			utenteLoggato.setCreditoAccumulato(creditoTotale - (int)(segno * 1000));
			if(creditoTotale < 0)
				utenteLoggato.setCreditoAccumulato(0);
		}
		
		
		utenteService.aggiorna(utenteLoggato);
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteLoggato);
	}
	
}
