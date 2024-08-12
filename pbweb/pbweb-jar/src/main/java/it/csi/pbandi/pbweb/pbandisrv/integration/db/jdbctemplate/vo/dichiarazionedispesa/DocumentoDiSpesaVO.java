package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import java.math.BigDecimal;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DocumentoDiSpesaVO extends GenericVO{

	private BigDecimal totaleImportoPagamenti;
	private BigDecimal totaleNoteCredito;
	private BigDecimal totaleImportoQuotaParte;
	private BigDecimal totaleTuttiPagamenti;
	private String task;



		/**
		 * @generated
		 */
		private java.lang.String _codiceFiscaleFornitore = null;

		/**
		 * @generated
		 */
		public void setCodiceFiscaleFornitore(java.lang.String val) {
			_codiceFiscaleFornitore = val;
		}

		public java.lang.String getCodiceFiscaleFornitore() {
			return _codiceFiscaleFornitore;
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

		
		public java.util.Date getDataDocumentoDiSpesa() {
			return _dataDocumentoDiSpesa;
		}

		/**
		 * @generated
		 */
		private java.lang.String _descTipologiaDocumentoDiSpesa = null;

		/**
		 * @generated
		 */
		public void setDescTipologiaDocumentoDiSpesa(java.lang.String val) {
			_descTipologiaDocumentoDiSpesa = val;
		}

		public java.lang.String getDescTipologiaDocumentoDiSpesa() {
			return _descTipologiaDocumentoDiSpesa;
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

		
		public java.lang.Long getIdDocumentoDiSpesa() {
			return _idDocumentoDiSpesa;
		}

		/**
		 * @generated
		 */
		private java.lang.Long _idDocDiRiferimento = null;

		/**
		 * @generated
		 */
		public void setIdDocDiRiferimento(java.lang.Long val) {
			_idDocDiRiferimento = val;
		}

		public java.lang.Long getIdDocDiRiferimento() {
			return _idDocDiRiferimento;
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

		
		public java.lang.Long getIdFornitore() {
			return _idFornitore;
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

		
		public java.lang.Long getIdTipoDocumentoDiSpesa() {
			return _idTipoDocumentoDiSpesa;
		}

		/**
		 * @generated
		 */
		private java.lang.String _motivazione = null;

		/**
		 * @generated
		 */
		public void setMotivazione(java.lang.String val) {
			_motivazione = val;
		}

		
		public java.lang.String getMotivazione() {
			return _motivazione;
		}


		/**
		 * @generated
		 */
		private java.lang.String _numeroDocumentoDiSpesa = null;

		/**
		 * @generated
		 */
		public void setNumeroDocumentoDiSpesa(java.lang.String val) {
			_numeroDocumentoDiSpesa = val;
		}

		
		public java.lang.String getNumeroDocumentoDiSpesa() {
			return _numeroDocumentoDiSpesa;
		}
		
		
		
		private java.lang.Double _importoTotaleDocumento = null;
		
		public void setImportoTotaleDocumento(Double val){
			_importoTotaleDocumento = val;
		}
		
		public Double getImportoTotaleDocumento () {
			return _importoTotaleDocumento;
		}
		
		
		private java.lang.Long _idProgetto = null;
		
		public void setIdProgetto (java.lang.Long val){
			_idProgetto = val;
		}
		
		public java.lang.Long getIdProgetto (){
			return _idProgetto;
		}
		
		private java.lang.Double _importoRendicontabile = null;
		
		public void setImportoRendicontabile(Double val){
			_importoRendicontabile = val;
		}
		
		public Double getImportoRendicontabile () {
			return _importoRendicontabile;
		}

	public void setTotaleImportoPagamenti(BigDecimal totaleImportoPagamenti) {
		this.totaleImportoPagamenti = totaleImportoPagamenti;
	}

	public BigDecimal getTotaleImportoPagamenti() {
		return totaleImportoPagamenti;
	}

	public void setTotaleNoteCredito(BigDecimal totaleNoteCredito) {
		this.totaleNoteCredito = totaleNoteCredito;
	}

	public BigDecimal getTotaleNoteCredito() {
		return totaleNoteCredito;
	}

	public void setTotaleImportoQuotaParte(BigDecimal totaleImportoQuotaParte) {
		this.totaleImportoQuotaParte = totaleImportoQuotaParte;
	}

	public BigDecimal getTotaleImportoQuotaParte() {
		return totaleImportoQuotaParte;
	}

	public void setTotaleTuttiPagamenti(BigDecimal totaleTuttiPagamenti) {
		this.totaleTuttiPagamenti = totaleTuttiPagamenti;
	}

	public BigDecimal getTotaleTuttiPagamenti() {
		return totaleTuttiPagamenti;
	}
	
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
}
