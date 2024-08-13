/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.security.InvalidParameterException;

import it.csi.pbandi.pbwebbo.dto.gestionenews.AvvisoDTO;
import it.csi.pbandi.pbwebbo.dto.gestionenews.InizializzaGestioneNewsDTO;

public interface GestioneNewsDAO {
	
	InizializzaGestioneNewsDTO inizializzaGestioneNews(Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean aggiornaAvviso(AvvisoDTO avvisoDTO, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean eliminaAvviso(Long idNews, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	
}
