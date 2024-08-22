/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class FileInfo   {
  private Integer idDocumentoIndex = null;
  private String nomeFile = null;

  /**
   **/
  
  @JsonProperty("idDocumentoIndex")
  public Integer getIdDocumentoIndex() {
    return idDocumentoIndex;
  }
  public void setIdDocumentoIndex(Integer idDocumentoIndex) {
    this.idDocumentoIndex = idDocumentoIndex;
  }

  /**
   **/
  
  @JsonProperty("nomeFile")
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileInfo fileInfo = (FileInfo) o;
    return Objects.equals(idDocumentoIndex, fileInfo.idDocumentoIndex) &&
        Objects.equals(nomeFile, fileInfo.nomeFile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDocumentoIndex, nomeFile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileInfo {\n");
    
    sb.append("    idDocumentoIndex: ").append(toIndentedString(idDocumentoIndex)).append("\n");
    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
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
