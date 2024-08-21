/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--*****************************************************--
----         F I N P I E M O N T E                -------
--*****************************************************--

----------------------------------------------------------
--              T R I G G E R                       ------
----------------------------------------------------------

create or replace trigger TG_PBANDI_T_RICH_INTEGRAZ_AI
   after insert on PBANDI_T_RICHIESTA_INTEGRAZ
   referencing old as OLD new as NEW
   for each row
   
when (NEW.ID_ENTITA = 570)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  c.descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit;
              
    CURSOR c_cl (c_id_controllo_loco NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where id_controllo_loco = c_id_controllo_loco;
            
    CURSOR c_monit_cl (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_id_entita_cl   PBANDI_C_ENTITA.id_entita%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;
   
     
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento della integrazione al controllo in loco
      OPEN c3(v_progr_bando_linea_intervento,'MOT07');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
           -- Recupero ID_ENTITA dell'evento che viene sospeso
           BEGIN
               SELECT ID_ENTITA 
                 INTO v_id_entita_cl
                 FROM PBANDI_C_ENTITA 
                 WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO';
           END;
           
       -- Recupero l'identificativo di monitoraggio per il controllo in loco che viene sospesa
       OPEN c_monit_cl (v_id_entita_cl,:NEW.ID_TARGET);
       FETCH c_monit_cl INTO v_id_monit_tempi_sospeso;
       IF c_monit_cl%NOTFOUND THEN
         RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_MONIT_TEMPI - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
       END IF;
       CLOSE c_monit_cl;
           
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_CONTROLLO_LOCO', to_char(:NEW.ID_TARGET));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          v_id_progetto,
          :NEW.DT_RICHIESTA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO'),
          :NEW.ID_TARGET,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          null,
          v_id_monit_tempi_sospeso);

      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger tg_pbandi_t_rich_integr_au_03
-- 6.1.8.2 da pag 39
   after update of DT_INVIO on PBANDI_T_RICHIESTA_INTEGRAZ
   referencing old as OLD new as NEW
   for each row   
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
	    FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
   -- Da verificare, non specificato in analisi ???
   CURSOR c_cl (c_id_target NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where ID_CONTROLLO_LOCO = c_id_target;     
	   
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
		         NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_progetto                  PBANDI_R_CAMP_CONTR_LOCO.id_progetto%TYPE;
   
   
   
   
BEGIN

   -- Verifica se e' attivo il monitoraggio a livello di sistema
   
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;
   
   -- Verifico se e'¨ attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_target);
	  FETCH c2 INTO v_progr_bando_linea_intervento,
	                v_monit_tempi;
	  IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Aggiornamento della data di chiusura della dichiarazione di spesa
	  UPDATE PBANDI_T_MONIT_TEMPI
	     SET DT_FINE = :NEW.DT_INVIO
		WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ')
		  AND ID_TARGET = :NEW.ID_RICHIESTA_INTEGRAZ;
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_RICH_INTEGR_AU_02
   after update of ID_STATO_RICHIESTA,DT_INVIO on PBANDI_T_RICHIESTA_INTEGRAZ
   referencing old as OLD new as NEW
   for each row
   
when (NEW.ID_ENTITA = 570)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_id_stato_richiesta NUMBER) is
           select desc_breve_stato_richiesta
             from PBANDI_D_STATO_RICH_INTEGRAZ a
            where a.id_stato_richiesta = c_id_stato_richiesta;

    CURSOR c_monit_cl (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_param_monit_bando_linea
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

    CURSOR c_cl (c_id_controllo_loco NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where id_controllo_loco = c_id_controllo_loco;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_cl   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_desc_breve_stato_richiesta PBANDI_D_STATO_RICH_INTEGRAZ.desc_breve_stato_richiesta%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
       -- Recupero ID_ENTITA dell'evento che viene sospeso
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_cl
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO';
       END;
       
       OPEN c_monit_cl (v_id_entita_cl,:NEW.ID_TARGET);
       FETCH c_monit_cl into v_id_param_monit_bando_linea;
       IF c_monit_cl%FOUND THEN
          IF :NEW.ID_STATO_RICHIESTA IS NOT NULL THEN
              OPEN c3(:NEW.ID_STATO_RICHIESTA);
              FETCH c3 INTO v_desc_breve_stato_richiesta;
              IF c3%NOTFOUND THEN
                RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_D_STATO_RICH_INTEGRAZ - ID_STATO_RICHIESTA='||:NEW.ID_STATO_RICHIESTA);
              END IF;
              CLOSE c3;
          END IF;
       
          -- Aggiornamento della data fine se lo stato della richiesta è CHIUSA D'UFFICIO o è valorizzata la data invio
          IF v_desc_breve_stato_richiesta = 'CHIUSA_UFFICIO' OR :NEW.DT_INVIO IS NOT NULL THEN
              UPDATE PBANDI_T_MONIT_TEMPI
                 SET DT_FINE = NVL(:NEW.DT_INVIO,SYSDATE)
                WHERE ID_ENTITA = v_id_entita_cl
                  AND ID_TARGET = :NEW.ID_TARGET;
          END IF;
       END IF;
       CLOSE c_monit_cl;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_RICH_INTEGR_AU_01
   after update of DT_NOTIFICA on PBANDI_T_RICHIESTA_INTEGRAZ
   referencing old as OLD new as NEW
   for each row
   
when (NEW.ID_ENTITA = 570)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_id_param_monit_bando_linea NUMBER) is
           select num_giorni
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
            where a.id_param_monit_bando_linea = c_id_param_monit_bando_linea;

    CURSOR c_monit_cl (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_param_monit_bando_linea
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

    CURSOR c_cl (c_id_controllo_loco NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where id_controllo_loco = c_id_controllo_loco;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_cl   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Recupero il progetto accedendo al controllo in loco
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
       -- Recupero ID_ENTITA dell'evento che viene sospeso
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_cl
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO';
       END;
       
       OPEN c_monit_cl (v_id_entita_cl,:NEW.ID_TARGET);
       FETCH c_monit_cl into v_id_param_monit_bando_linea;
       IF c_monit_cl%FOUND THEN
          OPEN c3(v_id_param_monit_bando_linea);
          FETCH c3 INTO v_num_giorni;
          IF c3%NOTFOUND THEN
         RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_MONIT_TEMPI - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
          END IF;
          CLOSE c3;
       
          -- Aggiornamento della data decorrenza e num giorni scadenza della integrazione di spesa
          UPDATE PBANDI_T_MONIT_TEMPI
             SET DT_DECORRENZA = :NEW.DT_NOTIFICA,
                 NUM_GIORNI = nvl(:NEW.NUM_GIORNI_SCADENZA,v_num_giorni)
            WHERE ID_ENTITA = v_id_entita_cl
              AND ID_TARGET = :NEW.ID_TARGET;
       END IF;
       CLOSE c_monit_cl;
   
   END IF;

END;
/

create or replace trigger tg_pbandi_t_rich_integr_ai_02
-- 6.1.8.2 da pag 37
   after insert on PBANDI_T_RICHIESTA_INTEGRAZ
   referencing old as OLD new as NEW
   for each row
   when (NEW.ID_ENTITA = 570) -- 'PBANDI_T_CONTROLLO_LOCO' --> 570
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
    CURSOR c_cl (c_id_target NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where ID_CONTROLLO_LOCO = c_id_target;    
            
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;

     CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;
   
   
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_entita_gr           PBANDI_C_ENTITA.id_entita%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_id_progetto              PBANDI_R_CAMP_CONTR_LOCO.id_progetto%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_TARGET);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_TARGET);
   END IF;
   CLOSE c_cl;

   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento del controllo in loco
      OPEN c3(v_progr_bando_linea_intervento,'MOT06');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
        -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ';
       END;
       
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_TARGET);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
           
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(v_id_progetto));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_CONTROLLO_LOCO', to_char(:NEW.ID_TARGET));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_AVVIO_CONTROLLI', to_char(:NEW.DT_RICHIESTA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          v_id_progetto,
          :NEW.DT_RICHIESTA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ'),
          :NEW.ID_RICHIESTA_INTEGRAZ,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          v_num_giorni,
          v_id_monit_tempi_sospeso);

       END IF;
       CLOSE c_monit_gr;
      
      
      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_PROPOSTA_EROG_AU_1
   after update of DT_FINE_VALIDITA on PBANDI_T_PROPOSTA_EROGAZIONE
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;


    CURSOR c_monit_pe (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_param_monit_bando_linea
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

    CURSOR c_dich (c_id_dichiarazione_spesa NUMBER) is
           select id_progetto
             from PBANDI_T_DICHIARAZIONE_SPESA
            where id_dichiarazione_spesa = c_id_dichiarazione_spesa;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_pe   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_pe
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE';
       END;
       
       OPEN c_monit_pe (v_id_entita_pe,:NEW.ID_PROPOSTA);
       FETCH c_monit_pe into v_id_param_monit_bando_linea;
       IF c_monit_pe%FOUND THEN
       
          -- Aggiornamento della data fine
          UPDATE PBANDI_T_MONIT_TEMPI
             SET DT_FINE = :NEW.DT_FINE_VALIDITA
            WHERE ID_ENTITA = v_id_entita_pe
              AND ID_TARGET = :NEW.ID_PROPOSTA;
       END IF;
       CLOSE c_monit_pe;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_PROPOSTA_EROG_AI
   after insert on PBANDI_T_PROPOSTA_EROGAZIONE
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  'NotificaTempiErogazione' as descr_breve_template_notifica -- Viene forzato NotificaTempiErogazione (Notifica per numero di giorni entro cui autorizzare l’erogazione dell’agevolazione a decorrere dalla data di chiusura della validazione della rendicontazione con esito Erogazione)
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;
              
    CURSOR c4 (c_id_progetto NUMBER,
               c_dt_validazione_spesa DATE) is
           select trunc(c_dt_validazione_spesa) - trunc(dt_dichiarazione)  as num_giorni_effet
             from PBANDI_T_DICHIARAZIONE_SPESA
            where id_progetto = c_id_progetto
              and dt_chiusura_validazione = c_dt_validazione_spesa;            
       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_num_giorni_residuo PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE; --Giorni residuo per controlli pre-erogazione
   v_num_giorni_effet PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE; --Giorni effettivi per la validazione della dichiarazione di spesa
   v_id_entita_pe   PBANDI_C_ENTITA.id_entita%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   

   
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento della proposta di erogazione
      OPEN c3(v_progr_bando_linea_intervento,'MOT01');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
           -- Recupero ID_ENTITA dell'evento 
           BEGIN
               SELECT ID_ENTITA 
                 INTO v_id_entita_pe
                 FROM PBANDI_C_ENTITA 
                 WHERE NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE';
           END;
           
         -- Recupero Giorni residuo per controlli pre-erogazione su  dichiarazione di spesa corrente
         open c4 (:NEW.ID_PROGETTO,:NEW.DT_VALIDAZIONE_SPESA);
         fetch c4 into     v_num_giorni_effet;
          IF c4%NOTFOUND THEN
            RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_DICHIARAZIONE_SPESA - ID_PROGETTO='||:NEW.ID_PROGETTO ||' DT_VALIDAZIONE_SPESA '||to_char(:NEW.DT_VALIDAZIONE_SPESA,'dd/mm/yyyy') );
          END IF;
         close c4;         
         v_num_giorni_residuo := v_num_giorni - v_num_giorni_effet;
           
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_CHIUSURA_VALIDAZIONE', to_char(:NEW.DT_VALIDAZIONE_SPESA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_VALIDAZIONE_SPESA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'),
          :NEW.ID_PROPOSTA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          v_num_giorni_residuo,
          null);

      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_INTEGR_SPESA_AU_02
   after update of ID_STATO_RICHIESTA,DATA_INVIO on PBANDI_T_INTEGRAZIONE_SPESA
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_id_stato_richiesta NUMBER) is
           select desc_breve_stato_richiesta
             from PBANDI_D_STATO_RICH_INTEGRAZ a
            where a.id_stato_richiesta = c_id_stato_richiesta;

    CURSOR c_monit_integr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_param_monit_bando_linea
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

    CURSOR c_dich (c_id_dichiarazione_spesa NUMBER) is
           select id_progetto
             from PBANDI_T_DICHIARAZIONE_SPESA
            where id_dichiarazione_spesa = c_id_dichiarazione_spesa;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_integr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_desc_breve_stato_richiesta PBANDI_D_STATO_RICH_INTEGRAZ.desc_breve_stato_richiesta%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Recupero il progetto accedendo alla dichiarazione di spesa
   OPEN c_dich (:NEW.ID_DICHIARAZIONE_SPESA);
   FETCH c_dich INTO v_id_progetto;
   IF c_dich%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_DICHIARAZIONE_SPESA - ID_DICHIARAZIONE_SPESA='||:NEW.ID_DICHIARAZIONE_SPESA);
   END IF;
   CLOSE c_dich;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
       -- Recupero ID_ENTITA dell'evento che viene sospeso
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_integr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA';
       END;
       
       OPEN c_monit_integr (v_id_entita_integr,:NEW.ID_INTEGRAZIONE_SPESA);
       FETCH c_monit_integr into v_id_param_monit_bando_linea;
       IF c_monit_integr%FOUND THEN
          IF :NEW.ID_STATO_RICHIESTA IS NOT NULL THEN
              OPEN c3(:NEW.ID_STATO_RICHIESTA);
              FETCH c3 INTO v_desc_breve_stato_richiesta;
              IF c3%NOTFOUND THEN
                RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_D_STATO_RICH_INTEGRAZ - ID_STATO_RICHIESTA='||:NEW.ID_STATO_RICHIESTA);
              END IF;
              CLOSE c3;
          END IF;
       
          -- Aggiornamento della data fine se lo stato della richiesta è CHIUSA D'UFFICIO o è valorizzata la data invio
          IF v_desc_breve_stato_richiesta = 'CHIUSA_UFFICIO' OR :NEW.DATA_INVIO IS NOT NULL THEN
              UPDATE PBANDI_T_MONIT_TEMPI
                 SET DT_FINE = NVL(:NEW.DATA_INVIO,SYSDATE)
                WHERE ID_ENTITA = v_id_entita_integr
                  AND ID_TARGET = :NEW.ID_INTEGRAZIONE_SPESA;
          END IF;
       END IF;
       CLOSE c_monit_integr;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_INTEGR_SPESA_AU_01
   after update of DT_NOTIFICA on PBANDI_T_INTEGRAZIONE_SPESA
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_id_param_monit_bando_linea NUMBER) is
           select num_giorni
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
            where a.id_param_monit_bando_linea = c_id_param_monit_bando_linea;

    CURSOR c_monit_integr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_param_monit_bando_linea
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

    CURSOR c_dich (c_id_dichiarazione_spesa NUMBER) is
           select id_progetto
             from PBANDI_T_DICHIARAZIONE_SPESA
            where id_dichiarazione_spesa = c_id_dichiarazione_spesa;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_integr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Recupero il progetto accedendo alla dichiarazione di spesa
   OPEN c_dich (:NEW.ID_DICHIARAZIONE_SPESA);
   FETCH c_dich INTO v_id_progetto;
   IF c_dich%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_DICHIARAZIONE_SPESA - ID_DICHIARAZIONE_SPESA='||:NEW.ID_DICHIARAZIONE_SPESA);
   END IF;
   CLOSE c_dich;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
       -- Recupero ID_ENTITA dell'evento che viene sospeso
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_integr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA';
       END;
       
       OPEN c_monit_integr (v_id_entita_integr,:NEW.ID_INTEGRAZIONE_SPESA);
       FETCH c_monit_integr into v_id_param_monit_bando_linea;
       IF c_monit_integr%FOUND THEN
          OPEN c3(v_id_param_monit_bando_linea);
          FETCH c3 INTO v_num_giorni;
          IF c3%NOTFOUND THEN
            RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_PARAM_MONIT_BANDO_LIN - ID_PARAM_MONIT_BANDO_LINEA='||v_id_param_monit_bando_linea);
          END IF;
          CLOSE c3;
       
          -- Aggiornamento della data decorrenza e num giorni scadenza della integrazione di spesa
          UPDATE PBANDI_T_MONIT_TEMPI
             SET DT_DECORRENZA = :NEW.DT_NOTIFICA,
                 NUM_GIORNI = nvl(:NEW.NUM_GIORNI_SCADENZA,v_num_giorni)
            WHERE ID_ENTITA = v_id_entita_integr
              AND ID_TARGET = :NEW.ID_INTEGRAZIONE_SPESA;
       END IF;
       CLOSE c_monit_integr;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_INTEGRAZ_SPESA_AI
   after insert on PBANDI_T_INTEGRAZIONE_SPESA
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  c.descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit;
              
    CURSOR c_dich (c_id_dichiarazione_spesa NUMBER) is
           select id_progetto
             from PBANDI_T_DICHIARAZIONE_SPESA
            where id_dichiarazione_spesa = c_id_dichiarazione_spesa;
            
    CURSOR c_monit_dich (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;

       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_progetto PBANDI_T_DICHIARAZIONE_SPESA.id_progetto%TYPE;
   v_id_entita_dich   PBANDI_C_ENTITA.id_entita%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo alla dichiarazione di spesa
   OPEN c_dich (:NEW.ID_DICHIARAZIONE_SPESA);
   FETCH c_dich INTO v_id_progetto;
   IF c_dich%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_T_DICHIARAZIONE_SPESA - ID_DICHIARAZIONE_SPESA='||:NEW.ID_DICHIARAZIONE_SPESA);
   END IF;
   CLOSE c_dich;
   
   
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento della integrazione della dichiarazione di spesa
      OPEN c3(v_progr_bando_linea_intervento,'MOT02');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
           -- Recupero ID_ENTITA dell'evento che viene sospeso
           BEGIN
               SELECT ID_ENTITA 
                 INTO v_id_entita_dich
                 FROM PBANDI_C_ENTITA 
                 WHERE NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA';
           END;
           
       -- Recupero l'identificativo di monitoraggio per la dichiarazione di spesa che viene sospesa
       OPEN c_monit_dich (v_id_entita_dich,:NEW.ID_DICHIARAZIONE_SPESA);
       FETCH c_monit_dich INTO v_id_monit_tempi_sospeso;
       IF c_monit_dich%FOUND THEN
           
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_DICHIARAZIONE_DI_SPESA', to_char(:NEW.ID_DICHIARAZIONE_SPESA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_RICHIESTA_INTEGRAZ_DIC', to_char(:NEW.DATA_RICHIESTA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          v_id_progetto,
          :NEW.DATA_RICHIESTA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA'),
          :NEW.ID_INTEGRAZIONE_SPESA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          null,
          v_id_monit_tempi_sospeso);
          
       END IF;
       CLOSE c_monit_dich;


      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REV_PROC_AU_1
   after update of DT_NOTIFICA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row   
when (NEW.ID_TIPOLOGIA_REVOCA = 2)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';       
    
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;

              
       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_idTarget  PBANDI_T_MONIT_TEMPI.ID_TARGET%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   
   -- Controllo lo stato revoca (in attesa di controdeduzione)e la data notifica valorizzata
   -- In caso negativo non attivo il monitoraggio
   -- Lo stato revoca = 6 è solo relativo al procedimento
   IF  NOT(:NEW.ID_STATO_REVOCA  = 6 
         AND :NEW.DT_NOTIFICA IS NOT NULL
         AND :NEW.ID_ATTIVITA_REVOCA IS NULL) THEN
       v_monit_tempi := 'N';
   END IF;
     
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   -- verifico che non ci sia già un record inserito sulla PBANDI_T_MONIT_TEMPI
    Begin
      SELECT ID_TARGET
        INTO v_idTarget
        FROM  PBANDI_T_MONIT_TEMPI
        WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		  AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
       --      
       v_monit_tempi := 'N'; -- esiste un record
       --
       update PBANDI_T_MONIT_TEMPI
       set    DT_DECORRENZA = :NEW.DT_NOTIFICA
       where  ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		      and ID_TARGET = :NEW.ID_GESTIONE_REVOCA;       
       -- 
     Exception 
         when no_data_found then null;
         when too_many_rows then v_monit_tempi := 'N';   -- esistono più record      
     end;
     --  
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento della gestione revoca
      OPEN c3(v_progr_bando_linea_intervento,'MOT03');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
           -- Recupero ID_ENTITA dell'evento 
           BEGIN
               SELECT ID_ENTITA 
                 INTO v_id_entita_gr
                 FROM PBANDI_C_ENTITA 
                 WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
           END;
           
       
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_NOTIFICA', to_char(:NEW.DT_NOTIFICA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          :NEW.GG_RISPOSTA,
          NULL);

      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_8
-- 6.1.10	Comunicazione di accettazione della proroga per ricevimento integrazioni M0T12
   after update of ID_STATO_REVOCA, ID_ATTIVITA_REVOCA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row 
when (
     (NEW.ID_ATTIVITA_REVOCA = 9) AND
     (NEW.ID_STATO_REVOCA = 7 ) -- 7 = SOSPESO
     )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT12');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       /*
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       */
       
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_NOTIFICA', to_char(:NEW.DT_NOTIFICA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      --
      update PBANDI_T_MONIT_TEMPI
       set    DT_FINE  = :NEW.DT_ATTIVITA
       where  ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		     and  ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
      -- 
      
      
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          :NEW.GG_RISPOSTA,
          NULL);
          
      -- END IF;
      -- CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_6
-- 6.1.9  Comunicazione di accettazione della proroga per ricevimento controdeduzioni M0T11
   after update of ID_ATTIVITA_REVOCA, ID_STATO_REVOCA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row   
   
when (
     (NEW.ID_ATTIVITA_REVOCA = 9) AND
     (NEW.ID_STATO_REVOCA = 6) -- 6 = ATT_CONTRODED
     )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT11');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       /*
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       */
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       --IF c_monit_gr%FOUND THEN
       
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_NOTIFICA', to_char(:NEW.DT_NOTIFICA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      --
      update PBANDI_T_MONIT_TEMPI
       set    DT_FINE  = :NEW.DT_ATTIVITA
       where  ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		     and  ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
      -- 
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          :NEW.GG_RISPOSTA,
          NULL);
          
      -- END IF;
      -- CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_3
   after update of ID_TIPOLOGIA_REVOCA, ID_STATO_REVOCA, DT_NOTIFICA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row   
when (NEW.ID_TIPOLOGIA_REVOCA = 2)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;

 /* Paragrafo 6.1.7 - pag 28
    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;
 */

 --
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   v_idTarget  PBANDI_T_MONIT_TEMPI.ID_TARGET%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Controllo lo stato revoca (in attesa di controdeduzione)e la data notifica valorizzata
   -- In caso negativo non attivo il monitoraggio
   -- Lo stato revoca = 6 è solo relativo al procedimento
   IF  NOT(:NEW.ID_STATO_REVOCA  = 7  -- sospeso
         AND :NEW.DT_NOTIFICA IS NOT NULL) THEN
       v_monit_tempi := 'N';
   END IF;
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   -- verifico che non ci sia già un record inserito sulla PBANDI_T_MONIT_TEMPI
    Begin
      SELECT ID_TARGET
        INTO v_idTarget
        FROM  PBANDI_T_MONIT_TEMPI
        WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		  AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
       --      
       v_monit_tempi := 'N'; -- esiste un record
       --            
       update PBANDI_T_MONIT_TEMPI
       set    DT_DECORRENZA = :NEW.DT_NOTIFICA
       where  ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		      and ID_TARGET = :NEW.ID_GESTIONE_REVOCA;       
       --   
     Exception 
         when no_data_found then null;
         when too_many_rows then v_monit_tempi := 'N';   -- esistono più record      
     end;
    --  
   
   
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT05');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       /*
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       */
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_ATTIVITA', to_char(:NEW.DT_ATTIVITA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          :NEW.GG_RISPOSTA,
          v_id_monit_tempi_sospeso);
          
     /*
       END IF;
       CLOSE c_monit_gr;        
     */ 
     END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_2
   after update of ID_ATTIVITA_REVOCA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row   
when (
     (NEW.ID_TIPOLOGIA_REVOCA = 2) AND
     (NEW.ID_STATO_REVOCA  = 6 )   AND
     (NEW.DT_NOTIFICA IS NOT NULL) AND 
     (NEW.ID_ATTIVITA_REVOCA = 6 )  AND -- Controdeduzione ricevuta
     (NEW.DT_ATTIVITA IS NOT NULL) 
     )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT04');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       /*
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       */
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_ATTIVITA', to_char(:NEW.DT_ATTIVITA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          v_num_giorni,
          NULL -- v_id_monit_tempi_sospeso -- mc 13092023 rif. analisi 6.1.6
          );
          
       --END IF;
       --CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_14
-- 6.1.13	Esame contestazioni al provvedimento di revoca MOT17
   after update of ID_STATO_REVOCA, ID_ATTIVITA_REVOCA, DT_ATTIVITA, DT_NOTIFICA  on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row
   
when (
        (NEW.ID_STATO_REVOCA  = 8) AND    -- 8 =  PROV_REV 
        (NEW.DT_NOTIFICA IS NOT NULL) AND 
        (NEW.ID_ATTIVITA_REVOCA = 12) AND -- 12 = CONTEST_RIC      
        (NEW.DT_ATTIVITA  IS NOT NULL) 
        )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Controllo lo stato revoca e ID_ATTIVITA_REVOCA 
   -- In caso negativo non attivo il monitoraggio
  
   IF  NOT(:NEW.ID_STATO_REVOCA  = 8) AND    -- 8 =  PROV_REV 
       NOT(:NEW.DT_NOTIFICA IS NOT NULL) AND 
       NOT(:NEW.ID_ATTIVITA_REVOCA = 12) AND -- 12 = CONTEST_RIC      
       NOT(:NEW.DT_ATTIVITA  IS NOT NULL) 
     THEN      
       v_monit_tempi := 'N';
   END IF;
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT17');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_ATTIVITA', to_char(:NEW.DT_ATTIVITA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_ATTIVITA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          v_num_giorni,
          v_id_monit_tempi_sospeso);
          
       END IF;
       CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_12
-- 6.1.12	Provvedimento di revoca (a decorrere dalla data di notifica che comunica 
-- l’emissione del provvedimento di “revoca”) MOT15
   after update of DT_NOTIFICA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row  
when (
     (NEW.DT_NOTIFICA IS NOT NULL) AND
     (NEW.ID_STATO_REVOCA  = 8) AND   -- 8 = PROV_REV 
     (NEW.ID_ATTIVITA_REVOCA IS NULL)
     )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   v_idTarget  PBANDI_T_MONIT_TEMPI.ID_TARGET%TYPE;
   
BEGIN
   
   
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   -- verifico che non ci sia già un record inserito sulla PBANDI_T_MONIT_TEMPI
    Begin
      SELECT ID_TARGET
        INTO v_idTarget
        FROM  PBANDI_T_MONIT_TEMPI
        WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		  AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
       --      
       v_monit_tempi := 'N'; -- esiste un record       
       --
       update PBANDI_T_MONIT_TEMPI
       set    DT_DECORRENZA = :NEW.DT_NOTIFICA
       where  ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		      and ID_TARGET = :NEW.ID_GESTIONE_REVOCA;       
       -- 
       
     Exception 
         when no_data_found then null;
         when too_many_rows then v_monit_tempi := 'N';   -- esistono più record      
     end;
    -- 
   
   
   
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT15');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       /*
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       */
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_NOTIFICA', to_char(:NEW.DT_NOTIFICA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_NOTIFICA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          :NEW.GG_RISPOSTA,
          NULL);
          
       --END IF;
       --CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/


create or replace trigger TG_PBANDI_T_GEST_REVOCA_AU_10
-- 6.1.11	 Esame delle integrazioni inviate dal beneficiario MOT13
   after update of ID_STATO_REVOCA, ID_ATTIVITA_REVOCA,DT_NOTIFICA,DT_ATTIVITA on PBANDI_T_GESTIONE_REVOCA
   referencing old as OLD new as NEW
   for each row   
when (
     (NEW.ID_STATO_REVOCA = 7) AND
     (NEW.DT_NOTIFICA IS NOT NULL) AND
     (NEW.ID_ATTIVITA_REVOCA  = 7) AND --7  = INTEGR_RIC 
     (NEW.DT_ATTIVITA  IS NOT NULL)  
     )
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;


    CURSOR c_monit_gr (c_id_entita NUMBER, c_id_target NUMBER) is
           select id_monit_tempi
             from PBANDI_T_MONIT_TEMPI
            where id_entita = c_id_entita
              and id_target = c_id_target;


   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_entita_gr   PBANDI_C_ENTITA.id_entita%TYPE;
   v_dummy VARCHAR2(1);
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   
   v_num_gg_esame_controd_non_ric   NUMBER;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;

   -- Controllo lo stato revoca e ID_ATTIVITA_REVOCA 
   -- In caso negativo non attivo il monitoraggio
  
   IF  NOT(:NEW.DT_NOTIFICA IS NOT NULL)
       AND(:NEW.ID_ATTIVITA_REVOCA  = 7) --7  = INTEGR_RIC 
       AND(:NEW.DT_ATTIVITA  IS NOT NULL)       
     THEN       
       v_monit_tempi := 'N';
   END IF;
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
   
      OPEN c3(v_progr_bando_linea_intervento,'MOT13');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
   
      IF c3%FOUND THEN
       -- Recupero ID_ENTITA dell'evento
       BEGIN
           SELECT ID_ENTITA 
             INTO v_id_entita_gr
             FROM PBANDI_C_ENTITA 
             WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA';
       END;
       
       /*
       -- Recupero l'identificativo di monitoraggio per la gestione revoca che viene sospesa
       OPEN c_monit_gr (v_id_entita_gr,:NEW.ID_GESTIONE_REVOCA);
       FETCH c_monit_gr INTO v_id_monit_tempi_sospeso;
       -- Solo se trovo l'occorrenza inserisco il monitoraggio
       IF c_monit_gr%FOUND THEN
       */
       
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_REVOCA', to_char(:NEW.NUMERO_REVOCA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('CODICE_VISUALIZZATO', v_codice_visualizzato);
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_ATTIVITA', to_char(:NEW.DT_ATTIVITA,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          :NEW.DT_ATTIVITA,
          NULL,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'),
          :NEW.ID_GESTIONE_REVOCA,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          v_num_giorni,
          NULL);
          
       --END IF;
       --CLOSE c_monit_gr;
          
      END IF;
      CLOSE c3;
   
   END IF;

END;
/

create or replace trigger "TG_PBANDI_T_GEST_REVOCA_AU_X1" 
after update of ID_ATTIVITA_REVOCA, ID_STATO_REVOCA  on PBANDI_T_GESTIONE_REVOCA 
  -- lato applicativo viene inserito un record clonando il precedente ma solo successivamente ne viene cambiato lo stato 
  -- quindi viene gestito l'update e non la insert
   referencing old as OLD new as NEW
   for each row
when ( 
       (NEW.ID_STATO_REVOCA  = 3)AND
       (NEW.ID_ATTIVITA_REVOCA IS NULL)
       )
DECLARE

PRAGMA AUTONOMOUS_TRANSACTION;

   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
	    FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
	   
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
		         NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;   
   v_NUMERO_REVOCA      PBANDI_T_GESTIONE_REVOCA.NUMERO_REVOCA%TYPE;  
  
   
   
BEGIN   
   -- Verifica se e' attivo il monitoraggio a livello di sistema   
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
      
   -- Verifico se e'¨ attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
	  FETCH c2 INTO v_progr_bando_linea_intervento,
	                v_monit_tempi;
	  IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
-----------------------------   
IF  v_monit_tempi = 'S' THEN
-----------------------------
-----------------------------
  
  v_NUMERO_REVOCA := :NEW.NUMERO_REVOCA; 


-- Par. 6.1.6 - Par 6.1.11
   For rec in 
       (
        SELECT ID_GESTIONE_REVOCA,
               ID_STATO_REVOCA, 
               ID_ATTIVITA_REVOCA,
               DT_STATO_REVOCA       
        FROM   PBANDI_T_GESTIONE_REVOCA
        WHERE  ID_TIPOLOGIA_REVOCA = 2
        AND    NUMERO_REVOCA = v_NUMERO_REVOCA
       )Loop
       
             
             UPDATE PBANDI_T_MONIT_TEMPI
               SET DT_FINE = :NEW.DT_STATO_REVOCA
               WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
               AND ID_TARGET = rec.id_gestione_revoca
               AND DT_FINE is null;
                
          COMMIT;
         
       end loop;

-----------------------------
-----------------------------

END IF;

COMMIT;

END;
/


create or replace trigger "TG_PBANDI_T_GEST_REVOCA_AU_X" 
after update of ID_ATTIVITA_REVOCA, ID_STATO_REVOCA  on PBANDI_T_GESTIONE_REVOCA 
   referencing old as OLD new as NEW
   for each row
   
when (NEW.DT_FINE_VALIDITA IS NULL)
DECLARE

   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
	    FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
	   
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
		         NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   
BEGIN

   -- Verifica se e' attivo il monitoraggio a livello di sistema   
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
      
   -- Verifico se e'¨ attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
	  FETCH c2 INTO v_progr_bando_linea_intervento,
	                v_monit_tempi;
	  IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
-----------------------------   
IF  v_monit_tempi = 'S' THEN
-----------------------------
-----------------------------
-- Par. 6.1.5
   IF
     :NEW.ID_STATO_REVOCA = 6 AND
     :NEW.ID_ATTIVITA_REVOCA IN (6,11)     -- 6 = CONTRODED_RIC , 11 = TERMINE_SCAD
   THEN
      -- Aggiornamento della data di chiusura 
	  UPDATE PBANDI_T_MONIT_TEMPI
	     SET DT_FINE = :NEW.DT_ATTIVITA
		WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		  AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
   END IF;
   ---
-- Par. 6.1.6
   IF      
      :NEW.ID_STATO_REVOCA = 6 and    --6 = ATT_CONTRODED 
      :NEW.ID_ATTIVITA_REVOCA IN (2,3)
        
   THEN    
    
      UPDATE PBANDI_T_MONIT_TEMPI
      SET DT_FINE = :NEW.DT_ATTIVITA
      WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
      AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA
      AND DT_FINE is null;
                  
   END IF;
          
-- Par. 6.1.7 e Par 6.1.10 
        IF         
           :NEW.ID_STATO_REVOCA = 7 AND
           :NEW.ID_ATTIVITA_REVOCA IN (7,11,15) -- 7 = INTEGR_RIC , 11 = TERMINE_SCAD., 15 = RICH_INTEGR_CHIUSA            
        THEN
          
         UPDATE PBANDI_T_MONIT_TEMPI
	       SET DT_FINE = :NEW.DT_ATTIVITA
		     WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		     AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
         
        END IF; 
-- Par 6.1.9
       IF
          :NEW.ID_STATO_REVOCA = 6 AND
          :NEW.ID_ATTIVITA_REVOCA IN (6,11) -- 6 = CONTRODED_RIC , 11 = TERMINE_SCAD.
       THEN
         
          UPDATE PBANDI_T_MONIT_TEMPI
	        SET DT_FINE = :NEW.DT_ATTIVITA
		      WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		      AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
      END IF;
      

-- Par 6.1.11
       IF
              :NEW.ID_STATO_REVOCA = 7 and    -- 7 = SOSPESO
              :NEW.ID_ATTIVITA_REVOCA IN (2,3) -- 2 = ITER_INTEGR oppure 3 = ITER_ARCH         
       THEN
           
           UPDATE PBANDI_T_MONIT_TEMPI
             SET DT_FINE = :NEW.DT_ATTIVITA
             WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
             AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA
             AND DT_FINE is null;  
                
       END IF;
-- Par. 6.1.12
        IF :NEW.ID_STATO_REVOCA = 8 AND
           :NEW.ID_ATTIVITA_REVOCA IN (12,11)          
        THEN
          UPDATE PBANDI_T_MONIT_TEMPI
	        SET DT_FINE = :NEW.DT_ATTIVITA
		      WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		      AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
        END IF;
-- Par. 6.1.13
        IF :NEW.ID_STATO_REVOCA = 8 AND
           :NEW.ID_ATTIVITA_REVOCA IN (5,13) -- 5 = ITER_RIT_AUTOTUTELA , 13 = ITER_CONF_PROV
        THEN
           UPDATE PBANDI_T_MONIT_TEMPI
	         SET DT_FINE = :NEW.DT_ATTIVITA
		       WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
		       AND ID_TARGET = :NEW.ID_GESTIONE_REVOCA;
        END IF;
-----------------------------
-----------------------------
END IF;

END;
/

create or replace trigger "TG_PBANDI_T_GEST_REVOCA_AI_X" 
after insert on PBANDI_T_GESTIONE_REVOCA    
   referencing old as OLD new as NEW
   for each row
when ( 
       (NEW.ID_STATO_REVOCA  = 3)AND
       (NEW.ID_ATTIVITA_REVOCA IS NULL)
       )
DECLARE

PRAGMA AUTONOMOUS_TRANSACTION;

   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
	    FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
	   
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
		         NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;   
   v_NUMERO_REVOCA      PBANDI_T_GESTIONE_REVOCA.NUMERO_REVOCA%TYPE;  
  
   
   
BEGIN

  
   -- Verifica se e' attivo il monitoraggio a livello di sistema   
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
      
   -- Verifico se e'¨ attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
	  FETCH c2 INTO v_progr_bando_linea_intervento,
	                v_monit_tempi;
	  IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
-----------------------------   
IF  v_monit_tempi = 'S' THEN
-----------------------------
-----------------------------
  
  v_NUMERO_REVOCA := :NEW.NUMERO_REVOCA; 


-- Par. 6.1.6 - Par 6.1.11
   For rec in 
       (
        SELECT ID_GESTIONE_REVOCA,
               ID_STATO_REVOCA, 
               ID_ATTIVITA_REVOCA,
               DT_STATO_REVOCA       
        FROM   PBANDI_T_GESTIONE_REVOCA
        WHERE  ID_TIPOLOGIA_REVOCA = 2
        AND    NUMERO_REVOCA = v_NUMERO_REVOCA
       )Loop
       
       
             
             UPDATE PBANDI_T_MONIT_TEMPI
               SET DT_FINE = :NEW.DT_STATO_REVOCA
               WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA')
               AND ID_TARGET = rec.id_gestione_revoca
               AND DT_FINE is null;
                
          COMMIT;
         
       end loop;

-----------------------------
-----------------------------

END IF;

COMMIT;

END;
/


create or replace trigger TG_PBANDI_T_DICHIARAZ_SPESA_AU
   after update of DT_CHIUSURA_VALIDAZIONE on PBANDI_T_DICHIARAZIONE_SPESA
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;

   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   
BEGIN
   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Aggiornamento della data di chiusura della dichiarazione di spesa
      UPDATE PBANDI_T_MONIT_TEMPI
         SET DT_FINE = :NEW.DT_CHIUSURA_VALIDAZIONE
        WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA')
          AND ID_TARGET = :NEW.ID_DICHIARAZIONE_SPESA;
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_DICHIARAZ_SPESA_AI
   after insert on PBANDI_T_DICHIARAZIONE_SPESA
   referencing old as OLD new as NEW
   for each row
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N')
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  c.descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit;

       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
      
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento della dichiarazione di spesa
      OPEN c3(v_progr_bando_linea_intervento,'MOT01');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('NUM_DICHIARAZIONE_DI_SPESA', to_char(:NEW.ID_DICHIARAZIONE_SPESA));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_DICHIARAZIONE_DI_SPESA', to_char(:NEW.DT_DICHIARAZIONE,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
          INSERT INTO PBANDI_T_MONIT_TEMPI
            ( ID_MONIT_TEMPI,
              ID_PROGETTO,
              DT_DECORRENZA,
              DT_FINE,
              ID_ENTITA,
              ID_TARGET,
              DESCRIZIONE,
              ID_PARAM_MONIT_BANDO_LINEA,
              NUM_GIORNI,
              ID_MONIT_TEMPI_SOSPESO)
            VALUES
             (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
              :NEW.ID_PROGETTO,
              :NEW.DT_DICHIARAZIONE,
              NULL,
              (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'),
              :NEW.ID_DICHIARAZIONE_SPESA,
              v_message_notifica,
              v_id_param_monit_bando_linea,
              v_num_giorni,
              NULL);

          END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_CONTROLLO_LOCO_AU
   after update of DT_AVVIO_CONTROLLI on PBANDI_T_CONTROLLO_LOCO
   referencing old as OLD new as NEW
   for each row
   
when (NEW.DT_AVVIO_CONTROLLI IS NOT NULL)
DECLARE
   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';
       
    CURSOR c_cl (c_id_controllo_loco NUMBER) is
           select id_progetto
             from PBANDI_R_CAMP_CONTR_LOCO
            where id_controllo_loco = c_id_controllo_loco;    
            
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;

       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_dt_avvio_controlli        PBANDI_T_CONTROLLO_LOCO.dt_avvio_controlli%TYPE;
   v_dt_fine_controlli        PBANDI_T_CONTROLLO_LOCO.dt_fine_controlli%TYPE;
   v_id_progetto PBANDI_R_CAMP_CONTR_LOCO.id_progetto%TYPE;
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
   -- Recupero il progetto accedendo a PBANDI_R_CAMP_CONTR_LOCO
   OPEN c_cl (:NEW.ID_CONTROLLO_LOCO);
   FETCH c_cl INTO v_id_progetto;
   IF c_cl%NOTFOUND THEN
     RAISE_APPLICATION_ERROR(-20000,'ERRORE ACCESSO PBANDI_R_CAMP_CONTR_LOCO - ID_CONTROLLO_LOCO='||:NEW.ID_CONTROLLO_LOCO);
   END IF;
   CLOSE c_cl;

   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      OPEN c2 (v_id_progetto);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
      -- Inserimento del controllo in loco
      OPEN c3(v_progr_bando_linea_intervento,'MOT06');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
           
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(v_id_progetto));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_CONTROLLO_LOCO', to_char(:NEW.ID_CONTROLLO_LOCO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_AVVIO_CONTROLLI', to_char(:NEW.dt_avvio_controlli,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          v_id_progetto,
          :NEW.dt_avvio_controlli,
          :NEW.dt_fine_controlli,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO'),
          :NEW.ID_CONTROLLO_LOCO,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          NULL,
          NULL);

      END IF;
      CLOSE c3;
              

   
   END IF;

END;
/

create or replace trigger TG_PBANDI_T_CONTROL_LOCO_AL_AI
-- 6.1.8 numero di giorni entro cui effettuare i controlli in loco (MOT06)
   after insert on PBANDI_T_CONTROLLO_LOCO_ALTRI
   referencing old as OLD new as NEW
   for each row
DECLARE

PRAGMA AUTONOMOUS_TRANSACTION;

   CURSOR c1 IS
      SELECT NVL(VALORE,'N')
        FROM PBANDI_C_COSTANTI
       WHERE ATTRIBUTO = 'monitoraggio_tempi';

            
   CURSOR c2 (c_id_progetto NUMBER) is
          select c.progr_bando_linea_intervento,
                 NVL(c.flag_monitoraggio_tempi,'N'),
                 a.codice_visualizzato
          from PBANDI_T_PROGETTO a,
               PBANDI_T_DOMANDA b,
               PBANDI_R_BANDO_LINEA_INTERVENT c
          where a.id_progetto = c_id_progetto
          and b.id_domanda = a.id_domanda
          and c.progr_bando_linea_intervento = b.progr_bando_linea_intervento;
          
    CURSOR c3 (c_progr_bando_linea_intervento NUMBER,
               c_descr_breve_parametro_monit VARCHAR2) is
           select id_param_monit_bando_linea,
                  num_giorni,
                  descr_breve_template_notifica
             from PBANDI_R_PARAM_MONIT_BANDO_LIN a
             join PBANDI_C_PARAMETRI_MONIT b ON b.id_parametro_monit = a.id_parametro_monit
             join PBANDI_D_TEMPLATE_NOTIFICA c ON c.id_template_notifica = b.id_template_notifica
            where a.progr_bando_linea_intervento = c_progr_bando_linea_intervento
              and b.desc_breve_parametro_monit = c_descr_breve_parametro_monit
              and a.dt_fine_validita IS NULL;

       
   v_monit_tempi VARCHAR2(1);
   v_progr_bando_linea_intervento PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento%TYPE;
   v_id_param_monit_bando_linea PBANDI_R_PARAM_MONIT_BANDO_LIN.id_param_monit_bando_linea%TYPE;
   v_num_giorni PBANDI_R_PARAM_MONIT_BANDO_LIN.num_giorni%TYPE;
   v_id_monit_tempi_sospeso PBANDI_T_MONIT_TEMPI.id_monit_tempi%TYPE;
   
   v_subject_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_message_notifica PBANDI_T_MONIT_TEMPI.DESCRIZIONE%TYPE;
   v_descr_breve_template_notific  PBANDI_D_TEMPLATE_NOTIFICA.descr_breve_template_notifica%TYPE;
   v_codice_visualizzato  PBANDI_T_PROGETTO.codice_visualizzato%TYPE;
   v_dt_avvio_controlli        PBANDI_T_CONTROLLO_LOCO.dt_avvio_controlli%TYPE;
   v_dt_fine_controlli        PBANDI_T_CONTROLLO_LOCO.dt_fine_controlli%TYPE;
 
   
 
   
BEGIN

   -- Verifica se è attivo il monitoraggio a livello di sistema
   OPEN c1;
   FETCH c1 INTO v_monit_tempi;
   IF c1%NOTFOUND THEN
      v_monit_tempi := 'N';
   END IF;
   CLOSE c1;
   
      
   
IF :NEW.TIPO_CONTROLLI = 'L' and 
   :NEW.ID_CATEG_ANAGRAFICA = 4 Then
  
  
   -- Verifico se è attivo il monitoraggio sul Bando
   IF v_monit_tempi = 'S' THEN
      --OPEN c2 (v_id_progetto);
      OPEN c2 (:NEW.ID_PROGETTO);
      FETCH c2 INTO v_progr_bando_linea_intervento,
                    v_monit_tempi,
                    v_codice_visualizzato;
      IF  c2%NOTFOUND THEN
         v_monit_tempi := 'N';
      END IF;
      CLOSE c2;
   END IF;
   
   IF v_monit_tempi = 'S' THEN
     
     
      -- Inserimento del controllo in loco
      OPEN c3(v_progr_bando_linea_intervento,'MOT06');
      FETCH c3 INTO v_id_param_monit_bando_linea,
                    v_num_giorni,
                    v_descr_breve_template_notific;
      
      IF c3%FOUND THEN
        
      
           
         -- Valorizzazione metadati per composizione notifica in modo dinamico tramite template
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_PROGETTO', to_char(:NEW.ID_PROGETTO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('ID_CONTROLLO_LOCO', to_char(:NEW.ID_CONTROLLO));
         PCK_PBANDI_PROCESSO.PutNotificationMetadata ('DATA_AVVIO_CONTROLLI', to_char(:NEW.dt_inizio_controlli,'dd/mm/yyyy'));
         
         GetTemplateMessage(v_descr_breve_template_notific,v_subject_notifica,v_message_notifica);
      
      INSERT INTO PBANDI_T_MONIT_TEMPI
        ( ID_MONIT_TEMPI,
          ID_PROGETTO,
          DT_DECORRENZA,
          DT_FINE,
          ID_ENTITA,
          ID_TARGET,
          DESCRIZIONE,
          ID_PARAM_MONIT_BANDO_LINEA,
          NUM_GIORNI,
          ID_MONIT_TEMPI_SOSPESO)
        VALUES
         (SEQ_PBANDI_T_MONIT_TEMPI.NEXTVAL,
          :NEW.ID_PROGETTO,
          nvl (:NEW.dt_avvio_controlli,sysdate),
          :NEW.dt_fine_controlli,
          (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_CONTROLLO_LOCO_ALTRI'),
          :NEW.ID_CONTROLLO,
          v_message_notifica,
          v_id_param_monit_bando_linea,
          NULL,
          NULL);

      END IF;
      CLOSE c3;
   
END IF;

end if;
COMMIT;

END;
/

