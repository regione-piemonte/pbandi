/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.InfoLineaDiInterventoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.CausaleTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.EsitoSalvaTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.FiltroTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.SoggettoTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.TrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.trasferimenti.TrasferimentiException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiTrasferimentoDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CausaleTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettoTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbservizit.util.NumberUtil;

public class TrasferimentiManager {
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private PbandiTrasferimentoDAOImpl pbandiTrasferimentoDAO;

	public PbandiTrasferimentoDAOImpl getPbandiTrasferimentoDAO() {
		return pbandiTrasferimentoDAO;
	}

	public void setPbandiTrasferimentoDAO(
			PbandiTrasferimentoDAOImpl pbandiTrasferimentoDAO) {
		this.pbandiTrasferimentoDAO = pbandiTrasferimentoDAO;
	}
	
	
	/**
	 * Trova i trasferimenti secondo i criteri di ricerca specificati nel filtro.
	 * 
	 * @param idUtente Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param filtro Criteri per la ricerca dei trasferimenti.
	 * @return Elenco dei trasferimenti trovati.
	 */
	public TrasferimentoDTO[] findTrasferimenti(Long idUtente, String identitaDigitale, FiltroTrasferimentoDTO filtro) {
	
		List<TrasferimentoVO> result = getPbandiTrasferimentoDAO().findTrasferimenti(filtro);
		return beanUtil.transform(result, TrasferimentoDTO.class);
	}	

	public TrasferimentoDTO findTrasferimento(Long idTrasferimento) {

		TrasferimentoVO result = getPbandiTrasferimentoDAO().findTrasferimento(idTrasferimento);
		return beanUtil.transform(result, TrasferimentoDTO.class);
	}	
	public CausaleTrasferimentoDTO[] findCausaliTrasferimenti() {

		List<CausaleTrasferimentoVO> result = getPbandiTrasferimentoDAO().findElencoCausaliTrasferimenti();
		return beanUtil.transform(result, CausaleTrasferimentoDTO.class);
	}	
	public SoggettoTrasferimentoDTO[] findSoggettiTrasferimento() {

		List<SoggettoTrasferimentoVO> result = getPbandiTrasferimentoDAO().findSoggettiTrasferimento();
		return beanUtil.transform(result, SoggettoTrasferimentoDTO.class);
	}	
	
	/**
	 * Crea una nuov trasferimento.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idTrasferimenti  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo dell'irregolarit&agrave; creata;
	 *         in caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws TrasferimentiException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaTrasferimentoDTO creaTrasferimento(Long idUtente, String identitaDigitale, TrasferimentoDTO trasferimento) 
		throws CSIException, SystemException, UnrecoverableException, TrasferimentiException {
		// creo il trasferimento
		return inserisciTrasferimento(idUtente, identitaDigitale, trasferimento);
	}


	/**
	 * Modifica un'irregolarit&agrave; esistente.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param trasferimento  Dati dell'irregolarit&agrave; da modificare.
	 * @param modificaDatiAggiuntivi true se l'allegato dati aggiuntivi &egrave; da modificare, false se &egrave; da inserire.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws TrasferimentiException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaTrasferimentoDTO modificaTrasferimento(Long idUtente, String identitaDigitale, TrasferimentoDTO trasferimento) 
		throws CSIException, SystemException, UnrecoverableException, TrasferimentiException {
		
		EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();
		
		// 1 - conversione DTO -> VO
		PbandiTTrasferimentoVO trasferimentoVO = beanUtil.transform(trasferimento, PbandiTTrasferimentoVO.class);
		trasferimentoVO.setIdUtenteAgg(new BigDecimal(idUtente));
		
		// 2 - modifica genericDAO del PbandiTTrasferimentoVO
		try {
//			trasferimentoVO.setIdUtenteAgg(new BigDecimal(idUtente));
//			genericDAO.update(trasferimentoVO);

			getPbandiTrasferimentoDAO().updateTrasferimento(idUtente, trasferimentoVO);

			
		} catch (Exception e) {
			logger.error("Errore in modifica su PBANDI_T_TRASFERIMENTO", e);		
			throw new TrasferimentiException(ErrorConstants.ERRORE_SERVER, e);
		}

		// 7 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		esito.setIdTrasferimento(trasferimento.getIdTrasferimento());
		return esito;
	}
	
	public EsitoSalvaTrasferimentoDTO eliminaTrasferimento(Long idTrasferimento) 
		throws CSIException, SystemException, UnrecoverableException, TrasferimentiException {
		
		EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();
		
		try {
	//		trasferimentoVO.setIdUtenteAgg(new BigDecimal(idUtente));
	
			getPbandiTrasferimentoDAO().deleteTrasferimento(idTrasferimento);
	
			
		} catch (Exception e) {
			logger.error("Errore in modifica su PBANDI_T_TRASFERIMENTO", e);		
			throw new TrasferimentiException(ErrorConstants.ERRORE_SERVER, e);
		}
	
		// 7 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		esito.setIdTrasferimento(idTrasferimento);
		return esito;
}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	
	
	/**
	 * Effettua le operazioni un nuovo trasferimento.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idTrasferimenti  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo dell'irregolarit&agrave; creata;
	 *         in caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws TrasferimentiException  Eccezione applicativa lanciata programmaticamente.
	 */
	private EsitoSalvaTrasferimentoDTO inserisciTrasferimento(Long idUtente, String identitaDigitale, TrasferimentoDTO trasferimento)
		throws CSIException, SystemException, UnrecoverableException, TrasferimentiException {
		
		EsitoSalvaTrasferimentoDTO esito = new EsitoSalvaTrasferimentoDTO();

		// 1 - conversione DTO -> VO
		PbandiTTrasferimentoVO trasferimentoVO = beanUtil.transform(trasferimento, PbandiTTrasferimentoVO.class);
		trasferimentoVO.setIdUtenteIns(new BigDecimal(idUtente));
		
		logger.info("\n\n INSERISCI TRASFERIMENTO \n\n" + trasferimentoVO.toString() + " \n\n");
	
		// 2 - inserimento genericDAO del PbandiTTrasferimentoVO
		try {
//			trasferimentoVO = genericDAO.insert(trasferimentoVO);
			
			getPbandiTrasferimentoDAO().inserisciTrasferimento(idUtente, trasferimentoVO);
			
		} catch (Exception e) {
			logger.error("Errore in inserimento su PBANDI_T_TRASFERIMENTO", e);
			throw new TrasferimentiException(ErrorConstants.ERRORE_SERVER, e);
		}		
		
		// 3 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		esito.setIdTrasferimento(NumberUtil.toLong(trasferimentoVO.getIdTrasferimento()));
		return esito;
		
	}

	public InfoLineaDiInterventoDTO[] findNormativeTrasferimento() {
		List<LineaDiInterventoVO> result = getPbandiTrasferimentoDAO().findNormativeTrasferimento();
		return beanUtil.transform(result, InfoLineaDiInterventoDTO.class);
	}
	
	
//	/**
//	 * 
//	 * @return
//	 */
//	private BigDecimal getIdEntita() {
//		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
//		entitaVO.setNomeEntita("PBANDI_T_TRASFERIMENTO");
//		entitaVO = genericDAO.findSingleWhere(entitaVO);
//		return entitaVO.getIdEntita();
//	}
	
}
