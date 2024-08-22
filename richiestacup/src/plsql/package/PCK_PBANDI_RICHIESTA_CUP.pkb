/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PBANDI_CUP.PCK_PBANDI_RICHIESTA_CUP AS
/******************************************************************************
   NAME:       PCK_PBANDI_RICHIESTA_CUP
   PURPOSE:    Procedura richiesta CUP verso BDU

   REVISIONS: 
   Ver        Date        Author                      Description
   ---------  ----------  ---------------             ------------------------------------
   1.0        05/06/2012  benedetto Provvisionato     Procedura richiesta CUP verso BDU      
  ******************************************************************************/


/************************************************************************
Main : funzione di lancio del processo
*************************************************************************/


nIdSistemaMittente  dati_richiesta_elab_cup.id_sistema_mittente%TYPE :='PBAN-';
nidRichiesta dati_richiesta_elab_cup.id_richiesta%TYPE;

FUNCTION Main RETURN NUMBER IS
 CURSOR cur_mittente IS  
   select distinct e_c.id_sistema_mittente
       from
           prg_class_cup_puntuale e_c

       where
       e_c.id_sistema_mittente <> '????-'; 

    nFn  PLS_INTEGER;
  
	PROCEDURE prerequisiti_progetto IS
	  CURSOR c_progetti IS
		 select prog.id_progetto,
				top.desc_breve_tipo_operazione,
				s.codice_fiscale_soggetto
		 from  pbandi_t_progetto prog,
			   pbandi_t_dati_progetto_monit prog_monit,
			   pbandi_d_tipo_operazione top,
			   pbandi_r_soggetto_progetto sp,
			   pbandi_t_soggetto s
		   where prog.cup is null
		   and prog_monit.flag_richiesta_cup ='S'
		   and prog_monit.flag_richiesta_cup_inviata ='N'
		   and prog.id_progetto =prog_monit.id_progetto
		   and prog.id_tipo_operazione = top.id_tipo_operazione
		   and sp.id_progetto = prog.id_progetto
		   and sp.id_tipo_beneficiario != 4 -- Beneficiario
		   and sp.id_tipo_anagrafica = 1 --Beneficiario
		   and s.id_soggetto = sp.id_soggetto;
		   
	BEGIN
	   FOR r1 IN c_progetti LOOP
		  IF r1.codice_fiscale_soggetto NOT LIKE '80087670016%' -- DIVERSO DA REGIONE
			AND r1.desc_breve_tipo_operazione IN ('01','02') THEN -- set flag_richiesta_cup = 'N'
			  UPDATE pbandi_t_dati_progetto_monit
				 SET flag_richiesta_cup = 'N'
			   WHERE id_progetto = r1.id_progetto;
			   
		  PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W094',' id_PROGETTO = '||r1.id_progetto);
			   
		  END IF;
		END LOOP;
		   
	END prerequisiti_progetto;

BEGIN

---------------------------------------  
--- delete di tutti i dati precedenti
---------------------------------------
  delete DATI_RICHIESTA_ELAB_cup ;
  delete prg_class_cup_puntuale ;
  delete PRG_FINANZIAMENTO_CUP;
  DELETE PRG_LOCALITA_GEO_CUP ;
  COMMIT;
  
  -------------------------------------------------------------------------------
  -- Procedura che stabilisce se il progetto deve essere processato 
  -- Flag_richiesta_cup= S
  
  -- ad eccezione di  casistiche particolari dove viene settato a N in modo
  -- da non rientrare nella richiesta CUP
  --se tipo operazione = 1 o 2 (pbandi_t_progetto.id_tipo_operazione) 
  --e se il codice fiscale dele beneficiario è diverso da quello della regione (pbandi_t_soggetto.codice_fiscale_soggetto != '80087670016%')
  --  allora non bisogna richiedere il cup
  --------------------------------------------------------------------------------
  prerequisiti_progetto;
-------------------------------------------------  


-- RICHIAMO LA FUNZIONE DATIPROGETTO
-- PER POPOLARE LE TABELLE :
--                         PRG_CASS_CUP_PUNTUALE
--                         PRG_FINANZIAMENTO_CUP
--                         PRG_LOCALITA_GEO_CUP
--
---------------------------------------------------
  nFn := DatiProgetto;

  IF nFn = 1 THEN
    
 -- ESITO CON ERRORE 
  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
    rollback;
    RETURN 1;
  ELSE

  --
  -- POPOLAMENTO TABELLA DATI_RICHIESTA_ELAB_CUP
  --
     nidRichiesta:=0;     
      
    if  controllo_tabelle = 0 then

	    nFn :=pareggio_progetti;
      
        for recmittente in cur_mittente loop
            nidRichiesta:= nidRichiesta+1;
		 begin    

		   
			INSERT INTO DATI_RICHIESTA_ELAB_cup
			(ID_SISTEMA_MITTENTE, 
			 ID_RICHIESTA, 
			 DATA_RICHIESTA)
			 
			VALUES
			(recmittente.id_sistema_mittente,
			 nidRichiesta,
			 SYSDATE);
			 
		 end;   

       end loop; 

     
    else 
	   PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W082','Errore su Controllo tabelle: Non tutte le tabelle sono popolate');      
       PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'KO');
    rollback;
    --RETURN 1;
    RETURN 0; -- non mando comunque in errore il batch
      
     
     end if; 
    
  END IF;

  
 commit;
 PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(gnIdProcessoBatch,'OK');

  RETURN 0;
END Main;


FUNCTION datiprogetto  return number is 

--       CURSORE CHE SELEZIONA I PROGETTI CHE 
--       VERRANNNO CONSIDERATI PER IL RIEMPIMENTO
--       DELLE TABELLE
--
CURSOR cur_progetti IS  select prog.id_progetto
       from
           pbandi_t_progetto prog,
           pbandi_t_dati_progetto_monit prog_monit

       where
           prog.cup is null
       and prog_monit.flag_richiesta_cup ='S'
       and prog_monit.flag_richiesta_cup_inviata ='N'
       and prog.id_progetto =prog_monit.id_progetto
       order by prog.dt_inserimento;




--     CURSORE CHE RICAVA I DATI FINANZIARI
--                               FONTE
--                               IMPORTO

CURSOR curFinanz(pIdProgetto  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS SELECT
       sogg_fin.cod_igrue_t25 fonte,
       decode(sum(p_s_f.imp_quota_sogg_finanziatore),null,0,sum(p_s_f.imp_quota_sogg_finanziatore)) importo

       from
           pbandi_r_prog_sogg_finanziat p_s_f,
           pbandi_d_soggetto_finanziatore sogg_fin
       where
           p_s_f.id_progetto=pIdProgetto 
       and p_s_f.id_soggetto_finanziatore=sogg_fin.id_soggetto_finanziatore

       group by
           p_s_f.id_progetto,
           sogg_fin.cod_igrue_t25;

--   cursore che ricava idati GEOgrafici

--                         COD_REGIONE 
--                         COD_PROVINCIA,
--                         COD_COMUNE
------------------------------------------
CURSOR curIndir(pIdProgetto  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) IS SELECT 
       i.*
       
       FROM   PBANDI_R_SOGGETTO_PROGETTO sp,
              PBANDI_R_SOGG_PROGETTO_SEDE sps,
              PBANDI_T_INDIRIZZO i
       WHERE  SP.ID_PROGETTO             = pIdProgetto
       AND    sp.ID_TIPO_ANAGRAFICA      = 1
       AND    sp.ID_TIPO_BENEFICIARIO   != 4
       AND    sp.PROGR_SOGGETTO_PROGETTO = sps.PROGR_SOGGETTO_PROGETTO
       AND    sps.ID_TIPO_SEDE           = 2
       AND    sps.ID_INDIRIZZO           = i.ID_INDIRIZZO 
       and    rownum                     =1 ;
  




ret_class integer;
tipo_fonte integer;
--importo integer;
importo PRG_FINANZIAMENTO_CUP.IMPORTO%TYPE;

vCodIstatRegione   PRG_LOCALITA_GEO_CUP.COD_REGIONE%TYPE;
vCodIstatProvincia PRG_LOCALITA_GEO_CUP.COD_PROVINCIA%TYPE;
vCodIstatComune    PBANDI_D_COMUNE.COD_ISTAT_COMUNE%TYPE;   
vmaxrecord         integer:=0;
costante_max       integer;


begin
       select to_number(c.valore)
       into
       costante_max
       from 
           pbandi_c_costanti c
       where c.attributo='PCK_PBANDI_RICHIESTA_CUP.NUM_MAX_PROG_CUP' ; 

for recprogetto in cur_progetti loop
 vmaxrecord :=vmaxrecord + 1;
 
 exit when  vmaxrecord >  costante_max;


--------------------------------------------------------------------------
---RICHIAMO FUNZIONE CLASSIFCUP CHE
--- INSERISCE DATI SU                    PRG_CLASS_CUP_PUNTUALE
--------------------------------------------------------------------------

ret_class:=ClassifCup(recprogetto.id_progetto);
if ret_class =1 then
  
   return 1;

end if;

--------------------------------------------
--POPOLAMENTO TABELLA PRG_FINANZIAMENTO_CUP
--------------------------------------------

FOR recfinanz in curfinanz(recprogetto.id_progetto) loop
  
     tipo_fonte:=recfinanz.fonte;
     importo:=recfinanz.importo;
begin
     insert into PRG_FINANZIAMENTO_CUP
                   (ID_SISTEMA_MITTENTE,
                    cod_locale_progetto,
                    FONTE,
                    IMPORTO)
             values

                    (nIdSistemaMittente,
                     recprogetto.id_progetto,
                     tipo_fonte,
                     importo);
      EXCEPTION
        WHEN OTHERS THEN
          PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E087',sqlerrm||' id_PROGETTO = '||recProgetto.ID_PROGETTO||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
       RETURN 1;                     
end;      

end loop;

-------------------------------------------
-- POPOLAMENTO TABELLA PRG_LOCALITA_GEO_CUP
-------------------------------------------

FOR recIndir IN curIndir(recProgetto.ID_PROGETTO) LOOP
-----------------
-- COD_REGIONE
-----------------    
    IF recIndir.ID_REGIONE IS NOT NULL THEN
    
        SELECT lpad(r.COD_ISTAT_REGIONE,3,'0')
        INTO   vCodIstatRegione
        FROM   PBANDI_D_REGIONE r
        WHERE  r.ID_REGIONE = recIndir.ID_REGIONE;
    
    ELSIF ((recIndir.ID_REGIONE IS NULL) AND (recIndir.ID_COMUNE_ESTERO IS NULL)) THEN
    
        begin
          SELECT lpad(r.COD_ISTAT_REGIONE,3,'0')
          INTO   vCodIstatRegione
          FROM   PBANDI_D_REGIONE r,
                 PBANDI_D_PROVINCIA p
          WHERE  p.ID_PROVINCIA = recIndir.ID_PROVINCIA
          AND    r.ID_REGIONE = p.ID_REGIONE;
        exception
          when no_data_found then
            vCodIstatRegione := '000';
        end;      
    
    ELSIF ((recIndir.ID_REGIONE IS NULL) AND (recIndir.ID_COMUNE_ESTERO IS NOT NULL)) THEN
        SELECT n.COD_ISTAT_NAZIONE
        INTO   vCodIstatRegione
        FROM   PBANDI_D_COMUNE_ESTERO ce,
               PBANDI_D_NAZIONE n
        WHERE  ce.ID_COMUNE_ESTERO = recIndir.ID_COMUNE_ESTERO
        AND    ce.ID_NAZIONE       = n.ID_NAZIONE; 
    END IF;

----------------
-- COD_PROVINCIA
----------------
      BEGIN
        SELECT p.COD_ISTAT_PROVINCIA
        INTO   vCodIstatProvincia
        FROM   PBANDI_D_PROVINCIA p
        WHERE  p.ID_PROVINCIA = recIndir.ID_PROVINCIA;
      exception
        when no_data_found then
          vCodIstatProvincia := '000';
      end;
---------------
-- COD_COMUNE 
---------------     
      BEGIN
        SELECT c.COD_ISTAT_COMUNE
        INTO   vCodIstatComune
        FROM   PBANDI_D_COMUNE c
        WHERE  c.ID_COMUNE = recIndir.ID_COMUNE;
      exception
        when no_data_found then
          vCodIstatComune := '000000';
      end;
      
      BEGIN
        
--  if  recProgetto.ID_PROGETTO =5046 then     
      
--      DBMS_OUTPUT.put_line ('recProgetto.ID_PROGETTO '||recProgetto.ID_PROGETTO||
 --                          'vCodIstatRegione '||vCodIstatRegione||
--                           'vCodIstatProvincia'||vCodIstatProvincia||
--                           'substr(vCodIstatComune,4,3) '||substr(vCodIstatComune,4,3));    
  
--  end if;

        INSERT INTO PRG_LOCALITA_GEO_CUP
        (ID_SISTEMA_MITTENTE,
         COD_LOCALE_PROGETTO,
         COD_REGIONE, 
         COD_PROVINCIA,
         COD_COMUNE)
        VALUES
        (nIdSistemaMittente,
         recProgetto.ID_PROGETTO,
         vCodIstatRegione,
         vCodIstatProvincia,
         substr(vCodIstatComune,4,3));
      EXCEPTION
        WHEN OTHERS THEN
          PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E087',sqlerrm||' id_PROGETTO = '||recProgetto.ID_PROGETTO||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
       RETURN 1;
      END;   

END LOOP;


UPDATE    Pbandi_t_Dati_Progetto_Monit d_p_m

          SET    
                 d_p_m.flag_richiesta_cup_inviata = 'S'
          WHERE  d_p_m.id_progetto        = recprogetto.id_progetto;
          

end loop;

return 0;
end datiprogetto;

----------------------------------------------------
-- FUNZIONE ClassifCup INPUT ID_PROGETTO

-- SCOPO POPOLA  LA TABELLA PRG_CLASS_CUP_PUNTUALE
-- REALIZZANDO GLI ALGORITMI DI ANALISI :
--                                     ALG-CIPE-00
--                                     ALG-CIPE-01
--                                     ALG-CIPE-02
--                                     ALG-CIPE-03
--                                     ALG-CIPE-04
--                                     ALG-CIPE-05
---------------------------------------------------
 FUNCTION ClassifCup
          (pIdProgetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) return number is
 



 
  pIdSoggTitolareCipe  PBANDI_T_DATI_PROGETTO_MONIT.ID_SOGG_TITOLARE_CIPE%TYPE;
  vCodCipe                 PBANDI_T_ENTE_COMPETENZA.COD_CIPE%TYPE;
  vUserCipe                PBANDI_T_ENTE_COMPETENZA.USER_CIPE%TYPE;
  vCodCipeUo               PBANDI_D_UNITA_ORGANIZZATIVA.COD_CIPE_UO%TYPE;
  nAnnoDecisione           PRG_CLASS_CUP_PUNTUALE.ANNO_DECISIONE%TYPE;
  vCodNaturaCipe           PBANDI_D_NATURA_CIPE.COD_NATURA_CIPE%TYPE;
  vCodTipologiaCipe        PBANDI_D_TIPOLOGIA_CIPE.COD_TIPOLOGIA_CIPE%TYPE;
  vCodSettoreCipe          PBANDI_D_SETTORE_CIPE.COD_SETTORE_CIPE%TYPE;
  vCodSottoSettoreCipe     PBANDI_D_SOTTO_SETTORE_CIPE.COD_SOTTO_SETTORE_CIPE%TYPE;
  vCodCategoriaCipe        PBANDI_D_CATEGORIA_CIPE.COD_CATEGORIA_CIPE%TYPE;
  vCodStrumento            PBANDI_D_TIPO_STRUMENTO_PROGR.COD_STRUMENTO%TYPE;
  vDescStrumento           PRG_CLASS_CUP_PUNTUALE.DESC_STRUMENTO_PROGRAMMAZIONE%TYPE;
  vDescStrumento_first     PRG_CLASS_CUP_PUNTUALE.DESC_STRUMENTO_PROGRAMMAZIONE%TYPE;
  vNomeStruttura           PRG_CLASS_CUP_PUNTUALE.NOME_STRUTTURA_INFRASTRUTTURA%TYPE;
  vTitoloProgetto          PBANDI_T_PROGETTO.TITOLO_PROGETTO%TYPE;
  vPartitaIva              PBANDI_T_SEDE.PARTITA_IVA%TYPE;
  vAtecoBeneficiario       Prg_Class_Cup_Puntuale.ATECO_BENEFICIARIO%TYPE;
  vDescBreveTipoOperaz     PBANDI_D_TIPO_OPERAZIONE.DESC_BREVE_TIPO_OPERAZIONE%TYPE;
  vFinanzaProgetto         Prg_Class_Cup_Puntuale.FINANZA_PROGETTO%TYPE;
  vSponsorizzazione        PRG_CLASS_CUP_PUNTUALE.SPONSORIZZAZIONE%TYPE;
  nPpf                     PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPp                      PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nIdLineaDiIntervento     PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  nIdLineaInterventoPadre  PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO_PADRE%TYPE;
  nIdTipoLineaIntervento   PBANDI_D_LINEA_DI_INTERVENTO.ID_TIPO_LINEA_INTERVENTO%TYPE;
  nIdLineaDiInterventoOb   PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO%TYPE;
  vCodSettore              PBANDI_D_SETTORE_ATTIVITA.COD_SETTORE%TYPE;
  nIdEnteGiuridico         PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO%TYPE;
  nIdPersonaFisica         PBANDI_R_SOGGETTO_PROGETTO.ID_PERSONA_FISICA%TYPE;
  vDenomBeneficiario       PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO%TYPE;
  vDescIntervento           PRG_CLASS_CUP_PUNTUALE.DESC_INTERVENTO%TYPE;
  vDescFormaGiuridica      PBANDI_D_FORMA_GIURIDICA.DESC_FORMA_GIURIDICA%TYPE;
  vCodGefo                 PBANDI_D_FORMA_GIURIDICA.COD_GEFO%TYPE;
  vCodIgrueT13T14          PBANDI_D_LINEA_DI_INTERVENTO.COD_IGRUE_T13_T14%TYPE;
  vNumdelibera             PRG_CLASS_CUP_PUNTUALE.NUM_DELIBERA%TYPE;
  vAnnodelibera            PRG_CLASS_CUP_PUNTUALE.ANNO_DELIBERA%TYPE;     
  vNumdelibera_monit             PRG_CLASS_CUP_PUNTUALE.NUM_DELIBERA%TYPE;
  vAnnodelibera_monit            PRG_CLASS_CUP_PUNTUALE.ANNO_DELIBERA%TYPE; 
  vflaglegobiettivo        pbandi_t_dati_progetto_monit.flag_leg_obiettivo%TYPE;
BEGIN
  



--**********************************************
----   INIZIO  ALG-CIPE-00
--
----VALORIZZA I CAMPI :
----      COD_CIPE_SOGGETTO_TITOLARE
----      COD_CIPE_UO_SOGG_TITOLARE
----      COD_CIPE_USER_SOGG_TITOLARE
--**********************************************

--                    ANALISI

-- 1	Se per il progetto corrente il campo
--    PBANDI_T_DATI_PROGETTO_MONIT.ID_SOGG_TITOLARE_CIPE è valorizzato allora
--    si usa questo identificativo sulla PBANDI_T_ENTE_COMPETENZA.

-- 2	Se invece per il progetto corrente il campo 
--    PBANDI_T_DATI_PROGETTO_MONIT.ID_SOGG_TITOLARE_CIPE NON è valorizzato 
--    allora dal progetto si risale alla PBANDI_R_BANDO_LINEA_INTERVENT e 
--    da questa alla PBANDI_R_BANDO_LINEA_ENTE_COMP, 
--    da qui si ottiene il record con PBANDI_D_RUOLO_ENTE_COMPETENZA 
--    corrispondente alla DESC_BREVE_RUOLO_ENTE=  ¿RICHIEDENTE CUP¿, 
--    quindi si usa l¿identificativo dell¿ente di competenza del record 
--    individuato sulla PBANDI_T_ENTE_COMPETENZA.
--    Con l¿informazione raccolta nei due casi alternativi si accede alla 
--    PBANDI_T_ENTE_COMPETENZA e si valorizzano i campi del tracciato come 
--    illustrato di seguito:

--    COD_CIPE_SOGGETTO_TITOLARE = PBANDI_T_ENTE_COMPETENZA.COD_CIPE

---   COD_CIPE_UO_SOGG_TITOLARE = dal 
---   PBANDI_T_ENTE_COMPETENZA.ID_ENTE_COMPETENZA si risale al campo 
---   PBANDI_D_UNITA_ORGANIZZATIVA.COD_CIPE_UO

--    COD_CIPE_USER_SOGG_TITOLARE = PBANDI_T_ENTE_COMPETENZA.USER_CIPE

---------------------------------------------------------------------------


    vCodCipe   := NULL;
    vCodCipeUo := NULL;
    vUserCipe  := NULL;
--DBMS_OUTPUT.put_line ('pIdProgetto '||pIdProgetto);    
SELECT D_P_M.ID_SOGG_TITOLARE_CIPE
       INTO  pIdSoggTitolareCipe
             FROM PBANDI_T_DATI_PROGETTO_MONIT D_P_M
             WHERE
             D_P_M.ID_PROGETTO=pIdProgetto;    
             
 
    
IF pIdSoggTitolareCipe IS NULL THEN
--    
--ID_SOGGETTO_TIPOLARE_CIPE NON VALORIZZATO  
-- 
--
BEGIN
  SELECT ENTE_COMP.COD_CIPE,ENTE_COMP.USER_CIPE,
       UNIT_ORG.COD_CIPE_UO
       
       INTO  vCodCipe,vUserCipe,vCodCipeUo
    
             FROM PBANDI_T_PROGETTO prog,
                  pbandi_r_bando_linea_intervent l_int,
                  pbandi_t_domanda dom,
                  pbandi_r_bando_linea_ente_comp l_comp,
                  pbandi_d_ruolo_ente_competenza ruolo_ente_comp, 
                  PBANDI_T_ENTE_COMPETENZA ENTE_COMP,
                  PBANDI_D_UNITA_ORGANIZZATIVA UNIT_ORG
         
             where
                  prog.id_progetto=pIdProgetto
                  and prog.id_domanda=dom.id_domanda 
                  and dom.progr_bando_linea_intervento=l_int.progr_bando_linea_intervento
                  and l_int.progr_bando_linea_intervento=l_comp.progr_bando_linea_intervento
                  and l_comp.id_ruolo_ente_competenza=ruolo_ente_comp.id_ruolo_ente_competenza
                  and ruolo_ente_comp.desc_breve_ruolo_ente='RICHIEDENTE CUP' 
                  AND ENTE_COMP.ID_ENTE_COMPETENZA =L_COMP.ID_ENTE_COMPETENZA 
                  AND ENTE_COMP.ID_ENTE_COMPETENZA=UNIT_ORG.ID_ENTE_COMPETENZA;
	    EXCEPTION
         WHEN NO_DATA_FOUND THEN 
           vCodCipe   := NULL;    
           vCodCipeUo := NULL;
           vUserCipe  := NULL;
     END;  
  
  ELSE
    
--    
--ID_SOGGETTO_TIPOLARE_CIPE  VALORIZZATO   
--   
    
 
 SELECT COD_CIPE,USER_CIPE
    INTO   vCodCipe,vUserCipe
           FROM   PBANDI_T_ENTE_COMPETENZA
           WHERE  ID_ENTE_COMPETENZA = pIdSoggTitolareCipe;

    BEGIN
      SELECT uo.COD_CIPE_UO
             INTO   vCodCipeUo
             FROM   PBANDI_D_UNITA_ORGANIZZATIVA uo
             WHERE  ID_ENTE_COMPETENZA = pIdSoggTitolareCipe;
      
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        vCodCipeUo := NULL;
    END;
END IF;

---------------------------------------
----FINE MODIFICA ALG-CIPE-00
---------------------------------------


---
--- INIZIO ESTRAZIONE CAMPI :
----                         ANNO_DECISIONE
----                         NATURA
----                         TIPOLOGIA
----                         CATEGORIA
---


  BEGIN
    SELECT TO_NUMBER(TO_CHAR(NVL(p.DT_CONCESSIONE,p.DT_COMITATO),'YYYY')),nc.COD_NATURA_CIPE,
           tc.COD_TIPOLOGIA_CIPE,cc.COD_CATEGORIA_CIPE,p.TITOLO_PROGETTO
    INTO   nAnnoDecisione,vCodNaturaCipe,
           vCodTipologiaCipe,vCodCategoriaCipe,vTitoloProgetto
    FROM   PBANDI_T_PROGETTO p,PBANDI_D_TIPOLOGIA_CIPE tc,PBANDI_D_NATURA_CIPE nc,
           PBANDI_D_CATEGORIA_CIPE cc
    WHERE  p.ID_PROGETTO       = pIdProgetto
    AND    p.ID_TIPOLOGIA_CIPE = tc.ID_TIPOLOGIA_CIPE(+)
    AND    tc.ID_NATURA_CIPE   = nc.ID_NATURA_CIPE(+)
    AND    p.ID_CATEGORIA_CIPE = cc.ID_CATEGORIA_CIPE(+);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      nAnnoDecisione    := NULL;
      vCodNaturaCipe    := NULL;
      vCodTipologiaCipe := NULL;
      vCodCategoriaCipe := NULL;
      vTitoloProgetto   := NULL;
  END;

---
--- FINE ESTRAZIONE CAMPI :
---


-----------------------------------------
-- INIZIO ESTRAZIONE CAMPI
---                        SETTORE
--                         SOTTOSETTORE
----------------------------------------
BEGIN
    
  SELECT 
        sc.COD_SETTORE_CIPE,
        ssc.COD_SOTTO_SETTORE_CIPE
  INTO  vCodSettoreCipe,
        vCodSottoSettoreCipe
  FROM
        PBANDI_T_PROGETTO PROG,
        PBANDI_T_DOMANDA  DOM,
        PBANDI_R_BANDO_LINEA_INTERVENT L_INT,
        PBANDI_T_BANDO BANDO,
        PBANDI_D_SOTTO_SETTORE_CIPE ssc,
        PBANDI_D_SETTORE_CIPE sc

  WHERE
        PROG.ID_PROGETTO=pIdProgetto
  AND   PROG.ID_DOMANDA=DOM.ID_DOMANDA 
  AND   DOM.PROGR_BANDO_LINEA_INTERVENTO=L_INT.PROGR_BANDO_LINEA_INTERVENTO
  AND   L_INT.ID_BANDO=BANDO.ID_BANDO
  AND   BANDO.ID_SOTTO_SETTORE_CIPE = ssc.ID_SOTTO_SETTORE_CIPE
  AND   ssc.ID_SETTORE_CIPE     = sc.ID_SETTORE_CIPE;
  
  
EXCEPTION
  WHEN NO_DATA_FOUND THEN
       vCodSettoreCipe      := NULL;
       vCodSottoSettoreCipe := NULL;
END;
  
----------------------------------------
-- FINE ESTRAZIONE CAMPI
---                        SETTORE
--                         SOTTOSETTORE
----------------------------------------     

  
  
  
  
--*********************************
----INIZIO MODIFICA ALG-CIPE-02
--*********************************
--    VALORIZZA I SEGUENTI CAMPI  : 
--    NOME_STRUTTURA_INFRASTRUTTURA (100)
--    DESC_INDIRIZZO_AREA (50)     
   

--                        ANALISI
-- Sulla tabella PBANDI_R_SOGGETTO_PROGETTO si cerca il record con:

--	ID_PROGETTO corrente 
--	ID_TIPO_ANAGRAFICA corrispondente alla descrizione BENEFICIARIO
--	ID_TIPO_BENEFICIARIO diverso da quello corrispondente alla descrizione 
--  BEN-ASSOCIATO

-- 1.	Attraverso il PROGR_SOGGETTO_PROGETTO trovato si cerca nella tabella 
--    PBANDI_R_SOGG_PROGETTO_SEDE il record con ID_TIPO_SEDE corrispondente 
--    alla DESC_BREVE= SI. In caso di più record si prende il primo.

-- 2.	Partendo dal record precedente si trova il campo ID_INDIRIZZO e 
--    si accede alla PBANDI_T_INDIRIZZO

-- 3.	Il campo sarà valorizzato legando DESC_INDIRIZZO +   + CIVICO 
--    + ,  + CAP +   +  DESC_COMUNE* +   + DESC_PROVINCIA 
--    *(oppure DESC_COMUNE_ESTERO* se valorizzato) +   + DESC_NAZIONE.

--   *: a questi valori si arriva partendo dagli ID_COMUNE, 
--   ID_PROVINCIA e ID_NAZIONE sulla PBANDI_T_INDIRIZZO.

-----------------------------------------------------------------------------------

  BEGIN
    SELECT substr(i.DESC_INDIRIZZO||' '||i.CIVICO||' '||i.CAP||','||
           DECODE(ce.DESC_COMUNE_ESTERO,
           NULL,d.DESC_COMUNE||' '||p.DESC_PROVINCIA,
           ce.DESC_COMUNE_ESTERO)||' '||n.DESC_NAZIONE,1,100)
    INTO   vNomeStruttura
    FROM   PBANDI_R_SOGGETTO_PROGETTO sp,PBANDI_R_SOGG_PROGETTO_SEDE sps,
           PBANDI_T_INDIRIZZO i,PBANDI_D_COMUNE d,PBANDI_D_PROVINCIA p,
           PBANDI_D_COMUNE_ESTERO ce,PBANDI_D_NAZIONE n,
           PBANDI_D_TIPO_SEDE TIPO_SEDE
    WHERE  SP.ID_PROGETTO             = pIdProgetto
    AND    sp.ID_TIPO_ANAGRAFICA      = 1 --BENEFICIARIO
    AND    sp.ID_TIPO_BENEFICIARIO   != 4 --DIVERSO DA BENEFICIARIO ASSOCIATO
    AND    sp.PROGR_SOGGETTO_PROGETTO = sps.PROGR_SOGGETTO_PROGETTO
    AND    sps.ID_TIPO_SEDE           = TIPO_SEDE.ID_TIPO_SEDE
    AND    TIPO_SEDE.DESC_BREVE_TIPO_SEDE = 'SI'
    AND    sps.ID_INDIRIZZO           = i.ID_INDIRIZZO
    AND    i.ID_COMUNE                = d.ID_COMUNE(+)
    AND    d.ID_PROVINCIA             = p.ID_PROVINCIA(+)
    AND    i.ID_COMUNE_ESTERO         = ce.ID_COMUNE_ESTERO(+)
    AND    I.ID_NAZIONE               = N.ID_NAZIONE(+)
    AND    ROWNUM                     = 1;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      vNomeStruttura := NULL;
  END;
  
--*****************************
----FINE MODIFICA ALG-CIPE-02
---****************************



---******************************
----inizio MODIFICA ALG-CIPE-05
---******************************

-- VALORIZZA I SEGUENTI CAMPI  : 
--                             PIVA_BENEFICIARIO
--                             ATECO_BENEFICIATIO
---

--   Sulla tabella PBANDI_R_SOGGETTO_PROGETTO si cerca il record con:

-- 	ID_PROGETTO corrente 
-- 	ID_TIPO_ANAGRAFICA corrispondente alla descrizione ¿BENEFICIARIO¿
--  ID_TIPO_BENEFICIARIO diverso da quello corrispondente alla descrizione ¿BEN-ASSOCIATO¿

--1.	Attraverso il PROGR_SOGGETTO_PROGETTO trovato si cerca nella tabella 
--    PBANDI_R_SOGG_PROGETTO_SEDE il record con ID_TIPO_SEDE corrispondente alla DESC_BREVE= ¿SI¿.
--    In caso di più record si prende il primo.

--2.	Il campo del tracciato PIVA_BENEFICIARIO sarà valorizzato con PBANDI_T_SEDE.PARTITA_IVA

--3.	Partendo dall'ID_SEDE si trova il campo PBANDI_T_SEDE.ID_ATTIVITA_ATECO, da cui si risale
--    al record sulla PBANDI_D_ ATTIVITA_ATECO e si seleziona il COD_ATECO (indichiamolo con A)

--4.	Sul record individuato al punto precedente si prende il campo ID_SETTORE_ATTIVITA e,
--    da questo, si risale sulla PBANDI_D_SETTORE_ATTIVITA al campo COD_SETTORE (indichiamolo con B)

--5.	Infine il campo del tracciato ATECO_BENEFICIARIO sarà valorizzato con B-A. 

--    Se A = null allora si cercano i record sulla 
--    PBANDI_T_SEDE con PARTITA_IVA = quella del record trovato al punto 2. 
--    Tra questi si prende il primo (se c¿è) con PBANDI_T_SEDE.ID_ATTIVITA_ATECO valorizzato e 
--    si prosegue col punto 4.

---------------------------------------------------------------------------------------------------------

BEGIN
    SELECT sa.COD_SETTORE,aa.COD_ATECO,s.PARTITA_IVA
           
    INTO   vCodSettore,vAtecoBeneficiario,vPartitaIva
    FROM   PBANDI_R_SOGGETTO_PROGETTO sp,PBANDI_R_SOGG_PROGETTO_SEDE sps,
           PBANDI_T_SEDE s,PBANDI_D_ATTIVITA_ATECO aa,PBANDI_D_SETTORE_ATTIVITA sa,
           PBANDI_D_ATTIVITA_ECONOMICA ae
    WHERE  SP.ID_PROGETTO             = pIdProgetto
    AND    sp.ID_TIPO_ANAGRAFICA      = 1 ---BENEFICIARIO
    AND    sp.ID_TIPO_BENEFICIARIO   != 4 
    AND    sp.PROGR_SOGGETTO_PROGETTO = sps.PROGR_SOGGETTO_PROGETTO
    AND    sps.ID_TIPO_SEDE           = 2
    AND    sps.ID_SEDE                = s.ID_SEDE
    AND    s.ID_ATTIVITA_ATECO        = aa.ID_ATTIVITA_ATECO(+)
    AND    aa.ID_SETTORE_ATTIVITA     = sa.ID_SETTORE_ATTIVITA(+)
    AND    aa.ID_ATTIVITA_ECONOMICA   = ae.ID_ATTIVITA_ECONOMICA(+)
    AND    ROWNUM                     = 1;

    IF vAtecoBeneficiario IS NOT NULL THEN
      vAtecoBeneficiario := vCodSettore||'-'||TrasformazCodAteco(vAtecoBeneficiario);
    ELSE
      IF vPartitaIva IS NULL THEN
        vAtecoBeneficiario := NULL;
      ELSE
        BEGIN
          SELECT sa.COD_SETTORE||'-'||TrasformazCodAteco(aa.COD_ATECO)
          INTO   vAtecoBeneficiario
          FROM   PBANDI_D_SETTORE_ATTIVITA sa,PBANDI_D_ATTIVITA_ATECO aa,
                 PBANDI_T_SEDE s
          WHERE  s.PARTITA_IVA          = vPartitaIva
          AND    aa.COD_ATECO           IS NOT NULL
          AND    s.ID_ATTIVITA_ATECO    = aa.ID_ATTIVITA_ATECO
          AND    aa.ID_SETTORE_ATTIVITA = sa.ID_SETTORE_ATTIVITA
          AND    ROWNUM                 = 1;

        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            vAtecoBeneficiario := NULL;
        END;
      END IF;
    END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      vAtecoBeneficiario := NULL;
      vPartitaIva        := NULL;
  END;
  
---***************************
----fine MODIFICA ALG-CIPE-05
---***************************




  ------------------------------------------------------------
  -- RICAVO ID LINEA INTERVENTO DA ID_PROGETTO PER ALG-CIPE-01
  ------------------------------------------------------------
  SELECT bli.ID_LINEA_DI_INTERVENTO
  INTO   nIdLineaDiIntervento
  FROM   PBANDI_T_DOMANDA d,
         PBANDI_R_BANDO_LINEA_INTERVENT bli,
         PBANDI_T_PROGETTO PROG
  
  WHERE  prog.id_progetto                   = pIdProgetto
  AND    d.PROGR_BANDO_LINEA_INTERVENTO = bli.PROGR_BANDO_LINEA_INTERVENTO
  AND    d.id_domanda=prog.id_domanda;  


  
---*******************************
----INIZIO MODIFICA ALG-CIPE-01
---******************************

-- Il campo del tracciato TIPO_STRUMENTO_PROGRAMMAZIONE sarà dato da 
-- PBANDI_T_PROGETTO.ID_TIPO_STRUMENTO_PROGRAM -> ID_TIPO_STRUMENTO_PROGRAM. COD_STRUMENTO.


-- Mentre il campo del tracciato DESC_STRUMENTO_PROGRAMMAZIONE si valorizzerà, solo se 
--  TIPO_STRUMENTO_PROGRAMMAZIONE è diverso da ¿ASSENTE¿, concatenando: 

--	PBANDI_D_TIPO_STRUMENTO_PROGR .DESC_STRUMENTO 
--	
--PBANDI_D_LINEA_DI_INTERVENTO.DESC_LINEA*
--
--PBANDI_D_LINEA_DI_INTERVENTO.COD_IGRUE_T13_T14*


--  *: 

-- Dal progetto si risale alla domanda e, attraverso la PBANDI_R_BANDO_LINEA_INTERVENT, 
-- all¿identificativo della PBANDI_D_LINEA_DI_INTERVENTO a cui è associato il progetto. 
-- Dalla linea si risale attraverso la relazione padre-figlio al record con 
-- ID_TIPO_LINEA_INTERVENTO corrispondente alla descrizione Normativa e si prende il campo 
-- DESC_LINEA e COD_IGRUE_T13_T14.

-- Se il campo COD_IGRUE_T13_T14 non è valorizzato per il record a cui si è risaliti allora, 
-- attraverso il suo identificativo, si cerca nella tabella LineaInterventoMapping campo LineaMigrata,
-- l'identificativo corrispondente del record LineaAttuale che sicuramente avrà il campo valorizzato 
-- e quindi si procede come prima per trovare DESC_LINEA e COD_IGRUE_T13_T14.
--------------------------------------------------------------------------------------------------

  SELECT TSP.COD_STRUMENTO
  INTO   vCodStrumento
  FROM   PBANDI_T_PROGETTO p,
         PBANDI_D_TIPO_STRUMENTO_PROGR tsp
  WHERE  P.ID_PROGETTO             = pIdProgetto
  AND    P.ID_TIPO_STRUMENTO_PROGR = tsp.ID_TIPO_STRUMENTO_PROGR;


    nIdLineaInterventoPadre := nIdLineaDiIntervento;
    nIdLineaDiInterventoOb  := nIdLineaDiIntervento;
    nIdTipoLineaIntervento  := NULL;
    vNumdelibera            := NULL;
    vAnnodelibera           := NULL;
    
    loop
      exit when ((nIdLineaInterventoPadre is null) or (nIdTipoLineaIntervento = 1));
      begin
        SELECT TSP.DESC_STRUMENTO||' - '||LDI.DESC_LINEA||' - '||LDI.COD_IGRUE_T13_T14,
               ID_TIPO_LINEA_INTERVENTO,
               ID_LINEA_DI_INTERVENTO_PADRE,
               LDI.COD_IGRUE_T13_T14,
               LDI.NUM_DELIBERA,
               ldi.anno_delibera
        INTO   vDescStrumento,nIdTipoLineaIntervento,nIdLineaInterventoPadre,
               vCodIgrueT13T14,vNumdelibera,vAnnodelibera 
        FROM   PBANDI_D_LINEA_DI_INTERVENTO ldi,
               PBANDI_D_TIPO_STRUMENTO_PROGR tsp
        WHERE  ldi.ID_LINEA_DI_INTERVENTO  = nIdLineaDiInterventoOb
        AND    ldi.ID_TIPO_STRUMENTO_PROGR = tsp.ID_TIPO_STRUMENTO_PROGR(+);

        if nIdTipoLineaIntervento != 1 AND nIdLineaInterventoPadre is NOT null then
          nIdLineaDiInterventoOb := nIdLineaInterventoPadre;
        end if;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vCodIgrueT13T14 := NULL;
      END;
    END LOOP;
    
--DBMS_OUTPUT.put_line ('pIdProgetto '||pIdProgetto||'nIdLineaDiInterventoOb '||nIdLineaDiInterventoOb);


    IF vCodIgrueT13T14 IS NULL THEN
      vDescStrumento_first:=vDescStrumento;
      BEGIN
        SELECT DESC_STRUMENTO||' - '||LDI.DESC_LINEA||' - '||LDI.COD_IGRUE_T13_T14,
               ldi.num_delibera,
               ldi.anno_delibera,
               LDI.COD_IGRUE_T13_T14
        INTO   vDescStrumento,vNumdelibera,vAnnodelibera,vCodIgrueT13T14
        FROM   PBANDI_R_LINEA_INTERV_MAPPING lim,PBANDI_D_LINEA_DI_INTERVENTO ldi,
               PBANDI_D_TIPO_STRUMENTO_PROGR tsp
        WHERE  lim.ID_LINEA_DI_INTERVENTO_MIGRATA = nIdLineaDiInterventoOb
        AND    lim.ID_LINEA_DI_INTERVENTO_ATTUALE = ldi.ID_LINEA_DI_INTERVENTO
        AND    ldi.ID_TIPO_STRUMENTO_PROGR        = tsp.ID_TIPO_STRUMENTO_PROGR(+);
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          vDescStrumento :=vDescStrumento_first;
      end;
    end if;


case   vCodIgrueT13T14
  --FAS
  when '2007PI002FA011' then  nIdSistemaMittente:='PBAN-FAS' ;
  --FSE 2014-2020
  when '2014IT05SFOP013' then  nIdSistemaMittente:='PBAN-' ;
  --FSE
  when '2007IT052PO011' then  nIdSistemaMittente:='PBAN-';
  --FESR
  when '2007IT162PO011' then  nIdSistemaMittente:='PBAN-';
  --FESR-2014-2020
  when '2014IT16RFOP014' then  nIdSistemaMittente:='PBAN-';
    else
      nIdSistemaMittente:='????-';
end case;



----**************************
----FINE MODIFICA ALG-CIPE-01
----**************************



----*******************************************
---  INIZIO MODIFICA ALG-CIPE-04
---- DENOM_BENEFICIARIO
----*******************************************

--	Sulla tabella PBANDI_R_SOGGETTO_PROGETTO si cerca il record con:

--	ID_PROGETTO corrente 
--	ID_TIPO_ANAGRAFICA corrispondente alla descrizione ¿BENEFICIARIO¿
--  ID_TIPO_BENEFICIARIO diverso da quello corrispondente alla descrizione ¿BEN-ASSOCIATO¿

--	Se il campo PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO non è null, il campo del tracciato 
--  sarà valorizzato con il corrispondente PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO.
--  Se DENOMINAZIONE_ENTE_GIURIDICO è minore di 5 caratteri aggiungere al fondo: spazio + C 
--  Dove C è: 
--	Partendo da PBANDI_T_ENTE_GIURIDICO.ID_FORMA_GIURIDICA si prende il valore del campo 
--  PBANDI_D_FORMA_GIURIDICA.COD_GEFO.
--	Se COD_GEFO è null allora C è:  ( + PBANDI_D_FORMA_GIURIDICA.DESC_FORMA_GIURIDICA + )
--	Se ID_FORMA_GIURIDICA è null allora C è tanti spazi quanti sono i caratteri che mancano ad 
--  arrivare a 5

--	Se il campo PBANDI_R_SOGGETTO_PROGETTO.ID_PERSONA_FISICA non è null,  il campo del tracciato 
--  sarà valorizzato con i corrispondenti -> 
--  PBANDI_T_PERSONA_FISICA.COGNOME + ¿ ¿ +  PBANDI_T_PERSONA_FISICA.NOME.
-------------------------------------------------------------------------------------------------


  BEGIN
    SELECT ID_PERSONA_FISICA,ID_ENTE_GIURIDICO
    INTO   nIdPersonaFisica, nIdEnteGiuridico
    FROM   PBANDI_R_SOGGETTO_PROGETTO sp
    WHERE  SP.ID_PROGETTO           = pIdProgetto
    AND    sp.ID_TIPO_ANAGRAFICA    = 1  --BENEFICIARIO
    AND    sp.ID_TIPO_BENEFICIARIO != 4  --DIVERSO DA BENEFICIARIO ASSOCIATO
    AND    ROWNUM                   = 1;

    IF nIdEnteGiuridico IS NOT NULL THEN
      SELECT SUBSTR(DENOMINAZIONE_ENTE_GIURIDICO,1,100),DESC_FORMA_GIURIDICA, COD_GEFO
      INTO   vDenomBeneficiario,vDescFormaGiuridica, vCodGefo
      FROM   PBANDI_T_ENTE_GIURIDICO eg,
             PBANDI_D_FORMA_GIURIDICA fg
      WHERE  ID_ENTE_GIURIDICO     = nIdEnteGiuridico
      AND    eg.ID_FORMA_GIURIDICA = fg.ID_FORMA_GIURIDICA(+);

      IF LENGTH(vDenomBeneficiario) < 5 THEN
        IF vDescFormaGiuridica IS NULL AND vCodGefo IS NULL THEN
          vDenomBeneficiario := vDenomBeneficiario||'    ';
        ELSE
          IF vCodGefo IS NULL THEN
            vDenomBeneficiario := vDenomBeneficiario||' '||'('||vDescFormaGiuridica||')';
          ELSE
            vDenomBeneficiario := vDenomBeneficiario||' '||vCodGefo;
          END IF;
        END IF;
      END IF;
    ELSIF nIdPersonaFisica IS NOT NULL THEN
      SELECT COGNOME||' '||NOME
      INTO   vDenomBeneficiario
      FROM   PBANDI_T_PERSONA_FISICA
      WHERE  ID_PERSONA_FISICA = nIdPersonaFisica;
    ELSE
      vDenomBeneficiario := NULL;
    END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      vDenomBeneficiario := NULL;
  END;
  
  
----*******************************
----  FINE MODIFICA ALG-CIPE-04
----  CAMPO DENOM_BENEFICIARIO
-----******************************



----***************************************************
---- INIZIO MODIFICA ALG-CIPE-03
----  CAMPO DESC_INTERVENTO
----***************************************************


---Il campo verrà valorizzato con PBANDI_T_PROGETTO.TITOLO_PROGETTO.
-- Se il campo è >100 caratteri troncare ai primi 100.
-- Se il campo è < 5 caratteri allora aggiungere al fondo: spazio + C

--Dove C è: 
--	Se il campo PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO non è null
--	Partendo da PBANDI_T_ENTE_GIURIDICO.ID_FORMA_GIURIDICA si prende il valore del campo 
--  PBANDI_D_FORMA_GIURIDICA.COD_GEFO.
--	Se COD_GEFO è null allora C è:  ( + PBANDI_D_FORMA_GIURIDICA.DESC_FORMA_GIURIDICA + )
--	Se ID_FORMA_GIURIDICA è null allora C è:  ( + PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO + )
--	Se il campo PBANDI_R_SOGGETTO_PROGETTO.ID_PERSONA_FISICA non è null
--  C è:  ( + PBANDI_T_PERSONA_FISICA.COGNOME +   +  PBANDI_T_PERSONA_FISICA.NOME + )

-----------------------------------------------------------------------------------------------
  

  IF LENGTH(vTitoloProgetto) < 5 THEN
    IF nIdEnteGiuridico IS NOT NULL THEN
      IF vDescFormaGiuridica IS NULL AND vCodGefo IS NULL THEN
        vDescIntervento := SUBSTR(vTitoloProgetto||' '||'('||vDenomBeneficiario||')',1,100);
      ELSE
        IF vCodGefo IS NULL THEN
          vDescIntervento := SUBSTR((vTitoloProgetto||' '||'('||vDescFormaGiuridica||')'),1,100);
        ELSE
          vDescIntervento := SUBSTR((vTitoloProgetto||' '||vCodGefo),1,100);
        END IF;
      END IF;
    ELSIF nIdPersonaFisica IS NOT NULL THEN
      vDescIntervento := SUBSTR(vTitoloProgetto||' '||'('||vDenomBeneficiario||')',1,100);
    ELSE
      vDescIntervento := NULL;
    END IF;
  ELSE
    vDescIntervento := substr(vTitoloProgetto,1,100);
  END IF;

----*****************************
---- FINE MODIFICA ALG-CIPE-03
---- CAMPO DESC_INTERVENTO
----*****************************

-- DBMS_OUTPUT.put_line('ID_PROGETTO '||pIdProgetto);

---
--ricavo flag legge obiettivo
---      numero delibera
---      anno delibera
---

  vNumdelibera_monit  :=NULL;
  vAnnodelibera_monit :=NULL;


begin
select p_mon.flag_leg_obiettivo ,
       p_mon.num_delibera,
       p_mon.anno_delibera
into vflaglegobiettivo,
     vNumdelibera_monit,
     vAnnodelibera_monit
from 
PBANDI_T_DATI_PROGETTO_MONIT p_mon
where
p_mon.id_progetto=pIdProgetto;
end;

if
   vNumdelibera_monit  is not null and  vAnnodelibera_monit is not NULL then
 vNumdelibera :=vNumdelibera_monit;
 vAnnodelibera:=vAnnodelibera_monit;


end  if;


  INSERT INTO PRG_CLASS_CUP_PUNTUALE
  (ID_SISTEMA_MITTENTE, 
   COD_LOCALE_PROGETTO, 
   COD_CIPE_SOGGETTO_TITOLARE,
   COD_CIPE_UO_SOGG_TITOLARE, 
   COD_CIPE_USER_SOGG_TITOLARE, 
   ANNO_DECISIONE,
   NATURA, 
   TIPOLOGIA,
   SETTORE, 
   SOTTOSETTORE,
   CATEGORIA,
   TIPO_STRUMENTO_PROGRAMMAZIONE,
    NOME_STRUTTURA_INFRASTRUTTURA,
     DESC_INTERVENTO,
   PIVA_BENEFICIARIO, 
   ATECO_BENEFICIARIO, 
   FINALITA, 
   TIPO_INDIRIZZO,
   DESC_INDIRIZZO_AREA,
   CUP_CUMULATIVO,
   DESC_STRUMENTO_PROGRAMMAZIONE,
   DENOM_BENEFICIARIO,
   NUM_DELIBERA,
   anno_delibera,
   l_obiettivo)
  VALUES
  
  (nIdSistemaMittente,
   pIdProgetto,
   vCodCipe ,
   vCodCipeUo,
   vUserCipe,
   nAnnoDecisione,
   vCodNaturaCipe,
   vCodTipologiaCipe,
   vCodSettoreCipe,
   vCodSottoSettoreCipe,
   vCodCategoriaCipe,
   vCodStrumento,
   vNomeStruttura,
   vDescIntervento,
   vPartitaIva,
   vAtecoBeneficiario,
   decode(vCodNaturaCipe,'08','03', null),
   '05',
   substrb(vNomeStruttura,1,50),
   'N',
   vDescStrumento,
   vDenomBeneficiario,
   vNumdelibera,
   vAnnodelibera,
   vflaglegobiettivo);

  RETURN 0;
  
EXCEPTION
  WHEN OTHERS THEN
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'E088',sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);
 
    RETURN 1;

END ClassifCup;



FUNCTION TrasformazCodAteco(vCodAteco  PBANDI_D_ATTIVITA_ATECO.COD_ATECO%TYPE) RETURN VARCHAR2 IS
  vCodAtecoOut  PBANDI_D_ATTIVITA_ATECO.COD_ATECO%TYPE;
  vPosz1        VARCHAR2(2);
  vPosz2        VARCHAR2(2);
  vPosz3        VARCHAR2(2);
BEGIN
  IF length(vCodAteco) = 8 THEN
    vCodAtecoOut := vCodAteco;
  ELSE
    SELECT COD_ATECO
    INTO   vCodAtecoOut
    FROM   PBANDI_D_ATTIVITA_ATECO
    WHERE  COD_ATECO         like vCodAteco||'%'
    AND    length(COD_ATECO) = 8
    AND    ROWNUM            = 1;

--    PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W053','Codice Ateco = '||vCodAteco);
  END IF;

  RETURN vCodAtecoOut;
END TrasformazCodAteco;

----------------------------------------
-- FUNZIONE PAREGGIO_PROGETTI----
----------------------------------------
-- I PROGETTI PRESENTI NELLE TABELLE
-- PRG_CLASS_CUP_PUNTUALE e PRG_LOCALITA_GEO_CUP ma
-- non presenti in prg_finanziamento_cup verranno eliminati
-- da PRG_CLASS_CUP_PUNTUALE e PRG_LOCALITA_GEO_CUP
-----------------------------------------------------------

FUNCTION Pareggio_progetti RETURN NUMBER IS
ok_par  PLS_INTEGER;

CURSOR cur_fin_cup IS  select
prog_class_geo.prog_class,
fin.cod_locale_progetto prog_fin
from
(select
       class_cup.cod_locale_progetto prog_class,
       loc_geo.cod_locale_progetto prog_geo
from
       (select distinct c.cod_locale_progetto 
               from 
               prg_class_cup_puntuale c) class_cup,
       (select distinct l.cod_locale_progetto 
               from 
               prg_localita_geo_cup l) loc_geo
               where
               class_cup.cod_locale_progetto=loc_geo.cod_locale_progetto) prog_class_geo,
       (select distinct f.cod_locale_progetto 
               from 
               prg_finanziamento_cup f) fin
               where 
               prog_class_geo.prog_class  = fin.cod_locale_progetto(+) ;
           


begin

 for rec_fin_cup in cur_fin_cup loop
  
 
 if  rec_fin_cup.prog_class is not null and
     rec_fin_cup.prog_fin is null then
 dbms_output.put_line ('rec_fin_cup.prog_class '||rec_fin_cup.prog_class ||
                      ' rec_fin_cup.prog_fin '  ||  rec_fin_cup.prog_fin);
  
 delete prg_class_cup_puntuale x
        where 
        x.cod_locale_progetto=rec_fin_cup.prog_class;
        
 delete prg_localita_geo_cup y
        where 
        y.cod_locale_progetto=rec_fin_cup.prog_class;
 
 PCK_PBANDI_UTILITY_BATCH.inslogbatch(gnIdProcessoBatch,'W082',' id_PROGETTO = '||rec_fin_cup.prog_class||
 ' eliminato da PRG_CLASS_CUP_PUNTUALE e da PRG_LOCALITA_GEO 
   poiche NON presente in PRG_FINANZIAMENTO_CUP');
   
 UPDATE    Pbandi_t_Dati_Progetto_Monit d_p_m

          SET    
                 d_p_m.flag_richiesta_cup_inviata = 'N'
          WHERE  d_p_m.id_progetto        = rec_fin_cup.prog_class;  
     
 end if;    
 
 end loop; 
 commit;
 return 0;
end   Pareggio_progetti;


--------------------------------------------------
-- FUNCTION controllo_tabelle
-- RETURN 0 SE TUTTE LE TABELLE SONO POPOLATE
-- RETURN 1 SE ALMENO UNA TABELLA NON E' POPOLATA
--------------------------------------------------

FUNCTION controllo_tabelle RETURN NUMBER IS
                                            ok_tab  PLS_INTEGER;
num_class_cup_puntuale integer;
num_finanziamento_cup integer;
num_localita_geo_cup integer ;
                                            
 begin                                           
     select count(*) 
      into 
      num_class_cup_puntuale
      from PRG_CLASS_CUP_PUNTUALE  ;
      
      select count(*)  
      into
      num_finanziamento_cup
      from PRG_FINANZIAMENTO_CUP   ;
      
      select count(*)  
      into
      num_localita_geo_cup
      from Prg_Localita_Geo_Cup   ;                                            
 
if       num_class_cup_puntuale <> 0 and
         num_finanziamento_cup  <> 0 and
         num_localita_geo_cup   <> 0      then
         
         return 0;
else 
        if       num_class_cup_puntuale = 0 and
                 num_finanziamento_cup  = 0 and
                 num_localita_geo_cup   = 0      then 
        return 0;
        
        else
          return 1;
         end if; 
  
                  
end if;         

end controllo_tabelle;
END PCK_PBANDI_RICHIESTA_CUP;
/

