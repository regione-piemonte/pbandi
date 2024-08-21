/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.FiltroTrasferimentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CausaleTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.LineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SoggettoTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.TrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTTrasferimentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;




public class PbandiTrasferimentoDAOImpl extends PbandiDAO {
	
	public PbandiTrasferimentoDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	
	private static int RICEVENTE_TRASFERIMENTO = 1;
	
	public Long getSeqpbandiTTrasferimento() {
		
		AbstractDataFieldMaxValueIncrementer sequence=new org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer();
		sequence.setIncrementerName("SEQ_PBANDI_T_TRASFERIMENTO");
		sequence.setDataSource(super.getDataSource());
		Long sequenceValue=sequence.nextLongValue();
		return sequenceValue.longValue();
	}


	public List<CausaleTrasferimentoVO> findElencoCausaliTrasferimenti() {
		getLogger().begin();
		try {
			List<CausaleTrasferimentoVO> result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT ID_CAUSALE_TRASFERIMENTO as idCausaleTrasferimento,")
												.append("DESC_CAUSALE_TRASFER as descCausaleTrasferimento ")
												.append("FROM PBANDI_D_CAUSALE_TRASFERIMENTO ")
												.append("WHERE TRUNC(sysdate) < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate)  +1) ")
												.append("ORDER BY ID_CAUSALE_TRASFERIMENTO");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			result = query(sqlSelect.toString(), params,new PbandiCausaleTrasferimentoRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}

			
	public List<TrasferimentoVO> findTrasferimenti (FiltroTrasferimentoDTO filtro ) {
		getLogger().begin();
		try {
			List<TrasferimentoVO> result = null;
			
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_TRASFERIMENTO.ID_TRASFERIMENTO as idTrasferimento,")
			.append("PBANDI_T_TRASFERIMENTO.COD_TRASFERIMENTO as codiceTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.DATA_TRASFERIMENTO as dtTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO as idCausaleTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.IMPORTO_TRASFERIMENTO as importoTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_SOGGETTO as idSoggettoBeneficiario, ")
			.append("PBANDI_T_TRASFERIMENTO.FLAG_PUBBLICO_PRIVATO as flagPubblicoPrivato, ")
			.append("(SELECT PBANDI_D_CAUSALE_TRASFERIMENTO.DESC_CAUSALE_TRASFER FROM PBANDI_D_CAUSALE_TRASFERIMENTO ")
			.append(" where PBANDI_D_CAUSALE_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO = PBANDI_T_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO) as descCausaleTrasferimento, ")
			.append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as cfBeneficiario, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_LINEA_DI_INTERVENTO as idLineaDiIntervento, ")
			.append("PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO as denominazioneBeneficiario ");
					
			StringBuilder tables = new StringBuilder(" PBANDI_T_TRASFERIMENTO, PBANDI_T_SOGGETTO, PBANDI_T_ENTE_GIURIDICO, ");		

			tables.append(" ( select PBANDI_T_ENTE_GIURIDICO.id_soggetto, max(PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico) AS id_ente_giuridico ") 
			.append(" from PBANDI_T_ENTE_GIURIDICO, PBANDI_T_TRASFERIMENTO where ")
			.append(" PBANDI_T_TRASFERIMENTO.id_soggetto= PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
//			.append(" AND TRUNC(sysdate) < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)+1) ")
			.append(" group by PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
			.append("  ) A");

			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto = PBANDI_T_TRASFERIMENTO.id_soggetto"));
			conditionList.add(new StringBuilder("A.id_soggetto =  PBANDI_T_TRASFERIMENTO.id_soggetto"));
			conditionList.add(new StringBuilder("PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico =  A.id_ente_giuridico"));
						
			MapSqlParameterSource params = new MapSqlParameterSource();
						
			if (filtro.getCodiceTrasferimento() != null && !filtro.getCodiceTrasferimento().equals("")){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.COD_TRASFERIMENTO = :codiceTrasferimento"));
				params.addValue("codiceTrasferimento", filtro.getCodiceTrasferimento(), Types.VARCHAR);
			}
			if (filtro.getIdCausaleTrasferimento() != null && !filtro.getIdCausaleTrasferimento().equals(new Long(0))){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO = :idCausaleTrasferimento"));
				params.addValue("idCausaleTrasferimento", filtro.getIdCausaleTrasferimento(), Types.NUMERIC);
			}
			if (filtro.getIdSoggettoBeneficiario() != null && !filtro.getIdSoggettoBeneficiario().equals(new Long(0))){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.ID_SOGGETTO = :idSoggettoBeneficiario"));
				params.addValue("idSoggettoBeneficiario", filtro.getIdSoggettoBeneficiario(), Types.NUMERIC);
			}
			if (filtro.getFlagPubblicoPrivato() != null && !filtro.getFlagPubblicoPrivato().equals("0") && !filtro.getFlagPubblicoPrivato().equals("")){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.FLAG_PUBBLICO_PRIVATO = :flagPubblicoPrivato"));
				params.addValue("flagPubblicoPrivato", filtro.getFlagPubblicoPrivato(), Types.CHAR);
			}
			if (filtro.getDaDataTrasferimento() != null){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.DATA_TRASFERIMENTO >= :dtDaDataTrasferimento"));
				params.addValue("dtDaDataTrasferimento", filtro.getDaDataTrasferimento(), Types.DATE);
			}
			if (filtro.getADataTrasferimento() != null){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.DATA_TRASFERIMENTO <= :dtADataTrasferimento"));
				params.addValue("dtADataTrasferimento", filtro.getADataTrasferimento(), Types.DATE);
			}
			if (filtro.getIdLineaDiIntervento() != null){
				conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.ID_LINEA_DI_INTERVENTO = :idLineaDiIntervento"));
				params.addValue("idLineaDiIntervento", filtro.getIdLineaDiIntervento(), Types.NUMERIC);
			}
							
			setWhereClause(conditionList, sqlSelect);
			sqlSelect.append(" ORDER BY PBANDI_T_TRASFERIMENTO.ID_TRASFERIMENTO");
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,new PbandiTrasferimentoRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	public TrasferimentoVO findTrasferimento (Long idTrasferimento) {
		getLogger().begin();
		try {
			TrasferimentoVO result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_TRASFERIMENTO.ID_TRASFERIMENTO as idTrasferimento,")
			.append("PBANDI_T_TRASFERIMENTO.COD_TRASFERIMENTO as codiceTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.DATA_TRASFERIMENTO as dtTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO as idCausaleTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.IMPORTO_TRASFERIMENTO as importoTrasferimento, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_SOGGETTO as idSoggettoBeneficiario, ")
			.append("PBANDI_T_TRASFERIMENTO.FLAG_PUBBLICO_PRIVATO as flagPubblicoPrivato, ")
			.append("(SELECT PBANDI_D_CAUSALE_TRASFERIMENTO.DESC_CAUSALE_TRASFER FROM PBANDI_D_CAUSALE_TRASFERIMENTO ")
			.append(" where PBANDI_D_CAUSALE_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO = PBANDI_T_TRASFERIMENTO.ID_CAUSALE_TRASFERIMENTO) as descCausaleTrasferimento, ")
			.append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as cfBeneficiario, ")
			.append("PBANDI_T_TRASFERIMENTO.ID_LINEA_DI_INTERVENTO as idLineaDiIntervento, ")
			.append("PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO as denominazioneBeneficiario ");
					
			StringBuilder tables = new StringBuilder(" PBANDI_T_TRASFERIMENTO, PBANDI_T_SOGGETTO, PBANDI_T_ENTE_GIURIDICO, ");		

			tables.append(" ( select PBANDI_T_ENTE_GIURIDICO.id_soggetto, max(PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico) AS id_ente_giuridico ") 
			.append(" from PBANDI_T_ENTE_GIURIDICO, PBANDI_T_TRASFERIMENTO where ")
			.append(" PBANDI_T_TRASFERIMENTO.id_trasferimento = :idTrasferimento AND ")
			.append(" PBANDI_T_TRASFERIMENTO.id_soggetto= PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
//			.append(" AND TRUNC(sysdate) < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)+1) ")
			.append(" group by PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
			.append("  ) A");

			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_TRASFERIMENTO.id_trasferimento = :idTrasferimento"));
			conditionList.add(new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto = PBANDI_T_TRASFERIMENTO.id_soggetto"));
			conditionList.add(new StringBuilder("A.id_soggetto =  PBANDI_T_TRASFERIMENTO.id_soggetto"));
			conditionList.add(new StringBuilder("PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico =  A.id_ente_giuridico"));
						
			MapSqlParameterSource params = new MapSqlParameterSource();
						
			params.addValue("idTrasferimento", idTrasferimento, Types.NUMERIC);			
				
			setWhereClause(conditionList, sqlSelect);
			//logger.debug("Query:"+sqlSelect.toString());
			result = queryForObject(sqlSelect.toString(), params,new PbandiTrasferimentoRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	public List<SoggettoTrasferimentoVO> findSoggettiTrasferimento () {
		getLogger().begin();
		try {
			
			List<SoggettoTrasferimentoVO> result = null;
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ")
			.append("PBANDI_T_ENTE_GIURIDICO.ID_SOGGETTO as idSoggettoBeneficiario, ")
			.append("PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as cfBeneficiario, ")
			.append("PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO as denominazioneBeneficiario ");
					
			StringBuilder tables = new StringBuilder(" PBANDI.PBANDI_T_SOGGETTO, PBANDI_T_ENTE_GIURIDICO, ");
			
			tables.append(" ( select PBANDI_T_ENTE_GIURIDICO.id_soggetto, ")
			.append(" max(PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico) AS id_ente_giuridico ") 
			.append(" from PBANDI_T_ENTE_GIURIDICO, PBANDI_T_SOGGETTO where ")
			.append(" PBANDI_T_SOGGETTO.RICEVENTE_TRASF = :riceventeTrasferimento ")
			.append(" AND PBANDI_T_SOGGETTO.id_soggetto= PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
			.append(" AND TRUNC(sysdate) < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)+1) ")
			.append(" group by PBANDI_T_ENTE_GIURIDICO.id_soggetto ")
			.append("  ) A");

			
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			//			conditionList.add(new StringBuilder(" PBANDI_T_SOGGETTO.RICEVENTE_TRASF = :riceventeTrasferimento "));
			
			conditionList.add(new StringBuilder("PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico =  A.id_ente_giuridico"));
			conditionList.add(new StringBuilder("PBANDI_T_SOGGETTO.id_soggetto =  PBANDI_T_ENTE_GIURIDICO.id_soggetto"));
			conditionList.add(new StringBuilder(" TRUNC(sysdate)  < NVL(TRUNC(PBANDI_T_ENTE_GIURIDICO.DT_FINE_VALIDITA), TRUNC(sysdate)  +1) "));

			MapSqlParameterSource params = new MapSqlParameterSource();
						
			params.addValue("riceventeTrasferimento", RICEVENTE_TRASFERIMENTO, Types.INTEGER);			
				
			setWhereClause(conditionList, sqlSelect, tables);
			
			sqlSelect.append(" ORDER BY PBANDI_T_ENTE_GIURIDICO.DENOMINAZIONE_ENTE_GIURIDICO ");
			
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params, new PbandiSoggettoTrasferimentoRowMapper());
			
			return result;
			
		} finally {
			getLogger().end();
		}
	}
	
	
	public Boolean inserisciTrasferimento (Long idUtente, PbandiTTrasferimentoVO vo) {
		getLogger().begin();
		try {
			
//			long idTrasferimento = getSeqpbandiTTrasferimento().nextLongValue();
			long idTrasferimento = getSeqpbandiTTrasferimento();
			getLogger().debug("idTrasferimento = "+idTrasferimento);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
						
			StringBuilder sqlInsert = new StringBuilder("INSERT INTO PBANDI_T_TRASFERIMENTO (")
			.append(" ID_TRASFERIMENTO, ")
			.append(" ID_LINEA_DI_INTERVENTO, ")
			.append(" COD_TRASFERIMENTO, ")
			.append(" DATA_TRASFERIMENTO, ")
			.append(" ID_CAUSALE_TRASFERIMENTO, ")
			.append(" IMPORTO_TRASFERIMENTO, ")
			.append(" ID_SOGGETTO, ")
			.append(" FLAG_PUBBLICO_PRIVATO, ")
			.append(" id_utente_ins")            
			.append(" ) VALUES (")
			.append(" :idTrasferimento,")
			.append(" :idLineaDiIntervento,")
			.append(" :codiceTrasferimento, ")
			.append(" :dtTrasferimento, ")
			.append(" :idCausaleTrasferimento, ")
			.append(" :importoTrasferimento, ")
			.append(" :idSoggettoBeneficiario, ")
			.append(" :flagPubblicoPrivato, ")			
			.append(" :idUtente )");
		
			params.addValue("idTrasferimento", idTrasferimento, Types.NUMERIC);
			params.addValue("idLineaDiIntervento", vo.getIdLineaDiIntervento(), Types.NUMERIC);
			params.addValue("codiceTrasferimento", vo.getCodiceTrasferimento(), Types.VARCHAR);
			params.addValue("dtTrasferimento", vo.getDtTrasferimento(), Types.DATE);
			params.addValue("idCausaleTrasferimento", vo.getIdCausaleTrasferimento(), Types.NUMERIC);
			params.addValue("importoTrasferimento", vo.getImportoTrasferimento(), Types.NUMERIC);
			params.addValue("idSoggettoBeneficiario", vo.getIdSoggettoBeneficiario(), Types.NUMERIC);
			params.addValue("flagPubblicoPrivato", vo.getFlagPubblicoPrivato(), Types.CHAR);	
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			
			inserisci(sqlInsert.toString(), params);
			
			return Boolean.TRUE;
		} finally {
			getLogger().end();
		}
	}
		
	public boolean deleteTrasferimento(Long  idTrasferimento){
		getLogger().begin();
		try {
			StringBuilder sqlDelete = new StringBuilder("DELETE PBANDI_T_TRASFERIMENTO WHERE PBANDI_T_TRASFERIMENTO.ID_TRASFERIMENTO = :idTrasferimento");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idTrasferimento", idTrasferimento, Types.NUMERIC);
			
			getLogger().debug("Query:"+sqlDelete.toString());
			elimina(sqlDelete.toString(), params);
			return true;
			
		} finally {
			getLogger().end();
		}
	}
	
	
	public boolean updateTrasferimento (Long idUtente, PbandiTTrasferimentoVO vo){
		getLogger().begin();
		try {
			StringBuilder sqlUpdate = new StringBuilder("UPDATE PBANDI_T_TRASFERIMENTO SET ")
			.append("COD_TRASFERIMENTO = :codiceTrasferimento, ")
			.append("ID_LINEA_DI_INTERVENTO = :idLineaDiIntervento, ")
			.append("DATA_TRASFERIMENTO = :dtTrasferimento, ")
			.append("ID_CAUSALE_TRASFERIMENTO = :idCausaleTrasferimento, ")
			.append("IMPORTO_TRASFERIMENTO = :importoTrasferimento, ")
//			.append("ID_SOGGETTO = :idSoggettoBeneficiario, ")
			.append("FLAG_PUBBLICO_PRIVATO = :flagPubblicoPrivato, ")
			.append("id_utente_agg = :idUtente ");
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("ID_TRASFERIMENTO = :idTrasferimento"));
						
			MapSqlParameterSource params = new MapSqlParameterSource();			
			
			params.addValue("idTrasferimento", vo.getIdTrasferimento(), Types.NUMERIC);
			params.addValue("idLineaDiIntervento", vo.getIdLineaDiIntervento(), Types.NUMERIC);
			params.addValue("codiceTrasferimento", vo.getCodiceTrasferimento(), Types.VARCHAR);
			params.addValue("dtTrasferimento", vo.getDtTrasferimento(), Types.DATE);
			params.addValue("idCausaleTrasferimento", vo.getIdCausaleTrasferimento(), Types.NUMERIC);
			params.addValue("importoTrasferimento", vo.getImportoTrasferimento(), Types.NUMERIC);
//			params.addValue("idSoggettoBeneficiario", vo.getIdSoggetto(), Types.NUMERIC);
			params.addValue("flagPubblicoPrivato", vo.getFlagPubblicoPrivato(), Types.CHAR);	
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlUpdate);
			return modifica(sqlUpdate.toString(), params).booleanValue();
		} finally {
			getLogger().end();
		}
	}

	/**
* 
*/
	private class PbandiTrasferimentoRowMapper implements
			RowMapper<TrasferimentoVO> {

		public TrasferimentoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			TrasferimentoVO vo = (TrasferimentoVO) beanMapper.toBean(rs, TrasferimentoVO.class);
			return vo;
		}
	}

	private class PbandiCausaleTrasferimentoRowMapper implements
		RowMapper<CausaleTrasferimentoVO> {

		public CausaleTrasferimentoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			CausaleTrasferimentoVO vo = (CausaleTrasferimentoVO) beanMapper.toBean(rs, CausaleTrasferimentoVO.class);
			return vo;
		}
	}

	
	private class PbandiSoggettoTrasferimentoRowMapper implements
		RowMapper<SoggettoTrasferimentoVO> {
	
		public SoggettoTrasferimentoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			SoggettoTrasferimentoVO vo = (SoggettoTrasferimentoVO) beanMapper.toBean(rs, SoggettoTrasferimentoVO.class);
			return vo;
		}
	}


	public List<LineaDiInterventoVO> findNormativeTrasferimento() {
		
			
		List<LineaDiInterventoVO> result = null;
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("SELECT * "
					+ " FROM  PBANDI_D_LINEA_DI_INTERVENTO"
					+ " WHERE TRUNC(sysdate) < NVL(TRUNC(DT_FINE_VALIDITA), TRUNC(sysdate)  +1) AND "
					+ "    ID_TIPO_LINEA_INTERVENTO = :idTipoLinea AND FLAG_TRASFERIMENTI = 'S'");		
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("idTipoLinea", 1, Types.INTEGER);	
			
			//logger.debug("Query:"+sqlSelect.toString());
			result =  query(sqlSelect.toString(), params, new PbandiLineaDiInterventoRowMapper());
			
			return  result;
	
	}
	
	private class PbandiLineaDiInterventoRowMapper implements
			RowMapper<LineaDiInterventoVO> {

		public LineaDiInterventoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			LineaDiInterventoVO vo = (LineaDiInterventoVO) beanMapper.toBean(rs, LineaDiInterventoVO.class);
			return vo;
		}
	}

}
