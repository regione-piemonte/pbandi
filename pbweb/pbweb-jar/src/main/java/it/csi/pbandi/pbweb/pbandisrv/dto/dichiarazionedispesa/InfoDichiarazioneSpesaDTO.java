package it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa;

////{PROTECTED REGION ID(R1217185937) ENABLED START////}
/**
 * Inserire qui la documentazione della classe InfoDichiarazioneSpesaDTO.
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
public class InfoDichiarazioneSpesaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String[] messaggi = null;

	/**
	 * @generated
	 */
	public void setMessaggi(java.lang.String[] val) {
		messaggi = val;
	}

	////{PROTECTED REGION ID(R1383901457) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo messaggi. 
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
	public java.lang.String[] getMessaggi() {
		return messaggi;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isFinaleOIntegrativa = null;

	/**
	 * @generated
	 */
	public void setIsFinaleOIntegrativa(java.lang.Boolean val) {
		isFinaleOIntegrativa = val;
	}

	////{PROTECTED REGION ID(R-877531429) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isFinaleOIntegrativa. 
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
	public java.lang.Boolean getIsFinaleOIntegrativa() {
		return isFinaleOIntegrativa;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean pagamentiRespinti = null;

	/**
	 * @generated
	 */
	public void setPagamentiRespinti(java.lang.Boolean val) {
		pagamentiRespinti = val;
	}

	////{PROTECTED REGION ID(R825294073) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiRespinti. 
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
	public java.lang.Boolean getPagamentiRespinti() {
		return pagamentiRespinti;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean pagamentiDaRespingere = null;

	/**
	 * @generated
	 */
	public void setPagamentiDaRespingere(java.lang.Boolean val) {
		pagamentiDaRespingere = val;
	}

	////{PROTECTED REGION ID(R-794861134) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamentiDaRespingere. 
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
	public java.lang.Boolean getPagamentiDaRespingere() {
		return pagamentiDaRespingere;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean dichiarazioneFinalePresente = null;

	/**
	 * @generated
	 */
	public void setDichiarazioneFinalePresente(java.lang.Boolean val) {
		dichiarazioneFinalePresente = val;
	}

	////{PROTECTED REGION ID(R1924591566) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dichiarazioneFinalePresente. 
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
	public java.lang.Boolean getDichiarazioneFinalePresente() {
		return dichiarazioneFinalePresente;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isIntermedia = null;

	/**
	 * @generated
	 */
	public void setIsIntermedia(java.lang.Boolean val) {
		isIntermedia = val;
	}

	////{PROTECTED REGION ID(R1851730019) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isIntermedia. 
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
	public java.lang.Boolean getIsIntermedia() {
		return isIntermedia;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean altreDichiarazioniDiSpesaIntermedieNonValidate = null;

	/**
	 * @generated
	 */
	public void setAltreDichiarazioniDiSpesaIntermedieNonValidate(
			java.lang.Boolean val) {
		altreDichiarazioniDiSpesaIntermedieNonValidate = val;
	}

	////{PROTECTED REGION ID(R1077620773) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo altreDichiarazioniDiSpesaIntermedieNonValidate. 
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
	public java.lang.Boolean getAltreDichiarazioniDiSpesaIntermedieNonValidate() {
		return altreDichiarazioniDiSpesaIntermedieNonValidate;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isProgettoChiuso = null;

	/**
	 * @generated
	 */
	public void setIsProgettoChiuso(java.lang.Boolean val) {
		isProgettoChiuso = val;
	}

	////{PROTECTED REGION ID(R207780780) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isProgettoChiuso. 
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
	public java.lang.Boolean getIsProgettoChiuso() {
		return isProgettoChiuso;
	}

	/*PROTECTED REGION ID(R-311898790) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
