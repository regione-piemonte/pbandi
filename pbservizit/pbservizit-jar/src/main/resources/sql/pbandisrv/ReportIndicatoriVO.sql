/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select 
pbandi_t_progetto.ID_PROGETTO ID_PROGETTO,
DESC_TIPO_INDICATORE    tipo_indicatore,
DESC_INDICATORE         descrizione_indicatore,
COD_IGRUE               codice_indicatore,
VALORE_PROG_INIZIALE	valore_iniziale,
VALORE_CONCLUSO	        valore_finale,
VALORE_PROG_AGG	        valore_aggiornato,
FLAG_NON_APPLICABILE    non_applicabile,
CODICE_VISUALIZZATO     codice_visualizzato_progetto,
PBANDI_T_PROGETTO.TITOLO_PROGETTO titolo,
NOME_BANDO_LINEA nome_bando_linea,
 case
     when PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null 
        then (
          select distinct eg.denominazione_ente_giuridico
          from PBANDI_T_ENTE_GIURIDICO eg
          where eg.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico 
        ) else ( 
           select distinct pf.cognome || ' ' || pf.nome
           from PBANDI_T_PERSONA_FISICA pf
           where pf.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica
        )
    end denominazione_beneficiario
 from PBANDI_R_DOMANDA_INDICATORI,
PBANDI_T_PROGETTO,
PBANDI_D_INDICATORI,
PBANDI_D_TIPO_INDICATORE,
PBANDI_T_DOMANDA,
PBANDI_R_BANDO_LINEA_INTERVENT,
PBANDI_T_SOGGETTO,
PBANDI_R_SOGGETTO_PROGETTO
where PBANDI_T_PROGETTO.id_domanda=PBANDI_R_DOMANDA_INDICATORI.id_domanda
and PBANDI_R_DOMANDA_INDICATORI.ID_INDICATORI=PBANDI_D_INDICATORI.ID_INDICATORI
and PBANDI_D_INDICATORI.ID_TIPO_INDICATORE =PBANDI_D_TIPO_INDICATORE.ID_TIPO_INDICATORE
and PBANDI_T_PROGETTO.id_domanda=PBANDI_T_DOMANDA.id_domanda
and PBANDI_T_DOMANDA.progr_bando_linea_intervento=PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento
and PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto
and PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')
and nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario,'-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
and PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto

