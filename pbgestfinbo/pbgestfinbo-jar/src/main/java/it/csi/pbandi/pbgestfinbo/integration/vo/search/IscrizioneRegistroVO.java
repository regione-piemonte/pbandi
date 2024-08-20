/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class IscrizioneRegistroVO {
	
	private String numIscrizione;
	
	private Date dtIscrizione;
	
	private String descProvincia;

	public IscrizioneRegistroVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNumIscrizione() {
		return numIscrizione;
	}

	public void setNumIscrizione(String numIscrizione) {
		this.numIscrizione = numIscrizione;
	}

	public Date getDtIscrizione() {
		return dtIscrizione;
	}

	public void setDtIscrizione(Date dtIscrizione) {
		this.dtIscrizione = dtIscrizione;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	
	

}
