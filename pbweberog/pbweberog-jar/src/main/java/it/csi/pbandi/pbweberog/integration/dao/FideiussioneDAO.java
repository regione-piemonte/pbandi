/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneDTO;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneEsitoGenericoDTO;

public interface FideiussioneDAO {

	FideiussioneDTO[] findFideiussioni(Long idUtente, String idIride, Long idProgetto,
			FideiussioneDTO filtro) throws UnrecoverableException;

	FideiussioneEsitoGenericoDTO eliminaFideiussione(Long idUtente, String idIride, Long idFideiussione) throws UnrecoverableException;

	FideiussioneEsitoGenericoDTO aggiornaInserisciFideiussione(Long idUtente, String idIride, FideiussioneDTO dto) throws UnrecoverableException;

	FideiussioneDTO dettaglioFideiussione(Long idUtente, String idIride, Long idFideiussione) throws UnrecoverableException;

	EsitoOperazioni isProgettoGestibile(Long idUtente, String idIride, Long idProgetto) throws UnrecoverableException;

}
