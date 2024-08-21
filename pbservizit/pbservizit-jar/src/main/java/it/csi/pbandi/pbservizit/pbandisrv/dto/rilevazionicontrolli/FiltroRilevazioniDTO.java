/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli;

////{PROTECTED REGION ID(R1618704167) ENABLED START////}
/**
 * Inserire qui la documentazione della classe FiltroRilevazioniDTO.
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
public class FiltroRilevazioniDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idAnnoContabile = null;

	/**
	 * @generated
	 */
	public void setIdAnnoContabile(java.lang.Long val) {
		idAnnoContabile = val;
	}

	////{PROTECTED REGION ID(R-1647801985) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAnnoContabile. 
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
	public java.lang.Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAutoritaControllante = null;

	/**
	 * @generated
	 */
	public void setIdAutoritaControllante(java.lang.Long val) {
		idAutoritaControllante = val;
	}

	////{PROTECTED REGION ID(R19757282) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAutoritaControllante. 
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
	public java.lang.Long getIdAutoritaControllante() {
		return idAutoritaControllante;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoControllo = null;

	/**
	 * @generated
	 */
	public void setTipoControllo(java.lang.String val) {
		tipoControllo = val;
	}

	////{PROTECTED REGION ID(R-1410377979) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoControllo. 
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
	public java.lang.String getTipoControllo() {
		return tipoControllo;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataCampione = null;

	/**
	 * @generated
	 */
	public void setDataCampione(java.util.Date val) {
		dataCampione = val;
	}

	////{PROTECTED REGION ID(R79673807) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataCampione. 
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
	public java.util.Date getDataCampione() {
		return dataCampione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idLineaIntervento = null;

	/**
	 * @generated
	 */
	public void setIdLineaIntervento(java.lang.Long val) {
		idLineaIntervento = val;
	}

	////{PROTECTED REGION ID(R-1821039895) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idLineaIntervento. 
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
	public java.lang.Long getIdLineaIntervento() {
		return idLineaIntervento;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean isScartato = null;

	/**
	 * @generated
	 */
	public void setIsScartato(java.lang.Boolean val) {
		isScartato = val;
	}

	////{PROTECTED REGION ID(R-594252454) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo isScartato. 
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
	public java.lang.Boolean getIsScartato() {
		return isScartato;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idAsse = null;

	/**
	 * @generated
	 */
	public void setIdAsse(java.lang.Long val) {
		idAsse = val;
	}

	////{PROTECTED REGION ID(R-1515810906) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAsse. 
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
	public java.lang.Long getIdAsse() {
		return idAsse;
	}

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

	////{PROTECTED REGION ID(R254884728) ENABLED START////}
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
	private java.lang.String descAnnoContabile = null;

	/**
	 * @generated
	 */
	public void setDescAnnoContabile(java.lang.String val) {
		descAnnoContabile = val;
	}

	////{PROTECTED REGION ID(R1629392265) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descAnnoContabile. 
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
	public java.lang.String getDescAnnoContabile() {
		return descAnnoContabile;
	}

	/*PROTECTED REGION ID(R1706756804) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FiltroRilevazioniDTO [idAnnoContabile=");
		builder.append(idAnnoContabile);
		builder.append(", idAutoritaControllante=");
		builder.append(idAutoritaControllante);
		builder.append(", tipoControllo=");
		builder.append(tipoControllo);
		builder.append(", dataCampione=");
		builder.append(dataCampione);
		builder.append(", idLineaIntervento=");
		builder.append(idLineaIntervento);
		builder.append(", isScartato=");
		builder.append(isScartato);
		builder.append(", idAsse=");
		builder.append(idAsse);
		builder.append(", idBando=");
		builder.append(idBando);
		builder.append(", descAnnoContabile=");
		builder.append(descAnnoContabile);
		builder.append("]");
		return builder.toString();
	}
	/*PROTECTED REGION END*/
}
