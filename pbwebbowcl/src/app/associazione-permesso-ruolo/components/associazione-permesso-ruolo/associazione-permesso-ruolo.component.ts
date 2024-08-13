/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { PermessoDTO } from '../../commons/dto/permesso-dto';
import { AssociazionePermessoRuoloService } from '../../services/associazione-permesso-ruolo.service';
import { AssociazionePrDialogComponent } from '../associazione-pr-dialog/associazione-pr-dialog.component';

@Component({
  selector: 'app-associazione-permesso-ruolo',
  templateUrl: './associazione-permesso-ruolo.component.html',
  styleUrls: ['./associazione-permesso-ruolo.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociazionePermessoRuoloComponent implements OnInit {

  user: UserInfoSec;
  descrizione: string;
  codice: string;
  descrizionePrec: string;
  codicePrec: string;
  criteriRicercaOpened: boolean = true;
  isResultVisible: boolean;
  permessi: Array<PermessoDTO>;

  dataSource: PermessiDatasource;
  displayedColumns: string[] = ['codice', 'descrizione'];

  @ViewChild("paginator", { static: true }) paginator: MatPaginator;
  @ViewChild("sort", { static: true }) sort: MatSort;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedRicerca: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private associazionePermessoRuoloService: AssociazionePermessoRuoloService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.ricerca();
      }
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  annulla() {
    this.descrizione = this.descrizionePrec;
    this.codice = this.codicePrec;
  }

  ricerca() {
    this.resetMessageError();
    this.loadedRicerca = false;
    this.descrizionePrec = this.descrizione;
    this.codicePrec = this.codice;
    this.subscribers.ricerca = this.associazionePermessoRuoloService.cercaPermessi(this.descrizione, this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.permessi = data;
        this.dataSource = new PermessiDatasource(this.permessi);
        this.paginator.length = this.permessi.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.isResultVisible = true;
      }
      this.loadedRicerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.isResultVisible = false;
      this.loadedRicerca = true;
    });
  }

  openAssociazione(permesso: PermessoDTO) {
    this.resetMessageError();
    this.dialog.open(AssociazionePrDialogComponent, {
      width: '80%',
      data: {
        codice: permesso.descBrevePermesso,
        descrizione: permesso.descPermesso,
        user: this.user
      }
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedRicerca) {
      return true;
    }
    return false;
  }

}

export class PermessiDatasource extends MatTableDataSource<PermessoDTO> {

  _orderData(data: PermessoDTO[]): PermessoDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "codice";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "codice":
        sortedData = data.sort((permA, permB) => {
          return permA.descBrevePermesso.localeCompare(permB.descBrevePermesso);
        });
        break;
      case "descrizione":
        sortedData = data.sort((permA, permB) => {
          return permA.descPermesso.localeCompare(permB.descPermesso);
        });
        break;
      default:
        sortedData = data.sort((permA, permB) => {
          return permA.descBrevePermesso.localeCompare(permB.descBrevePermesso);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: PermessoDTO[]) {
    super(data);
  }
}