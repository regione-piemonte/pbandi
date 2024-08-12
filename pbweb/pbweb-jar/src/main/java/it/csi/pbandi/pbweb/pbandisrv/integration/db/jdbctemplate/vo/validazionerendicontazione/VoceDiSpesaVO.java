package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

public class VoceDiSpesaVO {

	private java.lang.String descVoceSpesa = null;
	private java.lang.Double importoAmmesso = null;
	private java.lang.Double importoValidato = null;
	private Long idRigoContoEconomico=null;

	public void setDescVoceSpesa(java.lang.String descVoceSpesa) {
		this.descVoceSpesa = descVoceSpesa;
	}

	public java.lang.String getDescVoceSpesa() {
		return descVoceSpesa;
	}

	public void setImportoAmmesso(java.lang.Double importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public java.lang.Double getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoValidato(java.lang.Double importoValidato) {
		this.importoValidato = importoValidato;
	}

	public java.lang.Double getImportoValidato() {
		return importoValidato;
	}

	public void setIdRigoContoEconomico(Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	
}
