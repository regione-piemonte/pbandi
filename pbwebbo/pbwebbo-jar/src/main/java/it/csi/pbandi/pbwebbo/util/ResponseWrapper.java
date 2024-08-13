/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseWrapper<T> {
    
    @JsonInclude(Include.NON_NULL)
    private int code;

    @JsonInclude(Include.NON_NULL)
    private T result;

    @JsonInclude(Include.NON_NULL)
    private String error;

    @JsonInclude(Include.NON_NULL)
    private Date date;

    public ResponseWrapper(int code, String error, T result){
        this.code = code;
        this.error = error;
        this.result = result;

        if( error != null ){
            this.date = new Date();
        }
    }

    public static <T> ResponseWrapper<T> error(String error, T result) {
        return new ResponseWrapper<T>(500, error, result);
    }
    
    public static <T> ResponseWrapper<T> badRequest(String error, T result) {
        return new ResponseWrapper<T>(400, error, result);
    }
    public static <T> ResponseWrapper<T> error(String error) {
        return new ResponseWrapper<T>(500, error, null);
    }

    public static <T> ResponseWrapper<T> ok(T result) {
        return new ResponseWrapper<T>(200, null, result);
    }

    public static <T> ResponseWrapper<T> ok() {
        return new ResponseWrapper<T>(200, null, null);
    }

    public static <T> ResponseWrapper<T> notFound(String message) {
        return new ResponseWrapper<T>(404, message, null);
    }

    public static <T> ResponseWrapper<T> notFound() {
        return new ResponseWrapper<T>(404, "resource not found", null);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return new ResponseWrapper<T>(401, message, null);
    }

    public static <T> ResponseWrapper<T> unauthorized() {
        return new ResponseWrapper<T>(401, "unauthorized", null);
    }    

    public static <T> ResponseWrapper<T> forbidden(String message) {
        return new ResponseWrapper<T>(403, message, null);
    }

    public static <T> ResponseWrapper<T> forbidden() {
        return new ResponseWrapper<T>(403, "forbidden", null);
    }
    
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}    
    
}
