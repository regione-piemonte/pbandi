/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT *
FROM (
	select CODICE_FISCALE_BENEFICIARIO as codiceFiscaleBeneficiario , ID_SOGGETTO_BENEFICIARIO as idSoggettoBeneficiario , DENOMINAZIONE_BENEFICIARIO as denominazioneBeneficiario 
	from (
		SELECT DISTINCT m.codice_fiscale_soggetto,
		  m.id_soggetto,
		  m.id_tipo_anagrafica,
		  dta.desc_breve_tipo_anagrafica,
		  ben.id_soggetto_BENEFICIARIO,
		  ben.codice_fiscale_BENEFICIARIO,
		  ben.DENOMINAZIONE_BENEFICIARIO,
		  ben.id_progetto,
		  ben.progr_bando_linea_intervento
		FROM
		  (SELECT rsp.id_soggetto id_soggetto_beneficiario,
		    codice_fiscale_beneficiario,
		    denominazione_beneficiario,
		    rsp.id_progetto,
		    dom.progr_bando_linea_intervento
		  FROM pbandi_r_soggetto_progetto rsp,
		    (SELECT soggetto.id_soggetto,
		      soggetto.codice_fiscale_soggetto codice_fiscale_beneficiario,
		      NVL(eg.denominazione_ente_giuridico, nvl(pf.denominazione_persona_fisica, '')) AS denominazione_beneficiario
		    FROM
		      (SELECT pbandi_t_soggetto.id_soggetto,
		        pbandi_t_soggetto.codice_fiscale_soggetto,
		        pbandi_d_tipo_soggetto.desc_breve_tipo_soggetto
		      FROM pbandi_t_soggetto,
		        pbandi_d_tipo_soggetto
		      WHERE NVL(TRUNC(pbandi_d_tipo_soggetto.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
		      AND pbandi_d_tipo_soggetto.id_tipo_soggetto                                  = pbandi_t_soggetto.id_tipo_soggetto
		      ) soggetto,
		      ( SELECT DISTINCT rsp.id_soggetto,
		        first_value(teg.denominazione_ente_giuridico) over (partition BY teg.id_soggetto order by teg.dt_inizio_validita DESC, teg.id_ente_giuridico DESC) denominazione_ente_giuridico
		      FROM pbandi_t_ente_giuridico teg,
		        pbandi_r_soggetto_progetto rsp
		      WHERE rsp.id_soggetto      = teg.id_soggetto
		      AND rsp.id_ente_giuridico  = teg.id_ente_giuridico
		      AND rsp.ID_TIPO_ANAGRAFICA =
		        (SELECT dta.id_tipo_anagrafica
		        FROM pbandi_d_tipo_anagrafica dta
		        WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
		        )
		      AND NVL(rsp.id_tipo_beneficiario, '-1') <>
		        (SELECT dtb.id_tipo_beneficiario
		        FROM pbandi_d_tipo_beneficiario dtb
		        WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
		        )
		      AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
		      ) eg,
		      ( SELECT DISTINCT rsp.id_soggetto,
		        first_value(tpf.cognome
		        || ' '
		        || tpf.nome) over (partition BY tpf.id_soggetto order by tpf.dt_inizio_validita DESC, tpf.id_persona_fisica DESC) denominazione_persona_fisica
		      FROM pbandi_t_persona_fisica tpf,
		        pbandi_r_soggetto_progetto rsp
		      WHERE rsp.id_soggetto      = tpf.id_soggetto
		      AND rsp.id_persona_fisica  = tpf.id_persona_fisica
		      AND rsp.ID_TIPO_ANAGRAFICA =
		        (SELECT dta.id_tipo_anagrafica
		        FROM pbandi_d_tipo_anagrafica dta
		        WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
		        )
		      AND NVL(rsp.id_tipo_beneficiario, '-1') <>
		        (SELECT dtb.id_tipo_beneficiario
		        FROM pbandi_d_tipo_beneficiario dtb
		        WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
		        )
		      AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
		      ) pf
		    WHERE soggetto.id_soggetto = eg.id_soggetto (+)
		    AND soggetto.id_soggetto   = pf.id_soggetto (+)
		    ) benInfo,
		    pbandi_t_progetto prog,
		    pbandi_t_domanda dom
		  WHERE rsp.id_soggetto      = benInfo.id_soggetto
		  AND rsp.ID_TIPO_ANAGRAFICA =
		    (SELECT dta.id_tipo_anagrafica
		    FROM pbandi_d_tipo_anagrafica dta
		    WHERE dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO'
		    )
		  AND NVL(rsp.id_tipo_beneficiario, '-1') <>
		    (SELECT dtb.id_tipo_beneficiario
		    FROM pbandi_d_tipo_beneficiario dtb
		    WHERE dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO'
		    )
		  AND rsp.id_progetto                                     = prog.id_progetto
		  AND prog.id_domanda                                     = dom.id_domanda
		  AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)
		  ) ben,
		  ( SELECT DISTINCT tp.id_progetto,
		    ts.codice_fiscale_soggetto,
		    ts.id_soggetto,
		    ts.id_tipo_anagrafica,
		    NULL progr_soggetto_progetto
		  FROM
		    (SELECT ts1.*,
		      tec.id_ente_competenza,
		      rsta.id_tipo_anagrafica
		    FROM pbandi_t_soggetto ts1,
		      pbandi_t_ente_competenza tec,
		      pbandi_r_sogg_tipo_anagrafica rsta,
		      pbandi_d_tipo_anagrafica dta
		    WHERE rsta.id_soggetto                                   = ts1.id_soggetto
		    AND rsta.id_tipo_anagrafica = dta.id_tipo_anagrafica
		    AND dta.desc_breve_tipo_anagrafica = ?
		    AND NVL(TRUNC(rsta.dt_fine_validita), TRUNC(sysdate      +1)) > TRUNC(sysdate)
		    AND rsta.id_tipo_anagrafica NOT                         IN
		      (SELECT dta.id_tipo_anagrafica
		      FROM pbandi_d_tipo_anagrafica dta
		      WHERE dta.desc_breve_tipo_anagrafica IN ('PERSONA-FISICA', 'OI-ISTRUTTORE', 'ADG-ISTRUTTORE','ISTR-AFFIDAMENTI')
		      )
		    AND (rsta.id_tipo_anagrafica NOT IN
		      (SELECT dta.id_tipo_anagrafica
		      FROM pbandi_d_tipo_anagrafica dta
		      WHERE dta.desc_breve_tipo_anagrafica IN ('BEN-MASTER', 'OI-IST-MASTER', 'ADG-IST-MASTER')
		      )
		    OR ( NOT EXISTS
		      (SELECT 'x'
		      FROM pbandi_r_ente_competenza_sogg recs
		      WHERE recs.id_soggetto = ts1.id_soggetto
		      )
		    OR EXISTS
		      (SELECT 'x'
		      FROM pbandi_r_ente_competenza_sogg recs
		      WHERE recs.id_soggetto      = ts1.id_soggetto
		      AND recs.id_ente_competenza = tec.id_ente_competenza
		      ) ) )
		    ) ts,
		    pbandi_t_progetto tp,
		    pbandi_t_domanda td,
		    pbandi_r_bando_linea_intervent rbli,
		    pbandi_r_bando_linea_ente_comp rble
		  WHERE td.id_domanda                   = tp.id_domanda
		  AND rbli.progr_bando_linea_intervento = td.progr_bando_linea_intervento
		  AND rble.progr_bando_linea_intervento = rbli.progr_bando_linea_intervento
		  AND rble.id_ruolo_ente_competenza     =
		    (SELECT dre.id_ruolo_ente_competenza
		    FROM pbandi_d_ruolo_ente_competenza dre
		    WHERE dre.desc_breve_ruolo_ente = 'DESTINATARIO'
		    )
		  AND ts.id_ente_competenza = rble.id_ente_competenza
		  UNION
		  SELECT rsp.id_progetto id_progetto,
		    ts.codice_fiscale_soggetto,
		    ts.id_soggetto,
		    rsp.id_tipo_anagrafica,
		    rsp.progr_soggetto_progetto
		  FROM PBANDI_T_SOGGETTO ts,
		    pbandi_r_soggetto_progetto rsp,
		    pbandi_t_progetto tp
		  WHERE ts.id_soggetto                                   = rsp.id_soggetto
		  AND NVL(TRUNC(rsp.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
		  AND tp.id_progetto                                     = rsp.id_progetto
		 -- Soggetti abilitati per bando linea
		 UNION
		 SELECT   p.id_progetto,
				  ts.codice_fiscale_soggetto,
				  ts.id_soggetto,
				  sbli.id_tipo_anagrafica,
				  NULL AS progr_soggetto_progetto
		   FROM   PBANDI_R_SOGG_BANDO_LIN_INT sbli,
				  PBANDI_R_BANDO_LINEA_INTERVENT rbli,
				  PBANDI_T_SOGGETTO ts,
				  PBANDI_T_DOMANDA d,
				  PBANDI_T_PROGETTO p
		  WHERE   ts.id_soggetto = sbli.id_soggetto
				  AND d.progr_bando_linea_intervento =
						sbli.progr_bando_linea_intervento
				  AND rbli.progr_bando_linea_intervento =
						sbli.progr_bando_linea_intervento
				  AND p.id_domanda = d.id_domanda
		  ) m,
		  pbandi_d_tipo_anagrafica dta
		WHERE m.id_tipo_anagrafica            = dta.id_tipo_anagrafica
		AND ben.id_progetto                   = m.id_progetto
		AND ( dta.desc_breve_tipo_anagrafica <> 'PERSONA-FISICA'
		OR ben.id_soggetto_beneficiario      IN
		  (SELECT rsc.id_soggetto_ente_giuridico
		  FROM pbandi_r_sogg_prog_sogg_correl rspsc,
		    pbandi_r_soggetti_correlati rsc
		  WHERE rsc.progr_soggetti_correlati                     = rspsc.progr_soggetti_correlati
		  AND NVL(TRUNC(rsc.dt_fine_validita), TRUNC(sysdate+1)) > TRUNC(sysdate)
		  AND rspsc.progr_soggetto_progetto                      = m.progr_soggetto_progetto
		  AND rsc.id_tipo_soggetto_correlato                    IN
		    (SELECT rrta.id_tipo_soggetto_correlato
		    FROM pbandi_r_ruolo_tipo_anagrafica rrta
		    WHERE rrta.id_tipo_anagrafica = m.id_tipo_anagrafica
		    )
		  ) )
		  ) 
	where ID_SOGGETTO = ? and DESC_BREVE_TIPO_ANAGRAFICA = ?
	AND ((? IS NOT NULL AND ID_SOGGETTO_BENEFICIARIO = ?) OR (? IS NULL AND LOWER(DENOMINAZIONE_BENEFICIARIO) LIKE LOWER(?)))
	--parametri in ordine di questa riga
	--idBeneficiario, idBeneficiario, idBeneficiario, denominazioneBeneficiario
	group by CODICE_FISCALE_BENEFICIARIO , ID_SOGGETTO_BENEFICIARIO , DENOMINAZIONE_BENEFICIARIO 
	ORDER BY DENOMINAZIONE_BENEFICIARIO , CODICE_FISCALE_BENEFICIARIO DESC
)
WHERE ROWNUM <= 50
