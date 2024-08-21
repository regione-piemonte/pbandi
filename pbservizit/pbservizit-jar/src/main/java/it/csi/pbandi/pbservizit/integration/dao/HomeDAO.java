/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.vo.PermessoVO;

public interface HomeDAO {

	List<PermessoVO> getPermessi(String codiceRuolo, Long idSoggetto) throws ErroreGestitoException;

	Boolean isArchivioFileForBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario,
			String codiceRuolo) throws UtenteException, FileNotFoundException, IOException;
}
