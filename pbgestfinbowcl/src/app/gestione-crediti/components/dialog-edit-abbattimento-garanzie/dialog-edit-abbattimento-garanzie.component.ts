/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AbbattimentoGaranzieVO } from '../../commons/dto/abbattimento-garanzieVO';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { StoricoAbbattimentoGaranziaDTO } from '../../commons/dto/storico-abbattimento-garanzia-dto';
import { AbbattimentoGaranzieService } from '../../services/abbattimento-garanzie.service';


@Component({
  selector: 'app-dialog-edit-abbattimento-garanzie',
  templateUrl: './dialog-edit-abbattimento-garanzie.component.html',
  styleUrls: ['./dialog-edit-abbattimento-garanzie.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditAbbattimentoGaranzieComponent implements OnInit {
  today = new Date(); 
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  allegati: any;
  user: UserInfoSec;
  idUtente: number;
  abbattimentoGaranzieVO: AbbattimentoGaranzieVO = new AbbattimentoGaranzieVO();
  isStorico: boolean = false;
  isStorico2: boolean = false;
  isAttiva: boolean = true;
  listaAttivitaAbbattimentoGaranzia: Array<AttivitaDTO>;
  
  tipoGaranzia: string;
  storicoAbbattimentoGaranzia: Array<StoricoAbbattimentoGaranziaDTO>;
  storicoAbbatimenti: Array<StoricoAbbattimentoGaranziaDTO>;
  subscribers: any = {};
  idAbbattimentoGaranzia: number;
  dataAbbattimento: Date;
  idAttivitaGaranzie: number;
  importoIniziale: number;
  importoLiberato: number;
  importoNuovo: number;
  note: string;
  descAttivita: string;
  isConferma: boolean;
  isImportoControl: boolean = false;
  isSalvato: boolean;
  dataAbbattimentoControl = new FormControl('', Validators.required);
  attivitaAbbattimentoControl = new FormControl('', Validators.required);
  attivitaDTO: AttivitaDTO;
  isNew: boolean = true;
  messageError: string = " ATTENZIONE! È necessario indicare i campi obbligatori: data e tipo garanzia ";
  isModifica: boolean;
  importoInizialeFormatted: any;
  importoLiberatoFormatted: string;
  importoNuovoFormatted: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private abbattimentoGaranzieService: AbbattimentoGaranzieService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditAbbattimentoGaranzieComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {


    this.idAbbattimentoGaranzia = this.data.idAbbattimentoGaranzia;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
      this.subscribers.listaAttivita = this.abbattimentoGaranzieService.getListaAttivitaAbbattimenti().subscribe(data => {
        if (data) {
          this.listaAttivitaAbbattimentoGaranzia = data;
        }
      });

      if (this.data.idAbbattimentoGaranzia != null) {
        this.isNew = false;
        this.subscribers.abbattimento = this.abbattimentoGaranzieService.getAbbattimento(this.idUtente, this.idProgetto, this.idAbbattimentoGaranzia, this.idModalitaAgevolazione).subscribe(data => {
          if (data) {
            this.abbattimentoGaranzieVO = data;
            this.dataAbbattimento = this.abbattimentoGaranzieVO.dataAbbattimGaranzie;
            this.idAttivitaGaranzie = this.abbattimentoGaranzieVO.idAttivitaGaranzie;
            this.importoIniziale = this.abbattimentoGaranzieVO.impIniziale;
            if (this.importoIniziale != null) {
              this.importoInizialeFormatted = this.sharedService.formatValue(this.importoIniziale.toString());
            }
            this.importoLiberato = this.abbattimentoGaranzieVO.impLiberato;
            if (this.importoLiberato != null) {
              this.importoLiberatoFormatted = this.sharedService.formatValue(this.importoLiberato.toString());
            }
            this.importoNuovo = this.abbattimentoGaranzieVO.impNuovo;
            if(this.importoNuovo!=null){
              this.importoNuovoFormatted=this.sharedService.formatValue(this.importoNuovo.toString()); 
            }
            this.note = this.abbattimentoGaranzieVO.note;


            // this.descAttivita = this.getDescAttivita(this.idAttivitaGaranzie);
          }
        })
      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });



  }

  /* recuperaFile(newItem) {
    this.allegati = newItem;
  } */
  
  setDataNull(){
    this.dataAbbattimento = null; 
    //this.dataAbbattimentoControl = null;
  }
  salvaAbbattimento() {

    if (this.abbattimentoGaranzieVO.dataAbbattimGaranzie != this.dataAbbattimento
      || this.abbattimentoGaranzieVO.idAttivitaGaranzie != this.idAttivitaGaranzie
      || this.abbattimentoGaranzieVO.impIniziale != this.importoIniziale
      || this.abbattimentoGaranzieVO.impLiberato != this.importoLiberato
      || this.importoNuovo != this.abbattimentoGaranzieVO.impNuovo
      || this.note != this.abbattimentoGaranzieVO.note) {

      this.abbattimentoGaranzieVO = new AbbattimentoGaranzieVO();
      this.abbattimentoGaranzieVO.impLiberato = this.importoLiberato;
      this.abbattimentoGaranzieVO.impIniziale = this.importoIniziale;
      this.abbattimentoGaranzieVO.impNuovo = this.importoNuovo;
      this.abbattimentoGaranzieVO.dataAbbattimGaranzie = this.dataAbbattimento;
      this.abbattimentoGaranzieVO.idAttivitaGaranzie = this.idAttivitaGaranzie;
      this.abbattimentoGaranzieVO.note = this.note;
      this.abbattimentoGaranzieVO.idAbbattimGaranzie = this.idAbbattimentoGaranzia;

      if (this.data.idAbbattimentoGaranzia != null) {
        this.subscribers.modificaAbbattimento = this.abbattimentoGaranzieService
// .modificaAbbattimento(this.abbattimentoGaranzieVO,this.data.idAbbattimentoGaranzia, this.allegati, this.idProgetto, this.idModalitaAgevolazione ).subscribe(data => {
          .modificaAbbattimento(this.abbattimentoGaranzieVO,this.idUtente,this.idProgetto,this.data.idAbbattimentoGaranzia, this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          })
      } else {
        this.subscribers.insertAbbattimento = this.abbattimentoGaranzieService
// .insertAbbattimento(this.abbattimentoGaranzieVO,this.allegati, this.idProgetto, this.idModalitaAgevolazione ).subscribe(data => {
          .insertAbbattimento(this.abbattimentoGaranzieVO,this.idUtente, this.idProgetto, this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          })
      }
    } else {
      this.isModifica = true;
    }

  }

  controllaCampi() {

    var regex = /^\d+(?:\.\d{1,2})?$/;

    if (this.dataAbbattimento == null || this.idAttivitaGaranzie == null) {
      this.isConferma = true;
    } else if (
      ((this.importoIniziale != null && this.importoIniziale.toString().length > 13)
        || (this.importoIniziale != null && !this.importoIniziale.toString().match(regex)))
      || ((this.importoLiberato != null && this.importoLiberato.toString().length > 13)
        || (this.importoLiberato != null && !this.importoLiberato.toString().match(regex)))
      || ((this.importoNuovo != null && this.importoNuovo.toString().length > 13)
        || (this.importoNuovo != null && !this.importoNuovo.toString().match(regex)))
    ) {
      this.isImportoControl = true;
      console.log("Controlla campi");
    } else {
      this.salvaAbbattimento();
      this.isConferma = false
    }

  }

  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.importoIniziale = this.sharedService.getNumberFromFormattedString(this.importoInizialeFormatted);
        if (this.importoIniziale !== null) {
          this.importoInizialeFormatted = this.sharedService.formatValue(this.importoIniziale.toString());
        }
        break;
      case 2:
        this.importoLiberato = this.sharedService.getNumberFromFormattedString(this.importoLiberatoFormatted);
        if (this.importoLiberato !== null) {
          this.importoLiberatoFormatted = this.sharedService.formatValue(this.importoLiberato.toString());
        }
        break
      case 3:
        this.importoNuovo = this.sharedService.getNumberFromFormattedString(this.importoNuovoFormatted);
        if (this.importoNuovo !== null) {
          this.importoNuovoFormatted = this.sharedService.formatValue(this.importoNuovo.toString());
        }
        break;
      default:
        break;
    }
  }
  closeDialog() {
    this.dialogRef.close();
  }


}
