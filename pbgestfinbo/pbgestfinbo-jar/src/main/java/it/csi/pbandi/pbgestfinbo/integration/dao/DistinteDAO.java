/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.DettaglioDistintaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.DistintaPropostaErogazioneVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RicercaDistintaPropErogPlusVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.RicercaDistintaPropErogVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDistintaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProposteErogazioneDistVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface DistinteDAO {

    Boolean nuovaDistinta(Long idBando, Long idModalitaAgevolazione, HttpServletRequest req);

    Boolean existsDistinta(Long idDistinta);

    DettaglioDistintaVO copiaDistinta(Long idDistinta, HttpServletRequest req);

    Boolean modificaDistinta(Long idDistinta, DatiDistintaVO datiDistintaVO, HttpServletRequest req);

    DettaglioDistintaVO getNuovaDistinta(Long idBando, Long idModalitaAgevolazione);

    List<DistintaPropostaErogazioneVO> getProposteErogazione(FiltroProposteErogazioneDistVO filtroProposteErogazioneDistVO);

    DettaglioDistintaVO getDettaglioDistinta(Long idDistinta, HttpServletRequest req);

    Long salvaInBozza(DatiDistintaVO datiDistintaVO, HttpServletRequest req);

    Boolean isAbilitatoAvviaIter(HttpServletRequest req);

    Boolean checkAllegatiDistinta(Long idDistinta);
    void avviaIterAutorizzativo(Long idDistinta, HttpServletRequest req);

    void salvaAllegato(byte[] file, String visibilita, String nomeFile, Long idTipoDocumentoIndex, Long idDistinta, Long idProgetto, HttpServletRequest req);
    boolean eliminaAllegato(Long idTipoDocumentoIndex);

    boolean updateVisibilita(Long idDocumentoIndex, String visibilita);
    List<RicercaDistintaPropErogVO> filterDistinte(Date dataCreazioneFrom, Date dataCreazioneTo, Long idBando, Long idAgevolazione, Long idDistinta, HttpServletRequest req);

    List<RicercaDistintaPropErogPlusVO> filterDistinte(Date dataCreazioneFrom, Date dataCreazioneTo, Long idBeneficiario,
                                                       Long idBando, Long idAgevolazione, Long idProgetto, Long idDistinta, HttpServletRequest req);

    XSSFWorkbook esportaDettaglioDistinta(Long idDistinta);
}
