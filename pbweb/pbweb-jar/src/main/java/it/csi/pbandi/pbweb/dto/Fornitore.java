package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class Fornitore implements java.io.Serializable {

	private java.lang.String codiceFiscaleFornitore = null;
	private java.lang.String cognomeFornitore = null;
	private java.lang.Double costoOrario = null;
	private java.lang.Double costoRisorsa = null;
	private java.lang.String descQualifica = null;
	private java.lang.String descTipoSoggetto = null;
	private java.lang.String denominazioneFornitore = null;
	private java.util.ArrayList<DocumentoAllegatoDTO> documentiAllegati = new java.util.ArrayList<DocumentoAllegatoDTO>();
	private java.lang.Long idFornitore = null;
	private java.lang.Long idQualifica = null;
	private java.lang.Long idTipoSoggetto = null;
	private java.lang.Long idSoggettoFornitore = null;
	private java.lang.Boolean isLinkAllegaFileVisible = false;
	private java.lang.Boolean isInvalid = null;
	//private java.lang.String linkModifica = null;
	//private java.lang.String linkQualifiche = null;
	//private java.lang.String linkCancella = null;
	private java.lang.Double monteOre = null;
	private java.lang.String nomeFornitore = null;
	private java.lang.String partitaIvaFornitore = null;
	private java.lang.Long idAttivitaAteco = null;
	private java.lang.Long idFormaGiuridica = null;
	private java.lang.Long idNazione = null;
	private java.lang.String altroCodice = null;
	private java.lang.String codUniIpa = null;
	private java.lang.Long flagPubblicoPrivato = null;
	
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(java.lang.String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(java.lang.String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}

	public void setCostoOrario(java.lang.Double costoOrario) {
		this.costoOrario = costoOrario;
	}

	public java.lang.Double getCostoRisorsa() {
		return costoRisorsa;
	}

	public void setCostoRisorsa(java.lang.Double costoRisorsa) {
		this.costoRisorsa = costoRisorsa;
	}

	public java.lang.String getDescQualifica() {
		return descQualifica;
	}

	public void setDescQualifica(java.lang.String descQualifica) {
		this.descQualifica = descQualifica;
	}

	public java.lang.String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}

	public void setDescTipoSoggetto(java.lang.String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}

	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(java.lang.String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public java.util.ArrayList<DocumentoAllegatoDTO> getDocumentiAllegati() {
		return documentiAllegati;
	}

	public void setDocumentiAllegati(java.util.ArrayList<DocumentoAllegatoDTO> documentiAllegati) {
		this.documentiAllegati = documentiAllegati;
	}

	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public java.lang.Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(java.lang.Long idQualifica) {
		this.idQualifica = idQualifica;
	}

	public java.lang.Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(java.lang.Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public java.lang.Long getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}

	public void setIdSoggettoFornitore(java.lang.Long idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}

	public java.lang.Boolean getIsLinkAllegaFileVisible() {
		return isLinkAllegaFileVisible;
	}

	public void setIsLinkAllegaFileVisible(java.lang.Boolean isLinkAllegaFileVisible) {
		this.isLinkAllegaFileVisible = isLinkAllegaFileVisible;
	}

	public java.lang.Boolean getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(java.lang.Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public java.lang.Double getMonteOre() {
		return monteOre;
	}

	public void setMonteOre(java.lang.Double monteOre) {
		this.monteOre = monteOre;
	}

	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(java.lang.String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(java.lang.String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public java.lang.Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	public void setIdAttivitaAteco(java.lang.Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}

	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	public void setIdFormaGiuridica(java.lang.Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}

	public java.lang.Long getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(java.lang.Long idNazione) {
		this.idNazione = idNazione;
	}

	public java.lang.String getAltroCodice() {
		return altroCodice;
	}

	public void setAltroCodice(java.lang.String altroCodice) {
		this.altroCodice = altroCodice;
	}

	public java.lang.String getCodUniIpa() {
		return codUniIpa;
	}

	public void setCodUniIpa(java.lang.String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}

	public java.lang.Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(java.lang.Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}