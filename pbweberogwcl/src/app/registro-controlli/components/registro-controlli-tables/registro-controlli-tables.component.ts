/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from '@pbandi/common-lib';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EsitoSalvaIrregolaritaDTO } from '../../commons/dto/esito-salva-irregolarita-dto';
import { FiltroRicercaIrregolarita } from '../../commons/models/filtro-ricerca-irregolarita';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RettificaForfettaria } from '../../commons/models/rettifica-forfettaria';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { DettaglioEsitoControlloOpGestComponent } from '../dettaglio-esito-controllo_op_gest/dettaglio-esito-controllo-op-gest.component';
import { DettaglioIrregolaritaComponent } from '../dettaglio-irregolarita/dettaglio-irregolarita.component';
import { ModificaIrregolariComponent } from '../modifica-irregolari/modifica-irregolari.component';
import { ModificaRegolariComponent } from '../modifica-regolari/modifica-regolari.component';
import { RegistraInvioComponent } from '../registra-invio/registra-invio.component';

@Component({
  selector: 'app-registro-controlli-tables',
  templateUrl: './registro-controlli-tables.component.html',
  styleUrls: ['./registro-controlli-tables.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RegistroControlliTablesComponent implements OnInit {

  @Input() displayedColumnsRegolari: string[];
  @Input() dataSourceRegolari: MatTableDataSource<Irregolarita>;

  @Input() displayedColumnsIrregolari: string[];
  @Input() dataSourceIrregolari: MatTableDataSource<Irregolarita>;

  @Input() displayedColumnsRetForf: string[];
  @Input() dataSourceRetForf: MatTableDataSource<RettificaForfettaria>;

  @Input() showTable: boolean;

  @Input() idProgetto: number; //valorizzato solo dall'attività registro controlli

  @Output() loadEsitiRegolari = new EventEmitter<any>();
  @Output() loadIrregolarita = new EventEmitter<any>();
  @Output() loadRettifiche = new EventEmitter<any>();
  @Output() resetMessages = new EventEmitter<boolean>();

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedcancellaIrregolaritaRegolare: boolean;
  loadedcancellaIrregolarita: boolean;
  loadedcancellaIrregolaritaProvvisoria: boolean;
  loadedeliminaRettificaForfettaria: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private dialog: MatDialog,
    private registroControlliService: RegistroControlliService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
  }

  dettaglioRegolarita(row: Irregolarita, message?: string) {
    this.resetMessages.emit(true);
    this.dialog.open(DettaglioEsitoControlloOpGestComponent, {
      width: '50%',
      data: {
        idEsitoControllo: row.idEsitoControllo,
        beneficiario: row.denominazioneBeneficiario,
        codiceProgetto: row.codiceVisualizzatoProgetto,
        message: message
      }
    });
  }

  dettaglioIrregolarita(row: Irregolarita, message?: string) {
    this.resetMessages.emit(true);
    var irregDefinitiva = false;

    if (row.idIrregolaritaProvv == null) {
      irregDefinitiva = true;
    } else {
      if (row.idIrregolaritaProvv == row.idIrregolarita) {
        irregDefinitiva = false;
      } else {
        irregDefinitiva = true;
      }
    }
    let dialogRef = this.dialog.open(DettaglioIrregolaritaComponent, {
      width: '70%',
      data: {
        idIrregolarita: row.idIrregolarita,
        irregDefinitiva: irregDefinitiva,
        beneficiario: row.denominazioneBeneficiario,
        codiceProgetto: row.codiceVisualizzatoProgetto,
        message: message
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.registraInvio(row);
      }
    });
  }

  registraInvio(row: Irregolarita) {
    this.resetMessages.emit(true);
    let dialogRef = this.dialog.open(RegistraInvioComponent, {
      width: '40%',
      data: {
        idIrregolarita: row.idIrregolarita,
        beneficiario: row.denominazioneBeneficiario,
        codiceProgetto: row.codiceVisualizzatoProgetto
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.dettaglioIrregolarita(row, res.message);
      }
    });
  }


  modificaRegolarita(row: Irregolarita) {
    this.resetMessages.emit(true);
    let dialogRef = this.dialog.open(ModificaRegolariComponent, {
      width: '76%',
      disableClose: true,
      data: {
        idEsitoControllo: row.idEsitoControllo,
        beneficiario: row.denominazioneBeneficiario,
        codiceProgetto: row.codiceVisualizzatoProgetto,
        idProgetto: this.idProgetto
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadEsitiRegolari.emit({ refresh: true });
      }
    });
  }

  modificaIrregolarita(row: Irregolarita) {
    this.resetMessages.emit(true);
    var irregDefinitiva = false;

    if (row.idIrregolaritaProvv == null) {
      irregDefinitiva = true;
    } else {
      if (row.idIrregolaritaProvv == row.idIrregolarita) {
        irregDefinitiva = false;
      } else {
        irregDefinitiva = true;
      }
    }
    let dialogRef = this.dialog.open(ModificaIrregolariComponent, {
      width: '70%',
      disableClose: true,
      data: {
        idIrregolarita: row.idIrregolarita,
        irregDefinitiva: irregDefinitiva,
        beneficiario: row.denominazioneBeneficiario,
        codiceProgetto: row.codiceVisualizzatoProgetto,
        idProgetto: this.idProgetto,
        irregolari: this.dataSourceIrregolari.data
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        if (res.provvToReg) {
          this.loadEsitiRegolari.emit({ idIrregolarita: row.idIrregolarita });
          this.loadIrregolarita.emit({ refresh: true });
        } else if (res.idIrregolaritaDef) {
          this.loadIrregolarita.emit({ idIrregolarita: res.idIrregolaritaDef });
        } else {
          this.loadIrregolarita.emit({ idIrregolarita: row.idIrregolarita });
        }
      }
    });
  }

  eliminaRegolarita(row: Irregolarita) {
    this.resetMessages.emit(true);
    this.loadedcancellaIrregolaritaRegolare = true;
    this.subscribers.cancellaIrregolaritaRegolare = this.registroControlliService.cancellaIrregolaritaRegolare(row.idIrregolarita).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
      this.loadedcancellaIrregolaritaRegolare = false;
      if (res.esito == true) {
        this.showMessageSuccess("Eliminazione effettuata con successo.");

        this.loadEsitiRegolari.emit({ refresh: true });
        this.loadIrregolarita.emit({ refresh: true });
      } else {
        this.showMessageError(res.msgs[0].msgKey);
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cancellazione della regolarità");
      this.loadedcancellaIrregolaritaRegolare = false;
    });
  }

  eliminaIrregolarita(row: Irregolarita) {
    this.resetMessages.emit(true);
    if (row.tipoControlliProvv == undefined) {

      this.loadedcancellaIrregolarita = true;
      this.subscribers.cancellaIrregolarita = this.registroControlliService.cancellaIrregolarita(row.idIrregolarita).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
        this.loadedcancellaIrregolarita = false;
        if (res.esito == true) {
          this.showMessageSuccess("Eliminazione effettuata con successo.");
          this.loadIrregolarita.emit({ refresh: true })
        } else {
          this.showMessageError(res.msgs[0].msgKey);
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cancellazione dell'irregolarità");
        this.loadedcancellaIrregolarita = false;
      });
    } else {

      this.loadedcancellaIrregolaritaProvvisoria = true;
      this.subscribers.cancellaIrregolaritaProvvisoria = this.registroControlliService.cancellaIrregolaritaProvvisoria(row.idIrregolarita).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
        this.loadedcancellaIrregolaritaProvvisoria = false;
        if (res.esito == true) {
          this.showMessageSuccess("Eliminazione effettuata con successo.");

          this.loadIrregolarita.emit({ refresh: true });
        } else {
          this.showMessageError(res.msgs[0].msgKey);
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cancellazione dell'irregolarità provvisoria");
        this.loadedcancellaIrregolaritaProvvisoria = false;
      });
    }
  }

  eliminaRetForf(row: RettificaForfettaria) {
    this.resetMessages.emit(true);
    this.loadedeliminaRettificaForfettaria = true;
    this.subscribers.eliminaRettificaForfettaria = this.registroControlliService.eliminaRettificaForfettaria(row.idRettificaForfett).subscribe((res: EsitoSalvaIrregolaritaDTO) => {
      this.loadedeliminaRettificaForfettaria = false;
      if (res.esito == true) {
        this.showMessageSuccess("Eliminazione effettuata con successo.");

        this.loadRettifiche.emit({ refresh: true });

      } else {
        this.showMessageError(res.msgs[0].msgKey);
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di eliminazione delle rettifiche forfettarie");
      this.loadedeliminaRettificaForfettaria = false;
    });
  }

  hiddenEditButton(row: Irregolarita) {

    var risultato = false;
    // DISABILITO PULSANTE MODIFICA QUANDO FLAGBLOCCATA = 'S'
    if (row.flagBloccata == 'S') {
      risultato = true;
    } else {
      if (row.idIrregolarita == row.idIrregolaritaProvv) {
        // DISABILITO PULSANTE MODIFICA QUANDO IRREGOLARITA' PROVVISORIA E' LEGATA A UN'IRREGOLARITA' DEFINITIVA
        //definitiva associata a una provvisoria
        let def = this.dataSourceIrregolari.data.find(d => d.idIrregolarita !== d.idIrregolaritaProvv && d.idIrregolaritaProvv === row.idIrregolarita);
        if (def) {
          risultato = true;
        }
        // DISABILITO PULSANTE MODIFICA QUANDO IRREGOLARITA' PROVVISORIA E' LEGATA A UNA REGOLARE
        //regolare associata a una provvisoria
        let reg = this.dataSourceRegolari.data.find(d => d.idIrregolaritaCollegata === row.idIrregolarita);
        if (reg) {
          risultato = true;
        }
      }
    }
    return risultato;
  }


  getDescTipoByCode(code: string) {
    if (code == 'R') {
      return 'Rev.';
    }
    if (code == 'S') {
      return 'Soppr.';
    }
    if (code == 'D') {
      return 'Doc.';
    }
    if (code == 'L') {
      return 'Loco';
    }
    return "";
  }

  popolaColonnaRdCA(rettifica: RettificaForfettaria) {
    if (rettifica.esitoDefinitivo == null) {
      if (rettifica.esitoIntermedio == null) {
        return null;
      } else {
        let s = "Esito intermedio: ";
        switch (rettifica.esitoIntermedio) {
          case "POSITIVO":
            s += "REGOLARE";
            break;
          case "NEGATIVO":
            s += "IRREGOLARE";
            break;
          default:
            break;
        }
        s += rettifica.flagRettificaIntermedio === "S" ? " CON RETTIFICA" : "";
        return s;
      }
    } else {
      let s = "Esito intermedio: ";
      switch (rettifica.esitoDefinitivo) {
        case "POSITIVO":
          s += "REGOLARE";
          break;
        case "NEGATIVO":
          s += "IRREGOLARE";
          break;
        default:
          break;
      }
      s += rettifica.flagRettificaDefinitivo === "S" ? " CON RETTIFICA" : "";
      return s;
    }
  }

  getValueWithSlash(value: string) {
    return ' / ' + value;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (this.loadedcancellaIrregolaritaRegolare || this.loadedcancellaIrregolarita || this.loadedcancellaIrregolaritaProvvisoria
      || this.loadedeliminaRettificaForfettaria) {
      return true;
    }
    return false;
  }

}
