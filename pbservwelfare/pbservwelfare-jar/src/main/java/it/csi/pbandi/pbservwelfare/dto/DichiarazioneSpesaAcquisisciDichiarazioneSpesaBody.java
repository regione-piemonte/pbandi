/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.File;
import javax.validation.constraints.*;

public class DichiarazioneSpesaAcquisisciDichiarazioneSpesaBody   {
  private String dati = null;
  private File file = null;

  /**
   **/
  
  @JsonProperty("dati")
  @NotNull
  public String getDati() {
    return dati;
  }
  public void setDati(String dati) {
    this.dati = dati;
  }

  /**
   **/
  
  @JsonProperty("file")
  @NotNull
  public File getFile() {
    return file;
  }
  public void setFile(File file) {
    this.file = file;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DichiarazioneSpesaAcquisisciDichiarazioneSpesaBody dichiarazioneSpesaAcquisisciDichiarazioneSpesaBody = (DichiarazioneSpesaAcquisisciDichiarazioneSpesaBody) o;
    return Objects.equals(dati, dichiarazioneSpesaAcquisisciDichiarazioneSpesaBody.dati) &&
        Objects.equals(file, dichiarazioneSpesaAcquisisciDichiarazioneSpesaBody.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dati, file);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DichiarazioneSpesaAcquisisciDichiarazioneSpesaBody {\n");
    
    sb.append("    dati: ").append(toIndentedString(dati)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
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
