/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

//package it.csi.pbandi.pbwebrce.integration.dao;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbwebrce.base.PbwebrceJunitClassRunner;
//import it.csi.pbandi.pbwebrce.base.TestBaseService;
//
//import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.AffidamentoCheckListDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.DocumentoAllegato;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.FornitoreDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.MotiveAssenzaDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.NormativaAffidamentoDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.TipoAffidamentoDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAggiudicazioneDTO;
//import it.csi.pbandi.pbwebrce.dto.affidamenti.TipologiaAppaltoDTO;
//import it.csi.pbandi.pbwebrce.exception.GestioneAffidamentiException;
//import it.csi.pbandi.pbwebrce.integration.dao.impl.AffidamentiDAOImpl;
//import it.csi.pbandi.pbwebrce.integration.dao.impl.ArchivioFileDAOImpl;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.EsitoGestioneAffidamenti;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ParamInviaInVerificaDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO;
//import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO;
//import it.csi.pbandi.pbwebrce.util.DateUtil;
//
//
//@RunWith(PbwebrceJunitClassRunner.class)
//public class AffidamentiDAOImplTest extends TestBaseService {
//	
//	protected static Logger LOG = Logger.getLogger(AffidamentiDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Before()
//	public void before() {
//		LOG.info("[AttivitaDAOImplTest::before]START");
//	}
//
//	private static UserInfoSec getCurrentUserInfo() {
//		UserInfoSec userInfo = new UserInfoSec();
//		userInfo.setIdUtente(26300L);
//		userInfo.setIdIride("AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16");
//		userInfo.setCodiceRuolo("BEN-MASTER");
//		userInfo.setIdSoggetto(2130090L);
//		userInfo.setRuolo("Utente operatore per tutti i Beneficiari");
//		userInfo.setNome("CSI PIEMONTE");
//		userInfo.setCognome("DEMO 34");		
//		return userInfo;
//	}
//
//	@After
//	public void after() {
//		LOG.info("[AttivitaDAOImplTest::after]START");
//	}
//
//	@Autowired
//	AffidamentiDAOImpl affidamentiDAOImpl;
//	
//	@Autowired
//	ArchivioFileDAOImpl archivioFileDAOImpl;
//	
//	@Autowired
//	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
//	
//	
//	@Test
//	public void getElencoAffidamentiTest() throws Exception {
//		String prf = "[AffidamentiDaoImplTest :: getElencoAffidamentiTest] ";
//		LOG.info(prf + "START");
//
//		Long idProgetto = 19241L;
//		List<AffidamentoDTO> affidamenti = affidamentiDAOImpl.getElencoAffidamenti(idProgetto, "ISTR-AFFIDAMENTI");
//		for(AffidamentoDTO dto: affidamenti) {
//			LOG.info(prf + " Affidamento: "+ dto.getOggettoAppalto() + " stato: "+ dto.getDescStatoAffidamento());
//		}
//		LOG.info(prf + "END");
//	}
//	
//	@Test
//	public void findFornitoriTest() throws GestioneAffidamentiException, FileNotFoundException, IOException {
//		String prf = "[AffidamentiDaoImplTest :: findFornitoriTest] ";
//		LOG.info(prf + "START");
//		
//		boolean test = true;
//		
//		if(test) {
//			FornitoreDTO filtro = new FornitoreDTO();
//			filtro.setIdSoggettoFornitore(17408L);
//			filtro.setIdTipoSoggetto(2L);
//			
//			List<FornitoreDTO> fornitori = affidamentiDAOImpl.findFornitori(filtro);
//			for (FornitoreDTO f: fornitori) {
//				LOG.info(prf + "fornitore: "+ f.getDenominazioneFornitore());
//			}
//		}
//		
//		LOG.debug(prf + "END");
//		
//	}
//	
//	@Test
//	public void findAffidamentoTest() throws GestioneAffidamentiException, FileNotFoundException, IOException {
//		String prf = "[AffidamentiDaoImplTest :: findAffidamentoTest] ";
//		LOG.info(prf + "START");
//		Long idAppalto = 4267L;
//		AffidamentoDTO affidamento = affidamentiDAOImpl.findAffidamento(idAppalto);
//		LOG.info(prf + "affidamento: "+ affidamento.getDescStatoAffidamento());
//		
//		
//		LOG.debug(prf + "END");
//		
//	}
//	
//	@Test
//	public void findAllegatiTest() throws Exception {
//		String prf = "[AffidamentiDaoImplTest :: findAllegatiTest] ";
//		LOG.info(prf + "START");
//		Long idAppalto = 4267L;
//		List<DocumentoAllegato> allegati = archivioFileDAOImpl.findAllegati(idAppalto);
//		for(DocumentoAllegato d: allegati) {
//			LOG.info(prf + "affidamento: "+ d.getNome());
//		}	
//				
//		LOG.debug(prf + "END");
//		
//	}
//	
//	@Test
//	public void findCodiceProgettoTest() {
//		String prf = "[AffidamentiDAOImpl::findCodiceProgettoTest]";
//		LOG.info(prf + " BEGIN");
//		try {
//			Long idProgetto = 123L;
//			String codiceProgetto = affidamentiDAOImpl.findCodiceProgetto(idProgetto);
//			LOG.info(prf + " codiceProgetto :"+ codiceProgetto);
//			LOG.info(prf + " END");
//		} catch (Exception e) {
//			LOG.info(prf + " END");
//			throw e;
//		}
//	}
//	
//	
//	@Test
//	public void findNormativeAffidamentoTest() {
//		String prf = "[AffidamentiDAOImpl::findNormativeAffidamentoTest]";
//		LOG.info(prf + " BEGIN");
//
//		List<NormativaAffidamentoDTO> vo = affidamentiDAOImpl.findNormativeAffidamento();
//		
//		for(NormativaAffidamentoDTO d: vo) {
//			LOG.info(prf + "normativa affidamento : "+ d.getDescNorma());
//		}	
//			
//		LOG.info(prf + " END");
//	
//	}
//	
//	
//	@Test
//	public void findTipologieAffidamentoTest() {
//		String prf = "[AffidamentiDAOImpl::findTipologieAffidamentoTest]";
//		LOG.info(prf + " BEGIN");
//
//		List<TipoAffidamentoDTO> vo = affidamentiDAOImpl.findTipologieAffidamento();
//		
//		for(TipoAffidamentoDTO d: vo) {
//			LOG.info(prf + "tipologie affidamento : "+ d.getDescTipoAffidamento());
//		}	
//			
//		LOG.info(prf + " END");
//	}
//	
//	
//	@Test
//	public void findTipologieAppaltoTest() {
//		String prf = "[AffidamentiDAOImpl::findTipologieAppaltoTest]";
//		LOG.info(prf + " BEGIN");
//		List<TipologiaAppaltoDTO> vo = affidamentiDAOImpl.findTipologieAppalto();		
//		for(TipologiaAppaltoDTO d: vo) {
//			LOG.info(prf + "tipologie appalto : "+ d.getDescBreveAppalto());
//		}			
//		LOG.info(prf + " END");
//	}
//	
//	@Test
//	public void findTipologieProcedureAggiudicazioneTest() throws Exception {
//		String prf = "[AffidamentiDAOImpl::findTipologieProcedureAggiudicazioneTest]";
//		LOG.info(prf + " BEGIN");
//		List<TipologiaAggiudicazioneDTO> dto = affidamentiDAOImpl.findTipologieProcedureAggiudicazione(271L);	
//		for(TipologiaAggiudicazioneDTO d: dto) {
//			LOG.info(prf + "tipologie aggiudicazione : "+ d.getDescTipologiaAggiudicazione());
//		}			
//		LOG.info(prf + " END");
//	}
//	
//	@Test
//	public void findMotiveAssenzaTest() throws Exception {
//		String prf = "[AffidamentiDAOImpl::findMotiveAssenzaTest]";
//		LOG.info(prf + " BEGIN");
//		
//		boolean test = true;
//		
//		if(test) {
//			List<MotiveAssenzaDTO> dto = affidamentiDAOImpl.findMotiveAssenza();
//			
//			for(MotiveAssenzaDTO d: dto) {
//				LOG.info(prf + "motivo assenza : "+ d.getDescBreveMotivoAssenzaCig());
//			}	
//		}
//		LOG.info(prf + " END");
//	}
//	
//	
//	
//	@Test
//	public void findTipologieVariantiTest() throws Exception {
////		String prf = "[AffidamentiDAOImpl::findTipologieVariantiTest]";
////		LOG.info(prf + " BEGIN");
////		
////		ArrayList<CodiceDescrizioneDTO> dto = affidamentiDAOImpl.findTipologieVarianti(userInfo, GestioneDatiDiDominioSrv.TIPOLOGIE_VARIANTI);
////		
////		for(CodiceDescrizioneDTO d: dto) {
////			LOG.info(prf + "tipologia  : "+ d.getDescrizione());
////		}	
////		
////		LOG.info(prf + " END");
//
//	}
//	
//	@Test
//	public void findRuoliTest() throws Exception {
//		String prf = "[AffidamentiDAOImpl::findRuoliTest]";
//		LOG.info(prf + " BEGIN");
//		
//		ArrayList<CodiceDescrizioneDTO> dto = affidamentiDAOImpl.findRuoli(userInfo, GestioneDatiDiDominioSrv.TIPO_PERCETTORE);
//		
//		for(CodiceDescrizioneDTO d: dto) {
//			LOG.info(prf + "ruolo  : "+ d.getDescrizione());
//		}	
//		
//		LOG.info(prf + " END");
//
//	}
//	
//	
//	@Test
//	public void creaVarianteTest() throws Exception {
////		String prf = "[AffidamentiDAOImpl::creaVarianteTest]";
////		LOG.info(prf + " BEGIN");		
////		
////		boolean test = false;
////		
////		if(test) {
////			VarianteAffidamentoDTO dto = new VarianteAffidamentoDTO();
////			dto.setIdTipologiaVariante(1L);
////			dto.setIdVariante(null);
////			dto.setFlgInvioVerificaAffidamento("N");
////			dto.setDtInserimento(DateUtil.getSysdate());
////			dto.setIdAppalto(4267L);
////			dto.setDescrizioneTipologiaVariante("TEST_CREA_VARIANTE");
////			dto.setDtInvioVerificaAffidamento(DateUtil.getSysdate());
////			dto.setImporto(100.0);
////			dto.setNote(prf);
////			
////			EsitoGestioneAffidamenti esito = affidamentiDAOImpl.creaVariante(userInfo.getIdUtente(), userInfo.getIdIride(), dto);
////			
////			LOG.info(prf + " esito: "+ esito.getEsito() + " msg: "+ esito.getMessage() );
////		}
////		LOG.info(prf + " END");
//		
//	}
//	
//	@Test
//	public void creaFornitoreTest() throws Exception {
////		String prf = "[AffidamentiDAOImpl::creaFornitoreTest]";
////		LOG.info(prf + " BEGIN");		
////		
////		boolean test = true;
////		
////		if(test) {
////			FornitoreAffidamentoDTO dto = new FornitoreAffidamentoDTO();
////			dto.setIdTipoPercettore(1L);
////			dto.setIdFornitore(84523L);
////			dto.setFlgInvioVerificaAffidamento("S");
////			dto.setIdAppalto(3832L);
////			dto.setDtInvioVerificaAffidamento(DateUtil.getSysdate());
////			EsitoGestioneAffidamenti esito = affidamentiDAOImpl.creaFornitore(userInfo.getIdUtente(), userInfo.getIdIride(), dto);
////			
////			LOG.info(prf + " esito: "+ esito.getEsito() + " msg: "+ esito.getMessage() );
////		}
////		LOG.info(prf + " END");
//		
//	}
//	
//	
//	
//	@Test
//	public void inviaInVerificaTest() throws Exception {
////		String prf = "[AffidamentiDAOImpl::inviaInVerificaTest]";
////		LOG.info(prf + " BEGIN");
////		
////		boolean test = true;
////		
////		Long idProgetto = 19233L;
////		Long idAppalto = 4486L;
////	
////		if(test) {
////			DatiGeneraliDTO dg = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(userInfo.getIdUtente(), userInfo.getIdIride(), idProgetto, null);
////			
////			LOG.info(prf + "******* Dati generali *******");
////			LOG.info(prf + " Beneficiario: " + dg.getDescrizioneBeneficiario());
////			LOG.info(prf + " Titolo progetto: " + dg.getTitoloProgetto());
////			LOG.info(prf + " Codice progetto: " + dg.getCodiceProgetto());
////			LOG.info(prf + " Nome bando linea: " + dg.getDescrizioneBando());
////			
////			ParamInviaInVerificaDTO dto = new ParamInviaInVerificaDTO();
////			dto.setIdAppalto(idAppalto);
////			dto.setBeneficiario(dg.getDescrizioneBeneficiario());
////			dto.setTitoloProgetto(dg.getTitoloProgetto());
////			dto.setCodiceProgettoVisualizzato(dg.getCodiceProgetto());
////			dto.setNomeBandoLinea(dg.getDescrizioneBando());
////			
////			EsitoGestioneAffidamenti esito = affidamentiDAOImpl.inviaInVerifica(userInfo.getIdUtente(), dto);
////			
////			LOG.info(prf + "esito : " + esito.getEsito()   + " msg: "+ esito.getMessage());	
////		}
////		LOG.info(prf + " END");
//	}
//	
//	
//	@Test
//	public void salvaAffidamentoTest() throws Exception {
////		String prf = "[AffidamentiDAOImpl::salvaAffidamentoTest]";
////		LOG.info(prf + " BEGIN");
////		AffidamentoDTO dto = new AffidamentoDTO();
////		dto.setIdTipoAffidamento(1L);
////		dto.setImportoContratto(100.0);
////		dto.setIdNorma(2L);
////		dto.setOggettoAppalto("TEST_OGGETTO");
////		dto.setIdTipologiaAppalto(2L);
////		dto.setDtFirmaContratto(DateUtil.getSysdate());
////		dto.setIdStatoAffidamento(1L);
////		dto.setBilancioPreventivo(100.0);
////		dto.setImpRendicontabile(100.0);
////		dto.setIdProgetto(19233L);
////		dto.setDtInizioPrevista(DateUtil.getSysdate());
////		dto.setEsisteAllegatoNonInviato(true);
////		//dto.setSopraSoglia("N");
////		
////		ProceduraAggiudicazioneDTO pDto = new ProceduraAggiudicazioneDTO();
////		pDto.setIdProgetto(19233L);
////		pDto.setCigProcAgg("1248484848");
////		pDto.setCodProcAgg("5959577759595959959");
////		pDto.setDescProcAgg("altro");
////		pDto.setDtAggiudicazione(DateUtil.getSysdate());
////		pDto.setDtInizioValidita(DateUtil.getSysdate());
////		pDto.setIdTipologiaAggiudicaz(11L);
////		
////		
////		dto.setProceduraAggiudicazioneDTO(pDto);
////		
////		EsitoGestioneAffidamenti esito = affidamentiDAOImpl.salvaAffidamento(userInfo.getIdUtente(), dto);
////		
////		LOG.info(prf + "esito : " + esito.getEsito()   + " msg: "+ esito.getMessage());			
////		LOG.info(prf + " END");
//
//	}
//	
//	@Test
//	public void findAllAffidamentoChecklist() {
//		String prf = "[AffidamentiDAOImpl::findAllAffidamentoChecklist]";
//		LOG.info(prf + " BEGIN");
//		AffidamentoCheckListDTO[] dto = affidamentiDAOImpl.findAllAffidamentoChecklist();
//		for(AffidamentoCheckListDTO a: dto) {
//			LOG.info(prf + " IdAffidamentoChecklist "+ a.getIdAffidamentoChecklist() + " idNorma: "+ a.getIdNorma());
//		}
//	}
//	
//	
//}
