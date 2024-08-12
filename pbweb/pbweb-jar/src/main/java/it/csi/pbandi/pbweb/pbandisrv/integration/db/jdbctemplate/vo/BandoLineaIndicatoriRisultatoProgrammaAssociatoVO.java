package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinIndRisProVO;

public class BandoLineaIndicatoriRisultatoProgrammaAssociatoVO extends PbandiRBandoLinIndRisProVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7297161546238987178L;
	private String descIndRisultatoProgramma;
	private String codIgrueT20;

	public void setDescIndRisultatoProgramma(String descIndRisultatoProgramma) {
		this.descIndRisultatoProgramma = descIndRisultatoProgramma;
	}
	public String getDescIndRisultatoProgramma() {
		return descIndRisultatoProgramma;
	}
	public void setCodIgrueT20(String codIgrueT20) {
		this.codIgrueT20 = codIgrueT20;
	}
	public String getCodIgrueT20() {
		return codIgrueT20;
	}
	
}
