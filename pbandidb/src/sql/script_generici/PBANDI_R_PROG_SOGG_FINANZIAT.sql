/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

declare
  
  cursor curDom is select dom.ID_DOMANDA,ID_PROGETTO
                   from   PBANDI_T_DOMANDA dom,PBANDI_T_PROGETTO prog
                   where  /*dom.PROGR_BANDO_LINEA_INTERVENTO = 9
                   and */   dom.ID_DOMANDA                   = prog.ID_DOMANDA;
                   

  CURSOR curContoEconom(pIdDomanda PBANDI_T_DOMANDA.ID_DOMANDA%type)
  IS SELECT ce.ID_CONTO_ECONOMICO,SUM(cema.QUOTA_IMPORTO_AGEVOLATO) Tag
                           FROM   PBANDI_T_CONTO_ECONOMICO ce,PBANDI_R_CONTO_ECONOM_MOD_AGEV cema
                           WHERE  ce.ID_DOMANDA               = pIdDomanda  
                           AND    ce.ID_STATO_CONTO_ECONOMICO = 3
                           AND    ce.DT_FINE_VALIDITA         IS NULL
                           AND    cema.ID_CONTO_ECONOMICO     = ce.ID_CONTO_ECONOMICO
                           GROUP BY ce.ID_CONTO_ECONOMICO;

  TYPE recProgSoggFinanz IS RECORD (idSoggFinanz         PBANDI_R_PROG_SOGG_FINANZIAT.ID_SOGGETTO_FINANZIATORE%TYPE,
                                    percQuotaSoggFinanz  PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE);
  
  TYPE recTbProgSoggFinanz IS TABLE OF recProgSoggFinanz INDEX BY PLS_INTEGER;
  
  vTbProgSoggFinanz  recTbProgSoggFinanz;
      
  nTam                          PBANDI_T_RIGO_CONTO_ECONOMICO.IMPORTO_AMMESSO_FINANZIAMENTO%TYPE;
  nQp                           NUMBER;
  nQu                           NUMBER;
  nQs                           NUMBER;
  nQr                           NUMBER;
  nQrA                          NUMBER;
  nPercQuotaSoggFinanzPrivato   PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPercQuotaSoggFinanzUe        PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPercQuotaSoggFinanzStato     PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPercQuotaSoggFinanzRegione   PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPercQuotaSoggFinanzRegione1  PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE := NULL;
  nPercQuotaSoggFinanzTotale    PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nPercQuotaSoggFinanzDiff      PBANDI_R_PROG_SOGG_FINANZIAT.PERC_QUOTA_SOGG_FINANZIATORE%TYPE;
  nCont                         PLS_INTEGER; 
BEGIN
  for recDom in curDom loop
  FOR recContoEconom IN curContoEconom(recDom.id_domanda) LOOP
    
    SELECT SUM(rce.IMPORTO_AMMESSO_FINANZIAMENTO) 
    INTO   nTam 
    FROM   PBANDI_T_RIGO_CONTO_ECONOMICO rce
    WHERE  rce.ID_CONTO_ECONOMICO = recContoEconom.ID_CONTO_ECONOMICO
    AND    rce.DT_FINE_VALIDITA   IS NULL;
    
    nQp := nTam - recContoEconom.Tag;
    
    IF nQp >= 0 THEN 
      nQu := ((recContoEconom.Tag*39.57)/100);
      nQs := ((recContoEconom.Tag*46.49)/100);
      nQr := ((recContoEconom.Tag*13.94)/100);
      
      nPercQuotaSoggFinanzRegione1 := NULL;
      
      DELETE PBANDI_R_PROG_SOGG_FINANZIAT
      WHERE  ID_PROGETTO              = recDom.Id_Progetto
      AND    ID_SOGGETTO_FINANZIATORE = 3;
    ELSE
      nQp  := 0;
      nQrA := recContoEconom.Tag - nTam;
      nQu := ((nTam*39.57)/100);
      nQs := ((nTam*46.49)/100);
      nQr := ((nTam*13.94)/100);
      
      begin
        nPercQuotaSoggFinanzRegione1 := ROUND(((nQrA*100)/nTam),2);
      exception
        when others then
          nPercQuotaSoggFinanzRegione1 := 0;
      end;        
    END IF;  
      
    nPercQuotaSoggFinanzPrivato  := ROUND(((nQp*100)/nTam),2);
    nPercQuotaSoggFinanzUe       := ROUND(((nQu*100)/nTam),2);
    nPercQuotaSoggFinanzStato    := ROUND(((nQs*100)/nTam),2);
    nPercQuotaSoggFinanzRegione  := ROUND(((nQr*100)/nTam),2);
          
    IF (nTam - recContoEconom.Tag) >= 0 THEN 
      nPercQuotaSoggFinanzTotale  := nPercQuotaSoggFinanzPrivato +
                                     nPercQuotaSoggFinanzUe      +
                                     nPercQuotaSoggFinanzStato   +
                                     nPercQuotaSoggFinanzRegione;
        
      IF nPercQuotaSoggFinanzTotale != 100 THEN
        nPercQuotaSoggFinanzRegione := nPercQuotaSoggFinanzRegione + (100 - nPercQuotaSoggFinanzTotale);
      END IF;
    END IF;
      
    vTbProgSoggFinanz(1).idSoggFinanz        := 13;
    vTbProgSoggFinanz(1).percQuotaSoggFinanz := nPercQuotaSoggFinanzPrivato;
    vTbProgSoggFinanz(2).idSoggFinanz        := 1;
    vTbProgSoggFinanz(2).percQuotaSoggFinanz := nPercQuotaSoggFinanzUe;
    vTbProgSoggFinanz(3).idSoggFinanz        := 2;
    vTbProgSoggFinanz(3).percQuotaSoggFinanz := nPercQuotaSoggFinanzStato;
    vTbProgSoggFinanz(4).idSoggFinanz        := 12;
    vTbProgSoggFinanz(4).percQuotaSoggFinanz := nPercQuotaSoggFinanzRegione;
    
    IF nPercQuotaSoggFinanzRegione1 IS NOT NULL THEN
      vTbProgSoggFinanz(5).idSoggFinanz        := 3;
      vTbProgSoggFinanz(5).percQuotaSoggFinanz := nPercQuotaSoggFinanzRegione1;
    END IF;  
      
    FOR i IN vTbProgSoggFinanz.FIRST..vTbProgSoggFinanz.LAST LOOP
      SELECT COUNT(*)
      INTO   nCont
      FROM   PBANDI_R_PROG_SOGG_FINANZIAT psf
      WHERE  psf.ID_PROGETTO              = recDom.Id_Progetto  
      AND    psf.ID_SOGGETTO_FINANZIATORE = vTbProgSoggFinanz(i).idSoggFinanz;
        
      IF nCont = 0 THEN
        INSERT INTO PBANDI_R_PROG_SOGG_FINANZIAT
        (ID_PROGETTO, ID_SOGGETTO_FINANZIATORE, PERC_QUOTA_SOGG_FINANZIATORE, 
         ID_UTENTE_INS)
        VALUES
        (recDom.Id_Progetto,vTbProgSoggFinanz(i).idSoggFinanz,vTbProgSoggFinanz(i).percQuotaSoggFinanz,
         2);
      ELSE
        UPDATE PBANDI_R_PROG_SOGG_FINANZIAT
        SET    PERC_QUOTA_SOGG_FINANZIATORE = vTbProgSoggFinanz(i).percQuotaSoggFinanz,
               ID_UTENTE_AGG                = 2
        WHERE  ID_PROGETTO                  = recDom.Id_Progetto
        AND    ID_SOGGETTO_FINANZIATORE     = vTbProgSoggFinanz(i).idSoggFinanz;
      END IF;    
    END LOOP;
        
  END LOOP;
  end loop;
EXCEPTION
  WHEN OTHERS THEN
    dbms_output.put_line('Errore = '||sqlerrm||' riga = '||dbms_utility.FORMAT_ERROR_BACKTRACE);  
END;                                 
