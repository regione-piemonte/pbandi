/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DeclaratoriaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class AnteprimaDichiarazioneDiSpesaCulturaRequest extends AnteprimaDichiarazioneDiSpesaRequest {

	private DeclaratoriaDTO allegatiCultura;

	public DeclaratoriaDTO getAllegatiCultura() {
		return allegatiCultura;
	}

	public void setAllegatiCultura(DeclaratoriaDTO allegatiCultura) {
		this.allegatiCultura = allegatiCultura;
	}

	@Override
	public String toString() {
		return "AnteprimaDichiarazioneDiSpesaCulturaRequest{" +
				"allegatiCultura=" + allegatiCultura +
				"} " + super.toString();
	}
}
