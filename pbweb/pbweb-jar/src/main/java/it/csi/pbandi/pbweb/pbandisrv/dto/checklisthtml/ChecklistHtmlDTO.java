/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml;

import java.util.Arrays;

////{PROTECTED REGION ID(R-989317619) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ChecklistHtmlDTO.
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
public class ChecklistHtmlDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String codStatoTipoDocIndex = null;

	/**
	 * @generated
	 */
	public void setCodStatoTipoDocIndex(java.lang.String val) {
		codStatoTipoDocIndex = val;
	}

	////{PROTECTED REGION ID(R-2057892610) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codStatoTipoDocIndex. 
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
	public java.lang.String getCodStatoTipoDocIndex() {
		return codStatoTipoDocIndex;
	}

	/**
	 * @generated
	 */
	private java.lang.String contentHtml = null;

	/**
	 * @generated
	 */
	public void setContentHtml(java.lang.String val) {
		contentHtml = val;
	}

	////{PROTECTED REGION ID(R195574679) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo contentHtml. 
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
	public java.lang.String getContentHtml() {
		return contentHtml;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idChecklist = null;

	/**
	 * @generated
	 */
	public void setIdChecklist(java.lang.Long val) {
		idChecklist = val;
	}

	////{PROTECTED REGION ID(R815522462) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idChecklist. 
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
	public java.lang.Long getIdChecklist() {
		return idChecklist;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoIndex = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoIndex(java.lang.Long val) {
		idDocumentoIndex = val;
	}

	////{PROTECTED REGION ID(R-1879883642) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoIndex. 
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
	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
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

	////{PROTECTED REGION ID(R-849477172) ENABLED START////}
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
	private java.lang.String soggettoControllore = null;

	/**
	 * @generated
	 */
	public void setSoggettoControllore(java.lang.String val) {
		soggettoControllore = val;
	}

	////{PROTECTED REGION ID(R-1142340320) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo soggettoControllore. 
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
	public java.lang.String getSoggettoControllore() {
		return soggettoControllore;
	}

	/**
	 * @generated
	 */
	private byte[] bytesVerbale = null;

	/**
	 * @generated
	 */
	public void setBytesVerbale(byte[] val) {
		bytesVerbale = val;
	}

	////{PROTECTED REGION ID(R-574855235) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo bytesVerbale. 
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
	public byte[] getBytesVerbale() {
		return bytesVerbale;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO esitoIntermedio = null;

	/**
	 * @generated
	 */
	public void setEsitoIntermedio(
			it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO val) {
		esitoIntermedio = val;
	}

	////{PROTECTED REGION ID(R-1531676481) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoIntermedio. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO getEsitoIntermedio() {
		return esitoIntermedio;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO esitoDefinitivo = null;

	/**
	 * @generated
	 */
	public void setEsitoDefinitivo(
			it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO val) {
		esitoDefinitivo = val;
	}

	////{PROTECTED REGION ID(R-954140074) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo esitoDefinitivo. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoChecklistAffidamentoDTO getEsitoDefinitivo() {
		return esitoDefinitivo;
	}

	/**
	 * @generated
	 */
	private java.lang.String nomeFile = null;

	/**
	 * @generated
	 */
	public void setNomeFile(java.lang.String val) {
		nomeFile = val;
	}

	////{PROTECTED REGION ID(R-2043560638) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFile. 
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
	public java.lang.String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO[] allegati = null;

	/**
	 * @generated
	 */
	public void setAllegati(
			it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO[] val) {
		allegati = val;
	}

	////{PROTECTED REGION ID(R-1620455744) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo allegati. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO[] getAllegati() {
		return allegati;
	}

	@Override
	public String toString() {
		String r = "ChecklistHtmlDTO [codStatoTipoDocIndex=" + codStatoTipoDocIndex 
				+ ", idChecklist=" + idChecklist + ", idDocumentoIndex=" + idDocumentoIndex + ", idProgetto="
				+ idProgetto + ", soggettoControllore=" + soggettoControllore + ", esitoIntermedio=" + esitoIntermedio + ", esitoDefinitivo="
				+ esitoDefinitivo + ", nomeFile=" + nomeFile;
		
		if(contentHtml!=null) {
			r = r + ", contentHtml.length="+contentHtml.length();
		}else {
			r = r + ", contentHtml null";
		}
		
		if(allegati!=null && allegati.length>0) {
			r = r + ", allegati.length="+allegati.length;
		}else {
			r = r + ", allegati null";
		}
		
		if(bytesVerbale!=null && bytesVerbale.length>0) {
			r = r + ", bytesVerbale.length="+bytesVerbale.length;
		}else {
			r = r + ", bytesVerbale null";
		}
		
		return r +  "]";
	}

	
}
