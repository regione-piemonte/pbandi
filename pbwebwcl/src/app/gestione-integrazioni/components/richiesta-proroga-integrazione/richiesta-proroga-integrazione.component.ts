import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { HandleExceptionService } from '@pbandi/common-lib';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';

@Component({
  selector: 'app-richiesta-proroga-integrazione',
  templateUrl: './richiesta-proroga-integrazione.component.html',
  styleUrls: ['./richiesta-proroga-integrazione.component.scss']
})
export class RichiestaProrogaIntegrazioneComponent implements OnInit {
  isSubmitted = false;
  myForm: FormGroup;
  motivazioni: Array<Object> = [];
  date: Date;
  errore = null;
  message;
  isLoading = false;
  stepProroga;
  inviataDataSource;
  inviataDisplayedColumns : string[] =  ['dtRichiesta','ggRichiesti','motivazione','ggApprovati','statoProroga'];
  inviataDataSource2: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  ambito : string ;
 // @ViewChild(MatSort) sort: MatSort;
  

  constructor(
    public fb: FormBuilder,
    public datepipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private integrazioniService: GestioneIntegrazioniService,
    private handleExceptionService: HandleExceptionService,
    public dialog: MatDialogRef<RichiestaProrogaIntegrazioneComponent>) 
  
  {
    this.myForm = this.fb.group({
      ggRichiesti: ['',[Validators.required],],
      motivazione: [this.data.dataKey.motivazione],
    });
   }

  ngOnInit(): void {
    this.stepProroga = this.data.dataKey2;
    this.ambito = this.data.dataKey4;
    this.getInfoProroghe();
  }


  getInfoProroghe(){
      if(this.ambito === 'revoca'){
        this.getInfoProrogheRevoche();
      }
      
      else if (this.ambito === 'controlli'){
        this.getInfoProrogheControlli();
      }

      else if (this.ambito === 'rendicontazione'){
     //   this.inviataDisplayedColumns =  ['dtRichiesta','ggRichiesti','motivazione','ggApprovati','statoProroga','dtRichiestaIntegrazione'];
        this.getInfoProrogheRendicontazione();
      }
  }
  
  getInfoProrogheControlli() {
    this.integrazioniService.getInfoProrogheControlli(this.data.dataKey.idIntegrazione,this.data.dataKey.idControllo,this.data.dataKey3).subscribe(data => {
   
      this.inviataDataSource = data;
     //   this.inviataDataSource.sort = this.sort;
      
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    }); 
  } 

  getInfoProrogheRevoche(){
    this.integrazioniService.getInfoProrogheRevoche(this.data.dataKey.idRichIntegrazione).subscribe(data => {
     
      this.inviataDataSource = data;
     //   this.inviataDataSource.sort = this.sort;
      
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    }); 
  } 

  getInfoProrogheRendicontazione() {
    this.integrazioniService.getInfoProrogheRendicontazione(this.data.dataKey.idIntegrazioneSpesa).subscribe(data => {
      this.inviataDataSource = data;
     //   this.inviataDataSource.sort = this.sort;
      
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    }); 
  } 

  get myForms() {
    return this.myForm.value;
  }

  onSubmit() {
    this.isSubmitted = true;
    if (!this.myForm.valid) {
      return false;
    } 
    else {
    let datiBackend = this.myForm.value;

    if(this.ambito === 'controlli'){
      this.integrazioniService.inserisciRichProrogaControlli(datiBackend,this.data.dataKey.idIntegrazione).subscribe(
        (data) => {
           this.isSubmitted = false;
            if (data  == true) {
              this.message = "Proroga inserita con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000); 
            }
            else if (data === false) {
              this.errore = true;
              this.message = "Non è stato possibile inserire la proroga";
            }
              },
              (err) => {
                if(err.ok == false){
                  this.errore = true;
                  this.message = err.statusText;
                }
                }
              );  
    } 
    else if(this.ambito === 'revoca'){
          this.integrazioniService.inserisciRichProrogaRevoche(datiBackend,this.data.dataKey.idRichIntegrazione).subscribe(
            (data) => {
               this.isSubmitted = false;
                if (data  == true) {
                  this.message = "Proroga inserita con successo";
                  this.errore = false;
                  setTimeout(() => {
                    this.errore = null;
                    this.closeDialog();
                  }, 2000); 
                }
                else if (data === false) {
                  this.errore = true;
                  this.message = "Non è stato possibile inserire la proroga";
                }
                  },
                  (err) => {
                    if(err.ok == false){
                      this.errore = true;
                      this.message = err.statusText;
                    }
                    }
                  );  
    }

    else if(this.ambito === 'rendicontazione'){
      this.integrazioniService.inserisciRichProrogaRendicontazione(datiBackend,this.data.dataKey.idIntegrazioneSpesa).subscribe(
        (data) => {
           this.isSubmitted = false;
            if (data  == true) {
              this.message = "Proroga inserita con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000); 
            }
            else if (data === false) {
              this.errore = true;
              this.message = "Non è stato possibile inserire la proroga";
            }
              },
              (err) => {
                if(err.ok == false){
                  this.errore = true;
                  this.message = err.statusText;
                }
                }
              );  
}
        
    }
  }



  controllaForm() {
    this.isSubmitted = true;
  }

  closeDialog() {
    this.dialog.close();
}

}
