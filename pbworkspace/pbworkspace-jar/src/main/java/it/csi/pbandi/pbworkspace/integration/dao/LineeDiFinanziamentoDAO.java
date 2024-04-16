/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbworkspace.dto.BandoProcesso;
import it.csi.pbandi.pbworkspace.dto.EsitoAvviaProgettiDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaLineeDiFinanziamentoDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaSchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.dto.Progetto;
import it.csi.pbandi.pbworkspace.integration.request.AvviaProgettiRequest;
import it.csi.pbandi.pbworkspace.integration.request.RicercaProgettiRequest;

public interface LineeDiFinanziamentoDAO {

	InizializzaLineeDiFinanziamentoDTO inizializzaLineeDiFinanziamento(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	List<BandoProcesso> lineeDiFinanziamento(String descrizione, UserInfoSec userInfo) throws InvalidParameterException, Exception;
	List<Progetto> ricercaProgetti(RicercaProgettiRequest ricercaProgettiRequest, UserInfoSec userInfo) throws InvalidParameterException, Exception;
	EsitoAvviaProgettiDTO avviaProgetti(AvviaProgettiRequest avviaProgettiRequest, UserInfoSec userInfo) throws InvalidParameterException, Exception;
	
}
