/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali;



////{PROTECTED REGION ID(R-135519358) ENABLED START////}
/**
 * Inserire qui la documentazione della classe NotificaDTO.
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

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;

////{PROTECTED REGION END////}
public class NotificaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idNotifica = null;

	/**
	 * @generated
	 */
	public void setIdNotifica(java.lang.Long val) {
		idNotifica = val;
	}

	////{PROTECTED REGION ID(R1622345876) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNotifica. 
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
	public java.lang.Long getIdNotifica() {
		return idNotifica;
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

	////{PROTECTED REGION ID(R-964468479) ENABLED START////}
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
	private java.lang.Long idRuoloDiProcessoDest = null;

	/**
	 * @generated
	 */
	public void setIdRuoloDiProcessoDest(java.lang.Long val) {
		idRuoloDiProcessoDest = val;
	}

	////{PROTECTED REGION ID(R-694728871) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRuoloDiProcessoDest. 
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
	public java.lang.Long getIdRuoloDiProcessoDest() {
		return idRuoloDiProcessoDest;
	}

	/**
	 * @generated
	 */
	private java.lang.String subjectNotifica = null;

	/**
	 * @generated
	 */
	public void setSubjectNotifica(java.lang.String val) {
		subjectNotifica = val;
	}

	////{PROTECTED REGION ID(R228437729) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo subjectNotifica. 
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
	public java.lang.String getSubjectNotifica() {
		return subjectNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String messageNotifica = null;

	/**
	 * @generated
	 */
	public void setMessageNotifica(java.lang.String val) {
		messageNotifica = val;
	}

	////{PROTECTED REGION ID(R-1105244484) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo messageNotifica. 
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
	public java.lang.String getMessageNotifica() {
		return messageNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoNotifica = null;

	/**
	 * @generated
	 */
	public void setStatoNotifica(java.lang.String val) {
		statoNotifica = val;
	}

	////{PROTECTED REGION ID(R-1240189232) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoNotifica. 
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
	public java.lang.String getStatoNotifica() {
		return statoNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteMitt = null;

	/**
	 * @generated
	 */
	public void setIdUtenteMitt(java.lang.Long val) {
		idUtenteMitt = val;
	}

	////{PROTECTED REGION ID(R-1655003982) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteMitt. 
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
	public java.lang.Long getIdUtenteMitt() {
		return idUtenteMitt;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtNotifica = null;

	/**
	 * @generated
	 */
	public void setDtNotifica(java.util.Date val) {
		dtNotifica = val;
	}

	////{PROTECTED REGION ID(R-544554231) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtNotifica. 
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
	public java.util.Date getDtNotifica() {
		return dtNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteAgg = null;

	/**
	 * @generated
	 */
	public void setIdUtenteAgg(java.lang.Long val) {
		idUtenteAgg = val;
	}

	////{PROTECTED REGION ID(R-1161777493) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteAgg. 
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
	public java.lang.Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggStatoNotifica = null;

	/**
	 * @generated
	 */
	public void setDtAggStatoNotifica(java.util.Date val) {
		dtAggStatoNotifica = val;
	}

	////{PROTECTED REGION ID(R1296002883) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggStatoNotifica. 
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
	public java.util.Date getDtAggStatoNotifica() {
		return dtAggStatoNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTemplateNotifica = null;

	/**
	 * @generated
	 */
	public void setIdTemplateNotifica(java.lang.Long val) {
		idTemplateNotifica = val;
	}

	////{PROTECTED REGION ID(R1802015278) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTemplateNotifica. 
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
	public java.lang.Long getIdTemplateNotifica() {
		return idTemplateNotifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idEntita = null;

	/**
	 * @generated
	 */
	public void setIdEntita(java.lang.Long val) {
		idEntita = val;
	}

	////{PROTECTED REGION ID(R-1289298360) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEntita. 
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
	public java.lang.Long getIdEntita() {
		return idEntita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTarget = null;

	/**
	 * @generated
	 */
	public void setIdTarget(java.lang.Long val) {
		idTarget = val;
	}

	////{PROTECTED REGION ID(R-871928818) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTarget. 
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
	public java.lang.Long getIdTarget() {
		return idTarget;
	}

	/*PROTECTED REGION ID(R354322313) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

		temp = DataFilter.removeNull(idNotifica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idNotifica: " + temp + "\t\n");

		temp = DataFilter.removeNull(idEntita);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idEntita: " + temp + "\t\n");

		temp = DataFilter.removeNull(idTarget);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idTarget: " + temp + "\t\n");

		temp = DataFilter.removeNull(subjectNotifica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" subjectNotifica: " + temp + "\t\n");

		temp = DataFilter.removeNull(messageNotifica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" messageNotifica: " + temp + "\t\n");

		temp = DataFilter.removeNull(statoNotifica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" statoNotifica: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtNotifica);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtNotifica: " + temp + "\t\n");

		return sb.toString();
	}
	/*PROTECTED REGION END*/
}
