/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { DatiDomandaEG } from '../../commons/dto/model-dati-domanda-eg';
import { ModificaDatiDomandaEG } from '../../commons/dto/modifica-domandaEG';
import { DatiDomandaService } from '../../services/dati-domanda-service';

@Component({
  selector: 'app-modifica-dati-domanda-eg',
  templateUrl: './modifica-dati-domanda-eg.component.html',
  styleUrls: ['./modifica-dati-domanda-eg.component.scss']
})
export class ModificaDatiDomandaEGComponent implements OnInit {

  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  idVersione: any;
  idSoggetto: any;
  idDomanda: any;
  idEnteGiuridico: any;
  datiEG: DatiDomandaEG;
  modifiche: ModificaDatiDomandaEG;
  handleExceptionService: any;

  public subscribers: any = {};

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private router: Router,) { }

  ngOnInit() {

    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');

    this.myForm = this.fb.group({
      //STATO DOMANDA
      numeroDomanda: new FormControl({ value: '', disabled: false }),
      statoDomanda: new FormControl({ value: '', disabled: false }),
      dataPresentazioneDomanda: new FormControl({ value: '', disabled: false }),
      //RAPPRESENTANTE LEGALE
      cognome: new FormControl({ value: '', disabled: false }),
      nome: new FormControl({ value: '', disabled: false }),
      tipoSoggetto: new FormControl({ value: '', disabled: false }),
      codiceFiscale: new FormControl({ value: '', disabled: false }),
      dataDiNascita: new FormControl({ value: '', disabled: false }),
      comuneDiNascita: new FormControl({ value: '', disabled: false }),
      provinciaDiNascita: new FormControl({ value: '', disabled: false }),
      regioneDiNascita: new FormControl({ value: '', disabled: false }),
      nazioneDiNascita: new FormControl({ value: '', disabled: false }),
      //DATI DI RESIDENZA
      indirizzoRes: new FormControl({ value: '', disabled: false }),
      comuneRes: new FormControl({ value: '', disabled: false }),
      capRes: new FormControl({ value: '', disabled: false }),
      provinciaRes: new FormControl({ value: '', disabled: false }),
      regioneRes: new FormControl({ value: '', disabled: false }),
      nazioneRes: new FormControl({ value: '', disabled: false }),
      //SEDE AMMINISTRATIVA
      indirizzoSede: new FormControl({ value: '', disabled: false }),
      ivaSede: new FormControl({ value: '', disabled: false }),
      comuneSede: new FormControl({ value: '', disabled: false }),
      capSede: new FormControl({ value: '', disabled: false }),
      provinciaSede: new FormControl({ value: '', disabled: false }),
      regioneSede: new FormControl({ value: '', disabled: false }),
      nazioneSede: new FormControl({ value: '', disabled: false }),
      //RECAPITI
      telefono: new FormControl({ value: '', disabled: false }),
      fax: new FormControl({ value: '', disabled: false }),
      email: new FormControl({ value: '', disabled: false }),
      //CONTO CORRENTE
      numeroConto: new FormControl({ value: '', disabled: false }),
      iban: new FormControl({ value: '', disabled: false }),
      //BANCA DI APPOGGIO
      banca: new FormControl({ value: '', disabled: false }),
      abi: new FormControl({ value: '', disabled: false }),
      cab: new FormControl({ value: '', disabled: false }),
      // //CONSULENTI
      // ragioneSoc: new FormControl({ value: '', disabled: false }),
      // ruoloSoggettoCorrelato: new FormControl({ value: '', disabled: false }),
      // tipoSoggetto: new FormControl({ value: '', disabled: false }),
      // codiceFiscale: new FormControl({ value: '', disabled: false }),
      // //SEDE LEGALE
      // indirizzo: new FormControl({ value: '', disabled: false }),
      // partitaIva: new FormControl({ value: '', disabled: false }),
      // comune: new FormControl({ value: '', disabled: false }),
      // provincia: new FormControl({ value: '', disabled: false }),
      // regione: new FormControl({ value: '', disabled: false }),
      // capSedeLegale: new FormControl({ value: '', disabled: false }),
      // nazioneSedeLegale: new FormControl({ value: '', disabled: false }),
      // codiceAteco: new FormControl({ value: '', disabled: false }),
      // descrizioneAteco: new FormControl({ value: '', disabled: false }),
    });

    this.datiDomandaService.getDatiDomandaEG(this.idSoggetto, this.idDomanda);

    this.subscribers.datiDomandaEGInfo$ = this.datiDomandaService.datiDomandaEGInfo$.subscribe(data => {
      if (data) {
        this.datiEG = data;
        this.myForm.setValue({
          //STATO DOMANDA
          numeroDomanda: this.datiEG[0].numeroDomanda,
          statoDomanda: this.datiEG[0].statoDomanda,
          dataPresentazioneDomanda: new Date(Date.parse(this.datiEG[0].dataPresentazioneDomanda)),
          //RAPPRESENTANTE LEGALE
          cognome: this.datiEG[0].cognome,
          nome: this.datiEG[0].nome,
          tipoSoggetto: this.datiEG[0].tipoSoggetto,
          codiceFiscale: this.datiEG[0].codiceFiscale,
          dataDiNascita: new Date(Date.parse(this.datiEG[0].dataDiNascita)),
          comuneDiNascita: this.datiEG[0].comuneDiNascita,
          provinciaDiNascita: this.datiEG[0].provinciaDiNascita,
          regioneDiNascita: this.datiEG[0].regioneDiNascita,
          nazioneDiNascita: this.datiEG[0].nazioneDiNascita,
          //DATI DI RESIDENZA
          indirizzoRes: this.datiEG[0].indirizzoRes,
          comuneRes: this.datiEG[0].comuneRes,
          capRes: this.datiEG[0].capRes,
          provinciaRes: this.datiEG[0].provinciaRes,
          regioneRes: this.datiEG[0].regioneRes,
          nazioneRes: this.datiEG[0].nazioneRes,
          //SEDE AMMINISTRATIVA
          indirizzoSede: this.datiEG[0].indirizzoSede,
          ivaSede: this.datiEG[0].ivaSede,
          comuneSede: this.datiEG[0].comuneSede,
          capSede: this.datiEG[0].capSede,
          provinciaSede: this.datiEG[0].provinciaSede,
          regioneSede: this.datiEG[0].regioneSede,
          nazioneSede: this.datiEG[0].nazioneSede,
          //RECAPITI
          telefono: this.datiEG[0].telefono,
          fax: this.datiEG[0].fax,
          email: this.datiEG[0].email,
          //CONTO CORRENTE
          numeroConto: this.datiEG[0].numeroConto,
          iban: this.datiEG[0].iban,
          //BANCA DI APPOGGIO
          banca: this.datiEG[0].banca,
          abi: this.datiEG[0].abi,
          cab: this.datiEG[0].cab,
          // //CONSULENTI
          // ragioneSoc: [this.datiEG[0].denominazione],
          // ruoloSoggettoCorrelato: [this.datiEG[0].ruoloSoggettoCorrelato],
          // tipoSoggetto: [this.datiEG[0].tipoSoggetto],
          // codiceFiscale: [this.datiEG[0].codiceFiscale],
          // //SEDE LEGALE
          // indirizzo: [this.datiEG[0].indirizzo],
          // partitaIva: [this.datiEG[0].partitaIva],
          // comune: [this.datiEG[0].comune],
          // provincia: [this.datiEG[0].provincia],
          // regione: [this.datiEG[0].regione],
          // capSedeLegale: [this.datiEG[0].capSedeLegale],
          // nazioneSedeLegale: [this.datiEG[0].nazioneSedeLegale],
          // codiceAteco: [this.datiEG[0].codiceAteco],
          // descrizioneAteco: [this.dati[0].descrizioneAteco]
        });
      }
    });
  }

  //FUNZIONE CHE PERMETTE DI MODIFICARE I CAMPI DELLA DOMANDA/PROGETTO SELEZIONATA DALL'ELENCO
  configuraBandoLinea() {
    this.router.navigate(["/drawer/" + this.idOp + "/modificaDatiDomandaEG"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.idDomanda } });
    //ESEMPIO DI APPLICAZIONE:
    //this.controlloCampoValido(this.dati[0].banca) ? this.dati[0].banca : 'N.D.',
  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }
  //FUNZIONE CHE PERMETTE DI SALVARE LE MODIFICHE APPORTATE E FARE L'UPDATE SUL DB
  salvaModifiche() {
    // this.modifiche = new ModificaDatiDomandaEG();
    // //SERVIZIO DI POST:
    // this.datiDomandaService.updateDomandaPF(this.modifiche).subscribe(data => {
    //   if (data) {
    //     return true;
    //   }
    // }, err => {
    //   this.handleExceptionService.handleBlockingError(err);
    // });
    this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaEG"], { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.idDomanda, idEnteGiuridico: this.idEnteGiuridico } });
  }

}

