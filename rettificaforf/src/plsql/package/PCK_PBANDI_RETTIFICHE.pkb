/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE BODY PCK_PBANDI_RETTIFICHE AS
/*******************************************************************************
    NAME:       PCK_PBANDI_RETTIFICHE
    PURPOSE:    Package Rettifiche forfettarie

   REVISIONS: 
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0    14/10/2019       MC             Created this package.
*******************************************************************************/

vNomeBatch                      PBANDI_D_NOME_BATCH.NOME_BATCH%TYPE := 'PBAN-RETTIFICHE';
nIdProcessoBatch                PBANDI_L_PROCESSO_BATCH.id_processo_batch%TYPE := PCK_PBANDI_UTILITY_BATCH.insprocbatch(vNomeBatch);
vRetVal                         NUMBER:=0;


FUNCTION Fnc_Verifica RETURN NUMBER AS

vSommaPerc NUMBER;
vIdPropostaCertificaz NUMBER;
vDtOraCreazione DATE;



  BEGIN
  
      For Rec_appalti IN
         -- 1 step si identificano gli appalti con rettifiche
        (select   rf.id_appalto, 
                  SUM(rf.perc_rett) as somma_perc
        FROM      pbandi_t_rett_forfett rf
        group by  rf.id_appalto)
        
        loop
          
       -- 2 step calcolo delle rettifiche forfettarie
       -- quota parte dei progetti che fanno parte dell'appalto   
          for Rec_CalcoloRettifiche IN
          (SELECT DISTINCT 
                 qp.progr_pag_quot_parte_doc_sp,
                 qp.id_proposta_certificaz,
                 tp.id_progetto,
                 qp.importo_validato
          FROM   pbandi_t_progetto tp,
                 pbandi_r_progetti_appalti rpapp,
                 pbandi_r_doc_spesa_progetto dsp,
                 pbandi_t_quota_parte_doc_spesa qpds, 
                 pbandi_r_pag_quot_parte_doc_sp qp,
                 pbandi_t_dichiarazione_spesa ds     
          WHERE  tp.id_progetto =  rpapp.id_progetto AND
                 rpapp.id_APPALTO = Rec_appalti.Id_Appalto AND
                 rpapp.id_appalto = dsp.id_appalto AND
                 dsp.id_documento_di_spesa = qpds.id_documento_di_spesa AND
                 qpds.ID_QUOTA_PARTE_DOC_SPESA = qp.ID_QUOTA_PARTE_DOC_SPESA AND      
                 qp.id_dichiarazione_spesa = ds.id_dichiarazione_spesa AND
                 ds.dt_chiusura_validazione IS NOT NULL  -- DICHIARAZIONE CHIUSE               
                 --IsFESR (tp.ID_PROGETTO) = 'POR-FESR-2014-2020' AND
                 --tp.id_tipo_operazione IN (1,2)
                 order by qp.progr_pag_quot_parte_doc_sp
            ) loop
                 
                 -- svuoto le variabili
                 vIdPropostaCertificaz := null;                        
                 vDtOraCreazione := null;
                 --
             
             IF rec_CalcoloRettifiche.id_proposta_certificaz IS NULL THEN
                ---- verifico e recupero i riferimenti della prima 
                ---- certificazione approvata per il progetto
                BEGIN
                   
                   FOR Rec_Certificaz IN
                    (select pc.id_proposta_certificaz,
                            trunc(pc.DT_ORA_CREAZIONE) as DT_ORA_CREAZIONE                                              
                    from    pbandi_t_proposta_certificaz pc,
                            pbandi_t_dett_proposta_certif dpc,
                            pbandi_r_det_prop_cer_qp_doc qpdoc
                    where   pc.id_proposta_certificaz = dpc.id_proposta_certificaz AND
                            dpc.id_dett_proposta_certif =  qpdoc.id_dett_proposta_certif AND
                            pc.id_stato_proposta_certif = 6 AND --APPROVATA 
                            --dpc.id_progetto =  rec_CalcoloRettifiche.id_progetto -- PROGETTO
                            qpdoc.progr_pag_quot_parte_doc_sp = rec_CalcoloRettifiche.Progr_Pag_Quot_Parte_Doc_Sp                     
                            ORDER BY pc.id_proposta_certificaz)
                     loop
                       
                        vIdPropostaCertificaz := Rec_Certificaz.id_proposta_certificaz;                        
                        vDtOraCreazione := Rec_Certificaz.DT_ORA_CREAZIONE;
                        EXIT;
                        
                     End Loop;
                 END;            
               ----
                  IF vIdPropostaCertificaz IS NOT NULL THEN
                      -- il progetto è entrato in certificazione
                      -- STOP al calcolo dell' IMPORTO VALIDATO RETTIFICATO
                  
                       UPDATE pbandi_r_pag_quot_parte_doc_sp
                       SET    id_proposta_certificaz = vIdPropostaCertificaz
                       WHERE  progr_pag_quot_parte_doc_sp = Rec_CalcoloRettifiche.progr_pag_quot_parte_doc_sp;                 
                       
                       UPDATE PBANDI_T_RETT_FORFETT
                       SET    id_proposta_certificaz = vIdPropostaCertificaz
                       WHERE  id_appalto = Rec_appalti.id_appalto AND
                              id_proposta_certificaz IS NULL AND
                              DATA_INSERIMENTO < vDtOraCreazione;
                  ELSE
                      
                      -- fino a quando il progetto non entra in certificazione
                      -- viene effettuato il calcolo importo rettificato
                       UPDATE pbandi_r_pag_quot_parte_doc_sp
                       SET    IMPORTO_VALIDATO_RETT = rec_CalcoloRettifiche.importo_validato -(rec_CalcoloRettifiche.importo_validato * (Rec_appalti.somma_perc /100))                       
                       WHERE  progr_pag_quot_parte_doc_sp = Rec_CalcoloRettifiche.progr_pag_quot_parte_doc_sp;   
                      --
                      
                  END IF;
             
             END IF;
          
            End Loop;
          
        End Loop;

    RETURN vRetVal;
    
EXCEPTION WHEN OTHERS THEN
    
    PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'KO');
    PCK_PBANDI_UTILITY_BATCH.inslogbatch(nIdProcessoBatch,'E173',SQLERRM|| 'ERRORE SU RETTIFICHE FORFETTARIE '||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    ROLLBACK;
    RAISE_APPLICATION_ERROR (-20000,' ERRORE SU RETTIFICHE FORFETTARIE');

END fnc_Verifica;
---------------------------------------------------------------------------
FUNCTION Fnc_MainRettifiche RETURN NUMBER AS
 --
BEGIN
   
     vRetVal := fnc_Verifica;
     
     IF vRetVal = 0 THEN   
   -- chiusura del processo sulla LOG_BATCH
      PCK_PBANDI_UTILITY_BATCH.UpdFineProcBatch(nIdProcessoBatch,'OK');
     END IF;
   COMMIT;
     
   return 0;
END  Fnc_MainRettifiche;
---------------------------------------------------------------------------
END PCK_PBANDI_RETTIFICHE;
/