/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.manager;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.util.DateUtil;
import it.csi.pbandi.pbgestfinbo.util.StringUtil;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

public class DocumentoManager  extends JdbcDaoSupport{

	
	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	
	protected FileApiServiceImpl fileApiServiceImpl; 
	
	@Autowired
	public  DocumentoManager(DataSource dataSource) {
		setDataSource(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}

	// Cancella il file su file system e su PBANDI_T_DOCUMENTO_INDEX e cancella le Visibilita.
	// Si tratta dei file caricati con salvaFileConVisibilita().
	@Transactional
	public boolean cancellaFileConVisibilita(BigDecimal idDocumentoIndex) {
		String prf = "[DocumentoManager::cancellaFileConVisibilita] ";
		logger.info(prf + " BEGIN");
		boolean esito = false;
		try {

			if (fileApiServiceImpl == null) {
				logger.error(prf+"fileApiServiceImpl non istanziato.");
				return false;
			}
			if (idDocumentoIndex == null || idDocumentoIndex.intValue() == 0) {
				logger.error(prf+"idDocumentoIndex non valorizzato.");
				return false;
			}

			// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
			String sql = "SELECT NOME_DOCUMENTO, REPOSITORY FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.longValue();
			logger.info("\n"+sql);
			PbandiTDocumentoIndexVO vo = (PbandiTDocumentoIndexVO) getJdbcTemplate().queryForObject(sql, new BeanRowMapper(PbandiTDocumentoIndexVO.class));

			// Cancella da file system.
			esito = fileApiServiceImpl.deleteFile(vo.getNomeDocumento(), vo.getRepository());
			if (!esito) {
				logger.error(prf+"Cancellazione da file system fallita.");
				return false;
			}

			// Cancello da PBANDI_R_CATEG_ANAG_DOC_INDEX.
			String sql2 = "DELETE FROM PBANDI_R_CATEG_ANAG_DOC_INDEX WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.longValue();
			logger.info("\n"+sql2);
			getJdbcTemplate().update(sql2);

			// Cancella da PBANDI_T_DOCUMENTO_INDEX.
			String sql1 = "DELETE FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.longValue();
			logger.info("\n"+sql1);
			int numRecord = getJdbcTemplate().update(sql1);
			if (numRecord == 0) {
				logger.error(prf+"Cancellazione su PBANDI_T_DOCUMENTO_INDEX fallita.");
				return false;
			}

		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		return esito;
	}
	public boolean salvaFileConVisibilita(byte[] file, DocumentoIndexVO vo, ArrayList<Long> elencoIdCategAnag) {
		String prf = "[DocumentoManager::salvaFileConVisibilita] ";
		logger.info(prf + " BEGIN");
		try {
			
			if (file == null) {
				logger.error(prf+"file = NULL.");
				return false;
			}
			if (file.length == 0) {
				logger.error(prf+"file vuoto.");
				return false;
			}
			if (vo == null) {
				logger.error(prf+"PbandiTDocumentoIndexVO = NULL.");
				return false;
			}
			if (StringUtil.isEmpty(vo.getNomeFile())) {
				logger.error(prf+"PbandiTDocumentoIndexVO.NomeFile vuoto.");
				return false;
			}
//			if (elencoIdCategAnag == null || elencoIdCategAnag.size() == 0) {
//				logger.error(prf+"Visibilita' mancanti.");
//				return false;
//			}

			logger.info(prf+"file.length = "+file.length);
			logger.info(prf+vo.toString());
			
			// Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
			BigDecimal idTDocumentoIndex = nuovoIdTDocumentoIndex();
			if (idTDocumentoIndex == null) {
				logger.error(prf+"Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.");
				return false;
			}
			
			// Completo alcuni campi.
			vo.setIdDocumentoIndex(idTDocumentoIndex);			
			vo.setUuidNodo("UUID");	// Ci metto una stringa a caso, poichè su db il campo è obbligatorio.
			java.sql.Date oggi = DateUtil.getSysdate();
			vo.setDtInserimentoIndex(oggi);
			
			// Nome univoco con cui il file verrà salvato su File System.
			String newName = vo.getNomeFile();
			vo.setNomeDocumento(newName.replaceFirst("\\.", "_"+idTDocumentoIndex.longValue()+"."));			
			
			// PASSO 1 : Salvataggio file su file system.
			InputStream is = new ByteArrayInputStream(file);
			boolean esitoSalva = salvaSuFileSystem(is, vo.getNomeDocumento(), vo.getRepository());
			if (!esitoSalva) {
				logger.error(prf+"Salvataggio su file system fallito: termino la procedura.");
				return false;
			}
			
			// PASSO 2 : Insert in PBANDI_T_DOCUMENTO_INDEX.
			boolean esitoInsert = insertTDocumentoIndex(vo);
			if (!esitoInsert) {
				logger.error(prf+"Inserimento in PBANDI_T_DOCUMENTO_INDEX fallito: cancello il file da file system e termino.");
				cancellaSuFileSystem(vo.getNomeDocumento(), vo.getRepository());
				return false;
			}
			
			// PASSO 3 : Insert in PBANDI_R_CATEG_ANAG_DOC_INDEX.
//			logger.info(prf+"Inserimento delle visibilita su PBANDI_R_CATEG_ANAG_DOC_INDEX");			
//			for (Long idCategAnag : elencoIdCategAnag) {
//				if (idCategAnag != null) {					
//					String sql = "INSERT INTO PBANDI_R_CATEG_ANAG_DOC_INDEX (ID_CATEG_ANAGRAFICA, ID_DOCUMENTO_INDEX) ";
//					sql += "VALUES ("+idCategAnag+", "+vo.getIdDocumentoIndex()+") ";
//					logger.info(prf + "\n" + sql);					
//					getJdbcTemplate().update(sql.toString());								
//				}
//			}	
			
		} catch (Exception e) {
			logger.error(prf+"Si è verificato un errore: cancello il file da file system e rilancio l'eccezione per il rolback.");
			cancellaSuFileSystem(vo.getNomeDocumento(), vo.getRepository());
			throw e;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return true;
	}
	private BigDecimal nuovoIdTDocumentoIndex() {
		String prf = "[DocumentoManager::nuovoIdTDocumentoIndex] ";
		logger.info(prf + " BEGIN");
		BigDecimal id = null;
		try {
			String sqlSeq = "select SEQ_PBANDI_T_DOCUMENTO_INDEX.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			logger.info("Nuovo id PBANDI_T_DOCUMENTO_INDEX = "+id.longValue());
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return null;
		}
		return id;
	}
	
	public boolean salvaSuFileSystem(InputStream inputStream, String strFileName, String strTipoDocumento) {
		String prf = "[DocumentoManager::salvaFileSuFileSystem] ";
		logger.info(prf + " BEGIN");
		boolean esito = false;
		try {
			
//			if (fileApiServiceImpl == null) {
//				logger.error(prf+"fileApiServiceImpl non istanziato.");
//				return false;
//			}
			
			// Se il file da salvare esiste già (es: secondo tentativo di upload di file firmato), 
			// allora fileApiServiceImpl.uploadFile() 
			// - stampa nel log una sua eccezione
			// - NON salva il file
			// - restituisce comunque TRUE.
			// Per ovviare, prima di inserire, controllo se il file esiste e nel caso lo cancello.
			boolean fileEsisteGia = fileApiServiceImpl.fileExists(strFileName, strTipoDocumento);
			logger.info(prf+"fileEsisteGia = "+fileEsisteGia);
			if (fileEsisteGia)
				fileApiServiceImpl.deleteFile(strFileName, strTipoDocumento);
			
			//esito = fileApiServiceImpl.uploadFile(inputStream, strFileName, strTipoDocumento);
			//logger.info(prf+"esito fileApiServiceImpl.uploadFile = "+esito);
			if (!esito) {
				// Se la cartella non esiste, la creo e riprovo.
				logger.info(prf+"Verifico se la cartella "+strTipoDocumento+" esiste su file system");
				esito = fileApiServiceImpl.dirExists(strTipoDocumento, true);
				logger.info(prf+"esito esistenza cartella = "+esito);
				if (esito) {
					esito = fileApiServiceImpl.uploadFile(inputStream, strFileName, strTipoDocumento);
				}
			}
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		logger.info(prf + " END");
		return esito;
	}
	private boolean insertTDocumentoIndex(DocumentoIndexVO vo) {
		String prf = "[DocumentoManager::insertTDocumentoIndex] ";
		logger.info(prf + " BEGIN");
		boolean esito = true;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
			sql.append("(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,");
			sql.append("ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_PROGETTO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
			sql.append("ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO, ID_DOMANDA,ID_SOGG_BENEFICIARIO, FLAG_VISIBILE_BEN)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.info("\n"+sql);
			
			Object[] params = new Object[26];
			params[0] = vo.getIdDocumentoIndex();
			params[1] = vo.getDtAggiornamentoIndex();
			params[2] = vo.getDtInserimentoIndex();
			params[3] = vo.getDtMarcaTemporale();
			params[4] = vo.getIdCategAnagraficaMitt();
			params[5] = vo.getIdEntita();
			params[6] = vo.getIdModello();
			params[7] = vo.getIdProgetto();
			params[8] = vo.getIdSoggDelegato();
			params[9] = vo.getIdSoggRapprLegale();
			params[10] = vo.getIdStatoDocumento();
			params[11] = vo.getIdTarget();
			params[12] = vo.getIdTipoDocumentoIndex();
			params[13] = vo.getIdUtenteAgg();
			params[14] = vo.getIdUtenteIns();
			params[15] = vo.getMessageDigest();
			params[16] = vo.getNomeFile();
			params[17] = vo.getNoteDocumentoIndex();
			params[18] = vo.getNumProtocollo();
			params[19] = vo.getRepository();
			params[20] = vo.getUuidNodo();
			params[21] = vo.getVersione();
			params[22] = vo.getNomeDocumento();
			params[23] = vo.getIdDomanda();
			params[24] = vo.getIdSoggettoBenef();
			params[25] = vo.getFlagVisibileBen();
			
			getJdbcTemplate().update(sql.toString(), params);
			
			logger.info(prf+"inserito record inPBANDI_T_DOCUMENTO_INDEX con id = "+vo.getIdDocumentoIndex());
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		return esito;
	}
	private boolean cancellaSuFileSystem(String strFileName, String strTipoDocumento) {
		String prf = "[DocumentoManager::cancellaSuFileSystem] ";
		logger.info(prf + " BEGIN");
		boolean esito = false;
		try {

			if (fileApiServiceImpl == null) {
				logger.error(prf+"fileApiServiceImpl non istanziato.");
				return false;
			}
			
			esito = fileApiServiceImpl.deleteFile(strFileName, strTipoDocumento);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		return esito;
	}
	private File leggiSuFileSystem(String strFileName, String strTipoDocumento) {
		String prf = "[DocumentoManager::leggiSuFileSystem] ";
		logger.info(prf + " BEGIN");
		File file = null;
		try {
			
			if (fileApiServiceImpl == null) {
				logger.error(prf+"fileApiServiceImpl non istanziato.");
				return null;
			}
			
			file = fileApiServiceImpl.downloadFile(strFileName, strTipoDocumento);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return null;
		}
		return file;
	}
	// Legge un file da file system.	
	public FileDTO leggiFile(long idDocumentoIndex) {
		String prf = "[DocumentoManager::leggiFile] ";
		logger.info(prf + " BEGIN");
		FileDTO fileDTO = null;
		try {
			
			if (idDocumentoIndex == 0) {
				logger.error(prf+"idDocumentoIndex non valorizzato.");
				return null;
			}

			logger.info(prf+"idDocumentoIndex = "+idDocumentoIndex);
			
			// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO vo = trovaTDocumentoIndex(idDocumentoIndex);
			
			// legge il file da file system.
			logger.info(prf+"leggo il file da file system con NomeDocumento = "+vo.getNomeDocumento()+" e Repository = "+vo.getRepository());
			File file = leggiSuFileSystem(vo.getNomeDocumento(), vo.getRepository());
			if (file == null) {
				logger.error(prf+"Lettura file su system fallita.");
				return null;
			}
			
			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null - inizio.
			try {
				if (vo.getIdTipoDocumentoIndex() != null && StringUtil.isEmpty(vo.getMessageDigest())) {
					
					String sqlCTipoDoc = "SELECT FLAG_FIRMABILE FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = "+vo.getIdTipoDocumentoIndex().intValue();
					logger.info("\n"+sqlCTipoDoc);			
					String flagFirmable = (String) getJdbcTemplate().queryForObject(sqlCTipoDoc, String.class);
					
					if ("S".equalsIgnoreCase(flagFirmable))  {
						logger.error(prf+"MessageDigest non valorizzato e flagFirmable = S: calcolo MessageDigest e lo memorizzo su PBANDI_T_DOCUMENTO_INDEX.");
						
						byte[] b = FileUtils.readFileToByteArray(file);
						@SuppressWarnings("deprecation")
						String shaHex = DigestUtils.shaHex(b);
						
						String sqlUpd = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET MESSAGE_DIGEST = '"+shaHex+"' WHERE ID_DOCUMENTO_INDEX = "+vo.getIdDocumentoIndex().intValue();
						logger.info("\n"+sqlUpd);
						getJdbcTemplate().update(sqlUpd);
					}
									
				}
			} catch (Exception e) {
				logger.error(prf+" ERRORE nel calcolo di PBANDI_T_DOCUMENTO_INDEX.MESSAGE_DIGEST: ", e);
			}									
			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null - fine.
			
			fileDTO = new it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO();
			fileDTO.setBytes(FileUtil.getBytesFromFile(file));
			fileDTO.setNomeFile(vo.getNomeFile());
			fileDTO.setSizeFile(file.length());
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return null;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return fileDTO;
	}
	
	// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
	public PbandiTDocumentoIndexVO trovaTDocumentoIndex(long idDocumentoIndex) {
		String sql = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = " + idDocumentoIndex;
		logger.info("\n"+sql);			
		PbandiTDocumentoIndexVO vo = (PbandiTDocumentoIndexVO) getJdbcTemplate().queryForObject(sql, new BeanRowMapper(PbandiTDocumentoIndexVO.class));
		return vo;
	}
}
