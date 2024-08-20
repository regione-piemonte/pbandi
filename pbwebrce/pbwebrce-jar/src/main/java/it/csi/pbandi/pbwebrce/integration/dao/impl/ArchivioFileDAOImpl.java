/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFornitoreVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRichiestaErogazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.affidamenti.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.integration.dao.ArchivioFileDAO;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.AllegatiAffidamentoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.ArchivioFileVORowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.affidamenti.FileEntitaRowMapper;
import it.csi.pbandi.pbwebrce.integration.vo.affidamenti.ArchivioFileVO;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.FileDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbwebrce.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbwebrce.util.BeanUtil;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.DateUtil;
import it.csi.pbandi.pbwebrce.util.FileSqlUtil;

@Component
public class ArchivioFileDAOImpl extends JdbcDaoSupport implements ArchivioFileDAO {

private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	
	@Autowired
	private GenericDAO genericDAO;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	private DocumentoManager documentoManager;
	
	@Autowired
	public ArchivioFileDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
	}

	public ArchivioFileDAOImpl() { }
	
	
	
	@Override
	@Transactional
	public List<DocumentoAllegato> findAllegati(Long idAppalto) throws Exception {
		String prf = "[ArchivioFileDAOImpl::findAllegati]";
		LOG.info(prf + " BEGIN");
		List<DocumentoAllegato> allegati = new ArrayList<DocumentoAllegato>();
		try {
			
			String queryAllegati = fileSqlUtil.getQuery("AllegatiAffidamento.sql");
			Object[] paramA = new Object[] {idAppalto};
			List<ArchivioFileVO> list = getJdbcTemplate().query(queryAllegati, paramA, new AllegatiAffidamentoRowMapper());
			
			List <FileDTO> filez=new ArrayList<FileDTO>();
			for (ArchivioFileVO archivioFile : list) {	
				FileDTO dto = createFileDTO(archivioFile);
				
				// Cerco PBANDI_T_FILE_ENTITA per il flag per invio verifica.
				String queryFileEntitya = fileSqlUtil.getQuery("FileEntita.sql");
				Object[] paramF = new Object[] {archivioFile.getIdFileEntita()};
				PbandiTFileEntitaVO vo = getJdbcTemplate().queryForObject(queryFileEntitya, paramF, new FileEntitaRowMapper());				
				// Aggancio il flag per invio verifica.
				dto.setDtEntita(vo.getDtEntita());
				dto.setFlagEntita(vo.getFlagEntita());
				
				filez.add(dto);				
			}
			
			FileDTO[] lista = filez.toArray(new FileDTO [0] );
			
			for (FileDTO fileDTO : lista) {
				DocumentoAllegato allegato = new DocumentoAllegato();
				allegato.setId(fileDTO.getIdDocumentoIndex());
				allegato.setIsDisassociabile(fileDTO.getDtEntita() == null);
				allegato.setDataInvio(DateUtil.getTime(fileDTO.getDtEntita()));	// PBANDI-2829.
				allegato.setNome(fileDTO.getNomeFile());
				allegato.setSizeFile(fileDTO.getSizeFile());
				allegati.add(allegato);
			}
			LOG.info(prf + " END");
			return allegati;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
		
	}
	
	
	private FileDTO createFileDTO(ArchivioFileVO file) {
		String prf = "[ArchivioFileDAOImpl::createFileDTO]";
		LOG.info(prf + " START");
		FileDTO fileDTO=new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if(file.getEntitiesAssociated()!=null && file.getEntitiesAssociated().longValue()>0){
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if(file.getIslocked()!=null && file.getIslocked().equalsIgnoreCase("S"))
			fileDTO.setIsLocked(true);
		else
			fileDTO.setIsLocked(false);
		fileDTO.setNumProtocollo(file.getNumProtocollo());
		
		if (file.getIdEntita() != null) {
			fileDTO.setIdEntita(file.getIdEntita().longValue());	
		}
		if (file.getIdTarget() != null) {
			fileDTO.setIdTarget(file.getIdTarget().longValue());	
		}
		fileDTO.setFlagEntita(file.getFlagEntita());	//Jira PBANDI-2815
		
		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());
		fileDTO.setDescBreveTipoDocIndex(file.getDescBreveTipoDocIndex());
		LOG.info(prf + " END");
		return fileDTO;
	}

	@Override
	@Transactional
	public EsitoOperazioni dissociateFile(Long idUtente, String idIride, Long idDocumentoIndex, Long idEntita, Long idTarget, Long idProgetto) throws Exception {
		String prf = "[ArchivioFileDAOImpl::dissociateFile]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idEntita","idTarget"};		
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idDocumentoIndex,idEntita,idTarget);
		
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		
		try {
			long idEntitaDichiarazione= documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class).longValue();
			long idEntitaRichErogazione= documentoManager.getIdEntita(PbandiTRichiestaErogazioneVO.class).longValue();
			long idEntitaComunicazFineProg= documentoManager.getIdEntita(PbandiTComunicazFineProgVO.class).longValue();
			long idEntitaContoEconomico= documentoManager.getIdEntita(PbandiTContoEconomicoVO.class).longValue();
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			
			fileVO=genericDAO.findSingleWhere(fileVO);
			
			PbandiTFileEntitaVO filterEntita=new PbandiTFileEntitaVO();
			filterEntita.setIdFile(fileVO.getIdFile());
			filterEntita.setIdEntita(BigDecimal.valueOf(idEntita));
			filterEntita.setIdTarget(BigDecimal.valueOf(idTarget));
			// i file associati a dich di spesa/richErog hanno bisogno di una gestione diversa dell'id progetto
			if(idEntitaDichiarazione!=idEntita.longValue() && 
					idEntitaRichErogazione!=idEntita.longValue() &&
					idEntitaComunicazFineProg !=idEntita.longValue() &&
					idEntitaContoEconomico !=idEntita.longValue())
				filterEntita.setIdProgetto(BigDecimal.valueOf(idProgetto));
			genericDAO.deleteWhere(Condition.filterBy(filterEntita));
			esito.setMsg("Allegato disassociato con successo.");
			//esito.setMsg("doc index with id "+idDocumentoIndex+" disassociated with idFile: "+fileVO.getIdFile()+" idEntita : "+idEntita+" and idTarget "+idTarget);
			esito.setEsito(true);
		} catch (Exception e) {
			LOG.error(prf+ "Error genericDAO.update() idDocumentoIndex: "+idDocumentoIndex, e);
			throw e;
		}
		return esito;
	}

	
	
	@Override
	@Transactional
	public EsitoOperazioni associateFile(UserInfoSec userInfo, Long idTarget, Long idEntita, long idDocumentoIndex,
			Long idProgetto) throws Exception {
		String prf = "[ArchivioFileDAOImpl::associateFile]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale","idDocumentoIndex","idTarget", "idEntita","idProgetto"};		
		ValidatorInput.verifyNullValue(nameParameter, userInfo.getIdUtente() ,userInfo.getIdIride(), idDocumentoIndex,idTarget,idEntita,idProgetto);
		
		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(false);
		LOG.info(prf + " associateFile idDocumentoIndex:"+idDocumentoIndex+" idEntita:"+idEntita+" idTarget: "+idTarget+" idProgetto:"+idProgetto);
		try {
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(BigDecimal.valueOf(idDocumentoIndex));
			pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		 
			pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
			pbandiTDocumentoIndexVO.setIdUtenteAgg(BigDecimal.valueOf(userInfo.getIdUtente()));
			BigDecimal idEntitaComunicazioneFineProgettoVO = documentoManager.getIdEntita(PbandiTComunicazFineProgVO.class);
			BigDecimal idEntitaContoEconomicoVO = documentoManager.getIdEntita(PbandiTContoEconomicoVO.class);
			BigDecimal idEntitaDichiarazioneVO = documentoManager.getIdEntita(PbandiTDichiarazioneSpesaVO.class);
			BigDecimal idEntitaFornitoreVO = documentoManager.getIdEntita(PbandiTFornitoreVO.class);
			BigDecimal idEntitaPagamentoVO = documentoManager.getIdEntita(PbandiTPagamentoVO.class);
			BigDecimal idEntitaRichiestaErogazioneVO = documentoManager.getIdEntita(PbandiTRichiestaErogazioneVO.class);
			/*
			 * l 'id progetto non deve essere inserito nella t_documento_index
			 */
			/*if (idEntitaPagamentoVO.compareTo(BigDecimal.valueOf(idEntita)) != 0
					&& idEntitaFornitoreVO.compareTo(BigDecimal
							.valueOf(idEntita)) != 0 ) {
				//pbandiTDocumentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			}*/
			
			LOG.info(prf +" trying to associate  idDocumentoIndex "+idDocumentoIndex+" to entity "+idEntita+ " with idTarget "+idTarget);
			genericDAO.update(pbandiTDocumentoIndexVO);
			
			PbandiTFileVO fileVO=new PbandiTFileVO();
			fileVO.setIdDocumentoIndex(BigDecimal.valueOf(idDocumentoIndex));
			fileVO=genericDAO.findSingleWhere(fileVO);
			
			PbandiTFileEntitaVO pbandiTFileEntitaVO=new PbandiTFileEntitaVO();
			pbandiTFileEntitaVO.setIdFile(fileVO.getIdFile());
			pbandiTFileEntitaVO.setIdEntita(BigDecimal.valueOf(idEntita));
			pbandiTFileEntitaVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
			if(idEntitaDichiarazioneVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaComunicazioneFineProgettoVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaRichiestaErogazioneVO.compareTo(BigDecimal.valueOf(idEntita)) == 0 ||
					idEntitaContoEconomicoVO.compareTo(BigDecimal.valueOf(idEntita)) == 0){
				// quando la dich/richErog/comunicazFineProg/contoeconomico non è ancora stata creata(preassociazione)
				//viene inserito come id_target l'id progetto. 
				//Alla creazione della dich/richErog/comunicazFineProg
				//l'id target verrà sovrascritto con l'id di dich/richErog/comunicazFineProg e verrà inserito l'idProgetto 
				idTarget= idProgetto;	
				pbandiTFileEntitaVO.setIdProgetto(null);
	    	}
			pbandiTFileEntitaVO.setIdTarget(BigDecimal.valueOf(idTarget));
			
			genericDAO.insert(pbandiTFileEntitaVO);
			LOG.info(prf +" file with idDocumentoIndex "+idDocumentoIndex+" associated to entity "+idEntita+ " with idTarget "+idTarget);
			esito.setMsg("Allegato associato con successo.");
			//esito.setMsg("file with idDocumentoIndex "+idDocumentoIndex+" associated to entity "+idEntita+ " with idTarget "+idTarget);
			esito.setEsito(true);
		} catch (Exception e) {
			LOG.error(prf + " Error genericDAO.update() idDocumentoIndex: "+idDocumentoIndex, e);
			throw e;
		}
		
		return esito;
	}

	@Override
	@Transactional
	public DocumentoAllegato[] getChecklistAssociatedAffidamento(UserInfoSec userInfo, Long idAppalto) {
		String prf = "[ArchivioFileDAOImpl::getChecklistAssociatedAffidamento]";
		LOG.info(prf + " BEGIN");
		List <FileDTO> filez = new ArrayList<FileDTO>();
	
		List<ArchivioFileVO> result = null;
		MapSqlParameterSource params = new MapSqlParameterSource();

		StringBuilder sqlSelect = new StringBuilder("");
		sqlSelect
				.append("SELECT ptdi.id_Entita AS idEntita, ptdi.id_target AS idTarget, ")
				.append("	ptdi.id_progetto AS idProgetto, ptdi.num_protocollo AS numProtocollo, ")
				.append("	ptdi.NOME_FILE AS nomeFile, ptdi.ID_DOCUMENTO_INDEX AS idDocumentoIndex, ")
				.append("	pctdi.DESC_BREVE_TIPO_DOC_INDEX AS descBreveTipoDocIndex, pdstdi.DESC_STATO_TIPO_DOC_INDEX AS descStatoTipoDocIndex ")
				.append("FROM PBANDI_T_DOCUMENTO_INDEX ptdi, PBANDI_R_DOCU_INDEX_TIPO_STATO prdits, ")
				.append("	PBANDI_D_STATO_TIPO_DOC_INDEX pdstdi, PBANDI_C_TIPO_DOCUMENTO_INDEX pctdi ")
				.append("WHERE ptdi.ID_TARGET = :idAppalto ")
				.append("	AND ptdi.ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_APPALTO') ")
				.append("   AND prdits.ID_DOCUMENTO_INDEX = ptdi.ID_DOCUMENTO_INDEX ")
				.append("   AND pdstdi.ID_STATO_TIPO_DOC_INDEX = prdits.ID_STATO_TIPO_DOC_INDEX ")
				.append("   AND ptdi.ID_TIPO_DOCUMENTO_INDEX = pctdi.ID_TIPO_DOCUMENTO_INDEX ");
		sqlSelect.append("ORDER BY ptdi.ID_DOCUMENTO_INDEX");

		LOG.info(prf + "sqlSelect="+sqlSelect.toString());
		LOG.info(prf + "idAppalto="+idAppalto);
		params.addValue("idAppalto", idAppalto, Types.BIGINT);

		result = namedJdbcTemplate.query(sqlSelect.toString(), params, new ArchivioFileVORowMapper());
		
		for (ArchivioFileVO archivioFile : result) {
			FileDTO dto = createFileDTO(archivioFile);
			filez.add(dto);
		}	
		FileDTO[] files  = filez.toArray(new FileDTO [0] );
		ArrayList<DocumentoAllegato> documentiAllegati = new ArrayList<DocumentoAllegato>();
		
		if (!ObjectUtil.isEmpty(files)) {
			for (FileDTO file : files) {
				DocumentoAllegato allegato = new DocumentoAllegato();
				allegato.setId(file.getIdDocumentoIndex());
				allegato.setNome(file.getNomeFile());
				//allegato.setIsDisassociable(false);
				allegato.setSizeFile(file.getSizeFile());
				allegato.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());		
				allegato.setDescBreveTipoDocIndex(file.getDescBreveTipoDocIndex());
				documentiAllegati.add(allegato);
			}
		}
		
		LOG.info(prf + " END");
		return documentiAllegati.toArray(new DocumentoAllegato[0]);
	}
	
}
