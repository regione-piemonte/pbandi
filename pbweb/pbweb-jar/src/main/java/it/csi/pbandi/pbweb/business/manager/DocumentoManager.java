/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.manager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRTpDocIndBanLiIntVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.FileUtil;
import it.csi.pbandi.pbweb.util.StringUtil;


/**
 * questa classe si pone come obbiettivo di essere un frontend comune per tutta
 * la gestione della interazione db + file system Unix per la gestione della persistenza dei documenti
 */

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
	
	public DecodificheDAOImpl getDecodificheDAOImpl() {
		return decodificheDAOImpl;
	}

	public void setDecodificheDAOImpl(DecodificheDAOImpl decodificheDAOImpl) {
		this.decodificheDAOImpl = decodificheDAOImpl;
	}
	
	@Autowired
	private DecodificheManager decodificheManager;

	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;

	
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
	
	// Legge un file con firma autografa da file system.	
	public FileDTO leggiFileMarcatoFirmaAutografa(long idDocumentoIndex) {
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
	
	public BigDecimal nuovoIdTDocumentoIndex() {
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
			sql.append("NUM_PROTOCOLLO,REPOSITORY,UUID_NODO,VERSIONE,NOME_DOCUMENTO, FLAG_VISIBILE_BEN)");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.info("\n"+sql);
			
			Object[] params = new Object[26];
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
			params[25] = vo.getFlagVisibileBen();

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
			
			BigDecimal idStatoDocIndexAcquisito = decodificheDAOImpl.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_ACQUISITO);
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
			
			BigDecimal idStatoDocIndexInviato = decodificheDAOImpl.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO);
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
			String addStr = "_FA" + idDocumentoIndex.toString()+".";
			String newNomeFile = nomeFile.replaceFirst("\\.", addStr);
			
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
			sqlUpd += "ID_STATO_DOCUMENTO = "+idStatoDocIndexInviato.intValue()+", ID_UTENTE_AGG = "+idUtente.intValue()+", ";
			sqlUpd += "NOME_DOCUMENTO_FIRMATO = '"+newNomeFile + "', FLAG_FIRMA_AUTOGRAFA = 'S'";       
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
			
			BigDecimal idStatoDocIndexInviato = decodificheDAOImpl.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX", "ID_STATO_DOCUMENTO", "DESC_BREVE", Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO);
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
	
	
	public boolean cancellaRecordDocumentoIndex(BigDecimal idDocumentoIndex) {
		String prf = "[DocumentoManager::cancellaRecordDocumentoIndex] ";
		logger.info(prf + " BEGIN");
		boolean esito = true;
		try {
			
			if (fileApiServiceImpl == null) {
				logger.error(prf+"fileApiServiceImpl non istanziato.");
				return false;
			}			
			if (idDocumentoIndex == null || idDocumentoIndex.intValue() == 0) {
				logger.error(prf+"idDocumentoIndex non valorizzato.");
				return false;
			}
			
			// Cancella da PBANDI_T_DOC_PROTOCOLLO, se presente
			String deleteRecord = "DELETE FROM PBANDI_T_DOC_PROTOCOLLO WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.longValue();
			logger.info("\n"+deleteRecord);
			 getJdbcTemplate().update(deleteRecord);
			
			// Cancella da PBANDI_T_DOCUMENTO_INDEX.
			deleteRecord = "DELETE FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex.longValue();
			logger.info("\n"+deleteRecord);
			int numRecord = getJdbcTemplate().update(deleteRecord);
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
	
	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDb__TODEL(	String nomeFile, BigDecimal idTarget, Long idRappLegale, Long idDelegato, 
			BigDecimal idProgetto,	String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex) {
		String prf = "[DocumentiFSManager::salvaInfoNodoIndexSuDb] ";
		logger.info(prf + " BEGIN");
		
		logger.info(prf + " nomeFile: " + nomeFile);
		logger.info(prf + " idTarget: " + idTarget);
		logger.info(prf + " idRappLegale: " + idRappLegale);
		logger.info(prf + " idDelegato: " + idDelegato);
		logger.info(prf + " idProgetto: " + idProgetto);
		logger.info(prf + " tipoDocIndex: " + tipoDocIndex);
		logger.info(prf + " entita: " + entita);
		logger.info(prf + " codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(prf + " versione: " + versione);
		logger.info(prf + " shaHex: " + shaHex);
		
		it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO documentoIndexVO = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO();

		try {
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(prf + " idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			
			//TODO : PK repository non di index, metto directory di pbstorage_online
			//documentoIndexVO.setRepository(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO); 
			documentoIndexVO.setRepository(tipoDocIndex);			
			
			documentoIndexVO.setUuidNodo("UUID");  // PK per lo storage schianto "UUID"
			
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(this.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));

			// TODO : questo andrebbe valorizzato
			documentoIndexVO.setNomeDocumento(nomeFile);
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO = genericDAO.insert(documentoIndexVO);

			logger.info(prf + "idDocIndex inserito sul db " 	+ documentoIndexVO.getIdDocumentoIndex());

			if(codStatoTipoDocIndex!=null){
				   logger.info(prf + "codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info(prf + "idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(prf + " codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  finally {
			logger.info(prf + " END");
		}
		return documentoIndexVO;
	}
	
	public BigDecimal getIdEntita(Class<? extends GenericVO> voClass) {
		return getIdEntita(GenericVO.getTableNameForValueObject(voClass));
	}
	
	private BigDecimal getIdEntita(String nomeEntita) {
		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		entitaVO.setNomeEntita(nomeEntita);
		BigDecimal idEntita = null;
		try {
			idEntita = genericDAO.findSingleWhere(entitaVO).getIdEntita();
			logger.debug("[DocumentiManager::getIdEntita] " + nomeEntita + " -> " + idEntita);
		} catch (RecordNotFoundException e) {
			throw new RuntimeException("Non trovato id per codice "	+ nomeEntita);
		}
		return idEntita;
	}
	public BigDecimal getIdModello(BigDecimal idProgetto, BigDecimal idTipoDocumentoIndex) {
		PbandiRTpDocIndBanLiIntVO tpDocIndBanLiIntVO = new PbandiRTpDocIndBanLiIntVO();
		tpDocIndBanLiIntVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);

		BandoLineaProgettoVO bandoLineaProgettoVO = new BandoLineaProgettoVO();
		bandoLineaProgettoVO.setIdProgetto(idProgetto);

		List<Pair<GenericVO, PbandiRTpDocIndBanLiIntVO, BandoLineaProgettoVO>> list = genericDAO
				.join(Condition.filterBy(tpDocIndBanLiIntVO), Condition.filterBy(bandoLineaProgettoVO))
				.by("progrBandoLineaIntervento").select();

		BigDecimal idModello = list.size() == 1 ? list.get(0).getFirst().getIdModello() : null;

		return idModello;
	}
	public String getFlagFirmaCartacea(BigDecimal idTipoDocumentoIndex, BigDecimal idTarget, BigDecimal idProgetto) {
		String prf = "[DocumentiManager::getFlagFirmaCartacea] ";
		logger.info(prf + "BEGIN, idTipoDocumentoIndex = "+idTipoDocumentoIndex+"; idTarget = "+idTarget);
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 1) {
			// Si tratta di Ducumento Di Spesa.
			PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
			vo.setIdDichiarazioneSpesa(idTarget);
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo != null) {
				String tipoInvioDs = vo.getTipoInvioDs();
				logger.info(prf + "tipoInvioDs = "+tipoInvioDs);
				if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
					return "S";
				}
			}
		}
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 17) {
			// Si tratta di Comunicazione di fine Progetto.
			DichiarazioneDiSpesaConTipoVO vo = dichiarazioneDiSpesaManager.getDichiarazioneFinaleConComunicazione(idProgetto.longValue());
			if (vo == null) {
				logger.info(prf + "DichiarazioneFinale non trovata per idProgetto = "+idProgetto);
				return null;
			}
			String tipoInvioDs = vo.getTipoInvioDs();
			logger.info(prf + " tipoInvioDs = "+tipoInvioDs);
			if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
				return "S";
			}
		}
		return null;
	}

	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDbExl(
			String nomeFile, BigDecimal idTarget, Long idRappLegale, Long idDelegato, 
			BigDecimal idProgetto,	String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex, BigDecimal idTDocumentoIndex, String newName) {
		String prf = "[DocumentiFSManager::salvaInfoNodoIndexSuDbExl] ";
		logger.info(prf + " BEGIN");
		
		logger.info(prf + " nomeFile: " + nomeFile);
		logger.info(prf + " idTarget: " + idTarget);
		logger.info(prf + " idRappLegale: " + idRappLegale);
		logger.info(prf + " idDelegato: " + idDelegato);
		logger.info(prf + " idProgetto: " + idProgetto);
		logger.info(prf + " tipoDocIndex: " + tipoDocIndex);
		logger.info(prf + " entita: " + entita);
		logger.info(prf + " codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(prf + " versione: " + versione);
		logger.info(prf + " shaHex: " + shaHex);
		logger.info(prf + " idTDocumentoIndex: " + idTDocumentoIndex);
		logger.info(prf + " newName: " + newName);
		
		it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO documentoIndexVO = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO();

		try {
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(prf + " idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setIdDocumentoIndex(idTDocumentoIndex);
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			documentoIndexVO.setRepository(tipoDocIndex);			
			documentoIndexVO.setUuidNodo("UUID");  // PK per lo storage schianto "UUID"
			
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(this.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));
			
			documentoIndexVO.setNomeDocumento(newName);
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO = genericDAO.insert(documentoIndexVO);

			logger.info(prf + "idDocIndex inserito sul db " 	+ documentoIndexVO.getIdDocumentoIndex());

			if(codStatoTipoDocIndex!=null){
				   logger.info(prf + "codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info(prf + "idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(prf + " codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  finally {
			logger.info(prf + " END");
		}
		return documentoIndexVO;
	}

}
