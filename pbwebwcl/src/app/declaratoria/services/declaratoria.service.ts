import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable()
export class DeclaratoriaService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  
}
