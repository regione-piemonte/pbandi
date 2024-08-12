package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class VoceDiSpesaPagamentoVO extends GenericVO{

	private static final long serialVersionUID = -8126910131656335029L;
	
	private java.lang.Long idDocumentoDiSpesa = null;

	private java.lang.Long idPagamento = null;
	
	private java.lang.Double importoAssociato = null;

	private java.lang.Double importoValidato = null;

	private java.lang.Long idQuotaParte = null;

	private java.lang.String descVoceSpesa = null;
	
	private java.lang.Long idRigoContoEconomico=null;
	private java.lang.Long idDichiarazioneSpesa=null;

	private java.lang.Double oreLavorate = null;
	
	public void setIdDocumentoDiSpesa(java.lang.Long val) {
		idDocumentoDiSpesa = val;
	}

	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}


	public void setImportoAssociato(java.lang.Double val) {
		importoAssociato = val;
	}

	public java.lang.Double getImportoAssociato() {
		return importoAssociato;
	}


	public void setImportoValidato(java.lang.Double val) {
		importoValidato = val;
	}

	public java.lang.Double getImportoValidato() {
		return importoValidato;
	}


	public void setIdQuotaParte(java.lang.Long val) {
		idQuotaParte = val;
	}

	public java.lang.Long getIdQuotaParte() {
		return idQuotaParte;
	}
	
	public java.lang.String getDescVoceSpesa() {
		return descVoceSpesa;
	}

	public void setDescVoceSpesa(java.lang.String descVoceSpesa) {
		this.descVoceSpesa = descVoceSpesa;
	}

	public void setIdPagamento(java.lang.Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdRigoContoEconomico(java.lang.Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public java.lang.Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public java.lang.Long getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}

	public void setIdDichiarazioneSpesa(java.lang.Long idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}

	public java.lang.Double getOreLavorate() {
		return oreLavorate;
	}

	public void setOreLavorate(java.lang.Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}

}
