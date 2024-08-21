/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.impl;

import it.csi.pbandi.pbservizit.exception.ContoEconomicoException;
import it.csi.pbandi.pbservizit.integration.vo.ContoEconomicoMaxDataFineVO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.EsitoFindContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO;

import java.math.BigDecimal;

public interface ContoeconomicoSrv {


  EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazione(Long idUtente, Integer idProcesso, String identitaDigitale, ProgettoDTO progetto)
      throws it.csi.csi.wrapper.CSIException, ContoEconomicoException;

  ContoEconomicoMaxDataFineVO getDatiUltimaProposta(ProgettoDTO progetto);

  ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStato(ProgettoDTO progetto, String stato);

  ContoEconomicoMaxDataFineVO getDatiUltimaRimodulazione(ProgettoDTO progetto);
  
}