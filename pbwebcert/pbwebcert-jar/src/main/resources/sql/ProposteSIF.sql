SELECT a.id_progetto
from PBANDI_T_PROGETTO a,
	 PBANDI_T_DOMANDA b, 
     PBANDI_R_BANDO_LINEA_INTERVENT c, 
     PBANDI_T_DETT_PROPOSTA_CERTIF pc 
WHERE a.id_progetto = pc.id_progetto 
 and b.id_domanda = a.id_domanda 
 and b.progr_bando_linea_intervento = c.progr_bando_linea_intervento 
 and c.flag_sif = 'S' 
 and id_proposta_certificaz = ?