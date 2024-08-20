/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.DistinteService;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.DistinteDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestionDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDistintaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroDistinteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProposteErogazioneDistVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.util.Objects;

@Service
public class DistinteServiceImpl implements DistinteService {

    @Autowired
    DistinteDAO distinteDao;

    @Autowired
    SuggestionDAO suggestionDAO;

    @Override
    public Response suggestCodiceFondoFinpis(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestionDAO.suggestCodiceFondoFinpis(value, req)).build();
    }

    @Override
    public Response suggestDenominazione(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestionDAO.suggestDenominazione(value, req)).build();
    }

    @Override
    public Response suggestProgetto(String value, Long idBando, HttpServletRequest req) throws Exception {
        if (idBando == -1) {
            return Response.ok().entity(suggestionDAO.suggestProgetto(value, req)).build();
        } else {
            return Response.ok().entity(suggestionDAO.suggestProgetto(value, idBando, req)).build();
        }
    }

    @Override
    public Response suggestBando(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestionDAO.suggestionBando(value, req)).build();
    }

    @Override
    public Response suggestAgevolazione(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestionDAO.suggestionAgevolazione(value, req)).build();
    }

    @Override
    public Response suggestDistintaRespinta(String value, HttpServletRequest req) throws Exception {
        if(Objects.equals(value, "-1")){
            value = "";
        }
        return Response.ok().entity(suggestionDAO.suggestionDistinta(value, true, req)).build();
    }

    @Override
    public Response suggestDistinta(String value, HttpServletRequest req) throws Exception {
        return Response.ok().entity(suggestionDAO.suggestionDistinta(value, false, req)).build();
    }

    @Override
    public Response nuovaDistinta(Long idBando, Long idModalitaAgevolazione, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.nuovaDistinta(idBando, idModalitaAgevolazione, req)).build();
    }

    @Override
    public Response copiaDistinta(Long idDistinta, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.copiaDistinta(idDistinta, req)).build();
    }

    @Override
    public Response modificaDistinta(Long idDistinta, DatiDistintaVO datiDistintaVO, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.modificaDistinta(idDistinta, datiDistintaVO, req)).build();
    }

    @Override
    public Response existsDistinta(Long idDistinta, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.existsDistinta(idDistinta)).build();
    }

    @Override
    public Response getNuovaDistinta(Long idBando, Long idModalitaAgevolazione, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.getNuovaDistinta(idBando, idModalitaAgevolazione)).build();
    }

    @Override
    public Response getProposteErogazione(FiltroProposteErogazioneDistVO filtroProposteErogazioneDistVO, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.getProposteErogazione(filtroProposteErogazioneDistVO)).build();
    }

    @Override
    public Response getDettaglioDistinta(Long idDistinta, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.getDettaglioDistinta(idDistinta, req)).build();
    }

    @Override
    public Response salvaInBozza(DatiDistintaVO datiDistintaVO, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.salvaInBozza(datiDistintaVO, req)).build();
    }

    @Override
    public Response isAbilitatoAvviaIter(HttpServletRequest req) {
        return Response.ok().entity(distinteDao.isAbilitatoAvviaIter(req)).build();
    }

    @Override
    public Response avviaIterAutorizzativo(Long idDistinta, HttpServletRequest req) {
        String errore = "Verificare gli allegati!";

        if(distinteDao.checkAllegatiDistinta(idDistinta)){
            errore = "";
            try {
                distinteDao.avviaIterAutorizzativo(idDistinta, req);
            }catch (ErroreGestitoException e){
                errore = e.getMessage();
            }
        }

        return Response.ok().entity(errore).build();
    }

    @Override
    public Response salvaAllegato(MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) {
        byte[] file;
        String visibilita;
        String nomeFile;
        Long idTipoDocumentoIndex;
        Long idDistinta;
        Long idProgetto;
        try{
            file = FileUtil.getBytesFromFile(multipartFormDataInput.getFormDataPart("file", File.class, null));
            visibilita = multipartFormDataInput.getFormDataPart("visibilita", String.class, null);
            nomeFile = multipartFormDataInput.getFormDataPart("nomeFile", String.class, null);
            idTipoDocumentoIndex = multipartFormDataInput.getFormDataPart("idTipoDocumentoIndex", Long.class, null);
            idDistinta = multipartFormDataInput.getFormDataPart("idDistinta", Long.class, null);
            idProgetto = multipartFormDataInput.getFormDataPart("idProgetto", Long.class, null);

            distinteDao.salvaAllegato(file, visibilita, nomeFile, idTipoDocumentoIndex, idDistinta, idProgetto, req);
        }catch (Exception e){
            e.printStackTrace();
            return Response.ok().entity(false).build();
        }
        return Response.ok().entity(true).build();
    }

    @Override
    public Response eliminaAllegato(Long idDocumentoIndex, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.eliminaAllegato(idDocumentoIndex)).build();
    }

    @Override
    public Response updateVisibilita(Long idDocumentoIndex, String visibilita, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.updateVisibilita(idDocumentoIndex, visibilita)).build();
    }

    @Override
    public Response checkAllegati(Long idDistinta, HttpServletRequest req) {
        return Response.ok().entity(distinteDao.checkAllegatiDistinta(idDistinta)).build();
    }

    @Override
    public Response filterDistinte(FiltroDistinteVO filtroDistinteVO, HttpServletRequest req) {
        if(filtroDistinteVO.getIdProgetto() == null && filtroDistinteVO.getIdBeneficiario() == null){
            return Response.ok().entity(distinteDao.filterDistinte(filtroDistinteVO.getDataCreazioneFrom(), filtroDistinteVO.getDataCreazioneTo(),
                    filtroDistinteVO.getIdBando(), filtroDistinteVO.getIdAgevolazione(), filtroDistinteVO.getIdDistinta(), req)).build();
        }else{
            return Response.ok().entity(distinteDao.filterDistinte(filtroDistinteVO.getDataCreazioneFrom(), filtroDistinteVO.getDataCreazioneTo(),
                    filtroDistinteVO.getIdBeneficiario(), filtroDistinteVO.getIdBando(), filtroDistinteVO.getIdAgevolazione(),
                    filtroDistinteVO.getIdProgetto(), filtroDistinteVO.getIdDistinta(), req)).build();
        }
    }

    @Override
    public Response esportaDettaglioDistinta(Long idDistinta, HttpServletRequest req) throws ErroreGestitoException, RecordNotFoundException {
        XSSFWorkbook wb = this.distinteDao.esportaDettaglioDistinta(idDistinta);

        StreamingOutput streamOutput = wb::write;

        return Response.ok(streamOutput, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").header("Content-Disposition", "attachment; filename=DettaglioDistinta.xlsx").build();
    }
}
