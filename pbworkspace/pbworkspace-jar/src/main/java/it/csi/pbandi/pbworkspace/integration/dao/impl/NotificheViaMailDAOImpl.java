/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.dao.NotificheViaMailDAO;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.NotificaAlertVORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PbandiDFrequenzaVORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PbandiTEmailSoggettoVORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PbandiTNotificheIstruttoreVORowMapper;
import it.csi.pbandi.pbworkspace.integration.vo.BandoProgettiVO;
import it.csi.pbandi.pbworkspace.integration.vo.FrequenzaVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificaAlertVO;
import it.csi.pbandi.pbworkspace.integration.vo.NotificheFrequenzeVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTEmailSoggettoVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificheIstruttoreVO;
import it.csi.pbandi.pbworkspace.integration.vo.ProgettoNotificheVO;
import it.csi.pbandi.pbworkspace.util.Constants;

@Service
public class NotificheViaMailDAOImpl extends JdbcDaoSupport implements NotificheViaMailDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public NotificheViaMailDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public NotificheViaMailDAOImpl() {
	}

	@Override
	public String getMail(Long idUtente, String idIride, Long idSoggetto)
			throws UtenteException, ErroreGestitoException {
		String prf = "[NotificheViaMailDAOImpl::getMail]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}
		try {
			StringBuilder sql = new StringBuilder("");

			sql.append("select EMAIL_SOGGETTO as emailSoggetto, ID_SOGGETTO as idSoggetto ");
			sql.append("from PBANDI_T_EMAIL_SOGGETTO ");
			sql.append("where ID_SOGGETTO = " + idSoggetto);
			LOG.debug(sql.toString());

			List<PbandiTEmailSoggettoVO> mails = getJdbcTemplate().query(sql.toString(),
					new PbandiTEmailSoggettoVORowMapper());
			if (mails != null && mails.size() > 0) {
				if (mails.get(0) != null) {
					return mails.get(0).getEmailSoggetto();
				}
			}
			return null;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PbandiTEmailSoggettoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PbandiTEmailSoggettoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public void saveMail(Long idUtente, String idIride, Long idSoggetto, String mail)
			throws UtenteException, InvalidParameterException, ErroreGestitoException {
		String prf = "[NotificheViaMailDAOImpl::saveMail]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}
		if (mail == null) {
			throw new InvalidParameterException("mail is null");
		}
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM PBANDI_T_EMAIL_SOGGETTO ");
			sql.append("WHERE ID_SOGGETTO = " + idSoggetto);
			LOG.info(sql.toString());
			getJdbcTemplate().update(sql.toString());

			sql = new StringBuilder("");
			sql.append("INSERT INTO PBANDI_T_EMAIL_SOGGETTO ");
			sql.append("(EMAIL_SOGGETTO , ID_SOGGETTO) ");
			sql.append("VALUES ('" + mail + "', " + idSoggetto + ")");
			LOG.info(sql.toString());
			getJdbcTemplate().update(sql.toString());

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PbandiTEmailSoggettoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PbandiTEmailSoggettoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public NotificheFrequenzeVO getNotificheFrequenze(Long idUtente, String idIride, Long idSoggetto)
			throws UtenteException, ErroreGestitoException {
		String prf = "[NotificheViaMailDAOImpl::getNotificheFrequenze]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}

		try {
			NotificheFrequenzeVO notificheFrequenze = new NotificheFrequenzeVO();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DESCR_FREQUENZA AS descrFrequenza, ID_FREQUENZA AS idFrequenza ");
			sql.append("FROM PBANDI_D_FREQUENZA ");
			sql.append(
					"WHERE (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) AND trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1))");
			LOG.debug(sql.toString());

			List<FrequenzaVO> frequenze = getJdbcTemplate().query(sql.toString(), new PbandiDFrequenzaVORowMapper());

			if (frequenze != null & frequenze.size() > 0) {
				LOG.info(prf + " frequenze found: " + frequenze.size());
				notificheFrequenze.setFrequenze(frequenze);
			} else {
				LOG.info(prf + " frequenze found: 0");
			}

			sql = new StringBuilder("");

			sql.append(
					"SELECT PBANDI_D_NOTIFICA_ALERT.ID_NOTIFICA_ALERT AS idNotificaAlert, ID_SOGGETTO_NOTIFICA AS idSoggettoNotifica, PBANDI_T_NOTIFICHE_ISTRUTTORE.ID_FREQUENZA AS idFrequenza, ");
			sql.append(
					"PBANDI_D_NOTIFICA_ALERT.DESCR_NOTIFICA AS descrNotifica, PBANDI_T_NOTIFICHE_ISTRUTTORE.DT_FINE_VALIDITA AS dtFineValidita, ");
			sql.append("CASE WHEN  EXISTS ( SELECT 'x' FROM PBANDI_R_NOTIF_ISTR_PROGETTI ");
			sql.append("WHERE PBANDI_R_NOTIF_ISTR_PROGETTI.ID_SOGGETTO_NOTIFICA = ID_SOGGETTO_NOTIFICA ");
			sql.append("AND PBANDI_T_NOTIFICHE_ISTRUTTORE.DT_FINE_VALIDITA IS NULL) ");
			sql.append("THEN 'S' ELSE 'N' END AS hasProgettiAssociated ");
			sql.append("FROM PBANDI_D_NOTIFICA_ALERT ");
			sql.append("LEFT OUTER JOIN PBANDI_T_NOTIFICHE_ISTRUTTORE ");
			sql.append(
					"ON (PBANDI_D_NOTIFICA_ALERT.ID_NOTIFICA_ALERT = PBANDI_T_NOTIFICHE_ISTRUTTORE.ID_NOTIFICA_ALERT AND ID_SOGGETTO = "
							+ idSoggetto + ") ");
			sql.append(
					"LEFT OUTER JOIN PBANDI_D_FREQUENZA ON  PBANDI_T_NOTIFICHE_ISTRUTTORE.id_frequenza = PBANDI_D_FREQUENZA.id_frequenza ");
			sql.append("WHERE PBANDI_D_NOTIFICA_ALERT.DT_FINE_VALIDITA IS NULL ");
			sql.append("AND PBANDI_D_FREQUENZA.DT_FINE_VALIDITA IS NULL ");
			sql.append("ORDER BY PBANDI_D_NOTIFICA_ALERT.DESCR_NOTIFICA");
			LOG.debug(sql.toString());

			List<NotificaAlertVO> notificheAlert = getJdbcTemplate().query(sql.toString(),
					new NotificaAlertVORowMapper());
			if (notificheAlert != null & notificheAlert.size() > 0) {
				LOG.info(prf + " notificheAlert found: " + notificheAlert.size());
				for (NotificaAlertVO notificaAlert : notificheAlert) {
					notificaAlert.setDtFineValidita(null); // fix : createJSonObject goes crash with sql.Date
															// getHours !!!
				}
				notificheFrequenze.setNotificheAlert(notificheAlert);
			} else {
				LOG.info(prf + " notificheAlert found: 0");
			}
			return notificheFrequenze;

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PbandiTEmailSoggettoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read PbandiTEmailSoggettoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public void associateNotificheIstruttore(Long idUtente, String idIride, Long idSoggetto,
			List<NotificaAlertVO> notificheAlert) throws UtenteException, ErroreGestitoException {
		String prf = "[NotificheViaMailDAOImpl::saveMail]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}

		try {

			for (NotificaAlertVO notifica : notificheAlert) {
				StringBuilder sql = new StringBuilder("");
				sql.append(
						"SELECT ID_SOGGETTO_NOTIFICA AS idSoggettoNotifica, ID_NOTIFICA_ALERT AS idNotificaAlert, ID_FREQUENZA AS idFrequenza, ");
				sql.append(
						"DT_INIZIO_VALIDITA AS dtInizioValidita, ID_SOGGETTO AS idSoggetto, DT_FINE_VALIDITA AS dtFineValidita ");
				sql.append("FROM PBANDI_T_NOTIFICHE_ISTRUTTORE ");
				sql.append("WHERE ID_NOTIFICA_ALERT = " + notifica.getIdNotificaAlert() + " AND ID_SOGGETTO = "
						+ idSoggetto);
				LOG.debug(sql);
				List<PbandiTNotificheIstruttoreVO> notificheIstruttore = getJdbcTemplate().query(sql.toString(),
						new PbandiTNotificheIstruttoreVORowMapper());

				if (notifica.getSelected() == null && notifica.getIdSoggettoNotifica() != null) {
					sql = new StringBuilder("");
					sql.append("DELETE FROM PBANDI_R_NOTIF_ISTR_PROGETTI WHERE ID_SOGGETTO_NOTIFICA = "
							+ notifica.getIdSoggettoNotifica());
					LOG.debug(sql);
					LOG.info(prf + " deleting from PBANDI_R_NOTIF_ISTR_PROGETTI");
					getJdbcTemplate().update(sql.toString());
				}
				if (notificheIstruttore != null && notificheIstruttore.size() > 0) {
					LOG.info(prf + "updating in PBANDI_R_NOTIF_ISTR_PROGETTI");
					updateNotificaIstruttore(notifica, idSoggetto, notificheIstruttore.get(0));

				} else {
					insertNotificaIstruttore(idSoggetto, notifica);
				}

			}
		} catch (ErroreGestitoException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to associate notifiche istruttore", e);
			throw new ErroreGestitoException("DaoException while trying to associate notifiche istruttore", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	private void updateNotificaIstruttore(NotificaAlertVO notificaAlert, Long idSoggetto,
			PbandiTNotificheIstruttoreVO pbandiTNotificheIstruttoreVO) throws ErroreGestitoException {
		if (notificaAlert.getSelected() != null && notificaAlert.getSelected().equals(Boolean.TRUE)) {
			pbandiTNotificheIstruttoreVO.setDtFineValidita(null);
		} else {
			pbandiTNotificheIstruttoreVO.setDtFineValidita(new Date());
		}
		try {
			pbandiTNotificheIstruttoreVO.setIdFrequenza(notificaAlert.getIdFrequenza());
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE PBANDI_T_NOTIFICHE_ISTRUTTORE ");
			sql.append("SET ID_NOTIFICA_ALERT = " + pbandiTNotificheIstruttoreVO.getIdNotificaAlert()
					+ ", ID_FREQUENZA = " + notificaAlert.getIdFrequenza() + ", ID_SOGGETTO = " + idSoggetto + ", ");
			if (pbandiTNotificheIstruttoreVO.getDtFineValidita() != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDate = dateFormat.format(pbandiTNotificheIstruttoreVO.getDtFineValidita());
				sql.append("DT_FINE_VALIDITA = (to_date('" + strDate + "','dd/MM/yyyy')) ");
			} else {
				sql.append("DT_FINE_VALIDITA = null ");
			}
			sql.append("WHERE ID_SOGGETTO_NOTIFICA = " + pbandiTNotificheIstruttoreVO.getIdSoggettoNotifica());
			LOG.debug(sql);

			getJdbcTemplate().update(sql.toString());
		} catch (Exception ex) {
			LOG.error("Error inserting idNotificaAlert " + notificaAlert.getIdNotificaAlert() + " idFrequenza:"
					+ notificaAlert.getIdFrequenza(), ex);
			throw new ErroreGestitoException(ex.getMessage());
		}
	}

	private void insertNotificaIstruttore(Long idSoggetto, NotificaAlertVO notificaAlert)
			throws ErroreGestitoException {
		PbandiTNotificheIstruttoreVO pbandiTNotificheIstruttoreVO = new PbandiTNotificheIstruttoreVO();
		pbandiTNotificheIstruttoreVO.setIdSoggetto(idSoggetto);
		if (notificaAlert.getSelected() == null || !notificaAlert.getSelected().equals(Boolean.TRUE)) {
			pbandiTNotificheIstruttoreVO.setDtFineValidita(new Date());
		} else {
			pbandiTNotificheIstruttoreVO.setDtFineValidita(null);
		}
		try {
			pbandiTNotificheIstruttoreVO.setIdFrequenza(notificaAlert.getIdFrequenza());
			pbandiTNotificheIstruttoreVO.setIdNotificaAlert(notificaAlert.getIdNotificaAlert());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String strDtInizio = dateFormat.format(new Date());
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO PBANDI_T_NOTIFICHE_ISTRUTTORE ");
			sql.append(
					"(ID_SOGGETTO_NOTIFICA, ID_NOTIFICA_ALERT, ID_FREQUENZA, DT_INIZIO_VALIDITA, ID_SOGGETTO, DT_FINE_VALIDITA) ");
			sql.append("VALUES (SEQ_PBANDI_T_NOTIFICHE_ISTR.nextval, "
					+ pbandiTNotificheIstruttoreVO.getIdNotificaAlert() + ", ");
			sql.append(pbandiTNotificheIstruttoreVO.getIdFrequenza() + ", (to_date('" + strDtInizio
					+ "','dd/MM/yyyy')), " + idSoggetto + ", ");
			if (pbandiTNotificheIstruttoreVO.getDtFineValidita() != null) {
				String strDtFine = dateFormat.format(pbandiTNotificheIstruttoreVO.getDtFineValidita());
				sql.append("(to_date('" + strDtFine + "','dd/MM/yyyy')) )");
			} else {
				sql.append("null)");
			}
			getJdbcTemplate().update(sql.toString());

		} catch (Exception ex) {
			LOG.error("Error inserting idNotificaAlert: " + notificaAlert.getIdNotificaAlert() + " idFrequenza:"
					+ notificaAlert.getIdFrequenza(), ex);
			throw new ErroreGestitoException(ex.getMessage());
		}
	}

	@Override
	public List<BandoProgettiVO> getBandiProgetti(Long idUtente, String idIride, Long idSoggetto,
			Long idSoggettoNotifica) throws ErroreGestitoException, UtenteException {
		String prf = "[NotificheViaMailDAOImpl::getBandiProgetti]";
		LOG.info(prf + " BEGIN");

		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}

		try {
			StringBuilder sql = new StringBuilder("");

			sql.append(
					"SELECT distinct NOMEBANDOLINEA, PROGRBANDOLINEAINTERVENTO, IDPROGETTO,  CODICEVISUALIZZATO, CUP, TITOLOPROGETTO, ");
			sql.append("(select 'x' from pbandi_r_notif_istr_progetti where id_soggetto_notifica=" + idSoggettoNotifica
					+ " and id_progetto = IDPROGETTO) associated ");
			sql.append(" FROM( ");
			sql.append("SELECT DISTINCT PBANDI_R_BANDO_LINEA_INTERVENT.NOME_BANDO_LINEA nomeBandoLinea, ");
			sql.append("PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO progrBandoLineaIntervento, ");
			sql.append(
					"PBANDI_T_PROGETTO.ID_PROGETTO idProgetto, CODICE_VISUALIZZATO codiceVisualizzato, CUP, PBANDI_T_PROGETTO.TITOLO_PROGETTO titoloProgetto, ");
			sql.append("ID_SOGGETTO_NOTIFICA idSoggettoNotifica ");
			sql.append("FROM PBANDI_R_BANDO_LINEA_INTERVENT ");
			sql.append(
					"JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP ON (PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO=PBANDI_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO) ");
			sql.append(
					"JOIN PBANDI_R_ENTE_COMPETENZA_SOGG ON (PBANDI_R_BANDO_LINEA_ENTE_COMP.ID_ENTE_COMPETENZA=PBANDI_R_ENTE_COMPETENZA_SOGG.ID_ENTE_COMPETENZA ");
			sql.append("AND PBANDI_R_BANDO_LINEA_ENTE_COMP.DT_FINE_VALIDITA IS NULL ");
			sql.append("AND PBANDI_R_ENTE_COMPETENZA_SOGG.DT_FINE_VALIDITA IS NULL) ");
			sql.append(
					"JOIN PBANDI_T_DOMANDA ON (PBANDI_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO=PBANDI_T_DOMANDA.PROGR_BANDO_LINEA_INTERVENTO) ");
			sql.append("JOIN PBANDI_T_PROGETTO USING(ID_DOMANDA) ");
			sql.append(
					"LEFT OUTER JOIN PBANDI_R_NOTIF_ISTR_PROGETTI ON (PBANDI_R_NOTIF_ISTR_PROGETTI.id_progetto=PBANDI_T_PROGETTO.id_progetto) ");
			sql.append("WHERE ID_SOGGETTO=" + idSoggetto + " ) ORDER BY NOMEBANDOLINEA,CODICEVISUALIZZATO ");

			LOG.debug(sql.toString());

			List<BandoProgettiVO> bandiProgetti = getJdbcTemplate().query(sql.toString(),
					new ResultSetExtractor<List<BandoProgettiVO>>() {
						public List<BandoProgettiVO> extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							List<BandoProgettiVO> bandiProgetti = null;
							Long progrBandoLineaInterventoLast = 0L;
							BandoProgettiVO bandoProgetto = null;

							while (rs.next()) {
								String codiceVisualizzato = rs.getString("codiceVisualizzato");
								String cup = rs.getString("cup");
								Long idProgetto = rs.getLong("idProgetto");
								String nomeBandoLinea = rs.getString("nomeBandoLinea");
								Long progrBandoLineaIntervento = rs.getLong("progrBandoLineaIntervento");
								String titoloProgetto = rs.getString("titoloProgetto");
								String associated = rs.getString("associated");

								if (progrBandoLineaIntervento != null) {
									if (progrBandoLineaIntervento.equals(progrBandoLineaInterventoLast)) {
										ProgettoNotificheVO progetto = new ProgettoNotificheVO();
										progetto.setCodiceVisualizzato(codiceVisualizzato);
										progetto.setCup(cup);
										progetto.setIdProgetto(idProgetto);
										progetto.setTitoloProgetto(titoloProgetto);
										if (associated != null) {
											progetto.setIsAssociated(Boolean.TRUE);
										}
										List<ProgettoNotificheVO> listProgetti = bandoProgetto.getProgetti();
										listProgetti.add(progetto);
										bandoProgetto.setProgetti(listProgetti); // necessario?
									} else {
										if (!progrBandoLineaInterventoLast.equals(new Long(0L))) {
											bandiProgetti.add(bandoProgetto);
											bandoProgetto = new BandoProgettiVO();// first round
											progrBandoLineaInterventoLast = progrBandoLineaIntervento;
											bandoProgetto.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
											bandoProgetto.setNomeBandoLinea(nomeBandoLinea);
											List<ProgettoNotificheVO> progetti = new ArrayList<ProgettoNotificheVO>();
											ProgettoNotificheVO progetto = new ProgettoNotificheVO();
											progetto.setCodiceVisualizzato(codiceVisualizzato);
											progetto.setCup(cup);
											progetto.setIdProgetto(idProgetto);
											progetto.setTitoloProgetto(titoloProgetto);
											if (associated != null) {
												progetto.setIsAssociated(Boolean.TRUE);
											} else {
												progetto.setIsAssociated(Boolean.FALSE);
											}
											progetti.add(progetto);

											bandoProgetto.setProgetti(progetti);
										} else {
											bandiProgetti = new ArrayList<BandoProgettiVO>();
											bandoProgetto = new BandoProgettiVO();// first round
											progrBandoLineaInterventoLast = progrBandoLineaIntervento;
											bandoProgetto.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
											bandoProgetto.setNomeBandoLinea(nomeBandoLinea);
											List<ProgettoNotificheVO> progetti = new ArrayList<ProgettoNotificheVO>();
											ProgettoNotificheVO progetto = new ProgettoNotificheVO();
											progetto.setCodiceVisualizzato(codiceVisualizzato);
											progetto.setCup(cup);
											progetto.setIdProgetto(idProgetto);
											progetto.setTitoloProgetto(titoloProgetto);
											if (associated != null) {
												progetto.setIsAssociated(Boolean.TRUE);
											} else {
												progetto.setIsAssociated(Boolean.FALSE);
											}
											progetti.add(progetto);
											bandoProgetto.setProgetti(progetti);
										}

									}
								}
							}
							if (bandoProgetto != null)
								bandiProgetti.add(bandoProgetto);// last bandoProgetti

							return bandiProgetti;
						}
					});
			if (bandiProgetti != null)
				LOG.info("trovati bandiProgetti: " + bandiProgetti.size());
			return bandiProgetti;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to associate notifiche istruttore", e);
			throw new ErroreGestitoException("DaoException while trying to associate notifiche istruttore", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public void associateProgettiToNotifica(Long idUtente, String idIride, Long idSoggetto,
			NotificaAlertVO notificaAlert) throws UtenteException, ErroreGestitoException {
		String prf = "[NotificheViaMailDAOImpl::associateProgettiToNotifica]";
		LOG.info(prf + " BEGIN");
		if (idUtente == null || idIride == null || idSoggetto == null) {
			throw new UtenteException("utente non correttamente valorizzato");
		}

		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM PBANDI_R_NOTIF_ISTR_PROGETTI WHERE ID_SOGGETTO_NOTIFICA = "
					+ notificaAlert.getIdSoggettoNotifica());
			LOG.debug(sql);
			LOG.info(prf + " deleting progetti con idSoggettoNotifica = " + notificaAlert.getIdSoggettoNotifica());
			getJdbcTemplate().update(sql.toString());

			if (notificaAlert.getIdProgetti() != null && notificaAlert.getIdProgetti().size() > 0) {
				LOG.info(prf + " inserting progetti con idSoggettoNotifica = " + notificaAlert.getIdSoggettoNotifica());
				for (Long idProgetto : notificaAlert.getIdProgetti()) {
					sql = new StringBuilder("");
					sql.append("INSERT INTO PBANDI_R_NOTIF_ISTR_PROGETTI (ID_PROGETTO , ID_SOGGETTO_NOTIFICA) values ("
							+ idProgetto + ", " + notificaAlert.getIdSoggettoNotifica() + ")");
					LOG.debug(sql);
					getJdbcTemplate().update(sql.toString());
				}
			}
		} catch (ErroreGestitoException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to associate progetti to notifica", e);
			throw new ErroreGestitoException("DaoException while trying to associate  progetti to notifica", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
}
