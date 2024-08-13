/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.FornitoreQualificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.util.NumberUtil;

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

public class PbandiFornitoreQualificaDAOImpl extends PbandiDAO {
	
	// NOTA: Costruttore aggiunto importando la classe da pbandisrv, poichè altrimenti non compila.
	public PbandiFornitoreQualificaDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}


	private AbstractDataFieldMaxValueIncrementer seqProgrQualificaFornitori;
	
	

	public void setSeqProgrQualificaFornitori(AbstractDataFieldMaxValueIncrementer seqProgrQualificaFornitori) {
		this.seqProgrQualificaFornitori = seqProgrQualificaFornitori;
	}


	public void setPbandiRFornitoreQualificaDAO(
			PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO) {
		this.pbandiRFornitoreQualificaDAO = pbandiRFornitoreQualificaDAO;
	}
	public PbandiFornitoreQualificaDAOImpl getPbandiRFornitoreQualificaDAO() {
		return pbandiRFornitoreQualificaDAO;
	}


	private PbandiFornitoreQualificaDAOImpl pbandiRFornitoreQualificaDAO;

	public AbstractDataFieldMaxValueIncrementer getSeqProgrQualificaFornitori() {
		return seqProgrQualificaFornitori;
	}
	
	
	public Boolean inserisciQualifica(FornitoreDTO fornitore) {
		getLogger().begin();

		StringBuffer sqlInsert= new StringBuffer("insert into PBANDI_R_FORNITORE_QUALIFICA ");
		 sqlInsert.append("(")
		 .append("COSTO_ORARIO")
		// .append(",COSTO_RISORSA")
		 .append(",DT_INIZIO_VALIDITA")
		 .append(",ID_FORNITORE")
		 .append(",ID_QUALIFICA")
		 //.append(",MONTE_ORE")
		 .append(",PROGR_FORNITORE_QUALIFICA")

		 .append(")");
		 
		 sqlInsert.append("values(") 
		 .append(":costoOrario")
		// .append(",:costoRisorsa")
		 .append(",sysdate")
		 .append(",:idFornitore")
		 .append(",:idQualifica")
		// .append(",:monteOre")
		 .append(",:progrFornitoreQualifica")
		 .append(")");
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("costoOrario", fornitore.getCostoOrario(), Types.DOUBLE);
	//	 params.addValue("costoRisorsa", fornitore.getCostoRisorsa(), Types.DOUBLE);
		 params.addValue("idFornitore", fornitore.getIdFornitore(),Types.BIGINT);
		 params.addValue("idQualifica", fornitore.getIdQualifica(), Types.BIGINT);
		// params.addValue("monteOre", fornitore.getMonteOre(), Types.DOUBLE);
		 Long progrFornitoreQualifica= seqProgrQualificaFornitori.nextLongValue();
		 params.addValue("progrFornitoreQualifica",progrFornitoreQualifica,Types.BIGINT);
		 return  inserisci(sqlInsert.toString(), params);
		 
	}

	
	
	
	/*
	 * ID_FORNITORE
	ID_QUALIFICA
	MONTE_ORE
	COSTO_ORARIO
	DT_FINE_VALIDITA
	COSTO_RISORSA
	 */
	public Boolean modificaQualifica(FornitoreDTO fornitore,Long progressivoQualifica) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
		 StringBuilder tables = (new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA"));
		 
		 StringBuilder sqlUpdate= new StringBuilder("update ");
		 sqlUpdate.append(tables);
		 sqlUpdate.append(" set ")
		 .append(" ID_QUALIFICA=:idQualifica")
		// .append(",MONTE_ORE=:monteOre")
		 .append(",COSTO_ORARIO=:costoOrario");
	//	 .append(",COSTO_RISORSA=:costoRisorsa");
//		
		 List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
		 conditionList.add((new StringBuilder()).append("ID_FORNITORE=:idFornitore"));
		 conditionList.add((new StringBuilder()).append("PROGR_FORNITORE_QUALIFICA=:progrFornitoreQualifica"));
		 
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("idQualifica", fornitore.getIdQualifica(), Types.BIGINT);
		// params.addValue("monteOre", fornitore.getMonteOre(), Types.DOUBLE);
		 params.addValue("costoOrario", fornitore.getCostoOrario(), Types.DOUBLE);
	//	 params.addValue("costoRisorsa", fornitore.getCostoRisorsa(), Types.DOUBLE);
		 params.addValue("idFornitore", fornitore.getIdFornitore(),Types.BIGINT);
		 params.addValue("progrFornitoreQualifica", progressivoQualifica,Types.BIGINT);
		 
		 setWhereClause(conditionList, sqlUpdate,tables);	
		
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
	     
	     
	}
	

	

	public Boolean disattivaQualifica(Long idFornitore,Long progrQualifica) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
	
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_R_FORNITORE_QUALIFICA  set");
		 sqlUpdate.append(" DT_FINE_VALIDITA=sysdate")
		 .append(" where ID_FORNITORE=:idFornitore")
		 .append(" and PROGR_FORNITORE_QUALIFICA=:progrQualifica") ;
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("idFornitore", idFornitore, Types.BIGINT);
		 params.addValue("progrQualifica", progrQualifica, Types.BIGINT); 
		 
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
	}

	
	public Boolean attivaQualifica(Long idFornitore,Long progrQualifica) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
	
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_R_FORNITORE_QUALIFICA  set ");
		 sqlUpdate.append(" DT_FINE_VALIDITA=null")
		 .append(" where ID_FORNITORE=:idFornitore")
		 .append(" and PROGR_FORNITORE_QUALIFICA=:progrQualifica") ;
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("idFornitore", idFornitore, Types.BIGINT);
		 params.addValue("progrQualifica", progrQualifica, Types.BIGINT); 
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
	}
	
	public Boolean disattivaTutteLeQualifiche(Long idFornitore) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
	
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_R_FORNITORE_QUALIFICA  set");
		 sqlUpdate.append(" DT_FINE_VALIDITA=sysdate")
		 .append(" where ID_FORNITORE=:idFornitore");
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("idFornitore", idFornitore, Types.BIGINT);
		 
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
	}



	
	public List<FornitoreQualificaVO> findQualifiche(Long idFornitore) {
		List<FornitoreQualificaVO> result = new ArrayList<FornitoreQualificaVO>();
	 
			
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder tables = (new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA"));
		
		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		
		conditionList.add((new StringBuilder()).append("ID_FORNITORE=:idFornitore"));
		params.addValue("idFornitore",idFornitore,Types.BIGINT);
		
		StringBuilder sqlSelect = (new StringBuilder("SELECT COSTO_ORARIO as costoOrario ,"+
			//	" COSTO_RISORSA as costoRisorsa,"+
				" DT_FINE_VALIDITA as dtFineValidita,"+
				" DT_INIZIO_VALIDITA as dtInizioValidita,"+
				" ID_FORNITORE as idFornitore,"+
				" ID_QUALIFICA as idQualifica,"+
			//	" MONTE_ORE as monteOre,"+
				" PROGR_FORNITORE_QUALIFICA as  progrFornitoreQualifica " +
				" FROM ")).append(tables);
		
		// non va messa la data fine a null
		setWhereClause(conditionList, sqlSelect);		
		
		result  = query(sqlSelect.toString(), params,new PbandiFornitoriQualificheRowMapper());
		getLogger().debug(" qualifica fornitore trovate: " + result.size());
			
		 
		return result;
	}
	
	
	public FornitoreQualificaVO findQualifica(Long idFornitore) {
		FornitoreQualificaVO result = new FornitoreQualificaVO();
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA"));
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			conditionList.add((new StringBuilder()).append("ID_FORNITORE=:idFornitore"));
			params.addValue("idFornitore",idFornitore,Types.BIGINT);
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT COSTO_ORARIO as costoOrario ,"+
				//	" COSTO_RISORSA as costoRisorsa,"+
					" DT_FINE_VALIDITA as dtFineValidita,"+
					" DT_INIZIO_VALIDITA as dtInizioValidita,"+
					" ID_FORNITORE as idFornitore,"+
					" ID_QUALIFICA as idQualifica,"+
				//	" MONTE_ORE as monteOre,"+
					" PROGR_FORNITORE_QUALIFICA as  progrFornitoreQualifica " +
					" FROM ")).append(tables);
			
			setWhereClause(conditionList, sqlSelect,tables);		
			
			result  = queryForObject(sqlSelect.toString(), params,new PbandiFornitoriQualificheRowMapper());
		
			
		} finally{
			 getLogger().end();
		}
		return result;
	}


	/**
	 * 
	 */
	private class PbandiFornitoriQualificheRowMapper implements RowMapper<FornitoreQualificaVO>{

		public FornitoreQualificaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			FornitoreQualificaVO vo=(FornitoreQualificaVO) beanMapper.toBean(rs,
					FornitoreQualificaVO.class);
			 return vo;
		}
	}


	public Long findQualificaConMaxDataFineValidita(Long idFornitore,
			Long idQualifica) {
		Long progr=null;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder tables = (new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA"));
		
		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		
		conditionList.add((new StringBuilder()).append("ID_FORNITORE=:idFornitore"));
		conditionList.add((new StringBuilder()).append("ID_QUALIFICA=:idQualifica"));

		params.addValue("idFornitore",idFornitore,Types.BIGINT);
		params.addValue("idQualifica",idQualifica,Types.BIGINT);
		
		StringBuilder sqlSelect = (new StringBuilder("select max(DT_FINE_VALIDITA) as maxdata,"+
				" max(PROGR_FORNITORE_QUALIFICA) as PROGR_FORNITORE_QUALIFICA "+ 
				" FROM ")).append(tables);
		
		setWhereClause(conditionList, sqlSelect);		
		
		List <Map>list= queryForList(sqlSelect.toString(), params);
		for(Map result:list){
			if(result.containsKey("PROGR_FORNITORE_QUALIFICA")){
				BigDecimal id=((BigDecimal)result.get("PROGR_FORNITORE_QUALIFICA"));
				progr=NumberUtil.toLong(id);
				break;
			}
			
		}
		if(!isNull(progr)){
			getLogger().debug(" qualifica fornitore trovata con progr : " + progr);
		}else{
			getLogger().debug(" qualifica fornitore NON trovata " );
		}
			
		return progr;
		
	}



}
