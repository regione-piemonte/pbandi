select distinct p.id_progetto, 
       d.progr_bando_linea_intervento,
       p.codice_visualizzato,
       rbli.id_bando,
       rbli.nome_bando_linea,
       p.titolo_progetto
from PBANDI_T_PROGETTO p,
     PBANDI_T_DOMANDA d,
     PBANDI_R_BANDO_LINEA_INTERVENT rbli
where p.id_domanda = d.id_domanda
  and rbli.progr_bando_linea_intervento = d.progr_bando_linea_intervento