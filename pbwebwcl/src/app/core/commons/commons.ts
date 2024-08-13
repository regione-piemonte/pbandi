/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class ErrorRest {
    constructor(public type: TypeErrorRest, public message?: string, public codice?: string,) { }
}

export enum TypeErrorRest {
    TIMEOUT,
    SCONOSCIUTO,
    OK,
    UNAUTHORIZED
}

export enum ErrorCode {
    WARNING = "WARNING",
    ERROR = "ERROR"
}

export enum MessageRestError {
    GENERIC = "Attenzione! Si è verificato un errore di comunicazione con il server! Contattare l'assistenza.",
    TIMEOUT = "Attenzione! La rete da cui si è collegati risulta essere troppo lenta",
    UNAUTHORIZED = "Attenzione! L'utente non risulta autorizzato ad accedere alla risorsa richiesta",
}