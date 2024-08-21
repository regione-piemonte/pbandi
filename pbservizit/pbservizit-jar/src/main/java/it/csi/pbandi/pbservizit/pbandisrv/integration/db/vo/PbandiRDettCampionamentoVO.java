/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.BigDecimal;
import java.util.List;

public class PbandiRDettCampionamentoVO extends GenericVO{
	/*
	ID_CAMPIONE
	VALORE_MAX_AVANZAMENTO_U
	VALORE_MIN_AVANZAMENTO_U
	VALORE_MED_AVANZAMENTO_U
	VALORE_M75_AVANZAMENTO_U
	NUMEROSITA_CAMPIONE_U1
	NUMERO_UNITA_ESTRATTE_U1
	PROGR_OPERAZ_PRIMO_ESTRATTO_U1
	AVANZAMENTO_PRIMO_ESTRATTO_U1
	SOMMA_AVANZAMENTI_CAMPIONE_U1
	
	TOTALE_AVANZAMENTI_ESTRATTI_U1
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
	*/
	
	private BigDecimal idCampione;
	private BigDecimal valoreMaxAvanzamentoU;
	private BigDecimal valoreMinAvanzamentoU;
	private BigDecimal valoreMedAvanzamentoU;
	private BigDecimal valoreM75AvanzamentoU;
	private BigDecimal numerositaCampioneU1;
	private BigDecimal numeroUnitaEstratteU1;
	private BigDecimal progrOperazPrimoEstrattoU1;
	private BigDecimal avanzamentoPrimoEstrattoU1;
	private BigDecimal sommaAvanzamentiCampioneU1;
	private BigDecimal totaleAvanzamentiEstrattiU1;
	private BigDecimal rapportoNumerositaU1;
	private BigDecimal valoreMaxAvanzamentoU2;
	private BigDecimal valoreMinAvanzamentoU2;
	private BigDecimal numerositaCampioneU2;
	private BigDecimal numeroUnitaEstratteU2;
	private BigDecimal rapportoNumerositaU2;
	private BigDecimal valoreV;
	private BigDecimal sommaAvanzamentiCampioneU2;
	private BigDecimal progrOperazPrimoEstrattoU2;
	private BigDecimal totaleAvanzamentiEstrattiU2;
	private BigDecimal totaleAvanzamentiEstrattiU;
	private BigDecimal sommaAvanzamentiCampioneU;
	private BigDecimal rapportoAvanzamentiU;
	private BigDecimal numeroUnitaEstratteU;
	private BigDecimal numerositaCampioneU;
	private BigDecimal rapportoNumerositaU;
	private BigDecimal avanzamentoPrimoEstrattoU2;

	public BigDecimal getIdCampione() {
		return idCampione;
	}

	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}

	public BigDecimal getValoreMaxAvanzamentoU() {
		return valoreMaxAvanzamentoU;
	}

	public void setValoreMaxAvanzamentoU(BigDecimal valoreMaxAvanzamentoU) {
		this.valoreMaxAvanzamentoU = valoreMaxAvanzamentoU;
	}

	public BigDecimal getValoreMinAvanzamentoU() {
		return valoreMinAvanzamentoU;
	}

	public void setValoreMinAvanzamentoU(BigDecimal valoreMinAvanzamentoU) {
		this.valoreMinAvanzamentoU = valoreMinAvanzamentoU;
	}

	public BigDecimal getValoreMedAvanzamentoU() {
		return valoreMedAvanzamentoU;
	}

	public void setValoreMedAvanzamentoU(BigDecimal valoreMedAvanzamentoU) {
		this.valoreMedAvanzamentoU = valoreMedAvanzamentoU;
	}

	public BigDecimal getValoreM75AvanzamentoU() {
		return valoreM75AvanzamentoU;
	}

	public void setValoreM75AvanzamentoU(BigDecimal valoreM75AvanzamentoU) {
		this.valoreM75AvanzamentoU = valoreM75AvanzamentoU;
	}

	public BigDecimal getNumerositaCampioneU1() {
		return numerositaCampioneU1;
	}

	public void setNumerositaCampioneU1(BigDecimal numerositaCampioneU1) {
		this.numerositaCampioneU1 = numerositaCampioneU1;
	}

	public BigDecimal getNumeroUnitaEstratteU1() {
		return numeroUnitaEstratteU1;
	}

	public void setNumeroUnitaEstratteU1(BigDecimal numeroUnitaEstratteU1) {
		this.numeroUnitaEstratteU1 = numeroUnitaEstratteU1;
	}

	public BigDecimal getProgrOperazPrimoEstrattoU1() {
		return progrOperazPrimoEstrattoU1;
	}

	public void setProgrOperazPrimoEstrattoU1(BigDecimal progrOperazPrimoEstrattoU1) {
		this.progrOperazPrimoEstrattoU1 = progrOperazPrimoEstrattoU1;
	}

	public BigDecimal getAvanzamentoPrimoEstrattoU1() {
		return avanzamentoPrimoEstrattoU1;
	}

	public void setAvanzamentoPrimoEstrattoU1(BigDecimal avanzamentoPrimoEstrattoU1) {
		this.avanzamentoPrimoEstrattoU1 = avanzamentoPrimoEstrattoU1;
	}

	public BigDecimal getSommaAvanzamentiCampioneU1() {
		return sommaAvanzamentiCampioneU1;
	}

	public void setSommaAvanzamentiCampioneU1(BigDecimal sommaAvanzamentiCampioneU1) {
		this.sommaAvanzamentiCampioneU1 = sommaAvanzamentiCampioneU1;
	}

	public BigDecimal getTotaleAvanzamentiEstrattiU1() {
		return totaleAvanzamentiEstrattiU1;
	}

	public void setTotaleAvanzamentiEstrattiU1(
			BigDecimal totaleAvanzamentiEstrattiU1) {
		this.totaleAvanzamentiEstrattiU1 = totaleAvanzamentiEstrattiU1;
	}

	public BigDecimal getRapportoNumerositaU1() {
		return rapportoNumerositaU1;
	}

	public void setRapportoNumerositaU1(BigDecimal rapportoNumerositaU1) {
		this.rapportoNumerositaU1 = rapportoNumerositaU1;
	}

	public BigDecimal getValoreMaxAvanzamentoU2() {
		return valoreMaxAvanzamentoU2;
	}

	public void setValoreMaxAvanzamentoU2(BigDecimal valoreMaxAvanzamentoU2) {
		this.valoreMaxAvanzamentoU2 = valoreMaxAvanzamentoU2;
	}

	public BigDecimal getValoreMinAvanzamentoU2() {
		return valoreMinAvanzamentoU2;
	}

	public void setValoreMinAvanzamentoU2(BigDecimal valoreMinAvanzamentoU2) {
		this.valoreMinAvanzamentoU2 = valoreMinAvanzamentoU2;
	}

	public BigDecimal getNumerositaCampioneU2() {
		return numerositaCampioneU2;
	}

	public void setNumerositaCampioneU2(BigDecimal numerositaCampioneU2) {
		this.numerositaCampioneU2 = numerositaCampioneU2;
	}

	public BigDecimal getNumeroUnitaEstratteU2() {
		return numeroUnitaEstratteU2;
	}

	public void setNumeroUnitaEstratteU2(BigDecimal numeroUnitaEstratteU2) {
		this.numeroUnitaEstratteU2 = numeroUnitaEstratteU2;
	}

	public BigDecimal getRapportoNumerositaU2() {
		return rapportoNumerositaU2;
	}

	public void setRapportoNumerositaU2(BigDecimal rapportoNumerositaU2) {
		this.rapportoNumerositaU2 = rapportoNumerositaU2;
	}

	public BigDecimal getValoreV() {
		return valoreV;
	}

	public void setValoreV(BigDecimal valoreV) {
		this.valoreV = valoreV;
	}

	public BigDecimal getSommaAvanzamentiCampioneU2() {
		return sommaAvanzamentiCampioneU2;
	}

	public void setSommaAvanzamentiCampioneU2(BigDecimal sommaAvanzamentiCampioneU2) {
		this.sommaAvanzamentiCampioneU2 = sommaAvanzamentiCampioneU2;
	}

	public BigDecimal getProgrOperazPrimoEstrattoU2() {
		return progrOperazPrimoEstrattoU2;
	}

	public void setProgrOperazPrimoEstrattoU2(BigDecimal progrOperazPrimoEstrattoU2) {
		this.progrOperazPrimoEstrattoU2 = progrOperazPrimoEstrattoU2;
	}

	public BigDecimal getTotaleAvanzamentiEstrattiU2() {
		return totaleAvanzamentiEstrattiU2;
	}

	public void setTotaleAvanzamentiEstrattiU2(
			BigDecimal totaleAvanzamentiEstrattiU2) {
		this.totaleAvanzamentiEstrattiU2 = totaleAvanzamentiEstrattiU2;
	}

	public BigDecimal getTotaleAvanzamentiEstrattiU() {
		return totaleAvanzamentiEstrattiU;
	}

	public void setTotaleAvanzamentiEstrattiU(BigDecimal totaleAvanzamentiEstrattiU) {
		this.totaleAvanzamentiEstrattiU = totaleAvanzamentiEstrattiU;
	}

	public BigDecimal getSommaAvanzamentiCampioneU() {
		return sommaAvanzamentiCampioneU;
	}

	public void setSommaAvanzamentiCampioneU(BigDecimal sommaAvanzamentiCampioneU) {
		this.sommaAvanzamentiCampioneU = sommaAvanzamentiCampioneU;
	}

	public BigDecimal getRapportoAvanzamentiU() {
		return rapportoAvanzamentiU;
	}

	public void setRapportoAvanzamentiU(BigDecimal rapportoAvanzamentiU) {
		this.rapportoAvanzamentiU = rapportoAvanzamentiU;
	}

	public BigDecimal getNumeroUnitaEstratteU() {
		return numeroUnitaEstratteU;
	}

	public void setNumeroUnitaEstratteU(BigDecimal numeroUnitaEstratteU) {
		this.numeroUnitaEstratteU = numeroUnitaEstratteU;
	}

	public BigDecimal getNumerositaCampioneU() {
		return numerositaCampioneU;
	}

	public void setNumerositaCampioneU(BigDecimal numerositaCampioneU) {
		this.numerositaCampioneU = numerositaCampioneU;
	}

	public BigDecimal getRapportoNumerositaU() {
		return rapportoNumerositaU;
	}

	public void setRapportoNumerositaU(BigDecimal rapportoNumerositaU) {
		this.rapportoNumerositaU = rapportoNumerositaU;
	}

	@Override
	public List getPK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPKValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public BigDecimal getAvanzamentoPrimoEstrattoU2() {
		return avanzamentoPrimoEstrattoU2;
	}

	public void setAvanzamentoPrimoEstrattoU2(BigDecimal avanzamentoPrimoEstrattoU2) {
		this.avanzamentoPrimoEstrattoU2 = avanzamentoPrimoEstrattoU2;
	}
}
