/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.base;

import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:/applicationContextMonitoraggioTest.xml"})
@PropertySource("classpath*:src/test/java/it/csi/pbandi/pbweb/config/enviromentTest.properties")
public class TestBaseMonitoraggioService {

//	System.out.println("TestBaseService START");
}
