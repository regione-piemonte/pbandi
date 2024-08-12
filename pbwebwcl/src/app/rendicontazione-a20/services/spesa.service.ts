import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { VoceDiEntrataDaSalvare } from '../commons/entrate-vo';
import { SpesaPreventivata, VoceDiSpesaDaSalvare } from '../commons/conto-economico-vo';

@Injectable({
	providedIn: 'root',
})
export class SpesaService {
	constructor(private configService: ConfigService, private http: HttpClient) {}

	getSpesa(idProgetto: any): any {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/vociDiSpesaCultura';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<any>(url, { params: params });
	}

	getEntrate(idProgetto: any): any {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/vociDiEntrataCultura';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<any>(url, { params: params });
	}

	addVoceDiEntrataCultura(datiBackend) {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/addVoceDiEntrataCultura';
		return this.http.post<any>(url, datiBackend);
	}

	salvaVociDiEntrataCultura(datiBackend: { vociDiEntrataCultura: VoceDiEntrataDaSalvare[]; idProgetto: number }) {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/salvaVociDiEntrataCultura';
		return this.http.post<any>(url, datiBackend);
	}

	salvaSpesePreventivate(datiBackend: { spesePreventivate: Array<SpesaPreventivata>}) {
		let url = this.configService.getApiURL() + '/restfacade/contoEconomico/salvaSpesePreventivate';
		return this.http.post<any>(url, datiBackend);
	}

	// getPercentualeImportoAgevolato(idBando: number): any {
	// 	let url = this.configService.getApiURL() + '/restfacade/contoEconomico/percImportoAgevolato';
	// 	let params = new HttpParams().set('idBando', idBando.toString());
	// 	return this.http.get<any>(url, { params: params });
	// }

	//COMMENTATO PERCHE' VIENE CHIAMATO DIRETTAMENTE BE
	// chiudiConsuntivo(idProgetto: number){
	// 	let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/chiudiConsuntivo';
	// 	let params = new HttpParams().set('idProgetto', idProgetto.toString());
	// 	return this.http.get<boolean>(url, { params: params });
	// }

	isBottoneConsuntivoVisibile(idProgetto: number){
		let url = this.configService.getApiURL() + '/restfacade/dichiarazioneDiSpesa/isBottoneConsuntivoVisibile';
        let params = new HttpParams().set('idProgetto', idProgetto.toString());
        return this.http.get<boolean>(url, { params: params });
	}
}
