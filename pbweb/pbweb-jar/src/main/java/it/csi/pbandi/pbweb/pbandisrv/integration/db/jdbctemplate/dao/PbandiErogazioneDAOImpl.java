package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;


import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiErogazioneDAOImpl extends PbandiDAO{
	
	
	
	
	
	//INSERITO e NON VALIDATO
	/*if(!isEmpty(statiAmmessi)){
		String x="PBANDI_D_STATO_DOCUMENTO_SPESA.DESC_BREVE_STATO_DOC_SPESA in (";
		for (String statoDocumentoDispesa : statiAmmessi) {
			x+="'"+statoDocumentoDispesa+"',";	
		}
		x=x.substring(0,x.length()-1);
		x+=")";
		conditionList.add((new StringBuilder()).append(x));
	}*/
	
	public PbandiErogazioneDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	public boolean aggiornaAssociazioniPagamentiQuietanzati(Long idErogazione,Long[] idPagamenti){
		getLogger().begin();
		 boolean ret=false;
		
	
		 StringBuilder sqlUpdate= new StringBuilder("update  PBANDI_T_PAGAMENTO  set ");
		 sqlUpdate.append(" ID_EROGAZIONE =:idErogazione  ");
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		
		 params.addValue("idErogazione", idErogazione, Types.BIGINT);
		 
		 List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		 
		 StringBuilder listId = new StringBuilder();
		 for (Long id: idPagamenti){
			 listId.append(id+",");
		 }
			
		 conditionList.add((new StringBuilder()).append("ID_PAGAMENTO  IN ( ")
												   .append(listId.substring(0,listId.lastIndexOf(",")))
												   .append(" )"));
		 setWhereClause(conditionList, sqlUpdate);
		 
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
		
	}
	
	public FideiussioneVO[] findFideiussioniAttive(Long idProgetto) {
		getLogger().begin();
		List<FideiussioneVO> result = new ArrayList<FideiussioneVO>();
		FideiussioneVO[] ret = null;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder(" PBANDI_T_FIDEIUSSIONE , PBANDI_D_TIPO_TRATTAMENTO "));
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add((new StringBuilder())
					.append(" PBANDI_T_FIDEIUSSIONE.ID_TIPO_TRATTAMENTO = PBANDI_D_TIPO_TRATTAMENTO.ID_TIPO_TRATTAMENTO "));
			
			conditionList.add((new StringBuilder()).append(" (NVL(TRUNC(PBANDI_T_FIDEIUSSIONE.DT_SCADENZA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE)) "));
			conditionList.add((new StringBuilder()).append(" PBANDI_T_FIDEIUSSIONE.ID_PROGETTO=:idProgetto "));
			params.addValue("idProgetto",idProgetto,Types.BIGINT);
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT ID_FIDEIUSSIONE as id ,"+
					" PBANDI_T_FIDEIUSSIONE.IMPORTO_FIDEIUSSIONE as importo,"+
					" PBANDI_T_FIDEIUSSIONE.COD_RIFERIMENTO_FIDEIUSSIONE as numero,"+
					" PBANDI_T_FIDEIUSSIONE.DT_DECORRENZA as dataDecorrenza,"+
					" PBANDI_T_FIDEIUSSIONE.DT_SCADENZA as dataScadenza,"+
					" PBANDI_T_FIDEIUSSIONE.ID_TIPO_TRATTAMENTO as idTipoTrattamento, "+
					" PBANDI_D_TIPO_TRATTAMENTO.DESC_TIPO_TRATTAMENTO as descrizioneTipoTrattamento, "+
					" PBANDI_D_TIPO_TRATTAMENTO.DESC_BREVE_TIPO_TRATTAMENTO as descBreveTipoTrattamento,  "+
					" PBANDI_T_FIDEIUSSIONE.DESC_ENTE_EMITTENTE as descEnteEmittente  "+
					" FROM ")).append(tables);
			
			// non va messa la data fine a null
			setWhereClause(conditionList, sqlSelect);		
			
			result  = query(sqlSelect.toString(), params,new PbandiFideiussioneRowMapper());
			if (!result.isEmpty()){
				ret = result.toArray(new FideiussioneVO[]{});
			}
			
		} finally{
			 getLogger().end();
		}
		
		return ret;
	}
	
	
	public FideiussioneVO[] findFideiussioniAttivePerTipoTrattamento(Long idProgetto) {
		getLogger().begin();
		List<FideiussioneVO> result = new ArrayList<FideiussioneVO>();
		FideiussioneVO[] ret = null;
		try {
			
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT  SUM(PBANDI_T_FIDEIUSSIONE.IMPORTO_FIDEIUSSIONE) as importo, "));
					sqlSelect.append("PBANDI_T_FIDEIUSSIONE.ID_TIPO_TRATTAMENTO as idTipoTrattamento,");
					sqlSelect.append("PBANDI_D_TIPO_TRATTAMENTO.DESC_TIPO_TRATTAMENTO as descrizioneTipoTrattamento, ");
					sqlSelect.append("PBANDI_D_TIPO_TRATTAMENTO.DESC_BREVE_TIPO_TRATTAMENTO as descBreveTipoTrattamento ");
					sqlSelect.append(" FROM PBANDI_T_FIDEIUSSIONE , PBANDI_D_TIPO_TRATTAMENTO ");
					sqlSelect.append(" WHERE PBANDI_T_FIDEIUSSIONE.ID_TIPO_TRATTAMENTO = PBANDI_D_TIPO_TRATTAMENTO.ID_TIPO_TRATTAMENTO ");
					sqlSelect.append(" AND (NVL(TRUNC(PBANDI_T_FIDEIUSSIONE.DT_SCADENZA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE))  ");
					sqlSelect.append(" AND PBANDI_T_FIDEIUSSIONE.ID_PROGETTO =:idProgetto ");
					sqlSelect.append(" GROUP BY PBANDI_T_FIDEIUSSIONE.ID_TIPO_TRATTAMENTO ,");
					sqlSelect.append(" PBANDI_D_TIPO_TRATTAMENTO.DESC_TIPO_TRATTAMENTO, ");
					sqlSelect.append(" PBANDI_D_TIPO_TRATTAMENTO.DESC_BREVE_TIPO_TRATTAMENTO ");
					
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
							
			
			result  = query(sqlSelect.toString(), params,new PbandiFideiussioneRowMapper());
			if (!result.isEmpty()){
				ret = result.toArray(new FideiussioneVO[]{});
			}
			
		} finally{
			 getLogger().end();
		}
		
		return ret;
	}
	
	
	public Double getPercentualeErogazione(Long idProgetto, Long idCausaleErogazione){
		
		getLogger().begin();
		try {

			
			StringBuilder sqlSelect = new StringBuilder(
					"PBANDI_R_TIPO_OP_CAUSALE_EROGA.PERCENTUALE_EROGAZIONE  ");
			sqlSelect.append(" PBANDI_R_TIPO_OP_CAUSALE_EROGA ");
			sqlSelect.append(" WHERE");
			sqlSelect
					.append(" PBANDI_R_TIPO_OP_CAUSALE_EROGA.ID_CAUSALE_EROGAZIONE=:idCausaleErogazione and");
			sqlSelect
					.append(" PBANDI_R_TIPO_OP_CAUSALE_EROGA.ID_TIPO_OPERAZIONE  IN (");
			sqlSelect
					.append(" SELECT PBANDI_T_PROGETTO.ID_TIPO_OPERAZIONE from PBANDI_T_PROGETTO ");
			sqlSelect.append(" where PBANDI_T_PROGETTO.ID_PROGETTO =:idProgetto ) ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			params.addValue("idCausaleErogazione", idCausaleErogazione, Types.BIGINT);

			return queryForDouble(sqlSelect.toString(), params);

		} finally {
			getLogger().end();
		}	
	}
	
	private class PbandiFideiussioneRowMapper implements 
	RowMapper<it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO>{

		public it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			FideiussioneVO vo=
				(it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione.FideiussioneVO.class);
			 return vo;
		}
	}
}
