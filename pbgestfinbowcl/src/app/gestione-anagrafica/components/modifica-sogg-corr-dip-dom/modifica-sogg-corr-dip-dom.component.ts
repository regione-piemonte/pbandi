/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { TmplAstRecursiveVisitor } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { map, startWith } from 'rxjs/operators';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { BeneficiarioSoggCorrEG } from '../../commons/dto/beneficiario-sogg-corr-EG';
import { BeneficiarioSoggCorrPF } from '../../commons/dto/beneficiario-sogg-corr-PF';
import { Comuni } from '../../commons/dto/comuni';
import { Nazioni } from '../../commons/dto/nazioni';
import { Province } from '../../commons/dto/provincie';
import { Regioni } from '../../commons/dto/regioni';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';

@Component({
  selector: 'app-modifica-sogg-corr-dip-dom',
  templateUrl: './modifica-sogg-corr-dip-dom.component.html',
  styleUrls: ['./modifica-sogg-corr-dip-dom.component.scss']
})
export class ModificaSoggCorrDipDomComponent implements OnInit {
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  isEnteGiuridico: boolean;
  tipoSoggetto: any;
  idVersione: any;
  numeroDomanda: any;
  idSoggetto: any;
  idEnteGiuridico: any;
  idSoggettoCorr: any;
  tipoSoggettoCorr: any;
  idDomanda: any;
  userloaded: boolean;
  subscribers: any = {}
  user: UserInfoSec;
  idUtente: any;
  loadedEG: boolean = true;
  loadedPF: boolean = true;
  public myForm: FormGroup;
  nazioni: Array<Nazioni>;
  regioni: Array<Regioni>;
  provincie: Array<Province>;
  benefSoggCorrEG: BeneficiarioSoggCorrEG = new BeneficiarioSoggCorrEG();
  descComuni: Array<string> = [];
  comuni: Array<Comuni>;
  filteredOptions: Observable<string[]>;
  eleconRuoli: Array<AttivitaDTO>;

  //// dati EG //// 
  cap: any; capSociale: any; cfSoggetto: any; codAteco: any; codUniIpa: any; codiceFondo: any; codiceProgetto: any; descTipoSoggetto: any;
  descAteco: any; descComune: any; descFormaGiur: any; descIndirizzo: any; descNazione: any; descProvincia: any; descRegione: any; descTipoAnagrafica: any;
  descStatoAttivita: any; dtCostituzione: any; dtFineVal: any; dtIiscrizione: any;
  dtInizioAttEsito: any; dtUltimoEseChiuso: any; dtValEsito: any;
  flagIscrizione: any; flagPubblicoPrivato: any; flagRatingLeg: any; idTipoSogg: any;
  numIscrizione: any; pIva: any; periodoScadEse: any; ragSoc: any; statoDomanda: any; statoProgetto: any;
  ruoloEG: any;
  pec: string;
  descProvinciaIscriz: any;
  descSezioneSpeciale: any;
  soggCorr: any;
  idNazione: any; idRegione: any; idComune: any; idProvincia: any; 

  //// PERSONA FISICA//// 
  benefSoggCorrPF: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  cognome: any; nome: any; codiceFiscale: any; dataDiNascita: any; idNazioneDiNascita: any; comuneDiNascita: any;
  provinciaDiNascita: any; regioneDiNascita: any; nazioneDiNascita: any; indirizzoPF: any; comunePF: any;
  provinciaPF: any; capPF: any; regionePF: any; nazionePF: any; ruoloPF: any;
  messageError: string; idComuneNascitaPF: any;
  isMessageErrorVisible: boolean;
  listaRuoli: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  idRuoloPF: any;
  ruoloPFControl = new FormControl('', Validators.required);
  comuniNascPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  suggesttionnull: AttivitaDTO = new AttivitaDTO;
  listaProvinceNascitaPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  regioniNascitaPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  nazioniNascitaPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  comuniResPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  provincieResPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  regioniResPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  nazioniResPF: Array<AttivitaDTO> = new Array<AttivitaDTO>();
  savePF: any;
  isNotSavePF: boolean;
  loadedSettingPF: boolean = true;
  idIndirizzoPF: any;
  idComuneDiNascita: any; idProvinciaDiNascita: any; idRegioneDiNascita: any;
  idComunePF: any; idProvinciaPF: any; idRegionePF: any; idNazionePF: any;
  ruoloDTO: AttivitaDTO;
  loadedNazioni: boolean;
  loadedRegioni: boolean;
  loadedProvincie: boolean;



  constructor(
    private datiDomandaService: DatiDomandaService,
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder,
    private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private modificaAnagraficaService: ModificaAnagraficaService
  ) { }

  ngOnInit(): void {
    this.tipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get('numeroDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.idSoggettoCorr = this.route.snapshot.queryParamMap.get('idSoggettoCorr');;
    this.tipoSoggettoCorr = this.route.snapshot.queryParamMap.get('tipoSoggettoCorr');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');

    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
      }
    });
    this.getRuoli();
    
    this.getProvincieMeth();
    this.getRegioniMeth();
    this.getNazioniMeth();

    if (this.tipoSoggettoCorr == "Persona Giuridica") {
      this.isEnteGiuridico = true;
      //this.getSoggCorrEG();
    } else {
      this.getBeneficiarioSoggCorrPF();
    }

    // this.filteredOptions = this.myForm.controls.descComuneSede.valueChanges.pipe(
    //   startWith(''),
    //   map(value => this._filter(value)),
    // );
  }

  getRuoli() {
    this.subscribers.getRuoli = this.datiDomandaService.getRuoli().subscribe(data => {
      if (data) {
        this.listaRuoli = data;
        console.log(this.listaRuoli);
      }
    })

  }

  // getSoggCorrEG() {
  //   this.subscribers.getAnag = this.datiDomandaService.getBenefSoggCorrEG(this.idSoggettoCorr, this.idDomanda).subscribe(data => {
  //     if (data) {
  //       this.benefSoggCorrEG = data;
  //       this.benefSoggCorrEG = data;
  //       this.loadedEG = true;
  //       this.ragSoc = this.benefSoggCorrEG.ragSoc;
  //       this.descFormaGiur = this.benefSoggCorrEG.descFormaGiur;
  //       this.flagPubblicoPrivato = this.benefSoggCorrEG.flagPubblicoPrivato;
  //       this.codUniIpa = this.benefSoggCorrEG.codUniIpa;
  //       this.ruoloEG = this.benefSoggCorrEG.ruolo;
  //       this.cfSoggetto = this.benefSoggCorrEG.cfSoggetto;
  //       this.dtCostituzione = this.benefSoggCorrEG.dtCostituzione;
  //       this.pec = "ND";
  //       this.descIndirizzo = this.benefSoggCorrEG.descIndirizzo;
  //       this.pIva = this.benefSoggCorrEG.pIva;
  //       this.descComune = this.benefSoggCorrEG.descComune;
  //       this.descProvincia = this.benefSoggCorrEG.descProvincia;
  //       this.descRegione = this.benefSoggCorrEG.descRegione;
  //       this.descNazione = this.benefSoggCorrEG.descNazione;
  //       this.codAteco = this.benefSoggCorrEG.codAteco;
  //       this.descAteco = this.benefSoggCorrEG.descAteco;
  //       this.flagRatingLeg = this.benefSoggCorrEG.flagRatingLeg;
  //       this.descStatoAttivita = this.benefSoggCorrEG.descStatoAttivita;
  //       this.dtInizioAttEsito = this.benefSoggCorrEG.dtInizioAttEsito;
  //       this.periodoScadEse = this.benefSoggCorrEG.periodoScadEse;
  //       this.dtUltimoEseChiuso = this.benefSoggCorrEG.dtUltimoEseChiuso;
  //       this.numIscrizione = this.benefSoggCorrEG.numIscrizione;
  //       this.dtIiscrizione = this.benefSoggCorrEG.dtIscrizione;
  //       this.descProvinciaIscriz = this.benefSoggCorrEG.descProvinciaIscriz
  //       this.descSezioneSpeciale = this.benefSoggCorrEG.descSezioneSpeciale

  //     } else {
  //       this.loadedEG = true;
  //     }

  //   })

  // }
  getBeneficiarioSoggCorrPF() {
    this.isEnteGiuridico = false;
    this.loadedPF = false;

    // this.subscribers.benefPF = this.datiDomandaService.getBenefSoggCorrPF(this.idSoggettoCorr, this.idDomanda, ).subscribe(data => {
    //   if (data) {
    //     this.benefSoggCorrPF = data;
    //     this.loadedPF = true;
    //     this.cognome = this.benefSoggCorrPF.cognome;
    //     this.nome = this.benefSoggCorrPF.nome;
    //     this.codiceFiscale = this.benefSoggCorrPF.codiceFiscale;
    //     this.dataDiNascita = this.benefSoggCorrPF.dataDiNascita;
    //     this.comuneDiNascita = this.benefSoggCorrPF.comuneDiNascita;
    //     this.provinciaDiNascita = this.benefSoggCorrPF.provinciaDiNascita;
    //     this.regioneDiNascita = this.benefSoggCorrPF.regioneDiNascita;
    //     this.nazioneDiNascita = this.benefSoggCorrPF.nazioneDiNascita;
    //     this.indirizzoPF = this.benefSoggCorrPF.indirizzoPF;
    //     this.provinciaPF = this.benefSoggCorrPF.provinciaPF;
    //     this.regionePF = this.benefSoggCorrPF.regionePF;
    //     this.capPF = this.benefSoggCorrPF.capPF;
    //     this.nazionePF = this.benefSoggCorrPF.nazionePF;
    //     this.comunePF = this.benefSoggCorrPF.comunePF;
    //     this.ruoloPF = this.benefSoggCorrPF.descTipoSoggCorr;
    //     this.idIndirizzoPF = this.benefSoggCorrPF.idIndirizzo;
    //     this.idComuneDiNascita = this.benefSoggCorrPF.idComuneDiNascita;
    //     this.idProvinciaDiNascita = this.benefSoggCorrPF.idProvinciaDiNascita;
    //     this.idRegioneDiNascita = this.benefSoggCorrPF.idRegioneDiNascita;
    //     this.idNazioneDiNascita = this.benefSoggCorrPF.idNazioneDiNascita;
    //     this.idComunePF = this.benefSoggCorrPF.idComunePF;
    //     this.idProvinciaPF = this.benefSoggCorrPF.idProvinciaPF;
    //     this.idRegionePF = this.benefSoggCorrPF.idRegionePF;
    //     this.idNazionePF = this.benefSoggCorrPF.idNazionePF;
    //     console.log(this.benefSoggCorrPF)
    //   } else {
    //     this.loadedPF = true;
    //   }
    // })
  }


  suggestPF(idCampo: number) {

    switch (idCampo) {
      case 1:

        this.comuniNascPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.comuniNascPF.push(this.suggesttionnull);
        this.subscribers.comuniNascPF = this.datiDomandaService.getListaSuggest(idCampo, this.comuneDiNascita).subscribe(data => {
          if (data.length > 0) {
            this.comuniNascPF = data;
          } else {
            this.comuniNascPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.comuniNascPF.push(this.suggesttionnull);
          }
        });
        break;

      case 2:

        this.listaProvinceNascitaPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.listaProvinceNascitaPF.push(this.suggesttionnull);
        this.subscribers.listaProvinceNascitaPF = this.datiDomandaService.getListaSuggest(idCampo, this.provinciaDiNascita).subscribe(data => {
          if (data.length > 0) {
            this.listaProvinceNascitaPF = data;
          } else {
            this.listaProvinceNascitaPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.listaProvinceNascitaPF.push(this.suggesttionnull);
          }
        });

        break;

      case 3:
        this.regioniNascitaPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.regioniNascitaPF.push(this.suggesttionnull);
        this.subscribers.regioniNascitaPF = this.datiDomandaService.getListaSuggest(idCampo, this.regioneDiNascita).subscribe(data => {
          if (data.length > 0) {
            this.regioniNascitaPF = data;
          } else {
            this.regioniNascitaPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.regioniNascitaPF.push(this.suggesttionnull);
          }
        });

        break;

      case 4:
        this.nazioniNascitaPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.nazioniNascitaPF.push(this.suggesttionnull);
        this.subscribers.nazioniNascitaPF = this.datiDomandaService.getListaSuggest(idCampo, this.nazioneDiNascita).subscribe(data => {
          if (data.length > 0) {
            this.nazioniNascitaPF = data;
          } else {
            this.nazioniNascitaPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.nazioniNascitaPF.push(this.suggesttionnull);
          }
        });
        break;

      case 5:
        this.comuniResPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.comuniResPF.push(this.suggesttionnull);
        this.subscribers.comuneResPF = this.datiDomandaService.getListaSuggest(idCampo, this.comunePF).subscribe(data => {
          if (data.length > 0) {
            this.comuniResPF = data;
          } else {
            this.comuniResPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.comuniResPF.push(this.suggesttionnull);
          }
        });

        break;

      case 6:

        this.provincieResPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.provincieResPF.push(this.suggesttionnull);
        this.subscribers.provinciaResPF = this.datiDomandaService.getListaSuggest(idCampo, this.provinciaPF).subscribe(data => {
          if (data.length > 0) {
            this.provincieResPF = data;
          } else {
            this.provincieResPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.provincieResPF.push(this.suggesttionnull);
          }
        });
        break;

      case 7:

        this.regioniResPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.regioniResPF.push(this.suggesttionnull);
        this.subscribers.regioniResPF = this.datiDomandaService.getListaSuggest(idCampo, this.regionePF).subscribe(data => {
          if (data.length > 0) {
            this.regioniResPF = data;
          } else {
            this.regioniResPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.regioniResPF.push(this.suggesttionnull);
          }
        });
        break;

      case 8:
        this.nazioniResPF = new Array<AttivitaDTO>();
        this.suggesttionnull.descAttivita = 'caricamento...';
        this.nazioniResPF.push(this.suggesttionnull);
        this.subscribers.nazioniResPF = this.datiDomandaService.getListaSuggest(idCampo, this.nazionePF).subscribe(data => {
          if (data.length > 0) {
            this.nazioniResPF = data;
          } else {
            this.nazioniResPF = new Array<AttivitaDTO>();
            this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
            this.nazioniResPF.push(this.suggesttionnull);
          }
        });
        break;


      default:
        break;
    }
  }

  salvaModifiche() {


  }

  salvaModifichePF() {
    this.loadedSettingPF = false;

    let soggPF = new BeneficiarioSoggCorrPF();
    let provN = new AttivitaDTO;
    provN.idAttivita = this.idProvinciaDiNascita;
    provN.descAttivita = this.provinciaDiNascita;
    this.listaProvinceNascitaPF.push(provN);
    let comN = new AttivitaDTO
    comN.idAttivita = this.idComuneDiNascita;
    comN.descAttivita = this.comuneDiNascita;
    this.comuniNascPF.push(comN);
    let regN = new AttivitaDTO;
    regN.idAttivita = this.idRegioneDiNascita;
    regN.descAttivita = this.regioneDiNascita;
    this.regioniNascitaPF.push(regN);
    let nazN = new AttivitaDTO;
    nazN.idAttivita = this.idNazioneDiNascita;
    nazN.descAttivita = this.nazioneDiNascita;
    this.nazioniNascitaPF.push(nazN);
    let naz = new AttivitaDTO;
    naz.idAttivita = this.idNazionePF;
    naz.descAttivita = this.nazionePF;
    this.nazioniResPF.push(naz);
    let reg = new AttivitaDTO;
    reg.idAttivita = this.idRegionePF;
    reg.descAttivita = this.regionePF;
    this.regioniResPF.push(reg)
    let prov = new AttivitaDTO;
    prov.idAttivita = this.idProvinciaPF;
    prov.descAttivita = this.provinciaPF;
    this.provincieResPF.push(prov);
    let com = new AttivitaDTO;
    com.idAttivita = this.idComunePF;
    com.descAttivita = this.comunePF;
    this.comuniResPF.push(com);
    soggPF.cognome = this.cognome;
    soggPF.nome = this.nome;
    soggPF.capPF = this.capPF;
    soggPF.codiceFiscale = this.codiceFiscale;
    soggPF.idUtenteAgg = this.idUtente;
    soggPF.indirizzoPF = this.indirizzoPF;
    soggPF.dataDiNascita = this.dataDiNascita;
    soggPF.idRuoloPF = this.idRuoloPF;
    soggPF.idProvinciaDiNascita = this.listaProvinceNascitaPF.find(p => p.descAttivita == this.provinciaDiNascita).idAttivita;
    soggPF.idComuneDiNascita = this.comuniNascPF.find(c => c.descAttivita == this.comuneDiNascita).idAttivita;
    soggPF.idRegioneDiNascita = this.regioniNascitaPF.find(r => r.descAttivita == this.regioneDiNascita).idAttivita;
    soggPF.idNazioneDiNascita = this.nazioniNascitaPF.find(n => n.descAttivita == this.nazioneDiNascita).idAttivita;
    soggPF.idComunePF = this.comuniResPF.find(c => c.descAttivita == this.comunePF).idAttivita;
    soggPF.idRegionePF = this.regioniResPF.find(r => r.descAttivita == this.regionePF).idAttivita;
    soggPF.idProvinciaPF = this.provincieResPF.find(p => p.descAttivita == this.provinciaPF).idAttivita;
    soggPF.idNazionePF = this.nazioniResPF.find(n => n.descAttivita == this.nazionePF).idAttivita;
    soggPF.idIndirizzo = this.idIndirizzoPF;
    soggPF.descTipoSoggCorr = this.tipoSoggettoCorr
    soggPF.descStatoDomanda = this.benefSoggCorrPF.descStatoDomanda;
     this.subscribers.modifica = this.datiDomandaService.modificaPF(soggPF, this.idSoggettoCorr, this.idDomanda).subscribe(data => {
      if (data == true) {
        this.savePF = data;
        this.loadedSettingPF = true
        this.goBack();
      } else {
        this.isNotSavePF = true;
        this.loadedSettingPF = true
      }
    })
  }

  //METODO PER PRENDERMI I COMUNI LEGATI ALLA PROVINCIA DI RESIDENZA
  getComuni(provincia: HTMLSelectElement) {
    this.modificaAnagraficaService.getComuni(provincia.value);
    this.subscribers.comuniInfo$ = this.modificaAnagraficaService.comuniInfo$.subscribe(data => {
      if (data) {
        this.comuni = data;
        //QUI SVUOTO L'ARRAY DI DESC_COMUNI_RES PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
        this.descComuni = [];
        this.comuni.forEach(val => {
          if (val) {
            this.descComuni.push(val.descComune);
          }
        });
      }
    });
    this.myForm.controls.comune.setValue("")
  }
  //FILTRO PER AUTOCOMPLETE
  private _filter(value: string): string[] {
    if (value.length < 3) {
      return [];
    }
    const filterValue = value.toLowerCase();
    return this.descComuni.filter(option => option.toLowerCase().includes(filterValue));
  }

  //FUNZIONE PER LA VALIDAZIONE
  public hasError = (controlName: string, errorName: string) => {
    return this.myForm.controls[controlName].hasError(errorName);
  }

  getProvincieMeth() {

    this.loadedProvincie = true; 
    this.subscribers.provincieInfo$ = this.datiDomandaService.getProvince().subscribe(data => {
      if (data) {
        this.provincie = data;
        this.loadedProvincie = true
      }
    });
  }

  getRegioniMeth() {
    this.loadedRegioni = false; 
  
    this.subscribers.regioniInfo$ = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioni = data;
        this.loadedRegioni = true; 
      }
    });
  }

  getNazioniMeth() {
    
    this.loadedNazioni = false; 
    this.subscribers.nazione = this.datiDomandaService.getNazioni().subscribe(data=>{
      if(data)
      this.nazioni = data; 
      this.loadedNazioni = true
    });
  }

  goBack() {
    console.log(this.idSoggetto, this.numeroDomanda, this.idEnteGiuridico, this.tipoSoggetto)
    if (this.tipoSoggetto == "Persona Giuridica") {
      this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaEG"],
        { queryParams: { idSoggetto: this.idSoggetto, numeroDomanda: this.numeroDomanda, idEnteGiuridico: this.idEnteGiuridico, isEnteGiuridico: this.tipoSoggetto } });
    } else {
      this.router.navigate(["/drawer/" + this.idOp + "/datiDomandaPF"],
        { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.numeroDomanda, tipoSoggetto: this.tipoSoggetto } });
    }
  }

  isLoading() {
    if (!this.userloaded || !this.loadedEG || !this.loadedPF || !this.loadedSettingPF || !this.loadedNazioni||!this.loadedProvincie
      || !this.loadedRegioni) {
      return true;
    }
    return false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
