/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.amministrativoContabile;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class Beneficiario   {
  private Integer idBeneficiario = null;
  private String ragioneSociale = null;
  private String cognome = null;
  private String nome = null;
  private Integer nazione = null;
  private String provincia = null;
  private String localita = null;
  private String indirizzo = null;
  private String cap = null;
  private String codiceFiscale = null;
  private String partitaIVA = null;
  private String email = null;
  private String pec = null;
  private String telefono = null;

  /**
   **/
  
  @JsonProperty("idBeneficiario")
  @NotNull
  public Integer getIdBeneficiario() {
    return idBeneficiario;
  }
  public void setIdBeneficiario(Integer idBeneficiario) {
    this.idBeneficiario = idBeneficiario;
  }

  /**
   **/
  
  @JsonProperty("ragioneSociale")
  @NotNull
  @Size(max=60)
  public String getRagioneSociale() {
    return ragioneSociale;
  }
  public void setRagioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
  }

  /**
   **/
  
  @JsonProperty("cognome")
  @NotNull
  @Size(max=40)
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @JsonProperty("nome")
  @NotNull
  @Size(max=20)
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @JsonProperty("nazione")
  @NotNull
  public Integer getNazione() {
    return nazione;
  }
  public void setNazione(Integer nazione) {
    this.nazione = nazione;
  }

  /**
   **/
  
  @JsonProperty("provincia")
  @NotNull
  @Size(max=10)
  public String getProvincia() {
    return provincia;
  }
  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  /**
   **/
  
  @JsonProperty("localita")
  @NotNull
  @Size(max=40)
  public String getLocalita() {
    return localita;
  }
  public void setLocalita(String localita) {
    this.localita = localita;
  }

  /**
   **/
  
  @JsonProperty("indirizzo")
  @NotNull
  @Size(max=40)
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  @JsonProperty("cap")
  @NotNull
  @Size(max=10)
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscale")
  @NotNull
  @Size(max=20)
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   **/
  
  @JsonProperty("partitaIVA")
  @NotNull
  @Size(max=20)
  public String getPartitaIVA() {
    return partitaIVA;
  }
  public void setPartitaIVA(String partitaIVA) {
    this.partitaIVA = partitaIVA;
  }

  /**
   **/
  
  @JsonProperty("email")
  @NotNull
  @Size(max=128)
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   **/
  
  @JsonProperty("pec")
  @NotNull
  @Size(max=128)
  public String getPec() {
    return pec;
  }
  public void setPec(String pec) {
    this.pec = pec;
  }

  /**
   **/
  
  @JsonProperty("telefono")
  @NotNull
  @Size(max=24)
  public String getTelefono() {
    return telefono;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Beneficiario beneficiario = (Beneficiario) o;
    return Objects.equals(idBeneficiario, beneficiario.idBeneficiario) &&
        Objects.equals(ragioneSociale, beneficiario.ragioneSociale) &&
        Objects.equals(cognome, beneficiario.cognome) &&
        Objects.equals(nome, beneficiario.nome) &&
        Objects.equals(nazione, beneficiario.nazione) &&
        Objects.equals(provincia, beneficiario.provincia) &&
        Objects.equals(localita, beneficiario.localita) &&
        Objects.equals(indirizzo, beneficiario.indirizzo) &&
        Objects.equals(cap, beneficiario.cap) &&
        Objects.equals(codiceFiscale, beneficiario.codiceFiscale) &&
        Objects.equals(partitaIVA, beneficiario.partitaIVA) &&
        Objects.equals(email, beneficiario.email) &&
        Objects.equals(pec, beneficiario.pec) &&
        Objects.equals(telefono, beneficiario.telefono);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idBeneficiario, ragioneSociale, cognome, nome, nazione, provincia, localita, indirizzo, cap, codiceFiscale, partitaIVA, email, pec, telefono);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Beneficiario {\n");
    
    sb.append("    idBeneficiario: ").append(toIndentedString(idBeneficiario)).append("\n");
    sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    localita: ").append(toIndentedString(localita)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    partitaIVA: ").append(toIndentedString(partitaIVA)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
