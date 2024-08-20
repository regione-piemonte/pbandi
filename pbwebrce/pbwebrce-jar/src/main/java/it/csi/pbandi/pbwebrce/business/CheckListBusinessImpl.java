/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.TooMuchRecordFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTFileVO;
import it.csi.pbandi.pbwebrce.business.intf.CheckListSrv;
import it.csi.pbandi.pbwebrce.dto.checklist.EsitoEliminaCheckListDTO;
import it.csi.pbandi.pbwebrce.exception.CheckListException;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.FileDTO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTAppaltoChecklistVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTChecklistHtmlVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexLockVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.pbandisrv.util.Constants;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class CheckListBusinessImpl implements CheckListSrv {
	
	@Autowired
	protected DecodificheManager decodificheManager;
	
	@Autowired
	private DocumentoManager documentoManager;
	

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Override
	public EsitoEliminaCheckListDTO eliminaBozzaCheckListDocumentaleAffidamento(Long idUtente, String identitaDigitale,
			Long idAffidamento) throws CSIException, SystemException, UnrecoverableException, CheckListException {
		 
			String[] nameParameter = { "idUtente","identitaDigitale","idAffidamento" };
			ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idAffidamento);		

			EsitoEliminaCheckListDTO esitoEliminaCheckList = new EsitoEliminaCheckListDTO();
			esitoEliminaCheckList
					.setCodiceMessaggio(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

			try {
				
				BigDecimal idEntitaPbandiTAppalto = new BigDecimal(Constants.ID_ENTITA_PBANDI_T_APPALTO);
				BigDecimal idTipoDocIndexChecklistDocumentale = 
					decodificheManager.decodeDescBreve(
						PbandiCTipoDocumentoIndexVO.class,
						GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_VALIDAZIONE);
				
				// Dall'id appalto\affidamento recupera il docuemnto index relativo alla checklist documentale.			
				PbandiTDocumentoIndexVO docVO = new PbandiTDocumentoIndexVO();
				docVO.setIdTarget(new BigDecimal(idAffidamento));
				docVO.setIdEntita(idEntitaPbandiTAppalto);
				docVO.setIdTipoDocumentoIndex(idTipoDocIndexChecklistDocumentale);
				
				docVO = genericDAO.findSingleOrNoneWhere(Condition.filterBy(docVO));
				
				if (docVO == null) {
					LOG.info("eliminaBozzaCheckListDocumentaleAffidamento(): nessuna checklist documentale.");
				} else {
					try {
						
						PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						docuIndexTipoStatoVO.setIdDocumentoIndex(docVO.getIdDocumentoIndex());					
		
						PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO1 = new PbandiRDocuIndexTipoStatoVO();
						docuIndexTipoStatoVO1
								.setIdStatoTipoDocIndex(decodificheManager
										.decodeDescBreve(
												PbandiDStatoTipoDocIndexVO.class,
												GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_BOZZA));
						
						//PbandiRDocuIndexTipoStatoVO docuIndexTipoStatoVO2 = new PbandiRDocuIndexTipoStatoVO();
						//docuIndexTipoStatoVO2
						//		.setIdStatoTipoDocIndex(decodificheManager
						//				.decodeDescBreve(
						//						PbandiDStatoTipoDocIndexVO.class,
						//						GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX_DEFINITIVO));
		
						List<PbandiRDocuIndexTipoStatoVO> docuIndexTipoStatoStatiCancellabili = new ArrayList<PbandiRDocuIndexTipoStatoVO>();
						docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO1);
						//docuIndexTipoStatoStatiCancellabili.add(docuIndexTipoStatoVO2);
		
						genericDAO
								.findSingleWhere(Condition
										.filterBy(docuIndexTipoStatoVO)
										.and(Condition
												.filterBy(docuIndexTipoStatoStatiCancellabili)));
						
						// Trovo la checklist in PBANDI_T_CHECKLIST.
						PbandiTChecklistVO pbandiTChecklistVO = new PbandiTChecklistVO();
						pbandiTChecklistVO.setIdDocumentoIndex(docVO.getIdDocumentoIndex());
						pbandiTChecklistVO = genericDAO.findSingleWhere(pbandiTChecklistVO);

						// Elimino il record in PBANDI_T_CHECKLIST_HTML.
						PbandiTChecklistHtmlVO pbandiTChecklistHtmlVO = new PbandiTChecklistHtmlVO();
						pbandiTChecklistHtmlVO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
						genericDAO.delete(pbandiTChecklistHtmlVO);
						
						// Elimino il record in PBANDI_T_APPALTO_CHECKLIST.
						PbandiTAppaltoChecklistVO pbandiTAppaltoChecklistVO = new PbandiTAppaltoChecklistVO();
						pbandiTAppaltoChecklistVO.setIdAppalto(new BigDecimal(idAffidamento));
						pbandiTAppaltoChecklistVO.setIdChecklist(pbandiTChecklistVO.getIdChecklist());
						pbandiTAppaltoChecklistVO = genericDAO.findSingleWhere(pbandiTAppaltoChecklistVO);
						genericDAO.delete(pbandiTAppaltoChecklistVO);
						
						// Elimino il record in PBANDI_T_CHECKLIST.
						genericDAO.delete(pbandiTChecklistVO);
						
						// Cancello eventuale lock.
						PbandiTDocumentoIndexLockVO lockVO = new PbandiTDocumentoIndexLockVO();
						lockVO.setIdEntita(idEntitaPbandiTAppalto);
						lockVO.setIdTarget(new BigDecimal(idAffidamento));
						lockVO.setIdProgetto(docVO.getIdProgetto());
						lockVO.setIdTipoDocumentoIndex(idTipoDocIndexChecklistDocumentale);
						genericDAO.delete(lockVO);
						
						// nel caso elimino il documento (PBANDI_T_DOCUMENTO_INDEX e PBANDI_R_DOCU_INDEX_TIPO_STATO)
						documentoManager.eliminaDocumento(
								beanUtil.transform(idUtente, BigDecimal.class),
								docVO.getIdDocumentoIndex());
		
					} catch (RecordNotFoundException e) {
						LOG.warn("Trovati nessuno o più record in PbandiRDocuIndexTipoStato.");
						throw new CheckListException(
								MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE_STATO);
					} catch (Exception e) {
						String message = "Impossibile effettuare la cancellazione: "
								+ e.getMessage();
						LOG.error(message, e);
						throw new CheckListException(
								MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE, e);
					}
				}
			} catch (TooMuchRecordFoundException e) {
				LOG.error("eliminaBozzaCheckListDocumentaleAffidamento(): trovato più di 1 record in PbandiTDocumentoIndex.");
				throw new CheckListException(
						MessaggiConstants.KEY_CHECKLIST_NON_POSSIBILE_ELIMINARE);
			}  

			return esitoEliminaCheckList;
	}
	
	@Override
	public FileDTO[] getFilesAssociatedVerbaleChecklist(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.Long idDocIndex
	)	throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
	it.csi.csi.wrapper.UnrecoverableException
	{
		String prf = "[CheckListBusinessImpl:getFilesAssociatedVerbaleChecklist]";
		LOG.info(prf + " BEGIN");
		
		String[] nameParameter = { "idUtente", "identitaDigitale","idChecklist" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,identitaDigitale);
		
		
		//Dal codTipoDocIndex recuperiamo l'oggetto interessato
//		it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO tipoDocIndex = new it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO();
//		tipoDocIndex.setDescBreveTipoDocIndex(codTipoDocIndex);
//		tipoDocIndex = genericDAO.findSingleOrNoneWhere(tipoDocIndex);
//		if (tipoDocIndex == null || tipoDocIndex.getIdTipoDocumentoIndex() == null) {
//			return null;
//		}
//		BigDecimal idTipoDocumentoIndex = tipoDocIndex.getIdTipoDocumentoIndex();
		
		
		//Ricerca nella PbandiTChecklist
		PbandiTChecklistVO filter = new PbandiTChecklistVO();
		filter.setIdDocumentoIndex(new BigDecimal(idDocIndex));
		PbandiTChecklistVO checklistList = (PbandiTChecklistVO) genericDAO.findSingleOrNoneWhere(filter);
		BigDecimal idChecklist = checklistList.getIdChecklist();
		
		
		
		//set dati per recuperare allegati
		PbandiTDocumentoIndexVO docIndexFilter =new PbandiTDocumentoIndexVO();
		docIndexFilter.setIdTarget(idChecklist);
		//docIndexFilter.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		docIndexFilter.setIdEntita(new BigDecimal(242));
		ArrayList<PbandiTDocumentoIndexVO> listaVO = 
				(ArrayList<PbandiTDocumentoIndexVO>) genericDAO.findListWhere(docIndexFilter);	
			
		if (listaVO == null || listaVO.size() == 0) {
			LOG.info("listaVO null");
			return null;
		}
		
		List<FileDTO> listaDTO = new ArrayList<>();
		for (int i = 0; i < listaVO.size(); i++) {
			PbandiTDocumentoIndexVO doc = listaVO.get(i);
			if (doc.getIdTipoDocumentoIndex().equals(new BigDecimal(80)) || doc.getIdTipoDocumentoIndex().equals(new BigDecimal(81))) { // VCVA o VCDA
				PbandiTFileVO pbandiTFile = new PbandiTFileVO(); 
				pbandiTFile.setIdDocumentoIndex(doc.getIdDocumentoIndex());
				PbandiTFileVO file = genericDAO.findSingleOrNoneWhere(pbandiTFile);
				
				Long sizeFile = null;
				if (file != null && file.getSizeFile() != null)
					sizeFile = file.getSizeFile().longValue();
				
				FileDTO fileDTO = new FileDTO();
				fileDTO.setIdDocumentoIndex(doc.getIdDocumentoIndex().longValue());
				fileDTO.setNomeFile(doc.getNomeFile());
				fileDTO.setSizeFile(sizeFile);
				
				listaDTO.add(fileDTO); 
			}
		}
		if (listaDTO.size() == 0) {
			LOG.info("listaDTO null");
			return null;
		}
		FileDTO[] array = new FileDTO[listaDTO.size()];
		listaDTO.toArray(array);
		LOG.info(prf + " END");
		return array;
	}
}
