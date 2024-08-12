import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { DocumentoDiPagamentoDTO } from '../../commons/dto/documento-di-pagamento-dto';
import { PagamentoFormDTO } from '../../commons/dto/pagamento-form-dto';
import { PagamentoVoceDiSpesaDTO } from '../../commons/dto/pagamento-voce-di-spesa-dto';
import { VoceDiSpesaDTO } from '../../commons/dto/voce-di-spesa-dto';
import { AssociaPagamentoRequest } from '../../commons/requests/associa-pagamento-request';
import { DocumentoDiSpesaService } from '../../services/documento-di-spesa.service';

@Component({
  selector: 'app-nuovo-modifica-quietanza-dialog',
  templateUrl: './nuovo-modifica-quietanza-dialog.component.html',
  styleUrls: ['./nuovo-modifica-quietanza-dialog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoModificaQuietanzaDialogComponent implements OnInit {

  isNuovo: boolean;
  idDocumentoDiSpesa: number;
  idProgetto: number;
  idBandoLinea: number;
  isValidazione: boolean;
  user: UserInfoSec;
  quietanza: DocumentoDiPagamentoDTO;
  modalita: Array<DecodificaDTO>;
  modalitaDefault: Array<DecodificaDTO>;
  modalitaSelected: DecodificaDTO;
  causali: Array<DecodificaDTO>;
  causaleSelected: DecodificaDTO;
  date: FormControl = new FormControl();
  importo: number;
  importoFormatted: string;
  riferimento: string;
  causaleQuietanzaVisibile: boolean;
  residuoQuietanzabile: number;
  isConferma: boolean = false;
  tipoOperazioneFrom: string;
  vociDiSpesa: Array<VoceDiSpesaDTO>;
  isQuietanzaNonDisponibileChecked: boolean = false;
  isBR62: boolean = false;
  quietanze: number = 0;
  ggQuietanza: number = 0;
  dataDocumento: Date;

  @ViewChild("quietanzaForm", { static: true }) quietanzaForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedSave: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<NuovoModificaQuietanzaDialogComponent>,
    private documentoDiSpesaService: DocumentoDiSpesaService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private _adapter: DateAdapter<any>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.isNuovo = this.data.isNuovo;
    this.idDocumentoDiSpesa = this.data.idDocumentoDiSpesa;
    this.idProgetto = this.data.idProgetto;
    this.idBandoLinea = this.data.idBandoLinea;
    this.isValidazione = this.data.isValidazione;
    this.user = this.data.user;
    this.modalita = this.data.modalita;
    this.causali = this.data.causali;
    this.causaleQuietanzaVisibile = this.data.causaleQuietanzaVisibile;
    this.vociDiSpesa = this.data.vociDiSpesa;
    this.residuoQuietanzabile = this.data.residuoQuietanzabile;
    this.isBR62 = this.data.isBR62;
    this.quietanze = this.data.quietanze;
    this.ggQuietanza = this.data.ggQuietanza;
    this.dataDocumento = this.data.dataDocumento;

    if (!this.isNuovo) {
      this.quietanza = this.data.quietanza;
      this.modalitaDefault = [];
      if(this.quietanza.flagPagamento == "S"){
        this.isQuietanzaNonDisponibileChecked = true;
        this.importo = 0;
        this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
        this.modalitaSelected = {
          id: 5,
          descrizione: "Varie",
          descrizioneBreve: "Varie"
        }
        this.modalitaDefault.push(this.modalitaSelected);
        this.date = new FormControl(new Date());
      }else{
        this.modalitaSelected = this.modalita.find(m => m.id === this.quietanza.idModalitaPagamento);
        //scambio mese e giorno per avere il formato corretto per new Date()
        let dtSplit = this.quietanza.dtPagamento.split("/");
        let dt = dtSplit[1] + "/" + dtSplit[0] + "/" + dtSplit[2];
        this.date.setValue(new Date(dt));
        this.importo = this.quietanza.importoPagamento;
        this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
      }
      if (this.causaleQuietanzaVisibile) {
        this.causaleSelected = this.causali.find(m => m.id === +this.quietanza.idCausalePagamento);
        this.riferimento = this.quietanza.rifPagamento;
      }
    }
    this.tipoOperazioneFrom = this.data.tipoOperazioneFrom;
  }

  isVisibileCheckBox(): boolean {
    return (this.isBR62 && this.quietanze == 0 && this.tipoOperazioneFrom == "nuova") ||
           (this.isBR62 && this.quietanze < 2 && this.tipoOperazioneFrom == "modifica");
  }

  checkGgQuietanza(){
    this.resetMessageWarning();
    const selectedDate = this.date.value._d;
    const maxDate = new Date(this.dataDocumento.getTime() + (this.ggQuietanza * 24 * 60 * 60 * 1000));
    if(!(selectedDate.getTime() <= maxDate.getTime()) && this.ggQuietanza != undefined){
      this.showMessageWarning("Il documento di spesa potrebbe essere INVALIDATO poiché la data della quietanza supera i " + this.ggQuietanza + " giorni.");
    }
  }

  checkedQuietanzaNonDisponibile(){
    if(!this.isQuietanzaNonDisponibileChecked){
      this.modalitaDefault= [];
      this.importo = 0;
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
      this.modalitaSelected = {
        id: 5,
        descrizione: "Varie",
        descrizioneBreve: "Varie"
      }
      this.modalitaDefault.push(this.modalitaSelected);
      this.date = new FormControl(new Date());
    }else{
      this.date = new FormControl("");
      this.importo = 0;
      this.importoFormatted = "";
    }
  }

  setImporto() {
    this.importo = this.sharedService.getNumberFromFormattedString(this.importoFormatted);
    if (this.importo !== null) {
      this.importoFormatted = this.sharedService.formatValue(this.importo.toString());
    }
  }

  controlNumberLengthQuientanzaForm(name: string, min: number, max: number) {
    if (this.quietanzaForm.form.get(name) && this.quietanzaForm.form.get(name).value) {
      let s: string = this.quietanzaForm.form.get(name).value.toString();
      let length: number;
      s = s.replace(/\./g, '');
      if (s.includes('e')) {
        let n = s.split('e');
        length = +n[1] + 1;
      } else {
        let stringa = s.split(',');
        length = stringa[0].length;
      }
      if (s.includes)
        if (length < min || length > max) {
          this.quietanzaForm.form.get(name).setErrors({ error: 'wrongLength' });
          return true;
        }
    }
    return false;
  }

  controlNumberPositiveQuietanzaForm(name: string) {
    if (this.quietanzaForm.form.get(name) && this.quietanzaForm.form.get(name).value !== undefined && this.quietanzaForm.form.get(name).value !== null) {
      let value: number;
      value = this.sharedService.getNumberFromFormattedString(this.quietanzaForm.form.get(name).value);
      if (value <= 0) {
        this.quietanzaForm.form.get(name).setErrors({ error: 'negative' });
        return true;
      }
    }
  }

  validate() {
    if ((this.controlNumberLengthQuientanzaForm("importo", 1, 13) || this.controlNumberPositiveQuietanzaForm("importo")) && !this.isQuietanzaNonDisponibileChecked) {
      return false;
    }
    if ((this.isNuovo && this.importo > this.residuoQuietanzabile)
      || (!this.isNuovo && this.importo > (this.residuoQuietanzabile + this.quietanza.importoPagamento))) {
      this.showMessageError("Il residuo disponibile per l’associazione a pagamenti è di " +
        this.sharedService.formatValue((this.isNuovo ? this.residuoQuietanzabile : this.residuoQuietanzabile + this.quietanza.importoPagamento).toString())
        + " Euro.<br>Inserire un valore minore o uguale a tale residuo.");
      return false;
    }
    return true;
  }

  salva() {
    this.resetMessageError();
    if (this.validate()) {
      this.loadedSave = false;
      let pag = new PagamentoFormDTO(!this.isNuovo && this.quietanza ? this.quietanza.id : null, this.modalitaSelected.id, this.date.value, this.importo,
        this.causaleSelected ? this.causaleSelected.id : null, this.isQuietanzaNonDisponibileChecked ? "S" : null, this.riferimento);
      let request = new AssociaPagamentoRequest(pag, this.idDocumentoDiSpesa, this.idProgetto, this.idBandoLinea, this.isConferma, this.isValidazione,
        this.vociDiSpesa && this.vociDiSpesa.length ? this.vociDiSpesa.map(v => new PagamentoVoceDiSpesaDTO(v.idVoceDiSpesa, v.idQuotaParteDocSpesa,
          v.idRigoContoEconomico, v.descVoceDiSpesa, v.importoRendicontato, v.importo, null)) : []);
      this.subscribers.salva = this.documentoDiSpesaService.associaQuietanza(request).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.dialogRef.close(data.messaggio);
          } else {
            this.showMessageError(data.messaggio);
            this.isConferma = data.messaggio && data.messaggio.includes("proseguire") ? true : false;
          }
        }
        this.loadedSave = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel salvataggio della quietanza");
        this.loadedSave = true;
      });
    }
  }

  close() {
    this.dialogRef.close();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    return !this.loadedSave;
  }

}
