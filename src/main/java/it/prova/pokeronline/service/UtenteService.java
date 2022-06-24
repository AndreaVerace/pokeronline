package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Utente;

public interface UtenteService {

	public Utente findByUsername(String username);
	
	public Utente inserisciNuovo(Utente utenteInstance);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
	public Utente caricaSingoloUtente(Long id);
	
	public List<Utente> listAllUtenti();
	
	public Utente caricaSingoloUtenteConRuoli(Long id);

	public Utente aggiorna(Utente utenteInstance);
	
	public void rimuovi(Utente utenteInstance);

	public List<Utente> findByExample(Utente example);
}
