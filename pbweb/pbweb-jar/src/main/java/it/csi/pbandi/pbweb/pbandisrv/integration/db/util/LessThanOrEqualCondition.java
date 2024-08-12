package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class LessThanOrEqualCondition<T extends GenericVO> extends
		AbstractLessCondition<T> {

	public LessThanOrEqualCondition(T valueObject) {
		super(valueObject, true);
	}
}
