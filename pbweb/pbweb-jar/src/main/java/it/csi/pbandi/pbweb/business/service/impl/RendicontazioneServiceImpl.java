package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.integration.dao.RendicontazioneDAO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class RendicontazioneServiceImpl implements it.csi.pbandi.pbweb.business.service.RendicontazioneService {

	@Autowired
	private RendicontazioneDAO rendicontazioneDAO;

	@Override
	public Response inizializzaRendicontazione(long idProgetto, long idBandoLinea, long idSoggetto, String codiceRuolo,
			HttpServletRequest req) throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(rendicontazioneDAO.inizializzaRendicontazione(idProgetto, idBandoLinea, idSoggetto, codiceRuolo,
						userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getSALCorrente(long idProgetto, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(rendicontazioneDAO.getSALCorrente(idProgetto, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getSALByIdDocSpesa(long idProgetto, long idDocumentoSpesa, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(rendicontazioneDAO
						.getSALByIdDocSpesa(idProgetto, idDocumentoSpesa, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	@Override
	public Response getDatiColonneQteSALCorrente(Long idProgetto, Long idIter, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok()
				.entity(rendicontazioneDAO.getDatiColonneQteSALCorrente(idProgetto, idIter, userInfo.getIdSoggetto(),
						userInfo.getCodiceRuolo(), userInfo.getIdUtente(), userInfo.getIdIride(),
						userInfo.getBeneficiarioSelezionato().getIdBeneficiario()))
				.build();
	}

	/*
	 * 
	 * @Autowired private RendicontazioneDAO rendicontazioneDAO;
	 * 
	 * @Override public Response inizializzaRendicontazione(long idUtente, long
	 * idProgetto, long idSoggetto, long idSoggettoBeneficiario, String codiceRuolo,
	 * HttpServletRequest req) throws InvalidParameterException, Exception {
	 * 
	 * return Response.ok().entity(rendicontazioneDAO.inizializzaRendicontazione(
	 * idProgetto, idSoggetto, idSoggettoBeneficiario, codiceRuolo, idUtente,
	 * req)).build(); }
	 * 
	 * @Override public Response getRendicontazioneHome(
	 * 
	 * @DefaultValue("0") @QueryParam("idPrj") Long idPrj,
	 * 
	 * @DefaultValue("0") @QueryParam("idSg") Long idSg,
	 * 
	 * @DefaultValue("0") @QueryParam("idSgBen") Long idSgBen,
	 * 
	 * @DefaultValue("0") @QueryParam("idU") Long idU,
	 * 
	 * @DefaultValue("--") @QueryParam("role") String role,
	 * 
	 * @DefaultValue("--") @QueryParam("taskIdentity") String taskIdentity,
	 * 
	 * @DefaultValue("--") @QueryParam("taskName") String taskName,
	 * 
	 * @DefaultValue("--") @QueryParam("wkfAction") String wkfAction,
	 * HttpServletRequest req) throws UtenteException {
	 * 
	 * UserInfoSec user = inizializzazioneDAO.completaDatiUtente(idPrj, idSg,
	 * idSgBen, idU, role, taskIdentity, taskName, wkfAction, req); return
	 * Response.ok().entity(user).build();
	 * 
	 * //ProgettoBandoLineaVO progettoBandoLinea =
	 * inizializzazioneDAO.completaDatiProgetto(idPrj); //return
	 * Response.ok().entity(progettoBandoLinea).build(); }
	 */

}
