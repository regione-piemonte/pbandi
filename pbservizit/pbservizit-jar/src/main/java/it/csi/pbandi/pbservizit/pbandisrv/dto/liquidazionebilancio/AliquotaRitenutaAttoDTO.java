/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R1732681458) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AliquotaRitenutaAttoDTO.
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
public class AliquotaRitenutaAttoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idAliquotaRitenuta = null;

	/**
	 * @generated
	 */
	public void setIdAliquotaRitenuta(java.lang.Long val) {
		idAliquotaRitenuta = val;
	}

	////{PROTECTED REGION ID(R-392757053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAliquotaRitenuta. 
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
	public java.lang.Long getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.String codOnere = null;

	/**
	 * @generated
	 */
	public void setCodOnere(java.lang.String val) {
		codOnere = val;
	}

	////{PROTECTED REGION ID(R-242024077) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codOnere. 
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
	public java.lang.String getCodOnere() {
		return codOnere;
	}

	/**
	 * @generated
	 */
	private java.lang.String codNaturaOnere = null;

	/**
	 * @generated
	 */
	public void setCodNaturaOnere(java.lang.String val) {
		codNaturaOnere = val;
	}

	////{PROTECTED REGION ID(R633689872) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codNaturaOnere. 
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
	public java.lang.String getCodNaturaOnere() {
		return codNaturaOnere;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNaturaOnere = null;

	/**
	 * @generated
	 */
	public void setDescNaturaOnere(java.lang.String val) {
		descNaturaOnere = val;
	}

	////{PROTECTED REGION ID(R-895897677) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNaturaOnere. 
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
	public java.lang.String getDescNaturaOnere() {
		return descNaturaOnere;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percCaricoEnte = null;

	/**
	 * @generated
	 */
	public void setPercCaricoEnte(java.lang.Double val) {
		percCaricoEnte = val;
	}

	////{PROTECTED REGION ID(R1058213299) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percCaricoEnte. 
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
	public java.lang.Double getPercCaricoEnte() {
		return percCaricoEnte;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percCaricoSoggetto = null;

	/**
	 * @generated
	 */
	public void setPercCaricoSoggetto(java.lang.Double val) {
		percCaricoSoggetto = val;
	}

	////{PROTECTED REGION ID(R200443391) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percCaricoSoggetto. 
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
	public java.lang.Double getPercCaricoSoggetto() {
		return percCaricoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCaricoEnte = null;

	/**
	 * @generated
	 */
	public void setImportoCaricoEnte(java.lang.Double val) {
		importoCaricoEnte = val;
	}

	////{PROTECTED REGION ID(R-1947559149) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCaricoEnte. 
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
	public java.lang.Double getImportoCaricoEnte() {
		return importoCaricoEnte;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCaricoSoggetto = null;

	/**
	 * @generated
	 */
	public void setImportoCaricoSoggetto(java.lang.Double val) {
		importoCaricoSoggetto = val;
	}

	////{PROTECTED REGION ID(R-578526369) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCaricoSoggetto. 
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
	public java.lang.Double getImportoCaricoSoggetto() {
		return importoCaricoSoggetto;
	}

	/*PROTECTED REGION ID(R-1647522897) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
