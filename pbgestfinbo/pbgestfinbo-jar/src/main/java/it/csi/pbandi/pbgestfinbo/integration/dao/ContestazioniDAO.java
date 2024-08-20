/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.List;

import it.csi.pbandi.pbgestfinbo.integration.vo.AllegatiContestazioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.ContestazioniVO;

import javax.servlet.http.HttpServletRequest;

public interface ContestazioniDAO {
	List<ContestazioniVO> getContestazioni(Long idProgetto);
	List<String> getCodiceProgetto(Long idProgetto);
	List<AllegatiContestazioniVO> getAllegati(Long idContestazione);
	void inserisciLetteraAllegato(List<AllegatiContestazioniVO> allegati, Long idContestazione);
	Boolean deleteAllegato(Long idFileEntita);
	Boolean inserisciContestazione(Long idGestioneRevoca, HttpServletRequest req);
	Boolean inviaContestazione(Long idContestazione, HttpServletRequest req);
	Boolean eliminaContestazione(Long idContestazione, HttpServletRequest req);
}
