/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class ImpegnoKeyVO  {
	
	
	private String annoEsercizio;  //ANNO_ESERCIZIO (4). Non valorizzare. Quindi non creo set e get
	private String annoImpegno;  //ANNOIMP (4)
	private Integer numeroImpegno;  //NIMP (22)

	
	
	public void setAnnoImpegno(String annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public String getAnnoImpegno() {
		return annoImpegno;
	}
	public void setNumeroImpegno(Integer numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	public Integer getNumeroImpegno() {
		return numeroImpegno;
	}
	
	

}
