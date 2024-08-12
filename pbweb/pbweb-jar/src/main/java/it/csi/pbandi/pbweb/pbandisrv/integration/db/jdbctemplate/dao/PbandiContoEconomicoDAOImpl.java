package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione.VoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class PbandiContoEconomicoDAOImpl extends PbandiDAO {
	
	public PbandiContoEconomicoDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	private class VocediSpesaRowMapper implements RowMapper<VoceDiSpesaVO> {

		public VoceDiSpesaVO mapRow(ResultSet rs, int row) throws SQLException {
			BeanMapper beanMapper = new BeanMapper();
			VoceDiSpesaVO vo = (VoceDiSpesaVO) beanMapper.toBean(rs,
					VoceDiSpesaVO.class);
			return vo;
		}
	}

	public List<VoceDiSpesaVO> findVociImporti(Long idDichiarazione,
			Long idDocumento, Long idProgetto) {
		getLogger().begin();

		java.util.List<VoceDiSpesaVO> result = new ArrayList<VoceDiSpesaVO>();
		try {

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("idDichiarazione", idDichiarazione, Types.BIGINT);
			params.addValue("idDocumento", idDocumento, Types.BIGINT);
			params.addValue("idProgetto", idProgetto, Types.BIGINT);
			StringBuilder sqlSelect = new StringBuilder("");
			/*
			 * -- pbandi_ut svil -- idDoc 2442 -- idDich 167 -- idProg 210
			 */
			sqlSelect
					.append(" select ")
					.append(
							" righi.ID_RIGO_CONTO_ECONOMICO idRigoContoEconomico, ")
					.append(" righi.descvocespesa , ")
					.append(
							" righi.IMPORTO_AMMESSO_FINANZIAMENTO importoAmmesso,")
					.append(" righiValidato.importoValidato ")
					.append(" from ")
					.append(" (select distinct ")
					.append(" rce.ID_RIGO_CONTO_ECONOMICO ,  ")
					.append(
							" nvl2(vdsP.DESC_VOCE_DI_SPESA, vdsP.DESC_VOCE_DI_SPESA || ': ', '') || vds.DESC_VOCE_DI_SPESA  descvocespesa,  ")
					.append(" rce.IMPORTO_AMMESSO_FINANZIAMENTO  ")
					.append(" from ")
					.append(" pbandi_t_progetto pro, ")
					.append(" pbandi_t_domanda dom, ")
					.append(" pbandi_t_conto_economico ce,  ")
					.append(" pbandi_t_rigo_conto_economico rce,  ")
					.append(" pbandi_d_voce_di_spesa vds,  ")
					.append(" pbandi_d_voce_di_spesa vdsP,  ")
					.append(" (select ")
					.append(" pds.ID_PAGAMENTO, ")
					.append(
							" first_value(ds.ID_DICHIARAZIONE_SPESA) over (partition by pds.ID_PAGAMENTO order by ds.DT_DICHIARAZIONE desc, ds.ID_DICHIARAZIONE_SPESA desc) as ID_DICHIARAZIONE_SPESA ")
					.append(" from ")
					.append(" PBANDI_R_PAGAMENTO_DICH_SPESA pds, ")
					.append(" PBANDI_T_DICHIARAZIONE_SPESA ds ")
					.append(" where ")
					.append(
							" pds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA) pagDicOwn, ")
					.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQtp, ")
					.append(" PBANDI_R_PAGAMENTO_DOC_SPESA pagDoc, ")
					.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA qtp ")
					.append(" where ")
					.append(" pagDoc.ID_DOCUMENTO_DI_SPESA = :idDocumento ")
					.append(" and pagDoc.ID_PAGAMENTO = pagQtp.ID_PAGAMENTO ")
					.append(
							" and pagDoc.ID_PAGAMENTO = pagDicOwn.ID_PAGAMENTO ")
					.append(
							" and pagDicOwn.ID_DICHIARAZIONE_SPESA = :idDichiarazione ")
					.append(
							" and qtp.ID_QUOTA_PARTE_DOC_SPESA = pagQtp.ID_QUOTA_PARTE_DOC_SPESA ")
					.append(" and pro.ID_PROGETTO = :idProgetto  ")
					.append(" and pro.ID_DOMANDA = dom.ID_DOMANDA ")
					.append(" and dom.ID_DOMANDA = ce.ID_DOMANDA ")
					.append(" and ce.DT_FINE_VALIDITA is null ")
					.append(
							" and ce.ID_CONTO_ECONOMICO = rce.ID_CONTO_ECONOMICO  ")
					.append(
							" and rce.ID_VOCE_DI_SPESA = vds.ID_VOCE_DI_SPESA  ")
					.append(
							" and vds.ID_VOCE_DI_SPESA_PADRE = vdsP.ID_VOCE_DI_SPESA(+) ")
					.append(" and rce.DT_FINE_VALIDITA is null ")
					.append(
							" and rce.ID_RIGO_CONTO_ECONOMICO = qtp.ID_RIGO_CONTO_ECONOMICO ")
					.append(" ) righi, ")
					.append(" ( ")
					.append(" select ")
					.append(" qtpOther.ID_RIGO_CONTO_ECONOMICO, ")
					.append(
							" sum(pagQtpOther.IMPORTO_VALIDATO) importoVALIDATO ")
					.append(" from ")
					.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQtpOther, ")
					.append(" PBANDI_T_QUOTA_PARTE_DOC_SPESA qtpOther ")
					.append(" where ")
					.append(
							" pagQtpOther.ID_QUOTA_PARTE_DOC_SPESA = qtpOther.ID_QUOTA_PARTE_DOC_SPESA ")
					.append(" and pagQtpOther.ID_PAGAMENTO not in ")
					.append(" ( ")
					.append(" select distinct ")
					.append(" pagDoc.ID_PAGAMENTO ")
					.append(" from ")
					.append(" (select ")
					.append(" pds.ID_PAGAMENTO, ")
					.append(
							"   first_value(ds.ID_DICHIARAZIONE_SPESA) over (partition by pds.ID_PAGAMENTO  ")
					.append(
							"   order by ds.DT_DICHIARAZIONE desc, ds.ID_DICHIARAZIONE_SPESA desc) as ID_DICHIARAZIONE_SPESA ")
					.append("  from ")
					.append(" PBANDI_R_PAGAMENTO_DICH_SPESA pds, ")
					.append("   PBANDI_T_DICHIARAZIONE_SPESA ds ")
					.append("  where ")
					.append(
							"  pds.ID_DICHIARAZIONE_SPESA = ds.ID_DICHIARAZIONE_SPESA) pagDicOwn, ")
					.append(" PBANDI_R_PAG_QUOT_PARTE_DOC_SP pagQtp, ")
					.append(" PBANDI_R_PAGAMENTO_DOC_SPESA pagDoc ")
					.append(" where ")
					.append(" pagDoc.ID_DOCUMENTO_DI_SPESA = :idDocumento ")
					.append(
							" and pagDicOwn.ID_DICHIARAZIONE_SPESA = :idDichiarazione ")
					.append(" and pagDoc.ID_PAGAMENTO = pagQtp.ID_PAGAMENTO ")
					.append(
							" and pagDoc.ID_PAGAMENTO = pagDicOwn.ID_PAGAMENTO ")
					.append(" ) ")
					.append(" group by ")
					.append(" qtpOther.ID_RIGO_CONTO_ECONOMICO ")
					.append(" ) righiValidato  ")
					.append(" where ")
					.append(
							" righi.ID_RIGO_CONTO_ECONOMICO = righiValidato.ID_RIGO_CONTO_ECONOMICO(+) ")
					.append(" order by 2 ");
			result = query(sqlSelect.toString(), params,
					new VocediSpesaRowMapper());
			getLogger().debug(" voci di spesa con importi: " + result.size());

		} finally {
			getLogger().end();
		}

		return result;
	}
}
