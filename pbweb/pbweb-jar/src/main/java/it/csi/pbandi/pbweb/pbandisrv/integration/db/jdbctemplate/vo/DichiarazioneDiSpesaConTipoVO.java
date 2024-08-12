package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;

public class DichiarazioneDiSpesaConTipoVO extends PbandiTDichiarazioneSpesaVO {
	String descBreveTipoDichiaraSpesa;
	String descTipoDichiarazioneSpesa;
	
	public String getDescBreveTipoDichiaraSpesa() {
		return descBreveTipoDichiaraSpesa;
	}
	public void setDescBreveTipoDichiaraSpesa(String descBreveTipoDichiaraSpesa) {
		this.descBreveTipoDichiaraSpesa = descBreveTipoDichiaraSpesa;
	}
	public String getDescTipoDichiarazioneSpesa() {
		return descTipoDichiarazioneSpesa;
	}
	public void setDescTipoDichiarazioneSpesa(String descTipoDichiarazioneSpesa) {
		this.descTipoDichiarazioneSpesa = descTipoDichiarazioneSpesa;
	}
}
