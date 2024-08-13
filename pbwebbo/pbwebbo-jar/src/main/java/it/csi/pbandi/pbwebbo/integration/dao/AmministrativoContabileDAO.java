/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.AnagraficaDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.BancaSuggestVO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.EstremiBancariEstesiDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModAgevEstremiBancariDTO;
import it.csi.pbandi.pbwebbo.dto.estremiBancari.ModalitaAgevolazioneDTO;

public interface AmministrativoContabileDAO {
	
	// 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
	public Long trackCallToAmministrativoContabilePre(
														Long idServizio, // 7 = AnagraficaFondo.Anagrafica, 8 = AnagraficaFondo.IbanFondo
														Long idUtente,
														String modalitaChiamata, // I = insert, U = update
														String parametriInput, // Concatenzaione chiave-valore
														String parametriOutput,
														Long idEntita, // Valorizzato a seconda del servizio chiamato, es: 5 se è stato chiamato il servizio AnagraficaFondo.Anagrafica, 128 se è stato chiamato il servizio AnagraficaFondo.IbanFondo
														Long idTarget // Valorizzato con l’identificativo relative all’ID_ENTITA, es: Coincide con l’ID_BANDO se è stato chiamato il servizio AnagraficaFondo.Anagrafica, Coincide con ID_ESTREMI_BANCARI se è stato chiamato il servizio AnagraficaFondo.IbanFondo

			) throws Exception;
	
	// 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile
	public void trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr, String parametriOutput) throws Exception;
	
	public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile( Long idAmmCont) throws Exception;
	
	
}
