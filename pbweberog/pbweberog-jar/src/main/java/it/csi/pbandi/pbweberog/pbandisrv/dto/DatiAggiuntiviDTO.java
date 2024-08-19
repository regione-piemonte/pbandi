/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.dto;

import java.util.Date;

public class DatiAggiuntiviDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String numeroDomanda;
	private String nomeReferenteTecnico;
	private String cognomeReferenteTecnico;
	private String cfReferenteTecnico;
	private String telefonoReferenteTecnico;
	private String emailReferenteTecnico;
	private String pecReferenteTecnico;
	private Date dataUltimaTrasmissione;
	private String descrizioneIntervento;
	private String livelloProgettuale;
	private String dichTipologiaProgetto;
	private String individuazioneAmbito;
	private Long numeroBeniOggIntervento;
	private String descrizioneOggettiIntervento;
	private String tipologiaBene;
	private String titoloDisponibilitaBene;
	private String tipologiaVincoloIstruttoria;
	private String decretoSoprintendenza;
	private Long cofinanziamento;
	private String tipologiaIntervento;
	private String ivaDetraibileIndetraibile;
	private Long anno2023;
	private String impegno2023N;
	private Long anno2024;
	private String impegno2024N;
	private Long anno2025;
	private String impegno2025N;
	private String modErogSostFin;
	private String provincia;
	private String comuneCatasto;
	private String catastoCodiceComune;
	private String foglioCatasto;
	private String particelleCatasto;
	private String subParticellaCatasto;
	private String rea;
	private String codiceAteco;
	private Long contabiliaCodBen;
	private Long codiceCor;
	private String modAiutoStatoCon;
	private String prescrizioniDeMinimis;
	private String ddAssegnazioneNDel;
	private Date attoObbligoDataProtocollo;
	private Long attoObbligoNProtocollo;

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getNomeReferenteTecnico() {
		return nomeReferenteTecnico;
	}

	public void setNomeReferenteTecnico(String nomeReferenteTecnico) {
		this.nomeReferenteTecnico = nomeReferenteTecnico;
	}

	public String getCognomeReferenteTecnico() {
		return cognomeReferenteTecnico;
	}

	public void setCognomeReferenteTecnico(String cognomeReferenteTecnico) {
		this.cognomeReferenteTecnico = cognomeReferenteTecnico;
	}

	public String getCfReferenteTecnico() {
		return cfReferenteTecnico;
	}

	public void setCfReferenteTecnico(String cfReferenteTecnico) {
		this.cfReferenteTecnico = cfReferenteTecnico;
	}

	public String getTelefonoReferenteTecnico() {
		return telefonoReferenteTecnico;
	}

	public void setTelefonoReferenteTecnico(String telefonoReferenteTecnico) {
		this.telefonoReferenteTecnico = telefonoReferenteTecnico;
	}

	public String getEmailReferenteTecnico() {
		return emailReferenteTecnico;
	}

	public void setEmailReferenteTecnico(String emailReferenteTecnico) {
		this.emailReferenteTecnico = emailReferenteTecnico;
	}

	public String getPecReferenteTecnico() {
		return pecReferenteTecnico;
	}

	public void setPecReferenteTecnico(String pecReferenteTecnico) {
		this.pecReferenteTecnico = pecReferenteTecnico;
	}

	public Date getDataUltimaTrasmissione() {
		return dataUltimaTrasmissione;
	}

	public void setDataUltimaTrasmissione(Date dataUltimaTrasmissione) {
		this.dataUltimaTrasmissione = dataUltimaTrasmissione;
	}

	public String getDescrizioneIntervento() {
		return descrizioneIntervento;
	}

	public void setDescrizioneIntervento(String descrizioneIntervento) {
		this.descrizioneIntervento = descrizioneIntervento;
	}

	public String getLivelloProgettuale() {
		return livelloProgettuale;
	}

	public void setLivelloProgettuale(String livelloProgettuale) {
		this.livelloProgettuale = livelloProgettuale;
	}

	public String getDichTipologiaProgetto() {
		return dichTipologiaProgetto;
	}

	public void setDichTipologiaProgetto(String dichTipologiaProgetto) {
		this.dichTipologiaProgetto = dichTipologiaProgetto;
	}

	public String getIndividuazioneAmbito() {
		return individuazioneAmbito;
	}

	public void setIndividuazioneAmbito(String individuazioneAmbito) {
		this.individuazioneAmbito = individuazioneAmbito;
	}

	public Long getNumeroBeniOggIntervento() {
		return numeroBeniOggIntervento;
	}

	public void setNumeroBeniOggIntervento(Long numeroBeniOggIntervento) {
		this.numeroBeniOggIntervento = numeroBeniOggIntervento;
	}

	public String getDescrizioneOggettiIntervento() {
		return descrizioneOggettiIntervento;
	}

	public void setDescrizioneOggettiIntervento(String descrizioneOggettiIntervento) {
		this.descrizioneOggettiIntervento = descrizioneOggettiIntervento;
	}

	public String getTipologiaBene() {
		return tipologiaBene;
	}

	public void setTipologiaBene(String tipologiaBene) {
		this.tipologiaBene = tipologiaBene;
	}

	public String getTitoloDisponibilitaBene() {
		return titoloDisponibilitaBene;
	}

	public void setTitoloDisponibilitaBene(String titoloDisponibilitaBene) {
		this.titoloDisponibilitaBene = titoloDisponibilitaBene;
	}

	public String getTipologiaVincoloIstruttoria() {
		return tipologiaVincoloIstruttoria;
	}

	public void setTipologiaVincoloIstruttoria(String tipologiaVincoloIstruttoria) {
		this.tipologiaVincoloIstruttoria = tipologiaVincoloIstruttoria;
	}

	public String getDecretoSoprintendenza() {
		return decretoSoprintendenza;
	}

	public void setDecretoSoprintendenza(String decretoSoprintendenza) {
		this.decretoSoprintendenza = decretoSoprintendenza;
	}

	public Long getCofinanziamento() {
		return cofinanziamento;
	}

	public void setCofinanziamento(Long cofinanziamento) {
		this.cofinanziamento = cofinanziamento;
	}

	public String getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(String tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public String getIvaDetraibileIndetraibile() {
		return ivaDetraibileIndetraibile;
	}

	public void setIvaDetraibileIndetraibile(String ivaDetraibileIndetraibile) {
		this.ivaDetraibileIndetraibile = ivaDetraibileIndetraibile;
	}

	public Long getAnno2023() {
		return anno2023;
	}

	public void setAnno2023(Long anno2023) {
		this.anno2023 = anno2023;
	}

	public String getImpegno2023N() {
		return impegno2023N;
	}

	public void setImpegno2023N(String impegno2023n) {
		impegno2023N = impegno2023n;
	}

	public Long getAnno2024() {
		return anno2024;
	}

	public void setAnno2024(Long anno2024) {
		this.anno2024 = anno2024;
	}

	public String getImpegno2024N() {
		return impegno2024N;
	}

	public void setImpegno2024N(String impegno2024n) {
		impegno2024N = impegno2024n;
	}

	public Long getAnno2025() {
		return anno2025;
	}

	public void setAnno2025(Long anno2025) {
		this.anno2025 = anno2025;
	}

	public String getImpegno2025N() {
		return impegno2025N;
	}

	public void setImpegno2025N(String impegno2025n) {
		impegno2025N = impegno2025n;
	}

	public String getModErogSostFin() {
		return modErogSostFin;
	}

	public void setModErogSostFin(String modErogSostFin) {
		this.modErogSostFin = modErogSostFin;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComuneCatasto() {
		return comuneCatasto;
	}

	public void setComuneCatasto(String comuneCatasto) {
		this.comuneCatasto = comuneCatasto;
	}

	public String getCatastoCodiceComune() {
		return catastoCodiceComune;
	}

	public void setCatastoCodiceComune(String catastoCodiceComune) {
		this.catastoCodiceComune = catastoCodiceComune;
	}

	public String getFoglioCatasto() {
		return foglioCatasto;
	}

	public void setFoglioCatasto(String foglioCatasto) {
		this.foglioCatasto = foglioCatasto;
	}

	public String getParticelleCatasto() {
		return particelleCatasto;
	}

	public void setParticelleCatasto(String particelleCatasto) {
		this.particelleCatasto = particelleCatasto;
	}

	public String getSubParticellaCatasto() {
		return subParticellaCatasto;
	}

	public void setSubParticellaCatasto(String subParticellaCatasto) {
		this.subParticellaCatasto = subParticellaCatasto;
	}

	public String getRea() {
		return rea;
	}

	public void setRea(String rea) {
		this.rea = rea;
	}

	public String getCodiceAteco() {
		return codiceAteco;
	}

	public void setCodiceAteco(String codiceAteco) {
		this.codiceAteco = codiceAteco;
	}

	public Long getContabiliaCodBen() {
		return contabiliaCodBen;
	}

	public void setContabiliaCodBen(Long contabiliaCodBen) {
		this.contabiliaCodBen = contabiliaCodBen;
	}

	public Long getCodiceCor() {
		return codiceCor;
	}

	public void setCodiceCor(Long codiceCor) {
		this.codiceCor = codiceCor;
	}

	public String getModAiutoStatoCon() {
		return modAiutoStatoCon;
	}

	public void setModAiutoStatoCon(String modAiutoStatoCon) {
		this.modAiutoStatoCon = modAiutoStatoCon;
	}

	public String getPrescrizioniDeMinimis() {
		return prescrizioniDeMinimis;
	}

	public void setPrescrizioniDeMinimis(String prescrizioniDeMinimis) {
		this.prescrizioniDeMinimis = prescrizioniDeMinimis;
	}

	public String getDdAssegnazioneNDel() {
		return ddAssegnazioneNDel;
	}

	public void setDdAssegnazioneNDel(String ddAssegnazioneNDel) {
		this.ddAssegnazioneNDel = ddAssegnazioneNDel;
	}

	public Date getAttoObbligoDataProtocollo() {
		return attoObbligoDataProtocollo;
	}

	public void setAttoObbligoDataProtocollo(Date attoObbligoDataProtocollo) {
		this.attoObbligoDataProtocollo = attoObbligoDataProtocollo;
	}

	public Long getAttoObbligoNProtocollo() {
		return attoObbligoNProtocollo;
	}

	public void setAttoObbligoNProtocollo(Long attoObbligoNProtocollo) {
		this.attoObbligoNProtocollo = attoObbligoNProtocollo;
	}

}
