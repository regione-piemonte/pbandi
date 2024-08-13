/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.dto.DocumentoIndexVO;
import it.csi.pbandi.pbweb.dto.RelazioneTecnicaDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.integration.dao.GestioneRelazioneTecnicaDAO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.FileUtil;

@Service
public class GestioneRelazioneTecnicaDAOImpl extends JdbcDaoSupport implements GestioneRelazioneTecnicaDAO  {

	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	  @Autowired
	    public GestioneRelazioneTecnicaDAOImpl(DataSource dataSource) {
	        setDataSource(dataSource);
	    }
		@Autowired
		DocumentoManager documentoManager;
		
	@Override
	public String getCodiceProgetto(Long idProgetto) {
	    String prf = "[GestioneRelazioneTecnicaDAOImpl::getCodiceProgetto]";
        LOG.info(prf + " BEGIN");
		
		String codiceProgetto = null ; 
		
		try {
			
			codiceProgetto = getJdbcTemplate().queryForObject("SELECT ptp.CODICE_VISUALIZZATO \r\n"
					+ "    FROM pbandi_t_progetto ptp \r\n"
					+ "    WHERE ptp.ID_PROGETTO ="+ idProgetto, String.class); 
			
		} catch (Exception e) {
			LOG.error("errore durante la lettura della tabella pbandi_t_progetto"+ e);
		}
		
		return codiceProgetto;
	}
	@Override
	public Object salvaRelazioneTecnica(MultipartFormDataInput multipartFormData,  HttpServletRequest req) {
		  String prf = "[GestioneRelazioneTecnicaDAOImpl::salvaRelazioneTecnica]";
	        LOG.info(prf + " BEGIN");
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			Boolean result; 
		
		try {
			
			// inserimento della relazione tecnica dentro la tabella t_relazione_tecnica
			
			
			
			String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
			File filePart = multipartFormData.getFormDataPart("file", File.class, null); 
			BigDecimal idProgetto = multipartFormData.getFormDataPart("idProgetto", BigDecimal.class, null); 
			int idTipoRelazioneTecnica = multipartFormData.getFormDataPart("idTipoRelazioneTecnica", Integer.class, null); 
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

			// in relatà dovrei fare questo dentro il servizio
			BigDecimal idTarget = insertRelazioneTecnica(idProgetto, userInfoSec.getIdUtente(), idTipoRelazioneTecnica);
			if(idTarget == null) {
				return false; 
			}
			
			
			String getEntita ="SELECT id_entita\r\n"
					+ "FROM PBANDI_C_ENTITA\r\n"
					+ "WHERE nome_entita ='PBANDI_T_RELAZIONE_TECNICA'\r\n";
			
			BigDecimal numEntita = getJdbcTemplate().queryForObject(getEntita, BigDecimal.class);
					
			// Legge il file firmato dal multipart.		
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");
			
			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = "+listInputPart.size());
			}
			
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = "+x);
				}
			}
			
			ArrayList<Long> elencoIdCategAnag = new ArrayList<Long>();
			elencoIdCategAnag.add((long)2);
			
			FileDTO file = new FileDTO(); 
			file.setBytes(FileUtil.getBytesFromFile(filePart));
					//FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
			
			//Long idtipoindex= (long) 40;
			String descBreveTipoDocIndex =  getJdbcTemplate().queryForObject("select DESC_BREVE_TIPO_DOC_INDEX "
					+ "from PBANDI_C_TIPO_DOCUMENTO_INDEX where ID_TIPO_DOCUMENTO_INDEX=15", String.class);	
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");					
			
			
			Date currentDate = new Date(System.currentTimeMillis());
			
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(15));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(userInfoSec.getIdUtente()));
			documentoIndexVO.setIdEntita(numEntita);
			//documentoIndexVO.setIdSoggettoBenef(idSoggetto);
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			//PBANDI_T_PROGETTO
			documentoIndexVO.setIdTarget(idTarget);				//id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
			
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		    result = documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, elencoIdCategAnag);
//		    salvaFileConVisibilita(byte[] file, PbandiTDocumentoIndexVO vo, ArrayList<Long> elencoIdCategAnag)
		   

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		

		return result;
	}
	private BigDecimal insertRelazioneTecnica(BigDecimal idProgetto, Long idUtente, int idTipoRelazioneTecnica) {
		 String prf = "[GestioneRelazioneTecnicaDAOImpl::insertRelazioneTecnica]";
	        LOG.info(prf + " BEGIN");
	        BigDecimal idRelazioneTecnica = null;
	        try {
	        	
	        	// controllo prima se estiste una relazione tecnica con questo idProgetto sul db
	        	String idPreviousRelTec = null;
	        try {				
	        	idPreviousRelTec =  getJdbcTemplate().queryForObject(" SELECT ptrt.ID_RELAZIONE_TECNICA  \r\n"
	        			+ "    FROM PBANDI_T_RELAZIONE_TECNICA ptrt \r\n"
	        			+ "    WHERE ptrt.ID_PROGETTO ="+idProgetto+ "\r\n"
	        			+ "    AND ptrt.DT_FINE_VALIDITA IS NULL ", String.class); 
			} catch (Exception e) {
				idPreviousRelTec = null; 
				LOG.error("progetto non presente nella tabella PBANDI_T_RELAZIONE_TECNICA: "+e);
			}	
	        	
	        	if(idPreviousRelTec != null) {
	        		getJdbcTemplate().update(" UPDATE PBANDI_T_RELAZIONE_TECNICA ptrt\r\n"
	        				+ "    SET ptrt.DT_FINE_VALIDITA=sysdate , \r\n"
	        				+ "    ptrt.DT_AGGIORNAMENTO =sysdate, \r\n"
	        				+ "    ptrt.ID_UTENTE_AGG = ? \r\n"
	        				+ "    WHERE ptrt.ID_RELAZIONE_TECNICA = ?",  new Object[] {
	        						idUtente,idPreviousRelTec});
	        	}
	        	
	        	idRelazioneTecnica =  getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_RELAZIONE_TECNICA.nextval FROM dual", BigDecimal.class);
	        	
	        	// inserimento
	        	String insert ="insert into PBANDI_T_RELAZIONE_TECNICA (ID_RELAZIONE_TECNICA,\r\n"
	        			+ "ID_TIPO_RELAZIONE_TECNICA,\r\n"
	        			+ "ID_PROGETTO,\r\n"
	        			+ "DT_INIZIO_VALIDITA,\r\n"
	        			+ "ID_UTENTE_INS,\r\n"
	        			+ "DT_INSERIMENTO\r\n"
						+ ") values(?,?,?,trunc(sysdate),?,trunc(sysdate))";
				getJdbcTemplate().update(insert, new Object [] {idRelazioneTecnica,  idTipoRelazioneTecnica, idProgetto, idUtente	});
				
			} catch (Exception e) {
				LOG.error("errore durante l'inserimento della relazione tecnica: "+ e);
			}
	        
		return idRelazioneTecnica;
	}
	@Override
	public Object getRelazioneTecnica(Long idProgetto, int idTipoRelazioneTecnica, HttpServletRequest req) {
		 String prf = "[GestioneRelazioneTecnicaDAOImpl::getRelazioneTecnica]";
	        LOG.info(prf + " BEGIN");
	     //   DocumentoIndexVO result = new  DocumentoIndexVO();
	        RelazioneTecnicaDTO objResult = new RelazioneTecnicaDTO(); 
	        List<RelazioneTecnicaDTO> lista = new ArrayList<RelazioneTecnicaDTO>();
	        try {
	        	
	        	String sql = "SELECT\r\n"
	        			+ "   pti.ID_DOCUMENTO_INDEX,\r\n"
	        			+ "   pti.NOME_FILE,\r\n"
	        			+ "   ptrt.ID_RELAZIONE_TECNICA,\r\n"
	        			+ "   ptrt.id_progetto, \r\n"
	        			+ "	  ptrt.FLAG_VALIDATO,\r\n"
	        			+ "   ptrt.NOTE\r\n"
	        			+ "FROM\r\n"
	        			+ "   PBANDI_T_RELAZIONE_TECNICA ptrt\r\n"
	        			+ "   LEFT JOIN PBANDI_T_DOCUMENTO_INDEX pti ON pti.ID_TARGET = ptrt.ID_RELAZIONE_TECNICA\r\n"
	        			+ "WHERE\r\n"
	        			+ "   ptrt.ID_PROGETTO = ?\r\n"
	        			+ "   AND ptrt.ID_TIPO_RELAZIONE_TECNICA = ?\r\n"
	        			+ "   --AND ptrt.DT_FINE_VALIDITA IS NULL\r\n"
	        			+ "   AND pti.ID_ENTITA = 764\r\n"
	        			+ "   AND pti.ID_TIPO_DOCUMENTO_INDEX = 15\r\n"
	        			+ "  order by ptrt.ID_RELAZIONE_TECNICA DESC";
	        	
	        	RowMapper<DocumentoIndexVO> rowmap = new RowMapper<DocumentoIndexVO>() {
					
					@Override
					public DocumentoIndexVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						DocumentoIndexVO obj = new  DocumentoIndexVO();
						obj.setIdTarget(rs.getBigDecimal("ID_RELAZIONE_TECNICA"));
						obj.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
						obj.setNomeFile(rs.getString("NOME_FILE"));
						obj.setIdProgetto(rs.getBigDecimal("id_progetto"));
						obj.setNoteDocumentoIndex(rs.getString("NOTE"));
						obj.setFlagFirmaCartacea(rs.getString("FLAG_VALIDATO"));
						return obj;
					}
				};
				
				RowMapper<RelazioneTecnicaDTO> rowmapRelTec = new RowMapper<RelazioneTecnicaDTO>() {
					
					@Override
					public RelazioneTecnicaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						RelazioneTecnicaDTO obj = new RelazioneTecnicaDTO(); 
						
						obj.setIdRelazioneTecnica(rs.getBigDecimal("ID_RELAZIONE_TECNICA"));
						obj.setIdDocumentoIndex(rs.getBigDecimal("ID_DOCUMENTO_INDEX"));
						obj.setNomeFile(rs.getString("NOME_FILE"));
						obj.setIdProgetto(rs.getBigDecimal("id_progetto"));
						obj.setNote(rs.getString("NOTE"));
						obj.setFlagValidato(rs.getString("FLAG_VALIDATO"));
						
						return obj;
					}
				};
				
				lista =  getJdbcTemplate().query(sql, rowmapRelTec, new Object[] {idProgetto, idTipoRelazioneTecnica});
				if(lista.size()>0)
					objResult = lista.get(0); 
				
			} catch (Exception e) {
				LOG.error("erore lettura tabella PBANDI_T_RELAZIONE_TECNICA: "+ e);
			}
		return objResult;
	}
	@Override
	public Object validaRifiutaRelazioneTecnica(Long idRelazioneTecnica, String flagConferma,String nota, HttpServletRequest req) {
			 String prf = "[GestioneRelazioneTecnicaDAOImpl::getRelazioneTecnica]";
		        LOG.info(prf + " BEGIN");
		        Boolean result = true; 
		        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		        try {
		        	
		        	String update = " UPDATE \r\n"
		        			+ " PBANDI_T_RELAZIONE_TECNICA ptrt \r\n"
		        			+ " SET ptrt.FLAG_VALIDATO =?, \r\n"
		        			+ " ptrt.ID_UTENTE_AGG =?, \r\n"
		        			+ " ptrt.DT_AGGIORNAMENTO = sysdate , \r\n"
		        			+ " ptrt.NOTE =?, \r\n"
		        			+ " ptrt.DT_FINE_VALIDITA = SYSDATE \r\n"
		        			+ " WHERE ptrt.ID_RELAZIONE_TECNICA  =?"; 
		        	
		        	getJdbcTemplate().update(update,  new Object[] { flagConferma.trim(), userInfoSec.getIdUtente(), nota, idRelazioneTecnica});
					
				} catch (Exception e) {
					// TODO: handle exception
					result = false; 
					LOG.error("errore validazione della relazione tecnica ");
				}
		        
		return result;
	}

}
