/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo.GenericVO;

public class LessThanCondition<T extends GenericVO> extends
		AbstractLessCondition<T> {

	public LessThanCondition(T valueObject) {
		super(valueObject, false);
	}
}
