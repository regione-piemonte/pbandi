package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;



import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiProfilazioneDAOImpl extends PbandiDAO {
	

	
	public PbandiProfilazioneDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}


	/**
	 * @deprecated
	 * @param idProgetto
	 * @return
	 */
	public List<BeneficiarioVO> findBeneficiariProgetto (Long idProgetto) {
		getLogger().begin();
		try {
			java.util.List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder(" SELECT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE (")
												.append("      SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
												.append("      FROM PBANDI_T_PERSONA_FISICA")
												.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
												.append("    )")
												.append(" END as descrizione,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE null")
												.append(" END as idFormaGiuridica,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE null")
												.append(" END as idDimensioneImpresa,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("    THEN  (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE (")
												.append("      SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita")
												.append("      FROM PBANDI_T_PERSONA_FISICA")
												.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
												.append("    )")
												.append(" END as dataIniziovalidita,")
												.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,PBANDI_T_SOGGETTO");
			
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1 "));
			conditionList.add(new StringBuilder(" nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario, -1) <> 4"));
			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto = :idProgetto"));
			
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			
			result = query(sqlSelect.toString(), params, new PbandiBeneficiariRowMapper());
			
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	public List<BeneficiarioVO> findBeneficiario (Long idSoggettoBeneficiario) {
		getLogger().begin();
		try {
			java.util.List<BeneficiarioVO> result = new ArrayList<BeneficiarioVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder(" SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE (")
												.append("      SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
												.append("      FROM PBANDI_T_PERSONA_FISICA")
												.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
												.append("    )")
												.append(" END as descrizione,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE null")
												.append(" END as idFormaGiuridica,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("     THEN (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE null")
												.append(" END as idDimensioneImpresa,")
												.append(" CASE ")
												.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null")
												.append("    THEN  (")
												.append("      SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita")
												.append("      FROM PBANDI_T_ENTE_GIURIDICO")
												.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
												.append("    )")
												.append("    ELSE (")
												.append("      SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita")
												.append("      FROM PBANDI_T_PERSONA_FISICA")
												.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
												.append("    )")
												.append(" END as dataIniziovalidita,")
												.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,PBANDI_T_SOGGETTO");
			
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1 "));
			conditionList.add(new StringBuilder(" nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario, -1) <> 4"));
			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto"));
			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.id_soggetto = :idSoggettoBeneficiario"));
			
			params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			
			result = query(sqlSelect.toString(), params, new PbandiBeneficiariRowMapper());
			
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	 
		
	private class PbandiBeneficiariRowMapper implements RowMapper<BeneficiarioVO>{

		public BeneficiarioVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			BeneficiarioVO vo=(BeneficiarioVO) beanMapper.toBean(rs,
					BeneficiarioVO.class);
			 return vo;
		}
	}
}