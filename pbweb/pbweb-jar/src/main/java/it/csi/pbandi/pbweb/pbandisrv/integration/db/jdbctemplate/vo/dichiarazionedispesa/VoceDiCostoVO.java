package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class VoceDiCostoVO extends GenericVO{
	
	
	private String descVoceDiSpesa = null;
	private Long idVoceSpesa = null;
	private Long idVoceDiSpesaPadre = null;
	private String descVoceDiSpesaPadre = null;
	private Double importoAmmessoAFinanziamento = null;
	private Double importoQuietanzato = null;
	private Double importoValidato = null;
	
	
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public Long getIdVoceSpesa() {
		return idVoceSpesa;
	}
	public void setIdVoceSpesa(Long idVoceSpesa) {
		this.idVoceSpesa = idVoceSpesa;
	}
	public Long getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(Long idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	public Double getImportoAmmessoAFinanziamento() {
		return importoAmmessoAFinanziamento;
	}
	public void setImportoAmmessoAFinanziamento(Double importoAmmessoAFinanziamento) {
		this.importoAmmessoAFinanziamento = importoAmmessoAFinanziamento;
	}
	public Double getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(Double importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	public Double getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(Double importoValidato) {
		this.importoValidato = importoValidato;
	}
	
	
	

}
