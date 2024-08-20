/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.StoricoBeneficiarioDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoBeneficiarioRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoBeneficiarioRowMapperPF;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.VersioniRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.GestioneRichiesteVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.StoricoBeneficiarioVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.VersioniVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;

@Service
public class StoricoBeneficiarioDAOImpl extends JdbcDaoSupport implements StoricoBeneficiarioDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public StoricoBeneficiarioDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	private Long getIdEnteGiuridicoFromProgetto(Long idSoggetto) {

		String query = "WITH selezione AS (\r\n"
				+ "SELECT prsp.ID_ENTE_GIURIDICO \r\n"
				+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
				+ "WHERE  prsp.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
				+ "AND prsp.ID_SOGGETTO = :idSoggetto ORDER BY prsp.ID_PROGETTO desc) SELECT * FROM selezione where rownum < 2";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};

		return getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Long>()
		{
			@Override
			public  Long extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Long result = 0l;

				while (rs.next())
				{
					result = rs.getLong("ID_ENTE_GIURIDICO");
				}
				return result;
			}
		});
	}

	private Long getIdEnteGiuridicoFromDomanda(Long idSoggetto) {

		String query = "WITH selezione AS (\r\n"
				+ "SELECT prsd.ID_ENTE_GIURIDICO \r\n"
				+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
				+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 \r\n"
				+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 \r\n"
				+ "AND prsd.ID_SOGGETTO = :idSoggetto ORDER BY prsd.ID_DOMANDA desc) SELECT * FROM selezione where rownum < 2";

		Object[] args = new Object[]{idSoggetto};

		int[] types = new int[]{Types.NUMERIC};


		return getJdbcTemplate().query(query, args, types, new ResultSetExtractor<Long>()
		{
			@Override
			public  Long extractData(ResultSet rs) throws SQLException, DataAccessException
			{
				Long result = 0l;

				while (rs.next())
				{
					result = rs.getLong("ID_ENTE_GIURIDICO");
				}
				return result;
			}
		});
	}

	@Override
	public List<StoricoBeneficiarioVO> getStorico(Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[StoricoBeneficiarioDAOImpl::getStorico]";
		LOG.info(prf + " BEGIN");

		List<StoricoBeneficiarioVO> storico = new ArrayList<StoricoBeneficiarioVO>();
		try {

			Long idEnteGiuridico = this.getIdEnteGiuridicoFromDomanda(idSoggetto);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT pteg.DENOMINAZIONE_ENTE_GIURIDICO, pts.CODICE_FISCALE_SOGGETTO, pts.ndg, pts2.PARTITA_IVA, pdts.DESC_TIPO_SOGGETTO \r\n"
					+ "FROM PBANDI_T_ENTE_GIURIDICO pteg \r\n"
					+ "INNER JOIN PBANDI_T_SOGGETTO pts \r\n"
					+ "ON pteg.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
					+ "ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO \r\n"
					+ "INNER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "RIGHT JOIN PBANDI_T_SEDE pts2 \r\n"
					+ "ON prsds.ID_SEDE = pts2.ID_SEDE \r\n"
					+ "WHERE pts.ID_SOGGETTO = " + idSoggetto +" AND pts.ID_TIPO_SOGGETTO = 2 AND pteg.ID_ENTE_GIURIDICO = " + idEnteGiuridico + " AND prsd.ID_TIPO_ANAGRAFICA = 1");

			storico = getJdbcTemplate().query(sql.toString(),new StoricoBeneficiarioRowMapper());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return storico;
	}

	@Override
	public List<StoricoBeneficiarioVO> getStoricoFisico(Long idSoggetto, HttpServletRequest req) throws DaoException
	{

		String prf = "[StoricoBeneficiarioDAOImpl::getStoricoFisico]";
		LOG.info(prf + " BEGIN");

		List<StoricoBeneficiarioVO> storicoFisico = new ArrayList<StoricoBeneficiarioVO>();
		try {


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT \r\n" + 
					"	pts.ID_SOGGETTO , pts.ndg, \r\n" + 
					"	ptpf.COGNOME ,\r\n" + 
					"	ptpf.NOME ,\r\n" + 
					"	pts.ID_TIPO_SOGGETTO ,\r\n" + 
					"	pts.CODICE_FISCALE_SOGGETTO\r\n" + 
					"FROM\r\n" + 
					"	PBANDI_T_PERSONA_FISICA ptpf\r\n" + 
					"LEFT JOIN PBANDI_T_SOGGETTO pts ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = pts.ID_SOGGETTO)\r\n" + 
					"LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON\r\n" + 
					"	(ptpf.ID_SOGGETTO = prsd.ID_SOGGETTO)\r\n" + 
					"WHERE\r\n" + 
					"	pts.ID_SOGGETTO = "+idSoggetto+"\r\n" + 
					"	AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n" + 
					"	AND prsd.ID_TIPO_BENEFICIARIO <> 4");

			storicoFisico = getJdbcTemplate().query(sql.toString(),new StoricoBeneficiarioRowMapperPF());

		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return storicoFisico;
	}

	@Override
	public List<VersioniVO> getVersioni(Long idSoggetto, HttpServletRequest req) throws DaoException {
		String prf = "[StoricoBeneficiarioDAOImpl::getStorico]";
		LOG.info(prf + " BEGIN");

		List<VersioniVO> versioni = new ArrayList<VersioniVO>();
		List<VersioniVO> versioni2 = new ArrayList<VersioniVO>();
		List<VersioniVO> versionifinale = new ArrayList<VersioniVO>();
		try {


			
		// recupero le versioni chiuse dalla tabella pbandi PBANDI_R_SOGGETTO_PROGETTO prsp
			StringBuilder sql = new StringBuilder();	
			sql.append("SELECT\r\n"
					+ "    prsp.ID_PROGETTO,\r\n"
					+ "    ptp.DT_CONCESSIONE,\r\n"
					+ "    prsp.PROGR_SOGGETTO_PROGETTO ,\r\n"
					+ "    concat(ptpf.NOME, concat(' ', ptpf.COGNOME)) AS denom, prsd.ID_DOMANDA ,ptd.DT_PRESENTAZIONE_DOMANDA ,\r\n"
					+ "    pdsp.DESC_STATO_PROGETTO AS desc_stato, prsp.ID_UTENTE_INS \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON ptp.ID_STATO_PROGETTO = pdsp.ID_STATO_PROGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = prsd.ID_UTENTE_INS \r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON pts.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n"
					+ "WHERE\r\n"
					+ "     pdsp.ID_STATO_PROGETTO  IN (14,3,4)\r\n"
					+ "		and prsp.id_tipo_anagrafica =1 and prsp.id_tipo_beneficiario <>4\r\n"
					+ "    AND prsd.ID_SOGGETTO ="+ idSoggetto);


			ResultSetExtractor<List<VersioniVO>> rse = new ResultSetExtractor<List<VersioniVO>>() {

				@Override
				public List<VersioniVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<VersioniVO> vo = new ArrayList<VersioniVO>();
					while (rs.next())
					{
						VersioniVO v = new VersioniVO();

						v.setIdVersione(rs.getLong("ID_PROGETTO"));
						v.setDataRiferimento(rs.getDate("DT_CONCESSIONE"));
						v.setDenom(rs.getString("DENOM"));
						v.setProgSoggettoProgetto(rs.getLong("PROGR_SOGGETTO_PROGETTO"));
						v.setIdProgetto(rs.getLong("ID_PROGETTO"));
						vo.add(v);
					}

					return vo;
				}
			};

			versioni = getJdbcTemplate().query(sql.toString(),rse);
			
			
			// recupero le versioni dalla tabella PBANDI_R_SOGGETTO_DOMANDA; 
			
			
			String sqlDomanda="SELECT\r\n"
					+ "    DISTINCT prsd.ID_DOMANDA,\r\n"
					+ "    ptd.DT_PRESENTAZIONE_DOMANDA,\r\n"
					+ "    ptd.DT_PRESENTAZIONE_DOMANDA ,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    pdsd.DESC_STATO_DOMANDA  AS desc_stato, prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd  ON ptd.ID_STATO_DOMANDA  = pdsd.ID_STATO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_UTENTE = prsd.ID_UTENTE_AGG\r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON pts.ID_SOGGETTO = ptpf.ID_SOGGETTO\r\n"
					+ "WHERE\r\n"
					+ "	pdsd.ID_STATO_DOMANDA IN (6,8,9,10,12) and prsd.id_tipo_anagrafica =1 and prsd.id_tipo_beneficiario <>4\r\n"
					+ "   AND prsd.ID_SOGGETTO ="+ idSoggetto;
			
			ResultSetExtractor<List<VersioniVO>> rse2 = new ResultSetExtractor<List<VersioniVO>>() {

				@Override
				public List<VersioniVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<VersioniVO> vo = new ArrayList<VersioniVO>();
					while (rs.next())
					{
						VersioniVO v = new VersioniVO();

						v.setIdVersione(rs.getLong("ID_DOMANDA"));
						v.setDataRiferimento(rs.getDate("DT_PRESENTAZIONE_DOMANDA"));
						v.setCognomeUtente(rs.getString("COGNOME"));
						v.setNomeUtente(rs.getString("NOME"));
						v.setDenom(v.getCognomeUtente() + " "+ v.getNomeUtente());
						v.setProgSoggettoDomanda(rs.getLong("PROGR_SOGGETTO_DOMANDA"));
						v.setIdDomanda(rs.getLong("ID_DOMANDA"));
						vo.add(v);
					}

					return vo;
				}
			};

			versioni2 = getJdbcTemplate().query(sqlDomanda.toString(),rse2);
			

			versionifinale.addAll(versioni);
			versionifinale.addAll(versioni2);
			
		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return versionifinale;
	}


}
