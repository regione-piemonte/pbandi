/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione;

////{PROTECTED REGION ID(R-1313811808) ENABLED START////}
/**
 * Inserire qui la documentazione della classe IntegrazioneSpesaDTO.
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
public class IntegrazioneSpesaDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idIntegrazioneSpesa = null;

	/**
	 * @generated
	 */
	public void setIdIntegrazioneSpesa(java.lang.Long val) {
		idIntegrazioneSpesa = val;
	}

	////{PROTECTED REGION ID(R-59470940) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idIntegrazioneSpesa. 
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
	public java.lang.Long getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
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

	////{PROTECTED REGION ID(R1085661483) ENABLED START////}
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
	private java.lang.String descrizione = null;

	/**
	 * @generated
	 */
	public void setDescrizione(java.lang.String val) {
		descrizione = val;
	}

	////{PROTECTED REGION ID(R-2101555537) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizione. 
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
	public java.lang.String getDescrizione() {
		return descrizione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataRichiesta = null;

	/**
	 * @generated
	 */
	public void setDataRichiesta(java.util.Date val) {
		dataRichiesta = val;
	}

	////{PROTECTED REGION ID(R151301374) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataRichiesta. 
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
	public java.util.Date getDataRichiesta() {
		return dataRichiesta;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInvio = null;

	/**
	 * @generated
	 */
	public void setDataInvio(java.util.Date val) {
		dataInvio = val;
	}

	////{PROTECTED REGION ID(R169928877) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInvio. 
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
	public java.util.Date getDataInvio() {
		return dataInvio;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteRichiesta = null;

	/**
	 * @generated
	 */
	public void setIdUtenteRichiesta(java.lang.Long val) {
		idUtenteRichiesta = val;
	}

	////{PROTECTED REGION ID(R1381731060) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteRichiesta. 
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
	public java.lang.Long getIdUtenteRichiesta() {
		return idUtenteRichiesta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idUtenteInvio = null;

	/**
	 * @generated
	 */
	public void setIdUtenteInvio(java.lang.Long val) {
		idUtenteInvio = val;
	}

	////{PROTECTED REGION ID(R1733374371) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idUtenteInvio. 
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
	public java.lang.Long getIdUtenteInvio() {
		return idUtenteInvio;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataDichiarazione = null;

	/**
	 * @generated
	 */
	public void setDataDichiarazione(java.util.Date val) {
		dataDichiarazione = val;
	}

	////{PROTECTED REGION ID(R1851413852) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataDichiarazione. 
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
	public java.util.Date getDataDichiarazione() {
		return dataDichiarazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.AllegatoIntegrazioneSpesaDTO[] allegati = null;

	/**
	 * @generated
	 */
	public void setAllegati(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.AllegatoIntegrazioneSpesaDTO[] val) {
		allegati = val;
	}

	////{PROTECTED REGION ID(R989997139) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.AllegatoIntegrazioneSpesaDTO[] getAllegati() {
		return allegati;
	}

	/*PROTECTED REGION ID(R-291499285) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {
		String s = "";
		s += "\t\n" + this.getClass().getName() + "\t\n";
		s += " idIntegrazioneSpesa: " + idIntegrazioneSpesa + "\t\n";
		s += " idDichiarazioneSpesa: " + idDichiarazioneSpesa + "\t\n";
		s += " descrizione: " + descrizione + "\t\n";
		s += " dataRichiesta: " + dataRichiesta + "\t\n";
		s += " dataInvio: " + dataInvio + "\t\n";
		s += " idUtenteRichiesta: " + idUtenteRichiesta + "\t\n";
		s += " idUtenteInvio: " + idUtenteInvio + "\t\n";
		s += " dataDichiarazione: " + dataDichiarazione + "\t\n";
		String s1 = "";
		if (allegati != null) {
			for (int i = 0; i < allegati.length; i++) {
				s1 += allegati[i].getNomeFile() + " "
						+ allegati[i].getIdDocumentoIndex() + " - ";
			}
		}
		s += " allegati: " + s1 + "\t\n";
		return s;
	}
	/*PROTECTED REGION END*/
}
