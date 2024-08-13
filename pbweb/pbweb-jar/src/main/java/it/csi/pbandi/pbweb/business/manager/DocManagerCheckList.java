/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.util.StringUtil;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.CheckListDocumentaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.CheckListInLocoDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.manager.DocumentNotCreatedException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import javax.sql.DataSource;

public class DocManagerCheckList extends JdbcDaoSupport {

	@Autowired
	public DocManagerCheckList(DataSource dataSource) {
		setDataSource(dataSource);
	}

	private Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private BeanUtil beanUtil;
	
	@Autowired
	private it.csi.pbandi.pbweb.pbandisrv.business.manager.DocumentoManager documentoManagerPB;
	
	
	@Autowired
	private it.csi.pbandi.pbweb.business.manager.DocumentoManager documentoManager;
	
	@Autowired
	private DecodificheManager decodificheManager;
	
	public PbandiTDocumentoIndexVO creaDocumentoCLloco(Long idUtente, CheckListInLocoDTO dto,
			String codStatoTipoDocIndex,String shaHex, Long idRappLegale, Long idDelegato) throws DocumentNotCreatedException {
		try {
			
			String nomeFile = documentoManagerPB.getNomeFile(dto);			 //QUI checkList_IDCHECKLIST_DATA.pdf
			//beanUtil.setPropertyValueByName(dto, "nomeFile", nomeFile);
			logger.info(" nomeFile = "+nomeFile);
			
			
			BigDecimal idTDocumentoIndex = nuovoIdTDocumentoIndex();
			if (idTDocumentoIndex == null) {
				logger.error("Nuovo ID PBANDI_T_DOCUMENTO_INDEX non generato.");
				throw new DocumentNotCreatedException("Errore nel reperimento della chiave della documento index");
			}
			
			String nomeDocumento = nomeFile.replaceFirst("\\.", "_"+idTDocumentoIndex.longValue()+".");
			logger.info("nomeDocumento="+nomeDocumento);
						
			String tipoDocIndex = documentoManagerPB.getTipoDocIndex(dto.getClass());
			logger.info( "tipoDocIndex=" + tipoDocIndex);
			
			// PASSO 1 : Salvataggio file su file system.
			InputStream is = new ByteArrayInputStream(dto.getBytesModuloPdf());
			boolean esitoSalva = documentoManager.salvaSuFileSystem(is, nomeDocumento, tipoDocIndex);
			if (!esitoSalva) {
				logger.error("Salvataggio su file system fallito: termino la procedura.");
				throw new DocumentNotCreatedException("Salvataggio su file system fallito");
			}
			
			Class<? extends it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO> classeEntita = documentoManagerPB.getEntitaPerDocumento(dto.getClass());
			logger.info( "classeEntita= "+classeEntita);
			
			return salvaInfoNodoIndexSuDbCLloco( idTDocumentoIndex, nomeFile, nomeDocumento,
										  beanUtil.getPropertyValueByName(dto,documentoManagerPB.getNomeProprietaChiave(classeEntita),	BigDecimal.class), 
										  idRappLegale, 
										  idDelegato, 
										  beanUtil.getPropertyValueByName( dto, "idProgetto", BigDecimal.class),
										  tipoDocIndex, 
										  classeEntita, 
										  codStatoTipoDocIndex, 
										  beanUtil.getPropertyValueByName(dto,"versione", BigDecimal.class),
										  idUtente,
										  shaHex);

		} catch (Exception e) {
			throw new DocumentNotCreatedException(e.getMessage());
		}

	}
	
	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDbCLloco(BigDecimal idDocumentoIndex, String nomeFile, String nomeDocumento, 
			BigDecimal idTarget, Long idRappLegale, Long idDelegato, BigDecimal idProgetto,
			String tipoDocIndex, Class<? extends it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex) {
		logger.info(" BEGIN");
		
		logger.info(" idDocumentoIndex: " + idDocumentoIndex);
		logger.info(" nomeFile: " + nomeFile);
		logger.info(" nomeDocumento: " + nomeDocumento);
		logger.info(" idTarget: " + idTarget);
		logger.info(" idRappLegale: " + idRappLegale);
		logger.info(" idDelegato: " + idDelegato);
		logger.info(" idProgetto: " + idProgetto);
		logger.info(" tipoDocIndex: " + tipoDocIndex);
		logger.info(" entita: " + entita);
		logger.info(" codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(" versione: " + versione);
		logger.info(" shaHex: " + shaHex);
		
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

		try {
			
			documentoIndexVO.setIdDocumentoIndex(idDocumentoIndex);
			
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(" idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setNomeDocumento(nomeDocumento);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(documentoManagerPB.getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			documentoIndexVO.setRepository(tipoDocIndex);			
			documentoIndexVO.setUuidNodo("UUID");  
			
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			logger.info("statoGenerato="+statoGenerato);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(documentoManagerPB.getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(documentoManager.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			
			documentoIndexVO = genericDAO.insert(documentoIndexVO);

			logger.info("idDocIndex inserito sul db " 	+ documentoIndexVO.getIdDocumentoIndex());

			if(codStatoTipoDocIndex!=null){
				   logger.info("codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info("idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(" codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  finally {
			logger.info(" END");
		}
		return documentoIndexVO;
	}
	
	public BigDecimal nuovoIdTDocumentoIndex() {
		String prf = "[DocManagerCheckList::nuovoIdTDocumentoIndex] ";
		logger.info(prf + " BEGIN");
		BigDecimal id = null;
		try {
			String sqlSeq = "select SEQ_PBANDI_T_DOCUMENTO_INDEX.nextval from dual ";
			logger.info("\n"+sqlSeq.toString());
			id = (BigDecimal) getJdbcTemplate().queryForObject(sqlSeq.toString(), BigDecimal.class);
			logger.info("Nuovo id PBANDI_T_DOCUMENTO_INDEX = "+id.longValue());
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
			return null;
		}
		return id;
	}
}
