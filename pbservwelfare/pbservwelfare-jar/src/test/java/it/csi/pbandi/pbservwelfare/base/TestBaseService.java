/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.base;

import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;

@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"})
@PropertySource("classpath*:src/test/java/it/csi/pbandi/pbservwelfare/config/enviromentTest.properties")
public class TestBaseService extends BaseDAO {

//	System.out.println("TestBaseService START");
}
