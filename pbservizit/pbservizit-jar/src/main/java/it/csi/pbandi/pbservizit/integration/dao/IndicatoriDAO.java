/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.util.ArrayList;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.TipoIndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.dto.indicatori.EsitoSaveIndicatori;
import it.csi.pbandi.pbservizit.dto.indicatori.IndicatoreItem;
import it.csi.pbandi.pbservizit.dto.indicatori.InfoIndicatore;

public interface IndicatoriDAO {

	TipoIndicatoreDTO[] findIndicatoriGestione(Long idUtente, String idIride, Long idProgetto, boolean isMonitoraggio)
			throws FormalParameterException, UnrecoverableException;

	ArrayList<IndicatoreItem> findIndicatoriGestioneSif(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException;

	Boolean esisteCFP(UserInfoSec userInfo, Long idProgetto) throws FormalParameterException, UnrecoverableException;

	Boolean esisteDsFinale(UserInfoSec userInfo, Long idProgetto)
			throws FormalParameterException, UnrecoverableException;

	String findCodiceProgetto(Long idProgetto);

//	Boolean eliminaIndicatoriDomanda(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException, UnrecoverableException, Exception;

	EsitoSaveIndicatori saveIndicatoriGestione(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] listaIndicatori) throws FormalParameterException, UnrecoverableException, Exception;

	InfoIndicatore getInfoIndicatore(Long idBando, Long idIndicatore);

	TipoIndicatoreDTO[] findIndicatoriAvvio(Long idUtente, String idIride, Long idProgetto, boolean isMonitoraggio)
			throws FormalParameterException, UnrecoverableException;

	EsitoSaveIndicatori saveIndicatoriAvvio(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] listaIndicatori) throws FormalParameterException, Exception;

	EsitoSaveIndicatori deleteDomandaIndicatori(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] listaIndicatori) throws Exception;

}
