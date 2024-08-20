/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ProgettoEstrattoCampVO;

public class ProgettiEscludiEstrattiDTO {
	
	private ArrayList<ProgettoEstrattoCampVO>  esclusi; 
	private ArrayList<ProgettoEstrattoCampVO>  estratti;
	private ArrayList<ProgettoEstrattoCampVO>  campionati;
	
	
	
	public ArrayList<ProgettoEstrattoCampVO> getEsclusi() {
		return esclusi;
	}
	public void setEsclusi(ArrayList<ProgettoEstrattoCampVO> esclusi) {
		this.esclusi = esclusi;
	}
	public ArrayList<ProgettoEstrattoCampVO> getEstratti() {
		return estratti;
	}
	public void setEstratti(ArrayList<ProgettoEstrattoCampVO> estratti) {
		this.estratti = estratti;
	} 
	public ArrayList<ProgettoEstrattoCampVO> getCampionati() {
		return campionati;
	}
	public void setCampionati(ArrayList<ProgettoEstrattoCampVO> campionati) {
		this.campionati = campionati;
	}
	
	
	

}
