/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R-1758021765) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SpesaProgettoDTO.
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
public class SpesaProgettoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String tipoOperazione = null;

	/**
	 * @generated
	 */
	public void setTipoOperazione(java.lang.String val) {
		tipoOperazione = val;
	}

	////{PROTECTED REGION ID(R1453041523) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoOperazione. 
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
	public java.lang.String getTipoOperazione() {
		return tipoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoTotaleInvestimento = null;

	/**
	 * @generated
	 */
	public void setCostoTotaleInvestimento(java.lang.Double val) {
		costoTotaleInvestimento = val;
	}

	////{PROTECTED REGION ID(R1839350917) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoTotaleInvestimento. 
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
	public java.lang.Double getCostoTotaleInvestimento() {
		return costoTotaleInvestimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAmmessoContributo = null;

	/**
	 * @generated
	 */
	public void setImportoAmmessoContributo(java.lang.Double val) {
		importoAmmessoContributo = val;
	}

	////{PROTECTED REGION ID(R1627854803) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAmmessoContributo. 
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
	public java.lang.Double getImportoAmmessoContributo() {
		return importoAmmessoContributo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleSpesaSostenuta = null;

	/**
	 * @generated
	 */
	public void setTotaleSpesaSostenuta(java.lang.Double val) {
		totaleSpesaSostenuta = val;
	}

	////{PROTECTED REGION ID(R1657843554) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleSpesaSostenuta. 
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
	public java.lang.Double getTotaleSpesaSostenuta() {
		return totaleSpesaSostenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double avanzamentoSpesaSostenuta = null;

	/**
	 * @generated
	 */
	public void setAvanzamentoSpesaSostenuta(java.lang.Double val) {
		avanzamentoSpesaSostenuta = val;
	}

	////{PROTECTED REGION ID(R-1375316717) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo avanzamentoSpesaSostenuta. 
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
	public java.lang.Double getAvanzamentoSpesaSostenuta() {
		return avanzamentoSpesaSostenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleSpesaValidata = null;

	/**
	 * @generated
	 */
	public void setTotaleSpesaValidata(java.lang.Double val) {
		totaleSpesaValidata = val;
	}

	////{PROTECTED REGION ID(R-1119116884) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleSpesaValidata. 
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
	public java.lang.Double getTotaleSpesaValidata() {
		return totaleSpesaValidata;
	}

	/**
	 * @generated
	 */
	private java.lang.Double avanzamentoSpesaValidata = null;

	/**
	 * @generated
	 */
	public void setAvanzamentoSpesaValidata(java.lang.Double val) {
		avanzamentoSpesaValidata = val;
	}

	////{PROTECTED REGION ID(R1692533211) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo avanzamentoSpesaValidata. 
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
	public java.lang.Double getAvanzamentoSpesaValidata() {
		return avanzamentoSpesaValidata;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleSpesaAmmessa = null;

	/**
	 * @generated
	 */
	public void setTotaleSpesaAmmessa(java.lang.Double val) {
		totaleSpesaAmmessa = val;
	}

	////{PROTECTED REGION ID(R-318155549) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleSpesaAmmessa. 
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
	public java.lang.Double getTotaleSpesaAmmessa() {
		return totaleSpesaAmmessa;
	}

	/*PROTECTED REGION ID(R-977332922) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
