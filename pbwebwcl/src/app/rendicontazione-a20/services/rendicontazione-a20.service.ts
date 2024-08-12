import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable({
  providedIn: 'root'
})
export class RendicontazioneA20Service {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  inizializzaRendicontazione(idProgetto: number, idBandoLinea: number, idSoggetto: number, codiceRuolo: string) {
    let url = this.configService.getApiURL() + '/restfacade/rendicontazioneBandoCultura/inizializzaRendicontazione';
    let params = new HttpParams()
        .set("idProgetto", idProgetto.toString())
        .set("idBandoLinea", idBandoLinea.toString())
        .set("idSoggetto", idSoggetto.toString())
        .set("codiceRuolo", codiceRuolo);
    return this.http.get<any>(url, { params: params });
}

getDirezioniSettori(): any{
  let url = this.configService.getApiURL() + '/restfacade/rendicontazioneBandoCultura/direzioniSettori';
  let params = new HttpParams()
  return this.http.get<any>(url, { params: params });
}

}
