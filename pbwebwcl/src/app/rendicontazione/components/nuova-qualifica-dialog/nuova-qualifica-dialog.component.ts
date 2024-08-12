import { UserService } from './../../../core/services/user.service';
import { UserInfoSec } from './../../../core/commons/dto/user-info';
import { NgForm } from '@angular/forms';
import { HandleExceptionService } from './../../../core/services/handle-exception.service';
import { RendicontazioneService } from './../../services/rendicontazione.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { QualificaFormDTO } from '../../commons/dto/qualifica-form-dto';
import { SalvaQualificaRequest } from '../../commons/requests/salva-qualifica-request';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';

@Component({
  selector: 'app-nuova-qualifica-dialog',
  templateUrl: './nuova-qualifica-dialog.component.html',
  styleUrls: ['./nuova-qualifica-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaQualificaDialogComponent implements OnInit {

  @ViewChild("nuovaQualificaForm", { static: true }) nuovaQualificaForm: NgForm;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<NuovaQualificaDialogComponent>,
    public rendicontazioneService: RendicontazioneService,
    public handleExceptionService: HandleExceptionService,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public idFornitore: number
  ) { }

  qualifiche: Array<DecodificaDTO>;
  qualificaSelected: DecodificaDTO;
  costoOrario: number;
  nota: string;
  hasValidationError: boolean;
  user: UserInfoSec;

  ngOnInit(): void {
    this.qualifiche = new Array<DecodificaDTO>();

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.rendicontazioneService.getQualifiche(this.user).subscribe(dataQualifiche => {
          this.qualifiche = dataQualifiche;
          this.qualificaSelected = this.qualifiche[0];
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    })
  }

  salva() {
    this.validazioneCampi();
    if (!this.hasValidationError) {
      var datiForm = new QualificaFormDTO(null, this.idFornitore, this.qualificaSelected.id, this.costoOrario, this.nota);
      this.dialogRef.close({ data: true, request: new SalvaQualificaRequest(datiForm, this.user.idUtente) });
    }
  }

  validazioneCampi() {
    this.hasValidationError = false;

    this.controlRequiredNuovaQualificaForm("costoOrario");
    this.controlRequiredNuovaQualificaForm("nota");
  }

  controlRequiredNuovaQualificaForm(name: string) {
    if (!this.nuovaQualificaForm.form.get(name) || !this.nuovaQualificaForm.form.get(name).value) {
      this.nuovaQualificaForm.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  close() {
    this.dialogRef.close();
  }
}