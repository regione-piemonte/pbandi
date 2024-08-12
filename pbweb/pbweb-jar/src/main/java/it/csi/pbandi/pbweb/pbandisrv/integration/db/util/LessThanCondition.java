package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class LessThanCondition<T extends GenericVO> extends
		AbstractLessCondition<T> {

	public LessThanCondition(T valueObject) {
		super(valueObject, false);
	}
}
