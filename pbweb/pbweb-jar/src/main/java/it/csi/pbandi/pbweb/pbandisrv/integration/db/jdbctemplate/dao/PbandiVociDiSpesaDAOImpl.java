/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;


import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionevocidispesa.VoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPagQuotParteDocSpVO;
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
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

public class PbandiVociDiSpesaDAOImpl extends PbandiDAO {
	
	public PbandiVociDiSpesaDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}
	
	public List<VoceDiSpesaVO> findVociDiSpesaAssociateADocumento(Long idDocumentoDiSpesa, Long idProgetto) {
		getLogger().begin();
		List<VoceDiSpesaVO> result = null;
		try {
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico as idRigoContoEconomico,")
					.append("v1.id_voce_di_spesa as idVoceDiSpesa, ")
					.append("v1.desc_voce_di_spesa as descVoceDiSpesa,")
					.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa as idQuotaParteDocSpesa,")
					.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa as idDocSpesa,")
					.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_quota_parte_doc_spesa as importo,")
					.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto as idProgetto,")
					.append("v1.ID_VOCE_DI_SPESA_PADRE as idVoceDiSpesaPadre,")
					.append("decode(v1.ID_VOCE_DI_SPESA_PADRE,null,'',v2.desc_voce_di_spesa ||' : ') || v1.desc_voce_di_spesa as descVoceDiSpesaCompleta,")
					.append("PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA as idTipoDocumentoDiSpesa");
			
			StringBuilder tables = new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA, PBANDI_T_RIGO_CONTO_ECONOMICO, PBANDI_D_VOCE_DI_SPESA v1, PBANDI_D_VOCE_DI_SPESA v2,PBANDI_T_DOCUMENTO_DI_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico = PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico"));
			conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa = v1.id_voce_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa = :idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto = :idProgetto"));
			conditionList.add(new StringBuilder("v1.ID_VOCE_DI_SPESA_PADRE = v2.id_voce_di_spesa(+)"));
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa,Types.NUMERIC);
			params.addValue("idProgetto", idProgetto,Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY descVoceDiSpesaCompleta");
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,new PbandiVoceDiSpesaRowMapper());

		} finally {
			getLogger().end();
		}
		return result;
	}
	
	public  VoceDiSpesaVO findDettaglioVoceDiSpesaAssociata (Long idQuotaParteDocSpesa){
		getLogger().begin();
		try {
			VoceDiSpesaVO result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa as idVoceDiSpesa,")
												.append("PBANDI_D_VOCE_DI_SPESA.desc_voce_di_spesa as descVoceDiSpesa,")
												.append("nvl(PBANDI_T_RIGO_CONTO_ECONOMICO.importo_ammesso_finanziamento, 0) as importoFinanziamento,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ore_lavorate as oreLavorate,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa as idDocSpesa,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto as idProgetto,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa as idQuotaParteDocSpesa,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico as idRigoContoEconomico,")
												.append("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre as idVoceDiSpesaPadre,")
												.append("PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa as idTipoDocumentoDiSpesa,")
												.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.importo_quota_parte_doc_spesa as importo");
			
			StringBuilder tables = new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA, PBANDI_T_RIGO_CONTO_ECONOMICO, PBANDI_D_VOCE_DI_SPESA, PBANDI_T_DOCUMENTO_DI_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico"));
			conditionList.add(new StringBuilder("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa = :idQuotaParteDocSpesa"));
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idQuotaParteDocSpesa", idQuotaParteDocSpesa,Types.NUMERIC);
			setWhereClause(conditionList, sqlSelect, tables);
			//logger.debug("Query:"+sqlSelect.toString());
			result = queryForObject(sqlSelect.toString(), params, new PbandiVoceDiSpesaRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	public List<VoceDiSpesaVO> findVociDiSpesa (Long idVoceDiSpesa,Long idRigoContoEconomico, Long idVoceDiSpesaPadre ) {
		getLogger().begin();
		try {
			List<VoceDiSpesaVO> result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT nvl(PBANDI_T_RIGO_CONTO_ECONOMICO.importo_ammesso_finanziamento, 0) as importoFinanziamento,")
												.append("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa as idVoceDiSpesa,")
												.append("PBANDI_D_VOCE_DI_SPESA.desc_voce_di_spesa as descVoceDiSpesa,")
												.append("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre as idVoceDiSpesaPadre,")
												.append("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico as idRigoContoEconomico");
			
			StringBuilder tables = new StringBuilder(" PBANDI_T_RIGO_CONTO_ECONOMICO, PBANDI_D_VOCE_DI_SPESA ");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			if (idRigoContoEconomico != null && !idRigoContoEconomico.equals(new Long(0))){
				conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico = :idRigoContoEconomico"));
				params.addValue("idRigoContoEconomico", idRigoContoEconomico, Types.NUMERIC);
			}
			if (idVoceDiSpesa != null &&  !idVoceDiSpesa.equals(new Long(0))) {
				conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa = :idVoceDiSpesa"));
				params.addValue("idVoceDiSpesa", idVoceDiSpesa, Types.NUMERIC);
			}
			
			if (idVoceDiSpesaPadre != null && !idVoceDiSpesaPadre.equals(new Long(0))) {
				conditionList.add(new StringBuilder("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre = :idVoceDiSpesaPadre"));
				params.addValue("idVoceDiSpesaPadre", idVoceDiSpesaPadre, Types.NUMERIC);
			}
				
			setWhereClause(conditionList, sqlSelect, tables);
			sqlSelect.append(" ORDER BY descVoceDiSpesa");
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,new PbandiVoceDiSpesaRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	
	public VoceDiSpesaVO findVoceDiSpesa (Long idVoceDiSpesa,Long idRigoContoEconomico ) {
		getLogger().begin();
		try {
			VoceDiSpesaVO result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT nvl(PBANDI_T_RIGO_CONTO_ECONOMICO.importo_ammesso_finanziamento, 0) as importoFinanziamento,")
												.append("v1.id_voce_di_spesa as idVoceDiSpesa,")
												.append("v1.desc_voce_di_spesa as descVoceDiSpesa,")
												.append("v1.id_voce_di_spesa_padre as idVoceDiSpesaPadre,")
												.append("decode(v1.id_voce_di_spesa_padre,null,'',v2.desc_voce_di_spesa||' - ')||v1.desc_voce_di_spesa as descVoceDiSpesaCompleta,")
												.append("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico as idRigoContoEconomico");
			
			StringBuilder tables = new StringBuilder(" PBANDI_T_RIGO_CONTO_ECONOMICO, PBANDI_D_VOCE_DI_SPESA v1,  PBANDI_D_VOCE_DI_SPESA v2");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("v1.id_voce_di_spesa = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
			conditionList.add(new StringBuilder("v1.id_voce_di_spesa_padre = v2.id_voce_di_spesa(+)"));
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			
				conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico = :idRigoContoEconomico"));
				params.addValue("idRigoContoEconomico", idRigoContoEconomico, Types.NUMERIC);
			
			
				conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa = :idVoceDiSpesa"));
				params.addValue("idVoceDiSpesa", idVoceDiSpesa, Types.NUMERIC);
			
			
			
				
			setWhereClause(conditionList, sqlSelect, tables);
			//logger.debug("Query:"+sqlSelect.toString());
			result = queryForObject(sqlSelect.toString(), params,new PbandiVoceDiSpesaRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	
	
	public Boolean inserisciVoceDiSpesaAssociataDocumento (Long idUtente, VoceDiSpesaVO vo) {
		getLogger().begin();
		try {
			long idQuotaParteDocSpesa = seqpbandiTQuotaParteDocSpesa.nextLongValue();
			StringBuilder sqlInsert = new StringBuilder("INSERT INTO PBANDI_T_QUOTA_PARTE_DOC_SPESA (")
												.append("id_quota_parte_doc_spesa,")
												.append("id_rigo_conto_economico,")
												.append("id_documento_di_spesa,")
												.append("id_progetto,")
												.append("importo_quota_parte_doc_spesa,")
												.append("ore_lavorate,")
												.append("id_utente_ins")
												.append(" ) VALUES (")
												.append(" :idQuotaParteDocSpesa,")
												.append(" :idRigoContoEconomico,")
												.append(" :idDocumentoDiSpesa,")
												.append(" :idProgetto,")
												.append(" :importo,")
												.append(" :oreLavorate,")
												.append(" :idUtente )");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idQuotaParteDocSpesa", idQuotaParteDocSpesa, Types.NUMERIC);
			params.addValue("idRigoContoEconomico", vo.getIdRigoContoEconomico(), Types.NUMERIC);
			params.addValue("idDocumentoDiSpesa", vo.getIdDocSpesa(), Types.NUMERIC);
			params.addValue("idProgetto", vo.getIdProgetto(), Types.NUMERIC);
			params.addValue("importo", vo.getImporto(), Types.NUMERIC);
			params.addValue("oreLavorate", vo.getOreLavorate(), Types.NUMERIC);
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			
		
			//logger.debug("Query:"+sqlInsert.toString());
			
			inserisci(sqlInsert.toString(), params);
			
			return Boolean.TRUE;
		} finally {
			getLogger().end();
		}
	}
	
	public List<PbandiRPagQuotParteDocSpVO> findPagamentiAssociatiVoceDiSpesa(Long idQuotaParteDocSpesa) {
		getLogger().begin();
		try {
			List<PbandiRPagQuotParteDocSpVO> result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA as idQuotaParteDocSpesa,")
												.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento as idPagamento,")
												.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_validato as importoValidato, ")
												.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_quietanzato as importoQuietanzato");
			
			StringBuilder tables = new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA = :idQuotaParteDocSpesa"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idQuotaParteDocSpesa", idQuotaParteDocSpesa, Types.NUMERIC);
			setWhereClause(conditionList, sqlSelect, tables);
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,new PbandiRPagQuotParteDocSpRowMapper());
			return result;
		} finally {
			getLogger().end();
		}
	}
	
	
	public boolean deleteVoceDiSpesaAssociata(Long  idQuotaParteDocSpesa){
		getLogger().begin();
		try {
			StringBuilder sqlDelete = new StringBuilder("DELETE PBANDI_T_QUOTA_PARTE_DOC_SPESA WHERE PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = :idQuotaParteDocSpesa");
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idQuotaParteDocSpesa", idQuotaParteDocSpesa, Types.NUMERIC);
			
			getLogger().debug("Query:"+sqlDelete.toString());
			elimina(sqlDelete.toString(), params);
			return true;
			
		} finally {
			getLogger().end();
		}
	}
	
	
	public boolean updateVoceDiSpesaAssociata (Long idUtente, VoceDiSpesaVO vo){
		getLogger().begin();
		try {
			StringBuilder sqlUpdate = new StringBuilder("UPDATE PBANDI_T_QUOTA_PARTE_DOC_SPESA SET ")
												.append("id_utente_agg = :idUtente,")
												.append("importo_quota_parte_doc_spesa = :importo,")
												.append("ore_lavorate = :oreLavorate, ")
												.append("id_rigo_conto_economico = :idRigoContoEconomico ");
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("id_quota_parte_doc_spesa = :idQuotaParteDocSpesa"));
			
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idUtente", idUtente, Types.NUMERIC);
			params.addValue("importo", vo.getImporto(), Types.NUMERIC);
			params.addValue("oreLavorate", vo.getOreLavorate(), Types.NUMERIC);
			params.addValue("idRigoContoEconomico", vo.getIdRigoContoEconomico(), Types.NUMERIC);
			params.addValue("idQuotaParteDocSpesa", vo.getIdQuotaParteDocSpesa(), Types.NUMERIC);
			
			setWhereClause(conditionList, sqlUpdate);
			return modifica(sqlUpdate.toString(), params).booleanValue();
		} finally {
			getLogger().end();
		}
	}

	/**
* 
*/
	private class PbandiVoceDiSpesaRowMapper implements
	RowMapper<VoceDiSpesaVO> {

		public VoceDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			VoceDiSpesaVO vo = (VoceDiSpesaVO) beanMapper.toBean(rs,
					VoceDiSpesaVO.class);
			return vo;
		}
	}
	
	private class PbandiRPagQuotParteDocSpRowMapper implements RowMapper<PbandiRPagQuotParteDocSpVO> {

		public PbandiRPagQuotParteDocSpVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			PbandiRPagQuotParteDocSpVO vo = (PbandiRPagQuotParteDocSpVO) beanMapper.toBean(rs,
					PbandiRPagQuotParteDocSpVO.class);
			return vo;
		}
	}

	
	
	/**
	 * bean iniettati da Spring
	 */
	AbstractDataFieldMaxValueIncrementer seqpbandiTQuotaParteDocSpesa;

	public AbstractDataFieldMaxValueIncrementer getSeqpbandiTQuotaParteDocSpesa() {
		return seqpbandiTQuotaParteDocSpesa;
	}

	public void setSeqpbandiTQuotaParteDocSpesa(
			AbstractDataFieldMaxValueIncrementer seqpbandiTQuotaParteDocSpesa) {
		this.seqpbandiTQuotaParteDocSpesa = seqpbandiTQuotaParteDocSpesa;
	}
	


}
