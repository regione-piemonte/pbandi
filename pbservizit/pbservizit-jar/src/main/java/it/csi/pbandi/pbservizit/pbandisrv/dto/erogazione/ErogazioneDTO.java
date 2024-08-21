/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione;

////{PROTECTED REGION ID(R543513902) ENABLED START////}
/**
 * Inserire qui la documentazione della classe ErogazioneDTO.
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
public class ErogazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long idErogazione = null;

	/**
	 * @generated
	 */
	public void setIdErogazione(java.lang.Long val) {
		idErogazione = val;
	}

	////{PROTECTED REGION ID(R1425512516) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo idErogazione. 
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
	public java.lang.Long getIdErogazione() {
		return idErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setCodCausaleErogazione(java.lang.String val) {
		codCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-1318023313) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codCausaleErogazione. 
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
	public java.lang.String getCodCausaleErogazione() {
		return codCausaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descrizioneCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setDescrizioneCausaleErogazione(java.lang.String val) {
		descrizioneCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-65943944) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descrizioneCausaleErogazione. 
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
	public java.lang.String getDescrizioneCausaleErogazione() {
		return descrizioneCausaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualeErogazioneEffettiva = null;

	/**
	 * @generated
	 */
	public void setPercentualeErogazioneEffettiva(java.lang.Double val) {
		percentualeErogazioneEffettiva = val;
	}

	////{PROTECTED REGION ID(R-913461155) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeErogazioneEffettiva. 
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
	public java.lang.Double getPercentualeErogazioneEffettiva() {
		return percentualeErogazioneEffettiva;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualeErogazioneFinanziaria = null;

	/**
	 * @generated
	 */
	public void setPercentualeErogazioneFinanziaria(java.lang.Double val) {
		percentualeErogazioneFinanziaria = val;
	}

	////{PROTECTED REGION ID(R-569292519) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeErogazioneFinanziaria. 
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
	public java.lang.Double getPercentualeErogazioneFinanziaria() {
		return percentualeErogazioneFinanziaria;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoErogazioneEffettivo = null;

	/**
	 * @generated
	 */
	public void setImportoErogazioneEffettivo(java.lang.Double val) {
		importoErogazioneEffettivo = val;
	}

	////{PROTECTED REGION ID(R128111819) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogazioneEffettivo. 
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
	public java.lang.Double getImportoErogazioneEffettivo() {
		return importoErogazioneEffettivo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoErogazioneFinanziario = null;

	/**
	 * @generated
	 */
	public void setImportoErogazioneFinanziario(java.lang.Double val) {
		importoErogazioneFinanziario = val;
	}

	////{PROTECTED REGION ID(R-345057913) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoErogazioneFinanziario. 
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
	public java.lang.Double getImportoErogazioneFinanziario() {
		return importoErogazioneFinanziario;
	}

	/**
	 * @generated
	 */
	private java.lang.String codModalitaErogazione = null;

	/**
	 * @generated
	 */
	public void setCodModalitaErogazione(java.lang.String val) {
		codModalitaErogazione = val;
	}

	////{PROTECTED REGION ID(R193126254) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codModalitaErogazione. 
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
	public java.lang.String getCodModalitaErogazione() {
		return codModalitaErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setCodModalitaAgevolazione(java.lang.String val) {
		codModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R-1820463203) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codModalitaAgevolazione. 
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
	public java.lang.String getCodModalitaAgevolazione() {
		return codModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String codDirezione = null;

	/**
	 * @generated
	 */
	public void setCodDirezione(java.lang.String val) {
		codDirezione = val;
	}

	////{PROTECTED REGION ID(R1521158133) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo codDirezione. 
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
	public java.lang.String getCodDirezione() {
		return codDirezione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataContabile = null;

	/**
	 * @generated
	 */
	public void setDataContabile(java.util.Date val) {
		dataContabile = val;
	}

	////{PROTECTED REGION ID(R263665911) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataContabile. 
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
	public java.util.Date getDataContabile() {
		return dataContabile;
	}

	/**
	 * @generated
	 */
	private java.lang.String numeroRiferimento = null;

	/**
	 * @generated
	 */
	public void setNumeroRiferimento(java.lang.String val) {
		numeroRiferimento = val;
	}

	////{PROTECTED REGION ID(R1307147226) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroRiferimento. 
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
	public java.lang.String getNumeroRiferimento() {
		return numeroRiferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.String noteErogazione = null;

	/**
	 * @generated
	 */
	public void setNoteErogazione(java.lang.String val) {
		noteErogazione = val;
	}

	////{PROTECTED REGION ID(R2004453083) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo noteErogazione. 
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
	public java.lang.String getNoteErogazione() {
		return noteErogazione;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.FideiussioneDTO[] fideiussioni = null;

	/**
	 * @generated
	 */
	public void setFideiussioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.FideiussioneDTO[] val) {
		fideiussioni = val;
	}

	////{PROTECTED REGION ID(R-949355361) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fideiussioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.FideiussioneDTO[] getFideiussioni() {
		return fideiussioni;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO[] erogazioni = null;

	/**
	 * @generated
	 */
	public void setErogazioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO[] val) {
		erogazioni = val;
	}

	////{PROTECTED REGION ID(R-481081267) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo erogazioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ErogazioneDTO[] getErogazioni() {
		return erogazioni;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.RichiestaErogazioneDTO[] richiesteErogazioni = null;

	/**
	 * @generated
	 */
	public void setRichiesteErogazioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.RichiestaErogazioneDTO[] val) {
		richiesteErogazioni = val;
	}

	////{PROTECTED REGION ID(R488945149) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo richiesteErogazioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.RichiestaErogazioneDTO[] getRichiesteErogazioni() {
		return richiesteErogazioni;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.SpesaProgettoDTO spesaProgetto = null;

	/**
	 * @generated
	 */
	public void setSpesaProgetto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.SpesaProgettoDTO val) {
		spesaProgetto = val;
	}

	////{PROTECTED REGION ID(R-2017976468) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo spesaProgetto. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.SpesaProgettoDTO getSpesaProgetto() {
		return spesaProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleErogato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleErogato(java.lang.Double val) {
		importoTotaleErogato = val;
	}

	////{PROTECTED REGION ID(R-1433060038) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleErogato. 
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
	public java.lang.Double getImportoTotaleErogato() {
		return importoTotaleErogato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleResiduo = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleResiduo(java.lang.Double val) {
		importoTotaleResiduo = val;
	}

	////{PROTECTED REGION ID(R1146130840) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleResiduo. 
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
	public java.lang.Double getImportoTotaleResiduo() {
		return importoTotaleResiduo;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleRichiesto = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleRichiesto(java.lang.Double val) {
		importoTotaleRichiesto = val;
	}

	////{PROTECTED REGION ID(R-1038221447) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleRichiesto. 
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
	public java.lang.Double getImportoTotaleRichiesto() {
		return importoTotaleRichiesto;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaAgevolazione = null;

	/**
	 * @generated
	 */
	public void setDescModalitaAgevolazione(java.lang.String val) {
		descModalitaAgevolazione = val;
	}

	////{PROTECTED REGION ID(R1558014546) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaAgevolazione. 
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
	public java.lang.String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descTipoDirezione = null;

	/**
	 * @generated
	 */
	public void setDescTipoDirezione(java.lang.String val) {
		descTipoDirezione = val;
	}

	////{PROTECTED REGION ID(R1491495692) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descTipoDirezione. 
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
	public java.lang.String getDescTipoDirezione() {
		return descTipoDirezione;
	}

	/**
	 * @generated
	 */
	private java.lang.String descModalitaErogazione = null;

	/**
	 * @generated
	 */
	public void setDescModalitaErogazione(java.lang.String val) {
		descModalitaErogazione = val;
	}

	////{PROTECTED REGION ID(R813400931) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descModalitaErogazione. 
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
	public java.lang.String getDescModalitaErogazione() {
		return descModalitaErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleRecuperi = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleRecuperi(java.lang.Double val) {
		importoTotaleRecuperi = val;
	}

	////{PROTECTED REGION ID(R723675822) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleRecuperi. 
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
	public java.lang.Double getImportoTotaleRecuperi() {
		return importoTotaleRecuperi;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ModalitaAgevolazioneErogazioneDTO[] modalitaAgevolazioni = null;

	/**
	 * @generated
	 */
	public void setModalitaAgevolazioni(
			it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ModalitaAgevolazioneErogazioneDTO[] val) {
		modalitaAgevolazioni = val;
	}

	////{PROTECTED REGION ID(R1345712421) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo modalitaAgevolazioni. 
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
	public it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.ModalitaAgevolazioneErogazioneDTO[] getModalitaAgevolazioni() {
		return modalitaAgevolazioni;
	}

	/*PROTECTED REGION ID(R-1436987235) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
