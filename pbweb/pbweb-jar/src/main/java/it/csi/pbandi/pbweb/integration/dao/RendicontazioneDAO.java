/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;
import it.csi.pbandi.pbweb.dto.DatiColonnaQteSalDTO;
import it.csi.pbandi.pbweb.dto.DirezioneDTO;
import it.csi.pbandi.pbweb.dto.SalCorrenteDTO;
import it.csi.pbandi.pbweb.dto.rendicontazione.InizializzaRendicontazioneDTO;

public interface RendicontazioneDAO {
	InizializzaRendicontazioneDTO inizializzaRendicontazione(long idProgetto, long idBandoLinea, long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idSoggettoBeneficiario)
			throws InvalidParameterException, Exception;

	SalCorrenteDTO getSALCorrente(long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride,
			Long idBeneficiario) throws InvalidParameterException, Exception;

	SalCorrenteDTO getSALByIdDocSpesa(long idProgetto, long idDocumentoSpesa, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;

	List<DatiColonnaQteSalDTO> getDatiColonneQteSALCorrente(Long idProgetto, Long idIter, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	List<DirezioneDTO> getDirezioniSettori(Long idUtente, String idIride);
}
