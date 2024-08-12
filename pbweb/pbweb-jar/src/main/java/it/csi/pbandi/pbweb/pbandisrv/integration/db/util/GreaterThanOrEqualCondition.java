package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class GreaterThanOrEqualCondition<T extends GenericVO> extends
		AbstractComparisonCondition<T> {

	public GreaterThanOrEqualCondition(T valueObject) {
		super(valueObject, true);
	}
}
