/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.dto.profilazione.Beneficiario;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.vo.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweberog.business.SecurityHelper;
import it.csi.pbandi.pbweberog.business.service.InizializzazioneService;
import it.csi.pbandi.pbweberog.dto.DatiCumuloDeMinimis;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.ImportoDescrizione;
import it.csi.pbandi.pbweberog.dto.profilazione.BeneficiarioDTO;
import it.csi.pbandi.pbweberog.integration.dao.ErogazioneDAO;
import it.csi.pbandi.pbweberog.integration.dao.InizializzazioneDAO;
import it.csi.pbandi.pbweberog.util.Constants;

@Service
public class InizializzazioneServiceImpl implements InizializzazioneService {

	@Autowired
	ProfilazioneDAO profilazioneDao;

	@Autowired
	GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;

	@Autowired
	private ProfilazioneBusinessImpl profilazioneSrv;

	@Autowired
	private SecurityHelper springSecurityHelper;

	@Autowired
	private ErogazioneDAO erogazioneDAO;

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected InizializzazioneDAO inizializzazioneDAO;

	@Override
	public Response inizializzaHome(Long idPrj, Long idSg, Long idSgBen, Long idU, String role, String taskIdentity,
			String taskName, String wkfAction, HttpServletRequest req) throws UtenteException {
		String prf = "[InizializzazioneServiceImpl::inizializzaHome]";
		LOG.info(prf + " BEGIN");

		UserInfoSec user = inizializzazioneDAO.completaDatiUtente(idPrj, idSg, idSgBen, idU, role, taskIdentity,
				taskName, wkfAction, req);
		LOG.debug("[InizializzazioneServiceImpl::inizializzaHome] END");
		return Response.ok().entity(user).build();
	}

	@Override
	public Response inizializzaHome2(Long idSg, Long idSgBen, Long idU, String role, HttpServletRequest req)
			throws UtenteException {
		String prf = "[InizializzazioneServiceImpl::inizializzaHome2]";
		LOG.info(prf + " BEGIN");
		HttpSession session = req.getSession();
		UserInfoSec userInfo2 = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo=" + userInfo2);

		if (userInfo2 == null) {
			LOG.warn(prf + "userInfo in sessione null");
			throw new UtenteException("Utente in sessione non valido");
		}

		// TODO : verifico la valorizzazione dei parametri idSg, idSgBen, idU, role,

		LOG.info(prf + " idSg=" + idSg + ", idSgBen=" + idSgBen + ", idU=" + idU + " role=" + role);

		try {
			UserInfoDTO user = inizializza(idSg, idSgBen, idU, role, userInfo2);
			userInfo2 = inizializzazioneDAO.aggiornaConUserInfoDTO(user, userInfo2);
			userInfo2.setCodiceRuolo(role);
			List<TipoAnagraficaVO> listaTA = profilazioneDao.findTipiAnagrafica(BigDecimal.valueOf(idSg));
			for (TipoAnagraficaVO tipoAnagraficaVO : listaTA) {
				if (StringUtils.equals(role, tipoAnagraficaVO.getDescBreveTipoAnagrafica())) {
					userInfo2.setRuolo(tipoAnagraficaVO.getDescTipoAnagrafica());
					break;
				}
			}

			if (idSgBen.compareTo(0L) == 0) {
				// beneficiario non selezionato
				LOG.debug("Beneficiario non selezionato");
			} else {
				Beneficiario[] y = user.getBeneficiari();
				for (Beneficiario beneficiario : user.getBeneficiari()) {
					if (Long.compare(idSgBen, beneficiario.getId()) == 0) {
						BeneficiarioSec beneficiarioSelezionato = new BeneficiarioSec();
						beneficiarioSelezionato.setCodiceFiscale(beneficiario.getCodiceFiscale());
						beneficiarioSelezionato.setDenominazione(beneficiario.getDescrizione());
						beneficiarioSelezionato.setIdBeneficiario(idSgBen);
						userInfo2.setBeneficiarioSelezionato(beneficiarioSelezionato);
						break;
					}
				}
			}

			LOG.info(prf + " userInfo2=" + userInfo2);

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			e.printStackTrace();
			throw new ErroreGestitoException("Errore gestito");
		}

		LOG.info(prf + " END");
		return Response.ok().entity(userInfo2).build();
	}

	private UserInfoDTO inizializza(Long idSg, Long idSgBen, Long idU, String role, UserInfoSec userInfo2)
			throws UtenteException, DaoException {
		String prf = "[InizializzazioneServiceImpl::inizializza]";
		LOG.info(prf + " BEGIN");

		UserInfoDTO userInfoDTO = profilazioneDao.getInfoUtente(userInfo2.getCodFisc(), userInfo2.getNome(),
				userInfo2.getCognome());
		LOG.info(prf + " userInfoDTO=" + userInfoDTO);

		// verifico che utente sia presente sul DB
		if (userInfoDTO == null) {
			LOG.warn(prf + "Utente[" + idU + " - " + userInfo2.getCodFisc() + "] non censito in PBANDI");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro idU corrisponda all'idUtente in sessione
		if (Long.compare(idU, userInfoDTO.getIdUtente().longValue()) == 0) {
			LOG.debug(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") corretto");
		} else {
			LOG.warn(prf + " idUtente in sessione (" + userInfoDTO.getIdUtente() + ") diverso da quello in request ("
					+ idU + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}
		// verifico che parametro idS corrisponda all'idSoggetto dell'utente in sessione
		if (Long.compare(idSg, userInfoDTO.getIdSoggetto().longValue()) == 0) {
			LOG.debug(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto() + ") corretto");
		} else {
			LOG.warn(prf + " idSoggetto in sessione (" + userInfoDTO.getIdSoggetto()
					+ ") diverso da quello in request (" + idSg + ")");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico che parametro role corrisponda ad almeno un ruolo dell'utente in
		// sessione
		Ruolo[] ruoli = userInfoDTO.getRuoli();
		boolean checkRole = false;
		for (Ruolo ruolo : ruoli) {
			if (StringUtils.equals(ruolo.getDescrizioneBreve(), role)) {
				checkRole = true;
			}
		}
		if (!checkRole) {
			// parametro "role" passato in request non corrisponde a nessun ruolo utente
			LOG.warn(prf + "parametro \"role\" passato in request non corrisponde a nessun ruolo utente");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico l'utente in sessione possa accedere a questa funzionalita
		if (!profilazioneDao.hasPermesso(idSg, idU, role, UseCaseConstants.UC_MENUTRS)) { // TODO ----> verificare cosa
																							// passare, da dove arriva
																							// MENUCERT ??

			LOG.warn(prf + " idSoggetto in sessione (" + idSg + ") non ha i permessi per accedere");
			UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
			throw ue;
		}

		// verifico i beneficiari
		if (idSgBen.compareTo(0L) == 0) {
			// beneficiario non selezionato
			LOG.debug("Beneficiario non selezionato");
		} else {
			BeneficiarioSec beneficiarioSec = profilazioneDao.findBeneficiarioByIdSoggettoBen(idU,
					userInfo2.getCodFisc(), role, idSgBen);

			if (beneficiarioSec != null && Long.compare(idSgBen, beneficiarioSec.getIdBeneficiario()) == 0) {
				// beneficiario valido
				LOG.debug("Beneficiario idSgBen=" + idSgBen + " trovato");

				Beneficiario b = new Beneficiario();
				b.setId(beneficiarioSec.getIdBeneficiario());
				b.setCodiceFiscale(beneficiarioSec.getCodiceFiscale());
				b.setDescrizione(beneficiarioSec.getDenominazione());

				Beneficiario[] arrBenef = new Beneficiario[1];
				arrBenef[0] = b;

				userInfoDTO.setBeneficiari(arrBenef);
			} else {
				LOG.warn(prf + " id soggetto beneficiario (" + idSgBen + ") non e' associato all'utente " + idU);
				UtenteException ue = new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
				throw ue;
			}
		}

		LOG.info(prf + " END");
		return userInfoDTO;
	}

	@Override
	public Response getCodiceProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[InizializzazioneServiceImpl::getCodiceProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null) {
				return inviaErroreBadRequest("Parametro mancato: ?[idProgetto]");
			}
			String codiceProgetto = erogazioneDAO.findCodiceProgetto(idProgetto);
			
			//TODO : PK Mi devo preoccupare solo di IdProgetto
			// come aggancio l'IdProgetto al bando e all'utente collegato ???
//			PbandiTSoggettoVO soggetto = soggettoManager.findSoggetto(identita.getCodFiscale());
//			List<TipoAnagraficaVO> tipiAnagrafica = oggettoManager.findTipiAnagrafica(soggetto.getIdSoggetto());
			
			
			Enumeration<String> params = req.getParameterNames(); 
			while(params.hasMoreElements()){
			 String paramName = params.nextElement();
			 LOG.debug(prf + "Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
			}
			
			LOG.debug(prf + " END");
			return Response.ok().entity(codiceProgetto).build();
		} catch (Exception e) {
			throw e;
		}
	}

//	@Override
//	public Response getDatiGenerali(HttpServletRequest req, Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception {
//		String prf = "[InizializzazioneServiceImpl::getDatiGenerali]";
//		LOG.debug(prf + " BEGIN");
//		
//
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			String idIride = userInfo.getIdIride();
//			
//			DatiGeneraliDTO datiGenerali = gestioneDatiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
//			
//			DatiGenerali dati = new DatiGenerali();
//			
//			Bando b = new Bando();
//			b.setIdBando(datiGenerali.getIdBandoLinea());
//			b.setDescrizione(datiGenerali.getDescrizioneBando());
//			b.setTitolo(datiGenerali.getTitoloBando());
//			dati.setBando(b);
//			LOG.info(prf +" IdBandoLinea() = "+datiGenerali.getIdBandoLinea());
//			LOG.info("GestioneDatiGeneraliBusinessImpl :: PBANDIRCE - datiSrv.getIdTipoOperazione() = "+datiGenerali.getIdTipoOperazione());
//			
//			Progetto p = new Progetto();
//			p.setAcronimo(datiGenerali.getAcronimo());
//			p.setId(idProgetto);
//			p.setCodice(datiGenerali.getCodiceProgetto());
//			p.setCup(datiGenerali.getCup());
//			p.setImportoAgevolato(datiGenerali.getImportoAgevolato());
//			p.setTitolo(datiGenerali.getTitoloProgetto());
//			p.setDtConcessione(datiGenerali.getDtConcessione());
//			dati.setProgetto(p);
//			
//		
//			it.csi.pbandi.pbweberog.dto.Beneficiario beneficiario = new it.csi.pbandi.pbweberog.dto.Beneficiario();
//			beneficiario.setIdSoggetto(userInfo.getBeneficiarioSelezionato().getIdBeneficiario()); //?
//			beneficiario.setDescrizione(datiGenerali.getDescrizioneBeneficiario());
//			beneficiario.setIdDimensioneImpresa(datiGenerali.getIdDimensioneImpresa());
//			beneficiario.setIdFormaGiuridica(datiGenerali.getIdFormaGiuridica());
//			
//			//beneficiario.setIdDimensioneImpresa(datiSrv.get)
//			// FIXME dovrebbe arrivare dal servizio e non dal dato in sessione
//			beneficiario.setCodiceFiscale(userInfo.getBeneficiarioSelezionato().getCodiceFiscale());
//			beneficiario.setSede(datiGenerali.getSedeIntervento());
//			beneficiario.setPIvaSedeIntervento(datiGenerali.getPIvaSedeIntervento());
//			beneficiario.setSedeLegale(datiGenerali.getSedeLegale());
//			beneficiario.setPIvaSedeLegale(datiGenerali.getPIvaSedeLegale());
//			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
//			
//			beneficiario.setIsCapofila(datiGenerali.getIsCapofila());
//			dati.setBeneficiario(beneficiario);
//			//dati.setDescrizioneBreve(messageManager.getMessage(MessageKey.FILO_ARIANNA, datiSrv.getTitoloProgetto(), datiSrv.getDescrizioneBando()));
//			dati.setDataPresentazione(datiGenerali.getDtPresentazioneDomanda());
//			
//			ImportoAgevolatoDTO importoAgevolatoDTO[]= datiGenerali.getImportiAgevolati();
//			List <ImportoAgevolato>list=new ArrayList<ImportoAgevolato>();
//			if(!ObjectUtil.isEmpty(importoAgevolatoDTO)){
//				for (ImportoAgevolatoDTO dtoServer : importoAgevolatoDTO) {
//					ImportoAgevolato importoAgevolato=new ImportoAgevolato(); 
//					importoAgevolato.setDescrizione(dtoServer.getDescrizione());
//					importoAgevolato.setImporto(dtoServer.getImporto());
//					importoAgevolato.setImportoAlNettoRevoche(dtoServer.getImportoAlNettoRevoche());
//					list.add(importoAgevolato);
//				}
//				dati.setImportiAgevolati(new ArrayList<ImportoAgevolato>(list));
//			}
//			Double importoCertificatoNettoUltimoPropAmm = datiGenerali.getImportoCertificatoNettoUltimaPropAppr();
//			//dati.setImportoCertificatoNettoUltimaPropAppr(Constants.VALUE_IMPORTO_ULTIMO_CERTIFICATO_NON_PRESENTE);
//			if(importoCertificatoNettoUltimoPropAmm!=null){
//				dati.setImportoCertificatoNettoUltimaPropAppr(importoCertificatoNettoUltimoPropAmm);
//			}	
//
//			if (springSecurityHelper.verifyCurrentUserForUC(null ,UseCaseConstants.UC_TRSWKS005_1)) {
//				dati.setFlagImportoCertificatoNettoVisibile("S");
//			}
//
//			Double importoValidatoNettoRevoche = datiGenerali.getImportoValidatoNettoRevoche();
//			if(importoValidatoNettoRevoche!=null){
//				dati.setImportoValidatoNettoRevoche(importoValidatoNettoRevoche);
//			}	
//			Double importoSoppressioni = datiGenerali.getImportoSoppressioni();
//			if(importoSoppressioni!=null){
//				dati.setImportoSoppressioni(importoSoppressioni);
//			}	
//			dati.setErogazioni(getImportoDescrizioni(datiGenerali.getErogazioni()));
//			dati.setPreRecuperi(getImportoDescrizioni(datiGenerali.getPreRecuperi()));
//			dati.setRecuperi(getImportoDescrizioni(datiGenerali.getRecuperi()));
//			dati.setRevoche(getImportoDescrizioni(datiGenerali.getRevoche()));
//			
//			if(datiGenerali.getImportoImpegno() != null){
//				dati.setImportoImpegno(datiGenerali.getImportoImpegno());
//				LOG.info("caricaDatiGenerali(): PBANDIRCE - datiSrv.getImportoImpegno() = "+datiGenerali.getImportoImpegno()+"; dati.getImportoImpegno() = "+dati.getImportoImpegno());
//			}
//			
//			if(datiGenerali.getImportoRendicontato() != null){
//				dati.setImportoRendicontato(datiGenerali.getImportoRendicontato());
//			}
//			
//			if(datiGenerali.getImportoQuietanzato() != null){
//				dati.setImportoQuietanzato(datiGenerali.getImportoQuietanzato());
//			}
//			
//			if(datiGenerali.getImportoCofinanziamentoPubblico() != null){
//				dati.setImportoCofinanziamentoPubblico(datiGenerali.getImportoCofinanziamentoPubblico());
//			}
//			
//			if(datiGenerali.getImportoCofinanziamentoPrivato() != null){
//				dati.setImportoCofinanziamentoPrivato(datiGenerali.getImportoCofinanziamentoPrivato());
//			}
//			
//			if(datiGenerali.getImportoAmmesso() != null){
//				dati.setImportoAmmesso(datiGenerali.getImportoAmmesso());
//			}
//			LOG.info(prf + " ImportoAmmesso() = "+datiGenerali.getImportoAmmesso()+"; dati.getImportoAmmesso() = "+dati.getImportoAmmesso());
//			
//			dati.setDatiCumuloDeMinimis(getDatiCumuloDeMinimis(userInfo));
//			
//			/*
//			 * Calcolo del residuo ammesso come la differenza
//			 * tra   l' ammesso a finaziamento ed il totale rendicontato
//			 */
//			Double totaleRendicontato = dati.getImportoRendicontato() == null ? 0D : dati.getImportoRendicontato();
//			
//			Double importoAmmesso = dati.getImportoAmmesso() == null ? 0D : dati.getImportoAmmesso();
//			
//			Double importoResiduoAmmesso = NumberUtil.subtract(importoAmmesso, totaleRendicontato);
//			
//			dati.setImportoResiduoAmmesso(NumberUtil.getStringValueItalianFormat(importoResiduoAmmesso));
//			
//			/*
//			 * FIX PBandi-2307. Verifico se il progetto e' legato al bilancio ( presenza della regola BR37
//			 */
//			if (profilazioneSrv.isRegolaApplicabileForBandoLinea(
//					idUtente, 
//					idIride, 
//					b.getIdBando(), 
//					RegoleConstants.BR37_ATTIVITA_LIQUIDAZIONE_CONTRIBUTO)) {
//				dati.setIsLegatoBilancio(Boolean.TRUE);
//			} else {
//				dati.setIsLegatoBilancio(Boolean.FALSE);
//			}
//			
//			// Economie.
//			if (datiGenerali.getEconomie() == null) {
//				dati.setEconomie(new ArrayList<EconomiaPerDatiGenerali>());
//			} else {
//				ArrayList<EconomiaPerDatiGenerali> economie = new ArrayList<EconomiaPerDatiGenerali>();
//				for(EconomiaPerDatiGeneraliDTO eDTO : datiGenerali.getEconomie()){
//					EconomiaPerDatiGenerali e = new EconomiaPerDatiGenerali(); 
//					e.setDescBreveSoggFinanziatore(eDTO.getDescBreveSoggFinanziatore());
//					e.setImpQuotaEconSoggFinanziat(eDTO.getImpQuotaEconSoggFinanziat());
//					economie.add(e);
//				}
//				dati.setEconomie(economie);
//			}
////			
////			
////			if(datiGenerali == null) {
////				LOG.warn(prf + " datiGenerali == null, inviaErroreInternalServer");
////				return inviaErroreInternalServer("Errore durante il recupero dei dati generali.");
////			}
//					
//			LOG.debug(prf + " END");
//			return Response.ok().entity(dati).build();
//		} catch (Exception e) {
//			LOG.error(prf+"Exception e "+e.getMessage());
//			//e.printStackTrace();
//			throw e;
//		}
//	}

	private ArrayList<ImportoDescrizione> getImportoDescrizioni(ImportoDescrizioneDTO[] importoDescrizioneDTO) {
		ArrayList<ImportoDescrizione> listaImportoDescrizioni = new ArrayList<ImportoDescrizione>();
		if (importoDescrizioneDTO != null) {
			for (ImportoDescrizioneDTO importoDescrizioneDto : importoDescrizioneDTO) {
				ImportoDescrizione importoDescrizione = new ImportoDescrizione();
				importoDescrizione.setDescBreve(importoDescrizioneDto.getDescBreve());
				importoDescrizione.setDescrizione(importoDescrizioneDto.getDescrizione());
				importoDescrizione.setImporto(importoDescrizioneDto.getImporto());
				listaImportoDescrizioni.add(importoDescrizione);
			}
		}
		return listaImportoDescrizioni;
	}

	private DatiCumuloDeMinimis getDatiCumuloDeMinimis(UserInfoSec userInfo) throws Exception {
		DatiCumuloDeMinimis datiCumuloDeMinimis = new DatiCumuloDeMinimis();
		return datiCumuloDeMinimis;

	}

//	@Override
//	public Response getAttivitaPregresse(HttpServletRequest req, Long idProgetto)
//			throws UtenteException, FileNotFoundException, IOException, Exception {
//		String prf = "[InizializzazioneServiceImpl::getAttivitaPregresse]";
//		LOG.debug(prf + " BEGIN");
//		
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			String idIride = userInfo.getIdIride();
//			
//			
//			AttivitaPregresseDTO[] dto = gestioneDatiGeneraliBusinessImpl.caricaAttivitaPregresse(idUtente, idIride, idProgetto);
//					
//			LOG.debug(prf + " END");
//			return Response.ok().entity(dto).build();
//		} catch (Exception e) {
//			LOG.error(prf+"Exception e "+e.getMessage());
//			throw e;
//		}
//	}
//	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// METODI DI RESPONSE HTTP
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

}
