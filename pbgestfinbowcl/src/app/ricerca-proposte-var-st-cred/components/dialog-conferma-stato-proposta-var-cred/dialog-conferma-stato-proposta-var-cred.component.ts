/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SalvaVariazioneStatoCreditVO } from '../../commons/dto/salva-var-stato-cred-vo';
import { PropostaVarazioneStatoCreditoDTO } from '../../commons/dto/storico-proposta-var-st-cred-DTO';
import { RicercaProposteVarStCredService } from '../../services/ricerca-proposte-var-st-cred.service';

@Component({
  selector: 'app-dialog-conferma-stato-proposta-var-cred',
  templateUrl: './dialog-conferma-stato-proposta-var-cred.component.html',
  styleUrls: ['./dialog-conferma-stato-proposta-var-cred.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogConfermaStatoPropostaVarCredComponent implements OnInit {

  tipoConferma: boolean; 
  element:  PropostaVarazioneStatoCreditoDTO = new PropostaVarazioneStatoCreditoDTO; 
  subscribers: any={};
  user: UserInfoSec; 
  idUtente: number;
  variazioneCredito: SalvaVariazioneStatoCreditVO; 
  isSalvato: boolean; 
  beneficiario: string; 
  descNuovoStato: any;
  messaggio: string;
  

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private proposteService: RicercaProposteVarStCredService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogConfermaStatoPropostaVarCredComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    
   


    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.element = this.data.element; 
        this.tipoConferma = this.data.tipoConferma; 
        this.descNuovoStato = this.data.element.descNuovoStatoCred ;
        this.beneficiario = ((this.element.nome!=null)? this.element.nome : "")+ ' '+this.element.cognome; 
        if(this.tipoConferma){
          if(this.descNuovoStato=="In bonis"){
            this.messaggio =" Lo stato credito per "+this.beneficiario+" sarà modificato da <b>" +this.element.descStatoCredFin +"</b> a <b>"+
            this.element.descNuovoStatoCred +"</b>";
          } else {
            this.messaggio =" Lo stato credito per "+this.beneficiario+" sarà modificato da <b>" +this.element.descStatoCredFin +"</b> a <b>"+
            this.element.descNuovoStatoCred + "</b> generando <b>blocco anagrafico</b> e <b>proposta di revoca dove previsto</b>";
          }
        } else{
          this.messaggio =" Lo stato credito per "+this.beneficiario+"  <b>NON</b> sarà modificato da <b>" +this.element.descStatoCredFin +"</b> a <b>"+
          this.element.descNuovoStatoCred +"</b>";
        }
      }
    }); 
  } 

  salvaStato(){

    this.variazioneCredito =  new SalvaVariazioneStatoCreditVO();  
    if(this.tipoConferma == true){
      this.variazioneCredito.flagStatoCred='S';
      this.variazioneCredito.idVariazioneStatoCredito= this.element.idVariazStatoCredito; 
    } else{
      this.variazioneCredito.flagStatoCred='N';
    }
    this.variazioneCredito.idUtenteAgg = this.idUtente;
    this.variazioneCredito.idVariazioneStatoCredito = this.element.idVariazStatoCredito; 

    

    this.subscribers.salvaStatoCred = this.proposteService.salvaStatoCredito(this.variazioneCredito).subscribe(data=>{
        if(data)
        this.isSalvato = data; 
        this.dialogRef.close(this.isSalvato);
    })

  }
  closeDialog() {
    this.dialogRef.close();
  }

}
