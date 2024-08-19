/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RigoContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.dto.contoeconomico.ContoEconomicoDTO;
import it.csi.pbandi.pbweberog.integration.dao.ContoEconomicoDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class ContoEconomicoDAOImpl extends JdbcDaoSupport implements ContoEconomicoDAO{
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private ContoEconomicoManager contoEconomicoManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public ContoEconomicoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}

	static final private Map<String, String> mapVisualizzazioneMaster = new HashMap<String, String>();
	{
		mapVisualizzazioneMaster.put("importoAmmesso", "importoSpesaAmmessa");
		mapVisualizzazioneMaster.put("importoQuietanzato",
				"importoSpesaQuietanziata");
		mapVisualizzazioneMaster.put("importoRendicontato",
				"importoSpesaRendicontata");
		mapVisualizzazioneMaster.put("importoValidato",
				"importoSpesaValidataTotale");
		mapVisualizzazioneMaster.put("descVoceDiSpesa", "label");
		mapVisualizzazioneMaster.put("idVoceDiSpesa", "idVoce");
		mapVisualizzazioneMaster.put("idContoEconomico", "idContoEconomico");
		mapVisualizzazioneMaster.put("idVoceDiSpesaPadre", "idVocePadre");
		// Cultura
		mapVisualizzazioneMaster.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");
		mapVisualizzazioneMaster.put("percSpGenFunz", "percSpGenFunz");
	}
	
	@Override
	@Transactional
	public ContoEconomicoDTO findDatiContoEconomico(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[ContoEconomicoDAOImpl::findDatiContoEconomico]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, idProgetto);
			
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoMaster = contoEconomicoManager
					.findContoEconomicoMasterPotato(new BigDecimal(idProgetto));

			ContoEconomicoDTO contoEconomicoDTO = convertiContoEconomicoMaster(contoEconomicoMaster);

			return contoEconomicoDTO;
			
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	private ContoEconomicoDTO convertiContoEconomicoMaster(RigoContoEconomicoDTO rigo) {
		String prf = "[ContoEconomicoDAOImpl::convertiContoEconomicoMaster]";
		LOG.info(prf + " START");
		
		ContoEconomicoDTO contoEconomicoDTO;

		contoEconomicoDTO = beanUtil.transform(rigo,
				ContoEconomicoDTO.class, mapVisualizzazioneMaster);

		List<RigoContoEconomicoDTO> figli = rigo.getFigli();

		ContoEconomicoDTO[] figliTrasformati = null;
		if (figli != null && figli.size() > 0) {
			List<ContoEconomicoDTO> listaTrasformati = new ArrayList<ContoEconomicoDTO>();
			for (RigoContoEconomicoDTO figlioCorrente : figli) {
				ContoEconomicoDTO figlioTrasformato = convertiContoEconomicoMaster(figlioCorrente);

				listaTrasformati.add(figlioTrasformato);
			}
			figliTrasformati = listaTrasformati
					.toArray(new ContoEconomicoDTO[listaTrasformati.size()]);
		}
		contoEconomicoDTO.setFigli(figliTrasformati);

		contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigo.getImportoQuietanzato(), rigo.getImportoAmmesso())));
		contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil.toDouble(NumberUtil.percentage(rigo.getImportoValidato(), rigo.getImportoAmmesso())));
		LOG.info(prf + " END");
		return contoEconomicoDTO;
	}
	
	
}
