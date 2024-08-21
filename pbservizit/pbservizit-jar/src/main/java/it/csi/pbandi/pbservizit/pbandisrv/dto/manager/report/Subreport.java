/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;

public class Subreport {
	
	private String datasourceParamName;
	private String reportParamName;
	private Map<String, Class> parameters;
	private JasperDesign report ;
	private String name;
	private String jrxmlFilename;
	public static final String SUBREPORT_POSTFIX_REPORT_PARAM_NAME = "_SUBREPORT_REP";
	public static final String SUBREPORT_POSTFIX_DS_PARAM_NAME = "_SUBREPORT_DS";
	

	public Subreport(JasperDesign report, String name) {
		this.name = name;
		this.datasourceParamName = name + SUBREPORT_POSTFIX_DS_PARAM_NAME;
		this.reportParamName = name + SUBREPORT_POSTFIX_REPORT_PARAM_NAME;
		this.report = report;
		this.parameters = new HashMap<String, Class>();
		
		/*
		 * Aggiungo i parametri del report al subreport
		 */
		if (report != null ) {
			for (Object o : report.getParametersList()){
				JRDesignParameter param = (JRDesignParameter) o;
				parameters.put(param.getName(), param.getValueClass());
				
			}
		} 
		
	}

	public String getDatasourceParamName() {
		return datasourceParamName;
	}

	public void setDatasourceParamName(String datasourceParamName) {
		this.datasourceParamName = datasourceParamName;
	}

	public String getReportParamName() {
		return reportParamName;
	}

	public void setReportParamName(String reportParamName) {
		this.reportParamName = reportParamName;
	}

	public Map<String, Class> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Class> parameters) {
		this.parameters = parameters;
	}


	public JasperDesign getReport() {
		return report;
	}

	public void setJrxmlFilename(String jrxmlFilename) {
		this.jrxmlFilename = jrxmlFilename;
	}

	public String getJrxmlFilename() {
		return jrxmlFilename;
	}




}
