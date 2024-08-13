/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ModAgevEstremiBancariDTO implements Serializable {	
	
	
	
	private ModalitaAgevolazioneDTO modalitaAgevolazione;
	private List<EstremiBancariDTO> estremiBancariList;	
	
	
	
	
	
	public ModAgevEstremiBancariDTO() {
		
	}
	
	public ModAgevEstremiBancariDTO(ModalitaAgevolazioneDTO modalitaAgevolazione, List<EstremiBancariDTO> estremiBancariList ) {
		this.modalitaAgevolazione = modalitaAgevolazione;	
		this.estremiBancariList = estremiBancariList;
	}



	

	public ModalitaAgevolazioneDTO getModalitaAgevolazione() {
		return modalitaAgevolazione;
	}
	public void setModalitaAgevolazione(ModalitaAgevolazioneDTO modalitaAgevolazione) {
		this.modalitaAgevolazione = modalitaAgevolazione;
	}

	public List<EstremiBancariDTO> getEstremiBancariList() {
		return estremiBancariList;
	}
	public void setEstremiBancariList(List<EstremiBancariDTO> estremiBancariList) {
		this.estremiBancariList = estremiBancariList;
	}



	public static List<ModAgevEstremiBancariDTO>  getMockElement() {
		
		 List<ModAgevEstremiBancariDTO> list = new ArrayList<ModAgevEstremiBancariDTO>();
		 list.add(new ModAgevEstremiBancariDTO( ModalitaAgevolazioneDTO.getMockElement(), EstremiBancariDTO.getMockElement()));
		 list.add(new ModAgevEstremiBancariDTO( ModalitaAgevolazioneDTO.getMockElement(), EstremiBancariDTO.getMockElement()));
		 
		 return list;
		
	}
	
}
