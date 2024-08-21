/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;

public interface AttivitaDAO {

	String chiudiAttivita(Long idUtente, String idIride, String descBreveTask, Long idProgetto) throws NeoFluxException, SystemException, UnrecoverableException, CSIException;

}