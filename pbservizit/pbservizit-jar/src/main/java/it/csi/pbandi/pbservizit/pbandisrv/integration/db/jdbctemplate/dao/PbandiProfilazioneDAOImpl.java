/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeneficiarioSoggettoRuoloRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeneficiarioUtenteRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.profilazione.BeneficiarioUtenteVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiProfilazioneDAOImpl extends PbandiDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	public PbandiProfilazioneDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	/**
	 * @deprecated
	 * @param idProgetto
	 * @return
	 */
	public List<BeneficiarioVO> findBeneficiariProgetto(Long idProgetto) {
		getLogger().begin();
		try {
			java.util.List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE (")
					.append("      SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
					.append("      FROM PBANDI_T_PERSONA_FISICA")
					.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("    )").append(" END as descrizione,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE null").append(" END as idFormaGiuridica,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE null").append(" END as idDimensioneImpresa,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("    THEN  (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE (")
					.append("      SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita")
					.append("      FROM PBANDI_T_PERSONA_FISICA")
					.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("    )").append(" END as dataIniziovalidita,")
					.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,PBANDI_T_SOGGETTO");

			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1 "));
			conditionList.add(new StringBuilder(" nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario, -1) <> 4"));
			conditionList
					.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = :idProgetto"));

			params.addValue("idProgetto", idProgetto, Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect, tables);

			result = query(sqlSelect.toString(), params, new PbandiBeneficiariRowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<BeneficiarioVO> findBeneficiario(Long idSoggettoBeneficiario) {
		getLogger().begin();
		try {
			java.util.List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder(
					" SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE (")
					.append("      SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
					.append("      FROM PBANDI_T_PERSONA_FISICA")
					.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("    )").append(" END as descrizione,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE null").append(" END as idFormaGiuridica,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE null").append(" END as idDimensioneImpresa,").append(" CASE ")
					.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("    THEN  (")
					.append("      SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita")
					.append("      FROM PBANDI_T_ENTE_GIURIDICO")
					.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
					.append("    )").append("    ELSE (")
					.append("      SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita")
					.append("      FROM PBANDI_T_PERSONA_FISICA")
					.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
					.append("    )").append(" END as dataIniziovalidita,")
					.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,PBANDI_T_SOGGETTO");

			sqlSelect.append(" FROM ").append(tables);

			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1 "));
			conditionList.add(new StringBuilder(" nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario, -1) <> 4"));
			conditionList
					.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = :idSoggettoBeneficiario"));

			params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.NUMERIC);

			setWhereClause(conditionList, sqlSelect, tables);

			result = query(sqlSelect.toString(), params, new PbandiBeneficiariRowMapper());

			return result;
		} finally {
			getLogger().end();
		}
	}

	public List<BeneficiarioUtenteVO> findBeneficiariDocProgBySoggettoRuoloDenominazioneOrIdBen(BigDecimal idSoggetto,
			String ruoloIride, String denominazione, Long idBeneficiario) throws DaoException {

		String prf = "[ProfilazioneDAOImpl::findBeneficiariDocProgBySoggettoRuoloDenominazioneOrIdBen] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto=" + idSoggetto);
		LOG.info(prf + " ruoloIride=[" + ruoloIride + "]");
		LOG.info(prf + " denominazione=[" + denominazione + "]");
		LOG.info(prf + " idBeneficiario=[" + idBeneficiario + "]");

		List<BeneficiarioUtenteVO> beneficiari = new ArrayList<>();

		denominazione = "%" + denominazione.trim() + "%";

		try {

			String sql = fileSqlUtil.getQuery("BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql");
			LOG.info(sql.toString());

			beneficiari = getJdbcTemplate().query(sql.toString(), new Object[] { ruoloIride, idSoggetto, ruoloIride,
					idBeneficiario, idBeneficiario, idBeneficiario, denominazione }, new BeneficiarioUtenteRowMapper());

			if (beneficiari != null && !beneficiari.isEmpty()) {
				LOG.info(prf + " beneficiari.size=" + beneficiari.size());
			}
		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException(
					"DaoException while trying to read BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException(
					"DaoException while trying to read BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException(
					"DaoException while trying to read BeneficiarioDocProgSoggettoRuoloDenominazioneVO.sql", e);
		} finally {
			LOG.info(prf + " END");
		}

		return beneficiari;
	}

	private class PbandiBeneficiariRowMapper implements RowMapper<BeneficiarioVO> {

		public BeneficiarioVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			BeneficiarioVO vo = (BeneficiarioVO) beanMapper.toBean(rs, BeneficiarioVO.class);
			return vo;
		}
	}
}