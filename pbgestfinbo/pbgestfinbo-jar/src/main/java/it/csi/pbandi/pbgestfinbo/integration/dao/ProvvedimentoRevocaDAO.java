/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.DocumentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaMiniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ProvvedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProvvedimentoRevocaDAO {

    List<ProvvedimentoRevocaMiniVO> getProvvedimentoRevoca(FiltroRevocaVO filtroRevocaVO);

    ProvvedimentoRevocaVO getDettaglioProvvedimentoRevoca(Long idProvvedimentoRevoca, Long idUtente);

    List<DocumentoRevocaVO> getDocumentiProvvedimentoRevoca(Long numeroRevoca);

    void modificaProvvedimentoRevoca(Long numeroRevoca, ProvvedimentoRevocaVO provvedimentoRevocaVO, HttpServletRequest req);

    void eliminaProvvedimentoRevoca(Long numeroRevoca, HttpServletRequest req);

    void verificaImportiTotaliRevoca(Long numeroRevoca, ProvvedimentoRevocaVO provvedimentoRevocaVO);
    void abilitaEmettiProvvedimento(Long numeroRevoca);
    void emettiProvvedimentoRevoca(Long numeroGestioneRevoca, Long giorniScadenza, HttpServletRequest req);

    void confermaProvvedimentoRevoca(Long numeroGestioneRevoca, HttpServletRequest req);

    void ritiroInAutotutelaRevoca(Long numeroGestioneRevoca, HttpServletRequest req);


    //UTILS
    void aggiungiAllegato(Long numeroRevoca, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req);
}
