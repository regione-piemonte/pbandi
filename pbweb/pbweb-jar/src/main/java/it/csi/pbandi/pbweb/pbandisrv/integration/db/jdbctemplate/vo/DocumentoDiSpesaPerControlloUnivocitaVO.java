package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;

public class DocumentoDiSpesaPerControlloUnivocitaVO extends PbandiTDocumentoDiSpesaVO {
	
	private String descBreveTipoDocSpesa;
	private String codiceFiscaleFornitore;
	private BigDecimal idSoggettoFornitore;

	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setIdSoggettoFornitore(BigDecimal idSoggettoFornitore) {
		this.idSoggettoFornitore = idSoggettoFornitore;
	}
	public BigDecimal getIdSoggettoFornitore() {
		return idSoggettoFornitore;
	}
	
}
