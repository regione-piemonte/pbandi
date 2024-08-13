/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.util;

import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
	
	public static <T> ResponseEntity<ResponseWrapper> build(ResponseWrapper<T> response) {
        if( response.getCode() == 200 ){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
	
	
}