/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoDiSpesaManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DichiarazioneDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleConnectionWrapper;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;


public class PbandiDichiarazioneDiSpesaDAOImpl extends PbandiDAO {

	public PbandiDichiarazioneDiSpesaDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	@Autowired
	private DocumentoDiSpesaManager documentoDiSpesaManager;
	
	private AbstractDataFieldMaxValueIncrementer seqPbandiTDichSpesa;	
	
	public DocumentoDiSpesaManager getDocumentoDiSpesaManager() {
		return documentoDiSpesaManager;
	}

	public void setDocumentoDiSpesaManager(DocumentoDiSpesaManager documentoDiSpesaManager) {
		this.documentoDiSpesaManager = documentoDiSpesaManager;
	}

	public void setSeqPbandiTDichSpesa(AbstractDataFieldMaxValueIncrementer seqPbandiTDichSpesa) {
		this.seqPbandiTDichSpesa = seqPbandiTDichSpesa;
	}

	public AbstractDataFieldMaxValueIncrementer getSeqPbandiTDichSpesa() {
		return seqPbandiTDichSpesa;
	}
	
	public Long creaNuovoIdDichiarazioneDiSpesa() {
		String sqlSeq = "select SEQ_PBANDI_T_DICHIARAZIONE_SPE.nextval from dual ";
		logger.info("\n"+sqlSeq.toString());
		Long id = queryForLong(sqlSeq.toString(), new MapSqlParameterSource());
		logger.info("creaNuovoIdDichiarazioneDiSpesa(): Nuovo id SEQ_PBANDI_T_DICHIARAZIONE_SPE = "+id);
		return id;
	}

		
	public Long inserisci(DichiarazioneDiSpesaVO dichiarazioneDiSpesaVO) {


			StringBuffer sqlInsert= new StringBuffer("insert into PBANDI_T_DICHIARAZIONE_SPESA ");
			 sqlInsert.append("(")
			 .append("DT_DICHIARAZIONE")
			 .append(",ID_DICHIARAZIONE_SPESA")
			 .append(",ID_PROGETTO")
			 .append(",ID_UTENTE_INS")
			 .append(",PERIODO_AL")
			 .append(",PERIODO_DAL")
			 .append(",ID_TIPO_DICHIARAZ_SPESA")
			 .append(",TIPO_INVIO_DS")
			 .append(")");
			 sqlInsert.append("values(") 
			 .append(":dtDichiarazione")
			 .append(",:idDichiarazioneSpesa")
			 .append(",:idProgetto")
			 .append(",:idUtenteIns")
			 .append(",:periodoAl")
			 .append(",:periodoDal")
			 .append(",:idTipoDichiarazioneSpesa")
			 .append(",:tipoInvioDs")
			 .append(")");
			 MapSqlParameterSource params = new MapSqlParameterSource();
			 
		// Long idDichiarazioneSpesa= seqPbandiTDichSpesa.nextLongValue();
			 params.addValue("dtDichiarazione",dichiarazioneDiSpesaVO.getDataDichiarazioneDiSpesa(),Types.TIME);
			 params.addValue("idDichiarazioneSpesa",dichiarazioneDiSpesaVO.getIdDichiarazione(),Types.BIGINT);
			 params.addValue("idProgetto",dichiarazioneDiSpesaVO.getIdProgetto(),Types.BIGINT);
			 params.addValue("idUtenteIns",dichiarazioneDiSpesaVO.getIdUtente(),Types.BIGINT);
			 params.addValue("periodoAl", dichiarazioneDiSpesaVO.getDataFineRendicontazione(), Types.DATE);
			 params.addValue("periodoDal", dichiarazioneDiSpesaVO.getDataInizioRendicontazione(),Types.DATE);
			 params.addValue("idTipoDichiarazioneSpesa",dichiarazioneDiSpesaVO.getIdTipoDichiarazSpesa());
			 params.addValue("tipoInvioDs",dichiarazioneDiSpesaVO.getTipoInvioDs());
			 inserisci(sqlInsert.toString(), params);
			 return dichiarazioneDiSpesaVO.getIdDichiarazione();
			 
		}
	
	
	
	public void inserisciDocumentoPagamentiDichiarazione(BigDecimal idDichiarazione, BigDecimal idDocumento, List<BigDecimal> idPagamenti) throws SQLException {
		String sql = "insert into pbandi_s_dich_doc_spesa (" +
				"ID_DICHIARAZIONE_SPESA," +
				"ID_DOCUMENTO_DI_SPESA," +
				"ID_PAGAMENTO_NT) VALUES (" +
				":idDichiarazione," +
				":idDocumento," +
				":arrayPagamenti" +
				")";
	/*	
		ArrayDescriptor arrayDescriptor =  null;
		oracle.sql.ARRAY array = null;
		try {
			arrayDescriptor = ArrayDescriptor.createDescriptor("T_NUMBER_ARRAY", getDataSource().getConnection());
		    array = new oracle.sql.ARRAY(arrayDescriptor, getDataSource().getConnection(), idPagamenti.toArray(new BigDecimal []{}));
		
		} catch (SQLException e) {
			throw e;
		}
		*/
//		ArrayDescriptor arrayDescriptor =  null;
		oracle.sql.ARRAY array = null;
//		org.jboss.resource.adapter.jdbc.WrappedConnection vendorConnection =null;
		
		OracleConnection connection = null;
		OracleConnectionWrapper ocw = null;
		OracleConnection oc = null;
		
		try {
			
//			 vendorConnection = (org.jboss.resource.adapter.jdbc.WrappedConnection) getDataSource().getConnection();
//			 arrayDescriptor = ArrayDescriptor.createDescriptor("T_NUMBER_ARRAY", vendorConnection.getUnderlyingConnection());
//			 array = new oracle.sql.ARRAY(arrayDescriptor, vendorConnection.getUnderlyingConnection(), idPagamenti.toArray(new BigDecimal []{}));
		
			 logger.warn("\n\n TODO POCHETTINO ....... OracleConnectionWrapper funziona??????\n\n");
			//connection = (OracleConnection) DriverManager.getConnection( "url", "username", "password");
			connection = (OracleConnection) getDataSource().getConnection();
			ocw = new OracleConnectionWrapper((OracleConnection) connection);
			oc = ocw.unwrap();
			array = (ARRAY) oc.createArrayOf("T_NUMBER_ARRAY", idPagamenti.toArray(new BigDecimal []{}));
				
		} catch (SQLException e) {
			logger.error("error in getting vendorConnection for Array : "+e.getMessage(),e);
			throw e;
		}finally{
			try{
				logger.info("closing vendorConnection ...");
//				vendorConnection.close();
				oc.close();
				ocw.close();
				connection.close();
			}catch (Exception e) {
				logger.error("error in closing vendorConnection : "+e.getMessage(),e);
			}
		}
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);
		params.addValue("idDocumento", idDocumento, Types.NUMERIC);
		params.addValue("arrayPagamenti", array, oracle.jdbc.OracleTypes.ARRAY);
		
		inserisci(sql, params);
		
		
		
	}
	
	public void inserisciDocumentoPagamentiDichiarazioneCultura(BigDecimal idDichiarazione, BigDecimal idDocumento, List<BigDecimal> idPagamenti) throws Exception {
		
		if (idPagamenti == null) {
			logger.info("inserisciDocumentoPagamentiDichiarazioneCultura(): idPagamenti = NULL");
		} else {
			for (BigDecimal p : idPagamenti) { 
				logger.info("inserisciDocumentoPagamentiDichiarazioneCultura(): idPagamento = "+p.longValue());
			}
		}
				
		try {
			
			oracle.sql.ARRAY oracleArray = null;
			
			logger.info("VERSIONE INSTALLATA: CREO la connessione oracle.");
			
			Connection connection = getDataSource().getConnection();
			
			OracleConnection oracleConnection = (OracleConnection) 
					connection.getClass().getMethod("getUnderlyingConnection").invoke(connection);
			
			logger.info("ESEGUO createDescriptor()");
			ArrayDescriptor arrayDescriptor;
			arrayDescriptor = ArrayDescriptor.createDescriptor("T_NUMBER_ARRAY", oracleConnection);	// java.sql.Connection arg1
			
			logger.info("POPOLO oracleArray");
			oracleArray = new oracle.sql.ARRAY(arrayDescriptor, oracleConnection, idPagamenti.toArray(new BigDecimal []{}));
			
			try{
				logger.info("chiusura connessione ...");				
				connection.close();
				logger.info("chiusura connessione terminata ...");
			}catch (Exception e) {
				logger.error("inserisciDocumentoPagamentiDichiarazione(): ERRORE su oracleConnection.close() : "+e.getMessage(),e);
			}
			
			// ===========================================================================
			
			
			String sql = "insert into pbandi_s_dich_doc_spesa (" +
					"ID_DICHIARAZIONE_SPESA," +
					"ID_DOCUMENTO_DI_SPESA," +
					"ID_PAGAMENTO_NT) VALUES ( :idDichiarazione, :idDocumento, :arrayPagamenti )";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);
			params.addValue("idDocumento", idDocumento, Types.NUMERIC);
			params.addValue("arrayPagamenti", oracleArray, oracle.jdbc.OracleTypes.ARRAY);
			
			inserisci(sql, params);
			
		} catch (SQLException e) {
			logger.info("ERRORE: "+e);
			throw e;
		} catch (Exception e) {
			logger.info("ERRORE: "+e);
			throw e;
		}
	}


		
	
	
	
	
	
	
	
	
	
	/**
	 * GESTIONE VITTORIO
	 */

	
	
	public PbandiRFornitoreQualificaVO findQualificaFornitore (Long idFornitore){
			PbandiRFornitoreQualificaVO result = new PbandiRFornitoreQualificaVO();;
			
			StringBuilder sqlSelect = new StringBuilder("SELECT ID_FORNITORE as idFornitore,")
												.append("ID_QUALIFICA as idQualifica,")
											//	.append("MONTE_ORE as monteOre,")
												.append("COSTO_ORARIO as costoOrario,")
											//	.append("COSTO_RISORSA as costoRisorsa,")
												.append("PROGR_FORNITORE_QUALIFICA as progrFornitoreQualifica");
			StringBuilder tables = new StringBuilder("PBANDI_R_FORNITORE_QUALIFICA");
			sqlSelect.append(" FROM ").append(tables);
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("ID_FORNITORE = :idFornitore"));
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idFornitore", idFornitore, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			
			result = queryForObject(sqlSelect.toString(), params, new PbandiRFornitoreQualificaVORowMapper());
			
			return result;
			
			
		 
	}
	
	public void inserisciPagamentoRDichiarazioneSpesa(List<Long> idDocumentiDiSpesa, 
			Long idProgetto, List<String> statiAmmessiPagamento, 
			Long idDichiarazioneDiSpesa) 
	{

		StringBuilder sqlInsert = new StringBuilder(
				"insert into PBANDI_R_PAGAMENTO_DICH_SPESA ")
				.append(componiSelectPagamentiDichiarati(idDocumentiDiSpesa,
						statiAmmessiPagamento));

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);

		inserisci(sqlInsert.toString(), params);
	}
	
	/*
	 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
	public void modificaStatoValidazione(List<Long> idDocumentiDiSpesa,
			Long idProgetto, List<String> statiAmmessiPagamento,
			Long idDichiarazioneDiSpesa, Long idStatoValidazioneSpesa) {
		getLogger().begin();
		
		StringBuilder sqlUpdate = new StringBuilder(
		"update PBANDI_T_PAGAMENTO tp set" +
		" ID_STATO_VALIDAZIONE_SPESA=:idStatoValidazioneSpesa" +
		" where tp.id_pagamento in (select id_pagamento from (")
		.append(componiSelectPagamentiDichiarati(idDocumentiDiSpesa,
				statiAmmessiPagamento)).append("))");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa, Types.BIGINT);
		params.addValue("idProgetto", idProgetto, Types.BIGINT);
		params.addValue("idStatoValidazioneSpesa", idStatoValidazioneSpesa,Types.BIGINT);

		Boolean b = modifica(sqlUpdate.toString(), params);
		getLogger().debug("record modificati? " + b);
		getLogger().end();
	}
	*/


	private StringBuilder componiSelectPagamentiDichiarati(
			List<Long> idDocumentiDiSpesa, List<String> statiAmmessiPagamento) {
		StringBuilder tables = (new StringBuilder(
				"PBANDI_R_PAGAMENTO_DOC_SPESA,PBANDI_T_PAGAMENTO, PBANDI_R_DOC_SPESA_PROGETTO "));

		List<StringBuilder> conditionList = new ArrayList<StringBuilder>();

		StringBuilder listIdDoc = new StringBuilder();
		for (Long idDoc : idDocumentiDiSpesa) {
			listIdDoc.append(idDoc + ",");
		}

		/*
		 * I pagamenti ammessi sono quelli in stato INSERITO o RESPINTO
		 */
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento.
		if(!isEmpty(statiAmmessiPagamento)){
			String x="PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA in (";
			for (String statoPagamento : statiAmmessiPagamento) {
				x+="'"+statoPagamento+"',";	
			}
			x=x.substring(0,x.length()-1);
			x+=")";
			tables.append(",PBANDI_D_STATO_VALIDAZ_SPESA");
			conditionList .add((new StringBuilder()).append(x));
			conditionList.add((new StringBuilder()).append("PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA=PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA"));
		}
		*/
		
		conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO=PBANDI_T_PAGAMENTO.ID_PAGAMENTO"));
		conditionList.add((new StringBuilder()).append(
				"PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA  IN ( ").append(
				listIdDoc.substring(0, listIdDoc.lastIndexOf(","))).append(
				" )"));
		conditionList.add((new StringBuilder()).append("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO = :idProgetto"));
		conditionList.add((new StringBuilder()).append("PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA"));
		 

	
		StringBuilder sqlInsert = (new StringBuilder(
				"SELECT DISTINCT " +
				" PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO," +
				" :idDichiarazioneDiSpesa id_dichiarazione_spesa"));
		sqlInsert.append(" FROM ").append(tables);
		setWhereClause(conditionList, sqlInsert, tables);
		return sqlInsert;
	}	

	
	private class PbandiRFornitoreQualificaVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRFornitoreQualificaVO.class);
			 return vo;
		}
	}
	
	public  List<VoceDiCostoVO> findVociDiCostoForDichiarazioneDiSpesa(Long idProgetto, Date al, List<Long> idDocumentiValidi){
		
		
		
		
		StringBuilder selectPagDocProgetto = new StringBuilder("select rpagdoc.id_documento_di_spesa,") 
											           .append("	   rpagdoc.id_pagamento,")
											           .append("	   docPrj.id_progetto,")
											           .append("	   doc.dt_emissione_documento");
		
		StringBuilder tablesPagDocProgetto = new StringBuilder("	PBANDI_T_PAGAMENTO pag,")
													   .append("	PBANDI_R_PAGAMENTO_DOC_SPESA rPagDoc,")
													   .append("	PBANDI_T_DOCUMENTO_DI_SPESA doc, ")
													   .append("	PBANDI_R_DOC_SPESA_PROGETTO docPrj ");
													   
		
		selectPagDocProgetto.append(" FROM ").append(tablesPagDocProgetto);
		
		List<StringBuilder> conditionListPagDocProgetto = new ArrayList<StringBuilder>();
		/*
		 * FIX PBANDI-2314. Non esiste piu' lo stato del pagamento
		conditionListPagDocProgetto.add(new StringBuilder("pag.id_stato_validazione_spesa IN ( " +
				" select id_stato_validazione_spesa "+
                "   from PBANDI_D_STATO_VALIDAZ_SPESA " +
                "  where desc_breve_stato_validaz_spesa   IN ('I','T') "+
              	" )"));
        */
		conditionListPagDocProgetto.add(new StringBuilder("docPrj.id_progetto = :idProgetto"));
		conditionListPagDocProgetto.add(new StringBuilder("docPrj.id_documento_di_spesa = rpagdoc.id_documento_di_spesa"));
		conditionListPagDocProgetto.add(new StringBuilder("doc.dt_emissione_documento <= :dataAl"));
		conditionListPagDocProgetto.add(new StringBuilder("pag.id_pagamento = rpagdoc.id_pagamento"));
		conditionListPagDocProgetto.add(new StringBuilder("doc.id_documento_di_spesa = rpagdoc.id_documento_di_spesa"));
		/*
		 * TODO: Gestire i documenti validi
		 */
		conditionListPagDocProgetto.add(new StringBuilder("doc.id_documento_di_spesa IN ("+idDocumentiValidi+")"));
		
		setWhereClause(conditionListPagDocProgetto, selectPagDocProgetto);
		
		
		
		
		StringBuilder selectVoci = new StringBuilder("select distinct vds.id_voce_di_spesa, ")
				.append("vds.desc_voce_di_spesa, ")
				.append("vds.id_voce_di_spesa_padre, ")
				.append("case when vds.id_voce_di_spesa_padre is not null " +
						"	 then (select desc_voce_di_spesa from PBANDI_D_VOCE_DI_SPESA where id_voce_di_spesa = vds.id_voce_di_spesa_padre) " +
						"end desc_voce_di_spesa_padre,")
				.append("rce.id_rigo_conto_economico, ") 
				.append("quotaparte.id_quota_parte_doc_spesa, ")
    			.append("quotaparte.id_documento_di_spesa, ")
    			.append("quotaparte.id_progetto, ")
    			.append(" quotaparte.importo_quota_parte_doc_spesa, ")
    			.append("rce.importo_ammesso_finanziamento");
		
		StringBuilder tablesVoci = new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO rce,")
				.append("PBANDI_D_VOCE_DI_SPESA vds,")
				.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA quotaParte");
		
		selectVoci.append(" FROM ").append(tablesVoci);
		
		List<StringBuilder> conditionListVoci = new ArrayList<StringBuilder>();
		conditionListVoci.add(new StringBuilder("quotaparte.id_progetto = :idProgetto"));
		conditionListVoci.add(new StringBuilder("rce.id_voce_di_spesa = vds.id_voce_di_spesa"));
		conditionListVoci.add(new StringBuilder("quotaParte.id_rigo_conto_economico = rce.id_rigo_conto_economico"));
		/*
		 * TODO: Gestire i documenti
		 */
		conditionListVoci.add(new StringBuilder("quotaparte.id_documento_di_spesa in ("+idDocumentiValidi+")"));
		conditionListVoci.add(new StringBuilder("nvl(trunc(vds.DT_FINE_VALIDITA), trunc(sysdate+1)) > trunc(sysdate)"));
		
		setWhereClause(conditionListVoci, selectVoci);
		
		
		
		
		
		StringBuilder selectValidatoVoceProgetto = new StringBuilder("select rce1.id_voce_di_spesa,")
				.append("quotaParte1.id_progetto, ")
				.append("sum(pagQuotaParteDocSp1.importo_validato) as importo_validato "); 
		
		StringBuilder tablesValidatoVoceProgetto = new StringBuilder("PBANDI_T_RIGO_CONTO_ECONOMICO rce1,")
				.append("PBANDI_T_QUOTA_PARTE_DOC_SPESA quotaParte1, ")
				.append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQuotaParteDocSp1 ");
		
		selectValidatoVoceProgetto.append(" FROM ").append(tablesValidatoVoceProgetto);
		
		
		List<StringBuilder> conditionListValidatoVoceProgetto = new ArrayList<StringBuilder>();
		conditionListValidatoVoceProgetto.add(new StringBuilder("rce1.id_conto_economico in ( "+
                                  "  select ce.id_conto_economico "+
                                  "   from pbandi_t_conto_economico ce, "+
                                  "        pbandi_d_stato_conto_economico sce, "+
                                  "        pbandi_d_tipologia_conto_econ tipoce, "+
                                  "        pbandi_t_progetto p "+
                                  "  where ce.id_stato_conto_economico = sce.id_stato_conto_economico "+
                                  "    and sce.id_tipologia_conto_economico = tipoce.id_tipologia_conto_economico "+
                                  "    and tipoce.desc_breve_tipologia_conto_eco = 'MASTER' "+
                                  "    and ce.id_domanda = p.id_domanda "+
                                  "    and p.id_progetto = :idProgetto "+
								  " )"));
		conditionListValidatoVoceProgetto.add(new StringBuilder("quotaParte1.id_progetto = :idProgetto"));
		conditionListValidatoVoceProgetto.add(new StringBuilder("rce1.id_rigo_conto_economico = quotaParte1.id_rigo_conto_economico"));
		conditionListValidatoVoceProgetto.add(new StringBuilder("pagQuotaParteDocSp1.id_quota_parte_doc_spesa = quotaParte1.id_quota_parte_doc_spesa"));
		
		setWhereClause(conditionListValidatoVoceProgetto, selectValidatoVoceProgetto);
		
		selectValidatoVoceProgetto.append(" group by rce1.id_voce_di_spesa,quotaParte1.id_progetto ");
		
		
		StringBuilder selectComplessiva = new StringBuilder("select sum(rpagQuotaParte.importo_quietanzato) as importo_quietanzato, ")
				.append("sum(voci.importo_quota_parte_doc_spesa) as importo_rendicontato, ")
				.append("validatoVoceProgetto.importo_validato, ")
				.append(" voci.importo_ammesso_finanziamento, ")
				.append("validatoVoceProgetto.id_progetto, ")
				.append("voci.id_voce_di_spesa, ")
				.append("voci.desc_voce_di_spesa, ")
				.append("voci.id_voce_di_spesa_padre, ")
				.append("voci.desc_voce_di_spesa_padre");
		
		
		StringBuilder tablesComplessiva = new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP rpagQuotaParte,")
		.append("("+selectPagDocProgetto+") pagDocProgetto,")
		.append("("+selectVoci+") voci,")
		.append("("+selectValidatoVoceProgetto+") validatoVoceProgetto");
		
		selectComplessiva.append(" FROM ").append(tablesComplessiva);
		
		List<StringBuilder> conditionListComplessiva = new ArrayList<StringBuilder>();
		conditionListComplessiva.add(new StringBuilder("rpagQuotaParte.id_pagamento = pagDocProgetto.id_pagamento"));
		conditionListComplessiva.add(new StringBuilder("voci.id_quota_parte_doc_spesa = rpagQuotaParte.id_quota_parte_doc_spesa"));
		conditionListComplessiva.add(new StringBuilder("voci.id_progetto = validatoVoceProgetto.id_progetto"));
		conditionListComplessiva.add(new StringBuilder("voci.id_progetto = pagDocProgetto.id_progetto"));
		conditionListComplessiva.add(new StringBuilder("voci.id_voce_di_spesa = validatoVoceProgetto.id_voce_di_spesa"));
		
		setWhereClause(conditionListComplessiva, selectComplessiva);
		
		selectComplessiva.append(" group by voci.importo_ammesso_finanziamento," + 
		        "validatoVoceProgetto.importo_validato," +
		        "validatoVoceProgetto.id_progetto," +
		        "voci.id_voce_di_spesa," +
		        "voci.desc_voce_di_spesa," +
		        "voci.id_voce_di_spesa_padre," +
		        "voci.desc_voce_di_spesa_padre");
		selectComplessiva.append(" order by voci.desc_voce_di_spesa_padre,voci.desc_voce_di_spesa");
		return null;
	}
	
	public static void main (String [] args) {
		System.out.println("\u007E");
	}
	
	
	
	public List<VoceDiCostoVO> findVociDiCostoForRicreaReport (Long idProgetto, Date al, List<Long> idDocumentiValidi){
			
			List<VoceDiCostoVO> result = new ArrayList<VoceDiCostoVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("dataAl", al,Types.DATE);
			params.addValue("idProgetto", idProgetto,Types.NUMERIC);
			String idDocumenti = null;
			if (idDocumentiValidi != null && idDocumentiValidi.size() > 0) {
				StringBuffer temp = new StringBuffer();
				for (Long idDocumento : idDocumentiValidi) {
					temp.append(idDocumento + ",");
				}
				idDocumenti = temp.toString().substring(0,temp.toString().lastIndexOf(","));
			}
			//params.addValue("idDocumenti", idDocumenti,Types.NUMERIC);
			
			/**
			 * Spesa quietanzata
			 */
			StringBuilder sqlSelectSpesaQuietanzata = new StringBuilder ("SELECT PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa as idVoceDiSpesa,")
														   .append("v1.desc_voce_di_spesa as descVoceDiSpesa,")
														   .append("v1.id_voce_di_spesa_padre as idVoceDiSpesaPadre,")
														   .append("v2.desc_voce_di_spesa as descVoceDiSpesaPadre,")
														   .append("PBANDI_T_RIGO_CONTO_ECONOMICO.importo_ammesso_finanziamento as importoAmmessoAFinanziamento,")
														   .append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_quietanzato as importoQuietanzato,")
														   .append("null as importoValidato");
			
			StringBuilder tablesSpesaQuietanzata = new StringBuilder("PBANDI_T_PROGETTO,")
															 .append("PBANDI_T_RIGO_CONTO_ECONOMICO,")
															 .append("PBANDI_R_DOC_SPESA_PROGETTO,")
										 					 .append("PBANDI_T_QUOTA_PARTE_DOC_SPESA,")
								 							 .append("PBANDI_D_VOCE_DI_SPESA v1,")
					 									 	 .append("PBANDI_D_VOCE_DI_SPESA v2,")
			 									 			 .append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP,")
													    	 .append("PBANDI_T_PAGAMENTO,")
// PBANDI-2314													    	 .append("PBANDI_D_STATO_VALIDAZ_SPESA,")
													    	 .append("PBANDI_T_DOCUMENTO_DI_SPESA");
			sqlSelectSpesaQuietanzata.append(" FROM ").append(tablesSpesaQuietanzata);
			
			List<StringBuilder> conditionListSpesaQuitanzata = new ArrayList<StringBuilder>();
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO                 = PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA       = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO              = PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA    = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico  = PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico"));
			conditionListSpesaQuitanzata.add(new StringBuilder("v1.id_voce_di_spesa                                     = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_quota_parte_doc_spesa = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa"));
			conditionListSpesaQuitanzata.add(new StringBuilder("v1.id_voce_di_spesa_padre                               = v2.id_voce_di_spesa(+)"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_pagamento                         = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento"));
// PBANDI-2314			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA           = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA"));
// PBANDI-2314			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA IN ('R')"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento <= :dataAl"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
			conditionListSpesaQuitanzata.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA IN ("+idDocumenti+")"));			
			
			
			setWhereClause(conditionListSpesaQuitanzata, sqlSelectSpesaQuietanzata);

			
			/**
			 * Spesa Validata
			 */
			StringBuilder sqlSelectSpesaValidata = new StringBuilder("SELECT PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa as idVoceDiSpesa,")
															 .append("v1.desc_voce_di_spesa as descVoceDiSpesa,")
															 .append("v1.id_voce_di_spesa_padre as idVoceDiSpesaPadre,")
															 .append("v2.desc_voce_di_spesa as descVoceDiSpesaPadre,")
															 .append("PBANDI_T_RIGO_CONTO_ECONOMICO.importo_ammesso_finanziamento as importoAmmessoAFinanziamento,")  
															 .append(" null as importoQuietanzato,")
															 .append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_validato  as importoValidato");
			
			StringBuilder tablesSpesaValidata = new StringBuilder("PBANDI_T_PROGETTO, ")
														  .append("PBANDI_T_RIGO_CONTO_ECONOMICO,")
														  .append("PBANDI_R_DOC_SPESA_PROGETTO,")
														  .append("PBANDI_T_QUOTA_PARTE_DOC_SPESA,")
														  .append("PBANDI_D_VOCE_DI_SPESA v1,")
														  .append("PBANDI_D_VOCE_DI_SPESA v2,")
														  .append("PBANDI_R_PAG_QUOT_PARTE_DOC_SP,")
														  .append("PBANDI_T_PAGAMENTO,")
// PBANDI-2314														  .append("PBANDI_D_STATO_VALIDAZ_SPESA,")
														  .append("PBANDI_T_DOCUMENTO_DI_SPESA");
		
			sqlSelectSpesaValidata.append(" FROM ").append(tablesSpesaValidata);
			List<StringBuilder> conditionListSpesaValidata = new ArrayList<StringBuilder>();
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.ID_PROGETTO                 = PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.ID_DOCUMENTO_DI_SPESA       = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_PROGETTO              = PBANDI_T_PROGETTO.ID_PROGETTO"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.ID_DOCUMENTO_DI_SPESA    = PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_rigo_conto_economico    = PBANDI_T_RIGO_CONTO_ECONOMICO.id_rigo_conto_economico"));
			conditionListSpesaValidata.add(new StringBuilder("v1.id_voce_di_spesa                                       = PBANDI_T_RIGO_CONTO_ECONOMICO.id_voce_di_spesa"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_quota_parte_doc_spesa   = PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa"));
			conditionListSpesaValidata.add(new StringBuilder("v1.id_voce_di_spesa_padre                                 = v2.id_voce_di_spesa(+)"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_PAGAMENTO.id_pagamento                           = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento"));
// PBANDI-2314			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_PAGAMENTO.ID_STATO_VALIDAZIONE_SPESA             = PBANDI_D_STATO_VALIDAZ_SPESA.ID_STATO_VALIDAZIONE_SPESA"));
// PBANDI-2314			conditionListSpesaValidata.add(new StringBuilder("PBANDI_D_STATO_VALIDAZ_SPESA.DESC_BREVE_STATO_VALIDAZ_SPESA IN ('V','P')"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento <= :dataAl"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA IN ("+idDocumenti+")"));
			conditionListSpesaValidata.add(new StringBuilder("PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
			
			setWhereClause(conditionListSpesaValidata, sqlSelectSpesaValidata);

			

			 /**
			  * Composizione delle query complessiva
			  */
			 /*
			  * FIX PBandi-546: Elimino la DISTINCT e modifico la UNION in UNION ALL poich� scartava le voci con importi uguali
			  */
			 StringBuilder sqlSelect = new StringBuilder("SELECT idVoceDiSpesa as idVoceDiSpesa,")
			 									.append("descVoceDiSpesa as descVoceDiSpesa,")
			 									.append("idVoceDiSpesaPadre as idVoceDiSpesaPadre,")
			 									.append("descVoceDiSpesaPadre as descVoceDiSpesaPadre,")
			 									.append("importoAmmessoAFinanziamento as importoAmmessoAFinanziamento,")
			 									.append("sum(importoQuietanzato) as importoQuietanzato,")
			 									.append("sum(importoValidato) as importoValidato");
			 
			 sqlSelect.append(" FROM ( ")
			 .append(sqlSelectSpesaQuietanzata)
			 .append(" UNION ALL ")
			 .append(sqlSelectSpesaValidata)
			 .append(" )")
			 .append(" group by (idVoceDiSpesa, descVoceDiSpesa,idVoceDiSpesaPadre,descVoceDiSpesaPadre,importoAmmessoAFinanziamento) ");
			 
			 
			 result = query(sqlSelect.toString(), params, new VoceDiCostoVORowMapper());
			 return result;
			
			
		 
	}
	
	
	/*
	 * FIX: PBandi-403
	 * Restituisco l' elenco dei rappresentanti legali
	 */
	public List<RappresentanteLegaleDichiarazioneVO> findRappresentanteLegale (Long idProgetto, Long idSoggettoRappresentante){
			
			List<RappresentanteLegaleDichiarazioneVO> result = new ArrayList<RappresentanteLegaleDichiarazioneVO>();
			MapSqlParameterSource params = new MapSqlParameterSource();	
			/*
			 * Query per ricavare l'idSoggetto del
			 * rappresentante legale
			 */
			StringBuilder sqlSelectSoggettoRapprLegale = new StringBuilder(" SELECT DISTINCT PBANDI_R_SOGGETTI_CORRELATI.id_soggetto");
			StringBuilder tablesSoggettoRapprLegale = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,")
												                .append("PBANDI_D_TIPO_ANAGRAFICA,")
												        		.append("PBANDI_R_SOGG_PROG_SOGG_CORREL,")
															    .append("PBANDI_R_SOGGETTI_CORRELATI,")
															    .append("PBANDI_D_TIPO_SOGG_CORRELATO");
			 sqlSelectSoggettoRapprLegale.append(" FROM ").append(tablesSoggettoRapprLegale);
			 List<StringBuilder> conditionListSoggettoRapprLegale = new ArrayList<StringBuilder>();
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica = 'PERSONA-FISICA'"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_R_SOGG_PROG_SOGG_CORREL.progr_soggetto_progetto = PBANDI_R_SOGGETTO_PROGETTO.progr_soggetto_progetto"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_R_SOGGETTI_CORRELATI.progr_soggetti_correlati =  PBANDI_R_SOGG_PROG_SOGG_CORREL.progr_soggetti_correlati"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_D_TIPO_SOGG_CORRELATO.desc_breve_tipo_sogg_correlato = 'Rappr. Leg.'"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_R_SOGGETTI_CORRELATI.id_tipo_soggetto_correlato = PBANDI_D_TIPO_SOGG_CORRELATO.id_tipo_soggetto_correlato"));
			 conditionListSoggettoRapprLegale.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_progetto = :idProgetto"));
			 
			 /*
			  * FIX PBandi-402. inserisco il controllo della data di fine validita
			  */
			 setWhereClause(conditionListSoggettoRapprLegale, sqlSelectSoggettoRapprLegale,tablesSoggettoRapprLegale);
			 
			 /*
			  * CASE luogo di nascita
			  */
			 StringBuilder sqlCaseLuogoNascita = new StringBuilder("CASE ")
												 .append("  WHEN PBANDI_T_PERSONA_FISICA.id_comune_italiano_nascita is not null") 
												 .append("   THEN ( SELECT PBANDI_D_COMUNE.desc_comune || '('||PBANDI_D_PROVINCIA.sigla_provincia||')'")
												 .append("          FROM PBANDI_D_COMUNE,")
												 .append("          PBANDI_D_PROVINCIA")
												 .append("          WHERE PBANDI_D_COMUNE.id_comune = PBANDI_T_PERSONA_FISICA.id_comune_italiano_nascita")
												 .append("            AND PBANDI_D_PROVINCIA.id_provincia = PBANDI_D_COMUNE.id_provincia")
												 .append("         )")
												 .append("   ELSE  ( SELECT PBANDI_D_COMUNE_ESTERO.desc_comune_estero || '(' || PBANDI_D_NAZIONE.desc_nazione || ')'")
												 .append("           FROM PBANDI_D_COMUNE_ESTERO,")
												 .append("                PBANDI_D_NAZIONE")
												 .append("           WHERE PBANDI_D_COMUNE_ESTERO.id_comune_estero = PBANDI_T_PERSONA_FISICA.id_comune_estero_nascita")
												 .append("           AND PBANDI_D_NAZIONE.id_nazione = PBANDI_T_PERSONA_FISICA.id_nazione_nascita")  
												 .append("         )")
												 .append("END as luogoNascita,");
			 
			 /*
			  * CASE Comune di residenza
			  */
			 StringBuilder sqlCaseComuneResidenza = new StringBuilder("CASE ")
			 										.append("  WHEN PBANDI_T_INDIRIZZO.id_comune is not null")
			 										.append("    THEN ( SELECT PBANDI_D_COMUNE.desc_comune")
			 										.append("           FROM PBANDI_D_COMUNE")
			 										.append("           WHERE PBANDI_D_COMUNE.id_comune = PBANDI_T_INDIRIZZO.id_comune")
			 										.append("          )")
			 										.append("    ELSE ( ")
			 										.append("          SELECT  PBANDI_D_COMUNE_ESTERO.desc_comune_estero")
			 										.append("          FROM PBANDI_D_COMUNE_ESTERO")
			 										.append("          WHERE PBANDI_D_COMUNE_ESTERO.id_comune_estero = PBANDI_T_INDIRIZZO.id_comune_estero")
			 										.append("         )")
			 										.append("  END as comuneResidenza,");
			 
			 /*
			  * Case Provincia di residenza
			  */
			 StringBuilder sqlCaseProvinciaResidenza = new StringBuilder(" CASE ")
			 										  .append("  WHEN PBANDI_T_INDIRIZZO.id_comune is not null")
			 										  .append("    THEN ( SELECT PBANDI_D_PROVINCIA.sigla_provincia")
			 										  .append("           FROM PBANDI_D_PROVINCIA")
			 										  .append("           WHERE PBANDI_D_PROVINCIA.id_provincia = PBANDI_T_INDIRIZZO.id_provincia")
			 										  .append("          )")
			 										  .append("    ELSE ( ")
			 										  .append("           SELECT  PBANDI_D_NAZIONE.desc_nazione")
			 										  .append("           FROM PBANDI_D_NAZIONE")
			 										  .append("           WHERE PBANDI_D_NAZIONE.id_nazione = PBANDI_T_INDIRIZZO.id_nazione")
			 										  .append("         )")
			 										  .append("  END as provinciaResidenza");
			 
			 
			 /*
			  * Costruizione della query complessiva
			  */
			 
			 StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT ")
					 							 .append("PBANDI_T_PERSONA_FISICA.cognome as cognome,")
			 									 .append("PBANDI_T_PERSONA_FISICA.nome as nome,")
			 									 .append("PBANDI_T_PERSONA_FISICA.id_soggetto as idSoggetto,")
	 											 .append("PBANDI_T_PERSONA_FISICA.dt_nascita as dataNascita,")
												 .append(sqlCaseLuogoNascita)
												 .append("PBANDI_T_INDIRIZZO.desc_indirizzo as indirizzoResidenza,")
												 .append("PBANDI_T_INDIRIZZO.cap as capResidenza,")
												 .append(sqlCaseComuneResidenza)
												 .append(sqlCaseProvinciaResidenza);
			 StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO, PBANDI_T_PERSONA_FISICA, PBANDI_T_INDIRIZZO");
			 
			 sqlSelect.append(" FROM ").append(tables);
			 
			 List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			 conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto in ( ")
			                              .append(sqlSelectSoggettoRapprLegale)
			                              .append(" )"));
		     conditionList.add(new StringBuilder("PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica"));
			 conditionList.add(new StringBuilder("PBANDI_T_INDIRIZZO.id_indirizzo = PBANDI_R_SOGGETTO_PROGETTO.id_indirizzo_persona_fisica"));
			 conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_progetto = :idProgetto"));
			 
			 if (idSoggettoRappresentante != null) {
				 conditionList.add(new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO.id_soggetto = :idSoggettoRappresentante"));
				 params.addValue("idSoggettoRappresentante", idSoggettoRappresentante,Types.NUMERIC);
			 }
			 
			 /*
			  * FIX PBandi-402. Inserisco il controllo della data di fine validita
			  */
			 setWhereClause(conditionList, sqlSelect, tables);
			 
			 		
			 params.addValue("idProgetto", idProgetto,Types.NUMERIC);
			 
			 result = query(sqlSelect.toString(), params, new RappresentanteLegaleVORowMapper());
			 
			 return result;
			
			
		 
	}
	
	
	public EnteAppartenenzaVO findEnteAppartenenza (Long idProgetto, String codiceTipoRuoloEnte) {
			EnteAppartenenzaVO result = new EnteAppartenenzaVO();
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_DOMANDA.progr_bando_linea_intervento,")
												.append("PBANDI_T_ENTE_COMPETENZA.desc_ente as descEnte,")
												.append("PBANDI_T_INDIRIZZO.desc_indirizzo || PBANDI_T_INDIRIZZO.civico as indirizzo,")
												.append("PBANDI_T_INDIRIZZO.cap as cap,")
												.append("PBANDI_D_COMUNE.desc_comune as comune,")
												.append("PBANDI_D_PROVINCIA.sigla_provincia as siglaProvincia");
			StringBuilder tables = new StringBuilder("PBANDI_T_PROGETTO,")
											 .append("PBANDI_T_DOMANDA,")
											 .append("PBANDI_R_BANDO_LINEA_ENTE_COMP,")
											 .append("PBANDI_T_ENTE_COMPETENZA,")
											 .append("PBANDI_T_INDIRIZZO,")
											 .append("PBANDI_D_COMUNE,")
											 .append("PBANDI_D_RUOLO_ENTE_COMPETENZA,")
											 .append("PBANDI_D_PROVINCIA");
			sqlSelect.append(" FROM ").append(tables);
			
			 List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			 conditionList.add(new StringBuilder("PBANDI_T_DOMANDA.id_domanda = PBANDI_T_PROGETTO.id_domanda"));
			 conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_ENTE_COMP.progr_bando_linea_intervento = PBANDI_T_DOMANDA.progr_bando_linea_intervento"));
			 conditionList.add(new StringBuilder(" PBANDI_T_ENTE_COMPETENZA.id_ente_competenza = PBANDI_R_BANDO_LINEA_ENTE_COMP.id_ente_competenza"));
			 conditionList.add(new StringBuilder(" PBANDI_T_INDIRIZZO.id_indirizzo = PBANDI_T_ENTE_COMPETENZA.id_indirizzo"));
			 conditionList.add(new StringBuilder(" PBANDI_D_COMUNE.id_comune = PBANDI_T_INDIRIZZO.id_comune"));
			 conditionList.add(new StringBuilder(" PBANDI_D_PROVINCIA.id_provincia = PBANDI_D_COMUNE.id_provincia"));
			 conditionList.add(new StringBuilder(" PBANDI_R_BANDO_LINEA_ENTE_COMP.id_ruolo_ente_competenza = PBANDI_D_RUOLO_ENTE_COMPETENZA.id_ruolo_ente_competenza"));
			 conditionList.add(new StringBuilder(" PBANDI_D_RUOLO_ENTE_COMPETENZA.DESC_BREVE_RUOLO_ENTE = :codiceTipoRuoloEnte"));
			 conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
			 
			 setWhereClause(conditionList, sqlSelect);
			 
			 MapSqlParameterSource params = new MapSqlParameterSource();			
			 params.addValue("idProgetto", idProgetto,Types.NUMERIC);
			 params.addValue("codiceTipoRuoloEnte", codiceTipoRuoloEnte,Types.VARCHAR);
			 
			 result = queryForObject(sqlSelect.toString(), params, new EnteAppartenenzaVORowMapper());
			 return result;
		 
	}
	
	
	public ProgettoVO findDatiProgetto (Long idProgetto, Date al,
			List<Long> idDocumentiValidi,   Long idDichiarazione) {
		 
			
			ProgettoVO result = new ProgettoVO();
			 StringBuilder sqlSelect = new StringBuilder("SELECT PBANDI_T_PROGETTO.id_progetto as idProgetto,")
			.append("PBANDI_T_PROGETTO.titolo_progetto as titoloProgetto,")
			.append("PBANDI_T_PROGETTO.cup as cup,")
			.append("PBANDI_T_PROGETTO.DT_CONCESSIONE as dtConcessione,")
			.append("PBANDI_T_PROGETTO.CODICE_VISUALIZZATO as codiceProgetto,")
			.append("PBANDI_T_PROGETTO.SOGLIA_SPESA_CALC_EROGAZIONI as sogliaSpesaCalcErogazioni,")
			.append("SUM(QUIETANZATO_PROGETTO.IMPORTO_QUIETANZATO) AS totaleQuietanzato");
			
			 StringBuilder tables = new StringBuilder(" PBANDI_T_PROGETTO,")
			 .append(" ( SELECT :idProgetto as id_progetto," +
			 		"			PBANDI_R_PAG_QUOT_PARTE_DOC_SP.IMPORTO_QUIETANZATO " +
			 		"    FROM PBANDI_R_PAGAMENTO_DICH_SPESA," +
			 		"         PBANDI_R_PAGAMENTO_DOC_SPESA," +
			 		"         PBANDI_R_DOC_SPESA_PROGETTO," +
			 		"         PBANDI_T_DOCUMENTO_DI_SPESA," +
			 		"         PBANDI_R_PAG_QUOT_PARTE_DOC_SP," +
			 		"         PBANDI_T_QUOTA_PARTE_DOC_SPESA" +
			 		"   WHERE PBANDI_R_PAGAMENTO_DICH_SPESA.id_dichiarazione_spesa = :idDichiarazione" +
			 		"     and PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento = PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento " +
			 		"     and PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa" +
			 		"     and PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto" +
			 		"     and PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa");
			 if(al!=null)
				 tables.append("     and PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento <= :al ");
			 
			 tables.append(		"     and PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento = PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento " +
			 		"     and PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_progetto = PBANDI_R_DOC_SPESA_PROGETTO.id_progetto " +
			 		"     and PBANDI_T_QUOTA_PARTE_DOC_SPESA.id_quota_parte_doc_spesa = PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_quota_parte_doc_spesa" +
			 		"  ) QUIETANZATO_PROGETTO");
			 
			 sqlSelect.append(" FROM ").append(tables);
			 
			 List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			 conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = :idProgetto"));
			 conditionList.add(new StringBuilder(" PBANDI_T_PROGETTO.id_progetto = QUIETANZATO_PROGETTO.id_progetto(+)"));
 
			 setWhereClause(conditionList, sqlSelect);
			 
			 sqlSelect.append(" GROUP BY (PBANDI_T_PROGETTO.id_progetto," +
			 		"PBANDI_T_PROGETTO.titolo_progetto,PBANDI_T_PROGETTO.cup," +
			 		"PBANDI_T_PROGETTO.DT_CONCESSIONE, PBANDI_T_PROGETTO.codice_visualizzato, PBANDI_T_PROGETTO.SOGLIA_SPESA_CALC_EROGAZIONI)");
			 
			 MapSqlParameterSource params = new MapSqlParameterSource();			
			 params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			 params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);
			 
			 if(al!=null)
				 params.addValue("al", al, Types.DATE);
			
		
			 
			 result = queryForObject(sqlSelect.toString(), params, new ProgettoVORowMapper());
			 return result;
			
		 
	}
	
	
	
	public List<DocumentoContabileVO> findDocumentiContabili (Long idProgetto, Date al, List<Long> idDocumentiValidi,  Long idDichiarazione) {
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			params.addValue("al", al, Types.DATE);
			params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);
			
		 
			
			
			List<DocumentoContabileVO> result = new ArrayList<DocumentoContabileVO>();
			
			/**
			 * Case Fornitore per i documenti non Nota di credito
			 */
			StringBuilder caseFornitoriDocumentiNo_NC_SF = new StringBuilder("CASE ")
															.append(" WHEN PBANDI_T_FORNITORE.id_tipo_soggetto = 1")
															.append("  THEN PBANDI_T_FORNITORE.cognome_fornitore || ' ' || PBANDI_T_FORNITORE.nome_fornitore")
															.append("  ELSE PBANDI_T_FORNITORE.denominazione_fornitore")
															.append(" END as destinatarioPagamento");
			
			// query for num_protocollo
			StringBuilder sqlSelectNumProtocollo = new StringBuilder("");
			 
			sqlSelectNumProtocollo.append(", CASE")
			.append(" WHEN PBANDI_T_DOCUMENTO_DI_SPESA.FLAG_ELETTRONICO='S'")
			.append(" THEN")
			.append(" (SELECT DOCIND.NUM_PROTOCOLLO")
			.append(" from PBANDI_T_DOCUMENTO_INDEX DOCIND")
			.append(" JOIN PBANDI_T_DOCUMENTO_DI_SPESA inner_doc")
			.append(" ON DOCIND.ID_TARGET = inner_doc.ID_DOCUMENTO_DI_SPESA")
			.append(" JOIN PBANDI_C_ENTITA ENT")
			.append(" ON DOCIND.ID_ENTITA           = ENT.ID_ENTITA")
			.append(" AND ENT.NOME_ENTITA           = 'PBANDI_T_DOCUMENTO_DI_SPESA'")
			.append(" WHERE DOCIND.NUM_PROTOCOLLO  IS NOT NULL")
			.append(" AND PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA=inner_doc.ID_DOCUMENTO_DI_SPESA)")
			.append(" ELSE ''")
			.append(" END AS numProtocollo ");
			/**
			 * Query per tutti di documenti tranne Nota di Credito
			 */
			
			StringBuilder sqlSelectDocumentiNo_NC_SF = new StringBuilder("")
																.append(" select  case when exists (select RPDS.ID_DICHIARAZIONE_SPESA ")
																.append(" from  PBANDI_R_PAGAMENTO_DICH_SPESA RPDS  where RPDS.ID_DICHIARAZIONE_SPESA = PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA ")
													            .append(" and RPDS.ID_PAGAMENTO = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_PAGAMENTO)  ")
													            .append(" then 'S'  ")
													            .append(" else 'N'  end is_same_dich ,")
			                                                    .append("PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa as idDocumentoDiSpesa, ")
																.append("PBANDI_R_DOC_SPESA_PROGETTO.task as task,")
																.append("PBANDI_R_DOC_SPESA_PROGETTO.tipo_invio as tipo_invio,")
																.append("PBANDI_R_DOC_SPESA_PROGETTO.importo_rendicontazione AS importo_rendicontabile,")
																.append("PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento as idPagamento,")
																.append("PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as tipoDocumento,")
																.append("PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA as descBreveTipoDocumento,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO as descDocumento,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.FLAG_ELETTRONICO as flagElettronico,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento as numeroDocumento,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento as dataDocumento,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.imponibile as imponibile,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva as importoIva,")
																.append("PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento as importoTotaleDocumento,")
																.append("PBANDI_T_PAGAMENTO.importo_pagamento as importoPagamento,")
																.append("PBANDI_T_PAGAMENTO.dt_pagamento as dataPagamento,")
																.append("PBANDI_T_PAGAMENTO.dt_valuta as dataValuta,")
																.append("PBANDI_D_MODALITA_PAGAMENTO.desc_modalita_pagamento as modalitaPagamento,")
																.append(caseFornitoriDocumentiNo_NC_SF)
																.append(sqlSelectNumProtocollo);
			
			StringBuilder tablesDocumentiNo_NC_SF = new StringBuilder(" (SELECT docprj.*" +
																	  " FROM PBANDI_R_DOC_SPESA_PROGETTO docprj," +
																	  " PBANDI_R_PAGAMENTO_DICH_SPESA pagdich," +
																	  " PBANDI_R_PAGAMENTO_DOC_SPESA pagdoc" +
																	  " WHERE docprj.id_documento_di_spesa = pagdoc.id_documento_di_spesa" +
																	  " and pagdoc.id_pagamento = pagdich.id_pagamento" +
																	  " and docprj.id_progetto = :idProgetto" +
																	  " and pagdich.id_dichiarazione_spesa = :idDichiarazione" +
																	  ") PBANDI_R_DOC_SPESA_PROGETTO, ")
													.append("PBANDI_D_STATO_DOCUMENTO_SPESA,")
													.append("PBANDI_R_PAGAMENTO_DOC_SPESA,")
													.append("PBANDI_T_DOCUMENTO_DI_SPESA,")
													.append("PBANDI_T_PAGAMENTO,")
													.append("PBANDI_D_MODALITA_PAGAMENTO,")
 													.append("PBANDI_D_TIPO_DOCUMENTO_SPESA,")
													.append("PBANDI_T_FORNITORE,")
 													.append("PBANDI_T_DICHIARAZIONE_SPESA");
			sqlSelectDocumentiNo_NC_SF.append(" FROM ").append(tablesDocumentiNo_NC_SF);
			
			List<StringBuilder> conditionListDocumentiNo_NC_SF = new ArrayList<StringBuilder>();
			conditionListDocumentiNo_NC_SF.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa = PBANDI_D_STATO_DOCUMENTO_SPESA.id_stato_documento_spesa"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_PAGAMENTO.id_pagamento = PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_D_MODALITA_PAGAMENTO.id_modalita_pagamento = PBANDI_T_PAGAMENTO.id_modalita_pagamento"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore = PBANDI_T_FORNITORE.id_fornitore (+)"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento <= :al"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_R_PAGAMENTO_DOC_SPESA.ID_DOCUMENTO_DI_SPESA"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa = :idDichiarazione"));
			conditionListDocumentiNo_NC_SF.add(new StringBuilder(" PBANDI_T_DICHIARAZIONE_SPESA.id_progetto = :idProgetto"));
 
			setWhereClause(conditionListDocumentiNo_NC_SF, sqlSelectDocumentiNo_NC_SF);
			
			/**
			 * Case modalit� di pagamento per i documenti di tipo
			 * nota di credito
			 */
			StringBuilder sqlCaseModalitaPagamentoDocumenti_NC_SF = new StringBuilder(" CASE ")
															.append(" WHEN PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA IN ('NC')")
															.append("  AND PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento is not null")
															.append("  THEN ( SELECT v1.numero_documento||' '||v1.dt_emissione_documento")
															.append("         FROM PBANDI_T_DOCUMENTO_DI_SPESA v1")
															.append("         WHERE v1.id_documento_di_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento")
															.append("        )")
															.append("  ELSE")
															.append("      null")
															.append(" END as modalitaPagamento,");
			
			/**
			 * Case Fornitori per i documenti di tipo 
			 * nota di credito
			 */
			StringBuilder slqCaseFornitoreDocumenti_NC_SF = new StringBuilder(" CASE ")
															.append("   WHEN PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore is not null AND PBANDI_T_FORNITORE.id_tipo_soggetto = 1")
															.append("     THEN PBANDI_T_FORNITORE.cognome_fornitore || ' ' || PBANDI_T_FORNITORE.nome_fornitore")
															.append("   WHEN PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore is not null AND PBANDI_T_FORNITORE.id_tipo_soggetto = 2")
															.append("     THEN PBANDI_T_FORNITORE.denominazione_fornitore")
															.append(" END as destinatarioPagamento");
			
			
			/**
			 * Query per il documenti di tipo Nota di credito 
			 */
			StringBuilder sqlSelectDocumenti_NC_SF = new StringBuilder(" SELECT DISTINCT '', PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa as idDocumentoDiSpesa,")
					                                           .append("PBANDI_R_DOC_SPESA_PROGETTO.task as task,")
					                                           .append("PBANDI_R_DOC_SPESA_PROGETTO.tipo_invio as tipo_invio,")
			                                                   .append("PBANDI_R_DOC_SPESA_PROGETTO.importo_rendicontazione AS importo_rendicontabile,")
															   .append("null as idPagamento,")
															   .append("PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_TIPO_DOCUMENTO_SPESA as tipoDocumento,")
															   .append("PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA as descBreveTipoDocumento,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.DESC_DOCUMENTO as descDocumento,") 
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.FLAG_ELETTRONICO as flagElettronico,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.numero_documento as numeroDocumento,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento as dataDocumento,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.imponibile as imponibile,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.importo_iva as importoIva,")
															   .append("PBANDI_T_DOCUMENTO_DI_SPESA.importo_totale_documento as importoTotaleDocumento,")
															   .append("null as importoPagamento,")
															   .append("null as dataPagamento,")
															   .append("null as dataValuta,")
															   .append(sqlCaseModalitaPagamentoDocumenti_NC_SF)
															   .append(slqCaseFornitoreDocumenti_NC_SF)
															   .append(",'' as numProtocollo");
			StringBuilder tablesDocumenti_NC_SF = new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO,")
												 .append("PBANDI_D_STATO_DOCUMENTO_SPESA,")
												 .append("PBANDI_T_DOCUMENTO_DI_SPESA,")
												 .append("PBANDI_D_TIPO_DOCUMENTO_SPESA,")
												 .append("PBANDI_T_FORNITORE");
			
			sqlSelectDocumenti_NC_SF.append(" FROM ").append(tablesDocumenti_NC_SF);
			
		 
			List<StringBuilder> conditionListDocumenti_NC_SF = new ArrayList<StringBuilder>();
			conditionListDocumenti_NC_SF.add(new StringBuilder("PBANDI_R_DOC_SPESA_PROGETTO.id_stato_documento_spesa = PBANDI_D_STATO_DOCUMENTO_SPESA.id_stato_documento_spesa"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.id_documento_di_spesa = PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_D_TIPO_DOCUMENTO_SPESA.id_tipo_documento_spesa = PBANDI_T_DOCUMENTO_DI_SPESA.id_tipo_documento_spesa"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_D_TIPO_DOCUMENTO_SPESA.DESC_BREVE_TIPO_DOC_SPESA IN ('NC')"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.id_fornitore  = PBANDI_T_FORNITORE.id_fornitore (+)"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto"));
			conditionListDocumenti_NC_SF.add(new StringBuilder(" PBANDI_T_DOCUMENTO_DI_SPESA.dt_emissione_documento <= :al"));
			conditionListDocumenti_NC_SF.add(new StringBuilder("PBANDI_T_DOCUMENTO_DI_SPESA.id_doc_riferimento in ("+
								    " select distinct PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa "+
								    " from " +
								    "      PBANDI_R_PAGAMENTO_DICH_SPESA,"+
								    "      PBANDI_R_PAGAMENTO_DOC_SPESA, PBANDI_R_DOC_SPESA_PROGETTO "+
								    " where PBANDI_R_DOC_SPESA_PROGETTO.id_progetto = :idProgetto" +
								    "   and PBANDI_R_PAGAMENTO_DICH_SPESA.id_pagamento = PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento "+
								    "   and PBANDI_R_DOC_SPESA_PROGETTO.id_documento_di_spesa = PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa "+
								    "   and PBANDI_R_PAGAMENTO_DICH_SPESA.id_dichiarazione_spesa = :idDichiarazione)"));
									

			setWhereClause(conditionListDocumenti_NC_SF, sqlSelectDocumenti_NC_SF);
			
			
			
		
			 
			/**
			 * Costruzione della query complessiva
			 */
			StringBuilder sqlSelect = new StringBuilder("SELECT is_same_dich,idDocumentoDiSpesa as idDocumentoDiSpesa, task as task, tipo_invio as tipoInvio, importo_rendicontabile as importoRendicontabile," )
												.append("idPagamento as idPagamento,")
												.append("tipoDocumento as tipoDocumento," )
												.append("descDocumento as descDocumento," )
											    .append("descBreveTipoDocumento as descBreveTipoDocumento," )
												.append("flagElettronico as flagElettronico," )
												.append("numeroDocumento as numeroDocumento, " )
												.append("dataDocumento as dataDocumento," )
												.append("imponibile as imponibile,")
												.append("importoIva as importoIva, " )
												.append("importoTotaleDocumento as importoTotaleDocumento,") 
												.append("importoPagamento as importoPagamento," )
												.append("dataPagamento as dataPagamento,")
												.append("dataValuta as dataValuta, " )
												.append("modalitaPagamento as modalitaPagamento," )
												.append("destinatarioPagamento as destinatarioPagamento,")
												.append("numProtocollo as numProtocollo")
												 ;
			
			sqlSelect.append(" FROM ( ")
			.append(sqlSelectDocumentiNo_NC_SF)
			.append(" UNION ")
			.append(sqlSelectDocumenti_NC_SF)
			.append(" ) ")
			.append(" ORDER BY tipoInvio, dataDocumento,idDocumentoDiSpesa,destinatarioPagamento,dataPagamento,dataValuta,numeroDocumento");
			
			 
			result = query(sqlSelect.toString(), params, new DocumentoContabileVORowMapper());
			
			
			return result;
			
	}
	
	public BeneficiarioVO findBeneficiario (Long idSoggetto, Long idProgetto) {
 
			
			BeneficiarioVO result = new BeneficiarioVO();
			StringBuilder sqlSelectPersonaFisica = new StringBuilder("SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome");
			StringBuilder tablesPersonaFisica = new StringBuilder("PBANDI_T_PERSONA_FISICA");
			sqlSelectPersonaFisica.append(" FROM ").append(tablesPersonaFisica);
			List<StringBuilder> conditionListPF = new ArrayList<StringBuilder>();
			conditionListPF.add(new StringBuilder("PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica"));
			setWhereClause(conditionListPF, sqlSelectPersonaFisica, tablesPersonaFisica);
			
			
			StringBuilder sqlSelectEnteGiuridico = new StringBuilder("SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico");
			StringBuilder tablesEnteGiuridico = new StringBuilder("PBANDI_T_ENTE_GIURIDICO");
			sqlSelectEnteGiuridico.append(" FROM ").append(tablesEnteGiuridico);
			List<StringBuilder> conditionListEG = new ArrayList<StringBuilder>();
			conditionListEG.add(new StringBuilder("PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.id_ente_giuridico"));
			setWhereClause(conditionListEG, sqlSelectEnteGiuridico, tablesEnteGiuridico);
			
			
			StringBuilder sqlSelect = new StringBuilder(" SELECT ")
			   									.append(" CASE ")
			   									.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica IS NOT NULL ")
			   									.append("    THEN ( "+sqlSelectPersonaFisica+" )")
			   									.append("    ELSE ( "+sqlSelectEnteGiuridico+" )")
			   									.append(" END AS denominazioneBeneficiario ");
			StringBuilder tables = new StringBuilder("PBANDI_R_SOGGETTO_PROGETTO,PBANDI_D_TIPO_ANAGRAFICA,PBANDI_D_TIPO_BENEFICIARIO");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_soggetto                    = :idSoggetto "));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_progetto                    = :idProgetto "));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica             = PBANDI_D_TIPO_ANAGRAFICA.id_tipo_anagrafica "));
			conditionList.add(new StringBuilder(" PBANDI_D_TIPO_ANAGRAFICA.desc_breve_tipo_anagrafica       = 'BENEFICIARIO'"));
			conditionList.add(new StringBuilder(" PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario           = PBANDI_D_TIPO_BENEFICIARIO.id_tipo_beneficiario "));
			conditionList.add(new StringBuilder(" PBANDI_D_TIPO_BENEFICIARIO.desc_breve_tipo_beneficiario  <> 'BEN-ASSOCIATO' "));
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idSoggetto", idSoggetto, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			setWhereClause(conditionList, sqlSelect, tables);
			
			result = queryForObject(sqlSelect.toString(), params, new BeneficiarioVORowMapper());
			
			return result;

		 
	}
	
	
	public List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> getPagamenti (Long idDocumentoDiSpesa) {
			List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> result = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO>();
			StringBuilder sqlSelect = new StringBuilder(" SELECT DISTINCT PBANDI_T_PAGAMENTO.importo_pagamento as importoPagamento,")
												.append(" PBANDI_T_PAGAMENTO.id_pagamento as idPagamento");
			StringBuilder tables = new StringBuilder("PBANDI_T_PAGAMENTO, PBANDI_R_PAGAMENTO_DOC_SPESA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" PBANDI_T_PAGAMENTO.id_pagamento = PBANDI_R_PAGAMENTO_DOC_SPESA.id_pagamento"));
			conditionList.add(new StringBuilder("  PBANDI_R_PAGAMENTO_DOC_SPESA.id_documento_di_spesa = :idDocumentoDiSpesa"));
			
			setWhereClause(conditionList, sqlSelect);
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			
			result = query(sqlSelect.toString(), params, new PagamentoQuotePartiVORowMapper());
			return result;
			
	}
	
	public List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> getPagamenti (Long idDocumentoDiSpesa, Long idProgetto) {
			List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> result = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO>();
			StringBuilder sqlSelect = new StringBuilder(" SELECT DISTINCT pbandi_t_pagamento.id_pagamento as idPagamento,")
												.append(" pbandi_t_pagamento.importo_pagamento as importoPagamento");
			StringBuilder tables = new StringBuilder("pbandi_t_quota_parte_doc_spesa, pbandi_r_pag_quot_parte_doc_sp, pbandi_t_pagamento");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_quota_parte_doc_spesa     = pbandi_r_pag_quot_parte_doc_sp.id_quota_parte_doc_spesa"));
			conditionList.add(new StringBuilder(" pbandi_t_pagamento.id_pagamento                             = pbandi_r_pag_quot_parte_doc_sp.id_pagamento"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa        = :idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_progetto                  = :idProgetto"));
			
			setWhereClause(conditionList, sqlSelect);
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			result = query(sqlSelect.toString(), params, new PagamentoQuotePartiVORowMapper());
			return result;
			
	}
	
	public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO getTotaleQuietanzati (Long idDocumentoDiSpesa, Long idProgetto) {
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO result = new it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO();
			StringBuilder sqlSelect = new StringBuilder(" SELECT pbandi_t_quota_parte_doc_spesa.id_progetto AS idProgetto,")
												.append(" pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa AS idDocumentoDiSpesa,")
												.append(" SUM(pbandi_r_pag_quot_parte_doc_sp.importo_quietanzato) AS totaleImportiQuietanzati");
			StringBuilder tables = new StringBuilder("pbandi_r_pag_quot_parte_doc_sp,pbandi_t_quota_parte_doc_spesa");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_quota_parte_doc_spesa = pbandi_r_pag_quot_parte_doc_sp.id_quota_parte_doc_spesa (+)"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa = :idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_progetto = :idProgetto"));
						
			setWhereClause(conditionList, sqlSelect);
			sqlSelect.append(" GROUP BY pbandi_t_quota_parte_doc_spesa.id_progetto,pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa");
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			result = queryForObject(sqlSelect.toString(), params, new PagamentoQuotePartiVORowMapper());
			return result;
			
	}
	
	
	public Double getTotaleImportiQuietanziatiForPagamento(Long idPagamento) {
		 
			Double result = null;
			StringBuilder sqlSelect = new StringBuilder("SELECT sum(PBANDI_R_PAG_QUOT_PARTE_DOC_SP.importo_quietanzato) ");
			StringBuilder tables = new StringBuilder(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP.id_pagamento = :idPagamento"));
			setWhereClause(conditionList, sqlSelect);
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idPagamento", idPagamento, Types.NUMERIC);
			result = queryForDouble(sqlSelect.toString(), params);
			return result;
		 
		
	}
	
	
	private class RappresentanteLegaleVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.RappresentanteLegaleDichiarazioneVO.class);
			 return vo;
		}
	}
	
	private class BeneficiarioVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.BeneficiarioVO.class);
			 return vo;
		}
	}
	
	
	private class DocumentoContabileVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
		 
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoContabileVO.class);
			if(vo.getIs_same_dich()!=null && vo.getIs_same_dich().equalsIgnoreCase("N"))
				vo.setModalitaPagamento(vo.getModalitaPagamento()+" (#)");
			if(vo.getDescBreveTipoDocumento()!=null){
				if(getDocumentoDiSpesaManager().isCedolinoCostiStandard(vo.getDescBreveTipoDocumento()) ||
						getDocumentoDiSpesaManager().isSpeseGeneraliForfettarieCostiStandard(vo.getDescBreveTipoDocumento())){
					logger.info("annullo i dati per cedolino cs o spese gener cs");
					vo.setImponibile(null);
					vo.setImportoTotaleDocumento(null);
					vo.setModalitaPagamento(null);
					vo.setDataValuta(null);
					vo.setDataPagamento(null);
					vo.setImportoPagamento(null);
				}
			}
			return vo;
		}
	}
	
	
	
	private class ProgettoVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.ProgettoVO.class);
			 return vo;
		}
	}
	
	
	
	private class EnteAppartenenzaVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.EnteAppartenenzaVO.class);
			 return vo;
		}
	}
	
	
	
	private class VoceDiCostoVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.VoceDiCostoVO.class);
			 return vo;
		}
	}
	
	private class PagamentoQuotePartiVORowMapper implements 
	RowMapper<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO>{

		public it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO vo=
				(it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO) beanMapper.toBean(rs,
						it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO.class);
			 return vo;
		}
	}


	private class DichiarazioneDiSpesaVORowMapper implements 
	RowMapper<DichiarazioneDiSpesaVO>{

		public DichiarazioneDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			return
				(DichiarazioneDiSpesaVO) beanMapper.toBean(rs,
						DichiarazioneDiSpesaVO.class);
		}
	}
	

	
	
	
	public List<DichiarazioneDiSpesaVO> findDichiarazioni(List<Long> idPagamenti) {
		 
			java.util.List<DichiarazioneDiSpesaVO> result = new ArrayList<DichiarazioneDiSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder(" SELECT distinct "+
					"  DT_CHIUSURA_VALIDAZIONE as dtChiusuraValidazione"+	
					" ,DT_DICHIARAZIONE as dataDichiarazioneDiSpesa"+		
					" ,PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA as idDichiarazione"+	
					" ,ID_PROGETTO as idProgetto"+		
					" ,ID_UTENTE_AGG as idUtenteAgg"+			
					" ,ID_UTENTE_INS as idUtente"+			
					" ,NOTE_CHIUSURA_VALIDAZIONE as noteChiusuraValidazione"+
					" ,PERIODO_AL as dataFineRendicontazione"+		
					" ,PERIODO_DAL as dataInizioRendicontazione");		
			
			StringBuilder tables = new StringBuilder("PBANDI_T_DICHIARAZIONE_SPESA,PBANDI_R_PAGAMENTO_DICH_SPESA");

			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA=PBANDI_R_PAGAMENTO_DICH_SPESA.ID_DICHIARAZIONE_SPESA"));
			StringBuilder listIdPagamenti = new StringBuilder();
			for (Long idPagamento : idPagamenti) {
				listIdPagamenti.append(idPagamento + ",");
			}
			conditionList.add((new StringBuilder()).append(
					"PBANDI_R_PAGAMENTO_DICH_SPESA.ID_PAGAMENTO  IN ( ").append(
					listIdPagamenti.substring(0, listIdPagamenti.lastIndexOf(","))).append(
					" )"));			
			MapSqlParameterSource params = new MapSqlParameterSource();			
			
			setWhereClause(conditionList, sqlSelect,tables);
			
			result = query(sqlSelect.toString(), params, new DichiarazioneDiSpesaVORowMapper());
			getLogger().info("dichiarazioni trovate: " + result.size());
			return result;
			
		 
	}
	
	
	public List<DichiarazioneDiSpesaVO> findDatiDichiarazione (Long idDichiarazione) {
		 
			List<DichiarazioneDiSpesaVO> result = new ArrayList<DichiarazioneDiSpesaVO>();
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT PBANDI_T_DICHIARAZIONE_SPESA.dt_dichiarazione as dataDichiarazioneDiSpesa,")
												.append("PBANDI_T_DICHIARAZIONE_SPESA.PERIODO_DAL as dataInizioRendicontazione,")
												.append("PBANDI_T_DICHIARAZIONE_SPESA.PERIODO_AL as dataFineRendicontazione,")
												.append("PBANDI_T_DICHIARAZIONE_SPESA.id_progetto as idProgetto,")
												.append("PBANDI_T_DOMANDA.progr_bando_linea_intervento as idBandoLinea");
			StringBuilder tables = new StringBuilder("PBANDI_T_DICHIARAZIONE_SPESA, PBANDI_T_PROGETTO, PBANDI_T_DOMANDA");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder("PBANDI_T_DICHIARAZIONE_SPESA.id_progetto = PBANDI_T_PROGETTO.id_progetto"));
			conditionList.add(new StringBuilder(" PBANDI_T_DOMANDA.id_domanda = PBANDI_T_PROGETTO.id_domanda"));
			conditionList.add(new StringBuilder(" PBANDI_T_DICHIARAZIONE_SPESA.id_dichiarazione_spesa = :idDichiarazione"));
			MapSqlParameterSource params = new MapSqlParameterSource();		
			params.addValue("idDichiarazione", idDichiarazione, Types.NUMERIC);
			setWhereClause(conditionList, sqlSelect);
			result = query(sqlSelect.toString(), params, new DichiarazioneDiSpesaVORowMapper());
			return result;
			
	 
	}

	public List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> getPagamentiAssociatiAVociDiSpesa (Long idDocumentoDiSpesa, Long idProgetto) {
	 
			List<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO> result = new ArrayList<it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti.PagamentoQuotePartiVO>();
			StringBuilder sqlSelect = new StringBuilder(" SELECT DISTINCT pbandi_t_pagamento.id_pagamento as idPagamento,")
												.append(" pbandi_t_pagamento.importo_pagamento as importoPagamento");
			StringBuilder tables = new StringBuilder("pbandi_t_quota_parte_doc_spesa, pbandi_r_pag_quot_parte_doc_sp, pbandi_t_pagamento");
			sqlSelect.append(" FROM ").append(tables);
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_quota_parte_doc_spesa     = pbandi_r_pag_quot_parte_doc_sp.id_quota_parte_doc_spesa"));
			conditionList.add(new StringBuilder(" pbandi_t_pagamento.id_pagamento                             = pbandi_r_pag_quot_parte_doc_sp.id_pagamento"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_documento_di_spesa        = :idDocumentoDiSpesa"));
			conditionList.add(new StringBuilder(" pbandi_t_quota_parte_doc_spesa.id_progetto                  = :idProgetto"));
			
			setWhereClause(conditionList, sqlSelect);
			MapSqlParameterSource params = new MapSqlParameterSource();			
			params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
			params.addValue("idProgetto", idProgetto, Types.NUMERIC);
			
			result = query(sqlSelect.toString(), params, new PagamentoQuotePartiVORowMapper());
			return result;
			
		 
	}
	
	private class DocumentoDiSpesaDichiarazioneVORowMapper implements 
	RowMapper<DocumentoDiSpesaDichiarazioneVO>{
		public DocumentoDiSpesaDichiarazioneVO mapRow(ResultSet rs, int row) throws SQLException {
			
			BeanMapper beanMapper = new BeanMapper();
			return
				(DocumentoDiSpesaDichiarazioneVO) beanMapper.toBean(rs,
						DocumentoDiSpesaDichiarazioneVO.class);
		}
	}
	
	// Metodo modificato per la jira PBANDI-2769 (sotto c'e l'originale).
	public List<DocumentoDiSpesaDichiarazioneVO> findDichiarazioni(Long idProgetto,Long idDocumentoDiSpesa) {
		 
		java.util.List<DocumentoDiSpesaDichiarazioneVO> result = new ArrayList<DocumentoDiSpesaDichiarazioneVO>();
		StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT")		
		.append(" DICH.ID_DICHIARAZIONE_SPESA AS idDichiarazioneSpesa,")
		.append(" DICH.ID_PROGETTO                          AS idPRogetto,")
		.append(" DICH.DT_DICHIARAZIONE                     AS dtDichiarazione,")
		.append(" DICH.ID_TIPO_DICHIARAZ_SPESA              AS idTipoDichiarazSpesa,")
		.append(" DICH.PERIODO_AL periodoAl,")
		.append(" DICH.PERIODO_DAL periodoDal,")
		.append(" DICH.DT_CHIUSURA_VALIDAZIONE dtChiusuraValidazione,")
		.append(" DICH.NOTE_CHIUSURA_VALIDAZIONE noteChiusuraValidazione,")
		.append(" TIPODICH.DESC_TIPO_DICHIARAZIONE_SPESA descTipoDichiarazioneSpesa")
		.append(" FROM PBANDI_R_PAGAMENTO_DOC_SPESA RPAGDOC")
		.append(" JOIN PBANDI_R_PAGAMENTO_DICH_SPESA RPAGDICH")
		.append("   ON RPAGDICH.ID_PAGAMENTO = RPAGDOC.ID_PAGAMENTO")
		.append(" JOIN PBANDI_T_DICHIARAZIONE_SPESA DICH")
		.append("   ON DICH.ID_DICHIARAZIONE_SPESA = RPAGDICH.ID_DICHIARAZIONE_SPESA")
		.append(" JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA TIPODICH")
		.append("   ON TIPODICH.ID_TIPO_DICHIARAZ_SPESA = DICH.ID_TIPO_DICHIARAZ_SPESA")
		.append(" WHERE RPAGDOC.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa ")
		.append("   AND DICH.DT_CHIUSURA_VALIDAZIONE IS NOT NULL ") 
		.append(" ORDER BY idDichiarazioneSpesa ASC ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();			
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
		//params.addValue("idProgetto", idProgetto, Types.NUMERIC);
		
		result = query(sqlSelect.toString(), params, new DocumentoDiSpesaDichiarazioneVORowMapper());
		logger.info("findDichiarazioni(): DS trovate = "+result.size());
		for (DocumentoDiSpesaDichiarazioneVO vo : result) {
			logger.info(vo.toString());
		}
		
		return result;
	}
	
	// Metodo originale precedente alla jira PBANDI-2769.
	/*
	public List<DocumentoDiSpesaDichiarazioneVO> findDichiarazioni(Long idProgetto,Long idDocumentoDiSpesa) {
		 
		java.util.List<DocumentoDiSpesaDichiarazioneVO> result = new ArrayList<DocumentoDiSpesaDichiarazioneVO>();
		StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT")		
		.append(" DICH.ID_DICHIARAZIONE_SPESA as idDichiarazioneSpesa,DICH.ID_PROGETTO as idPRogetto,DICH.DT_DICHIARAZIONE as dtDichiarazione,")
		.append(" DICH.ID_TIPO_DICHIARAZ_SPESA as idTipoDichiarazSpesa,DICH.PERIODO_AL periodoAl,DICH.PERIODO_DAL periodoDal,")
		.append(" DICH.DT_CHIUSURA_VALIDAZIONE dtChiusuraValidazione,DICH.NOTE_CHIUSURA_VALIDAZIONE noteChiusuraValidazione,")
		.append(" RDOCPRJ.ID_DOCUMENTO_DI_SPESA idDocumentoDiSpesa,TIPODICH.DESC_TIPO_DICHIARAZIONE_SPESA descTipoDichiarazioneSpesa,")
		.append(" DOCINDEX.ID_DOCUMENTO_INDEX idDocumentoIndex,DOCINDEX.NOME_FILE nomeFile")
		.append(" FROM PBANDI_T_DICHIARAZIONE_SPESA DICH")
		.append(" JOIN PBANDI_R_PAGAMENTO_DICH_SPESA RPAGDICH ON DICH.ID_DICHIARAZIONE_SPESA = RPAGDICH.ID_DICHIARAZIONE_SPESA")
		.append(" JOIN PBANDI_R_PAGAMENTO_DOC_SPESA RPAGDOC ON RPAGDICH.ID_PAGAMENTO = RPAGDOC.ID_PAGAMENTO")
		.append(" JOIN PBANDI_R_DOC_SPESA_PROGETTO RDOCPRJ  ON RPAGDOC.ID_DOCUMENTO_DI_SPESA= RDOCPRJ.ID_DOCUMENTO_DI_SPESA AND RDOCPRJ.ID_PROGETTO = DICH.ID_PROGETTO")
		.append(" JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA TIPODICH ON  TIPODICH.ID_TIPO_DICHIARAZ_SPESA=DICH.ID_TIPO_DICHIARAZ_SPESA")
		.append(" JOIN PBANDI_T_DOCUMENTO_INDEX DOCINDEX ON  DICH.ID_DICHIARAZIONE_SPESA=DOCINDEX.ID_TARGET AND DOCINDEX.ID_ENTITA=(select id_entita from PBANDI_C_ENTITA where upper(nome_entita) ='PBANDI_T_DICHIARAZIONE_SPESA')")
		.append(" WHERE")
		.append(" RDOCPRJ.ID_PROGETTO = :idProgetto")
		.append(" AND RPAGDOC.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa")
		.append(" AND DICH.DT_CHIUSURA_VALIDAZIONE IS NOT NULL")
   		.append(" AND (ID_TIPO_DOCUMENTO_INDEX in (SELECT ID_TIPO_DOCUMENTO_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX  WHERE  DESC_BREVE_TIPO_DOC_INDEX in ('DS','RDDS')))")
   		.append(" AND DOCINDEX.ID_ENTITA=(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE UPPER(NOME_ENTITA) ='PBANDI_T_DICHIARAZIONE_SPESA')")
		.append(" UNION ")
		.append(" select DICH.ID_DICHIARAZIONE_SPESA as idDichiarazioneSpesa,DICH.ID_PROGETTO as idPRogetto,DICH.DT_DICHIARAZIONE as dtDichiarazione,")
		.append(" DICH.ID_TIPO_DICHIARAZ_SPESA as idTipoDichiarazSpesa,DICH.PERIODO_AL periodoAl,DICH.PERIODO_DAL periodoDal,")
		.append(" DICH.DT_CHIUSURA_VALIDAZIONE dtChiusuraValidazione,DICH.NOTE_CHIUSURA_VALIDAZIONE noteChiusuraValidazione,")
		.append(" RDOCPRJ.ID_DOCUMENTO_DI_SPESA idDocumentoDiSpesa,TIPODICH.DESC_TIPO_DICHIARAZIONE_SPESA descTipoDichiarazioneSpesa,")
		.append(" DOCINDEX.ID_DOCUMENTO_INDEX idDocumentoIndex,DOCINDEX.NOME_FILE nomeFile")
	    .append(" FROM PBANDI_T_DICHIARAZIONE_SPESA DICH")
		.append(" JOIN PBANDI_R_PAGAMENTO_DICH_SPESA RPAGDICH ON DICH.ID_DICHIARAZIONE_SPESA = RPAGDICH.ID_DICHIARAZIONE_SPESA")
		.append(" JOIN PBANDI_R_PAGAMENTO_DOC_SPESA RPAGDOC ON RPAGDICH.ID_PAGAMENTO = RPAGDOC.ID_PAGAMENTO")
		.append(" JOIN PBANDI_R_DOC_SPESA_PROGETTO RDOCPRJ  ON RPAGDOC.ID_DOCUMENTO_DI_SPESA= RDOCPRJ.ID_DOCUMENTO_DI_SPESA AND RDOCPRJ.ID_PROGETTO = DICH.ID_PROGETTO")
		.append(" JOIN PBANDI_D_TIPO_DICHIARAZ_SPESA TIPODICH ON  TIPODICH.ID_TIPO_DICHIARAZ_SPESA=DICH.ID_TIPO_DICHIARAZ_SPESA")
		.append(" JOIN PBANDI_S_DICH_DOC_SPESA ON (DICH.ID_DICHIARAZIONE_SPESA=PBANDI_S_DICH_DOC_SPESA.ID_DICHIARAZIONE_SPESA)") 
		.append(" JOIN PBANDI_T_COMUNICAZ_FINE_PROG on(PBANDI_T_COMUNICAZ_FINE_PROG.ID_PROGETTO=RDOCPRJ.ID_PROGETTO)")
		.append(" JOIN PBANDI_T_DOCUMENTO_INDEX DOCINDEX ON PBANDI_T_COMUNICAZ_FINE_PROG.ID_COMUNICAZ_FINE_PROG  =DOCINDEX.ID_TARGET")
		.append(" AND DOCINDEX.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE UPPER(NOME_ENTITA) ='PBANDI_T_COMUNICAZ_FINE_PROG')") 
		.append(" WHERE RDOCPRJ.ID_PROGETTO =  :idProgetto ")	
		.append(" AND RPAGDOC.ID_DOCUMENTO_DI_SPESA = :idDocumentoDiSpesa ")
		.append(" AND DICH.DT_CHIUSURA_VALIDAZIONE IS NOT NULL ") 
		.append(" ORDER BY idDichiarazioneSpesa ASC ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();			
		params.addValue("idDocumentoDiSpesa", idDocumentoDiSpesa, Types.NUMERIC);
		params.addValue("idProgetto", idProgetto, Types.NUMERIC);
		
		result = query(sqlSelect.toString(), params, new DocumentoDiSpesaDichiarazioneVORowMapper());
		return result;
	}
	*/
	
	public Long findIdComunicazFineProgetto(Long idProgetto) {
		logger.info("findIdComunicazFineProgetto(): idProgetto = "+idProgetto);
		
		StringBuilder sqlSelect = new StringBuilder("SELECT ID_COMUNICAZ_FINE_PROG")
		.append(" FROM PBANDI_T_COMUNICAZ_FINE_PROG")
		.append(" WHERE ID_PROGETTO = :idProgetto ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.NUMERIC);
		
		Long idComunicazFineProgetto = queryForLong(sqlSelect.toString(), params);
		logger.info("findIdComunicazFineProgetto(): idComunicazFineProgetto = "+idComunicazFineProgetto);
		
		return idComunicazFineProgetto;
	}
	
	public List<DocumentoDiSpesaDichiarazioneVO> findDocIndexDs(Long idProgetto, Long idTarget, String nomeEntita) {
		logger.info("findDocIndexDs(): idProgetto = "+idProgetto+"; idTarget = "+idTarget+"; nomeEntita = "+nomeEntita);
		
		java.util.List<DocumentoDiSpesaDichiarazioneVO> result = new ArrayList<DocumentoDiSpesaDichiarazioneVO>();
		StringBuilder sqlSelect = new StringBuilder("SELECT ")		
		.append(" DOCINDEX.ID_DOCUMENTO_INDEX idDocumentoIndex,")
		.append(" DOCINDEX.NOME_FILE nomeFile")
		.append(" FROM PBANDI_T_DOCUMENTO_INDEX DOCINDEX")
		.append(" WHERE DOCINDEX.ID_PROGETTO = :idProgetto")
		.append("   AND DOCINDEX.ID_TARGET = :idTarget")
		.append("  AND DOCINDEX.ID_ENTITA = (SELECT id_entita FROM PBANDI_C_ENTITA WHERE upper(nome_entita) = :nomeEntita)")
		.append(" ORDER BY nomeFile ");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idProgetto", idProgetto, Types.NUMERIC);
		params.addValue("idTarget", idTarget, Types.NUMERIC);
		params.addValue("nomeEntita", nomeEntita, Types.VARCHAR);
		
		result = query(sqlSelect.toString(), params, new DocumentoDiSpesaDichiarazioneVORowMapper());
		logger.info("findDocIndexDs(): file trovati = "+result.size());
		for (DocumentoDiSpesaDichiarazioneVO vo : result) {
			logger.info(" - "+vo.getIdDocumentoIndex()+"   "+vo.getNomeFile());
		}
		
		return result;
	}
	
	public DocumentoContabileVO findRuoloFirmaDocumentiContabili(Long idDocSpesa) {
		logger.begin();
		logger.debug("idDocSpesa="+idDocSpesa);
		MapSqlParameterSource params = new MapSqlParameterSource();			
		params.addValue("idDocSpesa", idDocSpesa, Types.NUMERIC);
		
		DocumentoContabileVO result = new DocumentoContabileVO();
		
		// PK : per non scrivere un RowMapper adHoc riuso DocumentoContabileVORowMapper
		StringBuilder sqlSelect = new StringBuilder("SELECT is_same_dich, idDocumentoDiSpesa as idDocumentoDiSpesa, task as task, tipo_invio as tipoInvio, importo_rendicontabile as importoRendicontabile, " )
				.append(" idPagamento as idPagamento, tipoDocumento as tipoDocumento, descDocumento as descDocumento, " )
			    .append(" descBreveTipoDocumento as descBreveTipoDocumento, flagElettronico as flagElettronico, numeroDocumento as numeroDocumento, " )
				.append(" dataDocumento as dataDocumento, imponibile as imponibile, importoIva as importoIva, importoTotaleDocumento as importoTotaleDocumento,") 
				.append(" importoPagamento as importoPagamento, dataPagamento as dataPagamento, dataValuta as dataValuta, " )
				.append(" modalitaPagamento as modalitaPagamento, destinatarioPagamento as destinatarioPagamento, numProtocollo as numProtocollo, ")
				.append(" dataFirmaContratto as dataFirmaContratto, ")
				.append(" ruolo as ruolo ")
				.append(" FROM( SELECT ")
				.append("		NULL AS is_same_dich,")
				.append("		NULL AS idDocumentoDiSpesa,")
				.append("		NULL AS task,")
				.append("		NULL AS tipo_invio,")
				.append("		NULL AS importo_rendicontabile,")
				.append("		NULL AS idPagamento,")
				.append("		NULL AS tipoDocumento,")
				.append("		NULL AS descBreveTipoDocumento,")
				.append("		NULL AS descDocumento,")
				.append("		NULL AS flagElettronico,")
				.append("		NULL AS numeroDocumento,")
				.append("		NULL AS dataDocumento,")
				.append("		NULL AS imponibile,")
				.append("		NULL AS importoIva,")
				.append("		NULL AS importoTotaleDocumento,")
				.append("		NULL AS importoPagamento,")
				.append("		NULL AS dataPagamento,")
				.append("		NULL AS dataValuta,")
				.append("		NULL AS modalitaPagamento,")
				.append("		NULL AS destinatarioPagamento,")
				.append("		NULL AS numProtocollo,")
				.append("		app.DT_FIRMA_CONTRATTO AS dataFirmaContratto,")
				.append("		tp.DESC_TIPO_PERCETTORE AS ruolo ")
				.append("	FROM pbandi_r_doc_spesa_progetto dsp, pbandi_t_documento_di_spesa ds, pbandi_r_fornitore_affidamento fa, pbandi_t_appalto app, pbandi_d_tipo_percettore tp ")
				.append("	WHERE  dsp.id_documento_di_spesa = ds.id_documento_di_spesa ")
				.append("		and dsp.id_appalto = fa.id_appalto ")
				.append("		and dsp.id_appalto = app.id_appalto ")
				.append("		AND DS.ID_FORNITORE = FA.ID_FORNITORE ")
				.append("		and fa.id_tipo_percettore = tp.id_tipo_percettore ")
				.append("		and dsp.id_documento_di_spesa = :idDocSpesa ")
				.append(" ) ");

		logger.debug("query="+sqlSelect.toString());
		result = queryForObject(sqlSelect.toString(), params, new DocumentoContabileVORowMapper());
		
		logger.end();
		return result;
	}

}
