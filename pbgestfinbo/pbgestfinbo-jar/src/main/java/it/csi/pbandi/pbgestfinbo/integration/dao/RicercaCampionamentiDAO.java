/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.util.List;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRicercaCampionamentiDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.RicercaCampionamentiVO;

public interface RicercaCampionamentiDAO {
	
	List<AttivitaDTO> getListaTipolgie();
	List<AttivitaDTO> getListaStati(); 
	List<StoricoRicercaCampionamentiDTO> getListaCampionamenti( RicercaCampionamentiVO campioneVO);
	List<AttivitaDTO> getListaUtenti(String string);
	List<AttivitaDTO> getListaBandi(String string);
	List<AttivitaDTO> getTipoDichiaraziSpesa();

}
