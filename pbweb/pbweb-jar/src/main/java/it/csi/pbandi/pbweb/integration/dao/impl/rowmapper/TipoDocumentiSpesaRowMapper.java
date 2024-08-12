package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;

public class TipoDocumentiSpesaRowMapper implements RowMapper<TipoDocumentiSpesaVO>{
	
	@Override
	public TipoDocumentiSpesaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TipoDocumentiSpesaVO cm = new TipoDocumentiSpesaVO();
		
		cm.setProgrBandoLineaIntervento(rs.getBigDecimal("progr_bando_linea_intervento"));
		cm.setIdTipoDocumentoSpesa(rs.getBigDecimal("id_tipo_documento_spesa"));
		cm.setDescBreveTipoDocSpesa(rs.getString("desc_breve_tipo_doc_spesa"));
		cm.setDescTipoDocumentoSpesa(rs.getString("desc_tipo_documento_spesa"));
	
		return cm;
	}
}
