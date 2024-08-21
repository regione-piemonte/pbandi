/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.csi.pbandi.pbservrest.dto.DomandaInfo;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.DomandaBeneficiarioDAO;
import it.csi.pbandi.pbservrest.util.Constants;

public class DomandaBeneficiarioDAOImpl extends BaseDAO implements DomandaBeneficiarioDAO {
	
private Logger LOG;

	public DomandaBeneficiarioDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}
	
	@Override
	public List<DomandaInfo> getDomandeBeneficiarioProgetto(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto,
			String denominazioneEnteGiuridico, String partitaIva, String acronimoProgetto, String tipoSogg) {
		String prf = "[DomandaBeneficiarioDAOImpl::getDomandeBeneficiarioProgetto]";
		LOG.info(prf + " BEGIN");
		List<DomandaInfo> datiDomanda = null;
		LOG.info(prf + " Numero domanda: " + numeroDomanda + ", Descrizione stato domanda: " + numRelazDichSpesa + ", PEC beneficiario: " + pec +
				", codiceBando: " + codiceBando + ", ndg: " + ndg +
				", Codice fiscale soggetto: " + codiceFiscaleSoggetto + ", Denominazione ente giuridico: [" + denominazioneEnteGiuridico + 
				"], Partita IVA: " + partitaIva + ", Acronimo progetto: " + acronimoProgetto+ ", tipoSogg: " + tipoSogg);
		try {
			
			StringBuilder sqlf= new StringBuilder(); 
			
			String sql = "SELECT\r\n"
					+ "    DISTINCT pts.codice_fiscale_soggetto,\r\n"
					+ "	   ptd.numero_domanda,\r\n"
					+ "    ptd.progr_bando_linea_intervento,\r\n"
					+ "    prcema.QUOTA_IMPORTO_AGEVOLATO, \r\n"
					+ "    pts.NDG,\r\n"
					+ "    pdsd.desc_stato_domanda,\r\n"
					+ "    ptr.acronimo_progetto,\r\n"
					+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
					+ "    pts2.partita_iva,\r\n"
					+ "    ptr2.pec,\r\n"
					+ "    pti.desc_indirizzo,\r\n"
					+ "    pti.cap,\r\n"
					+ "    pdc.desc_comune,\r\n"
					+ "    pdp.sigla_provincia,\r\n"
					+ "    pdr.desc_regione,\r\n"
					+ "    pdn.desc_nazione\r\n"
					+ " FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA  = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA  = ptd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.id_soggetto = prsp.id_soggetto\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.id_stato_domanda = ptd.ID_STATO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA  = ptd.id_domanda\r\n"
					+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "    LEFT JOIN PBANDI_T_SEDE pts2 ON pts2.id_sede = prsps.id_sede\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr2 ON ptr2.id_recapiti = prsps.ID_RECAPITI\r\n"
					+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.id_indirizzo = prsps.id_indirizzo\r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.id_comune = pti.id_comune\r\n"
					+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.id_nazione = pti.id_nazione\r\n"
					+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.id_provincia = pti.id_provincia\r\n"
					+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.id_regione\r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS null\r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n"; 
					
				String sql2=	 " WHERE\r\n"
					+ "     prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsps.ID_TIPO_SEDE = 1\r\n";

				sqlf.append(sql); 
				BigDecimal idFondoFinpis = null; 
					if(numeroDomanda!=null && numeroDomanda.length()>0) {sql2 += "	AND ptd.NUMERO_DOMANDA = :numeroDomanda \r\n";}
					if(codiceBando!=null ) {
						idFondoFinpis = getIdFondo(codiceBando);
						if(idFondoFinpis!=null) {
							codiceBando = idFondoFinpis; 
						}
						sql2 += "	AND ptd.progr_bando_linea_intervento = :codiceBando \r\n";	
					}
					if(numRelazDichSpesa!=null) {
						sqlf.append("LEFT JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON PRSP .ID_PROGETTO = ptds.ID_PROGETTO");
						sql2 += "	AND  ptds.ID_DICHIARAZIONE_SPESA = :numRelazDichSpesa \r\n";
					}
					if(ndg!=null && ndg.length()>0) {
						sql2 += "	AND pts.NDG LIKE :ndg \r\n";
					}
					if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) {
						sql2 += "	AND pts.CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto \r\n";
						}
					if(denominazioneEnteGiuridico!=null && denominazioneEnteGiuridico.length()>0) {
					 sql2 += "	AND UPPER(pteg.DENOMINAZIONE_ENTE_GIURIDICO) like UPPER( '%'||:denominazioneEnteGiuridico||'%' )\r\n";
					}
					if(partitaIva!=null && partitaIva.length()>0) {sql2 += "	AND pts2.PARTITA_IVA = :partitaIva \r\n";}
					if(acronimoProgetto!=null && acronimoProgetto.length()>0) {
						String elencoProgetti = getElencoProgetti(acronimoProgetto);
						if(elencoProgetti!=null) {							
							acronimoProgetto = elencoProgetti;
							sql2 += "	AND prsp.ID_PROGETTO IN ("+acronimoProgetto+") \r\n";
						} else {
							return new ArrayList<>(); 
						}
					}
					if(pec!=null && pec.length()>0) {sql2 += "	AND upper(ptr2.PEC) = upper(:pec) \r\n";}
					
			sqlf.append(sql2);
			
			LOG.info(prf + "sql="+sql);
			
			MapSqlParameterSource param = new MapSqlParameterSource(); 
		
			if(numeroDomanda!=null && numeroDomanda.length()>0) { param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR); }
			if(codiceBando!=null) {param.addValue("codiceBando", codiceBando, Types.INTEGER); }
			if(numRelazDichSpesa!=null) {param.addValue("numRelazDichSpesa", numRelazDichSpesa, Types.INTEGER); }
			if(ndg!=null && ndg.length()>0) {param.addValue("ndg", "%"+ndg+"%", Types.VARCHAR); }
			if(pec!=null && pec.length()>0) { param.addValue("pec", pec, Types.VARCHAR); }
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) { 
				param.addValue("codiceFiscaleSoggetto", codiceFiscaleSoggetto, Types.VARCHAR); }
			if(denominazioneEnteGiuridico!=null && denominazioneEnteGiuridico.length()>0) { 
//				param.addValue("denominazioneEnteGiuridico", "%"+denominazioneEnteGiuridico+"%", Types.VARCHAR); 
				param.addValue("denominazioneEnteGiuridico", denominazioneEnteGiuridico, Types.VARCHAR); 
			}
			if(partitaIva!=null && partitaIva.length()>0) { param.addValue("partitaIva", partitaIva, Types.VARCHAR); }
			//if(acronimoProgetto!=null && acronimoProgetto.length()>0) { param.addValue("acronimoProgetto", acronimoProgetto, Types.VARCHAR); }
			
			datiDomanda = this.namedParameterJdbcTemplate.query(sqlf.toString(), param, new ResultSetExtractor<List<DomandaInfo>>() {
				@Override
				public List<DomandaInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<DomandaInfo> elencoDatiDomanda = new ArrayList<>();
					while(rs.next()) {
						DomandaInfo domanda = new DomandaInfo();
						domanda.setTipoSogg(tipoSogg);
						domanda.setNumeroDomanda(rs.getString("numero_domanda"));
						domanda.setDescStatoDomanda(rs.getString("desc_stato_domanda"));
						domanda.setPec(rs.getString("pec"));
						domanda.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
						domanda.setDenominazioneEnteGiuridico(rs.getString("denominazione_ente_giuridico"));
						domanda.setPartitaIva(rs.getString("partita_iva"));
						domanda.setAcronimoProgetto(rs.getString("acronimo_progetto"));
						domanda.setCodiceBando(rs.getBigDecimal("progr_bando_linea_intervento"));
						domanda.setNdg(rs.getString("NDG"));
						BigDecimal qia = rs.getBigDecimal("quota_importo_agevolato");
						if(qia!=null) {
							domanda.setImportoConcesso(qia);
						}else {
							domanda.setImportoConcesso(new BigDecimal(0));
//							domanda.setImportoConcesso(rs.getBigDecimal("quota_importo_agevolato"));
						}
						domanda.setIndirizzo(rs.getString("DESC_INDIRIZZO"));
						domanda.setCAP(rs.getString("CAP"));
						domanda.setNazione(rs.getString("DESC_NAZIONE"));
						domanda.setRegione(rs.getString("DESC_REGIONE"));
						domanda.setComune(rs.getString("DESC_COMUNE"));
						domanda.setProvincia(rs.getString("SIGLA_PROVINCIA"));
						elencoDatiDomanda.add(domanda);
					}
					return elencoDatiDomanda;
				}
				
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandaInfo", e);
			throw new ErroreGestitoException("DaoException while trying to read DomandaInfo", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiDomanda;
	}

	private BigDecimal getIdFondo(BigDecimal codBando) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        logger.info(prf + " BEGIN");

        BigDecimal idFondo = null;
        try {
        	String query ="SELECT \r\n"
        			+ " mvfb.PROGR_BANDO_LINEA_INTERV \r\n"
        			+ "FROM MFINPIS_V_FONDI_BANDI mvfb \r\n"
        			+ "WHERE mvfb.COD_FONDO  = :codBando"; 
        	MapSqlParameterSource param = new MapSqlParameterSource();
        	param.addValue("codBando", codBando);
			idFondo = namedParameterJdbcTemplate.queryForObject(query, param, BigDecimal.class );


        } catch (Exception e) {
            String msg = "Errore l'esecuzione della query";
            logger.error(prf + msg, e);
//			throw new Exception(msg, e);
            return null;
        } finally {
            logger.info(prf + " END");
        }

        return idFondo;
    }


	private String getElencoProgetti(String acronimoProgetto) {
		String prf = "[DomandaBeneficiarioDAOImpl::getDomandeBeneficiarioFisicoProgetto]";
		LOG.info(prf + " BEGIN");
		
		StringBuilder elencoProgetti =new  StringBuilder(); 
		
		try {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("acronimoProgetto", acronimoProgetto, Types.VARCHAR);
			List<Long> progetti = new ArrayList<Long>();
			
			String sql = "  SELECT ptp2.ID_PROGETTO   \r\n"
					+ "    FROM pbandi_t_progetto ptp\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA = ptp.ID_DOMANDA \r\n"
					+ "    LEFT JOIN pbandi_t_progetto ptp2 ON ptp2.ID_PROGETTO_PADRE  = ptp.ID_PROGETTO\r\n"
					+ "    WHERE \r\n"
					+ "    upper(ptr.ACRONIMO_PROGETTO) = upper(:acronimoProgetto)"; 
			
			RowMapper<Long> rowMap =  new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					Long idProgetto = rs.getLong("ID_PROGETTO");
					return idProgetto;
				}
			};
			
			progetti=  this.namedParameterJdbcTemplate.query(sql,param, rowMap);
			for (int i = 0; i <progetti.size(); i++) {
				elencoProgetti.append(progetti.get(i));
			
				if(i< progetti.size()-1) {
					elencoProgetti.append(","); 
				}
			}
			
			sql = " SELECT ptp.ID_PROGETTO   \r\n"
					+ "    FROM pbandi_t_progetto ptp\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA = ptp.ID_DOMANDA \r\n"
					+ "    LEFT JOIN pbandi_t_progetto ptp2 ON ptp2.ID_PROGETTO_PADRE  = ptp.ID_PROGETTO\r\n"
					+ "    WHERE \r\n"
					+ "    upper(ptr.ACRONIMO_PROGETTO) = upper(:acronimoProgetto)\r\n"
					+ "    AND ptp.FLAG_PROGETTO_MASTER ='S'\r\n"
					+ "    AND ROWNUM < 2"; 
			
			List<Long> idProgettoMaster = namedParameterJdbcTemplate.queryForList(sql,param, Long.class);
//			for (Long progetto : progetti) {
//				if( progetti.indexOf(progetto)<progetti.size()-1) {	
//				}
//			}
			
			
			elencoProgetti.append(","+idProgettoMaster.get(0).toString());
			
		} catch (Exception e) {
			LOG.error(e);
			return null; 
		}
		
		
		return elencoProgetti.toString();
	}

	@Override
	public List<DomandaInfo> getDomandeBeneficiarioFisicoProgetto(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto, String nome,
			String cognome, String tipoSogg) {
		String prf = "[DomandaBeneficiarioDAOImpl::getDomandeBeneficiarioFisicoProgetto]";
		LOG.info(prf + " BEGIN");
		List<DomandaInfo> datiDomanda = null;
		LOG.info(prf + " Numero domanda: " + numeroDomanda + ", Descrizione stato domanda: " + numRelazDichSpesa + ", PEC beneficiario: " + pec + ", Codice fiscale soggetto: " + codiceFiscaleSoggetto + ", Nome: " + nome + ", Cognome: " + cognome);
		try {
			String sql = "SELECT\r\n"
					+ "    DISTINCT pts.codice_fiscale_soggetto,\r\n"
					+ "	   ptd.numero_domanda,\r\n"
					+ "    ptd.progr_bando_linea_intervento,\r\n"
					+ "    prcema.QUOTA_IMPORTO_AGEVOLATO, \r\n"
					+ "    pts.NDG,\r\n"
					+ "    pdsd.desc_stato_domanda,\r\n"
					+ "    ptr2.pec,\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptpf.DT_NASCITA, pti.DESC_INDIRIZZO  \r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA  = prsp.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA  = ptd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.id_soggetto = prsp.id_soggetto\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.id_stato_domanda = ptd.ID_STATO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA  = ptd.id_domanda\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr2 ON ptr2.id_recapiti = prsp.ID_RECAPITI_PERSONA_FISICA \r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.id_persona_fisica = prsp.ID_PERSONA_FISICA  \r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS null\r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n"
					+ "	   LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO =  prsp.ID_INDIRIZZO_PERSONA_FISICA\r\n"; 
			
			if(numRelazDichSpesa!=null) {
				sql +="LEFT JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON PRSP .ID_PROGETTO = ptds.ID_PROGETTO\r\n"; 
			}
					sql+= "WHERE\r\n"
					+ "     prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n";
			
//			if(numRelazDichSpesa!=null) {
//				sqlf.append();
//				sql2 += "	 = :numRelazDichSpesa \r\n";
//			}
			if(numeroDomanda!=null && numeroDomanda.length()>0) {sql += "	AND ptd.NUMERO_DOMANDA = :numeroDomanda \r\n";}
			BigDecimal idFondoFinpis = null; 
			if(codiceBando!=null) {
				idFondoFinpis = getIdFondo(codiceBando);
				if(idFondoFinpis!=null) {
					codiceBando = idFondoFinpis; 
				}
				sql += "	AND ptd.progr_bando_linea_intervento = :codiceBando \r\n";}
			if(numRelazDichSpesa!=null) {sql += "AND ptds.ID_DICHIARAZIONE_SPESA = :numRelazDichSpesa \r\n"
					+ " 	AND ptds.ID_DICHIARAZIONE_SPESA IS NOT NULL \r\n";}
			if(ndg!=null && ndg.length()>0) {
				sql += "	AND pts.NDG like :ndg \r\n";
			}
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) {sql += "	AND pts.CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto \r\n";}
			if(nome!=null && nome.length()>0) {sql += "	AND UPPER(ptpf.NOME) LIKE UPPER(:nome) \r\n";}  
			if(cognome!=null && cognome.length()>0) {sql += "	AND UPPER(ptpf.COGNOME) LIKE UPPER(:cognome) \r\n";} 
			if(pec!=null && pec.length()>0) {sql += "	AND upper(ptr2.PEC) = upper(:pec) \r\n";}
			sql += " AND ptpf.COGNOME is not null\r\n";
			
			MapSqlParameterSource param = new MapSqlParameterSource(); 
	
			if(numeroDomanda!=null && numeroDomanda.length()>0) { param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR); }
			if(codiceBando!=null) {param.addValue("codiceBando", codiceBando, Types.INTEGER); }
			if(numRelazDichSpesa!=null) {param.addValue("numRelazDichSpesa", numRelazDichSpesa, Types.INTEGER); }
			if(ndg!=null && ndg.length()>0) {param.addValue("ndg", "%"+ndg+"%", Types.VARCHAR); }
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) { param.addValue("codiceFiscaleSoggetto", codiceFiscaleSoggetto, Types.VARCHAR); }
			if(nome!=null && nome.length()>0) { param.addValue("nome", "%"+nome+"%", Types.VARCHAR); }
			if(cognome!=null && cognome.length()>0) { param.addValue("cognome", "%"+cognome+"%", Types.VARCHAR); }
			if(pec!=null && pec.length()>0) { param.addValue("pec", pec, Types.VARCHAR); }
			
			LOG.debug(sql);
			
			datiDomanda = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DomandaInfo>>() {
				@Override
				public List<DomandaInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<DomandaInfo> elencoDatiDomanda = new ArrayList<>();
					while(rs.next()) {
						DomandaInfo domanda = new DomandaInfo();
						domanda.setTipoSogg(tipoSogg);
						domanda.setNumeroDomanda(rs.getString("numero_domanda"));
						domanda.setDescStatoDomanda(rs.getString("desc_stato_domanda"));
						domanda.setPec(rs.getString("pec"));
						domanda.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
						domanda.setNome(rs.getString("nome"));
						domanda.setCognome(rs.getString("cognome"));
						domanda.setCodiceBando(rs.getBigDecimal("progr_bando_linea_intervento"));
						domanda.setNdg(rs.getString("NDG"));
//						domanda.setImportoConcesso(rs.getBigDecimal("quota_importo_agevolato"));
						BigDecimal qia = rs.getBigDecimal("quota_importo_agevolato");
						if(qia!=null) {
							domanda.setImportoConcesso(qia);
						}else {
							domanda.setImportoConcesso(new BigDecimal(0));
						}
						domanda.setDtNascita(rs.getString("DT_NASCITA"));
						domanda.setIndirizzo(rs.getString("DESC_INDIRIZZO"));
						elencoDatiDomanda.add(domanda);
						
					}
					return elencoDatiDomanda;
				}
				
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandaInfo", e);
			throw new ErroreGestitoException("DaoException while trying to read DomandaInfo", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiDomanda;
	}

	@Override
	public List<DomandaInfo> getDomandeBeneficiarioDomanda(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto,
			String denominazioneEnteGiuridico, String partitaIva, String acronimoProgetto, String tipoSogg) {
		String prf = "[DomandaBeneficiarioDAOImpl::getDomandeBeneficiarioDomanda]";
		LOG.info(prf + " BEGIN");
		List<DomandaInfo> datiDomanda = null;
		LOG.info(prf + " Numero domanda: " + numeroDomanda + ", Descrizione stato domanda: " + numRelazDichSpesa + ", PEC beneficiario: " + pec + ", Codice fiscale soggetto: " + codiceFiscaleSoggetto + ", Denominazione ente giuridico: " + denominazioneEnteGiuridico + ", Partita IVA: " + partitaIva + ", Acronimo progetto: " + acronimoProgetto);
		try {
			String sql = "SELECT\r\n"
					+ "    DISTINCT pts.codice_fiscale_soggetto,\r\n"
					+ "    ptd.progr_bando_linea_intervento,\r\n"
					+ "	   ptd.numero_domanda,\r\n"
					+ "    pts.NDG,\r\n"
					+ "    pdsd.desc_stato_domanda,\r\n"
					+ "    prcema.QUOTA_IMPORTO_AGEVOLATO,\r\n"
					+ "    ptr.acronimo_progetto,\r\n"
					+ "    pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
					+ "    pts2.partita_iva,\r\n"
					+ "    ptr2.pec,\r\n"
					+ "    pti.desc_indirizzo,\r\n"
					+ "    pti.cap,\r\n"
					+ "    pdc.desc_comune,\r\n"
					+ "    pdp.sigla_provincia,\r\n"
					+ "    pdr.desc_regione,\r\n"
					+ "    pdn.desc_nazione\r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA  = ptd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.id_soggetto = prsd.id_soggetto\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.id_stato_domanda = ptd.ID_STATO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA  = ptd.id_domanda\r\n"
					+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE  prsDs ON prsds.PROGR_SOGGETTO_DOMANDA  = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_SEDE pts2 ON pts2.id_sede = prsds.id_sede\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr2 ON ptr2.id_recapiti = prsds.ID_RECAPITI\r\n"
					+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.id_indirizzo = prsds.id_indirizzo\r\n"
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc ON pdc.id_comune = pti.id_comune\r\n"
					+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.id_nazione = pti.id_nazione\r\n"
					+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp ON pdp.id_provincia = pti.id_provincia\r\n"
					+ "    LEFT JOIN PBANDI_D_REGIONE pdr ON pdr.ID_REGIONE = pdp.id_regione\r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS null\r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n";
				
			if(numRelazDichSpesa!=null || (acronimoProgetto!=null && acronimoProgetto.length()>0)) {				
				sql +="JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA \r\n";
			}
			if(numRelazDichSpesa!=null) {
					sql+= "JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON PRSP .ID_PROGETTO = ptds.ID_PROGETTO \r\n"; 
			}
					sql+= "WHERE\r\n"
					+ "     prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsds.ID_TIPO_SEDE = 1\r\n";

					BigDecimal idFondoFinpis =  null; 
					if(numeroDomanda!=null && numeroDomanda.length()>0) {sql += "	AND ptd.NUMERO_DOMANDA = :numeroDomanda \r\n";}
					
					if(codiceBando!=null ) {
						idFondoFinpis = getIdFondo(codiceBando);
						if(idFondoFinpis!=null) {
							codiceBando = idFondoFinpis; 
						}
						sql += "	AND ptd.progr_bando_linea_intervento = :codiceBando \r\n";}
					if(numRelazDichSpesa!=null) {
						sql += "	AND ptds.ID_DICHIARAZIONE_SPESA = :numRelazDichSpesa \r\n";}
					if(ndg!=null && ndg.length()>0) {
						sql += "	AND pts.NDG LIKE :ndg \r\n";
					}
					if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) {sql += "	AND pts.CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto \r\n";}
					if(denominazioneEnteGiuridico!=null && denominazioneEnteGiuridico.length()>0) 
						 {sql += "	AND UPPER (pteg.DENOMINAZIONE_ENTE_GIURIDICO) LIKE UPPER( '%'||:denominazioneEnteGiuridico||'%' ) \r\n";}
					if(partitaIva!=null && partitaIva.length()>0) {sql += "	AND pts2.PARTITA_IVA = :partitaIva \r\n";}
				
					if(acronimoProgetto!=null && acronimoProgetto.length()>0) {
						String elencoProgetti = getElencoProgetti(acronimoProgetto);
						if(elencoProgetti!=null) {							
							acronimoProgetto = elencoProgetti;
							sql += "	AND prsp.ID_PROGETTO IN ("+acronimoProgetto+") \r\n";
						} else {
							return new ArrayList<>();
						}
					}
//					if(acronimoProgetto!=null && acronimoProgetto.length()>0) {
//						String elencoProgetti = getElencoProgetti(acronimoProgetto);
//						acronimoProgetto = elencoProgetti;
//						if(acronimoProgetto!=null) {							
//							sql += "	AND prsp.ID_PROGETTO IN (:acronimoProgetto) \r\n";}
//						}
					//if(acronimoProgetto!=null && acronimoProgetto.length()>0) {sql += "	AND ptr.ACRONIMO_PROGETTO = :acronimoProgetto \r\n";}
					if(pec!=null && pec.length()>0) {sql += "	AND upper(ptr2.PEC) = upper(:pec) \r\n";}
					
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			
			if(numeroDomanda!=null && numeroDomanda.length()>0) { param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR); }
			if(codiceBando!=null) {param.addValue("codiceBando", codiceBando, Types.INTEGER); }
			if(numRelazDichSpesa!=null) {param.addValue("numRelazDichSpesa", numRelazDichSpesa, Types.INTEGER); }
			if(ndg!=null && ndg.length()>0) {param.addValue("ndg", "%"+ndg+"%", Types.VARCHAR); }
			if(pec!=null && pec.length()>0) { param.addValue("pec", pec, Types.VARCHAR); }
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) { param.addValue("codiceFiscaleSoggetto", codiceFiscaleSoggetto, Types.VARCHAR); }
			if(denominazioneEnteGiuridico!=null && denominazioneEnteGiuridico.length()>0) { 
				param.addValue("denominazioneEnteGiuridico", denominazioneEnteGiuridico, Types.VARCHAR); 
			}
			if(partitaIva!=null && partitaIva.length()>0) { param.addValue("partitaIva", partitaIva, Types.VARCHAR); }
			//if(acronimoProgetto!=null && acronimoProgetto.length()>0) { param.addValue("acronimoProgetto", acronimoProgetto, Types.VARCHAR); }
			
			datiDomanda = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DomandaInfo>>() {
				@Override
				public List<DomandaInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<DomandaInfo> elencoDatiDomanda = new ArrayList<>();
					while(rs.next()) {
						DomandaInfo domanda = new DomandaInfo();
						domanda.setTipoSogg(tipoSogg);
						domanda.setNumeroDomanda(rs.getString("numero_domanda"));
						domanda.setDescStatoDomanda(rs.getString("desc_stato_domanda"));
						domanda.setPec(rs.getString("pec"));
						domanda.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
						domanda.setDenominazioneEnteGiuridico(rs.getString("denominazione_ente_giuridico"));
						domanda.setPartitaIva(rs.getString("partita_iva"));
						domanda.setAcronimoProgetto(rs.getString("acronimo_progetto"));
						domanda.setCodiceBando(rs.getBigDecimal("progr_bando_linea_intervento"));
						domanda.setNdg(rs.getString("NDG"));
//						domanda.setImportoConcesso(rs.getBigDecimal("quota_importo_agevolato"));
						BigDecimal qia = rs.getBigDecimal("quota_importo_agevolato");
						if(qia!=null) {
							domanda.setImportoConcesso(qia);
						}else {
							domanda.setImportoConcesso(new BigDecimal(0));
						}
						domanda.setIndirizzo(rs.getString("DESC_INDIRIZZO"));
						domanda.setCAP(rs.getString("CAP"));
						domanda.setNazione(rs.getString("DESC_NAZIONE"));
						domanda.setRegione(rs.getString("DESC_REGIONE"));
						domanda.setComune(rs.getString("DESC_COMUNE"));
						domanda.setProvincia(rs.getString("SIGLA_PROVINCIA"));
						elencoDatiDomanda.add(domanda);
					}
					return elencoDatiDomanda;
				}
				
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandaInfo", e);
			throw new ErroreGestitoException("DaoException while trying to read DomandaInfo", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiDomanda;
	}

	@Override
	public List<DomandaInfo> getDomandeBeneficiarioFisicoDomanda(String numeroDomanda, BigDecimal numRelazDichSpesa,
			BigDecimal codiceBando, String ndg, String pec, String codiceFiscaleSoggetto, String nome, String cognome, String tipoSogg) {
		String prf = "[DomandaBeneficiarioDAOImpl::getDomandeBeneficiarioFisicoDomanda]";
		LOG.info(prf + " BEGIN");
		List<DomandaInfo> datiDomanda = null;
		LOG.info(prf + " Numero domanda: " + numeroDomanda + ", Descrizione stato domanda: " + numRelazDichSpesa + ", PEC beneficiario: " + pec + 
				", Codice fiscale soggetto: " + codiceFiscaleSoggetto + ", Nome: " + nome + ", Cognome: " + cognome);
		try {
			String sql = "SELECT \r\n"
					+ "    DISTINCT pts.codice_fiscale_soggetto,\r\n"
					+ "	   ptd.numero_domanda,\r\n"
					+ "    ptd.progr_bando_linea_intervento,\r\n"
					+ "    prcema.QUOTA_IMPORTO_AGEVOLATO, \r\n"
					+ "    pts.NDG,\r\n"
					+ "    pdsd.desc_stato_domanda,\r\n"
					+ "    ptr.acronimo_progetto,\r\n"
					+ "    ptr2.pec,\r\n"
					+ "    ptpf.NOME,\r\n"
					+ "    ptpf.COGNOME,\r\n"
					+ "    ptpf.DT_NASCITA, \r\n"
					+ "    pti.CAP, \r\n"
					+ "    pti.DESC_INDIRIZZO, \r\n"
				    + "    pdn.DESC_NAZIONE, \r\n"
				    + "    pdr.DESC_REGIONE , \r\n"
				    + "    pdp.DESC_PROVINCIA , \r\n"
				    + "    pdc.DESC_COMUNE  \r\n"
					+ " FROM \r\n"
					+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA  = ptd.ID_DOMANDA \r\n"
					+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.id_soggetto = prsd.id_soggetto\r\n"
					+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.id_stato_domanda = ptd.ID_STATO_DOMANDA\r\n"
					+ "    LEFT JOIN PBANDI_T_RAGGRUPPAMENTO ptr ON ptr.ID_DOMANDA  = ptd.id_domanda\r\n"
					+ "    LEFT JOIN PBANDI_T_RECAPITI ptr2 ON ptr2.id_recapiti = prsd.ID_RECAPITI_PERSONA_FISICA \r\n"
					+ "    LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.id_persona_fisica = prsd.ID_PERSONA_FISICA \r\n"
					+ "    LEFT JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptd.ID_DOMANDA = ptce.ID_DOMANDA AND ptce.DT_FINE_VALIDITA IS null\r\n"
					+ "    LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.ID_CONTO_ECONOMICO = ptce.ID_CONTO_ECONOMICO\r\n"
					+ "    LEFT JOIN PBANDI_T_INDIRIZZO pti ON pti.ID_INDIRIZZO =  prsd.ID_INDIRIZZO_PERSONA_FISICA \r\n"
					+ "    LEFT JOIN PBANDI_D_NAZIONE pdn ON pdn.ID_NAZIONE =  pti.ID_NAZIONE \r\n"
					+ "    LEFT JOIN PBANDI_D_REGIONE pdr  ON pdr.ID_REGIONE =  pti.ID_REGIONE  \r\n"
					+ "    LEFT JOIN PBANDI_D_PROVINCIA pdp  ON pdp.ID_PROVINCIA =  pti.ID_PROVINCIA \r\n" 
					+ "    LEFT JOIN PBANDI_D_COMUNE pdc  ON pdc.ID_COMUNE =  pti.ID_COMUNE \r\n";
			
			if(numRelazDichSpesa!=null) {
				sql +="JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON PRSP .ID_PROGETTO = ptds.ID_PROGETTO \r\n"; 
			}
			
				sql += " WHERE \r\n"
					+ "   prsd.ID_TIPO_ANAGRAFICA = 1 \r\n"
					+ "   AND prsd.ID_TIPO_BENEFICIARIO <> 4 \r\n";
			
			if(numeroDomanda!=null && numeroDomanda.length()>0) {sql += "	AND ptd.NUMERO_DOMANDA = :numeroDomanda \r\n";}
			if(codiceBando!=null) {
				BigDecimal idFondoFinpis = getIdFondo(codiceBando);
				if(idFondoFinpis!=null) {
					codiceBando = idFondoFinpis; 
				}
				sql += "	AND ptd.progr_bando_linea_intervento = :codiceBando \r\n";}
			if(numRelazDichSpesa!=null) {sql += "AND ptds.ID_DICHIARAZIONE_SPESA = :numRelazDichSpesa \r\n";}
			if(ndg!=null && ndg.length()>0) {
				sql += "	AND pts.NDG LIKE :ndg \r\n";
			} 
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) {sql += "	AND pts.CODICE_FISCALE_SOGGETTO = :codiceFiscaleSoggetto \r\n";}
			if(nome!=null && nome.length()>0) {sql += "	AND UPPER(ptpf.NOME) LIKE UPPER(:nome) \r\n";} 
			if(cognome!=null && cognome.length()>0) {sql += " AND UPPER(ptpf.COGNOME) LIKE UPPER(:cognome) \r\n";} 
			if(pec!=null && pec.length()>0) {sql += "	AND upper(ptr2.PEC) = upper(:pec) \r\n";}
			
			sql += " AND ptpf.COGNOME is not null\r\n"; 
			
			MapSqlParameterSource param = new MapSqlParameterSource(); 
	
			if(numeroDomanda!=null && numeroDomanda.length()>0) { param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR); }
			if(codiceBando!=null) {param.addValue("codiceBando", codiceBando, Types.INTEGER); }
			if(numRelazDichSpesa!=null) {param.addValue("numRelazDichSpesa", numRelazDichSpesa, Types.INTEGER); }
			if(ndg!=null && ndg.length()>0) {param.addValue("ndg", "%"+ndg+"%", Types.VARCHAR); }
			if(codiceFiscaleSoggetto!=null && codiceFiscaleSoggetto.length()>0) { param.addValue("codiceFiscaleSoggetto", codiceFiscaleSoggetto, Types.VARCHAR); }
			if(nome!=null && nome.length()>0) { param.addValue("nome", "%"+nome+"%", Types.VARCHAR); }
			if(cognome!=null && cognome.length()>0) { param.addValue("cognome", "%"+cognome+"%", Types.VARCHAR); }
			if(pec!=null && pec.length()>0) { param.addValue("pec", pec, Types.VARCHAR); }
			
			LOG.debug(sql);
			
			datiDomanda = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DomandaInfo>>() {
				@Override
				public List<DomandaInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<DomandaInfo> elencoDatiDomanda = new ArrayList<>();
					while(rs.next()) {
						DomandaInfo domanda = new DomandaInfo();
						domanda.setTipoSogg(tipoSogg);
						domanda.setNumeroDomanda(rs.getString("numero_domanda"));
						domanda.setDescStatoDomanda(rs.getString("desc_stato_domanda"));
						domanda.setPec(rs.getString("pec"));
						domanda.setCodiceFiscaleSoggetto(rs.getString("codice_fiscale_soggetto"));
						domanda.setNome(rs.getString("nome"));
						domanda.setCognome(rs.getString("cognome"));
						domanda.setCodiceBando(rs.getBigDecimal("progr_bando_linea_intervento"));
						domanda.setNdg(rs.getString("NDG"));
//						domanda.setImportoConcesso(rs.getBigDecimal("quota_importo_agevolato"));
						BigDecimal qia = rs.getBigDecimal("quota_importo_agevolato");
						if(qia!=null) {
							domanda.setImportoConcesso(qia);
						}else {
							domanda.setImportoConcesso(new BigDecimal(0));
						}
						domanda.setDtNascita(rs.getString("DT_NASCITA"));
						domanda.setIndirizzo(rs.getString("DESC_INDIRIZZO"));
						elencoDatiDomanda.add(domanda);
						
					}
					return elencoDatiDomanda;
				}
				
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DomandaInfo", e);
			throw new ErroreGestitoException("DaoException while trying to read DomandaInfo", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiDomanda;
	}
	
	

	@Override
	public String getNumeroDom(String numeroDomanda) {
		String prf = "[DomandaBeneficiarioDAOImpl::getNumeroDom]";
		LOG.info(prf + " BEGIN");
		String dati = null;
		LOG.info(prf + "numeroDomanda=" + numeroDomanda);
		try {

			String sql =" with selezione as (\r\n"
					+ "    SELECT\r\n"
					+ "        DISTINCT ptd.NUMERO_DOMANDA\r\n"
					+ "    FROM\r\n"
					+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "        INNER JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
					+ "    WHERE\r\n"
					+ "        upper (ptd.NUMERO_DOMANDA) LIKE upper  ('%' || :numeroDomanda || '%')\r\n"
					+ "        AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "        AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ ") "
					+ " select * "
					+ " from selezione "
					+ " where rownum < 50 ";
			 

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);   

			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<String>() {
				@Override
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {
					String elencoDati = null;
					while(rs.next())
					{
						String numDom= null;
						
						numDom = rs.getString("NUMERO_DOMANDA");
						
						
						elencoDati= numDom;

		}
					return elencoDati;
					
			}
			   });
		}catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return dati;
	}

	@Override
	public Integer getIdTipoSoggetto(String tipoSogg) {
		String prf = "[DomandaBeneficiarioDAOImpl::getIdTipoSoggetto]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + "tipoSogg=" + tipoSogg);
		Integer idTipoSogg = null;
		try {

			String sql ="SELECT id_tipo_soggetto FROM PBANDI_D_TIPO_SOGGETTO WHERE DESC_BREVE_TIPO_SOGGETTO = UPPER(:tipoSogg)"+
						"AND DT_FINE_VALIDITA IS NULL ";
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("tipoSogg", tipoSogg, Types.VARCHAR); 
			idTipoSogg = this.namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
					
		}catch (EmptyResultDataAccessException e) {
			LOG.warn(prf + "tipoSogg non trovato sul db");
		}catch (Exception e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idTipoSogg;
	}
	
	

}