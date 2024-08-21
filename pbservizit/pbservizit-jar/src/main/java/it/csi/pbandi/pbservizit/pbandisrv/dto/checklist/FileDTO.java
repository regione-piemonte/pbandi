/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.checklist;

////{PROTECTED REGION ID(R-2112860653) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FileDTO.
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
public class FileDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R-2013641160) ENABLED START////}
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
	private java.lang.String codiceVisualizzato = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzato(java.lang.String val) {
		codiceVisualizzato = val;
	}

	////{PROTECTED REGION ID(R1129247961) ENABLED START////}
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
	private java.lang.String descBreveStatoDocSpesa = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoDocSpesa(java.lang.String val) {
		descBreveStatoDocSpesa = val;
	}

	////{PROTECTED REGION ID(R-620962183) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoDocSpesa. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescBreveStatoDocSpesa() {
		return descBreveStatoDocSpesa;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInserimento = null;

	/**
	 * @generated
	 */
	public void setDtInserimento(java.util.Date val) {
		dtInserimento = val;
	}

	////{PROTECTED REGION ID(R-1581327616) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInserimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtInserimento() {
		return dtInserimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggiornamento = null;

	/**
	 * @generated
	 */
	public void setDtAggiornamento(java.util.Date val) {
		dtAggiornamento = val;
	}

	////{PROTECTED REGION ID(R1913194000) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggiornamento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFolder = null;

	/**
	 * @generated
	 */
	public void setIdFolder(java.lang.Long val) {
		idFolder = val;
	}

	////{PROTECTED REGION ID(R268640380) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFolder. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdFolder() {
		return idFolder;
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

	////{PROTECTED REGION ID(R231115916) ENABLED START////}
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
	private java.lang.Long idEntita = null;

	/**
	 * @generated
	 */
	public void setIdEntita(java.lang.Long val) {
		idEntita = val;
	}

	////{PROTECTED REGION ID(R239331289) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEntita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdEntita() {
		return idEntita;
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

	////{PROTECTED REGION ID(R-830191022) ENABLED START////}
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
	private java.lang.Long idTarget = null;

	/**
	 * @generated
	 */
	public void setIdTarget(java.lang.Long val) {
		idTarget = val;
	}

	////{PROTECTED REGION ID(R656700831) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTarget. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdTarget() {
		return idTarget;
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

	////{PROTECTED REGION ID(R-1793476657) ENABLED START////}
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
	private java.lang.Boolean isLocked = null;

	/**
	 * @generated
	 */
	public void setIsLocked(java.lang.Boolean val) {
		isLocked = val;
	}

	////{PROTECTED REGION ID(R867807207) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isLocked. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsLocked() {
		return isLocked;
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

	////{PROTECTED REGION ID(R-1623429304) ENABLED START////}
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
	private java.lang.String numProtocollo = null;

	/**
	 * @generated
	 */
	public void setNumProtocollo(java.lang.String val) {
		numProtocollo = val;
	}

	////{PROTECTED REGION ID(R941954830) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numProtocollo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumProtocollo() {
		return numProtocollo;
	}

	/**
	 * @generated
	 */
	private java.lang.Long sizeFile = null;

	/**
	 * @generated
	 */
	public void setSizeFile(java.lang.Long val) {
		sizeFile = val;
	}

	////{PROTECTED REGION ID(R2137779248) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sizeFile. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getSizeFile() {
		return sizeFile;
	}

	/**
	 * @generated
	 */
	private java.lang.Long entityAssociated = null;

	/**
	 * @generated
	 */
	public void setEntityAssociated(java.lang.Long val) {
		entityAssociated = val;
	}

	////{PROTECTED REGION ID(R-1371792036) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo entityAssociated. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getEntityAssociated() {
		return entityAssociated;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtEntita = null;

	/**
	 * @generated
	 */
	public void setDtEntita(java.util.Date val) {
		dtEntita = val;
	}

	////{PROTECTED REGION ID(R1430371214) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtEntita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtEntita() {
		return dtEntita;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagEntita = null;

	/**
	 * @generated
	 */
	public void setFlagEntita(java.lang.String val) {
		flagEntita = val;
	}

	////{PROTECTED REGION ID(R-325541718) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagEntita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFlagEntita() {
		return flagEntita;
	}

	/*PROTECTED REGION ID(R-794266792) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
