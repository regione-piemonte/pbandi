/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { BeneficiarioDTO } from '../../commons/dto/beneficiario-dto';
import { DatiBeneficiarioProgettoDTO } from '../../commons/dto/dati-beneficiario-progetto-dto';
import { CambiaBeneficiarioService } from '../../services/cambia-beneficiario.service';

@Component({
  selector: 'app-confirm-cambia-beneficiario-dialog',
  templateUrl: './confirm-cambia-beneficiario-dialog.component.html',
  styleUrls: ['./confirm-cambia-beneficiario-dialog.component.scss']
})
export class ConfirmCambiaBeneficiarioDialogComponent implements OnInit {

  beneficiario : BeneficiarioDTO
  beneficiarioSubentrante : DatiBeneficiarioProgettoDTO

  constructor(
    public dialogRef: MatDialogRef<ConfirmCambiaBeneficiarioDialogComponent> ,
    @Inject(MAT_DIALOG_DATA) public data: any,
    //@Inject(MAT_DIALOG_DATA) public dati: DatiBeneficiarioProgettoDTO,
    //@Inject(MAT_DIALOG_DATA) public user: UserInfoSec,
    private cambiaBeneficiarioService: CambiaBeneficiarioService, 
    private handleExceptionService: HandleExceptionService,) {  

    this.beneficiario = data.beneficiario
    this.beneficiarioSubentrante = data.beneficiarioSubentrante

    console.log("Inizio del componente dialog, Parametri:")
    console.log(this.beneficiario)
    console.log(this.beneficiarioSubentrante)
    
  }

  ngOnInit(): void {
  }

  //FUNZIONI PULSANTI

  onConfermaClick(){
    this.dialogRef.close(true);
  }

  onAnnullaClick(){
    this.dialogRef.close(false);
  }

}
