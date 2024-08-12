package it.csi.pbandi.pbweb.pbandisrv.dto.gestioneeconomie;

import it.csi.pbandi.pbweb.dto.utils.ResponseCodeMessage;

////{PROTECTED REGION ID(R-1442173523) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EconomiaDTO.
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
public class EconomiaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idEconomia = null;

	/**
	 * @generated
	 */
	public void setIdEconomia(java.lang.Long val) {
		idEconomia = val;
	}

	////{PROTECTED REGION ID(R1287888795) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEconomia. 
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
	public java.lang.Long getIdEconomia() {
		return idEconomia;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgettoCedente = null;

	/**
	 * @generated
	 */
	public void setIdProgettoCedente(java.lang.Long val) {
		idProgettoCedente = val;
	}

	////{PROTECTED REGION ID(R2034407696) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoCedente. 
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
	public java.lang.Long getIdProgettoCedente() {
		return idProgettoCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgettoRicevente = null;

	/**
	 * @generated
	 */
	public void setIdProgettoRicevente(java.lang.Long val) {
		idProgettoRicevente = val;
	}

	////{PROTECTED REGION ID(R1309387019) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoRicevente. 
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
	public java.lang.Long getIdProgettoRicevente() {
		return idProgettoRicevente;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceVisualizzatoCedente = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzatoCedente(java.lang.String val) {
		codiceVisualizzatoCedente = val;
	}

	////{PROTECTED REGION ID(R1043092137) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceVisualizzatoCedente. 
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
	public java.lang.String getCodiceVisualizzatoCedente() {
		return codiceVisualizzatoCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCeduto = null;

	/**
	 * @generated
	 */
	public void setImportoCeduto(java.lang.Double val) {
		importoCeduto = val;
	}

	////{PROTECTED REGION ID(R-1070059605) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCeduto. 
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
	public java.lang.Double getImportoCeduto() {
		return importoCeduto;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteCessione = null;

	/**
	 * @generated
	 */
	public void setNoteCessione(java.lang.String val) {
		noteCessione = val;
	}

	////{PROTECTED REGION ID(R151459550) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteCessione. 
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
	public java.lang.String getNoteCessione() {
		return noteCessione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataCessione = null;

	/**
	 * @generated
	 */
	public void setDataCessione(java.util.Date val) {
		dataCessione = val;
	}

	////{PROTECTED REGION ID(R1182998166) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataCessione. 
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
	public java.util.Date getDataCessione() {
		return dataCessione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceVisualizzatoRicevente = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzatoRicevente(java.lang.String val) {
		codiceVisualizzatoRicevente = val;
	}

	////{PROTECTED REGION ID(R2137874532) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceVisualizzatoRicevente. 
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
	public java.lang.String getCodiceVisualizzatoRicevente() {
		return codiceVisualizzatoRicevente;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataRicezione = null;

	/**
	 * @generated
	 */
	public void setDataRicezione(java.util.Date val) {
		dataRicezione = val;
	}

	////{PROTECTED REGION ID(R1947022983) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataRicezione. 
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
	public java.util.Date getDataRicezione() {
		return dataRicezione;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteRicezione = null;

	/**
	 * @generated
	 */
	public void setNoteRicezione(java.lang.String val) {
		noteRicezione = val;
	}

	////{PROTECTED REGION ID(R34096959) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteRicezione. 
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
	public java.lang.String getNoteRicezione() {
		return noteRicezione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataUtilizzo = null;

	/**
	 * @generated
	 */
	public void setDataUtilizzo(java.util.Date val) {
		dataUtilizzo = val;
	}

	////{PROTECTED REGION ID(R-1671246049) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataUtilizzo. 
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
	public java.util.Date getDataUtilizzo() {
		return dataUtilizzo;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataModifica = null;

	/**
	 * @generated
	 */
	public void setDataModifica(java.util.Date val) {
		dataModifica = val;
	}

	////{PROTECTED REGION ID(R1277566591) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataModifica. 
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
	public java.util.Date getDataModifica() {
		return dataModifica;
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

	////{PROTECTED REGION ID(R-873226868) ENABLED START////}
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
	private java.lang.Long idBeneficiarioCedente = null;

	/**
	 * @generated
	 */
	public void setIdBeneficiarioCedente(java.lang.Long val) {
		idBeneficiarioCedente = val;
	}

	////{PROTECTED REGION ID(R-1717237286) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBeneficiarioCedente. 
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
	public java.lang.Long getIdBeneficiarioCedente() {
		return idBeneficiarioCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBeneficiarioRicevente = null;

	/**
	 * @generated
	 */
	public void setIdBeneficiarioRicevente(java.lang.Long val) {
		idBeneficiarioRicevente = val;
	}

	////{PROTECTED REGION ID(R-543879339) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBeneficiarioRicevente. 
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
	public java.lang.Long getIdBeneficiarioRicevente() {
		return idBeneficiarioRicevente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBandoLineaCedente = null;

	/**
	 * @generated
	 */
	public void setIdBandoLineaCedente(java.lang.Long val) {
		idBandoLineaCedente = val;
	}

	////{PROTECTED REGION ID(R-769518655) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBandoLineaCedente. 
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
	public java.lang.Long getIdBandoLineaCedente() {
		return idBandoLineaCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBandoLineaRicevente = null;

	/**
	 * @generated
	 */
	public void setIdBandoLineaRicevente(java.lang.Long val) {
		idBandoLineaRicevente = val;
	}

	////{PROTECTED REGION ID(R-319341700) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBandoLineaRicevente. 
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
	public java.lang.Long getIdBandoLineaRicevente() {
		return idBandoLineaRicevente;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneBeneficiarioCedente = null;

	/**
	 * @generated
	 */
	public void setDenominazioneBeneficiarioCedente(java.lang.String val) {
		denominazioneBeneficiarioCedente = val;
	}

	////{PROTECTED REGION ID(R-582524887) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneBeneficiarioCedente. 
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
	public java.lang.String getDenominazioneBeneficiarioCedente() {
		return denominazioneBeneficiarioCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneBeneficiarioRicevente = null;

	/**
	 * @generated
	 */
	public void setDenominazioneBeneficiarioRicevente(java.lang.String val) {
		denominazioneBeneficiarioRicevente = val;
	}

	////{PROTECTED REGION ID(R-1006957084) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneBeneficiarioRicevente. 
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
	private ResponseCodeMessage responseCodeMessage;
	
	public ResponseCodeMessage getResponseCodeMessage() {
		return responseCodeMessage;
	}

	public void setResponseCodeMessage(ResponseCodeMessage responseCodeMessage) {
		this.responseCodeMessage = responseCodeMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EconomiaDTO [idEconomia=");
		builder.append(idEconomia);
		builder.append(", idProgettoCedente=");
		builder.append(idProgettoCedente);
		builder.append(", idProgettoRicevente=");
		builder.append(idProgettoRicevente);
		builder.append(", codiceVisualizzatoCedente=");
		builder.append(codiceVisualizzatoCedente);
		builder.append(", importoCeduto=");
		builder.append(importoCeduto);
		builder.append(", noteCessione=");
		builder.append(noteCessione);
		builder.append(", dataCessione=");
		builder.append(dataCessione);
		builder.append(", codiceVisualizzatoRicevente=");
		builder.append(codiceVisualizzatoRicevente);
		builder.append(", dataRicezione=");
		builder.append(dataRicezione);
		builder.append(", noteRicezione=");
		builder.append(noteRicezione);
		builder.append(", dataUtilizzo=");
		builder.append(dataUtilizzo);
		builder.append(", dataModifica=");
		builder.append(dataModifica);
		builder.append(", dataInserimento=");
		builder.append(dataInserimento);
		builder.append(", idBeneficiarioCedente=");
		builder.append(idBeneficiarioCedente);
		builder.append(", idBeneficiarioRicevente=");
		builder.append(idBeneficiarioRicevente);
		builder.append(", idBandoLineaCedente=");
		builder.append(idBandoLineaCedente);
		builder.append(", idBandoLineaRicevente=");
		builder.append(idBandoLineaRicevente);
		builder.append(", denominazioneBeneficiarioCedente=");
		builder.append(denominazioneBeneficiarioCedente);
		builder.append(", denominazioneBeneficiarioRicevente=");
		builder.append(denominazioneBeneficiarioRicevente);
		builder.append(", responseCodeMessage=");
		builder.append(responseCodeMessage);
		builder.append("]");
		return builder.toString();
	}
	
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazioneBeneficiarioRicevente() {
		return denominazioneBeneficiarioRicevente;
	}

	/*PROTECTED REGION ID(R1388047124) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
