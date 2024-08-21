/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R-678790606) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiIntegrativiDTO.
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
public class DatiIntegrativiDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.util.Date dtScadenzaAtto = null;

	/**
	 * @generated
	 */
	public void setDtScadenzaAtto(java.util.Date val) {
		dtScadenzaAtto = val;
	}

	////{PROTECTED REGION ID(R1670120051) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtScadenzaAtto. 
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
	public java.util.Date getDtScadenzaAtto() {
		return dtScadenzaAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descAtto = null;

	/**
	 * @generated
	 */
	public void setDescAtto(java.lang.String val) {
		descAtto = val;
	}

	////{PROTECTED REGION ID(R2087240785) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descAtto. 
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
	public java.lang.String getDescAtto() {
		return descAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAllegatiFatture = null;

	/**
	 * @generated
	 */
	public void setFlagAllegatiFatture(java.lang.String val) {
		flagAllegatiFatture = val;
	}

	////{PROTECTED REGION ID(R258199900) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAllegatiFatture. 
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
	public java.lang.String getFlagAllegatiFatture() {
		return flagAllegatiFatture;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAllegatiEstrattoProv = null;

	/**
	 * @generated
	 */
	public void setFlagAllegatiEstrattoProv(java.lang.String val) {
		flagAllegatiEstrattoProv = val;
	}

	////{PROTECTED REGION ID(R205432116) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAllegatiEstrattoProv. 
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
	public java.lang.String getFlagAllegatiEstrattoProv() {
		return flagAllegatiEstrattoProv;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAllegatiDocGiustificat = null;

	/**
	 * @generated
	 */
	public void setFlagAllegatiDocGiustificat(java.lang.String val) {
		flagAllegatiDocGiustificat = val;
	}

	////{PROTECTED REGION ID(R-684372393) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAllegatiDocGiustificat. 
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
	public java.lang.String getFlagAllegatiDocGiustificat() {
		return flagAllegatiDocGiustificat;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagAllegatiDichiarazione = null;

	/**
	 * @generated
	 */
	public void setFlagAllegatiDichiarazione(java.lang.String val) {
		flagAllegatiDichiarazione = val;
	}

	////{PROTECTED REGION ID(R1058853525) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagAllegatiDichiarazione. 
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
	public java.lang.String getFlagAllegatiDichiarazione() {
		return flagAllegatiDichiarazione;
	}

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

	////{PROTECTED REGION ID(R-305036467) ENABLED START////}
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
	private java.lang.Long idSettoreEnte = null;

	/**
	 * @generated
	 */
	public void setIdSettoreEnte(java.lang.Long val) {
		idSettoreEnte = val;
	}

	////{PROTECTED REGION ID(R2104891709) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreEnte. 
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
	public java.lang.Long getIdSettoreEnte() {
		return idSettoreEnte;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteAtto = null;

	/**
	 * @generated
	 */
	public void setNoteAtto(java.lang.String val) {
		noteAtto = val;
	}

	////{PROTECTED REGION ID(R-1643913934) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteAtto. 
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
	public java.lang.String getNoteAtto() {
		return noteAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeDirigenteLiquidatore = null;

	/**
	 * @generated
	 */
	public void setNomeDirigenteLiquidatore(java.lang.String val) {
		nomeDirigenteLiquidatore = val;
	}

	////{PROTECTED REGION ID(R2003415033) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeDirigenteLiquidatore. 
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
	public java.lang.String getNomeDirigenteLiquidatore() {
		return nomeDirigenteLiquidatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeLiquidatore = null;

	/**
	 * @generated
	 */
	public void setNomeLiquidatore(java.lang.String val) {
		nomeLiquidatore = val;
	}

	////{PROTECTED REGION ID(R1283778760) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeLiquidatore. 
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
	public java.lang.String getNomeLiquidatore() {
		return nomeLiquidatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroTelefonoLiquidatore = null;

	/**
	 * @generated
	 */
	public void setNumeroTelefonoLiquidatore(java.lang.String val) {
		numeroTelefonoLiquidatore = val;
	}

	////{PROTECTED REGION ID(R-943685935) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroTelefonoLiquidatore. 
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
	public java.lang.String getNumeroTelefonoLiquidatore() {
		return numeroTelefonoLiquidatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String testoAllegatiAltro = null;

	/**
	 * @generated
	 */
	public void setTestoAllegatiAltro(java.lang.String val) {
		testoAllegatiAltro = val;
	}

	////{PROTECTED REGION ID(R-1175454136) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo testoAllegatiAltro. 
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
		builder.append("DatiIntegrativiDTO [dtScadenzaAtto=");
		builder.append(dtScadenzaAtto);
		builder.append(", descAtto=");
		builder.append(descAtto);
		builder.append(", flagAllegatiFatture=");
		builder.append(flagAllegatiFatture);
		builder.append(", flagAllegatiEstrattoProv=");
		builder.append(flagAllegatiEstrattoProv);
		builder.append(", flagAllegatiDocGiustificat=");
		builder.append(flagAllegatiDocGiustificat);
		builder.append(", flagAllegatiDichiarazione=");
		builder.append(flagAllegatiDichiarazione);
		builder.append(", idAttoLiquidazione=");
		builder.append(idAttoLiquidazione);
		builder.append(", idSettoreEnte=");
		builder.append(idSettoreEnte);
		builder.append(", noteAtto=");
		builder.append(noteAtto);
		builder.append(", nomeDirigenteLiquidatore=");
		builder.append(nomeDirigenteLiquidatore);
		builder.append(", nomeLiquidatore=");
		builder.append(nomeLiquidatore);
		builder.append(", numeroTelefonoLiquidatore=");
		builder.append(numeroTelefonoLiquidatore);
		builder.append(", testoAllegatiAltro=");
		builder.append(testoAllegatiAltro);
		builder.append("]");
		return builder.toString();
	}

	////{PROTECTED REGION END////}
	public java.lang.String getTestoAllegatiAltro() {
		return testoAllegatiAltro;
	}

	/*PROTECTED REGION ID(R1302594329) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
