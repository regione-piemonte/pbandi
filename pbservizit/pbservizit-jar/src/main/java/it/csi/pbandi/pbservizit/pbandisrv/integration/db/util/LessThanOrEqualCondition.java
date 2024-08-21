/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.util;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

public class LessThanOrEqualCondition<T extends GenericVO> extends
		AbstractLessCondition<T> {

	public LessThanOrEqualCondition(T valueObject) {
		super(valueObject, true);
	}
}
