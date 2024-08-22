/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.TrasmissioneDocumentazioneIntegrativaRevocaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;
import java.util.HashMap;

public class TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl extends BaseDAO implements TrasmissioneDocumentazioneIntegrativaRevocaDAO {
	
	private final Logger LOG;

	public TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public Integer getInfoDocumentazioneIntRevoca(Integer identificativoProcedimentoDiRevoca) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getInfoDocumentazioneIntRevoca]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", identificativoProcedimentoDiRevoca=" + identificativoProcedimentoDiRevoca);
		Integer idStatoRichiesta;
		try {
			String sql = "SELECT\n" +
					"DT_RICHIESTA,\n" +
					"ID_UTENTE_RICHIESTA,\n" +
					"NUM_GIORNI_SCADENZA,\n" +
					"ID_STATO_RICHIESTA\n" +
					"FROM PBANDI_T_RICHIESTA_INTEGRAZ ptri\n" +
					"WHERE ID_RICHIESTA_INTEGRAZ= :identificativoProcedimentoDiRevoca";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoProcedimentoDiRevoca", identificativoProcedimentoDiRevoca, Types.INTEGER);
			idStatoRichiesta = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				Integer stato = null;
				while (rs.next()) {
					stato = rs.getInt("ID_STATO_RICHIESTA");
				}
				return stato;
			});
		} finally {
			LOG.info(prf + " END");
		}

		return idStatoRichiesta;
	}

	@Override
	public Integer getIdEntitaIntRevoca() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdEntitaIntRevoca]";
		LOG.info(prf + " BEGIN");
		Integer idEntitaIntRevoca;
		try {
			String sql = "SELECT ID_ENTITA\n" +
					"FROM PBANDI_C_ENTITA pce\n" +
					"WHERE NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ'";
			
			LOG.debug(sql);
			idEntitaIntRevoca = this.queryForInteger(sql, null);
		} finally {
			LOG.info(prf + " END");
		}
		return idEntitaIntRevoca;
	}

	@Override
	public HashMap<String, String> getInfoPerCreazioneFolder(Integer identificativoProcedimentoDiRevoca) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getInfoPerCreazioneFolder]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		LOG.info(prf + ", identificativoProcedimentoDiRevoca=" + identificativoProcedimentoDiRevoca);
		try {
			String sql = "SELECT\n" +
					"PTGR.ID_PROGETTO,\n" +
					"PRSP.ID_SOGGETTO,\n" +
					"PTP.CODICE_VISUALIZZATO\n" +
					"FROM PBANDI_T_GESTIONE_REVOCA PTGR\n" +
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTGR.ID_PROGETTO\n" +
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_PROGETTO = PRSP.ID_PROGETTO\n" +
					"WHERE PTGR.ID_GESTIONE_REVOCA= :identificativoProcedimentoDiRevoca\n" +
					"AND PRSP.ID_TIPO_BENEFICIARIO = 1\n" +
					"AND PRSP.ID_TIPO_ANAGRAFICA <> 4";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("identificativoProcedimentoDiRevoca", identificativoProcedimentoDiRevoca, Types.INTEGER);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> info = new HashMap<>();
				while (rs.next()) {
					info.put("idProgetto", String.valueOf(rs.getInt("ID_PROGETTO")));
					info.put("idSoggetto", String.valueOf(rs.getInt("ID_SOGGETTO")));
					info.put("codiceVisualizzato", rs.getString("CODICE_VISUALIZZATO"));
				}
				return info;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DocIntRevoca", e);
			throw new ErroreGestitoException("DaoException while trying to read DocIntRevoca", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	@Override
	public Integer getIdFolderRichiestaIntegrazione(Integer idFolderPadre, String nomeFolder, String idSoggetto, String idProgetto){
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdFolderRichiestaIntegrazione]";
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
	public Integer getIdFolderProgettoPadre(String idSoggetto, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdFolderProgettoPadre]";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdFolder] ";
		LOG.info(prf + " BEGIN");
		Integer id;
		try {
			String sql = "SELECT SEQ_PBANDI_T_FOLDER.NEXTVAL FROM DUAL ";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdFolderSoggettoPadre]";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::insertFolder]";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdDocIndex] ";
		LOG.info(prf + " BEGIN");
		Integer idDocIndex;
		try {
			String sql = "SELECT SEQ_PBANDI_T_DOCUMENTO_INDEX.NEXTVAL FROM DUAL ";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::insertDocumentoIndex]";
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
	public Integer getIdFile() {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::getIdFile] ";
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
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::insertFile]";
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
	public void insertFileEntita(Integer idFile, Integer idEntitaRevoca, Integer idTarget, String idProgetto) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::insertFileEntita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idFile=" + idFile + ", idEntitaRevoca=" + idEntitaRevoca + ", idTarget=" + idTarget
				+ ", idProgetto=" + idProgetto);
		
		try {
			String sql = "INSERT INTO PBANDI_T_FILE_ENTITA (ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_PROGETTO)\n" +
					"VALUES(SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL, :idFile, :idEntitaRevoca, :idTarget, :idProgetto)";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idFile", idFile, Types.INTEGER);
			param.addValue("idEntitaRevoca", idEntitaRevoca, Types.INTEGER);
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
	public void updateStatoAttivita(Integer identificativoProcedimentoDiRevoca) {
		String prf = "[TrasmissioneDocumentazioneIntegrativaRevocaDAOImpl::updateStatoAttivita]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", identificativoProcedimentoDiRevoca=" + identificativoProcedimentoDiRevoca);
		
		try {
			// Update PBANDI_T_RICHIESTA_INTEGRAZ
			String sqlUpdateStatoIntegrazione =
					"UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET\n" +
					"ID_STATO_RICHIESTA = 2\n" +
					"WHERE ID_RICHIESTA_INTEGRAZ= :identificativoProcedimentoDiRevoca\n";
			
			LOG.debug(sqlUpdateStatoIntegrazione);
			MapSqlParameterSource paramIntegraz = new MapSqlParameterSource();
			paramIntegraz.addValue("identificativoProcedimentoDiRevoca", identificativoProcedimentoDiRevoca, Types.INTEGER);
			this.update(sqlUpdateStatoIntegrazione, paramIntegraz);
			
			// Update PBANDI_T_GESTIONE_REVOCA
			String sqlUpdateAttivitaRevoca =
					"UPDATE PBANDI_T_GESTIONE_REVOCA SET\n" +
					"ID_ATTIVITA_REVOCA = 7\n" +
					"WHERE ID_GESTIONE_REVOCA= :identificativoProcedimentoDiRevoca\n";
			
			LOG.debug(sqlUpdateAttivitaRevoca);
			MapSqlParameterSource paramAttivitaRevoca = new MapSqlParameterSource();
			paramAttivitaRevoca.addValue("identificativoProcedimentoDiRevoca", identificativoProcedimentoDiRevoca, Types.INTEGER);
			this.update(sqlUpdateAttivitaRevoca, paramAttivitaRevoca);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

}
