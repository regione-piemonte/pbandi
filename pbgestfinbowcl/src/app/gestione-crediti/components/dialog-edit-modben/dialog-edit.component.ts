/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { startWith, debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import { MatIconModule } from '@angular/material/icon';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatListModule } from '@angular/material/list';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DettaglioFinanziamentoErogato } from 'src/app/gestione-crediti/commons/dto/dettaglio-finanziamento-erogato';
import { CDK_CONNECTED_OVERLAY_SCROLL_STRATEGY_PROVIDER_FACTORY } from '@angular/cdk/overlay/overlay-directives';
import { MatDatepickerModule } from '@angular/material/datepicker';
//import { Tracing } from 'trace_events';
import { ModBenResponseService } from '../../services/modben-response-service.service';
import { SaveSchedaCliente } from 'src/app/gestione-crediti/commons/dto/save-scheda-cliente.all';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SuggestRatingVO } from '../../commons/dto/suggest-rating-VO';


@Component({
  selector: 'dialog-edit',
  templateUrl: './dialog-edit.component.html',
  styleUrls: ['./dialog-edit.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class EditDialogComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  schedaCliente: SchedaClienteMain = new SchedaClienteMain;

  today = new Date(); 

  public formStatoAzienda: FormGroup;
  public formCreditoFin: FormGroup;
  public formRating: FormGroup;
  public formBancaBen: FormGroup;

  staAz_idCurrentRecord: string;
  staCre_idCurrentRecord: string;
  rat_idCurrentRecord: string;
  banBen_idCurrentRecord: string;

  idComponentToShow: number;
  idSoggetto: string;
  idUtente: string

  // Se lo stato azienda passa da ATTIVA ad un altro stato, inserisco una proposta di revoca e creo un blocco.
  // Serve per controllare e comparare lo stato precedente.
  oldIdStatoAzienda: number = 0;
  oldStatoAzienda: string = "";

  isLoading = false;

  listBanche: Array<string> = []

  /*statoAzienda_statoAzienda: string;
  statoAzienda_dataInizio: any;
  statoAzienda_dataFine: any;*/
  statoAzienda_optionsStatoAzienda: Array<string> = []

  statoCredito_optionsStati: Array<string> = [];
  statoCredito_stato: string;
  statoCredito_dataModifica: any;

  //rating_optionsProviders: Array<string> = [];
  rating_optionsRating: Array<SuggestRatingVO> = [];
  rating_provider: string;
  rating_rating: string;
  rating_dataClassificazione: any;

  bancaBeneficiario_optionsBanche: Array<string> = [];
  bancaBeneficiario_optionsMotivazioni: Array<string> = ["Cessione soggetto terzo", "Altro"];
  bancaBeneficiario_banca: string;
  bancaBeneficiario_dataModifica: any;
  bancaBeneficiario_motivazione: string;
  bancaBeneficiario_soggTerzo: string = "";

  //SUBSCIRBERS
  subscribers: any = {};

  showError: boolean = false;
  errorMsg: string = "Oh no!"

  constructor(
    private responceService: ModBenResponseService,
    private fb: FormBuilder,
    //private snackBar: MatSnackBar,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.idComponentToShow = this.data.id;

    //console.log("dialogData:", this.data);

    this.idSoggetto = this.data.idSoggetto;
    this.idUtente = this.data.idUtente;

    this.schedaCliente = data.schedaCliente;

    this.staAz_idCurrentRecord = this.data.schedaCliente.stAz_idRecAtt; // TODO: stAz_idRecAtt non esiste, controllare

    if(this.idComponentToShow == 1) { // Se sto modificando lo stato azienda
      this.oldIdStatoAzienda = this.data.schedaCliente.staAz_idStatoAzienda;
      this.oldStatoAzienda = this.data.schedaCliente.statoAzienda;
    }


    //this.statoAzienda_optionsStatoAzienda = this.data.schedaCliente.stAz_statiAzienda;
    //console.log(this.data.schedaCliente.statoAzienda)
    //console.log(this.statoAzienda_optionsStatoAzienda);
    /*this.statoAzienda_statoAzienda = this.data.statoAzienda;
    this.statoAzienda_dataInizio = new FormControl(this.data.dataInizio);
    this.statoAzienda_dataFine = new FormControl(this.data.dataFine);*/

    this.statoCredito_optionsStati = data.listStatiCredito;
    this.statoCredito_stato = this.data.statocredFin;
    this.statoCredito_dataModifica = new FormControl();

    //this.rating_optionsProviders = data.rat_statiProvider;
    this.rating_optionsRating = data.schedaCliente.rating_ratings;
    this.rating_dataClassificazione = new FormControl();

    this.bancaBeneficiario_optionsBanche = data.baBen_banche;
    //console.log("opban: ", this.bancaBeneficiario_optionsBanche)
    //console.log("databan: ", data.baBen_banche)
    this.bancaBeneficiario_dataModifica = new FormControl();

  }



  ngOnInit(): void {
    //console.log("ban: ", this.bancaBeneficiario_optionsBanche)

    this.formStatoAzienda = this.fb.group({
      staAz_statoAzienda: new FormControl('', [Validators.required]),
      staAz_dataInizio: new FormControl('', [Validators.required]),
      staAz_dataFine: new FormControl(''),
    });

    this.formCreditoFin = this.fb.group({
      staCre_statoCredito: new FormControl('', [Validators.required]),
      staCre_dataModifica: new FormControl('', [Validators.required]),
    });

    this.formRating = this.fb.group({
      rat_provRating: new FormControl({value: '', disabled: true}),
      rat_rating: new FormControl('', [Validators.required]),
      rat_dataClass: new FormControl('', [Validators.required]),
    });

    this.formBancaBen = this.fb.group({
      ban_banca: new FormControl('', [Validators.required]),
      ban_dataModifica: new FormControl('', [Validators.required]),
      ban_motivazione: new FormControl('Altro'),
      ban_soggTerzo: new FormControl(''),
    });


    this.formStatoAzienda.patchValue({
      staAz_statoAzienda: this.schedaCliente.statoAzienda,
    });
    /*
        if (this.schedaCliente.staAz_dtInizioValidita != null) {
          this.formStatoAzienda.patchValue({
            staAz_dataInizio: new Date(this.schedaCliente.staAz_dtInizioValidita),
          });
        }
    
        if (this.schedaCliente.staAz_dtfineValidita != null) {
          this.formStatoAzienda.patchValue({
            staAz_dataFine: new Date(this.schedaCliente.staAz_dtfineValidita)
          });
        }
    */

    this.formCreditoFin.patchValue({
      staCre_statoCredito: this.schedaCliente.statoCredito,
      //staCre_dataModifica: null
    });


    this.formRating.patchValue({
      //rat_provRating: this.schedaCliente.provider,
      rat_rating: this.schedaCliente.rating,
    });

    /*if (this.schedaCliente.dataClassRating != null) {
      this.formRating.patchValue({
        rat_dataClass: new Date(this.schedaCliente.dataClassRating)
      });
    }*/


    /*this.formBancaBen.patchValue({
      ban_banca: this.schedaCliente.banca
    })

    if (this.idComponentToShow == 4) {
      this.isLoading = true
      this.subscribers.banche = this.responceService.getListBanche().subscribe(data => {
        if (data) {

          this.listBanche = data;
          //console.log("banche: ", this.listBanche);
          this.isLoading = false;
        }
      }, err => {
        this.isLoading = false;
        //this.snackBar.open("C'è stato un problema nel caricamento delle banche", "Chiudi", { duration: 3000, });
        this.handleExceptionService.handleBlockingError(err);
      });
    }*/

  }


  updateProvider(event: any) {
    console.log("Value: ", event);
    const selectedValue = event.value;
    const selectedOption = this.rating_optionsRating.find(option => option.idRating == event).descProvider;
    
    console.log("aft: ", selectedOption)
    this.formRating.get('rat_provRating').setValue(selectedOption);
  }

  onConfirmClick(idPls: any): void {

    //console.log(idPls)

    let newSchedaCliente: SaveSchedaCliente = new SaveSchedaCliente();

    switch (idPls) {
      case 1: { // SAVE STATO AZIENDA

        if (this.formStatoAzienda.valid) {

          let formControls = this.formStatoAzienda.getRawValue();

          let dataInizio = formControls.staAz_dataInizio.getFullYear() + "-" + ("0" + (formControls.staAz_dataInizio.getMonth() + 1)).slice(-2) + "-" + formControls.staAz_dataInizio.getDate();
          let dataFine = null;

          if (formControls.staAz_dataFine != "" && formControls.staAz_dataFine != null) {
            dataFine = formControls.staAz_dataFine.getFullYear() + "-" + ("0" + (formControls.staAz_dataFine.getMonth() + 1)).slice(-2) + "-" + formControls.staAz_dataFine.getDate();
          }

          newSchedaCliente.wtfImSaving = "1";
          newSchedaCliente.idSoggetto = this.schedaCliente.idSoggetto;
          newSchedaCliente.idUtente = this.idUtente;
          newSchedaCliente.staAz_idCurrentRecord = this.schedaCliente.staAz_currentId;
          newSchedaCliente.staAz_statoAzienda = formControls.staAz_statoAzienda;
          newSchedaCliente.staAz_dataInizio = dataInizio;
          newSchedaCliente.staAz_dataFine = dataFine;
          newSchedaCliente.staCre_idCurrentRecord = this.schedaCliente.staCre_currentId

          //console.log("before: ", newSchedaCliente)
          //this.responceService.setSchedaCliente(newSchedaCliente);

          // Se lo stato azienda passa da ATTIVA ad altro, inserisco una proposta di revoca e creo un blocco.
          let flagStatoAzienda: boolean = false

          console.log("oldIdStatoAzienda", this.oldIdStatoAzienda);
          console.log("oldStatoAzienda", this.oldStatoAzienda)
          console.log("formControls.staAz_statoAzienda", formControls.staAz_statoAzienda);

          if (this.oldIdStatoAzienda == 1 && formControls.staAz_statoAzienda != this.oldStatoAzienda) {
            flagStatoAzienda = true;
            console.log("flag: ", flagStatoAzienda);
          }

          this.dialogRef.close({

            save: "save",

            newSchedaCliente: newSchedaCliente,

            flagStatoAzienda: flagStatoAzienda
          });

        } else {
          this.showError = true;
          this.errorMsg = "È necessario indicare obbligatoriamente stato azienda e data inizio";
          //this.snackBar.open("È necessario indicare obbligatoriamente stato azienda e data inizio", "Chiudi", { duration: 3000, });
        }
        break;
      }
      case 2: { // SAVE STATO CREDITO

        if (this.formCreditoFin.valid) {

          let formControls = this.formCreditoFin.getRawValue();

          let dataModifica = formControls.staCre_dataModifica.getFullYear() + "-" + ("0" + (formControls.staCre_dataModifica.getMonth() + 1)).slice(-2) + "-" + formControls.staCre_dataModifica.getDate();

          newSchedaCliente.wtfImSaving = "2";
          newSchedaCliente.idSoggetto = this.schedaCliente.idSoggetto;
          newSchedaCliente.idUtente = this.idUtente;
          newSchedaCliente.staCre_idCurrentRecord = this.schedaCliente.staCre_currentId;
          newSchedaCliente.staCre_stato = formControls.staCre_statoCredito;
          newSchedaCliente.staCre_dataMod = dataModifica;
          newSchedaCliente.staCre_PROGR_SOGGETTO_PROGETTO = this.schedaCliente.progrSoggProg;

          //console.log("before: ", newSchedaCliente)

          //this.responceService.setSchedaCliente(newSchedaCliente);

          this.dialogRef.close({

            save: "save",

            newSchedaCliente: newSchedaCliente
          });
        } else {
          this.showError = true;
          this.errorMsg = "È necessario indicare obbligatoriamente stato credito e data modifica";
          //this.snackBar.open("È necessario indicare obbligatoriamente stato credito e data inizio", "Chiudi", { duration: 3000, });
        }

        break;
      }
      case 3: { // SAVE RATING

        if (this.formRating.valid) {

          let formControls = this.formRating.getRawValue();

          // let dataClass = formControls.rat_dataClass.getFullYear() + "-" + ("0" + (formControls.rat_dataClass.getMonth() + 1). 
          // slice(-2) + "-" + formControls.rat_dataClass.getDate());

          newSchedaCliente.wtfImSaving = "3";
          newSchedaCliente.idSoggetto = this.schedaCliente.idSoggetto;
          newSchedaCliente.idUtente = this.idUtente;
          newSchedaCliente.rat_idCurrentRecord = this.schedaCliente.rating_currentId;
          //newSchedaCliente.rat_provider = formControls.rat_provRating;
          newSchedaCliente.rat_idRating = formControls.rat_rating;
          
          newSchedaCliente.rat_dataClassificazione = formControls.rat_dataClass;

          //console.log("before: ", newSchedaCliente)

          //this.responceService.setSchedaCliente(newSchedaCliente);

          this.dialogRef.close({

            save: "save",

            newSchedaCliente: newSchedaCliente
          });

        } else {
          this.showError = true;
          this.errorMsg = "È necessario indicare obbligatoriamente rating e data classificazione";
          //this.snackBar.open("È necessario indicare obbligatoriamente provider, rating e data classificazione", "Chiudi", { duration: 3000, });
        }

        break;
      }
      case 4: { // SAVE BANCA

        if (!this.listBanche.includes(this.formBancaBen.controls.ban_banca.value) || this.formBancaBen.controls.ban_banca.value == "Caricamento..." || this.formBancaBen.controls.ban_banca.value == "Nessuna corrispondenza") {

          this.errorMsg = "Selezionare una banca valida dalla lista";
          this.showError = true;

        }else if (this.formBancaBen.valid && this.formBancaBen.controls.ban_motivazione.value != this.bancaBeneficiario_optionsMotivazioni[0]) {

          this.saveBanca(newSchedaCliente);

        } else if (this.formBancaBen.valid && this.formBancaBen.controls.ban_motivazione.value == this.bancaBeneficiario_optionsMotivazioni[0] && this.formBancaBen.controls.ban_soggTerzo.value != "") {

          this.saveBanca(newSchedaCliente);

        } else if (this.formBancaBen.valid && this.formBancaBen.controls.ban_motivazione.value == this.bancaBeneficiario_optionsMotivazioni[0] && this.formBancaBen.controls.ban_soggTerzo.value == "") {

          this.showError = true;
          this.errorMsg = "È necessario indicare obbligatoriamente il soggetto terzo";
          //this.snackBar.open("È necessario indicare obbligatoriamente il soggetto terzo", "Chiudi", { duration: 3000, });

        } else {

          this.showError = true;
          this.errorMsg = "È necessario indicare obbligatoriamente la banca e data modifica";
          //this.snackBar.open("È necessario indicare obbligatoriamente la banca e data modifica", "Chiudi", { duration: 3000, });

        }

        break;
      }
      default: {

        this.showError = false;

        console.log("there was an error")
        this.dialogRef.close({});

        break;
      }
    }

  }

  saveBanca(newSchedaCliente: SaveSchedaCliente) {

    let formControls = this.formBancaBen.getRawValue();

    let dataModifica = formControls.ban_dataModifica.getFullYear() + "-" + ("0" + (formControls.ban_dataModifica.getMonth() + 1)).slice(-2) + "-" + formControls.ban_dataModifica.getDate();

    newSchedaCliente.wtfImSaving = "4";
    newSchedaCliente.idSoggetto = this.schedaCliente.idSoggetto;
    newSchedaCliente.idUtente = this.idUtente;
    newSchedaCliente.banBen_banca = formControls.ban_banca;
    newSchedaCliente.banBen_idCurrentRecord = this.schedaCliente.banBen_currentId;
    newSchedaCliente.banBen_dataModifica = dataModifica;
    newSchedaCliente.benBen_motivazione = formControls.ban_motivazione;
    newSchedaCliente.banBen_soggettoTerzo = formControls.ban_soggTerzo;
    newSchedaCliente.banBen_PROGR_SOGGETTO_PROGETTO = this.schedaCliente.progrSoggProg;

    //console.log("before: ", newSchedaCliente)

    //this.responceService.setSchedaCliente(newSchedaCliente);

    this.dialogRef.close({

      save: "save",

      newSchedaCliente: newSchedaCliente
    });
  }


  sugg(value: string) {

    //console.log("val: ", value);

    if (value.length >= 3) {
      this.listBanche = ["Caricamento..."];
      this.responceService.getSuggestion(value).subscribe(data => { if (data.length > 0) { this.listBanche = data } else { this.listBanche = ["Nessuna corrispondenza"] } })
    } else { this.listBanche = [] }

  }


  closeDialog() {
    this.dialogRef.close();
  }
}
