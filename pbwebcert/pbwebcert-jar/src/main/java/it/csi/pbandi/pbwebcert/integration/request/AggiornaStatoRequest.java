/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

public class AggiornaStatoRequest {
	private Long idProposta;
	private Long idStatoNuovo;
	private Long idUtente;
	public Long getIdStatoNuovo() {
		return idStatoNuovo;
	}
	public void setIdStatoNuovo(Long idStatoNuovo) {
		this.idStatoNuovo = idStatoNuovo;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public Long getIdProposta() {
		return idProposta;
	}
	public void setIdProposta(Long idProposta) {
		this.idProposta = idProposta;
	}
}
