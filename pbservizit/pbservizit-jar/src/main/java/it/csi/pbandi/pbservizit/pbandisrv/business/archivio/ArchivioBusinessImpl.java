/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.archivio;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
//import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.AppaltoInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.ComFineProgInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.DichiarazioneInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.DocDiSpesaInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FornitoreInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.PagamentoInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.PropostaRimodInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.QualificaFornitoreInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.RichiestaErogazioneInfoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.ArchivioException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiArchivioFileDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedComFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedDocSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedIntegrazioneDsVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedPropostaRimodVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedQualificaFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.InfoFileAssociatedRichiestaErogVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIntegrazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
//import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.archivio.ArchivioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.util.Constants;

import java.math.BigDecimal;
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
	
	public Esito associateFile(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex, Long idTarget, Long idEntita, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			ArchivioException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idTarget", "idEntita","idProgetto"};		
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex,idTarget,idEntita,idProgetto);
		
		Esito esito = new Esito();
		esito.setEsito(false);
		logger.info("associateFile idDocumentoIndex:"+idDocumentoIndex+" idEntita:"+idEntita+" idTarget: "+idTarget+" idProgetto:"+idProgetto);
		try {
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
			pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		 
			pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
			pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			BigDecimal idEntitaComunicazioneFineProgettoVO = documentoManager.getIdEntita(PbandiTComunicazFineProgVO.class);
			BigDecimal idEntitaContoEconomicoVO = documentoManager.getIdEntita(PbandiTContoEconomicoVO.class);
			BigDecimal idEntitaDichiarazioneVO = documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class);
			BigDecimal idEntitaFornitoreVO = documentoManager.getIdEntita(PbandiTFornitoreVO.class);
			BigDecimal idEntitaPagamentoVO = documentoManager.getIdEntita(PbandiTPagamentoVO.class);
			BigDecimal idEntitaRichiestaErogazioneVO = documentoManager.getIdEntita(PbandiTRichiestaErogazioneVO.class);
			/*
			 * l 'id progetto non deve essere inserito nella t_documento_index
			 */
			/*if (idEntitaPagamentoVO.compareTo(BigDecimal.valueOf(idEntita)) != 0
					&& idEntitaFornitoreVO.compareTo(BigDecimal
							.valueOf(idEntita)) != 0 ) {
				//pbandiTDocumentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			}*/
			
			logger.info("trying to associate  idDocumentoIndex "+idDocumentoIndex+" to entity "+idEntita+ " with idTarget "+idTarget);
			genericDAO.update(pbandiTDocumentoIndexVO);
			
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			fileVO=genericDAO.findSingleWhere(fileVO);
			
			PbandiTFileEntitaVO pbandiTFileEntitaVO=new PbandiTFileEntitaVO();
			pbandiTFileEntitaVO.setIdFile(fileVO.getIdFile());
			pbandiTFileEntitaVO.setIdEntita(BigDecimal.valueOf(idEntita));
			pbandiTFileEntitaVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			if(idEntitaDichiarazioneVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaComunicazioneFineProgettoVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaRichiestaErogazioneVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaContoEconomicoVO.compareTo(BigDecimal.valueOf(idEntita)) == 0){
				// quando la dich/richErog/comunicazFineProg/contoeconomico non è ancora stata creata(preassociazione)
				//viene inserito come id_target l'id progetto. 
				//Alla creazione della dich/richErog/comunicazFineProg
				//l'id target verrà sovrascritto con l'id di dich/richErog/comunicazFineProg e verrà inserito l'idProgetto 
				idTarget= idProgetto;	
				pbandiTFileEntitaVO.setIdProgetto(null);
	    	}
			pbandiTFileEntitaVO.setIdTarget(BigDecimal.valueOf(idTarget));
			
			genericDAO.insert(pbandiTFileEntitaVO);
			logger.info("file with idDocumentoIndex "+idDocumentoIndex+" associated to entity "+idEntita+ " with idTarget "+idTarget);
			
			esito.setMessage("file with idDocumentoIndex "+idDocumentoIndex+" associated to entity "+idEntita+ " with idTarget "+idTarget);
			esito.setEsito(true);
		} catch (Exception e) {
			logger.error("Error genericDAO.update() idDocumentoIndex: "+idDocumentoIndex, e);
			throw new ArchivioException(e.getMessage());
		}
		
		return esito;
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
		} catch (Exception e) {
			logger.error("Error genericDAO.update() idDocumentoIndex: "+idDocumentoIndex, e);
			throw new ArchivioException(e.getMessage());
		}
		
		return esito;
	}
	
	public java.lang.Boolean isFileAssociated(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocumentoIndex,
			java.lang.Long idTarget,
			java.lang.Long idEntita
			) throws it.csi.csi.wrapper.CSIException,
					it.csi.csi.wrapper.SystemException,
					it.csi.csi.wrapper.UnrecoverableException
					, it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.ArchivioException

			{
				
			String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idTarget","idEntita"};		
			ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale, idDocumentoIndex,idTarget,idEntita);
			
			
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			
			fileVO=genericDAO.findSingleWhere(fileVO);
			
			PbandiTFileEntitaVO filterEntita=new PbandiTFileEntitaVO();
			filterEntita.setIdFile(fileVO.getIdFile());
			filterEntita.setIdTarget(BigDecimal.valueOf(idTarget));
			filterEntita.setIdEntita(BigDecimal.valueOf(idEntita));
			logger.info("searching previous association for file with idDocumentoIndex "+idDocumentoIndex
					+" ,idTarget:"+idTarget
					+" ,idEntita:"+idEntita);
			
			int count = genericDAO.count(Condition.filterBy(filterEntita));
			if(count<1) return false;
	 
			return true;
	}
	 
}
