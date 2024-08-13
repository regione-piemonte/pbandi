/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali;

////{PROTECTED REGION ID(R-125007866) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiGeneraliDTO.
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
public class DatiGeneraliDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String acronimo = null;

	/**
	 * @generated
	 */
	public void setAcronimo(java.lang.String val) {
		acronimo = val;
	}

	////{PROTECTED REGION ID(R458378498) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo acronimo. 
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
	public java.lang.String getAcronimo() {
		return acronimo;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceDichiarazione = null;

	/**
	 * @generated
	 */
	public void setCodiceDichiarazione(java.lang.String val) {
		codiceDichiarazione = val;
	}

	////{PROTECTED REGION ID(R261747309) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceDichiarazione. 
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
	public java.lang.String getCodiceDichiarazione() {
		return codiceDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceProgetto = null;

	/**
	 * @generated
	 */
	public void setCodiceProgetto(java.lang.String val) {
		codiceProgetto = val;
	}

	////{PROTECTED REGION ID(R1434040957) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceProgetto. 
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
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String civicoSedeLegale = null;

	/**
	 * @generated
	 */
	public void setCivicoSedeLegale(java.lang.String val) {
		civicoSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1094816086) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo civicoSedeLegale. 
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
	public java.lang.String getCivicoSedeLegale() {
		return civicoSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String codIstatComuneSedeLegale = null;

	/**
	 * @generated
	 */
	public void setCodIstatComuneSedeLegale(java.lang.String val) {
		codIstatComuneSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-940664075) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codIstatComuneSedeLegale. 
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
	public java.lang.String getCodIstatComuneSedeLegale() {
		return codIstatComuneSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String comuneSedeLegale = null;

	/**
	 * @generated
	 */
	public void setComuneSedeLegale(java.lang.String val) {
		comuneSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1695661264) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo comuneSedeLegale. 
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
	public java.lang.String getComuneSedeLegale() {
		return comuneSedeLegale;
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

	////{PROTECTED REGION ID(R-372591496) ENABLED START////}
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
	private java.lang.String descrizioneBando = null;

	/**
	 * @generated
	 */
	public void setDescrizioneBando(java.lang.String val) {
		descrizioneBando = val;
	}

	////{PROTECTED REGION ID(R-167509807) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneBando. 
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
	public java.lang.String getDescrizioneBando() {
		return descrizioneBando;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveLineaNormativa = null;

	/**
	 * @generated
	 */
	public void setDescBreveLineaNormativa(java.lang.String val) {
		descBreveLineaNormativa = val;
	}

	////{PROTECTED REGION ID(R43074725) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveLineaNormativa. 
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
	public java.lang.String getDescBreveLineaNormativa() {
		return descBreveLineaNormativa;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtConcessione = null;

	/**
	 * @generated
	 */
	public void setDtConcessione(java.util.Date val) {
		dtConcessione = val;
	}

	////{PROTECTED REGION ID(R2142778667) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtConcessione. 
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
	public java.util.Date getDtConcessione() {
		return dtConcessione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtDichiarazione = null;

	/**
	 * @generated
	 */
	public void setDtDichiarazione(java.util.Date val) {
		dtDichiarazione = val;
	}

	////{PROTECTED REGION ID(R1404880912) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtDichiarazione. 
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
	public java.util.Date getDtDichiarazione() {
		return dtDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtPresentazioneDomanda = null;

	/**
	 * @generated
	 */
	public void setDtPresentazioneDomanda(java.util.Date val) {
		dtPresentazioneDomanda = val;
	}

	////{PROTECTED REGION ID(R1598078737) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtPresentazioneDomanda. 
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
	public java.util.Date getDtPresentazioneDomanda() {
		return dtPresentazioneDomanda;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrizioneBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDescrizioneBeneficiario(java.lang.String val) {
		descrizioneBeneficiario = val;
	}

	////{PROTECTED REGION ID(R1855049827) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneBeneficiario. 
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
	public java.lang.String getDescrizioneBeneficiario() {
		return descrizioneBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagFirmaCartacea = null;

	/**
	 * @generated
	 */
	public void setFlagFirmaCartacea(java.lang.String val) {
		flagFirmaCartacea = val;
	}

	////{PROTECTED REGION ID(R-384398577) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagFirmaCartacea. 
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
	public java.lang.String getFlagFirmaCartacea() {
		return flagFirmaCartacea;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBandoLinea = null;

	/**
	 * @generated
	 */
	public void setIdBandoLinea(java.lang.Long val) {
		idBandoLinea = val;
	}

	////{PROTECTED REGION ID(R1100826804) ENABLED START////}
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
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
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

	////{PROTECTED REGION ID(R-148291227) ENABLED START////}
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
	private java.lang.Long idDimensioneImpresa = null;

	/**
	 * @generated
	 */
	public void setIdDimensioneImpresa(java.lang.Long val) {
		idDimensioneImpresa = val;
	}

	////{PROTECTED REGION ID(R-1771812051) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDimensioneImpresa. 
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
	public java.lang.Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoIndexDichiarazione = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoIndexDichiarazione(java.lang.Long val) {
		idDocumentoIndexDichiarazione = val;
	}

	////{PROTECTED REGION ID(R284345671) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoIndexDichiarazione. 
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
	public java.lang.Long getIdDocumentoIndexDichiarazione() {
		return idDocumentoIndexDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocIndexComunicFineProgetto = null;

	/**
	 * @generated
	 */
	public void setIdDocIndexComunicFineProgetto(java.lang.Long val) {
		idDocIndexComunicFineProgetto = val;
	}

	////{PROTECTED REGION ID(R135726327) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocIndexComunicFineProgetto. 
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
	public java.lang.Long getIdDocIndexComunicFineProgetto() {
		return idDocIndexComunicFineProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idFormaGiuridica = null;

	/**
	 * @generated
	 */
	public void setIdFormaGiuridica(java.lang.Long val) {
		idFormaGiuridica = val;
	}

	////{PROTECTED REGION ID(R1624521297) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idFormaGiuridica. 
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
	public java.lang.Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProcesso = null;

	/**
	 * @generated
	 */
	public void setIdProcesso(java.lang.Long val) {
		idProcesso = val;
	}

	////{PROTECTED REGION ID(R-1700098399) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProcesso. 
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
	public java.lang.Long getIdProcesso() {
		return idProcesso;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRegione = null;

	/**
	 * @generated
	 */
	public void setIdRegione(java.lang.Long val) {
		idRegione = val;
	}

	////{PROTECTED REGION ID(R-1430160112) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRegione. 
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
	public java.lang.Long getIdRegione() {
		return idRegione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAgevolato = null;

	/**
	 * @generated
	 */
	public void setImportoAgevolato(java.lang.Double val) {
		importoAgevolato = val;
	}

	////{PROTECTED REGION ID(R1973283940) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAgevolato. 
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
	public java.lang.Double getImportoAgevolato() {
		return importoAgevolato;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO[] importiAgevolati = null;

	/**
	 * @generated
	 */
	public void setImportiAgevolati(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO[] val) {
		importiAgevolati = val;
	}

	////{PROTECTED REGION ID(R-1142602332) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importiAgevolati. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO[] getImportiAgevolati() {
		return importiAgevolati;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCertificatoNettoUltimaPropAppr = null;

	/**
	 * @generated
	 */
	public void setImportoCertificatoNettoUltimaPropAppr(java.lang.Double val) {
		importoCertificatoNettoUltimaPropAppr = val;
	}

	////{PROTECTED REGION ID(R400557519) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCertificatoNettoUltimaPropAppr. 
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
	public java.lang.Double getImportoCertificatoNettoUltimaPropAppr() {
		return importoCertificatoNettoUltimaPropAppr;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] revoche = null;

	/**
	 * @generated
	 */
	public void setRevoche(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] val) {
		revoche = val;
	}

	////{PROTECTED REGION ID(R-492557778) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo revoche. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] getRevoche() {
		return revoche;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] recuperi = null;

	/**
	 * @generated
	 */
	public void setRecuperi(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] val) {
		recuperi = val;
	}

	////{PROTECTED REGION ID(R1372550231) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo recuperi. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] getRecuperi() {
		return recuperi;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] preRecuperi = null;

	/**
	 * @generated
	 */
	public void setPreRecuperi(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] val) {
		preRecuperi = val;
	}

	////{PROTECTED REGION ID(R1178955342) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo preRecuperi. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] getPreRecuperi() {
		return preRecuperi;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] erogazioni = null;

	/**
	 * @generated
	 */
	public void setErogazioni(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] val) {
		erogazioni = val;
	}

	////{PROTECTED REGION ID(R486851877) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo erogazioni. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.ImportoDescrizioneDTO[] getErogazioni() {
		return erogazioni;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRendicontato = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontato(java.lang.Double val) {
		importoRendicontato = val;
	}

	////{PROTECTED REGION ID(R-2085374834) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRendicontato. 
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
	public java.lang.Double getImportoRendicontato() {
		return importoRendicontato;
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

	////{PROTECTED REGION ID(R-1850400897) ENABLED START////}
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
	private java.lang.Double importoValidatoNettoRevoche = null;

	/**
	 * @generated
	 */
	public void setImportoValidatoNettoRevoche(java.lang.Double val) {
		importoValidatoNettoRevoche = val;
	}

	////{PROTECTED REGION ID(R-285175040) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoValidatoNettoRevoche. 
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
	public java.lang.Double getImportoValidatoNettoRevoche() {
		return importoValidatoNettoRevoche;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSoppressioni = null;

	/**
	 * @generated
	 */
	public void setImportoSoppressioni(java.lang.Double val) {
		importoSoppressioni = val;
	}

	////{PROTECTED REGION ID(R1340637652) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSoppressioni. 
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
	public java.lang.Double getImportoSoppressioni() {
		return importoSoppressioni;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCofinanziamentoPubblico = null;

	/**
	 * @generated
	 */
	public void setImportoCofinanziamentoPubblico(java.lang.Double val) {
		importoCofinanziamentoPubblico = val;
	}

	////{PROTECTED REGION ID(R-923026795) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCofinanziamentoPubblico. 
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
	public java.lang.Double getImportoCofinanziamentoPubblico() {
		return importoCofinanziamentoPubblico;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoCofinanziamentoPrivato = null;

	/**
	 * @generated
	 */
	public void setImportoCofinanziamentoPrivato(java.lang.Double val) {
		importoCofinanziamentoPrivato = val;
	}

	////{PROTECTED REGION ID(R-662801594) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoCofinanziamentoPrivato. 
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
	public java.lang.Double getImportoCofinanziamentoPrivato() {
		return importoCofinanziamentoPrivato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAmmesso = null;

	/**
	 * @generated
	 */
	public void setImportoAmmesso(java.lang.Double val) {
		importoAmmesso = val;
	}

	////{PROTECTED REGION ID(R247753095) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAmmesso. 
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
	public java.lang.Double getImportoAmmesso() {
		return importoAmmesso;
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

	////{PROTECTED REGION ID(R-1239393173) ENABLED START////}
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
	private boolean isCapofila = true;

	/**
	 * @generated
	 */
	public void setIsCapofila(boolean val) {
		isCapofila = val;
	}

	////{PROTECTED REGION ID(R-2091771483) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isCapofila. 
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
	public boolean getIsCapofila() {
		return isCapofila;
	}

	/**
	 * @generated
	 */
	private boolean isGestitoGefo = true;

	/**
	 * @generated
	 */
	public void setIsGestitoGefo(boolean val) {
		isGestitoGefo = val;
	}

	////{PROTECTED REGION ID(R-1986395524) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isGestitoGefo. 
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
	public boolean getIsGestitoGefo() {
		return isGestitoGefo;
	}

	/**
	 * @generated
	 */
	private java.lang.String pIvaSedeLegale = null;

	/**
	 * @generated
	 */
	public void setPIvaSedeLegale(java.lang.String val) {
		pIvaSedeLegale = val;
	}

	////{PROTECTED REGION ID(R924601321) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pIvaSedeLegale. 
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
	public java.lang.String getPIvaSedeLegale() {
		return pIvaSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String sedeIntervento = null;

	/**
	 * @generated
	 */
	public void setSedeIntervento(java.lang.String val) {
		sedeIntervento = val;
	}

	////{PROTECTED REGION ID(R662885175) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sedeIntervento. 
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
	public java.lang.String getSedeIntervento() {
		return sedeIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.String sedeLegale = null;

	/**
	 * @generated
	 */
	public void setSedeLegale(java.lang.String val) {
		sedeLegale = val;
	}

	////{PROTECTED REGION ID(R-334029435) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sedeLegale. 
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
	public java.lang.String getSedeLegale() {
		return sedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.String pIvaSedeIntervento = null;

	/**
	 * @generated
	 */
	public void setPIvaSedeIntervento(java.lang.String val) {
		pIvaSedeIntervento = val;
	}

	////{PROTECTED REGION ID(R-171823205) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pIvaSedeIntervento. 
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
	public java.lang.String getPIvaSedeIntervento() {
		return pIvaSedeIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.String protocollo = null;

	/**
	 * @generated
	 */
	public void setProtocollo(java.lang.String val) {
		protocollo = val;
	}

	////{PROTECTED REGION ID(R1652559521) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo protocollo. 
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
	public java.lang.String getProtocollo() {
		return protocollo;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean regolaDematAttiva = null;

	/**
	 * @generated
	 */
	public void setRegolaDematAttiva(java.lang.Boolean val) {
		regolaDematAttiva = val;
	}

	////{PROTECTED REGION ID(R801043004) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo regolaDematAttiva. 
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
	public java.lang.Boolean getRegolaDematAttiva() {
		return regolaDematAttiva;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoDichiarazione = null;

	/**
	 * @generated
	 */
	public void setStatoDichiarazione(java.lang.String val) {
		statoDichiarazione = val;
	}

	////{PROTECTED REGION ID(R2032132401) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoDichiarazione. 
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
	public java.lang.String getStatoDichiarazione() {
		return statoDichiarazione;
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

	////{PROTECTED REGION ID(R-71812191) ENABLED START////}
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
	private java.lang.String titoloProgetto = null;

	/**
	 * @generated
	 */
	public void setTitoloProgetto(java.lang.String val) {
		titoloProgetto = val;
	}

	////{PROTECTED REGION ID(R400149917) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo titoloProgetto. 
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
	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String viaSedeLegale = null;

	/**
	 * @generated
	 */
	public void setViaSedeLegale(java.lang.String val) {
		viaSedeLegale = val;
	}

	////{PROTECTED REGION ID(R-1724448409) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo viaSedeLegale. 
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
	public java.lang.String getViaSedeLegale() {
		return viaSedeLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComunicazFineProg = null;

	/**
	 * @generated
	 */
	public void setIdComunicazFineProg(java.lang.Long val) {
		idComunicazFineProg = val;
	}

	////{PROTECTED REGION ID(R-2089540768) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComunicazFineProg. 
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
	public java.lang.Long getIdComunicazFineProg() {
		return idComunicazFineProg;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoDichiarazionePiuGreen = null;

	/**
	 * @generated
	 */
	public void setStatoDichiarazionePiuGreen(java.lang.String val) {
		statoDichiarazionePiuGreen = val;
	}

	////{PROTECTED REGION ID(R1700352792) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoDichiarazionePiuGreen. 
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
	public java.lang.String getStatoDichiarazionePiuGreen() {
		return statoDichiarazionePiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagFirmaCartaceaPiuGreen = null;

	/**
	 * @generated
	 */
	public void setFlagFirmaCartaceaPiuGreen(java.lang.String val) {
		flagFirmaCartaceaPiuGreen = val;
	}

	////{PROTECTED REGION ID(R1072643830) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagFirmaCartaceaPiuGreen. 
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
	public java.lang.String getFlagFirmaCartaceaPiuGreen() {
		return flagFirmaCartaceaPiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocIndexComunicFineProgettoPiuGreen = null;

	/**
	 * @generated
	 */
	public void setIdDocIndexComunicFineProgettoPiuGreen(java.lang.Long val) {
		idDocIndexComunicFineProgettoPiuGreen = val;
	}

	////{PROTECTED REGION ID(R359354590) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocIndexComunicFineProgettoPiuGreen. 
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
	public java.lang.Long getIdDocIndexComunicFineProgettoPiuGreen() {
		return idDocIndexComunicFineProgettoPiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.String protocolloPiuGreen = null;

	/**
	 * @generated
	 */
	public void setProtocolloPiuGreen(java.lang.String val) {
		protocolloPiuGreen = val;
	}

	////{PROTECTED REGION ID(R-735244152) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo protocolloPiuGreen. 
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
	public java.lang.String getProtocolloPiuGreen() {
		return protocolloPiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idComunicazFineProgPiuGreen = null;

	/**
	 * @generated
	 */
	public void setIdComunicazFineProgPiuGreen(java.lang.Long val) {
		idComunicazFineProgPiuGreen = val;
	}

	////{PROTECTED REGION ID(R-1193544121) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idComunicazFineProgPiuGreen. 
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
	public java.lang.Long getIdComunicazFineProgPiuGreen() {
		return idComunicazFineProgPiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.String dtComunicazione = null;

	/**
	 * @generated
	 */
	public void setDtComunicazione(java.lang.String val) {
		dtComunicazione = val;
	}

	////{PROTECTED REGION ID(R-688949996) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtComunicazione. 
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
	public java.lang.String getDtComunicazione() {
		return dtComunicazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String dtComunicazionePiuGreen = null;

	/**
	 * @generated
	 */
	public void setDtComunicazionePiuGreen(java.lang.String val) {
		dtComunicazionePiuGreen = val;
	}

	////{PROTECTED REGION ID(R1217680379) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtComunicazionePiuGreen. 
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
	public java.lang.String getDtComunicazionePiuGreen() {
		return dtComunicazionePiuGreen;
	}

	/**
	 * @generated
	 */
	private java.lang.Long flagPubblicoPrivato = null;

	/**
	 * @generated
	 */
	public void setFlagPubblicoPrivato(java.lang.Long val) {
		flagPubblicoPrivato = val;
	}

	////{PROTECTED REGION ID(R543378861) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagPubblicoPrivato. 
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
	public java.lang.Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	/**
	 * @generated
	 */
	private java.lang.String codUniIpa = null;

	/**
	 * @generated
	 */
	public void setCodUniIpa(java.lang.String val) {
		codUniIpa = val;
	}

	////{PROTECTED REGION ID(R1159235420) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codUniIpa. 
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
	public java.lang.String getCodUniIpa() {
		return codUniIpa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idEnteGiuridico = null;

	/**
	 * @generated
	 */
	public void setIdEnteGiuridico(java.lang.Long val) {
		idEnteGiuridico = val;
	}

	////{PROTECTED REGION ID(R1529732640) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idEnteGiuridico. 
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
	public java.lang.Long getIdEnteGiuridico() {
		return idEnteGiuridico;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.SedeDTO[] sedi = null;

	/**
	 * @generated
	 */
	public void setSedi(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.SedeDTO[] val) {
		sedi = val;
	}

	////{PROTECTED REGION ID(R1335026525) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo sedi. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.SedeDTO[] getSedi() {
		return sedi;
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

	////{PROTECTED REGION ID(R-1801828529) ENABLED START////}
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
	private java.lang.Long idTipoOperazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoOperazione(java.lang.Long val) {
		idTipoOperazione = val;
	}

	////{PROTECTED REGION ID(R1976380729) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.EconomiaPerDatiGeneraliDTO[] economie = null;

	/**
	 * @generated
	 */
	public void setEconomie(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.EconomiaPerDatiGeneraliDTO[] val) {
		economie = val;
	}

	////{PROTECTED REGION ID(R-1247092227) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo economie. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.EconomiaPerDatiGeneraliDTO[] getEconomie() {
		return economie;
	}

	/**
	 * @generated
	 */
	private boolean isBandoCultura = true;

	/**
	 * @generated
	 */
	public void setIsBandoCultura(boolean val) {
		isBandoCultura = val;
	}

	////{PROTECTED REGION ID(R-2123867328) ENABLED START////}
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
	public boolean getIsBandoCultura() {
		return isBandoCultura;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoAmmessoVociDiEntrata = null;

	/**
	 * @generated
	 */
	public void setImportoAmmessoVociDiEntrata(java.lang.Double val) {
		importoAmmessoVociDiEntrata = val;
	}

	////{PROTECTED REGION ID(R1642256284) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoAmmessoVociDiEntrata. 
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
	public java.lang.Double getImportoAmmessoVociDiEntrata() {
		return importoAmmessoVociDiEntrata;
	}

	/*PROTECTED REGION ID(R-2020388347) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
