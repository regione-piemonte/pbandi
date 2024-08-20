/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SchedaClienteMain } from '../commons/dto/scheda-cliente-main';
import { BehaviorSubject, Observable } from 'rxjs';
import { SaveSchedaCliente } from '../commons/dto/save-scheda-cliente.all';
import { LiberazioneGaranteDTO } from '../commons/dto/liberazione-garante-dto';
import { DatePipe } from '@angular/common';
import { EscussioneConfidiDTO } from '../commons/dto/escussione-confidi-dto';
import { PianoRientroDTO } from '../commons/dto/piano-rientro-dto';
import { LiberazioneBancaDTO } from '../commons/dto/liberazione-banca-dto';
import { IscrizioneRuoloDTO } from '../commons/dto/iscrizione-ruolo-dto';
import { CessioneQuotaDTO } from '../commons/dto/cessione-quota-fin';
import { NoteGeneraliVO } from "../commons/dto/note-generaliVO";
import { StoricoRevocaDTO } from "../commons/dto/storico-revoca-dto";
import { LavorazioneBoxListVO } from '../commons/dto/lavorazione-box-list-VO';


@Injectable({
  providedIn: 'root'
})
export class AttivitàIstruttoreAreaCreditiService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
    private datePipe: DatePipe
  ) { }

  private spinSync = new BehaviorSubject<boolean>(false);
  spinState = this.spinSync.asObservable();

  setSpinner(state: boolean) {
    this.spinSync.next(state);
  }
  

  jsonModifiche: string;



  getBoxList(idModalitaAgevolazione :number, idArea: number): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/getBoxList';
    let params = new HttpParams()
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString())
      .set("idArea", idArea.toString())
    return this.http.get<Array<any>>(url, { params: params });
  }

  getLavorazioneBox(idModalitaAgevolazione, idProgetto): Observable<Array<LavorazioneBoxListVO>> {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/getLavorazioneBox';
    let params = new HttpParams()
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString())
      .set("idProgetto", idProgetto.toString())
    return this.http.get<Array<LavorazioneBoxListVO>>(url, { params: params });
  }


  /* insertLiberazioneGarante(NewLiberazioneGarante: LiberazioneGaranteDTO,allegati,idProgetto,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setLiberazioneGarante';
    
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "NewLiberazioneGarante",
      JSON.stringify(NewLiberazioneGarante)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    formData.append("idProgetto", idProgetto);
    return this.http.post<any>(url, formData, { params: params });
  } */

  /* insertPianoRientro(NewPianoRientro,allegati,idProgetto,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setPianoRientro';
    
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "NewPianoRientro",
      JSON.stringify(NewPianoRientro)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    formData.append("idProgetto", idProgetto);
    return this.http.post<any>(url, formData, { params: params });
  } */
  
  /* inserisciEscussioneConfidi(newEscussione,allegati,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setPianoRientro';
    
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "newEscussione",
      JSON.stringify(newEscussione)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    return this.http.post<any>(url, formData, { params: params });
  } */

  /* inserisciLiberazioneBanca(newLiberazione,allegati,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setPianoRientro';
    
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "newLiberazione",
      JSON.stringify(newLiberazione)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    return this.http.post<any>(url, formData, { params: params });
  } */


  // LIBERAZIONE GARANTE //

  getLiberazioneGarante(progetto: string = "", idModalitaAgevolazione :number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getLiberazioneGarante';
    let params = new HttpParams()
      .set("idProgetto", progetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<LiberazioneGaranteDTO>>(url, { params: params })
  }

  insertLiberazioneGarante(NewLiberazioneGarante: LiberazioneGaranteDTO, idModalitaAgevolazione: number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setLiberazioneGarante';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(NewLiberazioneGarante);

    return this.http.post<any>(url, this.jsonModifiche, { headers: headers, params: params });
  }


  // ESCUSSIONE CONFIDI //

  getEscussioneConfidi(progetto = "", idModalitaAgevolazione :number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getEscussioneConfidi';
    let params = new HttpParams()
      .set("idProgetto", progetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    return this.http.get<Array<EscussioneConfidiDTO>>(url, { params: params })
  }

  inserisciEscussioneConfidi(newEscussione: EscussioneConfidiDTO, idModalitaAgevolazione :number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setEscussioneConfidi';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(newEscussione);

    return this.http.post<String>(url, this.jsonModifiche, { headers: headers, params: params });
  }



  // PIANO DI RIENTRO //

  getPianoRientro(progetto = "", idModalitaAgevolazione :number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getPianoRientro';
    let params = new HttpParams()
      .set("idProgetto", progetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<PianoRientroDTO>>(url, { params: params });
  }

  insertPianoRientro(newPianoRientro: PianoRientroDTO, idModalitaAgevolazione :number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setPianoRientro';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(newPianoRientro);

    return this.http.post<any>(url, this.jsonModifiche, { headers: headers, params: params });
  }



  // LIBERAZIONE BANCA //

  /*
  liberazione: LiberazioneBancaDTO;
  private liberazioneInfo = new BehaviorSubject<LiberazioneBancaDTO>(null);
  liberazioneInfo$: Observable<LiberazioneBancaDTO> = this.liberazioneInfo.asObservable();
  getLiberazioneBanca(progetto = "") {

    //this.liberazione = new LiberazioneBancaDTO();

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getLiberazioneBanca';

    let params = new HttpParams().set("idProgetto", progetto);

    return this.http.get<LiberazioneBancaDTO>(url, { params: params }).subscribe(data => {
      if (data) {
        this.liberazione = data;
        console.log("prenext: ", this.liberazioneInfo.getValue());

        this.liberazioneInfo.next(data);
        console.log("doponext: ", this.liberazioneInfo.getValue());

        //this.liberazioneInfo = new BehaviorSubject<LiberazioneBancaDTO>(null);
        
        console.log("LibBan: ", this.liberazioneInfo.getValue());
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }*/

  getNewLiberazione(idProgetto: string = "", idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getLiberazioneBanca';
    let params = new HttpParams()
      .set("idProgetto", idProgetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<LiberazioneBancaDTO>>(url, { params: params })
  }

  inserisciLiberazioneBanca(newLiberazione: LiberazioneBancaDTO, idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setLiberazioneBanca';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(newLiberazione);
    return this.http.post<String>(url, this.jsonModifiche, { headers: headers, params: params })
  }


  // ISCRIZIONE AL RUOLO //

  getIscrizioneRuolo(idProgetto: string = "", idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/getIscrizioneRuolo';
    let params = new HttpParams()
      .set("idProgetto", idProgetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<IscrizioneRuoloDTO>>(url, { params: params })
  }

  inserisciIscrizioneRuolo(newIscrizione: IscrizioneRuoloDTO, idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/setIscrizioneRuolo';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(newIscrizione);
    return this.http.post<String>(url, this.jsonModifiche, { headers: headers, params: params })
  }

  /* inserisciIscrizioneRuolo(newIscrizione,allegati,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setIscrizioneRuolo';
    
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "newIscrizione",
      JSON.stringify(newIscrizione)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    return this.http.post<any>(url, formData, { params: params });
  } */


  // CESSIONE QUOTA FINPIEMONTE //

  getCessioneQuota(idProgetto: string = "", idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getCessioneQuota';
    let params = new HttpParams()
      .set("idProgetto", idProgetto)
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<CessioneQuotaDTO>>(url, { params: params })
  }

  inserisciCessioneQuota(newCessione: CessioneQuotaDTO, idModalitaAgevolazione :number) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setCessioneQuota';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    this.jsonModifiche = JSON.stringify(newCessione);
    return this.http.post<String>(url, this.jsonModifiche, { headers: headers, params: params })
  }

  /* inserisciCessioneQuota(newCessione: CessioneQuotaDTO,allegati,idModalitaAgevolazione) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setCessioneQuota';
    let params = new HttpParams()
    let formData = new FormData();
    for (var i = 0; i < allegati.length; i++) {
      console.log(allegati[i]);
      console.log(allegati[i].name);
      formData.append("file", allegati[i], allegati[i].name);
    }
    formData.append(
      "newCessione",
      JSON.stringify(newCessione)
    );
  
    formData.append("idModalitaAgevolazione", idModalitaAgevolazione); 
    return this.http.post<any>(url, formData, { params: params });
  } */


  // NOTE GENERALI //

  /* Vecchio - sostituito dal nuovo salvaNota
  salvaNotaGenerale(noteGeneraliVO: NoteGeneraliVO, allegati: Array<File>, isModifica: boolean, idModalitaAgevolazione: number): Observable<boolean>{
    let url = this.configService.getApiURL() + '/restfacade/passaggioPerdita/salvaNote';
    let params = new HttpParams()
      .set("isModifica", isModifica.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    let formData = new FormData();
    formData.append("noteGenerali", JSON.stringify(noteGeneraliVO));
    
    //console.log("alleg: ", allegati);

    allegati.forEach(att => {
      formData.append("file", att, att.name);
    });
    
    return this.http.post<boolean>(url, formData, { params: params });
  }*/

  getNoteGenerali(idProgetto: number, idModalitaAgevolazione: number): Observable<Array<NoteGeneraliVO>> {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/getNote';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<NoteGeneraliVO>>(url, { params: params });
  }

  getNoteMonitoraggio(idProgetto: number, idModalitaAgevolazione: number): Observable<Array<StoricoRevocaDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/getStoricoNote';
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<Array<StoricoRevocaDTO>>(url, { params: params });
  }

  salvaNota(nuovaNota: NoteGeneraliVO, allegati: Array<File>, isModifica: boolean, idModalitaAgevolazione: number) {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/salvaNota';
    let params = new HttpParams()
      .set("isModifica", isModifica.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    return this.http.post<boolean>(url, nuovaNota, { params: params });
  }

  eliminaNota(idAnnotazione: number, idModalitaAgevolazione: number) {
    let url = this.configService.getApiURL() + '/restfacade/attivitaIstruttore/eliminaNota';
    let params = new HttpParams()
      .set("idAnnotazione", idAnnotazione.toString())
      .set("idModalitaAgevolazione", idModalitaAgevolazione.toString());

    return this.http.get<boolean>(url, { params: params });
  }
}
