/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;


import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PDocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoVoceDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

public class PbandiPagamentiDAOImpl extends PbandiDAO {
	
	public PbandiPagamentiDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	private AbstractDataFieldMaxValueIncrementer seqpbandipagamenti;
	
	public AbstractDataFieldMaxValueIncrementer getSeqpbandipagamenti() {
		return seqpbandipagamenti;
	}

	public void setSeqpbandipagamenti(
			AbstractDataFieldMaxValueIncrementer seqpbandipagamenti) {
		this.seqpbandipagamenti = seqpbandipagamenti;
	}

	/**
	 * TODO: Gestione importoRendicontabilePagato
	 * 
	 */
	public java.util.List<PagamentoQuotePartiVO> findPagamentiAssociati(Long idDocumentoDiSpesa, Long idProgetto, String dataPagamento, boolean visualizzaImportoRendicPagato){
		
		java.util.List<PagamentoQuotePartiVO> result = new ArrayList<PagamentoQuotePartiVO>();
			
			/**
			 * I parametri di ricerca non sono tutti obbligatori
			 */
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("");
			
			sqlSelect.append("" +
					"SELECT PBANDI_V_PAGAM_PROG_DOC_SPESA.ID_PAGAMENTO   AS idPagamento " +
					", PBANDI_V_PAGAM_PROG_DOC_SPESA.IP                  AS importoPagamento " +
					", pagamento.DESC_BREVE_MODALITA_PAGAMENTO           AS descBreveModalitaPagamento " +
					", pagamento.DESC_MODALITA_PAGAMENTO                 AS descrizioneModalitaPagamento" +
					", pagamento.ID_MODALITA_PAGAMENTO                   AS idModalitaPagamento" +
					", pagamento.ID_CAUSALE_PAGAMENTO as idCausalePagamento, pagamento.RIF_PAGAMENTO as rifPagamento"+
					", nvl(sum(PBANDI_V_PAGAM_PROG_DOC_SPESA.IQ),0)      AS importoRendicontabilePagato" +
					", pagamento.isUsedDichiarazioni                     AS isUsedDichiarazioni" +
			        ", CASE " +
					"  WHEN NVL(SUM(PBANDI_V_PAGAM_PROG_DOC_SPESA.IQ),-1) > 0 " +
					"   THEN 'S' " +
					"   ELSE 'N'" +
					"  END                                               AS isQuietanzato, " +
					"  PBANDI_V_PAGAM_PROG_DOC_SPESA.IQ                  AS importoQuietanzato, " +
					"  pbandi_v_pagam_prog_doc_spesa.delta_i       AS importoResiduoUtilizzabile ");
			
			if(!isNull(dataPagamento))
				sqlSelect.append(", pagamento."+dataPagamento+"          AS dtPagamento");
			
			sqlSelect.append(" " +
					"FROM " +
					"  PBANDI_V_PAGAM_PROG_DOC_SPESA, " +
					"  ( select distinct pag.id_pagamento," +
					" pag.ID_CAUSALE_PAGAMENTO, pag.RIF_PAGAMENTO,	"+ // new fields
					"           pag.dt_pagamento," +
					"           pag.dt_valuta," +
					"           modpag.id_modalita_pagamento," +
					"           modpag.desc_breve_modalita_pagamento," +
					"           modpag.desc_modalita_pagamento," +
					"           pag.ora_rowscn," +
					"           case when exists (select 'x' " +
					"                               from pbandi_r_pagamento_dich_spesa pagdich " +
					"                               where pag.id_pagamento = pagdich.id_pagamento " +
					"                            ) " +
					"                then 'S' " +
					"                else 'N' " +
					"            end isUsedDichiarazioni " +
					"    from PBANDI_T_PAGAMENTO pag, " +
					"        PBANDI_D_MODALITA_PAGAMENTO modpag " +
					"    where pag.id_modalita_pagamento = modpag.id_modalita_pagamento " +
					"  ) pagamento " +
					"WHERE PBANDI_V_PAGAM_PROG_DOC_SPESA.id_documento_di_spesa = :idDocumentoDiSpesa " +
					"  and PBANDI_V_PAGAM_PROG_DOC_SPESA.id_progetto = :idProgetto " +
					"  and pagamento.ID_PAGAMENTO = PBANDI_V_PAGAM_PROG_DOC_SPESA.ID_PAGAMENTO " +
					"GROUP BY " +
					" PBANDI_V_PAGAM_PROG_DOC_SPESA.ID_PAGAMENTO," +
					" PBANDI_V_PAGAM_PROG_DOC_SPESA.IP," +
					" pagamento.DESC_BREVE_MODALITA_PAGAMENTO," +
					" pagamento.ID_MODALITA_PAGAMENTO, " +
					" pagamento.DESC_MODALITA_PAGAMENTO, " +
					" pagamento.isUsedDichiarazioni, " +
					" pagamento.ID_CAUSALE_PAGAMENTO, " +
					" pagamento.RIF_PAGAMENTO, " +
					" PBANDI_V_PAGAM_PROG_DOC_SPESA.IQ," +
					" PBANDI_V_PAGAM_PROG_DOC_SPESA.DELTA_I " );
			if(!isNull(dataPagamento))
				sqlSelect.append(", pagamento."+dataPagamento);
			sqlSelect.append(", pagamento.ora_rowscn ");
			
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			
			
			setOrderBy(sqlSelect, "pagamento."+SYSTEM_DATA_DI_INSERIMENTO, true);
			
			result = query(sqlSelect.toString(), params, new PagamentoRowMapper());
			
		return result;
	}
	
	/**
	 * Ritorna la tipologia del doc di spesa dato l'idDocumento
	 * @param idDocumentoDiSpesa
	 * @return
	 */
	public String getTipologiaDocumentoDiSpesa(Long idDocumentoDiSpesa){
		
		String result = null;
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			
			StringBuilder sqlSelect = new StringBuilder("select PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA");
			sqlSelect.append(" from PBANDI_D_TIPO_DOCUMENTO_SPESA, PBANDI_T_DOCUMENTO_DI_SPESA");
			sqlSelect.append(" where PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa and");
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_TIPO_DOCUMENTO_SPESA = PBANDI_D_TIPO_DOCUMENTO_SPESA.ID_TIPO_DOCUMENTO_SPESA");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			result = (String)getNamedJdbcTemplate().queryForObject(sqlSelect.toString(), params, String.class);
			
		
		return result;
	}
	
	
	
	public List<PagamentoVoceDiSpesaVO> findPagamentiVociDiSpesa(Long idDocumentoDiSpesa, Long idPagamento){
		
		getLogger().begin();
		java.util.List<PagamentoVoceDiSpesaVO> result = new ArrayList<PagamentoVoceDiSpesaVO>();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			StringBuilder tables = (new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA," +
													  "PBANDI_T_QUOTA_PARTE_DOC_SPESA," +
													  "PBANDI_T_RIGO_CONTO_ECONOMICO,"+
													  "PBANDI_D_VOCE_DI_SPESA," +
													  "PBANDI_D_VOCE_DI_SPESA voceSpesaPadre,"+
													  "PBANDI_R_PAG_QUOT_PARTE_DOC_SP," +
			" (SELECT ID_QUOTA_PARTE_DOC_SPESA,SUM(PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO) QUIETANZATO"+ 
			"	FROM PBANDI_R_PAG_QUOT_PARTE_DOC_SP" +
			"   GROUP BY ID_QUOTA_PARTE_DOC_SPESA) SOMMA_IMPORTI_QUIETANZATI"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "+
					" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA as idQuotaParteDocSpesa" +
					" ,PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO as idRigoContoEconomico" +
					" ,PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA as idVoceDiSpesa" +
					" ,PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA  || nvl2(voceSpesaPadre.DESC_VOCE_DI_SPESA, ' - ' || voceSpesaPadre.DESC_VOCE_DI_SPESA, '') as voceDiSpesa" +
					//" ,voceSpesaPadre.DESC_VOCE_DI_SPESA || ' - ' || PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA as voceDiSpesa" +
					" ,PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA as importoRendicontato"));
			
			
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA").append("=:idDocumentoDiSpesa")); 
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_RIGO_CONTO_ECONOMICO = PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO"));
			conditionList.add(new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO.ID_VOCE_DI_SPESA = PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA"));
			conditionList.add(new StringBuilder("PBANDI_D_VOCE_DI_SPESA.id_voce_di_spesa_padre = voceSpesaPadre.id_voce_di_spesa (+)"));
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = SOMMA_IMPORTI_QUIETANZATI.ID_QUOTA_PARTE_DOC_SPESA (+)")); 
			conditionList.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA =PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA (+)"));
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
			
			if (idPagamento!=null && idPagamento > 0L) {
				StringBuilder joinPagamentoRigoContoEconomico = new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO (+) ").append("=:idPagamento");
				conditionList.add(joinPagamentoRigoContoEconomico);
				params.addValue("idPagamento", idPagamento, Types.BIGINT);
				
				sqlSelect.append(",PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO as idPagamento");
				sqlSelect.append(",nvl(SOMMA_IMPORTI_QUIETANZATI.QUIETANZATO - PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO,0) as importoQuietanzato ");
				sqlSelect.append(",nvl(PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO,0) as nuovoPagamentoVoceDiSpesa");
				
			}else{
				sqlSelect.append(",nvl(SOMMA_IMPORTI_QUIETANZATI.QUIETANZATO,0) as importoQuietanzato ");
				sqlSelect.append(",'0' as nuovoPagamentoVoceDiSpesa");
			}
             
			sqlSelect.append(" FROM ").append(tables);
			
			setWhereClause(conditionList, sqlSelect, tables);
			setOrderBy(sqlSelect, "PBANDI_T_RIGO_CONTO_ECONOMICO."+SYSTEM_DATA_DI_INSERIMENTO, true);
			result = query(sqlSelect.toString(), params, new PagamentoVoceDiSpesaRowMapper());
			getLogger().debug(" pagamenti - voci di spesa: " + result.size());
		
		}
		finally{
			 getLogger().end();
		}
		return result;
	}
	
	/**
	 * Cerca le voci di spesa nella modifica del pagamento
	 * @param idDocumentoDiSpesa
	 * @param idPagamento
	 * @return
	 */
	public List<PagamentoVoceDiSpesaVO> findVociDiSpesaByIdDocumentoByIdPagamento(Long idDocumentoDiSpesa, Long idPagamento){
		
		getLogger().begin();
		java.util.List<PagamentoVoceDiSpesaVO> result = new ArrayList<PagamentoVoceDiSpesaVO>();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder();
			
			sqlSelect.append("select ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA as idQuotaParteDocSpesa, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA as idVoceDiSpesa, ");
			sqlSelect.append(" nvl2(voceSpesaPadre.DESC_VOCE_DI_SPESA, voceSpesaPadre.DESC_VOCE_DI_SPESA || ': ', '') || PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA  as voceDiSpesa, ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA as importoRendicontato, ");
			sqlSelect.append(" SUM(pqpA.IMPORTO_QUIETANZATO) as totaleAltriPagamenti, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO as importoVoceDiSpesaCorrente ");
			sqlSelect.append(" from ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA, ");
			sqlSelect.append(" PBANDI_T_RIGO_CONTO_ECONOMICO, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA voceSpesaPadre, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP pqpA, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP ");
			sqlSelect.append(" where ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa ");
			sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_RIGO_CONTO_ECONOMICO = PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO ");
		    sqlSelect.append(" and PBANDI_T_RIGO_CONTO_ECONOMICO.ID_VOCE_DI_SPESA = PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA ");
		    sqlSelect.append(" and PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA_PADRE = voceSpesaPadre.ID_VOCE_DI_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = pqpA.ID_QUOTA_PARTE_DOC_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO(+) = :idPagamento ");
		    sqlSelect.append(" and pqpA.ID_PAGAMENTO (+) <> :idPagamento ");
			sqlSelect.append(" group by ");
		    sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA, ");
		    sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA, ");
		    sqlSelect.append(" nvl2(voceSpesaPadre.DESC_VOCE_DI_SPESA, voceSpesaPadre.DESC_VOCE_DI_SPESA || ': ', '') || PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA, ");
		    sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA,  ");
		    sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO ");
			sqlSelect.append(" order by 3, 2 ");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			result = query(sqlSelect.toString(), params, new PagamentoVoceDiSpesaRowMapper());
			getLogger().debug(" pagamenti - voci di spesa: " + result.size());
		
		}
		finally{
			 getLogger().end();
		}
		return result;
	}

	
  /**
	 * Cerca le voci di spesa nella modifica del pagamento
	 * @param idDocumentoDiSpesa
	 * @param idPagamento
	 * @return
	 */
	public List<PagamentoVoceDiSpesaVO> findVociDiSpesaByIdDocumentoIdPagamentoIdProgetto(Long idDocumentoDiSpesa, Long idPagamento, Long idProgetto){
		
		getLogger().begin();
		java.util.List<PagamentoVoceDiSpesaVO> result = new ArrayList<PagamentoVoceDiSpesaVO>();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder();
			
			sqlSelect.append("select ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA as idQuotaParteDocSpesa, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA as idVoceDiSpesa, ");
			sqlSelect.append(" nvl2(voceSpesaPadre.DESC_VOCE_DI_SPESA, voceSpesaPadre.DESC_VOCE_DI_SPESA || ': ', '') || PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA  as voceDiSpesa, ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA as importoRendicontato, ");
			sqlSelect.append(" SUM(pqpA.IMPORTO_QUIETANZATO) as totaleAltriPagamenti, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO as importoVoceDiSpesaCorrente, ");
			sqlSelect.append(" PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO as idRigoContoEconomico ");
			sqlSelect.append(" from ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA, ");
			sqlSelect.append(" PBANDI_T_RIGO_CONTO_ECONOMICO, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA, ");
			sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA voceSpesaPadre, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP pqpA, ");
			sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP ");
			sqlSelect.append(" where ");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa ");
			sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_RIGO_CONTO_ECONOMICO = PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO ");
		    sqlSelect.append(" and PBANDI_T_RIGO_CONTO_ECONOMICO.ID_VOCE_DI_SPESA = PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA ");
		    sqlSelect.append(" and PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA_PADRE = voceSpesaPadre.ID_VOCE_DI_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA = pqpA.ID_QUOTA_PARTE_DOC_SPESA(+) ");
		    sqlSelect.append(" and PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO = :idProgetto ");
		    sqlSelect.append(" and PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO(+) = :idPagamento ");
		    sqlSelect.append(" and pqpA.ID_PAGAMENTO (+) <> :idPagamento ");
			sqlSelect.append(" group by ");
		    sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA, ");
		    sqlSelect.append(" PBANDI_D_VOCE_DI_SPESA.ID_VOCE_DI_SPESA, ");
		    sqlSelect.append(" nvl2(voceSpesaPadre.DESC_VOCE_DI_SPESA, voceSpesaPadre.DESC_VOCE_DI_SPESA || ': ', '') || PBANDI_D_VOCE_DI_SPESA.DESC_VOCE_DI_SPESA, ");
		    sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA,  ");
		    sqlSelect.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO, ");
		    sqlSelect.append(" PBANDI_T_RIGO_CONTO_ECONOMICO.ID_RIGO_CONTO_ECONOMICO ");
			sqlSelect.append(" order by 3, 2 ");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			result = query(sqlSelect.toString(), params, new PagamentoVoceDiSpesaRowMapper());
			getLogger().debug(" pagamenti - voci di spesa: " + result.size());
		
		}
		finally{
			 getLogger().end();
		}
		return result;
	}
	
	
	public PagamentoQuotePartiVO findPagamento(Long idDocumentoDiSpesa, Long idPagamento,  Long idProgetto, String dataPagamento ){
		getLogger().begin();
		
		PagamentoQuotePartiVO result = new PagamentoQuotePartiVO();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder("PBANDI_T_PAGAMENTO," +
					"PBANDI_D_MODALITA_PAGAMENTO," +
					"PBANDI_R_PAGAMENTO_DOC_SPESA," +
					"PBANDI_T_DOCUMENTO_DI_SPESA," +
// PBANDI-2314					"PBANDI_D_STATO_VALIDAZ_SPESA," +
					"PBANDI_R_DOC_SPESA_PROGETTO"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "+
					" PBANDI_T_PAGAMENTO.id_pagamento as idPagamento" +
					" ,PBANDI_T_PAGAMENTO.IMPORTO_PAGAMENTO as importoPagamento" +
					" ,PBANDI_T_PAGAMENTO."+dataPagamento+" as dtPagamento" +
					" ,PBANDI_D_MODALITA_PAGAMENTO.ID_MODALITA_PAGAMENTO as idModalitaPagamento" + 
					" ,PBANDI_D_MODALITA_PAGAMENTO.DESC_BREVE_MODALITA_PAGAMENTO as descBreveModalitaPagamento"));
// PBANDI-2314					" ,PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA as descBreveStatoValidazioneSpesa"));
					
			sqlSelect.append(" FROM ").append(tables);
													  
			
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA").append("=:idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder(" PBANDI_T_PAGAMENTO.ID_PAGAMENTO = :idPagamento"));
			conditionList.add(new StringBuilder(" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto "));
			conditionList.add(new StringBuilder(" PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa "));
			conditionList.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder(" PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento=PBANDI_T_PAGAMENTO.id_pagamento"));
			conditionList.add(new StringBuilder(" PBANDI_T_PAGAMENTO.id_modalita_pagamento=PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento"));
// PBANDI-2314			conditionList.add(new StringBuilder(" PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA"));

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idPagamento", idPagamento, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			result = (PagamentoQuotePartiVO)queryForObject(sqlSelect.toString(), params, new PagamentoRowMapper());

		}finally{
			getLogger().end();
		}
		
		return result;
	}
	
	
	public java.util.List<PagamentoQuotePartiVO> findPagamentiQuietanzatiNonAssociati(Long idDocumentoDiSpesa, Long idProgetto){
		getLogger().begin();
		
		java.util.List<PagamentoQuotePartiVO> result = new ArrayList<PagamentoQuotePartiVO>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder("PBANDI_T_PAGAMENTO," +
					"PBANDI_D_MODALITA_PAGAMENTO," +
					"PBANDI_R_PAGAMENTO_DOC_SPESA," +
					"PBANDI_T_DOCUMENTO_DI_SPESA," +
// PBANDI-2314					"PBANDI_D_STATO_VALIDAZ_SPESA," +
					"PBANDI_R_DOC_SPESA_PROGETTO"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "+
					"  PBANDI_T_PAGAMENTO.id_pagamento as idPagamento" +
					" ,PBANDI_T_PAGAMENTO.IMPORTO_PAGAMENTO as importoPagamento" +
					" ,PBANDI_T_PAGAMENTO.DT_PAGAMENTO as dtPagamento" +
					" ,PBANDI_D_MODALITA_PAGAMENTO.ID_MODALITA_PAGAMENTO as idModalitaPagamento" + 
					" ,PBANDI_D_MODALITA_PAGAMENTO.DESC_BREVE_MODALITA_PAGAMENTO as descBreveModalitaPagamento" +
// PBANDI-2314					" ,PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA as descBreveStatoValidazioneSpesa" +
// PBANDI-2314					" ,PBANDI_D_STATO_VALIDAZ_SPESA.DESC_STATO_VALIDAZIONE_SPESA as descStatoValidazioneSpesa" +
					" ,PBANDI_T_DOCUMENTO_DI_SPESA.NUMERO_DOCUMENTO  as numeroDocumentoDiSpesa" +
					" ,PBANDI_T_DOCUMENTO_DI_SPESA.DT_EMISSIONE_DOCUMENTO  as dtEmissioneDocumentoDiSpesa" +
					" ,PBANDI_T_PAGAMENTO.DT_VALUTA as dtValuta"));
					
			sqlSelect.append(" FROM ").append(tables);
													  
			
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA").append("=:idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
			conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento = PBANDI_T_PAGAMENTO.id_pagamento"));
			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_modalita_pagamento=PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento"));
// PBANDI-2314			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA"));
			conditionList.add(new StringBuilder("PBANDI_D_MODALITA_PAGAMENTO.DESC_BREVE_MODALITA_PAGAMENTO='MAN' "));
			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.ID_EROGAZIONE IS NULL "));
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto,  Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			result = query(sqlSelect.toString(), params, new PagamentoRowMapper());

		}finally{
			getLogger().end();
		}
		
		return result;
	}

	
	
	public java.util.List<PagamentoQuotePartiVO> findPagamentiAssociati(Long idDocumentoDiSpesa, Long idProgetto){
		getLogger().begin();
		
		java.util.List<PagamentoQuotePartiVO> result = new ArrayList<PagamentoQuotePartiVO>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder("PBANDI_T_PAGAMENTO," +
					"PBANDI_D_MODALITA_PAGAMENTO," +
					"PBANDI_R_PAGAMENTO_DOC_SPESA," +
					"PBANDI_T_DOCUMENTO_DI_SPESA," +
// PBANDI-2314					"PBANDI_D_STATO_VALIDAZ_SPESA, " +
					"PBANDI_R_DOC_SPESA_PROGETTO"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "+
					" PBANDI_T_PAGAMENTO.id_pagamento as idPagamento" +
					" ,PBANDI_T_PAGAMENTO.IMPORTO_PAGAMENTO as importoPagamento" +
					" ,PBANDI_T_PAGAMENTO.DT_PAGAMENTO as dtPagamento" +
					" ,PBANDI_D_MODALITA_PAGAMENTO.ID_MODALITA_PAGAMENTO as idModalitaPagamento" + 
					" ,PBANDI_D_MODALITA_PAGAMENTO.DESC_BREVE_MODALITA_PAGAMENTO as descBreveModalitaPagamento" ));
// PBANDI-2314					" ,PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA as descBreveStatoValidazioneSpesa"));
					
			sqlSelect.append(" FROM ").append(tables);
													  
			
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA").append("=:idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
			conditionList.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa=PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa"));
			conditionList.add(new StringBuilder("PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento=PBANDI_T_PAGAMENTO.id_pagamento"));
			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_modalita_pagamento=PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento"));
// PBANDI-2314			conditionList.add(new StringBuilder("PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA"));

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto,  Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			result = query(sqlSelect.toString(), params, new PagamentoRowMapper());

		}finally{
			getLogger().end();
		}
		
		return result;
	}
	
	
	public Double getImportoValidato(Long idProgetto,Long idPagamento){
		getLogger().begin();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect.append(" SUM(IMPORTO_VALIDATO) ");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_R_PaG_QUOT_PARTE_DOC_SP ");
			/*
			 * FIX PBANDI-2351: inizio 
			 */
			sqlSelect.append(" ,PBANDI_T_QUOTA_PARTE_DOC_SPESA ");
			/*
			 * FIX PBANDI-2351: fine 
			 */
			sqlSelect.append(" WHERE"); 
			sqlSelect.append(" PBANDI_R_PaG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO=:idPagamento ");
			/*
			 * FIX PBANDI-2351: inizio 
			 */
			sqlSelect.append(" AND PBANDI_R_PaG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA = PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA ");
			sqlSelect.append(" AND PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO=:idProgetto ");
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			/*
			 * FIX PBANDI-2351: fine 
			 */
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			return queryForDouble(sqlSelect.toString(), params);
			

						
		}finally{
			getLogger().end();
		}

	}
	
	public Double getImportoQuietanzato(Long idProgetto,Long idPagamento){
		getLogger().begin();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect.append(" SUM(IMPORTO_QUIETANZATO) ");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_R_PaG_QUOT_PARTE_DOC_SP ");
			/*
			 * FIX PBANDI-2351: inizio 
			 */
			sqlSelect.append(" ,PBANDI_T_QUOTA_PARTE_DOC_SPESA ");
			/*
			 * FIX PBANDI-2351: fine 
			 */
			
			sqlSelect.append(" WHERE"); 
			sqlSelect.append(" PBANDI_R_PaG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO=:idPagamento ");
			
			
			/*
			 * FIX PBANDI-2351: inizio 
			 */
			sqlSelect.append(" AND PBANDI_R_PaG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA = PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_QUOTA_PARTE_DOC_SPESA ");
			sqlSelect.append(" AND PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO=:idProgetto ");
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			/*
			 * FIX PBANDI-2351: fine 
			 */
			
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			return queryForDouble(sqlSelect.toString(), params);
			

						
		}finally{
			getLogger().end();
		}

	}
	
	public java.util.List<Long> findIdPagamentiAssociati(List<Long> idDocumentiDiSpesa){
		getLogger().begin();
		
		java.util.List<Long> result = new ArrayList<Long>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder(
					"PBANDI_R_PAGAMENTO_DOC_SPESA"));

			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "
					+ " ID_PAGAMENTO "));

			StringBuilder listIdDoc = new StringBuilder();
			for (Long idDoc : idDocumentiDiSpesa) {
				listIdDoc.append(idDoc + ",");
			}

			conditionList.add((new StringBuilder()).append(
					"ID_DOCUMENTO_DI_SPESA  IN ( ").append(
					listIdDoc.substring(0, listIdDoc.lastIndexOf(","))).append(
					" )"));

			sqlSelect.append(" FROM ").append(tables);

			setWhereClause(conditionList, sqlSelect, tables);

			List <Map>list= queryForList(sqlSelect.toString(), params);
			
			for(Map map:list){
				if(map.containsKey("ID_PAGAMENTO")){
					BigDecimal id=((BigDecimal)map.get("ID_PAGAMENTO"));
					result.add(NumberUtil.toLong(id));
				}
				
			}
		}finally{
			getLogger().end();
		}
		
		return result;
	}
	
	public java.util.List<Long> findIdPagamentiAssociati(Long idDocumentoDiSpesa){
		getLogger().begin();
		
		java.util.List<Long> result = new ArrayList<Long>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder(
					"PBANDI_R_PAGAMENTO_DOC_SPESA"));

			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "
					+ " ID_PAGAMENTO "));


			conditionList.add((new StringBuilder()).append(
					"ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa  "));


			sqlSelect.append(" FROM ").append(tables);
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
			
			setWhereClause(conditionList, sqlSelect, tables);

			List <Map>list= queryForList(sqlSelect.toString(), params);
			
			for(Map map:list){
				if(map.containsKey("ID_PAGAMENTO")){
					BigDecimal id=((BigDecimal)map.get("ID_PAGAMENTO"));
					result.add(NumberUtil.toLong(id));
				}
				
			}
		}finally{
			getLogger().end();
		}
		
		return result;
	}
	
	
	
	public boolean esistePagamentoPerVoceDiSpesa(Long idPagamento, Long idQuotaParteDocSpesa){
		
		getLogger().begin();
		
		boolean result = false;
		try {
			
			String sqlSelect = "select count(*) from  PBANDI_R_PAG_QUOT_PARTE_DOC_SP " + 
							   "where PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_QUOTA_PARTE_DOC_SPESA = :idQuotaParteDocSpesa and "+
							   "PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO = :idPagamento";
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idQuotaParteDocSpesa", idQuotaParteDocSpesa);
			params.addValue("idPagamento", idPagamento);
			
			//int count = getNamedJdbcTemplate().queryForInt(sqlSelect.toString(), params);
			int count = getNamedJdbcTemplate().queryForObject(sqlSelect.toString(), params, Integer.class);
			
			if(count > 0){
				result = true;
			} 
			
		}finally{
			getLogger().end();
		}
		return result;
	}
	
	
	public boolean esistonoVociDiSpesaByDocumento(Long idDocumentoDiSpesa){
		boolean result = false;
			
			String sqlSelect = "select distinct count(*) from PBANDI_T_DOCUMENTO_DI_SPESA,PBANDI_T_QUOTA_PARTE_DOC_SPESA " + 
							   "where PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa and "+
							   "PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA";
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);

//			int count = getNamedJdbcTemplate().queryForInt(sqlSelect.toString(), params);
			int count = getNamedJdbcTemplate().queryForObject(sqlSelect.toString(), params, Integer.class);
			
			if(count > 0){
				result = true;
			} 
			
		 
		return result;
	}
	
	
	public boolean esistonoVociDiSpesaByDocumentoProgetto(Long idDocumentoDiSpesa, Long idProgetto){
	 
		boolean result = false;
			
			String sqlSelect = "select distinct count(*) from PBANDI_T_DOCUMENTO_DI_SPESA,PBANDI_T_QUOTA_PARTE_DOC_SPESA " + 
							   "where PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa and "+
							   "PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA and " +
							   "PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO = :idProgetto";
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
			params.addValue("idProgetto", idProgetto);

			//int count = getNamedJdbcTemplate().queryForInt(sqlSelect.toString(), params);
			int count = getNamedJdbcTemplate().queryForObject(sqlSelect.toString(), params, Integer.class);
			
			if(count > 0){
				result = true;
			} 
			
		 
		return result;
	}
	
	public PDocumentoDiSpesaVO getInfoPagamentoDocumentoDiSpesa(Long idDocumentoDiSpesa){
		
		PDocumentoDiSpesaVO documentoVO = new PDocumentoDiSpesaVO();
	 
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA DS," +
					"PBANDI_T_PAGAMENTO P," +
					"PBANDI_R_PAGAMENTO_DOC_SPESA PDS," +
					"PBANDI_D_TIPO_DOCUMENTO_SPESA TDS," + 
					"PBANDI_T_DOCUMENTO_DI_SPESA DS_RIF"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT"+
					" DS.ID_TIPO_DOCUMENTO_SPESA as idTipoDocDiSpesa" +
					" ,DS.DT_EMISSIONE_DOCUMENTO as dataEmissione" +
					" ,sum(P.IMPORTO_PAGAMENTO) as sommaPagamenti" +
					" ,DS.IMPORTO_TOTALE_DOCUMENTO - DECODE(DS.ID_TIPO_DOCUMENTO_SPESA,4,DS_RIF.IMPORTO_TOTALE_DOCUMENTO,0) as importoTotaleNetto"));
					
			sqlSelect.append(" FROM ").append(tables);
													  
			
			conditionList.add(new StringBuilder("DS.ID_DOCUMENTO_DI_SPESA").append("=:idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder("DS.ID_DOCUMENTO_DI_SPESA = PDS.ID_DOCUMENTO_DI_SPESA (+)"));
			conditionList.add(new StringBuilder("PDS.ID_PAGAMENTO  = P.ID_PAGAMENTO (+)"));
			conditionList.add(new StringBuilder("DS.ID_TIPO_DOCUMENTO_SPESA = TDS.ID_TIPO_DOCUMENTO_SPESA"));
			conditionList.add(new StringBuilder("DS.ID_DOC_RIFERIMENTO = DS_RIF.ID_DOCUMENTO_DI_SPESA(+)"));

			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa);
			
			setWhereClause(conditionList, sqlSelect, tables);
			
			StringBuilder sqlRaggruppamento = new StringBuilder(" group by" +
					 " DS.DT_EMISSIONE_DOCUMENTO," +
					 " DS.ID_DOC_RIFERIMENTO," +
					 " TDS.DESC_BREVE_TIPO_DOC_SPESA,"+
					 " DS_RIF.IMPORTO_TOTALE_DOCUMENTO,"+
					 " DS.ID_TIPO_DOCUMENTO_SPESA," +
					 " DS.IMPORTO_TOTALE_DOCUMENTO,"+
					 " DS_RIF.ID_TIPO_DOCUMENTO_SPESA");
			
			sqlSelect.append(sqlRaggruppamento);
			documentoVO = (PDocumentoDiSpesaVO)queryForObject(sqlSelect.toString(), params, new PDocumentoDiSpesaRowMapper());
		
		 
		
		return documentoVO;
	}
	
	
	/**
	 * TODO: Gestire id_soggetto, in questo momento � simulato
	 * Chiedere con quale dato valorizzare la colonna
	 * Gestione id_stato_validazione_spesa 
	 * 
	 */
	public Long inserisciPagamento(Long idModalitaPagamento,java.sql.Date dataPagamento,Double importoTotalePagamento, 
			Long idUtente, String descStatoValidazioneSpesa, String tipoDataPagamento,
			Long idCausalePagamento,String rifPagamento
			) 
	{

		StringBuffer sqlInsert= new StringBuffer("insert into PBANDI_T_PAGAMENTO ");
		sqlInsert.append("(ID_MODALITA_PAGAMENTO")
				  .append(",").append(tipoDataPagamento)
				  .append(",IMPORTO_PAGAMENTO")
				  .append(",ID_PAGAMENTO");
		  		 
		if(idCausalePagamento!=null)
			sqlInsert .append(",ID_CAUSALE_PAGAMENTO");
		if(rifPagamento!=null)
			sqlInsert .append(",RIF_PAGAMENTO");
		
		sqlInsert .append(",ID_UTENTE_INS)");
		
		sqlInsert.append("  VALUES (:idModalitaPagamento,:dataPagamento, :importoTotalePagamento, :idPagamento");
		if(idCausalePagamento!=null)
			sqlInsert .append(",:idCausalePagamento");
		if(rifPagamento!=null)
			sqlInsert .append(",:rifPagamento");
		sqlInsert.append(",:idUtenteIns)");

				 
		MapSqlParameterSource params = new MapSqlParameterSource();
		 
		long idNuovoPagamento = getSeqpbandipagamenti().nextLongValue();
		params.addValue("idModalitaPagamento", idModalitaPagamento, Types.BIGINT);
		params.addValue("dataPagamento", dataPagamento, Types.TIMESTAMP);
		params.addValue("importoTotalePagamento", importoTotalePagamento, Types.DOUBLE);
		params.addValue("idPagamento", idNuovoPagamento,Types.BIGINT);
		params.addValue("idUtenteIns", idUtente, Types.BIGINT);
		
		if(idCausalePagamento!=null)
			params.addValue("idCausalePagamento", idCausalePagamento, Types.BIGINT);
		if(rifPagamento!=null)
			params.addValue("rifPagamento", rifPagamento, Types.VARCHAR);
		
		inserisci(sqlInsert.toString(), params);
		return idNuovoPagamento;
	}


	/**
	 * 
	 * @param idPagamento
	 * @param idDocumentoDiSpesa
	 * @param idUtente
	 */
	public void inserisciPagamentoRDocSpesa(Long idPagamento,Long idDocumentoDiSpesa, Long idUtente) 
	{
		getLogger().begin();

		StringBuffer sqlInsert= new StringBuffer("insert into PBANDI_R_PAGAMENTO_DOC_SPESA ");
		sqlInsert.append("(ID_PAGAMENTO")
				  .append(",ID_DOCUMENTO_DI_SPESA")
//JIRA23-14		  .append(",ID_PROGETTO")
				  .append(",ID_UTENTE_INS)");
		
		sqlInsert.append("values(:idPagamento")
		 		 .append(",:idDocumentoDiSpesa")
//JIRA23-14	     .append(",:idProgetto")
		 		 .append(",:idUtenteIns)");
		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idPagamento", idPagamento, Types.BIGINT);
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
//JIRA23-14		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		params.addValue("idUtenteIns", idUtente, Types.BIGINT);
	
		inserisci(sqlInsert.toString(), params);
		getLogger().end();
	}

	
	public void modificaPagamento(Long idPagamento, Long idModalitaPagamento,java.sql.Date dataPagamento,
			Double importoTotalePagamento, Long idUtente, String tipoDataPagamento,
			Long idCausalePagamento,String rifPagamento) 
	{
			
			StringBuffer sqlUpdate= new StringBuffer("update PBANDI_T_PAGAMENTO set ");
			sqlUpdate.append("ID_MODALITA_PAGAMENTO").append("=:idModalitaPagamento")
					  .append(",").append(tipoDataPagamento).append("=:dataPagamento")
					  .append(",IMPORTO_PAGAMENTO").append("=:importoTotalePagamento")
			  		  .append(",ID_UTENTE_INS").append("=:idUtenteIns");
			if(idCausalePagamento!=null)
				sqlUpdate .append(",ID_CAUSALE_PAGAMENTO=:idCausalePagamento");
			if(rifPagamento!=null)
				sqlUpdate .append(",RIF_PAGAMENTO=:rifPagamento");
			sqlUpdate .append(" where ID_PAGAMENTO=:idPagamento");
			
					 
			MapSqlParameterSource params = new MapSqlParameterSource();
			 

			params.addValue("idModalitaPagamento", idModalitaPagamento, Types.BIGINT);
			params.addValue("dataPagamento", dataPagamento, Types.TIMESTAMP);
			params.addValue("importoTotalePagamento", importoTotalePagamento, Types.DOUBLE);
			params.addValue("idPagamento", idPagamento,Types.BIGINT);
			params.addValue("idUtenteIns", idUtente, Types.BIGINT);
			if(idCausalePagamento!=null)
				params.addValue("idCausalePagamento", idCausalePagamento, Types.BIGINT);
			if(rifPagamento!=null)
				params.addValue("rifPagamento", rifPagamento, Types.VARCHAR);
			
			
			modifica(sqlUpdate.toString(), params);
		 
		
	}

		
	
	public Double getSommaPagamentiDocSpesa(Long idDocumentoDiSpesa){
		getLogger().begin();
		Double sommaPagamentiDocSpesa=0D;
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect.append(" sum(PBANDI_T_PAGAMENTO.IMPORTO_PAGAMENTO)");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA, PBANDI_T_PAGAMENTO, PBANDI_R_PAGAMENTO_DOC_SPESA");
			sqlSelect.append(" WHERE"); 
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa and");
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA =  PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA AND"); 
			sqlSelect.append(" PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO = PBANDI_T_PAGAMENTO.ID_PAGAMENTO");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			
			sommaPagamentiDocSpesa  = queryForDouble(sqlSelect.toString(), params);
			
			
		} finally {
			getLogger().end();
		}
		return sommaPagamentiDocSpesa;
	}
	
	
	public int getDocumentiDiSpesaByPagamento(Long idPagamento) {
		getLogger().begin();
		
		int documentiDiSpesaByPagamenti=0;
		
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlSelect = new StringBuilder("select count(*) ");
			sqlSelect.append(" from PBANDI_T_PAGAMENTO, PBANDI_R_PAGAMENTO_DOC_SPESA ");
			sqlSelect.append(" WHERE PBANDI_T_PAGAMENTO.ID_PAGAMENTO = :idPagamento");
			sqlSelect.append(" and PBANDI_T_PAGAMENTO.ID_PAGAMENTO = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO");
			
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			
			documentiDiSpesaByPagamenti = (Integer)getNamedJdbcTemplate().queryForObject(sqlSelect.toString(), params, Integer.class);
			
			
		}finally {
			getLogger().end();
		}
		
		return documentiDiSpesaByPagamenti;
	}
	
	
	public void cancellaPagamento(Long idPagamento){
		getLogger().begin();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_T_PAGAMENTO where ID_PAGAMENTO =:idPagamento");
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			elimina(sqlDelete.toString(), params);
			
		} finally{
			getLogger().end();
		}
	}

	public void cancellaRPagamentoDocDiSpesa(Long idPagamento){
		getLogger().begin();
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_R_PAGAMENTO_DOC_SPESA where ID_PAGAMENTO =:idPagamento");
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			elimina(sqlDelete.toString(), params);
			
		} finally{
			getLogger().end();
		}
	}
	
	public void cancellaRPagamentoDichDiSpesa(Long idPagamento){
		getLogger().begin();
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_R_PAGAMENTO_DICH_SPESA where ID_PAGAMENTO =:idPagamento");
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			elimina(sqlDelete.toString(), params);
			
		} finally{
			getLogger().end();
		}
	}
	
	public void cancellaPagamentiVociDiSpesa(Long idPagamento){
		
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_R_PAG_QUOT_PARTE_DOC_SP where ID_PAGAMENTO =:idPagamento");
			params.addValue("idPagamento", idPagamento, Types.BIGINT);
			elimina(sqlDelete.toString(), params);
			
		}finally{
			getLogger().end();
		}
	}

	
	/**
	 * 
	 */
	private class PagamentoRowMapper implements RowMapper<PagamentoQuotePartiVO>{

		public PagamentoQuotePartiVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			 PagamentoQuotePartiVO vo=(PagamentoQuotePartiVO) beanMapper.toBean(rs,
					PagamentoQuotePartiVO.class);
			 return vo;
		}
	}
	
	
	private class PDocumentoDiSpesaRowMapper implements RowMapper<PDocumentoDiSpesaVO>{

		public PDocumentoDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			PDocumentoDiSpesaVO vo=(PDocumentoDiSpesaVO) beanMapper.toBean(rs,
					PDocumentoDiSpesaVO.class);
			 return vo;
		}
	}	
	
	private class PagamentoVoceDiSpesaRowMapper implements RowMapper<PagamentoVoceDiSpesaVO>{

		public PagamentoVoceDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			PagamentoVoceDiSpesaVO vo=(PagamentoVoceDiSpesaVO) beanMapper.toBean(rs,
					PagamentoVoceDiSpesaVO.class);
			 return vo;
		}
	}

	
	
	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
	public List<Long> findIdPagamentiByStato(Long idDichiarazioneDiSpesa,
			Long idStatoValidazioneSpesa) {
		getLogger().begin();
		
		java.util.List<Long> result = new ArrayList<Long>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

			StringBuilder tables = (new StringBuilder(
					"PBANDI_R_PAGAMENTO_DICH_SPESA,PBANDI_T_PAGAMENTO"));

			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT "
					+ " PBANDI_T_PAGAMENTO.ID_PAGAMENTO "));

			conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA=:idDichiarazioneDiSpesa"));
			params.addValue("idDichiarazioneDiSpesa",idDichiarazioneDiSpesa, Types.BIGINT);
			conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO=PBANDI_T_PAGAMENTO.ID_PAGAMENTO"));
			
			conditionList.add((new StringBuilder()).append(
			"PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA =:idStatoValidazioneSpesa"));
			params.addValue("idStatoValidazioneSpesa",idStatoValidazioneSpesa, Types.BIGINT);
			
			sqlSelect.append(" FROM ").append(tables);

			setWhereClause(conditionList, sqlSelect, tables);

			List <Map>list= queryForList(sqlSelect.toString(), params);
			
			for(Map map:list){
				if(map.containsKey("ID_PAGAMENTO")){
					BigDecimal id=((BigDecimal)map.get("ID_PAGAMENTO"));
					result.add(NumberUtil.toLong(id));
				}
				
			}
		}finally{
			getLogger().end();
		}
		
		return result;
	}
	*/

	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
	public int modificaStatoValidazione(Long idPagamento, Long idStatoValidazioneSpesa) {
		StringBuffer sqlUpdate= new StringBuffer("update PBANDI_T_PAGAMENTO set ")
					.append(" ID_STATO_VALIDAZIONE_SPESA=:idStatoValidazioneSpesa")
		 		    .append(" where ID_PAGAMENTO=:idPagamento");
		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idStatoValidazioneSpesa",idStatoValidazioneSpesa, Types.BIGINT);
		params.addValue("idPagamento", idPagamento, Types.BIGINT);
		int pagamentiValidati=modificaConRitorno(sqlUpdate.toString(), params);
		getLogger().debug("record modificati? "+pagamentiValidati);
		return pagamentiValidati;
	}
	*/
	
	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
	public void modificaStatoValidazione(List<Long> idPagamentiDaRespingere,
			Long idStatoValidazioneSpesa) {
		getLogger().begin();
		StringBuffer sqlUpdate= new StringBuffer("update PBANDI_T_PAGAMENTO set ")
					.append(" ID_STATO_VALIDAZIONE_SPESA=:idStatoValidazioneSpesa")
		 		    .append(" where ");
		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idStatoValidazioneSpesa",idStatoValidazioneSpesa, Types.BIGINT);

		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

		
		StringBuilder listIdPagamentiDaRespingere = new StringBuilder();
		for (Long idPagamentoDaRespingere : idPagamentiDaRespingere) {
			listIdPagamentiDaRespingere.append(idPagamentoDaRespingere + ",");
		}
		String condition=listIdPagamentiDaRespingere.substring(0, listIdPagamentiDaRespingere.length()-1);
		condition="ID_PAGAMENTO IN( "+condition+")";
		
		sqlUpdate.append(condition);
		Boolean b=modifica(sqlUpdate.toString(), params);
		getLogger().debug("record modificati? "+b);
		getLogger().end();
		
	}
	*/


	public void cancellaPagamentiVociDiSpesa(List<Long> idPagamenti){
		getLogger().begin();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			StringBuilder sqlDelete = new StringBuilder("delete from PBANDI_R_PAG_QUOT_PARTE_DOC_SP where ");
			
			StringBuilder listIdPagamenti = new StringBuilder();
			
			for (Long idPagamento: idPagamenti) {
				listIdPagamenti.append(idPagamento + ",");
			}
			String condition=listIdPagamenti.substring(0, listIdPagamenti.length()-1);
			
			condition="ID_PAGAMENTO IN( "+condition+")";
			
			sqlDelete.append(condition);
			
			Boolean b=elimina(sqlDelete.toString(), params);
			
			getLogger().info("cancellate associazioni pagamenti-voci di spesa : "+b);
			
		}finally{
			getLogger().end();
		}
	}

	
	
	

	public Double getSommaImportiValidati(Long idDocumentoDiSpesa){
		getLogger().begin();
		Double sommaImportiValidati=0D;
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect.append(" sum(PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_VALIDATO)");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA, PBANDI_R_PAG_QUOT_PARTE_DOC_SP, PBANDI_R_PAGAMENTO_DOC_SPESA");
			sqlSelect.append(" WHERE"); 
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa and");
			sqlSelect.append(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA =  PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA AND"); 
			sqlSelect.append(" PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.ID_PAGAMENTO");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			
			sommaImportiValidati= queryForDouble(sqlSelect.toString(), params);
			
		} finally {
			getLogger().end();
		}
		return sommaImportiValidati;
	}
	
	
	
	
	public Double getSommaImportiRendicontabili(Long idDocumentoDiSpesa){
		getLogger().begin();
		Double sommaImportiRendicontabili=0D;
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect = new StringBuilder("select");
			sqlSelect.append(" sum(PBANDI_T_QUOTA_PARTE_DOC_SPESA.IMPORTO_QUOTA_PARTE_DOC_SPESA)");
			sqlSelect.append(" FROM");
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA");
			sqlSelect.append(" WHERE"); 
			sqlSelect.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa ");
			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
			
			sommaImportiRendicontabili= queryForDouble(sqlSelect.toString(), params);
			
		} finally {
			getLogger().end();
		}
		return sommaImportiRendicontabili;
	}
	
	public List<Long> findIdPagamentiByStato(Long idDichiarazioneDiSpesa,
			List<String> statiAmmessiPagamento,
			List<Long> idDocumentiDiSpesa) {
		getLogger().begin();
		
		java.util.List<Long> result = new ArrayList<Long>();
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sqlSelect =new StringBuilder("");
			prepareSelectPagamentiPerValidaTutti(idDichiarazioneDiSpesa,
					statiAmmessiPagamento, idDocumentiDiSpesa, params,
					sqlSelect);

			List <Map>list= queryForList(sqlSelect.toString(), params);
			
			for(Map map:list){
				if(map.containsKey("ID_PAGAMENTO")){
					BigDecimal id=((BigDecimal)map.get("ID_PAGAMENTO"));
					result.add(NumberUtil.toLong(id));
				}
				
			}
		}finally{
			getLogger().end();
		}
		
		return result;
	}

	
	private void prepareSelectPagamentiPerValidaTutti(
			Long idDichiarazioneDiSpesa, List<String> statiAmmessiPagamento,
			List<Long> idDocumentiDiSpesa, MapSqlParameterSource params,
			StringBuilder sqlSelect) {
		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

		StringBuilder tables = (new StringBuilder(
				"PBANDI_R_PAGAMENTO_DICH_SPESA"));
		tables.append(",PBANDI_T_PAGAMENTO");
		tables.append(",PBANDI_R_PAGAMENTO_DOC_SPESA");

		sqlSelect.append("SELECT DISTINCT "
				+ " PBANDI_T_PAGAMENTO.ID_PAGAMENTO ");

		conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA=:idDichiarazioneDiSpesa"));
		params.addValue("idDichiarazioneDiSpesa",idDichiarazioneDiSpesa, Types.BIGINT);
		conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO=PBANDI_T_PAGAMENTO.ID_PAGAMENTO"));
		conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO=PBANDI_T_PAGAMENTO.ID_PAGAMENTO"));
		
		
		String condition="PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA IN(";
		for (Long idDocDiSpesa: idDocumentiDiSpesa) {
			condition+="'"+idDocDiSpesa.toString()+"',";	
		}
		
		condition=condition.substring(0,condition.length()-1);
		condition+=")";
		conditionList.add((new StringBuilder()).append(condition));

		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
		if(!isEmpty(statiAmmessiPagamento)){
			tables.append(",PBANDI_D_STATO_VALIDAZ_SPESA");
			String x="PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA in (";
			for (String statoPagamento : statiAmmessiPagamento) {
				x+="'"+statoPagamento+"',";	
			}
			x=x.substring(0,x.length()-1);
			x+=")";
			conditionList.add((new StringBuilder()).append(x));
			conditionList.add((new StringBuilder()).append("PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA=PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA"));
		}
		*/
		
		sqlSelect.append(" FROM ").append(tables);

		setWhereClause(conditionList, sqlSelect, tables);
	}
	
	public boolean controllaTotaliImportiAmmessiPerVociDiSpesa(
			List<Long> idPagamenti) {
	
		getLogger().begin();
		boolean isImportoMaxSuperato=false;
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();

			StringBuilder sqlSelect = new StringBuilder("");
			sqlSelect.append(
			" select "+
			" pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO as idRigoContoEconomico, "+
			" sum(IMPORTO_QUIETANZATO) as totaleImportoQuietanziato, "+
			" validatoCE.totaleImportoValidato as totaleImportoValidato, "+
			" pbandi_t_rigo_conto_economico.IMPORTO_AMMESSO_FINANZIAMENTO as importoAmmesso "+
			" from "+
			" pbandi_r_pag_quot_parte_doc_sp, "+
			" pbandi_t_rigo_conto_economico, "+
			" pbandi_t_quota_parte_doc_spesa, "+
			" (select  "+
			" PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO, "+
			" pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO, "+
			" nvl2(sum(IMPORTO_VALIDATO), sum(IMPORTO_VALIDATO), 0) as totaleImportoValidato "+
			" from "+
			" pbandi_r_pag_quot_parte_doc_sp, "+
			" PBANDI_R_PAGAMENTO_DOC_SPESA, "+
			" pbandi_t_rigo_conto_economico, "+
			" pbandi_t_quota_parte_doc_spesa, PBANDI_R_DOC_SPESA_PROGETTO "+
			" where "+
			" PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO = pbandi_r_pag_quot_parte_doc_sp.ID_PAGAMENTO "+
			" and PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA "+
			" and pbandi_r_pag_quot_parte_doc_sp.ID_QUOTA_PARTE_DOC_SPESA = pbandi_t_quota_parte_doc_spesa.ID_QUOTA_PARTE_DOC_SPESA "+
			" and pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO = pbandi_t_rigo_conto_economico.ID_RIGO_CONTO_ECONOMICO "+
			" group by "+
			" PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO, "+
			" pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO) validatoCE "+
			" where   ");
			//" id_pagamento in (1749,1750,1751) ";

			if (idPagamenti.size() < 1000)  {
				// Codice originale che crea 1 sola condizione ID_PAGAMENTO IN(...)		
				StringBuilder listIdPagamenti = new StringBuilder();
				for (Long idPagamento : idPagamenti) {
					listIdPagamenti.append(idPagamento + ",");
				}
				String condition=listIdPagamenti.substring(0, listIdPagamenti.length()-1);
				condition="ID_PAGAMENTO IN( "+condition+" )";
				sqlSelect.append(condition);
			} else {
				// Codice nuovo che divide in blocchi la condizione ID_PAGAMENTO IN(...)
				StringBuilder condizioneConPiuBlocchi = new StringBuilder();
				StringBuilder listIdPagamenti = new StringBuilder();
				int i = 0;
				for (Long idPagamento : idPagamenti) {
					listIdPagamenti.append(idPagamento + ",");
					i = i + 1;
					if (i == 999)  {
						// Crea una condizione ID_PAGAMENTO IN(...)
						String condition = listIdPagamenti.substring(0, listIdPagamenti.length()-1);
						condition = " ID_PAGAMENTO IN( " + condition + " ) OR";
						condizioneConPiuBlocchi.append(condition);
						// Resetta le variabili per un nuovo ciclo.
						listIdPagamenti = new StringBuilder();
						i = 0;
					}				
				}
				// Crea una condizione ID_PAGAMENTO IN(...)
				String condition = listIdPagamenti.substring(0, listIdPagamenti.length()-1);
				condition = " ID_PAGAMENTO IN( " + condition + " ) OR";
				condizioneConPiuBlocchi.append(condition);
				// Tolgo l'ltimo OR
				String condizioneFinale = "( " + condizioneConPiuBlocchi.substring(0, condizioneConPiuBlocchi.length() - 2) + " )";
				sqlSelect.append(condizioneFinale);				
			}
			
			//(select id_pagamento from pbandi_t_pagamento where id_pagamento in (1749,1750,1751))   "+
			sqlSelect.append(" and pbandi_r_pag_quot_parte_doc_sp.ID_QUOTA_PARTE_DOC_SPESA=pbandi_t_quota_parte_doc_spesa.ID_QUOTA_PARTE_DOC_SPESA "+
			" and pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO=pbandi_t_rigo_conto_economico.ID_RIGO_CONTO_ECONOMICO "+
			" and pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO=validatoCE.ID_RIGO_CONTO_ECONOMICO "+
			" and pbandi_t_quota_parte_doc_spesa.ID_PROGETTO=validatoCE.ID_PROGETTO "+
			" group by "+
			" pbandi_t_quota_parte_doc_spesa.ID_RIGO_CONTO_ECONOMICO, "+
			" pbandi_t_rigo_conto_economico.IMPORTO_AMMESSO_FINANZIAMENTO, "+
			" validatoCE.totaleImportoValidato "+
			" having "+
			" (sum(IMPORTO_QUIETANZATO) + totaleImportoValidato) > pbandi_t_rigo_conto_economico.IMPORTO_AMMESSO_FINANZIAMENTO ");
		
			
			//	prepareSelectPagamentiPerValidaTutti(idDichiarazioneDiSpesa,
			//	statiAmmessiPagamento, idDocumentiDiSpesa, params,
			//	sqlSelect);
													  
			List list=queryForList(sqlSelect.toString(), params);
			if(!isEmpty(list)){
				isImportoMaxSuperato=true;
			}
			/*Map map=queryForMap(sqlSelect.toString(), params);
			
			if(!isEmpty(map)){
				isImportoMaxSuperato=true;
			}*/
		
		}finally{
			getLogger().end();
		}
		
		return isImportoMaxSuperato;
	}

	@Deprecated
	// by DettaglioOperazioneAutomaticaValidazioneVO
	public List <Map> findDatiSuPagamenti(Long idDichiarazioneDiSpesa) {
		
		getLogger().begin();
		List <Map>list= null;
		try {
			StringBuilder sqlSelect = new StringBuilder("");
			sqlSelect.append(
			" select "+
			" nvl2(svs.DESC_BREVE_STATO_VALIDAZ_SPESA,svs.DESC_BREVE_STATO_VALIDAZ_SPESA,'totale') as DESC_STATO,"+
			" nvl2(SUM(pagDicSvs.NUM_PAGAMENTI), SUM(pagDicSvs.NUM_PAGAMENTI), 0) NUM_PAGAMENTI "+
			" from "+
			" (select "+
			" pag.ID_STATO_VALIDAZIONE_SPESA as  ID_STATO_VALIDAZIONE_SPESA,"+
			" count(*) NUM_PAGAMENTI "+
			" from "+
			" pbandi_r_pagamento_dich_spesa pagDic, "+
			" pbandi_t_pagamento pag  "+
			" where "+
			" pagDic.ID_DICHIARAZIONE_SPESA = :idDichiarazioneDiSpesa "+
			" and pagDic.ID_PAGAMENTO = pag.ID_PAGAMENTO "+
			" group by "+
			" pag.ID_STATO_VALIDAZIONE_SPESA) pagDicSvs, "+
			" pbandi_d_stato_validaz_spesa svs "+
			" where "+
			" pagDicSvs.ID_STATO_VALIDAZIONE_SPESA(+) = svs.ID_STATO_VALIDAZIONE_SPESA "+
			" and svs.DT_FINE_VALIDITA is null "+
			" and trunc(svs.DT_INIZIO_VALIDITA) <= trunc(sysdate) "+
			" and svs.DESC_BREVE_STATO_VALIDAZ_SPESA <> 'T' "+
			" GROUP BY ROLLUP(svs.DESC_BREVE_STATO_VALIDAZ_SPESA)"); 

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa, Types.BIGINT);
			
			list= queryForList(sqlSelect.toString(), params);
		

		}finally{
			getLogger().end();
		}
		return list;
		
	}
}