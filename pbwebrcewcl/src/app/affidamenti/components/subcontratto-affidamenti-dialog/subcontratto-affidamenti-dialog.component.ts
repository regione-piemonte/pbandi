/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TipoSoggetto } from '../../commons/dto/tipo-soggetto';
import { FornitoreDTO } from 'src/app/shared/commons/dto/fornitore-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { SubcontrattoRequest } from '../../commons/requests/subcontratto-request';
import { SubcontrattoAffidDTO } from '../../commons/dto/subcontratto-affid-dto';

@Component({
  selector: 'app-subcontratto-affidamenti-dialog',
  templateUrl: './subcontratto-affidamenti-dialog.component.html',
  styleUrls: ['./subcontratto-affidamenti-dialog.component.scss']
})
export class SubcontrattoAffidamentiDialogComponent implements OnInit {

  idSubcontrattoAffidamento: number;
  subcontratto: SubcontrattoAffidDTO;
  fornitori: Array<FornitoreDTO>;
  fornitoriFiltered: Array<FornitoreDTO>;
  fornitoreSelected: FornitoreDTO;
  tipologie: Array<TipoSoggetto> = new Array<TipoSoggetto>();
  tipologiaSelected: TipoSoggetto;

  dataContratto: FormControl = new FormControl();
  riferimento: string;
  importoFormatted: string;
  importo: number;

  @ViewChild("subcontraenteForm", { static: true }) subcontraenteForm: NgForm;
  @ViewChild("subcontrattoForm", { static: true }) subcontrattoForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<SubcontrattoAffidamentiDialogComponent>,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.fornitori = this.data.fornitori;
    this.subcontratto = this.data.subcontratto;
    if (this.subcontratto) {
      this.idSubcontrattoAffidamento = this.subcontratto.idSubcontrattoAffidamento;
      this.dataContratto.setValue(new Date(this.subcontratto.dtSubcontratto));
      this.riferimento = this.subcontratto.riferimentoSubcontratto;
      this.importo = this.subcontratto.importoSubcontratto;
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
    this.popolaComboTipologia();
    this.popolaComboFornitore();
  }

  onSelectTipologia() {
    this.popolaComboFornitore();
  }

  popolaComboTipologia() {
    this.fornitori.forEach(f => {
      if (!this.tipologie.find(t => t.idTipoSoggetto === f.idTipoSoggetto)) {
        this.tipologie.push(new TipoSoggetto(f.idTipoSoggetto, f.descTipoSoggetto));
      }
    });
    this.tipologie.sort((a, b) => a.idTipoSoggetto - b.idTipoSoggetto);
    this.tipologiaSelected = this.tipologie[0];
    if (this.idSubcontrattoAffidamento) {
      let subcontraente = this.fornitori.find(f => f.idFornitore === this.subcontratto.idSubcontraente);
      this.tipologiaSelected = this.tipologie.find(t => t.idTipoSoggetto === subcontraente.idTipoSoggetto);
    }
  }

  popolaComboFornitore() {
    this.fornitoriFiltered = this.fornitori.filter(f => f.idTipoSoggetto === this.tipologiaSelected.idTipoSoggetto);
    this.fornitoreSelected = null;
    if (this.idSubcontrattoAffidamento) {
      this.fornitoreSelected = this.fornitori.find(f => f.idFornitore === this.subcontratto.idSubcontraente);
    }
  }

  setImporto() {
    this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
  }

  isSalvaDisabled() {
    if (this.subcontraenteForm?.form?.invalid || this.subcontrattoForm?.form?.invalid || this.dataContratto?.invalid) {
      return true;
    }
    return false;
  }

  salva() {
    if (this.tipologiaSelected.idTipoSoggetto !== Constants.ID_TIPO_SOGGETTO_PERSONA_FISICA && this.fornitoreSelected.idFormaGiuridica === 0) {
      this.resetMessageError();

      this.showMessageError("Il fonitore selezionato non è formalmente corretto: forma giuridica assente.<br> È necessario correggerlo in 'gestione fornitori' per poter salvare l'affidamento.");
    } else {
      this.dialogRef.close(new SubcontrattoRequest(this.idSubcontrattoAffidamento, null, null, this.fornitoreSelected.idFornitore,
        this.riferimento, this.dataContratto.value, this.importo));
    }
  }

  annulla() {
    this.dialogRef.close("ANNULLA");
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
