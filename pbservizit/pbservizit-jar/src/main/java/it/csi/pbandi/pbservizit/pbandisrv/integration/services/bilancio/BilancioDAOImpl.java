/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ClientDocumentiService;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ClientRicercaService;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.Errore;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.CapitoloUscitaGestione;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Contatti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.DocumentoSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Esito;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.LiquidazioneAtti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.MandatoDiPagamento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.ModalitaPagamento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.NaturaGiuridica;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Provvedimento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Recapito;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestione;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggetti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioni;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegno;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggetti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Sede;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Soggetto;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioXMLManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.BilancioXMLManagerOutput;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.tracciamento.TracciamentoBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.EsitoLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.InputLeggiStatoElaborazioneDocDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiLLogStatoElabDocVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDlVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDmVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiWAttiLiquidazioneDtVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.exception.ConsultaImpegniException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.CapitoloKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ConsultaBeneficiariVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.DatiBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.DettaglioConsultazioneAttoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.FornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ImpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.InserisciAttoLiquidazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ProvvedimentoKeyVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo.ResultVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;


public class BilancioDAOImpl extends BilancioDAO {

	private BeanUtil beanUtil;
	private BilancioManager bilancioManager;
	private ConfigurationManager configurationManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private TracciamentoBusinessImpl traceManagerBusiness;

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}

	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}
	
	
	// ****************************************************************
	//    BEAN NUOVI SERVIZI CONTABILIA VIA WEB SERVICES
	// ****************************************************************
	@Autowired
	private ClientRicercaService clientRicercaService;
	@Autowired
	private ClientDocumentiService clientDocumentiService;;
	@Autowired
	private BilancioXMLManager bilancioXMLManager;

	public ClientDocumentiService getClientDocumentiService() {
		return clientDocumentiService;
	}
	public void setClientDocumentiService(
			ClientDocumentiService clientDocumentiService) {
		this.clientDocumentiService = clientDocumentiService;
	}
	public ClientRicercaService getClientRicercaService() {
		return clientRicercaService;
	}
	public void setClientRicercaService(ClientRicercaService clientRicercaService) {
		this.clientRicercaService = clientRicercaService;
	}
	
	public BilancioXMLManager getBilancioXMLManager() {
		return bilancioXMLManager;
	}

	public void setBilancioXMLManager(BilancioXMLManager bilancioXMLManager) {
		this.bilancioXMLManager = bilancioXMLManager;
	}
	
	// ****************************************************************
	//    CONSULTA ATTO LIQUIDAZIONE - INIZIO
	// ****************************************************************
	
	// WEB SERVICES DI CONTABILIA USATI: 
	//	ricercaEstesaLiquidazioni()
	//	ricercaDocumentoSpesa()
	//	ricercaDettaglioSoggetti()
	//	ricercaCapitoloUscita()
	//	ricercaEstesaOrdinativiSpesa()
	
	// Legge da Contabilia i dati aggiornati di un atto e delle sue liquidazioni\quote. 
	public OutputConsultaAttoLiquidazione consultaAttoLiquidazione(
			BigDecimal idAttoLiquidazione, Long idUtente) {	
		
		try {
			logger.info("consultaAttoLiquidazione(): INIZIO");
			
			PbandiTAttoLiquidazioneVO pbandiTAttoLiquidazioneVO = null;
			pbandiTAttoLiquidazioneVO = genericDAO.findSingleWhere(new PbandiTAttoLiquidazioneVO(idAttoLiquidazione));
			if (pbandiTAttoLiquidazioneVO == null)
				return null;
			
			//DA TOGLIERE
			//pbandiTAttoLiquidazioneVO.setAnnoAtto(new BigDecimal(this.getAnnoAttoMock()));
			//DA TOGLIERE
			
			Integer anno = new Integer(pbandiTAttoLiquidazioneVO.getAnnoAtto().intValue());
			Integer numeroProvvedimento = new Integer(pbandiTAttoLiquidazioneVO.getNumeroAtto());
			String numeroDocSpesa = pbandiTAttoLiquidazioneVO.getNumeroDocumentoSpesa();
			
			// Cerca le liquidazioni\quote su Contabilia.
			RicercaEstesaLiquidazioniResponse ricercaEstesaLiquidazioniResponse = null;
			ricercaEstesaLiquidazioniResponse = eseguiRicercaEstesaLiquidazioni(anno, numeroProvvedimento);
			if (ricercaEstesaLiquidazioniResponse == null)
				return null;
			
			// Cerca il documento di spesa su Contabilia.
			RicercaDocumentoSpesaResponse ricercaDocumentoSpesaResponse = null;
			ricercaDocumentoSpesaResponse = eseguiRicercaDocumentoSpesa(anno, numeroDocSpesa);
			if (ricercaDocumentoSpesaResponse == null) {
				logger.error("ricercaDocumentoSpesaResponse == null");
				return null;
			}
			logger.info("\n\nKKKKKKKKKKKKKKKKKKKKKKK\n\n");	

			// Popola il record PbandiWAttiLiquidazioneDt da inserire su db.
			PbandiWAttiLiquidazioneDtVO recDT = null;
			try {
				recDT = popolaPbandiWAttiLiquidazioneDt(ricercaEstesaLiquidazioniResponse, ricercaDocumentoSpesaResponse, numeroDocSpesa);
			} catch(Exception e) {
				logger.error("ERRORE in popolaPbandiWAttiLiquidazioneDt(): "+e);
				recDT = null;
			}
			if (recDT == null) {
				logger.error("recDT == null");
				
				// Se il doc si spesa c'� su Contabilia, ma le liquidazioni no, restituisco esito false
				// (prima restituiva null) e popolo DescStatoDocContabilia. 
				DocumentoSpesa[] documenti = ricercaDocumentoSpesaResponse.getDocumentiSpesa();
				String esitoLiquidazione = ricercaEstesaLiquidazioniResponse.getEsito().getValue();
				String esitoDocSpesa = ricercaDocumentoSpesaResponse.getEsito().getValue();				
				if (Constants.CONTABILIA_ESITO_FALLIMENTO.equalsIgnoreCase(esitoLiquidazione) &&
					Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(esitoDocSpesa) &&
					documenti != null && documenti.length > 0 && documenti[0] != null &&
					documenti[0].getStato() != null) {
					OutputConsultaAttoLiquidazione output = new OutputConsultaAttoLiquidazione();
					output.setIdAttiLiquidazioneDt(null);
					output.setDescStatoDocContabilia(documenti[0].getStato().getDescrizione());
					logger.info("consultaAttoLiquidazione(): doc trovato; liquidazione non trovata; stato doc "+output.getDescStatoDocContabilia());
					return output;
				}
				
				return null;
			}
			logger.info("\n\nHHHHHHHHHHHHHHHHHHHHHHHHH\n\n");	
			// Popola gli PbandiWAttiLiquidazioneDl da inserire su db.
			ArrayList<PbandiWAttiLiquidazioneDlVO> listaDL = null;
			listaDL = popolaPbandiWAttiLiquidazioneDl(ricercaEstesaLiquidazioniResponse);
			if (listaDL == null) {
				logger.error("listaDL == null");
				return null;
			}
			
			// Cera i mandati su Contabilia.
			RicercaEstesaOrdinativiSpesaResponse ricercaEstesaOrdinativiSpesaResponse = null;
			ricercaEstesaOrdinativiSpesaResponse = eseguiRicercaEstesaOrdinativiSpesa(anno, anno, numeroProvvedimento);
			if (ricercaEstesaOrdinativiSpesaResponse == null) {
				logger.error("ricercaEstesaOrdinativiSpesaResponse == null");
				return null;
			}
			
			// Popola gli PbandiWAttiLiquidazioneDm da inserire su db.
			ArrayList<PbandiWAttiLiquidazioneDmVO> listaDM = null;
			listaDM = popolaPbandiWAttiLiquidazioneDm(ricercaEstesaOrdinativiSpesaResponse);

			// Aggiorno il db.
			BigDecimal idAttiLiquidazioneDt = bilancioManager.caricaAttoDiLiquidazione(recDT, listaDL, listaDM);
			logger.info("consultaAttoLiquidazione(): FINE");			
			
			// Legge lo stato del documento su Contabilia.
			String descStatoDocContabilia = "";
			DocumentoSpesa[] documenti = ricercaDocumentoSpesaResponse.getDocumentiSpesa(); 
			if (documenti != null && documenti.length > 0 && documenti[0] != null &&
				documenti[0].getStato() != null) {
				descStatoDocContabilia = documenti[0].getStato().getDescrizione();
			}
			
			OutputConsultaAttoLiquidazione output = new OutputConsultaAttoLiquidazione();
			output.setIdAttiLiquidazioneDt(idAttiLiquidazioneDt);
			output.setDescStatoDocContabilia(descStatoDocContabilia);
			logger.info("consultaAttoLiquidazione(): output: IdAttiLiquidazioneDt = "+output.getIdAttiLiquidazioneDt()+"; DescStatoDocContabilia = "+output.getDescStatoDocContabilia());
			
			return output;
			
		} catch (Exception e) {
			logger.error("Eccezione durante la chiamata del servizio di bilancio: "+e.getMessage());
			return null;
		}  
	}

	/*
	private String getAnnoAttoMock() {
		return "2016";
	}
	*/
	
	private PbandiWAttiLiquidazioneDtVO popolaPbandiWAttiLiquidazioneDt(
			RicercaEstesaLiquidazioniResponse ricercaEstesaLiquidazioniResponse,
			RicercaDocumentoSpesaResponse ricercaDocumentoSpesaResponse,
			String numeroDocSpesa) {
		
		PbandiWAttiLiquidazioneDtVO vo = null;
		
		if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(ricercaEstesaLiquidazioniResponse.getEsito().getValue())) {
			LiquidazioneAtti[] liquidazioni = ricercaEstesaLiquidazioniResponse.getLiquidazioni();
			if (liquidazioni != null && liquidazioni.length > 0 && liquidazioni[0] != null) {			
				vo = new PbandiWAttiLiquidazioneDtVO();
				vo.setTiporecord(Constants.TIPO_RECORD_DT);
				vo.setFlagOnline(Constants.FLAG_TRUE);
				vo.setNumeroDocumentoSpesa(numeroDocSpesa);
				LiquidazioneAtti liquidazione = liquidazioni[0];
				Provvedimento provvedimento = liquidazione.getAtto();
				if (provvedimento != null) {
					if (provvedimento.getAnnoProvvedimento() != null)
						vo.setAnnoatto(provvedimento.getAnnoProvvedimento().toString());
					if (provvedimento.getNumeroProvvedimento() != null)
						vo.setNroatto(provvedimento.getNumeroProvvedimento().toString());
				}
				vo.setDataaggatto(this.getDataOdierna());
				StrutturaAmministrativa sac = provvedimento.getSac();
//				if (sac != null && !StringUtils.isEmpty(sac.getCodice())) {
				if (sac != null && ( sac.getCodice() != null && sac.getCodice().length() > 0 )) {
					try { 
						vo.setDiratto(sac.getCodice().substring(0,3));	// primi 3 char
						vo.setSettatto(sac.getCodice().substring(3));	// dal char 4 in poi
					} catch (Exception e){
						logger.error("popolaPbandiWAttiLiquidazioneDt(): errore nella estrazione di direzione e settore dal codice sac "+sac.getCodice());
						return null;
					}
				}
				String beneficiario = liquidazione.getBeneficiario();
//				if (!StringUtils.isEmpty(beneficiario)) {
				if ( beneficiario != null && beneficiario.length() > 0 ) {
					// Estrae CodBen e RagSoc da una stringa del tipo "15494-COMUNE DI CHERASCO".
					try { 
						int i = beneficiario.indexOf('-');
						if (i > -1) {
							String codBenStringa = beneficiario.substring(0,i).trim();								
							Integer codBenInteger = Integer.parseInt(codBenStringa);							
							vo.setCodben(new BigDecimal(codBenInteger));
							//if (beneficiario.length() >= i+1)				meglio leggerlo altrove (vedi sotto)
							//	vo.setRagsoc(beneficiario.substring(i+1).trim());
						}
					} catch (Exception e) {
						logger.error("popolaPbandiWAttiLiquidazioneDt(): errore nella estrazione di CodBen e RagSoc dalla stringa "+beneficiario);
						return null;
					}											
				}
				
				// Recupera il dettaglio del beneficiario in base al suo codice.
				RicercaDettaglioSoggettiResponse ricercaDettaglioSoggettiResponse = null;
				String cfBeneficiario = null;
				String codiceBeneficiario = null;
				if (vo.getCodben() != null) {
					Integer i = vo.getCodben().intValue();
					codiceBeneficiario = i.toString();
				}
				ricercaDettaglioSoggettiResponse = eseguiRicercaDettaglioSoggetto(cfBeneficiario, codiceBeneficiario);
		
				// Inserisce i dati del beneficiario.
				if (ricercaDettaglioSoggettiResponse != null &&
					Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(ricercaDettaglioSoggettiResponse.getEsito().getValue())) {
					Soggetto[] soggetti = ricercaDettaglioSoggettiResponse.getSoggetti(); 
					if (soggetti != null && soggetti.length > 0) {
						Soggetto soggetto = soggetti[0];
						if (soggetto != null) {
							vo.setCodfisc(soggetto.getCodiceFiscale());
							vo.setPartiva(soggetto.getPartitaIva());
							vo.setRagsoc(soggetto.getRagioneSociale());
							// Indirizzo principale.
							if (soggetto.getIndirizzoPrincipale() != null) {
								Recapito recapito = soggetto.getIndirizzoPrincipale(); 
								vo.setCap(recapito.getCap());
								vo.setComune(recapito.getComune());															
								vo.setProv(recapito.getProvincia());
								vo.setVia(recapito.getSedime()+" "+recapito.getIndirizzo());
							}
							// Modalit� pagamento.
							ModalitaPagamento m = this.getModalitaDiPagamentoSoggetto(soggetto);							
							if (m != null) {
								vo.setProgmodpag(new BigDecimal(m.getCodice()));						
								vo.setDesccodaccre(m.getDescrizioneTipoAccredito());
								vo.setCodaccre(m.getCodiceTipoAccredito());																
								
								// I dati bancari (cab, abi, iban, num conto, etc): non sono pi� passati singolamente
								// ma all'interno della descrizione di una modalit� di pagamento.
								// Quindi se uno vuole l'iban o il numero di conto deve estrarlo come sottostringa dal campo descrizione.
								vo.setIban(this.estraiIBAN(m.getDescrizione()));
								vo.setNrocc(this.estraiConto(m.getDescrizione()));
								
								// Sede modalit� di pagamento.
								boolean sedeModPagTrovata = false;
								if (m.getSede() != null) {
									String descrizione = m.getSede().getDescrizione();
									Sede sede = cercaSedeModalitaPagamento(descrizione, soggetto);
									if (sede != null && sede.getRecapito() != null) {
										sedeModPagTrovata = true;
										Recapito r = sede.getRecapito();
										vo.setCapsede(r.getCap());
										vo.setComunesede(r.getComune());
										vo.setProvsede(r.getProvincia());
										vo.setViasede(r.getSedime()+" "+r.getIndirizzo());
									}	
								}
								if (!sedeModPagTrovata) {
									// Se non c'� una sede, riporto i dati dell'indirizzo principale.
									vo.setCapsede(vo.getCap());
									vo.setComunesede(vo.getComune());
									vo.setProvsede(vo.getProv());
									vo.setViasede(vo.getVia());
								}
							}	
						}
					}					
				}
				
				logger.info("\n\nPbandiWAttiLiquidazioneDtVO:"+vo.toString()+"\n\n");
			}
		}
		
		if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(ricercaDocumentoSpesaResponse.getEsito().getValue())) {
			DocumentoSpesa[] documenti = ricercaDocumentoSpesaResponse.getDocumentiSpesa();
			if (documenti != null && documenti.length > 0) {
				DocumentoSpesa doc = documenti[0];
				if (doc != null) {
					vo.setImportoatto(doc.getImportoDocumento());
					vo.setDescri(doc.getDescrizione());
					if (doc.getStato() != null) {
						vo.setDescStatoDocumento(doc.getStato().getDescrizione());
					}
				}
			}
			
		}
		
		return vo;
	}	

	// Restituisce la prima modalit� di pagamento valida.
	private ModalitaPagamento getModalitaDiPagamentoSoggetto (Soggetto soggetto) {
		ModalitaPagamento modalita = null;
		if (soggetto != null) {
			ModalitaPagamento[] elencoModalita = soggetto.getElencoModalitaPagamento();
			if (elencoModalita != null) {
				for(int i = 0; i < elencoModalita.length; i++) {
					ModalitaPagamento modPag = elencoModalita[i];
					if (modPag != null) {
						if (Constants.CONTABILIA_STATO_VALIDO.equalsIgnoreCase(modPag.getStato().getCodice())) {
							return modPag;
						}
					}
				}
			}
		}
		return null;
	}
	
	private ArrayList<PbandiWAttiLiquidazioneDlVO> popolaPbandiWAttiLiquidazioneDl(RicercaEstesaLiquidazioniResponse ricercaEstesaLiquidazioniResponse) {
		ArrayList<PbandiWAttiLiquidazioneDlVO> listaDL = null; 
		if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(ricercaEstesaLiquidazioniResponse.getEsito().getValue())) {			
			LiquidazioneAtti[] liquidazioni = ricercaEstesaLiquidazioniResponse.getLiquidazioni();
			if (liquidazioni != null) {
				listaDL = new ArrayList<PbandiWAttiLiquidazioneDlVO>();
				for (LiquidazioneAtti liquidazione : liquidazioni) {
					
					PbandiWAttiLiquidazioneDlVO vo = new PbandiWAttiLiquidazioneDlVO();				
					vo.setTiporecord(Constants.TIPO_RECORD_DL);
					vo.setFlagOnline(Constants.FLAG_TRUE);
					
					// Dati dettaglio liquidazione
					if (liquidazione.getAnnoEsercizio() != null)
						vo.setAnnoeser(new BigDecimal(liquidazione.getAnnoEsercizio()));
					if (liquidazione.getAtto() != null) {
						Provvedimento atto = liquidazione.getAtto();
						if (atto.getAnnoProvvedimento() != null)
							vo.setAnnoprovv(atto.getAnnoProvvedimento().toString());
						if (atto.getNumeroProvvedimento() != null)
							vo.setNroprov(atto.getNumeroProvvedimento().toString());
						if (atto.getSac() != null) {
							vo.setDirezione(estraiPrimi3Char(atto.getSac().getCodice()));
						}
					}
					if (liquidazione.getNumeroLiquidazione() != null)
						vo.setNliq(new BigDecimal(liquidazione.getNumeroLiquidazione()));
					vo.setImporto(liquidazione.getImportoLiquidazione());
					if (liquidazione.getStato() != null)
						vo.setStato(liquidazione.getStato().getCodice());
					if (liquidazione.getAnnoImpegno() != null)
						vo.setAnnoimp(liquidazione.getAnnoImpegno().toString());
					if (liquidazione.getNumeroImpegno() != null)
						vo.setNroimp(new BigDecimal(liquidazione.getNumeroImpegno()));					
					if (liquidazione.getNumeroCapitolo() != null)
						vo.setNrocapitolo(new BigDecimal(liquidazione.getNumeroCapitolo()));
					if (liquidazione.getNumeroArticolo() != null)
						vo.setNroarticolo(new BigDecimal(liquidazione.getNumeroArticolo()));
					
					// Recupera il capitolo.
					RicercaCapitoloUscitaGestioneResponse response1 = null;
					Integer annoBilancio = (liquidazione.getAnnoEsercizio()==null)?(null):(liquidazione.getAnnoEsercizio());
					Integer numeroCapitolo = (liquidazione.getNumeroCapitolo()==null)?(null):(liquidazione.getNumeroCapitolo());
					Integer numeroArticolo = (liquidazione.getNumeroArticolo()==null)?(null):(liquidazione.getNumeroArticolo());
					response1 = this.eseguiRicercaCapitoloUscitaGestione(annoBilancio, numeroCapitolo, numeroArticolo);
					if (response1 == null)
						return null;
					
					// Aggiungo i dati del capitolo.
					CapitoloUscitaGestione[] capitoloGest = response1.getCapitoliUscitaGestione(); 
					if (capitoloGest != null && capitoloGest.length > 0 && capitoloGest[0] != null) {
						CapitoloUscitaGestione capitolo = capitoloGest[0];
						if (capitolo.getTipoFinanziamento() != null)
							vo.setTipofondo(capitolo.getTipoFinanziamento().getCodice());
					}					
					
					logger.info("\n\nPbandiWAttiLiquidazioneDlVO:"+vo.toString()+"\n\n");
					listaDL.add(vo);
				}
			}
		}
		return listaDL;
	}
	
	private String estraiPrimi3Char(String s) { 
		if (s == null)
			return null;
		if (s.length() > 3)
			return s.substring(0,3);
		else
			return s;
	}
	
	private ArrayList<PbandiWAttiLiquidazioneDmVO> popolaPbandiWAttiLiquidazioneDm(RicercaEstesaOrdinativiSpesaResponse ricercaEstesaOrdinativiSpesaResponse) {
		ArrayList<PbandiWAttiLiquidazioneDmVO> listaDM = new ArrayList<PbandiWAttiLiquidazioneDmVO>(); 
		if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(ricercaEstesaOrdinativiSpesaResponse.getEsito().getValue())) {
			MandatoDiPagamento[] mandati = ricercaEstesaOrdinativiSpesaResponse.getMandatiDiPagamento();
			if (mandati != null) {
				for (MandatoDiPagamento mandato : mandati) {
					
					PbandiWAttiLiquidazioneDmVO vo = new PbandiWAttiLiquidazioneDmVO();				
					vo.setTiporecord(Constants.TIPO_RECORD_DM);
					vo.setFlagOnline(Constants.FLAG_TRUE);
					if (mandato.getNumeroOrdinativo() != null)
						vo.setNromand(new BigDecimal(mandato.getNumeroOrdinativo()));
					vo.setImportomandlordo(mandato.getImportoOrdinativo());
					vo.setImportomandnetto(mandato.getImportoOrdinativo());
					if (mandato.getNumeroLiquidazione() != null)
						vo.setNliq(new BigDecimal(mandato.getNumeroLiquidazione()));
					if (mandato.getAnnoEsercizio() != null)
						vo.setAnnoeser(new BigDecimal(mandato.getAnnoEsercizio()));
					logger.info("\n\nPbandiWAttiLiquidazioneDmVO:"+vo.toString()+"\n\n");
					listaDM.add(vo);
				}
			}
		}
		return listaDM;
	}
	
	// ****************************************************************
	//    CONSULTA ATTO LIQUIDAZIONE - FINE
	// ****************************************************************

	
	// ****************************************************************
	//    CONSULTA IMPEGNI - INIZIO
	// ****************************************************************
	
	// WEB SERVICES DI CONTABILIA USATI: 
	//	ricercaImpegno()
	//	ricercaCapitoloUscitaGestione()
	
	// Esegue la ricerca puntuale di uno o pi� impegni 
	// NOTA: Il vecchio metodo cercava impegni anche per provvedimento e\o capitolo:
	//       non ho considerato questi casi, poich� non implementati dal nuovo web service
	//       ne' usati in PBandi (almeno da quel che ho visto).
	//       Ho considerato solo il caso di ricerca impegno puntuale per annoEsercizio+AnnoImpegno+NumeroImpegno.
	public List<ImpegnoVO> consultaImpegni(List<ImpegnoKeyVO> impegniFilter,
			List<ProvvedimentoKeyVO> provvedimentiFilter,
			List<CapitoloKeyVO> capitoliFilter, Long annoEsercizio,
			Long numMaxRecord,Long idUtente) throws Exception {
		
		logger.info("consultaImpegni(): INIZIO");	
		
		// Verifica che siano presenti filtri di ricerca sugli impegni.	
		if (impegniFilter == null || impegniFilter.size() == 0) {
			String msg = "consultaImpegni(): nessun impegno da cercare.";
			logger.error(msg);
			throw new ConsultaImpegniException(msg);
		}
		
		// Esegue la ricerca per ogni filtro specificato.
		List<ImpegnoVO> impegni = new ArrayList<ImpegnoVO>();
		for (ImpegnoKeyVO impegnoKey : impegniFilter) {
			
			logger.info("consultaImpegni(): eseguo ricerca per annoEsercizio = "+annoEsercizio+";  annoImpegno = "+impegnoKey.getAnnoImpegno()+"; numeroImpegno = "+impegnoKey.getNumeroImpegno());
			
			Integer annoBilancio = null;
			if (annoEsercizio != null)
				annoBilancio = annoEsercizio.intValue();
			Integer annoImpegno = null;
			if (impegnoKey.getAnnoImpegno() != null)
				annoImpegno = new Integer(impegnoKey.getAnnoImpegno());
			Integer numeroImpegno = impegnoKey.getNumeroImpegno();
			
			// Chiama il web service RicercaService.ricercaImpegno() di Contabilia (Bilancio).
			RicercaImpegnoResponse ricercaImpegnoResponse = null;
			try {
				ricercaImpegnoResponse = eseguiRicercaImpegno(annoBilancio, annoImpegno, numeroImpegno);
			} catch (Exception ex) {
				logger.error("consultaImpegni(): ERRORE in eseguiRicercaImpegno(): "+ex.getMessage());
				throw new ConsultaImpegniException("Si &egrave; verificato un errore durante la ricerca.");				
			}
			
			// Verifico che la response sia valorizzata.
			if (ricercaImpegnoResponse == null) {
				logger.error("consultaImpegni(): ERRORE: clientRicercaService.ricercaImpegno() di BILANCIO ha restituito NULL");
				throw new ConsultaImpegniException("Si &egrave; verificato un errore durante la ricerca.");				
			}
			
			// Verifico l'esito della ricerca.
			logger.info("consultaImpegni(): ricerca conclusa con esito = "+ricercaImpegnoResponse.getEsito());
			logger.info("consultaImpegni(): impegni trovati (ricercaImpegnoResponse.TotaleRisultati) = "+ricercaImpegnoResponse.getTotaleRisultati());
			if (ricercaImpegnoResponse.getEsito() == Esito.SUCCESSO) {
				
				if (ricercaImpegnoResponse.getTotaleRisultati() != null &&
				    ricercaImpegnoResponse.getTotaleRisultati().intValue() > 0) {
				
					// Elabora ogni impegno trovato dalla ricerca.				
					int totaleRisultati = ricercaImpegnoResponse.getTotaleRisultati();
					for (int i=0; i<totaleRisultati; i++) {
						
						logger.info("consultaImpegni(): i = "+i);
						
						// Impegno restituito dal servizio.
						it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno impegno = ricercaImpegnoResponse.getImpegni(i);
						
						logger.info("\n\nconsultaImpegni(): impegno restituito da Contabilia : ");
						logger.info(this.objectToXML(impegno));
						logger.info("\n\n");
						
						// Capitolo associato all'impegno.						
						Integer numeroCapitolo = impegno.getNumeroCapitolo();
						Integer numeroArticolo = impegno.getNumeroArticolo();
						CapitoloUscitaGestione capitolo = this.getCapitolo(annoBilancio, numeroCapitolo, numeroArticolo);
						
						// Impegno da restituire in output.
						ImpegnoVO vo = this.popolaImpegnoVO(impegno, capitolo, annoEsercizio.toString());					
						logger.info("\n\nconsultaImpegni(): impegno Bandi = "+vo.toString()+"\n\n");
						
						// Aggiunge l'impegno all'output.
						impegni.add(vo);										
					}					
				}
				
			} else {
				
				// Segnala l'errore restituito dal web service.
				String msgErrore = "";
				if (ricercaImpegnoResponse.getErrori() != null &&
					ricercaImpegnoResponse.getErrori().length > 0)
					msgErrore = ricercaImpegnoResponse.getErrori(0).getCodice()+" - "+ricercaImpegnoResponse.getErrori(0).getDescrizione();
				logger.error("consultaImpegni(): clientRicercaService.ricercaImpegno() ha restituito esito negativo: "+msgErrore);
				throw new ConsultaImpegniException(msgErrore);				
				
			}
			
		}  // fine ciclo for su impegniFilter
		
		if (impegni == null)
			logger.error("consultaImpegni(): restituisce NULL");
		else
			logger.info("consultaImpegni(): numero impegni restituiti = "+impegni.size());
		logger.info("consultaImpegni(): FINE");		
		
		return impegni;	
	}

	private CapitoloUscitaGestione getCapitolo(
			Integer annoBilancio, Integer numeroCapitolo, Integer numeroArticolo) throws ConsultaImpegniException {
		
		logger.info("getCapitolo(): INIZIO");
		
		// Chiama il web service RicercaService.ricercaCapitoloUscitaGestione() di Contabilia (Bilancio).
		RicercaCapitoloUscitaGestioneResponse output = null;
		try {
			logger.info("getCapitolo(): chiamo clientRicercaService.ricercaCapitoloUscitaGestione().");
			output = eseguiRicercaCapitoloUscitaGestione(annoBilancio, numeroCapitolo, numeroArticolo);
		} catch (Exception ex) {
			logger.error("getCapitolo(): ERRORE in eseguiRicercaCapitoloUscitaGestione() : "+ex.getMessage());
			throw new ConsultaImpegniException("Si &egrave; verificato un errore durante la ricerca.");				
		}
	
		// Verifica l'esito della ricerca.
		CapitoloUscitaGestione capitolo = new CapitoloUscitaGestione();
		if (output != null) {
			if (output.getEsito() == Esito.SUCCESSO) {
				if (output.getCapitoliUscitaGestione() != null &&
					output.getCapitoliUscitaGestione().length > 0)
					capitolo = output.getCapitoliUscitaGestione(0);				
			} else {
				String msgErrore = "";
				if (output.getErrori() != null && output.getErrori().length > 0)
					msgErrore = output.getErrori(0).getCodice()+" - "+output.getErrori(0).getDescrizione();
				logger.error("getCapitolo(): clientRicercaService.ricercaCapitoloUscitaGestione() ha restituito esito negativo: "+msgErrore);
				throw new ConsultaImpegniException(msgErrore);
			}
		}
				
		logger.info("getCapitolo(): FINE");
		
		return capitolo;
	}
	
	private ImpegnoVO popolaImpegnoVO(
			it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Impegno impegno,
			CapitoloUscitaGestione capitolo,
			String annoEsercizio
			) {
		ImpegnoVO vo = new ImpegnoVO();				
		
		if (annoEsercizio != null)
			vo.setAnnoEsercizio(annoEsercizio.toString());
		
		vo.setAnnoImpegno(NumberUtil.getStringValue(impegno.getAnnoImpegno()));
		vo.setAnnoPerente(NumberUtil.getStringValue(impegno.getAnnoImpegnoRiaccertato()));
		if ("0".equalsIgnoreCase(vo.getAnnoPerente()))
				vo.setAnnoPerente(null);
		vo.setCig(impegno.getCig());
		vo.setCodiceBeneficiario(impegno.getCodiceSoggetto());
		vo.setCup(impegno.getCup());
		vo.setImportoDisponibilitaLiquidare(impegno.getDisponibilitaLiquidare());
		vo.setImportoAttualeImpegno(impegno.getImporto());		
		vo.setNumeroArticoloCapitolo(NumberUtil.getStringValue(impegno.getNumeroArticolo()));
		vo.setNumeroCapitolo(NumberUtil.getStringValue(impegno.getNumeroCapitolo()));
		vo.setNumeroPerente(NumberUtil.getStringValue(impegno.getNumImpegnoRiaccertato()));
		if (impegno.getStato() != null)
			vo.setDescBreveStatoImpegno(impegno.getStato().getCodice());
		vo.setDescImpegno(impegno.getDescrizione());
		vo.setNumeroImpegno(impegno.getNumeroImpegno());
		
		if (impegno.getProvvedimento() != null) {
			vo.setAnnoProvvedimento(NumberUtil.getStringValue(impegno.getProvvedimento().getAnnoProvvedimento()));			
			vo.setDescBreveDirezioneProvvedimento(this.getDescBreveBySac(impegno.getProvvedimento().getSac()));
			vo.setDescDirezioneProvvedimento(this.getDescBySac(impegno.getProvvedimento().getSac()));
			vo.setNumeroProvvedimento(NumberUtil.getStringValue(impegno.getProvvedimento().getNumeroProvvedimento()));
			vo.setDescBreveTipoProvvedimento(impegno.getProvvedimento().getCodiceTipoProvvedimento());
			vo.setCodiceProvvedimento(impegno.getProvvedimento().getCodiceTipoProvvedimento());
		}
		
		if (capitolo != null) {
			vo.setDescCapitolo(capitolo.getDescrizione());
			if (capitolo.getTipoFondo() != null)
				vo.setProvenienzaCapitolo(capitolo.getTipoFondo().getCodice());
			if (capitolo.getSac() != null)
				vo.setDescBreveEnteCapitolo(this.getDescBreveBySac(capitolo.getSac()));
			if (capitolo.getTipoFinanziamento() != null)
				//vo.setDescBreveTipoFondo(capitolo.getTipoFinanziamento().getDescrizione());
				vo.setDescBreveTipoFondo(capitolo.getTipoFinanziamento().getCodice());
		}
		
		if (!StringUtil.isEmpty(impegno.getCodiceSoggetto())) {
			RicercaSinteticaSoggettiResponse response = this.eseguiRicercaSinteticaSoggetti(impegno.getCodiceSoggetto());
			if (response != null && response.getEsito() != null && 
				response.getEsito() == Esito.SUCCESSO) {
				Soggetto[] soggetti = response.getSoggetti();
				if (soggetti != null && soggetti.length > 0) {
					String cf = soggetti[0].getCodiceFiscale();					// da inserire in PBANDI_T_IMPEGNO.CODICE_FISCALE.
					if (!StringUtil.isEmpty(cf))
						vo.setCodiceFiscale(cf.trim());
					String ragsoc = soggetti[0].getRagioneSociale();			// da inserire in PBANDI_T_IMPEGNO.RAGSOC.
					if (!StringUtil.isEmpty(ragsoc))
						vo.setRagioneSocialeBeneficiario(ragsoc.toUpperCase());						
				}
			}
		}
		
		return vo;
	}
	
	private String getDescBreveBySac(
		it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac) {			
		
		if (sac == null || sac.getCodice() == null)
			return null;

		String desc = "";
		//if (Constants.CONTABILIA_COD_TIPO_STRUTTURA_CDR.equals(sac.getCodiceTipoStruttura()))
		//	desc = sac.getCodice();
		//else {
			// a quanto pare il punto separatore non c'� pi�: ora prendo i primi 3 char.
			// String [] splits = sac.getCodice().split("\\.");
			// if (splits != null && splits.length > 0) desc = splits[0];
			if (sac.getCodice().length() > 3) {
				desc = sac.getCodice().substring(0,3);
			} else {
				desc = sac.getCodice();
			}
		//}
			 			
		return desc;
	}
	
	private String getDescBySac(
			it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.StrutturaAmministrativa sac) {			
			
			if (sac == null || sac.getCodice() == null)
				return null;
				 			
			return sac.getDescrizione();
	}
	
	// ****************************************************************
	//    CONSULTA IMPEGNI - FINE
	// ****************************************************************

	
	// ****************************************************************
	//    CONSULTA BENEFICIARI - INIZIO
	// ****************************************************************
	
	// WEB SERVICES DI CONTABILIA USATI: 
	//	ricercaDettaglioSoggetto()

	// Esegue la ricerca dei Beneficiari per CodiceFiscale.
	public ConsultaBeneficiariVO consultaBeneficiari(String codiceFiscale,
			String partitaIva,
			Long maxRec,Long idUtente) {
	
		logger.info("consultaBeneficiari(): INIZIO");
		logger.info("consultaBeneficiari(): codiceFiscale benef: " + codiceFiscale);
		ConsultaBeneficiariVO consultaBeneficiariVO = new ConsultaBeneficiariVO();
		consultaBeneficiariVO.setResult(new ResultVO());
		consultaBeneficiariVO.getResult().setEsito(true);
		Long idTracciamento = null;
		long start = 0L;
		try {
				
			idTracciamento = traceManagerBusiness
					.tracciaOperazione("RicercaService.ricercaDettaglioSoggetto",idUtente);
			start = System.currentTimeMillis();
			
			// Chiama il web service RicercaService.ricercaDettaglioSoggetto() di Contabilia (Bilancio).
			RicercaDettaglioSoggettiResponse output = null;
			try {				
				logger.info("consultaBeneficiari(): chiamo RicercaService.ricercaDettaglioSoggetto() via Web Service.");
				output = eseguiRicercaDettaglioSoggetto(codiceFiscale, null);
			} catch (Exception ex) {
				logger.error("consultaBeneficiari(): ERRORE in eseguiRicercaDettaglioSoggetto() : "+ex);
				throw new Exception("RicercaService.ricercaDettaglioSoggetto() ha sollevato una eccezione.");				
			}
			logger.info("\n\nRESPONSE DI ricercaDettaglioSoggetto() di Contabilia:\n"+objectToXML(output)+"\n\n");
			traceManagerBusiness.tracciaEsitoPositivo(idTracciamento, start);
			
			// Verifico che la response sia valorizzata.
			if (output == null) {
				logger.error("consultaBeneficiari(): ERRORE: eseguiRicercaDettaglioSoggetto() di BILANCIO ha restituito NULL");
				throw new Exception("eseguiRicercaDettaglioSoggetto() ha restituito NULL.");				
			}
			
			// Elaboro la response di Contabilia.
			logger.info("consultaBeneficiari(): esito = "+output.getEsito().getValue());
			if (output.getEsito() == Esito.SUCCESSO) {				
				// Popolo consultaBeneficiariVO con i dati provenienti da Contabilia. 
				consultaBeneficiariVO = this.popolaConsultaBeneficiariVO(output, codiceFiscale);			
			} else {
				String msgErrore = "";
				if (output.getErrori() != null && output.getErrori().length > 0)
					msgErrore = output.getErrori(0).getCodice()+" - "+output.getErrori(0).getDescrizione();
				logger.error("consultaBeneficiari(): RicercaService.ricercaDettaglioSoggetto() ha restituito esito negativo: "+msgErrore);
				throw new ConsultaImpegniException(msgErrore);
			}

			logger.info("\n\nOUTPUT di consultaBeneficiari():\n"+objectToXML(consultaBeneficiariVO)+"\n\n");
			
		} catch (Exception e) {
			consultaBeneficiariVO.getResult().setEsito(false);
			String message = "Impossibile la consultazione beneficiari: "
					+ e.getMessage();
			logger.error(message, e);
		}  
		logger.info("consultaBeneficiari(): FINE");
		return consultaBeneficiariVO;
	}

	private ConsultaBeneficiariVO popolaConsultaBeneficiariVO(RicercaDettaglioSoggettiResponse responseContabilia, String cf) {
		ConsultaBeneficiariVO consultaBeneficiariVO = new ConsultaBeneficiariVO();
		consultaBeneficiariVO.setCodFisc(cf);
		
		ResultVO result = new ResultVO();
		result.setEsito(true);
		consultaBeneficiariVO.setResult(result);
		
		// Scorro i soggetti cercando quello in stato VALIDO
		ArrayList<DatiBeneficiarioVO> listaDatiBeneficiarioVO = new ArrayList<DatiBeneficiarioVO>(); 
		Soggetto[] soggetti = responseContabilia.getSoggetti();
		if (soggetti != null) {
			logger.info("popolaConsultaBeneficiariVO(): num soggetti = "+soggetti.length);
			for (Soggetto s : soggetti) {
				if (Constants.CONTABILIA_STATO_VALIDO.equalsIgnoreCase(s.getStato().getCodice())) {
					logger.info("popolaConsultaBeneficiariVO(): considero soggetto "+s.getCodice()+" con stato "+s.getStato().getCodice());
					
					// Popola FornitoreVO con i dati ricevuti da Contabilia. 					
					FornitoreVO f = this.popolaFornitoreVO(s);
					
					// Popola una lista di BeneficiarioVO con i dati ricevuti da Contabilia.
					ArrayList<BeneficiarioVO> listaBeneficiarioVO = this.popolaBeneficiarioVO(s);
					
					DatiBeneficiarioVO datiBeneficiarioVO = new DatiBeneficiarioVO();
					datiBeneficiarioVO.setFornitore(f);
					int dim = listaBeneficiarioVO.size();
					datiBeneficiarioVO.setBeneficiario(listaBeneficiarioVO.toArray(new BeneficiarioVO[dim]));
					listaDatiBeneficiarioVO.add(datiBeneficiarioVO);
				} else {
					logger.info("popolaConsultaBeneficiariVO(): ignorato soggetto "+s.getCodice()+" con stato "+s.getStato().getCodice());
				}
			}
		}
		int dim = listaDatiBeneficiarioVO.size();
		consultaBeneficiariVO.setBeneficiario(listaDatiBeneficiarioVO.toArray(new DatiBeneficiarioVO[dim]));
		return consultaBeneficiariVO;
	}
	
	private FornitoreVO popolaFornitoreVO (Soggetto s) {
		FornitoreVO f = new FornitoreVO();
		f.setCodben(new Integer(s.getCodice()));
		f.setCodfisc(s.getCodiceFiscale());
		f.setPartiva(s.getPartitaIva());
		f.setRagsoc(s.getRagioneSociale());
		//f.setSesso(null);
		//f.setDataNascita(null);
		//f.setComuneNascita(null);
		//f.setProvNascita(null);		
		if (s.getNaturaGiuridica() != null) {
			NaturaGiuridica n = s.getNaturaGiuridica(); 
			if (Constants.CONTABILIA_COD_PERSONA_GIURIDICA_NO_IVA.equalsIgnoreCase(n.getCodice()) ||
				Constants.CONTABILIA_COD_PERSONA_GIURIDICA_IVA.equalsIgnoreCase(n.getCodice()))
				f.setPersonaFisica(false);
			else
				f.setPersonaFisica(true);
		}
		if (s.getContatti() != null) {
			Contatti c = s.getContatti();
			f.setEmail(c.getEmail());						
			f.setFax(c.getFax());						
			f.setTel1(c.getTelefono());
			f.setTel2(c.getCellulare());
		}
		if (s.getIndirizzoPrincipale() != null) {
			Recapito r = s.getIndirizzoPrincipale();
			f.setCap(r.getCap());
			f.setCodComune(r.getCodiceIstatComune());
			//f.setComune(r.getComune());
			f.setComune(this.getComuneByIstat(r.getCodiceIstatComune()));			
			f.setProv(r.getProvincia());
			f.setVia(r.getSedime()+" "+r.getIndirizzo());						
		}
		return f;
	}
	
	private ArrayList<BeneficiarioVO> popolaBeneficiarioVO(Soggetto s) {
		ArrayList<BeneficiarioVO> lista = new ArrayList<BeneficiarioVO>();
		
		if (s.getElencoModalitaPagamento() != null) {
			for (ModalitaPagamento m : s.getElencoModalitaPagamento()) {
				if (Constants.CONTABILIA_STATO_VALIDO.equalsIgnoreCase(m.getStato().getCodice())) {
					BeneficiarioVO b = new BeneficiarioVO();
					
					b.setCodBen(new Integer(s.getCodice()));
					b.setProgBen(new Integer(m.getCodice()));
					
					// Codice della modalit� di pagamento da reperire da codiceTipoAccredito.
					// serve decodifica da codice contabilia a codice bandi?
					b.setCodaccre(m.getCodiceTipoAccredito());
					b.setDescrCodAccre(m.getDescrizioneTipoAccredito());
					
					//b.setRagSocSede(s.getRagioneSociale());
					
					// I dati bancari (cab, abi, iban, num conto, etc): non sono pi� passati singolamente
					// ma all'interno della descrizione di una modalit� di pagamento.
					// Quindi se uno vuole l'iban o il numero di conto deve estrarlo come sottostringa dal campo descrizione.
					// Abi,descrAbi,Bic,Cab,descrCab,Cin
					b.setIban(this.estraiIBAN(m.getDescrizione()));
					b.setNroCC(this.estraiConto(m.getDescrizione()));					
															
					if (m.getSede() != null) {
						boolean sedeModPagTrovata = false;
						String descrizione = m.getSede().getDescrizione();
						Sede sede = cercaSedeModalitaPagamento(descrizione, s);
						if (sede != null && sede.getRecapito() != null) {
							sedeModPagTrovata = true;
							Recapito r = sede.getRecapito();
							b.setCapSede(r.getCap());
							//b.setComuneSede(r.getCodiceIstatComune());
							//b.setComuneSede(r.getComune());
							b.setComuneSede(this.getComuneByIstat(r.getCodiceIstatComune()));
							logger.info("popolaBeneficiarioVO(): istat = "+r.getCodiceIstatComune()+"; comune = "+b.getComuneSede());
							b.setProvSede(r.getProvincia());
							b.setViaSede(r.getSedime()+" "+r.getIndirizzo());
							b.setRagSocSede(descrizione);
						}
						if (!sedeModPagTrovata) {
							// Se non c'� una sede, riporto i dati dell'indirizzo principale.
							if (s.getIndirizzoPrincipale() != null) {
								b.setCapSede(s.getIndirizzoPrincipale().getCap());								
								//b.setComuneSede(s.getIndirizzoPrincipale().getComune());
								b.setComuneSede(this.getComuneByIstat(s.getIndirizzoPrincipale().getCodiceIstatComune()));
								logger.info("popolaBeneficiarioVO(): istat indirizzo principale = "+s.getIndirizzoPrincipale().getCodiceIstatComune()+"; comune = "+b.getComuneSede());
								b.setProvSede(s.getIndirizzoPrincipale().getProvincia());
								b.setViaSede(s.getIndirizzoPrincipale().getSedime()+" "+s.getIndirizzoPrincipale().getIndirizzo());
							}
						}
					}
					
					
					// campi da gestire?
					//codBenCedente				Codice del beneficiario che ha ceduo il credito 
					//codBenCeduto				Codice del beneficiario a cui � stato ceduto il credito (� il soggetto a cui viene fatto il reale pagamento)
					//progBenCeduto				Progressivo della modalit� di pagamento a cui � stato ceduto il credito (� la modalit� di pagamento usata per fare il pagamento)
					//codFiscQuiet				Codice fiscale del quietanzante
					//flagTutteErog				Flag per indicare, per le sole modalit� di pagamento 
					//mailModPag				Indirizzo Mail della  sulla sede secondaria
					//Quiet						Nominativo del quietanzante
					//ragSocSede				Ragione sociale aggiuntiva della sede secondaria
					//							serve popolarla? la sede sociale c'� gi� nel Fornitore
					
					lista.add(b);
				}					
			}			
		}
		
		Collections.sort(lista, new Comparator() {
			  public int compare(Object o1, Object o2) {
				  BeneficiarioVO b1 = (BeneficiarioVO) o1;
				  BeneficiarioVO b2 = (BeneficiarioVO) o2;
			    return b1.getProgBen().compareTo(b2.getProgBen());
			  }
			});
				
		return lista;
	}
	
	private String getComuneByIstat(String istat) {
		if (StringUtil.isEmpty(istat))
			return "";
		PbandiDComuneVO vo = new PbandiDComuneVO();
		vo.setCodIstatComune(istat);
		FilterCondition filtro = new FilterCondition<PbandiDComuneVO>(vo);
		NullCondition<PbandiDComuneVO> nullDtFineValidita = new NullCondition<PbandiDComuneVO>(
				PbandiDComuneVO.class,"dtFineValidita");
		AndCondition andCond = new AndCondition<PbandiDComuneVO>(filtro,nullDtFineValidita);
		List<PbandiDComuneVO> lista = genericDAO.findListWhere(andCond);
		if (lista == null || lista.size() == 0)
			return "";
		else
			return lista.get(0).getDescComune();
	}
	
	// Param descrizione = descrizione contenuta in una modalit� di pagamento.
	// Restituisce la sede avente la stessa descrizione presente tra quelle del soggetto.
	private Sede cercaSedeModalitaPagamento(String descrizione, Soggetto soggetto) {
		Sede output = null;
		if (!StringUtil.isEmpty(descrizione) &&
			soggetto.getElencoSedi() != null) {
			for(Sede s : soggetto.getElencoSedi()) {
				if (Constants.CONTABILIA_STATO_VALIDO.equalsIgnoreCase(s.getStato().getCodice())) {
					if (descrizione.equalsIgnoreCase(s.getDescrizione()))
						output = s;
				}
			}
		}		
		return output;
	}
	
	// ****************************************************************
	//    CONSULTA BENEFICIARI - FINE
	// ****************************************************************
	
	
	// ****************************************************************
	//    INSERISCI ATTO LIQUIDAZIONE (CREA ATTO) - INIZIO
	// ****************************************************************
	
	// WEB SERVICES DI CONTABILIA USATI: 
	//	elaboraDocumentoAsync()

	public InserisciAttoLiquidazioneVO inserisciAttoLiquidazione (
			InserisciAttoLiquidazioneVO inserisciAttoLiquidazioneVO,Long idUtente) {
		
		inserisciAttoLiquidazioneVO.setResult(new ResultVO());
		Long idTracciamento = null;
		long start = 0L;
		ElaboraDocumentoAsyncResponse elaboraDocumentoAsyncResponse = null;
		
		try {
			
			// Loggo i dati di input.
			logger.info("\n\ninserisciAttoLiquidazione(): DATI DI INPUT - INIZIO");
			logger.info("\n\n"+objectToXML(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getAtto())+"\n\n");
			logger.info("\n\n"+objectToXML(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getBeneficiario())+"\n\n");
			logger.info("\n\n"+objectToXML(inserisciAttoLiquidazioneVO.getAttoLiquidazione().getFornitore())+"\n\n");
			DettaglioConsultazioneAttoVO[] dettagli = inserisciAttoLiquidazioneVO.getAttoLiquidazione().getDettagliAtto(); 
			if (dettagli != null) {
				for(int i = 0; i < dettagli.length; i++) {
					logger.info("\n\n"+objectToXML(dettagli[i])+"\n\n");
				}
			}
			ImpegnoVO[] impegni = inserisciAttoLiquidazioneVO.getImpegni();
			if (impegni != null) {
				for(int i = 0; i < impegni.length; i++) {
					logger.info("\n\n"+objectToXML(impegni[i])+"\n\n");
				}
			}
			logger.info("\n\ninserisciAttoLiquidazione(): DATI DI INPUT - FINE");
			
			ElaboraDocumento elaboraDocumento = new ElaboraDocumento(); 
			
			elaboraDocumento.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
			elaboraDocumento.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
			elaboraDocumento.setCodiceTipoDocumento(Constants.CONTABILIA_CODICE_TIPO_DOCUMENTO);
			
			String annoAtto = inserisciAttoLiquidazioneVO.getAttoLiquidazione().getAtto().getAnnoAtto();			
//			if (!StringUtils.isEmpty(annoAtto))
			if (annoAtto != null && annoAtto.length() > 0)
				elaboraDocumento.setAnnoBilancio(new Integer(annoAtto));
			
			String xml = bilancioXMLManager.creaXmlElaboraDocumento(inserisciAttoLiquidazioneVO);			
			elaboraDocumento.setContenutoDocumento(xml);
			
			logger.info("\n\ninserisciAttoLiquidazione(): REQUEST elaboraDocumentoAsync() - INIZIO");
			logger.info("codiceEnte = "+elaboraDocumento.getCodiceEnte());
			logger.info("codiceFruitore = "+elaboraDocumento.getCodiceFruitore());
			logger.info("codiceTipoDocumento = "+elaboraDocumento.getCodiceTipoDocumento());
			logger.info("annoBilancio = "+elaboraDocumento.getAnnoBilancio());
			logger.info("contenutoDocumento = "+elaboraDocumento.getContenutoDocumento());
			logger.info("\n\ninserisciAttoLiquidazione(): REQUEST elaboraDocumentoAsync() - FINE");
			
			idTracciamento = traceManagerBusiness.tracciaOperazione("BilancioSrv.inserisciAttoLiquidazione", idUtente);
			start = System.currentTimeMillis();
			
			logger.info("inserisciAttoLiquidazione(): chiamo clientDocumentiService.elaboraDocumentoAsync() via Web Service.");
			elaboraDocumentoAsyncResponse = clientDocumentiService.elaboraDocumentoAsync(elaboraDocumento);
			
			if (elaboraDocumentoAsyncResponse == null)
				throw new Exception("elaboraDocumentoAsyncResponse nullo");
			
			String esitoContabilia = elaboraDocumentoAsyncResponse.getEsito().getValue();
			String idOperazioneAsincrona = "";
			if (elaboraDocumentoAsyncResponse.getIdOperazioneAsincrona() != null)
				idOperazioneAsincrona = elaboraDocumentoAsyncResponse.getIdOperazioneAsincrona().toString();
			
			String erroreContabilia = "";
			Errore[] errori = elaboraDocumentoAsyncResponse.getErrori();
			if (errori != null && errori.length > 0)
				erroreContabilia = errori[0].getCodice()+" - "+errori[0].getDescrizione();
			
			logger.info("RESPONSE DI elaboraDocumentoAsync(): esito = "+esitoContabilia);
			logger.info("RESPONSE DI elaboraDocumentoAsync(): idOperazioneAsincrona = "+idOperazioneAsincrona);
			logger.info("RESPONSE DI elaboraDocumentoAsync(): errore = "+erroreContabilia);
			
			// Inserisce un record in PBANDI_L_LOG_STATO_ELAB_DOC e memorizza il num doc spesa in PBANDI_T_ATTO_LIQUIDAZIONE.
			this.aggiornaDbCreaAtto(
					inserisciAttoLiquidazioneVO.getIdAttoLiquidazione(), 
					esitoContabilia, 
					erroreContabilia, 
					idOperazioneAsincrona,
					inserisciAttoLiquidazioneVO.getAttoLiquidazione().getAtto().getNumeroDocSpesa(),
					elaboraDocumento.getContenutoDocumento());

			inserisciAttoLiquidazioneVO.setIdOperazioneAsincrona(idOperazioneAsincrona);
			inserisciAttoLiquidazioneVO.setEsitoContabilia(esitoContabilia);
			inserisciAttoLiquidazioneVO.setErroreContabilia(erroreContabilia);
			if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(esitoContabilia)) {
				inserisciAttoLiquidazioneVO.getResult().setEsito(true);
			} else {				
				inserisciAttoLiquidazioneVO.getResult().setEsito(false);
			}
			
			traceManagerBusiness.tracciaEsitoPositivo(idTracciamento, start);			
			
		} catch (Exception ex) {
			logger.error("inserisciAttoLiquidazione(): ERRORE imprevisto: "+ex.getMessage());
			inserisciAttoLiquidazioneVO.getResult().setEsito(false);
		}
		
		return inserisciAttoLiquidazioneVO;
	}
	
	private void aggiornaDbCreaAtto(Long idAttoLiquidazione, String esitoElaboraDocumento, String erroreElaboraDocumento, String idOperazioneAsincrona, String numeroDocSpesa, String xmlRequest) throws Exception {
		
		// Inserisco un record in PBANDI_L_LOG_STATO_ELAB_DOC.
		// idChiamata popolato con sequence SEQ_PBANDI_L_LOG_ST_ELAB_DOC (vedi SequenceManager.java)
		logger.info("aggiornaDbCreaAtto(): inserisco un record in PBANDI_L_LOG_ST_ELAB_DOC.");
		PbandiLLogStatoElabDocVO vo = new PbandiLLogStatoElabDocVO();
		vo.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione));
		vo.setEsitoElaboraDocumento(esitoElaboraDocumento);
		vo.setErroreElaboraDocumento(erroreElaboraDocumento);
		vo.setDtElaboraDocumento(getDataOdierna());
//		if (!StringUtils.isEmpty(idOperazioneAsincrona)) {
		if (idOperazioneAsincrona != null && idOperazioneAsincrona.length() > 0) {
			vo.setIdOperazioneAsincrona(new BigDecimal(idOperazioneAsincrona));
		}
		vo = genericDAO.insert(vo);
		
		// Inserisce come CLOB in PBANDI_L_LOG_STATO_ELAB_DOC l'xml della request a Contabilia.
		logger.info("aggiornaDbCreaAtto(): inserisco l'xml passato a Contabilia in PBANDI_L_LOG_ST_ELAB_DOC.");
		PbandiLLogStatoElabDocVO filtro = new PbandiLLogStatoElabDocVO();
		filtro.setIdChiamata(vo.getIdChiamata());
		genericDAO.updateClob(vo, "requestelaboradocumento", xmlRequest);

		// Aggiorna numero doc spesa su PBANDI_T_ATTO_LIQUIDAZIONE.
  		logger.info("aggiornaDbCreaAtto(): aggiorno PBANDI_T_ATTO_LIQUIDAZIONE.");
  		PbandiTAttoLiquidazioneVO attoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		attoLiquidazioneVO.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione));
		attoLiquidazioneVO.setNumeroDocumentoSpesa(numeroDocSpesa);
		genericDAO.update(attoLiquidazioneVO);		
	}
	
	// ****************************************************************
	//    INSERISCI ATTO LIQUIDAZIONE (CREA ATTO) - FINE
	// ****************************************************************

	
	// ****************************************************************
	//    LEGGI STATO ELABORAZIONE - INIZIO
	// ****************************************************************
	
	// WEB SERVICES DI CONTABILIA USATI: 
	//	leggiStatoElaborazioneDocumento()

	// Sono previsti 2 tipi di dati di input:
	//  - idAttoLiquidazione
	//    oppure
	//  - idOperazioneAsincrona
	// In input va passato solo 1 dei 2.
	// Nota: InputLeggiStatoElaborazioneDocDTO.annoBilancio � venuto poi fuori che non serve come parametro per Contabilia. 
	public EsitoLeggiStatoElaborazioneDocDTO leggiStatoElaborazioneDoc (InputLeggiStatoElaborazioneDocDTO input, Long idUtente) {
		
		logger.info("\n\nInputLeggiStatoElaborazioneDocDTO:");
		logger.shallowDump(input, "info");
		logger.info("\n\n");
		
		EsitoLeggiStatoElaborazioneDocDTO esito = null;
		if (input.getIdAttoLiquidazione() != null) {
			esito = leggiStatoElaborazioneDocByIdAttoLiq(input.getIdAttoLiquidazione(),idUtente);
		} else if (input.getIdOperazioneAsincrona() != null) {
			esito = leggiStatoElaborazioneDocByIdOpAsinc(input.getIdOperazioneAsincrona(),idUtente);
		} else {
			logger.error("leggiStatoElaborazioneDoc(): ERRORE: parametri di input non corretti.");
			esito.setEsito(false);
			esito.setErrore("Errore imprevisto nella lettura dello stato.");
		}

		return esito;
	}

	private EsitoLeggiStatoElaborazioneDocDTO leggiStatoElaborazioneDocByIdAttoLiq (Long idAttoLiquidazione, Long idUtente) {		
		Long idTracciamento = null;
		long start = 0L;
		EsitoLeggiStatoElaborazioneDocDTO esito = new EsitoLeggiStatoElaborazioneDocDTO();
		try {			

			// Recupero l'idOperazioneAsincrona.
			logger.info("leggiStatoElaborazioneDocByIdAttoLiq(): cerco idOperazioneAsincrona in PBANDI_L_LOG_STATO_ELAB_DOC tramite idAttoLiquidazione = "+idAttoLiquidazione);
			PbandiLLogStatoElabDocVO voLog = new PbandiLLogStatoElabDocVO();
			voLog.setIdAttoLiquidazione(new BigDecimal(idAttoLiquidazione));
			voLog.setDescendentOrder("idChiamata");		// il primo record sar� quello inserito per ultimo.						
			List<PbandiLLogStatoElabDocVO> lista = genericDAO.findListWhere(voLog);
			if (lista.size() == 0) {
				logger.error("leggiStatoElaborazioneDocByIdAttoLiq(): ERRORE: idOperazioneAsincrona non trovato.");
				esito.setEsito(false);
				esito.setErrore("Errore imprevisto: idOperazioneAsincrona non trovato.");
			} else {
				Integer idOperazioneAsincrona = lista.get(0).getIdOperazioneAsincrona().intValue();
				logger.info("leggiStatoElaborazioneDocByIdAttoLiq(): idOperazioneAsincrona trovato = "+idOperazioneAsincrona);
				esito = leggiStatoElaborazioneDocByIdOpAsinc(idOperazioneAsincrona,idUtente);
			}
			
		} catch (Exception ex) {
			logger.error("leggiStatoElaborazioneDocByIdAttoLiq(): ERRORE inatteso: "+ex.getMessage());
			esito.setEsito(false);
			esito.setErrore("Errore imprevisto nella lettura dello stato.");
			return esito;
		}
		
		return esito;
	}

	private EsitoLeggiStatoElaborazioneDocDTO leggiStatoElaborazioneDocByIdOpAsinc (Integer idOperazioneAsincrona, Long idUtente) {		
		Long idTracciamento = null;
		long start = 0L;
		EsitoLeggiStatoElaborazioneDocDTO esito = new EsitoLeggiStatoElaborazioneDocDTO();
		try {			
			
			// Compone la request.
			LeggiStatoElaborazioneDocumento request = new LeggiStatoElaborazioneDocumento(); 			
			request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
			request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
			request.setIdOperazioneAsincrona(idOperazioneAsincrona);
			
			logger.info("\n\nREQUEST di clientDocumentiService.leggiStatoElaborazioneDocumento():");
			logger.shallowDump(request, "info");
			logger.info("\n\n");
			
			idTracciamento = traceManagerBusiness.tracciaOperazione("BilancioSrv.leggiStatoElaborazioneDocumento", idUtente);
			start = System.currentTimeMillis();
			
			// Chiama il servizio.
			logger.info("leggiStatoElaborazioneDocByIdOpAsinc(): chiamo clientDocumentiService.leggiStatoElaborazioneDocumento() via Web Service.");
			LeggiStatoElaborazioneDocumentoResponse response = null;
			try {
				response = clientDocumentiService.leggiStatoElaborazioneDocumento(request);
			} catch (Exception ex) {
				logger.error("leggiStatoElaborazioneDocByIdOpAsinc(): ERRORE restituito da clientDocumentiService.leggiStatoElaborazioneDocumento() di BILANCIO: "+ex.getMessage());
				esito.setEsito(false);
				esito.setErrore("Errore imprevisto nella lettura dello stato.");
				return esito;
			}
			
			if (response == null) {
				logger.error("leggiStatoElaborazioneDocByIdOpAsinc(): response di clientDocumentiService.leggiStatoElaborazioneDocumento() NULLA");
				esito.setEsito(false);
				esito.setErrore("Errore imprevisto nella lettura dello stato.");
				return esito;
			}
			
			traceManagerBusiness.tracciaEsitoPositivo(idTracciamento, start);
			
			// Legge i dati della response.
			String responseEsitoContabilia = response.getEsito().getValue();
			String responseStatoElabContabilia = response.getStatoElaborazione().getValue();
			String responseErrore = null; 
			if (response.getErrori() != null && response.getErrori().length > 0)
				responseErrore = response.getErrori(0).getCodice()+" - "+response.getErrori(0).getDescrizione();
			logger.info("\n");
			logger.info("leggiStatoElaborazioneDoc(): RESPONSE: esito = "+responseEsitoContabilia);
			logger.info("leggiStatoElaborazioneDoc(): RESPONSE: stato elaborazione = "+responseStatoElabContabilia);
			logger.info("leggiStatoElaborazioneDoc(): RESPONSE: errore = "+responseErrore);
			logger.info("\n");
			
			// Compongo l'output del metodo.			
			if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(responseEsitoContabilia)) 
				esito.setEsito(true);
			else
				esito.setEsito(false);
			esito.setEsitoContabilia(responseEsitoContabilia);
			esito.setStatoElaborazioneContabilia(responseStatoElabContabilia);
			esito.setErrore(responseErrore);
			
			// Se � il caso, estrae il numero atto dall'xml restituito dal servizio
			// e lo aggiunge all'output del metodo.
			String numeroAttoContabilia = "";
			java.sql.Date dataEmissione = null;
			if (Constants.CONTABILIA_STATO_ELAB_CONCLUSA.equalsIgnoreCase(responseStatoElabContabilia) &&
				Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(responseEsitoContabilia)) {
			
				BilancioXMLManagerOutput output = bilancioXMLManager.leggiXmlLeggiStatoElaborazioneDocumento(response.getResponseElaborazione());
				numeroAttoContabilia = output.getNumeroAtto();
				dataEmissione = output.getDataEmissione();
				
//				if (StringUtils.isEmpty(numeroAttoContabilia)) {
				if (numeroAttoContabilia == null || numeroAttoContabilia.length() <= 0) {
					logger.error("leggiStatoElaborazioneDocByIdOpAsinc(): ERRORE: nessun numero atto trovato nella response di Contabilia.");
					String msgErrore = "Numero atto non trovato nella risposta del servizio: forzato esito FALLIMENTO.";
					
					// Aggiorna il db
					this.aggiornaDbDopoLeggiStatoElab(
							idOperazioneAsincrona,
							Constants.CONTABILIA_ESITO_FALLIMENTO,
							responseStatoElabContabilia,
							msgErrore,
							null, null, idUtente);
					
					esito.setEsito(false);
					esito.setEsitoContabilia(Constants.CONTABILIA_ESITO_FALLIMENTO);
					esito.setErrore(msgErrore);	
					return esito;
				}
				logger.info("leggiStatoElaborazioneDoc(): numero atto restituito da Contabilia = "+numeroAttoContabilia);
			}			
			esito.setNumeroAtto(numeroAttoContabilia);
			
			// Aggiorna il db.
			this.aggiornaDbDopoLeggiStatoElab(
				idOperazioneAsincrona,
				responseEsitoContabilia, 
				responseStatoElabContabilia,
				responseErrore,
				numeroAttoContabilia,
				dataEmissione,
				idUtente);
												
			logger.info("\n\n:EsitoLeggiStatoElaborazioneDocDTO:");
			logger.shallowDump(esito, "info");
			logger.info("\n\n");
			
		} catch (Exception ex) {
			logger.error("leggiStatoElaborazioneDocByIdOpAsinc(): ERRORE inatteso: "+ex.getMessage());
			esito.setEsito(false);
			esito.setErrore("Errore imprevisto nella lettura dello stato.");
			return esito;
		}
		
		return esito;
	}
	
	private void aggiornaDbDopoLeggiStatoElab(Integer idOperazioneAsincrona, String esitoLeggiStato, String statoElaborazione, String erroreLeggiStato, String numeroAtto, java.sql.Date dataEmissione, Long idUtente) throws Exception {		

		logger.info("aggiornaDbDopoLeggiStatoElab(): idOperazioneAsincrona = "+idOperazioneAsincrona);
		logger.info("aggiornaDbDopoLeggiStatoElab(): esitoLeggiStato = "+esitoLeggiStato);
		logger.info("aggiornaDbDopoLeggiStatoElab(): statoElaborazione = "+statoElaborazione);
		logger.info("aggiornaDbDopoLeggiStatoElab(): erroreLeggiStato = "+erroreLeggiStato);		
		logger.info("aggiornaDbDopoLeggiStatoElab(): numeroAtto = "+numeroAtto);
		
		// Cerca PBANDI_L_LOG_STATO_ELAB_DOC per idOperazioneAsincrona.
		logger.info("aggiornaDbDopoLeggiStatoElab(): cerco PBANDI_L_LOG_STATO_ELAB_DOC per idOperazioneAsincrona.");
		PbandiLLogStatoElabDocVO voLog = new PbandiLLogStatoElabDocVO();
		voLog.setIdOperazioneAsincrona(new BigDecimal(idOperazioneAsincrona));
		voLog = genericDAO.findSingleOrNoneWhere(voLog);
		if (voLog == null) {
			String msg = "PbandiLLogStatoElabDocVO non trovato con idOperazioneAsincrona = "+idOperazioneAsincrona;
			logger.error("aggiornaLogStatoElabDoc(): "+msg);
			throw new Exception(msg);
		}
		
		// Aggiorna il record e lo salva su db.
		logger.info("aggiornaDbDopoLeggiStatoElab(): aggiorno PBANDI_L_LOG_STATO_ELAB_DOC.");
		PbandiLLogStatoElabDocVO voLog2 = new PbandiLLogStatoElabDocVO();
		voLog2.setIdChiamata(voLog.getIdChiamata());
		voLog2.setEsitoLeggiStato(esitoLeggiStato);
		voLog2.setStatoElaborazione(statoElaborazione);
		voLog2.setErroreLeggiStato(erroreLeggiStato);
		voLog2.setDtLeggiStato(getDataOdierna());
  		genericDAO.update(voLog2);
  		
  		// Aggiorna stato e numero atto su PBANDI_T_ATTO_LIQUIDAZIONE.
  		logger.info("aggiornaDbDopoLeggiStatoElab(): aggiorno PBANDI_T_ATTO_LIQUIDAZIONE con IdAttoLiquidazione = "+voLog.getIdAttoLiquidazione());
  		PbandiTAttoLiquidazioneVO attoLiquidazioneVO = new PbandiTAttoLiquidazioneVO();
		attoLiquidazioneVO.setIdAttoLiquidazione(voLog.getIdAttoLiquidazione());
		attoLiquidazioneVO.setIdStatoAtto(new BigDecimal(getIdStatoAtto(esitoLeggiStato, statoElaborazione)));
		attoLiquidazioneVO.setDtEmissioneAtto(dataEmissione);
//		if (!StringUtils.isEmpty(numeroAtto))
		if(numeroAtto != null && numeroAtto.length() > 0 )
			attoLiquidazioneVO.setNumeroAtto(numeroAtto);
		attoLiquidazioneVO.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(attoLiquidazioneVO);  		  		
	}
	
	// Determina quale stato assegnare all'atto in base al suo stato di elaborazione su Contabilia.
	private String getIdStatoAtto(String esito, String statoElab) {
		
		logger.info("GetIdStatoAtto(): esito = "+esito);
		logger.info("GetIdStatoAtto(): statoElaborazione = "+statoElab);
		
		// Determina quale stato assegnare all'atto.
		String idStatoAtto = "";
		if (Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(esito)) {
			if (Constants.CONTABILIA_STATO_ELAB_AVVIATA.equalsIgnoreCase(statoElab)) {
				// SUCCESSO \ AVVIATA
				idStatoAtto = Constants.ID_STATO_ATTO_IN_LAVORAZIONE;
			} else if (Constants.CONTABILIA_STATO_ELAB_CONCLUSA.equalsIgnoreCase(statoElab)) {
				// SUCCESSO \ CONCLUSA
				idStatoAtto = Constants.ID_STATO_ATTO_EMESSO;
			} else if (Constants.CONTABILIA_STATO_ELAB_ERRORE.equalsIgnoreCase(statoElab)) {
				// SUCCESSO \ ERRORE
				idStatoAtto = Constants.ID_STATO_ATTO_BLOCCATO;
			}
		} else {
//			if (StringUtils.isEmpty(statoElab)) {
			if (statoElab != null && statoElab.length() > 0) {
				// FALLIMENTO \ VUOTO
				idStatoAtto = Constants.ID_STATO_ATTO_BOZZA;
			} else if (Constants.CONTABILIA_STATO_ELAB_CONCLUSA.equalsIgnoreCase(statoElab)) {
				// FALLIMENTO \ CONCLUSA
				idStatoAtto = Constants.ID_STATO_ATTO_BOZZA;
			}
		}
		logger.info("aggiornaAttoLiquidazione(): id StatoAtto = "+idStatoAtto);
		return idStatoAtto;
	}
	
	// ****************************************************************
	//    LEGGI STATO ELABORAZIONE - FINE
	// ****************************************************************
	
	
	// ****************************************************************
	//    UTILITY VAIE - INIZIO
	// ****************************************************************
	
	private String estraiIBAN(String stringa) {
		// Esempio di string in input: 
		// IBAN: IT48E0200801033000003874117 - Tipo accredito: CB - CONTO CORRENTE BANCARIO
		String output = "";
		if (!StringUtil.isEmpty(stringa)) {
			if ("IBAN".equalsIgnoreCase(stringa.substring(0, 4))) {
				output = stringa.substring(6);
				String[] lista = output.split("-");
				if (lista.length > 0 && lista[0] != null)
					output = lista[0].trim();
			}
		}
		return output;
	}
	
	private String estraiConto(String stringa) {
		// Esempio di string in input: 
		// Conto: 0061212 - Tipo accredito: GF - GIRO FONDI
		String output = "";
		if (!StringUtil.isEmpty(stringa)) {
			if ("Conto".equalsIgnoreCase(stringa.substring(0, 5))) {
				output = stringa.substring(7);
				String[] lista = output.split("-");
				if (lista.length > 0 && lista[0] != null)
					output = lista[0].trim();
			}
		}
		return output;
	}
	
	private String objectToXML(final Object object) {
		String xml = "";		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLEncoder xmlEncoder = new XMLEncoder(baos);
			xmlEncoder.writeObject(object);
			xmlEncoder.close();
			xml = baos.toString();
		} catch (Exception e) {
			xml = "Errore nella stampa dell'oggetto.";
		}
		return xml.toString();
	}
	
	private java.sql.Date getDataOdierna() {
		java.util.Date d = new java.util.Date();
		java.sql.Date oggi = new java.sql.Date(d.getTime());
		return oggi;
	}
	
	public boolean testResources() {
		boolean esito = true;
		logger.info("testResources(): INIZIO");
		try {			
			RicercaDettaglioSoggettiResponse r = eseguiRicercaDettaglioSoggetto("CF-finto", null);
			if (r == null)
				throw new Exception("Resonse = null");						
		} catch (Exception e) {
			logger.error("Eccezione durante la chiamata del servizio di bilancio: "+e.getMessage());
			esito = false;
		}
		logger.info("testResources(): FINE con esito = "+esito);
		return esito;
	}
	
	// ****************************************************************
	//    UTILITY VAIE - FINE
	// ****************************************************************
	

	
	// ****************************************************************
	//     RICHIAMO NUOVI SERVIZI CONTABILIA VIA WEB SERVICES
	// ****************************************************************
	
	// NOTA: i servizi di ricerca funzionano un po' ognuno a modo suo.
	// Ad esempio, a fronte di nessun record trovato, alcuni restituiscono esito = FALLIMENTO
	// (ricercaEstesaOrdinativiSpesa), mentre altri restituiscono esito = SUCCESSO (ricercaDocumentoSpesa).
	
	// Richiesta di Carla Gamba del 10/01/2018.
	// Come "tipo documento" si passa:
	//   - "ALG" se il numero documento di spesa contiene "ALG" (documento inserito da Contabilia).
	//   - "BANDI" altrimenti.
	
	private RicercaDocumentoSpesaResponse eseguiRicercaDocumentoSpesa(Integer anno, String numeroDocumento) {
		
		// Determina il tipo di documento.
		String tipoDocumento = Constants.CONTABILIA_TIPO_DOC_PBANDI;
		if (numeroDocumento != null && numeroDocumento.indexOf("ALG") > -1) {
			tipoDocumento = Constants.CONTABILIA_TIPO_DOC_ALG;
		}
		
		// Chiamo clientRicercaService.ricercaDocumentoSpesa().
		logger.info("Preparo la request per clientRicercaService.ricercaDocumentoSpesa() di Contabilia.");
		RicercaDocumentoSpesa request = new RicercaDocumentoSpesa();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setTipoDocumento(tipoDocumento);
		request.setNumeroPagina(1);
		request.setNumeroElementiPerPagina(5);
		request.setAnnoBilancio(anno);
		request.setAnnoDocumento(anno);
		request.setNumeroDocumento(numeroDocumento);		
		logger.info("\n\nREQUEST DI ricercaDocumentoSpesa() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge il documento di spesa da Contabilia.
		RicercaDocumentoSpesaResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaDocumentoSpesa() di Contabilia.");
			response = clientRicercaService.ricercaDocumentoSpesa(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaDocumentoSpesa():\n"+e.getMessage());
			return null;
		}			
		logger.info("\n\nRESPONSE DI ricercaDocumentoSpesa() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		// Gestisce il fallimento della ricerca.
		if (!Constants.CONTABILIA_ESITO_SUCCESSO.equalsIgnoreCase(response.getEsito().getValue())) {
			it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.Errore[] errori = response.getErrori();
			if (errori != null && errori.length > 0) {
				logger.error("Ricerca su Contabilia fallita: "+errori[0].getCodice()+" - "+errori[0].getDescrizione());
			}
		}
		
		return response;
	}
	
	private RicercaEstesaLiquidazioniResponse eseguiRicercaEstesaLiquidazioni(Integer anno, Integer numeroProvvedimento) {
		
		// Chiamo clientRicercaService.ricercaEstesaLiquidazioni().
		logger.info("Preparo la request per clientRicercaService.ricercaEstesaLiquidazioni() di Contabilia.");
		RicercaEstesaLiquidazioni request = new RicercaEstesaLiquidazioni();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setCodiceTipoProvvedimento(Constants.CONTABILIA_CODICE_TIPO_PROVVEDIMENTO_ALG);
		request.setAnnoBilancio(anno);
		request.setAnnoProvvedimento(anno);
		request.setNumeroProvvedimento(numeroProvvedimento);
		logger.info("\n\nREQUEST DI ricercaEstesaLiquidazioni() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge le liquidazioni da Contabilia.
		RicercaEstesaLiquidazioniResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaEstesaLiquidazioni() di Contabilia.");
			response = clientRicercaService.ricercaEstesaLiquidazioni(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaEstesaLiquidazioni():\n"+e.getMessage());
			return null;
		}			
		logger.info("\n\nRESPONSE DI ricercaEstesaLiquidazioni() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		return response;
	}

	private RicercaImpegnoResponse eseguiRicercaImpegno(Integer annoBilancio, Integer annoImpegno, Integer numeroImpegno) {
		
		// Chiamo clientRicercaService.ricercaImpegno().
		logger.info("Preparo la request per clientRicercaService.ricercaImpegno() di Contabilia.");
		RicercaImpegno request = new RicercaImpegno();
		request.setNumeroElementiPerPagina(1);
		request.setNumeroPagina(1);
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setAnnoBilancio(annoBilancio);
		request.setAnnoImpegno(annoImpegno);
		request.setNumeroImpegno(numeroImpegno);
		logger.info("\n\nREQUEST DI ricercaImpegno() di Contabilia:\n"+	objectToXML(request)+"\n\n");
		
		// Legge l'impegno da Contabilia.
		RicercaImpegnoResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaImpegno() di Contabilia.");
			response = clientRicercaService.ricercaImpegno(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaImpegno():\n"+e.getMessage());
			e.printStackTrace();
			return null;
		}
		logger.info("RESPONSE DI ricercaImpegno() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		return response;
	}
	
	private RicercaSinteticaSoggettiResponse eseguiRicercaSinteticaSoggetti(String codiceSoggetto) {
		
		// Chiamo clientRicercaService.ricercaSinteticaSoggetti().
		logger.info("Preparo la request per clientRicercaService.ricercaSinteticaSoggetti() di Contabilia.");
		RicercaSinteticaSoggetti request = new RicercaSinteticaSoggetti();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setCodice(codiceSoggetto);
		logger.info("\n\nREQUEST DI ricercaSinteticaSoggetti() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge l'impegno da Contabilia.
		RicercaSinteticaSoggettiResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaSinteticaSoggetti() di Contabilia.");
			response = clientRicercaService.ricercaSinteticaSoggetti(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaSinteticaSoggetti():\n"+e.getMessage());
			return null;
		}

		logger.info("\n\nRESPONSE DI ricercaSinteticaSoggetti() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		return response;
	}
	
	private RicercaCapitoloUscitaGestioneResponse eseguiRicercaCapitoloUscitaGestione(Integer annoBilancio, Integer numeroCapitolo, Integer numeroArticolo) {
		
		// Chiamo clientRicercaService.ricercaCapitoloUscitaGestione().
		logger.info("Preparo la request per clientRicercaService.ricercaCapitoloUscitaGestione() di Contabilia.");
		RicercaCapitoloUscitaGestione request = new RicercaCapitoloUscitaGestione();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setNumeroElementiPerPagina(1);
		request.setNumeroPagina(1);
		request.setAnnoBilancio(annoBilancio);
		request.setNumeroCapitolo(numeroCapitolo);
		request.setNumeroArticolo(numeroArticolo);
		logger.info("\n\nREQUEST DI ricercaCapitoloUscitaGestione() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge l'impegno da Contabilia.
		RicercaCapitoloUscitaGestioneResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaCapitoloUscitaGestione() di Contabilia.");
			response = clientRicercaService.ricercaCapitoloUscitaGestione(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaCapitoloUscitaGestione():\n"+e.getMessage());
			return null;
		}
		logger.info("\n\nRESPONSE DI ricercaCapitoloUscitaGestione() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		return response;
	}

	private RicercaEstesaOrdinativiSpesaResponse eseguiRicercaEstesaOrdinativiSpesa(Integer annoBilancio, Integer annoProvvedimento, Integer numeroProvvedimento) {
		
		// Chiamo clientRicercaService.ricercaEstesaOrdinativiSpesa().
		logger.info("Preparo la request per clientRicercaService.ricercaEstesaOrdinativiSpesa() di Contabilia.");
		RicercaEstesaOrdinativiSpesa request = new RicercaEstesaOrdinativiSpesa();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
		request.setCodiceTipoProvvedimento(Constants.CONTABILIA_CODICE_TIPO_PROVVEDIMENTO_ALG);
		request.setAnnoBilancio(annoBilancio);
		request.setAnnoProvvedimento(annoProvvedimento);
		request.setNumeroProvvedimento(numeroProvvedimento);
		logger.info("\n\nREQUEST DI ricercaEstesaOrdinativiSpesa() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge l'ordinativo da Contabilia.
		RicercaEstesaOrdinativiSpesaResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaEstesaOrdinativiSpesa() di Contabilia.");
			response = clientRicercaService.ricercaEstesaOrdinativiSpesa(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaEstesaOrdinativiSpesa():\n"+e.getMessage());
			return null;
		}			
		logger.info("\n\nRESPONSE DI ricercaEstesaOrdinativiSpesa() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		return response;
	}
	 
	//  - Se si cerca per cf, si ottengono pi� record soggetti relativi alla stessa persona 
	//    ma ognuno con tipologie di pagamento e stati (valido\bloccato\...) diversi.
	//  - Se si cerca per codice si ottiene 1 solo soggetto con la sua modalit� di pagamento e il suo stato. 
	private RicercaDettaglioSoggettiResponse eseguiRicercaDettaglioSoggetto(String cfBeneficiario, String codiceBeneficiario) {
		
		// Chiamo clientRicercaService.ricercaDettaglioSoggetto().
		logger.info("Preparo la request per clientRicercaService.ricercaDettaglioSoggetto() di Contabilia.");
		RicercaDettaglioSoggetti request = new RicercaDettaglioSoggetti();
		request.setCodiceEnte(Constants.CONTABILIA_CODICE_ENTE);
		request.setCodiceFruitore(Constants.CONTABILIA_CODICE_FRUITORE);
//		if (!StringUtils.isEmpty(cfBeneficiario))
		if (cfBeneficiario != null && cfBeneficiario.length() > 0)
			request.setCodiceFiscale(cfBeneficiario);
//		if (!StringUtils.isEmpty(codiceBeneficiario))
		if (codiceBeneficiario != null && codiceBeneficiario.length() > 0)
			request.setCodice(codiceBeneficiario);
		logger.info("\n\nREQUEST DI ricercaDettaglioSoggetto() di Contabilia:\n"+objectToXML(request)+"\n\n");
		
		// Legge il beneficiario da Contabilia.
		RicercaDettaglioSoggettiResponse response = null;
		try {
			logger.info("Chiamo clientRicercaService.ricercaDettaglioSoggetto() di Contabilia.");
			response = clientRicercaService.ricercaDettaglioSoggetto(request);
		} catch (Exception e) {
			logger.error("Eccezione innalzata da clientRicercaService.ricercaDettaglioSoggetto():\n"+e.getMessage());
			return null;
		}			
		logger.info("\n\nRESPONSE DI ricercaDettaglioSoggetto() di Contabilia:\nESITO = "+response.getEsito().getValue()+"\n"+objectToXML(response)+"\n\n");
		
		return response;
	}
	
	// ****************************************************************
	//     RICHIAMO NUOVI SERVIZI CONTABILIA VIA WEB SERVICES - FINE
	// ****************************************************************
	
	
}

