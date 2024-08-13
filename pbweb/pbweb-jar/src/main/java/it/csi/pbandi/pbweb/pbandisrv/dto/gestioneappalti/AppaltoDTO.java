/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti;

////{PROTECTED REGION ID(R768121457) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AppaltoDTO.
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
public class AppaltoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R1839364251) ENABLED START////}
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

	////{PROTECTED REGION ID(R-218434945) ENABLED START////}
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
	private java.lang.Double bilancioPreventivo = null;

	/**
	 * @generated
	 */
	public void setBilancioPreventivo(java.lang.Double val) {
		bilancioPreventivo = val;
	}

	////{PROTECTED REGION ID(R-1929648334) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1680081003) ENABLED START////}
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
	private java.util.Date dtInizioPrevista = null;

	/**
	 * @generated
	 */
	public void setDtInizioPrevista(java.util.Date val) {
		dtInizioPrevista = val;
	}

	////{PROTECTED REGION ID(R1909812167) ENABLED START////}
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

	////{PROTECTED REGION ID(R862055148) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1790426132) ENABLED START////}
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
	private java.lang.Long idUtenteIns = null;

	/**
	 * @generated
	 */
	public void setIdUtenteIns(java.lang.Long val) {
		idUtenteIns = val;
	}

	////{PROTECTED REGION ID(R-1804076215) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1804084132) ENABLED START////}
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
	private java.lang.Long idProceduraAggiudicaz = null;

	/**
	 * @generated
	 */
	public void setIdProceduraAggiudicaz(java.lang.Long val) {
		idProceduraAggiudicaz = val;
	}

	////{PROTECTED REGION ID(R-1121652563) ENABLED START////}
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

	////{PROTECTED REGION ID(R-905707799) ENABLED START////}
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
	private java.lang.String descrizioneProcAgg = null;

	/**
	 * @generated
	 */
	public void setDescrizioneProcAgg(java.lang.String val) {
		descrizioneProcAgg = val;
	}

	////{PROTECTED REGION ID(R1442849197) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneProcAgg. 
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
	public java.lang.String getDescrizioneProcAgg() {
		return descrizioneProcAgg;
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

	////{PROTECTED REGION ID(R-2007885982) ENABLED START////}
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
	private java.util.Date dtGuue = null;

	/**
	 * @generated
	 */
	public void setDtGuue(java.util.Date val) {
		dtGuue = val;
	}

	////{PROTECTED REGION ID(R1110872671) ENABLED START////}
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

	////{PROTECTED REGION ID(R1110872582) ENABLED START////}
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

	////{PROTECTED REGION ID(R12512819) ENABLED START////}
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

	////{PROTECTED REGION ID(R1280403699) ENABLED START////}
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

	////{PROTECTED REGION ID(R492765673) ENABLED START////}
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
	private java.lang.Long idProgetto = null;

	/**
	 * @generated
	 */
	public void setIdProgetto(java.lang.Long val) {
		idProgetto = val;
	}

	////{PROTECTED REGION ID(R-985188048) ENABLED START////}
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
	private java.lang.Long idTipologiaAppalto = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaAppalto(java.lang.Long val) {
		idTipologiaAppalto = val;
	}

	////{PROTECTED REGION ID(R1202079531) ENABLED START////}
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
	private java.lang.String impresaAppaltatrice = null;

	/**
	 * @generated
	 */
	public void setImpresaAppaltatrice(java.lang.String val) {
		impresaAppaltatrice = val;
	}

	////{PROTECTED REGION ID(R2019733904) ENABLED START////}
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
	private java.lang.String interventoPisu = null;

	/**
	 * @generated
	 */
	public void setInterventoPisu(java.lang.String val) {
		interventoPisu = val;
	}

	////{PROTECTED REGION ID(R365098730) ENABLED START////}
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
	private java.lang.Double importoRibassoAsta = null;

	/**
	 * @generated
	 */
	public void setImportoRibassoAsta(java.lang.Double val) {
		importoRibassoAsta = val;
	}

	////{PROTECTED REGION ID(R-1092835393) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRibassoAsta. 
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
	public java.lang.Double getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualeRibassoAsta = null;

	/**
	 * @generated
	 */
	public void setPercentualeRibassoAsta(java.lang.Double val) {
		percentualeRibassoAsta = val;
	}

	////{PROTECTED REGION ID(R-1966462497) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeRibassoAsta. 
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
	public java.lang.Double getPercentualeRibassoAsta() {
		return percentualeRibassoAsta;
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

	////{PROTECTED REGION ID(R-1240244565) ENABLED START////}
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
	private java.lang.Long idTipoAffidamento = null;

	/**
	 * @generated
	 */
	public void setIdTipoAffidamento(java.lang.Long val) {
		idTipoAffidamento = val;
	}

	////{PROTECTED REGION ID(R-885837044) ENABLED START////}
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

	////{PROTECTED REGION ID(R611539523) ENABLED START////}
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

	/*PROTECTED REGION ID(R1347547792) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
