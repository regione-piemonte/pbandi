/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service;

import java.util.Date;

import javax.ws.rs.core.Response;

import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;

public interface AmministrativoContabileService {
	
	
	public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile( Long idAmmCont) throws Exception;
	
	public Long callToAnagraficaFondoAnagrafica (Long idBando, String titoloBando, String contropartita, Integer idNatura, 
			Long idEnteCompetenza, String note, long idUtente)  throws Exception;
	
	public Long callToAnagraficaFondoIbanFondo (long idBando, String iban, int idModalitaAgev, String tipologiaConto, 
			int moltiplicatore, String note, String fondoInps, long idUtente)  throws Exception;
	
	
}
