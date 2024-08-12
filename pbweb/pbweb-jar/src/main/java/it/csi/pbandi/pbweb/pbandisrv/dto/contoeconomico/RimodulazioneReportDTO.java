package it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-1576049653) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RimodulazioneReportDTO.
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
public class RimodulazioneReportDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R1569738693) ENABLED START////}
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
	private byte[] bytes = null;

	/**
	 * @generated
	 */
	public void setBytes(byte[] val) {
		bytes = val;
	}

	////{PROTECTED REGION ID(R206711616) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo bytes. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceProgettoVisualizzato = null;

	/**
	 * @generated
	 */
	public void setCodiceProgettoVisualizzato(java.lang.String val) {
		codiceProgettoVisualizzato = val;
	}

	////{PROTECTED REGION ID(R-738623819) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceProgettoVisualizzato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCodiceProgettoVisualizzato() {
		return codiceProgettoVisualizzato;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoItemDTO[] contoEconomicoItem = null;

	/**
	 * @generated
	 */
	public void setContoEconomicoItem(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoItemDTO[] val) {
		contoEconomicoItem = val;
	}

	////{PROTECTED REGION ID(R-2113178597) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo contoEconomicoItem. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoItemDTO[] getContoEconomicoItem() {
		return contoEconomicoItem;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataCreazioneDocumento = null;

	/**
	 * @generated
	 */
	public void setDataCreazioneDocumento(java.util.Date val) {
		dataCreazioneDocumento = val;
	}

	////{PROTECTED REGION ID(R583745725) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataCreazioneDocumento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataCreazioneDocumento() {
		return dataCreazioneDocumento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInvio = null;

	/**
	 * @generated
	 */
	public void setDataInvio(java.util.Date val) {
		dataInvio = val;
	}

	////{PROTECTED REGION ID(R1464319906) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInvio. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataInvio() {
		return dataInvio;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBandoLineaIntervento = null;

	/**
	 * @generated
	 */
	public void setDescBandoLineaIntervento(java.lang.String val) {
		descBandoLineaIntervento = val;
	}

	////{PROTECTED REGION ID(R2059579597) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBandoLineaIntervento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescBandoLineaIntervento() {
		return descBandoLineaIntervento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.EnteAppartenenzaDTO enteAppartenenza = null;

	/**
	 * @generated
	 */
	public void setEnteAppartenenza(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.EnteAppartenenzaDTO val) {
		enteAppartenenza = val;
	}

	////{PROTECTED REGION ID(R-1769880352) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo enteAppartenenza. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.EnteAppartenenzaDTO getEnteAppartenenza() {
		return enteAppartenenza;
	}

	/**
	 * @generated
	 */
	private long idContoEconomico = 0;

	/**
	 * @generated
	 */
	public void setIdContoEconomico(long val) {
		idContoEconomico = val;
	}

	////{PROTECTED REGION ID(R581271587) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idContoEconomico. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public long getIdContoEconomico() {
		return idContoEconomico;
	}

	/**
	 * @generated
	 */
	private long idProgetto = 0;

	/**
	 * @generated
	 */
	public void setIdProgetto(long val) {
		idProgetto = val;
	}

	////{PROTECTED REGION ID(R1006258762) ENABLED START////}
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
	public long getIdProgetto() {
		return idProgetto;
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

	////{PROTECTED REGION ID(R-1885205184) ENABLED START////}
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
	private java.lang.String noteContoEconomico = null;

	/**
	 * @generated
	 */
	public void setNoteContoEconomico(java.lang.String val) {
		noteContoEconomico = val;
	}

	////{PROTECTED REGION ID(R1759021050) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteContoEconomico. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNoteContoEconomico() {
		return noteContoEconomico;
	}

	/**
	 * @generated
	 */
	private java.lang.String riferimento = null;

	/**
	 * @generated
	 */
	public void setRiferimento(java.lang.String val) {
		riferimento = val;
	}

	////{PROTECTED REGION ID(R1589704729) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo riferimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getRiferimento() {
		return riferimento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.RappresentanteLegale rappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setRappresentanteLegale(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.RappresentanteLegale val) {
		rappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R-1490776241) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rappresentanteLegale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.RappresentanteLegale getRappresentanteLegale() {
		return rappresentanteLegale;
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

	////{PROTECTED REGION ID(R1182055650) ENABLED START////}
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
	private java.lang.Double percRibassoAsta = null;

	/**
	 * @generated
	 */
	public void setPercRibassoAsta(java.lang.Double val) {
		percRibassoAsta = val;
	}

	////{PROTECTED REGION ID(R-162130649) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO proceduraAggiudicazione = null;

	/**
	 * @generated
	 */
	public void setProceduraAggiudicazione(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO val) {
		proceduraAggiudicazione = val;
	}

	////{PROTECTED REGION ID(R1702291467) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo proceduraAggiudicazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO getProceduraAggiudicazione() {
		return proceduraAggiudicazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String cup = null;

	/**
	 * @generated
	 */
	public void setCup(java.lang.String val) {
		cup = val;
	}

	////{PROTECTED REGION ID(R487366227) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cup. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCup() {
		return cup;
	}

	/*PROTECTED REGION ID(R-617266954) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
