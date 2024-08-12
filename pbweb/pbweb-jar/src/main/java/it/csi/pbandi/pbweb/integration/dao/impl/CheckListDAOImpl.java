package it.csi.pbandi.pbweb.integration.dao.impl;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbweb.business.manager.InizializzazioneManager;
import it.csi.pbandi.pbweb.dto.CheckListItem;
import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.InizializzaGestioneChecklistDTO;
import it.csi.pbandi.pbweb.integration.dao.CheckListDAO;
import it.csi.pbandi.pbweb.pbandisrv.business.checklist.CheckListBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoEliminaCheckListHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DichiarazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.checklist.CheckListException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatididominio.GestioneDatiDiDominioException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.CheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.DichiarazioneDiSpesaChecklistDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FiltroRicercaCheckListDTO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.StringUtil;
import it.csi.pbandi.pbweb.util.TraduttoreMessaggiPbandisrv;

import javax.sql.DataSource;

@Service
public class CheckListDAOImpl extends JdbcDaoSupport implements CheckListDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public CheckListDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	protected ChecklistHtmlBusinessImpl checklistHtmlBusinessImpl;
	
	@Autowired
	protected CheckListBusinessImpl checkListBusinessImpl;
	
	@Autowired
	protected GestioneDatiDiDominioBusinessImpl gestioneDatiDiDominioBusinessImpl;
	
	@Autowired
	protected GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;
	
	@Autowired
	protected ProfilazioneDAO profilazioneDao;
	
	@Autowired
	InizializzazioneManager inizializzazioneManager;
	
	@Override
	public EsitoOperazioneDTO saveCheckListInLocoHtml(Long idUtente, String identitaDigitale, Long checklistSelezionata, Long idProgetto, String statoChecklist, String checklistHtml) {
		String prf = "[CheckListDAOImpl::saveCheckListInLocoHtml]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " checklistSelezionata="+checklistSelezionata);
		LOG.info(prf + " idProgetto="+idProgetto);
		LOG.info(prf + " statoChecklist="+statoChecklist);
	
		EsitoOperazioneDTO ret = new EsitoOperazioneDTO();
		try {
			 
			ChecklistHtmlDTO checkListHtmlDTO = new ChecklistHtmlDTO();
			checkListHtmlDTO.setContentHtml(checklistHtml);
			checkListHtmlDTO.setCodStatoTipoDocIndex(statoChecklist);
			checkListHtmlDTO.setIdProgetto(idProgetto);

			if(checklistSelezionata!=null){
				LOG.info(prf + "chk diversa da null,checklistSelezionata "+checklistSelezionata);
				
				//PK ricavo IdDocumentoIndex dalla PBANDI_T_CHECKLIST
				
				PbandiTChecklistVO checklist = checklistHtmlBusinessImpl.getChecklist(checklistSelezionata);
				if(checklist!=null)
					checkListHtmlDTO.setIdDocumentoIndex(checklist.getIdDocumentoIndex().longValue());
				
//				checkListHtmlDTO.setIdDocumentoIndex(checklistSelezionata); // NOOOOOOOOOOOOOOO
				
			}
			
			LOG.info(prf + " checklistHtmlBusinessImpl="+checklistHtmlBusinessImpl);
			
			EsitoSalvaModuloCheckListDTO esito = checklistHtmlBusinessImpl.saveCheckListInLocoHtml(idUtente, identitaDigitale, idProgetto, statoChecklist, checkListHtmlDTO);
			LOG.info(prf + "after checklistHtmlSrv.saveCheckListInLocoHtml: "+esito.getEsito());

			ret.setEsito(esito.getEsito());
			ret.setCodiceMessaggio(esito.getMessage());
				
		} catch (CSIException e) {
			LOG.error(prf + " CSIException e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		return ret;
	}

	@Override
	public Object getModuloCheckList(Long idUtente, String identitaDigitale, Long idProgetto,
			String soggettoControllore) {
		String prf = "[CheckListDAOImpl::getModuloCheckList]";
		LOG.info(prf + " BEGIN");
		
		if (idProgetto == null) {
			LOG.error(prf + " InvalidParameterException, idProgetto null");
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			LOG.error(prf + " InvalidParameterException, idUtente null");
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " soggettoControllore="+soggettoControllore);
		LOG.info(prf + " idProgetto="+idProgetto);
		
		ChecklistHtmlDTO checklistHtmlDTO = null;
		try {
			checklistHtmlDTO = checklistHtmlBusinessImpl.getModuloCheckListInLocoHtml(idUtente, identitaDigitale, idProgetto, soggettoControllore);
		
			if(checklistHtmlDTO==null || (checklistHtmlDTO!=null && StringUtils.isBlank(checklistHtmlDTO.getContentHtml()))){
				LOG.warn(prf + "Attenzione! Modulo html non configurato per progetto: " + idProgetto);
			}else {
				LOG.info(prf + "prime 100 righe xx chklist validazione : \n"
						+ checklistHtmlDTO.getContentHtml().substring(0, 100));
			}
			
		} catch (CSIException e) {
			LOG.error(prf + " errore " + e.getMessage());
//			e.printStackTrace();
			
		}finally {
			LOG.info(prf + " END");
		}
		return checklistHtmlDTO;
	}

	@Override
	public List<it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO> caricaDichiarazioneDiSpesa(String idProgetto, Boolean isFP, Long idUtente, String identitaDigitale) throws CheckListException, SystemException, UnrecoverableException, CSIException {
		String prf = "[CheckListDAOImpl::caricaDichiarazioneDiSpesa]";
		LOG.info(prf + " BEGIN");
		ArrayList<it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO> result = null;
		
		Long idPrj = Long.parseLong(idProgetto);
		LOG.info(prf + " idPrj="+idPrj);

		List<DichiarazioneDiSpesaChecklistDTO> dichiarazioni;
		if(isFP){
			dichiarazioni = getJdbcTemplate().query(
					"SELECT ptds.ID_DICHIARAZIONE_SPESA,\n" +
							"ptds.DT_DICHIARAZIONE\n" +
							"FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n" +
							"WHERE ptds.ID_PROGETTO = ?\n" +
							"AND ptds.ID_ATTRIB_VALID_SPESA <> 7",
					ps -> ps.setString(1, idProgetto),
					(rs, rownum) -> {
						DichiarazioneDiSpesaChecklistDTO dto = new DichiarazioneDiSpesaChecklistDTO();

						dto.setIdDichiarazione(rs.getLong("ID_DICHIARAZIONE_SPESA"));
						dto.setDescDichiarazione(dto.getIdDichiarazione() + "-" + rs.getString("DT_DICHIARAZIONE"));

						return dto;
					}
			);
		}else{
			dichiarazioni = Arrays.asList(checkListBusinessImpl.caricaComboElencoDichiarazioniDiSpesa(idUtente, identitaDigitale, idPrj));
		}

		if(dichiarazioni!=null) {
			result = new ArrayList<>();
			LOG.info(prf + " dichiarazioni.length="+dichiarazioni.size());
			for (DichiarazioneDiSpesaChecklistDTO item : dichiarazioni) {
				it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
				cd.setCodice(item.getIdDichiarazione()+"");
				cd.setDescrizione(item.getDescDichiarazione());
				result.add(cd);
			}
		}

		LOG.info(prf + " END");
		return result;
	}

	@Override
	public List<it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO> caricaStatoSoggetto(String idUtente, String identitaDigitale) throws GestioneDatiDiDominioException, NumberFormatException, UnrecoverableException {
		String prf = "[CheckListDAOImpl::caricaStatoSoggetto]";
		LOG.info(prf + " BEGIN");
		
		ArrayList<it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO> result = null;
		
//		Decodifica[] decodifiche = gestioneDatiDiDominioBusinessImpl.findDecodifiche(Long.parseLong(idUtente), identitaDigitale, GestioneDatiDiDominioSrv.STATO_TIPO_DOC_INDEX);
		Decodifica[] decodifiche = gestioneDatiDiDominioBusinessImpl.findDecodifiche(Long.parseLong(idUtente), identitaDigitale, 
				"it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO");
		
		if(decodifiche!=null) {
			result = new ArrayList<it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO>();
			LOG.info(prf + " decodifiche.length="+decodifiche.length);
		}
		for (Decodifica item : decodifiche) {
			it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
			//cd.setCodice(item.getId()+"");
			cd.setCodice(item.getDescrizioneBreve());
			cd.setDescrizione(item.getDescrizione());
			result.add(cd);
		}
		LOG.info(prf + " END");
		return result;
	}

	@Override
	public List<CheckListItem> cercaChecklist(Long idUtente, String idIride, String dichiarazioneSpesa,
			String dataControllo, String soggettoControllo, String[] stati, String tipologia, String rilevazione,
			String versione, String idProgetto) throws CheckListException, NumberFormatException, SystemException, UnrecoverableException, CSIException {
		String prf = "[CheckListDAOImpl::cercaChecklist]";
		LOG.info(prf + " BEGIN");
		
		List<CheckListItem> lista = null;
		
		// CPBEGestioneCheckList.java > findCheckListGen
		
		Long idDichSpesa = null;
		if(StringUtils.isNumeric(dichiarazioneSpesa)) {
			idDichSpesa = Long.parseLong(dichiarazioneSpesa);
		}
		
		Long idProj = null;
		if(StringUtils.isNumeric(idProgetto)) {
			idProj = Long.parseLong(idProgetto);
		}else {
			LOG.error(prf + "idProgetto non valorizzato");
			throw new CheckListException("idProgetto non valorizzato");
		}
		
		it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FiltroRicercaCheckListDTO filtro = new it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FiltroRicercaCheckListDTO();
		filtro.setCodiceTipoCheckList(tipologia);
		filtro.setDataControllo(dataControllo);
		filtro.setIdDichiarazione(idDichSpesa);
		filtro.setIdProgetto(idProj);
		filtro.setSoggettoControllore(soggettoControllo);
		filtro.setVersione(versione);
		filtro.setFlagRilevazioneIrregolarita(rilevazione);

		cleanFilter(filtro);
		LOG.info(prf + " filtro ="+filtro);
		
		//	filtro.setCodiciStatoCheckList(stato); //TODO PK cosa metto? perche dovrebbe arrovarne piu di uno??
		filtro.setCodiciStatoCheckList(stati);
		
		CheckListDTO[] checkLists = checkListBusinessImpl.findCheckList(idUtente, idIride, idProj, filtro);
		LOG.info(prf + " checkLists ="+checkLists);
		
		if(checkLists!=null) {
			LOG.info(prf + " checkLists,length = "+checkLists.length);
			lista = converti(checkLists); //new ArrayList<CheckListItem>();

			//M:E: PEZZA Piu Green
			DatiPiuGreenDTO datiPG = gestioneDatiGeneraliBusinessImpl.findDatiPiuGreen(idUtente, idIride, idProj);
			LOG.info(prf + " datiPG = "+datiPG);
			
			if (datiPG != null && datiPG.getIdProgettoContributo() != null) {
				LOG.info(prf + " datiPG.getIdProgettoContributo = "+datiPG.getIdProgettoContributo());

				if(StringUtil.isEmpty(dichiarazioneSpesa)) {
					// L'utente a video non ha selezionato alcuna DS: cerco tutte le checklist del progetto contributo.
					
					CheckListDTO[]  checkListsPG = checkListBusinessImpl.findCheckList(idUtente, idIride, datiPG.getIdProgettoContributo(), filtro);
					LOG.info(prf + " checkListsPG = "+checkListsPG);
					
					lista.addAll(converti(checkListsPG));
					
				}else {
					// L'utente a video ha selezionato una DS: verifico se e' di tipo Integrativa,
					// cioe se PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA_COLL e' valorizzato.
				
					Long idDs = new Long(dichiarazioneSpesa);
					LOG.info(prf + " idDs = "+idDs);
					
					DichiarazioneSpesaDTO dto = gestioneDatiGeneraliBusinessImpl.findDsIntegrativaContributo(idUtente, idIride, idDs);
					LOG.info(prf + " dto = "+dto);
					
					if (dto != null	&& dto.getIdDichiarazioneSpesa() != null) {
						// La Ds e' di tipo Integrativa: cerco le checklist in base a progetto e ds entrambe contributo.
					
						filtro.setIdDichiarazione(dto.getIdDichiarazioneSpesa());
						CheckListDTO[]  checkListsPG2 = checkListBusinessImpl.findCheckList(idUtente, idIride, datiPG.getIdProgettoContributo(), filtro);
						LOG.info(prf + " checkListsPG2 = "+checkListsPG2);
						lista.addAll(converti(checkListsPG2));
						filtro.setIdDichiarazione(idDs);// Rimetto nel filtro l'id DS originale.

					} else {
						// Non e' una DS Integrativa (ma ad esempio una Finale): 
						// cerco le checklist in base a progetto contributo e ds finanziamento.
						
						CheckListDTO[]  checkListsPG3 = checkListBusinessImpl.findCheckList(idUtente, idIride, datiPG.getIdProgettoContributo(), filtro);
						LOG.info(prf + " checkListsPG3 = "+checkListsPG3);
						lista.addAll(converti(checkListsPG3));
					}
				}
			}
		}
		LOG.info(prf + " END");
		return lista;
	}

	// CPBEGestioneCheckList.java > cleanFilter
	private void cleanFilter(FiltroRicercaCheckListDTO filtro) {
		String prf = "[CheckListDAOImpl::cleanFilter]";
		LOG.info(prf + " BEGIN");
		
		if(!StringUtils.equals(filtro.getCodiceTipoCheckList(), gestioneDatiDiDominioBusinessImpl.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO)
				&&!StringUtils.equals(filtro.getCodiceTipoCheckList(), gestioneDatiDiDominioBusinessImpl.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE)) {
			filtro.setCodiceTipoCheckList(null);
		}
		if(StringUtils.isEmpty(filtro.getSoggettoControllore())){
			filtro.setSoggettoControllore(null);
		}
//		if(filtro.getCodiciStatoCheckList()==null){
//			String[] uuu = null;
//			filtro.setCodiciStatoCheckList(uuu);
//		}	
		if(StringUtils.isEmpty(filtro.getVersione())){
			filtro.setVersione(null);
		}
		if(!StringUtils.equals(filtro.getFlagRilevazioneIrregolarita(), "S")) {
			filtro.setFlagRilevazioneIrregolarita(null);
		}
		LOG.info(prf + " END");
	}

	private List<CheckListItem> converti(CheckListDTO[] lista) {
		List<CheckListItem> l = null;
		if(lista!=null) {
			l = new ArrayList<CheckListItem>();
			for (CheckListDTO item : lista) {
				CheckListItem cl = new CheckListItem();
				
				cl.setCodiceStato(item.getCodiceStato());
				cl.setCodiceTipo(item.getCodiceTipo());
				cl.setDataControllo(DateUtil.getDate(item.getDataControllo()));
				cl.setDescStato(item.getDescStato());
	//			cl.setFlagRilevazioneIrregolarita(item.getF);				
				cl.setDescTipo(item.getDescTipo());
				cl.setIdDichiarazione(item.getIdDichiarazione());
				cl.setIdProgetto(item.getIdProgetto());
				cl.setNome(item.getNome());
				cl.setSoggettoControllore(item.getSoggettoControllore());
				cl.setVersione(item.getVersione());
				
				// Nel vecchio IdDocumentoIndex veniva messo in IdChecklist, ho creato un campo ad hoc. 
				//cl.setIdChecklist(item.getIdDocumentoIndex());
				cl.setIdDocumentoIndex(item.getIdDocumentoIndex());
	
				l.add(cl);
			}
		}
		return l;
	}
	
	@Override
	public InizializzaGestioneChecklistDTO inizializzaGestioneChecklist(Long idUtente, Long idSoggetto, String codiceRuolo, Long idProgetto) throws SystemException, UnrecoverableException, Exception {
		String prf = "[CheckListDAOImpl::inizializzaGestioneChecklist] ";
		LOG.info(prf + " BEGIN");
		
		InizializzaGestioneChecklistDTO result = new InizializzaGestioneChecklistDTO();
		
		DatiProgettoInizializzazioneDTO datiProgetto = inizializzazioneManager.completaDatiProgetto(idProgetto);
		if (datiProgetto != null) {
			result.setCodiceVisualizzatoProgetto(datiProgetto.getCodiceVisualizzato());
		}
		
		// Indica se mostrare o no l'icona di modifica di una checklist.
		result.setModificaChecklistAmmessa(profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN047"));
		
		// Indica se mostrare o no l'icona di eliminazione di una checklist.
		result.setEliminazioneChecklistAmmessa(profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN048"));

		LOG.info(prf + " END");
		return result;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoDTO eliminaChecklist(Long idDocumentoIndex, Long idUtente, String idIride) throws SystemException, UnrecoverableException, Exception {
		String prf = "[CheckListDAOImpl::eliminaChecklist] ";
		LOG.info(prf + " BEGIN");
		
		EsitoDTO esito = new EsitoDTO();				
		try {
			
			EsitoEliminaCheckListHtmlDTO esitoSrv = checklistHtmlBusinessImpl.eliminaCheckList(idUtente, idIride, idDocumentoIndex);
			
			esito.setEsito(true);
			esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoSrv.getCodiceMessaggio()));
			
		} catch (CheckListException e) {
			esito.setEsito(false);
			esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(e.getMessage()));
		} catch (Exception e) {
			LOG.error(prf+"ERRORE: "+e);
			throw e;
		}
		
		LOG.info(prf + " END");
		return esito;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoLockCheckListDTO salvaLockCheckListInLoco(Long idProgetto, Long idDocumentoIndex,  Long idUtente, String idIride) throws SystemException, UnrecoverableException, Exception {
		String prf = "[CheckListDAOImpl::salvaLockCheckListInLoco] ";
		LOG.info(prf + " BEGIN");
		
		EsitoLockCheckListDTO esito = null;				
		try {
			
			esito = checkListBusinessImpl.salvaLockCheckListInLoco(idUtente, idIride, idProgetto, idDocumentoIndex);
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE: "+e);
			throw e;
		}
		
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public EsitoLockCheckListDTO salvaLockCheckListValidazione(Long idProgetto, Long idDichiarazione,  Long idUtente, String idIride) throws SystemException, UnrecoverableException, Exception {
		String prf = "[CheckListDAOImpl::salvaLockCheckListValidazione] ";
		LOG.info(prf + " BEGIN");
		
		EsitoLockCheckListDTO esito = null;				
		try {
			
			esito = checkListBusinessImpl.salvaLockCheckListValidazione(idUtente, idIride, idProgetto, idDichiarazione);
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE: "+e);
			throw e;
		}
		
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	public Object getCheckListInLocoHtml(Long idUtente, String identitaDigitale, Long idDocumentoIndex) throws CSIException {
		String prf = "[CheckListDAOImpl::getCheckListInLocoHtml]";
		LOG.info(prf + " BEGIN");
		
		if (idDocumentoIndex == null) {
			LOG.error(prf + " InvalidParameterException, idDocumentoIndex null");
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idUtente == null) {
			LOG.error(prf + " InvalidParameterException, idUtente null");
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " idDocumentoIndex="+idDocumentoIndex);
		
		ChecklistHtmlDTO checklistHtmlDTO = null;
		try {
			checklistHtmlDTO = checklistHtmlBusinessImpl.getCheckListInLocoHtml(idUtente, identitaDigitale, idDocumentoIndex);
		
			if(checklistHtmlDTO==null || (checklistHtmlDTO!=null && StringUtils.isBlank(checklistHtmlDTO.getContentHtml()))){
				LOG.warn(prf + "Attenzione! Modulo html non trovato per idDocumentoIndex: " + idDocumentoIndex);
			}else {
				LOG.info(prf + "prime 100 righe xx chklist validazione : \n"
						+ checklistHtmlDTO.getContentHtml().substring(0, 100));
			}
			
		} catch (CSIException e) {
			LOG.error(prf + " errore " + e.getMessage());
			throw e;
			
		}finally {
			LOG.info(prf + " END");
		}
		return checklistHtmlDTO;
	}

	@Override
	public Object getCheckListValidazioneHtml(Long idUtente, String identitaDigitale, Long idDocumentoIndex, Long idDichiarazione,
			Long idProgetto) throws InvalidParameterException, CSIException {
		String prf = "[CheckListDAOImpl::getCheckListInLocoHtml]";
		LOG.info(prf + " BEGIN");
		
		if (idDocumentoIndex == null) {
			LOG.error(prf + " InvalidParameterException, idDocumentoIndex null");
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}
		if (idDichiarazione == null) {
			LOG.error(prf + " InvalidParameterException, idDichiarazione null");
			throw new InvalidParameterException("idDichiarazione non valorizzato");
		}
		if (idProgetto == null) {
			LOG.error(prf + " InvalidParameterException, idProgetto null");
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			LOG.error(prf + " InvalidParameterException, idUtente null");
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " idDocumentoIndex="+idDocumentoIndex);
		LOG.info(prf + " idDichiarazione="+idDichiarazione);
		
		ChecklistHtmlDTO checklistHtmlDTO = null;
		try {
			checklistHtmlDTO = checklistHtmlBusinessImpl.getCheckListValidazioneHtml(idUtente, identitaDigitale, idDocumentoIndex, idDichiarazione, idProgetto);
		
			if(checklistHtmlDTO==null || (checklistHtmlDTO!=null && StringUtils.isBlank(checklistHtmlDTO.getContentHtml()))){
				LOG.warn(prf + "Attenzione! Modulo html non trovato per idDocumentoIndex: " + idDocumentoIndex);
			}else {
				LOG.info(prf + "prime 100 righe xx chklist validazione : \n"
						+ checklistHtmlDTO.getContentHtml().substring(0, 100));
			}
			
		} catch (CSIException e) {
			LOG.error(prf + " errore " + e.getMessage());
			throw e;
			
		}finally {
			LOG.info(prf + " END");
		}
		return checklistHtmlDTO;
	}

	
	public EsitoOperazioneDTO saveCheckListDocumentaleHtmlOld(Long idUtente, String identitaDigitale, Long checklistSelezionata, 
			Long idProgetto, String statoChecklist,	String checklistHtml) {
		String prf = "[CheckListDAOImpl::saveCheckListDocumentaleHtml]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " checklistSelezionata="+checklistSelezionata);
		LOG.info(prf + " idProgetto="+idProgetto);
		LOG.info(prf + " statoChecklist="+statoChecklist);
	
		EsitoOperazioneDTO ret = new EsitoOperazioneDTO();
		try {
			 
			ChecklistHtmlDTO checkListHtmlDTO = new ChecklistHtmlDTO();
			checkListHtmlDTO.setContentHtml(checklistHtml);
			checkListHtmlDTO.setCodStatoTipoDocIndex(statoChecklist);
			checkListHtmlDTO.setIdProgetto(idProgetto);
			
			
//			if(checklistSelezionata!=null){
//				LOG.info(prf + "chk diversa da null,checklistSelezionata "+checklistSelezionata);
//				
//				ChecklistHtmlDTO checklistHtmlDTO = (ChecklistHtmlDTO) getCheckListValidazioneHtml(idUtente, identitaDigitale, checklistSelezionata, checklistSelezionata, idProgetto);
//				
//				if(checklistHtmlDTO!=null) {
//					LOG.info(prf + "chk diversa da null,checklistHtmlDTO.getIdChecklist= "+checklistHtmlDTO.getIdChecklist());
//					LOG.info(prf + "chk diversa da null,checklistHtmlDTO.getIdDocumentoIndex= "+checklistHtmlDTO.getIdDocumentoIndex());
//					idDichiarazione = checklistHtmlDTO.getI
//				}else {
//					LOG.error(prf + "chk diversa da null,checklistHtmlDTO null");
//					throw new InvalidParameterException("checklistSelezionata non valido"); 
//				}
//				
//				
//				//checkListHtmlDTO.setIdDocumentoIndex(checklistSelezionata);
//			}
			
			EsitoSalvaModuloCheckListHtmlDTO esito = checklistHtmlBusinessImpl.saveCheckListDocumentaleHtml(idUtente, identitaDigitale, 
					idProgetto, statoChecklist, checkListHtmlDTO, checklistSelezionata);
			LOG.info(prf + "after checklistHtmlSrv.saveCheckListInLocoHtml: "+esito.getEsito());

			ret.setEsito(esito.getEsito());
			ret.setCodiceMessaggio(esito.getMessage());
				
		} catch (CSIException e) {
			LOG.error(prf + " CSIException e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		return ret;
	}

	@Override
	public EsitoOperazioneDTO saveCheckListDocumentaleHtml(Long idUtente, String identitaDigitale, Long checklistSelezionata, 
			Long idProgetto, String statoChecklist,	String checklistHtml, FileDTO[] verbali) {
		String prf = "[CheckListDAOImpl::saveCheckListDocumentaleHtml]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " checklistSelezionata="+checklistSelezionata);
		LOG.info(prf + " idProgetto="+idProgetto);
		LOG.info(prf + " statoChecklist="+statoChecklist);
	
		EsitoOperazioneDTO ret = new EsitoOperazioneDTO();
		try {
			 
			ChecklistHtmlDTO checkListHtmlDTO = new ChecklistHtmlDTO();
			checkListHtmlDTO.setContentHtml(checklistHtml);
			checkListHtmlDTO.setCodStatoTipoDocIndex(statoChecklist);
			checkListHtmlDTO.setIdProgetto(idProgetto);
			checkListHtmlDTO.setAllegati(verbali);
			
			
//			if(checklistSelezionata!=null){
//				LOG.info(prf + "chk diversa da null,checklistSelezionata "+checklistSelezionata);
//				
//				ChecklistHtmlDTO checklistHtmlDTO = (ChecklistHtmlDTO) getCheckListValidazioneHtml(idUtente, identitaDigitale, checklistSelezionata, checklistSelezionata, idProgetto);
//				
//				if(checklistHtmlDTO!=null) {
//					LOG.info(prf + "chk diversa da null,checklistHtmlDTO.getIdChecklist= "+checklistHtmlDTO.getIdChecklist());
//					LOG.info(prf + "chk diversa da null,checklistHtmlDTO.getIdDocumentoIndex= "+checklistHtmlDTO.getIdDocumentoIndex());
//					idDichiarazione = checklistHtmlDTO.getI
//				}else {
//					LOG.error(prf + "chk diversa da null,checklistHtmlDTO null");
//					throw new InvalidParameterException("checklistSelezionata non valido"); 
//				}
//				
//				
//				//checkListHtmlDTO.setIdDocumentoIndex(checklistSelezionata);
//			}
			
			EsitoSalvaModuloCheckListHtmlDTO esito = checklistHtmlBusinessImpl.saveCheckListDocumentaleHtml(idUtente, identitaDigitale, 
					idProgetto, statoChecklist, checkListHtmlDTO, checklistSelezionata);
			LOG.info(prf + "after checklistHtmlSrv.saveCheckListInLocoHtml: "+esito.getEsito());

			ret.setIdDocumentoIndexCL(esito.getIdDocumentoIndex());
			ret.setEsito(esito.getEsito());
			ret.setCodiceMessaggio(esito.getMessage());
				
		} catch (CSIException e) {
			LOG.error(prf + " CSIException e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		return ret;
	}

	
	@Override
	public EsitoOperazioneDTO saveCheckListInLocoHtmlDef(Long idUtente, String identitaDigitale, Long checklistSelezionata, Long idProgetto,
			String statoChecklist, String checklistHtml, FileDTO[] verbali) {
		String prf = "[CheckListDAOImpl::saveCheckListInLocoHtmlDef]";
		LOG.info(prf + " BEGIN");
		
		// vecchio pbandiweb CheckListBusinessImpl::salvaChecklistHtmlMulti
		
		LOG.info(prf + " idUtente="+idUtente);
		LOG.info(prf + " identitaDigitale="+identitaDigitale);
		LOG.info(prf + " checklistSelezionata="+checklistSelezionata);
		LOG.info(prf + " idProgetto="+idProgetto);
		LOG.info(prf + " statoChecklist="+statoChecklist);
		if(verbali== null || verbali.length == 0) {
			LOG.debug(prf + " Nessun file allegato.");
		}else {
			LOG.debug(prf + " verbali.length="+verbali.length);
		}
		
		EsitoOperazioneDTO ret = new EsitoOperazioneDTO();
		try {
			 
			ChecklistHtmlDTO checkListHtmlDTO = new ChecklistHtmlDTO();
			checkListHtmlDTO.setContentHtml(checklistHtml);
			checkListHtmlDTO.setCodStatoTipoDocIndex(statoChecklist);
			checkListHtmlDTO.setIdProgetto(idProgetto);
			checkListHtmlDTO.setAllegati(verbali);
			checkListHtmlDTO.setNomeFile(null);// campo di quando c'era 1 solo allegato.
			checkListHtmlDTO.setBytesVerbale(null);
			
			if(checklistSelezionata!=null){
				LOG.info(prf + "chk diversa da null,checklistSelezionata "+checklistSelezionata);
				checkListHtmlDTO.setIdChecklist(checklistSelezionata);
				
				// recupero da DB IdDocumentoIndex
				PbandiTDocumentoIndexVO docIndexVOTmp = checklistHtmlBusinessImpl.getDocumentoIndexByCheckListSelezionata(idProgetto,checklistSelezionata );
				if(docIndexVOTmp!=null) {
					checkListHtmlDTO.setIdDocumentoIndex(docIndexVOTmp.getIdDocumentoIndex().longValue());
				}else {
					checkListHtmlDTO.setIdDocumentoIndex(null);
				}
			}
			
			//LOG.info(prf + " checklistHtmlBusinessImpl="+checklistHtmlBusinessImpl);
			
			EsitoSalvaModuloCheckListDTO esito = checklistHtmlBusinessImpl.saveCheckListInLocoHtml(idUtente, identitaDigitale, idProgetto, statoChecklist, checkListHtmlDTO);
			LOG.info(prf + "after checklistHtmlSrv.saveCheckListInLocoHtml: "+esito.getEsito());

			ret.setEsito(esito.getEsito());
			ret.setCodiceMessaggio(esito.getMessage());
				
		} catch (CSIException e) {
			LOG.error(prf + " CSIException e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		return ret;
	}
}
