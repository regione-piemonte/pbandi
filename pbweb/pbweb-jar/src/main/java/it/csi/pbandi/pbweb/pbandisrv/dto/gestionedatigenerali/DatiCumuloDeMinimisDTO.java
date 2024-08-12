package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali;

////{PROTECTED REGION ID(R1427219541) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DatiCumuloDeMinimisDTO.
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
public class DatiCumuloDeMinimisDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Double cumulo = null;

	/**
	 * @generated
	 */
	public void setCumulo(java.lang.Double val) {
		cumulo = val;
	}

	////{PROTECTED REGION ID(R1112286514) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cumulo. 
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
	public java.lang.Double getCumulo() {
		return cumulo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoDisponibile = null;

	/**
	 * @generated
	 */
	public void setImportoDisponibile(java.lang.Double val) {
		importoDisponibile = val;
	}

	////{PROTECTED REGION ID(R485023443) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoDisponibile. 
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
	public java.lang.Double getImportoDisponibile() {
		return importoDisponibile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoSuperato = null;

	/**
	 * @generated
	 */
	public void setImportoSuperato(java.lang.Double val) {
		importoSuperato = val;
	}

	////{PROTECTED REGION ID(R-1122994218) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoSuperato. 
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
	public java.lang.Double getImportoSuperato() {
		return importoSuperato;
	}

	/*PROTECTED REGION ID(R1256689004) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
