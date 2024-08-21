/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservrest.dto.RecuperoNote;



public interface GestioneNoteDAO {
	
	List<RecuperoNote>	getRecuperoNote(String codiceDomanda, String codiceBeneficiario, String agevolazione,
			String codiceFondo, String codiceFiscaleBeneficiario);


}
