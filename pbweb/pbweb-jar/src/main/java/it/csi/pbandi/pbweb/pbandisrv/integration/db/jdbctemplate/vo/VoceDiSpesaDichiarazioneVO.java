package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class VoceDiSpesaDichiarazioneVO extends GenericVO{
	
	
	private String descVoceDiSpesa = null;
	private String descVoceDiSpesaPadre = null;
	private BigDecimal importoAmmessoFinanziamento = null;
	private BigDecimal totaleQuietanzato = null;
	private BigDecimal totaleValidato = null;
	private BigDecimal totaleRendicontato = null;
	private BigDecimal idProgetto= null;
	private BigDecimal idDichiarazioneSpesa = null;
//	private BigDecimal idDichiarazioneRend= null;
	


	/**
	 * @return the descVoceDiSpesa
	 */
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	/**
	 * @param descVoceDiSpesa the descVoceDiSpesa to set
	 */
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	
	/**
	 * @return the descVoceDiSpesaPadre
	 */
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	/**
	 * @param descVoceDiSpesaPadre the descVoceDiSpesaPadre to set
	 */
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	
	
	
	/**
	 * @return the idProgetto
	 */
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
	
	public void setImportoAmmessoFinanziamento(
			BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setTotaleQuietanzato(BigDecimal totaleQuietanzato) {
		this.totaleQuietanzato = totaleQuietanzato;
	}
	public BigDecimal getTotaleQuietanzato() {
		return totaleQuietanzato;
	}
	public void setTotaleValidato(BigDecimal totaleValidato) {
		this.totaleValidato = totaleValidato;
	}
	public BigDecimal getTotaleValidato() {
		return totaleValidato;
	}
	public void setTotaleRendicontato(BigDecimal totaleRendicontato) {
		this.totaleRendicontato = totaleRendicontato;
	}
	public BigDecimal getTotaleRendicontato() {
		return totaleRendicontato;
	}
	
//	public void setIdDichiarazioneRend(BigDecimal idDichiarazioneRend) {
//		this.idDichiarazioneRend = idDichiarazioneRend;
//	}
//	public BigDecimal getIdDichiarazioneRend() {
//		return idDichiarazioneRend;
//	}
	

	

	
	

}
