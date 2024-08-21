/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;

public interface CupDAO {

	public boolean testConnection();

	public boolean domandaExist(String numeroDomanda);

	public boolean aggiornaCodiceCUP(String codiceCup, String numeroDomanda);
}
