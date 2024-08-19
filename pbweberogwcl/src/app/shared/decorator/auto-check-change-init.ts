/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SimpleChanges, SimpleChange } from '@angular/core';

export class _AutoCheckChangeInit {

  static isFunction(fn) { return typeof fn === 'function' };

  static getParamName(paramName: string): string { return '_' + this.constructor.name + '_' + paramName };

  static setParam(constructor: Function, paramName: string, paramValue: any) { return ((constructor.prototype[this.getParamName(paramName)] === undefined) ? constructor.prototype[this.getParamName(paramName)] = paramValue : console.warn('params ' + this.getParamName(paramName) + ' in AutoCheckChangeInit is not initalized')); }

  static getParam(constructor: Function, paramName: string) { return constructor.prototype[this.getParamName(paramName)]; }

  static setChange(constructor: Function, element: string) { return constructor.prototype[this.getParamName('initParams')][element] = true; }

  static getChange(constructor: Function, element: string): SimpleChange { return constructor.prototype[this.getParamName('initParams')][element]; }

  static getChanges(constructor: Function): SimpleChange { return constructor.prototype[this.getParamName('initParams')]; }

  static isChangeUndefined(change: SimpleChange) { return !change || (change.currentValue === undefined && change.previousValue === undefined); }

  static isChangeInit(change: SimpleChange) { return change && !this.isChangeUndefined(change); }

  static checkChangeInit(constructor: Function, changes: SimpleChanges) {
    let array: Array<string> = Object.keys(changes);
    array.forEach(element => {
      console.log('AutoCheckChangeInit - checkChangeInit - k', element);
      console.log('AutoCheckChangeInit - checkChangeInit - v', changes[element]);
      if (this.isChangeInit(changes[element])) {
        this.setChange(constructor, element);
      }
    });
    console.log('AutoCheckChangeInit - ngOnChanges - getChanges', this.getChanges(constructor));
  }

}

export function AutoCheckChangeInit({ auto = true, blackList = [], arrayName = '', objectName = '' } = {}) {

  //let _ACCI: _AutoCheckChangeInit = new _AutoCheckChangeInit(); // _AutoCheckChangeInit without static

  return (constructor: Function) => {
    const original = constructor.prototype.ngOnChanges;
    _AutoCheckChangeInit.setParam(constructor, 'counter', 0);
    _AutoCheckChangeInit.setParam(constructor, 'initParams', []);
    constructor.prototype.ngOnChanges = function () {
      console.log('AutoCheckChangeInit - this.constructor.name', this.constructor.name);
      console.log('AutoCheckChangeInit - ' + _AutoCheckChangeInit.getParamName('counter'), ++this[_AutoCheckChangeInit.getParamName('counter')]);
      console.log('AutoCheckChangeInit - arguments', arguments);
      _AutoCheckChangeInit.checkChangeInit(constructor, arguments[0]);
      _AutoCheckChangeInit.isFunction(original) && original.apply(this, arguments);
    };
  }

}