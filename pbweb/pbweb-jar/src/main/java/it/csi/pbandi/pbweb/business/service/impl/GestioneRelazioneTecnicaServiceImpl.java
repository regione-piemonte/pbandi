package it.csi.pbandi.pbweb.business.service.impl;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbweb.business.service.GestioneRelazioneTecnicaService;
import it.csi.pbandi.pbweb.integration.dao.GestioneRelazioneTecnicaDAO;
import it.csi.pbandi.pbweb.util.Constants;

@Service
public class GestioneRelazioneTecnicaServiceImpl implements GestioneRelazioneTecnicaService {

private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GestioneRelazioneTecnicaDAO relazioneTecnicaDAO; 
	
	@Override
	public Response getCodiceProgetto(Long idProgetto, HttpServletRequest req) throws Exception {
		
		return Response.ok(relazioneTecnicaDAO.getCodiceProgetto(idProgetto)).build();
	}

	@Override
	public Response salvaRelazioneTecnica(MultipartFormDataInput multipartFormData, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		
		return Response.ok(relazioneTecnicaDAO.salvaRelazioneTecnica(multipartFormData, req)).build();
	}

	@Override
	public Response getRelazioneTecnica(Long idProgetto, int idTipoRelazioneTecnica,HttpServletRequest req) throws Exception {
		return Response.ok(relazioneTecnicaDAO.getRelazioneTecnica(idProgetto,idTipoRelazioneTecnica, req)).build();
	}

	@Override
	public Response validaRifiutaRelazioneTecnica(Long idRelazioneTecnica, String flagConferma,String nota, HttpServletRequest req)
			throws InvalidParameterException, Exception {
		
		return Response.ok(relazioneTecnicaDAO.validaRifiutaRelazioneTecnica(idRelazioneTecnica, flagConferma,nota, req)).build();
	}

}
