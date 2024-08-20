/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;
import java.util.List;

public class BeneficiarioCreditiVO {
	
	/* TENETE AGGIORNATO L'OGGETTO 'FinanziamentoErogato' SE FATE UNA MODIFICA BE, MANNAGGIA A CRI*** */

	private Long idSoggetto;
	private String ndg;
	private Long idTipoSoggetto;
	private String denominazione;
	private String codiceFiscale;
	private String partitaIva;
	private Long idFormaGiuridica;
	private String formaGiuridica;
	private Long idStatoAzienda;
	private String statoAzienda;
	private Long idRating;
	private String rating;
	private Date dataProcedura;

	private List<DettaglioBeneficiarioCreditiVO> listaDettagli;



	
	public BeneficiarioCreditiVO() {
	}


	public BeneficiarioCreditiVO(Long idSoggetto, String ndg, Long idTipoSoggetto, String denominazione,
			String codiceFiscale, String partitaIva, Long idFormaGiuridica, String formaGiuridica, Long idStatoAzienda,
			String statoAzienda, Long idRating, String rating, Date dataProcedura,
			List<DettaglioBeneficiarioCreditiVO> listaDettagli) {
		this.idSoggetto = idSoggetto;
		this.ndg = ndg;
		this.idTipoSoggetto = idTipoSoggetto;
		this.denominazione = denominazione;
		this.codiceFiscale = codiceFiscale;
		this.partitaIva = partitaIva;
		this.idFormaGiuridica = idFormaGiuridica;
		this.formaGiuridica = formaGiuridica;
		this.idStatoAzienda = idStatoAzienda;
		this.statoAzienda = statoAzienda;
		this.idRating = idRating;
		this.rating = rating;
		this.dataProcedura = dataProcedura;
		this.listaDettagli = listaDettagli;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public String getNdg() {
		return ndg;
	}


	public void setNdg(String ndg) {
		this.ndg = ndg;
	}


	public Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}


	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}


	public String getDenominazione() {
		return denominazione;
	}


	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}


	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}


	public String getFormaGiuridica() {
		return formaGiuridica;
	}


	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}


	public Long getIdStatoAzienda() {
		return idStatoAzienda;
	}


	public void setIdStatoAzienda(Long idStatoAzienda) {
		this.idStatoAzienda = idStatoAzienda;
	}


	public String getStatoAzienda() {
		return statoAzienda;
	}


	public void setStatoAzienda(String statoAzienda) {
		this.statoAzienda = statoAzienda;
	}


	public Long getIdRating() {
		return idRating;
	}


	public void setIdRating(Long idRating) {
		this.idRating = idRating;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public Date getDataProcedura() {
		return dataProcedura;
	}


	public void setDataProcedura(Date dataProcedura) {
		this.dataProcedura = dataProcedura;
	}


	public List<DettaglioBeneficiarioCreditiVO> getListaDettagli() {
		return listaDettagli;
	}


	public void setListaDettagli(List<DettaglioBeneficiarioCreditiVO> listaDettagli) {
		this.listaDettagli = listaDettagli;
	}


	@Override
	public String toString() {
		return "BeneficiarioCreditiVO [idSoggetto=" + idSoggetto + ", ndg=" + ndg + ", idTipoSoggetto=" + idTipoSoggetto
				+ ", denominazione=" + denominazione + ", codiceFiscale=" + codiceFiscale + ", partitaIva=" + partitaIva
				+ ", idFormaGiuridica=" + idFormaGiuridica + ", formaGiuridica=" + formaGiuridica + ", idStatoAzienda="
				+ idStatoAzienda + ", statoAzienda=" + statoAzienda + ", idRating=" + idRating + ", rating=" + rating
				+ ", dataProcedura=" + dataProcedura + ", listaDettagli=" + listaDettagli + "]";
	}





}
