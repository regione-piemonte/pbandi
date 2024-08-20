/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.AllegatiControdeduzioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.ControdeduzioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.ProgettoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.RichiestaProrogaVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GestioneControdeduzioniDAO {

	List<AllegatiControdeduzioniVO> getAllegati(Long idControdeduz);

	Boolean inserisciAllegati(Long idControdeduz, List<AllegatiControdeduzioniVO> allegati);

	Boolean deleteAllegato(Long idFileEntita);

	//NEW
	List<ProgettoVO> getIntestazioneControdeduzioni(Long idProgetto);

	List<ControdeduzioneVO> getControdeduzioni(Long idProgetto);

	Boolean insertControdeduz(Long idGestioneRevoca, HttpServletRequest req);

	Boolean richiediAccessoAtti(Long idControdeduz, HttpServletRequest req);

	Boolean inviaControdeduz(Long idControdeduz, HttpServletRequest req);

	Boolean deleteControdeduz(Long idControdeduz, HttpServletRequest req);

	List<RichiestaProrogaVO> getRichiestaProroga(Long idControdeduz);

	Boolean richiediProroga(Long idControdeduz, RichiestaProrogaVO richiestaProrogaVO, HttpServletRequest req);
}
