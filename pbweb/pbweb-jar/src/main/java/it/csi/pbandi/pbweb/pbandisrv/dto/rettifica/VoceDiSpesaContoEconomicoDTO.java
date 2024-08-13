/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.rettifica;

////{PROTECTED REGION ID(R-1734498840) ENABLED START////}
/**
 * Inserire qui la documentazione della classe VoceDiSpesaContoEconomicoDTO.
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
public class VoceDiSpesaContoEconomicoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idContoEconomico = null;

	/**
	 * @generated
	 */
	public void setIdContoEconomico(java.lang.Long val) {
		idContoEconomico = val;
	}

	////{PROTECTED REGION ID(R20342784) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idContoEconomico. 
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
	public java.lang.Long getIdContoEconomico() {
		return idContoEconomico;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdVoceDiSpesa(java.lang.Long val) {
		idVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1884562925) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceDiSpesa. 
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
	public java.lang.Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
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

	////{PROTECTED REGION ID(R-1465028685) ENABLED START////}
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
	private java.lang.Double importoRendicontato = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontato(java.lang.Double val) {
		importoRendicontato = val;
	}

	////{PROTECTED REGION ID(R-232081556) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1929164575) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1750449726) ENABLED START////}
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
	private java.lang.Double importoAmmesso = null;

	/**
	 * @generated
	 */
	public void setImportoAmmesso(java.lang.Double val) {
		importoAmmesso = val;
	}

	////{PROTECTED REGION ID(R-338521111) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAmmesso. 
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
	public java.lang.Double getImportoAmmesso() {
		return importoAmmesso;
	}

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

	////{PROTECTED REGION ID(R1187912925) ENABLED START////}
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

	/*PROTECTED REGION ID(R-167095069) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
