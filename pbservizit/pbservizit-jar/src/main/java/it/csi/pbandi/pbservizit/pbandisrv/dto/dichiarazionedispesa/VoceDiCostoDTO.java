/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa;

////{PROTECTED REGION ID(R-1209242067) ENABLED START////}
/**
 * Inserire qui la documentazione della classe VoceDiCostoDTO.
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
public class VoceDiCostoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String descVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescVoceDiSpesa(java.lang.String val) {
		descVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1553522184) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descVoceDiSpesa. 
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
	public java.lang.String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVoceSpesa = null;

	/**
	 * @generated
	 */
	public void setIdVoceSpesa(java.lang.Long val) {
		idVoceSpesa = val;
	}

	////{PROTECTED REGION ID(R-1977444685) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceSpesa. 
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
	public java.lang.Long getIdVoceSpesa() {
		return idVoceSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVoceDiSpesaPadre = null;

	/**
	 * @generated
	 */
	public void setIdVoceDiSpesaPadre(java.lang.Long val) {
		idVoceDiSpesaPadre = val;
	}

	////{PROTECTED REGION ID(R1539739832) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceDiSpesaPadre. 
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
	public java.lang.Long getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}

	/**
	 * @generated
	 */
	private java.lang.String descVoceDiSpesaPadre = null;

	/**
	 * @generated
	 */
	public void setDescVoceDiSpesaPadre(java.lang.String val) {
		descVoceDiSpesaPadre = val;
	}

	////{PROTECTED REGION ID(R1621905134) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descVoceDiSpesaPadre. 
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
	public java.lang.String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAmmessoAFinanziamento = null;

	/**
	 * @generated
	 */
	public void setImportoAmmessoAFinanziamento(java.lang.Double val) {
		importoAmmessoAFinanziamento = val;
	}

	////{PROTECTED REGION ID(R1046015140) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAmmessoAFinanziamento. 
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
	public java.lang.Double getImportoAmmessoAFinanziamento() {
		return importoAmmessoAFinanziamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoQuietanzato = null;

	/**
	 * @generated
	 */
	public void setImportoQuietanzato(java.lang.Double val) {
		importoQuietanzato = val;
	}

	////{PROTECTED REGION ID(R1075603942) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoQuietanzato. 
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
	public java.lang.Double getImportoQuietanzato() {
		return importoQuietanzato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoValidato = null;

	/**
	 * @generated
	 */
	public void setImportoValidato(java.lang.Double val) {
		importoValidato = val;
	}

	////{PROTECTED REGION ID(R-196917539) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoValidato. 
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
	public java.lang.Double getImportoValidato() {
		return importoValidato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRendicontato = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontato(java.lang.Double val) {
		importoRendicontato = val;
	}

	////{PROTECTED REGION ID(R-1573538041) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRendicontato. 
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
	public java.lang.Double getImportoRendicontato() {
		return importoRendicontato;
	}

	/*PROTECTED REGION ID(R-870944876) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
