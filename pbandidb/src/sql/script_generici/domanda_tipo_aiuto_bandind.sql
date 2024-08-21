/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

DECLARE
  PXML XMLTYPE;
  PNOMETAGXML VARCHAR2(200);
  v_ReturnAc PCK_PBANDI_CARICAMENTO_XML.RECTBTAGXML;
  v_ReturnAs PCK_PBANDI_CARICAMENTO_XML.RECTBTAGXML;
  
  cursor curXml is select file_xml,numero_domanda 
                   from   PBANDI_S_CARICAMENTO_XML
                   where  fonte = 'BANDIND';
                   
  vCodIgrue                   PBANDI_D_TIPO_AIUTO.COD_IGRUE_T1%TYPE;
  nIdTipoAiuto                PBANDI_D_TIPO_AIUTO.ID_TIPO_AIUTO%TYPE; 
  nProgrBandoLineaIntervento  PBANDI_T_DOMANDA.PROGR_BANDO_LINEA_INTERVENTO%TYPE;    
  bErrore                        BOOLEAN := FALSE;  
  nCont                       pls_integer;    
BEGIN
  for recXml in curXml loop
    -- Modify the code to initialize the variable
    -- PXML := NULL;
    bErrore := FALSE;
    
    begin
      select d.PROGR_BANDO_LINEA_INTERVENTO
      into   nProgrBandoLineaIntervento
      from   pbandi_t_domanda d
      where  numero_domanda = recXml.numero_domanda
      and    id_utente_ins  = 1;
    
    PCK_PBANDI_CARICAMENTO_XML.gdoc := DBMS_XMLDOM.newDOMDocument(recXml.file_xml); 
  
    PNOMETAGXML := 'codice_tipologia_regime_di_aiuto';

   
    v_ReturnAc := PCK_PBANDI_CARICAMENTO_XML.ReturnValoreTagXml(
      PXML => recXml.file_xml,
      PNOMETAGXML => PNOMETAGXML
    );
  
    if to_char(v_ReturnAC(1).ValoreTag) in ('2','I13.5') then
      vCodIgrue := 'B';  
    elsif to_char(v_ReturnAC(1).ValoreTag) in ('1b','1a','I13.1','I13.2','I13.3') then
      vCodIgrue := 'A';
    elsif to_char(v_ReturnAC(1).ValoreTag) in ('2a','3','I13.4') then  
       vCodIgrue := 'D';
    else
      vCodIgrue := null;
    end if; 
     
    begin 
      select ID_TIPO_AIUTO
      into   nIdTipoAiuto
      from   PBANDI_D_TIPO_AIUTO
      where  COD_IGRUE_T1 = vCodIgrue;
      
      bErrore := FALSE;
    exception
      when no_data_found then
        begin
          select blta.ID_TIPO_AIUTO
          into   nIdTipoAiuto
          from   PBANDI_R_BANDO_LIN_TIPO_AIUTO blta, pbandi_t_domanda d
          where  blta.PROGR_BANDO_LINEA_INTERVENTO = d.PROGR_BANDO_LINEA_INTERVENTO
          and    numero_domanda                      = recXml.numero_domanda
          and    id_utente_ins                       = 1;
          
          bErrore := FALSE;
        exception  
          when others then
            bErrore := TRUE;
            dbms_output.PUT_LINE('PBANDI_R_BANDO_LIN_TIPO_AIUTO '||
                                 'PROGR_BANDO_LINEA_INTERVENTO = '||nProgrBandoLineaIntervento||' '||
                                 'ID_TIPO_AIUTO = '||nIdTipoAiuto||' '||
                                 'numero_domanda = '||recXml.numero_domanda);  
        end;
     end;         
     
     if not bErrore then
      update pbandi_t_domanda
      set    ID_TIPO_AIUTO  = nIdTipoAiuto
      where  numero_domanda = recXml.numero_domanda
      and    id_utente_ins  = 1
      returning PROGR_BANDO_LINEA_INTERVENTO into nProgrBandoLineaIntervento;
     
     
      select count(*)
      into   nCont
      from   PBANDI_R_BANDO_LIN_TIPO_AIUTO
      where  PROGR_BANDO_LINEA_INTERVENTO = nProgrBandoLineaIntervento
      and    ID_TIPO_AIUTO                = nIdTipoAiuto;
     
      if nCont = 0 then
        dbms_output.PUT_LINE('PROGR_BANDO_LINEA_INTERVENTO = '||nProgrBandoLineaIntervento||' '||
                               'ID_TIPO_AIUTO = '||nIdTipoAiuto||' '||
                               'numero_domanda = '||recXml.numero_domanda);
      end if;
    end if;  
    exception
      when no_data_found then
        null;
    end;        
  end loop;
END;
