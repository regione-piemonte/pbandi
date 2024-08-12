package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.richiestaerogazione.RichiestaErogazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.dichiarazionedispesa.DichiarazioneDiSpesaException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AllegatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoDichiarazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TipoAllegatoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMacroSezioneModuloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDichSpesaDocAllegVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWProgettoDocAllegVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;

/**
 * Manager che gestisce i TipoAllegati
 * @author mohamed.eddaakouri
 *
 */
public class TipoAllegatiManager {
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private RichiestaErogazioneBusinessImpl richiestaErogazioneBusiness;

	
	public BeanUtil getBeanUtil() {
		return beanUtil;
	}
	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	public LoggerUtil getLogger() {
		return logger;
	}
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
	
	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}
	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	
	public RichiestaErogazioneBusinessImpl getRichiestaErogazioneBusiness() {
		return richiestaErogazioneBusiness;
	}
	public void setRichiestaErogazioneBusiness(
			RichiestaErogazioneBusinessImpl richiestaErogazioneBusiness) {
		this.richiestaErogazioneBusiness = richiestaErogazioneBusiness;
	}
	/**
	 * 
	 * @param idDichiarazione
	 * @param idProgetto
	 * @param idBandoLinea
	 * @return
	 */
	public List<TipoAllegatoDTO> findTipoAllegati(Long idDichiarazione, Long idProgetto, Long idBandoLinea, String codTipoDocumentoIndex){
		
		//TODO: Da spostare da qui e metterlo in un manager!
		BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, codTipoDocumentoIndex);
	
		List <TipoAllegatoDTO> allegati = null; //Devo ottenere gli allegati dal database in base all'idDichiarazione oppure idProgetto

		if(idDichiarazione == null || idDichiarazione == 0){
			TipoAllegatoProgettoVO filtroAllegati = new TipoAllegatoProgettoVO();
			filtroAllegati.setIdProgetto(BigDecimal.valueOf(idProgetto));
			filtroAllegati.setFlagAllegato("S");
			filtroAllegati.setProgrBandoLineaIntervento(BigDecimal.valueOf(idBandoLinea));
			filtroAllegati.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			allegati = beanUtil.transformList(genericDAO.findListWhere(filtroAllegati), TipoAllegatoDTO.class);
		}
		else{
			TipoAllegatoDichiarazioneVO filtroAllegati = new TipoAllegatoDichiarazioneVO();
			filtroAllegati.setIdDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
			filtroAllegati.setFlagAllegato("S");
			filtroAllegati.setProgrBandoLineaIntervento(BigDecimal.valueOf(idBandoLinea));
			filtroAllegati.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			allegati = beanUtil.transformList(genericDAO.findListWhere(filtroAllegati), TipoAllegatoDTO.class);
		}

		return allegati;
	}
	
	/**
	 * tipoDocIndex = DS o CFP
	 */
	public void spostaTipoAllegati(Long idBando, Long idProgetto, Long idDichiarazione, String tipoDocIndex) throws Exception{
		
		try{
			//BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, "DS");
			BigDecimal idTipoDocumentoIndex = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, tipoDocIndex);
			
			TipoAllegatoProgettoVO filtroAllegatiProgetto = new TipoAllegatoProgettoVO();
			filtroAllegatiProgetto.setIdProgetto(BigDecimal.valueOf(idProgetto));
			filtroAllegatiProgetto.setFlagAllegato("S");
			filtroAllegatiProgetto.setProgrBandoLineaIntervento(BigDecimal.valueOf(idBando));
			filtroAllegatiProgetto.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			List<PbandiRDichSpesaDocAllegVO> dichSpesa = beanUtil.transformList(genericDAO.findListWhere(filtroAllegatiProgetto), PbandiRDichSpesaDocAllegVO.class);
			for(int i = 0; dichSpesa.size() > 0 && i < dichSpesa.size(); i++){
				dichSpesa.get(i).setidDichiarazioneSpesa(BigDecimal.valueOf(idDichiarazione));
			}
			genericDAO.insert(dichSpesa);
			
			/*
			 * Elimina i dati dalla tabella di supporto 
			 */
			PbandiWProgettoDocAllegVO filtroToDelete = new PbandiWProgettoDocAllegVO();
			filtroToDelete.setIdProgetto(BigDecimal.valueOf(idProgetto));
			genericDAO.deleteWhere(Condition.filterBy(filtroToDelete));
		}catch(Exception ex){
			DichiarazioneDiSpesaException dse = new DichiarazioneDiSpesaException(
				"Errore durante la ricerca della sede di intervento");
			throw dse;
		}
		
	}
	
	
	public  List<TipoAllegatoDTO> findTipoAllegatiErogazione(Long progrBandoLineaIntervento,
			String codCausaleErogazione, Long idProgetto ) throws CSIException,
	SystemException, UnrecoverableException,
	GestioneDatiGeneraliException {
		String[] nameParameter = {"progrBandoLineaIntervento","codCausaleErogazione" }; 
		ValidatorInput.verifyNullValue(nameParameter,progrBandoLineaIntervento,codCausaleErogazione);
		logger.info("\n\n\nfindTipoAllegatiErogazione progrBandoLineaIntervento:"+progrBandoLineaIntervento+
				" ,codCausaleErogazione "+codCausaleErogazione);
				
		BigDecimal idMacroAllegati = decodificheManager.decodeDescBreve(PbandiDMacroSezioneModuloVO.class,
				GestioneDatiDiDominioSrv.DESC_BREVE_MACRO_SEZ_ALLEGATI);
		
		AllegatoVO allegatoVO=new AllegatoVO();
		allegatoVO.setIdMacroSezioneModulo(idMacroAllegati);
		
		BigDecimal idTipoDocIndex = richiestaErogazioneBusiness.getIdTipoDocIndex(codCausaleErogazione);
		allegatoVO.setIdTipoDocumentoIndex(idTipoDocIndex);
		allegatoVO.setAscendentOrder("numOrdinamentoMicroSezione");
		allegatoVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
		
		List<AllegatoVO> allegati = genericDAO.findListWhere(allegatoVO);
		Map <String,String> mapProps=new HashMap<String,String>();
		mapProps.put("contenutoMicroSezione","descTipoAllegato");
		mapProps.put("idTipoDocumentoIndex", "idTipoDocumentoIndex");
		mapProps.put("progrBandoLineaIntervento", "progrBandoLineaIntervento");
		mapProps.put("numOrdinamentoMicroSezione", "numOrdinamentoMicroSezione");
		mapProps.put("idMicroSezioneModulo", "idMicroSezioneModulo");
		
		List<TipoAllegatoDTO> resultList = beanUtil.transformList(allegati, TipoAllegatoDTO.class, mapProps);
		
		List<PbandiWProgettoDocAllegVO> progettoDocAlleg = beanUtil.transformList(resultList, PbandiWProgettoDocAllegVO.class);
		if(progettoDocAlleg != null && progettoDocAlleg.size() > 0){
				
				List<TipoAllegatoProgettoVO> arrayFiltroVO = beanUtil.transformList(resultList, TipoAllegatoProgettoVO.class);
				arrayFiltroVO.get(0).setFlagAllegato("S");
				arrayFiltroVO.get(0).setIdProgetto(BigDecimal.valueOf(idProgetto));
				List<TipoAllegatoProgettoVO> tipoAllegatiList = genericDAO.findListWhere(Condition.filterBy(arrayFiltroVO));
				if(tipoAllegatiList != null && tipoAllegatiList.size() > 0){
					resultList = beanUtil.transformList(tipoAllegatiList , TipoAllegatoDTO.class);
				}	
		}
		
		return resultList; 
	}
 
}
