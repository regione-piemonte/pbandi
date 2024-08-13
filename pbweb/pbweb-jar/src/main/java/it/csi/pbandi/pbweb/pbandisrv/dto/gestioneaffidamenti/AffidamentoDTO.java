/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti;

////{PROTECTED REGION ID(R-1007744239) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AffidamentoDTO.
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
public class AffidamentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R-2129567280) ENABLED START////}
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
	private java.lang.Long idAppalto = null;

	/**
	 * @generated
	 */
	public void setIdAppalto(java.lang.Long val) {
		idAppalto = val;
	}

	////{PROTECTED REGION ID(R832617467) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAppalto. 
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
	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}

	/**
	 * @generated
	 */
	private java.lang.String oggettoAppalto = null;

	/**
	 * @generated
	 */
	public void setOggettoAppalto(java.lang.String val) {
		oggettoAppalto = val;
	}

	////{PROTECTED REGION ID(R-163591393) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo oggettoAppalto. 
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
	public java.lang.String getOggettoAppalto() {
		return oggettoAppalto;
	}

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

	////{PROTECTED REGION ID(R804856845) ENABLED START////}
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
	private java.lang.Long idTipologiaAppalto = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaAppalto(java.lang.Long val) {
		idTipologiaAppalto = val;
	}

	////{PROTECTED REGION ID(R-175255605) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaAppalto. 
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
	public java.lang.Long getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipologiaAppalto = null;

	/**
	 * @generated
	 */
	public void setDescTipologiaAppalto(java.lang.String val) {
		descTipologiaAppalto = val;
	}

	////{PROTECTED REGION ID(R-1871174399) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipologiaAppalto. 
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
	public java.lang.String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoAffidamento = null;

	/**
	 * @generated
	 */
	public void setIdTipoAffidamento(java.lang.Long val) {
		idTipoAffidamento = val;
	}

	////{PROTECTED REGION ID(R870848108) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoAffidamento. 
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
	public java.lang.Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoPercettore = null;

	/**
	 * @generated
	 */
	public void setIdTipoPercettore(java.lang.Long val) {
		idTipoPercettore = val;
	}

	////{PROTECTED REGION ID(R1776585443) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoPercettore. 
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
	public java.lang.Long getIdTipoPercettore() {
		return idTipoPercettore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idStatoAffidamento = null;

	/**
	 * @generated
	 */
	public void setIdStatoAffidamento(java.lang.Long val) {
		idStatoAffidamento = val;
	}

	////{PROTECTED REGION ID(R1654802493) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoAffidamento. 
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
	public java.lang.Long getIdStatoAffidamento() {
		return idStatoAffidamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoAffidamento = null;

	/**
	 * @generated
	 */
	public void setDescStatoAffidamento(java.lang.String val) {
		descStatoAffidamento = val;
	}

	////{PROTECTED REGION ID(R-41116301) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoAffidamento. 
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
	public java.lang.String getDescStatoAffidamento() {
		return descStatoAffidamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNorma = null;

	/**
	 * @generated
	 */
	public void setIdNorma(java.lang.Long val) {
		idNorma = val;
	}

	////{PROTECTED REGION ID(R-1247489127) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNorma. 
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
	public java.lang.Long getIdNorma() {
		return idNorma;
	}

	/**
	 * @generated
	 */
	private java.lang.Double bilancioPreventivo = null;

	/**
	 * @generated
	 */
	public void setBilancioPreventivo(java.lang.Double val) {
		bilancioPreventivo = val;
	}

	////{PROTECTED REGION ID(R987983826) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo bilancioPreventivo. 
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
	public java.lang.Double getBilancioPreventivo() {
		return bilancioPreventivo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoContratto = null;

	/**
	 * @generated
	 */
	public void setImportoContratto(java.lang.Double val) {
		importoContratto = val;
	}

	////{PROTECTED REGION ID(R-515035083) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoContratto. 
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
	public java.lang.Double getImportoContratto() {
		return importoContratto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double impRendicontabile = null;

	/**
	 * @generated
	 */
	public void setImpRendicontabile(java.lang.Double val) {
		impRendicontabile = val;
	}

	////{PROTECTED REGION ID(R2132989322) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impRendicontabile. 
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
	public java.lang.Double getImpRendicontabile() {
		return impRendicontabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double impRibassoAsta = null;

	/**
	 * @generated
	 */
	public void setImpRibassoAsta(java.lang.Double val) {
		impRibassoAsta = val;
	}

	////{PROTECTED REGION ID(R974534973) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impRibassoAsta. 
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
	public java.lang.Double getImpRibassoAsta() {
		return impRibassoAsta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percRibassoAsta = null;

	/**
	 * @generated
	 */
	public void setPercRibassoAsta(java.lang.Double val) {
		percRibassoAsta = val;
	}

	////{PROTECTED REGION ID(R1807663457) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percRibassoAsta. 
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
	public java.lang.Double getPercRibassoAsta() {
		return percRibassoAsta;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtGuue = null;

	/**
	 * @generated
	 */
	public void setDtGuue(java.util.Date val) {
		dtGuue = val;
	}

	////{PROTECTED REGION ID(R-307361025) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtGuue. 
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
	public java.util.Date getDtGuue() {
		return dtGuue;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtGuri = null;

	/**
	 * @generated
	 */
	public void setDtGuri(java.util.Date val) {
		dtGuri = val;
	}

	////{PROTECTED REGION ID(R-307361114) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtGuri. 
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
	public java.util.Date getDtGuri() {
		return dtGuri;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtQuotNazionali = null;

	/**
	 * @generated
	 */
	public void setDtQuotNazionali(java.util.Date val) {
		dtQuotNazionali = val;
	}

	////{PROTECTED REGION ID(R1712662931) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtQuotNazionali. 
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
	public java.util.Date getDtQuotNazionali() {
		return dtQuotNazionali;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtWebStazAppaltante = null;

	/**
	 * @generated
	 */
	public void setDtWebStazAppaltante(java.util.Date val) {
		dtWebStazAppaltante = val;
	}

	////{PROTECTED REGION ID(R1532687443) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtWebStazAppaltante. 
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
	public java.util.Date getDtWebStazAppaltante() {
		return dtWebStazAppaltante;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtWebOsservatorio = null;

	/**
	 * @generated
	 */
	public void setDtWebOsservatorio(java.util.Date val) {
		dtWebOsservatorio = val;
	}

	////{PROTECTED REGION ID(R-2045516471) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtWebOsservatorio. 
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
	public java.util.Date getDtWebOsservatorio() {
		return dtWebOsservatorio;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInizioPrevista = null;

	/**
	 * @generated
	 */
	public void setDtInizioPrevista(java.util.Date val) {
		dtInizioPrevista = val;
	}

	////{PROTECTED REGION ID(R-1220109209) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInizioPrevista. 
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
	public java.util.Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtConsegnaLavori = null;

	/**
	 * @generated
	 */
	public void setDtConsegnaLavori(java.util.Date val) {
		dtConsegnaLavori = val;
	}

	////{PROTECTED REGION ID(R2027101068) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtConsegnaLavori. 
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
	public java.util.Date getDtConsegnaLavori() {
		return dtConsegnaLavori;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtFirmaContratto = null;

	/**
	 * @generated
	 */
	public void setDtFirmaContratto(java.util.Date val) {
		dtFirmaContratto = val;
	}

	////{PROTECTED REGION ID(R-625380212) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFirmaContratto. 
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
	public java.util.Date getDtFirmaContratto() {
		return dtFirmaContratto;
	}

	/**
	 * @generated
	 */
	private java.lang.String interventoPisu = null;

	/**
	 * @generated
	 */
	public void setInterventoPisu(java.lang.String val) {
		interventoPisu = val;
	}

	////{PROTECTED REGION ID(R419942282) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo interventoPisu. 
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
	public java.lang.String getInterventoPisu() {
		return interventoPisu;
	}

	/**
	 * @generated
	 */
	private java.lang.String impresaAppaltatrice = null;

	/**
	 * @generated
	 */
	public void setImpresaAppaltatrice(java.lang.String val) {
		impresaAppaltatrice = val;
	}

	////{PROTECTED REGION ID(R-2022949648) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impresaAppaltatrice. 
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
	public java.lang.String getImpresaAppaltatrice() {
		return impresaAppaltatrice;
	}

	/**
	 * @generated
	 */
	private java.lang.String sopraSoglia = null;

	/**
	 * @generated
	 */
	public void setSopraSoglia(java.lang.String val) {
		sopraSoglia = val;
	}

	////{PROTECTED REGION ID(R1938704907) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sopraSoglia. 
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
	public java.lang.String getSopraSoglia() {
		return sopraSoglia;
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

	////{PROTECTED REGION ID(R-2021725623) ENABLED START////}
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
	private java.util.Date dtInserimento = null;

	/**
	 * @generated
	 */
	public void setDtInserimento(java.util.Date val) {
		dtInserimento = val;
	}

	////{PROTECTED REGION ID(R-759190846) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInserimento. 
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
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggiornamento = null;

	/**
	 * @generated
	 */
	public void setDtAggiornamento(java.util.Date val) {
		dtAggiornamento = val;
	}

	////{PROTECTED REGION ID(R1712647506) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggiornamento. 
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
	public java.util.Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteIns = null;

	/**
	 * @generated
	 */
	public void setIdUtenteIns(java.lang.Long val) {
		idUtenteIns = val;
	}

	////{PROTECTED REGION ID(R1374873257) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteIns. 
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
	public java.lang.Long getIdUtenteIns() {
		return idUtenteIns;
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

	////{PROTECTED REGION ID(R1374865340) ENABLED START////}
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
	private java.lang.String cigProcAgg = null;

	/**
	 * @generated
	 */
	public void setCigProcAgg(java.lang.String val) {
		cigProcAgg = val;
	}

	////{PROTECTED REGION ID(R14920603) ENABLED START////}
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
	private java.lang.String codProcAgg = null;

	/**
	 * @generated
	 */
	public void setCodProcAgg(java.lang.String val) {
		codProcAgg = val;
	}

	////{PROTECTED REGION ID(R1121632004) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO proceduraAggiudicazioneDTO = null;

	/**
	 * @generated
	 */
	public void setProceduraAggiudicazioneDTO(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO val) {
		proceduraAggiudicazioneDTO = val;
	}

	////{PROTECTED REGION ID(R993619290) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo proceduraAggiudicazioneDTO. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO getProceduraAggiudicazioneDTO() {
		return proceduraAggiudicazioneDTO;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] varianti = null;

	/**
	 * @generated
	 */
	public void setVarianti(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] val) {
		varianti = val;
	}

	////{PROTECTED REGION ID(R-473666923) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo varianti. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] getVarianti() {
		return varianti;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] fornitori = null;

	/**
	 * @generated
	 */
	public void setFornitori(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] val) {
		fornitori = val;
	}

	////{PROTECTED REGION ID(R130944069) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fornitori. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] getFornitori() {
		return fornitori;
	}

	/**
	 * @generated
	 */
	private long numFornitoriAssociati = 0;

	/**
	 * @generated
	 */
	public void setNumFornitoriAssociati(long val) {
		numFornitoriAssociati = val;
	}

	////{PROTECTED REGION ID(R162620225) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numFornitoriAssociati. 
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
	public long getNumFornitoriAssociati() {
		return numFornitoriAssociati;
	}

	/**
	 * @generated
	 */
	private long numAllegatiAssociati = 0;

	/**
	 * @generated
	 */
	public void setNumAllegatiAssociati(long val) {
		numAllegatiAssociati = val;
	}

	////{PROTECTED REGION ID(R573578330) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numAllegatiAssociati. 
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
	public long getNumAllegatiAssociati() {
		return numAllegatiAssociati;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean respingibile = null;

	/**
	 * @generated
	 */
	public void setRespingibile(java.lang.Boolean val) {
		respingibile = val;
	}

	////{PROTECTED REGION ID(R-1615834456) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo respingibile. 
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
	public java.lang.Boolean getRespingibile() {
		return respingibile;
	}

	/**
	 * @generated
	 */
	private java.lang.String fase1Esito = null;

	/**
	 * @generated
	 */
	public void setFase1Esito(java.lang.String val) {
		fase1Esito = val;
	}

	////{PROTECTED REGION ID(R-2457629) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fase1Esito. 
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
	public java.lang.String getFase1Esito() {
		return fase1Esito;
	}

	/**
	 * @generated
	 */
	private java.lang.String fase1Rettifica = null;

	/**
	 * @generated
	 */
	public void setFase1Rettifica(java.lang.String val) {
		fase1Rettifica = val;
	}

	////{PROTECTED REGION ID(R-1410664348) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fase1Rettifica. 
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
	public java.lang.String getFase1Rettifica() {
		return fase1Rettifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String fase2Esito = null;

	/**
	 * @generated
	 */
	public void setFase2Esito(java.lang.String val) {
		fase2Esito = val;
	}

	////{PROTECTED REGION ID(R26171522) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fase2Esito. 
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
	public java.lang.String getFase2Esito() {
		return fase2Esito;
	}

	/**
	 * @generated
	 */
	private java.lang.String fase2Rettifica = null;

	/**
	 * @generated
	 */
	public void setFase2Rettifica(java.lang.String val) {
		fase2Rettifica = val;
	}

	////{PROTECTED REGION ID(R-1607177853) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fase2Rettifica. 
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
	public java.lang.String getFase2Rettifica() {
		return fase2Rettifica;
	}

	/**
	 * @generated
	 */
	private boolean esisteAllegatoNonInviato = true;

	/**
	 * @generated
	 */
	public void setEsisteAllegatoNonInviato(boolean val) {
		esisteAllegatoNonInviato = val;
	}

	////{PROTECTED REGION ID(R1034447562) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esisteAllegatoNonInviato. 
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
	public boolean getEsisteAllegatoNonInviato() {
		return esisteAllegatoNonInviato;
	}

	/*PROTECTED REGION ID(R-357149968) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
