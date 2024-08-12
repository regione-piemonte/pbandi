package it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentazione;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DichiarazioneDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DatiUploadFileConVisibilitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DimensioneFileZipDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.EsitoEliminaFileConVisibilitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.EsitoUploadFileConVisibilitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.FiltroDownloadZip;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.FiltroTipoDocumentoIndexDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.TipoDocumentoIndexDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.ConfigurationDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiRicercaDocumentiDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocAllegatiSpazioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoBandoLineaLightVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.columnfilter.ProgettoBandoLineaLightFilterByBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.AllegatiDocumentiRendicontatiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.AllegatiDocumentoSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.AllegatiIntegrazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.AllegatiPagamentoDsVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.DocumentoIndexProgettoBeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.TipoDocumentoIndexAnagraficaEnteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.TipoDocumentoIndexUtente2VO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.TipoDocumentoIndexUtenteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRCategAnagDocIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedocumentazione.GestioneDocumentazioneSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.Constants;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.doqui.index.ecmengine.dto.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class GestioneDocumentazioneBusinessImpl extends BusinessImpl implements
GestioneDocumentazioneSrv {

	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	private IndexDAO indexDAO;
	
	@Autowired
	private PbandiRicercaDocumentiDAOImpl ricercaDocumentiDAOImpl ;
	
	@Autowired
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
	
	@Autowired
	private ProgettoManager progettoManager;
	
	@Autowired
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	
	@Autowired
	private ConfigurationManager configurationManager;

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}
	public PbandiRicercaDocumentiDAOImpl getRicercaDocumentiDAOImpl() {
		return ricercaDocumentiDAOImpl;
	}

	public void setRicercaDocumentiDAOImpl(PbandiRicercaDocumentiDAOImpl ricercaDocumentiDAOImpl) {
		this.ricercaDocumentiDAOImpl = ricercaDocumentiDAOImpl;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	// Elimina i file e relativi dati caricati con la popup "Upload" nella finesta "Gestione Documenti".
	public EsitoEliminaFileConVisibilitaDTO eliminaFileConVisibilita(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocumentoIndex)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);
		logger.info("idDocumentoIndex = "+idDocumentoIndex);

		EsitoEliminaFileConVisibilitaDTO esito = new EsitoEliminaFileConVisibilitaDTO();
		try {

			BigDecimal idDocIndex = new BigDecimal(idDocumentoIndex);

			// Accedo a PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO docIndex = new PbandiTDocumentoIndexVO();
			docIndex.setIdDocumentoIndex(idDocIndex);
			docIndex = genericDAO.findSingleOrNoneWhere(docIndex);
			if (docIndex == null) {
				logger.error("Record in PBANDI_T_DOCUMENTO_INDEX non trovato.");
				esito.setEsito(false);
				return esito;
			}

			String uuid = docIndex.getUuidNodo();

			// Cancello da PBANDI_R_CATEG_ANAG_DOC_INDEX.
			PbandiRCategAnagDocIndexVO filtro = new PbandiRCategAnagDocIndexVO();
			filtro.setIdDocumentoIndex(idDocIndex);
			List<PbandiRCategAnagDocIndexVO> lista = genericDAO.findListWhere(filtro);
			for (PbandiRCategAnagDocIndexVO vo : lista) {
				logger.info("  Cancello idCategAnag = "+vo.getIdCategAnagrafica()+" - idDocIndex = "+vo.getIdDocumentoIndex());
				if (!genericDAO.delete(vo)) {
					throw new Exception("Delete su PBANDI_R_CATEG_ANAG_DOC_INDEX fallita.");					
				}
			}

			// Cancello da PBANDI_T_DOCUMENTO_INDEX.
			PbandiTDocumentoIndexVO docIndexVO = new PbandiTDocumentoIndexVO();
			docIndexVO.setIdDocumentoIndex(idDocIndex);
			if (!genericDAO.delete(docIndexVO)) {
				throw new Exception("Delete su PBANDI_T_DOCUMENTO_INDEX fallita.");					
			}

			// Cancello il file da INDEX.
			try {				
				Node nodo = new Node(uuid);
				logger.info("Cancello da INDEX il file con UUID "+uuid);
				indexDAO.cancellaNodo(nodo);
			} catch (Exception e) {
				logger.error("Eliminazione del file su INDEX fallita.");
				throw e;
			}

			esito.setEsito(true);

		} catch (Exception e) {
			String msg = "Impossibile eliminare il documento: "+e.getMessage();
			logger.error(msg, e);
			throw new GestioneDocumentazioneException(msg);
		} 

		return esito;
	}

	// Inserisce i file e relativi dati caricati con la popup "Upload" nella finesta "Gestione Documenti".
	public EsitoUploadFileConVisibilitaDTO uploadFileConVisibilita(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DatiUploadFileConVisibilitaDTO dati)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale","dati" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, dati);

		// Log...
		logDatiUploadFileConVisibilitaDTO(dati);

		EsitoUploadFileConVisibilitaDTO esito = new EsitoUploadFileConVisibilitaDTO();
		try {

			// Verifica i campi obbligatori.
			String msgErrore = checkDatiUploadFileConVisibilitaDTO(dati);
			if (!StringUtil.isBlank(msgErrore)) {
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}			

			// Verifica la dimensione del file; 		NOTA: uso il limite di ArchivioFile.			
			ConfigurationDTO configuration = configurationManager.getConfiguration();
			Long fileSizeLimit = configuration.getArchivioFileSizeLimit() * 1000;
			int fileSize= dati.getBytesDocumento().length;
			if(fileSize > fileSizeLimit){
				msgErrore = "Dimensione del file troppo grande.";
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}

			// Verifica l'estensione del file; 			NOTA: uso le estensioni di ArchivioFile.
			String estensioniConsentite = configuration.getArchivioAllowedFileExtensions();
			logger.info("estensioniConsentite = "+estensioniConsentite);
			StringTokenizer strtkz = new StringTokenizer(estensioniConsentite,",");
			List<String> allowedExtensions = new ArrayList<String>();
			while(strtkz.hasMoreElements()) {
				allowedExtensions.add(strtkz.nextToken());
			}
			if (!estensioneValida(dati.getNomeFile(), allowedExtensions)) {
				msgErrore = "Tipo di file non consentito.";
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}

			Date currentDate = new Date(System.currentTimeMillis());

			String nomeFile = indexDAO.addTimestampToFileName(dati.getNomeFile());

			BigDecimal idCategAnagrafica = findIdCategAnagrafica(dati.getDescBreveTipoAnagrafica());
			if (idCategAnagrafica == null) {
				msgErrore = "Categoria anagrafica non trovata per tipo anagrafica "+dati.getDescBreveTipoAnagrafica();
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}

			String tipoDocumento = findTipoDocumento(dati.getIdTipoDocumentoIndex());
			if (tipoDocumento == null || tipoDocumento.length() == 0) {
				msgErrore = "Descrizione Tipo Documento Index non trovata per id "+dati.getIdTipoDocumentoIndex();
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}

			// INSERT su Index
			logger.info("\n\nuploadFileConVisibilita - inserimento su INDEX");
			Node node =null;
			node = indexDAO.creaContentUploadFile(
					idUtente, 
					nomeFile,
					dati.getBytesDocumento(),
					dati.getIdProgetto(),
					tipoDocumento,
					dati.getDescBreveTipoAnagrafica());
			if (node == null) {
				msgErrore = "Nodo Index non creato.";
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}
			if (node.getUid() == null) {
				msgErrore = "Uid del Nodo Index non valorizzato.";
				logger.error(msgErrore);
				esito.setMessage(msgErrore);
				esito.setEsito(false);
				return esito;
			}

			// INSERT su PBANDI_T_DOCUMENTO_INDEX.
			logger.info("\n\nuploadFileConVisibilita - inserimento su PBANDI_T_DOCUMENTO_INDEX");
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(dati.getIdTipoDocumentoIndex()));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(new BigDecimal(dati.getIdProgetto()));
			documentoIndexVO.setIdCategAnagraficaMitt(idCategAnagrafica);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(new BigDecimal(53));					//PBANDI_T_PROGETTO
			documentoIndexVO.setIdTarget(new BigDecimal(dati.getIdProgetto()));	//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(Constants.APP_COMPANY_HOME_CM_BANDI);
			documentoIndexVO.setUuidNodo(node.getUid());
			documentoIndexVO = genericDAO.insert(documentoIndexVO);

			BigDecimal idDocIndex = documentoIndexVO.getIdDocumentoIndex();
			logger.info("\nidDocIndex inserito sul db "+documentoIndexVO.getIdDocumentoIndex()+"\n");			
			if (idDocIndex == null)
				throw new Exception("Errore nella insert su PBANDI_T_DOCUMENTO_INDEX: idDocIndex nullo.");

			// Inserisco le visibilita su PBANDI_R_CATEG_ANAG_DOC_INDEX: cancello e reinserisco.
			logger.info("\n\nuploadFileConVisibilita - inserimento su PBANDI_R_CATEG_ANAG_DOC_INDEX");			
			for (Long idCategAnag : dati.getVisibilita()) {
				if (idCategAnag != null) {
					PbandiRCategAnagDocIndexVO vo = new PbandiRCategAnagDocIndexVO();
					vo.setIdCategAnagrafica(new BigDecimal(idCategAnag));
					vo.setIdDocumentoIndex(idDocIndex);
					logger.info("  Inserisco idCategAnag = "+vo.getIdCategAnagrafica()+" - idDocIndex = "+vo.getIdDocumentoIndex());
					genericDAO.insert(vo);
				}
			}

			esito.setEsito(true);

		} catch (Exception e) {
			String msg = "Impossibile salvare il documento: "+e.getMessage();
			logger.error(msg, e);
			throw new GestioneDocumentazioneException(msg);
		} 

		return esito;
	}

	private BigDecimal findIdCategAnagrafica(String descBreveTipoAnagrafica) {
		PbandiDTipoAnagraficaVO tipo = new PbandiDTipoAnagraficaVO();
		tipo.setDescBreveTipoAnagrafica(descBreveTipoAnagrafica);
		tipo = genericDAO.findSingleOrNoneWhere(tipo);		
		if (tipo == null)
			return null;
		else 
			return tipo.getIdCategAnagrafica();
	}

	private String findTipoDocumento(Long idTipoDocumentoIndex) {
		PbandiCTipoDocumentoIndexVO tipo = new PbandiCTipoDocumentoIndexVO();
		tipo.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocumentoIndex));
		tipo = genericDAO.findSingleOrNoneWhere(tipo);		
		if (tipo == null)
			return null;
		else 
			return tipo.getDescTipoDocIndex();
	}

	private void logDatiUploadFileConVisibilitaDTO(DatiUploadFileConVisibilitaDTO dati) {
		logger.info("\n\n\nuploadFileConVisibilita - parametri in input:");
		logger.info("NomeFile = "+dati.getNomeFile());
		logger.info("DescBreveTipoAnagrafica = "+dati.getDescBreveTipoAnagrafica());
		logger.info("IdDocumentoIndex = "+dati.getIdDocumentoIndex());
		logger.info("IdProgetto = "+dati.getIdProgetto());
		logger.info("IdTipoDocumentoIndex = "+dati.getIdTipoDocumentoIndex());
		if (dati.getVisibilita() == null)
			logger.info("Visibilita = null");
		else {
			int l = dati.getVisibilita().length;
			Long[] v = dati.getVisibilita();
			logger.info("Visibilita.length = "+l);
			for (int i = 0; i < l; i++) {
				logger.info("  visibilita = "+v[i]);
			}
		}		
		if (dati.getBytesDocumento() == null)
			logger.info("BytesDocumento = null");
		else
			logger.info("BytesDocumento.length = "+dati.getBytesDocumento().length);
		logger.info("\n\n");
	}

	private String checkDatiUploadFileConVisibilitaDTO(DatiUploadFileConVisibilitaDTO dati) {
		if (dati.getBytesDocumento() == null || dati.getBytesDocumento().length == 0)
			return "File non valorizzato";

		if (dati.getDescBreveTipoAnagrafica() == null)
			return "DescBreveTipoAnagrafica non valorizzata";

		if (dati.getIdProgetto() == null)
			return "IdProgetto non valorizzato";

		if (dati.getIdTipoDocumentoIndex() == null)
			return "IdTipoDocumentoIndex non valorizzato";

		if (dati.getNomeFile() == null)
			return "NomeFile non valorizzato";

		if (dati.getVisibilita() == null || dati.getVisibilita().length == 0)
			return "Visibilita non valorizzato";

		return null;
	}

	private boolean estensioneValida(String nomeFile, List<String> allowedExtensions) {
		for (String ext : allowedExtensions) {
			if(nomeFile.endsWith("." + ext.trim())) {
				return true;
			}
		}
		return false;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] ricercaDocumenti(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.RicercaDocumentazioneDTO filtro

	)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException	{

		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
		logger.info("ricercaDocumenti con filtro: ");
		logger.shallowDump(filtro,"info");
		
		DocumentoIndexProgettoBeneficiarioVO filtroVO = beanUtil.transform(
				filtro, DocumentoIndexProgettoBeneficiarioVO.class,
				new HashMap<String, String>() {
					{
						put("idProgetto", "idProgetto");
						put("idSoggettoBeneficiario","idSoggettoBeneficiario");
						put("idTipoDocumentoIndex", "idTipoDocumentoIndex");
						put("idSoggetto", "idSoggetto");
						put("codiceRuolo","descBreveTipoAnagrafica");
						put("idBando", "progrBandoLineaIntervento");
					}
				});



		List<DocumentoIndexProgettoBeneficiarioVO> documentiIndex = getRicercaDocumentiDAOImpl().findDocumenti(filtroVO,
				filtro.getDataDal(),filtro.getDataAl(),filtro.getDocInFirma(),filtro.getDocInviati());
		DecodificaDTO statoNonValidato = null;
		DecodificaDTO statoInviato= null;
		if(!isEmpty(documentiIndex)){
			statoNonValidato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_NON_VALIDATO);
			statoInviato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_INVIATO);
		}
		logger.info("\nfound documentiIndex "+documentiIndex.size());
		List<DocumentoDTO> documentiDto =new ArrayList<DocumentoDTO>();
		for (DocumentoIndexProgettoBeneficiarioVO vo : documentiIndex) {
			DocumentoDTO dto=new DocumentoDTO();
			dto.setBeneficiario(vo.getBeneficiario());
			dto.setCodiceVisualizzato(vo.getCodiceVisualizzato());
			dto.setDtCreazione(vo.getDtInserimentoIndex());
//			logger.info("Data creazione : " + vo.getDtInserimentoIndex());
			dto.setDescErrore(vo.getDescErrore());
			dto.setDescTipoDocIndex(vo.getDescTipoDocIndex());
			dto.setFlagFirmaCartacea(vo.getFlagFirmaCartacea());
			dto.setFlagTrasmDest(vo.getFlagTrasmDest());
			dto.setIdDocumentoIndex(vo.getIdDocumentoIndex().longValue());
			dto.setNomeFile(vo.getNomeFile());
			dto.setNoteDocumentoIndex(vo.getNoteDocumentoIndex());
			if(vo.getIdProgetto()!=null)
				dto.setIdProgetto(vo.getIdProgetto().longValue());

			//	if(vo.getIdStatoDocumento()!=null && ( vo.getIdStatoDocumento().longValue()==statoInviato.getId().longValue())){
			dto.setTimeStamped(vo.getDtMarcaTemporale()!=null);
			dto.setDtTimestamp(vo.getDtMarcaTemporale());
			//}

			dto.setProtocollo(vo.getProtocollo());

			if(isSignable(vo)){
				dto.setSignable(Boolean.TRUE);
				dto.setSigned(vo.getDtVerificaFirma()!=null);
				if(vo.getIdStatoDocumento()!=null && ( vo.getIdStatoDocumento().longValue()==statoNonValidato.getId().longValue()))
					dto.setSignValid(Boolean.FALSE);
				dto.setDtSign(vo.getDtVerificaFirma());
			}
			dto.setCodStatoDoc(vo.getDescStatoDocumentoIndex());
			dto.setCodTipoDoc(vo.getDescBreveTipoDocIndex());
			
			// Campi aggiunti a DocumentoDTO per visualizzare gli allegati alle integrazioniDS
			// in Gestione Documenti.
			if (vo.getIdEntita() != null) {
				dto.setIdEntita(vo.getIdEntita().longValue());
			}
			if (vo.getIdTarget() != null) {
				dto.setIdTarget(vo.getIdTarget().longValue());
				//dto.setIdTarget(vo.getIdChecklist().longValue());
			}

			if (Constants.TIPO_DOC_INDEX_DS.equalsIgnoreCase(vo.getDescBreveTipoDocIndex())) {
				// Se e' un Documento Di Spesa, legge il tipoInvioDs.
				logger.info(vo.getIdDocumentoIndex()+"e' un Documento di Spesa: cerco il tipoInvioDs.");				
				PbandiTDocumentoIndexVO doc = new PbandiTDocumentoIndexVO();
				doc.setIdDocumentoIndex(vo.getIdDocumentoIndex());
				doc = genericDAO.findSingleOrNoneWhere(doc);
				if (doc != null) {
					PbandiTDichiarazioneSpesaVO ds = new PbandiTDichiarazioneSpesaVO();
					ds.setIdDichiarazioneSpesa(doc.getIdTarget());
					ds = genericDAO.findSingleOrNoneWhere(ds);
					if (ds != null) {
						logger.info("Trovato tipoInvioDs = "+ds.getTipoInvioDs());
						dto.setTipoInvioDs(ds.getTipoInvioDs());
					}
				}
			}

			if (Constants.TIPO_DOC_INDEX_CFP.equalsIgnoreCase(vo.getDescBreveTipoDocIndex())) {
				// Se e' un Documento Di Fine Progetto, legge il tipoInvioDs della DS Finale.
				logger.info(vo.getIdDocumentoIndex()+"e' un Documento di Fine Progetto: cerco il tipoInvioDs.");				
				PbandiTDocumentoIndexVO doc = new PbandiTDocumentoIndexVO();
				doc.setIdDocumentoIndex(vo.getIdDocumentoIndex());
				doc = genericDAO.findSingleOrNoneWhere(doc);
				if (doc != null) {					
					DichiarazioneDiSpesaConTipoVO ds = dichiarazioneDiSpesaManager.getDichiarazioneFinaleConComunicazione(doc.getIdProgetto().longValue()); 
					if (ds != null) {
						logger.info("Trovato tipoInvioDs = "+ds.getTipoInvioDs());
						dto.setTipoInvioDs(ds.getTipoInvioDs());
						//SOVRASCIVO ENTITA E TRAGET DLLA CFP CON QUELLO DELLA DS
						dto.setIdTarget(new Long(ds.getIdDichiarazioneSpesa().longValue()));
						dto.setIdEntita(new Long(documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class).longValue()));
					}
				}
			}

			if (vo.getIdCategAnagraficaMitt() != null) {
				dto.setIdCategAnagraficaMitt(vo.getIdCategAnagraficaMitt().longValue());
				dto.setDescCategAnagraficaMitt(vo.getDescCategAnagraficaMitt());
			}

			documentiDto.add(dto);
		}
		return  documentiDto.toArray(new DocumentoDTO[0]);
	}

	private boolean isSignable(DocumentoIndexProgettoBeneficiarioVO vo) {
		if(vo.getFlagFirmabile()!=null && vo.getFlagFirmabile().equalsIgnoreCase("S")
				&& vo.getFlagRegolaDemat()!=null && vo.getFlagRegolaDemat().equalsIgnoreCase("S"))
			return true;
		return false;
	}

	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.EsitoScaricaDocumentoDTO scaricaDocumento(
			java.lang.Long idUtente, java.lang.String identitaDigitale,
			java.lang.Long idDocumentoIndex)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);
		logger.info("\n\n\nscaricaDocumento for idDocumentoIndex:"+idDocumentoIndex);
		EsitoScaricaDocumentoDTO esitoScaricaDocumentoDTO = new EsitoScaricaDocumentoDTO();
		esitoScaricaDocumentoDTO.setEsito(Boolean.FALSE);

		try {

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null
			PbandiTDocumentoIndexVO documentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
			documentoIndexVO=genericDAO.findSingleWhere(documentoIndexVO);
			it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO doc =
				getDocumentoManager()
				.getDocumento(documentoIndexVO.getUuidNodo());

			esitoScaricaDocumentoDTO.setBytesDocumento(doc.getBytesDocumento());

			logger.info("documentoIndexVO.getMessageDigest(): "+documentoIndexVO.getMessageDigest());
			if(documentoIndexVO.getMessageDigest()==null){
				BigDecimal idTipoDocumentoIndex = documentoIndexVO.getIdTipoDocumentoIndex();
				PbandiCTipoDocumentoIndexVO pbandiCTipoDocumentoIndexVO=new PbandiCTipoDocumentoIndexVO(idTipoDocumentoIndex);
				pbandiCTipoDocumentoIndexVO = genericDAO.findSingleWhere(pbandiCTipoDocumentoIndexVO);
				logger.info("pbandiCTipoDocumentoIndexVO.getFlagFirmabile(): "+pbandiCTipoDocumentoIndexVO.getFlagFirmabile());
				if(pbandiCTipoDocumentoIndexVO.getFlagFirmabile()!=null && 
						pbandiCTipoDocumentoIndexVO.getFlagFirmabile().equalsIgnoreCase("S")){
					String shaHex = DigestUtils.shaHex(doc.getBytesDocumento());
					documentoIndexVO.setMessageDigest(shaHex);
					logger.info("*** UPDATING DOC INDEX with MESSGE DIGEST "+shaHex);
					genericDAO.update(documentoIndexVO);
				}
			}

			// 25 febb 16: added buz logic for DEMAT II : creating message digest if null

			esitoScaricaDocumentoDTO.setNomeFile(documentoIndexVO.getNomeFile());
			esitoScaricaDocumentoDTO.setParams(new String[] { doc.getMimeType() });
			esitoScaricaDocumentoDTO.setEsito(Boolean.TRUE);
		} catch (Exception e) {
			String msg = "Impossibile recuperare il documento: "
				+ e.getMessage();
			logger.error(msg, e);
			throw new GestioneDocumentazioneException(msg);
		} 

		return esitoScaricaDocumentoDTO;
	}

	public TipoDocumentoIndexDTO[] findTipiDocumentoIndex(Long idUtente,
			String identitaDigitale, FiltroTipoDocumentoIndexDTO filtro)
	throws CSIException, SystemException, UnrecoverableException,
	GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
		TipoDocumentoIndexAnagraficaEnteVO filter = new TipoDocumentoIndexAnagraficaEnteVO();
		filter.setIdSoggetto(BigDecimal.valueOf(filtro.getIdSoggetto()));
		filter.setDescBreveTipoAnagrafica(filtro.getCodiceRuolo());
		filter.setAscendentOrder("descTipoDocIndex");
		if (filtro.getIdProgetto() != null) {
			/*
			 * Ricerco il progressivo bando linea legato al progetto
			 */
			BandoLineaProgettoVO blpVO = new BandoLineaProgettoVO();
			blpVO.setIdProgetto(BigDecimal.valueOf(filtro.getIdProgetto()));
			blpVO = genericDAO.findSingleWhere(blpVO);
			filter.setProgrBandoLineaIntervento(blpVO.getProgrBandoLineaIntervento());
		}

		TipoDocumentoIndexUtenteVO groupByVO = new TipoDocumentoIndexUtenteVO();

		List<TipoDocumentoIndexUtenteVO> listTipoDocumentoIndex = genericDAO
		.findListWhereGroupBy(
				new FilterCondition<TipoDocumentoIndexAnagraficaEnteVO>(
						filter), groupByVO);

		// Cerco i tipi documento con PBANDI_C_TIPO_DOCUMENTO_INDEX.FLAG_UPLOADABLE = 'S'
		TipoDocumentoIndexUtente2VO vo = new TipoDocumentoIndexUtente2VO();
		vo.setDescBreveTipoAnagrafica(filtro.getCodiceRuolo());
		vo.setIdSoggetto(BigDecimal.valueOf(filtro.getIdSoggetto()));
		List<TipoDocumentoIndexUtente2VO> list2 = genericDAO.findListWhere(vo);
		for (TipoDocumentoIndexUtente2VO x : list2) {
			logger.info("---------------------");
			logger.info(x.getDescBreveTipoAnagrafica());
			logger.info(x.getDescBreveTipoDocIndex());
			logger.info(x.getDescTipoDocIndex());
			logger.info(x.getIdSoggetto().toString());
			logger.info(x.getIdTipoDocumentoIndex().toString());

			// Li aggiungo a quelli trovati prima.
			if (!this.giaPresente(x, listTipoDocumentoIndex)) {
				TipoDocumentoIndexUtenteVO newVO = new TipoDocumentoIndexUtenteVO();
				newVO.setDescTipoDocIndex(x.getDescTipoDocIndex());
				newVO.setDescBreveTipoDocIndex(x.getDescBreveTipoDocIndex());
				newVO.setIdTipoDocumentoIndex(x.getIdTipoDocumentoIndex());
				listTipoDocumentoIndex.add(newVO);
			}
		}

		// Ordino l'array.
		Collections.sort(listTipoDocumentoIndex, new Comparator() {
			public int compare(Object o1, Object o2) {
				TipoDocumentoIndexUtenteVO b1 = (TipoDocumentoIndexUtenteVO) o1;
				TipoDocumentoIndexUtenteVO b2 = (TipoDocumentoIndexUtenteVO) o2;
				return b1.getDescBreveTipoDocIndex().compareTo(b2.getDescBreveTipoDocIndex());
			}
		});

		return beanUtil.transform(listTipoDocumentoIndex,
				TipoDocumentoIndexDTO.class);

	}
	
	private boolean giaPresente(TipoDocumentoIndexUtente2VO item, List<TipoDocumentoIndexUtenteVO> lista) {
		if (item == null || lista == null)
			return true;
		for(TipoDocumentoIndexUtenteVO vo : lista) {
			if (vo.getIdTipoDocumentoIndex().intValue() == item.getIdTipoDocumentoIndex().intValue())
				return true;
		}
		return false;
	}

	public CodiceDescrizioneDTO[] findBandiDaProgetti(Long idUtente,
			String identitaDigitale, Long[] idProgetti) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale);
		if (idProgetti == null || idProgetti.length == 0) {
			return new CodiceDescrizioneDTO[0];
		}
		List<ProgettoBandoLineaLightVO> progetti = getBeanUtil().beanify(
				Arrays.asList(idProgetti), "idProgetto",
				ProgettoBandoLineaLightVO.class);
		progetti.get(0).setOrderPropertyNamesList(new ArrayList<String>() {
			{
				add("descrizioneBando");
			}
		});
		List<ProgettoBandoLineaLightFilterByBandoVO> bandi = genericDAO
		.findListWhereDistinct(Condition.filterBy(progetti),
				ProgettoBandoLineaLightFilterByBandoVO.class);
		return beanUtil.transform(bandi, CodiceDescrizioneDTO.class,
				new HashMap<String, String>() {
			{
				put("idBandoLinea", "codice");
				put("descrizioneBando", "descrizione");
			}
		});

	}

	public EsitoScaricaDocumentoDTO getFileSigned(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex)
	throws CSIException, SystemException, UnrecoverableException,
	GestioneDocumentazioneException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);

		EsitoScaricaDocumentoDTO esitoScaricaDocumentoDTO = new EsitoScaricaDocumentoDTO();
		esitoScaricaDocumentoDTO.setEsito(Boolean.FALSE);

		try {

			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
			pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			pbandiTDocumentoIndexVO=genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
			logger.info("\n\n\n\n\n\ncalling indexDAO.getP7m for pbandiTDocumentoIndexVO.getUuidNodo():"+pbandiTDocumentoIndexVO.getUuidNodo()+" ,fileName: "+pbandiTDocumentoIndexVO.getNomeFile());
			byte[] bytes_p7m = indexDAO.getP7m(pbandiTDocumentoIndexVO.getUuidNodo());
			logger.info("bytes_p7m : "+bytes_p7m );

			esitoScaricaDocumentoDTO.setBytesDocumento(bytes_p7m);
			esitoScaricaDocumentoDTO.setNomeFile(pbandiTDocumentoIndexVO.getNomeFile()+".p7m");

			esitoScaricaDocumentoDTO.setEsito(Boolean.TRUE);
			esitoScaricaDocumentoDTO.setParams(new String[] { "application/pdf" });
		} catch (Exception e) {
			String msg = "Impossibile recuperare il documento p7m per idDocIndex: "+idDocumentoIndex+" : "+ e.getMessage();
			logger.error(msg, e);
			throw new GestioneDocumentazioneException(msg);
		} 

		return esitoScaricaDocumentoDTO;
	}

	public EsitoScaricaDocumentoDTO getFileTimestamped(Long idUtente,
			String identitaDigitale, Long idDocumentoIndex)
	throws CSIException, SystemException, UnrecoverableException,
	GestioneDocumentazioneException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idDocumentoIndex);

		EsitoScaricaDocumentoDTO esitoScaricaDocumentoDTO = new EsitoScaricaDocumentoDTO();
		esitoScaricaDocumentoDTO.setEsito(Boolean.FALSE);

		try {

			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
			pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			pbandiTDocumentoIndexVO=genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
			logger.info("\n\n\n\n\n\ncalling indexDAO.getTsd for pbandiTDocumentoIndexVO.getUuidNodo():"+pbandiTDocumentoIndexVO.getUuidNodo()+" ,fileName: "+pbandiTDocumentoIndexVO.getNomeFile());
			byte[] bytes_tsd = indexDAO.getTsd(pbandiTDocumentoIndexVO.getUuidNodo());
			logger.info("bytes_tsd : "+bytes_tsd );
			esitoScaricaDocumentoDTO.setBytesDocumento(bytes_tsd);
			esitoScaricaDocumentoDTO.setNomeFile(pbandiTDocumentoIndexVO.getNomeFile()+".p7m.tsd");

			esitoScaricaDocumentoDTO.setEsito(Boolean.TRUE);
			esitoScaricaDocumentoDTO.setParams(new String[] { "application/pdf" });
		} catch (Exception e) {
			String msg = "Impossibile recuperare il documento tsd per idDocIndex: "+idDocumentoIndex+" : "+ e.getMessage();
			logger.error(msg, e);
			throw new GestioneDocumentazioneException(msg);
		} 

		return esitoScaricaDocumentoDTO;
	}


	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] getFilesAssociated(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String codTipoDocIndex,
			java.lang.Long idDocumentoIndex

	)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException
	,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idDocumentoIndex);
		logger.info("\n\n#############\ngetFilesAssociated ,codTipoDocIndex:"+codTipoDocIndex+", idDocumentoIndex "+idDocumentoIndex);
		List <DocumentoDTO> filez=new ArrayList<DocumentoDTO>();
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
		pbandiTDocumentoIndexVO=genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		logger.info("idTarget ()---> "+pbandiTDocumentoIndexVO.getIdTarget()
				+" ,idEntita ---> "+pbandiTDocumentoIndexVO.getIdEntita());
		BigDecimal idEntita=pbandiTDocumentoIndexVO.getIdEntita();
		BigDecimal idTarget=pbandiTDocumentoIndexVO.getIdTarget();
		if(codTipoDocIndex.equalsIgnoreCase("PR")){
			logger.info("proposta rimod, modifico idEntita del conto economico con quella della t_progetto, e idTarget (idContoEconomico) con idProgetto");
			idEntita = documentoManager.getIdEntita(PbandiTContoEconomicoVO.class);
			//idTarget = pbandiTDocumentoIndexVO.getIdProgetto();
		}

		List<ArchivioFileVO> list = pbandiArchivioFileDAOImpl.getFiles(idTarget,idEntita );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createDocumentoDTO(archivioFile));
		}		
		logger.info("found file associated to tipoDoc "+codTipoDocIndex+" with id "+idDocumentoIndex+" target:"+pbandiTDocumentoIndexVO.getIdTarget()+": "+list.size());

		return filez.toArray(new DocumentoDTO [0] );

	}
	/*
	// Restituisce le integrazioni con relativi allegati della DS associata al documento in input.
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] getFilesAssociatedIntegrazioniDS(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocumentoIndex
	)
			throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException,
			it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException
	{
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale,idDocumentoIndex);
		logger.info("\n\n#############\ngetFilesAssociatedIntegrazioniDS(): idDocumentoIndex "+idDocumentoIndex);

		List <DocumentoDTO> filez = new ArrayList<DocumentoDTO>();
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
		pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		BigDecimal idDichiarazioneSpesa = pbandiTDocumentoIndexVO.getIdTarget();
		logger.info("getFilesAssociatedIntegrazioniDS(): idDichiarazioneSpesa = "+idDichiarazioneSpesa);

		List<ArchivioFileVO> list = pbandiArchivioFileDAOImpl.getFiles(idTarget,idEntita );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createDocumentoDTO(archivioFile));
		}		
		logger.info("found file associated to tipoDoc "+codTipoDocIndex+" with id "+idDocumentoIndex+" target:"+pbandiTDocumentoIndexVO.getIdTarget()+": "+list.size());

		return filez.toArray(new DocumentoDTO [0] );

	}
	 */
	private DocumentoDTO createDocumentoDTO(ArchivioFileVO file) {
		DocumentoDTO documentoDTO=new DocumentoDTO();
		documentoDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		documentoDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		documentoDTO.setNomeFile(file.getNomeFile());
		documentoDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		return documentoDTO;
	}

	public String getCodStatoDoc(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);
		logger.info("\n\n#############\ngetCodStatoDoc for idDocumentoIndex:"+idDocumentoIndex);
		String codStatoDoc="";

		codStatoDoc = ricercaDocumentiDAOImpl.getCodStatoDoc(idDocumentoIndex);
		logger.info("returning codStatoDoc ---> "+codStatoDoc+" for idDocumentoIndex:"+idDocumentoIndex);
		return codStatoDoc;
	}

	public EsitoScaricaDocumentoDTO scaricaZipDichiarazioneSpesa(Long idUtente,
			String identitaDigitale, FiltroDownloadZip filtroDownload)
	throws CSIException, SystemException, UnrecoverableException,
	GestioneDocumentazioneException {

		String[] nameParameter = { "idUtente", "identitaDigitale","filtroDownload" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, filtroDownload);

		//VERIFICARE LO SPAZIO TOTALE DEI FILE DA SCRICARE
		//Verificare le dimensioni dei file sulla base delle tipologie selezionate
		EsitoScaricaDocumentoDTO esito = new EsitoScaricaDocumentoDTO();

		try{
			if(filtroDownload.getIdDichiarazioneSpesa() != null){

				HashMap<String, ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>> allegatiDsMap = new HashMap<String, ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>>();

				if(filtroDownload.getFlagDicSpesaAllegati() != null && filtroDownload.getFlagDicSpesaAllegati()){
					ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> fileDichSpesaAllegati = caricaFileDichSpesaAllegati(filtroDownload.getIdProgetto(), filtroDownload.getIdDichiarazioneSpesa());
					allegatiDsMap.put("dic_spesa_e_allegati", fileDichSpesaAllegati);

				}
				if(filtroDownload.getFlagRendicontati() != null && filtroDownload.getFlagIntegrazione()){
					ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> fileIntegrazioneAllegati = caricaFileIntegrazioneSpesa(filtroDownload.getIdProgetto(), filtroDownload.getIdDichiarazioneSpesa());
					allegatiDsMap.put("documenti_rendicontati", fileIntegrazioneAllegati);
				}
				if(filtroDownload.getFlagPagamenti() != null && filtroDownload.getFlagPagamenti()){
					ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> filePagamento = caricaFilePagamento(filtroDownload.getIdProgetto(), filtroDownload.getIdDichiarazioneSpesa());
					allegatiDsMap.put("pagamenti_dei_documenti", filePagamento);
				}
				if(filtroDownload.getFlagIntegrazione() != null && filtroDownload.getFlagRendicontati()){
					ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> fileRendicontati = caricaDocumentiRendicontati(filtroDownload.getIdProgetto(), filtroDownload.getIdDichiarazioneSpesa());
					allegatiDsMap.put("integrazione_dic_spesa", fileRendicontati);
				}

				HashSet<String> entries = new HashSet<String>();
				StringBuilder sbFiles = new StringBuilder();
				
				try {

					if(allegatiDsMap != null && !allegatiDsMap.isEmpty()){

						String tempDir = (String) System.getProperty("java.io.tmpdir");
						File directory = new File(tempDir);
						File outputZipFile = File.createTempFile("allegati",".zip", directory);
						FileOutputStream fos = new FileOutputStream(outputZipFile);
						ZipOutputStream zos = new ZipOutputStream(fos);

						if(logger.getLogger().isDebugEnabled())
							logger.debug("Creata struttura zip file");
						
						try {
							logger.info("Generating zip file!");
							//suddivido ogni tipologia di allegati in differenti cartelle separate
							Set<String> dirNames = allegatiDsMap.keySet();
							for(String dirName : dirNames){
								//Riferimento per questo algoritmo su https://www.giannistsakiris.com/2015/12/24/java-adding-directories-in-a-zipoutputstream/
								String completeDirName = dirName.concat("/");
								
								sbFiles.append(completeDirName);
								
								ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> docs = allegatiDsMap.get(dirName);
								
								if(docs != null && !docs.isEmpty()){
									logger.info("Inizio creazione archivio zip.");
									
									//creo prima la cartella in cui inserire tutti i file per tipologia
									zos.putNextEntry(new ZipEntry(completeDirName));

									for(it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO doc : docs){

										String zipNameFile = doc.getNomeFile();
										Integer z = 1;
										//controllo nomi file per non avere doppioni
										while(entries.contains(zipNameFile)){
											zipNameFile = NumberUtil.getStringValue(z) + "_" + doc.getNomeFile();
											z++;
										}

										ZipEntry ze = new ZipEntry(completeDirName + zipNameFile);
										zos.putNextEntry(ze);
										zos.write(doc.getBytesDocumento(), 0, doc.getBytesDocumento().length);
										zos.closeEntry();
										entries.add(zipNameFile);
										sbFiles.append("\n\t" + zipNameFile);
									}
									
								}else{
									logger.info("Nessun file da archviare");
								}
								sbFiles.append("\n\n");
							}
							
							if(entries.size() > 0){
								zos.putNextEntry(new ZipEntry("lista_documenti.txt"));
								zos.write(sbFiles.toString().getBytes(), 0 , sbFiles.toString().getBytes().length);
								zos.closeEntry();
								zos.flush();
								zos.close();
								esito.setEsito(Boolean.TRUE);
								esito.setNomeFile("AllegatiDichiarazioneSpesa_" + filtroDownload.getIdDichiarazioneSpesa() + ".zip");
								esito.setBytesDocumento(FileUtils.readFileToByteArray(outputZipFile));
																		
								logger.info("Fine creazione archivio file zip");
							}
							
						} catch (Exception e) {
							logger.error("Errore in fase di creazione dello zip", e);
						}finally{
							try {
								fos.flush();
								fos.close();
							} catch (Exception e) {
								logger.error("Errore nella chiusura del FileOutputStream.", e);
							}
						}
					}

				} catch(Exception ex){
					throw new GestioneDocumentazioneException("Errore durante la creazione del file zip.", ex);
				}
			}

		}catch(Exception ex){
			throw new GestioneDocumentazioneException("Errore durante l'esecuzione del metodo scaricaZipDichiarazioneSpesa", ex);
		}
		
		return esito;

	}
	
	private ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> caricaDocumentiRendicontati(
			Long idProgetto, Long idDocumentoSpesa) throws Exception{		
		try{
			
			ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> documentiDTO = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>();
			AllegatiDocumentiRendicontatiVO filtroFile = new AllegatiDocumentiRendicontatiVO();
			filtroFile.setIdProgetto(new BigDecimal(idProgetto));
			filtroFile.setIdDichiarazioneSpesa(new BigDecimal(idDocumentoSpesa));
			
			List<AllegatiDocumentiRendicontatiVO> allegatiIntegrazione = genericDAO.findListWhere(filtroFile);
			
			if(allegatiIntegrazione != null && !allegatiIntegrazione.isEmpty()){
				for(AllegatiDocumentiRendicontatiVO allegato : allegatiIntegrazione){
					it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO documento = documentoManager.getDocumento(allegato.getUuidNodo());
					documento.setNomeFile(allegato.getNomeFile());
					logger.info("DOCUMENTO Ottenuto :: " + documento.getNomeFile());
					documentiDTO.add(documento);
				}
			}
			
			return documentiDTO;
			
		}catch(Exception ex){
			throw new GestioneDocumentazioneException("Errore durante l'esecuzione del metodo caricaDocumentiRendicontati:" ,ex);
		}
	}

	private ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> caricaFilePagamento(
			Long idProgetto, Long idDocumentoSpesa) throws Exception{		
		try{
			
			ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> documentiDTO = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>();
			AllegatiPagamentoDsVO filtroFile = new AllegatiPagamentoDsVO();
			filtroFile.setIdProgetto(new BigDecimal(idProgetto));
			filtroFile.setIdDichiarazioneSpesa(new BigDecimal(idDocumentoSpesa));
			
			List<AllegatiPagamentoDsVO> allegatiIntegrazione = genericDAO.findListWhere(filtroFile);
			
			if(allegatiIntegrazione != null && !allegatiIntegrazione.isEmpty()){
				for(AllegatiPagamentoDsVO allegato : allegatiIntegrazione){
					it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO documento = documentoManager.getDocumento(allegato.getUuidNodo());
					documento.setNomeFile(allegato.getNomeFile());
					logger.info("DOCUMENTO Ottenuto :: " + documento.getNomeFile());
					documentiDTO.add(documento);
				}
			}
			
			return documentiDTO;
			
		}catch(Exception ex){
			throw new GestioneDocumentazioneException("Errore durante l'esecuzione del metodo caricaFilePagamento:" ,ex);
		}
	}

	private ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> caricaFileIntegrazioneSpesa(
			Long idProgetto, Long idDocumentoSpesa) throws Exception {
		try{
			
			ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> documentiDTO = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>();
			AllegatiIntegrazioneSpesaVO fileIntegrazione = new AllegatiIntegrazioneSpesaVO();
			fileIntegrazione.setIdProgetto(new BigDecimal(idProgetto));
			fileIntegrazione.setIdDichiarazioneSpesa(new BigDecimal(idDocumentoSpesa));
			
			List<AllegatiIntegrazioneSpesaVO> allegatiIntegrazione = genericDAO.findListWhere(fileIntegrazione);
			
			if(allegatiIntegrazione != null && !allegatiIntegrazione.isEmpty()){
				for(AllegatiIntegrazioneSpesaVO allegato : allegatiIntegrazione){
					it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO documento = documentoManager.getDocumento(allegato.getUuidNodo());
					documento.setNomeFile(allegato.getNomeFile());
					logger.info("DOCUMENTO Ottenuto :: " + documento.getNomeFile());
					documentiDTO.add(documento);
				}
			}
			
			return documentiDTO;
			
		}catch(Exception ex){
			throw new GestioneDocumentazioneException("Errore durante l'esecuzione del metodo caricaFileIntegrazioneSpesa:" ,ex);
		}
	}

	private ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> caricaFileDichSpesaAllegati(Long idProgetto, Long idDichSpesa) throws Exception{

		try{
			ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO> documentiDTO = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO>();

			AllegatiDocumentoSpesaVO filtroAllegati = new AllegatiDocumentoSpesaVO();
			filtroAllegati.setIdDichiarazioneSpesa(new BigDecimal(idDichSpesa));
			filtroAllegati.setIdProgetto(new BigDecimal(idProgetto));
			
			List<AllegatiDocumentoSpesaVO> allegati = genericDAO.findListWhere(filtroAllegati);			
			
			if(allegati != null && !allegati.isEmpty()){
				for(AllegatiDocumentoSpesaVO allegato : allegati){
					it.csi.pbandi.pbweb.pbandisrv.dto.manager.DocumentoDTO documento = documentoManager.getDocumento(allegato.getUuidNodo());
					documento.setNomeFile(allegato.getNomeFile());
					logger.info("DOCUMENTO Ottenuto :: " + documento.getNomeFile());
					documentiDTO.add(documento);
				}
			}
						
			return documentiDTO;
			
		}catch(Exception ex){
			throw new GestioneDocumentazioneException("Errore durante l'esecuzione del metodo caricaFileDichSpesaAllegati:", ex);
		}
		
	}

	public DimensioneFileZipDTO getDimensioniFileZip(Long idUtente,
			String identitaDigitale, Long idProgetto, Long idDichiarazioneSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneDocumentazioneException {
		
		try{
			String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto", "idDichiarazioneSpesa" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto, idDichiarazioneSpesa);
			
			DocAllegatiSpazioVO docSpazioAllegati = new DocAllegatiSpazioVO();
			
			BigDecimal maxZipSize = configurationManager.getConfiguration().getDownloadSizeLimit();
			
			docSpazioAllegati.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			docSpazioAllegati.setIdDichiarazioneSpesa(beanUtil.transform(idDichiarazioneSpesa, BigDecimal.class));

			docSpazioAllegati = genericDAO.findSingleOrNoneWhere(docSpazioAllegati);
			
			DimensioneFileZipDTO dimFileZipDTO = beanUtil.transform(docSpazioAllegati, DimensioneFileZipDTO.class);
			
			if(dimFileZipDTO == null)
				dimFileZipDTO = new DimensioneFileZipDTO();
			
			dimFileZipDTO.setDownloadSizeLimit(maxZipSize.longValue());
			
			return dimFileZipDTO;
			
		}catch(Exception ex){
			logger.error("Errore durante la chiamata del metodo getDimansioniFileZip:", ex);
			throw new GestioneDocumentazioneException("Errore durante la chiamata del metodo getDimensioniFileZip:", ex);
		}

	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] getFilesAssociatedVerbaleChecklist(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String codTipoDocIndex,
			java.lang.Long idChecklist

	)
	throws it.csi.csi.wrapper.CSIException,
	it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException
	,
	it.csi.pbandi.pbweb.pbandisrv.exception.gestionedocumentazione.GestioneDocumentazioneException

	{
		String[] nameParameter = { "idUtente", "identitaDigitale","codTipoDocIndex","idDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, codTipoDocIndex,idChecklist);
		logger.info("\n\n#############\ngetFilesAssociatedVerbaleChecklist ,codTipoDocIndex:"+codTipoDocIndex+", idDocumentoIndex "+idChecklist);
		List <DocumentoDTO> filez=new ArrayList<DocumentoDTO>();
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idChecklist));
		pbandiTDocumentoIndexVO=genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		logger.info("idTarget ()---> "+pbandiTDocumentoIndexVO.getIdTarget()
				+" ,idEntita ---> "+pbandiTDocumentoIndexVO.getIdEntita());
		BigDecimal idEntita=pbandiTDocumentoIndexVO.getIdEntita();
		BigDecimal idTarget=pbandiTDocumentoIndexVO.getIdTarget();
		if(codTipoDocIndex.equalsIgnoreCase("VCV")){
			logger.info("proposta rimod, modifico idEntita del conto economico con quella della t_progetto, e idTarget (idContoEconomico) con idProgetto");
			idEntita = documentoManager.getIdEntita(PbandiTContoEconomicoVO.class);
			//idTarget = pbandiTDocumentoIndexVO.getIdProgetto();
		}

		List<ArchivioFileVO> list = pbandiArchivioFileDAOImpl.getFiles(idTarget,idEntita );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createDocumentoDTO(archivioFile));
		}		
		logger.info("found file associated to tipoDoc "+codTipoDocIndex+" with id "+idChecklist+" target:"+pbandiTDocumentoIndexVO.getIdTarget()+": "+list.size());

		return filez.toArray(new DocumentoDTO [0] );

	}
}
