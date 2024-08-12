package it.csi.pbandi.pbweb.integration.dao.impl.rowmapper;

import it.csi.pbandi.pbweb.dto.IntegrazioneRendicontazioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IntegrazioneRendicontazioneRowMapper implements RowMapper<IntegrazioneRendicontazioneDTO>{
	
	@Override
	public IntegrazioneRendicontazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		IntegrazioneRendicontazioneDTO ir = new IntegrazioneRendicontazioneDTO();
		
		ir.setnDichiarazioneSpesa(rs.getLong("idDichiarazioneSpesa"));
		ir.setDataRichiesta(rs.getDate("dataRichiesta").toString());
		Date date = rs.getDate("dataNotifica");
		int numeroGiorni = rs.getInt("numGiorniScadenza");
		if(date != null) {
			ir.setDataNotifica(date.toString());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, numeroGiorni);
			date = calendar.getTime();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ir.setDataScadenza(dateFormat.format(date));
		}
		ir.setnGgScadenza(Integer.toString(numeroGiorni));
		ir.setDataInvio(rs.getString("dataInvio"));
		ir.setIdStatoRichiesta(rs.getLong("idStatoRichiesta"));
		ir.setStatoRichiesta(rs.getString("descBreveStatoRichiesta"));
		ir.setLongStatoRichiesta(rs.getString("descStatoRichiesta"));
		ir.setIdIntegrazioneSpesa(rs.getLong("idIntegrazioneSpesa"));
		ir.setAllegatiInseriti(rs.getLong("allegatiInseriti") == 1);

		return ir;
	}
}
