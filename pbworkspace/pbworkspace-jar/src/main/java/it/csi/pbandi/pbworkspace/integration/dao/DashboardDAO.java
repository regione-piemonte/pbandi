/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.pbandi.pbworkspace.dto.BandoWidgetDTO;
import it.csi.pbandi.pbworkspace.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbworkspace.dto.WidgetDTO;
import it.csi.pbandi.pbworkspace.exception.DaoException;

public interface DashboardDAO {

	Boolean isDashboardVisible(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception;

	Boolean areWidgetsConfigured(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception;

	List<WidgetDTO> getWidgets(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception;

	List<BandoWidgetDTO> getBandiDaAssociare(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception;

	List<BandoWidgetDTO> getBandiAssociati(Long idWidget, String codiceRuolo, Long idSoggetto, Long idUtente,
			String idIride) throws InvalidParameterException, DaoException, Exception;

	EsitoOperazioneDTO changeWidgetAttivo(Long idWidget, Boolean flagWidgetAttivo, String codiceRuolo, Long idSoggetto,
			Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception;

	EsitoOperazioneDTO associaBandoAWidget(Long idWidget, Long progrBandoLineaIntervento, String codiceRuolo,
			Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception;

	EsitoOperazioneDTO disassociaBandoAWidget(Long idBandoLinSoggWidget, String codiceRuolo, Long idSoggetto,
			Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception;

}
