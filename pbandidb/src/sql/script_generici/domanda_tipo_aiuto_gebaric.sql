/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

DECLARE
  
  cursor curDom is select rowid,PROGR_BANDO_LINEA_INTERVENTO,numero_domanda
                   from   pbandi_t_domanda
                   where  id_utente_ins = 3;
  
  nIdTipoAiuto  PBANDI_R_BANDO_LIN_TIPO_AIUTO.ID_TIPO_AIUTO%TYPE;                    
BEGIN
  for recDom in curDom loop
    -- Modify the code to initialize the variable
    -- PXML := NULL;
    begin
      select blta.ID_TIPO_AIUTO
      into   nIdTipoAiuto
      from   PBANDI_R_BANDO_LIN_TIPO_AIUTO blta
      where  blta.PROGR_BANDO_LINEA_INTERVENTO = recDom.PROGR_BANDO_LINEA_INTERVENTO;
      
      update pbandi_t_domanda
      set    ID_TIPO_AIUTO = nIdTipoAiuto
      where  rowid         = recDom.rowid;
    exception  
      when others then
        dbms_output.PUT_LINE('PROGR_BANDO_LINEA_INTERVENTO = '||recDom.PROGR_BANDO_LINEA_INTERVENTO||' '||
                             'ID_TIPO_AIUTO = '||nIdTipoAiuto||' '||
                             'numero_domanda = '||recDom.numero_domanda);  
    end;
  end loop;    
END;
