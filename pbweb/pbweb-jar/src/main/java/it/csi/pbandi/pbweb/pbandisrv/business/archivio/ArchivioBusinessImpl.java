package it.csi.pbandi.pbweb.pbandisrv.business.archivio;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.AppaltoInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.ComFineProgInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.DichiarazioneInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.DocDiSpesaInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FornitoreInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.PagamentoInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.PropostaRimodInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.QualificaFornitoreInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.RichiestaErogazioneInfoDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.archivio.ArchivioException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedComFineProgVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedDocSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedIntegrazioneDsVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedPropostaRimodVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedQualificaFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedRichiestaErogVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTIntegrazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.archivio.ArchivioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.util.Constants;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

 
public class ArchivioBusinessImpl extends BusinessImpl implements ArchivioSrv {
	
	private Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);

	/*
	private ConfigurationManager configurationManager;
	
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
	
	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}
	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}
	*/
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;

	public PbandiArchivioFileDAOImpl getPbandiArchivioFileDAOImpl() {
		return pbandiArchivioFileDAOImpl;
	}

	public void setPbandiArchivioFileDAOImpl(PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl) {
		this.pbandiArchivioFileDAOImpl = pbandiArchivioFileDAOImpl;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}
	
	
	public FileInfoDTO getFileInfo(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex, Long idFolderSelected) throws CSIException, SystemException,
			UnrecoverableException, ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex);
		FileInfoDTO fileInfoDTO =null;
		
		logger.info("\n\n\n\n\ngetFileInfo idDocumentoIndex: "+idDocumentoIndex+"; idFolderSelected: "+idFolderSelected);
		
		long idEntitaProgetto= documentoManager.getIdEntita(PbandiTProgettoVO.class).longValue();
		long idEntitaDocSpesa = documentoManager.getIdEntita(PbandiTDocumentoDiSpesaVO.class).longValue();
		long idEntitaPagamento= documentoManager.getIdEntita(PbandiTPagamentoVO.class).longValue();
		long idEntitaFornitore= documentoManager.getIdEntita(PbandiTFornitoreVO.class).longValue();
		long idEntitaQualifica= documentoManager.getIdEntita(PbandiRFornitoreQualificaVO.class).longValue();
		
		long idEntitaComFineProg= documentoManager.getIdEntita(PbandiTComunicazFineProgVO.class).longValue();
		long idEntitaContoE= documentoManager.getIdEntita(PbandiTContoEconomicoVO.class).longValue();
		long idEntitaDich= documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class).longValue();
		long idEntitaRicErog= documentoManager.getIdEntita(PbandiTRichiestaErogazioneVO.class).longValue();
		long idEntitaAppalto= documentoManager.getIdEntita(PbandiTAppaltoVO.class).longValue();
		long idIntegrazioneDs= documentoManager.getIdEntita(PbandiTIntegrazioneSpesaVO.class).longValue();
		
		logger.info("after find entities "+idIntegrazioneDs);
		
		PbandiTFileVO fileVO=new PbandiTFileVO();
		fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
		fileVO.setIdFolder(BigDecimal.valueOf(idFolderSelected));
		logger.info("searching t_file");
		fileVO=genericDAO.findSingleWhere(fileVO);
		
		PbandiTFileEntitaVO filterEntita=new PbandiTFileEntitaVO();
		filterEntita.setIdFile(fileVO.getIdFile());
		logger.info("searching r_file_entities");
		List<PbandiTFileEntitaVO> rfileEntitas = genericDAO.findListWhere(filterEntita);
		logger.info("Entita associated found: "+rfileEntitas.size());
		
		ArchivioFileVO archivioFileVO=new ArchivioFileVO();
		archivioFileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
		archivioFileVO.setIdFolder(BigDecimal.valueOf(idFolderSelected));
		archivioFileVO=genericDAO.findSingleWhere(archivioFileVO);
		fileInfoDTO = beanUtil.transform(archivioFileVO, FileInfoDTO.class);	
		
		List<DocDiSpesaInfoDTO> listDocDiSpesaInfo=new ArrayList<DocDiSpesaInfoDTO>();
		List<FornitoreInfoDTO> listFornitoreInfo=new ArrayList<FornitoreInfoDTO>();
		List<PagamentoInfoDTO> listPagamentoInfo=new ArrayList<PagamentoInfoDTO>();
		List<QualificaFornitoreInfoDTO> listQualificaFornitoreInfo=new ArrayList<QualificaFornitoreInfoDTO>();
		
		List<ComFineProgInfoDTO> listComFineProgInfo=new ArrayList<ComFineProgInfoDTO>();
		List<PropostaRimodInfoDTO> listPropRimInfo=new ArrayList<PropostaRimodInfoDTO>();
		List<DichiarazioneInfoDTO> listDichInfo=new ArrayList<DichiarazioneInfoDTO>();
		List<RichiestaErogazioneInfoDTO> listRichErogInfo=new ArrayList<RichiestaErogazioneInfoDTO>();
		List<AppaltoInfoDTO> listAppaltoInfo=new ArrayList<AppaltoInfoDTO>();
		List<DichiarazioneInfoDTO> listIntegrazioniDsInfo = new ArrayList<DichiarazioneInfoDTO>();	// Jira PBANDI-2802.
		
		Set<BigDecimal> idsDocSpesa=new HashSet<BigDecimal>();
		Set<BigDecimal> idsPagamento=new HashSet<BigDecimal>();
		Set<BigDecimal> idsFornitore=new HashSet<BigDecimal>();
		Set<BigDecimal> idsQualifica=new HashSet<BigDecimal>();
		
		Set<BigDecimal> idsComFineProg=new HashSet<BigDecimal>();
		Set<BigDecimal> idsContoE=new HashSet<BigDecimal>();
		Set<BigDecimal> idsDich=new HashSet<BigDecimal>();
		Set<BigDecimal> idsRichErogQualifica=new HashSet<BigDecimal>();
		Set<BigDecimal> idsAppalto=new HashSet<BigDecimal>();
		
		for (PbandiTFileEntitaVO rfileEntita : rfileEntitas) {
			if(rfileEntita.getIdEntita().longValue()==idEntitaDocSpesa){
				List <InfoFileAssociatedDocSpesaVO> fileAssociated=new ArrayList<InfoFileAssociatedDocSpesaVO>();
				InfoFileAssociatedDocSpesaVO filter=new InfoFileAssociatedDocSpesaVO();
				filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
				fileAssociated = genericDAO.findListWhere(filter);
				logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita docSpesa:"+idEntitaDocSpesa);
				for (InfoFileAssociatedDocSpesaVO infoFileAssociatedDocSpesaVO : fileAssociated) {
					if(!idsDocSpesa.contains(infoFileAssociatedDocSpesaVO.getIdDocumentoDiSpesa())){
						idsDocSpesa.add(infoFileAssociatedDocSpesaVO.getIdDocumentoDiSpesa());
						DocDiSpesaInfoDTO infoDocSpesaDTO = beanUtil.transform(infoFileAssociatedDocSpesaVO, DocDiSpesaInfoDTO.class);	
						logger.info("entita doc spesa infoDocSpesaDTO.getIdDocumentoDiSpesa(): "+infoDocSpesaDTO.getIdDocumentoDiSpesa());
						if(infoDocSpesaDTO.getDtEmissioneDocumento()!=null)
							infoDocSpesaDTO.setDtEmissioneDocumentoFormattata(DateUtil.getDate(infoDocSpesaDTO.getDtEmissioneDocumento()));
						listDocDiSpesaInfo.add(infoDocSpesaDTO);
					}
				}		
			} else if(rfileEntita.getIdEntita().longValue()==idEntitaPagamento){
				logger.info("entita pagamento");
				List <InfoFileAssociatedPagamentoVO> fileAssociated= new ArrayList<InfoFileAssociatedPagamentoVO>();
				InfoFileAssociatedPagamentoVO filter= new InfoFileAssociatedPagamentoVO();
				filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
				fileAssociated = genericDAO.findListWhere(filter);
				logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita pagamento:"+idEntitaPagamento);
				for (InfoFileAssociatedPagamentoVO infoFileAssociatedPagamentoVO : fileAssociated) {
					if(!idsPagamento.contains(infoFileAssociatedPagamentoVO.getIdPagamento())){
						idsPagamento.add(infoFileAssociatedPagamentoVO.getIdPagamento());
						PagamentoInfoDTO infoPagamentoDTO = beanUtil.transform(infoFileAssociatedPagamentoVO, PagamentoInfoDTO.class);
						infoPagamentoDTO.setDtPagamentoFormattata(DateUtil.getDate(infoPagamentoDTO.getDtPagamento()))  ;
						if(infoFileAssociatedPagamentoVO.getDtEmissioneDocumento()!=null)
							infoPagamentoDTO.setDtEmissioneDocumentoFormattata(DateUtil.getDate(infoFileAssociatedPagamentoVO.getDtEmissioneDocumento()));
						logger.info("entita pagamento infoPagamentoDTO.getDescModalitaPagamento(): "+infoPagamentoDTO.getDescModalitaPagamento());
						listPagamentoInfo.add(infoPagamentoDTO);
					}
				}
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaFornitore) {
				List <InfoFileAssociatedFornitoreVO> fileAssociated= new ArrayList<InfoFileAssociatedFornitoreVO>();
				InfoFileAssociatedFornitoreVO filter= new InfoFileAssociatedFornitoreVO();
				filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
				fileAssociated = genericDAO.findListWhere(filter);
				logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita fornitore:"+idEntitaFornitore);
				for (InfoFileAssociatedFornitoreVO infoFileAssociatedFornitoreVO : fileAssociated) {
					if(!idsFornitore.contains(infoFileAssociatedFornitoreVO.getIdFornitore())){
						idsFornitore.add(infoFileAssociatedFornitoreVO.getIdFornitore());
						FornitoreInfoDTO infoFornitoreDTO = beanUtil.transform(infoFileAssociatedFornitoreVO, FornitoreInfoDTO.class);
						logger.info("entita fornitore "+infoFornitoreDTO.getNomeFornitore());
						listFornitoreInfo.add(infoFornitoreDTO);
					}
				}
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaQualifica) {
				List <InfoFileAssociatedQualificaFornitoreVO> fileAssociated= new ArrayList<InfoFileAssociatedQualificaFornitoreVO>();
				InfoFileAssociatedQualificaFornitoreVO filter= new InfoFileAssociatedQualificaFornitoreVO();
				filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
				fileAssociated = genericDAO.findListWhere(filter);
				logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita qualifica:"+idEntitaQualifica);
				for (InfoFileAssociatedQualificaFornitoreVO infoFileAssociatedQualificaFornitoreVO : fileAssociated) {
						if(!idsQualifica.contains(infoFileAssociatedQualificaFornitoreVO.getProgrFornitoreQualifica())){
							idsQualifica.add(infoFileAssociatedQualificaFornitoreVO.getProgrFornitoreQualifica());
							QualificaFornitoreInfoDTO infoQualificaDTO = beanUtil.transform(infoFileAssociatedQualificaFornitoreVO, QualificaFornitoreInfoDTO.class);
							logger.info("entita qualifica infoQualificaDTO.getDescQualifica() "+infoQualificaDTO.getDescQualifica());
							listQualificaFornitoreInfo.add(infoQualificaDTO);
						}
				}
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaProgetto) {
				BigDecimal idProgetto=rfileEntita.getIdTarget();
				BandoLineaProgettoVO bandoLineaProgettoVO=new BandoLineaProgettoVO();
				bandoLineaProgettoVO.setIdProgetto(idProgetto);
				bandoLineaProgettoVO=genericDAO.findSingleWhere(bandoLineaProgettoVO);
				logger.info("entita progetto pbandiTProgettoVO.getCodiceVisualizzato() "+bandoLineaProgettoVO.getCodiceVisualizzato()
						+" bandoLineaProgettoVO.getNomeBandoLinea(): "+bandoLineaProgettoVO.getNomeBandoLinea());
				fileInfoDTO.setCodiceProgetto(bandoLineaProgettoVO.getCodiceVisualizzato());
				fileInfoDTO.setDescBandoLinea(bandoLineaProgettoVO.getNomeBandoLinea());
				fileInfoDTO.setTitoloProgetto(bandoLineaProgettoVO.getTitoloProgetto());
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaComFineProg) {
				if(rfileEntita.getIdProgetto()!=null){ // escludo le entita preassociate
					List <InfoFileAssociatedComFineProgVO> fileAssociated= new ArrayList<InfoFileAssociatedComFineProgVO>();
					InfoFileAssociatedComFineProgVO filter= new InfoFileAssociatedComFineProgVO();
					filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
					fileAssociated = genericDAO.findListWhere(filter);
					logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita comFineProg  :"+idEntitaComFineProg);
					for (InfoFileAssociatedComFineProgVO infoFileAssociatedComFineProgVO : fileAssociated) {
						ComFineProgInfoDTO comFineProgInfoDTO = beanUtil.transform(infoFileAssociatedComFineProgVO, ComFineProgInfoDTO.class);
						listComFineProgInfo.add(comFineProgInfoDTO);
					}
				}
			 } else if (rfileEntita.getIdEntita().longValue()==idEntitaContoE) {
					if(rfileEntita.getIdProgetto()!=null){ // escludo le entita preassociate
						List <InfoFileAssociatedPropostaRimodVO> fileAssociated= new ArrayList<InfoFileAssociatedPropostaRimodVO>();
						InfoFileAssociatedPropostaRimodVO filter= new InfoFileAssociatedPropostaRimodVO();
						filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
						fileAssociated = genericDAO.findListWhere(filter);
						logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita conto econom/prop rim  :"+idEntitaContoE);
						for (InfoFileAssociatedPropostaRimodVO infoFileAssociatedPropostaRimodVO : fileAssociated) {
							PropostaRimodInfoDTO propostaRimodInfoDTO = beanUtil.transform(infoFileAssociatedPropostaRimodVO, PropostaRimodInfoDTO.class);
							listPropRimInfo.add(propostaRimodInfoDTO);
						}
					}
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaDich) {
					if(rfileEntita.getIdProgetto()!=null){ // escludo le entita preassociate
						List <InfoFileAssociatedDichiarazioneVO> fileAssociated= new ArrayList<InfoFileAssociatedDichiarazioneVO>();
						InfoFileAssociatedDichiarazioneVO filter= new InfoFileAssociatedDichiarazioneVO();
						filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
						fileAssociated = genericDAO.findListWhere(filter);
						logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita dichiarazione  :"+idEntitaDich);
						for (InfoFileAssociatedDichiarazioneVO infoFileAssociatedDichiarazioneVO : fileAssociated) {
							DichiarazioneInfoDTO dichiarazioneInfoDTO = beanUtil.transform(infoFileAssociatedDichiarazioneVO, DichiarazioneInfoDTO.class);
							listDichInfo.add(dichiarazioneInfoDTO);
						}
					}
			// Jira PBANDI-2802: aggiunge all'output le integrazioni DS.
			} else if (rfileEntita.getIdEntita().longValue()==idIntegrazioneDs) {
					List <InfoFileAssociatedIntegrazioneDsVO> fileAssociated= new ArrayList<InfoFileAssociatedIntegrazioneDsVO>();
					InfoFileAssociatedIntegrazioneDsVO filter= new InfoFileAssociatedIntegrazioneDsVO();
					filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
					fileAssociated = genericDAO.findListWhere(filter);
					logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita integrazione ds  :"+idIntegrazioneDs);
					for (InfoFileAssociatedIntegrazioneDsVO infoFileAssociatedIntegrazioneDsVO : fileAssociated) {
						// Uso DichiarazioneInfoDTO gi� usato per le dichiarazioni, poich� i dati da visualizzare sono gli stessi delle dichiarazioni.
						DichiarazioneInfoDTO dichiarazioneInfoDTO = beanUtil.transform(infoFileAssociatedIntegrazioneDsVO, DichiarazioneInfoDTO.class);
						listIntegrazioniDsInfo.add(dichiarazioneInfoDTO);
					}		// Fine Jira PBANDI-2802.
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaRicErog) {
				if(rfileEntita.getIdProgetto()!=null){ // escludo le entita preassociate
					List <InfoFileAssociatedRichiestaErogVO> fileAssociated= new ArrayList<InfoFileAssociatedRichiestaErogVO>();
					InfoFileAssociatedRichiestaErogVO filter= new InfoFileAssociatedRichiestaErogVO();
					filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
					fileAssociated = genericDAO.findListWhere(filter);
					logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita rich erog  :"+idEntitaRicErog);
					for (InfoFileAssociatedRichiestaErogVO infoFileAssociatedRichiestaErogVO : fileAssociated) {
						RichiestaErogazioneInfoDTO richiestaErogazioneInfoDTO = beanUtil.transform(infoFileAssociatedRichiestaErogVO, RichiestaErogazioneInfoDTO.class);
						listRichErogInfo.add(richiestaErogazioneInfoDTO);
					}
				}
			} else if (rfileEntita.getIdEntita().longValue()==idEntitaAppalto) {				
				List <InfoFileAssociatedAppaltoVO> fileAssociated =new ArrayList<InfoFileAssociatedAppaltoVO>();
				InfoFileAssociatedAppaltoVO filter = new InfoFileAssociatedAppaltoVO();
				filter.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
				fileAssociated = genericDAO.findListWhere(filter);
				logger.info("\n\nfound "+fileAssociated.size()+" fileAssociated for entita appalto:"+idEntitaAppalto);
				for (InfoFileAssociatedAppaltoVO info : fileAssociated) {
					if(!idsAppalto.contains(info.getIdAppalto())){
						idsAppalto.add(info.getIdAppalto());
						AppaltoInfoDTO dto = new AppaltoInfoDTO();
						if (info.getIdAppalto() != null) {
							dto.setIdAppalto(info.getIdAppalto().longValue());
						}
						dto.setCodProcAgg(info.getCodProcAgg());
						dto.setOggetto(info.getOggettoAppalto());
						dto.setCodiceProgettoVisualizzato(info.getCodiceProgetto());
						dto.setNomeBandoLinea(info.getDescBandoLinea());
						logger.info("entita appalto AppaltoInfoDTO.getIdAppalto(): "+dto.getIdAppalto());
						listAppaltoInfo.add(dto);			
					}
				}
			}
		}  // for
		
		
		if(!listComFineProgInfo.isEmpty())
			fileInfoDTO.setComFineProgInfo(listComFineProgInfo.toArray(new ComFineProgInfoDTO[0]));
		
		if(!listPropRimInfo.isEmpty())
			fileInfoDTO.setContoEcoInfo(listPropRimInfo.toArray(new PropostaRimodInfoDTO[0]));
		
		if(!listDichInfo.isEmpty())
			fileInfoDTO.setDichiarazioneInfo(listDichInfo.toArray(new DichiarazioneInfoDTO[0]));
				
		if(!listDocDiSpesaInfo.isEmpty())
			fileInfoDTO.setDocDiSpesaInfo(listDocDiSpesaInfo.toArray(new DocDiSpesaInfoDTO[0]));
		
		if(!listFornitoreInfo.isEmpty())
			fileInfoDTO.setFornitoreInfo(listFornitoreInfo.toArray(new FornitoreInfoDTO[0]));
		
		if(!listPagamentoInfo.isEmpty())
			fileInfoDTO.setPagamentoInfo(listPagamentoInfo.toArray(new PagamentoInfoDTO[0]));
		
		if(!listQualificaFornitoreInfo.isEmpty())
			fileInfoDTO.setQualificaFornitoreInfo(listQualificaFornitoreInfo.toArray(new QualificaFornitoreInfoDTO[0]));
		
		if(!listRichErogInfo.isEmpty())
			fileInfoDTO.setRichiestaErogazioneInfo(listRichErogInfo.toArray(new RichiestaErogazioneInfoDTO[0]));
		
		if(!listAppaltoInfo.isEmpty())
			fileInfoDTO.setAppaltoInfo(listAppaltoInfo.toArray(new AppaltoInfoDTO[0]));
		
		// Jira PBANDI-2802.
		if(!listIntegrazioniDsInfo.isEmpty())
			fileInfoDTO.setIntegrazioneDsInfo(listIntegrazioniDsInfo.toArray(new DichiarazioneInfoDTO[0]));
				
		logger.info(
				"\nlistComFineProgInfo: "+listComFineProgInfo.size()+
				"\nlistQualificaFornitoreInfo: "+listDichInfo.size()+
				"\nlistDocDiSpesaInfo: "+listDocDiSpesaInfo.size()+
				"\nlistFornitoreInfo: "+listFornitoreInfo.size()+
				"\nlistPagamentoInfo: "+listPagamentoInfo.size()+
				"\nlistPropRimInfo: "+listPropRimInfo.size()+
				"\nlistQualificaFornitoreInfo: "+listQualificaFornitoreInfo.size()+
				"\nlistRichErogInfo: "+listRichErogInfo.size()+
				"\nlistAppaltoInfo: "+listAppaltoInfo.size()+
				"\nlistIntegrazioniDsInfo: "+listIntegrazioniDsInfo.size()+
				"\nfileInfoDTO.getCodiceProgetto:"+fileInfoDTO.getCodiceProgetto()
				);
		
		return fileInfoDTO;
	}
	
	public FileDTO[] getFilesAssociatedAffidamento(Long idUtente,
			String identitaDigitale, Long idAffidamento)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idAffidamento" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idAffidamento);
		logger.info("\n\n\n\ngetFilesAssociatedAffidamento with idAffidamento: "+idAffidamento);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedAffidamento(BigDecimal.valueOf(idAffidamento));
		for (ArchivioFileVO archivioFile : list) {	
			
			FileDTO dto = createFileDTO(archivioFile);
			
			// Cerco PBANDI_T_FILE_ENTITA per il flag per invio verifica.
			PbandiTFileEntitaVO vo = new PbandiTFileEntitaVO();
			vo.setIdFileEntita(archivioFile.getIdFileEntita());
			vo = genericDAO.findSingleWhere(vo);
			
			// Aggancio il flag per invio verifica.
			dto.setDtEntita(vo.getDtEntita());
			dto.setFlagEntita(vo.getFlagEntita());
			
			filez.add(dto);
		}		
		return filez.toArray(new FileDTO [0] );
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
		
		fileDTO.setDtAssociazione(file.getDtAssociazione());	// Jira PBANDI-2890	
		
		fileDTO.setIdIntegrazioneSpesa(file.getIdIntegrazioneSpesa());
		
		return fileDTO;
	}
	
	public FileDTO[] getFilesAssociatedFornitoriOrQualifiche(Long idUtente, String identitaDigitale,
			Long idTarget, Long idEntita, Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idTarget", "idEntita"};		
		logger.info("\n\n\n\ngetFilesAssociatedFornitoriOrQualifiche with idTarget: "+idTarget+" idEntita:"+idEntita);
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idTarget,idEntita);
		
		
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedFornitoriOrQualifiche(BigDecimal.valueOf(idEntita),
				BigDecimal.valueOf(idTarget), idProgetto == null ? null : BigDecimal.valueOf(idProgetto));
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	
	public FileDTO[] getFilesAssociatedDocDiSpesa(Long idUtente,
			String identitaDigitale, Long idDocumentoDiSpesa, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoDiSpesa" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDocumentoDiSpesa);
		logger.info("\n\n\n\ngetFilesAssociatedDocDiSpesa with idDocumentoDiSpesa: "+idDocumentoDiSpesa+" idProgetto:"+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedDocDiSpesa(BigDecimal.valueOf(idDocumentoDiSpesa),
				BigDecimal.valueOf(idProgetto));
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	
	public Esito disassociateFile(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex, 
			Long idEntita,
			Long idTarget,
			Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idEntita","idTarget"};		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex,idEntita,idTarget);
		
		Esito esito = new Esito();
		esito.setEsito(false);
		
		try {
			long idEntitaDichiarazione= documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class).longValue();
			long idEntitaRichErogazione= documentoManager.getIdEntita(PbandiTRichiestaErogazioneVO.class).longValue();
			long idEntitaComunicazFineProg= documentoManager.getIdEntita(PbandiTComunicazFineProgVO.class).longValue();
			long idEntitaContoEconomico= documentoManager.getIdEntita(PbandiTContoEconomicoVO.class).longValue();
			
			logger.info("\n\nAAAAAAA  "+idEntitaDichiarazione);
			
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			
			fileVO=genericDAO.findSingleWhere(fileVO);
			
			PbandiTFileEntitaVO filterEntita=new PbandiTFileEntitaVO();
			filterEntita.setIdFile(fileVO.getIdFile());
			filterEntita.setIdEntita(BigDecimal.valueOf(idEntita));
			filterEntita.setIdTarget(BigDecimal.valueOf(idTarget));
			// i file associati a dich di spesa/richErog hanno bisogno di una gestione diversa dell'id progetto
			if(idEntitaDichiarazione!=idEntita.longValue() && 
					idEntitaRichErogazione!=idEntita.longValue() &&
					idEntitaComunicazFineProg !=idEntita.longValue() &&
					idEntitaContoEconomico !=idEntita.longValue())
				filterEntita.setIdProgetto(BigDecimal.valueOf(idProgetto));
			genericDAO.deleteWhere(Condition.filterBy(filterEntita));
		 
			esito.setMessage("doc index with id "+idDocumentoIndex+" disassociated with idFile: "+fileVO.getIdFile()+" idEntita : "+idEntita+" and idTarget "+idTarget);
			esito.setEsito(true);
			esito.setMessage("Operazione eseguita correttamente.");
		} catch (Exception e) {
			logger.error("Error genericDAO.update() idDocumentoIndex: "+idDocumentoIndex, e);
			throw new ArchivioException(e.getMessage());
		}
		
		return esito;
	}
	
	public FileDTO[] getFilesAssociatedPagamento(Long idUtente, String identitaDigitale, Long idPagamento)
			throws CSIException, SystemException, UnrecoverableException, ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idPagamento" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idPagamento);
		
		List <FileDTO> filez = new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedPagamento(BigDecimal.valueOf(idPagamento));
		for (ArchivioFileVO pbandiRFolderFileDocIndexVO : list) {
			filez.add(createFileDTO(pbandiRFolderFileDocIndexVO));
		}
		return filez.toArray(new FileDTO[0]);
	}
	
	// Si assume che la dichiarazione di spesa non sia stata ancora salvata, quindi si usa l'id progetto.
	// E' una pre-associazione: quando la dichiarazione verrà creata, l'id della dichiarazione sostituirà l'id progetto come id_target.
	public FileDTO[] getFilesAssociatedDichiarazioneByIdProgetto(Long idUtente,
			String identitaDigitale, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto);
		/*PBANDI_T_RICHIESTA_EROGAZIONE
		PBANDI_T_DICHIARAZIONE_SPESA*/
		logger.info("\n\n\n\ngetFilesAssociatedDichiarazioneByIdProgetto with  idProgetto:"+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedByIdProgetto( 
				BigDecimal.valueOf(idProgetto),"PBANDI_T_DICHIARAZIONE_SPESA");
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}

	// Usato per associare allegati ad una DS FINALE, che ora comprende anche la CFP.
	// Si assume che la cfp non sia stata ancora salvata, quindi si usa l'id progetto.
	// E' una pre-associazione: quando la cfp verrà creata, l'id della cfp sostituirà l'id progetto come id_target.
//	NON PIU USATO, FILE SEMPRE ALLEGATI ALLA DS E NON ALLA CFP
//	public FileDTO[] getFilesAssociatedComunicazioneFineProgByIdProgetto(Long idUtente,
//			String identitaDigitale, Long idProgetto) throws CSIException,
//			SystemException, UnrecoverableException, ArchivioException {
//		String[] nameParameter = { "idUtente", "identitaDigitale","idProgetto" };		
//		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idProgetto);
//		
//		logger.info("\n\n\n\ngetFilesAssociatedComunicazioneFineProgByIdProgetto with  idProgetto:"+idProgetto);
//		List <FileDTO> filez=new ArrayList<FileDTO>();
//		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedByIdProgetto( 
//				BigDecimal.valueOf(idProgetto),"PBANDI_T_COMUNICAZ_FINE_PROG");
//		for (ArchivioFileVO archivioFile : list) {
//			filez.add(createFileDTO(archivioFile));
//		}
//		return filez.toArray(new FileDTO [0] );
//	}
	
	// da usare dopo che la CFP è stata creata e quindi ne esiste l'id (ad esempio nella popup allegati in Validazione). 
	public FileDTO[] getFilesAssociatedComunicazioneFineProg(Long idUtente,
			String identitaDigitale, Long idComunicazioneFineProg, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idComunicazioneFineProg","idProgetto" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idComunicazioneFineProg,idProgetto);
		logger.info("\n\n\n\ngetFilesAssociatedComunicazioneFineProg with idComunicazioneFineProg: "+idComunicazioneFineProg+" ,idProgetto: "+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociated(BigDecimal.valueOf(idComunicazioneFineProg),"PBANDI_T_COMUNICAZ_FINE_PROG" );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	// da usare dopo che la CFP è stata creata e quindi ne esiste l'id (ad esempio nella popup allegati in Validazione).
	public FileDTO[] getFilesAssociatedComunicazioneFineProgNoIntegr(Long idUtente, String identitaDigitale, Long idComunicazioneFineProg, Long idProgetto) throws CSIException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idComunicazioneFineProg","idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idComunicazioneFineProg,idProgetto);
		logger.info("\n\n\n\ngetFilesAssociatedComunicazioneFineProg with idComunicazioneFineProg: "+idComunicazioneFineProg+" ,idProgetto: "+idProgetto);
		List <FileDTO> filez= new ArrayList<>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedNoIntegr(BigDecimal.valueOf(idComunicazioneFineProg),"PBANDI_T_COMUNICAZ_FINE_PROG" );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	
	public FileDTO[] getFilesAssociatedIntegrazioneDS(Long idUtente,
			String identitaDigitale, Long idDichiarazioneSpesa)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idAffidamento" };		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDichiarazioneSpesa);
		
		PbandiCEntitaVO entita = new PbandiCEntitaVO();
		entita.setNomeEntita(NOME_ENTITA_PBANDI_T_INTEGRAZIONE_SPESA);
		entita = genericDAO.findSingleWhere(entita);
		
		logger.info("\n\n\n\ngetFilesAssociatedIntegrazioneDS with idDichiarazioneSpesa: "+idDichiarazioneSpesa);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFiles(
				new BigDecimal(idDichiarazioneSpesa), entita.getIdEntita());
		for (ArchivioFileVO archivioFile : list) {	
			filez.add(createFileDTO(archivioFile));
		}		
		return filez.toArray(new FileDTO [0] );		
	}
	
	// Valorizza il campo PBANDI_T_FILE_ENTITA.FLAG_ENTITA
	// Metodo creato per Jira PBANDI-2815.
	public Esito marcaFlagEntitaFile(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex, 
			Long idEntita,
			Long idTarget,
			Long idProgetto,
			String flag)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idEntita","idTarget"};		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex,idEntita,idTarget);
		
		Esito esito = new Esito();
		esito.setEsito(false);
		
		try {
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));			
			fileVO = genericDAO.findSingleWhere(fileVO);
			Long idFile = fileVO.getIdFile().longValue();
			
			logger.info("marcaFlagEntitaFile(): idDocumentoIndex = "+idDocumentoIndex+"; idFile = "+idFile+"; idEntita = "+idEntita+"; idTarget = "+idTarget+"; idProgetto = "+idProgetto+"; flag = "+flag);
			
			// Trova i file associati allo stesso target (ad esempio gli allegati di una richiesta di integrazione).
			PbandiTFileEntitaVO vo = new PbandiTFileEntitaVO();
			vo.setIdEntita(BigDecimal.valueOf(idEntita));
			vo.setIdTarget(BigDecimal.valueOf(idTarget));
			vo.setIdProgetto(BigDecimal.valueOf(idProgetto));
			List<PbandiTFileEntitaVO> lista = genericDAO.findListWhere(vo);
			
			// Valorizzo il campo flag del file in input e setto a null gli altri.
			for (PbandiTFileEntitaVO f : lista) {
				if (f.getIdFile().intValue() == idFile.intValue())
					f.setFlagEntita(flag);
				else
					f.setFlagEntita(null);
				genericDAO.updateNullables(f);
			}
			
			esito.setEsito(true);
			esito.setMessage("Operazione eseguita con successo.");
		} catch (Exception e) {
			logger.error("Errore in marcaFlagEntitaFile(): ", e);
			throw new ArchivioException(e.getMessage());
		}		
		return esito;
	}

	public FileDTO[] getFilesAssociatedDichiarazione(Long idUtente,
													 String identitaDigitale, Long idDichiarazione,Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDichiarazione);
		logger.info("\n\n\n\ngetFilesAssociatedDichiarazione with idDichiarazione: "+idDichiarazione+" ,idProgetto: "+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociated(BigDecimal.valueOf(idDichiarazione),"PBANDI_T_DICHIARAZIONE_SPESA" );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	public FileDTO[] getFilesAssociatedDichiarazioneNoIntegr(Long idUtente, String identitaDigitale, Long idDichiarazione,Long idProgetto) throws CSIException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDichiarazione);
		logger.info("\n\n\n\ngetFilesAssociatedDichiarazione with idDichiarazione: "+idDichiarazione+" ,idProgetto: "+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedNoIntegr(BigDecimal.valueOf(idDichiarazione),"PBANDI_T_DICHIARAZIONE_SPESA" );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	
	public FileDTO[] getFilesAssociatedByIdTargetAndEntita(Long idUtente, String identitaDigitale, Long idTarget, String nomeEntita, Long idProgetto) throws CSIException {
		String[] nameParameter = { "idUtente", "identitaDigitale","idDichiarazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idTarget, nomeEntita);
		logger.info("\n\n\n\ngetFilesAssociatedByIdTargetAndEntita with idTarget: "+idTarget+" ,nomeEntita: "+nomeEntita
				+" ,idProgetto: "+idProgetto);
		List <FileDTO> filez=new ArrayList<FileDTO>();
		List<ArchivioFileVO> list = getPbandiArchivioFileDAOImpl().getFilesAssociatedNoIntegr(BigDecimal.valueOf(idTarget), nomeEntita );
		for (ArchivioFileVO archivioFile : list) {
			filez.add(createFileDTO(archivioFile));
		}
		return filez.toArray(new FileDTO [0] );
	}
	 
}
