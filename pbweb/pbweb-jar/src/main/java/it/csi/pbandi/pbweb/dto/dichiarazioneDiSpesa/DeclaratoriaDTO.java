/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.util.ArrayList;

public class DeclaratoriaDTO {
  String collegiali;
  String contributiStataliComunitarie;
  String contributiStrutture;
  String contributiSuccessivi;
  String documentoUnico;
  String iva;
  String ritenutaIres;
  ArrayList<StataleComunitaria> stataleComunitaria;
  ArrayList<Strutture> strutture;

  public String getCollegiali() {
    return collegiali;
  }

  public void setCollegiali(String collegiali) {
    this.collegiali = collegiali;
  }

  public String getContributiStataliComunitarie() {
    return contributiStataliComunitarie;
  }

  public void setContributiStataliComunitarie(String contributiStataliComunitarie) {
    this.contributiStataliComunitarie = contributiStataliComunitarie;
  }

  public String getContributiStrutture() {
    return contributiStrutture;
  }

  public void setContributiStrutture(String contributiStrutture) {
    this.contributiStrutture = contributiStrutture;
  }

  public String getContributiSuccessivi() {
    return contributiSuccessivi;
  }

  public void setContributiSuccessivi(String contributiSuccessivi) {
    this.contributiSuccessivi = contributiSuccessivi;
  }

  public String getDocumentoUnico() {
    return documentoUnico;
  }

  public void setDocumentoUnico(String documentoUnico) {
    this.documentoUnico = documentoUnico;
  }

  public String getIva() {
    return iva;
  }

  public void setIva(String iva) {
    this.iva = iva;
  }

  public String getRitenutaIres() {
    return ritenutaIres;
  }

  public void setRitenutaIres(String ritenutaIres) {
    this.ritenutaIres = ritenutaIres;
  }

  public ArrayList<StataleComunitaria> getStataleComunitaria() {
    return stataleComunitaria;
  }

  public String getStataleComunitariaString(){
    StringBuilder result = new StringBuilder();
    if (stataleComunitaria == null || stataleComunitaria.isEmpty()) return "";

    for (StataleComunitaria s : stataleComunitaria) {
      result.append("Programma: ").append(s.getProgramma()).append(" - ").append("Struttura: ").append(s.getStruttura()).append("<br>");
    }
    return "<br>" + result;
  }

  public void setStataleComunitaria(ArrayList<StataleComunitaria> stataleComunitaria) {
    this.stataleComunitaria = stataleComunitaria;
  }

  public ArrayList<Strutture> getStrutture() {
    return strutture;
  }

  public String getStruttureString() {
    StringBuilder result = new StringBuilder();
    if (strutture == null || strutture.isEmpty()) return "";
    for (Strutture s : strutture) {
      result.append("Direzione: ").append(s.getDirezione()).append(" - ").append("Settore: ").append(s.getSettore()).append(" - ").append("Normativa: ").append(s.getNormativa()).append("<br>");
    }
    return "<br>" + result;
  }
  public void setStrutture(ArrayList<Strutture> strutture) {
    this.strutture = strutture;
  }

  private static class StataleComunitaria {
    String programma;
    String struttura;

    public StataleComunitaria(String programma, String struttura) {
      this.programma = programma;
      this.struttura = struttura;
    }

    public StataleComunitaria() {
    }

    public String getProgramma() {
      return programma;
    }

    public void setProgramma(String programma) {
      this.programma = programma;
    }

    public String getStruttura() {
      return struttura;
    }

    public void setStruttura(String struttura) {
      this.struttura = struttura;
    }

  }

  private static class Strutture {
    String direzione;
    String normativa;
    String settore;

    public Strutture(String direzione, String normativa, String settore) {
      this.direzione = direzione;
      this.normativa = normativa;
      this.settore = settore;
    }

    public Strutture() {
    }

    public String getDirezione() {
      return direzione;
    }

    public void setDirezione(String direzione) {
      this.direzione = direzione;
    }

    public String getNormativa() {
      return normativa;
    }

    public void setNormativa(String normativa) {
      this.normativa = normativa;
    }

    public String getSettore() {
      return settore;
    }

    public void setSettore(String settore) {
      this.settore = settore;
    }
  }
}
