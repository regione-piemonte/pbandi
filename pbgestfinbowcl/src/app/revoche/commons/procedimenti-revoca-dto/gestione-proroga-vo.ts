/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/*
public class GestioneProrogaVO {
    private Long idProroga;
    private Long idStatoProroga;
    private String descStatoProroga;
    private Date dtRichiestaProroga;
    private Date dtEsitoRichiestaProroga;
    private BigDecimal numGiorniRichiestiProroga;
    private BigDecimal numGiorniApprovatiProroga;
    private String motivazioneProroga;
    private Date dtInserimentoProroga;
}
*/
export class GestioneProrogaVO {

  public idProroga: string;
  public idStatoProroga: string;
  public descStatoProroga: string;
  public dtRichiestaProroga: Date;
  public dtEsitoRichiestaProroga: Date;
  public numGiorniRichiestiProroga: string;
  public numGiorniApprovatiProroga: string;
  public motivazioneProroga: string;
  public dtInserimentoProroga: Date;

}
