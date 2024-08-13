/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio;

////{PROTECTED REGION ID(R-1145604584) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DettaglioLiquidazioneDTO.
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
public class DettaglioLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String numBilancioLiquidazione = null;

	/**
	 * @generated
	 */
	public void setNumBilancioLiquidazione(java.lang.String val) {
		numBilancioLiquidazione = val;
	}

	////{PROTECTED REGION ID(R1203764759) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numBilancioLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumBilancioLiquidazione() {
		return numBilancioLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoLiquidato = null;

	/**
	 * @generated
	 */
	public void setImportoLiquidato(java.lang.Double val) {
		importoLiquidato = val;
	}

	////{PROTECTED REGION ID(R-460368978) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoLiquidato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoLiquidato() {
		return importoLiquidato;
	}

	/**
	 * @generated
	 */
	private java.lang.String cupLiquidazione = null;

	/**
	 * @generated
	 */
	public void setCupLiquidazione(java.lang.String val) {
		cupLiquidazione = val;
	}

	////{PROTECTED REGION ID(R416939416) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cupLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCupLiquidazione() {
		return cupLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String cigLiquidazione = null;

	/**
	 * @generated
	 */
	public void setCigLiquidazione(java.lang.String val) {
		cigLiquidazione = val;
	}

	////{PROTECTED REGION ID(R534335387) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cigLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCigLiquidazione() {
		return cigLiquidazione;
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

	////{PROTECTED REGION ID(R-1605524709) ENABLED START////}
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

	////{PROTECTED REGION ID(R134624107) ENABLED START////}
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
	private java.util.Date dtAggBilancioLiquidaz = null;

	/**
	 * @generated
	 */
	public void setDtAggBilancioLiquidaz(java.util.Date val) {
		dtAggBilancioLiquidaz = val;
	}

	////{PROTECTED REGION ID(R-860278651) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggBilancioLiquidaz. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtAggBilancioLiquidaz() {
		return dtAggBilancioLiquidaz;
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

	////{PROTECTED REGION ID(R789294089) ENABLED START////}
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
	private java.lang.String numeroImpegno = null;

	/**
	 * @generated
	 */
	public void setNumeroImpegno(java.lang.String val) {
		numeroImpegno = val;
	}

	////{PROTECTED REGION ID(R1532433627) ENABLED START////}
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
	private java.lang.String annoEsercizio = null;

	/**
	 * @generated
	 */
	public void setAnnoEsercizio(java.lang.String val) {
		annoEsercizio = val;
	}

	////{PROTECTED REGION ID(R-1995037223) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoEsercizio. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoEsercizio() {
		return annoEsercizio;
	}

	/**
	 * @generated
	 */
	private java.lang.String cupImpegno = null;

	/**
	 * @generated
	 */
	public void setCupImpegno(java.lang.String val) {
		cupImpegno = val;
	}

	////{PROTECTED REGION ID(R-1493256279) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cupImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCupImpegno() {
		return cupImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String cigImpegno = null;

	/**
	 * @generated
	 */
	public void setCigImpegno(java.lang.String val) {
		cigImpegno = val;
	}

	////{PROTECTED REGION ID(R215936966) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cigImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCigImpegno() {
		return cigImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleLiquidatoImpegno = null;

	/**
	 * @generated
	 */
	public void setTotaleLiquidatoImpegno(java.lang.Double val) {
		totaleLiquidatoImpegno = val;
	}

	////{PROTECTED REGION ID(R1608586120) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleLiquidatoImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getTotaleLiquidatoImpegno() {
		return totaleLiquidatoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Double totaleQuietanzatoImpegno = null;

	/**
	 * @generated
	 */
	public void setTotaleQuietanzatoImpegno(java.lang.Double val) {
		totaleQuietanzatoImpegno = val;
	}

	////{PROTECTED REGION ID(R85980933) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo totaleQuietanzatoImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getTotaleQuietanzatoImpegno() {
		return totaleQuietanzatoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoFondo = null;

	/**
	 * @generated
	 */
	public void setDescTipoFondo(java.lang.String val) {
		descTipoFondo = val;
	}

	////{PROTECTED REGION ID(R1691172499) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoFondo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescTipoFondo() {
		return descTipoFondo;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroCapitolo = null;

	/**
	 * @generated
	 */
	public void setNumeroCapitolo(java.lang.String val) {
		numeroCapitolo = val;
	}

	////{PROTECTED REGION ID(R632814825) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroCapitolo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroCapitolo() {
		return numeroCapitolo;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroMandato = null;

	/**
	 * @generated
	 */
	public void setNumeroMandato(java.lang.String val) {
		numeroMandato = val;
	}

	////{PROTECTED REGION ID(R442048830) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroMandato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroMandato() {
		return numeroMandato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoMandatoLordo = null;

	/**
	 * @generated
	 */
	public void setImportoMandatoLordo(java.lang.Double val) {
		importoMandatoLordo = val;
	}

	////{PROTECTED REGION ID(R-1823771494) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoMandatoLordo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoMandatoLordo() {
		return importoMandatoLordo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRitenute = null;

	/**
	 * @generated
	 */
	public void setImportoRitenute(java.lang.Double val) {
		importoRitenute = val;
	}

	////{PROTECTED REGION ID(R-1540329518) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRitenute. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoRitenute() {
		return importoRitenute;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoMandatoNetto = null;

	/**
	 * @generated
	 */
	public void setImportoMandatoNetto(java.lang.Double val) {
		importoMandatoNetto = val;
	}

	////{PROTECTED REGION ID(R-1822219944) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoMandatoNetto. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoMandatoNetto() {
		return importoMandatoNetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoQuietanzato = null;

	/**
	 * @generated
	 */
	public void setImportoQuietanzato(java.lang.Double val) {
		importoQuietanzato = val;
	}

	////{PROTECTED REGION ID(R2067492113) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoQuietanzato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoQuietanzato() {
		return importoQuietanzato;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtQuietanzaMandato = null;

	/**
	 * @generated
	 */
	public void setDtQuietanzaMandato(java.util.Date val) {
		dtQuietanzaMandato = val;
	}

	////{PROTECTED REGION ID(R-1447874702) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtQuietanzaMandato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtQuietanzaMandato() {
		return dtQuietanzaMandato;
	}

	/**
	 * @generated
	 */
	private java.lang.String cupMandato = null;

	/**
	 * @generated
	 */
	public void setCupMandato(java.lang.String val) {
		cupMandato = val;
	}

	////{PROTECTED REGION ID(R1711326220) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cupMandato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCupMandato() {
		return cupMandato;
	}

	/**
	 * @generated
	 */
	private java.lang.String cigMandato = null;

	/**
	 * @generated
	 */
	public void setCigMandato(java.lang.String val) {
		cigMandato = val;
	}

	////{PROTECTED REGION ID(R-874447831) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cigMandato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCigMandato() {
		return cigMandato;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInserimentoMandato = null;

	/**
	 * @generated
	 */
	public void setDtInserimentoMandato(java.util.Date val) {
		dtInserimentoMandato = val;
	}

	////{PROTECTED REGION ID(R-932730761) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInserimentoMandato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDtInserimentoMandato() {
		return dtInserimentoMandato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdLiquidazione(java.lang.Long val) {
		idLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-1306625499) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdLiquidazione() {
		return idLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setAnnoLiquidazione(java.lang.String val) {
		annoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-1164524232) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoLiquidazione() {
		return annoLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setStatoLiquidazione(java.lang.String val) {
		statoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R450855541) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoLiquidazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getStatoLiquidazione() {
		return statoLiquidazione;
	}

	/*PROTECTED REGION ID(R-1989504525) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
