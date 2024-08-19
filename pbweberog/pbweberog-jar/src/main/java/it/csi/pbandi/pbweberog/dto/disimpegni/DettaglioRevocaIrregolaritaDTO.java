/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.disimpegni;

////{PROTECTED REGION ID(R-1310756974) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DettaglioRevocaIrregolaritaDTO.
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
public class DettaglioRevocaIrregolaritaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idDettRevocaIrreg = null;

	/**
	 * @generated
	 */
	public void setIdDettRevocaIrreg(java.lang.Long val) {
		idDettRevocaIrreg = val;
	}

	////{PROTECTED REGION ID(R348030451) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDettRevocaIrreg. 
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
	public java.lang.Long getIdDettRevocaIrreg() {
		return idDettRevocaIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRevoca = null;

	/**
	 * @generated
	 */
	public void setIdRevoca(java.lang.Long val) {
		idRevoca = val;
	}

	////{PROTECTED REGION ID(R1963344919) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRevoca. 
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
	public java.lang.Long getIdRevoca() {
		return idRevoca;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idIrregolarita = null;

	/**
	 * @generated
	 */
	public void setIdIrregolarita(java.lang.Long val) {
		idIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-244160854) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIrregolarita. 
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
	public java.lang.Long getIdIrregolarita() {
		return idIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idClassRevocaIrreg = null;

	/**
	 * @generated
	 */
	public void setIdClassRevocaIrreg(java.lang.Long val) {
		idClassRevocaIrreg = val;
	}

	////{PROTECTED REGION ID(R-1719655146) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idClassRevocaIrreg. 
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
	public java.lang.Long getIdClassRevocaIrreg() {
		return idClassRevocaIrreg;
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

	////{PROTECTED REGION ID(R-1729214056) ENABLED START////}
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
	private java.lang.Double importoAudit = null;

	/**
	 * @generated
	 */
	public void setImportoAudit(java.lang.Double val) {
		importoAudit = val;
	}

	////{PROTECTED REGION ID(R1690476835) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAudit. 
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
	public java.lang.Double getImportoAudit() {
		return importoAudit;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipologia = null;

	/**
	 * @generated
	 */
	public void setTipologia(java.lang.String val) {
		tipologia = val;
	}

	////{PROTECTED REGION ID(R-711495338) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipologia. 
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
	public java.lang.String getTipologia() {
		return tipologia;
	}

	/**
	 * @generated
	 */
	private DsDettaglioRevocaIrregolaritaDTO[] dsDettagliRevocaIrregolarita = null;

	/**
	 * @generated
	 */
	public void setDsDettagliRevocaIrregolarita(
			DsDettaglioRevocaIrregolaritaDTO[] val) {
		dsDettagliRevocaIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-2056552820) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dsDettagliRevocaIrregolarita. 
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
	public DsDettaglioRevocaIrregolaritaDTO[] getDsDettagliRevocaIrregolarita() {
		return dsDettagliRevocaIrregolarita;
	}

	/*PROTECTED REGION ID(R-1275895281) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
