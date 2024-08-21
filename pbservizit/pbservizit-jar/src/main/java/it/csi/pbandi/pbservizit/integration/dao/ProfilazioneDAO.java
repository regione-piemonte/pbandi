/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import it.csi.pbandi.pbservizit.exception.DaoException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbservizit.dto.AvvisoUtenteDTO;
import it.csi.pbandi.pbservizit.dto.profilazione.BeneficiarioCount;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.integration.vo.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;

public interface ProfilazioneDAO {

	UserInfoDTO getInfoUtente(String codFisc, String nome, String cognome) throws UtenteException;

	BeneficiarioCount countBeneficiari(Long idUtente, String codiceFiscale, String ruoloIride) throws DaoException;

	BeneficiarioSec findBeneficiarioByIdSoggettoBen(Long idUtente, String codiceFiscale, String ruoloIride,
			Long idSoggettoBeneficiario) throws DaoException;

	List<BeneficiarioSec> findBeneficiariByDenominazione(Long idUtente, String codiceFiscale, String ruoloIride,
			String denominazione) throws DaoException;

	void memorizzaIride(Long idUtenteFittizio, String idIrideShib, String sessionId, String action) throws DaoException;

	List<TipoAnagraficaVO> findTipiAnagrafica(BigDecimal idSoggetto) throws DaoException;

	PbandiTSoggettoVO findSoggetto(String codFiscale) throws DaoException;

	PbandiTUtenteVO findUtente(String codFiscale) throws DaoException;

	boolean hasPermesso(Long idUSoggetto, Long idutente, String descBreveTipoAnagrafica, String descBrevePermesso)
			throws DaoException;

	public BeneficiarioVO findBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario)
			throws DaoException;

	ArrayList<AvvisoUtenteDTO> avvisi(String codiceRuolo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	Boolean isRegolaApplicabileForBandoLinea(Long idBandoLinea, String codiceRegola, Long idUtente, String idIride)
			throws DaoException;

}
