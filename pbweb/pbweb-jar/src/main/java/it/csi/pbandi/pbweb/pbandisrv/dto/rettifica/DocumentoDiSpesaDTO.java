package it.csi.pbandi.pbweb.pbandisrv.dto.rettifica;

import java.sql.Date;

////{PROTECTED REGION ID(R-2132701146) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DocumentoDiSpesaDTO. Consigli:
 * <ul>
 * <li>Descrivere il "concetto" rappresentato dall'entita' (qual'� l'oggetto del
 * dominio del servizio rappresentato)
 * <li>Se necessario indicare se questo concetto � mantenuto sugli archivi di
 * una particolare applicazione
 * <li>Se l'oggetto ha un particolare ciclo di vita (stati, es. creato, da
 * approvare, approvato, respinto, annullato.....) si pu� decidere di descrivere
 * la state machine qui o nella documentazione dell'interfaccia del servizio che
 * manipola quest'oggetto
 * </ul>
 * 
 * @generated
 */
////{PROTECTED REGION END////}
public class DocumentoDiSpesaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idVoceSpesa = null;

	/**
	 * @generated
	 */
	public void setIdVoceSpesa(java.lang.Long val) {
		idVoceSpesa = val;
	}

	//// {PROTECTED REGION ID(R1747496730) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoceSpesa. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdVoceSpesa() {
		return idVoceSpesa;
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

	//// {PROTECTED REGION ID(R1059344549) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgetto. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		idDocumentoDiSpesa = val;
	}

	//// {PROTECTED REGION ID(R567111934) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoDiSpesa. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoSpesa(java.lang.Long val) {
		idTipoDocumentoSpesa = val;
	}

	//// {PROTECTED REGION ID(R303472023) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumentoSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idStatoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setIdStatoDocumentoSpesa(java.lang.Long val) {
		idStatoDocumentoSpesa = val;
	}

	//// {PROTECTED REGION ID(R686985116) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoDocumentoSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoFornitore = null;

	/**
	 * @generated
	 */
	public void setIdTipoFornitore(java.lang.Long val) {
		idTipoFornitore = val;
	}

	//// {PROTECTED REGION ID(R2137538845) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoFornitore. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setDescTipoDocumentoSpesa(java.lang.String val) {
		descTipoDocumentoSpesa = val;
	}

	//// {PROTECTED REGION ID(R-1042764787) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDocumentoSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoDocSpesa = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoDocSpesa(java.lang.String val) {
		descBreveTipoDocSpesa = val;
	}

	//// {PROTECTED REGION ID(R1285297567) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoDocSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumento = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumento(java.lang.String val) {
		numeroDocumento = val;
	}

	//// {PROTECTED REGION ID(R1616333074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumento. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtEmissioneDocumento = null;

	/**
	 * @generated
	 */
	public void setDtEmissioneDocumento(java.util.Date val) {
		dtEmissioneDocumento = val;
	}

	//// {PROTECTED REGION ID(R-1124593044) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtEmissioneDocumento.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.util.Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String task = null;

	/**
	 * @generated
	 */
	public void setTask(java.lang.String val) {
		task = val;
	}

	//// {PROTECTED REGION ID(R401031851) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo task. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getTask() {
		return task;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneFornitore = null;

	/**
	 * @generated
	 */
	public void setDenominazioneFornitore(java.lang.String val) {
		denominazioneFornitore = val;
	}

	//// {PROTECTED REGION ID(R596883538) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneFornitore.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscaleFornitore = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscaleFornitore(java.lang.String val) {
		codiceFiscaleFornitore = val;
	}

	//// {PROTECTED REGION ID(R-2100680348) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleFornitore.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String partitaIvaFornitore = null;

	/**
	 * @generated
	 */
	public void setPartitaIvaFornitore(java.lang.String val) {
		partitaIvaFornitore = val;
	}

	//// {PROTECTED REGION ID(R-951763141) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIvaFornitore.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleDocumento = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleDocumento(java.lang.Double val) {
		importoTotaleDocumento = val;
	}

	//// {PROTECTED REGION ID(R1468362479) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleDocumento.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Double getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setDescStatoDocumentoSpesa(java.lang.String val) {
		descStatoDocumentoSpesa = val;
	}

	//// {PROTECTED REGION ID(R1903316966) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoDocumentoSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveStatoDocSpesa = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoDocSpesa(java.lang.String val) {
		descBreveStatoDocSpesa = val;
	}

	//// {PROTECTED REGION ID(R-1036058548) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoDocSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String cognomeFornitore = null;

	/**
	 * @generated
	 */
	public void setCognomeFornitore(java.lang.String val) {
		cognomeFornitore = val;
	}

	//// {PROTECTED REGION ID(R-1258286172) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cognomeFornitore. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeFornitore = null;

	/**
	 * @generated
	 */
	public void setNomeFornitore(java.lang.String val) {
		nomeFornitore = val;
	}

	//// {PROTECTED REGION ID(R2069098163) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFornitore. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichiarazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDichiarazioneSpesa(java.lang.Long val) {
		idDichiarazioneSpesa = val;
	}

	//// {PROTECTED REGION ID(R490119089) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichiarazioneSpesa.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleRettificaDoc = null;

	/**
	 * @generated
	 */
	public void setTotaleRettificaDoc(java.lang.Double val) {
		totaleRettificaDoc = val;
	}

	//// {PROTECTED REGION ID(R-143172504) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleRettificaDoc. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Double getTotaleRettificaDoc() {
		return totaleRettificaDoc;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isRettificato = null;

	/**
	 * @generated
	 */
	public void setIsRettificato(java.lang.Boolean val) {
		isRettificato = val;
	}

	//// {PROTECTED REGION ID(R-11037950) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isRettificato. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.Boolean getIsRettificato() {
		return isRettificato;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoInvio = null;

	/**
	 * @generated
	 */
	public void setTipoInvio(java.lang.String val) {
		tipoInvio = val;
	}

	//// {PROTECTED REGION ID(R797000861) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoInvio. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}

	/* PROTECTED REGION ID(R907254779) ENABLED START */
	private java.lang.Double validatoPerDichiarazione = null;

	public java.lang.Double getValidatoPerDichiarazione() {
		return validatoPerDichiarazione;
	}

	public void setValidatoPerDichiarazione(java.lang.Double validatoPerDichiarazione) {
		this.validatoPerDichiarazione = validatoPerDichiarazione;
	}

	private String rilievoContabile;
	private Date dtRilievoContabile;

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	/* PROTECTED REGION END */
}
