package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ArchivioFileVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiArchivioFileDAOImpl extends PbandiDAO {
	
	public PbandiArchivioFileDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	public List<ArchivioFileVO> getFilesAssociatedDocDiSpesa(
			BigDecimal idDocumentoDiSpesa, BigDecimal idProgetto) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");

		sqlSelect
				.append("SELECT DOCIND.id_Entita as idEntita,DOCIND.id_target as idTarget,DOCIND.id_progetto idProgetto,DOCIND.num_protocollo numProtocollo")
				.append(",ID_FOLDER as idFolder,ID_DOCUMENTO_INDEX as idDocumentoIndex,PBANDI_T_FILE.NOME_FILE AS nomeFile")
				.append(",SIZE_FILE as sizeFile,PBANDI_T_FILE.ID_UTENTE_INS AS idUtenteIns")
				.append(",DT_INSERIMENTO dtInserimento,PBANDI_T_FILE.ID_UTENTE_AGG AS idUtenteAgg,DT_AGGIORNAMENTO dtAggiornamento")
				.append(",ID_STATO_DOCUMENTO_SPESA AS idStatoDocumentoSpesa")
				.append(",STATOSPESA.DESC_BREVE_STATO_DOC_SPESA as descBreveStatoDocSpesa, PBANDI_T_FILE_ENTITA.ID_INTEGRAZIONE_SPESA AS idIntegrazioneSpesa")
				.append(" FROM ")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE) ")
				.append(" JOIN")
				.append(" PBANDI_R_DOC_SPESA_PROGETTO DOCPROG on DOCPROG.ID_DOCUMENTO_DI_SPESA=PBANDI_T_FILE_ENTITA.ID_TARGET and DOCPROG.id_progetto = PBANDI_T_FILE_ENTITA.id_progetto")
				.append(" JOIN")
				.append(" PBANDI_D_STATO_DOCUMENTO_SPESA STATOSPESA  USING(ID_STATO_DOCUMENTO_SPESA)")
				.append(" where (DOCPROG.ID_DOCUMENTO_DI_SPESA=:idDocumentoDiSpesa")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_ENTITA =  (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_DOCUMENTO_DI_SPESA'))");
			
		if (idProgetto != null)
			sqlSelect.append(" AND PBANDI_T_FILE_ENTITA.id_progetto = :idProgetto ");
		
		sqlSelect.append(" order by  ID_DOCUMENTO_INDEX");

		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
		if (idProgetto != null)
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = query(sqlSelect.toString(), params,
				new ArchivioFileVORowMapper());

		return result;
	}

	public List<ArchivioFileVO> getFilesAssociatedAffidamento(BigDecimal idAppalto) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT DOCIND.id_Entita as idEntita,DOCIND.id_target as idTarget,DOCIND.id_progetto idProgetto,DOCIND.num_protocollo numProtocollo")
				.append(",ID_FOLDER as idFolder,ID_DOCUMENTO_INDEX as idDocumentoIndex,PBANDI_T_FILE.NOME_FILE AS nomeFile")
				.append(",SIZE_FILE as sizeFile,PBANDI_T_FILE.ID_UTENTE_INS AS idUtenteIns")
				.append(",DT_INSERIMENTO dtInserimento,PBANDI_T_FILE.ID_UTENTE_AGG AS idUtenteAgg,DT_AGGIORNAMENTO dtAggiornamento")
				.append(",PBANDI_T_FILE_ENTITA.ID_FILE_ENTITA as idFileEntita")
				.append(" FROM ")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE) ")
				.append(" where (PBANDI_T_FILE_ENTITA.ID_TARGET = :idAppalto")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_APPALTO'))");
		sqlSelect.append(" order by  PBANDI_T_FILE_ENTITA.DT_ENTITA, nomeFile");
		// Jira PBANDI-2829: cambiato l'order by.

		params.addValue("idAppalto", idAppalto, Types.BIGINT);

		result = query(sqlSelect.toString(), params, new ArchivioFileVORowMapper());

		return result;
	}
	
	public List<ArchivioFileVO> getChecklistAssociatedAffidamento(BigDecimal idAppalto) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT PBANDI_T_DOCUMENTO_INDEX.id_Entita as idEntita, PBANDI_T_DOCUMENTO_INDEX.id_target as idTarget, PBANDI_T_DOCUMENTO_INDEX.id_progetto idProgetto, PBANDI_T_DOCUMENTO_INDEX.num_protocollo numProtocollo, PBANDI_T_DOCUMENTO_INDEX.NOME_FILE nomeFile, PBANDI_T_DOCUMENTO_INDEX.ID_DOCUMENTO_INDEX idDocumentoIndex, PBANDI_D_STATO_TIPO_DOC_INDEX.DESC_STATO_TIPO_DOC_INDEX as descStatoTipoDocIndex")
				.append(" FROM PBANDI_T_DOCUMENTO_INDEX, PBANDI_R_DOCU_INDEX_TIPO_STATO, PBANDI_D_STATO_TIPO_DOC_INDEX ")
				.append(" where PBANDI_T_DOCUMENTO_INDEX.ID_TARGET = :idAppalto")
				.append("   AND PBANDI_T_DOCUMENTO_INDEX.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_APPALTO')")
				.append("   AND PBANDI_R_DOCU_INDEX_TIPO_STATO.ID_DOCUMENTO_INDEX = PBANDI_T_DOCUMENTO_INDEX.ID_DOCUMENTO_INDEX")
				.append("   AND PBANDI_D_STATO_TIPO_DOC_INDEX.ID_STATO_TIPO_DOC_INDEX = PBANDI_R_DOCU_INDEX_TIPO_STATO.ID_STATO_TIPO_DOC_INDEX");
		sqlSelect.append(" order by  PBANDI_T_DOCUMENTO_INDEX.ID_DOCUMENTO_INDEX");

		params.addValue("idAppalto", idAppalto, Types.BIGINT);

		result = query(sqlSelect.toString(), params, new ArchivioFileVORowMapper());

		return result;
	}

	public List<ArchivioFileVO> getFilesAssociated(
			BigDecimal idTarget,String nomeEntita) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT")
				.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
				.append(" ID_FOLDER  AS IDFOLDER,")
				.append(" SIZE_FILE  AS SIZEFILE,")
				.append(" PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,")
				.append(" DT_INSERIMENTO DTINSERIMENTO,")
				.append(" PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,")
				.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
				.append(" PBANDI_T_FILE.NOME_FILE  AS NOMEFILE,")
				.append(" DOCIND.ID_ENTITA AS IDENTITA,")
				.append(" DOCIND.ID_TARGET AS IDTARGET,")
				.append(" DOCIND.ID_PROGETTO AS IDPROGETTO,")
				.append(" '' AS DESC_BREVE_STATO_DOC_SPESA, ")
				.append("  'S' as islocked")
				.append(" FROM")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE)")
				.append(" where")
				.append(" PBANDI_T_FILE_ENTITA.ID_ENTITA =  (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA=:nomeEntita)")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_TARGET = :id")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_PROGETTO IS NOT NULL") // DICH   EXIST, ID PROGETTO IS NOT NULL
				.append(" order by ID_DOCUMENTO_INDEX");
		params.addValue("nomeEntita", nomeEntita, Types.VARCHAR);
		params.addValue("id", idTarget, Types.BIGINT);
		result = query(sqlSelect.toString(), params,new ArchivioFileVORowMapper());
		return result;
	}
	public List<ArchivioFileVO> getFilesAssociatedNoIntegr(
			BigDecimal idTarget,String nomeEntita) {
		List<ArchivioFileVO> result;
		MapSqlParameterSource params = new MapSqlParameterSource();

		String sqlSelect = "SELECT\n" +
				"ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,\n" +
				"ID_FOLDER AS IDFOLDER,\n" +
				"SIZE_FILE AS SIZEFILE,\n" +
				"PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,\n" +
				"DT_INSERIMENTO DTINSERIMENTO,\n" +
				"PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,\n" +
				"DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,\n" +
				"PBANDI_T_FILE.NOME_FILE AS NOMEFILE,\n" +
				"DOCIND.ID_ENTITA AS IDENTITA,\n" +
				"DOCIND.ID_TARGET AS IDTARGET,\n" +
				"DOCIND.ID_PROGETTO AS IDPROGETTO,\n" +
				"'' AS DESC_BREVE_STATO_DOC_SPESA, \n" +
				"'S' AS islocked\n" +
				"FROM PBANDI_T_FILE\n" +
				"JOIN PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)\n" +
				"JOIN PBANDI_T_FILE_ENTITA using (ID_FILE)\n" +
				"WHERE PBANDI_T_FILE_ENTITA.ID_ENTITA = \n" +
				"\t(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA= :nomeEntita)\n" +
				"AND PBANDI_T_FILE_ENTITA.ID_TARGET = :id\n" +
				"AND PBANDI_T_FILE_ENTITA.ID_PROGETTO IS NOT NULL \n" +
				"AND PBANDI_T_FILE_ENTITA.ID_INTEGRAZIONE_SPESA IS NULL\n" +
				"ORDER BY ID_DOCUMENTO_INDEX";
		params.addValue("nomeEntita", nomeEntita, Types.VARCHAR);
		params.addValue("id", idTarget, Types.BIGINT);
		result = query(sqlSelect, params,new ArchivioFileVORowMapper());
		return result;
	}
	
	/*PBANDI_T_RICHIESTA_EROGAZIONE
	PBANDI_T_DICHIARAZIONE_SPESA*/
	public List<ArchivioFileVO> getFilesAssociatedByIdProgetto(
			BigDecimal idProgetto,String nomeEntita) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT")
				.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
				.append(" ID_FOLDER  AS IDFOLDER,")
				.append(" SIZE_FILE  AS SIZEFILE,")
				.append(" PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,")
				.append(" DT_INSERIMENTO DTINSERIMENTO,")
				.append(" PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,")
				.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
				.append(" PBANDI_T_FILE.NOME_FILE  AS NOMEFILE,")
				.append(" DOCIND.ID_ENTITA AS IDENTITA,")
				.append(" DOCIND.ID_TARGET AS IDTARGET,")
				.append(" '' AS DESC_BREVE_STATO_DOC_SPESA")
				.append(" FROM")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE)")
				.append(" where")
				.append(" PBANDI_T_FILE_ENTITA.ID_ENTITA =  (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA= :nomeEntita)")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_TARGET = :idProgetto")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_PROGETTO IS NULL") // DICH or EROG DOESN'T YET EXIST,IT'A PRE ASSOCIATION. ASSUME THAT ID PROGETTO IS NULL
				.append(" order by ID_DOCUMENTO_INDEX");

		params.addValue("nomeEntita", nomeEntita, Types.VARCHAR);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = query(sqlSelect.toString(), params,new ArchivioFileVORowMapper());
		return result;
	}
	
	// Jira PBANDI-2890: nuovo campo PBANDI_T_FILE_ENTITA.DT_ASSOCIAZIONE.
	public List<ArchivioFileVO> getFilesAssociatedFornitoriOrQualifiche(
			BigDecimal idEntita, BigDecimal idTarget, BigDecimal idProgetto) {
		
		String pr = "[PbandiArchivioFileDAOImpl::getFilesAssociatedFornitoriOrQualifiche]";
		logger.debug(pr + "BEGIN");
		
		// PK : questa viene eseguita per ogni fornitore trovato ed estrae i documenti del fornitore....
		
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT")
				.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
				.append(" ID_FOLDER  AS IDFOLDER,")
				.append(" SIZE_FILE  AS SIZEFILE,")
				.append(" PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,")
				.append(" DT_INSERIMENTO DTINSERIMENTO,")
				.append(" PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,")
				.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
				.append(" PBANDI_T_FILE.NOME_FILE  AS NOMEFILE,")
				.append(" DOCIND.ID_ENTITA AS IDENTITA,")
				.append(" DOCIND.ID_TARGET AS IDTARGET,")
				.append(" PBANDI_T_FILE_ENTITA.ID_PROGETTO AS IDPROGETTO,")
				.append(" PBANDI_T_FILE_ENTITA.DT_ASSOCIAZIONE AS DTASSOCIAZIONE,")				
				.append(" '' AS DESC_BREVE_STATO_DOC_SPESA")
				.append(" FROM")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE)")
				.append(" where")
				.append(" PBANDI_T_FILE_ENTITA.ID_ENTITA = :idEntita")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_TARGET = :idTarget");
				if(idProgetto != null)
					sqlSelect.append(" AND PBANDI_T_FILE_ENTITA.ID_PROGETTO = :idProgetto");
					
				sqlSelect.append(" order by ID_DOCUMENTO_INDEX");

		params.addValue("idEntita", idEntita, Types.BIGINT);
		params.addValue("idTarget", idTarget, Types.BIGINT);
		if(idProgetto != null)
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = query(sqlSelect.toString(), params,new ArchivioFileVORowMapper());
			
		logger.debug(pr + "END");
		return result;
	}
	
	
	
	public List<ArchivioFileVO> getFilesAssociatedPagamento(
			BigDecimal idPagamento) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");

		sqlSelect
				.append("SELECT")
				.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
				.append(" ID_FOLDER  AS IDFOLDER,")
				.append(" SIZE_FILE  AS SIZEFILE,")
				.append(" PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,")
				.append(" DT_INSERIMENTO DTINSERIMENTO,")
				.append(" PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,")
				.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
				.append(" PBANDI_T_FILE.NOME_FILE  AS NOMEFILE,")
				.append(" DOCIND.ID_ENTITA AS IDENTITA,")
				.append(" DOCIND.ID_PROGETTO AS IDPROGETTO,")
				.append(" DOCIND.ID_TARGET AS IDTARGET,")
				.append(" '' AS DESC_BREVE_STATO_DOC_SPESA, PBANDI_T_FILE_ENTITA.ID_INTEGRAZIONE_SPESA AS idIntegrazioneSpesa")
				.append(" FROM")
				.append(" PBANDI_T_FILE")
				.append(" JOIN")
				.append(" PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" JOIN")
				.append(" PBANDI_T_FILE_ENTITA using (ID_FILE) ")
				.append(" JOIN")
				.append(" PBANDI_R_PAGAMENTO_DOC_SPESA PAGDOC")
				.append(" ON PBANDI_T_FILE_ENTITA.ID_TARGET = PAGDOC.ID_PAGAMENTO ")			
				.append(" where PAGDOC.ID_PAGAMENTO =:idPagamento ")
				.append(" AND PBANDI_T_FILE_ENTITA.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_PAGAMENTO')")
				.append(" order by  ID_DOCUMENTO_INDEX");

		params.addValue("idPagamento", idPagamento, Types.BIGINT);
		result = query(sqlSelect.toString(), params,new ArchivioFileVORowMapper());

		return result;
	}

	private class ArchivioFileVORowMapper implements
			RowMapper<ArchivioFileVO> {

		public ArchivioFileVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			ArchivioFileVO vo = (ArchivioFileVO) beanMapper.toBean(rs,
					ArchivioFileVO.class);
			return vo;
		}
	}

	public int associateAllegatiToDichiarazione(Long idDichiarazione,
			  Long idProgetto) {
		int result = 0;
		StringBuilder sqlUpdate = new StringBuilder("");
		// where idPRogetto is null && id_entita=t_dicha and id_target= id_progetto
		sqlUpdate
				.append("UPDATE PBANDI_T_FILE_ENTITA  ")
				.append(" SET ID_TARGET=:idDichiarazione , id_progetto=:idProgetto ")
				.append(" WHERE  ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_DICHIARAZIONE_SPESA') ")
				.append(" and id_progetto is NULL and id_target=:idProgetto");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDichiarazione", idDichiarazione, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = modificaConRitorno(sqlUpdate.toString(), params);
		logger.info("modified record: "+result);
		return result;
	}

	 
	public int dissociateArchivioFilePagamenti(Long idUtente,
			Long idDocumentoDiSpesa, Long idProgetto) {
		int result = 0;
		StringBuilder sqlDelete= new StringBuilder("delete from ");
		sqlDelete.append("PBANDI_T_FILE_ENTITA where ")
		.append("ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_PAGAMENTO') ")
		.append("and  ID_TARGET in (  select ID_PAGAMENTO from ")
		.append("pbandi_r_pagamento_doc_spesa where id_documento_di_spesa = :idDocumentoDiSpesa)")
		;
		 
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.BIGINT);
		result = modificaConRitorno(sqlDelete.toString(), params);

		return result;
	}


 

	public List<ArchivioFileVO> getFilesAssociatedProgetto(BigDecimal idProgetto) {
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");

		sqlSelect
				.append("SELECT")
				.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
				.append(" ID_FOLDER  AS IDFOLDER,")
				.append(" SIZE_FILE  AS SIZEFILE,")
				.append(" PBANDI_R_FOLDER_FILE_DOC_INDEX.ID_UTENTE_INS AS IDUTENTEINS,")
				.append(" DT_INSERIMENTO DTINSERIMENTO,")
				.append(" PBANDI_R_FOLDER_FILE_DOC_INDEX.ID_UTENTE_AGG AS IDUTENTEAGG,")
				.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
				.append(" PBANDI_R_FOLDER_FILE_DOC_INDEX.NOME_FILE  AS NOMEFILE,")
				.append(" DOCIND.ID_ENTITA AS IDENTITA,")
				.append(" DOCIND.ID_TARGET AS IDTARGET")
				.append(" FROM PBANDI_R_FOLDER_FILE_DOC_INDEX")
				.append(" JOIN PBANDI_T_DOCUMENTO_INDEX DOCIND using (ID_DOCUMENTO_INDEX)")
				.append(" WHERE DOCIND.ID_TARGET = :idProgetto ")
				.append(" AND DOCIND.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_PROGETTO')")
				.append(" order by  ID_DOCUMENTO_INDEX");

		params.addValue("idProgetto", idProgetto, Types.BIGINT);

		result = query(sqlSelect.toString(), params,
				new ArchivioFileVORowMapper());

		return result;
	}

	public int associateAllegatiToRichiestaErogazione(
			BigDecimal idRichiestaErogazione, BigDecimal idProgetto) {
		int result = 0;
		StringBuilder sqlUpdate = new StringBuilder("");
		sqlUpdate
				.append("UPDATE PBANDI_T_FILE_ENTITA  ")
				.append(" SET ID_TARGET=:idRichiestaErogazione , id_progetto=:idProgetto ")
				.append(" WHERE  ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_RICHIESTA_EROGAZIONE') ")
				.append(" and id_progetto is NULL and id_target=:idProgetto");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idRichiestaErogazione", idRichiestaErogazione, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = modificaConRitorno(sqlUpdate.toString(), params);
		logger.info("modified record: "+result);
		return result;
	}

//	NON PIU USATO: ALLEGATI SEMPRE ASSOCIATI ALLA DS
//	public int associateAllegatiToComunicazioneFineProgetto(
//			BigDecimal idComunicazioneFineProgetto, BigDecimal idProgetto) {
//		int result = 0;
//		StringBuilder sqlUpdate = new StringBuilder("");
//		sqlUpdate
//				.append("UPDATE PBANDI_T_FILE_ENTITA  ")
//				.append(" SET ID_TARGET=:idComunicazioneFineProgetto , id_progetto=:idProgetto ")
//				.append(" WHERE  ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_COMUNICAZ_FINE_PROG') ")
//				.append(" and id_progetto is NULL and id_target=:idProgetto");
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		params.addValue("idComunicazioneFineProgetto", idComunicazioneFineProgetto, Types.BIGINT);
//		params.addValue("idProgetto", idProgetto, Types.BIGINT);
//		result = modificaConRitorno(sqlUpdate.toString(), params);
//		logger.info("modified record: "+result);
//		return result;
//	}

	public int associateAllegatiToContoEconomico(
			BigDecimal idContoEconomico, BigDecimal idProgetto) {
		int result = 0;
		StringBuilder sqlUpdate = new StringBuilder("");
		sqlUpdate
				.append("UPDATE PBANDI_T_FILE_ENTITA  ")
				.append(" SET ID_TARGET=:idContoEconomico , id_progetto=:idProgetto ")
				.append(" WHERE  ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_CONTO_ECONOMICO') ")
				.append(" and id_progetto is NULL and id_target=:idProgetto");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idContoEconomico", idContoEconomico, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		result = modificaConRitorno(sqlUpdate.toString(), params);
		logger.info("modified record: "+result);
		return result;
	}
	
	public List<ArchivioFileVO> getFiles(BigDecimal idTarget,BigDecimal idEntita) {
		 
			List<ArchivioFileVO> result = null;
			MapSqlParameterSource params = new MapSqlParameterSource();

			StringBuilder sqlSelect = new StringBuilder("");
			sqlSelect
					.append(" SELECT")
					.append(" ID_DOCUMENTO_INDEX as IDDOCUMENTOINDEX,")
					.append(" ID_FOLDER  AS IDFOLDER,")
					.append(" SIZE_FILE  AS SIZEFILE,")
					.append(" PBANDI_T_FILE.ID_UTENTE_INS AS IDUTENTEINS,")
					.append(" DT_INSERIMENTO DTINSERIMENTO,")
					.append(" PBANDI_T_FILE.ID_UTENTE_AGG AS IDUTENTEAGG,")
					.append(" DT_AGGIORNAMENTO AS DTAGGIORNAMENTO,")
					.append(" PBANDI_T_FILE.NOME_FILE  AS NOMEFILE,")
					.append(" PBANDI_T_FILE_ENTITA.ID_ENTITA AS IDENTITA,")
					.append(" PBANDI_T_FILE_ENTITA.ID_TARGET AS IDTARGET,")
					.append(" PBANDI_T_FILE_ENTITA.FLAG_ENTITA AS FLAGENTITA")	// Jira PBANDI-2815
					.append(" FROM")
					.append(" PBANDI_T_FILE")
					.append(" JOIN")
					.append(" PBANDI_T_FILE_ENTITA using (ID_FILE)")
					.append(" where")
					.append(" PBANDI_T_FILE_ENTITA.ID_ENTITA = :idEntita")
					.append(" AND PBANDI_T_FILE_ENTITA.ID_TARGET = :idTarget")
					.append(" AND PBANDI_T_FILE_ENTITA.ID_PROGETTO IS NOT NULL") // DICH   EXIST, ID PROGETTO IS NOT NULL
					.append(" order by PBANDI_T_FILE.NOME_FILE");
			params.addValue("idTarget", idTarget, Types.BIGINT);
			params.addValue("idEntita", idEntita, Types.BIGINT);
			result = query(sqlSelect.toString(), params,new ArchivioFileVORowMapper());
			return result;
		}

	
}
