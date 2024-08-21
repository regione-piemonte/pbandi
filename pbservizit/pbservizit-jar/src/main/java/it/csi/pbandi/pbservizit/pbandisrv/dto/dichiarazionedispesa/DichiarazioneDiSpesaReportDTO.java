/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa;

////{PROTECTED REGION ID(R-641826512) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DichiarazioneDiSpesaReportDTO.
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
public class DichiarazioneDiSpesaReportDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EnteAppartenenzaDTO ente = null;

	/**
	 * @generated
	 */
	public void setEnte(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EnteAppartenenzaDTO val) {
		ente = val;
	}

	////{PROTECTED REGION ID(R-412484086) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.EnteAppartenenzaDTO getEnte() {
		return ente;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.ProgettoDTO progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.ProgettoDTO val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R1221681908) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.ProgettoDTO getProgetto() {
		return progetto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDal = null;

	/**
	 * @generated
	 */
	public void setDataDal(java.util.Date val) {
		dataDal = val;
	}

	////{PROTECTED REGION ID(R-1671705515) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDal. 
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
	public java.util.Date getDataDal() {
		return dataDal;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataAl = null;

	/**
	 * @generated
	 */
	public void setDataAl(java.util.Date val) {
		dataAl = val;
	}

	////{PROTECTED REGION ID(R-1300852059) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataAl. 
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
	public java.util.Date getDataAl() {
		return dataAl;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.BeneficiarioDTO beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.BeneficiarioDTO val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R-680796822) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.BeneficiarioDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO rappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setRappresentanteLegale(
			it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO val) {
		rappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R1463724020) ENABLED START////}
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO getRappresentanteLegale() {
		return rappresentanteLegale;
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

	////{PROTECTED REGION ID(R1351913211) ENABLED START////}
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
	private java.lang.String descTipoDichiarazione = null;

	/**
	 * @generated
	 */
	public void setDescTipoDichiarazione(java.lang.String val) {
		descTipoDichiarazione = val;
	}

	////{PROTECTED REGION ID(R-1817120015) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDichiarazione. 
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
	public java.lang.String getDescTipoDichiarazione() {
		return descTipoDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String indirizzo = null;

	/**
	 * @generated
	 */
	public void setIndirizzo(java.lang.String val) {
		indirizzo = val;
	}

	////{PROTECTED REGION ID(R-500257218) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo indirizzo. 
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
	public java.lang.String getIndirizzo() {
		return indirizzo;
	}

	/*PROTECTED REGION ID(R862306267) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
