/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

import java.util.Date;

////{PROTECTED REGION ID(R-1618851737) ENABLED START////}
/**
 * Inserire qui la documentazione della classe InformazioniBaseDTO. Consigli:
 * <ul>
 * <li>Descrivere il "concetto" rappresentato dall'entita' (qual'� l'oggetto del
 * dominio del servizio rappresentato)
 * <li>Se necessario indicare se questo concetto � mantenuto sugli archivi di
 * una particolare applicazione
 * <li>Se l'oggetto ha un particolare ciclo di vita (stati, es. creato, da
 * approvare, approvato, respinto, annullato.....) si pu� decidere di descrivere
 * la state machine qui o nella documentazione dell'interfaccia del servizio che
 * manipola quest'oggetto
 * </ul>
 * 
 * @generated
 */
////{PROTECTED REGION END////}
public class InformazioniBaseDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	//// {PROTECTED REGION ID(R-1981624258) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo titoloProgetto. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDomanda = null;

	/**
	 * @generated
	 */
	public void setNumeroDomanda(java.lang.String val) {
		numeroDomanda = val;
	}

	//// {PROTECTED REGION ID(R224478441) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDomanda. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getNumeroDomanda() {
		return numeroDomanda;
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

	//// {PROTECTED REGION ID(R975768695) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cup. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getCup() {
		return cup;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataDecorrenza = null;

	/**
	 * @generated
	 */
	public void setDataDecorrenza(java.lang.String val) {
		dataDecorrenza = val;
	}

	//// {PROTECTED REGION ID(R-100445650) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDecorrenza. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDataDecorrenza() {
		return dataDecorrenza;
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

	//// {PROTECTED REGION ID(R260704464) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataConcessione. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDataConcessione() {
		return dataConcessione;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataComitato = null;

	/**
	 * @generated
	 */
	public void setDataComitato(java.lang.String val) {
		dataComitato = val;
	}

	//// {PROTECTED REGION ID(R1745883233) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataComitato. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDataComitato() {
		return dataComitato;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataGenerazione = null;

	/**
	 * @generated
	 */
	public void setDataGenerazione(java.lang.String val) {
		dataGenerazione = val;
	}

	//// {PROTECTED REGION ID(R962407938) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataGenerazione. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDataGenerazione() {
		return dataGenerazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String idSettoreAttivita = null;

	/**
	 * @generated
	 */
	public void setIdSettoreAttivita(java.lang.String val) {
		idSettoreAttivita = val;
	}

	//// {PROTECTED REGION ID(R796868502) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreAttivita. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String idAttivitaAteco = null;

	/**
	 * @generated
	 */
	public void setIdAttivitaAteco(java.lang.String val) {
		idAttivitaAteco = val;
	}

	//// {PROTECTED REGION ID(R1409550452) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttivitaAteco. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	/**
	 * @generated
	 */
	private java.lang.String idPrioritaQsn = null;

	/**
	 * @generated
	 */
	public void setIdPrioritaQsn(java.lang.String val) {
		idPrioritaQsn = val;
	}

	//// {PROTECTED REGION ID(R-1393259458) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPrioritaQsn. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdPrioritaQsn() {
		return idPrioritaQsn;
	}

	/**
	 * @generated
	 */
	private java.lang.String idObiettivoGeneraleQsn = null;

	/**
	 * @generated
	 */
	public void setIdObiettivoGeneraleQsn(java.lang.String val) {
		idObiettivoGeneraleQsn = val;
	}

	//// {PROTECTED REGION ID(R812287006) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idObiettivoGeneraleQsn.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdObiettivoGeneraleQsn() {
		return idObiettivoGeneraleQsn;
	}

	/**
	 * @generated
	 */
	private java.lang.String idObiettivoSpecificoQsn = null;

	/**
	 * @generated
	 */
	public void setIdObiettivoSpecificoQsn(java.lang.String val) {
		idObiettivoSpecificoQsn = val;
	}

	//// {PROTECTED REGION ID(R1012307552) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idObiettivoSpecificoQsn.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdObiettivoSpecificoQsn() {
		return idObiettivoSpecificoQsn;
	}

	/**
	 * @generated
	 */
	private java.lang.String idStrumentoAttuativo = null;

	/**
	 * @generated
	 */
	public void setIdStrumentoAttuativo(java.lang.String val) {
		idStrumentoAttuativo = val;
	}

	//// {PROTECTED REGION ID(R2036830032) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStrumentoAttuativo.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}

	/**
	 * @generated
	 */
	private java.lang.String idSettoreCpt = null;

	/**
	 * @generated
	 */
	public void setIdSettoreCpt(java.lang.String val) {
		idSettoreCpt = val;
	}

	//// {PROTECTED REGION ID(R256543641) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreCpt. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdSettoreCpt() {
		return idSettoreCpt;
	}

	/**
	 * @generated
	 */
	private java.lang.String idTemaPrioritario = null;

	/**
	 * @generated
	 */
	public void setIdTemaPrioritario(java.lang.String val) {
		idTemaPrioritario = val;
	}

	//// {PROTECTED REGION ID(R-378786075) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTemaPrioritario. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdTemaPrioritario() {
		return idTemaPrioritario;
	}

	/**
	 * @generated
	 */
	private java.lang.String idIndicatoreRisultatoProgramma = null;

	/**
	 * @generated
	 */
	public void setIdIndicatoreRisultatoProgramma(java.lang.String val) {
		idIndicatoreRisultatoProgramma = val;
	}

	//// {PROTECTED REGION ID(R979656757) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIndicatoreRisultatoProgramma.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdIndicatoreRisultatoProgramma() {
		return idIndicatoreRisultatoProgramma;
	}

	/**
	 * @generated
	 */
	private java.lang.String idIndicatoreQsn = null;

	/**
	 * @generated
	 */
	public void setIdIndicatoreQsn(java.lang.String val) {
		idIndicatoreQsn = val;
	}

	//// {PROTECTED REGION ID(R639158740) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIndicatoreQsn. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}

	/**
	 * @generated
	 */
	private java.lang.String idTipoAiuto = null;

	/**
	 * @generated
	 */
	public void setIdTipoAiuto(java.lang.String val) {
		idTipoAiuto = val;
	}

	//// {PROTECTED REGION ID(R-1334777838) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoAiuto. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdTipoAiuto() {
		return idTipoAiuto;
	}

	/**
	 * @generated
	 */
	private java.lang.String idTipoStrumentoProgrammazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoStrumentoProgrammazione(java.lang.String val) {
		idTipoStrumentoProgrammazione = val;
	}

	//// {PROTECTED REGION ID(R1228011542) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoStrumentoProgrammazione.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdTipoStrumentoProgrammazione() {
		return idTipoStrumentoProgrammazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String idTipoOperazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoOperazione(java.lang.String val) {
		idTipoOperazione = val;
	}

	//// {PROTECTED REGION ID(R-1985999974) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoOperazione. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdTipoOperazione() {
		return idTipoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String idDimensioneTerritoriale = null;

	/**
	 * @generated
	 */
	public void setIdDimensioneTerritoriale(java.lang.String val) {
		idDimensioneTerritoriale = val;
	}

	//// {PROTECTED REGION ID(R1094021393) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDimensioneTerritoriale.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdDimensioneTerritoriale() {
		return idDimensioneTerritoriale;
	}

	/**
	 * @generated
	 */
	private java.lang.String idProgettoComplesso = null;

	/**
	 * @generated
	 */
	public void setIdProgettoComplesso(java.lang.String val) {
		idProgettoComplesso = val;
	}

	//// {PROTECTED REGION ID(R1399481153) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoComplesso.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	/**
	 * @generated
	 */
	private java.lang.String idSettoreCipe = null;

	/**
	 * @generated
	 */
	public void setIdSettoreCipe(java.lang.String val) {
		idSettoreCipe = val;
	}

	//// {PROTECTED REGION ID(R-637088471) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreCipe. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdSettoreCipe() {
		return idSettoreCipe;
	}

	/**
	 * @generated
	 */
	private java.lang.String idSottoSettoreCipe = null;

	/**
	 * @generated
	 */
	public void setIdSottoSettoreCipe(java.lang.String val) {
		idSottoSettoreCipe = val;
	}

	//// {PROTECTED REGION ID(R-892941798) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSottoSettoreCipe. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}

	/**
	 * @generated
	 */
	private java.lang.String idCategoriaCipe = null;

	/**
	 * @generated
	 */
	public void setIdCategoriaCipe(java.lang.String val) {
		idCategoriaCipe = val;
	}

	//// {PROTECTED REGION ID(R847290380) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCategoriaCipe. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdCategoriaCipe() {
		return idCategoriaCipe;
	}

	/**
	 * @generated
	 */
	private java.lang.String idNaturaCipe = null;

	/**
	 * @generated
	 */
	public void setIdNaturaCipe(java.lang.String val) {
		idNaturaCipe = val;
	}

	//// {PROTECTED REGION ID(R835709312) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idNaturaCipe. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdNaturaCipe() {
		return idNaturaCipe;
	}

	/**
	 * @generated
	 */
	private java.lang.String idTipologiaCipe = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaCipe(java.lang.String val) {
		idTipologiaCipe = val;
	}

	//// {PROTECTED REGION ID(R494827777) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaCipe. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdTipologiaCipe() {
		return idTipologiaCipe;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagCardine = null;

	/**
	 * @generated
	 */
	public void setFlagCardine(java.lang.String val) {
		flagCardine = val;
	}

	//// {PROTECTED REGION ID(R-367432963) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagCardine. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagCardine() {
		return flagCardine;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagGeneratoreEntrate = null;

	/**
	 * @generated
	 */
	public void setFlagGeneratoreEntrate(java.lang.String val) {
		flagGeneratoreEntrate = val;
	}

	//// {PROTECTED REGION ID(R-1530912794) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagGeneratoreEntrate.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagLeggeObiettivo = null;

	/**
	 * @generated
	 */
	public void setFlagLeggeObiettivo(java.lang.String val) {
		flagLeggeObiettivo = val;
	}

	//// {PROTECTED REGION ID(R-1693000006) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagLeggeObiettivo. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagLeggeObiettivo() {
		return flagLeggeObiettivo;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAltroFondo = null;

	/**
	 * @generated
	 */
	public void setFlagAltroFondo(java.lang.String val) {
		flagAltroFondo = val;
	}

	//// {PROTECTED REGION ID(R-1766822211) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAltroFondo. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagAltroFondo() {
		return flagAltroFondo;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagStatoProgettoProgramma = null;

	/**
	 * @generated
	 */
	public void setFlagStatoProgettoProgramma(java.lang.String val) {
		flagStatoProgettoProgramma = val;
	}

	//// {PROTECTED REGION ID(R1309827724) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagStatoProgettoProgramma.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagStatoProgettoProgramma() {
		return flagStatoProgettoProgramma;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagDettaglioCup = null;

	/**
	 * @generated
	 */
	public void setFlagDettaglioCup(java.lang.String val) {
		flagDettaglioCup = val;
	}

	//// {PROTECTED REGION ID(R-139905114) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagDettaglioCup. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagDettaglioCup() {
		return flagDettaglioCup;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagBeneficiarioCup = null;

	/**
	 * @generated
	 */
	public void setFlagBeneficiarioCup(java.lang.String val) {
		flagBeneficiarioCup = val;
	}

	//// {PROTECTED REGION ID(R-597184815) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagBeneficiarioCup.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagBeneficiarioCup() {
		return flagBeneficiarioCup;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAggiungiCup = null;

	/**
	 * @generated
	 */
	public void setFlagAggiungiCup(java.lang.String val) {
		flagAggiungiCup = val;
	}

	//// {PROTECTED REGION ID(R-859980760) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAggiungiCup. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagAggiungiCup() {
		return flagAggiungiCup;
	}

	/**
	 * @generated
	 */
	private java.lang.String annoConcessioneOld = null;

	/**
	 * @generated
	 */
	public void setAnnoConcessioneOld(java.lang.String val) {
		annoConcessioneOld = val;
	}

	//// {PROTECTED REGION ID(R-1145571525) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoConcessioneOld. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getAnnoConcessioneOld() {
		return annoConcessioneOld;
	}

	/**
	 * @generated
	 */
	private java.lang.String dataPresentazioneDomanda = null;

	/**
	 * @generated
	 */
	public void setDataPresentazioneDomanda(java.lang.String val) {
		dataPresentazioneDomanda = val;
	}

	//// {PROTECTED REGION ID(R102905100) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataPresentazioneDomanda.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagProgettoDaInviareAlMonitoraggio = null;

	/**
	 * @generated
	 */
	public void setFlagProgettoDaInviareAlMonitoraggio(java.lang.String val) {
		flagProgettoDaInviareAlMonitoraggio = val;
	}

	//// {PROTECTED REGION ID(R-942216512) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo
	 * flagProgettoDaInviareAlMonitoraggio. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagProgettoDaInviareAlMonitoraggio() {
		return flagProgettoDaInviareAlMonitoraggio;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagRichiestaAutomaticaDelCup = null;

	/**
	 * @generated
	 */
	public void setFlagRichiestaAutomaticaDelCup(java.lang.String val) {
		flagRichiestaAutomaticaDelCup = val;
	}

	//// {PROTECTED REGION ID(R-549612386) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagRichiestaAutomaticaDelCup.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getFlagRichiestaAutomaticaDelCup() {
		return flagRichiestaAutomaticaDelCup;
	}

	/**
	 * @generated
	 */
	private java.lang.String idObiettivoTematico = null;

	/**
	 * @generated
	 */
	public void setIdObiettivoTematico(java.lang.String val) {
		idObiettivoTematico = val;
	}

	//// {PROTECTED REGION ID(R649919991) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idObiettivoTematico.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdObiettivoTematico() {
		return idObiettivoTematico;
	}

	/**
	 * @generated
	 */
	private java.lang.String idClassificazioneRA = null;

	/**
	 * @generated
	 */
	public void setIdClassificazioneRA(java.lang.String val) {
		idClassificazioneRA = val;
	}

	//// {PROTECTED REGION ID(R-197326958) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idClassificazioneRA.
	 * Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdClassificazioneRA() {
		return idClassificazioneRA;
	}

	/**
	 * @generated
	 */
	private java.lang.String idGrandeProgetto = null;

	/**
	 * @generated
	 */
	public void setIdGrandeProgetto(java.lang.String val) {
		idGrandeProgetto = val;
	}

	//// {PROTECTED REGION ID(R-1474439649) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idGrandeProgetto. Descrivere:
	 * <ul>
	 * <li>se l'attributo deve essere sempre valoriuzzato o meno
	 * <li>se ci sono dei vincoli sulla valorizzazione (es. range numerico,
	 * dimensioni massime in caso di stringa o tipo array, eventuale necessit� di
	 * corrispondenza con una particolare codifica, che pu� essere prefissata (es.
	 * da un elenco predefinito) oppure dinamica (presente su un archivio di
	 * un'applicazione)
	 * <li>se ci sono particolari vincoli di valorizzazione relativi al valore di
	 * altri attributi della stessa classe.
	 * <li>...
	 * </ul>
	 * 
	 * @generated
	 */
	//// {PROTECTED REGION END////}
	public java.lang.String getIdGrandeProgetto() {
		return idGrandeProgetto;
	}

	private java.lang.String flagPPP = null;
	private java.lang.String flagStrategico = null;
	private Date dtFirmaAccordo = null;
	private Date dtCompletamentoValutazione = null;

	public java.lang.String getFlagPPP() {
		return flagPPP;
	}

	public void setFlagPPP(java.lang.String flagPPP) {
		this.flagPPP = flagPPP;
	}

	public java.lang.String getFlagStrategico() {
		return flagStrategico;
	}

	public void setFlagStrategico(java.lang.String flagStrategico) {
		this.flagStrategico = flagStrategico;
	}

	public Date getDtFirmaAccordo() {
		return dtFirmaAccordo;
	}

	public void setDtFirmaAccordo(Date dtFirmaAccordo) {
		this.dtFirmaAccordo = dtFirmaAccordo;
	}

	public Date getDtCompletamentoValutazione() {
		return dtCompletamentoValutazione;
	}

	public void setDtCompletamentoValutazione(Date dtCompletamentoValutazione) {
		this.dtCompletamentoValutazione = dtCompletamentoValutazione;
	}

	/* PROTECTED REGION ID(R711708378) ENABLED START */
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/* PROTECTED REGION END */
}
