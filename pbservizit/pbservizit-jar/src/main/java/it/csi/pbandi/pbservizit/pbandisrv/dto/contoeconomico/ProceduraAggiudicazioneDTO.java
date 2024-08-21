/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-721136767) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ProceduraAggiudicazioneDTO.
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
public class ProceduraAggiudicazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idProceduraAggiudicaz = null;

	/**
	 * @generated
	 */
	public void setIdProceduraAggiudicaz(java.lang.Long val) {
		idProceduraAggiudicaz = val;
	}

	////{PROTECTED REGION ID(R-2063433315) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProceduraAggiudicaz. 
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
	public java.lang.Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}

	/**
	 * @generated
	 */
	private java.lang.String descProcAgg = null;

	/**
	 * @generated
	 */
	public void setDescProcAgg(java.lang.String val) {
		descProcAgg = val;
	}

	////{PROTECTED REGION ID(R-1148912679) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descProcAgg. 
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
	public java.lang.String getDescProcAgg() {
		return descProcAgg;
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

	////{PROTECTED REGION ID(R189590217) ENABLED START////}
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
	private java.lang.String codProcAgg = null;

	/**
	 * @generated
	 */
	public void setCodProcAgg(java.lang.String val) {
		codProcAgg = val;
	}

	////{PROTECTED REGION ID(R872692596) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codProcAgg. 
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
	public java.lang.String getCodProcAgg() {
		return codProcAgg;
	}

	/**
	 * @generated
	 */
	private java.lang.String cigProcAgg = null;

	/**
	 * @generated
	 */
	public void setCigProcAgg(java.lang.String val) {
		cigProcAgg = val;
	}

	////{PROTECTED REGION ID(R-234018805) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cigProcAgg. 
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
	public java.lang.String getCigProcAgg() {
		return cigProcAgg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipologiaAggiudicaz = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaAggiudicaz(java.lang.Long val) {
		idTipologiaAggiudicaz = val;
	}

	////{PROTECTED REGION ID(R-1893089226) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaAggiudicaz. 
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
	public java.lang.Long getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipologiaAggiudicazione = null;

	/**
	 * @generated
	 */
	public void setDescTipologiaAggiudicazione(java.lang.String val) {
		descTipologiaAggiudicazione = val;
	}

	////{PROTECTED REGION ID(R2130738333) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipologiaAggiudicazione. 
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
	public java.lang.String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
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

	////{PROTECTED REGION ID(R1916460608) ENABLED START////}
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
	private java.lang.Double percentuale = null;

	/**
	 * @generated
	 */
	public void setPercentuale(java.lang.Double val) {
		percentuale = val;
	}

	////{PROTECTED REGION ID(R1918338217) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentuale. 
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
	public java.lang.Double getPercentuale() {
		return percentuale;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoPercentuale = null;

	/**
	 * @generated
	 */
	public void setImportoPercentuale(java.lang.Double val) {
		importoPercentuale = val;
	}

	////{PROTECTED REGION ID(R1425717313) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPercentuale. 
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
	public java.lang.Double getImportoPercentuale() {
		return importoPercentuale;
	}

	/**
	 * @generated
	 */
	private java.lang.Double iva = null;

	/**
	 * @generated
	 */
	public void setIva(java.lang.Double val) {
		iva = val;
	}

	////{PROTECTED REGION ID(R41093555) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo iva. 
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
	public java.lang.Double getIva() {
		return iva;
	}

	/**
	 * @generated
	 */
	private java.lang.String codice = null;

	/**
	 * @generated
	 */
	public void setCodice(java.lang.String val) {
		codice = val;
	}

	////{PROTECTED REGION ID(R-25628460) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codice. 
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
	public java.lang.String getCodice() {
		return codice;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.StepAggiudicazione[] iter = null;

	/**
	 * @generated
	 */
	public void setIter(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.StepAggiudicazione[] val) {
		iter = val;
	}

	////{PROTECTED REGION ID(R1273898521) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo iter. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.StepAggiudicazione[] getIter() {
		return iter;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMotivoAssenzaCig = null;

	/**
	 * @generated
	 */
	public void setIdMotivoAssenzaCig(java.lang.Long val) {
		idMotivoAssenzaCig = val;
	}

	////{PROTECTED REGION ID(R570844316) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMotivoAssenzaCig. 
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
	public java.lang.Long getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggiudicazione = null;

	/**
	 * @generated
	 */
	public void setDtAggiudicazione(java.util.Date val) {
		dtAggiudicazione = val;
	}

	////{PROTECTED REGION ID(R-1586774536) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggiudicazione. 
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
	public java.util.Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String oggettoAffidamento = null;

	/**
	 * @generated
	 */
	public void setOggettoAffidamento(java.lang.String val) {
		oggettoAffidamento = val;
	}

	////{PROTECTED REGION ID(R-890679852) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo oggettoAffidamento. 
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
	public java.lang.String getOggettoAffidamento() {
		return oggettoAffidamento;
	}

	/*PROTECTED REGION ID(R-1836864448) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
