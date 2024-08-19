/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, SimpleChanges, OnInit, ViewChild, Input, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { DatePipe, DecimalPipe } from "@angular/common";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
//import * as EventEmitter from 'events';

@Component({
  selector: 'blx-data-table',
  templateUrl: './blx-data-table.component.html',
  styleUrls: ['./blx-data-table.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush,
  // animations: [
  //   trigger('detailExpand', [
  //     state('collapsed', style({ height: '0px', minHeight: '0' })),
  //     state('expanded', style({ height: '*' })),
  //     transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
  //   ]),
  // ],
})
export class BlxDataTableComponent implements OnInit {

  //@Input() columns: string[];
  //@Input() rows: any[];
  //@Input() nestedColumns2: string[];
  //@Input() nestedRows2: any[];
  tmpElementUnique: number = 0;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;  //  = new MatSort() // ...
  // @ViewChild('sort', { static: false }) set sort(sort: MatSort) {
  //   this.sort = sort;
  //   if (this.dataSource !== null && this.dataSource !== undefined) {
  //       this.dataSource.sort = this.sort;
  //   }
  // }
  //dataRequest: any;  // FiltroTrasferimento
  //dataResponse: any;
  //displayedColumns: string[] = ['tipologia', 'progetto', 'fornitore', 'datanum', 'stato', 'rendicontazione', 'validato', 'azioni'];
  @Input() displayedColumns: string[] = [];  // = ['data', 'causale', 'denominazionebeneficiario', 'importotrasferimento', 'codicetrasferimento', 'flagpubblicoprivato', 'azioni'];
  @Input() displayedHeaders: string[] = [];
  @Input() displayedFooters: string[] = [];
  @Input() nestedColumns: string[] = [];
  @Input() nestedHeaders: string[] = [];
  @Input() nestedFooters: string[] = [];
  @Input() displayedColumnsCustom: string[] = [];
  @Input() displayedHeadersCustom: string[] = [];
  @Input() displayedFootersCustom: string[] = [];
  @Input() nestedColumnsCustom: string[] = [];
  @Input() nestedHeadersCustom: string[] = [];
  @Input() nestedFootersCustom: string[] = [];
  @Input() dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  @Input() dataSourceId: string;
  @Input() ngClassFunction: Function;
  @Input() nestedField: string;
  @Input() nestedFieldId: string;
  @Input() ngClassNestedFunction: Function;
  //@Input() set dataSourceS (dataSource: MatTableDataSource<any>) {
  //  this.dataSource = dataSource;
  //};
  @Input() cwidth: string;
  @Input() nestedCwidth: string;
  @Input() enableSort: boolean;
  @Input() enableNestedSort: boolean;
  @Input() azioni: any; // { visualizza: true, modifica: true, elimina: true, custom: true, icon: { custom: 'info' }, tooltip { visualizza: '', modifica: '', elimina: '', custom: '' } }
  @Input() nestedColumnsCustomAzioni: string;
  @Input() enablePaginator: boolean;
  @Input() enableNestedPaginator: boolean;
  @Input() pageSize: number = 10;
  @Input() collapsed: boolean;
  //@Input() nestedCollapsed: boolean;
  @Input() expandedElement: any;
  @Input() expandedElementIndex: number;
  @Input() isDisabledInputFloatNumber: Function;
  @Input() isNestedDisabledInputFloatNumber: Function;
  @Input() isDisabledSelect: Function;
  @Input() isNestedDisabledSelect: Function;
  @Output() onSelectSelect: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedSelectSelect: EventEmitter<any> = new EventEmitter<any>();
  @Output() onRadioButtonChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedRadioButtonChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() isDisabledRadioButton: Function;
  @Input() isNestedDisabledRadioButton: Function;
  @Input() isRequiredRadioButton: Function;
  @Input() isNestedRequiredRadioButton: Function; // Todo: integrare
  @Output() onHeaderCheckboxChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedHeaderCheckboxChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() isCheckedHeaderCheckbox: Function;
  @Input() isDisabledHeaderCheckbox: Function;
  @Input() isNestedDisabledHeaderCheckbox: Function;
  @Output() onCheckboxChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedCheckboxChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() isCheckedCheckbox: Function;
  @Input() isDisabledCheckbox: Function;
  @Input() isNestedDisabledCheckbox: Function;
  //@Input() isRequiredCheckbox: Function; // Todo: da fare
  //@Input() isNestedRequiredCheckbox: Function; // Todo: da fare
  @Output() onVisualizza: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedVisualizza: EventEmitter<any> = new EventEmitter<any>();
  // @Output() onAssocia = new EventEmitter<any>();
  // @Output() onClona = new EventEmitter<any>();
  @Output() onModifica: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedModifica: EventEmitter<any> = new EventEmitter<any>();
  @Output() onElimina: EventEmitter<any> = new EventEmitter<any>();
  @Output() onPersonOff: EventEmitter<any> = new EventEmitter<any>();
  @Output() onCustom: EventEmitter<any> = new EventEmitter<any>();
  @Output() onCustom2: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedCustom: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedCustom2: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedElimina: EventEmitter<any> = new EventEmitter<any>();
  @Output() onSlide: EventEmitter<any> = new EventEmitter<any>();
  @Output() onNestedSlides: EventEmitter<boolean[]> = new EventEmitter<boolean[]>();

  @Input() slideKey: string;
  @Input() slides: boolean[];
  //@Output() slidesChange:EventEmitter<boolean[]> = new EventEmitter<boolean[]>();
  @Input() nestedSlideKey: string;
  @Input() nestedSlides: boolean[];

  defaultValues: any = {
    input: {
      inputNumberType: 'float',
      autocomplete: 'off',
      pattern: '([0-9]+[\.])*[0-9]*([,][0-9]+)?',
      placeholder: undefined,
      disabled: false
    },
    tooltip: {
      visualizza: 'Visualizza',
      // associa: 'Associa',
      // clona: 'Clona',
      modifica: 'Modifica',
      elimina: 'Elimina',
      custom: 'Info',
      slideTrue: 'Disabilita',
      slideTrueFields: [],
      slideTrueFieldTypes: [],
      slideFalse: 'Abilita',
      slideFalseFields: [],
      slideFalseFieldTypes: []
    }
  }

  //dataSource: MatTableDataSource<any>;  // dataSourceS

  constructor(
    private cd: ChangeDetectorRef,
  ) { }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.dataSource) {
      if (changes.dataSource.currentValue) {
        this.updateDataSourceComponents();
      } else {
        //
      }

    }
  }

  ngOnInit() {
    this.log();
  }

  //ngAfterViewInit() {
  //this.updateDataSource(); // Required  // <blx-data-table *ngIf="!(!dataSource || !dataSource.data)">  // <table *ngIf="">
  //}

  log() {
    console.log('displayedColumns', this.displayedColumns);
    console.log('displayedHeaders', this.displayedHeaders);
    console.log('displayedFooters', this.displayedFooters);
    console.log('dataSource', this.dataSource);
  }

  updateDataSourceComponents() {
    if (this.dataSource) {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort; // ...
    }
    if (this.sort) {
      this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    }
  }

  toggleRow(element: any, elementIndex: number) {
    if (this.collapsed) {
      //console.log('toggleRow - i', elementIndex);
      //console.log('toggleRow', element);
      element[this.nestedField] && (element[this.nestedField] as MatTableDataSource<any>).data.length ? (this.expandedElementIndex = this.expandedElementIndex === elementIndex ? null : elementIndex) : null;
      if (this.expandedElementIndex) {
        //this.expandedElement = '';
      }
      // console.log('toggleRow - this.expandedElementIndex', this.expandedElementIndex);
      // console.log('toggleRow - this.expandedElement', this.expandedElement);
      this.cd.detectChanges();
      //this.innerTables.forEach((table, index) => (table.dataSource as MatTableDataSource<Address>).sort = this.innerSort.toArray()[index]);
    }
  }

  ngClassFunctionRow(element: any, elementIndex: number) {
    let ret: any;
    if (this.ngClassFunction) {
      ret = this.ngClassFunction(element, elementIndex);
    }
    return ret;
  }

  applyFilter(filterValue: string) {
    //this.innerTables.forEach((table, index) => (table.dataSource as MatTableDataSource<Address>).filter = filterValue.trim().toLowerCase());
  }

  isDisabledRowInputFloatNumber(row: any, column?: string, elementIndex?: number) {
    //console.log('isDisabledRowInputFloatNumber', row , column, elementIndex);
    let ret: any;
    if (this.isDisabledInputFloatNumber) {
      ret = this.isDisabledInputFloatNumber(row, column);
    }
    return ret;
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return option && value && (option.codice === value.codice);
  }

  isDisabledRowSelect(row: any, column?: string, elementIndex?: number) {
    let ret: any;
    if (this.isDisabledSelect) {
      ret = this.isDisabledSelect(row, column);
    }
    return ret;
  }

  // isNestedDisabledRowSelect(row: any, column?: string, elementIndex?: number) {
  //   let ret: any;
  //   if (this.isNestedDisabledSelect) {
  //     ret = this.isNestedZDisabledSelect(row, column);
  //   }
  //   return ret;
  // }

  onRowSelectSelect(row: any, column: string, elementIndex: number) {
    console.log('onRowSelectSelect', { row: row, column: column });
    this.onSelectSelect.emit({ row: row, column: column });
  }

  onNestedRowSelectSelect(e: any) {
    console.log('onNestedRowSelectSelect', e);
    this.onNestedSelectSelect.emit(e);
  }

  onRowRadioButtonChange(row: any, column: string, elementIndex: number) {
    this.onRadioButtonChange.emit(row);
  }

  onNestedRowRadioButtonChange(row: any) {
    this.onNestedRadioButtonChange.emit(row);
  }

  isDisabledRowRadioButton(row: any, column: string, elementIndex: number) {
    console.log('isDisabledRowRadioButton', row, column, elementIndex);
    let ret: any;
    if (this.isDisabledRadioButton) {
      ret = this.isDisabledRadioButton(row, elementIndex);
    }
    return ret;
  }

  // isNestedDisabledRowRadioButton(element: any, column: string, elementIndex: number) {
  //   let ret: any;
  //   if (this.isNestedDisabledRadioButton) {
  //     ret = this.isNestedDisabledRadioButton(element, elementIndex);
  //   }
  //   return ret;
  // }

  isRequiredRowRadioButton(element: any, column: string, elementIndex: number) {
    let ret: any;
    if (this.isRequiredRadioButton) {
      ret = this.isRequiredRadioButton(element, elementIndex);
    }
    return ret;
  }

  // isNestedRequiredRowRadioButton(element: any, column: string, elementIndex: number) {
  //   let ret: any;
  //   if (this.isNestedRequiredRadioButton) {
  //     ret = this.isNestedRequiredRadioButton(element, elementIndex);
  //   }
  //   return ret;
  // }

  onHeaderRowCheckboxChange(e: any, row: any, column: string, elementIndex: number) {
    if (e) {
      if (row === undefined) {  // && row[column] // row = undefined -> header
        if (e.checked != undefined) {
          if (e.checked === true) {
            let ret: boolean = true;
            this.dataSource.filteredData.forEach((row: any, i: number) => {
              if (this.azioni && this.azioni.customCheckbox && this.azioni.customCheckbox.defaultFieldBindUpdate) {
                if (row[this.azioni.customCheckbox.defaultFieldUpdate]) {
                  row[this.azioni.customCheckbox.defaultFieldUpdate] = ret;
                }
                if (row[this.azioni.customCheckbox.defaultFieldBindUpdate]) {
                  row[this.azioni.customCheckbox.defaultFieldBindUpdate] = { codice: ret, descrizione: row[this.azioni.customCheckbox.defaultFieldBindUpdate]['descrizione'] };
                }
              }
            });
          }
        }
      }
    }
    this.onHeaderCheckboxChange.emit({ event: e, row: row, column: column });
  }

  onNestedHeaderRowCheckboxChange(row: any) {
    this.onNestedHeaderCheckboxChange.emit(row);
  }

  isCheckedHeaderRowCheckbox(row: any, column?: string, elementIndex?: number, value?: CodiceDescrizione) { // row = undefined
    let ret: any;
    if (this.isCheckedHeaderCheckbox) {
      ret = this.isCheckedHeaderCheckbox(row, column);
    } else {
      //let ic: number = this.displayedColumns.findIndex(x => x === column);
      let ic: number = elementIndex;
      if (elementIndex !== -1) {
        if (this.displayedColumnsCustom[ic] == 'checkbox') {
          if (this.azioni && this.azioni.customCheckbox && this.azioni.customCheckbox.defaultValues) {
            if (this.dataSource && this.dataSource.filteredData && this.dataSource.filteredData.length) {
              let findedFalse: boolean = false;
              this.dataSource.filteredData.forEach((row: any, i: number) => {
                ret = this.compareWithCodiceDescrizione(row[column], value);
                if (ret !== true) {
                  findedFalse = true;
                }
              });
              ret = !findedFalse;
            }
          }
        }
      }
      //if (this.azioni && this.azioni.customCheckbox && this.azioni.customCheckbox.defaultFiledUpdate) {
      //  row[this.azioni.customCheckbox.defaultFiledUpdate] = ret;
      //}
    }
    return ret;
  }

  isDisabledHeaderRowCheckbox(row: any, column?: string, elementIndex?: number) {
    let ret: any;
    if (this.isDisabledHeaderCheckbox) {
      ret = this.isDisabledHeaderCheckbox(row, column);
    } else {

    }
    return ret;
  }

  // isNestedRequiredRowCheckbox(element: any, column: string, elementIndex: number) {
  //   let ret: any;
  //   if (this.isNestedRequiredCheckbox) {
  //     ret = this.isNestedRequiredCheckbox(element, elementIndex);
  //   }
  //   return ret;
  // }

  onRowCheckboxChange(e: any, row: any, column: string, elementIndex: number) {
    if (e) {
      if (row) {  // && row[column]
        if (e.checked != undefined) {
          let ret: boolean = e.checked;
          if (this.azioni && this.azioni.customCheckbox && this.azioni.customCheckbox.defaultFieldBindUpdate) {
            if (row[this.azioni.customCheckbox.defaultFieldBindUpdate]) {
              row[this.azioni.customCheckbox.defaultFieldBindUpdate] = { codice: ret, descrizione: row[this.azioni.customCheckbox.defaultFieldBindUpdate]['descrizione'] };
            }
          }
        }
      }
    }
    this.onCheckboxChange.emit({ event: e, row: row, column: column });
  }

  onNestedRowCheckboxChange(row: any) {
    this.onNestedCheckboxChange.emit(row);
  }

  isCheckedRowCheckbox(row: any, column?: string, elementIndex?: number, value?: CodiceDescrizione) {
    let ret: any;
    if (this.isCheckedCheckbox) {
      ret = this.isCheckedCheckbox(row, column);
    } else {
      ret = this.compareWithCodiceDescrizione(row[column], value);
      //if (this.azioni && this.azioni.customCheckbox && this.azioni.customCheckbox.defaultFiledUpdate) {
      //  row[this.azioni.customCheckbox.defaultFiledUpdate] = ret;
      //}
    }
    return ret;
  }

  isDisabledRowCheckbox(row: any, column?: string, elementIndex?: number) {
    let ret: any;
    if (this.isDisabledCheckbox) {
      ret = this.isDisabledCheckbox(row, column);
    }
    return ret;
  }

  // isNestedRequiredRowCheckbox(element: any, column: string, elementIndex: number) {
  //   let ret: any;
  //   if (this.isNestedRequiredCheckbox) {
  //     ret = this.isNestedRequiredCheckbox(element, elementIndex);
  //   }
  //   return ret;
  // }

  onVisualizzaRowBtn(row: any) {
    this.onVisualizza.emit(row);
  }

  // onAssociaRowBtn(row: any) {
  //   this.onAssocia.emit(row);
  // }

  // onClonaRowBtn(row: any) {
  //   this.onClona.emit(row);
  // }

  onModificaRowBtn(row: any) {
    this.onModifica.emit(row);
  }

  onEliminaRowBtn(row: any) {
    this.onElimina.emit(row);
  }

  onPersonOffBtn(row: any) {
    this.onPersonOff.emit(row);
  }

  onCustomRowBtn(row: any) {
    this.onCustom.emit(row);
  }

  onCustom2RowBtn(row: any) {
    this.onCustom2.emit(row);
  }

  onNestedCustomRowBtn(row: any) {
    this.onNestedCustom.emit(row);
  }

  onNestedCustom2RowBtn(row: any) {
    this.onNestedCustom2.emit(row);
  }

  onNestedVisualizzaRowBtn(row: any) {
    this.onNestedVisualizza.emit(row);
  }

  onSlideRowBtn(row: any, slide: boolean) {
    this.onSlide.emit({ soggetto: row, slide: !slide });
  }

  onNestedSlideRowBtn(row: any) {
    this.onNestedSlides.emit(row);
  }

  // onNestedAssociaRowBtn(row: any) {
  //   this.onNestedAssocia.emit(row);
  // }

  // onNestedClonaRowBtn(row: any) {
  //   this.onNestedClona.emit(row);
  // }

  onNestedModificaRowBtn(row: any) {
    this.onNestedModifica.emit(row);
  }

  onNestedEliminaRowBtn(row: any) {
    this.onNestedElimina.emit(row);
  }

  sprintfTooltip(str: string, keyTypes: string[], keys: string[], row: any) {
    let sprintf: any = (str, ...argv) => !argv.length ? str : sprintf(str = str.replace(sprintf.token || "$", argv.shift()), ...argv);
    sprintf.token = "$";
    if (keys) {
      let vals = keys.map((val, i: number) => {
        let s = row[val];
        if (keyTypes && keyTypes[i]) {
          if (keyTypes[i] == 'date') {
            s = new DatePipe('it').transform(s, 'dd/MM/yyyy');
          } else if (keyTypes[i] == 'number') {
            s = new DecimalPipe('it').transform(s, '1.2-2');
          }
        }
        return s;
      });
      str = sprintf(str, ...vals);
    }
    return str;
  }

  elementUnique = () => {
    return ++this.tmpElementUnique;
  }

}
