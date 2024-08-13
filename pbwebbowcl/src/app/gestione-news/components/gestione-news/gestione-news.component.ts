/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AvvisoDTO } from '../../commons/dto/avviso-dto';
import { CodiceDescrizioneDTO } from '../../commons/dto/codice-descrizione-dto';
import { GestioneNewsService } from '../../services/gestione-news.service';
import { NewsDialogComponent } from '../news-dialog/news-dialog.component';

@Component({
  selector: 'app-gestione-news',
  templateUrl: './gestione-news.component.html',
  styleUrls: ['./gestione-news.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneNewsComponent implements OnInit {

  user: UserInfoSec;
  avvisi: Array<AvvisoDTO>;
  tipiAnagrafica: Array<CodiceDescrizioneDTO>;

  dataSource: MatTableDataSource<AvvisoDTO> = new MatTableDataSource<AvvisoDTO>([]);
  displayedColumns: string[] = ['titolo', 'tipo', 'dataInizio', 'dataFine', 'profili', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedElimina: boolean = true;
  loadedSalva: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private gestioneNewsService: GestioneNewsService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadData();
      }
    });
  }

  loadData() {
    this.loadedInizializza = false;
    this.subscribers.init = this.gestioneNewsService.inizializzaGestioneNews().subscribe(init => {
      if (init) {
        this.avvisi = init.avvisi;
        this.tipiAnagrafica = init.tipiAnagrafica;

        this.dataSource = new MatTableDataSource<AvvisoDTO>(this.avvisi);
        this.paginator.pageIndex = 0;
        this.paginator.length = this.avvisi.length;
        this.dataSource.paginator = this.paginator;
      }
      this.loadedInizializza = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di inizializzazione.");
      this.loadedInizializza = true;
    });

  }

  isEliminabile(avviso: AvvisoDTO) {
    if (!avviso.dtFine) {
      return true;
    }
    let today = new Date();
    today.setHours(0, 0, 0, 0);
    let data = new Date(avviso.dtFine);
    data.setHours(0, 0, 0, 0);
    if (data > today) {
      return true
    }
    return false;
  }

  elimina(avviso: AvvisoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione dell'avviso?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.elimina = this.gestioneNewsService.eliminaAvviso(avviso.idNews).subscribe(data => {
          if (data) {
            this.showMessageSuccess("Operazione avvenuta con successo.");
            this.loadData();
          } else {
            this.showMessageError("Errore in fase di eliminazione dell'avviso.");
          }

          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore in fase di eliminazione dell'avviso.");
        });
      }
    });
  }

  salva(avviso: AvvisoDTO) {
    this.loadedSalva = false;
    this.subscribers.nuovo = this.gestioneNewsService.aggiornaAvviso(avviso).subscribe(data => {
      if (data) {
        this.showMessageSuccess("Salvataggio avvenuto con successo.");
        this.loadData();
      } else {
        this.showMessageError("Errore in fase di salvataggio.");
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalva = true;
      this.showMessageError("Errore in fase di salvataggio.");
    });
  }

  nuovaNews() {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(NewsDialogComponent, {
      width: '50%',
      data: {
        tipiAnagrafica: this.tipiAnagrafica,
        type: "N" //N nuova, M modifica, D dettaglio
      }
    });
    dialogRef.afterClosed().subscribe((res: AvvisoDTO) => {
      if (res) {
        this.salva(res);
      }
    });
  }

  modifica(avviso: AvvisoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(NewsDialogComponent, {
      width: '50%',
      data: {
        tipiAnagrafica: this.tipiAnagrafica,
        type: "M", //N nuova, M modifica, D dettaglio
        avviso: avviso
      }
    });
    dialogRef.afterClosed().subscribe((res: AvvisoDTO) => {
      if (res) {
        this.salva(res);
      }
    });
  }

  dettaglio(avviso: AvvisoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.dialog.open(NewsDialogComponent, {
      width: '50%',
      data: {
        tipiAnagrafica: this.tipiAnagrafica,
        type: "D", //N nuova, M modifica, D dettaglio
        avviso: avviso
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
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

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializza || !this.loadedElimina || !this.loadedSalva) {
      return true;
    }
    return false;
  }

}
