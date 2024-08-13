/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BancaSuggestVO implements Serializable {
	
	
	private Long idBanca;
	private String descBanca;	
	
	
	public BancaSuggestVO() {
		super();
	}
	
	public BancaSuggestVO(Long idBanca, String descBanca, List<EstremiContoDTO> estremi) {
		super();
		this.idBanca = idBanca;
		this.descBanca = descBanca;		
	}





	public Long getIdBanca() {
		return idBanca;
	}
	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}	
	public String getDescBanca() {
		return descBanca;
	}
	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}
	

	
	
	
}
