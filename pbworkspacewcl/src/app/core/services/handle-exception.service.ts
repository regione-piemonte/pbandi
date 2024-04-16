/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { MessageRestError, ErrorRest, TypeErrorRest } from '../commons/commons';

/**
 * SE UNAUTHORIZED => estraggo messaggio e lo mostro
 * SE ALTRO ERRORE => controllo se presente lo stato e lo mostro . Altrimenti messaggio generico 
 */
@Injectable()
export class HandleExceptionService {


    constructor(
        private router: Router
    ) { }


    /**
     * Return to Homepage
     */
    public handleBlockingError(err: any): void {
        //UNAUTHORIZED
        if (err.status == 401) {
            let object = null;
            try {
                if (typeof (err.error) === 'string' || err.error instanceof String)
                    object = JSON.parse(err.error);
                else
                    object = err.error;
            } catch (e) {
                console.log('eccezione in parse json:' + e);
                this.router.navigate(['/errorRouting'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
            }
            let mes = MessageRestError.UNAUTHORIZED
            if (object) {
                let exception = object['exception'];
                if (exception && exception.hasOwnProperty('message') && exception.message != null) {
                    mes = object['message'];
                }
            }
            console.log(`Backend returned code ${err.status}, message was: ${err.message}`);
            this.router.navigate(['/errorRouting'], { queryParams: { message: mes }, skipLocationChange: true });

        }
        else {
            if (this.isTimeOutError(err.name))
                this.router.navigate(['/errorRouting'], { queryParams: { message: MessageRestError.TIMEOUT }, skipLocationChange: true });
            if (this.isClientSideErrorOrNetworkError(err))
                this.router.navigate(['/errorRouting'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
            else {
                let object = null;
                try {
                    if (typeof (err.error) === 'string' || err.error instanceof String)
                        object = JSON.parse(err.error);
                    else
                        object = err.error;
                } catch (e) {
                    console.log('eccezione in parse json:' + e);
                    this.router.navigate(['/errorRouting'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
                }

                console.log(`Backend returned code ${err.status}, body was: ${err.message}`);
                let exception = null;
                try {
                    exception = object['exception'];
                } catch (e) {
                    console.log("NON SI TRATTA DI UN ECCEZIONE BEN GESTITA");
                }
                let codice = null;
                let mes = MessageRestError.GENERIC
                if (exception != null && exception.hasOwnProperty('codice') && exception.codice != null) {
                    codice = exception['codice'];
                    mes = object['message'];
                }
                this.router.navigate(['/errorRouting'], { queryParams: { message: mes }, skipLocationChange: true });
            }
        }

    }

    //ESTRAE MESSAGGIO BE E LO MOSTRA SOLO SE POSSIEDE CODICE
    public handleNotBlockingError(err: any): ErrorRest {
        if (err.status == 401) {
            let object = null;
            try {
                if (typeof (err.error) === 'string' || err.error instanceof String)
                    object = JSON.parse(err.error);
                else
                    object = err.error;
            } catch (e) {
                console.log('eccezione in parse json:' + e);
                return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
            }
            let exception = object['exception'];
            let mes = MessageRestError.UNAUTHORIZED
            if (exception.hasOwnProperty('message') && exception.message != null) {
                mes = object['message'];
            }
            console.log(`Backend returned code ${err.status}, message was: ${err.message}`);
            return new ErrorRest(TypeErrorRest.UNAUTHORIZED, mes);
        }
        else {
            if (this.isTimeOutError(err.name)) {
                console.log('eccezione isTimeOutError');
                return new ErrorRest(TypeErrorRest.TIMEOUT, MessageRestError.TIMEOUT);
            } else if (this.isClientSideErrorOrNetworkError(err)) {
                console.log('eccezione isClientSideErrorOrNetworkError');
                return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
            } else {
                console.log('eccezione else');
                //BE ERROR 
                let object = null;
                try {
                    if (typeof (err.error) === 'string' || err.error instanceof String)
                        object = JSON.parse(err.error);
                    else
                        object = err.error;
                } catch (e) {
                    console.log('eccezione in parse json:' + e);
                    return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
                }
                console.log(`Backend returned code ${err.status}, body was: ${err.message}, Json: ${JSON.stringify(err.error)}`);
                let exception = null;
                try {
                    exception = object['exception'];
                } catch (e) {
                    console.log("NON SI TRATTA DI UN ECCEZIONE BEN GESTITA");
                }
                let customMessage = null;
                try {
                    customMessage = object['message'];
                } catch (e) {
                    console.log("NON VI E' UN MESSAGGIO DAL BACKEND");
                }
                let codice = null;
                let message = MessageRestError.GENERIC
                if (exception != null && exception.hasOwnProperty('codice') && exception.codice != null) {
                    codice = exception['codice'];
                    message = object['message'];
                } else if (customMessage != null) {
                    codice = '500';
                    message = customMessage;
                }

                return new ErrorRest(TypeErrorRest.OK, message, codice);
            }
        }

    }


    private isClientSideErrorOrNetworkError(err: any) {
        if (err.error instanceof Error) {
            console.log('An error occurred:', err.error.message);
            return true;
        }
        else false;
    }

    private isTimeOutError(err: any) {
        if (err == "TimeoutError") {
            console.log('TIMEOUT ERROR WITH MESSAGE:', err);
            return true;
        }
        else false;
    }

}