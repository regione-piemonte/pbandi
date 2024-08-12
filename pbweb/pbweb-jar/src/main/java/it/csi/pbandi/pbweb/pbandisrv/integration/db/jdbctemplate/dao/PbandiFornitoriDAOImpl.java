package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestionefornitori.FornitoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori.FornitoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;


public class PbandiFornitoriDAOImpl extends PbandiDAO {
	
	
	public PbandiFornitoriDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}


	private AbstractDataFieldMaxValueIncrementer seqpbandifornitori;
	
	public AbstractDataFieldMaxValueIncrementer getSeqpbandifornitori() {
		return seqpbandifornitori;
	}


	public void setSeqpbandifornitori(
			AbstractDataFieldMaxValueIncrementer seqpbandifornitori) {
		this.seqpbandifornitori = seqpbandifornitori;
	}


	/**
	 */
	public java.util.List<FornitoreVO> findFornitori(FornitoreDTO filtro){
		java.util.List<FornitoreVO> result = new ArrayList<FornitoreVO>();
		String pr = "[PbandiFornitoriDAOImpl::findFornitori] ";
		logger.debug(pr+"BEGIN");
			
			/**
			 * I parametri di ricerca non sono tutti obbligatori
			 */
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder("PBANDI_T_FORNITORE,PBANDI_D_TIPO_SOGGETTO"));
			
			tables.append(",PBANDI_R_FORNITORE_QUALIFICA,PBANDI_D_QUALIFICA");
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			if(!isEmpty(filtro.getCodiceFiscaleFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE LIKE :codiceFiscale"));
				params.addValue("codiceFiscale", filtro.getCodiceFiscaleFornitore().toUpperCase()+ "%",Types.VARCHAR);
			}
			if(!isEmpty(filtro.getCognomeFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.COGNOME_FORNITORE LIKE :cognomeFornitore"));
				params.addValue("cognomeFornitore",filtro.getCognomeFornitore().toUpperCase()+ "%",Types.VARCHAR);
			}
			if(!isEmpty(filtro.getDenominazioneFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE LIKE :denominazioneFornitore"));
				params.addValue("denominazioneFornitore",filtro.getDenominazioneFornitore().toUpperCase()+ "%",Types.VARCHAR);
			}
			if(!isNull(filtro.getIdTipoSoggetto())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO=:idTipoSoggetto"));
				params.addValue("idTipoSoggetto",filtro.getIdTipoSoggetto(),Types.BIGINT);
			}
			if(!isNull(filtro.getIdSoggettoFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_SOGGETTO_FORNITORE = :idSoggettoFornitore"));
				params.addValue("idSoggettoFornitore",filtro.getIdSoggettoFornitore(),Types.BIGINT);
			}
			if(!isEmpty(filtro.getAltroCodice())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ALTRO_CODICE LIKE :altroCodice"));
				params.addValue("altroCodice", filtro.getAltroCodice().toUpperCase()+ "%",Types.VARCHAR);
			}
			if(!isNull(filtro.getIdFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_FORNITORE = :idFornitore"));
				params.addValue("idFornitore",filtro.getIdFornitore(),Types.BIGINT);
			}
			
			conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO=PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO"));
			
			if(!isEmpty(filtro.getNomeFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.NOME_FORNITORE LIKE :nomeFornitore"));
				params.addValue("nomeFornitore",filtro.getNomeFornitore().toUpperCase()+ "%",Types.VARCHAR);
			}
			if(!isEmpty(filtro.getPartitaIvaFornitore())){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE LIKE :partitaIvaFornitore"));
				params.addValue("partitaIvaFornitore",filtro.getPartitaIvaFornitore()+ "%",Types.VARCHAR);
			}
			conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_FORNITORE=PBANDI_R_FORNITORE_QUALIFICA.ID_FORNITORE(+)"));
			conditionList.add((new StringBuilder()).append("PBANDI_R_FORNITORE_QUALIFICA.ID_QUALIFICA =PBANDI_D_QUALIFICA.ID_QUALIFICA(+)"));
			if(!isNull(filtro.getIdQualifica())){
				conditionList.add((new StringBuilder()).append("PBANDI_R_FORNITORE_QUALIFICA.ID_QUALIFICA =:idQualifica"));
				params.addValue("idQualifica",filtro.getIdQualifica(),Types.BIGINT);
			}
			
			/**
			 * FIXME: PBandi_240
			 * Inserisco nel filtro i codici delle qualifiche che devono
			 * essere scartate
			 */
			
			if (filtro.getCodQualificheNotIn()!= null && filtro.getCodQualificheNotIn().length > 0){
				StringBuilder qualificheNotIn = new StringBuilder();
				for (String codiceQualifica : filtro.getCodQualificheNotIn()){
					qualificheNotIn.append("'"+codiceQualifica+"',");
				}
				conditionList.add((new StringBuilder()).append("PBANDI_D_QUALIFICA.DESC_BREVE_QUALIFICA NOT IN ( ")
													   .append(qualificheNotIn.substring(0,qualificheNotIn.lastIndexOf(",")))
													   .append(" )"));
				
			}
			
			/**
			 * FIXME: PBandi_286
			 * Inserisco nel filtro i codici delle qualifiche che devono
			 * essere considerate
			 */
			if (filtro.getCodQualificheIn()!= null && filtro.getCodQualificheIn().length > 0){
				StringBuilder qualificheIn = new StringBuilder();
				for (String codiceQualifica : filtro.getCodQualificheIn()){
					qualificheIn.append("'"+codiceQualifica+"',");
				}
				conditionList.add((new StringBuilder()).append("PBANDI_D_QUALIFICA.DESC_BREVE_QUALIFICA  IN ( ")
													   .append(qualificheIn.substring(0,qualificheIn.lastIndexOf(",")))
													   .append(" )"));
				
			}
	
			//I fornitori aventi una data di fine validit� non saranno esposti all�attore per la gestione
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT " +
					" PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE as codiceFiscaleFornitore, "+
					" PBANDI_T_FORNITORE.COGNOME_FORNITORE as cognomeFornitore, "+
					" PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE as denominazioneFornitore, "+
					" PBANDI_T_FORNITORE.ID_FORNITORE as idFornitore, "+
					" PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO as idTipoSoggetto, "+
					" PBANDI_T_FORNITORE.NOME_FORNITORE as nomeFornitore, "+
					" PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE as partitaIvaFornitore," +
					" PBANDI_T_FORNITORE.ID_FORMA_GIURIDICA as idFormaGiuridica," +
					" PBANDI_T_FORNITORE.ID_ATTIVITA_ATECO as idAttivitaAteco," +
					" PBANDI_T_FORNITORE.ID_NAZIONE as idNazione," +
					" PBANDI_T_FORNITORE.ALTRO_CODICE as altroCodice," +
					" PBANDI_D_TIPO_SOGGETTO.DESC_TIPO_SOGGETTO as descTipoSoggetto,"+
					// qualifica: non la breve,quella estesa
					" PBANDI_D_QUALIFICA.DESC_QUALIFICA as descQualifica,"+
					" PBANDI_D_QUALIFICA.ID_QUALIFICA as idQualifica," +
					" PBANDI_T_FORNITORE.DT_FINE_VALIDITA as dtFineValidita,"+
					" PBANDI_T_FORNITORE.FLAG_PUBBLICO_PRIVATO as flagPubblicoPrivato,"+
					" PBANDI_T_FORNITORE.COD_UNI_IPA as codUniIpa" +
					" FROM ")).append(tables);
			
			// this feature is no more used on web, but for jira 2521 doesn't have to check dt_fine_validita pbandi_r_fornitore_qualifica
			 /*boolean includiFornitoriInvalidi=(filtro.getIncludiFornitoriInvalidi()!=null?filtro.getIncludiFornitoriInvalidi():false);
			 if(includiFornitoriInvalidi){
			 	StringBuilder tablesDaEscludere = (new StringBuilder("PBANDI_T_FORNITORE"));
				tablesDaEscludere.append(",pbandi_r_fornitore_qualifica");
				setWhereClause( conditionList, sqlSelect,tables,tablesDaEscludere);
			 }
			else
				setWhereClause( conditionList, sqlSelect,tables);*/
			
			// 3/7/2015 jira 2521  doesn't have to check dt_fine_validita pbandi_r_fornitore_qualifica
			StringBuilder tablesDaEscludere = (new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA"));
			setWhereClause( conditionList, sqlSelect,tables,tablesDaEscludere);
			// 3/7/2015 jira 2521
			
			setOrderBy(sqlSelect, "cognomeFornitore asc, nomeFornitore asc, denominazioneFornitore", true);
			result = query(sqlSelect.toString(), params, new PbandiFornitoriRowMapper());
			
			logger.debug(pr+"params="+params);
			logger.debug(pr+"sqlSelect.toString()="+sqlSelect.toString());
			logger.debug(pr+"END");
		return result;
	}
	
	/**
	 * TODO: aggiungere il booleano per decidere se controllare dt_fine_validita (PBANDI-491)
	 */
	public FornitoreVO findDettaglioFornitore(Long idFornitore,Long progFornitoreQualifica, boolean checkDataFine){
		List<FornitoreVO> result;
		try {
			
			/**
			 * I parametri di ricerca non sono tutti obbligatori
			 */
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder tables = (new StringBuilder("PBANDI_T_FORNITORE,PBANDI_D_TIPO_SOGGETTO"));
			tables.append(",PBANDI_R_FORNITORE_QUALIFICA,PBANDI_D_QUALIFICA");
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			
			if(!isNull(idFornitore)){
				conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_FORNITORE=:idFornitore"));
				params.addValue("idFornitore",idFornitore,Types.BIGINT);
			}
			if(!isNull(progFornitoreQualifica)){
				conditionList.add((new StringBuilder()).append("PBANDI_R_FORNITORE_QUALIFICA.PROGR_FORNITORE_QUALIFICA=:progFornitoreQualifica"));
				params.addValue("progFornitoreQualifica",progFornitoreQualifica,Types.BIGINT);
			}
			
			conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO=PBANDI_D_TIPO_SOGGETTO.ID_TIPO_SOGGETTO"));
			conditionList.add((new StringBuilder()).append("PBANDI_T_FORNITORE.ID_FORNITORE=PBANDI_R_FORNITORE_QUALIFICA.ID_FORNITORE(+)"));
			conditionList.add((new StringBuilder()).append("PBANDI_R_FORNITORE_QUALIFICA.ID_QUALIFICA =PBANDI_D_QUALIFICA.ID_QUALIFICA(+)"));
			
			StringBuilder sqlSelect = (new StringBuilder("SELECT DISTINCT " +
					" PBANDI_T_FORNITORE.CODICE_FISCALE_FORNITORE as codiceFiscaleFornitore, "+
					" PBANDI_T_FORNITORE.COGNOME_FORNITORE as cognomeFornitore, "+
					" PBANDI_T_FORNITORE.DENOMINAZIONE_FORNITORE as denominazioneFornitore, "+
					" PBANDI_T_FORNITORE.DT_INIZIO_VALIDITA as dtInizioValidita," +
					" PBANDI_T_FORNITORE.ID_FORNITORE as idFornitore, "+
					" PBANDI_T_FORNITORE.ID_SOGGETTO_FORNITORE as idSoggettoFornitore, "+
					" PBANDI_T_FORNITORE.ID_TIPO_SOGGETTO as idTipoSoggetto, "+
					" PBANDI_T_FORNITORE.ID_UTENTE_INS as idUtenteIns," +
					" PBANDI_T_FORNITORE.NOME_FORNITORE as nomeFornitore, "+
					" PBANDI_T_FORNITORE.PARTITA_IVA_FORNITORE as partitaIvaFornitore," +
					" PBANDI_T_FORNITORE.ALTRO_CODICE as altroCodice," +
					" PBANDI_T_FORNITORE.COD_UNI_IPA as codUniIpa," +
					" PBANDI_T_FORNITORE.FLAG_PUBBLICO_PRIVATO as flagPubblicoPrivato," +
					" PBANDI_D_TIPO_SOGGETTO.DESC_TIPO_SOGGETTO as descTipoSoggetto, "+
					// qualifica: non la breve,quella estesa
					" PBANDI_D_QUALIFICA.DESC_QUALIFICA as descQualifica,"+
					" PBANDI_D_QUALIFICA.ID_QUALIFICA as idQualifica,"+
					//" PBANDI_R_FORNITORE_QUALIFICA.MONTE_ORE  as monteOre,"+
					" PBANDI_R_FORNITORE_QUALIFICA.COSTO_ORARIO AS costoOrario,"+
				//	" PBANDI_R_FORNITORE_QUALIFICA.COSTO_RISORSA AS costoRisorsa,"+
					" PBANDI_R_FORNITORE_QUALIFICA.dt_inizio_validita as dtiniziovaliditaqualif"+
					
					" FROM ")).append(tables);
			   
			if (checkDataFine) {
				setWhereClause(conditionList, sqlSelect, tables);		
			} else {
				setWhereClause(conditionList, sqlSelect, tables, new StringBuilder("PBANDI_T_FORNITORE"));
			}
			sqlSelect.append(" order by PBANDI_R_FORNITORE_QUALIFICA.dt_inizio_validita desc");
			
			result = query(sqlSelect.toString(), params, new PbandiFornitoriRowMapper());
			
		} 
		finally{
			getLogger().end();
		}
		return result.size() > 0 ? result.get(0) : null;
	}
	
	
	/**
	 * 
	 */
	private class PbandiFornitoriRowMapper implements RowMapper<FornitoreVO>{

		public FornitoreVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			FornitoreVO vo=(FornitoreVO) beanMapper.toBean(rs,
			FornitoreVO.class);
			 return vo;
		}
	}


	
	
	
	public Long inserisciFornitore(FornitoreDTO fornitore,Long idUtente) {
		getLogger().begin();
		 StringBuffer sqlInsert= new StringBuffer("insert into PBANDI_T_FORNITORE ");
		 sqlInsert.append("(CODICE_FISCALE_FORNITORE")
		 .append(",COGNOME_FORNITORE")
		 .append(",DENOMINAZIONE_FORNITORE")
		 .append(",DT_INIZIO_VALIDITA")
		 .append(",ID_FORNITORE")
		 .append(",ID_SOGGETTO_FORNITORE")
		 .append(",ID_TIPO_SOGGETTO")
		 .append(",ID_UTENTE_INS")
		 .append(",NOME_FORNITORE")
		 .append(",ID_ATTIVITA_ATECO")
		 .append(",ID_FORMA_GIURIDICA")
         .append(",ID_NAZIONE")
		 .append(",PARTITA_IVA_FORNITORE ")
		 .append(",COD_UNI_IPA ")
		 .append(",FLAG_PUBBLICO_PRIVATO ")
		 .append(", ALTRO_CODICE) ");
		 
		 sqlInsert.append("values(:codiceFiscaleFornitore")
		 .append(",:cognomeFornitore")
		 .append(",:denominazioneFornitore")
		 .append(",:dtInizioValidita")
		 .append(",:idFornitore")
		 .append(",:idSoggettoFornitore")
		 .append(",:idTipoSoggetto")
		 .append(",:idUtenteIns")
		 .append(",:nomeFornitore")
		 .append(",:idAttivitaAteco")
		 .append(",:idFormaGiuridica")
		 .append(",:idNazione")
		 .append(",:partitaIvaFornitore")
		 .append(",:codUniIpa")
		 .append(",:flagPubblicoPrivato")
		 .append(",:altroCodice)")
		 ;
		 
		 // Alex 20/4/2021 : sostituito con select sulla sequence poichè getSeqpbandifornitori() restituisce un oggetto nullo.
		 //Long idFornitore=getSeqpbandifornitori().nextLongValue();
		 String sqlSeq = "select SEQ_PBANDI_T_FORNITORE.nextval from dual ";
		 getLogger().info("\n"+sqlSeq.toString());
		 Long idFornitore = queryForLong(sqlSeq.toString(), new MapSqlParameterSource());
		 getLogger().info("idFornitore = "+idFornitore);
		 
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("codiceFiscaleFornitore", upper(fornitore.getCodiceFiscaleFornitore()), Types.VARCHAR);
		 params.addValue("cognomeFornitore",  upper(fornitore.getCognomeFornitore()), Types.VARCHAR);
		 params.addValue("denominazioneFornitore",  upper(fornitore.getDenominazioneFornitore()), Types.VARCHAR);
		 params.addValue("dtInizioValidita", new Date(Calendar.getInstance().getTimeInMillis()), Types.TIMESTAMP);
		 params.addValue("idFornitore", idFornitore, Types.BIGINT);
		 params.addValue("idSoggettoFornitore", fornitore.getIdSoggettoFornitore(), Types.BIGINT);
		 params.addValue("idTipoSoggetto", fornitore.getIdTipoSoggetto(), Types.BIGINT);
		 params.addValue("idUtenteIns", idUtente, Types.BIGINT);
		 params.addValue("nomeFornitore",  upper(fornitore.getNomeFornitore()), Types.VARCHAR);
		 Long idAttivitaAteco=null;
		 if(fornitore.getIdAttivitaAteco()!=null && fornitore.getIdAttivitaAteco()>0l)idAttivitaAteco=fornitore.getIdAttivitaAteco();
		 params.addValue("idAttivitaAteco", idAttivitaAteco, Types.BIGINT);
		 params.addValue("idFormaGiuridica", fornitore.getIdFormaGiuridica(), Types.BIGINT);
		 params.addValue("idNazione", fornitore.getIdNazione(), Types.BIGINT);
		 params.addValue("partitaIvaFornitore", fornitore.getPartitaIvaFornitore(),Types.VARCHAR);
		 params.addValue("altroCodice", fornitore.getAltroCodice(), Types.VARCHAR);
		 params.addValue("flagPubblicoPrivato", fornitore.getFlagPubblicoPrivato(), Types.BIGINT);
		 params.addValue("codUniIpa", fornitore.getCodUniIpa(), Types.VARCHAR);
		 	
		 inserisci(sqlInsert.toString(), params);
		 
		 return idFornitore;
	}

	
	
	
	

 
	public Boolean modificaFornitore(FornitoreDTO fornitore,Long idUtente) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
		
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_T_FORNITORE  set");
		 sqlUpdate.append(" CODICE_FISCALE_FORNITORE=:codiceFiscaleFornitore")
		 .append(",COGNOME_FORNITORE=:cognomeFornitore")
		 .append(",DENOMINAZIONE_FORNITORE=:denominazioneFornitore")
		 .append(",ID_SOGGETTO_FORNITORE=:idSoggettoFornitore")
		 .append(",ID_TIPO_SOGGETTO=:idTipoSoggetto")
		 .append(",ID_UTENTE_AGG=:idUtente")
		 .append(",NOME_FORNITORE=:nomeFornitore")
		 .append(",ID_ATTIVITA_ATECO=:idAttivitaAteco")
		 .append(",ID_FORMA_GIURIDICA=:idFormaGiuridica")
		 .append(",ID_NAZIONE=:idNazione")
		 .append(",PARTITA_IVA_FORNITORE=:partitaIvaFornitore ")
		 .append(",COD_UNI_IPA=:codUniIpa ")
		 .append(",FLAG_PUBBLICO_PRIVATO=:flagPubblicoPrivato")
		 .append(" where ID_FORNITORE=:idFornitore ");
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("codiceFiscaleFornitore", upper(fornitore.getCodiceFiscaleFornitore()), Types.VARCHAR);
		 params.addValue("cognomeFornitore",  upper(fornitore.getCognomeFornitore()), Types.VARCHAR);
		 params.addValue("denominazioneFornitore",  upper(fornitore.getDenominazioneFornitore()), Types.VARCHAR);
		 params.addValue("idSoggettoFornitore", fornitore.getIdSoggettoFornitore(), Types.BIGINT);
		 params.addValue("idTipoSoggetto", fornitore.getIdTipoSoggetto(), Types.BIGINT);
		 params.addValue("idUtente", idUtente,Types.BIGINT);
		 params.addValue("nomeFornitore",  upper(fornitore.getNomeFornitore()), Types.VARCHAR);
		 Long idAttivitaAteco=null;
		 if(fornitore.getIdAttivitaAteco()!=null && fornitore.getIdAttivitaAteco()>0l)idAttivitaAteco=fornitore.getIdAttivitaAteco();
		 params.addValue("idAttivitaAteco",idAttivitaAteco, Types.BIGINT);
		 params.addValue("idFormaGiuridica", fornitore.getIdFormaGiuridica(), Types.BIGINT);
		 params.addValue("idNazione", fornitore.getIdNazione(), Types.BIGINT);
		 params.addValue("partitaIvaFornitore", fornitore.getPartitaIvaFornitore(),Types.VARCHAR);
		 params.addValue("idFornitore", fornitore.getIdFornitore(), Types.BIGINT);
		 params.addValue("codUniIpa", fornitore.getCodUniIpa(), Types.VARCHAR);
		 params.addValue("flagPubblicoPrivato", fornitore.getFlagPubblicoPrivato(), Types.BIGINT);
		
		 ret = modifica(sqlUpdate.toString(), params);
 	    
		 return ret;
	     
	     
	}
	
	public Boolean riattivaFornitore(FornitoreDTO fornitore,Long idUtente) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
		
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_T_FORNITORE  set");
		 sqlUpdate.append(" CODICE_FISCALE_FORNITORE=:codiceFiscaleFornitore")
		 .append(",COGNOME_FORNITORE=:cognomeFornitore")
		 .append(",DENOMINAZIONE_FORNITORE=:denominazioneFornitore")
		 .append(",ID_SOGGETTO_FORNITORE=:idSoggettoFornitore")
		 .append(",ID_TIPO_SOGGETTO=:idTipoSoggetto")
		 .append(",ID_UTENTE_AGG=:idUtente")
		 .append(",NOME_FORNITORE=:nomeFornitore")
		 .append(",PARTITA_IVA_FORNITORE=:partitaIvaFornitore")
		 .append(",DT_FINE_VALIDITA=null" )
		 .append(" where ID_FORNITORE=:idFornitore");
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("codiceFiscaleFornitore", upper(fornitore.getCodiceFiscaleFornitore()), Types.VARCHAR);
		 params.addValue("cognomeFornitore",  upper(fornitore.getCognomeFornitore()), Types.VARCHAR);
		 params.addValue("denominazioneFornitore",  upper(fornitore.getDenominazioneFornitore()), Types.VARCHAR);
		 params.addValue("idSoggettoFornitore", fornitore.getIdSoggettoFornitore(), Types.BIGINT);
		 params.addValue("idTipoSoggetto", fornitore.getIdTipoSoggetto(), Types.BIGINT);
		 params.addValue("idUtente", idUtente,Types.BIGINT);
		 params.addValue("nomeFornitore",  upper(fornitore.getNomeFornitore()), Types.VARCHAR);
		 params.addValue("partitaIvaFornitore", fornitore.getPartitaIvaFornitore(),Types.VARCHAR);
		 params.addValue("idFornitore", fornitore.getIdFornitore(), Types.BIGINT);
		
		
		 ret = modifica(sqlUpdate.toString(), params);
	     getLogger().end();
		 return ret;
	     
	     
	}
	

	public Boolean disattivaFornitore(Long idFornitore) {
		 Boolean ret=Boolean.FALSE;
		 getLogger().begin();
	
		 StringBuffer sqlUpdate= new StringBuffer("update  PBANDI_T_FORNITORE  set");
		 sqlUpdate.append(" DT_FINE_VALIDITA=sysdate")
		 .append(" where ID_FORNITORE=:idFornitore");
		
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 
		 params.addValue("idFornitore", idFornitore, Types.BIGINT);
		 
		 ret = modifica(sqlUpdate.toString(), params);
		 getLogger().end();
		 return ret;
	}


	/**
	 * @param idFornitore
	 * @return TRUE se il fornitore non ha documenti associati a dichiarazioni di spesa in stato diverso da "DICHIARABILE"
	 * Jira PBANDI-2683
	 */
	public Boolean isAllegatoFornitoreDisassociabile(Long idFornitore, Long idProgetto) {
		String pr = "[PbandiFornitoriDAOImpl::isAllegatoFornitoreDisassociabile] ";
	
		logger.debug(pr+"BEGIN");
		logger.debug(pr+"idFornitore="+idFornitore);

		Boolean ret=Boolean.TRUE;

		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder tables = (new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA , PBANDI_R_DOC_SPESA_PROGETTO, PBANDI_D_STATO_DOCUMENTO_SPESA"));

		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
		conditionList.add((new StringBuilder()).append("pbandi_t_documento_di_spesa.id_fornitore=:idFornitore"));
		conditionList.add((new StringBuilder()).append("pbandi_r_doc_spesa_progetto.id_progetto=:idProgetto"));
		params.addValue("idFornitore", idFornitore, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);

		conditionList.add((new StringBuilder()).append("pbandi_d_stato_documento_spesa.DESC_STATO_DOCUMENTO_SPESA not in ( 'DICHIARABILE')"));

		conditionList.add((new StringBuilder()).append("pbandi_t_documento_di_spesa.id_documento_di_spesa = pbandi_r_doc_spesa_progetto.id_documento_di_spesa"));

		conditionList.add((new StringBuilder()).append("pbandi_r_doc_spesa_progetto.id_stato_documento_spesa = pbandi_d_stato_documento_spesa.id_stato_documento_spesa"));

		StringBuilder sqlSelect = (new StringBuilder("SELECT COUNT(pbandi_r_doc_spesa_progetto.ID_DOCUMENTO_DI_SPESA) " +
														"FROM ").append(tables));

		setWhereClause(conditionList, sqlSelect);
		
		int numDoc = queryForInt(sqlSelect.toString(), params);
	
		logger.debug(pr+"params="+params);
		logger.debug(pr+"sqlSelect.toString()="+sqlSelect.toString());
		logger.debug(pr+"numDoc="+numDoc);

		if (numDoc>0)
			ret = Boolean.FALSE;

		logger.debug(pr+"END");
		return ret;
	}	
	
}
