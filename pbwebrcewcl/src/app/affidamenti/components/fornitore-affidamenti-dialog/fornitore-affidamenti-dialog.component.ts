/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Constants } from 'src/app/core/commons/util/constants';
import { CodiceDescrizioneDTO } from 'src/app/shared/commons/dto/codice-descrizione-dto';
import { FornitoreDTO } from 'src/app/shared/commons/dto/fornitore-dto';
import { SalvaFornitoreRequest } from '../../commons/requests/salva-fornitore-request';
import { TipoSoggetto } from '../../commons/dto/tipo-soggetto';

@Component({
  selector: 'app-fornitore-affidamenti-dialog',
  templateUrl: './fornitore-affidamenti-dialog.component.html',
  styleUrls: ['./fornitore-affidamenti-dialog.component.scss']
})
export class FornitoreAffidamentiDialogComponent implements OnInit {
  fornitori: Array<FornitoreDTO>;
  fornitoriFiltered: Array<FornitoreDTO>;
  fornitoreSelected: FornitoreDTO;
  tipologie: Array<TipoSoggetto> = new Array<TipoSoggetto>();
  tipologiaSelected: TipoSoggetto;
  ruoli: Array<CodiceDescrizioneDTO>;
  ruoliFiltered: Array<CodiceDescrizioneDTO> = new Array<CodiceDescrizioneDTO>();
  ruoloSelected: CodiceDescrizioneDTO;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<FornitoreAffidamentiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.ruoli = this.data.ruoli;
    this.fornitori = this.data.fornitori;
    this.popolaComboTipologia();
    this.popolaComboFornitore();
    this.popolaComboRuolo();
  }

  onSelectTipologia() {
    this.popolaComboFornitore();
    this.popolaComboRuolo();
  }

  popolaComboTipologia() {
    this.fornitori.forEach(f => {
      if (!this.tipologie.find(t => t.idTipoSoggetto === f.idTipoSoggetto)) {
        this.tipologie.push(new TipoSoggetto(f.idTipoSoggetto, f.descTipoSoggetto));
      }
    });
    this.tipologie.sort((a, b) => a.idTipoSoggetto - b.idTipoSoggetto);
    this.tipologiaSelected = this.tipologie[0];
  }

  popolaComboFornitore() {
    this.fornitoriFiltered = this.fornitori.filter(f => f.idTipoSoggetto === this.tipologiaSelected.idTipoSoggetto);
    this.fornitoreSelected = null;
  }

  popolaComboRuolo() {
    if (this.tipologiaSelected.idTipoSoggetto === Constants.ID_TIPO_SOGGETTO_PERSONA_FISICA) {
      this.ruoliFiltered.push(this.ruoli.find(r => +r.codice === Constants.ID_TIPO_PERCETTORE_PERSONA_FISICA));
    } else {
      this.ruoliFiltered = this.ruoli.filter(r => +r.codice !== Constants.ID_TIPO_PERCETTORE_PERSONA_FISICA);
    }
  }

  annulla() {
    this.dialogRef.close("ANNULLA");
  }

  salva() {
    if (this.tipologiaSelected.idTipoSoggetto !== Constants.ID_TIPO_SOGGETTO_PERSONA_FISICA && this.fornitoreSelected.idFormaGiuridica === 0) {
      this.resetMessageError();

      this.showMessageError("Il fonitore selezionato non è formalmente corretto: forma giuridica assente.<br> È necessario correggerlo in 'gestione fornitori' per poter salvare l'affidamento.");
    } else {
      this.dialogRef.close(new SalvaFornitoreRequest(null, this.fornitoreSelected.idFornitore, +this.ruoloSelected.codice));
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

}
