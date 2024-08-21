/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class ConsultaImpegniVO extends BaseVO{
	
		
	private Integer maxRec;
	private String annoEser;

	
	private ImpegnoVO[] impegni; //Output del servizio consultaImpegni.
	
	private ProvvedimentoKeyVO[] provvedimentiKeys;	
	private CapitoloKeyVO[] capitoliKeys;
	private ImpegnoKeyVO[] impegniKeys;
	
	public Integer getMaxRec() {
		return maxRec;
	}
	public void setMaxRec(Integer maxRec) {
		this.maxRec = maxRec;
	}
	public String getAnnoEser() {
		return annoEser;
	}
	public void setAnnoEser(String annoEser) {
		this.annoEser = annoEser;
	}
	public ImpegnoVO[] getImpegni() {
		return impegni;
	}
	public void setImpegni(ImpegnoVO[] impegni) {
		this.impegni = impegni;
	}
	public ProvvedimentoKeyVO[] getProvvedimentiKeys() {
		return provvedimentiKeys;
	}
	public void setProvvedimentiKeys(ProvvedimentoKeyVO[] provvedimentiKeys) {
		this.provvedimentiKeys = provvedimentiKeys;
	}
	public CapitoloKeyVO[] getCapitoliKeys() {
		return capitoliKeys;
	}
	public void setCapitoliKeys(CapitoloKeyVO[] capitoliKeys) {
		this.capitoliKeys = capitoliKeys;
	}
	public ImpegnoKeyVO[] getImpegniKeys() {
		return impegniKeys;
	}
	public void setImpegniKeys(ImpegnoKeyVO[] impegniKeys) {
		this.impegniKeys = impegniKeys;
	}
	

}