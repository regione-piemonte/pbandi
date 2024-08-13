/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import java.util.List;

import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaAssociatiAIstruttoreVO;
import it.csi.pbandi.pbwebbo.integration.vo.BandoLineaDaAssociareAIstruttoreVO;

public interface AssociazioneIstruttoreProgettoDAO {
	
	public List<BandoLineaAssociatiAIstruttoreVO> findBandoLineaIstruttore(Long idSoggetto, Boolean isIstruttoreAffidamenti);
	
	public BandoLineaAssociatiAIstruttoreVO conteggioIstruttoriAssociatiBandoLinea(Long idSoggetto, Long progBandoLina, Boolean isIstruttoreAffidamenti);
	
	public List<BandoLineaAssociatiAIstruttoreVO> dettaglioIstruttore(Long idSoggetto, Long progBandoLina);
	
	public List<BandoLineaDaAssociareAIstruttoreVO> getBandoLineaDaAssociareAIstruttoreMaster(Long idSoggetto, String descBreveTipoAnagrafica,  Long idSoggettoIstruttore);
	
	public int associaIstruttoreBandoLinea(Long idU, Long idSoggettoIstruttore, Long progBandoLinaIntervento, int tipoAnagrafica) throws Exception;
	
	public int eliminaAssocizioneIstruttoreBandoLinea(Long idU, Long idSoggettoIstruttore, Long progBandoLinaIntervento) throws Exception;

}
