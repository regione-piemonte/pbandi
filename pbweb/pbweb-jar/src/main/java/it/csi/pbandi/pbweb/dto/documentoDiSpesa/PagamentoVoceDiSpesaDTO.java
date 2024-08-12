package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

public class PagamentoVoceDiSpesaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idVoceDiSpesa = null;
	private java.lang.Long idQuotaParteDocSpesa = null;
	private java.lang.Long idRigoContoEconomico = null;
	private java.lang.String voceDiSpesa = null;
	private java.lang.Double importoRendicontato = null;
	private java.lang.Double importoVoceDiSpesaCorrente = null;
	private java.lang.Double totaleAltriPagamenti = null;
	
	public java.lang.Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(java.lang.Long idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public java.lang.Long getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(java.lang.Long idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public java.lang.String getVoceDiSpesa() {
		return voceDiSpesa;
	}
	public void setVoceDiSpesa(java.lang.String voceDiSpesa) {
		this.voceDiSpesa = voceDiSpesa;
	}
	public java.lang.Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(java.lang.Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public java.lang.Double getImportoRendicontato() {
		return importoRendicontato;
	}
	public void setImportoRendicontato(java.lang.Double importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}
	public java.lang.Double getTotaleAltriPagamenti() {
		return totaleAltriPagamenti;
	}
	public void setTotaleAltriPagamenti(java.lang.Double totaleAltriPagamenti) {
		this.totaleAltriPagamenti = totaleAltriPagamenti;
	}
	public java.lang.Double getImportoVoceDiSpesaCorrente() {
		return importoVoceDiSpesaCorrente;
	}
	public void setImportoVoceDiSpesaCorrente(java.lang.Double importoVoceDiSpesaCorrente) {
		this.importoVoceDiSpesaCorrente = importoVoceDiSpesaCorrente;
	}
	@Override
	public String toString() {
		return "PagamentoVoceDiSpesaDTO [idVoceDiSpesa=" + idVoceDiSpesa + ", idQuotaParteDocSpesa="
				+ idQuotaParteDocSpesa + ", idRigoContoEconomico=" + idRigoContoEconomico + ", voceDiSpesa="
				+ voceDiSpesa + ", importoRendicontato=" + importoRendicontato + ", importoVoceDiSpesaCorrente="
				+ importoVoceDiSpesaCorrente + ", totaleAltriPagamenti=" + totaleAltriPagamenti + "]";
	}

}
