package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.web.api.exception.CustomException;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> listAllTavoli() {
		return (List<Tavolo>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConUtenti(Long id) {
		return repository.findByIdConUtenti(id);
	}

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		if(!tavoloInstance.getUtentiTavolo().isEmpty()) {
			throw new CustomException("Impossibile modificare tavolo con giocatori al suo interno.");
		}
		return repository.save(tavoloInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		if(!tavoloInstance.getUtentiTavolo().isEmpty()) {
			throw new CustomException("Impossibile eliminare tavolo con giocatori al suo interno.");
		}
		repository.delete(tavoloInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example) {
		return repository.findByExample(example);
	}

	@Override
	public List<Tavolo> findtavoloDiUtente(String descrizione, String username) {
		return repository.findtavoloDiUtente(descrizione, username);
	}

}
