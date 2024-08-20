/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from "@angular/common";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { HandleExceptionService } from "@pbandi/common-lib";
import { ConfigService } from "src/app/core/services/config.service";

@Injectable({
    providedIn: 'root'
})

export class AttivitaIstruttorieService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService,
        private datePipe: DatePipe
    ) {}

}