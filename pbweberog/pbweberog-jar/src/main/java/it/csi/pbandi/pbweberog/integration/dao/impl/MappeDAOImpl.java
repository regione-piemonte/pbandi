/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.ecogis.geeco_java_client.api.ConfigurationBuilder;
import it.csi.ecogis.geeco_java_client.build.AttributeSchemaFactory;
import it.csi.ecogis.geeco_java_client.build.ConfigurationFactory;
import it.csi.ecogis.geeco_java_client.dto.Configuration;
import it.csi.ecogis.geeco_java_client.dto.internal.Feature;
import it.csi.ecogis.geeco_java_client.dto.internal.Features;
import it.csi.ecogis.geeco_java_client.dto.internal.MultipointGeometry;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.AttributeSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.TextSchema;
import it.csi.ecogis.geeco_java_client.util.ConversionUtils;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweberog.integration.dao.MappeDAO;
import it.csi.pbandi.pbweberog.pbandisrv.dto.LinkMappaDTO;
import it.csi.pbandi.pbweberog.util.Constants;

@Component
public class MappeDAOImpl extends JdbcDaoSupport implements MappeDAO{
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private GenericDAO genericDAO;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public MappeDAOImpl(DataSource dataSource) throws Exception {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	@Override
	public LinkMappaDTO linkMappa() throws Exception {
		String prf = "[MappeDAOImpl::linkMappa]";
		LOG.info(prf + " BEGIN");
		//Boolean esito = false;
		LinkMappaDTO linkMappaDTO = new LinkMappaDTO();
		try {

			ConfigurationBuilder cf = new ConfigurationFactory();
			createAppInfo(cf);
			createStartupInfo(cf);
			createQuitInfo(cf);
			createEditingLayerWithFeature_12(cf);
			
			Configuration confDto = cf.build();
			
			
			String json = ConversionUtils.configurationBeanToJson(confDto);			
			LOG.info(prf+"Jason = "+json);
			
			linkMappaDTO.setJson(json);
			linkMappaDTO.setUrl("http://<da sostiure con il VH di esposizione API mappe>/ssagmf/setup-index");
			
		} catch (Exception e) {
			throw new Exception("Errore imprevisto", e);
		}
		finally {
			LOG.info(prf+" END");
		}
		return linkMappaDTO;
	}
	
	private void createAppInfo(ConfigurationBuilder cf) {
		
		String uuid = "RUNPR0lTX0dFRUNPX0dFRUNPX0RFTU9fTlVPVk8";
		String environment = "demo";
		
		cf = cf.createAppInfo(uuid, environment);
	}

	private void createStartupInfo(ConfigurationBuilder cf) {
		
		boolean readonly = false;
		int[] sessionEditableLayers = null;
		boolean showInputFeatures = true;
		boolean zoomEnabled = true;
		String authLevel = null;
		String authCommunity = null;
		boolean isHttps = false;
		boolean showLabelOnFeatures = true;
		String activeToolOnStartup = null;
		
		cf = cf.createStartupInfo(readonly, sessionEditableLayers, showInputFeatures, zoomEnabled, authLevel, authCommunity, isHttps, showLabelOnFeatures, activeToolOnStartup);
	}
	
	private void createQuitInfo(ConfigurationBuilder cf) {
		
		String quitUrl = "www.quit.url";
		
		cf = cf.createQuitInfo(quitUrl);
	}
	
	private void createEditingLayerWithFeature_12(ConfigurationBuilder cf) {
		
		String idLayer = "12";
		List<AttributeSchema> schema = new ArrayList<AttributeSchema>();
		Features features = this.createFeatures();
		HashMap<String, Object> defaultValues = null;
		String defaultStyles = "";
		boolean canInsertNewFeatures = true;
		boolean canDeleteFeatures = true;
		String alias = null;
		
		// NOTA: uno schema "identificativo" viene aggiunto di default.
				
		AttributeSchemaFactory asf = new AttributeSchemaFactory();
		//asf.addTextAttributeSchema(createTextSchemaIdentificativo());
		asf.addTextAttributeSchema(createTextSchemaNote());
		schema = asf.create();
		
		cf = cf.createEditingLayerWithFeatures(idLayer, schema, features, defaultValues, defaultStyles, canInsertNewFeatures, canDeleteFeatures, alias);
	}
	
	private TextSchema createTextSchemaIdentificativo() {
		
		String name = "Identificativo";
		String alias = "Identificativo dell'oggetto";
		boolean required = false;
		boolean readonly = true;
		String tooltip = "Identificativo dell'oggetto";
		
		return new TextSchema(name, alias, required, readonly, tooltip);
	}
	
	private TextSchema createTextSchemaNote() {
		
		String name = "Note";
		String alias = "Note dell'oggetto";
		boolean required = false;
		boolean readonly = false;
		String tooltip = "Note dell'oggetto";
		
		return new TextSchema(name, alias, required, readonly, tooltip);
	}
	
	private Features createFeatures() {
		
		Feature feature = createFeature_1();
		
		List<Feature> featuresList = new ArrayList<Feature>();
		featuresList.add(feature);
		
		Features features = new Features();
		features.setFeaturesList(featuresList); 
		
		return features;
	}
	
	private Feature createFeature_1() {
		
		List<BigDecimal> coordinata1 = new ArrayList<BigDecimal>();
		coordinata1.add(new BigDecimal("344727.0205"));
		coordinata1.add(new BigDecimal("4989144.027"));
		
		List<List<BigDecimal>> coordinate = new ArrayList<List<BigDecimal>>();
		coordinate.add(coordinata1);
		
		HashMap<String, Object> proprieta = new HashMap<String, Object>();
		proprieta.put("Identificativo", "oggetto passato a Geeco id. 1");
		proprieta.put("Note", "note oggetto passato a Geeco id. 1");
		  
		MultipointGeometry g1 = new MultipointGeometry();
		g1.setCoordinates(coordinate);
		
		Feature feature = new Feature();
		feature.setId(new Long(1));
		feature.setGeometry(g1);
		feature.setProperties(proprieta);

		return feature;
	}
	
}
