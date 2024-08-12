package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class OwnerVO extends GenericVO {

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	private String owner;
}
