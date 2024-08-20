/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { DatiDomanda } from '../../commons/dto/dati-domanda';
import { ModificaDatiDomandaPF } from '../../commons/dto/modifica-domandaPF';
import { DatiDomandaService } from '../../services/dati-domanda-service';

@Component({
  selector: 'app-modifica-dati-domanda-pf',
  templateUrl: './modifica-dati-domanda-pf.component.html',
  styleUrls: ['./modifica-dati-domanda-pf.component.scss']
})
export class ModificaDatiDomandaPFComponent implements OnInit {
  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  idVersione: any;
  idSoggetto: any;
  idDomanda: any;
  modifiche: ModificaDatiDomandaPF;
  dati: DatiDomanda;
  handleExceptionService: any;
  verificaTipoSoggetto: any;
  numeroDomanda: any;
  statoDomanda: any;
  dataPresentazioneDomanda: any;


  public subscribers: any = {};
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private router: Router,) { }

  ngOnInit() {
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');

    this.myForm = this.fb.group({
      //DOCUMENTO IDENTITA'
      documentoIdentita: new FormControl('', [Validators.required]),
      numeroDocumento: new FormControl('', [Validators.required]),
      dataRilascio: new FormControl({ value: '', disabled: false }),
      enteRilascio: new FormControl('', [Validators.required]),
      //if se non obbligatorio metto N.D.
      scadenzaDocumento: new FormControl({ value: '', disabled: false }),
      //RECAPITI
      telefono: new FormControl('', [Validators.required]),
      //if se non obbligatorio metto N.D.
      fax: new FormControl({ value: '', disabled: false }),
      email: new FormControl('', [Validators.required]),
      pec: new FormControl('', [Validators.required]),
      //CONTO CORRENTE
      banca: new FormControl({ value: '', disabled: false }),
      iban: new FormControl('', [Validators.required]),
      // //CONSULENTI
      // denominazione: new FormControl({ value: '', disabled: true }),
      // ruoloSoggettoCorrelato: new FormControl({ value: '', disabled: true }),
      // tipoSoggetto: new FormControl({ value: '', disabled: true }),
      // codiceFiscale: new FormControl({ value: '', disabled: true }),
      // //SEDE LEGALE
      // indirizzo: new FormControl({ value: '', disabled: true }),
      // partitaIva: new FormControl({ value: '', disabled: true }),
      // comune: new FormControl({ value: '', disabled: true }),
      // provincia: new FormControl({ value: '', disabled: true }),
      // regione: new FormControl({ value: '', disabled: true }),
      // capSedeLegale: new FormControl({ value: '', disabled: true }),
      // nazioneSedeLegale: new FormControl({ value: '', disabled: true }),
      // codiceAteco: new FormControl({ value: '', disabled: true }),
      // descrizioneAteco: new FormControl({ value: '', disabled: true }),
    });

    this.datiDomandaService.getDatiDomandaPF(this.idSoggetto, this.idDomanda).subscribe(data => {
      if (data) {
        this.dati = data;
        this.numeroDomanda  = this.dati[0].numeroDomanda,
        this.statoDomanda = this.dati[0].statoDomanda,
        this.dataPresentazioneDomanda = this.dati[0].dataPresentazioneDomanda,
        this.myForm.setValue({
          //DOCUMENTO IDENTITA'
          documentoIdentita: this.dati[0].documentoIdentita,
          numeroDocumento: this.dati[0].numeroDocumento,
          dataRilascio: new Date(Date.parse(this.dati[0].dataRilascio)),
          enteRilascio: this.dati[0].enteRilascio,
          scadenzaDocumento: this.dati[0].scadenzaDocumento,
          //RECAPITI
          telefono: this.dati[0].telefono,
          fax: this.dati[0].fax,
          email: this.dati[0].email,
          pec: '',
          //CONTO CORRENTE
          banca: this.dati[0].banca,
          iban: this.dati[0].iban,
          // //CONSULENTI
          // denominazione: [this.dati[0].denominazione],
          // ruoloSoggettoCorrelato: [this.dati[0].ruoloSoggettoCorrelato],
          // tipoSoggetto: [this.dati[0].tipoSoggetto],
          // codiceFiscale: [this.dati[0].codiceFiscale],
          // //SEDE LEGALE
          // indirizzo: [this.dati[0].indirizzo],
          // partitaIva: [this.dati[0].partitaIva],
          // comune: [this.dati[0].comune],
          // provincia: [this.dati[0].provincia],
          // regione: [this.dati[0].regione],
          // capSedeLegale: [this.dati[0].capSedeLegale],
          // nazioneSedeLegale: [this.dati[0].nazioneSedeLegale],
          // codiceAteco: [this.dati[0].codiceAteco],
          // descrizioneAteco: [this.dati[0].descrizioneAteco]
        });
      }
    });
  }

  //FUNZIONE PER LA VALIDAZIONE
  public hasError = (controlName: string, errorName: string) => {
    return this.myForm.controls[controlName].hasError(errorName);
  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }

  //FUNZIONE CHE PERMETTE DI SALVARE LE MODIFICHE APPORTATE E FARE L'UPDATE SUL DB
  salvaModifiche() {
    this.modifiche = new ModificaDatiDomandaPF();
    //SERVIZIO DI POST:
    this.datiDomandaService.updateDomandaPF(this.modifiche).subscribe(data => {
      if (data) {
        return true;
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaPF"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.idDomanda } });
  }

  annullaModifiche(){
    this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"], { queryParams: { idSoggetto: this.idSoggetto, tipoSoggetto: "Persona Fisica" } });
  }

}

