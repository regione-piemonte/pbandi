/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R-226234598) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiPerConclusioneRimodulazioneDTO.
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
public class DatiPerConclusioneRimodulazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione = null;

	/**
	 * @generated
	 */
	public void setModalitaDiAgevolazione(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] val) {
		modalitaDiAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-1873133198) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo modalitaDiAgevolazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] getModalitaDiAgevolazione() {
		return modalitaDiAgevolazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaliModalitaDiAgevolazione = null;

	/**
	 * @generated
	 */
	public void setTotaliModalitaDiAgevolazione(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO val) {
		totaliModalitaDiAgevolazione = val;
	}

	////{PROTECTED REGION ID(R1465640439) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaliModalitaDiAgevolazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO getTotaliModalitaDiAgevolazione() {
		return totaliModalitaDiAgevolazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiFinanziarieNonPrivate = null;

	/**
	 * @generated
	 */
	public void setFontiFinanziarieNonPrivate(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] val) {
		fontiFinanziarieNonPrivate = val;
	}

	////{PROTECTED REGION ID(R1427864074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fontiFinanziarieNonPrivate. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] getFontiFinanziarieNonPrivate() {
		return fontiFinanziarieNonPrivate;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] fontiFinanziariePrivate = null;

	/**
	 * @generated
	 */
	public void setFontiFinanziariePrivate(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] val) {
		fontiFinanziariePrivate = val;
	}

	////{PROTECTED REGION ID(R-1844410897) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fontiFinanziariePrivate. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO[] getFontiFinanziariePrivate() {
		return fontiFinanziariePrivate;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO subTotaliFontiFinanziarieNonPrivate = null;

	/**
	 * @generated
	 */
	public void setSubTotaliFontiFinanziarieNonPrivate(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO val) {
		subTotaliFontiFinanziarieNonPrivate = val;
	}

	////{PROTECTED REGION ID(R791050747) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo subTotaliFontiFinanziarieNonPrivate. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO getSubTotaliFontiFinanziarieNonPrivate() {
		return subTotaliFontiFinanziarieNonPrivate;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO totaliFontiFinanziarie = null;

	/**
	 * @generated
	 */
	public void setTotaliFontiFinanziarie(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO val) {
		totaliFontiFinanziarie = val;
	}

	////{PROTECTED REGION ID(R2120821049) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaliFontiFinanziarie. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.FontiFinanziarieDTO getTotaliFontiFinanziarie() {
		return totaliFontiFinanziarie;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSpesaAmmessa = null;

	/**
	 * @generated
	 */
	public void setImportoSpesaAmmessa(java.lang.Double val) {
		importoSpesaAmmessa = val;
	}

	////{PROTECTED REGION ID(R874048279) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSpesaAmmessa. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoSpesaAmmessa() {
		return importoSpesaAmmessa;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProgettoDTO val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R1986479838) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progetto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProgettoDTO getProgetto() {
		return progetto;
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

	////{PROTECTED REGION ID(R580289036) ENABLED START////}
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
	private java.lang.String riferimento = null;

	/**
	 * @generated
	 */
	public void setRiferimento(java.lang.String val) {
		riferimento = val;
	}

	////{PROTECTED REGION ID(R-436191830) ENABLED START////}
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
	private java.lang.Double importoFinanziamentoBancario = null;

	/**
	 * @generated
	 */
	public void setImportoFinanziamentoBancario(java.lang.Double val) {
		importoFinanziamentoBancario = val;
	}

	////{PROTECTED REGION ID(R668178354) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoFinanziamentoBancario. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoFinanziamentoBancario() {
		return importoFinanziamentoBancario;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		idSoggetto = val;
	}

	////{PROTECTED REGION ID(R-47341957) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggetto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idContoEconomico = null;

	/**
	 * @generated
	 */
	public void setIdContoEconomico(java.lang.Long val) {
		idContoEconomico = val;
	}

	////{PROTECTED REGION ID(R9964850) ENABLED START////}
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
	public java.lang.Long getIdContoEconomico() {
		return idContoEconomico;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataConcessione = null;

	/**
	 * @generated
	 */
	public void setDataConcessione(java.lang.String val) {
		dataConcessione = val;
	}

	////{PROTECTED REGION ID(R-1991916099) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataConcessione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDataConcessione() {
		return dataConcessione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProceduraAggiudicazione = null;

	/**
	 * @generated
	 */
	public void setIdProceduraAggiudicazione(java.lang.Long val) {
		idProceduraAggiudicazione = val;
	}

	////{PROTECTED REGION ID(R-968407295) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProceduraAggiudicazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdProceduraAggiudicazione() {
		return idProceduraAggiudicazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO proceduraAggiudicazioneBeneficiario = null;

	/**
	 * @generated
	 */
	public void setProceduraAggiudicazioneBeneficiario(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO val) {
		proceduraAggiudicazioneBeneficiario = val;
	}

	////{PROTECTED REGION ID(R905513590) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo proceduraAggiudicazioneBeneficiario. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.ProceduraAggiudicazioneDTO getProceduraAggiudicazioneBeneficiario() {
		return proceduraAggiudicazioneBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSpesaAmmessaUltima = null;

	/**
	 * @generated
	 */
	public void setImportoSpesaAmmessaUltima(java.lang.Double val) {
		importoSpesaAmmessaUltima = val;
	}

	////{PROTECTED REGION ID(R1918904279) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSpesaAmmessaUltima. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoSpesaAmmessaUltima() {
		return importoSpesaAmmessaUltima;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoOperazione = null;

	/**
	 * @generated
	 */
	public void setTipoOperazione(java.lang.String val) {
		tipoOperazione = val;
	}

	////{PROTECTED REGION ID(R1514380498) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoOperazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTipoOperazione() {
		return tipoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoImpegnoVincolante = null;

	/**
	 * @generated
	 */
	public void setImportoImpegnoVincolante(java.lang.Double val) {
		importoImpegnoVincolante = val;
	}

	////{PROTECTED REGION ID(R-1111633342) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoImpegnoVincolante. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoImpegnoVincolante() {
		return importoImpegnoVincolante;
	}

	/**
	 * @generated
	 */
	private java.lang.String contrattiDaStipulare = null;

	/**
	 * @generated
	 */
	public void setContrattiDaStipulare(java.lang.String val) {
		contrattiDaStipulare = val;
	}

	////{PROTECTED REGION ID(R934062506) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo contrattiDaStipulare. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getContrattiDaStipulare() {
		return contrattiDaStipulare;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isPeriodoUnico = null;

	/**
	 * @generated
	 */
	public void setIsPeriodoUnico(java.lang.Boolean val) {
		isPeriodoUnico = val;
	}

	////{PROTECTED REGION ID(R-940368110) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isPeriodoUnico. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsPeriodoUnico() {
		return isPeriodoUnico;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.PercentualeBandoSoggFinanzDTO[] percentualiBandoSoggFinanzDTO = null;

	/**
	 * @generated
	 */
	public void setPercentualiBandoSoggFinanzDTO(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.PercentualeBandoSoggFinanzDTO[] val) {
		percentualiBandoSoggFinanzDTO = val;
	}

	////{PROTECTED REGION ID(R-1216070789) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualiBandoSoggFinanzDTO. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.PercentualeBandoSoggFinanzDTO[] getPercentualiBandoSoggFinanzDTO() {
		return percentualiBandoSoggFinanzDTO;
	}

	/*PROTECTED REGION ID(R1349215495) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
