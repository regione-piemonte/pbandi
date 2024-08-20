/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RevocaAmministrativaDTO {

  /* parte di dettaglio beneficiario
  public idSoggetto: string;
  public idProgetto: string;
  public progrSoggettoProgetto: string;

  public idModalitaAgevolazioneOrig: string;
  public descBreveModalitaAgevolazioneOrig: string;
  public descModalitaAgevolazioneOrig: string;
  public idModalitaAgevolazioneRif: string;
  public descBreveModalitaAgevolazioneRif: string;
  public descModalitaAgevolazioneRif: string;
  public statoAgevolazione: string;

  public totaleErogato: number;
  public totaleFinpiemonte: number;
  public totaleBanca: number;
  public importoFinpiemonte: number;
  public cessSoggettoTerzo: string;
  public revocaManBanca: string;
  public revocaAmministrativa: string;*/

  
  public esito: Boolean;
	public msg: String;
	public exc: String;

  public idProgetto: string; 
	public idRevoca: string;
	public idCausaleBlocco: string;
	public descCausaleBlocco: string;
	public idCateAnag: string;
	public descCateAnag: string;
	public dataGestione: Date;
	public dataStatoRevoca: Date;
	public descStatoRevoca: string;
	public idMotivoRevoca: string;
	public descMotivoRevoca: string;
	public impAmmesso: number;
	public impErogato: number;
	public impRecupero: number;
  public flagOrdineRecupero: string;
	public impRevoca: number;
	public impBando: number; 
	public numeroRevoca: number; 
	public recuperoQuotaCapitale: number; 
	public recuperoAgevolazione:number; 
  public dataNotificaProvRevoca: Date; 
  public tipoRevoca:string
  public statoProvRevoca: string; 
  public descCausa: string; 
  public dataProvedimentoRevoca: Date; 
  public quotaCapitale: number;
  public impAgevolazione: number; 
  public importoErogatoFinp: number; 
  public importoTotaleRevocato: number; 
  public importoConcesso: number; 
    /*public progetto: number,
    public idSoggetto: string,
    public idProgetto: string,
    public idModalitàAgevolazione: string,
    public modAgevolazione: string,
    public statoAgevolazione: string,
    //public totaleErogato: string,
    public totaleFinpiemonte: number,
    public totaleBanca: number,
    public cessioneSoggettoTerzo: string,
    public revocaMandatoBanca: string,
    public revocaAmministrativa: string,*/

}
