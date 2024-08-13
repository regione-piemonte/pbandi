/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FlatTreeControl } from '@angular/cdk/tree';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { DocumentoAllegatoDTO } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AttivitaAtecoNodoDTO } from '../../commons/dto/attivita-ateco-nodo-dto';
import { DecodificaDTO } from '../../commons/dto/decodifica-dto';
import { FornitoreFormDTO } from '../../commons/dto/fornitore-form-dto';
import { NazioneDTO } from '../../commons/dto/nazione-dto';
import { FornitoreService } from '../../services/fornitore.service';
import { RendicontazioneService } from '../../services/rendicontazione.service';

export class AttivitaAtecoFlatNode {
  attivitaAteco: AttivitaAtecoNodoDTO;
  level: number;
  expandable: boolean;
}

@Component({
  selector: 'app-form-nuovo-modifica-fornitore',
  templateUrl: './form-nuovo-modifica-fornitore.component.html',
  styleUrls: ['./form-nuovo-modifica-fornitore.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class FormNuovoModificaFornitoreComponent implements OnInit {
  @Input('user') user: UserInfoSec;
  @Input('fornitore') fornitore: FornitoreFormDTO;
  @Input('fornitoreAnagrafica') fornitoreAnagrafica: FornitoreFormDTO;
  @Input('fornitoreFattura') fornitoreFattura: FornitoreFormDTO;
  @Input('isNuovo') isNuovo: boolean;
  @Input('isXml') isXml: boolean;
  @Input('isSeleziona') isSeleziona: boolean;
  @Input('tipologie') tipologie: Array<DecodificaDTO>;
  @Output() saved = new EventEmitter<any>();
  tipologiaSelected: DecodificaDTO;
  cognome: string;
  nome: string;
  denominazione: string;
  codiceFiscale: string;
  partitaIva: string;
  codIdentificativo: string;
  isSenzaCFoEstero: boolean;
  formaGiuridica: string = "1";
  fornitoreSelected: string = "A";
  codiceIPA: string;
  nazioni: Array<NazioneDTO>;
  nazioneSelected: NazioneDTO;
  tipologieFormaGiuridica: Array<DecodificaDTO>;
  tipologiaFormaGiuridicaSelected: DecodificaDTO;
  isAtecoVisible: boolean;
  alberoAteco: Array<AttivitaAtecoNodoDTO>;
  codiceAteco: string;
  idAttivitaAtecoSelected: number = null;
  isCFVerificato: boolean;
  isSenzaCFoEsteroDisabled: boolean;

  treeControl: FlatTreeControl<AttivitaAtecoFlatNode>;
  treeFlattener: MatTreeFlattener<AttivitaAtecoNodoDTO, AttivitaAtecoFlatNode>;
  dataSource: MatTreeFlatDataSource<AttivitaAtecoNodoDTO, AttivitaAtecoFlatNode>;
  flatNodeMap = new Map<AttivitaAtecoFlatNode, AttivitaAtecoNodoDTO>();
  nestedNodeMap = new Map<AttivitaAtecoNodoDTO, AttivitaAtecoFlatNode>();
  selectedParent: AttivitaAtecoFlatNode | null = null;
  newItemName = '';
  getLevel = (node: AttivitaAtecoFlatNode) => node.level;
  isExpandable = (node: AttivitaAtecoFlatNode) => node.expandable;
  getChildren = (node: AttivitaAtecoNodoDTO): AttivitaAtecoNodoDTO[] => node.figli;
  hasChild = (_: number, _nodeData: AttivitaAtecoFlatNode) => _nodeData.expandable;
  transformer = (node: AttivitaAtecoNodoDTO, level: number) => {
    const existingNode = this.nestedNodeMap.get(node);
    const flatNode = existingNode && existingNode.attivitaAteco === node
      ? existingNode
      : new AttivitaAtecoFlatNode();
    flatNode.attivitaAteco = node;
    flatNode.level = level;
    flatNode.expandable = !!node.figli?.length;
    this.flatNodeMap.set(flatNode, node);
    this.nestedNodeMap.set(node, flatNode);
    return flatNode;
  }

  @ViewChild("fornitoreForm", { static: true }) fornitoreForm: NgForm;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedNazioni: boolean = true;
  loadedTipologieFormaGiuridica: boolean = true;
  loadedAteco: boolean = true;
  loadedSave: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private rendicontazioneService: RendicontazioneService,
    private fornitoreService: FornitoreService,
    private handleExceptionService: HandleExceptionService,
  ) {
    this.treeFlattener = new MatTreeFlattener(this.transformer, this.getLevel,
      this.isExpandable, this.getChildren);
    this.treeControl = new FlatTreeControl<AttivitaAtecoFlatNode>(this.getLevel, this.isExpandable);
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
  }

  ngOnInit(): void {
    if (this.isNuovo && !this.isXml) {
      this.tipologiaSelected = this.tipologie.find(t => t.id = Constants.ID_TIPO_FORNITORE_PERSONA_FISICA);
    } else { //modifica o lettura xml
      this.tipologiaSelected = this.tipologie.find(t => t.id === this.fornitore.idTipoSoggetto);
      if (this.fornitore.codiceFiscaleFornitore.indexOf('PBAN') > -1) {
        this.codiceFiscale = "N.D.";
      } else {
        this.codiceFiscale = this.fornitore.codiceFiscaleFornitore;
      }
      this.cognome = this.fornitore.cognomeFornitore;
      this.nome = this.fornitore.nomeFornitore;
      this.denominazione = this.fornitore.denominazioneFornitore;
      this.partitaIva = this.fornitore.partitaIvaFornitore;
      this.isSenzaCFoEstero = this.fornitore.idNazione ? true : false;
      this.codIdentificativo = this.fornitore.altroCodice;
      this.formaGiuridica = this.fornitore.flagPubblicoPrivato ? this.fornitore.flagPubblicoPrivato.toString() : null;
      this.codiceIPA = this.fornitore.codUniIpa;
      this.disableCF();
    }
    if (this.isNuovo || (!this.isNuovo && this.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_GIURIDICA)) {
      this.loadedNazioni = false;
      this.subscribers.nazioni = this.fornitoreService.getNazioni().subscribe(data => {
        this.nazioni = data;
        if (!this.isNuovo && this.fornitore.idNazione) {
          this.nazioneSelected = this.nazioni.find(n => n.idNazione === this.fornitore.idNazione);
          this.onSelectNazione();
        }
        this.loadedNazioni = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
      this.loadTipologieFormaGiuridica();
      this.loadedAteco = false;
      this.subscribers.ateco = this.fornitoreService.getAlberoAttivitaAteco(this.user.idUtente).subscribe(data => {
        this.alberoAteco = data;
        this.dataSource.data = data;
        if (!this.isNuovo && this.fornitore.idAttivitaAteco) {
          this.idAttivitaAtecoSelected = this.fornitore.idAttivitaAteco;
          this.getCodiceAtecoFromAlbero(this.alberoAteco);
        }
        this.loadedAteco = true;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    }
  }

  loadTipologieFormaGiuridica() {
    this.loadedTipologieFormaGiuridica = false;
    this.subscribers.tipoFormaGiuridica = this.rendicontazioneService.getTipologieFormaGiuridica(this.formaGiuridica == "1" ? "S" : "N").subscribe(data => {
      this.tipologieFormaGiuridica = data;
      if ((!this.isNuovo || this.isXml) && this.formaGiuridica === this.fornitore.flagPubblicoPrivato.toString()) {
        this.tipologiaFormaGiuridicaSelected = this.tipologieFormaGiuridica.find(t => t.id === this.fornitore.idFormaGiuridica);
      } else {
        this.tipologiaFormaGiuridicaSelected = null;
      }
      this.loadedTipologieFormaGiuridica = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  getCodiceAtecoFromAlbero(albero: Array<AttivitaAtecoNodoDTO>) {
    if (this.codiceAteco) return;
    for (let nodo of albero) {
      if (nodo.figli && nodo.figli.length > 0) {
        this.getCodiceAtecoFromAlbero(nodo.figli);
      }
      if (nodo.idAttivitaAteco === this.idAttivitaAtecoSelected) {
        this.codiceAteco = nodo.codAteco;
        return;
      }
    }
  }

  disableCF() {
    if (this.verificaCodice(this.codiceFiscale)) {
      this.isCFVerificato = true;
    }
    if ((this.fornitore.codiceFiscaleFornitore == 'N.D.') || (this.fornitore.codiceFiscaleFornitore.indexOf('PBAN') > -1)) {
      this.isSenzaCFoEsteroDisabled = true;
    }
  }

  onSelectTipologia() {
    this.codiceFiscale = null;
    this.fornitoreForm.form.get('codFisc').markAsUntouched();
    this.cognome = null;
    this.nome = null;
    this.denominazione = null;
    this.partitaIva = null;
    this.isSenzaCFoEstero = false;
    this.codIdentificativo = null;
    this.nazioneSelected = null;
    this.formaGiuridica = "1";
    this.tipologiaFormaGiuridicaSelected = null;
    this.codiceIPA = null;
    this.idAttivitaAtecoSelected = null;
    this.codiceAteco = null;
  }

  isSenzaCFoEsteroChecked(event: MatCheckboxChange) {
    if (event.checked) {
      this.codiceFiscale = "N.D.";
    } else {
      this.codiceFiscale = this.isNuovo && !this.isXml ? null : this.fornitore.codiceFiscaleFornitore;
    }
  }

  onSelectNazione() {
    let eu = this.isEu(this.nazioneSelected.idNazione);
    if (eu && (this.codiceFiscale == 'N.D.' || this.codiceFiscale.indexOf('PBAN') > -1)) {
      this.isAtecoVisible = true;
    } else {
      this.isAtecoVisible = false;
    }
  }

  onSelectCodAteco(attivitaAteco: AttivitaAtecoNodoDTO) {
    this.codiceAteco = attivitaAteco.codAteco;
    this.idAttivitaAtecoSelected = attivitaAteco.idAttivitaAteco;
  }

  isSalvaDisabled() {
    if (!this.tipologiaSelected) {
      return true;
    }
    if (this.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_FISICA && !(this.nome && this.cognome && this.codiceFiscale)) {
      return true;
    }
    if (this.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_GIURIDICA) {
      if (!(this.denominazione && this.codiceFiscale && this.tipologiaFormaGiuridicaSelected)) {
        return true;
      }
      if (this.isSenzaCFoEstero && !(this.codIdentificativo && this.nazioneSelected)) {
        return true;
      }
      if (this.formaGiuridica == "2" && !this.codiceIPA) {
        return true;
      }
      if (this.isSenzaCFoEstero && this.nazioneSelected) {
        let eu = this.isEu(this.nazioneSelected.idNazione);
        if (eu) {
          if (!this.codiceAteco || this.codiceAteco === '') {
            return true;
          }
        }
      }
    }
    return false;
  }

  checkAlphabetic(string) {
    if (string.search(/^[a-zA-Z ]*$/) === -1) return false;
    else return true;
  }

  controllaCF(cf: string) {
    let validi, i, s, set1, set2, setpari, setdisp;
    if (cf == '') return '';
    cf = cf.toUpperCase();
    if (cf.length != 16) {
      return "La lunghezza del codice fiscale non è\r\n"
        + "corretta: il codice fiscale dovrebbe essere lungo\n"
        + "esattamente 16 caratteri.\n";
    }
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (i = 0; i < 16; i++) {
      if (validi.indexOf(cf.charAt(i)) == -1)
        return "Il codice fiscale contiene un carattere non valido `" +
          cf.charAt(i) +
          "'.\r\nI caratteri validi sono le lettere e le cifre.\n";
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for (i = 1; i <= 13; i += 2) {
      s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    for (i = 0; i <= 14; i += 2) {
      s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    if (s % 26 != cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
      return "Il codice fiscale non è corretto:\r\n" +
        "il codice di controllo non corrisponde.\n";
    }
    return '';
  }

  controllaPIVA(pi: string) {
    if (pi == '') {
      return '';
    }
    if (pi.length != 11) {
      return "La lunghezza della partita IVA non è\n" +
        "corretta: la partita IVA dovrebbe essere lunga\n" +
        "esattamente 11 caratteri.\n";
    }
    let validi = "0123456789";
    for (let i = 0; i < 11; i++) {
      if (validi.indexOf(pi.charAt(i)) == -1) {
        return "Partita IVA / codice fiscale contiene un carattere non valido '" +
          pi.charAt(i) + "'.\r\nI caratteri validi sono le cifre.\n";
      }
    }
    let s = 0;
    for (let i = 0; i <= 9; i += 2)
      s += pi.charCodeAt(i) - '0'.charCodeAt(0);
    for (let i = 1; i <= 9; i += 2) {
      let c = 2 * (pi.charCodeAt(i) - '0'.charCodeAt(0));
      if (c > 9) c = c - 9;
      s += c;
    }
    if ((10 - s % 10) % 10 != pi.charCodeAt(10) - '0'.charCodeAt(0)) {
      return "Partita IVA / codice fiscale non valido:\r\n" +
        "il codice di controllo non corrisponde.\n";
    }
    return '';
  }

  verificaCodice(cod: string) {
    let err = '';
    if (cod == '') {
      err = "Compilare il campo Codice Fiscale\n";
    } else if (/^([A-Z]){4}_([0-9]){11}\*$/.test(cod)) {
      err = '';
    } else if (cod == '00000000000') {
      err = "Il Codice Fiscale non è formalmente corretto\n";
    } else if (cod.length == 16) {
      err = this.controllaCF(cod);
    } else if (cod.length == 11 && cod != '00000000000') {
      err = this.controllaPIVA(cod);
    } else if (cod === 'N.D.') {
      err = '';
    } else {
      err = "Il codice introdotto non è valido:\r\n" +
        "  - un codice fiscale deve essere lungo 16 caratteri \r\n" +
        "  - una partita IVA deve essere lunga 11 caratteri \r\n";
    }

    if (err > '') {
      let result = err.replace("\r\n", '<br />')
      this.showMessageError("<div class='contentMsg'><p>VALORE ERRATO</p><p>" + result + "</p></div>");
      return false;
    } else {
      return true;
      //showErrorMessage("Il codice è valido.");
    }
  }

  isEu(idNazione: number) {
    let eu: boolean;
    for (let nazione of this.nazioni) {
      if (idNazione == nazione.idNazione &&
        nazione.flagAppartenenzaUe == 'S') {
        eu = true;
        return eu;
      }
    }
    return eu;
  }

  validate() {
    this.resetMessageError();
    if (this.tipologiaSelected.id === Constants.ID_TIPO_FORNITORE_PERSONA_FISICA) {
      if (this.cognome.trim().length < 2) {
        this.showMessageError("Compilare il cognome.");
        return false;
      }
      if (!this.checkAlphabetic(this.cognome)) {
        this.showMessageError("Compilare il cognome con caratteri alfabetici.");
        return false;
      }
      if (this.nome.trim().length < 2) {
        this.showMessageError("Compilare il nome.");
        return false;
      }
      if (!this.checkAlphabetic(this.nome)) {
        this.showMessageError("Compilare il nome con caratteri alfabetici.");
        return false;
      }
      let cfregexp = /^(?:[B-DF-HJ-NP-TV-Z](?:[AEIOU]{2}|[AEIOU]X)|[AEIOU]{2}X|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[\dLMNP-V][1-9MNP-V]|[1-9MNP-V][0L]))[A-Z]$/i;
      if (this.codiceFiscale.trim().length < 16
        || cfregexp.test(this.codiceFiscale) == false) {
        this.showMessageError("Inserire un codice fiscale valido.");
        return false;
      }
      return true;
    } else { //PERSONA GIURIDICA
      if (this.fornitore.denominazioneFornitore.trim().length < 2) {
        this.showMessageError("Compilare la denominazione.");
        return false;
      }
      if (!this.isSenzaCFoEstero) {
        let cod = this.codiceFiscale.trim();
        if (!this.verificaCodice(cod)) {
          return false;
        }
        if (this.fornitore.partitaIvaFornitore && this.fornitore.partitaIvaFornitore.trim().length != 0 && this.fornitore.partitaIvaFornitore.trim().length != 11) {
          this.showMessageError("Se indicata, la partita iva deve essere di 11 caratteri.");
          return false;
        }
      } else {
        if (this.codIdentificativo.trim().length == 0) {
          this.showMessageError("Indicare un codice identificativo per il fornitore (max 30 caratteri alfanumerici).");
          return false;
        }
      }
      return true;
    }
  }

  salva() {
    this.resetMessageError();
    this.loadedSave = false;
    if (!this.isSeleziona && this.fornitore && this.fornitore.idTipoSoggetto === this.tipologiaSelected.id &&
      (this.fornitore.codiceFiscaleFornitore === this.codiceFiscale || (this.fornitore.codiceFiscaleFornitore.indexOf('PBAN') > -1 && this.codiceFiscale === "N.D.")) &&
      this.fornitore.cognomeFornitore === this.cognome &&
      this.fornitore.nomeFornitore === this.nome &&
      this.fornitore.denominazioneFornitore === this.denominazione &&
      this.fornitore.partitaIvaFornitore === this.partitaIva &&
      this.fornitore.altroCodice === this.codIdentificativo &&
      this.fornitore.flagPubblicoPrivato === +this.formaGiuridica &&
      (!this.nazioneSelected || (this.nazioneSelected && this.fornitore.idNazione === this.nazioneSelected.idNazione)) &&
      (!this.tipologiaFormaGiuridicaSelected || (this.tipologiaFormaGiuridicaSelected && this.fornitore.idFormaGiuridica === this.tipologiaFormaGiuridicaSelected.id)) &&
      this.fornitore.codUniIpa === this.codiceIPA &&
      this.fornitore.idAttivitaAteco === this.idAttivitaAtecoSelected) {
      this.saved.emit({ esito: "Nessun dato è stato modificato.", idTipoFornitore: this.tipologiaSelected.id, fornitore: this.fornitore });
      this.loadedSave = true;
    } else if (this.isSeleziona && this.fornitoreSelected === "A" && this.fornitore.denominazioneFornitore === this.fornitoreAnagrafica.denominazioneFornitore
      && this.tipologiaFormaGiuridicaSelected && this.fornitore.idFormaGiuridica === this.tipologiaFormaGiuridicaSelected.id
      && this.fornitore.codUniIpa === this.codiceIPA) {
      this.saved.emit({ esito: "Nessun dato è stato modificato.", idTipoFornitore: this.tipologiaSelected.id, fornitore: this.fornitore });
      this.loadedSave = true;
    } else {
      if (this.isSeleziona) {
        if (this.fornitoreSelected === "A") {
          this.fornitore = Object.assign({}, this.fornitoreAnagrafica);
        } else if (this.fornitoreSelected === "F") {
          this.fornitore.denominazioneFornitore = this.fornitoreFattura.denominazioneFornitore;
          this.fornitore.codiceFiscaleFornitore = this.fornitoreFattura.codiceFiscaleFornitore;
          this.fornitore.partitaIvaFornitore = this.fornitoreFattura.partitaIvaFornitore;
        }
      } else {
        if (!this.fornitore) {
          this.fornitore = new FornitoreFormDTO();
        }
        this.fornitore.idTipoSoggetto = this.tipologiaSelected.id;
        if (this.tipologiaSelected.id == Constants.ID_TIPO_FORNITORE_PERSONA_FISICA) {
          this.fornitore.cognomeFornitore = this.cognome && this.cognome.length ? this.cognome.trim() : null;
          this.fornitore.nomeFornitore = this.nome && this.nome.length ? this.nome.trim() : null;
          this.fornitore.codiceFiscaleFornitore = this.codiceFiscale;
        } else {
          this.fornitore.denominazioneFornitore = this.denominazione && this.denominazione.length ? this.denominazione.trim() : null;
          this.fornitore.cfAutoGenerato = this.isSenzaCFoEstero ? "S" : "N";
          if (this.isSenzaCFoEstero) {
            this.fornitore.idNazione = this.nazioneSelected ? this.nazioneSelected.idNazione : null;
            this.fornitore.altroCodice = this.codIdentificativo;
            if (this.isEu(this.nazioneSelected.idNazione)) {
              this.fornitore.idAttivitaAteco = this.idAttivitaAtecoSelected;
            }
            if (!(!this.isNuovo && this.fornitore.codiceFiscaleFornitore && this.fornitore.codiceFiscaleFornitore.indexOf('PBAN') > -1 && this.codiceFiscale === "N.D.")) {
              this.fornitore.codiceFiscaleFornitore = this.codiceFiscale;
            }
          } else {
            this.fornitore.codiceFiscaleFornitore = this.codiceFiscale;
            this.fornitore.partitaIvaFornitore = this.partitaIva;
          }
          if (this.isSeleziona || this.tipologiaSelected.id == Constants.ID_TIPO_FORNITORE_PERSONA_GIURIDICA) {
            this.fornitore.flagPubblicoPrivato = +this.formaGiuridica;
            this.fornitore.idFormaGiuridica = this.tipologiaFormaGiuridicaSelected ? this.tipologiaFormaGiuridicaSelected.id : null;
            if (this.formaGiuridica === "2") {
              this.fornitore.codUniIpa = this.codiceIPA;
            }
          }
        }
      }
      if (this.validate()) {
        let all = new Array<DocumentoAllegatoDTO>();
        let forn = new FornitoreFormDTO();
        forn = Object.assign({}, this.fornitore);
        if (forn.documentiAllegati) {
          forn.documentiAllegati.forEach(d => all.push(Object.assign({}, d)));
          all = all.map(q => {
            delete q['associato'];
            return q;
          });
        }
        forn.documentiAllegati = all;
        this.subscribers.salvaFornitore = this.fornitoreService.salvaFornitore(forn, this.user.idUtente, this.user.beneficiarioSelezionato.idBeneficiario).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.fornitore.idFornitore = data.id;
              this.disableCF();
              this.saved.emit({ esito: data, idTipoFornitore: this.tipologiaSelected.id, fornitore: this.fornitore });
            } else {
              this.showMessageError(data.messaggio);
            }
          }
          this.loadedSave = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore nel salvataggio.");
          this.loadedSave = true;
        });
      } else {
        this.loadedSave = true;
      }
    }
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedAteco || !this.loadedNazioni || !this.loadedTipologieFormaGiuridica || !this.loadedSave) {
      return true;
    }
    return false;
  }
}
