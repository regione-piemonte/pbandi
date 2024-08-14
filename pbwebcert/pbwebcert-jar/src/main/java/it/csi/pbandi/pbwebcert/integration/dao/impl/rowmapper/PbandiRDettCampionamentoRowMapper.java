/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbwebcert.integration.vo.PbandiRDettCampionamentoVO;

public class PbandiRDettCampionamentoRowMapper implements RowMapper<PbandiRDettCampionamentoVO>{

	/*
	 * ID_CAMPIONE
	VALORE_MAX_AVANZAMENTO_U
	VALORE_MIN_AVANZAMENTO_U
	VALORE_MED_AVANZAMENTO_U
	VALORE_M75_AVANZAMENTO_U
	NUMEROSITA_CAMPIONE_U1
	NUMERO_UNITA_ESTRATTE_U1
	PROGR_OPERAZ_PRIMO_ESTRATTO_U1
	AVANZAMENTO_PRIMO_ESTRATTO_U1
	SOMMA_AVANZAMENTI_CAMPIONE_U1
	
	 * TOTALE_AVANZAMENTI_ESTRATTI_U1
	RAPPORTO_NUMEROSITA_U1
	VALORE_MAX_AVANZAMENTO_U2
	VALORE_MIN_AVANZAMENTO_U2
	
	NUMEROSITA_CAMPIONE_U2
	NUMERO_UNITA_ESTRATTE_U2
	RAPPORTO_NUMEROSITA_U2
	VALORE_V
	SOMMA_AVANZAMENTI_CAMPIONE_U2
	
	PROGR_OPERAZ_PRIMO_ESTRATTO_U2
	TOTALE_AVANZAMENTI_ESTRATTI_U2
	TOTALE_AVANZAMENTI_ESTRATTI_U
	SOMMA_AVANZAMENTI_CAMPIONE_U
	
	RAPPORTO_AVANZAMENTI_U
	NUMERO_UNITA_ESTRATTE_U
	NUMEROSITA_CAMPIONE_U
	RAPPORTO_NUMEROSITA_U
	 * */
	@Override
	public PbandiRDettCampionamentoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiRDettCampionamentoVO cm = new PbandiRDettCampionamentoVO();
		cm.setIdCampione(rs.getBigDecimal("ID_CAMPIONE"));
		cm.setValoreMaxAvanzamentoU(rs.getBigDecimal("VALORE_MAX_AVANZAMENTO_U"));
		cm.setValoreMinAvanzamentoU(rs.getBigDecimal("VALORE_MIN_AVANZAMENTO_U"));
		cm.setValoreMedAvanzamentoU(rs.getBigDecimal("VALORE_MED_AVANZAMENTO_U"));
		cm.setValoreM75AvanzamentoU(rs.getBigDecimal("VALORE_M75_AVANZAMENTO_U"));
		cm.setNumerositaCampioneU1(rs.getBigDecimal("NUMEROSITA_CAMPIONE_U1"));
		cm.setNumeroUnitaEstratteU1(rs.getBigDecimal("NUMERO_UNITA_ESTRATTE_U1"));
		cm.setProgrOperazPrimoEstrattoU1(rs.getBigDecimal("PROGR_OPERAZ_PRIMO_ESTRATTO_U1"));
		cm.setAvanzamentoPrimoEstrattoU1(rs.getBigDecimal("AVANZAMENTO_PRIMO_ESTRATTO_U1"));
		cm.setSommaAvanzamentiCampioneU1(rs.getBigDecimal("SOMMA_AVANZAMENTI_CAMPIONE_U1"));
		cm.setAvanzamentoPrimoEstrattoU2(rs.getBigDecimal("AVANZAMENTO_PRIMO_ESTRATTO_U2"));
		
		cm.setTotaleAvanzamentiEstrattiU1(rs.getBigDecimal("TOTALE_AVANZAMENTI_ESTRATTI_U1"));
		cm.setRapportoNumerositaU1(rs.getBigDecimal("RAPPORTO_NUMEROSITA_U1"));
		cm.setValoreMaxAvanzamentoU2(rs.getBigDecimal("VALORE_MAX_AVANZAMENTO_U2"));
		cm.setValoreMinAvanzamentoU2(rs.getBigDecimal("VALORE_MIN_AVANZAMENTO_U2"));
		
		cm.setNumerositaCampioneU2(rs.getBigDecimal("NUMEROSITA_CAMPIONE_U2"));
		cm.setNumeroUnitaEstratteU2(rs.getBigDecimal("NUMERO_UNITA_ESTRATTE_U2"));
		cm.setRapportoNumerositaU2(rs.getBigDecimal("RAPPORTO_NUMEROSITA_U2"));
		cm.setValoreV(rs.getBigDecimal("VALORE_V"));
		cm.setSommaAvanzamentiCampioneU2(rs.getBigDecimal("SOMMA_AVANZAMENTI_CAMPIONE_U2"));
		
		cm.setProgrOperazPrimoEstrattoU2(rs.getBigDecimal("PROGR_OPERAZ_PRIMO_ESTRATTO_U2"));
		cm.setTotaleAvanzamentiEstrattiU2(rs.getBigDecimal("TOTALE_AVANZAMENTI_ESTRATTI_U2"));
		cm.setTotaleAvanzamentiEstrattiU(rs.getBigDecimal("TOTALE_AVANZAMENTI_ESTRATTI_U"));
		cm.setSommaAvanzamentiCampioneU(rs.getBigDecimal("SOMMA_AVANZAMENTI_CAMPIONE_U"));
		
		cm.setRapportoAvanzamentiU(rs.getBigDecimal("RAPPORTO_AVANZAMENTI_U"));
		cm.setNumeroUnitaEstratteU(rs.getBigDecimal("NUMERO_UNITA_ESTRATTE_U"));
		cm.setNumerositaCampioneU(rs.getBigDecimal("NUMEROSITA_CAMPIONE_U"));
		cm.setRapportoNumerositaU(rs.getBigDecimal("RAPPORTO_NUMEROSITA_U"));
		
		
		return cm;
	}

}
