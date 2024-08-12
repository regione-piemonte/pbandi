package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class ValidaMensilitaRequest {
	
	private Long idPrg;
	private String mese; 
	private Long anno;
	private Long idDichSpesa;
	private String esitoValidMesi;
	private String note;
	
	public Long getIdPrg() {
		return idPrg;
	}

	public void setIdPrg(Long idPrg) {
		this.idPrg = idPrg;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public Long getIdDichSpesa() {
		return idDichSpesa;
	}

	public void setIdDichSpesa(Long idDichSpesa) {
		this.idDichSpesa = idDichSpesa;
	}

	public String getEsitoValidMesi() {
		return esitoValidMesi;
	}

	public void setEsitoValidMesi(String esitoValidMesi) {
		this.esitoValidMesi = esitoValidMesi;
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
