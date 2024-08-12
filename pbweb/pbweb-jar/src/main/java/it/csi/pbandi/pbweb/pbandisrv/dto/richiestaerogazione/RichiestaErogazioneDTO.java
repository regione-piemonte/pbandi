package it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione;

////{PROTECTED REGION ID(R-999195447) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RichiestaErogazioneDTO.
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
public class RichiestaErogazioneDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.Long id = null;

	/**
	 * @generated
	 */
	public void setId(java.lang.Long val) {
		id = val;
	}

	////{PROTECTED REGION ID(R1845853092) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo id. 
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
	public java.lang.Long getId() {
		return id;
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

	////{PROTECTED REGION ID(R103333779) ENABLED START////}
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
	private java.lang.Double percentualeErogazione = null;

	/**
	 * @generated
	 */
	public void setPercentualeErogazione(java.lang.Double val) {
		percentualeErogazione = val;
	}

	////{PROTECTED REGION ID(R-303603236) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeErogazione. 
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
	public java.lang.Double getPercentualeErogazione() {
		return percentualeErogazione;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoRichiesto = null;

	/**
	 * @generated
	 */
	public void setImportoRichiesto(java.lang.Double val) {
		importoRichiesto = val;
	}

	////{PROTECTED REGION ID(R2026171573) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoRichiesto. 
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
	public java.lang.Double getImportoRichiesto() {
		return importoRichiesto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO spesaProgetto = null;

	/**
	 * @generated
	 */
	public void setSpesaProgetto(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO val) {
		spesaProgetto = val;
	}

	////{PROTECTED REGION ID(R1944023985) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO getSpesaProgetto() {
		return spesaProgetto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] fideiussioni = null;

	/**
	 * @generated
	 */
	public void setFideiussioni(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] val) {
		fideiussioni = val;
	}

	////{PROTECTED REGION ID(R9735098) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] getFideiussioni() {
		return fideiussioni;
	}

	/**
	 * @generated
	 */
	private java.lang.Double percentualeLimite = null;

	/**
	 * @generated
	 */
	public void setPercentualeLimite(java.lang.Double val) {
		percentualeLimite = val;
	}

	////{PROTECTED REGION ID(R499009995) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo percentualeLimite. 
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
	public java.lang.Double getPercentualeLimite() {
		return percentualeLimite;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] fideiussioniTipoTrattamento = null;

	/**
	 * @generated
	 */
	public void setFideiussioniTipoTrattamento(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] val) {
		fideiussioniTipoTrattamento = val;
	}

	////{PROTECTED REGION ID(R-1284680379) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo fideiussioniTipoTrattamento. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] getFideiussioniTipoTrattamento() {
		return fideiussioniTipoTrattamento;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] tipoAllegati = null;

	/**
	 * @generated
	 */
	public void setTipoAllegati(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] val) {
		tipoAllegati = val;
	}

	////{PROTECTED REGION ID(R-2039885744) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo tipoAllegati. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] getTipoAllegati() {
		return tipoAllegati;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO estremiBancari = null;

	/**
	 * @generated
	 */
	public void setEstremiBancari(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO val) {
		estremiBancari = val;
	}

	////{PROTECTED REGION ID(R-138926760) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo estremiBancari. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO getEstremiBancari() {
		return estremiBancari;
	}

	/**
	 * @generated
	 */
	private java.lang.String descBreveCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setDescBreveCausaleErogazione(java.lang.String val) {
		descBreveCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-1833029585) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descBreveCausaleErogazione. 
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
	public java.lang.String getDescBreveCausaleErogazione() {
		return descBreveCausaleErogazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtInizioLavori = null;

	/**
	 * @generated
	 */
	public void setDtInizioLavori(java.util.Date val) {
		dtInizioLavori = val;
	}

	////{PROTECTED REGION ID(R-427326662) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtInizioLavori. 
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
	public java.util.Date getDtInizioLavori() {
		return dtInizioLavori;
	}

	/**
	 * @generated
	 */
	private java.lang.String direttoreLavori = null;

	/**
	 * @generated
	 */
	public void setDirettoreLavori(java.lang.String val) {
		direttoreLavori = val;
	}

	////{PROTECTED REGION ID(R-2038900666) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo direttoreLavori. 
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
	public java.lang.String getDirettoreLavori() {
		return direttoreLavori;
	}

	/**
	 * @generated
	 */
	private java.lang.String residenzaDirettoreLavori = null;

	/**
	 * @generated
	 */
	public void setResidenzaDirettoreLavori(java.lang.String val) {
		residenzaDirettoreLavori = val;
	}

	////{PROTECTED REGION ID(R-474858323) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo residenzaDirettoreLavori. 
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
	public java.lang.String getResidenzaDirettoreLavori() {
		return residenzaDirettoreLavori;
	}

	/**
	 * @generated
	 */
	private java.util.Date dtStipulazioneContratti = null;

	/**
	 * @generated
	 */
	public void setDtStipulazioneContratti(java.util.Date val) {
		dtStipulazioneContratti = val;
	}

	////{PROTECTED REGION ID(R1549404570) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dtStipulazioneContratti. 
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
	public java.util.Date getDtStipulazioneContratti() {
		return dtStipulazioneContratti;
	}

	/*PROTECTED REGION ID(R882649912) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
