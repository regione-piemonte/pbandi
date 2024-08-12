package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

public class PbandiDocumentiDiSpesaDAOImpl extends PbandiDAO {


	public PbandiDocumentiDiSpesaDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> findDocumentiDiSpesa(
			DocumentoDiSpesaDTO documentoDiSpesa, List<String> statiAmmessi) {
		return findDocumentiDiSpesa(documentoDiSpesa, statiAmmessi, true);
	}

	/*
	 * Tipologia documento di spesa -
	 * documentoDiSpesa.getIdTipoDocumentoDiSpesa() Numero documento di spesa
	 * Data documento Tipologia fornitore Codice fiscale fornitore Partita iva
	 * fornitore Denominazione fornitore Cognome fornitore Nome fornitore
	 * Documenti di spesa del progetto o tutti
	 */
	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> findDocumentiDiSpesa(
			DocumentoDiSpesaDTO documentoDiSpesa, List<String> statiAmmessi,
			boolean documentiGestitiNelProgetto) {
		/*
		 * Attenzione l'idProgetto dovrebbe essere sempre valorizzato
		 */

		getLogger().begin();
		java.util.List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO>();
		try {
			/*
			 * Tipologia Numero documento Data documento Codice fiscale
			 * fornitore Importo documento Stato Codice progetto Partner
			 */

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA"));
			tables
					.append(",PBANDI_D_TIPO_DOCUMENTO_SPESA")
					.append(",PBANDI_D_TIPO_SOGGETTO")
					.append(",PBANDI_T_FORNITORE")
					.append(",PBANDI_D_STATO_DOCUMENTO_SPESA")
					.append(",PBANDI_T_PROGETTO")
					.append(
							",PBANDI_R_SOGGETTO_PROGETTO,PBANDI_R_DOC_SPESA_PROGETTO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE = PBANDI_T_FORNITORE.ID_FORNITORE(+)"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO=PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_SOGGETTO_PROGETTO.id_progetto=PBANDI_T_PROGETTO.id_progetto"));

			// conditionList.add((new
			// StringBuilder()).append("PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO=PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO"));
			conditionList
					.add((new StringBuilder())
							.append("( pbandi_t_documento_di_spesa.id_fornitore is null or pbandi_d_tipo_soggetto.id_tipo_soggetto ="
									+ "pbandi_t_fornitore.id_tipo_soggetto )"));

			if (!isNull(documentoDiSpesa.getCodiceFiscaleFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE LIKE :codiceFiscaleFornitore"));
				params.addValue("codiceFiscaleFornitore",
						upper(documentoDiSpesa.getCodiceFiscaleFornitore())
								+ "%", Types.VARCHAR);
			}

			if (!isNull(documentoDiSpesa.getCognomeFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.COGNOME_FORNITORE LIKE :cognomeFornitore"));
				params.addValue("cognomeFornitore", upper(documentoDiSpesa
						.getCognomeFornitore())
						+ "%", Types.VARCHAR);
			}

			if (!isNull(documentoDiSpesa.getDataDocumentoDiSpesa())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO=:dtEmissioneDocumento"));
				params.addValue("dtEmissioneDocumento", documentoDiSpesa
						.getDataDocumentoDiSpesa(), Types.DATE);
			}

			if (!isNull(documentoDiSpesa.getDenominazioneFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE LIKE :denominazioneFornitore"));
				params.addValue("denominazioneFornitore",
						upper(documentoDiSpesa.getDenominazioneFornitore())
								+ "%", Types.VARCHAR);
			}
			if (isTrue(documentoDiSpesa.getIsGestitiNelProgetto())) {
				getLogger()
						.debug(
								"Si cercano i documenti gestiti nel progetto,devono avere stato INSERITO(I) o NON VALIDATO(N)");
				if (documentiGestitiNelProgetto
						&& !isNull(documentoDiSpesa.getIdProgetto())) {
					// }L{ FIX PBANDI-750
					if (!isNull(documentoDiSpesa.getIsRicercaPerPartner())
							&& documentoDiSpesa.getIsRicercaPerPartner()) {
						conditionList
								.add((new StringBuilder())
										.append(
												"PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO IN (")
										.append(
												"SELECT ID_PROGETTO FROM PBANDI_T_PROGETTO")
										.append(
												" START WITH ID_PROGETTO_PADRE = :idProgetto")
										.append(
												" CONNECT BY PRIOR ID_PROGETTO = ID_PROGETTO_PADRE")
										.append(")"));
					} else {
						conditionList
								.add((new StringBuilder())
										.append("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO=:idProgetto"));
					}
				}
				// INSERITO e NON VALIDATO
				if (!isEmpty(statiAmmessi)) {
					String x = "PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA in (";
					for (String statoDocumentoDispesa : statiAmmessi) {
						x += "'" + statoDocumentoDispesa + "',";
					}
					x = x.substring(0, x.length() - 1);
					x += ")";
					conditionList.add((new StringBuilder()).append(x));
				}
				// conditionList.add((new
				// StringBuilder()).append("PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA IN ('I','N')"));
			} else {
				getLogger().debug(
						"Si cercano i documenti non gestiti nel progetto");
			}
			if (!isNull(documentoDiSpesa.getIdTipoDocumentoDiSpesa())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA=:idTipoDocumentoDiSpesa"));
				params.addValue("idTipoDocumentoDiSpesa", documentoDiSpesa
						.getIdTipoDocumentoDiSpesa(), Types.BIGINT);
			}

			if (!isNull(documentoDiSpesa.getIdTipoFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO=:idTipoFornitore"));
				params.addValue("idTipoFornitore", documentoDiSpesa
						.getIdTipoFornitore(), Types.BIGINT);
			}

			if (!isNull(documentoDiSpesa.getNomeFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.NOME_FORNITORE LIKE :nomeFornitore"));
				params.addValue("nomeFornitore", upper(documentoDiSpesa
						.getNomeFornitore())
						+ "%", Types.VARCHAR);
			}

			if (!isNull(documentoDiSpesa.getNumeroDocumento())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO LIKE :numeroDocumento"));
				params.addValue("numeroDocumento", upper(documentoDiSpesa
						.getNumeroDocumento())
						+ "%", Types.VARCHAR);
			}

			if (!isNull(documentoDiSpesa.getPartitaIvaFornitore())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE LIKE :partitaIvaFornitore"));
				params.addValue("partitaIvaFornitore", documentoDiSpesa
						.getPartitaIvaFornitore()
						+ "%", Types.VARCHAR);
			}

			if (!isNull(documentoDiSpesa.getTask())) {
				conditionList.add((new StringBuilder())
						.append("PBANDI_R_DOC_SPESA_PROGETTO.TASK =:task"));
				params.addValue("task", documentoDiSpesa.getTask(),
						Types.VARCHAR);
			}
			if (!isNull(documentoDiSpesa.getIdSoggetto())) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_R_SOGGETTO_PROGETTO.ID_SOGGETTO=:idSoggetto"));
				params.addValue("idSoggetto", documentoDiSpesa.getIdSoggetto(),
						Types.BIGINT);
			}

			conditionList
					.add((new StringBuilder())
							.append("pbandi_r_soggetto_progetto.ID_TIPO_BENEFICIARIO <> 4"));

			String colonnaTask;
			if (!isNull(documentoDiSpesa.getIdProgetto())) {
				colonnaTask = " (select rdsp.TASK from PBANDI_R_DOC_SPESA_PROGETTO rdsp where PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = rdsp.id_documento_di_spesa and rdsp.id_progetto = :idProgetto) as task, ";
				params.addValue("idProgetto", documentoDiSpesa.getIdProgetto(),
						Types.BIGINT);
			} else {
				colonnaTask = " PBANDI_R_DOC_SPESA_PROGETTO.TASK as task, ";
			}

			StringBuilder sqlSelect = (new StringBuilder(
					"SELECT DISTINCT "
							+ " PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE as codiceFiscaleFornitore,"
							+

							/**
							 * FIX: PBandi-316
							 */
							// " PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceProgetto,"+
							" PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO as dataDocumentoDiSpesa , "
							+ " PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA as descStatoDocumentoSpesa, "
							+

							"DECODE(pbandi_t_fornitore.id_fornitore,null,null,pbandi_d_tipo_soggetto.desc_tipo_soggetto) as descTipologiaFornitore,"
							+
							// " PBANDI_D_TIPO_SOGGETTO.DESC_TIPO_SOGGETTO as descTipologiaFornitore, "+
							"DECODE(pbandi_t_fornitore.id_fornitore,null,null,pbandi_d_tipo_soggetto.id_tipo_soggetto) as idTipoFornitore,"
							+

							" PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as descTipologiaDocumentoDiSpesa,"
							+
							/**
							 * FIX: PBANDI-401
							 */
							" PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa as descBreveTipoDocumentoDiSpesa,"
							+
							/**
							 * END FIX
							 */
							" PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO as  descrizioneDocumentoDiSpesa, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA as destinazioneTrasferta , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA as  durataTrasferta, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO as  idDocRiferimento, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA as idDocumentoDiSpesa , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE as  idFornitore, "
							+
							/**
							 * FIX: PBandi-316
							 */
							// " PBANDI_T_PROGETTO.ID_PROGETTO as idProgetto,"+
							" PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO as  idSoggetto, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumentoDiSpesa , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA as idTipoOggettoAttivita , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG as idUtenteAgg , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS as idUtenteIns , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE as  imponibile, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA as importoIva , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO as importoIvaCosto , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO as importoTotaleDocumentoIvato,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO as numeroDocumento, "
							+ colonnaTask
							+ " NVL2(PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO,"
							+ "(SELECT PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO FROM PBANDI_T_ENTE_GIURIDICO "
							+ "WHERE PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO = PBANDI_T_ENTE_GIURIDICO.ID_ENTE_GIURIDICO "
							+ "AND NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE)),"
							+ "(SELECT PBANDI_T_PERSONA_FISICA.COGNOME||' '|| PBANDI_T_PERSONA_FISICA.NOME FROM PBANDI_T_PERSONA_FISICA "
							+ "WHERE PBANDI_R_SOGGETTO_PROGETTO.ID_PERSONA_FISICA = PBANDI_T_PERSONA_FISICA.ID_PERSONA_FISICA "
							+ "AND NVL(TRUNC(PBANDI_T_PERSONA_FISICA.DT_FINE_VALIDITA), TRUNC(SYSDATE +1)) > TRUNC(SYSDATE) "
							+ ")) as partner " + " "));
			// PARTNER ???
			// }L{ IL PARTNER C'E'
			sqlSelect.append(" FROM ").append(tables);

			/*
			 * . Gli elementi sono ordinati per codice fiscale fornitore,
			 * tipologia di documento e numero di documento.
			 */
			// setWhereClause(conditionList, sqlSelect,tables);
			// FIX: PBANDI-490
			setWhereClause(conditionList, sqlSelect, tables, new StringBuilder(
					"PBANDI_T_FORNITORE"));
			setOrderBy(sqlSelect, "codiceFiscaleFornitore,"
					+ "descTipologiaDocumentoDiSpesa," + "numeroDocumento",
					true);
			result = query(sqlSelect.toString(), params,
					new DocumentiDiSpesaRowMapper());
			getLogger().debug(" documenti di spesa trovati: " + result.size());

		} finally {
			getLogger().end();
		}
		return result;

	}


	private class DocumentiDiSpesaPerFornitoriRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.DocumentoDiSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.DocumentoDiSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.DocumentoDiSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.DocumentoDiSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.DocumentoDiSpesaVO.class);
			return vo;
		}
	}

	private class DocumentiDiSpesaRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO.class);
			return vo;
		}
	}

	private class DocumentiDiSpesaPerDichiarazioneRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO.class);
			
			return vo;
		}
	}

	@Deprecated
	// 7/12/2010 : Usare DettaglioDocumentoDiSpesaVO (NB: alcuni campi hanno un
	// nome differente da questa query, controllare
	// ValidazioneRendicontazioneBusinessImpl.DIFFMAP_FROM_DETTAGLIODOCUMENTODISPESAVO_TO_DOCUMENTODISPESADTO)
	public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO findDettaglioDocumentoDiSpesa(
			Long idDocumentoDiSpesa, Long idProgetto) {
		getLogger().begin();
		it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO result = new it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO();
		try {

			/**
			 * I parametri di ricerca non sono tutti obbligatori
			 */
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA"));
			tables.append(",PBANDI_D_TIPO_DOCUMENTO_SPESA").append(
					",PBANDI_D_STATO_DOCUMENTO_SPESA").append(
					",PBANDI_D_TIPO_SOGGETTO").append(
					",PBANDI_R_DOC_SPESA_PROGETTO").append(
					",PBANDI_R_SOGGETTO_PROGETTO")
					.append(",PBANDI_T_FORNITORE").append(",PBANDI_T_PROGETTO");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA = PBANDI_D_STATO_DOCUMENTO_SPESA.ID_STATO_DOCUMENTO_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE = PBANDI_T_FORNITORE.ID_FORNITORE(+)"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO=PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionList
					.add((new StringBuilder())
							.append("PBANDI_R_SOGGETTO_PROGETTO.id_progetto=PBANDI_T_PROGETTO.id_progetto"));
			// conditionList.add((new
			// StringBuilder()).append("PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO=PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO"));
			conditionList
					.add((new StringBuilder())
							.append("( PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE is null or PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO=PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO)"));

			if (!isNull(idDocumentoDiSpesa)) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa"));
				params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
						Types.BIGINT);
			}

			if (!isNull(idProgetto)) {
				conditionList
						.add((new StringBuilder())
								.append("PBANDI_R_SOGGETTO_PROGETTO.id_progetto=:idProgetto"));
				params.addValue("idProgetto", idProgetto, Types.BIGINT);
			}

			StringBuilder sqlSelect = (new StringBuilder(
					"SELECT DISTINCT "
							+ " PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE as codiceFiscaleFornitore,"
							+ " PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceProgetto,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO as dataDocumentoDiSpesa , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO as  descrizioneDocumentoDiSpesa, "
							+ " PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_STATO_DOCUMENTO_SPESA as descStatoDocumentoSpesa, "
							+ " PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as descTipologiaDocumentoDiSpesa,"
							+ " PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA as descBreveTipoDocumentoDiSpesa,"
							+ " decode (PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE,null,null,PBANDI_D_TIPO_SOGGETTO.DESC_TIPO_SOGGETTO) as descTipologiaFornitore,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DESTINAZIONE_TRASFERTA as destinazioneTrasferta , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.DURATA_TRASFERTA as  durataTrasferta, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOC_RIFERIMENTO as  idDocRiferimento, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA as idDocumentoDiSpesa , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_FORNITORE as  idFornitore, "
							+ " PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO as idProgetto,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_SOGGETTO as  idSoggetto, "
							+ " PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA as idStatoDocumentoDiSpesa,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumentoDiSpesa , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_OGGETTO_ATTIVITA as idTipoOggettoAttivita , "
							+ " PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO as idTipoFornitore,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_AGG as idUtenteAgg , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.ID_UTENTE_INS as idUtenteIns , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPONIBILE as  imponibile, "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA as importoIva , "
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_IVA_COSTO as importoIvaACosto , "
							+ " PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE as importoRendicontabile ,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.IMPORTO_TOTALE_DOCUMENTO as importoTotaleDocumentoIvato,"
							+ " PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO as numeroDocumento,"
							+ " PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE as partitaIvaFornitore, "
							+ " PBANDI_R_DOC_SPESA_PROGETTO.TASK as task "
							+ " "));

			sqlSelect.append(" FROM ").append(tables);
			// TODO }L{ PBANDI-490 : check
			// setWhereClause(conditionList, sqlSelect,tables);
			setWhereClause(conditionList, sqlSelect, tables, new StringBuilder(
					"PBANDI_T_FORNITORE"));

			result = queryForObject(sqlSelect.toString(), params,
					new DocumentiDiSpesaRowMapper());

		} finally {
			getLogger().end();
		}
		return result;
	}


	public boolean cancellaDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		getLogger().begin();
		boolean ret = false;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder(
					"delete from PBANDI_T_DOCUMENTO_DI_SPESA where ID_DOCUMENTO_DI_SPESA =:idDocumentoDiSpesa");
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.BIGINT);
			ret = elimina(sqlDelete.toString(), params);
		} finally {
			getLogger().end();
		}
		return ret;
	}

	public boolean cancellaAssociazioniDocDiSpesaConVociDispesa(
			Long idDocumentoDiSpesa, Long idProgetto) {

		getLogger().begin();
		boolean ret = false;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder(
					"delete from PBANDI_T_QUOTA_PARTE_DOC_SPESA "
							+ " where ID_DOCUMENTO_DI_SPESA =:idDocumentoDiSpesa "
							+ " and ID_PROGETTO =:idProgetto");
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			ret = elimina(sqlDelete.toString(), params);

		} finally {
			getLogger().end();
		}
		return ret;
	}

	public boolean cancellaAssociazioneDocumentoDiSpesaProgetto(
			Long idDocumentoDiSpesa, Long idProgetto) {
		getLogger().begin();
		boolean ret = false;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlDelete = new StringBuilder(
					"delete from PBANDI_R_DOC_SPESA_PROGETTO "
							+ " where ID_DOCUMENTO_DI_SPESA =:idDocumentoDiSpesa "
							+ " and ID_PROGETTO =:idProgetto");
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			ret = elimina(sqlDelete.toString(), params);

		} finally {
			getLogger().end();
		}
		return ret;
	}

	public List<Long> findAltriProgettiCollegati(Long idDocumentoDiSpesa,
			Long idProgetto) {
		getLogger().begin();
		List<Long> ret = new ArrayList<Long>();
		List<Map> list = null;
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO"));

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			conditionList.add((new StringBuilder())
					.append("ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa"));
			conditionList.add((new StringBuilder())
					.append("ID_PROGETTO != :idProgetto"));
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);

			StringBuilder sqlSelect = (new StringBuilder("SELECT ID_PROGETTO "
					+ " FROM ")).append(tables);

			setWhereClause(conditionList, sqlSelect, tables);

			list = queryForList(sqlSelect.toString(), params);

			if (!isEmpty(list)) {

				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					BigDecimal idAltroProgetto = (BigDecimal) map
							.get("ID_PROGETTO");
					ret.add(NumberUtil.toLong(idAltroProgetto));
				}

			}
		} finally {
			getLogger().end();
		}
		return ret;
	}

	/* VITTORIO */

	private AbstractDataFieldMaxValueIncrementer seqpbandiTDocumentoDiSpesa;

	public AbstractDataFieldMaxValueIncrementer getSeqpbandiTDocumentoDiSpesa() {
		return seqpbandiTDocumentoDiSpesa;
	}

	public void setSeqpbandiTDocumentoDiSpesa(
			AbstractDataFieldMaxValueIncrementer seqpbandiTDocumentoDiSpesa) {
		this.seqpbandiTDocumentoDiSpesa = seqpbandiTDocumentoDiSpesa;
	}



	public boolean inserisciAssociazioneDocumentoDiSpesaProgetto(Long idUtente,
			Long idProgetto, Long idDocumentoDiSpesa,
			Double importoRendicontabile, String task, Long idStatoDocumento) {
		getLogger().begin();
		try {

			
			StringBuilder sqlInsert = new StringBuilder(
					"INSERT INTO PBANDI_R_DOC_SPESA_PROGETTO (").append(
					"ID_DOCUMENTO_DI_SPESA,").append("ID_PROGETTO,").append(
					"IMPORTO_RENDICONTAZIONE, ID_STATO_DOCUMENTO_SPESA,").append("ID_UTENTE_INS,")
					.append("TASK ").append(" ) VALUES (").append(
							":idDocumentoDiSpesa,").append(":idProgetto,")
					.append(":importoRendicontabile,:idStatoDocumento,").append(":idUtente,")
					.append(":task").append(")");
		

			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			params.addValue("importoRendicontabile", importoRendicontabile,Types.NUMERIC);
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			params.addValue("task", task, Types.VARCHAR);
			params.addValue("idStatoDocumento", idStatoDocumento, Types.NUMERIC);

			return inserisci(sqlInsert.toString(), params).booleanValue();

		} finally {
			getLogger().end();
		}
	}

	public List<Map> findFornitore(String codiceFiscaleFornitore,
			boolean isCedolinoOAutodichiarazioneSoci,
			String[] codQualificheDaEscludere) {
		getLogger().begin();
		try {
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_FORNITORE.ID_FORNITORE as idFornitore ");
			StringBuilder tables = new StringBuilder("PBANDI_T_FORNITORE");
			if (isCedolinoOAutodichiarazioneSoci) {
				tables.append(",PBANDI_R_FORNITORE_QUALIFICA");
			}
			if (codQualificheDaEscludere != null
					&& codQualificheDaEscludere.length > 0) {
				tables.append(",PBANDI_D_QUALIFICA");
			}
			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_T_FORNITORE.DT_FINE_VALIDITA is null"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE = :codiceFiscaleFornitore"));
			if (isCedolinoOAutodichiarazioneSoci) {
				conditionList
						.add(new StringBuilder(
								"PBANDI_T_FORNITORE.ID_FORNITORE = PBANDI_R_FORNITORE_QUALIFICA.ID_FORNITORE"));
			}
			if (codQualificheDaEscludere != null
					&& codQualificheDaEscludere.length > 0) {
				StringBuilder qualificheNotIn = new StringBuilder();
				for (String codiceQualifica : codQualificheDaEscludere) {
					qualificheNotIn.append("'" + codiceQualifica + "',");
				}
				conditionList.add((new StringBuilder()).append(
						"PBANDI_D_QUALIFICA.DESC_BREVE_QUALIFICA NOT IN ( ")
						.append(
								qualificheNotIn.substring(0, qualificheNotIn
										.lastIndexOf(","))).append(" )"));
				conditionList
						.add(new StringBuilder(
								"PBANDI_R_FORNITORE_QUALIFICA.id_qualifica = PBANDI_D_QUALIFICA.id_qualifica"));
			}
			// conditionList.add(new
			// StringBuilder("PBANDI_R_FORNITORE_QUALIFICA.DT_FINE_VALIDITA is null"));

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("codiceFiscaleFornitore", codiceFiscaleFornitore,
					Types.VARCHAR);

			setWhereClause(conditionList, sqlSelect, tables);
			List<Map> lista = queryForList(sqlSelect.toString(), params);
			return lista;
		} finally {
			getLogger().end();
		}

	}

	public List<Map> getDomandeWithDataPresentazionePrecedenteDocumento(
			DocumentoDiSpesaVO documento) {
		getLogger().begin();
		try {
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_DOMANDA.*");
			StringBuilder tables = new StringBuilder(
					"PBANDI_T_PROGETTO, PBANDI_T_DOMANDA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOMANDA.dt_presentazione_domanda < :dataDocumentoDiSpesa"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", documento.getIdProgetto(),
					Types.NUMERIC);
			params.addValue("dataDocumentoDiSpesa", documento
					.getDataDocumentoDiSpesa(), Types.DATE);

			setWhereClause(conditionList, sqlSelect, tables);
			List<Map> lista = queryForList(sqlSelect.toString(), params);
			return lista;

		} finally {
			getLogger().end();
		}
	}

	@Deprecated
	public List<Map> getDocumentiForUnicita(DocumentoDiSpesaVO documento,
			String descrBreveTipoDocumentoDiSpesa) {
		getLogger().begin();
		try {

			// StringBuilder sqlSelect = new
			// StringBuilder(" SELECT DISTINCT PBANDI_T_DOCUMENTO_DI_SPESA.*");
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT DISTINCT PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa as idDocumentoDiSpesa");

			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA, PBANDI_T_FORNITORE, PBANDI_D_TIPO_DOCUMENTO_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento = :numeroDocumento"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento = :dataDocumentoDiSpesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA = :descrBreveTipoDocumentoDiSpesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_FORNITORE.codice_fiscale_fornitore = :codiceFiscaleFornitore"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore = PBANDI_T_FORNITORE.id_fornitore"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa = PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa"));
			// conditionList.add(new
			// StringBuilder("PBANDI_D_TIPO_DOCUMENTO_SPESA.dt_fine_validita is null"));
			// conditionList.add(new
			// StringBuilder("PBANDI_T_FORNITORE.dt_fine_validita is null"));

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("numeroDocumento", documento.getNumeroDocumento(),
					Types.VARCHAR);
			params.addValue("dataDocumentoDiSpesa", documento
					.getDataDocumentoDiSpesa(), Types.DATE);
			params.addValue("descrBreveTipoDocumentoDiSpesa",
					descrBreveTipoDocumentoDiSpesa, Types.VARCHAR);
			params.addValue("codiceFiscaleFornitore", documento
					.getCodiceFiscaleFornitore(), Types.VARCHAR);

			setWhereClause(conditionList, sqlSelect, tables);
			// logger.debug("Query:"+sqlSelect.toString());
			List<Map> lista = queryForList(sqlSelect.toString(), params);
			return lista;

		} finally {
			getLogger().end();
		}
	}

	public Long getProgrLineaIntervento(Long idProgetto) {
		getLogger().begin();
		try {
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento");
			StringBuilder tables = new StringBuilder(
					" PBANDI_T_PROGETTO, PBANDI_T_DOMANDA, PBANDI_R_BANDO_LINEA_INTERVENT ");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(
					"PBANDI_T_PROGETTO.id_progetto= :idProgetto"));
			// conditionList.add(new
			// StringBuilder("PBANDI_C_REGOLA.desc_breve_regola = :descrBreveRegola"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_PROGETTO.id_domanda = PBANDI_T_DOMANDA.id_domanda"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_BANDO_LINEA_INTERVENT.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			// conditionList.add(new
			// StringBuilder("PBANDI_C_REGOLA.id_regola = PBANDI_R_REGOLA_BANDO_LINEA.id_regola"));
			// conditionList.add(new
			// StringBuilder("PBANDI_C_REGOLA.dt_fine_validita is null"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			// params.addValue("descrBreveRegola", descrBreveRegola,
			// Types.VARCHAR);

			setWhereClause(conditionList, sqlSelect, tables);
			// logger.debug("Query:"+sqlSelect.toString());
			return queryForLong(sqlSelect.toString(), params);

		} finally {
			getLogger().end();
		}
	}

	public List<Map> getDatiFornitoreQualifica(String codiceFiscaleFornitore) {
		getLogger().begin();
		try {
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT PBANDI_R_FORNITORE_QUALIFICA.*");
			StringBuilder tables = new StringBuilder(
					"PBANDI_R_FORNITORE_QUALIFICA, PBANDI_T_FORNITORE");
			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_FORNITORE.codice_fiscale_fornitore = :codiceFiscaleFornitore"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_FORNITORE_QUALIFICA.id_fornitore = PBANDI_T_FORNITORE.id_fornitore"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_FORNITORE_QUALIFICA.costo_risorsa is not null"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_FORNITORE_QUALIFICA.monte_ore is not null"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("codiceFiscaleFornitore", codiceFiscaleFornitore,
					Types.VARCHAR);

			setWhereClause(conditionList, sqlSelect, tables);
			// logger.debug("Query:"+sqlSelect.toString());
			List<Map> lista = queryForList(sqlSelect.toString(), params);
			return lista;
		} finally {
			getLogger().end();
		}
	}

	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> findImportiDocumentoDiSpesaProgetto(
			Long idDocumentoDiSpesa, Long idProgetto) {
		getLogger().begin();
		try {
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocumentoSpesa", idDocumentoDiSpesa,
					Types.NUMERIC);

			StringBuilder sqlSelect = new StringBuilder(
					"SELECT PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa as idDocumentoDiSpesa,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.imponibile as imponibile,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva as importoIva,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva_costo as importoIvaACosto,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento as importoTotaleDocumentoIvato,")
					.append(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_progetto as idProgetto,")
					.append(
							"PBANDI_R_DOC_SPESA_PROGETTO.importo_rendicontazione as importoRendicontabile");

			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA, PBANDI_R_DOC_SPESA_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = :idDocumentoSpesa"));

			if (idProgetto != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}

			setWhereClause(conditionList, sqlSelect);

			// logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,
					new DocumentiDiSpesaRowMapper());
			getLogger().debug(
					"Progetti collegati al documento di spesa trovati: "
							+ result.size());
			return result;
		} finally {
			getLogger().end();
		}
	}

	/**
	 * Restituisce gli importi delle quote parte. Filtra per: - idProgetto -
	 * idDocumentoDiSpesa
	 * 
	 * @param idDocumentoDiSpesa
	 * @param idProgetto
	 * @return
	 */
	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO> findImportiQuotaParteByDocumentoDiSpesaProgetto(
			Long idDocumentoDiSpesa, Long idProgetto, Long idRigoContoEconomico) {
		getLogger().begin();
		try {
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa as idQuotaParteDocSpesa,")
					.append(
							"PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa as idDocumentoDiSpesa,")
					.append(
							"PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto as idProgetto,")
					.append(
							"PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico as idRigoContoEconomico,")
					.append(
							"PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_quota_parte_doc_spesa as importoQuotaParteDocSpesa");

			/**
			 * FIX: Pbandi-374 Eliminati i campi IMPORTO_VALIDATO,
			 * IMPORTO_QUOTA_PARTE_QUIETANZ dalla tabella
			 * PBANDI_T_QUOTA_PARTE_DOC_SPESA
			 */
			// .append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_quota_parte_quietanz as importoQuotaParteQuietanz")
			// .append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_validato as importoValidato");
			StringBuilder tables = new StringBuilder(
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA, PBANDI_R_DOC_SPESA_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			if (idProgetto != null) {
				conditionList
						.add(new StringBuilder(
								"PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}
			if (idDocumentoDiSpesa != null) {
				conditionList
						.add(new StringBuilder(
								"PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = :idDocumentoDiSpesa"));
				params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
						Types.NUMERIC);
			}
			if (idRigoContoEconomico != null) {
				conditionList
						.add(new StringBuilder(
								"PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico = :idRigoContoEconomico"));
				params.addValue("idRigoContoEconomico", idRigoContoEconomico,
						Types.NUMERIC);
			}
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto"));

			setWhereClause(conditionList, sqlSelect);
			// logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,
					new QuotaParteDocSpesaRowMapper());
			getLogger().debug(
					"QuoteParteDocSpesa collegate al documento di spesa trovate: "
							+ result.size());
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> findFatturePerFornitore(
			Long idProgetto, Long idFornitore) {
		getLogger().begin();
		try {
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa as idDocumentoDiSpesa,")
					// .append("PBANDI_T_DOCUMENTO_DI_SPESA.desc_documento as descrizioneDocumentoDiSpesa,")
					.append(
							"PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as descTipologiaDocumentoDiSpesa,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento as dataDocumentoDiSpesa,")
					.append(
							"PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento as numeroDocumento");
			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA, PBANDI_R_DOC_SPESA_PROGETTO, PBANDI_T_FORNITORE,PBANDI_D_TIPO_DOCUMENTO_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore = PBANDI_T_FORNITORE.id_fornitore"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa = PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa IN('FF','FT','FL')"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
			conditionList.add(new StringBuilder(
					"PBANDI_T_FORNITORE.id_fornitore = :idFornitore"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			params.addValue("idFornitore", idFornitore, Types.NUMERIC);
			// params.addValue("codiceTipoDocumentoDiSpesa",
			// codiceTipoDocumentoDiSpesa, Types.VARCHAR);

			// fix per jira 942: se il fornitore � invalidato il doc va cercato
			// lo stesso
			StringBuilder tablesDaEscludere = (new StringBuilder(
					"PBANDI_T_FORNITORE"));
			// tablesDaEscludere.append(",pbandi_r_fornitore_qualifica");
			setWhereClause(conditionList, sqlSelect, tables, tablesDaEscludere);
			// setWhereClause(conditionList, sqlSelect, tables);

			setOrderBy(sqlSelect,
					"PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA",
					true);
			result = query(sqlSelect.toString(), params,
					new DocumentiDiSpesaRowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}

	public boolean updateDocumentoDiSpesa(Long idUtente, DocumentoDiSpesaVO vo) {
	 
			StringBuilder sqlUpdate = new StringBuilder(
					"UPDATE PBANDI_T_DOCUMENTO_DI_SPESA SET ")
					.append("DT_EMISSIONE_DOCUMENTO = :dataEmissioneDocumento,")
					.append("DESC_DOCUMENTO = :descrizioneDocumento,")
					.append("DESTINAZIONE_TRASFERTA = :destinazioneTrasferta,")
					.append("DURATA_TRASFERTA = :durataTrasferta,")
					.append("ID_DOC_RIFERIMENTO = :idDocRiferimento,")
					.append("ID_FORNITORE = :idFornitore,")
					.append("ID_SOGGETTO = :idSoggetto,")
					//.append("ID_STATO_DOCUMENTO_SPESA = :idStatoDocumentoDiSpesa,")
					.append("ID_TIPO_DOCUMENTO_SPESA = :idTipoDocumentoSpesa,")
					.append(
							"ID_TIPO_OGGETTO_ATTIVITA = :idTipoOggettoAttivita,")
					.append("ID_UTENTE_AGG = :idUtente,")
					.append("IMPONIBILE = :imponibile,")
					.append("IMPORTO_IVA = :importoIva,")
					.append("IMPORTO_IVA_COSTO = :importoIvaACosto,")
					.append(
							"IMPORTO_TOTALE_DOCUMENTO = :importoTotaleDocumento,")
					.append("NUMERO_DOCUMENTO = :numeroDocumento");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa"));

			setWhereClause(conditionList, sqlUpdate);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("dataEmissioneDocumento", vo
					.getDataDocumentoDiSpesa(), Types.DATE);
			params.addValue("descrizioneDocumento", StringUtil.toUpperCase(vo
					.getDescrizioneDocumentoDiSpesa()), Types.VARCHAR);
			params.addValue("destinazioneTrasferta", StringUtil.toUpperCase(vo
					.getDestinazioneTrasferta()), Types.VARCHAR);
			params.addValue("durataTrasferta", vo.getDurataTrasferta(),
					Types.NUMERIC);
			params.addValue("idDocRiferimento", vo.getIdDocRiferimento(),
					Types.NUMERIC);
			params.addValue("idDocumentoDiSpesa", vo.getIdDocumentoDiSpesa(),
					Types.NUMERIC);
			params.addValue("idFornitore", vo.getIdFornitore(), Types.NUMERIC);
			params.addValue("idSoggetto", vo.getIdSoggetto(), Types.NUMERIC);
			//params.addValue("idStatoDocumentoDiSpesa", vo.getIdStatoDocumentoDiSpesa(), Types.NUMERIC);
			params.addValue("idTipoDocumentoSpesa", vo
					.getIdTipoDocumentoDiSpesa(), Types.NUMERIC);
			params.addValue("idTipoOggettoAttivita", vo
					.getIdTipoOggettoAttivita(), Types.NUMERIC);
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			params.addValue("imponibile", vo.getImponibile(), Types.NUMERIC);
			params.addValue("importoIva", vo.getImportoIva(), Types.NUMERIC);
			params.addValue("importoIvaACosto", vo.getImportoIvaACosto(),
					Types.NUMERIC);
			params.addValue("importoTotaleDocumento", vo
					.getImportoTotaleDocumentoIvato(), Types.NUMERIC);
			params.addValue("numeroDocumento", StringUtil.toUpperCase(vo
					.getNumeroDocumento()), Types.VARCHAR);

			return modifica(sqlUpdate.toString(), params).booleanValue();
		 
	}

	public boolean updateAssociazioneProgettoDocumentoDiSpesa(Long idUtente,
			DocumentoDiSpesaVO vo) {
		getLogger().begin();
		try {
			StringBuilder sqlUpdate = new StringBuilder(
					"UPDATE PBANDI_R_DOC_SPESA_PROGETTO SET ").append(
					"importo_rendicontazione = :importoRendicontabile,").append(
					"id_utente_agg = :idUtente,").append("task = :task, ID_STATO_DOCUMENTO_SPESA = :idStatoDocumentoSpesa, ").append("note_validazione = :noteValidazione ");

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = :idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));

			setWhereClause(conditionList, sqlUpdate);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("importoRendicontabile", vo
					.getImportoRendicontabile(), Types.NUMERIC);
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			params.addValue("idDocumentoDiSpesa", vo.getIdDocumentoDiSpesa(),
					Types.NUMERIC);
			params.addValue("idProgetto", vo.getIdProgetto(), Types.NUMERIC);
			params.addValue("task", vo.getTask(), Types.VARCHAR);
			params.addValue("noteValidazione", vo.getNoteValidazione(), Types.VARCHAR);
			params.addValue("idStatoDocumentoSpesa", vo.getIdStatoDocumentoDiSpesa(), Types.NUMERIC);
			return modifica(sqlUpdate.toString(), params);
		} finally {
			getLogger().end();
		}
	}

	private class QuotaParteDocSpesaRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO.class);
			return vo;
		}
	}


	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO> findPagamentiAssociatiADocumento(
			Long idDocumentoDiSpesa) {
		getLogger().begin();
		try {
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO> result = null;
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			MapSqlParameterSource params = new MapSqlParameterSource();

			StringBuilder sqlSelect = new StringBuilder(
					"select PBANDI_T_PAGAMENTO.id_pagamento as idPagamento, ")
					.append("PBANDI_T_PAGAMENTO.importo_pagamento as importoPagamento");
			StringBuilder tables = new StringBuilder(
					"PBANDI_R_PAGAMENTO_DOC_SPESA, PBANDI_T_PAGAMENTO");

			sqlSelect.append(" FROM ").append(tables);

			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento = PBANDI_T_PAGAMENTO.id_pagamento"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = :idDocumentoDiSpesa"));

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect);

			result = query(sqlSelect.toString(), params,
					new PbandiTPagamentoRowMapper());

			return result;

		} finally {
			getLogger().end();
		}
	}

	private class PbandiTPagamentoRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO.class);
			return vo;
		}
	}

	public boolean updateStatoDocumentiDiSpesa(Long idUtente,
			List<Long> idDocumentiDiSpesa, Long idProgetto, Long idStato) {

		boolean ret = false;

		StringBuilder sqlUpdate = new StringBuilder(
				"update  PBANDI_R_DOC_SPESA_PROGETTO  set ");
		sqlUpdate.append(" ID_STATO_DOCUMENTO_SPESA =:idStato ,  ID_UTENTE_AGG=:idUtente,  ID_STATO_DOCUMENTO_SPESA_VALID =:idStato");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("idStato", idStato, Types.BIGINT);
		params.addValue("idUtente", idUtente, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);

		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

/*  PK : questo codice genera una query non valida nel caso gli ID siano > 1000 , lo dice la Oracle
 * 
		StringBuilder listIdDoc = new StringBuilder();
		for (Long idDoc : idDocumentiDiSpesa) {
			listIdDoc.append(idDoc + ",");
		}

		conditionList.add((new StringBuilder()).append(
				"ID_DOCUMENTO_DI_SPESA  IN ( ").append(
				listIdDoc.substring(0, listIdDoc.lastIndexOf(",")))
				.append(" )"));
*/
		
		String condizioneOR = generaCondizioneOR(idDocumentiDiSpesa);
		if(condizioneOR!=null && !condizioneOR.equals(""))
			conditionList.add((new StringBuilder()).append(condizioneOR));
		
		conditionList.add(new StringBuilder(" ID_PROGETTO = :idProgetto "));
		setWhereClause(conditionList, sqlUpdate);

		ret = modifica(sqlUpdate.toString(), params);
		return ret;
	}

	private String generaCondizioneOR(List<Long> idDocumentiDiSpesa) {

		logger.begin();

		int lim = 500; // limite degli id che si possono inserire in una clausola IN (per Oracle e' 1000)
		
		// stringa che conterra' tutte le condizioni IN messe in OR
		String condizioneOR = "";
					
		if(idDocumentiDiSpesa!=null && !idDocumentiDiSpesa.isEmpty()) {
			
			if(idDocumentiDiSpesa.size()<lim)
				lim = idDocumentiDiSpesa.size();
			
			// lista che conterra' le clausole IN
			List <String> listaCondiz = new ArrayList<String>();
				
			// numero di condizioni OR da generare
			int numCicli = idDocumentiDiSpesa.size()/lim ;
			// calcolo il resto per verificare se devo aumentare di 1 il numCicli
			int resto = idDocumentiDiSpesa.size()%lim ;
			
			logger.info("numCicli"+numCicli);
			logger.info("resto="+resto);
			
			if(resto>0)
				numCicli ++;
			
			logger.info("numCicli="+numCicli);
			
			// indice da cui partire a calcolare la subList
			int fromIndex = 0; 
			
			for (int i = 0; i < numCicli; i++) {
				
				// indice in cui finire di calcolare la subList
				int toIndex = fromIndex + lim;
				
				// il toIndex non puo' superare la dimensione della lista originale
				if(toIndex > idDocumentiDiSpesa.size())
					toIndex =  idDocumentiDiSpesa.size();
				
				logger.info("fromIndex="+fromIndex+" , toIndex="+toIndex);
				List<Long> l1 = idDocumentiDiSpesa.subList(fromIndex, toIndex);
				logger.info("l1="+l1);
				
				// incremento gli indici per la prossima iterazione
				fromIndex=fromIndex+lim;
	
				// trasformo la sublist ottenuta in una stringa per la query finale
				StringBuilder listIdDocTmp = new StringBuilder();
				for (Long idDoc : l1) {
					listIdDocTmp.append(idDoc + ",");
				}
				
				listaCondiz.add(" ID_DOCUMENTO_DI_SPESA  IN ( " + listIdDocTmp.substring(0, listIdDocTmp.lastIndexOf(",")) + " )");
			}			
			
			logger.info("listaCondiz="+listaCondiz);
			condizioneOR = condizioneOR + " ( ";
			
			for (String str : listaCondiz) {
				condizioneOR = condizioneOR + str + " OR ";
			}
			condizioneOR = condizioneOR.substring(0, condizioneOR.lastIndexOf("OR")) + ")";
			
		} else {
			logger.info("idDocumentiDiSpesa nulla o vuota");
		}
		logger.info("condizioneOR="+condizioneOR);
		
		logger.end();
		return condizioneOR;
	}

	// }L{ FIX 19/08/2009 : necessario l'importo rendicontabile delle note di
	// credito per il calcolo corretto degli importi validati
	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> findNoteDiCreditoAssociateFattura(
			Long idDocumentoDiSpesa) {
		getLogger().begin();
		try {
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa as idDocumentoDiSpesa,")
					.append(
							" PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento as importoTotaleDocumento,")
					.append(
							" SUM(PBANDI_R_DOC_SPESA_PROGETTO.importo_rendicontazione) as importoRendicontabile");

			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DOCUMENTO_DI_SPESA, ")
					.append("PBANDI_R_DOC_SPESA_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento =:idDocRiferimento"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa  = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa"));

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("idDocRiferimento", idDocumentoDiSpesa,
					Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect);

			StringBuilder sqlGroupBy = new StringBuilder(" GROUP BY"
					+ " PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa,"
					+ " PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento");

			sqlSelect.append(sqlGroupBy);

			result = query(sqlSelect.toString(), params,
					new DocumentiDiSpesaPerDichiarazioneRowMapper());

			return result;

		} finally {
			getLogger().end();
		}
	}

	public List<PbandiTProgettoVO> findProgettiAssociatiDocumentoDiSpesa(
			Long idDocumentoDiSpesa) {
		getLogger().begin();
		try {

			List<PbandiTProgettoVO> result = new ArrayList<PbandiTProgettoVO>();
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_T_PROGETTO.ID_PROGETTO as idProgetto,")
					.append(
							"PBANDI_T_PROGETTO.CODICE_PROGETTO as codiceProgetto,")
					.append(
							"PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceVisualizzato");
			StringBuilder tables = new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO,PBANDI_T_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO =  PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect, tables);

			result = query(sqlSelect.toString(), params,
					new PbandiTProgettoVORowMapper());

			return result;

		} finally {
			getLogger().end();
		}
	}


	private class PbandiTProgettoVORowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO.class);
			return vo;
		}
	}

	public List<Double> findOreLavorateFornitore(Long idFornitore) {
		getLogger().begin();
		try {
			List<Double> result = new ArrayList<Double>();
			List<Map> mapList = null;
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT nvl(PBANDI_T_QUOTA_PARTE_DOC_SPESA.ore_lavorate,0) as oreLavorate");
			StringBuilder tables = new StringBuilder(
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA,").append(
					"PBANDI_T_DOCUMENTO_DI_SPESA,").append(
					"PBANDI_D_TIPO_DOCUMENTO_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_D_TIPO_DOCUMENTO_SPESA.desc_breve_tipo_doc_spesa in ('CD','CS')"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore = :idFornitore"));

			setWhereClause(conditionList, sqlSelect, tables);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idFornitore", idFornitore, Types.NUMERIC);
			mapList = queryForList(sqlSelect.toString(), params);
			if (mapList != null) {
				for (int i = 0; i < mapList.size(); i++) {
					Map map = (Map) mapList.get(i);
					BigDecimal oreLavorate = (BigDecimal) map
							.get("oreLavorate");
					result.add(NumberUtil.toDouble(oreLavorate));
				}
			}
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<Long> findIdDocumentiDiSpesa(Long idDichiarazioneDiSpesa,
			Long idProgetto) {
		/*
		 * select PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA from
		 * PBANDI_R_PAGAMENTO_DICH_SPESA, PBANDI_R_PAGAMENTO_DOC_SPESA where
		 * PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA=1 and
		 * PBANDI_R_PAGAMENTO_DICH_SPESA
		 * .ID_PAGAMENTO=PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO and
		 * PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PROGETTO=1040
		 */
		getLogger().begin();
		List<Long> result = new ArrayList<Long>();
		try {
			List<Map> mapList = null;
			StringBuilder sqlSelect = new StringBuilder(
					"select distinct PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA ");
			StringBuilder tables = new StringBuilder(
					"PBANDI_R_PAGAMENTO_DICH_SPESA,")
					.append("PBANDI_R_PAGAMENTO_DOC_SPESA,PBANDI_R_DOC_SPESA_PROGETTO ");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA=:idDichiarazioneDiSpesa"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO=:idProgetto"));
			conditionList.add(new StringBuilder(
					"PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO=PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO"));

			setWhereClause(conditionList, sqlSelect, tables);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa,
					Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			mapList = queryForList(sqlSelect.toString(), params);
			if (mapList != null) {
				for (int i = 0; i < mapList.size(); i++) {
					Map map = (Map) mapList.get(i);
					BigDecimal id = (BigDecimal) map
							.get("ID_DOCUMENTO_DI_SPESA");
					result.add(NumberUtil.toLong(id));
				}
			}
			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<PagamentoVoceDiSpesaVO> findImportiRipartitiDocumentoProgettoPagamento(
			Long idDocumentoDiSpesa, Long idProgetto, Long idPagamento,
			boolean controllaDataFine) {
		getLogger().begin();
		try {

			List<PagamentoVoceDiSpesaVO> result = new ArrayList<PagamentoVoceDiSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_quota_parte_doc_spesa as idQuotaParteDocSpesa,")
					.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_quietanzato as importoQuietanzato");

			StringBuilder tables = new StringBuilder(
					"PBANDI_R_PAGAMENTO_DOC_SPESA,PBANDI_R_DOC_SPESA_PROGETTO,")
					.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP, PBANDI_T_QUOTA_PARTE_DOC_SPESA");
			MapSqlParameterSource params = new MapSqlParameterSource();

			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento"));
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA"));
			conditionList
					.add(new StringBuilder(
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO = PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO"));
			conditionList
					.add(new StringBuilder(
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA"));
			
			
			
			if (idProgetto != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
				params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			}
			if (idDocumentoDiSpesa != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = :idDocumentoDiSpesa"));
				params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
						Types.NUMERIC);
			}
			if (idPagamento != null) {
				conditionList
						.add(new StringBuilder(
								" PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento = :idPagamento"));
				params.addValue("idPagamento", idPagamento, Types.NUMERIC);
			}
			if (controllaDataFine)
				setWhereClause(conditionList, sqlSelect, tables);
			else
				setWhereClause(conditionList, sqlSelect);

			return result = query(sqlSelect.toString(), params,
					new PagamentoVoceDiSpesaVORowMapper());

		} finally {
			getLogger().end();
		}
	}

	private class PagamentoVoceDiSpesaVORowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO.class);
			return vo;
		}
	}

	public Double getSommaImportiRendicontazione(Long idDocumentoDiSpesa) {
		getLogger().begin();
		Double sommaImportiRendicontazione = null;
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect
					.append(" sum(PBANDI_R_DOC_SPESA_PROGETTO.IMPORTO_RENDICONTAZIONE)");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_R_DOC_SPESA_PROGETTO");
			sqlSelect.append(" WHERE");
			sqlSelect
					.append(" PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa ");

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,
					Types.BIGINT);

			sommaImportiRendicontazione = queryForDouble(sqlSelect.toString(),
					params);

		} finally {
			getLogger().end();
		}
		return sommaImportiRendicontazione;
	}

 
	public List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> findDocumentiDiSpesaDichiarazione(
			Long idDichiarazione) {

	//	logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
	 
			List<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO> result = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO>();
			// OLD IMPLEMENTATION
			StringBuilder sqlSelect = new StringBuilder(
					"SELECT DISTINCT PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa as idDocumentoDiSpesa");
			StringBuilder tables = new StringBuilder(
					"PBANDI_T_DICHIARAZIONE_SPESA, PBANDI_R_PAGAMENTO_DICH_SPESA, PBANDI_R_PAGAMENTO_DOC_SPESA, PBANDI_R_DOC_SPESA_PROGETTO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList
					.add(new StringBuilder(
							"PBANDI_R_PAGAMENTO_DICH_SPESA.id_dichiarazione_spesa = PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento = PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = PBANDI_T_DICHIARAZIONE_SPESA.id_progetto"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList
					.add(new StringBuilder(
							" PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa = :idDichiarazione"));

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect);

			result = query(sqlSelect.toString(), params,
					new DocumentiDiSpesaPerDichiarazioneRowMapper());

			// }L{ NEW IMPLEMENTATION
			// DocumentoDiSpesaDichiarazioneTotalePagamentiVO vo = new
			// DocumentoDiSpesaDichiarazioneTotalePagamentiVO();
			// vo.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazione));
			// try {
			// result = beanUtil.transformList(genericDAO.findListWhere(vo),
			// it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO.class);
			// } catch (Exception e) {
			// logger.error("Errore nella transformList", e);
			// }

			return result;

		 
	}


	private class DocumentiDiSpesaPerValidazioneRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoDiSpesaVO.class);
			return vo;
		}
	}

	private class DocumentiDiSpesaPerChiudiValidazioneRowMapper
			implements
			RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoChiudiValidazioneVO> {

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoChiudiValidazioneVO mapRow(
				ResultSet rs, int row) throws SQLException {

			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoChiudiValidazioneVO vo = (it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoChiudiValidazioneVO) beanMapper
					.toBean(
							rs,
							it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.DocumentoChiudiValidazioneVO.class);
			return vo;
		}
	}


	public boolean updateStatoNoteDiCredito(Long idUtente, Long idDichiarazione, Long idProgetto) {

		boolean ret = false;
		getLogger().begin();

		StringBuilder sqlUpdate = new StringBuilder("");
		sqlUpdate
				.append(" update pbandi_r_doc_spesa_progetto note ")
				.append(" set ID_STATO_DOCUMENTO_SPESA = ")
				.append("     ( SELECT docprg.ID_STATO_DOCUMENTO_SPESA ")
				.append("       FROM PBANDI_R_DOC_SPESA_PROGETTO DOCPRG, ")
				.append("            PBANDI_T_DOCUMENTO_DI_SPESA DOC ")
				.append("       where DOC.ID_DOC_RIFERIMENTO = DOCPRG.ID_DOCUMENTO_DI_SPESA ")
				.append("         and DOC.ID_DOCUMENTO_DI_SPESA = NOTE.ID_DOCUMENTO_DI_SPESA ")
				.append("         AND note.id_progetto = docprg.id_progetto ")
				.append("      ), ")
				.append("      id_utente_agg = :idUtente ")
				.append(" where ID_DOCUMENTO_DI_SPESA in( ")
				.append("   select distinct ")
				.append("          note.ID_DOCUMENTO_DI_SPESA ")
				.append("   from ")
				.append("         pbandi_t_documento_di_spesa docAll, ")
				.append("         pbandi_t_documento_di_spesa note, ")
				.append("         pbandi_r_doc_spesa_progetto docProAll, ")
				.append("         pbandi_d_tipo_documento_spesa tipoDoc, ")
				.append("         pbandi_d_stato_documento_spesa statoDoc, ")
				.append("         pbandi_r_pagamento_doc_spesa pagDoc, ")
				.append("         pbandi_r_pagamento_dich_spesa pagDic ")
				.append("   where ")
				.append("        docAll.ID_DOCUMENTO_DI_SPESA = docProAll.ID_DOCUMENTO_DI_SPESA ")
				.append("    and docAll.ID_DOCUMENTO_DI_SPESA =note.ID_DOC_RIFERIMENTO ")
				.append("    and docAll.ID_TIPO_DOCUMENTO_SPESA = tipoDoc.ID_TIPO_DOCUMENTO_SPESA ")
				.append("    and docProAll.ID_STATO_DOCUMENTO_SPESA = statoDoc.ID_STATO_DOCUMENTO_SPESA ")
				.append("    and docAll.ID_DOCUMENTO_DI_SPESA = pagDoc.ID_DOCUMENTO_DI_SPESA ")
				.append("    and pagDoc.ID_PAGAMENTO = pagDic.ID_PAGAMENTO ")
				.append("    and pagDic.ID_DICHIARAZIONE_SPESA = :idDichiarazione )")
				.append("    and ID_PROGETTO = :idProgetto");

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("idUtente", idUtente, Types.BIGINT);
		params.addValue("idDichiarazione", idDichiarazione, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);

		ret = modifica(sqlUpdate.toString(), params);
		getLogger().end();
		return ret;

	}
	
	
	public void setNullNumeroProtocollo (BigDecimal idUtente,BigDecimal idDocumentoDiSpesa, BigDecimal idEntita)  {
		String sql ="UPDATE PBANDI_T_DOCUMENTO_INDEX " +
				"    SET NUM_PROTOCOLLO = NULL, " +
				"    ID_UTENTE_AGG =:idUtente " +
				"    WHERE id_target =:idDocumentoDiSpesa " +
				" AND id_entita=:idEntita" +
				" AND NUM_PROTOCOLLO IS NOT NULL";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idUtente", idUtente, Types.BIGINT);
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
		params.addValue("idEntita", idEntita, Types.BIGINT);
		
		modifica(sql, params);
	}

	/*
	 * 
	 * )
	 */
}
