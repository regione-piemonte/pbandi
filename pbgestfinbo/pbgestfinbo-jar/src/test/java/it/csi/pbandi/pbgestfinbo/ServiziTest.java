/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import javax.ws.rs.client.Entity;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import it.csi.findom.finservrest.dto.DocumentoResponse;
import it.csi.findom.finservrest.dto.RicezioneBloccoSbloccoResponse;
import it.csi.pbandi.ammvoservrest.dto.Iban;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class ServiziTest {

	RestTemplate restTemplate = new RestTemplate();
	
	@Test
    public void testName()
    {
		System.out.println("ss");
//		Assert.assertSame("Name is not the same as expected: ", "Default Random Number Generator", randomGenerator.name());
		
		
		String url = "http://dev-<VH_esposizione_servizi>/finservrest"+ "/api/ricezioneBloccoSbloccoAnagrafico/ricezione";
		
		Client client = ClientBuilder.newBuilder().build(); 
		WebTarget target = client.target(url);
		target.queryParam("numeroDomanda", 15505); 
		target.queryParam("codiceFiscale","3785920103"); 
		target.queryParam("descCausaleBlocco", "cessata"); 
		target.queryParam("dtInsBlocco", "2023-05-12"); 
//		target.queryParam("dtInsSblocco", LocalDate.now()); 
		
		
		Response resp = (Response) target.request().get();
		System.out.println(resp.getStatus());
		RicezioneBloccoSbloccoResponse dr = resp.readEntity(RicezioneBloccoSbloccoResponse.class);
		System.out.println("la risposta al servizio è:: "+dr);
		
//		Entity<Iban> jsonIbanAmmCont = Entity.json(ibanAmmCont);
//		System.out.println( "eIban="+jsonIbanAmmCont.toString());
		
		
    }
	
	
}
