/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R-1953178640) ENABLED START////}
/**
 * Inserire qui la documentazione della classe VoceDiSpesaAssociataDTO.
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
public class VoceDiSpesaAssociataDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R1032499467) ENABLED START////}
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

	////{PROTECTED REGION ID(R-908708933) ENABLED START////}
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
	private java.lang.String descVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescVoceDiSpesa(java.lang.String val) {
		descVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-128772651) ENABLED START////}
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
	private java.lang.Long idVoceDiSpesaMonit = null;

	/**
	 * @generated
	 */
	public void setIdVoceDiSpesaMonit(java.lang.Long val) {
		idVoceDiSpesaMonit = val;
	}

	////{PROTECTED REGION ID(R-911053076) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceDiSpesaMonit. 
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
	public java.lang.Long getIdVoceDiSpesaMonit() {
		return idVoceDiSpesaMonit;
	}

	/**
	 * @generated
	 */
	private java.lang.String codTipoVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setCodTipoVoceDiSpesa(java.lang.String val) {
		codTipoVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-765419878) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codTipoVoceDiSpesa. 
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
	public java.lang.String getCodTipoVoceDiSpesa() {
		return codTipoVoceDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrOrdinamento = null;

	/**
	 * @generated
	 */
	public void setProgrOrdinamento(java.lang.Long val) {
		progrOrdinamento = val;
	}

	////{PROTECTED REGION ID(R-2109441330) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrOrdinamento. 
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
	public java.lang.Long getProgrOrdinamento() {
		return progrOrdinamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipologiaVoceDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaVoceDiSpesa(java.lang.Long val) {
		idTipologiaVoceDiSpesa = val;
	}

	////{PROTECTED REGION ID(R357096185) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaVoceDiSpesa. 
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
	public java.lang.Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagEdit = null;

	/**
	 * @generated
	 */
	public void setFlagEdit(java.lang.String val) {
		flagEdit = val;
	}

	////{PROTECTED REGION ID(R-1562560634) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagEdit. 
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
	public java.lang.String getFlagEdit() {
		return flagEdit;
	}
	
	private String flagSpeseGestione;
	
	

	/*PROTECTED REGION ID(R-1261562703) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals

	public String getFlagSpeseGestione() {
		return flagSpeseGestione;
	}

	public void setFlagSpeseGestione(String flagSpeseGestione) {
		this.flagSpeseGestione = flagSpeseGestione;
	}

	public String toString() {
		String s = "\n";
		s = s + "idVoceDiSpesa=" + idVoceDiSpesa + "; ";
		s = s + "idVoceDiSpesaPadre=" + idVoceDiSpesaPadre + "; ";
		s = s + "idVoceDiSpesaMonit=" + idVoceDiSpesaMonit + "; ";
		s = s + "descVoceDiSpesa=" + descVoceDiSpesa + "; ";
		s = s + "codTipoVoceDiSpesa=" + codTipoVoceDiSpesa + "; ";
		s = s + "idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa + "; ";
		s = s + "flagEdit=" + flagEdit + "; ";
		s = s + "flagSpeseGestione=" + flagSpeseGestione + "; ";
		return s;
	}
	/*PROTECTED REGION END*/
}
