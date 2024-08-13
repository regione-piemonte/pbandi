/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbweb.integration.vo.IntegrazioniControlliInLocoVO;

public interface IntegrazioniControlliInLocoDAO {
	/*List <ContestazioniVO> getContestazioni( Long idProgetto ,HttpServletRequest req);*/
	
	
	List<IntegrazioniControlliInLocoVO> getAllegati(int idIntegrazione);

	Boolean insertFileEntita(List<IntegrazioniControlliInLocoVO> allegati, Long idContestazione);

	Boolean aggiornaIntegrazione(Long idUtente, int idIntegrazione);

	Boolean deleteAllegato(int idFileEntita);

	List<IntegrazioniControlliInLocoVO> getIntegrazioni(int idControllo, int idEntita);

	List<Integer> getIdControlloLoco(int idProgetto);

	List<Integer> getIdControllo(int idProgetto);

	IntegrazioniControlliInLocoVO getAbilitaRichProroga(int idControllo, int idEntita);

	List<IntegrazioniControlliInLocoVO> getStatoProroga(int idIntegrazione);

	Boolean inserisciRichProroga(int ggRichiesti, String motivazione, Long idIntegrazione, Long idUtente);

	IntegrazioniControlliInLocoVO getLettera(int idIntegrazione);


}
