package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.MacroSezione;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.MicroSezione;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.Subreport;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SezioneModelloJasperVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TemplateJasperVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignExpressionChunk;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;

public class DynamicTemplateManager {
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private DecodificheManager decodificheManager;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private ReportManager reportManager;
	@Autowired
	private TemplateDbManager templateDbManager;
	@Autowired
	private ProgettoManager progettoManager;
	
	public static final int TITLE_BAND_TYPE = 1;
	public static final int DETAIL_BAND_TYPE = 2;
	public static final String TIPO_TEMPLATE_SUBREPORT = "SUB";
	public static final String TIPO_TEMPLATE_MASTER = "MASTER";
	public static final String SUBREPORT_TEMPLATE_KEY = "_SUBREPORT";

	
	
	
	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
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
	
	
	
	public void setTemplateDbManager(TemplateDbManager templateDbManager) {
		this.templateDbManager = templateDbManager;
	}

	public TemplateDbManager getTemplateDbManager() {
		return templateDbManager;
	}

	/**
	 * Restituisce il pdf del modello completo 
	 * @param idTipoDocumentoIndex
	 * @param progrBandolinea
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public byte[] anteprimaDesignTemplate(BigDecimal idTipoDocumentoIndex, BigDecimal progrBandolinea, 
			Map<String, Object> parameters ) throws Exception {
		
		parameters.put("IS_BOZZA", Boolean.TRUE);
		InputStream imageBozza = null;
		try {
			 imageBozza = this.getClass().getClassLoader().getResourceAsStream("report/bozza.gif");
			 parameters.put("imgBozza", imageBozza);
		} catch (Exception e) {
			logger.error("Errore durante il caricamento di bozza.gif", e);
		} 
		/*
		 * Carico il template del master
		 */
		JasperDesign masterTemplateDesign = getReportManager().getReportJRXML("templateModelli_v2.jrxml");
		
		/*
		 * Ricerco le macrosezioni
		 */
		List<MacroSezione> macroSezioni = templateDbManager.findMacroSezioni(idTipoDocumentoIndex, progrBandolinea);
		
		
		/*
		 * per ogni macro-sezione: 
		 * - creo il jasper
		 * - aggiungo ai parametri il datasource(vuoto, cioe' una lista con un solo elemento di tipo string vuoto) 
		 *   ed il report della macro-sezione (cioe' il jasper).
		 */
		
		for(int i=0; i < macroSezioni.size(); ++i) {
			MacroSezione macroSezione = macroSezioni.get(i);
			JasperReport sezioneJasper = createJasperMacroSezione(macroSezione, masterTemplateDesign, i);
			parameters.put(macroSezione.getDatasourceParamName(),createDatasourceVuoto());
			parameters.put(macroSezione.getReportParamName(), sezioneJasper);
		}
		

		
		/*
		 * Creo il jasper del master
		 */
		JasperReport masterJasper = compileDesign(masterTemplateDesign);
		
		DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX,idTipoDocumentoIndex);
		logger.info("\n\n\ndecodifica.getDescrizione() : "+ decodifica.getDescrizione());
		parameters.put("tipoTemplate", decodifica.getDescrizione());
		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(masterJasper, parameters,new JREmptyDataSource());
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	

	
	
	/**
	 * Aggiunge il datasource, relativo al template, ai parametri del report
	 * @param ds
	 * @param template: indica il subreport nel quale aggiungere il datasource
	 * @param parameters: mappa dei parametri del master report
	 * @throws Exception
	 */
	public <T> void  addReportToTemplateJasper(JRBeanCollectionDataSource ds, 
			Class<T>  datasourceBeanClass, TemplateJasperVO template,
			Map<String, Object> parameters) throws Exception {
		String datasourceParamName = template.getSubDsParamName();
		String reportParamNane = template.getSubReportParamName();
		
		
		
		if(ObjectUtil.isEmpty(ds.getData())) {
				List<T> list = new ArrayList<T>();
				list.add(datasourceBeanClass.newInstance());
				ds = new JRBeanCollectionDataSource(list);
		}
		
		/*
		 * Verifico che il template sia di tipo subreport
		 */
		if (!template.getDescBreveTipoTemplate().equals(TIPO_TEMPLATE_SUBREPORT)) {
			Exception e = new Exception("Errore: Il template[tipoTemplate="+
					template.getDescBreveTipoTemplate()+"] non e' del tipo "+TIPO_TEMPLATE_SUBREPORT);
			throw e;
		}
		
		/*
		 * Ricerco i dati della macrosezione del template
		 */
		MacroSezione macroSezione = templateDbManager.findMacroSezione(template.getIdTipoDocumentoIndex(), 
				template.getProgrBandolineaIntervento(), 
				template.getIdMacroSezioneModulo());
		
		
		if (macroSezione == null) {
			Exception e = new Exception("Macrosezione non trovata.");
			throw e;
		}
		
		/*
		 * Verifico che la macrosezione contiene un subreport.
		 */
		if (macroSezione.getSubreport() == null) {
			Exception e = new Exception("Errore: la macrosezione non prevede nessun subreport. Controllare la configurazione del template [idTipoDocumentoIndex="+template.getIdTipoDocumentoIndex()+" progrBandolineaIntervento="+template.getProgrBandolineaIntervento()+" idMacroSezioneModulo="+template.getIdMacroSezioneModulo()+ "].");
			throw e;
		}
		
		/*
		 * Recupero il .jasper del report dal classloader
		 */
		String jasperFilename = macroSezione.getSubreport().getJrxmlFilename().replaceAll(".jrxml", ".jasper");
		JasperReport report = reportManager.getReportCompilato(jasperFilename);
		
		/*
		 * Aggiungo ai parametri del report master, il datasource ed il report
		 */
		parameters.put(datasourceParamName, ds);
		parameters.put(reportParamNane, report);
		
	}
	
	
	
	
	
/*
 * BEGIN: Utilities
 * Metodi di utilita' utilizzati
 * sia per il disegno del template e sia per la creazione del jasper	
 */
	
	
	JRBeanCollectionDataSource createDatasourceVuoto() {
		List <Object>listVuotaPerSubreport=new ArrayList<Object>();
		listVuotaPerSubreport.add("");
		return new JRBeanCollectionDataSource(listVuotaPerSubreport);
	}

	

	
/*
 * END. Utilities	
 */
	
	
	
/*
 * BEGIN: Design
 * Metodi per il disegno del report	
 */
	
	
	JasperReport compileDesign(JasperDesign design)throws JRException {
		logger.begin();
		logger.debug("Compilazione del template "+design.getName());
		JRDesignBand titleBand = getBand(design, TITLE_BAND_TYPE);
		JRDesignBand detailBand = getBand(design, DETAIL_BAND_TYPE);
		/*
		 * Elimino dalla banda TITLE e dalla banda DETAIL gli eventuali subreport 
		 * utilizzati per creare gli altri subreport.
		 */
		if (titleBand != null && getSubreportTemplate(titleBand) != null ) {
			titleBand.removeElement(getSubreportTemplate(titleBand));
		}
		if (detailBand != null && getSubreportTemplate(detailBand) != null) {
			detailBand.removeElement(getSubreportTemplate(detailBand));
		}
		JasperReport template = JasperCompileManager.compileReport(design);
		logger.end();
		return template;
	}
	
	/**
	 * Crea il file .jasper della macro-sezione ed lo inserisce nella posizione indicata all' interno del template master
	 * @param sezione
	 * @param templateModello
	 * @param position : posizione nella quale inserire il report
	 * @return
	 * @throws Exception
	 */
	JasperReport createJasperMacroSezione (MacroSezione sezione, JasperDesign templateModello, int position) throws Exception {
		JasperReport result  =  null;
		/*
		 * Aggiungo la macro sezione al template come subreport della banda DETAIL
		 */
		addMacroSezioneToTemplate(sezione, templateModello, DETAIL_BAND_TYPE,  position);
		
		/*
		 * Genero il report
		 */
		if (sezione.getSubreport() == null)
			result =  createSezione(sezione);
		else 
			result = createSubreport(sezione);
		return result;
	}


	/**
	 * Inserisce la macrosezione come subreport del master template nella banda e nella posizione indicata
	 * @param sezione
	 * @param masterTemplate
	 * @param bandType
	 * @param position
	 * @return
	 * @throws Exception
	 */
	private void addMacroSezioneToTemplate(MacroSezione sezione, JasperDesign masterTemplate,  int bandType,   int position) throws Exception {
		JRDesignBand band = getBand(masterTemplate, bandType);
		insertToBand(masterTemplate, band, sezione.getDatasourceParamName(), sezione.getReportParamName(), sezione.getParameters(), position);
	}
	
	
	private JRDesignBand getBand(JasperDesign masterTemplate, int bandType) {
		JRDesignBand result = null;
		switch(bandType){
		case TITLE_BAND_TYPE:
			result = (JRDesignBand) masterTemplate.getTitle();
			break;
		case DETAIL_BAND_TYPE:
			result = (JRDesignBand) masterTemplate.getDetailSection().getBands()[0];
			break;
		}
		return result;
	}
	
	
	/**
	 * Restituisce il template del subreport della banda utilizzato come clone per creare tutti 
	 * gli altri subreport
	 * @param band
	 * @return
	 */
	private JRDesignSubreport getSubreportTemplate(JRDesignBand band) {
		JRDesignSubreport result = (JRDesignSubreport) band.getElementByKey(SUBREPORT_TEMPLATE_KEY);
		return result;
	}
	
	/**
	 * Aggiunge un subreport al report master.
	 * Il subreport viene inserito all' interno della banda specifica, nella posizione indicata
	 * @param masterReportDesign
	 * @param band
	 * @param designDatasourceParamName: nome del datasource del subreport( sara' sempre un datasource vuoto)
	 * @param designReportParamName: nome del report contenete i dati
	 * @param params: parametri del subreport
	 * @param position
	 * @throws Exception
	 */
	private void insertToBand(JasperDesign masterReportDesign, JRDesignBand band, 
			String designDatasourceParamName, String designReportParamName, 
			Map<String,Class> params, int position ) throws Exception{
		/* Poiche' ogni subreport ha un datasource, un report e dei parametri associati, 
		 * per poter essere visibile devono essere aggiunti anche ai parametri del report principale.
		 */
		addDesignParameters(masterReportDesign, params);
		addDesignDatasourceParameter(masterReportDesign, designDatasourceParamName);
		addDesignReportParameter(masterReportDesign, designReportParamName);
		
		/*
		 * Clono il template del subreport.
		 */
		JRDesignSubreport subreportDesign = (JRDesignSubreport) getSubreportTemplate(band).clone();
		subreportDesign.setKey(null);
		
		/*
		 * Aggiungo al subreport i parametri
		 */
		if (params != null){
			for (String key : params.keySet()) {
				
				JRDesignSubreportParameter param = new JRDesignSubreportParameter();
				param.setName(key);
				JRDesignExpression expression = new JRDesignExpression();
				expression.setText("$P{"+key+"}");
				expression.setValueClass(java.lang.Object.class);
				param.setExpression(expression);
				subreportDesign.addParameter(param);
				
			}
		}
		/*
		 * Aggiungo il parametro datasource dal subreport
		 */
		JRDesignExpressionChunk datasourceExpression = (JRDesignExpressionChunk) subreportDesign.getDataSourceExpression().getChunks()[0];
		datasourceExpression.setText(designDatasourceParamName);
		
		/*
		 * Aggiungo il parametro report al subreport
		 */
		JRDesignExpressionChunk subreportExpression = (JRDesignExpressionChunk) subreportDesign.getExpression().getChunks()[0];
		subreportExpression.setText(designReportParamName);
		
		
		band.addElement(position,subreportDesign);
		subreportDesign.setY(position*20);
		band.setHeight(band.getHeight()+20);
		
	}
	
	/**
	 * Crea il .jasper della macro-sezione senza subreport
	 * @param sezione
	 * @return
	 * @throws Exception
	 */
	private JasperReport createSezione (MacroSezione sezione) throws Exception {
		/*
		 * Carico il template jrxml
		 */
		JasperDesign sezioneTemplate = reportManager.getReportJRXML(sezione.getTemplateJrxml());
		sezioneTemplate.setName(sezione.getName()+":"+sezione.getTemplateJrxml());
		
		/*
		 * Aggiungo i parametri al template
		 */
		addDesignParameters(sezioneTemplate, sezione.getParameters());
		
		// DETTAGLIO
		/*
		 * Aggiungo le microsezioni alla banda DETAIL del template
		 */
		JRDesignBand detailBand = getBand(sezioneTemplate, DETAIL_BAND_TYPE);
		addMicroSezioniToBand(detailBand, sezione.getMicroSezioni());
		
		
		
		/*
		 * Compilo il template
		 */
		return compileDesign(sezioneTemplate);
	}
	
	public JasperReport crateSezioneAlt (MacroSezione sezione) throws Exception{
		return createSezione(sezione);
	}
	
	
	

	
	
	/**
	 * Crea il .jasper della macro-sezione con il subreport
	 * @param sezione
	 * @return
	 * @throws Exception
	 */
	private JasperReport createSubreport (MacroSezione sezione) throws Exception{
		/*
		 * Carico il template jrxml
		 */
		JasperDesign sezioneTemplate = reportManager.getReportJRXML(sezione.getTemplateJrxml());
		sezioneTemplate.setName(sezione.getName()+":"+sezione.getTemplateJrxml());
		
		
		Subreport subreport = sezione.getSubreport();
		/*
		 * TITOLO
		 */
		/*
		 * Aggiungo le microsezioni alla banda TITLE del template
		 */
		JRDesignBand titleBand = getBand(sezioneTemplate, TITLE_BAND_TYPE);
		addMicroSezioniToBand(titleBand, sezione.getMicroSezioni());
		
		/*
		 * DETTAGLIO
		 */
		/*
		 * Aggiungo il report alla banda DETAIL del template
		 */
		JRDesignBand detailBand = getBand(sezioneTemplate, DETAIL_BAND_TYPE);
		insertToBand(sezioneTemplate, detailBand, subreport.getDatasourceParamName(), subreport.getReportParamName(), subreport.getParameters(), 0);
		return compileDesign(sezioneTemplate);
	}
	
	
	private void addDesignParameters(JasperDesign template_design, Map<String, Class> designParams) throws Exception {
		if (designParams != null) {
			for (String key : designParams.keySet()) {
				try {
					addDesignParameter(template_design, key, designParams.get(key));
				} catch (Exception e){
					logger.error("Param:"+key);
					throw e;
				}
			}
		}
	}
	
	
	private void addDesignReportParameter(JasperDesign masterReportDesign,
			String reportParamName) throws JRException {
		addDesignParameter(masterReportDesign, reportParamName, JasperReport.class);
		
	}


	private void addDesignDatasourceParameter(JasperDesign masterReportDesign,
			String datasourceParamName) throws JRException {
		addDesignParameter(masterReportDesign, datasourceParamName, JRBeanCollectionDataSource.class);
		
	}


	
	private void addDesignParameter(JasperDesign templateDesign, String nomeParametro, Class clazz) throws JRException {
		JRDesignParameter parameter = new JRDesignParameter();
		parameter.setName(nomeParametro);
		parameter.setValueClass(clazz);
		if (!templateDesign.getParametersMap().containsKey(nomeParametro))
			templateDesign.addParameter(parameter);
	}
	
	/**
	 * Aggiunge alla banda i testi (parametrici) letti dal db
	 * @param band
	 * @param dati
	 */
	private void addMicroSezioniToBand(JRDesignBand  band, MicroSezione[] dati) {
		/*
		 * Le microsezioni contengono i testi che devono essere visualizzati.
		 * Questi testi possono contenere anche dei parametri nella forma testo $P{<nome_parametro>},
		 * in questo caso la stringa contenente il testo deve essere normalizzata
		 * affinche' il report possa leggere i parametri correttamente.
		 * Alcuni esempi di normalizzazione sono i seguenti:
		 * - db: pippo $P{param1} deve trovare ${param2}
		 *   normalizzata: "pippo " + $P{param1} + " deve trovare " + $P{param2}
		 * 
		 * - db: pippo $P{param1} $P{param2}
		 *   normalizzata: "pippo " + $P{param1} + $P{param2}
		 *   
		 *   
		 * Ogni testo sara' inserito in un campo di tipo textField
		 */
		JRDesignTextField textFiledTemplate = (JRDesignTextField) band.getChildren().get(0);
		int i = 0;
		for (MicroSezione t : dati) {
			band.setHeight(textFiledTemplate.getHeight()*i+textFiledTemplate.getHeight());
			JRDesignExpression expression = new JRDesignExpression();
			expression.setValueClass(java.lang.String.class);
			/*
			 * Se la microsezione contiene dei parametri allora ne normalizzo il testo
			 * altrimenti no.
			 */
			if (t.getTextFieldParams()!= null && !t.getTextFieldParams().isEmpty())
				expression.setText(normalizeParam(t.getTesto()));
			else
				expression.setText("\""+t.getTesto()+"\"");
			JRDesignTextField textField = (JRDesignTextField) textFiledTemplate.clone();
			textField.setExpression(expression);
			textField.setY(textFiledTemplate.getHeight()*i);
			switch (t.getAlign()) {
				case MicroSezione.ALIGN_CENTER:
					textField.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
					break;
				case MicroSezione.ALIGN_LEFT:
					textField.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
					break;
				case MicroSezione.ALIGN_RIGHT:
					textField.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);				
					break;
				case MicroSezione.ALIGN_JUSTIFIED:
					textField.setHorizontalAlignment(HorizontalAlignEnum.JUSTIFIED);
					break;
			}
			band.addElement(textField);
			++i;
		}
	}
	
	
	private String normalizeParam(String text) {
		StringBuffer b = new StringBuffer();
		String result = null;
		for (String token : text.split("\\$P")) {
			if (!token.startsWith("{")) {
				b.append("\""+token+"\"+");
			} else {
				int end = token.indexOf("}");
				b.append("$P"+token.substring(0,end+1)+"+\""+token.substring(end+1, token.length())+ "\"+");
			}
		}
		result = b.toString().substring(0,b.toString().length()-1);
		return result;
	}
	
	
	
	
	
	

	
	MacroSezione popolaMacroSezione(SezioneModelloJasperVO vo) throws Exception {
		MacroSezione macroSezione = new MacroSezione(vo.getDescBreveMacroSezione()+vo.getNumOrdinamentoMacroSezione());
		macroSezione.setIdTipoDocumentoIndex(vo.getIdTipoDocumentoIndex());
		macroSezione.setDescBreveTipoDocIndex(vo.getDescBreveTipoDocIndex());
		macroSezione.setProgrBandolineaIntervento(vo.getProgrBandolineaIntervento());
		macroSezione.setIdMacroSezione(vo.getIdMacroSezioneModulo());
		macroSezione.setTemplateJrxml(vo.getTemplateJrxml());
		if (vo.getReportJrxml() != null && vo.getReportJrxml().trim().length() > 0 ) {
			Subreport subreport = new Subreport(reportManager.getReportJRXML(vo.getReportJrxml()), 
					macroSezione.getName());
			subreport.setJrxmlFilename(vo.getReportJrxml());
			macroSezione.setSubreport(subreport);
		}
		return macroSezione;
	}
	
	
	
	
	
	public byte[] createTimbro(Long idProgetto, Long idDocumentoDiSpesa, Long idDocumentoIndex)throws Exception {
		
		/*
		 * Carico il template 
		 */
		JasperDesign timbroTemplateDesign = getReportManager().getReportJRXML("timbro_documento.jrxml");
		
		
		
		/*
		 * Carico i parametri del template
		 */
		DocumentoDiSpesaProgettoVO filterVO = new DocumentoDiSpesaProgettoVO();
		filterVO.setIdDocumentoDiSpesa(NumberUtil.toBigDecimal(idDocumentoDiSpesa));
		filterVO.setIdProgetto(NumberUtil.toBigDecimal(idProgetto));
		
		DocumentoDiSpesaProgettoVO documento = genericDAO.findSingleWhere(filterVO);
		
		
		BandoLineaProgettoVO bandolineaProgettoVO = progettoManager.findBandoLineaForProgetto(idProgetto);
		
		
		PbandiTDocumentoIndexVO documentoIndex = new PbandiTDocumentoIndexVO();
		documentoIndex.setIdDocumentoIndex(NumberUtil.toBigDecimal(idDocumentoIndex));
		documentoIndex = genericDAO.findSingleWhere(documentoIndex);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("bando", bandolineaProgettoVO.getNomeBandoLinea());
		parameters.put("progetto", bandolineaProgettoVO.getCodiceVisualizzato());
		parameters.put("tipoDocumento", documento.getDescTipoDocumentoSpesa());
		parameters.put("totaleDocumento", NumberUtil.toDouble(documento.getImportoTotaleDocumento()));
		parameters.put("rendicontabile", NumberUtil.toDouble(documento.getImportoRendicontazione()));
		parameters.put("protocollo", documentoIndex.getNumProtocollo());
		parameters.put("dataDocumento", DateUtil.getDate(documento.getDtEmissioneDocumento()));
		parameters.put("numeroDocumento", documento.getNumeroDocumento());
		
		JasperReport timbroReport = compileDesign(timbroTemplateDesign);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(timbroReport, parameters,new JREmptyDataSource());
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
/*
 * END: Design	
 */
	
}
