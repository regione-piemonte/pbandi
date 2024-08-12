select i.id_indirizzo,
		i.id_comune,
		c.desc_comune,
		i.id_comune_estero,
		ce.desc_comune_estero,
		i.id_nazione,
		n.desc_nazione,
		i.id_provincia,
		p.sigla_provincia,
		p.desc_provincia,
		i.id_regione,
		r.desc_regione,
		i.desc_indirizzo,
		i.cap,
		i.civico,
		i.dt_inizio_validita,
		i.dt_fine_validita
from pbandi_t_indirizzo i 
  left outer join pbandi_d_comune c on i.id_comune = c.id_comune
  left outer join pbandi_d_nazione n on i.id_nazione = n.id_nazione
  left outer join pbandi_d_provincia p on i.id_provincia = p.id_provincia
  left outer join pbandi_d_regione r on i.id_regione = r.id_regione
  left outer join pbandi_d_comune_estero ce on i.id_comune_estero = ce.id_comune_estero