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
public class RendicontazioneCulturaServiceImpl implements it.csi.pbandi.pbweb.business.service.RendicontazioneCulturaService {

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
  public Response getDirezioniSettori(HttpServletRequest req) throws Exception {
    UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
    return Response.ok().entity(rendicontazioneDAO.getDirezioniSettori(userInfo.getIdUtente(), userInfo.getIdIride()))
        .build();
  }

}
