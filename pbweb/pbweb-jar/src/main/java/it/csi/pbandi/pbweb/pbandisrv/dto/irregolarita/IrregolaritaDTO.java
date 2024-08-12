package it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita;

////{PROTECTED REGION ID(R-683626646) ENABLED START////}
/**
 * Inserire qui la documentazione della classe IrregolaritaDTO.
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
public class IrregolaritaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idIrregolarita = null;

	/**
	 * @generated
	 */
	public void setIdIrregolarita(java.lang.Long val) {
		idIrregolarita = val;
	}

	////{PROTECTED REGION ID(R1767574658) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIrregolarita. 
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
	public java.lang.Long getIdIrregolarita() {
		return idIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroIms = null;

	/**
	 * @generated
	 */
	public void setNumeroIms(java.lang.String val) {
		numeroIms = val;
	}

	////{PROTECTED REGION ID(R329841129) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroIms. 
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
	public java.lang.String getNumeroIms() {
		return numeroIms;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtIms = null;

	/**
	 * @generated
	 */
	public void setDtIms(java.util.Date val) {
		dtIms = val;
	}

	////{PROTECTED REGION ID(R191720437) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtIms. 
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
	public java.util.Date getDtIms() {
		return dtIms;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtComunicazione = null;

	/**
	 * @generated
	 */
	public void setDtComunicazione(java.util.Date val) {
		dtComunicazione = val;
	}

	////{PROTECTED REGION ID(R-1887233744) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtComunicazione. 
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
	public java.util.Date getDtComunicazione() {
		return dtComunicazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagCasoChiuso = null;

	/**
	 * @generated
	 */
	public void setFlagCasoChiuso(java.lang.String val) {
		flagCasoChiuso = val;
	}

	////{PROTECTED REGION ID(R-115976003) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagCasoChiuso. 
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
	public java.lang.String getFlagCasoChiuso() {
		return flagCasoChiuso;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagBlocco = null;

	/**
	 * @generated
	 */
	public void setFlagBlocco(java.lang.String val) {
		flagBlocco = val;
	}

	////{PROTECTED REGION ID(R-1040225792) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagBlocco. 
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
	public java.lang.String getFlagBlocco() {
		return flagBlocco;
	}

	/**
	 * @generated
	 */
	private java.lang.String notePraticaUsata = null;

	/**
	 * @generated
	 */
	public void setNotePraticaUsata(java.lang.String val) {
		notePraticaUsata = val;
	}

	////{PROTECTED REGION ID(R1616467482) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo notePraticaUsata. 
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
	public java.lang.String getNotePraticaUsata() {
		return notePraticaUsata;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroVersione = null;

	/**
	 * @generated
	 */
	public void setNumeroVersione(java.lang.Long val) {
		numeroVersione = val;
	}

	////{PROTECTED REGION ID(R229792947) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroVersione. 
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
	public java.lang.Long getNumeroVersione() {
		return numeroVersione;
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

	////{PROTECTED REGION ID(R-1456985367) ENABLED START////}
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
	private java.lang.Long idStatoAmministrativo = null;

	/**
	 * @generated
	 */
	public void setIdStatoAmministrativo(java.lang.Long val) {
		idStatoAmministrativo = val;
	}

	////{PROTECTED REGION ID(R-481177865) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoAmministrativo. 
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
	public java.lang.Long getIdStatoAmministrativo() {
		return idStatoAmministrativo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDispComunitaria = null;

	/**
	 * @generated
	 */
	public void setIdDispComunitaria(java.lang.Long val) {
		idDispComunitaria = val;
	}

	////{PROTECTED REGION ID(R-643818169) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDispComunitaria. 
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
	public java.lang.Long getIdDispComunitaria() {
		return idDispComunitaria;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMetodoIndividuazione = null;

	/**
	 * @generated
	 */
	public void setIdMetodoIndividuazione(java.lang.Long val) {
		idMetodoIndividuazione = val;
	}

	////{PROTECTED REGION ID(R1580336039) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMetodoIndividuazione. 
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
	public java.lang.Long getIdMetodoIndividuazione() {
		return idMetodoIndividuazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNaturaSanzione = null;

	/**
	 * @generated
	 */
	public void setIdNaturaSanzione(java.lang.Long val) {
		idNaturaSanzione = val;
	}

	////{PROTECTED REGION ID(R-1482832865) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNaturaSanzione. 
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
	public java.lang.Long getIdNaturaSanzione() {
		return idNaturaSanzione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoIrregolarita = null;

	/**
	 * @generated
	 */
	public void setIdTipoIrregolarita(java.lang.Long val) {
		idTipoIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-1510233258) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoIrregolarita. 
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
	public java.lang.Long getIdTipoIrregolarita() {
		return idTipoIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idQualificazioneIrreg = null;

	/**
	 * @generated
	 */
	public void setIdQualificazioneIrreg(java.lang.Long val) {
		idQualificazioneIrreg = val;
	}

	////{PROTECTED REGION ID(R1523366026) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idQualificazioneIrreg. 
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
	public java.lang.Long getIdQualificazioneIrreg() {
		return idQualificazioneIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idStatoFinanziario = null;

	/**
	 * @generated
	 */
	public void setIdStatoFinanziario(java.lang.Long val) {
		idStatoFinanziario = val;
	}

	////{PROTECTED REGION ID(R-1368776504) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoFinanziario. 
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
	public java.lang.Long getIdStatoFinanziario() {
		return idStatoFinanziario;
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

	////{PROTECTED REGION ID(R544231024) ENABLED START////}
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
	private java.lang.String descTipoIrregolarita = null;

	/**
	 * @generated
	 */
	public void setDescTipoIrregolarita(java.lang.String val) {
		descTipoIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-130130356) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoIrregolarita. 
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
	public java.lang.String getDescTipoIrregolarita() {
		return descTipoIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoIrregolarita = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoIrregolarita(java.lang.String val) {
		descBreveTipoIrregolarita = val;
	}

	////{PROTECTED REGION ID(R-516882278) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoIrregolarita. 
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
	public java.lang.String getDescBreveTipoIrregolarita() {
		return descBreveTipoIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.String descQualificazioneIrreg = null;

	/**
	 * @generated
	 */
	public void setDescQualificazioneIrreg(java.lang.String val) {
		descQualificazioneIrreg = val;
	}

	////{PROTECTED REGION ID(R446994900) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descQualificazioneIrreg. 
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
	public java.lang.String getDescQualificazioneIrreg() {
		return descQualificazioneIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveQualificIrreg = null;

	/**
	 * @generated
	 */
	public void setDescBreveQualificIrreg(java.lang.String val) {
		descBreveQualificIrreg = val;
	}

	////{PROTECTED REGION ID(R-891959076) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveQualificIrreg. 
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
	public java.lang.String getDescBreveQualificIrreg() {
		return descBreveQualificIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.String descDispComunitaria = null;

	/**
	 * @generated
	 */
	public void setDescDispComunitaria(java.lang.String val) {
		descDispComunitaria = val;
	}

	////{PROTECTED REGION ID(R1063269265) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descDispComunitaria. 
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
	public java.lang.String getDescDispComunitaria() {
		return descDispComunitaria;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveDispComunitaria = null;

	/**
	 * @generated
	 */
	public void setDescBreveDispComunitaria(java.lang.String val) {
		descBreveDispComunitaria = val;
	}

	////{PROTECTED REGION ID(R-611774589) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveDispComunitaria. 
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
	public java.lang.String getDescBreveDispComunitaria() {
		return descBreveDispComunitaria;
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

	////{PROTECTED REGION ID(R-529376603) ENABLED START////}
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
	private java.lang.String descBreveMetodoInd = null;

	/**
	 * @generated
	 */
	public void setDescBreveMetodoInd(java.lang.String val) {
		descBreveMetodoInd = val;
	}

	////{PROTECTED REGION ID(R-1512055848) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveMetodoInd. 
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
	public java.lang.String getDescBreveMetodoInd() {
		return descBreveMetodoInd;
	}

	/**
	 * @generated
	 */
	private java.lang.String descMetodoInd = null;

	/**
	 * @generated
	 */
	public void setDescMetodoInd(java.lang.String val) {
		descMetodoInd = val;
	}

	////{PROTECTED REGION ID(R-223436186) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descMetodoInd. 
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
	public java.lang.String getDescMetodoInd() {
		return descMetodoInd;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveStatoAmministrativ = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoAmministrativ(java.lang.String val) {
		descBreveStatoAmministrativ = val;
	}

	////{PROTECTED REGION ID(R-827632452) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoAmministrativ. 
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
	public java.lang.String getDescBreveStatoAmministrativ() {
		return descBreveStatoAmministrativ;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoAmministrativo = null;

	/**
	 * @generated
	 */
	public void setDescStatoAmministrativo(java.lang.String val) {
		descStatoAmministrativo = val;
	}

	////{PROTECTED REGION ID(R-1557548991) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoAmministrativo. 
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
	public java.lang.String getDescStatoAmministrativo() {
		return descStatoAmministrativo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveStatoFinanziario = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoFinanziario(java.lang.String val) {
		descBreveStatoFinanziario = val;
	}

	////{PROTECTED REGION ID(R-375425524) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoFinanziario. 
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
	public java.lang.String getDescBreveStatoFinanziario() {
		return descBreveStatoFinanziario;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoFinanziario = null;

	/**
	 * @generated
	 */
	public void setDescStatoFinanziario(java.lang.String val) {
		descStatoFinanziario = val;
	}

	////{PROTECTED REGION ID(R11326398) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoFinanziario. 
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
	public java.lang.String getDescStatoFinanziario() {
		return descStatoFinanziario;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagProvvedimento = null;

	/**
	 * @generated
	 */
	public void setFlagProvvedimento(java.lang.String val) {
		flagProvvedimento = val;
	}

	////{PROTECTED REGION ID(R-1106180002) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagProvvedimento. 
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
	public java.lang.String getFlagProvvedimento() {
		return flagProvvedimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveNaturaSanzione = null;

	/**
	 * @generated
	 */
	public void setDescBreveNaturaSanzione(java.lang.String val) {
		descBreveNaturaSanzione = val;
	}

	////{PROTECTED REGION ID(R-1343251869) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveNaturaSanzione. 
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
	public java.lang.String getDescBreveNaturaSanzione() {
		return descBreveNaturaSanzione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descNaturaSanzione = null;

	/**
	 * @generated
	 */
	public void setDescNaturaSanzione(java.lang.String val) {
		descNaturaSanzione = val;
	}

	////{PROTECTED REGION ID(R96255125) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descNaturaSanzione. 
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
	public java.lang.String getDescNaturaSanzione() {
		return descNaturaSanzione;
	}

	/**
	 * @generated
	 */
	private java.lang.String soggettoResponsabile = null;

	/**
	 * @generated
	 */
	public void setSoggettoResponsabile(java.lang.String val) {
		soggettoResponsabile = val;
	}

	////{PROTECTED REGION ID(R481350893) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo soggettoResponsabile. 
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
	public java.lang.String getSoggettoResponsabile() {
		return soggettoResponsabile;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO schedaOLAF = null;

	/**
	 * @generated
	 */
	public void setSchedaOLAF(
			it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO val) {
		schedaOLAF = val;
	}

	////{PROTECTED REGION ID(R-1710694090) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo schedaOLAF. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO getSchedaOLAF() {
		return schedaOLAF;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO datiAggiuntivi = null;

	/**
	 * @generated
	 */
	public void setDatiAggiuntivi(
			it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO val) {
		datiAggiuntivi = val;
	}

	////{PROTECTED REGION ID(R2087850693) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo datiAggiuntivi. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO getDatiAggiuntivi() {
		return datiAggiuntivi;
	}

	/**
	 * @generated
	 */
	private java.lang.String cfBeneficiario = null;

	/**
	 * @generated
	 */
	public void setCfBeneficiario(java.lang.String val) {
		cfBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-1336478169) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cfBeneficiario. 
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
	public java.lang.String getCfBeneficiario() {
		return cfBeneficiario;
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

	////{PROTECTED REGION ID(R-1428291242) ENABLED START////}
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
	private java.lang.Long idIrregolaritaCollegata = null;

	/**
	 * @generated
	 */
	public void setIdIrregolaritaCollegata(java.lang.Long val) {
		idIrregolaritaCollegata = val;
	}

	////{PROTECTED REGION ID(R1398103262) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIrregolaritaCollegata. 
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
	public java.lang.Long getIdIrregolaritaCollegata() {
		return idIrregolaritaCollegata;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIrregolarita = null;

	/**
	 * @generated
	 */
	public void setImportoIrregolarita(java.lang.Double val) {
		importoIrregolarita = val;
	}

	////{PROTECTED REGION ID(R408570045) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIrregolarita. 
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
	public java.lang.Double getImportoIrregolarita() {
		return importoIrregolarita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double quotaImpIrregCertificato = null;

	/**
	 * @generated
	 */
	public void setQuotaImpIrregCertificato(java.lang.Double val) {
		quotaImpIrregCertificato = val;
	}

	////{PROTECTED REGION ID(R1642445204) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo quotaImpIrregCertificato. 
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
	public java.lang.Double getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}

	/**
	 * @generated
	 */
	private java.lang.String descDisimpegnoAssociato = null;

	/**
	 * @generated
	 */
	public void setDescDisimpegnoAssociato(java.lang.String val) {
		descDisimpegnoAssociato = val;
	}

	////{PROTECTED REGION ID(R-1200140340) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descDisimpegnoAssociato. 
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
	public java.lang.String getDescDisimpegnoAssociato() {
		return descDisimpegnoAssociato;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoControlli = null;

	/**
	 * @generated
	 */
	public void setTipoControlli(java.lang.String val) {
		tipoControlli = val;
	}

	////{PROTECTED REGION ID(R-1099897956) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoControlli. 
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
	public java.lang.String getTipoControlli() {
		return tipoControlli;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAgevolazioneIrreg = null;

	/**
	 * @generated
	 */
	public void setImportoAgevolazioneIrreg(java.lang.Double val) {
		importoAgevolazioneIrreg = val;
	}

	////{PROTECTED REGION ID(R1094463617) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAgevolazioneIrreg. 
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
	public java.lang.Double getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMotivoRevoca = null;

	/**
	 * @generated
	 */
	public void setIdMotivoRevoca(java.lang.Long val) {
		idMotivoRevoca = val;
	}

	////{PROTECTED REGION ID(R-316273441) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMotivoRevoca. 
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
	public java.lang.Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	/**
	 * @generated
	 */
	private java.lang.String descMotivoRevoca = null;

	/**
	 * @generated
	 */
	public void setDescMotivoRevoca(java.lang.String val) {
		descMotivoRevoca = val;
	}

	////{PROTECTED REGION ID(R-462116139) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descMotivoRevoca. 
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
	public java.lang.String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInizioControlli = null;

	/**
	 * @generated
	 */
	public void setDataInizioControlli(java.util.Date val) {
		dataInizioControlli = val;
	}

	////{PROTECTED REGION ID(R1261416842) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInizioControlli. 
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
	public java.util.Date getDataInizioControlli() {
		return dataInizioControlli;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataFineControlli = null;

	/**
	 * @generated
	 */
	public void setDataFineControlli(java.util.Date val) {
		dataFineControlli = val;
	}

	////{PROTECTED REGION ID(R279241036) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataFineControlli. 
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
	public java.util.Date getDataFineControlli() {
		return dataFineControlli;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idIrregolaritaProvv = null;

	/**
	 * @generated
	 */
	public void setIdIrregolaritaProvv(java.lang.Long val) {
		idIrregolaritaProvv = val;
	}

	////{PROTECTED REGION ID(R-1809887541) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIrregolaritaProvv. 
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
	public java.lang.Long getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtComunicazioneProvv = null;

	/**
	 * @generated
	 */
	public void setDtComunicazioneProvv(java.util.Date val) {
		dtComunicazioneProvv = val;
	}

	////{PROTECTED REGION ID(R-1442185507) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtComunicazioneProvv. 
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
	public java.util.Date getDtComunicazioneProvv() {
		return dtComunicazioneProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoControlliProvv = null;

	/**
	 * @generated
	 */
	public void setTipoControlliProvv(java.lang.String val) {
		tipoControlliProvv = val;
	}

	////{PROTECTED REGION ID(R846583793) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoControlliProvv. 
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
	public java.lang.String getTipoControlliProvv() {
		return tipoControlliProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtFineProvvisoriaProvv = null;

	/**
	 * @generated
	 */
	public void setDtFineProvvisoriaProvv(java.util.Date val) {
		dtFineProvvisoriaProvv = val;
	}

	////{PROTECTED REGION ID(R1744454895) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFineProvvisoriaProvv. 
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
	public java.util.Date getDtFineProvvisoriaProvv() {
		return dtFineProvvisoriaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgettoProvv = null;

	/**
	 * @generated
	 */
	public void setIdProgettoProvv(java.lang.Long val) {
		idProgettoProvv = val;
	}

	////{PROTECTED REGION ID(R228071620) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoProvv. 
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
	public java.lang.Long getIdProgettoProvv() {
		return idProgettoProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMotivoRevocaProvv = null;

	/**
	 * @generated
	 */
	public void setIdMotivoRevocaProvv(java.lang.Long val) {
		idMotivoRevocaProvv = val;
	}

	////{PROTECTED REGION ID(R1441202318) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMotivoRevocaProvv. 
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
	public java.lang.Long getIdMotivoRevocaProvv() {
		return idMotivoRevocaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String descMotivoRevocaProvv = null;

	/**
	 * @generated
	 */
	public void setDescMotivoRevocaProvv(java.lang.String val) {
		descMotivoRevocaProvv = val;
	}

	////{PROTECTED REGION ID(R1274719320) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descMotivoRevocaProvv. 
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
	public java.lang.String getDescMotivoRevocaProvv() {
		return descMotivoRevocaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIrregolaritaProvv = null;

	/**
	 * @generated
	 */
	public void setImportoIrregolaritaProvv(java.lang.Double val) {
		importoIrregolaritaProvv = val;
	}

	////{PROTECTED REGION ID(R871595888) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIrregolaritaProvv. 
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
	public java.lang.Double getImportoIrregolaritaProvv() {
		return importoIrregolaritaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAgevolazioneIrregProvv = null;

	/**
	 * @generated
	 */
	public void setImportoAgevolazioneIrregProvv(java.lang.Double val) {
		importoAgevolazioneIrregProvv = val;
	}

	////{PROTECTED REGION ID(R-308293076) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAgevolazioneIrregProvv. 
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
	public java.lang.Double getImportoAgevolazioneIrregProvv() {
		return importoAgevolazioneIrregProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIrregolareCertificatoProvv = null;

	/**
	 * @generated
	 */
	public void setImportoIrregolareCertificatoProvv(java.lang.Double val) {
		importoIrregolareCertificatoProvv = val;
	}

	////{PROTECTED REGION ID(R972369784) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIrregolareCertificatoProvv. 
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
	public java.lang.Double getImportoIrregolareCertificatoProvv() {
		return importoIrregolareCertificatoProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtFineValiditaProvv = null;

	/**
	 * @generated
	 */
	public void setDtFineValiditaProvv(java.util.Date val) {
		dtFineValiditaProvv = val;
	}

	////{PROTECTED REGION ID(R-713001665) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFineValiditaProvv. 
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
	public java.util.Date getDtFineValiditaProvv() {
		return dtFineValiditaProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInizioControlliProvv = null;

	/**
	 * @generated
	 */
	public void setDataInizioControlliProvv(java.util.Date val) {
		dataInizioControlliProvv = val;
	}

	////{PROTECTED REGION ID(R1410371267) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInizioControlliProvv. 
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
	public java.util.Date getDataInizioControlliProvv() {
		return dataInizioControlliProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataFineControlliProvv = null;

	/**
	 * @generated
	 */
	public void setDataFineControlliProvv(java.util.Date val) {
		dataFineControlliProvv = val;
	}

	////{PROTECTED REGION ID(R780986433) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataFineControlliProvv. 
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
	public java.util.Date getDataFineControlliProvv() {
		return dataFineControlliProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagIrregolaritaAnnullataProvv = null;

	/**
	 * @generated
	 */
	public void setFlagIrregolaritaAnnullataProvv(java.lang.String val) {
		flagIrregolaritaAnnullataProvv = val;
	}

	////{PROTECTED REGION ID(R1015439654) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagIrregolaritaAnnullataProvv. 
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
	public java.lang.String getFlagIrregolaritaAnnullataProvv() {
		return flagIrregolaritaAnnullataProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idPeriodo = null;

	/**
	 * @generated
	 */
	public void setIdPeriodo(java.lang.Long val) {
		idPeriodo = val;
	}

	////{PROTECTED REGION ID(R1246228745) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPeriodo. 
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
	public java.lang.Long getIdPeriodo() {
		return idPeriodo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descPeriodoVisualizzata = null;

	/**
	 * @generated
	 */
	public void setDescPeriodoVisualizzata(java.lang.String val) {
		descPeriodoVisualizzata = val;
	}

	////{PROTECTED REGION ID(R581498040) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descPeriodoVisualizzata. 
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
	public java.lang.String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idPeriodoProvv = null;

	/**
	 * @generated
	 */
	public void setIdPeriodoProvv(java.lang.Long val) {
		idPeriodoProvv = val;
	}

	////{PROTECTED REGION ID(R1577002660) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPeriodoProvv. 
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
	public java.lang.Long getIdPeriodoProvv() {
		return idPeriodoProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String descPeriodoVisualizzataProvv = null;

	/**
	 * @generated
	 */
	public void setDescPeriodoVisualizzataProvv(java.lang.String val) {
		descPeriodoVisualizzataProvv = val;
	}

	////{PROTECTED REGION ID(R-479717291) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descPeriodoVisualizzataProvv. 
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
	public java.lang.String getDescPeriodoVisualizzataProvv() {
		return descPeriodoVisualizzataProvv;
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

	////{PROTECTED REGION ID(R677474870) ENABLED START////}
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

	////{PROTECTED REGION ID(R-1910404992) ENABLED START////}
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
	private java.lang.Long idCategAnagraficaProvv = null;

	/**
	 * @generated
	 */
	public void setIdCategAnagraficaProvv(java.lang.Long val) {
		idCategAnagraficaProvv = val;
	}

	////{PROTECTED REGION ID(R-713533033) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCategAnagraficaProvv. 
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
	public java.lang.Long getIdCategAnagraficaProvv() {
		return idCategAnagraficaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCategAnagraficaProvv = null;

	/**
	 * @generated
	 */
	public void setDescCategAnagraficaProvv(java.lang.String val) {
		descCategAnagraficaProvv = val;
	}

	////{PROTECTED REGION ID(R278700429) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCategAnagraficaProvv. 
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
	public java.lang.String getDescCategAnagraficaProvv() {
		return descCategAnagraficaProvv;
	}

	/**
	 * @generated
	 */
	private java.lang.String esitoControllo = null;

	/**
	 * @generated
	 */
	public void setEsitoControllo(java.lang.String val) {
		esitoControllo = val;
	}

	////{PROTECTED REGION ID(R-2041285388) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoControllo. 
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
	public java.lang.String getEsitoControllo() {
		return esitoControllo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idEsitoControllo = null;

	/**
	 * @generated
	 */
	public void setIdEsitoControllo(java.lang.Long val) {
		idEsitoControllo = val;
	}

	////{PROTECTED REGION ID(R1807806575) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEsitoControllo. 
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
	public java.lang.Long getIdEsitoControllo() {
		return idEsitoControllo;
	}

	/**
	 * @generated
	 */
	private java.lang.String note = null;

	/**
	 * @generated
	 */
	public void setNote(java.lang.String val) {
		note = val;
	}

	////{PROTECTED REGION ID(R-547710372) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo note. 
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
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteProvv = null;

	/**
	 * @generated
	 */
	public void setNoteProvv(java.lang.String val) {
		noteProvv = val;
	}

	////{PROTECTED REGION ID(R349198641) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteProvv. 
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
	public java.lang.String getNoteProvv() {
		return noteProvv;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataCampione = null;

	/**
	 * @generated
	 */
	public void setDataCampione(java.util.Date val) {
		dataCampione = val;
	}

	////{PROTECTED REGION ID(R-1849973358) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataCampione. 
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
	public java.util.Date getDataCampione() {
		return dataCampione;
	}

	/*PROTECTED REGION ID(R935658081) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {
		return "IrregolaritaDTO [cfBeneficiario=" + cfBeneficiario
				+ ", codiceVisualizzato=" + codiceVisualizzato
				+ ", dataCampione=" + dataCampione + ", dataFineControlli="
				+ dataFineControlli + ", idIrregolarita=" + idIrregolarita
				+ ", idPeriodo=" + idPeriodo + ", idProgetto=" + idProgetto

				/*				
				 + ", dataFineControlliProvv="
				 + dataFineControlliProvv + ", dataInizioControlli="
				 + dataInizioControlli + ", dataInizioControlliProvv="
				 + dataInizioControlliProvv + ", datiAggiuntivi="
				 + datiAggiuntivi + ", denominazioneBeneficiario="
				 + denominazioneBeneficiario + ", descBreveDispComunitaria="
				 + descBreveDispComunitaria + ", descBreveMetodoInd="
				 + descBreveMetodoInd + ", descBreveNaturaSanzione="
				 + descBreveNaturaSanzione + ", descBreveQualificIrreg="
				 + descBreveQualificIrreg + ", descBreveStatoAmministrativ="
				 + descBreveStatoAmministrativ + ", descBreveStatoFinanziario="
				 + descBreveStatoFinanziario + ", descBreveTipoIrregolarita="
				 + descBreveTipoIrregolarita + ", descCategAnagrafica="
				 + descCategAnagrafica + ", descCategAnagraficaProvv="
				 + descCategAnagraficaProvv + ", descDisimpegnoAssociato="
				 + descDisimpegnoAssociato + ", descDispComunitaria="
				 + descDispComunitaria + ", descMetodoInd=" + descMetodoInd
				 + ", descMotivoRevoca=" + descMotivoRevoca
				 + ", descMotivoRevocaProvv=" + descMotivoRevocaProvv
				 + ", descNaturaSanzione=" + descNaturaSanzione
				 + ", descPeriodoVisualizzata=" + descPeriodoVisualizzata
				 + ", descPeriodoVisualizzataProvv="
				 + descPeriodoVisualizzataProvv + ", descQualificazioneIrreg="
				 + descQualificazioneIrreg + ", descStatoAmministrativo="
				 + descStatoAmministrativo + ", descStatoFinanziario="
				 + descStatoFinanziario + ", descTipoIrregolarita="
				 + descTipoIrregolarita + ", dtComunicazione=" + dtComunicazione
				 + ", dtComunicazioneProvv=" + dtComunicazioneProvv
				 + ", dtFineProvvisoriaProvv=" + dtFineProvvisoriaProvv
				 + ", dtFineValiditaProvv=" + dtFineValiditaProvv + ", dtIms="
				 + dtIms + ", esitoControllo=" + esitoControllo
				 + ", flagBlocco=" + flagBlocco + ", flagCasoChiuso="
				 + flagCasoChiuso + ", flagIrregolaritaAnnullataProvv="
				 + flagIrregolaritaAnnullataProvv + ", flagProvvedimento="
				 + flagProvvedimento + ", idCategAnagrafica="
				 + idCategAnagrafica + ", idCategAnagraficaProvv="
				 + idCategAnagraficaProvv + ", idDispComunitaria="
				 + idDispComunitaria + ", idEsitoControllo=" + idEsitoControllo
				 + ", idIrregolaritaCollegata=" + idIrregolaritaCollegata
				 + ", idIrregolaritaProvv=" + idIrregolaritaProvv
				 + ", idMetodoIndividuazione=" + idMetodoIndividuazione
				 + ", idMotivoRevoca=" + idMotivoRevoca
				 + ", idMotivoRevocaProvv=" + idMotivoRevocaProvv
				 + ", idNaturaSanzione=" + idNaturaSanzione + ", idPeriodoProvv=" + idPeriodoProvv
				 + ", idProgettoProvv="
				 + idProgettoProvv + ", idQualificazioneIrreg="
				 + idQualificazioneIrreg + ", idSoggettoBeneficiario="
				 + idSoggettoBeneficiario + ", idStatoAmministrativo="
				 + idStatoAmministrativo + ", idStatoFinanziario="
				 + idStatoFinanziario + ", idTipoIrregolarita="
				 + idTipoIrregolarita + ", importoAgevolazioneIrreg="
				 + importoAgevolazioneIrreg + ", importoAgevolazioneIrregProvv="
				 + importoAgevolazioneIrregProvv
				 + ", importoIrregolareCertificatoProvv="
				 + importoIrregolareCertificatoProvv + ", importoIrregolarita="
				 + importoIrregolarita + ", importoIrregolaritaProvv="
				 + importoIrregolaritaProvv + ", note=" + note
				 + ", notePraticaUsata=" + notePraticaUsata + ", noteProvv="
				 + noteProvv + ", numeroIms=" + numeroIms + ", numeroVersione="
				 + numeroVersione + ", quotaImpIrregCertificato="
				 + quotaImpIrregCertificato + ", schedaOLAF=" + schedaOLAF
				 + ", soggettoResponsabile=" + soggettoResponsabile
				 */
				+ ", tipoControlli=" + tipoControlli + ", tipoControlliProvv="
				+ tipoControlliProvv + "]";
	}
	/*PROTECTED REGION END*/
}
