/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BandoProcesso } from '../../commons/dto/bando-processo';
import { LineeFinanziamentoService } from '../../services/linee-finanziamento.service';
import { NavigationLineeFinanziamentoService } from '../../services/navigation-linee-finanziamento.service';

@Component({
  selector: 'app-linee-finanziamento',
  templateUrl: './linee-finanziamento.component.html',
  styleUrls: ['./linee-finanziamento.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class LineeFinanziamentoComponent implements OnInit {

  user: UserInfoSec;
  titoloLinea: string;
  criteriRicercaOpened: boolean = true;
  isResultVisible: boolean;
  lineeFinanziamento: Array<BandoProcesso>;

  displayedColumns: string[] = ['linea'];
  dataSource: MatTableDataSource<BandoProcesso>;

  @ViewChild("paginator", { static: true }) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedLinee: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private lineeFinanziamentoService: LineeFinanziamentoService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private navigationService: NavigationLineeFinanziamentoService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        if (this.navigationService.lastTitoloLinea) {
          this.titoloLinea = this.navigationService.lastTitoloLinea;
        }
      }
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  ricerca() {
    this.resetMessageError();
    if (this.titoloLinea?.length > 2) {
      this.loadedLinee = false;
      this.subscribers.linee = this.lineeFinanziamentoService.lineeDiFinanziamento(this.titoloLinea).subscribe(data => {
        if (data) {
          this.lineeFinanziamento = data;
          this.dataSource = new MatTableDataSource<BandoProcesso>(this.lineeFinanziamento);
          if (this.navigationService.lastTitoloLinea) {
            this.ripristinaPaginator();
          } else {
            this.paginator.length = this.lineeFinanziamento.length;
            this.paginator.pageIndex = 0;
            this.dataSource.paginator = this.paginator;
          }
          this.isResultVisible = true;
          this.criteriRicercaOpened = false;
        }
        this.loadedLinee = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    } else {
      this.showMessageError("Attenzione! Digitare almeno tre caratteri del titolo di una linea di finanziamento");
    }
  }

  goToLinea(idLinea: number) {
    this.saveDataForNavigation();
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO}/avvioProgetto/${idLinea}`);
  }

  saveDataForNavigation() {
    this.navigationService.lastTitoloLinea = this.titoloLinea;
    this.salvaPaginator();
  }

  salvaPaginator() {
    if (this.dataSource) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedLinee) {
      return true;
    }
    return false;
  }

}
