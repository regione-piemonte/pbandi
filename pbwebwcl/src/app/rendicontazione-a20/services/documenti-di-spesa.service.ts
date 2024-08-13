/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable({
	providedIn: 'root',
})
export class DocumentiDiSpesaService {
	constructor(private configService: ConfigService, private http: HttpClient) {}

	getCategorie(idProgetto: number) {
		let url = this.configService.getApiURL() + '/restfacade/decodificheCultura/categorie';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<Array<any>>(url, { params: params });
	}

	getFornitori(idProgetto: number) {
		let url = this.configService.getApiURL() + '/restfacade/decodificheCultura/fornitoriComboRicerca';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<Array<any>>(url, { params: params });
	}

	getPartners(idProgetto: number, idBandoLinea: number) {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/partners';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idBandoLinea', idBandoLinea.toString());
		return this.http.get<any>(url, { params: params });
	}

	getTipologieDocumento(idBandoLinea: string) {
		let url = this.configService.getApiURL() + '/restfacade/decodifiche/tipologieDocumento';
		let params = new HttpParams().set('idBandoLinea', idBandoLinea);
		return this.http.get<Array<any>>(url, { params: params });
	}

	getVociDiSpesaRicerca(idProgetto: number, isBandoCultura: boolean) {
		let url = this.configService.getApiURL();
		if (isBandoCultura) url += '/restfacade/documentoDiSpesa/vociDiSpesaRicercaCultura';
		else url += '/restfacade/documentoDiSpesa/vociDiSpesaRicerca';

		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<Array<any>>(url, { params: params });
	}

	ricercaDocumentiDiSpesa(idProgetto: number, codiceProgettoCorrente: string, idSoggettoBeneficiario: number, codiceRuolo: string, datiForm) {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/ricercaDocumentiDiSpesa';
		let params = new HttpParams()
			.set('idProgetto', idProgetto.toString())
			.set('codiceProgettoCorrente', codiceProgettoCorrente)
			.set('idSoggettoBeneficiario', idSoggettoBeneficiario.toString())
			.set('codiceRuolo', codiceRuolo);
		return this.http.post<any>(url, datiForm, { params: params });
	}
}
