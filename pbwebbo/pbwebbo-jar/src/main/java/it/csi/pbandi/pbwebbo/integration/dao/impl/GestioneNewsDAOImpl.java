/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbwebbo.util.BeanUtil;
import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.dto.gestionenews.AvvisoDTO;
import it.csi.pbandi.pbwebbo.dto.gestionenews.InizializzaGestioneNewsDTO;
import it.csi.pbandi.pbwebbo.integration.dao.GestioneNewsDAO;
import it.csi.pbandi.pbwebbo.integration.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbwebbo.integration.vo.PbandiTNewsVO;
import it.csi.pbandi.pbwebbo.util.BeanRowMapper;
import it.csi.pbandi.pbwebbo.util.Constants;

@Service
public class GestioneNewsDAOImpl extends JdbcDaoSupport implements GestioneNewsDAO {
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	public GestioneNewsDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	protected BeanUtil beanUtil;

	@Override
	public InizializzaGestioneNewsDTO inizializzaGestioneNews(Long idUtente, String idIride) throws InvalidParameterException, Exception {		
		String prf = "[GestioneNewsDAOImpl::inizializzaGestioneNews] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idUtente = "+idUtente);
				
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		InizializzaGestioneNewsDTO result = new InizializzaGestioneNewsDTO();
		try {
			
			// Popola la droplist dei tipi di anagrafica associabili ad un avviso.
			result.setTipiAnagrafica(this.tipiAnagrafica());
			
			result.setAvvisi(this.elencoNews());
			
			LOG.info(prf + result.toString());

		} catch (Exception e) {
			String msg = "Errore durante la inizializzazione.";
			LOG.error(prf+msg, e);
			throw new Exception(msg, e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return result;
	}
	
	private ArrayList<CodiceDescrizioneDTO> tipiAnagrafica()  {
		String prf = "[GestioneNewsDAOImpl::inizializzaGestioneNews] ";
		
		String sql = "";
		sql += "SELECT ID_TIPO_ANAGRAFICA, DESC_BREVE_TIPO_ANAGRAFICA, DESC_TIPO_ANAGRAFICA, "; 
		sql += "DT_INIZIO_VALIDITA, DT_FINE_VALIDITA, ID_RUOLO_HELP, ID_CATEG_ANAGRAFICA "; 
		sql += "FROM PBANDI_D_TIPO_ANAGRAFICA ORDER BY ID_TIPO_ANAGRAFICA";		
		LOG.info(prf + "\n" + sql);
		
		List<PbandiDTipoAnagraficaVO> tipiAnagraficaVO = getJdbcTemplate().query(sql, new BeanRowMapper(PbandiDTipoAnagraficaVO.class));
		
		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		//CodiceDescrizioneDTO dto = new CodiceDescrizioneDTO();
		//dto.setId(-1L);
		//dto.setDescrizione("TUTTI");
		//dto.setDescrizioneBreve("TUTTI");
		//result.add(dto);
		
		if (tipiAnagraficaVO != null) {
			for(PbandiDTipoAnagraficaVO vo : tipiAnagraficaVO) {
				CodiceDescrizioneDTO dto = new CodiceDescrizioneDTO();
				dto.setId(vo.getIdTipoAnagrafica());
				dto.setDescrizione(vo.getDescTipoAnagrafica());
				dto.setDescrizioneBreve(vo.getDescBreveTipoAnagrafica());
				result.add(dto);
			}
		}
		
		return result;
	}
	
	private ArrayList<AvvisoDTO> elencoNews()  {
		String prf = "[GestioneNewsDAOImpl::elencoNews] ";
		
		String sql = "";
		sql += "SELECT ID_NEWS, TITOLO, DESCRIZIONE, URL_PAGINA, "; 
		sql += "DT_INIZIO, DT_FINE, UTENTE_INS, TIPO_NEWS "; 
		sql += "FROM PBANDI_T_NEWS ORDER BY DT_INIZIO DESC";		
		LOG.info(prf + "\n" + sql);
		
		List<PbandiTNewsVO> newsVO = getJdbcTemplate().query(sql, new BeanRowMapper(PbandiTNewsVO.class));
		
		ArrayList<AvvisoDTO> result = new ArrayList<AvvisoDTO>();		
		if (newsVO != null) {
			List<AvvisoDTO> lista = beanUtil.transformList(newsVO, AvvisoDTO.class);
			result = new ArrayList<AvvisoDTO>(lista);
			for (AvvisoDTO vo : result)
				vo.setTipiAnagrafica(this.tipiAnagraficaAssociati(vo.getIdNews()));
		}
		
		return result;
	}
	
	private ArrayList<CodiceDescrizioneDTO> tipiAnagraficaAssociati(Long idNews)  {
		String prf = "[GestioneNewsDAOImpl::tipiAnagraficaAssociati] ";
		LOG.info(prf + "idNews = "+idNews);
		
		if (idNews == null)
			return new ArrayList<CodiceDescrizioneDTO>();
		
		String sql = "";
		sql += "SELECT d.ID_TIPO_ANAGRAFICA, d.DESC_TIPO_ANAGRAFICA, d.DESC_BREVE_TIPO_ANAGRAFICA "; 
		sql += "FROM PBANDI_R_NEWS_TIPO_ANAGRAFICA r, PBANDI_D_TIPO_ANAGRAFICA d "; 
		sql += "WHERE r.ID_NEWS="+idNews+" AND d.ID_TIPO_ANAGRAFICA=r.ID_TIPO_ANAGRAFICA";		
		LOG.info(prf + "\n" + sql);
		
		List<PbandiDTipoAnagraficaVO> tipiAnagraficaVO = getJdbcTemplate().query(sql, new BeanRowMapper(PbandiDTipoAnagraficaVO.class));
		
		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		if (tipiAnagraficaVO != null) {
			for(PbandiDTipoAnagraficaVO vo : tipiAnagraficaVO) {
				CodiceDescrizioneDTO dto = new CodiceDescrizioneDTO();
				dto.setId(vo.getIdTipoAnagrafica());
				dto.setDescrizione(vo.getDescTipoAnagrafica());
				dto.setDescrizioneBreve(vo.getDescBreveTipoAnagrafica());
				result.add(dto);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	// Inserisce\modifica un avviso in PBANDI_T_NEWS e i tipi anagrafica associati in PBANDI_R_NEWS_TIPO_ANAGRAFICA.
	public Boolean aggiornaAvviso(AvvisoDTO avvisoDTO, Long idUtente, String idIride) throws InvalidParameterException, Exception {		
		String prf = "[GestioneNewsDAOImpl::aggiornaAvviso] ";
		LOG.info(prf + " BEGIN");
		
		if (avvisoDTO == null) {
			throw new InvalidParameterException("avvisoDTO non valorizzato.");
		}
		if (avvisoDTO.getTipiAnagrafica() == null || avvisoDTO.getTipiAnagrafica().size() == 0) {
			throw new InvalidParameterException("nessun destinatario valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		LOG.info(prf + avvisoDTO.toString());
		LOG.info(prf + "idUtente = "+idUtente);
		
		try {
			
			if (avvisoDTO.getIdNews() == null) {
				
				// Legge il nuovo id dalla sequence.
				String sqlSeq = "select SEQ_PBANDI_T_NEWS.nextval from dual ";
				logger.info("\n"+sqlSeq.toString());
				Long newId = (Long) getJdbcTemplate().queryForObject(sqlSeq.toString(), Long.class);
				logger.info("Nuovo id PBANDI_T_NEWS = "+newId);
				
				avvisoDTO.setIdNews(newId);
				
				// Inserisce l'avviso.
				String sqlInsert = "";
				sqlInsert += "INSERT INTO PBANDI_T_NEWS ";
				sqlInsert += "(ID_NEWS, TITOLO, DESCRIZIONE, URL_PAGINA, DT_INIZIO, DT_FINE, UTENTE_INS, TIPO_NEWS) ";
				sqlInsert += "VALUES (?,?,?,?,?,?,?,?)";		
				LOG.info(prf + "\n" + sqlInsert);
				
				Object[] params = new Object[8];
				params[0] = newId;
				params[1] = avvisoDTO.getTitolo();
				params[2] = avvisoDTO.getDescrizione();
				params[3] = avvisoDTO.getUrlPagina();
				params[4] = avvisoDTO.getDtInizio();
				params[5] = avvisoDTO.getDtFine();
				params[6] = idUtente;
				params[7] = avvisoDTO.getTipoNews();
				
				getJdbcTemplate().update(sqlInsert, params);
				
			} else {
				
				// Modifica l'avviso.
				String sqlUpdate = "";
				sqlUpdate += "UPDATE PBANDI_T_NEWS ";
				sqlUpdate += "SET TITOLO=?, DESCRIZIONE=?, URL_PAGINA=?, DT_INIZIO=?, DT_FINE=?, TIPO_NEWS=? WHERE ID_NEWS=? ";
				LOG.info(prf + "\n" + sqlUpdate);
				
				Object[] params = new Object[7];
				params[0] = avvisoDTO.getTitolo();
				params[1] = avvisoDTO.getDescrizione();
				params[2] = avvisoDTO.getUrlPagina();
				params[3] = avvisoDTO.getDtInizio();
				params[4] = avvisoDTO.getDtFine();
				params[5] = avvisoDTO.getTipoNews();
				params[6] = avvisoDTO.getIdNews();
				
				getJdbcTemplate().update(sqlUpdate, params);
				
				// Cancella i destinatari attuali.
				String sqlDelete = "DELETE FROM PBANDI_R_NEWS_TIPO_ANAGRAFICA WHERE ID_NEWS = "+avvisoDTO.getIdNews();
				LOG.info(prf + "\n" + sqlDelete);
				getJdbcTemplate().update(sqlDelete);
				
			}
			
			
			// Inserisce i nuovi destinatari.
			for ( CodiceDescrizioneDTO dto : avvisoDTO.getTipiAnagrafica()) {
				
				String sqlInsertDestinatari = "";
				sqlInsertDestinatari += "INSERT INTO PBANDI_R_NEWS_TIPO_ANAGRAFICA (ID_NEWS, ID_TIPO_ANAGRAFICA) ";
				sqlInsertDestinatari += "VALUES ("+avvisoDTO.getIdNews()+", "+dto.getId()+")";
				
				getJdbcTemplate().update(sqlInsertDestinatari);
			}
			

		} catch (Exception e) {
			String msg = "Errore durante l'aggiornamento dell'avviso.";
			LOG.error(prf+msg, e);
			throw new Exception(msg, e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return true;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	// Cancella logicamente un avviso in PBANDI_T_NEWS.
	public Boolean eliminaAvviso(Long idNews, Long idUtente, String idIride) throws InvalidParameterException, Exception {		
		String prf = "[GestioneNewsDAOImpl::eliminaAvviso] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idNews = " + idNews + "; idUtente = " + idUtente);
		
		if (idNews == null) {
			throw new InvalidParameterException("idNews non valorizzato.");	
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		
		try {
			
			// Cancellazione logica.
			String sqlUpdate = "";
			sqlUpdate += "UPDATE PBANDI_T_NEWS SET DT_FINE = SYSDATE WHERE ID_NEWS= "+ idNews;
			LOG.info(prf + "\n" + sqlUpdate);
			getJdbcTemplate().update(sqlUpdate);
			
			/*
			// Cancella i destinatari associati.
			String sqlDelete1 = "DELETE FROM PBANDI_R_NEWS_TIPO_ANAGRAFICA WHERE ID_NEWS = "+idNews;
			LOG.info(prf + "\n" + sqlDelete1);
			getJdbcTemplate().update(sqlDelete1);
			
			// Cancella l'avviso.
			String sqlDelete2 = "DELETE FROM PBANDI_T_NEWS WHERE ID_NEWS = "+idNews;
			LOG.info(prf + "\n" + sqlDelete2);
			getJdbcTemplate().update(sqlDelete2);
			*/
			
		} catch (Exception e) {
			String msg = "Errore durante la cancellazione dell'avviso.";
			LOG.error(prf+msg, e);
			throw new Exception(msg, e);
		}
		finally {
			LOG.info(prf+" END");
		}
		
		return true;
	}
	
}
