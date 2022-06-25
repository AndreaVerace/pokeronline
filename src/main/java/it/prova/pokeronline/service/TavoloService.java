package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {

	public List<Tavolo> listAllTavoli();
	
	public Tavolo caricaSingoloTavolo(Long id);
	
	public Tavolo caricaSingoloTavoloConUtenti(Long id);
	
	public Tavolo inserisciNuovo(Tavolo tavoloInstance);
	
	public Tavolo aggiorna(Tavolo tavoloInstance);
	
	public void rimuovi(Tavolo tavoloInstance);
	
	public List<Tavolo> findByExample(Tavolo example);
	
	public List<Tavolo> findtavoloDiUtente(String descrizione,String username);
}
