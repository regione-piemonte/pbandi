/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

////{PROTECTED REGION ID(R2063733020) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SedeLegaleBeneficiarioCspDTO.
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
public class SedeLegaleBeneficiarioCspDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;
	
	private String civico = null;

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
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

	////{PROTECTED REGION ID(R-1681295315) ENABLED START////}
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
	private java.lang.String descIndirizzo = null;

	/**
	 * @generated
	 */
	public void setDescIndirizzo(java.lang.String val) {
		descIndirizzo = val;
	}

	////{PROTECTED REGION ID(R1143237185) ENABLED START////}
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
	private java.lang.String cap = null;

	/**
	 * @generated
	 */
	public void setCap(java.lang.String val) {
		cap = val;
	}

	////{PROTECTED REGION ID(R-1786345162) ENABLED START////}
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
	private java.lang.Long idNazione = null;

	/**
	 * @generated
	 */
	public void setIdNazione(java.lang.Long val) {
		idNazione = val;
	}

	////{PROTECTED REGION ID(R-1905968179) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1361194857) ENABLED START////}
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

	////{PROTECTED REGION ID(R1741016250) ENABLED START////}
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

	////{PROTECTED REGION ID(R-2009177724) ENABLED START////}
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

	////{PROTECTED REGION ID(R1752711734) ENABLED START////}
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

	////{PROTECTED REGION ID(R1293864064) ENABLED START////}
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

	////{PROTECTED REGION ID(R-640944670) ENABLED START////}
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

	////{PROTECTED REGION ID(R485007320) ENABLED START////}
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
	private java.lang.Long idComuneEstero = null;

	/**
	 * @generated
	 */
	public void setIdComuneEstero(java.lang.Long val) {
		idComuneEstero = val;
	}

	////{PROTECTED REGION ID(R-1681278306) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComuneEstero. 
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
	public java.lang.Long getIdComuneEstero() {
		return idComuneEstero;
	}

	/**
	 * @generated
	 */
	private java.lang.String descComuneEstero = null;

	/**
	 * @generated
	 */
	public void setDescComuneEstero(java.lang.String val) {
		descComuneEstero = val;
	}

	////{PROTECTED REGION ID(R-331312108) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descComuneEstero. 
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
	public java.lang.String getDescComuneEstero() {
		return descComuneEstero;
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

	////{PROTECTED REGION ID(R1311411200) ENABLED START////}
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
	private java.lang.String telefono = null;

	/**
	 * @generated
	 */
	public void setTelefono(java.lang.String val) {
		telefono = val;
	}

	////{PROTECTED REGION ID(R-893018096) ENABLED START////}
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
	private java.lang.String fax = null;

	/**
	 * @generated
	 */
	public void setFax(java.lang.String val) {
		fax = val;
	}

	////{PROTECTED REGION ID(R-1786342271) ENABLED START////}
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

	/*PROTECTED REGION ID(R-46960529) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
