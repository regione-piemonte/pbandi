SELECT t.id_tipol_intervento id_tipol_intervento, 
       t.codice codice_tipol_intervento, 
       t.descrizione desc_tipol_intervento, 
       t.id_macro_voce id_macro_tipol_intervento,
       d.id_dett_tipol_intervento id_dett_tipol_intervento, 
       d.codice codice_dett_tipol_intervento, 
       d.descrizione desc_dett_tipol_intervento, 
       d.id_macro_voce id_macro_dett_tipol_intervento,
       c.id_campo_intervento id_campo_intervento, 
       c.descrizione desc_campo_intervento,
       r.id_bando id_bando
FROM pbandi_r_bandi_tipol_interv r,
	 pbandi_d_tipol_interventi t, 
	 pbandi_d_dett_tipol_interventi d,
	 pbandi_d_campi_intervento c
WHERE t.id_tipol_intervento = r.id_tipol_intervento
  AND d.id_tipol_intervento(+) = t.id_tipol_intervento
  AND c.id_campo_intervento(+) = t.id_tipol_intervento	   
ORDER BY t.codice, d.codice