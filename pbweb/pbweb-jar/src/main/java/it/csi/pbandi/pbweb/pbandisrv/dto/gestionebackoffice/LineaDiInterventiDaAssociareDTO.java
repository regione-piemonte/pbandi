/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice;

////{PROTECTED REGION ID(R557371355) ENABLED START////}
/**
 * Inserire qui la documentazione della classe LineaDiInterventiDaAssociareDTO.
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
public class LineaDiInterventiDaAssociareDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idBando = null;

	/**
	 * @generated
	 */
	public void setIdBando(java.lang.Long val) {
		idBando = val;
	}

	////{PROTECTED REGION ID(R-510365628) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBando. 
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
	public java.lang.Long getIdBando() {
		return idBando;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLineaDiIntervento = null;

	/**
	 * @generated
	 */
	public void setIdLineaDiIntervento(java.lang.Long val) {
		idLineaDiIntervento = val;
	}

	////{PROTECTED REGION ID(R277478746) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaDiIntervento. 
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
	public java.lang.Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeBandoLinea = null;

	/**
	 * @generated
	 */
	public void setNomeBandoLinea(java.lang.String val) {
		nomeBandoLinea = val;
	}

	////{PROTECTED REGION ID(R-1658271033) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeBandoLinea. 
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
	public java.lang.String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDefinizioneProcesso = null;

	/**
	 * @generated
	 */
	public void setIdDefinizioneProcesso(java.lang.Long val) {
		idDefinizioneProcesso = val;
	}

	////{PROTECTED REGION ID(R-443380638) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDefinizioneProcesso. 
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
	public java.lang.Long getIdDefinizioneProcesso() {
		return idDefinizioneProcesso;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCategoriaCIPE = null;

	/**
	 * @generated
	 */
	public void setIdCategoriaCIPE(java.lang.Long val) {
		idCategoriaCIPE = val;
	}

	////{PROTECTED REGION ID(R1881359864) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCategoriaCIPE. 
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
	public java.lang.Long getIdCategoriaCIPE() {
		return idCategoriaCIPE;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipologiaCIPE = null;

	/**
	 * @generated
	 */
	public void setIdTipologiaCIPE(java.lang.Long val) {
		idTipologiaCIPE = val;
	}

	////{PROTECTED REGION ID(R1528897261) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipologiaCIPE. 
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
	public java.lang.Long getIdTipologiaCIPE() {
		return idTipologiaCIPE;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idObiettivoSpecifQSN = null;

	/**
	 * @generated
	 */
	public void setIdObiettivoSpecifQSN(java.lang.Long val) {
		idObiettivoSpecifQSN = val;
	}

	////{PROTECTED REGION ID(R-664619561) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idObiettivoSpecifQSN. 
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
	public java.lang.Long getIdObiettivoSpecifQSN() {
		return idObiettivoSpecifQSN;
	}

	/**
	 * @generated
	 */
	private java.lang.Double mesiDurataDtConcessione = null;

	/**
	 * @generated
	 */
	public void setMesiDurataDtConcessione(java.lang.Double val) {
		mesiDurataDtConcessione = val;
	}

	////{PROTECTED REGION ID(R-1711432581) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo mesiDurataDtConcessione. 
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
	public java.lang.Double getMesiDurataDtConcessione() {
		return mesiDurataDtConcessione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProcesso = null;

	/**
	 * @generated
	 */
	public void setIdProcesso(java.lang.Long val) {
		idProcesso = val;
	}

	////{PROTECTED REGION ID(R741527606) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProcesso. 
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
	public java.lang.Long getIdProcesso() {
		return idProcesso;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagSif = null;

	/**
	 * @generated
	 */
	public void setFlagSif(java.lang.String val) {
		flagSif = val;
	}

	////{PROTECTED REGION ID(R1379905929) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagSif. 
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
	public java.lang.String getFlagSif() {
		return flagSif;
	}

	/**
	 * @generated
	 */
	private java.lang.Long progrBandoLineaIntervSif = null;

	/**
	 * @generated
	 */
	public void setProgrBandoLineaIntervSif(java.lang.Long val) {
		progrBandoLineaIntervSif = val;
	}

	////{PROTECTED REGION ID(R1138831814) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progrBandoLineaIntervSif. 
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
	public java.lang.Long getProgrBandoLineaIntervSif() {
		return progrBandoLineaIntervSif;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idTipoLocalizzazione = null;

	/**
	 * @generated
	 */
	public void setIdTipoLocalizzazione(java.lang.Long val) {
		idTipoLocalizzazione = val;
	}

	////{PROTECTED REGION ID(R2106114526) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoLocalizzazione. 
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
	public java.lang.Long getIdTipoLocalizzazione() {
		return idTipoLocalizzazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLivelloIstituzione = null;

	/**
	 * @generated
	 */
	public void setIdLivelloIstituzione(java.lang.Long val) {
		idLivelloIstituzione = val;
	}

	////{PROTECTED REGION ID(R44058442) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLivelloIstituzione. 
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
	public java.lang.Long getIdLivelloIstituzione() {
		return idLivelloIstituzione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idProgettoComplesso = null;

	/**
	 * @generated
	 */
	public void setIdProgettoComplesso(java.lang.Long val) {
		idProgettoComplesso = val;
	}

	////{PROTECTED REGION ID(R-413819059) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idProgettoComplesso. 
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
	public java.lang.Long getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idClassificazioneMet = null;

	/**
	 * @generated
	 */
	public void setIdClassificazioneMet(java.lang.Long val) {
		idClassificazioneMet = val;
	}

	////{PROTECTED REGION ID(R2095063597) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idClassificazioneMet. 
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
	public java.lang.Long getIdClassificazioneMet() {
		return idClassificazioneMet;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagFondoDiFondi = null;

	/**
	 * @generated
	 */
	public void setFlagFondoDiFondi(java.lang.String val) {
		flagFondoDiFondi = val;
	}

	////{PROTECTED REGION ID(R-1131921476) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagFondoDiFondi. 
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
	public java.lang.String getFlagFondoDiFondi() {
		return flagFondoDiFondi;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idClassificazioneRa = null;

	/**
	 * @generated
	 */
	public void setIdClassificazioneRa(java.lang.Long val) {
		idClassificazioneRa = val;
	}

	////{PROTECTED REGION ID(R-2010627138) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idClassificazioneRa. 
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
	public java.lang.Long getIdClassificazioneRa() {
		return idClassificazioneRa;
	}

	/**
	 * @generated
	 */
	private java.lang.String codAiutoRna = null;

	/**
	 * @generated
	 */
	public void setCodAiutoRna(java.lang.String val) {
		codAiutoRna = val;
	}

	////{PROTECTED REGION ID(R-246095142) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codAiutoRna. 
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
	public java.lang.String getCodAiutoRna() {
		return codAiutoRna;
	}

	/*PROTECTED REGION ID(R-1419255834) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals

	public String toString() {

		String out = "it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaAssociareDTO:";
		out = out + "\nNomeBandoLinea = " + this.getNomeBandoLinea();
		out = out + "\nIdBando = " + this.getIdBando();
		out = out + "\nIdCategoriaCIPE = " + this.getIdCategoriaCIPE();
		out = out + "\nIdDefinizioneProcesso = "
				+ this.getIdDefinizioneProcesso();
		out = out + "\nIdLineaDiIntervento = " + this.getIdLineaDiIntervento();
		out = out + "\nIdObiettivoSpecifQSN = "
				+ this.getIdObiettivoSpecifQSN();
		out = out + "\nIdTipologiaCIPE = " + this.getIdTipologiaCIPE();
		out = out + "\nMesiDurataDtConcessione = "
				+ this.getMesiDurataDtConcessione();
		out = out + "\nFlagSif = " + this.getFlagSif();
		out = out + "\nProgrBandoLineaIntervSif = "
				+ this.getProgrBandoLineaIntervSif();
		out = out + "\nIdLivelloIstituzione = "
				+ this.getIdLivelloIstituzione();
		out = out + "\nFlagFondoDiFondi = " + this.getFlagFondoDiFondi();
		out = out + "\nCodAiutoRna = " + this.getCodAiutoRna();

		return out;
	}

	/*PROTECTED REGION END*/
}
