/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AttivitaExtendedDTO } from '../../commons/dto/AttivitaExtendedDTO';
import { DichiarazioneSpesaCampionamentoVO } from '../../commons/dto/DichiarazioneSpesaVO';
import { CampionamentoService } from '../../services/campionamento.services';

@Component({
  selector: 'app-dialog-edit-nuovo-campionamento',
  templateUrl: './dialog-edit-nuovo-campionamento.component.html',
  styleUrls: ['./dialog-edit-nuovo-campionamento.component.scss']
})
export class DialogEditNuovoCampionamentoComponent implements OnInit {
  descBando: string;
  listaBando:  Array<AttivitaExtendedDTO>;
  listaBandoSelected:  Array<AttivitaExtendedDTO> = new Array<AttivitaExtendedDTO>();
  suggesttionnull: AttivitaExtendedDTO;
  subscribers: any={};
  bandoDTO: AttivitaDTO;
  bando: AttivitaDTO = new AttivitaDTO;
  element: number;
  listaTipoDichiarazSpesa : Array<AttivitaExtendedDTO> =  new Array<AttivitaExtendedDTO>();
  dichiarazioneSpesaVO: DichiarazioneSpesaCampionamentoVO = new DichiarazioneSpesaCampionamentoVO();
  statoDichiarazione: boolean;
  tipoDichiarazione: AttivitaDTO[];
  flagValidita: boolean = true;
  importoRendicontatoInizio: number;
  importoRendicontatoInizioFormatted: string
  importoRendicontatoFine: number;
  importoRendicontatoFineFormatted: string;
  importoValidatoInizio: number;
  importoValidatoFine: number;
  dataRicezioneInizio: Date;
  dataRicezioneFine: Date;
  dataUltimoEsitoInizio: Date;
  dataUltimoEsitoFine: Date;
  idTipoDichiarSpesa: number;
  tipoSpesaDTO: AttivitaExtendedDTO = new AttivitaExtendedDTO();
  listaTipoDichiarazSpesaSelected: Array<AttivitaExtendedDTO> = new Array<AttivitaExtendedDTO>();
  validata: boolean = true;
  allTipoDichSpesa:  boolean;
  @ViewChild(NgForm) importiForm:NgForm;
  importoValidatoFineFormatted: string;
  importoValidatoInizioFormatted: string;
  motivazioneEscluzione:  string; 
  isAnnullaCampionamento: boolean;
 


  constructor(
    private campionamentiService: CampionamentoService,
    public dialogRef: MatDialogRef<DialogEditNuovoCampionamentoComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.element = this.data.element;
    console.log(this.element);
    if(this.element==3){
      this.getListaTipoDichiarazSpesa();
      this.setDichiarazioneSpesa(); 
    } 
    // element = 5 corrisponde all'aggiunta della motivazione dell'esclusione di un progetto
    // element = 6 corrisponde alla riammissione di tutti i progetti esclusi
    // element = 7 corrispnde alla creazione controllo in locco con successo
    // element = 8 corrispnde annulla controllo in loco con successo
  
    
  }
  setDichiarazioneSpesa() {
    console.log("setDichiarazione");
    this.dichiarazioneSpesaVO = this.data.dichirazioneSpesa; 
    this.importoRendicontatoFine = this.dichiarazioneSpesaVO.imporRendicontatoFine; 
    this.importoRendicontatoInizio = this.dichiarazioneSpesaVO.imporRendicontatoInizio; 
    this.importoValidatoInizio = this.dichiarazioneSpesaVO.importoValidatoInizio; 
    this.importoValidatoFine = this.dichiarazioneSpesaVO.importoValidatoFine; 
    this.dataRicezioneInizio = this.dichiarazioneSpesaVO.dataRicezioneInizio; 
    this.dataRicezioneFine = this.dichiarazioneSpesaVO.dataRicezioneFine; 
    this.dataUltimoEsitoFine = this.dichiarazioneSpesaVO.dataUltimoEsitoFine; 
    this.dataUltimoEsitoInizio = this.dichiarazioneSpesaVO.dataUltimoEsitoInizio
    this.tipoDichiarazione = this.dichiarazioneSpesaVO.tipoDichiarazione; 

    if(this.importoRendicontatoInizio)
    this.importoRendicontatoInizioFormatted = this.sharedService.formatValue(this.importoRendicontatoInizio.toString());
    if(this.importoRendicontatoFine)
    this.importoRendicontatoFineFormatted = this.sharedService.formatValue(this.importoRendicontatoFine.toString());
    if(this.importoValidatoFine)
    this.importoValidatoFineFormatted = this.sharedService.formatValue(this.importoValidatoFine.toString());
    if(this.importoValidatoInizio)
    this.importoValidatoInizioFormatted = this.sharedService.formatValue(this.importoValidatoInizio.toString());
    //  this.setImportoRendicontatoFine(); 
    //  this.setImportoRendicontatoInizio(); 
    //  this.setImportoValidatoFine(); 
    //  this.setImportoValidatoInizio(); 

   if (this.tipoDichiarazione!=null) {
	  var list =  this.tipoDichiarazione; 
	   list.map(t => {
	    var tipo =  new AttivitaExtendedDTO();
	    tipo.idAttivita=t.idAttivita;
	    tipo.descAttivita = t.descAttivita;
	    tipo.selected= true; 
	    this.listaTipoDichiarazSpesaSelected.push(tipo);
	    return tipo;
	  })
}

  //this.listaTipoDichiarazSpesaSelected.push(list); 
  }

  suggest(value: string) {

    this.suggesttionnull = new AttivitaExtendedDTO();
    if (value.length >= 3) {
      this.listaBando = new Array<AttivitaExtendedDTO>();
      this.suggesttionnull.descAttivita = 'caricamento...';
      this.listaBando.push(this.suggesttionnull);
      this.subscribers.listaUtenti = this.campionamentiService.getListaBandi(value).subscribe(data => {
        if (data.length > 0) {
          this.listaBando = <Array <AttivitaExtendedDTO>> data;
        } else {
          this.listaBando = new Array<AttivitaExtendedDTO>();
          this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
          this.listaBando.push(this.suggesttionnull);
        }
      });
    }
  }

  aggiungiBando(){

    if(this.descBando){
      var bando = this.listaBando.find(b => b.descAttivita ==this.descBando);
      if(bando && !this.listaBandoSelected.find(b => b.idAttivita==bando.idAttivita)){
        bando.selected=true;
        this.listaBandoSelected.push(bando);
        this.descBando=null;
      }
    }
  }

  confermaBando(){
    var bandi = this.listaBandoSelected.filter(b => b.selected);
    this.dialogRef.close(bandi.map(b=> {
      var bando =  new AttivitaDTO();
      bando.idAttivita=b.idAttivita;
      bando.descAttivita = b.descAttivita;
      return bando;
    }))
  }



  removeBando(bando: AttivitaDTO){
    for(var i =0; i<this.listaBandoSelected.length;i++){
      if(this.listaBandoSelected[i].idAttivita=bando.idAttivita){
        this.listaBandoSelected.splice(i,1);
      }
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
  getListaTipoDichiarazSpesa(){
    this.subscribers.listaTipoDichiarazSpesa =  this.campionamentiService.getTipoDichiaraziSpesa().subscribe(data =>{
      if(data){
        this.listaTipoDichiarazSpesa = <Array<AttivitaExtendedDTO>>data;
      }
    })
  }
  aggiungiTipoDichiarazSpesa(){

    if(this.idTipoDichiarSpesa){
      var tipo = this.listaTipoDichiarazSpesa.find(b => b.idAttivita ==this.idTipoDichiarSpesa);
      if(tipo && !this.listaTipoDichiarazSpesaSelected.find(b => b.idAttivita==tipo.idAttivita)){
        tipo.selected=true;
        this.listaTipoDichiarazSpesaSelected.push(tipo);
        this.idTipoDichiarSpesa = null;
      }
    }
  }
  tutti(event: MatCheckboxChange){
    if(event.checked){

      for(let tipo of this.listaTipoDichiarazSpesa){
        if(!this.listaTipoDichiarazSpesaSelected.find(t => t.idAttivita == tipo.idAttivita)){
          tipo.selected = true;
          this.listaTipoDichiarazSpesaSelected.push(tipo);
        }
      }
    } else {
      this.listaTipoDichiarazSpesaSelected.splice(0,this.listaTipoDichiarazSpesaSelected.length);
    }
  }
  setImportoRendicontatoInizio(){
    this.importoRendicontatoInizio = this.sharedService.getNumberFromFormattedString(this.importoRendicontatoInizioFormatted);
    if (this.importoRendicontatoInizio !== null) {
      this.importoRendicontatoInizioFormatted = this.sharedService.formatValue(this.importoRendicontatoInizio.toString());
    }
    // this.importiForm.form.get('importoRendicontatoDa').setErrors({error: 'min'});
  }

  setImportoRendicontatoFine(){
    this.importoRendicontatoFine = this.sharedService.getNumberFromFormattedString(this.importoRendicontatoFineFormatted);
    if (this.importoRendicontatoFine !== null) {
      this.importoRendicontatoFineFormatted = this.sharedService.formatValue(this.importoRendicontatoFine.toString());
    }
  }
  setImportoValidatoInizio(){
    this.importoValidatoInizio = this.sharedService.getNumberFromFormattedString(this.importoValidatoInizioFormatted);
    if (this.importoValidatoInizio !== null) {
      this.importoValidatoInizioFormatted = this.sharedService.formatValue(this.importoValidatoInizio.toString());
    }
  }
  setImportoValidatoFine(){
    this.importoValidatoFine = this.sharedService.getNumberFromFormattedString(this.importoValidatoFineFormatted);
    if (this.importoValidatoFine !== null) {
      this.importoValidatoFineFormatted = this.sharedService.formatValue(this.importoValidatoFine.toString());
    }
  }

  confermaDichiarazSpesa(){

    var tipi = this.listaTipoDichiarazSpesaSelected.filter(t => t.selected);
    tipi.map(t => {
      var tipo =  new AttivitaDTO();
      tipo.idAttivita=t.idAttivita;
      tipo.descAttivita = t.descAttivita;
      return tipo;
    })
    console.log(tipi);
    this.dichiarazioneSpesaVO.dataRicezioneFine = this.dataRicezioneFine;
    this.dichiarazioneSpesaVO.dataRicezioneInizio = this.dataRicezioneInizio;
    this.dichiarazioneSpesaVO.dataUltimoEsitoFine = this.dataUltimoEsitoFine;
    this.dichiarazioneSpesaVO.dataUltimoEsitoInizio = this.dataRicezioneInizio;
    this.dichiarazioneSpesaVO.tipoDichiarazione = tipi;
    this.dichiarazioneSpesaVO.imporRendicontatoFine = this.importoRendicontatoFine;
    this.dichiarazioneSpesaVO.imporRendicontatoInizio =this.importoRendicontatoInizio;
    this.dichiarazioneSpesaVO.importoValidatoFine = this.importoValidatoFine;
    this.dichiarazioneSpesaVO.importoValidatoInizio = this.importoValidatoFine;

    console.log(this.dichiarazioneSpesaVO)

    this.dialogRef.close(this.dichiarazioneSpesaVO);
  }
  setDataNull(idData: number){
    switch (idData) {
      case 1:
        this.dataRicezioneInizio = null; 
        break;
      case 2:
        this.dataRicezioneFine = null; 
        break;
      case 3:
        this.dataUltimoEsitoInizio = null; 
        break;
      case 4:
        this.dataUltimoEsitoFine = null; 
        break;
    
      default:
        break;
    }
  }
  confermaEscluzione(){
    var result = new AttivitaExtendedDTO; 
    result.descAttivita = this.motivazioneEscluzione; 
    result.selected = true; 
    this.dialogRef.close(result); 
  }
  confermaEscluzioneTutti(){
    this.dialogRef.close(true); 
  }
  annullaCampionamento(){
    this.subscribers.annullaCampionamento = this.campionamentiService.annullaCampionamento(this.data.idCampionamento, this.data.idUtente).subscribe(data=>{
      if(data){
        this.isAnnullaCampionamento = true; 
      }
    })
  }

  ricercaCampionamento(){
    this.dialogRef.close(1); 
  }
  nuovoCampionamento(){
    this.dialogRef.close(2); 
  }

}
