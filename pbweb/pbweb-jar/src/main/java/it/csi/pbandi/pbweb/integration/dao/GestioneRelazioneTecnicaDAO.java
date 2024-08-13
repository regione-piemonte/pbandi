/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public interface GestioneRelazioneTecnicaDAO {
	
	
	String getCodiceProgetto(Long idProgetto);

	Object salvaRelazioneTecnica(MultipartFormDataInput multipartFormData, HttpServletRequest req);

	Object getRelazioneTecnica(Long idProgetto, int idTipoRelazioneTecnica, HttpServletRequest req);

	Object validaRifiutaRelazioneTecnica(Long idRelazioneTecnica, String flagConferma, String nota, HttpServletRequest req);

}
