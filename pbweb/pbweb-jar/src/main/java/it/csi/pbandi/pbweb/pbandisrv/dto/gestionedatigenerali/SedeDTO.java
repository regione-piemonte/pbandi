/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali;

////{PROTECTED REGION ID(R1417243142) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SedeDTO.
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
public class SedeDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idProgetto = null;

	/**
	 * @generated
	 */
	public void setIdProgetto(java.lang.Long val) {
		idProgetto = val;
	}

	////{PROTECTED REGION ID(R-442674555) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgetto. 
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
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoBeneficiario(java.lang.Long val) {
		idSoggettoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R1664940097) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoBeneficiario. 
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
	public java.lang.Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSede = null;

	/**
	 * @generated
	 */
	public void setIdSede(java.lang.Long val) {
		idSede = val;
	}

	////{PROTECTED REGION ID(R-1602447468) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSede. 
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
	public java.lang.Long getIdSede() {
		return idSede;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrSoggettoProgettoSede = null;

	/**
	 * @generated
	 */
	public void setProgrSoggettoProgettoSede(java.lang.Long val) {
		progrSoggettoProgettoSede = val;
	}

	////{PROTECTED REGION ID(R-969922513) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrSoggettoProgettoSede. 
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
	public java.lang.Long getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoSede = null;

	/**
	 * @generated
	 */
	public void setIdTipoSede(java.lang.Long val) {
		idTipoSede = val;
	}

	////{PROTECTED REGION ID(R-1423500184) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoSede. 
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
	public java.lang.Long getIdTipoSede() {
		return idTipoSede;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoSede = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoSede(java.lang.String val) {
		descBreveTipoSede = val;
	}

	////{PROTECTED REGION ID(R-1768218892) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoSede. 
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
	public java.lang.String getDescBreveTipoSede() {
		return descBreveTipoSede;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoSede = null;

	/**
	 * @generated
	 */
	public void setDescTipoSede(java.lang.String val) {
		descTipoSede = val;
	}

	////{PROTECTED REGION ID(R-1618760098) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoSede. 
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
	public java.lang.String getDescTipoSede() {
		return descTipoSede;
	}

	/**
	 * @generated
	 */
	private java.lang.String partitaIva = null;

	/**
	 * @generated
	 */
	public void setPartitaIva(java.lang.String val) {
		partitaIva = val;
	}

	////{PROTECTED REGION ID(R50214039) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIva. 
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
	public java.lang.String getPartitaIva() {
		return partitaIva;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNazione = null;

	/**
	 * @generated
	 */
	public void setIdNazione(java.lang.Long val) {
		idNazione = val;
	}

	////{PROTECTED REGION ID(R-187545053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNazione. 
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
	public java.lang.Long getIdNazione() {
		return idNazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNazione = null;

	/**
	 * @generated
	 */
	public void setDescNazione(java.lang.String val) {
		descNazione = val;
	}

	////{PROTECTED REGION ID(R775987565) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNazione. 
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
	public java.lang.String getDescNazione() {
		return descNazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRegione = null;

	/**
	 * @generated
	 */
	public void setIdRegione(java.lang.Long val) {
		idRegione = val;
	}

	////{PROTECTED REGION ID(R-835527920) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRegione. 
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
	public java.lang.Long getIdRegione() {
		return idRegione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descRegione = null;

	/**
	 * @generated
	 */
	public void setDescRegione(java.lang.String val) {
		descRegione = val;
	}

	////{PROTECTED REGION ID(R128004698) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descRegione. 
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
	public java.lang.String getDescRegione() {
		return descRegione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProvincia = null;

	/**
	 * @generated
	 */
	public void setIdProvincia(java.lang.Long val) {
		idProvincia = val;
	}

	////{PROTECTED REGION ID(R-405073140) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProvincia. 
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
	public java.lang.Long getIdProvincia() {
		return idProvincia;
	}

	/**
	 * @generated
	 */
	private java.lang.String descProvincia = null;

	/**
	 * @generated
	 */
	public void setDescProvincia(java.lang.String val) {
		descProvincia = val;
	}

	////{PROTECTED REGION ID(R2131804118) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descProvincia. 
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
	public java.lang.String getDescProvincia() {
		return descProvincia;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComune = null;

	/**
	 * @generated
	 */
	public void setIdComune(java.lang.Long val) {
		idComune = val;
	}

	////{PROTECTED REGION ID(R1492698316) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComune. 
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
	public java.lang.Long getIdComune() {
		return idComune;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComune = null;

	/**
	 * @generated
	 */
	public void setDescComune(java.lang.String val) {
		descComune = val;
	}

	////{PROTECTED REGION ID(R-2078450622) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComune. 
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
	public java.lang.String getDescComune() {
		return descComune;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idIndirizzo = null;

	/**
	 * @generated
	 */
	public void setIdIndirizzo(java.lang.Long val) {
		idIndirizzo = val;
	}

	////{PROTECTED REGION ID(R-555700019) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIndirizzo. 
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
	public java.lang.Long getIdIndirizzo() {
		return idIndirizzo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descIndirizzo = null;

	/**
	 * @generated
	 */
	public void setDescIndirizzo(java.lang.String val) {
		descIndirizzo = val;
	}

	////{PROTECTED REGION ID(R1981177239) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descIndirizzo. 
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
	public java.lang.String getDescIndirizzo() {
		return descIndirizzo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRecapiti = null;

	/**
	 * @generated
	 */
	public void setIdRecapiti(java.lang.Long val) {
		idRecapiti = val;
	}

	////{PROTECTED REGION ID(R-253440960) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRecapiti. 
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
	public java.lang.Long getIdRecapiti() {
		return idRecapiti;
	}

	/**
	 * @generated
	 */
	private java.lang.String email = null;

	/**
	 * @generated
	 */
	public void setEmail(java.lang.String val) {
		email = val;
	}

	////{PROTECTED REGION ID(R-2133314218) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo email. 
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
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * @generated
	 */
	private java.lang.String fax = null;

	/**
	 * @generated
	 */
	public void setFax(java.lang.String val) {
		fax = val;
	}

	////{PROTECTED REGION ID(R1562024791) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fax. 
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
	public java.lang.String getFax() {
		return fax;
	}

	/**
	 * @generated
	 */
	private java.lang.String telefono = null;

	/**
	 * @generated
	 */
	public void setTelefono(java.lang.String val) {
		telefono = val;
	}

	////{PROTECTED REGION ID(R1240624890) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo telefono. 
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
	public java.lang.String getTelefono() {
		return telefono;
	}

	/**
	 * @generated
	 */
	private java.lang.String cap = null;

	/**
	 * @generated
	 */
	public void setCap(java.lang.String val) {
		cap = val;
	}

	////{PROTECTED REGION ID(R1562021900) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cap. 
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
	public java.lang.String getCap() {
		return cap;
	}

	/**
	 * @generated
	 */
	private java.lang.String codIstatComune = null;

	/**
	 * @generated
	 */
	public void setCodIstatComune(java.lang.String val) {
		codIstatComune = val;
	}

	////{PROTECTED REGION ID(R1968566102) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codIstatComune. 
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
	public java.lang.String getCodIstatComune() {
		return codIstatComune;
	}

	/**
	 * @generated
	 */
	private java.lang.Long civico = null;

	/**
	 * @generated
	 */
	public void setCivico(java.lang.Long val) {
		civico = val;
	}

	////{PROTECTED REGION ID(R-1768558261) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo civico. 
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
	public java.lang.Long getCivico() {
		return civico;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagSedeAmministrativa = null;

	/**
	 * @generated
	 */
	public void setFlagSedeAmministrativa(java.lang.String val) {
		flagSedeAmministrativa = val;
	}

	////{PROTECTED REGION ID(R144573176) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagSedeAmministrativa. 
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
	public java.lang.String getFlagSedeAmministrativa() {
		return flagSedeAmministrativa;
	}

	/*PROTECTED REGION ID(R-332520187) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
