/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.business.manager.tracciamento;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface TracciamentoSrv {
	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";

	public java.lang.Long tracciaOperazione(java.lang.String descOperazione,Long idUtente);

	public void tracciaEntita(String sql, MapSqlParameterSource params,
			String operazione);

	public void tracciaEsitoPositivo(Long idTracciamento, long start);
	
	public void tracciaEsitoPositivoConMessaggio(Long idTracciamento, long start, String messaggio);

	public void tracciaEsitoNegativo(Long idTracciamento, long start,
			String messaggio);
	
	public void tracciaEsitoNegativo(Long idTracciamento, long start,
			String messaggio, Throwable eccezioneDaTracciare);

}
