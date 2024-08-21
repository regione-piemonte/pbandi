/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.dto.help.HelpDTO;

public interface HelpDAO {

	public Boolean getFlagEditHelpUser(Long idUtente, String idIride) throws Exception;

	public Boolean hasTipoAnagHelp(String codiceRuolo, Long idUtente, String idIride) throws Exception;

	public Boolean esisteHelpByPaginaAndTipoAnag(String descBrevePagina, String codiceRuolo, Long idUtente,
			String idIride) throws Exception;

	public HelpDTO getHelpByPaginaAndTipoAnag(String descBrevePagina, String codiceRuolo, Long idUtente, String idIride)
			throws Exception;

	public EsitoOperazioni saveHelp(String descBrevePagina, HelpDTO helpDTO, String codiceRuolo, Long idUtente,
			String idIride) throws Exception;

	public EsitoOperazioni deleteHelp(Long idHelp, Long idUtente, String idIride) throws Exception;

}
