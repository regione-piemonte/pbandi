/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiURL: 'http://localhost:4220/finanziamenti/bandi/pbwebbo',
  pbworkspaceURL: 'http://localhost:4200/pbworkspace',
  pbwebURL: 'http://localhost:4301/pbweb',
  pbweberogURL: 'http://localhost:4280/pbweberog',
  pbwebcertURL: 'http://localhost:4240/pbwebcert',
  pbwebrceURL: 'http://localhost:4260/pbwebrce',
  pbgestfinboURL: "http://localhost:4300/pbgestfinbo",
  pbwebfinURL: "http://localhost:4290/pbwebfin",
  shibbolethSSOLogoutURL: "https://dev-<VH_SECURE>//ssp_liv3_sisp_liv2_spid_GASP_REGIONE/logout.do",
  timeout: 120
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
