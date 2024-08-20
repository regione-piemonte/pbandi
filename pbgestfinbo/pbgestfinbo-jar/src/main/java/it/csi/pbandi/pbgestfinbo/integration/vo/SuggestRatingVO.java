/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


public class SuggestRatingVO {

	private Long idRating;
	private String codiceRating;
	private String descRating;
	private Long idProvider;
	private String descProvider;
	
	
	
	

	public SuggestRatingVO() {
	}

	public SuggestRatingVO(Long idRating, String codiceRating, String descRating, Long idProvider,
			String descProvider) {
		this.idRating = idRating;
		this.codiceRating = codiceRating;
		this.descRating = descRating;
		this.idProvider = idProvider;
		this.descProvider = descProvider;
	}

	public Long getIdRating() {
		return idRating;
	}

	public void setIdRating(Long idRating) {
		this.idRating = idRating;
	}

	public String getCodiceRating() {
		return codiceRating;
	}

	public void setCodiceRating(String codiceRating) {
		this.codiceRating = codiceRating;
	}

	public String getDescRating() {
		return descRating;
	}

	public void setDescRating(String descRating) {
		this.descRating = descRating;
	}

	public Long getIdProvider() {
		return idProvider;
	}

	public void setIdProvider(Long idProvider) {
		this.idProvider = idProvider;
	}

	public String getDescProvider() {
		return descProvider;
	}

	public void setDescProvider(String descProvider) {
		this.descProvider = descProvider;
	}

	@Override
	public String toString() {
		return "SuggestRatingVO [idRating=" + idRating + ", codiceRating=" + codiceRating + ", descRating=" + descRating
				+ ", idProvider=" + idProvider + ", descProvider=" + descProvider + "]";
	}




}
	