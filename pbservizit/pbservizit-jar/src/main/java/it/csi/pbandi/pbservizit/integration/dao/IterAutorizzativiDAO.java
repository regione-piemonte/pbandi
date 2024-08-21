/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

public interface IterAutorizzativiDAO {
	
	// ITER AUTORIZZATIVO CDU PBAN-CDU-V01-ITERAUT002 Avvio iter autorizzativo
	String avviaIterAutorizzativo(Long idTipoIter, Long idEntita, Long idTarget, Long idProgetto, Long idUtente);
}
