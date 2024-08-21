/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbservizit.business.api.HomeApi;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.integration.dao.HomeDAO;
import it.csi.pbandi.pbservizit.integration.vo.OperazioneVO;
import it.csi.pbandi.pbservizit.integration.vo.PermessoVO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;

@Service
public class HomeApiServiceImpl implements HomeApi {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private HomeDAO homeDAO;

	@Override
	public Response getOperazioni(HttpServletRequest req) throws ErroreGestitoException {
		String prf = "[HomeServiceImpl::getOperazioni]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		LOG.info(prf + " userInfo=" + userInfo);

		List<OperazioneVO> operazioni = new ArrayList<OperazioneVO>();

		LOG.info(prf + " homeDAO=" + homeDAO);

		try {
			List<PermessoVO> permessi = homeDAO.getPermessi(userInfo.getCodiceRuolo(), userInfo.getIdSoggetto());

			if (permessi != null) {
				for (PermessoVO permesso : permessi) {
					switch (permesso.getDescBrevePermesso()) {
						case UseCaseConstants.UC_TRSWKS001:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSWKS003:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUCERT:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_CERTIFICAZIONE, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_OPEIRR001_1:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_REGISTRO_CONTROLLI,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUTRS:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_TRASFERIMENTI, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUBIL:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_BILANCIO, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSRENMAS01:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_RENDICONTAZIONE_MASSIVA,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUNOTIF:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_NOTIFICHE_VIA_MAIL,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENURIL:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_RILEVAZIONE_CONTROLLI,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO010:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_DOCUMENTI_PROGETTO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_OPEGECO001:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO005:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_CONFIGURAZIONE_BANDO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO011:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_UTENTI, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO025:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_ASSOCIAZIONE_PERMESSO_RUOLO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO026:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_ASSOCIAZIONE_RUOLO_PERMESSO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO027:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_TEMPLATES,
									permesso.getDescMenu()));
							break;
//						NASCONDO verifica servizi perchè l'unico servizio rimasto attivo è ACTA
//						case UseCaseConstants.UC_VERSERV:
//							operazioni.add(
//									new OperazioneVO(Constants.ID_OPERAZIONE_VERIFICA_SERVIZI, permesso.getDescMenu()));
//							break;
						case UseCaseConstants.UC_TRSCLA001:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_CONSOLE_APPLICATIVA,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSACC002:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_CAMBIA_BENEFICIARIO,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_TRSBKO002:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_ASSOCIAZIONE_ISTRUTTORE_PROGETTI,
											permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_OPEANAG003:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUMONITCRED:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUGESTRICH:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUNEWS:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_NEWS, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUPROPVARIAZIONI:
							operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_PROPOSTE_VARIAZIONI_STATO_CREDITI,
									permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_OPE_CDU_V01_CNT006:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_CAMPIONAMENTO, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUGESTGAR:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_GARANZIE, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUAMBEROG:
							operazioni.add(
									new OperazioneVO(Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI, permesso.getDescMenu()));
							break;
						case UseCaseConstants.UC_MENUREV:
                            operazioni.add(
                                    new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_REVOCHE, permesso.getDescMenu()));
                            break;
						case UseCaseConstants.UC_PBAN_OPE_CDU_V01_CNT001:
                            operazioni.add(
                                    new OperazioneVO(Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO, permesso.getDescMenu()));
                            break;
						case UseCaseConstants.UC_MENUITER:
                            operazioni.add(
                                    new OperazioneVO(Constants.ID_OPERAZIONE_ITER_AUTORIZZATIVI, permesso.getDescMenu()));
                            break;
						case UseCaseConstants.UC_MENUBOSTORAGE:
                            operazioni.add(
                                    new OperazioneVO(Constants.ID_OPERAZIONE_BO_STORAGE, permesso.getDescMenu()));
                            break;
						case UseCaseConstants.UC_MENUGESTRIASS:
                            operazioni.add(
                                    new OperazioneVO(Constants.ID_OPERAZIONE_GESTIONE_RIASSICURAZIONI, permesso.getDescMenu()));
                            break;
						default:
							break;
					}
				}
			}
			if (isArchivioFileVisible(userInfo)) {
				operazioni.add(new OperazioneVO(Constants.ID_OPERAZIONE_ARCHIVIO_FILE, "Archivio File"));
			}

			return Response.ok(operazioni).build();

		} catch (ErroreGestitoException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PermessoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PermessoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	private boolean isArchivioFileVisible(UserInfoSec userInfo) throws ErroreGestitoException {
		try {
			boolean isVisible = false;
			/*
			 * Visibile solo se l'utente ha selezionato un beneficiario ed esiste almeno un
			 * bandolinea relativo ai progetti ai quali l'utente beneficiario e' associato
			 * che ha abilitato la regola BR42 e l' utente e' abilitato al caso d'uso
			 * OPEARC001
			 */
			Long idSoggetto = null;

			if (userInfo.getBeneficiarioSelezionato() != null) {
				idSoggetto = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
				if (userInfo.getCodiceRuolo().equalsIgnoreCase("BEN-MASTER")
						|| userInfo.getCodiceRuolo().equalsIgnoreCase("PERSONA-FISICA")) {
					isVisible = homeDAO.isArchivioFileForBeneficiario(userInfo.getIdUtente(), userInfo.getIdIride(),
							idSoggetto, "BENEFICIARIO");
				}
			} else {
				idSoggetto = userInfo.getIdSoggetto();
				if (idSoggetto != null) {
					if (userInfo.getBeneficiariCount() != null && userInfo.getBeneficiariCount().compareTo(2L) < 0) {
						isVisible = homeDAO.isArchivioFileForBeneficiario(userInfo.getIdUtente(), userInfo.getIdIride(),
								idSoggetto, userInfo.getCodiceRuolo());
					}
				}
			}
			LOG.info("[HomeServiceImpl::isArchivioFileVisible] isArchivioFileVisible: " + isVisible);
			return isVisible;
		} catch (Exception ex) {
			LOG.error("[HomeServiceImpl::isArchivioFileVisible] Errore durante l'esecuzione del metodo", ex);
			throw new ErroreGestitoException("Errore durante l'esecuzione del metodo isArchivioFileVisible", ex);
		}
	}
}
