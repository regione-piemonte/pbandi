/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto.utils;


/**
 * Classe di utilità che rappresenta un parametro un metodo. Viene utilizzato dai metodi di logging per ricevere l'elenco dei parametri di un metodo quando si
 * verifica un errore e bisogna stamparli.
 * @since 1.0
 */
public class LogParameter implements ILoggable
{
  /**
   * Serial Version UID
   */
  private static final long serialVersionUID = -4835724540697548318L;
  /**
   * Nome del parametro
   */
  private String            nome;
  /**
   * Valore del parametro
   */
  private Object            valore;

  public LogParameter(String nome, Object valore)
  {
    this.nome = nome;
    this.valore = valore;
  }

  public LogParameter(String nome, long valore)
  {
    this.nome = nome;
    this.valore = new Long(valore);
  }

  public LogParameter(String nome, int valore)
  {
    this.nome = nome;
    this.valore = new Long(valore);
  }

  public LogParameter(String nome, boolean valore)
  {
    this.nome = nome;
    this.valore = new Boolean(valore);
  }

  public LogParameter(String nome, char valore)
  {
    this.nome = nome;
    this.valore = String.valueOf(valore);
  }

  public LogParameter(String nome, double valore)
  {
    this.nome = nome;
    this.valore = new Double(valore);
  }

  public LogParameter(String nome, float valore)
  {
    this.nome = nome;
    this.valore = new Float(valore);
  }

  public String getNome()
  {
    return nome;
  }

  public Object getValore()
  {
    return valore;
  }
}
