/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.AllegatiCronoprogrammaFasiRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.CronoprogrammaFasiItemRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.PbandiRProgettoIterVORowMapper;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.archivio.ArchivioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbservizit.dto.EsitoDTO;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.AllegatiCronoprogrammaFasi;
import it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi.CronoprogrammaFasiItem;
import it.csi.pbandi.pbservizit.exception.ErroreGestitoException;
import it.csi.pbandi.pbservizit.integration.dao.CronoProgrammaFasiDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRProgettoIterVO;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;

@Component
public class CronoProgrammaFasiDAOImpl extends JdbcDaoSupport implements CronoProgrammaFasiDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	private DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	private ArchivioBusinessImpl archivioBusinessImpl;

	@Autowired
	private ArchivioFileDAOImpl archivioFileDAOImpl;

	@Autowired
	public CronoProgrammaFasiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	// ottiene i dati relativi al cronoprogramma
	@Override
	public List<CronoprogrammaFasiItem> getDataCronoprogramma(Long idProgetto) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		// Controllo parametri

		List<CronoprogrammaFasiItem> cronoprogrammaFasiItem = new ArrayList<CronoprogrammaFasiItem>();

		String sql;
		sql = "SELECT c.id_iter, c.desc_iter,b.id_fase_monit,b.desc_fase_monit, a.data_limite, \r\n"
				+ "p.dt_fine_prevista AS data_prevista, p.dt_fine_effettiva AS data_effettiva, \r\n"
				+ "p.motivo_scostamento, i.id_dichiarazione_spesa, c.id_tipo_dichiaraz_spesa, i.flag_fase_chiusa, \r\n"
				+ "p.estremi_atto_amministrativo, a.flag_estremi_vis_obb, a.flag_prev_abilitata \r\n"
				+ "FROM PBANDI_R_BANDO_LINEA_FASIMONIT a \r\n"
				+ "JOIN PBANDI_T_DOMANDA d ON d.progr_bando_linea_intervento = a.progr_bando_linea_intervento \r\n"
				+ "JOIN PBANDI_T_PROGETTO pr ON Pr.id_domanda = d.id_domanda \r\n"
				+ "LEFT JOIN PBANDI_R_PROGETTO_FASE_MONIT p ON  p.id_progetto = pr.id_progetto AND p.id_fase_monit = a.id_fase_monit \r\n"
				+ "JOIN PBANDI_D_FASE_MONIT b ON a.id_fase_monit = b.id_fase_monit \r\n"
				+ "JOIN PBANDI_D_ITER c ON c.id_iter= b.id_iter \r\n"
				+ "LEFT JOIN PBANDI_R_PROGETTO_ITER i ON c.id_iter = i.id_iter AND pr.id_progetto = i.id_progetto \r\n"
				+ "WHERE pr.id_progetto = ? \r\n" + "ORDER BY c.ordinamento, b.ordinamento ";

		Object[] args = new Object[] { idProgetto };

		logger.info(prf + "\n" + sql + "\n");
		cronoprogrammaFasiItem = getJdbcTemplate().query(sql, args, new CronoprogrammaFasiItemRowMapper());

		logger.info(prf + " END");

		return cronoprogrammaFasiItem;

	}

	@Override
	public boolean existRowInProgettoFaseMonit(Long idProgetto, Long idFaseMonit) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		String sql;
		sql = "SELECT count(*) AS VALUE \r\n" + "FROM pbandi_r_progetto_fase_monit\r\n" + "WHERE ID_PROGETTO = ?\r\n"
				+ "AND ID_FASE_MONIT = ?";

		Object[] args = new Object[] { idProgetto, idFaseMonit };

		logger.info(prf + "\n" + sql + "\n");
		List<Integer> result = getJdbcTemplate().queryForList(sql, args, Integer.class);
		if (result != null && result.size() > 0 && result.get(0) != null && result.get(0) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void insertDateInProgettoFaseMonit(Long idProgetto, CronoprogrammaFasiItem dataCronoprogramma, Long idUtente)
			throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		String sql;
		sql = "INSERT INTO pbandi_r_progetto_fase_monit\r\n"
				+ "(ID_PROGETTO , ID_FASE_MONIT, ID_UTENTE_INS, DT_INSERIMENTO, DT_FINE_PREVISTA, DT_FINE_EFFETTIVA, MOTIVO_SCOSTAMENTO, ESTREMI_ATTO_AMMINISTRATIVO )\r\n"
				+ "VALUES (?,?,?,SYSDATE,?,?,?,?)";

		Object[] args = new Object[] { idProgetto, dataCronoprogramma.getIdFaseMonit(), idUtente,
				dataCronoprogramma.getDataPrevista(), dataCronoprogramma.getDataEffettiva(),
				dataCronoprogramma.getMotivoScostamento(), dataCronoprogramma.getEstremiAttoAmministrativo() };

		logger.info(prf + "\n" + sql + "\n");

		getJdbcTemplate().update(sql, args);

	}

	@Override
	public void updateDateInProgettoFaseMonit(Long idProgetto, CronoprogrammaFasiItem dataCronoprogramma, Long idUtente)
			throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		String sql;
		sql = "UPDATE PBANDI_R_PROGETTO_FASE_MONIT \r\n" + "SET 			\r\n" + "		ID_UTENTE_AGG = ?, \r\n"
				+ "		DT_AGGIORNAMENTO = SYSDATE, \r\n" + "		DT_FINE_PREVISTA = ?, \r\n"
				+ "		DT_FINE_EFFETTIVA = ?, \r\n" + "		MOTIVO_SCOSTAMENTO = ?, \r\n"
				+ "		ESTREMI_ATTO_AMMINISTRATIVO = ? \r\n" + "WHERE ID_PROGETTO = ? AND ID_FASE_MONIT = ?";

		Object[] args = new Object[] { idUtente, dataCronoprogramma.getDataPrevista(),
				dataCronoprogramma.getDataEffettiva(), dataCronoprogramma.getMotivoScostamento(),
				dataCronoprogramma.getEstremiAttoAmministrativo(), idProgetto, dataCronoprogramma.getIdFaseMonit() };

		logger.info(prf + "\n" + sql + "\n");

		getJdbcTemplate().update(sql, args);

	}

	@Override
	public List<AllegatiCronoprogrammaFasi> getAllegatiByIter(Long idProgetto, Long idIter) throws Exception {

		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		logger.info(prf + " BEGIN");

		List<AllegatiCronoprogrammaFasi> allegati;
		try {
			String sql;
			sql = "SELECT DISTINCT \r\n" + "T.ID_FILE_ENTITA, \r\n" + "T.ID_FILE, FF.ID_FOLDER, \r\n"
					+ "FF.ID_DOCUMENTO_INDEX, \r\n" + "FF.NOME_FILE,FF.SIZE_FILE,\r\n" + "D.ID_PROGETTO_ITER, \r\n"
					+ "D.ID_PROGETTO, \r\n" + "D.ID_ITER,\r\n" + "I.DESC_ITER, \r\n" + "D.FLAG_FASE_CHIUSA, \r\n"
					+ "D.ID_DICHIARAZIONE_SPESA\r\n"
					+ "FROM PBANDI_T_FILE_ENTITA T, PBANDI_C_ENTITA C,  PBANDI_T_FILE FF,\r\n"
					+ "PBANDI_R_PROGETTO_ITER D, PBANDI_R_PROGETTO_FASE_MONIT E, PBANDI_D_FASE_MONIT F, PBANDI_D_ITER I\r\n"
					+ "WHERE C.NOME_ENTITA= 'PBANDI_R_PROGETTO_ITER'\r\n" + "AND T.ID_ENTITA = C.ID_ENTITA\r\n"
					+ "AND T.ID_TARGET = D.ID_PROGETTO_ITER\r\n" + "AND T.ID_FILE = FF.ID_FILE\r\n"
					+ "AND D.ID_PROGETTO=E.ID_PROGETTO\r\n" + "AND D.ID_ITER = F.ID_ITER\r\n"
					+ "AND E.ID_FASE_MONIT= F.ID_FASE_MONIT\r\n" + "AND F.ID_ITER = ? \r\n"
					+ "AND I.ID_ITER= F.ID_ITER\r\n" + "AND D.ID_PROGETTO = ?";

			Object[] args = new Object[] { idIter, idProgetto };

			logger.info(prf + "\n" + sql + "\n");

			allegati = getJdbcTemplate().query(sql, args, new AllegatiCronoprogrammaFasiRowMapper());
			if (allegati != null) {
				LOG.info(prf + "Record trovati = " + allegati.size());
			} else {
				LOG.info(prf + "Record trovati = 0");
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel recuperare gli allegati dell'iter: ", e);
			throw new ErroreGestitoException(" ERRORE nel recuperare gli allegati dell'iter.");
		}
		return allegati;
	}

	@Override
	public EsitoDTO disassociaAllegato(Long idDocumentoIndex, Long idProgettoIter, Long idProgetto, Long idUtente,
			String idIride) throws Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idProgettoIter = " + idProgettoIter
				+ "; idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idIride = " + idIride);

		if (idDocumentoIndex == 0) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idProgettoIter == 0) {
			throw new InvalidParameterException("idProgettoIter non valorizzato");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null) {
			throw new InvalidParameterException("idIride non valorizzato");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_PBANDI_R_PROGETTO_ITER);
			if (idEntita == null)
				throw new ErroreGestitoException("Id entita non trovato.");

			Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex,
					idEntita.longValue(), idProgettoIter, idProgetto);
			esito.setEsito(esitoPbandisrv.getEsito());
			esito.setMessaggio(esitoPbandisrv.getMessage());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel disassociare gli allegati del cronoprogramma: ", e);
			throw new ErroreGestitoException(" ERRORE nel disassociare gli allegati del cronoprogramma.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoAssociaFilesDTO associaAllegati(AssociaFilesRequest associaFilesRequest, Long idIter, Long idUtente)
			throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato");
		}
		if (idUtente == 0) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
		try {

			BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_PBANDI_R_PROGETTO_ITER);

			String sql;
			sql = "SELECT ID_PROGETTO_ITER FROM PBANDI_R_PROGETTO_ITER WHERE ID_PROGETTO = ? AND ID_ITER = ?";

			Object[] args = new Object[] { associaFilesRequest.getIdProgetto(), idIter };
			logger.info("<idProgetto>: " + associaFilesRequest.getIdProgetto() + ", <idIter>: " + idIter);
			logger.info(prf + "\n" + sql + "\n");

			List<Long> idTarget = getJdbcTemplate().queryForList(sql, args, Long.class);
			if (idTarget != null && idTarget.size() > 0) {
				logger.info("<idProgettoIter>: " + idTarget.get(0));
				associaFilesRequest.setIdTarget(idTarget.get(0));
				esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
			} else {
				throw new ErroreGestitoException("ID_PROGETTO_ITER non trovato");
			}
			LOG.info(prf + esito.toString());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel associare allegati all'iter: ", e);
			throw new ErroreGestitoException(" ERRORE nel associare all'iter.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public Boolean getFlagFaseChiusaProgettoIter(Long idProgetto, Long idIter)
			throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato");
		}
		Boolean flagFaseChiusa = null;
		try {
			String sql;
			sql = "SELECT a.flag_fase_chiusa \r\n" + "FROM PBANDI_R_PROGETTO_ITER a \r\n"
					+ "WHERE a.id_progetto = ? AND a.id_iter = ? ";

			Object[] args = new Object[] { idProgetto, idIter };
			logger.info("<idProgetto>: " + idProgetto + ", <idIter>: " + idIter);
			logger.info(prf + "\n" + sql + "\n");

			List<Long> flag = getJdbcTemplate().queryForList(sql, args, Long.class);
			if (flag != null && flag.size() > 0) {
				logger.info("<flagFaseChiusa>: " + flag.get(0));
				flagFaseChiusa = flag.get(0) == null || flag.get(0) == 0 ? Boolean.FALSE : Boolean.TRUE;
			}

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getFlagFaseChiusaProgettoIter: ", e);
			throw new ErroreGestitoException(" ERRORE in getFlagFaseChiusaProgettoIter.");
		} finally {
			LOG.info(prf + " END");
		}

		return flagFaseChiusa;
	}

	@Override
	public void insertOrUpdateProgettoIter(Long idProgetto, Long idIter) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato");
		}

		try {
			String sql;
			sql = "SELECT * FROM PBANDI_R_PROGETTO_ITER WHERE id_progetto = ? AND id_iter = ? ";

			Object[] args = new Object[] { idProgetto, idIter };
			logger.info("<idProgetto>: " + idProgetto + ", <idIter>: " + idIter);
			logger.info(prf + "\n" + sql + "\n");

			List<PbandiRProgettoIterVO> progettoIters = getJdbcTemplate().query(sql, args,
					new PbandiRProgettoIterVORowMapper());
			Long idProgettoIter = null;
			Long flagFaseChiusaOld = null;
			if (progettoIters != null && progettoIters.size() > 0) {
				idProgettoIter = progettoIters.get(0).getIdProgettoIter();
				flagFaseChiusaOld = progettoIters.get(0).getFlagFaseChiusa();
				logger.info("<idProgettoIter>: " + idProgettoIter);
			}

			Long flagFaseChiusa = calculateFlagFaseChiusaProgettoIter(idProgetto, idIter);

			int rows = 0;
			if (idProgettoIter == null) {
				sql = "INSERT INTO PBANDI_R_PROGETTO_ITER (ID_PROGETTO_ITER, ID_PROGETTO, ID_ITER, FLAG_FASE_CHIUSA) \r\n"
						+ "VALUES (SEQ_PBANDI_R_PROGETTO_ITER.NEXTVAL, ?, ?, ?)";

				args = new Object[] { idProgetto, idIter, flagFaseChiusa };
				logger.info("<idProgetto>: " + idProgetto + ", <idIter>: " + idIter + ", <flagFaseChiusa>: "
						+ flagFaseChiusa);
				logger.info(prf + "\n" + sql + "\n");

				rows = getJdbcTemplate().update(sql, args);
			} else if (flagFaseChiusa != flagFaseChiusaOld) {
				sql = "UPDATE PBANDI_R_PROGETTO_ITER SET FLAG_FASE_CHIUSA = ? \r\n" + "WHERE ID_PROGETTO_ITER = ?";

				args = new Object[] { flagFaseChiusa, idProgettoIter };
				logger.info("<idProgettoIter>: " + idProgettoIter + ", <flagFaseChiusa>: " + flagFaseChiusa);
				logger.info(prf + "\n" + sql + "\n");

				rows = getJdbcTemplate().update(sql, args);
			}

			if (rows != 1) {
				LOG.info("Nessuna riga modificata");
			}

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in insertOrUpdateProgettoIter: ", e);
			throw new ErroreGestitoException(" ERRORE in insertOrUpdateProgettoIter.");
		} finally {
			LOG.info(prf + " END");
		}

	}

	private Long calculateFlagFaseChiusaProgettoIter(Long idProgetto, Long idIter)
			throws InvalidParameterException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idIter == 0) {
			throw new InvalidParameterException("idIter non valorizzato");
		}
		try {
			String sql;
			sql = "SELECT \r\n" + "	CASE \r\n" + "	    WHEN DT_FINE_EFFETTIVA IS NOT NULL THEN 1  \r\n"
					+ "	    ELSE 0\r\n" + "	END CASE\r\n" + "FROM (\r\n" + "	SELECT p.dt_fine_effettiva\r\n"
					+ "	FROM PBANDI_R_BANDO_LINEA_FASIMONIT a \r\n"
					+ "	JOIN PBANDI_T_DOMANDA d ON d.progr_bando_linea_intervento = a.progr_bando_linea_intervento \r\n"
					+ "	JOIN PBANDI_T_PROGETTO pr ON Pr.id_domanda = d.id_domanda \r\n"
					+ "	LEFT JOIN PBANDI_R_PROGETTO_FASE_MONIT p ON  p.id_progetto = pr.id_progetto AND p.id_fase_monit = a.id_fase_monit \r\n"
					+ "	JOIN PBANDI_D_FASE_MONIT b ON a.id_fase_monit = b.id_fase_monit \r\n"
					+ "	WHERE pr.id_progetto = ?  AND b.ID_ITER = ? \r\n" + "	ORDER BY p.DT_FINE_EFFETTIVA DESC\r\n"
					+ "	) fasi\r\n" + "WHERE ROWNUM = 1";

			Object[] args = new Object[] { idProgetto, idIter };
			logger.info("<idProgetto>: " + idProgetto + ", <idIter>: " + idIter);
			logger.info(prf + "\n" + sql + "\n");

			List<Long> flag = getJdbcTemplate().queryForList(sql, args, Long.class);
			if (flag != null && flag.size() > 0) {
				logger.info("<flagFaseChiusa>: " + flag.get(0));
				return flag.get(0);
			}
			return null;
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in calculateFlagFaseChiusaProgettoIter: ", e);
			throw new ErroreGestitoException(" ERRORE in calculateFlagFaseChiusaProgettoIter.");
		} finally {
			LOG.info(prf + " END");
		}
	}
}
