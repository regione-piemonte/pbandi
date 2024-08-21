/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocumentoDiSpesaVO extends GenericVO{
	
	

	static final long serialVersionUID = 1;

	/**
	 * @generated
	 */
	private java.lang.String _codiceFiscaleFornitore = null;
	private Long _idStatoDocumentoDiSpesa = null;
	private String task;
	private BigDecimal importoTotaleValidato;
	private String noteValidazione;
	

	public Long getIdStatoDocumentoDiSpesa() {
		return _idStatoDocumentoDiSpesa;
	}

	public void setIdStatoDocumentoDiSpesa(Long idStatoDocumentoDiSpesa) {
		this._idStatoDocumentoDiSpesa = idStatoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	public void setCodiceFiscaleFornitore(java.lang.String val) {
		_codiceFiscaleFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo codiceFiscaleFornitore. 
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
	public java.lang.String getCodiceFiscaleFornitore() {
		return _codiceFiscaleFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String _codiceProgetto = null;

	/**
	 * @generated
	 */
	public void setCodiceProgetto(java.lang.String val) {
		_codiceProgetto = val;
	}

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
	public java.lang.String getCodiceProgetto() {
		return _codiceProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.String _cognomeFornitore = null;

	/**
	 * @generated
	 */
	public void setCognomeFornitore(java.lang.String val) {
		_cognomeFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo cognomeFornitore. 
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
	public java.lang.String getCognomeFornitore() {
		return _cognomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.util.Date _dataDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDataDocumentoDiSpesa(java.util.Date val) {
		_dataDocumentoDiSpesa = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo dataDocumentoDiSpesa. 
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
	public java.util.Date getDataDocumentoDiSpesa() {
		return _dataDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String _descrizioneDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setDescrizioneDocumentoDiSpesa(java.lang.String val) {
		_descrizioneDocumentoDiSpesa = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo descrizioneDocumentoDiSpesa. 
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
	public java.lang.String getDescrizioneDocumentoDiSpesa() {
		return _descrizioneDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.String _destinazioneTrasferta = null;

	/**
	 * @generated
	 */
	public void setDestinazioneTrasferta(java.lang.String val) {
		_destinazioneTrasferta = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo destinazioneTrasferta. 
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
	public java.lang.String getDestinazioneTrasferta() {
		return _destinazioneTrasferta;
	}

	/**
	 * @generated
	 */
	private java.lang.String _denominazioneFornitore = null;

	/**
	 * @generated
	 */
	public void setDenominazioneFornitore(java.lang.String val) {
		_denominazioneFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo denominazioneFornitore. 
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
	public java.lang.String getDenominazioneFornitore() {
		return _denominazioneFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String _descTipoDocumentoDiSpesa = null;

	


	/**
	 * @generated
	 */
	private java.lang.Double _durataTrasferta = null;

	/**
	 * @generated
	 */
	public void setDurataTrasferta(java.lang.Double val) {
		_durataTrasferta = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo durataTrasferta. 
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
	public java.lang.Double getDurataTrasferta() {
		return _durataTrasferta;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idBeneficiario = null;

	/**
	 * @generated
	 */
	public void setIdBeneficiario(java.lang.Long val) {
		_idBeneficiario = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idBeneficiario. 
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
	public java.lang.Long getIdBeneficiario() {
		return _idBeneficiario;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idDocRiferimento = null;

	/**
	 * @generated
	 */
	public void setIdDocRiferimento(java.lang.Long val) {
		_idDocRiferimento = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idDocRiferimento. 
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
	public java.lang.Long getIdDocRiferimento() {
		return _idDocRiferimento;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		_idDocumentoDiSpesa = val;
	}

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
	public java.lang.Long getIdDocumentoDiSpesa() {
		return _idDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idFornitore = null;

	/**
	 * @generated
	 */
	public void setIdFornitore(java.lang.Long val) {
		_idFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idFornitore. 
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
	public java.lang.Long getIdFornitore() {
		return _idFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idProgetto = null;

	/**
	 * @generated
	 */
	public void setIdProgetto(java.lang.Long val) {
		_idProgetto = val;
	}

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
	public java.lang.Long getIdProgetto() {
		return _idProgetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idSoggetto = null;

	/**
	 * @generated
	 */
	public void setIdSoggetto(java.lang.Long val) {
		_idSoggetto = val;
	}

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
	public java.lang.Long getIdSoggetto() {
		return _idSoggetto;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idTipoDocumentoDiSpesa = null;

	/**
	 * @generated
	 */
	public void setIdTipoDocumentoDiSpesa(java.lang.Long val) {
		_idTipoDocumentoDiSpesa = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idTipoDocumentoDiSpesa. 
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
	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return _idTipoDocumentoDiSpesa;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idTipoFornitore = null;

	/**
	 * @generated
	 */
	public void setIdTipoFornitore(java.lang.Long val) {
		_idTipoFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idTipoFornitore. 
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
	public java.lang.Long getIdTipoFornitore() {
		return _idTipoFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idTipoOggettoAttivita = null;

	/**
	 * @generated
	 */
	public void setIdTipoOggettoAttivita(java.lang.Long val) {
		_idTipoOggettoAttivita = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idTipoOggettoAttivita. 
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
	public java.lang.Long getIdTipoOggettoAttivita() {
		return _idTipoOggettoAttivita;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _imponibile = null;

	/**
	 * @generated
	 */
	public void setImponibile(java.lang.Double val) {
		_imponibile = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo imponibile. 
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
	public java.lang.Double getImponibile() {
		return _imponibile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _importoIva = null;

	/**
	 * @generated
	 */
	public void setImportoIva(java.lang.Double val) {
		_importoIva = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo importoIva. 
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
	public java.lang.Double getImportoIva() {
		return _importoIva;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _importoIvaACosto = null;

	/**
	 * @generated
	 */
	public void setImportoIvaACosto(java.lang.Double val) {
		_importoIvaACosto = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo importoIvaACosto. 
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
	public java.lang.Double getImportoIvaACosto() {
		return _importoIvaACosto;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _importoRendicontabile = null;

	/**
	 * @generated
	 */
	public void setImportoRendicontabile(java.lang.Double val) {
		_importoRendicontabile = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo importoRendicontabile. 
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
	public java.lang.Double getImportoRendicontabile() {
		return _importoRendicontabile;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _importoTotaleDocumentoIvato = null;

	/**
	 * @generated
	 */
	public void setImportoTotaleDocumentoIvato(java.lang.Double val) {
		_importoTotaleDocumentoIvato = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo importoTotaleDocumentoIvato. 
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
	public java.lang.Double getImportoTotaleDocumentoIvato() {
		return _importoTotaleDocumentoIvato;
	}

	/**
	 * @generated
	 */
	private java.lang.Double _importoRitenutaDAcconto = null;

	/**
	 * @generated
	 */
	public void setImportoRitenutaDAcconto(java.lang.Double val) {
		_importoRitenutaDAcconto = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo importoRitenutaDAcconto. 
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
	public java.lang.Double getImportoRitenutaDAcconto() {
		return _importoRitenutaDAcconto;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean _isGestitiNelProgetto = null;

	/**
	 * @generated
	 */
	public void setIsGestitiNelProgetto(java.lang.Boolean val) {
		_isGestitiNelProgetto = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo isGestitiNelProgetto. 
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
	public java.lang.Boolean getIsGestitiNelProgetto() {
		return _isGestitiNelProgetto;
	}

	
	/**
	 * @generated
	 */
	private java.lang.Boolean _isRicercaPerCapofila = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerCapofila(java.lang.Boolean val) {
		_isRicercaPerCapofila = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerCapofila. 
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
	public java.lang.Boolean getIsRicercaPerCapofila() {
		return _isRicercaPerCapofila;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean _isRicercaPerTutti = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerTutti(java.lang.Boolean val) {
		_isRicercaPerTutti = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerTutti. 
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
	public java.lang.Boolean getIsRicercaPerTutti() {
		return _isRicercaPerTutti;
	}

	/**
	 * @generated
	 */
	private java.lang.Boolean _isRicercaPerPartner = null;

	/**
	 * @generated
	 */
	public void setIsRicercaPerPartner(java.lang.Boolean val) {
		_isRicercaPerPartner = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo isRicercaPerPartner. 
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
	public java.lang.Boolean getIsRicercaPerPartner() {
		return _isRicercaPerPartner;
	}

	/**
	 * @generated
	 */
	private java.lang.String _nomeFornitore = null;

	/**
	 * @generated
	 */
	public void setNomeFornitore(java.lang.String val) {
		_nomeFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo nomeFornitore. 
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
	public java.lang.String getNomeFornitore() {
		return _nomeFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String _numeroDocumento = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumento(java.lang.String val) {
		_numeroDocumento = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumento. 
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
	public java.lang.String getNumeroDocumento() {
		return _numeroDocumento;
	}

	/**
	 * @generated
	 */
	private java.lang.String _partitaIvaFornitore = null;

	/**
	 * @generated
	 */
	public void setPartitaIvaFornitore(java.lang.String val) {
		_partitaIvaFornitore = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo partitaIvaFornitore. 
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
	public java.lang.String getPartitaIvaFornitore() {
		return _partitaIvaFornitore;
	}

	/**
	 * @generated
	 */
	private java.lang.String _partner = null;

	/**
	 * @generated
	 */
	public void setPartner(java.lang.String val) {
		_partner = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo partner. 
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
	public java.lang.String getPartner() {
		return _partner;
	}

	/**
	 * @generated
	 */
	private java.lang.Long _idSoggettoPartner = null;

	/**
	 * @generated
	 */
	public void setIdSoggettoPartner(java.lang.Long val) {
		_idSoggettoPartner = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo idSoggettoPartner. 
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
	public java.lang.Long getIdSoggettoPartner() {
		return _idSoggettoPartner;
	}
		
		
		
		
		
	private java.util.Date _dataDocumentoDiSpesaDiRiferimento = null;

	/**
	 * @generated
	 */
	public void setDataDocumentoDiSpesaDiRiferimento(java.util.Date val) {
		_dataDocumentoDiSpesaDiRiferimento = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo dataDocumentoDiSpesaDiRiferimento. 
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
	public java.util.Date getDataDocumentoDiSpesaDiRiferimento() {
		return _dataDocumentoDiSpesaDiRiferimento;
	}
		
		
	private java.lang.String _numeroDocumentoDiSpesaDiRiferimento = null;

	/**
	 * @generated
	 */
	public void setNumeroDocumentoDiSpesaDiRiferimento(java.lang.String val) {
		_numeroDocumentoDiSpesaDiRiferimento = val;
	}

	/**
	 * Inserire qui la documentazione dell'attributo numeroDocumentoDiSpesaDiRiferimento. 
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
	public java.lang.String getNumeroDocumentoDiSpesaDiRiferimento() {
		return _numeroDocumentoDiSpesaDiRiferimento;
	}	

	
	private java.lang.String _descStatoDocumentoSpesa = null;

	/**
	 * @generated
	 */
	public void setDescStatoDocumentoSpesa(java.lang.String val) {
		_descStatoDocumentoSpesa = val;
	}


	public java.lang.String getDescStatoDocumentoSpesa() {
		return _descStatoDocumentoSpesa;
	}

	
	private java.lang.String _descTipologiaFornitore = null;

	
	public void setDescTipologiaFornitore(java.lang.String val) {
		_descTipologiaFornitore = val;
	}


	public java.lang.String getDescTipologiaFornitore() {
		return _descTipologiaFornitore;
	}


	private java.lang.String descBreveTipoDocumentoDiSpesa = null;

	public void setDescBreveTipoDocumentoDiSpesa(java.lang.String val) {
		descBreveTipoDocumentoDiSpesa = val;
	}

	public java.lang.String getDescBreveTipoDocumentoDiSpesa() {
		return descBreveTipoDocumentoDiSpesa;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTask() {
		return task;
	}

	public void setImportoTotaleValidato(BigDecimal importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}

	public BigDecimal getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public java.lang.String get_descTipoDocumentoDiSpesa() {
		return _descTipoDocumentoDiSpesa;
	}

	public void set_descTipoDocumentoDiSpesa(java.lang.String _descTipoDocumentoDiSpesa) {
		this._descTipoDocumentoDiSpesa = _descTipoDocumentoDiSpesa;
	}

	public String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
}
