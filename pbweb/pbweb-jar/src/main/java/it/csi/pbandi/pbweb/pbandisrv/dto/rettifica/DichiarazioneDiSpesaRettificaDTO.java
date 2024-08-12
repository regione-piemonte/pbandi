package it.csi.pbandi.pbweb.pbandisrv.dto.rettifica;

import java.util.Date;

////{PROTECTED REGION ID(R-851824721) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DichiarazioneDiSpesaRettificaDTO.
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
public class DichiarazioneDiSpesaRettificaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;
	
	private Long idUtenteIns;

	public Long getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDichiarazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setDescTipoDichiarazioneSpesa(java.lang.String val) {
		descTipoDichiarazioneSpesa = val;
	}

	////{PROTECTED REGION ID(R-1754150076) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDichiarazioneSpesa. 
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
	public java.lang.String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtChiusuraValidazione = null;

	/**
	 * @generated
	 */
	public void setDtChiusuraValidazione(java.util.Date val) {
		dtChiusuraValidazione = val;
	}

	////{PROTECTED REGION ID(R-1496244271) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtChiusuraValidazione. 
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
	public java.util.Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtDichiarazione = null;

	/**
	 * @generated
	 */
	public void setDtDichiarazione(java.util.Date val) {
		dtDichiarazione = val;
	}

	////{PROTECTED REGION ID(R7846215) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtDichiarazione. 
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
	public java.util.Date getDtDichiarazione() {
		return dtDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDichiarazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDichiarazioneSpesa(java.lang.Long val) {
		idDichiarazioneSpesa = val;
	}

	////{PROTECTED REGION ID(R-1947918150) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDichiarazioneSpesa. 
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
	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocIndexDichSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocIndexDichSpesa(java.lang.Long val) {
		idDocIndexDichSpesa = val;
	}

	////{PROTECTED REGION ID(R553712424) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocIndexDichSpesa. 
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
	public java.lang.Long getIdDocIndexDichSpesa() {
		return idDocIndexDichSpesa;
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

	////{PROTECTED REGION ID(R-1226450706) ENABLED START////}
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
	private java.lang.Long idTipoDichiarazSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDichiarazSpesa(java.lang.Long val) {
		idTipoDichiarazSpesa = val;
	}

	////{PROTECTED REGION ID(R-49152053) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idTipoDichiarazSpesa. 
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
	public java.lang.Long getIdTipoDichiarazSpesa() {
		return idTipoDichiarazSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteChiusuraValidazione = null;

	/**
	 * @generated
	 */
	public void setNoteChiusuraValidazione(java.lang.String val) {
		noteChiusuraValidazione = val;
	}

	////{PROTECTED REGION ID(R1968080111) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteChiusuraValidazione. 
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
	public java.lang.String getNoteChiusuraValidazione() {
		return noteChiusuraValidazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date periodoAl = null;

	/**
	 * @generated
	 */
	public void setPeriodoAl(java.util.Date val) {
		periodoAl = val;
	}

	////{PROTECTED REGION ID(R264557770) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo periodoAl. 
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
	public java.util.Date getPeriodoAl() {
		return periodoAl;
	}

	/**
	 * @generated
	 */
	private java.util.Date periodoDal = null;

	/**
	 * @generated
	 */
	public void setPeriodoDal(java.util.Date val) {
		periodoDal = val;
	}

	////{PROTECTED REGION ID(R-388641072) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo periodoDal. 
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
	public java.util.Date getPeriodoDal() {
		return periodoDal;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocIndexReportDettaglio = null;

	/**
	 * @generated
	 */
	public void setIdDocIndexReportDettaglio(java.lang.Long val) {
		idDocIndexReportDettaglio = val;
	}

	////{PROTECTED REGION ID(R-597552781) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocIndexReportDettaglio. 
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
	public java.lang.Long getIdDocIndexReportDettaglio() {
		return idDocIndexReportDettaglio;
	}
	
	private String rilievoContabile;
	private Date dtRilievoContabile;
	private Date dtChiusuraRilievo;
	private Date dtConfermaValidita;

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public Date getDtChiusuraRilievo() {
		return dtChiusuraRilievo;
	}

	public void setDtChiusuraRilievo(Date dtChiusuraRilievo) {
		this.dtChiusuraRilievo = dtChiusuraRilievo;
	}

	public Date getDtConfermaValidita() {
		return dtConfermaValidita;
	}

	public void setDtConfermaValidita(Date dtConfermaValidita) {
		this.dtConfermaValidita = dtConfermaValidita;
	}
	

	/*PROTECTED REGION ID(R-196397764) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
