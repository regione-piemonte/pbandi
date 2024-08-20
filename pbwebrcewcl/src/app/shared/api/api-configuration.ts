/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';

/**
 * Global configuration for Api services
 */
@Injectable({
  providedIn: 'root',
})
export class ApiConfiguration {
  //rootUrl: string = 'http://localhost:4260/finanziamenti/bandi/pbwebrce/restfacade';
  rootUrl: string = 'https://dev-<VH_SECURE>/finanziamenti/bandi/pbwebrce/restfacade';
  //rootUrl: string = 'https://dev-<VH_SECURE>/finanziamenti/bandi/pbwebrce/restfacade';
}

export interface ApiConfigurationInterface {
  rootUrl?: string;
}
