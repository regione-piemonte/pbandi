/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.tracciamento;

public class TracciamentoDTO implements java.io.Serializable {
	private Long idEntita;
	private Long idAttributo;
	private UtenteDTO utente = new UtenteDTO();
	private Long idTracciamento;
	private Long idTarget;
	private String descAttivita;
	private String valorePrecedente;
	private String valoreSuccessivo;
	private Long methodStartTimeMillis;

	/**
	 * @return the idTracciamento
	 */
	public Long getIdTracciamento() {
		return idTracciamento;
	}

	/**
	 * @param idTracciamento
	 *            the idTracciamento to set
	 */
	public void setIdTracciamento(Long idTracciamento) {
		this.idTracciamento = idTracciamento;
	}

	/**
	 * @return the idTarget
	 */
	public Long getIdTarget() {
		return idTarget;
	}

	/**
	 * @param idTarget
	 *            the idTarget to set
	 */
	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

	/**
	 * @return the valorePrecedente
	 */
	public String getValorePrecedente() {
		return valorePrecedente;
	}

	/**
	 * @param valorePrecedente
	 *            the valorePrecedente to set
	 */
	public void setValorePrecedente(String valorePrecedente) {
		this.valorePrecedente = valorePrecedente;
	}

	/**
	 * @return the valoreSuccessivo
	 */
	public String getValoreSuccessivo() {
		return valoreSuccessivo;
	}

	/**
	 * @param valoreSuccessivo
	 *            the valoreSuccessivo to set
	 */
	public void setValoreSuccessivo(String valoreSuccessivo) {
		this.valoreSuccessivo = valoreSuccessivo;
	}

	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}

	public Long getIdEntita() {
		return idEntita;
	}

	public Long getIdAttributo() {
		return idAttributo;
	}

	public void setIdAttributo(Long idAttributo) {
		this.idAttributo = idAttributo;
	}

	

	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	}

	public String getDescAttivita() {
		return descAttivita;
	}

	public void setMethodStartTimeMillis(Long methodStartTimeMillis) {
		this.methodStartTimeMillis = methodStartTimeMillis;
	}

	public Long getMethodStartTimeMillis() {
		return methodStartTimeMillis;
	}
}
