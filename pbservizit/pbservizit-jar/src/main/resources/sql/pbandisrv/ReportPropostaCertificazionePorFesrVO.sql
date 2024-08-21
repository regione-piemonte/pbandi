/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT
	*
FROM
	(
	SELECT
		bl.desc_breve_linea desc_programma,
		bl.id_linea_di_intervento,
		tdpc.titolo_progetto,
		tdpc.importo_rendicontato,
		tdpc.contributo_pubblico_concesso,
		tdpc.costo_ammesso,
		SUBSTR(tdpc.sede_intervento, 0, INSTR(tdpc.sede_intervento, ';') - 1) comune_sede_intervento,
		SUBSTR(tdpc.sede_intervento, INSTR(tdpc.sede_intervento, ';') + 1) provincia_sede_intervento,
		(
		SELECT
			NVL2(ind.id_comune, (SELECT com.desc_comune FROM pbandi_d_comune com WHERE com.id_comune = ind.id_comune), (SELECT est.desc_comune_estero || '(' || (SELECT naz.desc_nazione FROM pbandi_d_nazione naz WHERE naz.id_nazione = est.id_nazione) || ')' FROM pbandi_d_comune_estero est WHERE est.id_comune_estero = ind.id_comune_estero))
		FROM
			pbandi_t_indirizzo ind
		WHERE
			ind.id_indirizzo = tdpc.id_indirizzo_sede_legale) comune_sede_legale,
		NVL2((SELECT id_provincia FROM pbandi_t_indirizzo ind WHERE ind.id_indirizzo = tdpc.id_indirizzo_sede_legale), (SELECT prov.sigla_provincia FROM pbandi_t_indirizzo ind, pbandi_d_provincia prov WHERE ind.id_indirizzo = tdpc.id_indirizzo_sede_legale AND ind.id_provincia = prov.id_provincia), (SELECT prov.sigla_provincia FROM pbandi_t_indirizzo ind, pbandi_d_provincia prov, pbandi_d_comune com WHERE ind.id_indirizzo = tdpc.id_indirizzo_sede_legale AND ind.id_comune = com.id_comune AND com.id_provincia = prov.id_provincia)) AS provincia_sede_legale,
		NVL2(tdpc.id_persona_fisica, (SELECT pf.cognome || ' ' || pf.nome FROM pbandi_t_persona_fisica pf WHERE pf.id_persona_fisica = tdpc.id_persona_fisica), (SELECT eg.denominazione_ente_giuridico FROM pbandi_t_ente_giuridico eg WHERE eg.id_ente_giuridico = tdpc.id_ente_giuridico)) AS beneficiario,
		CASE
			WHEN natura_cipe.DESC_NATURA_CIPE IS NOT NULL THEN natura_cipe.DESC_NATURA_CIPE
			ELSE NVL2(tdpc.id_tipo_operazione, (SELECT dtodesc.desc_tipo_operazione FROM pbandi_d_tipo_operazione dtodesc WHERE dtodesc.id_tipo_operazione = tdpc.id_tipo_operazione), NULL)
		END tipo_operazione,
		--NVL2(tdpc.id_tipo_operazione, (SELECT dtodesc.desc_tipo_operazione FROM pbandi_d_tipo_operazione dtodesc WHERE dtodesc.id_tipo_operazione = tdpc.id_tipo_operazione), null) tipo_operazione,
 		NVL2(tdpc.id_stato_progetto, (SELECT dspdesc.desc_stato_progetto FROM pbandi_d_stato_progetto dspdesc WHERE dspdesc.id_stato_progetto = tdpc.id_stato_progetto), NULL) stato_progetto,
		NVL2(tdpc.id_dimensione_impresa, (SELECT ddidesc.desc_dimensione_impresa FROM pbandi_d_dimensione_impresa ddidesc WHERE ddidesc.id_dimensione_impresa = tdpc.id_dimensione_impresa), NULL) dimensione_impresa,
		tdpc.nome_bando_linea,
		(
		SELECT
			pbandi_t_ente_competenza.desc_ente
		FROM
			pbandi_r_bando_linea_ente_comp,
			pbandi_t_ente_competenza
		WHERE
			pbandi_r_bando_linea_ente_comp.progr_bando_linea_intervento = td.progr_bando_linea_intervento
			AND pbandi_t_ente_competenza.id_ente_competenza = pbandi_r_bando_linea_ente_comp.id_ente_competenza
			AND pbandi_r_bando_linea_ente_comp.id_ruolo_ente_competenza = (
			SELECT
				id_ruolo_ente_competenza
			FROM
				pbandi_d_ruolo_ente_competenza
			WHERE
				DESC_BREVE_RUOLO_ENTE = 'RESPONSABILE')) responsabilita_Gestionale,
		(
		SELECT
			desc_breve_linea
		FROM
			(
			SELECT
				connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
				dl.id_linea_di_intervento
			FROM
				pbandi_d_linea_di_intervento dl
			CONNECT BY
				PRIOR dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre) dlr,
			(
			SELECT
				NVL((SELECT rlim.id_linea_di_intervento_attuale FROM pbandi_r_linea_interv_mapping rlim WHERE rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
				progr_bando_linea_intervento
			FROM
				pbandi_r_bando_linea_intervent) rbl,
			pbandi_d_linea_di_intervento dl,
			pbandi_d_tipo_linea_intervento dtl
		WHERE
			rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
			AND dl.id_linea_di_intervento = dlr.id_linea_radice
			AND dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
			AND dtl.livello_tipo_linea = (
			SELECT
				PBANDI_D_TIPO_LINEA_INTERVENTO.LIVELLO_TIPO_LINEA
			FROM
				pbandi_d_tipo_linea_intervento
			WHERE
				cod_tipo_linea = '02')
			AND td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento) desc_asse,
		(
		SELECT
			desc_breve_linea
		FROM
			(
			SELECT
				NVL(connect_by_root dl.id_linea_di_intervento_padre, dl.id_linea_di_intervento) id_linea_radice,
				dl.id_linea_di_intervento
			FROM
				pbandi_d_linea_di_intervento dl
			CONNECT BY
				PRIOR dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre) dlr,
			(
			SELECT
				NVL((SELECT rlim.id_linea_di_intervento_attuale FROM pbandi_r_linea_interv_mapping rlim WHERE rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
				progr_bando_linea_intervento
			FROM
				pbandi_r_bando_linea_intervent) rbl,
			pbandi_d_linea_di_intervento dl,
			pbandi_d_tipo_linea_intervento dtl
		WHERE
			rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
			AND dl.id_linea_di_intervento = dlr.id_linea_radice
			AND dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
			AND dtl.livello_tipo_linea = (
			SELECT
				PBANDI_D_TIPO_LINEA_INTERVENTO.LIVELLO_TIPO_LINEA
			FROM
				pbandi_d_tipo_linea_intervento
			WHERE
				cod_tipo_linea = '04')
			AND td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento) desc_linea,
		(
		SELECT
			desc_breve_linea
		FROM
			(
			SELECT
				connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
				dl.id_linea_di_intervento
			FROM
				pbandi_d_linea_di_intervento dl
			CONNECT BY
				PRIOR dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre) dlr,
			(
			SELECT
				NVL((SELECT rlim.id_linea_di_intervento_attuale FROM pbandi_r_linea_interv_mapping rlim WHERE rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
				progr_bando_linea_intervento
			FROM
				pbandi_r_bando_linea_intervent) rbl,
			pbandi_d_linea_di_intervento dl,
			pbandi_d_tipo_linea_intervento dtl
		WHERE
			rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
			AND dl.id_linea_di_intervento = dlr.id_linea_radice
			AND dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
			AND dtl.livello_tipo_linea = (
			SELECT
				PBANDI_D_TIPO_LINEA_INTERVENTO.LIVELLO_TIPO_LINEA
			FROM
				pbandi_d_tipo_linea_intervento
			WHERE
				cod_tipo_linea = '03')
			AND td.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento) desc_misura,
		td.progr_bando_linea_intervento,
		tpc.id_proposta_certificaz,
		tdpc.id_dett_proposta_certif,
		tpc.dt_ora_creazione,
		tpc.dt_cut_off_pagamenti,
		tpc.dt_cut_off_validazioni,
		tpc.dt_cut_off_erogazioni,
		tpc.dt_cut_off_fideiussioni,
		tpc.desc_proposta,
		tdpc.id_progetto,
		tp.codice_visualizzato,
		tp.CUP,
		CASE
			WHEN tp.id_tipo_operazione = (
			SELECT
				id_tipo_operazione
			FROM
				pbandi_d_tipo_operazione
			WHERE
				desc_breve_tipo_operazione = '03') THEN 'S'
			ELSE 'N'
		END flag_ra,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR1' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS CPUPOR1,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR2' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS CPUPOR2,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR3' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS CPUPOR3,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'COFPOR' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS COFPOR,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'OTHFIN' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS OTHFIN,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR1' THEN rdpcsf.PERC_TIPO_SOGG_FINANZ_FESR ELSE .00 END) AS CPUPOR1_FESR,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR2' THEN rdpcsf.PERC_TIPO_SOGG_FINANZ_FESR ELSE .00 END) AS CPUPOR2_FESR,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUPOR3' THEN rdpcsf.PERC_TIPO_SOGG_FINANZ_FESR ELSE .00 END) AS CPUPOR3_FESR,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUFAS-STA' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS CPUFAS_STA,
		SUM(CASE WHEN rdpcsf.DESC_BREVE_TIPO_SOGG_FINANZIAT = 'CPUFAS-REG' THEN rdpcsf.PERC_TIPO_SOGG_FINANZIATORE ELSE .00 END) AS CPUFAS_REG,
		NVL(tdpc.importo_fideiussioni, 0.00) importo_fideiussioni,
		NVL((SELECT SUM(importo_utilizzato) FROM pbandi_r_dett_prop_cer_fideius df WHERE df.id_dett_proposta_certif = tdpc.id_dett_proposta_certif), 0.00) importo_fid_utilizzate,
		NVL(tdpc.importo_pag_validati_orig, 0.00) importo_pag_validati_orig,
		NVL(tdpc.importo_pagamenti_validati, 0.00) importo_pagamenti_validati,
		NVL(tdpc.importo_eccendenze_validazione, 0.00) importo_eccendenze_validazione,
		NVL(tdpc.importo_erogazioni, 0.00) importo_erogazioni,
		NVL(tdpc.spesa_certificabile_lorda, 0.00) spesa_certificabile_lorda,
		tdpc.importo_revoche,
		tdpc.QUOTA_REVOCA_PER_IRR,
		tdpc.DT_ULTIMA_REVOCA,
		tdpc.IMPORTO_DA_RECUPERARE,
		tdpc.IMPORTO_PRERECUPERI,
		tdpc.DT_ULTIMO_PRERECUPERO,
		tdpc.IMPORTO_RECUPERI,
		tdpc.DT_ULTIMO_RECUPERO,
		tdpc.IMPORTO_SOPPRESSIONI,
		tdpc.DT_ULTIMA_SOPPRESSIONE,
		tdpc.IMPORTO_NON_RILEVANTE_CERTIF,
		tdpc.IMPORTO_CERTIFICAZIONE_NETTO,
		tdpc.IMP_CERTIF_NETTO_PREMODIFICA,
		tdpc.IDENTIFICATIVI_IRREGOLARITA,
		tdpc.EROGAZIONI_IN_MENO_PER_ERRORE,
		tdpc.FIDEIUSSIONI_IN_MENO_PER_ERROR,
		tdpc.RECUPERI_IN_MENO_PER_ERRORE,
		tdpc.SOPPRESSIONI_IN_MENO_PER_ERROR,
		tdpc.PRERECUPERI_IN_MENO_PER_ERROR,
		tdpc.REVOCHE_IN_MENO_PER_ERROR,
		tdpc.IMP_INTERESSI_RECUPERATI_NETTI,
		tdpc.IMP_CERTIFICABILE_NETTO_SOPPR,
		tdpc.IMP_REVOCHE_NETTO_SOPPRESSIONI,
		tdpc.IMP_CERTIFICABILE_NETTO_REVOC,
		tdpc.IMPORTO_REVOCHE_INTERMEDIO,
		tdpc.IMP_CERTIFICAZIONE_NETTO_PREC,
		tdpc.AVANZAMENTO,
		tdpc.IMPORTO_SOPPRESSIONI_NETTO,
		tdpc.IMPORTO_RECUPERI_PRERECUPERI,
		tdpc.IMPORTO_INTERESSI_RECUPERATI,
		tdpc.IMPORTO_CERT_NET_ANNO_PREC,
		tdpc.IMPORTO_CERT_NET_ANNO_IN_CORSO,
		azione.COD_LINEA_AZIONE,
		cod_fiscale.codice_fiscale_soggetto,
		CASE
			WHEN tdpc.FLAG_CHECK_LIST_IN_LOCO = 'S'
			AND tdpc.FLAG_CHECK_LIST_CERTIFICAZIONE = 'N' THEN 'L'
			WHEN tdpc.FLAG_CHECK_LIST_IN_LOCO = 'N'
			AND tdpc.FLAG_CHECK_LIST_CERTIFICAZIONE = 'S' THEN 'C'
			WHEN tdpc.FLAG_CHECK_LIST_IN_LOCO = 'S'
			AND tdpc.FLAG_CHECK_LIST_CERTIFICAZIONE = 'S' THEN 'L/C'
			ELSE NULL
		END flag_lc,
		tdpc.DT_ULTIMA_CHECKLIST_IN_LOCO,
		tdpc.IMPORTO_CERT_NET_ANNI_PREC,
		tdpc.VALORE_N,
		tdpc.VALORE_N_1,
		tdpc.SOMMA_VALORI_N,
	    tdpc.ID_PROGETTO_SIF
	FROM
		(
		SELECT
			desc_breve_linea,
			dl.id_linea_di_intervento,
			rbl.progr_bando_linea_intervento,
			rbl.id_linea_azione
		FROM
			(
			SELECT
				connect_by_root dl.id_linea_di_intervento_padre id_linea_radice,
				dl.id_linea_di_intervento
			FROM
				pbandi_d_linea_di_intervento dl
			CONNECT BY
				PRIOR dl.id_linea_di_intervento = dl.id_linea_di_intervento_padre) dlr,
			(
			SELECT
				NVL((SELECT rlim.id_linea_di_intervento_attuale FROM pbandi_r_linea_interv_mapping rlim WHERE rlim.id_linea_di_intervento_migrata = id_linea_di_intervento), id_linea_di_intervento) id_linea_di_intervento,
				progr_bando_linea_intervento,
				id_linea_azione
			FROM
				pbandi_r_bando_linea_intervent) rbl,
			pbandi_d_linea_di_intervento dl,
			pbandi_d_tipo_linea_intervento dtl
		WHERE
			rbl.id_linea_di_intervento = dlr.id_linea_di_intervento
			AND dl.id_linea_di_intervento = dlr.id_linea_radice
			AND dtl.id_tipo_linea_intervento = dl.id_tipo_linea_intervento
			AND dtl.livello_tipo_linea = '1') bl,
		pbandi_t_progetto tp,
		pbandi_t_domanda td,
		pbandi_t_proposta_certificaz tpc,
		(
		SELECT
			PCK_PBANDI_UTILITY_ONLINE.ReturnIndirSedeIntervento(t.id_dett_proposta_certif) sede_intervento,
			t.*
		FROM
			pbandi_t_dett_proposta_certif t) tdpc,
		(
		SELECT
			dtsf.DESC_BREVE_TIPO_SOGG_FINANZIAT,
			rdpcsf.PERC_TIPO_SOGG_FINANZIATORE,
			rdpcsf.PERC_TIPO_SOGG_FINANZ_FESR,
			rdpcsf.id_dett_proposta_certif
		FROM
			pbandi_r_det_prop_cer_sogg_fin rdpcsf,
			pbandi_d_tipo_sogg_finanziat dtsf
		WHERE
			dtsf.id_tipo_sogg_finanziat = rdpcsf.id_tipo_sogg_finanziat) rdpcsf,
		pbandi_r_det_prop_ind_sede_int rdpisi,
		pbandi_d_linea_azione azione,
		--CODICE FISCALE
(
		SELECT
			P.ID_PROGETTO,
			SOGG.CODICE_FISCALE_SOGGETTO
		FROM
			PBANDI_T_PROGETTO P,
			PBANDI_R_SOGGETTO_PROGETTO SP,
			PBANDI_T_SOGGETTO SOGG
		WHERE
			P.ID_PROGETTO = SP.ID_PROGETTO
			AND SP.ID_SOGGETTO = SOGG.ID_SOGGETTO
			AND SP.ID_TIPO_ANAGRAFICA = 1
			AND SP.ID_TIPO_BENEFICIARIO != 4) cod_fiscale,
		-- NATURA CIPE
(
		SELECT
			c.flag_sif,
			cipe.DESC_NATURA_CIPE,
			a.ID_PROGETTO,
			c.PROGR_BANDO_LINEA_INTERVENTO
		FROM
			PBANDI_T_PROGETTO a,
			PBANDI_T_DOMANDA b,
			PBANDI_R_BANDO_LINEA_INTERVENT c,
			PBANDI_T_BANDO bando,
			PBANDI_D_NATURA_CIPE cipe
		WHERE
			b.id_domanda = a.id_domanda
			AND b.progr_bando_linea_intervento = c.progr_bando_linea_intervento
			AND c.flag_sif = 'S'
			AND bando.ID_BANDO = c.ID_BANDO
			AND bando.ID_NATURA_CIPE = cipe.ID_NATURA_CIPE) natura_cipe
	WHERE
		tpc.id_proposta_certificaz = tdpc.id_proposta_certificaz
		AND tp.id_domanda = td.id_domanda
		AND rdpcsf.id_dett_proposta_certif(+) = tdpc.id_dett_proposta_certif
		AND tp.id_progetto = tdpc.id_progetto
		AND td.progr_bando_linea_intervento = bl.progr_bando_linea_intervento
		AND rdpisi.id_dett_proposta_certif(+) = rdpcsf.id_dett_proposta_certif
		AND azione.ID_LINEA_AZIONE(+) = bl.id_linea_azione
		AND cod_fiscale.id_progetto = tp.ID_PROGETTO
		AND natura_cipe.PROGR_BANDO_LINEA_INTERVENTO(+) = td.progr_bando_linea_intervento
	GROUP BY
		tpc.id_proposta_certificaz,
		td.progr_bando_linea_intervento,
		tp.codice_visualizzato,
		tpc.dt_ora_creazione,
		tpc.dt_cut_off_erogazioni,
		tpc.dt_cut_off_fideiussioni,
		tpc.dt_cut_off_pagamenti,
		tpc.dt_cut_off_validazioni,
		tpc.desc_proposta,
		tp.acronimo_progetto,
		tp.titolo_progetto,
		tp.id_tipo_operazione,
		tdpc.id_dett_proposta_certif,
		tdpc.id_progetto,
		tdpc.importo_fideiussioni,
		tdpc.importo_pag_validati_orig,
		tdpc.importo_pagamenti_validati,
		tdpc.importo_eccendenze_validazione,
		tdpc.importo_erogazioni,
		tdpc.spesa_certificabile_lorda,
		tdpc.sede_intervento,
		bl.desc_breve_linea,
		bl.id_linea_di_intervento,
		tdpc.importo_revoche,
		tdpc.QUOTA_REVOCA_PER_IRR,
		tdpc.DT_ULTIMA_REVOCA,
		tdpc.IMPORTO_DA_RECUPERARE,
		tdpc.IMPORTO_PRERECUPERI,
		tdpc.DT_ULTIMO_PRERECUPERO,
		tdpc.IMPORTO_RECUPERI,
		tdpc.DT_ULTIMO_RECUPERO,
		tdpc.IMPORTO_SOPPRESSIONI,
		tdpc.DT_ULTIMA_SOPPRESSIONE,
		tdpc.IMPORTO_NON_RILEVANTE_CERTIF,
		tdpc.IMPORTO_CERTIFICAZIONE_NETTO,
		tdpc.IMP_CERTIF_NETTO_PREMODIFICA,
		tdpc.IDENTIFICATIVI_IRREGOLARITA,
		tdpc.EROGAZIONI_IN_MENO_PER_ERRORE,
		tdpc.FIDEIUSSIONI_IN_MENO_PER_ERROR,
		tdpc.RECUPERI_IN_MENO_PER_ERRORE,
		tdpc.SOPPRESSIONI_IN_MENO_PER_ERROR,
		tdpc.PRERECUPERI_IN_MENO_PER_ERROR,
		tdpc.REVOCHE_IN_MENO_PER_ERROR,
		tdpc.FLAG_CHECK_LIST_IN_LOCO,
		tdpc.FLAG_CHECK_LIST_CERTIFICAZIONE,
		tdpc.DT_ULTIMA_CHECKLIST_IN_LOCO,
		tdpc.id_tipo_operazione,
		tdpc.id_stato_progetto,
		tdpc.id_dimensione_impresa,
		tdpc.titolo_progetto,
		tdpc.nome_bando_linea,
		tdpc.id_persona_fisica,
		tdpc.id_ente_giuridico,
		tdpc.id_indirizzo_sede_legale,
		tdpc.contributo_pubblico_concesso,
		tdpc.importo_rendicontato,
		tdpc.costo_ammesso,
		tdpc.IMP_INTERESSI_RECUPERATI_NETTI,
		tdpc.IMP_CERTIFICABILE_NETTO_SOPPR,
		tdpc.IMP_REVOCHE_NETTO_SOPPRESSIONI,
		tdpc.IMP_CERTIFICABILE_NETTO_REVOC,
		tdpc.IMPORTO_REVOCHE_INTERMEDIO,
		tdpc.IMP_CERTIFICAZIONE_NETTO_PREC,
		tdpc.AVANZAMENTO,
		tdpc.IMPORTO_SOPPRESSIONI_NETTO,
		tdpc.IMPORTO_RECUPERI_PRERECUPERI,
		tdpc.IMPORTO_INTERESSI_RECUPERATI,
		tdpc.IMPORTO_CERT_NET_ANNO_PREC,
		tdpc.IMPORTO_CERT_NET_ANNO_IN_CORSO,
		tdpc.IMPORTO_CERT_NET_ANNI_PREC,
		tdpc.VALORE_N,
		tdpc.VALORE_N_1,
		tdpc.SOMMA_VALORI_N,
		tdpc.ID_PROGETTO_SIF,
		azione.COD_LINEA_AZIONE,
		tp.CUP,
		cod_fiscale.codice_fiscale_soggetto,
		natura_cipe.DESC_NATURA_CIPE)
ORDER BY
	desc_programma,
	desc_asse,
	codice_visualizzato