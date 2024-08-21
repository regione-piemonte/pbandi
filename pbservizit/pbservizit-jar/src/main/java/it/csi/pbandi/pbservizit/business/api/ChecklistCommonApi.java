/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/checklist-common")
public interface ChecklistCommonApi {

	@POST
	@Path("/allegaFilesChecklist")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	Response allegaFilesChecklist(MultipartFormDataInput multipartFormData, @Context HttpServletRequest req)
			throws InvalidParameterException, Exception;

}
