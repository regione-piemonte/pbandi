/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SedeProgettoDTO } from "../../commons/dto/sede-progetto-dto";
import { ModificaSedeInterventoRequest } from "../../commons/requests/modifica-sede-intervento-request";
import { DatiProgettoService } from "../../services/dati-progetto.service";
import { EsitoOperazioni } from '../../commons/models/esito-operazioni';

@Component({
  selector: 'app-dati-progetto-sedi-dialog',
  templateUrl: './dati-progetto-sedi-dialog.component.html',
  styleUrls: ['./dati-progetto-sedi-dialog.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiProgettoSediDialogComponent implements OnInit {

  action: string; //modifica, dettaglio
  title: string = 'Gestione dati del progetto';
  titleModifica: string = 'Modifica Sede';
  titleDettaglio: string = 'Dettaglio Sede';
  is1420: boolean;
  idProgetto: number;
  idSede: number;
  isModificabile: boolean;
  dettaglioSedeProgetto: SedeProgettoDTO;

  //LOADED
  loadedLineaPORFESR1420: boolean = true;
  loadedDettaglioSedeProgetto: boolean = true;
  loadedModifica: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private datiProgettoService: DatiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<DatiProgettoSediDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }


  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
    this.idSede = this.data.idSede;
    this.isModificabile = this.data.isModifica;
    if (this.isModificabile) {
      this.action = 'modifica';
      this.title = this.titleModifica;
    } else {
      this.action = 'dettaglio';
      this.title = this.titleDettaglio;
    }
    this.loadDettaglioSedeProgetto();
  }

  loadLineaPORFESR1420() {
    this.loadedLineaPORFESR1420 = false;
    this.subscribers.settoreAttivita = this.datiProgettoService.isLineaPORFESR1420(this.idProgetto).subscribe((res: boolean) => {
      this.is1420 = res;
      this.is1420 = true; // Todo: remove test
      this.loadedLineaPORFESR1420 = true;
      if (this.is1420) {
        this.loadDettaglioSedeProgetto();
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica bando linea POR-FESR 14-20.");
      this.is1420 = true;
      this.loadedLineaPORFESR1420 = true;
    });
  }

  loadDettaglioSedeProgetto() {
    this.loadedDettaglioSedeProgetto = false;
    this.subscribers.dettaglioSedeProgetto = this.datiProgettoService.getDettaglioSedeProgetto(this.idSede, this.idProgetto).subscribe((res: SedeProgettoDTO) => {
      if (res) {
        this.dettaglioSedeProgetto = res;
      }
      this.loadedDettaglioSedeProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del dettaglio della sede.");
      this.loadedDettaglioSedeProgetto = true;
    });
  }


  onMatCheckboxChange(checkbox: MatCheckboxChange, field?: string) {
    debugger;
    if (field !== undefined) {
      if (checkbox.checked) {
        this.dettaglioSedeProgetto.flagSedeAmministrativa = checkbox.source.value;
      } else {
        this.dettaglioSedeProgetto.flagSedeAmministrativa = '';
      }
    }
  }

  isLoading() {
    let r = false;
    if (
      (
        (
          this.action == 'dettaglio' ||
          this.action == 'modifica'
        ) && (
          !this.loadedDettaglioSedeProgetto
        )
      )
    ) {
      r = true;
    }
    return r;
  }


  salva() {
    this.confermaDialog();  // Salva
  }

  confermaDialog() {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi la modifica dei dati della sede di intervento?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadSalvaDatiProgettoDettaglio();
      }
    });
  }

  loadSalvaDatiProgettoDettaglio() {
    if (this.action == 'modifica') {
      let request: ModificaSedeInterventoRequest = {
        idSedeInterventoAttuale: this.idSede,
        sedeIntervento: this.dettaglioSedeProgetto
      };
      this.loadedModifica = false;
      this.subscribers.trasferimentiService = this.datiProgettoService.modificaSedeIntervento(request).subscribe((data: EsitoOperazioni) => {
        if (data) {
          if (data.esito) {
            this.showMessageSuccess(data.msg);
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedModifica = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedModifica = true;
        this.showMessageError("Errore nella modifica del trasferimento.");
      });
    }
  }

  //MESSAGGI SUCCESS E ERROR - start
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end

  close() {
    this.dialogRef.close();
  }
}
