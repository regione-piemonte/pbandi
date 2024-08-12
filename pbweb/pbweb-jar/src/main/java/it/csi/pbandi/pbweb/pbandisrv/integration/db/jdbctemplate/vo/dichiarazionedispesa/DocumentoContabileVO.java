package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.util.Date;

public class DocumentoContabileVO extends GenericVO{
	private Date _dataPagamento = null;
	private Date _dataValuta = null;
	private Date _dataDocumento = null;
	private String _descBreveTipoDocumento = null;
	private String _descDocumento = null;
	private String _destinatarioPagamento = null;
	private String flagElettronico = null;
	private Long _idDocumentoDiSpesa = null;
	private Long _idPagamento = null;
	private Double _importoPagamento = null;
	private Double _imponibile = null;
	private Double _importoIva = null;
	private Double _importoTotaleDocumento = null;
	private Double importoRendicontabile = null;
	private String is_same_dich = null;
	private String _modalitaPagamento = null;
	private String _numeroDocumento = null;
	private String numProtocollo= null;
	private String task = null;
	private String _tipoDocumento = null;
	private String tipoInvio = null;
	
	//PK 05/2023 bandi Arch. Rurali
	private String ruolo = null;
	private java.util.Date dataFirmaContratto = null;
	
	public Long getIdDocumentoDiSpesa() {
		return _idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(Long documentoDiSpesa) {
		_idDocumentoDiSpesa = documentoDiSpesa;
	}
	public Long getIdPagamento() {
		return _idPagamento;
	}
	public void setIdPagamento(Long pagamento) {
		_idPagamento = pagamento;
	}
	public String getTipoDocumento() {
		return _tipoDocumento;
	}
	public void setTipoDocumento(String documento) {
		_tipoDocumento = documento;
	}
	public String getNumeroDocumento() {
		return _numeroDocumento;
	}
	public void setNumeroDocumento(String documento) {
		_numeroDocumento = documento;
	}
	public String getModalitaPagamento() {
		return _modalitaPagamento;
	}
	public void setModalitaPagamento(String pagamento) {
		_modalitaPagamento = pagamento;
	}
	public String getDestinatarioPagamento() {
		return _destinatarioPagamento;
	}
	public void setDestinatarioPagamento(String pagamento) {
		_destinatarioPagamento = pagamento;
	}
	public Double getImponibile() {
		return _imponibile;
	}
	public void setImponibile(Double _imponibile) {
		this._imponibile = _imponibile;
	}
	public Double getImportoIva() {
		return _importoIva;
	}
	public void setImportoIva(Double iva) {
		_importoIva = iva;
	}
	public Double getImportoTotaleDocumento() {
		return _importoTotaleDocumento;
	}
	public void setImportoTotaleDocumento(Double totaleDocumento) {
		_importoTotaleDocumento = totaleDocumento;
	}
	public Double getImportoPagamento() {
		return _importoPagamento;
	}
	public void setImportoPagamento(Double pagamento) {
		_importoPagamento = pagamento;
	}
	public Date getDataPagamento() {
		return _dataPagamento;
	}
	public void setDataPagamento(Date pagamento) {
		_dataPagamento = pagamento;
	}
	public Date getDataValuta() {
		return _dataValuta;
	}
	public void setDataValuta(Date valuta) {
		_dataValuta = valuta;
	}
	public Date getDataDocumento() {
		return _dataDocumento;
	}
	public void setDataDocumento(Date documento) {
		_dataDocumento = documento;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTask() {
		return task;
	}
	public void setImportoRendicontabile(Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public String getDescDocumento() {
		return _descDocumento;
	}
	public void setDescDocumento(String _descDocumento) {
		this._descDocumento = _descDocumento;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public String getIs_same_dich() {
		return is_same_dich;
	}
	public void setIs_same_dich(String is_same_dich) {
		this.is_same_dich = is_same_dich;
	}
	public String getFlagElettronico() {
		return flagElettronico;
	}
	public void setFlagElettronico(String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}
	public String getDescBreveTipoDocumento() {
		return _descBreveTipoDocumento;
	}
	public void setDescBreveTipoDocumento(String descBreveTipoDocumento) {
		this._descBreveTipoDocumento= descBreveTipoDocumento;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public java.util.Date getDataFirmaContratto() {
		return dataFirmaContratto;
	}
	public void setDataFirmaContratto(java.util.Date dataFirmaContratto) {
		this.dataFirmaContratto = dataFirmaContratto;
	}
	

}
