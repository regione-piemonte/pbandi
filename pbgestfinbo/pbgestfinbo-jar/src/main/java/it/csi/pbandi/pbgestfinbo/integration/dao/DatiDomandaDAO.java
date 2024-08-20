/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.SoggettiCorrelatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioPfVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.AnagraficaBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaEgVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDomandaVO;

public interface DatiDomandaDAO {

	List<DatiDomandaVO> getDatiDomanda(Long idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException;
	
	DatiDomandaEgVO getDatiDomandaEG(Long idSoggetto, String idDomanda, HttpServletRequest req) throws DaoException;

	List<SoggettiCorrelatiVO> getElencoSoggCorrDipDaDomanda(String idDomanda, Long idSoggetto);

	AnagraficaBeneficiarioPfVO getAnagSoggCorrDipDaDomPF(Long idSoggetto,String idDomanda, BigDecimal idSoggCorr);

	AnagraficaBeneficiarioVO getAnagSoggCorrDipDaDomEG(Long idSoggetto,String idDomanda, BigDecimal idSoggCorr);
	
	//// Modifica//////////
	
	List<AttivitaDTO> listRuoli();

	List<AttivitaDTO> getListaSuggest(String stringa, int id); 
	
	Boolean modificaSoggetto(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String idDomanda);
	Boolean modificaSoggettoEG(AnagraficaBeneficiarioVO soggetto, String idSoggetto, String idDomanda);

	Boolean updateDatiDomandaEG(DatiDomandaEgVO domandaEG, BigDecimal idUtente);

	Boolean checkProgetto(String numeroDomanda);

	Boolean updateDatiDomandaPF(DatiDomandaVO datiDomanda, BigDecimal idUtente);

	Boolean updateAnagBeneFisico(AnagraficaBeneficiarioPfVO soggetto, String idSoggetto, String numeroDomanda)
			throws ErroreGestitoException;

	Object getAltreSedi(Long idSoggetto, String idDomanda)throws DaoException ;

}
