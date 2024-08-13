/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R732141035) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DichiarazioneDiRinunciaReportDTO.
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
public class DichiarazioneDiRinunciaReportDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.BeneficiarioDTO beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(
			it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.BeneficiarioDTO val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R-267865691) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.BeneficiarioDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataRinuncia = null;

	/**
	 * @generated
	 */
	public void setDataRinuncia(java.util.Date val) {
		dataRinuncia = val;
	}

	////{PROTECTED REGION ID(R173847552) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataRinuncia. 
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
	public java.util.Date getDataRinuncia() {
		return dataRinuncia;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.EnteAppartenenzaDTO ente = null;

	/**
	 * @generated
	 */
	public void setEnte(
			it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.EnteAppartenenzaDTO val) {
		ente = val;
	}

	////{PROTECTED REGION ID(R-487572155) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ente. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.EnteAppartenenzaDTO getEnte() {
		return ente;
	}

	/**
	 * @generated
	 */
	private java.lang.Long giorniRinuncia = null;

	/**
	 * @generated
	 */
	public void setGiorniRinuncia(java.lang.Long val) {
		giorniRinuncia = val;
	}

	////{PROTECTED REGION ID(R-1643799978) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo giorniRinuncia. 
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
	public java.lang.Long getGiorniRinuncia() {
		return giorniRinuncia;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichiarazione = null;

	/**
	 * @generated
	 */
	public void setIdDichiarazione(java.lang.Long val) {
		idDichiarazione = val;
	}

	////{PROTECTED REGION ID(R-2098066208) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichiarazione. 
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
	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDaRestituire = null;

	/**
	 * @generated
	 */
	public void setImportoDaRestituire(java.lang.Double val) {
		importoDaRestituire = val;
	}

	////{PROTECTED REGION ID(R-1006354590) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDaRestituire. 
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
	public java.lang.Double getImportoDaRestituire() {
		return importoDaRestituire;
	}

	/**
	 * @generated
	 */
	private java.lang.String motivoRinuncia = null;

	/**
	 * @generated
	 */
	public void setMotivoRinuncia(java.lang.String val) {
		motivoRinuncia = val;
	}

	////{PROTECTED REGION ID(R-2036428762) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo motivoRinuncia. 
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
	public java.lang.String getMotivoRinuncia() {
		return motivoRinuncia;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.ProgettoDTO progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(
			it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.ProgettoDTO val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R-1939895121) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.ProgettoDTO getProgetto() {
		return progetto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.RappresentanteLegaleDTO rappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setRappresentanteLegale(
			it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.RappresentanteLegaleDTO val) {
		rappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R-343076561) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rappresentanteLegale. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.erogazione.RappresentanteLegaleDTO getRappresentanteLegale() {
		return rappresentanteLegale;
	}

	/*PROTECTED REGION ID(R-577246506) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
