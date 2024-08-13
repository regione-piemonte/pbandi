/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { element } from 'protractor';
import { Component, Inject, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { FileFirmato } from '../../commons/dto/file-firmato';

@Component({
  selector: 'app-upload-documento-firmato-dialog',
  templateUrl: './upload-documento-firmato-dialog.component.html',
  styleUrls: ['./upload-documento-firmato-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class UploadDocumentoFirmatoDialogComponent implements OnInit {

  nomeFile: string;
  file: File;
  isBR50: boolean;
  dimMaxSingoloFile: number;

  messageError: string;
  isMessageErrorVisible: boolean = false;

  //lastFile 

  constructor(
    public dialogRef: MatDialogRef<UploadDocumentoFirmatoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.nomeFile = this.data.nomeFile;
    this.isBR50 = this.data.isBR50;
    this.dimMaxSingoloFile = this.data.dimMaxSingoloFile
    if (this.data.messageError) {
      this.showMessageError(this.data.messageError);
    }
    console.log(this.isBR50)    

  }

  handleFileInput(file) {
    /*
    var fileList : FileList = event.target.files;
    this.file = fileList.item(0);
    event = undefined;
   
    */
  console.log(file) 
   this.file = file;
  }

  //dissociate



  inviaConFirmaDigitale() {

    console.log(this.file.name)

    this.resetMessageError();
    /*if (this.file.size > this.dimMaxFileFirmato) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxFileFirmato);
      return;
    }*/
    if (this.file.size > this.dimMaxSingoloFile) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxSingoloFile);
      return
    }
    if (!this.file.name.endsWith(".p7m") && !this.file.name.endsWith(".P7M")) {
      this.showMessageError("Il file deve avere estensione .p7m");
      return 
    }
    if (!this.file.name.startsWith(this.nomeFile)) {
      this.showMessageError("Il file firmato e da caricare deve essere: " + this.nomeFile + ".p7m");
      return 
    }

    
    var data : FileFirmato = new FileFirmato(this.file, "digitale");      

    this.dialogRef.close(data);
  }


  inviaConFirmaAutografa() {

   
    this.resetMessageError();
    /*if (this.file.size > this.dimMaxFileFirmato) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxFileFirmato);
      return;
    }*/

    var check : boolean = true;

    if (this.file.size > this.dimMaxSingoloFile) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxSingoloFile);
      check = false;
    }
    if (!this.file.name.endsWith(".pdf") && !this.file.name.endsWith(".PDF")) {
      this.showMessageError("Il file deve avere estensione .pdf");
      check = false;
    }
    if (!this.file.name.startsWith(this.nomeFile)) {
      this.showMessageError("Il file firmato e da caricare deve essere: " + this.nomeFile );
      check = false;
    }   

    if(check){

      var data : FileFirmato = new FileFirmato(this.file, "autografa");   
      this.dialogRef.close(data);
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



  onTabChange(event){
    console.log(event)
    this.resetMessageError();
    //this.file = null;
  }

}
