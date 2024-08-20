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
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-elabora-bdna',
  templateUrl: './elabora-bdna.component.html',
  styleUrls: ['./elabora-bdna.component.scss']
})
export class ElaboraBdnaComponent implements OnInit {
  messageError: string;
  messageSuccess: string;
  loadedElaborazione: boolean = true;
  loadedFile: boolean = true;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private richiesteService: RichiesteService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private userService: UserService) { }


  //COSTANTE NECESSARIA AL ROUTING TRA COMPONENTI DA ACCODARE AL DRAWER COMPONENT
  idOp = Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE;
  spinner: boolean = false;

  //VARIABILI:
  idRichiesta: any;
  nag: any;
  datiRichiesta: Richiesta;
  idUtente: any;
  numeroDomanda: any;

  //FORM
  public myForm: FormGroup;

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  //VARIABILE PER IL BANNER
  error: boolean = false;
  success: boolean = false;

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

    console.log(sessionStorage.getItem('richiesta'));

    this.myForm = this.fb.group({
      dataRicezione: new FormControl('', [Validators.required]),
      numeroProtocolloRicevuta: new FormControl('', [Validators.required, Validators.maxLength(20)]),
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
    this.error = false;
    this.success = false;
    console.log("DATI RICHIESTA: ", this.datiRichiesta)
    let parseDate = this.myForm.controls.dataRicezione.value.getFullYear() + "-" + ("0" + (this.myForm.controls.dataRicezione.value.getMonth() + 1)).slice(-2) + "-" + this.myForm.controls.dataRicezione.value.getDate();
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
      null,
      null,
      null,
      null,
      parseDate,
      this.myForm.controls.numeroProtocolloRicevuta.value,
      null, this.datiRichiesta.flagUrgenza
    );
    this.numeroDomanda = this.datiRichiesta.numeroDomanda;
    //SERVIZIO DI POST:
    this.richiesteService.elaboraBdna(modifica, (this.file)? true: false).subscribe(data => {
      if (data.toString() != null) {
        this.richiesteService.elaboraRichiesta(this.datiRichiesta).subscribe(data => {
          if (data.toString() == "0") {
            this.error = false;
            this.success = true;
            this.loadedElaborazione = true; 
          }
        });
        //sessionStorage.removeItem("richiesta");
        this.error = false;
        this.success = true;
        if (this.file) {
          this.uploadFile(data, modifica);
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

  uploadFile(idTarget: any,  richiesta: any) {
  this.loadedFile = false; 
    // UPLOAD FILE
    this.richiesteService.salvaUploadBdna(this.file, this.file.name, this.idUtente, idTarget, this.numeroDomanda, this.nag, richiesta).subscribe(data => {
      if (data) {
        this.error = false;
        this.success = true;
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
  reset() {
    this.myForm.setValue({
      dataRicezione: '',
      numeroProtocolloRicevuta: '',
      nomeFile: '',
    });
  }
  isLoading() {
    if (!this.loadedElaborazione || !this.loadedFile) {
      return true;
    }
    return false;
  }
}
