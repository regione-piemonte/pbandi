package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

////{PROTECTED REGION ID(R929141975) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ItemRicercaDocumentiSpesa.
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
public class ItemRicercaDocumentiSpesa implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idDocumento = null;

	/**
	 * @generated
	 */
	public void setIdDocumento(java.lang.Long val) {
		idDocumento = val;
	}

	////{PROTECTED REGION ID(R1777910434) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumento. 
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
	public java.lang.Long getIdDocumento() {
		return idDocumento;
	}
	
	private java.lang.Long idProgetto = null;

	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipologia = null;

	/**
	 * @generated
	 */
	public void setTipologia(java.lang.String val) {
		tipologia = val;
	}

	////{PROTECTED REGION ID(R-679843407) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipologia. 
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
	public java.lang.String getTipologia() {
		return tipologia;
	}

	/**
	 * @generated
	 */
	private java.lang.String estremi = null;

	/**
	 * @generated
	 */
	public void setEstremi(java.lang.String val) {
		estremi = val;
	}

	////{PROTECTED REGION ID(R-13153058) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo estremi. 
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
	public java.lang.String getEstremi() {
		return estremi;
	}

	/**
	 * @generated
	 */
	private java.lang.String fornitore = null;

	/**
	 * @generated
	 */
	public void setFornitore(java.lang.String val) {
		fornitore = val;
	}

	////{PROTECTED REGION ID(R-1829560005) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fornitore. 
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
	public java.lang.String getFornitore() {
		return fornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String stato = null;

	/**
	 * @generated
	 */
	public void setStato(java.lang.String val) {
		stato = val;
	}

	////{PROTECTED REGION ID(R1072143908) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo stato. 
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
	public java.lang.String getStato() {
		return stato;
	}

	/**
	 * @generated
	 */
	private java.lang.String progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(java.lang.String val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R542498715) ENABLED START////}
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
	public java.lang.String getProgetto() {
		return progetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importo = null;

	/**
	 * @generated
	 */
	public void setImporto(java.lang.Double val) {
		importo = val;
	}

	////{PROTECTED REGION ID(R-933651277) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importo. 
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
	public java.lang.Double getImporto() {
		return importo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double validato = null;

	/**
	 * @generated
	 */
	public void setValidato(java.lang.Double val) {
		validato = val;
	}

	////{PROTECTED REGION ID(R122676983) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo validato. 
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
	public java.lang.Double getValidato() {
		return validato;
	}

	/**
	 * @generated
	 */
	private java.lang.String progetti = null;

	/**
	 * @generated
	 */
	public void setProgetti(java.lang.String val) {
		progetti = val;
	}

	////{PROTECTED REGION ID(R542498709) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progetti. 
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
	public java.lang.String getProgetti() {
		return progetti;
	}

	/**
	 * @generated
	 */
	private java.lang.String importi = null;

	/**
	 * @generated
	 */
	public void setImporti(java.lang.String val) {
		importi = val;
	}

	////{PROTECTED REGION ID(R-933651283) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importi. 
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
	public java.lang.String getImporti() {
		return importi;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isModificabile = null;

	/**
	 * @generated
	 */
	public void setIsModificabile(java.lang.Boolean val) {
		isModificabile = val;
	}

	////{PROTECTED REGION ID(R874341737) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isModificabile. 
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
	public java.lang.Boolean getIsModificabile() {
		return isModificabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isClonabile = null;

	/**
	 * @generated
	 */
	public void setIsClonabile(java.lang.Boolean val) {
		isClonabile = val;
	}

	////{PROTECTED REGION ID(R1552727448) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isClonabile. 
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
	public java.lang.Boolean getIsClonabile() {
		return isClonabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isEliminabile = null;

	/**
	 * @generated
	 */
	public void setIsEliminabile(java.lang.Boolean val) {
		isEliminabile = val;
	}

	////{PROTECTED REGION ID(R1876438128) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isEliminabile. 
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
	public java.lang.Boolean getIsEliminabile() {
		return isEliminabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isAssociabile = null;

	/**
	 * @generated
	 */
	public void setIsAssociabile(java.lang.Boolean val) {
		isAssociabile = val;
	}

	////{PROTECTED REGION ID(R516459116) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isAssociabile. 
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
	public java.lang.Boolean getIsAssociabile() {
		return isAssociabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isAssociato = null;

	/**
	 * @generated
	 */
	public void setIsAssociato(java.lang.Boolean val) {
		isAssociato = val;
	}

	////{PROTECTED REGION ID(R-526835737) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isAssociato. 
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
	public java.lang.Boolean getIsAssociato() {
		return isAssociato;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoInvio = null;

	/**
	 * @generated
	 */
	public void setTipoInvio(java.lang.String val) {
		tipoInvio = val;
	}

	////{PROTECTED REGION ID(R-712182004) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoInvio. 
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
	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isAllegatiPresenti = null;

	/**
	 * @generated
	 */
	public void setIsAllegatiPresenti(java.lang.Boolean val) {
		isAllegatiPresenti = val;
	}

	////{PROTECTED REGION ID(R-436600766) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isAllegatiPresenti. 
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
	public java.lang.Boolean getIsAllegatiPresenti() {
		return isAllegatiPresenti;
	}

	/*PROTECTED REGION ID(R-1785721174) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
