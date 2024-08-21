/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PCK_PBANDI_MIGRAZIONE_FINPIS AS
-----------
vNomeBatch                      PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-MFINPISMIGR';
nIdProcessoBatch                PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
-----------

PROCEDURE PRC_BONIFICA_VOCISPESA as
----------
/*

-- rispristino dati a null
UPDATE mfinpis_d_voce_di_spesa
SET FLAG_CANCELLATO = NULL,
ID_VOCE_DI_SPESA_NEW  = NULL,
ID_VOCE_DI_SPESA_PADRE_NEW  = NULL;

UPDATE mfinpis_r_bando_voce_spesa
SET    ID_VOCE_DI_SPESA_NEW = NULL;
*/
----------


 vdesc_voce_di_spesa          mfinpis_d_voce_di_spesa.desc_voce_di_spesa%type;
 vid_voce_di_spesa_NEW        mfinpis_d_voce_di_spesa.id_voce_di_spesa%type;
 vcount NUMBER;

--
Begin
------------------------------------------------
-- Imposta flag du duplicazione voci di spesa --
------------------------------------------------
  For rec in --> cursore voci duplicate
  (Select UPPER(desc_voce_di_spesa) desc_voce_di_spesa ,
          id_voce_di_spesa_padre,
          count(*)
    From mfinpis_d_voce_di_spesa
    Group by
          UPPER(DESC_VOCE_DI_SPESA),
          id_voce_di_spesa_padre
    Having count(*) > 1
   )
     Loop
       --
       vcount := 0;
       vdesc_voce_di_spesa := 'X';
       --
       For rec_aggiorna in
         (Select * from mfinpis_d_voce_di_spesa
          Where UPPER(desc_voce_di_spesa) = UPPER(rec.desc_voce_di_spesa)
          and   NVL(id_voce_di_spesa_padre,0) = NVL(rec.id_voce_di_spesa_padre,0)
          Order by id_voce_di_spesa
         )Loop



            If   UPPER(vdesc_voce_di_spesa) <> UPPER(rec.desc_voce_di_spesa) -- rottura di chiave
            and  vcount = 0 then -- primo record

                   Update mfinpis_d_voce_di_spesa
                    set    flag_cancellato = 'N',
                           id_voce_di_spesa_NEW =  rec_aggiorna.id_voce_di_spesa
                    where  id_voce_di_spesa = rec_aggiorna.id_voce_di_spesa;

                    vid_voce_di_spesa_NEW := rec_aggiorna.id_voce_di_spesa;
                    vdesc_voce_di_spesa := UPPER(rec.desc_voce_di_spesa);
                    vcount:= 1;

            Else

                   Update mfinpis_d_voce_di_spesa
                    set    flag_cancellato = 'S',
                           id_voce_di_spesa_NEW = vid_voce_di_spesa_NEW
                    where  id_voce_di_spesa = rec_aggiorna.id_voce_di_spesa;

                    vdesc_voce_di_spesa := UPPER(rec.desc_voce_di_spesa);

            End if;


          End Loop;


     End Loop;

-----------------------------------------------------------------------
-- Aggiorna id_voce_di_spesa_padre_new -- d_voci_di_spesa
-----------------------------------------------------------------------
  For rec in
    (Select * from mfinpis_d_voce_di_spesa
    )Loop

     Update  mfinpis_d_voce_di_spesa
     Set     ID_VOCE_DI_SPESA_PADRE_new = rec.ID_VOCE_DI_SPESA_new
     Where   id_voce_di_spesa_padre = rec.ID_VOCE_DI_SPESA;

     End loop;
-----------------------------------------------------------------------
--  valorizzo i record senza doppioni
------------------------------------------------------------------------
     Update mfinpis_d_voce_di_spesa
     set    flag_cancellato = 'N',
            id_voce_di_spesa_NEW =  id_voce_di_spesa,
            ID_VOCE_DI_SPESA_PADRE_NEW = ID_VOCE_DI_SPESA_PADRE
     Where  flag_cancellato is null and
     ID_VOCE_DI_SPESA_PADRE_NEW is null;

     Update mfinpis_d_voce_di_spesa
     set    flag_cancellato = 'N',
            id_voce_di_spesa_NEW =  id_voce_di_spesa
     Where  flag_cancellato is null and
     ID_VOCE_DI_SPESA_PADRE_NEW is not null;


-----------------------------------------------------------------------
-- Imposta associazione voci di spesa -- r_bando_voci_di_spesa
-----------------------------------------------------------------------
  For rec in
  (select * from
    mfinpis_d_voce_di_spesa
  )Loop

      update mfinpis_r_bando_voce_spesa
      set    id_voce_di_spesa_new = rec.id_voce_di_spesa_new
      where  id_voce_di_spesa = rec.id_voce_di_spesa;

   End loop;

--
--

   COMMIT;
--
--

END PRC_BONIFICA_VOCISPESA;

---
---
PROCEDURE PRC_LOAD_XML_FILE (p_dir  IN  VARCHAR2,
                    p_filename  IN  VARCHAR2,
                    p_tipoFile IN NUMBER) AS

-- PBANDI_RP_02_PBANDI_DB (/utl_db/skedul/pbandi_rp_01/in)

  l_bfile  BFILE := BFILENAME(p_dir, p_filename);
  l_clob   CLOB;

  l_dest_offset   INTEGER := 1;
  l_src_offset    INTEGER := 1;
  l_bfile_csid    NUMBER  := 0;
  l_lang_context  INTEGER := 0;
  l_warning       INTEGER := 0;

BEGIN
  DBMS_LOB.createtemporary (l_clob, TRUE);

  DBMS_LOB.fileopen(l_bfile, DBMS_LOB.file_readonly);
  -- loadfromfile deprecated.
  -- DBMS_LOB.loadfromfile(l_clob, l_bfile, DBMS_LOB.getlength(l_bfile));
  DBMS_LOB.loadclobfromfile (
    dest_lob      => l_clob,
    src_bfile     => l_bfile,
    amount        => DBMS_LOB.lobmaxsize,
    dest_offset   => l_dest_offset,
    src_offset    => l_src_offset,
    bfile_csid    => l_bfile_csid ,
    lang_context  => l_lang_context,
    warning       => l_warning);

  DBMS_LOB.fileclose(l_bfile);

  INSERT INTO
  pbandi_t_migrazione_finpis
   (
    id_caricamento,
    nome_file,
    file_xml,
    id_tipo_file,
    data_caricamento,
    data_migrazione
  )
  VALUES
   (
    SEQ_PBANDI_t_migrazione_finpis.NEXTVAL,
    p_filename,
    XMLTYPE.createXML(l_clob),
    p_tipoFile,
    sysdate,
    NULL
  );

  COMMIT;

  DBMS_LOB.freetemporary (l_clob);

END prc_load_xml_file;
-----------
-----------
/*FORMATTARE LE DATE
ES.
/*
select TO_DATE (TRIM(substr('2023-08-01 01:00:00.000',1,19)),'yyyy-mm-dd hh24:mi:ss' ) from dual
select TO_DATE (TRIM(substr('2023-08-01',1,19)),'yyyy-mm-dd hh24:mi:ss' ) from dual
*/

FUNCTION FNC_CARICA_XML_MFINPIS RETURN NUMBER IS

  nRet  NUMBER:=0;
  vId_Caricamento NUMBER;
  vCount number:= 0;
  vID_TIPO_CALC_AMM mfinpis_t_ammortamento_prog.Id_Tipo_Calc_Amm%TYPE;
  vID_TASSO_AMM  mfinpis_t_ammortamento_prog.Id_Tasso_Amm%TYPE;
  vID_TIPO_PERIODO_AMM  mfinpis_t_ammortamento_prog.Id_Tipo_Periodo_Ammortamento%TYPE;
  vID_FREQUENZA_AMM mfinpis_t_ammortamento_prog.frequenza%TYPE;
  vId_Banca pbandi_d_banca.cod_banca%type;

  nconta NUMBER:=0;

/*
 delete from pbandi_t_migrazione_finpis 
 delete from bandi_fondi;
 delete from mfinpis_d_linea_di_intervento;
 delete from mfinpis_t_bando;
 delete from mfinpis_r_bando_linea_inter;
 delete from mfinpis_r_bando_mod_ag_es_ban;
 delete from mfinpis_r_bando_modalita_agev;
 delete from Mfinpis_r_bando_linea_ente_com;
 delete from mfinpis_t_indirizzo;
 delete from mfinpis_t_agenzia;
 delete from mfinpis_t_estremi_bancari;
 -- delete from mfinpis_r_bando_sogg_finanziat;
 delete from mfinpis_d_voce_di_spesa;
 delete from mfinpis_r_bando_voce_spesa;
*/



BEGIN
-----
begin
-- 0. BANDI_FONDI
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'BANDI_FONDI';

 For Rec_BANDI_FONDI IN
   (
   SELECT  extractValue(t.column_value,'/bandiFondo/id_bando')      as id_bando,
           extractValue(t.column_value,'/bandiFondo/codice_fondo_finpis')   as codice_fondo_finpis
            FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/BANDI_FONDI/bandiFondo'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'BANDI_FONDI')
          )  t
   ) LOOP

   Insert into Bandi_Fondi
   (id_bando,
    codice_fondo_finpis
   )values
   (Rec_BANDI_FONDI.Id_Bando,
   '0'||Rec_BANDI_FONDI.Codice_Fondo_Finpis
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - Bandi_Fondi  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    
   
end;
------------------------------------
-- CONFIGURAZIONE BANDO -----------
------------------------------------
BEGIN
  --  1. D_LINEA_DI_INTERVENTO
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'D_LINEA_DI_INTERVENTO';

   For Rec_D_LINEA_DI_INTERVENTO IN
   (
   SELECT  extractValue(t.column_value,'/lineaintervento/@id_linea_di_intervento')      as id_linea_di_intervento,
           extractValue(t.column_value,'/lineaintervento/id_linea_di_intervento_padre')   as id_linea_di_intervento_padre,
           extractValue(t.column_value,'/lineaintervento/desc_breve_linea')         as desc_breve_linea,
           extractValue(t.column_value,'/lineaintervento/desc_linea')           as desc_linea,
           extractValue(t.column_value,'/lineaintervento/dt_inizio_validita')       as dt_inizio_validita,
           extractValue(t.column_value,'/lineaintervento/dt_fine_validita')         as dt_fine_validita,
           extractValue(t.column_value,'/lineaintervento/id_tipo_linea_intervento')     as id_tipo_linea_intervento,
           extractValue(t.column_value,'/lineaintervento/id_tipo_strumento_progr')     as id_tipo_strumento_progr,
           extractValue(t.column_value,'/lineaintervento/cod_igrue_t13_t14')       as cod_igrue_t13_t14,
           extractValue(t.column_value,'/lineaintervento/id_strumento_attuativo')     as id_strumento_attuativo,
           extractValue(t.column_value,'/lineaintervento/num_delibera')           as num_delibera,
           extractValue(t.column_value,'/lineaintervento/anno_delibera')           as anno_delibera,
           extractValue(t.column_value,'/lineaintervento/id_processo')             as id_processo,
           extractValue(t.column_value,'/lineaintervento/cod_liv_gerarchico')         as cod_liv_gerarchico,
           extractValue(t.column_value,'/lineaintervento/flag_campion_rilev ')         as flag_campion_rilev,
           extractValue(t.column_value,'/lineaintervento/flag_trasferimenti')         as flag_trasferimenti
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/D_LINEA_DI_INTERVENTO/lineaintervento'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'D_LINEA_DI_INTERVENTO')
          )  t
         -- where extractValue(t.column_value,'/lineaintervento/@id_linea_di_intervento') >= 1294

     ) LOOP

        INSERT INTO MFinpis_d_linea_di_intervento
        ( ID_LINEA_DI_INTERVENTO,
          ID_LINEA_DI_INTERVENTO_PADRE,
          DESC_BREVE_LINEA,
          DESC_LINEA,
          DT_INIZIO_VALIDITA,
          DT_FINE_VALIDITA,
          ID_TIPO_LINEA_INTERVENTO,
          ID_TIPO_STRUMENTO_PROGR,
          COD_IGRUE_T13_T14,
          ID_STRUMENTO_ATTUATIVO,
          NUM_DELIBERA,
          ANNO_DELIBERA,
          ID_PROCESSO,
          COD_LIV_GERARCHICO,
          FLAG_CAMPION_RILEV,
          FLAG_TRASFERIMENTI,
          ID_LINEA_DI_INTERVENTO_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
        )
        values
        ( Rec_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO,
          NULL, --Rec_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO_PADRE,
          'FINPIS',--Rec_D_LINEA_DI_INTERVENTO.DESC_BREVE_LINEA,
          NVL(Rec_D_LINEA_DI_INTERVENTO.DESC_LINEA,'FINPIS'),
          SYSDATE, --Rec_D_LINEA_DI_INTERVENTO.DT_INIZIO_VALIDITA,
          NULL, --Rec_D_LINEA_DI_INTERVENTO.DT_FINE_VALIDITA,
          1, -- 'NORMATIVA',
          Rec_D_LINEA_DI_INTERVENTO.ID_TIPO_STRUMENTO_PROGR,
          Rec_D_LINEA_DI_INTERVENTO.COD_IGRUE_T13_T14,
          Rec_D_LINEA_DI_INTERVENTO.ID_STRUMENTO_ATTUATIVO,
          Rec_D_LINEA_DI_INTERVENTO.NUM_DELIBERA,
          Rec_D_LINEA_DI_INTERVENTO. ANNO_DELIBERA,
          Rec_D_LINEA_DI_INTERVENTO.ID_PROCESSO,
          Rec_D_LINEA_DI_INTERVENTO.COD_LIV_GERARCHICO,
          Rec_D_LINEA_DI_INTERVENTO.FLAG_CAMPION_RILEV,
          Rec_D_LINEA_DI_INTERVENTO.FLAG_TRASFERIMENTI,
          NULL,
          vId_Caricamento,
          null-- SYSDATE
        );


       END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;

  -----

 -----
 --  2. T_BANDO

   Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_BANDO';

   FOR Rec_T_BANDO IN
     (select
           extractvalue(t.column_value,'/bando/@id_bando')as id_bando,
           extractvalue(t.column_value,'/bando/titolo_bando')   as titolo_bando,
           extractvalue(t.column_value,'/bando/dt_pubblicazione_bando')         as dt_pubblicazione_bando,
           extractvalue(t.column_value,'/bando/dt_inizio_presentaz_domande')           as dt_inizio_presentaz_domande,
           extractvalue(t.column_value,'/bando/dt_scadenza_bando')       as dt_scadenza_bando,
           extractvalue(t.column_value,'/bando/stato_bando')         as stato_bando,
           extractvalue(t.column_value,'/bando/flag_allegato')     as flag_allegato,
           extractvalue(t.column_value,'/bando/flag_graduatoria')     as flag_graduatoria,
           extractvalue(t.column_value,'/bando/dotazione_finanziaria')       as dotazione_finanziaria,
           extractvalue(t.column_value,'/bando/costo_totale_min_ammissibile')     as costo_totale_min_ammissibile,
           extractvalue(t.column_value,'/bando/flag_quietanza')           as flag_quietanza,
           extractvalue(t.column_value,'/bando/percentuale_premialita')           as percentuale_premialita,
           extractvalue(t.column_value,'/bando/importo_premialita')             as importo_premialita,
           extractvalue(t.column_value,'/bando/punteggio_premialita')         as punteggio_premialita,
           extractvalue(t.column_value,'/bando/id_utente_ins')         as id_utente_ins,
           extractvalue(t.column_value,'/bando/id_utente_agg')         as id_utente_agg,
           extractvalue(t.column_value,'/bando/id_materia')   as id_materia,
           extractvalue(t.column_value,'/bando/id_intesa')         as id_intesa,
           extractvalue(t.column_value,'/bando/id_sotto_settore_cipe')           as id_sotto_settore_cipe,
           extractvalue(t.column_value,'/bando/id_natura_cipe')       as id_natura_cipe,
           extractvalue(t.column_value,'/bando/id_tipo_operazione')         as id_tipo_operazione,
           extractvalue(t.column_value,'/bando/id_settore_cpt')     as id_settore_cpt,
           extractvalue(t.column_value,'/bando/id_tipologia_attivazione')     as id_tipologia_attivazione,
           extractvalue(t.column_value,'/bando/determina_approvazione')       as determina_approvazione,
           extractvalue(t.column_value,'/bando/dt_inserimento')     as dt_inserimento,
           extractvalue(t.column_value,'/bando/dt_aggiornamento')           as dt_aggiornamento,
           extractvalue(t.column_value,'/bando/id_linea_di_intervento')           as id_linea_di_intervento,
           extractvalue(t.column_value,'/bando/data_determina_approvazione')             as data_determina_approvazione,
           extractvalue(t.column_value,'/bando/flag_macro_voce_spesa')         as flag_macro_voce_spesa
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_BANDO/bando'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_BANDO')
          )  t
 -- WHERE NOT EXISTS
 --   (SELECT null
 --      from PBANDI_T_BANDO
 --      where id_bando = TO_NUMBER(
 --       extractValue(t.column_value,'/bando/@id_bando'))
 --     )
     )LOOP
     
      INSERT INTO MFinpis_t_bando
        (
        ID_BANDO,
        TITOLO_BANDO,
        DT_PUBBLICAZIONE_BANDO,
        DT_INIZIO_PRESENTAZ_DOMANDE,
        DT_SCADENZA_BANDO,
        STATO_BANDO,
        FLAG_ALLEGATO,
        FLAG_GRADUATORIA,
        DOTAZIONE_FINANZIARIA,
        COSTO_TOTALE_MIN_AMMISSIBILE,
        FLAG_QUIETANZA,
        PERCENTUALE_PREMIALITA,
        IMPORTO_PREMIALITA,
        PUNTEGGIO_PREMIALITA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_MATERIA,
        ID_INTESA,
        ID_SOTTO_SETTORE_CIPE,
        ID_NATURA_CIPE,
        ID_TIPO_OPERAZIONE,
        ID_SETTORE_CPT,
        ID_TIPOLOGIA_ATTIVAZIONE,
        DETERMINA_APPROVAZIONE,
        DT_INSERIMENTO,
        DT_AGGIORNAMENTO,
        ID_LINEA_DI_INTERVENTO,
        DATA_DETERMINA_APPROVAZIONE,
        FLAG_MACRO_VOCE_SPESA,
        ID_BANDO_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
        )VALUES
       (Rec_T_BANDO.ID_BANDO,
        NVL (Rec_T_BANDO.TITOLO_BANDO,'Migrazione Finpiemonte'),
        Rec_T_BANDO.DT_PUBBLICAZIONE_BANDO,
        Rec_T_BANDO.DT_INIZIO_PRESENTAZ_DOMANDE,
        Rec_T_BANDO.DT_SCADENZA_BANDO,
        Rec_T_BANDO.STATO_BANDO,
        Rec_T_BANDO.FLAG_ALLEGATO,
        Rec_T_BANDO.FLAG_GRADUATORIA,
        Rec_T_BANDO.DOTAZIONE_FINANZIARIA,
        Rec_T_BANDO.COSTO_TOTALE_MIN_AMMISSIBILE,
        Rec_T_BANDO.FLAG_QUIETANZA,
        Rec_T_BANDO.PERCENTUALE_PREMIALITA,
        Rec_T_BANDO.IMPORTO_PREMIALITA,
        Rec_T_BANDO.PUNTEGGIO_PREMIALITA,
        -14,--Rec_T_BANDO.ID_UTENTE_INS,
        Rec_T_BANDO.ID_UTENTE_AGG,
        1, --Rec_T_BANDO.ID_MATERIA,    dato provvisorio
        Rec_T_BANDO.ID_INTESA,
        Rec_T_BANDO.ID_SOTTO_SETTORE_CIPE,
        Rec_T_BANDO.ID_NATURA_CIPE,
        1, --Rec_T_BANDO.ID_TIPO_OPERAZIONE,    dato provvisorio
        Rec_T_BANDO.ID_SETTORE_CPT,
        Rec_T_BANDO.ID_TIPOLOGIA_ATTIVAZIONE,
        Rec_T_BANDO.DETERMINA_APPROVAZIONE,
        SYSDATE, --Rec_T_BANDO.DT_INSERIMENTO,
        Rec_T_BANDO.DT_AGGIORNAMENTO,
        Rec_T_BANDO.ID_LINEA_DI_INTERVENTO,
        Rec_T_BANDO.DATA_DETERMINA_APPROVAZIONE,
        Rec_T_BANDO.FLAG_MACRO_VOCE_SPESA,
        NULL,
        vId_Caricamento,
        null--SYSDATE
        );

       END LOOP;


   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
  -----


-----
 --  3. R_BANDO_LINEA_INTERVENT

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_LINEA_INTERVENT';


     For Rec_r_bando_linea_inter IN
      (Select
           extractvalue(t.column_value,'/linea/@progr_bando_linea_intervento')as progr_bando_linea_intervento,
           extractvalue(t.column_value,'/linea/id_linea_di_intervento')   as id_linea_di_intervento,
           extractvalue(t.column_value,'/linea/id_bando')         as id_bando,
           --extractvalue(t.column_value,'/linea/fondo_finpis')         as fondo_finpis,
           extractvalue(t.column_value,'/linea/id_utente_ins')           as id_utente_ins,
           extractvalue(t.column_value,'/linea/id_utente_agg')       as id_utente_agg,
           extractvalue(t.column_value,'/linea/id_area_scientifica')         as id_area_scientifica,
           extractvalue(t.column_value,'/linea/nome_bando_linea')     as nome_bando_linea,
           extractvalue(t.column_value,'/linea/id_definizione_processo')     as id_definizione_processo,
           extractvalue(t.column_value,'/linea/id_unita_organizzativa')       as id_unita_organizzativa,
           extractvalue(t.column_value,'/linea/mesi_durata_da_dt_concessione')     as mesi_durata_da_dt_concessione,
           extractvalue(t.column_value,'/linea/id_obiettivo_specif_qsn')           as id_obiettivo_specif_qsn,
           extractvalue(t.column_value,'/linea/id_categoria_cipe')           as id_categoria_cipe,
           extractvalue(t.column_value,'/linea/id_tipologia_cipe')             as id_tipologia_cipe,
           extractvalue(t.column_value,'/linea/flag_schedin')         as flag_schedin,
           extractvalue(t.column_value,'/linea/id_processo')         as id_processo,
           extractvalue(t.column_value,'/linea/flag_sif')         as flag_sif,
           extractvalue(t.column_value,'/linea/progr_bando_linea_interv_sif')   as progr_bando_linea_interv_sif,
           extractvalue(t.column_value,'/linea/id_tipo_localizzazione')         as id_tipo_localizzazione,
           extractvalue(t.column_value,'/linea/id_livello_istituzione')           as id_livello_istituzione,
           extractvalue(t.column_value,'/linea/id_progetto_complesso')       as id_progetto_complesso,
           extractvalue(t.column_value,'/linea/id_classificazione_met')         as id_classificazione_met,
           extractvalue(t.column_value,'/linea/flag_fondo_di_fondi')     as flag_fondo_di_fondi,
           extractvalue(t.column_value,'/linea/id_classificazione_ra')     as id_classificazione_ra,
           extractvalue(t.column_value,'/linea/cod_aiuto_rna')       as cod_aiuto_rna,
           extractvalue(t.column_value,'/linea/id_linea_azione')     as id_linea_azione
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_LINEA_INTERVENTO/linea'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_LINEA_INTERVENT')
          )  t
 -- WHERE NOT EXISTS
 --   (SELECT null
 --      from pbandi_r_bando_linea_intervent
 --      where PROGR_BANDO_LINEA_INTERVENTO =
 --       extractValue(t.column_value,'/linea/@progr_bando_linea_intervento')
 --    )
      )LOOP





      INSERT INTO MFinpis_r_bando_linea_inter
        (
        ID_LINEA_DI_INTERVENTO,
        ID_BANDO,
       -- fondo_finpis,
        PROGR_BANDO_LINEA_INTERVENTO,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_AREA_SCIENTIFICA,
        NOME_BANDO_LINEA,
        ID_DEFINIZIONE_PROCESSO,
        ID_UNITA_ORGANIZZATIVA,
        MESI_DURATA_DA_DT_CONCESSIONE,
        ID_OBIETTIVO_SPECIF_QSN,
        ID_CATEGORIA_CIPE,
        ID_TIPOLOGIA_CIPE,
        FLAG_SCHEDIN,
        ID_PROCESSO,
        FLAG_SIF,
        PROGR_BANDO_LINEA_INTERV_SIF,
        ID_TIPO_LOCALIZZAZIONE,
        ID_LIVELLO_ISTITUZIONE,
        ID_PROGETTO_COMPLESSO,
        ID_CLASSIFICAZIONE_MET,
        FLAG_FONDO_DI_FONDI,
        ID_CLASSIFICAZIONE_RA,
        COD_AIUTO_RNA,
        ID_LINEA_AZIONE,
        FLAG_MONITORAGGIO_TEMPI,
        PROGR_BANDO_LINEA_INTERV_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
        )
        VALUES
        (
        Rec_r_bando_linea_inter.ID_LINEA_DI_INTERVENTO,
        Rec_r_bando_linea_inter.ID_BANDO,
       -- Rec_r_bando_linea_inter.fondo_finpis,
        Rec_r_bando_linea_inter.PROGR_BANDO_LINEA_INTERVENTO,
        -14, --Rec_r_bando_linea_inter.ID_UTENTE_INS,
        Rec_r_bando_linea_inter.ID_UTENTE_AGG,
        Rec_r_bando_linea_inter.ID_AREA_SCIENTIFICA,
        Rec_r_bando_linea_inter.NOME_BANDO_LINEA,
        Rec_r_bando_linea_inter.ID_DEFINIZIONE_PROCESSO,
        Rec_r_bando_linea_inter.ID_UNITA_ORGANIZZATIVA,
        Rec_r_bando_linea_inter.MESI_DURATA_DA_DT_CONCESSIONE,
        Rec_r_bando_linea_inter.ID_OBIETTIVO_SPECIF_QSN,
        Rec_r_bando_linea_inter.ID_CATEGORIA_CIPE,
        Rec_r_bando_linea_inter.ID_TIPOLOGIA_CIPE,
        Rec_r_bando_linea_inter.FLAG_SCHEDIN,
        Rec_r_bando_linea_inter.ID_PROCESSO,
        Rec_r_bando_linea_inter.FLAG_SIF,
        Rec_r_bando_linea_inter.PROGR_BANDO_LINEA_INTERV_SIF,
        Rec_r_bando_linea_inter.ID_TIPO_LOCALIZZAZIONE,
        Rec_r_bando_linea_inter.ID_LIVELLO_ISTITUZIONE,
        Rec_r_bando_linea_inter.ID_PROGETTO_COMPLESSO,
        Rec_r_bando_linea_inter.ID_CLASSIFICAZIONE_MET,
        Rec_r_bando_linea_inter.FLAG_FONDO_DI_FONDI,
        Rec_r_bando_linea_inter.ID_CLASSIFICAZIONE_RA,
        Rec_r_bando_linea_inter.COD_AIUTO_RNA,
        Rec_r_bando_linea_inter.ID_LINEA_AZIONE,
        NULL,-- FLAG_MONITORAGGIO_TEMPI assente su XML
        NULL,
        vId_Caricamento,
        null--SYSDATE
        );

      END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
  -----

 --  4. R_BANDO_MOD_AG_ESTR_BAN
  -- DA MODIFICARE PER AGGIUNGERE LA TIPOLOGIA CONTO

   Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_MOD_AG_ESTR_BAN';


      For Rec_R_BANDO_MOD_AG_ESTR_BAN IN
      (select     extractvalue(t.column_value,'/bandoagevbanca/@id_bando_mod_ag_estr_ban')as id_bando_mod_ag_estr_ban,
           extractvalue(t.column_value,'/bandoagevbanca/id_modalita_agevolazione')   as id_modalita_agevolazione,
           extractvalue(t.column_value,'/bandoagevbanca/id_bando')         as id_bando,
           extractvalue(t.column_value,'/bandoagevbanca/id_estremi_bancari')           as id_estremi_bancari,
           extractvalue(t.column_value,'/bandoagevbanca/codice_fondo_finpis')           as codice_fondo_finpis,
           extractvalue(t.column_value,'/bandoagevbanca/moltiplicatore')       as moltiplicatore,
           extractvalue(t.column_value,'/bandoagevbanca/dt_inizio_validita')         as dt_inizio_validita,
           extractvalue(t.column_value,'/bandoagevbanca/dt_fine_validita')     as dt_fine_validita,
           extractvalue(t.column_value,'/bandoagevbanca/id_utente_ins')     as id_utente_ins,
           extractvalue(t.column_value,'/bandoagevbanca/id_utente_agg')       as id_utente_agg
        FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_MOD_AG_ESTR_BAN/bandoagevbanca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_MOD_AG_ESTR_BAN')
          )  t
 -- WHERE NOT EXISTS
 --   (SELECT null
 --      from PBANDI_R_BANDO_MOD_AG_ESTR_BAN
 --      where ID_BANDO_MOD_AG_ESTR_BAN =
 --       extractValue(t.column_value,'/bandoagevbanca/@id_bando_mod_ag_estr_ban')
 --    )
      )LOOP

      INSERT INTO MFinpis_r_bando_mod_ag_es_ban
      (
      ID_BANDO_MOD_AG_ESTR_BAN,
      ID_MODALITA_AGEVOLAZIONE,
      ID_BANDO,
      ID_ESTREMI_BANCARI,
      CODICE_FONDO_FINPIS,
      MOLTIPLICATORE,
      --TIPOLOGIA_CONTO,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_BANDO_MOD_AG_ESTR_BAN_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
      )VALUES
     (
     Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_BANDO_MOD_AG_ESTR_BAN,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_MODALITA_AGEVOLAZIONE,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_BANDO,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_ESTREMI_BANCARI,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.Codice_Fondo_Finpis,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.MOLTIPLICATORE,
     --Rec_R_BANDO_MOD_AG_ESTR_BAN.TIPOLOGIA_CONTO,
     SYSDATE, --Rec_R_BANDO_MOD_AG_ESTR_BAN.DT_INIZIO_VALIDITA,
     NULL, --Rec_R_BANDO_MOD_AG_ESTR_BAN.DT_FINE_VALIDITA,
     -14,--Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_UTENTE_INS,
     Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_UTENTE_AGG,
     NULL,
     vId_Caricamento,
     null--SYSDATE
      );

      END LOOP;


   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
-----

-----
--  5. R_BANDO_MODALITA_AGEVOL

   Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_MODALITA_AGEVOL';


   For Rec_R_BANDO_MODALITA_AGEVOL IN
     (select     extractvalue(t.column_value,'/modalita/@id_modalita_agevolazione')as id_modalita_agevolazione,
           extractvalue(t.column_value,'/modalita/id_bando')   as id_bando,
           extractvalue(t.column_value,'/modalita/cod_banca_finp')         as cod_banca_finp,
           extractvalue(t.column_value,'/modalita/percentuale_importo_agevolato')           as percentuale_importo_agevolato,
           extractvalue(t.column_value,'/modalita/massimo_importo_agevolato')       as massimo_importo_agevolato,
           extractvalue(t.column_value,'/modalita/id_utente_ins')         as id_utente_ins,
           extractvalue(t.column_value,'/modalita/id_utente_agg')     as id_utente_agg,
           extractvalue(t.column_value,'/modalita/flag_liquidazione')     as flag_liquidazione,
           extractvalue(t.column_value,'/modalita/minimo_importo_agevolato')       as minimo_importo_agevolato
       FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_MODALITA_AGEVOL/modalita'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_MODALITA_AGEVOL')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from PBANDI_R_BANDO_MODALITA_AGEVOL
--       where (id_modalita_agevolazione,id_bando) IN
--        ((extractValue(t.column_value,'/modalita/@id_modalita_agevolazione'),
--        (extractValue(t.column_value,'/modalita/id_bando')))
--        )
--    )
     )LOOP


     INSERT INTO  MFinpis_r_bando_modalita_agev
     (ID_MODALITA_AGEVOLAZIONE,
      ID_BANDO,
      -- cod_banca_finp,
      PERCENTUALE_IMPORTO_AGEVOLATO,
      MASSIMO_IMPORTO_AGEVOLATO,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      FLAG_LIQUIDAZIONE,
      MINIMO_IMPORTO_AGEVOLATO,
      ID_MODALITA_AGEVOLAZIONE_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
     )
     VALUES
     (
      Rec_R_BANDO_MODALITA_AGEVOL.ID_MODALITA_AGEVOLAZIONE,
      Rec_R_BANDO_MODALITA_AGEVOL.ID_BANDO,
      -- null
      Rec_R_BANDO_MODALITA_AGEVOL.PERCENTUALE_IMPORTO_AGEVOLATO,
      Rec_R_BANDO_MODALITA_AGEVOL.MASSIMO_IMPORTO_AGEVOLATO,
      -14, --Rec_R_BANDO_MODALITA_AGEVOL.ID_UTENTE_INS,
      Rec_R_BANDO_MODALITA_AGEVOL.ID_UTENTE_AGG,
      Rec_R_BANDO_MODALITA_AGEVOL.FLAG_LIQUIDAZIONE,
      Rec_R_BANDO_MODALITA_AGEVOL.MINIMO_IMPORTO_AGEVOLATO,
      NULL,
      vId_Caricamento,
      null--SYSDATE
      );

     END LOOP;


   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
  -----
-----
--  6. R_BANDO_LINEA_ENTE_COMP

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_LINEA_ENTE_COMP';


    For Rec_R_BANDO_LINEA_ENTE_COMP IN
      (select
           extractvalue(t.column_value,'/bandolineaente/@PROGR_BANDO_LINEA_ENTE_COMP')as progr_bando_linea_ente_comp,
           extractvalue(t.column_value,'/bandolineaente/DT_FINE_VALIDITA')   as dt_fine_validita,
           extractvalue(t.column_value,'/bandolineaente/ID_RUOLO_ENTE_COMPETENZA')         as id_ruolo_ente_competenza,
           extractvalue(t.column_value,'/bandolineaente/ID_UTENTE_INS')           as id_utente_ins,
           extractvalue(t.column_value,'/bandolineaente/ID_UTENTE_AGG')       as id_utente_agg,
           extractvalue(t.column_value,'/bandolineaente/ID_ENTE_COMPETENZA')         as id_ente_competenza,
           extractvalue(t.column_value,'/bandolineaente/PROGR_BANDO_LINEA_INTERVENTO')     as progr_bando_linea_intervento,
           extractvalue(t.column_value,'/bandolineaente/PAROLA_CHIAVE')     as parola_chiave,
           extractvalue(t.column_value,'/bandolineaente/FEEDBACK_ACTA')       as feedback_acta,
           extractvalue(t.column_value,'/bandolineaente/INDIRIZZO_MAIL_PEC')       as indirizzo_mail_pec,
           extractvalue(t.column_value,'/bandolineaente/CONSERVAZIONE_CORRENTE')       as conservazione_corrente,
           extractvalue(t.column_value,'/bandolineaente/CONSERVAZIONE_GENERALE')       as conservazione_generale
      FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_LINEA_ENTE_COMP/bandolineaente'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_LINEA_ENTE_COMP')
          )  t
--    WHERE NOT EXISTS
--      (SELECT null
--       from PBANDI_R_BANDO_LINEA_ENTE_COMP
--       where PROGR_BANDO_LINEA_ENTE_COMP =
--        extractValue(t.column_value,'/bandolineaente/@PROGR_BANDO_LINEA_ENTE_COMP')
--     )
      ) LOOP


      Insert Into Mfinpis_r_bando_linea_ente_com
      (
      DT_FINE_VALIDITA,
      ID_RUOLO_ENTE_COMPETENZA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_ENTE_COMPETENZA,
      PROGR_BANDO_LINEA_INTERVENTO,
      PROGR_BANDO_LINEA_ENTE_COMP,
      PAROLA_CHIAVE,
      FEEDBACK_ACTA,
      INDIRIZZO_MAIL_PEC,
      CONSERVAZIONE_CORRENTE,
      CONSERVAZIONE_GENERALE,
      PROGR_BANDO_LINEA_ENTE_C_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
      )VALUES
      (
      NULL,-- Rec_R_BANDO_LINEA_ENTE_COMP.DT_FINE_VALIDITA,
      Rec_R_BANDO_LINEA_ENTE_COMP.ID_RUOLO_ENTE_COMPETENZA,
      -14,--Rec_R_BANDO_LINEA_ENTE_COMP.ID_UTENTE_INS,
      NULL,--Rec_R_BANDO_LINEA_ENTE_COMP.ID_UTENTE_AGG,
      Rec_R_BANDO_LINEA_ENTE_COMP.ID_ENTE_COMPETENZA,
      Rec_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO,
      Rec_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_ENTE_COMP,
      Rec_R_BANDO_LINEA_ENTE_COMP.PAROLA_CHIAVE,
      Rec_R_BANDO_LINEA_ENTE_COMP.FEEDBACK_ACTA,
      Rec_R_BANDO_LINEA_ENTE_COMP.INDIRIZZO_MAIL_PEC,
      Rec_R_BANDO_LINEA_ENTE_COMP.CONSERVAZIONE_CORRENTE,
      Rec_R_BANDO_LINEA_ENTE_COMP.CONSERVAZIONE_GENERALE,
      NULL,
      vId_Caricamento,
      null--SYSDATE
      );

     END LOOP;


   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
-----
-----

 --  7. T_INDIRIZZI

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_INDIRIZZI';

   For Rec_T_INDIRIZZO IN
     (select     extractvalue(t.column_value,'/indirizzo/@id_indirizzo')as id_indirizzo,
           extractvalue(t.column_value,'/indirizzo/desc_indirizzo')   as desc_indirizzo,
           extractvalue(t.column_value,'/indirizzo/cap')         as cap,
           extractvalue(t.column_value,'/indirizzo/id_nazione')           as id_nazione,
           extractvalue(t.column_value,'/indirizzo/id_comune')       as id_comune,
           extractvalue(t.column_value,'/indirizzo/id_provincia')         as id_provincia,
           extractvalue(t.column_value,'/indirizzo/id_via_l2')     as id_via_l2,
           extractvalue(t.column_value,'/indirizzo/civico')     as civico,
           extractvalue(t.column_value,'/indirizzo/bis')       as bis,
           extractvalue(t.column_value,'/indirizzo/dt_inizio_validita')       as dt_inizio_validita,
           extractvalue(t.column_value,'/indirizzo/dt_fine_validita')       as dt_fine_validita,
           extractvalue(t.column_value,'/indirizzo/id_utente_ins')       as id_utente_ins,
           extractvalue(t.column_value,'/indirizzo/id_utente_agg')       as id_utente_agg,
           extractvalue(t.column_value,'/indirizzo/id_regione')       as id_regione,
           extractvalue(t.column_value,'/indirizzo/id_fonte_indirizzo')       as id_fonte_indirizzo,
           extractvalue(t.column_value,'/indirizzo/id_geo_riferimento')       as id_geo_riferimento,
           extractvalue(t.column_value,'/indirizzo/id_comune_estero')       as id_comune_estero
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_Indirizzo/indirizzo'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_INDIRIZZI')
          )  t
 -- WHERE NOT EXISTS
 --   (SELECT null
 --      from pbandi_t_indirizzo
 --      where id_indirizzo =
 --       extractValue(t.column_value,'/indirizzo/@id_indirizzo')
 --    )
     ) LOOP



   Insert into MFinpis_t_indirizzo
   (
    ID_INDIRIZZO,
    DESC_INDIRIZZO,
    CAP,
    ID_NAZIONE,
    ID_COMUNE,
    ID_PROVINCIA,
    ID_VIA_L2,
    CIVICO,
    BIS,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    ID_REGIONE,
    ID_FONTE_INDIRIZZO,
    ID_GEO_RIFERIMENTO,
    ID_COMUNE_ESTERO,
    ID_INDIRIZZO_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
    )VALUES
    (Rec_T_INDIRIZZO.ID_INDIRIZZO,
    Rec_T_INDIRIZZO.DESC_INDIRIZZO,
    Rec_T_INDIRIZZO.CAP,
    Rec_T_INDIRIZZO.ID_NAZIONE,
    Rec_T_INDIRIZZO.ID_COMUNE,
    Rec_T_INDIRIZZO.ID_PROVINCIA,
    Rec_T_INDIRIZZO.ID_VIA_L2,
    Rec_T_INDIRIZZO.CIVICO,
    Rec_T_INDIRIZZO.BIS,
    SYSDATE,--Rec_T_INDIRIZZO.DT_INIZIO_VALIDITA,
    NULL, --Rec_T_INDIRIZZO.DT_FINE_VALIDITA,
    -14, --Rec_T_INDIRIZZO.ID_UTENTE_INS,
    Rec_T_INDIRIZZO.ID_UTENTE_AGG,
    Rec_T_INDIRIZZO.ID_REGIONE,
    Rec_T_INDIRIZZO.ID_FONTE_INDIRIZZO,
    Rec_T_INDIRIZZO.ID_GEO_RIFERIMENTO,
    Rec_T_INDIRIZZO.ID_COMUNE_ESTERO,
    NULL,
    vId_Caricamento,
    null--SYSDATE
    );

    END LOOP;


   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
  -----
-----
--  8. T_AGENZIA

  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_AGENZIA';


   For Rec_T_agenzia IN
    (select
           extractvalue(t.column_value,'/agenzia/@id_agenzia')as id_agenzia,
           extractvalue(t.column_value,'/agenzia/cod_agenzia')as cod_agenzia,
           extractvalue(t.column_value,'/agenzia/desc_agenzia')as desc_agenzia,
           extractvalue(t.column_value,'/agenzia/id_utente_ins')as id_utente_ins,
           extractvalue(t.column_value,'/agenzia/id_utente_agg')as id_utente_agg,
           extractvalue(t.column_value,'/agenzia/id_banca')as id_banca,
           extractvalue(t.column_value,'/agenzia/id_indirizzo')as id_indirizzo,
           extractvalue(t.column_value,'/agenzia/dt_inizio_validita')as dt_inizio_validita,
           extractvalue(t.column_value,'/agenzia/dt_fine_validita')as dt_fine_validita
       FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_agenzia/agenzia'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_AGENZIA')
          )  t
--    WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_t_agenzia
--       where id_agenzia =
--        extractValue(t.column_value,'/agenzia/@id_agenzia')
--     )
    )
    LOOP

    Insert Into MFinpis_t_agenzia
   (
    ID_AGENZIA,
    COD_AGENZIA,
    DESC_AGENZIA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    ID_BANCA,
    ID_INDIRIZZO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_AGENZIA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
   ) VALUES
   (Rec_T_agenzia.ID_AGENZIA,
    Rec_T_agenzia.COD_AGENZIA,
    Rec_T_agenzia.DESC_AGENZIA,
    -14, --Rec_T_agenzia.ID_UTENTE_INS,
    Rec_T_agenzia.ID_UTENTE_AGG,
    Rec_T_agenzia.ID_BANCA,
    Rec_T_agenzia.ID_INDIRIZZO,
    SYSDATE,--Rec_T_agenzia.DT_INIZIO_VALIDITA,
    NULL,   --Rec_T_agenzia.DT_FINE_VALIDITA,
    NULL,
    vId_Caricamento,
    null--SYSDATE
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
-----
-----
--  9. T_ESTREMI_BANCARI

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_ESTREMI_BANCARI';


   For Rec_T_ESTREMI_BANCARI IN
     (select     extractvalue(t.column_value,'/banca/@id_estremi_bancari')as id_estremi_bancari,
            extractvalue(t.column_value,'/banca/numero_conto')as numero_conto,
            extractvalue(t.column_value,'/banca/CIN')as cin,
            extractvalue(t.column_value,'/banca/ABI')as abi,
            extractvalue(t.column_value,'/banca/CAB')as cab,
            extractvalue(t.column_value,'/banca/IBAN')as iban,
            extractvalue(t.column_value,'/banca/BIC')as bic,
            extractvalue(t.column_value,'/banca/id_utente_ins')as id_utente_ins,
            extractvalue(t.column_value,'/banca/id_utente_agg')as id_utente_agg,
            extractvalue(t.column_value,'/banca/id_agenzia')as id_agenzia,
            extractvalue(t.column_value,'/banca/id_banca')as id_banca,
            extractvalue(t.column_value,'/banca/dt_inizio_validita')as dt_inizio_validita,
            extractvalue(t.column_value,'/banca/dt_fine_validita')as dt_fine_validita
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_Estremi_Bancari/banca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_ESTREMI_BANCARI')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_T_ESTREMI_BANCARI
--       where id_estremi_bancari =
--        extractValue(t.column_value,'/banca/@id_estremi_bancari')
--     )
     )LOOP

     --dbms_output.put_line(Rec_T_ESTREMI_BANCARI.ID_AGENZIA);


     Insert Into MFinpis_t_estremi_bancari
      (
      ID_ESTREMI_BANCARI,
      NUMERO_CONTO,
      CIN,
      ABI,
      CAB,
      IBAN,
      BIC,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_AGENZIA,
      ID_BANCA,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_ESTREMI_BANCARI_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
      )values
      (Rec_T_ESTREMI_BANCARI.ID_ESTREMI_BANCARI,
      Rec_T_ESTREMI_BANCARI.NUMERO_CONTO,
      Rec_T_ESTREMI_BANCARI.CIN,
      Rec_T_ESTREMI_BANCARI.ABI,
      Rec_T_ESTREMI_BANCARI.CAB,
      Rec_T_ESTREMI_BANCARI.IBAN,
      Rec_T_ESTREMI_BANCARI.BIC,
      -14, --Rec_T_ESTREMI_BANCARI.ID_UTENTE_INS,
      NULL,--Rec_T_ESTREMI_BANCARI.ID_UTENTE_AGG,
      Rec_T_ESTREMI_BANCARI.ID_AGENZIA,
      substr(Rec_T_ESTREMI_BANCARI.ID_BANCA,1,4),
      SYSDATE,--Rec_T_ESTREMI_BANCARI.DT_INIZIO_VALIDITA,
      NULL,--Rec_T_ESTREMI_BANCARI.DT_FINE_VALIDITA,
      NULL,
      vId_Caricamento,
      NULL
      );

      END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
  -----


/* -- FILE NON PIU' GESTITO

-----
   --  10. R_BANDO_SOGG_FINANZIAT

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_SOGG_FINANZIAT';



For Rec_R_BANDO_SOGG_FINANZIAT IN
     (select     extractvalue(t.column_value,'/bando/@ID_BANDO')as id_bando,
                 extractvalue(t.column_value,'/bando/ID_SOGGETTO_FINANZIATORE')as id_soggetto_finanziatore,
                 extractvalue(t.column_value,'/bando/PERCENTUALE_QUOTA_SOGG_FINANZ')as PERCENTUALE_QUOTA_SOGG_FINANZ,
                 extractvalue(t.column_value,'/bando/ID_UTENTE_INS')as id_utente_ins,
                 extractvalue(t.column_value,'/bando/ID_UTENTE_AGG')as id_utente_agg,
                 extractvalue(t.column_value,'/bando/PERC_QUOTA_CONTRIBUTO_PUB')as PERC_QUOTA_CONTRIBUTO_PUB
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_SOGG_FINANZIAT/bando'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_SOGG_FINANZIAT')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_r_bando_sogg_finanziat
--       where (id_bando, id_soggetto_finanziatore) IN
--        ((extractValue(t.column_value,'/bando/@ID_BANDO'),
--        (extractValue(t.column_value,'/bando/ID_SOGGETTO_FINANZIATORE')))
--        )
--     )
     )LOOP



     Insert into mfinpis_r_bando_sogg_finanziat
     (ID_BANDO,
      ID_SOGGETTO_FINANZIATORE,
      PERCENTUALE_QUOTA_SOGG_FINANZ,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PERC_QUOTA_CONTRIBUTO_PUB,
      ID_SOGGETTO_FINANZIATORE_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
     ) VALUES
     (Rec_R_BANDO_SOGG_FINANZIAT.ID_BANDO,
      Rec_R_BANDO_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE,
      Rec_R_BANDO_SOGG_FINANZIAT.PERCENTUALE_QUOTA_SOGG_FINANZ,
      Rec_R_BANDO_SOGG_FINANZIAT.ID_UTENTE_INS,
      Rec_R_BANDO_SOGG_FINANZIAT.ID_UTENTE_AGG,
      Rec_R_BANDO_SOGG_FINANZIAT.PERC_QUOTA_CONTRIBUTO_PUB,
      NULL,
      vId_caricamento,
      null--sysdate
     );

      END LOOP;

 Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;

*/

-- 11. D_VOCE_DI_SPESA

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'D_VOCE_DI_SPESA';


   For Rec_d_voce_di_spesa IN
   (
   Select
           extractvalue(t.column_value,'/voce/@ID_VOCE_DI_SPESA')as id_voce_di_spesa,
           extractvalue(t.column_value,'/voce/ID_VOCE_DI_SPESA_PADRE')   as id_voce_di_spesa_padre,
           extractvalue(t.column_value,'/voce/DESC_VOCE_DI_SPESA')         as desc_voce_di_spesa,
           extractvalue(t.column_value,'/voce/DT_INIZIO_VALIDITA')         as dt_inizio_validita,
           extractvalue(t.column_value,'/voce/DT_FINE_VALIDITA')           as dt_fine_validita,
           extractvalue(t.column_value,'/voce/COD_TIPO_VOCE_DI_SPESA')       as cod_tipo_voce_di_spesa,
           extractvalue(t.column_value,'/voce/ID_VOCE_DI_SPESA_MONIT')         as id_voce_di_spesa_monit,
           extractvalue(t.column_value,'/voce/ID_TIPOLOGIA_VOCE_DI_SPESA')     as id_tipologia_voce_di_spesa,
           extractvalue(t.column_value,'/voce/FLAG_EDIT')     as flag_edit
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/D_VOCE_DI_SPESA/voce'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'D_VOCE_DI_SPESA')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_d_voce_di_spesa
--       where ID_VOCE_DI_SPESA  =
--        extractValue(t.column_value,'/voce/@ID_VOCE_DI_SPESA')
--     )
  )LOOP

   INSERT INTO Mfinpis_d_Voce_Di_Spesa
   (ID_VOCE_DI_SPESA,
    ID_VOCE_DI_SPESA_PADRE,
    DESC_VOCE_DI_SPESA,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    COD_TIPO_VOCE_DI_SPESA,
    ID_VOCE_DI_SPESA_MONIT,
    ID_TIPOLOGIA_VOCE_DI_SPESA,
    FLAG_EDIT,
    ID_VOCE_DI_SPESA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
   )VALUES
   (Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA,
    Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA_PADRE,
    NVL(Rec_d_voce_di_spesa.DESC_VOCE_DI_SPESA,'FINPIS'),
    sysdate,--Rec_d_voce_di_spesa.DT_INIZIO_VALIDITA,
    null,--Rec_d_voce_di_spesa.DT_FINE_VALIDITA,
    Rec_d_voce_di_spesa.COD_TIPO_VOCE_DI_SPESA,
    Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA_MONIT,
    Rec_d_voce_di_spesa.ID_TIPOLOGIA_VOCE_DI_SPESA,
    Rec_d_voce_di_spesa.FLAG_EDIT,
    NULL,
    vID_CARICAMENTO,
    null--Sysdate
   );


   END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;


--  12. R_BANDO_VOCE_SPESA

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_BANDO_VOCE_SPESA';

 For Rec_R_BANDO_VOCE_SPESA IN
   (
   Select
           extractvalue(t.column_value,'/bando/@ID_BANDO')as id_bando,
           extractvalue(t.column_value,'/bando/ID_VOCE_DI_SPESA')   as id_voce_di_spesa,
           extractvalue(t.column_value,'/bando/PERCENTUALE_IMP_AMMISSIBILE')         as percentuale_imp_ammissibile,
           extractvalue(t.column_value,'/bando/MASSIMO_IMPORTO_AMMISSIBILE')         as massimo_importo_ammissibile,
           extractvalue(t.column_value,'/bando/ID_UTENTE_INS')           as id_utente_ins,
           extractvalue(t.column_value,'/bando/ID_UTENTE_AGG')       as id_utente_agg,
           extractvalue(t.column_value,'/bando/PROGR_ORDINAMENTO')         as progr_ordinamento
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_BANDO_VOCE_SPESA/bando'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_BANDO_VOCE_SPESA')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_r_bando_voce_spesa
--       where (id_voce_di_spesa,id_bando) IN
--        ((extractValue(t.column_value,'/bando/ID_VOCE_DI_SPESA'),
--        (extractValue(t.column_value,'/bando/@ID_BANDO')))
--        )
--     )
  )LOOP



   Insert into Mfinpis_r_Bando_Voce_Spesa
   (ID_BANDO,
      ID_VOCE_DI_SPESA,
      PERCENTUALE_IMP_AMMISSIBILE,
      MASSIMO_IMPORTO_AMMISSIBILE,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PROGR_ORDINAMENTO,
      ID_BANDO_PBAN,
      ID_VOCE_DI_SPESA_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
   )VALUES
   (  Rec_R_BANDO_VOCE_SPESA.ID_BANDO,
      Rec_R_BANDO_VOCE_SPESA.ID_VOCE_DI_SPESA,
      Rec_R_BANDO_VOCE_SPESA.PERCENTUALE_IMP_AMMISSIBILE,
      Rec_R_BANDO_VOCE_SPESA.MASSIMO_IMPORTO_AMMISSIBILE,
      -14, --Rec_R_BANDO_VOCE_SPESA.ID_UTENTE_INS,
      Rec_R_BANDO_VOCE_SPESA.ID_UTENTE_AGG,
      Rec_R_BANDO_VOCE_SPESA.PROGR_ORDINAMENTO,
      NULL,
      NULL,
      vID_CARICAMENTO,
      null--sysdate
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
 -------------

 -- procedura per la bonifica delle voci di spesa
 -----------------------
 PRC_BONIFICA_VOCISPESA; 
 -----------------------
  
  COMMIT;
  
-----------------------------
-- FINE CONFIGURAZIONE BANDO
-----------------------------
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - CONFIGURAZIONE BANDO  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
End;    
    
------------------------------
-- FLUSSI DATI AGGIUNTIVI
------------------------------    

begin
 --  32. T_RICHIESTA_DURC

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_RICHIESTA_DURC';

 For Rec_T_RICHIESTA_DURC IN
  (
   Select
           extractvalue(t.column_value,'/richiesta/@ID_RICHIESTA')as id_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_DOMANDA')   as id_domanda,
           extractvalue(t.column_value,'/richiesta/ID_TIPO_RICHIESTA') as id_tipo_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_STATO_RICHIESTA') as id_stato_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_RICHIEDENTE')as id_utente_richiedente,
           extractvalue(t.column_value,'/richiesta/DT_INVIO_RICHIESTA')as dt_invio_richiesta,
           extractvalue(t.column_value,'/richiesta/DT_CHIUSURA_RICHIESTA') as dt_chiusura_richiesta,
           extractvalue(t.column_value,'/richiesta/FLAG_URGENZA') as flag_urgenza,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_AGG') as id_utente_agg,
           extractvalue(t.column_value,'/richiesta/DT_INIZIO_VALIDITA') as dt_inizio_validita,
           extractvalue(t.column_value,'/richiesta/DT_FINE_VALIDITA') as dt_fine_validita
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_RICHIESTA_DURC/richiesta'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_RICHIESTA_DURC')
          )  t
  --WHERE NOT EXISTS
  --  (SELECT null
  --     from pbandi_t_richiesta
  --     where id_richiesta =
  --      (extractValue(t.column_value,'/richiesta/@ID_RICHIESTA'))
  --      )
     )
  LOOP


  Insert into MFinpis_t_richiesta
   (ID_RICHIESTA,
    ID_DOMANDA,
    ID_TIPO_RICHIESTA,
    ID_STATO_RICHIESTA,
    ID_UTENTE_RICHIEDENTE,
    DT_INVIO_RICHIESTA,
    DT_CHIUSURA_RICHIESTA,
    FLAG_URGENZA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_RICHIESTA_PBAN,
    ID_DOMANDA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
    )VALUES
    (Rec_T_RICHIESTA_DURC.ID_RICHIESTA,
    Rec_T_RICHIESTA_DURC.ID_DOMANDA,-- verificare che non ci siano id alfanumerici
    Rec_T_RICHIESTA_DURC.ID_TIPO_RICHIESTA,
    Rec_T_RICHIESTA_DURC.ID_STATO_RICHIESTA,
    -14,
    NVL(to_date (Rec_T_RICHIESTA_DURC.DT_INVIO_RICHIESTA,'yyyy/mm/dd'),to_date (Rec_T_RICHIESTA_DURC.DT_CHIUSURA_RICHIESTA,'yyyy/mm/dd')),
    to_date (Rec_T_RICHIESTA_DURC.DT_CHIUSURA_RICHIESTA,'yyyy/mm/dd'),
    Rec_T_RICHIESTA_DURC.FLAG_URGENZA,
    -14, --Rec_T_RICHIESTA_DURC.ID_UTENTE_INS,
    Rec_T_RICHIESTA_DURC.ID_UTENTE_AGG,
    to_date(Rec_T_RICHIESTA_DURC.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
    Rec_T_RICHIESTA_DURC.DT_FINE_VALIDITA,--NULL, --DT_FINE_VALIDITA,
    NULL,
    NULL,
    vId_Caricamento,
    NULL
    );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - MFinpis_t_richiesta DURC riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    
end;


begin
 --  33. T_SOGGETTO_DURC

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGGETTO_DURC';

vcount:= 1;

For Rec_T_SOGGETTO_DURC IN
  (
   Select
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO_DURC')as id_soggetto_durc,
           extractvalue(t.column_value,'/soggetto/ID_SOGGETTO')   as id_soggetto,
           extractvalue(t.column_value,'/soggetto/ID_TIPO_ESITO_DURC') as id_tipo_esito_durc,
           extractvalue(t.column_value,'/soggetto/DT_EMISSIONE_DURC') as dt_emissione_durc,
           extractvalue(t.column_value,'/soggetto/DT_SCADENZA')as dt_scadenza,
           extractvalue(t.column_value,'/soggetto/NUM_PROTOCOLLO_INPS')as num_protocollo_inps,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_AGG') as id_utente_agg,
           extractvalue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA') as dt_inizio_validita,
           extractvalue(t.column_value,'/soggetto/DT_FINE_VALIDITA') as dt_fine_validita
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGGETTO_DURC/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGGETTO_DURC')
          )  t
 -- WHERE NOT EXISTS
 --   (SELECT null
 --      from pbandi_t_soggetto_durc
 --      where id_soggetto_durc =
 --       (extractValue(t.column_value,'/richiesta/@ID_SOGGETTO_DURC'))
 --       )

     )
  LOOP



    INSERT INTO mfinpis_t_soggetto_durc
    (ID_SOGGETTO_DURC,
      ID_SOGGETTO,
      ID_TIPO_ESITO_DURC,
      DT_EMISSIONE_DURC,
      DT_SCADENZA,
      NUM_PROTOCOLLO_INPS,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_SOGGETTO_DURC_PBAN,
      ID_SOGGETTO_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
     )VALUES
     (vcount, --Rec_T_SOGGETTO_DURC.ID_SOGGETTO_DURC,
      Rec_T_SOGGETTO_DURC.ID_SOGGETTO_DURC,
      Rec_T_SOGGETTO_DURC.ID_TIPO_ESITO_DURC,
      to_date(Rec_T_SOGGETTO_DURC.DT_EMISSIONE_DURC,'yyyy-mm-dd'),
      to_date(Rec_T_SOGGETTO_DURC.DT_SCADENZA,'yyyy-mm-dd'),
      Rec_T_SOGGETTO_DURC.NUM_PROTOCOLLO_INPS,
      -14,--Rec_T_SOGGETTO_DURC.ID_UTENTE_INS,
      Rec_T_SOGGETTO_DURC.ID_UTENTE_AGG,
      to_date(Rec_T_SOGGETTO_DURC.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
      to_date(Rec_T_SOGGETTO_DURC.DT_FINE_VALIDITA,'yyyy-mm-dd'),
      NULL,
      NULL,
      vId_Caricamento,
      NULL
     );

     vcount:= vcount+1;


  END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:= 0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_soggetto_durc  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
     
    
End;

begin
--  34. T_RICHIESTA_ANTIMAFIA

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_RICHIESTA_ANTIMAFIA';

 For Rec_T_RICHIESTA_ANTIMAFIA IN
  (
   Select
           extractvalue(t.column_value,'/richiesta/@ID_RICHIESTA')as id_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_DOMANDA')   as id_domanda,
           extractvalue(t.column_value,'/richiesta/ID_TIPO_RICHIESTA') as id_tipo_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_STATO_RICHIESTA') as id_stato_richiesta,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_RICHIEDENTE')as id_utente_richiedente,
           extractvalue(t.column_value,'/richiesta/DT_INVIO_RICHIESTA')as dt_invio_richiesta,
           extractvalue(t.column_value,'/richiesta/DT_CHIUSURA_RICHIESTA') as dt_chiusura_richiesta,
           extractvalue(t.column_value,'/richiesta/FLAG_URGENZA') as flag_urgenza,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/richiesta/ID_UTENTE_AGG') as id_utente_agg,
           extractvalue(t.column_value,'/richiesta/DT_INIZIO_VALIDITA') as dt_inizio_validita,
           extractvalue(t.column_value,'/richiesta/DT_FINE_VALIDITA') as dt_fine_validita
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_RICHIESTA_ANTIMAFIA/richiesta'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_RICHIESTA_ANTIMAFIA')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_t_richiesta
--       where id_richiesta =
--        (extractValue(t.column_value,'/richiesta/@ID_RICHIESTA'))
--        )
     )
  LOOP


  Insert into MFinpis_t_richiesta
   (ID_RICHIESTA,
    ID_DOMANDA,
    ID_TIPO_RICHIESTA,
    ID_STATO_RICHIESTA,
    ID_UTENTE_RICHIEDENTE,
    DT_INVIO_RICHIESTA,
    DT_CHIUSURA_RICHIESTA,
    FLAG_URGENZA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_RICHIESTA_PBAN,
    ID_DOMANDA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
    )VALUES
    (Rec_T_RICHIESTA_ANTIMAFIA.ID_RICHIESTA,
    Rec_T_RICHIESTA_ANTIMAFIA.ID_DOMANDA,
    Rec_T_RICHIESTA_ANTIMAFIA.ID_TIPO_RICHIESTA,
    Rec_T_RICHIESTA_ANTIMAFIA.ID_STATO_RICHIESTA,
    -14,
    NVL(to_date(Rec_T_RICHIESTA_ANTIMAFIA.DT_INVIO_RICHIESTA,'yyyy/mm/dd'),NVL(to_date (Rec_T_RICHIESTA_ANTIMAFIA.DT_CHIUSURA_RICHIESTA,'yyyy/mm/dd'),SYSDATE)),
    to_date (Rec_T_RICHIESTA_ANTIMAFIA.DT_CHIUSURA_RICHIESTA,'yyyy/mm/dd'),
    Rec_T_RICHIESTA_ANTIMAFIA.FLAG_URGENZA,
    -14,-- Rec_T_RICHIESTA_ANTIMAFIA.ID_UTENTE_INS,
    Rec_T_RICHIESTA_ANTIMAFIA.ID_UTENTE_AGG,
    to_date(Rec_T_RICHIESTA_ANTIMAFIA.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
    Rec_T_RICHIESTA_ANTIMAFIA.DT_FINE_VALIDITA,--NULL, --DT_FINE_VALIDITA,
    NULL,
    NULL,
    vId_Caricamento,
    sysdate
    );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;

   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - MFinpis_t_richiesta ANTIMAFIA  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    

End;


begin
 -- 35. T_SOGGETTO_ANTIMAFIA

 Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGGETTO_ANTIMAFIA';

  vcount:= 0;

 For REC_SOGGETTO_ANTIMAFIA IN
 (Select
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO_ANTIMAFIA')as id_soggetto_antimafia,
           extractvalue(t.column_value,'/soggetto/ID_SOGGETTO')   as id_soggetto,
           extractvalue(t.column_value,'/soggetto/ID_DOMANDA')   as id_domanda,
           extractvalue(t.column_value,'/soggetto/ID_TIPO_ESITO_ANTIMAFIA') as id_tipo_esito_antimafia,
           extractvalue(t.column_value,'/soggetto/DT_RICEZIONE_BDNA') as dt_ricezione_bdna,
           extractvalue(t.column_value,'/soggetto/NUMER_PROTOCOLLO_RICEVUTA') as numer_protocollo_ricevuta,
           extractvalue(t.column_value,'/soggetto/DT_EMISSIONE')as dt_emissione,
           extractvalue(t.column_value,'/soggetto/ESITO_LIBERATORIA_ANTIMAFIA')as esito_liberatoria_antimafia,
           extractvalue(t.column_value,'/soggetto/DT_SCADENZA_ANTIMAFIA')as dt_scadenza_antimafia,
           extractvalue(t.column_value,'/soggetto/NUM_PROTOCOLLO_PREFETTURA')as num_protocollo_prefettura,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_AGG') as id_utente_agg,
           extractvalue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA') as dt_inizio_validita,
           extractvalue(t.column_value,'/soggetto/DT_FINE_VALIDITA') as dt_fine_validita
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGGETTO_ANTIMAFIA/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGGETTO_ANTIMAFIA')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_t_soggetto_antimafia
--       where id_soggetto_antimafia =
--       (extractValue(t.column_value,'/richiesta/@ID_SOGGETTO_ANTIMAFIA'))
--        )
   )LOOP

   vcount:= vcount+1;

   INSERT INTO  Mfinpis_t_Soggetto_Antimafia
   (ID_SOGGETTO_ANTIMAFIA,
    --ID_SOGGETTO,
    ID_DOMANDA,
    ID_TIPO_ESITO_ANTIMAFIA,
    DT_RICEZIONE_BDNA,
    NUMER_PROTOCOLLO_RICEVUTA,
    DT_EMISSIONE,
    ESITO_LIBERATORIA_ANTIMAFIA,
    DT_SCADENZA_ANTIMAFIA,
    NUM_PROTOCOLLO_PREFETTURA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_SOGGETTO_ANTIMAFIA_PBAN,
    ID_DOMANDA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
   )VALUES
   (
    vcount,
    --REC_SOGGETTO_ANTIMAFIA.ID_SOGGETTO,
    REC_SOGGETTO_ANTIMAFIA.ID_DOMANDA,
    REC_SOGGETTO_ANTIMAFIA.ID_TIPO_ESITO_ANTIMAFIA,
    to_date(REC_SOGGETTO_ANTIMAFIA.DT_RICEZIONE_BDNA,'yyyy-mm-dd'),
    REC_SOGGETTO_ANTIMAFIA.NUMER_PROTOCOLLO_RICEVUTA,
    to_date(REC_SOGGETTO_ANTIMAFIA.DT_EMISSIONE,'yyyy-mm-dd'),
    REC_SOGGETTO_ANTIMAFIA.ESITO_LIBERATORIA_ANTIMAFIA,
    to_date(REC_SOGGETTO_ANTIMAFIA.DT_SCADENZA_ANTIMAFIA,'yyyy-mm-dd'),
    REC_SOGGETTO_ANTIMAFIA.NUM_PROTOCOLLO_PREFETTURA,
    -14, --REC_SOGGETTO_ANTIMAFIA.ID_UTENTE_INS,
    NULL,    --ID_UTENTE_AGG,
    to_date(REC_SOGGETTO_ANTIMAFIA.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),--SYSDATE, --DT_INIZIO_VALIDITA,
    REC_SOGGETTO_ANTIMAFIA.DT_FINE_VALIDITA,--NULL,    --DT_FINE_VALIDITA
    NULL,
    NULL,
    vId_Caricamento,
    NULL
   );



  END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
   vcount:=0;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - Mfinpis_t_Soggetto_Antimafia  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    
   
end;


begin
 -- 36. T_REVOCHE

  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_REVOCHE';

 For REC_T_REVOCHE IN
 (Select
           extractvalue(t.column_value,'/revoca/@ID_RICHIESTA')as id_richiesta,
           extractvalue(t.column_value,'/revoca/IMPORTO')   as importo,
           extractvalue(t.column_value,'/revoca/DT_REVOCA')   as dt_revoca,
           extractvalue(t.column_value,'/revoca/ID_PROGETTO') as id_progetto,
           extractvalue(t.column_value,'/revoca/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/revoca/ID_UTENTE_AGG') as id_utente_agg,
           extractvalue(t.column_value,'/revoca/ESTREMI') as estremi,
           extractvalue(t.column_value,'/revoca/ID_MOTIVO_REVOCA') as id_motivo_revoca,
           extractvalue(t.column_value,'/revoca/NOTE')as note,
           extractvalue(t.column_value,'/revoca/ID_MODALITA_AGEVOLAZIONE')as id_modalita_agevolazione,
           extractvalue(t.column_value,'/revoca/DT_INSERIMENTO')as dt_inserimento,
           extractvalue(t.column_value,'/revoca/DT_AGGIORNAMENTO')as dt_aggiornamento,
           extractvalue(t.column_value,'/revoca/INTERESSI_REVOCA') as interessi_revoca,
           extractvalue(t.column_value,'/revoca/ID_CASUALE_DISIMPEGNO') as id_causale_disimpegno,
           extractvalue(t.column_value,'/revoca/ID_PERIODO')as id_periodo,
           extractvalue(t.column_value,'/revoca/FLAG_ORDINE_RECUPERO') as flag_ordine_recupero,
           extractvalue(t.column_value,'/revoca/ID_MANCATO_RECUPERO') as id_mancato_recupero
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_REVOCHE/revoca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_REVOCHE')
          )  t
  --WHERE NOT EXISTS
  --  (SELECT null
  --     from pbandi_t_revoca
  --     where id_revoca =
  --      (extractValue(t.column_value,'/revoca/@ID_RICHIESTA'))  -- DA VERIFICARE CON ATTENZIONE
                                                                -- NON SO SE BASARE L ESCLUSIONE SU ID_PROGETTO
  --      )
      )LOOP

         INSERT INTO MFINPIS_T_REVOCA
         (ID_REVOCA,
          IMPORTO,
          DT_REVOCA,
          ID_PROGETTO,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          ESTREMI,
          ID_MOTIVO_REVOCA,
          NOTE,
          ID_MODALITA_AGEVOLAZIONE,
          DT_INSERIMENTO,
          DT_AGGIORNAMENTO,
          INTERESSI_REVOCA,
          ID_CAUSALE_DISIMPEGNO,
          ID_PERIODO,
          FLAG_ORDINE_RECUPERO,
          ID_MANCATO_RECUPERO,
          ID_REVOCA_PBAN,
          ID_PROGETTO_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
         )VALUES
         (REC_T_REVOCHE.ID_RICHIESTA,
          REC_T_REVOCHE.IMPORTO,
          NVL(to_date(substr(REC_T_REVOCHE.DT_REVOCA,1,10),'yyyy-mm-dd'),SYSDATE),
          REC_T_REVOCHE.ID_PROGETTO,
          REC_T_REVOCHE.ID_UTENTE_INS,
          REC_T_REVOCHE.ID_UTENTE_AGG,
          REC_T_REVOCHE.ESTREMI,
          --1,--REC_T_REVOCHE.ID_MOTIVO_REVOCA,  --> DA MODIFICARE
          REC_T_REVOCHE.ID_MOTIVO_REVOCA, -- modifica del 170323
          REC_T_REVOCHE.NOTE,
          REC_T_REVOCHE.ID_MODALITA_AGEVOLAZIONE,
          NVL(to_date(substr(REC_T_REVOCHE.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
          REC_T_REVOCHE.DT_AGGIORNAMENTO,
          REC_T_REVOCHE.INTERESSI_REVOCA,
          REC_T_REVOCHE.ID_CAUSALE_DISIMPEGNO,
          REC_T_REVOCHE.ID_PERIODO,
          REC_T_REVOCHE.FLAG_ORDINE_RECUPERO,
          REC_T_REVOCHE.ID_MANCATO_RECUPERO,
          NULL,
          NULL,
          vId_Caricamento,
          NULL
         );

       END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - MFINPIS_T_REVOCA  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 37. T_ESCUSSIONE


  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_ESCUSSIONE';

FOR rec_t_escussione IN
(Select
           extractvalue(t.column_value,'/escussione/@ID_ESCUSSIONE')as id_escussione,
           TO_CHAR(extractvalue(t.column_value,'/escussione/ID_PROGETTO'))   as id_progetto,
           extractvalue(t.column_value,'/escussione/ID_TIPO_ESCUSSIONE')   as id_tipo_escussione,
           extractvalue(t.column_value,'/escussione/ID_STATO_ESCUSSIONE') as id_stato_escussione,
           extractvalue(t.column_value,'/escussione/DT_RIC_RICH_ESCUSSIONE') as dt_ric_rich_escussione,
           extractvalue(t.column_value,'/escussione/DT_NOTIFICA') as dt_notifica,
           extractvalue(t.column_value,'/escussione/IMP_RICHIESTO') as imp_richiesto,
           extractvalue(t.column_value,'/escussione/IMP_APPROVATO') as imp_approvato,
           extractvalue(t.column_value,'/escussione/CAUSALE_BONIFICO')as causale_bonifico,
           extractvalue(t.column_value,'/escussione/IBAN_BONIFICO')as iban_bonifico,
           extractvalue(t.column_value,'/escussione/NOTE')as note,
           extractvalue(t.column_value,'/escussione/DT_INIZIO_VALIDITA')as dt_inizio_validita,
           extractvalue(t.column_value,'/escussione/DT_FINE_VALIDITA') as dt_fine_validita,
           extractvalue(t.column_value,'/escussione/ID_UTENTE_INS') as id_utente_ins,
           extractvalue(t.column_value,'/escussione/ID_UTENTE_AGG')as id_utente_agg,
           extractvalue(t.column_value,'/escussione/DT_INSERIMENTO') as dt_inserimento,
           extractvalue(t.column_value,'/escussione/DT_AGGIORNAMENTO') as dt_aggiornamento,
           extractvalue(t.column_value,'/escussione/DATA_STATO') as data_stato
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_ESCUSSIONE/escussione'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_ESCUSSIONE')
          )  t
--  WHERE NOT EXISTS
--    (SELECT null
--       from pbandi_t_escussione
--       where id_escussione =
--        (extractValue(t.column_value,'/escussione/@ID_ESCUSSIONE'))
--        )
 ) LOOP

 Insert into mfinpis_t_escussione
   (ID_ESCUSSIONE,
    ID_PROGETTO,
    ID_TIPO_ESCUSSIONE,
    ID_STATO_ESCUSSIONE,
    DT_RIC_RICH_ESCUSSIONE,
    DT_NOTIFICA,
    IMP_RICHIESTO,
    IMP_APPROVATO,
    CAUSALE_BONIFICO,
    IBAN_BONIFICO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_ESCUSSIONE_PBAN,
    ID_PROGETTO_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
   )values
   (rec_t_escussione.ID_ESCUSSIONE,
    to_char( rec_t_escussione.ID_PROGETTO),
    rec_t_escussione.ID_TIPO_ESCUSSIONE,
    rec_t_escussione.ID_STATO_ESCUSSIONE,
    NVL(to_date(substr(rec_t_escussione.DT_RIC_RICH_ESCUSSIONE,1,10),'yyyy-mm-dd'),SYSDATE),
    --NVL(to_date(substr(rec_t_escussione.DT_NOTIFICA,1,10),'yyyy-mm-dd'),SYSDATE),
    NULL, -- non è mai valorizzata
    NVL(rec_t_escussione.IMP_RICHIESTO,0),
    rec_t_escussione.IMP_APPROVATO,
    rec_t_escussione.CAUSALE_BONIFICO,
    rec_t_escussione.IBAN_BONIFICO,
    rec_t_escussione.NOTE,
    NVL(to_date(substr(rec_t_escussione.data_stato,1,10),'yyyy-mm-dd'),SYSDATE), -- new
    to_date(substr(rec_t_escussione.DT_FINE_VALIDITA,1,10),'yyyy-mm-dd'), -- modificato il 21/04/23
    -14, --rec_t_escussione.ID_UTENTE_INS,
    rec_t_escussione.ID_UTENTE_AGG,
    NVL(to_date(substr(rec_t_escussione.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
    NVL(to_date(substr(rec_t_escussione.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
    NULL,
    NULL,
    vId_Caricamento,
    NULL
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_escussione  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    
   
end;
-------------------------------------------
---              AREA CREDITI           ---
-------------------------------------------
begin
-- 50. R_SOGG_PROG_STA_CRE_FP
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'R_SOGG_PROG_STA_CRE_FP';

 vcount:= 0;

FOR rec_R_SOGG_PROG_STA_CRE_FP IN
(Select
           extractvalue(t.column_value,'/progetto/@ID_SOGG_PROG_STATO_CREDITO_FP')as ID_SOGG_PROG_STATO_CREDITO_FP,
           extractvalue(t.column_value,'/progetto/PROGR_SOGGETTO_PROGETTO')   as PROGR_SOGGETTO_PROGETTO,
           extractvalue(t.column_value,'/progetto/ID_STATO_CREDITO_FP')   as ID_STATO_CREDITO_FP,
           extractvalue(t.column_value,'/progetto/ID_UTENTE_INS') as ID_UTENTE_INS,
           extractvalue(t.column_value,'/progetto/ID_UTENTE_AGG') as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/progetto/DT_INSERIMENTO') as DT_INSERIMENTO,
           extractvalue(t.column_value,'/progetto/DT_AGGIORNAMENTO') as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/progetto/DT_INIZIO_VALIDITA') as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/progetto/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/progetto/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/R_SOGG_PROG_STA_CRED_FP/progetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'R_SOGG_PROG_STA_CRE_FP')
          )  t
 ) LOOP

 vcount:= vcount+1;



 Insert into mfinpis_R_SOGG_PROG_STA_CRE_FP
   (ID_SOGG_PROG_STATO_CREDITO_FP,
    PROGR_SOGGETTO_PROGETTO,
    ID_STATO_CREDITO_FP,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_SOGG_PROG_STATO_CRE_FP_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    ID_MODALITA_AGEVOLAZIONE
   )values
   (--rec_R_SOGG_PROG_STA_CRE_FP.ID_SOGG_PROG_STATO_CREDITO_FP,
    vcount,
    rec_R_SOGG_PROG_STA_CRE_FP.ID_SOGG_PROG_STATO_CREDITO_FP,
    -- rec_R_SOGG_PROG_STA_CRE_FP.PROGR_SOGGETTO_PROGETTO, -- questa informazione non serve
    rec_R_SOGG_PROG_STA_CRE_FP.ID_STATO_CREDITO_FP,
    -14,--rec_R_SOGG_PROG_STA_CRE_FP.ID_UTENTE_INS,
    rec_R_SOGG_PROG_STA_CRE_FP.ID_UTENTE_AGG,
    NVL(to_date(substr(rec_R_SOGG_PROG_STA_CRE_FP.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
    to_date(substr(rec_R_SOGG_PROG_STA_CRE_FP.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd'),
    NVL(to_date(substr(rec_R_SOGG_PROG_STA_CRE_FP.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),SYSDATE),
    to_date(substr(rec_R_SOGG_PROG_STA_CRE_FP.DT_FINE_VALIDITA,1,10),'yyyy-mm-dd'),
    NULL,
    vId_Caricamento,
    NULL,
    rec_R_SOGG_PROG_STA_CRE_FP.Id_Modalita_Agevolazione
    );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:= 0;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_R_SOGG_PROG_STA_CRE_FP  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
     
end;

begin
-- 51. T_AZIONE_RECUP_BANCA
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_AZIONE_RECUP_BANCA';

FOR rec_T_AZIONE_RECUP_BANCA IN
(Select
           extractvalue(t.column_value,'/recupero_banca/@ID_AZIONE_RECUPERO_BANCA')as ID_AZIONE_RECUPERO_BANCA,
           extractvalue(t.column_value,'/recupero_banca/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/recupero_banca/ID_ATTIVITA_AZIONE')   as ID_ATTIVITA_AZIONE,
           extractvalue(t.column_value,'/recupero_banca/DT_AZIONE') as DT_AZIONE,
           extractvalue(t.column_value,'/recupero_banca/NUM_PROTOCOLLO') as NUM_PROTOCOLLO,
           extractvalue(t.column_value,'/recupero_banca/NOTE') as NOTE,
           extractvalue(t.column_value,'/recupero_banca/DT_INIZIO_VALIDITA') as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/recupero_banca/DT_FINE_VALIDITA') as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/recupero_banca/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/recupero_banca/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/recupero_banca/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/recupero_banca/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/recupero_banca/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_AZIONE_RECUP_BANCA/recupero_banca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_AZIONE_RECUP_BANCA')
          )  t
 ) LOOP

 Insert into mfinpis_T_AZIONE_RECUP_BANCA
   (ID_AZIONE_RECUPERO_BANCA,
    ID_PROGETTO,
    ID_ATTIVITA_AZIONE,
    DT_AZIONE,
    NUM_PROTOCOLLO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_AZIONE_RECUPERO_BANCA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    ID_MODALITA_AGEVOLAZIONE
   )values
   (rec_T_AZIONE_RECUP_BANCA.ID_AZIONE_RECUPERO_BANCA,
    rec_T_AZIONE_RECUP_BANCA.ID_PROGETTO,
    rec_T_AZIONE_RECUP_BANCA.ID_ATTIVITA_AZIONE,
    to_date(substr(rec_T_AZIONE_RECUP_BANCA.DT_AZIONE,1,10),'yyyy-mm-dd'),
    rec_T_AZIONE_RECUP_BANCA.NUM_PROTOCOLLO,
    rec_T_AZIONE_RECUP_BANCA.NOTE,
    NVL(to_date(substr(rec_T_AZIONE_RECUP_BANCA.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),SYSDATE),
    to_date(substr(rec_T_AZIONE_RECUP_BANCA.DT_FINE_VALIDITA,1,10),'yyyy-mm-dd'),
    -14,--rec_T_AZIONE_RECUP_BANCA.ID_UTENTE_INS,
    rec_T_AZIONE_RECUP_BANCA.ID_UTENTE_AGG,
    NVL(to_date(substr(rec_T_AZIONE_RECUP_BANCA.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
    to_date(substr(rec_T_AZIONE_RECUP_BANCA.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd'),
    NULL,
    vId_Caricamento,
    NULL,
    rec_T_AZIONE_RECUP_BANCA.ID_MODALITA_AGEVOLAZIONE
    );


   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_T_AZIONE_RECUP_BANCA  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;   
   
end;



begin
-- 52. T_CESSIONE_QUOTA_FP
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_CESSIONE_QUOTA_FP';

vcount:= 0;

FOR rec_T_CESSIONE_QUOTA_FP IN
(select
           extractvalue(t.column_value,'/cessione/@ID_CESSIONE_QUOTA_FP')as ID_CESSIONE_QUOTA_FP,
           extractvalue(t.column_value,'/cessione/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/cessione/IMP_CESSIONE_CAPITALE')   as IMP_CESSIONE_CAPITALE,
           extractvalue(t.column_value,'/cessione/IMP_CESSIONE_ONERI') as IMP_CESSIONE_ONERI,
           extractvalue(t.column_value,'/cessione/IMP_CESSIONE_INTERESSI_MORA') as IMP_CESSIONE_INTERESSI_MORA,
           extractvalue(t.column_value,'/cessione/IMP_CESSIONE_COMPLESSIVA') as IMP_CESSIONE_COMPLESSIVA,
           extractvalue(t.column_value,'/cessione/DT_CESSIONE') as DT_CESSIONE,
           extractvalue(t.column_value,'/cessione/IMP_CORRISP_CESSIONE') as IMP_CORRISP_CESSIONE,
           extractvalue(t.column_value,'/cessione/DENOM_CESSIONARIO')as DENOM_CESSIONARIO,
           extractvalue(t.column_value,'/cessione/ID_STATO_CESSIONE')as ID_STATO_CESSIONE,
           extractvalue(t.column_value,'/cessione/NOTE')as NOTE,
           extractvalue(t.column_value,'/cessione/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/cessione/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/cessione/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/cessione/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/cessione/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/cessione/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/cessione/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_CESSIONE_QUOTA_FP/cessione'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_CESSIONE_QUOTA_FP')
          )  t
 ) LOOP

  vcount:= vcount+1;

 Insert into mfinpis_T_CESSIONE_QUOTA_FP
   (ID_CESSIONE_QUOTA_FP,
    ID_PROGETTO  ,
    IMP_CESSIONE_CAPITALE  ,
    IMP_CESSIONE_ONERI  ,
    IMP_CESSIONE_INTERESSI_MORA  ,
    IMP_CESSIONE_COMPLESSIVA  ,
    DT_CESSIONE  ,
    IMP_CORRISP_CESSIONE  ,
    DENOM_CESSIONARIO  ,
    ID_STATO_CESSIONE  ,
    NOTE  ,
    DT_INIZIO_VALIDITA  ,
    DT_FINE_VALIDITA  ,
    ID_UTENTE_INS  ,
    ID_UTENTE_AGG  ,
    DT_INSERIMENTO  ,
    DT_AGGIORNAMENTO  ,
    ID_CESSIONE_QUOTA_FP_PBAN  ,
    ID_CARICAMENTO ,
    DATA_CARICAMENTO,
    ID_MODALITA_AGEVOLAZIONE
   )values
   (vcount, --rec_T_CESSIONE_QUOTA_FP.ID_CESSIONE_QUOTA_FP,
    rec_T_CESSIONE_QUOTA_FP.ID_PROGETTO  ,
    rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_CAPITALE  ,
    rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_ONERI  ,
    rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_INTERESSI_MORA  ,
    rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_COMPLESSIVA  ,
    rec_T_CESSIONE_QUOTA_FP.DT_CESSIONE  ,
    rec_T_CESSIONE_QUOTA_FP.IMP_CORRISP_CESSIONE  ,
    rec_T_CESSIONE_QUOTA_FP.DENOM_CESSIONARIO  ,
    rec_T_CESSIONE_QUOTA_FP.ID_STATO_CESSIONE  ,
    rec_T_CESSIONE_QUOTA_FP.NOTE  ,
    sysdate,--DT_INIZIO_VALIDITA  ,
    rec_T_CESSIONE_QUOTA_FP.DT_FINE_VALIDITA  ,
    -14,--rec_T_CESSIONE_QUOTA_FP.ID_UTENTE_INS  ,
    null,--ID_UTENTE_AGG  ,
    NVL(to_date(substr(rec_T_CESSIONE_QUOTA_FP.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),--DT_INSERIMENTO  ,
    rec_T_CESSIONE_QUOTA_FP.DT_AGGIORNAMENTO  ,
    NULL,
    vId_Caricamento,
    NULL,
    rec_T_CESSIONE_QUOTA_FP.ID_MODALITA_AGEVOLAZIONE
    );



   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_T_CESSIONE_QUOTA_FP  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 53. T_ESCUSS_CONFIDI


  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_ESCUSS_CONFIDI';

FOR rec_T_ESCUSS_CONFIDI IN
(Select
           extractvalue(t.column_value,'/escussione_confidi/@ID_ESCUSS_CONFIDI')as ID_ESCUSS_CONFIDI,
           extractvalue(t.column_value,'/escussione_confidi/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/escussione_confidi/ID_ATTIVITA_GARANZIA_CONFIDI')   as ID_ATTIVITA_GARANZIA_CONFIDI,
           extractvalue(t.column_value,'/escussione_confidi/DENOM_CONFIDI') as DENOM_CONFIDI,
           extractvalue(t.column_value,'/escussione_confidi/DT_ESCUSS_CONFIDI') as DT_ESCUSS_CONFIDI,
           extractvalue(t.column_value,'/escussione_confidi/DT_PAGAM_CONFIDI') as DT_PAGAM_CONFIDI,
           extractvalue(t.column_value,'/escussione_confidi/PERC_GARANZIA') as PERC_GARANZIA,
           extractvalue(t.column_value,'/escussione_confidi/NOTE') as NOTE,
           extractvalue(t.column_value,'/escussione_confidi/DENOM_CESSIONARIO')as DENOM_CESSIONARIO,
           extractvalue(t.column_value,'/escussione_confidi/ID_STATO_CESSIONE')as ID_STATO_CESSIONE,
           extractvalue(t.column_value,'/escussione_confidi/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/escussione_confidi/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/escussione_confidi/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/escussione_confidi/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/escussione_confidi/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/escussione_confidi/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/escussione_confidi/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_ESCUSS_CONFIDI/escussione_confidi'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_ESCUSS_CONFIDI')
          )  t
 ) LOOP

 Insert into mfinpis_T_ESCUSS_CONFIDI
   (ID_ESCUSS_CONFIDI,
      ID_PROGETTO,
      ID_ATTIVITA_GARANZIA_CONFIDI,
      DENOM_CONFIDI,
      DT_ESCUSS_CONFIDI,
      DT_PAGAM_CONFIDI,
      PERC_GARANZIA,
      NOTE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_ESCUSS_CONFIDI_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO,
      ID_MODALITA_AGEVOLAZIONE
   )values
   ( rec_T_ESCUSS_CONFIDI.ID_ESCUSS_CONFIDI,
     rec_T_ESCUSS_CONFIDI.ID_PROGETTO,
     rec_T_ESCUSS_CONFIDI.ID_ATTIVITA_GARANZIA_CONFIDI,
     rec_T_ESCUSS_CONFIDI.DENOM_CONFIDI,
     to_date(rec_T_ESCUSS_CONFIDI.DT_ESCUSS_CONFIDI,'yyyy/mm/dd'),
     rec_T_ESCUSS_CONFIDI.DT_PAGAM_CONFIDI,
     rec_T_ESCUSS_CONFIDI.PERC_GARANZIA,
     rec_T_ESCUSS_CONFIDI.NOTE,
     to_date(rec_T_ESCUSS_CONFIDI.DT_INIZIO_VALIDITA,'yyyy/mm/dd'),
     to_date(rec_T_ESCUSS_CONFIDI.DT_FINE_VALIDITA,'yyyy/mm/dd'),
     -14, --rec_T_ESCUSS_CONFIDI.ID_UTENTE_INS,
     rec_T_ESCUSS_CONFIDI.ID_UTENTE_AGG,
     to_date(rec_T_ESCUSS_CONFIDI.DT_INSERIMENTO,'yyyy/mm/dd'),
     to_date(rec_T_ESCUSS_CONFIDI.DT_AGGIORNAMENTO,'yyyy/mm/dd'),
     NULL,
     vId_Caricamento,
     NULL,
     rec_T_ESCUSS_CONFIDI.ID_MODALITA_AGEVOLAZIONE
    );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_T_ESCUSS_CONFIDI  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 54. T_SCRIZIONE_RUOLO

  Select id_caricamento
  Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_ISCRIZIONE_RUOLO';

   vcount:= 0;

FOR rec_T_SCRIZIONE_RUOLO IN
(Select
           extractvalue(t.column_value,'/id_iscrizione/@ID_ISCRIZIONE_RUOLO')as ID_ISCRIZIONE_RUOLO,
           extractvalue(t.column_value,'/id_iscrizione/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/id_iscrizione/DT_RICHIESTA_ISCRIZIONE')   as DT_RICHIESTA_ISCRIZIONE,
           extractvalue(t.column_value,'/id_iscrizione/NUM_PROTOCOLLO') as NUM_PROTOCOLLO,
           extractvalue(t.column_value,'/id_iscrizione/DT_RICHIESTA_DISCARICO') as DT_RICHIESTA_DISCARICO,
           extractvalue(t.column_value,'/id_iscrizione/NUM_PROTOCOLLO_DISCARICO') as NUM_PROTOCOLLO_DISCARICO,
           extractvalue(t.column_value,'/id_iscrizione/DT_ISCRIZIONE_RUOLO') as DT_ISCRIZIONE_RUOLO,
           extractvalue(t.column_value,'/id_iscrizione/DT_DISCARICO') as DT_DISCARICO,
           extractvalue(t.column_value,'/id_iscrizione/NUM_PROTOCOLLO_DISCAR_REG')as NUM_PROTOCOLLO_DISCAR_REG,
           extractvalue(t.column_value,'/id_iscrizione/DT_RICHIESTA_SOSP')as DT_RICHIESTA_SOSP,
           extractvalue(t.column_value,'/id_iscrizione/NUM_PROTOCOLLO_SOSP')as NUM_PROTOCOLLO_SOSP,
           extractvalue(t.column_value,'/id_iscrizione/IMP_CAPITALE_RUOLO')as IMP_CAPITALE_RUOLO,
           extractvalue(t.column_value,'/id_iscrizione/IMP_AGEVOLAZ_RUOLO')as IMP_AGEVOLAZ_RUOLO,
           extractvalue(t.column_value,'/id_iscrizione/DT_ISCRIZIONE')as DT_ISCRIZIONE,
           extractvalue(t.column_value,'/id_iscrizione/NUM_PROTOCOLLO_SOSP')as NUM_PROTOCOLLO_REGIONE,
           extractvalue(t.column_value,'/id_iscrizione/TIPO_PAGAMENTO')as TIPO_PAGAMENTO,
           extractvalue(t.column_value,'/id_iscrizione/NOTE')as NOTE,
           extractvalue(t.column_value,'/id_iscrizione/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/id_iscrizione/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/id_iscrizione/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/id_iscrizione/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/id_iscrizione/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/id_iscrizione/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/id_iscrizione/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_ISCRIZIONE_RUOLO/id_iscrizione'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_ISCRIZIONE_RUOLO')
          )  t
 ) LOOP

 vcount:= vcount+1;

 Insert into mfinpis_t_iscrizione_ruolo
   (  ID_ISCRIZIONE_RUOLO,
    ID_PROGETTO,
    DT_RICHIESTA_ISCRIZIONE,
    NUM_PROTOCOLLO,
    DT_RICHIESTA_DISCARICO,
    NUM_PROTOCOLLO_DISCARICO,
    DT_ISCRIZIONE_RUOLO,
    DT_DISCARICO,
    NUM_PROTOCOLLO_DISCAR_REG  ,
    DT_RICHIESTA_SOSP  ,
    NUM_PROTOCOLLO_SOSP  ,
    IMP_CAPITALE_RUOLO  ,
    IMP_AGEVOLAZ_RUOLO  ,
    DT_ISCRIZIONE,
    NUM_PROTOCOLLO_REGIONE  ,
    TIPO_PAGAMENTO  ,
    NOTE  ,
    DT_INIZIO_VALIDITA  ,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS  ,
    ID_UTENTE_AGG  ,
    DT_INSERIMENTO  ,
    DT_AGGIORNAMENTO  ,
    ID_ISCRIZIONE_RUOLO_PBAN,
    ID_CARICAMENTO,
   DATA_CARICAMENTO,
   ID_MODALITA_AGEVOLAZIONE
  )values
   (--rec_T_SCRIZIONE_RUOLO.ID_ISCRIZIONE_RUOLO,
   vcount,
    rec_T_SCRIZIONE_RUOLO.ID_PROGETTO,
    to_date(substr(rec_T_SCRIZIONE_RUOLO.DT_RICHIESTA_ISCRIZIONE,1,10),'yyyy-mm-dd'),
    rec_T_SCRIZIONE_RUOLO.NUM_PROTOCOLLO,
    rec_T_SCRIZIONE_RUOLO.DT_RICHIESTA_DISCARICO,
    rec_T_SCRIZIONE_RUOLO.NUM_PROTOCOLLO_DISCARICO,
    to_date(substr(rec_T_SCRIZIONE_RUOLO.DT_ISCRIZIONE_RUOLO,1,10),'yyyy-mm-dd'),
    rec_T_SCRIZIONE_RUOLO.DT_DISCARICO,
    rec_T_SCRIZIONE_RUOLO.NUM_PROTOCOLLO_DISCAR_REG  ,
    rec_T_SCRIZIONE_RUOLO.DT_RICHIESTA_SOSP  ,
    rec_T_SCRIZIONE_RUOLO.NUM_PROTOCOLLO_SOSP  ,
    rec_T_SCRIZIONE_RUOLO.IMP_CAPITALE_RUOLO  ,
    rec_T_SCRIZIONE_RUOLO.IMP_AGEVOLAZ_RUOLO  ,
    rec_T_SCRIZIONE_RUOLO.DT_ISCRIZIONE,
    rec_T_SCRIZIONE_RUOLO.NUM_PROTOCOLLO_REGIONE  ,
    rec_T_SCRIZIONE_RUOLO.TIPO_PAGAMENTO  ,
    rec_T_SCRIZIONE_RUOLO.NOTE  ,
    NVL(to_date(substr(rec_T_SCRIZIONE_RUOLO.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),SYSDATE),
    rec_T_SCRIZIONE_RUOLO.DT_FINE_VALIDITA,
    -14,--rec_T_SCRIZIONE_RUOLO.ID_UTENTE_INS  ,
    rec_T_SCRIZIONE_RUOLO.ID_UTENTE_AGG  ,
    NVL(to_date(substr(rec_T_SCRIZIONE_RUOLO.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
    rec_T_SCRIZIONE_RUOLO.DT_AGGIORNAMENTO  ,
    NULL,
    vId_Caricamento,
    NULL,
    rec_T_SCRIZIONE_RUOLO.ID_MODALITA_AGEVOLAZIONE
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_iscrizione_ruolo riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



begin
-- 55. T_PASSAGGIO_PERDITA

  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_PASSAGGIO_PERDITA';

FOR rec_T_PASSAGGIO_PERDITA IN
(Select
           extractvalue(t.column_value,'/passaggio_perdita/@ID_PASSAGGIO_PERDITA')as ID_PASSAGGIO_PERDITA,
           extractvalue(t.column_value,'/passaggio_perdita/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/passaggio_perdita/DT_PASSAGGIO_PERDITA')   as DT_PASSAGGIO_PERDITA,
           extractvalue(t.column_value,'/passaggio_perdita/IMP_PERDITA_COMPLESSIVA') as IMP_PERDITA_COMPLESSIVA,
           extractvalue(t.column_value,'/passaggio_perdita/IMP_PERDITA_CAPITALE') as IMP_PERDITA_CAPITALE,
           extractvalue(t.column_value,'/passaggio_perdita/IMP_PERDITA_INTERESSI') as IMP_PERDITA_INTERESSI,
           extractvalue(t.column_value,'/passaggio_perdita/IMP_PERDITA_AGEVOLAZ') as IMP_PERDITA_AGEVOLAZ,
           extractvalue(t.column_value,'/passaggio_perdita/IMP_PERDITA_MORA') as IMP_PERDITA_MORA,
           extractvalue(t.column_value,'/passaggio_perdita/NOTE')as NOTE,
           extractvalue(t.column_value,'/passaggio_perdita/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/passaggio_perdita/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/passaggio_perdita/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/passaggio_perdita/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/passaggio_perdita/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/passaggio_perdita/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/passaggio_perdita/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_PASSAGGIO_PERDITA/passaggio_perdita'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_PASSAGGIO_PERDITA')
          )  t
 ) LOOP

 Insert into mfinpis_t_passaggio_perdita
   (ID_PASSAGGIO_PERDITA,
    ID_PROGETTO,
    DT_PASSAGGIO_PERDITA,
    IMP_PERDITA_COMPLESSIVA,
    IMP_PERDITA_CAPITALE,
    IMP_PERDITA_INTERESSI,
    IMP_PERDITA_AGEVOLAZ,
    IMP_PERDITA_MORA,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_PASSAGGIO_PERDITA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    ID_MODALITA_AGEVOLAZIONE
  )values
   (rec_T_PASSAGGIO_PERDITA.ID_PASSAGGIO_PERDITA,
    rec_T_PASSAGGIO_PERDITA.ID_PROGETTO,
    TO_DATE (rec_T_PASSAGGIO_PERDITA.DT_PASSAGGIO_PERDITA,'yyyy/mm/dd'),
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_COMPLESSIVA,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_CAPITALE,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_INTERESSI,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_AGEVOLAZ,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_MORA,
    rec_T_PASSAGGIO_PERDITA.NOTE,
    TO_DATE(rec_T_PASSAGGIO_PERDITA.DT_INIZIO_VALIDITA,'yyyy/mm/dd'),
    rec_T_PASSAGGIO_PERDITA.DT_FINE_VALIDITA,
    -14, --rec_T_PASSAGGIO_PERDITA.ID_UTENTE_INS,
    rec_T_PASSAGGIO_PERDITA.ID_UTENTE_AGG,
    to_date(rec_T_PASSAGGIO_PERDITA.DT_INSERIMENTO,'yyyy/mm/dd'),
    rec_T_PASSAGGIO_PERDITA.DT_AGGIORNAMENTO,
    NULL,
    vId_Caricamento,
    NULL,
    rec_T_PASSAGGIO_PERDITA.ID_MODALITA_AGEVOLAZIONE
   );

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_passaggio_perdita riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 56. T_PIANO_RIENTRO

  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_PIANO_RIENTRO';

   vcount:= 0;

FOR rec_T_PIANO_RIENTRO IN
(Select
           extractvalue(t.column_value,'/piano_rientro/@ID_PIANO_RIENTRO')as ID_PIANO_RIENTRO,
           extractvalue(t.column_value,'/piano_rientro/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/piano_rientro/DT_PROPOSTA')   as DT_PROPOSTA,
           extractvalue(t.column_value,'/piano_rientro/IMP_QUOTA_CAPITALE') as IMP_QUOTA_CAPITALE,
           extractvalue(t.column_value,'/piano_rientro/IMP_AGEVOLAZIONE') as IMP_AGEVOLAZIONE,
           extractvalue(t.column_value,'/piano_rientro/ID_ATTIVITA_ESITO') as ID_ATTIVITA_ESITO,
           extractvalue(t.column_value,'/piano_rientro/DT_ESITO') as DT_ESITO,
           extractvalue(t.column_value,'/piano_rientro/ID_ATTIVITA_RECUPERO') as ID_ATTIVITA_RECUPERO,
           extractvalue(t.column_value,'/piano_rientro/NOTE')as NOTE,
           extractvalue(t.column_value,'/piano_rientro/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/piano_rientro/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/piano_rientro/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/piano_rientro/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/piano_rientro/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/piano_rientro/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/piano_rientro/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_PIANO_RIENTRO/piano_rientro'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_PIANO_RIENTRO')
          )  t
 ) LOOP


 vcount:= vcount +1;

 Insert into mfinpis_T_PIANO_RIENTRO
   (ID_PIANO_RIENTRO,
    ID_PROGETTO,
    DT_PROPOSTA,
    IMP_QUOTA_CAPITALE,
    IMP_AGEVOLAZIONE,
    ID_ATTIVITA_ESITO,
    DT_ESITO,
    ID_ATTIVITA_RECUPERO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_PIANO_RIENTRO_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    ID_MODALITA_AGEVOLAZIONE
  )values
   (--rec_T_PIANO_RIENTRO.ID_PIANO_RIENTRO,
    vcount,
    rec_T_PIANO_RIENTRO.ID_PROGETTO,
    to_date(rec_T_PIANO_RIENTRO.DT_PROPOSTA,'yyyy/mm/dd'),
    rec_T_PIANO_RIENTRO.IMP_QUOTA_CAPITALE,
    rec_T_PIANO_RIENTRO.IMP_AGEVOLAZIONE,
    rec_T_PIANO_RIENTRO.ID_ATTIVITA_ESITO,
    to_date(rec_T_PIANO_RIENTRO.DT_ESITO,'yyyy/mm/dd'),
    rec_T_PIANO_RIENTRO.ID_ATTIVITA_RECUPERO,
    rec_T_PIANO_RIENTRO.NOTE,
    to_date(rec_T_PIANO_RIENTRO.DT_INIZIO_VALIDITA,'yyyy/mm/dd'),
    rec_T_PIANO_RIENTRO.DT_FINE_VALIDITA,
    -14,--rec_T_PIANO_RIENTRO.ID_UTENTE_INS,
    rec_T_PIANO_RIENTRO.ID_UTENTE_AGG,
    to_date(substr(rec_T_PIANO_RIENTRO.DT_INSERIMENTO,1,10),'yyyy/mm/dd'),
    rec_T_PIANO_RIENTRO.DT_AGGIORNAMENTO,
    NULL,
    vId_Caricamento,
    NULL,
    rec_T_PIANO_RIENTRO.ID_MODALITA_AGEVOLAZIONE
   );


   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_T_PIANO_RIENTRO riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 57. T_REVOCA_BANCARIA

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_REVOCA_BANCARIA';

   vcount:=0;

 FOR rec_T_REVOCA_BANCARIA IN
(Select
           extractvalue(t.column_value,'/revoca_bancaria/@ID_REVOCA_BANCARIA')as ID_REVOCA_BANCARIA,
           extractvalue(t.column_value,'/revoca_bancaria/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/revoca_bancaria/DT_RICEZIONE_COMUNICAZ')   as DT_RICEZIONE_COMUNICAZ,
           extractvalue(t.column_value,'/revoca_bancaria/DT_REVOCA') as DT_REVOCA,
           extractvalue(t.column_value,'/revoca_bancaria/IMP_DEBITO_RESIDUO_BANCA') as IMP_DEBITO_RESIDUO_BANCA,
           extractvalue(t.column_value,'/revoca_bancaria/IMP_DEBITO_RESIDUO_FP') as IMP_DEBITO_RESIDUO_FP,
           extractvalue(t.column_value,'/revoca_bancaria/PERC_COFINANZ_FP') as PERC_COFINANZ_FP,
           extractvalue(t.column_value,'/revoca_bancaria/NUM_PROTOCOLLO') as NUM_PROTOCOLLO,
           extractvalue(t.column_value,'/revoca_bancaria/NOTE')as NOTE,
           extractvalue(t.column_value,'/revoca_bancaria/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/revoca_bancaria/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/revoca_bancaria/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/revoca_bancaria/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/revoca_bancaria/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/revoca_bancaria/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/revoca_bancaria/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_REVOCA_BANCARIA/revoca_bancaria'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_REVOCA_BANCARIA')
          )  t
 ) LOOP

 vcount:= vcount + 1;

   Insert into mfinpis_t_revoca_bancaria
   (  ID_REVOCA_BANCARIA,
      ID_PROGETTO,
      DT_RICEZIONE_COMUNICAZ,
      DT_REVOCA,
      IMP_DEBITO_RESIDUO_BANCA,
      IMP_DEBITO_RESIDUO_FP,
      PERC_COFINANZ_FP,
      NUM_PROTOCOLLO,
      NOTE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_REVOCA_BANCARIA_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO,
      ID_MODALITA_AGEVOLAZIONE
  )values
   (  vcount, --rec_T_REVOCA_BANCARIA.ID_REVOCA_BANCARIA,
      rec_T_REVOCA_BANCARIA.ID_PROGETTO,
      to_date(substr(rec_T_REVOCA_BANCARIA.DT_RICEZIONE_COMUNICAZ,1,10),'yyyy/mm/dd'),
      to_date(substr(rec_T_REVOCA_BANCARIA.DT_REVOCA,1,10),'yyyy/mm/dd'),
      rec_T_REVOCA_BANCARIA.IMP_DEBITO_RESIDUO_BANCA,
      rec_T_REVOCA_BANCARIA.IMP_DEBITO_RESIDUO_FP,
      rec_T_REVOCA_BANCARIA.PERC_COFINANZ_FP,
      rec_T_REVOCA_BANCARIA.NUM_PROTOCOLLO,
      rec_T_REVOCA_BANCARIA.NOTE,
      to_date(substr(rec_T_REVOCA_BANCARIA.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),
      rec_T_REVOCA_BANCARIA.DT_FINE_VALIDITA,
      -14,--rec_T_REVOCA_BANCARIA.ID_UTENTE_INS,
      rec_T_REVOCA_BANCARIA.ID_UTENTE_AGG,
      to_date(substr(rec_T_REVOCA_BANCARIA.DT_INSERIMENTO,1,10),'yyyy/mm/dd'),
      rec_T_REVOCA_BANCARIA.DT_AGGIORNAMENTO,
      NULL,
      vId_Caricamento,
      NULL,
      rec_T_REVOCA_BANCARIA.ID_MODALITA_AGEVOLAZIONE
   );


   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_revoca_bancaria riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 58. T_SALDO_STRALCIO

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SALDO_STRALCIO';

   vcount := 1;

 FOR rec_T_SALDO_STRALCIO IN
(Select
           extractvalue(t.column_value,'/saldo_stralcio/@ID_SALDO_STRALCIO')as ID_SALDO_STRALCIO,
           extractvalue(t.column_value,'/saldo_stralcio/ID_PROGETTO')   as ID_PROGETTO,
           extractvalue(t.column_value,'/saldo_stralcio/ID_ATTIVITA_SALDO_STRALCIO')   as ID_ATTIVITA_SALDO_STRALCIO,
           extractvalue(t.column_value,'/saldo_stralcio/DT_PROPOSTA') as DT_PROPOSTA,
           extractvalue(t.column_value,'/saldo_stralcio/DT_ACCETTAZIONE') as DT_ACCETTAZIONE,
           extractvalue(t.column_value,'/saldo_stralcio/IMP_DEBITORE') as IMP_DEBITORE,
           extractvalue(t.column_value,'/saldo_stralcio/IMP_CONFIDI') as IMP_CONFIDI,
           extractvalue(t.column_value,'/saldo_stralcio/ID_ATTIVITA_ESITO') as ID_ATTIVITA_ESITO,
           extractvalue(t.column_value,'/saldo_stralcio/DT_ESITO') as DT_ESITO,
           extractvalue(t.column_value,'/saldo_stralcio/DT_PAGAM_DEBITORE') as DT_PAGAM_DEBITORE,
           extractvalue(t.column_value,'/saldo_stralcio/DT_PAGAM_CONFIDI') as DT_PAGAM_CONFIDI,
           extractvalue(t.column_value,'/saldo_stralcio/ID_ATTIVITA_RECUPERO') as ID_ATTIVITA_RECUPERO,
           extractvalue(t.column_value,'/saldo_stralcio/IMP_RECUPERATO') as IMP_RECUPERATO,
           extractvalue(t.column_value,'/saldo_stralcio/IMP_PERDITA') as IMP_PERDITA,
           extractvalue(t.column_value,'/saldo_stralcio/NOTE')as NOTE,
           extractvalue(t.column_value,'/saldo_stralcio/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/saldo_stralcio/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/saldo_stralcio/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/saldo_stralcio/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/saldo_stralcio/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/saldo_stralcio/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO,
           extractvalue(t.column_value,'/saldo_stralcio/ID_MODALITA_AGEVOLAZIONE')as ID_MODALITA_AGEVOLAZIONE
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SALDO_STRALCIO/saldo_stralcio'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SALDO_STRALCIO')
          )  t
 ) LOOP


   Insert into mfinpis_t_saldo_stralcio
   (ID_SALDO_STRALCIO,
      ID_PROGETTO,
      ID_ATTIVITA_SALDO_STRALCIO,
      DT_PROPOSTA,
      DT_ACCETTAZIONE,
      IMP_DEBITORE,
      IMP_CONFIDI,
      ID_ATTIVITA_ESITO,
      DT_ESITO,
      DT_PAGAM_DEBITORE,
      DT_PAGAM_CONFIDI,
      ID_ATTIVITA_RECUPERO,
      IMP_RECUPERATO  ,
      IMP_PERDITA,
      NOTE  ,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS  ,
      ID_UTENTE_AGG  ,
      DT_INSERIMENTO  ,
      DT_AGGIORNAMENTO,
      ID_SALDO_STRALCIO_PBAN  ,
      ID_CARICAMENTO  ,
      DATA_CARICAMENTO,
      ID_MODALITA_AGEVOLAZIONE
  )values
   (  vcount,--rec_T_SALDO_STRALCIO.ID_SALDO_STRALCIO,
      rec_T_SALDO_STRALCIO.ID_PROGETTO,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_SALDO_STRALCIO,
      to_date(rec_T_SALDO_STRALCIO.DT_PROPOSTA,'yyyy/mm/dd'),
      to_date(rec_T_SALDO_STRALCIO.DT_ACCETTAZIONE,'yyyy/mm/dd'),
      rec_T_SALDO_STRALCIO.IMP_DEBITORE,
      rec_T_SALDO_STRALCIO.IMP_CONFIDI,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_ESITO,
      to_date(rec_T_SALDO_STRALCIO.DT_ESITO,'yyyy/mm/dd'),
      rec_T_SALDO_STRALCIO.DT_PAGAM_DEBITORE,
      rec_T_SALDO_STRALCIO.DT_PAGAM_CONFIDI,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_RECUPERO,
      rec_T_SALDO_STRALCIO.IMP_RECUPERATO  ,
      rec_T_SALDO_STRALCIO.IMP_PERDITA,
      rec_T_SALDO_STRALCIO.NOTE  ,
      to_date(rec_T_SALDO_STRALCIO.DT_INIZIO_VALIDITA,'yyyy/mm/dd'),
      rec_T_SALDO_STRALCIO.DT_FINE_VALIDITA,
      -14,--rec_T_SALDO_STRALCIO.ID_UTENTE_INS  ,
      rec_T_SALDO_STRALCIO.ID_UTENTE_AGG  ,
      to_date(rec_T_SALDO_STRALCIO.DT_INSERIMENTO,'yyyy/mm/dd'),
      rec_T_SALDO_STRALCIO.DT_AGGIORNAMENTO,
      NULL,
      vId_Caricamento,
      NULL,
      rec_T_SALDO_STRALCIO.ID_MODALITA_AGEVOLAZIONE
   );

   vcount := vcount + 1;

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_saldo_stralcio riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 59. T_SOGG_STATO_AZIENDA

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGG_STATO_AZIENDA';

   vcount := 1;

 FOR rec_T_SOGG_STATO_AZIENDA IN
(Select
           extractvalue(t.column_value,'/soggetto/ID_SOGGETTO')as ID_SOGGETTO,
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO_STATO_AZIENDA')   as ID_SOGGETTO_STATO_AZIENDA,
           extractvalue(t.column_value,'/soggetto/ID_STATO_AZIENDA')   as ID_STATO_AZIENDA,
           extractvalue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/soggetto/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/soggetto/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/soggetto/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGG_STATO_AZIENDA/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGG_STATO_AZIENDA')
          )  t
 ) LOOP

   Insert into mfinpis_t_sogg_stato_azienda
   (ID_SOGGETTO_STATO_AZIENDA,
    ID_SOGGETTO,
    ID_STATO_AZIENDA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_SOGGETTO_STATO_AZ_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO
  )values
   ( vcount, --rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO_STATO_AZIENDA,
      rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO_STATO_AZIENDA,
      NVL(rec_T_SOGG_STATO_AZIENDA.ID_STATO_AZIENDA,0),
      -14, --rec_T_SOGG_STATO_AZIENDA.ID_UTENTE_INS,
      rec_T_SOGG_STATO_AZIENDA.ID_UTENTE_AGG,
      to_date(substr(rec_T_SOGG_STATO_AZIENDA.DT_INSERIMENTO,1,10),'yyyy/mm/dd'),
      rec_T_SOGG_STATO_AZIENDA.DT_AGGIORNAMENTO,
      NVL(to_date(substr(rec_T_SOGG_STATO_AZIENDA.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),SYSDATE),
      to_date(substr(rec_T_SOGG_STATO_AZIENDA.DT_FINE_VALIDITA,1,10),'yyyy/mm/dd'),
      NULL,
      vId_Caricamento,
      NULL
   );

   vcount := vcount + 1;

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_sogg_stato_azienda riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 60. T_SOGGETTO_CLA_RISCHIO

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGGETTO_CLA_RISCHIO';

   vcount:=1;

 FOR rec_T_SOGGETTO_CLA_RISCHIO IN
(Select
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO_CLA_RISCHIO')as ID_SOGGETTO_CLA_RISCHIO,
           extractvalue(t.column_value,'/soggetto/ID_SOGGETTO')as ID_SOGGETTO,
           extractvalue(t.column_value,'/soggetto/ID_CLASSE_RISCHIO')   as ID_CLASSE_RISCHIO,
           extractvalue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/soggetto/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/soggetto/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/soggetto/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGGETTO_CLA_RISCHIO/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGGETTO_CLA_RISCHIO')
          )  t
 ) LOOP

   Insert into mfinpis_t_soggetto_cla_rischio
   (ID_SOGGETTO_CLA_RISCHIO,
      ID_SOGGETTO,
      ID_CLASSE_RISCHIO,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_SOGGETTO_CLA_RISC_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
  )values
   (  vcount,
      rec_T_SOGGETTO_CLA_RISCHIO.ID_SOGGETTO_CLA_RISCHIO,
      rec_T_SOGGETTO_CLA_RISCHIO.ID_CLASSE_RISCHIO,
      to_date(substr(rec_T_SOGGETTO_CLA_RISCHIO.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),
      to_date(substr(rec_T_SOGGETTO_CLA_RISCHIO.DT_FINE_VALIDITA,1,10),'yyyy/mm/dd'),
      -14,--rec_T_SOGGETTO_CLA_RISCHIO.ID_UTENTE_INS,
      rec_T_SOGGETTO_CLA_RISCHIO.ID_UTENTE_AGG,
      to_date(substr(rec_T_SOGGETTO_CLA_RISCHIO.DT_INSERIMENTO,1,10),'yyyy/mm/dd'),
      rec_T_SOGGETTO_CLA_RISCHIO.DT_AGGIORNAMENTO,
      NULL,
      vId_Caricamento,
      NULL
   );

   vcount:= vcount + 1;

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_soggetto_cla_rischio riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 61. T_SOGGETTO_RATING
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGGETTO_RATING';

   vcount:=1;

 FOR rec_T_SOGGETTO_RATING IN
(Select
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO_CLA_RISCHIO')as ID_SOGGETTO_CLA_RISCHIO,
           extractvalue(t.column_value,'/soggetto/@ID_SOGGETTO')as ID_SOGGETTO,
           extractvalue(t.column_value,'/soggetto/ID_RATING')   as ID_RATING,
           extractvalue(t.column_value,'/soggetto/DT_CLASSIFICAZIONE')as DT_CLASSIFICAZIONE,
           extractvalue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA')as DT_INIZIO_VALIDITA,
           extractvalue(t.column_value,'/soggetto/DT_FINE_VALIDITA')as DT_FINE_VALIDITA,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_INS')as ID_UTENTE_INS,
           extractvalue(t.column_value,'/soggetto/ID_UTENTE_AGG')as ID_UTENTE_AGG,
           extractvalue(t.column_value,'/soggetto/DT_INSERIMENTO')as DT_INSERIMENTO,
           extractvalue(t.column_value,'/soggetto/DT_AGGIORNAMENTO')as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGGETTO_RATING/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL and
                 ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGGETTO_RATING')
          )  t
 ) LOOP

   Insert into mfinpis_t_soggetto_rating
   (ID_SOGGETTO_RATING,
      ID_SOGGETTO,
      ID_RATING,
      DT_CLASSIFICAZIONE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_SOGGETTO_RATING_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
  )values
   (  vcount,
      rec_T_SOGGETTO_RATING.ID_SOGGETTO_CLA_RISCHIO,
      rec_T_SOGGETTO_RATING.Id_Rating,
      to_date(substr(rec_T_SOGGETTO_RATING.Dt_Classificazione,1,10),'yyyy/mm/dd'),
      to_date(substr(rec_T_SOGGETTO_RATING.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),
      to_date(substr(rec_T_SOGGETTO_RATING.DT_FINE_VALIDITA,1,10),'yyyy/mm/dd'),
      -14, --rec_T_SOGGETTO_RATING.ID_UTENTE_INS,
      rec_T_SOGGETTO_RATING.ID_UTENTE_AGG,
      to_date(substr(rec_T_SOGGETTO_RATING.DT_INSERIMENTO,1,10),'yyyy/mm/dd'),
      to_date(substr(rec_T_SOGGETTO_RATING.DT_AGGIORNAMENTO,1,10),'yyyy/mm/dd'),
      NULL,
      vId_Caricamento,
      NULL
   );

   vcount:= vcount + 1;

   END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_soggetto_rating riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;
 
--------------------------------------------
---- FINE AREA CREDITI
--------------------------------------------


---------------------------------------------
---- AMMORTAMENTO
---------------------------------------------
begin
--  70. T_AMMORTAMENTO_PROGETTI
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_AMMORTAMENTO_PROGETTI';

   vcount:= 0;

   For Rec_T_AMMORTAMENTO_PROGETTI IN
   (
   SELECT  extractValue(t.column_value,'/delibera/@id_delibera')      as id_delibera,
           extractValue(t.column_value,'/delibera/oggetto_delibera')   as oggetto_delibera,
           extractValue(t.column_value,'/delibera/ID_PROGETTO')         as id_progetto,
           extractValue(t.column_value,'/delibera/data_delibera')           as data_delibera,
           extractValue(t.column_value,'/delibera/mm_ammortamento')       as mm_ammortamento,
           extractValue(t.column_value,'/delibera/mm_preammortamento')         as mm_preammortamento,
           extractValue(t.column_value,'/delibera/frequenza')     as frequenza,
           extractValue(t.column_value,'/delibera/perc_spread')     as perc_spread,
           extractValue(t.column_value,'/delibera/perc_interessi')       as perc_interessi,
           extractValue(t.column_value,'/delibera/tipo_tasso')     as tipo_tasso,
           extractValue(t.column_value,'/delibera/tasso')           as tasso,
           extractValue(t.column_value,'/delibera/tipo_calcolo')   as tipo_calcolo,
           extractValue(t.column_value,'/delibera/tipo_periodo') as tipo_periodo -- mc 04092023
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/AMMORTAMENTO_PROGETTO/delibera'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_AMMORTAMENTO_PROGETTI')
          )  t
     ) LOOP



      begin
      select ID_TIPO_CALC_AMM
      into   vID_TIPO_CALC_AMM
      from   pbandi_d_tipo_calcolo_amm
      where  UPPER(DESC_BREVE_TIPO_CALC_AMM) = UPPER(Rec_T_AMMORTAMENTO_PROGETTI.Tipo_Calcolo);
      Exception when others then
             vID_TIPO_CALC_AMM := NULL;
      End;

      begin
      select ID_TASSO_AMM
      into   vID_TASSO_AMM
      from   pbandi_d_tasso_amm
      where  TRIM(UPPER(DESC_BREVE_TASSO_AMM)) = UPPER(Rec_T_AMMORTAMENTO_PROGETTI.Tasso);
      Exception when others then
             vID_TASSO_AMM:= NULL;
      End;
      
      Begin
      select ID_TIPO_PERIODO_AMMORTAMENTO
      into vID_TIPO_PERIODO_AMM
      FROM pbandi_d_tipo_periodo_ammort
      where TRIM (UPPER(DESCR_BREVE_TIPO_PERIOD_AMM)) = UPPER(Rec_T_AMMORTAMENTO_PROGETTI.Tipo_Periodo);
      Exception when others then
             vID_TIPO_PERIODO_AMM:= NULL;
      End;
      
      Begin
      select ID_FREQUENZA_AMMORTAMENTO
      into vID_FREQUENZA_AMM
      FROM pbandi_d_frequenza_ammort
      where DESCR_BREVE_FREQUENZA_AMMORT = Rec_T_AMMORTAMENTO_PROGETTI.Frequenza;
      Exception when others then
             vID_FREQUENZA_AMM:= NULL;
      End;

      vcount:= vcount+1;

      INSERT INTO mfinpis_T_DELIBERA_PROG
      (
        id_delibera,
          id_progetto,
          oggetto_delibera,
          dt_delibera,
          dt_annullamento,
          dt_inizio_validita,
          dt_fine_validita,
          id_utente_ins,
          id_utente_agg ,
          ID_DELIBERA_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
          )VALUES
          (
          --Rec_T_AMMORTAMENTO_PROGETTI.id_delibera,
          vcount,
          Rec_T_AMMORTAMENTO_PROGETTI.id_progetto,
          Rec_T_AMMORTAMENTO_PROGETTI.oggetto_delibera,
          to_date(Rec_T_AMMORTAMENTO_PROGETTI.data_delibera,'yyyy-mm-dd'),
          NULL,
          SYSDATE,
          NULL,
          -14,--id_utente_ins,
          NULL,--id_utente_agg ,
          NULL,
          vId_Caricamento,
          null
          );




        INSERT INTO mfinpis_t_ammortamento_prog
        ( id_ammort_progetti,
          id_delibera,
          mm_ammortamento,
          mm_preammortamento,
          id_tipo_tasso,
          perc_spread,
          frequenza,
          perc_interessi,
          dt_inizio_validita,
          dt_fine_validita,
          id_utente_ins,
          id_utente_agg,
          id_ammort_progetti_pban,
          id_caricamento,
          data_caricamento,
          ID_TIPO_CALC_AMM,
          ID_TASSO_AMM,
          ID_TIPO_PERIODO_AMMORTAMENTO
        )
        values
        ( vcount,--Rec_T_AMMORTAMENTO_PROGETTI.id_ammort_progetti,
          vcount,--Rec_T_AMMORTAMENTO_PROGETTI.id_delibera,
          Rec_T_AMMORTAMENTO_PROGETTI.mm_ammortamento,
          Rec_T_AMMORTAMENTO_PROGETTI.mm_preammortamento,
          DECODE(Rec_T_AMMORTAMENTO_PROGETTI.tipo_tasso,'F',1,2),
          Rec_T_AMMORTAMENTO_PROGETTI.perc_spread,
          vID_FREQUENZA_AMM, -- mc 04092023
          --Rec_T_AMMORTAMENTO_PROGETTI.frequenza,
          Rec_T_AMMORTAMENTO_PROGETTI.perc_interessi,
          SYSDATE,
          NULL,
          -14,
          NULL,
          NULL,
          vId_Caricamento,
          null,
          vID_TIPO_CALC_AMM,
          vID_TASSO_AMM,
          vID_TIPO_PERIODO_AMM
        );


       END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;

  
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_T_DELIBERA_PROG,mfinpis_t_ammortamento_prog  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


  ----------------------------------
  --  altri DATI AGGIUNTIVI   -----
  ----------------------------------

begin
  --  71. T_AGGIUNTIVI_RNA_AUTOMATICO
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_AGGIUNTIVI_RNA_AUTOMATICO';

  FOR Rec_RNA_AUTOMATICO IN
    (SELECT
           extractValue(t.column_value,'/progetto/@ID_PROGETTO')      as ID_PROGETTO,
           extractValue(t.column_value,'/progetto/CODICE_BANDO_RNA')   as CODICE_BANDO_RNA,
           extractValue(t.column_value,'/progetto/FONDO_FINPIS')         as FONDO_FINPIS,
           extractValue(t.column_value,'/progetto/ID_CONCESSIONE_GEST')           as ID_CONCESSIONE_GEST,
           extractValue(t.column_value,'/progetto/TIPO_AGEVOLAZIONE')           as TIPO_AGEVOLAZIONE,
           extractValue(t.column_value,'/progetto/COR')         as COR,
           extractValue(t.column_value,'/progetto/COVAR')     as COVAR_,
           extractValue(t.column_value,'/progetto/ESITO_VALIDAZIONE')     as ESITO_VALIDAZIONE,
           extractValue(t.column_value,'/progetto/ID_COSTO_GEST')       as ID_COSTO_GEST,
           extractValue(t.column_value,'/progetto/ID_COMP_AIUTO_GEST')     as ID_COMP_AIUTO_GEST,
           extractValue(t.column_value,'/progetto/ID_STRUM_AIUTO_GEST')           as ID_STRUM_AIUTO_GEST,
           extractValue(t.column_value,'/progetto/IMPORTO_NOMINALE')   as IMPORTO_NOMINALE,
           extractValue(t.column_value,'/progetto/IMPORTO_AGEVOLAZIONE')      as IMPORTO_AGEVOLAZIONE,
           extractValue(t.column_value,'/progetto/INTENSITA_AIUTO')   as INTENSITA_AIUTO,
           extractValue(t.column_value,'/progetto/CODICE_FISCALE')         as CODICE_FISCALE,
           extractValue(t.column_value,'/progetto/domanda_finpis')           as domanda_finpis,
           extractValue(t.column_value,'/progetto/AIDCONC')     as AIDCONC,
           extractValue(t.column_value,'/progetto/AIDRICH')           as AIDRICH,
           extractValue(t.column_value,'/progetto/CODRNA')   as CODRNA
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/AGGIUNTIVI_RNA_AUTOMATICO/progetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_AGGIUNTIVI_RNA_AUTOMATICO')
          )  t
    )LOOP

       nconta := nconta+1;

       INSERT INTO mfinpis_t_rna_progetto
       (ID_RNA_PROGETTO,
        CODICE_BANDO_RNA,
        ID_PROGETTO,
        TIPO_AGEVOLAZIONE,
        ID_CONCESSIONE_GEST,
        ID_COMP_AIUTO_GEST,
        COR,
        CODICE_FISCALE_BENEFICIARIO,
        COVAR,
        DT_ESITO_VALIDAZIONE,
        ID_COSTO_GEST,
        ID_STRUM_AIUTO_GEST,
        ID_MODALITA_AGEVOLAZIONE,
        IMPORTO_NOMINALE,
        IMPORTO_AGEVOLAZIONE,
        INTENSITA_AIUTO,
        DT_INIZIO_VALIDITA,
        DT_FINE_VALIDITA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_RNA_PROGETTO_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
       ) VALUES
       (nconta,
        Rec_RNA_AUTOMATICO.CODICE_BANDO_RNA,
        Rec_RNA_AUTOMATICO.id_progetto,
        Rec_RNA_AUTOMATICO.TIPO_AGEVOLAZIONE,
        Rec_RNA_AUTOMATICO.ID_CONCESSIONE_GEST,
        Rec_RNA_AUTOMATICO.ID_COMP_AIUTO_GEST,
        Rec_RNA_AUTOMATICO.COR,
        Rec_RNA_AUTOMATICO.CODICE_FISCALE,
        Rec_RNA_AUTOMATICO.COVAR_,
        to_date(Rec_RNA_AUTOMATICO.ESITO_VALIDAZIONE,'yyyy-mm-dd'),
        Rec_RNA_AUTOMATICO.ID_COSTO_GEST,
        Rec_RNA_AUTOMATICO.ID_STRUM_AIUTO_GEST,
        DECODE (Rec_RNA_AUTOMATICO.TIPO_AGEVOLAZIONE,'C',1,'F',5,'G',10),
        Rec_RNA_AUTOMATICO.IMPORTO_NOMINALE,
        Rec_RNA_AUTOMATICO.IMPORTO_AGEVOLAZIONE,
        Rec_RNA_AUTOMATICO.INTENSITA_AIUTO,
        sysdate, --DT_INIZIO_VALIDITA,
        null, --DT_FINE_VALIDITA,
        -14,  --ID_UTENTE_INS,
        null, --ID_UTENTE_AGG,
        null,
        vID_CARICAMENTO,
        null
       );


     END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_rna_progetto riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
--  72. T_AGGIUNTIVI_RNA_MANUALE
  
  Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_AGGIUNTIVI_RNA_MANUALE';
   
 vcount:= 0;

 For rec_RNA_MANUALE IN
  (SELECT     extractValue(t.column_value,'/progetto/@ID_PROGETTO')      as ID_PROGETTO,
           extractValue(t.column_value,'/progetto/fondo_finpis')   as fondo_finpis,
           extractValue(t.column_value,'/progetto/domanda_finpis')         as domanda_finpis,
           extractValue(t.column_value,'/progetto/ACOVAR1')       as ACOVAR1,
           extractValue(t.column_value,'/progetto/DDATCOVAR1')         as DDATCOVAR1,
           extractValue(t.column_value,'/progetto/ACOVAR2')     as ACOVAR2,
           extractValue(t.column_value,'/progetto/DDATCOVAR2')     as DDATCOVAR2,
           extractValue(t.column_value,'/progetto/ACOVAR3')       as ACOVAR3,
           extractValue(t.column_value,'/progetto/DDATCOVAR3')     as DDATCOVAR3,
           extractValue(t.column_value,'/progetto/AVERCOR1')           as AVERCOR1,
           extractValue(t.column_value,'/progetto/DDTVERCOR1')   as DDTVERCOR1,
           extractValue(t.column_value,'/progetto/AVERCOR2')      as AVERCOR2,
           extractValue(t.column_value,'/progetto/DDTVERCOR2')   as DDTVERCOR2,
           extractValue(t.column_value,'/progetto/AVERCOR3')         as AVERCOR3,
           extractValue(t.column_value,'/progetto/DDTVERCOR3')       as DDTVERCOR3,
           extractValue(t.column_value,'/progetto/ACOR2')         as ACOR2,
           extractValue(t.column_value,'/progetto/DDATACOR2')     as DDATACOR2,
           extractValue(t.column_value,'/progetto/ACUP')       as ACUP,
           extractValue(t.column_value,'/progetto/AIDCONC')     as AIDCONC,
           extractValue(t.column_value,'/progetto/AIDRICH')           as AIDRICH,
           extractValue(t.column_value,'/progetto/CODRNA')   as CODRNA,
           extractValue(t.column_value,'/progetto/CODRNA2')     as CODRNA2,
           extractValue(t.column_value,'/progetto/DDATRNA2')       as DDATRNA2
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/AGGIUNTIVI_RNA/progetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_AGGIUNTIVI_RNA_MANUALE')
          )  t
     )LOOP
     
     vcount:= vcount +1;
     
     insert into mfinpis_t_rna_progetto_man
     (ID_RNA_PROGETTO_MAN,
        ID_PROGETTO,
        FONDO_FINPIS,
        DOMANDA_FINPIS,
        ACOVAR1,
        DDATCOVAR1,
        ACOVAR2,
        DDATCOVAR2,
        ACOVAR3,
        DDATCOVAR3,
        AVERCOR1,
        DDTVERCOR1,
        AVERCOR2,
        DDTVERCOR2,
        AVERCOR3,
        DDTVERCOR3,
        ACOR2,
        DDATACOR2,
        ACUP,
        AIDCONC,
        AIDRICH,
        CODRNA,
        CODRNA2,
        DDATRNA2,
        ID_RNA_PROGETTO_MAN_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
     )values
     (vcount,
        rec_RNA_MANUALE.ID_PROGETTO,
        rec_RNA_MANUALE.FONDO_FINPIS,
        rec_RNA_MANUALE.DOMANDA_FINPIS,
        rec_RNA_MANUALE.ACOVAR1,
        NULL,--rec_RNA_MANUALE.DDATCOVAR1,
        rec_RNA_MANUALE.ACOVAR2,
        NULL,--rec_RNA_MANUALE.DDATCOVAR2,
        rec_RNA_MANUALE.ACOVAR3,
        NULL,--rec_RNA_MANUALE.DDATCOVAR3,
        rec_RNA_MANUALE.AVERCOR1,
        NULL,--rec_RNA_MANUALE.DDTVERCOR1,
        rec_RNA_MANUALE.AVERCOR2,
        NULL,--rec_RNA_MANUALE.DDTVERCOR2,
        rec_RNA_MANUALE.AVERCOR3,
        NULL,--rec_RNA_MANUALE.DDTVERCOR3,
        rec_RNA_MANUALE.ACOR2,
        NULL,--rec_RNA_MANUALE.DDATACOR2,
        rec_RNA_MANUALE.ACUP,
        rec_RNA_MANUALE.AIDCONC,
        rec_RNA_MANUALE.AIDRICH,
        rec_RNA_MANUALE.CODRNA,
        rec_RNA_MANUALE.CODRNA2,
        NULL,--rec_RNA_MANUALE.DDATRNA2,
        null,
        vId_Caricamento,
        null
     );



     END LOOP;

   Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   vcount:=0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_rna_progetto_man '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- T_SOGGETTO_DSAN

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SOGGETTO_DSAN';

 For rec_T_SOGGETTO_DSAN IN
  (
  SELECT  extractValue(t.column_value,'/soggetto/@ID_SOGGETTO_DSAN')      as ID_SOGGETTO_DSAN,
           extractValue(t.column_value,'/soggetto/ID_DOMANDA')   as ID_DOMANDA,
           extractValue(t.column_value,'/soggetto/ID_ESITO_DSAN')         as ID_ESITO_DSAN,
           extractValue(t.column_value,'/soggetto/DT_EMISSIONE_DSAN')       as DT_EMISSIONE_DSAN,
           extractValue(t.column_value,'/soggetto/DT_SCADENZA')         as DT_SCADENZA,
           extractValue(t.column_value,'/soggetto/NUM_PROTOCOLLO')     as NUM_PROTOCOLLO,
           extractValue(t.column_value,'/soggetto/ID_UTENTE_INS')     as ID_UTENTE_INS,
           extractValue(t.column_value,'/soggetto/ID_UTENTE_AGG')       as ID_UTENTE_AGG,
           extractValue(t.column_value,'/soggetto/DT_INIZIO_VALIDITA')     as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/soggetto/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SOGGETTO_DSAN/soggetto'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SOGGETTO_DSAN')
          )  t
     )LOOP




      INSERT INTO MFINPIS_T_SOGGETTO_DSAN
      (
        id_soggetto_dsan,
        id_domanda,
        id_esito_dsan,
        dt_emissione_dsan,
        dt_scadenza,
        num_protocollo,
        id_utente_ins,
        id_utente_agg,
        dt_inizio_validita,
        dt_fine_validita,
        id_soggetto_dsan_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
        )VALUES
        (rec_T_SOGGETTO_DSAN.id_soggetto_dsan,
        rec_T_SOGGETTO_DSAN.id_domanda,
        rec_T_SOGGETTO_DSAN.id_esito_dsan,
        to_date(rec_T_SOGGETTO_DSAN.dt_emissione_dsan,'yyyy-mm-dd'),
        to_date(rec_T_SOGGETTO_DSAN.dt_scadenza,'yyyy-mm-dd'),
        rec_T_SOGGETTO_DSAN.num_protocollo,
        -14, --rec_T_SOGGETTO_DSAN.id_utente_ins,
        rec_T_SOGGETTO_DSAN.id_utente_agg,
        NVL(to_date(rec_T_SOGGETTO_DSAN.dt_inizio_validita,'yyyy-mm-dd'),SYSDATE),
        to_date(rec_T_SOGGETTO_DSAN.dt_fine_validita,'yyyy-mm-dd'),
        NULL,
        vID_CARICAMENTO,
        NULL
        );

     END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - MFINPIS_T_SOGGETTO_DSAN '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- T_NOTE_ANA
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_NOTE_ANA';

For rec_T_NOTE_ANA IN
  (SELECT  extractValue(t.column_value,'/nota/@ID_NOTA')      as ID_NOTA,
           extractValue(t.column_value,'/nota/ID_SOGGETTO_BENEFICIARIO')   as ID_SOGGETTO_BENEFICIARIO,
           extractValue(t.column_value,'/nota/ID_PROGETTO')         as ID_PROGETTO,
           extractValue(t.column_value,'/nota/ID_ESTERNO')       as ID_ESTERNO,
           extractValue(t.column_value,'/nota/CODICE_VISUALIZZATO')         as CODICE_VISUALIZZATO,
           --extractValue(t.column_value,'/nota/TESTO_NOTA')     as TESTO_NOTA,
           xmltype.extract(t.column_value, '/nota/TESTO_NOTA').getclobval() as TESTO_NOTA,
           extractValue(t.column_value,'/nota/ID_MODALITA_AGEVOLAZIONE')     as ID_MODALITA_AGEVOLAZIONE,
           extractValue(t.column_value,'/nota/FLAG_VISIBILITA_BENEFICIARIO')       as FLAG_VISIBILITA_BENEFICIARIO,
           extractValue(t.column_value,'/nota/DT_INIZIO_VALIDITA')     as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/nota/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/nota/ID_UTENTE_INS')           as ID_UTENTE_INS,
           extractValue(t.column_value,'/nota/ID_UTENTE_AGG')           as ID_UTENTE_AGG,
           extractValue(t.column_value,'/nota/AREA')           as AREA,
           extractValue(t.column_value,'/nota/FASE')           as FASE,
           extractValue(t.column_value,'/nota/TIPO')           as TIPO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_NOTE_ANA/nota'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_NOTE_ANA')
          )  t
  )LOOP

   INSERT INTO mfinpis_t_note
   (ID_NOTA,
    ID_SOGGETTO_BENEFICIARIO,
    ID_PROGETTO,
    TESTO_NOTA,
    ID_MODALITA_AGEVOLAZIONE,
    FLAG_VISIBILITA_BENEFICIARIO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    ID_NOTA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    AREA,
    FASE,
    TIPO
    )VALUES
    (rec_T_NOTE_ANA.ID_NOTA,
    rec_T_NOTE_ANA.ID_SOGGETTO_BENEFICIARIO,
    rec_T_NOTE_ANA.ID_PROGETTO,
    rec_T_NOTE_ANA.TESTO_NOTA,
    rec_T_NOTE_ANA.ID_MODALITA_AGEVOLAZIONE,
    rec_T_NOTE_ANA.FLAG_VISIBILITA_BENEFICIARIO,
    to_date(substr(rec_T_NOTE_ANA.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),
    to_date(substr(rec_T_NOTE_ANA.DT_FINE_VALIDITA,1,10),'yyyy/mm/dd'),
    -14, --ID_UTENTE_INS,
    rec_T_NOTE_ANA.ID_UTENTE_AGG,
    NULL,
    vID_CARICAMENTO,
    NULL,
    rec_T_NOTE_ANA.AREA,
    rec_T_NOTE_ANA.FASE,
    rec_T_NOTE_ANA.TIPO
    );


   END LOOP;



Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_note ANA '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- T_NOTE_DOM
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_NOTE_DOM';

   For rec_T_NOTE_DOM IN
  (SELECT  extractValue(t.column_value,'/nota/@ID_NOTA')      as ID_NOTA,
           extractValue(t.column_value,'/nota/ID_SOGGETTO_BENEFICIARIO')   as ID_SOGGETTO_BENEFICIARIO,
           extractValue(t.column_value,'/nota/ID_PROGETTO')         as ID_PROGETTO,
           extractValue(t.column_value,'/nota/ID_ESTERNO')       as ID_ESTERNO,
           extractValue(t.column_value,'/nota/CODICE_VISUALIZZATO')         as CODICE_VISUALIZZATO,
           --extractValue(t.column_value,'/nota/TESTO_NOTA')     as TESTO_NOTA,
           xmltype.extract(t.column_value, '/nota/TESTO_NOTA').getclobval() as TESTO_NOTA,
           extractValue(t.column_value,'/nota/ID_MODALITA_AGEVOLAZIONE')     as ID_MODALITA_AGEVOLAZIONE,
           extractValue(t.column_value,'/nota/FLAG_VISIBILITA_BENEFICIARIO')       as FLAG_VISIBILITA_BENEFICIARIO,
           extractValue(t.column_value,'/nota/DT_INIZIO_VALIDITA')     as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/nota/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/nota/ID_UTENTE_INS')           as ID_UTENTE_INS,
           extractValue(t.column_value,'/nota/ID_UTENTE_AGG')           as ID_UTENTE_AGG,
           extractValue(t.column_value,'/nota/AREA')           as AREA,
           extractValue(t.column_value,'/nota/FASE')           as FASE,
           extractValue(t.column_value,'/nota/TIPO')           as TIPO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_NOTE_DOM/nota'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_NOTE_DOM')
          )  t
  )LOOP



   INSERT INTO mfinpis_t_note
   (ID_NOTA,
    ID_SOGGETTO_BENEFICIARIO,
    ID_PROGETTO,
    TESTO_NOTA,
    ID_MODALITA_AGEVOLAZIONE,
    FLAG_VISIBILITA_BENEFICIARIO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    ID_NOTA_PBAN,
    ID_CARICAMENTO,
    DATA_CARICAMENTO,
    AREA,
    FASE,
    TIPO
    )VALUES
    (rec_T_NOTE_DOM.ID_NOTA,
    rec_T_NOTE_DOM.ID_SOGGETTO_BENEFICIARIO,
    rec_T_NOTE_DOM.ID_PROGETTO,
    rec_T_NOTE_DOM.TESTO_NOTA,
    rec_T_NOTE_DOM.ID_MODALITA_AGEVOLAZIONE,
    rec_T_NOTE_DOM.FLAG_VISIBILITA_BENEFICIARIO,
    to_date(substr(rec_T_NOTE_DOM.DT_INIZIO_VALIDITA,1,10),'yyyy/mm/dd'),
    to_date(substr(rec_T_NOTE_DOM.DT_FINE_VALIDITA,1,10),'yyyy/mm/dd'),
    -14, --ID_UTENTE_INS,
    rec_T_NOTE_DOM.ID_UTENTE_AGG,
    NULL,
    vID_CARICAMENTO,
    NULL,
    rec_T_NOTE_DOM.AREA,
    rec_T_NOTE_DOM.FASE,
    rec_T_NOTE_DOM.TIPO
    );


   END LOOP;

  Update pbandi_t_migrazione_finpis
   set    data_migrazione = sysdate
   where  id_caricamento = vId_Caricamento;
   
   COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_note DOM '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

-------------------------------------------------------
-------- REVOCHE
-------------------------------------------------------
begin
-- 80. T_CONTRODEDUZ
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_CONTRODEDUZ';

   For Rec_T_CONTRODEDUZ IN
   (
   SELECT  extractValue(t.column_value,'/revoche_controded/@ID_CONTRODEDUZIONE')      as ID_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/NUMERO_CONTRODEDUZIONE')   as NUMERO_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/ID_ENTITA')         as ID_ENTITA,
           extractValue(t.column_value,'/revoche_controded/ID_TARGET')           as ID_TARGET,
           extractValue(t.column_value,'/revoche_controded/ID_STATO_CONTRODEDUZIONE')       as ID_STATO_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/DT_STATO_CONTRODEDUZIONE')         as DT_STATO_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/ID_ATTIVITA_CONTRODEDUZIONE')     as ID_ATTIVITA_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/DT_ATTIVITA_CONTRODEDUZIONE')     as DT_ATTIVITA_CONTRODEDUZIONE,
           extractValue(t.column_value,'/revoche_controded/DT_INIZIO_VALIDITA')       as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/revoche_controded/DT_FINE_VALIDITA')     as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/revoche_controded/ID_UTENTE_INS')           as ID_UTENTE_INS,
           extractValue(t.column_value,'/revoche_controded/ID_UTENTE_AGG')           as ID_UTENTE_AGG,
           extractValue(t.column_value,'/revoche_controded/DT_INSERIMENTO')             as DT_INSERIMENTO,
           extractValue(t.column_value,'/revoche_controded/DT_AGGIORNAMENTO')         as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_CONTRODEDUZ/revoche_controded'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_CONTRODEDUZ')
          )  t

     ) LOOP


     INSERT INTO mfinpis_t_controdeduz
     (ID_CONTRODEDUZ,
      NUMERO_CONTRODEDUZ,
      ID_ENTITA,
      ID_TARGET,
      ID_STATO_CONTRODEDUZ,
      DT_STATO_CONTRODEDUZ,
      ID_ATTIV_CONTRODEDUZ,
      DT_ATTIV_CONTRODEDUZ,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_CONTRODEDUZ_PBAN,
      ID_CARICAMENTO,
      DATA_CARICAMENTO
     )VALUES
     (Rec_T_CONTRODEDUZ.ID_CONTRODEDUZIONE,
      Rec_T_CONTRODEDUZ.NUMERO_CONTRODEDUZIONE,
      Rec_T_CONTRODEDUZ.ID_ENTITA,
      Rec_T_CONTRODEDUZ.ID_TARGET,
      Rec_T_CONTRODEDUZ.ID_STATO_CONTRODEDUZIONE,
      to_date(substr(Rec_T_CONTRODEDUZ.DT_STATO_CONTRODEDUZIONE,1,10),'yyyy-mm-dd'),
      Rec_T_CONTRODEDUZ.ID_ATTIVITA_CONTRODEDUZIONE,
      DECODE(Rec_T_CONTRODEDUZ.DT_ATTIVITA_CONTRODEDUZIONE,'00:00:00.000',SYSDATE,to_date(substr(Rec_T_CONTRODEDUZ.DT_ATTIVITA_CONTRODEDUZIONE,1,10),'yyyy-mm-dd')),
      DECODE(Rec_T_CONTRODEDUZ.DT_INIZIO_VALIDITA,'0000-00-00 00:00:00.000',SYSDATE,to_date(substr(Rec_T_CONTRODEDUZ.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd')),
      Rec_T_CONTRODEDUZ.DT_FINE_VALIDITA,
      -14,
      Rec_T_CONTRODEDUZ.ID_UTENTE_AGG,
      NVL(Rec_T_CONTRODEDUZ.DT_INSERIMENTO,SYSDATE),
      Rec_T_CONTRODEDUZ.DT_AGGIORNAMENTO,
      NULL,
      vId_Caricamento,
      NULL
     );

       END LOOP;

       Update pbandi_t_migrazione_finpis
       set    data_migrazione = sysdate
       where  id_caricamento = vId_Caricamento;
       
       COMMIT;
       
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_controdeduz '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
  -- 81. T_GESTIONE_REVOCA_PROCEDIMENTI

Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_GESTIONE_REVOCA_PROCEDIMENTI';

   For Rec_T_GESTIONE_REV_PROC IN
   (
   SELECT  extractValue(t.column_value,'/revoca/@ID_GESTIONE_REVOCA')      as ID_GESTIONE_REVOCA,
           extractValue(t.column_value,'/revoca/NUMERO_REVOCA')   as NUMERO_REVOCA,
           extractValue(t.column_value,'/revoca/ID_TIPOLOGIA_REVOCA')         as ID_TIPOLOGIA_REVOCA,
           extractValue(t.column_value,'/revoca/ID_PROGETTO')           as ID_PROGETTO,
           extractValue(t.column_value,'/revoca/ID_CAUSALE_BLOCCO')       as ID_CAUSALE_BLOCCO,
           extractValue(t.column_value,'/revoca/ID_CATEG_ANAGRAFICA')         as ID_CATEG_ANAGRAFICA,
           extractValue(t.column_value,'/revoca/DT_GESTIONE')     as DT_GESTIONE,
           extractValue(t.column_value,'/revoca/NUM_PROTOCOLLO')     as NUM_PROTOCOLLO,
           extractValue(t.column_value,'/revoca/FLAG_ORDINE_RECUPERO')       as FLAG_ORDINE_RECUPERO,
           extractValue(t.column_value,'/revoca/ID_MANCATO_RECUPERO')     as ID_MANCATO_RECUPERO,
           extractValue(t.column_value,'/revoca/ID_STATO_REVOCA')           as ID_STATO_REVOCA,
           extractValue(t.column_value,'/revoca/DT_STATO_REVOCA')           as DT_STATO_REVOCA,
           extractValue(t.column_value,'/revoca/DT_NOTIFICA')             as DT_NOTIFICA,
           extractValue(t.column_value,'/revoca/GG_RISPOSTA')         as GG_RISPOSTA,
           extractValue(t.column_value,'/revoca/FLAG_PROROGA')     as FLAG_PROROGA,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_CONTRIB')       as IMP_DA_REVOCARE_CONTRIB,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_FINANZ')     as IMP_DA_REVOCARE_FINANZ,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_GARANZIA')           as IMP_DA_REVOCARE_GARANZIA,
           extractValue(t.column_value,'/revoca/FLAG_DETERMINA')           as FLAG_DETERMINA,
           extractValue(t.column_value,'/revoca/ESTREMI')             as ESTREMI,
           extractValue(t.column_value,'/revoca/DT_DETERMINA')         as DT_DETERMINA,
           extractValue(t.column_value,'/revoca/NOTE')     as NOTE,
           extractValue(t.column_value,'/revoca/ID_ATTIVITA_REVOCA')       as ID_ATTIVITA_REVOCA,
           extractValue(t.column_value,'/revoca/DT_ATTIVITA')     as DT_ATTIVITA,
           extractValue(t.column_value,'/revoca/ID_MOTIVO_REVOCA')           as ID_MOTIVO_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_REVOCA')           as FLAG_CONTRIBUTO_REVOCA,
            extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_MINORI_SPESE')             as FLAG_CONTRIBUTO_MIN_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_DECURTAZIONE')             as FLAG_CONTRIBUTO_DECURTAZIONE,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_REVOCA')         as FLAG_FINANZIAMENTO_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_MINORI_SPESE')     as FLAG_FINANZIAMENTO_MIN_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_DECURTAZIONE')       as FLAG_FINANZIAMENTO_DECURT,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_REVOCA')     as FLAG_GARANZIA_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_MINORI_SPESE')           as FLAG_GARANZIA_MINORI_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_DECURTAZIONE')           as FLAG_GARANZIA_DECURTAZIONE,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_IMPORTO_REVOCA_SENZA_RECUPERO')             as CONTRIBUTO_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_IMPORTO_REVOCA_CON_RECUPERO')         as CONTRIBUTO_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_INTERESSI')     as CONTRIBUTO_INTERESSI,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_IMPORTO_REVOCA_SENZA_RECUPERO')       as FIN_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_IMPORTO_REVOCA_CON_RECUPERO')     as FIN_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_INTERESSI')           as FINANZIAMENTO_INTERESSI,
           extractValue(t.column_value,'/revoca/GARANZIA_IMPORTO_REVOCA_SENZA_RECUPERO')           as GARANZIA_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/GARANZIA_IMPORTO_REVOCA_CON_RECUPERO')             as GARANZIA_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/GARANZIA_INTERESSI')         as GARANZIA_INTERESSI,
           extractValue(t.column_value,'/revoca/DT_INIZIO_VALIDITA')     as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/revoca/DT_FINE_VALIDITA')       as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/revoca/ID_UTENTE_INS')     as ID_UTENTE_INS,
           extractValue(t.column_value,'/revoca/ID_UTENTE_AGG')           as ID_UTENTE_AGG,
           extractValue(t.column_value,'/revoca/DT_INSERIMENTO')           as DT_INSERIMENTO,
           extractValue(t.column_value,'/revoca/DT_AGGIORNAMENTO')             as DT_AGGIORNAMENTO,
           extractValue(t.column_value,'/revoca/IMPORTO_AMMESSO')         as IMPORTO_AMMESSO,
           extractValue(t.column_value,'/revoca/IMPORTO_CONCESSO')     as IMPORTO_CONCESSO,
           extractValue(t.column_value,'/revoca/IMPORTO_GIA_REVOCATO')       as IMPORTO_GIA_REVOCATO,
           extractValue(t.column_value,'/revoca/IMPORTO_EROGATO')     as IMPORTO_EROGATO,
           extractValue(t.column_value,'/revoca/IMPORTO_RECUPERATO')           as IMPORTO_RECUPERATO,
           extractValue(t.column_value,'/revoca/IMPORTO_RIMBORSATO')           as IMPORTO_RIMBORSATO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_REVOCHE/revoca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_GESTIONE_REVOCA_PROCEDIMENTI')
          )  t

     ) LOOP



     INSERT INTO mfinpis_t_gestione_revoca 
     ( ID_GESTIONE_REVOCA,
        NUMERO_REVOCA,
        ID_TIPOLOGIA_REVOCA,
        ID_PROGETTO,
        ID_CAUSALE_BLOCCO,
        ID_CATEG_ANAGRAFICA,
        DT_GESTIONE,
        NUM_PROTOCOLLO,
        FLAG_ORDINE_RECUPERO,
        ID_MANCATO_RECUPERO,
        ID_STATO_REVOCA,
        DT_STATO_REVOCA,
        DT_NOTIFICA,
        GG_RISPOSTA,
        FLAG_PROROGA,
        IMP_DA_REVOCARE_CONTRIB,
        IMP_DA_REVOCARE_FINANZ  ,
        IMP_DA_REVOCARE_GARANZIA  ,
        FLAG_DETERMINA  ,
        ESTREMI  ,
        DT_DETERMINA  ,
        NOTE  ,
        ID_ATTIVITA_REVOCA  ,
        DT_ATTIVITA  ,
        ID_MOTIVO_REVOCA  ,
        FLAG_CONTRIB_REVOCA  ,
        FLAG_CONTRIB_MINOR_SPESE  ,
        FLAG_CONTRIB_DECURTAZ  ,
        FLAG_FINANZ_REVOCA  ,
        FLAG_FINANZ_MINOR_SPESE  ,
        FLAG_FINANZ_DECURTAZ  ,
        FLAG_GARANZIA_REVOCA  ,
        FLAG_GARANZIA_MINOR_SPESE  ,
        FLAG_GARANZIA_DECURTAZ  ,
        IMP_CONTRIB_REVOCA_NO_RECU  ,
        IMP_CONTRIB_REVOCA_RECU  ,
        IMP_CONTRIB_INTERESSI  ,
        IMP_FINANZ_REVOCA_NO_RECU  ,
        IMP_FINANZ_REVOCA_RECU  ,
        IMP_FINANZ_INTERESSI  ,
        IMP_GARANZIA_REVOCA_NO_RECU  ,
        IMP_GARANZIA_REVOCA_RECUPERO  ,
        IMP_GARANZIA_INTERESSI  ,
        DT_INIZIO_VALIDITA  ,
        DT_FINE_VALIDITA  ,
        ID_UTENTE_INS  ,
        ID_UTENTE_AGG  ,
        DT_INSERIMENTO  ,
        DT_AGGIORNAMENTO  ,
        ID_GESTIONE_REVOCA_PBAN  ,
        ID_CARICAMENTO  ,
        DATA_CARICAMENTO
     )VALUES
     (  Rec_T_GESTIONE_REV_PROC.ID_GESTIONE_REVOCA,
        Rec_T_GESTIONE_REV_PROC.NUMERO_REVOCA,
        Rec_T_GESTIONE_REV_PROC.ID_TIPOLOGIA_REVOCA,
        Rec_T_GESTIONE_REV_PROC.ID_PROGETTO,             
        Rec_T_GESTIONE_REV_PROC.ID_CAUSALE_BLOCCO,
        Rec_T_GESTIONE_REV_PROC.ID_CATEG_ANAGRAFICA,
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_GESTIONE,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROC.NUM_PROTOCOLLO,
        Rec_T_GESTIONE_REV_PROC.FLAG_ORDINE_RECUPERO,
        Rec_T_GESTIONE_REV_PROC.ID_MANCATO_RECUPERO,
        Rec_T_GESTIONE_REV_PROC.ID_STATO_REVOCA,
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_STATO_REVOCA,1,10),'yyyy-mm-dd'),
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_NOTIFICA,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROC.GG_RISPOSTA,
        Rec_T_GESTIONE_REV_PROC.FLAG_PROROGA,
        Rec_T_GESTIONE_REV_PROC.IMP_DA_REVOCARE_CONTRIB,
        Rec_T_GESTIONE_REV_PROC.IMP_DA_REVOCARE_FINANZ  ,
        Rec_T_GESTIONE_REV_PROC.IMP_DA_REVOCARE_GARANZIA  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_DETERMINA  ,
        Rec_T_GESTIONE_REV_PROC.ESTREMI  ,
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_DETERMINA,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROC.NOTE  ,
        Rec_T_GESTIONE_REV_PROC.ID_ATTIVITA_REVOCA  ,
        --NVL(to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_ATTIVITA,1,10),'yyyy-mm-dd'),SYSDATE), -- 10022023
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_ATTIVITA,1,10),'yyyy-mm-dd'), -- 01092023 Rodolfi
        Rec_T_GESTIONE_REV_PROC.ID_MOTIVO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_CONTRIBUTO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_CONTRIBUTO_MIN_SPESE  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_CONTRIBUTO_DECURTAZIONE  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_FINANZIAMENTO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_FINANZIAMENTO_MIN_SPESE  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_FINANZIAMENTO_DECURT  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_GARANZIA_REVOCA  ,
        Rec_T_GESTIONE_REV_PROC.FLAG_GARANZIA_MINORI_SPESE,
        Rec_T_GESTIONE_REV_PROC.FLAG_GARANZIA_DECURTAZIONE  ,
        Rec_T_GESTIONE_REV_PROC.CONTRIBUTO_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROC.CONTRIBUTO_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROC.CONTRIBUTO_INTERESSI  ,
        Rec_T_GESTIONE_REV_PROC.FIN_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROC.FIN_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROC.FINANZIAMENTO_INTERESSI  ,
        Rec_T_GESTIONE_REV_PROC.GARANZIA_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROC.GARANZIA_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROC.GARANZIA_INTERESSI  ,
        NVL(to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),SYSDATE),
        Rec_T_GESTIONE_REV_PROC.DT_FINE_VALIDITA  ,
        -14  ,
        Rec_T_GESTIONE_REV_PROC.ID_UTENTE_AGG  ,
        NVL(to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE),
        to_date(substr(Rec_T_GESTIONE_REV_PROC.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd'),
        NULL,
        vId_Caricamento,
        NULL
     );

       END LOOP;

       Update pbandi_t_migrazione_finpis
       set    data_migrazione = sysdate
       where  id_caricamento = vId_Caricamento;
       
       COMMIT;
       
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_gestione_revoca PROC '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
 -- 82. T_GESTIONE_REVOCA_PROVVEDIMENTI
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_GESTIONE_REVOCA_PROVVEDIMENTI';


   For Rec_T_GESTIONE_REV_PROV IN
   (
   SELECT  extractValue(t.column_value,'/revoca/@ID_GESTIONE_REVOCA')      as ID_GESTIONE_REVOCA,
           extractValue(t.column_value,'/revoca/NUMERO_REVOCA')   as NUMERO_REVOCA,
           extractValue(t.column_value,'/revoca/ID_TIPOLOGIA_REVOCA')         as ID_TIPOLOGIA_REVOCA,
           extractValue(t.column_value,'/revoca/ID_PROGETTO')           as ID_PROGETTO,
           extractValue(t.column_value,'/revoca/ID_CAUSALE_BLOCCO')       as ID_CAUSALE_BLOCCO,
           extractValue(t.column_value,'/revoca/ID_CATEG_ANAGRAFICA')         as ID_CATEG_ANAGRAFICA,
           extractValue(t.column_value,'/revoca/DT_GESTIONE')     as DT_GESTIONE,
           extractValue(t.column_value,'/revoca/NUM_PROTOCOLLO')     as NUM_PROTOCOLLO,
           extractValue(t.column_value,'/revoca/FLAG_ORDINE_RECUPERO')       as FLAG_ORDINE_RECUPERO,
           extractValue(t.column_value,'/revoca/ID_MANCATO_RECUPERO')     as ID_MANCATO_RECUPERO,
           extractValue(t.column_value,'/revoca/ID_STATO_REVOCA')           as ID_STATO_REVOCA,
           extractValue(t.column_value,'/revoca/DT_STATO_REVOCA')           as DT_STATO_REVOCA,
           extractValue(t.column_value,'/revoca/DT_NOTIFICA')             as DT_NOTIFICA,
           extractValue(t.column_value,'/revoca/GG_RISPOSTA')         as GG_RISPOSTA,
           extractValue(t.column_value,'/revoca/FLAG_PROROGA')     as FLAG_PROROGA,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_CONTRIB')       as IMP_DA_REVOCARE_CONTRIB,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_FINANZ')     as IMP_DA_REVOCARE_FINANZ,
           extractValue(t.column_value,'/revoca/IMP_DA_REVOCARE_GARANZIA')           as IMP_DA_REVOCARE_GARANZIA,
           extractValue(t.column_value,'/revoca/FLAG_DETERMINA')           as FLAG_DETERMINA,
           extractValue(t.column_value,'/revoca/ESTREMI')             as ESTREMI,
           extractValue(t.column_value,'/revoca/DT_DETERMINA')         as DT_DETERMINA,
           extractValue(t.column_value,'/revoca/NOTE')     as NOTE,
           extractValue(t.column_value,'/revoca/ID_ATTIVITA_REVOCA')       as ID_ATTIVITA_REVOCA,
           extractValue(t.column_value,'/revoca/DT_ATTIVITA')     as DT_ATTIVITA,
           extractValue(t.column_value,'/revoca/ID_MOTIVO_REVOCA')           as ID_MOTIVO_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_REVOCA')           as FLAG_CONTRIBUTO_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_MINORI_SPESE')             as FLAG_CONTRIBUTO_MIN_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_CONTRIBUTO_DECURTAZIONE')             as FLAG_CONTRIBUTO_DECURTAZIONE,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_REVOCA')         as FLAG_FINANZIAMENTO_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_MINORI_SPESE')     as FLAG_FINANZIAMENTO_MIN_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_FINANZIAMENTO_DECURTAZIONE')       as FLAG_FINANZIAMENTO_DECURT,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_REVOCA')     as FLAG_GARANZIA_REVOCA,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_MINORI_SPESE')           as FLAG_GARANZIA_MINORI_SPESE,
           extractValue(t.column_value,'/revoca/FLAG_GARANZIA_DECURTAZIONE')           as FLAG_GARANZIA_DECURTAZIONE,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_IMPORTO_REVOCA_SENZA_RECUPERO')             as CONTRIBUTO_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_IMPORTO_REVOCA_CON_RECUPERO')         as CONTRIBUTO_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/CONTRIBUTO_INTERESSI')     as CONTRIBUTO_INTERESSI,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_IMPORTO_REVOCA_SENZA_RECUPERO')       as FIN_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_IMPORTO_REVOCA_CON_RECUPERO')     as FIN_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/FINANZIAMENTO_INTERESSI')           as FINANZIAMENTO_INTERESSI,
           extractValue(t.column_value,'/revoca/GARANZIA_IMPORTO_REVOCA_SENZA_RECUPERO')           as GARANZIA_IMP_REV_SENZA_REC,
           extractValue(t.column_value,'/revoca/GARANZIA_IMPORTO_REVOCA_CON_RECUPERO')             as GARANZIA_IMP_REV_CON_REC,
           extractValue(t.column_value,'/revoca/GARANZIA_INTERESSI')         as GARANZIA_INTERESSI,
           extractValue(t.column_value,'/revoca/DT_INIZIO_VALIDITA')     as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/revoca/DT_FINE_VALIDITA')       as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/revoca/ID_UTENTE_INS')     as ID_UTENTE_INS,
           extractValue(t.column_value,'/revoca/ID_UTENTE_AGG')           as ID_UTENTE_AGG,
           extractValue(t.column_value,'/revoca/DT_INSERIMENTO')           as DT_INSERIMENTO,
           extractValue(t.column_value,'/revoca/DT_AGGIORNAMENTO')             as DT_AGGIORNAMENTO,
           extractValue(t.column_value,'/revoca/IMPORTO_AMMESSO')         as IMPORTO_AMMESSO,
           extractValue(t.column_value,'/revoca/IMPORTO_CONCESSO')     as IMPORTO_CONCESSO,
           extractValue(t.column_value,'/revoca/IMPORTO_GIA_REVOCATO')       as IMPORTO_GIA_REVOCATO,
           extractValue(t.column_value,'/revoca/IMPORTO_EROGATO')     as IMPORTO_EROGATO,
           extractValue(t.column_value,'/revoca/IMPORTO_RECUPERATO')           as IMPORTO_RECUPERATO,
           extractValue(t.column_value,'/revoca/IMPORTO_RIMBORSATO')           as IMPORTO_RIMBORSATO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_REVOCHE/revoca'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_GESTIONE_REVOCA_PROVVEDIMENTI')
          )  t

     )
      LOOP

       INSERT INTO mfinpis_t_gestione_revoca
     ( ID_GESTIONE_REVOCA,
        NUMERO_REVOCA,
        ID_TIPOLOGIA_REVOCA,
        ID_PROGETTO,
        ID_CAUSALE_BLOCCO,
        ID_CATEG_ANAGRAFICA,
        DT_GESTIONE,
        NUM_PROTOCOLLO,
        FLAG_ORDINE_RECUPERO,
        ID_MANCATO_RECUPERO,
        ID_STATO_REVOCA,
        DT_STATO_REVOCA,
        DT_NOTIFICA,
        GG_RISPOSTA,
        FLAG_PROROGA,
        IMP_DA_REVOCARE_CONTRIB,
        IMP_DA_REVOCARE_FINANZ  ,
        IMP_DA_REVOCARE_GARANZIA  ,
        FLAG_DETERMINA  ,
        ESTREMI  ,
        DT_DETERMINA  ,
        NOTE  ,
        ID_ATTIVITA_REVOCA  ,
        DT_ATTIVITA  ,
        ID_MOTIVO_REVOCA  ,
        FLAG_CONTRIB_REVOCA  ,
        FLAG_CONTRIB_MINOR_SPESE  ,
        FLAG_CONTRIB_DECURTAZ  ,
        FLAG_FINANZ_REVOCA  ,
        FLAG_FINANZ_MINOR_SPESE  ,
        FLAG_FINANZ_DECURTAZ  ,
        FLAG_GARANZIA_REVOCA  ,
        FLAG_GARANZIA_MINOR_SPESE  ,
        FLAG_GARANZIA_DECURTAZ  ,
        IMP_CONTRIB_REVOCA_NO_RECU  ,
        IMP_CONTRIB_REVOCA_RECU  ,
        IMP_CONTRIB_INTERESSI  ,
        IMP_FINANZ_REVOCA_NO_RECU  ,
        IMP_FINANZ_REVOCA_RECU  ,
        IMP_FINANZ_INTERESSI  ,
        IMP_GARANZIA_REVOCA_NO_RECU  ,
        IMP_GARANZIA_REVOCA_RECUPERO  ,
        IMP_GARANZIA_INTERESSI  ,
        DT_INIZIO_VALIDITA  ,
        DT_FINE_VALIDITA  ,
        ID_UTENTE_INS  ,
        ID_UTENTE_AGG  ,
        DT_INSERIMENTO  ,
        DT_AGGIORNAMENTO  ,
        ID_GESTIONE_REVOCA_PBAN  ,
        ID_CARICAMENTO  ,
        DATA_CARICAMENTO
     )VALUES
     (  Rec_T_GESTIONE_REV_PROV.ID_GESTIONE_REVOCA,
        Rec_T_GESTIONE_REV_PROV.NUMERO_REVOCA,
        Rec_T_GESTIONE_REV_PROV.ID_TIPOLOGIA_REVOCA,
        Rec_T_GESTIONE_REV_PROV.ID_PROGETTO,         
        Rec_T_GESTIONE_REV_PROV.ID_CAUSALE_BLOCCO,
        Rec_T_GESTIONE_REV_PROV.ID_CATEG_ANAGRAFICA,
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_GESTIONE,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROV.NUM_PROTOCOLLO,
        Rec_T_GESTIONE_REV_PROV.FLAG_ORDINE_RECUPERO,
        Rec_T_GESTIONE_REV_PROV.ID_MANCATO_RECUPERO,
        Rec_T_GESTIONE_REV_PROV.ID_STATO_REVOCA,
        nvl(to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_STATO_REVOCA,1,10),'yyyy-mm-dd'),sysdate),
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_NOTIFICA,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROV.GG_RISPOSTA,
        Rec_T_GESTIONE_REV_PROV.FLAG_PROROGA,
        Rec_T_GESTIONE_REV_PROV.IMP_DA_REVOCARE_CONTRIB,
        Rec_T_GESTIONE_REV_PROV.IMP_DA_REVOCARE_FINANZ  ,
        Rec_T_GESTIONE_REV_PROV.IMP_DA_REVOCARE_GARANZIA  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_DETERMINA  ,
        Rec_T_GESTIONE_REV_PROV.ESTREMI  ,
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_DETERMINA,1,10),'yyyy-mm-dd'),
        Rec_T_GESTIONE_REV_PROV.NOTE  ,
        Rec_T_GESTIONE_REV_PROV.ID_ATTIVITA_REVOCA,
        --NVL(to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_ATTIVITA,1,10),'yyyy-mm-dd'),SYSDATE),
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_ATTIVITA,1,10),'yyyy-mm-dd'), -- 01092023 Rodolfi
        Rec_T_GESTIONE_REV_PROV.ID_MOTIVO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_CONTRIBUTO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_CONTRIBUTO_MIN_SPESE  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_CONTRIBUTO_DECURTAZIONE  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_FINANZIAMENTO_REVOCA  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_FINANZIAMENTO_MIN_SPESE  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_FINANZIAMENTO_DECURT  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_GARANZIA_REVOCA  ,
        Rec_T_GESTIONE_REV_PROV.FLAG_GARANZIA_MINORI_SPESE,
        Rec_T_GESTIONE_REV_PROV.FLAG_GARANZIA_DECURTAZIONE  ,
        Rec_T_GESTIONE_REV_PROV.CONTRIBUTO_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROV.CONTRIBUTO_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROV.CONTRIBUTO_INTERESSI  ,
        Rec_T_GESTIONE_REV_PROV.FIN_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROV.FIN_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROV.FINANZIAMENTO_INTERESSI  ,
        Rec_T_GESTIONE_REV_PROV.GARANZIA_IMP_REV_SENZA_REC  ,
        Rec_T_GESTIONE_REV_PROV.GARANZIA_IMP_REV_CON_REC  ,
        Rec_T_GESTIONE_REV_PROV.GARANZIA_INTERESSI  ,
        NVL(to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),SYSDATE)  ,
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_FINE_VALIDITA,1,10),'yyyy-mm-dd')  ,
        -14  ,
        Rec_T_GESTIONE_REV_PROV.ID_UTENTE_AGG  ,
        NVL(to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_INSERIMENTO,1,10),'yyyy-mm-dd'),SYSDATE)  ,
        to_date(substr(Rec_T_GESTIONE_REV_PROV.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd') ,
        NULL,
        vId_Caricamento,
        NULL
     );

      END LOOP;

      Update pbandi_t_migrazione_finpis
       set    data_migrazione = sysdate
       where  id_caricamento = vId_Caricamento;
       
       COMMIT;
       
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_gestione_revoca PROV  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 83. T_PROROGA
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_PROROGA';

   For Rec_T_PROROGA IN
   (
   SELECT  extractValue(t.column_value,'/RICHIESTA_PROROGA/@ID_RICHIESTA_PROROGA') as ID_RICHIESTA_PROROGA,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/ID_ENTITA')   as ID_ENTITA,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/ID_TARGET')         as ID_TARGET,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/DT_RICHIESTA')           as DT_RICHIESTA,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/NUM_GIORNI_RICH')       as NUM_GIORNI_RICH,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/MOTIVAZIONE')         as MOTIVAZIONE,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/NUM_GIORNI_APPROV')     as NUM_GIORNI_APPROV,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/ID_STATO_PROROGA')         as ID_STATO_PROROGA,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/DT_ESITO_RICHIESTA')           as DT_ESITO_RICHIESTA,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/ID_UTENTE_INS')       as ID_UTENTE_INS,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/ID_UTENTE_AGG')         as ID_UTENTE_AGG,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/DT_INSERIMENTO')         as DT_INSERIMENTO,
           extractValue(t.column_value,'/RICHIESTA_PROROGA/DT_AGGIORNAMENTO')     as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_PROROGA/RICHIESTA_PROROGA'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_PROROGA')
          )  t

     ) LOOP

     INSERT INTO mfinpis_t_proroga
     ( ID_RICHIESTA_PROROGA,
        ID_ENTITA,
        ID_TARGET  ,
        DT_RICHIESTA,
        NUM_GIORNI_RICH  ,
        MOTIVAZIONE  ,
        NUM_GIORNI_APPROV  ,
        ID_STATO_PROROGA  ,
        DT_ESITO_RICHIESTA  ,
        ID_UTENTE_INS  ,
        ID_UTENTE_AGG  ,
        DT_INSERIMENTO  ,
        DT_AGGIORNAMENTO  ,
        ID_RICHIESTA_PROROGA_PBAN  ,
        ID_CARICAMENTO  ,
        DATA_CARICAMENTO
     )VALUES
     ( Rec_T_PROROGA.ID_RICHIESTA_PROROGA,
        Rec_T_PROROGA.ID_ENTITA,
        Rec_T_PROROGA.ID_TARGET  ,
        to_date(substr(Rec_T_PROROGA.DT_RICHIESTA,1,10),'yyyy-mm-dd'),
        Rec_T_PROROGA.NUM_GIORNI_RICH  ,
        NVL(Rec_T_PROROGA.MOTIVAZIONE,'Motivazione non disponibile')  ,
        Rec_T_PROROGA.NUM_GIORNI_APPROV  ,
        Rec_T_PROROGA.ID_STATO_PROROGA  ,
        to_date(substr(Rec_T_PROROGA.DT_ESITO_RICHIESTA,1,10),'yyyy-mm-dd')  ,
        -14,
        Rec_T_PROROGA.ID_UTENTE_AGG  ,
        nvl(to_date(substr(Rec_T_PROROGA. DT_INSERIMENTO,1,10),'yyyy-mm-dd'),sysdate),
        to_date(substr(Rec_T_PROROGA.DT_AGGIORNAMENTO,1,10),'yyyy-mm-dd'),
        NULL,
        vId_Caricamento,
        NULL
     );

       END LOOP;

       Update pbandi_t_migrazione_finpis
       set    data_migrazione = sysdate
       where  id_caricamento = vId_Caricamento;
       
       COMMIT;
       
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_proroga  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 84. T_RICHIESTA_INTEGRAZ
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_RICHIESTA_INTEGRAZ';

   For Rec_T_RICHIESTA_INTEGRAZ IN
   (
   SELECT  extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/@ID_CONTRODEDUZIONE') as ID_CONTRODEDUZIONE,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_ENTITA')   as ID_ENTITA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_TARGET')         as ID_TARGET,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_RICHIESTA')           as DT_RICHIESTA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_INVIO')       as DT_INVIO,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_UTENTE_RICHIESTA')         as ID_UTENTE_RICHIESTA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_UTENTE_INVIO ')     as ID_UTENTE_INVIO ,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_NOTIFICA')         as DT_NOTIFICA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/NUM_GIORNI_SCADENZA')           as NUM_GIORNI_SCADENZA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_CHIUSURA_UFFICIO')         as DT_CHIUSURA_UFFICIO,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_STATO_RICHIESTA ')     as ID_STATO_RICHIESTA ,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_SCADENZA')         as DT_SCADENZA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_INIZIO_VALIDITA')           as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_UTENTE_INS')       as ID_UTENTE_INS,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/ID_UTENTE_AGG')         as ID_UTENTE_AGG,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_INSERIMENTO')         as DT_INSERIMENTO,
           extractValue(t.column_value,'/ID_RICHIESTA_INTEGRAZ/DT_AGGIORNAMENTO')     as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_RICHIESTA_INTEGRAZ/ID_RICHIESTA_INTEGRAZ'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_RICHIESTA_INTEGRAZ')
          )  t

     ) LOOP

     INSERT INTO mfinpis_t_richiesta_integraz
     ( ID_RICHIESTA_INTEGRAZ,
        ID_ENTITA,
        ID_TARGET,
        DT_RICHIESTA,
        DT_INVIO,
        ID_UTENTE_RICHIESTA,
        ID_UTENTE_INVIO,
        DT_NOTIFICA,
        NUM_GIORNI_SCADENZA,
        DT_CHIUSURA_UFFICIO,
        ID_STATO_RICHIESTA,
        DT_SCADENZA,
        DT_INIZIO_VALIDITA,
        DT_FINE_VALIDITA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        DT_INSERIMENTO,
        DT_AGGIORNAMENTO,
        ID_RICHIESTA_INTEGRAZ_PBAN,
        ID_CARICAMENTO,
        DATA_CARICAMENTO
     )VALUES
     ( Rec_T_RICHIESTA_INTEGRAZ.ID_CONTRODEDUZIONE,
        Rec_T_RICHIESTA_INTEGRAZ.ID_ENTITA,
        Rec_T_RICHIESTA_INTEGRAZ.ID_TARGET,
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_RICHIESTA,1,10),'yyyy-mm-dd'),NULL),
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_INVIO,1,10),'yyyy-mm-dd'),NULL),
        -14, --Rec_T_RICHIESTA_INTEGRAZ.ID_UTENTE_RICHIESTA,
        Rec_T_RICHIESTA_INTEGRAZ.ID_UTENTE_INVIO,
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_NOTIFICA,1,10),'yyyy-mm-dd'),NULL),
        Rec_T_RICHIESTA_INTEGRAZ.NUM_GIORNI_SCADENZA,
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_CHIUSURA_UFFICIO,1,10),'yyyy-mm-dd'),NULL),
        Rec_T_RICHIESTA_INTEGRAZ.ID_STATO_RICHIESTA,
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_SCADENZA,1,10),'yyyy-mm-dd'),NULL),
        NVL(to_date(substr(Rec_T_RICHIESTA_INTEGRAZ.DT_INIZIO_VALIDITA,1,10),'yyyy-mm-dd'),NULL),
        Rec_T_RICHIESTA_INTEGRAZ.DT_FINE_VALIDITA,
        -14,
        Rec_T_RICHIESTA_INTEGRAZ.ID_UTENTE_AGG,
        NVL(Rec_T_RICHIESTA_INTEGRAZ.DT_INSERIMENTO,SYSDATE),
        Rec_T_RICHIESTA_INTEGRAZ.DT_AGGIORNAMENTO,
        NULL,
        vID_CARICAMENTO,
        NULL
     );

       END LOOP;

       Update pbandi_t_migrazione_finpis
       set    data_migrazione = sysdate
       where  id_caricamento = vId_Caricamento;
       
       COMMIT;
       
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_richiesta_integraz  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
----------------------------
-- 90. T_LIBERAZ_GARANTE
----------------------------
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_LIBERAZ_GARANTE';

   vcount := 0;
For Rec_T_LIBERAZ_GARANTE IN
   (
   SELECT  extractValue(t.column_value,'/liberazione_garante/@ID_LIBERAZ_GARANTE') as ID_LIBERAZ_GARANTE,
           extractValue(t.column_value,'/liberazione_garante/ID_MODALITA_AGEVOLAZIONE')   as ID_MODALITA_AGEVOLAZIONE,
           extractValue(t.column_value,'/liberazione_garante/ID_PROGETTO')         as ID_PROGETTO,
           extractValue(t.column_value,'/liberazione_garante/DT_LIBERAZ_GARANTE')           as DT_LIBERAZ_GARANTE,
           extractValue(t.column_value,'/liberazione_garante/IMP_LIBERAZIONE')       as IMP_LIBERAZIONE,
           extractValue(t.column_value,'/liberazione_garante/DENOM_GARANTE_LIBERATO')         as DENOM_GARANTE_LIBERATO,
           extractValue(t.column_value,'/liberazione_garante/NOTE ')     as NOTE,
           extractValue(t.column_value,'/liberazione_garante/DT_INIZIO_VALIDITA')           as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/liberazione_garante/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/liberazione_garante/ID_UTENTE_INS')       as ID_UTENTE_INS,
           extractValue(t.column_value,'/liberazione_garante/ID_UTENTE_AGG')         as ID_UTENTE_AGG,
           extractValue(t.column_value,'/liberazione_garante/DT_INSERIMENTO')         as DT_INSERIMENTO,
           extractValue(t.column_value,'/liberazione_garante/DT_AGGIORNAMENTO')     as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_LIBERAZ_GARANTE/liberazione_garante'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_LIBERAZ_GARANTE')
          )  t

     ) LOOP

        vcount := vcount +1;
        INSERT INTO mfinpis_t_liberaz_garante
        (ID_LIBERAZ_GARANTE,
          ID_PROGETTO,
          ID_MODALITA_AGEVOLAZIONE,
          DT_LIBERAZ_GARANTE,
          IMP_LIBERAZIONE,
          DENOM_GARANTE_LIBERATO,
          NOTE,
          DT_INIZIO_VALIDITA,
          DT_FINE_VALIDITA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          DT_INSERIMENTO,
          DT_AGGIORNAMENTO,
          ID_LIBERAZ_GARANTE_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
        )values
        ( vcount,--Rec_T_LIBERAZ_GARANTE.ID_LIBERAZ_GARANTE,
          Rec_T_LIBERAZ_GARANTE.ID_PROGETTO,
          Rec_T_LIBERAZ_GARANTE.ID_MODALITA_AGEVOLAZIONE,
          to_date(Rec_T_LIBERAZ_GARANTE.DT_LIBERAZ_GARANTE,'yyyy-mm-dd'),
          Rec_T_LIBERAZ_GARANTE.IMP_LIBERAZIONE,
          Rec_T_LIBERAZ_GARANTE.DENOM_GARANTE_LIBERATO,
          Rec_T_LIBERAZ_GARANTE.NOTE,
          to_date(Rec_T_LIBERAZ_GARANTE.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
          Rec_T_LIBERAZ_GARANTE.DT_FINE_VALIDITA,
          -14, --,ID_UTENTE_INS,
          null, --ID_UTENTE_AGG,
          sysdate, --Rec_T_LIBERAZ_GARANTE.DT_INSERIMENTO,
          Rec_T_LIBERAZ_GARANTE.DT_AGGIORNAMENTO,
          NULL,
          vID_CARICAMENTO,
          NULL
        );


      END LOOP;

Update pbandi_t_migrazione_finpis
set    data_migrazione = sysdate
where  id_caricamento = vId_Caricamento;

vcount:=0;

COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_liberaz_garante  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
----------------------------
-- 91. T_MESSA_IN_MORA
----------------------------
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_MESSA_IN_MORA';

 vcount:= 0;

For Rec_T_MESSA_IN_MORA IN
   (
   SELECT  extractValue(t.column_value,'/messa_in_mora/@ID_MESSA_IN_MORA') as ID_MESSA_IN_MORA,
           extractValue(t.column_value,'/messa_in_mora/ID_MODALITA_AGEVOLAZIONE')   as ID_MODALITA_AGEVOLAZIONE,
           extractValue(t.column_value,'/messa_in_mora/ID_ATTIVITA_MORA')         as ID_ATTIVITA_MORA,
           extractValue(t.column_value,'/messa_in_mora/DT_MESSA_IN_MORA')           as DT_MESSA_IN_MORA,
           extractValue(t.column_value,'/messa_in_mora/IMP_MESSA_IN_MORA_COMPLESSIVA')       as IMP_MESSA_IN_MORA_COMPLESSIVA,
           extractValue(t.column_value,'/messa_in_mora/IMP_QUOTA_CAPITALE_REVOCA')         as IMP_QUOTA_CAPITALE_REVOCA,
           extractValue(t.column_value,'/messa_in_mora/IMP_AGEVOLAZ_REVOCA')     as IMP_AGEVOLAZ_REVOCA,
           extractValue(t.column_value,'/messa_in_mora/IMP_CREDITO_RESIDUO')     as IMP_CREDITO_RESIDUO,
           extractValue(t.column_value,'/messa_in_mora/IMP_INTERESSI_MORA')     as IMP_INTERESSI_MORA,
           extractValue(t.column_value,'/messa_in_mora/DT_NOTIFICA')     as DT_NOTIFICA,
           extractValue(t.column_value,'/messa_in_mora/ID_ATTIVITA_RECUPERO_MORA')     as ID_ATTIVITA_RECUPERO_MORA,
           extractValue(t.column_value,'/messa_in_mora/DT_PAGAMENTO')     as DT_PAGAMENTO,
           extractValue(t.column_value,'/messa_in_mora/NOTE')     as NOTE,
           extractValue(t.column_value,'/messa_in_mora/DT_INIZIO_VALIDITA')           as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/messa_in_mora/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/messa_in_mora/ID_UTENTE_INS')       as ID_UTENTE_INS,
           extractValue(t.column_value,'/messa_in_mora/ID_UTENTE_AGG')         as ID_UTENTE_AGG,
           extractValue(t.column_value,'/messa_in_mora/DT_INSERIMENTO')         as DT_INSERIMENTO,
           extractValue(t.column_value,'/messa_in_mora/DT_AGGIORNAMENTO')     as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_MESSA_IN_MORA/messa_in_mora'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_MESSA_IN_MORA')
          )  t

     ) LOOP

     vcount:= vcount+1;

        INSERT INTO mfinpis_t_messa_in_mora
       (ID_MESSA_IN_MORA,
          ID_PROGETTO,
          ID_MODALITA_AGEVOLAZIONE,
          ID_ATTIVITA_MORA,
          DT_MESSA_IN_MORA,
          IMP_MESSA_IN_MORA_COMPLESSIVA,
          IMP_QUOTA_CAPITALE_REVOCA,
          IMP_AGEVOLAZ_REVOCA,
          IMP_CREDITO_RESIDUO,
          IMP_INTERESSI_MORA,
          DT_NOTIFICA,
          ID_ATTIVITA_RECUPERO_MORA,
          DT_PAGAMENTO,
          NOTE,
          DT_INIZIO_VALIDITA,
          DT_FINE_VALIDITA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          DT_INSERIMENTO,
          DT_AGGIORNAMENTO,
          ID_MESSA_IN_MORA_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
       )values
       (vcount,
          Rec_T_MESSA_IN_MORA.ID_MESSA_IN_MORA,--Rec_T_MESSA_IN_MORA.ID_PROGETTO,
          Rec_T_MESSA_IN_MORA.ID_MODALITA_AGEVOLAZIONE,
          Rec_T_MESSA_IN_MORA.ID_ATTIVITA_MORA,
          to_date(Rec_T_MESSA_IN_MORA.DT_MESSA_IN_MORA,'yyyy-mm-dd'),
          Rec_T_MESSA_IN_MORA.IMP_MESSA_IN_MORA_COMPLESSIVA,
          Rec_T_MESSA_IN_MORA.IMP_QUOTA_CAPITALE_REVOCA,
          Rec_T_MESSA_IN_MORA.IMP_AGEVOLAZ_REVOCA,
          Rec_T_MESSA_IN_MORA.IMP_CREDITO_RESIDUO,
          Rec_T_MESSA_IN_MORA.IMP_INTERESSI_MORA,
          Rec_T_MESSA_IN_MORA.DT_NOTIFICA,
          Rec_T_MESSA_IN_MORA.ID_ATTIVITA_RECUPERO_MORA,
          Rec_T_MESSA_IN_MORA.DT_PAGAMENTO,
          Rec_T_MESSA_IN_MORA.NOTE,
          to_date(Rec_T_MESSA_IN_MORA.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
          Rec_T_MESSA_IN_MORA.DT_FINE_VALIDITA,
          -14, --Rec_T_MESSA_IN_MORA.ID_UTENTE_INS,
          Rec_T_MESSA_IN_MORA.ID_UTENTE_AGG,
          sysdate, --Rec_T_MESSA_IN_MORA.DT_INSERIMENTO,
          Rec_T_MESSA_IN_MORA.DT_AGGIORNAMENTO,
          NULL,
          vID_CARICAMENTO,
          NULL
        );


      END LOOP;

Update pbandi_t_migrazione_finpis
set    data_migrazione = sysdate
where  id_caricamento = vId_Caricamento;

vcount:=0;

COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_messa_in_mora  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
----------------------------
-- 92. T_SEGNALAZ_CORTE_CONTI
----------------------------
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_SEGNALAZ_CORTE_CONTI';


For Rec_T_SEGNALAZ_CORTE_CONTI IN
   (
   SELECT  extractValue(t.column_value,'/segnalazione_corte_conti/@ID_SEGNALAZIONE_CORTE_CONTI') as ID_SEGNALAZIONE_CORTE_CONTI,
           extractValue(t.column_value,'/segnalazione_corte_conti/ID_MODALITA_AGEVOLAZIONE')   as ID_MODALITA_AGEVOLAZIONE,
           extractValue(t.column_value,'/segnalazione_corte_conti/ID_PROGETTO')         as ID_PROGETTO,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_SEGNALAZIONE')           as DT_SEGNALAZIONE,
           extractValue(t.column_value,'/segnalazione_corte_conti/NUM_PROTOCOLLO_SEGN')       as NUM_PROTOCOLLO_SEGN,
           extractValue(t.column_value,'/segnalazione_corte_conti/IMP_CRED_RES_CAPITALE')         as IMP_CRED_RES_CAPITALE,
           extractValue(t.column_value,'/segnalazione_corte_conti/IMP_ONERI_AGEVOLAZ')     as IMP_ONERI_AGEVOLAZ,
           extractValue(t.column_value,'/segnalazione_corte_conti/IMP_QUOTA_SEGNALAZ')     as IMP_QUOTA_SEGNALAZ,
           extractValue(t.column_value,'/segnalazione_corte_conti/IMP_GARANZIA')     as IMP_GARANZIA,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_PIANO_RIENTRO')     as FLAG_PIANO_RIENTRO,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_PIANO_RIENTRO')     as DT_PIANO_RIENTRO,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_SALDO_STRALCIO')     as FLAG_SALDO_STRALCIO,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_SALDO_STRALCIO')     as DT_SALDO_STRALCIO,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_PAGAM_INTEGRALE')     as FLAG_PAGAM_INTEGRALE,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_PAGAMENTO')     as DT_PAGAMENTO,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_DISSEGNALAZIONE')     as FLAG_DISSEGNALAZIONE,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_DISSEGNALAZIONE')     as DT_DISSEGNALAZIONE,
           extractValue(t.column_value,'/segnalazione_corte_conti/NUM_PROTOCOLLO_DISS')     as NUM_PROTOCOLLO_DISS,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_DECRETO_ARCHIV')     as FLAG_DECRETO_ARCHIV,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_ARCHIV')     as DT_ARCHIV,
           extractValue(t.column_value,'/segnalazione_corte_conti/NUM_PROTOCOLLO_ARCHIV')     as NUM_PROTOCOLLO_ARCHIV,
           extractValue(t.column_value,'/segnalazione_corte_conti/FLAG_COMUNICAZ_REGIONE')     as FLAG_COMUNICAZ_REGIONE,
           extractValue(t.column_value,'/segnalazione_corte_conti/NOTE')     as NOTE,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_INIZIO_VALIDITA')           as DT_INIZIO_VALIDITA,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_FINE_VALIDITA')           as DT_FINE_VALIDITA,
           extractValue(t.column_value,'/segnalazione_corte_conti/ID_UTENTE_INS')       as ID_UTENTE_INS,
           extractValue(t.column_value,'/segnalazione_corte_conti/ID_UTENTE_AGG')         as ID_UTENTE_AGG,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_INSERIMENTO')         as DT_INSERIMENTO,
           extractValue(t.column_value,'/segnalazione_corte_conti/DT_AGGIORNAMENTO')     as DT_AGGIORNAMENTO
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/T_SEGNALAZ_CORTE_CONTI/segnalazione_corte_conti'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_SEGNALAZ_CORTE_CONTI')
          )  t

     ) LOOP

        INSERT INTO mfinpis_t_segnalaz_corte_conti
        (ID_SEGNALAZIONE_CORTE_CONTI,
          ID_PROGETTO,
          ID_MODALITA_AGEVOLAZIONE,
          DT_SEGNALAZIONE,
          NUM_PROTOCOLLO_SEGN,
          IMP_CRED_RES_CAPITALE,
          IMP_ONERI_AGEVOLAZ,
          IMP_QUOTA_SEGNALAZ,
          IMP_GARANZIA,
          FLAG_PIANO_RIENTRO,
          DT_PIANO_RIENTRO,
          FLAG_SALDO_STRALCIO,
          DT_SALDO_STRALCIO,
          FLAG_PAGAM_INTEGRALE,
          DT_PAGAMENTO,
          FLAG_DISSEGNALAZIONE,
          DT_DISSEGNALAZIONE,
          NUM_PROTOCOLLO_DISS,
          FLAG_DECRETO_ARCHIV,
          DT_ARCHIV,
          NUM_PROTOCOLLO_ARCHIV,
          FLAG_COMUNICAZ_REGIONE,
          NOTE,
          DT_INIZIO_VALIDITA,
          DT_FINE_VALIDITA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          DT_INSERIMENTO,
          DT_AGGIORNAMENTO,
          ID_SEGNALAZIONE_CORTE_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
        )values
        (
          to_number(Rec_T_SEGNALAZ_CORTE_CONTI.ID_SEGNALAZIONE_CORTE_CONTI),
          Rec_T_SEGNALAZ_CORTE_CONTI.ID_PROGETTO,
          Rec_T_SEGNALAZ_CORTE_CONTI.ID_MODALITA_AGEVOLAZIONE,
          to_date(Rec_T_SEGNALAZ_CORTE_CONTI.DT_SEGNALAZIONE,'yyyy-mm-dd'),
          Rec_T_SEGNALAZ_CORTE_CONTI.NUM_PROTOCOLLO_SEGN,
          Rec_T_SEGNALAZ_CORTE_CONTI.IMP_CRED_RES_CAPITALE,
          Rec_T_SEGNALAZ_CORTE_CONTI.IMP_ONERI_AGEVOLAZ,
          Rec_T_SEGNALAZ_CORTE_CONTI.IMP_QUOTA_SEGNALAZ,
          Rec_T_SEGNALAZ_CORTE_CONTI.IMP_GARANZIA,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_PIANO_RIENTRO,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_PIANO_RIENTRO,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_SALDO_STRALCIO,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_SALDO_STRALCIO,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_PAGAM_INTEGRALE,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_PAGAMENTO,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_DISSEGNALAZIONE,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_DISSEGNALAZIONE,
          Rec_T_SEGNALAZ_CORTE_CONTI.NUM_PROTOCOLLO_DISS,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_DECRETO_ARCHIV,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_ARCHIV,
          Rec_T_SEGNALAZ_CORTE_CONTI.NUM_PROTOCOLLO_ARCHIV,
          Rec_T_SEGNALAZ_CORTE_CONTI.FLAG_COMUNICAZ_REGIONE,
          Rec_T_SEGNALAZ_CORTE_CONTI.NOTE,
          to_date(Rec_T_SEGNALAZ_CORTE_CONTI.DT_INIZIO_VALIDITA,'yyyy-mm-dd'),
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_FINE_VALIDITA,
          Rec_T_SEGNALAZ_CORTE_CONTI.ID_UTENTE_INS,
          Rec_T_SEGNALAZ_CORTE_CONTI.ID_UTENTE_AGG,
          sysdate, --Rec_T_SEGNALAZ_CORTE_CONTI.DT_INSERIMENTO,
          Rec_T_SEGNALAZ_CORTE_CONTI.DT_AGGIORNAMENTO,
          NULL,
          vID_CARICAMENTO,
          NULL);


      END LOOP;

Update pbandi_t_migrazione_finpis
set    data_migrazione = sysdate
where  id_caricamento = vId_Caricamento;

COMMIT;

Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_segnalaz_corte_conti  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;




begin
-- 93. T_RIASSICURAZIONI
Select id_caricamento
   Into  vId_Caricamento
   From  pbandi_t_migrazione_finpis mf,
         pbandi_d_tipo_file_migr_finpis fmf
   Where mf.data_migrazione IS NULL
   and   mf.id_tipo_file = fmf.id_tipo_file
   and   fmf.desc_breve_file = 'T_RIASSICURAZIONI';

 vcount:= 0;

  For Rec_T_RIASSICURAZIONI IN
   (
   SELECT  extractValue(t.column_value,'/soggetto_riassicurato/@ID_SOGG_RIASSICURATO') as ID_SOGG_RIASSICURATO,
           extractValue(t.column_value,'/soggetto_riassicurato/linea_intervento_soggetto')   as linea_intervento_soggetto,
           extractValue(t.column_value,'/soggetto_riassicurato/ragione_sociale')         as ragione_sociale,
           extractValue(t.column_value,'/soggetto_riassicurato/forma_giuridica')         as forma_giuridica,
           extractValue(t.column_value,'/soggetto_riassicurato/descr_forma_giuridica')         as descr_forma_giuridica,
           extractValue(t.column_value,'/soggetto_riassicurato/codice_fiscale')           as codice_fiscale,
           extractValue(t.column_value,'/soggetto_riassicurato/indirizzo_sede_destinataria')       as indirizzo_sede_destinataria,
           extractValue(t.column_value,'/soggetto_riassicurato/localita_sede_destinataria')         as localita_sede_destinataria,
           extractValue(t.column_value,'/soggetto_riassicurato/provincia_sede_destinataria')     as provincia_sede_destinataria,
           extractValue(t.column_value,'/soggetto_riassicurato/ateco')     as ateco,
           extractValue(t.column_value,'/soggetto_riassicurato/descrizione_ateco')   as descrizione_ateco,
           extractValue(t.column_value,'/soggetto_riassicurato/importo_finanziato')     as importo_finanziato,
           extractValue(t.column_value,'/soggetto_riassicurato/id_progetto')     as id_progetto,
           extractValue(t.column_value,'/soggetto_riassicurato/importo_garanzia')     as importo_garanzia,
           extractValue(t.column_value,'/soggetto_riassicurato/importo_ammesso')     as importo_ammesso,
           extractValue(t.column_value,'/soggetto_riassicurato/percentuale_garanzia')     as percentuale_garanzia,
           extractValue(t.column_value,'/soggetto_riassicurato/percentuale_riassicurato')           as percentuale_riassicurato,
           extractValue(t.column_value,'/soggetto_riassicurato/erogazione_mutuo')           as erogazione_mutuo,
           extractValue(t.column_value,'/soggetto_riassicurato/delibera_mutuo')       as delibera_mutuo,
           extractValue(t.column_value,'/soggetto_riassicurato/emissione_garanzia')         as emissione_garanzia,
           extractValue(t.column_value,'/soggetto_riassicurato/scadenza_mutuo')         as scadenza_mutuo,
           extractValue(t.column_value,'/soggetto_riassicurato/importo_escusso')     as importo_escusso,
           extractValue(t.column_value,'/soggetto_riassicurato/data_escussione')     as data_escussione,
           extractValue(t.column_value,'/soggetto_riassicurato/data_scarico')     as data_scarico,
           extractValue(t.column_value,'/soggetto_riassicurato/data_revoca')     as data_revoca
  FROM TABLE (select XMLSequence(
          extract(s.file_xml,'/RIASSICURAZIONI/soggetto_riassicurato'))
           FROM  PBANDI_T_MIGRAZIONE_FINPIS s
           WHERE DATA_MIGRAZIONE IS NULL AND
                ID_TIPO_FILE = (SELECT id_tipo_file
                                   FROM PBANDI_D_TIPO_FILE_MIGR_FINPIS
                                          WHERE desc_breve_file = 'T_RIASSICURAZIONI')
          )  t

     ) LOOP
     
     vcount:= vcount+1;
     
     INSERT INTO mfinpis_t_riassicurazioni
     (    id_riassicurazione,
          id_sogg_riassicurato,
          linea_intervento_soggetto,
          ragione_sociale,
          forma_giuridica,
          descr_forma_giuridica,
          codice_fiscale,
          indirizzo_sede_destinataria,
          localita_sede_destinataria,
          provincia_sede_destinataria,
          ateco,
          descrizione_ateco,
          importo_finanziato,
          id_progetto,
          importo_garanzia,
          importo_ammesso,
          percentuale_garanzia,
          percentuale_riassicurato,
          erogazione_mutuo,
          delibera_mutuo,
          emissione_garanzia,
          scadenza_mutuo,
          importo_escusso,
          data_escussione,
          data_scarico,
          data_revoca,
          ID_RIASSICURAZIONE_PBAN,
          ID_CARICAMENTO,
          DATA_CARICAMENTO
     )values
       (  vcount,
          Rec_T_RIASSICURAZIONI.id_sogg_riassicurato,
          Rec_T_RIASSICURAZIONI.linea_intervento_soggetto,
          Rec_T_RIASSICURAZIONI.ragione_sociale,
          Rec_T_RIASSICURAZIONI.forma_giuridica,
          Rec_T_RIASSICURAZIONI.descr_forma_giuridica,
          Rec_T_RIASSICURAZIONI.codice_fiscale,
          Rec_T_RIASSICURAZIONI.indirizzo_sede_destinataria,
          Rec_T_RIASSICURAZIONI.localita_sede_destinataria,
          Rec_T_RIASSICURAZIONI.provincia_sede_destinataria,
          Rec_T_RIASSICURAZIONI.ateco,
          Rec_T_RIASSICURAZIONI.descrizione_ateco,
          Rec_T_RIASSICURAZIONI.importo_finanziato,
          Rec_T_RIASSICURAZIONI.id_progetto,
          Rec_T_RIASSICURAZIONI.importo_garanzia,
          Rec_T_RIASSICURAZIONI.importo_ammesso,
          Rec_T_RIASSICURAZIONI.percentuale_garanzia,
          Rec_T_RIASSICURAZIONI.percentuale_riassicurato,
          to_date(substr(Rec_T_RIASSICURAZIONI.erogazione_mutuo,1,10),'yyyy-mm-dd'),
          to_date(substr(Rec_T_RIASSICURAZIONI.delibera_mutuo,1,10),'yyyy-mm-dd'),
          to_date(substr(Rec_T_RIASSICURAZIONI.emissione_garanzia,1,10),'yyyy-mm-dd'),
          to_date(substr(Rec_T_RIASSICURAZIONI.scadenza_mutuo,1,10),'yyyy-mm-dd'),
          Rec_T_RIASSICURAZIONI.importo_escusso,
          to_date(substr(Rec_T_RIASSICURAZIONI.data_escussione,1,10),'yyyy-mm-dd'),
          to_date(substr(Rec_T_RIASSICURAZIONI.data_scarico,1,10),'yyyy-mm-dd'),
          to_date(substr(Rec_T_RIASSICURAZIONI.data_revoca,1,10),'yyyy-mm-dd'),
          null,
          vID_CARICAMENTO,
          null
       );
     
     END LOOP;

     Update pbandi_t_migrazione_finpis
     set    data_migrazione = sysdate
     where  id_caricamento = vId_Caricamento;
     
     vcount:=0;
     
     COMMIT;
     
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - mfinpis_t_riassicurazioni  '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

-------------------------------------
  DBMS_OUTPUT.PUT_LINE (nRet);
  COMMIT;
  RETURN nRet;

END FNC_CARICA_XML_MFINPIS;

-----------
-----------
FUNCTION FNC_INSERT_PBANDI RETURN NUMBER IS
 vdummy varchar2(17);
 nRet NUMBER:= 0;
 pIdUtenteIns NUMBER:= -14;
 vcount number;
 vcount_ndg number;
 -- chiavi pban sulle tabelle Mfinpis
 vID_LINEA_DI_INTERVENTO_PBAN     PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO%TYPE;
 vID_BANDO_PBAN                   PBANDI_T_BANDO.ID_BANDO%TYPE;
 vPROGR_BANDO_LINEA_INTERV_PBAN   PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE;
 vID_BANDO_MOD_AG_ESTR_BAN        pbandi_r_bando_mod_ag_estr_ban.Id_Bando_Mod_Ag_Estr_Ban%TYPE;
 vPROGR_BANDO_LIN_ENTE_C_PBAN     pbandi_r_bando_linea_ente_comp.Progr_Bando_Linea_Ente_Comp%TYPE;
 vID_INDIRIZZO_PBAN               pbandi_t_indirizzo.id_indirizzo%TYPE;
 vID_AGENZIA_PBAN                 PBANDI_T_AGENZIA.ID_AGENZIA%TYPE;
 vID_ESTREMI_BANCARI_PBAN         PBANDI_T_ESTREMI_BANCARI.ID_ESTREMI_BANCARI%TYPE;
 vID_VOCE_DI_SPESA_PBAN           PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA%TYPE;
 vID_RICHIESTA_PBAN               pbandi_t_richiesta.id_richiesta%type;
 vID_SOGGETTO_DURC_PBAN           pbandi_t_soggetto_durc.id_soggetto_durc%type;
 vID_SOGGETTO_ANTIMAFIA_PBAN      PBANDI_T_SOGGETTO_ANTIMAFIA.ID_SOGGETTO_ANTIMAFIA%TYPE;
 vID_REVOCA_PBAN                  PBANDI_T_REVOCA.ID_REVOCA%TYPE;
 vID_ESCUSSIONE_PBAN              PBANDI_T_ESCUSSIONE.ID_ESCUSSIONE%TYPE;
 vID_DELIBERA_PBAN                pbandi_t_delibera_progetti.ID_DELIBERA%TYPE;
 vID_RNA_PROGETTO_pban            pbandi_t_rna_progetto.id_rna_progetto%TYPE;
 vID_SOGGETTO_DSAN_pban           PBANDI_T_SOGGETTO_DSAN.ID_SOGGETTO_DSAN%type;
 vID_CONTRODEDUZ_PBAN             PBANDI_T_CONTRODEDUZ.ID_CONTRODEDUZ%TYPE;
 vID_RICHIESTA_INTEGRAZ_PBAN      PBANDI_T_RICHIESTA_INTEGRAZ.ID_RICHIESTA_INTEGRAZ%type;
 vID_RICHIESTA_PROROGA_PBAN       PBANDI_T_PROROGA.ID_RICHIESTA_PROROGA%TYPE;
 vID_GESTIONE_REVOCA_PBAN         PBANDI_T_GESTIONE_REVOCA.ID_GESTIONE_REVOCA%TYPE;
 vID_RNA_PROGETTO_man_pban        PBANDI_T_RNA_PROGETTO_MAN.ID_RNA_PROGETTO_MAN%type;
 vID_AMMORT_PROGETTI_PBAN         PBANDI_T_AMMORTAMENTO_PROGETTI.ID_AMMORT_PROGETTI%type;
 -- associazioni
 vID_LINEA_DI_INTERVENTO       PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO%TYPE;
 vID_BANDO                     PBANDI_T_BANDO.ID_BANDO%TYPE;
 vPROGR_BANDO_LINEA_INTERVENTO PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO%TYPE;
 vID_INDIRIZZO                 PBANDI_T_INDIRIZZO.ID_INDIRIZZO%TYPE;
 vID_AGENZIA                   PBANDI_T_AGENZIA.ID_AGENZIA%TYPE;
 vID_VOCE_DI_SPESA_PADRE       PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA_PADRE%TYPE;
 vID_VOCE_DI_SPESA             PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA%TYPE;
 vID_DOMANDA                   pbandi_t_domanda.id_domanda%type;
 vID_SOGGETTO                  pbandi_t_soggetto_durc.id_soggetto_durc%type;
 vID_PROGETTO                  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;
 vid_estremi_bancari           PBANDI_T_ESTREMI_BANCARI.ID_ESTREMI_BANCARI%type;
 vID_SOGG_PROG_STATO_CREDITO_FP pbandi_r_sogg_prog_sta_cred_fp.id_sogg_prog_stato_credito_fp%type;
 vID_AZIONE_RECUPERO_BANCA     pbandi_T_AZIONE_RECUP_BANCA.Id_Azione_Recupero_Banca%type;
 vID_CESSIONE_QUOTA_FP         pbandi_t_cessione_quota_fp.ID_CESSIONE_QUOTA_FP%type;
 vID_ESCUSS_CONFIDI            pbandi_T_ESCUSS_CONFIDI.Id_Escuss_Confidi%TYPE;
 vID_ISCRIZIONE_RUOLO          pbandi_t_iscrizione_ruolo.id_iscrizione_ruolo%type;
 vID_PASSAGGIO_PERDITA         pbandi_t_passaggio_perdita.id_passaggio_perdita%type;
 vID_PIANO_RIENTRO             pbandi_t_piano_rientro.id_piano_rientro%type;
 vID_REVOCA_BANCARIA           pbandi_t_revoca_bancaria.id_revoca_bancaria%type;
 vID_SALDO_STRALCIO            pbandi_t_saldo_stralcio.id_saldo_stralcio%type;
 vID_SOGGETTO_STATO_AZIENDA    pbandi_t_sogg_stato_azienda.id_soggetto_stato_azienda%type;
 vID_SOGGETTO_CLA_RISCHIO      PBANDI_t_soggetto_cla_RISCHIO.ID_SOGGETTO_CLA_RISCHIO%type;
 vID_SOGGETTO_RATING           pbandi_t_soggetto_rating.id_soggetto_rating%type;
 vid_ammort_progetti           pbandi_t_ammortamento_progetti.id_ammort_progetti%type;
 vID_DELIBERA                  pbandi_t_delibera_progetti.ID_DELIBERA%TYPE;
 vID_LIBERAZ_GARANTE           PBANDI_T_LIBERAZ_GARANTE.ID_LIBERAZ_GARANTE%type;
 vID_MESSA_IN_MORA             PBANDI_T_messa_in_mora.Id_Messa_In_Mora%type;
 vID_SEGNALAZIONE_CORTE_CONTI  PBANDI_t_segnalaz_corte_conti.Id_Segnalazione_Corte_Conti%type;
 vID_NOTA_PBAN                 PBANDI_T_NOTE.ID_NOTA%type;
 vID_RIASSICURAZIONE           pbandi_t_riassicurazioni.id_riassicurazione%type;
 --
 vId_Soggetto_NDG NUMBER(8);
 vPROGR_SOGGETTO_PROGETTO pbandi_r_soggetto_progetto.progr_soggetto_progetto%type;
 --
 vid_progetto_split       pbandi_t_progetto.id_progetto%type;
 --
 -- procedura Salvatore
  v_id_linea_intervento INTEGER;
--
  v_id_modalita_agevolazione  pbandi_d_modalita_agevolazione.id_modalita_agevolazione%type;
--

  vid_banca PBANDI_D_BANCA.ID_BANCA%TYPE;
  vid_Esito number;

   CURSOR c1 IS

    select c.progr_bando_linea_intervento
    from   PBANDI_R_BANDO_LINEA_INTERVENT  c    
    where  c.id_utente_ins = -14;
 --
    CURSOR c2 IS
    select progr_bando_linea_intervento
    from PBANDI_R_BANDO_LINEA_INTERVENT
    where id_utente_ins = -14;
 --

 -- Storicizzazione tabella PBANDI_R_SOGG_PROG_STA_CRED_FP

     cursor c3 is
      select distinct PROGR_SOGGETTO_PROGETTO, 
                      ID_MODALITA_AGEVOLAZIONE 
      from PBANDI_R_SOGG_PROG_STA_CRED_FP
      where id_utente_ins = -14;

      cursor c4 (c_progr_soggetto_progetto number, 
              c_id_modalita_agevolazione number) is
      select ID_SOGG_PROG_STATO_CREDITO_FP,
            DT_INIZIO_VALIDITA
       from PBANDI_R_SOGG_PROG_STA_CRED_FP
       where PROGR_SOGGETTO_PROGETTO = c_progr_soggetto_progetto
        and  id_modalita_agevolazione = c_id_modalita_agevolazione
       order by DT_INIZIO_VALIDITA, ID_SOGG_PROG_STATO_CREDITO_FP;




BEGIN
  
/*
 delete from pbandi_d_linea_di_intervento where trunc(dt_inizio_validita) = trunc(sysdate) 
 delete from pbandi_t_bando where id_utente_ins = -14;
 delete from pbandi_r_bando_causale_erogaz where id_utente_ins = -14;
 delete from pbandi_r_bando_tipo_trattament where id_utente_ins = -14;
 delete from pbandi_r_bando_linea_intervent where id_utente_ins = -14;
 delete from pbandi_r_eccez_ban_lin_doc_pag  where PROGR_BANDO_LINEA_INTERVENTO in (select PROGR_BANDO_LINEA_INTERVENTO from pbandi_r_bando_linea_intervent where id_utente_ins = -14)
 delete from pbandi_r_bando_lin_tipo_period  where PROGR_BANDO_LINEA_INTERVENTO in (select PROGR_BANDO_LINEA_INTERVENTO from pbandi_r_bando_linea_intervent where id_utente_ins = -14)
 delete from pbandi_r_regola_bando_linea where PROGR_BANDO_LINEA_INTERVENTO in (select PROGR_BANDO_LINEA_INTERVENTO from pbandi_r_bando_linea_intervent where id_utente_ins = -14)
 delete from pbandi_r_bando_mod_ag_estr_ban where id_utente_ins = -14; --
 delete from pbandi_r_bando_modalita_agevol where id_utente_ins = -14; --
 delete from pbandi_r_bando_linea_ente_comp where id_utente_ins = -14; --
 delete from pbandi_t_indirizzo where id_utente_ins = -14; --
 delete from pbandi_t_agenzia where id_utente_ins = -14; --;
 delete from pbandi_t_estremi_bancari where id_utente_ins = -14; --
 delete from pbandi_d_voce_di_spesa where trunc(dt_inizio_validita) = trunc(sysdate) --
 delete from pbandi_r_bando_voce_spesa where id_utente_ins = -14  --
 */



--------------------------------
--- CONFIGURAZIONE BANDO
--------------------------------

begin
 -- 1. D_LINEA_DI_INTERVENTO
 
 For Rec_d_linea_di_intervento IN
   (Select * from MFinpis_d_linea_di_intervento
    WHERE data_caricamento is null
    --AND ID_LINEA_DI_INTERVENTO >= 1294
    )
    LOOP

    Select Max(ID_LINEA_DI_INTERVENTO)+1
    Into   vID_LINEA_DI_INTERVENTO_PBAN
    From   PBANDI_D_LINEA_DI_INTERVENTO;


    INSERT INTO PBANDI_D_LINEA_DI_INTERVENTO
   (ID_LINEA_DI_INTERVENTO,
    ID_LINEA_DI_INTERVENTO_PADRE,
    DESC_BREVE_LINEA,
    DESC_LINEA,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_TIPO_LINEA_INTERVENTO,
    ID_TIPO_STRUMENTO_PROGR,
    COD_IGRUE_T13_T14,
    ID_STRUMENTO_ATTUATIVO,
    NUM_DELIBERA,
    ANNO_DELIBERA,
    ID_PROCESSO,
    COD_LIV_GERARCHICO,
    FLAG_CAMPION_RILEV,
    FLAG_TRASFERIMENTI
    )VALUES
   (vID_LINEA_DI_INTERVENTO_PBAN,
    Rec_d_linea_di_intervento.ID_LINEA_DI_INTERVENTO_PADRE,
    Rec_d_linea_di_intervento.DESC_BREVE_LINEA,
    Rec_d_linea_di_intervento.DESC_LINEA,
    --Rec_d_linea_di_intervento.DT_INIZIO_VALIDITA,
    SYSDATE,
    Rec_d_linea_di_intervento.DT_FINE_VALIDITA,
    Rec_d_linea_di_intervento.ID_TIPO_LINEA_INTERVENTO,
    6,--Rec_d_linea_di_intervento.ID_TIPO_STRUMENTO_PROGR,
    Rec_d_linea_di_intervento.COD_IGRUE_T13_T14,
    1,--Rec_d_linea_di_intervento.ID_STRUMENTO_ATTUATIVO,
    Rec_d_linea_di_intervento.NUM_DELIBERA,
    Rec_d_linea_di_intervento.ANNO_DELIBERA,
    Rec_d_linea_di_intervento.ID_PROCESSO,
    Rec_d_linea_di_intervento.COD_LIV_GERARCHICO,
    Rec_d_linea_di_intervento.FLAG_CAMPION_RILEV,
    Rec_d_linea_di_intervento.FLAG_TRASFERIMENTI
    );


    Update MFinpis_d_linea_di_intervento
    Set    data_caricamento = sysdate,
           ID_LINEA_DI_INTERVENTO_PBAN = vID_LINEA_DI_INTERVENTO_PBAN
    Where  ID_LINEA_DI_INTERVENTO = Rec_d_linea_di_intervento.Id_Linea_Di_Intervento;


    END LOOP;



  -- 2. T_BANDO 


    For Rec_T_BANDO IN
      (Select * from MFinpis_T_bando
       WHERE data_caricamento is null
       and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number(dg.cod_fondo_gefo)
        )
       )

    LOOP


   Begin
    Select ID_LINEA_DI_INTERVENTO_PBAN
     Into   vID_LINEA_DI_INTERVENTO
      From   MFINPIS_D_LINEA_DI_INTERVENTO
      Where  ID_LINEA_DI_INTERVENTO = Rec_T_BANDO.Id_Linea_Di_Intervento;
   Exception When Others then
           vID_LINEA_DI_INTERVENTO:= NULL;
   End;




    INSERT INTO PBANDI_T_BANDO
    (   ID_BANDO,
        TITOLO_BANDO,
        DT_PUBBLICAZIONE_BANDO,
        DT_INIZIO_PRESENTAZ_DOMANDE,
        DT_SCADENZA_BANDO,
        STATO_BANDO,
        FLAG_ALLEGATO,
        FLAG_GRADUATORIA,
        DOTAZIONE_FINANZIARIA,
        COSTO_TOTALE_MIN_AMMISSIBILE,
        FLAG_QUIETANZA,
        PERCENTUALE_PREMIALITA,
        IMPORTO_PREMIALITA,
        PUNTEGGIO_PREMIALITA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_MATERIA,
        ID_INTESA,
        ID_SOTTO_SETTORE_CIPE,
        ID_NATURA_CIPE,
        ID_TIPO_OPERAZIONE,
        ID_SETTORE_CPT,
        ID_TIPOLOGIA_ATTIVAZIONE,
        DETERMINA_APPROVAZIONE,
        DT_INSERIMENTO,
        DT_AGGIORNAMENTO,
        ID_LINEA_DI_INTERVENTO,
        DATA_DETERMINA_APPROVAZIONE,
        FLAG_MACRO_VOCE_SPESA
    )VALUES
    (seq_pbandi_t_bando.NEXTVAL,
        Rec_T_BANDO.TITOLO_BANDO,
        Rec_T_BANDO.DT_PUBBLICAZIONE_BANDO,
        Rec_T_BANDO.DT_INIZIO_PRESENTAZ_DOMANDE,
        Rec_T_BANDO.DT_SCADENZA_BANDO,
        Rec_T_BANDO.STATO_BANDO,
        Rec_T_BANDO.FLAG_ALLEGATO,
        Rec_T_BANDO.FLAG_GRADUATORIA,
        Rec_T_BANDO.DOTAZIONE_FINANZIARIA,
        Rec_T_BANDO.COSTO_TOTALE_MIN_AMMISSIBILE,
        Rec_T_BANDO.FLAG_QUIETANZA,
        Rec_T_BANDO.PERCENTUALE_PREMIALITA,
        Rec_T_BANDO.IMPORTO_PREMIALITA,
        Rec_T_BANDO.PUNTEGGIO_PREMIALITA,
        pIdUtenteIns,
        NULL,
        Rec_T_BANDO.ID_MATERIA,
        Rec_T_BANDO.ID_INTESA,
        50,--Rec_T_BANDO.ID_SOTTO_SETTORE_CIPE,
        4,--Rec_T_BANDO.ID_NATURA_CIPE,      -- mc 22082022
        3,--Rec_T_BANDO.ID_TIPO_OPERAZIONE,  -- mc 22082022
        Rec_T_BANDO.ID_SETTORE_CPT,
        Rec_T_BANDO.ID_TIPOLOGIA_ATTIVAZIONE,
        Rec_T_BANDO.DETERMINA_APPROVAZIONE,
        --Rec_T_BANDO.DT_INSERIMENTO,
        SYSDATE,
        Rec_T_BANDO.DT_AGGIORNAMENTO,
        --Rec_T_BANDO.ID_LINEA_DI_INTERVENTO,  --
        vID_LINEA_DI_INTERVENTO,
        Rec_T_BANDO.DATA_DETERMINA_APPROVAZIONE,
        Rec_T_BANDO.FLAG_MACRO_VOCE_SPESA
    )   RETURNING ID_BANDO INTO vID_BANDO_PBAN;

     -- mc 22082022
     INSERT INTO PBANDI_R_BANDO_TIPO_TRATTAMENT
     (ID_BANDO,
      ID_TIPO_TRATTAMENTO,
      FLAG_ASSOCIAZIONE_DEFAULT,
      ID_UTENTE_INS,
      ID_UTENTE_AGG
      )
     VALUES
     (vID_BANDO_PBAN,
      3,
     'S',
      -14,
      NULL);
    --

    -- mc 23082022
    for i in 1..4 loop
      insert into PBANDI_R_BANDO_CAUSALE_EROGAZ
      (ID_BANDO,
       ID_CAUSALE_EROGAZIONE,
       ID_UTENTE_INS,
       PROGR_BANDO_CAUSALE_EROGAZ
      )values
      (vID_BANDO_PBAN,
       i,
       -14,
       seq_pbandi_r_bando_causale_ero.nextval
      );
    end loop;
    --


    Update MFinpis_T_bando
    Set    data_caricamento = sysdate,
           ID_BANDO_PBAN = vID_BANDO_PBAN
    Where  ID_BANDO = Rec_t_bando.Id_bando;

    END LOOP;



--  3. R_BANDO_LINEA_INTERVENT
   
    For Rec_R_BANDO_LINEA_INTERVENT IN
      (Select * from MFinpis_R_BANDO_LINEA_INTER
       WHERE data_caricamento is null
       and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number( dg.cod_fondo_gefo)
        )
       )
      LOOP


    Select ID_LINEA_DI_INTERVENTO_PBAN
    Into   vID_LINEA_DI_INTERVENTO
    From   MFINPIS_D_LINEA_DI_INTERVENTO
    Where  ID_LINEA_DI_INTERVENTO = Rec_R_BANDO_LINEA_INTERVENT.Id_Linea_Di_Intervento;

    Select ID_BANDO_PBAN
    Into   vID_BANDO
    From   MFINPIS_T_BANDO
    Where  ID_BANDO = Rec_R_BANDO_LINEA_INTERVENT.ID_BANDO;

    -- mc 23082022
    -- associo la linea di intervento al bando

    update pbandi_t_bando
    set    id_linea_di_intervento = vID_LINEA_DI_INTERVENTO
    Where  ID_BANDO = vID_BANDO;
    --

    INSERT INTO PBANDI_R_BANDO_LINEA_INTERVENT
    (   ID_LINEA_DI_INTERVENTO,
        ID_BANDO,
        PROGR_BANDO_LINEA_INTERVENTO,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_AREA_SCIENTIFICA,
        NOME_BANDO_LINEA,
        ID_DEFINIZIONE_PROCESSO,
        ID_UNITA_ORGANIZZATIVA,
        MESI_DURATA_DA_DT_CONCESSIONE,
        ID_OBIETTIVO_SPECIF_QSN,
        ID_CATEGORIA_CIPE,
        ID_TIPOLOGIA_CIPE,
        FLAG_SCHEDIN,
        ID_PROCESSO,
        FLAG_SIF,
        PROGR_BANDO_LINEA_INTERV_SIF,
        ID_TIPO_LOCALIZZAZIONE,
        ID_LIVELLO_ISTITUZIONE,
        ID_PROGETTO_COMPLESSO,
        ID_CLASSIFICAZIONE_MET,
        FLAG_FONDO_DI_FONDI,
        ID_CLASSIFICAZIONE_RA,
        COD_AIUTO_RNA,
        ID_LINEA_AZIONE,
        FLAG_MONITORAGGIO_TEMPI
    )VALUES
    ( vID_LINEA_DI_INTERVENTO,  --
        vID_BANDO,   --
        seq_pbandi_r_bando_linea_inter.nextval,
        pIdUtenteIns,
        NULL,  --ID_UTENTE_AGG,
        Rec_R_BANDO_LINEA_INTERVENT.ID_AREA_SCIENTIFICA,
        Rec_R_BANDO_LINEA_INTERVENT.NOME_BANDO_LINEA,
        Rec_R_BANDO_LINEA_INTERVENT.ID_DEFINIZIONE_PROCESSO,
        Rec_R_BANDO_LINEA_INTERVENT.ID_UNITA_ORGANIZZATIVA,
        Rec_R_BANDO_LINEA_INTERVENT.MESI_DURATA_DA_DT_CONCESSIONE,
        Rec_R_BANDO_LINEA_INTERVENT.ID_OBIETTIVO_SPECIF_QSN,
        Rec_R_BANDO_LINEA_INTERVENT.ID_CATEGORIA_CIPE,
        Rec_R_BANDO_LINEA_INTERVENT.ID_TIPOLOGIA_CIPE,
        Rec_R_BANDO_LINEA_INTERVENT.FLAG_SCHEDIN,
        2, --Rec_R_BANDO_LINEA_INTERVENT.ID_PROCESSO,  -- mc 23082022
        Rec_R_BANDO_LINEA_INTERVENT.FLAG_SIF,
        Rec_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERV_SIF,
        Rec_R_BANDO_LINEA_INTERVENT.ID_TIPO_LOCALIZZAZIONE,
        Rec_R_BANDO_LINEA_INTERVENT.ID_LIVELLO_ISTITUZIONE,
        Rec_R_BANDO_LINEA_INTERVENT.ID_PROGETTO_COMPLESSO,
        Rec_R_BANDO_LINEA_INTERVENT.ID_CLASSIFICAZIONE_MET,
        Rec_R_BANDO_LINEA_INTERVENT.FLAG_FONDO_DI_FONDI,
        Rec_R_BANDO_LINEA_INTERVENT.ID_CLASSIFICAZIONE_RA,
        Rec_R_BANDO_LINEA_INTERVENT.COD_AIUTO_RNA,
        Rec_R_BANDO_LINEA_INTERVENT.ID_LINEA_AZIONE,
        Rec_R_BANDO_LINEA_INTERVENT.FLAG_MONITORAGGIO_TEMPI
    ) RETURNING PROGR_BANDO_LINEA_INTERVENTO INTO vPROGR_BANDO_LINEA_INTERV_PBAN;

    -- mc 23082022
    -- SET DI REGOLE DI BASE (4) PREVISTI PER I BANDI FINPIS
      insert into pbandi_r_regola_bando_linea (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
      values (3, vPROGR_BANDO_LINEA_INTERV_PBAN);
      insert into pbandi_r_regola_bando_linea (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
      values (5, vPROGR_BANDO_LINEA_INTERV_PBAN);
      insert into pbandi_r_regola_bando_linea (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
      values (36, vPROGR_BANDO_LINEA_INTERV_PBAN);
      insert into pbandi_r_regola_bando_linea (ID_REGOLA, PROGR_BANDO_LINEA_INTERVENTO)
      values (42, vPROGR_BANDO_LINEA_INTERV_PBAN);
    --

    -- mc  22082022
    -- PBANDI_R_BANDO_LIN_TIPO_PERIOD
    insert into PBANDI_R_BANDO_LIN_TIPO_PERIOD
    (PROGR_BANDO_LINEA_INTERVENTO,
      ID_TIPO_PERIODO,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_PERIODO
    )values
    ( vPROGR_BANDO_LINEA_INTERV_PBAN,
      2,
      -14,
      null,
      null
    );

    Update MFinpis_R_BANDO_LINEA_INTER
    Set    data_caricamento = sysdate,
           PROGR_BANDO_LINEA_INTERV_PBAN = vPROGR_BANDO_LINEA_INTERV_PBAN
    Where  PROGR_BANDO_LINEA_INTERVENTO = Rec_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO;

    END LOOP;
    --
    BEGIN

       FOR r1 IN c2 LOOP

        INSERT INTO PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG
        ( ID_MODALITA_PAGAMENTO,
      ID_TIPO_DOCUMENTO_SPESA,
      FLAG_AGGIUNTA,
          PROGR_BANDO_LINEA_INTERVENTO,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PROGR_ECCEZ_BAN_LIN_DOC_PAG,
      DT_INSERIMENTO
          )
          VALUES
          (
      NULL,
      86, -- migrazione Finpiemonte
      'S',
      r1.progr_bando_linea_intervento,
          -14,
      NULL,
      SEQ_PBANDI_R_ECC_BAN_LIN_DOC_P.nextval,
      sysdate
          );

        INSERT INTO PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG
        ( ID_MODALITA_PAGAMENTO,
      ID_TIPO_DOCUMENTO_SPESA,
      FLAG_AGGIUNTA,
          PROGR_BANDO_LINEA_INTERVENTO,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PROGR_ECCEZ_BAN_LIN_DOC_PAG,
      DT_INSERIMENTO
          )
          VALUES
          (
      5,  --Varie
      86, -- migrazione Finpiemonte
      'S',
      r1.progr_bando_linea_intervento,
          -14,
      NULL,
      SEQ_PBANDI_R_ECC_BAN_LIN_DOC_P.nextval,
      sysdate
          );

  END LOOP;
END;

-- MC 04072023

BEGIN

  FOR r1 IN c1 LOOP
        INSERT INTO PBANDI_R_REGOLA_BANDO_LINEA
        ( ID_REGOLA,
          PROGR_BANDO_LINEA_INTERVENTO
          )
          VALUES
          (
          47, -- Progetti extra POR
          r1.progr_bando_linea_intervento
          );

  END LOOP;  

END;

--  5. R_BANDO_MODALITA_AGEVOL


   For Rec_R_BANDO_MODALITA_AGEVOL IN
     (SELECT * FROM Mfinpis_r_bando_modalita_agev
      WHERE data_caricamento is null
      and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number( dg.cod_fondo_gefo)
        )
     )LOOP

    Select ID_BANDO_PBAN
    Into   vID_BANDO
    From   MFINPIS_T_BANDO
    Where  ID_BANDO = Rec_R_BANDO_MODALITA_AGEVOL.ID_BANDO;


   INSERT INTO  PBANDI_r_bando_modalita_agevol
     (ID_MODALITA_AGEVOLAZIONE,-- pk
      ID_BANDO,-- pk
      PERCENTUALE_IMPORTO_AGEVOLATO,
      MASSIMO_IMPORTO_AGEVOLATO,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      FLAG_LIQUIDAZIONE,
      MINIMO_IMPORTO_AGEVOLATO
      )
      VALUES
      (Rec_R_BANDO_MODALITA_AGEVOL.ID_MODALITA_AGEVOLAZIONE,-- pk
      vID_BANDO,-- pk
      Rec_R_BANDO_MODALITA_AGEVOL.PERCENTUALE_IMPORTO_AGEVOLATO,
      Rec_R_BANDO_MODALITA_AGEVOL.MASSIMO_IMPORTO_AGEVOLATO,
      pIdUtenteIns,
      Null,--ID_UTENTE_AGG,
      Rec_R_BANDO_MODALITA_AGEVOL.FLAG_LIQUIDAZIONE,
      Rec_R_BANDO_MODALITA_AGEVOL.MINIMO_IMPORTO_AGEVOLATO
      );

   Update MFinpis_r_bando_modalita_agev
    Set    data_caricamento = sysdate,
           ID_MODALITA_AGEVOLAZIONE_PBAN = Rec_R_BANDO_MODALITA_AGEVOL.ID_MODALITA_AGEVOLAZIONE,
           ID_BANDO_PBAN = vID_BANDO
    Where  ID_MODALITA_AGEVOLAZIONE = Rec_R_BANDO_MODALITA_AGEVOL.ID_MODALITA_AGEVOLAZIONE AND
           ID_BANDO = Rec_R_BANDO_MODALITA_AGEVOL.ID_BANDO ;

   END LOOP;


--  6. R_BANDO_LINEA_ENTE_COMP

       For Rec_R_BANDO_LINEA_ENTE_COMP IN
       (SELECT * FROM Mfinpis_r_bando_linea_ente_com
        WHERE data_caricamento is  null
        and PROGR_BANDO_LINEA_INTERVENTO IN
       (Select PROGR_BANDO_LINEA_INTERVENTO
        From   mfinpis_r_bando_linea_inter
        where  PROGR_BANDO_LINEA_INTERV_PBAN is not null -- record caricati
        )
       )LOOP

     Select PROGR_BANDO_LINEA_INTERV_PBAN
      Into   vPROGR_BANDO_LINEA_INTERVENTO
      From   MFINPIS_R_BANDO_LINEA_INTER
      Where  PROGR_BANDO_LINEA_INTERVENTO = Rec_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO;



    Insert Into pbandi_r_bando_linea_ente_comp
      (
      DT_FINE_VALIDITA,
      ID_RUOLO_ENTE_COMPETENZA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_ENTE_COMPETENZA,
      PROGR_BANDO_LINEA_INTERVENTO,
      PROGR_BANDO_LINEA_ENTE_COMP,
      PAROLA_CHIAVE,
      FEEDBACK_ACTA,
      INDIRIZZO_MAIL_PEC,
      CONSERVAZIONE_CORRENTE,
      CONSERVAZIONE_GENERALE
      )VALUES
      (Rec_R_BANDO_LINEA_ENTE_COMP.DT_FINE_VALIDITA,
      Rec_R_BANDO_LINEA_ENTE_COMP.ID_RUOLO_ENTE_COMPETENZA,
      pIdUtenteIns,
      NULL,--ID_UTENTE_AGG,
      Rec_R_BANDO_LINEA_ENTE_COMP.ID_ENTE_COMPETENZA,
      vPROGR_BANDO_LINEA_INTERVENTO,
      seq_pbandi_r_bando_lin_ente_co.nextval,
      Rec_R_BANDO_LINEA_ENTE_COMP.PAROLA_CHIAVE,
      Rec_R_BANDO_LINEA_ENTE_COMP.FEEDBACK_ACTA,
      Rec_R_BANDO_LINEA_ENTE_COMP.INDIRIZZO_MAIL_PEC,
      Rec_R_BANDO_LINEA_ENTE_COMP.CONSERVAZIONE_CORRENTE,
      Rec_R_BANDO_LINEA_ENTE_COMP.CONSERVAZIONE_GENERALE
      )RETURNING PROGR_BANDO_LINEA_ENTE_COMP INTO vPROGR_BANDO_LIN_ENTE_C_PBAN ;



     Update Mfinpis_r_bando_linea_ente_com
      Set    data_caricamento = sysdate,
             PROGR_BANDO_LINEA_ENTE_C_PBAN = vPROGR_BANDO_LIN_ENTE_C_PBAN
      Where  PROGR_BANDO_LINEA_ENTE_COMP = Rec_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_ENTE_COMP;

    END LOOP;

   
-- Sviluppo 03102022
   
  For Rec_Aggiorna in
    (select * from
     pbandi_r_bando_linea_ente_comp
     where id_ruolo_ente_competenza = 15
     and ID_UTENTE_INS = -14
     )LOOP

    Begin
     Insert Into pbandi_r_bando_linea_ente_comp
          (
          DT_FINE_VALIDITA,
          ID_RUOLO_ENTE_COMPETENZA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          ID_ENTE_COMPETENZA,
          PROGR_BANDO_LINEA_INTERVENTO,
          PROGR_BANDO_LINEA_ENTE_COMP,
          PAROLA_CHIAVE,
          FEEDBACK_ACTA,
          INDIRIZZO_MAIL_PEC,
          CONSERVAZIONE_CORRENTE,
          CONSERVAZIONE_GENERALE
          )VALUES
          (Rec_Aggiorna.DT_FINE_VALIDITA,
          2,
          pIdUtenteIns,
          NULL,--ID_UTENTE_AGG,
          Rec_Aggiorna.ID_ENTE_COMPETENZA,
          Rec_Aggiorna.Progr_Bando_Linea_Intervento,
          seq_pbandi_r_bando_lin_ente_co.nextval,
          Rec_Aggiorna.PAROLA_CHIAVE,
          Rec_Aggiorna.FEEDBACK_ACTA,
          Rec_Aggiorna.INDIRIZZO_MAIL_PEC,
          Rec_Aggiorna.CONSERVAZIONE_CORRENTE,
          Rec_Aggiorna.CONSERVAZIONE_GENERALE
          );

       end;

      Begin
       Insert Into pbandi_r_bando_linea_ente_comp
          (
          DT_FINE_VALIDITA,
          ID_RUOLO_ENTE_COMPETENZA,
          ID_UTENTE_INS,
          ID_UTENTE_AGG,
          ID_ENTE_COMPETENZA,
          PROGR_BANDO_LINEA_INTERVENTO,
          PROGR_BANDO_LINEA_ENTE_COMP,
          PAROLA_CHIAVE,
          FEEDBACK_ACTA,
          INDIRIZZO_MAIL_PEC,
          CONSERVAZIONE_CORRENTE,
          CONSERVAZIONE_GENERALE
          )VALUES
          (Rec_Aggiorna.DT_FINE_VALIDITA,
          3,
          pIdUtenteIns,
          NULL,--ID_UTENTE_AGG,
          Rec_Aggiorna.ID_ENTE_COMPETENZA,
          Rec_Aggiorna.Progr_Bando_Linea_Intervento,
          seq_pbandi_r_bando_lin_ente_co.nextval,
          Rec_Aggiorna.PAROLA_CHIAVE,
          Rec_Aggiorna.FEEDBACK_ACTA,
          Rec_Aggiorna.INDIRIZZO_MAIL_PEC,
          Rec_Aggiorna.CONSERVAZIONE_CORRENTE,
          Rec_Aggiorna.CONSERVAZIONE_GENERALE
          );

       end;

  END LOOP;


--  7. T_INDIRIZZI

     FOR REC_T_INDIRIZZO IN
       (Select * from MFinpis_t_indirizzo
        WHERE data_caricamento is null
       )LOOP

       Insert into pbandi_t_indirizzo
       (
        ID_INDIRIZZO,
        DESC_INDIRIZZO,
        CAP,
        ID_NAZIONE,
        ID_COMUNE,
        ID_PROVINCIA,
        ID_VIA_L2,
        CIVICO,
        BIS,
        DT_INIZIO_VALIDITA,
        DT_FINE_VALIDITA,
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        ID_REGIONE,
        ID_FONTE_INDIRIZZO,
        ID_GEO_RIFERIMENTO,
        ID_COMUNE_ESTERO
        )VALUES
        (seq_pbandi_t_indirizzo.nextval,
        Rec_T_INDIRIZZO.DESC_INDIRIZZO,
        Rec_T_INDIRIZZO.CAP,
        Rec_T_INDIRIZZO.ID_NAZIONE,
        Rec_T_INDIRIZZO.ID_COMUNE,
        Rec_T_INDIRIZZO.ID_PROVINCIA,
        Rec_T_INDIRIZZO.ID_VIA_L2,
        Rec_T_INDIRIZZO.CIVICO,
        Rec_T_INDIRIZZO.BIS,
        sysdate,--DT_INIZIO_VALIDITA,
        Rec_T_INDIRIZZO.DT_FINE_VALIDITA,
        pIdUtenteIns,
        null,--ID_UTENTE_AGG,
        Rec_T_INDIRIZZO.ID_REGIONE,
        Rec_T_INDIRIZZO.ID_FONTE_INDIRIZZO,
        Rec_T_INDIRIZZO.ID_GEO_RIFERIMENTO,
        Rec_T_INDIRIZZO.ID_COMUNE_ESTERO
        )RETURNING ID_INDIRIZZO INTO vID_INDIRIZZO_PBAN;


        Update MFinpis_t_indirizzo
        Set    data_caricamento = sysdate,
               ID_INDIRIZZO_PBAN = vID_INDIRIZZO_PBAN
        Where  ID_INDIRIZZO = Rec_T_INDIRIZZO.ID_INDIRIZZO;


        END LOOP;

--  8. T_AGENZIA

--DA FARE IN PROD ???
--select * from pbandi_d_banca where cod_banca  = 03069 --> 1542
--select * from pbandi_d_banca where cod_banca  = 05034 -->  5068

--insert into pbandi_d_banca values
--(seq_pbandi_d_banca.nextval,'05034','BANCO B.P.M. S.P.A.',sysdate,null,null);
--COMMIT;

--UPDATE MFinpis_t_agenzia SET ID_BANCA = 1542 WHERE ID_BANCA IN (90395,90396,90397,90398)
--UPDATE MFinpis_t_agenzia SET ID_BANCA = 5068 WHERE ID_BANCA IN (90394)


   FOR REC_T_AGENZIA IN
     (Select * from MFinpis_t_agenzia
      WHERE data_caricamento is null
     )LOOP

     Select ID_INDIRIZZO_PBAN
     INTO   vID_INDIRIZZO
     FROM   Mfinpis_t_indirizzo
     WHERE  ID_INDIRIZZO = REC_T_AGENZIA.Id_Indirizzo;

   
 --dbms_output.put_line(Rec_T_agenzia.ID_BANCA);

   Insert Into pbandi_t_agenzia
   (
    ID_AGENZIA,
    COD_AGENZIA,
    DESC_AGENZIA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    ID_BANCA,
    ID_INDIRIZZO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA
   ) VALUES
   (seq_pbandi_t_agenzia.nextval,
    Rec_T_agenzia.COD_AGENZIA,
    Rec_T_agenzia.DESC_AGENZIA,
    pIdUtenteIns,
    NULL, --ID_UTENTE_AGG,
    1, --Rec_T_agenzia.ID_BANCA,   TEMPORANEO DA TOGLIERE
    vID_INDIRIZZO,
    SYSDATE, --DT_INIZIO_VALIDITA,
    Rec_T_agenzia.DT_FINE_VALIDITA
   ) RETURNING ID_AGENZIA INTO vID_AGENZIA_PBAN;

    Update MFinpis_t_agenzia
      Set    data_caricamento = sysdate,
             ID_AGENZIA_PBAN = vID_AGENZIA_PBAN
      Where  ID_AGENZIA = Rec_T_AGENZIA.ID_AGENZIA;


   END LOOP;


 --  9. T_ESTREMI_BANCARI
  FOR REC_T_ESTREMI_BANCARI IN
     (Select * from MFinpis_T_ESTREMI_BANCARI
      WHERE data_caricamento is null
     )LOOP

       Select ID_AGENZIA_PBAN
       Into   vID_AGENZIA
       From   Mfinpis_t_Agenzia
       Where  ID_AGENZIA = REC_T_ESTREMI_BANCARI.ID_AGENZIA;

       begin
       select cod_banca
       Into   vId_Banca
       from   pbandi_d_banca
       where  cod_banca = REC_T_ESTREMI_BANCARI.cab;
       Exception when no_data_found then
         vId_Banca:= NULL;
       end;

     Insert Into pbandi_t_estremi_bancari
      (
      ID_ESTREMI_BANCARI,
      NUMERO_CONTO,
      CIN,
      ABI,
      CAB,
      IBAN,
      BIC,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      ID_AGENZIA,
      ID_BANCA,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA
      )VALUES
      (seq_pbandi_t_estremi_banca.Nextval,
      Rec_T_ESTREMI_BANCARI.NUMERO_CONTO,
      Rec_T_ESTREMI_BANCARI.CIN,
      Rec_T_ESTREMI_BANCARI.ABI,
      Rec_T_ESTREMI_BANCARI.CAB,
      Rec_T_ESTREMI_BANCARI.IBAN,
      Rec_T_ESTREMI_BANCARI.BIC,
      pIdUtenteIns,
      NULL,--ID_UTENTE_AGG,
      vID_AGENZIA,
      Rec_T_ESTREMI_BANCARI.ID_BANCA,
      SYSDATE, --DT_INIZIO_VALIDITA,
      Rec_T_ESTREMI_BANCARI.DT_FINE_VALIDITA
      ) RETURNING ID_ESTREMI_BANCARI INTO vID_ESTREMI_BANCARI_PBAN;


       Update Mfinpis_T_ESTREMI_BANCARI
       Set    data_caricamento = sysdate,
              ID_ESTREMI_BANCARI_PBAN = vID_ESTREMI_BANCARI_PBAN
       Where  ID_ESTREMI_BANCARI = REC_T_ESTREMI_BANCARI.ID_ESTREMI_BANCARI;


      END LOOP;

      Update pbandi_t_migrazione_finpis
      set    data_migrazione = sysdate
      where  upper(NOME_FILE) like upper('t_estremi%')
      and    data_migrazione = null;

 
--  4. R_BANDO_MOD_AG_ESTR_BAN
 

/*da eseguire prima di caricare -- da verificare

-- sui nuovi bandi da migrare
update MFinpis_r_bando_mod_ag_es_ban
set data_caricamento = to_date('01/01/1900','mm/dd/yyyy')
where (id_bando, id_modalita_agevolazione) in
(select id_bando, id_modalita_agevolazione
from MFinpis_r_bando_mod_ag_es_ban
where id_bando >= 1320 and (id_bando, id_modalita_agevolazione) not in
(Select id_bando, id_modalita_agevolazione from mfinpis_r_bando_modalita_agev)
)
-- sui bandi già presenti
update MFinpis_r_bando_mod_ag_es_ban
set data_caricamento = to_date('01/01/1900','mm/dd/yyyy')
where (id_bando, id_modalita_agevolazione) in
(select id_bando, id_modalita_agevolazione
from MFinpis_r_bando_mod_ag_es_ban
where id_bando < 1320 and  (id_bando, id_modalita_agevolazione) not in
(Select id_bando, id_modalita_agevolazione from pbandi_r_bando_modalita_agevol)
)

*/


   For Rec_R_BANDO_MOD_AG_ESTR_BAN IN
   (Select * from MFinpis_r_bando_mod_ag_es_ban
    WHERE data_caricamento is null
    and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number( dg.cod_fondo_gefo)
        )
     )
    LOOP


    begin
     Select ID_BANDO_PBAN
    Into   vID_BANDO
    From   MFINPIS_T_BANDO
    Where  ID_BANDO = Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_BANDO;
    exception when no_data_found then
           vID_BANDO:=Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_BANDO;
    end;


    Select id_estremi_bancari_pban
    Into   vid_estremi_bancari
    From   mfinpis_t_estremi_bancari
    Where  id_estremi_bancari = Rec_R_BANDO_MOD_AG_ESTR_BAN.Id_Estremi_Bancari;



      INSERT INTO pbandi_r_bando_mod_ag_estr_ban
      (ID_BANDO_MOD_AG_ESTR_BAN,
      ID_MODALITA_AGEVOLAZIONE,
      ID_BANDO,
      ID_ESTREMI_BANCARI,
      CODICE_FONDO_FINPIS,
      MOLTIPLICATORE,
      --TIPOLOGIA_CONTO,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG
      )VALUES
      (SEQ_PBANDI_R_BAN_MOD_AG_ES_BAN.NEXTVAL,
      Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_MODALITA_AGEVOLAZIONE,
      vID_BANDO,  --
      vid_estremi_bancari,
      Rec_R_BANDO_MOD_AG_ESTR_BAN.CODICE_FONDO_FINPIS,
      Rec_R_BANDO_MOD_AG_ESTR_BAN.MOLTIPLICATORE,
      -- Rec_R_BANDO_MOD_AG_ESTR_BAN.TIPOLOGIA_CONTO,
     -- Rec_R_BANDO_MOD_AG_ESTR_BAN.DT_INIZIO_VALIDITA,
      SYSDATE,
      Rec_R_BANDO_MOD_AG_ESTR_BAN.DT_FINE_VALIDITA,
      pIdUtenteIns,
      NULL --ID_UTENTE_AGG
      )RETURNING ID_BANDO_MOD_AG_ESTR_BAN INTO vID_BANDO_MOD_AG_ESTR_BAN;


    Update MFinpis_r_bando_mod_ag_es_ban
    Set    data_caricamento = sysdate,
           ID_BANDO_MOD_AG_ESTR_BAN_PBAN = vID_BANDO_MOD_AG_ESTR_BAN
    Where  ID_BANDO_MOD_AG_ESTR_BAN = Rec_R_BANDO_MOD_AG_ESTR_BAN.ID_BANDO_MOD_AG_ESTR_BAN;


    END LOOP;


/*

--  NON ESEGUIRE
--  12. R_BANDO_SOGG_FINANZIAT

      FOR REC_R_BANDO_SOGG_FINANZIAT IN
     (Select * from MFinpis_R_BANDO_SOGG_FINANZIAT
      WHERE data_caricamento is null
      and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number( dg.cod_fondo_gefo)
        )
     )LOOP

     Select ID_BANDO_PBAN
     Into   vID_BANDO
     From   MFINPIS_T_BANDO
     Where  ID_BANDO = REC_R_BANDO_SOGG_FINANZIAT.ID_BANDO;

     Insert Into pbandi_R_BANDO_SOGG_FINANZIAT
     (ID_BANDO,
      ID_SOGGETTO_FINANZIATORE,
      PERCENTUALE_QUOTA_SOGG_FINANZ,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PERC_QUOTA_CONTRIBUTO_PUB)
      Values
      (vID_BANDO,
       REC_R_BANDO_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE,
       REC_R_BANDO_SOGG_FINANZIAT.PERCENTUALE_QUOTA_SOGG_FINANZ,
       pIdUtenteIns,
       NULL, --ID_UTENTE_AGG,
       REC_R_BANDO_SOGG_FINANZIAT.PERC_QUOTA_CONTRIBUTO_PUB
      );

      Update Mfinpis_R_BANDO_SOGG_FINANZIAT
       Set    data_caricamento = sysdate,
              ID_BANDO_PBAN = vID_BANDO,
              ID_SOGGETTO_FINANZIATORE_PBAN = REC_R_BANDO_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE
       Where  ID_BANDO = REC_R_BANDO_SOGG_FINANZIAT.ID_BANDO and
              ID_SOGGETTO_FINANZIATORE = REC_R_BANDO_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE;

    END LOOP;
*/

-- 25012023
-- Procedura di Salvatore Gabriele
BEGIN

  FOR r1 IN c1 LOOP
        INSERT INTO PBANDI_R_REGOLA_BANDO_LINEA
        ( ID_REGOLA,
          PROGR_BANDO_LINEA_INTERVENTO
          )
          VALUES
          (
          47, -- Progetti extra POR
          r1.progr_bando_linea_intervento
          );

  END LOOP;
END;

--

-- 13. D_VOCE_SPESA

    For Rec_d_voce_di_spesa IN
    (Select * from MFinpis_d_voce_di_spesa
      WHERE data_caricamento is null
      and flag_cancellato = 'N' --
     )LOOP

      INSERT INTO PBANDI_D_VOCE_DI_SPESA
      (ID_VOCE_DI_SPESA,
        ID_VOCE_DI_SPESA_PADRE,
        DESC_VOCE_DI_SPESA,
        DT_INIZIO_VALIDITA,
        DT_FINE_VALIDITA,
        COD_TIPO_VOCE_DI_SPESA,
        ID_VOCE_DI_SPESA_MONIT,
        ID_TIPOLOGIA_VOCE_DI_SPESA,
        FLAG_EDIT
      ) VALUES
      ( seq_pbandi_d_voce_di_spesa.nextval,
        null,--Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA_PADRE,
        Rec_d_voce_di_spesa.DESC_VOCE_DI_SPESA,
        sysdate, --DT_INIZIO_VALIDITA,
        Rec_d_voce_di_spesa.DT_FINE_VALIDITA,
        Rec_d_voce_di_spesa.COD_TIPO_VOCE_DI_SPESA,
        Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA_MONIT,
        Rec_d_voce_di_spesa.ID_TIPOLOGIA_VOCE_DI_SPESA,
        Rec_d_voce_di_spesa.FLAG_EDIT
      )RETURNING ID_VOCE_DI_SPESA INTO vID_VOCE_DI_SPESA_PBAN;


       Update Mfinpis_D_VOCE_DI_SPESA
       Set    data_caricamento = sysdate,
              ID_VOCE_DI_SPESA_PBAN = vID_VOCE_DI_SPESA_PBAN
       Where  ID_VOCE_DI_SPESA = Rec_d_voce_di_spesa.ID_VOCE_DI_SPESA;



      END LOOP;

      

      -- Aggiorna ID_VOCE_DI_SPESA_PADRE
      
      For Rec_Aggiorna IN
        (Select * from mfinpis_D_VOCE_DI_SPESA
         where Id_Voce_Di_Spesa_padre_NEW is not null
         and flag_cancellato = 'N' )
      Loop


        select id_voce_di_spesa_pban
         into   vID_VOCE_DI_SPESA
        from   mfinpis_D_VOCE_DI_SPESA
        where  Id_Voce_di_spesa_NEW= Rec_Aggiorna.Id_Voce_Di_Spesa_NEW
        AND    flag_cancellato = 'N';

       --DBMS_OUTPUT.PUT_LINE(Rec_Aggiorna.Id_Voce_Di_Spesa_padre_NEW);
        select id_voce_di_spesa_pban
          into   vID_VOCE_DI_SPESA_padre
        from   mfinpis_D_VOCE_DI_SPESA
        where  Id_Voce_di_spesa_NEW = Rec_Aggiorna.Id_Voce_Di_Spesa_padre_NEW
        AND    flag_cancellato = 'N';

        UPDATE PBANDI_D_VOCE_DI_SPESA
        SET    ID_VOCE_DI_SPESA_PADRE = vID_VOCE_DI_SPESA_padre
        WHERE  id_voce_di_spesa = vID_VOCE_DI_SPESA;


      end loop;
      --

-- 14. R_BANDO_VOCE_SPESA
    FOR REC_R_BANDO_VOCE_SPESA IN
     (Select * from MFinpis_R_BANDO_VOCE_SPESA
      WHERE data_caricamento is null
      and id_bando NOT IN
       (Select distinct bf.id_bando
        From   bandi_fondi bf,
               tmp_num_domanda_gefo dg
        where  bf.codice_fondo_finpis = to_number( dg.cod_fondo_gefo)
        )
     )LOOP


    Select ID_BANDO_PBAN
    Into   vID_BANDO_PBAN
    From   MFINPIS_T_BANDO
    Where  ID_BANDO = REC_R_BANDO_VOCE_SPESA.ID_BANDO;

     Select ID_VOCE_DI_SPESA_PBAN
    Into   vID_VOCE_DI_SPESA_PBAN
    From   MFINPIS_D_VOCE_DI_SPESA
    Where  ID_VOCE_DI_SPESA = REC_R_BANDO_VOCE_SPESA.ID_VOCE_DI_SPESA_new -- mc modificato per bonifica
    and    flag_cancellato = 'N'; --
    
  --dbms_output.put_line(REC_R_BANDO_VOCE_SPESA.ID_VOCE_DI_SPESA_new);

    begin
    Insert into pbandi_R_BANDO_VOCE_SPESA
    (ID_BANDO,
      ID_VOCE_DI_SPESA,
      PERCENTUALE_IMP_AMMISSIBILE,
      MASSIMO_IMPORTO_AMMISSIBILE,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      PROGR_ORDINAMENTO
    )VALUES
    (vID_BANDO_PBAN,
      vID_VOCE_DI_SPESA_PBAN,
      REC_R_BANDO_VOCE_SPESA.PERCENTUALE_IMP_AMMISSIBILE,
      REC_R_BANDO_VOCE_SPESA.MASSIMO_IMPORTO_AMMISSIBILE,
      pIdUtenteIns,
      NULL,--ID_UTENTE_AGG,
      REC_R_BANDO_VOCE_SPESA.PROGR_ORDINAMENTO
    );

    exception when others then
      NULL;
     --dbms_output.put_line('bando '||vID_BANDO_PBAN);
     --dbms_output.put_line('voce spesa '||vID_VOCE_DI_SPESA_PBAN);
    end;

    Update MFINPIS_R_BANDO_VOCE_SPESA
       Set    data_caricamento = sysdate,
              ID_BANDO_PBAN = vID_BANDO_PBAN,
              ID_VOCE_DI_SPESA_PBAN = vID_VOCE_DI_SPESA_PBAN
       Where  ID_BANDO = REC_R_BANDO_VOCE_SPESA.ID_BANDO and
              ID_VOCE_DI_SPESA = REC_R_BANDO_VOCE_SPESA.ID_VOCE_DI_SPESA;

    END LOOP;
    
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - Configurazione Bando PBAN  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

------------------------------------------
----- FINE CONFIGURAZIONE BANDO      -----
------------------------------------------


------------------------------------------
----- DATI AGGIUNTIVI                -----
------------------------------------------


begin
 -- 33. T_SOGGETTO_DURC
 
 For Rec_T_Soggetto_Durc IN
    (Select * from mfinpis_t_soggetto_durc
      WHERE data_caricamento is null
    )
   LOOP
   -- CALCOLO NDG
   --

    -- conteggio inserito per evitare di trascinare ndg precedente in caso di valore null
    select count(*)
          into vcount_ndg
          from   pbandi_t_soggetto
          where  NDG = Rec_T_SOGGETTO_DURC.ID_SOGGETTO;


    if vcount_ndg = 0 then vId_Soggetto_NDG:= null; end if;
    --

    vcount := 0;
    For rec in
         (select id_soggetto
          from   pbandi_t_soggetto
          where  NDG = Rec_T_SOGGETTO_DURC.ID_SOGGETTO
          order by id_tipo_soggetto desc
          )
          LOOP
            if vcount = 0 then
              vId_Soggetto_NDG:=rec.id_soggetto;
              vcount := 1;
          end if;
          END LOOP;
    --
    --


   INSERT INTO pbandi_t_soggetto_durc
    (ID_SOGGETTO_DURC,
      ID_SOGGETTO,
      ID_TIPO_ESITO_DURC,
      DT_EMISSIONE_DURC,
      DT_SCADENZA,
      NUM_PROTOCOLLO_INPS,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA
     )VALUES
     (seq_pbandi_t_soggetto_durc.nextval,
      vId_Soggetto_NDG,
      Rec_T_SOGGETTO_DURC.ID_TIPO_ESITO_DURC,
      Rec_T_SOGGETTO_DURC.DT_EMISSIONE_DURC,
      Rec_T_SOGGETTO_DURC.DT_SCADENZA,
      Rec_T_SOGGETTO_DURC.NUM_PROTOCOLLO_INPS,
      -14,--ID_UTENTE_INS,
      null,--Rec_T_SOGGETTO_DURC.ID_UTENTE_AGG,
      sysdate, --Rec_T_SOGGETTO_DURC.DT_INIZIO_VALIDITA,
      null--Rec_T_SOGGETTO_DURC.DT_FINE_VALIDITA
     )returning ID_SOGGETTO_DURC into vID_SOGGETTO_DURC_PBAN;

    Update MFINPIS_t_soggetto_durc
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_DURC_PBAN =  vID_SOGGETTO_DURC_PBAN,
              ID_SOGGETTO_PBAN = vID_SOGGETTO
       Where  ID_SOGGETTO_DURC = Rec_T_SOGGETTO_DURC.ID_SOGGETTO_DURC;



   END LOOP;
   
   vcount := 0;
   
   COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_soggetto_durc  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 32. 34.  T_RICHIESTA (DURC E ANTIMAFIA)
  For Rec_T_Richiesta IN
    (Select * from MFINPIS_T_RICHIESTA
      WHERE data_caricamento is null
    )
   LOOP

   For Rec_DomandeSPLIT IN
     (Select id_domanda
      from pbandi_t_progetto
      Where codice_visualizzato like Rec_t_richiesta.Id_Domanda||'%'
      )
   Loop


   Insert into pbandi_t_richiesta
   (ID_RICHIESTA,
    ID_DOMANDA,
    ID_TIPO_RICHIESTA,
    ID_STATO_RICHIESTA,
    ID_UTENTE_RICHIEDENTE,
    DT_INVIO_RICHIESTA,
    DT_CHIUSURA_RICHIESTA,
    FLAG_URGENZA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA
    )VALUES
    (seq_pbandi_t_richiesta.nextval,
    Rec_DomandeSPLIT.id_domanda,
    Rec_T_RICHIESTA.ID_TIPO_RICHIESTA,
    Rec_T_RICHIESTA.ID_STATO_RICHIESTA,
    Rec_T_RICHIESTA.ID_UTENTE_RICHIEDENTE,
    Rec_T_RICHIESTA.DT_INVIO_RICHIESTA,
    Rec_T_RICHIESTA.DT_CHIUSURA_RICHIESTA,
    Rec_T_RICHIESTA.FLAG_URGENZA,
    pIdUtenteIns,
    null, --Rec_T_RICHIESTA_DURC.ID_UTENTE_AGG,
    sysdate,--DT_INIZIO_VALIDITA,
    NULL --DT_FINE_VALIDITA,
    )RETURNING ID_RICHIESTA INTO vID_RICHIESTA_PBAN;

   Update MFINPIS_T_RICHIESTA
       Set    data_caricamento = sysdate,
              ID_RICHIESTA_PBAN = vID_RICHIESTA_PBAN,
              ID_DOMANDA_PBAN = Rec_DomandeSPLIT.id_domanda
       Where  ID_RICHIESTA = REC_T_RICHIESTA.ID_RICHIESTA;

   END LOOP;

END LOOP;

COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_richiesta  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



begin
  
-- 35. MFINPIS_T_SOGGETTO_ANTIMAFIA

For Rec_T_SOGGETTO_ANTIMAFIA IN
    (Select * from MFINPIS_T_SOGGETTO_ANTIMAFIA
      WHERE data_caricamento is null
    )
   LOOP

       For Rec_DomandeSPLIT in
         (Select id_domanda
         from pbandi_t_progetto
         where codice_visualizzato LIKE Rec_T_SOGGETTO_ANTIMAFIA.id_domanda||'%'
         )Loop

           Insert into PBANDI_T_SOGGETTO_ANTIMAFIA
           (ID_SOGGETTO_ANTIMAFIA,
            ID_DOMANDA,
            ID_TIPO_ESITO_ANTIMAFIA,
            DT_RICEZIONE_BDNA,
            NUMER_PROTOCOLLO_RICEVUTA,
            DT_EMISSIONE,
           -- ESITO_LIBERATORIA_ANTIMAFIA, da verificare
            DT_SCADENZA_ANTIMAFIA,
            NUM_PROTOCOLLO_PREFETTURA,
            ID_UTENTE_INS,
            ID_UTENTE_AGG,
            DT_INIZIO_VALIDITA,
            DT_FINE_VALIDITA
           )VALUES
           (SEQ_PBANDI_T_SOGG_ANTIMAFIA.NEXTVAL,
            Rec_DomandeSPLIT.ID_DOMANDA,
            Rec_T_SOGGETTO_ANTIMAFIA.ID_TIPO_ESITO_ANTIMAFIA,
            Rec_T_SOGGETTO_ANTIMAFIA.DT_RICEZIONE_BDNA,
            Rec_T_SOGGETTO_ANTIMAFIA.NUMER_PROTOCOLLO_RICEVUTA,
            Rec_T_SOGGETTO_ANTIMAFIA.DT_EMISSIONE,
            --Rec_T_SOGGETTO_ANTIMAFIA.ESITO_LIBERATORIA_ANTIMAFIA,
            Rec_T_SOGGETTO_ANTIMAFIA.DT_SCADENZA_ANTIMAFIA,
            Rec_T_SOGGETTO_ANTIMAFIA.NUM_PROTOCOLLO_PREFETTURA,
            pIdUtenteIns,
            NULL,    --ID_UTENTE_AGG,
            SYSDATE, --DT_INIZIO_VALIDITA,
            NULL    --DT_FINE_VALIDITA
           )returning ID_SOGGETTO_ANTIMAFIA into vID_SOGGETTO_ANTIMAFIA_PBAN;


   Update MFINPIS_T_SOGGETTO_ANTIMAFIA
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_ANTIMAFIA_PBAN = vID_SOGGETTO_ANTIMAFIA_PBAN,
              ID_DOMANDA_PBAN = Rec_DomandeSPLIT.ID_DOMANDA
       Where  ID_SOGGETTO_ANTIMAFIA = Rec_T_SOGGETTO_ANTIMAFIA.ID_SOGGETTO_ANTIMAFIA;

   END LOOP;
 End loop;
 
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - PBANDI_T_SOGGETTO_ANTIMAFIA  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



begin
-- 37. MFINPIS_T_ESCUSSIONE

For Rec_T_ESCUSSIONE IN
    (Select * from MFINPIS_T_ESCUSSIONE
      WHERE data_caricamento is null
    )
   LOOP

   
   Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_T_ESCUSSIONE.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --

         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_T_ESCUSSIONE.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
                when too_many_rows then -- gestione anomalia, mail di s.gabriele del 020823
        vcount := 0;
        dbms_output.put_line(Rec_T_ESCUSSIONE.Id_Progetto);
      end;
   End;
   ----
If vcount <> 0 then

  Insert into pbandi_t_escussione
   (ID_ESCUSSIONE,
    ID_PROGETTO,
    ID_TIPO_ESCUSSIONE,
    ID_STATO_ESCUSSIONE,
    DT_RIC_RICH_ESCUSSIONE,
    DT_NOTIFICA,
    IMP_RICHIESTO,
    IMP_APPROVATO,
    CAUSALE_BONIFICO,
    IBAN_BONIFICO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO
   )values
   (SEQ_PBANDI_T_ESCUSSIONE.NEXTVAL,
    vid_progetto_split,
    rec_t_escussione.ID_TIPO_ESCUSSIONE,
    rec_t_escussione.ID_STATO_ESCUSSIONE,
    rec_t_escussione.DT_RIC_RICH_ESCUSSIONE,
    rec_t_escussione.DT_NOTIFICA,
    rec_t_escussione.IMP_RICHIESTO,
    rec_t_escussione.IMP_APPROVATO,
    rec_t_escussione.CAUSALE_BONIFICO,
    rec_t_escussione.IBAN_BONIFICO,
    rec_t_escussione.NOTE,
    nvl(rec_t_escussione.data_stato_escussione,rec_t_escussione.DT_INIZIO_VALIDITA), --new
    --rec_t_escussione.DT_INIZIO_VALIDITA,
    rec_t_escussione.DT_FINE_VALIDITA,
    rec_t_escussione.ID_UTENTE_INS,
    rec_t_escussione.ID_UTENTE_AGG,
    rec_t_escussione.DT_INSERIMENTO,
    rec_t_escussione.DT_AGGIORNAMENTO
   )returning ID_ESCUSSIONE into vID_ESCUSSIONE_PBAN;



    Update MFINPIS_T_ESCUSSIONE
       Set    data_caricamento = sysdate,
              ID_ESCUSSIONE_PBAN = vID_ESCUSSIONE_PBAN,
              ID_PROGETTO_PBAN = vid_progetto_split
       Where  ID_ESCUSSIONE = Rec_T_ESCUSSIONE.ID_ESCUSSIONE;


End if;
 END LOOP;

vcount:= 0;

COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_escussione  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


-------------------------------------------------
----------------- AREA CREDITO ------------------
-------------------------------------------------


begin
-- 50. R_SOGG_PROG_STA_CRE_FP

For Rec_R_SOGG_PROG_STA_CRE_FP IN
    (Select * from MFINPIS_R_SOGG_PROG_STA_CRE_FP
      WHERE data_caricamento is null
    )
   LOOP



   Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_R_SOGG_PROG_STA_CRE_FP.Progr_Soggetto_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --

         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_R_SOGG_PROG_STA_CRE_FP.Progr_Soggetto_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
          vcount := 0;
                when too_many_rows then
          begin
                 Select id_progetto
                 Into   vid_progetto_split
                 From pbandi_t_progetto
                 Where codice_visualizzato like substr(Rec_R_SOGG_PROG_STA_CRE_FP.Progr_Soggetto_Progetto,1,11)||'%';
                 vcount := 1;
                  Exception when no_data_found then
                   vcount := 0;
          end;

      end;
   End;
   ----
   ----
   
     Select PROGR_SOGGETTO_PROGETTO
     Into   vPROGR_SOGGETTO_PROGETTO
     From   pbandi_r_soggetto_progetto
     Where  id_tipo_anagrafica = 1
     And    id_tipo_beneficiario <> 4
     And    id_progetto = vid_progetto_split;
     
    /*
   exception when others then
     if vid_progetto_split IN
       (278891,246309,280691,242084,242396,263089,244362,261839,352656,355753,365454,365855,
        350771,353926,380911,288331,372820,352848,396420) THEN
     Select PROGR_SOGGETTO_PROGETTO
     Into   vPROGR_SOGGETTO_PROGETTO
     From   pbandi_r_soggetto_progetto
     wHERE  id_progetto = vid_progetto_split;
     end if;
   end;
   */

If vcount <> 0 then

  Insert into pbandi_r_sogg_prog_sta_cred_fp
   (ID_SOGG_PROG_STATO_CREDITO_FP,
    PROGR_SOGGETTO_PROGETTO,
    ID_STATO_CREDITO_FP,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_MODALITA_AGEVOLAZIONE
   )values
   (SEQ_PBANDI_R_SOG_PROG_STA_CRED.NEXTVAL,
    vPROGR_SOGGETTO_PROGETTO, -- Rec_R_SOGG_PROG_STA_CRE_FP.PROGR_SOGGETTO_PROGETTO,
    Rec_R_SOGG_PROG_STA_CRE_FP.ID_STATO_CREDITO_FP,
    -14,--ID_UTENTE_INS,
    NULL,--ID_UTENTE_AGG,
    Rec_R_SOGG_PROG_STA_CRE_FP.DT_INSERIMENTO,
    Rec_R_SOGG_PROG_STA_CRE_FP.DT_AGGIORNAMENTO,
    Rec_R_SOGG_PROG_STA_CRE_FP.DT_INIZIO_VALIDITA,
    Rec_R_SOGG_PROG_STA_CRE_FP.DT_FINE_VALIDITA,
    Rec_R_SOGG_PROG_STA_CRE_FP.Id_Modalita_Agevolazione
   )returning ID_SOGG_PROG_STATO_CREDITO_FP into vID_SOGG_PROG_STATO_CREDITO_FP;

    Update MFINPIS_R_SOGG_PROG_STA_CRE_FP
       Set    data_caricamento = sysdate,
              ID_SOGG_PROG_STATO_CRE_FP_PBAN = vID_SOGG_PROG_STATO_CREDITO_FP
       Where  ID_SOGG_PROG_STATO_CREDITO_FP = Rec_R_SOGG_PROG_STA_CRE_FP.ID_SOGG_PROG_STATO_CREDITO_FP;


End if;
 END LOOP;

-------------------------------------------------------------
-- Storicizzazione tabella PBANDI_R_SOGG_PROG_STA_CRED_FP
-------------------------------------------------------------


  BEGIN
   FOR r1 in c3 LOOP
      FOR r2 in c4 (r1.PROGR_SOGGETTO_PROGETTO, r1.ID_MODALITA_AGEVOLAZIONE) LOOP
         -- Si mette la data di fine validità sul record cronologicamente precedente
         UPDATE PBANDI_R_SOGG_PROG_STA_CRED_FP
            SET DT_FINE_VALIDITA =  r2.DT_INIZIO_VALIDITA
          WHERE PROGR_SOGGETTO_PROGETTO = r1.PROGR_SOGGETTO_PROGETTO
            AND id_modalita_agevolazione = r1.id_modaLita_agevolazione
            AND DT_FINE_VALIDITA IS NULL
            AND DT_INIZIO_VALIDITA <= r2.DT_INIZIO_VALIDITA
            AND ID_SOGG_PROG_STATO_CREDITO_FP != r2.ID_SOGG_PROG_STATO_CREDITO_FP;


         -- Così si garantisce che l'ultimo record è valido
         UPDATE PBANDI_R_SOGG_PROG_STA_CRED_FP
            SET DT_FINE_VALIDITA =  ''
          WHERE PROGR_SOGGETTO_PROGETTO = r1.PROGR_SOGGETTO_PROGETTO
            AND ID_SOGG_PROG_STATO_CREDITO_FP = r2.ID_SOGG_PROG_STATO_CREDITO_FP
            AND id_modalita_agevolazione = r1.id_modaLita_agevolazione;
      END LOOP;
   END LOOP;
END;
------------------------------------

vcount:= 0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_r_sogg_prog_sta_cred_fp  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
 -- 51. T_AZIONE_RECUP_BANCA

For Rec_T_AZIONE_RECUP_BANCA IN
    (Select * from MFINPIS_T_AZIONE_RECUP_BANCA
      WHERE data_caricamento is null
    )
   LOOP

  Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_T_AZIONE_RECUP_BANCA.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_T_AZIONE_RECUP_BANCA.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

  Insert into pbandi_T_AZIONE_RECUP_BANCA
   (ID_AZIONE_RECUPERO_BANCA,
    ID_PROGETTO,
    ID_ATTIVITA_AZIONE,
    DT_AZIONE,
    NUM_PROTOCOLLO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_MODALITA_AGEVOLAZIONE
   )values
   (SEQ_PBANDI_T_AZION_RECUP_BANCA.NEXTVAL,
    vid_progetto_split,
    Rec_T_AZIONE_RECUP_BANCA.ID_ATTIVITA_AZIONE,
    Rec_T_AZIONE_RECUP_BANCA.DT_AZIONE,
    Rec_T_AZIONE_RECUP_BANCA.NUM_PROTOCOLLO,
    Rec_T_AZIONE_RECUP_BANCA.NOTE,
    Rec_T_AZIONE_RECUP_BANCA.DT_INIZIO_VALIDITA,
    Rec_T_AZIONE_RECUP_BANCA.DT_FINE_VALIDITA,
    -14, --ID_UTENTE_INS,
    NULL,--ID_UTENTE_AGG,
    Rec_T_AZIONE_RECUP_BANCA.DT_INSERIMENTO,
    Rec_T_AZIONE_RECUP_BANCA.DT_AGGIORNAMENTO,
    Rec_T_AZIONE_RECUP_BANCA.ID_MODALITA_AGEVOLAZIONE
   )returning ID_AZIONE_RECUPERO_BANCA into vID_AZIONE_RECUPERO_BANCA;


    Update MFINPIS_T_AZIONE_RECUP_BANCA
       Set    data_caricamento = sysdate,
              ID_AZIONE_RECUPERO_BANCA_pban = vID_AZIONE_RECUPERO_BANCA
       Where  ID_AZIONE_RECUPERO_BANCA =Rec_T_AZIONE_RECUP_BANCA.Id_Azione_Recupero_Banca;
end if;
   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_T_AZIONE_RECUP_BANCA  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin

-- 52. T_CESSIONE_QUOTA_FP
For rec_T_CESSIONE_QUOTA_FP IN
    (Select * from MFINPIS_T_CESSIONE_QUOTA_FP
      WHERE data_caricamento is null
    )
   LOOP

   ----
   Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_T_CESSIONE_QUOTA_FP.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_T_CESSIONE_QUOTA_FP.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then
      Insert into pbandi_T_CESSIONE_QUOTA_FP
         (ID_CESSIONE_QUOTA_FP,
          ID_PROGETTO  ,
          IMP_CESSIONE_CAPITALE  ,
          IMP_CESSIONE_ONERI  ,
          IMP_CESSIONE_INTERESSI_MORA  ,
          IMP_CESSIONE_COMPLESSIVA  ,
          DT_CESSIONE  ,
          IMP_CORRISP_CESSIONE  ,
          DENOM_CESSIONARIO  ,
          ID_STATO_CESSIONE  ,
          NOTE  ,
          DT_INIZIO_VALIDITA  ,
          DT_FINE_VALIDITA  ,
          ID_UTENTE_INS  ,
          ID_UTENTE_AGG  ,
          DT_INSERIMENTO  ,
          DT_AGGIORNAMENTO,
          ID_MODALITA_AGEVOLAZIONE
         )values
         (seq_pbandi_t_cessione_quota_fp.nextval,
          vid_progetto_split,
          rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_CAPITALE,
          rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_ONERI,
          rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_INTERESSI_MORA,
          rec_T_CESSIONE_QUOTA_FP.IMP_CESSIONE_COMPLESSIVA,
          rec_T_CESSIONE_QUOTA_FP.DT_CESSIONE,
          rec_T_CESSIONE_QUOTA_FP.IMP_CORRISP_CESSIONE,
          rec_T_CESSIONE_QUOTA_FP.DENOM_CESSIONARIO,
          rec_T_CESSIONE_QUOTA_FP.ID_STATO_CESSIONE,
          rec_T_CESSIONE_QUOTA_FP.NOTE,
          rec_T_CESSIONE_QUOTA_FP.DT_INIZIO_VALIDITA,
          rec_T_CESSIONE_QUOTA_FP.DT_FINE_VALIDITA,
          -14, --rec_T_CESSIONE_QUOTA_FP.ID_UTENTE_INS,
          NULL, --rec_T_CESSIONE_QUOTA_FP.ID_UTENTE_AGG,
          rec_T_CESSIONE_QUOTA_FP.DT_INSERIMENTO,
          rec_T_CESSIONE_QUOTA_FP.Dt_Aggiornamento,
          rec_T_CESSIONE_QUOTA_FP.ID_MODALITA_AGEVOLAZIONE
          )returning ID_CESSIONE_QUOTA_FP into vID_CESSIONE_QUOTA_FP;

    Update MFINPIS_T_CESSIONE_QUOTA_FP
       Set    data_caricamento = sysdate,
              ID_CESSIONE_QUOTA_FP_pban = vID_CESSIONE_QUOTA_FP
       Where  ID_CESSIONE_QUOTA_FP = rec_T_CESSIONE_QUOTA_FP.ID_CESSIONE_QUOTA_FP;

End if;

END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_T_CESSIONE_QUOTA_FP  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 53. T_ESCUSS_CONFIDI
For Rec_T_ESCUSS_CONFIDI IN
    (Select * from MFINPIS_T_ESCUSS_CONFIDI
      WHERE data_caricamento is null
    )
   LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_T_ESCUSS_CONFIDI.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_T_ESCUSS_CONFIDI.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

Insert into pbandi_T_ESCUSS_CONFIDI
   (  ID_ESCUSS_CONFIDI,
      ID_PROGETTO,
      ID_ATTIVITA_GARANZIA_CONFIDI,
      DENOM_CONFIDI,
      DT_ESCUSS_CONFIDI,
      DT_PAGAM_CONFIDI,
      PERC_GARANZIA,
      NOTE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_MODALITA_AGEVOLAZIONE
   )values
   (SEQ_pbandi_T_ESCUSS_CONFIDI.Nextval,
      vid_progetto_split,
      Rec_T_ESCUSS_CONFIDI.ID_ATTIVITA_GARANZIA_CONFIDI,
      Rec_T_ESCUSS_CONFIDI.DENOM_CONFIDI,
      Rec_T_ESCUSS_CONFIDI.DT_ESCUSS_CONFIDI,
      Rec_T_ESCUSS_CONFIDI.DT_PAGAM_CONFIDI,
      Rec_T_ESCUSS_CONFIDI.PERC_GARANZIA,
      Rec_T_ESCUSS_CONFIDI.NOTE,
      Rec_T_ESCUSS_CONFIDI.DT_INIZIO_VALIDITA,
      Rec_T_ESCUSS_CONFIDI.DT_FINE_VALIDITA,
      Rec_T_ESCUSS_CONFIDI.ID_UTENTE_INS,
      Rec_T_ESCUSS_CONFIDI.ID_UTENTE_AGG,
      Rec_T_ESCUSS_CONFIDI.DT_INSERIMENTO,
      Rec_T_ESCUSS_CONFIDI.DT_AGGIORNAMENTO,
      Rec_T_ESCUSS_CONFIDI.ID_MODALITA_AGEVOLAZIONE
    )returning ID_ESCUSS_CONFIDI into vID_ESCUSS_CONFIDI;

    Update mfinpis_T_ESCUSS_CONFIDI
       Set    data_caricamento = sysdate,
              ID_ESCUSS_CONFIDI_pban = vID_ESCUSS_CONFIDI
       Where  ID_ESCUSS_CONFIDI = Rec_T_ESCUSS_CONFIDI.ID_ESCUSS_CONFIDI;
End if; --
   END LOOP;
   
vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_T_ESCUSS_CONFIDI  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
 -- 54. T_SCRIZIONE_RUOLO
For rec_t_iscrizione_ruolo IN
    (Select * from mfinpis_t_iscrizione_ruolo
      WHERE data_caricamento is null
    )
   LOOP
   
   Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_iscrizione_ruolo.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_iscrizione_ruolo.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
                when too_many_rows then -- gestione anomalia, mail di s.gabriele del 020823
        vcount := 0;
        dbms_output.put_line(rec_t_iscrizione_ruolo.Id_Progetto);
      end;
   End;
   ----
If vcount <> 0 then



    Insert into pbandi_t_iscrizione_ruolo
       (ID_ISCRIZIONE_RUOLO,
        ID_PROGETTO,
        DT_RICHIESTA_ISCRIZIONE,
        NUM_PROTOCOLLO,
        DT_RICHIESTA_DISCARICO,
        NUM_PROTOCOLLO_DISCARICO,
        DT_ISCRIZIONE_RUOLO,
        DT_DISCARICO,
        NUM_PROTOCOLLO_DISCAR_REG  ,
        DT_RICHIESTA_SOSP  ,
        NUM_PROTOCOLLO_SOSP  ,
        IMP_CAPITALE_RUOLO  ,
        IMP_AGEVOLAZ_RUOLO  ,
        DT_ISCRIZIONE,
        NUM_PROTOCOLLO_REGIONE  ,
        TIPO_PAGAMENTO  ,
        NOTE  ,
        DT_INIZIO_VALIDITA  ,
        DT_FINE_VALIDITA,
        ID_UTENTE_INS  ,
        ID_UTENTE_AGG  ,
        DT_INSERIMENTO  ,
        DT_AGGIORNAMENTO ,
        ID_MODALITA_AGEVOLAZIONE
       )values
       (seq_pbandi_t_iscrizione_ruolo.nextval,
        vid_progetto_split,
        rec_t_iscrizione_ruolo.DT_RICHIESTA_ISCRIZIONE,
        rec_t_iscrizione_ruolo.NUM_PROTOCOLLO,
        rec_t_iscrizione_ruolo.DT_RICHIESTA_DISCARICO,
        rec_t_iscrizione_ruolo.NUM_PROTOCOLLO_DISCARICO,
        rec_t_iscrizione_ruolo.DT_ISCRIZIONE_RUOLO,
        rec_t_iscrizione_ruolo.DT_DISCARICO,
        rec_t_iscrizione_ruolo.NUM_PROTOCOLLO_DISCAR_REG  ,
        rec_t_iscrizione_ruolo.DT_RICHIESTA_SOSP  ,
        rec_t_iscrizione_ruolo.NUM_PROTOCOLLO_SOSP  ,
        rec_t_iscrizione_ruolo.IMP_CAPITALE_RUOLO  ,
        rec_t_iscrizione_ruolo.IMP_AGEVOLAZ_RUOLO  ,
        rec_t_iscrizione_ruolo.DT_ISCRIZIONE,
        rec_t_iscrizione_ruolo.NUM_PROTOCOLLO_REGIONE  ,
        rec_t_iscrizione_ruolo.TIPO_PAGAMENTO  ,
        rec_t_iscrizione_ruolo.NOTE  ,
       rec_t_iscrizione_ruolo.DT_INIZIO_VALIDITA  ,
        rec_t_iscrizione_ruolo.DT_FINE_VALIDITA,
        -14,--ID_UTENTE_INS  ,
        null,--ID_UTENTE_AGG  ,
        rec_t_iscrizione_ruolo.DT_INSERIMENTO,
        rec_t_iscrizione_ruolo.Dt_Aggiornamento,
        rec_t_iscrizione_ruolo.ID_MODALITA_AGEVOLAZIONE
        )returning ID_ISCRIZIONE_RUOLO into vID_ISCRIZIONE_RUOLO;


    Update mfinpis_t_iscrizione_ruolo
       Set    data_caricamento = sysdate,
              ID_ISCRIZIONE_RUOLO_pban = vID_ISCRIZIONE_RUOLO
       Where  ID_ISCRIZIONE_RUOLO = rec_t_iscrizione_ruolo.id_iscrizione_ruolo;

End if; --
END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_iscrizione_ruolo  riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 55. T_PASSAGGIO_PERDITA

For rec_t_passaggio_perdita IN
    (Select * from mfinpis_t_passaggio_perdita
      WHERE data_caricamento is null
    )
   LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_passaggio_perdita.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_passaggio_perdita.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

  Insert into pbandi_t_passaggio_perdita
   (ID_PASSAGGIO_PERDITA,
    ID_PROGETTO,
    DT_PASSAGGIO_PERDITA,
    IMP_PERDITA_COMPLESSIVA,
    IMP_PERDITA_CAPITALE,
    IMP_PERDITA_INTERESSI,
    IMP_PERDITA_AGEVOLAZ,
    IMP_PERDITA_MORA,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_MODALITA_AGEVOLAZIONE
  )values
   (seq_pbandi_t_passaggio_perdita.nextval,
    vid_progetto_split,
    rec_T_PASSAGGIO_PERDITA.DT_PASSAGGIO_PERDITA,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_COMPLESSIVA,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_CAPITALE,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_INTERESSI,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_AGEVOLAZ,
    rec_T_PASSAGGIO_PERDITA.IMP_PERDITA_MORA,
    rec_T_PASSAGGIO_PERDITA.NOTE,
    rec_T_PASSAGGIO_PERDITA.DT_INIZIO_VALIDITA,
    rec_T_PASSAGGIO_PERDITA.DT_FINE_VALIDITA,
    -14,--rec_T_PASSAGGIO_PERDITA.ID_UTENTE_INS,
    NULL,-- rec_T_PASSAGGIO_PERDITA.ID_UTENTE_AGG,
    SYSDATE,--rec_T_PASSAGGIO_PERDITA.DT_INSERIMENTO,
    rec_T_PASSAGGIO_PERDITA.DT_AGGIORNAMENTO,
    rec_T_PASSAGGIO_PERDITA.ID_MODALITA_AGEVOLAZIONE
   )RETURNING ID_PASSAGGIO_PERDITA INTO vID_PASSAGGIO_PERDITA;


   Update mfinpis_t_passaggio_perdita
       Set    data_caricamento = sysdate,
              ID_PASSAGGIO_PERDITA_pban = vID_PASSAGGIO_PERDITA
       Where  vID_PASSAGGIO_PERDITA = rec_t_passaggio_perdita.id_passaggio_perdita;
end if;
   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_passaggio_perdita riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 56. T_PIANO_RIENTRO

For rec_t_piano_rientro IN
    (Select * from mfinpis_t_piano_rientro
      WHERE data_caricamento is null
    )
   LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_piano_rientro.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_piano_rientro.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then
  
   if rec_T_PIANO_RIENTRO.DT_ESITO is not null then vid_Esito := 14; else vid_Esito := null; end if;

    Insert into pbandi_T_PIANO_RIENTRO
   (ID_PIANO_RIENTRO,
    ID_PROGETTO,
    DT_PROPOSTA,
    IMP_QUOTA_CAPITALE,
    IMP_AGEVOLAZIONE,
    ID_ATTIVITA_ESITO,
    DT_ESITO,
    ID_ATTIVITA_RECUPERO,
    NOTE,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    ID_MODALITA_AGEVOLAZIONE
  )values
   (seq_pbandi_t_piano_rientro.nextval,
    vid_progetto_split,
    rec_T_PIANO_RIENTRO.DT_PROPOSTA,
    rec_T_PIANO_RIENTRO.IMP_QUOTA_CAPITALE,
    rec_T_PIANO_RIENTRO.IMP_AGEVOLAZIONE,
    vid_Esito, --rec_T_PIANO_RIENTRO.ID_ATTIVITA_ESITO, -- richiesta Arangino/Caputo
    rec_T_PIANO_RIENTRO.DT_ESITO,
    rec_T_PIANO_RIENTRO.ID_ATTIVITA_RECUPERO,
    rec_T_PIANO_RIENTRO.NOTE,
    rec_T_PIANO_RIENTRO.DT_INIZIO_VALIDITA,
    rec_T_PIANO_RIENTRO.DT_FINE_VALIDITA,
    decode(rec_T_PIANO_RIENTRO.DT_ESITO,null,null,14)
    -14, --rec_T_PIANO_RIENTRO.ID_UTENTE_INS,
    NULL,--rec_T_PIANO_RIENTRO.ID_UTENTE_AGG,
    rec_T_PIANO_RIENTRO.DT_INSERIMENTO,
    rec_T_PIANO_RIENTRO.DT_AGGIORNAMENTO,
    rec_T_PIANO_RIENTRO.ID_MODALITA_AGEVOLAZIONE
   ) RETURNING ID_PIANO_RIENTRO INTO vID_PIANO_RIENTRO ;

   Update mfinpis_t_piano_rientro
       Set    data_caricamento = sysdate,
              ID_PIANO_RIENTRO_pban = vID_PIANO_RIENTRO
       Where  ID_PIANO_RIENTRO = rec_t_piano_rientro.id_piano_rientro;

 End if; ---
END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_T_PIANO_RIENTRO riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 57. T_REVOCA_BANCARIA
For rec_t_revoca_bancaria IN
    (Select * from mfinpis_t_revoca_bancaria
      WHERE data_caricamento is null
    )
   LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_revoca_bancaria.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_revoca_bancaria.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

      Insert into pbandi_t_revoca_bancaria
   (ID_REVOCA_BANCARIA,
      ID_PROGETTO,
      DT_RICEZIONE_COMUNICAZ,
      DT_REVOCA,
      IMP_DEBITO_RESIDUO_BANCA,
      IMP_DEBITO_RESIDUO_FP,
      PERC_COFINANZ_FP,
      NUM_PROTOCOLLO,
      NOTE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO,
      ID_MODALITA_AGEVOLAZIONE
  )values
   (seq_pbandi_t_revoca_bancaria.nextval,
      vid_progetto_split,
      rec_T_REVOCA_BANCARIA.DT_RICEZIONE_COMUNICAZ,
      rec_T_REVOCA_BANCARIA.DT_REVOCA,
      rec_T_REVOCA_BANCARIA.IMP_DEBITO_RESIDUO_BANCA,
      rec_T_REVOCA_BANCARIA.IMP_DEBITO_RESIDUO_FP,
      rec_T_REVOCA_BANCARIA.PERC_COFINANZ_FP,
      rec_T_REVOCA_BANCARIA.NUM_PROTOCOLLO,
      rec_T_REVOCA_BANCARIA.NOTE,
      rec_T_REVOCA_BANCARIA.DT_INIZIO_VALIDITA,
      rec_T_REVOCA_BANCARIA.DT_FINE_VALIDITA,
      -14, --rec_T_REVOCA_BANCARIA.ID_UTENTE_INS,
      NULL,--rec_T_REVOCA_BANCARIA.ID_UTENTE_AGG,
      rec_T_REVOCA_BANCARIA.DT_INSERIMENTO,
      rec_T_REVOCA_BANCARIA.DT_AGGIORNAMENTO,
      rec_T_REVOCA_BANCARIA.ID_MODALITA_AGEVOLAZIONE

   ) RETURNING ID_REVOCA_BANCARIA INTO vID_REVOCA_BANCARIA;

    Update mfinpis_t_revoca_bancaria
       Set    data_caricamento = sysdate,
              ID_REVOCA_BANCARIA_pban = vID_REVOCA_BANCARIA
       Where  ID_REVOCA_BANCARIA = rec_T_REVOCA_BANCARIA.Id_Revoca_Bancaria;

End if;
END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_revoca_bancaria riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 58. T_SALDO_STRALCIO
For rec_t_saldo_stralcio IN
    (Select * from mfinpis_t_saldo_stralcio
      WHERE data_caricamento is null
    )
   LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_saldo_stralcio.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_saldo_stralcio.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

      Insert into pbandi_t_saldo_stralcio
   (ID_SALDO_STRALCIO,
      ID_PROGETTO,
      ID_ATTIVITA_SALDO_STRALCIO,
      DT_PROPOSTA,
      DT_ACCETTAZIONE,
      IMP_DEBITORE,
      IMP_CONFIDI,
      ID_ATTIVITA_ESITO,
      DT_ESITO,
      DT_PAGAM_DEBITORE,
      DT_PAGAM_CONFIDI,
      ID_ATTIVITA_RECUPERO,
      IMP_RECUPERATO  ,
      IMP_PERDITA,
      NOTE  ,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS  ,
      ID_UTENTE_AGG  ,
      DT_INSERIMENTO  ,
      DT_AGGIORNAMENTO,
      ID_MODALITA_AGEVOLAZIONE
  )values
   (  seq_pbandi_t_saldo_stralcio.nextval,
      vid_progetto_split,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_SALDO_STRALCIO,
      rec_T_SALDO_STRALCIO.DT_PROPOSTA,
      rec_T_SALDO_STRALCIO.DT_ACCETTAZIONE,
      rec_T_SALDO_STRALCIO.IMP_DEBITORE,
      rec_T_SALDO_STRALCIO.IMP_CONFIDI,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_ESITO,
      rec_T_SALDO_STRALCIO.DT_ESITO,
      rec_T_SALDO_STRALCIO.DT_PAGAM_DEBITORE,
      rec_T_SALDO_STRALCIO.DT_PAGAM_CONFIDI,
      rec_T_SALDO_STRALCIO.ID_ATTIVITA_RECUPERO,
      rec_T_SALDO_STRALCIO.IMP_RECUPERATO  ,
      rec_T_SALDO_STRALCIO.IMP_PERDITA,
      rec_T_SALDO_STRALCIO.NOTE  ,
      rec_T_SALDO_STRALCIO.DT_INIZIO_VALIDITA,
      rec_T_SALDO_STRALCIO.DT_FINE_VALIDITA,
      rec_T_SALDO_STRALCIO.ID_UTENTE_INS  ,
      rec_T_SALDO_STRALCIO.ID_UTENTE_AGG  ,
      rec_T_SALDO_STRALCIO.DT_INSERIMENTO  ,
      rec_T_SALDO_STRALCIO.DT_AGGIORNAMENTO,
      rec_T_SALDO_STRALCIO.ID_MODALITA_AGEVOLAZIONE
   ) RETURNING ID_SALDO_STRALCIO INTO vID_SALDO_STRALCIO;

    Update mfinpis_t_saldo_stralcio
       Set    data_caricamento = sysdate,
              ID_SALDO_STRALCIO_pban = vID_SALDO_STRALCIO
       Where  ID_SALDO_STRALCIO = rec_t_saldo_stralcio.ID_SALDO_STRALCIO;

End if; --
   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_saldo_stralcio riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- 59. T_SOGG_STATO_AZIENDA

For rec_T_SOGG_STATO_AZIENDA IN
    (Select * from mfinpis_t_sogg_stato_azienda
      WHERE data_caricamento is null
    )
   LOOP
   --
   select count(*)
      into vcount_ndg
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO;
IF   vcount_ndg <> 0 THEN --- DA TOGLIERE ?
   if vcount_ndg = 0 then vId_Soggetto_NDG:= null; end if;
   --

   --- calcolo ndg
   -- in caso di più record si prende la persona giuridica
   vcount:=0;
   For rec in
     (select id_soggetto
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO
      order by id_tipo_soggetto desc
      )loop
      if vcount = 0 then
          vId_Soggetto_NDG:=rec.id_soggetto;
          vcount := 1;
      end if;
        end loop;
     ---
     ---

      Insert into pbandi_t_sogg_stato_azienda
   (ID_SOGGETTO_STATO_AZIENDA,
    ID_SOGGETTO,
    ID_STATO_AZIENDA,
    ID_UTENTE_INS,
    ID_UTENTE_AGG,
    DT_INSERIMENTO,
    DT_AGGIORNAMENTO,
    DT_INIZIO_VALIDITA,
    DT_FINE_VALIDITA
  )values
   (seq_pbandi_t_sogg_stato_azi.nextval,
    vId_Soggetto_NDG, --rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO,
    rec_T_SOGG_STATO_AZIENDA.ID_STATO_AZIENDA,
    rec_T_SOGG_STATO_AZIENDA.ID_UTENTE_INS,
    rec_T_SOGG_STATO_AZIENDA.ID_UTENTE_AGG,
    rec_T_SOGG_STATO_AZIENDA.DT_INSERIMENTO,
    rec_T_SOGG_STATO_AZIENDA.DT_AGGIORNAMENTO,
    rec_T_SOGG_STATO_AZIENDA.DT_INIZIO_VALIDITA,
    rec_T_SOGG_STATO_AZIENDA.DT_FINE_VALIDITA
   ) RETURNING ID_SOGGETTO_STATO_AZIENDA INTO vID_SOGGETTO_STATO_AZIENDA;

    Update mfinpis_t_sogg_stato_azienda
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_STATO_AZ_PBAN = vID_SOGGETTO_STATO_AZIENDA
       Where  ID_SOGGETTO_STATO_AZIENDA = rec_T_SOGG_STATO_AZIENDA.ID_SOGGETTO_STATO_AZIENDA;
END IF; -- DA TOGLIERE

   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS -  pbandi_t_sogg_stato_azienda riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 60. T_SOGGETTO_CLA_RISCHIO

For rec_T_SOGGETTO_CLA_RISCHIO IN
    (Select * from mfinpis_t_soggetto_cla_rischio
      WHERE data_caricamento is null
    )
   LOOP

    ----
      select count(*)
      into vcount_ndg
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGGETTO_CLA_RISCHIO.ID_SOGGETTO;
 IF  vcount_ndg <> 0 THEN -- DA TOGLIERE
    if vcount_ndg = 0 then vId_Soggetto_NDG:= null; end if;
    ----


    --- calcolo NDG
    ---
      vcount:=0;
      For rec in
     (select id_soggetto
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGGETTO_CLA_RISCHIO.ID_SOGGETTO
      order by id_tipo_soggetto desc
      )loop
      if vcount = 0 then
          vId_Soggetto_NDG:=rec.id_soggetto;
          vcount := 1;
      end if;
        end loop;
     ---
     ---

      Insert into pbandi_t_soggetto_cla_rischio
   (ID_SOGGETTO_CLA_RISCHIO,
      ID_SOGGETTO,
      ID_CLASSE_RISCHIO,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO
  )values
   ( seq_pbandi_t_sogg_cla_rischio.nextval,
      vId_Soggetto_NDG,
      rec_T_SOGGETTO_CLA_RISCHIO.ID_CLASSE_RISCHIO,
      rec_T_SOGGETTO_CLA_RISCHIO.DT_INSERIMENTO,
      rec_T_SOGGETTO_CLA_RISCHIO.DT_AGGIORNAMENTO,
      -14, --rec_T_SOGGETTO_CLA_RISCHIO.ID_UTENTE_INS,
      rec_T_SOGGETTO_CLA_RISCHIO.ID_UTENTE_AGG,
      rec_T_SOGGETTO_CLA_RISCHIO.DT_INIZIO_VALIDITA,
      rec_T_SOGGETTO_CLA_RISCHIO.DT_FINE_VALIDITA
   ) RETURNING ID_SOGGETTO_CLA_RISCHIO INTO vID_SOGGETTO_CLA_RISCHIO;

    Update mfinpis_t_soggetto_cla_rischio
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_CLA_RISC_pban = vID_SOGGETTO_CLA_RISCHIO
       Where  ID_SOGGETTO_CLA_RISCHIO = rec_T_SOGGETTO_CLA_RISCHIO.ID_SOGGETTO_CLA_RISCHIO;
END IF; -- DA TOGLIERE
   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_soggetto_cla_rischio riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
-- 61. T_SOGGETTO_RATING
For rec_T_SOGGETTO_RATING IN
    (Select * from mfinpis_t_soggetto_rating
      WHERE data_caricamento is null
    )
   LOOP
  --
  select count(*)
      into vcount_ndg
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGGETTO_RATING.ID_SOGGETTO;
IF     vcount_ndg <> 0 THEN -- DA TOGLIERE
  if vcount_ndg = 0 then vId_Soggetto_NDG:= null; end if;
  --
  -- calcolo NDG
  --
    vcount:=0;
   For rec in
     (select id_soggetto
      from   pbandi_t_soggetto
      where  NDG = rec_T_SOGGETTO_RATING.ID_SOGGETTO
      order by id_tipo_soggetto desc
      )loop
      if vcount = 0 then
          vId_Soggetto_NDG:=rec.id_soggetto;
          vcount := 1;
      end if;
        end loop;
     ---
     ---

      Insert into pbandi_t_soggetto_rating
   (ID_SOGGETTO_RATING,
      ID_SOGGETTO,
      ID_RATING,
      DT_CLASSIFICAZIONE,
      DT_INIZIO_VALIDITA,
      DT_FINE_VALIDITA,
      ID_UTENTE_INS,
      ID_UTENTE_AGG,
      DT_INSERIMENTO,
      DT_AGGIORNAMENTO
  )values
   ( seq_pbandi_t_sogg_rating.nextval,
     vId_Soggetto_NDG, --rec_T_SOGGETTO_RATING.Id_Soggetto,
     rec_T_SOGGETTO_RATING.Id_Rating,
     rec_T_SOGGETTO_RATING.Dt_Classificazione,
      rec_T_SOGGETTO_RATING.DT_INSERIMENTO,
      rec_T_SOGGETTO_RATING.DT_AGGIORNAMENTO,
      rec_T_SOGGETTO_RATING.ID_UTENTE_INS,
      rec_T_SOGGETTO_RATING.ID_UTENTE_AGG,
      rec_T_SOGGETTO_RATING.DT_INIZIO_VALIDITA,
      rec_T_SOGGETTO_RATING.DT_FINE_VALIDITA
   ) RETURNING ID_SOGGETTO_RATING INTO vID_SOGGETTO_RATING;

    Update mfinpis_t_soggetto_rating
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_RATING_pban = vID_SOGGETTO_RATING
       Where  ID_SOGGETTO_RATING = rec_T_SOGGETTO_RATING.ID_SOGGETTO_RATING;
END IF; -- DA TOGLIERE
   END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - pbandi_t_soggetto_rating riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;
-------------------------------------------------
-------------FINE AREA CREDITO ------------------
-------------------------------------------------

begin
------------------------------------
-- 70. t_ammortamento_progetti
------------------------------------

-- da verificare prima di eseguire se il progetto e splittato
/*
select substr(id_progetto,1,10), count(*) from mfinpis_T_DELIBERA_PROG
group by substr(id_progetto,1,10) having count(*) > 1
*/


-- query elaborazione Dati pre-insert
--
update MFINPIS_T_DELIBERA_PROG a
set data_caricamento = trunc(sysdate)
where substr(id_progetto,1,10) in
                              (select substr(id_progetto,1,10)
                                 from   MFINPIS_T_DELIBERA_PROG a
                                group by  substr(id_progetto,1,10)
                               having count(*)  > 1)
and rowid != (select min(rowid)
                     from MFINPIS_T_DELIBERA_PROG
                     where substr(id_progetto,1,10) = substr(a.id_progetto,1,10));

commit;

--
update mfinpis_t_ammortamento_prog
set data_caricamento = trunc(sysdate)
where id_delibera in
(select id_delibera from mfinpis_t_delibera_prog where data_caricamento is not null);
commit;
--
--

For rec_T_DELIBERA_PROG IN
  (select * from mfinpis_T_DELIBERA_PROG
    WHERE data_caricamento is null
   )
LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_T_DELIBERA_PROG.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_T_DELIBERA_PROG.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        dbms_output.put_line (rec_T_DELIBERA_PROG.Id_Progetto);
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

      INSERT INTO PBANDI_T_DELIBERA_PROGETTI
      (
        id_delibera,
          id_progetto,
          oggetto_delibera,
          dt_delibera,
          dt_annullamento,
          dt_inizio_validita,
          dt_fine_validita,
          id_utente_ins,
          id_utente_agg
          )VALUES
          (SEQ_PBANDI_T_DELIBERA_PROGETTI.NEXTVAL,
          vid_progetto_split,
          rec_T_DELIBERA_PROG.oggetto_delibera,
          rec_T_DELIBERA_PROG.dt_delibera,
          rec_T_DELIBERA_PROG.dt_annullamento,
          rec_T_DELIBERA_PROG.dt_inizio_validita,
          rec_T_DELIBERA_PROG.dt_fine_validita,
          rec_T_DELIBERA_PROG.id_utente_ins,
          rec_T_DELIBERA_PROG.id_utente_agg
          ) RETURNING id_delibera INTO vid_delibera;
          
          Update mfinpis_T_DELIBERA_PROG
          Set    data_caricamento = sysdate,
                 id_delibera_pban = vid_delibera
          Where  id_delibera = rec_T_DELIBERA_PROG.Id_Delibera;
          

       FOR  Rec_T_AMMORTAMENTO_PROG in
        (Select t.* from mfinpis_t_ammortamento_prog t
         WHERE data_caricamento is null and
               id_delibera = rec_T_DELIBERA_PROG.Id_Delibera)
          LOOP

          INSERT INTO pbandi_t_ammortamento_progetti
        ( id_ammort_progetti,
          id_delibera,
          mm_ammortamento,
          mm_preammortamento,
          id_tipo_tasso,
          ID_TIPO_CALC_AMM,
          ID_TASSO_AMM,
          perc_spread,
          ID_FREQUENZA_AMMORTAMENTO, -- mc 04092023
          perc_interessi,
          dt_inizio_validita,
          dt_fine_validita,
          id_utente_ins,
          id_utente_agg,
          ID_TIPO_PERIODO_AMMORTAMENTO -- mc 04092023
        )
        values
        ( seq_pbandi_t_ammortam_progetti.nextval,
          vid_delibera,
          Rec_T_AMMORTAMENTO_PROG.mm_ammortamento,
          Rec_T_AMMORTAMENTO_PROG.mm_preammortamento,
          Rec_T_AMMORTAMENTO_PROG.id_tipo_tasso,
          Rec_T_AMMORTAMENTO_PROG.ID_TIPO_CALC_AMM,
          Rec_T_AMMORTAMENTO_PROG.ID_TASSO_AMM,
          Rec_T_AMMORTAMENTO_PROG.perc_spread,
          Rec_T_AMMORTAMENTO_PROG.frequenza,
          Rec_T_AMMORTAMENTO_PROG.perc_interessi,
          Rec_T_AMMORTAMENTO_PROG.dt_inizio_validita,
          Rec_T_AMMORTAMENTO_PROG.Dt_Fine_Validita,
          -14,
          NULL,
          Rec_T_AMMORTAMENTO_PROG.ID_TIPO_PERIODO_AMMORTAMENTO -- mc 04092023
         ) RETURNING ID_AMMORT_PROGETTI INTO vID_AMMORT_PROGETTI_PBAN;
         
         Update mfinpis_t_ammortamento_prog
          Set    data_caricamento = sysdate,
                 ID_AMMORT_PROGETTI_PBAN = vID_AMMORT_PROGETTI_PBAN
          Where  ID_AMMORT_PROGETTI = Rec_T_AMMORTAMENTO_PROG.ID_AMMORT_PROGETTI;
         
         
End loop;

End if;

End Loop;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - PBANDI_T_DELIBERA_PROGETTI/pbandi_t_ammortamento_progetti riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
------------------------------------
-- 71. t_rna_progetto
------------------------------------
For rec_t_rna_progetto IN
    (Select * from mfinpis_t_rna_progetto
      WHERE data_caricamento is null
     )
  LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_rna_progetto.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_rna_progetto.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then


  insert into PBANDI_T_RNA_PROGETTO
     (ID_RNA_PROGETTO  ,
      CODICE_BANDO_RNA  ,
      ID_PROGETTO  ,
      ID_STATO_PROGETTO  ,
      ID_CONCESSIONE_GEST  ,
      ID_COMP_AIUTO_GEST  ,
      COR  ,
      CODICE_FISCALE_BENEFICIARIO  ,
      COVAR  ,
      COR_COLLEGTO  ,
      DT_ESITO_VALIDAZIONE  ,
      ID_COSTO_GEST  ,
      ID_STRUM_AIUTO_GEST  ,
      ID_MODALITA_AGEVOLAZIONE  ,
      IMPORTO_NOMINALE  ,
      IMPORTO_AGEVOLAZIONE  ,
      INTENSITA_AIUTO  ,
      DT_RICH_ANNULLAMENTO  ,
      ESITO_REG  ,
      ESITO_CONFERMA_REG  ,
      DT_ESITO  ,
      DT_CONFERMA_REG  ,
      ID_PROVVEDIMENTO  ,
      ID_CARICAMENTO  ,
      COD_TIPO_VARIAZIONE  ,
      ID_RICHIESTA_RNA ,
      DT_INIZIO_VALIDITA  ,
      DT_FINE_VALIDITA  ,
      ID_UTENTE_INS  ,
      ID_UTENTE_AGG
  )
  values
  (SEQ_PBANDI_T_RNA_PROGETTO.NEXTVAL,
      rec_t_rna_progetto.CODICE_BANDO_RNA  ,
      vid_progetto_split  ,
      1,--NULL,--rec_t_rna_progetto.ID_STATO_PROGETTO  ,  ---    VALORE OBBLIGATORIO
      rec_t_rna_progetto.ID_CONCESSIONE_GEST  ,
      rec_t_rna_progetto.ID_COMP_AIUTO_GEST  ,
      rec_t_rna_progetto.COR  ,
      rec_t_rna_progetto.CODICE_FISCALE_BENEFICIARIO  ,
      rec_t_rna_progetto.COVAR  ,
      NULL,--rec_t_rna_progetto.COR_COLLEGTO  ,  ---
      rec_t_rna_progetto.DT_ESITO_VALIDAZIONE  ,
      rec_t_rna_progetto.ID_COSTO_GEST  ,
      rec_t_rna_progetto.ID_STRUM_AIUTO_GEST  ,
      rec_t_rna_progetto.ID_MODALITA_AGEVOLAZIONE  ,
      rec_t_rna_progetto.IMPORTO_NOMINALE  ,
      rec_t_rna_progetto.IMPORTO_AGEVOLAZIONE  ,
      rec_t_rna_progetto.INTENSITA_AIUTO  ,
      NULL,--rec_t_rna_progetto.DT_RICH_ANNULLAMENTO  ,  ---
      NULL,--rec_t_rna_progetto.ESITO_REG  ,  ---
      NULL,--rec_t_rna_progetto.ESITO_CONFERMA_REG  , ---
      rec_t_rna_progetto.dt_esito_validazione  ,
      NULL,--rec_t_rna_progetto.DT_CONFERMA_REG  , ---
      NULL,--rec_t_rna_progetto.ID_PROVVEDIMENTO  ,  ---
      NULL,--rec_t_rna_progetto.ID_CARICAMENTO  ,  ---
      NULL,--rec_t_rna_progetto.COD_TIPO_VARIAZIONE  ,  ---
      NULL,--rec_t_rna_progetto.ID_RICHIESTA_RNA , --- VALORE OBBLIGATORIO
      rec_t_rna_progetto.DT_INIZIO_VALIDITA  ,
      rec_t_rna_progetto.DT_FINE_VALIDITA  ,
      -14, --ID_UTENTE_INS  ,
      NULL
  )RETURNING ID_RNA_PROGETTO INTO vID_RNA_PROGETTO_pban;

     Update mfinpis_t_rna_progetto
       Set    data_caricamento = sysdate,
              ID_RNA_PROGETTO_pban = vID_RNA_PROGETTO_pban
       Where  ID_RNA_PROGETTO = rec_t_rna_progetto.ID_RNA_PROGETTO;

 End if;

END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - PBANDI_T_RNA_PROGETTO riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
------------------------
-- t_rna_progetto_man
------------------------


For rec_t_rna_progetto_man IN
    (Select * from mfinpis_t_rna_progetto_man
      WHERE data_caricamento is null
     )
  LOOP

Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_rna_progetto_man.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_rna_progetto_man.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

 
  Insert into PBANDI_T_RNA_PROGETTO_MAN
  (
  ID_RNA_PROGETTO_MAN,
      ID_PROGETTO,
      ACOVAR1,
      DDATCOVAR1,
      ACOVAR2,
      DDATCOVAR2,
      ACOVAR3,
      DDATCOVAR3,
      AVERCOR1,
      DDTVERCOR1,
      AVERCOR2,
      DDTVERCOR2,
      AVERCOR3,
      DDTVERCOR3,
      ACOR2,
      DDATACOR2,
      ACUP,
      AIDCONC,
      AIDRICH,
      CODRNA,
      CODRNA2,
      DDATRNA2,
      ID_UTENTE_INS
 ) values
 ( SEQ_PBANDI_T_RNA_PROGETTO_MAN.nextval,
    vid_progetto_split,
    rec_t_rna_progetto_man.ACOVAR1,
    rec_t_rna_progetto_man.DDATCOVAR1,
    rec_t_rna_progetto_man.ACOVAR2,
    rec_t_rna_progetto_man.DDATCOVAR2,
    rec_t_rna_progetto_man.ACOVAR3,
    rec_t_rna_progetto_man.DDATCOVAR3,
    rec_t_rna_progetto_man.AVERCOR1,
    rec_t_rna_progetto_man.DDTVERCOR1,
    rec_t_rna_progetto_man.AVERCOR2,
    rec_t_rna_progetto_man.DDTVERCOR2,
    rec_t_rna_progetto_man.AVERCOR3,
    rec_t_rna_progetto_man.DDTVERCOR3,
    rec_t_rna_progetto_man.ACOR2,
    rec_t_rna_progetto_man.DDATACOR2,
    rec_t_rna_progetto_man.ACUP,
    rec_t_rna_progetto_man.AIDCONC,
    rec_t_rna_progetto_man.AIDRICH,
    rec_t_rna_progetto_man.CODRNA,
    rec_t_rna_progetto_man.CODRNA2,
    rec_t_rna_progetto_man.DDATRNA2,
   -14) returning ID_RNA_PROGETTO_man into vID_RNA_PROGETTO_man_pban;
 
 
Update mfinpis_t_rna_progetto_man
       Set    data_caricamento = sysdate,
              ID_RNA_PROGETTO_man_pban = vID_RNA_PROGETTO_man_pban
       Where  ID_RNA_PROGETTO_man = rec_t_rna_progetto_man.ID_RNA_PROGETTO_man;

 End if;

END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - PBANDI_T_RNA_PROGETTO_MAN riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;

begin
------------------------------------
-- . t_soggetto_dsan
------------------------------------

For rec_t_soggetto_dsan IN
    (Select * from mfinpis_t_soggetto_dsan
      WHERE data_caricamento is null
     )
  LOOP
       for rec_DomandeSplit in
         (select * from pbandi_t_progetto
          where codice_visualizzato like rec_t_soggetto_dsan.id_domanda||'%'
          )
        loop
          
-- alter table PBANDI_T_SOGGETTO_DSAN add id_esito_dsan number(3);


  insert into PBANDI_T_SOGGETTO_DSAN
     (ID_SOGGETTO_DSAN,
        ID_DOMANDA,
        ID_ESITO_DSAN,
        DT_EMISSIONE_DSAN,
        DT_SCADENZA,
        --NUM_PROTOCOLLO, da verificare
        ID_UTENTE_INS,
        ID_UTENTE_AGG,
        DT_INIZIO_VALIDITA,
        DT_FINE_VALIDITA
  )
  values
  (seq_pbandi_t_soggetto_dsan.nextval,
        Rec_DomandeSPLIT.ID_DOMANDA,
        rec_t_soggetto_dsan.ID_ESITO_DSAN,
        rec_t_soggetto_dsan.DT_EMISSIONE_DSAN,
        rec_t_soggetto_dsan.DT_SCADENZA,
        --rec_t_soggetto_dsan.NUM_PROTOCOLLO,
        -14,    -- ID_UTENTE_INS,
        rec_t_soggetto_dsan.ID_UTENTE_AGG,
        sysdate, -- DT_INIZIO_VALIDITA,
        rec_t_soggetto_dsan.DT_FINE_VALIDITA
  )RETURNING ID_SOGGETTO_DSAN INTO vID_SOGGETTO_DSAN_pban;

     Update mfinpis_t_soggetto_dsan
       Set    data_caricamento = sysdate,
              ID_SOGGETTO_DSAN_pban = vID_SOGGETTO_DSAN_pban
              --ID_DOMANDA_pban = Rec_DomandeSPLIT.ID_DOMANDA
       Where  ID_SOGGETTO_DSAN = rec_t_soggetto_dsan.ID_SOGGETTO_DSAN;

   END LOOP;
End loop;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - PBANDI_T_SOGGETTO_DSAN riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



---------------------------------------------
---------------------------------------------
--    REVOCHE   -----------------------------
---------------------------------------------
---------------------------------------------

-----------------------------------
-- t_gestione_revoca
-----------------------------------

BEGIN
FOR REC_GESTIONE_REVOCA IN
  (Select * from mfinpis_t_gestione_revoca
   WHERE data_caricamento is null
     )
  LOOP

    Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = REC_GESTIONE_REVOCA.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         vdummy:= REC_GESTIONE_REVOCA.Id_Progetto;
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(REC_GESTIONE_REVOCA.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
        when too_many_rows then
          begin
                 Select id_progetto
                 Into   vid_progetto_split
                 From pbandi_t_progetto
                 Where codice_visualizzato like substr(REC_GESTIONE_REVOCA.Id_Progetto,1,11)||'%';
                 vcount := 1;
                  Exception when no_data_found then
                   vcount := 0;
          end;
      end;
   End;
   ----
If vcount <> 0 then

     INSERT INTO PBANDI_T_GESTIONE_REVOCA
      (ID_GESTIONE_REVOCA,
      NUMERO_REVOCA,
      ID_TIPOLOGIA_REVOCA,
      ID_PROGETTO,
      ID_CAUSALE_BLOCCO,
      ID_CATEG_ANAGRAFICA,
      DT_GESTIONE,
      NUM_PROTOCOLLO,
      FLAG_ORDINE_RECUPERO,
      ID_MANCATO_RECUPERO,
      ID_STATO_REVOCA,
      DT_STATO_REVOCA,
      DT_NOTIFICA,
      GG_RISPOSTA,
      FLAG_PROROGA,
      IMP_DA_REVOCARE_CONTRIB,
      IMP_DA_REVOCARE_FINANZ,
      IMP_DA_REVOCARE_GARANZIA,
      FLAG_DETERMINA,
      ESTREMI  ,
      DT_DETERMINA,
      NOTE  ,
      ID_ATTIVITA_REVOCA  ,
      DT_ATTIVITA  ,
      ID_MOTIVO_REVOCA,
      FLAG_CONTRIB_REVOCA,
      FLAG_CONTRIB_MINOR_SPESE,
      FLAG_CONTRIB_DECURTAZ  ,
      FLAG_FINANZ_REVOCA  ,
      FLAG_FINANZ_MINOR_SPESE  ,
      FLAG_FINANZ_DECURTAZ  ,
      FLAG_GARANZIA_REVOCA  ,
      FLAG_GARANZIA_MINOR_SPESE  ,
      FLAG_GARANZIA_DECURTAZ  ,
      IMP_CONTRIB_REVOCA_NO_RECU  ,
      IMP_CONTRIB_REVOCA_RECU  ,
      IMP_CONTRIB_INTERESSI  ,
      IMP_FINANZ_REVOCA_NO_RECU  ,
      IMP_FINANZ_REVOCA_RECU  ,
      IMP_FINANZ_INTERESSI  ,
      IMP_GARANZIA_REVOCA_NO_RECU  ,
      IMP_GARANZIA_REVOCA_RECUPERO,
      IMP_GARANZIA_INTERESSI  ,
      DT_INIZIO_VALIDITA  ,
      DT_FINE_VALIDITA  ,
      ID_UTENTE_INS  ,
      ID_UTENTE_AGG  ,
      DT_INSERIMENTO  ,
      DT_AGGIORNAMENTO
     )values
     (SEQ_PBANDI_T_GESTIONE_REVOCA.nextval,
      REC_GESTIONE_REVOCA.NUMERO_REVOCA,
      REC_GESTIONE_REVOCA.ID_TIPOLOGIA_REVOCA,
      vid_progetto_split,  --
      REC_GESTIONE_REVOCA.ID_CAUSALE_BLOCCO,
      REC_GESTIONE_REVOCA.ID_CATEG_ANAGRAFICA,
      REC_GESTIONE_REVOCA.DT_GESTIONE,
      REC_GESTIONE_REVOCA.NUM_PROTOCOLLO,
      DECODE(REC_GESTIONE_REVOCA.FLAG_ORDINE_RECUPERO,'N',NULL,REC_GESTIONE_REVOCA.FLAG_ORDINE_RECUPERO),
      REC_GESTIONE_REVOCA.ID_MANCATO_RECUPERO,
      REC_GESTIONE_REVOCA.ID_STATO_REVOCA,
      REC_GESTIONE_REVOCA.DT_STATO_REVOCA,
      REC_GESTIONE_REVOCA.DT_NOTIFICA,
      REC_GESTIONE_REVOCA.GG_RISPOSTA,
      DECODE(REC_GESTIONE_REVOCA.FLAG_PROROGA,'N',NULL,REC_GESTIONE_REVOCA.FLAG_PROROGA),
      REC_GESTIONE_REVOCA.IMP_DA_REVOCARE_CONTRIB,
      REC_GESTIONE_REVOCA.IMP_DA_REVOCARE_FINANZ,
      REC_GESTIONE_REVOCA.IMP_DA_REVOCARE_GARANZIA,
      DECODE(REC_GESTIONE_REVOCA.FLAG_DETERMINA,'N',NULL,REC_GESTIONE_REVOCA.FLAG_DETERMINA),
      REC_GESTIONE_REVOCA.ESTREMI  ,
      REC_GESTIONE_REVOCA.DT_DETERMINA,
      REC_GESTIONE_REVOCA.NOTE  ,
      REC_GESTIONE_REVOCA.ID_ATTIVITA_REVOCA  ,
      REC_GESTIONE_REVOCA.DT_ATTIVITA  ,
      REC_GESTIONE_REVOCA.ID_MOTIVO_REVOCA,
      DECODE(REC_GESTIONE_REVOCA.FLAG_CONTRIB_REVOCA,'N',NULL,REC_GESTIONE_REVOCA.FLAG_CONTRIB_REVOCA),
      DECODE(REC_GESTIONE_REVOCA.FLAG_CONTRIB_MINOR_SPESE,'N',NULL,REC_GESTIONE_REVOCA.FLAG_CONTRIB_MINOR_SPESE),
      DECODE(REC_GESTIONE_REVOCA.FLAG_CONTRIB_DECURTAZ,'N',NULL,REC_GESTIONE_REVOCA.FLAG_CONTRIB_DECURTAZ),
      DECODE(REC_GESTIONE_REVOCA.FLAG_FINANZ_REVOCA,'N',NULL,REC_GESTIONE_REVOCA.FLAG_FINANZ_REVOCA),
      DECODE(REC_GESTIONE_REVOCA.FLAG_FINANZ_MINOR_SPESE,'N',NULL,REC_GESTIONE_REVOCA.FLAG_FINANZ_MINOR_SPESE),
      DECODE(REC_GESTIONE_REVOCA.FLAG_FINANZ_DECURTAZ,'N',NULL,REC_GESTIONE_REVOCA.FLAG_FINANZ_DECURTAZ),
      DECODE(REC_GESTIONE_REVOCA.FLAG_GARANZIA_REVOCA,'N',NULL,REC_GESTIONE_REVOCA.FLAG_GARANZIA_REVOCA),
      DECODE(REC_GESTIONE_REVOCA.FLAG_GARANZIA_MINOR_SPESE,'N',NULL,REC_GESTIONE_REVOCA.FLAG_GARANZIA_MINOR_SPESE),
      DECODE(REC_GESTIONE_REVOCA.FLAG_GARANZIA_DECURTAZ,'N',NULL,REC_GESTIONE_REVOCA.FLAG_GARANZIA_DECURTAZ),
      REC_GESTIONE_REVOCA.IMP_CONTRIB_REVOCA_NO_RECU  ,
      REC_GESTIONE_REVOCA.IMP_CONTRIB_REVOCA_RECU  ,
      REC_GESTIONE_REVOCA.IMP_CONTRIB_INTERESSI  ,
      REC_GESTIONE_REVOCA.IMP_FINANZ_REVOCA_NO_RECU  ,
      REC_GESTIONE_REVOCA.IMP_FINANZ_REVOCA_RECU  ,
      REC_GESTIONE_REVOCA.IMP_FINANZ_INTERESSI  ,
      REC_GESTIONE_REVOCA.IMP_GARANZIA_REVOCA_NO_RECU  ,
      REC_GESTIONE_REVOCA.IMP_GARANZIA_REVOCA_RECUPERO,
      REC_GESTIONE_REVOCA.IMP_GARANZIA_INTERESSI  ,
      REC_GESTIONE_REVOCA.DT_INIZIO_VALIDITA  ,
      REC_GESTIONE_REVOCA.DT_FINE_VALIDITA  ,
      -14,
      NULL,
      REC_GESTIONE_REVOCA.DT_INSERIMENTO  ,
      NULL
     ) RETURNING ID_GESTIONE_REVOCA INTO vID_GESTIONE_REVOCA_PBAN;

       Update mfinpis_t_gestione_revoca
       Set    data_caricamento = sysdate,
              ID_GESTIONE_REVOCA_PBAN = vID_GESTIONE_REVOCA_PBAN
       Where  ID_GESTIONE_REVOCA = REC_GESTIONE_REVOCA.ID_GESTIONE_REVOCA;     
      
      
      ------------------------------------
      -- t_controdeduz
      ------------------------------------

     For rec_t_controdeduz IN
    (Select * from mfinpis_t_controdeduz
      WHERE data_caricamento is null and
      id_target = REC_GESTIONE_REVOCA.ID_GESTIONE_REVOCA
     )
        LOOP

        insert into PBANDI_T_CONTRODEDUZ
           (ID_CONTRODEDUZ,
              NUMERO_CONTRODEDUZ,
              ID_ENTITA  ,
              ID_TARGET  ,
              ID_STATO_CONTRODEDUZ,
              DT_STATO_CONTRODEDUZ,
              ID_ATTIV_CONTRODEDUZ,
              DT_ATTIV_CONTRODEDUZ,
              DT_INIZIO_VALIDITA,
              DT_FINE_VALIDITA,
              ID_UTENTE_INS,
              ID_UTENTE_AGG,
              DT_INSERIMENTO,
              DT_AGGIORNAMENTO
        )
        values
        (SEQ_PBANDI_T_CONTRODEDUZ.NEXTVAL,
              rec_t_controdeduz.NUMERO_CONTRODEDUZ,
              rec_t_controdeduz.ID_ENTITA  ,
              vID_GESTIONE_REVOCA_PBAN, --rec_t_controdeduz.ID_TARGET  ,
              rec_t_controdeduz.ID_STATO_CONTRODEDUZ,
              rec_t_controdeduz.DT_STATO_CONTRODEDUZ,
              rec_t_controdeduz.ID_ATTIV_CONTRODEDUZ,
              rec_t_controdeduz.DT_ATTIV_CONTRODEDUZ,
              SYSDATE,
              NULL,
              -14,
              NULL,
              SYSDATE,
              NULL
        )RETURNING ID_CONTRODEDUZ INTO vID_CONTRODEDUZ_PBAN;

           Update mfinpis_t_CONTRODEDUZ
             Set    data_caricamento = sysdate,
                    ID_CONTRODEDUZ_PBAN = vID_CONTRODEDUZ_PBAN
             Where  ID_CONTRODEDUZ = rec_t_controdeduz.ID_CONTRODEDUZ;

        End loop;
       ------------------
        -----------------------
        -- T_RICHIESTA_INTEGRAZ
        ------------------------
        For rec_t_richiesta_integraz IN
            (Select * from mfinpis_t_richiesta_integraz
              WHERE data_caricamento is null
              and id_target = REC_GESTIONE_REVOCA.ID_GESTIONE_REVOCA
             )
          LOOP

          insert into PBANDI_T_RICHIESTA_INTEGRAZ
             (ID_RICHIESTA_INTEGRAZ,
                ID_ENTITA,
                ID_TARGET,
                DT_RICHIESTA,
                DT_INVIO,
                ID_UTENTE_RICHIESTA,
                ID_UTENTE_INVIO,
                DT_NOTIFICA,
                NUM_GIORNI_SCADENZA,
                DT_CHIUSURA_UFFICIO,
                ID_STATO_RICHIESTA,
                DT_SCADENZA,
                DT_INIZIO_VALIDITA,
                DT_FINE_VALIDITA,
                ID_UTENTE_INS,
                ID_UTENTE_AGG,
                DT_INSERIMENTO,
                DT_AGGIORNAMENTO
          )
          values
          ( Seq_Pbandi_t_Rich_Integraz.Nextval,
                rec_t_richiesta_integraz.ID_ENTITA,
                vID_GESTIONE_REVOCA_PBAN,--rec_t_richiesta_integraz.ID_TARGET,
                rec_t_richiesta_integraz.DT_RICHIESTA,
                rec_t_richiesta_integraz.DT_INVIO,
                rec_t_richiesta_integraz.ID_UTENTE_RICHIESTA,
                rec_t_richiesta_integraz.ID_UTENTE_INVIO,
                rec_t_richiesta_integraz.DT_NOTIFICA,
                rec_t_richiesta_integraz.NUM_GIORNI_SCADENZA,
                rec_t_richiesta_integraz.DT_CHIUSURA_UFFICIO,
                rec_t_richiesta_integraz.ID_STATO_RICHIESTA,
                rec_t_richiesta_integraz.DT_SCADENZA,
                rec_t_richiesta_integraz.DT_INIZIO_VALIDITA,
                rec_t_richiesta_integraz.DT_FINE_VALIDITA,
                -14,
                NULL,
                rec_t_richiesta_integraz.DT_INSERIMENTO,
                NULL
          )RETURNING ID_RICHIESTA_INTEGRAZ INTO vID_RICHIESTA_INTEGRAZ_PBAN;

             Update Mfinpis_t_Richiesta_Integraz
               Set    data_caricamento = sysdate,
                      ID_RICHIESTA_INTEGRAZ_PBAN = vID_RICHIESTA_INTEGRAZ_PBAN
               Where  ID_RICHIESTA_INTEGRAZ = rec_t_richiesta_integraz.id_richiesta_integraz;

               ----------------------

                -----------------
                -- T_PROROGA
                -----------------

              For rec_t_proroga IN
                  (Select * from mfinpis_t_proroga
                    WHERE data_caricamento is null
                    and id_target = rec_t_richiesta_integraz.id_richiesta_integraz
                   )
                LOOP

                insert into PBANDI_T_PROROGA
                (ID_RICHIESTA_PROROGA,
                  ID_ENTITA,
                  ID_TARGET,
                  DT_RICHIESTA,
                  NUM_GIORNI_RICH  ,
                  MOTIVAZIONE  ,
                  NUM_GIORNI_APPROV  ,
                  ID_STATO_PROROGA  ,
                  DT_ESITO_RICHIESTA,
                  ID_UTENTE_INS  ,
                  ID_UTENTE_AGG  ,
                  DT_INSERIMENTO  ,
                  DT_AGGIORNAMENTO
                )
                values
                ( seq_pbandi_t_proroga.nextval,
                  rec_t_proroga.ID_ENTITA,
                  vID_RICHIESTA_INTEGRAZ_PBAN,
                  rec_t_proroga.DT_RICHIESTA,
                  rec_t_proroga.NUM_GIORNI_RICH  ,
                  rec_t_proroga.MOTIVAZIONE  ,
                  rec_t_proroga.NUM_GIORNI_APPROV  ,
                  rec_t_proroga.ID_STATO_PROROGA  ,
                  rec_t_proroga.DT_ESITO_RICHIESTA,
                  -14,
                  NULL,
                  rec_t_proroga.DT_INSERIMENTO  ,
                  NULL
                )RETURNING ID_RICHIESTA_PROROGA INTO vID_RICHIESTA_PROROGA_PBAN;

                   Update Mfinpis_t_proroga
                     Set    data_caricamento = sysdate,
                            ID_RICHIESTA_PROROGA_PBAN = vID_RICHIESTA_PROROGA_PBAN
                     Where  ID_RICHIESTA_PROROGA = rec_t_proroga.ID_RICHIESTA_PROROGA;

                End loop;
               ----------------------

          End loop;
       -------------------

       ------------------
       -- T_REVOCA
       ------------------

             For Rec_T_REVOCHE IN
              (Select * from MFINPIS_T_REVOCA
                WHERE data_caricamento is null
                and id_revoca = REC_GESTIONE_REVOCA.NUMERO_REVOCA
              )
             LOOP

                INSERT INTO PBANDI_T_REVOCA
                 (ID_REVOCA,
                  IMPORTO,
                  DT_REVOCA,
                  ID_PROGETTO,
                  ID_UTENTE_INS,
                  ID_UTENTE_AGG,
                  ESTREMI,
                  ID_MOTIVO_REVOCA,
                  NOTE,
                  ID_MODALITA_AGEVOLAZIONE,
                  DT_INSERIMENTO,
                  DT_AGGIORNAMENTO,
                  INTERESSI_REVOCA,
                  ID_CAUSALE_DISIMPEGNO,
                  ID_PERIODO,
                  FLAG_ORDINE_RECUPERO,
                  ID_MANCATO_RECUPERO,
                  ID_GESTIONE_REVOCA
                 )VALUES
                 (SEQ_PBANDI_T_REVOCA.Nextval,
                  REC_T_REVOCHE.IMPORTO,
                  REC_T_REVOCHE.DT_REVOCA,
                  vid_progetto_split,  --,
                  pIdUtenteIns, --REC_T_REVOCHE.ID_UTENTE_INS,
                  REC_T_REVOCHE.ID_UTENTE_AGG,
                  REC_T_REVOCHE.ESTREMI,
                  REC_T_REVOCHE.ID_MOTIVO_REVOCA,
                  REC_T_REVOCHE.NOTE,
                  REC_T_REVOCHE.ID_MODALITA_AGEVOLAZIONE,
                  SYSDATE, --REC_T_REVOCHE.DT_INSERIMENTO,
                  REC_T_REVOCHE.DT_AGGIORNAMENTO,
                  REC_T_REVOCHE.INTERESSI_REVOCA,
                  REC_T_REVOCHE.ID_CAUSALE_DISIMPEGNO,
                  REC_T_REVOCHE.ID_PERIODO,
                  REC_T_REVOCHE.FLAG_ORDINE_RECUPERO,
                  REC_T_REVOCHE.ID_MANCATO_RECUPERO,
                  vID_GESTIONE_REVOCA_PBAN --
                 )returning ID_REVOCA into vID_REVOCA_PBAN;

                 Update MFINPIS_T_REVOCA
                   Set    data_caricamento = sysdate,
                          ID_REVOCA_PBAN = vID_REVOCA_PBAN,
                          ID_PROGETTO_PBAN = vID_PROGETTO
                   Where  ID_REVOCA = Rec_T_REVOCHE.ID_REVOCA;

                -- mc 01092023 
                -- il numero revoca deve rimanere quello originario di finpis
                
                -- Update PBANDI_T_GESTIONE_REVOCA
                -- SET    NUMERO_REVOCA = vID_REVOCA_PBAN
                -- WHERE  ID_GESTIONE_REVOCA  = vID_GESTIONE_REVOCA_PBAN;
                
          END LOOP;


     END IF;

     End Loop;
-------------------------
vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  MIGRAZIONE FINPIS - FLUSSO REVOCHE riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;
------------------------------
---- FINE FLUSSO REVOCHE
------------------------------


begin
  
        For Rec_T_NOTE IN
              (Select * from MFINPIS_T_NOTE
                WHERE data_caricamento is null)
         LOOP

          -- CALCOLO NDG
          -- conteggio inserito per evitare di trascinare ndg precedente in caso di valore null
         select count(*)
          into vcount_ndg
          from   pbandi_t_soggetto
          where  NDG = lpad(rec_t_note.id_soggetto_beneficiario,9,'0');

          if vcount_ndg = 0 then vId_Soggetto_NDG:= null; end if;
         --
        vcount := 0;

        For rec in
         (select id_soggetto
          from   pbandi_t_soggetto
          where  NDG = lpad(rec_t_note.id_soggetto_beneficiario,9,'0')
          order by id_tipo_soggetto desc
          )
          LOOP
            if vcount = 0 then
              vId_Soggetto_NDG:=rec.id_soggetto;
              vcount := 1;
          end if;
          END LOOP;
    --
    --
 IF  rec_t_note.id_progetto IS NOT NULL THEN
      --
 if length(rec_t_note.id_progetto) = 10 then

    vcount :=0;

    For Rec_DomandeSPLIT in
         (Select id_progetto
         from pbandi_t_progetto
         where codice_visualizzato LIKE Rec_T_NOTE.id_progetto||'%'
         )Loop

          INSERT INTO PBANDI_T_NOTE
           (ID_NOTA,
            ID_SOGGETTO_BENEFICIARIO,
            ID_PROGETTO,
            TESTO_NOTA,
            ID_MODALITA_AGEVOLAZIONE,
            FLAG_VISIBILITA_BENEFICIARIO,
            DT_INIZIO_VALIDITA,
            DT_FINE_VALIDITA,
            ID_UTENTE_INS,
            ID_UTENTE_AGG
           )VALUES
           (seq_pbandi_t_note.nextval,
            vId_Soggetto_NDG,
            Rec_DomandeSPLIT.Id_Progetto,
            rec_t_note.testo_nota,
            decode(rec_t_note.id_modalita_agevolazione,'C',1,'F',15,'G',16,NULL),
            'S',--rec_t_note.flag_visibilita_beneficiario,
            rec_t_note.dt_inizio_validita,
            rec_t_note.dt_fine_validita,
            -14,
            null
           ) RETURNING ID_NOTA into vID_NOTA_PBAN;

             Update MFINPIS_T_NOTE
               Set    data_caricamento = sysdate,
                      ID_NOTA_PBAN = vID_NOTA_PBAN
               Where  ID_NOTA = rec_t_note.id_nota;


          End loop;


 else

   vdummy:=  rec_t_note.id_progetto;
     Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_note.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato = substr(rec_t_note.Id_Progetto,1,10);
          vcount := 1;
         --
      Exception when no_data_found then
        begin
       Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_note.Id_Progetto,1,11)||'%';
          vcount := 1;
          Exception when no_data_found then
          vcount := 0;
         end;
      end;
   End;
   ----
If vcount <> 0 then


           INSERT INTO PBANDI_T_NOTE
           (ID_NOTA,
            ID_SOGGETTO_BENEFICIARIO,
            ID_PROGETTO,
            TESTO_NOTA,
            ID_MODALITA_AGEVOLAZIONE,
            FLAG_VISIBILITA_BENEFICIARIO,
            DT_INIZIO_VALIDITA,
            DT_FINE_VALIDITA,
            ID_UTENTE_INS,
            ID_UTENTE_AGG
           )VALUES
           (seq_pbandi_t_note.nextval,
            vId_Soggetto_NDG,
            vid_progetto_split,
            rec_t_note.testo_nota,
            decode(rec_t_note.id_modalita_agevolazione,'C',1,'F',15,'G',16,NULL),
            'S',--rec_t_note.flag_visibilita_beneficiario,
            rec_t_note.dt_inizio_validita,
            rec_t_note.dt_fine_validita,
            -14,
            null
           )RETURNING ID_NOTA into vID_NOTA_PBAN;

             Update MFINPIS_T_NOTE
               Set    data_caricamento = sysdate,
                      ID_NOTA_PBAN = vID_NOTA_PBAN
               Where  ID_NOTA = rec_t_note.id_nota;
end if;
end if;
       --
       ELSE

           INSERT INTO PBANDI_T_NOTE
           (ID_NOTA,
            ID_SOGGETTO_BENEFICIARIO,
            ID_PROGETTO,
            TESTO_NOTA,
            ID_MODALITA_AGEVOLAZIONE,
            FLAG_VISIBILITA_BENEFICIARIO,
            DT_INIZIO_VALIDITA,
            DT_FINE_VALIDITA,
            ID_UTENTE_INS,
            ID_UTENTE_AGG
           )VALUES
           (seq_pbandi_t_note.nextval,
            vId_Soggetto_NDG,
            NULL,
            rec_t_note.testo_nota,
            decode(rec_t_note.id_modalita_agevolazione,'C',1,'F',15,'G',16,NULL),
            'S',--rec_t_note.flag_visibilita_beneficiario,
            rec_t_note.dt_inizio_validita,
            rec_t_note.dt_fine_validita,
            -14,
            null
           )RETURNING ID_NOTA into vID_NOTA_PBAN;

             Update MFINPIS_T_NOTE
               Set    data_caricamento = sysdate,
                      ID_NOTA_PBAN = vID_NOTA_PBAN
               Where  ID_NOTA = rec_t_note.id_nota;

       END IF;

END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  PBANDI_T_NOTE riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;
-------------------------------------------
-------------------------------------------


begin
-- PBANDI_T_LIBERAZ_GARANTE

          For Rec_t_liberaz_garante IN
              (Select * from MFINPIS_T_LIBERAZ_GARANTE
                WHERE data_caricamento is null
              )
             LOOP

   Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_t_liberaz_garante.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_t_liberaz_garante.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

              INSERT INTO PBANDI_T_LIBERAZ_GARANTE
              (ID_LIBERAZ_GARANTE,
                ID_PROGETTO,
                ID_MODALITA_AGEVOLAZIONE,
                DT_LIBERAZ_GARANTE,
                IMP_LIBERAZIONE,
                DENOM_GARANTE_LIBERATO,
                NOTE,
                DT_INIZIO_VALIDITA,
                DT_FINE_VALIDITA,
                ID_UTENTE_INS,
                ID_UTENTE_AGG,
                DT_INSERIMENTO,
                DT_AGGIORNAMENTO
              )VALUES
              (SEQ_PBANDI_T_LIBERAZ_GARANTE.NEXTVAL,
                vid_progetto_split,
                Rec_t_liberaz_garante.ID_MODALITA_AGEVOLAZIONE,
                Rec_t_liberaz_garante.DT_LIBERAZ_GARANTE,
                Rec_t_liberaz_garante.IMP_LIBERAZIONE,
                Rec_t_liberaz_garante.DENOM_GARANTE_LIBERATO,
                Rec_t_liberaz_garante.NOTE,
                Rec_t_liberaz_garante.DT_INIZIO_VALIDITA,
                Rec_t_liberaz_garante.DT_FINE_VALIDITA,
                Rec_t_liberaz_garante.ID_UTENTE_INS,
                Rec_t_liberaz_garante.ID_UTENTE_AGG,
                Rec_t_liberaz_garante.DT_INSERIMENTO,
                Rec_t_liberaz_garante.DT_AGGIORNAMENTO
               ) RETURNING ID_LIBERAZ_GARANTE INTO vID_LIBERAZ_GARANTE;

               Update mfinpis_t_liberaz_garante
               Set    data_caricamento = sysdate,
                      ID_LIBERAZ_GARANTE_pban = vID_LIBERAZ_GARANTE
               Where  ID_LIBERAZ_GARANTE = Rec_t_liberaz_garante.ID_LIBERAZ_GARANTE;

End if;

        END LOOP;
vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  PBANDI_T_LIBERAZ_GARANTE riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;




begin
-- PBANDI_T_MESSA_IN_MORA
    For Rec_t_messa_in_mora IN
              (Select * from MFINPIS_T_messa_in_mora
                WHERE data_caricamento is null
              )
             LOOP

 Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_t_messa_in_mora.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_t_messa_in_mora.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then

              INSERT INTO PBANDI_T_messa_in_mora
              (ID_MESSA_IN_MORA,
                ID_PROGETTO,
                ID_MODALITA_AGEVOLAZIONE,
                ID_ATTIVITA_MORA,
                DT_MESSA_IN_MORA,
                IMP_MESSA_IN_MORA_COMPLESSIVA,
                IMP_QUOTA_CAPITALE_REVOCA,
                IMP_AGEVOLAZ_REVOCA,
                IMP_CREDITO_RESIDUO,
                IMP_INTERESSI_MORA,
                DT_NOTIFICA,
                ID_ATTIVITA_RECUPERO_MORA,
                DT_PAGAMENTO,
                NOTE,
                DT_INIZIO_VALIDITA,
                DT_FINE_VALIDITA,
                ID_UTENTE_INS,
                ID_UTENTE_AGG,
                DT_INSERIMENTO,
                DT_AGGIORNAMENTO
              )VALUES
              (SEQ_PBANDI_T_MESSA_IN_MORA.NEXTVAL,
                vid_progetto_split,
                Rec_t_messa_in_mora.ID_MODALITA_AGEVOLAZIONE,
                Rec_t_messa_in_mora.ID_ATTIVITA_MORA,
                Rec_t_messa_in_mora.DT_MESSA_IN_MORA,
                Rec_t_messa_in_mora.IMP_MESSA_IN_MORA_COMPLESSIVA,
                Rec_t_messa_in_mora.IMP_QUOTA_CAPITALE_REVOCA,
                Rec_t_messa_in_mora.IMP_AGEVOLAZ_REVOCA,
                Rec_t_messa_in_mora.IMP_CREDITO_RESIDUO,
                Rec_t_messa_in_mora.IMP_INTERESSI_MORA,
                Rec_t_messa_in_mora.DT_NOTIFICA,
                Rec_t_messa_in_mora.ID_ATTIVITA_RECUPERO_MORA,
                Rec_t_messa_in_mora.DT_PAGAMENTO,
                Rec_t_messa_in_mora.NOTE,
                Rec_t_messa_in_mora.DT_INIZIO_VALIDITA,
                Rec_t_messa_in_mora.DT_FINE_VALIDITA,
                Rec_t_messa_in_mora.ID_UTENTE_INS,
                Rec_t_messa_in_mora.ID_UTENTE_AGG,
                Rec_t_messa_in_mora.DT_INSERIMENTO,
                Rec_t_messa_in_mora.DT_AGGIORNAMENTO
               ) RETURNING ID_MESSA_IN_MORA INTO vID_MESSA_IN_MORA;

               Update mfinpis_t_messa_in_mora
               Set    data_caricamento = sysdate,
                      ID_MESSA_IN_MORA_pban = vID_MESSA_IN_MORA
               Where  ID_MESSA_IN_MORA = Rec_t_messa_in_mora.ID_MESSA_IN_MORA;

End if;

        END LOOP;

vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  PBANDI_T_messa_in_mora riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;


begin
-- PBANDI_t_segnalaz_corte_conti
    For Rec_t_segnalaz_corte_conti  IN
              (Select * from MFINPIS_t_segnalaz_corte_conti
                WHERE data_caricamento is null
              )
             LOOP
Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = Rec_t_segnalaz_corte_conti.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(Rec_t_segnalaz_corte_conti.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then
  -- da testare id_modalita_agevolazione
   begin
    
    SELECT a.id_modalita_agevolazione
      INTO  v_id_modalita_agevolazione
      FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a
      JOIN PBANDI_T_CONTO_ECONOMICO b on b.id_conto_economico = a.id_conto_economico
      JOIN PBANDI_T_DOMANDA c on c.id_domanda = b.id_domanda
      JOIN PBANDI_T_PROGETTO d on d.id_domanda = c.id_domanda
      JOIN PBANDI_D_MODALITA_AGEVOLAZIONE e on e.id_modalita_agevolazione = a.id_modalita_agevolazione
     WHERE d.id_progetto = vid_progetto_split
      AND e.id_modalita_agevolazione_rif = Rec_t_segnalaz_corte_conti.ID_MODALITA_AGEVOLAZIONE
      AND b.dt_fine_validita IS NULL;  
    Exception 
      when no_data_found then
         v_id_modalita_agevolazione:= Rec_t_segnalaz_corte_conti.ID_MODALITA_AGEVOLAZIONE;
         dbms_output.put_line ('per il progetto '|| vid_progetto_split ||'  modalita di agevolazione NON PREVISTA');
         
    end;
  



              INSERT INTO PBANDI_t_segnalaz_corte_conti
              (ID_SEGNALAZIONE_CORTE_CONTI,
                ID_PROGETTO,
                ID_MODALITA_AGEVOLAZIONE,
                DT_SEGNALAZIONE,
                NUM_PROTOCOLLO_SEGN,
                IMP_CRED_RES_CAPITALE,
                IMP_ONERI_AGEVOLAZ,
                IMP_QUOTA_SEGNALAZ,
                IMP_GARANZIA,
                FLAG_PIANO_RIENTRO,
                DT_PIANO_RIENTRO,
                FLAG_SALDO_STRALCIO,
                DT_SALDO_STRALCIO,
                FLAG_PAGAM_INTEGRALE,
                DT_PAGAMENTO,
                FLAG_DISSEGNALAZIONE,
                DT_DISSEGNALAZIONE,
                NUM_PROTOCOLLO_DISS,
                FLAG_DECRETO_ARCHIV,
                DT_ARCHIV,
                NUM_PROTOCOLLO_ARCHIV,
                FLAG_COMUNICAZ_REGIONE,
                NOTE,
                DT_INIZIO_VALIDITA,
                DT_FINE_VALIDITA,
                ID_UTENTE_INS,
                ID_UTENTE_AGG,
                DT_INSERIMENTO,
                DT_AGGIORNAMENTO
              )VALUES
              (SEQ_PBANDI_T_SEGN_CORTE_CONTI.nextval,
                vid_progetto_split,
                v_id_modalita_agevolazione, --Rec_t_segnalaz_corte_conti.ID_MODALITA_AGEVOLAZIONE,
                Rec_t_segnalaz_corte_conti.DT_SEGNALAZIONE,
                Rec_t_segnalaz_corte_conti.NUM_PROTOCOLLO_SEGN,
                Rec_t_segnalaz_corte_conti.IMP_CRED_RES_CAPITALE,
                Rec_t_segnalaz_corte_conti.IMP_ONERI_AGEVOLAZ,
                Rec_t_segnalaz_corte_conti.IMP_QUOTA_SEGNALAZ,
                Rec_t_segnalaz_corte_conti.IMP_GARANZIA,
                Rec_t_segnalaz_corte_conti.FLAG_PIANO_RIENTRO,
                Rec_t_segnalaz_corte_conti.DT_PIANO_RIENTRO,
                Rec_t_segnalaz_corte_conti.FLAG_SALDO_STRALCIO,
                Rec_t_segnalaz_corte_conti.DT_SALDO_STRALCIO,
                Rec_t_segnalaz_corte_conti.FLAG_PAGAM_INTEGRALE,
                Rec_t_segnalaz_corte_conti.DT_PAGAMENTO,
                Rec_t_segnalaz_corte_conti.FLAG_DISSEGNALAZIONE,
                Rec_t_segnalaz_corte_conti.DT_DISSEGNALAZIONE,
                Rec_t_segnalaz_corte_conti.NUM_PROTOCOLLO_DISS,
                Rec_t_segnalaz_corte_conti.FLAG_DECRETO_ARCHIV,
                Rec_t_segnalaz_corte_conti.DT_ARCHIV,
                Rec_t_segnalaz_corte_conti.NUM_PROTOCOLLO_ARCHIV,
                Rec_t_segnalaz_corte_conti.FLAG_COMUNICAZ_REGIONE,
                Rec_t_segnalaz_corte_conti.NOTE,
                Rec_t_segnalaz_corte_conti.DT_INIZIO_VALIDITA,
                Rec_t_segnalaz_corte_conti.DT_FINE_VALIDITA,
                -14,--Rec_t_segnalaz_corte_conti.ID_UTENTE_INS,
                Rec_t_segnalaz_corte_conti.ID_UTENTE_AGG,
                Rec_t_segnalaz_corte_conti.DT_INSERIMENTO,
                Rec_t_segnalaz_corte_conti.DT_AGGIORNAMENTO
               ) RETURNING ID_SEGNALAZIONE_CORTE_CONTI INTO vID_SEGNALAZIONE_CORTE_CONTI;

               Update MFINPIS_t_segnalaz_corte_conti
               Set    data_caricamento = sysdate,
                      ID_SEGNALAZIONE_CORTE_PBAN = vID_SEGNALAZIONE_CORTE_CONTI
               Where  ID_SEGNALAZIONE_CORTE_CONTI = Rec_t_segnalaz_corte_conti.ID_SEGNALAZIONE_CORTE_CONTI;

End if;

        END LOOP;
        
vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  PBANDI_t_segnalaz_corte_conti riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



begin
-- RIASSICURAZIONI  

For rec_t_riassicurazioni  IN
  (Select * from mfinpis_t_riassicurazioni
    WHERE data_caricamento is null
  )
 LOOP
Begin
     Select id_progetto
     Into   vid_progetto_split
      From pbandi_t_progetto
      Where codice_visualizzato = rec_t_riassicurazioni.Id_Progetto;
      vcount := 1;
   Exception When no_data_found then
     begin
        --
         Select id_progetto
         Into   vid_progetto_split
          From pbandi_t_progetto
          Where codice_visualizzato like substr(rec_t_riassicurazioni.Id_Progetto,1,10)||'%';
          vcount := 1;
         --
      Exception when no_data_found then
        vcount := 0;
      end;
   End;
   ----
If vcount <> 0 then


              INSERT INTO pbandi_t_riassicurazioni
              (ID_RIASSICURAZIONE,
                LINEA_INTERVENTO_SOGGETTO,
                RAGIONE_SOCIALE,
                FORMA_GIURIDICA,
                DESCR_FORMA_GIURIDICA,
                CODICE_FISCALE,
                INDIRIZZO_SEDE_DESTINATARIA,
                LOCALITA_SEDE_DESTINATARIA,
                PROVINCIA_SEDE_DESTINATARIA,
                ATECO,
                DESCRIZIONE_ATECO,
                IMPORTO_FINANZIATO,
                ID_PROGETTO,
                IMPORTO_GARANZIA,
                IMPORTO_AMMESSO,
                PERCENTUALE_GARANZIA,
                PERCENTUALE_RIASSICURATO,
                DT_EROGAZIONE_MUTUO,
                DT_DELIBERA_MUTUO,
                DT_EMISSIONE_GARANZIA,
                DT_SCADENZA_MUTUO,
                IMPORTO_ESCUSSO,
                DATA_ESCUSSIONE,
                DATA_SCARICO,
                DATA_REVOCA,
                DATA_INIZIO,
                DATA_FINE,
                ID_UTENTE_INS,
                ID_UTENTE_AGG
              )VALUES
              (seq_pbandi_t_riassicurazioni.nextval,
                Rec_T_RIASSICURAZIONI.linea_intervento_soggetto,
                Rec_T_RIASSICURAZIONI.ragione_sociale,
                Rec_T_RIASSICURAZIONI.forma_giuridica,
                Rec_T_RIASSICURAZIONI.descr_forma_giuridica,
                Rec_T_RIASSICURAZIONI.codice_fiscale,
                Rec_T_RIASSICURAZIONI.indirizzo_sede_destinataria,
                Rec_T_RIASSICURAZIONI.localita_sede_destinataria,
                Rec_T_RIASSICURAZIONI.provincia_sede_destinataria,
                Rec_T_RIASSICURAZIONI.ateco,
                Rec_T_RIASSICURAZIONI.descrizione_ateco,
                Rec_T_RIASSICURAZIONI.importo_finanziato,
                vid_progetto,
                Rec_T_RIASSICURAZIONI.importo_garanzia,
                Rec_T_RIASSICURAZIONI.importo_ammesso,
                Rec_T_RIASSICURAZIONI.percentuale_garanzia,
                Rec_T_RIASSICURAZIONI.percentuale_riassicurato,
                Rec_T_RIASSICURAZIONI.erogazione_mutuo,
                Rec_T_RIASSICURAZIONI.delibera_mutuo,
                Rec_T_RIASSICURAZIONI.emissione_garanzia,
                Rec_T_RIASSICURAZIONI.scadenza_mutuo,
                Rec_T_RIASSICURAZIONI.importo_escusso,
                Rec_T_RIASSICURAZIONI.data_escussione,
                Rec_T_RIASSICURAZIONI.data_scarico,
                Rec_T_RIASSICURAZIONI.data_revoca,
                sysdate,
                null,
                -14,
                null
           ) RETURNING ID_RIASSICURAZIONE INTO vID_RIASSICURAZIONE;

               Update MFINPIS_t_riassicurazioni
               Set    data_caricamento = sysdate,
                      ID_RIASSICURAZIONE_PBAN = vID_RIASSICURAZIONE
               Where  ID_RIASSICURAZIONE = Rec_T_RIASSICURAZIONI.ID_RIASSICURAZIONE;

End if;

        END LOOP;
        
vcount:=0;
COMMIT;
   
Exception when others then  
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E194', SQLERRM || ' ERRORE SU:  pbandi_t_riassicurazioni riga '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
end;



--------------------------------------------
 
 COMMIT;
 RETURN nRet;

END FNC_INSERT_PBANDI;

-----------
-----------
-- ELENCO DELLE PROCEDURE (ORDINATE) PER MIGRAZIONE
FUNCTION FNC_MAIN_MIGRAZIONE_FINPIS  RETURN NUMBER IS

  nRet  NUMBER:=0;

BEGIN

   -- prc load xml
   -- step   1

   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'BANDI_FONDI_2022-09-05-10.20.17.959879.xml',
                                                  p_tipofile => 0);--BANDI_FONDI
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'D_LINEA_DI_INTERVENTO_2022-08-29-11.02.51.303658.xml',
                                                  p_tipofile => 1);--D_LINEA_DI_INTERVENTO
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_BANDO_2022-08-29-11.02.15.069943.xml',
                                                  p_tipofile => 2);--T_BANDO
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_LINEA_INTERVENT_2022-08-29-11.05.37.666012.xml',
                                                  p_tipofile => 3);--R_BANDO_LINEA_INTERVENT
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_MOD_AG_ESTR_BAN_2022-08-29-11.02.39.095088.xml',
                                                  p_tipofile => 4);--R_BANDO_MOD_AG_ESTR_BAN
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_MODALITA_AGEVOL_2022-08-29-11.06.02.242404.xml',
                                                  p_tipofile => 5);--R_BANDO_MODALITA_AGEVOL
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_LINEA_ENTE_COMP_2022-08-29-11.02.59.587578.xml',
                                                  p_tipofile => 6);--R_BANDO_LINEA_ENTE_COMP
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_INDIRIZZO_2022-08-29-11.02.46.440210.xml',
                                                  p_tipofile => 7);--T_INDIRIZZI
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_AGENZIA_2022-08-29-11.02.19.878347.xml',
                                                  p_tipofile => 8);--T_AGENZIA
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_ESTREMI_BANCARI_2022-08-29-11.02.34.439970.xml',
                                                  p_tipofile => 9); --T_ESTREMI_BANCARI
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_SOGG_FINANZIAT_2022-08-29-11.06.04.044116.xml',
                                                  p_tipofile => 10); --R_BANDO_SOGG_FINANZIAT
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'D_VOCE_DI_SPESA_2022-08-29-11.06.13.657656.xml',
                                                  p_tipofile => 11); --D_VOCE_DI_SPESA
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_BANDO_VOCE_SPESA_2022-08-29-11.02.27.893371.xml',
                                                  p_tipofile => 12); --R_BANDO_VOCE_SPESA

   --
   -- da caricare alla fine

   /*
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 30); --T_NOTE_ANA
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 31); --T_NOTE_DOM
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 32); --T_RICHIESTA_DURC
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 33); --T_SOGGETTO_DURC
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 34); --T_RICHIESTA_ANTIMAFIA
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 35); --T_SOGGETTO_ANTIMAFIA
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 36); --T_REVOCHE
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => '.xml',
                                                  p_tipofile => 37); --T_ESCUSSIONE


  --------------------------------------------
  AREA CREDITI
  ---------------------------------------------

  pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'R_SOGG_PROG_STA_CRED_FP_2022-06-15-15.45.47.896985.xml',
                                                  p_tipofile => 50); --R_SOGG_PROG_STA_CRE_FP

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_AZIONE_RECUP_BANCA_2022-06-15-15.16.37.455623.xml',
                                                  p_tipofile => 51); --T_AZIONE_RECUP_BANCA

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_CESSIONE_QUOTA_FP_2022-06-15-15.15.34.847900.xml',
                                                  p_tipofile => 52); --T_CESSIONE_QUOTA_FP

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_ESCUSS_CONFIDI_2022-06-15-15.15.44.167923.xml',
                                                  p_tipofile => 53); --T_ESCUSS_CONFIDI

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_ISCRIZIONE_RUOLO_2022-06-15-15.15.58.172885.xml',
                                                  p_tipofile => 54); --T_ISCRIZIONE_RUOLO

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_PASSAGGIO_PERDITA_2022-06-15-15.16.00.222596.xml',
                                                  p_tipofile => 55); --T_PASSAGGIO_PERDITA

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_PIANO_RIENTRO_2022-06-15-15.16.02.657407.xml',
                                                  p_tipofile => 56); --T_PIANO_RIENTRO

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_REVOCA_BANCARIA_2022-06-15-15.16.52.292877.xml',
                                                  p_tipofile => 57); --T_REVOCA_BANCARIA

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_SALDO_STRALCIO_2022-06-15-15.16.55.268804.xml',
                                                  p_tipofile => 58); --T_SALDO_STRALCIO

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_SOGG_STATO_AZIENDA_2022-06-15-15.28.37.087147.xml',
                                                  p_tipofile => 59); --T_SOGG_STATO_AZIENDA

 pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_SOGGETTO_CLA_RISCHIO_2022-06-15-15.16.34.952506.xml',
                                                  p_tipofile => 60); --T_SOGGETTO_CLA_RISCHIO

  pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                 p_filename => 'T_SOGGETTO_RATING_2022-06-15-15.16.20.657230.xml',
                                                 p_tipofile => 61); --T_SOGGETTO_RATING



   */

   -------------------
   --  AMMORTAMENTO
   -------------------
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                 p_filename => 'AMMORTAMENTO_PROGETTI_2022-09-05-10.38.46.650809.xml',
                                                 p_tipofile => 70); --T_AMMORTAMENTO_PROGETTI


   ---------------------
   --  DATI AGGIUNTIVI
   ---------------------

   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                 p_filename => 'AGGIUNTIVI_RNA_AUTOMATICO_2022-10-06-19.56.27.753355.xml',
                                                 p_tipofile => 71); -- T_AGGIUNTIVI_RNA_AUTOMATICO

  -- CARICAMENTO SOSPESO
  -- pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
  --                                               p_filename => 'AGGIUNTIVI_RNA_MANUALE_2022-10-06-22.15.49.708929.xml',
  --                                               p_tipofile => 72); -- T_AGGIUNTIVI_RNA_MANUALE

  -----------------------------------------------------------------------------------------
   pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_SOGGETTO_DSAN_2022-11-21-08.59.26.858247.xml',
                                                  p_tipofile => 38); --T_SOGGETTO_DSAN
------------------------------------------------------------------------------------------

-------------
--- REVOCHE
--------------
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_CONTRODEDUZ_2022-12-30-18.46.19.583639.xml',
                                                  p_tipofile => 80); -- T_CONTRODEDUZ
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_GESTIONE_REVOCA_PROCEDIMENTI_2022-12-30-18.48.35.578949.xml',
                                                  p_tipofile => 81); -- T_GESTIONE_REVOCA_PROCEDIMENTI
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_GESTIONE_REVOCA_PROVVEDIMENTI_2022-12-30-18.52.39.924086.xml',
                                                  p_tipofile => 82); -- T_GESTIONE_REVOCA_PROCEDIMENTI
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_PROROGA_2022-12-30-18.49.03.803521.xml',
                                                  p_tipofile => 83); -- T_PROROGA
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_RICHIESTA_INTEGRAZ_2022-12-30-18.46.40.374489.xml',
                                                  p_tipofile => 84); -- T_RICHIESTA_INTEGRAZ
---------------

pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_LIBERAZ_GARANTE_2023-05-08-17.42.08.767089.xml',
                                                  p_tipofile => 90); -- T_LIBERAZ_GARANTE
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_MESSA_IN_MORA_2023-05-08-17.42.15.644374.xml',
                                                  p_tipofile => 91); -- T_MESSA_IN_MORA
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'T_SEGNALAZ_CORTE_CONTI_2023-05-08-17.41.54.335755.xml',
                                                  p_tipofile => 92); -- T_SEGNALAZ_CORTE_CONTI

---------------
pck_pbandi_migrazione_finpis.prc_load_xml_file(p_dir => 'PBANDI_RP_02_PBANDI_DB',
                                                  p_filename => 'RIASSICURAZIONI_2023-09-15-09.00.00.731095.xml',
                                                  p_tipofile => 93); -- T_RIASSICURAZIONI

---------------
   
   -- 2 step
   -- Lettura XML e insert sulle tabelle mFinpis
   /*
   nRet:= FNC_CARICA_XML_MFINPIS;
   */

   --
   --  3 step
   --   insert sulle tabelle pban  e assegna le nuove pk sull tabella
   --   di appoggio Mfinpis
   /*
   nRet:= FNC_INSERT_PBANDI;
   */


  RETURN nRet;
END FNC_MAIN_MIGRAZIONE_FINPIS;
-----------
-----------
END PCK_PBANDI_MIGRAZIONE_FINPIS; 
