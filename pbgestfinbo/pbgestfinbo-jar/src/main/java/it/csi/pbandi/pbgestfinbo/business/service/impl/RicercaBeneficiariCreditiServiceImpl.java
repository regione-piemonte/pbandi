/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.RicercaBeneficiariCreditiService;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaBeneficiariCreditiDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;


@Service
public class RicercaBeneficiariCreditiServiceImpl implements RicercaBeneficiariCreditiService{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected RicercaBeneficiariCreditiDAO beneficiarioDao;
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;

	
		// RICERCA BENEFICIARI //
	
	@Override
	public Response getElencoBeneficiari(String codiceFiscale, String nag, String partitaIva, String denominazione, String descBando, String codiceProgetto, HttpServletRequest req)
			throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getElencoBeneficiari(codiceFiscale, nag, partitaIva, denominazione, descBando, codiceProgetto, req)).build();
	}

	
	
	@Override
	public Response getSuggestion(String value, String id, HttpServletRequest req)
			throws Exception {
				
		return Response.ok().entity(beneficiarioDao.getSuggestion(value, id)).build();
	}
	
	
	@Override
	public Response getRevocaAmministrativa(Long idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, String listRevoche, HttpServletRequest req) throws Exception {
		
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(beneficiarioDao.getRevocaAmministrativa(idProgetto, idModalitaAgevolazione, idModalitaAgevolazioneRif, listRevoche, userInfoSec.getIdUtente())).build();
	}
	
	
	@Override
	public Response getCreditoResiduoEAgevolazione(int idProgetto, int idBando, int idModalitaAgevolazioneOrig, int idModalitaAgevolazioneRif, HttpServletRequest req) throws Exception {
		
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(beneficiarioDao.getCreditoResiduoEAgevolazione(idProgetto, idBando, idModalitaAgevolazioneOrig, idModalitaAgevolazioneRif, userInfoSec.getIdUtente())).build();
	}
	
	
	
		// MODIFICA BENEFICIARIO //
	
	@Override
	public Response getSchedaCliente(Long idProgetto, Long idModalitaAgevolazione, HttpServletRequest req)
			throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getSchedaCliente(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	@Override
	public Response getListBanche(String value, HttpServletRequest req)
			throws Exception {
				
		return Response.ok().entity(beneficiarioDao.getListBanche(value)).build();
	}
	
	
	@Override
	public Response setSchedaCliente(SaveSchedaClienteAllVO schedaCliente, Long idModalitaAgevolazione, Boolean flagStatoAzienda, Long idProgetto, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setSchedaCliente]";
			
			LOG.info(prf + " BEGIN");
			LOG.debug(prf + " BEGIN");


			//this.beneficiarioDao.setSchedaCliente(schedaCliente);
			
			boolean success = false;
			try {
				if(schedaCliente != null) {
					
					switch (schedaCliente.getWtfImSaving()) {
					
					case "1":
						LOG.info("SAVE STATO AZIENDA");
						LOG.info("flagStatoAzienda: " + flagStatoAzienda);
						
						this.beneficiarioDao.setStatoAzienda(schedaCliente, idModalitaAgevolazione, flagStatoAzienda, idProgetto); // STATO AZIENDA
						break;

					case "2":
						LOG.info("SAVE STATO CREDITO");
						this.beneficiarioDao.setStatoCredito(schedaCliente, idModalitaAgevolazione, idProgetto); // STATO CREDITO
						break;
					
					case "3":
						LOG.info("SAVE RATING");
						this.beneficiarioDao.setRating(schedaCliente, idModalitaAgevolazione); // RATING
						break;
						
					case "4":
						LOG.info("SAVE BANCA");
						this.beneficiarioDao.setBanca(schedaCliente, idModalitaAgevolazione); // BANCA
						break;
					
					default:
						LOG.error("SAVE NOTHING, ERROR");
						success = false;
						break;
					}
	
					success = true;
				} else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			LOG.info(prf + " END");

			return Response.ok().entity(success).build();
	}
	
	
		// ISTRUTTORE AREA CREDITI //
	
	/*@Override
	public Response getBoxList(Long idModalitaAgevolazione, Long idArea, HttpServletRequest req) throws Exception, InvalidParameterException {
				
		return Response.ok().entity(beneficiarioDao.getBoxList(idModalitaAgevolazione, idArea)).build();
	}*/
	
	
	@Override
	public Response getLiberazioneGarante(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req)
			throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getLiberazioneGarante(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setLiberazioneGarante(LiberazioneGaranteVO liberazioneGarante, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setLiberazioneGarante]";

		LOG.info(prf + " BEGIN");

		boolean success = false;
		
		try {
			if(liberazioneGarante != null) {
				this.beneficiarioDao.setLiberazioneGarante(liberazioneGarante, idModalitaAgevolazione);
				success = true;
			}else {
				success = false;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return Response.ok().entity(success).build();
	}
	
	
	
	
	@Override
	public Response getEscussioneConfidi(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getEscussioneConfidi(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setEscussioneConfidi(EscussioneConfidiVO escussioneConfidi, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setEscussioneConfidi]";
			
			LOG.info(prf + " BEGIN");

			boolean success = false;
			try {
				if(escussioneConfidi != null) {
					this.beneficiarioDao.setEscussioneConfidi(escussioneConfidi, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}
	
	
	
	
	@Override
	public Response getPianoRientro(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getPianoRientro(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setPianoRientro(PianoRientroVO pianoRientro, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setPianoRientro]";
			
			LOG.info(prf + " BEGIN");
			
			boolean success = false;
			try {
				if(pianoRientro != null) {
					this.beneficiarioDao.setPianoRientro(pianoRientro, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}
	
	
	@Override
	public Response getLiberazioneBanca(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getLiberazioneBanca(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setLiberazioneBanca(LiberazioneBancaVO liberazioneBanca, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setLiberazioneBanca]";
			
			LOG.info(prf + " BEGIN");
			
			boolean success = false;
			try {
				if(liberazioneBanca != null) {
					this.beneficiarioDao.setLiberazioneBanca(liberazioneBanca, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}

	
	
	/*@Override
	public Response getIscrizioneRuolo(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getIscrizioneRuolo(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setIscrizioneRuolo(IscrizioneRuoloVO iscrizioneRuolo, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setIscrizioneRuolo]";			
		LOG.info(prf + " BEGIN");

			boolean success = false;
			try {
				if(iscrizioneRuolo != null) {
					this.beneficiarioDao.setIscrizioneRuolo(iscrizioneRuolo, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}*/
	
	
	@Override
	public Response getCessioneQuota(String idProgetto, Long idModalitaAgevolazione, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(beneficiarioDao.getCessioneQuota(idProgetto, idModalitaAgevolazione)).build();
	}
	
	
	
	@Override
	public Response setCessioneQuota(CessioneQuotaVO cessioneQuota, Long idModalitaAgevolazione, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
		String prf = "[RicercaBeneficiariCreditiServiceImpl::setCessioneQuota]";
			
			LOG.info(prf + " BEGIN");

			boolean success = false;
			try {
				if(cessioneQuota != null) {
					this.beneficiarioDao.setCessioneQuota(cessioneQuota, idModalitaAgevolazione);
					success = true;
				}else {
					success = false;
				}

			} catch (Exception e) {
				throw e;
			} finally {
				LOG.info(prf + " END");
			}

			return Response.ok().entity(success).build();
	}
		
	
		// AMMINISTRATIVO CONTABILE //
	
	@Override
	public Response getEstrattoConto(Long idUtente, Long idProgetto, String codiceVisualizzato, Long ndg, Long idAgevolazione, Long idAgevolazionerif, HttpServletRequest req) throws Exception {
		
		return Response.ok().entity(amministrativoContabileServiceImpl.callToEstrattoConto(idProgetto.intValue(), idAgevolazione.intValue(), idAgevolazionerif.intValue(), idUtente)).build();
		
	}
	
	@Override
	public Response getPianoAmmortamento(Long idUtente, Long idProgetto, String codiceVisualizzato, Long ndg, Long idAgevolazione, Long idAgevolazionerif, HttpServletRequest req) throws Exception {
				
		return Response.ok().entity(amministrativoContabileServiceImpl.callToPianoAmmortamento(idProgetto.intValue(), idAgevolazione.intValue(), idAgevolazionerif.intValue(), idUtente)).build();

	}
	
	@Override
	public Response getDebitoResiduo(int idProgetto, int idBando, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, HttpServletRequest req) throws Exception {
		
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(amministrativoContabileServiceImpl.callToDebitoResiduo(idProgetto, idBando, idModalitaAgevolazione, idModalitaAgevolazioneRif, userInfoSec.getIdUtente())).build();							
	}
	
	/*@Override
	public Response getRecuperoRevoca(int idProgetto, int idBando, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, int idRevoca, HttpServletRequest req) throws Exception {
		
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		return Response.ok().entity(amministrativoContabileServiceImpl.callToRecuperiRevoche(idProgetto, idBando, idModalitaAgevolazione, idModalitaAgevolazioneRif, userInfoSec.getIdUtente(), idRevoca)).build();							
	}*/
	
}
