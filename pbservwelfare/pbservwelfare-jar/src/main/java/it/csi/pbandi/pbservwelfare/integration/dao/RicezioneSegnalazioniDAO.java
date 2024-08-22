/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.HashMap;

import it.csi.pbandi.pbservwelfare.dto.InfoDaNumeroDomanda;

public interface RicezioneSegnalazioniDAO {

	InfoDaNumeroDomanda getInfo(String numeroDomanda);

	HashMap<String, String> getInfoTemplate(String descBreveTemplate);

	int insertNotifica(Integer idProgetto, String subjectNotifica, String messageNotifica, Integer idTemplateNotifica);

}
