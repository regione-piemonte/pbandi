/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatiDomandaEG } from '../../commons/dto/model-dati-domanda-eg';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { AtlanteVO } from '../../commons/dto/atlante-vo';
import { Regioni } from '../../commons/dto/regioni';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { map, startWith } from 'rxjs/operators';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Nazioni } from '../../commons/dto/nazioni';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';


@Component({
  selector: 'dialog-dettaglio-altre-sedi-EG',
  templateUrl: './dialog-dettaglio-altre-sedi-EG.component.html',
  styleUrls: ['./dialog-dettaglio-altre-sedi-EG.component.scss'],
})

export class DialogDettaglioAltreSediEGComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  rigoTipoSede: DatiDomandaEG;
  
  public myForm: FormGroup;

  public subscribers: any = {};
  datiEG: DatiDomandaEG;
  nazioneData: Nazioni[] = [];
  filteredOptionsNazioni: Observable<Nazioni[]>;
  nazioneSelected: FormControl = new FormControl();
  regioneData: Regioni[] = [];
  filteredOptionsRegioni: Observable<Regioni[]>;
  regioneSelected: FormControl = new FormControl();
  provinciaData: AtlanteVO[] = [];
  filteredOptionsProvince: Observable<AtlanteVO[]>;
  provinciaSelected: FormControl = new FormControl();
  comuneData: AtlanteVO[] = [];
  filteredOptionsComuni: Observable<AtlanteVO[]>;
  comuneSelected: FormControl = new FormControl();
  loadedNazioni: boolean = true;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;
  messageError: string;
  isMessageErrorVisible: boolean;

  constructor(
    public dialogRef: MatDialogRef<DialogDettaglioAltreSediEGComponent>,
    private fb: FormBuilder,
    private datiDomandaService: DatiDomandaService,
    private handleExceptionService: HandleExceptionService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    @Inject(MAT_DIALOG_DATA) public data: any) {

      console.log("Dialog Data: ", this.data);
    
  
      this.rigoTipoSede = this.data.row;
      this.datiEG = this.rigoTipoSede;
    
    }


  ngOnInit(): void {

    this.myForm = this.fb.group({
      //SEDE Intervento
      indirizzo: new FormControl({ value: '', disabled: true }),
      partitaIva: new FormControl({ value: '', disabled: true }),
      comune: new FormControl({ value: '', disabled: true }),
      cap: new FormControl({ value: '', disabled: true }),
      provincia: new FormControl({ value: '', disabled: true }),
      regione: new FormControl({ value: '', disabled: true }),
      nazione: new FormControl({ value: '', disabled: true }),
      
      //RECAPITI
      telefono: new FormControl({ value: '', disabled: true }),
      fax: new FormControl({ value: '', disabled: true }),
      email: new FormControl({ value: '', disabled: true }),
      pec: new FormControl({ value: '', disabled: true }),

    });

    this.myForm.setValue({
      //SEDE INTERVENTO:
      indirizzo: this.controlloCampoValido(this.datiEG.descIndirizzo),
      partitaIva: this.controlloCampoValido(this.datiEG.partitaIva),
      comune: this.controlloCampoValido(this.datiEG.descComune),
      provincia: this.controlloCampoValido(this.datiEG.descProvincia),
      cap: this.controlloCampoValido(this.datiEG.cap),
      regione: this.controlloCampoValido(this.datiEG.descRegione),
      nazione: this.controlloCampoValido(this.datiEG.descNazione),
      //RECAPITI:
      telefono: this.controlloCampoValido(this.datiEG.telefono),
      fax: this.controlloCampoValido(this.datiEG.fax),
      email: this.controlloCampoValido(this.datiEG.email),
      pec: this.controlloCampoValido(this.datiEG.pec),
    });

    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.myForm.get('nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.datiEG && this.datiEG.idNazione) {
          this.myForm.get("nazione").setValue(this.nazioneData.find(f => f.idNazione === this.datiEG.idNazione.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.datiEG.idNazione.toString()))
          if (this.datiEG.idNazione == 118) {
            this.loadRegioni(true);
          } else {
            this.loadComuniEsteri(true);
          }
        }
      }
      this.loadedNazioni = true;
    }, err => {
      //this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioni = true;
    });
  }

  click(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneSelected.value) {
          this.nazioneSelected.setValue(event.option.value);
          this.resetRegione();
          if (this.nazioneSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioni();
          } else {
            this.loadComuniEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneSelected.value) {
          this.regioneSelected.setValue(event.option.value);
          this.resetProvincia();
          this.loadProvince();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaSelected.value) {
          this.provinciaSelected.setValue(event.option.value);
          this.resetComune();
          this.loadComuniItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneSelected.value) {
          this.comuneSelected.setValue(event.option.value);
          this.setCap();
        }
        break;
      default:
        break;
    }
  }

  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.myForm.get('nazione') && this.nazioneSelected.value !== this.myForm.get('nazione').value)) {
            this.myForm.get('nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.myForm.get('regione') && this.regioneSelected.value !== this.myForm.get('regione').value)) {
            this.myForm.get('regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.myForm.get('provincia') && this.provinciaSelected.value !== this.myForm.get('provincia').value)) {
            this.myForm.get('provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.myForm.get('comune') && this.comuneSelected.value !== this.myForm.get('comune').value)) {
            this.myForm.get('comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        default:
          break;
      }
    }, 200);
  }

  setCap() {
    this.myForm.get('cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.myForm.get("cap").disable();
    } else {
      this.myForm.get("cap").enable();
    }
  }

  loadComuniItalia(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.myForm.get('comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.datiEG && this.datiEG.idComune && init) {
          this.myForm.get("comune").setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          // if (this.comuneSelected.value != null)
          //   this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  controlloCampoValido(valore) {
    return valore != null ? valore : "";
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.myForm.get('comune').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.myForm.get('comune').enable();
    } else {
      this.myForm.get('comune').disable();
    }
    this.resetCap();
  }

  displayFnNazioni(nazione: Nazioni): string {
    return nazione && nazione.descNazione ? nazione.descNazione : '';
  }

  displayFnRegioni(regione: Regioni): string {
    return regione && regione.descRegione ? regione.descRegione : '';

  }

  displayFnProvince(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.siglaProvincia : '';
  }

  displayFnComuni(com: AtlanteVO): string {
    return com && com.descComune ? com.descComune : '';
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.myForm.get('comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.datiEG && this.datiEG.idComune && this.myForm.get("sede-legale") && init) {
          this.myForm.get("comune").setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          //this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.myForm.get('regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneData.slice())
          );
        if (this.datiEG && this.datiEG.idRegione && init) {
          this.myForm.get("regione").setValue(this.regioneData.find(f => f.idRegione === this.datiEG.idRegione.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.datiEG.idRegione.toString()));
          if (this.regioneSelected.value != null)
            this.loadProvince(init);
        }
      }
      this.loadedRegioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioni = true;
    });
  }

  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.myForm.get('provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaData.slice())
          );
        if (this.datiEG && this.datiEG.idProvincia && init) {
          this.myForm.get("provincia").setValue(this.provinciaData.find(f => f.idProvincia === this.datiEG.idProvincia.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.datiEG.idProvincia.toString()));
          console.log(this.provinciaSelected.value);
          if (this.provinciaSelected.value != null)
            this.loadComuniItalia(init);
        }
      }
      this.loadedProvince = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvince = true;
    });
  }

  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.myForm.get('regione').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.myForm.get('regione').enable();
    } else {
      this.myForm.get('regione').disable();
    }
    this.resetProvincia();
  }

  resetCap() {
    this.myForm.get('cap').setValue(null);
    this.myForm.get('cap').disable();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.myForm.get('provincia').setValue(null);
    if (this.regioneSelected?.value) {
      this.myForm.get('provincia').enable();
    } else {
      this.myForm.get('provincia').disable();
    }
    this.resetComune();
  }

  private _filterRegioni(descrizione: string): Regioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.regioneData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
  }

  private _filterComuni(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.comuneData.filter(option => option.descComune.toLowerCase().includes(filterValue));
  }

  private _filterProvince(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.provinciaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));
  }

  private _filterNazioni(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  closeDialog() { this.dialogRef.close() }

}
