import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { RelazioneTecnicaService } from 'src/app/relazione-tecnica/services/relazione-tecnica.service';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-nota-rel-tec-dialog',
  templateUrl: './nota-rel-tec-dialog.component.html',
  styleUrls: ['./nota-rel-tec-dialog.component.scss']
})
export class NotaRelTecDialogComponent implements OnInit {

  subscirbers:any; 
  idProgetto: number;
  idRelazioneTecnica: number;
  flagConferma: string;
  tipoConferma: boolean;
  tipoRelazioneTenica: string;
  public myForm: FormGroup;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private relazioneTecnicaService: RelazioneTecnicaService,
    public dialogRef: MatDialogRef<NotaRelTecDialogComponent>,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
    this.idRelazioneTecnica = this.data.idRelazioneTecnica;
    this.flagConferma = this.data.flagConferma;
    this.tipoRelazioneTenica=  this.data.tipoRelazioneTecnica; 
    console.log(this.data.flagConferma);
    
    if (this.flagConferma=='S') {
      this.tipoConferma = true
    } else {
      this.tipoConferma= false; 
    }
    this.myForm = this.fb.group({
      motivazione: new FormControl({ value: "", disabled: false },  [Validators.required])
    });
    
  }

  closeDialog() {
    this.dialogRef.close();
  }
  confermaRelTec(){
    console.log(this.myForm.get("motivazione").value.toString());
    let nota = this.myForm.get("motivazione").value.toString(); 
    this.subscirbers.confermaRelTec= this.relazioneTecnicaService.validaRifiutaRelazioneTecnica(this.idRelazioneTecnica, this.flagConferma,nota).subscribe(
      data =>{
          if(data){
            this.dialogRef.close(data);
          } 
      } , 
      err =>{

      });
    
  }

}
