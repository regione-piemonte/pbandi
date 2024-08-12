package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class GreaterThanCondition<T extends GenericVO> extends
		AbstractComparisonCondition<T> {

	public GreaterThanCondition(T valueObject) {
		super(valueObject, false);
	}
}
