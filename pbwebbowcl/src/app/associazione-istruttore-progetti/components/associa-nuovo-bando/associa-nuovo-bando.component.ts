/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BandoLineaDaAssociareAIstruttoreVO } from '../../commons/dto/bando-linea-da-associare-istruttore-dto';
import { AssociazioneIstruttoreProgettiService } from '../../services/associazione-istruttore-progetti.service';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-associa-nuovo-bando',
  templateUrl: './associa-nuovo-bando.component.html',
  styleUrls: ['./associa-nuovo-bando.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociaNuovoBandoComponent implements OnInit {

  cognomeIstruttore: string;
  nomeIstruttore: string;
  codiceFiscale: string;
  form: FormGroup;

  bandilinea: Array<BandoLineaDaAssociareAIstruttoreVO>;
  bandoSelected: BandoLineaDaAssociareAIstruttoreVO;

  filteredOptions$: Observable<Array<BandoLineaDaAssociareAIstruttoreVO>>;

  params: URLSearchParams;

  idOperazione: number;

  LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY = 'ricercaIstruttore_anagraficaIstruttoreSelected';
  anagraficaIstruttoreSelected: string;

  messageError: string;
  isMessageErrorVisible: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  user: UserInfoSec;

  //LOADED
  loadedGetBandoLineaDaAssociare: boolean;
  loadedassociaIstruttoreBandolinea: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private associazioneIstruttoreProgettiService: AssociazioneIstruttoreProgettiService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private fBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.form = this.fBuilder.group({
      bandoLinea: ['', [Validators.required]]
    });
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.getTipoIstruttoreFromLocalStorage();
        this.loadBandiDaAssociare();
      }
    });

    this.cognomeIstruttore = this.params.get('cognome');
    this.nomeIstruttore = this.params.get('nome');
    this.codiceFiscale = this.params.get('codFisc');

  }

  getTipoIstruttoreFromLocalStorage() {

    this.anagraficaIstruttoreSelected = localStorage.getItem(this.LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY);

  }

  loadBandiDaAssociare() {
    this.loadedGetBandoLineaDaAssociare = true;
    this.subscribers.getBandoLineaDaAssociare = this.associazioneIstruttoreProgettiService.getBandoLineaDaAssociare(this.user, this.user.idSoggetto.toString(),
      this.params.get('idSoggettoIstruttore'),
      this.anagraficaIstruttoreSelected === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI ? this.anagraficaIstruttoreSelected : this.user.codiceRuolo).subscribe(data => {
        this.bandilinea = data;
        if (this.bandilinea) {
          this.bandilinea.sort((a, b) => a?.nomeBandolinea.localeCompare(b?.nomeBandolinea));
          this.filteredOptions$ = this.form.get("bandoLinea").valueChanges.pipe(
            startWith(""),
            map(value => {
              const desc = typeof value === 'string' ? value : value?.descrizione;
              return desc ? this._filter(desc as string) : this.bandilinea.slice();
            })
          );
        }
        this.loadedGetBandoLineaDaAssociare = false;
      }, err => {
        this.loadedGetBandoLineaDaAssociare = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cerca dei bandi");
      });
  }

  click(event: any) {
    this.bandoSelected = event.option.value;
  }

  onBlurAutoComplete() {
    setTimeout(() => {
      this.sharedService.onBlurAutoComplete(this.form, ["bandoLinea"]);
      this.bandoSelected = this.form.get("bandoLinea")?.value;
    }, 300);
  }

  displayFn(element: BandoLineaDaAssociareAIstruttoreVO): string {
    return element && element.nomeBandolinea ? element.nomeBandolinea : '';
  }

  _filter(value: string): Array<BandoLineaDaAssociareAIstruttoreVO> {
    const filterValue = value.toLowerCase();
    return this.bandilinea.filter(option => option.nomeBandolinea.toLowerCase().includes(filterValue));
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  tornaAllaGestioneAssociazioni() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/gestioneAssociazione?idSoggettoIstruttore=` + this.params.get('idSoggettoIstruttore') + `&cognome=` + this.params.get('cognome') + `&nome=` + this.params.get('nome') + `&codFisc=` + this.params.get('codFisc') + `&idBando=` + this.params.get('idBando'));
  }

  associaAIstruttore() {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (!this.form.get("bandoLinea")?.value) {
      this.showMessageError("Si prega di popolare i campi obbligatori")
    } else {
      this.loadedassociaIstruttoreBandolinea = true;
      this.subscribers.associaIstruttoreBandolinea = this.associazioneIstruttoreProgettiService.associaIstruttoreBandolinea(this.user,
        this.form.get("bandoLinea").value?.progBandoLinaIntervento.toString(), this.params.get('idSoggettoIstruttore'),
        this.anagraficaIstruttoreSelected === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI ? this.anagraficaIstruttoreSelected : this.user.codiceRuolo).subscribe(data => {
          this.loadedassociaIstruttoreBandolinea = false;
          this.showMessageSuccess("Associazione effettuata con successo.");
          this.loadBandiDaAssociare();
          this.bandoSelected = null;
          this.form.get("bandoLinea").setValue(null);
          this.form.get("bandoLinea").setErrors(null);
          this.form.markAsUntouched();
        }, err => {
          this.loadedassociaIstruttoreBandolinea = false;
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di asociazione dei bando linea all'istruttore");
        });
    }
  }

  isLoading() {
    if (this.loadedGetBandoLineaDaAssociare || this.loadedassociaIstruttoreBandolinea) {
      return true;
    }
    return false;
  }
}
