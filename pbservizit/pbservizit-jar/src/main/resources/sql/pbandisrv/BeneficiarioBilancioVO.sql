/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT DISTINCT
LIQ.ID_ATTO_LIQUIDAZIONE,
LIQ.ID_PROGETTO,
LIQ.DT_EMISSIONE_ATTO,
LIQ.ID_STATO_ATTO,
STATOATTO.DESC_BREVE_STATO_ATTO,
DATI.COD_MOD_PAG_BILANCIO,
DATI.ID_DATI_PAGAMENTO_ATTO,
BENB.CODICE_BENEFICIARIO_BILANCIO,
BENB.ID_BENEFICIARIO_BILANCIO,
BENB.ID_INDIRIZZO,
BENB.ID_SOGGETTO,
BENB.ID_SEDE,
SOGG.CODICE_FISCALE_SOGGETTO CODICE_FISCALE_BEN,
DENOMINAZIONE_ENTE_GIURIDICO DENOMINAZIONE,
COGNOME,
NOME,
SEDE.PARTITA_IVA PARTITA_IVA,
SEDEDATIPAG.DENOMINAZIONE RAGIONE_SOCIALE_SECONDARIA,
sededatipag.id_sede id_sede_secondaria,
dati.id_indirizzo id_indirizzo_sede_secondaria,
NVL( IND.DESC_INDIRIZZO,'') ||
NVL2(IND.CAP,' - ' || IND.CAP,'') ||
NVL2(COM.DESC_COMUNE,' - ' || COM.DESC_COMUNE,'' )||' '||
NVL2(PROV.SIGLA_PROVINCIA,' (' || PROV.SIGLA_PROVINCIA ||')','' ) ||
NVL2(COM_ESTERO.DESC_COMUNE_ESTERO,' - ' || COM_ESTERO.DESC_COMUNE_ESTERO,'' ) ||
CASE
    WHEN 
         NAZ.DESC_NAZIONE <> 'ITALIA' 
    THEN 
        ' - ' || NAZ.DESC_NAZIONE 
    ELSE ''
    END
SEDE,
NVL( IND.DESC_INDIRIZZO,'') INDIRIZZO,
IND.CAP,
COM.ID_COMUNE ID_COMUNE_SEDE,
COM_ESTERO.ID_COMUNE_ESTERO ID_COMUNE_SEDE_ESTERO,
IND.ID_NAZIONE ID_STATO_SEDE,
PROV.ID_PROVINCIA ID_PROVINCIA_SEDE,
ESTR.IBAN,
ESTR.NUMERO_CONTO,
DATI.ID_ESTREMI_BANCARI,
ESTR.ABI,
ESTR.CAB,
ESTR.CIN,
ESTR.BIC,
DATI.ID_MODALITA_EROGAZIONE,
MODA.DESC_MODALITA_EROGAZIONE MODALITA_PAGAMENTO,
RECAPITI.EMAIL,
BENB.ID_ENTE_GIURIDICO,
PFIS.ID_PERSONA_FISICA,
PFIS.DT_NASCITA,
PFIS.SESSO,
PFIS.ID_COMUNE_ITALIANO_NASCITA ID_COMUNE_NASCITA,
 CASE
    WHEN PFIS.ID_COMUNE_ITALIANO_NASCITA IS NOT NULL
    THEN
   ( SELECT ID_PROVINCIA FROM PBANDI_D_COMUNE WHERE ID_COMUNE= PFIS.ID_COMUNE_ITALIANO_NASCITA)
  END ID_PROVINCIA_NASCITA
FROM
     PBANDI_T_ATTO_LIQUIDAZIONE LIQ JOIN PBANDI_T_BENEFICIARIO_BILANCIO BENB
     ON LIQ.ID_BENEFICIARIO_BILANCIO = BENB.ID_BENEFICIARIO_BILANCIO
     JOIN PBANDI_T_SOGGETTO SOGG ON  BENB.ID_SOGGETTO=SOGG.ID_SOGGETTO
     JOIN PBANDI_D_STATO_ATTO STATOATTO ON LIQ.ID_STATO_ATTO = STATOATTO.ID_STATO_ATTO
     LEFT OUTER JOIN PBANDI_T_SEDE SEDE ON BENB.ID_SEDE=SEDE.ID_SEDE
     LEFT OUTER JOIN PBANDI_T_INDIRIZZO IND ON BENB.ID_INDIRIZZO=IND.ID_INDIRIZZO 
     LEFT OUTER JOIN PBANDI_D_COMUNE COM ON IND.ID_COMUNE=COM.ID_COMUNE
     LEFT OUTER JOIN PBANDI_D_COMUNE_ESTERO COM_ESTERO ON IND.ID_COMUNE_ESTERO=COM_ESTERO.ID_COMUNE_ESTERO
  	 LEFT OUTER JOIN PBANDI_D_NAZIONE NAZ ON IND.ID_NAZIONE=NAZ.ID_NAZIONE
     LEFT OUTER JOIN PBANDI_D_PROVINCIA PROV ON IND.ID_PROVINCIA=PROV.ID_PROVINCIA
     LEFT OUTER JOIN PBANDI_T_DATI_PAGAMENTO_ATTO DATI ON LIQ.ID_DATI_PAGAMENTO_ATTO=DATI.ID_DATI_PAGAMENTO_ATTO
     LEFT OUTER JOIN PBANDI_T_SEDE SEDEDATIPAG ON DATI.ID_SEDE=SEDEDATIPAG.ID_SEDE
     LEFT OUTER JOIN PBANDI_T_RECAPITI RECAPITI ON BENB.ID_RECAPITI=RECAPITI.ID_RECAPITI
     LEFT OUTER JOIN PBANDI_T_ESTREMI_BANCARI ESTR ON DATI.ID_ESTREMI_BANCARI=ESTR.ID_ESTREMI_BANCARI
     LEFT OUTER JOIN PBANDI_D_MODALITA_EROGAZIONE MODA ON DATI.ID_MODALITA_EROGAZIONE=MODA.ID_MODALITA_EROGAZIONE
     LEFT OUTER JOIN PBANDI_T_PERSONA_FISICA PFIS ON BENB.ID_PERSONA_FISICA=PFIS.ID_PERSONA_FISICA
     LEFT OUTER JOIN PBANDI_T_ENTE_GIURIDICO EG ON EG.ID_ENTE_GIURIDICO = BENB.ID_ENTE_GIURIDICO