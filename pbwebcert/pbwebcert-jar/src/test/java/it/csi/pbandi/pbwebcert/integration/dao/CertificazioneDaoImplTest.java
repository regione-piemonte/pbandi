/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbwebcert.base.PbwebcertJunitClassRunner;
import it.csi.pbandi.pbwebcert.base.TestBaseService;
import it.csi.pbandi.pbwebcert.dto.ProgettoNuovaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BandoLineaDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.BeneficiarioDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.CodiceDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.Documento;
import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.EsitoOperazioni;
import it.csi.pbandi.pbwebcert.dto.certificazione.FiltroRicercaDocumentoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.LineaDiInterventoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaProgettoDTO;
import it.csi.pbandi.pbwebcert.dto.certificazione.StatoPropostaDTO;
import it.csi.pbandi.pbwebcert.exception.CertificazioneException;
import it.csi.pbandi.pbwebcert.integration.dao.impl.CertificazioneDaoImpl;

@RunWith(PbwebcertJunitClassRunner.class)
public class CertificazioneDaoImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(CertificazioneDaoImplTest.class);

	@Before()
	public void before() {
		LOG.info("[AttivitaDAOImplTest::before]START");
	}

	@After
	public void after() {
		LOG.info("[AttivitaDAOImplTest::after]START");
	}

	CertificazioneDaoImpl certificazioneDaoImpl;

	@Test
	public void findProposteCertificazioneTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findProposteCertificazioneTest] ";
		LOG.debug(prf + "START");

		List<String> statiProposta = new ArrayList<>();

		// statiProposta.add("bozza");
		// statiProposta.add("bozza")
		statiProposta.add("bozza");

		PropostaCertificazioneDTO[] propostaCertificazioneDTOs = certificazioneDaoImpl.findProposteCertificazione(null,
				null, statiProposta);
		
		LOG.debug(prf + "END");
	}

	@Test
	public void findLineaDiInterventoFromPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findLineaDiInterventoFromPropostaTest] ";
		LOG.debug(prf + "START");

		List<String> statiProposta = new ArrayList<>();
		statiProposta.add("bozza");

		// ottieni un array di proposte dal servizio
		PropostaCertificazioneDTO[] propostaCertificazioneDTOs = certificazioneDaoImpl.findProposteCertificazione(null,
				null, statiProposta);

		LOG.debug(prf + " size" + propostaCertificazioneDTOs.length);

		List<Long> idLinee = new ArrayList<>();
		//
		for (int i = 0; i < 3; i++) {
			if (!idLinee.contains(propostaCertificazioneDTOs[i].getIdPropostaCertificaz())) {
				idLinee.add(propostaCertificazioneDTOs[i].getIdPropostaCertificaz());
				LOG.debug(prf + "idLineaDiIntervento= " + propostaCertificazioneDTOs[i].getIdPropostaCertificaz());
			}
		}
		LineaDiInterventoDTO[] lineeIntervento = certificazioneDaoImpl.getLineeDiInterventoFromIdLinee(idLinee);
		LOG.debug(prf + "linee di intervento trovate: " + lineeIntervento.length);
		LOG.debug(prf + "END");
	}

	@Test
	public void findPropostaCertificazioneTest() {
		String prf = "[CertificazioneDaoImplTest :: findPropostaCertificazioneTest] ";
		LOG.debug(prf + "START");

		BigDecimal idProposta = new BigDecimal(13);
	}
	
    @Test
    public void findAttivitaLineaInterventoTest() throws Exception {
    	String prf = "[CertificazioneDaoImplTest :: findAttivitaLineaInterventoTest] ";
    	LOG.debug( prf + "START");
    	//List<AttivitaLineaInterventoDTO> attivita = certificazioneDaoImpl.findAttivitaLineaIntervento(5L);
    	assertNotNull(certificazioneDaoImpl.findAttivitaLineaIntervento(5L));
    	assertNotNull(certificazioneDaoImpl.findAttivitaLineaIntervento(null));
    	LOG.debug( prf + "END");
    }
        
    @Test
    public void findAllProgettiTest() {
    	String prf = "[CertificazioneDaoImplTest :: findAllProgettiTest] ";
    	LOG.debug( prf + "START");
    	List<ProgettoDTO> progetti = certificazioneDaoImpl.findAllProgetti(202L, 1036L, null);
    	for (ProgettoDTO p: progetti) {
    		LOG.debug( prf + " idProgetto: "+ p.getIdProgetto() + " codiceProgetto: "+ p.getCodiceProgetto());
    	}
    	//assertNotNull(certificazioneDaoImpl.findAllProgetti(8L, null, null));
    	//assertNotNull(certificazioneDaoImpl.findAllProgetti(8L, 506L, null));
    	   	
    	LOG.debug( prf + "END");
    }
        
    @Test
    public void findAllBeneficiariTest() throws Exception {
    	String prf = "[CertificazioneDaoImplTest :: findAllBeneficiariTest] ";
    	LOG.debug( prf + "START");
    	
    	assertNotNull(certificazioneDaoImpl.findAllBeneficiari(null, 8L));
    	//assertNotNull(certificazioneDaoImpl.findAllProgetti(8L, 506L, null));
    	   	
    	LOG.debug( prf + "END");
    }    
        
        
    @Test
    public void findProgettiPropostaTest() {
    	String prf = "[CertificazioneDaoImplTest :: findProgettiPropostaTest] ";
    	LOG.debug( prf + "START");
    	
    	Long idProgetto = 44L;
    	Long idProposta = 506L;
    	Long idLineaIntervento = 8L;
    	Long idBeneficiario;
    		
    	
    	assertNotNull(certificazioneDaoImpl.findProgettiProposta(idProgetto, idProposta, idLineaIntervento, null));
    	   	
    	LOG.debug( prf + "END");
    }  
//    
    @Test
    public void cancellaAllegatiTest() throws Exception {
	    String prf = "[CertificazioneDaoImplTest :: cancellaAllegatiTest] ";
		LOG.debug( prf + "START");
		List<Documento> documenti = new ArrayList<>();
//		Documento doc = new Documento();
//		doc.setIdDocumentoIndex("229917");
//		doc.setNomeFile("");
//		doc.setTipoDocumento("");
//		documenti.add(doc);
//		
//    	assertEquals(certificazioneDaoImpl.cancellaAllegati(documenti).getEsito(), true);
    	LOG.debug( prf + "END");
    }
     
//   @Test
//   public void allegaFilePropostaTest() {
//  	certificazioneDaoImpl.allegaFileProposta(null)
//   }
      
	@Test
	public void creaIntermediaFinaleTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: creaIntermediaFinaleTest] ";
    	LOG.debug( prf + "START");
    	
		EsitoOperazioni esito = certificazioneDaoImpl.creaIntermediaFinale(21957L);
		assertNotNull(esito);
		LOG.debug(prf + " Esito crea intermedia finale: "+ esito.getMsg());
		
		LOG.debug( prf + "END");
	}
	
	@Test
	public void getAnnoContabileTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: getAnnoContabileTest] ";
    	LOG.debug( prf + "START");
    	
    	List<CodiceDescrizione> codiciDesc = certificazioneDaoImpl.getAnnoContabile();
    	for(CodiceDescrizione codice : codiciDesc) {
    		LOG.debug(prf + " anno contabile: " + codice.getCodice());
    	}
    	LOG.debug( prf + "END");
    	
	}
        
	@Test
	public void getLineeInterventoNormativeTest() {
		String prf = "[CertificazioneDaoImplTest :: getLineeInterventoNormativeTest] ";
    	LOG.debug( prf + "START");
    	List<CodiceDescrizione> codiciDesc = certificazioneDaoImpl.getLineeInterventoNormative(false, "C");
    	for(CodiceDescrizione codice : codiciDesc) {
    		LOG.debug(prf + " " + codice.getCodice());
    	}
    	
    	LOG.debug( prf + "END");
    	
	}
	
	@Test
	public void eseguiEstrazioneCampionamentoTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: eseguiEstrazioneCampionamentoTest] ";
    	LOG.debug( prf + "START");
//    	EsitoGenerazioneReportDTO dto = certificazioneDaoImpl.eseguiEstrazioneCampionamento(57L, 21957L);
//    	assertNotNull(dto);
    	LOG.debug( prf + "END");
    	
	}
	
	@Test
	public void ricercaDocumentiTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: ricercaDocumentiTest] ";
    	LOG.debug( prf + "START");
    	FiltroRicercaDocumentoDTO filtro = new FiltroRicercaDocumentoDTO();
    	//filtro.setIdProposta(19L);
//    	filtro.setIsChecklist(false);
//    	filtro.setIsDichiarazioneFinale(false);
//    	filtro.setIsPropostaDiCertificazione(false);
//    	filtro.setIsRicercaProposta(false);
//    	filtro.setIsRicercaPropostaProgetto(false);
//    	String[] statiProposte = {"07appr", "05open", "09resp", "06annu"};
//    	filtro.setStatiProposte(statiProposte);
//    	List<DocumentoCertificazioneDTO> dto = certificazioneDaoImpl.ricercaDocumenti(filtro);
    	
    	filtro.setIsChecklist(false);
    	filtro.setIsDichiarazioneFinale(false);
    	filtro.setIsPropostaDiCertificazione(false);
    	
    	filtro.setIsRicercaProposta(false);
    	filtro.setIsRicercaPropostaProgetto(false);
    	String[] statiProposte = {"07appr", "05open", "09resp", "06annu"};
    	filtro.setStatiProposte(statiProposte);
    	List<DocumentoCertificazioneDTO> dto = certificazioneDaoImpl.ricercaDocumenti(filtro);
    	
    	for(DocumentoCertificazioneDTO dt: dto) {
    		LOG.debug(prf + " IdDocumentoIndex: "+dt.getIdDocumentoIndex() + " : "+ dt.getNomeDocumento());;
    	}
    	
    	assertNotNull(dto);
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void getElencoReportCampionamentoTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getElencoReportCampionamentoTest] ";
    	LOG.debug( prf + "START");
    
    	List<it.csi.pbandi.pbwebcert.dto.rilevazionicontrolli.ReportCampionamentoDTO> dto = certificazioneDaoImpl.getElencoReportCampionamento(202L, null);
    	LOG.debug(prf + " result: "+dto);;
    	assertNotNull(dto);
    	LOG.debug( prf + "END");
	}
	
	
	@Test
	public void getDettaglioPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getDettaglioPropostaTest] ";
    	LOG.debug( prf + "START");
    
    	PropostaCertificazioneDTO dto = certificazioneDaoImpl.findDettaglioProposta(833701L);
    	LOG.debug(prf + " result: "+dto);;
    	LOG.debug( prf + "END");
	}
	
	
	@Test
	public void getStatiSelezionabiliTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getStatiSelezionabiliTest] ";
    	LOG.debug( prf + "START");
    
    	List<StatoPropostaDTO> dto = certificazioneDaoImpl.getStatiSelezionabili();
    	LOG.debug(prf + " result: "+dto);;
    	LOG.debug( prf + "END");
	}
	@Test
	public void aggiornaStatoPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: aggiornaStatoPropostaTest] ";
    	LOG.debug( prf + "START");
    
    	Integer esito = certificazioneDaoImpl.aggiornaStatoProposta(21957L, 506L, 6L);
    	LOG.debug(prf + " esito: "+esito);;
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void creaAnteprimaPropostaCertificazioneTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: creaAnteprimaPropostaCertificazioneTest] ";
    	LOG.debug( prf + "START");
    
    	//EsitoOperazioni esito = certificazioneDaoImpl.creaAnteprimaPropostaCertificazione(null, null, null);
    	//LOG.debug(prf + " result: "+esito);;
    	LOG.debug( prf + "END");
	}
//	
	@Test
	public void getGestisciPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getGestisciPropostaTest] ";
    	LOG.debug( prf + "START");
    	String nomeBandoLinea = "POR-FESR 14-20 ASSE I - I.1B.1.2 - BANDO POLI_LINEA A";
    	String codiceProgetto = "0311000207";
    	String denominazioneBeneficiario = "INTEGRATED SOLUTIONS SRL";
    	List<ProgettoNuovaCertificazioneDTO> result = certificazioneDaoImpl.getGestisciProposta(1021L, null, null, null, true);
    	
    	for(ProgettoNuovaCertificazioneDTO p: result) {
    		LOG.debug(prf + " codiceProgetto: "+ p.getCodiceProgetto() + " titoloProgetto: "+ p.getTitoloProgetto());
    	}
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void getBandoLineaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getBandoLineaTest] ";
    	LOG.debug( prf + "START");
    	List<BandoLineaDTO> result = certificazioneDaoImpl.getBandoLinea(1021L);
    	
    	for(BandoLineaDTO p: result) {
    		LOG.debug(prf + " nomeBandoLinea: "+ p.getNomeBandoLinea() );
    	}
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void findAllBeneficiariDaPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findAllBeneficiariDaPropostaTest] ";
    	LOG.debug( prf + "START");
    	String nomeBandoLinea = "POR-FESR 14-20 ASSE I - I.1B.1.1 - IERRE2 (MISE DGIAI)";
    	List<BeneficiarioDTO> beneficiari = certificazioneDaoImpl.findAllBeneficiariDaProposta(1021L, nomeBandoLinea);
    	
    	for(BeneficiarioDTO p: beneficiari) {
    		LOG.debug(prf + " denominazioneBeneficiario: "+ p.getDenominazioneBeneficiario());
    	}
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void findAllProgettiDaPropostaTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findAllProgettiDaPropostaTest] ";
    	LOG.debug( prf + "START");
    	String nomeBandoLinea = "POR-FESR 14-20 ASSE I - I.1A.5.1-  INFRA-P";
    	String denominazioneBenef = "SOCIETA' PUBBLICA PER IL RECUPERO ED IL TRATTAMENTO DEI RIFI";
    	List<ProgettoDTO> progetti = certificazioneDaoImpl.findAllProgettiDaProposta(1021L, nomeBandoLinea, denominazioneBenef);
    	
    	for(ProgettoDTO p: progetti) {
    		LOG.debug(prf + " idProgetto: "+ p.getIdProgetto() + " codiceProgetto: "+ p.getCodiceProgetto());
    	}
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void aggiornaImportoNettoTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: aggiornaImportoNettoTest] ";
    	LOG.debug( prf + "START");
    	Long idUtente = 21957L;
    	Long idProposta = 1021L;
    	Long idDettProposta = 1641091L;
    	Double nuovoImporto = 1000.0;
    	Long idProgetto = 19984L;
    	EsitoOperazioni esito = certificazioneDaoImpl.aggiornaImportoNetto(idUtente, idProposta, idDettProposta, nuovoImporto, "test nota", idProgetto);
    	
   		LOG.debug(prf + " esito: "+ esito.getEsito() + " Msg: "+ esito.getMsg());

    	LOG.debug( prf + "END");
	}
	
	@Test
	public void findBandoLineaIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findBandoLineaIntermediaFinaleTest] ";
    	LOG.debug( prf + "START");
    	Long idProposta = 1036L;
    	List<BandoLineaDTO> bandiLinea = certificazioneDaoImpl.findBandoLineaIntermediaFinale(idProposta);
    	for(BandoLineaDTO b: bandiLinea) {
    		LOG.debug(prf + " nomeBandoLinea: "+ b.getNomeBandoLinea() );
    	}
   	
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void findBeneficiariIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findBeneficiariIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
    	Long idProposta = 1036L;
    	String nomeBandoLinea = "POR-FESR 14-20 ASSE I - I.1A.5.1-  INFRA-P";
    	List<BeneficiarioDTO> beneficiari = certificazioneDaoImpl.findBeneficiariIntermediaFinale(idProposta, nomeBandoLinea);
    	for(BeneficiarioDTO b: beneficiari) {
    		LOG.debug(prf + " nomeBeneficiario: "+ b.getDenominazioneBeneficiario());
    	}
   	
    	LOG.debug( prf + "END");
	}
	@Test
	public void findProgettiIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: findProgettiIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
    	Long idProposta = 1036L;
    	List<ProgettoDTO> progetti = certificazioneDaoImpl.findProgettiIntermediaFinale(idProposta, null, null);
    	for(ProgettoDTO b: progetti) {
    		LOG.debug(prf + " idProgetto: "+ b.getIdProgetto() + " codiceProgetto: "+ b.getCodiceProgetto());
    	}
   
    	LOG.debug( prf + "END");
	}
	
	@Test
	public void getGestisciPropostaIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getGestisciPropostaIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
    	Long idProposta = 1036L;
    	List<ProgettoCertificazioneIntermediaFinaleDTO> progetti = certificazioneDaoImpl.getGestisciPropostaIntermediaFinale(idProposta, null, null, null, null);
    	for(ProgettoCertificazioneIntermediaFinaleDTO b: progetti) {
    		LOG.debug(prf + " idProgetto: "+ b.getIdProgetto() + " codiceProgetto: "+ b.getCodiceProgetto());
    	}
   
    	LOG.debug( prf + "END");
	}
//	
	@Test
	public void modificaProgettiIntermediaFinaleTest() throws CertificazioneException, FileNotFoundException, IOException {
		String prf = "[CertificazioneDaoImplTest :: modificaProgettiIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
    	ProgettoCertificazioneIntermediaFinaleDTO dto = new ProgettoCertificazioneIntermediaFinaleDTO();
    	dto.setIdDettPropCertAnnual(12213L);
    	dto.setImportoCertifNettoAnnual(1.0);
    	dto.setColonnaC(0.0);
    	
    	
    	List<ProgettoCertificazioneIntermediaFinaleDTO> progettiCertificazioneIntermediaFinale = new ArrayList<>();
    	progettiCertificazioneIntermediaFinale.add(dto);
    	EsitoOperazioni esito = certificazioneDaoImpl.modificaProgettiIntermediaFinale(progettiCertificazioneIntermediaFinale);
    	LOG.debug(prf + " ESITO: "+ esito.getEsito() + " MSG: "+ esito.getMsg());
    	LOG.debug(prf + " END");
	}
//	
	@Test
	public void chiusuraContiPropostaIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: chiusuraContiPropostaIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
		Long idUtente = 21957L;
		Long idProposta = 1111L;
//		EsitoOperazioni esito = certificazioneDaoImpl.chiusuraContiPropostaIntermediaFinale(idUtente, idProposta);
//		LOG.debug(prf + " ESITO: "+ esito.getEsito() + " MSG: "+ esito.getMsg());
    	LOG.debug(prf + " END");
	}
//	
	@Test
	public void findBandoLineaPerAnteprimaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: findBandoLineaPerAnteprimaTest] ";
		LOG.debug( prf + "START");
		Long idProposta = 1054L;
		
		List<BandoLineaDTO> elencoBandoLinea = certificazioneDaoImpl.findBandoLineaPerAnteprima(idProposta);
		for(BandoLineaDTO b: elencoBandoLinea) {
			LOG.info(prf + "nomeBando: "+ b.getNomeBandoLinea());
		}
		LOG.debug(prf + " END");
	}
	
	@Test
	public void findBeneficiariPerAnteprimaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: findBeneficiariPerAnteprimaTest] ";
		LOG.debug( prf + "START");
		
		Long idProposta = 1054L;
		Long progrBandoLineaIntervento = 13L;
		List<BeneficiarioDTO> beneficiari = certificazioneDaoImpl.findBeneficiariPerAnteprima(idProposta, null);
		for(BeneficiarioDTO b: beneficiari) {
			LOG.info(prf + "beneficiario: "+ b.getDenominazioneBeneficiario());
		}
		LOG.debug(prf + " END");
	}
	
	@Test
	public void findProgettiPerAnteprimaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: findProgettiPerAnteprimaTest] ";
		LOG.debug( prf + "START");
		
		Long idProposta = 1054L;
		Long progrBandoLineaIntervento = 13L;
		Long idBeneficiario = 506L;
//		List<ProgettoDTO> progetti = certificazioneDaoImpl.findProgettiPerAnteprima(idProposta, null, idBeneficiario);
//		for(ProgettoDTO b: progetti) {
//			LOG.info(prf + "idProgetto: "+ b.getIdProgetto() + "codice progetto: "+ b.getCodiceProgetto());
//		}
		LOG.debug(prf + " END");
	}
	@Test
	public void findLineeDiInterventoDisponibiliTest() throws CertificazioneException, FileNotFoundException, IOException {
		String prf = "[CertificazioneDaoImplTest :: findLineeDiInterventoDisponibiliTest] ";
		LOG.debug( prf + "START");
		Long idSoggetto = 2130090L;
		List<LineaDiInterventoDTO> linee = certificazioneDaoImpl.findLineeDiInterventoDisponibili(idSoggetto, null);
		for(LineaDiInterventoDTO b: linee) {
			LOG.info(prf + "descLinea: "+ b.getDescLinea() + "descBreveLinea: "+ b.getDescBreveLinea());
		}
		
		LOG.debug(prf + " END");
	}
	
	@Test
	public void findAntePrimaPropostaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: findAntePrimaPropostaTest] ";
		LOG.debug( prf + "START");
		Long idProposta = 1054L;
		Long progrBandoLineaIntervento = 13L;
		Long idBeneficiario = 506L;
		List<PropostaProgettoDTO> progetti = certificazioneDaoImpl.findAntePrimaProposta(idProposta, null, null, null , null);
		for(PropostaProgettoDTO b: progetti) {
			LOG.info(prf + "descLinea: "+ b.getDescLinea() + "descBreveLinea: "+ b.getDescBreveLinea());
		}
		
		LOG.debug(prf + " END");
	}
	
	@Test
	public void sospendiProgettiPropostaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: sospendiProgettiPropostaTest] ";
		LOG.debug( prf + "START");
		Long[] idPrevProp = new Long[] {2433434L};
		Long idUtente = 21957L;
		EsitoOperazioni esito = certificazioneDaoImpl.sospendiProgettiProposta(idUtente, idPrevProp);
		LOG.info(prf + "esito: "+ esito.getEsito() + "Msg: "+ esito.getMsg());
		
		LOG.debug(prf + " END");
	}
	
	@Test
	public void ammettiProgettiPropostaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: ammettiProgettiPropostaTest] ";
		LOG.debug( prf + "START");
		Long[] idPrevProp = new Long[] {2361228L, 2361950L};
		Long idUtente = 21957L;
		EsitoOperazioni esito = certificazioneDaoImpl.ammettiProgettiProposta(idUtente, idPrevProp);
		LOG.info(prf + "esito: "+ esito.getEsito() + "Msg: "+ esito.getMsg());
		
		LOG.debug(prf + " END");
	}
	
	@Test
	public void accodaPropostaCertificazioneTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: accodaPropostaCertificazioneTest] ";
		LOG.debug( prf + "START");
		Long idUtente = 21957L;
	//EsitoOperazioni esito = certificazioneDaoImpl.accodaPropostaCertificazione(idUtente);
		//LOG.info(prf + "esito: "+ esito.getEsito() + "Msg: "+ esito.getMsg());
		
		LOG.debug(prf + " END");
	}
	
	@Test
	@Transactional
	public void lanciaCreazionePropostaTest() throws CertificazioneException {
		String prf = "[CertificazioneDaoImplTest :: lanciaCreazionePropostaTest] ";
		LOG.debug( prf + "START");
		Long idUtente = 21957L;
		PropostaCertificazioneDTO dto = new PropostaCertificazioneDTO();
		dto.setIsBozza(false);
		dto.setIdPropostaCertificaz(1107L);
		
//		Long[] idLineeIntervento = new Long[] {5L, 57L, 202L};
//		EsitoOperazioni esito1 = certificazioneDaoImpl.accodaPropostaCertificazione(idUtente, dto);
//		EsitoOperazioni esito2 = certificazioneDaoImpl.lanciaCreazioneProposta(idUtente, dto, idLineeIntervento);
//		
//		LOG.info(prf + "esito accoda: "+ esito1.getEsito());
//		LOG.info(prf + "esito lancia: "+ esito2.getEsito() + "Msg: "+ esito1.getMsg());
		LOG.debug(prf + " END");
	}
	@Test
	public void inviaReportPostGestioneTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: inviaReportPostGestioneTest] ";
		LOG.debug( prf + "START");
		Long idUtente = 21957L;
		Long idProposta = 1020L;
		PropostaCertificazioneDTO dto = new PropostaCertificazioneDTO();
		dto.setIdPropostaCertificaz(idProposta);
		
//		Long[] idLineeDiIntervento = new Long[] {5L, 57L, 202L};
//		
//		EsitoOperazioni esito = certificazioneDaoImpl.inviaReportPostGestione(idUtente, dto, idLineeDiIntervento);
//		LOG.info(prf + "esito lancia: "+ esito.getEsito() + "Msg: "+ esito.getMsg());

		LOG.debug(prf + " END");
	}
	
	@Test
	public void aggiornaDatiIntermediaFinaleTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: aggiornaDatiIntermediaFinaleTest] ";
		LOG.debug( prf + "START");
		Long idUtente = 21957L;
		Long idProposta = 1098L;
		
		
//		Long[] idLineeDiIntervento = new Long[] {5L, 57L, 202L};
//		
		//certificazioneDaoImpl.aggiornaDatiIntermediaFinale(idUtente, idProposta);
//		LOG.info(prf + "esito lancia: "+ esito.getEsito() + "Msg: "+ esito.getMsg());

		LOG.debug(prf + " END");
	}
	
	@Test
	public void getContenutoDocumentoByIdTest() throws Exception {
		String prf = "[CertificazioneDaoImplTest :: getContenutoDocumentoByIdTest] ";
		LOG.debug( prf + "START");
		Long idDocumentoIndex = 230149L;
		
		
//		Long[] idLineeDiIntervento = new Long[] {5L, 57L, 202L};
//		
		//EsitoGenerazioneReportDTO esito = certificazioneDaoImpl.getContenutoDocumentoById(idDocumentoIndex);
		//LOG.info(prf + "esito lancia: "+ esito.getEsito() + "Msg: "+ esito.getNomeDocumento());

		LOG.debug(prf + " END");
	}
	
	
  }
