/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.consultazioneattibilancio;

////{PROTECTED REGION ID(R-1779648924) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AttoDiLiquidazioneDTO.
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
public class AttoDiLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idAttoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdAttoLiquidazione(java.lang.Long val) {
		idAttoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-1310468353) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttoLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoAtto = null;

	/**
	 * @generated
	 */
	public void setAnnoAtto(java.lang.String val) {
		annoAtto = val;
	}

	////{PROTECTED REGION ID(R1422007328) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoAtto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoAtto() {
		return annoAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroAtto = null;

	/**
	 * @generated
	 */
	public void setNumeroAtto(java.lang.String val) {
		numeroAtto = val;
	}

	////{PROTECTED REGION ID(R2091241870) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroAtto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroAtto() {
		return numeroAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSettoreEnte = null;

	/**
	 * @generated
	 */
	public void setIdSettoreEnte(java.lang.Long val) {
		idSettoreEnte = val;
	}

	////{PROTECTED REGION ID(R-957956533) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreEnte. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSettoreEnte() {
		return idSettoreEnte;
	}

	/**
	 * @generated
	 */
	private java.lang.String estremiAtto = null;

	/**
	 * @generated
	 */
	public void setEstremiAtto(java.lang.String val) {
		estremiAtto = val;
	}

	////{PROTECTED REGION ID(R-1765322689) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo estremiAtto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getEstremiAtto() {
		return estremiAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoAtto = null;

	/**
	 * @generated
	 */
	public void setDescStatoAtto(java.lang.String val) {
		descStatoAtto = val;
	}

	////{PROTECTED REGION ID(R-594014796) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoAtto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescStatoAtto() {
		return descStatoAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoEsercizioImpegno = null;

	/**
	 * @generated
	 */
	public void setAnnoEsercizioImpegno(java.lang.String val) {
		annoEsercizioImpegno = val;
	}

	////{PROTECTED REGION ID(R1241795042) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoEsercizioImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoEsercizioImpegno() {
		return annoEsercizioImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoImpegno = null;

	/**
	 * @generated
	 */
	public void setAnnoImpegno(java.lang.String val) {
		annoImpegno = val;
	}

	////{PROTECTED REGION ID(R63669309) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoImpegno() {
		return annoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceVisualizzatoProgetto = null;

	/**
	 * @generated
	 */
	public void setCodiceVisualizzatoProgetto(java.lang.String val) {
		codiceVisualizzatoProgetto = val;
	}

	////{PROTECTED REGION ID(R-1885915442) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceVisualizzatoProgetto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneBeneficiarioBil = null;

	/**
	 * @generated
	 */
	public void setDenominazioneBeneficiarioBil(java.lang.String val) {
		denominazioneBeneficiarioBil = val;
	}

	////{PROTECTED REGION ID(R2097850313) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneBeneficiarioBil. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDenominazioneBeneficiarioBil() {
		return denominazioneBeneficiarioBil;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBeneficiarioBilancio = null;

	/**
	 * @generated
	 */
	public void setIdBeneficiarioBilancio(java.lang.Long val) {
		idBeneficiarioBilancio = val;
	}

	////{PROTECTED REGION ID(R1835216656) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBeneficiarioBilancio. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
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

	////{PROTECTED REGION ID(R-1000652445) ENABLED START////}
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
	private java.lang.String numeroImpegno = null;

	/**
	 * @generated
	 */
	public void setNumeroImpegno(java.lang.String val) {
		numeroImpegno = val;
	}

	////{PROTECTED REGION ID(R-8278001) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroImpegno() {
		return numeroImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String impegno = null;

	/**
	 * @generated
	 */
	public void setImpegno(java.lang.String val) {
		impegno = val;
	}

	////{PROTECTED REGION ID(R1161321675) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getImpegno() {
		return impegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveStatoAtto = null;

	/**
	 * @generated
	 */
	public void setDescBreveStatoAtto(java.lang.String val) {
		descBreveStatoAtto = val;
	}

	////{PROTECTED REGION ID(R335455322) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveStatoAtto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
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

	////{PROTECTED REGION ID(R1336234309) ENABLED START////}
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

	/*PROTECTED REGION ID(R-1502661251) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
