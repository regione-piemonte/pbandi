/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl.boxAttivitaIstruttore;

import it.csi.pbandi.pbgestfinbo.business.service.boxAttivitaIstruttore.BoxAttivitaIstruttoreService;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileUtil;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.boxAttivitaIstruttore.BoxSaldoStralcioDAO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.util.Objects;

@Service
public class BoxAttivitaIstruttoreServiceImpl implements BoxAttivitaIstruttoreService {

    @Autowired
    private BoxSaldoStralcioDAO saldoStralcioDao;

    @Override
    public Response getSaldoStralcio(Long idProgetto, int idModalitaAgevolazione, HttpServletRequest req) throws Exception {
        return Response.ok().entity(saldoStralcioDao.getSaldoStralcio(idProgetto, idModalitaAgevolazione)).build();
    	//return Response.ok().entity("Test OK").build();
    }

    @Override
    public Response initSaldoStralcio(HttpServletRequest req) throws Exception {
        return Response.ok().entity(saldoStralcioDao.initSaldoStralcio()).build();
        //return Response.ok().entity("Test OK").build();
    }
}
