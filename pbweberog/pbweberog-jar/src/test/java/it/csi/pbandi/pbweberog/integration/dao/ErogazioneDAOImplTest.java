/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

//package it.csi.pbandi.pbweberog.integration.dao;
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
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO;
//import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbweberog.base.PbweberogJunitClassRunner;
//import it.csi.pbandi.pbweberog.base.TestBaseService;
//import it.csi.pbandi.pbweberog.integration.dao.impl.ErogazioneDAOImpl;
//
//@RunWith(PbweberogJunitClassRunner.class)
//public class ErogazioneDAOImplTest extends TestBaseService {
//	protected static Logger LOG = Logger.getLogger(ErogazioneDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//
//	@Autowired
//	ErogazioneDAOImpl erogazioneDAOImpl;
//	
//	@Autowired
//	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
//	
//	@Before()
//	public void before() {
//		LOG.info("[ErogazioneDAOImplTest::before]START");
//	}
//	@After()
//	public void after() {
//		LOG.info("[ErogazioneDAOImplTest::after]START");
//	}
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
//	
//	
//	
////	@Test
////	public void findErogazioneTest() throws Exception {
////		String prf = "[ErogazioneDAOImplTest :: findErogazioneTest] ";
////		LOG.info(prf + "START");
////		//Long idProgetto = 20170L;
////		Long idProgetto = 15959L;
////		//Long idBando = 248L;
////		Long idBando = 15959L;
////		EsitoErogazioneDTO esito = erogazioneDAOImpl.findErogazione(getCurrentUserInfo().getIdUtente(),
////															getCurrentUserInfo().getIdIride(), idProgetto, idBando);
////		
////		LOG.info(prf + "size erog:"+ esito.getErogazione().getErogazioni().length);
//////		for(ErogazioneDTO e: esito.getErogazione().getErogazioni()) {
////			LOG.info(prf + "*******************SPESA DEL PROGETTO*******************");
////			LOG.info(prf + " tipo operazione: " + esito.getErogazione().getSpesaProgetto().getTipoOperazione());
////			LOG.info(prf + " Totale sostenuta: " + esito.getErogazione().getSpesaProgetto().getTotaleSpesaSostenuta() + " Totale spesa ammessa: " + esito.getErogazione().getSpesaProgetto().getTotaleSpesaAmmessa());
////			LOG.info(prf + "\n");
////			
////			LOG.info(prf + "*******************FIDIUSSIONE BANCARIA DEL PROGETTO*******************");
////			if(esito.getErogazione().getFideiussioni()!=null) {
////				for(FideiussioneDTO f: esito.getErogazione().getFideiussioni()) {
////					LOG.info(prf + "Desc fideiussione: " + f.getDescrizione());
////				}
////			} else {
////				LOG.info(prf + "non ci sono elementi da visualizzare.\n");
////			}
////			
////			
////			LOG.info(prf + "*******************RIEPILOGO DELLE RICHIESTE DI EROGAZIONE DEL PROGETTO*******************");
////			if(esito.getErogazione().getRichiesteErogazioni()!=null) {
////				for(RichiestaErogazioneDTO r: esito.getErogazione().getRichiesteErogazioni()) {
////					LOG.info(prf + "Desc Causale Erogazione: " + r.getDescCausaleErogazione());
////				}
////			}else {
////				LOG.info(prf + "non ci sono elementi da visualizzare.\n");
////			}
//////			
//////			
//////			LOG.info(prf + "*******************MODALITA AGEVOLAZIONI DEL PROGETTO*******************");
//////			if(esito.getErogazione().getModalitaAgevolazioni()!=null) {
//////				for(ModalitaAgevolazioneErogazioneDTO m: esito.getErogazione().getModalitaAgevolazioni()) {
//////					LOG.info(prf + "Desc Breve Modalita Agevolaz: " + m.getDescBreveModalitaAgevolaz());
//////					LOG.info(prf + "Ultimo Importo Agevolato: " + m.getUltimoImportoAgevolato());
//////				}
//////			} else {
//////				LOG.info(prf + "non ci sono elementi da visualizzare.\n");
//////			}
//////						
//////			
////////		}
////			LOG.info(prf + "END");
////	}
//
////	@Test
////	public void findCausaliErogazioneTest() throws Exception {
////		String prf = "[ErogazioneDAOImplTest :: findCausaliErogazioneTest] ";
////		LOG.info(prf + "START");
////		Long idProgetto = 20170L;
////		Long idBando = 248L;
////		Long idFormaGiuridica = 62L;
////		Long idDimensioneImpresa = 5L;
////		CodiceDescrizioneDTO[] causali = erogazioneDAOImpl.findCausaliErogazione(getCurrentUserInfo().getIdUtente(),
////															getCurrentUserInfo().getIdIride(), idProgetto, idBando, idFormaGiuridica, idDimensioneImpresa);
////		LOG.info(prf + "size causali:"+ causali.length);
////		
////		ArrayList<CodiceDescrizione> list = new ArrayList<CodiceDescrizione>();
////		for(int i=0; i < causali.length; i++){
////			CodiceDescrizione cd = new CodiceDescrizione();
////			cd.setCodice(causali[i].getCodice());
////			cd.setDescrizione(causali[i].getDescrizione());
////			list.add(cd);
////		}
////		//METTO IN CODA QUELLI FISSI
////		CodiceDescrizione cdPrimoAcconto = new CodiceDescrizione();
////		cdPrimoAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO+"@"+"0"+"@"+"0");
////		cdPrimoAcconto.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_PRIMO_ACCONTO);
////		list.add(cdPrimoAcconto);
////		CodiceDescrizione cdUlterioreAcconto = new CodiceDescrizione();
////		cdUlterioreAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO+"@"+"0"+"@"+"0");
////		cdUlterioreAcconto.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO);
////		list.add(cdUlterioreAcconto);
////		CodiceDescrizione cdSaldo = new CodiceDescrizione();
////		cdSaldo.setCodice(Constants.COD_CAUSALE_EROGAZIONE_SALDO+"@"+"0"+"@"+"0");
////		cdSaldo.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_SALDO);
////		list.add(cdSaldo);
////		for(CodiceDescrizione c: list) {
////			LOG.info(prf + "Desc Causale: " + c.getDescrizione());
////		}
////		LOG.info(prf + "END");
////	}
//	
////	@Test
////	public void findModalitaAgevolazionePerContoEconomicoTest() throws Exception {
////		String prf = "[ErogazioneDAOImplTest :: findModalitaAgevolazionePerContoEconomicoTest] ";
////		LOG.info(prf + "START");
////		Long idProgetto = 20170L;
////
////		CodiceDescrizioneDTO[] listSrv = erogazioneDAOImpl.findModalitaAgevolazionePerContoEconomico(getCurrentUserInfo().getIdUtente(),
////															getCurrentUserInfo().getIdIride(), idProgetto);
////		LOG.info(prf + "size Modalita Agevolazione:"+ listSrv.length);
////		
////		ArrayList<CodiceDescrizione> listModalita = new ArrayList<CodiceDescrizione>();
////		if (listSrv != null) {
////			  for (CodiceDescrizioneDTO cdSrv : listSrv) {
////				 CodiceDescrizione cd = new CodiceDescrizione();
////				 cd.setCodice(cdSrv.getCodice());
////				 cd.setDescrizione(cdSrv.getDescrizione());
////				 listModalita.add(cd);
////			 }
////		}
////		for(CodiceDescrizione c: listModalita) {
////			LOG.info(prf + "Modalità di agevolazione: " + c.getDescrizione());
////		}
////		LOG.info(prf + "END");
////	}
//	
//	
////	@Test
////	public void inserisciErogazioneTest() throws Exception {
////		String prf = "[ErogazioneDAOImplTest :: inserisciErogazioneTest] ";
////		LOG.info(prf + "START");
////		Long idProgetto = 20170L;
////
//////		CodiceDescrizioneDTO[] listSrv = erogazioneDAOImpl.inserisciErogazione(getCurrentUserInfo().getIdUtente(),
//////															getCurrentUserInfo().getIdIride(), idProgetto);
//////		LOG.info(prf + "size Modalita Agevolazione:"+ listSrv.length);
//////		
//////		ArrayList<CodiceDescrizione> listModalita = new ArrayList<CodiceDescrizione>();
//////		if (listSrv != null) {
//////			  for (CodiceDescrizioneDTO cdSrv : listSrv) {
//////				 CodiceDescrizione cd = new CodiceDescrizione();
//////				 cd.setCodice(cdSrv.getCodice());
//////				 cd.setDescrizione(cdSrv.getDescrizione());
//////				 listModalita.add(cd);
//////			 }
//////		}
//////		for(CodiceDescrizione c: listModalita) {
//////			LOG.info(prf + "Modalità di agevolazione: " + c.getDescrizione());
//////		}
////		LOG.info(prf + "END");
////	}
//	
//	
//	@Test
//	public void controllaDatiOnSelectCausaleErogazioneTest() throws Exception {
//		String prf = "[ErogazioneDAOImplTest :: controllaDatiOnSelectCausaleErogazioneTest] ";
//		LOG.info(prf + "START");
//		Long idProgetto = 21632L;
//		
//		LOG.info(prf + "END");
//	}
//	
//	@Test
//	public void creaReportRichiestaErogazioneTest() throws Exception {
//		String prf = "[ErogazioneDAOImplTest :: creaReportRichiestaErogazione] ";
//		LOG.info(prf + "START");
//		Long idProgetto = 21632L;
//		
//		LOG.info(prf + "END");
//	}
//	@Test
//	public void findDatiRiepilogoRichiestaErogazioneTest() throws Exception {
//		String prf = "[ErogazioneDAOImplTest :: findDatiRiepilogoRichiestaErogazioneTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 15959L;
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		Long idSoggetto = 2130090L;
//		
//		LOG.info(prf + "idUtente: "+ idUtente + " idIride: " + idIride + " idSoggetto: " + idSoggetto);
//		
//		//DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, idSoggetto);
//		Long idBandoLinea = 341L;
//		Long idDimensioneImpresa = 5L;
//		Long idFormaGiuridica = 44L;
////		
////		EsitoRichiestaErogazioneDTO esito =  erogazioneDAOImpl.findDatiRiepilogoRichiestaErogazione(idUtente, idIride, idProgetto, "SA",idDimensioneImpresa, idFormaGiuridica, idBandoLinea, idSoggetto);
////		LOG.info(prf + " Esito: " + esito.getEsito());
////		
////		LOG.info(prf + " Desc Breve Causale Erogazione: " + esito.getRichiestaErogazione().getDescBreveCausaleErogazione() );
////		LOG.info(prf + " Direttore Lavori: " + esito.getRichiestaErogazione().getDirettoreLavori() );
////		LOG.info(prf + " Residenza Direttore Lavori: " + esito.getRichiestaErogazione().getResidenzaDirettoreLavori() );
////		LOG.info(prf + " Importo Richiesto: " + esito.getRichiestaErogazione().getImportoRichiesto() );
////		LOG.info(prf + " Percentuale Erogazione: " + esito.getRichiestaErogazione().getPercentualeErogazione() );
////		LOG.info(prf + " Percentuale Limite: " + esito.getRichiestaErogazione().getPercentualeLimite() );
////		LOG.info(prf + " Dt Inizio Lavori: " + esito.getRichiestaErogazione().getDtInizioLavori());
////		LOG.info(prf + " Dt Stipulazione Contratti: " + esito.getRichiestaErogazione().getDtStipulazioneContratti() );
////		
////		LOG.info(prf + " ********************************* Spesa progetto ********************************\n");
////		LOG.info(prf + " Avanzamento Spesa Prevista Bando: " + esito.getRichiestaErogazione().getSpesaProgetto().getAvanzamentoSpesaPrevistaBando());
////		LOG.info(prf + " AvanzamentoSpesaSostenuta : " + esito.getRichiestaErogazione().getSpesaProgetto().getAvanzamentoSpesaSostenuta() );
////		LOG.info(prf + " AvanzamentoSpesaValidata : " + esito.getRichiestaErogazione().getSpesaProgetto().getAvanzamentoSpesaValidata());
////		LOG.info(prf + " ImportoAmmessoContributo : " + esito.getRichiestaErogazione().getSpesaProgetto().getImportoAmmessoContributo());
////		LOG.info(prf + " ImportoAmmessoContributo : " + esito.getRichiestaErogazione().getSpesaProgetto().getImportoAmmessoContributo() );
////		LOG.info(prf + " ImportoSpesaDaRaggiungere : " + esito.getRichiestaErogazione().getSpesaProgetto().getImportoSpesaDaRaggiungere() );
////		LOG.info(prf + " TotaleSpesaSostenuta : " + esito.getRichiestaErogazione().getSpesaProgetto().getTotaleSpesaSostenuta() );
////		LOG.info(prf + " TotaleSpesaValidata : " + esito.getRichiestaErogazione().getSpesaProgetto().getTotaleSpesaValidata() );				
////		LOG.info(prf + "\n ********************************* Spesa progetto - END ********************************\n");
////				
////		
////		LOG.info(prf + " ********************************* Fideiussioni ********************************\n");
////		for(FideiussioneDTO dto: esito.getRichiestaErogazione().getFideiussioni() ) {
////			LOG.info(prf + " Desc Ente Emittente: " + dto.getDescEnteEmittente());
////			LOG.info(prf + " Descrizione : " + dto.getDescrizione());
////			LOG.info(prf + " Importo : " + dto.getImporto());
////			LOG.info(prf + " Importo totale tipo trattamento: " + dto.getImportoTotaleTipoTrattamento());
////			LOG.info(prf + " \n ");
////		}		
////		LOG.info(prf + "\n ********************************* Fideiussioni - END ********************************\n");
////		
////		
////		LOG.info(prf + " ********************************* Fideiussioni Tipo Trattamento ********************************\n");
////		if(esito.getRichiestaErogazione().getFideiussioniTipoTrattamento() !=null ) {
////			for(FideiussioneTipoTrattamentoDTO dto: esito.getRichiestaErogazione().getFideiussioniTipoTrattamento() ) {
////				LOG.info(prf + " Desc Breve Tipo Trattamento: " + dto.getDescBreveTipoTrattamento() );
////				LOG.info(prf + " Importo: " + dto.getImporto() );			
////			}
////		}
////		
////		LOG.info(prf + "\n ********************************* Fideiussioni Tipo Trattamento - END ********************************\n");
////		
////		
////		LOG.info(prf + " ********************************* Tipo Allegati ********************************\n");
////		for(TipoAllegatoDTO dto: esito.getRichiestaErogazione().getTipoAllegati() ) {
////			LOG.info(prf + " Desc Tipo Allegato: " + dto.getDescTipoAllegato() );
////			LOG.info(prf + " IdTipoAllegato : " + dto.getIdTipoAllegato() );
////			
////		}
////		LOG.info(prf + "\n ********************************* Tipo Allegai - END ********************************\n");
////		
////		LOG.info(prf + " *********************************Estremi Bancari ********************************\n");
////		if(esito.getRichiestaErogazione().getEstremiBancari() !=null) {
////			LOG.info(prf + " Descrizione Agenzia: " + esito.getRichiestaErogazione().getEstremiBancari().getDescrizioneAgenzia());
////			LOG.info(prf + " Descrizione Banca: " + esito.getRichiestaErogazione().getEstremiBancari().getDescrizioneBanca());
////			LOG.info(prf + " IBAN: " + esito.getRichiestaErogazione().getEstremiBancari().getIban());
////			LOG.info(prf + " Numero conto: " + esito.getRichiestaErogazione().getEstremiBancari().getNumeroConto());		
////			LOG.info(prf + "\n ********************************* Estremi Bancari - END ********************************\n");
////		}
//		
//		
//		
//		LOG.info(prf + "END");
//	}
//	
//	
//}
