package it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma;

////{PROTECTED REGION ID(R-1303666098) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoFindFasiMonitoraggio.
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
public class EsitoFindFasiMonitoraggio implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private boolean successo = true;

	/**
	 * @generated
	 */
	public void setSuccesso(boolean val) {
		successo = val;
	}

	////{PROTECTED REGION ID(R945173786) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo successo. 
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
	public boolean getSuccesso() {
		return successo;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO[] fasiMonitoraggio = null;

	/**
	 * @generated
	 */
	public void setFasiMonitoraggio(
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO[] val) {
		fasiMonitoraggio = val;
	}

	////{PROTECTED REGION ID(R1984845324) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fasiMonitoraggio. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionecronoprogramma.FaseMonitoraggioDTO[] getFasiMonitoraggio() {
		return fasiMonitoraggio;
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

	////{PROTECTED REGION ID(R-516681757) ENABLED START////}
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
	private java.lang.String descTipoOperazione = null;

	/**
	 * @generated
	 */
	public void setDescTipoOperazione(java.lang.String val) {
		descTipoOperazione = val;
	}

	////{PROTECTED REGION ID(R1451132663) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoOperazione. 
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
	public java.lang.String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descIter = null;

	/**
	 * @generated
	 */
	public void setDescIter(java.lang.String val) {
		descIter = val;
	}

	////{PROTECTED REGION ID(R-285101673) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descIter. 
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
	public java.lang.String getDescIter() {
		return descIter;
	}

	/**
	 * @generated
	 */
	private java.lang.String codFaseObbligatoriaFinale = null;

	/**
	 * @generated
	 */
	public void setCodFaseObbligatoriaFinale(java.lang.String val) {
		codFaseObbligatoriaFinale = val;
	}

	////{PROTECTED REGION ID(R-42097873) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codFaseObbligatoriaFinale. 
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
	public java.lang.String getCodFaseObbligatoriaFinale() {
		return codFaseObbligatoriaFinale;
	}

	/*PROTECTED REGION ID(R185097555) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
