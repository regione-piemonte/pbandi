/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.dto.ProgettiEscludiEstrattiDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ElenchiProgettiCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.NuovoCampionamentoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoCampioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoEstrattoCampVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroAcqProgettiVO;

public interface NuovoCampionamentoDAO {

	List<ProgettoEstrattoCampVO> elaboraNuovoCampionamento(NuovoCampionamentoVO nuovoCampVO);

	Boolean estrazioneProgetti(ProgettiEscludiEstrattiDTO progetti, Long idUtente);

	 List<ProgettoEstrattoCampVO>campionaProgetti(NuovoCampionamentoVO nuovoCampVO, Long idUtente);
	 
	 BigDecimal getImportoValidatoTotale(NuovoCampionamentoVO nuovoCampVO);

	Boolean creaControlloLoco(ProgettiEscludiEstrattiDTO progetti, Long idUtente);

	Boolean annullaCampionamento(Long idCampionamento, Long idUtente);

	Object getNormative(String suggest);

	ElenchiProgettiCampionamentoVO acquisisciProgetti(FiltroAcqProgettiVO filtro, ArrayList<String> idProgettiList);

	Long checkCampionamento(Long numCampionamento);

	Object confermaAcquisizione(List<Long> progettiDaConfermare, FiltroAcqProgettiVO filtro, Long idUtente); 
	
	
	
	

}
