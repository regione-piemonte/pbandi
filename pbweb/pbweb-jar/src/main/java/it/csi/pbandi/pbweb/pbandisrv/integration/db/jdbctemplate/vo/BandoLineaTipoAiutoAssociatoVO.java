package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinTipoAiutoVO;

public class BandoLineaTipoAiutoAssociatoVO extends PbandiRBandoLinTipoAiutoVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2373970658036326404L;
	private String descBreveTipoAiuto;
	private String descTipoAiuto;
	private String codIgrueT1;

	public String getDescBreveTipoAiuto() {
		return descBreveTipoAiuto;
	}
	public void setDescBreveTipoAiuto(String descBreveTipoAiuto) {
		this.descBreveTipoAiuto = descBreveTipoAiuto;
	}
	public String getDescTipoAiuto() {
		return descTipoAiuto;
	}
	public void setDescTipoAiuto(String descTipoAiuto) {
		this.descTipoAiuto = descTipoAiuto;
	}
	public void setCodIgrueT1(String codIgrueT1) {
		this.codIgrueT1 = codIgrueT1;
	}
	public String getCodIgrueT1() {
		return codIgrueT1;
	}
	
}
