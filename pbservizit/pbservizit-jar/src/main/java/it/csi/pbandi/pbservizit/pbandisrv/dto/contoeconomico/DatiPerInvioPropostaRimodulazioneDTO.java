/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R1623884511) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiPerInvioPropostaRimodulazioneDTO.
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
public class DatiPerInvioPropostaRimodulazioneDTO
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
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] modalitaDiAgevolazione = null;

	/**
	 * @generated
	 */
	public void setModalitaDiAgevolazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] val) {
		modalitaDiAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-112491657) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO[] getModalitaDiAgevolazione() {
		return modalitaDiAgevolazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO totaliModalitaDiAgevolazione = null;

	/**
	 * @generated
	 */
	public void setTotaliModalitaDiAgevolazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO val) {
		totaliModalitaDiAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-346894276) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ModalitaDiAgevolazioneDTO getTotaliModalitaDiAgevolazione() {
		return totaliModalitaDiAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSpesaRichiesto = null;

	/**
	 * @generated
	 */
	public void setImportoSpesaRichiesto(java.lang.Double val) {
		importoSpesaRichiesto = val;
	}

	////{PROTECTED REGION ID(R1722440043) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSpesaRichiesto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoSpesaRichiesto() {
		return importoSpesaRichiesto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R-1558952541) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.ProgettoDTO getProgetto() {
		return progetto;
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

	////{PROTECTED REGION ID(R1465981390) ENABLED START////}
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
	private java.lang.Boolean isImportiModificabili = null;

	/**
	 * @generated
	 */
	public void setIsImportiModificabili(java.lang.Boolean val) {
		isImportiModificabili = val;
	}

	////{PROTECTED REGION ID(R-1303177465) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isImportiModificabili. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsImportiModificabili() {
		return isImportiModificabili;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoFinanziamentoRichiesto = null;

	/**
	 * @generated
	 */
	public void setImportoFinanziamentoRichiesto(java.lang.Double val) {
		importoFinanziamentoRichiesto = val;
	}

	////{PROTECTED REGION ID(R65322250) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoFinanziamentoRichiesto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoFinanziamentoRichiesto() {
		return importoFinanziamentoRichiesto;
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

	////{PROTECTED REGION ID(R-1298792448) ENABLED START////}
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
	private java.lang.Long idSoggettoRappresentante = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoRappresentante(java.lang.Long val) {
		idSoggettoRappresentante = val;
	}

	////{PROTECTED REGION ID(R1099090936) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoRappresentante. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSoggettoRappresentante() {
		return idSoggettoRappresentante;
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

	////{PROTECTED REGION ID(R-1072662793) ENABLED START////}
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
	private java.lang.Long idProceduraAggiudicazione = null;

	/**
	 * @generated
	 */
	public void setIdProceduraAggiudicazione(java.lang.Long val) {
		idProceduraAggiudicazione = val;
	}

	////{PROTECTED REGION ID(R163121884) ENABLED START////}
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
	private java.lang.Long idDelegato = null;

	/**
	 * @generated
	 */
	public void setIdDelegato(java.lang.Long val) {
		idDelegato = val;
	}

	////{PROTECTED REGION ID(R-1814905879) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDelegato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdDelegato() {
		return idDelegato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoOperazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoOperazione(java.lang.Long val) {
		idTipoOperazione = val;
	}

	////{PROTECTED REGION ID(R114578962) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoOperazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	/*PROTECTED REGION ID(R1067624034) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
