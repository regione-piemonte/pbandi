/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DettPropostaCertifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;

public class PbandiCertificazioneDAOImpl extends PbandiDAO {
	
	public PbandiCertificazioneDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	public List<LineaDiInterventoVO> findAlberaturaLineaIntervento(
			Long idLineaIntervento) {
		getLogger().begin();
		try {
			/*
			 * Costruzione della query per la linea d'intervento di partenza
			 */
			List<LineaDiInterventoVO> result = new ArrayList<LineaDiInterventoVO>();
			StringBuilder sqlSelectLinea = new StringBuilder(
					" select t.id_linea_di_intervento ");
			StringBuilder tablesLinea = new StringBuilder(
					"PBANDI_D_LINEA_DI_INTERVENTO t ");
			sqlSelectLinea.append(" FROM ").append(tablesLinea);

			List<StringBuilder> conditionListLinea = new ArrayList<StringBuilder>();
			conditionListLinea.add(new StringBuilder(
					"t.id_linea_di_intervento = :idLineaIntervento"));

			setWhereClause(conditionListLinea, sqlSelectLinea, tablesLinea);

			/*
			 * Costruzione della query complessiva
			 */

			StringBuilder sqlSelect = new StringBuilder(
					"select PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento as idLineaDiIntervento ")
					.append(
							" ,PBANDI_D_LINEA_DI_INTERVENTO.desc_linea as descLinea")
					.append(
							" ,substr(sys_connect_by_path(PBANDI_D_LINEA_DI_INTERVENTO.desc_breve_linea,'.'),2) as descBreveCompleta");
			StringBuilder tables = new StringBuilder(
					" PBANDI_D_LINEA_DI_INTERVENTO");
			sqlSelect.append(" FROM ").append(tables);

			setWhereClause(new ArrayList<StringBuilder>(), sqlSelect, tables);

			if (idLineaIntervento != null)
				sqlSelect.append("start with id_linea_di_intervento = (")
						.append(sqlSelectLinea).append(")");
			else
				sqlSelect
						.append(" start with id_linea_di_intervento_padre is null ");
			sqlSelect
					.append(" CONNECT by prior id_linea_di_intervento = id_linea_di_intervento_padre");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idLineaIntervento", idLineaIntervento);

			result = query(sqlSelect.toString(), params,
					new LineaDInterventoVORowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}
	
	private class LineaDInterventoVORowMapper implements RowMapper<LineaDiInterventoVO> {
		public LineaDiInterventoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			return (LineaDiInterventoVO) beanMapper.toBean(rs, LineaDiInterventoVO.class);
		}
	}

	public List<DettPropostaCertifVO> findProgettiProposta(
			Long idPropostaCertificazione, Long idLineaIntervento,
			Long idProgetto, Long idBeneficiario) {
		getLogger().begin();
		logger.info("BEGIN");
		try {
			
			List<DettPropostaCertifVO> result = null;
			MapSqlParameterSource params = new MapSqlParameterSource();

			/*
			 * Costruzione della subquery per le linee di intervento
			 */
			StringBuilder sqlSelectLinea = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLinea = new StringBuilder(
					"PBANDI_D_LINEA_DI_INTERVENTO");
			sqlSelectLinea.append(" FROM ").append(tablesLinea);
			sqlSelectLinea
					.append(" START WITH id_linea_di_intervento = :idLineaIntervento "
							+ " CONNECT BY PRIOR id_linea_di_intervento = id_linea_di_intervento_padre");

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN ("
							+ sqlSelectLinea + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione,
					tablesMigrazione);

			/*
			 * Costruzione della query complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder(
				" SELECT DISTINCT pc.ID_PROPOSTA_CERTIFICAZ as idPropostaCertificaz")
				.append(" ,pc.DT_ORA_CREAZIONE as dtOraCreazione")
				.append(" ,pc.DESC_PROPOSTA as descProposta")
				.append(" ,pc.DT_CUT_OFF_PAGAMENTI as dtCutOffPagamenti")
				.append(" ,pc.DT_CUT_OFF_VALIDAZIONI as dtCutOffValidazioni")
				.append(" ,pc.DT_CUT_OFF_EROGAZIONI as dtCutOffErogazioni")
				.append(" ,pc.DT_CUT_OFF_FIDEIUSSIONI as dtCutOffFideiussioni")
				.append(" ,pc.ID_STATO_PROPOSTA_CERTIF as idStatoPropostaCertif")
				.append(" ,p.codice_visualizzato as codiceVisualizzato")
				.append(" ,p.titolo_progetto as titoloProgetto")
				.append(" ,sc.desc_breve_stato_proposta_cert as descBreveStatoPropostaCert")
				.append(" ,sc.desc_stato_proposta_certif as descStatoPropostaCertif")
				.append(" ,dc.costo_ammesso as costoAmmesso")
				.append(" ,dc.id_dett_proposta_certif as idDettPropostaCertif")
				.append(" ,dc.id_progetto as idProgetto")
				.append(" ,dc.importo_eccendenze_validazione as importoEccendenzeValidazione")
				.append(" ,dc.importo_erogazioni as importoErogazioni")
				.append(" ,dc.importo_fideiussioni as importoFideiussioni")
				.append(" ,dc.importo_pagamenti_validati as importoPagamentiValidati")
				.append(" ,dc.spesa_certificabile_lorda as spesaCertificabileLorda")
				.append(" ,PBANDI_D_LINEA_DI_INTERVENTO.desc_linea as attivita")
				.append(" ,( SELECT substr(sys_connect_by_path(v.desc_breve_linea,'.'),2) "
						+ "    FROM PBANDI_D_LINEA_DI_INTERVENTO v "
						+ "    WHERE v.id_linea_di_intervento = PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento "
						+ "    START WITH v.id_linea_di_intervento_padre is null "
						+ "    CONNECT BY prior v.id_linea_di_intervento = v.id_linea_di_intervento_padre "
						+ "  ) as descBreveCompletaAttivita ")
				.append(" ,( SELECT SUM(PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore) "
						+ "    FROM PBANDI_R_DET_PROP_CER_SOGG_FIN, "
						+ "         PBANDI_D_TIPO_SOGG_FINANZIAT "
						+ "    WHERE PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif = dc.id_dett_proposta_certif "
						+ "      AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat      = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat "
						+ "      AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat LIKE 'CPUPOR%' "
						+ "   ) as percContributoPubblico ")
				.append(",( select distinct PBANDI_R_DET_PROP_CER_SOGG_FIN.perc_tipo_sogg_finanziatore "
						+ "   from PBANDI_R_DET_PROP_CER_SOGG_FIN,  "
						+ "        PBANDI_D_TIPO_SOGG_FINANZIAT "
						+ "  where PBANDI_R_DET_PROP_CER_SOGG_FIN.id_dett_proposta_certif = dc.id_dett_proposta_certif "
						+ "    AND PBANDI_R_DET_PROP_CER_SOGG_FIN.id_tipo_sogg_finanziat      = PBANDI_D_TIPO_SOGG_FINANZIAT.id_tipo_sogg_finanziat "
						+ "    AND PBANDI_D_TIPO_SOGG_FINANZIAT.desc_breve_tipo_sogg_finanziat = 'COFPOR' "
						+ "  ) as percCofinFesr ")
				.append(", CASE "
						+ "    WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null "
						+ "    THEN (  "
						+ "           SELECT DISTINCT eg.denominazione_ente_giuridico || '  ' || sg.codice_fiscale_soggetto "
						+ "           FROM PBANDI_T_ENTE_GIURIDICO eg, "
						+ "                PBANDI_T_SOGGETTO sg "
						+ "           WHERE eg.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico "
						+ "             AND sg.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto "
						+ "         ) "
						+ "    ELSE (  "
						+ "          SELECT DISTINCT pf.cognome || ' ' || pf.nome || '  ' || sg.codice_fiscale_soggetto "
						+ "          FROM PBANDI_T_PERSONA_FISICA pf, "
						+ "               PBANDI_T_SOGGETTO sg "
						+ "          WHERE pf.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica "
						+ "            AND sg.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto "
						+ "         ) " + " END as beneficiario");

			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DETT_PROPOSTA_CERTIF dc").append(
					",PBANDI_T_PROGETTO p").append(
					",PBANDI_T_PROPOSTA_CERTIFICAZ pc").append(
					",PBANDI_D_STATO_PROPOSTA_CERTIF sc").append(
					",PBANDI_T_DOMANDA").append(
					",PBANDI_R_BANDO_LINEA_INTERVENT").append(
					",PBANDI_D_LINEA_DI_INTERVENTO").append(
					",PBANDI_R_SOGGETTO_PROGETTO").append(
					",PBANDI_D_TIPO_ANAGRAFICA").append(
					",PBANDI_D_TIPO_BENEFICIARIO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"dc.id_progetto = p.id_progetto"));
			conditionList.add(new StringBuilder(
					"pc.id_proposta_certificaz = dc.id_proposta_certificaz"));
			conditionList
					.add(new StringBuilder(
							"pc.id_stato_proposta_certif = sc.id_stato_proposta_certif"));
			conditionList.add(new StringBuilder(
					"pc.id_proposta_certificaz = :idPropostaCertificazione"));
			conditionList.add(new StringBuilder(
					"p.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_LINEA_DI_INTERVENTO.ID_LINEA_DI_INTERVENTO = PBANDI_R_BANDO_LINEA_INTERVENT.ID_LINEA_DI_INTERVENTO"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			conditionList.add(new StringBuilder(
					"p.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));

			if (idProgetto != null) {
				conditionList.add(new StringBuilder(
						" p.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto);
			}
			if (idLineaIntervento != null) {
				conditionList.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
								+ " (" + sqlSelectLinea + " UNION "
								+ sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}
			if (idBeneficiario != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idBeneficiario"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = p.id_progetto"));
				params.addValue("idBeneficiario", idBeneficiario);
			}
			sqlSelect.append(" FROM ").append(tables);
			params.addValue("idPropostaCertificazione",
					idPropostaCertificazione);
			setWhereClause(conditionList, sqlSelect, tables);

			result = query(sqlSelect.toString(), params,
					new DettPropostaCertifVOROWMapper());

			return result;
		} finally {
			logger.info("END");
			getLogger().end();
		}
	}
	
	private class DettPropostaCertifVOROWMapper implements RowMapper<DettPropostaCertifVO> {
		public DettPropostaCertifVO mapRow(ResultSet rs, int row) throws SQLException {		
			BeanMapper beanMapper = new BeanMapper();
			return (DettPropostaCertifVO) beanMapper.toBean(rs,
					DettPropostaCertifVO.class);
		}
	}

	public java.util.List<BeneficiarioVO> findAllBeneficiariProgettiProposta(
			Long idLineaIntervento, Long idProposta) {
		getLogger().begin();
		try {
			java.util.List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();

			/*
			 * Query per l' alberatura della linea di intervento
			 */
			StringBuilder sqlLineaIntervento = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLineaIntervento = new StringBuilder(
					"PBANDI_D_LINEA_DI_INTERVENTO");
			sqlLineaIntervento.append(" FROM ").append(tablesLineaIntervento);
			List<StringBuilder> conditionListLineaIntervento = new ArrayList<StringBuilder>();
			setWhereClause(conditionListLineaIntervento, sqlLineaIntervento,
					tablesLineaIntervento);
			sqlLineaIntervento
					.append(" START WITH id_linea_di_intervento = :idLineaIntervento");
			sqlLineaIntervento
					.append(" CONNECT BY prior id_linea_di_intervento = id_linea_di_intervento_padre");

			/*
			 * Select persone fisiche
			 */
			StringBuilder sqlSelectPF = new StringBuilder(
					" SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome ");
			StringBuilder tablesPF = new StringBuilder(
					"PBANDI_T_PERSONA_FISICA");
			sqlSelectPF.append(" FROM ").append(tablesPF);
			List<StringBuilder> conditionListPF = new ArrayList<StringBuilder>();
			conditionListPF
					.add(new StringBuilder(
							"PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica"));
			setWhereClause(conditionListPF, sqlSelectPF, tablesPF);

			/*
			 * Select ente giuridico
			 */
			StringBuilder sqlSelectEG = new StringBuilder(
					"SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico");
			StringBuilder tablesEG = new StringBuilder(
					"PBANDI_T_ENTE_GIURIDICO");
			sqlSelectEG.append(" FROM ").append(tablesEG);
			List<StringBuilder> conditionListEG = new ArrayList<StringBuilder>();
			conditionListEG
					.add(new StringBuilder(
							"PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico"));
			setWhereClause(conditionListEG, sqlSelectEG, tablesEG);

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN ("
							+ sqlLineaIntervento + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione,
					tablesMigrazione);

			/*
			 * Query complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto AS id_soggetto,")
					.append(
							" CASE "
									+ "  WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is not null "
									+ "  THEN (" + sqlSelectPF + ")"
									+ "  ELSE (" + sqlSelectEG + ")"
									+ " END AS descrizione, ")
					.append(
							" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO  AS codiceFiscale");
			StringBuilder tables = new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO").append(
					",PBANDI_D_TIPO_ANAGRAFICA").append(
					",PBANDI_D_TIPO_BENEFICIARIO").append(
					",PBANDI_T_DETT_PROPOSTA_CERTIF").append(
					",PBANDI_T_SOGGETTO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));

			if (idProposta != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
				params.addValue("idProposta", idProposta);
			}

			if (idLineaIntervento != null) {
				tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
				tables.append(",PBANDI_T_DOMANDA");
				tables.append(",PBANDI_T_PROGETTO");
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
				conditionList.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
								+ " (" + sqlLineaIntervento + " UNION "
								+ sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}

			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY descrizione ");

			/*
			 * Query per i beneficiari di tipo persona fisica
			 */
			// StringBuilder sqlSelectPF = new
			// StringBuilder("SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,")
			// .append("PBANDI_T_PERSONA_FISICA.cognome as cognome, ")
			// .append("PBANDI_T_PERSONA_FISICA.nome as nome,")
			// .append("PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome as descrizione,")
			// .append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale,")
			// .append("PBANDI_T_PERSONA_FISICA.DT_INIZIO_VALIDITA as dataIniziovalidita");
			// StringBuilder tablesPF = new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO")
			// .append(",PBANDI_D_TIPO_ANAGRAFICA")
			// .append(",PBANDI_D_TIPO_BENEFICIARIO")
			// .append(",PBANDI_T_PERSONA_FISICA")
			// .append(",PBANDI_T_DETT_PROPOSTA_CERTIF")
			// .append(",PBANDI_T_SOGGETTO");
			//			
			//			
			//			
			//			
			//			
			//			
			// List<StringBuilder> conditionListPF = new
			// ArrayList<StringBuilder>();
			// conditionListPF.add(new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica = PBANDI_T_PERSONA_FISICA.id_persona_fisica"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));
			//			
			// if (idProposta != null) {
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
			// params.addValue("idProposta", idProposta);
			// }
			//			
			// if (idLineaIntervento != null) {
			// tablesPF.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
			// tablesPF.append(",PBANDI_T_DOMANDA");
			// tablesPF.append(",PBANDI_T_PROGETTO");
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
			// conditionListPF.add(new
			// StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ("+sqlLineaIntervento+")"));
			// params.addValue("idLineaIntervento", idLineaIntervento);
			// }
			//			
			// sqlSelectPF.append(" FROM ").append(tablesPF);
			//			
			// setWhereClause(conditionListPF, sqlSelectPF, tablesPF);
			//			
			//			
			// /**
			// * Query per i beneficiari di tipo
			// * ente giuridico
			// */
			// StringBuilder sqlSelectEG = new
			// StringBuilder("SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,")
			// .append("null as cognome,")
			// .append("null as nome,")
			// .append("PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico as descrizione,")
			// .append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale,")
			// .append("PBANDI_T_ENTE_GIURIDICO.DT_INIZIO_VALIDITA as dataIniziovalidita");
			// StringBuilder tablesEG = new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO")
			// .append(",PBANDI_D_TIPO_ANAGRAFICA")
			// .append(",PBANDI_D_TIPO_BENEFICIARIO")
			// .append(",PBANDI_T_ENTE_GIURIDICO")
			// .append(",PBANDI_T_DETT_PROPOSTA_CERTIF")
			// .append(",PBANDI_T_SOGGETTO");
			//			
			//			
			// List<StringBuilder> conditionListEG = new
			// ArrayList<StringBuilder>();
			// conditionListEG.add(new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico = PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));
			//			
			//			
			// if (idProposta != null) {
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
			// params.addValue("idProposta", idProposta);
			// }
			//			
			// if (idLineaIntervento != null) {
			// tablesEG.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
			// tablesEG.append(",PBANDI_T_DOMANDA");
			// tablesEG.append(",PBANDI_T_PROGETTO");
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
			// conditionListEG.add(new
			// StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ("+sqlLineaIntervento+")"));
			// params.addValue("idLineaIntervento", idLineaIntervento);
			//				
			// }
			// sqlSelectEG.append(" FROM ").append(tablesEG);
			//			
			// setWhereClause(conditionListEG, sqlSelectEG, tablesEG);
			//			
			// /**
			// * Costruzione della query complessiva
			// */
			// StringBuilder sqlSelect = new StringBuilder("")
			// .append(sqlSelectPF)
			// .append(" UNION ")
			// .append(sqlSelectEG)
			// .append(" ORDER BY descrizione ");

			result = query(sqlSelect.toString(), params,
					new PbandiBeneficiariRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

	private class PbandiBeneficiariRowMapper implements RowMapper<BeneficiarioVO> {
		public BeneficiarioVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			BeneficiarioVO vo = (BeneficiarioVO) beanMapper.toBean(rs, BeneficiarioVO.class);
			return vo;
		}
	}
	
	public List<PbandiTProgettoVO> findAllProgetti(Long idProposta,
			Long idLineaIntervento, Long idBeneficiario) {
		getLogger().begin();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<PbandiTProgettoVO> result = null;
			/*
			 * Query per l' alberatura della linea di intervento
			 */
			StringBuilder sqlLineaIntervento = new StringBuilder(
					"SELECT PBANDI_D_LINEA_DI_INTERVENTO.id_linea_di_intervento");
			StringBuilder tablesLineaIntervento = new StringBuilder(
					"PBANDI_D_LINEA_DI_INTERVENTO");
			sqlLineaIntervento.append(" FROM ").append(tablesLineaIntervento);

			List<StringBuilder> conditionListLineaIntervento = new ArrayList<StringBuilder>();
			setWhereClause(conditionListLineaIntervento, sqlLineaIntervento,
					tablesLineaIntervento);

			sqlLineaIntervento
					.append(" START WITH id_linea_di_intervento = :idLineaIntervento");
			sqlLineaIntervento
					.append(" CONNECT BY prior id_linea_di_intervento = id_linea_di_intervento_padre");

			/*
			 * Select per la migrazione delle linee di intervento
			 */
			StringBuilder sqlSelectMigrazione = new StringBuilder(
					"SELECT PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_migrata");
			StringBuilder tablesMigrazione = new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING");
			sqlSelectMigrazione.append(" FROM ").append(tablesMigrazione);
			List<StringBuilder> conditionListMigrazione = new ArrayList<StringBuilder>();
			conditionListMigrazione.add(new StringBuilder(
					"PBANDI_R_LINEA_INTERV_MAPPING.id_linea_di_intervento_attuale IN ("
							+ sqlLineaIntervento + ")"));
			setWhereClause(conditionListMigrazione, sqlSelectMigrazione,
					tablesMigrazione);

			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
					.append(
							" PBANDI_T_PROGETTO.codice_progetto as codiceProgetto,")
					.append(
							" PBANDI_T_PROGETTO.codice_visualizzato as codiceVisualizzato");

			StringBuilder tables = new StringBuilder(" PBANDI_T_PROGETTO")
					.append(",PBANDI_T_DETT_PROPOSTA_CERTIF");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							" PBANDI_T_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto "));

			if (idProposta != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_T_DETT_PROPOSTA_CERTIF.id_proposta_certificaz = :idProposta"));
				params.addValue("idProposta", idProposta);
			}

			if (idLineaIntervento != null) {
				tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT");
				tables.append(",PBANDI_T_DOMANDA");
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
				conditionList.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.id_linea_di_intervento IN ( "
								+ " (" + sqlLineaIntervento + " UNION "
								+ sqlSelectMigrazione + ")" + ")"));
				params.addValue("idLineaIntervento", idLineaIntervento);
			}

			if (idBeneficiario != null) {
				tables.append(",PBANDI_R_SOGGETTO_PROGETTO");
				tables.append(",PBANDI_D_TIPO_ANAGRAFICA");
				tables.append(",PBANDI_D_TIPO_BENEFICIARIO");
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario <> 'BEN-ASSOCIATO'"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_DETT_PROPOSTA_CERTIF.id_progetto"));
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idBeneficiario"));
				params.addValue("idBeneficiario", idBeneficiario);
			}

			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);

			/*
			 * ORDER BY
			 */
			sqlSelect.append(" ORDER BY PBANDI_T_PROGETTO.codice_visualizzato ");
			result = query(sqlSelect.toString(), params, new PBandiTProgettoVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}
	}
	
	private class PBandiTProgettoVORowMapper implements RowMapper<PbandiTProgettoVO> {
		public PbandiTProgettoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			return (PbandiTProgettoVO) beanMapper.toBean(rs, PbandiTProgettoVO.class);
		}
	}

	public List<Map> findProposteSIF(BigDecimal idPropostaCert ){

		logger.debug(" BEGIN, idPropostaCert="+idPropostaCert);
		List<Map> result = null;
		
		String sqlSelect = "SELECT a.id_progetto " +
							"from PBANDI_T_PROGETTO a, "+
							" PBANDI_T_DOMANDA b, " +
							" PBANDI_R_BANDO_LINEA_INTERVENT c, " +
							" PBANDI_T_DETT_PROPOSTA_CERTIF pc " +
							"WHERE a.id_progetto = pc.id_progetto " +
							" and b.id_domanda = a.id_domanda " +
							" and b.progr_bando_linea_intervento = c.progr_bando_linea_intervento " +
							" and c.flag_sif = 'S' " +
							" and id_proposta_certificaz = :idPropostaCert " ; //; -- certificazione corrente";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idPropostaCert", idPropostaCert);
		
		result = queryForList(sqlSelect, params);
		logger.debug(" END");
		return result;
	}

}
