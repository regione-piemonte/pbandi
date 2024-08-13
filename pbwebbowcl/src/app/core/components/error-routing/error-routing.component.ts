/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConfigService } from '../../services/config.service';

@Component({
  selector: 'app-error-routing',
  templateUrl: './error-routing.component.html',
  styleUrls: ['./error-routing.component.scss']
})
export class ErrorRoutingComponent implements OnInit {

  constructor(
    private configService: ConfigService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  goToHome() {
    let url = this.configService.getPbworkspaceURL() + "/#/homeSceltaProfilo";
    this.router.navigate(["/"]).then(result => { window.location.href = url; });
  }

}
