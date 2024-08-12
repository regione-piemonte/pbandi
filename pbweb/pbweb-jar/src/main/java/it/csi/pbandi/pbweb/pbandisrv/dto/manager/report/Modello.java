package it.csi.pbandi.pbweb.pbandisrv.dto.manager.report;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.TemplateJasperVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;

public class Modello {
	
	private JasperReport masterReport;
	private List<TemplateJasperVO> templateReports;
	private Map<String, Object> masterParameters;
	
	public Modello() {
		masterParameters = new HashMap<String, Object>();
	}
	
	public void setMasterReport(JasperReport masterReport) {
		this.masterReport = masterReport;
	}
	public JasperReport getMasterReport() {
		return masterReport;
	}
	public void setTemplateReports(List<TemplateJasperVO> templateReports) {
		this.templateReports = templateReports;
	}
	public List<TemplateJasperVO> getTemplateReports() {
		return templateReports;
	}

	public void setMasterParameters(Map<String, Object> masterParameters) {
		this.masterParameters = masterParameters;
	}

	public Map<String, Object> getMasterParameters() {
		return masterParameters;
	}

}
