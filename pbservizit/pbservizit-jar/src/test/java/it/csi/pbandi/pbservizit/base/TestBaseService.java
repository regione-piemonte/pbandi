/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.base;

import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:/applicationContextTest.xml"})
@PropertySource("classpath*:src/test/java/it/csi/pbandi/pbservizit/config/enviromentTest.properties")
public class TestBaseService {

}
