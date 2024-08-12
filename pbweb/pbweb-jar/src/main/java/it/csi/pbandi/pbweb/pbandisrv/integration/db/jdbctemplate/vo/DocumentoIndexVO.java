package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;

public class DocumentoIndexVO extends PbandiTDocumentoIndexVO {
	
	private String descBreveTipoDocIndex;
	private String descTipoDocIndex;
	private String actaIndiceClassificazEsteso;
	
	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}
	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}
	public void setDescTipoDocIndex(String descTipoDocIndex) {
		this.descTipoDocIndex = descTipoDocIndex;
	}
	public String getDescTipoDocIndex() {
		return descTipoDocIndex;
	}
	public String getActaIndiceClassificazEsteso() {
		return actaIndiceClassificazEsteso;
	}
	public void setActaIndiceClassificazEsteso(String actaIndiceClassificazEsteso) {
		this.actaIndiceClassificazEsteso = actaIndiceClassificazEsteso;
	}

}
