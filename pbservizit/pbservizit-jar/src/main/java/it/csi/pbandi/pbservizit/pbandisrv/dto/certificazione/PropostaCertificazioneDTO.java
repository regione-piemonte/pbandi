/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione;

////{PROTECTED REGION ID(R1539441454) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PropostaCertificazioneDTO.
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
public class PropostaCertificazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.util.Date dataPagamenti = null;

	/**
	 * @generated
	 */
	public void setDataPagamenti(java.util.Date val) {
		dataPagamenti = val;
	}

	////{PROTECTED REGION ID(R1102338600) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataPagamenti. 
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
	public java.util.Date getDataPagamenti() {
		return dataPagamenti;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataValidazioni = null;

	/**
	 * @generated
	 */
	public void setDataValidazioni(java.util.Date val) {
		dataValidazioni = val;
	}

	////{PROTECTED REGION ID(R1702557982) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataValidazioni. 
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
	public java.util.Date getDataValidazioni() {
		return dataValidazioni;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataFideiussioni = null;

	/**
	 * @generated
	 */
	public void setDataFideiussioni(java.util.Date val) {
		dataFideiussioni = val;
	}

	////{PROTECTED REGION ID(R-1601376151) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataFideiussioni. 
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
	public java.util.Date getDataFideiussioni() {
		return dataFideiussioni;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataErogazioni = null;

	/**
	 * @generated
	 */
	public void setDataErogazioni(java.util.Date val) {
		dataErogazioni = val;
	}

	////{PROTECTED REGION ID(R-1219189097) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataErogazioni. 
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
	public java.util.Date getDataErogazioni() {
		return dataErogazioni;
	}

	/**
	 * @generated
	 */
	private java.lang.String descProposta = null;

	/**
	 * @generated
	 */
	public void setDescProposta(java.lang.String val) {
		descProposta = val;
	}

	////{PROTECTED REGION ID(R-920560365) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descProposta. 
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
	public java.lang.String getDescProposta() {
		return descProposta;
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

	////{PROTECTED REGION ID(R562078097) ENABLED START////}
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

	////{PROTECTED REGION ID(R2128619660) ENABLED START////}
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
	private java.lang.Long numeroProposta = null;

	/**
	 * @generated
	 */
	public void setNumeroProposta(java.lang.Long val) {
		numeroProposta = val;
	}

	////{PROTECTED REGION ID(R1191688222) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroProposta. 
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
	public java.lang.Long getNumeroProposta() {
		return numeroProposta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDettPropostaCertif = null;

	/**
	 * @generated
	 */
	public void setIdDettPropostaCertif(java.lang.Long val) {
		idDettPropostaCertif = val;
	}

	////{PROTECTED REGION ID(R-1509417377) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDettPropostaCertif. 
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
	public java.lang.Long getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoErogazioni = null;

	/**
	 * @generated
	 */
	public void setImportoErogazioni(java.lang.Double val) {
		importoErogazioni = val;
	}

	////{PROTECTED REGION ID(R-398152645) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogazioni. 
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
	public java.lang.Double getImportoErogazioni() {
		return importoErogazioni;
	}

	/**
	 * @generated
	 */
	private java.lang.Double spesaCertificabileLorda = null;

	/**
	 * @generated
	 */
	public void setSpesaCertificabileLorda(java.lang.Double val) {
		spesaCertificabileLorda = val;
	}

	////{PROTECTED REGION ID(R-1062973522) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo spesaCertificabileLorda. 
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
	public java.lang.Double getSpesaCertificabileLorda() {
		return spesaCertificabileLorda;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoAmmesso = null;

	/**
	 * @generated
	 */
	public void setCostoAmmesso(java.lang.Double val) {
		costoAmmesso = val;
	}

	////{PROTECTED REGION ID(R-612063657) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoAmmesso. 
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
	public java.lang.Double getCostoAmmesso() {
		return costoAmmesso;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoPagamentiValidati = null;

	/**
	 * @generated
	 */
	public void setImportoPagamentiValidati(java.lang.Double val) {
		importoPagamentiValidati = val;
	}

	////{PROTECTED REGION ID(R163596606) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPagamentiValidati. 
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
	public java.lang.Double getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoEccendenzeValidazione = null;

	/**
	 * @generated
	 */
	public void setImportoEccendenzeValidazione(java.lang.Double val) {
		importoEccendenzeValidazione = val;
	}

	////{PROTECTED REGION ID(R-166297908) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoEccendenzeValidazione. 
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
	public java.lang.Double getImportoEccendenzeValidazione() {
		return importoEccendenzeValidazione;
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

	////{PROTECTED REGION ID(R1702271405) ENABLED START////}
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
	private java.lang.Double importoFideiussioni = null;

	/**
	 * @generated
	 */
	public void setImportoFideiussioni(java.lang.Double val) {
		importoFideiussioni = val;
	}

	////{PROTECTED REGION ID(R1435639053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoFideiussioni. 
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
	public java.lang.Double getImportoFideiussioni() {
		return importoFideiussioni;
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

	////{PROTECTED REGION ID(R-1697269452) ENABLED START////}
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
	private java.lang.String descBreveStatoPropostaCert = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoPropostaCert(java.lang.String val) {
		descBreveStatoPropostaCert = val;
	}

	////{PROTECTED REGION ID(R-1617177554) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoPropostaCert. 
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
	public java.lang.String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoPropostaCertif = null;

	/**
	 * @generated
	 */
	public void setDescStatoPropostaCertif(java.lang.String val) {
		descStatoPropostaCertif = val;
	}

	////{PROTECTED REGION ID(R189649) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoPropostaCertif. 
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
	public java.lang.String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}

	/**
	 * @generated
	 */
	private java.lang.String titoloProgetto = null;

	/**
	 * @generated
	 */
	public void setTitoloProgetto(java.lang.String val) {
		titoloProgetto = val;
	}

	////{PROTECTED REGION ID(R-1331387707) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo titoloProgetto. 
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
	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String attivita = null;

	/**
	 * @generated
	 */
	public void setAttivita(java.lang.String val) {
		attivita = val;
	}

	////{PROTECTED REGION ID(R200333174) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo attivita. 
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
	public java.lang.String getAttivita() {
		return attivita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percContributoPubblico = null;

	/**
	 * @generated
	 */
	public void setPercContributoPubblico(java.lang.Double val) {
		percContributoPubblico = val;
	}

	////{PROTECTED REGION ID(R1813744377) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percContributoPubblico. 
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
	public java.lang.Double getPercContributoPubblico() {
		return percContributoPubblico;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percCofinFesr = null;

	/**
	 * @generated
	 */
	public void setPercCofinFesr(java.lang.Double val) {
		percCofinFesr = val;
	}

	////{PROTECTED REGION ID(R1291842409) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percCofinFesr. 
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
	public java.lang.Double getPercCofinFesr() {
		return percCofinFesr;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveCompletaAttivita = null;

	/**
	 * @generated
	 */
	public void setDescBreveCompletaAttivita(java.lang.String val) {
		descBreveCompletaAttivita = val;
	}

	////{PROTECTED REGION ID(R-908157758) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveCompletaAttivita. 
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
	public java.lang.String getDescBreveCompletaAttivita() {
		return descBreveCompletaAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(java.lang.String val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R422990440) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo beneficiario. 
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
	public java.lang.String getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isBozza = null;

	/**
	 * @generated
	 */
	public void setIsBozza(java.lang.Boolean val) {
		isBozza = val;
	}

	////{PROTECTED REGION ID(R243050428) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isBozza. 
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
	public java.lang.Boolean getIsBozza() {
		return isBozza;
	}

	/*PROTECTED REGION ID(R1430159517) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("Attivita=" + getAttivita());
		sb.append("\nBeneficiario=" + getBeneficiario());
		sb.append("\nCodiceVisualizzato=" + getCodiceVisualizzato());
		sb.append("\nCostoAmmesso=" + getCostoAmmesso());
		sb.append("\nDataErogazioni=" + getDataErogazioni());
		sb.append("\nDataFideiussioni=" + getDataFideiussioni());
		sb.append("\nDataPagamenti=" + getDataPagamenti());
		sb.append("\nDataValidazioni=" + getDataValidazioni());
		sb.append("\nDescBreveCompletaAttivita="
				+ getDescBreveCompletaAttivita());
		sb.append("\nDescBreveStatoPropostaCert="
				+ getDescBreveStatoPropostaCert());
		sb.append("\nDescProposta=" + getDescProposta());
		sb.append("\nDescStatoPropostaCertif=" + getDescStatoPropostaCertif());
		sb.append("\nDtOraCreazione=" + getDtOraCreazione());
		sb.append("\nIdDettPropostaCertif=" + getIdDettPropostaCertif());
		sb.append("\nIdProgetto=" + getIdProgetto());
		sb.append("\nIdPropostaCertificaz=" + getIdPropostaCertificaz());
		sb.append("\nImportoEccendenzeValidazione="
				+ getImportoEccendenzeValidazione());
		sb.append("\nImportoErogazioni=" + getImportoErogazioni());
		sb.append("\nImportoFideiussioni=" + getImportoFideiussioni());
		sb
				.append("\nImportoPagamentiValidati="
						+ getImportoPagamentiValidati());
		sb.append("\nIsBozza=" + getIsBozza());
		sb.append("\nNumeroProposta=" + getNumeroProposta());
		sb.append("\nPercCofinFesr=" + getPercCofinFesr());
		sb.append("\nPercContributoPubblico=" + getPercContributoPubblico());
		sb.append("\nSpesaCertificabileLorda=" + getSpesaCertificabileLorda());
		sb.append("\nTitoloProgetto=" + getTitoloProgetto());
		sb.append("]");
		return sb.toString();
	}
	/*PROTECTED REGION END*/
}
