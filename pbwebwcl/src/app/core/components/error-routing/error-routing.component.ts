import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../../services/config.service';

@Component({
  selector: 'app-error-routing',
  templateUrl: './error-routing.component.html',
  styleUrls: ['./error-routing.component.scss']
})
export class ErrorRoutingComponent implements OnInit {

  message: string

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute
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
    window.location.assign(this.configService.getPbworkspaceURL() + "/#/homeSceltaProfilo");
  }

}
