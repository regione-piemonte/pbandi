/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer} from '@angular/platform-browser';

@Pipe({name: 'cutDecimal'})
export class CutDecimalPipe implements PipeTransform {
    constructor() { }
    transform(value: number) {
        return Math.round(value/1024*100)/100;
    }
}
