select distinct NOME_BANDO_LINEA as nomeBandoLinea , 
PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento
 from PBANDI_T_PREVIEW_DETT_PROP_CER 
 where ID_PROPOSTA_CERTIFICAZ = ?
 ORDER BY PROGR_BANDO_LINEA_INTERVENTO ASC