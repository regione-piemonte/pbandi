/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class ProvvedimentoKeyVO {
	
	private String annoProvvedimento;
	private String numeroProvvedimento;
	private String direzioneProvvedimento;
	private String tipologiaProvvedimento; // non valorizzare. Quindi non creo set e get.
	
	
	public void setAnnoProvvedimento(String annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	public String getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setDirezioneProvvedimento(String direzioneProvvedimento) {
		this.direzioneProvvedimento = direzioneProvvedimento;
	}
	public String getDirezioneProvvedimento() {
		return direzioneProvvedimento;
	}
	
	

	
}	
	
	
