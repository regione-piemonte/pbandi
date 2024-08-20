/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.business.digitalsign;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RappresentanteLegaleManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbwebrce.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbwebrce.pbandisrv.business.manager.DoSignManager;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.digitalsign.SignedFileDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo.DelegatoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiDSistemaProtocolloVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocProtocolloVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class DigitalSignBusinessImpl extends BusinessImpl implements DigitalSignSrv {
	
	@Autowired
	DocumentoManager documentoManager;
	
	@Autowired
	private	DoSignManager doSignManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private RappresentanteLegaleManager rappresentanteLegaleManager;
	
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public RappresentanteLegaleManager getRappresentanteLegaleManager() {
		return rappresentanteLegaleManager;
	}


	public void setRappresentanteLegaleManager(
			RappresentanteLegaleManager rappresentanteLegaleManager) {
		this.rappresentanteLegaleManager = rappresentanteLegaleManager;
	}

	public DoSignManager getDoSignManager() {
		return doSignManager;
	}

	public void setDoSignManager(DoSignManager doSignManager) {
		this.doSignManager = doSignManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	
	// NOTA: controllo spostato lato Angular.
	public java.lang.Long getFileSizeLimit(Long idUtente,String identitaDigitale) throws it.csi.csi.wrapper.CSIException,
					it.csi.csi.wrapper.SystemException,
					it.csi.csi.wrapper.UnrecoverableException
					,
					it.csi.pbandi.pbwebrce.pbandisrv.business.digitalsign.DigitalSignException
			{
		return null;
		/*
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale );
		Long sizeLimit=3072000l;
		try {
			 Long digitalSignFileSizeLimit = configurationManager.getConfiguration().getDigitalSignFileSizeLimit();
			 if(digitalSignFileSizeLimit!=null){
				 logger.info("getFileSizeLimit on DB: "+digitalSignFileSizeLimit);
				 sizeLimit=digitalSignFileSizeLimit;
			 }else{ 
				 logger.info("getFileSizeLimit NOT FOUND on DB, using DEFAULT "+sizeLimit);
				 }
				 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sizeLimit;
		*/		
	}
	
	
	public java.lang.Boolean setFlagCartaceo(
			 Long idUtente,String identitaDigitale, Long idDocIndex,String flag)
						throws it.csi.csi.wrapper.CSIException,
						it.csi.csi.wrapper.SystemException,
						it.csi.csi.wrapper.UnrecoverableException,
						it.csi.pbandi.pbwebrce.pbandisrv.business.digitalsign.DigitalSignException{
							String[] nameParameter = { "idUtente", "identitaDigitale", "idDocIndex","flag"};
							ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,idDocIndex,flag);
			try {
				logger.info("\n\nsetFlagCartaceo for idDocIndex:"+idDocIndex+" , flag: "+flag );
				PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
				pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocIndex));
				pbandiTDocumentoIndexVO.setFlagFirmaCartacea(flag);
				pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
				genericDAO.update(pbandiTDocumentoIndexVO);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new DigitalSignException(e.getMessage(),e);
			}
					
	}

	// Metodo non usato nella vecchia versione.
	public String uploadSignedFileDichSpesa(Long idUtente,
			String identitaDigitale,Long idSoggettoBen,String cfBeneficiario,String cfLegaleRapp,String cfDelegato,
			SignedFileDTO signedFileDTO) throws CSIException, SystemException,
			UnrecoverableException, DigitalSignException {
		return null;
		/*
		long start=System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale","idSoggettoBen","cfBeneficiario","cfLegaleRapp","signedFileDTO" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idSoggettoBen,cfBeneficiario,cfLegaleRapp,signedFileDTO);
		logger.warn("\n\n\n\nuploadSignedFile ASYNC... ");
		logger.info("idSoggettoBen: "+idSoggettoBen+" ,cfBeneficiario:"+cfBeneficiario+" ,cfDelegato:"+cfDelegato+", signedFileDTO.getBytes() :"+signedFileDTO.getBytes()+
				"signedFileDTO.getFileName(): "+signedFileDTO.getFileName()+" , signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex());
		try{
		  
			String bypassCheckDigitalSign = getConfigurationManager().getConfiguration().getBypassCheckDigitalSign();
		  
		  
			if(bypassCheckDigitalSign!=null && bypassCheckDigitalSign.equalsIgnoreCase("S")){
			  logger.warn("************ATTENZIONE! BYPASS DEI CONTROLLI SULLA FIRMA DIGITALE , controllare il flag sul db************\n\n\n\n");
			  return "";
			}else{
			  List<String>cfLegaleRapps=new ArrayList<String>();
			  cfLegaleRapps.add(cfLegaleRapp);
///			  Boolean verify = doSignManager.verify(idUtente,cfLegaleRapps,cfDelegato,signedFileDTO.getIdDocIndex(),signedFileDTO.getBytes());
///			  logger.info("call to doSignManager.verify  return: "+verify);
			}
		}  catch(Exception x){
			logger.error("Error in getBypassCheckDigitalSign "+x.getMessage(), x);
		}
		
		logger.warn("\n\nuploadSignedFile executed in "+(System.currentTimeMillis()- start)+" ms,idSoggettoBen:"+
		idSoggettoBen+",cdBeneficiario: "+cfBeneficiario+", idUtente :"+idUtente+"\n\n\n\n");
		return "";
		*/
	}
	
	
	
 


	private void saveSignedFile(Long idUtente,SignedFileDTO signedFileDTO,PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO) throws Exception {
	
		// INDEX DISMESSO
		//logger.info("\n\n\nsaveSignedFile on index for uinode: "+pbandiTDocumentoIndexVO.getUuidNodo());
		//indexDAO.updateWithSignedContent(pbandiTDocumentoIndexVO.getUuidNodo(),signedFileDTO.getBytes());
	
		DecodificaDTO statoAcquisito= decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_ACQUISITO);
		pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
		pbandiTDocumentoIndexVO.setFlagFirmaCartacea("N");
		pbandiTDocumentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoAcquisito.getId()));
		pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		
		logger.info("\n\n\nupdating db for idDocIndex: "+signedFileDTO.getIdDocIndex()+" with statoFirmato.getId():"+statoAcquisito.getId());
		genericDAO.update(pbandiTDocumentoIndexVO);
	}

/*
 * // PK : index dismesso, logica di questo metodo trascritta in documentoManager.salvaFileMarcato
	private void saveTimestampedFile(PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO,byte [] bytesTimestamped,Long idUtente) throws Exception {
		
		// INDEX DISMESSO
		//logger.info("\n\n\nsaveTimestampedFile on index for uinode: "+pbandiTDocumentoIndexVO.getUuidNodo());
		//indexDAO.updateWithTimestampedContent(pbandiTDocumentoIndexVO.getUuidNodo(),bytesTimestamped);
		
		DecodificaDTO statoInviato= decodificheManager.findDecodifica(
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
				GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_INVIATO);
		pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
		pbandiTDocumentoIndexVO.setDtMarcaTemporale(DateUtil.getSysdate());
		pbandiTDocumentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoInviato.getId()));
		pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
		logger.info("\n\n\nupdating db for idDocIndex: "+pbandiTDocumentoIndexVO.getIdDocumentoIndex()+" with statoInviato.getId():"+statoInviato.getId());
		genericDAO.update(pbandiTDocumentoIndexVO);
	}
*/

	public String signFile(Long idUtente, String identitaDigitale,
			SignedFileDTO signedFileDTO)
			throws CSIException, SystemException, UnrecoverableException,
			DigitalSignException {
		long start=System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "signedFileDTO" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,signedFileDTO);
		logger.warn("\n\n\n\n\n\nsignFile ASYNC... ");
		logger.info( "signedFileDTO.getBytes() :"+signedFileDTO.getBytes()+
				"signedFileDTO.getFileName(): "+signedFileDTO.getFileName()+" , signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex());
		
		
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
		pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(signedFileDTO.getIdDocIndex()));
		 DecodificaDTO statoNonValidato= decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_NON_VALIDATO);
		try{			  
			
		      logger.info("searching pbandiTDocumentoIndexVO for signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex());
			  pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO) ;
			  
			  // +Green: verifico se il progetto � un +Green di tipo contributo.
			  PbandiTProgettoVO progetto = new PbandiTProgettoVO();
			  progetto.setIdProgetto(pbandiTDocumentoIndexVO.getIdProgetto());
			  progetto = genericDAO.findSingleWhere(progetto);
			  Long idProgettoFinanziamento = null;
			  if (progetto.getIdProgettoFinanz() != null)
				  idProgettoFinanziamento = progetto.getIdProgettoFinanz().longValue();
			  logger.info("signFile(): idProgettoFinanziamento = "+idProgettoFinanziamento);
			
			  // INDEX DISMESSO e lo stato ACQUISITO è già stato settato sulla T_DOUMENTO_INDEX, non serve rifarlo.
			  // 1) update content and metadata on INDEX and update PBANDI_T_DOC_INDEX  
			  //saveSignedFile(idUtente,signedFileDTO,pbandiTDocumentoIndexVO);
			  
			  // 2) find String cfLegaleRapp,String cfDelegato, associate rapp legale and delegato to dichiarazione?
			  List<String>cfLegaleRapps=new ArrayList<String>();
			  BigDecimal idSoggDelegato = pbandiTDocumentoIndexVO.getIdSoggDelegato();
			  BigDecimal idSoggRappLegale= pbandiTDocumentoIndexVO.getIdSoggRapprLegale();
			  logger.info("idSoggRappLegale : "+idSoggRappLegale + "\nidSoggDelegato:"+idSoggDelegato);
			  if(idSoggRappLegale!=null){
				  RappresentanteLegaleDTO rappresentanteLegale = rappresentanteLegaleManager.findRappresentanteLegale(pbandiTDocumentoIndexVO.getIdProgetto().longValue(), idSoggRappLegale.longValue());
				  
				  // +Green: se non trovo nulla ed � un progetto contributo, provo col progetto finanziamento.
				  if (rappresentanteLegale == null || 
					  StringUtil.isEmpty(rappresentanteLegale.getCodiceFiscaleSoggetto())) {
					  if (idProgettoFinanziamento != null) {
						  logger.info("signFile(): cerco il rappresentante legale col progetto finanziamento.");
						  rappresentanteLegale = rappresentanteLegaleManager.findRappresentanteLegale(idProgettoFinanziamento, idSoggRappLegale.longValue());
					  }
				  }
					  				  
				  logger.info("adding rappresentanteLegale.getCodiceFiscaleSoggetto() "+rappresentanteLegale.getCodiceFiscaleSoggetto());
				  cfLegaleRapps.add(rappresentanteLegale.getCodiceFiscaleSoggetto());
				  				  
			  }else{
				  List<RappresentanteLegaleDTO> rappresentantiLegali = rappresentanteLegaleManager.findRappresentantiLegali(pbandiTDocumentoIndexVO.getIdProgetto().longValue(), null);
				  for (RappresentanteLegaleDTO rappresentanteLegaleDTO : rappresentantiLegali) {
					  logger.info("adding rappresentanteLegaleDTO.getCodiceFiscaleSoggetto() "+rappresentanteLegaleDTO.getCodiceFiscaleSoggetto());
					  cfLegaleRapps.add(rappresentanteLegaleDTO.getCodiceFiscaleSoggetto());
				}
			  }
			  
			  String cfDelegato="";
			  if(idSoggDelegato!=null){
						logger.info("findDelegato idSoggDelegato"+idSoggDelegato);
					    DelegatoVO delegatoVO = new DelegatoVO();
					    delegatoVO.setIdSoggetto(idSoggDelegato.longValue());
					    delegatoVO.setIdProgetto(pbandiTDocumentoIndexVO.getIdProgetto().longValue());
					    List<DelegatoVO> delegati = genericDAO.findListWhere(delegatoVO);
					    
					    // +Green: se non trovo nulla ed � un progetto contributo, provo col progetto finanziamento.
					    if(delegati == null || delegati.isEmpty()){
					    	if (idProgettoFinanziamento != null) {
					    		logger.info("signFile(): cerco il delegato col progetto finanziamento.");
					    		DelegatoVO delegato2 = new DelegatoVO();
							    delegato2.setIdSoggetto(idSoggDelegato.longValue());
							    delegato2.setIdProgetto(idProgettoFinanziamento);
							    delegati = genericDAO.findListWhere(delegato2);
					    	}
					    }					    
					    
					    if(delegati!=null && !delegati.isEmpty()){
					    	delegatoVO=delegati.get(0);
					    	cfDelegato=delegatoVO.getCodiceFiscaleSoggetto();
					    }
					    
					    
			  }
			  			  
			  //3) verify
			 
			  logger.info("Chiamo doSignManager.verify()");
			  Boolean verify = true; 
			  verify = doSignManager.verify(idUtente,cfLegaleRapps,cfDelegato,signedFileDTO.getIdDocIndex(),signedFileDTO.getBytes());
			  logger.info("esito doSignManager.verify() = "+verify);
			  if(verify){
					DecodificaDTO statoValidato= decodificheManager.findDecodifica(
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
							GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_VALIDATO);
					pbandiTDocumentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoValidato.getId()));
					pbandiTDocumentoIndexVO.setDtVerificaFirma(DateUtil.getSysdate());
					pbandiTDocumentoIndexVO.setIdUtenteAgg( BigDecimal.valueOf(idUtente));
					logger.info("updating db for idDocIndex: "+signedFileDTO.getIdDocIndex()+" with statoValidato.getId():"+statoValidato.getId());
					genericDAO.update(pbandiTDocumentoIndexVO);
					 				
					byte[] bytesTimeStamp =null;
					try{
						 bytesTimeStamp = doSignManager.timeStamp(idUtente, pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue(), signedFileDTO.getBytes());
					}  catch(Exception x){
						logger.error("Error : "+x.getMessage(), x);
					}
						
					// 4) INSERT IN INDEX BYTES TIMESTAMPED
					
					// INDEX DISMESSO
					//if(bytesTimeStamp!=null) saveTimestampedFile(pbandiTDocumentoIndexVO,bytesTimeStamp,idUtente);
					
					if(bytesTimeStamp!=null) { 
						Long idDocIndex = pbandiTDocumentoIndexVO.getIdDocumentoIndex().longValue();
						documentoManager.salvaFileMarcato(bytesTimeStamp, idDocIndex, idUtente);
					}
			  }else{
				
				  pbandiTDocumentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoNonValidato.getId()));
				  pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
				  pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
				  logger.info("updating db for idDocIndex: "+signedFileDTO.getIdDocIndex()+" with statoNonValidato.getId():"+statoNonValidato.getId());
				  genericDAO.update(pbandiTDocumentoIndexVO);
			  }
		}  catch(Exception x){
				logger.error("Error : "+x.getMessage(), x);
				  pbandiTDocumentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoNonValidato.getId()));
				  pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
				  pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
				  logger.info("updating db for idDocIndex: "+signedFileDTO.getIdDocIndex()+" with statoNonValidato.getId():"+statoNonValidato.getId());
				  try {
					genericDAO.update(pbandiTDocumentoIndexVO);
				} catch (Exception e) {
					logger.error("Error : "+x.getMessage(), x);
				}
		}
		
		logger.warn("\n\nuploadSignedFile executed in "+(System.currentTimeMillis()- start)+" ms,idSoggettoBen:"+
		 ", idUtente :"+idUtente+"\n\n\n\n");
		return "";
	}

	public Boolean uploadSignedFile(Long idUtente, String identitaDigitale,
			SignedFileDTO signedFileDTO) throws CSIException, SystemException,
			UnrecoverableException, DigitalSignException {
		long start=System.currentTimeMillis();
		String[] nameParameter = { "idUtente", "identitaDigitale", "signedFileDTO" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,signedFileDTO);
		Boolean ret=Boolean.FALSE;
		logger.warn("\n\n\n\nuploadSignedFile  SYNC... ");
		logger.info( "signedFileDTO.getBytes() :"+signedFileDTO.getBytes()+
				"signedFileDTO.getFileName(): "+signedFileDTO.getFileName()+" , signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex());
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO();
		pbandiTDocumentoIndexVO.setIdDocumentoIndex(BigDecimal.valueOf(signedFileDTO.getIdDocIndex()));
	
		try{
		      logger.info("searching pbandiTDocumentoIndexVO for signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex());
			  pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO) ;
			
			  // 1) update content and metadata on INDEX and update PBANDI_T_DOC_INDEX  
			  saveSignedFile(idUtente,signedFileDTO,pbandiTDocumentoIndexVO);
			  
			  ret=Boolean.TRUE;
			 
		}  catch(Exception x){
			logger.error("Error uploadSignedFile signedFileDTO.getIdDocIndex():"+signedFileDTO.getIdDocIndex()+" , error message: "+x.getMessage(), x);
		}
		
		logger.warn("\n\nuploadSignedFile executed in "+(System.currentTimeMillis()- start)+" ms,idSoggettoBen:"+", idUtente :"+idUtente+"\n\n\n\n");
		return ret;
	}

	public Boolean saveProtocollo(Long idUtente, String identitaDigitale,
			Long idDocIndex, String protocollo) throws CSIException,
			SystemException, UnrecoverableException, DigitalSignException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDocIndex","protocollo"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale,idDocIndex,protocollo);
			try {
				logger.info("\n\n\n\nsaveProtocollo for idDocIndex:"+idDocIndex+" , protocollo: "+protocollo );
				PbandiTDocProtocolloVO pbandiTDocProtocolloVO=new PbandiTDocProtocolloVO();
				pbandiTDocProtocolloVO.setDtInserimento(DateUtil.getSysdate());
				pbandiTDocProtocolloVO.setDtProtocollo(DateUtil.getSysdate());
				pbandiTDocProtocolloVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocIndex));
				pbandiTDocProtocolloVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
				pbandiTDocProtocolloVO.setNumProtocollo(protocollo);
				PbandiDSistemaProtocolloVO pbandiDSistemaProtocolloVO=new PbandiDSistemaProtocolloVO();
				pbandiDSistemaProtocolloVO.setDescrizione("AUTOMATICO");
				pbandiDSistemaProtocolloVO = genericDAO.findSingleWhere(pbandiDSistemaProtocolloVO);
				pbandiTDocProtocolloVO.setIdSistemaProt(pbandiDSistemaProtocolloVO.getIdSistemaProt() );
				genericDAO.insert(pbandiTDocProtocolloVO);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new DigitalSignException(e.getMessage(),e);
			}			
	}

	 

}
