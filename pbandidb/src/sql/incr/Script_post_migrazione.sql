/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--
--
-- Script per valorizzare CODICE_FONDO_FINPIS su pbandi_t_progetto per tutti e solo i progetti Finpiemonte non migrati
--

begin
for rec in (
select distinct NDG.COD_FONDO_GEFO,P.ID_PROGETTO
from TMP_NUM_DOMANDA_GEFO ndg,PBANDI_R_BANDO_LINEA_INTERVENT bli,pbandi_t_domanda d,pbandi_t_progetto p
where NDG.PROGR_BANDO_LINEA_INTERVENTO = BLI.PROGR_BANDO_LINEA_INTERVENTO
and   D.PROGR_BANDO_LINEA_INTERVENTO = BLI.PROGR_BANDO_LINEA_INTERVENTO
and   D.ID_DOMANDA = P.ID_DOMANDA
and   P.CODICE_FONDO_FINPIS is null
and NDG.PROGR_BANDO_LINEA_INTERVENTO not in (
select PROGR_BANDO_LINEA_INTERVENTO
from TMP_NUM_DOMANDA_GEFO
group by PROGR_BANDO_LINEA_INTERVENTO
having count(*) > 1)) loop

  update pbandi_t_progetto
  set    CODICE_FONDO_FINPIS = rec.COD_FONDO_GEFO
  where  ID_PROGETTO = rec.ID_PROGETTO;
end loop;
end;

begin
for rec in (
select p.*
from PBANDI_R_BANDO_LINEA_INTERVENT bli,pbandi_t_domanda d,pbandi_t_progetto p
where D.PROGR_BANDO_LINEA_INTERVENTO = BLI.PROGR_BANDO_LINEA_INTERVENTO
and   D.ID_DOMANDA = P.ID_DOMANDA
and   P.CODICE_FONDO_FINPIS is null
and BLI.PROGR_BANDO_LINEA_INTERVENTO in (
select PROGR_BANDO_LINEA_INTERVENTO
from TMP_NUM_DOMANDA_GEFO
group by PROGR_BANDO_LINEA_INTERVENTO
having count(*) > 1)) loop

  update pbandi_t_progetto
  set CODICE_FONDO_FINPIS = substr(rec.codice_visualizzato,1,4)
  where id_progetto = rec.id_progetto;
end loop;
end;



-- Eccezione Azhard
  update pbandi_t_progetto
  set CODICE_FONDO_FINPIS = '0324'
  where codice_visualizzato = 'A19_2018_CSP708';

-- Aggiornamento nome bando linea anteponendo i codici Fondo di Finpis per i bandi Finpiemonte
DECLARE
  CURSOR c1 IS
	select PROGR_BANDO_LINEA_INTERV , LISTAGG (cod_fondo,' - ') within group (order by cod_fondo) as fondi
	from MFINPIS_T_FONDI_BANDI
	where flag_caricato = 'SI'
	group by PROGR_BANDO_LINEA_INTERV
	order by PROGR_BANDO_LINEA_INTERV;

BEGIN
  for r1 in c1 LOOP
    update PBANDI_R_BANDO_LINEA_INTERVENT
	set NOME_BANDO_LINEA = r1.fondi||' - '||NOME_BANDO_LINEA
	where PROGR_BANDO_LINEA_INTERVENTO = r1.PROGR_BANDO_LINEA_INTERV;
  end loop;
END;


