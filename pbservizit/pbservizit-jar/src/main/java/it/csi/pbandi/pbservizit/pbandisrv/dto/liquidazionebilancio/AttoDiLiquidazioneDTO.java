/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R-144312527) ENABLED START////}
/**
 * Inserire qui la documentazione della classe AttoDiLiquidazioneDTO.
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
public class AttoDiLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String annoAtto = null;

	/**
	 * @generated
	 */
	public void setAnnoAtto(java.lang.String val) {
		annoAtto = val;
	}

	////{PROTECTED REGION ID(R63455213) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoAtto. 
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
	public java.lang.String getAnnoAtto() {
		return annoAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double irpNonSoggette = null;

	/**
	 * @generated
	 */
	public void setIrpNonSoggette(java.lang.Double val) {
		irpNonSoggette = val;
	}

	////{PROTECTED REGION ID(R328448531) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo irpNonSoggette. 
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
	public java.lang.Double getIrpNonSoggette() {
		return irpNonSoggette;
	}

	/**
	 * @generated
	 */
	private java.util.Date inpsDal = null;

	/**
	 * @generated
	 */
	public void setInpsDal(java.util.Date val) {
		inpsDal = val;
	}

	////{PROTECTED REGION ID(R1977793590) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inpsDal. 
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
	public java.util.Date getInpsDal() {
		return inpsDal;
	}

	/**
	 * @generated
	 */
	private java.util.Date inpsAl = null;

	/**
	 * @generated
	 */
	public void setInpsAl(java.util.Date val) {
		inpsAl = val;
	}

	////{PROTECTED REGION ID(R340894372) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inpsAl. 
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
	public java.util.Date getInpsAl() {
		return inpsAl;
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

	////{PROTECTED REGION ID(R-1999770831) ENABLED START////}
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
	private java.lang.String flFatture = null;

	/**
	 * @generated
	 */
	public void setFlFatture(java.lang.String val) {
		flFatture = val;
	}

	////{PROTECTED REGION ID(R835004246) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flFatture. 
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
	public java.lang.String getFlFatture() {
		return flFatture;
	}

	/**
	 * @generated
	 */
	private java.lang.String flEstrCopiaProv = null;

	/**
	 * @generated
	 */
	public void setFlEstrCopiaProv(java.lang.String val) {
		flEstrCopiaProv = val;
	}

	////{PROTECTED REGION ID(R1733867106) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flEstrCopiaProv. 
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
	public java.lang.String getFlEstrCopiaProv() {
		return flEstrCopiaProv;
	}

	/**
	 * @generated
	 */
	private java.lang.String flDocGiustif = null;

	/**
	 * @generated
	 */
	public void setFlDocGiustif(java.lang.String val) {
		flDocGiustif = val;
	}

	////{PROTECTED REGION ID(R-1166829488) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flDocGiustif. 
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
	public java.lang.String getFlDocGiustif() {
		return flDocGiustif;
	}

	/**
	 * @generated
	 */
	private java.lang.String flDichiaraz = null;

	/**
	 * @generated
	 */
	public void setFlDichiaraz(java.lang.String val) {
		flDichiaraz = val;
	}

	////{PROTECTED REGION ID(R1394902194) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flDichiaraz. 
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
	public java.lang.String getFlDichiaraz() {
		return flDichiaraz;
	}

	/**
	 * @generated
	 */
	private java.lang.String allAltro = null;

	/**
	 * @generated
	 */
	public void setAllAltro(java.lang.String val) {
		allAltro = val;
	}

	////{PROTECTED REGION ID(R-1810011466) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo allAltro. 
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
	public java.lang.String getAllAltro() {
		return allAltro;
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

	////{PROTECTED REGION ID(R868395518) ENABLED START////}
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
	private java.lang.String nroTelLiq = null;

	/**
	 * @generated
	 */
	public void setNroTelLiq(java.lang.String val) {
		nroTelLiq = val;
	}

	////{PROTECTED REGION ID(R-1526166669) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nroTelLiq. 
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
	public java.lang.String getNroTelLiq() {
		return nroTelLiq;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeLiq = null;

	/**
	 * @generated
	 */
	public void setNomeLiq(java.lang.String val) {
		nomeLiq = val;
	}

	////{PROTECTED REGION ID(R2145794154) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeLiq. 
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
	public java.lang.String getNomeLiq() {
		return nomeLiq;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeDir = null;

	/**
	 * @generated
	 */
	public void setNomeDir(java.lang.String val) {
		nomeDir = val;
	}

	////{PROTECTED REGION ID(R2145786467) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeDir. 
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
	public java.lang.String getNomeDir() {
		return nomeDir;
	}

	/*PROTECTED REGION ID(R1326483408) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
