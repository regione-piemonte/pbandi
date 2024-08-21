/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione;

////{PROTECTED REGION ID(R-903332153) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ProgettoCertificazioneIntermediaFinaleDTO.
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
public class ProgettoCertificazioneIntermediaFinaleDTO
		implements
			java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idDettPropCertAnnual = null;

	/**
	 * @generated
	 */
	public void setIdDettPropCertAnnual(java.lang.Long val) {
		idDettPropCertAnnual = val;
	}

	////{PROTECTED REGION ID(R-1351127095) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDettPropCertAnnual. 
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
	public java.lang.Long getIdDettPropCertAnnual() {
		return idDettPropCertAnnual;
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

	////{PROTECTED REGION ID(R-1385327752) ENABLED START////}
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
	private java.lang.Long idPropostaCertificaz = null;

	/**
	 * @generated
	 */
	public void setIdPropostaCertificaz(java.lang.Long val) {
		idPropostaCertificaz = val;
	}

	////{PROTECTED REGION ID(R686167722) ENABLED START////}
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
	private java.lang.Double importoRevocheRilevCum = null;

	/**
	 * @generated
	 */
	public void setImportoRevocheRilevCum(java.lang.Double val) {
		importoRevocheRilevCum = val;
	}

	////{PROTECTED REGION ID(R281397670) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRevocheRilevCum. 
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
	public java.lang.Double getImportoRevocheRilevCum() {
		return importoRevocheRilevCum;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRecuperiCum = null;

	/**
	 * @generated
	 */
	public void setImportoRecuperiCum(java.lang.Double val) {
		importoRecuperiCum = val;
	}

	////{PROTECTED REGION ID(R162760039) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRecuperiCum. 
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
	public java.lang.Double getImportoRecuperiCum() {
		return importoRecuperiCum;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSoppressioniCum = null;

	/**
	 * @generated
	 */
	public void setImportoSoppressioniCum(java.lang.Double val) {
		importoSoppressioniCum = val;
	}

	////{PROTECTED REGION ID(R265357256) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSoppressioniCum. 
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
	public java.lang.Double getImportoSoppressioniCum() {
		return importoSoppressioniCum;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoErogazioniCum = null;

	/**
	 * @generated
	 */
	public void setImportoErogazioniCum(java.lang.Double val) {
		importoErogazioniCum = val;
	}

	////{PROTECTED REGION ID(R1458382105) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogazioniCum. 
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
	public java.lang.Double getImportoErogazioniCum() {
		return importoErogazioniCum;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoPagamValidCum = null;

	/**
	 * @generated
	 */
	public void setImportoPagamValidCum(java.lang.Double val) {
		importoPagamValidCum = val;
	}

	////{PROTECTED REGION ID(R1574304254) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPagamValidCum. 
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
	public java.lang.Double getImportoPagamValidCum() {
		return importoPagamValidCum;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCertifNettoAnnual = null;

	/**
	 * @generated
	 */
	public void setImportoCertifNettoAnnual(java.lang.Double val) {
		importoCertifNettoAnnual = val;
	}

	////{PROTECTED REGION ID(R-572128941) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCertifNettoAnnual. 
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
	public java.lang.Double getImportoCertifNettoAnnual() {
		return importoCertifNettoAnnual;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataAgg = null;

	/**
	 * @generated
	 */
	public void setDataAgg(java.util.Date val) {
		dataAgg = val;
	}

	////{PROTECTED REGION ID(R-1748706224) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataAgg. 
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
	public java.util.Date getDataAgg() {
		return dataAgg;
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

	////{PROTECTED REGION ID(R978798150) ENABLED START////}
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
	private java.lang.Double certificatoNettoCumulato = null;

	/**
	 * @generated
	 */
	public void setCertificatoNettoCumulato(java.lang.Double val) {
		certificatoNettoCumulato = val;
	}

	////{PROTECTED REGION ID(R-966114840) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo certificatoNettoCumulato. 
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
	public java.lang.Double getCertificatoNettoCumulato() {
		return certificatoNettoCumulato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double certificatoLordoCumulato = null;

	/**
	 * @generated
	 */
	public void setCertificatoLordoCumulato(java.lang.Double val) {
		certificatoLordoCumulato = val;
	}

	////{PROTECTED REGION ID(R-940297430) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo certificatoLordoCumulato. 
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
	public java.lang.Double getCertificatoLordoCumulato() {
		return certificatoLordoCumulato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double colonnaC = null;

	/**
	 * @generated
	 */
	public void setColonnaC(java.lang.Double val) {
		colonnaC = val;
	}

	////{PROTECTED REGION ID(R-792735496) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo colonnaC. 
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
	public java.lang.Double getColonnaC() {
		return colonnaC;
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

	////{PROTECTED REGION ID(R718361223) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1212633832) ENABLED START////}
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
	private java.lang.String descProposta = null;

	/**
	 * @generated
	 */
	public void setDescProposta(java.lang.String val) {
		descProposta = val;
	}

	////{PROTECTED REGION ID(R-1923744980) ENABLED START////}
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
	private java.lang.String beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(java.lang.String val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R-580194175) ENABLED START////}
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
	private java.lang.String codiceProgetto = null;

	/**
	 * @generated
	 */
	public void setCodiceProgetto(java.lang.String val) {
		codiceProgetto = val;
	}

	////{PROTECTED REGION ID(R2009729918) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceProgetto. 
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
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeBandoLinea = null;

	/**
	 * @generated
	 */
	public void setNomeBandoLinea(java.lang.String val) {
		nomeBandoLinea = val;
	}

	////{PROTECTED REGION ID(R-1782207309) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeBandoLinea. 
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
	public java.lang.String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idStatoPropostaCertif = null;

	/**
	 * @generated
	 */
	public void setIdStatoPropostaCertif(java.lang.Long val) {
		idStatoPropostaCertif = val;
	}

	////{PROTECTED REGION ID(R412414094) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoPropostaCertif. 
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
	public java.lang.Long getIdStatoPropostaCertif() {
		return idStatoPropostaCertif;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLineaDiIntervento = null;

	/**
	 * @generated
	 */
	public void setIdLineaDiIntervento(java.lang.Long val) {
		idLineaDiIntervento = val;
	}

	////{PROTECTED REGION ID(R-1635160338) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaDiIntervento. 
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
	public java.lang.Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
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

	////{PROTECTED REGION ID(R1044244998) ENABLED START////}
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
	private java.lang.String asse = null;

	/**
	 * @generated
	 */
	public void setAsse(java.lang.String val) {
		asse = val;
	}

	////{PROTECTED REGION ID(R-352626357) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo asse. 
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
	public java.lang.String getAsse() {
		return asse;
	}

	/**
	 * @generated
	 */
	private java.lang.Double diffCna = null;

	/**
	 * @generated
	 */
	public void setDiffCna(java.lang.Double val) {
		diffCna = val;
	}

	////{PROTECTED REGION ID(R-1532451222) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo diffCna. 
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
	public java.lang.Double getDiffCna() {
		return diffCna;
	}

	/**
	 * @generated
	 */
	private java.lang.Double diffRev = null;

	/**
	 * @generated
	 */
	public void setDiffRev(java.lang.Double val) {
		diffRev = val;
	}

	////{PROTECTED REGION ID(R-1532437065) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo diffRev. 
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
	public java.lang.Double getDiffRev() {
		return diffRev;
	}

	/**
	 * @generated
	 */
	private java.lang.Double diffRec = null;

	/**
	 * @generated
	 */
	public void setDiffRec(java.lang.Double val) {
		diffRec = val;
	}

	////{PROTECTED REGION ID(R-1532437084) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo diffRec. 
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
	public java.lang.Double getDiffRec() {
		return diffRec;
	}

	/**
	 * @generated
	 */
	private java.lang.Double diffSoppr = null;

	/**
	 * @generated
	 */
	public void setDiffSoppr(java.lang.Double val) {
		diffSoppr = val;
	}

	////{PROTECTED REGION ID(R502982314) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo diffSoppr. 
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
	public java.lang.Double getDiffSoppr() {
		return diffSoppr;
	}

	/*PROTECTED REGION ID(R1108154980) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
