package it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione;

////{PROTECTED REGION ID(R-1871783051) ENABLED START////}
/**
 * Inserire qui la documentazione della classe RichiestaErogazioneReportDTO.
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
public class RichiestaErogazioneReportDTO implements java.io.Serializable {
	// il serial version UID � impostato in base a quanto configurato nel modello
	/**
	 * @generated
	 */
	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EnteAppartenenzaDTO ente = null;

	/**
	 * @generated
	 */
	public void setEnte(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EnteAppartenenzaDTO val) {
		ente = val;
	}

	////{PROTECTED REGION ID(R-1104564785) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo ente. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.EnteAppartenenzaDTO getEnte() {
		return ente;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.ProgettoDTO progetto = null;

	/**
	 * @generated
	 */
	public void setProgetto(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.ProgettoDTO val) {
		progetto = val;
	}

	////{PROTECTED REGION ID(R1425647673) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo progetto. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.ProgettoDTO getProgetto() {
		return progetto;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.BeneficiarioDTO beneficiario = null;

	/**
	 * @generated
	 */
	public void setBeneficiario(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.BeneficiarioDTO val) {
		beneficiario = val;
	}

	////{PROTECTED REGION ID(R1605761071) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo beneficiario. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.BeneficiarioDTO getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @generated
	 */
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO rappresentanteLegale = null;

	/**
	 * @generated
	 */
	public void setRappresentanteLegale(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO val) {
		rappresentanteLegale = val;
	}

	////{PROTECTED REGION ID(R24653241) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo rappresentanteLegale. 
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO getRappresentanteLegale() {
		return rappresentanteLegale;
	}

	/**
	 * @generated
	 */
	private java.lang.Double importoTotaleSpesaQuietanzata = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleSpesaQuietanzata(java.lang.Double val) {
		importoTotaleSpesaQuietanzata = val;
	}

	////{PROTECTED REGION ID(R-691010731) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleSpesaQuietanzata. 
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
	public java.lang.Double getImportoTotaleSpesaQuietanzata() {
		return importoTotaleSpesaQuietanzata;
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

	////{PROTECTED REGION ID(R1013412705) ENABLED START////}
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
	private java.lang.String descCausaleErogazione = null;

	/**
	 * @generated
	 */
	public void setDescCausaleErogazione(java.lang.String val) {
		descCausaleErogazione = val;
	}

	////{PROTECTED REGION ID(R-1877926669) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo descCausaleErogazione. 
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
	public java.lang.String getDescCausaleErogazione() {
		return descCausaleErogazione;
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

	////{PROTECTED REGION ID(R-295033168) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] fideiussioni = null;

	/**
	 * @generated
	 */
	public void setFideiussioni(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] val) {
		fideiussioni = val;
	}

	////{PROTECTED REGION ID(R360532070) ENABLED START////}
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
	private it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] allegati = null;

	/**
	 * @generated
	 */
	public void setAllegati(
			it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] val) {
		allegati = val;
	}

	////{PROTECTED REGION ID(R-64451544) ENABLED START////}
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
	public it.csi.pbandi.pbweb.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] getAllegati() {
		return allegati;
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

	////{PROTECTED REGION ID(R1969514244) ENABLED START////}
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
	private java.lang.Long numeroRichiestaErogazione = null;

	/**
	 * @generated
	 */
	public void setNumeroRichiestaErogazione(java.lang.Long val) {
		numeroRichiestaErogazione = val;
	}

	////{PROTECTED REGION ID(R-818432174) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo numeroRichiestaErogazione. 
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
	public java.lang.Long getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataInizioLavori = null;

	/**
	 * @generated
	 */
	public void setDataInizioLavori(java.util.Date val) {
		dataInizioLavori = val;
	}

	////{PROTECTED REGION ID(R492107712) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataInizioLavori. 
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
	public java.util.Date getDataInizioLavori() {
		return dataInizioLavori;
	}

	/**
	 * @generated
	 */
	private java.util.Date dataStipulazioneContratti = null;

	/**
	 * @generated
	 */
	public void setDataStipulazioneContratti(java.util.Date val) {
		dataStipulazioneContratti = val;
	}

	////{PROTECTED REGION ID(R-1579136044) ENABLED START////}
	/**
	 * Inserire qui la documentazione dell'attributo dataStipulazioneContratti. 
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
	public java.util.Date getDataStipulazioneContratti() {
		return dataStipulazioneContratti;
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

	////{PROTECTED REGION ID(R-1101738982) ENABLED START////}
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

	////{PROTECTED REGION ID(R1432967001) ENABLED START////}
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

	/*PROTECTED REGION ID(R241708236) ENABLED START*/
	/// inserire qui eventuali ridefinizioni di toString, hashCode, equals
	/*PROTECTED REGION END*/
}
