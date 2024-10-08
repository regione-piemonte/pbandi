/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class VoceDiSpesaImportiDichiarazioneVO extends GenericVO{
	
	
	private String descVoceDiSpesa = null;
	private String descVoceDiSpesaPadre = null;
	private BigDecimal importoAmmessoFinanziamento = null;
	private BigDecimal importoQuietanzato = null;
	private BigDecimal importoValidato = null;
	private BigDecimal idProgetto= null;
	private BigDecimal importoRendicontato= null;

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
	 * @return the importoQuietanzato
	 */
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	/**
	 * @param importoQuietanzato the importoQuietanzato to set
	 */
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	/**
	 * @return the importoValidato
	 */
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	/**
	 * @param importoValidato the importoValidato to set
	 */
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
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
	
	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}
	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}


	

	
	

}
