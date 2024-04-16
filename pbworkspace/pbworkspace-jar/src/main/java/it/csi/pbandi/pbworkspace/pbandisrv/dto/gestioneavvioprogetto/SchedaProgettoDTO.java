/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

////{PROTECTED REGION ID(R1577520109) ENABLED START////}
/**
 * Inserire qui la documentazione della classe SchedaProgettoDTO.
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
public class SchedaProgettoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R1370685356) ENABLED START////}
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
	private java.lang.String idBandoLinea = null;

	/**
	 * @generated
	 */
	public void setIdBandoLinea(java.lang.String val) {
		idBandoLinea = val;
	}

	////{PROTECTED REGION ID(R-2069485029) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBandoLinea. 
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
	public java.lang.String getIdBandoLinea() {
		return idBandoLinea;
	}

	/**
	 * @generated
	 */
	private java.lang.String idLineaNormativa = null;

	/**
	 * @generated
	 */
	public void setIdLineaNormativa(java.lang.String val) {
		idLineaNormativa = val;
	}

	////{PROTECTED REGION ID(R199634176) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaNormativa. 
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
	public java.lang.String getIdLineaNormativa() {
		return idLineaNormativa;
	}

	/**
	 * @generated
	 */
	private java.lang.String idLineaAsse = null;

	/**
	 * @generated
	 */
	public void setIdLineaAsse(java.lang.String val) {
		idLineaAsse = val;
	}

	////{PROTECTED REGION ID(R-1273943639) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaAsse. 
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
	public java.lang.String getIdLineaAsse() {
		return idLineaAsse;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagSalvaIntermediario = null;

	/**
	 * @generated
	 */
	public void setFlagSalvaIntermediario(java.lang.String val) {
		flagSalvaIntermediario = val;
	}

	////{PROTECTED REGION ID(R-1100212288) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagSalvaIntermediario. 
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
	public java.lang.String getFlagSalvaIntermediario() {
		return flagSalvaIntermediario;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.InformazioniBaseDTO informazioniBase = null;

	/**
	 * @generated
	 */
	public void setInformazioniBase(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.InformazioniBaseDTO val) {
		informazioniBase = val;
	}

	////{PROTECTED REGION ID(R1272262945) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo informazioniBase. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.InformazioniBaseDTO getInformazioniBase() {
		return informazioniBase;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO sedeInterventoDefault = null;

	/**
	 * @generated
	 */
	public void setSedeInterventoDefault(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO val) {
		sedeInterventoDefault = val;
	}

	////{PROTECTED REGION ID(R-422076029) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sedeInterventoDefault. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO getSedeInterventoDefault() {
		return sedeInterventoDefault;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO[] sediIntervento = null;

	/**
	 * @generated
	 */
	public void setSediIntervento(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO[] val) {
		sediIntervento = val;
	}

	////{PROTECTED REGION ID(R527155682) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sediIntervento. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SedeInterventoDTO[] getSediIntervento() {
		return sediIntervento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R-403622745) ENABLED START////}
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO rappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setRappresentanteLegale(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO val) {
		rappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R-1463061967) ENABLED START////}
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO getRappresentanteLegale() {
		return rappresentanteLegale;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO intermediario = null;

	/**
	 * @generated
	 */
	public void setIntermediario(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO val) {
		intermediario = val;
	}

	////{PROTECTED REGION ID(R1389155523) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo intermediario. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO getIntermediario() {
		return intermediario;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO altroSoggettoDefault = null;

	/**
	 * @generated
	 */
	public void setAltroSoggettoDefault(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO val) {
		altroSoggettoDefault = val;
	}

	////{PROTECTED REGION ID(R768790178) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo altroSoggettoDefault. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO getAltroSoggettoDefault() {
		return altroSoggettoDefault;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO[] altriSoggetti = null;

	/**
	 * @generated
	 */
	public void setAltriSoggetti(
			it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO[] val) {
		altriSoggetti = val;
	}

	////{PROTECTED REGION ID(R-1983372141) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo altriSoggetti. 
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
	public it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.SoggettoGenericoDTO[] getAltriSoggetti() {
		return altriSoggetti;
	}

	/*PROTECTED REGION ID(R547120212) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
