package it.csi.pbandi.pbweb.integration.dao;

import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.ProrogaIntegrazioneDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GestioneProrogheDAO {
    List<ProrogaIntegrazioneDTO> getProrogaIntegrazione(Long idDichiarazioneSpesa, HttpServletRequest req);
    List<ProrogaIntegrazioneDTO> getStoricoProrogheDS(Long idDichiarazioneSpesa, HttpServletRequest req);
    void approvaProroga(Long idProroga, Long numGiorni, HttpServletRequest req);
    void respingiProroga(Long idProroga, HttpServletRequest req);
    Boolean gestioneRichiestaProroga(Long idDichiarazioneSpesa, HttpServletRequest req);
    Boolean storicoRichiestaProroga(Long idDichiarazioneSpesa, HttpServletRequest req);
}
