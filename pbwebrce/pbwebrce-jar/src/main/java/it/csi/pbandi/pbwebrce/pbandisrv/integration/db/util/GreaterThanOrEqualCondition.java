/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;

public class GreaterThanOrEqualCondition<T extends GenericVO> extends
		AbstractComparisonCondition<T> {

	public GreaterThanOrEqualCondition(T valueObject) {
		super(valueObject, true);
	}
}
