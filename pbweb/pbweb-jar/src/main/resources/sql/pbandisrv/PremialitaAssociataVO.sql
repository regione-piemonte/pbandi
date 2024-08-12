select d.ID_PREMIALITA, r.PROGR_BANDO_LINEA_INTERVENTO,
       d.DESCRIZIONE, d.LINK, 
       d.FLAG_TIPO_DATO_RICHIESTO, d.DATO_RICHIESTO
from pbandi_r_bandi_premialita r, pbandi_d_premialita d
where d.ID_PREMIALITA = r.ID_PREMIALITA