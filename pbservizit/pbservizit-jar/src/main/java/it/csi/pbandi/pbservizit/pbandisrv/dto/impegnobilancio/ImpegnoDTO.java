/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R-108101550) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ImpegnoDTO.
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
public class ImpegnoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R407875074) ENABLED START////}
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
	private java.lang.Long annoImpegno = null;

	/**
	 * @generated
	 */
	public void setAnnoImpegno(java.lang.Long val) {
		annoImpegno = val;
	}

	////{PROTECTED REGION ID(R1669978511) ENABLED START////}
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

	////{PROTECTED REGION ID(R1761605857) ENABLED START////}
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
	private java.lang.Long annoEsercizio = null;

	/**
	 * @generated
	 */
	public void setAnnoEsercizio(java.lang.Long val) {
		annoEsercizio = val;
	}

	////{PROTECTED REGION ID(R-1765864993) ENABLED START////}
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
	private java.lang.Long importoInizialeImpegno = null;

	/**
	 * @generated
	 */
	public void setImportoInizialeImpegno(java.lang.Long val) {
		importoInizialeImpegno = val;
	}

	////{PROTECTED REGION ID(R-385591536) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoInizialeImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getImportoInizialeImpegno() {
		return importoInizialeImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAttualeImpegno = null;

	/**
	 * @generated
	 */
	public void setImportoAttualeImpegno(java.lang.Double val) {
		importoAttualeImpegno = val;
	}

	////{PROTECTED REGION ID(R468096161) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAttualeImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Long totaleLiquidatoImpegno = null;

	/**
	 * @generated
	 */
	public void setTotaleLiquidatoImpegno(java.lang.Long val) {
		totaleLiquidatoImpegno = val;
	}

	////{PROTECTED REGION ID(R1370576450) ENABLED START////}
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
	public java.lang.Long getTotaleLiquidatoImpegno() {
		return totaleLiquidatoImpegno;
	}

	/**
	 * @generated
	 */
	private java.lang.Double disponibilitaLiquidare = null;

	/**
	 * @generated
	 */
	public void setDisponibilitaLiquidare(java.lang.Double val) {
		disponibilitaLiquidare = val;
	}

	////{PROTECTED REGION ID(R-1823173263) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo disponibilitaLiquidare. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getDisponibilitaLiquidare() {
		return disponibilitaLiquidare;
	}

	/**
	 * @generated
	 */
	private java.lang.Long totaleQuietanzatoImpegno = null;

	/**
	 * @generated
	 */
	public void setTotaleQuietanzatoImpegno(java.lang.Long val) {
		totaleQuietanzatoImpegno = val;
	}

	////{PROTECTED REGION ID(R-1008045249) ENABLED START////}
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
	public java.lang.Long getTotaleQuietanzatoImpegno() {
		return totaleQuietanzatoImpegno;
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

	////{PROTECTED REGION ID(R-1376352479) ENABLED START////}
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

	////{PROTECTED REGION ID(R1325805041) ENABLED START////}
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
	private java.lang.String cupImpegno = null;

	/**
	 * @generated
	 */
	public void setCupImpegno(java.lang.String val) {
		cupImpegno = val;
	}

	////{PROTECTED REGION ID(R1860288867) ENABLED START////}
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

	////{PROTECTED REGION ID(R-725485184) ENABLED START////}
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
	private java.lang.Long numeroCapitoloOrigine = null;

	/**
	 * @generated
	 */
	public void setNumeroCapitoloOrigine(java.lang.Long val) {
		numeroCapitoloOrigine = val;
	}

	////{PROTECTED REGION ID(R-265115940) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroCapitoloOrigine. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getNumeroCapitoloOrigine() {
		return numeroCapitoloOrigine;
	}

	/**
	 * @generated
	 */
	private StatoImpegnoDTO statoImpegno = null;

	/**
	 * @generated
	 */
	public void setStatoImpegno(
			StatoImpegnoDTO val) {
		statoImpegno = val;
	}

	////{PROTECTED REGION ID(R-1985060890) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public StatoImpegnoDTO getStatoImpegno() {
		return statoImpegno;
	}

	/**
	 * @generated
	 */
	private ProvvedimentoDTO provvedimento = null;

	/**
	 * @generated
	 */
	public void setProvvedimento(
			ProvvedimentoDTO val) {
		provvedimento = val;
	}

	////{PROTECTED REGION ID(R1630251906) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo provvedimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public ProvvedimentoDTO getProvvedimento() {
		return provvedimento;
	}

	/**
	 * @generated
	 */
	private CapitoloDTO capitolo = null;

	/**
	 * @generated
	 */
	public void setCapitolo(
			CapitoloDTO val) {
		capitolo = val;
	}

	////{PROTECTED REGION ID(R1030205159) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo capitolo. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public CapitoloDTO getCapitolo() {
		return capitolo;
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

	////{PROTECTED REGION ID(R-934609577) ENABLED START////}
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

	////{PROTECTED REGION ID(R-842982231) ENABLED START////}
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
	private java.lang.String descImpegno = null;

	/**
	 * @generated
	 */
	public void setDescImpegno(java.lang.String val) {
		descImpegno = val;
	}

	////{PROTECTED REGION ID(R-1838479284) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descImpegno. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDescImpegno() {
		return descImpegno;
	}

	/**
	 * @generated
	 */
	private TrasferimentoDTO trasferimento = null;

	/**
	 * @generated
	 */
	public void setTrasferimento(
			TrasferimentoDTO val) {
		trasferimento = val;
	}

	////{PROTECTED REGION ID(R-1171654375) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo trasferimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public TrasferimentoDTO getTrasferimento() {
		return trasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numeroTotaleMandati = null;

	/**
	 * @generated
	 */
	public void setNumeroTotaleMandati(java.lang.Long val) {
		numeroTotaleMandati = val;
	}

	////{PROTECTED REGION ID(R-1433634083) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroTotaleMandati. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getNumeroTotaleMandati() {
		return numeroTotaleMandati;
	}

	/**
	 * @generated
	 */
	private BeneficiarioImpegnoDTO beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(
			BeneficiarioImpegnoDTO val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R-375637620) ENABLED START////}
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
	public BeneficiarioImpegnoDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagNoProvvedimento = null;

	/**
	 * @generated
	 */
	public void setFlagNoProvvedimento(java.lang.String val) {
		flagNoProvvedimento = val;
	}

	////{PROTECTED REGION ID(R-1007192139) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagNoProvvedimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFlagNoProvvedimento() {
		return flagNoProvvedimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceFiscale = null;

	/**
	 * @generated
	 */
	public void setCodiceFiscale(java.lang.String val) {
		codiceFiscale = val;
	}

	////{PROTECTED REGION ID(R740638146) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @generated
	 */
	private java.lang.String ragsoc = null;

	/**
	 * @generated
	 */
	public void setRagsoc(java.lang.String val) {
		ragsoc = val;
	}

	////{PROTECTED REGION ID(R-293770943) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ragsoc. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
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
		StringBuilder builder = new StringBuilder();
		builder.append("ImpegnoDTO [idImpegno=");
		builder.append(idImpegno);
		builder.append(", annoImpegno=");
		builder.append(annoImpegno);
		builder.append(", numeroImpegno=");
		builder.append(numeroImpegno);
		builder.append(", annoEsercizio=");
		builder.append(annoEsercizio);
		builder.append(", importoInizialeImpegno=");
		builder.append(importoInizialeImpegno);
		builder.append(", importoAttualeImpegno=");
		builder.append(importoAttualeImpegno);
		builder.append(", totaleLiquidatoImpegno=");
		builder.append(totaleLiquidatoImpegno);
		builder.append(", disponibilitaLiquidare=");
		builder.append(disponibilitaLiquidare);
		builder.append(", totaleQuietanzatoImpegno=");
		builder.append(totaleQuietanzatoImpegno);
		builder.append(", dtInserimento=");
		builder.append(dtInserimento);
		builder.append(", dtAggiornamento=");
		builder.append(dtAggiornamento);
		builder.append(", cupImpegno=");
		builder.append(cupImpegno);
		builder.append(", cigImpegno=");
		builder.append(cigImpegno);
		builder.append(", numeroCapitoloOrigine=");
		builder.append(numeroCapitoloOrigine);
		builder.append(", statoImpegno=");
		builder.append(statoImpegno);
		builder.append(", provvedimento=");
		builder.append(provvedimento);
		builder.append(", capitolo=");
		builder.append(capitolo);
		builder.append(", annoPerente=");
		builder.append(annoPerente);
		builder.append(", numeroPerente=");
		builder.append(numeroPerente);
		builder.append(", descImpegno=");
		builder.append(descImpegno);
		builder.append(", trasferimento=");
		builder.append(trasferimento);
		builder.append(", numeroTotaleMandati=");
		builder.append(numeroTotaleMandati);
		builder.append(", beneficiario=");
		builder.append(beneficiario);
		builder.append(", flagNoProvvedimento=");
		builder.append(flagNoProvvedimento);
		builder.append(", codiceFiscale=");
		builder.append(codiceFiscale);
		builder.append(", ragsoc=");
		builder.append(ragsoc);
		builder.append("]");
		return builder.toString();
	}
	////{PROTECTED REGION END////}
	public java.lang.String getRagsoc() {
		return ragsoc;
	}

	/*PROTECTED REGION ID(R-160606449) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
