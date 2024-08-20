/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import it.csi.pbandi.pbgestfinbo.dto.EsitoDTO;
import it.csi.pbandi.pbgestfinbo.dto.GestioneAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.ModificaEscussioneRiassicurazioniDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_BeneficiarioDomandaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.Riassicurazione_RiassicurazioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.VisualizzaAllegatiVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.initGestioneEscussioneRiassicurazioniVO;

public interface GestioneRiassicurazioniDAO {
	
	// Cerca Riassicurazioni //

	List<Riassicurazione_BeneficiarioDomandaVO> ricercaBeneficiariRiassicurazioni(String descrizioneBando, String codiceProgetto, String codiceFiscale, String ndg, String partitaIva, String denominazioneCognomeNome, String statoEscussione, String denominazioneBanca) throws Exception;

	List<Riassicurazione_RiassicurazioniVO> getDettaglioRiassicurazioni(Long idProgetto, Long idRiassicurazione, Boolean getStorico) throws Exception;
	
	Boolean modificaRiassicurazione(Long idRiassicurazione, String importoEscusso, Date dataEscussione, Date dataScarico, Long idUtente) throws Exception;
	
	// Gestione Escussione Riassicurazioni //
	
	initGestioneEscussioneRiassicurazioniVO initGestioneEscussioneRiassicurazioni(Long idProgetto);
	
	EsitoDTO modificaEscussioneRiassicurazioni(ModificaEscussioneRiassicurazioniDTO dati, Boolean inserisciNuovo, Long idUtente) throws Exception;
	
	Boolean aggiornaAllegati(List<GestioneAllegatiVO> allegatiPresenti, Long idTarget);
	
	List<GestioneAllegatiVO> getAllegati(Long idEscussione);
}
