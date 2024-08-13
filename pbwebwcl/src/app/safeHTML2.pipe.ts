/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer} from '@angular/platform-browser';

@Pipe({name: 'safeHTML2'})
export class SafeHtmlPipe2 implements PipeTransform {
    constructor(private sanitized: DomSanitizer) { }
    transform(value: string) {
        return this.sanitized.bypassSecurityTrustHtml(value);
    }
}
