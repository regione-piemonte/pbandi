/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.business.digitalsign;


import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTLogValidazFirmaVO;
import it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweberog.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweberog.pbandiutil.common.LoggerUtil;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.DosignSignatureValidation;
import it.doqui.dosign.dosign.business.session.dosign.timestamping.DosignTimestamping;
import it.doqui.dosign.dosign.client.DosignServiceClient;
import it.doqui.dosign.dosign.dto.publish.DosignServiceDto;
import it.doqui.dosign.dosign.dto.signaturevalidation.SignerDto;
import it.doqui.dosign.dosign.dto.signaturevalidation.VerifyDto;
import it.doqui.dosign.dosign.dto.signaturevalidation.VerifyInfoDto;
import it.doqui.dosign.dosign.dto.signaturevalidation.VerifyReportDto;
import it.doqui.dosign.dosign.dto.timestamping.TimestampingParameterDto;
import it.doqui.dosign.dosign.dto.timestamping.TimestampingValueDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DoSignManager {
	private static final String VERIFY = "VERIFY";
	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String ERR_M_DIG = "ERR_M_DIG";
	private static final String ERR_CF_FIR = "ERR_CF_FIR";
	private static final String ERR_GENERIC = "ERR_GENERIC";
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private DecodificheManager decodificheManager;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private LoggerUtil logger;
	
	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	
	/*
	private ConfigurationManager configurationManager;
	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
	*/
	
	private class ResultCheckMessageDigest{
		private boolean result;
		private String message;
		public boolean isResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}

	public  Boolean verify  (Long idUtente,List<String> cfLegaleRapps,String cfDelegato,Long idDocIndex, byte[] bytesSignedFile) throws DigitalSignException{
		long start=System.currentTimeMillis();
		List<String> listCfMatched = new ArrayList<String>();
		String codiceErroreDosign=null;
		String msgErroreDosign=null;
	    try {
	    	
	    	// Legge da db se la verifica della firma è da fare.
	    	String bypassCheckDigitalSign = findCostante("conf.bypassCheckDigitalSign");
	    	
	    	
	    	// Legge da db se la verifica del codice fiscale del rappresentante legale è da fare.
	    	String bypassCheckCFDigitalSign = findCostante("conf.bypassCheckCFDigitalSign");
	    	
	    	//String bypassCheckDigitalSign = getConfigurationManager().getConfiguration().getBypassCheckDigitalSign();
	    	//String bypassCheckCFDigitalSign = getConfigurationManager().getConfiguration().getBypassCheckCFDigitalSign();
	    	
			  if(bypassCheckDigitalSign!=null && bypassCheckDigitalSign.equalsIgnoreCase("S")){
				  logger.warn("************ATTENZIONE! BYPASS DEI CONTROLLI SULLA FIRMA DIGITALE , controllare il flag sul db************\n\n\n\n");
				  return Boolean.TRUE;
			  }else{
	    	
				 logger.info("\n\n\n\n\n#######################\nbegin verify for idDocIndex "+idDocIndex+" , bytesSignedFile: "+bytesSignedFile+" ,bytesSignedFile.length: "+bytesSignedFile.length);
				
				 DosignServiceDto dosignService = initializeDosignService();
	 
				 logger.info("\n\n\ncalling DosignServiceClient.getSignatureValidationService...");
				 start=System.currentTimeMillis();
			     DosignSignatureValidation dosignSignatureValidation = DosignServiceClient.getSignatureValidationService(dosignService);
			     logger.info("\n*** DosignServiceClient.getSignatureValidationService(serviceDosignService) executed in ms:  "+ (System.currentTimeMillis()-start));
			 
				 VerifyDto verifyDto = initializeVerifyDTO(bytesSignedFile);
				 
				 start=System.currentTimeMillis();
		         VerifyReportDto verifyReport = dosignSignatureValidation.verify(verifyDto);
		         logger.info("\n***DosignSignatureValidation.verify executed in ms : "+(System.currentTimeMillis()-start));
				 
		         List<VerifyInfoDto> listOfVerifyInfo = verifyReport.getVerifyInfo();
		         
				 logger.info("listOfVerifyInfo: "+listOfVerifyInfo);
		         StringBuilder sb=new StringBuilder();
				 if(listOfVerifyInfo != null) {
					  int numberVerifyInfo = listOfVerifyInfo.size();
					  sb.append("numberVerifyInfo: "+numberVerifyInfo);
					  
					  for(int indiceVerifyInfo = 0; indiceVerifyInfo < numberVerifyInfo; indiceVerifyInfo++) {
					      VerifyInfoDto verifyInfo = listOfVerifyInfo.get(indiceVerifyInfo);
					      sb.append("\nverifyInfo: "+verifyInfo);
					      sb.append("\nenvelope compliance value : "+verifyInfo.getEnvelopeCompliance());
					      sb.append("\nenvelope compliance code value : "+verifyInfo.getEnvelopeComplianceCode());
					      sb.append("\nenvelope compliance info value : "+verifyInfo.getEnvelopeComplianceInfo());
					      sb.append("\nerror value : "+verifyInfo.getError());
					      sb.append("\nerror msg value : "+verifyInfo.getErrorMsg());
					      sb.append("\nhex error code value : "+verifyInfo.getHexErrorCode());
					      msgErroreDosign="VerifyInfo error: "+verifyInfo.getError()+" ,VerifyInfo errorMsg:"+verifyInfo.getErrorMsg();
					      codiceErroreDosign=verifyInfo.getHexErrorCode();
					      sb.append("\ninvalid sign count value : "+verifyInfo.getInvalidSignCount());
					      sb.append("\nsigner count value : "+verifyInfo.getSignerCount());
					      sb.append("\ndata value : "+verifyInfo.getData());
					      
					      ResultCheckMessageDigest resultCheckMessageDigest= checkMessageDigest(idDocIndex ,verifyInfo.getData());
					      if(!resultCheckMessageDigest.isResult()){
					    	  logDoSign(idUtente,idDocIndex,"N",VERIFY,(System.currentTimeMillis()-start),resultCheckMessageDigest.getMessage(),null,ERR_M_DIG);
					    	  return false;
					      }else{
					    	  logDoSign(idUtente,idDocIndex,"S",VERIFY,(System.currentTimeMillis()-start),resultCheckMessageDigest.getMessage(),null, null);
					      }
					      
					      
					      
					      
						  List<SignerDto> listOfSigners = verifyInfo.getSigner();
						  List<String> fiscalCodes=new ArrayList<String>();
						  if(listOfSigners != null) {
						     int numberSigners = listOfSigners.size();
						     sb.append("\nnumberSigners: "+numberSigners);
		 					 for(int indiceSigner = 0; indiceSigner < numberSigners; indiceSigner++) {
								SignerDto signer = listOfSigners.get(indiceSigner);
								sb.append("\nsigner: "+signer);
								sb.append("\nigner.getSubjectDn(): "+signer.getSubjectDn());
								sb.append("\nsigner.getCertificateStatus(): "+signer.getCertificateStatus());
								sb.append("\nsigner.getCertificateStatusCode(): "+signer.getCertificateStatusCode());
								sb.append("\nsigner.getCertificateStatusDescription(): "+signer.getCertificateStatusDescription());
								sb.append("\nsigner.getCertificateStatusInfo(): "+signer.getCertificateStatusInfo());
								sb.append("\nsigner.getError(): "+signer.getError());
								sb.append("\nsigner.getErrorMsg(): "+signer.getErrorMsg());
								sb.append("\nsigner.getVerificationDate(): "+signer.getVerificationDate());
								sb.append("\nsigner.getFiscalCode(): "+signer.getFiscalCode());
								String fiscalCode = signer.getFiscalCode();
								
								if(fiscalCode.length() > 16)
									fiscalCode = fiscalCode.substring(3);
								
								sb.append("\nfiscalCode --> "+fiscalCode);
								fiscalCodes.add(fiscalCode);
							 }	
						  }
						  boolean foundCf=false;
						  String cfDoSign="";
						  String cfLegRappNotFound="";
						  for (String fiscalCode : fiscalCodes) {
							  cfDoSign+=fiscalCode+" ";
							  if(fiscalCode.equals(cfDelegato)){
								  logger.info("\n\n--- CF MATCH! --"+fiscalCode);
								  listCfMatched.add(fiscalCode);
								  foundCf=true;
							  }
							  for (String cfLegaleRapp : cfLegaleRapps) { 
								 cfLegRappNotFound+=cfLegaleRapp+" ";
								 if(fiscalCode.equals(cfLegaleRapp)   ){
									 logger.info("--- CF MATCH! --"+fiscalCode);
									 listCfMatched.add(fiscalCode);
									 foundCf=true;
								 }
							  }
						  }
						  
						  if(bypassCheckCFDigitalSign==null || bypassCheckCFDigitalSign.equalsIgnoreCase("N")){
							  if(!foundCf){
								  String msgCf="Codice fiscale del firmatario non valido: cf legale rappresentante: "+cfLegRappNotFound;
								  msgCf+=" ,cf delegato: "+cfDelegato;
								  msgCf+=" ,cf restituito/i da doSign: "+cfDoSign;
								  logger.info("Verify KO: "+msgCf+"\n\n\n\n\n\n");
								  logDoSign(idUtente,idDocIndex,"N",VERIFY,(System.currentTimeMillis()-start),msgCf,null,ERR_CF_FIR);
						    	  return false;
							  }
						  }
						  
					  }
				 }
				 logger.info(sb.toString());
			  }
		    }catch(Exception ex) {
		    	logger.info("System.currentTimeMillis() = "+System.currentTimeMillis()+"; start = "+start);
				  logger.error("KO doSign verify, executed in ms: "+(System.currentTimeMillis()-start),ex);
				  // in case of exception  log the problem but don't modify doc STATUS, leave it 'ACQUISITO' so that can retry the verify later (batch?) 
				  logDoSign(idUtente,idDocIndex,"N",VERIFY,(System.currentTimeMillis()-start),(ex.getMessage()==null?""+ex.getClass():ex.getMessage()),"",ERR_GENERIC);
				  logger.info("\n\n\n\n\n\n");
				  throw new DigitalSignException("");
		}
	    String cfMatched="";
	    for (String cf : listCfMatched) {
	    	cfMatched+=cf+" ";
	    }   
	    if(codiceErroreDosign!=null && codiceErroreDosign.length()>0){
	    	logDoSign(idUtente,idDocIndex,"N",VERIFY,(System.currentTimeMillis()-start), msgErroreDosign,codiceErroreDosign,"");
	    	return false;
	    }else{
	    	logDoSign(idUtente,idDocIndex,"S",VERIFY,(System.currentTimeMillis()-start),"Cf validi: "+cfMatched,"","");
	    }
	    
		return true;
	}
	
	public byte[] timeStamp(Long idUtente, Long idDocIndex,byte[] bytesSignedFile)   {
		   long start=System.currentTimeMillis();
		   byte[] bytesTimeStamped=null;
        DosignServiceDto dosignService = initializeTimeStampingService();
        try {
			   DosignTimestamping timestampingService = DosignServiceClient.getTimestampingService(dosignService);
			   logger.info("DosignServiceClient.getTimestampingService ms: "+(System.currentTimeMillis()-start));
			   TimestampingParameterDto timestampingParameter=new TimestampingParameterDto();
			   timestampingParameter.setData(bytesSignedFile);
			   timestampingParameter.setHashAlgorithm(5);
			   timestampingParameter.setEncoding(1);
			   timestampingParameter.setVerificationType(1);
			   timestampingParameter.setProfileType(0);
			   timestampingParameter.setCustomerInformation(null);
			   timestampingParameter.setCustomerTsa("TsaInfocert");
			   timestampingParameter.setHashProtected(false);
			   timestampingParameter.setFileName(null);
			   timestampingParameter.setMediaType(null);
			   TimestampingValueDto timeStampedData = timestampingService.createTimeStampedData(timestampingParameter);
			   bytesTimeStamped=timeStampedData.getEnvelopeArray();
			   logger.info("bytesTimeStamped returned from createTimeStampedData : "+bytesTimeStamped);
			   logDoSign(idUtente, idDocIndex, "S", TIMESTAMP, (System.currentTimeMillis()-start),"OK TIMESTAMP" ,null,null);
        }catch(Exception ex){
     	   logger.error("ERROR doSignManager.timeStamp",ex);
     	   //TODO RIMAPPARE CODICE DI ERRORE DOSIGN SERVICE
     	   logDoSign(idUtente,idDocIndex,"N",TIMESTAMP,(System.currentTimeMillis()-start),(ex.getMessage()==null?""+ex.getClass():"Error: "+ex.getMessage()),"",ERR_GENERIC);
        }
		   return bytesTimeStamped;
	}

	
	private void logDoSign(Long idUtente, Long idDocIndex,
			String flagStatoValidazione,String metodo, long duration, String msg ,String codiceErrore,String idMessaggioAppl ) {
		PbandiTLogValidazFirmaVO pbandiTLogValidazFirmaVO= new PbandiTLogValidazFirmaVO();
		pbandiTLogValidazFirmaVO.setCodiceErrore(codiceErrore);
		pbandiTLogValidazFirmaVO.setDtLog(DateUtil.getSysdate());
		pbandiTLogValidazFirmaVO.setFlagStatoValidazione(flagStatoValidazione);
		pbandiTLogValidazFirmaVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocIndex));
		pbandiTLogValidazFirmaVO.setIdMessaggioAppl(idMessaggioAppl);
		pbandiTLogValidazFirmaVO.setIdUtente(BigDecimal.valueOf(idUtente));
		if(msg!=null && msg.length()>4000 ){
			msg=msg.substring(0,4000); 
		}
		pbandiTLogValidazFirmaVO.setMessaggio(msg);
		pbandiTLogValidazFirmaVO.setMetodo(metodo);
		pbandiTLogValidazFirmaVO.setDuration(BigDecimal.valueOf(duration));
		try {
			logger.info("inserting pbandiTLogValidazFirmaVO duration: "+duration);
			genericDAO.insert(pbandiTLogValidazFirmaVO);
		} catch (Exception e) {
			logger.error("Error logDoSignVerifyServiceCall for idDocIndex :"+idDocIndex,e);
		}
	}
	
	private DosignServiceDto initializeDosignService() {
		logger.info("initializeDosignService(): START");
		DosignServiceDto serviceDosignService = new DosignServiceDto();
		ResourceBundle rB = null;
		try {
			rB = ResourceBundle.getBundle("conf/dosign");
		} catch (Exception e) {
			logger.error("initializeDosignService(): "+e);
		}
		String port= rB.getString("port");
		String server= rB.getString("server");
		logger.info("initializing doSign service with server "+server + " port: "+port);
		serviceDosignService.setServer(server);
		serviceDosignService.setContext("/dosignmanager/DosignSignatureValidation");
		serviceDosignService.setPort(new Integer(port));
		logger.info("initializeDosignService(): END");
		return serviceDosignService;
	}
	
	private VerifyDto initializeVerifyDTO(byte[] bytesSignedFile) {
		VerifyDto verifyDto = new VerifyDto();
		 verifyDto.setSignedDataMimeType("octet-stream");
		 verifyDto.setProfileType( getProfileType());
		 verifyDto.setVerificationType( getVerificationType());
		 verifyDto.setExtractData(true);// to check message_digest against original file
		 verifyDto.setEnvelopeArray(bytesSignedFile);
		return verifyDto;
	}
	
	private int getProfileType() {		
		//Long profileTypeDoSign = getConfigurationManager().getConfiguration().getProfileTypeDoSign();		
		Long profileTypeDoSign = findCostanteLong("conf.profileTypeDoSign");		
		if(profileTypeDoSign==null){
			 logger.info("profileTypeDoSign not configured, assume 1");
			 profileTypeDoSign=Long.valueOf(1);
		}else{
			 logger.info("profileTypeDoSign configured on db:"+profileTypeDoSign);
		}
		return profileTypeDoSign.intValue();
	}
	
	private int getVerificationType() {		
		//Long verificationTypeDoSign = getConfigurationManager().getConfiguration().getVerificationTypeDoSign();
		Long verificationTypeDoSign = findCostanteLong("conf.verificationTypeDoSign");
		 if(verificationTypeDoSign==null){
			 logger.info("verificationTypeDoSign not configured, assume 1");
			 verificationTypeDoSign=Long.valueOf(1);
		 }else{
			 logger.info("verificationTypeDoSign configured on db:"+verificationTypeDoSign);
		 }
		 return verificationTypeDoSign.intValue();
	}
	
	private ResultCheckMessageDigest checkMessageDigest( Long idDocIndex,byte[] bytesFromSignedFile ) {
		//String bypassCheckCFDigitalSign = getConfigurationManager().getConfiguration().getBypassCheckCFDigitalSign();
		String bypassCheckCFDigitalSign = findCostante("conf.bypassCheckCFDigitalSign");
		ResultCheckMessageDigest resultCheckMessageDigest=new ResultCheckMessageDigest();
		 if(bypassCheckCFDigitalSign==null || bypassCheckCFDigitalSign.equalsIgnoreCase("S")){
			 resultCheckMessageDigest.setResult(true);
			 return resultCheckMessageDigest;
		 }
			 
		 logger.info("checkMessageDigest for idDocIndex"+idDocIndex);
		 PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocIndex));
		 pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		 String messageDigestDoSign = DigestUtils.shaHex(bytesFromSignedFile);
		 String messageDigestDB=pbandiTDocumentoIndexVO.getMessageDigest();
		 logger.info("messageDigest from original file on db: "+messageDigestDB+"\nDigestUtils.shaHex(bytesFromSignedFile) ----> "+messageDigestDoSign);
		 if(messageDigestDB!=null && messageDigestDB.equalsIgnoreCase(messageDigestDoSign)){
			 logger.info("checkMessageDigest for idDocIndex: "+idDocIndex+" ---> OK\n\n\n\n");
			 resultCheckMessageDigest.setMessage("message_digest corretto.originale: "+messageDigestDB+" , da doSign:"+messageDigestDoSign);
			 resultCheckMessageDigest.setResult(true);
			 return resultCheckMessageDigest;
		 } else{
			 logger.info("checkMessageDigest for idDocIndex: "+idDocIndex+" ---> KO\n\n\n\n");
			 resultCheckMessageDigest.setResult(false);
			 resultCheckMessageDigest.setMessage("message_digest non corretto.originale: "+messageDigestDB+" , da doSign:"+messageDigestDoSign);
			 return resultCheckMessageDigest;
		 }
	}
	
	// Jira PBANDI-2626
	public boolean testResources() {
		try {
			logger.info("Eseguo initializeDosignService()");
			DosignServiceDto dosignService = initializeDosignService();
			logger.info("Eseguo DosignServiceClient.getSignatureValidationService()");
			DosignSignatureValidation signatureValidationService = DosignServiceClient.getSignatureValidationService(dosignService);
			logger.info("Eseguo SignatureValidationService.testResources()");
			boolean esito = signatureValidationService.testResources();
			logger.info("Esito = "+esito);
			if (!esito)
				return false;
		} catch (Exception e) {
			logger.error("Errore su SignatureValidationService: "+e);
			return false;
		}
		
		try {
			logger.info("Eseguo initializeTimeStampingService()");
			DosignServiceDto dosignService = this.initializeTimeStampingService();
			logger.info("Eseguo DosignServiceClient.getTimestampingService()");
			DosignTimestamping timestampingService = DosignServiceClient.getTimestampingService(dosignService);
			logger.info("Eseguo TimestampingService.testResources()");
			boolean esito = timestampingService.testResources();
			logger.info("Esito = "+esito);
			if (!esito)
				return false;
		} catch (Exception e) {
			logger.error("Errore su TimestampingService: "+e);
			return false;
		}
		
		return true;
	}
	
	private DosignServiceDto initializeTimeStampingService() {
		DosignServiceDto serviceDosignService = new DosignServiceDto();
		ResourceBundle rB = ResourceBundle.getBundle("conf/dosign");
		String port= rB.getString("port");
		String server= rB.getString("server");
		logger.info("initializing doSign service with server "+server + " port: "+port);
		serviceDosignService.setServer(server);
		serviceDosignService.setContext("/dosignmanager/DosignTimestamping");
		serviceDosignService.setPort(new Integer(port));
		return serviceDosignService;
	}
	
	private String findCostante(String attributo) {
		PbandiCCostantiVO vo = new PbandiCCostantiVO();
    	vo.setAttributo(attributo);
    	vo = genericDAO.findSingleOrNoneWhere(vo);
    	if (vo == null)
    		return null;
    	else
    		return vo.getValore();
	}
	
	private Long findCostanteLong(String attributo) {
		PbandiCCostantiVO vo = new PbandiCCostantiVO();
    	vo.setAttributo(attributo);
    	vo = genericDAO.findSingleOrNoneWhere(vo);
    	if (vo == null || StringUtils.isBlank(vo.getValore()))
    		return null;
    	else
    		return new Long(vo.getValore());
	}

}