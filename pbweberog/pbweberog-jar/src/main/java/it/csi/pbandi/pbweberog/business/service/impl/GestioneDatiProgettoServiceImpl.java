/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.impl.UserApiServiceImpl;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.ComuneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaAbilitazVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.MailDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.NumberUtil;
import it.csi.pbandi.pbweberog.business.MailUtil;
import it.csi.pbandi.pbweberog.business.SecurityHelper;
import it.csi.pbandi.pbweberog.business.service.GestioneDatiProgettoService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.datiprogetto.Comune;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DatiProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioDatiProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoBeneficiarioDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DettaglioSoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.DimensioneTerritorialeDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.EsitoScaricaDocumentoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.FileDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.Recapiti;
import it.csi.pbandi.pbweberog.dto.datiprogetto.RecapitoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgetto;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SedeProgettoDTO;
import it.csi.pbandi.pbweberog.dto.datiprogetto.SoggettoProgettoDTO;
import it.csi.pbandi.pbweberog.integration.dao.GestioneDatiProgettoDAO;
import it.csi.pbandi.pbweberog.integration.request.DatiSifRequest;
import it.csi.pbandi.pbweberog.integration.request.InserisciSedeInterventoRequest;
import it.csi.pbandi.pbweberog.integration.request.ModificaSedeInterventoRequest;
import it.csi.pbandi.pbweberog.integration.request.RequestCambiaStatoSoggettoProgetto;
import it.csi.pbandi.pbweberog.integration.request.RequestDissociateFile;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaCup;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaRecapito;
import it.csi.pbandi.pbweberog.integration.request.RequestSalvaTitoloProgetto;
import it.csi.pbandi.pbweberog.integration.request.SalvaDatiProgettoRequest;
import it.csi.pbandi.pbweberog.pbandisrv.dto.DatiAggiuntiviDTO;
import it.csi.pbandi.pbweberog.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.ConstantsMap;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.StringUtil;
import it.csi.pbandi.pbweberog.util.ValidatorCodiceFiscale;

@Service
public class GestioneDatiProgettoServiceImpl implements GestioneDatiProgettoService {

	@Autowired
	protected BeanUtil beanUtil;
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	GestioneDatiProgettoDAO gestioneDatiProgettoDAO;

	@Autowired
	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;

	@Autowired
	GestioneDatiDiDominioBusinessImpl datiDiDominioBusinessImpl;
	
	@Autowired
	UserApiServiceImpl userApiServiceImpl;

	@Autowired
	ArchivioBusinessImpl archivioBusinessImpl;

	@Autowired
	private SecurityHelper springSecurityHelper;

	@Autowired
	MailDAO maildao;

	@Autowired
	GenericDAO genericDAO;

	@Override
	public Response isLineaPORFESR1420(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::isLineaPORFESR1420]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			Boolean is1420 = false;
			DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto,
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			if (new BigDecimal(datiGenerali.getIdLineaDiIntervento())
					.compareTo(Constants.ID_LINEA_DI_INTERVENTO_POR_FESR_14_20) == 0) {
				is1420 = true;
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(is1420).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDatiProgetto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDatiProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idSoggettoBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();

			DatiProgettoDTO datiProgettoDTO = gestioneDatiProgettoDAO.findDatiProgetto(idUtente, idIride, idProgetto,
					idSoggettoBeneficiario);
			DatiProgetto datiProgetto = beanUtil.transform(datiProgettoDTO, DatiProgetto.class);
			datiProgetto.setDettaglio(beanUtil.transform(datiProgettoDTO.getDettaglio(), DettaglioDatiProgetto.class));
			datiProgetto.setIdProgetto(idProgetto);
			LOG.debug(prf + " END");
			return Response.ok().entity(datiProgetto).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaDatiProgetto(HttpServletRequest req, SalvaDatiProgettoRequest salvaRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaDatiProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			DatiProgettoDTO dto = new DatiProgettoDTO();
			beanUtil.valueCopy(salvaRequest.getDatiProgetto(), dto);
			if (salvaRequest.getDatiProgetto().getIdProgetto() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{SalvaDatiProgettoRequest.DatiProgetto.idProgetto}");
			if (salvaRequest.getDatiProgetto().getIdSedeIntervento() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{SalvaDatiProgettoRequest.DatiProgetto.idSedeIntervento}");
			if (salvaRequest.getDatiProgetto().getProgrSoggettoProgetto() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{SalvaDatiProgettoRequest.DatiProgetto.progrSoggettoProgetto}");
			if (salvaRequest.getDatiProgetto().getIdDomanda() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{SalvaDatiProgettoRequest.DatiProgetto.idDomanda}");
			Boolean isLineaBANDIREGP = userApiServiceImpl.isLineaByIdProgettoDescBreveLinea(salvaRequest.getDatiProgetto().getIdProgetto(),
					Constants.DESC_BREVE_LINEA_BANDIREGP, idUtente, idIride,
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			EsitoOperazioni esito = gestioneDatiProgettoDAO.salvaDatiProgetto(idUtente, idIride, dto, isLineaBANDIREGP);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response salvaDatiSif(@Context HttpServletRequest req, Long idProgetto, DatiSifRequest request)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaDatiSif]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			if (idProgetto == null)
				return inviaErroreBadRequest(
						"idProgetto non valorizzato");
			if (request.getDtFirmaAccordo() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{DatiSifRequest.dtFirmaAccordo}");
			if (request.getDtCompletamentoValutazione() == null)
				return inviaErroreBadRequest(
						"parametro mancato nel body: ?{DatiSifRequest.dtCompletamentoValutazione}");
			EsitoOperazioni esito = gestioneDatiProgettoDAO.salvaDatiSif(idUtente, idIride, idProgetto, request);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getFilesAssociatedProgetto(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getFilesAssociatedProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			FileDTO[] files = gestioneDatiProgettoDAO.getFilesAssociatedProgetto(idUtente, idIride, idProgetto);
//			  ArrayList<DocumentoAllegato> docs = new ArrayList<DocumentoAllegato>();
//			  for (FileDTO f: files) {
//					DocumentoAllegato temp = new DocumentoAllegato();
//					temp.setId(f.getIdEntita());
//					temp.setIdParent(new Long(i * 10));
//					temp.setIsDisassociabile(f.getIsLocked());
//					temp.setNome(f.getNomeFile());
//					docs.add(temp);
//				}
			LOG.debug(prf + " END");
			return Response.ok().entity(files).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDocumento(HttpServletRequest req, Long idDocumentoIndex)
			throws UtenteException, FileNotFoundException, IOException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDocumento]";
		LOG.debug(prf + " BEGIN");
		EsitoScaricaDocumentoDTO esito = new EsitoScaricaDocumentoDTO();
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			esito = gestioneDatiProgettoDAO.findDocumento(idUtente, idIride, idDocumentoIndex);
			LOG.info(prf + " bytes: " + esito.getBytesDocumento());

			LOG.debug(prf + " END");
			return Response.ok(esito.getBytesDocumento()).build();

		} catch (Exception e) {
			LOG.error(prf + " Exception " + e.getMessage());
			throw e;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// SERVIZI PER SEDI PROGETTO
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getAllSediProgetto(HttpServletRequest req, Long idProgetto, String codiceRuolo)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getAllSediProgetto]";
		LOG.debug(prf + " BEGIN");
		LOG.debug(prf + " Parametri ingresso: idProgetto = " + idProgetto + ", codiceRuolo = " + codiceRuolo);
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idSoggettoBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
			SedeProgettoDTO[] sediDTO = gestioneDatiProgettoDAO.findAllSediProgetto(idUtente, idIride, idProgetto,
					idSoggettoBeneficiario);

			ArrayList<SedeProgetto> sedi = beanUtil.transformToArrayList(sediDTO, SedeProgetto.class);
			StringBuilder sbx = new StringBuilder();
			StringBuilder sby = new StringBuilder();
			StringBuilder sbIndirizzi = new StringBuilder();
			for (SedeProgetto sedeProgetto : sedi) {
				// inizializzazione
				sedeProgetto.setIsEliminabile(false);
				sedeProgetto.setIsModificabile(false);

				// logger.dumpError(sedeProgetto);
				String indirizzoComposto = composeAddress(sedeProgetto);
				sedeProgetto.setDescIndirizzoComposto(indirizzoComposto);

				if (!ObjectUtil.isEmpty(sedeProgetto.getCodIstatComune())) {
//					int idComuneTope = locindHelper.getIdComuneTope(sedeProgetto
//							.getCodIstatComune());
//					LOG.info("SEDE INTERVENTO sedeProgetto.getDescComune() "
//							+ sedeProgetto.getDescComune()
//							+ " , idComuneTope extracted by tope : " + idComuneTope);
//					Via[] vie = null;
//					try {
//						vie = locindHelper.getVie(sedeProgetto.getCodIstatComune(),
//								"");
//					} catch (Exception x) {
//						if (!(x instanceof TopeNoDataExtractedException)) {
//							isErrorToponomastica = true;
//						}
//						logger.error(x.getMessage());
//					}

//					if (!ObjectUtil.isEmpty(vie)) {
					String descVia = sedeProgetto.getDescIndirizzo();
					String indirizzo = "";
					if (descVia != null) {
						descVia = descVia.replaceAll(",", " ");
						StringTokenizer st = new StringTokenizer(descVia);
						String civicoParsed = "";
						while (st.hasMoreTokens()) {
							String token = st.nextToken();
							if (NumberUtil.isNumber(token)) {
								civicoParsed = token;
								break;
							} else {
								indirizzo += token + " ";
							}
						}
						descVia = indirizzo.trim();
//							int idViaL2 = getIdViaL2(vie, descVia);
//							if (idViaL2 > 0) {
//								Double civico = 1d;
//								if (sedeProgetto.getCivico() != null)
//									civico = sedeProgetto.getCivico().doubleValue();
//								else if (!ObjectUtil.isEmpty(civicoParsed)) {
//									civico = new Double(civicoParsed);
//								}
//
//								PuntoVO punto = locindHelper.getPunto(idComuneTope,
//										idViaL2, civico.intValue());
//								if (punto != null) {
//									logger.info("coord by locind / idVial2 --> x: "
//											+ punto.getCoordX() + " , y: "
//											+ punto.getCoordY());
//									sbx.append(
//											NumberUtil.getStringValue(punto
//													.getCoordX())).append(",");
//									sby.append(
//											NumberUtil.getStringValue(punto
//													.getCoordY())).append(",");
//									sbIndirizzi.append(indirizzoComposto + "$");
//								}
//							}
					}

				}
				DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride,
						idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
				String scenario = getScenarioCupGefo(datiGenerali.getIsGestitoGefo(), datiGenerali.getCup());

				if (springSecurityHelper.verifyCurrentUserForUC(null, "OPEPRO007")
						&& (scenario.equals(Constants.SCENARIO_CUP_NO_GEFO_NO))) {
					sedeProgetto.setIsEliminabile(true);// OPEPRO007}
				}

//				if (springSecurityHelper.verifyCurrentUserForUC(
//						null, "OPEPRO006")) {
//					sedeProgetto.setIsModificabile(true); // OPEPRO006}
//				}
				if (codiceRuolo != null && (codiceRuolo.equals("ADG-ISTRUTTORE") || codiceRuolo.equals("ADG-IST-MASTER")
						|| codiceRuolo.equals("OI-IST-MASTER"))) {
					sedeProgetto.setIsModificabile(true);
				}
			}

//			}
			LOG.debug(prf + " END");
			return Response.ok().entity(sedi).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private String getScenarioCupGefo(boolean isGestitoGEFO, String cup) {

		boolean isCupPresente = false;
		String scenario = new String();

		if (cup != null && cup.trim().length() > 0)
			isCupPresente = true;

		if (isGestitoGEFO) {
			if (isCupPresente)
				scenario = Constants.SCENARIO_CUP_SI_GEFO_SI;
			else
				scenario = Constants.SCENARIO_CUP_NO_GEFO_SI;
		} else {
			if (isCupPresente)
				scenario = Constants.SCENARIO_CUP_SI_GEFO_NO;
			else
				scenario = Constants.SCENARIO_CUP_NO_GEFO_NO;
		}

		return scenario;
	}

	@Override
	public Response getDettaglioSedeProgetto(HttpServletRequest req, Long idProgetto, Long idSede)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDettaglioSedeProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idSoggettoBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();

			SedeProgettoDTO sedeDTO = gestioneDatiProgettoDAO.findDettaglioSedeProgetto(idUtente, idIride, idProgetto,
					idSoggettoBeneficiario, idSede);
			LOG.debug(prf + " END");
			return Response.ok().entity(sedeDTO).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response modificaSedeIntervento(HttpServletRequest req, ModificaSedeInterventoRequest moRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::modificaSedeIntervento]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idSedeAttuale = moRequest.getIdSedeInterventoAttuale();
			SedeProgettoDTO sedeIntervento = moRequest.getSedeIntervento();

			EsitoOperazioni esito = gestioneDatiProgettoDAO.modificaSedeIntervento(idUtente, idIride, sedeIntervento,
					idSedeAttuale);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response inserisciSedeInterventoProgetto(HttpServletRequest req, InserisciSedeInterventoRequest inRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::inserisciSedeInterventoProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idSedeIntervento = inRequest.getIdSedeIntervento();
			SedeProgettoDTO sede = inRequest.getSedeIntervento();
			Long progrSoggettoProgetto = inRequest.getProgrSoggettoProgetto();

			EsitoOperazioni esito = gestioneDatiProgettoDAO.inserisciSedeInterventoProgetto(idUtente, idIride, sede,
					progrSoggettoProgetto, idSedeIntervento);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response cancellaSedeInterventoProgetto(HttpServletRequest req, Long progrSoggettoProgettoSede)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::cancellaSedeInterventoProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			EsitoOperazioni esito = gestioneDatiProgettoDAO.cancellaSedeIntervento(idUtente, idIride,
					progrSoggettoProgettoSede);

			// TO DO : elimare la sede dal map
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////// SOGGETTI
	//////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getSoggettiProgetto(HttpServletRequest req, Long idU, Long idProgetto, String codiceRuolo)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSoggettiProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			String idIride = userInfo.getIdIride();
			SoggettoProgettoDTO[] soggetti = gestioneDatiProgettoDAO.findSoggettiProgetto(idUtente, idIride,
					idProgetto);
			soggetti = eliminaSoggettoBeneficiario(soggetti);
			
			LOG.debug(prf + " idProgetto="+idProgetto);
			if(session!=null) {
				LOG.debug(prf + " ID PROGETTO LETTO DA SESSIONE = [" + session.getAttribute("idProgetto")+"]");
			}

			if (codiceRuolo.equals("ADG-ISTRUTTORE") || codiceRuolo.equals("ADG-IST-MASTER")
					|| codiceRuolo.equals("BEN-MASTER") || codiceRuolo.equals("PERSONA-FISICA")) {

				List<PbandiTRichiestaAbilitazVO> listRichiestaAbilitazione = gestioneDatiProgettoDAO
						.findRichiestaAbilitazVO();

				for (PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO : listRichiestaAbilitazione) {

					for (int i = 0; i < soggetti.length; i++) {
						try {
							if (new BigDecimal(soggetti[i].getProgrSoggettiCorrelati())
									.equals(pbandiTRichiestaAbilitazVO.getProgrSoggettiCorrelati())
									&& new BigDecimal(soggetti[i].getProgrSoggettoProgetto())
											.equals(pbandiTRichiestaAbilitazVO.getProgrSoggettoProgetto())) {
								soggetti[i].setInAttesaEsito(true);
								break;
							}
						} catch (Exception e) {
							LOG.warn(e.getMessage());
						}

					}

				}

			}

			for (int i = 0; i < soggetti.length; i++) {
				List<PbandiTUtenteVO> listPbandiTUtenteVO = null;
				listPbandiTUtenteVO = gestioneDatiProgettoDAO
						.findPbandiTUtenteVO(soggetti[i].getCodiceFiscaleSoggetto());
				if (listPbandiTUtenteVO != null && listPbandiTUtenteVO.size() > 0) {
					soggetti[i].setAbilitatoAccesso("SI");
				} else {
					soggetti[i].setAbilitatoAccesso("NO");
				}
			}

			// Inserisce true sul campo "rifiutata" per le richieste di accesso rifiutate
			List<PbandiTRichiestaAbilitazVO> listRichiestaAbilitazioneRifiutata = gestioneDatiProgettoDAO
					.findRichiestaAccessoRifiutata();

			for (PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO : listRichiestaAbilitazioneRifiutata) {

				for (int i = 0; i < soggetti.length; i++) {
					try {
						if (new BigDecimal(soggetti[i].getProgrSoggettiCorrelati())
								.equals(pbandiTRichiestaAbilitazVO.getProgrSoggettiCorrelati())
								&& new BigDecimal(soggetti[i].getProgrSoggettoProgetto())
										.equals(pbandiTRichiestaAbilitazVO.getProgrSoggettoProgetto())) {
							soggetti[i].setRifiutata(true);
							break;
						}
					} catch (Exception e) {
						LOG.warn(e.getMessage());
					}

				}

			}

			// Check data_cessazione valorizzata su PBANDI_R_SOGGETTO_PROGETTO
			for (int i = 0; i < soggetti.length; i++) {
				List<PbandiRSoggettoProgettoVO> listPbandiRSoggettoProgettoVO = new ArrayList<PbandiRSoggettoProgettoVO>();
				listPbandiRSoggettoProgettoVO = gestioneDatiProgettoDAO
						.findListRSoggettoProgetto(soggetti[i].getIdSoggetto(), idProgetto);
				if (listPbandiRSoggettoProgettoVO != null
						&& listPbandiRSoggettoProgettoVO.get(0).getDataCessazione() != null) {
					soggetti[i].setDisattivazioneDefinitiva(true);
				} else {
					soggetti[i].setDisattivazioneDefinitiva(false);
				}
			}

			LOG.debug(prf + " END");
			return Response.ok().entity(soggetti).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private SoggettoProgettoDTO[] eliminaSoggettoBeneficiario(SoggettoProgettoDTO[] soggetti) {
		ArrayList<SoggettoProgettoDTO> soggetti2 = new ArrayList<SoggettoProgettoDTO>();
		if (soggetti.length > 0) {
			for (int i = 0; i < soggetti.length; i++) {
				if (!soggetti[i].getIdTipoAnagrafica().equals(new Long(1))
						|| !soggetti[i].getIdTipoSoggetto().equals(new Long(2))) {
					soggetti2.add(soggetti[i]);
				}
			}
		}
		SoggettoProgettoDTO[] output = new SoggettoProgettoDTO[soggetti2.size()];
		output = soggetti2.toArray(output);
		return output;

		/*
		 * versione originale che dà errore se c'è 1 solo soggetto e non è il soggetto
		 * beneficiario. SoggettoProgettoDTO[] soggetti2 = null; if(soggetti.length > 0)
		 * { soggetti2 = new SoggettoProgettoDTO[soggetti.length - 1]; int n = 0;
		 * for(int i = 0; i < soggetti.length; i++) {
		 * if(!soggetti[i].getIdTipoAnagrafica().equals(new Long(1)) ||
		 * !soggetti[i].getIdTipoSoggetto().equals(new Long(2))) { soggetti2[n] =
		 * soggetti[i]; n++; } } } return soggetti2;
		 */

	}

	@Override
	public Response richiestaAccessoNegata(HttpServletRequest req, Long idU, Long progrSoggettoProgetto,
			Long progrSoggettiCorrelati) throws UtenteException, Exception {

		String prf = "[GestioneDatiProgettoServiceImpl::richiestaAccessoNegata]";
		LOG.debug(prf + " BEGIN");
		LOG.debug(prf + " Parametri ingresso: idU = " + idU + ", progrSoggettoProgetto = " + progrSoggettoProgetto
				+ ", progrSoggettiCorrelati = " + progrSoggettiCorrelati);

		int result = -1;

		try {

			RequestCambiaStatoSoggettoProgetto cambiaStatoRequest = new RequestCambiaStatoSoggettoProgetto();
			cambiaStatoRequest.setProgrSoggettoProgetto(progrSoggettoProgetto);
			cambiaStatoRequest.setProgrSoggettiCorrelati(progrSoggettiCorrelati);

			result = gestioneDatiProgettoDAO.updateRichiestaAbilitazione(idU, cambiaStatoRequest, "N");
			if (result > 0)
				LOG.info("Aggiornamento ESITO e DT_ESITO dell'Elemento con progrSoggettoProgetto = "
						+ cambiaStatoRequest.getProgrSoggettoProgetto() + " e progrSoggettiCorrelati= "
						+ cambiaStatoRequest.getProgrSoggettiCorrelati() + "ed ESITO = N avvenuta con successo");

		} catch (Exception e) {
			throw e;
		}

		return Response.ok().entity(result).build();
	}

	@Override
	public Response getDettaglioSoggettoProgetto(HttpServletRequest req, Long idU, Long progrSoggettoProgetto,
			Long idTipoSoggettoCorrelato, Long progrSoggettiCorrelati, String codiceFiscale)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDettaglioSoggettoProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			String idIride = userInfo.getIdIride();
			DettaglioSoggettoProgettoDTO dettaglio = gestioneDatiProgettoDAO.findDettaglioSoggettoProgetto(idUtente,
					idIride, progrSoggettoProgetto, idTipoSoggettoCorrelato, progrSoggettiCorrelati);

			// ------------------ PARTE NUOVA START

			List<PbandiTRichiestaAbilitazVO> checkAccessoSistema = gestioneDatiProgettoDAO
					.findCheckAccessoSistema(progrSoggettoProgetto, progrSoggettiCorrelati);
			if (checkAccessoSistema != null && checkAccessoSistema.size() > 0) {
				dettaglio.setAccessoSistema(checkAccessoSistema.get(0).getAccessoSistema().equals("S") ? true : false);
			} else {

				List<PbandiTUtenteVO> listPbandiTUtenteVO = gestioneDatiProgettoDAO
						.findUtenteVOFromCodiceUtente(codiceFiscale);
				if (listPbandiTUtenteVO == null || listPbandiTUtenteVO.size() == 0) {
					dettaglio.setAccessoSistema(false);
				} else {
					dettaglio.setAccessoSistema(true);
				}
			}

			// ------------------ PARTE NUOVA END
			LOG.debug(prf + " END");
			return Response.ok().entity(dettaglio).build();
		} catch (Exception e) {
			throw e;
		}
	}

//	@Override
//	public Response getDettaglioSoggettoBeneficiario(HttpServletRequest req, Long progrSoggettoProgetto)
//			throws UtenteException, Exception {
//		String prf = "[GestioneDatiProgettoServiceImpl::getDettaglioSoggettoProgetto]";
//		LOG.debug(prf + " BEGIN");
//		try {
//			HttpSession session = req.getSession();
//			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
//			Long idUtente = userInfo.getIdUtente();
//			String idIride = userInfo.getIdIride();
//			Long idSoggettoBen = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
//			
//			DettaglioSoggettoBeneficiarioDTO dettaglio  = gestioneDatiProgettoDAO
//					.findDettaglioSoggettoBeneficiario(idUtente, idIride, progrSoggettoProgetto, idSoggettoBen);
//
//			LOG.debug(prf + " END");
//			return Response.ok().entity(dettaglio).build();
//		} catch(Exception e) {
//			throw e;
//		}
//	}

	@Override
	public Response getDettaglioSoggettoBeneficiario(HttpServletRequest req, Long idU, Long idProgetto,
			String codiceRuolo) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDettaglioSoggettoBeneficiario]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " parametri input: idU = " + idU + ", idProgetto = " + idProgetto + ", codiceRuolo = "
				+ codiceRuolo);
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			String idIride = userInfo.getIdIride();
			SoggettoProgettoDTO[] soggetti = gestioneDatiProgettoDAO.findSoggettiProgetto(idUtente, idIride,
					idProgetto);

			SoggettoProgettoDTO soggettoBeneficiario = new SoggettoProgettoDTO();

			for (SoggettoProgettoDTO soggettoProgettoDTO : soggetti) {

				if (soggettoProgettoDTO.getIdTipoAnagrafica().equals(new Long(1))
						&& soggettoProgettoDTO.getIdTipoSoggetto().equals(new Long(2))) {
					soggettoBeneficiario = soggettoProgettoDTO;
					LOG.info(prf + " Trovato soggetto beneficiario. CF = "
							+ soggettoBeneficiario.getCodiceFiscaleSoggetto());
				}

			}

			Long idSoggettoBen = soggettoBeneficiario.getIdSoggetto().longValue();
			DettaglioSoggettoBeneficiarioDTO dettaglio = gestioneDatiProgettoDAO.findDettaglioSoggettoBeneficiario(
					idUtente, idIride, soggettoBeneficiario.getProgrSoggettoProgetto(), idSoggettoBen);

			LOG.info(prf + " END");
			return Response.ok().entity(dettaglio).build();
		} catch (Exception e) {
			LOG.error("Errore in GestioneDatiProgettoServiceImpl::getDettaglioSoggettoBeneficiario", e);
			throw e;
		}
	}

	@Override
	public Response getTipiSoggettiCorrelati(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTipiSoggettiCorrelati]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			Decodifica[] decodifica = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPO_SOGG_CORRELATO);
			ArrayList<CodiceDescrizione> ruoli = beanUtil.transformToArrayList(decodifica, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(ruoli).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getSedeLegale(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSedeLegale]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto,
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());

			String sedeLegale = datiGenerali.getSedeLegale();

			datiGenerali.getSedeLegale();
//			String sedeLegale = viaSedeLegale.replaceAll(",", " ");
//			StringTokenizer st = new StringTokenizer(sedeLegale);
//			String descVia = "";
//			String civicoParsed = "";
//			while (st.hasMoreTokens()) {
//				String token = st.nextToken();
//				
//				if (NumberUtil.isNumber(token)) {
//					civicoParsed = token;
//					break;
//				} else {
//					descVia += token + " ";
//				}
//			}
//			LOG.info(prf + " descVia " + descVia + " , civicoParsed "
//					+ civicoParsed);
//			descVia = descVia.trim();
			LOG.debug(prf + " END");
			return Response.ok().entity(sedeLegale).build();
		} catch (Exception e) {
			throw e;
		}
	}

	private String composeAddress(SedeProgetto sedeProgetto) {
		String indirizzoComposto = "";
		if (!ObjectUtil.isEmpty(sedeProgetto.getDescIndirizzo()))
			indirizzoComposto += sedeProgetto.getDescIndirizzo();
		if (!ObjectUtil.isNull(sedeProgetto.getCivico()))
			indirizzoComposto += " " + sedeProgetto.getCivico().longValue();
		if (!ObjectUtil.isEmpty(indirizzoComposto))
			indirizzoComposto += ", ";
		if (!ObjectUtil.isEmpty(sedeProgetto.getDescComune()))
			indirizzoComposto += sedeProgetto.getDescComune();
		if (!ObjectUtil.isEmpty(sedeProgetto.getDescProvincia()))
			indirizzoComposto += ", (" + sedeProgetto.getDescProvincia() + ")";
		return indirizzoComposto;
	}

	@Override
	public Response cambiaStatoSoggettoProgetto(HttpServletRequest req, Long idU,
			RequestCambiaStatoSoggettoProgetto cambiaStatoRequest) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::cambiaStatoSoggettoProgetto]";
		LOG.debug(prf + " BEGIN");

		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long progrSoggettoProgetto = cambiaStatoRequest.getProgrSoggettoProgetto();
			Long progrSoggettiCorrelati = cambiaStatoRequest.getProgrSoggettiCorrelati();

			Boolean esito = gestioneDatiProgettoDAO.cambiaStatoSoggettoProgetto(idUtente, idIride,
					progrSoggettoProgetto, progrSoggettiCorrelati);

			LOG.info(prf + "cambiaStatoRequest=" + cambiaStatoRequest);
			/*
			 * RequestCambiaStatoSoggettoProgetto [progrSoggettoProgetto=92082,
			 * progrSoggettiCorrelati=77290, codiceFiscale=RBNRRT68P49D205P, idUtente=27345,
			 * idSoggetto=2130090, codideRuolo=ADG-IST-MASTER, ruoloNuovoUtente=6,
			 * utenteAbilitatoProgetto=OFF, idProgetto=16316]>
			 * 
			 */
			// PK invio email
			RecapitoDTO rec = gestioneDatiProgettoDAO.findRecapito(idUtente, idIride,
					cambiaStatoRequest.getIdProgetto());
			LOG.info(prf + "recapito=" + rec);
			if (rec != null && rec.getEmail() != null && !rec.getEmail().isEmpty()) {
				LOG.info(
						prf + "recapito email presente, invio la comunicazione all'indirizzo [" + rec.getEmail() + "]");

				Long idSoggettoBeneficiario = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
				LOG.info(prf + "idSoggettoBeneficiario=" + idSoggettoBeneficiario);

				DatiProgettoDTO datiProgettoDTO = gestioneDatiProgettoDAO.findDatiProgetto(idUtente, idIride,
						cambiaStatoRequest.getIdProgetto(), idSoggettoBeneficiario);

				if (datiProgettoDTO != null) {
					LOG.info(prf + "datiProgettoDTO.getTitoloProgetto()=" + datiProgettoDTO.getTitoloProgetto());
				} else {
					LOG.info(prf + "datiProgettoDTO nullo");
				}

				inviaEMail(rec.getEmail(), cambiaStatoRequest.getIdProgetto(), datiProgettoDTO.getTitoloProgetto(),
						cambiaStatoRequest.getUtenteAbilitatoProgetto(), cambiaStatoRequest.getCodiceFiscale());

			} else {
				LOG.info(prf + "recapito email nullo, NON invio la comunicazione via email");
			}
			// PK fine invio email

			if ((cambiaStatoRequest.getCodideRuolo().equals("ADG-ISTRUTTORE")
					|| cambiaStatoRequest.getCodideRuolo().equals("ADG-IST-MASTER"))
					&& (cambiaStatoRequest.getRuoloNuovoUtente() == 1 || cambiaStatoRequest.getRuoloNuovoUtente() == 21)
					&& cambiaStatoRequest.getUtenteAbilitatoProgetto().equals("ON")) {

				PbandiTRichiestaAbilitazVO pbandiTRichiestaAbilitazVO = gestioneDatiProgettoDAO
						.findRichiestaAbilitazVO(cambiaStatoRequest);

				if (pbandiTRichiestaAbilitazVO != null) {
					LOG.info(prf + "Elemento con progrSoggettoProgetto = "
							+ cambiaStatoRequest.getProgrSoggettoProgetto() + " e progrSoggettiCorrelati= "
							+ cambiaStatoRequest.getProgrSoggettiCorrelati() + ""
							+ "trovato sulla tabella PBANDI_T_RICHIESTA_ABILITAZ in attesa di esito");

					int result = gestioneDatiProgettoDAO.updateRichiestaAbilitazione(idU, cambiaStatoRequest, "S");
					if (result > 0)
						LOG.info(prf + "Aggiornamento ESITO e DT_ESITO dell'Elemento con progrSoggettoProgetto = "
								+ cambiaStatoRequest.getProgrSoggettoProgetto() + " e progrSoggettiCorrelati= "
								+ cambiaStatoRequest.getProgrSoggettiCorrelati());

					if (pbandiTRichiestaAbilitazVO.getAccessoSistema().equals("S")) {
						gestioneDatiProgettoDAO.insertPbandiTUtenteVO(idUtente, cambiaStatoRequest);
					}
				}
			}
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response disabilitaSoggettoPermanentemente(HttpServletRequest req, Long idSoggetto, Long idProgetto)
			throws UtenteException, Exception {

		String prf = "[GestioneDatiProgettoServiceImpl::disabilitaSoggettoPermanentemente]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + " Parametri in input: idSoggetto = " + idSoggetto + ", idProgetto = " + idProgetto);

		int result = -1;
		result = gestioneDatiProgettoDAO.updateDataCessazione(idSoggetto, idProgetto);

		LOG.debug(prf + " END");
		return Response.ok().entity(result).build();
	}

	private void inviaEMail(String email, Long idProgetto, String titoloProgetto, String utenteAbilitatoProgetto,
			String codFiscale) throws Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::inviaEMail]";
		LOG.debug(prf + " BEGIN");

		String mittente = leggiCostante(Constants.COSTANTE_INDIRIZZO_EMAIL_MITTENTE);

		String oggi = DateUtil.getData();
		String userAbilitato = "abilitato";
		if ("OFF".equals(utenteAbilitatoProgetto)) {
			userAbilitato = "disabilitato";
		}

		String oggetto = "Modifica delega utente progetto [" + idProgetto + "]";
		String testo = "Con la presente si notifica che in data " + oggi + " e' stato " + userAbilitato + " l'utente '"
				+ codFiscale + "' sul progetto '" + titoloProgetto + "'";

//		String testo = "Con la presente si notifica che in data "+oggi+" il beneficiario "+paramInviaInVerificaDTO.getBeneficiario()+
//				" ha inviato in verifica l'affidamento con oggetto '"+appalto.getOggettoAppalto()+
//				"' per il progetto '"+paramInviaInVerificaDTO.getTitoloProgetto()+"' bando '"+paramInviaInVerificaDTO.getNomeBandoLinea()+
//				"'.\n\nI dettagli dell'affidamento sono consultabili sul Gestionale Finanziamenti \nall'indirizzo http://www.sistemapiemonte.it/finanziamenti/bandi/";

		List<String> separatedMailAddresses = new ArrayList<String>();
		separatedMailAddresses.add(email);

		MailVO mailVO = new MailVO();
		mailVO.setToAddresses(separatedMailAddresses);
		mailVO.setFromAddress(mittente);
		mailVO.setSubject(oggetto);
		mailVO.setContent(testo);
		// logger.shallowDump(mailVO, "info");
		maildao.send(mailVO);
		LOG.debug(prf + " END");
	}

	private String leggiCostante(String attributo) throws Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::leggiCostante] ";
		LOG.debug(prf + " BEGIN");
		String valore = null;
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			LOG.info(prf + "Valore della costante " + attributo + " = " + valore);
			if (valore == null || (valore != null && valore.isEmpty())) {
				throw new Exception("Costante " + attributo + " non valorizzata.");
			}
		} catch (Exception e) {
			LOG.error(prf + "ERRORE in leggiCostante(): " + e);
			throw new Exception("Costante " + attributo + " non valorizzata.");
		}
		return valore;
	}

	@Override
	public Response getRecapito(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getRecapito]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			RecapitoDTO rec = gestioneDatiProgettoDAO.findRecapito(idUtente, idIride, idProgetto);
			Recapiti recapito = new Recapiti();
			recapito = beanUtil.transform(rec, Recapiti.class);
			LOG.debug(prf + " END");
			return Response.ok().entity(recapito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaRecapito(HttpServletRequest req, Long idU, RequestSalvaRecapito salvaRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaRecapito]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			userInfo.setIdUtente(idUtente);
			String idIride = userInfo.getIdIride();
			Long idProgetto = salvaRequest.getIdProgetto();
			Recapiti rec = salvaRequest.getRecapito();
			EsitoOperazioni esito = new EsitoOperazioni();
			if (rec == null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecapito.recapito}");
			// recupero la mail dalla pbandi_t_recapiti
			RecapitoDTO recapitoSuDB = gestioneDatiProgettoDAO.findRecapito(idUtente, idIride, idProgetto);
			Recapiti rec1 = new Recapiti();
			Boolean hasError = false;
			rec1 = beanUtil.transform(recapitoSuDB, Recapiti.class);
			// PK solo i seguenti ruoli possono modificare il recapito : BEN-MASTER,
			// BENEFICIARIO, PERSONA-FISICA, CSI-ADMIN
//			if (userInfo.getCodiceRuolo().equalsIgnoreCase("BEN-MASTER") || userInfo.getCodiceRuolo().equalsIgnoreCase("BENEFICIARIO")
//					|| userInfo.getCodiceRuolo().equalsIgnoreCase("PERSONA-FISICA") || userInfo.getCodiceRuolo().equalsIgnoreCase("CSI-ADMIN")) {
			if (StringUtils.equalsIgnoreCase(rec.getEmail(), recapitoSuDB.getEmail())) {
				// analisi 2) Se [email attuale] uguale [email precedente
				// email postata identica a quella vecchia
				// anche se flagConfermata e' impostato, lo aggiorno
				LOG.info(prf + "Email attuale = Email precedente\n");
				rec1.setFlagEmailConfermata("S");
				// rec1.setIdUtenteAgg(theModel.getAppDatacurrentUser().getIdUtente());
				// theModel.setAppDatarecapito(rec);
				hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSede(userInfo,
						rec.getProgrSoggettoProgettoSede(), rec.getIdRecapiti());
				LOG.info(prf + " hasError " + hasError);
			} else {
				LOG.info(prf + "Email attuale != Email precedente\n");
				Recapiti recapitoFromMail = gestioneDatiProgettoDAO.getMailFromRecapiti(idUtente, idIride,
						rec.getEmail());
				if (recapitoFromMail != null) {
					LOG.info(prf + " UPDATE PBANDI_R_SOGG_PROGETTO_SEDE");
					recapitoFromMail.setFlagEmailConfermata("S");
					hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSede(userInfo,
							rec.getProgrSoggettoProgettoSede(), recapitoFromMail.getIdRecapiti());
				} else {
					LOG.info(prf + " INSERT PBANDI_T_RECAPITI");
					Recapiti recReg = gestioneDatiProgettoDAO.insertRecapito(idUtente, idIride, rec);
					if (recReg == null)
						hasError = true;
					hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSede(userInfo,
							rec.getProgrSoggettoProgettoSede(), recReg.getIdRecapiti());
				}
				LOG.info(prf + " hasError " + hasError);
			}
//			} else {
//				return inviaErroreUnauthorized(MessageConstants.GESTPROGETTO_SOGGETTI_RUOLO_ERR);
//			}
			if (hasError) {
				return inviaErroreInternalServer("Errore occorso nel update del soggetto progetto sede");
			}
			esito.setEsito(true);
			esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public Response salvaRecapitoPec(HttpServletRequest req, Long idU, RequestSalvaRecapito salvaRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaRecapitoPec]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			userInfo.setIdUtente(idUtente);
			String idIride = userInfo.getIdIride();
			Long idProgetto = salvaRequest.getIdProgetto();
			Recapiti rec = salvaRequest.getRecapito();
			EsitoOperazioni esito = new EsitoOperazioni();
			if (rec == null)
				return inviaErroreBadRequest("parametro mancato nel body: ?{RequestSalvaRecapito.recapito}");
			// recupero la mail dalla pbandi_t_recapiti
			RecapitoDTO recapitoSuDB = gestioneDatiProgettoDAO.findRecapito(idUtente, idIride, idProgetto);
			Recapiti rec1 = new Recapiti();
			Boolean hasError = false;
			rec1 = beanUtil.transform(recapitoSuDB, Recapiti.class);
			// PK solo i seguenti ruoli possono modificare il recapito : BEN-MASTER,
			// BENEFICIARIO, PERSONA-FISICA, CSI-ADMIN
//			if (userInfo.getCodiceRuolo().equalsIgnoreCase("BEN-MASTER") || userInfo.getCodiceRuolo().equalsIgnoreCase("BENEFICIARIO")
//					|| userInfo.getCodiceRuolo().equalsIgnoreCase("PERSONA-FISICA") || userInfo.getCodiceRuolo().equalsIgnoreCase("CSI-ADMIN")) {
			if (StringUtils.equalsIgnoreCase(rec.getPec(), recapitoSuDB.getPec())) {
				// analisi 2) Se [pec attuale] uguale [pec precedente
				// pec postata identica a quella vecchia
				// anche se flagConfermata e' impostato, lo aggiorno
				LOG.info(prf + "Pec attuale = Pec precedente\n");
				rec1.setFlagPecConfermata("S");
				hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSedePec(userInfo,
						rec.getProgrSoggettoProgettoSede(), rec.getIdRecapiti());
				LOG.info(prf + " hasError " + hasError);
			} else {
				LOG.info(prf + "Pec attuale != Pec precedente\n");
				Recapiti recapitoFromPec = gestioneDatiProgettoDAO.getPecFromRecapiti(idUtente, idIride,
						rec.getPec());
				if (recapitoFromPec != null) {
					LOG.info(prf + " UPDATE PBANDI_R_SOGG_PROGETTO_SEDE");
					recapitoFromPec.setFlagPecConfermata("S");
					hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSedePec(userInfo,
							rec.getProgrSoggettoProgettoSede(), recapitoFromPec.getIdRecapiti());
				} else {
					LOG.info(prf + " INSERT PBANDI_T_RECAPITI");
					Recapiti recReg = gestioneDatiProgettoDAO.insertRecapito(idUtente, idIride, rec);
					if (recReg == null)
						hasError = true;
					hasError = gestioneDatiProgettoDAO.updateSoggettoProgettoSedePec(userInfo,
							rec.getProgrSoggettoProgettoSede(), recReg.getIdRecapiti());
				}
				LOG.info(prf + " hasError " + hasError);
			}
			if (hasError) {
				return inviaErroreInternalServer("Errore occorso nel update del soggetto progetto sede pec");
			}
			esito.setEsito(true);
			esito.setMsg(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Response salvaDettaglioSoggettoProgetto(HttpServletRequest req, Long idU,
			DettaglioSoggettoProgettoDTO dettaglioSoggettoProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaDettaglioSoggettoProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = idU;
			String idIride = userInfo.getIdIride();
			// VALIDAZIONE DEI DATI
			// In caso di inserimento (ProgrSoggettoProgetto vuoto), verifica la correttezza
			// del codice fiscale.
			if (StringUtil.isEmpty(dettaglioSoggettoProgetto.getProgrSoggettoProgetto())) {
				boolean checkCF = ValidatorCodiceFiscale
						.isCodiceFiscalePersonaFisicaValido(dettaglioSoggettoProgetto.getCodiceFiscale());
				if (!checkCF) {
					return inviaMessaggio("parametro non valido: Codice Fiscale.");
				}
			}
			// Verifico che dataNascita non sia maggiore di oggi.
			if (!StringUtil.isEmpty(dettaglioSoggettoProgetto.getDataNascita())) {
				Date oggi = DateUtil.getDataOdiernaSenzaOra();
				Date dataNascita = DateUtil.getDate(dettaglioSoggettoProgetto.getDataNascita());
				if (DateUtil.after(dataNascita, oggi)) {
					return inviaErroreBadRequest("parametro non valido: Data di Nascita superiore a oggi.");
				}
			}

			// SALVA
			EsitoOperazioni esito = gestioneDatiProgettoDAO.salvaDettaglioSoggettoProgetto(idUtente, idIride,
					dettaglioSoggettoProgetto);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaSoggettoBeneficiario(HttpServletRequest req,
			DettaglioSoggettoBeneficiarioDTO dettaglioSoggettoBeneficiario) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::salvaSoggettoBeneficiario]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			// SALVA
			EsitoOperazioni esito = gestioneDatiProgettoDAO.salvaDettaglioSoggettoBeneficiario(idUtente, idIride,
					dettaglioSoggettoBeneficiario);

			LOG.debug(prf + " END");
			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// SERVIZI PER LE COMBO
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getSettoreAttivita(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSettoreAttivita]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.SETTORE_ATTIVITA);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getAttivitaAteco(HttpServletRequest req, Long idSettoreAttivita) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getAttivitaAteco]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idSettoreAttivita == null)
				return inviaErroreBadRequest("parametro mancato: ?idSettoreAttivita");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.ATTIVITA_ATECO, "idSettoreAttivita", idSettoreAttivita.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getTipoOperazione(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTipoOperazione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPO_OPERAZIONE);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getStrumentoAttuativo(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getStrumentoAttuativo]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.STRUMENTO_ATTUATIVO);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getSettoreCpt(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSettoreCpt]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.SETTORE_CPT);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getTemaPrioritario(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTemaPrioritario]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBandoLinea = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null)
					.getIdBandoLinea();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.TEMA_PRIORITARIO_BANDO_LINEA, Constants.FILTER_FIELD_ID_BANDO_LINEA,
					idBandoLinea.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getIndicatoreRisultatoProgramma(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getIndicatoreRisultatoProgramma]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null)
				return inviaErroreBadRequest("parametro mancato: ?idProgetto");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBandoLinea = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null)
					.getIdBandoLinea();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.IND_RISULTATO_PROGRAM_BANDO_LINEA, Constants.FILTER_FIELD_ID_BANDO_LINEA,
					idBandoLinea.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getIndicatoreQsn(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getIndicatoreQsn]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null)
				return inviaErroreBadRequest("parametro mancato: ?idProgetto");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBandoLinea = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null)
					.getIdBandoLinea();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.INDICATORE_QSN_BANDO_LINEA, Constants.FILTER_FIELD_ID_BANDO_LINEA,
					idBandoLinea.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getTipoAiuto(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTipoAiuto]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProgetto == null)
				return inviaErroreBadRequest("parametro mancato: ?idProgetto");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Long idBandoLinea = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null)
					.getIdBandoLinea();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPO_AIUTO_BANDO_LINEA, Constants.FILTER_FIELD_ID_BANDO_LINEA,
					idBandoLinea.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getStrumentoProgrammazione(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getStrumentoProgrammazione]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPO_STRUMENTO_PROGR);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDimensioneTerritoriale(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDimensioneTerritoriale]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.DIMENSIONE_TERRITOR);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getProgettoComplesso(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getProgettoComplesso]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.PROGETTO_COMPLESSO);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getSettoreCipe(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSettoreCipe]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.SETTORE_CIPE);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getSottoSettoreCipe(HttpServletRequest req, Long idSettoreCipe) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getSottoSettoreCipe]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idSettoreCipe == null)
				return inviaErroreBadRequest("parametro mancato: ?idSettoreCipe");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.SOTTO_SETTORE_CIPE, "idSettoreCipe", idSettoreCipe.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getCategoriaCipe(HttpServletRequest req, Long idSottoSettoreCipe)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getCategoriaCipe]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idSottoSettoreCipe == null)
				return inviaErroreBadRequest("parametro mancato: ?idSottoSettoreCipe");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.CATEGORIA_CIPE, "idSottoSettoreCipe", idSottoSettoreCipe.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getNaturaCipe(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getNaturaCipe]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.NATURA_CIPE);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getTipologiaCipe(HttpServletRequest req, Long idNaturaCipe) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTipologiaCipe]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idNaturaCipe == null)
				return inviaErroreBadRequest("parametro mancato: ?idNaturaCipe");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPOLOGIA_CIPE, "idNaturaCipe", idNaturaCipe.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getNazioni(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getNazioni]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.NAZIONE);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getRegioni(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getRegioni]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.REGIONE);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getProvince(HttpServletRequest req, Long idRegione) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getProvince]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			ArrayList<CodiceDescrizione> province = new ArrayList<>();
			if (idRegione == null || idRegione.toString().length() == 0) {
				province = beanUtil.transformToArrayList(
						datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
								GestioneDatiDiDominioSrv.PROVINCIA),
						CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
			} else {
				province = beanUtil.transformToArrayList(
						datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
								GestioneDatiDiDominioSrv.PROVINCIA, "idRegione", idRegione.toString()),
						CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE);
			}

			LOG.debug(prf + " END");
			return Response.ok().entity(province).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getComuniEsterni(HttpServletRequest req, Long idNazione) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getComuniEsterni]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idNazione == null)
				return inviaErroreBadRequest("parametro mancato: ?idNazione");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
					GestioneDatiDiDominioSrv.COMUNE_ESTERO, "idNazione", idNazione.toString());
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);
			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	// I communi di italia
	@Override
	public Response getComuni(HttpServletRequest req, Long idProvincia) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getItalia]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idProvincia == null)
				return inviaErroreBadRequest("parametro mancato: ?idProvincia");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			ComuneDTO[] comuniDto = datiDiDominioBusinessImpl.findComuni(idUtente, idIride, idProvincia);
			ArrayList<Comune> comuni = beanUtil.transformToArrayList(comuniDto, Comune.class);
			return Response.ok().entity(comuni).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response isFinpiemonte(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {

		String prf = "[GestioneDatiProgettoServiceImpl::isFinpiemonte]";
		LOG.debug(prf + " BEGIN");

		LOG.debug(prf + "idProgetto="+idProgetto);
		HttpSession session = req.getSession();
		if(session!=null) {
			LOG.debug(prf + " ID PROGETTO LETTO DA SESSIONE = [" + session.getAttribute("idProgetto")+"]");
		}else {
			LOG.debug(prf + "SESSIONE NULLA");
		}
		boolean isFinpiemonte = false;
		PbandiTProgettoVO pbandiTProgettoVO = gestioneDatiProgettoDAO.getProgettoRegione(idProgetto);

		if (pbandiTProgettoVO == null) {
			isFinpiemonte = true;
		}

		LOG.debug(prf + " End");

		return Response.ok().entity(isFinpiemonte).build();

	}

	@Override
	public Response saveCup(HttpServletRequest req, RequestSalvaCup salvaRequest) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::saveCup]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoOperazioni esito = gestioneDatiProgettoDAO.saveCup(idUtente, idIride, salvaRequest.getIdProgetto(),
					salvaRequest.getCup());

			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response saveTitoloProgetto(HttpServletRequest req, RequestSalvaTitoloProgetto salvaRequest)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::saveTitoloProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			EsitoOperazioni esito = gestioneDatiProgettoDAO.saveTitoloProgetto(idUtente, idIride,
					salvaRequest.getIdProgetto(), salvaRequest.getTitoloProgetto());

			return Response.ok().entity(esito).build();
		} catch (Exception e) {
			throw e;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// 1420
	/////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Response getTipoProceduraOriginaria(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getTipoProceduraOriginaria]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.TIPO_PROCEDURA_ORIG);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getObiettivoTematico(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getObiettivoTematico]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.OBIETTIVO_TEMATICO);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getClassificazioneRA(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getClassificazioneRA]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.CLASSIFICAZIONE_RA);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getGrandeProgetto(HttpServletRequest req) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getGrandeProgetto]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			Decodifica[] res = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride,
					GestioneDatiDiDominioSrv.GRANDE_PROGETTO);
			ArrayList<CodiceDescrizione> result = beanUtil.transformToArrayList(res, CodiceDescrizione.class,
					ConstantsMap.MAP_CODICE_DESCRIZIONE);

			LOG.debug(prf + " END");
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getPrioritaQsn(HttpServletRequest req, Long idLineaInterventoAsse)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getPrioritaQsn]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idLineaInterventoAsse == null)
				return inviaErroreBadRequest("parametro mancato: ?idLineaInterventoAsse");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();
			LOG.debug(prf + " END");
			return Response.ok()
					.entity(beanUtil.transformToArrayList(
							datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
									GestioneDatiDiDominioSrv.PRIORITA_QSN_LINEA, "idLineaDiIntervento",
									idLineaInterventoAsse.toString()),
							CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE))
					.build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getObiettivoGeneraleQsn(HttpServletRequest req, Long idPriorityQsn)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getObiettivoGeneraleQsn]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idPriorityQsn == null)
				return inviaErroreBadRequest("parametro mancato: ?idPriorityQsn");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			LOG.debug(prf + " END");
			return Response.ok().entity(beanUtil.transformToArrayList(
					datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
							GestioneDatiDiDominioSrv.OBIETTIVO_GEN_QSN, "idPrioritaQsn", idPriorityQsn.toString()),
					CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE)).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getObiettivoSpecificoQsn(HttpServletRequest req, Long idObiettivoGenerale)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getObiettivoSpecificoQsn]";
		LOG.debug(prf + " BEGIN");
		try {
			if (idObiettivoGenerale == null)
				return inviaErroreBadRequest("parametro mancato: ?idObiettivoGenerale");
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			LOG.debug(prf + " END");
			return Response.ok()
					.entity(beanUtil.transformToArrayList(
							datiDiDominioBusinessImpl.findDecodificheFiltrato(idUtente, idIride,
									GestioneDatiDiDominioSrv.OBIETTIVO_SPECIF_QSN, "idObiettivoGenQsn",
									idObiettivoGenerale.toString()),
							CodiceDescrizione.class, ConstantsMap.MAP_CODICE_DESCRIZIONE))
					.build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response getDimensioneTerritoriale1420(HttpServletRequest req, Long idProgetto)
			throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDimensioneTerritoriale1420]";
		LOG.debug(prf + " BEGIN");
		try {

			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			DimensioneTerritorialeDTO[] listDimensioneTerritoriale = gestioneDatiProgettoDAO
					.findDimensioneTerritoriale(idUtente, idIride, idProgetto);
			HashMap<String, String> trsMap = new HashMap<String, String>();
			trsMap.put("idDimensioneTerritor", "codice");
			trsMap.put("descDimensioneTerritoriale", "descrizione");

			LOG.debug(prf + " END");
			return Response.ok()
					.entity(beanUtil.transformToArrayList(listDimensioneTerritoriale, CodiceDescrizione.class, trsMap))
					.build();
		} catch (Exception e) {
			throw e;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// METODI DI RESPONSE HTTP
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Response inviaMessaggio(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreBadRequest(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreNotFound(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.NOT_FOUND).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreUnauthorized(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.UNAUTHORIZED).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaRispostaVuota(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.OK).entity(esito).type(MediaType.APPLICATION_JSON).build();
	}

	private Response inviaErroreInternalServer(String msg) {
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		esito.setMsg(msg);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).type(MediaType.APPLICATION_JSON)
				.build();
	}

	@Override
	public Response leggiEmailBeneficiarioPF(HttpServletRequest req, Long idProgetto, Long idSoggettoBen)
			throws UtenteException, Exception {
		try {
			return Response.ok().entity(gestioneDatiProgettoDAO.leggiEmailBeneficiarioPF(idProgetto, idSoggettoBen))
					.build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response salvaEmailBeneficiarioPF(HttpServletRequest req, Long idProgetto, Long idSoggettoBen, String email)
			throws UtenteException, Exception {
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);

			return Response.ok().entity(gestioneDatiProgettoDAO.salvaEmailBeneficiarioPF(idProgetto, idSoggettoBen,
					email, userInfo.getIdUtente())).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response associaAllegatiAProgetto(AssociaFilesRequest associaFilesRequest, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(gestioneDatiProgettoDAO.associaAllegatiAProgetto(associaFilesRequest, userInfo.getIdUtente()))
				.build();
	}

	@Override
	public Response disassociaAllegatoProgetto(Long idDocumentoIndex, Long idProgetto, HttpServletRequest request)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneDatiProgettoDAO.disassociaAllegatoProgetto(idDocumentoIndex, idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getDatiAggiuntivi(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
		String prf = "[GestioneDatiProgettoServiceImpl::getDatiAggiuntivi]";
		LOG.debug(prf + " BEGIN");
		try {
			HttpSession session = req.getSession();
			UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
			Long idUtente = userInfo.getIdUtente();
			String idIride = userInfo.getIdIride();

			DatiAggiuntiviDTO datiAggiuntiviDTO = gestioneDatiProgettoDAO.getDatiAggiuntiviByIdProgetto(idProgetto,
					idUtente, idIride);

			return Response.ok().entity(datiAggiuntiviDTO).build();
		} catch (Exception e) {
			throw e;
		} finally {
			LOG.debug(prf + " END");
		}
	}

}
