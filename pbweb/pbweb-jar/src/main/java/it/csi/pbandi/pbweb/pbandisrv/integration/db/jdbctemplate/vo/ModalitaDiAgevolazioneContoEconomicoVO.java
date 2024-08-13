/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;

import java.math.BigDecimal;

public class ModalitaDiAgevolazioneContoEconomicoVO extends
		PbandiTContoEconomicoVO {
	private BigDecimal idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private String flagLvlprj;
	private BigDecimal massimoImportoAgevolato;
	private BigDecimal percImportoAgevolatoBando;
	private BigDecimal quotaImportoAgevolato;
	private BigDecimal percImportoAgevolato;
	private BigDecimal quotaImportoRichiesto;
	private BigDecimal importoErogazioni;

	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}

	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}

	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}

	public String getFlagLvlprj() {
		return flagLvlprj;
	}

	public void setFlagLvlprj(String flagLvlprj) {
		this.flagLvlprj = flagLvlprj;
	}

	public BigDecimal getMassimoImportoAgevolato() {
		return massimoImportoAgevolato;
	}

	public void setMassimoImportoAgevolato(BigDecimal massimoImportoAgevolato) {
		this.massimoImportoAgevolato = massimoImportoAgevolato;
	}

	public BigDecimal getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}

	public void setQuotaImportoAgevolato(BigDecimal quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}

	public BigDecimal getPercImportoAgevolato() {
		return percImportoAgevolato;
	}

	public void setPercImportoAgevolato(BigDecimal percImportoAgevolato) {
		this.percImportoAgevolato = percImportoAgevolato;
	}

	public BigDecimal getQuotaImportoRichiesto() {
		return quotaImportoRichiesto;
	}

	public void setQuotaImportoRichiesto(BigDecimal quotaImportoRichiesto) {
		this.quotaImportoRichiesto = quotaImportoRichiesto;
	}

	public BigDecimal getImportoErogazioni() {
		return importoErogazioni;
	}

	public void setImportoErogazioni(BigDecimal importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}

	public void setPercImportoAgevolatoBando(BigDecimal percImportoAgevolatoBando) {
		this.percImportoAgevolatoBando = percImportoAgevolatoBando;
	}

	public BigDecimal getPercImportoAgevolatoBando() {
		return percImportoAgevolatoBando;
	}
}
