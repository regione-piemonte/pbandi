/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.util.List;

import it.csi.pbandi.pbwebbo.dto.configuraBando.DataModalitaAgevolazioneDTO;
import it.csi.pbandi.pbwebbo.integration.vo.NaturaCipeVO;

public interface ConfigurazioneBandoDAO {

	public List<DataModalitaAgevolazioneDTO> findModalitaDiAgevolazioneAssociate(Long idBando) throws Exception;

	public boolean modalitaAgevolazioneHasEstremiBancariAssociati(Long idBando, Long idModalitaAgevolazione)
			throws Exception;

	public List<NaturaCipeVO> findNatureCipe() throws Exception;

	public Object getModalitaAgevErogazone();

}
