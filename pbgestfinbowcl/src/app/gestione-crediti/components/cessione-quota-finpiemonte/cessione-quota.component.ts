/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject, Input } from '@angular/core';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { CessioneQuotaDTO } from '../../commons/dto/cessione-quota-fin';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component';



@Component({
  selector: 'app-cessione-quota-fin',
  templateUrl: './cessione-quota.component.html',
  styleUrls: ['./cessione-quota.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class CessioneQuotaFinComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati:any;
  spinner: boolean;

  today = new Date(); 
  public myForm: FormGroup;

  cessione: Array<CessioneQuotaDTO> = new Array<CessioneQuotaDTO>();

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  historyDisplayedColumns: string[] = ['data', 'istruttore'];

  stati: string[] = [];

  idUtente: string;

  idProgetto: string;

  isShowing: string = "main";
  //errorMsg = "";

  nodata: boolean = false;

  showError: boolean = false;
  errorMsg: string = "";

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";

  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    private fb: FormBuilder,
    public sharedService: SharedService,
    //private handleExceptionService: HandleExceptionService,
  ) { }

  ngOnInit(): void {

    this.isShowing = "loading";

    this.myForm = this.fb.group({
      cessQuoCap: new FormControl(null),
      cessOneri: new FormControl(null),
      cessInterMora: new FormControl(null),
      totCessione: new FormControl(null),
      dataCessione: new FormControl(null),
      corrCessione: new FormControl(null),
      nomCess: new FormControl(null),
      statoCess: new FormControl("-"),
      note: new FormControl(null)
    });



    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = String(data.idUtente);

        this.idProgetto = this.item;

        this.load();
      }
    }, err => {
      this.isShowing = "error"
      //this.errorMsg = "Si è verificato un problema nell'identificazione dell'utente"
      //this.handleExceptionService.handleNotBlockingError(err);
    });

  }


/* recuperaFile(newItem) {
    this.allegati = newItem;
  } */


  action(action: string): void {

    if (action == "save") {

      let formControls = this.myForm.getRawValue();

      if (this.myForm.invalid) {

        return;

      }else if ((formControls.dataCessione == null || formControls.dataCessione == "") &&
        (formControls.cessQuoCap == null || formControls.cessQuoCap == "") &&
        (formControls.cessOneri == null || formControls.cessOneri == "") &&
        (formControls.cessInterMora == null || formControls.cessInterMora == "") &&
        (formControls.totCessione == null || formControls.totCessione == "") &&
        (formControls.corrCessione == null || formControls.corrCessione == "") &&
        (formControls.nomCess == null || formControls.nomCess == "") &&
        (formControls.statoCess == null || formControls.statoCess == "-") &&
        (formControls.note == null || formControls.note == "")) {

        this.errorMsg = "Almeno un campo deve essere valorizzato";
        
        this.showError = true;
        //document.querySelector('#scrollId').scrollIntoView();

        //this.snackBar.open(, "Chiudi", { duration: 3000, });

      } else if (!this.myForm.dirty) {

        this.errorMsg = "Modificare almeno un campo";
        this.showError = true;

      } else {

        this.showError = false;
        this.isSuccVisible = false;

        let newCessione: CessioneQuotaDTO = new CessioneQuotaDTO();

        this.isShowing = "loading";

        //console.log("fc :", formControls);

        newCessione.idUtente = this.idUtente;
        newCessione.idProgetto = this.item;

        if (this.cessione[0]?.idCurrentRecord) {
          newCessione.idCurrentRecord = this.cessione[0].idCurrentRecord;
        }

        newCessione.impCessQuotaCap = this.sharedService.getNumberFromFormattedString(formControls.cessQuoCap);
        newCessione.impCessOneri = this.sharedService.getNumberFromFormattedString(formControls.cessOneri);
        newCessione.impCessInterMora = this.sharedService.getNumberFromFormattedString(formControls.cessInterMora);
        newCessione.impTotCess = this.sharedService.getNumberFromFormattedString(formControls.totCessione);
        newCessione.dataCessione = formControls.dataCessione;
        newCessione.corrispettivoCess = this.sharedService.getNumberFromFormattedString(formControls.corrCessione);
        newCessione.nominativoCess = formControls.nomCess;

        if (formControls.statoCess != "-") {
          newCessione.statoCess = formControls.statoCess;
        }

        newCessione.note = formControls.note;

 //this.resService.setCessioneQuota(newCessione,this.allegati,this.idModalitaAgevolazione).subscribe((data) => {
        this.resService.inserisciCessioneQuota(newCessione, this.idModalitaAgevolazione).subscribe((data) => {

          if (data) {

            this.isSuccVisible = true;
          }

          this.load();
        }, err => {
          this.load();
        });

      }
    } else {
      this.setForm();

      this.myForm.markAsPristine();

      this.isSuccVisible = false;
      this.showError = false;

      if (!this.nodata) { this.isShowing = "main" }
    }
  }

  openHistory(): void {

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '500px',
      data: {
        id: 6,
        cessione: this.cessione
      }
    });

    this.isSuccVisible = false;
  }

  setForm() {

    if (!this.nodata) {

      if (this.cessione[0].dataCessione != null) {
        this.myForm.patchValue({ dataCessione: new Date(this.cessione[0].dataCessione) });
      }

      if (this.cessione[0].impCessQuotaCap != null) {
        this.myForm.patchValue({ cessQuoCap: this.sharedService.formatValue(String(this.cessione[0].impCessQuotaCap)) });
      }

      if (this.cessione[0].impCessOneri != null) {
        this.myForm.patchValue({ cessOneri: this.sharedService.formatValue(String(this.cessione[0].impCessOneri)) });
      }

      if (this.cessione[0].impCessInterMora != null) {
        this.myForm.patchValue({ cessInterMora: this.sharedService.formatValue(String(this.cessione[0].impCessInterMora)) });
      }

      if (this.cessione[0].impTotCess != null) {
        this.myForm.patchValue({ totCessione: this.sharedService.formatValue(String(this.cessione[0].impTotCess)) });
      }

      if (this.cessione[0].corrispettivoCess != null) {
        this.myForm.patchValue({ corrCessione: this.sharedService.formatValue(String(this.cessione[0].corrispettivoCess)) });
      }


      this.myForm.patchValue({
        nomCess: this.cessione[0].nominativoCess,
        statoCess: this.cessione[0].statoCess,
        note: this.cessione[0].note
      });

    } else {

      this.myForm.patchValue({
        dataCessione: null,
        cessQuoCap: null,
        cessOneri: null,
        cessInterMora: null,
        totCessione: null,
        corrCessione: null,
        nomCess: null,
        statoCess: "-",
        note: null
      });
    }
  }


  load() {

    this.isShowing = "loading";

    this.resService.getCessioneQuota(this.item, this.idModalitaAgevolazione).subscribe((data) => {

      if (data.length != 0) {

        if (data[0].idCurrentRecord == null) {
          this.stati = data[0].listStati;

          this.isShowing = "edit";
          this.nodata = true
        } else {
          this.cessione = data;
          this.stati = data[0].listStati;

          this.isShowing = "main";
          this.nodata = false;
          this.setForm();
        }

        /*this.cessione = data;

        this.nodata = false;
        //console.log("cess: ", data);

        this.setForm();

        //console.log("Id: ", this.escussione[0].idCurrentRecord)
        this.isShowing = "main"*/
        
      } else {
        this.isShowing = "edit"
        this.nodata = true;
      }
    }, err => {
      this.isShowing = "error";
      //this.errorMsg = "errore nel caricamento dei dati dal DB";
      //this.handleExceptionService.handleBlockingError(err);
      //this.snackBar.open("nan", "Chiudi", { duration: 3000, });
    });
  }

}
