/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import java.util.List;

import it.csi.pbandi.pbservwelfare.dto.InfoMensilita;

public interface EsposizioneMensilitaDAO {

	List<Integer> getListaProgetti(String numeroDomanda);

	List<InfoMensilita> recuperaMensilita(Integer idProgetto, Integer identificativoDichiarazioneDiSpesa);

}
