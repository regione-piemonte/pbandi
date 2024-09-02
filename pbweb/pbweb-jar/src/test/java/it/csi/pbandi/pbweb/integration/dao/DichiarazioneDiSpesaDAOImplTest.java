/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;

//import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.integration.dao.impl.DichiarazioneDiSpesaDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.DoSignManager;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.DosignException_Exception;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.DosignSignatureValidation;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.DosignSignatureValidation_Service;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.VerifyDto;
import it.doqui.dosign.dosign.business.session.dosign.signaturevalidation.VerifyReportDto;


@RunWith(PbwebJunitClassRunner.class)
public class DichiarazioneDiSpesaDAOImplTest extends TestBaseService {
	
	Long idUtente = new Long(25846);
	String idIride = "idIrideFinto";
	Long idSoggetto = new Long(2130091);

	protected static Logger LOG = Logger.getLogger(DichiarazioneDiSpesaDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[DichiarazioneDiSpesaDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[DichiarazioneDiSpesaDAOImplTest::after]START-STOP");
	}

	@Autowired
	DichiarazioneDiSpesaDAOImpl dichiarazioneDiSpesaDAOImpl;
	
	@Autowired
	DocumentoManager documentoManager;	
	
	@Autowired
	DoSignManager dosignManager;
	
	@Test
	public void verificaDichiarazioneDiSpesa() {
	//[DichiarazioneDiSpesaDAOImpl::inviaDichiarazioneDiSpesa]
		String prf = "[DichiarazioneDiSpesaDAOImplTest::verificaDichiarazioneDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");
    /*
		try {
			
			it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest req = 
					new it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest();
			
			req.setIdProgetto(new Long(20812));
			req.setIdBandoLinea(new Long(295));
			req.setDataLimiteDocumentiRendicontabili(DateUtil.getDataOdierna());
			req.setIdSoggettoBeneficiario(new Long(3550));
			
			//req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTEGRATIVA);
			req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTERMEDIA);
			
					
			it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneVerificaDichiarazioneSpesa 	esito = 
					dichiarazioneDiSpesaDAOImpl.verificaDichiarazioneDiSpesa(req, idUtente, idIride);
			
			for (String s : esito.getMessaggi())
				LOG.info("\n\nMessaggio = "+s);
			
			LOG.info("\n\nNum record: "+esito.getDocumentiDiSpesa().size());
			for (it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa d : esito.getDocumentiDiSpesa())
				LOG.info("\n\nTipoDocumento = "+d.getDescrizioneTipologiaDocumento()+"; motivazione = "+d.getMotivazione());
						
		}catch(Exception e) {
			e.printStackTrace();
		}
	*/
		LOG.info("\n\n"+prf+"END"+"\n\n");

	}
	
	@Test
	public void disassociaAllegatoDichiarazioneDiSpesa() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::disassociaAllegatoDichiarazioneDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			

			long idDocumentoIndex = 230433;
			Long idDichiarazioneDiSpesa = null;
			long idProgetto = 19205;
												
			EsitoDTO esito = dichiarazioneDiSpesaDAOImpl.disassociaAllegatoDichiarazioneDiSpesa(idDocumentoIndex, idDichiarazioneDiSpesa, idProgetto, idUtente, idIride);
			
			LOG.info("\n\nesito = "+esito.getEsito());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void associaAllegatiADichiarazioneDiSpesa() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::associaAllegatiADichiarazioneDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {			
			AssociaFilesRequest req = new AssociaFilesRequest();
			req.setElencoIdDocumentoIndex(new ArrayList<Long>());
			req.getElencoIdDocumentoIndex().add(new Long(230433));
			req.setIdTarget(null);		// sarebbe l'id dichiarazione di spesa
			req.setIdProgetto(new Long(19205));
			
			EsitoAssociaFilesDTO esito = dichiarazioneDiSpesaDAOImpl.associaAllegatiADichiarazioneDiSpesa(req, idUtente);
			LOG.info(prf+esito.toString());			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void allegatiDichiarazioneDiSpesaPerIdProgetto() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::allegatiDichiarazioneDiSpesaPerIdProgetto] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {			
			
			Long idProgetto = new Long(19253);
			
			// String codiceTipoDichiarazioneDiSpesa = Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE;
			String codiceTipoDichiarazioneDiSpesa = Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTEGRATIVA;
			
			ArrayList<DocumentoAllegatoDTO> result = dichiarazioneDiSpesaDAOImpl.allegatiDichiarazioneDiSpesaPerIdProgetto(codiceTipoDichiarazioneDiSpesa, idProgetto, idUtente, idIride);
			
			for (DocumentoAllegatoDTO d : result)
				LOG.info("nome = "+d.getNome());
							
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void inserisciDocumentoPagamentiDichiarazione1 () {
		/*	
		String prf = "[DichiarazioneDiSpesaDAOImplTest::inserisciDocumentoPagamentiDichiarazione] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			BigDecimal idDichiarazione = new BigDecimal(25732);
			
			BigDecimal idDocumento = new BigDecimal(575181);
			
			ArrayList<BigDecimal> idPagamenti = new ArrayList<BigDecimal>();
			idPagamenti.add(new BigDecimal(111));
			idPagamenti.add(new BigDecimal(222));
			
			dichiarazioneDiSpesaDAOImpl.inserisciDocumentoPagamentiDichiarazione1 (idDichiarazione, idDocumento, idPagamenti);
				
		}catch(Exception e) {
			LOG.info("ERRORE: "+e);
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/		
	}
	
	@Test
	public void anteprimaDichiarazioneDiSpesa() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::anteprimaDichiarazioneDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			// Comune di Aramengo
			AnteprimaDichiarazioneDiSpesaRequest req = new AnteprimaDichiarazioneDiSpesaRequest();
			req.setIdBandoLinea(new Long(272));
			req.setIdProgetto(new Long(19268));
			req.setIdSoggetto(new Long(1134));			
			req.setDataLimiteDocumentiRendicontabili(DateUtil.getDataOdierna());
			req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTERMEDIA);
			req.setIdRappresentanteLegale(new Long(2139598));

			// Universita Torino
			AnteprimaDichiarazioneDiSpesaRequest reqUniv = new AnteprimaDichiarazioneDiSpesaRequest();
			reqUniv.setIdBandoLinea(new Long(268));
			reqUniv.setIdProgetto(new Long(19014));
			reqUniv.setIdSoggetto(new Long(2130090));		
			reqUniv.setIdSoggettoBeneficiario(new Long(3550));
			reqUniv.setDataLimiteDocumentiRendicontabili(DateUtil.getDataOdierna());
			reqUniv.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE);
			reqUniv.setIdRappresentanteLegale(new Long(6422));
			reqUniv.setNote("Nota finta");
			reqUniv.setImportoRichiestaSaldo(new Double(1234));
			
			// Universita Torino   -   POR-FESR 14-20 ASSE I - I.1B.1.1 - IERRE2    caso con 29 documenti di progetto.
			AnteprimaDichiarazioneDiSpesaRequest req = new AnteprimaDichiarazioneDiSpesaRequest();
			req.setIdBandoLinea(new Long(247));
			req.setIdProgetto(new Long(18874));
			req.setIdSoggetto(new Long(3550));			
			req.setDataLimiteDocumentiRendicontabili(DateUtil.getDataOdierna());
			req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTERMEDIA);
			req.setIdRappresentanteLegale(new Long(2115919));
			
			EsitoOperazioneAnteprimaDichiarazioneSpesa esito;
			esito = dichiarazioneDiSpesaDAOImpl.anteprimaDichiarazioneDiSpesa(reqUniv, idUtente, idIride);
			
			LOG.info("Esito = "+esito.getEsito()+"; NomeFile = "+esito.getNomeFile()+"; bytes = "+((esito.getPdfBytes() == null) ? null : esito.getPdfBytes().length));
			FileOutputStream fileOuputStream;
			try {
				fileOuputStream = new FileOutputStream("C:\\temp\\"+esito.getNomeFile());
				try {
					fileOuputStream.write(esito.getPdfBytes());
				} catch (IOException e) {
					LOG.error("IOException: ", e);
				}
			} catch (FileNotFoundException e) {
				LOG.error("FileNotFoundException: ", e);
			}
	        
				
		}catch(Exception e) {
			LOG.info("ERRORE: "+e);
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void inviaDichiarazioneDiSpesa() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::inviaDichiarazioneDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			// Comune di Aramengo
			InviaDichiarazioneDiSpesaRequest req = new InviaDichiarazioneDiSpesaRequest();
			req.setIdBandoLinea(new Long(272));
			req.setIdProgetto(new Long(19268));
			req.setIdSoggetto(new Long(1134));			
			req.setDataLimiteDocumentiRendicontabili(DateUtil.getDataOdierna());
			//req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTERMEDIA);
			req.setCodiceTipoDichiarazioneDiSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_INTEGRATIVA);
			req.setIdRappresentanteLegale(new Long(2139598));
			
			EsitoOperazioneInviaDichiarazioneDTO esito;
			esito = dichiarazioneDiSpesaDAOImpl.inviaDichiarazioneDiSpesa(req, idUtente, idIride);
			
			if (esito.getDichiarazioneDTO() != null) {
				LOG.info("DichiarazioneDTO: Esito = "+esito.getEsito()+"; NomeFile = "+esito.getDichiarazioneDTO().getNomeFile()+"; idDocIndex = "+esito.getDichiarazioneDTO().getIdDocIndex());
			} 
			if (esito.getDichiarazionePiuGreenDTO() != null) {
				LOG.info("DichiarazionePiuGreenDTO: Esito = "+esito.getEsito()+"; NomeFile = "+esito.getDichiarazionePiuGreenDTO().getNomeFile()+"; idDocIndex = "+esito.getDichiarazionePiuGreenDTO().getIdDocIndex());
			}
				
		}catch(Exception e) {
			LOG.info("ERRORE: "+e);
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void salvaFileFirmato() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::salvaFileFirmato] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");	
    	
		byte[] bytes = null;
		try {
			File file = new File("C:\\temp\\aaa-AR1.pdf.p7m");
			try {
				bytes = FileUtils.readFileToByteArray(file);
			} catch (IOException e) {
				LOG.error("IOException: ", e);
			}
		} catch (Exception e) {
			LOG.error("Lettura file fallita: ", e);			
		}
		
		String nomeFile = "aaa-AR1.pdf.p7m";
		Long idDocumentoIndex = new Long(230598);
		
		boolean esito = false;
		try {
			esito = documentoManager.salvaFileFirmato(bytes, nomeFile, idDocumentoIndex, idUtente);
		} catch (InvalidParameterException e) {
			LOG.error("InvalidParameterException: ", e);
		} catch (Exception e) {
			LOG.error("Exception: ", e);
		}
		LOG.info("ESITO = "+esito);
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void verificaFirmaDigitale() {
		
		String prf = "[DichiarazioneDiSpesaDAOImplTest::verificaFirmaDigitale] ";
    	LOG.info(prf+"START");	
    	
		Long idDocumentoIndex = new Long(343520);
		boolean esito = false;
		
		Long idDocIndex = 0L;
		String cfDelegato = "";
		byte[] bytesSignedFile = null;
		List<String> cfLegaleRapps = new ArrayList<String>();
		
		/*
		String wsdlLocation = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_DOSIGN_SignatureValidation/1.0";
		
		try {
			URL url = new URL(wsdlLocation);
			DosignSignatureValidation_ServiceLocator dosignLocator = new DosignSignatureValidation_ServiceLocator();
			DosignSignatureValidation_PortType dosignWS = dosignLocator.getDosignSignatureValidationBeanPort(url);
		
			//applications: PBANDI-REGP
			String CONSUMER_KEY = <CONSUMER_KEY>;
			String CONSUMER_SECRET = <CONSUMER_SECRET>;
			String OAUTH_URL = "http://tst-<da sostiure con il VH di esposizione API>/api/token";
			
			OauthHelper oauthHelper = new OauthHelper(OAUTH_URL,CONSUMER_KEY,CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauthHelper= "+oauthHelper);
			
			Hashtable<String, String> headers = new Hashtable<String, String>();
			headers.put("Authorization", "Bearer "+	oauthHelper.getToken() );
			
			org.apache.axis.client.Stub s = (Stub)dosignWS;
			s._setProperty(HTTPConstants.REQUEST_HEADERS, headers);
			LOG.debug(prf + " dosignWS bearer iniettato negli headers");
			
			boolean tst = dosignWS.testResources();
			LOG.info(prf+"tst="+tst);
		
		} catch (DosignException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
		
//		try {
			Boolean v1 = dosignManager.testResources();
			LOG.info(prf+"v1="+v1);
			
			//Boolean v = dosignManager.verify(idDocumentoIndex, cfLegaleRapps, cfDelegato, idDocIndex, bytesSignedFile);
//			LOG.info(prf+"v="+v);	

//		} catch (DigitalSignException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/*
		try {
			esito = dichiarazioneDiSpesaDAOImpl.verificaFirmaDigitale(idDocumentoIndex, idUtente, idIride);
		} catch (InvalidParameterException e) {
			LOG.error("InvalidParameterException: ", e);
		} catch (Exception e) {
			LOG.error("Exception: ", e);
		}
		*/
//		LOG.info("ESITO = "+esito);
		LOG.info(prf+"END");
		
	}
	
	@Test
	public void verificaFirma() {
		String prf = "[DichiarazioneDiSpesaDAOImplTest::verificaFirma] ";
    	LOG.info(prf+"START");	
   /*
    	 try {
			DosignSignatureValidation dosignWS = inizializzaDosignSignatureCXF();
			LOG.info(prf+"dosignWS FATTO");	
			
	    	 
//	    	 InputStream data = DichiarazioneDiSpesaDAOImplTest.class.getResourceAsStream("/it/csi/pbandi/pbweb/integration/dao/DichiarazioneDiSpesa_27315_14032022.pdf.p7m");
	    	 InputStream data = DichiarazioneDiSpesaDAOImplTest.class.getResourceAsStream("/it/csi/pbandi/pbweb/integration/dao/DichiarazioneDiSpesa_27316_16032022.pdf.p7m");
	    	 LOG.info(prf+"data= "+data);	
	    	 LOG.info(prf+"data.available= "+data.available());	
	    	 //byte[] bytesSignedFile = new byte[data.available()];
	    	 byte[] bytesSignedFile = new byte[(int) data.available()];
	    	 DataInputStream dis = new DataInputStream(data);
	    	 dis.readFully(bytesSignedFile);
	    	 
	    	 LOG.info(prf+"bytesSignedFile= "+bytesSignedFile);	
	    	 
			VerifyDto verifyDto = initializeVerifyDTO(bytesSignedFile);
			LOG.info(prf+"verifyDto FATTO");	
			
//			LOG.info(prf+"testResources= " + dosignWS.testResources());
			
			 VerifyReportDto verifyReport = dosignWS.verify(verifyDto);

		} catch (ClassNotFoundException  e) {
			LOG.error(prf+">>>>>> ClassNotFoundException " + e.getMessage());
			e.printStackTrace();
		} catch ( MalformedURLException e) {
			LOG.error(prf+">>>>>> MalformedURLException " + e.getMessage());
			e.printStackTrace();
		} catch (DosignException_Exception e) {
			LOG.error(prf+">>>>>> DosignException_Exception " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(prf+">>>>>> IOException " + e.getMessage());
			e.printStackTrace();
		}
    	 */

    	 
    	 LOG.info(prf+"END");	
	}
	
	private VerifyDto initializeVerifyDTO(byte[] bytesSignedFile) {
		VerifyDto verifyDto = new VerifyDto();
		 verifyDto.setSignedDataMimeType("octet-stream");
		 verifyDto.setProfileType( 1 ) ; //getProfileType());
		 verifyDto.setVerificationType( 1 ); //getVerificationType());
		 verifyDto.setExtractData(true);// to check message_digest against original file
		 verifyDto.setEnvelopeArray(bytesSignedFile);
		 verifyDto.setVerificationScope(1);
		return verifyDto;
	}
	
	private DosignSignatureValidation inizializzaDosignSignatureCXF() throws ClassNotFoundException, MalformedURLException {
		String prf ="[DoSignManager::inizializzaDosignSignatureCXF]";
		LOG.info(prf+"BEGIN");	
		DosignSignatureValidation dosignWS = null;
		
		try {
			
	
			ResourceBundle rB = ResourceBundle.getBundle("conf/dosign");
			String wsdlLocation= rB.getString("dosignSignatureValidationsUrl");
			String CONSUMER_KEY = rB.getString("CONSUMER_KEY");
			String CONSUMER_SECRET = rB.getString("CONSUMER_SECRET");
			String OAUTH_URL = rB.getString("OAUTH_URL");
			LOG.info(prf + " wsdlLocation="+wsdlLocation);
			LOG.info(prf + " CONSUMER_KEY="+CONSUMER_KEY);
			LOG.info(prf + " CONSUMER_SECRET="+CONSUMER_SECRET);
			LOG.info(prf + " OAUTH_URL="+OAUTH_URL);
			
//			URL url = new URL(wsdlLocation);
			URL url = this.getClass().getClassLoader().getResource("wsdl/DOSIGN-Signaturevalidation.wsdl");
			LOG.info(prf + " url="+url);
			
			DosignSignatureValidation_Service ds = new DosignSignatureValidation_Service(url);
			dosignWS = ds.getDosignSignatureValidationBeanPort();
			LOG.info(prf + " dosignWS="+dosignWS);
			
			BindingProvider bp = (BindingProvider) dosignWS;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation);
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(wsdlLocation);
			gwfb.setWrappedInterface(DosignSignatureValidation.class);
			gwfb.setPort(dosignWS);
			dosignWS = null;
			gwfb.setTokenRetryManager(trm);

			Object o = gwfb.create();
			dosignWS = (DosignSignatureValidation) o;

			LOG.debug(prf + " dosignWS= "+dosignWS);
		
		} catch ( ClassNotFoundException e) {
			LOG.error(prf+"ClassNotFoundException "+e.getMessage());
			e.printStackTrace();
			throw e;

		} finally {
			LOG.info(prf+"END");	
		}
		
		return dosignWS;
	}
	
	@Test
	public void salvaTipoAllegati() {
		/*
		String prf = "[DichiarazioneDiSpesaDAOImplTest::salvaTipoAllegati] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");	
    	
		Long idDocumentoIndex = new Long(343520);
		
		boolean esito = false;
		try {
			// Leggo dei tipi allegato.
			Long idBandoLinea = new Long(269);
			String codiceTipoDichiarazioneDiSpesa = "I"; 
			Long idDichiarazione = null;
			Long idProgetto = new Long(19200);
			ArrayList<TipoAllegatoDTO> lista = dichiarazioneDiSpesaDAOImpl.tipoAllegati(idBandoLinea, codiceTipoDichiarazioneDiSpesa, idDichiarazione, idProgetto, idUtente, idIride);
			
			LOG.info("\nSecondo elemento = "+lista.get(1).getDescTipoAllegato()+" - "+lista.get(1).getFlagAllegato());
			LOG.info("\nQuarto  elemento = "+lista.get(3).getDescTipoAllegato()+" - "+lista.get(3).getFlagAllegato());
			
			// Modifico il 2° e 4° elemento
			if (StringUtil.isBlank(lista.get(1).getFlagAllegato()) || 
				lista.get(1).getFlagAllegato().equalsIgnoreCase("N")) 
				lista.get(1).setFlagAllegato("S");
			else
				lista.get(1).setFlagAllegato("N");
			if (StringUtil.isBlank(lista.get(3).getFlagAllegato()) || 
					lista.get(3).getFlagAllegato().equalsIgnoreCase("N")) 
					lista.get(3).setFlagAllegato("S");
				else
					lista.get(3).setFlagAllegato("N");
			
			LOG.info("\nSecondo elemento modificato = "+lista.get(1).getDescTipoAllegato()+" - "+lista.get(1).getFlagAllegato());
			LOG.info("\nQuarto  elemento modificato = "+lista.get(3).getDescTipoAllegato()+" - "+lista.get(3).getFlagAllegato());
			
			// Salvo le modifiche.
			EsitoDTO e = dichiarazioneDiSpesaDAOImpl.salvaTipoAllegati(lista, codiceTipoDichiarazioneDiSpesa, idUtente, idIride);
			LOG.info("\nMessaggio esito = "+e.getMessaggio());
			esito = e.getEsito();
			
			// Rileggo da db.
			ArrayList<TipoAllegatoDTO> lista2 = dichiarazioneDiSpesaDAOImpl.tipoAllegati(idBandoLinea, codiceTipoDichiarazioneDiSpesa, idDichiarazione, idProgetto, idUtente, idIride);
			
			LOG.info("\nSecondo elemento dopo salvataggio = "+lista2.get(1).getDescTipoAllegato()+" - "+lista2.get(1).getFlagAllegato());
			LOG.info("\nQuarto  elemento dopo salvataggio = "+lista2.get(3).getDescTipoAllegato()+" - "+lista2.get(3).getFlagAllegato());
			
		} catch (InvalidParameterException e) {
			LOG.error("InvalidParameterException: ", e);
		} catch (Exception e) {
			LOG.error("Exception: ", e);
		}
		LOG.info("ESITO = "+esito);
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
}	
