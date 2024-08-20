/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R1885858452) ENABLED START////}
/**
 * Inserire qui la documentazione della classe QuadroPrevisionaleDTO.
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
public class QuadroPrevisionaleDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String note = null;

	/**
	 * @generated
	 */
	public void setNote(java.lang.String val) {
		note = val;
	}

	////{PROTECTED REGION ID(R-826527610) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo note. 
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
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idQuadro = null;

	/**
	 * @generated
	 */
	public void setIdQuadro(java.lang.Long val) {
		idQuadro = val;
	}

	////{PROTECTED REGION ID(R881822035) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idQuadro. 
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
	public java.lang.Long getIdQuadro() {
		return idQuadro;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idRigoQuadro = null;

	/**
	 * @generated
	 */
	public void setIdRigoQuadro(java.lang.Long val) {
		idRigoQuadro = val;
	}

	////{PROTECTED REGION ID(R1655094290) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idRigoQuadro. 
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
	public java.lang.Long getIdRigoQuadro() {
		return idRigoQuadro;
	}

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

	////{PROTECTED REGION ID(R1615264147) ENABLED START////}
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
	private java.lang.Double importoPreventivo = null;

	/**
	 * @generated
	 */
	public void setImportoPreventivo(java.lang.Double val) {
		importoPreventivo = val;
	}

	////{PROTECTED REGION ID(R-1754102528) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoPreventivo. 
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
	public java.lang.Double getImportoPreventivo() {
		return importoPreventivo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRealizzato = null;

	/**
	 * @generated
	 */
	public void setImportoRealizzato(java.lang.Double val) {
		importoRealizzato = val;
	}

	////{PROTECTED REGION ID(R1334302887) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRealizzato. 
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
	public java.lang.Double getImportoRealizzato() {
		return importoRealizzato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSpesaAmmessa = null;

	/**
	 * @generated
	 */
	public void setImportoSpesaAmmessa(java.lang.Double val) {
		importoSpesaAmmessa = val;
	}

	////{PROTECTED REGION ID(R-1113245347) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSpesaAmmessa. 
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
	public java.lang.Double getImportoSpesaAmmessa() {
		return importoSpesaAmmessa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVoce = null;

	/**
	 * @generated
	 */
	public void setIdVoce(java.lang.Long val) {
		idVoce = val;
	}

	////{PROTECTED REGION ID(R121731114) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVoce. 
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
	public java.lang.Long getIdVoce() {
		return idVoce;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idVocePadre = null;

	/**
	 * @generated
	 */
	public void setIdVocePadre(java.lang.Long val) {
		idVocePadre = val;
	}

	////{PROTECTED REGION ID(R1797916668) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idVocePadre. 
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
	public java.lang.Long getIdVocePadre() {
		return idVocePadre;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO[] figli = null;

	/**
	 * @generated
	 */
	public void setFigli(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO[] val) {
		figli = val;
	}

	////{PROTECTED REGION ID(R139868781) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo figli. 
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
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.contoeconomico.QuadroPrevisionaleDTO[] getFigli() {
		return figli;
	}

	/**
	 * @generated
	 */
	private java.lang.String periodo = null;

	/**
	 * @generated
	 */
	public void setPeriodo(java.lang.String val) {
		periodo = val;
	}

	////{PROTECTED REGION ID(R1450576314) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo periodo. 
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
	public java.lang.String getPeriodo() {
		return periodo;
	}

	/**
	 * @generated
	 */
	private java.lang.String descVoce = null;

	/**
	 * @generated
	 */
	public void setDescVoce(java.lang.String val) {
		descVoce = val;
	}

	////{PROTECTED REGION ID(R-1693308512) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descVoce. 
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
	public java.lang.String getDescVoce() {
		return descVoce;
	}

	/**
	 * @generated
	 */
	private boolean macroVoce = true;

	/**
	 * @generated
	 */
	public void setMacroVoce(boolean val) {
		macroVoce = val;
	}

	////{PROTECTED REGION ID(R715151027) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo macroVoce. 
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
	public boolean getMacroVoce() {
		return macroVoce;
	}

	/**
	 * @generated
	 */
	private boolean haFigli = true;

	/**
	 * @generated
	 */
	public void setHaFigli(boolean val) {
		haFigli = val;
	}

	////{PROTECTED REGION ID(R-1509644812) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo haFigli. 
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
	public boolean getHaFigli() {
		return haFigli;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDaRealizzare = null;

	/**
	 * @generated
	 */
	public void setImportoDaRealizzare(java.lang.Double val) {
		importoDaRealizzare = val;
	}

	////{PROTECTED REGION ID(R809345852) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDaRealizzare. 
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
	public java.lang.Double getImportoDaRealizzare() {
		return importoDaRealizzare;
	}

	/**
	 * @generated
	 */
	private boolean isPeriodo = true;

	/**
	 * @generated
	 */
	public void setIsPeriodo(boolean val) {
		isPeriodo = val;
	}

	////{PROTECTED REGION ID(R609306160) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isPeriodo. 
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
	public boolean getIsPeriodo() {
		return isPeriodo;
	}

	/*PROTECTED REGION ID(R-734863881) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
