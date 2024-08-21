/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ContoEconomicoItem } from '../../commons/dto/conto-economico-item';
import { VisualizzaContoEconomicoService } from '../../services/visualizza-conto-economico.service';
import { AutoUnsubscribe } from '../../../shared/decorator/auto-unsubscribe';
import { CodiceDescrizioneDTO } from '../../commons/dto/codice-descrizione-dto';
import { HandleExceptionService } from './../../../core/services/handle-exception.service';

@Component({
  selector: 'app-visualizza-conto-economico-dialog',
  templateUrl: './visualizza-conto-economico-dialog.component.html',
  styleUrls: ['./visualizza-conto-economico-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class VisualizzaContoEconomicoDialogComponent implements OnInit {

  idProgetto: number;
  apiURL: string;
  ricercaCapofila: boolean;
  partnersCapofila: Array<CodiceDescrizioneDTO>;
  partnerSelected: CodiceDescrizioneDTO;
  contoEconomico: Array<ContoEconomicoItem>;
  tipoRicerca: string = "CAPOFILA";

  displayedColumns: string[] = ['vociSpesa', 'spesaAmmessa', 'spesaRendicontata', 'spesaQuietanzata', 'qa', 'spesaValidata', 'via'];
  dataSource: MatTableDataSource<ContoEconomicoItem> = new MatTableDataSource<ContoEconomicoItem>([]);

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedAggiorna: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<VisualizzaContoEconomicoDialogComponent>,
    private visualizzaContoEconomicoService: VisualizzaContoEconomicoService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
    this.apiURL = this.data.apiURL;
    this.loadedInizializza = false;
    this.subscribers.init = this.visualizzaContoEconomicoService.inizializzaVisualizzaContoEconomico(this.idProgetto, this.apiURL).subscribe(data => {
      if (data) {
        this.ricercaCapofila = data.ricercaCapofila;
        this.partnersCapofila = data.partnersCapofila;
        this.contoEconomico = data.contoEconomico;
        this.contoEconomico.forEach(r => { r.visible = true });
        for (let item of this.contoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
          let padre = this.contoEconomico.find(r => r.id === item.idPadre);
          padre.figliVisible = true;
        }
        this.calcolaPercentuali();
        this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.contoEconomico);
      }
      this.loadedInizializza = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di inizializzazione.")
      this.loadedInizializza = true;
    });
  }

  aggiorna() {
    this.loadedAggiorna = false;
    this.subscribers.aggiorna = this.visualizzaContoEconomicoService.aggiornaVisualizzaContoEconomico(this.idProgetto,
      this.partnerSelected && this.tipoRicerca === "PARTNER" ? +this.partnerSelected.codice : null, this.tipoRicerca, this.apiURL).subscribe(data => {
        if (data) {
          this.contoEconomico = data;
          this.contoEconomico.forEach(r => { r.visible = true });
          for (let item of this.contoEconomico.filter(r => r.level > 1 && !r.haFigli)) {
            let padre = this.contoEconomico.find(r => r.id === item.idPadre);
            padre.figliVisible = true;
          }
          this.calcolaPercentuali();
          this.dataSource = new MatTableDataSource<ContoEconomicoItem>(this.contoEconomico);
        }
        this.loadedAggiorna = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di aggiornamento.")
        this.loadedAggiorna = true;
      });
  }

  openCloseVoce(id: string) {
    let righe = this.contoEconomico.filter(r => r.idPadre === id);
    let padre = this.contoEconomico.find(r => r.id === id);
    if (righe && !padre.figliVisible) {
      righe.forEach(r => { r.visible = true });
      padre.figliVisible = true;
    } else if (righe && padre.figliVisible) {
      righe.forEach(r => { r.visible = false });
      padre.figliVisible = false;
    }
  }

  calcolaPercentuali() {
    let padri = this.contoEconomico.filter(r => r.level === 1);
    let conto = this.contoEconomico.find(r => r.level === 0 && r.haFigli);
    conto.perc = 0;
    for (let padre of padri) {
      if (padre.haFigli) {
        let figli = this.contoEconomico.filter(r => r.idPadre === padre.id);
        for (let figlio of figli) {
          //percentuale dei figli
          figlio.perc = figlio.importoSpesaAmmessa * 100 / conto.importoSpesaAmmessa;
          if (!figlio.perc) {
            figlio.perc = 0;
          }
        }
        //percentuale padre
        padre.perc = 0;
        figli.forEach(f => {
          padre.perc += f.perc;
        });
      } else {
        padre.perc = padre.importoSpesaAmmessa * 100 / conto.importoSpesaAmmessa;
      }
      //percentuale conto
      conto.perc += padre.perc;
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  close() {
    this.dialogRef.close();
  }

  isLoading() {
    if (!this.loadedInizializza || !this.loadedAggiorna) {
      return true;
    }
    return false;
  }

}
