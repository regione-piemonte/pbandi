/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R1616970756) ENABLED START////}
/**
 * Inserire qui la documentazione della classe LiquidazioneDTO.
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
public class LiquidazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idAttoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdAttoLiquidazione(java.lang.Long val) {
		idAttoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R289143967) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idAttoLiquidazione. 
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
	public java.lang.Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idCausaleLiquidazione = null;

	/**
	 * @generated
	 */
	public void setIdCausaleLiquidazione(java.lang.Long val) {
		idCausaleLiquidazione = val;
	}

	////{PROTECTED REGION ID(R26701425) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idCausaleLiquidazione. 
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
	public java.lang.Long getIdCausaleLiquidazione() {
		return idCausaleLiquidazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percLiquidazioneEffettiva = null;

	/**
	 * @generated
	 */
	public void setPercLiquidazioneEffettiva(java.lang.Double val) {
		percLiquidazioneEffettiva = val;
	}

	////{PROTECTED REGION ID(R-1023456968) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percLiquidazioneEffettiva. 
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
	public java.lang.Double getPercLiquidazioneEffettiva() {
		return percLiquidazioneEffettiva;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoLiquidazioneEffettiva = null;

	/**
	 * @generated
	 */
	public void setImportoLiquidazioneEffettiva(java.lang.Double val) {
		importoLiquidazioneEffettiva = val;
	}

	////{PROTECTED REGION ID(R941720252) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoLiquidazioneEffettiva. 
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
	public java.lang.Double getImportoLiquidazioneEffettiva() {
		return importoLiquidazioneEffettiva;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setIdModalitaAgevolazione(java.lang.Long val) {
		idModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R80741810) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idModalitaAgevolazione. 
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
	public java.lang.Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codCausaleLiquidazione = null;

	/**
	 * @generated
	 */
	public void setCodCausaleLiquidazione(java.lang.String val) {
		codCausaleLiquidazione = val;
	}

	////{PROTECTED REGION ID(R745561468) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codCausaleLiquidazione. 
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
	public java.lang.String getCodCausaleLiquidazione() {
		return codCausaleLiquidazione;
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

	////{PROTECTED REGION ID(R-427620605) ENABLED START////}
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
	private java.lang.Long idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		idSoggetto = val;
	}

	////{PROTECTED REGION ID(R1909266149) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSoggetto. 
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
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long idSettoreEnte = null;

	/**
	 * @generated
	 */
	public void setIdSettoreEnte(java.lang.Long val) {
		idSettoreEnte = val;
	}

	////{PROTECTED REGION ID(R2033554603) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idSettoreEnte. 
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
	public java.lang.Long getIdSettoreEnte() {
		return idSettoreEnte;
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

	////{PROTECTED REGION ID(R-1897470956) ENABLED START////}
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
	private java.lang.Long idStatoAtto = null;

	/**
	 * @generated
	 */
	public void setIdStatoAtto(java.lang.Long val) {
		idStatoAtto = val;
	}

	////{PROTECTED REGION ID(R-1468334134) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idStatoAtto. 
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
	public java.lang.Long getIdStatoAtto() {
		return idStatoAtto;
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

	////{PROTECTED REGION ID(R-1070039838) ENABLED START////}
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
	@Override
	public String toString() {
		return "LiquidazioneDTO [idAttoLiquidazione=" + idAttoLiquidazione + ", idCausaleLiquidazione="
				+ idCausaleLiquidazione + ", percLiquidazioneEffettiva=" + percLiquidazioneEffettiva
				+ ", importoLiquidazioneEffettiva=" + importoLiquidazioneEffettiva + ", idModalitaAgevolazione="
				+ idModalitaAgevolazione + ", codCausaleLiquidazione=" + codCausaleLiquidazione + ", idProgetto="
				+ idProgetto + ", idSoggetto=" + idSoggetto + ", idSettoreEnte=" + idSettoreEnte + ", descStatoAtto="
				+ descStatoAtto + ", idStatoAtto=" + idStatoAtto + ", numeroDocumentoSpesa=" + numeroDocumentoSpesa
				+ "]";
	}
	
	////{PROTECTED REGION END////}
	public java.lang.String getNumeroDocumentoSpesa() {
		return numeroDocumentoSpesa;
	}

	/*PROTECTED REGION ID(R30590045) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
