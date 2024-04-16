/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SoggettoPG implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.util.ArrayList<java.lang.String> ruolo = new java.util.ArrayList<java.lang.String>();
	private java.lang.String iban = null;
	private java.lang.String denominazione = null;
	private java.lang.String formaGiuridica = null;
	private java.lang.String settoreAttivita = null;
	private java.lang.String attivitaAteco = null;
	private java.lang.String dimensioneImpresa = null;
	private java.lang.String tipoDipDir = null;
	private java.lang.String partitaIvaSedeLegale = null;
	private java.lang.String indirizzoSedeLegale = null;
	private java.lang.String capSedeLegale = null;
	private java.lang.String emailSedeLegale = null;
	private java.lang.String pecSedeLegale = null;
	private java.lang.String faxSedeLegale = null;
	private java.lang.String telefonoSedeLegale = null;
	private java.lang.String dataCostituzioneAzienda = null;
	private java.lang.String denominazioneEnteDirReg = null;
	private java.lang.String ateneo = null;
	private java.lang.String denominazioneEnteDipUni = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune sedeLegale = null;
	private java.lang.String codiceFiscale = null;
	private java.lang.String idRelazioneColBeneficiario = null;
	private java.lang.String descRelazioneColBeneficiario = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabDipUni = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabDirReg = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabAltro = null;
	private java.lang.String numCivicoSedeLegale = null;
	private java.lang.String idSettoreEnte = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabPA = null;
	private java.lang.String denominazioneEntePA = null;

	public SoggettoPG() {
		super();
	}

	public java.util.ArrayList<java.lang.String> getRuolo() {
		return ruolo;
	}

	public void setRuolo(java.util.ArrayList<java.lang.String> ruolo) {
		this.ruolo = ruolo;
	}

	public java.lang.String getIban() {
		return iban;
	}

	public void setIban(java.lang.String iban) {
		this.iban = iban;
	}

	public java.lang.String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(java.lang.String denominazione) {
		this.denominazione = denominazione;
	}

	public java.lang.String getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(java.lang.String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public java.lang.String getSettoreAttivita() {
		return settoreAttivita;
	}

	public void setSettoreAttivita(java.lang.String settoreAttivita) {
		this.settoreAttivita = settoreAttivita;
	}

	public java.lang.String getAttivitaAteco() {
		return attivitaAteco;
	}

	public void setAttivitaAteco(java.lang.String attivitaAteco) {
		this.attivitaAteco = attivitaAteco;
	}

	public java.lang.String getDimensioneImpresa() {
		return dimensioneImpresa;
	}

	public void setDimensioneImpresa(java.lang.String dimensioneImpresa) {
		this.dimensioneImpresa = dimensioneImpresa;
	}

	public java.lang.String getTipoDipDir() {
		return tipoDipDir;
	}

	public void setTipoDipDir(java.lang.String tipoDipDir) {
		this.tipoDipDir = tipoDipDir;
	}

	public java.lang.String getPartitaIvaSedeLegale() {
		return partitaIvaSedeLegale;
	}

	public void setPartitaIvaSedeLegale(java.lang.String partitaIvaSedeLegale) {
		this.partitaIvaSedeLegale = partitaIvaSedeLegale;
	}

	public java.lang.String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}

	public void setIndirizzoSedeLegale(java.lang.String indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}

	public java.lang.String getCapSedeLegale() {
		return capSedeLegale;
	}

	public void setCapSedeLegale(java.lang.String capSedeLegale) {
		this.capSedeLegale = capSedeLegale;
	}

	public java.lang.String getEmailSedeLegale() {
		return emailSedeLegale;
	}

	public void setEmailSedeLegale(java.lang.String emailSedeLegale) {
		this.emailSedeLegale = emailSedeLegale;
	}

	public java.lang.String getPecSedeLegale() {
		return pecSedeLegale;
	}

	public void setPecSedeLegale(java.lang.String pecSedeLegale) {
		this.pecSedeLegale = pecSedeLegale;
	}

	public java.lang.String getFaxSedeLegale() {
		return faxSedeLegale;
	}

	public void setFaxSedeLegale(java.lang.String faxSedeLegale) {
		this.faxSedeLegale = faxSedeLegale;
	}

	public java.lang.String getTelefonoSedeLegale() {
		return telefonoSedeLegale;
	}

	public void setTelefonoSedeLegale(java.lang.String telefonoSedeLegale) {
		this.telefonoSedeLegale = telefonoSedeLegale;
	}

	public java.lang.String getDataCostituzioneAzienda() {
		return dataCostituzioneAzienda;
	}

	public void setDataCostituzioneAzienda(java.lang.String dataCostituzioneAzienda) {
		this.dataCostituzioneAzienda = dataCostituzioneAzienda;
	}

	public java.lang.String getDenominazioneEnteDirReg() {
		return denominazioneEnteDirReg;
	}

	public void setDenominazioneEnteDirReg(java.lang.String denominazioneEnteDirReg) {
		this.denominazioneEnteDirReg = denominazioneEnteDirReg;
	}

	public java.lang.String getAteneo() {
		return ateneo;
	}

	public void setAteneo(java.lang.String ateneo) {
		this.ateneo = ateneo;
	}

	public java.lang.String getDenominazioneEnteDipUni() {
		return denominazioneEnteDipUni;
	}

	public void setDenominazioneEnteDipUni(java.lang.String denominazioneEnteDipUni) {
		this.denominazioneEnteDipUni = denominazioneEnteDipUni;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public java.lang.String getIdRelazioneColBeneficiario() {
		return idRelazioneColBeneficiario;
	}

	public void setIdRelazioneColBeneficiario(java.lang.String idRelazioneColBeneficiario) {
		this.idRelazioneColBeneficiario = idRelazioneColBeneficiario;
	}

	public java.lang.String getDescRelazioneColBeneficiario() {
		return descRelazioneColBeneficiario;
	}

	public void setDescRelazioneColBeneficiario(java.lang.String descRelazioneColBeneficiario) {
		this.descRelazioneColBeneficiario = descRelazioneColBeneficiario;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg getTabDipUni() {
		return tabDipUni;
	}

	public void setTabDipUni(it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabDipUni) {
		this.tabDipUni = tabDipUni;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg getTabDirReg() {
		return tabDirReg;
	}

	public void setTabDirReg(it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabDirReg) {
		this.tabDirReg = tabDirReg;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg getTabAltro() {
		return tabAltro;
	}

	public void setTabAltro(it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabAltro) {
		this.tabAltro = tabAltro;
	}

	public java.lang.String getNumCivicoSedeLegale() {
		return numCivicoSedeLegale;
	}

	public void setNumCivicoSedeLegale(java.lang.String numCivicoSedeLegale) {
		this.numCivicoSedeLegale = numCivicoSedeLegale;
	}

	public java.lang.String getIdSettoreEnte() {
		return idSettoreEnte;
	}

	public void setIdSettoreEnte(java.lang.String idSettoreEnte) {
		this.idSettoreEnte = idSettoreEnte;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg getTabPA() {
		return tabPA;
	}

	public void setTabPA(it.csi.pbandi.pbworkspace.dto.schedaProgetto.TabDirRegSogg tabPA) {
		this.tabPA = tabPA;
	}

	public java.lang.String getDenominazioneEntePA() {
		return denominazioneEntePA;
	}

	public void setDenominazioneEntePA(java.lang.String denominazioneEntePA) {
		this.denominazioneEntePA = denominazioneEntePA;
	}

}
