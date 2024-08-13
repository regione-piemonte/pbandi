/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.estremiBancari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EstremiBancariDTO implements Serializable {
	
	
	private Long idBanca;
	private String descBanca;
	private List<EstremiContoDTO> estremi;
	private boolean isSendToAmministrativoContabile;
	
	
	
	public EstremiBancariDTO() {
		super();
	}
	
	public EstremiBancariDTO(Long idBanca, String descBanca, List<EstremiContoDTO> estremi) {
		super();
		this.idBanca = idBanca;
		this.descBanca = descBanca;
		this.estremi = estremi;
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
	public List<EstremiContoDTO> getEstremi() {
		return estremi;
	}

	public void setEstremi(List<EstremiContoDTO> estremi) {
		this.estremi = estremi;
	}	
	public boolean getIsSendToAmministrativoContabile() {
		return isSendToAmministrativoContabile;
	}

	public void setSendToAmministrativoContabile(boolean isSendToAmministrativoContabile) {
		this.isSendToAmministrativoContabile = isSendToAmministrativoContabile;
	}

	public void addEstremo(EstremiContoDTO estremo) {
		if(estremi == null)
			estremi = new ArrayList<EstremiContoDTO>();
		
		estremi.add(estremo);
		
	}

	
	public static List<EstremiBancariDTO>  getMockElement() {
		
		 List<EstremiBancariDTO> list = new ArrayList<EstremiBancariDTO>();
		 list.add(new EstremiBancariDTO( 1L, "Banca", EstremiContoDTO.getMockElement()));
		 list.add(new EstremiBancariDTO( 2L, "Banca", EstremiContoDTO.getMockElement()));
		 
		 return list;
		
	}
	
	
	public static List<EstremiBancariDTO> extractData (List<EstremiBancariEstesiDTO> ebel) {
		
		List<EstremiBancariDTO> estremiBancari = new ArrayList <EstremiBancariDTO>();
		
		Long idBanca = null;
		int index = 0;
		
		for( EstremiBancariEstesiDTO ebe : ebel) {
			
			if(idBanca != null && idBanca.equals(ebe.getIdBanca())) {
				
				setEstremiConto(estremiBancari.get(index-1), ebe);
			}
			else {
				idBanca = ebe.getIdBanca();
				EstremiBancariDTO newEstremoBancario = new EstremiBancariDTO();
				newEstremoBancario.setIdBanca(ebe.getIdBanca());
				newEstremoBancario.setDescBanca(ebe.getDescBanca());
				setEstremiConto(newEstremoBancario, ebe);
				estremiBancari.add(newEstremoBancario);
				index++;
			}
		}
		
		return estremiBancari;
		
	}

	private static void setEstremiConto(EstremiBancariDTO newEstremoBancario, EstremiBancariEstesiDTO ebe) {		
		
		EstremiContoDTO estremoContoDTO = new EstremiContoDTO(ebe.getIdEstremiBancari(), ebe.getMoltiplicatore(), ebe.getTipologiaConto(), ebe.getIban(), ebe.getSendToAmmCont());
		newEstremoBancario.addEstremo(estremoContoDTO);
		
	}
	
}
