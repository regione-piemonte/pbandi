/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.impegnobilancio;

////{PROTECTED REGION ID(R317715589) ENABLED START////}
/**
 * Inserire qui la documentazione della classe BandolineaImpegnoDTO.
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
public class BandolineaImpegnoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R967711270) ENABLED START////}
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
	private java.lang.Long progrBandolineaIntervento = null;

	/**
	 * @generated
	 */
	public void setProgrBandolineaIntervento(java.lang.Long val) {
		progrBandolineaIntervento = val;
	}

	////{PROTECTED REGION ID(R-672849596) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrBandolineaIntervento. 
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
	public java.lang.Long getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeBandolinea = null;

	/**
	 * @generated
	 */
	public void setNomeBandolinea(java.lang.String val) {
		nomeBandolinea = val;
	}

	////{PROTECTED REGION ID(R1258843665) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeBandolinea. 
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
	public java.lang.String getNomeBandolinea() {
		return nomeBandolinea;
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

	////{PROTECTED REGION ID(R1073290006) ENABLED START////}
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
	private java.lang.Double impTotImpegniBandolinea = null;

	/**
	 * @generated
	 */
	public void setImpTotImpegniBandolinea(java.lang.Double val) {
		impTotImpegniBandolinea = val;
	}

	////{PROTECTED REGION ID(R-109748662) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impTotImpegniBandolinea. 
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
	public java.lang.Double getImpTotImpegniBandolinea() {
		return impTotImpegniBandolinea;
	}

	/**
	 * @generated
	 */
	private java.lang.Long numTotImpegniBandolinea = null;

	/**
	 * @generated
	 */
	public void setNumTotImpegniBandolinea(java.lang.Long val) {
		numTotImpegniBandolinea = val;
	}

	////{PROTECTED REGION ID(R2060472580) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numTotImpegniBandolinea. 
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
	public java.lang.Long getNumTotImpegniBandolinea() {
		return numTotImpegniBandolinea;
	}

	/**
	 * @generated
	 */
	private ImpegnoDTO[] impegni = null;

	/**
	 * @generated
	 */
	public void setImpegni(
			ImpegnoDTO[] val) {
		impegni = val;
	}

	////{PROTECTED REGION ID(R-1682299004) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impegni. 
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
	public ImpegnoDTO[] getImpegni() {
		return impegni;
	}

	/*PROTECTED REGION ID(R1779096124) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
