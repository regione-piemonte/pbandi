/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.manager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.DateUtil;
import it.csi.pbandi.pbservizit.util.FileUtil;
import it.csi.pbandi.pbservizit.util.StringUtil;


//	Classe copiata da pbweb portando ArchivioFile in pbservizit.


public class DocumentoManager  extends JdbcDaoSupport {
	
	public static final String ROOT_FILE_SYSTEM = "/pbstorage_online";
	
	private Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);
	
	protected FileApiServiceImpl fileApiServiceImpl;
	
	@Autowired
	public DocumentoManager(DataSource dataSource) {
		setDataSource(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;		
	
	
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
			
			fileDTO = new FileDTO();
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
	
	// Legge un file firmato da file system.	
	public FileDTO leggiFileFirmato(long idDocumentoIndex) {
		String prf = "[DocumentoManager::leggiFileFirmato] ";
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
			if (StringUtils.isBlank(vo.getNomeDocumentoFirmato())) {
				logger.error(prf+"NomeDocumentoFirmato non valorizzato.");
				return null;
			}			
			
			// legge il file da file system.
			logger.info(prf+"leggo il file da file system con NomeDocumentoFirmato = "+vo.getNomeDocumentoFirmato()+" e Repository = "+vo.getRepository());
			File file = leggiSuFileSystem(vo.getNomeDocumentoFirmato(), vo.getRepository());
			if (file == null) {
				logger.error(prf+"Lettura file su system fallita.");
				return null;
			}			
			
			fileDTO = new FileDTO();
			fileDTO.setBytes(FileUtil.getBytesFromFile(file));
			fileDTO.setNomeFile(vo.getNomeFile()+".p7m");
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
	
	// Legge un file firmato da file system.	
	public FileDTO leggiFileFirmaAutografa(long idDocumentoIndex) {
		String prf = "[DocumentoManager::leggiFileFirmaAutografa] ";
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
			if (StringUtils.isBlank(vo.getNomeDocumentoFirmato())) {
				logger.error(prf+"NomeDocumentoFirmato non valorizzato.");
				return null;
			}			
			
			// legge il file da file system.
			logger.info(prf+"leggo il file da file system con NomeDocumentoFirmato = "+vo.getNomeDocumentoFirmato()+" e Repository = "+vo.getRepository());
			File file = leggiSuFileSystem(vo.getNomeDocumentoFirmato(), vo.getRepository());
			if (file == null) {
				logger.error(prf+"Lettura file su system fallita.");
				return null;
			}			
			
			fileDTO = new FileDTO();
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
		
	
	// Legge un file firmato da file system.	
	public FileDTO leggiFileMarcato(long idDocumentoIndex) {
		String prf = "[DocumentoManager::leggiFileMarcato] ";
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
			if (StringUtils.isBlank(vo.getNomeDocumentoMarcato())) {
				logger.error(prf+"NomeDocumentoMarcato non valorizzato.");
				return null;
			}			
			
			// legge il file da file system.
			logger.info(prf+"leggo il file da file system con NomeDocumentoMarcato = "+vo.getNomeDocumentoMarcato()+" e Repository = "+vo.getRepository());
			File file = leggiSuFileSystem(vo.getNomeDocumentoMarcato(), vo.getRepository());
			if (file == null) {
				logger.error(prf+"Lettura file su system fallita.");
				return null;
			}			
			
			fileDTO = new FileDTO();
			fileDTO.setBytes(FileUtil.getBytesFromFile(file));
			fileDTO.setNomeFile(vo.getNomeFile()+".p7m.tsd");
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
	
	// Salva il file su file system e inserisce un record nella PBANDI_T_DOCUMENTO_INDEX.
	public boolean salvaFile(byte[] file, PbandiTDocumentoIndexVO vo) {
		String prf = "[DocumentoManager::salvaFile] ";
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
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return true;
	}

	public boolean salvaFileSenzaInsert(byte[] file, PbandiTDocumentoIndexVO vo) {
		String prf = "[DocumentoManager::salvaFileSenzaInsert] ";
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

			logger.info(prf+"file.length = "+file.length);
			logger.info(prf+vo.toString());

			// Id del nuovo record della PBANDI_T_DOCUMENTO_INDEX.
			BigDecimal idTDocumentoIndex = vo.getIdDocumentoIndex();

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

			// PASSO 2 : Update in PBANDI_T_DOCUMENTO_INDEX.
			int esitoUpdate = getJdbcTemplate().update("UPDATE PBANDI_T_DOCUMENTO_INDEX SET NOME_DOCUMENTO = ? WHERE ID_DOCUMENTO_INDEX = ?", new Object[]{vo.getNomeDocumento(), idTDocumentoIndex});
			logger.info(esitoUpdate+" record aggiornati in PBANDI_T_DOCUMENTO_INDEX");

		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
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
	
	private boolean insertTDocumentoIndex(PbandiTDocumentoIndexVO vo) {
		String prf = "[DocumentoManager::insertTDocumentoIndex] ";
		logger.info(prf + " BEGIN");
		boolean esito = true;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_DOCUMENTO_INDEX ");
			sql.append("(ID_DOCUMENTO_INDEX,DT_AGGIORNAMENTO_INDEX,DT_INSERIMENTO_INDEX,DT_MARCA_TEMPORALE,DT_VERIFICA_FIRMA,FLAG_FIRMA_CARTACEA,");
			sql.append("ID_CATEG_ANAGRAFICA_MITT,ID_ENTITA,ID_MODELLO,ID_PROGETTO,ID_SOGG_DELEGATO,ID_SOGG_RAPPR_LEGALE,ID_STATO_DOCUMENTO,");
			sql.append("ID_TARGET,ID_TIPO_DOCUMENTO_INDEX,ID_UTENTE_AGG,ID_UTENTE_INS,MESSAGE_DIGEST,NOME_FILE,NOTE_DOCUMENTO_INDEX,");
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.info("\n"+sql);
			
			Object[] params = new Object[25];
			params[0] = vo.getIdDocumentoIndex();
			params[1] = vo.getDtAggiornamentoIndex();
			params[2] = vo.getDtInserimentoIndex();
			params[3] = vo.getDtMarcaTemporale();
			params[4] = vo.getDtVerificaFirma();
			params[5] = vo.getFlagFirmaCartacea();
			params[6] = vo.getIdCategAnagraficaMitt();
			params[7] = vo.getIdEntita();
			params[8] = vo.getIdModello();
			params[9] = vo.getIdProgetto();
			params[10] = vo.getIdSoggDelegato();
			params[11] = vo.getIdSoggRapprLegale();
			params[12] = vo.getIdStatoDocumento();
			params[13] = vo.getIdTarget();
			params[14] = vo.getIdTipoDocumentoIndex();
			params[15] = vo.getIdUtenteAgg();
			params[16] = vo.getIdUtenteIns();
			params[17] = vo.getMessageDigest();
			params[18] = vo.getNomeFile();
			params[19] = vo.getNoteDocumentoIndex();
			params[20] = vo.getNumProtocollo();
			params[21] = vo.getRepository();
			params[22] = vo.getUuidNodo();
			params[23] = vo.getVersione();
			params[24] = vo.getNomeDocumento();
			
			getJdbcTemplate().update(sql.toString(), params);
			
			logger.info(prf+"inserito record inPBANDI_T_DOCUMENTO_INDEX con id = "+vo.getIdDocumentoIndex());
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		return esito;
	}
	
	public boolean salvaSuFileSystem(InputStream inputStream, String strFileName, String strTipoDocumento) {
		String prf = "[DocumentoManager::salvaFileSuFileSystem] ";
		logger.info(prf + " BEGIN");
		boolean esito = false;
		try {
			
			if (fileApiServiceImpl == null) {
				logger.error(prf+"fileApiServiceImpl non istanziato.");
				return false;
			}
			
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
			
			esito = fileApiServiceImpl.uploadFile(inputStream, strFileName, strTipoDocumento);
			logger.info(prf+"esito fileApiServiceImpl.uploadFile = "+esito);
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
	
	// Cancella il file su file system e su PBANDI_T_DOCUMENTO_INDEX.
	@Transactional
	public boolean cancellaFile(BigDecimal idDocumentoIndex) {
		String prf = "[DocumentoManager::cancellaFile] ";
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
	
	// Salva il file firmato su file system e aggiorna la PBANDI_T_DOCUMENTO_INDEX.
	// Ex DigitalSignAction.upload() + DigitalSignBusinessImpl.uploadSignedFile().
	public boolean salvaFileFirmato(byte[] file, String nomeFile, Long idDocumentoIndex, Long idUtente) {
		String prf = "[DocumentoManager::salvaFileFirmato] ";
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
			if (StringUtil.isEmpty(nomeFile)) {
				logger.error(prf+"nomeFile vuoto.");
				return false;
			}
			if (idDocumentoIndex == null) {
				logger.error(prf+"idDocumentoIndex = NULL.");
				return false;
			}
			
			logger.info(prf+"file.length = "+file.length+"; nomeFile = "+nomeFile+"; idDocumentoIndex = "+idDocumentoIndex);
			
			BigDecimal idStatoDocIndexAcquisito = this.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_ACQUISITO);
			if (idStatoDocIndexAcquisito == null) {
				logger.error(prf+"idStatoDocIndexAcquisito non trovato");
				return false;
			}
			
			// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO vo = trovaTDocumentoIndex(idDocumentoIndex);
			if (vo == null) {
				logger.error(prf+"record TDocumentoIndex non trovato");
				return false;
			}
						
			// Nome univoco con cui il file verrà salvato su File System.
			String newNomeFile = nomeFile.replaceFirst("\\.", "_"+idDocumentoIndex+".");
			
			// PASSO 1 : Salvataggio file su file system.
			InputStream is = new ByteArrayInputStream(file);
			boolean esitoSalva = salvaSuFileSystem(is, newNomeFile, vo.getRepository());
			if (!esitoSalva) {
				logger.error(prf+"Salvataggio su file system fallito: termino la procedura.");
				return false;
			}
			
			// PASSO 2 : Update in PBANDI_T_DOCUMENTO_INDEX.
			String sqlUpd = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET DT_AGGIORNAMENTO_INDEX = SYSDATE, FLAG_FIRMA_CARTACEA = 'N', ";
			sqlUpd += "ID_STATO_DOCUMENTO = "+idStatoDocIndexAcquisito.intValue()+", ID_UTENTE_AGG = "+idUtente.intValue()+", ";
			sqlUpd += "NOME_DOCUMENTO_FIRMATO = '"+newNomeFile+"' WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.intValue();
			logger.info("\n"+sqlUpd);
			try {
				getJdbcTemplate().update(sqlUpd);
			} catch (Exception e) {
				logger.error(prf+"Update in PBANDI_T_DOCUMENTO_INDEX fallita: cancello il file da file system e termino.");
				cancellaSuFileSystem(newNomeFile, vo.getRepository());
				return false;
			}
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return true;
	}
	
	// Salva il file firmato su file system e aggiorna la PBANDI_T_DOCUMENTO_INDEX.
	// Ex DigitalSignAction.upload() + DigitalSignBusinessImpl.uploadSignedFile().
	public boolean salvaFileFirmaAutografa(byte[] file, String nomeFile, Long idDocumentoIndex, Long idUtente) {
		String prf = "[DocumentoManager::salvaFileFirmaAutografa] ";
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
			if (StringUtil.isEmpty(nomeFile)) {
				logger.error(prf+"nomeFile vuoto.");
				return false;
			}
			if (idDocumentoIndex == null) {
				logger.error(prf+"idDocumentoIndex = NULL.");
				return false;
			}
			
			logger.info(prf+"file.length = "+file.length+"; nomeFile = "+nomeFile+"; idDocumentoIndex = "+idDocumentoIndex);
			
			BigDecimal idStatoDocIndexAcquisito = decodificheDAOImpl.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO);
			if (idStatoDocIndexAcquisito == null) {
				logger.error(prf+"idStatoDocIndexAcquisito non trovato");
				return false;
			}
			
			// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO vo = trovaTDocumentoIndex(idDocumentoIndex);
			if (vo == null) {
				logger.error(prf+"record TDocumentoIndex non trovato");
				return false;
			}
						
			// Nome univoco con cui il file verrà salvato su File System.
			String newNomeFile = nomeFile.replaceFirst("\\.", "_FA"+idDocumentoIndex+".");
			
			// PASSO 1 : Salvataggio file su file system.
			InputStream is = new ByteArrayInputStream(file);
			boolean esitoSalva = salvaSuFileSystem(is, newNomeFile, vo.getRepository());
			if (!esitoSalva) {
				logger.error(prf+"Salvataggio su file system fallito: termino la procedura.");
				return false;
			}
			
			// PASSO 2 : Update in PBANDI_T_DOCUMENTO_INDEX.
			String sqlUpd = "";
			sqlUpd = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET DT_AGGIORNAMENTO_INDEX = SYSDATE, FLAG_FIRMA_CARTACEA = 'N', ";
			sqlUpd += "ID_STATO_DOCUMENTO = "+idStatoDocIndexAcquisito.intValue()+", ID_UTENTE_AGG = "+idUtente.intValue()+", ";
			sqlUpd += "NOME_DOCUMENTO_FIRMATO = '"+newNomeFile + "', FLAG_FIRMA_AUTOGRAFA = 'S'";//, NOME_DOCUMENTO_MARCATO = '" + newNomeFile + "' ";       
			sqlUpd += " WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.intValue();
			logger.info("\n"+sqlUpd);
			try {
				getJdbcTemplate().update(sqlUpd);
			} catch (Exception e) {
				logger.error(prf+"Update in PBANDI_T_DOCUMENTO_INDEX fallita: cancello il file da file system e termino.");
				cancellaSuFileSystem(newNomeFile, vo.getRepository());
				return false;
			}
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
				return false;
			}
			finally {
				logger.info(prf+" END");
			}
			
			return true;
		}
	
			
	
	// Salva il file marcato temporalmente su file system e aggiorna la PBANDI_T_DOCUMENTO_INDEX.
	// Ex DigitalSignAction.upload() + DigitalSignBusinessImpl.uploadSignedFile().
	public boolean salvaFileMarcato(byte[] file, Long idDocumentoIndex, Long idUtente) {
		String prf = "[DocumentoManager::salvaFileMarcato] ";
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
			if (idDocumentoIndex == null) {
				logger.error(prf+"idDocumentoIndex = NULL.");
				return false;
			}
			
			logger.info(prf+"file.length = "+file.length+"; idDocumentoIndex = "+idDocumentoIndex);
			
			BigDecimal idStatoDocIndexInviato = this.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO);
			if (idStatoDocIndexInviato == null) {
				logger.error(prf+"idStatoDocIndexInviato non trovato");
				return false;
			}
			
			// Recupera il recod sulla PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO vo = trovaTDocumentoIndex(idDocumentoIndex);
			if (vo == null) {
				logger.error(prf+"record TDocumentoIndex non trovato");
				return false;
			}
						
			// Nome univoco con cui il file verrà salvato su File System.
			String newNomeFile = vo.getNomeDocumentoFirmato()+".tsd";
			
			// PASSO 1 : Salvataggio file su file system.
			InputStream is = new ByteArrayInputStream(file);
			boolean esitoSalva = salvaSuFileSystem(is, newNomeFile, vo.getRepository());
			if (!esitoSalva) {
				logger.error(prf+"Salvataggio su file system fallito: termino la procedura.");
				return false;
			}
			
			// PASSO 2 : Update in PBANDI_T_DOCUMENTO_INDEX.
			String sqlUpd = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET DT_AGGIORNAMENTO_INDEX = SYSDATE, DT_MARCA_TEMPORALE = SYSDATE, ";
			sqlUpd += "ID_STATO_DOCUMENTO = "+idStatoDocIndexInviato.intValue()+", ID_UTENTE_AGG = "+idUtente.intValue()+", ";
			sqlUpd += "NOME_DOCUMENTO_MARCATO = '"+newNomeFile+"' WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.intValue();
			logger.info("\n"+sqlUpd);
			try {
				getJdbcTemplate().update(sqlUpd);
			} catch (Exception e) {
				logger.error(prf+"Update in PBANDI_T_DOCUMENTO_INDEX fallita: cancello il file da file system e termino.");
				cancellaSuFileSystem(newNomeFile, vo.getRepository());
				return false;
			}
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return false;
		}
		finally {
			logger.info(prf+" END");
		}
		
		return true;
	}
	
	// Salva il file su file system, inserisce un record nella PBANDI_T_DOCUMENTO_INDEX e inserisce le Visibilita'.
	// Ex GestioneDocumentazioneBusinessImpl.uploadFileConVisibilita()
	public boolean salvaFileConVisibilita(byte[] file, PbandiTDocumentoIndexVO vo, ArrayList<Long> elencoIdCategAnag) {
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
			if (elencoIdCategAnag == null || elencoIdCategAnag.size() == 0) {
				logger.error(prf+"Visibilita' mancanti.");
				return false;
			}

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
			logger.info(prf+"Inserimento delle visibilita su PBANDI_R_CATEG_ANAG_DOC_INDEX");			
			for (Long idCategAnag : elencoIdCategAnag) {
				if (idCategAnag != null) {					
					String sql = "INSERT INTO PBANDI_R_CATEG_ANAG_DOC_INDEX (ID_CATEG_ANAGRAFICA, ID_DOCUMENTO_INDEX) ";
					sql += "VALUES ("+idCategAnag+", "+vo.getIdDocumentoIndex()+") ";
					logger.info(prf + "\n" + sql);					
					getJdbcTemplate().update(sql.toString());								
				}
			}	
			
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
	
	private BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione, String valoreDescrizione) 
			throws Exception {
		String prf = "[DocumentoManager::idDaDescrizione()] ";
		logger.info(prf + "BEGIN");
		BigDecimal id = null;
		try {			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT "+colonnaId+" FROM "+tabella+" WHERE "+colonnaDescrizione+" = '"+valoreDescrizione+"'");
			logger.debug("\n"+sql.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);			
		} catch (Exception e) {
			logger.error(prf+"ERRORE nella ricerca di un id da una descriziozne: ", e);
			throw new Exception("ERRORE nella ricerca di un id da una descriziozne: ", e);
		}
		finally {
			logger.info(prf+"END");
		}
		
		return id;
	}

}
