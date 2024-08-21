/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori;

////{PROTECTED REGION ID(R2126431463) ENABLED START////}
/**
 * Inserire qui la documentazione della classe IndicatoreDTO.
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
public class IndicatoreDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idIndicatore = null;

	/**
	 * @generated
	 */
	public void setIdIndicatore(java.lang.Long val) {
		idIndicatore = val;
	}

	////{PROTECTED REGION ID(R2137008664) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIndicatore. 
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
	public java.lang.Long getIdIndicatore() {
		return idIndicatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descIndicatore = null;

	/**
	 * @generated
	 */
	public void setDescIndicatore(java.lang.String val) {
		descIndicatore = val;
	}

	////{PROTECTED REGION ID(R-1654570418) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descIndicatore. 
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
	public java.lang.String getDescIndicatore() {
		return descIndicatore;
	}

	/**
	 * @generated
	 */
	private boolean flagObbligatorio = true;

	/**
	 * @generated
	 */
	public void setFlagObbligatorio(boolean val) {
		flagObbligatorio = val;
	}

	////{PROTECTED REGION ID(R1860375658) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagObbligatorio. 
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
	public boolean getFlagObbligatorio() {
		return flagObbligatorio;
	}

	/**
	 * @generated
	 */
	private java.lang.String codIgrue = null;

	/**
	 * @generated
	 */
	public void setCodIgrue(java.lang.String val) {
		codIgrue = val;
	}

	////{PROTECTED REGION ID(R-633546765) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codIgrue. 
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
	public java.lang.String getCodIgrue() {
		return codIgrue;
	}

	/**
	 * @generated
	 */
	private java.lang.String descUnitaMisura = null;

	/**
	 * @generated
	 */
	public void setDescUnitaMisura(java.lang.String val) {
		descUnitaMisura = val;
	}

	////{PROTECTED REGION ID(R-654575022) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descUnitaMisura. 
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
	public java.lang.String getDescUnitaMisura() {
		return descUnitaMisura;
	}

	/**
	 * @generated
	 */
	private java.lang.Double valoreIniziale = null;

	/**
	 * @generated
	 */
	public void setValoreIniziale(java.lang.Double val) {
		valoreIniziale = val;
	}

	////{PROTECTED REGION ID(R-1837302129) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo valoreIniziale. 
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
	public java.lang.Double getValoreIniziale() {
		return valoreIniziale;
	}

	/**
	 * @generated
	 */
	private java.lang.Double valoreAggiornamento = null;

	/**
	 * @generated
	 */
	public void setValoreAggiornamento(java.lang.Double val) {
		valoreAggiornamento = val;
	}

	////{PROTECTED REGION ID(R-1173331893) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo valoreAggiornamento. 
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
	public java.lang.Double getValoreAggiornamento() {
		return valoreAggiornamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double valoreFinale = null;

	/**
	 * @generated
	 */
	public void setValoreFinale(java.lang.Double val) {
		valoreFinale = val;
	}

	////{PROTECTED REGION ID(R-928045161) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo valoreFinale. 
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
	public java.lang.Double getValoreFinale() {
		return valoreFinale;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean flagNonApplicabile = null;

	/**
	 * @generated
	 */
	public void setFlagNonApplicabile(java.lang.Boolean val) {
		flagNonApplicabile = val;
	}

	////{PROTECTED REGION ID(R-689415966) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagNonApplicabile. 
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
	public java.lang.Boolean getFlagNonApplicabile() {
		return flagNonApplicabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBando = null;

	/**
	 * @generated
	 */
	public void setIdBando(java.lang.Long val) {
		idBando = val;
	}

	////{PROTECTED REGION ID(R-1829833288) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBando. 
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
	public java.lang.Long getIdBando() {
		return idBando;
	}

	/*PROTECTED REGION ID(R360575002) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
	
	private String infoIniziale;
	private String infoFinale;
	
	public String getInfoIniziale() {
		return infoIniziale;
	}

	public void setInfoIniziale(String infoIniziale) {
		this.infoIniziale = infoIniziale;
	}

	public String getInfoFinale() {
		return infoFinale;
	}

	public void setInfoFinale(String infoFinale) {
		this.infoFinale = infoFinale;
	}

	@Override
	public String toString() {
		return "IndicatoreDTO [idIndicatore=" + idIndicatore + ", descIndicatore=" + descIndicatore
				+ ", flagObbligatorio=" + flagObbligatorio + ", codIgrue=" + codIgrue + ", descUnitaMisura="
				+ descUnitaMisura + ", valoreIniziale=" + valoreIniziale + ", valoreAggiornamento="
				+ valoreAggiornamento + ", valoreFinale=" + valoreFinale + ", flagNonApplicabile=" + flagNonApplicabile
				+ ", idBando=" + idBando + ", infoIniziale=" + infoIniziale + ", infoFinale=" + infoFinale + "]";
	}
	
}
