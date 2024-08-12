import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HandleExceptionService } from '@pbandi/common-lib';
import { GestioneProrogheVO } from '../../commons/dto/gestione-proroghe-dto';
import { ValidazioneService } from '../../services/validazione.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-gestione-proroga',
  templateUrl: './gestione-proroga.component.html',
  styleUrls: ['./gestione-proroga.component.scss']
})

export class GestioneProrogaComponent implements OnInit {
  stepProroga: number;
  isSubmitted :boolean = false;
  myForm: FormGroup;
  inviataDisplayedColumns : string[] =  ['dtRichiesta','ggRichiesti','motivazione','ggApprovati','statoProroga','dtRichiestaIntegrazione'];
  errore = null;
  message : string;
  idDichiarazioneDiSpesa: number;
  idIntregrazioneSpesa: number;
  idProgetto: number;
  prorogaDaElaborare: GestioneProrogheVO;
  //storicoProroghe: GestioneProrogheVO;
  storicoProroghe: Array<GestioneProrogheVO>;
  stepProrogaSecond: number;
  numeroDefault: any;
  dataSource ;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialogRef<GestioneProrogaComponent>,
    public fb: FormBuilder,
    public datepipe: DatePipe,
    private validazioneService: ValidazioneService,
    private handleExceptionService: HandleExceptionService,
    ) {
     }

  ngOnInit(): void {
    this.idDichiarazioneDiSpesa = this.data.idDichiarazioneDiSpesa;
    this.idIntregrazioneSpesa = this.data.idIntregrazioneSpesa;
    this.idProgetto = this.data.idProgetto;
    this.prorogaDaElaborare = this.data.prorogaDaElaborare;
    this.stepProroga = this.data.stepProroga;
    this.storicoProroghe = this.data.storicoProroghe
    this.numeroDefault = this.data.prorogaDaElaborare[0]?.numGiorniRichProroga;
    this.dataSource = new MatTableDataSource<GestioneProrogheVO>(this.storicoProroghe);
    this.myForm = this.fb.group({
      numGiorni: [this.numeroDefault ,[Validators.required],]
    });

    this.getStepProroga();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  getStepProroga(){
    switch (this.stepProroga) {
      case  1:
        this.stepProrogaSecond = 1;
        break;
      case  2:
        this.stepProrogaSecond = 2;
        break;
      case  3:
        this.stepProrogaSecond = 3;
        break;
      default:
        break;
    } 


  }

  get myForms() {
    return this.myForm.value;
  }

  approvaProroga() {
    this.isSubmitted = true;
    if (!this.myForm.valid) {
      return false;
    } 
    else {
      let numGiorni = this.myForm.get('numGiorni').value;
      if(numGiorni > 0){
        this.validazioneService.approvaProroga(this.data.prorogaDaElaborare[0].idRichiestaProroga,numGiorni).subscribe(
            (data) => {
              this.isSubmitted = false;
                if (data  == true) {
                  this.message = "Proroga approvata con successo";
                  this.errore = false;
                  setTimeout(() => {
                    this.errore = null;
                    this.closeDialog();
                  }, 2000); 
                }
                else if (data === false) {
                  this.errore = true;
                  this.message = "Non è stato possibile approvare la proroga";
                }
                  },
                  (err) => {
                    if(err.ok == false){
                      this.errore = true;
                      this.message = err.statusText;
                    }
                    }
                  );   
      } else {
        return false;
      }
    }
  }

  respingiProroga(){
    this.validazioneService.respingiProroga(this.data.prorogaDaElaborare[0].idRichiestaProroga).subscribe(
        (data) => {
           this.isSubmitted = false;
            if (data  == true) {
              this.message = "Proroga respinta con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000); 
            }
            else if (data === false) {
              this.errore = true;
              this.message = "Non è stato possibile respingere la proroga";
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



  controllaForm() {
    this.isSubmitted = true;
  }

  closeDialog() {
    this.dialog.close();
}

}
