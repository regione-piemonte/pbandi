/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class GreaterThanCondition<T extends GenericVO> extends
		AbstractComparisonCondition<T> {

	public GreaterThanCondition(T valueObject) {
		super(valueObject, false);
	}
}
