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

    getPbworkspaceURL(): string {
        return environment.pbworkspaceURL;
    }

    getPbwebURL(): string {
        return environment.pbwebURL;
    }

    getPbwebcertURL(): string {
        return environment.pbwebcertURL;
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