/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.business.service.ContoEconomicoWaService;
import it.csi.pbandi.pbwebrce.dto.ComuneProvinciaSedeDTO;
import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.DatiCccDTO;
import it.csi.pbandi.pbwebrce.dto.DtFineEffEstremiAttoDTO;
import it.csi.pbandi.pbwebrce.dto.EsitoDTO;
import it.csi.pbandi.pbwebrce.dto.QteProgettoDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;
import it.csi.pbandi.pbwebrce.integration.dao.ContoEconomicoWaDAO;
import it.csi.pbandi.pbwebrce.util.Constants;

@Service
public class ContoEconomicoWaServiceImpl implements ContoEconomicoWaService {

	@Autowired
	private ContoEconomicoWaDAO contoEconomicoWaDAO;

	@Override
	public Response getModelloQte(Long progrBandoLineaIntervento, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(contoEconomicoWaDAO.getModelloQte(progrBandoLineaIntervento, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getQteProgetto(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(contoEconomicoWaDAO.getQteProgetto(idProgetto, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	@Transactional
	public Response salvaQteProgetto(QteProgettoDTO request, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoDTO esito = contoEconomicoWaDAO.salvaQteHtmlProgetto(request.getIdProgetto(),
				request.getIdQtesHtmlProgetto(), request.getHtmlQtesProgetto(), userInfo.getIdSoggetto(),
				userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
				userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
		if (esito.getEsito().equals(Boolean.TRUE) && request.getDatiQte() != null && request.getDatiQte().size() > 0) {
			esito = contoEconomicoWaDAO.salvaQteDatiProgetto(request.getIdProgetto(), request.getDatiQte(),
					userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
		}
		if (esito.getEsito().equals(Boolean.TRUE)) {
			esito.setMessaggio("Quadro tecnico economico salvato con successo.");
		} else {
			esito.setMessaggio("Errore durante il salvataggio del quadro tecnico economico salvato.");
		}
		return Response.ok().entity(esito).build();
	}

	@Override
	public Response getColonneModelloQte(Long progrBandoLineaIntervento, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(contoEconomicoWaDAO.getColonneModelloQte(progrBandoLineaIntervento, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getDatiCCC(Long progrBandoLineaIntervento, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		DatiCccDTO datiCCC = new DatiCccDTO();
		try {
			String descEnteCompetenza = contoEconomicoWaDAO.getDescEnteCompetenza(progrBandoLineaIntervento,
					Constants.DESC_BREVE_RUOLO_ENTE_COMPETENZA_DESTINATARIO, userInfo.getIdSoggetto(),
					userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			String descSettoreEnte = contoEconomicoWaDAO.getDescSettoreEnte(progrBandoLineaIntervento,
					Constants.DESC_BREVE_RUOLO_ENTE_COMPETENZA_DESTINATARIO, userInfo.getIdSoggetto(),
					userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			TitoloBandoNomeBandoLineaDTO titoloBandoNomeBandoLinea = contoEconomicoWaDAO.getTitoloBandoNomeBandoLinea(
					progrBandoLineaIntervento, userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(),
					userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			CupProgettoNumeroDomandaDTO cupProgettoNumeroDomanda = contoEconomicoWaDAO.getCupProgettoNumeroDomanda(
					idProgetto, userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(), userInfo.getIdUtente(),
					userInfo.getIdIride(), userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			ComuneProvinciaSedeDTO comuneProvinciaSede = contoEconomicoWaDAO.getComuneProvinciaSede(idProgetto,
					Constants.DESC_BREVE_TIPO_SEDE_INTERVENTO, userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(),
					userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
			DtFineEffEstremiAttoDTO dtFineEffEstremiAtto = contoEconomicoWaDAO.getDtFineEffEstremiAtto(idProgetto,
					Constants.COD_IGRUE_EMISSIONE_POSITIVO_CERTIFICATO_COLLAUDO, userInfo.getIdSoggetto(),
					userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
					userInfo.getBeneficiarioSelezionato().getIdBeneficiario());

			datiCCC.setDescEnte(descEnteCompetenza);
			datiCCC.setDescSettore(descSettoreEnte);
			if (titoloBandoNomeBandoLinea != null) {
				datiCCC.setTitoloBando(titoloBandoNomeBandoLinea.getTitoloBando());
				datiCCC.setNomeBandoLinea(titoloBandoNomeBandoLinea.getNomeBandoLinea());
			}
			if (cupProgettoNumeroDomanda != null) {
				datiCCC.setNumeroDomanda(cupProgettoNumeroDomanda.getNumeroDomanda());
				datiCCC.setCup(cupProgettoNumeroDomanda.getCup());
			}
			if (comuneProvinciaSede != null) {
				datiCCC.setDescComune(comuneProvinciaSede.getDescComune());
				datiCCC.setDescProvincia(comuneProvinciaSede.getDescProvincia());
			}
			if (dtFineEffEstremiAtto != null) {
				datiCCC.setDtFineEffettiva(dtFineEffEstremiAtto.getDtFineEffettiva());
				datiCCC.setEstremiAttoAmministrativo(dtFineEffEstremiAtto.getEstremiAttoAmministrativo());
			}
		} catch (Exception e) {
			throw e;
		}
		return Response.ok().entity(datiCCC).build();
	}

	@Override
	public Response salvaCCCDefinitivo(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(contoEconomicoWaDAO.salvaCCCDefinitivo(multipartFormData, userInfo.getIdUtente(),
				userInfo.getCodiceRuolo(), userInfo.getIdIride())).build();
	}

	@Override
	public Response getIdDocumentoIndexCCC(Long idProgetto, Long idQtesHtmlProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(contoEconomicoWaDAO.getIdDocumentoIndexCCC(idProgetto, idQtesHtmlProgetto,
						Constants.DESC_BREVE_TIPO_DOC_INDEX_CCC, userInfo.getIdSoggetto(), userInfo.getCodiceRuolo(),
						userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

}
