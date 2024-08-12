select distinct r.*,
ma.desc_modalita_agevolazione,
ma.desc_breve_modalita_agevolaz,
p.codice_visualizzato,
tr.desc_breve_tipo_recupero,
tr.desc_tipo_recupero
from PBANDI_T_RECUPERO r,
     PBANDI_D_MODALITA_AGEVOLAZIONE ma,
     PBANDI_T_PROGETTO p,
     PBANDI_D_TIPO_RECUPERO tr
where r.id_modalita_agevolazione = ma.id_modalita_agevolazione
  and r.id_progetto = p.id_progetto
  and r.id_tipo_recupero = tr.id_tipo_recupero