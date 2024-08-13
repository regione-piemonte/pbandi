/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BeneficiarioProgettoAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DettaglioUtenteDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoSalvaUtente;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.UtenteRicercatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.GestioneUtenti;
import it.csi.pbandi.pbwebbo.dto.gestioneutenti.EntiDTO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.util.DateUtil;
import it.csi.pbandi.pbwebbo.util.FileUtil;
import it.csi.pbandi.pbwebbo.util.ValidatorCodiceFiscale;

@Service
public class GestioneUtentiImpl implements GestioneUtenti {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeSrv;

	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioSrv;

	@Override
	public Response findUtenti(Long idU, Long idSoggetto, String ruolo, UtenteRicercatoDTO filtro,
			HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::findUtenti]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto + ", ruolo = " + ruolo
				+ ", filtro = " + filtro.toString());

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UtenteRicercatoDTO[] utentiTrovati = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			if (filtro != null && (filtro.getCodiceFiscale() != null || filtro.getCognome() != null
					|| filtro.getNome() != null || filtro.getDataInserimentoAl() != null
					|| filtro.getDataInserimentoDal() != null || userInfo.getRuolo().equalsIgnoreCase("ADG-IST-MASTER")
					|| userInfo.getRuolo().equalsIgnoreCase("OI-IST-MASTER"))) {
				utentiTrovati = gestioneBackofficeSrv.findUtenti(idU, userInfo.getIdIride(), idSoggetto, ruolo, filtro);
				LOG.debug(prf + "trovati = " + utentiTrovati.length + " elementi");

				if (utentiTrovati == null || utentiTrovati.length <= 0) {
					resp.setCode("NOT_FOUND");
					resp.setMessage("Nessun elemento trovato con i parametri passati in input");
					return Response.ok().entity(resp).build();
				}
			} else {
				resp.setCode("ERROR");
				resp.setMessage("Nessun filtro passato in input");
				return Response.ok().entity(resp).build();
			}

		} catch (Exception e) {
			LOG.error(e);
			resp.setCode("Errore durante la gestione del filtro");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(utentiTrovati).build();

	}

	@Override
	public Response dettaglioUtente(Long idU, Long idPersonaFisica, Long idTipoAnagrafica,
			String descBreveTipoAnagrafica, Long idSoggetto, HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::dettaglioUtente]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idPersonaFisica = " + idPersonaFisica
				+ ", idTipoAnagrafica = " + idTipoAnagrafica + ", idSoggetto = " + idSoggetto);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UtenteRicercatoDTO dettaglio = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			dettaglio = gestioneBackofficeSrv.findDettaglioUtente(idU, userInfo.getIdIride(), idPersonaFisica,
					idTipoAnagrafica);
			LOG.debug(prf + "Dettaglio utente = " + dettaglio);
			String anagrafica = dettaglio.getDescBreveTipoAnagrafica();

			if (anagrafica != null && anagrafica.equals("PERSONA-FISICA")) {
				// Persona fisica
				BeneficiarioProgettoAssociatoDTO[] beneficiarioProgettoAssociatoDTO = gestioneBackofficeSrv
						.findBeneficiarioProgettoAssociati(idU, userInfo.getIdIride(), idSoggetto);

				if (beneficiarioProgettoAssociatoDTO == null || beneficiarioProgettoAssociatoDTO.length == 0) {
					resp.setCode("UTENTE_PF_NO_TABLE");
					resp.setMessage(
							"Nessun dato trovato su findBeneficiarioProgettoAssociati per idSoggetto = " + idSoggetto);
					return Response.ok().entity(resp).build();
				}

				return Response.ok().entity(beneficiarioProgettoAssociatoDTO).build();
			} else if (anagrafica == null || isRuoloADG(anagrafica) || isRuoloCreator(anagrafica)
					|| isRuoloOI(anagrafica)) {
				// Istruttore master dell'autorità di gestione
				// Operatore abilitato al caricamento scheda progetto
				IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findEntiAssociatiSoggetto(
						idU, userInfo.getIdIride(), idSoggetto,
						anagrafica == null ? descBreveTipoAnagrafica : anagrafica);

				if (idDescBreveDescEstesaDTO == null || idDescBreveDescEstesaDTO.length == 0) {
					resp.setCode("ISTR_AUTORITY_GESTIONE_NO_TABLE");
					resp.setMessage("Nessun dato trovato su findEntiAssociatiSoggetto per idSoggetto = " + idSoggetto
							+ " e anagrafica = " + anagrafica);
					return Response.ok().entity(resp).build();
				}

				return Response.ok().entity(idDescBreveDescEstesaDTO).build();
			}
//			else if (isRuoloOI(anagrafica)) {
//				// Istruttore master dell'organismo intermedio
//				
//				IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findEntiAssociatiSoggetto(idU, userInfo.getIdIride(), idSoggetto, anagrafica);
//				String entiPerOrganismoIntermedio = extractEntiPerOrganismoIntermedio( new ArrayList<IdDescBreveDescEstesaDTO> (Arrays.asList(idDescBreveDescEstesaDTO)) );
//				return Response.ok().entity(entiPerOrganismoIntermedio).build();
//			}

		} catch (Exception e) {
			LOG.error(e);
			resp.setCode("Errore durante la gestione del filtro");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(dettaglio).build();

	}

	@Override
	public Response getenti(Long idU, Long idSoggetto, Long idTipoAnagrafica, String ruolo, HttpServletRequest req)
			throws Exception {

		String prf = "[GestioneUtentiImpl::getenti]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idTipoAnagrafica = " + idTipoAnagrafica
				+ ", idSoggetto = " + idSoggetto + ", ruolo = " + ruolo);

		EntiDTO entiDTO = new EntiDTO();

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			String descBreveTipoAnagrafica = null;
			IdDescBreveDescEstesaDTO[] entiAssociati = null;
			
			//La gestione e' fatta cosi' perche' sto richiamando il metodo gia' presente in gestione backoffice
			//che e' fatto cosi', 
			//sabbe meglio direttamente andarsi a pescare i dati in base all'id che arriva da FE
			

			if (idTipoAnagrafica == 20 || idTipoAnagrafica == 2 || idTipoAnagrafica == 3 || idTipoAnagrafica == 25 ) {
				descBreveTipoAnagrafica = "ADG-IST-MASTER";
				entiAssociati = gestioneBackofficeSrv.findEntiAssociatiSoggetto(idU, userInfo.getIdIride(), idSoggetto,
						descBreveTipoAnagrafica);
			} else if (idTipoAnagrafica == 4 || idTipoAnagrafica == 5) {
				descBreveTipoAnagrafica = "OI-IST-MASTER";
				entiAssociati = gestioneBackofficeSrv.findEntiAssociatiSoggetto(idU, userInfo.getIdIride(), idSoggetto,
						descBreveTipoAnagrafica);
			} else {
				resp.setCode("KO");
				resp.setMessage("Non ci sono enti di competenza associati per i parametri in ingresso");
				return Response.ok().entity(resp).build();
			}
			LOG.debug(prf + "Trovati" + entiAssociati.length + " enti associati");

			Decodifica[] entiAssociabili = gestioneDatiDiDominioSrv.findEntiDiCompetenzaFiltrato(idU,
					userInfo.getIdIride(), descBreveTipoAnagrafica, idSoggetto, ruolo);
			LOG.debug(prf + "Trovati" + entiAssociabili.length + " enti associabili");

			List<IdDescBreveDescEstesaDTO> entiAssociatiList = new ArrayList<IdDescBreveDescEstesaDTO>();
			entiAssociatiList = Arrays.asList(entiAssociati);

			List<Decodifica> entiAssociabiliList = new ArrayList<Decodifica>();
			entiAssociabiliList = Arrays.asList(entiAssociabili);

			List<Decodifica> entiAssociabiliFiltrati = new ArrayList<Decodifica>();

			for (Decodifica enteAssociabile : entiAssociabiliList) {
				if (!entiAssociatiList.stream().filter(o -> o.getId().equals(enteAssociabile.getId())).findFirst()
						.isPresent()) {
					entiAssociabiliFiltrati.add(enteAssociabile);
				}
			}

			entiDTO.setEntiAssociabiliFiltrati(entiAssociabiliFiltrati);
			entiDTO.setEntiAssociatiList(entiAssociatiList);

		} catch (Exception e) {
			LOG.error(e);
			resp.setCode("Errore");
			resp.setMessage("Descrizone = " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(entiDTO).build();

	}

	@Override
	public Response salvaModifica(Long idU, Long idSoggetto, Long idProgetto, String codiceFiscaleBeneficiario,
			String codiceVisualizzatoProgetto, Boolean isRappresentanteLegale, HttpServletRequest req)
			throws Exception {

		String prf = "[GestioneUtentiImpl::salvaModifica]";
		LOG.debug(prf + " BEGIN");

		LOG.info(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto + ", idProgetto = "
				+ idProgetto + ", codiceFiscaleBeneficiario = " + codiceFiscaleBeneficiario
				+ ", codiceVisualizzatoProgetto = " + codiceVisualizzatoProgetto + ", isRappresentanteLegale = "
				+ isRappresentanteLegale);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);

		try {
			esito = gestioneBackofficeSrv.cambiaAssociazioneBeneficiarioProgetto(idU, userInfo.getIdIride(), idSoggetto,
					idProgetto, codiceFiscaleBeneficiario, codiceVisualizzatoProgetto, isRappresentanteLegale,
					userInfo.getCodiceRuolo(), userInfo.getIdSoggetto());

			LOG.info(prf + "Esito = " + esito.getEsito() + ", descrizione" + esito.getMessage());

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setMessage("Errore: " + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	public Response aggiungiAssociazioneBeneficiarioProgetto(Long idU, Long idSoggetto,
			String codiceFiscaleBeneficiario, String codiceVisualizzatoProgetto, Boolean isRappresentanteLegale,
			HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::salvaNuovo]";
		LOG.debug(prf + " BEGIN");

		LOG.info(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto
				+ ", codiceFiscaleBeneficiario = " + codiceFiscaleBeneficiario + ", codiceVisualizzatoProgetto = "
				+ codiceVisualizzatoProgetto + ", isRappresentanteLegale = " + isRappresentanteLegale);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);

		try {
			esito = gestioneBackofficeSrv.inserisciAssociazioneBeneficiarioProgetto(idU, userInfo.getIdIride(),
					idSoggetto, codiceFiscaleBeneficiario, codiceVisualizzatoProgetto, isRappresentanteLegale,
					userInfo.getCodiceRuolo(), userInfo.getIdSoggetto());

			LOG.debug(prf + "Esito = " + esito.getEsito() + ", descrizione" + esito.getMessage());

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setMessage("Errore: " + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	public Response eliminaAssociazioneBeneficiarioProgetto(Long idU, Long idSoggetto,
			String codiceProgettoVisualizzato, HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::eliminaAssociazioneBeneficiarioProgetto]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto
				+ ", codiceProgettoVisualizzato = " + codiceProgettoVisualizzato);

		GestioneBackOfficeEsitoGenerico esito = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			BeneficiarioProgettoAssociatoDTO[] beneficiarioProgettoAssociatoDTOArray = gestioneBackofficeSrv
					.findBeneficiarioProgettoAssociati(idU, userInfo.getIdIride(), idSoggetto);
			LOG.debug(prf + "Trovati  = " + beneficiarioProgettoAssociatoDTOArray.length
					+ " progetti per il soggetto = " + idSoggetto);

			BeneficiarioProgettoAssociatoDTO beneficiarioProgettoAssociatoDTO = findBeneficiarioProgetto(
					beneficiarioProgettoAssociatoDTOArray, codiceProgettoVisualizzato);
			LOG.debug(
					prf + "Trovato beneficiarioProgettoAssociatoDTO = " + beneficiarioProgettoAssociatoDTO.toString());

			esito = gestioneBackofficeSrv.eliminaAssociazioneBeneficiarioProgetto(idU, userInfo.getIdIride(),
					idSoggetto, beneficiarioProgettoAssociatoDTO.getIdProgetto());
			LOG.debug(prf + "Esito = " + esito.getEsito() + ", descrizione" + esito.getMessage());

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setMessage("Errore: " + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	@Transactional
	public Response eliminaUtente(Long idU, Long idSoggetto, Long idTipoAnagraficaSoggetto, Long idPersonaFisica,
			HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::eliminaUtente]";
		LOG.debug(prf + " BEGIN");
		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto
				+ ", idTipoAnagraficaSoggetto = " + idTipoAnagraficaSoggetto + ", idPersonaFisica = "
				+ idPersonaFisica);

		GestioneBackOfficeEsitoGenerico esito = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			UtenteRicercatoDTO dettaglio = gestioneBackofficeSrv.findDettaglioUtente(idU, userInfo.getIdIride(),
					idPersonaFisica, idTipoAnagraficaSoggetto, false);
			LOG.debug(prf + "Dettaglio utente = " + dettaglio);
			esito = gestioneBackofficeSrv.eliminaUtente(idU, userInfo.getIdIride(), idSoggetto,
					idTipoAnagraficaSoggetto, dettaglio.getDescBreveTipoAnagrafica());
			

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setMessage("Errore: " + e);
		}
		

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	public Response findTipoAnagrafica(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::findTipoAnagrafica]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findTipiAnagrafica(idU, userInfo.getIdIride());

			LOG.debug(prf + "Trovati " + idDescBreveDescEstesaDTO.length + " tipoAnagrafica");

		} catch (Exception e) {
			LOG.error(e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("Errore in findTipiAnagrafica");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}

	@Override
	public Response salvaNuovoUtente(Long idU, String nome, String cognome, String codiceFiscale, Long idTipoAnagrafica,
			String ruolo, String email, Long[] idEntiAssociati, HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::salvaNuovoUtente]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "Parametri in input -> idU = " + idU + ", nome = " + nome + ", cognome = " + cognome
				+ ", codiceFiscale = " + codiceFiscale + ", idTipoAnagrafica = " + idTipoAnagrafica + ", ruolo = "
				+ ruolo + ", email = " + email + ", numeroEntiAssociate = " + idEntiAssociati.length);

		UtenteRicercatoDTO[] utentiTrovati = null;
		UserInfoSec userInfo = null;
		EsitoSalvaUtente esito = new EsitoSalvaUtente();
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);

		try {

			if (nome == null || cognome == null || codiceFiscale == null || idTipoAnagrafica == null) {
				esito.setEsito(false);
				esito.setMessage("I campi Nome, Cognome, codice fiscale, tipo anagrafica sono campi obbligatori");
				return Response.ok().entity(esito).build();
			}

			UtenteRicercatoDTO dettaglio = new UtenteRicercatoDTO();
			dettaglio.setCodiceFiscale(codiceFiscale);
			if (ValidatorCodiceFiscale.isCodiceFiscalePersonaFisicaValido(codiceFiscale)) {
				// verifico la presenza di un CF uguale
				utentiTrovati = gestioneBackofficeSrv.findUtenti(idU, userInfo.getIdIride(), userInfo.getIdSoggetto(),
						ruolo, dettaglio);
				LOG.info(prf + "Trovati = " + utentiTrovati.length + " utenti");
				dettaglio.setNome(nome);
				dettaglio.setCognome(cognome);

				ArrayList<UtenteRicercatoDTO> UtenteRicercatoList = new ArrayList<UtenteRicercatoDTO>();
				for (UtenteRicercatoDTO utente : utentiTrovati) {
					UtenteRicercatoList.add(utente);
				}

				if (isNomeCognomeDifferentePerStessoCodFisc(UtenteRicercatoList, dettaglio)) {
					esito.setEsito(false);
					esito.setMessage("Codice fiscale gia' esittente per un altro nome/cognome");
				} else {

					// salvataggio
					DettaglioUtenteDTO dettaglioUtenteDTO = new DettaglioUtenteDTO();
					dettaglioUtenteDTO.setNome(nome);
					dettaglioUtenteDTO.setCognome(cognome);
					dettaglioUtenteDTO.setCodiceFiscale(codiceFiscale);
					dettaglioUtenteDTO.setIdTipoAnagrafica(idTipoAnagrafica);
					dettaglioUtenteDTO.setIdEntiAssociati(idEntiAssociati);
					dettaglioUtenteDTO.setEmail(email);

					LOG.info(prf + "Chiamo salvaUtente()");
					esito = gestioneBackofficeSrv.salvaUtente(idU, userInfo.getIdIride(), dettaglioUtenteDTO);
					if (esito.getEsito().equals(Boolean.FALSE)) {
						return Response.ok().entity(esito).build();
					} else {
						// Jira PBANDI-2778
						LOG.info(prf + "Chiamo inserisciTabellaUtente()");
						dettaglioUtenteDTO.setIdSoggetto(esito.getIdSoggetto());
						gestioneBackofficeSrv.inserisciTabellaUtente(idU, userInfo.getIdIride(), dettaglioUtenteDTO);
					}
				}				
			} else {
				esito.setEsito(false);
				esito.setMessage("Codice fiscale formalmente non corretto");
			}

		} catch (Exception e) {
			LOG.error(e);
			esito.setEsito(false);
			esito.setMessage("Errore: " + e);
		}

		LOG.info(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	public Response salvaModificaUtente(Long idU, String nome, String cognome, String codiceFiscale,
			Long idTipoAnagrafica, String email, Long[] idEntiAssociati, HttpServletRequest req) throws Exception {

		String prf = "[GestioneUtentiImpl::salvaModificaUtente]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", nome = " + nome + ", cognome = " + cognome
				+ ", codiceFiscale = " + codiceFiscale + ", idTipoAnagrafica = " + idTipoAnagrafica + ", email = " + email
				+ ", idEntiAssociati.length = " + idEntiAssociati.length);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		DettaglioUtenteDTO dettaglio = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			dettaglio = new DettaglioUtenteDTO();
			dettaglio.setNome(nome);
			dettaglio.setCognome(cognome);
			dettaglio.setCodiceFiscale(codiceFiscale);
			dettaglio.setIdTipoAnagrafica(idTipoAnagrafica);
			dettaglio.setIdEntiAssociati(idEntiAssociati);
			dettaglio.setEmail(email);

			EsitoSalvaUtente esito = gestioneBackofficeSrv.salvaUtente(idU, userInfo.getIdIride(), dettaglio);
			LOG.debug(prf + "Esito modifica utente = " + esito.getEsito() + ". Descrizione = " + esito.getMessage());

			if (esito.getEsito()) {
				resp.setCode("OK");
				resp.setMessage("Modifica dati utente effettuato con successo");
			} else {
				resp.setCode("KO");
				resp.setMessage("Impossibile modificare i dati utente.");
			}

		} catch (Exception e) {
			LOG.error(e);
			resp.setCode("Errore");
			resp.setMessage("Descrizone = " + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@SuppressWarnings("resource")
	@Override
	public Response caricaFile(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws Exception, InvalidParameterException {

		String prf = "[GestioneUtentiImpl::caricaFile]";
		LOG.debug(prf + " BEGIN");

		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}

		UserInfoSec user = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		LOG.debug(prf + "userInfo = " + user);

		// Legge il file firmato dal multipart.
		Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
		List<InputPart> listInputPart = map.get("file");

		if (listInputPart == null) {
			LOG.info("listInputPart NULLO");
		} else {
			LOG.info("listInputPart SIZE = " + listInputPart.size());
		}
		for (InputPart i : listInputPart) {
			MultivaluedMap<String, String> m = i.getHeaders();
			Set<String> s = m.keySet();
			for (String x : s) {
				LOG.info("SET = " + x);
			}
		}

		FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
		if (file == null) {
			throw new InvalidParameterException("File non valorizzato");
		}

		LOG.info(prf + "input nomeFile = " + file.getNomeFile());
		LOG.info(prf + "input bytes.length = " + file.getBytes().length);

		try {

			if (!file.getNomeFile().toLowerCase().endsWith(".csv")) {
				resp.setCode("ERROR: E.M9");
				resp.setMessage("Formato file non valido");

				return Response.ok().entity(resp).build();
			}

			Properties props = new java.util.Properties();
			//////////////////////////////////////////////
			LOG.info(prf + "inizio caricamento file di properties... ");
			// InputStream fis =
			// getClass().getClassLoader().getResourceAsStream("pbandiwebbo.properties");
			// InputStream fis =
			// getClass().getClassLoader().getResourceAsStream("conf/config.properties");
			InputStream fis = getClass().getClassLoader().getResourceAsStream("conf/areaShare.properties");
			if (fis == null) {
				LOG.info(prf + "il file di properties, non è stato trovato");
			}
			props.load(fis);
			LOG.info(prf + "finito caricamento file di properties.");
			String uploadUtenti_Path = props.getProperty("uploadUtenti.path");

			String fNameOut = (DateUtil.getData() + "_" + DateUtil.getOra() + "_" + file.getNomeFile()).toLowerCase()
					.replaceAll(" ", "_").replaceAll("/", "-").replaceAll("\\\\", "-");
			String fPath = uploadUtenti_Path + "/" + fNameOut;
			LOG.debug("[BackEndFacade::upload] File path : " + fPath);
			File fOut = null;

			try {
				FileReader fr = null;
				try {
					// da rivedere
					File f = File.createTempFile("temp", "csv");
					FileOutputStream fos = new FileOutputStream(f);
					fos.write(file.getBytes());
					fr = new FileReader(f);
					fos.close();
					// fr = new FileReader(new String(file.getBytes())); <-- originale che non
					// funziona
					// ------------
				} catch (Exception e) {
					LOG.debug("CZ - Excption: " + e.toString());
					resp.setCode("ERROR: E.E258");
					resp.setMessage("Caricamento file non valido");

					return Response.ok().entity(resp).build();
				}
				fOut = new File(fPath);

				FileWriter fw = new FileWriter(fOut);
				int lineNum = 0;
				BufferedReader in = new BufferedReader(fr);
				String inputline;
				java.util.List<String> listErrs = new java.util.ArrayList<String>();
				java.util.List<String> listLineNumb = new java.util.ArrayList<String>();
				while ((inputline = in.readLine()) != null) {
					lineNum++;
					if (lineNum > 200) {

						resp.setCode("ERROR: E.E250");
						resp.setMessage("Superata la soglia delle 200 righe per file");

						fw.close();
						fr.close();
						fOut.delete();

						return Response.ok().entity(resp).build();

					}

					checkCSVLine(lineNum, inputline, listErrs, listLineNumb);
					fw.write(inputline + "\n");
				}

				if (listErrs != null && listErrs.size() > 0) {
					try {
						fw.close();
						fr.close();
						fOut.delete();
					} catch (Exception e1) {
					}
//					String[] errs = listErrs.toArray(new String[listErrs
//							.size()]);
//					String[] lineNumb = listLineNumb
//							.toArray(new String[listLineNumb.size()]);
					resp.setCode("KO");
					resp.setMessage("Contenuto del file erroto");

					return Response.ok().entity(resp).build();
				}

				gestioneBackofficeSrv.insertFlussoUpload(user.getIdUtente(), user.getIdIride(), fNameOut);
				fw.close();
				fr.close();
			} catch (Exception e) {
				try {
					fOut.delete();
				} catch (Exception e1) {
				}
				throw e;
			}

			resp.setCode("OK");
			resp.setMessage("File " + fNameOut + " caricato correttamente");
			/* PROTECTED REGION END */

		} catch (Exception e) {
			LOG.error("[BackEndFacade::upload] Errore occorso nell'esecuzione del metodo:" + e, e);
			resp.setCode("Error");
			resp.setMessage("Errore occorso nell'esecuzione del metodo:" + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

//	private String extractEntiPerOrganismoIntermedio(
//			ArrayList<IdDescBreveDescEstesaDTO> entiAssociati) {
//		if (entiAssociati == null || entiAssociati.size() == 0) {
//			return null;
//		} else {
//			StringBuilder strB = new StringBuilder();
//			for (IdDescBreveDescEstesaDTO ente : entiAssociati) {
//				strB.append(ente.getDescBreve());
//				strB.append(", ");
//			}
//			return strB.substring(0, strB.length() - 2);
//		}
//	}

	private Boolean isRuoloOI(String codiceRuolo) {
		return Constants.RUOLO_OI_IST_MASTER.equals(codiceRuolo) || Constants.RUOLO_OI_ISTRUTTORE.equals(codiceRuolo);
	}

	private Boolean isRuoloADG(String codiceRuolo) {
		return Constants.RUOLO_ADG_IST_MASTER.equals(codiceRuolo) || Constants.RUOLO_ADG_ISTRUTTORE.equals(codiceRuolo);
	}

	private Boolean isRuoloCreator(String codiceRuolo) {
		return Constants.RUOLO_CREATOR.equals(codiceRuolo);
	}

	private boolean isNomeCognomeDifferentePerStessoCodFisc(ArrayList<UtenteRicercatoDTO> utenti,
			UtenteRicercatoDTO filtro) {
		if ((utenti != null) && (utenti.size() > 0)) {
			for (UtenteRicercatoDTO utente : utenti) {
				if ((filtro.getCodiceFiscale().equals(utente.getCodiceFiscale()))
						&& ((!filtro.getNome().equals(utente.getNome()))
								|| (!filtro.getCognome().equals(utente.getCognome())))) {
					return true;
				}
			}
		}
		return false;
	}

	private BeneficiarioProgettoAssociatoDTO findBeneficiarioProgetto(
			BeneficiarioProgettoAssociatoDTO[] beneficiariProgetti, String beneficiarioProgettoSelezionato) {
		for (BeneficiarioProgettoAssociatoDTO beneficiarioProgetto : beneficiariProgetti) {
			if (beneficiarioProgetto.getCodiceProgettoVisualizzato().equals(beneficiarioProgettoSelezionato)) {
				return beneficiarioProgetto;
			}
		}
		return null;
	}

	private boolean checkCSVLine(int numLine, String line, List<String> listErrs, List<String> listLineNumb) {

		boolean isError = false;

		String cf, nome, cognome, progetto, flagRL, cfBeneficiario;

		String[] strParse = line.split(";");

		try {
			cf = strParse[0];
			nome = strParse[1];
			cognome = strParse[2];
			progetto = strParse[3];
			flagRL = strParse[4];
			cfBeneficiario = strParse[5];

		} catch (Exception e) {
			listErrs.add("Caricamento file numero campi errato");
			listLineNumb.add("" + numLine);
			isError = true;
			return isError;
		}

		if (strParse.length != 6 || line.endsWith(";")) {
			listErrs.add("Caricamento file numero campi errato");
			listLineNumb.add("" + numLine);
			isError = true;
		}
		if (!ValidatorCodiceFiscale.isCodiceFiscalePersonaFisicaValido(cf)) {
			listErrs.add("Codice Fiscale errato");
			listLineNumb.add("" + numLine);
			isError = true;
		}

		if (nome.trim().length() == 0) {
			listErrs.add("Nome file non esistente");
			listLineNumb.add("" + numLine);
			isError = true;
		}
		if (cognome.trim().length() == 0) {
			listErrs.add("Cognome errato");
			listLineNumb.add("" + numLine);
			isError = true;
		}
		if (progetto.trim().length() == 0) {
			listErrs.add("Codice progetto errato");
			listLineNumb.add("" + numLine);
			isError = true;
		}

		if (flagRL.trim().length() != 1 || (!flagRL.equalsIgnoreCase("S") && !flagRL.equalsIgnoreCase("N"))) {
			listErrs.add("Flag Responsabile legale errato");
			listLineNumb.add("" + numLine);
			isError = true;
		}

		if (cfBeneficiario.trim().length() == 0) {
			listErrs.add("CF_BENEFICIARIO_ERRATO");
			listLineNumb.add("" + numLine);
			isError = true;
		}

		return isError;
	}

	@Override
	public Response gestioneBeneficiarioProgetto(Long idU, Long idSoggetto, HttpServletRequest req) throws Exception {
		String prf = "[GestioneUtentiImpl::gestioneBeneficiarioProgetto]";
		LOG.debug(prf + " BEGIN");

		HttpSession session = req.getSession();
		UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		BeneficiarioProgettoAssociatoDTO[] beneficiarioProgettoAssociatoDTO = null;
		try {

			beneficiarioProgettoAssociatoDTO = gestioneBackofficeSrv.findBeneficiarioProgettoAssociati(idU,
					userInfo.getIdIride(), idSoggetto);

		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(beneficiarioProgettoAssociatoDTO).build();
	}

}
