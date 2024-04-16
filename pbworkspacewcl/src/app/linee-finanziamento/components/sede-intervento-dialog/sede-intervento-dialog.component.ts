/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { patternCap, patternEmail, patternTelFax, SharedService } from 'src/app/shared/services/shared.service';
import { CodiceDescrizione } from '../../commons/dto/codice-descrizione';
import { Comune } from '../../commons/dto/comune';
import { IdDescBreveDescEstesa } from '../../commons/dto/id-desc-breve-desc-estesa';
import { SedeIntervento } from '../../commons/dto/sede-intervento';
import { SchedaProgettoService } from '../../services/scheda-progetto.service';

@Component({
  selector: 'app-sede-intervento-dialog',
  templateUrl: './sede-intervento-dialog.component.html',
  styleUrls: ['./sede-intervento-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SedeInterventoDialogComponent implements OnInit {

  sedeInterventoDefault: SedeIntervento;

  partitaIva: string;
  indirizzo: string;
  civico: string;
  cap: string;
  email: string;
  telefono: string;
  fax: string;

  nazioneSelected: IdDescBreveDescEstesa;
  regioneSelected: CodiceDescrizione;
  provinciaSelected: CodiceDescrizione;
  comuneSelected: CodiceDescrizione;

  nazioni: Array<IdDescBreveDescEstesa>;
  regioni: Array<CodiceDescrizione>;
  province: Array<CodiceDescrizione>;
  comuni: Array<CodiceDescrizione>;
  comuniEsteri: Array<CodiceDescrizione>;

  @ViewChild('sedeForm', { static: true }) sedeForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedCombo: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private schedaProgettoService: SchedaProgettoService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    public dialogRef: MatDialogRef<SedeInterventoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.nazioni = this.data.nazioni;
    this.sedeInterventoDefault = this.data.sedeInterventoDefault;
    if (this.sedeInterventoDefault.comuneSede.idNazione) {
      this.nazioneSelected = this.nazioni.find(n => n.id === this.sedeInterventoDefault.comuneSede.idNazione);
      this.changeNazione(this.sedeInterventoDefault.comuneSede.idRegione, this.sedeInterventoDefault.comuneSede.idProvincia, this.sedeInterventoDefault.comuneSede.idComune);
    }
  }

  changeNazione(idRegione?: string, idProvincia?: string, idComune?: string) {
    this.regioni = null;
    this.regioneSelected = null;
    this.province = null;
    this.provinciaSelected = null;
    this.comuni = null;
    this.comuniEsteri = null;
    this.comuneSelected = null;
    this.resetMessageError();
    if (this.nazioneSelected) {
      if (this.nazioneSelected.descEstesa === "ITALIA") {
        this.loadedCombo = false;
        this.subscribers.regioni = this.schedaProgettoService.popolaComboRegione().subscribe(data => {
          if (data) {
            this.regioni = data;
            if (idRegione) {
              this.regioneSelected = this.regioni.find(s => s.codice === idRegione);
              this.changeRegione(idProvincia, idComune);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero delle regioni della sede legale.");
          this.loadedCombo = true;
        });
      } else {
        this.loadedCombo = false;
        this.subscribers.comuniEsteri = this.schedaProgettoService.popolaComboComuneEstero(+this.nazioneSelected.id).subscribe(data => {
          if (data) {
            this.comuniEsteri = data;
            if (idComune) {
              this.comuneSelected = this.comuniEsteri.find(s => s.codice === idComune);
            }
          }
          this.loadedCombo = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di recupero deli comuni esteri della sede legale.");
          this.loadedCombo = true;
        });
      }
    }
  }

  changeRegione(idProvincia?: string, idComune?: string) {
    this.province = null;
    this.provinciaSelected = null;
    this.comuni = null;
    this.comuniEsteri = null;
    this.comuneSelected = null;
    if (this.regioneSelected) {
      this.resetMessageError();
      this.loadedCombo = false;
      this.subscribers.province = this.schedaProgettoService.popolaComboProvincia(+this.regioneSelected.codice).subscribe(data => {
        if (data) {
          this.province = data;
          if (idProvincia) {
            this.provinciaSelected = this.province.find(s => s.codice === idProvincia);
            this.changeProvincia(idComune);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero delle province della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  changeProvincia(idComune?: string) {
    this.comuni = null;
    this.comuniEsteri = null;
    this.comuneSelected = null;
    if (this.provinciaSelected) {
      this.resetMessageError();
      this.loadedCombo = false;
      this.subscribers.comuni = this.schedaProgettoService.popolaComboComuneItaliano(+this.provinciaSelected.codice).subscribe(data => {
        if (data) {
          this.comuni = data;
          if (idComune) {
            this.comuneSelected = this.comuni.find(s => s.codice === idComune);
          }
        }
        this.loadedCombo = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero dei comuni della sede legale.");
        this.loadedCombo = true;
      });
    }
  }

  salva() {
    let errore: boolean = false;
    if (this.sharedService.checkFieldFormPattern(this.sedeForm, this.email, 'email', patternEmail)) {
      errore = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.sedeForm, this.cap, 'cap', patternCap)) {
      errore = true;
    }

    if (this.sharedService.checkFieldFormPattern(this.sedeForm, this.telefono, 'telefono', patternTelFax)) {
      errore = true;
    }
    if (this.sharedService.checkFieldFormPattern(this.sedeForm, this.fax, 'fax', patternTelFax)) {
      errore = true;
    }
    if (this.sharedService.checkCodiceFiscaleEG(this.sedeForm, this.partitaIva, 'partitaIva')) {
      if (this.sharedService.checkCodiceFiscalePF(this.sedeForm, this.partitaIva, 'partitaIva')) {
        errore = true;
      }
    }
    if (!errore) {
      this.dialogRef.close(new SedeIntervento(null, this.partitaIva, this.indirizzo, this.cap, this.email, this.fax, this.telefono,
        new Comune(this.comuneSelected ? this.comuneSelected.descrizione : null, this.nazioneSelected.descEstesa, this.provinciaSelected ? this.provinciaSelected.descrizione : null,
          this.regioneSelected ? this.regioneSelected.descrizione : null, this.nazioneSelected.descEstesa === "ITALIA" ? "S" : "N", this.comuneSelected ? this.comuneSelected.codice : null,
          this.nazioneSelected.id, this.provinciaSelected ? this.provinciaSelected.codice : null, this.regioneSelected ? this.regioneSelected.codice : null), this.civico));
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

  isLoading() {
    if (!this.loadedCombo) {
      return true;
    }
    return false;
  }

  close() {
    this.dialogRef.close();
  }

}
