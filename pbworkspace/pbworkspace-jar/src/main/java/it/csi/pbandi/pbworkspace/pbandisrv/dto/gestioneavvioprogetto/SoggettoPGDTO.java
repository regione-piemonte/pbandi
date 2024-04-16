/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

////{PROTECTED REGION ID(R1416506846) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SoggettoPGDTO.
 * Consigli:
 * <ul>
 * <li> Descrivere il "concetto" rappresentato dall'entita' (qual'� l'oggetto
 *      del dominio del servizio rappresentato)
 * <li> Se necessario indicare se questo concetto � mantenuto sugli archivi di
 *      una particolare applicazione
 * <li> Se l'oggetto ha un particolare ciclo di vita (stati, es. creato, da approvare, 
 *      approvato, respinto, annullato.....) si pu� decidere di descrivere
 *      la state machine qui o nella documentazione dell'interfaccia del servizio
 *      che manipola quest'oggetto
 * </ul>
 * @generated
 */
////{PROTECTED REGION END////}
public class SoggettoPGDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String iban = null;

	/**
	 * @generated
	 */
	public void setIban(java.lang.String val) {
		iban = val;
	}

	////{PROTECTED REGION ID(R-201767452) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo iban. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getIban() {
		return iban;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazione = null;

	/**
	 * @generated
	 */
	public void setDenominazione(java.lang.String val) {
		denominazione = val;
	}

	////{PROTECTED REGION ID(R-413908728) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazione() {
		return denominazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String formaGiuridica = null;

	/**
	 * @generated
	 */
	public void setFormaGiuridica(java.lang.String val) {
		formaGiuridica = val;
	}

	////{PROTECTED REGION ID(R-177356370) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo formaGiuridica. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFormaGiuridica() {
		return formaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.String settoreAttivita = null;

	/**
	 * @generated
	 */
	public void setSettoreAttivita(java.lang.String val) {
		settoreAttivita = val;
	}

	////{PROTECTED REGION ID(R-947014118) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo settoreAttivita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getSettoreAttivita() {
		return settoreAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String attivitaAteco = null;

	/**
	 * @generated
	 */
	public void setAttivitaAteco(java.lang.String val) {
		attivitaAteco = val;
	}

	////{PROTECTED REGION ID(R-1014607880) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo attivitaAteco. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAttivitaAteco() {
		return attivitaAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String dimensioneImpresa = null;

	/**
	 * @generated
	 */
	public void setDimensioneImpresa(java.lang.String val) {
		dimensioneImpresa = val;
	}

	////{PROTECTED REGION ID(R1286843056) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dimensioneImpresa. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDimensioneImpresa() {
		return dimensioneImpresa;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoDipDir = null;

	/**
	 * @generated
	 */
	public void setTipoDipDir(java.lang.String val) {
		tipoDipDir = val;
	}

	////{PROTECTED REGION ID(R-765316940) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoDipDir. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTipoDipDir() {
		return tipoDipDir;
	}

	/**
	 * @generated
	 */
	private java.lang.String partitaIvaSedeLegale = null;

	/**
	 * @generated
	 */
	public void setPartitaIvaSedeLegale(java.lang.String val) {
		partitaIvaSedeLegale = val;
	}

	////{PROTECTED REGION ID(R1942047950) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIvaSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getPartitaIvaSedeLegale() {
		return partitaIvaSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String indirizzoSedeLegale = null;

	/**
	 * @generated
	 */
	public void setIndirizzoSedeLegale(java.lang.String val) {
		indirizzoSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1416412561) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo indirizzoSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String capSedeLegale = null;

	/**
	 * @generated
	 */
	public void setCapSedeLegale(java.lang.String val) {
		capSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1590989485) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo capSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCapSedeLegale() {
		return capSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String emailSedeLegale = null;

	/**
	 * @generated
	 */
	public void setEmailSedeLegale(java.lang.String val) {
		emailSedeLegale = val;
	}

	////{PROTECTED REGION ID(R1717064733) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo emailSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getEmailSedeLegale() {
		return emailSedeLegale;
	}

	private String pecSedeLegale;
	
	public String getPecSedeLegale() {
		return pecSedeLegale;
	}

	public void setPecSedeLegale(String pecSedeLegale) {
		this.pecSedeLegale = pecSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String faxSedeLegale = null;

	/**
	 * @generated
	 */
	public void setFaxSedeLegale(java.lang.String val) {
		faxSedeLegale = val;
	}

	////{PROTECTED REGION ID(R333059806) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo faxSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFaxSedeLegale() {
		return faxSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String telefonoSedeLegale = null;

	/**
	 * @generated
	 */
	public void setTelefonoSedeLegale(java.lang.String val) {
		telefonoSedeLegale = val;
	}

	////{PROTECTED REGION ID(R399392113) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo telefonoSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTelefonoSedeLegale() {
		return telefonoSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneEnteDirReg = null;

	/**
	 * @generated
	 */
	public void setDenominazioneEnteDirReg(java.lang.String val) {
		denominazioneEnteDirReg = val;
	}

	////{PROTECTED REGION ID(R2129902953) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneEnteDirReg. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazioneEnteDirReg() {
		return denominazioneEnteDirReg;
	}

	/**
	 * @generated
	 */
	private java.lang.String ateneo = null;

	/**
	 * @generated
	 */
	public void setAteneo(java.lang.String val) {
		ateneo = val;
	}

	////{PROTECTED REGION ID(R-837280476) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ateneo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAteneo() {
		return ateneo;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneEnteDipUni = null;

	/**
	 * @generated
	 */
	public void setDenominazioneEnteDipUni(java.lang.String val) {
		denominazioneEnteDipUni = val;
	}

	////{PROTECTED REGION ID(R2129846535) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneEnteDipUni. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazioneEnteDipUni() {
		return denominazioneEnteDipUni;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ComuneDTO sedeLegale = null;

	/**
	 * @generated
	 */
	public void setSedeLegale(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ComuneDTO val) {
		sedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1427810979) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.ComuneDTO getSedeLegale() {
		return sedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscale = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscale(java.lang.String val) {
		codiceFiscale = val;
	}

	////{PROTECTED REGION ID(R439415734) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] ruolo = null;

	/**
	 * @generated
	 */
	public void setRuolo(java.lang.String[] val) {
		ruolo = val;
	}

	////{PROTECTED REGION ID(R-1950932495) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ruolo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String[] getRuolo() {
		return ruolo;
	}

	/**
	 * @generated
	 */
	private java.lang.String idRelazioneColBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdRelazioneColBeneficiario(java.lang.String val) {
		idRelazioneColBeneficiario = val;
	}

	////{PROTECTED REGION ID(R233490308) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRelazioneColBeneficiario. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getIdRelazioneColBeneficiario() {
		return idRelazioneColBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String descRelazioneColBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDescRelazioneColBeneficiario(java.lang.String val) {
		descRelazioneColBeneficiario = val;
	}

	////{PROTECTED REGION ID(R831428474) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descRelazioneColBeneficiario. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescRelazioneColBeneficiario() {
		return descRelazioneColBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataCostituzioneAzienda = null;

	/**
	 * @generated
	 */
	public void setDataCostituzioneAzienda(java.lang.String val) {
		dataCostituzioneAzienda = val;
	}

	////{PROTECTED REGION ID(R566100980) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataCostituzioneAzienda. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDataCostituzioneAzienda() {
		return dataCostituzioneAzienda;
	}

	/**
	 * @generated
	 */
	private java.lang.String numCivicoSedeLegale = null;

	/**
	 * @generated
	 */
	public void setNumCivicoSedeLegale(java.lang.String val) {
		numCivicoSedeLegale = val;
	}

	////{PROTECTED REGION ID(R840527788) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numCivicoSedeLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumCivicoSedeLegale() {
		return numCivicoSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String idSettoreEnte = null;

	/**
	 * @generated
	 */
	public void setIdSettoreEnte(java.lang.String val) {
		idSettoreEnte = val;
	}

	////{PROTECTED REGION ID(R510704913) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreEnte. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getIdSettoreEnte() {
		return idSettoreEnte;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneEntePA = null;

	/**
	 * @generated
	 */
	public void setDenominazioneEntePA(java.lang.String val) {
		denominazioneEntePA = val;
	}

	////{PROTECTED REGION ID(R-1332471949) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneEntePA. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazioneEntePA() {
		return denominazioneEntePA;
	}

	/*PROTECTED REGION ID(R-1681160125) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
