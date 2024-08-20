/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.ProcedimentoRevocaService;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProcedimentoRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.PropostaRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProcedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.math.BigDecimal;

@Service
public class ProcedimentoRevocaServiceImpl implements ProcedimentoRevocaService {
    @Autowired
    ProcedimentoRevocaDAO procedimentoRevocaDAO;

    @Autowired
    PropostaRevocaDAO propostaRevocaDAO;

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Override
    public Response getProcedimentoRevoca(FiltroProcedimentoRevocaVO filtroProcedimentoRevocaVO, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;

        try {
            //CONTROLLI
            if(filtroProcedimentoRevocaVO.equals(new FiltroProcedimentoRevocaVO()))
                throw new ErroreGestitoException("ERR002");

            //CHIAMATA A DAO --->>
            result = procedimentoRevocaDAO.getProcedimentoRevoca(filtroProcedimentoRevocaVO);
            if(result == null){
                throw new ErroreGestitoException("ERR003");
            }
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }

        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response getDettaglioProcedimentoRevoca(Long idProcedimentoRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            //CONTROLLI
            if(idProcedimentoRevoca == null)
                throw new ErroreGestitoException("idProcedimentoRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            result = procedimentoRevocaDAO.getDettaglioProcedimentoRevoca(idProcedimentoRevoca, userInfoSec.getIdUtente());
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response getDocumentiProcedimentoRevoca(Long numeroRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(numeroRevoca == null)
                throw new ErroreGestitoException("numeroRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            result = procedimentoRevocaDAO.getDocumentiProcedimentoRevoca(numeroRevoca);
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response eliminaAllegato(Long idDocumentoIndex, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(idDocumentoIndex == null)
                throw new ErroreGestitoException("idDocumentoIndex non impostato correttamente!");

            //CHIAMATA A DAO --->>
            if(procedimentoRevocaDAO.eliminaAllegato(idDocumentoIndex)){
                result = new ResponseCodeMessage("OK", "Allegato eliminato con successo");
            } else {
                result = new ResponseCodeMessage("KO", "Allegato non trovato");
            }
        } catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response updateProcedimentoRevoca(ProcedimentoRevocaVO procedimentoRevocaVO, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(procedimentoRevocaVO.getIdProcedimentoRevoca() == null)
                throw new ErroreGestitoException("idProcedimentoRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.updateProcedimentoRevoca(procedimentoRevocaVO, req);
            result = new ResponseCodeMessage("OK", "Importi aggiornati correttamente!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response eliminaProcedimentoRevoca(Long numeroProcedimentoRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("numeroProcedimentoRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.eliminaProcedimentoRevoca(numeroProcedimentoRevoca, req);
            result = new ResponseCodeMessage("OK", "Bozza di procedimento revoca eliminato correttamente!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response verificaImporti(Long idGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(idGestioneRevoca == null)
                throw new ErroreGestitoException("idGestioneRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.verificaImporti(idGestioneRevoca);
            result = new ResponseCodeMessage("OK", "Importi valorizzati correttamente!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response avviaProcedimentoRevoca(Long numeroProcedimentoRevoca, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("Variabile numeroProcedimentoRevoca non valorizzata");
            if(giorniScadenza == null)
                throw new ErroreGestitoException("Variabile giorniScadenza non valorizzata");
            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.avviaProcedimentoRevoca(numeroProcedimentoRevoca, giorniScadenza, req);
            result = new ResponseCodeMessage("OK", "Procedimento di revoca avviato con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response inviaIncaricoAErogazione(Long numeroProcedimentoRevoca, Long numeroDichiarazioneSpesa, BigDecimal importoDaErogareContributo, BigDecimal importoDaErogareFinanziamento, BigDecimal importoIres, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("Variabile numeroProcedimentoRevoca non valorizzata");
            if(numeroDichiarazioneSpesa == null)
                throw new ErroreGestitoException("Variabile numeroDichiarazioneSpesa non valorizzata");
            if(importoDaErogareContributo == null && importoDaErogareFinanziamento == null)
                throw new ErroreGestitoException("Variabile importoDaErogare non valorizzata");
            if(importoIres == null)
                throw new ErroreGestitoException("Variabile importoIres non valorizzata");
            if(giorniScadenza == null)
                throw new ErroreGestitoException("Variabile giorniScadenza non valorizzata");
            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.inviaIncaricoAErogazione(numeroProcedimentoRevoca, numeroDichiarazioneSpesa, importoDaErogareContributo, importoDaErogareFinanziamento, importoIres, giorniScadenza, req);
            result = new ResponseCodeMessage("OK", "Procedimento di revoca avviato con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response aggiungiAllegato(Long idGestioneRevoca, MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            File allegato = multipartFormDataInput.getFormDataPart("allegato", File.class, null);
            String nomeAllegato = multipartFormDataInput.getFormDataPart("nomeAllegato", String.class, null);
            String letteraAccompagnatoria = multipartFormDataInput.getFormDataPart("letteraAccompagnatoria", String.class, null);
            String allegatoIntegrazione = multipartFormDataInput.getFormDataPart("allegatoIntegrazione", String.class, null);
            String allegatoArchiviazione = multipartFormDataInput.getFormDataPart("allegatoArchiviazione", String.class, null);
            if(allegato == null)
                throw new ErroreGestitoException("Variabile allegato non valorizzata correttamente");
            if(nomeAllegato == null)
                throw new ErroreGestitoException("Variabile nomeAllegato non valorizzata correttamente");
            if(letteraAccompagnatoria == null)
                throw new ErroreGestitoException("Variabile letteraAccompagnatoria non valorizzata correttamente");
            if(allegatoIntegrazione == null)
                throw new ErroreGestitoException("Variabile allegatoIntegrazione non valorizzata correttamente");
            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.aggiungiAllegato(idGestioneRevoca, FileUtil.getBytesFromFile(allegato), nomeAllegato, letteraAccompagnatoria.equals("letteraAccompagnatoria"), allegatoIntegrazione.equals("allegatoIntegrazione"), allegatoArchiviazione.equals("allegatoArchiviazione"), req);
            result = new ResponseCodeMessage("OK", "Allegato aggiunto con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response creaBozzaProvvedimentoRevoca(Long idGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(idGestioneRevoca == null)
                throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            //CHIAMATA A DAO --->>
            //2 inserimento nuovo record procedimento di revoca che è la copia di quello con idGestioneRevoca corrispondente
            propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca), null, 2L, 3L, userInfoSec.getIdUtente());
            //3 inserimento nuovo record provvedimento di revoca
            propostaRevocaDAO.updateRevoca(propostaRevocaDAO.cloneGestioneRevoca(idGestioneRevoca), null, 3L, 5L, userInfoSec.getIdUtente());
            //1 Disabilitazione record attuale
            propostaRevocaDAO.disableCurrentGestioneRevoca(idGestioneRevoca, userInfoSec.getIdUtente());
            result = new ResponseCodeMessage("OK", "Bozza provvedimento di revoca creata con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response archiviaProcedimentoRevoca(Long numeroProcedimentoRevoca, String note, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("Variabile numeroProcedimentoRevoca non valorizzata");

            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.archiviaProcedimentoRevoca(numeroProcedimentoRevoca, note, req);
            result = new ResponseCodeMessage("OK", "Procedimento di revoca archiviato con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response salvaNoteArchiviazione(Long idGestioneRevoca, String note, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(note == null)
                note = "";

            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.salvaNoteArchiviazione(idGestioneRevoca, note, req);
            result = new ResponseCodeMessage("OK", "Note archiviazione salvate correttamente!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }
    /*Proroghe*/

    @Override
    public Response getRichiestaProroga(Long numeroProcedimentoRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("Variabile numeroProcedimentoRevoca non valorizzata");
            //CHIAMATA A DAO --->>
            result = procedimentoRevocaDAO.getRichiestaProroga(numeroProcedimentoRevoca);
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response updateRichiestaProroga(Long numeroProcedimentoRevoca, Boolean esitoRichiestaProroga, Long giorniApprovati, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(esitoRichiestaProroga == null)
                throw new ErroreGestitoException("Variabile esitoRichiestaProroga non valorizzata");
            if(numeroProcedimentoRevoca == null)
                throw new ErroreGestitoException("Variabile numeroProcedimentoRevoca non valorizzata");
            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.updateRichiestaProroga(numeroProcedimentoRevoca,esitoRichiestaProroga, giorniApprovati, req);
            result = new ResponseCodeMessage("OK", "Richiesta di proroga aggiornata con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }


    /*Integrazioni*/
    @Override
    public Response abilitaRichiediIntegrazione(Long idGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;


        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //CONTROLLI
        if(idGestioneRevoca == null)
            throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
        //CHIAMATA A DAO --->>
        if(procedimentoRevocaDAO.abilitaRichiediIntegrazione(idGestioneRevoca) &&
                procedimentoRevocaDAO.abilitaAvviaIterIntegrazione(userInfoSec.getIdUtente())) {
            result = new ResponseCodeMessage("OK", "Richiedi integrazione abilitato!");
        }else{
            result = new ResponseCodeMessage("KO", "Richiedi integrazione non abilitato!");
        }


        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }
    @Override
    public Response creaRichiestaIntegrazione(Long idGestioneRevoca, Long numGiorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //richiesta integrazione
            //CONTROLLI
            if(idGestioneRevoca == null)
                throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
            //CHIAMATA A DAO --->>
            Long idRichiestaIntegrazione = procedimentoRevocaDAO.creaRichiestaIntegrazione(idGestioneRevoca, numGiorniScadenza, req);

            /* gli allegati sono autonomi rispetto alla creazione della richiesta d'integrazione
            Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
            //lettereAccompagnatoria
            List<InputPart> letteraAccompagnatoria = map.get("letteraAccompagnatoria");
            if(letteraAccompagnatoria.isEmpty()){
                throw new ErroreGestitoException("Lettera Accompagnatoria non valorizzata correttamente");
            }
            procedimentoRevocaDAO.aggiungiAllegato(idRichiestaIntegrazione, IOUtils.toByteArray(letteraAccompagnatoria.get(0).getBody(InputStream.class, null)), getFileName(letteraAccompagnatoria.get(0).getHeaders()), true, true, false, req);
            //altriAllegati
            List<InputPart> altriAllegati = null;
            try {
                altriAllegati = map.get("file");
            }catch (Exception ignored){}
            if(altriAllegati != null) {
                for (InputPart altroAllegato : altriAllegati) {
                    procedimentoRevocaDAO.aggiungiAllegato(idRichiestaIntegrazione, IOUtils.toByteArray(altroAllegato.getBody(InputStream.class, null)), getFileName(altroAllegato.getHeaders()), false, true, false, req);
                }
            }
             */

            result = new ResponseCodeMessage("OK", "Richiesta di integrazione creata con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response abilitaChiudiIntegrazione(Long idGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        //CONTROLLI
        if(idGestioneRevoca == null)
            throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
        //CHIAMATA A DAO --->>
        if(procedimentoRevocaDAO.abilitaChiudiIntegrazione(idGestioneRevoca)){
            result = new ResponseCodeMessage("OK", "Chiudi richiesta integrazione abilitato!");
        }else{
            result = new ResponseCodeMessage("KO", "Chiudi richiesta integrazione non abilitato!");
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }

    @Override
    public Response chiudiRichiestaIntegrazione(Long idGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(idGestioneRevoca == null)
                throw new ErroreGestitoException("Variabile idGestioneRevoca non valorizzata");
            //CHIAMATA A DAO --->>
            procedimentoRevocaDAO.chiudiRichiestaIntegrazione(idGestioneRevoca, req);
            result = new ResponseCodeMessage("OK", "Richiesta di integrazione chiusa d'ufficio!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
            //throw e;
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }


    /*UTILS*/
    public static String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String value : contentDisposition) {
            if (value.trim().startsWith("filename")) {
                String[] name = value.split("=");
                if (name.length > 1) {
                    return name[1].trim().replaceAll("\"", "");
                }
            }
        }
        return null;
    }

}
