/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio;

////{PROTECTED REGION ID(R315058903) ENABLED START////}
/**
 * Inserire qui la documentazione della classe PreparaAttoDTO.
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
public class PreparaAttoDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String direzioneAtto = null;

	/**
	 * @generated
	 */
	public void setDirezioneAtto(java.lang.String val) {
		direzioneAtto = val;
	}

	////{PROTECTED REGION ID(R1717922678) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo direzioneAtto. 
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
	public java.lang.String getDirezioneAtto() {
		return direzioneAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String settoreAtto = null;

	/**
	 * @generated
	 */
	public void setSettoreAtto(java.lang.String val) {
		settoreAtto = val;
	}

	////{PROTECTED REGION ID(R2085585959) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo settoreAtto. 
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
	public java.lang.String getSettoreAtto() {
		return settoreAtto;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoSoggetto = null;

	/**
	 * @generated
	 */
	public void setTipoSoggetto(java.lang.String val) {
		tipoSoggetto = val;
	}

	////{PROTECTED REGION ID(R-1131395759) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoSoggetto. 
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
	public java.lang.String getTipoSoggetto() {
		return tipoSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String tipoRitenuta = null;

	/**
	 * @generated
	 */
	public void setTipoRitenuta(java.lang.String val) {
		tipoRitenuta = val;
	}

	////{PROTECTED REGION ID(R761307399) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoRitenuta. 
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
	public java.lang.String getTipoRitenuta() {
		return tipoRitenuta;
	}

	/**
	 * @generated
	 */
	private java.lang.Double aliquotaIrpef = null;

	/**
	 * @generated
	 */
	public void setAliquotaIrpef(java.lang.Double val) {
		aliquotaIrpef = val;
	}

	////{PROTECTED REGION ID(R1680144983) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo aliquotaIrpef. 
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
	public java.lang.Double getAliquotaIrpef() {
		return aliquotaIrpef;
	}

	/**
	 * @generated
	 */
	private java.lang.Long datoInps = null;

	/**
	 * @generated
	 */
	public void setDatoInps(java.lang.Long val) {
		datoInps = val;
	}

	////{PROTECTED REGION ID(R-1566977641) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo datoInps. 
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
	public java.lang.Long getDatoInps() {
		return datoInps;
	}

	/**
	 * @generated
	 */
	private java.lang.String inpsAltraCassa = null;

	/**
	 * @generated
	 */
	public void setInpsAltraCassa(java.lang.String val) {
		inpsAltraCassa = val;
	}

	////{PROTECTED REGION ID(R-1174626102) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo inpsAltraCassa. 
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
	public java.lang.String getInpsAltraCassa() {
		return inpsAltraCassa;
	}

	/**
	 * @generated
	 */
	private java.lang.String codAttivita = null;

	/**
	 * @generated
	 */
	public void setCodAttivita(java.lang.String val) {
		codAttivita = val;
	}

	////{PROTECTED REGION ID(R-822540119) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codAttivita. 
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
	public java.lang.String getCodAttivita() {
		return codAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.String codAltraCassa = null;

	/**
	 * @generated
	 */
	public void setCodAltraCassa(java.lang.String val) {
		codAltraCassa = val;
	}

	////{PROTECTED REGION ID(R148409420) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codAltraCassa. 
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
	public java.lang.String getCodAltraCassa() {
		return codAltraCassa;
	}

	/**
	 * @generated
	 */
	private java.lang.String rischioInail = null;

	/**
	 * @generated
	 */
	public void setRischioInail(java.lang.String val) {
		rischioInail = val;
	}

	////{PROTECTED REGION ID(R-353683665) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rischioInail. 
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
	public java.lang.String getRischioInail() {
		return rischioInail;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AttoDiLiquidazioneDTO attoLiquidazione = null;

	/**
	 * @generated
	 */
	public void setAttoLiquidazione(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AttoDiLiquidazioneDTO val) {
		attoLiquidazione = val;
	}

	////{PROTECTED REGION ID(R-1873636553) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo attoLiquidazione. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.AttoDiLiquidazioneDTO getAttoLiquidazione() {
		return attoLiquidazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioCreaAttoDTO beneficiarioBilancio = null;

	/**
	 * @generated
	 */
	public void setBeneficiarioBilancio(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioCreaAttoDTO val) {
		beneficiarioBilancio = val;
	}

	////{PROTECTED REGION ID(R-1054273720) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo beneficiarioBilancio. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.BeneficiarioBilancioCreaAttoDTO getBeneficiarioBilancio() {
		return beneficiarioBilancio;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiPagamentoAttoDTO datiPagamento = null;

	/**
	 * @generated
	 */
	public void setDatiPagamento(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiPagamentoAttoDTO val) {
		datiPagamento = val;
	}

	////{PROTECTED REGION ID(R-727452131) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo datiPagamento. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.DatiPagamentoAttoDTO getDatiPagamento() {
		return datiPagamento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO[] impegni = null;

	/**
	 * @generated
	 */
	public void setImpegni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO[] val) {
		impegni = val;
	}

	////{PROTECTED REGION ID(R2094158066) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo impegni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.liquidazionebilancio.RipartizioneImpegniLiquidazioneDTO[] getImpegni() {
		return impegni;
	}

	/*PROTECTED REGION ID(R167672020) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
