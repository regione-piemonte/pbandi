/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.integration.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface ControlloPreErogazioneDAO {
	DatiPreErogazioneVO fetchData(Long idProposta) throws Exception;
	ControlloDurcVO getControlloDurc(Long idProgetto) throws Exception;
	ControlloAntimafiaVO getControlloAntimafia(Long idProgetto) throws Exception;
	ControlloDeggendorfVO getControlloDeggendorf(Long idSoggetto) throws Exception;
	String getControlloNonApplicabile(Long idProposta, Long idTipoRichiesta) throws Exception;
	Boolean controlloNonApplicabile(Long idProposta, Long idTipoRichiesta, String motivazione, HttpServletRequest req) throws Exception;
	Boolean saveControlliPreErogazione(Long idProposta, Long idSoggetto, Boolean esitoDeggendorf, String vercor, HttpServletRequest req) throws Exception;
	Boolean checkControlliPreErogazione(Long idPropostaErogazione, HttpServletRequest req);

	List<DestinatarioInterventoSostVO> getDestinatariInterventi();
	void inserisciInterventiSostitutivi(Long idProposta, List<InterventoSostitutivoVO> interventiSostitutivi, HttpServletRequest req);

	Long creaDistintaInterventiSostitutivi(Long idPropostaPadre, String descrizione, HttpServletRequest req);

	Long getIdProgetto(Long idPropostaErogazione);
	Long getIdEntitaPropostaErogazione();
	Long getIdEntitaDistinta();

	void aggiungiAllegato(BigDecimal idTarget, BigDecimal idEntita, Long idProgetto, BigDecimal idTipoDocumentoIndex, BigDecimal idUtente, byte[] file, String nomeFile, String visibilita);

	boolean salvaImportoDaErogare(Long idProposta, BigDecimal importoDaErogare, HttpServletRequest req);
}
