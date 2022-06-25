package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "denominazione")
	private String denominazione;
	
	@Column(name = "dataCreazione")
	private Date dataCreazione;
	
	@Column(name = "esperienzaMinima")
	private int esperienzaMinima;
	
	@Column(name = "cifraMinima")
	private int cifraMinima;

	@ManyToMany
	@JoinTable(name = "tavolo_utente", joinColumns = @JoinColumn(name = "tavolo_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"))
	private Set<Utente> utentiTavolo = new HashSet<>(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utenteCreazione_id", referencedColumnName = "id", nullable = false)
	private Utente utenteCreazione;
	
	public Tavolo() {
		
	}

	
	
	public Tavolo(Long id) {
		this.id = id;
	}



	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMinima, int cifraMinima) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMinima, int cifraMinima,
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

	public Set<Utente> getUtentiTavolo() {
		return utentiTavolo;
	}

	public void setUtentiTavolo(Set<Utente> utentiTavolo) {
		this.utentiTavolo = utentiTavolo;
	}

	public Utente getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(Utente utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}
	

	
}
