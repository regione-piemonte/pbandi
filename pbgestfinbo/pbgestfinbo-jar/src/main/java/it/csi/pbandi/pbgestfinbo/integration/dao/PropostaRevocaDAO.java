/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.PropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.DataPropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.ImportiPropostaRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.NomeCognomeIstruttore;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.NotaRevocaVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PropostaRevocaDAO {
    List<PropostaRevocaVO> getProposteRevoche(Long idSoggetto, Long idBando, Long idProgetto, Long idCausaleBlocco, Long idStatoRevoca, Long numeroPropostaRevoca, Date dataPropostaRevocaFrom, Date dataPropostaRevocaTo, HttpServletRequest req) throws Exception;
    DataPropostaRevocaVO getInfoRevoche(Long idGestioneRevoca, Long idSoggetto)throws Exception;
    NomeCognomeIstruttore getNomeCognomeIstruttore(Long idGestioneRevoca)throws Exception;
    List<ImportiPropostaRevocaVO> getImportiRevoche(Long idGestioneRevoca, Long idUtente)throws Exception;
    NotaRevocaVO getNotaRevoche (Long idGestioneRevoca)throws Exception;
    void updateNotaRevoche(Long idGestioneRevoca, String nota, Long idUtente)throws Exception;
    void disableCurrentGestioneRevoca(Long idGestioneRevoca, Long idUtente)throws Exception;
    Long cloneGestioneRevoca(Long idGestioneRevoca)throws Exception;
    void spostaAllegatoRevoca(Long idGestioneRevoca, Long newIdGestioneRevoca)throws Exception;
    void updateRevoca(Long idGestioneRevoca, String note, Long idTipologiaRevoca, Long idStatoRevoca, Long idUtente)throws Exception;
    Long creaPropostaRevoca(Long numeroRevoca, Long idProgetto, Long idCausaleBlocco, Long idAutoritaControllante, Date dataPropostaRevoca, Long idUtente)throws Exception;
    void verificaInserimentoPropostaRevoca(Date dataPropostaRevoca, Long idProgetto) throws Exception;
}
