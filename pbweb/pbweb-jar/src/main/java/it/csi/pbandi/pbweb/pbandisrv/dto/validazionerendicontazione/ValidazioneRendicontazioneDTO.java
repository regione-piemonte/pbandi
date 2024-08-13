/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

import java.util.Arrays;

////{PROTECTED REGION ID(R1292740585) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ValidazioneRendicontazioneDTO.
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
public class ValidazioneRendicontazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private byte[] bytesFile = null;

	/**
	 * @generated
	 */
	public void setBytesFile(byte[] val) {
		bytesFile = val;
	}

	////{PROTECTED REGION ID(R-671536450) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo bytesFile. 
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
	public byte[] getBytesFile() {
		return bytesFile;
	}

	/**
	 * @generated
	 */
	private byte[] bytesFileVerbale = null;

	/**
	 * @generated
	 */
	public void setBytesFileVerbale(byte[] val) {
		bytesFileVerbale = val;
	}

	////{PROTECTED REGION ID(R1888010109) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo bytesFileVerbale. 
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
	public byte[] getBytesFileVerbale() {
		return bytesFileVerbale;
	}

	/**
	 * @generated
	 */
	private java.lang.String cfBeneficiario = null;

	/**
	 * @generated
	 */
	public void setCfBeneficiario(java.lang.String val) {
		cfBeneficiario = val;
	}

	////{PROTECTED REGION ID(R871081062) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo cfBeneficiario. 
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
	public java.lang.String getCfBeneficiario() {
		return cfBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.String codiceProgetto = null;

	/**
	 * @generated
	 */
	public void setCodiceProgetto(java.lang.String val) {
		codiceProgetto = val;
	}

	////{PROTECTED REGION ID(R1940377888) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codiceProgetto. 
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
	public java.lang.String getCodiceProgetto() {
		return codiceProgetto;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataChiusura = null;

	/**
	 * @generated
	 */
	public void setDataChiusura(java.util.Date val) {
		dataChiusura = val;
	}

	////{PROTECTED REGION ID(R1654139925) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataChiusura. 
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
	public java.util.Date getDataChiusura() {
		return dataChiusura;
	}

	/**
	 * @generated
	 */
	private java.lang.String flagRichiestaIntegrativa = null;

	/**
	 * @generated
	 */
	public void setFlagRichiestaIntegrativa(java.lang.String val) {
		flagRichiestaIntegrativa = val;
	}

	////{PROTECTED REGION ID(R858876877) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo flagRichiestaIntegrativa. 
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
	public java.lang.String getFlagRichiestaIntegrativa() {
		return flagRichiestaIntegrativa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idBandoLinea = null;

	/**
	 * @generated
	 */
	public void setIdBandoLinea(java.lang.Long val) {
		idBandoLinea = val;
	}

	////{PROTECTED REGION ID(R949398551) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idBandoLinea. 
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
	public java.lang.Long getIdBandoLinea() {
		return idBandoLinea;
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

	////{PROTECTED REGION ID(R-1621639441) ENABLED START////}
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

	/**
	 * @generated
	 */
	private java.lang.Long idDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		idDocumentoDiSpesa = val;
	}

	////{PROTECTED REGION ID(R-1299563007) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idDocumentoDiSpesa. 
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
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
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

	////{PROTECTED REGION ID(R-1164717912) ENABLED START////}
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
	private java.lang.Long idSoggettoBen = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoBen(java.lang.Long val) {
		idSoggettoBen = val;
	}

	////{PROTECTED REGION ID(R1997922209) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoBen. 
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
	public java.lang.Long getIdSoggettoBen() {
		return idSoggettoBen;
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

	////{PROTECTED REGION ID(R-653946082) ENABLED START////}
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
	private java.lang.String nomeFileVerbale = null;

	/**
	 * @generated
	 */
	public void setNomeFileVerbale(java.lang.String val) {
		nomeFileVerbale = val;
	}

	////{PROTECTED REGION ID(R-660067555) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo nomeFileVerbale. 
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
	public java.lang.String getNomeFileVerbale() {
		return nomeFileVerbale;
	}

	/**
	 * @generated
	 */
	private java.lang.String note = null;

	/**
	 * @generated
	 */
	public void setNote(java.lang.String val) {
		note = val;
	}

	////{PROTECTED REGION ID(R1021918043) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo note. 
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
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO[] pagamenti = null;

	/**
	 * @generated
	 */
	public void setPagamenti(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO[] val) {
		pagamenti = val;
	}

	////{PROTECTED REGION ID(R2144354647) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo pagamenti. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO[] getPagamenti() {
		return pagamenti;
	}

	/**
	 * @generated
	 */
	private java.lang.String checkListHtml = null;

	/**
	 * @generated
	 */
	public void setCheckListHtml(java.lang.String val) {
		checkListHtml = val;
	}

	////{PROTECTED REGION ID(R831695176) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo checkListHtml. 
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
	public java.lang.String getCheckListHtml() {
		return checkListHtml;
	}

	@Override
	public String toString() {
		String ret= "ValidazioneRendicontazioneDTO [ cfBeneficiario=" + cfBeneficiario + ", codiceProgetto="
				+ codiceProgetto + ", dataChiusura=" + dataChiusura + ", flagRichiestaIntegrativa="
				+ flagRichiestaIntegrativa + ", idBandoLinea=" + idBandoLinea + ", idDichiarazioneDiSpesa="
				+ idDichiarazioneDiSpesa + ", idDocumentoDiSpesa=" + idDocumentoDiSpesa + ", idProgetto=" + idProgetto
				+ ", idSoggettoBen=" + idSoggettoBen + ", nomeFile=" + nomeFile + ", nomeFileVerbale=" + nomeFileVerbale
				+ ", note=" + note + ", pagamenti=" + Arrays.toString(pagamenti) ;
		
		if(checkListHtml!=null) {
			ret = ret + ", checkListHtml valorizzato con " + checkListHtml.length() + " caratteri]";
		}else {
			ret = ret + ", checkListHtml nullo";
		}
		if(bytesFile!=null) {
			ret = ret + ", bytesFile non nullo ";
		}else {
			ret = ret + ", bytesFile nullo ";
		}
		if(bytesFileVerbale!=null) {
			ret = ret + ", bytesFileVerbale non nullo";
		}else {
			ret = ret +", bytesFileVerbale  nullo";
		}
		
		ret = ret + "]";
		
		return ret;
	}

	/*PROTECTED REGION ID(R2106595544) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
