select PROGR_BANDO_LINEA_INTERVENTO, NOME_BANDO_LINEA
from PBANDI_R_BANDO_LINEA_INTERVENT t1 
where t1.FLAG_SIF='S'
and not exists (select t2.* from PBANDI_R_BANDO_LINEA_INTERVENT t2 
                where t2.progr_bando_linea_interv_sif = t1.progr_bando_linea_intervento)