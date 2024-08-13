/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BandoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BandoLineaAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.CausaleDiErogazioneAssociataDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.DatiBandoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoAssociazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoSalvaDatiBando;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesa2DTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaAssociareDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaModificareDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.LineaDiInterventoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.PbandiDMateriaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.SoggettoFinanziatoreAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.TipoDiTrattamentoAssociatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.VoceDiSpesaAssociataDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.ConfigurazioneBandoService;
import it.csi.pbandi.pbwebbo.dto.configuraBando.DataModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbwebbo.integration.dao.ConfigurazioneBandoDAO;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class ConfigurazioneBandoServiceImpl implements ConfigurazioneBandoService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeSrv;

	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioSrv;
	
	@Autowired
	private ConfigurazioneBandoDAO configurazioneBandoDAO;
	
	@Autowired
	private GenericDAO genericDAO;

	@Override
	public Response findNormative(Long idU, HttpServletRequest req) throws CSIException {

		String prf = "[ConfigurazioneBandoServiceImpl:: findNormative]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, null, null, "lineeDiInterventoNormative", req);

//		theModel
//		.setAppDataCaricaComboNormative(gestioneBackOfficeBusiness
//				.findLineeDiIntervento(theModel
//						.getAppDatacurrentUser(), null,
//						Constants.PROGRAMMAZIONE_2016));
//
//		// Legge le linee intervento "vecchia programmazione".
//		theModel
//				.setAppDatalineeDiInterventoProgrPre2016(gestioneBackOfficeBusiness
//						.findLineeDiIntervento(theModel
//						.getAppDatacurrentUser(), null,
//						Constants.PROGRAMMAZIONE_PRE_2016));

		LOG.debug(prf + "[ConfigurazioneBandoServiceImpl::findNormative] - END");

		return lineaDiInterventoDTO;
	}

	@Override
	public Response findNormativePost2016(Long idU, HttpServletRequest req)
			throws GestioneBackOfficeException, SystemException, UnrecoverableException, CSIException {

		String prf = "[ConfigurazioneBandoServiceImpl:: findNormative]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, null, Constants.PROGRAMMAZIONE_2016,
				"lineeDiInterventoNormative", req);

		LOG.debug(prf + "[ConfigurazioneBandoServiceImpl::findNormative] - END");

		return lineaDiInterventoDTO;

	}

	@Override
	public Response ricercaBandi(Long idU, String titoloBando, String nomeBandoLinea, String normativa,
			Long idLineaDiIntervento, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::ricercaBandi]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", titoloBando = " + titoloBando + ", nomeBandoLinea = "
				+ nomeBandoLinea + ", idLineaDiIntervento = " + idLineaDiIntervento);

		final String RICERCABANDI_OUTCOME_CODE__VALIDATE_ERROR = "VALIDATE_ERROR";

		UserInfoSec userInfo = null;
		BandoDTO[] bandoDTO = null;

		try {

			HttpSession session = req.getSession();
			userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			LOG.debug(prf + "userInfo = " + userInfo);

			if (validateFindBandi(titoloBando, nomeBandoLinea, idLineaDiIntervento)) {
//				DA controllare
				ResponseCodeMessage resp = new ResponseCodeMessage();
				resp.setCode("Validazine campi errata");
				resp.setMessage(RICERCABANDI_OUTCOME_CODE__VALIDATE_ERROR);
				return Response.ok().entity(resp).build();
			}

			bandoDTO = gestioneBackofficeSrv.findBandiConfigurabili(idU, userInfo.getIdIride(), titoloBando,
					nomeBandoLinea, null, idLineaDiIntervento);
			if (bandoDTO != null && bandoDTO.length > 0) {
				LOG.debug(prf + "trovati " + bandoDTO.length + " disponibili per utente: " + idU + " e idBandoLinea = "
						+ idLineaDiIntervento);
			} else {
				LOG.debug(prf + "non ci sono bandi disponibili per utente: " + idU + " e idBandoLinea = "
						+ idLineaDiIntervento);
			}

		} catch (GestioneBackOfficeException e) {
			bandoDTO = null;
			LOG.debug(prf + e.getMessage());
			throw e;
		} catch (SystemException e) {
			bandoDTO = null;
			LOG.debug(prf + e.getMessage());
			throw e;
		} catch (UnrecoverableException e) {
			bandoDTO = null;
			throw e;
		} catch (CSIException e) {
			bandoDTO = null;
			LOG.debug(prf + e.getMessage());
			throw e;
		} catch (Exception e) {

			LOG.error("[BackEndFacade::ricercaBandi] Errore occorso nell'esecuzione del metodo:" + e, e);
			throw new Exception("Errore occorso nell'esecuzione del metodo:" + e, e);

		}

		LOG.debug(prf + " END");

		return Response.ok().entity(bandoDTO).build();

	}

	@Override
	public Response datibando(Long idU, String titoloBando, String nomeBandoLinea, String normativa,
			Long idLineaDiIntervento, Long idBando, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::configurabando]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", titoloBando = " + titoloBando + ", nomeBandoLinea = "
				+ nomeBandoLinea + ", idLineaDiIntervento = " + idLineaDiIntervento + ", " + "idBando = " + idBando);

		DatiBandoDTO bandoDaTrovare = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		// ANDIAMO A PRENDERE LE LINEE DI INTERVENTO

		// Non condideriamo materia
		// PbandiDMateriaDTO[] pbandiMateria =
		// gestioneBackofficeSrv.findMaterieDiRiferimento(idU, userInfo.getIdIride());

//		LineaDiInterventoDTO[] lineeDiIntervento = gestioneBackofficeSrv.findLineeDiIntervento(idU, userInfo.getIdIride(), null, Constants.PROGRAMMAZIONE_2016);
//		LineaDiInterventoDTO[] lineeDiInterventoPre2016 = gestioneBackofficeSrv.findLineeDiIntervento(idU, userInfo.getIdIride(), null, Constants.PROGRAMMAZIONE_PRE_2016);

		if (validateFindBandi(titoloBando, nomeBandoLinea, idBando)) {
//			DA controllare
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("Validazine campi errata");
			resp.setMessage("Nessun cmapo selezionato");
			return Response.ok().entity(resp).build();
		}

//		String tipoProgr = getTipoProgrammazione(idLineaDiIntervento); 

//		if(tipoProgr.equals(Constants.PROGRAMMAZIONE_2016)) {
//			
//			// Tab Dati Bando.
//			Decodifica[] tipologieDiAttivazione = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPOLOGIA_ATTIVAZIONE, idLineaDiIntervento);
//			
//			// Dati aggiuntivi - Indicatori
//			Decodifica[] tipiIndicatore = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.TIPO_INDICATORE, idLineaDiIntervento);
//			
//			// Dati aggiuntivi - Modalità di agevolazione
//			Decodifica[] modalitaDiAgevolazione = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(), GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE, idLineaDiIntervento);

		bandoDaTrovare = new DatiBandoDTO();
		bandoDaTrovare.setIdBando(idBando);
		bandoDaTrovare = gestioneBackofficeSrv.findDatiBando(idU, userInfo.getIdIride(), bandoDaTrovare);

//		}

		LOG.debug(prf + " END");

		return Response.ok().entity(bandoDaTrovare).build();
	}

	@Override
	public Response getIdProcessoByProgrBandoLineaIntervento(Long progrLineaLineaIntervento, HttpServletRequest req)
			throws UtenteException {
		String prf = "[ConfigurazioneBandoServiceImpl::getIdProcessoByIdBandoIdLineaIntervento]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (userInfo == null || userInfo.getIdUtente() == null || userInfo.getIdSoggetto() == null) {
			throw new UtenteException("Userinfo non valorizzato.");
		}

		LOG.info(prf + " progrLineaLineaIntervento = " + progrLineaLineaIntervento);
		if (progrLineaLineaIntervento == null) {
			throw new InvalidParameterException();
		}

		PbandiRBandoLineaInterventVO vo = new PbandiRBandoLineaInterventVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrLineaLineaIntervento));

		vo = genericDAO.findSingleOrNoneWhere(vo);
		if (vo != null) {
			LOG.info(prf + " END");
			return Response.ok().entity(vo.getIdProcesso()).build();
		}
		LOG.info(prf + " END");
		return Response.ok().entity(null).build();
	}

	@Override
	public Response datibandoCodiceIntesaIstituzionale(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoCodiceIntesaIstituzionale]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response codiciIntesaIstituzionali = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.INTESA,
				"datibandoCodiceIntesaIstituzionale", req);

		LOG.debug(prf + " END");

		return codiciIntesaIstituzionali;

	}

	@Override
	public Response datibandoSettoreCipe(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoSettoreCipe]";
		LOG.debug(prf + " BEGIN");

		Response settoreCipe = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.SETTORE_CIPE,
				"datibandoSettoreCipe", req);

		LOG.debug(prf + " END");

		return settoreCipe;
	}

	@Override
	public Response datibandoSottoSettoreCipe(Long idU, Long idSettoreCipe, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoSottoSettoreCipe]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSettoreCipe = " + idSettoreCipe);

		IdDescBreveDescEstesaDTO[] sottoSettorecipeDTO = null;
		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			sottoSettorecipeDTO = gestioneBackofficeSrv.findSottoSettoriCIPE(idU, userInfo.getIdIride(), idSettoreCipe);

		} catch (Exception e) {
			e.getStackTrace();
			resp.setCode("Errore in datibandoSottoSettoreCipe");
			resp.setMessage("Errore: " + e.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(sottoSettorecipeDTO).build();

	}

	@Override
	public Response datibandoNaturaCipe(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoNaturaCipe]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + " END");

		return Response.ok().entity(configurazioneBandoDAO.findNatureCipe()).build();

	}

	@Override
	public Response datibandoMateriaDiRiferimento(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoMateriaDiRiferimento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		PbandiDMateriaDTO[] pbandiMateria = gestioneBackofficeSrv.findMaterieDiRiferimento(idU, userInfo.getIdIride());

		LOG.debug(prf + " END");

		return Response.ok().entity(pbandiMateria).build();

	}

	@Override
	public Response datibandoTipologiadiattivazione(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandotipologiadiattivazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idLineaDiIntervento = " + idLineaDiIntervento);

		Response tipologieDiAttivazione = gestisciFindDecodificheMultiProgr(idU,
				GestioneDatiDiDominioSrv.TIPOLOGIA_ATTIVAZIONE, idLineaDiIntervento, "datibandoTipologiadiattivazione",
				req);

		LOG.debug(prf + " END");

		return tipologieDiAttivazione;

	}

	@Override
	public Response datibandoTipoOperazione(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoTipoOperazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response tipoOperazione = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.TIPO_OPERAZIONE,
				"datibandoTipoOperazione", req);

		LOG.debug(prf + " END");

		return tipoOperazione;

	}

	@Override
	public Response datibandoSettoreCPT(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datibandoSettoreCPT]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response settoreCPT = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.SETTORE_CPT, "datibandoSettoreCPT",
				req);

		LOG.debug(prf + " END");

		return settoreCPT;

	}

	@Override
	public Response lineeDiInterventoNormative(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoNormative]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);
		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, null, null, "lineeDiInterventoNormative", req);

		LOG.debug(prf + " END");

		return lineaDiInterventoDTO;

	}

	@Override
	public Response lineeDiInterventoAsse(Long idU, Long idLineaDiIntervento, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoAsse]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + "; idLineaDiIntervento = " + idLineaDiIntervento);

		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, idLineaDiIntervento, null, "lineeDiInterventoAsse",
				req);

		LOG.debug(prf + " END");

		return lineaDiInterventoDTO;

	}

	@Override
	public Response lineeDiInterventoMiusra(Long idU, Long idLineaPadreAsse, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoMiusra]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + "; idLineaPadreAsse = " + idLineaPadreAsse);

		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, idLineaPadreAsse, null, "lineeDiInterventoMiusra",
				req);

		LOG.debug(prf + " END");

		return lineaDiInterventoDTO;

	}

	@Override
	public Response lineeDiInterventoLinea(Long idU, Long idLineaPadreMisura, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoLinea]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + "; idLineaPadreMisura = " + idLineaPadreMisura);

		Response lineaDiInterventoDTO = findLineeDiIntervento(idU, idLineaPadreMisura, null, "lineeDiInterventoLinea",
				req);

		LOG.debug(prf + " END");

		return lineaDiInterventoDTO;

	}

	@Override
	public Response lineeDiInterventoCategoriaCipe(Long idU, Long idSottoSettoreCipe, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoCategoriaCipe]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + "idSottoSettoreCipe = " + idSottoSettoreCipe);

		Response categoriaCipe = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.CATEGORIA_CIPE,
				"idSottoSettoreCipe", String.valueOf(idSottoSettoreCipe), "lineeDiInterventoCategoriaCipe", req);

		LOG.debug(prf + " END");

		return categoriaCipe;

	}

	@Override
	public Response lineeDiInterventoTipologiaCipe(Long idU, String idNaturaCipe, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoTipologiaCipe]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + "idNaturaCipe = " + idNaturaCipe);

		Response tipologiaCipe = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.TIPOLOGIA_CIPE, "idNaturaCipe",
				idNaturaCipe, "lineeDiInterventoTipologiaCipe", req);

		LOG.debug(prf + " END");

		return tipologiaCipe;

	}

	@Override
	public Response lineeDiInterventoObiettivoTematico(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoObiettivoTematico]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response obiettivoTematico = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.OBIETTIVO_TEMATICO,
				"lineeDiInterventoObiettivoTematico", req);

		LOG.debug(prf + " END");

		return obiettivoTematico;

	}

	@Override
	public Response lineeDiInterventoClassificazioneRA(Long idU, String idObiettivoTematico, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoClassificazioneRA]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + "; idObiettivoTematico = " + idObiettivoTematico);

		Response classificazioneRA = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.CLASSIFICAZIONE_RA,
				"idObiettivoTem", idObiettivoTematico, "lineeDiInterventoClassificazioneRA", req);

		LOG.debug(prf + " END");

		return classificazioneRA;

	}

	@Override
	public Response lineeDiInterventoProcessoInterno(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoProcessoInterno]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response processoInterno = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.PROCESSO,
				"lineeDiInterventoProcessoInterno", req);

		LOG.debug(prf + " END");

		return processoInterno;

	}

	@Override
	public Response lineeDiInterventoElencoBandiSIF(Long idU, Long progrBandoLinea, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoElencoBandiSIF]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", progrBandoLinea = " + progrBandoLinea);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		BandoLineaAssociatoDTO[] bandoLineaAssociatoDTO = gestioneBackofficeSrv.findBandiSifNonAssociati(idU,
				userInfo.getIdIride(), progrBandoLinea);

		LOG.debug(prf + " END");

		return Response.ok().entity(bandoLineaAssociatoDTO).build();

	}

	@Override
	public Response lineeDiInterventoLivelloIstruzione(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoLivelloIstruzione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response livelloIstruzione = gestisciFindDecodificheMultiProgr(idU,
				GestioneDatiDiDominioSrv.LIVELLO_ISTITUZIONE, idLineaDiIntervento, "lineeDiInterventoLivelloIstruzione",
				req);

		LOG.debug(prf + " END");

		return livelloIstruzione;

	}

	@Override
	public Response lineeDiInterventoTipoLocalizzazione(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoTipoLocalizzazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response tipoLocalizzazione = gestisciFindDecodificheMultiProgr(idU,
				GestioneDatiDiDominioSrv.TIPO_LOCALIZZAZIONE, idLineaDiIntervento,
				"lineeDiInterventoTipoLocalizzazione", req);

		LOG.debug(prf + " END");

		return tipoLocalizzazione;

	}

	@Override
	public Response lineeDiInterventoProgettoComplesso(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoProgettoComplesso]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response progettoComplesso = gestisciFindDecodificheMultiProgr(idU, GestioneDatiDiDominioSrv.PROGETTO_COMPLESSO,
				idLineaDiIntervento, "lineeDiInterventoProgettoComplesso", req);

		LOG.debug(prf + " END");

		return progettoComplesso;

	}

	@Override
	public Response lineeDiInterventoClassificazioneMeccanismiErogazioneTerritoriale(Long idU, Long idLineaDiIntervento,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoClassificazioneMeccanismiErogazioneTerritoriale]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idLineaDiIntervento = " + idLineaDiIntervento);

		Response met = gestisciFindDecodificheMultiProgr(idU, GestioneDatiDiDominioSrv.CLASSIFICAZIONE_MET,
				idLineaDiIntervento, "lineeDiInterventoClassificazioneMeccanismiErogazioneTerritoriale", req);

		LOG.debug(prf + " END");

		return met;

	}

	@Override
	public Response lineeDiInterventoPrioritaQSN(Long idU, String idAsse, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoPrioritaQSN]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + "; idAsse = " + idAsse);

		Response met = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.PRIORITA_QSN_LINEA, "idLineaDiIntervento",
				idAsse, "lineeDiInterventoPrioritaQSN", req);

		LOG.debug(prf + " END");

		return met;

	}

	@Override
	public Response lineeDiInterventoObiettivoGeneraleQSN(Long idU, String idPrioritaQsn, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoObiettivoGeneraleQSN]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + "; idPrioritaQsn = " + idPrioritaQsn);

		Response obiettivoGeneraleQSN = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.OBIETTIVO_GEN_QSN,
				"idPrioritaQsn", idPrioritaQsn, "lineeDiInterventoObiettivoGeneraleQSN", req);

		LOG.debug(prf + " END");

		return obiettivoGeneraleQSN;

	}

	@Override
	public Response lineeDiInterventoObiettivoSpecificoQSN(Long idU, String idObiettivoGenQsn, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoObiettivoSpecificoQSN]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + "; idObiettivoGenQsn" + idObiettivoGenQsn);

		Response obiettivoSpecificoQSN = findDecodificheFiltrato(idU, GestioneDatiDiDominioSrv.OBIETTIVO_SPECIF_QSN,
				"idObiettivoGenQsn", idObiettivoGenQsn, "lineeDiInterventoObiettivoSpecificoQSN", req);

		LOG.debug(prf + " END");

		return obiettivoSpecificoQSN;

	}

	@Override
	public Response vociDiSpesaVoceDiSpesa(Long idU, Long idVoceDiSpesaPadre, Long idBando, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::vociDiSpesaVoceDiSpesa]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + ", idBando = " + idBando + ", idVoceDiSpesaPadre" + idVoceDiSpesaPadre);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		VoceDiSpesaAssociataDTO[] voceDiSpesaAssociataDTO = gestioneBackofficeSrv.findVociDiSpesa(idU,
				userInfo.getIdIride(), idVoceDiSpesaPadre, idBando);

		LOG.debug(prf + " END");

		return Response.ok().entity(voceDiSpesaAssociataDTO).build();

	}

	@Override
	public Response vociDiSpesaVoceSottovoce(Long idU, Long idVoceDiSpesaPadre, Long idBando, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::vociDiSpesaVoceSottovoce]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + ", idBando = " + idBando + ", idVoceDiSpesaPadre" + idVoceDiSpesaPadre);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		VoceDiSpesaAssociataDTO[] voceDiSpesaAssociataDTO = gestioneBackofficeSrv.findVociDiSpesa(idU,
				userInfo.getIdIride(), idVoceDiSpesaPadre, idBando);

		LOG.debug(prf + " END");

		return Response.ok().entity(voceDiSpesaAssociataDTO).build();
	}

	@Override
	public Response vociDiSpesaVociAssociate(Long idU, Long idBando, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::vociDiSpesaVociAssociate]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		IdDescBreveDescEstesa2DTO[] vociDiSpesaAssociate = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			vociDiSpesaAssociate = gestioneBackofficeSrv.findVociDiSpesaAssociate(idU, userInfo.getIdIride(), idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in vociDiSpesaVociAssociate");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(vociDiSpesaAssociate).build();

	}

	@Override
	public Response vociDiSpesaVoceDiSpesaMonitoraggio(Long idU, Long idTipoOperazione, Long idNaturaCipe,
			HttpServletRequest req) throws Exception {
		String prf = "[ConfigurazioneBandoServiceImpl::vociDiSpesaVoceDiSpesaMonitoraggio]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idTipoOperazione = " + idTipoOperazione
				+ ", idNaturaCipe = " + idNaturaCipe);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findVociDiSpesaMonitoraggio(idU,
				userInfo.getIdIride(), idTipoOperazione, idNaturaCipe);

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}

	@Override
	public Response datiaggiuntiviModalitaDiAgevolazione(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviModalitaDiAgevolazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idLineaDiIntervento = " + idLineaDiIntervento);

		Response modalitaDiAgevolazione = gestisciFindDecodificheMultiProgr(idU,
				GestioneDatiDiDominioSrv.MODALITA_AGEVOLAZIONE, idLineaDiIntervento,
				"datiaggiuntiviModalitaDiAgevolazione", req);

		LOG.debug(prf + " END");

		return modalitaDiAgevolazione;

	}

	@Override
	public Response datiaggiuntiviModalitaAssociata(Long idU, Long idBando, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviModalitaAssociata]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findModalitaDiAgevolazioneAssociate(idU,
					userInfo.getIdIride(), idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviModalitaAssociata");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}
	
	//Aggiunto questo metodo che migliora il servizio precedente, lascio anche il precedete non sapendo dove venga usato
	@Override
	public Response datiaggiuntiviModalitaAssociata(Long idBando, HttpServletRequest req) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idBando = " + idBando);

		List<DataModalitaAgevolazioneDTO> dataModalitaAgevolazioneDTOList = null;	

		try {
			
			dataModalitaAgevolazioneDTOList = configurazioneBandoDAO.findModalitaDiAgevolazioneAssociate(idBando);
			//LOG.info(prf + " Prova");
			
		} catch (Exception e) {
			LOG.error(prf + ": "+e);
			e.getStackTrace();
			ResponseCodeMessage rcm = new ResponseCodeMessage("KO", e.getMessage());
			return Response.status(500).entity(rcm).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(dataModalitaAgevolazioneDTOList).build();

	}

	
	@Override
	public Response datiaggiuntiviAssociaModalitaDiAgevolazione(Long idU, Long idBando, Long idModalitaDiAgevolazione,Double importoAgevolato, 
			Double massimoImportoAgevolato, String flagLiquidazione, Double periodoStabilita, Double importoDaErogare, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviAssociaModalitaAgevolazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando
				+ ", idModalitaDiAgevolazione = " + idModalitaDiAgevolazione + ", importoAgevolato = "
				+ importoAgevolato + "" + ", massimoImportoAgevolato = " + massimoImportoAgevolato
				+ ", flagLiquidazione = " + flagLiquidazione);

		EsitoAssociazione esito = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			esito = gestioneBackofficeSrv.associaModalitaDiAgevolazione(idU, userInfo.getIdIride(), idBando,
					idModalitaDiAgevolazione, importoAgevolato, massimoImportoAgevolato, flagLiquidazione, null, periodoStabilita, importoDaErogare);

		} catch (Exception e) {
			esito.setEsito(false);
			esito.setMessage("Impossibile effettuare l'inserimento. " + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	@Transactional
	public Response datiaggiuntiviEliminaModalitaDiAgevolazioneAssociata(Long idU, Long idBando,
			Long idModalitaDiAgevolazione, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviEliminaModalitaDiAgevolazioneAssociata]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando
				+ ", idModalitaDiAgevolazione = " + idModalitaDiAgevolazione);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		//ResponseCodeMessage responseCodeMessage = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			
			boolean hasEsterimiBancariAssociati = configurazioneBandoDAO.modalitaAgevolazioneHasEstremiBancariAssociati(idBando, idModalitaDiAgevolazione);
			if(hasEsterimiBancariAssociati)
				throw new ErroreGestitoException("Impossibile effettuare la cancellazione della modalità"
						+ " di agevolazione se ci sono degli estremi bancari associati");
			
			
			esito = gestioneBackofficeSrv.eliminaModalitaDiAgevolazioneAssociata(idU, userInfo.getIdIride(), idBando,
					idModalitaDiAgevolazione);

		} 
		catch (ErroreGestitoException e) {
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
			//responseCodeMessage.setCode("KO");
			//responseCodeMessage.setMessage(e.getMessage());
			//return Response.status(500).entity(responseCodeMessage).build();
		}
		catch (Exception e) {
			esito.setEsito(false);
			esito.setMessage("Impossibile effettuare la cancellazione della modalità di agevolazione con idBando = "
					+ idBando + " e idModalitaDiAgevolazione = " + idModalitaDiAgevolazione + ". " + e);
			//responseCodeMessage.setCode("KO");
			//responseCodeMessage.setMessage(e.getMessage());
			//return Response.status(500).entity(esito).build();
		}

		LOG.debug(prf + " END");

		//return Response.ok().entity(esito).build();
		return Response.ok().entity(esito).build();

	}

	@Override
	public Response findTipoTrattamento(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::findTipoTrattamento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		Decodifica[] tipoDiTrattamento = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			tipoDiTrattamento = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(),
					GestioneDatiDiDominioSrv.TIPO_TRATTAMENTO);
		} catch (Exception e) {
			resp.setCode("ERRORE durente la ricerca dei tipiTrattamento");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(tipoDiTrattamento).build();

	}

	@Override
	public Response datiaggiuntiviTipoTrattamentoAssociato(Long idU, Long idBando, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviTipoTrattamentoAssociato]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		TipoDiTrattamentoAssociatoDTO[] tipoDiTrattamentoAssociatoDTO = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			tipoDiTrattamentoAssociatoDTO = gestioneBackofficeSrv.findTipiDiTrattamentoAssociati(idU,
					userInfo.getIdIride(), idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviTipoTrattamentoAssociato");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(tipoDiTrattamentoAssociatoDTO).build();

	}

	@Override
	public Response datiaggiuntiviAssociaTipoTrattamento(Long idU, Long idBando, Long idTipoDiTrattamento,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviAssociaTipoTrattamento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", idTipoDiTrattamento = "
				+ idTipoDiTrattamento);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		EsitoAssociazione esito = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			if (idTipoDiTrattamento == null || idTipoDiTrattamento == 0) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_SELEZIONARE_ALMENO_UN_ELEMENTO);
			} else {

				Long[] idTipoDiTrattamentoArray = new Long[] { idTipoDiTrattamento };
				esito = gestioneBackofficeSrv.associaTipiDiTrattamento(idU, userInfo.getIdIride(), idBando,
						idTipoDiTrattamentoArray);

				if (esito == null) {
					resp.setCode("ERROR");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
				} else if (!esito.getEsito()) {
					resp.setCode("ERROR");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("OK");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
				}

			}

		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviAssociaTipoTrattamento");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();
	}

	@Override
	public Response datiaggiuntiviEliminaTipoTrattamentoAssociato(Long idU, Long idBando, Long idTipoTrattamento,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviEliminaTipoTrattamentoAssociato]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + ", idBando = " + idBando + ", idTipoTrattamento = " + idTipoTrattamento);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaTipoDiTrattamento(idU,
				userInfo.getIdIride(), idBando, idTipoTrattamento);

		if (esito != null && esito.getEsito()) {
			resp.setCode("OK");
			;
			resp.setMessage(esito.getMessage());
		} else {
			resp.setCode("ERROR");
			;
			resp.setMessage(esito.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntiviTipoTrattamentoAssociatoPredefinito(Long idU, Long idBando, Long idTipoTrattamento,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviTipoTrattamentoAssociatoPredefinito]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ",  idTipoTrattamento = "
				+ idTipoTrattamento);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		GestioneBackOfficeEsitoGenerico esito = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {

			if (idTipoTrattamento == null || idBando == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_SELEZIONARE_ALMENO_UN_ELEMENTO);
			} else {
				esito = gestioneBackofficeSrv.rendiTipoDiTrattamentoPredefinito(idU, userInfo.getIdIride(), idBando,
						idTipoTrattamento);

				if (esito != null && esito.getEsito()) {
					resp.setCode("OK");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("ERROR");
					resp.setMessage(esito.getMessage());
				}

			}

		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviTipoTrattamentoAssociatoPredefinito");
			resp.setMessage("Errore: " + e.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntiviSoggettiFinanziatori(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviSoggettiFinanziari]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findSoggettiFinanziatori(idU,
				userInfo.getIdIride());

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}

	@Override
	public Response datiaggiuntiviSoggettiFinanziatoreAssociato(Long idU, Long idBando, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviSoggettiFinanziatoreAssociato]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		SoggettoFinanziatoreAssociatoDTO[] soggettoFinanziatoreAssociatoDTO = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			soggettoFinanziatoreAssociatoDTO = gestioneBackofficeSrv.findSoggettiFinanziatoriAssociati(idU,
					userInfo.getIdIride(), idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviSoggettiFinanziatoreAssociato");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(soggettoFinanziatoreAssociatoDTO).build();

	}

	@Override
	public Response datiaggiuntiviAssociaSoggettoFinanziatore(Long idU, Long idBando, String soggettoFinanziatore,
			Long idSoggettoFinanziatore, Double percentuale, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviAssociaSoggettoFinanziatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", soggettoFinanziatore = "
				+ soggettoFinanziatore + ", " + "idSoggettoFinanziatore = " + idSoggettoFinanziatore
				+ ", percentuale = " + percentuale);

		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			if (idBando == null || soggettoFinanziatore == null || idSoggettoFinanziatore == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_CAMPO_OBBLIGATORIO);
			} else {
				SoggettoFinanziatoreAssociatoDTO soggettoFinanziatoreAssociatoDTO = new SoggettoFinanziatoreAssociatoDTO();
				soggettoFinanziatoreAssociatoDTO.setIdSoggettoFinanziatore(idSoggettoFinanziatore);
				soggettoFinanziatoreAssociatoDTO.setDescSoggFinanziatore(soggettoFinanziatore);
				soggettoFinanziatoreAssociatoDTO.setPercentualeQuotaSoggFinanz(percentuale);
				soggettoFinanziatoreAssociatoDTO.setIdBando(idBando);

				EsitoAssociazione esito = gestioneBackofficeSrv.associaSoggettoFinanziatore(idU, userInfo.getIdIride(),
						soggettoFinanziatoreAssociatoDTO);

				if (esito == null) {
					resp.setCode("ERROR");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
				} else if (!esito.getEsito()) {
					resp.setCode("ERROR");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("OK");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
				}
			}

		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviAssociaSoggettoFinanziatore");
			resp.setMessage("Errore: " + e.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();
	}

	@Override
	public Response datiaggiuntiviEliminaSoggettoFinanziatore(Long idU, Long idBando, Long idSoggettoFinanziatore,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviEliminaSoggettoFinanziatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", idSoggettoFinanziatore = "
				+ idSoggettoFinanziatore);

		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		if (idBando == null || idSoggettoFinanziatore == null) {
			resp.setCode("ERROR");
			resp.setMessage("idBando e idSoggettoFinanziatore sono campi obbligatori");
		} else {

			GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaSoggettoFinanziatore(idU,
					userInfo.getIdIride(), idBando, idSoggettoFinanziatore);

			if (esito != null && esito.getEsito()) {
				resp.setCode("OK");
				resp.setMessage(esito.getMessage());
			} else {
				resp.setCode("ERROR");
				resp.setMessage(esito.getMessage());
			}
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntiviCausaleErogazione(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviCausaleErogazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		IdDescBreveDescEstesaDTO[] idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findCausaliDiErogazione(idU,
				userInfo.getIdIride());

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}

	@Override
	public Response datiaggiuntiviCausaleErogazioneAssociata(Long idU, Long idBando, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviCausaleErogazioneAssociata]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		CausaleDiErogazioneAssociataDTO[] causaleDiErogazioneAssociataDTO = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			causaleDiErogazioneAssociataDTO = gestioneBackofficeSrv.findCausaliDiErogazioneAssociati(idU,
					userInfo.getIdIride(), idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviCausaleErogazioneAssociata");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(causaleDiErogazioneAssociataDTO).build();

	}

	@Override
	public Response datiaggiuntiviDimensioneImpresa(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviDimensioneImpresa]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response dimensioneImpresa = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.DIMENSIONE_IMPRESA,
				"datiaggiuntiviDimensioneImpresa", req);

		LOG.debug(prf + " END");

		return dimensioneImpresa;

	}

	@Override
	public Response datiaggiuntiviFormaGiuridica(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviFormaGiuridica]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response formaGiuridica = gestisciFindDecodifiche(idU, GestioneDatiDiDominioSrv.FORMA_GIURIDICA,
				"datiaggiuntiviFormaGiuridica", req);

		LOG.debug(prf + " END");

		return formaGiuridica;

	}

	@Override
	public Response datiAggiuntiviAssociaCausale(HttpServletRequest req, Long idU,
			CausaleDiErogazioneAssociataDTO causaleDiErogazione) throws UtenteException, Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiAggiuntiviAssociaCausale]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ",  idBando = " + causaleDiErogazione.getIdBando()
				+ ", percSogliaSpesaQuietanzata = " + causaleDiErogazione.getPercSogliaSpesaQuietanzata() + ""
				+ "idFormaGiuridica = " + causaleDiErogazione.getIdFormaGiuridica() + ", idCausaleErogazione= "
				+ causaleDiErogazione.getIdCausaleErogazione() + ", " + "percErogazione = "
				+ causaleDiErogazione.getPercErogazione() + ", idDimensioneImpresa = "
				+ causaleDiErogazione.getIdDimensioneImpresa() + ", " + "percLimite = "
				+ causaleDiErogazione.getPercLimite());

		ResponseCodeMessage resp = new ResponseCodeMessage();
		EsitoAssociazione esito = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			if (causaleDiErogazione.getIdCausaleErogazione() == null
					|| causaleDiErogazione.getIdCausaleErogazione() == 0) {
				resp.setCode("ERROR");
				resp.setMessage("IdCausaleErogazione non può essere null");
			} else {
				esito = gestioneBackofficeSrv.associaCausaleDiErogazione(idU, userInfo.getIdIride(),
						causaleDiErogazione);
				if (esito == null) {
					resp.setCode("ERROR");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
				} else if (!esito.getEsito()) {
					resp.setCode("ERROR");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("OK");
					resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
				}

			}

		} catch (Exception e) {
			resp.setCode("ERROR in datiAggiuntiviAssociaCausale");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntivEliminaCausaleErogazione(Long idU, Long progrBandoCausaleErogaz,
			HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntivEliminaCausaleErogazione]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idU = " + idU + ", progrBandoCausaleErogaz = " + progrBandoCausaleErogaz);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaCausaleDiErogazione(idU,
				userInfo.getIdIride(), progrBandoCausaleErogaz);

		if (esito != null && esito.getEsito()) {
			resp.setCode("OK");
			resp.setMessage(esito.getMessage());
		} else {
			resp.setCode("ERROR");
			resp.setMessage(esito.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntiviTipoIndicatore(Long idU, Long idLineaDiIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviTipoIndicatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		Response tipoIndicatore = gestisciFindDecodificheMultiProgr(idU, GestioneDatiDiDominioSrv.TIPO_INDICATORE,
				idLineaDiIntervento, "datiaggiuntiviTipoIndicatore", req);

		LOG.debug(prf + " END");

		return tipoIndicatore;

	}

	@Override
	public Response datiaggiuntiviIndicatore(Long idU, Long idTipoIndicatore, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviIndicatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idTipoIndicatore = " + idTipoIndicatore);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		IdDescBreveDescEstesaDTO[] indicatoreDTO = gestioneBackofficeSrv.findIndicatori(idU, userInfo.getIdIride(),
				idTipoIndicatore);

		LOG.debug(prf + " END");

		return Response.ok().entity(indicatoreDTO).build();

	}

	@Override
	public Response datiaggiuntiviIndicatoreAssociato(Long idU, Long idBando, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviIndicatoreAssociato]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		IdDescBreveDescEstesa2DTO[] idDescBreveDescEstesaDTO = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			idDescBreveDescEstesaDTO = gestioneBackofficeSrv.findIndicatoriAssociati(idU, userInfo.getIdIride(),
					idBando);
		} catch (Exception e) {
			resp.setCode("ERROR in datiaggiuntiviIndicatoreAssociato");
			resp.setMessage("Errore: " + e.getMessage());
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(idDescBreveDescEstesaDTO).build();

	}

	@Override
	public Response datiaggiuntiviAssociaIndicatore(Long idU, Long idBando, Long idIndicatore, String infoIniziale, String infoFinale, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviAssociaIndicatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", idIndicatore = "
				+ idIndicatore);

		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			EsitoAssociazione esito = gestioneBackofficeSrv.associaIndicatore(idU, userInfo.getIdIride(), idBando,
					idIndicatore, infoIniziale, infoFinale);

			if (esito == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				resp.setCode("ERROR");
				resp.setMessage(esito.getMessage());
			} else {
				resp.setCode("OK");
				resp.setMessage(esito.getMessage());
			}
		} catch (Exception e) {
			resp.setCode("ERROR");
			resp.setMessage("Impossibile effettuare l'inserimento." + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response datiaggiuntiviEliminaIndicatore(Long idU, Long idBando, Long idIndicatore, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::datiaggiuntiviEliminaIndicatore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", idIndicatore = "
				+ idIndicatore);

		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaIndicatoreAssociato(idU,
					userInfo.getIdIride(), idBando, idIndicatore);

			if (esito == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				resp.setCode("ERROR");
				resp.setMessage(esito.getMessage());
			} else {
				resp.setCode("OK");
				resp.setMessage(esito.getMessage());
			}
		} catch (Exception e) {
			resp.setCode("ERROR");
			resp.setMessage("Impossibile effettuare l'inserimento." + e);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response salvaDatiBando(HttpServletRequest req, DatiBandoDTO datiBando) throws UtenteException, Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::salvaDatiBando]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idBando = " + datiBando.getIdBando());

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoSalvaDatiBando esito = null;
		try {
			esito = gestioneBackofficeSrv.salvaDatiBando(datiBando.getIdUtenteAgg(), userInfo.getIdIride(), datiBando);
		} catch (Exception e) {
			LOG.error("[BackEndFacade::salvaDatiBando] Errore occorso nell'esecuzione del metodo:" + e, e);
			esito = new EsitoSalvaDatiBando();
			esito.setEsito(false);
			esito.setMessage(e.getMessage());
		}

		LOG.debug(prf + " END");
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response lineeDiInterventoAssociate(Long idU, Long idBando, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineeDiInterventoAssociate]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBando = " + idBando);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		BandoLineaAssociatoDTO[] bandoLineaAssociatoDTO = gestioneBackofficeSrv.findLineeDiInterventoAssociate(idU,
				userInfo.getIdIride(), idBando);

		LOG.debug(prf + " END");

		return Response.ok().entity(bandoLineaAssociatoDTO).build();

	}

	@Override
	public Response modificaLineaDiIntervento(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::modificaLineaDiIntervento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", progrBandoLineaIntervento = "
				+ progrBandoLineaIntervento);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		LineaDiInterventiDaModificareDTO lineaDiInterventiDaModificareDTO = gestioneBackofficeSrv
				.findLineaDiIntervento(idU, userInfo.getIdIride(), progrBandoLineaIntervento);

		if (lineaDiInterventiDaModificareDTO != null && lineaDiInterventiDaModificareDTO.getFlagSif() == null) {
			lineaDiInterventiDaModificareDTO.setFlagSif("N");
		}
		if (lineaDiInterventiDaModificareDTO != null
				&& lineaDiInterventiDaModificareDTO.getFlagFondoDiFondi() == null) {
			lineaDiInterventiDaModificareDTO.setFlagFondoDiFondi("N");
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(lineaDiInterventiDaModificareDTO).build();

	}

	@Override
	public Response associaLineaIntervento(HttpServletRequest req,
			LineaDiInterventiDaAssociareDTO dettaglioLineaDiIntervento) throws UtenteException, Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::associaLineaIntervento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> dettaglioLineaDiIntervento = " + dettaglioLineaDiIntervento.toString());

		ResponseCodeMessage resp = new ResponseCodeMessage();

		// Validaete checkrunner

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		EsitoAssociazione esitoAssociazione = gestioneBackofficeSrv.associaLineaDiIntervento(userInfo.getIdUtente(),
				userInfo.getIdIride(), dettaglioLineaDiIntervento);

		if (esitoAssociazione == null) {
			resp.setCode("ERROR");
			resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
		} else if (esitoAssociazione.getEsito() == null) {
			if (esitoAssociazione.getIdAssociazione() != null) {
				resp.setCode("OKSIF");
			} else {
				resp.setCode("ERRORSIF");
			}
			resp.setMessage(esitoAssociazione.getMessage());
		} else if (!esitoAssociazione.getEsito()) {
			resp.setCode("ERROR");
			resp.setMessage(esitoAssociazione.getMessage());
		} else {
			resp.setCode("OK");
			resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response lineaDiInterventoSalvaModifiche(HttpServletRequest req,
			LineaDiInterventiDaModificareDTO dettaglioLineaDiIntervento) throws UtenteException, Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::lineaDiInterventoSalvaModifiche]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> dettaglioLineaDiIntervento. idLinea = "
				+ dettaglioLineaDiIntervento.getIdLinea());

		GestioneBackOfficeEsitoGenerico esitoModifica = null;
		ResponseCodeMessage resp = new ResponseCodeMessage();

		// Validaete checkrunner

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			esitoModifica = gestioneBackofficeSrv.modificaLineaDiIntervento(userInfo.getIdUtente(),
					userInfo.getIdIride(), dettaglioLineaDiIntervento);

			if (esitoModifica == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esitoModifica.getEsito()) {
				resp.setCode("ERROR");
				resp.setMessage(esitoModifica.getMessage());
			} else {
				resp.setCode("OK");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
			}

		} catch (Exception e) {
			resp.setCode("ERROR");
			resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response eliminaLineaDiIntervento(Long idU, Long progrBandoLineaIntervento, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::eliminaLineaDiIntervento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", progrBandoLineaIntervento = "
				+ progrBandoLineaIntervento);

		final String ELIMINALINEADIINTERVENTO_OUTCOME_CODE__NO_SELECTED = "NO_SELECTED";

		ResponseCodeMessage resp = new ResponseCodeMessage();

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		if (progrBandoLineaIntervento == null || progrBandoLineaIntervento <= 0) {
			resp.setCode(ELIMINALINEADIINTERVENTO_OUTCOME_CODE__NO_SELECTED);
			resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_SELEZIONARE_ALMENO_UN_ELEMENTO);
		} else {

			GestioneBackOfficeEsitoGenerico esito = gestioneBackofficeSrv.eliminaBandoLineaAssociato(
					userInfo.getIdUtente(), userInfo.getIdIride(), progrBandoLineaIntervento);

			if (esito == null || !esito.getEsito()) {
				resp.setCode("ERROR");
				resp.setMessage(esito.getMessage());
			} else {
				resp.setCode("OK");
				resp.setMessage(esito.getMessage());
			}

		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	// Metodo centralizzato per la gestinoe della funzionalita'
	// "findLineeDiIntervento"
	private Response findLineeDiIntervento(Long idU, Long idLineaDiIntervento, String tipoProgrammazione,
			String metodoChiamante, HttpServletRequest req) {

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		LineaDiInterventoDTO[] lineaDiInterventoDTO = null;

		try {

			lineaDiInterventoDTO = gestioneBackofficeSrv.findLineeDiIntervento(idU, userInfo.getIdIride(),
					idLineaDiIntervento, tipoProgrammazione);
			LOG.debug("Trovate " + lineaDiInterventoDTO.length + " deocodificheMultiProgr");
			for (int i = 0; i < lineaDiInterventoDTO.length; i++) {
				LOG.debug("idLineaIntervento =" + lineaDiInterventoDTO[i].getIdLinea() + "; descrizioneBreve ="
						+ lineaDiInterventoDTO[i].getDescBreve() + "; " + "descrizione estesa ="
						+ lineaDiInterventoDTO[i].getDescEstesa());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Errore in: " + metodoChiamante + ". Errore: " + e.getMessage());
			resp.setCode("Errore in findLineeDiIntervento dal chiamante: " + metodoChiamante);
			resp.setMessage("Errore generico: " + e.getStackTrace());
			return Response.ok().entity(resp).build();

		}

		return Response.ok().entity(lineaDiInterventoDTO).build();

	}

	// Metodo centralizzato per la gestinoe della funzionalita'
	// "findDecodificheFiltrato"
	private Response findDecodificheFiltrato(Long idU, String nomeTabella, String chiave, String id,
			String metodoChiamante, HttpServletRequest req) {

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		Decodifica[] decodifica = null;

		try {

			decodifica = gestioneDatiDiDominioSrv.findDecodificheFiltrato(idU, userInfo.getIdIride(), nomeTabella,
					chiave, id);
			LOG.debug("Trovate " + decodifica.length + " deocodificheMultiProgr");
			for (int i = 0; i < decodifica.length; i++) {
				LOG.debug("id =" + decodifica[i].getId() + "; descrizioneBreve =" + decodifica[i].getDescrizioneBreve()
						+ "; " + "descrizione =" + decodifica[i].getDescrizione());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Errore in: " + metodoChiamante + ". Errore: " + e.getMessage());
			resp.setCode("Errore in findLineeDiIntervento dal chiamante: " + metodoChiamante);
			resp.setMessage("Errore generico: " + e.getStackTrace());
			return Response.ok().entity(resp).build();

		}

		return Response.ok().entity(decodifica).build();

	}

	// Metodo centralizzato per la gestinoe della funzionalita' "findDecodifiche"
	private Response gestisciFindDecodifiche(Long idU, String tabellaDiDominio, String metodoChiamante,
			HttpServletRequest req) {

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		Decodifica[] decodifica = null;

		try {

			decodifica = gestioneDatiDiDominioSrv.findDecodifiche(idU, userInfo.getIdIride(), tabellaDiDominio);
			LOG.debug("Trovate " + decodifica.length + " deocodifiche");
			for (int i = 0; i < decodifica.length; i++) {
				LOG.debug("id =" + decodifica[i].getId() + "; descrizioneBreve =" + decodifica[i].getDescrizioneBreve()
						+ "; descrizione =" + decodifica[i].getDescrizione());
			}

		} catch (GestioneDatiDiDominioException | UnrecoverableException e) {

			e.printStackTrace();
			LOG.error("Errore in: " + metodoChiamante + ". Errore: " + e.getStackTraceMessage());
			resp.setCode("Errore in gestisciFindDecodifiche dal chiamante: " + metodoChiamante);
			resp.setMessage("Errore: " + e.getStackTrace());
			return Response.ok().entity(resp).build();

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error("Errore generico in: " + metodoChiamante + ". Errore: " + e.getStackTrace());
			resp.setCode("Errore generico in gestisciFindDecodifiche dal chiamante: " + metodoChiamante);
			resp.setMessage("Errore generico: " + e.getStackTrace());
			return Response.ok().entity(resp).build();

		}

		return Response.ok().entity(decodifica).build();

	}

	// Metodo centralizzato per la gestinoe della funzionalita'
	// "findDecodificheMultiProgr"
	private Response gestisciFindDecodificheMultiProgr(Long idU, String tabellaDiDominio, Long idLineaDiIntervento,
			String metodoChiamante, HttpServletRequest req) {

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		Decodifica[] decodifica = null;

//			LOG.debug("Parametri in input -> idLineaDiIntervento = "+idLineaDiIntervento);

		try {

			decodifica = gestioneDatiDiDominioSrv.findDecodificheMultiProgr(idU, userInfo.getIdIride(),
					tabellaDiDominio, idLineaDiIntervento);
			LOG.debug("Trovate " + decodifica.length + " deocodificheMultiProgr");
			for (int i = 0; i < decodifica.length; i++) {
				LOG.debug("id =" + decodifica[i].getId() + "; descrizioneBreve =" + decodifica[i].getDescrizioneBreve()
						+ "; descrizione =" + decodifica[i].getDescrizione());
				
				if(decodifica[i].getDescrizioneBreve() != null && (
					decodifica[i].getDescrizioneBreve().equals("Contributo") ||
					decodifica[i].getDescrizioneBreve().equals("AiutiFondoRotativo") ||
					decodifica[i].getDescrizioneBreve().equals("SSF: garanzia") )
				) {
					decodifica[i].setHasPeriodoDiStabilita(true);
				}
				
				
			}

		} catch (GestioneDatiDiDominioException | UnrecoverableException e) {

			e.printStackTrace();
			LOG.error("Errore in: " + metodoChiamante + ". Errore: " + e.getStackTraceMessage());
			String str = "";
			resp.setCode("Errore in gestisciFindDecodificheMultiProgr dal chiamante: " + metodoChiamante);
			str += resp.getCode() + "\n";
			resp.setMessage("Errore: " + e.getStackTrace());
			str += resp.getMessage() + "\n";
			return Response.status(500, str).entity(resp).build();

		} catch (Exception e) {

			e.printStackTrace();
			LOG.error("Errore generico in: " + metodoChiamante + ". Errore: " + e.getStackTrace());
			String str = "";
			resp.setCode("Errore in gestisciFindDecodificheMultiProgr dal chiamante: " + metodoChiamante);
			str += resp.getCode() + "\n";
			resp.setMessage("Errore: " + e.getStackTrace());
			str += resp.getMessage() + "\n";
			return Response.status(500, str).entity(resp).build();

		}

		return Response.ok().entity(decodifica).build();

	}

	@Override
	public Response vociDiSpesaVoceAssociaVoce(Long idU, Long idBando, Long idVoceDiSpesa, String descVoceDiSpesa,
			Long idSottovoce, String descSottovoce, Long idVoceDiSpesaMonit, String codTipoVoceDiSpesaMacro,
			String codTipoVoceDiSpesaMicro, String flagSpeseGestioneMacro, String flagSpeseGestioneMicro, HttpServletRequest req) throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::eliminaLineaDiIntervento]";
		LOG.debug(prf + " BEGIN");

		LOG.debug("Parametri in input -> idU = " + idU + ", idBando = " + idBando + ", idVoceDiSpesa = " + idVoceDiSpesa
				+ ", descVoceDiSpesa = " + descVoceDiSpesa + ", idSottovoce = " + idSottovoce + ", descSottovoce = "
				+ descSottovoce + ", idVoceDiSpesaMonit = " + idVoceDiSpesaMonit + ", codTipoVoceDiSpesaMacro = "
				+ codTipoVoceDiSpesaMacro + ", codTipoVoceDiSpesaMicro = " + codTipoVoceDiSpesaMicro);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		VoceDiSpesaAssociataDTO voceDiSpesaPadre = null; // corrisponde a sottovoce
		VoceDiSpesaAssociataDTO voceDiSpesa = new VoceDiSpesaAssociataDTO();

		EsitoAssociazione esito = null;

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

		try {

			if (idSottovoce != null) {
				// ho scelto voce e sottovoce
				voceDiSpesa.setIdVoceDiSpesa(idSottovoce);
				voceDiSpesa.setIdVoceDiSpesaPadre(idVoceDiSpesa);
				voceDiSpesa.setDescVoceDiSpesa(descSottovoce);
				voceDiSpesa.setIdVoceDiSpesaMonit(idVoceDiSpesaMonit);
				voceDiSpesa.setCodTipoVoceDiSpesa(codTipoVoceDiSpesaMicro);
				voceDiSpesa.setFlagSpeseGestione(flagSpeseGestioneMicro);

				voceDiSpesaPadre = new VoceDiSpesaAssociataDTO();
				voceDiSpesaPadre.setIdVoceDiSpesa(idVoceDiSpesa);
				voceDiSpesaPadre.setIdVoceDiSpesaPadre(null);
				voceDiSpesaPadre.setIdVoceDiSpesaMonit(idVoceDiSpesaMonit);
				voceDiSpesaPadre.setDescVoceDiSpesa(descVoceDiSpesa);
				voceDiSpesaPadre.setCodTipoVoceDiSpesa(codTipoVoceDiSpesaMacro);
				voceDiSpesaPadre.setFlagSpeseGestione(flagSpeseGestioneMacro);
			} else {
				// ho scelto solo la voce padre
				voceDiSpesa.setIdVoceDiSpesa(idVoceDiSpesa);
				voceDiSpesa.setIdVoceDiSpesaPadre(null);
				voceDiSpesa.setDescVoceDiSpesa(descVoceDiSpesa);
				voceDiSpesa.setIdVoceDiSpesaMonit(idVoceDiSpesaMonit);
				voceDiSpesa.setCodTipoVoceDiSpesa(codTipoVoceDiSpesaMacro);
				voceDiSpesa.setFlagSpeseGestione(flagSpeseGestioneMacro);
			}

			esito = gestioneBackofficeSrv.associaVoceDiSpesa(idU, userInfo.getIdIride(), idBando, voceDiSpesa,
					voceDiSpesaPadre);

			if (esito == null) {
				resp.setCode("ERROR");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_ERR_OPERAZIONE_NON_COMPL);
			} else if (!esito.getEsito()) {
				resp.setCode("ERROR");
				resp.setMessage(esito.getMessage());
			} else {
				resp.setCode("OK");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_OPERAZIONE_ESEGUITA);
			}

		} catch (FormalParameterException ef) {
			resp.setCode("ERROR");
			resp.setMessage("Errore nel tipo di parametri passati: " + ef.getMessage());
		} catch (Exception e) {
			resp.setCode("ERROR");
			resp.setMessage("Errore generico: " + e.getMessage());
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();
	}

	@Override
	public Response vociDiSpesaEliminaVoceAssociata(Long idU, Long idBando, Long idVoceDiSpesa, HttpServletRequest req)
			throws Exception {

		String prf = "[ConfigurazioneBandoServiceImpl::eliminaLineaDiIntervento]";
		LOG.debug(prf + " BEGIN");

//			LOG.debug("Parametri in input -> idU = "+idU+", idBando = "+idBando+", idVoceDiSpesa = "+idVoceDiSpesa);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		ResponseCodeMessage resp = new ResponseCodeMessage();
		GestioneBackOfficeEsitoGenerico esito = null;

		try {

			if (idVoceDiSpesa == null || idVoceDiSpesa < 0) {
				resp.setCode("NO_SELECTED");
				resp.setMessage(it.csi.pbandi.pbservizit.business.intf.MessageKey.KEY_SELEZIONARE_ALMENO_UN_ELEMENTO);
			} else {
				esito = gestioneBackofficeSrv.eliminaVoceDiSpesaAssociata(idU, userInfo.getIdIride(), idBando,
						idVoceDiSpesa);

				if (esito == null || !esito.getEsito()) {
					resp.setCode("ERRORE");
					resp.setMessage(esito.getMessage());
				} else {
					resp.setCode("OK");
					resp.setMessage(esito.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(" Errore: " + e.getMessage());
			resp.setCode("Errore in eliminaVoceDiSpesaAssociata");
			resp.setMessage("Errore: " + e.getStackTrace());
			return Response.ok().entity(resp).build();

		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	private boolean validateFindBandi(String titoloBando, String nomeBandoLinea, Long idLineaDiIntervento)
			throws Exception {

		return ((titoloBando == null || titoloBando.equals("")) && (nomeBandoLinea == null || nomeBandoLinea.equals(""))
				&& (idLineaDiIntervento == null || idLineaDiIntervento == 0));
	}

	// Restituisce il tipo di programmazione (pre-2016, 2016) del bando selezionato
	// a video.
	private String getTipoProgrammazione(Long idLineaDiInterventoBando) {

		LOG.debug("getTipoProgrammazione(): idLineaDiInterventoBando = " + idLineaDiInterventoBando);

		String tipoProgrammazione = null;
		if (idLineaDiInterventoBando == null || idLineaDiInterventoBando == 0)
			tipoProgrammazione = Constants.PROGRAMMAZIONE_PRE_2016;
		else {
			tipoProgrammazione = Constants.PROGRAMMAZIONE_2016;

		}
		LOG.debug("getTipoProgrammazione(): tipoProgrammazione = " + tipoProgrammazione);
		return tipoProgrammazione;
	}

	@Override
	public Response getModalitaAgevErogazione(HttpServletRequest req) throws Exception {
		// TODO Auto-generated method stub
		return Response.ok(configurazioneBandoDAO.getModalitaAgevErogazone()).build();
	}

}
