package it.csi.pbandi.pbweb.business.manager;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.FileSqlUtil;

public class InizializzazioneManager extends JdbcDaoSupport {
	
//	public static final String ROOT_FILE_SYSTEM = "/pbstorage_onlinePK";
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);	
	
	@Autowired
	public InizializzazioneManager(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	
	public DatiProgettoInizializzazioneDTO completaDatiProgetto(Long idPrj) {
		String prf = "[InizializzazioneManager::completaDatiProgetto] ";
		LOG.info(prf + " BEGIN");		
		LOG.info(prf + " idPrj="+idPrj);
		
		DatiProgettoInizializzazioneDTO dto = null;
		try {
			if (idPrj != null && idPrj.intValue() != 0) {				
				String sql = fileSqlUtil.getQuery("ProgettoBandoLineaVO.sql"); 
				LOG.info(prf + "\n" + sql+" con id_progetto = "+idPrj);
				ProgettoBandoLineaVO progettoBandoLineaVO = (ProgettoBandoLineaVO) getJdbcTemplate().queryForObject(sql.toString(), new Object[] {idPrj}, new BeanRowMapper(ProgettoBandoLineaVO.class));
				if (progettoBandoLineaVO != null) {
					dto = new DatiProgettoInizializzazioneDTO();
					dto.setCodiceVisualizzato(progettoBandoLineaVO.getCodiceVisualizzato());
					dto.setIdProcesso(progettoBandoLineaVO.getIdProcesso());
					if (progettoBandoLineaVO.getIdTipoOperazione() != null)
						dto.setIdTipoOperazione(progettoBandoLineaVO.getIdTipoOperazione().longValue());
				}
			}			
		} catch (Exception e) {
			LOG.error(prf + " Exception "+e.getMessage());
		}
		
		LOG.info(prf + " END");
		return dto;
	}
	

}
