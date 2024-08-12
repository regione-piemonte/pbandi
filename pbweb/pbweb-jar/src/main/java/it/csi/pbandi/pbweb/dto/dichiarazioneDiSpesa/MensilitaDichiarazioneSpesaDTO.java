package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class MensilitaDichiarazioneSpesaDTO implements Serializable {
	
	private Long anno = null;
	private String mese = null;
	private String esitoValidMesi = null;
	private String sabbatico = null;
	private Long idPrg = null;
	private Long idDichSpesa = null;
	private Long idDichMeseRipetuto = null;
	private String note = null;
	
	public Long getAnno() {
		return anno;
	}
	public void setAnno(Long anno) {
		this.anno = anno;
	}
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}
	public String getEsitoValidMesi() {
		return esitoValidMesi;
	}
	public void setEsitoValidMesi(String esitoValidMesi) {
		this.esitoValidMesi = esitoValidMesi;
	}
	public String getSabbatico() {
		return sabbatico;
	}
	public void setSabbatico(String sabbatico) {
		this.sabbatico = sabbatico;
	}
	public Long getIdPrg() {
		return idPrg;
	}
	public void setIdPrg(Long idPrg) {
		this.idPrg = idPrg;
	}
	public Long getIdDichSpesa() {
		return idDichSpesa;
	}
	public void setIdDichSpesa(Long idDichSpesa) {
		this.idDichSpesa = idDichSpesa;
	}
	public Long getIdDichMeseRipetuto() {
		return idDichMeseRipetuto;
	}
	public void setIdDichMeseRipetuto(Long idDichMeseRipetuto) {
		this.idDichMeseRipetuto = idDichMeseRipetuto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
