/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao;


import java.util.List;
import it.csi.pbandi.pbservrest.dto.StatoCredito;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;

public interface CreditoDAO {

	List<StatoCredito> getStatoCredito(String numeroDomanda) throws RecordNotFoundException, ErroreGestitoException;
		
}