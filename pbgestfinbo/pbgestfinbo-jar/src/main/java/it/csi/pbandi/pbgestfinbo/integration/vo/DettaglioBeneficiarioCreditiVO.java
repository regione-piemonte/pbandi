/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class DettaglioBeneficiarioCreditiVO {
	
	/* TENETE AGGIORNATO L'OGGETTO 'DettaglioFinanziamentoErogato' SE FATE UNA MODIFICA BE, MANNAGGIA A CRI*** */

	private Long idBando;
	private Long idProgetto;
	private Long idSoggetto;
	private Long progrSoggettoProgetto;
	
	private Long idModalitaAgevolazioneOrig;
	private String descBreveModalitaAgevolazioneOrig;
	private String descModalitaAgevolazioneOrig;
	private Long idModalitaAgevolazioneRif;
	private String descBreveModalitaAgevolazioneRif;
	private String descModalitaAgevolazioneRif;
	private String dispDescAgevolazione; // Per visualizzare solo Contributo, Finanziamento o Garanzia
	
	private String codProgetto;
	private String titoloBando;
	private Long numErogazioni; // Usato nella logica per valorizzare Stato agevolazione (dispStatoAgevolazione)
	private String dispStatoAgevolazione;
	private Long idUltimaCausale; // Serve?
	private BigDecimal importoAmmesso;
	private BigDecimal totaleErogato;
	private BigDecimal totaleErogatoFin;
	private BigDecimal totaleErogatoBanca;
	private Boolean isCreditoResiduoLoading; // Parametro solo FE
	private BigDecimal creditoResiduo; // Da amm.vo cont.
	private Boolean isAgevolazioneLoading; // Parametro solo FE
	private BigDecimal agevolazione; // Da amm.vo cont.
	private Long idStatoCessione;
	private String statoCessione;
	private String revocaAmministrativa;
	private Date dataRevocaBancaria;
	private String libMandatoBanca;
	private String descStatoCredito; 
	private String listaRevoche; 
	private int idStatoCredito; 
	
	
	
	public DettaglioBeneficiarioCreditiVO() {
	}
	
	public DettaglioBeneficiarioCreditiVO(Long idBando, Long idProgetto, Long idSoggetto, Long progrSoggettoProgetto,
			Long idModalitaAgevolazioneOrig, String descBreveModalitaAgevolazioneOrig,
			String descModalitaAgevolazioneOrig, Long idModalitaAgevolazioneRif,
			String descBreveModalitaAgevolazioneRif, String descModalitaAgevolazioneRif, String dispDescAgevolazione,
			String codProgetto, String titoloBando, Long numErogazioni, String dispStatoAgevolazione,
			Long idUltimaCausale, BigDecimal importoAmmesso, BigDecimal totaleErogato, BigDecimal totaleErogatoFin,
			BigDecimal totaleErogatoBanca, Boolean isCreditoResiduoLoading, BigDecimal creditoResiduo,
			Boolean isAgevolazioneLoading, BigDecimal agevolazione, Long idStatoCessione, String statoCessione,
			String revocaAmministrativa, Date dataRevocaBancaria, String libMandatoBanca, String descStatoCredito,
			String listaRevoche, int idStatoCredito) {
		this.idBando = idBando;
		this.idProgetto = idProgetto;
		this.idSoggetto = idSoggetto;
		this.progrSoggettoProgetto = progrSoggettoProgetto;
		this.idModalitaAgevolazioneOrig = idModalitaAgevolazioneOrig;
		this.descBreveModalitaAgevolazioneOrig = descBreveModalitaAgevolazioneOrig;
		this.descModalitaAgevolazioneOrig = descModalitaAgevolazioneOrig;
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
		this.descBreveModalitaAgevolazioneRif = descBreveModalitaAgevolazioneRif;
		this.descModalitaAgevolazioneRif = descModalitaAgevolazioneRif;
		this.dispDescAgevolazione = dispDescAgevolazione;
		this.codProgetto = codProgetto;
		this.titoloBando = titoloBando;
		this.numErogazioni = numErogazioni;
		this.dispStatoAgevolazione = dispStatoAgevolazione;
		this.idUltimaCausale = idUltimaCausale;
		this.importoAmmesso = importoAmmesso;
		this.totaleErogato = totaleErogato;
		this.totaleErogatoFin = totaleErogatoFin;
		this.totaleErogatoBanca = totaleErogatoBanca;
		this.isCreditoResiduoLoading = isCreditoResiduoLoading;
		this.creditoResiduo = creditoResiduo;
		this.isAgevolazioneLoading = isAgevolazioneLoading;
		this.agevolazione = agevolazione;
		this.idStatoCessione = idStatoCessione;
		this.statoCessione = statoCessione;
		this.revocaAmministrativa = revocaAmministrativa;
		this.dataRevocaBancaria = dataRevocaBancaria;
		this.libMandatoBanca = libMandatoBanca;
		this.descStatoCredito = descStatoCredito;
		this.listaRevoche = listaRevoche;
		this.idStatoCredito = idStatoCredito;
	}




	public int getIdStatoCredito() {
		return idStatoCredito;
	}


	public void setIdStatoCredito(int idStatoCredito) {
		this.idStatoCredito = idStatoCredito;
	}


	public String getListaRevoche() {
		return listaRevoche;
	}


	public void setListaRevoche(String listaRevoche) {
		this.listaRevoche = listaRevoche;
	}


	public Long getIdBando() {
		return idBando;
	}

	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}

	public String getDescStatoCredito() {
		return descStatoCredito;
	}


	public void setDescStatoCredito(String descStatoCredito) {
		this.descStatoCredito = descStatoCredito;
	}


	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}


	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}


	public String getCodProgetto() {
		return codProgetto;
	}


	public void setCodProgetto(String codProgetto) {
		this.codProgetto = codProgetto;
	}


	public String getTitoloBando() {
		return titoloBando;
	}


	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}


	public Long getIdModalitaAgevolazioneOrig() {
		return idModalitaAgevolazioneOrig;
	}


	public void setIdModalitaAgevolazioneOrig(Long idModalitaAgevolazioneOrig) {
		this.idModalitaAgevolazioneOrig = idModalitaAgevolazioneOrig;
	}


	public String getDescBreveModalitaAgevolazioneOrig() {
		return descBreveModalitaAgevolazioneOrig;
	}


	public void setDescBreveModalitaAgevolazioneOrig(String descBreveModalitaAgevolazioneOrig) {
		this.descBreveModalitaAgevolazioneOrig = descBreveModalitaAgevolazioneOrig;
	}


	public String getDescModalitaAgevolazioneOrig() {
		return descModalitaAgevolazioneOrig;
	}


	public void setDescModalitaAgevolazioneOrig(String descModalitaAgevolazioneOrig) {
		this.descModalitaAgevolazioneOrig = descModalitaAgevolazioneOrig;
	}


	public Long getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}


	public void setIdModalitaAgevolazioneRif(Long idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}


	public String getDescBreveModalitaAgevolazioneRif() {
		return descBreveModalitaAgevolazioneRif;
	}


	public void setDescBreveModalitaAgevolazioneRif(String descBreveModalitaAgevolazioneRif) {
		this.descBreveModalitaAgevolazioneRif = descBreveModalitaAgevolazioneRif;
	}


	public String getDescModalitaAgevolazioneRif() {
		return descModalitaAgevolazioneRif;
	}


	public void setDescModalitaAgevolazioneRif(String descModalitaAgevolazioneRif) {
		this.descModalitaAgevolazioneRif = descModalitaAgevolazioneRif;
	}


	public Long getIdUltimaCausale() {
		return idUltimaCausale;
	}


	public void setIdUltimaCausale(Long idUltimaCausale) {
		this.idUltimaCausale = idUltimaCausale;
	}


	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public BigDecimal getTotaleErogato() {
		return totaleErogato;
	}


	public void setTotaleErogato(BigDecimal totaleErogato) {
		this.totaleErogato = totaleErogato;
	}


	public BigDecimal getTotaleErogatoFin() {
		return totaleErogatoFin;
	}


	public void setTotaleErogatoFin(BigDecimal totaleErogatoFin) {
		this.totaleErogatoFin = totaleErogatoFin;
	}


	public BigDecimal getTotaleErogatoBanca() {
		return totaleErogatoBanca;
	}


	public void setTotaleErogatoBanca(BigDecimal totaleErogatoBanca) {
		this.totaleErogatoBanca = totaleErogatoBanca;
	}


	public BigDecimal getCreditoResiduo() {
		return creditoResiduo;
	}


	public void setCreditoResiduo(BigDecimal creditoResiduo) {
		this.creditoResiduo = creditoResiduo;
	}


	public BigDecimal getAgevolazione() {
		return agevolazione;
	}


	public void setAgevolazione(BigDecimal agevolazione) {
		this.agevolazione = agevolazione;
	}


	public String getRevocaAmministrativa() {
		return revocaAmministrativa;
	}


	public void setRevocaAmministrativa(String revocaAmministrativa) {
		this.revocaAmministrativa = revocaAmministrativa;
	}


	public Date getDataRevocaBancaria() {
		return dataRevocaBancaria;
	}


	public void setDataRevocaBancaria(Date dataRevocaBancaria) {
		this.dataRevocaBancaria = dataRevocaBancaria;
	}


	public String getLibMandatoBanca() {
		return libMandatoBanca;
	}


	public void setLibMandatoBanca(String libMandatoBanca) {
		this.libMandatoBanca = libMandatoBanca;
	}


	public Long getIdStatoCessione() {
		return idStatoCessione;
	}


	public void setIdStatoCessione(Long idStatoCessione) {
		this.idStatoCessione = idStatoCessione;
	}


	public String getStatoCessione() {
		return statoCessione;
	}


	public void setStatoCessione(String statoCessione) {
		this.statoCessione = statoCessione;
	}


	public Boolean getIsCreditoResiduoLoading() {
		return isCreditoResiduoLoading;
	}


	public void setIsCreditoResiduoLoading(Boolean isCreditoResiduoLoading) {
		this.isCreditoResiduoLoading = isCreditoResiduoLoading;
	}


	public Boolean getIsAgevolazioneLoading() {
		return isAgevolazioneLoading;
	}


	public void setIsAgevolazioneLoading(Boolean isAgevolazioneLoading) {
		this.isAgevolazioneLoading = isAgevolazioneLoading;
	}


	public String getDispDescAgevolazione() {
		return dispDescAgevolazione;
	}


	public void setDispDescAgevolazione(String dispDescAgevolazione) {
		this.dispDescAgevolazione = dispDescAgevolazione;
	}


	public String getDispStatoAgevolazione() {
		return dispStatoAgevolazione;
	}


	public void setDispStatoAgevolazione(String dispStatoAgevolazione) {
		this.dispStatoAgevolazione = dispStatoAgevolazione;
	}

	public Long getNumErogazioni() {
		return numErogazioni;
	}

	public void setNumErogazioni(Long numErogazioni) {
		this.numErogazioni = numErogazioni;
	}

	@Override
	public String toString() {
		return "DettaglioBeneficiarioCreditiVO [idBando=" + idBando + ", idProgetto=" + idProgetto + ", idSoggetto="
				+ idSoggetto + ", progrSoggettoProgetto=" + progrSoggettoProgetto + ", idModalitaAgevolazioneOrig="
				+ idModalitaAgevolazioneOrig + ", descBreveModalitaAgevolazioneOrig="
				+ descBreveModalitaAgevolazioneOrig + ", descModalitaAgevolazioneOrig=" + descModalitaAgevolazioneOrig
				+ ", idModalitaAgevolazioneRif=" + idModalitaAgevolazioneRif + ", descBreveModalitaAgevolazioneRif="
				+ descBreveModalitaAgevolazioneRif + ", descModalitaAgevolazioneRif=" + descModalitaAgevolazioneRif
				+ ", dispDescAgevolazione=" + dispDescAgevolazione + ", codProgetto=" + codProgetto + ", titoloBando="
				+ titoloBando + ", numErogazioni=" + numErogazioni + ", dispStatoAgevolazione=" + dispStatoAgevolazione
				+ ", idUltimaCausale=" + idUltimaCausale + ", importoAmmesso=" + importoAmmesso + ", totaleErogato="
				+ totaleErogato + ", totaleErogatoFin=" + totaleErogatoFin + ", totaleErogatoBanca="
				+ totaleErogatoBanca + ", isCreditoResiduoLoading=" + isCreditoResiduoLoading + ", creditoResiduo="
				+ creditoResiduo + ", isAgevolazioneLoading=" + isAgevolazioneLoading + ", agevolazione=" + agevolazione
				+ ", idStatoCessione=" + idStatoCessione + ", statoCessione=" + statoCessione
				+ ", revocaAmministrativa=" + revocaAmministrativa + ", dataRevocaBancaria=" + dataRevocaBancaria
				+ ", libMandatoBanca=" + libMandatoBanca + ", descStatoCredito=" + descStatoCredito + ", listaRevoche="
				+ listaRevoche + ", idStatoCredito=" + idStatoCredito + "]";
	}

	


}
