package it.csi.pbandi.pbweb.pbandisrv.dto.checklist;

////{PROTECTED REGION ID(R536531387) ENABLED START////}
/**
 * Inserire qui la documentazione della classe datiChecklistDTO.
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
public class datiChecklistDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String statoChecklistDocumentale = null;

	/**
	 * @generated
	 */
	public void setStatoChecklistDocumentale(java.lang.String val) {
		statoChecklistDocumentale = val;
	}

	////{PROTECTED REGION ID(R-1320590087) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoChecklistDocumentale. 
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
	public java.lang.String getStatoChecklistDocumentale() {
		return statoChecklistDocumentale;
	}

	/**
	 * @generated
	 */
	private java.lang.String statoChecklistInLoco = null;

	/**
	 * @generated
	 */
	public void setStatoChecklistInLoco(java.lang.String val) {
		statoChecklistInLoco = val;
	}

	////{PROTECTED REGION ID(R576128346) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo statoChecklistInLoco. 
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
	public java.lang.String getStatoChecklistInLoco() {
		return statoChecklistInLoco;
	}

	/*PROTECTED REGION ID(R-1229565178) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
