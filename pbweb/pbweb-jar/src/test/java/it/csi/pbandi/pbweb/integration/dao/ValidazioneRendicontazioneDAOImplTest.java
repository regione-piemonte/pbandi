package it.csi.pbandi.pbweb.integration.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.EsitoRicercaFornitori;
import it.csi.pbandi.pbweb.dto.Fornitore;
import it.csi.pbandi.pbweb.dto.QualificaFormDTO;
import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.EsitoVerificaOperazioneMassivaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InizializzaValidazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.RigaRicercaDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.FornitoreDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.impl.ValidazioneRendicontazioneDAOImpl;
import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiDiSpesaValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.OperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ProseguiChiudiValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.RichiediIntegrazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaQualificheRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaParzialmenteDocumentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaOperazioneMassivaRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;


@RunWith(PbwebJunitClassRunner.class)
public class ValidazioneRendicontazioneDAOImplTest extends TestBaseService {
	
	Long idUtente = new Long(21957);
	String idIride = "idIrideFinto";

	protected static Logger LOG = Logger.getLogger(ValidazioneRendicontazioneDAOImplTest.class);

	@Before()
	public void before() {
		LOG.info("[ValidazioneRendicontazioneDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[ValidazioneRendicontazioneDAOImplTest::after]START-STOP");
	}

	@Autowired
	ValidazioneRendicontazioneDAOImpl validazioneRendicontazioneDAOImpl;
	
	@Test
	public void cercaDocumentiDiSpesaValidazione() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::cercaDocumentiDiSpesaValidazione] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			CercaDocumentiDiSpesaValidazioneRequest req = new CercaDocumentiDiSpesaValidazioneRequest();
			//req.setIdDichiarazioneSpesa(new Long(27098));		// ds intermedia con 1 doc
			req.setIdDichiarazioneSpesa(new Long(26758));		// ds finale con molti doc 'in validazione'
			//ArrayList<String> statiDocumento = new ArrayList<String>();
			//statiDocumento.add("2");
			//req.setStatiDocumento(statiDocumento);
			req.setIdTipoDocumentoSpesa(new Long(1));
			
			ArrayList<RigaRicercaDocumentiDiSpesaDTO> result = validazioneRendicontazioneDAOImpl.cercaDocumentiDiSpesaValidazione(req, idUtente, idIride);
			
			LOG.info("\n\nresult.size = "+result.size());
			//for (RigaRicercaDocumentiDiSpesaDTO p : result)
			//		LOG.info("\n\n"+p.toString());	
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void validaParzialmenteDocumento() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::validaParzialmenteDocumento] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			// UNIVERSITA TORINO -bando SALUTE - prog 0320000030 - ds 26270 - documento cedolino di BIGHARAZ
			ValidaParzialmenteDocumentoRequest req = new ValidaParzialmenteDocumentoRequest();
			req.setIdBandoLinea(new Long(268));
			req.setIdDichiarazioneDiSpesa(new Long(26270));
			req.setIdDocumentoDiSpesa(new Long(589957));
			req.setIdProgetto(new Long(19014));
			req.setNoteValidazione("nota di prova per validazione parziale");
						
			RigaValidazioneItemDTO item1 = new RigaValidazioneItemDTO();
			item1.setIdRigoContoEconomico(new Long(415327));
			item1.setImportoValidatoVoceDiSpesa(new Double(1259.03));
			
			RigaValidazioneItemDTO item2 = new RigaValidazioneItemDTO();
			item2.setIdRigoContoEconomico(new Long(415329));
			item2.setImportoValidatoVoceDiSpesa(new Double(31814.03));
			
			ArrayList<RigaValidazioneItemDTO> lista = new ArrayList<RigaValidazioneItemDTO>();
			lista.add(item1);
			lista.add(item2);
			req.setRigheValidazioneItem(lista);
			
			EsitoDTO esito = validazioneRendicontazioneDAOImpl.validaParzialmenteDocumento(req, idUtente, idIride);
			
			LOG.info("\n\nESITO = "+esito.getEsito()+" - MSG = "+esito.getMessaggio());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void reportDettaglioDocumentoDiSpesa() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::reportDettaglioDocumentoDiSpesa] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			Long idDichiarazioneDiSpesa = new Long(26270);
			byte[] byteFile = validazioneRendicontazioneDAOImpl.reportDettaglioDocumentoDiSpesa(idDichiarazioneDiSpesa, idUtente, idIride);
			
			LOG.info("\n\nbyteFile.size = "+byteFile.length);
			
			FileOutputStream fileOuputStream = new FileOutputStream("C:\\temp\\report.xls");
			try {
				fileOuputStream.write(byteFile);
			} catch (IOException e) {
				LOG.error("IOException: ", e);
			}
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void verificaOperazioneMassiva() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::verificaOperazioneMassiva] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			VerificaOperazioneMassivaRequest req = new VerificaOperazioneMassivaRequest();
			req.setTutti(true);
			req.setOperazione(Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE);
			//req.setIdDichiarazioneDiSpesa(new Long(26270));
			req.setIdDichiarazioneDiSpesa(new Long(27098));
			req.setIdBandoLinea(new Long(268));
			
			EsitoVerificaOperazioneMassivaDTO e = validazioneRendicontazioneDAOImpl.verificaOperazioneMassiva(req, idUtente, idIride);
			
			LOG.info(e.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		 */
	}
	
	@Test
	public void operazioneMassiva() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::perazioneMassiva] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			OperazioneMassivaRequest req = new OperazioneMassivaRequest();
			req.setOperazione(Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE);
			req.setIdDichiarazioneDiSpesa(new Long(26270));
			
			ArrayList<Long> idDocSpesa = new ArrayList<Long>();
			idDocSpesa.add(new Long(590276));
			idDocSpesa.add(new Long(589960));
			req.setIdDocumentiDiSpesaDaElaborare(idDocSpesa);
			
			EsitoDTO e = validazioneRendicontazioneDAOImpl.operazioneMassiva(req, idUtente, idIride);
			
			LOG.info(e.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void richiediIntegrazione() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::richiediIntegrazione] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			RichiediIntegrazioneRequest req = new RichiediIntegrazioneRequest();
			req.setIdDichiarazioneDiSpesa(new Long(26704));
			req.setNoteIntegrazione("Nota JUnit");
			
			EsitoDTO e = validazioneRendicontazioneDAOImpl.richiediIntegrazione(req, idUtente, idIride);
			
			LOG.info(e.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void proseguiChiudiValidazione() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::proseguiChiudiValidazione] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			ProseguiChiudiValidazioneRequest req = new ProseguiChiudiValidazioneRequest();
			req.setIdDichiarazioneDiSpesa(new Long(26270));
			req.setIdProgetto(new Long(19014));
			req.setDsIntegrativaConsentita(true);
			req.setNoteChiusura("Nota JUnit");
			
			EsitoDTO e = validazioneRendicontazioneDAOImpl.proseguiChiudiValidazione(req, idUtente, idIride);
			
			LOG.info(e.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void inizializzaValidazione() {
		/*
		String prf = "[ValidazioneRendicontazioneDAOImplTest::inizializzaValidazione] ";
    	LOG.info("\n\n"+prf+"START"+"\n\n");		
		try {
			
			// Beneficiario	ALPLAST SRL
			// bando POR-FESR 14-20 ASSE IV - IV.4B.2.1 - EFFICIENZA ENERGETICA E FONTI RINNOVABILI NELLE IMPRESE (IV4B21_ENERGIA_IMPRESE) PERCETTORI
			// progetto 0307000155
			Long idDichiarazioneDiSpesa = new Long(25003);
			Long idProgetto = new Long(18430);
			Long idSoggetto = new Long(2130090);
			String codiceRuolo = "ADG-IST-MASTER";
			
			InizializzaValidazioneDTO e = validazioneRendicontazioneDAOImpl.inizializzaValidazione(
					idDichiarazioneDiSpesa, idProgetto, idSoggetto, codiceRuolo, idUtente, idIride);
			
			LOG.info(e.toString());
						
		}catch(Exception e) {
			e.printStackTrace();
		}		
		LOG.info("\n\n"+prf+"END"+"\n\n");
		*/
	}
	
	@Test
	public void dettaglioDocumentoDiSpesaPerValidazione() {
		/*
		Long idDichiarazioneDiSpesa = new Long(27000);
		Long idDocumentoDiSpesa = new Long(608927);
		Long idProgetto = new Long(22931);
		Long idBandoLinea = new Long(388);
	
		try {
			validazioneRendicontazioneDAOImpl.dettaglioDocumentoDiSpesaPerValidazione(idDichiarazioneDiSpesa, idDocumentoDiSpesa, idProgetto, idBandoLinea, idUtente, idIride);
		} catch (Exception e) {
			LOG.error(e);
		}
		*/
	}
	
}	
