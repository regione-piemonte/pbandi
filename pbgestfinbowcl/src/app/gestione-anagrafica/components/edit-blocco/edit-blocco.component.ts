/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { getLocaleDateFormat, getLocaleDateTimeFormat } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { subscribeOn } from 'rxjs/operators';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BloccoVO } from '../../commons/dto/bloccoVO';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';

@Component({
  selector: 'app-edit-blocco',
  templateUrl: './edit-blocco.component.html',
  styleUrls: ['./edit-blocco.component.scss']
})
export class EditBloccoComponent implements OnInit {
  subscribers: any ={}; 
  modifica: number;
  verificaTipoSoggetto: any ;
  idEnteGiuridico: any;
  idSoggetto: any;
  dataBlocco :  Date = new Date(); 
  listaCausali:  Array<AttivitaDTO> = new Array<AttivitaDTO>(); 
  causaleBloccoControl =  new FormControl('', Validators.required);
  idCausaleBlocco:  number; 
  causaleDTO: AttivitaDTO = new AttivitaDTO; 
  bloccoVO:  BloccoVO = new BloccoVO(); 
  idUtente: number
  isSave: boolean;
  user: UserInfoSec; 
  dataSblocco: Date =  new Date();
  domanda: number ; 
  SbloccoDom: Boolean;
  numeroDomanda: string;

  constructor( private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private userService: UserService,
    public dialogRef: MatDialogRef<EditBloccoComponent>,
    private sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data=>{
      if(data)
      this.user = data; 
      this.idUtente =this.user.idUtente; 
    })
    this.modifica = this.data.modifica;
    this.domanda= this.data.domanda; 
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get('numeroDomanda');
    
    if(this.modifica==1){
      this.getListaCausali(); 
    }
    
    if(this.domanda==1){
      this.getListaCausaliDomanda();
    }
    
  }
  getListaCausali(){
    this.subscribers.getListaCausali = this.anagBeneService.getListaCausali(this.idSoggetto).subscribe(data=>{
      if(data){
        this.listaCausali = data; 
      }
    })
  }
  confermaBlocco(){
    this.bloccoVO.dataInserimentoBlocco = this.dataBlocco ; 
    this.bloccoVO.idCausaleBlocco = this.idCausaleBlocco; 
    this.bloccoVO.idSoggetto = this.idSoggetto; 
    this.bloccoVO.idUtente = this.idUtente

    if(this.domanda==1){
      this.subscribers.insert = this.anagBeneService.insertBloccoDomanda(this.bloccoVO, this.idSoggetto, this.numeroDomanda).subscribe(data=>{
        if(data){
          this.isSave =data; 
          this.dialogRef.close(this.isSave);
        }else {
          this.isSave = data; 
          this.dialogRef.close(this.isSave); 
        }
      })
     
    } else {

      console.log (this.bloccoVO)
      this.subscribers.insertBlocco = this.anagBeneService.insertBlocco(this.bloccoVO).subscribe(data=>{
        if (data){
          this.isSave =data; 
          this.dialogRef.close(this.isSave);
        }
      })
    }

  }
  confermaSblocco(){
    this.bloccoVO.dataInserimentoSblocco = this.dataSblocco; 
    this.bloccoVO.idBlocco = this.data.bloccoVO.idBlocco; 
    this.bloccoVO.idSoggetto = this.idSoggetto; 
    this.bloccoVO.idUtente = this.idUtente; 
    this.bloccoVO.idCausaleBlocco = this.data.bloccoVO.idCausaleBlocco;
    this.bloccoVO.descCausaleBlocco  = this.causaleDTO.descAttivita; 
    this.bloccoVO.numeroDomanda = this.numeroDomanda; 
    this.bloccoVO.dataInserimentoBlocco = this.data.bloccoVO.dataInserimentoBlocco;
    

    if(this.domanda==2){
      
      console.log(this.idUtente+ " "+ this.bloccoVO.idBlocco + " " +this.bloccoVO.idUtente); 
      this.bloccoVO.idUtente = this.idUtente;  
      this.subscribers.save = this.anagBeneService.modificaBloccoDomanda(this.bloccoVO,  this.numeroDomanda).subscribe(
        data=>{
          if(data){
            this.SbloccoDom = data; 
            this.dialogRef.close(this.SbloccoDom); 
          }
        }
      )
    } else {
      this.subscribers.modifica = this.anagBeneService.modificaBlocco(this.bloccoVO).subscribe(data=>{
        if(data){
          this.isSave = data; 
          this.dialogRef.close(this.isSave); 
        } else {
          this.isSave = data; 
          this.dialogRef.close(this.isSave); 
        }
      })
    }
  }
  closeDialog() {
    this.dialogRef.close();
  }
  getListaCausaliDomanda(){
    this.subscribers.getListaCausaliDomanda = this.anagBeneService.getListaCausaliDomanda(this.idSoggetto, this.numeroDomanda).subscribe(data=>{
      if(data){
        this.listaCausali= data; 
      }
    })
  }


}
