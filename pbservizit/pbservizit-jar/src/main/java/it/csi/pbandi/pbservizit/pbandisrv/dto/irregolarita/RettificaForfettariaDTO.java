/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita;

////{PROTECTED REGION ID(R229738509) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RettificaForfettariaDTO.
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
public class RettificaForfettariaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idRettificaForfett = null;

	/**
	 * @generated
	 */
	public void setIdRettificaForfett(java.lang.Long val) {
		idRettificaForfett = val;
	}

	////{PROTECTED REGION ID(R907551129) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRettificaForfett. 
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
	public java.lang.Long getIdRettificaForfett() {
		return idRettificaForfett;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInserimento = null;

	/**
	 * @generated
	 */
	public void setDataInserimento(java.util.Date val) {
		dataInserimento = val;
	}

	////{PROTECTED REGION ID(R-1541392596) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInserimento. 
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
	public java.util.Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percRett = null;

	/**
	 * @generated
	 */
	public void setPercRett(java.lang.Double val) {
		percRett = val;
	}

	////{PROTECTED REGION ID(R-929133818) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percRett. 
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
	public java.lang.Double getPercRett() {
		return percRett;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCategAnagrafica = null;

	/**
	 * @generated
	 */
	public void setIdCategAnagrafica(java.lang.Long val) {
		idCategAnagrafica = val;
	}

	////{PROTECTED REGION ID(R-543255565) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCategAnagrafica. 
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
	public java.lang.Long getIdCategAnagrafica() {
		return idCategAnagrafica;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCategAnagrafica = null;

	/**
	 * @generated
	 */
	public void setDescCategAnagrafica(java.lang.String val) {
		descCategAnagrafica = val;
	}

	////{PROTECTED REGION ID(R1788686077) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCategAnagrafica. 
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
	public java.lang.String getDescCategAnagrafica() {
		return descCategAnagrafica;
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

	////{PROTECTED REGION ID(R629136529) ENABLED START////}
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
	private java.lang.String codProcAgg = null;

	/**
	 * @generated
	 */
	public void setCodProcAgg(java.lang.String val) {
		codProcAgg = val;
	}

	////{PROTECTED REGION ID(R433205504) ENABLED START////}
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

	////{PROTECTED REGION ID(R-673505897) ENABLED START////}
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
	private java.lang.Long idDocumentoIndex = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoIndex(java.lang.Long val) {
		idDocumentoIndex = val;
	}

	////{PROTECTED REGION ID(R-809987450) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoIndex. 
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
	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeFile = null;

	/**
	 * @generated
	 */
	public void setNomeFile(java.lang.String val) {
		nomeFile = val;
	}

	////{PROTECTED REGION ID(R23662402) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFile. 
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
	public java.lang.String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idEsitoIntermedio = null;

	/**
	 * @generated
	 */
	public void setIdEsitoIntermedio(java.lang.Long val) {
		idEsitoIntermedio = val;
	}

	////{PROTECTED REGION ID(R1052282724) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEsitoIntermedio. 
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
	public java.lang.Long getIdEsitoIntermedio() {
		return idEsitoIntermedio;
	}

	/**
	 * @generated
	 */
	private java.lang.String esitoIntermedio = null;

	/**
	 * @generated
	 */
	public void setEsitoIntermedio(java.lang.String val) {
		esitoIntermedio = val;
	}

	////{PROTECTED REGION ID(R-1912805697) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoIntermedio. 
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
	public java.lang.String getEsitoIntermedio() {
		return esitoIntermedio;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagRettificaIntermedio = null;

	/**
	 * @generated
	 */
	public void setFlagRettificaIntermedio(java.lang.String val) {
		flagRettificaIntermedio = val;
	}

	////{PROTECTED REGION ID(R1577076340) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagRettificaIntermedio. 
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
	public java.lang.String getFlagRettificaIntermedio() {
		return flagRettificaIntermedio;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idEsitoDefinitivo = null;

	/**
	 * @generated
	 */
	public void setIdEsitoDefinitivo(java.lang.Long val) {
		idEsitoDefinitivo = val;
	}

	////{PROTECTED REGION ID(R1629819131) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEsitoDefinitivo. 
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
	public java.lang.Long getIdEsitoDefinitivo() {
		return idEsitoDefinitivo;
	}

	/**
	 * @generated
	 */
	private java.lang.String esitoDefinitivo = null;

	/**
	 * @generated
	 */
	public void setEsitoDefinitivo(java.lang.String val) {
		esitoDefinitivo = val;
	}

	////{PROTECTED REGION ID(R-1335269290) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoDefinitivo. 
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
	public java.lang.String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagRettificaDefinitivo = null;

	/**
	 * @generated
	 */
	public void setFlagRettificaDefinitivo(java.lang.String val) {
		flagRettificaDefinitivo = val;
	}

	////{PROTECTED REGION ID(R-2140354549) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagRettificaDefinitivo. 
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
	public java.lang.String getFlagRettificaDefinitivo() {
		return flagRettificaDefinitivo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idPropostaCertificaz = null;

	/**
	 * @generated
	 */
	public void setIdPropostaCertificaz(java.lang.Long val) {
		idPropostaCertificaz = val;
	}

	////{PROTECTED REGION ID(R1573126384) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPropostaCertificaz. 
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
	public java.lang.Long getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtOraCreazione = null;

	/**
	 * @generated
	 */
	public void setDtOraCreazione(java.util.Date val) {
		dtOraCreazione = val;
	}

	////{PROTECTED REGION ID(R192559915) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtOraCreazione. 
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
	public java.util.Date getDtOraCreazione() {
		return dtOraCreazione;
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

	////{PROTECTED REGION ID(R1476973516) ENABLED START////}
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
	private java.lang.String codiceVisualizzato = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzato(java.lang.String val) {
		codiceVisualizzato = val;
	}

	////{PROTECTED REGION ID(R1356293203) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceVisualizzato. 
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
	public java.lang.String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoBeneficiario(java.lang.Long val) {
		idSoggettoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-1178220792) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoBeneficiario. 
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
	public java.lang.Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDenominazioneBeneficiario(java.lang.String val) {
		denominazioneBeneficiario = val;
	}

	////{PROTECTED REGION ID(R502273555) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneBeneficiario. 
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
	public java.lang.String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
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

	////{PROTECTED REGION ID(R-852157825) ENABLED START////}
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
	private java.lang.Long idAppaltoChecklist = null;

	/**
	 * @generated
	 */
	public void setIdAppaltoChecklist(java.lang.Long val) {
		idAppaltoChecklist = val;
	}

	////{PROTECTED REGION ID(R1939224039) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAppaltoChecklist. 
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
	public java.lang.Long getIdAppaltoChecklist() {
		return idAppaltoChecklist;
	}

	/*PROTECTED REGION ID(R-197102626) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
