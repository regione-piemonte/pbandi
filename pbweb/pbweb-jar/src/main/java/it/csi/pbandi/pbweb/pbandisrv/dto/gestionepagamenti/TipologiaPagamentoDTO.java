package it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti;

////{PROTECTED REGION ID(R1418082857) ENABLED START////}
/**
 * Inserire qui la documentazione della classe TipologiaPagamentoDTO.
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
public class TipologiaPagamentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setIdModalitaPagamento(java.lang.Long val) {
		idModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R2110769593) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaPagamento. 
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
	public java.lang.Long getIdModalitaPagamento() {
		return idModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setDescBreveModalitaPagamento(java.lang.String val) {
		descBreveModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R-1494974445) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveModalitaPagamento. 
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
	public java.lang.String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setDescModalitaPagamento(java.lang.String val) {
		descModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R-1640677053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaPagamento. 
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
	public java.lang.String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtFineValidita = null;

	/**
	 * @generated
	 */
	public void setDtFineValidita(java.util.Date val) {
		dtFineValidita = val;
	}

	////{PROTECTED REGION ID(R-1403978227) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtFineValidita. 
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
	public java.util.Date getDtFineValidita() {
		return dtFineValidita;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInizioValidita = null;

	/**
	 * @generated
	 */
	public void setDtInizioValidita(java.util.Date val) {
		dtInizioValidita = val;
	}

	////{PROTECTED REGION ID(R-255719217) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInizioValidita. 
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
	public java.util.Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	/*PROTECTED REGION ID(R-1126188606) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
