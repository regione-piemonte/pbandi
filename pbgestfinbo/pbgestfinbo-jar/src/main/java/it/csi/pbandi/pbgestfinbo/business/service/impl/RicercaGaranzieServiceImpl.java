/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbgestfinbo.business.service.RicercaGaranzieService;
import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaGaranzieDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.IterAutorizzativiDAOImpl;
import it.csi.pbandi.pbgestfinbo.integration.vo.RichiestaIntegrazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAzioniRecuperoBancaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDatiAnagraficiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaDettagliGaranzieVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaRevocaBancariaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSaldoStralcioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaSezioneDettagliGaranziaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStatoCreditoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaStoricoEscussioneVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

@Service
public class RicercaGaranzieServiceImpl implements RicercaGaranzieService {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected RicercaGaranzieDAO ricercaGaranzieDao;
	
	@Autowired
	protected IterAutorizzativiDAO iterDAO;
	
	
	// Ricerca //
	
	@Override
	public Response ricercaGaranzie(String descrizioneBando, String codiceProgetto, String codiceFiscale, String nag, String partitaIva, String denominazioneCognomeNome, String statoEscussione, String denominazioneBanca, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(ricercaGaranzieDao.ricercaGaranzie(descrizioneBando, codiceProgetto, codiceFiscale, nag, partitaIva, denominazioneCognomeNome, statoEscussione, denominazioneBanca, req)).build();
	}

	@Override
	public Response getSuggestions(String value, String id, HttpServletRequest req) throws Exception {
		return Response.ok().entity(ricercaGaranzieDao.getSuggestions(value, id)).build();
	}
	
	@Override
	public Response initListaStatiEscussione(HttpServletRequest req) throws Exception {
		return Response.ok(ricercaGaranzieDao.initListaStatiEscussione()).build();
	}
	
	@Override
	public Response getBancaSuggestion(String value, HttpServletRequest req) throws Exception {
		return Response.ok().entity(ricercaGaranzieDao.getBancaSuggestion(value)).build();
	}
	
	
	
	@Override
	public Response getListaTipoEscussione(HttpServletRequest req) throws Exception {
		// TODO Auto-generated method stub
		return Response.ok(ricercaGaranzieDao.getListaTipoEscussione()).build();
	}
	
	@Override
	public Response getListaStatiCredito(HttpServletRequest req) throws Exception {
		// TODO Auto-generated method stub
		return Response.ok(ricercaGaranzieDao.getListaStatiCredito()).build();
	}

	@Override
	public Response initDialogEscussione(int idStatoEscussione, HttpServletRequest req) throws Exception {
		return Response.ok(ricercaGaranzieDao.initDialogEscussione(idStatoEscussione)).build();
	}
	
	/* Non usato da nesuna parte
	@Override
	public Response getVisualizzaDettaglioGaranzia(String idProgetto, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaDettaglioGaranzia]";
		LOG.info(prf + " BEGIN");
		VisualizzaDettagliGaranzieVO vdg = new VisualizzaDettagliGaranzieVO();
		List<Integer> ras = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			vdg.setTipoAgevolazione(ricercaGaranzieDao.getTipoAgevolazione(idProgetto));
			vdg.setImportoAmmesso(ricercaGaranzieDao.getImportoAmmesso(idProgetto));
			vdg.setTotaleFondo(ricercaGaranzieDao.getTotaleFondo(idProgetto));
			vdg.setTotaleBanca(ricercaGaranzieDao.getTotaleBanca(idProgetto));
			vdg.setDtConcessione(ricercaGaranzieDao.getDtConcessione(idProgetto));
			vdg.setDtErogazioneFinanziamento(ricercaGaranzieDao.getDtErogazione(idProgetto));
			vdg.setImportoEscusso(ricercaGaranzieDao.getImportoEscusso(idProgetto));
			vdg.setStatoCredito(ricercaGaranzieDao.getStatoDelCredito(idProgetto));
			ras=ricercaGaranzieDao.getRevocaAzioniSaldo(idProgetto);
			
			if(ras.get(0)>0) {
				vdg.setRevocaBancaria("Si");
			}else {
				vdg.setRevocaBancaria("No");
			}
			
			if(ras.get(1)>0) {
				vdg.setAzioniRecuperoBanca("Si");
			}else {
				vdg.setAzioniRecuperoBanca("No");
			}
			
			if(ras.get(2)>0) {
				vdg.setSaldoEStralcio("Si");
			}else {
				vdg.setSaldoEStralcio("No");
			}
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaDettagliGaranzieVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaDettagliGaranzieVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vdg).build();
	}*/

	@Override
	public Response getVisualizzaRevocaBancaria(String idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaRevocaBancaria]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaRevocaBancariaVO> vrb = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			vrb= ricercaGaranzieDao.getRevoca(idProgetto);
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaRevocaBancariaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaRevocaBancariaVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vrb).build();
	}

	@Override
	public Response getVisualizzaAzioniRecuperoBanca(String idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaAzioniRecuperoBanca]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaAzioniRecuperoBancaVO> varb = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			varb= ricercaGaranzieDao.getRecupero(idProgetto);
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaAzioniRecuperoBancaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaAzioniRecuperoBancaVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(varb).build();
	}

	@Override
	public Response getVisualizzaSaldoStralcio(String idProgetto, int idAttivitaEsito, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaSaldoStralcio]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaSaldoStralcioVO> vss = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			vss= ricercaGaranzieDao.getSaldoStralcio(idProgetto, idAttivitaEsito);
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaSaldoStralcioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaSaldoStralcioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vss).build();
	}

	@Override
	public Response getVisualizzaDatiAnagrafici(String idProgetto, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaDatiAnagrafici]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaDatiAnagraficiVO> vda = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			vda= ricercaGaranzieDao.getDatiAnagrafici(idProgetto);
			//vda.addAll(ricercaGaranzieDao.getDatiAnagraficiPersona(idProgetto));
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaDatiAnagraficiVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaDatiAnagraficiVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vda).build();
	}

	@Override
	public Response getVisualizzaSezioneDettaglioGaranzia(String idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaSezioneDettaglioGaranzia]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaSezioneDettagliGaranziaVO> vsdg = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			vsdg= ricercaGaranzieDao.getSezioneDettaglioGaranzia(idProgetto);
			//impDebitoResiduo è gestito da Amministrativo Contabile 
			vsdg.addAll(ricercaGaranzieDao.getStatoCredito(idProgetto));
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaSezioneDettagliGaranziaVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaSezioneDettagliGaranziaVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vsdg).build();
	}

	@Override
	public Response getVisualizzaStatoCreditoStorico(String idProgetto, int idUtente, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaStatoCreditoStorico]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaStatoCreditoVO> vsc = new ArrayList<>();
		
		try {
			LOG.info(prf + " idProgetto="+idProgetto);
			
			
			vsc= ricercaGaranzieDao.getStatoCreditoStorico(idProgetto, idUtente);
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaStatoCreditoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaStatoCreditoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vsc).build();
	}

	@Override
	public Response getVisualizzaStoricoEscussione(String idProgetto, HttpServletRequest req)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::getVisualizzaStoricoEscussione]";
		LOG.info(prf + " BEGIN");
		List<VisualizzaStoricoEscussioneVO> vse = new ArrayList<>();
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		
		try {
			LOG.info(prf + " idProgetto="+idProgetto + " idUtente="+userInfo.getIdUtente());
			vse= ricercaGaranzieDao.getStoricoEscussione(idProgetto);
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaStoricoEscussioneVO", e);
			throw new ErroreGestitoException("DaoException while trying to read VisualizzaStoricoEscussioneVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(vse).build();
	}

	@Override
	public Response updateStatoCredito(HttpServletRequest req, VisualizzaStatoCreditoVO vsc) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[AttivitaServiceImpl::updateStatoCredito]";
		//LOG.info(prf + " BEGIN");

		return Response.ok().entity(ricercaGaranzieDao.insertStatoCredito(vsc.getStatoCredito(), vsc.getDtModifica(), vsc.getIdSoggetto(), vsc.getIdProgetto(), vsc.getIdUtente())).build();
	}

	
	@Override
	public Response insertEscussione(HttpServletRequest req, VisualizzaStoricoEscussioneVO vse)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::insertEscussione]";
		LOG.info(prf + " BEGIN");
		LOG.info("Dati: " + vse.getDtRichEscussione() + ", " + vse.getDescTipoEscussione()+ ", " + vse.getDtInizioValidita()+ ", " + vse.getImpRichiesto());

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);		
		
		LOG.info("idUtente: " + userInfo.getIdUtente());
		
		EsitoDTO esito = new EsitoDTO();
		EsitoDTO resInsert = new EsitoDTO();

		try {

			if(vse.getDtRichEscussione()!=null && vse.getDescTipoEscussione()!=null && vse.getDtInizioValidita()!=null && vse.getImpRichiesto()!=null) {

				if (vse.getDescTipoEscussione().equalsIgnoreCase("totale") || vse.getDescTipoEscussione().equalsIgnoreCase("acconto")) {

					LOG.info("il sistema verifica che per il progetto selezionato non ci sia già un’altra escussione");

					if(ricercaGaranzieDao.escussioneTotAccIsPresent(vse.getIdProgetto(), vse.getDescTipoEscussione())==0){

						resInsert = ricercaGaranzieDao.insertEscussione(vse.getDtRichEscussione(), vse.getDescTipoEscussione(), vse.getImpRichiesto(),
								vse.getNumProtocolloRich(),	vse.getNote(), vse.getDtInizioValidita(), vse.getIdProgetto(), vse.getNumProtocolloNotif(), userInfo.getIdUtente());

						if(resInsert.getEsito()) {
							esito.setEsito(true);
							esito.setMessaggio("Escussione inserita correttamente.");
							esito.setId(resInsert.getId());
						} else {
							esito.setEsito(false);
							esito.setMessaggio(resInsert.getMessaggio());
						}

					} else {
						//vse2.setMessaggio("Esiste già una escussione per il progetto");
						esito.setEsito(false);
						esito.setMessaggio("Esiste già un escussione '" + vse.getDescTipoEscussione() + "' per l'idProgetto " + vse.getIdProgetto());
					}

				} else {
					// Disabilitato per demo
					//LOG.info("il sistema verifica che per il progetto selezionato ci sia una escussione in Acconto ");
					//if(ricercaGaranzieDao.escussioneSaldoIsPresent(vse.getIdProgetto())>0){

						resInsert = ricercaGaranzieDao.insertEscussione(vse.getDtRichEscussione(), vse.getDescTipoEscussione(), vse.getImpRichiesto(),
								vse.getNumProtocolloRich(),	vse.getNote(), vse.getDtInizioValidita(), vse.getIdProgetto(), vse.getNumProtocolloNotif(), userInfo.getIdUtente());

						if(resInsert.getEsito()) {
							esito.setEsito(true);
							esito.setMessaggio("Escussione inserita correttamente.");
							esito.setId(resInsert.getId());
						} else {
							esito.setEsito(false);
							esito.setMessaggio(resInsert.getMessaggio());
						}
					// Disabilitato per demo	
					/*} else {
						//vse2.setMessaggio("Manca escussione in acconto");
						esito.setEsito(false);
						esito.setMessaggio("È necessario inserire prima un escussione in acconto.");
					}*/

				}

			} else {
				//vse2.setMessaggio("Dati obbligatori mancanti");
				esito.setEsito(false);
				esito.setMessaggio("Dati obbligatori mancanti");
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaStoricoEscussioneVO", e);
			esito.setEsito(false);
			esito.setMessaggio("Exception in AttivitaServiceImpl::updateStatoCredito : " + e.toString());
			//throw new ErroreGestitoException("DaoException while trying to read VisualizzaStoricoEscussioneVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok().entity(esito).build();
	}


@Override
	public Response updateEscussione(HttpServletRequest req, VisualizzaStoricoEscussioneVO vse)
			throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaGaranzieServiceImpl::updateEscussione]";
		LOG.info(prf + " BEGIN");
		LOG.info("Dati: " + vse.getDtRichEscussione() + ", " + vse.getDescTipoEscussione()+ ", " + vse.getDtInizioValidita()+ ", " + vse.getImpRichiesto());
		
		//VisualizzaStoricoEscussioneVO vse2 = new VisualizzaStoricoEscussioneVO();
		EsitoDTO esito = new EsitoDTO();
		String errMsg = "Si è verificato un errore durante la modifica dell'escussione.";
		String succMsg = "Escussione modificata correttamente.";
		
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info("idUtente: " + userInfo.getIdUtente());
		
		try {
	
			if(vse.getDtRichEscussione()!=null && vse.getDescTipoEscussione()!=null && vse.getDescStatoEscussione()!=null && vse.getDtInizioValidita()!=null && vse.getImpRichiesto()!=null) {
		
				if (vse.getDescTipoEscussione().equalsIgnoreCase("totale") || vse.getDescTipoEscussione().equalsIgnoreCase("acconto") ) {
			
					//LOG.info("il sistema verifica che per il progetto selezionato non ci sia già un’altra escussione");

					//if(ricercaGaranzieDao.escussioneTotAccIsPresent(vse.getIdProgetto(), vse.getDescTipoEscussione())==0){
						
						//LOG.info("il sistema non ha trovato escussioni");
						
						// Incorporato in insertModificaEscussione
						/*EsitoDTO esito1 = ricercaGaranzieDao.UpdateEscussione(vse.getIdProgetto(), userInfo.getIdUtente(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdEscussione());
						if (esito1.getEsito()) {
							LOG.info("UpdateEscussione terminato con esito True");
						} else {
							esito.setEsito(false);
							esito.setMessaggio(errMsg);
							esito.setException(esito1.getException());
							return Response.ok().entity(esito).build();
						}*/
						
						EsitoDTO esito2 = ricercaGaranzieDao.insertModificaEscussione(vse.getDtRichEscussione(), vse.getDescTipoEscussione(), vse.getDescStatoEscussione(), vse.getImpRichiesto(), vse.getImpApprovato(), vse.getNumProtocolloRich(), vse.getNumProtocolloNotif(), vse.getCausaleBonifico(), vse.getIbanBonifico(), vse.getIdBanca(), vse.getDescBanca(), vse.getIdSoggProgBancaBen(), vse.getProgrSoggettoProgetto(), vse.getNote(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdProgetto(), userInfo.getIdUtente(), vse.getIdEscussione());
						if (esito2.getEsito()) {
							LOG.info("insertModificaEscussione terminato con esito True, id nuova escussione: " + esito2.getId());
							esito.setId(esito2.getId());
						} else {
							esito.setEsito(false);
							esito.setMessaggio(errMsg);
							esito.setException(esito2.getException());
							return Response.ok().entity(esito).build();
						}
						
						ricercaGaranzieDao.aggiornaAllegati(vse.getListaAllegatiPresenti(), esito2.getId());

					/*} else {
						//vse2.setMessaggio("Esiste già una escussione per il progetto");
						esito.setEsito(false);
						esito.setMessaggio("Esiste già un escussione per il progetto corrente.");
						return Response.ok().entity(esito).build();
					}*/
					
				} else {
					// Commentato momentaneamente per demo
					//LOG.info("Il sistema verifica che per il progetto selezionato ci sia una escussione in Acconto");
					//if(ricercaGaranzieDao.escussioneSaldoIsPresent(vse.getIdProgetto())>0){
						
						//LOG.info("È stata trovata un escussione in Acconto");
						
						// Incorporato in insertModificaEscussione
						/*EsitoDTO esito1 = ricercaGaranzieDao.UpdateEscussione(vse.getIdProgetto(), userInfo.getIdUtente(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdEscussione());
						if (esito1.getEsito()) {
							LOG.info("UpdateEscussione terminato con esito True");
						} else {
							esito.setEsito(false);
							esito.setMessaggio(errMsg);
							esito.setException(esito1.getException());
							return Response.ok().entity(esito).build();
						}*/
						
						EsitoDTO esito2 = ricercaGaranzieDao.insertModificaEscussione(vse.getDtRichEscussione(), vse.getDescTipoEscussione(), vse.getDescStatoEscussione(), vse.getImpRichiesto(), vse.getImpApprovato(), vse.getNumProtocolloRich(),	vse.getNumProtocolloNotif(), vse.getCausaleBonifico(), vse.getIbanBonifico(),  vse.getIdBanca(), vse.getDescBanca(), vse.getIdSoggProgBancaBen(), vse.getProgrSoggettoProgetto(), vse.getNote(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdProgetto(), userInfo.getIdUtente(), vse.getIdEscussione());
						if (esito2.getEsito()) {
							LOG.info("insertModificaEscussione terminato con esito True, id nuova escussione: " + esito2.getId());
							esito.setId(esito2.getId());
						} else {
							esito.setEsito(false);
							esito.setMessaggio(errMsg);
							esito.setException(esito2.getException());
							return Response.ok().entity(esito).build();
						}
						
						ricercaGaranzieDao.aggiornaAllegati(vse.getListaAllegatiPresenti(), esito2.getId());

					/*} else {
						//vse2.setMessaggio("Manca escussione in acconto");
						esito.setEsito(false);
						esito.setMessaggio("È necessario inserire prima un escussione in Acconto.");
						return Response.ok().entity(esito).build();
					}*/
						
				}
		
		} else {
			//vse2.setMessaggio("Dati obbligatori mancanti");
			esito.setEsito(false);
			esito.setMessaggio(errMsg);
			esito.setException("Dati obbligatori mancanti");
		}
		
		} catch (RecordNotFoundException e) {
			//throw e;
			esito.setEsito(false);
			esito.setMessaggio(errMsg);
			esito.setException("RecordNotFoundException: " + e.toString());
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read VisualizzaStoricoEscussioneVO", e);
			//throw new ErroreGestitoException("DaoException while trying to read VisualizzaStoricoEscussioneVO", e);
			esito.setEsito(false);
			esito.setMessaggio(errMsg);
			esito.setException("Exception: " + e.toString());
		} finally {
			LOG.info(prf + " END");
		}
		
		esito.setEsito(true);
		esito.setMessaggio(succMsg);
		return Response.ok().entity(esito).build();
	}


@Override
public Response updateStatoEscussione(HttpServletRequest req, VisualizzaStoricoEscussioneVO vse)
		throws ErroreGestitoException, RecordNotFoundException {
	String prf = "[RicercaGaranzieServiceImpl::updateStatoEscussione]";
	LOG.info(prf + " BEGIN");
	
	LOG.info("Dati: " + vse.getNote() + ", " + vse.getIdEscussione()+ ", " + vse.getDtInizioValidita() + ", " + vse.getIdUtente() + ", " + vse.getIdProgetto());
	//VisualizzaStoricoEscussioneVO vse2 = new VisualizzaStoricoEscussioneVO();
	
	UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	LOG.info("idUtente: " + userInfo.getIdUtente());
	
	EsitoDTO esito = new EsitoDTO();
	String succMsg = "Stato escussione modificato correttamente.";
	String failMsg = "Non è stato possibile modificare lo stato.";
	
	try {
		if(vse.getDtInizioValidita() == null) {
			esito.setEsito(false);
			esito.setMessaggio("Data stato deve essere valorizzata.");
			return Response.ok().entity(esito).build();
		}

		if(vse.getIdEscussione() == 0) {
			esito.setEsito(false);
			esito.setMessaggio(failMsg);
			esito.setException("idEscussione non valorizzata.");
			return Response.ok().entity(esito).build();
		}

		if(vse.getDescStatoEscussione() == null) {
			esito.setEsito(false);
			esito.setMessaggio("È necessario selezionare uno stato.");
			esito.setException("statoEscussione non valorizzato");
			return Response.ok().entity(esito).build();
		}
	} catch (Exception e) {
		esito.setEsito(false);
		esito.setMessaggio(failMsg);
		esito.setException("Exception durante il controllo dei dati: " + e.toString());
		return Response.ok().entity(esito).build();
	}
		
	try {

		EsitoDTO esito1 = ricercaGaranzieDao.UpdateEscussione(vse.getIdProgetto(), userInfo.getIdUtente(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdEscussione());
		if (!esito1.getEsito()) {
			esito.setEsito(false);
			esito.setMessaggio(failMsg);
			esito.setException(esito1.getException());
			return Response.ok().entity(esito).build();
		} 
		
		EsitoDTO esito2 = ricercaGaranzieDao.insertModificaStatoEscussione(vse.getNote(), vse.getDtInizioValidita(), vse.getIdEscussione(), userInfo.getIdUtente(), vse.getDtNotifica(), vse.getDescStatoEscussione());
		if (esito2.getEsito()) {
			esito.setEsito(true);
			esito.setMessaggio(succMsg);
			esito.setId(esito2.getId());
			
			ricercaGaranzieDao.aggiornaAllegati(vse.getListaAllegatiPresenti(), esito2.getId());
		} else {
			esito.setEsito(false);
			esito.setMessaggio(failMsg);
			esito.setException(esito2.getException());
		}

	} catch (Exception e) {
		LOG.error(prf + "Exception while trying to read VisualizzaStoricoEscussioneVO", e);
		//throw new ErroreGestitoException("DaoException while trying to read VisualizzaStoricoEscussioneVO", e);
		esito.setEsito(false);
		esito.setMessaggio(failMsg);
		esito.setException("Exception in RicercaGaranzieServiceImpl: " + e.toString());
	}
	
	LOG.info(prf + " END");
	esito.setEsito(true);
	esito.setMessaggio(succMsg);
	return Response.ok().entity(esito).build();
}

@Override
public Response updateStatoEscussioneRicIntegrazione(HttpServletRequest req, VisualizzaStoricoEscussioneVO vse)
		throws ErroreGestitoException, RecordNotFoundException {
	String prf = "[RicercaGaranzieServiceImpl::updateStatoEscussioneRicIntegrazione]";
	LOG.info(prf + " BEGIN");
	LOG.info("Dati: " + vse.getNote() + ", " + vse.getIdEscussione()+ ", " + vse.getDtInizioValidita() + ", " + vse.getIdUtente() + ", " + vse.getIdProgetto() + ", " + vse.getDescTipoEscussione());
	VisualizzaStoricoEscussioneVO vse2 = new VisualizzaStoricoEscussioneVO();
	
	UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	LOG.info("idUtente: " + userInfo.getIdUtente());
	
	try {

		if(vse.getDtInizioValidita()!=null ) {
	
			ricercaGaranzieDao.UpdateEscussione(vse.getIdProgetto(), userInfo.getIdUtente(), vse.getDtInizioValidita(), vse.getDtNotifica(), vse.getIdEscussione());
			ricercaGaranzieDao.insertModificaStatoEscussioneRicIntegrazione(vse.getNote(), vse.getDtInizioValidita(), vse.getIdEscussione(), vse.getIdUtente(), vse.getDescStatoEscussione(), vse.getDtNotifica());
				
	
	}else vse2.setMessaggio("Dati obbligatori mancanti");
	
	}catch (RecordNotFoundException e) {
		throw e;
	} catch (Exception e) {
		LOG.error(prf + "Exception while trying to read VisualizzaStoricoEscussioneVO", e);
		throw new ErroreGestitoException("DaoException while trying to read VisualizzaStoricoEscussioneVO", e);
	} finally {
		LOG.info(prf + " END");
	}
	return Response.ok().entity(vse2).build();
}

@Override
public Response salvaUploadAllegato(ArrayList<MultipartFormDataInput> multipartFormData, HttpServletRequest req) throws InvalidParameterException, Exception {

	ArrayList<Boolean> results = new ArrayList<>();
	
	for(MultipartFormDataInput element: multipartFormData) {
		results.add(ricercaGaranzieDao.salvaUploadAllegato(element));
	}
	
	return Response.ok(results).build();
}

@Override
public Response salvaAllegatiGenerici(Long idEscussione, MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) {
	String className = Thread.currentThread().getStackTrace()[1].getClassName();
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	String prf = "[" + className + "::" + methodName + "]";
	LOG.info(prf + "BEGIN");
	Object result;

	try {
		/*
    	//CONTROLLI
        if(numeroGestioneRevoca == null) {
            throw new ErroreGestitoException("Variabile numeroGestioneRevoca non valorizzata");
        }*/

		/*boolean letteraAccompagnatoriaPresente = "S".equals(multipartFormDataInput.getFormDataPart("letteraAccompagnatoriaPresente", String.class, null));*/
		Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
		/*if(!letteraAccompagnatoriaPresente){
            //lettereAccompagnatoria
            List<InputPart> letteraAccompagnatoria = null;
            try {
                letteraAccompagnatoria = map.get("letteraAccompagnatoria");
            }catch (Exception ignored){}
            if(letteraAccompagnatoria == null || letteraAccompagnatoria.isEmpty()){
                throw new ErroreGestitoException("Lettera Accompagnatoria non valorizzata correttamente");
            }
            provvedimentoRevocaDAO.aggiungiAllegato(numeroGestioneRevoca, true, 0, IOUtils.toByteArray(letteraAccompagnatoria.get(0).getBody(InputStream.class, null)), getFileName(letteraAccompagnatoria.get(0).getHeaders()),req);
        }*/

		//altriAllegati
		List<InputPart> altriAllegati = null;
		try{
			altriAllegati = map.get("file");
		}catch (Exception ignored){}
		if(altriAllegati != null) {
			for (InputPart altroAllegato : altriAllegati) {
				ricercaGaranzieDao.newAggiungiAllegato(idEscussione, false, 0, IOUtils.toByteArray(altroAllegato.getBody(InputStream.class, null)), getFileName(altroAllegato.getHeaders()), req);
			}
		}

		//emettiProvvedimentoRevoca
		//CHIAMATA A DAO --->>
		//provvedimentoRevocaDAO.emettiProvvedimentoRevoca(numeroGestioneRevoca, giorniScadenza, req);
		result = new ResponseCodeMessage("OK", "Allegati salvati correttamente.");
	}
	catch(Exception e){
		result = new ResponseCodeMessage("KO", e.getMessage());
		LOG.info(prf + e.getMessage());
		//throw e;
	}

	LOG.info(prf + "END");
	return Response.ok().entity(result).build();
}
//UTILS
public static String getFileName(MultivaluedMap<String, String> header) {

	String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
	for (String value : contentDisposition) {
		if (value.trim().startsWith("filename")) {
			String[] name = value.split("=");
			if (name.length > 1) {
				return name[1].trim().replaceAll("\"", "");
			}
		}
	}
	return null;
}


@Override
public Response getAllegati(Long idEscussione, HttpServletRequest req)
		throws ErroreGestitoException, RecordNotFoundException {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.getAllegati(idEscussione)).build();
}

@Override
public Response deleteAllegato(HttpServletRequest req, VisualizzaAllegatiVO va)
		throws ErroreGestitoException, RecordNotFoundException {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.deleteAllegato(va.getIdDocIndex())).build();
}

@Override
public Response getAbilitazione(String ruolo, HttpServletRequest req)
		throws ErroreGestitoException, RecordNotFoundException {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.getAbilitazione(ruolo)).build();
}

@Override
@Transactional(rollbackFor=Exception.class)
public Response insertRichiestaIntegrazELettera(Long idProgetto, Long idEscussione, Long giorniScadenza, MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
	String prf = "[RicercaGaranzieServiceImpl::insertRichiestaIntegrazELettera]";
	LOG.info(prf + " BEGIN");
	
	EsitoDTO esito = new EsitoDTO();
	String succMsg = "Richiesta d'integrazione inserita correttamente.";
	String failMsg = "Non è stato possibile inserire la richiesta d'integrazione.";
	
	UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	LOG.info(prf + "idUtente: " + userInfo.getIdUtente());

	try {
		EsitoDTO esito1 = ricercaGaranzieDao.insertRichiestaIntegrazELettera(true, idProgetto, userInfo.getIdUtente(), idEscussione, giorniScadenza, multipartFormDataInput, (long) 37);
		if (esito1.getEsito()) {
			esito.setEsito(true);
			esito.setMessaggio(succMsg);
		} else {
			esito.setEsito(false);
			esito.setMessaggio(failMsg);
			esito.setException(esito1.getException());
		}
		
		iterDAO.avviaIterAutorizzativo(Long.valueOf(14), Long.valueOf(605), idEscussione, idProgetto, userInfo.getIdUtente());
	} catch (Exception e) {
		esito.setEsito(false);
		esito.setMessaggio(failMsg);
		esito.setException(e.toString());
	}

		
	LOG.info(prf + " END");
	return Response.ok(esito).build();
}

@Override
public Response salvaUploadRichiestaIntegraz(MultipartFormDataInput multipartFormData, HttpServletRequest req)
		throws InvalidParameterException, Exception {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.salvaUploadRichiestaIntegraz(multipartFormData)).build();
}

@Override
public Response getRichIntegr(String idEscussione, HttpServletRequest req)
		throws ErroreGestitoException, RecordNotFoundException {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.getRichIntegr(idEscussione)).build();
}

@Override
public Response salvaUploadLettera(MultipartFormDataInput multipartFormData, HttpServletRequest req)
		throws InvalidParameterException, Exception {
	// TODO Auto-generated method stub
	return Response.ok(ricercaGaranzieDao.salvaUploadLettera(multipartFormData)).build();
}

@Override
public Response insertDistinta(HttpServletRequest req, RichiestaIntegrazioneVO ri)
		throws ErroreGestitoException, RecordNotFoundException {
	String prf = "[RicercaGaranzieServiceImpl::insertDistinta]";
	LOG.info(prf + " BEGIN");
	LOG.info("Dati: " + ri.getIdProgetto() + ", " + ri.getIdTarget()+ ", " + ri.getUtente());
	
	UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	LOG.info("idUtente: " + userInfo.getIdUtente());
	
	try {

		
		//ricercaGaranzieDao.insertDistinta(userInfo.getIdUtente(), Long.valueOf(ri.getIdProgetto()));
		//ricercaGaranzieDao.insertDettaglio((long) ri.getIdTarget(), userInfo.getIdUtente());
		//ricercaGaranzieDao.updateIdDistinta((long) ri.getIdTarget());
	
	}catch (RecordNotFoundException e) {
		throw e;
	} catch (Exception e) {
		LOG.error(prf + "Exception while trying to read RichiestaIntegrazioneVO", e);
		throw new ErroreGestitoException("DaoException while trying to read RichiestaIntegrazioneVO", e);
	} finally {
		LOG.info(prf + " END");
	}
	return Response.ok().build();
}

@Override
public Response getGaranzia(BigDecimal idEscussione, BigDecimal idProgetto, HttpServletRequest req) throws Exception {
	
	return Response.ok(ricercaGaranzieDao.getGaranzia(idEscussione, idProgetto)).build();
}

@Override
public Response getStatoCredito(String idProgetto, HttpServletRequest req)
		throws ErroreGestitoException, RecordNotFoundException {
	
	return Response.ok(ricercaGaranzieDao.getStatoCreditoNew(idProgetto)).build();
}

@Override
@Transactional
public Response salvaEsito(Long idProgetto, Long idEscussione, Long idStatoEscussione, int idTipoEscussione, MultipartFormDataInput multipartFormData, HttpServletRequest req) throws InvalidParameterException, Exception {
	String prf = "[RicercaGaranzieServiceImpl::salvaEsito]";
	LOG.info(prf + " BEGIN");
	
	EsitoDTO esito = new EsitoDTO();
	String succMsg = "Esito salvato correttamente.";
	String failMsg = "Non è stato possibile salvare l'esito correttamente.";
	
	UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	LOG.info("idUtente: " + userInfo.getIdUtente());
		
	EsitoDTO esito1 = null;
	if(idStatoEscussione == 6) { // Negativo
		esito1 = ricercaGaranzieDao.insertRichiestaIntegrazELettera(true, idProgetto, userInfo.getIdUtente(), idEscussione, -1L, multipartFormData, (long) 38);
	} else { // Positivo
		esito1 = ricercaGaranzieDao.insertRichiestaIntegrazELettera(false, idProgetto,  userInfo.getIdUtente(), idEscussione, -1L, multipartFormData, (long) 38);
	}
	
	if (esito1.getEsito()) {
		esito.setEsito(true);
		esito.setMessaggio(succMsg);
	} else {
		esito.setEsito(false);
		esito.setMessaggio(failMsg);
		esito.setException(esito1.getException());
		return Response.ok(esito).build();
	}
	
	/*if (idStatoEscussione == 5) {
		try {
			//ricercaGaranzieDao.insertDistinta(userInfo.getIdUtente(), Long.valueOf(idProgetto));
			//ricercaGaranzieDao.insertDettaglio(idEscussione, userInfo.getIdUtente());
			//ricercaGaranzieDao.updateIdDistinta(idEscussione);
		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessaggio(failMsg);
			esito.setException("Exception durante l'inserimento della distinta: " + e.toString());
			return Response.ok(esito).build();
		}
	}*/
	
	/* Non è più necessario
	if (idStatoEscussione == 6) {
		iterDAO.avviaIterAutorizzativo(Long.valueOf(15), Long.valueOf(605), idEscussione, idProgetto, userInfo.getIdUtente());
	} else if (idStatoEscussione == 5) {
		iterDAO.avviaIterAutorizzativo(Long.valueOf(16), Long.valueOf(605), idEscussione, idProgetto, userInfo.getIdUtente());
	}*/
	
	if (idStatoEscussione == 6) { // Negativo
		iterDAO.avviaIterAutorizzativo(Long.valueOf(15), Long.valueOf(605), idEscussione, idProgetto, userInfo.getIdUtente());
	}
		
	EsitoDTO esito2 = ricercaGaranzieDao.setFlagEsito(idEscussione);
	if (esito2.getEsito()) {
		esito.setEsito(true);
		esito.setMessaggio(succMsg);
	} else {
		/*esito.setEsito(false);
		esito.setMessaggio(failMsg);
		esito.setException(esito2.getException());*/
		//return Response.ok(esito).build();
		LOG.error(prf + " Errore nel salvataggio dell'esito, controllare il metodo.");
		LOG.error(prf + " " + esito2.getException());
	}
	
	if (idStatoEscussione == 5) { // Positivo
		Boolean esito3 = ricercaGaranzieDao.sendEsitoToErogazioni(idProgetto, idEscussione, idTipoEscussione, userInfo.getIdUtente());
		LOG.info(prf + " sendEsitoToErogazioni() terminato con " + esito3);
	}
	
	
	
	LOG.info(prf + " END");
	esito.setEsito(true);
	esito.setMessaggio(succMsg);
	return Response.ok(esito).build();
}

}

