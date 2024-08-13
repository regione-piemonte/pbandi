/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.profilazione;

public class BeneficiarioDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Long idSoggetto = null;

	private Long idProgetto = null;

	private String codiceFiscale = null;

//	private String cognome = null;
//
//	private String nome = null;

	private String descrizione = null;

	private Integer id = null;

	private Long idFormaGiuridica = null;

	private Long idDimensioneImpresa = null;


	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

//	public String getCognome() {
//		return cognome;
//	}
//
//	public void setCognome(String cognome) {
//		this.cognome = cognome;
//	}
//
//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}

	public Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}

	public void setIdDimensioneImpresa(Long idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}

	@Override
	public String toString() {
		return "BeneficiarioDTO [idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto + ", codiceFiscale="
				+ codiceFiscale + 
				//", cognome=" + cognome + ", nome=" + nome + 
				", descrizione=" + descrizione + ", id="
				+ id + ", idFormaGiuridica=" + idFormaGiuridica + ", idDimensioneImpresa=" + idDimensioneImpresa + "]";
	}

	
}
