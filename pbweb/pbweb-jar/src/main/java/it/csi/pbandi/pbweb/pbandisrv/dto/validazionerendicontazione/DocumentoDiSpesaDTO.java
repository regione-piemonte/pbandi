/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R571269666) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DocumentoDiSpesaDTO.
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
public class DocumentoDiSpesaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R-603613600) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleFornitore. 
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
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
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

	////{PROTECTED REGION ID(R-699124839) ENABLED START////}
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
	private java.lang.String cognomeFornitore = null;

	/**
	 * @generated
	 */
	public void setCognomeFornitore(java.lang.String val) {
		cognomeFornitore = val;
	}

	////{PROTECTED REGION ID(R-962597472) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cognomeFornitore. 
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
	public java.lang.String getCognomeFornitore() {
		return cognomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDataDocumentoDiSpesa(java.util.Date val) {
		dataDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1693728759) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDocumentoDiSpesa. 
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
	public java.util.Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDocumentoDiSpesaDiRiferimento = null;

	/**
	 * @generated
	 */
	public void setDataDocumentoDiSpesaDiRiferimento(java.util.Date val) {
		dataDocumentoDiSpesaDiRiferimento = val;
	}

	////{PROTECTED REGION ID(R-203338314) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDocumentoDiSpesaDiRiferimento. 
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
	public java.util.Date getDataDocumentoDiSpesaDiRiferimento() {
		return dataDocumentoDiSpesaDiRiferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrizioneDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescrizioneDocumentoDiSpesa(java.lang.String val) {
		descrizioneDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R1441580842) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneDocumentoDiSpesa. 
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
	public java.lang.String getDescrizioneDocumentoDiSpesa() {
		return descrizioneDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String destinazioneTrasferta = null;

	/**
	 * @generated
	 */
	public void setDestinazioneTrasferta(java.lang.String val) {
		destinazioneTrasferta = val;
	}

	////{PROTECTED REGION ID(R-2090354543) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo destinazioneTrasferta. 
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
	public java.lang.String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
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

	////{PROTECTED REGION ID(R2093950286) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneFornitore. 
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
	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
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

	////{PROTECTED REGION ID(R1067745898) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoDocumentoSpesa. 
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
	public java.lang.String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescTipoDocumentoDiSpesa(java.lang.String val) {
		descTipoDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-2147242300) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDocumentoDiSpesa. 
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
	public java.lang.String getDescTipoDocumentoDiSpesa() {
		return descTipoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipologiaFornitore = null;

	/**
	 * @generated
	 */
	public void setDescTipologiaFornitore(java.lang.String val) {
		descTipologiaFornitore = val;
	}

	////{PROTECTED REGION ID(R531032445) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipologiaFornitore. 
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
	public java.lang.String getDescTipologiaFornitore() {
		return descTipologiaFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Double durataTrasferta = null;

	/**
	 * @generated
	 */
	public void setDurataTrasferta(java.lang.Double val) {
		durataTrasferta = val;
	}

	////{PROTECTED REGION ID(R-102526143) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo durataTrasferta. 
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
	public java.lang.Double getDurataTrasferta() {
		return durataTrasferta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdBeneficiario(java.lang.Long val) {
		idBeneficiario = val;
	}

	////{PROTECTED REGION ID(R79999895) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBeneficiario. 
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
	public java.lang.Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocRiferimento = null;

	/**
	 * @generated
	 */
	public void setIdDocRiferimento(java.lang.Long val) {
		idDocRiferimento = val;
	}

	////{PROTECTED REGION ID(R1928109257) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocRiferimento. 
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
	public java.lang.Long getIdDocRiferimento() {
		return idDocRiferimento;
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

	////{PROTECTED REGION ID(R1256111098) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoDiSpesa. 
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
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFornitore = null;

	/**
	 * @generated
	 */
	public void setIdFornitore(java.lang.Long val) {
		idFornitore = val;
	}

	////{PROTECTED REGION ID(R-1676039691) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFornitore. 
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
	public java.lang.Long getIdFornitore() {
		return idFornitore;
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

	////{PROTECTED REGION ID(R-1807853663) ENABLED START////}
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
	private java.lang.Long idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		idSoggetto = val;
	}

	////{PROTECTED REGION ID(R529033091) ENABLED START////}
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
	private java.lang.Long idSoggettoPartner = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoPartner(java.lang.Long val) {
		idSoggettoPartner = val;
	}

	////{PROTECTED REGION ID(R-860544059) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoPartner. 
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
	public java.lang.Long getIdSoggettoPartner() {
		return idSoggettoPartner;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoDiSpesa(java.lang.Long val) {
		idTipoDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R431142094) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumentoDiSpesa. 
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
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
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

	////{PROTECTED REGION ID(R900151201) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoFornitore. 
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
	public java.lang.Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoOggettoAttivita = null;

	/**
	 * @generated
	 */
	public void setIdTipoOggettoAttivita(java.lang.Long val) {
		idTipoOggettoAttivita = val;
	}

	////{PROTECTED REGION ID(R-704763120) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoOggettoAttivita. 
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
	public java.lang.Long getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double imponibile = null;

	/**
	 * @generated
	 */
	public void setImponibile(java.lang.Double val) {
		imponibile = val;
	}

	////{PROTECTED REGION ID(R645855168) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo imponibile. 
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
	public java.lang.Double getImponibile() {
		return imponibile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIva = null;

	/**
	 * @generated
	 */
	public void setImportoIva(java.lang.Double val) {
		importoIva = val;
	}

	////{PROTECTED REGION ID(R770887340) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIva. 
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
	public java.lang.Double getImportoIva() {
		return importoIva;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoIvaACosto = null;

	/**
	 * @generated
	 */
	public void setImportoIvaACosto(java.lang.Double val) {
		importoIvaACosto = val;
	}

	////{PROTECTED REGION ID(R-1314949075) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoIvaACosto. 
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
	public java.lang.Double getImportoIvaACosto() {
		return importoIvaACosto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRendicontabile = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontabile(java.lang.Double val) {
		importoRendicontabile = val;
	}

	////{PROTECTED REGION ID(R-431169353) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRendicontabile. 
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
	public java.lang.Double getImportoRendicontabile() {
		return importoRendicontabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleDocumentoIvato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleDocumentoIvato(java.lang.Double val) {
		importoTotaleDocumentoIvato = val;
	}

	////{PROTECTED REGION ID(R298758436) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleDocumentoIvato. 
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
	public java.lang.Double getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRitenutaDAcconto = null;

	/**
	 * @generated
	 */
	public void setImportoRitenutaDAcconto(java.lang.Double val) {
		importoRitenutaDAcconto = val;
	}

	////{PROTECTED REGION ID(R-291545541) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRitenutaDAcconto. 
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
	public java.lang.Double getImportoRitenutaDAcconto() {
		return importoRitenutaDAcconto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isGestitiNelProgetto = null;

	/**
	 * @generated
	 */
	public void setIsGestitiNelProgetto(java.lang.Boolean val) {
		isGestitiNelProgetto = val;
	}

	////{PROTECTED REGION ID(R341068902) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isGestitiNelProgetto. 
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
	public java.lang.Boolean getIsGestitiNelProgetto() {
		return isGestitiNelProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isRicercaPerCapofila = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerCapofila(java.lang.Boolean val) {
		isRicercaPerCapofila = val;
	}

	////{PROTECTED REGION ID(R614697127) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerCapofila. 
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
	public java.lang.Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isRicercaPerTutti = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerTutti(java.lang.Boolean val) {
		isRicercaPerTutti = val;
	}

	////{PROTECTED REGION ID(R-916026826) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerTutti. 
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
	public java.lang.Boolean getIsRicercaPerTutti() {
		return isRicercaPerTutti;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isRicercaPerPartner = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerPartner(java.lang.Boolean val) {
		isRicercaPerPartner = val;
	}

	////{PROTECTED REGION ID(R337046454) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerPartner. 
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
	public java.lang.Boolean getIsRicercaPerPartner() {
		return isRicercaPerPartner;
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

	////{PROTECTED REGION ID(R-618219977) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFornitore. 
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
	public java.lang.String getNomeFornitore() {
		return nomeFornitore;
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

	////{PROTECTED REGION ID(R378945430) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumento. 
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
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumentoDiSpesa(java.lang.String val) {
		numeroDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-2118134789) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumentoDiSpesa. 
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
	public java.lang.String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumentoDiSpesaDiRiferimento = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumentoDiSpesaDiRiferimento(java.lang.String val) {
		numeroDocumentoDiSpesaDiRiferimento = val;
	}

	////{PROTECTED REGION ID(R-1957907708) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumentoDiSpesaDiRiferimento. 
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
	public java.lang.String getNumeroDocumentoDiSpesaDiRiferimento() {
		return numeroDocumentoDiSpesaDiRiferimento;
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

	////{PROTECTED REGION ID(R-1067625537) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partitaIvaFornitore. 
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
	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String partner = null;

	/**
	 * @generated
	 */
	public void setPartner(java.lang.String val) {
		partner = val;
	}

	////{PROTECTED REGION ID(R515223654) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo partner. 
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
	public java.lang.String getPartner() {
		return partner;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoOrario = null;

	/**
	 * @generated
	 */
	public void setCostoOrario(java.lang.Double val) {
		costoOrario = val;
	}

	////{PROTECTED REGION ID(R-500168966) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoOrario. 
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
	public java.lang.Double getCostoOrario() {
		return costoOrario;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] statiDocumento = null;

	/**
	 * @generated
	 */
	public void setStatiDocumento(java.lang.String[] val) {
		statiDocumento = val;
	}

	////{PROTECTED REGION ID(R230523969) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statiDocumento. 
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
	public java.lang.String[] getStatiDocumento() {
		return statiDocumento;
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

	////{PROTECTED REGION ID(R-1360972121) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo task. 
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
	public java.lang.String getTask() {
		return task;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleValidato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleValidato(java.lang.Double val) {
		importoTotaleValidato = val;
	}

	////{PROTECTED REGION ID(R-1910239991) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleValidato. 
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
	public java.lang.Double getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveTipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescBreveTipoDocumentoDiSpesa(java.lang.String val) {
		descBreveTipoDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R1692882850) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveTipoDocumentoDiSpesa. 
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
	public java.lang.String getDescBreveTipoDocumentoDiSpesa() {
		return descBreveTipoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleRendicontato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleRendicontato(java.lang.Double val) {
		importoTotaleRendicontato = val;
	}

	////{PROTECTED REGION ID(R883918643) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleRendicontato. 
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
	public java.lang.Double getImportoTotaleRendicontato() {
		return importoTotaleRendicontato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrFornitoreQualifica = null;

	/**
	 * @generated
	 */
	public void setProgrFornitoreQualifica(java.lang.Long val) {
		progrFornitoreQualifica = val;
	}

	////{PROTECTED REGION ID(R1985964639) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrFornitoreQualifica. 
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
	public java.lang.Long getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleQuietanzato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleQuietanzato(java.lang.Double val) {
		importoTotaleQuietanzato = val;
	}

	////{PROTECTED REGION ID(R877782074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleQuietanzato. 
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
	public java.lang.Double getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
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

	////{PROTECTED REGION ID(R1012372256) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoDocumentoSpesa. 
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
	public java.lang.Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Double rendicontabileQuietanzato = null;

	/**
	 * @generated
	 */
	public void setRendicontabileQuietanzato(java.lang.Double val) {
		rendicontabileQuietanzato = val;
	}

	////{PROTECTED REGION ID(R549941202) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rendicontabileQuietanzato. 
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
	public java.lang.Double getRendicontabileQuietanzato() {
		return rendicontabileQuietanzato;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteValidazione = null;

	/**
	 * @generated
	 */
	public void setNoteValidazione(java.lang.String val) {
		noteValidazione = val;
	}

	////{PROTECTED REGION ID(R2061080158) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteValidazione. 
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
	public java.lang.String getNoteValidazione() {
		return noteValidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isValidabile = null;

	/**
	 * @generated
	 */
	public void setIsValidabile(java.lang.Boolean val) {
		isValidabile = val;
	}

	////{PROTECTED REGION ID(R1207620945) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isValidabile. 
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
	public java.lang.Boolean getIsValidabile() {
		return isValidabile;
	}

	/**
	 * @generated
	 */
	private java.lang.String[] msgNonValidabile = null;

	/**
	 * @generated
	 */
	public void setMsgNonValidabile(java.lang.String[] val) {
		msgNonValidabile = val;
	}

	////{PROTECTED REGION ID(R-269933517) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo msgNonValidabile. 
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
	public java.lang.String[] getMsgNonValidabile() {
		return msgNonValidabile;
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

	////{PROTECTED REGION ID(R1674341921) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoInvio. 
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
	public java.lang.String getTipoInvio() {
		return tipoInvio;
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

	////{PROTECTED REGION ID(R1258637322) ENABLED START////}
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

	/*PROTECTED REGION ID(R-406505537) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
