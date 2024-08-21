/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservrest.business.service.GestioneNoteApi;
import it.csi.pbandi.pbservrest.dto.RecuperoNote;
import it.csi.pbandi.pbservrest.integration.dao.GestioneNoteDAO;
import it.csi.pbandi.pbservrest.util.Constants;

@Transactional(timeout = 240, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
@Component
public class GestioneNoteApiImpl implements GestioneNoteApi {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	 GestioneNoteDAO gestioneNoteDAO;
	
	@Override
	public Response recuperoNote(String codiceDomanda, String codiceBeneficiario, String agevolazione,
			String codiceFondo, String codiceFiscaleBeneficiario, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String prf = "[GestioneNoteApiImpl::recuperoNote]";
		LOG.info(prf + " BEGIN");
		List<RecuperoNote> noteList = null;
		try {
			noteList = gestioneNoteDAO.getRecuperoNote(codiceDomanda, codiceBeneficiario, agevolazione,
					codiceFondo, codiceFiscaleBeneficiario);
		}catch (Exception e) {
			LOG.error(prf + "Exception while trying to read getRecuperoNote()", e);
			
		}finally {
			LOG.info(prf + " END");
		}
		
		return Response.ok(noteList).build();
	}

}
