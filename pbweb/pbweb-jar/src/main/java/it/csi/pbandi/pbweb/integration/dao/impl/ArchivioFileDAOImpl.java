/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.Esito;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoInsertFiles;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.FolderDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.InizializzaArchivioFileDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.UserSpaceDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.ArchivioFileDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexLockVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTFileVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileInfoDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.FileSqlUtil;
import it.csi.pbandi.pbweb.util.FileUtil;
import it.csi.pbandi.pbweb.util.NumberUtil;
import it.csi.pbandi.pbweb.util.RequestUtil;
import it.csi.pbandi.pbweb.util.StringUtil;



@Service
public class ArchivioFileDAOImpl extends JdbcDaoSupport implements ArchivioFileDAO {
	
	@Autowired
	public ArchivioFileDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	/*
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	protected FileUtil fileUtil;
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
		
	@Autowired
	protected DocumentoManager documentoManager;
	
	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;
	
	@Override
	// Restituisce i dati con cui popolare Archivio File all'apertura:
	// - dati utente: userInfoSec
	//    - beneficiari: idBeneficiario, denominazione, codiceFiscale
	//    - ruoli": descrizione (es: "Utente operatore per tutti i Beneficiari", descrizioneBreve (es: "BEN-MASTER"), id (es: "19"), ruoloHelp (es: "Beneficiario")
	//    - codFisc
	//    - codiceRuolo (es: "BEN-MASTER")
	//    - cognome
	//    - nome
	//    - idIride (es: "AAAAAA00A11T000B/CSI PIEMONTE/DEMO 37/ACTALIS_EU/20210226113023/16",)
	//    - idSoggetto
	//    - idUtente
	//    - isIncaricato 
	//    - ruolo (es: "Utente operatore per tutti i Beneficiari")
	// - elenco folder della root.
	// - elenco folder\file della root.
	// - spazio disco totale e usato.
	// - dimensione massima di un file da caricare.
	// - elenco delle estensioni di file ammesse per essere caricate.
	public InizializzaArchivioFileDTO inizializzaArchivioFile (long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo, long idUtente, HttpServletRequest req) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::inizializzaArchivioFile] ";
		LOG.info(prf + " BEGIN");
		InizializzaArchivioFileDTO result = new InizializzaArchivioFileDTO();
		try {
			
			if (idSoggetto == 0) {
				throw new InvalidParameterException("idSoggetto non valorizzato.");
			}
			if (idSoggettoBeneficiario == 0) {
				throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
			}
			if (StringUtil.isEmpty(codiceRuolo)) {
				throw new InvalidParameterException("codiceRuolo non valorizzato.");
			}
				
			result.setUserSpaceDTO(this.spazioDisco(idSoggettoBeneficiario));
			
			result.setFolders(this.leggiRoot(idSoggetto, idSoggettoBeneficiario, codiceRuolo));
			
			result.setDimMaxSingoloFile(new Long(this.costante(Constants.COSTANTE_ARCHIVIO_FILE_SIZE_LIMIT, true)));
			
			String archivioAllowedFileExtensions = this.costante(Constants.COSTANTE_ARCHIVIO_ALLOWED_FILE_EXT, true);
			StringTokenizer strtkz = new StringTokenizer(archivioAllowedFileExtensions,",");
			ArrayList<String> allowedExtensions = new ArrayList<String>();
			while(strtkz.hasMoreElements()) {
				allowedExtensions.add(strtkz.nextToken());
			}
			result.setEstensioniConsentite(allowedExtensions);

		} catch (Exception e) {
			LOG.error(prf+" ERROREin inizializzaArchivioFile(): ", e);
			throw new DaoException("Errore durante la inizializzazione di Archivio File.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	@Override
	// Reperisce lo spazio disco totale ed usato per un beneficiazio.
	public UserSpaceDTO spazioDisco (long idSoggettoBeneficiario) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::spazioUtente] ";
		LOG.info(prf + " BEGIN");
		UserSpaceDTO userSpaceDTO = new UserSpaceDTO();
		try {
			
			// Spazio totale.
			String valore = this.costante(Constants.COSTANTE_USER_SPACE_LIMIT, true);
			
			// Spazio già usato.
			StringBuilder sql = new StringBuilder("SELECT SUM (SIZE_FILE) AS userSpaceUsed");
			sql.append(" FROM pbandi_t_file")
			   .append(" JOIN pbandi_t_folder USING(id_folder)") 
	    	   .append(" where ID_SOGGETTO_BEN = ?");
			LOG.info("\n"+sql.toString());			
			Long usato = (Long) getJdbcTemplate().queryForObject(sql.toString(), new Object[] { idSoggettoBeneficiario }, Long.class);			
			
			userSpaceDTO.setTotal(new Long(valore));
			userSpaceDTO.setUsed(usato);

		} catch (Exception e) {
			LOG.error(prf+" ERROREin inizializzaArchivioFile(): ", e);
			throw new DaoException("Errore durante la ricerca dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return userSpaceDTO;
	}
	
	// ex ArchivioBusinessImpl.getFolders()
	public ArrayList<FolderDTO> leggiRoot(long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiRoot] ";
		LOG.info(prf + " BEGIN");
		ArrayList<FolderDTO> result = new ArrayList<FolderDTO>();
		try {
			
			if (idSoggetto == 0) {
				throw new InvalidParameterException("idSoggetto non valorizzato.");
			}
			if (idSoggettoBeneficiario == 0) {
				throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
			}
			if (StringUtil.isEmpty(codiceRuolo)) {
				throw new InvalidParameterException("codiceRuolo non valorizzato.");
			}
			
			// Restituisce tutto il contenuto della root del beneficiario.
			List<ArchivioFileVO> listaArchivioFileVO = leggiContenutoRoot (idSoggettoBeneficiario); 
			
			Map<String, FolderDTO> folders = archivioFileToListFolderDTOOrdered(listaArchivioFileVO);
			Collection<FolderDTO> values = folders.values();
			List<FolderDTO> list = new ArrayList<FolderDTO>(values);
			Collections.sort(list, new AlphabeticalFolderComparator());			
			
			// Elimina le cartelle che l'utente non è autorizzato a vedere.
			if (list != null && list.size() > 0 &&
					Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA.equalsIgnoreCase(codiceRuolo)) {	
				this.eliminaCartelleNonAutorizzate(list, idSoggetto);	
			}
			
			result = (ArrayList<FolderDTO>) list;

		} catch (Exception e) {
			LOG.error(prf+" ERRORE in leggiRoot(): ", e);
			throw new DaoException("Errore durante la ricerca del contenuto della root.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// ex ArchivioBusinessImpl.getFolder()
	public ArrayList<FolderDTO> leggiFolder(long idFolder, long idSoggetto, String codiceRuolo) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiFolder] ";
		LOG.info(prf + " BEGIN");
		ArrayList<FolderDTO> result = new ArrayList<FolderDTO>();
		try {
			
			if (idSoggetto == 0) {
				throw new InvalidParameterException("idSoggetto non valorizzato.");
			}
			if (idFolder == 0) {
				throw new InvalidParameterException("idFolder non valorizzato.");
			}
			if (StringUtil.isEmpty(codiceRuolo)) {
				throw new InvalidParameterException("codiceRuolo non valorizzato.");
			}
			
			// Restituisce tutto il contenuto del folder in input.
			List<ArchivioFileVO> listaArchivioFileVO = leggiContenutoFolder (idFolder); 
			
			List<FolderDTO> folders = archivioFileToListFolderDTO(listaArchivioFileVO);
			if(folders.isEmpty()) {
				return result;
			}
			
			FolderDTO folderOutput = folders.get(0);
			
			// L'operatore del Beneficiario deve vedere solo i folder a cui è associato.
			// Se il folder cercato è la root (idPadre = null) scarto i folder di progetto non consentiti in esso contenuti.
			if (folderOutput != null && folderOutput.getIdPadre() == null &&
				folderOutput.getFolders() != null && folderOutput.getFolders().size() > 0 && 
				Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA.equalsIgnoreCase(codiceRuolo)) {
				this.eliminaCartelleNonAutorizzate(folderOutput.getFolders(), idSoggetto);	
			}
			
			result = (ArrayList<FolderDTO>) folders;

		} catch (Exception e) {
			LOG.error(prf+" ERRORE in leggiFolder(): ", e);
			throw new DaoException("Errore durante la ricerca del contenuto del folder.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// Restituisce tutto il contenuto della root di un beneficiario.
	private List<ArchivioFileVO> leggiContenutoRoot (long idSoggettoBeneficiario) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiContenutoRoot] ";
		LOG.info(prf + " BEGIN");
		List<ArchivioFileVO> result = null;
		try {
			
			String sql = fileSqlUtil.getQuery("ArchivioFileVO.sql");
			sql += " where fo.id_soggetto_ben = "+idSoggettoBeneficiario+" order by  ID_PADRE nulls first, NOME_FOLDER, FI.NOME_FILE"; 
			LOG.info(prf + "\n" + sql);
			result = getJdbcTemplate().query(sql.toString(), new Object[] {}, new BeanRowMapper(ArchivioFileVO.class));
			LOG.info(prf + "Record trovati = " + result.size());

		} catch (Exception e) {
			LOG.error(prf+" ERROREin leggiContenutoRoot(): ", e);
			throw new DaoException("Errore durante la ricerca dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// Restituisce tutto il contenuto del folder in input
	private List<ArchivioFileVO> leggiContenutoFolder (long idFolder) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiContenutoFolder] ";
		LOG.info(prf + " BEGIN");
		List<ArchivioFileVO> result = null;
		try {
			
			String sql = fileSqlUtil.getQuery("ArchivioFileVO.sql");
			sql += " where id_folder = "+idFolder+" or fo.id_padre = "+idFolder;
			sql += " order by  ID_PADRE nulls first, NOME_FOLDER, FI.NOME_FILE";
			LOG.info(prf + "\n" + sql);
			result = getJdbcTemplate().query(sql.toString(), new Object[] {}, new BeanRowMapper(ArchivioFileVO.class));
			LOG.info(prf + "Record trovati = " + result.size());

		} catch (Exception e) {
			LOG.error(prf+" ERROREin leggiContenutoFolder(): ", e);
			throw new DaoException("Errore durante la ricerca dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	private Map<String,FolderDTO> archivioFileToListFolderDTOOrdered(
			List<ArchivioFileVO> filez) {
	 		
		Map<Long,FolderDTO> folders = new HashMap<Long, FolderDTO>();		
		for (ArchivioFileVO archivioFileVO : filez) {
	      FolderDTO folderDTO = createFolderDTO(archivioFileVO);
	      folders.put(folderDTO.getIdFolder(), folderDTO);
		}
		logger.info("folders size"+folders.size());
		for (ArchivioFileVO archivioFileVO : filez) {		      
		      if(archivioFileVO.getIdDocumentoIndex()!=null){
		    	  FileDTO fileDTO = createFileDTO(archivioFileVO);
		    	  FolderDTO folderDTO =  folders.get(archivioFileVO.getIdFolder().longValue());
		    	  addFileToFolder(folderDTO, fileDTO);
		      }
		}
		 
		Map<String,FolderDTO> foldersIerarchical=new HashMap<String, FolderDTO>();
		for ( FolderDTO folderDTO : folders.values()) {
			if(folderDTO.getIdPadre()==null){
				if(!foldersIerarchical.containsKey(folderDTO.getNomeFolder() )){
					foldersIerarchical.put(folderDTO.getNomeFolder(),folderDTO);
				}
			}else{
				FolderDTO father = folders.get(folderDTO.getIdPadre());
				addFolderToFolders(father, father.getFolders(), folderDTO);
				
			}
		}
		
		return foldersIerarchical ;
	}
	
	private FolderDTO createFolderDTO(ArchivioFileVO file) {
		FolderDTO folderDTO= new FolderDTO();
		folderDTO.setDtAggiornamento(file.getDtAggiornamentoFolder());
		folderDTO.setDtInserimento(file.getDtInserimentoFolder());
		folderDTO.setIdFolder(file.getIdFolder().longValue());
		if(file.getIdPadre()!=null) folderDTO.setIdPadre(file.getIdPadre().longValue());
		if(file.getIdSoggettoBen()!=null) folderDTO.setIdSoggettoBen(file.getIdSoggettoBen().longValue());
		folderDTO.setNomeFolder(file.getNomeFolder());
		if (file.getIdProgettoFolder() != null) {
			folderDTO.setIdProgettoFolder(file.getIdProgettoFolder().longValue());	
		}		
		return folderDTO;
	}

	private FileDTO createFileDTO(ArchivioFileVO file) {
		FileDTO fileDTO=new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if(file.getEntitiesAssociated()!=null && file.getEntitiesAssociated().longValue()>0){
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if(file.getIslocked()!=null && file.getIslocked().equalsIgnoreCase("S"))
			fileDTO.setIsLocked(true);
		else
			fileDTO.setIsLocked(false);
		fileDTO.setNumProtocollo(file.getNumProtocollo());
		
		if (file.getIdEntita() != null) {
			fileDTO.setIdEntita(file.getIdEntita().longValue());	
		}
		if (file.getIdTarget() != null) {
			fileDTO.setIdTarget(file.getIdTarget().longValue());	
		}
		fileDTO.setFlagEntita(file.getFlagEntita());	//Jira PBANDI-2815		
		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());		
		return fileDTO;
	}
	
	// Modificato rispetto all'originale per il passaggio da [] a List.
	private void addFileToFolder(FolderDTO folderDTO, FileDTO fileDTO) {		
		if (folderDTO.getFiles() == null)
			folderDTO.setFiles(new ArrayList<FileDTO>());
		folderDTO.getFiles().add(fileDTO);
	}
	
	// Modificato rispetto all'originale per il passaggio da [] a List.
	private void addFolderToFolders(FolderDTO father, ArrayList<FolderDTO> children, FolderDTO child) {
		if (children == null) {
			children = new ArrayList<FolderDTO>();
		}
		// mi sembra inutile, lo commento.
		//for (FolderDTO oldChild : children) {
		//	if(oldChild.getNomeFolder().equalsIgnoreCase(child.getNomeFolder()))
		//		return;
		//}
		children.add(child);
		Collections.sort(children, new AlphabeticalFolderComparator());
		father.setFolders(children);		
	}
	
	class AlphabeticalFolderComparator implements Comparator<FolderDTO>{
		public int compare(FolderDTO o1, FolderDTO o2) {
			 return o1.getNomeFolder().toLowerCase().compareTo( o2.getNomeFolder().toLowerCase());
		}
		 
	}
	
	// Elimina le cartelle che l'utente non è autorizzato a vedere.
	private List<FolderDTO> eliminaCartelleNonAutorizzate(List<FolderDTO> list, long idSoggetto)  throws Exception {
		String prf = "[ArchivioFileDAOImpl::eliminaCartelleNonAutorizzate] ";
		LOG.info(prf + " BEGIN");
		List<Long> listaIdProgetto = new ArrayList<Long>();
		try {
			LOG.info(prf + "idSoggetto = " + idSoggetto);
						
					LOG.info(prf + "operatore del beneficiario: scarto i folder non consentiti.");
					
					// Recupero i progetti a cui l'utente può accedere.
					List<Long> idProgettiAssociati = findIdProgettiAssociati(idSoggetto);		// id dell'utente entrato come operatore del beneficiario (demo21, demo25, etc).
								
					// Recupero l'elenco dei folder direttamente figli della cartella root.
					ArrayList<FolderDTO> folderFigliDiRoot = list.get(0).getFolders();
					
					// Considero solo i folder associati a progetti a cui l'utente può accedere.
					ArrayList<FolderDTO> folderAmmessi = new ArrayList<FolderDTO>();
					for (FolderDTO f : folderFigliDiRoot) {
						if (f.getIdProgettoFolder() == null) {
							// Il folder non è associato ad alcun progetto, quindi è visibile a tutti.
							LOG.info(prf + "Folder "+f.getIdFolder()+"  "+f.getNomeFolder()+" non associato ad alcun progetto: ammesso.");
							folderAmmessi.add(f);
						} else if (presenteInElenco(f.getIdProgettoFolder(), idProgettiAssociati)) {
							// Folder associato a progetto ammesso.
							LOG.info("prf + Folder "+f.getIdFolder()+"  "+f.getNomeFolder()+" associato al progetto consentito "+f.getIdProgettoFolder()+" : ammesso.");
							folderAmmessi.add(f);
						} else {
							LOG.info(prf + "Folder "+f.getIdFolder()+"  "+f.getNomeFolder()+" associato al progetto non consentito "+f.getIdProgettoFolder()+" : scartato.");
						}
					}
										
					list.get(0).setFolders(folderAmmessi);
					return list;
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE in eliminaCartelleNonAutorizzate(): ", e);
			throw new DaoException("Errore durante la eliminazione delle cartelle non autorizzate.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
	}
	
	// Restituisce l'elenco degli idProgetto visibili all'utente.
	private List<Long> findIdProgettiAssociati(long idSoggetto) throws Exception {
		String prf = "[ArchivioFileDAOImpl::findIdProgettiAssociati] ";
		LOG.info(prf + " BEGIN");
		List<Long> listaIdProgetto = new ArrayList<Long>();
		try {
			LOG.info(prf + "idSoggetto = " + idSoggetto);
		
			String sql = "SELECT ID_PROGETTO FROM PBANDI_R_SOGGETTO_PROGETTO WHERE ID_SOGGETTO = "+idSoggetto;
			LOG.info(prf + "\n" + sql);
			listaIdProgetto = getJdbcTemplate().queryForList(sql.toString(), Long.class);
			LOG.info(prf + "Record trovati = " + listaIdProgetto.size());
			
			String s = "findIdProgettiAssociati(): lista id Progetti:";
			for (Long id : listaIdProgetto) {
				s += " "+id;
			}
			LOG.info(prf + "lista id Progetti = " + s);
			
		} catch (Exception e) {
			LOG.error(prf+" ERROREin findIdProgettiAssociati(): ", e);
			throw new DaoException("Errore durante la ricerca dei progetti visibili.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return listaIdProgetto;
	}
	
	private boolean presenteInElenco(Long item, List<Long> idProgettiAssociati) {
		if (idProgettiAssociati == null || item == null)
			return false;
		for (Long l : idProgettiAssociati) {
			if (l.intValue() == item.intValue()) {
				return true;
			}
		}
		return false;
	}
	
	private List<FolderDTO>  archivioFileToListFolderDTO(List<ArchivioFileVO> filez) {
		
		// create flat folders map 
		Map<Long, FolderDTO> folders = createFolders(filez);
		
		// add files to folders owner
		createAndAddFilesToFolders(filez, folders);
		
		// transform flat map into ierarchical one
		Map<Long, FolderDTO> ierarchicalFolders = toIerarchicalFolderStructure(folders);
		 
		return new ArrayList<FolderDTO> (ierarchicalFolders.values());
	}
	
	private Map<Long, FolderDTO> createFolders(List<ArchivioFileVO> filez) {
		Map<Long,FolderDTO> folders=new HashMap<Long,FolderDTO>();
		for (ArchivioFileVO archivioFileVO : filez) {
			if(!folders.containsKey(archivioFileVO.getIdFolder().longValue())){
				FolderDTO folderDTO=createFolderDTO(archivioFileVO);
				folders.put(folderDTO.getIdFolder() , folderDTO);
			}
		}
		return folders;
	}
	
	private void createAndAddFilesToFolders(List<ArchivioFileVO> filez, Map<Long, FolderDTO> folders) {
		for (ArchivioFileVO archivioFileVO : filez) {
			if(archivioFileVO.getIdDocumentoIndex()!=null){
				FileDTO fileDTO=createFileDTO(archivioFileVO);			
				FolderDTO folderDTO = folders.get(fileDTO.getIdFolder());
				addFileToFolder(folderDTO, fileDTO);
			}
		}
	}
	
	private Map<Long, FolderDTO> toIerarchicalFolderStructure(Map<Long, FolderDTO> folders) {
		 Collection<FolderDTO> values = folders.values();
		 Map<Long,FolderDTO> ierarchicalMap=new HashMap<Long,FolderDTO>();
		 for (FolderDTO folderDTO : values) {
			if(folderDTO.getIdPadre()!=null){
				FolderDTO ancestor=ierarchicalMap.get(folderDTO.getIdPadre());
				if(ancestor==null)
					ancestor= folders.get(folderDTO.getIdPadre());
				
				if(ancestor!=null){
					addFolderToFolders(ancestor,ancestor.getFolders(),folderDTO);
					if(ancestor.getIdPadre()==null)
						ierarchicalMap.put(ancestor.getIdFolder() , ancestor);
				}
				else
					ierarchicalMap.put(folderDTO.getIdFolder() , folderDTO);
					
			}  else{
				ierarchicalMap.put(folderDTO.getIdFolder() , folderDTO);
			}
		 }
		 return ierarchicalMap;
	}
	
	// Ex GestioneDocumentazioneBusinessImpl.scaricaDocumento()
	public EsitoLeggiFile leggiFile(long idDocumentoIndex) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiFile] ";
		LOG.info(prf + " BEGIN");		
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		
		EsitoLeggiFile esito = new EsitoLeggiFile();
		esito.setEsito(true);
		esito.setIdDocumentoIndex(idDocumentoIndex);
		try {
					
			FileDTO fileDTO = documentoManager.leggiFile(idDocumentoIndex);
			
			if (fileDTO == null || StringUtil.isEmpty(fileDTO.getNomeFile()) ||
				fileDTO.getSizeFile() == null || fileDTO.getSizeFile().intValue() == 0) {				
				esito.setEsito(false);
				esito.setMessaggio("Lettura del file fallita.");	
				
				// NOTA: è stato richiesto di passare ad Angular solo il flusso di byte.
				// Quindi se esito = false, innalzo una eccezione.
				logger.error(prf+"Errore in documentoManager.leggiFile()");
				throw new DaoException("Lettura del file fallita.");
				
			} else {
				esito.setBytes(fileDTO.getBytes());
				esito.setNomeFile(fileDTO.getNomeFile());
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura del file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
		
	public EsitoLeggiFile leggiFileFirmato(long idDocumentoIndex) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiFileFirmato] ";
		LOG.info(prf + " BEGIN");		
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		
		EsitoLeggiFile esito = new EsitoLeggiFile();
		esito.setEsito(true);
		esito.setIdDocumentoIndex(idDocumentoIndex);
		try {
					
			FileDTO fileDTO = documentoManager.leggiFileFirmato(idDocumentoIndex);
			
			if (fileDTO == null || StringUtil.isEmpty(fileDTO.getNomeFile()) ||
				fileDTO.getSizeFile() == null || fileDTO.getSizeFile().intValue() == 0) {				
				esito.setEsito(false);
				esito.setMessaggio("Lettura del file fallita.");	
				
				// NOTA: è stato richiesto di passare ad Angular solo il flusso di byte.
				// Quindi se esito = false, innalzo una eccezione.
				logger.error(prf+"Errore in documentoManager.leggiFile()");
				throw new DaoException("Lettura del file fallita.");
				
			} else {
				esito.setBytes(fileDTO.getBytes());
				esito.setNomeFile(fileDTO.getNomeFile());
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura del file firmato.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	public EsitoLeggiFile leggiFileMarcato(long idDocumentoIndex) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiFileMarcato] ";
		LOG.info(prf + " BEGIN");		
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		
		EsitoLeggiFile esito = new EsitoLeggiFile();
		esito.setEsito(true);
		esito.setIdDocumentoIndex(idDocumentoIndex);
		try {
					
			FileDTO fileDTO = documentoManager.leggiFileMarcato(idDocumentoIndex);
			
			if (fileDTO == null || StringUtil.isEmpty(fileDTO.getNomeFile()) ||
				fileDTO.getSizeFile() == null || fileDTO.getSizeFile().intValue() == 0) {				
				esito.setEsito(false);
				esito.setMessaggio("Lettura del file fallita.");	
				
				// NOTA: è stato richiesto di passare ad Angular solo il flusso di byte.
				// Quindi se esito = false, innalzo una eccezione.
				logger.error(prf+"Errore in documentoManager.leggiFile()");
				throw new DaoException("Lettura del file fallita.");
				
			} else {
				esito.setBytes(fileDTO.getBytes());
				esito.setNomeFile(fileDTO.getNomeFile());
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura del file marcato.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.insertFiles()
	public ArrayList<EsitoInsertFiles> salvaFiles(MultipartFormDataInput multipartFormData)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::salvaFiles] ";
		LOG.info(prf + " BEGIN");		
		ArrayList<EsitoInsertFiles> result = new ArrayList<EsitoInsertFiles>();
		
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato.");
		}
		
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> files = map.get("file");
		
		Long idFolder = multipartFormData.getFormDataPart("idFolder", Long.class, null);
		Long idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		Long idSoggettoBeneficiario = multipartFormData.getFormDataPart("idSoggettoBeneficiariO", Long.class, null);			
		
		//idFolder = multipartFormData.getFormDataPart("idFolder", Long.class, null);
		//idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
		//idSoggettoBeneficiario = multipartFormData.getFormDataPart("idSoggettoBeneficiariO", Long.class, null);		
		//InputPart inputPart = multipartFormData.getFormDataPart("file", InputPart.class, null);
		//List<InputPart> listInputPart = new ArrayList<InputPart>();
		//listInputPart.add(inputPart);		
		
		if (idFolder == null || idFolder.intValue() == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		if (idUtente == null || idUtente.intValue() == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (idSoggettoBeneficiario == null || idSoggettoBeneficiario.intValue() == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (files.size() == 0) {
			throw new InvalidParameterException("file non valorizzati.");
		}
		
		LOG.info(prf + "num files = "+files.size()+"; idFolder = "+idFolder+"; idUtente = "+idUtente+"; idSoggettoBeneficiario = "+idSoggettoBeneficiario);
		
		try {
			
			// Estae i file dal multipart.
			ArrayList<FileDTO> filesDTO = leggiFilesDaMultipart(files, idFolder);			
			
			Long archivioFileSizeLimit = new Long(this.costante(Constants.COSTANTE_ARCHIVIO_FILE_SIZE_LIMIT, true));
			
			String archivioAllowedFileExtensions = this.costante(Constants.COSTANTE_ARCHIVIO_ALLOWED_FILE_EXT, true);
			StringTokenizer strtkz = new StringTokenizer(archivioAllowedFileExtensions,",");
			
			List<String> allowedExtensions = new ArrayList<String>();
			while(strtkz.hasMoreElements()) {
				allowedExtensions.add(strtkz.nextToken());
			}
			
			UserSpaceDTO userSpace = spazioDisco(idSoggettoBeneficiario);
			
			Esito esito = null;
			for (FileDTO fileDTO : filesDTO) {				
				
				logger.info(prf+"==========================================");
				logger.info(prf+"  SALVATAGGIO FILE "+fileDTO.getNomeFile());
				logger.info(prf+"==========================================");
				
				// Verifica la dimensione del file.
				esito = isFileSizeValid(fileDTO, userSpace, archivioFileSizeLimit*1000);
				if (!esito.getEsito()) {
					LOG.error(prf+" ERRORE: "+esito.getMessaggio());
					//throw new DaoException(esito.getMessaggio());
					// Restituisce in output l'esito del salvataggio.
					EsitoInsertFiles fileResult = new EsitoInsertFiles();
					fileResult.setEsito(false);
					fileResult.setMessaggio(esito.getMessaggio());
					fileResult.setNomeFile(fileDTO.getNomeFile());
					result.add(fileResult);
					continue;
				}
				
				// Verifica l'estensione del file.
				esito = isFileExtensionValid(fileDTO, allowedExtensions);
				if(!esito.getEsito()) {
					String[] params = new String[1];
					params[0] = archivioAllowedFileExtensions;
					String msg = StringUtil.formattaMsgConParam(ErrorMessages.ERROR_INVALID_FILE_EXTENSION, params);					
					LOG.error(prf+" ERRORE: "+msg);
					//throw new DaoException(msg);
					// Restituisce in output l'esito del salvataggio.
					EsitoInsertFiles fileResult = new EsitoInsertFiles();
					fileResult.setEsito(false);
					fileResult.setMessaggio(msg);
					fileResult.setNomeFile(fileDTO.getNomeFile());
					result.add(fileResult);
					continue;
				}

				// PASSO 1 : Inserimento su PBANDI_T_DOCUMENTO_INDEX.
				
				BigDecimal idTarget = BigDecimal.valueOf(fileDTO.getIdFolder()); // idFolder
				BigDecimal idTipoDocIndex = this.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX", "ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", Constants.COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE);
				BigDecimal idEntita = this.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_C_FILE);
				
				PbandiTDocumentoIndexVO vo = new PbandiTDocumentoIndexVO();
				vo.setIdEntita(idEntita);
				vo.setIdTarget(idTarget);
				vo.setIdTipoDocumentoIndex(idTipoDocIndex);
				vo.setIdUtenteIns(new BigDecimal(idUtente));
				vo.setNomeFile(fileDTO.getNomeFile());
				vo.setRepository(Constants.COD_TIPO_DOCUMENTO_INDEX_ARCHIVIO_FILE);
				
				// Salva il file su file system e inserisce un record nella PBANDI_T_DOCUMENTO_INDEX.
				boolean esitoSalva = documentoManager.salvaFile(fileDTO.getBytes(), vo);
				if (!esitoSalva) {
					logger.error(prf+"Errore in documentoManager.salvaFile()");
					//throw new DaoException("Salvataggio del file fallito.");
					// Restituisce in output l'esito del salvataggio.
					EsitoInsertFiles fileResult = new EsitoInsertFiles();
					fileResult.setEsito(false);
					fileResult.setMessaggio("Salvataggio del file fallito.");
					fileResult.setNomeFile(fileDTO.getNomeFile());
					result.add(fileResult);
					continue;
				}
				BigDecimal idDocumentoIndex = vo.getIdDocumentoIndex(); 
				
				// PASSO 2 : Inserimento su PBANDI_T_FILE.
				
				PbandiTFileVO fileVO=beanUtil.transform(fileDTO, PbandiTFileVO.class);
				fileVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				fileVO.setIdDocumentoIndex(idDocumentoIndex);
				fileVO.setSizeFile(BigDecimal.valueOf(fileDTO.getBytes().length));
				logger.info(prf+"Inserimento del file "+fileVO.getNomeFile()+" , idDocumentoIndex: "+idDocumentoIndex +" con idFolder "+fileVO.getIdFolder());
				
				try{
					 BigDecimal id = insertPbandiTFile(fileVO);				 
				} catch (Exception e) {
					// Cancella il file su file system e su PBANDI_T_DOCUMENTO_INDEX.
					documentoManager.cancellaFile(idDocumentoIndex);
					if (e instanceof DataIntegrityViolationException) {
						logger.error(prf+"DataIntegrityViolationException: ",e);
						//throw new DaoException(ErrorMessages.ERROR_DUPLICATED_FILE_NAME_PER_FOLDER);
						// Restituisce in output l'esito del salvataggio.
						EsitoInsertFiles fileResult = new EsitoInsertFiles();
						fileResult.setEsito(false);
						fileResult.setMessaggio(ErrorMessages.ERROR_DUPLICATED_FILE_NAME_PER_FOLDER);
						fileResult.setNomeFile(fileDTO.getNomeFile());
						result.add(fileResult);
						continue;
					} else  {
						logger.error(prf+"Errore in insertPbandiTFile");
						//throw new DaoException("Salvataggio del file fallito.");
						// Restituisce in output l'esito del salvataggio.
						EsitoInsertFiles fileResult = new EsitoInsertFiles();
						fileResult.setEsito(false);
						fileResult.setMessaggio("Salvataggio del file fallito.");
						fileResult.setNomeFile(fileDTO.getNomeFile());
						result.add(fileResult);
						continue;
					}
				}
				
				// Restituisce in output nome e id del file salvato.
				EsitoInsertFiles fileResult = new EsitoInsertFiles();
				fileResult.setEsito(true);
				fileResult.setNomeFile(vo.getNomeFile());
				fileResult.setIdDocumentoIndex(idDocumentoIndex.longValue());
				result.add(fileResult);			 
				
			}  // fine salvataggio singolo file.
			
		} catch (DaoException e) {
			LOG.error(prf+" DaoException: ", e);
			throw new DaoException(e.getMessage());
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante il salvataggio dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		logger.info(prf+"Esito salvataggio files:");
		for (EsitoInsertFiles e : result)
			logger.info(prf+"  esito file "+e.getNomeFile()+" = "+e.getEsito()+"  "+e.getMessaggio()+"  "+e.getIdDocumentoIndex());
		
		return result;
	}
	
	private ArrayList<FileDTO> leggiFilesDaMultipart(List<InputPart> files, long idFolder ) throws Exception {
		String prf = "[DocumentoManager::leggiFilesDaMultipart] ";
		logger.info(prf + " BEGIN");
		ArrayList<FileDTO> result = new ArrayList<FileDTO>();
		try {

			String nomeFile = null;
			String mimeType = null;
			byte[] content;
			MultivaluedMap<String, String> header;
			
			for (InputPart inputPart : files) {
				
				header = inputPart.getHeaders();				
				nomeFile = getFileName(header);
				content = IOUtils.toByteArray(inputPart.getBody(InputStream.class, null));
								
				FileDTO fileDTO = new FileDTO();
				fileDTO.setBytes(content);
				fileDTO.setNomeFile(nomeFile);				
				fileDTO.setSizeFile(new Long(content.length));
				fileDTO.setIdFolder(idFolder);	
				logger.info(prf+"file estratto: nome = " + fileDTO.getNomeFile()+"; size = "+fileDTO.getSizeFile());
			
				result.add(fileDTO);
			}
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la lettura dei file da MultipartFormDataInput.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	public static String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst(Constants.CONTENT_DISPOSITION).split(";");
		for (String value : contentDisposition) {
			if (value.trim().startsWith(Constants.FILE_NAME_KEY)) {
				String[] name = value.split("=");
				if (name.length > 1) {
					return name[1].trim().replaceAll("\"", "");
				}
			}
		}
		return null;
	}

	public static String getMimeType(MultivaluedMap<String, String> header) {
		String[] contentType = header.getFirst(Constants.CONTENT_TYPE).split(";");
		for (String value : contentType) {
			if (!value.contains("=")) {
				return value.trim();
			}
		}
		return null;
	}
	
	private static List<InputPart> getParts(MultipartFormDataInput input) {
		Map<String, List<InputPart>> form = input.getFormDataMap();
		List<InputPart> result = new ArrayList();

		for (Map.Entry<String, List<InputPart>> item : form.entrySet()) {
			List<InputPart> inputParts = item.getValue();
			if (!CollectionUtils.isEmpty(inputParts)) {
				result.addAll(inputParts);
			}
		}
		return result;
	}
	
	public BigDecimal insertPbandiTFile(PbandiTFileVO vo) throws Exception {
		String prf = "[DocumentoManager::insertPbandiTFile] ";
		logger.info(prf + " BEGIN");
		BigDecimal id = null;
		try {
			logger.info(vo.toString());
			
			java.sql.Date oggi = DateUtil.getSysdate();
			vo.setDtInserimento(oggi);
			
			String sqlSeq = "select SEQ_PBANDI_T_FILE.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI_T_FILE ");
			sql.append("(ID_FILE,DT_INSERIMENTO,ID_DOCUMENTO_INDEX,ID_FOLDER,ID_UTENTE_INS,NOME_FILE,SIZE_FILE)");
			sql.append("VALUES (?,?,?,?,?,?,?)");
			logger.info("\n"+sql);
			
			Object[] params = new Object[7];
			params[0] = id;
			params[1] = vo.getDtInserimento();
			params[2] = vo.getIdDocumentoIndex();
			params[3] = vo.getIdFolder();
			params[4] = vo.getIdUtenteIns();
			params[5] = vo.getNomeFile();
			params[6] = vo.getSizeFile();
			
			getJdbcTemplate().update(sql.toString(), params);			
			logger.info(prf+"id nuovo record inserito = "+id);
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante l'inserimento in PBANDI_T_FILE.", e);
		}
		finally {
			logger.info(prf+" END");
		}
		
		return id;
	}
	
	private Esito isFileSizeValid(FileDTO file, UserSpaceDTO userSpace, Long totalFileSizeLimit){
		Esito esito=new Esito();
		esito.setEsito(true);		
		
		int fileSize = file.getBytes().length;
		if(fileSize == 0) {
			logger.error("File "+file.getNomeFile()+" not valid: 0 byte size");
			esito.setEsito(false);
			esito.setMessaggio(ErrorMessages.ERROR_ZERO_FILE_SIZE);
			return esito;
		}
		
		if(fileSize > totalFileSizeLimit) {
			logger.error("File "+file.getNomeFile()+" not valid:  byte size ("+fileSize+") > totalFileSizeLimit ("+totalFileSizeLimit*1000+")"); 
			String[] params = new String[1];
			BigDecimal megaBytesLimit = NumberUtil.divide(totalFileSizeLimit.doubleValue(), new Double(1024*1024));
			params[0] = NumberUtil.getStringValue(megaBytesLimit);
			esito.setEsito(false);
			esito.setMessaggio(StringUtil.formattaMsgConParam(ErrorMessages.ERROR_MAX_FILE_SIZE, params));
			return esito;
		}
		
		long userSpaceUsed = userSpace.getUsed();
		userSpaceUsed += fileSize;
		long totalUserSpace = userSpace.getTotal();
		if(userSpaceUsed > (totalUserSpace*1000)) { 
			logger.error("File "+file.getNomeFile()+" not valid: userSpaceUsed("+userSpaceUsed+") > totalUserSpace("+totalUserSpace+")");
			esito.setEsito(false);
			String[] params = new String[1];
			params[0] = ""+totalUserSpace;
			esito.setMessaggio(StringUtil.formattaMsgConParam(ErrorMessages.ERROR_USER_SPACE_FULL, params));
			return esito;
		}
		
		return esito;
	}
	
	private Esito isFileExtensionValid(FileDTO file, List<String> allowedExtensions) {
		Esito esito = new Esito();
		esito.setEsito(false);
		for (String ext : allowedExtensions) {
			if(file.getNomeFile().endsWith("." + ext.trim())) {
				esito.setEsito(true);
				break;
			}
		}
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.createFolder()
	public Long creaFolder(String nomeFolder, long idFolderPadre, long idSoggettoBeneficiario, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::creaFolder] ";
		LOG.info(prf + " BEGIN");
		
		if (StringUtil.isEmpty(nomeFolder)) {
			throw new InvalidParameterException("nomeFolder non valorizzato.");
		}
		if (idFolderPadre == 0) {
			throw new InvalidParameterException("idFolderPadre non valorizzato.");
		}
		if (idSoggettoBeneficiario == 0) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		
		Long idNuovoFolder = null;
		try {
			
			String sqlSeq = "select SEQ_PBANDI_T_FOLDER.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			BigDecimal id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
		
			String sql = new String();
			sql =  "INSERT INTO PBANDI_T_FOLDER ";
			sql += "(ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_UTENTE_INS, DT_INSERIMENTO) ";
			sql += "VALUES (?,?,?,?,?,?) ";
			logger.info(prf + "\n" + sql + "\n con parametri: idFolder = "+id+"; idFolderPadre = "+idFolderPadre+"; nomeFolder = "+nomeFolder+"; idSoggettoBeneficiario = "+idSoggettoBeneficiario+"; idUtente = "+idUtente);
			
			Object[] params = new Object[6];
			params[0] = id;
			params[1] = idFolderPadre;
			params[2] = nomeFolder;
			params[3] = idSoggettoBeneficiario;
			params[4] = idUtente;
			params[5] = DateUtil.getSysdate();
			
			getJdbcTemplate().update(sql.toString(), params);			
			logger.info(prf+"id nuovo record inserito = "+id);
			
			idNuovoFolder = id.longValue();
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la creazione del folder.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return idNuovoFolder;
	}
	
	// Ex ArchivioBusinessImpl.renameFolder()
	public Boolean rinominaFolder(String nomeFolder, long idFolder, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::rinominaFolder] ";
		LOG.info(prf + " BEGIN");
		
		if (StringUtil.isEmpty(nomeFolder)) {
			throw new InvalidParameterException("nomeFolder non valorizzato.");
		}
		if (idFolder == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			String sql = new String();
			sql =  "UPDATE PBANDI_T_FOLDER SET NOME_FOLDER = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = ? WHERE ID_FOLDER = ?";
			logger.info(prf + "\n" + sql + "\n con parametri: nomeFolder = "+nomeFolder+"; idFolder = "+idFolder+"; idUtente = "+idUtente);
			
			Object[] params = new Object[4];
			params[0] = nomeFolder;
			params[1] = idUtente;
			params[2] = DateUtil.getSysdate();
			params[3] = idFolder;
			
			getJdbcTemplate().update(sql.toString(), params);
			
		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la rinomina del folder.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.moveFolder()
	// idFolder: id della cartella di partenza.
	// idFolderDestinazione = id della cartella di destinazione.
	public Boolean spostaFolder(long idFolder, long idFolderDestinazione, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::spostaFolder] ";
		LOG.info(prf + " BEGIN");
		
		if (idFolder == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		if (idFolderDestinazione == 0) {
			throw new InvalidParameterException("idFolderDestinazione non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			String sql = new String();
			sql =  "UPDATE PBANDI_T_FOLDER SET ID_PADRE = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = ? WHERE ID_FOLDER = ?";
			logger.info(prf + "\n" + sql + "\n con parametri: idPadre = "+idFolderDestinazione+"; idFolder = "+idFolder+"; idUtente = "+idUtente);
			
			Object[] params = new Object[4];
			params[0] = idFolderDestinazione;
			params[1] = idUtente;
			params[2] = DateUtil.getSysdate();
			params[3] = idFolder;
			
			getJdbcTemplate().update(sql.toString(), params);
			
			aggiornaTracciamentoEntita(idUtente, "ArchivioImpl.moveFolder", idFolder, idFolder, idFolderDestinazione);
			
		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante lo spostamento del folder.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.deleteFolder()
	public Boolean cancellaFolder(long idFolder, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::cancellaFolder] ";
		LOG.info(prf + " BEGIN");
		
		if (idFolder == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			String sql = new String();
			sql =  "DELETE PBANDI_T_FOLDER WHERE ID_FOLDER = "+idFolder;
			logger.info(prf + "\n" + sql);
			
			getJdbcTemplate().update(sql.toString(), new Object[] {});
			
		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante lo spostamento del folder.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.renameFile()
	@Transactional(rollbackFor = {Exception.class})
	public Boolean rinominaFile(String nomeFile, long idDocumentoIndex, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::rinominaFile] ";
		LOG.info(prf + " BEGIN");
		
		if (StringUtil.isEmpty(nomeFile)) {
			throw new InvalidParameterException("nomeFile non valorizzato.");
		}
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			// PBANDI_T_FILE
			
			String sqlTFile = new String();
			sqlTFile =  "UPDATE PBANDI_T_FILE SET NOME_FILE = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = ? WHERE ID_DOCUMENTO_INDEX = ?";
			logger.info(prf + "\n" + sqlTFile + "\n con parametri: nomeFile = "+nomeFile+"; idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);
			
			Object[] params = new Object[4];
			params[0] = nomeFile;
			params[1] = idUtente;
			params[2] = DateUtil.getSysdate();
			params[3] = idDocumentoIndex;
			
			getJdbcTemplate().update(sqlTFile.toString(), params);
			
			// PBANDI_T_DOCUMENTO_INDEX
			
			String sql = new String();
			sql =  "UPDATE PBANDI_T_DOCUMENTO_INDEX SET NOME_FILE = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO_INDEX = ? WHERE ID_DOCUMENTO_INDEX = ?";
			logger.info(prf + "\n" + sql + "\n con parametri: nomeFile = "+nomeFile+"; idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);
			
			getJdbcTemplate().update(sql.toString(), params);

		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la rinomina del file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.moveFile()
	// idDocumentoIndex: id del documento da spostare.
	// idFolder: id della cartella di partenza.
	// idFolderDestinazione = id della cartella di destinazione.
	public Boolean spostaFile(long idDocumentoIndex, long idFolder, long idFolderDestinazione, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::spostaFile] ";
		LOG.info(prf + " BEGIN");
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idFolder == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		if (idFolderDestinazione == 0) {
			throw new InvalidParameterException("idFolderDestinazione non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			String sqlId = "SELECT ID_FILE FROM PBANDI_T_FILE WHERE ID_FOLDER = "+idFolder+" AND ID_DOCUMENTO_INDEX = "+idDocumentoIndex;
			LOG.debug(prf+"\n"+sqlId);
			BigDecimal idFile = (BigDecimal) getJdbcTemplate().queryForObject(sqlId, BigDecimal.class);
			
			String sql = "UPDATE PBANDI_T_FILE SET ID_FOLDER = "+idFolderDestinazione+", ID_UTENTE_AGG = "+idUtente+", DT_AGGIORNAMENTO = SYSDATE WHERE ID_FILE = "+idFile;
			
			//logger.info(prf + "\n" + sql + "\n con parametri: idFolder nuovo = "+idFolderDestinazione+"; idFolder vecchio = "+idFolder+"; idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);			
			
			//DateUtil.getSysdate()
			//Object[] params = new Object[5];
			//params[0] = idFolderDestinazione;
			//params[1] = idUtente;
			//params[2] = DateUtil.getSysdate();
			//params[3] = idFolder;
			//params[4] = idDocumentoIndex;			
			//getJdbcTemplate().update(sql.toString(), params);			
			
			LOG.debug(prf+"\n"+sql);
			getJdbcTemplate().update(sql.toString());
			
			aggiornaTracciamentoEntita(idUtente, "ArchivioImpl.moveFile", idFile.longValue(), idFolder, idFolderDestinazione);
			
		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante lo spostamento del file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Ex ArchivioBusinessImpl.deleteFile()
	@Transactional
	public Boolean cancellaFile(long idDocumentoIndex, long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::cancellaFile] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoIndex = "+idDocumentoIndex+"; idUtente = "+idUtente);
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		
		Boolean esito = true;
		try {
			
			String sqlIdFile = "SELECT ID_FILE FROM PBANDI_T_FILE WHERE ID_DOCUMENTO_INDEX = "+idDocumentoIndex;
			LOG.info(prf+"\n"+sqlIdFile);
			BigDecimal idFile = (BigDecimal) getJdbcTemplate().queryForObject(sqlIdFile, BigDecimal.class);
			
			String sqlFileEntita = "SELECT COUNT(*) FROM PBANDI_T_FILE_ENTITA WHERE ID_FILE = "+idFile;
			LOG.info(prf+"\n"+sqlFileEntita);
			Integer numFileEntita = getJdbcTemplate().queryForObject(sqlFileEntita, Integer.class);
			LOG.info(prf + "Record trovati = " + numFileEntita);
			
			if (numFileEntita.intValue() > 0)
				throw new DaoException("Impossibile cancellare il file poiche' risulta allegato. ");
			
			PbandiTDocumentoIndexVO docIndexVO = documentoManager.trovaTDocumentoIndex(idDocumentoIndex);
			
			PbandiTDocumentoIndexLockVO lockVO = beanUtil.transform(docIndexVO, PbandiTDocumentoIndexLockVO.class);
			lockVO.setIdUtente(BigDecimal.valueOf(idUtente));

			// delete from PBANDI_T_DOCUMENTO_INDEX_LOCK where ID_ENTITA = 360 and ID_UTENTE = 21958 and ID_TIPO_DOCUMENTO_INDEX = 23 and ID_TARGET = 25038
			String whereDeleteLock = "";
			if (lockVO.getIdEntita() != null) {
				whereDeleteLock += "ID_ENTITA = "+lockVO.getIdEntita()+" ";
			}
			if (lockVO.getIdTarget() != null) {
				if (whereDeleteLock.length() > 0)
					whereDeleteLock += "AND ";
				whereDeleteLock += "ID_TARGET = "+lockVO.getIdTarget()+" ";
			}
			if (lockVO.getIdUtente() != null) {
				if (whereDeleteLock.length() > 0)
					whereDeleteLock += "AND ";
				whereDeleteLock += "ID_UTENTE = "+lockVO.getIdUtente()+" ";
			}
			if (lockVO.getIdTipoDocumentoIndex() != null) {
				if (whereDeleteLock.length() > 0)
					whereDeleteLock += "AND ";
				whereDeleteLock += "ID_TIPO_DOCUMENTO_INDEX = "+lockVO.getIdTipoDocumentoIndex()+" ";
			}
			String sqlDeleteLock = "DELETE PBANDI_T_DOCUMENTO_INDEX_LOCK WHERE "+whereDeleteLock;
			LOG.info(prf+"\n"+sqlDeleteLock);
			int j = getJdbcTemplate().update(sqlDeleteLock);
			LOG.info(prf+"Record cancellati = "+j);
			
			
			long maxTimeLock=1800000;
			boolean lockNonPresente=false;
			lockVO.setIdUtente(null);
			
			String whereLock = "";
			if (lockVO.getIdEntita() != null) {
				whereLock += "ID_ENTITA = "+lockVO.getIdEntita().longValue()+" ";
			}
			if (lockVO.getIdTarget() != null) {
				if (whereLock.length() > 0)
					whereLock += "AND ";
				whereLock += "ID_TARGET = "+lockVO.getIdTarget().longValue()+" ";
			}
			if (lockVO.getIdTipoDocumentoIndex() != null) {
				if (whereLock.length() > 0)
					whereLock += "AND ";
				whereLock += "ID_TIPO_DOCUMENTO_INDEX = "+lockVO.getIdTipoDocumentoIndex().longValue()+" ";
			}
			String sqlLock = "SELECT * FROM PBANDI_T_DOCUMENTO_INDEX_LOCK WHERE "+whereLock;
			LOG.info(prf+"\n"+sqlLock);
			List<PbandiTDocumentoIndexLockVO> locks = getJdbcTemplate().query(sqlLock, new Object[] {}, new BeanRowMapper(PbandiTDocumentoIndexLockVO.class));
			LOG.info(prf + "Record trovati = " + locks.size());
			
			if(locks.size() == 0) {
				lockNonPresente = true;
			} else {
				for (PbandiTDocumentoIndexLockVO pbandiTDocumentoIndexLockVO : locks) {
					java.sql.Date dtLockDocumento = pbandiTDocumentoIndexLockVO.getDtLockDocumento();
					long timeLock = dtLockDocumento.getTime();
					long actualTime = System.currentTimeMillis();
					LOG.info(prf + "actualTime-timeLock= "+(actualTime-timeLock));				
					if((actualTime-timeLock) < maxTimeLock) {
						lockNonPresente = true;
					}
				}
			}
			
			if (!lockNonPresente)
				throw new DaoException("Impossibile eliminare il documento, lock presente.");
			
			String sqlDeleteFile = "DELETE PBANDI_T_FILE WHERE ID_FILE = "+idFile.longValue();
			LOG.info(prf+"\n"+sqlDeleteFile);
			int i = getJdbcTemplate().update(sqlDeleteFile);
			LOG.info(prf+"Record cancellati = "+i);
			
			boolean esitoDocManager = documentoManager.cancellaFile(docIndexVO.getIdDocumentoIndex());
			if (!esitoDocManager)
				throw new DaoException("Cancellazione del file fallita.");
			
			
		} catch (DaoException e) {
			esito = false;
			LOG.error(prf+" ERRORE DaoException: ", e);
			throw new DaoException(e.getMessage());
		} catch (Exception e) {
			esito = false;
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la cancellazione del file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return esito;
	}
	
	// Memorizza la modifica di un campo nella PBANDI_T_TRACCIAMENTO_ENTITA.
	// Ex ArchivioBusinessImpl.aggiornaTracciamentoEntita().
	private void aggiornaTracciamentoEntita(Long idUtente, String descOperazione, Long target, Long valorePrec, Long valoreNuovo) {
		try {
			LOG.info("aggiornaTracciamentoEntita(): idUtente = "+idUtente+"; descOperazione = "+descOperazione+"; valorePrec = "+valorePrec+"; valoreNuovo = "+valoreNuovo);					

			if (idUtente == null) {
				throw new Exception("Parametro idUtente non valorizzato.");
			}
			if (StringUtil.isEmpty(descOperazione)) {
				throw new Exception("Parametro descOperazione non valorizzato.");
			}
			if (valorePrec == null) {
				throw new Exception("Parametro valorePrec non valorizzato.");
			}
			if (valoreNuovo == null) {
				throw new Exception("Parametro valoreNuovo non valorizzato.");
			}
			
			String opMoveFile = "ArchivioImpl.moveFile";
			String opMoveFolder = "ArchivioImpl.moveFolder";
			
			// Trovo l'id_operazione in PBANDI_C_OPERAZIONE (es ArchivioImpl.moveFile -> 625).
			String sqlOperaz = "SELECT ID_OPERAZIONE FROM PBANDI_C_OPERAZIONE WHERE DESC_FISICA = '"+descOperazione+"' AND DT_FINE_VALIDITA IS NOT NULL";
			LOG.debug("\n"+sqlOperaz);			
			BigDecimal idOperazione = (BigDecimal) getJdbcTemplate().queryForObject(sqlOperaz, BigDecimal.class);
			if (idOperazione == null) {
				throw new Exception("Id Operazione non trovato.");
			}
			
			// Trovo l'ultimo id tracciamento in PBANDI_T_TRACCIAMENTO per utente e operazione. 
			List<BigDecimal> idTracciamenti = new ArrayList<BigDecimal>();
			String sqlTrac = "SELECT ID_TRACCIAMENTO FROM PBANDI_T_TRACCIAMENTO WHERE ID_UTENTE = "+idUtente+" AND ID_OPERAZIONE = "+idOperazione+" ORDER BY ID_TRACCIAMENTO DESC";
			LOG.debug("\n"+sqlTrac);	
			idTracciamenti = getJdbcTemplate().queryForList(sqlTrac, BigDecimal.class);
			if (idTracciamenti == null || idTracciamenti.size() == 0) {
				throw new Exception("Nessun tracciamento trovato.");
			}
			BigDecimal idTracciamento = idTracciamenti.get(0);
			
			// Trovo l'entità
			String nomeEntita = "";
			if (descOperazione.equalsIgnoreCase(opMoveFile))
				nomeEntita = Constants.ENTITA_C_FILE;
			else if (descOperazione.equalsIgnoreCase(opMoveFolder))
				nomeEntita = Constants.ENTITA_C_FOLDER;
			BigDecimal idEntita = this.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", nomeEntita);
			
			// Trovo l'attributo.
			String nomeAttributo = "";
			if (descOperazione.equalsIgnoreCase(opMoveFile))
				nomeAttributo = "ID_FOLDER";
			else if (descOperazione.equalsIgnoreCase(opMoveFolder))
				nomeAttributo = "ID_PADRE";
			String sqlAttr = "SELECT ID_ATTRIBUTO FROM PBANDI_C_ATTRIBUTO WHERE ID_ENTITA = "+idEntita.longValue()+"' AND NOME_ATTRIBUTO = '"+nomeAttributo+"'";
			LOG.debug("\n"+sqlAttr);			
			BigDecimal idAttributo = (BigDecimal) getJdbcTemplate().queryForObject(sqlAttr, BigDecimal.class);
			
			String sqlSeq = "select SEQ_PBANDI_T_TRAC_ENTITA.nextval from dual ";
			LOG.info("\n"+sqlSeq);
			BigDecimal idTracciamentoEntita = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq, BigDecimal.class);
			
			String sqlTracEntita = "INSERT INTO PBANDI_T_TRACCIAMENTO_ENTITA ";
			sqlTracEntita += "(ID_TRACCIAMENTO_ENTITA, ID_ATTRIBUTO, ID_ENTITA, ID_TRACCIAMENTO, TARGET, VALORE_PRECEDENTE, VALORE_SUCCESSIVO, DESC_ATTIVITA) ";			
			sqlTracEntita += "VALUES ("+idTracciamentoEntita.longValue()+", ";
			sqlTracEntita += idAttributo.longValue()+", ";
			sqlTracEntita += idEntita.longValue()+", ";
			sqlTracEntita += idTracciamento.longValue()+", ";
			sqlTracEntita += target+", ";
			sqlTracEntita += valorePrec+", ";
			sqlTracEntita += valoreNuovo+", ";
			sqlTracEntita += "'AGGIORNAMENTO')";
			LOG.info("\n"+sqlTracEntita);
			getJdbcTemplate().update(sqlTracEntita);
			LOG.info("aggiornaTracciamentoEntita(): inserito record con IdTracciamentoEntita = "+idTracciamentoEntita);
			
		} catch (Exception e) {
			LOG.error("ERRORE in aggiornaTracciamentoEntita(): ", e);
		}
	}
	
	// Usa servizio PBANDISRV.
	public FileInfoDTO infoFile(long idDocumentoIndex, long idFolder, long idUtente, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::infoFile] ";
		LOG.info(prf + " BEGIN");
		
		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idFolder == 0) {
			throw new InvalidParameterException("idFolder non valorizzato.");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		FileInfoDTO result = new FileInfoDTO();
		try {
			
			String idIride = RequestUtil.idIrideInSessione(request);
			
			result = archivioBusinessImpl.getFileInfo(idUtente, idIride, idDocumentoIndex, idFolder);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la ricerca delle informazioni del file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// Associa più file ad una qualche entità (dich di spesa, pagamento, etc).	
	// Ex pbandiweb ArchivioFileAction.associate() + pbandisrv ArchivioBusinessImpl.associateFile()
	// NOTA: ad oggi questo non è un servizio richiamabile da angular.
	public EsitoAssociaFilesDTO associaFiles(AssociaFilesRequest associaFilesRequest, Long idEntita, Long idUtente)
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::associaFiles] ";
		LOG.info(prf + " BEGIN");
		
		if (associaFilesRequest == null) {
			throw new InvalidParameterException("associaFilesRequest non valorizzato");
		}
		if (idEntita == null) {
			throw new InvalidParameterException("idEntita non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		ArrayList<Long> elencoIdDocumentoIndex = associaFilesRequest.getElencoIdDocumentoIndex();
		Long idTarget = associaFilesRequest.getIdTarget();
		Long idProgetto = associaFilesRequest.getIdProgetto(); 
		
		if (elencoIdDocumentoIndex == null) {
			throw new InvalidParameterException("elencoIdDocumentoIndex non valorizzato");
		}
		if (idTarget == null) {
			throw new InvalidParameterException("idTarget non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		
		EsitoAssociaFilesDTO result = new EsitoAssociaFilesDTO();
		try {
			
			String s = "";
			for (Long id : associaFilesRequest.getElencoIdDocumentoIndex())
				s = s + id + "  ";
			LOG.info(prf+"IdEntita = "+idEntita+"; IdTarget = "+idTarget+"; IdProgetto = "+idProgetto+"; IdUtente = "+idUtente+"; ElencoIdDocumentoIndex = "+s);
					
			BigDecimal idEntitaFornitore = this.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA", Constants.ENTITA_T_FORNITORE);
			
			// Scorro i file passati in input.
			for (Long idDocIndex : elencoIdDocumentoIndex) {
				
				// Recupera l'id_file in pbandi_t_file.
				String sqlTFile = "SELECT ID_FILE FROM pbandi_t_file WHERE id_documento_index = "+idDocIndex;
				LOG.info("\n"+sqlTFile);				
				Long idFile = (Long) getJdbcTemplate().queryForObject(sqlTFile, Long.class);
				LOG.info(prf + "idFile = "+idFile);
				
				// Verifico se il file è già stato associato precedentemente.
				boolean fileGiaAssociato;
				if (idEntita.intValue() == idEntitaFornitore.intValue()) {
					fileGiaAssociato = this.fileGiaAssociatoFornitore(idFile, idEntita, idTarget, idProgetto);
				} else {
					fileGiaAssociato = this.fileGiaAssociato(idFile, idEntita, idTarget);
				}
				
				if (fileGiaAssociato) {
					// Il file risulta già associato: non può essere associato nuoamente.
					result.getElencoIdDocIndexFilesNonAssociati().add(idDocIndex);
				} else {
					
					// Associo il file.
					try {
					
						// Aggiorna DtAggiornamentoIndex e IdUtenteAgg nella TDocumentoIndex.
						String sqlUpdateTIndex = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET ID_UTENTE_AGG = "+idUtente+", DT_AGGIORNAMENTO_INDEX = SYSDATE WHERE ID_DOCUMENTO_INDEX = "+idDocIndex;
						logger.info(prf + "\n"+sqlUpdateTIndex);					
						getJdbcTemplate().update(sqlUpdateTIndex);
						
						String sqlSeq = "select SEQ_PBANDI_T_FILE_ENTITA.nextval from dual ";
						logger.info("\n"+sqlSeq.toString());
						Long idTFileEntita = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
						
						// Quando la dich/richErog/comunicazFineProg/contoeconomico non è ancora stata creata(preassociazione)
						// viene inserito come id_target l'id progetto. 
						// Alla creazione della dich/richErog/comunicazFineProg l'id target verrà sovrascritto 
						// con l'id di dich/richErog/comunicazFineProg e verrà inserito l'idProgetto.
						String sqlInsertTFileEntita;
						if (this.idProgettoComeIdTarget(idEntita)) {
							// Caso con idProgetto al posto di idTarget.
							sqlInsertTFileEntita = "INSERT INTO PBANDI_T_FILE_ENTITA (ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET) VALUES ("+idTFileEntita+","+idFile+","+idEntita+","+idProgetto+")";
						} else {
							// Caso normale.
							sqlInsertTFileEntita = "INSERT INTO PBANDI_T_FILE_ENTITA (ID_FILE_ENTITA, ID_FILE, ID_ENTITA, ID_TARGET, ID_PROGETTO) VALUES ("+idTFileEntita+","+idFile+","+idEntita+","+idTarget+","+idProgetto+")";
						}
						logger.info(prf + "\n"+sqlInsertTFileEntita);
						
						getJdbcTemplate().update(sqlInsertTFileEntita);
						
						result.getElencoIdDocIndexFilesAssociati().add(idDocIndex);
					} catch (Exception e) {
						LOG.error(prf+"ERRORE: durante l'associazione del file con idDocIndex = "+idDocIndex, e);
						result.getElencoIdDocIndexFilesNonAssociati().add(idDocIndex);
					}
				}

			}
						
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la associazione dei file.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	// Restituisce true se l'id entita è di quelli che richiede di assegnare l'id progetto al campo id target.
	// In pbandisrv questo controllo stava in pbandisrv ArchivioBusinessImpl.associateFile().
	private boolean idProgettoComeIdTarget(Long idEntita) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::idProgettoComeIdTarget] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idEntita = "+idEntita);
		
		boolean idProgettoComeIdTarget = false;
		try {
			
			String sql = "SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA IN ('PBANDI_T_COMUNICAZ_FINE_PROG','PBANDI_T_CONTO_ECONOMICO','PBANDI_T_DICHIARAZIONE_SPESA','PBANDI_T_RICHIESTA_EROGAZIONE')";
			LOG.info("\n"+sql);				
			List<Long> listaIdIdentita = getJdbcTemplate().queryForList(sql, Long.class);
			
			for (Long id : listaIdIdentita) {
				if (idEntita.intValue() == id.intValue()) {
					idProgettoComeIdTarget = true;
				}
			}
			LOG.info(prf+" idProgettoComeIdTarget = "+idProgettoComeIdTarget);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la verifica dell'associazione.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return idProgettoComeIdTarget;
	}
	
	// Ex pbandisrv ArchivioBusinessImpl.isFileAssociated()
	private boolean fileGiaAssociato(Long idFile, Long idEntita, Long idTarget) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::fileGiaAssociato] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idFile = "+idFile+"; idEntita = "+idEntita+"; idTarget = "+idTarget);
		
		boolean fileGiaAssociato = false;
		try {
			
			// Verifica se il file è già associato.
			String sqlTFileEntita = "SELECT COUNT(*) FROM PBANDI_T_FILE_ENTITA WHERE ID_FILE = "+idFile+" AND ID_ENTITA = "+idEntita+" AND ID_TARGET = "+idTarget;
			LOG.info(prf+"\n"+sqlTFileEntita);
			Integer numFileEntita = getJdbcTemplate().queryForObject(sqlTFileEntita, Integer.class);
			LOG.info(prf + "Record trovati = " + numFileEntita);
			
			fileGiaAssociato = (numFileEntita > 0);
			LOG.info(prf+" fileGiaAssociato = "+fileGiaAssociato);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la verifica dell'associazione.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return fileGiaAssociato;
	}
	
	// Ex pbandisrv ArchivioBusinessImpl.isFileAssociatedFornitore()
	private boolean fileGiaAssociatoFornitore(Long idFile, Long idEntita, Long idTarget, Long idProgetto) throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::fileGiaAssociatoFornitore] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idFile = "+idFile+"; idEntita = "+idEntita+"; idTarget = "+idTarget+"; idProgetto = "+idProgetto);
		
		boolean fileGiaAssociato = false;
		try {
						
			// Verifica se il file è già associato.
			String sqlTFileEntita = "SELECT COUNT(*) FROM PBANDI_T_FILE_ENTITA WHERE ID_FILE = "+idFile+" AND ID_ENTITA = "+idEntita+" AND ID_TARGET = "+idTarget+" AND ID_PROGETTO = "+idProgetto;
			LOG.info(prf+"\n"+sqlTFileEntita);
			Integer numFileEntita = getJdbcTemplate().queryForObject(sqlTFileEntita, Integer.class);
			LOG.info(prf + "Record trovati = " + numFileEntita);
			
			fileGiaAssociato = (numFileEntita > 0);
			LOG.info(prf+" fileGiaAssociato = "+fileGiaAssociato);
			
		} catch (Exception e) {
			LOG.error(prf+" ERRORE: ", e);
			throw new DaoException("Errore durante la verifica dell'associazione.", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return fileGiaAssociato;
	}
	
	// Restituisce il campo PBANDI_C_COSTANTI.VALORE in base al campo ATTRIBUTO.
	// Se non la trova e obbligatoria = true, innalza una eccezione.
	private String costante(String attributo, boolean obbligatoria) throws DaoException {
		String prf = "[ArchivioFileDAOImpl::costante()] ";
		LOG.info(prf + "BEGIN");
		String valore = null;
		try {
			
			String sql = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			LOG.debug("\n"+sql.toString());
			
			valore = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
			
			if (obbligatoria && StringUtil.isEmpty(valore)) {
				throw new DaoException("Costante "+attributo+" non trovata.");
			}
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di una COSTANTE: ", e);
			throw new DaoException("ERRORE nella ricerca della costante "+attributo+".", e);
		}
		finally {
			LOG.info(prf+"END");
		}
		
		return valore;
	}
	
	private BigDecimal idDaDescrizione(String tabella, String colonnaId, String colonnaDescrizione, String valoreDescrizione) 
			throws DaoException {
		String prf = "[ArchivioFileDAOImpl::idDaDescrizione()] ";
		LOG.info(prf + "BEGIN");
		BigDecimal id = null;
		try {			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT "+colonnaId+" FROM "+tabella+" WHERE "+colonnaDescrizione+" = '"+valoreDescrizione+"'");
			LOG.debug("\n"+sql.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sql.toString(), BigDecimal.class);			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di un id da una descriziozne: ", e);
			throw new DaoException("ERRORE nella ricerca di un id da una descriziozne: ", e);
		}
		finally {
			LOG.info(prf+"END");
		}
		
		return id;
	}
	
	*/
}
