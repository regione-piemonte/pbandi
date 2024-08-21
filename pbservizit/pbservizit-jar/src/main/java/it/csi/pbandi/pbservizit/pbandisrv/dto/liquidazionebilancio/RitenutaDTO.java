/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R-264891590) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RitenutaDTO.
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
public class RitenutaDTO implements java.io.Serializable {
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

	////{PROTECTED REGION ID(R-1820595627) ENABLED START////}
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
	private java.lang.Double sommeNonImponibili = null;

	/**
	 * @generated
	 */
	public void setSommeNonImponibili(java.lang.Double val) {
		sommeNonImponibili = val;
	}

	////{PROTECTED REGION ID(R1746387744) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sommeNonImponibili. 
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
	public java.lang.Double getSommeNonImponibili() {
		return sommeNonImponibili;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoSoggettoRitenuta = null;

	/**
	 * @generated
	 */
	public void setIdTipoSoggettoRitenuta(java.lang.Long val) {
		idTipoSoggettoRitenuta = val;
	}

	////{PROTECTED REGION ID(R1588780523) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoSoggettoRitenuta. 
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
	public java.lang.Long getIdTipoSoggettoRitenuta() {
		return idTipoSoggettoRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoRitenuta = null;

	/**
	 * @generated
	 */
	public void setIdTipoRitenuta(java.lang.Long val) {
		idTipoRitenuta = val;
	}

	////{PROTECTED REGION ID(R1402877733) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoRitenuta. 
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
	public java.lang.Long getIdTipoRitenuta() {
		return idTipoRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAliquotaRitenuta = null;

	/**
	 * @generated
	 */
	public void setIdAliquotaRitenuta(java.lang.Long val) {
		idAliquotaRitenuta = val;
	}

	////{PROTECTED REGION ID(R-1073941749) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAliquotaRitenuta. 
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
	public java.lang.Long getIdAliquotaRitenuta() {
		return idAliquotaRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSituazioneInps = null;

	/**
	 * @generated
	 */
	public void setIdSituazioneInps(java.lang.Long val) {
		idSituazioneInps = val;
	}

	////{PROTECTED REGION ID(R1634180074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSituazioneInps. 
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
	public java.lang.Long getIdSituazioneInps() {
		return idSituazioneInps;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRitenutaPrevidenziale = null;

	/**
	 * @generated
	 */
	public void setIdRitenutaPrevidenziale(java.lang.Long val) {
		idRitenutaPrevidenziale = val;
	}

	////{PROTECTED REGION ID(R-1922082845) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRitenutaPrevidenziale. 
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
	public java.lang.Long getIdRitenutaPrevidenziale() {
		return idRitenutaPrevidenziale;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAltraCassaPrevidenziale = null;

	/**
	 * @generated
	 */
	public void setIdAltraCassaPrevidenziale(java.lang.Long val) {
		idAltraCassaPrevidenziale = val;
	}

	////{PROTECTED REGION ID(R-895990604) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAltraCassaPrevidenziale. 
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
	public java.lang.Long getIdAltraCassaPrevidenziale() {
		return idAltraCassaPrevidenziale;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAttivitaInps = null;

	/**
	 * @generated
	 */
	public void setIdAttivitaInps(java.lang.Long val) {
		idAttivitaInps = val;
	}

	////{PROTECTED REGION ID(R439275205) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttivitaInps. 
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
	public java.lang.Long getIdAttivitaInps() {
		return idAttivitaInps;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInpsDal = null;

	/**
	 * @generated
	 */
	public void setDtInpsDal(java.util.Date val) {
		dtInpsDal = val;
	}

	////{PROTECTED REGION ID(R-1928186915) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInpsDal. 
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
	public java.util.Date getDtInpsDal() {
		return dtInpsDal;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInpsAl = null;

	/**
	 * @generated
	 */
	public void setDtInpsAl(java.util.Date val) {
		dtInpsAl = val;
	}

	////{PROTECTED REGION ID(R353442333) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInpsAl. 
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
	public java.util.Date getDtInpsAl() {
		return dtInpsAl;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRischioInail = null;

	/**
	 * @generated
	 */
	public void setIdRischioInail(java.lang.Long val) {
		idRischioInail = val;
	}

	////{PROTECTED REGION ID(R287886669) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRischioInail. 
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
	public java.lang.Long getIdRischioInail() {
		return idRischioInail;
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

	////{PROTECTED REGION ID(R-787502376) ENABLED START////}
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RitenutaDTO [idAttoLiquidazione=");
		builder.append(idAttoLiquidazione);
		builder.append(", sommeNonImponibili=");
		builder.append(sommeNonImponibili);
		builder.append(", idTipoSoggettoRitenuta=");
		builder.append(idTipoSoggettoRitenuta);
		builder.append(", idTipoRitenuta=");
		builder.append(idTipoRitenuta);
		builder.append(", idAliquotaRitenuta=");
		builder.append(idAliquotaRitenuta);
		builder.append(", idSituazioneInps=");
		builder.append(idSituazioneInps);
		builder.append(", idRitenutaPrevidenziale=");
		builder.append(idRitenutaPrevidenziale);
		builder.append(", idAltraCassaPrevidenziale=");
		builder.append(idAltraCassaPrevidenziale);
		builder.append(", idAttivitaInps=");
		builder.append(idAttivitaInps);
		builder.append(", dtInpsDal=");
		builder.append(dtInpsDal);
		builder.append(", dtInpsAl=");
		builder.append(dtInpsAl);
		builder.append(", idRischioInail=");
		builder.append(idRischioInail);
		builder.append(", imponibile=");
		builder.append(imponibile);
		builder.append("]");
		return builder.toString();
	}
	////{PROTECTED REGION END////}
	public java.lang.Double getImponibile() {
		return imponibile;
	}

	/*PROTECTED REGION ID(R326132455) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
