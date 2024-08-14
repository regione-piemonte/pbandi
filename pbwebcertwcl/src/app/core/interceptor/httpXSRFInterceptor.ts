/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpInterceptor, HttpXsrfTokenExtractor, HttpHandler, HttpRequest, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';

@Injectable()
export class XsrfInterceptor implements HttpInterceptor {

    constructor(private tokenExtractor: HttpXsrfTokenExtractor) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let requestToForward = req;
        let token = this.tokenExtractor.getToken() as string;
        if (token !== null) {
            requestToForward = req.clone({ setHeaders: { "X-XSRF-TOKEN-WEBCERT": token } });
        }
        return next.handle(requestToForward);
    }
}