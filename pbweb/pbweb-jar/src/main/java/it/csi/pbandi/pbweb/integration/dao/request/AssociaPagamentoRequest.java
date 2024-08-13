/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.documentoDiSpesa.PagamentoFormDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoVoceDiSpesaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class AssociaPagamentoRequest {
	
	private PagamentoFormDTO pagamentoFormDTO;
	private Long idDocumentoDiSpesa;
	private Long idProgetto;
	private Long idBandoLinea;
	private Boolean forzaOperazione;		// se true alcuni controlli vengono ignorati.
	private Boolean validazione;			// true se si è in Validazione o in ValidazioneFinale.
	private ArrayList<PagamentoVoceDiSpesaDTO> vociDiSpesa;
	
	
	public PagamentoFormDTO getPagamentoFormDTO() {
		return pagamentoFormDTO;
	}

	public void setPagamentoFormDTO(PagamentoFormDTO pagamentoFormDTO) {
		this.pagamentoFormDTO = pagamentoFormDTO;
	}

	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public Boolean getForzaOperazione() {
		return forzaOperazione;
	}

	public void setForzaOperazione(Boolean forzaOperazione) {
		this.forzaOperazione = forzaOperazione;
	}

	public Boolean getValidazione() {
		return validazione;
	}

	public void setValidazione(Boolean validazione) {
		this.validazione = validazione;
	}
	
	

	public ArrayList<PagamentoVoceDiSpesaDTO> getVociDiSpesa() {
		return vociDiSpesa;
	}

	public void setVociDiSpesa(ArrayList<PagamentoVoceDiSpesaDTO> vociDiSpesa) {
		this.vociDiSpesa = vociDiSpesa;
	}

	@Override
	public String toString() {
		String ogg= "AssociaPagamentoRequest [pagamentoFormDTO=" + pagamentoFormDTO + ", idDocumentoDiSpesa="
				+ idDocumentoDiSpesa + ", idProgetto=" + idProgetto + ", idBandoLinea=" + idBandoLinea
				+ ", forzaOperazione=" + forzaOperazione + ", validazione=" + validazione ;
		
		if(vociDiSpesa!=null && !vociDiSpesa.isEmpty()) {
			ogg = ogg +", vociDiSpesa size=="+vociDiSpesa.size()+"{";
			for (PagamentoVoceDiSpesaDTO pagamentoVoceDiSpesaDTO : vociDiSpesa) {
				if(pagamentoVoceDiSpesaDTO!=null)
					ogg = ogg + pagamentoVoceDiSpesaDTO.toString();
			}
			ogg = ogg +"}";
		}else {
			ogg = ogg +", vociDiSpesa=NULL";
		}
		ogg = ogg + "]";
		return ogg;
	}

}
