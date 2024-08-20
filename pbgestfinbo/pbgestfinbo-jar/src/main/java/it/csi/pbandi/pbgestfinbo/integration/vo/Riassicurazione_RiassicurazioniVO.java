/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class Riassicurazione_RiassicurazioniVO {
	
	private Long idProgetto;
	private Long idRiassicurazione;

	private String lineaInterventoSogg;
	private String ragioneSociale;
	private String formaGiuridica;
	private String descFormaGiuridica;
	private String codiceFiscale;
	private String indirizzoSedeDestinatario;
	private String localitaSedeDestinatario;
	private String provinciaSedeDestinatario;
	private String ateco;
	private String descAteco;
	private BigDecimal importoFinanziato;
	private BigDecimal importoGaranzia;
	private BigDecimal importoAmmesso;
	private BigDecimal importoEscusso;
	private BigDecimal percentualeGaranzia;
	private BigDecimal percentualeRiassicurato;
	private Date dataErogazioneMutuo;
	private Date dataDeliberaMutuo;
	private Date dataEmissioneGaranzia;
	private Date dataScadenzaMutuo;
	private Date dataScarico;
	private Date dataRevoca;
	private Date dataEscussione;
	
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String utenteIserito;
	
	
	
	public Riassicurazione_RiassicurazioniVO(Long idProgetto, Long idRiassicurazione, String lineaInterventoSogg,
			String ragioneSociale, String formaGiuridica, String descFormaGiuridica, String codiceFiscale,
			String indirizzoSedeDestinatario, String localitaSedeDestinatario, String provinciaSedeDestinatario,
			String ateco, String descAteco, BigDecimal importoFinanziato, BigDecimal importoGaranzia,
			BigDecimal importoAmmesso, BigDecimal importoEscusso, BigDecimal percentualeGaranzia,
			BigDecimal percentualeRiassicurato, Date dataErogazioneMutuo, Date dataDeliberaMutuo,
			Date dataEmissioneGaranzia, Date dataScadenzaMutuo, Date dataScarico, Date dataRevoca, Date dataEscussione,
			Date dataInizioValidita, Date dataFineValidita, String utenteIserito) {
		this.idProgetto = idProgetto;
		this.idRiassicurazione = idRiassicurazione;
		this.lineaInterventoSogg = lineaInterventoSogg;
		this.ragioneSociale = ragioneSociale;
		this.formaGiuridica = formaGiuridica;
		this.descFormaGiuridica = descFormaGiuridica;
		this.codiceFiscale = codiceFiscale;
		this.indirizzoSedeDestinatario = indirizzoSedeDestinatario;
		this.localitaSedeDestinatario = localitaSedeDestinatario;
		this.provinciaSedeDestinatario = provinciaSedeDestinatario;
		this.ateco = ateco;
		this.descAteco = descAteco;
		this.importoFinanziato = importoFinanziato;
		this.importoGaranzia = importoGaranzia;
		this.importoAmmesso = importoAmmesso;
		this.importoEscusso = importoEscusso;
		this.percentualeGaranzia = percentualeGaranzia;
		this.percentualeRiassicurato = percentualeRiassicurato;
		this.dataErogazioneMutuo = dataErogazioneMutuo;
		this.dataDeliberaMutuo = dataDeliberaMutuo;
		this.dataEmissioneGaranzia = dataEmissioneGaranzia;
		this.dataScadenzaMutuo = dataScadenzaMutuo;
		this.dataScarico = dataScarico;
		this.dataRevoca = dataRevoca;
		this.dataEscussione = dataEscussione;
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
		this.utenteIserito = utenteIserito;
	}



	public Riassicurazione_RiassicurazioniVO() {
	}



	public Long getIdProgetto() {
		return idProgetto;
	}



	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}



	public Long getIdRiassicurazione() {
		return idRiassicurazione;
	}



	public void setIdRiassicurazione(Long idRiassicurazione) {
		this.idRiassicurazione = idRiassicurazione;
	}



	public String getLineaInterventoSogg() {
		return lineaInterventoSogg;
	}



	public void setLineaInterventoSogg(String lineaInterventoSogg) {
		this.lineaInterventoSogg = lineaInterventoSogg;
	}



	public String getRagioneSociale() {
		return ragioneSociale;
	}



	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}



	public String getFormaGiuridica() {
		return formaGiuridica;
	}



	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}



	public String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}



	public void setDescFormaGiuridica(String descFormaGiuridica) {
		this.descFormaGiuridica = descFormaGiuridica;
	}



	public String getCodiceFiscale() {
		return codiceFiscale;
	}



	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}



	public String getIndirizzoSedeDestinatario() {
		return indirizzoSedeDestinatario;
	}



	public void setIndirizzoSedeDestinatario(String indirizzoSedeDestinatario) {
		this.indirizzoSedeDestinatario = indirizzoSedeDestinatario;
	}



	public String getLocalitaSedeDestinatario() {
		return localitaSedeDestinatario;
	}



	public void setLocalitaSedeDestinatario(String localitaSedeDestinatario) {
		this.localitaSedeDestinatario = localitaSedeDestinatario;
	}



	public String getProvinciaSedeDestinatario() {
		return provinciaSedeDestinatario;
	}



	public void setProvinciaSedeDestinatario(String provinciaSedeDestinatario) {
		this.provinciaSedeDestinatario = provinciaSedeDestinatario;
	}



	public String getAteco() {
		return ateco;
	}



	public void setAteco(String ateco) {
		this.ateco = ateco;
	}



	public String getDescAteco() {
		return descAteco;
	}



	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}



	public BigDecimal getImportoFinanziato() {
		return importoFinanziato;
	}



	public void setImportoFinanziato(BigDecimal importoFinanziato) {
		this.importoFinanziato = importoFinanziato;
	}



	public BigDecimal getImportoGaranzia() {
		return importoGaranzia;
	}



	public void setImportoGaranzia(BigDecimal importoGaranzia) {
		this.importoGaranzia = importoGaranzia;
	}



	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}



	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}



	public BigDecimal getImportoEscusso() {
		return importoEscusso;
	}



	public void setImportoEscusso(BigDecimal importoEscusso) {
		this.importoEscusso = importoEscusso;
	}



	public BigDecimal getPercentualeGaranzia() {
		return percentualeGaranzia;
	}



	public void setPercentualeGaranzia(BigDecimal percentualeGaranzia) {
		this.percentualeGaranzia = percentualeGaranzia;
	}



	public BigDecimal getPercentualeRiassicurato() {
		return percentualeRiassicurato;
	}



	public void setPercentualeRiassicurato(BigDecimal percentualeRiassicurato) {
		this.percentualeRiassicurato = percentualeRiassicurato;
	}



	public Date getDataErogazioneMutuo() {
		return dataErogazioneMutuo;
	}



	public void setDataErogazioneMutuo(Date dataErogazioneMutuo) {
		this.dataErogazioneMutuo = dataErogazioneMutuo;
	}



	public Date getDataDeliberaMutuo() {
		return dataDeliberaMutuo;
	}



	public void setDataDeliberaMutuo(Date dataDeliberaMutuo) {
		this.dataDeliberaMutuo = dataDeliberaMutuo;
	}



	public Date getDataEmissioneGaranzia() {
		return dataEmissioneGaranzia;
	}



	public void setDataEmissioneGaranzia(Date dataEmissioneGaranzia) {
		this.dataEmissioneGaranzia = dataEmissioneGaranzia;
	}



	public Date getDataScadenzaMutuo() {
		return dataScadenzaMutuo;
	}



	public void setDataScadenzaMutuo(Date dataScadenzaMutuo) {
		this.dataScadenzaMutuo = dataScadenzaMutuo;
	}



	public Date getDataScarico() {
		return dataScarico;
	}



	public void setDataScarico(Date dataScarico) {
		this.dataScarico = dataScarico;
	}



	public Date getDataRevoca() {
		return dataRevoca;
	}



	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}



	public Date getDataEscussione() {
		return dataEscussione;
	}



	public void setDataEscussione(Date dataEscussione) {
		this.dataEscussione = dataEscussione;
	}



	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}



	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}



	public Date getDataFineValidita() {
		return dataFineValidita;
	}



	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}



	public String getUtenteIserito() {
		return utenteIserito;
	}



	public void setUtenteIserito(String utenteIserito) {
		this.utenteIserito = utenteIserito;
	}



	@Override
	public String toString() {
		return "Riassicurazione_RiassicurazioniVO [idProgetto=" + idProgetto + ", idRiassicurazione="
				+ idRiassicurazione + ", lineaInterventoSogg=" + lineaInterventoSogg + ", ragioneSociale="
				+ ragioneSociale + ", formaGiuridica=" + formaGiuridica + ", descFormaGiuridica=" + descFormaGiuridica
				+ ", codiceFiscale=" + codiceFiscale + ", indirizzoSedeDestinatario=" + indirizzoSedeDestinatario
				+ ", localitaSedeDestinatario=" + localitaSedeDestinatario + ", provinciaSedeDestinatario="
				+ provinciaSedeDestinatario + ", ateco=" + ateco + ", descAteco=" + descAteco + ", importoFinanziato="
				+ importoFinanziato + ", importoGaranzia=" + importoGaranzia + ", importoAmmesso=" + importoAmmesso
				+ ", importoEscusso=" + importoEscusso + ", percentualeGaranzia=" + percentualeGaranzia
				+ ", percentualeRiassicurato=" + percentualeRiassicurato + ", dataErogazioneMutuo="
				+ dataErogazioneMutuo + ", dataDeliberaMutuo=" + dataDeliberaMutuo + ", dataEmissioneGaranzia="
				+ dataEmissioneGaranzia + ", dataScadenzaMutuo=" + dataScadenzaMutuo + ", dataScarico=" + dataScarico
				+ ", dataRevoca=" + dataRevoca + ", dataEscussione=" + dataEscussione + ", dataInizioValidita="
				+ dataInizioValidita + ", dataFineValidita=" + dataFineValidita + ", utenteIserito=" + utenteIserito
				+ "]";
	}
	
	

}
