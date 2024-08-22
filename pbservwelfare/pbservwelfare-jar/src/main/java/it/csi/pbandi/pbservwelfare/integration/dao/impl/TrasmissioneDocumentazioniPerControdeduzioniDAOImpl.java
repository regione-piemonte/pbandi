/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioniPerControdeduzioniDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;
import java.util.HashMap;

public class TrasmissioneDocumentazioniPerControdeduzioniDAOImpl extends BaseDAO implements TrasmissioneDocumentazioniPerControdeduzioniDAO {

	private final Logger LOG;

	public TrasmissioneDocumentazioniPerControdeduzioniDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public Integer getInfoDocumentazioneControdeduzioni(Integer identificativoControdeduzione) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getInfoDocumentazioneControdeduzioni]";
		LOG.info(prf + " BEGIN");
		Integer idStatoControdeduz;
		LOG.info(prf + ", identificativoControdeduzione=" + identificativoControdeduzione);
		try {
			String sql = "SELECT\n" +
					"NUMERO_CONTRODEDUZ,\n" +
					"ID_STATO_CONTRODEDUZ,\n" +
					"DT_STATO_CONTRODEDUZ\n" +
					"FROM PBANDI_T_CONTRODEDUZ ptc\n" +
					"WHERE ID_CONTRODEDUZ = :identificativoControdeduzione";
			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoControdeduzione", identificativoControdeduzione, Types.INTEGER);

			idStatoControdeduz = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				Integer stato = null;
				while (rs.next()) {
					stato = rs.getInt("ID_STATO_CONTRODEDUZ");
				}
				return stato;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return idStatoControdeduz;
	}

	@Override
	public Integer getIdEntitaControdeduzione() {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdEntitaControdeduzione]";
		LOG.info(prf + " BEGIN");
		Integer idEntitaControdeduzione;
		try {
			String sql = "SELECT ID_ENTITA\n" +
					"FROM PBANDI_C_ENTITA pce\n" +
					"WHERE NOME_ENTITA = 'PBANDI_T_CONTRODEDUZ'";
			
			LOG.debug(sql);
			idEntitaControdeduzione = this.queryForInteger(sql, null);
		} finally {
			LOG.info(prf + " END");
		}
		return idEntitaControdeduzione;
	}

	@Override
	public HashMap<String, String> getInfoSoggettoProgetto(Integer identificativoControdeduzione) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getInfoPerCreazioneFolder]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		LOG.info(prf + ", identificativoControdeduzione=" + identificativoControdeduzione);
		try {
			String sql = "SELECT \n" +
					"PTGR.ID_PROGETTO,\n" +
					"PRSP.ID_SOGGETTO,\n" +
					"PTP.CODICE_VISUALIZZATO\n" +
					"FROM PBANDI_T_GESTIONE_REVOCA PTGR\n" +
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTGR.ID_PROGETTO\n" +
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_PROGETTO = PRSP.ID_PROGETTO\n" +
					"WHERE PTGR.ID_GESTIONE_REVOCA= :identificativoControdeduzione\n" +
					"AND PRSP.ID_TIPO_BENEFICIARIO = 1\n" +
					"AND PRSP.ID_TIPO_ANAGRAFICA <> 4";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoControdeduzione", identificativoControdeduzione, Types.INTEGER);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> info = new HashMap<>();
				while (rs.next()) {
					info.put("idProgetto", String.valueOf(rs.getInt("ID_PROGETTO")));
					info.put("idSoggetto", String.valueOf(rs.getInt("ID_SOGGETTO")));
					info.put("codiceVisualizzato", rs.getString("CODICE_VISUALIZZATO"));
				}
				return info;
			});

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocControdeduzioni", e);
			throw new ErroreGestitoException("DaoException while trying to read DocControdeduzioni", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	@Override
	public Integer getIdFolderControdeduzioni(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto){
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdFolderControdeduzioni]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFolderPadre=" + idFolderPadre + ", nomeFolder=" + nomeFolder + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);
		Integer idFolderControdeduzioni;
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
			idFolderControdeduzioni = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFolderControdeduzioni;
	}

	@Override
	public Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdFolderProgettoPadre]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);
		Integer idFolderProgettoPadre;
		try {
			String sql = "SELECT ID_FOLDER\n" +
					"FROM PBANDI_T_FOLDER ptf\n" +
					"WHERE ID_SOGGETTO_BEN = :idSoggetto\n" +
					"AND ID_PROGETTO = :idProgetto";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			idFolderProgettoPadre = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFolderProgettoPadre;
	}
	
	@Override
	public Integer getIdFolder() {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdFolder] ";
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
	public Integer getIdFolderSoggettoPadre(String idSoggetto) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdFolderSoggettoPadre]";
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
	public void insertFolder(Integer idFolder, Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::insertFolder]";
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
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdDocIndex] ";
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
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::insertDocumentoIndex]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idDocIndex=" + idDocIndex + ", idFolder=" + idFolder + ", nomeDocumento=" + nomeDocumento
				+ ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_DOCUMENTO_INDEX (ID_DOCUMENTO_INDEX, ID_TARGET, ID_ENTITA, UUID_NODO, REPOSITORY, DT_INSERIMENTO_INDEX, ID_TIPO_DOCUMENTO_INDEX, ID_UTENTE_INS, NOME_FILE, ID_PROGETTO, ID_STATO_DOCUMENTO, ID_SOGG_BENEFICIARIO)\n" +
					"VALUES(:idDocIndex, :idFolder, 360, 'UUID', 'AF', SYSDATE, 23, 1, :nomeDocumento, :idProgetto, 1, :idSoggetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDocIndex", idDocIndex, Types.INTEGER);
			param.addValue("idFolder", idFolder, Types.INTEGER);
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
	public void insertFile(Integer idFolder, Integer idDocumentoIndex, String nomeDocumento, int sizeFile) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::insertFile]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFolder=" + idFolder + ", idDocumentoIndex=" + idDocumentoIndex + ", nomeDocumento=" + nomeDocumento + ", sizeFile=" + sizeFile);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FILE (ID_FILE, ID_FOLDER, ID_DOCUMENTO_INDEX, NOME_FILE, SIZE_FILE, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
					"VALUES(SEQ_PBANDI_T_FILE.NEXTVAL, :idFolder, :idDocumentoIndex, :nomeDocumento, :sizeFile, 1, SYSDATE)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
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
	public Integer getIdFile(Integer idFolder, Integer idDocumentoIndex, String nomeDocumento) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::getIdFile]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFolder=" + idFolder + ", idDocumentoIndex=" + idDocumentoIndex + ", nomeDocumento=" + nomeDocumento);
		Integer idFile;
		try {
			String sql = "SELECT ID_FILE\n" +
					"FROM PBANDI_T_FILE ptf\n" +
					"WHERE ID_FOLDER= :idFolder\n" +
					"AND ID_DOCUMENTO_INDEX= :idDocumentoIndex\n" +
					"AND NOME_FILE= :nomeDocumento";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFolder", idFolder, Types.INTEGER);
			param.addValue("idDocumentoIndex", idDocumentoIndex, Types.INTEGER);
			param.addValue("nomeDocumento", nomeDocumento, Types.VARCHAR);
			idFile = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return idFile;
	}

	@Override
	public void insertFileEntita(Integer idFile, Integer idEntitaControdeduz, Integer idTarget, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::insertFileEntita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFile=" + idFile + ", idEntitaControdeduz=" + idEntitaControdeduz + ", idTarget=" + idTarget + ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FILE_ENTITA (ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_PROGETTO)\n" +
					"VALUES(SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL, :idFile, :idEntitaControdeduz, :idTarget, :idProgetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFile", idFile, Types.INTEGER);
			param.addValue("idEntitaControdeduz", idEntitaControdeduz, Types.INTEGER);
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
	public void updateStatoAttivita(Integer identificativoControdeduzione) {
		String prf = "[TrasmissioneDocumentazioniPerControdeduzioniDAOImpl::updateStatoAttivita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", identificativoControdeduzione=" + identificativoControdeduzione);
		
		try {
			// Update PBANDI_T_CONTRODEDUZ
			String sqlUpdateStatoControdeduz =
					"UPDATE PBANDI_T_CONTRODEDUZ SET \n" +
					"ID_STATO_CONTRODEDUZ = 2\n" +
					"WHERE ID_TARGET= :identificativoControdeduzione";
			
			LOG.debug(sqlUpdateStatoControdeduz);
			MapSqlParameterSource paramControdeduz = new MapSqlParameterSource();
			paramControdeduz.addValue("identificativoControdeduzione", identificativoControdeduzione, Types.INTEGER);
			this.update(sqlUpdateStatoControdeduz, paramControdeduz);
			
			// Update PBANDI_T_GESTIONE_REVOCA
			String sqlUpdateAttivitaRevoca =
					"UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
					"ID_ATTIVITA_REVOCA = 6\n" +
					"WHERE ID_GESTIONE_REVOCA = :identificativoControdeduzione";
			
			LOG.debug(sqlUpdateAttivitaRevoca);
			MapSqlParameterSource paramAttivitaRevoca = new MapSqlParameterSource();
			paramAttivitaRevoca.addValue("identificativoControdeduzione", identificativoControdeduzione, Types.INTEGER);
			this.update(sqlUpdateAttivitaRevoca, paramAttivitaRevoca);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

}
