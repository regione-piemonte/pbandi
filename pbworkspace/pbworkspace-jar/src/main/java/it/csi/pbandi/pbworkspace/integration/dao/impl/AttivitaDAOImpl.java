/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneprogetto.RecapitoVO;
import it.csi.pbandi.pbworkspace.dto.RicercaAttivitaPrecedenteDTO;
import it.csi.pbandi.pbworkspace.dto.profilazione.ConsensoInformatoDTO;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.RecordNotFoundException;
import it.csi.pbandi.pbworkspace.exception.UtenteException;
import it.csi.pbandi.pbworkspace.integration.dao.AttivitaDAO;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.BandoVORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PbandiTNotificaProcessoVORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.PbandiTUtenteRowMapper;
import it.csi.pbandi.pbworkspace.integration.vo.AttivitaVO;
import it.csi.pbandi.pbworkspace.integration.vo.BandoVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTNotificaProcessoVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTRicercaAttivitaVO;
import it.csi.pbandi.pbworkspace.integration.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbworkspace.integration.vo.ProgettoVO;
import it.csi.pbandi.pbworkspace.util.Constants;
import it.csi.pbandi.pbworkspace.util.DateUtil;

@Service
public class AttivitaDAOImpl extends JdbcDaoSupport implements AttivitaDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public AttivitaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public AttivitaDAOImpl() {
	}

	@Autowired
	NeofluxBusinessImpl neofluxBusinessImpl;

	@Override
	public List<ProgettoVO> getProgettiWithProcedures(Long progrBandoLineaIntervento, Long idSoggettoBen, Long idUtente)
			throws Exception {
		String prf = "[AttivitaDAOImpl::getProgettiWithProcedure]";
		LOG.info(prf + " BEGIN");

		final StringBuilder sql = new StringBuilder("select  " + Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "GetProgetti(:progrBandoLineaIntervento,:idSoggettoBen,:idUtente)");
		sql.append(" FROM dual");

		LOG.info(prf + " sql=" + sql.toString());
		List<ProgettoVO> prj = null;

		try {
			PreparedStatementCallback<List<ProgettoVO>> action = new PreparedStatementCallback<List<ProgettoVO>>() {

				public List<ProgettoVO> doInPreparedStatement(PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {

					// LOG.info(prf + " doInPreparedStatement");

					preparedStatement.setLong(1, progrBandoLineaIntervento);
					preparedStatement.setLong(2, idSoggettoBen);
					preparedStatement.setLong(3, idUtente);

					List<ProgettoVO> progetti = new ArrayList<ProgettoVO>();
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						// LOG.info(prf + " rs="+rs);
						java.sql.Array array = (java.sql.Array) rs.getArray(1);
						ResultSet arrayRs = array.getResultSet();
						// LOG.info(prf + " arrayRs="+arrayRs);

						while (arrayRs.next()) {
							BigDecimal row = (BigDecimal) arrayRs.getObject(1);
							// LOG.info(prf + " row="+row);

							oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs.getObject(2);
							if (struct != null) {
								Object[] attributes = struct.getAttributes();
								if (attributes != null) {
									ProgettoVO progetto = new ProgettoVO();
									// LOG.info(prf + " attributes="+attributes);
									progetto.setId(getLong(attributes[0]));
									progetto.setTitolo(getString(attributes[1]));
									progetto.setCodiceVisualizzato(getString(attributes[2]));
									progetto.setAcronimo(getString(attributes[3]));
									progetti.add(progetto);
								}
							}
						}
						arrayRs.close();
					}
					rs.close();

					if (progetti != null) {
						LOG.info(prf + " progetti size=" + progetti.size());
					}
					return progetti;
				}
			};

			prj = (List<ProgettoVO>) getJdbcTemplate().execute(sql.toString(), action);

			if (prj != null) {
				for (ProgettoVO progettoVO : prj) {
					LOG.info(prf + " progettoVO=" + progettoVO);
				}
			} else {
				LOG.info(prf + " result nullo");
			}
		} catch (Exception e) {
			LOG.error(prf + " Exception : " + e.getMessage());
			throw new Exception("Exception", e);
		} finally {
			LOG.info(prf + " END");
		}
		return prj;

	}

	protected Long getLong(Object object) {
		if (object != null && object instanceof BigDecimal)
			return ((BigDecimal) object).longValue();
		return null;
	}

	private String getString(Object object) {
		if (object != null && object instanceof String)
			return (String) object;
		return null;
	}

	@Override
	public List<BandoVO> getBandi(String codiceRuolo, Long idSoggetto, Long idBeneficiario)
			throws ErroreGestitoException, UtenteException, RecordNotFoundException {

		String prf = "[AttivitaDAOImpl::getBandi]";
		LOG.info(prf + " BEGIN");
		List<BandoVO> bandi = null;
		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idBeneficiario="	+ idBeneficiario);
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT NOME_BANDO_LINEA as nomeBandoLinea , PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento, PROCESSO as processo ");
			sql.append("from ( ");
			sql.append(
					"SELECT DISTINCT nome_bando_linea, progr_bando_linea_intervento, processo,id_soggetto,desc_breve_tipo_anagrafica,id_soggetto_beneficiario ");
			sql.append("FROM  pbandi_v_processo_ben_bl ) ");
			sql.append("where DESC_BREVE_TIPO_ANAGRAFICA = ? and ID_SOGGETTO = ? and ID_SOGGETTO_BENEFICIARIO = ?");
			sql.append(" ORDER BY NOME_BANDO_LINEA ASC ");
			
			// PK: 5/3/2024.... mi hanno venduto questa query come miglioria della precedente...... MA NON FUNZIONA
			
//			sql.append("SELECT NOME_BANDO_LINEA AS nomeBandoLinea , PROGR_BANDO_LINEA_INTERVENTO AS progrBandoLineaIntervento, PROCESSO AS processo FROM (");
//			sql.append("	select distinct a.nome_bando_linea, a.progr_bando_linea_intervento,id_processo as processo,");
//			sql.append("    c.id_soggetto as id_soggetto_beneficiario from PBANDI_R_BANDO_LINEA_INTERVENT a ");
//			sql.append(" join PBANDI_T_DOMANDA b on b. progr_bando_linea_intervento = a. progr_bando_linea_intervento ");
//			sql.append(" join PBANDI_R_SOGGETTO_DOMANDA c on c.id_domanda = b.id_domanda and c.id_tipo_anagrafica = 1 and c.id_tipo_beneficiario != 4) ");
//			sql.append(" where ID_SOGGETTO_BENEFICIARIO = ?");
			
			LOG.debug(prf + "sql="+sql.toString());

			bandi = getJdbcTemplate().query(sql.toString(), new Object[] { codiceRuolo, idSoggetto, idBeneficiario }, new BandoVORowMapper());
//			bandi = getJdbcTemplate().query(sql.toString(), new Object[] {  idBeneficiario }, new BandoVORowMapper());
			LOG.info(prf + "sono stati trovati " + bandi.size() + " bandi");

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BandoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read BandoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return bandi;
	}

	@Override
	public List<BandoVO> ordinaBandi(List<BandoVO> listaInput) throws RecordNotFoundException, Exception {
		String prf = "[AttivitaDAOImpl::ordinaBandi]";
		LOG.info(prf + " BEGIN");

		List<BandoVO> listaVecchiaProgr = new ArrayList<BandoVO>();
		List<BandoVO> listaNuovaProgr = new ArrayList<BandoVO>();

		try {
			if (listaInput != null) {
				// Divide i bandi in 2 elenchi in base alla programmazione.
				for (BandoVO bando : listaInput) {
					StringBuilder sql = new StringBuilder();
					sql.append("SELECT B.ID_LINEA_DI_INTERVENTO ");
					sql.append("FROM PBANDI_T_BANDO B ");
					sql.append("INNER JOIN PBANDI_R_BANDO_LINEA_INTERVENT BLI ON BLI.ID_BANDO = B.ID_BANDO ");
					sql.append("WHERE BLI.PROGR_BANDO_LINEA_INTERVENTO = " + bando.getProgrBandoLineaIntervento());

					LOG.debug(sql.toString());

					List<Long> idLineaInterventos = getJdbcTemplate().queryForList(sql.toString(), Long.class);

					if (idLineaInterventos != null && idLineaInterventos.size() == 1) {
						LOG.info(prf + "idLineaIntervento: " + idLineaInterventos.get(0));
						if (idLineaInterventos.get(0) == null) {
							// Vecchia programmazione
							listaVecchiaProgr.add(bando);
						} else {
							// Nuova programmazione
							listaNuovaProgr.add(bando);
						}
					} else {
						RecordNotFoundException exception = new RecordNotFoundException(
								"Atteso risultato unico, trovati molti o nessuno.");
						throw exception;
					}

				}
				// Appende i bandi vecchia progr. al fondo di quelli nuova progr.
				for (BandoVO bando : listaVecchiaProgr) {
					listaNuovaProgr.add(bando);
				}
			}
			return listaNuovaProgr;
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception ex) {
			LOG.error("ordinaBandi(): " + ex.getMessage() + "\n\n", ex);
			listaNuovaProgr = listaInput;
		} finally {
			LOG.info(prf + " END");
		}
		return null;
	}

	@Override
	public List<AttivitaVO> getAttivitaBENWithProcedures(Long progrBandoLineaIntervento, Long idBeneficiario,
			Long idUtente, String descBreveTipoAnag, Long start, Long idProgetto, String descrAttivita)
			throws Exception {
		String prf = "[AttivitaDAOImpl::getAttivitaBENWithProcedures]";
		LOG.info(prf + " BEGIN");

		List<AttivitaVO> result = new ArrayList<AttivitaVO>();
		final StringBuilder sql = new StringBuilder("select  " + Constants.NOME_PACKAGE_PROC_PROCESSO + "."
				+ "GetAttivitaBEN(:progrBandoLineaIntervento,:idBeneficiario,:idUtente,:descBreveTipoAnag,:startt,:idProgetto,:descrAttivita)");
		sql.append(" FROM dual");
		LOG.info(prf + " progrBandoLineaIntervento: " + progrBandoLineaIntervento + ", idBeneficiario: "
				+ idBeneficiario + ", idUtente: " + idUtente + ", descBreveTipoAnag: " + descBreveTipoAnag
				+ ", idProgetto: " + idProgetto + ", descrAttivita: " + descrAttivita);
		;
		try {
			PreparedStatementCallback<List<AttivitaVO>> action = new PreparedStatementCallback<List<AttivitaVO>>() {

				public List<AttivitaVO> doInPreparedStatement(PreparedStatement preparedStatement)
						throws SQLException, DataAccessException {
					preparedStatement.setLong(1, progrBandoLineaIntervento);
					preparedStatement.setLong(2, idBeneficiario);
					preparedStatement.setLong(3, idUtente);
					preparedStatement.setString(4, descBreveTipoAnag);
					preparedStatement.setLong(5, start);
					if (idProgetto != null && !idProgetto.equals(Long.valueOf(0L))) {
						preparedStatement.setLong(6, idProgetto);
						LOG.info(prf + " preparedStatement.setLong(6, idProgetto);");
					} else {
						preparedStatement.setNull(6, java.sql.Types.NUMERIC);
						LOG.info(prf + " preparedStatement.setNull(6, java.sql.Types.NUMERIC);");
					}
					preparedStatement.setString(7, descrAttivita);

					List<AttivitaVO> tasks = new ArrayList<AttivitaVO>();
					ResultSet rs = preparedStatement.executeQuery();
					while (rs.next()) {
						java.sql.Array array = (java.sql.Array) rs.getArray(1);
						ResultSet arrayRs = array.getResultSet();
						while (arrayRs.next()) {
							BigDecimal row = (BigDecimal) arrayRs.getObject(1);
							oracle.sql.STRUCT struct = (oracle.sql.STRUCT) arrayRs.getObject(2);
							if (struct != null) {
								Object[] attributes = struct.getAttributes();
								if (attributes != null) {
									AttivitaVO task = new AttivitaVO();
									task.setIdProgetto(getLong(attributes[0]));
									task.setTitoloProgetto(getString(attributes[1]));
									task.setCodiceVisualizzato(getString(attributes[2]));
									task.setDescrBreveTask(getString(attributes[3]));
									task.setDescrTask(getString(attributes[4]));
									task.setProgrBandoLineaIntervento(getLong(attributes[5]));
									task.setNomeBandoLinea(getString(attributes[6]));
									task.setFlagOpt(getString(attributes[7]));
									task.setFlagLock(getString(attributes[8]));
									task.setAcronimoProgetto(getString(attributes[9]));
									task.setIdBusiness(getLong(attributes[10]));
									task.setIdNotifica(getLong(attributes[11]));
									task.setDenominazioneLock(getString(attributes[12]));
									task.setFlagRichAbilitazUtente(getString(attributes[13]));
									if (task.getDescrTask() != null && task.getDescrTask().length() > 0)
										tasks.add(task);
								}
							}
						}
						arrayRs.close();
					}
					rs.close();
					return tasks;
				}
			};

			result = (List<AttivitaVO>) getJdbcTemplate().execute(sql.toString(), action);

			if (result != null) {

				Long idProgettoAttivita = 0L;
				if (result.size() > 0)
					idProgettoAttivita = result.get(0).getIdProgetto();
				boolean beneficiarioPersonaFisica = this.beneficiarioPersonaFisica(idProgettoAttivita, idBeneficiario);

				for (AttivitaVO attivitaVO : result) {
					if (beneficiarioPersonaFisica) {
						attivitaVO.setFlagUpdRecapito(
								this.getUpdRecapitoBeneficiarioPF(attivitaVO, descBreveTipoAnag, idBeneficiario));
					} else {
						attivitaVO.setFlagUpdRecapito(this.getUpdRecapito(attivitaVO, descBreveTipoAnag));
					}
					// LOG.info(prf + " " + attivitaVO.toString());
				}
			} else {
				LOG.info(prf + " result nullo");
			}

			// Se non ci sono stati errori, memorizzo i filtri di ricerca.
			this.salvaFiltriRicercaAttivita(idBeneficiario, progrBandoLineaIntervento, idProgetto, descrAttivita,
					idUtente);

		} catch (Exception e) {
			LOG.error(prf + " Exception : " + e.getMessage());
			throw new Exception("Exception", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private boolean beneficiarioPersonaFisica(Long idProgetto, Long idSoggettoBen) {
		LOG.info("[AttivitaDAOImpl::beneficiarioPersonaFisica] idProgetto = " + idProgetto + "; idSoggettoBen = " + idSoggettoBen);
		boolean esito = false;
		try {

			String sql = "SELECT ID_PERSONA_FISICA FROM PBANDI_R_SOGGETTO_PROGETTO WHERE ID_PROGETTO = " + idProgetto
					+ " AND ID_SOGGETTO = " + idSoggettoBen
					+ " AND ID_TIPO_BENEFICIARIO <> 4 AND ID_TIPO_ANAGRAFICA = 1";
			LOG.info("[AttivitaDAOImpl::beneficiarioPersonaFisica] sql=" + sql);
			Long idPersonaFisica = (Long) getJdbcTemplate().queryForObject(sql, Long.class);
			LOG.info("[AttivitaDAOImpl::beneficiarioPersonaFisica] idPersonaFisica = " + idPersonaFisica);

			esito = (idPersonaFisica != null);

		} catch (Exception e) {
			LOG.warn("[AttivitaDAOImpl::beneficiarioPersonaFisica] warn: " + e.getMessage());
			esito = false;
		}

		LOG.info("[AttivitaDAOImpl::beneficiarioPersonaFisica] END, esito = " + esito);
		return esito;
	}

	// Metodo fatto sulla falsa riga di getUpdRecapito().
	public String getUpdRecapitoBeneficiarioPF(AttivitaVO task, String codiceRuolo, Long idBeneficiario) {
		LOG.info("getUpdRecapito(): DescrBreveTask = " + task.getDescrBreveTask() + "; idProgetto = "
				+ task.getIdProgetto() + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);
		String updRecapito = "N";
		if ("DATI-PROG".equalsIgnoreCase(task.getDescrBreveTask())) {
			String flag = this.caricaFlagEmailConfermataBeneficiarioPF(task.getIdProgetto(), idBeneficiario);
			String email = this.caricaEmailBeneficiarioPF(task.getIdProgetto(), idBeneficiario);
			if ((StringUtil.isBlank(email) || (!StringUtil.isBlank(email) && !"S".equalsIgnoreCase(flag)))) {
				// Solo i seguenti ruoli possono modificare il recapito e vedere l'icona:
				// BEN-MASTER, BENEFICIARIO, PERSONA-FISICA, CSI-ADMIN
				if (codiceRuolo.equalsIgnoreCase("BEN-MASTER") || codiceRuolo.equalsIgnoreCase("BENEFICIARIO")
						|| codiceRuolo.equalsIgnoreCase("PERSONA-FISICA")
						|| codiceRuolo.equalsIgnoreCase("CSI-ADMIN")) {
					updRecapito = "S";
				}
			}
		}
		LOG.info("getUpdRecapito(): updRecapito = " + updRecapito);
		return updRecapito;
	}

	private String caricaEmailBeneficiarioPF(Long idProgetto, Long idBeneficiario) {
		String email = null;
		try {

			String sql = "SELECT ID_RECAPITI_PERSONA_FISICA FROM PBANDI_R_SOGGETTO_PROGETTO WHERE ID_PROGETTO = "
					+ idProgetto + " AND ID_SOGGETTO = " + idBeneficiario
					+ " AND ID_TIPO_BENEFICIARIO <> 4 AND ID_TIPO_ANAGRAFICA = 1";
			LOG.info("\n" + sql);
			Long idRecapitiPF = (Long) getJdbcTemplate().queryForObject(sql, Long.class);
			LOG.info("caricaEmailBeneficiarioPF(): idRecapitiPF = " + idRecapitiPF);

			if (idRecapitiPF != null) {
				String sql1 = "SELECT EMAIL FROM PBANDI_T_RECAPITI WHERE ID_RECAPITI = " + idRecapitiPF;
				LOG.info("\n" + sql1);
				email = (String) getJdbcTemplate().queryForObject(sql1, String.class);
			}

		} catch (Exception e) {
			LOG.error("Errore " + e.getMessage());
			email = null;
		}
		LOG.info("caricaEmailBeneficiarioPF(): email = " + email);
		return email;
	}

	private String caricaFlagEmailConfermataBeneficiarioPF(Long idProgetto, Long idBeneficiario) {
		String flag = null;
		try {

			String sql = "SELECT FLAG_EMAIL_CONFERMATA FROM PBANDI_R_SOGGETTO_PROGETTO WHERE ID_PROGETTO = "
					+ idProgetto + " AND ID_SOGGETTO = " + idBeneficiario
					+ " AND ID_TIPO_BENEFICIARIO <> 4 AND ID_TIPO_ANAGRAFICA = 1";
			LOG.info("\n" + sql);
			flag = (String) getJdbcTemplate().queryForObject(sql, String.class);
			LOG.info("caricaFlagEmailConfermataBeneficiarioPF(): FLAG_EMAIL_CONFERMATA = " + flag);

		} catch (Exception e) {
			LOG.error("Errore " + e.getMessage());
			flag = null;
		}
		return flag;
	}

	public String getUpdRecapito(AttivitaVO task, String codiceRuolo) {
		LOG.info("getUpdRecapito(): DescrBreveTask = " + task.getDescrBreveTask() + "; idProgetto = "
				+ task.getIdProgetto() + "; codiceRuolo = " + codiceRuolo);
		String updRecapito = "N";
		if ("DATI-PROG".equalsIgnoreCase(task.getDescrBreveTask())) {
			RecapitoVO recapito = this.caricaRecapiti(task.getIdProgetto());
			if (recapito != null
					&& (StringUtil.isBlank(recapito.getEmail()) || (!StringUtil.isBlank(recapito.getEmail())
							&& !"S".equalsIgnoreCase(recapito.getFlagEmailConfermata())))) {
				// Solo i seguenti ruoli possono modificare il recapito e vedere l'icona:
				// BEN-MASTER, BENEFICIARIO, PERSONA-FISICA, CSI-ADMIN
				if (codiceRuolo.equalsIgnoreCase("BEN-MASTER") || codiceRuolo.equalsIgnoreCase("BENEFICIARIO")
						|| codiceRuolo.equalsIgnoreCase("PERSONA-FISICA")
						|| codiceRuolo.equalsIgnoreCase("CSI-ADMIN")) {
					updRecapito = "S";
				}
			}
		}
		return updRecapito;
	}

	private RecapitoVO caricaRecapiti(Long idProgetto) {
		RecapitoVO ret = new RecapitoVO();
		try {

			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("sps.ID_RECAPITI, r.EMAIL, r.FAX, r.TELEFONO, r.ID_UTENTE_INS, r.ID_UTENTE_AGG, ");
			sqlSelect.append("r.DT_INIZIO_VALIDITA, r.DT_FINE_VALIDITA, sps.FLAG_EMAIL_CONFERMATA, ");
			sqlSelect.append("sps.progr_soggetto_progetto, sps.progr_soggetto_progetto_sede ");
			sqlSelect.append(
					"from pbandi_t_recapiti r, pbandi_r_soggetto_progetto sp, pbandi_r_sogg_progetto_sede sps ");
			sqlSelect.append("where sp.id_progetto = " + idProgetto.toString() + " ");
			sqlSelect.append("and sp.progr_soggetto_progetto= sps.progr_soggetto_progetto ");
			sqlSelect.append("and r.id_recapiti(+) = sps.id_recapiti ");
			sqlSelect.append("and sp.id_tipo_anagrafica=1 ");
			sqlSelect.append("and sp.id_tipo_beneficiario <>4 ");
			sqlSelect.append("and sps.id_tipo_sede =1 ");
			sqlSelect.append("AND ROWNUM = 1 ");
			sqlSelect.append("order by sp.dt_inserimento desc , sp.dt_aggiornamento desc");
			LOG.info("\n" + sqlSelect.toString());

			ret = (RecapitoVO) getJdbcTemplate().queryForObject(sqlSelect.toString(), new RecapitoVORowMapper());
			LOG.info("Recapito = " + ret.toString());

		} catch (Exception e) {
			LOG.error("Errore " + e.getMessage());
		}
		return ret;
	}

	private class RecapitoVORowMapper implements RowMapper<RecapitoVO> {
		public RecapitoVO mapRow(ResultSet rs, int row) throws SQLException {
			RecapitoVO vo = new RecapitoVO();
			vo.setIdRecapiti(rs.getLong(1));
			vo.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
			vo.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
			vo.setEmail(rs.getString("EMAIL"));
			vo.setFax(rs.getString("FAX"));
			vo.setFlagEmailConfermata(rs.getString("FLAG_EMAIL_CONFERMATA"));
			vo.setIdUtenteAgg(rs.getLong("ID_UTENTE_AGG"));
			vo.setIdUtenteIns(rs.getLong("ID_UTENTE_INS"));
			vo.setTelefono(rs.getString("TELEFONO"));
			vo.setProgrSoggettoProgetto(rs.getLong("progr_soggetto_progetto"));
			vo.setProgrSoggettoProgettoSede(rs.getLong("progr_soggetto_progetto_sede"));
			return vo;
		}
	}

	@Override
	public List<PbandiTNotificaProcessoVO> caricaListaNotifiche(Long idBandoLineaIntervento,
			Long idSoggettoBeneficiario, long[] idProgetti, String codiceRuolo) throws Exception {
		String prf = "[AttivitaDAOImpl::caricaListaNotifiche]";
		LOG.info(prf + " BEGIN");

		List<PbandiTNotificaProcessoVO> lista = null;

		int idRuolo = 0;
		if ("".equals(codiceRuolo) || codiceRuolo == null) {
			idRuolo = 0;
		} else if ("BENEFICIARIO".equals(codiceRuolo) || "PERSONA-FISICA".equals(codiceRuolo)
				|| "BEN-MASTER".equals(codiceRuolo)) {
			idRuolo = 2;
		} else {
			idRuolo = 1;
		}
		try {

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT ID_NOTIFICA, ID_PROGETTO, ID_RUOLO_DI_PROCESSO_DEST, SUBJECT_NOTIFICA, ");
			sql.append("  STATO_NOTIFICA, ID_UTENTE_MITT, DT_NOTIFICA, ID_UTENTE_AGG, ");
			sql.append("  DT_AGG_STATO_NOTIFICA, ID_TEMPLATE_NOTIFICA, ID_ENTITA, ID_TARGET, MESSAGE_NOTIFICA ");
			sql.append("FROM PBANDI_T_NOTIFICA_PROCESSO ");

			if (idProgetti != null) {

				sql.append("WHERE id_progetto IN (");
				int max = idProgetti.length - 1;

				for (int i = 0; i < idProgetti.length; i++) {
					sql.append(idProgetti[i]);
					if (i < max)
						sql.append(" , ");
				}
				sql.append(")");

			} else {
				sql.append("WHERE id_progetto IN ");
				sql.append("( SELECT DISTINCT id_progetto ");
				sql.append("   FROM pbandi_v_processo_ben_bl b ");
				sql.append("   WHERE b.PROGR_BANDO_LINEA_INTERVENTO = " + idBandoLineaIntervento + " ");
				sql.append("     AND b.ID_SOGGETTO_BENEFICIARIO = " + idSoggettoBeneficiario + " ) ");
			}

			if (idRuolo > 0) {
				sql.append(" AND ID_RUOLO_DI_PROCESSO_DEST = " + idRuolo + " ");
			}
			LOG.debug(sql.toString());
			LOG.info(prf + " idBandoLineaIntervento: " + idBandoLineaIntervento + "; idSoggettoBeneficiario: "
					+ idSoggettoBeneficiario + "; idRuolo: " + idRuolo);

			lista = getJdbcTemplate().query(sql.toString(), new PbandiTNotificaProcessoVORowMapper());

		} catch (Exception e) {
			LOG.error(prf + "Exception while executing query to get PermessoVO", e);
			throw new ErroreGestitoException("DaoException while executing query to get PermessoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return lista;
	}

	@Override
	public List<BandoVO> getBandoByProgr(String codiceRuolo, Long idSoggetto, Long idBeneficiario,
			Long progrBandoLineaIntervento) throws ErroreGestitoException, UtenteException, RecordNotFoundException {

		String prf = "[AttivitaDAOImpl::getBandoByProgr]";
		LOG.info(prf + " BEGIN");
		List<BandoVO> bandi = null;
		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idBeneficiario="
				+ idBeneficiario + ", progrBandoLineaIntervento: " + progrBandoLineaIntervento);
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT NOME_BANDO_LINEA as nomeBandoLinea , PROGR_BANDO_LINEA_INTERVENTO as progrBandoLineaIntervento, PROCESSO as processo ");
			sql.append("from ( ");
			sql.append(
					"SELECT DISTINCT nome_bando_linea, progr_bando_linea_intervento, processo,id_soggetto,desc_breve_tipo_anagrafica,id_soggetto_beneficiario ");
			sql.append("FROM  pbandi_v_processo_ben_bl ) ");
			sql.append(
					"where DESC_BREVE_TIPO_ANAGRAFICA = ? and ID_SOGGETTO = ? and ID_SOGGETTO_BENEFICIARIO = ? and PROGR_BANDO_LINEA_INTERVENTO = ?");

			// PK: 5/3/2024.... mi hanno venduto questa query come miglioria della precedente...... MA NON FUNZIONA
//			sql.append("SELECT NOME_BANDO_LINEA AS nomeBandoLinea , PROGR_BANDO_LINEA_INTERVENTO AS progrBandoLineaIntervento, PROCESSO AS processo FROM (");
//			sql.append(" select distinct a.nome_bando_linea, a.progr_bando_linea_intervento,id_processo as processo, c.id_soggetto as id_soggetto_beneficiario ");
//			sql.append(" from PBANDI_R_BANDO_LINEA_INTERVENT a ");
//			sql.append(" join PBANDI_T_DOMANDA b on b. progr_bando_linea_intervento = a. progr_bando_linea_intervento ");
//			sql.append(" join PBANDI_R_SOGGETTO_DOMANDA c on c.id_domanda = b.id_domanda and c.id_tipo_anagrafica = 1 and c.id_tipo_beneficiario != 4) ");
//			sql.append(" where ID_SOGGETTO_BENEFICIARIO = ? AND PROGR_BANDO_LINEA_INTERVENTO = ?");
			
			LOG.debug(prf + "sql="+sql.toString());

			bandi = getJdbcTemplate().query(sql.toString(),
					new Object[] { codiceRuolo, idSoggetto, idBeneficiario, progrBandoLineaIntervento },
					new BandoVORowMapper());
//			bandi = getJdbcTemplate().query(sql.toString(),
//					new Object[] { idBeneficiario, progrBandoLineaIntervento },
//					new BandoVORowMapper());
			LOG.info(prf + "sono stati trovati " + bandi.size() + " bandi");

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BandoVO", e);
			throw new ErroreGestitoException("DaoException while trying to read BandoVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return bandi;
	}

	@Override
	public Integer aggiornaStatoNotifiche(long[] elencoIdNotifiche, String stato) throws Exception {
		String prf = "[AttivitaDAOImpl::aggiornaStatoNotifiche]";
		LOG.info(prf + " BEGIN");
		Integer ris = 0;
		try {
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE PBANDI_T_NOTIFICA_PROCESSO ");
			sql.append("  SET STATO_NOTIFICA = '" + stato + "', DT_AGG_STATO_NOTIFICA = sysdate");
			sql.append(" WHERE ID_NOTIFICA in (");

			int max = elencoIdNotifiche.length - 1;

			for (int i = 0; i < elencoIdNotifiche.length; i++) {
				sql.append(elencoIdNotifiche[i]);

				if (i < max) {
					sql.append(", ");
				}
			}
			sql.append(")");

			LOG.info("query=" + sql.toString());
			ris = getJdbcTemplate().update(sql.toString());
			LOG.info(prf + " records modified: " + ris + "\n");

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to update PBANDI_T_NOTIFICA_PROCESSO", e);
			throw new ErroreGestitoException("DaoException while trying to update PBANDI_T_NOTIFICA_PROCESSO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return ris;
	}

	@Override
	public String startAttivita(Long idUtente, String identitaDigitale, String descBreveTask, Long idProgetto)
			throws NeoFluxException, SystemException, UnrecoverableException, CSIException {
		LOG.info("[AttivitaDAOImpl::startAttivita] BEGIN");
		return neofluxBusinessImpl.startAttivita(idUtente, identitaDigitale, idProgetto, descBreveTask);
	}

	@Override
	public ConsensoInformatoDTO trovaConsensoInvioComunicazione(Long idUtente)
			throws RecordNotFoundException, Exception {
		String prf = "[AttivitaDAOImpl::trovaConsensoInvioComunicazione] ";
		LOG.info(prf + " BEGIN");

		ConsensoInformatoDTO consensoDTO = new ConsensoInformatoDTO();
		try {

			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT * FROM PBANDI_T_UTENTE WHERE ID_UTENTE = " + idUtente);
			LOG.info("query=" + sql.toString());

			PbandiTUtenteVO utente = (PbandiTUtenteVO) getJdbcTemplate().queryForObject(sql.toString(),
					new PbandiTUtenteRowMapper());
			if (utente == null || utente.getIdUtente() == null)
				throw new RecordNotFoundException("Utente " + idUtente + " non trovato");

			LOG.info(prf + "FlagConsensoMail = " + utente.getFlagConsensoMail() + "; EmailConsenso = "
					+ utente.getEmailConsenso());

			consensoDTO.setFlagConsensoMail(utente.getFlagConsensoMail());
			consensoDTO.setEmailConsenso(utente.getEmailConsenso());

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Errore durante la ricerca del consenso informato. ", e);
			throw new ErroreGestitoException("Errore durante la ricerca del consenso informato. ", e);
		} finally {
			LOG.info(prf + " END");
		}
		return consensoDTO;
	}

	@Override
	public Integer salvaConsensoInvioComunicazione(Long idUtente, String emailConsenso, String flagConsensoMail)
			throws Exception {
		String prf = "[AttivitaDAOImpl::salvaConsensoInvioComunicazione]";
		LOG.info(prf + " BEGIN");
		Integer ris = 0;
		try {

			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE PBANDI_T_UTENTE ");
			// sql.append("SET FLAG_CONSENSO_MAIL = '" + flagConsensoMail +"',
			// EMAIL_CONSENSO = '"+emailConsenso+ "', DT_AGGIORNAMENTO = sysdate ");
			sql.append("SET FLAG_CONSENSO_MAIL = ?, EMAIL_CONSENSO = ?, DT_AGGIORNAMENTO = sysdate ");
			sql.append("WHERE ID_UTENTE = " + idUtente);
			LOG.info("query=" + sql.toString());
			LOG.info("con parametri: flagConsensoMail=" + flagConsensoMail + "; emailConsenso=" + emailConsenso);

			ris = getJdbcTemplate().update(sql.toString(), new Object[] { flagConsensoMail, emailConsenso });
			LOG.info(prf + " records modified: " + ris + "\n");

		} catch (Exception e) {
			LOG.error(prf + "Errore durante il salvataggio del consenso informato. ", e);
			throw new ErroreGestitoException("Errore durante il salvataggio del consenso informato. ", e);
		} finally {
			LOG.info(prf + " END");
		}
		return ris;
	}

	@Override
	public void salvaFiltriRicercaAttivita(Long idBeneficiario, Long progrBandoLineaIntervento, Long idProgetto,
			String descrAttivita, Long idUtente) throws Exception {
		String prf = "[AttivitaDAOImpl::salvaFiltriRicercaAttivita] ";
		LOG.info(prf + " BEGIN");

		if (idUtente == null || idUtente.intValue() == 0)
			return;

		try {

			if (progrBandoLineaIntervento != null && progrBandoLineaIntervento.intValue() == 0)
				progrBandoLineaIntervento = null;

			if (idProgetto != null && idProgetto.intValue() == 0)
				idProgetto = null;

			// Nuovo id per la PBANDI_T_RICERCA_ATTIVITA
			String sqlSeq = "select SEQ_PBANDI_T_RICERCA_ATTIVITA.nextval from dual ";
			LOG.info("\n" + sqlSeq.toString());
			Long id = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
			logger.info("Nuovo id SEQ_PBANDI_T_RICERCA_ATTIVITA = " + id);

			// Cancello eventuale record già presente.
			String sqlDelete = "DELETE PBANDI_T_RICERCA_ATTIVITA WHERE ID_UTENTE = " + idUtente;
			LOG.info("\n" + sqlDelete);
			getJdbcTemplate().update(sqlDelete);

			// Inserisco nuovo record.
			String oggi = "TO_DATE('" + DateUtil.getData() + "','DD/MM/YYYY')";
			String attivita = StringUtil.isBlank(descrAttivita) ? null : "'" + descrAttivita + "'";
			String sqlInsert = "INSERT INTO PBANDI_T_RICERCA_ATTIVITA ";
			sqlInsert += "(ID_RICERCA_ATTIVITA,ID_UTENTE,ID_SOGGETTO_BENEFICIARIO,PROGR_BANDO_LINEA_INTERVENTO,ID_PROGETTO,ATTIVITA,DT_RICERCA) ";
			sqlInsert += "VALUES (" + id + "," + idUtente + "," + idBeneficiario + "," + progrBandoLineaIntervento + ","
					+ idProgetto + "," + attivita + "," + oggi + ") ";
			LOG.info("\n" + sqlInsert);
			getJdbcTemplate().update(sqlInsert);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE : " + e.getMessage());
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public RicercaAttivitaPrecedenteDTO ricercaAttivitaPrecedente(Long idUtente, String descBreveTipoAnag,
			Long idSoggettoBeneficiario) throws Exception {
		String prf = "[AttivitaDAOImpl::ricercaAttivitaPrecedente] ";
		LOG.info(prf + " BEGIN");

		if (idUtente == null || idUtente.intValue() == 0 || idSoggettoBeneficiario == null
				|| idSoggettoBeneficiario.intValue() == 0)
			return null;

		RicercaAttivitaPrecedenteDTO result = new RicercaAttivitaPrecedenteDTO();
		try {

			// Cerco una ricerca precedente dell'utente.
			String sql = "SELECT ID_UTENTE,ID_SOGGETTO_BENEFICIARIO,PROGR_BANDO_LINEA_INTERVENTO,ID_PROGETTO,ATTIVITA,DT_RICERCA FROM PBANDI_T_RICERCA_ATTIVITA WHERE ID_UTENTE = "
					+ idUtente;
			LOG.info("\n" + sql);

			PbandiTRicercaAttivitaVO vo = null;
			try {
				vo = (PbandiTRicercaAttivitaVO) getJdbcTemplate().queryForObject(sql,
						new BeanRowMapper(PbandiTRicercaAttivitaVO.class));
			} catch (EmptyResultDataAccessException e) {
				// Nessun record trovato.
				RicercaAttivitaPrecedenteDTO dto = new RicercaAttivitaPrecedenteDTO();
				dto.setEsisteRicercaPrecedente(false);
				return dto;
			}

			if (idSoggettoBeneficiario.intValue() != vo.getIdSoggettoBeneficiario().intValue()) {
				LOG.info(prf + "Il Beneficiario è cambiato: ignoro la ricerca precedente.");
				RicercaAttivitaPrecedenteDTO dto = new RicercaAttivitaPrecedenteDTO();
				dto.setEsisteRicercaPrecedente(false);
				return dto;
			}

			String oggi = DateUtil.getData();
			String dataRicerca = DateUtil.getDate(vo.getDtRicerca());
			if (!oggi.equalsIgnoreCase(dataRicerca)) {
				LOG.info(prf + "La ricerca precedente è troppo vecchia: la ignoro.");
				RicercaAttivitaPrecedenteDTO dto = new RicercaAttivitaPrecedenteDTO();
				dto.setEsisteRicercaPrecedente(false);
				return dto;
			}

			List<AttivitaVO> listaAttivita = this.getAttivitaBENWithProcedures(vo.getProgrBandoLineaIntervento(),
					vo.getIdSoggettoBeneficiario(), idUtente, descBreveTipoAnag, 1L, vo.getIdProgetto(),
					vo.getAttivita());

			// Copiato da AttivitaServiceImpl.getAttivita().
			List<AttivitaVO> attivitaFiltered = new ArrayList<>();
			if (listaAttivita != null) {
				for (AttivitaVO att : listaAttivita) {
					if (att.getIdNotifica() == null) { // Perchè se rimuovo questo IF vedo notifiche con attivita
						attivitaFiltered.add(att);
					}
				}
			}

			result.setEsisteRicercaPrecedente(true);
			result.setListaAttivita(attivitaFiltered);
			result.setProgrBandoLineaIntervento(vo.getProgrBandoLineaIntervento());
			result.setIdProgetto(vo.getIdProgetto());
			result.setDescAttivita(vo.getAttivita());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE : " + e.getMessage());
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

}
