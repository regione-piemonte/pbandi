/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.util.Date;

public class StoricoRichiestaVO {
	private String id_richiesta;
	private String destinatario_mittente;
	private Date dt_comunicazione_esterna;
	private String numero_protocollo;
	private String motivazione;
	private String cognome;
	private String desc_tipo_comunicazione;
	
	public String getId_richiesta() {
		return id_richiesta;
	}
	public void setId_richiesta(String id_richiesta) {
		this.id_richiesta = id_richiesta;
	}
	public String getDestinatario_mittente() {
		return destinatario_mittente;
	}
	public void setDestinatario_mittente(String destinatario_mittente) {
		this.destinatario_mittente = destinatario_mittente;
	}
	public Date getDt_comunicazione_esterna() {
		return dt_comunicazione_esterna;
	}
	public void setDt_comunicazione_esterna(Date dt_comunicazione_esterna) {
		this.dt_comunicazione_esterna = dt_comunicazione_esterna;
	}
	public String getNumero_protocollo() {
		return numero_protocollo;
	}
	public void setNumero_protocollo(String numero_protocollo) {
		this.numero_protocollo = numero_protocollo;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDesc_tipo_comunicazione() {
		return desc_tipo_comunicazione;
	}
	public void setDesc_tipo_comunicazione(String desc_tipo_comunicazione) {
		this.desc_tipo_comunicazione = desc_tipo_comunicazione;
	}
	@Override
	public String toString() {
		return "StoricoRichiestaVO [id_richiesta=" + id_richiesta + ", destinatario_mittente=" + destinatario_mittente
				+ ", dt_comunicazione_esterna=" + dt_comunicazione_esterna + ", numero_protocollo=" + numero_protocollo
				+ ", motivazione=" + motivazione + ", cognome=" + cognome + ", desc_tipo_comunicazione="
				+ desc_tipo_comunicazione + "]";
	}
	
	
}
