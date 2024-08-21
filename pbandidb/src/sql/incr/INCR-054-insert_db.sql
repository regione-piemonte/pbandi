/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

/*
*** script da eseguire ad ogni rilascio
*/


-- PBANDI_C_COSTANTI (da aggiornare)
INSERT INTO PBANDI_C_COSTANTI VALUES
('beneficiario_mail_mittente','no-reply@csi.it');
        
insert into PBANDI_C_COSTANTI values
('beneficiario_mail_mittente_GDPR',
 'La presente e-mail e'' stata generata automaticamente da un indirizzo di posta elettronica di solo invio; si chiede pertanto di non rispondere al messaggio.Le informazioni trasmesse attraverso la presente e-mail ed i suoi allegati, sono diretti esclusivamente al destinatario e devono ritenersi riservati con divieto di diffusione e di uso nei giudizi, salva espressa autorizzazione; nel caso di utilizzo senza espressa autorizzazione, verra'' effettuata denuncia al competente Consiglio dell''Ordine Forense per violazione dell''art. 28 del Codice Deontologico. La diffusione e la comunicazione da parte di soggetto diverso dal destinatario, e'' vietata dall''art. 616 e ss. c.p. e dal d. lgs. n. 196/03. Se la presente e-mail e eventuali suoi allegati fossero stati ricevuti per errore da persona diversa dal destinatario, siete pregati di distruggere tutto quanto ricevuto e di informare il mittente attraverso l''indirizzo assistenzapiattaforma.bandi@csi.it'
);
--
INSERT INTO PBANDI_D_NOME_BATCH VALUES
(28, 'PBAN-INVIO-MAIL-BENEFICIARI','Procedura pck_pbandi_processo.SendNotificationMessage invio mail ai beneficiari');
INSERT INTO pbandi_d_errore_batch VALUES
('W108', 'Errore invio mail ai beneficiari Procedura pck_pbandi_processo.SendNotificationMessage',NULL);
--

Insert into PBANDI_D_TIPO_ENTE_COMPETENZA
   (ID_TIPO_ENTE_COMPETENZA, DESC_BREVE_TIPO_ENTE_COMPETENZ, DESC_TIPO_ENTE_COMPETENZA, DT_INIZIO_VALIDITA, COD_IGRUE_T51)
 Values
   (10, 'PA', 'Pubblica Amministrazione (per scheda progetto)', TO_DATE('11/05/2021 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 10);


update PBANDI_C_VERSIONE
set VERSIONE_DB = '3.25.0';

insert into PBANDI_C_ENTITA
(ID_ENTITA, NOME_ENTITA, FLAG_DA_TRACCIARE) 
(select seq_PBANDI_C_ENTITA.nextval,tabs.TABLE_NAME,'S'
from all_tables tabs
where tabs.OWNER = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
and tabs.TABLE_NAME like 'PBANDI_%'
and not exists (select 'x' from pbandi_c_entita where tabs.TABLE_NAME = NOME_ENTITA));

commit;

declare
  cursor curAttr is select col.COLUMN_NAME,en.id_ENTITA,col.TABLE_NAME
                    from all_tab_cols col,PBANDI_C_ENTITA en
                    where col.owner = (select decode(instr(user,'_RW'),0,user,replace(user,'_RW',null)) from dual)
                    and col.TABLE_NAME like 'PBANDI_%'
                    and col.COLUMN_NAME not like 'SYS_%'
                    and col.TABLE_NAME = en.NOME_ENTITA
                    and not exists (select 'x' from PBANDI_C_ATTRIBUTO att 
                                    where col.COLUMN_NAME = att.NOME_ATTRIBUTO
                                    and att.id_entita = en.id_ENTITA);
                                    
  nPosiz  PLS_INTEGER := NULL;                                                                          
begin
  for recAttr in curAttr loop

    BEGIN
      SELECT POSITION
      INTO   nPosiz 
      FROM   all_CONSTRAINTS A,all_CONS_COLUMNS B
      WHERE  A.CONSTRAINT_TYPE = 'P'
      AND    A.TABLE_NAME      = recAttr.TABLE_NAME
      AND    A.CONSTRAINT_NAME = B.CONSTRAINT_NAME
      AND    COLUMN_NAME       = recAttr.COLUMN_NAME;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        nPosiz := NULL;
    END;
    
    insert into PBANDI_C_ATTRIBUTO
    (ID_ATTRIBUTO, 
     NOME_ATTRIBUTO, 
     FLAG_DA_TRACCIARE, 
     ID_ENTITA,
     KEY_POSITION_ID) 
    values
    (SEQ_PBANDI_C_ATTRIBUTO.NEXTVAL,
     recAttr.COLUMN_NAME,
     'N',
     recAttr.id_ENTITA,
     nPosiz);
  end loop;
  COMMIT;
end;
/