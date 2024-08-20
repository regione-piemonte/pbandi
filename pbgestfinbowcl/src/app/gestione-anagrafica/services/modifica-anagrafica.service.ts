/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AnagraficaBeneficiarioSec } from '../commons/dto/anagrafica-beneficiario';
import { BeneficiarioSoggCorrEG } from '../commons/dto/beneficiario-sogg-corr-EG';
import { BeneficiarioSoggCorrPF } from '../commons/dto/beneficiario-sogg-corr-PF';
import { Comuni } from '../commons/dto/comuni';
import { ElencoFormaGiuridica } from '../commons/dto/elencoFormaGiuridica';
import { ModificaEnteGiuridico } from '../commons/dto/modificaEnteGiuridico';
import { ModificaPersonaFisica } from '../commons/dto/modificaPersonaFisica';
import { Nazioni } from '../commons/dto/nazioni';
import { Province } from '../commons/dto/provincie';
import { Regioni } from '../commons/dto/regioni';
import { RuoloDTO } from '../commons/dto/ruoloDTO';
import { SoggettoGiuridicoIndipendenteDomanda } from '../commons/dto/soggettoGiuridicoIndipendenteDomanda';

@Injectable({
  providedIn: 'root'
})
export class ModificaAnagraficaService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService
  ) { }

  nazioni: Array<Nazioni>;
  private nazioniInfo = new BehaviorSubject<Array<Nazioni>>(null);
  nazioniInfo$: Observable<Array<Nazioni>> = this.nazioniInfo.asObservable();


  getElencoFormaGiuridica(): Observable<Array<ElencoFormaGiuridica>> {
    return this.http.get<Array<ElencoFormaGiuridica>>(`${this.configService.getApiURL()}/restfacade/anagrafica/getElencoFormaGiuridica`);
  }


  getNazioni() {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getNazioni';

    let params = new HttpParams()

    return this.http.get<Array<Nazioni>>(url, { params })
  }

  regioni: Array<Regioni>;
  private regioniInfo = new BehaviorSubject<Array<Regioni>>(null);
  regioniInfo$: Observable<Array<Regioni>> = this.regioniInfo.asObservable();

  getRegioni() {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getRegioni';

    let params = new HttpParams()

    return this.http.get<Array<Regioni>>(url, { params: params })
  }

  provincie: Array<Province>;
  private provincieInfo = new BehaviorSubject<Array<Province>>(null);
  provincieInfo$: Observable<Array<Province>> = this.provincieInfo.asObservable();

  getProvincie() {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getProvincie';
    let params = new HttpParams()
    return this.http.get<Array<Province>>(url, { params: params })
  }

  comuni: Array<Comuni>;
  private comuniInfo = new BehaviorSubject<Array<Comuni>>(null);
  comuniInfo$: Observable<Array<Comuni>> = this.comuniInfo.asObservable();

  getComuni(idProvincia: any) {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getComuni';

    let params = new HttpParams().set("idProvincia", idProvincia.toString())

    return this.http.get<Array<any>>(url, { params: params })
  }

  jsonModifiche: string;
  // updateAnagraficaPF(modificaAnagBeneFisico: ModificaPersonaFisica) {
  //   let url = this.configService.getApiURL() + '/restfacade/anagrafica/modificaAnagBeneFisico';
  //   let headers = new HttpHeaders().set('Content-Type', 'application/json');
  //   this.jsonModifiche = JSON.stringify(modificaAnagBeneFisico);
  //   return this.http.post<String>(url, this.jsonModifiche, { headers });
  // }

  //AGGIORNA PER ENTE GIURIDICO
  updateAnagraficaEG(sogg: BeneficiarioSoggCorrEG, idSoggetto:  string, idDomanda: string):Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/updateAnagBeneGiuridico';
    let params = new HttpParams().set("idSoggetto",  idSoggetto.toString()).set("idDomanda", idDomanda.toString()); 
    return this.http.post<boolean>(url,sogg,{ params: params });
  }
  updateAnagraficaPF(sogg: BeneficiarioSoggCorrPF, idSoggetto: any, idDomanda: any): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/updateAnagraficaPF';
    let params = new HttpParams().set("idSoggetto", idSoggetto).set("numeroDomanda", idDomanda);
    return this.http.post<boolean>(url, sogg, { params: params });
  }

  aggiornaBeneficiario(beneficiario: AnagraficaBeneficiarioSec, formControls: any) {
    //DATI ANAGRAFICI
    beneficiario.ragSoc = formControls.ragSociale;
    beneficiario.descFormaGiur = formControls.formaGiuridica;
    beneficiario.pIva = formControls.iva;
    beneficiario.descTipoSoggetto = formControls.descTipoSoggetto;
    beneficiario.cfSoggetto = formControls.codiceFiscale;
    //SEDE LEGALE:
    beneficiario.descIndirizzo = formControls.indirizzoSede;
    beneficiario.pIva = formControls.ivaSede;
    beneficiario.descComune = formControls.descComuneSede;
    beneficiario.idComune = Number(formControls.idComuneSede);
    beneficiario.descProvincia = formControls.descProvinciaSede;
    beneficiario.idProvincia = Number(formControls.idProvinciaSede);
    beneficiario.cap = formControls.capSede;
    beneficiario.idRegione = Number(formControls.idRegioneSede);
    beneficiario.descRegione = formControls.descRegioneSede;
    beneficiario.idNazione = Number(formControls.idNazioneSede);
    beneficiario.descNazione = formControls.descNazioneSede;
    beneficiario.codAteco = formControls.ateco;
    //DATI ISCRIZIONE:
    let parseDate = formControls.dataIscrizione.getFullYear() + "-" + ("0" + (formControls.dataIscrizione.getMonth() + 1)).slice(-2) + "-" + formControls.dataIscrizione.getDate();
    beneficiario.dtIiscrizione = parseDate.toString();
  }

  comparazione(beneficiario: AnagraficaBeneficiarioSec, beneficiario2: AnagraficaBeneficiarioSec) {

    if (beneficiario.cap != beneficiario2.cap) {
      return false
    };

    if (beneficiario.capSociale != beneficiario2.capSociale) {
      return false
    };

    if (beneficiario.cfSoggetto != beneficiario2.cfSoggetto) {
      return false
    };

    if (beneficiario.codAteco != beneficiario2.codAteco) {
      return false
    };

    if (beneficiario.codUniIpa != beneficiario2.codUniIpa) {
      return false
    };

    if (beneficiario.codiceFondo != beneficiario2.codiceFondo) {
      return false
    };

    if (beneficiario.codiceProgetto != beneficiario2.codiceProgetto) {
      return false
    };

    if (beneficiario.descTipoSoggetto != beneficiario2.descTipoSoggetto) {
      return false
    };

    if (beneficiario.descAteco != beneficiario2.descAteco) {
      return false
    };

    if (beneficiario.descComune != beneficiario2.descComune) {
      return false
    };

    if (beneficiario.descFormaGiur != beneficiario2.descFormaGiur) {
      return false
    };

    if (beneficiario.descIndirizzo != beneficiario2.descIndirizzo) {
      return false
    };

    if (beneficiario.descNazione != beneficiario2.descNazione) {
      return false
    };

    if (beneficiario.descProvincia != beneficiario2.descProvincia) {
      return false
    };

    if (beneficiario.descRegione != beneficiario2.descRegione) {
      return false
    };

    if (beneficiario.dtCostituzione != beneficiario2.dtCostituzione) {
      return false
    };

    if (beneficiario.dtFineVal != beneficiario2.dtFineVal) {
      return false
    };

    if (beneficiario.dtIiscrizione != beneficiario2.dtIiscrizione) {
      return false
    };

    if (beneficiario.dtInizioAttEsito != beneficiario2.dtInizioAttEsito) {
      return false
    };

    if (beneficiario.dtInizioVal != beneficiario2.dtInizioVal) {
      return false
    };

    if (beneficiario.dtInizioValSede != beneficiario2.dtInizioValSede) {
      return false
    };

    if (beneficiario.dtUltimoEseChiuso != beneficiario2.dtUltimoEseChiuso) {
      return false
    };

    if (beneficiario.dtValEsito != beneficiario2.dtValEsito) {
      return false
    };

    if (beneficiario.flagIscrizione != beneficiario2.flagIscrizione) {
      return false
    };

    if (beneficiario.flagPubblicoPrivato != beneficiario2.flagPubblicoPrivato) {
      return false
    };

    if (beneficiario.flagRatingLeg != beneficiario2.flagRatingLeg) {
      return false
    };

    if (beneficiario.idAttAteco != beneficiario2.idAttAteco) {
      return false
    };

    if (beneficiario.idAttIuc != beneficiario2.idAttIuc) {
      return false
    };

    if (beneficiario.idClassEnte != beneficiario2.idClassEnte) {
      return false
    };

    if (beneficiario.idComune != beneficiario2.idComune) {
      return false
    };

    if (beneficiario.idDimensioneImpresa != beneficiario2.idDimensioneImpresa) {
      return false
    };

    if (beneficiario.idEnteGiuridico != beneficiario2.idEnteGiuridico) {
      return false
    };

    if (beneficiario.idFormaGiuridica != beneficiario2.idFormaGiuridica) {
      return false
    };

    if (beneficiario.idIndirizzo != beneficiario2.idIndirizzo) {
      return false
    };

    if (beneficiario.idIscrizione != beneficiario2.idIscrizione) {
      return false
    };

    if (beneficiario.idNazione != beneficiario2.idNazione) {
      return false
    };

    if (beneficiario.idProvincia != beneficiario2.idProvincia) {
      return false
    };

    if (beneficiario.idProvinciaIscrizione != beneficiario2.idProvinciaIscrizione) {
      return false
    };

    if (beneficiario.idRegione != beneficiario2.idRegione) {
      return false
    };

    if (beneficiario.idSede != beneficiario2.idSede) {
      return false
    };

    if (beneficiario.idSoggetto != beneficiario2.idSoggetto) {
      return false
    };

    if (beneficiario.idStatoAtt != beneficiario2.idStatoAtt) {
      return false
    };

    if (beneficiario.idTipoIscrizione != beneficiario2.idTipoIscrizione) {
      return false
    };

    if (beneficiario.idTipoSogg != beneficiario2.idTipoSogg) {
      return false
    };

    if (beneficiario.idUtenteAgg != beneficiario2.idUtenteAgg) {
      return false
    };

    if (beneficiario.idUtenteIns != beneficiario2.idUtenteIns) {
      return false
    };

    if (beneficiario.numIscrizione != beneficiario2.numIscrizione) {
      return false
    };

    if (beneficiario.numeroDomanda != beneficiario2.numeroDomanda) {
      return false
    };

    if (beneficiario.pIva != beneficiario2.pIva) {
      return false
    };

    if (beneficiario.periodoScadEse != beneficiario2.periodoScadEse) {
      return false
    };

    if (beneficiario.ragSoc != beneficiario2.ragSoc) {
      return false
    };

    if (beneficiario.statoDomanda != beneficiario2.statoDomanda) {
      return false
    };

    if (beneficiario.statoProgetto != beneficiario2.statoProgetto) {
      return false
    };


    return true;
  }

  getElencoRuolo() {
    let url = this.configService.getApiURL() + '/restfacade/anagrafica/getElencoRuoloIndipendente';
    return this.http.get<Array<RuoloDTO>>(url)
  }


}
