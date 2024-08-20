/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RevocaAmministrativaCreditiVO {
	
	private Boolean esito;
	private String msg;
	private String exc;
	
	/* vecchia parte
	private String idSoggetto;
	private String idProgetto;
	private String progrSoggettoProgetto;
	private String idModalitaAgevolazioneOrig;
	private String descBreveModalitaAgevolazioneOrig;
	private String descModalitaAgevolazioneOrig;
	private String idModalitaAgevolazioneRif;
	private String descBreveModalitaAgevolazioneRif;
	private String descModalitaAgevolazioneRif;
	private String statoAgevolazione;
	private BigDecimal totaleErogato;
	private BigDecimal totaleFinpiemonte;
	private BigDecimal totaleBanca;
	private BigDecimal importoFinpiemonte;
	private String cessSoggettoTerzo;
	private String revocaManBanca;
	private String revocaAmministrativa;*/
	
	private Long idGestioneRevoca;
	private String idProgetto;
	private String idRevoca;
	private String idCausaleBlocco;
	private String descCausaleBlocco;
	private String idCateAnag;
	private String descCateAnag;
	private Date dataGestione;
	private Date dataStatoRevoca;
	private String descStatoRevoca;
	private String idMotivoRevoca;
	private String descMotivoRevoca;
	private BigDecimal impAmmesso;
	private BigDecimal impErogato;
	private BigDecimal impRecupero;
	private String flagOrdineRecupero;
	private BigDecimal impRevoca;
	private BigDecimal impBando;
	private Long numeroRevoca; 
	private BigDecimal recuperoQuotaCapitale; // da amm.vo
	private BigDecimal recuperoAgevolazione; // da amm.vo
	private Date dataNotificaProvRevoca ; 
	private String tipoRevoca;
	private String statoProvRevoca; 
	private String descCausa; 
	private Date dataProvedimentoRevoca; 
	private BigDecimal quotaCapitale;
	private BigDecimal impAgevolazione; 
	private BigDecimal importoErogatoFinp; 
	private BigDecimal importoTotaleRevocato; 
	private BigDecimal importoConcesso; 
	
	
	
	
	public RevocaAmministrativaCreditiVO(Boolean esito, String msg, String exc, Long idGestioneRevoca,
			String idProgetto, String idRevoca, String idCausaleBlocco, String descCausaleBlocco, String idCateAnag,
			String descCateAnag, Date dataGestione, Date dataStatoRevoca, String descStatoRevoca, String idMotivoRevoca,
			String descMotivoRevoca, BigDecimal impAmmesso, BigDecimal impErogato, BigDecimal impRecupero,
			String flagOrdineRecupero, BigDecimal impRevoca, BigDecimal impBando, Long numeroRevoca,
			BigDecimal recuperoQuotaCapitale, BigDecimal recuperoAgevolazione, Date dataNotificaProvRevoca,
			String tipoRevoca, String statoProvRevoca, String descCausa, Date dataProvedimentoRevoca,
			BigDecimal quotaCapitale, BigDecimal impAgevolazione, BigDecimal importoErogatoFinp,
			BigDecimal importoTotaleRevocato, BigDecimal importoConcesso) {
		this.esito = esito;
		this.msg = msg;
		this.exc = exc;
		this.idGestioneRevoca = idGestioneRevoca;
		this.idProgetto = idProgetto;
		this.idRevoca = idRevoca;
		this.idCausaleBlocco = idCausaleBlocco;
		this.descCausaleBlocco = descCausaleBlocco;
		this.idCateAnag = idCateAnag;
		this.descCateAnag = descCateAnag;
		this.dataGestione = dataGestione;
		this.dataStatoRevoca = dataStatoRevoca;
		this.descStatoRevoca = descStatoRevoca;
		this.idMotivoRevoca = idMotivoRevoca;
		this.descMotivoRevoca = descMotivoRevoca;
		this.impAmmesso = impAmmesso;
		this.impErogato = impErogato;
		this.impRecupero = impRecupero;
		this.flagOrdineRecupero = flagOrdineRecupero;
		this.impRevoca = impRevoca;
		this.impBando = impBando;
		this.numeroRevoca = numeroRevoca;
		this.recuperoQuotaCapitale = recuperoQuotaCapitale;
		this.recuperoAgevolazione = recuperoAgevolazione;
		this.dataNotificaProvRevoca = dataNotificaProvRevoca;
		this.tipoRevoca = tipoRevoca;
		this.statoProvRevoca = statoProvRevoca;
		this.descCausa = descCausa;
		this.dataProvedimentoRevoca = dataProvedimentoRevoca;
		this.quotaCapitale = quotaCapitale;
		this.impAgevolazione = impAgevolazione;
		this.importoErogatoFinp = importoErogatoFinp;
		this.importoTotaleRevocato = importoTotaleRevocato;
		this.importoConcesso = importoConcesso;
	}



	public Long getIdGestioneRevoca() {
		return idGestioneRevoca;
	}



	public void setIdGestioneRevoca(Long idGestioneRevoca) {
		this.idGestioneRevoca = idGestioneRevoca;
	}



	public BigDecimal getImportoConcesso() {
		return importoConcesso;
	}


	public void setImportoConcesso(BigDecimal importoConcesso) {
		this.importoConcesso = importoConcesso;
	}


	public Date getDataNotificaProvRevoca() {
		return dataNotificaProvRevoca;
	}


	public void setDataNotificaProvRevoca(Date dataNotificaProvRevoca) {
		this.dataNotificaProvRevoca = dataNotificaProvRevoca;
	}


	public String getTipoRevoca() {
		return tipoRevoca;
	}


	public void setTipoRevoca(String tipoRevoca) {
		this.tipoRevoca = tipoRevoca;
	}


	public String getStatoProvRevoca() {
		return statoProvRevoca;
	}


	public void setStatoProvRevoca(String statoProvRevoca) {
		this.statoProvRevoca = statoProvRevoca;
	}


	public String getDescCausa() {
		return descCausa;
	}


	public void setDescCausa(String descCausa) {
		this.descCausa = descCausa;
	}


	public Date getDataProvedimentoRevoca() {
		return dataProvedimentoRevoca;
	}


	public void setDataProvedimentoRevoca(Date dataProvedimentoRevoca) {
		this.dataProvedimentoRevoca = dataProvedimentoRevoca;
	}


	public BigDecimal getQuotaCapitale() {
		return quotaCapitale;
	}


	public void setQuotaCapitale(BigDecimal quotaCapitale) {
		this.quotaCapitale = quotaCapitale;
	}


	public BigDecimal getImpAgevolazione() {
		return impAgevolazione;
	}


	public void setImpAgevolazione(BigDecimal impAgevolazione) {
		this.impAgevolazione = impAgevolazione;
	}


	public BigDecimal getImportoErogatoFinp() {
		return importoErogatoFinp;
	}


	public void setImportoErogatoFinp(BigDecimal importoErogatoFinp) {
		this.importoErogatoFinp = importoErogatoFinp;
	}


	public BigDecimal getImportoTotaleRevocato() {
		return importoTotaleRevocato;
	}


	public void setImportoTotaleRevocato(BigDecimal importoTotaleRevocato) {
		this.importoTotaleRevocato = importoTotaleRevocato;
	}


	public BigDecimal getRecuperoQuotaCapitale() {
		return recuperoQuotaCapitale;
	}


	public void setRecuperoQuotaCapitale(BigDecimal recuperoQuotaCapitale) {
		this.recuperoQuotaCapitale = recuperoQuotaCapitale;
	}


	public BigDecimal getRecuperoAgevolazione() {
		return recuperoAgevolazione;
	}


	public void setRecuperoAgevolazione(BigDecimal recuperoAgevolazione) {
		this.recuperoAgevolazione = recuperoAgevolazione;
	}


	public Long getNumeroRevoca() {
		return numeroRevoca;
	}


	public void setNumeroRevoca(Long numeroRevoca) {
		this.numeroRevoca = numeroRevoca;
	}


	public Boolean getEsito() {
		return esito;
	}


	public void setEsito(Boolean esito) {
		this.esito = esito;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getExc() {
		return exc;
	}


	public void setExc(String exc) {
		this.exc = exc;
	}


	public RevocaAmministrativaCreditiVO() {
	}


	public String getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}


	public String getIdRevoca() {
		return idRevoca;
	}


	public void setIdRevoca(String idRevoca) {
		this.idRevoca = idRevoca;
	}


	public String getIdCausaleBlocco() {
		return idCausaleBlocco;
	}


	public void setIdCausaleBlocco(String idCausaleBlocco) {
		this.idCausaleBlocco = idCausaleBlocco;
	}


	public String getDescCausaleBlocco() {
		return descCausaleBlocco;
	}


	public void setDescCausaleBlocco(String descCausaleBlocco) {
		this.descCausaleBlocco = descCausaleBlocco;
	}


	public String getIdCateAnag() {
		return idCateAnag;
	}


	public void setIdCateAnag(String idCateAnag) {
		this.idCateAnag = idCateAnag;
	}


	public String getDescCateAnag() {
		return descCateAnag;
	}


	public void setDescCateAnag(String descCateAnag) {
		this.descCateAnag = descCateAnag;
	}


	public Date getDataGestione() {
		return dataGestione;
	}


	public void setDataGestione(Date dataGestione) {
		this.dataGestione = dataGestione;
	}


	public Date getDataStatoRevoca() {
		return dataStatoRevoca;
	}


	public void setDataStatoRevoca(Date dataStatoRevoca) {
		this.dataStatoRevoca = dataStatoRevoca;
	}


	public String getDescStatoRevoca() {
		return descStatoRevoca;
	}


	public void setDescStatoRevoca(String descStatoRevoca) {
		this.descStatoRevoca = descStatoRevoca;
	}


	public String getIdMotivoRevoca() {
		return idMotivoRevoca;
	}


	public void setIdMotivoRevoca(String idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}


	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}


	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}


	public BigDecimal getImpAmmesso() {
		return impAmmesso;
	}


	public void setImpAmmesso(BigDecimal impAmmesso) {
		this.impAmmesso = impAmmesso;
	}


	public BigDecimal getImpErogato() {
		return impErogato;
	}


	public void setImpErogato(BigDecimal impErogato) {
		this.impErogato = impErogato;
	}


	public BigDecimal getImpRecupero() {
		return impRecupero;
	}


	public void setImpRecupero(BigDecimal impRecupero) {
		this.impRecupero = impRecupero;
	}


	public String getFlagOrdineRecupero() {
		return flagOrdineRecupero;
	}


	public void setFlagOrdineRecupero(String flagOrdineRecupero) {
		this.flagOrdineRecupero = flagOrdineRecupero;
	}


	public BigDecimal getImpRevoca() {
		return impRevoca;
	}


	public void setImpRevoca(BigDecimal impRevoca) {
		this.impRevoca = impRevoca;
	}


	public BigDecimal getImpBando() {
		return impBando;
	}


	public void setImpBando(BigDecimal impBando) {
		this.impBando = impBando;
	}



	@Override
	public String toString() {
		return "RevocaAmministrativaCreditiVO [esito=" + esito + ", msg=" + msg + ", exc=" + exc + ", idGestioneRevoca="
				+ idGestioneRevoca + ", idProgetto=" + idProgetto + ", idRevoca=" + idRevoca + ", idCausaleBlocco="
				+ idCausaleBlocco + ", descCausaleBlocco=" + descCausaleBlocco + ", idCateAnag=" + idCateAnag
				+ ", descCateAnag=" + descCateAnag + ", dataGestione=" + dataGestione + ", dataStatoRevoca="
				+ dataStatoRevoca + ", descStatoRevoca=" + descStatoRevoca + ", idMotivoRevoca=" + idMotivoRevoca
				+ ", descMotivoRevoca=" + descMotivoRevoca + ", impAmmesso=" + impAmmesso + ", impErogato=" + impErogato
				+ ", impRecupero=" + impRecupero + ", flagOrdineRecupero=" + flagOrdineRecupero + ", impRevoca="
				+ impRevoca + ", impBando=" + impBando + ", numeroRevoca=" + numeroRevoca + ", recuperoQuotaCapitale="
				+ recuperoQuotaCapitale + ", recuperoAgevolazione=" + recuperoAgevolazione + ", dataNotificaProvRevoca="
				+ dataNotificaProvRevoca + ", tipoRevoca=" + tipoRevoca + ", statoProvRevoca=" + statoProvRevoca
				+ ", descCausa=" + descCausa + ", dataProvedimentoRevoca=" + dataProvedimentoRevoca + ", quotaCapitale="
				+ quotaCapitale + ", impAgevolazione=" + impAgevolazione + ", importoErogatoFinp=" + importoErogatoFinp
				+ ", importoTotaleRevocato=" + importoTotaleRevocato + ", importoConcesso=" + importoConcesso + "]";
	}


	
	

	
	
}
