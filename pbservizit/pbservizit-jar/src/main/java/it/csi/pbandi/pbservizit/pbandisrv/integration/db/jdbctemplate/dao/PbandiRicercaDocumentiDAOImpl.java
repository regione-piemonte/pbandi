/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionedocumentazione.DocumentoIndexProgettoBeneficiarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiRicercaDocumentiDAOImpl extends PbandiDAO {
	
	public PbandiRicercaDocumentiDAOImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub//PK
	}

	public List<DocumentoIndexProgettoBeneficiarioVO> findDocumenti(DocumentoIndexProgettoBeneficiarioVO filtro,
			Date dataDal,Date dataAl,
			String docInFirma, String docInviati
			){
			List<DocumentoIndexProgettoBeneficiarioVO> result = null;
			
			MapSqlParameterSource params = new MapSqlParameterSource();
		 
			
			List<StringBuilder> conditionList = new ArrayList<StringBuilder>();
			
			
			if(!ObjectUtil.isEmpty(filtro.getDescBreveTipoAnagrafica())){
				if(filtro.getDescBreveTipoAnagrafica().equalsIgnoreCase("BEN-MASTER"))
					conditionList.add((new StringBuilder()).append("DESC_BREVE_TIPO_ANAGRAFICA in ('BEN-MASTER','PERSONA-FISICA') "));
				else
					conditionList.add((new StringBuilder()).append("DESC_BREVE_TIPO_ANAGRAFICA=:descBreveTipoAnagrafica"));
				params.addValue("descBreveTipoAnagrafica",filtro.getDescBreveTipoAnagrafica(),Types.VARCHAR);
			}
			if(filtro.getIdTipoDocumentoIndex()!=null){
				conditionList.add((new StringBuilder()).append("ID_TIPO_DOCUMENTO_INDEX=:idTipoDocumentoIndex"));
				params.addValue("idTipoDocumentoIndex",filtro.getIdTipoDocumentoIndex(),Types.BIGINT);
			}
			
			if(filtro.getIdProgetto()!=null){
				// +Green: inizio.
				BigDecimal idProgetto2 = getIdProgettoPiuGreenAssociato(filtro.getIdProgetto());
				if (idProgetto2 != null) {
					// Caso +Green: aggiungo anche il progetto associato.
					conditionList.add((new StringBuilder()).append("(ID_PROGETTO=:idProgetto OR ID_PROGETTO=:idProgetto2)"));
					params.addValue("idProgetto",filtro.getIdProgetto(),Types.BIGINT);
					params.addValue("idProgetto2",idProgetto2,Types.BIGINT);
				} else {
					// Caso normale non +Green.
					conditionList.add((new StringBuilder()).append("ID_PROGETTO=:idProgetto"));
					params.addValue("idProgetto",filtro.getIdProgetto(),Types.BIGINT);
				}
				// +Green: fine.
			}
			
			if(filtro.getIdSoggetto()!=null){
				conditionList.add((new StringBuilder()).append("ID_SOGGETTO=:idSoggetto"));
				params.addValue("idSoggetto",filtro.getIdSoggetto(),Types.BIGINT);				
			}
			if(filtro.getIdSoggettoBeneficiario()!=null){
				conditionList.add((new StringBuilder()).append("ID_SOGGETTO_BENEFICIARIO=:idSoggettoBeneficiario"));
				params.addValue("idSoggettoBeneficiario",filtro.getIdSoggettoBeneficiario(),Types.BIGINT);
			}
			if(filtro.getProgrBandoLineaIntervento()!=null){
				conditionList.add((new StringBuilder()).append("PROGR_BANDO_LINEA_INTERVENTO=:progrBandoLineaIntervento"));
				params.addValue("progrBandoLineaIntervento",filtro.getProgrBandoLineaIntervento(),Types.BIGINT);
			}
			if(docInFirma!=null && docInviati!=null 
					&& docInFirma.equalsIgnoreCase("true")
					&& docInviati.equalsIgnoreCase("true")){
				conditionList.add((new StringBuilder()).append("FLAG_FIRMABILE='S'"));
				conditionList.add((new StringBuilder()).append("FLAG_REGOLA_DEMAT='S'"));
				conditionList.add((new StringBuilder()).append("(FLAG_FIRMA_CARTACEA IS NULL OR FLAG_FIRMA_CARTACEA='N')"));
				params.addValue("idTipoDocumentoIndex",filtro.getIdTipoDocumentoIndex(),Types.BIGINT);
			}else if(docInFirma!=null && docInFirma.equalsIgnoreCase("true")){
				conditionList.add((new StringBuilder()).append("ID_STATO_DOCUMENTO IN (SELECT ID_STATO_DOCUMENTO FROM PBANDI_D_STATO_DOCUMENTO_INDEX WHERE DESC_BREVE IN('GENERATO','ACQUISITO','VALIDATO','NON-VALIDATO') )"));
				conditionList.add((new StringBuilder()).append("FLAG_FIRMABILE='S'"));
				conditionList.add((new StringBuilder()).append("FLAG_REGOLA_DEMAT='S'"));
				conditionList.add((new StringBuilder()).append("(FLAG_FIRMA_CARTACEA IS NULL OR FLAG_FIRMA_CARTACEA='N')"));
				params.addValue("idTipoDocumentoIndex",filtro.getIdTipoDocumentoIndex(),Types.BIGINT);
			}else if(docInviati!=null && docInviati.equalsIgnoreCase("true")){
				conditionList.add((new StringBuilder()).append("ID_STATO_DOCUMENTO IN (SELECT ID_STATO_DOCUMENTO FROM PBANDI_D_STATO_DOCUMENTO_INDEX WHERE DESC_BREVE IN('INVIATO','CLASSIFICATO','PROTOCOLLATO') )"));
				params.addValue("idTipoDocumentoIndex",filtro.getIdTipoDocumentoIndex(),Types.BIGINT);
			}
	
			StringBuilder sqlSelect = new StringBuilder("SELECT DISTINCT ");
			sqlSelect.append("ID_DOCUMENTO_INDEX as idDocumentoIndex ,")
					 .append("ID_ENTITA as idEntita,")
					 .append("ID_TARGET as idTarget,")
			 		 .append("ID_PROGETTO as idProgetto,")
			 		 .append("DESC_BREVE_TIPO_DOC_INDEX as descBreveTipoDocIndex,")
			 		 .append("DESC_STATO_DOCUMENTO_INDEX as descStatoDocumentoIndex,")
//			 		 .append("BENEFICIARIO as beneficiario ,")
					 .append("NOTE_DOCUMENTO_INDEX as noteDocumentoIndex ,")
					 .append("NOME_FILE as nomeFile ,")
					 .append("CODICE_VISUALIZZATO as codiceVisualizzato ,")
					 .append("DESC_TIPO_DOC_INDEX as descTipoDocIndex,")
					 .append("DT_VERIFICA_FIRMA as dtVerificaFirma,")
					 .append("DT_MARCA_TEMPORALE as dtMarcaTemporale,")
					 .append("FLAG_FIRMA_CARTACEA as flagFirmaCartacea,")
					 .append("FLAG_TRASM_DEST as flagTrasmDest,")
					 .append("FLAG_REGOLA_DEMAT as flagRegolaDemat,")
					 .append("FLAG_FIRMABILE as flagFirmabile,")
					 .append("ID_STATO_DOCUMENTO as idStatoDocumento,")
					 .append("NUM_PROTOCOLLO as protocollo,") 
					 .append("DT_INSERIMENTO_INDEX as dtInserimentoIndex,")
					 .append("MESSAGGIO as descErrore,")
					 .append("ID_CATEG_ANAGRAFICA_MITT as idCategAnagraficaMitt,")
					 .append("DESC_CATEG_ANAGRAFICA_MITT as descCategAnagraficaMitt,");
			
			if(filtro.getDescBreveTipoAnagrafica().equalsIgnoreCase(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OPERATORE_ISTRUTTORIA_PROGETTI)){
				sqlSelect.append("( SELECT eg.denominazione_ente_giuridico " +
						"FROM pbandi_r_soggetto_progetto sp, pbandi_t_ente_giuridico eg, pbandi_t_progetto p " +
						"WHERE p.id_progetto = sp.id_progetto " +
						"AND sp.id_ente_giuridico = eg.id_ente_giuridico " +
						"AND sp.id_tipo_anagrafica = 1 " +
						"AND sp.id_tipo_beneficiario != 4 " +
						"AND p.id_progetto = doc.id_progetto) AS  beneficiario ");
			}else
				sqlSelect.append("BENEFICIARIO as beneficiario ");
					 
		    sqlSelect.append(" FROM pbandi_v_doc_index doc");
			
			
			if (!conditionList.isEmpty()) {
				sqlSelect.append(" WHERE ");
				for (int i = 0; i < conditionList.size(); i++) {
					if (i > 0) {
						sqlSelect.append(" AND");
					}
					sqlSelect.append(" ").append(conditionList.get(i));
				}
			}
			
			if(dataDal!=null){
				
				sqlSelect.append(" AND trunc(DT_INSERIMENTO_INDEX)  >= trunc(:dataDal)");
				params.addValue("dataDal",dataDal,Types.DATE);
			}
			if(dataAl!=null){
				
				sqlSelect.append(" AND trunc(DT_INSERIMENTO_INDEX) <= trunc(:dataAl) ");
				params.addValue("dataAl",dataAl,Types.DATE);
			}
			
			
			result = query(sqlSelect.toString(), params, new DocumentoIndexProgettoBeneficiarioVORowMapper());
		 
		return result;
	}

	private class DocumentoIndexProgettoBeneficiarioVORowMapper implements RowMapper<DocumentoIndexProgettoBeneficiarioVO>{

		public DocumentoIndexProgettoBeneficiarioVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			DocumentoIndexProgettoBeneficiarioVO vo=(DocumentoIndexProgettoBeneficiarioVO) beanMapper.toBean(rs,DocumentoIndexProgettoBeneficiarioVO.class);
			return vo;
		}
	}

	public String getCodStatoDoc(Long idDocumentoIndex) {
		StringBuilder sqlSelect = new StringBuilder("select desc_breve 	from PBANDI_T_DOCUMENTO_INDEX");
		sqlSelect.append(" join PBANDI_D_STATO_DOCUMENTO_INDEX using (ID_STATO_DOCUMENTO )")
		         .append(" where ID_DOCUMENTO_INDEX=:idDocumentoIndex");
		MapSqlParameterSource params = new MapSqlParameterSource();			
		params.addValue("idDocumentoIndex", idDocumentoIndex, Types.NUMERIC);
		String codStatoDoc = queryForString(sqlSelect.toString(), params);
		return codStatoDoc;
	}
	
	// +Green inizio.
	
	@Autowired
	private GenericDAO genericDAO;
	 
	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	// Se l'id in input � un progetto +Green, restituisce l'id del progetto associato; null altrimenti.
	private BigDecimal getIdProgettoPiuGreenAssociato(BigDecimal idProgetto) {
		// Se � un progetto a finanziamento, resituisce l'id del progetto a contributo.
		PbandiTProgettoVO vo1 = new PbandiTProgettoVO();  
		vo1.setIdProgettoFinanz(idProgetto);
		vo1 = genericDAO.findSingleOrNoneWhere(vo1);		
		if (vo1 != null)			
			return vo1.getIdProgetto();
		// Se � un progetto a contributo, resituisce l'id del progetto a finanziamento.
		PbandiTProgettoVO vo2 = new PbandiTProgettoVO();  
		vo2.setIdProgetto(idProgetto);
		vo2 = genericDAO.findSingleOrNoneWhere(vo2);
		if (vo2 != null && vo2.getIdProgettoFinanz() != null)
			return vo2.getIdProgettoFinanz();
		// Non � un progetto +Green.
		return null;
	}
	// +Green fine.

}
