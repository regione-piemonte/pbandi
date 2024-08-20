/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { InterventoSostitutivoVO } from '../../commons/intervento-sostitutivo-vo';
import { DestinatarioInterventoSostVO } from '../../commons/destinatario-intervento-sost-vo';

@Component({
  selector: 'app-nuovo-intervento-sostitutivo-dialog',
  templateUrl: './nuovo-intervento-sostitutivo-dialog.component.html',
  styleUrls: ['./nuovo-intervento-sostitutivo-dialog.component.scss']
})
export class NuovoInterventoSostitutivoDialogComponent implements OnInit {
  importoBeneficiario: number;
  importoValido: boolean = false;
  destinatari: DestinatarioInterventoSostVO[] = [];

  listaInterventi:InterventoSostitutivoVO[] = [];

  myForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private resService: ControlliPreErogazioneResponseService,
    private dialogRef: MatDialogRef<NuovoInterventoSostitutivoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      importoBeneficiario: number,
      listaInterventi: InterventoSostitutivoVO[]
    }
  ) { }

  ngOnInit(): void {
    this.importoBeneficiario = this.data.importoBeneficiario;
    this.listaInterventi = this.data.listaInterventi;
    this.popolaDestinatari();

    this.myForm = this.fb.group({
      destinatario: new FormControl("", Validators.required),
      iban: new FormControl(""),
      importoDaErogare: new FormControl("", Validators.required),
    });

    console.log(this.data);
  }

  importoTotale() : number {
    let importo: number = this.importoBeneficiario;

    this.listaInterventi.forEach(element => importo = importo + element.importoDaErogare);

    return importo;
  }

  popolaDestinatari() {
    this.resService.getDestinatariIntervento().subscribe(data =>{
      this.destinatari = data;
    })
  }

  popolaIban() {
    let formControls = this.myForm.getRawValue();

    this.myForm.get('iban').setValue(formControls.destinatario.iban);
  }

  displayDestinatario(element: DestinatarioInterventoSostVO): string {
    return element ? element.denominazione : '';
  }

  checkImporto(){
    let formControls = this.myForm.getRawValue();
    this.importoValido = this.importoBeneficiario - formControls.importoDaErogare > 0;
    console.log(this.importoBeneficiario - formControls.importoDaErogare > 0);
  }

  salva() {
    let formControls = this.myForm.getRawValue();
    console.log(formControls);

    let interventoSostitutivo: InterventoSostitutivoVO = new InterventoSostitutivoVO();

    interventoSostitutivo.idDestinatario = formControls.destinatario.idDestinatario;
    interventoSostitutivo.denominazione = formControls.destinatario.denominazione;
    interventoSostitutivo.iban = formControls.iban;
    interventoSostitutivo.importoDaErogare = formControls.importoDaErogare;

    this.dialogRef.close(interventoSostitutivo);
  }

  annulla() {
    this.dialogRef.close();
  }

}