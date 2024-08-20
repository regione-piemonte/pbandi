/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { SaldoStralcioVO } from '../../commons/dto/saldo-stralcio-VO';
import { SaldoStralcioService } from '../../services/saldo-stralcio-service';

@Component({
  selector: 'app-dialog-edit-saldo-stralcio',
  templateUrl: './dialog-edit-saldo-stralcio.component.html',
  styleUrls: ['./dialog-edit-saldo-stralcio.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditSaldoStralcioComponent implements OnInit {
  today = new Date();
  idModalitaAgevolazione: number;
  ambito = this.data.ambito;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  allegati: any;
  user: UserInfoSec;
  idUtente: number;
  saldoStralcioVO: SaldoStralcioVO = new SaldoStralcioVO();
  isAttiva: boolean = true;
  listaAttivitaSaldoStralcio: Array<AttivitaDTO>;
  listaAttivitaEsito: Array<AttivitaDTO>;
  listaAttivitaRecupero: Array<AttivitaDTO>;
  dataProposta: Date;
  dataAccettazione: Date;
  idAttivitaSaldoStralcio: number;
  importoDebitore: number;
  importoConfidi: number;
  idAttivitaEsito: number;
  dataEsito: Date;
  dataPagamDebitore: Date;
  dataPagamConfidi: Date;
  idAttivitaRecupero: number;
  importoRecuperato: number;
  importoPerdita: number;
  importoDisimpegno: number;
  note: string;
  tipoSaldoStralcio: string;
  flagAgevolazione: boolean = false;
  subscribers: any = {};
  isNew: boolean = true;


  descAttivita: string;
  isConferma: boolean;
  isSalvato: boolean;
  attivitaDTO: AttivitaDTO;
  attivitaDTO1: AttivitaDTO;
  attivitaDTO2: AttivitaDTO;
  isImportoControl: boolean;
  idSaldoStralcio: number;
  isCampiVuoti: boolean;
  isDataVuota: boolean;
  isModifica: boolean;
  importoDebitoreFormatted: string;
  importoConfidiFormatted: string;
  importoRecuperatoFormatted: string;
  importoPerditaFormatted: string;
  importoDisimpegnoFormatted: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private saldoStralcioService: SaldoStralcioService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditSaldoStralcioComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any

  ) { }

  ngOnInit(): void {
    
    //console.log("data dialog: ", this.data);

    this.idSaldoStralcio = this.data.idSaldoStralcio;
    this.idModalitaAgevolazione = this.data.idModalitaAgevolazione;
    
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
      this.subscribers.listaAttivitaSaldo = this.saldoStralcioService.getListaAttivitaSaldoStralcio().subscribe(data => {
        if (data) {
          this.listaAttivitaSaldoStralcio = data;
        }
      });
      this.subscribers.listaAttivitaEsito = this.saldoStralcioService.getListaAttivitaEsito().subscribe(data => {
        if (data) {
          this.listaAttivitaEsito = data;
        }
      });
      this.subscribers.listaAttivitaRecupero = this.saldoStralcioService.getListaAttivitaRecupero().subscribe(data => {
        if (data) {
          this.listaAttivitaRecupero = data;
        }
      });

      if (this.data.idSaldoStralcio != null) {
        this.isNew = false;
        this.subscribers.saldo = this.saldoStralcioService.getSaldoStralcio(this.idSaldoStralcio, this.idModalitaAgevolazione).subscribe(data => {
          if (data) {

            this.saldoStralcioVO = data;
            this.dataAccettazione = this.saldoStralcioVO.dataAcettazione;
            this.dataEsito = this.saldoStralcioVO.dataEsito;
            this.dataPagamConfidi = this.saldoStralcioVO.dataPagamConfidi;
            this.dataPagamDebitore = this.saldoStralcioVO.dataPagamDebitore;
            this.dataProposta = this.saldoStralcioVO.dataProposta;
            this.importoConfidi = this.saldoStralcioVO.impConfindi;
            if (this.importoConfidi != null) {
              this.importoConfidiFormatted = this.sharedService.formatValue(this.importoConfidi.toString());
            }
            this.importoDebitore = this.saldoStralcioVO.impDebitore;
            if (this.importoDebitore != null) {
              this.importoDebitoreFormatted = this.sharedService.formatValue(this.importoDebitore.toString());
            }
            this.importoPerdita = this.saldoStralcioVO.impPerdita;
            if (this.importoPerdita != null) {
              this.importoPerditaFormatted = this.sharedService.formatValue(this.importoPerdita.toString());
            }
            this.importoRecuperato = this.saldoStralcioVO.impRecuperato;
            if (this.importoRecuperato !== null) {
              this.importoRecuperatoFormatted = this.sharedService.formatValue(this.importoRecuperato.toString());
            }
            this.importoDisimpegno = this.saldoStralcioVO.impDisimpegno;
            if (this.importoDisimpegno != null) {
              this.importoDisimpegnoFormatted = this.sharedService.formatValue(this.importoDisimpegno.toString());
            }
            this.note = this.saldoStralcioVO.note;
            this.flagAgevolazione = this.saldoStralcioVO.flagAgevolazione;

            this.idAttivitaEsito = this.saldoStralcioVO.idAttivitaEsito;
            this.idAttivitaRecupero = this.saldoStralcioVO.idAttivitaRecupero;
            this.idAttivitaSaldoStralcio = this.saldoStralcioVO.idAttivitaSaldoStralcio;

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

  setDataNull(idData: number) {
    switch (idData) {
      case 1:
        this.dataProposta = null;
        break;
      case 2:
        this.dataAccettazione = null;
        break;
      case 3:
        this.dataEsito = null;
        break;
      case 4:
        this.dataPagamDebitore = null;
        break;
      case 5:
        this.dataPagamConfidi = null;
        break;

      default:
        break;
    }
  }

  controllaCampi() {
    var regex = /^\d+(?:\.\d{1,2})?$/;

    if(this.idModalitaAgevolazione == 10) {
      if (this.dataProposta == null && this.dataAccettazione == null && this.idAttivitaSaldoStralcio == null && this.importoDebitore == null
        && this.idAttivitaEsito == null && this.dataEsito == null && this.dataPagamDebitore == null && this.dataPagamConfidi == null
        && this.idAttivitaRecupero == null && this.importoRecuperato == null && this.importoPerdita == null && this.note == null && this.importoDisimpegno == null) {
        this.isCampiVuoti = true;
      } else {
        if (
          ((this.importoConfidi != null && this.importoConfidi.toString().length > 13)
            || (this.importoConfidi != null && !this.importoConfidi.toString().match(regex)))
          || ((this.importoDebitore != null && this.importoDebitore.toString().length > 13)
            || (this.importoDebitore != null && !this.importoDebitore.toString().match(regex)))
          || ((this.importoPerdita != null && this.importoPerdita.toString().length > 13)
            || (this.importoPerdita != null && !this.importoPerdita.toString().match(regex)))
          || ((this.importoDisimpegno != null && this.importoDisimpegno.toString().length > 13)
            || (this.importoDisimpegno != null && !this.importoDisimpegno.toString().match(regex)))
          || ((this.importoRecuperato != null && this.importoRecuperato.toString().length > 13)
            || (this.importoRecuperato != null && !this.importoRecuperato.toString().match(regex)))
        ) {

          this.isImportoControl = true;
        } else if (this.idAttivitaEsito != null && this.dataEsito == null) {
          this.isDataVuota = true;
          this.isCampiVuoti = false;
        } else {
          this.salvaSaldoStralcio();
          this.isConferma = false;
        }

      }
    } else {
      if (this.dataProposta == null && this.dataAccettazione == null && this.idAttivitaSaldoStralcio == null && this.importoDebitore == null
        && this.idAttivitaEsito == null && this.dataEsito == null && this.dataPagamDebitore == null && this.dataPagamConfidi == null
        && this.idAttivitaRecupero == null && this.importoRecuperato == null && this.importoPerdita == null && this.note == null) {
        this.isCampiVuoti = true;
      } else {
        if (
          ((this.importoConfidi != null && this.importoConfidi.toString().length > 13)
            || (this.importoConfidi != null && !this.importoConfidi.toString().match(regex)))
          || ((this.importoDebitore != null && this.importoDebitore.toString().length > 13)
            || (this.importoDebitore != null && !this.importoDebitore.toString().match(regex)))
          || ((this.importoPerdita != null && this.importoPerdita.toString().length > 13)
            || (this.importoPerdita != null && !this.importoPerdita.toString().match(regex)))

          || ((this.importoRecuperato != null && this.importoRecuperato.toString().length > 13)
            || (this.importoRecuperato != null && !this.importoRecuperato.toString().match(regex)))
        ) {

          this.isImportoControl = true;
        } else if (this.idAttivitaEsito != null && this.dataEsito == null) {
          this.isDataVuota = true;
          this.isCampiVuoti = false;
        } else {
          this.salvaSaldoStralcio();
          this.isConferma = false;
        }

      }
    }

    

  }

  salvaSaldoStralcio() {

    //console.log(this.saldoStralcioVO.flagAgevolazione, " - ", this.flagAgevolazione)

    if (this.saldoStralcioVO.dataAcettazione == this.dataAccettazione && this.saldoStralcioVO.dataEsito == this.dataEsito &&
      this.saldoStralcioVO.dataPagamConfidi == this.dataPagamConfidi && this.saldoStralcioVO.dataPagamDebitore == this.dataPagamDebitore
      && this.saldoStralcioVO.dataProposta == this.dataProposta && this.saldoStralcioVO.idAttivitaEsito == this.idAttivitaEsito &&
      this.saldoStralcioVO.idAttivitaRecupero == this.idAttivitaRecupero && this.saldoStralcioVO.idAttivitaSaldoStralcio == this.idAttivitaSaldoStralcio
      && this.saldoStralcioVO.impConfindi == this.importoConfidi && this.saldoStralcioVO.impDebitore == this.importoDebitore &&
      this.saldoStralcioVO.impPerdita == this.importoPerdita && this.saldoStralcioVO.impRecuperato == this.importoRecuperato
      && this.saldoStralcioVO.note == this.note && this.saldoStralcioVO.flagAgevolazione == this.flagAgevolazione && this.saldoStralcioVO.impDisimpegno == this.importoDisimpegno) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.saldoStralcioVO.dataAcettazione = this.dataAccettazione;
      this.saldoStralcioVO.dataEsito = this.dataEsito;
      this.saldoStralcioVO.flagAgevolazione = this.flagAgevolazione;
      this.saldoStralcioVO.dataPagamConfidi = this.dataPagamConfidi;
      this.saldoStralcioVO.dataPagamDebitore = this.dataPagamDebitore;
      this.saldoStralcioVO.dataProposta = this.dataProposta;
      this.saldoStralcioVO.idAttivitaEsito = this.idAttivitaEsito;
      this.saldoStralcioVO.idAttivitaRecupero = this.idAttivitaRecupero;
      this.saldoStralcioVO.idAttivitaSaldoStralcio = this.idAttivitaSaldoStralcio;
      this.saldoStralcioVO.idProgetto = this.idProgetto;
      this.saldoStralcioVO.impConfindi = this.importoConfidi;
      this.saldoStralcioVO.impDebitore = this.importoDebitore;
      this.saldoStralcioVO.impPerdita = this.importoPerdita;
      this.saldoStralcioVO.impRecuperato = this.importoRecuperato;
      this.saldoStralcioVO.impDisimpegno = this.importoDisimpegno;
      this.saldoStralcioVO.note = this.note;

      if (this.data.idSaldoStralcio != null) {
        this.subscribers.modifica = this.saldoStralcioService
 //.modificaSaldoStralcio(this.saldoStralcioVO, this.idSaldoStralcio, this.idProgetto,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
          .modificaSaldoStralcio(this.saldoStralcioVO,this.idUtente,this.idProgetto, this.idSaldoStralcio ,this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          })
      } else {
        this.subscribers.insert = this.saldoStralcioService
//.insertSaldoStralcio(this.saldoStralcioVO, this.idProgetto,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
        .insertSaldoStralcio(this.saldoStralcioVO, this.idUtente,this.idProgetto, this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.isSalvato = data;
              this.dialogRef.close(this.isSalvato);
            }
          });
      }
    }
  }

  setImporto(campNumber: number) {
    switch (campNumber) {
      case 1:
        this.importoDebitore = this.sharedService.getNumberFromFormattedString(this.importoDebitoreFormatted);
        if (this.importoDebitore !== null) {
          this.importoDebitoreFormatted = this.sharedService.formatValue(this.importoDebitore.toString());
        }
        break;
      case 2:
        this.importoConfidi = this.sharedService.getNumberFromFormattedString(this.importoConfidiFormatted);
        if (this.importoConfidi !== null) {
          this.importoConfidiFormatted = this.sharedService.formatValue(this.importoConfidi.toString());
        }
        break
      case 3:
        this.importoRecuperato = this.sharedService.getNumberFromFormattedString(this.importoRecuperatoFormatted);
        if (this.importoRecuperato !== null) {
          this.importoRecuperatoFormatted = this.sharedService.formatValue(this.importoRecuperato.toString());
        }
        break;
      case 4:
        this.importoPerdita = this.sharedService.getNumberFromFormattedString(this.importoPerditaFormatted);
        if (this.importoPerdita !== null) {
          this.importoPerditaFormatted = this.sharedService.formatValue(this.importoPerdita.toString());
        }
        break;
        case 5: // Disimpegno
        this.importoDisimpegno = this.sharedService.getNumberFromFormattedString(this.importoDisimpegnoFormatted);
        if (this.importoDisimpegno !== null) {
          this.importoDisimpegnoFormatted = this.sharedService.formatValue(this.importoDisimpegno.toString());
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
