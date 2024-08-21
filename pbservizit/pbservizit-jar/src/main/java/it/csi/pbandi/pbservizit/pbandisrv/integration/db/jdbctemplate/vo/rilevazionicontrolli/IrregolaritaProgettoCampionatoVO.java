/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;


import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class IrregolaritaProgettoCampionatoVO extends GenericVO {
	public BigDecimal annoContabile;
	public String asse;
	public String azione;
	public String bando;
	public String codiceProgetto;
	public String beneficiario;
	public String autoritaControllante;
	public String tipoControllo;
	public Date data_inizio_controllo;
	public Date data_fine_controllo;
	public String esitoProvvisorio;
	public BigDecimal importoSpessaIrregolare;
	public BigDecimal importoAgevolazioneIrregolare;
	public BigDecimal importoAgevolazioneCertificato;
	public String descMotivoRevocaProvv;
	public String esitoDefinitivo;
	public BigDecimal importoSpesaIrregolare;
	public BigDecimal importoAgevolazioneIrreg;
	public BigDecimal quotaImpIrregCertificato;
	public String descMotivoRevoca;
	public BigDecimal numeroIms;
	public String tipoProvvedimento;
	public String estremi;
	public BigDecimal importoRevoca;
	public BigDecimal importoRecupero;
	public Date dataRecupero;
	public String descMancatoRecupero;
	public BigDecimal getAnnoContabile() {
		return annoContabile;
	}
	public void setAnnoContabile(BigDecimal annoContabile) {
		this.annoContabile = annoContabile;
	}
	public String getAsse() {
		return asse;
	}
	public void setAsse(String asse) {
		this.asse = asse;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getBando() {
		return bando;
	}
	public void setBando(String bando) {
		this.bando = bando;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getAutoritaControllante() {
		return autoritaControllante;
	}
	public void setAutoritaControllante(String autoritaControllante) {
		this.autoritaControllante = autoritaControllante;
	}
	public String getTipoControllo() {
		return tipoControllo;
	}
	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}
	public Date getData_inizio_controllo() {
		return data_inizio_controllo;
	}
	public void setData_inizio_controllo(Date dataInizioControllo) {
		data_inizio_controllo = dataInizioControllo;
	}
	public Date getData_fine_controllo() {
		return data_fine_controllo;
	}
	public void setData_fine_controllo(Date dataFineControllo) {
		data_fine_controllo = dataFineControllo;
	}
	public String getEsitoProvvisorio() {
		return esitoProvvisorio;
	}
	public void setEsitoProvvisorio(String esitoProvvisorio) {
		this.esitoProvvisorio = esitoProvvisorio;
	}
	public BigDecimal getImportoSpessaIrregolare() {
		return importoSpessaIrregolare;
	}
	public void setImportoSpessaIrregolare(BigDecimal importoSpessaIrregolare) {
		this.importoSpessaIrregolare = importoSpessaIrregolare;
	}
	public BigDecimal getImportoAgevolazioneIrregolare() {
		return importoAgevolazioneIrregolare;
	}
	public void setImportoAgevolazioneIrregolare(
			BigDecimal importoAgevolazioneIrregolare) {
		this.importoAgevolazioneIrregolare = importoAgevolazioneIrregolare;
	}
	public BigDecimal getImportoAgevolazioneCertificato() {
		return importoAgevolazioneCertificato;
	}
	public void setImportoAgevolazioneCertificato(
			BigDecimal importoAgevolazioneCertificato) {
		this.importoAgevolazioneCertificato = importoAgevolazioneCertificato;
	}
	public String getDescMotivoRevocaProvv() {
		return descMotivoRevocaProvv;
	}
	public void setDescMotivoRevocaProvv(String descMotivoRevocaProvv) {
		this.descMotivoRevocaProvv = descMotivoRevocaProvv;
	}
	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}
	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}
	public BigDecimal getImportoSpesaIrregolare() {
		return importoSpesaIrregolare;
	}
	public void setImportoSpesaIrregolare(BigDecimal importoSpesaIrregolare) {
		this.importoSpesaIrregolare = importoSpesaIrregolare;
	}
	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}
	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}
	public BigDecimal getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}
	public void setQuotaImpIrregCertificato(BigDecimal quotaImpIrregCertificato) {
		this.quotaImpIrregCertificato = quotaImpIrregCertificato;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	public BigDecimal getNumeroIms() {
		return numeroIms;
	}
	public void setNumeroIms(BigDecimal numeroIms) {
		this.numeroIms = numeroIms;
	}
	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}
	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}
	public String getEstremi() {
		return estremi;
	}
	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}
	public BigDecimal getImportoRevoca() {
		return importoRevoca;
	}
	public void setImportoRevoca(BigDecimal importoRevoca) {
		this.importoRevoca = importoRevoca;
	}
	public BigDecimal getImportoRecupero() {
		return importoRecupero;
	}
	public void setImportoRecupero(BigDecimal importoRecupero) {
		this.importoRecupero = importoRecupero;
	}
	public Date getDataRecupero() {
		return dataRecupero;
	}
	public void setDataRecupero(Date dataRecupero) {
		this.dataRecupero = dataRecupero;
	}
	public String getDescMancatoRecupero() {
		return descMancatoRecupero;
	}
	public void setDescMancatoRecupero(String descMancatoRecupero) {
		this.descMancatoRecupero = descMancatoRecupero;
	}
}
