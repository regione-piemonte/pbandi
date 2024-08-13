/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaCulturaRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.service.DichiarazioneDiSpesaService;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.integration.dao.DichiarazioneDiSpesaDAO;
import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class DichiarazioneDiSpesaServiceImpl implements DichiarazioneDiSpesaService {

	@Autowired
	private DichiarazioneDiSpesaDAO dichiarazioneDiSpesaDAO;

	@Override
	public Response getIsBeneficiarioPrivatoCittadino(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.getIsBeneficiarioPrivatoCittadino(idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
		// return Response.ok().entity("Prova").build();
	}
	
	@Override
	public Response isBottoneConsuntivoVisibile(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.isBottoneConsuntivoVisibile(idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response rappresentantiLegali(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(
				dichiarazioneDiSpesaDAO.rappresentantiLegali(idProgetto, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response verificaDichiarazioneDiSpesa(
			VerificaDichiarazioneDiSpesaRequest verificaDichiarazioneDiSpesaRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(dichiarazioneDiSpesaDAO.verificaDichiarazioneDiSpesa(verificaDichiarazioneDiSpesaRequest,
						userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response delegatiCombo(Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(
				dichiarazioneDiSpesaDAO.delegatiCombo(idProgetto, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response iban(Long idProgetto, Long idSoggettoBeneficiario, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.iban(idProgetto, idSoggettoBeneficiario,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response tipoAllegati(Long idBandoLinea, String codiceTipoDichiarazioneDiSpesa, Long idDichiarazione,
			Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.tipoAllegati(idBandoLinea, codiceTipoDichiarazioneDiSpesa,
				idDichiarazione, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response salvaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati,
			String codiceTipoDichiarazioneDiSpesa, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.salvaTipoAllegati(listaTipoAllegati,
				codiceTipoDichiarazioneDiSpesa, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response allegatiDichiarazioneDiSpesaPerIdProgetto(String codiceTipoDichiarazioneDiSpesa, Long idProgetto,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(dichiarazioneDiSpesaDAO.allegatiDichiarazioneDiSpesaPerIdProgetto(
						codiceTipoDichiarazioneDiSpesa, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response allegatiDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa, Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.allegatiDichiarazioneDiSpesa(idDichiarazioneDiSpesa,
				idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response allegatiDichiarazioneDiSpesaIntegrazioni(Long idDichiarazioneDiSpesa, Long idProgetto, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.allegatiDichiarazioneDiSpesaIntegrazioni(idDichiarazioneDiSpesa,
				idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response disassociaAllegatoDichiarazioneDiSpesa(Long idDocumentoIndex, Long idDichiarazioneDiSpesa,
			Long idProgetto, String tipoDichiarazione, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.disassociaAllegatoDichiarazioneDiSpesa(idDocumentoIndex,
				idDichiarazioneDiSpesa, idProgetto, tipoDichiarazione, userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();
	}

	@Override
	public Response disassociaAllegatoIntegrazioneDiSpesa(Long idDocumentoIndex, Long idIntegrazioneDiSpesa,
			Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.disassociaAllegatoIntegrazioneDiSpesa(idDocumentoIndex,
				idIntegrazioneDiSpesa, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response associaAllegatiADichiarazioneDiSpesa(AssociaFilesRequest associaFilesRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.associaAllegatiADichiarazioneDiSpesa(associaFilesRequest,
				userInfo.getIdUtente())).build();
	}

	@Override
	public Response associaAllegatiADichiarazioneDiSpesaFinale(AssociaFilesRequest associaFilesRequest,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO
				.associaAllegatiADichiarazioneDiSpesaFinale(associaFilesRequest, userInfo.getIdUtente())).build();
	}

	@Override
	public Response associaAllegatiAIntegrazioneDiSpesa(AssociaFilesRequest associaFilesRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.associaAllegatiAIntegrazioneDiSpesa(associaFilesRequest,
				userInfo.getIdUtente())).build();
	}

	@Override
	public Response inizializzaInvioDichiarazioneDiSpesa(Long idProgetto, Long idBandoLinea, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.inizializzaInvioDichiarazioneDiSpesa(idProgetto,
				idBandoLinea, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response inizializzaIntegrazioneDiSpesa(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.inizializzaIntegrazioneDiSpesa(idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response anteprimaDichiarazioneDiSpesa(
			AnteprimaDichiarazioneDiSpesaRequest anteprimaDichiarazioneDiSpesaRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(dichiarazioneDiSpesaDAO.anteprimaDichiarazioneDiSpesa(anteprimaDichiarazioneDiSpesaRequest,
						userInfo.getIdUtente(), userInfo.getIdIride()).getPdfBytes())
				.build();
	}

	@Override
	public Response anteprimaDichiarazioneDiSpesaCultura(
			AnteprimaDichiarazioneDiSpesaCulturaRequest anteprimaDichiarazioneDiSpesaRequest, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(dichiarazioneDiSpesaDAO.anteprimaDichiarazioneDiSpesaCultura(anteprimaDichiarazioneDiSpesaRequest,
						userInfo.getIdUtente(), userInfo.getIdIride()).getPdfBytes())
				.build();
	}

	@Override
	public Response inviaDichiarazioneDiSpesa(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.inviaDichiarazioneDiSpesa(multipartFormData,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.salvaInvioCartaceo(invioCartaceo, idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response salvaFileFirmato(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.salvaFileFirmato(multipartFormData, userInfo.getIdUtente(),
				userInfo.getIdIride())).build();
	}

	@Override
	public Response salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {

		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.salvaFileFirmaAutografa(multipartFormData,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response verificaFirmaDigitale(Long idDocumentoIndex, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.verificaFirmaDigitale(idDocumentoIndex,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response integrazioniSpesaByIdProgetto(Long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.integrazioniSpesaByIdProgetto(idProgetto,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response marcaComeDichiarazioneDiIntegrazione(Long idDocumentoIndex, Long idIntegrazioneDiSpesa,
			Long idProgetto, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.marcaComeDichiarazioneDiIntegrazione(idDocumentoIndex,
				idIntegrazioneDiSpesa, idProgetto, userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response inviaIntegrazioneDiSpesaAIstruttore(Long idIntegrazioneDiSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(dichiarazioneDiSpesaDAO.inviaIntegrazioneDiSpesaAIstruttore(idIntegrazioneDiSpesa,
				userInfo.getIdUtente(), userInfo.getIdIride())).build();
	}

	@Override
	public Response isBandoCultura(Long idBandoLinea, HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if(userInfo == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Sessione non valida").build();
		}
		if(idBandoLinea == null || idBandoLinea <= 0L) {
			return Response.status(Response.Status.BAD_REQUEST).entity("idBandoLinea non valorizzato correttamente").build();
		}

		return Response.ok().entity(dichiarazioneDiSpesaDAO.isBandoCultura(idBandoLinea, userInfo.getIdUtente(),
				userInfo.getIdIride())).build();
	}

	/*
	 * @Override public Response testAnteprimaDichiarazioneDiSpesa(Long
	 * idBandoLinea, Long idProgetto, Long idSoggetto, Long idSoggettoBeneficiario,
	 * Long idRappresentanteLegale, String codiceTipoDichiarazioneDiSpesa,
	 * HttpServletRequest req) throws InvalidParameterException, Exception {
	 * UserInfoSec userInfo = (UserInfoSec)
	 * req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR); return
	 * Response.ok().entity(dichiarazioneDiSpesaDAO.
	 * testAnteprimaDichiarazioneDiSpesa(idBandoLinea, idProgetto, idSoggetto,
	 * idSoggettoBeneficiario, idRappresentanteLegale,
	 * codiceTipoDichiarazioneDiSpesa,
	 * userInfo.getIdUtente(),userInfo.getIdIride())).build(); }
	 */

}
