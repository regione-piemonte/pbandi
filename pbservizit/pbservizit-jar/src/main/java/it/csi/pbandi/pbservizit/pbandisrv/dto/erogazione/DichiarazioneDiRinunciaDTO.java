/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R713607103) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DichiarazioneDiRinunciaDTO.
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
public class DichiarazioneDiRinunciaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.util.Date dataRinuncia = null;

	/**
	 * @generated
	 */
	public void setDataRinuncia(java.util.Date val) {
		dataRinuncia = val;
	}

	////{PROTECTED REGION ID(R1921107924) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataRinuncia. 
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
	public java.util.Date getDataRinuncia() {
		return dataRinuncia;
	}

	/**
	 * @generated
	 */
	private java.lang.String fileName = null;

	/**
	 * @generated
	 */
	public void setFileName(java.lang.String val) {
		fileName = val;
	}

	////{PROTECTED REGION ID(R-939106586) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fileName. 
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
	public java.lang.String getFileName() {
		return fileName;
	}

	/**
	 * @generated
	 */
	private java.lang.Long giorniRinuncia = null;

	/**
	 * @generated
	 */
	public void setGiorniRinuncia(java.lang.Long val) {
		giorniRinuncia = val;
	}

	////{PROTECTED REGION ID(R-1858795222) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo giorniRinuncia. 
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
	public java.lang.Long getGiorniRinuncia() {
		return giorniRinuncia;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDelegato = null;

	/**
	 * @generated
	 */
	public void setIdDelegato(java.lang.Long val) {
		idDelegato = val;
	}

	////{PROTECTED REGION ID(R-811046711) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDelegato. 
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
	public java.lang.Long getIdDelegato() {
		return idDelegato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoIndex = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoIndex(java.lang.Long val) {
		idDocumentoIndex = val;
	}

	////{PROTECTED REGION ID(R1112419896) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoIndex. 
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
	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

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

	////{PROTECTED REGION ID(R1663147262) ENABLED START////}
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
	private java.lang.Long idRappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setIdRappresentanteLegale(java.lang.Long val) {
		idRappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R2049319678) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRappresentanteLegale. 
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
	public java.lang.Long getIdRappresentanteLegale() {
		return idRappresentanteLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDaRestituire = null;

	/**
	 * @generated
	 */
	public void setImportoDaRestituire(java.lang.Double val) {
		importoDaRestituire = val;
	}

	////{PROTECTED REGION ID(R-1794312946) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDaRestituire. 
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
	public java.lang.Double getImportoDaRestituire() {
		return importoDaRestituire;
	}

	/**
	 * @generated
	 */
	private java.lang.String motivoRinuncia = null;

	/**
	 * @generated
	 */
	public void setMotivoRinuncia(java.lang.String val) {
		motivoRinuncia = val;
	}

	////{PROTECTED REGION ID(R2043543290) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo motivoRinuncia. 
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
	public java.lang.String getMotivoRinuncia() {
		return motivoRinuncia;
	}

	/**
	 * @generated
	 */
	private java.lang.String uuidNodoIndex = null;

	/**
	 * @generated
	 */
	public void setUuidNodoIndex(java.lang.String val) {
		uuidNodoIndex = val;
	}

	////{PROTECTED REGION ID(R-968251572) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo uuidNodoIndex. 
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
	public java.lang.String getUuidNodoIndex() {
		return uuidNodoIndex;
	}

	/*PROTECTED REGION ID(R432912578) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
