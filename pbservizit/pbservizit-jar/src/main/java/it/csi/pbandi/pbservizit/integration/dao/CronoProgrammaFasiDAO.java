/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.dto.EsitoDTO;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.AllegatiCronoprogrammaFasi;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;

public interface CronoProgrammaFasiDAO {

	public List<CronoprogrammaFasiItem> getDataCronoprogramma(Long idProgetto) throws Exception;

	public List<AllegatiCronoprogrammaFasi> getAllegatiByIter(Long idProgetto, Long idIter) throws Exception;

	public boolean existRowInProgettoFaseMonit(Long idProgetto, Long idFaseMonit) throws Exception;

	public void insertDateInProgettoFaseMonit(Long idProgetto, CronoprogrammaFasiItem tmp, Long idUtente)
			throws Exception;

	public void updateDateInProgettoFaseMonit(Long idProgetto, CronoprogrammaFasiItem tmp, Long idUtente)
			throws Exception;

	public EsitoDTO disassociaAllegato(Long idDocumentoIndex, Long idProgettoIter, Long idProgetto, Long idUtente,
			String idIride) throws Exception;

	public EsitoAssociaFilesDTO associaAllegati(AssociaFilesRequest associaFilesRequest, Long idIter, Long idUtente)
			throws InvalidParameterException, Exception;

	public Boolean getFlagFaseChiusaProgettoIter(Long idProgetto, Long idIter)
			throws InvalidParameterException, Exception;

	public void insertOrUpdateProgettoIter(Long idProgetto, Long idIter);
}
