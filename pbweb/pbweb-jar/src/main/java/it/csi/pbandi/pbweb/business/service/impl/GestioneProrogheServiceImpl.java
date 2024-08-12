package it.csi.pbandi.pbweb.business.service.impl;

import it.csi.pbandi.pbweb.business.service.GestioneProrogheService;
import it.csi.pbandi.pbweb.integration.dao.GestioneProrogheDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@Service
public class GestioneProrogheServiceImpl implements GestioneProrogheService {

    @Autowired
    GestioneProrogheDAO gestioneProrogheDAO;

    @Override
    public Response getProrogaIntegrazione(Long idDichiarazioneSpesa, HttpServletRequest req) {
        return Response.ok().entity(gestioneProrogheDAO.getProrogaIntegrazione(idDichiarazioneSpesa, req)).build();
    }

    @Override
    public Response getStoricoProrogheDS(Long idDichiarazioneSpesa, HttpServletRequest req) {
        return Response.ok().entity(gestioneProrogheDAO.getStoricoProrogheDS(idDichiarazioneSpesa, req)).build();
    }

    @Override
    public Response approvaProroga(Long idProroga, Long numGiorni, HttpServletRequest req) {
        boolean esito;
        try {
            gestioneProrogheDAO.approvaProroga(idProroga, numGiorni, req);
            esito = true;
        }catch (Exception e){
            esito = false;
        }
        return Response.ok().entity(esito).build();
    }

    @Override
    public Response respingiProroga(Long idProroga, HttpServletRequest req) {
        boolean esito;
        try {
            gestioneProrogheDAO.respingiProroga(idProroga, req);
            esito = true;
        }catch (Exception e){
            esito = false;
        }
        return Response.ok().entity(esito).build();
    }

    @Override
    public Response getStepProroga(Long idDichiarazioneSpesa, HttpServletRequest req) {
        int decodifica;
        boolean gestioneRichiestaProroga = gestioneProrogheDAO.gestioneRichiestaProroga(idDichiarazioneSpesa, req);
        boolean storicoRichiestaProroga = gestioneProrogheDAO.storicoRichiestaProroga(idDichiarazioneSpesa, req);

        if(gestioneRichiestaProroga){
            if(storicoRichiestaProroga){
                decodifica = 2;
            }else{
                decodifica = 1;
            }
        }else{
            if(storicoRichiestaProroga){
                decodifica = 3;
            }else{
                decodifica = 0;
            }
        }

        return Response.ok().entity(decodifica).build();
    }
}
