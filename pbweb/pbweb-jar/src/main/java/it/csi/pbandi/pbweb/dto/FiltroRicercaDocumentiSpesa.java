package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class FiltroRicercaDocumentiSpesa implements java.io.Serializable {

	private String partner = null;					// combo Rendicontazione
	private String documentiDiSpesa = null;			// check gestiti nel progetto \ tutti
	private Long idTipologia = null;
	private Long idStato = null;
	private Long idFornitore = null;
	private Long idVoceDiSpesa = null;
	private String numero = null;
	private Date data = null;
	private Date dataA = null;
	private String task = null;
	private Long idCategoria = null;
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getDocumentiDiSpesa() {
		return documentiDiSpesa;
	}
	public void setDocumentiDiSpesa(String documentiDiSpesa) {
		this.documentiDiSpesa = documentiDiSpesa;
	}
	public Long getIdTipologia() {
		return idTipologia;
	}
	public void setIdTipologia(Long idTipologia) {
		this.idTipologia = idTipologia;
	}
	public Long getIdStato() {
		return idStato;
	}
	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	public Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(Long idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getDataA() {
		return dataA;
	}
	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n"+this.getClass().getName()+": ");
//		try {
//			Set<String> properties = BeanUtil.getProperties(this.getClass());
//			for (String propName : properties) {
//				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
//			}
//		} catch (IntrospectionException e) {
//		}
//		return sb.toString();
//	}


	@Override
	public String toString() {
		return "FiltroRicercaDocumentiSpesa{" +
				"partner='" + partner + '\'' +
				", documentiDiSpesa='" + documentiDiSpesa + '\'' +
				", idTipologia=" + idTipologia +
				", idStato=" + idStato +
				", idFornitore=" + idFornitore +
				", idVoceDiSpesa=" + idVoceDiSpesa +
				", numero='" + numero + '\'' +
				", data=" + data +
				", dataA=" + dataA +
				", task='" + task + '\'' +
				", idCategoria='" + idCategoria + '\'' +
				'}';
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
}
