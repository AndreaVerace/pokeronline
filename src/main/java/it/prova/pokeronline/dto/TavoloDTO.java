package it.prova.pokeronline.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {

	private Long id;
	
	private String denominazione;
	
	private Date dataCreazione;
	
	private int esperienzaMinima;
	
	private int cifraMinima;
	
	private Long[] utentiTavoloIds;
	
	private Utente utenteCreazione;

	public TavoloDTO() {
		
	}

	public TavoloDTO(Long id, String denominazione, Date dataCreazione, int esperienzaMinima, int cifraMinima,
			Utente utenteCreazione) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
		this.utenteCreazione = utenteCreazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public int getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(int esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public int getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(int cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public Long[] getUtentiTavoloIds() {
		return utentiTavoloIds;
	}

	public void setUtentiTavoloIds(Long[] utentiTavoloIds) {
		this.utentiTavoloIds = utentiTavoloIds;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	
	public Tavolo buildTavoloModel(boolean includeIdUtenti) {
		Tavolo result = new Tavolo(this.id, this.denominazione, this.dataCreazione, this.esperienzaMinima, this.cifraMinima,
				this.utenteCreazione);
		if (includeIdUtenti && utentiTavoloIds != null)
			result.setUtentiTavolo(Arrays.asList(utentiTavoloIds).stream().map(id -> new Utente(id)).collect(Collectors.toSet()));

		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel) {
		TavoloDTO result = new TavoloDTO(tavoloModel.getId(), tavoloModel.getDenominazione(), tavoloModel.getDataCreazione(),
				tavoloModel.getEsperienzaMinima(),tavoloModel.getCifraMinima(),tavoloModel.getUtenteCreazione());

		if (!tavoloModel.getUtentiTavolo().isEmpty())
			result.utentiTavoloIds = tavoloModel.getUtentiTavolo().stream().map(ut -> ut.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}
	
	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(tavoloEntity -> {
			TavoloDTO result = TavoloDTO.buildTavoloDTOFromModel(tavoloEntity);
			return result;
		}).collect(Collectors.toList());
	}
	
	
}
