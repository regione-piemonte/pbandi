/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { TipoAnagraficaDTO } from 'src/app/associazione-permesso-ruolo/commons/dto/tipo-anagrafica-dto';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AssociazioneRuoloPermessoService } from '../../services/associazione-ruolo-permesso.service';
import { AssociazioneRpDialogComponent } from '../associazione-rp-dialog/associazione-rp-dialog.component';

@Component({
  selector: 'app-associazione-ruolo-permesso',
  templateUrl: './associazione-ruolo-permesso.component.html',
  styleUrls: ['./associazione-ruolo-permesso.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociazioneRuoloPermessoComponent implements OnInit {

  user: UserInfoSec;
  descrizione: string;
  codice: string;
  descrizionePrec: string;
  codicePrec: string;
  criteriRicercaOpened: boolean = true;
  isResultVisible: boolean;
  ruoli: Array<TipoAnagraficaDTO>;

  dataSource: RuoliDatasource;
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
    private associazioneRuoloPermessoService: AssociazioneRuoloPermessoService,
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
    this.subscribers.ricerca = this.associazioneRuoloPermessoService.cercaRuoli(this.descrizione, this.codice, this.user.idUtente).subscribe(data => {
      if (data) {
        this.ruoli = data;
        this.dataSource = new RuoliDatasource(this.ruoli);
        this.paginator.length = this.ruoli.length;
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

  openAssociazione(ruolo: TipoAnagraficaDTO) {
    this.resetMessageError();
    this.dialog.open(AssociazioneRpDialogComponent, {
      width: '80%',
      data: {
        codice: ruolo.descBreveTipoAnagrafica,
        descrizione: ruolo.descTipoAnagrafica,
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

export class RuoliDatasource extends MatTableDataSource<TipoAnagraficaDTO> {

  _orderData(data: TipoAnagraficaDTO[]): TipoAnagraficaDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "codice";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "codice":
        sortedData = data.sort((tipoA, tipoB) => {
          return tipoA.descBreveTipoAnagrafica.localeCompare(tipoB.descBreveTipoAnagrafica);
        });
        break;
      case "descrizione":
        sortedData = data.sort((tipoA, tipoB) => {
          return tipoA.descTipoAnagrafica.localeCompare(tipoB.descTipoAnagrafica);
        });
        break;
      default:
        sortedData = data.sort((tipoA, tipoB) => {
          return tipoA.descBreveTipoAnagrafica.localeCompare(tipoB.descBreveTipoAnagrafica);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: TipoAnagraficaDTO[]) {
    super(data);
  }
}