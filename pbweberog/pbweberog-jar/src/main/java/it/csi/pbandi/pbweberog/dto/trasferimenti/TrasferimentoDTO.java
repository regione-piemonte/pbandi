/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

import it.csi.pbandi.pbweberog.pbandisrv.integration.util.DataFilter;

////{PROTECTED REGION ID(R786119422) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TrasferimentoDTO.
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
public class TrasferimentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idTrasferimento = null;

	/**
	 * @generated
	 */
	public void setIdTrasferimento(java.lang.Long val) {
		idTrasferimento = val;
	}

	////{PROTECTED REGION ID(R-14543854) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTrasferimento. 
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
	public java.lang.Long getIdTrasferimento() {
		return idTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceTrasferimento = null;

	/**
	 * @generated
	 */
	public void setCodiceTrasferimento(java.lang.String val) {
		codiceTrasferimento = val;
	}

	////{PROTECTED REGION ID(R-925597414) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceTrasferimento. 
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
	public java.lang.String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtTrasferimento = null;

	/**
	 * @generated
	 */
	public void setDtTrasferimento(java.util.Date val) {
		dtTrasferimento = val;
	}

	////{PROTECTED REGION ID(R1538628285) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtTrasferimento. 
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
	public java.util.Date getDtTrasferimento() {
		return dtTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCausaleTrasferimento = null;

	/**
	 * @generated
	 */
	public void setIdCausaleTrasferimento(java.lang.Long val) {
		idCausaleTrasferimento = val;
	}

	////{PROTECTED REGION ID(R119273062) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCausaleTrasferimento. 
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
	public java.lang.Long getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSoggettoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoBeneficiario(java.lang.Long val) {
		idSoggettoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-76595399) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoBeneficiario. 
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
	public java.lang.Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTrasferimento = null;

	/**
	 * @generated
	 */
	public void setImportoTrasferimento(java.lang.Double val) {
		importoTrasferimento = val;
	}

	////{PROTECTED REGION ID(R795672287) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTrasferimento. 
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
	public java.lang.Double getImportoTrasferimento() {
		return importoTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagPubblicoPrivato = null;

	/**
	 * @generated
	 */
	public void setFlagPubblicoPrivato(java.lang.String val) {
		flagPubblicoPrivato = val;
	}

	////{PROTECTED REGION ID(R1730873269) ENABLED START////}
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
	public java.lang.String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	/**
	 * @generated
	 */
	private java.lang.String descCausaleTrasferimento = null;

	/**
	 * @generated
	 */
	public void setDescCausaleTrasferimento(java.lang.String val) {
		descCausaleTrasferimento = val;
	}

	////{PROTECTED REGION ID(R1989765468) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCausaleTrasferimento. 
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
	public java.lang.String getDescCausaleTrasferimento() {
		return descCausaleTrasferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String cfBeneficiario = null;

	/**
	 * @generated
	 */
	public void setCfBeneficiario(java.lang.String val) {
		cfBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-939807045) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cfBeneficiario. 
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
	public java.lang.String getCfBeneficiario() {
		return cfBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String denominazioneBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDenominazioneBeneficiario(java.lang.String val) {
		denominazioneBeneficiario = val;
	}

	////{PROTECTED REGION ID(R1179247682) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo denominazioneBeneficiario. 
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
	public java.lang.String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	private java.lang.Long idLineaDiIntervento = null;

	public void setIdLineaDiIntervento(java.lang.Long val) {
		idLineaDiIntervento = val;
	}

	public java.lang.Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
		
		
	/*PROTECTED REGION ID(R-282984179) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals

	public String toString() {

		String temp = "";
		StringBuffer sb = new StringBuffer();
		sb.append("\t\n" + this.getClass().getName() + "\t\n");

		temp = DataFilter.removeNull(idTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idTrasferimento: " + temp + "\t\n");

		temp = DataFilter.removeNull(codiceTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" codTrasferimento: " + temp + "\t\n");

		temp = DataFilter.removeNull(idCausaleTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idCausaleTrasferimento: " + temp + "\t\n");

		temp = DataFilter.removeNull(importoTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" importoTrasferimento: " + temp + "\t\n");

		temp = DataFilter.removeNull(dtTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" dtTrasferimento: " + temp + "\t\n");

		temp = DataFilter.removeNull(flagPubblicoPrivato);
		if (!DataFilter.isEmpty(temp))
			sb.append(" flagPubblicoPrivato: " + temp + "\t\n");

		temp = DataFilter.removeNull(idCausaleTrasferimento);
		if (!DataFilter.isEmpty(temp))
			sb.append(" idCausaleTrasferimento: " + temp + "\t\n");

		return sb.toString();
	}
	/*PROTECTED REGION END*/
}
