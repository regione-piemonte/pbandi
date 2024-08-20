/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SearchAcqProgVO{
    
    public idBandoLineaIntervent: number;  
    public numCampionamento: number;  
    public dataCampione: Date;
    public descCampionamento: string;
    public progetti: string;
    public  progettiConfermati: Array<number>;
}