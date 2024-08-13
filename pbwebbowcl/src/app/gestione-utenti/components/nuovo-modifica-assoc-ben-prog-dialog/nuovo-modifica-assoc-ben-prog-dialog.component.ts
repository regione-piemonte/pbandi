/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { BeneficiarioProgettoAssociatoDTO } from '../../commons/dto/beneficiario-progetto-associato-dto';
import { UtenteRicercatoDTO } from '../../commons/dto/utente-ricercato-dto';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';

@Component({
  selector: 'app-nuovo-modifica-assoc-ben-prog-dialog',
  templateUrl: './nuovo-modifica-assoc-ben-prog-dialog.component.html',
  styleUrls: ['./nuovo-modifica-assoc-ben-prog-dialog.component.scss']
})
export class NuovoModificaAssocBenProgDialogComponent implements OnInit {

  utente: UtenteRicercatoDTO;
  isNuovo: boolean;
  cfBenef: string;
  codProg: string;
  rappr: string;
  idProgetto: number;

  messageError: string;
  isMessageErrorVisible: boolean;

  loadedSalva: boolean = true;
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<NuovoModificaAssocBenProgDialogComponent>,
    private gestioneUtentiService: GestioneUtentiService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.isNuovo = this.data.isNuovo;
    this.utente = this.data.utente;
    if (!this.isNuovo) {
      this.cfBenef = this.data.row.codiceFiscaleBeneficiario;
      this.codProg = this.data.row.codiceProgettoVisualizzato;
      this.idProgetto = this.data.row.idProgetto;
      this.rappr = this.data.row.isRappresentanteLegale ? "true" : "false";
    }
  }

  salva() {
    this.resetMessageError();
    if (this.isNuovo) {
      this.loadedSalva = false;
      this.subscribers.elimina = this.gestioneUtentiService.aggiungiAssociazioneBeneficiarioProgetto(this.utente.idUtente, this.utente.idSoggetto, this.cfBenef, this.codProg,
        this.rappr).subscribe(data => {
          if (data && data.esito) {
            this.dialogRef.close({ message: "Salvataggio avvenuto con successo." });
          } else {
            this.showMessageError(data.message);
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSalva = true;
          this.showMessageError("Errore in fase di salvataggio.");
        });
    } else {
      this.loadedSalva = false;
      this.subscribers.elimina = this.gestioneUtentiService.modificaAssociazioneBeneficiarioProgetto(this.utente.idUtente, this.utente.idSoggetto, this.idProgetto, this.cfBenef,
        this.codProg, this.rappr).subscribe(data => {
          if (data && data.esito) {
            this.dialogRef.close({ message: "Salvataggio avvenuto con successo." });
          } else {
            this.showMessageError(data.message);
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSalva = true;
          this.showMessageError("Errore in fase di salvataggio.");
        });
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
    if (!this.loadedSalva) {
      return true;
    }
    return false;
  }

}
