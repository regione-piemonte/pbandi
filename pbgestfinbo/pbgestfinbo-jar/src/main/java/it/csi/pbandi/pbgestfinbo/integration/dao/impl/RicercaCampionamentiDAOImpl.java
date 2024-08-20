/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.filestorage.util.Constants;
import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.profilazione.StoricoRicercaCampionamentiDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.RicercaCampionamentiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.AttivitaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.search.StoricoRicerccaCampionamentiRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.RicercaCampionamentiVO;

public class RicercaCampionamentiDAOImpl  extends JdbcDaoSupport implements RicercaCampionamentiDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME); 
	
	
	@Autowired
	public RicercaCampionamentiDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<StoricoRicercaCampionamentiDTO> getListaCampionamenti(RicercaCampionamentiVO campioneVO) {
		String prf = "[RicercaCampionamentiDAOImpl::getListaCampionamenti]";
		LOG.info(prf + "BEGIN");
		
		List<StoricoRicercaCampionamentiDTO> lista = new ArrayList<StoricoRicercaCampionamentiDTO>(); 
		
		try {
			
			
			StringBuilder sql = new StringBuilder(); 
			sql.append("SELECT \r\n"
					+ "	DISTINCT  PTCF .ID_CAMPIONAMENTO , \r\n"
					+ "	   PTCF .DESC_CAMPIONAMENTO , \r\n"
					+ "	   pdtc .DESC_TIPO_CAMP,\r\n"
					+ "	   ptpf .nome ,\r\n"
					+ "	   ptpf .cognome ,\r\n"
					+ "	   PTCF .NUM_PROGETTI_SEL ,\r\n"
					+ "	   PTCF .IMPORTO_VALIDATO , \r\n"
					+ "	   PTCF .DT_CAMPIONAMENTO , \r\n"
					+ "	   PTCF .PERC_ESTRATTA ,\r\n"
					+ "	   PTCF .IMP_VAL_PERC_ESTRATTA , \r\n"
					+ "	   PTCF .DT_INIZIO_VALIDITA ,\r\n"
					+ "	   PTCF .DT_FINE_VALIDITA ,\r\n"
					+ "	   PTCF .ID_UTENTE_INS ,\r\n"
					+ "	   PTCF .ID_UTENTE_AGG ,\r\n"
					+ "	   PTCF .ID_FASE_CAMP \r\n"
					+ "FROM PBANDI_T_CAMPIONAMENTO_FINP ptcf , \r\n"
					+ "	   PBANDI_T_PERSONA_FISICA ptpf , \r\n"
					+ "	   PBANDI_D_TIPO_CAMP pdtc, \r\n"
					+ "	   PBANDI_D_CAMP_STATO pdcs , \r\n"
					+ "	   PBANDI_T_UTENTE ptu \r\n"
					+ "WHERE PTCF.ID_TIPO_CAMP  = pdtc.ID_TIPO_CAMP \r\n"
					+ "and ptcf.id_tipo_camp = pdtc.id_tipo_camp\r\n"
					+ "AND PTCF .ID_UTENTE_INS = ptu.ID_UTENTE\r\n"
					+ "AND ptu.ID_SOGGETTO = ptpf .id_soggetto\r\n"
					+ "AND PTPF .ID_PERSONA_FISICA  IN  (SELECT MAX(ID_PERSONA_FISICA) \r\n"
					+ "								FROM PBANDI_T_PERSONA_FISICA ptpf2, PBANDI_T_UTENTE ptu2\r\n"
					+ "    							WHERE ptu2.ID_UTENTE = ptcf .ID_UTENTE_INS \r\n"
					+ "								AND PTPF2 .ID_SOGGETTO = ptu2.ID_SOGGETTO) \r\n");
			
			
			//SEQ_PBANDI_T_CAMPIONAMENT_FINP
			if(campioneVO.getDataInizio()!=null) {
				sql.append("AND TRUNC(ptcf.DT_CAMPIONAMENTO)>=TO_DATE('"+campioneVO.getDataInizio()+"', 'yyyy-mm-dd') ");
			}
			if (campioneVO.getDataFine()!=null) {
				sql.append("AND TRUNC(ptcf.DT_CAMPIONAMENTO)<=TO_DATE('"+campioneVO.getDataFine()+"', 'yyyy-mm-dd') ");
			}
			if(campioneVO.getDescrizione()!=null) {
				sql.append(" AND  UPPER(ptcf.DESC_CAMPIONAMENTO) LIKE UPPER('%"+campioneVO.getDescrizione()+"%')\r\n"); 
			}
			if (campioneVO.getIdUtenteCampionamento()!=null) {
				sql.append(" AND ptpf.id_soggetto = "+ campioneVO.getIdUtenteCampionamento()+ "\r\n");
			} 
			if (campioneVO.getIdStatoCampionamento()!=null) {
				
				if(campioneVO.getIdStatoCampionamento()==2) {
					sql.append(" AND ptcf.FLAG_ANNULLATO is null \r\n");
				}
				if (campioneVO.getIdStatoCampionamento()==3) {
					sql.append("AND ptcf.FLAG_ANNULLATO ='S' \r\n");
				}
			}
			if (campioneVO.getIdTipologiaSelezione()!= null) {
				sql.append(" AND ptcf.id_tipo_camp = "+ campioneVO.getIdTipologiaSelezione()+ "\r\n");
			}
			
			sql.append(" ORDER by PTCF .ID_CAMPIONAMENTO DESC  \r\n");
				
			lista = (ArrayList<StoricoRicercaCampionamentiDTO>)getJdbcTemplate().query(sql.toString(), new StoricoRicerccaCampionamentiRowMapper()); 
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_CAMPIONAMENTO_FINP", e);
		}finally {
			LOG.info(prf + " END");
		}
		
		return lista;
	}

	@Override
	public List<AttivitaDTO> getListaTipolgie() {
		String prf = "[RicercaCampionamentiDAOImpl::getListaTipolgie ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "select ID_TIPO_CAMP AS ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "DESC_TIPO_CAMP AS DESC_ATT_MONIT_CRED\r\n"
					+ "from PBANDI_D_TIPO_CAMP pdtc ";
			
			listaAttivita = (ArrayList<AttivitaDTO>) getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
		}finally {
			LOG.info(prf + " END");
			}
		
	
		return listaAttivita;
	}

	@Override
	public List<AttivitaDTO> getListaStati() {	
	String prf = "[RicercaCampionamentiDAOImpl::getListaStati ]";
	LOG.info(prf + "BEGIN");
	
	ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

	try {
		String sql = "select ID_STATO_CAMP  AS ID_ATTIVITA_MONIT_CRED,\r\n"
				+ "          DESC_STATO_CAMP AS DESC_ATT_MONIT_CRED\r\n"
				+ "from PBANDI_D_CAMP_STATO ";
		
		listaAttivita = (ArrayList<AttivitaDTO>) getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
		
		
	} catch (Exception e) {
		LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
	}finally {
		LOG.info(prf + " END");
		}
	
	return listaAttivita;
	}

	@Override
	public List<AttivitaDTO> getListaUtenti(String string) {
		
		String prf = "[RicercaCampionamentiDAOImpl::listaUtenti ]";
		LOG.info(prf + "BEGIN");
		
		List<AttivitaDTO> listaDenominazioni = new ArrayList<AttivitaDTO>();
	
		try {
			
			String query = "SELECT persfis.COGNOME AS desc_1 ,persfis.NOME AS desc_2, PERSFIS .ID_SOGGETTO \r\n"
					+ "	FROM\r\n"
					+ "	PBANDI_T_PERSONA_FISICA persfis\r\n"
					+ " where  UPPER(persfis.nome) LIKE UPPER('%"+ string +"%')\r\n"
					+ " OR  UPPER(persfis.cognome) LIKE UPPER('%"+ string +"%')\r\n"
							+ " AND  ROWNUM <= 100 \r\n"
					+ "UNION \r\n"
					+ "SELECT entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS desc_1, '' AS desc_2, ENTEGIU .ID_SOGGETTO \r\n"
					+ "FROM PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
					+ " where  UPPER(entegiu.DENOMINAZIONE_ENTE_GIURIDICO) LIKE UPPER('%"+ string +"%')\r\n"
							+ " AND  ROWNUM <= 100";
			
			RowMapper<AttivitaDTO> lista =  new RowMapper<AttivitaDTO>() {
				
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO denom = new AttivitaDTO(); 
					denom.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					denom.setCognome(rs.getString("desc_1"));
					denom.setNome(rs.getString("desc_2"));
					if(denom.getNome() == null)
						denom.setDescAttivita(denom.getCognome());
					else
						denom.setDescAttivita(denom.getCognome() + " "+denom.getNome());
					return denom;
				}
			};
			
			listaDenominazioni =  getJdbcTemplate().query(query, lista); 
			
		} catch (Exception e) {
			
			LOG.error(prf + "Exception while trying to read PBANDI_T_ENTE_GIURIDICO", e);
			}finally {
				LOG.info(prf + " END");
		} 
		
		return listaDenominazioni;
	}

	@Override
	public List<AttivitaDTO> getListaBandi(String string) {
		String prf = "[RicercaCampionamentiDAOImpl::getListaBandi ]";
		LOG.info(prf + "BEGIN");
		
		List<AttivitaDTO> listaBandi = new ArrayList<AttivitaDTO>();
		
		try {
			
			String sql = "SELECT DISTINCT pbi.PROGR_BANDO_LINEA_INTERVENTO,pbi.NOME_BANDO_LINEA \r\n"
					+ "FROM \r\n"
					+ "PBANDI_R_BANDO_LINEA_ENTE_COMP pbl,\r\n"
					+ "PBANDI_R_BANDO_LINEA_INTERVENT pbi\r\n"
					+ "WHERE pbl.ID_RUOLO_ENTE_COMPETENZA =1\r\n"
					+ "AND pbl.ID_ENTE_COMPETENZA =2\r\n"
					+ "AND pbi.PROGR_BANDO_LINEA_INTERVENTO =pbl.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "	AND  UPPER(pbi.NOME_BANDO_LINEA) LIKE UPPER('%"+ string +"%')\r\n"
					+ "AND  ROWNUM <= 100";
			
			RowMapper<AttivitaDTO> lista =  new RowMapper<AttivitaDTO>() {		
				@Override
				public AttivitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AttivitaDTO bando = new AttivitaDTO(); 
					bando.setDescAttivita(rs.getString("NOME_BANDO_LINEA"));
					bando.setIdAttivita(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
					bando.setProgBandoLinea(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
					return bando;
				}
			};
			
			listaBandi =  getJdbcTemplate().query(sql, lista); 
		
			
		} catch (Exception e) {
			
			LOG.error(prf + "Exception while trying to read PBANDI_T_BANDO", e);
			}finally {
				LOG.info(prf + " END");
		}
			
		return listaBandi;
	}

	@Override
	public List<AttivitaDTO> getTipoDichiaraziSpesa() {
		String prf = "[RicercaCampionamentiDAOImpl::getTipoDichiaraziSpesa ]";
		LOG.info(prf + "BEGIN");
		
		ArrayList <AttivitaDTO> listaAttivita =  new ArrayList<AttivitaDTO>(); 

		try {
			String sql = "select ID_TIPO_DICHIARAZ_SPESA  AS ID_ATTIVITA_MONIT_CRED,\r\n"
					+ "          DESC_TIPO_DICHIARAZIONE_SPESA AS DESC_ATT_MONIT_CRED\r\n"
					+ " from PBANDI_D_TIPO_DICHIARAZ_SPESA ";
			
			listaAttivita = (ArrayList<AttivitaDTO>) getJdbcTemplate().query(sql, new AttivitaRowMapper()); 
			
			
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_D_ATTIVITA_MONIT_CRED", e);
		}finally {
			LOG.info(prf + " END");
			}
		
		return listaAttivita;
		}

}
