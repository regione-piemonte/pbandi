/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R224246172) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FideiussioneDTO.
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
public class FideiussioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String descrizione = null;

	/**
	 * @generated
	 */
	public void setDescrizione(java.lang.String val) {
		descrizione = val;
	}

	////{PROTECTED REGION ID(R-1862077901) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizione. 
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
	public java.lang.String getDescrizione() {
		return descrizione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrizioneTipoTrattamento = null;

	/**
	 * @generated
	 */
	public void setDescrizioneTipoTrattamento(java.lang.String val) {
		descrizioneTipoTrattamento = val;
	}

	////{PROTECTED REGION ID(R899157420) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneTipoTrattamento. 
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
	public java.lang.String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDecorrenza = null;

	/**
	 * @generated
	 */
	public void setDataDecorrenza(java.util.Date val) {
		dataDecorrenza = val;
	}

	////{PROTECTED REGION ID(R15860387) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDecorrenza. 
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
	public java.util.Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataScadenza = null;

	/**
	 * @generated
	 */
	public void setDataScadenza(java.util.Date val) {
		dataScadenza = val;
	}

	////{PROTECTED REGION ID(R183182313) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataScadenza. 
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
	public java.util.Date getDataScadenza() {
		return dataScadenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String numero = null;

	/**
	 * @generated
	 */
	public void setNumero(java.lang.String val) {
		numero = val;
	}

	////{PROTECTED REGION ID(R856947896) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numero. 
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
	public java.lang.String getNumero() {
		return numero;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importo = null;

	/**
	 * @generated
	 */
	public void setImporto(java.lang.Double val) {
		importo = val;
	}

	////{PROTECTED REGION ID(R427065422) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importo. 
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
	public java.lang.Double getImporto() {
		return importo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleTipoTrattamento = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleTipoTrattamento(java.lang.Double val) {
		importoTotaleTipoTrattamento = val;
	}

	////{PROTECTED REGION ID(R-1942612784) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleTipoTrattamento. 
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
	public java.lang.Double getImportoTotaleTipoTrattamento() {
		return importoTotaleTipoTrattamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long id = null;

	/**
	 * @generated
	 */
	public void setId(java.lang.Long val) {
		id = val;
	}

	////{PROTECTED REGION ID(R752209847) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo id. 
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
	public java.lang.Long getId() {
		return id;
	}

	/*PROTECTED REGION ID(R-157127633) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}