/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select CODICE_FISCALE_SOGGETTO as codiceFiscaleSoggetto, PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento, DESC_BREVE_TIPO_ANAGRAFICA as descBreveTipoAnagrafica, 
		CODICE_VISUALIZZATO as codiceVisualizzato, ID_ISTANZA_PROCESSO as idIstanzaProcesso, ID_PROGETTO as idProgetto, NOME_BANDO_LINEA as nomeBandoLinea,
		BENEFICIARIO as beneficiario, ID_SOGGETTO as idSoggetto 
from (
		select p.codice_visualizzato
		      ,rsp.id_progetto
		      ,p.id_istanza_processo
		      ,bli.nome_bando_linea
		      ,bli.progr_bando_linea_intervento
		      ,rsp.id_soggetto
		      ,nvl2( rsp.id_ente_giuridico,
		              (select denominazione_ente_giuridico
		               from pbandi_t_ente_giuridico
		               where id_ente_giuridico = rsp.id_ente_giuridico),
		              ( select cognome || nome
		                from pbandi_t_persona_fisica
		                where id_persona_fisica = rsp.id_persona_fisica)
		       ) as beneficiario
		       ,s.codice_fiscale_soggetto
		       ,dta.desc_breve_tipo_anagrafica
		from pbandi_r_soggetto_progetto rsp
		     ,pbandi_t_progetto p
		     ,pbandi_t_soggetto s
		     ,pbandi_t_domanda d
		     ,pbandi_r_bando_linea_intervent bli
		     , pbandi_d_tipo_anagrafica dta
		  where nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')
		  and rsp.id_tipo_anagrafica=dta.id_tipo_anagrafica
		  and p.id_progetto = rsp.id_progetto
		  and s.id_soggetto = rsp.id_soggetto
		  and p.id_domanda = d.id_domanda
		  and d.progr_bando_linea_intervento = bli.progr_bando_linea_intervento
		  and nvl(trunc(rsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)
)
where DESC_BREVE_TIPO_ANAGRAFICA = ? and ID_SOGGETTO = ?