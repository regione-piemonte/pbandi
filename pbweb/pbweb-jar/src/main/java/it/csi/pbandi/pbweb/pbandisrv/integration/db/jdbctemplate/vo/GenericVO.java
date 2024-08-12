package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.util.List;

public abstract class GenericVO extends it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO {
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isPKValid() {
		return false;
	}
	
	
	@Override
	public List getPK() {
		return null;
	}
	
}
