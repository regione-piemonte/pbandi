package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R-1076353991) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PagamentoDTO.
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
public class PagamentoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

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

	////{PROTECTED REGION ID(R-998948557) ENABLED START////}
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
	private java.lang.Long idModalitaPagamento = null;

	/**
	 * @generated
	 */
	public void setIdModalitaPagamento(java.lang.Long val) {
		idModalitaPagamento = val;
	}

	////{PROTECTED REGION ID(R1861158313) ENABLED START////}
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
	private java.lang.Long idPagamento = null;

	/**
	 * @generated
	 */
	public void setIdPagamento(java.lang.Long val) {
		idPagamento = val;
	}

	////{PROTECTED REGION ID(R1065411186) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idPagamento. 
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
	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotale = null;

	/**
	 * @generated
	 */
	public void setImportoTotale(java.lang.Double val) {
		importoTotale = val;
	}

	////{PROTECTED REGION ID(R36340754) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotale. 
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
	public java.lang.Double getImportoTotale() {
		return importoTotale;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtPagamento = null;

	/**
	 * @generated
	 */
	public void setDtPagamento(java.util.Date val) {
		dtPagamento = val;
	}

	////{PROTECTED REGION ID(R-1683982691) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtPagamento. 
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
	public java.util.Date getDtPagamento() {
		return dtPagamento;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtValuta = null;

	/**
	 * @generated
	 */
	public void setDtValuta(java.util.Date val) {
		dtValuta = val;
	}

	////{PROTECTED REGION ID(R792970954) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtValuta. 
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
	public java.util.Date getDtValuta() {
		return dtValuta;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] vociDiSpesa = null;

	/**
	 * @generated
	 */
	public void setVociDiSpesa(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] val) {
		vociDiSpesa = val;
	}

	////{PROTECTED REGION ID(R1596366009) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo vociDiSpesa. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] getVociDiSpesa() {
		return vociDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichiarazioneDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDichiarazioneDiSpesa(java.lang.Long val) {
		idDichiarazioneDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1268965569) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichiarazioneDiSpesa. 
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
	public java.lang.Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	/*PROTECTED REGION ID(R-1375799886) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
