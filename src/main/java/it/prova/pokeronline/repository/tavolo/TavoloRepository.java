package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>,CustomTavoloRepository {

	@Query("from Tavolo t left join fetch t.utentiTavolo where t.id = ?1")
	<Optional>Tavolo findByIdConUtenti(Long id);
	
	@Query("from Tavolo t left join fetch t.utenteCreazione u inner join fetch u.ruoli r "
			+ " where r.descrizione like ?1 and u.username like ?2")
	List<Tavolo> findtavoloDiUtente(String descrizione,String username);
	
}
