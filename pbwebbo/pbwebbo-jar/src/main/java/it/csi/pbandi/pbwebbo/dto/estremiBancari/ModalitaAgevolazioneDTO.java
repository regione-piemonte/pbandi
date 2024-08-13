/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ModalitaAgevolazioneDTO implements Serializable {
	

	private Long idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione; 	
	
	
	public ModalitaAgevolazioneDTO() {
		
	}
	
	
	
	public ModalitaAgevolazioneDTO(Long idModalitaAgevolazione, String descBreveModalitaAgevolaz,String descModalitaAgevolazione) {
		super();
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
		this.descModalitaAgevolazione = descModalitaAgevolazione;	
	}



	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	
	

	
	public static ModalitaAgevolazioneDTO getMockElement() {
		
		return new ModalitaAgevolazioneDTO( 1L,"str", "Modalita agevolazione");
		
		
	}
	
}
