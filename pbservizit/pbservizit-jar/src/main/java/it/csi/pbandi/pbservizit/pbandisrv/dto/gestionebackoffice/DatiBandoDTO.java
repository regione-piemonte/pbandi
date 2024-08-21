/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R-1217391038) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiBandoDTO.
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
public class DatiBandoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteIns = null;

	/**
	 * @generated
	 */
	public void setIdUtenteIns(java.lang.Long val) {
		idUtenteIns = val;
	}

	////{PROTECTED REGION ID(R-286416168) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteIns. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdUtenteIns() {
		return idUtenteIns;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteAgg = null;

	/**
	 * @generated
	 */
	public void setIdUtenteAgg(java.lang.Long val) {
		idUtenteAgg = val;
	}

	////{PROTECTED REGION ID(R-286424085) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteAgg. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBando = null;

	/**
	 * @generated
	 */
	public void setIdBando(java.lang.Long val) {
		idBando = val;
	}

	////{PROTECTED REGION ID(R-1923385347) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBando. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdBando() {
		return idBando;
	}

	/**
	 * @generated
	 */
	private java.lang.String titoloBando = null;

	/**
	 * @generated
	 */
	public void setTitoloBando(java.lang.String val) {
		titoloBando = val;
	}

	////{PROTECTED REGION ID(R2018684901) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo titoloBando. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getTitoloBando() {
		return titoloBando;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataPubblicazione = null;

	/**
	 * @generated
	 */
	public void setDataPubblicazione(java.util.Date val) {
		dataPubblicazione = val;
	}

	////{PROTECTED REGION ID(R1865316907) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataPubblicazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataPresentazioneDomande = null;

	/**
	 * @generated
	 */
	public void setDataPresentazioneDomande(java.util.Date val) {
		dataPresentazioneDomande = val;
	}

	////{PROTECTED REGION ID(R705356267) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataPresentazioneDomande. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataPresentazioneDomande() {
		return dataPresentazioneDomande;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataScadenza = null;

	/**
	 * @generated
	 */
	public void setDataScadenza(java.util.Date val) {
		dataScadenza = val;
	}

	////{PROTECTED REGION ID(R-381044849) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataScadenza. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataScadenza() {
		return dataScadenza;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isPrevistaGraduatoria = null;

	/**
	 * @generated
	 */
	public void setIsPrevistaGraduatoria(java.lang.Boolean val) {
		isPrevistaGraduatoria = val;
	}

	////{PROTECTED REGION ID(R1285826783) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isPrevistaGraduatoria. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsPrevistaGraduatoria() {
		return isPrevistaGraduatoria;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isPrevistoAllegato = null;

	/**
	 * @generated
	 */
	public void setIsPrevistoAllegato(java.lang.Boolean val) {
		isPrevistoAllegato = val;
	}

	////{PROTECTED REGION ID(R1157452477) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isPrevistoAllegato. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsPrevistoAllegato() {
		return isPrevistoAllegato;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isQuietanza = null;

	/**
	 * @generated
	 */
	public void setIsQuietanza(java.lang.Boolean val) {
		isQuietanza = val;
	}

	////{PROTECTED REGION ID(R440843964) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isQuietanza. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsQuietanza() {
		return isQuietanza;
	}

	/**
	 * @generated
	 */
	private java.lang.Double dotazioneFinanziaria = null;

	/**
	 * @generated
	 */
	public void setDotazioneFinanziaria(java.lang.Double val) {
		dotazioneFinanziaria = val;
	}

	////{PROTECTED REGION ID(R-1458832301) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dotazioneFinanziaria. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getDotazioneFinanziaria() {
		return dotazioneFinanziaria;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idMateriaRiferimento = null;

	/**
	 * @generated
	 */
	public void setIdMateriaRiferimento(java.lang.Long val) {
		idMateriaRiferimento = val;
	}

	////{PROTECTED REGION ID(R1123981724) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idMateriaRiferimento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdMateriaRiferimento() {
		return idMateriaRiferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double costoTotaleMinimoAmmissibile = null;

	/**
	 * @generated
	 */
	public void setCostoTotaleMinimoAmmissibile(java.lang.Double val) {
		costoTotaleMinimoAmmissibile = val;
	}

	////{PROTECTED REGION ID(R748417735) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo costoTotaleMinimoAmmissibile. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getCostoTotaleMinimoAmmissibile() {
		return costoTotaleMinimoAmmissibile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualePremialita = null;

	/**
	 * @generated
	 */
	public void setPercentualePremialita(java.lang.Double val) {
		percentualePremialita = val;
	}

	////{PROTECTED REGION ID(R-1586438700) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualePremialita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getPercentualePremialita() {
		return percentualePremialita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double punteggioPremialita = null;

	/**
	 * @generated
	 */
	public void setPunteggioPremialita(java.lang.Double val) {
		punteggioPremialita = val;
	}

	////{PROTECTED REGION ID(R-1668374710) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo punteggioPremialita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getPunteggioPremialita() {
		return punteggioPremialita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoPremialita = null;

	/**
	 * @generated
	 */
	public void setImportoPremialita(java.lang.Double val) {
		importoPremialita = val;
	}

	////{PROTECTED REGION ID(R-2020744332) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPremialita. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Double getImportoPremialita() {
		return importoPremialita;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCodiceIntesaIstituzionale = null;

	/**
	 * @generated
	 */
	public void setIdCodiceIntesaIstituzionale(java.lang.Long val) {
		idCodiceIntesaIstituzionale = val;
	}

	////{PROTECTED REGION ID(R104125304) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCodiceIntesaIstituzionale. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdCodiceIntesaIstituzionale() {
		return idCodiceIntesaIstituzionale;
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

	////{PROTECTED REGION ID(R799327093) ENABLED START////}
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

	/**
	 * @generated
	 */
	private java.lang.Long idSettoreCIPE = null;

	/**
	 * @generated
	 */
	public void setIdSettoreCIPE(java.lang.Long val) {
		idSettoreCIPE = val;
	}

	////{PROTECTED REGION ID(R-1637277938) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreCIPE. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSettoreCIPE() {
		return idSettoreCIPE;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSottoSettoreCIPE = null;

	/**
	 * @generated
	 */
	public void setIdSottoSettoreCIPE(java.lang.Long val) {
		idSottoSettoreCIPE = val;
	}

	////{PROTECTED REGION ID(R41712405) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSottoSettoreCIPE. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSottoSettoreCIPE() {
		return idSottoSettoreCIPE;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idNaturaCIPE = null;

	/**
	 * @generated
	 */
	public void setIdNaturaCIPE(java.lang.Long val) {
		idNaturaCIPE = val;
	}

	////{PROTECTED REGION ID(R-997700933) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNaturaCIPE. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdNaturaCIPE() {
		return idNaturaCIPE;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSettoreCPT = null;

	/**
	 * @generated
	 */
	public void setIdSettoreCPT(java.lang.Long val) {
		idSettoreCPT = val;
	}

	////{PROTECTED REGION ID(R-1576835852) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreCPT. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdSettoreCPT() {
		return idSettoreCPT;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipologiaAttivazione = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaAttivazione(java.lang.Long val) {
		idTipologiaAttivazione = val;
	}

	////{PROTECTED REGION ID(R1806672601) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaAttivazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdTipologiaAttivazione() {
		return idTipologiaAttivazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numMaxDomande = null;

	/**
	 * @generated
	 */
	public void setNumMaxDomande(java.lang.Long val) {
		numMaxDomande = val;
	}

	////{PROTECTED REGION ID(R-1186943792) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numMaxDomande. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getNumMaxDomande() {
		return numMaxDomande;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagFindom = null;

	/**
	 * @generated
	 */
	public void setFlagFindom(java.lang.String val) {
		flagFindom = val;
	}

	////{PROTECTED REGION ID(R977719205) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagFindom. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getFlagFindom() {
		return flagFindom;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLineaDiIntervento = null;

	/**
	 * @generated
	 */
	public void setIdLineaDiIntervento(java.lang.Long val) {
		idLineaDiIntervento = val;
	}

	////{PROTECTED REGION ID(R1001273235) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaDiIntervento. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDeterminaApprovazione = null;

	/**
	 * @generated
	 */
	public void setDataDeterminaApprovazione(java.util.Date val) {
		dataDeterminaApprovazione = val;
	}

	////{PROTECTED REGION ID(R892950417) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDeterminaApprovazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.util.Date getDataDeterminaApprovazione() {
		return dataDeterminaApprovazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String determinaApprovazione = null;

	/**
	 * @generated
	 */
	public void setDeterminaApprovazione(java.lang.String val) {
		determinaApprovazione = val;
	}

	////{PROTECTED REGION ID(R-552249317) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo determinaApprovazione. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.String getDeterminaApprovazione() {
		return determinaApprovazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isBandoCultura = null;

	/**
	 * @generated
	 */
	public void setIsBandoCultura(java.lang.Boolean val) {
		isBandoCultura = val;
	}

	////{PROTECTED REGION ID(R-1150791556) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isBandoCultura. 
	 * Descrivere:
	 * <ul>
	 *      <li>se l'attributo deve essere sempre valoriuzzato o meno
	 *      <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 *          dimensioni massime in caso di stringa o tipo array, eventuale necessit�
	 *          di corrispondenza con una particolare codifica, che pu� essere prefissata
	 *          (es. da un elenco predefinito) oppure dinamica (presente su un archivio
	 *          di un'applicazione)
	 *      <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 *          altri attributi della stessa classe.
	 *      <li>...
	 *      </ul>
	 * @generated 
	 */
	////{PROTECTED REGION END////}
	public java.lang.Boolean getIsBandoCultura() {
		return isBandoCultura;
	}

	/*PROTECTED REGION ID(R-180616695) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
