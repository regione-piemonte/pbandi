package it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico;

////{PROTECTED REGION ID(R1851702827) ENABLED START////}
/**
 * Inserire qui la documentazione della classe EsitoFindContoEconomicoDTO.
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
public class EsitoFindContoEconomicoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private boolean locked = true;

	/**
	 * @generated
	 */
	public void setLocked(boolean val) {
		locked = val;
	}

	////{PROTECTED REGION ID(R823813365) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo locked. 
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
	public boolean getLocked() {
		return locked;
	}

	/**
	 * @generated
	 */
	private boolean modificabile = true;

	/**
	 * @generated
	 */
	public void setModificabile(boolean val) {
		modificabile = val;
	}

	////{PROTECTED REGION ID(R-144869325) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo modificabile. 
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
	public boolean getModificabile() {
		return modificabile;
	}

	/**
	 * @generated
	 */
	private boolean copiaModificataPresente = true;

	/**
	 * @generated
	 */
	public void setCopiaModificataPresente(boolean val) {
		copiaModificataPresente = val;
	}

	////{PROTECTED REGION ID(R438540624) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo copiaModificataPresente. 
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
	public boolean getCopiaModificataPresente() {
		return copiaModificataPresente;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO contoEconomico = null;

	/**
	 * @generated
	 */
	public void setContoEconomico(
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO val) {
		contoEconomico = val;
	}

	////{PROTECTED REGION ID(R743856904) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo contoEconomico. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoRimodulazioneDTO getContoEconomico() {
		return contoEconomico;
	}

	/**
	 * @generated
	 */
	private boolean rimodulazionePresente = true;

	/**
	 * @generated
	 */
	public void setRimodulazionePresente(boolean val) {
		rimodulazionePresente = val;
	}

	////{PROTECTED REGION ID(R-1022593353) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rimodulazionePresente. 
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
	public boolean getRimodulazionePresente() {
		return rimodulazionePresente;
	}

	/**
	 * @generated
	 */
	private boolean propostaPresente = true;

	/**
	 * @generated
	 */
	public void setPropostaPresente(boolean val) {
		propostaPresente = val;
	}

	////{PROTECTED REGION ID(R1567163529) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo propostaPresente. 
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
	public boolean getPropostaPresente() {
		return propostaPresente;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataUltimaProposta = null;

	/**
	 * @generated
	 */
	public void setDataUltimaProposta(java.util.Date val) {
		dataUltimaProposta = val;
	}

	////{PROTECTED REGION ID(R-1800019223) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataUltimaProposta. 
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
	public java.util.Date getDataUltimaProposta() {
		return dataUltimaProposta;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataUltimaRimodulazione = null;

	/**
	 * @generated
	 */
	public void setDataUltimaRimodulazione(java.util.Date val) {
		dataUltimaRimodulazione = val;
	}

	////{PROTECTED REGION ID(R-1820507069) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataUltimaRimodulazione. 
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
	public java.util.Date getDataUltimaRimodulazione() {
		return dataUltimaRimodulazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataPresentazioneDomanda = null;

	/**
	 * @generated
	 */
	public void setDataPresentazioneDomanda(java.util.Date val) {
		dataPresentazioneDomanda = val;
	}

	////{PROTECTED REGION ID(R1227529936) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataPresentazioneDomanda. 
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
	public java.util.Date getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataFineIstruttoria = null;

	/**
	 * @generated
	 */
	public void setDataFineIstruttoria(java.util.Date val) {
		dataFineIstruttoria = val;
	}

	////{PROTECTED REGION ID(R-1109985255) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataFineIstruttoria. 
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
	public java.util.Date getDataFineIstruttoria() {
		return dataFineIstruttoria;
	}

	/**
	 * @generated
	 */
	private boolean inIstruttoria = true;

	/**
	 * @generated
	 */
	public void setInIstruttoria(boolean val) {
		inIstruttoria = val;
	}

	////{PROTECTED REGION ID(R-1516716904) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inIstruttoria. 
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
	public boolean getInIstruttoria() {
		return inIstruttoria;
	}

	/**
	 * @generated
	 */
	private boolean inStatoRichiesto = true;

	/**
	 * @generated
	 */
	public void setInStatoRichiesto(boolean val) {
		inStatoRichiesto = val;
	}

	////{PROTECTED REGION ID(R-1802116469) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inStatoRichiesto. 
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
	public boolean getInStatoRichiesto() {
		return inStatoRichiesto;
	}

	/**
	 * @generated
	 */
	private boolean isContoMainNew = true;

	/**
	 * @generated
	 */
	public void setIsContoMainNew(boolean val) {
		isContoMainNew = val;
	}

	////{PROTECTED REGION ID(R67409951) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isContoMainNew. 
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
	public boolean getIsContoMainNew() {
		return isContoMainNew;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagUltimoRibassoAstaInProposta = null;

	/**
	 * @generated
	 */
	public void setFlagUltimoRibassoAstaInProposta(java.lang.String val) {
		flagUltimoRibassoAstaInProposta = val;
	}

	////{PROTECTED REGION ID(R1875309324) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagUltimoRibassoAstaInProposta. 
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
	public java.lang.String getFlagUltimoRibassoAstaInProposta() {
		return flagUltimoRibassoAstaInProposta;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagUltimoRibassoAstaInRimodulazione = null;

	/**
	 * @generated
	 */
	public void setFlagUltimoRibassoAstaInRimodulazione(java.lang.String val) {
		flagUltimoRibassoAstaInRimodulazione = val;
	}

	////{PROTECTED REGION ID(R-2075143680) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagUltimoRibassoAstaInRimodulazione. 
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
	public java.lang.String getFlagUltimoRibassoAstaInRimodulazione() {
		return flagUltimoRibassoAstaInRimodulazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoUltimoRibassoAstaInProposta = null;

	/**
	 * @generated
	 */
	public void setImportoUltimoRibassoAstaInProposta(java.lang.Double val) {
		importoUltimoRibassoAstaInProposta = val;
	}

	////{PROTECTED REGION ID(R-1985357500) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoUltimoRibassoAstaInProposta. 
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
	public java.lang.Double getImportoUltimoRibassoAstaInProposta() {
		return importoUltimoRibassoAstaInProposta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percUltimoRibassoAstaInProposta = null;

	/**
	 * @generated
	 */
	public void setPercUltimoRibassoAstaInProposta(java.lang.Double val) {
		percUltimoRibassoAstaInProposta = val;
	}

	////{PROTECTED REGION ID(R925901650) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percUltimoRibassoAstaInProposta. 
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
	public java.lang.Double getPercUltimoRibassoAstaInProposta() {
		return percUltimoRibassoAstaInProposta;
	}

	/*PROTECTED REGION ID(R-1960485546) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
