package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.integration.vo.PbandiDTipoSoggettoVO;

public class PbandiDTipoSoggettoRowMapper implements RowMapper<PbandiDTipoSoggettoVO> {

	@Override
	public PbandiDTipoSoggettoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		PbandiDTipoSoggettoVO cm = new PbandiDTipoSoggettoVO();		
		cm.setIdTipoSoggetto(rs.getBigDecimal("ID_TIPO_SOGGETTO"));
		cm.setDescTipoSoggetto(rs.getString("DESC_TIPO_SOGGETTO"));
		cm.setDescBreveTipoSoggetto(rs.getString("DESC_BREVE_TIPO_SOGGETTO"));
		cm.setDtInizioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
		cm.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
		return cm;
	}
	
}
