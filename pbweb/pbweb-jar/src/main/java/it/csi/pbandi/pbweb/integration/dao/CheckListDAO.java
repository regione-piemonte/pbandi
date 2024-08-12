package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.List;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.dto.CheckListItem;
import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.InizializzaGestioneChecklistDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.checklist.CheckListException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;

public interface CheckListDAO {

	EsitoOperazioneDTO saveCheckListInLocoHtml(Long idUtente, String identitaDigitale, Long checklistSelezionata, Long idProgetto,
			String statoChecklist, String checklistHtml);

	Object getModuloCheckList(Long idUtente, String identitaDigitale, Long idProgetto, String soggettoControllore) throws InvalidParameterException;

	List<CodiceDescrizioneDTO> caricaDichiarazioneDiSpesa(String idProgetto, Boolean isFP, Long idUtente, String idIride) throws CheckListException, SystemException, UnrecoverableException, CSIException;

	List<CodiceDescrizioneDTO> caricaStatoSoggetto(String idUtente, String idIride) throws GestioneDatiDiDominioException, NumberFormatException, UnrecoverableException;

	List<CheckListItem> cercaChecklist(Long idUtente, String idIride, String dichiarazioneSpesa, String dataControllo,
			String soggettoControllo, String[] stati, String tipologia, String rilevazione, String versione, String idProgetto) throws CheckListException, NumberFormatException, SystemException, UnrecoverableException, CSIException;

	InizializzaGestioneChecklistDTO inizializzaGestioneChecklist(Long idUtente, Long idSoggetto, String codiceRuolo, Long idProgetto) throws SystemException, UnrecoverableException, Exception;
	
	EsitoDTO eliminaChecklist(Long idDocumentoIndex, Long idUtente, String idIride) throws SystemException, UnrecoverableException, Exception;
	
	EsitoLockCheckListDTO salvaLockCheckListInLoco(Long idProgetto, Long idDocumentoIndex,  Long idUtente, String idIride) throws InvalidParameterException, Exception;
	
	EsitoLockCheckListDTO salvaLockCheckListValidazione(Long idProgetto, Long idDichiarazione,  Long idUtente, String idIride) throws InvalidParameterException, Exception;

	Object getCheckListInLocoHtml(Long idUtente, String idIride, Long idDocumentoIndex) throws InvalidParameterException, CSIException;

	Object getCheckListValidazioneHtml(Long idUtente, String idIride, Long idDocumentoIndex, Long idDichiarazione, Long idProgetto) throws InvalidParameterException, CSIException;

	EsitoOperazioneDTO saveCheckListDocumentaleHtml(Long idUtente, String idIride, Long idCh, Long idProgetto,
			String statoChecklist, String checklistHtml, it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO[] verbali) throws InvalidParameterException, Exception;

	EsitoOperazioneDTO saveCheckListInLocoHtmlDef(Long idUtente, String idIride, Long idCh, Long idProgetto, String statoChecklist,
			String checklistHtml, it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO[] verbali);
}
