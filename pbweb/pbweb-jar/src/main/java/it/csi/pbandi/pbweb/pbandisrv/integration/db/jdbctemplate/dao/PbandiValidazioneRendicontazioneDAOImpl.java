package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;



import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoValidazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.VoceDiSpesaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTQuotaParteDocSpesaVO;
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

public class PbandiValidazioneRendicontazioneDAOImpl extends PbandiDAO {
	
	public PbandiValidazioneRendicontazioneDAOImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	private AbstractDataFieldMaxValueIncrementer seqpbandipagamenti;
	
	public AbstractDataFieldMaxValueIncrementer getSeqpbandipagamenti() {
		return seqpbandipagamenti;
	}

	public void setSeqpbandipagamenti(
			AbstractDataFieldMaxValueIncrementer seqpbandipagamenti) {
		this.seqpbandipagamenti = seqpbandipagamenti;
	}
		
	public java.util.List<PagamentoValidazioneVO> findPagamentiAssociati(Long idDocumentoDiSpesa, Long idProgetto){
		
		java.util.List<PagamentoValidazioneVO> result = new ArrayList<PagamentoValidazioneVO>();
		
			MapSqlParameterSource params = new MapSqlParameterSource();

			StringBuilder tables = (new StringBuilder(
					"PBANDI_R_PAGAMENTO_DOC_SPESA, PBANDI_R_DOC_SPESA_PROGETTO, "+
					"PBANDI_T_PAGAMENTO, "+
					"PBANDI_D_MODALITA_PAGAMENTO, "+
// FIX PBANDI-2314					"PBANDI_D_STATO_VALIDAZ_SPESA, "+
					"(select pds.ID_PAGAMENTO, "+
					" first_value(ds.ID_DICHIARAZIONE_SPESA) over (partition by pds.ID_PAGAMENTO order by ds.DT_DICHIARAZIONE desc, ds.ID_DICHIARAZIONE_SPESA desc,pds.ID_PAGAMENTO) as ID_DICHIARAZIONE_SPESA"+
					"  from PBANDI_R_PAGAMENTO_DICH_SPESA pds ,  PBANDI_T_DICHIARAZIONE_SPESA ds "+
					"  where pds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA) pagDich " 
						
				));
			
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT ");
					sqlSelect.append("PBANDI_D_MODALITA_PAGAMENTO.desc_modalita_pagamento as descModalitaPagamento, ");
					sqlSelect.append("PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento as idModalitaPagamento, ");
					sqlSelect.append("PBANDI_T_PAGAMENTO.id_pagamento as idPagamento, ");
					sqlSelect.append("PBANDI_T_PAGAMENTO.importo_pagamento as importoTotale, ");
					sqlSelect.append("pagDich.id_dichiarazione_spesa as idDichiarazioneDiSpesa,");
					sqlSelect.append("PBANDI_T_PAGAMENTO.dt_pagamento as dtPagamento, ");
					sqlSelect.append("PBANDI_T_PAGAMENTO.dt_valuta as dtValuta ");
// FIX PBANDI-2314					sqlSelect.append(",PBANDI_D_STATO_VALIDAZ_SPESA.desc_stato_validazione_spesa as descStatoPagamento, ");
// FIX PBANDI-2314					sqlSelect.append("PBANDI_D_STATO_VALIDAZ_SPESA.desc_breve_stato_validaz_spesa as descBreveStatoPagamento");
					
			sqlSelect.append(" FROM ").append(tables);
													  
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			conditionList.add(new StringBuilder("PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = ").append(":idDocumentoDiSpesa"));
			if(!isNull(idProgetto))
				conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = ").append(":idProgetto"));
			conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = ").append("PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			
			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_pagamento = PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento"));
			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_modalita_pagamento = PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento"));
// FIX PBANDI-2314			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_stato_validazione_spesa = PBANDI_D_STATO_VALIDAZ_SPESA.id_stato_validazione_spesa"));
			conditionList.add(new StringBuilder(" PBANDI_T_PAGAMENTO.id_pagamento = pagDich.id_pagamento(+)"));

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
			if(!isNull(idProgetto))
				params.addValue("idProgetto", idProgetto);
			
			setWhereClause(conditionList, sqlSelect, tables);
			result = query(sqlSelect.toString(), params, new PagamentoInValidazioneRowMapper());

	 
		
		return result;
	}
	
	/**
	 * 
	 * @author 71732
	 * Riscrittura di PagamentoRowMapper per it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.PagamentoValidazioneVO
	 */
	private class PagamentoInValidazioneRowMapper implements RowMapper<PagamentoValidazioneVO>{

		public PagamentoValidazioneVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			PagamentoValidazioneVO vo=(PagamentoValidazioneVO) beanMapper.toBean(rs, PagamentoValidazioneVO.class);
			 return vo;
		}
	}

	/**
	 * @author 71732
	 * @param idPagamento
	 * @return Tutte le voci di spesa associate al singolo pagamento
	 */
	public List<VoceDiSpesaPagamentoVO> findVociDiSpesaAssociateAPagamento(Long idPagamento) {
		List<VoceDiSpesaPagamentoVO> result = null;
		
		try {
			StringBuilder sqlSelect = new StringBuilder("SELECT ");
			sqlSelect.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_quietanzato as importoAssociato, ")
					.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA as idQuotaParte, ")
					.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_documento_di_spesa as idDocumentoDiSpesa, ")
					.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_validato as importoValidato, ")
					.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_dichiarazione_spesa   AS idDichiarazioneSpesa,")
					.append("CASE ")
					.append("WHEN PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre is not null ")
					.append("THEN (")
					.append("SELECT v1.desc_voce_di_spesa || ' : ' || PBANDI_D_VOCE_DI_SPESA.desc_voce_di_spesa ")
					.append("FROM PBANDI_D_VOCE_DI_SPESA v1 ")
					.append("WHERE v1.id_voce_di_spesa = PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre ")
					.append(") ELSE PBANDI_D_VOCE_DI_SPESA.desc_voce_di_spesa ")
					.append("END as descVoceSpesa, PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_RIGO_CONTO_ECONOMICO as idRigoContoEconomico, PBANDI_T_QUOTA_PARTE_DOC_SPESA.ore_lavorate as oreLavorate ");
			
			StringBuilder tables = new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP," +
					"PBANDI_T_QUOTA_PARTE_DOC_SPESA," +
					"PBANDI_T_RIGO_CONTO_ECONOMICO," +
					"PBANDI_D_VOCE_DI_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento = ").append(":idPagamento"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_quota_parte_doc_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico"));
			conditionList.add(new StringBuilder("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
									
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idPagamento", idPagamento,Types.NUMERIC);
									
			setWhereClause(conditionList, sqlSelect, tables);
			
			//logger.debug("Query:"+sqlSelect.toString());
			result = query(sqlSelect.toString(), params,new VoceDiSpesaInValidazioneRowMapper());

		} finally {
			getLogger().end();
		}
		
		return result;
	}
	
	private class VoceDiSpesaInValidazioneRowMapper implements RowMapper<VoceDiSpesaPagamentoVO> {

		public VoceDiSpesaPagamentoVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			VoceDiSpesaPagamentoVO vo = (VoceDiSpesaPagamentoVO) beanMapper.toBean(rs,
					VoceDiSpesaPagamentoVO.class);
			logger.info("vo.getImportoAssociato():"+vo.getImportoAssociato()+" , vo.getImportoValidato():"+vo.getImportoValidato());
			return vo;
		}
	}
	
	
	private class DocumentiDiSpesaPerValidazioneRowMapper implements 
	RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO>{

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO vo=
				(it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentidispesa.DocumentoDiSpesaVO.class);
			 return vo;
		}
	}
	
	// Jira PBANDI-2768
	private class PbandiTQuotaParteDocSpesaRowMapper implements RowMapper<PbandiTQuotaParteDocSpesaVO>{
		public PbandiTQuotaParteDocSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			PbandiTQuotaParteDocSpesaVO vo=(PbandiTQuotaParteDocSpesaVO) beanMapper.toBean(rs, PbandiTQuotaParteDocSpesaVO.class);
			 return vo;
		}
	}
	
	// Jira PBANDI-2768
	public Double findImportoValidatoSuVoceDiSpesa(Long idDocumentoDiSpesa, Long idProgetto) {

		// Select che recupera gli idRigoContoEconomico; usato nella IN della select principale.
		StringBuilder sqlSelectIdRigoContoEconomico = new StringBuilder("select qpds.id_rigo_conto_economico from pbandi_t_quota_parte_doc_spesa qpds ");
		sqlSelectIdRigoContoEconomico.append("where qpds.id_progetto = :idProgetto ");
		sqlSelectIdRigoContoEconomico.append("and qpds.id_documento_di_spesa = :idDocumentoDiSpesa ");
		
		StringBuilder sqlSelect = new StringBuilder("select sum(pqpds.importo_validato) from pbandi_t_quota_parte_doc_spesa qpds, pbandi_r_pag_quot_parte_doc_sp pqpds ");
		sqlSelect.append("where qpds.id_progetto = :idProgetto ");
		sqlSelect.append("and qpds.id_rigo_conto_economico in ("+sqlSelectIdRigoContoEconomico+") ");
		sqlSelect.append("and qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto);
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
		logger.info("\nQuery:"+sqlSelect.toString());
		Double importoValidatoSuVoceDiSpesa = queryForDouble(sqlSelect.toString(), params);
		
		logger.info("findImportoValidatoSuVoceDiSpesa(): importoValidatoSuVoceDiSpesa:"+importoValidatoSuVoceDiSpesa);
		return importoValidatoSuVoceDiSpesa;
	} 
	
	// Jira PBANDI-2768
	// Restituisce true se il validato di almeno una voce di spesa del conto economico � superiore all'ammesso per la stessa voce. 
	public Boolean validatoMaggioreAmmesso(Long idProgetto) {
		
		//StringBuilder sqlSelect = new StringBuilder("select valid.validato, ammes.ammesso from  ");
		StringBuilder sqlSelect = new StringBuilder("select count(*) from  ");
		sqlSelect.append("(select qpds.id_rigo_conto_economico, sum(pqpds.importo_validato) validato from pbandi_t_quota_parte_doc_spesa qpds, pbandi_r_pag_quot_parte_doc_sp pqpds "); 
		sqlSelect.append("where qpds.id_progetto = :idProgetto "); 
		sqlSelect.append("and qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa "); 
		sqlSelect.append("group by qpds.id_rigo_conto_economico) valid,  ");
		sqlSelect.append("(select rce.id_rigo_conto_economico, rce.importo_ammesso_finanziamento ammesso from pbandi_t_progetto p, pbandi_t_conto_economico ce, pbandi_t_rigo_conto_economico rce "); 
		sqlSelect.append("where p.id_domanda = ce.id_domanda  ");
		sqlSelect.append("and ce.id_conto_economico = rce.id_conto_economico "); 
		sqlSelect.append("and p.id_progetto = :idProgetto  ");
		sqlSelect.append("and ce.dt_fine_validita is null "); 
		sqlSelect.append("and ce.id_stato_conto_economico in (2,3,8,9)) ammes "); 
		sqlSelect.append("where valid.id_rigo_conto_economico = ammes.id_rigo_conto_economico ");
		sqlSelect.append("and valid.validato > ammes.ammesso ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto);

		//logger.info("\nQuery:"+sqlSelect.toString());
		Integer numRecord = queryForInt(sqlSelect.toString(), params);
				
		return (numRecord > 0);
	}

}