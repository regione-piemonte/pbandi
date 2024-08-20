/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Richiesta } from '../../commons/dto/richiesta';
import { RichiesteService } from '../../services/richieste.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

interface TipiComunicazione {
  value: string;
  viewValue: string;
}

interface TipiRichiesta {
  value: string;
  viewValue: string;
}

interface StatoRichiesta {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-elabora-richiesta',
  templateUrl: './elabora-richiesta.component.html',
  styleUrls: ['./elabora-richiesta.component.scss']
})
export class ElaboraRichiestaComponent implements OnInit {
  ndg: string;
  loadedElaborazione: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private richiesteService: RichiesteService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE;
  spinner: boolean = true;

  //FORM
  public myForm: FormGroup;

  //TRUE = ORDINARIA
  flagUrgenza: boolean = true;

  //VARIABILI PER LE GET/POST
  idUtente: any;
  idRichiesta: any;
  tipoRichiesta: any;
  statoRichiesta: any;
  isAntimafia: boolean = false;
  numeroDomanda: any;
  nag: any;

  //DATEPICKER VALIDATOR
  today: Date;
  myFilter = (d: Date | null): boolean => {
    return d < this.today;
  }

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //OPZIONI PER IL TIPO DI COMUNICAZIONE
  tipiComunicazione: TipiComunicazione[] = [
    { value: '1', viewValue: 'Invio' },
    { value: '2', viewValue: 'Ricezione' },
  ];

  //OPZIONI PER I TIPI RICHIESTE 
  richieste: TipiRichiesta[] = [
    { value: '1', viewValue: 'DURC' },
    { value: '2', viewValue: 'DSAN in assenza di DURC' },
  ];
  richiestaAntimafia: TipiRichiesta[] = [{ value: '3', viewValue: 'Antimafia' }]
  richiestaDsan: TipiRichiesta[] = [{ value: '2', viewValue: 'DSAN in assenza di DURC' }]

  //OPZIONI PER GLI STATI DELLE RICHIESTE
  statiRichieste: StatoRichiesta[] = [
    { value: '2', viewValue: 'In elaborazione' },
    { value: '4', viewValue: 'Completata' },
  ];

  statoRichiestePerAntimafia: StatoRichiesta[] = [
    { value: '2', viewValue: 'In elaborazione' },
    { value: '3', viewValue: 'In istruttoria BDNA' },
    { value: '4', viewValue: 'Completata' },
  ];

  //LISTE PER LE SUGGEST
  suggDestinatarioMittente: string[] = [];
  suggNumeroProtocollo: string[] = [];
  suggMotivazione: string[] = [];

  //CHECK PER DISABILITARE I BOTTONI
  destMittIns: any;
  numProtIns: any;
  motiIns: any;

  //BOOLEAN PER ALERT PER SUCCESSO O FALLIMENTO
  success: boolean = false;
  error: boolean = false;
  denominazione: any;
  partitaIva: any;

  ngOnInit() {
    this.idRichiesta = this.route.snapshot.queryParamMap.get('idRichiesta');
    this.nag = this.route.snapshot.queryParamMap.get('nag');
    this.ndg = this.route.snapshot.queryParamMap.get('ndg');
    this.tipoRichiesta = this.route.snapshot.queryParamMap.get('idTipoRichiesta');
    this.statoRichiesta = this.route.snapshot.queryParamMap.get('idStatoRichiesta');
    this.denominazione = this.route.snapshot.queryParamMap.get('denominazione');
    this.partitaIva = this.route.snapshot.queryParamMap.get('partitaIva');

    this.today = new Date();

    this.myForm = this.fb.group({
      idTipoRichiesta: new FormControl(''),
      idStatoRichiesta: new FormControl('', [Validators.required]),
      dataComunicazione: new FormControl('', [Validators.required]),
      tipoComunicazione: new FormControl('', [Validators.required]),
      destinatarioMittente: new FormControl('', [Validators.required]),
      numeroProtocollo: new FormControl('', Validators.maxLength(20)), 
      motivazione: new FormControl(''),
      urgenza: new FormControl('')
    });

    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = data.idUtente
      }
    }, err => {
    });

      this.getRichiesta();
    
  }
getRichiesta(){
  //SETTING DELLA RICHIESTA DA ELBORARE
  this.richiesteService.getRichiesta(this.idRichiesta).subscribe(data => {
    if (data) {
      if (this.statoRichiesta == "1") {
        this.myForm.setValue({
          idTipoRichiesta: data[0].idTipoRichiesta,
          idStatoRichiesta: "2",
          dataComunicazione: new Date("01/01/2022"),
          tipoComunicazione: "",
          destinatarioMittente: "",
          numeroProtocollo: "",
          motivazione: "",
          urgenza: data[0].flagUrgenza
        });
      } else {
        this.myForm.setValue({
          idTipoRichiesta: data[0].idTipoRichiesta,
          idStatoRichiesta: data[0].idStatoRichiesta,
          dataComunicazione: new Date("01/01/2022"),
          tipoComunicazione: "",
          destinatarioMittente: "",
          numeroProtocollo: "",
          motivazione: "",
          urgenza: data[0].flagUrgenza
        });
      }

      if(data[0].flagUrgenza ==1){
        this.flagUrgenza=false
      } else {
        this.flagUrgenza= true
      }
      
      this.numeroDomanda = data[0].numeroDomanda;
      this.spinner = false;
      if (this.myForm.controls.idTipoRichiesta.value == '3') {
        this.isAntimafia = true;
      }
    }
  });
}

  confermaElaborazione() {
    this.error = false;
    let flag;
    if (this.flagUrgenza == false) {
      flag = "S"
    } else {
      flag = "N"
    }
    this.success = false;
    let richiesta = this.idRichiesta;
    let NAG = this.nag;
    let parseDate = this.myForm.controls.dataComunicazione.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataComunicazione.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataComunicazione.value.getDate();
    let modifica = new Richiesta(
      this.idRichiesta,
      this.idUtente,
      null,
      this.myForm.controls.idTipoRichiesta.value,
      this.myForm.controls.idStatoRichiesta.value,
      this.myForm.controls.tipoComunicazione.value,
      parseDate.toString(),
      this.myForm.controls.destinatarioMittente.value,
      this.myForm.controls.numeroProtocollo.value,
      this.myForm.controls.motivazione.value,
      this.numeroDomanda,
      this.nag,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      flag
    );


    if (this.error == false) {
      let tipoRichiesta = this.myForm.controls.idTipoRichiesta.value;
      if ((this.myForm.controls.idTipoRichiesta.value == '1' || this.myForm.controls.idTipoRichiesta.value == '2') && this.myForm.controls.idStatoRichiesta.value == '4') {
        sessionStorage.setItem("richiesta", JSON.stringify(modifica));
        this.router.navigate(["/drawer/" + this.idOp + "/elaboraDurc"], { queryParams: { richiesta, NAG, tipoRichiesta } });
      } else if (this.myForm.controls.idTipoRichiesta.value == '3' && this.myForm.controls.idStatoRichiesta.value == '3') {
        sessionStorage.setItem("richiesta", JSON.stringify(modifica));
        this.router.navigate(["/drawer/" + this.idOp + "/elaboraBdna"], { queryParams: { richiesta, NAG } });
      } else if (this.myForm.controls.idTipoRichiesta.value == '3' && this.myForm.controls.idStatoRichiesta.value == '4') {
        sessionStorage.setItem("richiesta", JSON.stringify(modifica));
        this.router.navigate(["/drawer/" + this.idOp + "/elaboraAntimafia"], { queryParams: { richiesta, NAG } });
      } else {
        this.richiesteService.elaboraRichiesta(modifica).subscribe(data => {
          if(data.toString()=="0"){
            sessionStorage.removeItem("richiesta");
            this.error = false;
            this.success = true;
            setTimeout(() => {
              this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
            }, 3000);
          }  else {
            this.error = true;
            this.success = false;
          }
        },error => {
          this.loadedElaborazione = true; 
          this.error = true; 
          this.handleExceptionService.handleNotBlockingError(error);
        });
      }
    }
  }

  cambiaFlag(value) {
    if (this.myForm.controls.idTipoRichiesta.value == '3' && value == 1 && this.flagUrgenza == true) {
      this.flagUrgenza = !this.flagUrgenza;
    } else if (this.myForm.controls.idTipoRichiesta.value == '3' && value == 2 && this.flagUrgenza == false) {
      this.flagUrgenza = !this.flagUrgenza;
    }
  }

  reset() {
    this.myForm.setValue({
      idTipoRichiesta: this.tipoRichiesta,
      idStatoRichiesta: "2",
      dataComunicazione: new Date("01/01/2022"),
      tipoComunicazione: "",
      destinatarioMittente: "",
      numeroProtocollo: "",
      motivazione: "",
      urgenza: this.flagUrgenza
    });
    this.getRichiesta();
  }

  sugg(id: number, value: string) {
    if (id == 6) {
      if (value.length >= 2) {
        this.suggDestinatarioMittente = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 6).subscribe(data => { if (data.length > 0) { this.suggDestinatarioMittente = data } else { this.suggDestinatarioMittente = ["Nessuna corrispondenza"] } })
      } else { this.suggDestinatarioMittente = [] }
    }
    else if (id == 7) {

      if (value.length >= 2) {
        this.suggNumeroProtocollo = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 7).subscribe(data => { if (data.length > 0) { this.suggNumeroProtocollo = data } else { this.suggNumeroProtocollo = ["Nessuna corrispondenza"] } })
      } else { this.suggNumeroProtocollo = [] }
    }
    else if (id == 8) {

      if (value.length >= 2) {
        this.suggMotivazione = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 8).subscribe(data => { if (data.length > 0) { this.suggMotivazione = data } else { this.suggMotivazione = ["Nessuna corrispondenza"] } })
      } else { this.suggMotivazione = [] }
    }
  }

  goBack() {
    this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
  }

}
