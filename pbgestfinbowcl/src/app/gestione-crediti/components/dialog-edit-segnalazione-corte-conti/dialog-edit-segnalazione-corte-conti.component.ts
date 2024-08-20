/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';



import { SegnalazioneCorteContiVO } from '../../commons/dto/segnalazione-corte-contiVO';
import { SegnalazioneCorteContiService } from '../../services/segnalazione-corte-conti-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';


@Component({
  selector: 'app-dialog-edit-segnalazione-corte-conti',
  templateUrl: './dialog-edit-segnalazione-corte-conti.component.html',
  styleUrls: ['./dialog-edit-segnalazione-corte-conti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditSegnalazioneCorteContiComponent implements OnInit {
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  allegati: any;

  today = new Date();
  
  idSegnalazioneCorteConti: number;
  dataSegn: Date;
  numProtocolloSegn: string;
  impCredResCapitale: number;
  impOneriAgevolaz: number;
  impQuotaSegnalaz: number;
  impGaranzia: number;
  flagPianoRientro: string;
  dataPianoRientro: Date;
  flagSaldoStralcio: string;
  dataSaldoStralcio: Date;
  flagPagamIntegrale: string;
  dataPagamento: Date;
  flagDissegnalazione: string;
  dataDissegnalazione: Date;
  numProtocolloDiss: string;
  flagDecretoArchiv: string;
  dataArchiv: Date;
  numProtocolloArchiv: string;
  flagComunicazRegione: string;
  note: string;
  idUtente: number;
  isSalvato: boolean;
  isControllaCampi: boolean;
  isCampiVuoti: boolean;
  isModifica: boolean;

  segnalazioneCCVO: SegnalazioneCorteContiVO = new SegnalazioneCorteContiVO();
  subscribers: any = {};
  user: UserInfoSec;
  isNew: boolean = true;
  impCredResCapitaleFormatted: string;
  impOneriAgevolazFormatted: string;
  impQuotaSegnalazFormatted: string;
  impGaranziaFormatted: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private segnalazioneCorteContiService: SegnalazioneCorteContiService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditSegnalazioneCorteContiComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idSegnalazioneCorteConti = this.data.idSegnalazioneCC;

      this.idProgetto = this.data.idProgetto;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    });

    if (this.idSegnalazioneCorteConti != null) {
      this.isNew = false;
      this.caricaSegnalazione();
    }

  }

   /* recuperaFile(newItem) {
    this.allegati = newItem;
  } */
  caricaSegnalazione() {

    this.subscribers.caricaSegnalazione = this.segnalazioneCorteContiService.getSegnalazione(this.idSegnalazioneCorteConti)
      .subscribe(data => {
        if (data) {
          this.segnalazioneCCVO = data;
          this.dataSegn = this.segnalazioneCCVO.dataSegnalazione;
          this.numProtocolloSegn = this.segnalazioneCCVO.numProtocolloSegn;
          this.impCredResCapitale = this.segnalazioneCCVO.impCredResCapitale;
          this.impOneriAgevolaz = this.segnalazioneCCVO.impOneriAgevolaz;
          this.impQuotaSegnalaz = this.segnalazioneCCVO.impQuotaSegnalaz;
          this.impGaranzia = this.segnalazioneCCVO.impGaranzia;
          this.flagPianoRientro = this.segnalazioneCCVO.flagPianoRientro;
          this.dataPianoRientro = this.segnalazioneCCVO.dataPianoRientro;
          this.flagSaldoStralcio = this.segnalazioneCCVO.flagSaldoStralcio;
          this.dataSaldoStralcio = this.segnalazioneCCVO.dataSaldoStralcio;
          this.flagPagamIntegrale = this.segnalazioneCCVO.flagPagamIntegrale;
          this.dataPagamento = this.segnalazioneCCVO.dataPagamento;
          this.flagDissegnalazione = this.segnalazioneCCVO.flagDissegnalazione;
          this.dataDissegnalazione = this.segnalazioneCCVO.dataDissegnalazione;
          this.numProtocolloDiss = this.segnalazioneCCVO.numProtocolloDiss;
          this.flagDecretoArchiv = this.segnalazioneCCVO.flagDecretoArchiv;
          this.dataArchiv = this.segnalazioneCCVO.dataArchiv;
          this.numProtocolloArchiv = this.segnalazioneCCVO.numProtocolloArchiv;
          this.flagComunicazRegione = this.segnalazioneCCVO.flagComunicazRegione;
          this.note = this.segnalazioneCCVO.note;

          if (this.impCredResCapitale) {
            this.impCredResCapitaleFormatted = this.sharedService.formatValue(this.impCredResCapitale.toString());
          }
          if (this.impGaranzia) {
            this.impGaranziaFormatted = this.sharedService.formatValue(this.impGaranzia.toString());
          }
          if (this.impOneriAgevolaz) {
            this.impOneriAgevolazFormatted = this.sharedService.formatValue(this.impOneriAgevolaz.toString());
          }
          if (this.impQuotaSegnalaz) {
            this.impQuotaSegnalazFormatted = this.sharedService.formatValue(this.impQuotaSegnalaz.toString());
          }
        }
      });

  }

  controllaCampi() {

    var regex = /^\d+(?:\.\d{1,2})?$/;

    if (this.dataArchiv == null && this.dataDissegnalazione == null && this.dataPagamento == null && this.note == null
      && this.dataPianoRientro == null && this.dataSaldoStralcio == null && this.dataSegn == null && this.numProtocolloArchiv == null
      && this.numProtocolloDiss == null && this.numProtocolloSegn == null && this.impCredResCapitale == null && this.impGaranzia == null
      && this.impOneriAgevolaz == null && this.impQuotaSegnalaz == null && this.flagComunicazRegione == null && this.flagDecretoArchiv == null
      && this.flagDissegnalazione == null && this.flagPagamIntegrale == null && this.flagPianoRientro == null && this.flagSaldoStralcio == null) {
      this.isCampiVuoti = true;
    } else {
      if (
        ((this.impCredResCapitale != null && this.impCredResCapitale.toString().length > 13)
          || (this.impCredResCapitale != null && !this.impCredResCapitale.toString().match(regex)))

        || ((this.impOneriAgevolaz != null && this.impOneriAgevolaz.toString().length > 13)
          || (this.impOneriAgevolaz != null && !this.impOneriAgevolaz.toString().match(regex)))

        || ((this.impQuotaSegnalaz != null && this.impQuotaSegnalaz.toString().length > 13)
          || (this.impQuotaSegnalaz != null && !this.impQuotaSegnalaz.toString().match(regex)))

        || ((this.impGaranzia != null && this.impGaranzia.toString().length > 13)
          || (this.impGaranzia != null && !this.impGaranzia.toString().match(regex)))
      ) {
        this.isControllaCampi = true;
      } else {
        this.isControllaCampi = false;
        this.salvaSegnalazione();
      }
    }



  }

  salvaSegnalazione() {

    if (this.segnalazioneCCVO.dataSegnalazione == this.dataSegn &&
      this.segnalazioneCCVO.numProtocolloSegn == this.numProtocolloSegn &&
      this.segnalazioneCCVO.impCredResCapitale == this.impCredResCapitale &&
      this.segnalazioneCCVO.impOneriAgevolaz == this.impOneriAgevolaz &&
      this.segnalazioneCCVO.impQuotaSegnalaz == this.impQuotaSegnalaz &&
      this.segnalazioneCCVO.impGaranzia == this.impGaranzia &&
      this.segnalazioneCCVO.flagPianoRientro == this.flagPianoRientro &&
      this.segnalazioneCCVO.dataPianoRientro == this.dataPianoRientro &&
      this.segnalazioneCCVO.flagSaldoStralcio == this.flagSaldoStralcio &&
      this.segnalazioneCCVO.dataSaldoStralcio == this.dataSaldoStralcio &&
      this.segnalazioneCCVO.flagPagamIntegrale == this.flagPagamIntegrale &&
      this.segnalazioneCCVO.dataPagamento == this.dataPagamento &&
      this.segnalazioneCCVO.flagDissegnalazione == this.flagDissegnalazione &&
      this.segnalazioneCCVO.dataDissegnalazione == this.dataDissegnalazione &&
      this.segnalazioneCCVO.numProtocolloDiss == this.numProtocolloDiss &&
      this.segnalazioneCCVO.flagDecretoArchiv == this.flagDecretoArchiv &&
      this.segnalazioneCCVO.dataArchiv == this.dataArchiv &&
      this.segnalazioneCCVO.numProtocolloArchiv == this.numProtocolloArchiv &&
      this.segnalazioneCCVO.flagComunicazRegione == this.flagComunicazRegione &&
      this.segnalazioneCCVO.note == this.note) {
      this.isModifica = true;
    } else {
      this.isModifica = false;
      this.segnalazioneCCVO.dataSegnalazione = this.dataSegn;
      this.segnalazioneCCVO.numProtocolloSegn = this.numProtocolloSegn;
      this.segnalazioneCCVO.impCredResCapitale = this.impCredResCapitale;
      this.segnalazioneCCVO.impOneriAgevolaz = this.impOneriAgevolaz;
      this.segnalazioneCCVO.impQuotaSegnalaz = this.impQuotaSegnalaz;
      this.segnalazioneCCVO.impGaranzia = this.impGaranzia;
      this.segnalazioneCCVO.flagPianoRientro = this.flagPianoRientro;
      this.segnalazioneCCVO.dataPianoRientro = this.dataPianoRientro;
      this.segnalazioneCCVO.flagSaldoStralcio = this.flagSaldoStralcio;
      this.segnalazioneCCVO.dataSaldoStralcio = this.dataSaldoStralcio;
      this.segnalazioneCCVO.flagPagamIntegrale = this.flagPagamIntegrale;
      this.segnalazioneCCVO.dataPagamento = this.dataPagamento;
      this.segnalazioneCCVO.flagDissegnalazione = this.flagDissegnalazione;
      this.segnalazioneCCVO.dataDissegnalazione = this.dataDissegnalazione;
      this.segnalazioneCCVO.numProtocolloDiss = this.numProtocolloDiss;
      this.segnalazioneCCVO.flagDecretoArchiv = this.flagDecretoArchiv;
      this.segnalazioneCCVO.dataArchiv = this.dataArchiv;
      this.segnalazioneCCVO.numProtocolloArchiv = this.numProtocolloArchiv;
      this.segnalazioneCCVO.flagComunicazRegione = this.flagComunicazRegione;
      this.segnalazioneCCVO.note = this.note;
      this.segnalazioneCCVO.idProgetto = this.idProgetto;
      this.segnalazioneCCVO.idUtenteIns = this.idUtente;

      if (this.idSegnalazioneCorteConti != null) {
//this.subscribers.modifica = this.segnalazioneCorteContiService.modificaSegnalazione(this.segnalazioneCCVO, this.idSegnalazioneCorteConti,this.allegati,this.idModalitaAgevolazione).subscribe(dataDB => {
        this.subscribers.modifica = this.segnalazioneCorteContiService.modificaSegnalazione(this.segnalazioneCCVO, this.idSegnalazioneCorteConti,this.idModalitaAgevolazione).subscribe(dataDB => {
            if (dataDB)
              this.isSalvato = dataDB;
            this.dialogRef.close(this.isSalvato);
          });

      } else {
//this.subscribers.insert = this.segnalazioneCorteContiService.insertSegnalazione(this.segnalazioneCCVO,this.allegati,this.idModalitaAgevolazione).subscribe(dataDB => {
        this.subscribers.insert = this.segnalazioneCorteContiService.insertSegnalazione(this.segnalazioneCCVO, this.idModalitaAgevolazione).subscribe(dataDB => {
          if (dataDB) {
            this.isSalvato = dataDB;
            this.dialogRef.close(this.isSalvato);
          }
        });
      }
    }
  }

  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.impCredResCapitale = this.sharedService.getNumberFromFormattedString(this.impCredResCapitaleFormatted);
        if (this.impCredResCapitale !== null) {
          this.impCredResCapitaleFormatted = this.sharedService.formatValue(this.impCredResCapitale.toString());
        }
        break;
      case 2:
        this.impOneriAgevolaz = this.sharedService.getNumberFromFormattedString(this.impOneriAgevolazFormatted);
        if (this.impOneriAgevolaz !== null) {
          this.impOneriAgevolazFormatted = this.sharedService.formatValue(this.impOneriAgevolaz.toString());
        }
        break
      case 3:
        this.impQuotaSegnalaz = this.sharedService.getNumberFromFormattedString(this.impQuotaSegnalazFormatted);
        if (this.impQuotaSegnalaz !== null) {
          this.impQuotaSegnalazFormatted = this.sharedService.formatValue(this.impQuotaSegnalaz.toString());
        }
        break;
      case 4:
        this.impGaranzia = this.sharedService.getNumberFromFormattedString(this.impGaranziaFormatted);
        if (this.impGaranzia !== null) {
          this.impGaranziaFormatted = this.sharedService.formatValue(this.impGaranzia.toString());
        }
        break;
      default:
        break;
    }
  }

  setDataNull(idData: number) {
    switch (idData) {
      case 1:
        this.dataSegn = null;
        break;
      case 2:
        this.dataPianoRientro = null;
        break;
      case 3:
        this.dataSaldoStralcio = null;
        break;
      case 4:
        this.dataPagamento = null;
        break;
      case 5:
        this.dataDissegnalazione = null;
        break;
      case 6:
        this.dataArchiv = null;
        break;
      default:
        break;
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
