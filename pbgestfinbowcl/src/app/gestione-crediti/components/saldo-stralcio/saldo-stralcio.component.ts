/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AttivitaDTO } from '../../commons/dto/attivita-dto';
import { SaldoStralcioVO } from '../../commons/dto/saldo-stralcio-VO';
import { StoricoSaldoStralcioDTO } from '../../commons/dto/storico-saldo-stralcio-dto';
import { SaldoStralcioService } from '../../services/saldo-stralcio-service';
import { DialogEditSaldoStralcioComponent } from '../dialog-edit-saldo-stralcio/dialog-edit-saldo-stralcio.component';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';


@Component({
  selector: 'app-saldo-stralcio',
  templateUrl: './saldo-stralcio.component.html',
  styleUrls: ['./saldo-stralcio.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SaldoStralcioComponent implements OnInit {
  @Input() item;
  @Input() ambito;
  @Input() nomeBox;
  @Input() idModalitaAgevolazione;
  user: UserInfoSec;
  idUtente: number;
  saldoStralcioVO: SaldoStralcioVO;
  isStorico: boolean = false;
  isStorico2: boolean = false;
  isAttiva: boolean = true;
  listaAttivitaSaldoStralcio: Array<AttivitaDTO>;
  idProgetto: number;
  storicoSaldi: Array<StoricoSaldoStralcioDTO>;
  storicoSaldoStralcio: Array<StoricoSaldoStralcioDTO>;
  subscribers: any = {};
  displayedColumns: string[] = ['esito', 'dataEsito', 'utente', 'actions'];
  loadedSaldiStralci: boolean;
  loadedUser: boolean;
  loadedStorico: boolean = false;
  isSaveSuccess: boolean;
  isSaveError: boolean;
  disableStorico: boolean;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private saldoStralcioService: SaldoStralcioService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.item = params.idProgetto;
    });

    this.loadedStorico = true;
    this.loadedUser = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.loadedUser = true;
        this.idProgetto = this.item;

        this.loadSaldo();
        this.getStorico();
      }
    });


  }


  openDialog() {
    this.isSaveSuccess = false;
    let dialogRef = this.dialog.open(DialogEditSaldoStralcioComponent, {
      width: '40%',
      data: {
        idSaldoStralcio: null,
        nomeBox: this.nomeBox,
        ambito: this.ambito,
        idProgetto: this.item,
        idModalitaAgevolazione: this.idModalitaAgevolazione
      }
    });
    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.loadSaldo();
        this.getStorico();
        this.isSaveSuccess = data;
        this.disableStorico = false;
      } else if (data == false) {
        this.isSaveError = data;
      }
    });

  }
  modifica(idSaldoStralcio: number) {
    this.isSaveSuccess = false;

    let dialogRef = this.dialog.open(DialogEditSaldoStralcioComponent, {
      width: '40%',
      data: {
        idSaldoStralcio: idSaldoStralcio,
        nomeBox: this.nomeBox,
        ambito: this.ambito,
        idProgetto: this.item,
        idModalitaAgevolazione: this.idModalitaAgevolazione
      }
    });
    dialogRef.afterClosed().subscribe(data => {
      if (data == true) {
        this.loadSaldo();
        this.getStorico();
        this.isSaveSuccess = data;
      } else if (data == false) {
        this.isSaveError = data;
      }
    });
  }


  loadSaldo() {
    this.loadedStorico = false;
    this.loadedSaldiStralci = false;
    this.subscribers.storico = this.saldoStralcioService.getStoricoSaldoStralcio(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length > 0) {
        this.storicoSaldoStralcio = data;
        this.isStorico = true;
        this.loadedSaldiStralci = true
      } else {
        this.loadedSaldiStralci = true;
        this.isStorico = false;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  getStorico() {
    this.loadedStorico = false;
    this.subscribers.storico = this.saldoStralcioService.getStorico(this.idUtente, this.item, this.idModalitaAgevolazione).subscribe(
      data => {
        if (data.length > 0) {
          this.storicoSaldi = data;
          this.loadedStorico = true;
        } else {
          this.disableStorico = true;
        }
      });
  }

  openStorico() {
    this.isSaveSuccess = false;
    this.dialog.open(DialogStoricoBoxComponent, {
      width: '600px',
      data: {
        id: 4,
        storico: this.storicoSaldi
      }
    });
  }

  checkIsNuovoSaldoStralcio() {
    if (this.storicoSaldoStralcio == null)
      return true;
      
    for (const saldo of this.storicoSaldoStralcio) {
      if (saldo.descEsito == "ACCOLTO") {
        return false;
      }
    }
    return true;
  }

  isLoading() {
    if (!this.loadedSaldiStralci || !this.loadedUser) {
      return true;
    }
    return false;
  }

}


