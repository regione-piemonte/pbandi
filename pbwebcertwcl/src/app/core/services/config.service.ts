/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment'
/**
 * Servizio di configurazione
 */
@Injectable()
export class ConfigService {

    /**
     * server del backend nel formato http://server:port
     */
    getApiURL(): string {
        return environment.apiURL;
    }

    getPbwebURL(): string {
        return environment.pbwebURL;
    }

    getPbwebboURL(): string {
        return environment.pbwebboURL;
    }

    getPbworkspaceURL(): string {
        return environment.pbworkspaceURL;
    }

    getPbweberogURL(): string {
        return environment.pbweberogURL;
    }

    getPbwebrceURL(): string {
        return environment.pbwebrceURL;
    }

    getPbgestfinboURL(): string {
        return environment.pbgestfinboURL;
    }

    getPbwebfinURL(): string {
        return environment.pbwebfinURL;
    }

    /**
     * Url di logout da SSO
     */
    getSSOLogoutURL(): string {
        return environment.shibbolethSSOLogoutURL;
    }

    getTimeout(): number {
        return environment.timeout;
    }

}