package it.csi.pbandi.pbweb.pbandisrv.dto.manager.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 * Classe che rappresenta una sezione del report ( che non e'
 * altro che un subreport con parametri, un datasource ed un report 
 * contenente i dati).
 * 
 *
 */
public class MacroSezione {
	
	private String reportParamName;
	private String datasourceParamName;
	private Map<String, Class> parameters;
	private MicroSezione[] microSezioni;
	private String name;
	private Subreport subreport;
	private String templateJrxml;
	private BigDecimal progrBandolineaIntervento;
	private BigDecimal idTipoDocumentoIndex;
	private String descBreveTipoDocIndex;
	private BigDecimal idMacroSezione;
	public static final String SEZIONE_POSTFIX_REPORT_PARAM_NAME = "_SEZIONE_SUB";
	public static final String SEZIONE_POSTFIX_DS_PARAM_NAME = "_SEZIONE_DS";
	
	
	
	
	public MacroSezione (String name,  MicroSezione[] microSezioni) {
		this.name = name;
		this.reportParamName = name + SEZIONE_POSTFIX_REPORT_PARAM_NAME;
		this.datasourceParamName = name + SEZIONE_POSTFIX_DS_PARAM_NAME;
		this.parameters = new HashMap<String, Class>();
		this.setMicroSezioni(microSezioni);
		/*
		 * Se la sezione ha dei textField, allora i parametri
		 * di ogni textField sono anche i parametri della sezione
		 */
		if (microSezioni != null) {
			for (MicroSezione t : microSezioni) {
				if (t.getTextFieldParams() != null)
					this.parameters.putAll(t.getTextFieldParams());
			}
		}
	}

	public MacroSezione (String name) {
		this.name = name;
		this.reportParamName = name + SEZIONE_POSTFIX_REPORT_PARAM_NAME;
		this.datasourceParamName = name + SEZIONE_POSTFIX_DS_PARAM_NAME;
		this.parameters = new HashMap<String, Class>();
	}

	public void setReportParamName(String reportParamName) {
		this.reportParamName = reportParamName;
	}


	public String getReportParamName() {
		return reportParamName;
	}


	public void setDatasourceParamName(String datasourceParamName) {
		this.datasourceParamName = datasourceParamName;
	}


	public String getDatasourceParamName() {
		return datasourceParamName;
	}




	public Map<String, Class> getParameters() {
		return parameters;
	}


	public void setMicroSezioni(MicroSezione[] microSezioni) {
		this.microSezioni = microSezioni;
		/*
		 * Se la sezione ha dei textField, allora i parametri
		 * di ogni textField sono anche i parametri della sezione
		 */
		if (microSezioni != null) {
			for (MicroSezione t : microSezioni) {
				if (t.getTextFieldParams() != null)
					this.parameters.putAll(t.getTextFieldParams());
			}
		}
	}


	


	public String getName() {
		return name;
	}


	public void setSubreport(Subreport subreport) {
		this.subreport = subreport;
		/*
		 * Poiche' il subreport e' contenuto in una sezione,
		 * devo aggiungere ai parametri della sezione anche
		 * i parametri del subreport, il datasource del subreport ed il report
		 */
		if (subreport != null && subreport.getParameters() != null) {
			this.getParameters().putAll(subreport.getParameters());
			this.getParameters().put(subreport.getDatasourceParamName(), JRBeanCollectionDataSource.class);
			this.getParameters().put(subreport.getReportParamName(), JasperReport.class);
		}
		
	}


	public Subreport getSubreport() {
		return subreport;
	}

	public void setTemplateJrxml(String templateJrxml) {
		this.templateJrxml = templateJrxml;
	}

	public String getTemplateJrxml() {
		return templateJrxml;
	}

	public BigDecimal getProgrBandolineaIntervento() {
		return progrBandolineaIntervento;
	}

	public void setProgrBandolineaIntervento(BigDecimal progrBandolineaIntervento) {
		this.progrBandolineaIntervento = progrBandolineaIntervento;
	}

	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}

	public String getDescBreveTipoDocIndex() {
		return descBreveTipoDocIndex;
	}

	public void setDescBreveTipoDocIndex(String descBreveTipoDocIndex) {
		this.descBreveTipoDocIndex = descBreveTipoDocIndex;
	}


	public MicroSezione[] getMicroSezioni() {
		return microSezioni;
	}

	public void setIdMacroSezione(BigDecimal idMacroSezione) {
		this.idMacroSezione = idMacroSezione;
	}

	public BigDecimal getIdMacroSezione() {
		return idMacroSezione;
	}

	



	

}
