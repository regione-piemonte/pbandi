/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebfin.dto.beneficiario;

import java.io.Serializable;
import java.util.Arrays;

public class ProgettiBeneficiario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private  Long[] idImpegno;
    private Long[] progetti;
    
	public Long[] getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(Long[] idImpegno) {
		this.idImpegno = idImpegno;
	}
	public Long[] getProgetti() {
		return progetti;
	}
	public void setProgetti(Long[] progetti) {
		this.progetti = progetti;
	}
	
	@Override
	public String toString() {
		return "Beneficiario [idImpegno=" + Arrays.toString(idImpegno) + ", progetti=" + Arrays.toString(progetti)
				+ "]";
	}
        

}
