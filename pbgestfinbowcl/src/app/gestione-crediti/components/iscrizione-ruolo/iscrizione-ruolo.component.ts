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
import { IscrizioneRuoloDTO } from '../../commons/dto/iscrizione-ruolo-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component';


@Component({
  selector: 'app-iscrizione-ruolo',
  templateUrl: './iscrizione-ruolo.component.html',
  styleUrls: ['./iscrizione-ruolo.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class IscrizioneRuoloComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati:any;
  public myForm: FormGroup;

  iscrizione: Array<IscrizioneRuoloDTO> = new Array<IscrizioneRuoloDTO>();

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  historyDisplayedColumns: string[] = ['data', 'istruttore'];

  idUtente: string;

  idProgetto: string;

  isShowing: number = 1;
  noData: boolean = false;

  showError: boolean = false;
  errorMsg: string = "";

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";

  compHeight: string = "332px"

  today = new Date(); 
  
  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    public sharedService: SharedService,
    private fb: FormBuilder,
    //private handleExceptionService: HandleExceptionService,
    //private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      dataRichiestaIscrizione: new FormControl(null),
      nProtocollo: new FormControl(null, Validators.maxLength(30)),
      dataRichiestaDiscarico: new FormControl(null),
      nProtocolloDiscarico: new FormControl(null, Validators.maxLength(30)),
      dataIscrizioneRuolo: new FormControl(null),
      dataDiscarico: new FormControl(null),
      nProtDiscRegione: new FormControl(null, Validators.maxLength(30)),
      dataRichiestaSospensione: new FormControl(null),
      nProtocolloSospensione: new FormControl(null, Validators.maxLength(30)),
      capitaleIscrittoRuolo: new FormControl(null),
      agevolazioneIscrittaRuolo: new FormControl(null),
      // dataIscrizione: new FormControl(null),
      nProtocolloRegione: new FormControl(null, Validators.maxLength(30)),
      pagamento: new FormControl('I'),
      note: new FormControl(null)
    });



    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = String(data.idUtente);

        this.idProgetto = this.item;

        this.load();
      }
    }, err => {
      //this.spinner = false;
      //this.handleExceptionService.handleNotBlockingError(err);
    });

  }


/* recuperaFile(newItem) {
    this.allegati = newItem;
  } */


  action(action: string): void {

    if (action == "save") {

      this.compHeight = "332px";
      this.showError = false;
      this.isSuccVisible = false

      let formControls = this.myForm.getRawValue();

      if (this.myForm.invalid) {

        return;

      }else if ((formControls.nProtocollo == null || formControls.nProtocollo == "") &&
        (formControls.nProtocolloDiscarico == null || formControls.nProtocolloDiscarico == "") &&
        (formControls.nProtDiscRegione == null || formControls.nProtDiscRegione == "") &&
        (formControls.nProtocolloSospensione == null || formControls.nProtocolloSospensione == "") &&
        (formControls.capitaleIscrittoRuolo == null || formControls.capitaleIscrittoRuolo == "") &&
        (formControls.agevolazioneIscrittaRuolo == null || formControls.agevolazioneIscrittaRuolo == "") &&
        (formControls.nProtocolloRegione == null || formControls.nProtocolloRegione == "") &&
        (formControls.note == null || formControls.note == "") &&
        (formControls.dataRichiestaIscrizione == null || formControls.dataRichiestaIscrizione == "") &&
        (formControls.dataRichiestaDiscarico == null || formControls.dataRichiestaDiscarico == "") &&
        (formControls.dataIscrizioneRuolo == null || formControls.dataIscrizioneRuolo == "") &&
        (formControls.dataDiscarico == null || formControls.dataDiscarico == "") &&
        (formControls.dataRichiestaSospensione == null || formControls.dataRichiestaSospensione == "")){
        // (formControls.dataIscrizione == null || formControls.dataIscrizione == "")) 
        this.compHeight = "246px";
        this.errorMsg = "Almeno un campo deve essere valorizzato";
        this.showError = true;
        //document.querySelector('#scrollId').scrollIntoView();


      } else if (this.myForm.controls.nProtocollo.errors?.maxlength ||
                this.myForm.controls.nProtocolloDiscarico.errors?.maxlength ||
                this.myForm.controls.nProtDiscRegione.errors?.maxlength ||
                this.myForm.controls.nProtocolloSospensione.errors?.maxlength ||
                this.myForm.controls.nProtocolloRegione.errors?.maxlength) {

        this.compHeight = "246px";
        this.errorMsg = "I protocolli devono contenere max 30 caratteri"
        this.showError = true;
        //document.querySelector('#scrollId').scrollIntoView();

      } else if(!this.myForm.dirty) {

        this.compHeight = "246px";
        this.errorMsg = "Modificare almeno un campo"
        this.showError = true;

      } else {

        this.compHeight = "332px";
        this.showError = false;
        this.isSuccVisible = false;

        let newIscrizione: IscrizioneRuoloDTO = new IscrizioneRuoloDTO();

        //console.log("fc :", formControls);

        newIscrizione.idUtente = this.idUtente;
        newIscrizione.idProgetto = this.item;

        if (this.iscrizione[0]?.idCurrentRecord) {
          newIscrizione.idCurrentRecord = this.iscrizione[0].idCurrentRecord;
        }

        newIscrizione.dataRichiestaIscrizione = formControls.dataRichiestaIscrizione;
        newIscrizione.dataRichiestaDiscarico = formControls.dataRichiestaDiscarico;
        newIscrizione.dataIscrizioneRuolo = formControls.dataIscrizioneRuolo;
        newIscrizione.dataDiscarico = formControls.dataDiscarico;
        newIscrizione.dataRichiestaSospensione = formControls.dataRichiestaSospensione;
        // newIscrizione.dataIscrizione = formControls.dataIscrizione;
        newIscrizione.numProtocollo = formControls.nProtocollo;
        newIscrizione.numProtocolloDiscarico = formControls.nProtocolloDiscarico;

        if (formControls.nProtDiscRegione != null && formControls.nProtDiscRegione != "") {
          newIscrizione.numProtoDiscReg = formControls.nProtDiscRegione;
        }

        newIscrizione.numProtoSosp = formControls.nProtocolloSospensione;

        newIscrizione.capitaleRuolo = this.sharedService.getNumberFromFormattedString(formControls.capitaleIscrittoRuolo);

        newIscrizione.agevolazioneRuolo = this.sharedService.getNumberFromFormattedString(formControls.agevolazioneIscrittaRuolo);
        newIscrizione.numProtoReg = formControls.nProtocolloRegione;
        newIscrizione.tipoPagamento = formControls.pagamento;

        if (formControls.note != null && formControls.note != "") {
          newIscrizione.note = formControls.note;
        }

        //console.log("newIsc: ", newIscrizione);

        this.isShowing = 4;
//this.resService.setIscrizioneRuolo(newIscrizione,this.allegati,this.idModalitaAgevolazione).subscribe((data) => {
        this.resService.inserisciIscrizioneRuolo(newIscrizione, this.idModalitaAgevolazione).subscribe((data) => {
          if (data) {

            this.isSuccVisible = true;
            //this.snackBar.open("Salvato", "Chiudi", { duration: 3000, });
            this.load();
          }
        }, err => {
          this.isShowing = 2;
          this.compHeight = "246px";
          this.errorMsg = "Si è verificato un problema nel salvataggio dei dati";
          this.showError = true;
          //document.querySelector('#scrollId').scrollIntoView();
          //this.handleExceptionService.handleBlockingError(err);
          //this.snackBar.open("C'è stato un problema nel salvataggio di dati", "Chiudi", { duration: 3000, });
        });
      }
    } else {
      this.setForm();
      //this.isShowing = 1;

      this.compHeight = "332px";
      this.isSuccVisible = false;
      this.showError = false;

      if (!this.noData) { this.isShowing = 1 }
    }
  }

  openHistory(): void {

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '500px',
      data: {
        id: 5,
        iscrizione: this.iscrizione
      }
    });

    this.isSuccVisible = false;
  }


  setForm() {

    if (!this.noData) {

      if (this.iscrizione[0].dataRichiestaIscrizione != null) {
        this.myForm.patchValue({
          dataRichiestaIscrizione: new Date(this.iscrizione[0].dataRichiestaIscrizione)
        });
      }

      if (this.iscrizione[0].dataRichiestaDiscarico != null) {
        this.myForm.patchValue({
          dataRichiestaDiscarico: new Date(this.iscrizione[0].dataRichiestaDiscarico)
        });
      }

      if (this.iscrizione[0].dataIscrizioneRuolo != null) {
        this.myForm.patchValue({
          dataIscrizioneRuolo: new Date(this.iscrizione[0].dataIscrizioneRuolo)
        });
      }

      if (this.iscrizione[0].dataDiscarico != null) {
        this.myForm.patchValue({
          dataDiscarico: new Date(this.iscrizione[0].dataDiscarico)
        });
      }

      if (this.iscrizione[0].dataRichiestaSospensione != null) {
        this.myForm.patchValue({
          dataRichiestaSospensione: new Date(this.iscrizione[0].dataRichiestaSospensione)
        });
      }

      // if (this.iscrizione[0].dataIscrizione != null) {
      //   this.myForm.patchValue({
      //     dataIscrizione: new Date(this.iscrizione[0].dataIscrizione)
      //   });
      // }

      if (this.iscrizione[0].capitaleRuolo != null) {
        this.myForm.patchValue({ capitaleIscrittoRuolo: this.sharedService.formatValue(String(this.iscrizione[0].capitaleRuolo)) });
      }

      this.myForm.patchValue({
        nProtocollo: this.iscrizione[0].numProtocollo,
        nProtocolloDiscarico: this.iscrizione[0].numProtocolloDiscarico,
        nProtDiscRegione: this.iscrizione[0].numProtoDiscReg,
        nProtocolloSospensione: this.iscrizione[0].numProtoSosp,
        agevolazioneIscrittaRuolo: this.iscrizione[0].agevolazioneRuolo,
        nProtocolloRegione: this.iscrizione[0].numProtoReg,
        pagamento: this.iscrizione[0].tipoPagamento,
        note: this.iscrizione[0].note
      });

    } else {

      this.myForm.patchValue({
        nProtocollo: null,
        nProtocolloDiscarico: null,
        nProtDiscRegione: null,
        nProtocolloSospensione: null,
        capitaleIscrittoRuolo: null,
        agevolazioneIscrittaRuolo: null,
        nProtocolloRegione: null,
        pagamento: "I",
        note: null,
        dataRichiestaIscrizione: null,
        dataRichiestaDiscarico: null,
        dataIscrizioneRuolo: null,
        dataDiscarico: null,
        dataRichiestaSospensione: null,
        // dataIscrizione: null
      });

    }
  }


  load() {
    // To populate the page on loading
    this.isShowing = 4;

    this.resService.getIscrizioneRuolo(this.item, this.idModalitaAgevolazione).subscribe((data) => {

      if (data.length != 0) {

        this.iscrizione = data;

        this.noData = false;
        //console.log("iscr: ", data);

        this.setForm();

        //console.log("Id: ", this.escussione[0].idCurrentRecord)
        this.isShowing = 1

      } else {
        this.noData = true;
        this.isShowing = 2
      }
    }, err => {
      //this.handleExceptionService.handleBlockingError(err);
      //this.snackBar.open("nan", "Chiudi", { duration: 3000, });
    });
  }

  maxVal(event: number) {

    if (event > 100) {
      this.myForm.patchValue({nProtocollo: 100});
    } else if (event < 0) {
      this.myForm.patchValue({nProtocollo: 0});
    }
  }

  /*omit(event) {
    var key;
    key = event.charCode;
    return ((key > 47 && key < 58) || (key > 64 && key < 91) || (key > 96 && key < 123) || key == 45 || key == 46 || key == 95);
  }*/
  
  get nProtocollo() {
    return this.myForm.get('nProtocollo');
  } 

}
