/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AnteprimaDialogComponent, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';

@Component({
  selector: 'app-dettaglio',
  templateUrl: './dettaglio.component.html',
  styleUrls: ['./dettaglio.component.scss']
})
export class DettaglioComponent implements OnInit , AfterViewInit{
  
  public dialogRef: MatDialogRef<DettaglioComponent>;
  dettaglio = [];
  isMessageErrorVisible: boolean;
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegati;
  messageError: string;
  dataSource = new MatTableDataSource([]);
  contDisplayedColumns: string[] = ['nProtocollo', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio','statoRichiesta'];
  revDisplayedColumns: string[] = ['nRevoca', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'statoRichiesta'];
  rendicontazioneDisplayedColumns: string[] = ['nDichiarazioneSpesa', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'statoRichiesta'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  ambito : string ;
  stepProroga;
  inviataDataSource;
  inviataDisplayedColumns : string[] =  ['dtRichiesta','ggRichiesti','motivazione','ggApprovati','statoProroga'];
  lettaraIstruttore: any[];
  
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
  private integrazioniService: GestioneIntegrazioniService,
  private handleExceptionService: HandleExceptionService,
  private dialog: MatDialog,
  private configService: ConfigService,) { }

  ngOnInit(): void {
    console.log(this.data.dataKey4);
    
    this.dataSource  = new MatTableDataSource<any>([this.data.dataKey]);
    this.ambito = this.data.dataKey4;
    this.getInfoProroghe();
    if(this.ambito === 'revoca'){
      this.integrazioniService.getAllegatiIntegrazione(this.data.dataKey.idRichIntegrazione).subscribe(data => {
        if (data) {
          for (let a of data) {
            if(a.flagEntita === 'I'){
                this.letteraAccompagnatoria.push(Object.assign({}, a))
            }else{
              this.allegatiGenerici.push(Object.assign({}, a))
            }
            
          }
        } else {
          this.allegati = new Array<DocumentoAllegatoDTO>();
        }
        },err => {
          this.handleExceptionService.handleBlockingError(err);
        });
    }
    
    else if(this.ambito === 'controlli'){
       this.getAllegatiControlli();
       this.getLetteraIstruttore();
    }

    else if(this.ambito === 'rendicontazione'){
      this.integrazioniService.getAllegatiNuovaIntegrazioneDS(this.data.dataKey.nDichiarazioneSpesa,this.data.dataKey.idIntegrazioneSpesa).subscribe(data => { 
        if (data) {
          for (let a of data) {
            if(a.flagEntita === 'I'){
                this.letteraAccompagnatoria.push(Object.assign({}, a))
            }else{
              this.allegatiGenerici.push(Object.assign({}, a))
            }
          }
        } else {
          this.allegati = new Array<DocumentoAllegatoDTO>();
        }
         },err => {
          this.handleExceptionService.handleBlockingError(err);
        }); 
    }
    
  }

  getAllegatiControlli(){
  this.integrazioniService.getAllegatiControlli(this.data.dataKey.idIntegrazione).subscribe(data => { 
    if (data) {
      for (let a of data) {
        if(a.flagEntita === 'I'){
            this.letteraAccompagnatoria.push(Object.assign({}, a))
        }else{
          this.allegatiGenerici.push(Object.assign({}, a))
        }
      }
    } else {
      this.allegati = new Array<DocumentoAllegatoDTO>();
    }
     },err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  getLetteraIstruttore(){
    this.integrazioniService.getLetteraIstruttore(this.data.dataKey.idIntegrazione).subscribe(data => { 
      if (data) {
        this.lettaraIstruttore = [data];
        console.log(data);
        
      } else {
        
      }
       },err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  getInfoProroghe(){
      if(this.ambito === 'revoca'){
        this.getInfoProrogheRevoche();
      }
      
      else if (this.ambito === 'controlli'){
        this.getInfoProrogheControlli();
      }

      else if (this.ambito === 'rendicontazione'){
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

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  anteprimaAllegato(lettera)  {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(lettera.nomeFile); //nome del file
    comodo.push(lettera.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(lettera.codTipoDoc, lettera.nomeFile, null, null, null, null, null, lettera.idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(lettera.codTipoDoc); //dalla pbandi_t_documenti_index e pbandi_c_tipo_documento_index
  
    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  
  }
  
  isAnteprimaVisible(nomeFile: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    const splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }

  
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
