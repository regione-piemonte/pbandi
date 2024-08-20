/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { MY_FORMATS } from '@pbandi/common-lib';
import { Observable, Subscriber } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { TouchSequence } from 'selenium-webdriver';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { NuovaRichiesta } from 'src/app/gestione-richieste/commons/dto/nuova-richiesta';
import { RichiesteService } from 'src/app/gestione-richieste/services/richieste.service';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DialogGestioneRichiesteComponent } from '../../dialog-gestione-richieste/dialog-gestione-richieste.component';

interface Richiesta {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-inserisci-richiesta',
  templateUrl: './inserisci-richiesta.component.html',
  styleUrls: ['./inserisci-richiesta.component.scss']
})
export class InserisciRichiestaComponent implements OnInit {
  handleExceptionService: any;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private richiesteService: RichiesteService,
    private fb: FormBuilder,
    private userService: UserService,
    public dialog: MatDialog,
  ) { }

  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE;
  spinner: boolean = false;

  //FORM
  public myForm: FormGroup;
  public myDate: FormGroup;

  //LISTE PER LE SUGGEST
  suggCodFis: string[] = [];
  suggDomanda: string[] = [];
  suggBando: string[] = [];
  suggProgetto: string[] = [];

  //TRUE = ORDINARIA
  flagUrgenza: boolean = true;
  antimafia: boolean = false;
  idUtente: any;

  //CHECK PER DISABILITARE I BOTTONI
  codFisIns: any;
  domandaIns: any;
  bandoIns: any;
  progettoIns: any;

  //OPZIONI PER IL TIPO RICHIESTE
  richieste: Richiesta[] = [
    { value: '1', viewValue: 'DURC' },
    { value: '2', viewValue: 'DSAN in assenza di DURC' },
    { value: '3', viewValue: 'Antimafia' },
  ];
  subscribers: any = {};
  //TODO MARTI

  //DATEPICKER VALIDATOR
  today: Date;
  myFilter = (d: Date | null): boolean => {
    return d < this.today;
  }

  //BOOLEAN PER ALERT PER SUCCESSO O FALLIMENTO
  success: boolean = false;
  error: boolean = false;
  esitoDTO: EsitoDTO = new EsitoDTO(null, null, null); idStatoPrecedenteControllo: number;
  ngOnInit() {
    this.today = new Date();
    this.myForm = this.fb.group({
      idTipoRichiesta: new FormControl('', [Validators.required]),
      codiceFiscale: new FormControl('', [Validators.required]),
      codiceBando: new FormControl('', [Validators.required]),
      numeroDomanda: new FormControl('', [Validators.required]),
      codiceProgetto: new FormControl(''),
      urgenza: new FormControl(''),
    });

    this.myDate = this.fb.group({
      dataRichiesta: new FormControl(this.today)
    });

    this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = data.idUtente
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  sugg(id: number, value: string) {
    if (id == 1) {

      if (value.length >= 3) {
        this.suggCodFis = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 1).subscribe(data => { if (data.length > 0) { this.suggCodFis = data } else { this.suggCodFis = ["Nessuna corrispondenza"] } })
      } else { this.suggCodFis = [] }

    } else if (id == 2) {

      if (value.length >= 3) {
        this.suggDomanda = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 2).subscribe(data => { if (data.length > 0) { this.suggDomanda = data } else { this.suggDomanda = ["Nessuna corrispondenza"] } })
      } else { this.suggDomanda = [] }

    } else if (id == 3) {

      if (value.length >= 3) {
        this.suggBando = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 3).subscribe(data => { if (data.length > 0) { this.suggBando = data } else { this.suggBando = ["Nessuna corrispondenza"] } })
      } else { this.suggBando = [] }

    } else if (id == 5) {
      if (value.length >= 3) {
        this.suggProgetto = ["Caricamento..."];
        this.richiesteService.getSuggestion(value, 5).subscribe(data => {
          if (data.length > 0) { this.suggProgetto = data } else { this.suggProgetto = ["Nessuna corrispondenza"] }
        })
      } else { this.suggProgetto = [] }
    }
  }

  reset() {
    this.myForm = this.fb.group({
      idTipoRichiesta: new FormControl('', [Validators.required]),
      codiceFiscale: new FormControl('', [Validators.required]),
      codiceBando: new FormControl('', [Validators.required]),
      numeroDomanda: new FormControl('', [Validators.required]),
      codiceProgetto: new FormControl(''),
      urgenza: new FormControl(''),
    });
    /* this.myForm.setValue({
      idTipoRichiesta: '',
      numeroDomanda: "",
      codiceProgetto: "",
      codiceBando: "",
      codiceFiscale: "",
    }); */
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  resetTranneCf() {
    this.myForm.controls.codiceBando.patchValue("");
    this.myForm.controls.numeroDomanda.patchValue("");
    this.myForm.controls.codiceProgetto.patchValue("");
  }

  checkTipoRichiesta(idTipoRichiesta) {
    if (idTipoRichiesta == '3') {
      this.antimafia = true;
      this.myForm.get('urgenza').setValue('2');
    } else {
      this.antimafia = false;
      this.myForm.get('urgenza').setValue('2');
    }
  }

  cambiaFlag(value) {
    if (this.myForm.controls.idTipoRichiesta.value == '3' && value == 1 && this.flagUrgenza == true) {
      this.flagUrgenza = !this.flagUrgenza;
    } else if (this.myForm.controls.idTipoRichiesta.value == '3' && value == 2 && this.flagUrgenza == false) {
      this.flagUrgenza = !this.flagUrgenza;
      console.log("tipo cambiato");

    }
  }

  inserisciRichiesta() {
    let flag: string;
    this.error = false;
    this.success = false;

    if (this.flagUrgenza == false) {
      flag = "S"
    } else {
      flag = "N"
    }

    let nuovaRichiesta = new NuovaRichiesta(
      this.myForm.controls.idTipoRichiesta.value,
      this.idUtente,
      this.myForm.controls.numeroDomanda.value,
      flag,
      this.myForm.controls.codiceFiscale.value,
      this.myForm.controls.codiceBando.value,
      this.myForm.controls.codiceProgetto.value
    );

    // if (nuovaRichiesta.codiceFiscale.toString() != "Nessuna corrispondenza") {
    //   if (!this.suggCodFis.find(e => e === nuovaRichiesta.codiceFiscale.toString())) {
    //     this.error = true;
    //   }
    // } else if (nuovaRichiesta.codiceFiscale.toString() == "Nessuna corrispondenza") {
    //   this.error = true;
    // }

    // if (nuovaRichiesta.numeroDomanda.toString() != "Nessuna corrispondenza") {
    //   if (!this.suggDomanda.find(e => e === nuovaRichiesta.numeroDomanda.toString())) {
    //     this.error = true;
    //   }
    // } else if (nuovaRichiesta.numeroDomanda.toString() == "Nessuna corrispondenza") {
    //   this.error = true;
    // }

    // if (nuovaRichiesta.codiceBando.toString() != "Nessuna corrispondenza") {
    //   if (!this.suggBando.find(e => e === nuovaRichiesta.codiceBando.toString())) {
    //     this.error = true;
    //   }
    // } else if (nuovaRichiesta.codiceBando.toString() == "Nessuna corrispondenza") {
    //   this.error = true;
    // }

    // if (nuovaRichiesta.codiceProgetto) {
    //   if (nuovaRichiesta.codiceProgetto.toString() != "Nessuna corrispondenza") {
    //     if (!this.suggProgetto.find(e => e === nuovaRichiesta.codiceProgetto.toString())) {
    //       this.error = true;
    //     }
    //   } else if (nuovaRichiesta.codiceProgetto.toString() == "Nessuna corrispondenza") {
    //     this.error = true;
    //   }

    // }

    console.log(nuovaRichiesta);

    console.log(this.error);


    if (this.error == false) {
      this.richiesteService.insertRichiesta(nuovaRichiesta).subscribe(data => {
        if (data) {
          this.esitoDTO = data;
          this.error = false;
          if (this.esitoDTO.esito == true) {
            this.success = true;
            setTimeout(() => {
              this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
            }, 3000);
          } else {
            if (this.esitoDTO.id > 1) {
              let dialogRef = this.dialog.open(DialogGestioneRichiesteComponent, {
                width: '30%',
                data: {
                  element: 1 ,// conferma annulla richiesta antimafia: 
                  title: 'Richiesta Antimafia',
                  message: this.esitoDTO.messaggio
                }
              })
              dialogRef.afterClosed().subscribe(data => {
                if (data) {
                  console.log(data);
                  this.subscribers.annullaRichiestaAntimafia =  this.richiesteService.annullaRichiestaAntimafia(this.esitoDTO.id).subscribe(data =>{
                    if(data){
                      this.showMessageSuccess("Richiesta antimafia cancellata con successo!");
                      this.timeOutMessage();
                    } else{
                      this.showMessageSuccess("la richiesta antimafia non è stata cancellata!");
                      this.timeOutMessage();
                    }
                  }); 
                  
                } 
              });

            } else {
              this.showMessageError(this.esitoDTO.messaggio);
              this.timeOutMessage();
            }

          }
        } else if (data.toString() == "0") {
          this.error = true;
          this.success = false;
        }
      });
    }
  }
  timeOutMessage(){

    setTimeout(() => {
      this.resetMessageError(); 
      this.resetMessageSuccess(); 
    }, 4000);

  }

  //FUNZIONE PER LA VALIDAZIONE
  public hasError = (controlName: string, errorName: string) => {
    return this.myForm.controls[controlName].hasError(errorName);
  }

  goBack() {
    this.router.navigate(["/drawer/" + this.idOp + "/gestioneRichieste"], { queryParams: {} });
  }

}
