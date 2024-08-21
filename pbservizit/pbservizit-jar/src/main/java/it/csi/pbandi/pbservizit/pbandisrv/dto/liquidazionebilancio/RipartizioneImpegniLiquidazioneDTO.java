/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R1190015333) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RipartizioneImpegniLiquidazioneDTO.
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
public class RipartizioneImpegniLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idFonteFinanziaria = null;

	/**
	 * @generated
	 */
	public void setIdFonteFinanziaria(java.lang.Long val) {
		idFonteFinanziaria = val;
	}

	////{PROTECTED REGION ID(R-473388614) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFonteFinanziaria. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdFonteFinanziaria() {
		return idFonteFinanziaria;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idImpegno = null;

	/**
	 * @generated
	 */
	public void setIdImpegno(java.lang.Long val) {
		idImpegno = val;
	}

	////{PROTECTED REGION ID(R1316651599) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdImpegno() {
		return idImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.String descFonteFinanziaria = null;

	/**
	 * @generated
	 */
	public void setDescFonteFinanziaria(java.lang.String val) {
		descFonteFinanziaria = val;
	}

	////{PROTECTED REGION ID(R992606704) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descFonteFinanziaria. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescFonteFinanziaria() {
		return descFonteFinanziaria;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCausale = null;

	/**
	 * @generated
	 */
	public void setDescCausale(java.lang.String val) {
		descCausale = val;
	}

	////{PROTECTED REGION ID(R-1751175608) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCausale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescCausale() {
		return descCausale;
	}

	/**
	 * @generated
	 */
	private java.lang.String capitoloNumero = null;

	/**
	 * @generated
	 */
	public void setCapitoloNumero(java.lang.String val) {
		capitoloNumero = val;
	}

	////{PROTECTED REGION ID(R1925795254) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo capitoloNumero. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCapitoloNumero() {
		return capitoloNumero;
	}

	/**
	 * @generated
	 */
	private java.lang.String impegnoAnnoNumero = null;

	/**
	 * @generated
	 */
	public void setImpegnoAnnoNumero(java.lang.String val) {
		impegnoAnnoNumero = val;
	}

	////{PROTECTED REGION ID(R1753553588) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impegnoAnnoNumero. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getImpegnoAnnoNumero() {
		return impegnoAnnoNumero;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoImpegno = null;

	/**
	 * @generated
	 */
	public void setImportoImpegno(java.lang.Double val) {
		importoImpegno = val;
	}

	////{PROTECTED REGION ID(R-128797814) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoImpegno() {
		return importoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Double disponibilitaResidua = null;

	/**
	 * @generated
	 */
	public void setDisponibilitaResidua(java.lang.Double val) {
		disponibilitaResidua = val;
	}

	////{PROTECTED REGION ID(R-397847757) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo disponibilitaResidua. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getDisponibilitaResidua() {
		return disponibilitaResidua;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDaLiquidare = null;

	/**
	 * @generated
	 */
	public void setImportoDaLiquidare(java.lang.Double val) {
		importoDaLiquidare = val;
	}

	////{PROTECTED REGION ID(R-400504330) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDaLiquidare. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoDaLiquidare() {
		return importoDaLiquidare;
	}

	/**
	 * @generated
	 */
	private java.lang.String cig = null;

	/**
	 * @generated
	 */
	public void setCig(java.lang.String val) {
		cig = val;
	}

	////{PROTECTED REGION ID(R1086822716) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cig. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCig() {
		return cig;
	}

	/**
	 * @generated
	 */
	private java.lang.Long annoEsercizio = null;

	/**
	 * @generated
	 */
	public void setAnnoEsercizio(java.lang.Long val) {
		annoEsercizio = val;
	}

	////{PROTECTED REGION ID(R1469902764) ENABLED START////}
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
	public java.lang.Long getAnnoEsercizio() {
		return annoEsercizio;
	}

	/**
	 * @generated
	 */
	private java.lang.Long annoImpegno = null;

	/**
	 * @generated
	 */
	public void setAnnoImpegno(java.lang.Long val) {
		annoImpegno = val;
	}

	////{PROTECTED REGION ID(R-1169109348) ENABLED START////}
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
	public java.lang.Long getAnnoImpegno() {
		return annoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroImpegno = null;

	/**
	 * @generated
	 */
	public void setNumeroImpegno(java.lang.Long val) {
		numeroImpegno = val;
	}

	////{PROTECTED REGION ID(R702406318) ENABLED START////}
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
	public java.lang.Long getNumeroImpegno() {
		return numeroImpegno;
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

	////{PROTECTED REGION ID(R1086823097) ENABLED START////}
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

	/**
	 * @generated
	 */
	private java.lang.String tipoProv = null;

	/**
	 * @generated
	 */
	public void setTipoProv(java.lang.String val) {
		tipoProv = val;
	}

	////{PROTECTED REGION ID(R-1694192350) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoProv. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTipoProv() {
		return tipoProv;
	}

	/**
	 * @generated
	 */
	private java.lang.String nroProv = null;

	/**
	 * @generated
	 */
	public void setNroProv(java.lang.String val) {
		nroProv = val;
	}

	////{PROTECTED REGION ID(R1249351727) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nroProv. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getNroProv() {
		return nroProv;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoProv = null;

	/**
	 * @generated
	 */
	public void setAnnoProv(java.lang.String val) {
		annoProv = val;
	}

	////{PROTECTED REGION ID(R-362557540) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoProv. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getAnnoProv() {
		return annoProv;
	}

	/**
	 * @generated
	 */
	private java.lang.String direzione = null;

	/**
	 * @generated
	 */
	public void setDirezione(java.lang.String val) {
		direzione = val;
	}

	////{PROTECTED REGION ID(R-598619494) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo direzione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDirezione() {
		return direzione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long annoPerente = null;

	/**
	 * @generated
	 */
	public void setAnnoPerente(java.lang.Long val) {
		annoPerente = val;
	}

	////{PROTECTED REGION ID(R521269860) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoPerente. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getAnnoPerente() {
		return annoPerente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroPerente = null;

	/**
	 * @generated
	 */
	public void setNumeroPerente(java.lang.Long val) {
		numeroPerente = val;
	}

	////{PROTECTED REGION ID(R-1902181770) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroPerente. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getNumeroPerente() {
		return numeroPerente;
	}

	/**
	 * @generated
	 */
	private java.lang.String impegnoPerenteAnnoNumero = null;

	/**
	 * @generated
	 */
	public void setImpegnoPerenteAnnoNumero(java.lang.String val) {
		impegnoPerenteAnnoNumero = val;
	}

	////{PROTECTED REGION ID(R-1154975305) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impegnoPerenteAnnoNumero. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	@Override
	public String toString() {
		return "RipartizioneImpegniLiquidazioneDTO [idFonteFinanziaria=" + idFonteFinanziaria + ", idImpegno="
				+ idImpegno + ", descFonteFinanziaria=" + descFonteFinanziaria + ", descCausale=" + descCausale
				+ ", capitoloNumero=" + capitoloNumero + ", impegnoAnnoNumero=" + impegnoAnnoNumero
				+ ", importoImpegno=" + importoImpegno + ", disponibilitaResidua=" + disponibilitaResidua
				+ ", importoDaLiquidare=" + importoDaLiquidare + ", cig=" + cig + ", annoEsercizio=" + annoEsercizio
				+ ", annoImpegno=" + annoImpegno + ", numeroImpegno=" + numeroImpegno + ", cup=" + cup + ", tipoProv="
				+ tipoProv + ", nroProv=" + nroProv + ", annoProv=" + annoProv + ", direzione=" + direzione
				+ ", annoPerente=" + annoPerente + ", numeroPerente=" + numeroPerente + ", impegnoPerenteAnnoNumero="
				+ impegnoPerenteAnnoNumero + "]";
	}
	////{PROTECTED REGION END////}
	public java.lang.String getImpegnoPerenteAnnoNumero() {
		return impegnoPerenteAnnoNumero;
	}

	/*PROTECTED REGION ID(R800077574) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
