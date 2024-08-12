package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;

import java.math.BigDecimal;


public class PbandiTAffidamentoChecklistVO extends GenericVO{
	
	private static final BigDecimal ID_SOPRA_SOGLIA = new BigDecimal(1);
	private static final BigDecimal ID_SOTTO_SOGLIA = new BigDecimal(2);
	private static final BigDecimal ID_SOGLIA_ND = new BigDecimal(0);
	
	private BigDecimal idAffidamentoChecklist;
	private BigDecimal idNorma;
	private BigDecimal idTipoAffidamento;
	private BigDecimal idTipoAppalto;
	private BigDecimal idTipologiaAggiudicaz;
	private String sopraSoglia;
	private BigDecimal idModelloCd;
	private BigDecimal idModelloCl;
	private String rifChecklistAffidamenti;
	private BigDecimal idLineaDiIntervento;
	
	public PbandiTAffidamentoChecklistVO(){}

	public PbandiTAffidamentoChecklistVO(BigDecimal idAffidamentoChecklist) {
		super();
		this.idAffidamentoChecklist = idAffidamentoChecklist;
	}
	
	
	public PbandiTAffidamentoChecklistVO(BigDecimal idNorma,
			BigDecimal idTipoAffidamento, BigDecimal idTipoAppalto,
			BigDecimal idTipologiaAggiudicaz, String sopraSoglia) {
		super();
		this.idNorma = idNorma;
		this.idTipoAffidamento = idTipoAffidamento;
		this.idTipoAppalto = idTipoAppalto;
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
		this.sopraSoglia = sopraSoglia;
	}

	public PbandiTAffidamentoChecklistVO(BigDecimal idAffidamentoChecklist,
			BigDecimal idNorma, BigDecimal idTipoAffidamento,
			BigDecimal idTipoAppalto, BigDecimal idTipologiaAggiudicaz,
			String sopraSoglia, BigDecimal idModelloCd,
			BigDecimal idModelloCl, String rifChecklistAffidamenti,
			BigDecimal idLineaDiIntervento) {
		super();
		this.idAffidamentoChecklist = idAffidamentoChecklist;
		this.idNorma = idNorma;
		this.idTipoAffidamento = idTipoAffidamento;
		this.idTipoAppalto = idTipoAppalto;
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
		this.sopraSoglia = sopraSoglia;
		this.idModelloCd = idModelloCd;
		this.idModelloCl = idModelloCl;
		this.rifChecklistAffidamenti = rifChecklistAffidamenti;
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	

	public BigDecimal getIdAffidamentoChecklist() {
		return idAffidamentoChecklist;
	}

	public void setIdAffidamentoChecklist(BigDecimal idAffidamentoChecklist) {
		this.idAffidamentoChecklist = idAffidamentoChecklist;
	}

	public BigDecimal getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}

	public BigDecimal getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(BigDecimal idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public BigDecimal getIdTipoAppalto() {
		return idTipoAppalto;
	}

	public void setIdTipoAppalto(BigDecimal idTipoAppalto) {
		this.idTipoAppalto = idTipoAppalto;
	}

	public BigDecimal getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}

	public void setIdTipologiaAggiudicaz(BigDecimal idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}

	public String getSopraSoglia() {
		return sopraSoglia;
	}

	public void setSopraSoglia(String sopraSoglia) {
		this.sopraSoglia = sopraSoglia;
	}

	public BigDecimal getIdModelloCd() {
		return idModelloCd;
	}

	public void setIdModelloCd(BigDecimal idModelloCd) {
		this.idModelloCd = idModelloCd;
	}

	public BigDecimal getIdModelloCl() {
		return idModelloCl;
	}

	public void setIdModelloCl(BigDecimal idModelloCl) {
		this.idModelloCl = idModelloCl;
	}

	public String getRifChecklistAffidamenti() {
		return rifChecklistAffidamenti;
	}

	public void setRifChecklistAffidamenti(String rifChecklistAffidamenti) {
		this.rifChecklistAffidamenti = rifChecklistAffidamenti;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	@Override
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAffidamentoChecklist");
		
	    return pk;
	}

	@Override
	public boolean isPKValid() {
		return idAffidamentoChecklist != null;
	}
	
	public boolean isValid(){
		return isPKValid() && idNorma != null && sopraSoglia != null && idTipoAffidamento != null && idTipoAppalto != null && idTipologiaAggiudicaz != null;
	}


	@Override
	public String toString() {
		return "PbandiTAffidamentoChecklistVO [idAffidamentoChecklist="
				+ idAffidamentoChecklist + ", idModelloCd=" + idModelloCd
				+ ", idModelloCl=" + idModelloCl + ", idNorma=" + idNorma
				+ ", idSoglia=" + sopraSoglia + ", idTipoAffidamento="
				+ idTipoAffidamento + ", idTipoAppalto=" + idTipoAppalto
				+ ", idTipologiaAggiudicaz=" + idTipologiaAggiudicaz
				+ ", rif_checklist_affidamento=" + rifChecklistAffidamenti
				+ ", idLineaDiIntervento=" + idLineaDiIntervento
				+ "]";
	}
	
//	public void setSoglia(String sopraSoglia){
//		if(sopraSoglia != null && !StringUtil.isEmpty(sopraSoglia)){
//			if(sopraSoglia.equals("S"))
//				setIdSoglia(ID_SOPRA_SOGLIA);
//			else
//				setIdSoglia(ID_SOTTO_SOGLIA);
//		}
//		else
//			setIdSoglia(ID_SOGLIA_ND);
//	}

}
