/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.DocumentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.GestioneProrogaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaMiniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProcedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProcedimentoRevocaVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProcedimentoRevocaDAO {

    List<ProcedimentoRevocaMiniVO> getProcedimentoRevoca(FiltroProcedimentoRevocaVO filtroProcedimentoRevocaVO);

    ProcedimentoRevocaVO getDettaglioProcedimentoRevoca(Long idProcedimentoRevoca, Long idUtente);

    List<DocumentoRevocaVO> getDocumentiProcedimentoRevoca(Long numeroRevoca);

    Boolean eliminaAllegato(Long idDocumentoIndex);

    void updateProcedimentoRevoca(ProcedimentoRevocaVO procedimentoRevocaVO, HttpServletRequest req);

    void eliminaProcedimentoRevoca(Long numeroRevoca, HttpServletRequest req);

    void verificaImporti(Long idGestioneRevoca);

    void avviaProcedimentoRevoca(Long numeroProcedimentoRevoca, Long giorniScadenza, HttpServletRequest req) throws Exception;

    void inviaIncaricoAErogazione(Long numeroProcedimentoRevoca, Long numeroDichiarazioneSpesa, BigDecimal importoDaErogareContributo, BigDecimal importoDaErogareFinanziamento, BigDecimal importoIres, Long giorniScadenza, HttpServletRequest req) throws Exception;

    void aggiungiAllegato(Long idGestioneRevoca, byte[] allegato, String nomeAllegato, Boolean letteraAccompagnatoria, Boolean allegatoIntegrazione, Boolean allegatoArchiviazione, HttpServletRequest req) throws IOException;

    void archiviaProcedimentoRevoca(Long numeroProcedimentoRevoca, String note, HttpServletRequest req) throws Exception;

    void salvaNoteArchiviazione(Long idGestioneRevoca, String note, HttpServletRequest req) throws Exception;

    /*PROROGHE*/

    GestioneProrogaVO getRichiestaProroga(Long numeroProcedimentoRevoca);

    void updateRichiestaProroga(Long numeroProcedimentoRevoca, Boolean esitoRichiestaProroga, Long giorniApprovati, HttpServletRequest req);

    /*INTEGRAZIONI*/

    Boolean abilitaRichiediIntegrazione(Long idGestioneRevoca);

    Boolean abilitaAvviaIterIntegrazione(Long idUtente);

    Long creaRichiestaIntegrazione(Long idGestioneRevoca, Long numGiorniScadenza, HttpServletRequest req);

    Boolean abilitaChiudiIntegrazione(Long idGestioneRevoca);

    void chiudiRichiestaIntegrazione(Long idGestioneRevoca, HttpServletRequest req) throws Exception;
}
