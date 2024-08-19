/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnChanges, SimpleChanges, SimpleChange, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { CodiceDescrizione } from "../../commons/models/codice-descrizione";

import { NgForm, FormControl, FormGroup, FormBuilder } from "@angular/forms";
import { CdkVirtualScrollViewport } from "@angular/cdk/scrolling";
import { MatOptionSelectionChange } from "@angular/material/core";
import { MatSelect } from "@angular/material/select";

import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
//import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AutoCheckChangeInit, _AutoCheckChangeInit } from 'src/app/shared/decorator/auto-check-change-init';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "src/app/core/commons/dto/user-info";
import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';
import { TrasferimentiService } from "../../../trasferimenti//services/trasferimenti.service";
import { SoggettoTrasferimento } from "../../../erogazione/commons/models/soggetto-trasferimento";

@Component({
  selector: 'select-beneficiario',
  templateUrl: './select-beneficiario.component.html',
  styleUrls: ['./select-beneficiario.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
//@AutoUnsubscribe({ objectName: 'subscribers' })
@AutoCheckChangeInit({ objectName: 'subscribers' })
export class SelectBeneficiarioComponent implements OnInit {

  @Input() labels: CodiceDescrizione[];
  @Input() buttons: CodiceDescrizione[];
  @Input() class: string;
  @Input() required: boolean;
  @Input() disabled: boolean;
  @Output() onBtnClick = new EventEmitter<CodiceDescrizione>();

  @Input() beneficiari: Array<BeneficiarioSec>;
  @Input() beneficiario: BeneficiarioSec;
  @Output() beneficiarioChange = new EventEmitter<BeneficiarioSec>();

  isInitParams: boolean;
  //initParams: Array<{[key: string]: boolean}> = [];
  loadedBeneficiari: boolean;
  user: UserInfoSec;
  //beneficiari: Array<BeneficiarioSec>;
  //beneficiario: BeneficiarioSec;
  beneficiarioSelected: FormControl = new FormControl();
  beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
  filteredOptions: Observable<BeneficiarioSec[]>;

  subscribers: any = {};

  constructor(
    //private _ACCI: _AutoCheckChangeInit, // _AutoCheckChangeInit without static
    private fb: FormBuilder,
    private userService: UserService,
    private trasferimentiService: TrasferimentiService,
    private handleExceptionService: HandleExceptionService,
  ) {
    console.log('select-beneficiario', 'constructor');
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('ngOnChanges', changes);
    //this.checkChangeInit(changes);
    if (!this.isInitParams) {
      if (_AutoCheckChangeInit.getChange(this.constructor, 'beneficiari')) {
        //this.setupSelectBeneficiario();
        this.isInitParams = true;
        console.log('ngOnChanges - changes.beneficiari', changes.beneficiari);
      }
    }
    if (_AutoCheckChangeInit.isChangeInit(changes.beneficiario)) {
      console.log('ngOnChanges - this.beneficiario', this.beneficiario);
      _AutoCheckChangeInit.setChange(this.constructor, 'beneficiario');
      this.beneficiarioSelected.setValue(this.beneficiario);
      this.beneficiarioGroup.controls['beneficiarioControl'].setValue(this.beneficiario);
      if (this.disabled) {
        this.beneficiarioGroup.controls['beneficiarioControl'].disable();
        // this.beneficiarioGroup = this.fb.group({
        //   beneficiarioControl: new FormControl({ value: this.beneficiario, disabled: this.disabled })
        // });
      }
      console.log('ngOnChanges - changes.beneficiario', changes.beneficiario);
    }
  }

  // checkChangeInit(changes: SimpleChanges) {
  //   let array: Array<string> = Object.keys(changes);
  //   array.forEach(element => {
  //     console.log('ngOnChanges - k', element);
  //     console.log('ngOnChanges - v', changes[element]);
  //     if (this.isChangeInit(changes[element])) {
  //       this.setChange(element);
  //     }
  //   });
  // }

  // isChangeInit(change: SimpleChange) {
  //   return change && !this.isChangeUndefined(change);
  // }

  // isChangeUndefined(change: SimpleChange) {
  //   return !change || (change.currentValue === undefined && change.previousValue === undefined);
  // }

  // setChange(element: string) {
  //   this.initParams[element] = true;
  // }

  // getChange(element: string): SimpleChange {
  //   return this.initParams[element];
  // }

  ngOnInit() {
    //setInterval(() => {console.log('ngOn - this.initParams', this.isInitParams, this.initParams);}, 2000);
    console.log('select-beneficiario', 'ngOnInit');
    this.setupSelectBeneficiario();
    //this.setupSelectBeneficiarioNgIf();  // Test - *ngIf
    // Test - FullLoad - start
    if (false)
    {
      this.loadedBeneficiari = false;
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          //this.loadedBeneficiari = true;
          if (this.user.idIride) {
            this.loadDataFullLoad();
          }
        }
      });
    }
    // Test - FullLoad - end
  }

  setupSelectBeneficiario() {
    this.filteredOptions = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
      .pipe(
        startWith(''),
        map(value => {
          return typeof value === 'string' || value == null ? value : value.denominazione
        }),
        map(name => {
          let ret = name ? this._filter(name) : this.beneficiari
          //console.log('ret', ret);
          return ret;
        })
      );
  }

  setupSelectBeneficiarioNgIf() { // Test - *ngIf
    console.log('this.beneficiari', this.beneficiari);
    this.loadedBeneficiari = false;
    if (this.beneficiari) {
      this.beneficiarioSelected.setValue(this.beneficiario);
      this.beneficiarioGroup.controls['beneficiarioControl'].setValue(this.beneficiario);
      if (this.beneficiari.length == 1) {
        this.beneficiario = this.beneficiari[0];
        this.selectBeneficiario(this.beneficiario);
      } else {
        this.filteredOptions = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => {
              return typeof value === 'string' || value == null ? value : value.denominazione
            }),
            map(name => {
              let ret = name ? this._filter(name) : this.beneficiari
              //console.log('ret', ret);
              return ret;
            })
          );
      }
    }
    console.log('this.beneficiario', this.beneficiario);
    this.loadedBeneficiari = true;
  }

  loadDataFullLoad = () => {  // Test - FullLoad
    //LOAD SOGGETTI TRASFERIMENTO
    this.loadedBeneficiari = false;
    this.subscribers.soggettiTrasferimento = this.trasferimentiService.getSoggettiTrasferimento().subscribe((res: SoggettoTrasferimento[]) => {
      if (res) {
        //this.beneficiari = res;
        //this.beneficiarioSelected = this.beneficiari[0];
        this.user.beneficiari = res.map((res) => {
          return {
            denominazione: res.denominazioneBeneficiario,
            codiceFiscale: res.cfBeneficiario,
            idBeneficiario: res.idSoggettoBeneficiario
          };
        });
        this.beneficiari = this.user.beneficiari;
        this.beneficiario = this.beneficiari[2];
        this.beneficiarioSelected.setValue(this.beneficiario);
        console.log('this.beneficiari', this.beneficiari);
        console.log('this.beneficiario', this.beneficiario);
        console.log('this.beneficiarioSelected', this.beneficiarioSelected);

        this.setupSelectBeneficiarioFullLoad();
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBeneficiari = true;
    });
  }

  setupSelectBeneficiarioFullLoad = () => {  // Test - FullLoad
    //if (this.activatedRoute.snapshot.paramMap.get('action') === 'cambia') {
      this.beneficiario = this.user.beneficiarioSelezionato;
      this.beneficiarioSelected.setValue(this.beneficiario);
      this.beneficiarioGroup.controls['beneficiarioControl'].setValue(this.beneficiario);
    //}

    if (this.user.beneficiari) {
      this.beneficiari = this.user.beneficiari;
      if (this.user.beneficiari.length == 1) {
        this.user.beneficiarioSelezionato = this.user.beneficiari[0];
        this.selectBeneficiario(this.user.beneficiarioSelezionato);
      } else {
        this.filteredOptions = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => {
              return typeof value === 'string' || value == null ? value : value.denominazione
            }),
            map(name => {
              let ret = name ? this._filter(name) : this.beneficiari
              //console.log('ret', ret);
              return ret;
            })
          );
      }
    }
  }

  onButtonsBtnClick(btn: CodiceDescrizione) {
    this.onBtnClick.emit(btn);
  }

  selectBeneficiario(beneficiario?: BeneficiarioSec) {
    if (!beneficiario) {
      this.beneficiario = this.beneficiarioGroup.controls['beneficiarioControl'].value;
      this.user.beneficiarioSelezionato = this.beneficiarioGroup.controls['beneficiarioControl'].value;
    }
  }

  displayFn(beneficiario: BeneficiarioSec): string {
    return (beneficiario && beneficiario.denominazione ? beneficiario.denominazione : '')
      //+ (beneficiario && beneficiario.denominazione && beneficiario.codiceFiscale ? ' - ' : '')
      //+ (beneficiario && beneficiario.codiceFiscale ? beneficiario.codiceFiscale : '');
  }

  private _filter(denominazione: string): BeneficiarioSec[] {
    const filterValue = denominazione.toLowerCase();
    return ((this.beneficiari)?this.beneficiari.filter(option => option.denominazione.toLowerCase().includes(filterValue) || option.codiceFiscale.toLowerCase().includes(filterValue)):undefined);
  }

  check() {
    setTimeout(() => {
      if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected.value !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
        this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
        this.beneficiarioSelected = new FormControl();
        this.beneficiarioChange.emit(this.beneficiarioSelected.value);
      }
    }, 200);
  }

  click(event: any) {
    this.beneficiarioSelected.setValue(event.option.value);
    this.beneficiarioChange.emit(this.beneficiarioSelected.value);
  }

  isLoading() { // Test - FullLoad
    if (!this.loadedBeneficiari) {
      return true;
    }
    return false;
  }
}