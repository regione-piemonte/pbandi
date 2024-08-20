/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { Form, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { constants } from 'buffer';
import { element } from 'protractor';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { ControdeduzioneVO } from 'src/app/gestione-crediti/commons/dto/controdeduzioneVO';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ControlloLocoVo } from '../../commons/controllo-loco-vo';
import { searchControlliDTO } from '../../commons/dto/searchControlliDTO';
import { ControlliService } from '../../services/controlli.service';
import { DialogEditAltriControlliComponent } from '../dialog-edit-altri-controlli/dialog-edit-altri-controlli.component';

@Component({
  selector: 'app-inserimento-altri-controlli',
  templateUrl: './inserimento-altri-controlli.component.html',
  styleUrls: ['./inserimento-altri-controlli.component.scss']
})
export class InserimentoAltriControlliComponent implements OnInit {

  userloaded: boolean = true;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: number;
  messageError: string;
  isMessageErrorVisible: boolean;
  criteriRicercaOpened: boolean = true;
  denIns: string;
  suggesttionnull: AttivitaDTO = new AttivitaDTO();
  listaDenomin: AttivitaDTO[] = [];
  descBanIns: string;
  listaBando: AttivitaDTO[] = [];
  listaCodiceProgetto: AttivitaDTO[] = [];
  listaStatoControllo: AttivitaDTO[] = [];
  listaAutoritaControllante: AttivitaDTO[] = [];
  idStatoControllo: number;
  numProtocollo: string;
  dataInizioControllo: Date;
  dataFineControllo: Date;
  importoDaControllare: number;
  importoIrregolarita: number;
  importoAgevIrreg: number;
  importoDaControllareFormatted: string
  importoIrregolaritaFormatted: string;
  importoAgevIrregFormatted: string;
  dataVisitaControllo: Date;
  lisTipoVisita: string[] = [];
  descTipoVisita: string;
  istruttoreVisita: string;
  dataAvvioControlli: Date;
  loadedStControllo: boolean;
  tipologiaControllo: string;
  codProIns: string;
  codProgDTO: AttivitaDTO = new AttivitaDTO();
  result: any;
  idAutoritaControllante: number;
  controllo: ControlloLocoVo = new ControlloLocoVo();
  isSave: boolean;
  isLoadedSalva: boolean = true;
  idProgetto: number;
  progBandoLinea: number;
  idSoggetto: number;
  loadedBandi: boolean = true;
  loadedProgetto: boolean = true;
  suggestDTO: searchControlliDTO = new searchControlliDTO();  
  bandoControl = new FormControl('', Validators.required);
  beneficiarioControl = new FormControl('', Validators.required);
  progettoControl = new FormControl('', Validators.required);
  autoritaControllanteControl = new FormControl('', Validators.required);
  tipoControlloControl = new FormControl('', Validators.required);
  statoControlloControl = new FormControl('', Validators.required);
  isConferma: boolean;
   

  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private controlliService: ControlliService,
    public sharedService: SharedService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    public dialog: MatDialog
  ) { }

  @ViewChild("formControl") formControllo: any; 
  ngOnInit(): void {

    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
        this.loadedStControllo = false;
        this.subscribers.staprop = this.controlliService.getListaStatoControllo().subscribe({
          next: data => this.listaStatoControllo = data,
          error: errore => console.log(errore),
          complete: () => this.loadedStControllo = true
        });
        this.subscribers.listaAutoritaControllante = this.controlliService.getAutoritaControlante().subscribe({
          next: data => this.listaAutoritaControllante = data,
          error: errore => console.log(errore),
          complete: () => this.loadedStControllo = true
        });
        this.lisTipoVisita.push("Amministrativa");
        this.lisTipoVisita.push("Video-call");
      }
    });
  }
  setCampi() {

    // if (this.codProIns != null && this.codProIns.toString().length > 0) {
    //   var codv = this.listaCodiceProgetto.find(p => p.descAttivita == this.codProIns)
    //   this.controllo.idProgetto = codv.idAttivita;
    // } else {
    //   this.controllo.idProgetto = null;
    // }

    // if (this.denIns != null && this.denIns.toString().length > 0) {
    //   var den = this.listaDenomin.find(de => de.descAttivita == this.denIns);
    //   this.controllo.idSoggettoBenef = den.idSoggetto;

    // } else {
    //   this.controllo.idSoggettoBenef = null;
    // }

    // if (this.descBanIns != null && this.descBanIns.toString().length > 0) {
    //   const bando = this.listaBando.find(b => b.descAttivita == this.descBanIns)
    //   this.controllo.progrBandoLinea = bando.progBandoLinea;
    // } else {
    //   this.controllo.progrBandoLinea = null;
    // }

    console.log(this.idSoggetto + "  " + this.progBandoLinea + "  " + this.idProgetto);

    this.controllo.idProgetto = this.idProgetto;
    this.controllo.progrBandoLinea = this.progBandoLinea;
    this.controllo.idSoggettoBenef = this.idSoggetto;
    this.controllo.idAutoritaControllante = this.idAutoritaControllante;
    this.controllo.tipoControllo = this.tipologiaControllo;
    this.controllo.dataInizioControlli = this.dataInizioControllo;
    this.controllo.dataFineControlli = this.dataFineControllo;
    if (this.importoAgevIrreg != null) {
      this.controllo.importoAgevIrreg = this.importoAgevIrreg;
    }
    if (this.importoDaControllare != null) {
      this.controllo.importoDaControllare = this.importoDaControllare;
    }
    if (this.importoIrregolarita != null) {
      this.controllo.importoIrregolarita = this.importoIrregolarita;
    }
    this.controllo.idStatoControllo = this.idStatoControllo;
    this.controllo.numProtocollo = this.numProtocollo;
    this.controllo.dataAvvioControlli = this.dataAvvioControlli;
    this.controllo.dataVisitaControllo = this.dataVisitaControllo;
    (this.descTipoVisita) ? this.controllo.descTipoVisita = this.descTipoVisita : null;
    (this.istruttoreVisita != null) ? this.controllo.istruttoreVisita = this.istruttoreVisita : null;

    if (this.idAutoritaControllante != 4) {
      this.controllo.dataAvvioControlli = null;
      this.controllo.dataVisitaControllo = null;
      this.controllo.istruttoreVisita = null;
      this.controllo.descTipoVisita = null;
    }
    console.log(this.controllo);
  }

  gestisciAllegati() {

    this.isLoadedSalva = false;
    let dialogRef = this.dialog.open(DialogEditAltriControlliComponent, {
      width: '30%',
      data: {
        element: 1,
        controllo: this.controllo,
        idUtente: this.idUtente,
        idControllo: 0
      }
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data) {
        console.log(data);
        this.isSave = data;
        this.isLoadedSalva = true;
        setTimeout(() => {
          this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI + "/ricercaAltriControlli"], { queryParams: {} });
        }, 3000);
      }
    });



  }

  controllaCampi(){
    this.isMessageErrorVisible = false;
    this.isConferma=false;
    this.setCampi();

    if(this.controllo.idSoggettoBenef==null){
     this.isConferma=true;
    } 
    if(this.controllo.progrBandoLinea==null){
      this.isConferma = true;
    } 
    if(this.controllo.idProgetto==null){
      this.isConferma=true;
    } 
    if(this.controllo.tipoControllo==null){
      this.isConferma=true;
    }
    if(this.controllo.idAutoritaControllante==null){
      this.isConferma=true; 
    }
    if(this.controllo.idStatoControllo==null){
      this.isConferma=true;
    }

    if(this.isConferma==true){
      this.isMessageErrorVisible = true;
      // this.progettoControl.hasError('required');
      // this.formControllo.markAllAsTouched();
      this.bandoControl.markAllAsTouched(); 
      this.progettoControl.markAllAsTouched();
      this.beneficiarioControl.markAllAsTouched();
      this.statoControlloControl.markAllAsTouched();
      this.autoritaControllanteControl.markAllAsTouched();
      this.tipoControlloControl.markAllAsTouched();
    } else {
      this.salvaAltroControllo();
    }
  }
  changeAutoritaControllante(){
    if(this.idAutoritaControllante==4){
      this.idStatoControllo=1
      this.statoControlloControl.disable();
    } else{
      this.statoControlloControl.enable();
    }

  }

  salvaAltroControllo() {
    this.isLoadedSalva = false;
    if (this.idAutoritaControllante == 4) {
      this.subscribers.avviaIter = this.controlliService.salvaAltroControllo(this.controllo, this.idUtente, 1).subscribe(data => {
        if (data) {
          this.result = data;
          this.isSave = true;
          this.isLoadedSalva = true;
          setTimeout(() => {
            this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI + "/ricercaAltriControlli"], { queryParams: {} });
          }, 3000);
        }
      });

    } else {
      this.gestisciAllegati();
    }


  }
  disableStato(idStato): boolean{
    if(idStato==3)
    return true
    else
    return false;
  }
  suggest(id: number, value: string) {

    switch (id) {
      case 1:
        if (value.length >= 3) {
          this.listaDenomin = new Array<AttivitaDTO>();
          this.suggesttionnull.descAttivita = 'caricamento...';
          this.listaDenomin.push(this.suggesttionnull);
          this.suggestDTO.idProgetto = this.checkProgetto(); 
          this.suggestDTO.progrBandoLinea = this.checkProgBando(); 
          this.suggestDTO.value = value;
          this.subscribers.listaDenomin = this.controlliService.getListaSuggest(id, this.suggestDTO).subscribe(data => {
            if (data.length > 0) {
              this.listaDenomin = data;
            } else {
              this.listaDenomin = new Array<AttivitaDTO>();
              this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
              this.listaDenomin.push(this.suggesttionnull);
            }
          });
        }
        break;
        case 2:
          this.listaBando = new Array<AttivitaDTO>();
          if (value.length >= 3) {
            this.suggesttionnull.descAttivita = 'caricamento...';
            this.listaBando.push(this.suggesttionnull);
            this.suggestDTO.idProgetto = this.checkProgetto(); 
            this.suggestDTO.idSoggetto = this.checkSoggetto(); 
            this.suggestDTO.value = value;
            this.subscribers.listaBando = this.controlliService.getListaSuggest(id, this.suggestDTO).subscribe(data => {
              if (data.length > 0) {
                this.listaBando = data;
              } else {
                this.listaBando = new Array<AttivitaDTO>();
                this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
                this.listaBando.push(this.suggesttionnull);
              }
            });
          }
          break;

        case 3:
          this.listaCodiceProgetto = new Array<AttivitaDTO>();
          if (value.length >= 3) {
            this.suggesttionnull.descAttivita = 'caricamento...';
            this.listaCodiceProgetto.push(this.suggesttionnull);
            this.suggestDTO.idSoggetto = this.checkSoggetto(); 
            this.suggestDTO.progrBandoLinea = this.checkProgBando();
            this.suggestDTO.value = value; 
            this.subscribers.listaCodiceProgetto = this.controlliService.getListaSuggest(id, this.suggestDTO).subscribe(data => {
              if (data.length > 0) {
                this.listaCodiceProgetto = data;
              } else {
                this.listaCodiceProgetto = new Array<AttivitaDTO>();
                this.suggesttionnull.descAttivita = 'Nessuna corrispondenza';
                this.listaCodiceProgetto.push(this.suggesttionnull);
              }
            });
          }
          break;
  

      default:
        break;
    }
  }
  getListaBando(event: any) {

    this.loadedBandi = false;
    if (this.denIns != null && this.denIns.toString().length > 0) {
      var den = this.listaDenomin.find(de => de.descAttivita == this.denIns);
      this.idSoggetto = den.idSoggetto;
      this.subscribers.getListaBando = this.controlliService.getListaBando(den.idSoggetto).subscribe(data => {
        if (data) {
          this.listaBando = data;
          this.loadedBandi = true;
        }
      });
    } else {
      this.listaBando = [];
      this.loadedBandi = true;
    }

  }
  check(string: string) {

  }

  getListaProgetto(event: any) {

    this.loadedProgetto = false;
    console.log(this.idSoggetto + "   " + this.progBandoLinea);

    if (this.progBandoLinea != null && this.idSoggetto != null) {
      this.subscribers.getListaProgetto = this.controlliService.getListaProgetto(this.idSoggetto, this.progBandoLinea).subscribe(data => {
        if (data) {
          this.listaCodiceProgetto = data;
          this.loadedProgetto = true;
        }
      });
    } else {
      this.listaCodiceProgetto = [];
      this.loadedProgetto = true;
    }



  }

  setImporto(campoNumber: number) {
    switch (campoNumber) {
      case 1:
        this.importoDaControllare = this.sharedService.getNumberFromFormattedString(this.importoDaControllareFormatted);
        if (this.importoDaControllare !== null) {
          this.importoDaControllareFormatted = this.sharedService.formatValue(this.importoDaControllare.toString());
        }
        break;
      case 2:
        this.importoIrregolarita = this.sharedService.getNumberFromFormattedString(this.importoIrregolaritaFormatted);
        if (this.importoIrregolarita !== null) {
          this.importoIrregolaritaFormatted = this.sharedService.formatValue(this.importoIrregolarita.toString());
        }
        break
      case 3:
        this.importoAgevIrreg = this.sharedService.getNumberFromFormattedString(this.importoAgevIrregFormatted);
        if (this.importoAgevIrreg !== null) {
          this.importoAgevIrregFormatted = this.sharedService.formatValue(this.importoAgevIrreg.toString());
        }
        break;
      default:
        break;
    }
  }

  setDataNull(idData: number) {
    if (idData == 1) {
      this.dataInizioControllo = null;
    }
    if (idData == 2) {
      this.dataFineControllo = null
    }
    if (idData == 4) {
      this.dataVisitaControllo = null;
    }
    if (idData == 3) {
      this.dataAvvioControlli = null;
    }
  }

  goBack() {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_FIN + "/ricercaControlliProgettiFinp"], { queryParams: {} });
  }
  isLoading() {
    if (!this.userloaded || !this.isLoadedSalva || !this.loadedBandi || !this.loadedProgetto) {
      return true;
    }
    return false;
  }

  
  private checkProgBando(){
  
    let progBandoLinea: any; 
    if (this.descBanIns != null && this.descBanIns.toString().length > 0) {
      const bando = this.listaBando.find(b => b.descAttivita == this.descBanIns)
     progBandoLinea = bando.progBandoLinea;
    
    } else {
     progBandoLinea = null;
    }

    return progBandoLinea; 
  }
  private checkProgetto(){
    let idProgetto:any;
    if (this.codProIns != null && this.codProIns.toString().length > 0) {
      var codv = this.listaCodiceProgetto.find(p => p.descAttivita == this.codProIns)
      idProgetto = codv.idAttivita;
      console.log(this.codProgDTO);
    } else {
      idProgetto = null;
    }
    return idProgetto;
  }
  private checkSoggetto(){
    let idSoggetto:any; 
    if (this.denIns != null && this.denIns.toString().length > 0) {
      var den = this.listaDenomin.find(de => de.descAttivita == this.denIns);
     idSoggetto = den.idSoggetto;
    } else {
      idSoggetto = null;
    }

    return idSoggetto; 
  }
}
