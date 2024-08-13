/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BandoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.BeneficiarioProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoOperazioneAssociaProgettiAIstruttore;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.EsitoOperazioneDisassociaProgettiAIstruttore;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.IstruttoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.business.service.AssociazioneIstruttoreProgettoService;
import it.csi.pbandi.pbwebbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbwebbo.integration.dao.AssociazioneIstruttoreProgettoDAO;
import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaAssociatiAIstruttoreVO;
import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaDaAssociareAIstruttoreVO;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class AssociazioneIstruttoreProgettoServiceImpl implements AssociazioneIstruttoreProgettoService {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private GestioneBackofficeBusinessImpl gestioneBackofficeBusinessImpl;

	@Autowired
	private AssociazioneIstruttoreProgettoDAO associazioneIstruttoreProgettoDAO;

	@Override
	public Response cercabandi(Long idU, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::findBandi]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		BandoDTO[] bandiDTO = gestioneBackofficeBusinessImpl.findBandi(idU, userInfo.getIdIride());

		List<CodiceDescrizioneDTO> bandi = new ArrayList<CodiceDescrizioneDTO>();
		for (BandoDTO dto : bandiDTO) {
			CodiceDescrizioneDTO bando = new CodiceDescrizioneDTO();
			bando.setCodice("" + dto.getIdBando());
			/*
			 * Poiche' le descrizioni sono troppo lunghe le tronco a 70 caratteri
			 */
			String titoloBando = dto.getTitoloBando();
			if (titoloBando != null && titoloBando.length() > 70) {
				bando.setDescrizione(titoloBando.substring(0, 70).concat(" ..."));
			} else {
				bando.setDescrizione(titoloBando);
			}
			bandi.add(bando);
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(bandi).build();

	}

	@Override
	public Response cercaIstruttore(Long idU, Long idSoggetto, String nome, String cognome, String Codicefiscale,
			Long idBando, String tipoAnagrafica, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::cercaIstruttore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto + ", nome =" + nome
				+ ", cognome = " + cognome + ", codiceFiscale = " + Codicefiscale + ", idBando = " + idBando);

		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		IstruttoreDTO[] istruttoriDTO = null;
		IstruttoreDTO filtro = new IstruttoreDTO();
		filtro.setNome(nome);
		filtro.setCognome(cognome);
		filtro.setCodiceFiscale(Codicefiscale);
		filtro.setIdBando(idBando);
		filtro.setDescBreveTipoAnagrafica(tipoAnagrafica);

		try {
			istruttoriDTO = gestioneBackofficeBusinessImpl.findIstruttoriSemplici(idU, userInfo.getIdIride(),
					idSoggetto, filtro);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(istruttoriDTO).build();

	}

	@Override
	public Response gestisciAssociazioni(Long idU, Long idSoggettoIstruttoreMaster, Boolean isIstruttoreAffidamenti, HttpServletRequest req)
			throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::gestisciAssociazioni]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "Parametri in input -> idU = " + idU + ", idSoggettoIstruttoreMaster ="
				+ idSoggettoIstruttoreMaster + ", isIstruttoreAffidamenti ="
				+ isIstruttoreAffidamenti);

		ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);

		try {

			progettoDTO = gestioneBackofficeBusinessImpl.findDettaglioProgettiAssociatiAIstruttore(idU,
					userInfo.getIdIride(), idSoggettoIstruttoreMaster, isIstruttoreAffidamenti);

			for (int i = 0; i < progettoDTO.length; i++) {

				IstruttoreDTO[] istruttori = gestioneBackofficeBusinessImpl.findIstruttoriSempliciAssociatiProgetto(idU,
						userInfo.getIdIride(), idSoggettoIstruttoreMaster, null, progettoDTO[i].getIdProgetto(), isIstruttoreAffidamenti);
				/*
				 * Dagli istruttori associati devo escludere l' istruttore semplice selezionato.
				 * START
				 */
				IstruttoreDTO[] istruttoriTemp = new IstruttoreDTO[istruttori.length - 1];
				int j = 0;
				for (int n = 0; n < istruttori.length; n++) {

					if (istruttori[n].getIdSoggetto().equals(idSoggettoIstruttoreMaster)) {
						LOG.debug(
								"Istruttori associato uguale all' istruttore semplice selezionato: eliminato dalla lista");
					} else {
						istruttoriTemp[j] = istruttori[n];
						j++;
					}

				}
				/*
				 * Dagli istruttori associati devo escludere l' istruttore semplice selezionato.
				 * END
				 */

				progettoDTO[i].setIstruttoriSempliciAssociati(istruttoriTemp);

			}

		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.info(prf + " END");

		return Response.ok().entity(progettoDTO).build();

	}

	@Override
	public Response disassociaProgettiAIstruttore(Long idU, Long idSoggetto, String codRuolo,
			String progrSoggettoProgetto, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::disassociaProgettiAIstruttore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto =" + idSoggetto + ", codRuolo = "
				+ codRuolo + ", progrSoggettoProgetto = " + progrSoggettoProgetto);

		ResponseCodeMessage resp = new ResponseCodeMessage();
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			String[] progressiviProgettoSoggetto = { progrSoggettoProgetto };
			EsitoOperazioneDisassociaProgettiAIstruttore esito = gestioneBackofficeBusinessImpl
					.disassociaProgettiAIstruttore(idU, userInfo.getIdIride(), progressiviProgettoSoggetto, idSoggetto,
							codRuolo);

			if (esito != null) {

				if (esito.getEsito()) {
					resp.setCode("OK - M.N036");
					resp.setMessage("Operazione disassociaProgettiAIstruttore eseguita correttamente");
				} else if (!esito.getEsito()) {
					resp.setCode("KO - E.W034");
					resp.setMessage("Disassocia progetti a istruttore non eseguita");
				}

			}

		} catch (Exception e) {
			LOG.error(prf + e);
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(resp).build();

	}

	@Override
	public Response cercaProgettiByBandoLinea(Long idU, Long idBandoLinea, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::cercaProgettiByBando]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBandoLinea =" + idBandoLinea);

		ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			progettoDTO = gestioneBackofficeBusinessImpl.findProgettiByBandoLinea(idU, userInfo.getIdIride(),
					idBandoLinea);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(progettoDTO).build();

	}

	@Override
	public Response cercaBeneficiari(Long idU, Long idBandoLinea, Long idProgetto, HttpServletRequest req)
			throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::cercaBeneficiari]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBandoLinea =" + idBandoLinea + ", idProgetto = "
				+ idProgetto);

		BeneficiarioProgettoDTO[] beneficiarioProgettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			beneficiarioProgettoDTO = gestioneBackofficeBusinessImpl.findBeneficiari(idU, userInfo.getIdIride(),
					idBandoLinea, idProgetto);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(beneficiarioProgettoDTO).build();

	}

	@Override
	public Response cercaProgettiByBeneficiario(Long idU, Long idBandoLinea, Long idSoggettoBeneficiario,
			HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::cercaProgettiByBeneficiario]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBandoLinea =" + idBandoLinea
				+ ", idSoggettoBeneficiario = " + idSoggettoBeneficiario);

		ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			progettoDTO = gestioneBackofficeBusinessImpl.findProgettiBeneficiarioBando(idU, userInfo.getIdIride(),
					idBandoLinea, idSoggettoBeneficiario);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(progettoDTO).build();

	}

	@Override
	public Response findProgettiDaAssociare(Long idU, Long idBandoLinea, Long idProgetto, Long idSoggettoBeneficiario,
			Long idSoggetto, boolean isIstruttoriAssociati, Boolean isIstruttoreAffidamenti, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::findProgettiDaAssociare]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idBandoLinea = " + idBandoLinea + ", idProgetto ="
				+ idProgetto + ", idSoggettoBeneficiario = " + idSoggettoBeneficiario + ", " + "idSoggetto = "
				+ idSoggetto + ", isIstruttoriAssociati = " + isIstruttoriAssociati);

		ProgettoDTO[] progettoDTO = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			progettoDTO = gestioneBackofficeBusinessImpl.findProgettiDaAssociare(idU, userInfo.getIdIride(), idBandoLinea,
					idProgetto, idSoggettoBeneficiario, idSoggetto, isIstruttoriAssociati, isIstruttoreAffidamenti);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(progettoDTO).build();

	}

	@Override
	public Response associaProgettiAIstruttore(Long idU, String idProgetto, Long idSoggettoIstruttore, Long idSoggetto,
			String codRuolo, Boolean isIstruttoreAffidamenti, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::associaProgettiAIstruttore]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(
				prf + "Parametri in input -> idU = " + idU + ", idProgetto = " + idProgetto + ", idSoggettoIstruttore ="
						+ idSoggettoIstruttore + ", " + "idSoggetto = " + idSoggetto + ", codRuolo = " + codRuolo);

		EsitoOperazioneAssociaProgettiAIstruttore esito = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			esito = gestioneBackofficeBusinessImpl.associaProgettiAIstruttore(idU, userInfo.getIdIride(),
					new String[] { idProgetto }, idSoggettoIstruttore, idSoggetto, codRuolo, isIstruttoreAffidamenti);
		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(esito).build();

	}

	@Override
	public Response findBandolinaGiaAssociatiAIstruttore(Long idU, Long idSoggettoIstruttore, Boolean isIstruttoreAffidamenti, HttpServletRequest req)
			throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::findBandolinaGiaAssociatiAIstruttore]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "Parametri in input -> idU = " + idU + ", idSoggettoIstruttore = " + idSoggettoIstruttore+ ", isIstruttoreAffidamenti = " + isIstruttoreAffidamenti);

		List<BandoLineaAssociatiAIstruttoreVO> listBandoLinea;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.info(prf + "userInfo = " + userInfo);

		try {

			listBandoLinea = associazioneIstruttoreProgettoDAO.findBandoLineaIstruttore(idSoggettoIstruttore, isIstruttoreAffidamenti);

			for (int i = 0; i < listBandoLinea.size(); i++) {
				BandoLineaAssociatiAIstruttoreVO vo = associazioneIstruttoreProgettoDAO
						.conteggioIstruttoriAssociatiBandoLinea(idSoggettoIstruttore,
								listBandoLinea.get(i).getProgBandoLina(),isIstruttoreAffidamenti);
				listBandoLinea.get(i).setNumIstruttoriAssociati(vo.getNumIstruttoriAssociati());
			}

		} catch (Exception e) {
			LOG.error(prf + e);
			ResponseCodeMessage resp = new ResponseCodeMessage();
			resp.setCode("ERRORE");
			resp.setMessage("Errore: " + e);
			return Response.ok().entity(resp).build();
		}

		LOG.info(prf + " END");

		return Response.ok().entity(listBandoLinea).build();

	}

	@Override
	public Response dettaglioIstruttori(Long idU, Long idSoggettoIstruttore, Long progBandoLina, HttpServletRequest req)
			throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::dettaglioIstruttori]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggettoIstruttore = " + idSoggettoIstruttore
				+ ", progBandoLina = " + progBandoLina);

		List<BandoLineaAssociatiAIstruttoreVO> listDettaglioIstruttori;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			listDettaglioIstruttori = associazioneIstruttoreProgettoDAO.dettaglioIstruttore(idSoggettoIstruttore,
					progBandoLina);
		} catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(listDettaglioIstruttori).build();

	}

	@Override
	public Response getBandoLineaDaAssociareAdIstruttore(Long idU, Long idSoggetto, Long idSoggettoIstruttore,
			String descBreveTipoAnagrafica, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::dettaglioIstruttori]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", idSoggetto = " + idSoggetto
				+ ", idSoggettoIstruttore = " + idSoggettoIstruttore + ", descBreveTipoAnagrafica = "
				+ descBreveTipoAnagrafica);

		List<BandoLineaDaAssociareAIstruttoreVO> listBandoLineaDaAssociareAIstruttoreMaster = null;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {
			listBandoLineaDaAssociareAIstruttoreMaster = associazioneIstruttoreProgettoDAO
					.getBandoLineaDaAssociareAIstruttoreMaster(idSoggetto, descBreveTipoAnagrafica,
							idSoggettoIstruttore);
			LOG.debug(prf + "BandoLinea trovati = " + listBandoLineaDaAssociareAIstruttoreMaster.size());
			LOG.debug(prf + "Valori dei BandoLinea trovati:");
			for (BandoLineaDaAssociareAIstruttoreVO vo : listBandoLineaDaAssociareAIstruttoreMaster) {
				LOG.debug(prf + " " + vo.toString());
			}

		} catch (NullPointerException en) {
			LOG.error(prf + en);
			LOG.debug(prf + "Non sono stati trovati Enti di competenza trovati");
		} catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(listBandoLineaDaAssociareAIstruttoreMaster).build();

	}

	@Override
	public Response associaIstruttoreBandolinea(Long idU, Long progBandoLinaIntervento, Long idSoggettoIstruttore,
			String ruolo, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::associaIstruttoreBandolinea]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", progBandoLinaIntervento = " + progBandoLinaIntervento
				+ ", idSoggettoIstruttore = " + idSoggettoIstruttore + ", ruolo = " + ruolo);

		int result = -1;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			int idTipoAnagrafica = 0;
			if (ruolo != null && ruolo.equalsIgnoreCase("ADG-IST-MASTER")) {
				idTipoAnagrafica = 3;
			} else if (ruolo != null && ruolo.equalsIgnoreCase("OI-IST-MASTER")) {
				idTipoAnagrafica = 5;
			} else if (ruolo != null && ruolo.equalsIgnoreCase("ISTR-AFFIDAMENTI")) {
				idTipoAnagrafica = 25;
			}

			LOG.debug(prf + "Parametri calcolato -> idTipoAnagrafica = " + idTipoAnagrafica);

			result = associazioneIstruttoreProgettoDAO.associaIstruttoreBandoLinea(idU, idSoggettoIstruttore,
					progBandoLinaIntervento, idTipoAnagrafica);
		} catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(result).build();

	}

	@Override
	public Response eliminaAssocizioneIstruttoreBandoLinea(Long idU, Long idSoggettoIstruttore,
			Long progBandoLineaIntervento, HttpServletRequest req) throws Exception {

		String prf = "[AssociazioneIstruttoreProgettoServiceImpl::eliminaAssocizioneIstruttoreBandoLinea]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "Parametri in input -> idU = " + idU + ", progBandoLineaIntervento = "
				+ progBandoLineaIntervento + ", idSoggettoIstruttore = " + idSoggettoIstruttore);

		int result = -1;
		UserInfoSec userInfo = null;
		HttpSession session = req.getSession();
		userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
		LOG.debug(prf + "userInfo = " + userInfo);

		try {

			result = associazioneIstruttoreProgettoDAO.eliminaAssocizioneIstruttoreBandoLinea(idU, idSoggettoIstruttore,
					progBandoLineaIntervento);

		} catch (Exception e) {
			LOG.error(prf + e);
			throw e;
		}

		LOG.debug(prf + " END");

		return Response.ok().entity(result).build();

	}

}
