/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto.EsitoOperazioneChiudiProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;

public interface ChiusuraProgettoDAO {

	Boolean isRinunciaPresente(Long idUtente, String idIride, Long idProgetto) throws Exception;

	Long[] getListaIdModalitaAgevolazioni(Long idUtente, String idIride, Long idProgetto) throws Exception;

	Boolean isProgettoAssociatoRegola(Long idUtente, String idIride, Long idProgetto, String codRegola) throws Exception;

	String getCausaleErogazionePerContoEconomicoLiquidazione(Long idUtente, String idIride, Long idProgetto,
			Long idModAg) throws Exception;

	String getCausaleErogazionePerContoEconomicoErogazione(Long idUtente, String idIride, Long idProgetto,
			Long idModAg) throws Exception;

	Long getIdProgettoAContributo(Long idUtente, String idIride, Long idProgetto) throws Exception;

	boolean checkErogazioneSaldoProgettoContributo(Long idUtente, String idIride, Long idProgettoAContributo) throws Exception;

	EsitoOperazioneChiudiProgetto chiudiProgetto(Long idUtente, String idIride, Long idProgetto, String note) throws Exception;

	EsitoOperazioneChiudiProgetto chiudiProgettoDiUfficio(Long idUtente, String idIride, Long idProgetto, String note) throws FormalParameterException, Exception;

}
