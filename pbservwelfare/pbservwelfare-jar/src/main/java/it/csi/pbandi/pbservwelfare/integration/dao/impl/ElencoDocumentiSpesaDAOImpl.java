/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.dto.DocumentoSpesa;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.ElencoDocumentiSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class ElencoDocumentiSpesaDAOImpl extends BaseDAO implements ElencoDocumentiSpesaDAO {
	
	private final Logger LOG;
	
	public ElencoDocumentiSpesaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public List<DocumentoSpesa> getDocumentiDiSpesaDaNumeroDomanda(String numeroDomanda) {
		String prf = "[ElencoDocumentiSpesaDAOImpl::getDocumentiDiSpesaDaNumeroDomanda]";
		LOG.info(prf + " BEGIN");
		ArrayList<DocumentoSpesa> listaDocumentiSpesa;
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PDRSP.ID_DOCUMENTO_DI_SPESA, PTDDS.IMPORTO_TOTALE_DOCUMENTO										\n" +
					"FROM PBANDI_T_DOMANDA PTD																						\n" + 
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_DOMANDA = PTD.ID_DOMANDA													\n" + 
					"JOIN PBANDI_R_DOC_SPESA_PROGETTO PDRSP ON PDRSP.ID_PROGETTO = PTP.ID_PROGETTO									\n" + 
					"JOIN PBANDI_T_DOCUMENTO_DI_SPESA PTDDS ON PTDDS.ID_DOCUMENTO_DI_SPESA = PDRSP.ID_DOCUMENTO_DI_SPESA			\n" + 
					"JOIN PBANDI_D_TIPO_DOCUMENTO_SPESA PDTDS ON PDTDS.ID_TIPO_DOCUMENTO_SPESA = PTDDS.ID_TIPO_DOCUMENTO_SPESA		\n" + 
					"WHERE PTD.NUMERO_DOMANDA= :numeroDomanda																		\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			listaDocumentiSpesa = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				ArrayList<DocumentoSpesa> list = new ArrayList<>();
				while (rs.next()) {
					DocumentoSpesa doc = new DocumentoSpesa();
					doc.setIdentificativoDocumentoDiSpesa(rs.getInt("ID_DOCUMENTO_DI_SPESA"));
					doc.setImportoTotaleDocumento(rs.getInt("IMPORTO_TOTALE_DOCUMENTO"));
					list.add(doc);
				}
				return list;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocumentoSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocumentoSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaDocumentiSpesa;
	}
	
	@Override
	public List<DocumentoSpesa> getDocumentiDiSpesaDaIdDichiarazioneSpesa(Integer identificativoDichiarazioneDiSpesa) {
		String prf = "[ElencoDocumentiSpesaDAOImpl::getDocumentiDiSpesaDaIdDichiarazioneSpesa]";
		LOG.info(prf + " BEGIN");
		ArrayList<DocumentoSpesa> listaDocumentiSpesa;
		LOG.info(prf + ", identificativoDichiarazioneDiSpesa=" + identificativoDichiarazioneDiSpesa);
		try {
			String sql = "SELECT PRPDS2.ID_DOCUMENTO_DI_SPESA, PTDDS.IMPORTO_TOTALE_DOCUMENTO												\n" +
					"FROM PBANDI_R_PAGAMENTO_DICH_SPESA PRPDS																				\n" + 
					"JOIN PBANDI_R_PAGAMENTO_DOC_SPESA PRPDS2 ON PRPDS2.ID_PAGAMENTO = PRPDS.ID_PAGAMENTO									\n" + 
					"JOIN PBANDI_T_DOCUMENTO_DI_SPESA PTDDS ON PTDDS.ID_DOCUMENTO_DI_SPESA = PRPDS2.ID_DOCUMENTO_DI_SPESA					\n" + 
					"WHERE PRPDS.ID_DICHIARAZIONE_SPESA= :identificativoDichiarazioneDiSpesa												\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoDichiarazioneDiSpesa", identificativoDichiarazioneDiSpesa, Types.INTEGER);

			listaDocumentiSpesa = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				ArrayList<DocumentoSpesa> list = new ArrayList<>();
				while (rs.next()) {
					DocumentoSpesa doc = new DocumentoSpesa();
					doc.setIdentificativoDocumentoDiSpesa(rs.getInt("ID_DOCUMENTO_DI_SPESA"));
					doc.setImportoTotaleDocumento(rs.getInt("IMPORTO_TOTALE_DOCUMENTO"));
					list.add(doc);
				}
				return list;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocumentoSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocumentoSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaDocumentiSpesa;
	}
	
	@Override
	public List<DocumentoSpesa> getDocumentiDiSpesaDaIdDocSpesa(Integer identificativoDocumentoDiSpesa) {
		String prf = "[ElencoDocumentiSpesaDAOImpl::getDocumentiDiSpesaDaIdDocSpesa]";
		LOG.info(prf + " BEGIN");
		ArrayList<DocumentoSpesa> listaDocumentiSpesa;
		LOG.info(prf + ", identificativoDocumentoDiSpesa=" + identificativoDocumentoDiSpesa);
		try {
			String sql = "SELECT PTDDS.ID_DOCUMENTO_DI_SPESA, PTDDS.IMPORTO_TOTALE_DOCUMENTO											\n" +
					"FROM PBANDI_T_DOCUMENTO_DI_SPESA PTDDS																				\n" + 
					"JOIN PBANDI_D_TIPO_DOCUMENTO_SPESA PDTDS ON PDTDS.ID_TIPO_DOCUMENTO_SPESA = PTDDS.ID_TIPO_DOCUMENTO_SPESA			\n" + 
					"WHERE PTDDS.ID_DOCUMENTO_DI_SPESA= :identificativoDocumentoDiSpesa													\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoDocumentoDiSpesa", identificativoDocumentoDiSpesa, Types.INTEGER);

			listaDocumentiSpesa = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				ArrayList<DocumentoSpesa> list = new ArrayList<>();
				while (rs.next()) {
					DocumentoSpesa doc = new DocumentoSpesa();
					doc.setIdentificativoDocumentoDiSpesa(rs.getInt("ID_DOCUMENTO_DI_SPESA"));
					doc.setImportoTotaleDocumento(rs.getInt("IMPORTO_TOTALE_DOCUMENTO"));
					list.add(doc);
				}
				return list;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocumentoSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocumentoSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaDocumentiSpesa;
	}

	@Override
	public DocumentoSpesa getInfoAggiuntive(Integer idDocumentoDiSpesa) {
		String prf = "[ElencoDocumentiSpesaDAOImpl::getInfoAggiuntive]";
		LOG.info(prf + " BEGIN");
		DocumentoSpesa info;
		LOG.info(prf + ", idDocumentoDiSpesa=" + idDocumentoDiSpesa);
		
		try {
			String sql = "SELECT \n" +
					"PDSDS.DESC_STATO_DOCUMENTO_SPESA, \n" +
					"CASE \n" +
					"\tWHEN PTDS.DT_CHIUSURA_VALIDAZIONE IS NULL THEN NULL\n" +
					"\tWHEN PTDS.DT_CHIUSURA_VALIDAZIONE IS NOT NULL THEN PDRSP.IMPORTO_RENDICONTAZIONE\n" +
					"END AS IMPORTO_RENDICONTAZIONE,\n" +
					"importiQuietanzati.IMPORTO_QUIETANZATO, \n" +
					"CASE \n" +
					"\tWHEN PTDS.DT_CHIUSURA_VALIDAZIONE IS NULL THEN NULL\n" +
					"\tWHEN PTDS.DT_CHIUSURA_VALIDAZIONE IS NOT NULL THEN importiValidati.IMPORTO_VALIDATO\n" +
					"END AS IMPORTO_VALIDATO,\n" +
					"PTF.CODICE_FISCALE_FORNITORE, \n" +
					"PTF.DENOMINAZIONE_FORNITORE, \n" +
					"PTF.COGNOME_FORNITORE, \n" +
					"PTF.NOME_FORNITORE,\n" +
					"PTF.DT_INIZIO_CONTRATTO, \n" +
					"PTF.DT_FINE_CONTRATTO, \n" +
					"PDRSP.NOTE_VALIDAZIONE\n" +
					"FROM PBANDI_T_DOCUMENTO_DI_SPESA PTDDS\n" +
					"JOIN PBANDI_S_DICH_DOC_SPESA PSDDS ON PSDDS.ID_DOCUMENTO_DI_SPESA = PTDDS.ID_DOCUMENTO_DI_SPESA\n" +
					"LEFT JOIN PBANDI_T_DICHIARAZIONE_SPESA PTDS ON PTDS.ID_DICHIARAZIONE_SPESA = PSDDS.ID_DICHIARAZIONE_SPESA \n" +
					"JOIN PBANDI_T_FORNITORE PTF ON PTF.ID_FORNITORE = PTDDS.ID_FORNITORE \n" +
					"JOIN PBANDI_R_DOC_SPESA_PROGETTO PDRSP ON PDRSP.ID_DOCUMENTO_DI_SPESA = PTDDS.ID_DOCUMENTO_DI_SPESA \n" +
					"JOIN PBANDI_D_STATO_DOCUMENTO_SPESA PDSDS ON PDSDS.ID_STATO_DOCUMENTO_SPESA = PDRSP.ID_STATO_DOCUMENTO_SPESA_VALID \n" +
					"JOIN (\n" +
					"\tSELECT SUM(ptp.IMPORTO_PAGAMENTO) AS IMPORTO_QUIETANZATO, prpds.ID_DOCUMENTO_DI_SPESA \n" +
					"\tFROM PBANDI_R_PAGAMENTO_DOC_SPESA prpds \n" +
					"\tJOIN PBANDI_T_PAGAMENTO ptp ON ptp.ID_PAGAMENTO = prpds.ID_PAGAMENTO \n" +
					"\tGROUP BY prpds.ID_DOCUMENTO_DI_SPESA\n" +
					") importiQuietanzati ON importiQuietanzati.ID_DOCUMENTO_DI_SPESA = PTDDS.ID_DOCUMENTO_DI_SPESA\n" +
					"LEFT JOIN (\n" +
					"\tSELECT SUM(prpqpds.IMPORTO_VALIDATO) AS IMPORTO_VALIDATO, prpds.ID_DOCUMENTO_DI_SPESA \n" +
					"\tFROM PBANDI_R_PAGAMENTO_DOC_SPESA prpds \n" +
					"\tLEFT JOIN PBANDI_R_PAG_QUOT_PARTE_DOC_SP prpqpds ON prpqpds.ID_PAGAMENTO = prpds.ID_PAGAMENTO \n" +
					"\tGROUP BY prpds.ID_DOCUMENTO_DI_SPESA\n" +
					") importiValidati ON importiValidati.ID_DOCUMENTO_DI_SPESA = PTDDS.ID_DOCUMENTO_DI_SPESA\n" +
					"WHERE PTDDS.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.INTEGER);

			info = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				DocumentoSpesa doc = new DocumentoSpesa();
				while (rs.next()) {
					doc.setStatoDocumento(rs.getString("DESC_STATO_DOCUMENTO_SPESA"));
					doc.setImportoRendicontato(rs.getInt("IMPORTO_RENDICONTAZIONE"));
					if(rs.wasNull()){
						doc.setImportoRendicontato(null);
					}
					doc.setImportoQuietanzato(rs.getInt("IMPORTO_QUIETANZATO"));
					doc.setImportoValidato(rs.getInt("IMPORTO_VALIDATO"));
					if(rs.wasNull()){
						doc.setImportoValidato(null);
					}
					doc.setCodiceFiscaleFornitore(rs.getString("CODICE_FISCALE_FORNITORE"));
					doc.setDenominazioneFornitore(rs.getString("DENOMINAZIONE_FORNITORE"));
					doc.setCognome(rs.getString("COGNOME_FORNITORE"));
					doc.setNome(rs.getString("NOME_FORNITORE"));
					doc.setDataInizioContratto(rs.getDate("DT_INIZIO_CONTRATTO"));
					doc.setDataFineContratto(rs.getDate("DT_FINE_CONTRATTO"));
					doc.setNoteValidazione(rs.getString("NOTE_VALIDAZIONE"));
				}
				return doc;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocumentoSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocumentoSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return info;
	}

}
