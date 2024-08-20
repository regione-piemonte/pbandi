/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class UserInfo implements java.io.Serializable {

	/// Field [idIride]
	private java.lang.String _idIride = null;

	public void setIdIride(java.lang.String val) {
		_idIride = val;
	}

	public java.lang.String getIdIride() {
		return _idIride;
	}

	/// Field [codFisc]
	private java.lang.String _codFisc = null;

	public void setCodFisc(java.lang.String val) {
		_codFisc = val;
	}

	public java.lang.String getCodFisc() {
		return _codFisc;
	}

	/// Field [cognome]
	private java.lang.String _cognome = null;

	public void setCognome(java.lang.String val) {
		_cognome = val;
	}

	public java.lang.String getCognome() {
		return _cognome;
	}

	/// Field [nome]
	private java.lang.String _nome = null;

	public void setNome(java.lang.String val) {
		_nome = val;
	}

	public java.lang.String getNome() {
		return _nome;
	}

	/// Field [ente]
	private java.lang.String _ente = null;

	public void setEnte(java.lang.String val) {
		_ente = val;
	}

	public java.lang.String getEnte() {
		return _ente;
	}

	/// Field [ruolo]
	private java.lang.String _ruolo = null;

	public void setRuolo(java.lang.String val) {
		_ruolo = val;
	}

	public java.lang.String getRuolo() {
		return _ruolo;
	}

	/// Field [idUtente]
	private java.lang.Long _idUtente = null;

	public void setIdUtente(java.lang.Long val) {
		_idUtente = val;
	}

	public java.lang.Long getIdUtente() {
		return _idUtente;
	}

	/// Field [codiceRuolo]
	private java.lang.String _codiceRuolo = null;

	public void setCodiceRuolo(java.lang.String val) {
		_codiceRuolo = val;
	}

	public java.lang.String getCodiceRuolo() {
		return _codiceRuolo;
	}

	/// Field [idSoggetto]
	private java.lang.Long _idSoggetto = null;

	public void setIdSoggetto(java.lang.Long val) {
		_idSoggetto = val;
	}

	public java.lang.Long getIdSoggetto() {
		return _idSoggetto;
	}

	/// Field [idSoggettoIncaricante]
	private java.lang.Long _idSoggettoIncaricante = null;

	public void setIdSoggettoIncaricante(java.lang.Long val) {
		_idSoggettoIncaricante = val;
	}

	public java.lang.Long getIdSoggettoIncaricante() {
		return _idSoggettoIncaricante;
	}

	/// Field [ruoli]
	private java.util.ArrayList<Ruolo> _ruoli = new java.util.ArrayList<Ruolo>();

	public void setRuoli(
			java.util.ArrayList<Ruolo> val) {
		_ruoli = val;
	}

	public java.util.ArrayList<Ruolo> getRuoli() {
		return _ruoli;
	}

	/// Field [numeroBeneficiari]
	private java.lang.Long _numeroBeneficiari = null;

	public void setNumeroBeneficiari(java.lang.Long val) {
		_numeroBeneficiari = val;
	}

	public java.lang.Long getNumeroBeneficiari() {
		return _numeroBeneficiari;
	}

	/// Field [beneficiarioSelezionatoAutomaticamente]
	private java.lang.Boolean _beneficiarioSelezionatoAutomaticamente = null;

	public void setBeneficiarioSelezionatoAutomaticamente(java.lang.Boolean val) {
		_beneficiarioSelezionatoAutomaticamente = val;
	}

	public java.lang.Boolean getBeneficiarioSelezionatoAutomaticamente() {
		return _beneficiarioSelezionatoAutomaticamente;
	}

	/// Field [beneficiarioSelezionato]
	private Beneficiario _beneficiarioSelezionato = null;

	public void setBeneficiarioSelezionato(
			Beneficiario val) {
		_beneficiarioSelezionato = val;
	}

	public Beneficiario getBeneficiarioSelezionato() {
		return _beneficiarioSelezionato;
	}

	/// Field [codRuolo]
	private java.lang.String _codRuolo = null;

	public void setCodRuolo(java.lang.String val) {
		_codRuolo = val;
	}

	public java.lang.String getCodRuolo() {
		return _codRuolo;
	}

	/// Field [isIncaricato]
	private java.lang.Boolean _isIncaricato = null;

	public void setIsIncaricato(java.lang.Boolean val) {
		_isIncaricato = val;
	}

	public java.lang.Boolean getIsIncaricato() {
		return _isIncaricato;
	}

	/// Field [ruoloHelp]
	private java.lang.String _ruoloHelp = null;

	public void setRuoloHelp(java.lang.String val) {
		_ruoloHelp = val;
	}

	public java.lang.String getRuoloHelp() {
		return _ruoloHelp;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public UserInfo() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-154446683) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();

	}
	/*
	 * Metodo richiamato prima della serializzazione.
	 * Serve, in fase di serializzazione, a non far serializzare
	 * il proxy ma solo la proxy class.
	 */
	public Object writeReplace() throws java.io.ObjectStreamException {
		return this;
	}

	/*
	 * Metodo richiamato dopo la deserializzazione.
	 * Serve per ricreare il proxy.
	 */
	private Object readResolve() throws Exception {
		it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper userInfoHelper = (it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper) it.csi.util.beanlocatorfactory.ServiceBeanLocator
				.getBeanByName("userInfoHelper");
		return userInfoHelper.createUserInfoProxyPostDeserialization(this,
				UserInfo.class);
		/*PROTECTED REGION END*/
	}
}
