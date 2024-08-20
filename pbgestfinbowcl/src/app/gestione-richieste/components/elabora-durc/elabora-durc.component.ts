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
  selector: 'app-elabora-durc',
  templateUrl: './elabora-durc.component.html',
  styleUrls: ['./elabora-durc.component.scss']
})

export class ElaboraDurcComponent implements OnInit {
  messageError: string;
  messageSuccess: string;
  loadedElaborazione: boolean = true;
  loadedFile: boolean = true;

  constructor(
    private location: Location,
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
  spinner: boolean = false;

  //VARIABILI
  idRichiesta: any;
  nag: any;
  numeroDomanda: any;
  datiRichiesta: any;
  idUtente: any;
  tipoRichiesta: any;
  tipoEsito:number;

  //FORM
  public myForm: FormGroup;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //VARIABILE PER IL BANNER
  error: boolean = false;
  success: boolean = false;

  //OPZIONI PER IL TIPO RICHIESTE
  esiti: Esito[] = [
    { value: '1', viewValue: 'Regolare' },
    { value: '2', viewValue: 'Non regolare' },
  ];

  //TODO MARTI


  //DATEPICKER VALIDATOR
  today: Date;
  myFilter = (d: Date | null): boolean => {
    return d < this.today;
  }

  // FILE
  file: File;


  ngOnInit() {
    this.today = new Date();
    this.idRichiesta = this.route.snapshot.queryParamMap.get('richiesta');
    this.nag = this.route.snapshot.queryParamMap.get('NAG');
    this.datiRichiesta = JSON.parse(sessionStorage.getItem("richiesta"));
    this.tipoRichiesta = this.route.snapshot.queryParamMap.get('tipoRichiesta');

    this.myForm = this.fb.group({
      dataEmissione: new FormControl('', [Validators.required]),
      dataScadenza: new FormControl(''),
      esito: new FormControl('', [Validators.required]),
      numeroProtocolloInps: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      nomeFile:[{ value: '', disabled: true }],
    });

    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = data.idUtente
      }
    }, err => {
    });
  }
  provaChange(value){
    this.tipoEsito = value;
    console.log(this.tipoEsito);
    
    
  }
  confermaElaborazione() {
    this.loadedElaborazione = false; 
    this.error = false;
    this.success = false;
    let parseDate = this.myForm.controls.dataEmissione.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataEmissione.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataEmissione.value.getDate();
    let parseDate2 = this.myForm.controls.dataScadenza.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataScadenza.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataScadenza.value.getDate();
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
      this.myForm.controls.numeroProtocolloInps.value,
      null,
      null,
      null, this.datiRichiesta.flagUrgenza
    );
    this.numeroDomanda = this.datiRichiesta.numeroDomanda;

    if (this.tipoRichiesta == '1' && (this.myForm.controls.numeroProtocolloInps.value == '' || !this.myForm.controls.numeroProtocolloInps.value)) {
      this.error = true;
    }
    

    //SERVIZIO DI POST:
    if (this.error == false) {
   
      
      this.richiesteService.elaboraDurc(modifica, (this.file)? true: false).subscribe(data => {
        if (data.toString() !=null) {
          this.richiesteService.elaboraRichiesta(this.datiRichiesta).subscribe(data => {
            if (data.toString() == "0") {
              this.loadedElaborazione = true; 
              this.error = false;
              this.success = true;
            }
          });
         if(this.file){
           this.uploadFile(data,this.datiRichiesta.idTipoRichiesta , modifica);
         } else {
          setTimeout(() => {
            this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
          }, 4000);
         }

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
  }
  uploadFile(idTarget: any, idTipoRichiesta: any, richiesta: any){

    this.loadedFile = false; 
    // UPLOAD FILE
    this.richiesteService.salvaUploadDurc(this.file, this.file.name, this.idUtente, idTarget, this.numeroDomanda
        , idTipoRichiesta.toString(), this.nag, richiesta).subscribe(data=>{
      if(data){
        this.error = false;
        this.success = true;
        this.loadedFile= true; 
        setTimeout(() => {
          this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
        }, 4000);
        this.showMessageSuccess("Salvataggio del file eseguito con successo.");
      }
      else{
        this.loadedFile = true; 
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

  showMessageError(msg: string) {
    this.messageError = msg;
  }
  showMessageSuccess( msg: string) {
    this.messageSuccess = msg;
  }
  setFileNull(){
    this.file = null;
    this.myForm.get("nomeFile").setValue("")
  }
  handleFileInput(file: File) {
    this.file=file;
    //this.myForm.get("documento").setValue(file);
    this.myForm.get("nomeFile").setValue(file.name);

  }

  setDataScadenza() {
    let tempDate = new Date(this.myForm.controls.dataEmissione.value)
    if (this.tipoRichiesta == '1') {
      tempDate.setDate(tempDate.getDate() + 120);
    } else {
      tempDate.setMonth(tempDate.getMonth() + 6);
    }
    this.myForm.controls.dataScadenza.patchValue(tempDate);
  }

  reset() {
    this.myForm.setValue({
      dataEmissione: '',
      dataScadenza: '',
      esito: '',
      numeroProtocolloInps: '',
      nomeFile:''
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
