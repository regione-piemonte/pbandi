/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Richiesta } from '../../commons/dto/richiesta';
import { RichiesteService } from '../../services/richieste.service';
import { Location } from '@angular/common';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

interface Esito {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-elabora-antimafia',
  templateUrl: './elabora-antimafia.component.html',
  styleUrls: ['./elabora-antimafia.component.scss']
})
export class ElaboraAntimafiaComponent implements OnInit {
  file: File;
  messageError: any;
  messageSuccess: string;
  loadedElaborazione: boolean = true;
  loadedFile: boolean = true;

  constructor(private route: ActivatedRoute,
    private location: Location,
    private router: Router,
    private sharedService: SharedService,
    private richiesteService: RichiesteService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private userService: UserService) { }

  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE;
  spinner: boolean = false;

  //VARIABILI
  idRichiesta: any;
  nag: any;
  numeroDomanda: any;
  datiRichiesta: any;
  idUtente: Number;
  sequenziale: any;

  //FORM
  public myForm: FormGroup;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //VARIABILE PER IL BANNER
  error: boolean = false;
  success: boolean = false;

  //OPZIONI PER IL TIPO RICHIESTE
  esiti: Esito[] = [
    { value: '4', viewValue: 'Sussistono impedimenti' },
    { value: '3', viewValue: 'Non sussistono impedimenti' },
  ];

  //TODO MARTI

  //DATEPICKER VALIDATOR
  today: Date;
  myFilter = (d: Date | null): boolean => {
    return d < this.today;
  }
  name: string;
  ngOnInit() {
    this.today = new Date();
    this.name = this.file?.name;
    this.idRichiesta = this.route.snapshot.queryParamMap.get('richiesta');
    this.nag = this.route.snapshot.queryParamMap.get('NAG');
    this.datiRichiesta = JSON.parse(sessionStorage.getItem("richiesta"));
    console.log("DATA: ", this.datiRichiesta)
    this.myForm = this.fb.group({
      dataEmissione: new FormControl('', [Validators.required]),
      dataScadenza: new FormControl('', [Validators.required]),
      esito: new FormControl('', [Validators.required]),
      numeroProtocolloPrefettura: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      nomeFile: [{ value: '', disabled: true }],
    });

    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = data.idUtente
      }
    }, err => {
    });
  }

  confermaElaborazione() {
    this.loadedElaborazione = false; 
    let parseDate = this.myForm.controls.dataEmissione.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataEmissione.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataEmissione.value.getDate();
    let parseDate2 = this.myForm.controls.dataScadenza.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataScadenza.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataScadenza.value.getDate();
    let parseDate3 = this.today.getFullYear() + "-" + ("0" + (this.today.getMonth() + 1)).slice(-2) + "-" + this.today.getDate();
   
    let modifica = new Richiesta(
      this.idRichiesta,
      this.idUtente,
      null,
      this.datiRichiesta.idTipoRichiesta,
      this.datiRichiesta.idStatoRichiesta,
      this.datiRichiesta.tipoComunicazione,
      this.datiRichiesta.dataComunicazione,
      this.datiRichiesta.destinatarioMittente,
      this.datiRichiesta.numeroProtocollo,
      this.datiRichiesta.motivazione,
      this.datiRichiesta.numeroDomanda,
      this.nag,
      parseDate,
      parseDate2,
      this.myForm.controls.esito.value,
      null,
      parseDate3,
      '-',
      this.myForm.controls.numeroProtocolloPrefettura.value,
      this.datiRichiesta.flagUrgenza
    );
    this.numeroDomanda = this.datiRichiesta.numeroDomanda;
    //SERVIZIO DI POST:

    this.richiesteService.elaboraAntimafia(modifica, (this.file)? true: false).subscribe(data => {
      if (data != null) {
        console.log("data:res " + data);
        this.richiesteService.elaboraRichiesta(this.datiRichiesta).subscribe(data => {
          if (data.toString() == "0") {
            this.error = false;
            this.success = true;
            this.loadedElaborazione = true; 
          }
        });
        if (this.file){
          this.uploadFile(data, modifica);
        } else{
          setTimeout(() => {
            this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
          }, 4000);
        }
        sessionStorage.removeItem("richiesta");
        // this.error = false;
        // this.success = true;
        // this.sequenziale = data; 

      } else {
        this.error = true;
        this.success = false;
      }
    }, 
    error => {
      this.loadedElaborazione = true; 
      this.handleExceptionService.handleNotBlockingError(error);
      this.showMessageError("Errore in elaborazione della richiesta.");
    });

  }
  uploadFile(idTarget: any, richiesta: any) {
    this.loadedFile= false; 
    // UPLOAD FILE
    this.richiesteService.salvaUploadAntiMafia(this.file, this.file.name, this.idUtente, idTarget, this.numeroDomanda, this.nag, richiesta).subscribe(data => {
      if (data) {
        this.error = false;
        this.success = true;
        this.loadedFile= true;
        this.showMessageSuccess("Salvataggio del file eseguito con successo.");
        setTimeout(() => {
          this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
        }, 4000);
      }
      else {
        this.error = true;
        this.success = false;
        this.showMessageError("Errore in fase di upload del file.");
      }
    }, 
    error => {
      this.loadedFile = true; 
      this.handleExceptionService.handleNotBlockingError(error);
      this.showMessageError("Errore in fase di salvataggio del file della richiesta. ");
    });
  }
  setFileNull() {
    this.file = null;
    this.myForm.get("nomeFile").setValue("")
  }
  showMessageError(msg: string) {
    this.messageError = msg;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
  }
  handleFileInput(file: File) {
    this.file = file;
    this.myForm.get("nomeFile").setValue(file.name);
  }

  setDataScadenza() {
    let tempDate = new Date(this.myForm.controls.dataEmissione.value)
    tempDate.setDate(tempDate.getDate() + 365);
    this.myForm.controls.dataScadenza.patchValue(tempDate);
  }

  reset() {
    this.myForm.setValue({
      dataEmissione: '',
      dataScadenza: '',
      esito: '',
      numeroProtocolloPrefettura: '',
      nomeFile: ''
    });
    this.file = null;
  }

  goBack(): void {
    this.location.back();
  }

  isLoading() {
    if (!this.loadedElaborazione || !this.loadedFile) {
      return true;
    }
    return false;
  }
}
