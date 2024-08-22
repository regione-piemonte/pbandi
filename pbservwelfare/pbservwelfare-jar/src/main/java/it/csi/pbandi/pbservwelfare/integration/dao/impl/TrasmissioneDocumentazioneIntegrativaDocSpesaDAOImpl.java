/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.Types;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioneIntegrativaDocSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl extends BaseDAO implements TrasmissioneDocumentazioneIntegrativaDocSpesaDAO {
	
	private final Logger LOG;
	
	public TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public HashMap<String, String> getInfoRichiestaIntegrazione(Integer identificativoRichiestaDiIntegrazione) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getInfoRichiestaIntegrazione]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		LOG.info(prf + ", identificativoRichiestaDiIntegrazione=" + identificativoRichiestaDiIntegrazione);
		try {
			String sql = "SELECT \n" +
					"ptis.DATA_INVIO,\n" +
					"ptis.ID_STATO_RICHIESTA,\n" +
					"ptis.ID_DICHIARAZIONE_SPESA\n" +
					"FROM PBANDI_T_INTEGRAZIONE_SPESA ptis\n" +
					"WHERE ID_INTEGRAZIONE_SPESA = :identificativoRichiestaDiIntegrazione";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoRichiestaDiIntegrazione", identificativoRichiestaDiIntegrazione, Types.INTEGER);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> info = new HashMap<>();
				while (rs.next()) {
					info.put("dtInvio", rs.getString("DATA_INVIO"));
					info.put("idStatoRichiesta", rs.getString("ID_STATO_RICHIESTA"));
					info.put("idDichiarazioneSpesa", rs.getString("ID_DICHIARAZIONE_SPESA"));
				}
				return info;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntegrativaSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntegrativaSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	@Override
	public Integer getIdEntitaDichSpesa() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdEntitaDichSpesa]";
		LOG.info(prf + " BEGIN");
		Integer idEntitaDichSpesa;
		try {
			String sql = "SELECT ID_ENTITA\n" +
					"FROM PBANDI_C_ENTITA pce\n" +
					"WHERE NOME_ENTITA = 'PBANDI_T_INTEGRAZIONE_SPESA'";
			
			LOG.debug(sql);
			idEntitaDichSpesa = this.queryForInteger(sql, null);
		} finally {
			LOG.info(prf + " END");
		}
		return idEntitaDichSpesa;
	}

	@Override
	public HashMap<String, String> getInfoSoggettoProgetto(String idDichiarazioneSpesa) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getInfoSoggettoProgetto]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		LOG.info(prf + ", idDichiarazioneSpesa=" + idDichiarazioneSpesa);
		try {
			String sql = "SELECT \n" +
					"PTDS.ID_PROGETTO,\n" +
					"PTDS.DT_DICHIARAZIONE,\n" +
					"PRSP.ID_SOGGETTO,\n" +
					"PTP.CODICE_VISUALIZZATO\n" +
					"FROM PBANDI_T_DICHIARAZIONE_SPESA PTDS\n" +
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTDS.ID_PROGETTO\n" +
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_PROGETTO = PTDS.ID_PROGETTO\n" +
					"WHERE PTDS.ID_DICHIARAZIONE_SPESA= :idDichiarazioneSpesa\n" +
					"AND PRSP.ID_TIPO_BENEFICIARIO = 1\t\n" +
					"AND PRSP.ID_TIPO_ANAGRAFICA <> 4";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDichiarazioneSpesa", idDichiarazioneSpesa, Types.INTEGER);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> info = new HashMap<>();
				if (rs.next()) {
					info.put("idProgetto", rs.getString("ID_PROGETTO"));
					info.put("idSoggetto", rs.getString("ID_SOGGETTO"));
					info.put("codiceVisualizzato", rs.getString("CODICE_VISUALIZZATO"));
					info.put("dtDichiarazioneSpesa", rs.getString("DT_DICHIARAZIONE"));
				}
				return info;
			});

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntegrativaSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntegrativaSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	@Override
	public Integer getIdFolderRichiestaIntegrazioneSpesa(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto){
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdFolderRichiestaIntegrazioneSpesa]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFolderPadre=" + idFolderPadre + ", nomeFolder=" + nomeFolder + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);
		Integer idFolderRichiestaIntegrazione;
		try {
			String sql = "SELECT ID_FOLDER\n" +
					"FROM PBANDI_T_FOLDER ptf\n" +
					"WHERE ID_SOGGETTO_BEN = :idSoggetto\n" +
					"AND ID_PROGETTO = :idProgetto\n" +
					"AND NOME_FOLDER = :nomeFolder\n" +
					"AND ID_PADRE = :idFolderPadre";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("nomeFolder", nomeFolder, Types.VARCHAR);
			param.addValue("idFolderPadre", idFolderPadre, Types.INTEGER);
			idFolderRichiestaIntegrazione = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFolderRichiestaIntegrazione;
	}

	@Override
	public Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto, String nomeFolder) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdFolderProgettoPadre]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto + ", nomeFolder=" + nomeFolder);
		Integer idFolderProgettoPadre;
		try {
			String sql = "SELECT ID_FOLDER\n" +
					"FROM PBANDI_T_FOLDER ptf\n" +
					"WHERE ID_SOGGETTO_BEN = :idSoggetto\n" +
					"AND ID_PROGETTO = :idProgetto\n" +
					"AND NOME_FOLDER = :nomeFolder";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("nomeFolder", nomeFolder, Types.VARCHAR);
			idFolderProgettoPadre = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFolderProgettoPadre;
	}

	@Override
	public Integer getIdFolderSoggettoPadre(String idSoggetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdFolderSoggettoPadre]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggetto=" + idSoggetto);
		Integer idFolderSoggettoPadre;
		try {
			String sql = "SELECT ID_FOLDER\n" +
					"FROM PBANDI_T_FOLDER ptf\n" +
					"WHERE ID_SOGGETTO_BEN = :idSoggetto\n" +
					"AND NOME_FOLDER = '/root'";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			idFolderSoggettoPadre = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFolderSoggettoPadre;
	}
	
	@Override
	public Integer getIdFolder() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdFolder] ";
		LOG.info(prf + " BEGIN");
		Integer id;
		try {
			String sql = "SELECT SEQ_PBANDI_T_FOLDER.NEXTVAL FROM DUAL";
			LOG.debug(sql);
			id = this.queryForInteger(sql, null);
			LOG.info("Nuovo id PBANDI_T_FOLDER = " + id);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return null;
		}
		return id;
	}
	
	@Override
	public void insertFolder(Integer idFolder, Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::insertFolder]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFolder=" + idFolder + ", idFolderPadre=" + idFolderPadre
				+ ", nomeFolder=" + nomeFolder + ", idSoggetto="
				+ idSoggetto + ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FOLDER (ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO, ID_PROGETTO)\n" +
					"VALUES(:idFolder, :idFolderPadre, :nomeFolder, :idSoggetto, 1, SYSDATE, :idProgetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFolder", idFolder, Types.INTEGER);
			param.addValue("idFolderPadre", idFolderPadre, Types.INTEGER);
			param.addValue("nomeFolder", nomeFolder, Types.VARCHAR);
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public Integer getIdDocIndex() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdDocIndex] ";
		LOG.info(prf + " BEGIN");
		Integer idDocIndex;
		try {
			String sql = "SELECT SEQ_PBANDI_T_DOCUMENTO_INDEX.NEXTVAL FROM DUAL";
			LOG.debug(sql);
			idDocIndex = this.queryForInteger(sql, null);
			LOG.info("Nuovo id PBANDI_T_DOCUMENTO_INDEX = " + idDocIndex);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return null;
		}
		return idDocIndex;
	}
	
	@Override
	public void insertDocumentoIndex(Integer idDocIndex, Integer idFolder, String nomeDocumento, String idProgetto, String idSoggetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::insertDocumentoIndex]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idDocIndex=" + idDocIndex + ", idFolder=" + idFolder + ", nomeDocumento=" + nomeDocumento
				+ ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_DOCUMENTO_INDEX (ID_DOCUMENTO_INDEX, ID_TARGET, ID_ENTITA, UUID_NODO, REPOSITORY, DT_INSERIMENTO_INDEX, ID_TIPO_DOCUMENTO_INDEX, ID_UTENTE_INS, NOME_FILE, NOME_DOCUMENTO, ID_PROGETTO, ID_STATO_DOCUMENTO, ID_SOGG_BENEFICIARIO)\n" +
					"VALUES(:idDocIndex, :idFolder, 360, 'UUID', 'AF', SYSDATE, 23, 1, :nomeFile, :nomeDocumento, :idProgetto, 1, :idSoggetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDocIndex", idDocIndex, Types.INTEGER);
			param.addValue("idFolder", idFolder, Types.INTEGER);
			param.addValue("nomeFile", nomeDocumento, Types.VARCHAR);
			param.addValue("nomeDocumento", nomeDocumento, Types.VARCHAR);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public Integer getIdFile() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getIdFile] ";
		LOG.info(prf + " BEGIN");
		Integer idFile;
		try {
			String sql = "SELECT SEQ_PBANDI_T_FILE.NEXTVAL FROM DUAL";
			LOG.debug(sql);
			idFile = this.queryForInteger(sql, null);
			LOG.info("Nuovo id PBANDI_T_FILE = " + idFile);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE: ", e);
			return null;
		}
		return idFile;
	}
	
	@Override
	public void insertFile(Integer idFile, Integer idFolder, Integer idDocumentoIndex, String nomeDocumento, int sizeFile) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::insertFile]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFile=" + idFile + ", idFolder=" + idFolder + ", idDocumentoIndex=" + idDocumentoIndex
				+ ", nomeDocumento=" + nomeDocumento + ", sizeFile=" + sizeFile);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FILE (ID_FILE, ID_FOLDER, ID_DOCUMENTO_INDEX, NOME_FILE, SIZE_FILE, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
					"VALUES(:idFile, :idFolder, :idDocumentoIndex, :nomeDocumento, :sizeFile, 1, SYSDATE)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFile", idFile, Types.INTEGER);
			param.addValue("idFolder", idFolder, Types.INTEGER);
			param.addValue("idDocumentoIndex", idDocumentoIndex, Types.INTEGER);
			param.addValue("nomeDocumento", nomeDocumento, Types.VARCHAR);
			param.addValue("sizeFile", sizeFile, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public void insertFileEntita(Integer idFile, Integer idEntitaDichSpesa, Integer idTarget, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::insertFileEntita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFile=" + idFile + ", idEntitaDichSpesa=" + idEntitaDichSpesa + ", idTarget=" + idTarget
				+ ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FILE_ENTITA (ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_PROGETTO)\n" +
					"VALUES(SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL, :idFile, :idEntitaDichSpesa, :idTarget, :idProgetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFile", idFile, Types.INTEGER);
			param.addValue("idEntitaDichSpesa", idEntitaDichSpesa, Types.INTEGER);
			param.addValue("idTarget", idTarget, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public void chiusuraRichiestaIntegrazione(Integer identificativoRichiestaDiIntegrazione) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::chiusuraRichiestaIntegrazione]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", identificativoRichiestaDiIntegrazione=" + identificativoRichiestaDiIntegrazione);
		
		try {
			String sql = "UPDATE PBANDI_T_INTEGRAZIONE_SPESA SET\n" +
					"DATA_INVIO = SYSDATE,\n" +
					"ID_UTENTE_INVIO = -20\n" +
					"WHERE ID_INTEGRAZIONE_SPESA= :identificativoRichiestaDiIntegrazione";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoRichiestaDiIntegrazione", identificativoRichiestaDiIntegrazione, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public HashMap<String, String> getTemplateNotifica() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::getTemplateNotifica]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		try {
			String sql = "SELECT COMP_MESSAGE,			\n" + 
					"       ID_TEMPLATE_NOTIFICA		\n" + 
					"FROM PBANDI_D_TEMPLATE_NOTIFICA	\n" + 
					"WHERE DESCR_BREVE_TEMPLATE_NOTIFICA = 'NotificaIntegrazioneDichiarazione'";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> info = new HashMap<>();
				while (rs.next()) {
					info.put("compMessage", rs.getString("COMP_MESSAGE"));
					info.put("idTemplateNotifica", String.valueOf(rs.getInt("ID_TEMPLATE_NOTIFICA")));
				}
				return info;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntegrativaSpesa", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntegrativaSpesa", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}
	
	@Override
	public void insertNotificaProcesso(String idProgetto, String compMessage, String idTemplateNotifica) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaDocSpesaDAOImpl::insertNotificaProcesso]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idProgetto=" + idProgetto + ", compMessage=" + compMessage + ", idTemplateNotifica="
				+ idTemplateNotifica);
		
		try {
			String sql = "INSERT INTO PBANDI_T_NOTIFICA_PROCESSO(ID_NOTIFICA, ID_PROGETTO, ID_RUOLO_DI_PROCESSO_DEST, SUBJECT_NOTIFICA, MESSAGE_NOTIFICA, MESSAGE_NOTIFICA2, STATO_NOTIFICA, ID_UTENTE_MITT, DT_NOTIFICA, ID_UTENTE_AGG, DT_AGG_STATO_NOTIFICA, ID_TEMPLATE_NOTIFICA, ID_ENTITA, ID_TARGET, FLAG_INVIO_MAIL) \n" + 
					"VALUES(SEQ_PBANDI_T_NOTIFICA_PROCESSO.NEXTVAL,					\n" + 
					"       :idProgetto,											\n" + 
					"       1,														\n" + 
					"       'Notifica integrazione dichiarazione di spesa',			\n" + 
					"       :compMessage,											\n" + 
					"       :compMessage,											\n" + 
					"       'I',													\n" + 
					"       -20,													\n" + 
					"       SYSDATE,												\n" + 
					"       NULL,													\n" + 
					"       NULL,													\n" + 
					"       :idTemplateNotifica,									\n" + 
					"       NULL,													\n" + 
					"       NULL,													\n" + 
					"       NULL)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("compMessage", compMessage, Types.VARCHAR);
			param.addValue("idTemplateNotifica", idTemplateNotifica, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

}
