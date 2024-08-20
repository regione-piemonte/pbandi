/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import it.csi.pbandi.pbgestfinbo.business.service.ProvvedimentoRevocaService;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.dto.utils.ResponseCodeMessage;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProvvedimentoRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;

@Service
public class ProvvedimentoRevocaServiceImpl implements ProvvedimentoRevocaService {
    @Autowired
    ProvvedimentoRevocaDAO provvedimentoRevocaDAO;

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Override
    public Response getProvvedimentoRevoca(FiltroRevocaVO filtroRevocaVO, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;

        try {

            //CHIAMATA A DAO --->>
            result = provvedimentoRevocaDAO.getProvvedimentoRevoca(filtroRevocaVO);
            if(result == null){
                throw new ErroreGestitoException("Non sono presenti provvedimenti di revoca per i criteri impostati");
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
    public Response getDettaglioProvvedimentoRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            //CONTROLLI
            if(numeroGestioneRevoca == null)
                throw new ErroreGestitoException("numeroGestioneRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            result = provvedimentoRevocaDAO.getDettaglioProvvedimentoRevoca(numeroGestioneRevoca, userInfoSec.getIdUtente());
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response getDocumentiProvvedimentoRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(numeroGestioneRevoca == null)
                throw new ErroreGestitoException("numeroGestioneRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            result = provvedimentoRevocaDAO.getDocumentiProvvedimentoRevoca(numeroGestioneRevoca);
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response modificaProvvedimentoRevoca(Long numeroGestioneRevoca, ProvvedimentoRevocaVO provvedimentoRevocaVO, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(numeroGestioneRevoca == null)
                throw new ErroreGestitoException("numeroGestioneRevoca non impostato correttamente!");
            if(provvedimentoRevocaVO == null)
                throw new ErroreGestitoException("provvedimentoRevocaVO non impostato correttamente!");

            //Verifica importi totali revoca
            provvedimentoRevocaDAO.verificaImportiTotaliRevoca(numeroGestioneRevoca, provvedimentoRevocaVO);

            //CHIAMATA A DAO --->>
            provvedimentoRevocaDAO.modificaProvvedimentoRevoca(numeroGestioneRevoca, provvedimentoRevocaVO, req);
            result = new ResponseCodeMessage("OK", "Aggiornamento provvedimento revoca avvenuto con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response eliminaProvvedimentoRevoca(Long numeroRevoca, HttpServletRequest req) {
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
            provvedimentoRevocaDAO.eliminaProvvedimentoRevoca(numeroRevoca, req);
            result = new ResponseCodeMessage("OK", "Bozza di provvedimento revoca eliminata correttamente!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response abilitaEmettiProvvedimento(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Object result;
        try {
            //CONTROLLI
            if(numeroGestioneRevoca == null)
                throw new ErroreGestitoException("numeroGestioneRevoca non impostato correttamente!");

            //CHIAMATA A DAO --->>
            provvedimentoRevocaDAO.abilitaEmettiProvvedimento(numeroGestioneRevoca);
            result = new ResponseCodeMessage("OK", "Emetti provvedimento revoca abilitato!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }
        LOG.info(prf + "END");

        return Response.ok().entity(result).build();
    }

    @Override
    public Response emettiProvvedimentoRevoca(Long numeroGestioneRevoca, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroGestioneRevoca == null) {
                throw new ErroreGestitoException("Variabile numeroGestioneRevoca non valorizzata");
            }

            //emettiProvvedimentoRevoca
            //CHIAMATA A DAO --->>
            provvedimentoRevocaDAO.emettiProvvedimentoRevoca(numeroGestioneRevoca, giorniScadenza, req);
            result = new ResponseCodeMessage("OK", "Provvedimento di revoca emesso con successo!");
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
    public Response confermaProvvedimentoRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if(numeroGestioneRevoca == null) {
                throw new ErroreGestitoException("Variabile numeroGestioneRevoca non valorizzata");
            }

            //emettiProvvedimentoRevoca
            //CHIAMATA A DAO --->>
            provvedimentoRevocaDAO.confermaProvvedimentoRevoca(numeroGestioneRevoca, req);
            result = new ResponseCodeMessage("OK", "Provvedimento di revoca confermato con successo!");
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
    public Response ritiroInAutotutelaRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        Object result;

        try {
            //CONTROLLI
            if (numeroGestioneRevoca == null) {
                throw new ErroreGestitoException("Variabile numeroGestioneRevoca non valorizzata");
            }

            //emettiProvvedimentoRevoca
            //CHIAMATA A DAO --->>
            provvedimentoRevocaDAO.ritiroInAutotutelaRevoca(numeroGestioneRevoca, req);
            result = new ResponseCodeMessage("OK", "Provvedimento di revoca ritirato in autotutela con successo!");
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
    public Response aggiungiAllegato(Long numeroRevoca, MultipartFormDataInput multipartFormDataInput, HttpServletRequest req) throws Exception {
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
            String ambito = multipartFormDataInput.getFormDataPart("ambito", String.class, null);
            if(allegato == null)
                throw new ErroreGestitoException("Variabile allegato non valorizzata correttamente");
            if(nomeAllegato == null)
                throw new ErroreGestitoException("Variabile nomeAllegato non valorizzata correttamente");
            if(letteraAccompagnatoria == null)
                throw new ErroreGestitoException("Variabile letteraAccompagnatoria non valorizzata correttamente");
            if(ambito == null)
                throw new ErroreGestitoException("Variabile ambito non valorizzata correttamente");
            //CHIAMATA A DAO --->>

            int ambitoAllegato;
            switch (ambito) {
                case "ritiro":
                    ambitoAllegato = 1;
                    break;
                case "conferma":
                    ambitoAllegato = 2;
                    break;
                default:
                    ambitoAllegato = 0;
                    break;
            }
            provvedimentoRevocaDAO.aggiungiAllegato(numeroRevoca, letteraAccompagnatoria.equals("letteraAccompagnatoria"), ambitoAllegato, FileUtil.getBytesFromFile(allegato), nomeAllegato, req);
            result = new ResponseCodeMessage("OK", "Allegato aggiunto con successo!");
        }
        catch(Exception e){
            result = new ResponseCodeMessage("KO", e.getMessage());
            LOG.info(prf + e.getMessage());
        }

        LOG.info(prf + "END");
        return Response.ok().entity(result).build();
    }


    //UTILS
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
