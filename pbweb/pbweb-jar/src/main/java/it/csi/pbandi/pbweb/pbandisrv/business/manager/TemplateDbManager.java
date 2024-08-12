package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.MacroSezione;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.MicroSezione;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.Modello;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SezioneModelloJasperVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TemplateJasperVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoTemplateVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTTemplateVO;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

public class TemplateDbManager {
	
	@Autowired
	private ReportManager reportManager;
	@Autowired
	private DynamicTemplateManager dynamicTemplateManager;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;
	
	public static final int TITLE_BAND_TYPE = 1;
	public static final int DETAIL_BAND_TYPE = 2;
	public static final String TIPO_TEMPLATE_SUBREPORT = "SUB";
	public static final String TIPO_TEMPLATE_MASTER = "MASTER";
	public static final String SUBREPORT_TEMPLATE_KEY = "_SUBREPORT";
	
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}
	
	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public ReportManager getReportManager() {
		return reportManager;
	}
	
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	
	public void setDynamicTemplateManager(DynamicTemplateManager dynamicTemplateManager) {
		this.dynamicTemplateManager = dynamicTemplateManager;
	}

	public DynamicTemplateManager getDynamicTemplateManager() {
		return dynamicTemplateManager;
	}
	

	List<MacroSezione> findMacroSezioni(BigDecimal idTipoDocumentoIndex, 
			BigDecimal progrBandolinea) throws Exception {
		List<MacroSezione> result = new ArrayList<MacroSezione>();
		
		SezioneModelloJasperVO filterVO = new SezioneModelloJasperVO();
		filterVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		filterVO.setProgrBandolineaIntervento(progrBandolinea);
		filterVO.setAscendentOrder("numOrdinamentoMacroSezione","numOrdinamentoMicroSezione");
		
		List<SezioneModelloJasperVO> sezioni = genericDAO.findListWhere(filterVO);
		BigDecimal idMacro = null;
		int macroSezionePosition = 0;
		int textFieldPosition = 0;
		MacroSezione macroSezione = null;
		List<MicroSezione> datiMacroSezione = new ArrayList<MicroSezione>();
		
		for (SezioneModelloJasperVO vo : sezioni) {
			if (vo.getTemplateJrxml() != null && vo.getTemplateJrxml().trim().length() > 0 ) {
				MicroSezione t = new MicroSezione();
				t.setTesto(vo.getContenutoMicroSezione());
				if (vo.getDescMicroSezione().startsWith("SEZIONE ")) {
					t.setAlign(MicroSezione.ALIGN_CENTER);
				}
				if (idMacro == null || idMacro.compareTo(vo.getIdMacroSezioneModulo()) != 0 ) {
					if (idMacro != null) {
						macroSezione.setMicroSezioni(datiMacroSezione.toArray(new MicroSezione[]{}));
						datiMacroSezione = new ArrayList<MicroSezione>();
						result.add(macroSezionePosition,macroSezione);
						macroSezionePosition++;
						textFieldPosition = 0;
					}
					idMacro = vo.getIdMacroSezioneModulo();
					macroSezione = dynamicTemplateManager.popolaMacroSezione(vo);
				}
				datiMacroSezione.add(textFieldPosition,t);
				textFieldPosition++;
			}
		}
		if (!ObjectUtil.isEmpty(sezioni) && ObjectUtil.isEmpty(datiMacroSezione)) {
			/*
			 * Non e' stato configurato il campo TemplateJrxml per le 
			 * macro sezioni
			 */
			Exception e = new Exception("Non � stato configurato il campo TemplateJrxml per le macrosezioni del tipo di documento "+idTipoDocumentoIndex+" per il bandolinea "+progrBandolinea);
			throw e;
		}
		/*
		 * aggiungo i dati all' ultima sezione
		 */
		if (macroSezione != null ){
			macroSezione.setMicroSezioni(datiMacroSezione.toArray(new MicroSezione[]{}));
			result.add(macroSezione);
		}
		
		return result;
	}
	
		
	/**
	 * ricerca i dati dei subreports
	 */
	List<TemplateJasperVO> findSubreportsTemplateJasper (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandolinea) {
		TemplateJasperVO filterVO = new TemplateJasperVO();
		filterVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		filterVO.setProgrBandolineaIntervento(progrBandolinea);
		filterVO.setDescBreveTipoTemplate(TIPO_TEMPLATE_SUBREPORT);
		
		List<TemplateJasperVO> templates = genericDAO.findListWhere(filterVO);
		
		return templates;
	}
	
	
	/**
	 * Ricerca i dati del subreport relativo alla macrosezione indicata.
	 * @param idTipoDocumentoIndex
	 * @param progrBandolinea
	 * @param idMacroSezione
	 * @return
	 */
	public TemplateJasperVO findSubreportTemplateJasper (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandolinea, BigDecimal idMacroSezione) {
		TemplateJasperVO filterVO = new TemplateJasperVO();
		filterVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		filterVO.setProgrBandolineaIntervento(progrBandolinea);
		filterVO.setIdMacroSezioneModulo(idMacroSezione);
		filterVO.setDescBreveTipoTemplate(TIPO_TEMPLATE_SUBREPORT);
		
		filterVO = genericDAO.findSingleWhere(filterVO);
		
		return  filterVO;
	}
	
	/**
	 * Ricerca i dati del template master 
	 * @param idTipoDocumentoIndex
	 * @param progrBandolinea
	 * @return
	 */
	public TemplateJasperVO findMasterTemplateJasper (BigDecimal idTipoDocumentoIndex, BigDecimal progrBandolinea) {
		TemplateJasperVO filterVO = new TemplateJasperVO();
		filterVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		filterVO.setProgrBandolineaIntervento(progrBandolinea);
		filterVO.setDescBreveTipoTemplate(TIPO_TEMPLATE_MASTER);
		
		 filterVO = genericDAO.findSingleWhere(filterVO);
		
		return filterVO;
	}

	
/*
 * END: Jasper	
 */

	
	
	/**
	 * 
	 */
	public void saveTemplate(Long idUtente,
			Long idTipoDocumentoIndex, Long progrBandoLinea)
			throws Exception {
		
		String[] nameParameter = { "idUtente", "idTipoDocumentoIndex",
				"progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				idTipoDocumentoIndex, progrBandoLinea);

		PbandiCTipoDocumentoIndexVO docIndexVO = new PbandiCTipoDocumentoIndexVO();
		docIndexVO.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocumentoIndex));
		docIndexVO = genericDAO.findSingleWhere(docIndexVO);
		
		
		/*
		 * Cancello tutto il contenuto di PBandiTTemplate per
		 * il tipodocumento e progrbandolinea.
		 */
		PbandiTTemplateVO filtroVO = new PbandiTTemplateVO();
		filtroVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		filtroVO.setIdTipoDocumentoIndex(docIndexVO.getIdTipoDocumentoIndex());
		genericDAO.deleteWhere(Condition.filterBy(filtroVO));
		
		
		/*
		 * Carico design del template master
		 */
		JasperDesign masterTemplateDesign = reportManager.getReportJRXML("templateModelli_v2.jrxml");
		
		/*
		 * Ricerco le macro-sezioni del template
		 */
		List<MacroSezione> macroSezioni = findMacroSezioni(new BigDecimal(idTipoDocumentoIndex), new BigDecimal(progrBandoLinea));
		
		if (ObjectUtil.isEmpty(macroSezioni)) {
			Exception e = new Exception("Nessuna macrosezione configurata per il bandolinea "+progrBandoLinea+" e tipo documento index "+idTipoDocumentoIndex+"["+docIndexVO.getDescTipoDocIndex()+"]");
			throw e;
		}
		
		
		/*
		 * Per ogni macro-sezione creo il jasper e lo salvo
		 * sul db e su index
		 */
		for (int i=0; i< macroSezioni.size(); ++i ) {	
			MacroSezione macroSezione = macroSezioni.get(i);
			logger.info("   "+macroSezione.getName()+" [template:"+macroSezione.getTemplateJrxml()+"]");
			JasperReport sezioneJasper = dynamicTemplateManager.createJasperMacroSezione(macroSezione, masterTemplateDesign, i);
			saveSezioneTemplate(sezioneJasper, macroSezione, idUtente);
		}
		
		
		/*
		 * Creo il jasper del template master
		 */
		JasperReport masterJasper = dynamicTemplateManager.compileDesign(masterTemplateDesign);
		
		
		/*
		 * Salvo il master sul db e su index
		 */
		saveMasterTemplate(masterJasper, docIndexVO.getDescBreveTipoDocIndex(), idTipoDocumentoIndex, progrBandoLinea, idUtente);
				
	}
	
	
	/*
	 * 
	 * TNT salvataggio subreport come Blob
	 * */
	
	private void saveSezioneTemplate (JasperReport report, MacroSezione sezione, Long  idUtente) throws Exception {
		String nomeFile = new String (sezione.getName()+"_"+sezione.getDescBreveTipoDocIndex()+
				"_"+sezione.getProgrBandolineaIntervento()+".jasper");
		

		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JRSaver.saveObject(report, os);
		
		
		PbandiDTipoTemplateVO tipoTemplateVO = new PbandiDTipoTemplateVO();
		tipoTemplateVO.setDescBreveTipoTemplate(TIPO_TEMPLATE_SUBREPORT);
		tipoTemplateVO = genericDAO.findSingleWhere(tipoTemplateVO);
		
		PbandiTTemplateVO vo = new PbandiTTemplateVO();
		vo.setIdTipoTemplate(tipoTemplateVO.getIdTipoTemplate());
		vo.setSezDsParamName(sezione.getDatasourceParamName());
		vo.setSezReportParamName(sezione.getReportParamName());
		vo.setProgrBandoLineaIntervento(sezione.getProgrBandolineaIntervento());
		vo.setIdTipoDocumentoIndex(sezione.getIdTipoDocumentoIndex());
		vo.setIdMacroSezioneModulo(sezione.getIdMacroSezione());
		if (sezione.getSubreport() != null){
			vo.setSubDsParamName(sezione.getSubreport().getDatasourceParamName());
			vo.setSubReportParamName(sezione.getSubreport().getReportParamName());
		}
		vo.setNomeTemplate(nomeFile);
		
		vo.setIdUtenteIns(new BigDecimal(idUtente));
		
		vo.setJasperblob(os.toByteArray());
		
		vo=genericDAO.insert(vo);
		
	}	

	
	
	private void saveMasterTemplate (JasperReport report, String descBreveTipoDocumentoIndex, Long idTipoDocumentoIndex, Long progrBandolinea, Long idUtente) throws Exception {
		String nomeFile = new String ("templateModello_"+descBreveTipoDocumentoIndex+"_"+progrBandolinea+".jasper");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		JRSaver.saveObject(report, os);
		
		
		PbandiDTipoTemplateVO tipoTemplateVO = new PbandiDTipoTemplateVO();
		tipoTemplateVO.setDescBreveTipoTemplate(TIPO_TEMPLATE_MASTER);
		tipoTemplateVO = genericDAO.findSingleWhere(tipoTemplateVO);
		
		PbandiTTemplateVO vo = new PbandiTTemplateVO();
		vo.setIdTipoTemplate(tipoTemplateVO.getIdTipoTemplate());
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandolinea));
		vo.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocumentoIndex));
		vo.setNomeTemplate(nomeFile);
		

		
		vo.setIdUtenteIns(new BigDecimal(idUtente));
		
		vo.setJasperblob(os.toByteArray());		
		
		vo=genericDAO.insert(vo);
		
	}
	

	/**
	 * 
	 * @param idTipoDocumentoIndex
	 * @param progrBandolinea
	 * @return
	 * @throws Exception
	 */
	public Modello recuperaModello(BigDecimal idTipoDocumentoIndex,
		BigDecimal progrBandolinea) throws Exception{
		
		long start=System.currentTimeMillis();
		Modello modello = new Modello();
		
		/*
		 * Ricerco i subreports del master;
		 */
		List<TemplateJasperVO> subReportTemplates = findSubreportsTemplateJasper(idTipoDocumentoIndex, progrBandolinea);
		
		
		modello.setTemplateReports(subReportTemplates);
		
		/*
		 * Recupero i .jasper dei subreports ed aggiungo ai parametri del master
		 * il datasource (vuoto) ed i subreports
		 */
		long startSubReport=System.currentTimeMillis();
		
		for (TemplateJasperVO template : subReportTemplates) {
			
			byte [] bytesSubReport =template.getJasperblob();
			
			
			JasperReport report = (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(bytesSubReport));
			/*
			 * Aggiungo ai parametri del master il subreport relativo alla macrosezione ed il datasource (vuoto) per il subreport.
			 * Se la macrosezione deve contenere un report (cioe' una tabella) il parametro datasource ( subDsParamName) ed il
			 * parametro report (subReportParamName) devono essere aggiunti ai parametri dal metodo di business specifico del
			 * report che si vuole aggiungere.
			 */
			modello.getMasterParameters().put(template.getSezDsParamName(), dynamicTemplateManager.createDatasourceVuoto());
			modello.getMasterParameters().put(template.getSezReportParamName(), report);
		}
		logger.info("\n\n\n########################\nrecuperaModello()  ,"+subReportTemplates.size()+" subReports recuperati su Index in "+(System.currentTimeMillis()-startSubReport)+" ms\n");		
		
		/*
		 * Ricerco il .jasper del template
		 */
		TemplateJasperVO masterTemplate = findMasterTemplateJasper(idTipoDocumentoIndex, progrBandolinea);
		
		/*
		 * Recupero il file del master 
		 */
		long startMaster=System.currentTimeMillis();
		byte [] bytesMasterReport =masterTemplate.getJasperblob();
		logger.info("\n\n\n########################\nrecuperaModello(), MASTER recuperato in "+(System.currentTimeMillis()-startMaster)+" ms\n");

		
		JasperReport master = (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(bytesMasterReport));
		
		modello.setMasterReport(master);
		
		logger.info("\n\n\n########################\nrecuperaModello() eseguito in "+(System.currentTimeMillis()-start)+" ms\n");
		return modello;
	}

	
	MacroSezione findMacroSezione(BigDecimal idTipoDocumentoIndex,
			BigDecimal progrBandolinea, BigDecimal idMacroSezione) throws Exception {
		SezioneModelloJasperVO filterVO = new SezioneModelloJasperVO();
		filterVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		filterVO.setProgrBandolineaIntervento(progrBandolinea);
		filterVO.setIdMacroSezioneModulo(idMacroSezione);
		filterVO.setAscendentOrder("numOrdinamentoMacroSezione",
				"numOrdinamentoMicroSezione");
		
		List<SezioneModelloJasperVO> sezioni = genericDAO.findListWhere(filterVO);
		BigDecimal idMacro = null;
		int textFieldPosition = 0;
		MacroSezione macroSezione = null;
		List<MicroSezione> datiMacroSezione = new ArrayList<MicroSezione>();
		
		for (SezioneModelloJasperVO vo : sezioni) {
			if (vo.getTemplateJrxml() != null && vo.getTemplateJrxml().trim().length() > 0 ) {
				MicroSezione t = new MicroSezione();
				t.setTesto(vo.getContenutoMicroSezione()+"  ");
				if (vo.getDescMicroSezione().startsWith("SEZIONE ")) {
					t.setAlign(MicroSezione.ALIGN_CENTER);
				}
				if (idMacro == null ) {
					idMacro = vo.getIdMacroSezioneModulo();
					macroSezione = dynamicTemplateManager.popolaMacroSezione(vo);
				}
				
				if (idMacro.compareTo(vo.getIdMacroSezioneModulo()) != 0) {
					Exception e = new Exception("Errore in fase di ricerca dati macrosezione["+idMacroSezione+"]. Trovati dati relativi ad un' altra sezione ["+vo.getIdMacroSezioneModulo()+"]");
					throw e;
				}
				
				datiMacroSezione.add(textFieldPosition,t);
				textFieldPosition++;
			}
		}
		/*
		 * aggiungo alla macrosezione i dati relativi alle microsezioni
		 */
		if (macroSezione != null ){
			macroSezione.setMicroSezioni(datiMacroSezione.toArray(new MicroSezione[]{}));
		}
		
		return macroSezione;
	}
	

	
	
}
