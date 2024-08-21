/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BandoLineaAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CheckListBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DocPagamBandoLineaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IstruttoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IntegerValueDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.BeanRowMapper;

public class PbandiBackOfficeDAOImpl extends PbandiDAO {

	public PbandiBackOfficeDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	AbstractDataFieldMaxValueIncrementer seqpbandiRSoggettoProgetto;

	public AbstractDataFieldMaxValueIncrementer getSeqpbandiRSoggettoProgetto() {
		return seqpbandiRSoggettoProgetto;
	}

	public void setSeqpbandiRSoggettoProgetto(AbstractDataFieldMaxValueIncrementer seqpbandiRSoggettoProgetto) {
		this.seqpbandiRSoggettoProgetto = seqpbandiRSoggettoProgetto;
	}

	// CDU-14 V04

	public boolean eliminaModelloAssociato(Long progrBandoLineaIntervento, Long idTipoDocumentoIndex) {
		getLogger().begin();
		boolean esito = false;
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder(
					"DELETE FROM PBANDI_R_BL_TIPO_DOC_MICRO_SEZ " + "WHERE PROGR_BL_TIPO_DOC_SEZ_MOD IN "
							+ "(SELECT PROGR_BL_TIPO_DOC_SEZ_MOD FROM PBANDI_R_BL_TIPO_DOC_SEZ_MOD "
							+ " WHERE ID_TIPO_DOCUMENTO_INDEX = :idTipoDocumentoIndex "
							+ " AND PROGR_BANDO_LINEA_INTERVENTO = :progrBandoLineaIntervento)");
			params.addValue("idTipoDocumentoIndex", idTipoDocumentoIndex, Types.BIGINT);
			params.addValue("progrBandoLineaIntervento", progrBandoLineaIntervento, Types.BIGINT);
			elimina(sqlDelete.toString(), params);

			sqlDelete = new StringBuilder("DELETE FROM PBANDI_R_BL_TIPO_DOC_SEZ_MOD "
					+ "WHERE ID_TIPO_DOCUMENTO_INDEX = :idTipoDocumentoIndex "
					+ " AND PROGR_BANDO_LINEA_INTERVENTO = :progrBandoLineaIntervento ");
			elimina(sqlDelete.toString(), params);

			esito = true;

		} finally {
			getLogger().end();
		}
		return esito;
	}

	public List<PbandiCTipoDocumentoIndexVO> findModelliAssociati(Long progrBandoLineaIntervento) {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder(
					"select distinct c.id_tipo_documento_index as idTipoDocumentoIndex, c.desc_breve_tipo_doc_index as descBreveTipoDocIndex, c.desc_tipo_doc_index as descTipoDocIndex "
							+ "from PBANDI_R_BL_TIPO_DOC_SEZ_MOD t, pbandi_c_tipo_documento_index c, PBANDI_R_TP_DOC_IND_BAN_LI_INT tt "
							+ "where t.id_tipo_documento_index = c.id_tipo_documento_index "
							+ "and tt.id_tipo_documento_index = c.id_tipo_documento_index "
							+ "and t.progr_bando_linea_intervento= :progrBandoLineaIntervento " + "order by 1,2 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("progrBandoLineaIntervento", progrBandoLineaIntervento, Types.BIGINT);

			List<PbandiCTipoDocumentoIndexVO> result = new ArrayList<PbandiCTipoDocumentoIndexVO>();
			result = query(sqlSelect.toString(), params, new PbandiCTipoDocumentoIndexVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}
	}

	public CheckListBandoLineaDTO[] findCheckListAssociabili() {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder(
					"select distinct tp.id_tipo_documento_index as idTipoDocumentoIndex, "
							+ "mo.id_modello as idModello, mo.nome_modello as nomeModello, "
							+ "mo.versione_modello as versioneModello, "
							+ "tp.desc_breve_tipo_doc_index as descBreveTipoDocIndex, "
							+ "tp.desc_tipo_doc_index as descTipoDocIndex "
							+ "from PBANDI_R_TP_DOC_IND_BAN_LI_INT t, pbandi_c_tipo_documento_index tp, "
							+ "pbandi_c_modello mo, pbandi_r_bando_linea_intervent ril "
							+ "where t.id_tipo_documento_index = tp.id_tipo_documento_index "
							+ "and mo.id_modello = t.id_modello "
							+ "and ril.progr_bando_linea_intervento = t.progr_bando_linea_intervento "
							+ "order by mo.nome_modello ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			List<CheckListBandoLineaDTO> result = query(sqlSelect.toString(), params,
					new CheckListBandoLineaDTORowMapper());

			// Trasformo da list ad array.
			CheckListBandoLineaDTO[] result1 = new CheckListBandoLineaDTO[result.size()];
			result1 = result.toArray(result1);

			// Popolo il campo idRecord: serve come id univoco nella tabella che
			// visualizzar� i record.
			Long i = new Long(1);
			for (CheckListBandoLineaDTO item : result1) {
				item.setIdRecord(i);
				i = i + 1;
			}

			return result1;

		} finally {
			getLogger().end();
		}
	}

	public CheckListBandoLineaDTO[] findCheckListAssociate(Long progrBandoLineaIntervento) {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder("select tp.id_tipo_documento_index as idTipoDocumentoIndex, "
					+ "mo.id_modello as idModello, mo.nome_modello as nomeModello, "
					+ "mo.versione_modello as versioneModello, "
					+ "tp.desc_breve_tipo_doc_index as descBreveTipoDocIndex, "
					+ "tp.desc_tipo_doc_index as descTipoDocIndex "
					+ "from PBANDI_R_TP_DOC_IND_BAN_LI_INT t, pbandi_c_tipo_documento_index tp, "
					+ "pbandi_c_modello mo, pbandi_r_bando_linea_intervent ril "
					+ "where t.id_tipo_documento_index = tp.id_tipo_documento_index "
					+ "and mo.id_modello = t.id_modello "
					+ "and ril.progr_bando_linea_intervento = t.progr_bando_linea_intervento "
					+ "and t.progr_bando_linea_intervento = :progrBandoLineaIntervento " + "order by mo.nome_modello ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("progrBandoLineaIntervento", progrBandoLineaIntervento, Types.BIGINT);

			List<CheckListBandoLineaDTO> result = query(sqlSelect.toString(), params,
					new CheckListBandoLineaDTORowMapper());

			// Trasformo da list ad array.
			CheckListBandoLineaDTO[] result1 = new CheckListBandoLineaDTO[result.size()];
			result1 = result.toArray(result1);

			// Popolo il campo idRecord: serve come id univoco nella tabella che
			// visualizzar� i record.
			Long i = new Long(1);
			for (CheckListBandoLineaDTO item : result1) {
				item.setIdRecord(i);
				i = i + 1;
			}

			return result1;

		} finally {
			getLogger().end();
		}
	}

	public DocPagamBandoLineaDTO[] findDocPagamAssociati(Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder(
					"select tt.desc_breve_tipo_doc_spesa as descBreveTipoDocumento, "
							+ "tt.desc_tipo_documento_spesa as descTipoDocumento, "
							+ "pag.desc_breve_modalita_pagamento as descBreveModalitaPagamento, "
							+ "pag.desc_modalita_pagamento as descModalitaPagamento, "
							+ "t.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumento, "
							+ "t.ID_MODALITA_PAGAMENTO as idModalitaPagamento, "
							+ "t.PROGR_ECCEZ_BAN_LIN_DOC_PAG as progrEccezBanLinDocPag "
							+ "from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG t, Pbandi_d_Tipo_Documento_Spesa tt, " +
							// "pbandi_r_bando_linea_intervent ril, pbandi_d_modalita_pagamento pag "+
							"pbandi_d_modalita_pagamento pag "
							+ "where t.id_tipo_documento_spesa= tt.id_tipo_documento_spesa " +
							// "and ril.progr_bando_linea_intervento= t.progr_bando_linea_intervento "+
							"and t.id_modalita_pagamento = pag.id_modalita_pagamento "
							+ "and  t.progr_bando_linea_intervento = :progrBandoLineaIntervento  " + "order by 1,3 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("progrBandoLineaIntervento", progrBandoLineaIntervento, Types.BIGINT);

			List<DocPagamBandoLineaDTO> result = query(sqlSelect.toString(), params,
					new DocPagamBandoLineaDTORowMapper());

			// Trasformo da list ad array.
			DocPagamBandoLineaDTO[] result1 = new DocPagamBandoLineaDTO[result.size()];
			result1 = result.toArray(result1);

			return result1;

		} finally {
			getLogger().end();
		}
	}

	public boolean inserisciTipoDocumentoSpesa(String descrizione, String descrizioneBreve)
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder("insert into PBANDI_D_TIPO_DOCUMENTO_SPESA "
					+ "(ID_TIPO_DOCUMENTO_SPESA, DESC_BREVE_TIPO_DOC_SPESA, "
					+ " DESC_TIPO_DOCUMENTO_SPESA, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA) "
					+ "select max(ID_TIPO_DOCUMENTO_SPESA)+1, :descrizioneBreve, :descrizione, sysdate, null from PBANDI_D_TIPO_DOCUMENTO_SPESA ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("descrizioneBreve", descrizioneBreve, Types.VARCHAR);
			params.addValue("descrizione", descrizione, Types.VARCHAR);

			boolean result = inserisci(sqlSelect.toString(), params);

			return result;

		} finally {
			getLogger().end();
		}
	}

	public boolean inserisciModalitaPagamento(String descrizione, String descrizioneBreve)
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder("insert into PBANDI_D_MODALITA_PAGAMENTO "
					+ "(ID_MODALITA_PAGAMENTO, DESC_BREVE_MODALITA_PAGAMENTO, "
					+ " DESC_MODALITA_PAGAMENTO, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA) "
					+ "select max(ID_MODALITA_PAGAMENTO)+1, :descrizioneBreve, :descrizione, sysdate, null from PBANDI_D_MODALITA_PAGAMENTO ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("descrizioneBreve", descrizioneBreve, Types.VARCHAR);
			params.addValue("descrizione", descrizione, Types.VARCHAR);

			boolean result = inserisci(sqlSelect.toString(), params);

			return result;

		} finally {
			getLogger().end();
		}
	}

	public boolean eliminaDocPagamAssociato(Long progrEccezBanLinDocPag) {
		getLogger().begin();
		boolean esito = false;
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG "
					+ "where PROGR_ECCEZ_BAN_LIN_DOC_PAG = :progrEccezBanLinDocPag ");

			params.addValue("progrEccezBanLinDocPag", progrEccezBanLinDocPag, Types.BIGINT);
			elimina(sqlDelete.toString(), params);
			esito = true;

		} finally {
			getLogger().end();
		}
		return esito;
	}

	public DocPagamBandoLineaDTO[] findDocPagamAssociatiATuttiBL()
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		getLogger().begin();
		try {

			StringBuilder sqlSelect = new StringBuilder(
					"select tt.desc_breve_tipo_doc_spesa as descBreveTipoDocumento, "
							+ "tt.desc_tipo_documento_spesa as descTipoDocumento, "
							+ "pag.desc_breve_modalita_pagamento as descBreveModalitaPagamento, "
							+ "pag.desc_modalita_pagamento as descModalitaPagamento, "
							+ "t.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumento, "
							+ "t.ID_MODALITA_PAGAMENTO as idModalitaPagamento "
							+ "from PBANDI_R_TIPO_DOC_MODALITA_PAG t, Pbandi_d_Tipo_Documento_Spesa tt, "
							+ "pbandi_d_modalita_pagamento pag "
							+ "where t.id_tipo_documento_spesa= tt.id_tipo_documento_spesa "
							+ "and t.id_modalita_pagamento = pag.id_modalita_pagamento " + "order by 1,3 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			List<DocPagamBandoLineaDTO> result = query(sqlSelect.toString(), params,
					new DocPagamBandoLineaDTORowMapper());

			// Trasformo da list ad array.
			DocPagamBandoLineaDTO[] result1 = new DocPagamBandoLineaDTO[result.size()];
			result1 = result.toArray(result1);

			return result1;

		} finally {
			getLogger().end();
		}
	}

	public boolean eliminaDocPagamAssociatoATuttiBL(Long idTipoDocumento, Long idModalitaPagamento) {
		getLogger().begin();
		boolean esito = false;
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder(
					"delete from PBANDI_R_TIPO_DOC_MODALITA_PAG " + "where ID_TIPO_DOCUMENTO_SPESA = :idTipoDocumento "
							+ "  and ID_MODALITA_PAGAMENTO = :idModalitaPagamento ");

			params.addValue("idTipoDocumento", idTipoDocumento, Types.BIGINT);
			params.addValue("idModalitaPagamento", idModalitaPagamento, Types.BIGINT);

			elimina(sqlDelete.toString(), params);
			esito = true;

		} finally {
			getLogger().end();
		}
		return esito;
	}

	// CDU-14 V04 fine

	public List<IstruttoreVO> findIstruttoriSempliciForIstruttoreMaster(Long idSoggettoIstruttoreMaster,
			IstruttoreDTO filtro) {
		getLogger().begin();
		try {
			List<IstruttoreVO> result = new ArrayList<IstruttoreVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			/*
			 * Costruzione della query per la selezione dell' ente di competenza dell'
			 * istruttore MASTER
			 */
			StringBuilder sqlSelectEnte = new StringBuilder("SELECT  PBANDI_R_ENTE_COMPETENZA_SOGG.id_ente_competenza");
			StringBuilder tablesEnte = new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG");
			sqlSelectEnte.append(" FROM ").append(tablesEnte);

			List<StringBuilder> conditionListEnte = new ArrayList<StringBuilder>();
			conditionListEnte.add(new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG.id_soggetto = :idSoggettoMaster"));

			setWhereClause(conditionListEnte, sqlSelectEnte, tablesEnte);

			/*
			 * Costruzione della query per gli istruttori
			 */
			StringBuilder sqlSelectIstruttore = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_SOGGETTO.codice_fiscale_soggetto as codiceFiscale,")
					.append("PBANDI_T_PERSONA_FISICA.cognome as cognome,")
					.append("PBANDI_T_PERSONA_FISICA.nome as nome,")
					.append("PBANDI_T_SOGGETTO.id_soggetto as idSoggetto, ")
					.append("PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA  AS descBreveTipoAnagrafica");

			StringBuilder tablesIstruttore = new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG,")
					.append("PBANDI_T_SOGGETTO").append(",PBANDI_T_PERSONA_FISICA");

			List<StringBuilder> conditionListIstruttore = new ArrayList<StringBuilder>();
			conditionListIstruttore.add(
					new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_ENTE_COMPETENZA_SOGG.id_soggetto"));
			conditionListIstruttore
					.add(new StringBuilder("PBANDI_T_PERSONA_FISICA.id_soggetto = PBANDI_T_SOGGETTO.id_soggetto"));

			conditionListIstruttore.add(
					new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG.id_ente_competenza IN (" + sqlSelectEnte + " )"));
			/*
			 * Gli istruttori semplici non hanno piu' il tipo di anagrafica valorizzata su
			 * PBANDI_T_SOGGETTO
			 */

			if (filtro.getIdBando() != null) {
				tablesIstruttore.append(",PBANDI_T_PROGETTO").append(",PBANDI_T_DOMANDA")
						.append(",PBANDI_R_SOGGETTO_PROGETTO").append(",PBANDI_R_BANDO_LINEA_INTERVENT");

				conditionListIstruttore
						.add(new StringBuilder(" PBANDI_T_DOMANDA.id_domanda = PBANDI_T_PROGETTO.id_domanda"));
				conditionListIstruttore.add(
						new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto"));
				conditionListIstruttore.add(new StringBuilder(
						" PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = PBANDI_T_DOMANDA.PROGR_BANDO_LINEA_INTERVENTO"));
				conditionListIstruttore.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_bando = :idBando"));
				params.addValue("idBando", filtro.getIdBando(), Types.NUMERIC);
			}

			if (filtro.getIdProgetto() != null) {
				if (filtro.getIdBando() == null) {
					tablesIstruttore.append(",PBANDI_R_SOGGETTO_PROGETTO");
					conditionListIstruttore.add(new StringBuilder(
							" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto"));
				}
				conditionListIstruttore.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
				params.addValue("idProgetto", filtro.getIdProgetto(), Types.NUMERIC);
			}

			// if (filtro.getIdBando() == null && filtro.getIdProgetto() ==
			// null) {
			// conditionListIstruttore.add(new
			// StringBuilder("PBANDI_T_SOGGETTO.id_tipo_anagrafica is null"));
			// }

			if (filtro.getCognome() != null && filtro.getCognome().trim().length() > 0) {
				conditionListIstruttore.add(new StringBuilder(
						" UPPER(PBANDI_T_PERSONA_FISICA.cognome) LIKE '%" + filtro.getCognome().toUpperCase() + "%'"));
			}
			if (filtro.getNome() != null && filtro.getNome().trim().length() > 0) {
				conditionListIstruttore.add(new StringBuilder(
						" UPPER(PBANDI_T_PERSONA_FISICA.nome) LIKE '%" + filtro.getNome().toUpperCase() + "%'"));
			}
			/*
			 * FIX Pbandi 625: ricerca parziale per codice fiscale
			 */
			if (filtro.getCodiceFiscale() != null && filtro.getCodiceFiscale().trim().length() > 0) {
				conditionListIstruttore
						.add(new StringBuilder(" UPPER(PBANDI_T_SOGGETTO.codice_fiscale_soggetto) LIKE '%"
								+ filtro.getCodiceFiscale().toUpperCase() + "%'"));
				// params.addValue("codiceFiscale",
				// filtro.getCodiceFiscale().toUpperCase(), Types.VARCHAR);
			}

			if (filtro.getIdProgetto() != null || filtro.getIdBando() != null) {
				tablesIstruttore.append(",PBANDI_D_TIPO_ANAGRAFICA");
				conditionListIstruttore.add(new StringBuilder(
						"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
				conditionListIstruttore.add(new StringBuilder(
						"PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"));
				conditionListIstruttore.add(
						new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = PBANDI_T_SOGGETTO.id_soggetto"));
			}

			/*
			 * JIRA-1097
			 */
			String tipoAnagrafica;
			if (filtro.getDescBreveTipoAnagrafica() != null) {
				tipoAnagrafica = "'" + filtro.getDescBreveTipoAnagrafica() + "'";
			} else {
				tipoAnagrafica = "'OI-ISTRUTTORE','ADG-ISTRUTTORE'";
			}

			tablesIstruttore.append(",PBANDI_R_SOGG_TIPO_ANAGRAFICA, PBANDI_D_TIPO_ANAGRAFICA");
			conditionListIstruttore.add(new StringBuilder(
					"PBANDI_R_SOGG_TIPO_ANAGRAFICA.id_tipo_anagrafica in (select id_tipo_anagrafica from PBANDI_D_TIPO_ANAGRAFICA where desc_breve_tipo_anagrafica IN ("
							+ tipoAnagrafica + "))"));

			conditionListIstruttore.add(
					new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGG_TIPO_ANAGRAFICA.id_soggetto"));
			conditionListIstruttore.add(new StringBuilder(
					"PBANDI_R_SOGG_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA = PBANDI_D_TIPO_ANAGRAFICA.ID_TIPO_ANAGRAFICA"));

			sqlSelectIstruttore.append(" FROM ").append(tablesIstruttore);
			setWhereClause(conditionListIstruttore, sqlSelectIstruttore, tablesIstruttore);

			params.addValue("idSoggettoMaster", idSoggettoIstruttoreMaster, Types.NUMERIC);

			sqlSelectIstruttore.append(" GROUP BY (PBANDI_T_SOGGETTO.codice_fiscale_soggetto,")
					.append("PBANDI_T_PERSONA_FISICA.cognome,").append("PBANDI_T_PERSONA_FISICA.nome,")
					.append("PBANDI_T_SOGGETTO.id_soggetto, ")
					.append("PBANDI_D_TIPO_ANAGRAFICA.DESC_BREVE_TIPO_ANAGRAFICA)")
					.append(" ORDER BY PBANDI_T_PERSONA_FISICA.cognome,").append("PBANDI_T_PERSONA_FISICA.nome ");

			/*
			 * Costruzione della quey complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder("SELECT codiceFiscale as codiceFiscale,")
					.append("cognome as cognome,").append("nome as nome,").append("idSoggetto as idSoggetto")
					.append(", descBreveTipoAnagrafica as descBreveTipoAnagrafica");

			sqlSelect.append(" FROM ( ").append(sqlSelectIstruttore).append(" )");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			setWhereClause(conditionList, sqlSelect);

			result = query(sqlSelect.toString(), params, new IstruttoreVORowMapper());

			return result;
		} finally {
			getLogger().end();
		}

	}

	public List<ProgettoVO> findProgettiAssociatiIstruttore(Long idSoggettoIstruttore, boolean checkDataFineValidita,
			String descBreveTipoAnagrafica) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_progetto as idProgetto,")
					.append("PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO AS progrSoggettoProgetto,")
					.append("PBANDI_T_PROGETTO.codice_visualizzato AS codiceVisualizzato");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,").append("PBANDI_D_TIPO_ANAGRAFICA,")
					.append("PBANDI_T_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));

			if (descBreveTipoAnagrafica != null && descBreveTipoAnagrafica.length() > 0) {
				conditionList.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN (:descBreveTipoAnagrafica)"));
			} else {
				conditionList.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"));
			}
			conditionList
					.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto"));
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoIstruttore"));
			if (checkDataFineValidita)
				setWhereClause(conditionList, sqlSelect, tables);
			else
				setWhereClause(conditionList, sqlSelect);

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (descBreveTipoAnagrafica != null && descBreveTipoAnagrafica.length() > 0) {
				params.addValue("descBreveTipoAnagrafica", descBreveTipoAnagrafica, Types.VARCHAR);
			}
			params.addValue("idSoggettoIstruttore", idSoggettoIstruttore, Types.NUMERIC);

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}

	}

	public List<BandoLineaAssociatoDTO> findBandiLineaAssociatiIstruttore(Long idSoggettoIstruttore,
			boolean checkDataFineValidita, String descBreveTipoAnagrafica) {
		getLogger().begin();
		try {
			List<BandoLineaAssociatoDTO> result = new ArrayList<BandoLineaAssociatoDTO>();

			StringBuilder sqlSelect = new StringBuilder(
					"SELECT rbl.nome_bando_linea, rbl.progr_bando_linea_intervento \r\n"
							+ "FROM PBANDI_R_SOGG_BANDO_LIN_INT sbl, pbandi_r_bando_linea_intervent rbl, PBANDI_D_TIPO_ANAGRAFICA pdta \r\n"
							+ "WHERE sbl.id_soggetto = :idSoggettoIstruttore AND sbl.progr_bando_linea_intervento = rbl.progr_bando_linea_intervento\r\n"
							+ "AND sbl.ID_TIPO_ANAGRAFICA = pdta.ID_TIPO_ANAGRAFICA ");
			if (descBreveTipoAnagrafica != null && descBreveTipoAnagrafica.length() > 0) {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA IN (:descBreveTipoAnagrafica)");
			} else {
				sqlSelect.append("AND pdta.DESC_BREVE_TIPO_ANAGRAFICA IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')");
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idSoggettoIstruttore", idSoggettoIstruttore, Types.NUMERIC);
			if (descBreveTipoAnagrafica != null && descBreveTipoAnagrafica.length() > 0) {
				params.addValue("descBreveTipoAnagrafica", descBreveTipoAnagrafica, Types.VARCHAR);
			}

			result = query(sqlSelect.toString(), params, new BandoLineaAssociatoDTORowMapper());
			return result;

		} finally {
			getLogger().end();
		}

	}

	public List<ProgettoVO> verificaProgettiAssociatiIstruttore(Long idSoggettoIstruttore, List<String> idProgetti,
			boolean checkDataFineValidita) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto as idProgetto")
					.append(",PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO AS progrSoggettoProgetto")
					.append(",PBANDI_T_PROGETTO.ID_ISTANZA_PROCESSO as idIstanzaProcesso")
					.append(",PBANDI_T_PROGETTO.codice_visualizzato AS codiceVisualizzato");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,").append("PBANDI_D_TIPO_ANAGRAFICA,")
					.append("PBANDI_T_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);

			String progetti = null;
			if (idProgetti != null && idProgetti.size() > 0) {
				StringBuilder temp = new StringBuilder();
				for (String idProgetto : idProgetti)
					temp.append(idProgetto + ",");
				progetti = temp.toString().substring(0, temp.toString().lastIndexOf(","));
			}

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			conditionList.add(new StringBuilder(
					" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"));
			conditionList
					.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PBANDI_T_PROGETTO.id_progetto"));
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoIstruttore"));
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_progetto IN (" + progetti + ")"));
			if (checkDataFineValidita)
				setWhereClause(conditionList, sqlSelect, tables);
			else
				setWhereClause(conditionList, sqlSelect);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idSoggettoIstruttore", idSoggettoIstruttore, Types.NUMERIC);

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}

	}

	public List<ProgettoVO> findDettaglioProgettiAssociatiIstruttore(Long idSoggettoIstruttore,
			Boolean isIstruttoreAffidamenti) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();

			/**
			 * Costruzione della query per la ricerca degli progetti associati all'
			 * istruttore
			 */
			StringBuilder sqlSelectProgettiIstruttore = new StringBuilder(
					"SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto as idProgetto,")
					.append("PBANDI_R_SOGGETTO_PROGETTO.progr_soggetto_progetto AS progrSoggettoProgetto");

			StringBuilder tablesProgettiIstruttore = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,")
					.append("PBANDI_D_TIPO_ANAGRAFICA");
			sqlSelectProgettiIstruttore.append(" FROM ").append(tablesProgettiIstruttore);
			List<StringBuilder> conditionListIstruttori = new ArrayList<StringBuilder>();
			conditionListIstruttori.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			if (isIstruttoreAffidamenti == null || isIstruttoreAffidamenti.equals(Boolean.FALSE)) {
				conditionListIstruttori.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"));
			} else {
				conditionListIstruttori.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica LIKE 'ISTR-AFFIDAMENTI'"));
			}
			conditionListIstruttori
					.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoIstruttore"));
			setWhereClause(conditionListIstruttori, sqlSelectProgettiIstruttore, tablesProgettiIstruttore);

			/**
			 * Costruzione del case per il tipo di beneficiario
			 */
			StringBuilder sqlCase = new StringBuilder(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null ")
					.append("    THEN ( SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
					.append("           FROM PBANDI_T_ENTE_GIURIDICO")
					.append("           WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico")
					.append("         )")
					.append("    ELSE ( SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
					.append("          FROM PBANDI_T_PERSONA_FISICA")
					.append("          WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("        )").append(" END as beneficiario ");

			/**
			 * Costruzione della query complessiva.
			 */
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
					.append("PBANDI_T_PROGETTO.codice_visualizzato as codiceVisualizzato,")
					.append("PBANDI_R_BANDO_LINEA_INTERVENT.nome_bando_linea as titoloBando,")
					.append("PROGETTI_ISTRUTTORE.progrSoggettoProgetto as progrSoggettoProgetto,")
					// .append("ISTRUTTORI.numeroIstuttoriAssociati as numeroIstruttoriAssociati,")
					// .append("count(*) as numeroIstuttoriAssociati,")
					.append(sqlCase);
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO, ").append("PBANDI_D_TIPO_ANAGRAFICA,")
					.append("PBANDI_D_TIPO_BENEFICIARIO,").append("PBANDI_T_PROGETTO,").append("PBANDI_T_DOMANDA,")
					.append("PBANDI_R_BANDO_LINEA_INTERVENT,").append("PBANDI_T_BANDO,")
					.append("(" + sqlSelectProgettiIstruttore + ") PROGETTI_ISTRUTTORE");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList
					.add(new StringBuilder("PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList.add(new StringBuilder("PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario <> 4"));
			conditionList
					.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
			conditionList.add(new StringBuilder("PBANDI_T_DOMANDA.id_domanda = PBANDI_T_PROGETTO.id_domanda"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			conditionList.add(new StringBuilder("PBANDI_T_BANDO.id_bando = PBANDI_R_BANDO_LINEA_INTERVENT.id_bando"));
			conditionList
					.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_progetto = PROGETTI_ISTRUTTORE.idProgetto"));

			setWhereClause(conditionList, sqlSelect, tables);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idSoggettoIstruttore", idSoggettoIstruttore, Types.NUMERIC);

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<BandoVO> findBandi() {
		getLogger().begin();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<BandoVO> result = new ArrayList<BandoVO>();
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_R_BANDO_LINEA_INTERVENT.ID_BANDO as idBando,")
					.append("PBANDI_R_BANDO_LINEA_INTERVENT.nome_bando_linea as titoloBando");

			StringBuilder tables = new StringBuilder("PBANDI_R_BANDO_LINEA_INTERVENT, PBANDI_T_BANDO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" PBANDI_T_BANDO.id_bando = PBANDI_R_BANDO_LINEA_INTERVENT.id_bando"));
			setWhereClause(conditionList, sqlSelect, tables);
			result = query(sqlSelect.toString(), params, new BandoVORowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<ProgettoVO> findProgettiByBandoLinea(Long idBandoLinea) {
		getLogger().begin();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
					.append("PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato");
			StringBuilder tables = new StringBuilder("PBANDI_R_BANDO_LINEA_INTERVENT,").append("PBANDI_T_DOMANDA,")
					.append("PBANDI_T_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList.add(
					new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLinea"));

			setWhereClause(conditionList, sqlSelect);

			params.addValue("idBandoLinea", idBandoLinea, Types.NUMERIC);
			sqlSelect.append(" ORDER BY PBANDI_T_PROGETTO.CODICE_VISUALIZZATO");

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<BeneficiarioVO> findBeneficiariProgetto(Long idProgetto, Long idBandoLinea,
			Long idSoggettoBeneficiario) {
		getLogger().begin();
		try {
			List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			// params.addValue("idBando", filtro.getIdBando(), Types.NUMERIC);
			/**
			 * Costruzione del case per il beneficiario
			 */
			StringBuilder sqlCase = new StringBuilder(" CASE ")
					.append("WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null ")
					.append("   THEN ( SELECT DISTINCT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
					.append("          FROM PBANDI_T_ENTE_GIURIDICO")
					.append("          WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico")
					.append("         )")
					.append("    ELSE ( SELECT DISTINCT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
					.append("           FROM PBANDI_T_PERSONA_FISICA")
					.append("           WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("          )").append("END as beneficiario");

			/**
			 * Costruzione della query
			 */
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as idSoggettoBeneficiario,")
					.append(sqlCase);

			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,").append("PBANDI_D_TIPO_ANAGRAFICA,")
					.append("PBANDI_D_TIPO_BENEFICIARIO,").append("PBANDI_T_PROGETTO");
			// .append("PBANDI_T_DOMANDA,")
			// .append("PBANDI_R_BANDO_LINEA_INTERVENT,")
			// .append(" PBANDI_T_BANDO");
			// sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList
					.add(new StringBuilder("PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList.add(new StringBuilder("PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario <> 4"));
			conditionList
					.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
			if (idBandoLinea != null) {
				tables.append(",PBANDI_T_DOMANDA,").append("PBANDI_R_BANDO_LINEA_INTERVENT ");// .append(",
																								// PBANDI_T_BANDO");

				conditionList.add(new StringBuilder("PBANDI_T_DOMANDA.id_domanda = PBANDI_T_PROGETTO.id_domanda"));
				conditionList.add(new StringBuilder(
						"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
//				conditionList
//						.add(new StringBuilder(
//								"PBANDI_T_BANDO.id_bando = PBANDI_R_BANDO_LINEA_INTERVENT.id_bando"));
//				conditionList.add(new StringBuilder(
//						"PBANDI_T_BANDO.id_bando = :idBando"));
				conditionList.add(new StringBuilder(
						"PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLinea"));

				params.addValue("idBandoLinea", idBandoLinea, Types.NUMERIC);

			}
			if (idProgetto != null) {
				conditionList.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}

			if (idSoggettoBeneficiario != null) {
				conditionList
						.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoBeneficiario"));
				params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.NUMERIC);
			}
			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY 2");
			result = query(sqlSelect.toString(), params, new BeneficiarioVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}
	}

	public List<ProgettoVO> findProgettiByBeneficiarioBando(Long idSoggettoBeneficiario, Long idBandoLinea) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idBandoLinea", idBandoLinea, Types.NUMERIC);
			params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.NUMERIC);
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
					.append("PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato");

			StringBuilder tables = new StringBuilder("PBANDI_R_BANDO_LINEA_INTERVENT,").append("PBANDI_T_DOMANDA,")
					.append("PBANDI_T_PROGETTO, PBANDI_R_SOGGETTO_PROGETTO, PBANDI_D_TIPO_ANAGRAFICA, PBANDI_D_TIPO_BENEFICIARIO ");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList.add(
					new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLinea"));
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList
					.add(new StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'BENEFICIARIO'"));
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario"));
			conditionList.add(new StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario <> 4"));
			conditionList
					.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto"));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoBeneficiario"));

			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY PBANDI_T_PROGETTO.CODICE_VISUALIZZATO");
			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}
	}

	public List<ProgettoVO> findProgettiDaAssociare(Long idBandoLinea, Long idProgetto, boolean isIstruttoriAssociati,
			Boolean isIstruttoreAffidamenti) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();
			// /**
			// * COSTRUZIONE QUERY PER I PROGETTI NON ASSOCIATI
			// */
			// StringBuilder sqlSelectProgettiNonAssociati = new
			// StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
			// .append(" PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato,")
			// .append("0 as numProgettiAssociati");
			// StringBuilder tablesProgettiNonAssociati = new
			// StringBuilder("PBANDI_T_PROGETTO");
			// sqlSelectProgettiNonAssociati.append(" FROM
			// ").append(tablesProgettiNonAssociati);
			// List<StringBuilder> conditionListProgettiNonAssociati = new
			// ArrayList<StringBuilder>();
			// conditionListProgettiNonAssociati.add(new
			// StringBuilder("PBANDI_T_PROGETTO.id_progetto NOT IN ( " +
			// " SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto " +
			// " FROM PBANDI_R_SOGGETTO_PROGETTO," +
			// " PBANDI_D_TIPO_ANAGRAFICA" +
			// " WHERE PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica =
			// PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"
			// +
			// " AND PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN
			// ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"
			// +
			// " ) "));
			// conditionListProgettiNonAssociati.add(new
			// StringBuilder("PBANDI_T_PROGETTO.id_progetto IN ( " +
			// " SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto " +
			// " FROM PBANDI_R_SOGGETTO_PROGETTO," +
			// " PBANDI_D_TIPO_ANAGRAFICA" +
			// " WHERE PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica =
			// PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"
			// +
			// " AND PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN
			// ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"
			// +
			// " AND PBANDI_R_SOGGETTO_PROGETTO.DT_FINE_VALIDITA is not null) "));
			//
			//
			// setWhereClause(conditionListProgettiNonAssociati,
			// sqlSelectProgettiNonAssociati,tablesProgettiNonAssociati);
			//
			// /**
			// * COSTRUZIONE QUERY PER I PROGETTI ASSOCIATI
			// */
			// StringBuilder sqlSelectProgettiAssociati = new
			// StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
			// .append(" PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato,")
			// .append("count(*) as numProgettiAssociati");
			// StringBuilder tablesProgettiAssociati = new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,")
			// .append("PBANDI_T_PROGETTO,")
			// .append("PBANDI_D_TIPO_ANAGRAFICA");
			// sqlSelectProgettiAssociati.append(" FROM ").append(tablesProgettiAssociati);
			// List<StringBuilder> conditionListProgettiAssociati = new
			// ArrayList<StringBuilder>();
			// conditionListProgettiAssociati.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_progetto =
			// PBANDI_R_SOGGETTO_PROGETTO.id_progetto "));
			// conditionListProgettiAssociati.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica =
			// PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			// conditionListProgettiAssociati.add(new
			// StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN
			// ('OI-ISTRUTTORE','ADG-ISTRUTTORE') "));
			//
			// StringBuilder tablesProgettiAssociatiNonControlloData = new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO");
			// setWhereClause(conditionListProgettiAssociati,
			// sqlSelectProgettiAssociati,tablesProgettiAssociati,tablesProgettiAssociatiNonControlloData);
			//
			// sqlSelectProgettiAssociati.append(" GROUP BY PBANDI_T_PROGETTO.id_progetto,
			// PBANDI_T_PROGETTO.CODICE_VISUALIZZATO ");
			//
			// /**
			// * QUERY PER I PROGETTI ( ASSOCIATI E NON ASSOCIATI )
			// */
			// StringBuilder sqlSelectProgetti = sqlSelectProgettiNonAssociati;
			// if (isIstruttoriAssociati) {
			// sqlSelectProgetti
			// .append(" UNION ")
			// .append(sqlSelectProgettiAssociati);
			// }
			//
			// /**
			// * COSTRUZIONE QUERY PER I BENEFICIARI
			// */
			// StringBuilder sqlSelectBeneficiari = new
			// StringBuilder(" SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto as idProgetto,
			// ")
			// .append(" CASE ")
			// .append(" WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica is null ")
			// .append(" THEN ( SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico
			// ")
			// .append(" FROM PBANDI_T_ENTE_GIURIDICO ")
			// .append(" WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico =
			// PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico ")
			// .append(" ) ")
			// .append(" ELSE ( SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' ||
			// PBANDI_T_PERSONA_FISICA.nome ")
			// .append(" FROM PBANDI_T_PERSONA_FISICA ")
			// .append(" WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica =
			// PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica ")
			// .append(" ) ")
			// .append(" END as beneficiario,")
			// .append("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as idSoggettoBeneficiario ");
			// StringBuilder tablesBeneficiari = new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,")
			// .append("PBANDI_D_TIPO_ANAGRAFICA,")
			// .append("PBANDI_D_TIPO_BENEFICIARIO");
			// sqlSelectBeneficiari.append(" FROM ").append(tablesBeneficiari);
			// List<StringBuilder> conditionListBeneficiari = new
			// ArrayList<StringBuilder>();
			// conditionListBeneficiari.add(new
			// StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica =
			// PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			// conditionListBeneficiari.add(new
			// StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica =
			// 'BENEFICIARIO' "));
			// conditionListBeneficiari.add(new
			// StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario =
			// PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario "));
			// conditionListBeneficiari.add(new
			// StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario IN
			// ('BEN-CAPOFILA','BEN-SINGOLO','BEN-PARTNER','BEN-PROPONENTE') "));
			// setWhereClause(conditionListBeneficiari,
			// sqlSelectBeneficiari,tablesBeneficiari);
			//
			// /**
			// * COSTRUZIONE DELLA QUERY COMPLESSIVA
			// */
			// StringBuilder sqlSelect = new
			// StringBuilder("SELECT progetti.idProgetto as idProgetto")
			// .append(",beneficiari.beneficiario as beneficiario")
			// .append(",progetti.codiceVisualizzato as codiceVisualizzato")
			// .append(",sum(progetti.numProgettiAssociati) as numeroIstruttoriAssociati");
			// StringBuilder tables = new StringBuilder(" ");
			// List<StringBuilder> conditionList = new
			// ArrayList<StringBuilder>();
			// MapSqlParameterSource params = new MapSqlParameterSource();
			// StringBuilder groupBy = new
			// StringBuilder(" GROUP BY (progetti.idProgetto,"
			// +" beneficiari.beneficiario, progetti.codiceVisualizzato");
			//
			// if (idBando != null) {
			// sqlSelect.append(",PBANDI_R_BANDO_LINEA_INTERVENT.nome_bando_linea as
			// titoloBando ");
			// tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT")
			// .append(",PBANDI_T_DOMANDA ")
			// .append(",PBANDI_T_PROGETTO")
			// .append(",PBANDI_T_BANDO");
			// conditionList.add(new
			// StringBuilder("PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento =
			// PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			// conditionList.add(new
			// StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_bando
			// =PBANDI_T_BANDO.id_bando"));
			// conditionList.add(new
			// StringBuilder(" PBANDI_T_BANDO.id_bando = :idBando"));
			// conditionList.add(new
			// StringBuilder("PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			// conditionList.add(new
			// StringBuilder("PBANDI_T_PROGETTO.id_progetto = progetti.idProgetto"));
			// params.addValue("idBando", idBando, Types.NUMERIC);
			// groupBy.append(",PBANDI_R_BANDO_LINEA_INTERVENT.nome_bando_linea");
			// }
			// if (idProgetto != null) {
			// conditionList.add(new
			// StringBuilder(" PBANDI_T_PROGETTO.id_progetto = :idProgetto "));
			// params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			// }
			// if (idSoggettoBeneficiario != null) {
			// conditionList.add(new
			// StringBuilder(" beneficiari.idSoggettoBeneficiario = :idSoggettoBeneficiario
			// "));
			// params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario,
			// Types.NUMERIC);
			// }
			//
			//
			// sqlSelect.append(" FROM ")
			// .append("(" +sqlSelectProgetti+" ) progetti,")
			// .append("( "+sqlSelectBeneficiari+" ) beneficiari")
			// .append(tables);
			// conditionList.add(new
			// StringBuilder(" progetti.idProgetto = beneficiari.idProgetto(+)"));
			// setWhereClause(conditionList, sqlSelect,tables);
			// groupBy.append(")");
			// sqlSelect.append(groupBy);
			//
			// result = query(sqlSelect.toString(), params, new
			// ProgettoVORowMapper());

			/*
			 * Query per i progetti non associati
			 */
			StringBuilder sqlSelectProgettiAssociati = new StringBuilder(
					"SELECT PBANDI_R_SOGGETTO_PROGETTO.id_progetto");
			StringBuilder tablesProgettiAssociati = new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO,PBANDI_D_TIPO_ANAGRAFICA");
			sqlSelectProgettiAssociati.append(" FROM ").append(tablesProgettiAssociati);
			List<StringBuilder> conditionListProgettiAssociati = new ArrayList<StringBuilder>();
			conditionListProgettiAssociati.add(new StringBuilder(
					"PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			if (isIstruttoreAffidamenti == null || isIstruttoreAffidamenti.equals(Boolean.FALSE)) {
				conditionListProgettiAssociati.add(new StringBuilder(
						"PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE')"));
			} else {
				conditionListProgettiAssociati.add(new StringBuilder(
						"PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica LIKE 'ISTR-AFFIDAMENTI'"));
			}
			setWhereClause(conditionListProgettiAssociati, sqlSelectProgettiAssociati, tablesProgettiAssociati);

			/*
			 * Query per i progetti associati
			 */
			StringBuilder sqlSelectProgettiNonAssociati = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto");
			StringBuilder tablesProgettiNonAssociati = new StringBuilder("PBANDI_T_PROGETTO");
			sqlSelectProgettiNonAssociati.append(" FROM ").append(tablesProgettiNonAssociati);
			List<StringBuilder> conditionListProgettiNonAssociati = new ArrayList<StringBuilder>();
			conditionListProgettiNonAssociati.add(
					new StringBuilder("PBANDI_T_PROGETTO.id_progetto NOT IN (" + sqlSelectProgettiAssociati + ")"));
			setWhereClause(conditionListProgettiNonAssociati, sqlSelectProgettiNonAssociati,
					tablesProgettiNonAssociati);

			/*
			 * Query complessiva
			 */
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto")
					.append(",PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato");
			StringBuilder tables = new StringBuilder("PBANDI_T_PROGETTO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			if (isIstruttoriAssociati)
				conditionList.add(
						new StringBuilder("PBANDI_T_PROGETTO.id_progetto IN (" + sqlSelectProgettiAssociati + ")"));
			else
				conditionList.add(
						new StringBuilder("PBANDI_T_PROGETTO.id_progetto IN (" + sqlSelectProgettiNonAssociati + ")"));

			if (idBandoLinea != null) {
				sqlSelect.append(",PBANDI_R_BANDO_LINEA_INTERVENT.nome_bando_linea as titoloBando ");
				tables.append(",PBANDI_R_BANDO_LINEA_INTERVENT").append(",PBANDI_T_DOMANDA ");// .append(",PBANDI_T_BANDO");
				conditionList.add(new StringBuilder(
						"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
//				conditionList
//						.add(new StringBuilder(
//								" PBANDI_R_BANDO_LINEA_INTERVENT.id_bando =PBANDI_T_BANDO.id_bando"));
//				conditionList.add(new StringBuilder(
//						" PBANDI_T_BANDO.id_bando = :idBando"));
				conditionList.add(new StringBuilder(
						"PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLinea"));
				conditionList.add(new StringBuilder("PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
				params.addValue("idBandoLinea", idBandoLinea, Types.NUMERIC);
			}

			if (idProgetto != null) {
				conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = :idProgetto "));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}
			sqlSelect.append(" FROM ").append(tables);
			setWhereClause(conditionList, sqlSelect, tables);

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());
			return result;

		} finally {
			getLogger().end();
		}
	}

	public List<IstruttoreVO> findIstruttoriAssociatiAProgetto(Long idSoggettoMaster, Long idBandoLinea,
			Long idProgetto, Boolean isIstruttoreAffidamenti) {
		getLogger().begin();
		try {
			List<IstruttoreVO> result = new ArrayList<IstruttoreVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idSoggettoMaster", idSoggettoMaster, Types.NUMERIC);
			/**
			 * Costruzione della query per la selezione dell' ente di competenza dell'
			 * istruttore MASTER
			 */
			StringBuilder sqlSelectEnte = new StringBuilder("SELECT  PBANDI_R_ENTE_COMPETENZA_SOGG.id_ente_competenza");
			StringBuilder tablesEnte = new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG");
			sqlSelectEnte.append(" FROM ").append(tablesEnte);

			List<StringBuilder> conditionListEnte = new ArrayList<StringBuilder>();
			conditionListEnte.add(new StringBuilder("PBANDI_R_ENTE_COMPETENZA_SOGG.id_soggetto = :idSoggettoMaster"));

			setWhereClause(conditionListEnte, sqlSelectEnte, tablesEnte);

			/**
			 * COSTRUZIONE DELLA QUERY COMPLESSIVA
			 */
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto ")
					.append(",PBANDI_T_BANDO.id_bando as idBando ")
					.append(",PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as idSoggetto ")
					.append(",PBANDI_T_PERSONA_FISICA.cognome as cognome")
					.append(",PBANDI_T_PERSONA_FISICA.nome as nome")
					.append(",PBANDI_T_SOGGETTO.codice_fiscale_soggetto as codiceFiscale");
			StringBuilder tables = new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO ").append(",PBANDI_T_PROGETTO ")
					.append(",PBANDI_D_TIPO_ANAGRAFICA ").append(",PBANDI_R_BANDO_LINEA_INTERVENT ")
					.append(",PBANDI_T_DOMANDA ").append(",PBANDI_T_PERSONA_FISICA ").append(",PBANDI_T_BANDO")
					.append(",PBANDI_R_ENTE_COMPETENZA_SOGG").append(",PBANDI_T_SOGGETTO");

			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = PBANDI_R_SOGGETTO_PROGETTO.id_progetto "));
			conditionList.add(new StringBuilder(
					" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			if (isIstruttoreAffidamenti == null || isIstruttoreAffidamenti.equals(Boolean.FALSE)) {
				conditionList.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica  IN ('OI-ISTRUTTORE','ADG-ISTRUTTORE') "));
			} else {
				conditionList.add(new StringBuilder(
						" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica  LIKE 'ISTR-AFFIDAMENTI' "));
			}

			conditionList.add(new StringBuilder(
					" PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento "));
			conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_INTERVENT.id_bando = PBANDI_T_BANDO.id_bando "));
			conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda "));
			conditionList.add(new StringBuilder(
					" PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica "));
			conditionList.add(
					new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_ENTE_COMPETENZA_SOGG.id_soggetto "));
			conditionList
					.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = PBANDI_T_SOGGETTO.id_soggetto "));
			conditionList.add(new StringBuilder(
					" PBANDI_R_ENTE_COMPETENZA_SOGG.id_ente_competenza IN ( " + sqlSelectEnte + " )"));

			if (idBandoLinea != null) {
//				conditionList.add(new StringBuilder(
//						" PBANDI_T_BANDO.id_bando = :idBando "));
//				params.addValue("idBando", idBando, Types.NUMERIC);
				conditionList.add(new StringBuilder(
						"PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO = :idBandoLinea"));
				params.addValue("idBandoLinea", idBandoLinea, Types.NUMERIC);
			}
			if (idProgetto != null) {
				conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = :idProgetto "));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}

			setWhereClause(conditionList, sqlSelect, tables);
			result = query(sqlSelect.toString(), params, new IstruttoreVORowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<IstruttoreVO> findDettaglioIstruttore(Long idSoggettoIstruttore) {
		getLogger().begin();
		try {
			List<IstruttoreVO> result = new ArrayList<IstruttoreVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PERSONA_FISICA.cognome as cognome,")
					.append("PBANDI_T_PERSONA_FISICA.nome as nome,")
					.append("PBANDI_T_SOGGETTO.codice_fiscale_soggetto as codiceFiscale,")
					.append("PBANDI_T_PERSONA_FISICA.id_persona_fisica as idPersonaFisica,")
					.append("PBANDI_T_SOGGETTO.id_soggetto as idSoggetto");

			StringBuilder tables = new StringBuilder("PBANDI_T_SOGGETTO,").append("PBANDI_T_PERSONA_FISICA");

			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto = PBANDI_T_PERSONA_FISICA.id_soggetto"));
			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = :idSoggettoIstruttore"));
			params.addValue("idSoggettoIstruttore", idSoggettoIstruttore, Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect);

			result = query(sqlSelect.toString(), params, new IstruttoreVORowMapper());

			return result;

		} finally {
			getLogger().end();
		}
	}

	public ProgettoVO findProgettoByProgressivoSoggettoProgetto(Long progrSoggettoProgetto, boolean checkDataFine) {
		getLogger().begin();
		try {
			ProgettoVO result = new ProgettoVO();
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto")
					.append(",PBANDI_T_PROGETTO.ID_ISTANZA_PROCESSO as idIstanzaProcesso")
					.append(",PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO, PBANDI_T_PROGETTO");

			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder("PBANDI_T_PROGETTO.ID_PROGETTO = PBANDI_R_SOGGETTO_PROGETTO.ID_PROGETTO"));
			conditionList.add(
					new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.PROGR_SOGGETTO_PROGETTO = :progrSoggettoProgetto"));

			if (checkDataFine)
				setWhereClause(conditionList, sqlSelect, tables);
			else
				setWhereClause(conditionList, sqlSelect);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("progrSoggettoProgetto", progrSoggettoProgetto, Types.NUMERIC);

			result = queryForObject(sqlSelect.toString(), params, new ProgettoVORowMapper());

			return result;

		} finally {
			getLogger().end();
		}

	}

	public ProgettoVO findProgetto(Long idProgetto) {
		getLogger().begin();
		try {
			ProgettoVO result = new ProgettoVO();
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto")
					.append(",PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato")
					.append(",PBANDI_T_PROGETTO.ID_ISTANZA_PROCESSO as idIstanzaProcesso");
			StringBuilder tables = new StringBuilder("PBANDI_T_PROGETTO, PBANDI_R_SOGGETTO_PROGETTO");

			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_PROGETTO.ID_PROGETTO = :idProgetto"));

			setWhereClause(conditionList, sqlSelect);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);

			result = queryForObject(sqlSelect.toString(), params, new ProgettoVORowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<ProgettoVO> findProgetti(List<String> idProgetti) {
		getLogger().begin();
		try {
			List<ProgettoVO> result = new ArrayList<ProgettoVO>();

			String progetti = new String();
			if (idProgetti != null && idProgetti.size() > 0) {
				StringBuilder temp = new StringBuilder();
				for (String idProgetto : idProgetti) {
					temp.append(idProgetto + ",");
				}
				progetti = temp.toString().substring(0, temp.toString().lastIndexOf(","));
			}
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_PROGETTO.id_progetto as idProgetto")
					.append(",PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato")
					.append(",PBANDI_T_PROGETTO.ID_ISTANZA_PROCESSO as idIstanzaProcesso");
			StringBuilder tables = new StringBuilder("PBANDI_T_PROGETTO");

			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_PROGETTO.ID_PROGETTO IN ( " + progetti + " )"));

			setWhereClause(conditionList, sqlSelect, tables);

			MapSqlParameterSource params = new MapSqlParameterSource();

			result = query(sqlSelect.toString(), params, new ProgettoVORowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}

	public String findRuoloIstruttoreMaster(Long idIstruttoreMaster, String codRuolo, boolean checkDataFine) {
		getLogger().begin();
		try {
			List<Map> list = null;
			String result = null;
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idIstruttoreMaster", idIstruttoreMaster, Types.NUMERIC);
			params.addValue("codRuolo", codRuolo);
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica as codice");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGG_TIPO_ANAGRAFICA, PBANDI_D_TIPO_ANAGRAFICA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_R_SOGG_TIPO_ANAGRAFICA.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			conditionList.add(new StringBuilder("PBANDI_R_SOGG_TIPO_ANAGRAFICA.id_soggetto = :idIstruttoreMaster"));
			conditionList.add(new StringBuilder("PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = :codRuolo"));

			if (checkDataFine)
				setWhereClause(conditionList, sqlSelect, tables);
			else
				setWhereClause(conditionList, sqlSelect);

			list = queryForList(sqlSelect.toString(), params);
			if (!isEmpty(list)) {
				for (int i = 0; i < 1; i++) {
					Map map = (Map) list.get(i);
					result = (String) map.get("codice");
				}
			}
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<BandoVO> findBandiConfigurabili(BandoVO filter) {

		final char escapeChar = '~';
		final String escapeCommand = " ESCAPE '" + escapeChar + "' ";

		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("SELECT").append(" PBANDI_T_BANDO.ID_BANDO as idBando")
				.append(", PBANDI_T_BANDO.ID_LINEA_DI_INTERVENTO as idLineaDiIntervento")
				.append(", PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO as idBandoLinea")
				.append(", PBANDI_T_BANDO.TITOLO_BANDO as titoloBando")
				.append(", PBANDI_D_MATERIA.DESC_MATERIA as descMateria")
				.append(", PBANDI_R_BANDO_LINEA_INTERVENT.NOME_BANDO_LINEA as nomeBandoLinea");

		StringBuilder tables = new StringBuilder("PBANDI_T_BANDO").append(", PBANDI_R_BANDO_LINEA_INTERVENT")
				.append(", PBANDI_D_MATERIA");

		sqlSelect.append(" FROM ").append(tables);

		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		conditionList.add(new StringBuilder("PBANDI_T_BANDO.ID_MATERIA = PBANDI_D_MATERIA.ID_MATERIA"));
		conditionList.add(new StringBuilder("PBANDI_T_BANDO.ID_BANDO = PBANDI_R_BANDO_LINEA_INTERVENT.ID_BANDO(+)"));

		if (filter.getIdMateria() != null) {
			conditionList.add(new StringBuilder("PBANDI_D_MATERIA.ID_MATERIA = :idMateria"));
			params.addValue("idMateria", filter.getIdMateria(), Types.NUMERIC);
		}
		if (filter.getTitoloBando() != null) {
			conditionList
					.add(new StringBuilder("upper(PBANDI_T_BANDO.TITOLO_BANDO) LIKE :titoloBando" + escapeCommand));
			params.addValue("titoloBando", "%" + StringUtil.escapeWildcards(filter.getTitoloBando(), escapeChar) + "%",
					Types.VARCHAR);
		}
		if (filter.getNomeBandoLinea() != null) {
			conditionList.add(new StringBuilder(
					"upper(PBANDI_R_BANDO_LINEA_INTERVENT.NOME_BANDO_LINEA) LIKE :nomeBandoLinea" + escapeCommand));
			params.addValue("nomeBandoLinea",
					"%" + StringUtil.escapeWildcards(filter.getNomeBandoLinea(), escapeChar) + "%", Types.VARCHAR);
		}
		if (filter.getIdLineaDiIntervento() != null) {
			conditionList.add(new StringBuilder("PBANDI_T_BANDO.ID_LINEA_DI_INTERVENTO = :idLineaDiIntervento"));
			params.addValue("idLineaDiIntervento", filter.getIdLineaDiIntervento(), Types.NUMERIC);
		}

		setWhereClause(conditionList, sqlSelect, tables);

		List<BandoVO> result = query(sqlSelect.toString(), params, new BandoVORowMapper());

		return result;
	}

	private class IstruttoreVORowMapper implements
			RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO> {
		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO vo = (it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO) beanMapper
					.toBean(rs,
							it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO.class);
			return vo;
		}
	}

	private class BandoVORowMapper implements
			RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO> {

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO vo = (it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO) beanMapper
					.toBean(rs,
							it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO.class);
			return vo;
		}
	}

	private class ProgettoVORowMapper
			implements RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO> {

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO mapRow(ResultSet rs,
				int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO vo = (it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO) beanMapper
					.toBean(rs, it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO.class);
			return vo;
		}
	}

	private class BandoLineaAssociatoDTORowMapper implements RowMapper<BandoLineaAssociatoDTO> {

		public BandoLineaAssociatoDTO mapRow(ResultSet rs, int row) throws SQLException {

			BandoLineaAssociatoDTO vo = new BandoLineaAssociatoDTO();
			vo.setProgrBandoLineaIntervento(rs.getLong("progr_bando_linea_intervento"));
			vo.setNomeBandoLinea(rs.getString("nome_bando_linea"));
			return vo;
		}
	}

	private class BeneficiarioVORowMapper implements
			RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO> {

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO vo = (it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO) beanMapper
					.toBean(rs,
							it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO.class);
			return vo;
		}
	}

	// CDU-14 V04

	private class PbandiCTipoDocumentoIndexVORowMapper implements RowMapper<PbandiCTipoDocumentoIndexVO> {

		public PbandiCTipoDocumentoIndexVO mapRow(ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			PbandiCTipoDocumentoIndexVO vo = (PbandiCTipoDocumentoIndexVO) beanMapper.toBean(rs,
					PbandiCTipoDocumentoIndexVO.class);
			return vo;
		}
	}

	private class CheckListBandoLineaDTORowMapper implements RowMapper<CheckListBandoLineaDTO> {

		public CheckListBandoLineaDTO mapRow(ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			CheckListBandoLineaDTO dto = (CheckListBandoLineaDTO) beanMapper.toBean(rs, CheckListBandoLineaDTO.class);
			return dto;
		}
	}

	private class DocPagamBandoLineaDTORowMapper implements RowMapper<DocPagamBandoLineaDTO> {

		public DocPagamBandoLineaDTO mapRow(ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			DocPagamBandoLineaDTO dto = (DocPagamBandoLineaDTO) beanMapper.toBean(rs, DocPagamBandoLineaDTO.class);
			return dto;
		}
	}

	// CDU-14 V04 fine

	////////////////////////////////////////////////////////

	public boolean bandoIsEnteCompetenzaFinpiemonte(Long progBandoLineaIntervento) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		boolean bandoIsEnteCompetenzaFinpiemonte;

		try {

			String sql;
			sql = "SELECT count(*) AS VALUE\r\n" + "FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli\r\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP iec\r\n"
					+ "ON prbli.PROGR_BANDO_LINEA_INTERVENTO = iec.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "WHERE iec.ID_ENTE_COMPETENZA = 2\r\n" + "AND iec.ID_RUOLO_ENTE_COMPETENZA = 1\r\n"
					+ "AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?";

			logger.info("\n" + sql);

			Object[] args = new Object[] { progBandoLineaIntervento };

			List<IntegerValueDTO> queryResult = getJdbcTemplate().query(sql, args,
					new BeanRowMapper(IntegerValueDTO.class));
			Integer numRow = queryResult.get(0).getValue();
			bandoIsEnteCompetenzaFinpiemonte = numRow > 0 ? true : false;

		} catch (Exception e) {
			String msg = "Errore l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}

		return bandoIsEnteCompetenzaFinpiemonte;

	}

	public Boolean isBandoSif(Long progBandoLineaIntervento) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		Boolean isSif = Boolean.FALSE;

		try {

			String sql;
			sql = "SELECT FLAG_SIF\r\n" + "FROM PBANDI_R_BANDO_LINEA_INTERVENT \r\n"
					+ "WHERE PROGR_BANDO_LINEA_INTERVENTO = ?";

			logger.info("\n" + sql);

			Object[] args = new Object[] { progBandoLineaIntervento };
			
			logger.info(prf + " <progBandoLineaIntervento>: "+ progBandoLineaIntervento);

			String flag = getJdbcTemplate().queryForObject(sql, args, String.class);
			
			if(flag != null) {
				isSif = flag.equalsIgnoreCase("S") ? Boolean.TRUE : Boolean.FALSE;
			} else {
				isSif = Boolean.FALSE;
			}

		} catch (Exception e) {
			String msg = "Errore durante l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}

		return isSif;
	}

	public Boolean isBandoSiffino(Long progBandoLineaIntervento, Long idBando) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		Boolean isSiffino = Boolean.FALSE;

		try {
			if (idBando == null && progBandoLineaIntervento == null) {
				logger.error(prf + "progBandoLineaIntervento e idBando sono null");
				throw new Exception("progBandoLineaIntervento e idBando sono null");
			}
			
			String sql = "SELECT COUNT(*)\r\n"
					+ "FROM PBANDI_R_BANDO_LINEA_INTERVENT \r\n"
					+ "WHERE PROGR_BANDO_LINEA_INTERV_SIF IS NOT NULL ";
			Object[] args = null;
			if (progBandoLineaIntervento != null) {
				sql  += "AND PROGR_BANDO_LINEA_INTERVENTO = ? \r\n";
				args = new Object[] { progBandoLineaIntervento };
				logger.info(prf + " <progBandoLineaIntervento>: "+ progBandoLineaIntervento);
			} else {
				sql  += "AND ID_BANDO = ? \r\n";
				args = new Object[] { idBando };
				logger.info(prf + " <idBando>: "+ idBando);
			} 
			logger.info("\n" + sql);
			
			Long count = getJdbcTemplate().queryForObject(sql, args, Long.class);
			
			if(count > 0) {
				isSiffino = Boolean.TRUE;
			} else {
				isSiffino = Boolean.FALSE;
			}

		} catch (Exception e) {
			String msg = "Errore durante l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}

		return isSiffino;
	}

	public Long getProgrBandoLineaByIdProgetto(Long idProgetto) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		try {
			if (idProgetto == null ) {
				logger.error(prf + "idProgetto e' null");
				throw new Exception("idProgetto e' null");
			}
			
			String sql = "SELECT d.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "FROM PBANDI_T_PROGETTO p\r\n"
					+ "JOIN PBANDI_T_DOMANDA d ON d.ID_DOMANDA = p.ID_DOMANDA\r\n"
					+ "WHERE p.ID_PROGETTO = ?";
			Object[] args= new Object[] { idProgetto };
			logger.info(prf + " <idProgetto>: "+ idProgetto); 
			logger.info("\n" + sql);
			
			Long progrBandoLinea = getJdbcTemplate().queryForObject(sql, args, Long.class);
			
			return progrBandoLinea;

		} catch (Exception e) {
			String msg = "Errore durante l'esecuzione della query";
			logger.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			logger.info(prf + " END");
		}
	}

}
