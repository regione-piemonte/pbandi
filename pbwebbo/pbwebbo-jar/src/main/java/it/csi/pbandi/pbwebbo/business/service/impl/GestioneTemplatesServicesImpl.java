/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionetemplates.GestioneTemplatesBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionetemplates.EsitoAnteprimaTemplateDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebbo.util.Constants;
import it.csi.pbandi.pbwebbo.business.service.GestioneTemplatesService;

@Service
public class GestioneTemplatesServicesImpl implements GestioneTemplatesService {

	@Autowired
	private GestioneTemplatesBusinessImpl gestioneTemplatesSrv;

	@Autowired
	private GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioSrv;

	@Override
	public Response findBandiLinea(HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneTemplatesSrv.findBandoLinea(userInfo.getIdUtente(), userInfo.getIdIride()))
				.build();

	}

	@Override
	public Response findTipiDocumento(HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneDatiDiDominioSrv.findDecodifiche(userInfo.getIdUtente(),
				userInfo.getIdIride(), gestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX)).build();
	}

	@Override
	public Response findModelli(Long progrBandolinea, Long idTipoDocumento, HttpServletRequest req) throws Exception {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		return Response.ok().entity(gestioneTemplatesSrv.findModelli(userInfo.getIdUtente(), userInfo.getIdIride(),
				progrBandolinea, idTipoDocumento)).build();

	}

	@Override
	public Response anteprimaTemplate(Long progrBandolinea, Long idTipoDocumento, HttpServletRequest req)
			throws Exception, ErroreGestitoException {
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		EsitoAnteprimaTemplateDTO esito = gestioneTemplatesSrv.anteprimaTemplate(userInfo.getIdUtente(),
				userInfo.getIdIride(), progrBandolinea, idTipoDocumento);
		if (esito == null || esito.getBytes() == null) {
			throw new ErroreGestitoException("Errore");
		}
		return Response.ok().header("header-nome-file", esito.getNomeFile()).entity(esito.getBytes()).build();

	}

}
