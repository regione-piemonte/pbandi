/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio;

////{PROTECTED REGION ID(R-1566864827) ENABLED START////}
/**
 * Inserire qui la documentazione della classe DettaglioAttoDiLiquidazioneDTO.
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
public class DettaglioAttoDiLiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String annoAtto = null;

	/**
	 * @generated
	 */
	public void setAnnoAtto(java.lang.String val) {
		annoAtto = val;
	}

	////{PROTECTED REGION ID(R-1961656575) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo annoAtto. 
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
	public java.lang.String getAnnoAtto() {
		return annoAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroAtto = null;

	/**
	 * @generated
	 */
	public void setNumeroAtto(java.lang.String val) {
		numeroAtto = val;
	}

	////{PROTECTED REGION ID(R1680474159) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroAtto. 
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
	public java.lang.String getNumeroAtto() {
		return numeroAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descAtto = null;

	/**
	 * @generated
	 */
	public void setDescAtto(java.lang.String val) {
		descAtto = val;
	}

	////{PROTECTED REGION ID(R-293727900) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descAtto. 
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
	public java.lang.String getDescAtto() {
		return descAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleAtto = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleAtto(java.lang.Double val) {
		importoTotaleAtto = val;
	}

	////{PROTECTED REGION ID(R351366292) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleAtto. 
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
	public java.lang.Double getImportoTotaleAtto() {
		return importoTotaleAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoAtto = null;

	/**
	 * @generated
	 */
	public void setDescStatoAtto(java.lang.String val) {
		descStatoAtto = val;
	}

	////{PROTECTED REGION ID(R-1413066893) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoAtto. 
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
	public java.lang.String getDescStatoAtto() {
		return descStatoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInserimentoAtto = null;

	/**
	 * @generated
	 */
	public void setDtInserimentoAtto(java.util.Date val) {
		dtInserimentoAtto = val;
	}

	////{PROTECTED REGION ID(R-582281444) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInserimentoAtto. 
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
	public java.util.Date getDtInserimentoAtto() {
		return dtInserimentoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggiornamentoAtto = null;

	/**
	 * @generated
	 */
	public void setDtAggiornamentoAtto(java.util.Date val) {
		dtAggiornamentoAtto = val;
	}

	////{PROTECTED REGION ID(R210103980) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggiornamentoAtto. 
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
	public java.util.Date getDtAggiornamentoAtto() {
		return dtAggiornamentoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAggiornamentoBilancio = null;

	/**
	 * @generated
	 */
	public void setDtAggiornamentoBilancio(java.util.Date val) {
		dtAggiornamentoBilancio = val;
	}

	////{PROTECTED REGION ID(R2031799637) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAggiornamentoBilancio. 
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
	public java.util.Date getDtAggiornamentoBilancio() {
		return dtAggiornamentoBilancio;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtEmissioneAtto = null;

	/**
	 * @generated
	 */
	public void setDtEmissioneAtto(java.util.Date val) {
		dtEmissioneAtto = val;
	}

	////{PROTECTED REGION ID(R-1707816137) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtEmissioneAtto. 
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
	public java.util.Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtScadenzaAtto = null;

	/**
	 * @generated
	 */
	public void setDtScadenzaAtto(java.util.Date val) {
		dtScadenzaAtto = val;
	}

	////{PROTECTED REGION ID(R1590293830) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtScadenzaAtto. 
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
	public java.util.Date getDtScadenzaAtto() {
		return dtScadenzaAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtCompletamentoAtto = null;

	/**
	 * @generated
	 */
	public void setDtCompletamentoAtto(java.util.Date val) {
		dtCompletamentoAtto = val;
	}

	////{PROTECTED REGION ID(R1349210549) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtCompletamentoAtto. 
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
	public java.util.Date getDtCompletamentoAtto() {
		return dtCompletamentoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtAnnullamentoAtto = null;

	/**
	 * @generated
	 */
	public void setDtAnnullamentoAtto(java.util.Date val) {
		dtAnnullamentoAtto = val;
	}

	////{PROTECTED REGION ID(R608438023) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtAnnullamentoAtto. 
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
	public java.util.Date getDtAnnullamentoAtto() {
		return dtAnnullamentoAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtRicezioneAtto = null;

	/**
	 * @generated
	 */
	public void setDtRicezioneAtto(java.util.Date val) {
		dtRicezioneAtto = val;
	}

	////{PROTECTED REGION ID(R-862702825) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtRicezioneAtto. 
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
	public java.util.Date getDtRicezioneAtto() {
		return dtRicezioneAtto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtRifiutoRagioneria = null;

	/**
	 * @generated
	 */
	public void setDtRifiutoRagioneria(java.util.Date val) {
		dtRifiutoRagioneria = val;
	}

	////{PROTECTED REGION ID(R-2018868938) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtRifiutoRagioneria. 
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
	public java.util.Date getDtRifiutoRagioneria() {
		return dtRifiutoRagioneria;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtRichiestaModifica = null;

	/**
	 * @generated
	 */
	public void setDtRichiestaModifica(java.util.Date val) {
		dtRichiestaModifica = val;
	}

	////{PROTECTED REGION ID(R1501343163) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtRichiestaModifica. 
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
	public java.util.Date getDtRichiestaModifica() {
		return dtRichiestaModifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String testoRichiestaModifica = null;

	/**
	 * @generated
	 */
	public void setTestoRichiestaModifica(java.lang.String val) {
		testoRichiestaModifica = val;
	}

	////{PROTECTED REGION ID(R1787916984) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo testoRichiestaModifica. 
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
	public java.lang.String getTestoRichiestaModifica() {
		return testoRichiestaModifica;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteAtto = null;

	/**
	 * @generated
	 */
	public void setNoteAtto(java.lang.String val) {
		noteAtto = val;
	}

	////{PROTECTED REGION ID(R270084677) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteAtto. 
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
	public java.lang.String getNoteAtto() {
		return noteAtto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio.DettaglioLiquidazioneDTO[] liquidazioni = null;

	/**
	 * @generated
	 */
	public void setLiquidazioni(
			it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio.DettaglioLiquidazioneDTO[] val) {
		liquidazioni = val;
	}

	////{PROTECTED REGION ID(R1461143579) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo liquidazioni. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.consultazioneattibilancio.DettaglioLiquidazioneDTO[] getLiquidazioni() {
		return liquidazioni;
	}

	/**
	 * @generated
	 */
	private java.lang.String DISettoreAppartenenza = null;

	/**
	 * @generated
	 */
	public void setDISettoreAppartenenza(java.lang.String val) {
		DISettoreAppartenenza = val;
	}

	////{PROTECTED REGION ID(R294619233) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DISettoreAppartenenza. 
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
	public java.lang.String getDISettoreAppartenenza() {
		return DISettoreAppartenenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIEnteCompetenza = null;

	/**
	 * @generated
	 */
	public void setDIEnteCompetenza(java.lang.String val) {
		DIEnteCompetenza = val;
	}

	////{PROTECTED REGION ID(R-912741582) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIEnteCompetenza. 
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
	public java.lang.String getDIEnteCompetenza() {
		return DIEnteCompetenza;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIFunzionarioLiquidatore = null;

	/**
	 * @generated
	 */
	public void setDIFunzionarioLiquidatore(java.lang.String val) {
		DIFunzionarioLiquidatore = val;
	}

	////{PROTECTED REGION ID(R528471993) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIFunzionarioLiquidatore. 
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
	public java.lang.String getDIFunzionarioLiquidatore() {
		return DIFunzionarioLiquidatore;
	}

	/**
	 * @generated
	 */
	private java.lang.String DINumeroTelefono = null;

	/**
	 * @generated
	 */
	public void setDINumeroTelefono(java.lang.String val) {
		DINumeroTelefono = val;
	}

	////{PROTECTED REGION ID(R-2083044518) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DINumeroTelefono. 
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
	public java.lang.String getDINumeroTelefono() {
		return DINumeroTelefono;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIDirigente = null;

	/**
	 * @generated
	 */
	public void setDIDirigente(java.lang.String val) {
		DIDirigente = val;
	}

	////{PROTECTED REGION ID(R-1890341029) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDirigente. 
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
	public java.lang.String getDIDirigente() {
		return DIDirigente;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setDIModalitaAgevolazione(java.lang.String val) {
		DIModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-2031872099) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIModalitaAgevolazione. 
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
	public java.lang.String getDIModalitaAgevolazione() {
		return DIModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String DICausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setDICausaleErogazione(java.lang.String val) {
		DICausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-1678022801) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DICausaleErogazione. 
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
	public java.lang.String getDICausaleErogazione() {
		return DICausaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIQuietanzante = null;

	/**
	 * @generated
	 */
	public void setDIQuietanzante(java.lang.String val) {
		DIQuietanzante = val;
	}

	////{PROTECTED REGION ID(R96805057) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIQuietanzante. 
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
	public java.lang.String getDIQuietanzante() {
		return DIQuietanzante;
	}

	/**
	 * @generated
	 */
	private java.lang.String DICodiceFiscale = null;

	/**
	 * @generated
	 */
	public void setDICodiceFiscale(java.lang.String val) {
		DICodiceFiscale = val;
	}

	////{PROTECTED REGION ID(R-919661718) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DICodiceFiscale. 
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
	public java.lang.String getDICodiceFiscale() {
		return DICodiceFiscale;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean DIFattura = null;

	/**
	 * @generated
	 */
	public void setDIFattura(java.lang.Boolean val) {
		DIFattura = val;
	}

	////{PROTECTED REGION ID(R-1358829025) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIFattura. 
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
	public java.lang.Boolean getDIFattura() {
		return DIFattura;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean DIEstrattoCopiaProvvedimento = null;

	/**
	 * @generated
	 */
	public void setDIEstrattoCopiaProvvedimento(java.lang.Boolean val) {
		DIEstrattoCopiaProvvedimento = val;
	}

	////{PROTECTED REGION ID(R-853306180) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIEstrattoCopiaProvvedimento. 
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
	public java.lang.Boolean getDIEstrattoCopiaProvvedimento() {
		return DIEstrattoCopiaProvvedimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean DIDocumentazioneGiustificativa = null;

	/**
	 * @generated
	 */
	public void setDIDocumentazioneGiustificativa(java.lang.Boolean val) {
		DIDocumentazioneGiustificativa = val;
	}

	////{PROTECTED REGION ID(R1676054321) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDocumentazioneGiustificativa. 
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
	public java.lang.Boolean getDIDocumentazioneGiustificativa() {
		return DIDocumentazioneGiustificativa;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean DIDichiarazione = null;

	/**
	 * @generated
	 */
	public void setDIDichiarazione(java.lang.Boolean val) {
		DIDichiarazione = val;
	}

	////{PROTECTED REGION ID(R-457115108) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDichiarazione. 
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
	public java.lang.Boolean getDIDichiarazione() {
		return DIDichiarazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIAltro = null;

	/**
	 * @generated
	 */
	public void setDIAltro(java.lang.String val) {
		DIAltro = val;
	}

	////{PROTECTED REGION ID(R946250300) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIAltro. 
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
	public java.lang.String getDIAltro() {
		return DIAltro;
	}

	/**
	 * @generated
	 */
	private java.util.Date DIDataInserimento = null;

	/**
	 * @generated
	 */
	public void setDIDataInserimento(java.util.Date val) {
		DIDataInserimento = val;
	}

	////{PROTECTED REGION ID(R-1578100753) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDataInserimento. 
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
	public java.util.Date getDIDataInserimento() {
		return DIDataInserimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date DIDataAggiornamento = null;

	/**
	 * @generated
	 */
	public void setDIDataAggiornamento(java.util.Date val) {
		DIDataAggiornamento = val;
	}

	////{PROTECTED REGION ID(R719242047) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDataAggiornamento. 
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
	public java.util.Date getDIDataAggiornamento() {
		return DIDataAggiornamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIModalitaErogazione = null;

	/**
	 * @generated
	 */
	public void setDIModalitaErogazione(java.lang.String val) {
		DIModalitaErogazione = val;
	}

	////{PROTECTED REGION ID(R1918044014) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIModalitaErogazione. 
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
	public java.lang.String getDIModalitaErogazione() {
		return DIModalitaErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIEstremiBancari = null;

	/**
	 * @generated
	 */
	public void setDIEstremiBancari(java.lang.String val) {
		DIEstremiBancari = val;
	}

	////{PROTECTED REGION ID(R586196089) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIEstremiBancari. 
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
	public java.lang.String getDIEstremiBancari() {
		return DIEstremiBancari;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIBeneficiarioCedente = null;

	/**
	 * @generated
	 */
	public void setDIBeneficiarioCedente(java.lang.String val) {
		DIBeneficiarioCedente = val;
	}

	////{PROTECTED REGION ID(R67036312) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIBeneficiarioCedente. 
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
	public java.lang.String getDIBeneficiarioCedente() {
		return DIBeneficiarioCedente;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIBeneficiarioCeduto = null;

	/**
	 * @generated
	 */
	public void setDIBeneficiarioCeduto(java.lang.String val) {
		DIBeneficiarioCeduto = val;
	}

	////{PROTECTED REGION ID(R-1521842638) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIBeneficiarioCeduto. 
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
	public java.lang.String getDIBeneficiarioCeduto() {
		return DIBeneficiarioCeduto;
	}

	/**
	 * @generated
	 */
	private java.util.Date DIDataInserimentoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDIDataInserimentoBeneficiario(java.util.Date val) {
		DIDataInserimentoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-1585864247) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDataInserimentoBeneficiario. 
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
	public java.util.Date getDIDataInserimentoBeneficiario() {
		return DIDataInserimentoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.util.Date DIDataAggiornamentoBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDIDataAggiornamentoBeneficiario(java.util.Date val) {
		DIDataAggiornamentoBeneficiario = val;
	}

	////{PROTECTED REGION ID(R1135727897) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIDataAggiornamentoBeneficiario. 
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
	public java.util.Date getDIDataAggiornamentoBeneficiario() {
		return DIDataAggiornamentoBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIModalitaErogazioneBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDIModalitaErogazioneBeneficiario(java.lang.String val) {
		DIModalitaErogazioneBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-1796322616) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIModalitaErogazioneBeneficiario. 
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
	public java.lang.String getDIModalitaErogazioneBeneficiario() {
		return DIModalitaErogazioneBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String DIEstremiBancariBeneficiario = null;

	/**
	 * @generated
	 */
	public void setDIEstremiBancariBeneficiario(java.lang.String val) {
		DIEstremiBancariBeneficiario = val;
	}

	////{PROTECTED REGION ID(R-592859309) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DIEstremiBancariBeneficiario. 
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
	public java.lang.String getDIEstremiBancariBeneficiario() {
		return DIEstremiBancariBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBCodiceBeneficiarioBilancio = null;

	/**
	 * @generated
	 */
	public void setDBCodiceBeneficiarioBilancio(java.lang.String val) {
		DBCodiceBeneficiarioBilancio = val;
	}

	////{PROTECTED REGION ID(R-1851987833) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBCodiceBeneficiarioBilancio. 
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
	public java.lang.String getDBCodiceBeneficiarioBilancio() {
		return DBCodiceBeneficiarioBilancio;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBDenominazione = null;

	/**
	 * @generated
	 */
	public void setDBDenominazione(java.lang.String val) {
		DBDenominazione = val;
	}

	////{PROTECTED REGION ID(R1765924195) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBDenominazione. 
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
	public java.lang.String getDBDenominazione() {
		return DBDenominazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBRagioneSocialeAgg = null;

	/**
	 * @generated
	 */
	public void setDBRagioneSocialeAgg(java.lang.String val) {
		DBRagioneSocialeAgg = val;
	}

	////{PROTECTED REGION ID(R2105780059) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBRagioneSocialeAgg. 
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
	public java.lang.String getDBRagioneSocialeAgg() {
		return DBRagioneSocialeAgg;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBIndirizzo = null;

	/**
	 * @generated
	 */
	public void setDBIndirizzo(java.lang.String val) {
		DBIndirizzo = val;
	}

	////{PROTECTED REGION ID(R-367827157) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBIndirizzo. 
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
	public java.lang.String getDBIndirizzo() {
		return DBIndirizzo;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBComune = null;

	/**
	 * @generated
	 */
	public void setDBComune(java.lang.String val) {
		DBComune = val;
	}

	////{PROTECTED REGION ID(R1706220334) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBComune. 
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
	public java.lang.String getDBComune() {
		return DBComune;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBProvincia = null;

	/**
	 * @generated
	 */
	public void setDBProvincia(java.lang.String val) {
		DBProvincia = val;
	}

	////{PROTECTED REGION ID(R-217200278) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBProvincia. 
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
	public java.lang.String getDBProvincia() {
		return DBProvincia;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBCodiceFiscale = null;

	/**
	 * @generated
	 */
	public void setDBCodiceFiscale(java.lang.String val) {
		DBCodiceFiscale = val;
	}

	////{PROTECTED REGION ID(R-1675718639) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBCodiceFiscale. 
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
	public java.lang.String getDBCodiceFiscale() {
		return DBCodiceFiscale;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBPartitaIVA = null;

	/**
	 * @generated
	 */
	public void setDBPartitaIVA(java.lang.String val) {
		DBPartitaIVA = val;
	}

	////{PROTECTED REGION ID(R1682587636) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBPartitaIVA. 
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
	public java.lang.String getDBPartitaIVA() {
		return DBPartitaIVA;
	}

	/**
	 * @generated
	 */
	private java.util.Date DBDataInserimento = null;

	/**
	 * @generated
	 */
	public void setDBDataInserimento(java.util.Date val) {
		DBDataInserimento = val;
	}

	////{PROTECTED REGION ID(R1995638486) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBDataInserimento. 
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
	public java.util.Date getDBDataInserimento() {
		return DBDataInserimento;
	}

	/**
	 * @generated
	 */
	private java.util.Date DBDataAggiornamento = null;

	/**
	 * @generated
	 */
	public void setDBDataAggiornamento(java.util.Date val) {
		DBDataAggiornamento = val;
	}

	////{PROTECTED REGION ID(R-891186074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBDataAggiornamento. 
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
	public java.util.Date getDBDataAggiornamento() {
		return DBDataAggiornamento;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBEmail = null;

	/**
	 * @generated
	 */
	public void setDBEmail(java.lang.String val) {
		DBEmail = val;
	}

	////{PROTECTED REGION ID(R749551577) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBEmail. 
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
	public java.lang.String getDBEmail() {
		return DBEmail;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBTelefono = null;

	/**
	 * @generated
	 */
	public void setDBTelefono(java.lang.String val) {
		DBTelefono = val;
	}

	////{PROTECTED REGION ID(R-1765494377) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBTelefono. 
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
	public java.lang.String getDBTelefono() {
		return DBTelefono;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBFax = null;

	/**
	 * @generated
	 */
	public void setDBFax(java.lang.String val) {
		DBFax = val;
	}

	////{PROTECTED REGION ID(R-1129944422) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBFax. 
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
	public java.lang.String getDBFax() {
		return DBFax;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBIndirizzoSecondaria = null;

	/**
	 * @generated
	 */
	public void setDBIndirizzoSecondaria(java.lang.String val) {
		DBIndirizzoSecondaria = val;
	}

	////{PROTECTED REGION ID(R1153713416) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBIndirizzoSecondaria. 
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
	public java.lang.String getDBIndirizzoSecondaria() {
		return DBIndirizzoSecondaria;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBComuneSecondaria = null;

	/**
	 * @generated
	 */
	public void setDBComuneSecondaria(java.lang.String val) {
		DBComuneSecondaria = val;
	}

	////{PROTECTED REGION ID(R1699795275) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBComuneSecondaria. 
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
	public java.lang.String getDBComuneSecondaria() {
		return DBComuneSecondaria;
	}

	/**
	 * @generated
	 */
	private java.lang.String DBProvinciaSecondaria = null;

	/**
	 * @generated
	 */
	public void setDBProvinciaSecondaria(java.lang.String val) {
		DBProvinciaSecondaria = val;
	}

	////{PROTECTED REGION ID(R234103943) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DBProvinciaSecondaria. 
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
	public java.lang.String getDBProvinciaSecondaria() {
		return DBProvinciaSecondaria;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRTipoSoggetto = null;

	/**
	 * @generated
	 */
	public void setDRTipoSoggetto(java.lang.String val) {
		DRTipoSoggetto = val;
	}

	////{PROTECTED REGION ID(R1895828013) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRTipoSoggetto. 
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
	public java.lang.String getDRTipoSoggetto() {
		return DRTipoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRTipoRitenuta = null;

	/**
	 * @generated
	 */
	public void setDRTipoRitenuta(java.lang.String val) {
		DRTipoRitenuta = val;
	}

	////{PROTECTED REGION ID(R-506436125) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRTipoRitenuta. 
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
	public java.lang.String getDRTipoRitenuta() {
		return DRTipoRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRAliquotaRitenuta = null;

	/**
	 * @generated
	 */
	public void setDRAliquotaRitenuta(java.lang.String val) {
		DRAliquotaRitenuta = val;
	}

	////{PROTECTED REGION ID(R2011009737) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRAliquotaRitenuta. 
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
	public java.lang.String getDRAliquotaRitenuta() {
		return DRAliquotaRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRSommeNonSoggette = null;

	/**
	 * @generated
	 */
	public void setDRSommeNonSoggette(java.lang.String val) {
		DRSommeNonSoggette = val;
	}

	////{PROTECTED REGION ID(R-742315885) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRSommeNonSoggette. 
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
	public java.lang.String getDRSommeNonSoggette() {
		return DRSommeNonSoggette;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRSituazioneINPS = null;

	/**
	 * @generated
	 */
	public void setDRSituazioneINPS(java.lang.String val) {
		DRSituazioneINPS = val;
	}

	////{PROTECTED REGION ID(R734566152) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRSituazioneINPS. 
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
	public java.lang.String getDRSituazioneINPS() {
		return DRSituazioneINPS;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRINPSaltraCassa = null;

	/**
	 * @generated
	 */
	public void setDRINPSaltraCassa(java.lang.String val) {
		DRINPSaltraCassa = val;
	}

	////{PROTECTED REGION ID(R932245094) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRINPSaltraCassa. 
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
	public java.lang.String getDRINPSaltraCassa() {
		return DRINPSaltraCassa;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRAttivitaINPS = null;

	/**
	 * @generated
	 */
	public void setDRAttivitaINPS(java.lang.String val) {
		DRAttivitaINPS = val;
	}

	////{PROTECTED REGION ID(R-1470070429) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRAttivitaINPS. 
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
	public java.lang.String getDRAttivitaINPS() {
		return DRAttivitaINPS;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRAltraCassaPrevid = null;

	/**
	 * @generated
	 */
	public void setDRAltraCassaPrevid(java.lang.String val) {
		DRAltraCassaPrevid = val;
	}

	////{PROTECTED REGION ID(R-1255236532) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRAltraCassaPrevid. 
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
	public java.lang.String getDRAltraCassaPrevid() {
		return DRAltraCassaPrevid;
	}

	/**
	 * @generated
	 */
	private java.lang.String DRRischioINAIL = null;

	/**
	 * @generated
	 */
	public void setDRRischioINAIL(java.lang.String val) {
		DRRischioINAIL = val;
	}

	////{PROTECTED REGION ID(R-1622412277) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRRischioINAIL. 
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
	public java.lang.String getDRRischioINAIL() {
		return DRRischioINAIL;
	}

	/**
	 * @generated
	 */
	private java.util.Date DRPeriodoAttivitaDal = null;

	/**
	 * @generated
	 */
	public void setDRPeriodoAttivitaDal(java.util.Date val) {
		DRPeriodoAttivitaDal = val;
	}

	////{PROTECTED REGION ID(R-323121300) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRPeriodoAttivitaDal. 
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
	public java.util.Date getDRPeriodoAttivitaDal() {
		return DRPeriodoAttivitaDal;
	}

	/**
	 * @generated
	 */
	private java.util.Date DRPeriodoAttivitaAL = null;

	/**
	 * @generated
	 */
	public void setDRPeriodoAttivitaAL(java.util.Date val) {
		DRPeriodoAttivitaAL = val;
	}

	////{PROTECTED REGION ID(R-841707378) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo DRPeriodoAttivitaAL. 
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
	public java.util.Date getDRPeriodoAttivitaAL() {
		return DRPeriodoAttivitaAL;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean esito = null;

	/**
	 * @generated
	 */
	public void setEsito(java.lang.Boolean val) {
		esito = val;
	}

	////{PROTECTED REGION ID(R-1097974255) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esito. 
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
	public java.lang.Boolean getEsito() {
		return esito;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumentoSpesa(java.lang.String val) {
		numeroDocumentoSpesa = val;
	}

	////{PROTECTED REGION ID(R-456501085) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumentoSpesa. 
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
	public java.lang.String getNumeroDocumentoSpesa() {
		return numeroDocumentoSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoDocumento = null;

	/**
	 * @generated
	 */
	public void setDescStatoDocumento(java.lang.String val) {
		descStatoDocumento = val;
	}

	////{PROTECTED REGION ID(R-1785034673) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoDocumento. 
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
	public java.lang.String getDescStatoDocumento() {
		return descStatoDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceErrore = null;

	/**
	 * @generated
	 */
	public void setCodiceErrore(java.lang.String val) {
		codiceErrore = val;
	}

	////{PROTECTED REGION ID(R-911241707) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceErrore. 
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
	public java.lang.String getCodiceErrore() {
		return codiceErrore;
	}

	/**
	 * @generated
	 */
	private java.lang.String descStatoDocumentoContabilia = null;

	/**
	 * @generated
	 */
	public void setDescStatoDocumentoContabilia(java.lang.String val) {
		descStatoDocumentoContabilia = val;
	}

	////{PROTECTED REGION ID(R-843513731) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descStatoDocumentoContabilia. 
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
	public java.lang.String getDescStatoDocumentoContabilia() {
		return descStatoDocumentoContabilia;
	}

	/*PROTECTED REGION ID(R-440316698) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
