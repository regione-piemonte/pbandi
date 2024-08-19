/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class SedeProgetto implements java.io.Serializable {

	private Long idViaL2;
	private Long idProgetto;
	private Long idSoggettoBeneficiario;
	private Long idSede;
	private Long progrSoggettoProgettoSede;
	private Long idTipoSede;
	private String descBreveTipoSede;
	private String descTipoSede;
	private String partitaIva;
	private Long idNazione;
	private String descNazione;
	private Long idRegione;
	private String descRegione;
	private Long idProvincia;
	private String descProvincia;
	private Long idComune;
	private String descComune;
	private Long idIndirizzo;
	private String descIndirizzo;
	private Long idRecapiti;
	private String email;
	private String fax;
	private String telefono;
	private String cap;
	private String linkDettaglio;
	private String linkModifica;
	private String linkElimina;
	private String descIndirizzoComposto;
	private Boolean isEliminabile;
	private Boolean isModificabile;
	private Long civico;
	private String codIstatComune;
	private Boolean flagSedeAmministrativa;
	private static final long serialVersionUID = 1L;

	
	
	
	public Long getIdViaL2() {
		return idViaL2;
	}

	public void setIdViaL2(Long idViaL2) {
		this.idViaL2 = idViaL2;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public Long getIdSede() {
		return idSede;
	}

	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}

	public Long getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}

	public void setProgrSoggettoProgettoSede(Long progrSoggettoProgettoSede) {
		this.progrSoggettoProgettoSede = progrSoggettoProgettoSede;
	}

	public Long getIdTipoSede() {
		return idTipoSede;
	}

	public void setIdTipoSede(Long idTipoSede) {
		this.idTipoSede = idTipoSede;
	}

	public String getDescBreveTipoSede() {
		return descBreveTipoSede;
	}

	public void setDescBreveTipoSede(String descBreveTipoSede) {
		this.descBreveTipoSede = descBreveTipoSede;
	}

	public String getDescTipoSede() {
		return descTipoSede;
	}

	public void setDescTipoSede(String descTipoSede) {
		this.descTipoSede = descTipoSede;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public Long getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}

	public String getDescNazione() {
		return descNazione;
	}

	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}

	public Long getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}

	public String getDescRegione() {
		return descRegione;
	}

	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public Long getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public String getDescIndirizzo() {
		return descIndirizzo;
	}

	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}

	public Long getIdRecapiti() {
		return idRecapiti;
	}

	public void setIdRecapiti(Long idRecapiti) {
		this.idRecapiti = idRecapiti;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getLinkDettaglio() {
		return linkDettaglio;
	}

	public void setLinkDettaglio(String linkDettaglio) {
		this.linkDettaglio = linkDettaglio;
	}

	public String getLinkModifica() {
		return linkModifica;
	}

	public void setLinkModifica(String linkModifica) {
		this.linkModifica = linkModifica;
	}

	public String getLinkElimina() {
		return linkElimina;
	}

	public void setLinkElimina(String linkElimina) {
		this.linkElimina = linkElimina;
	}

	public String getDescIndirizzoComposto() {
		return descIndirizzoComposto;
	}

	public void setDescIndirizzoComposto(String descIndirizzoComposto) {
		this.descIndirizzoComposto = descIndirizzoComposto;
	}

	public Boolean getIsEliminabile() {
		return isEliminabile;
	}

	public void setIsEliminabile(Boolean isEliminabile) {
		this.isEliminabile = isEliminabile;
	}

	public Boolean getIsModificabile() {
		return isModificabile;
	}

	public void setIsModificabile(Boolean isModificabile) {
		this.isModificabile = isModificabile;
	}

	public Long getCivico() {
		return civico;
	}

	public void setCivico(Long civico) {
		this.civico = civico;
	}

	public String getCodIstatComune() {
		return codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public Boolean getFlagSedeAmministrativa() {
		return flagSedeAmministrativa;
	}

	public void setFlagSedeAmministrativa(Boolean flagSedeAmministrativa) {
		this.flagSedeAmministrativa = flagSedeAmministrativa;
	}

	public SedeProgetto() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R1386193091) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
