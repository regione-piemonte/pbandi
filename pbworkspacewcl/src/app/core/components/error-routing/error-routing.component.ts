/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-error-routing',
  templateUrl: './error-routing.component.html',
  styleUrls: ['./error-routing.component.scss']
})
export class ErrorRoutingComponent implements OnInit {

  message: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.message = params.message;
      if (!this.message) {
        this.message = "URL non valida";
      }
    });
  }

  goToHome() {
    this.router.navigateByUrl("homeSceltaProfilo");
  }

}
