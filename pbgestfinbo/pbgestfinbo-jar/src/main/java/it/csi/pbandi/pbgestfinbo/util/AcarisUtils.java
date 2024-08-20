/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.util;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdFormaDocumentariaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryNameType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryableObjectType;

public class AcarisUtils {

	
	public static String pseudoCipher(String in)
    {
        if(StringUtils.isBlank(in))
        {
            return in;
        }
        in = StringUtils.rightPad(in, 128);
        StringBuilder sb = new StringBuilder(256);
        int mask = 85;
        for(int i = 0; i < in.length(); i++)
        {
            int ch = in.charAt(i);
            if(ch > 255)
            {
                throw new RuntimeException("unexpected char");
            }
            ch ^= mask;
            mask = mask << 1 & 0xff;
            if(mask % 4 == 0)
            {
                mask |= 1;
            }
            sb.append(String.format("%02x", new Object[] {
                Integer.valueOf(ch)
            }));
        }

        return sb.toString();
    }
	
	    public static String pseudoDecipher(String in)
	    {
	        if(StringUtils.isEmpty(in) || StringUtils.length(in) != 256)
	        {
	            return "";
	        }
	        StringBuilder sb = new StringBuilder(128);
	        int mask = 85;
	        for(int i = 0; i < in.length(); i += 2)
	        {
	            int ch = Integer.parseInt(in.substring(i, i + 2), 16);
	            ch ^= mask;
	            mask = mask << 1 & 0xff;
	            if(mask % 4 == 0)
	            {
	                mask |= 1;
	            }
	            char c = (char)ch;
	            sb.append(c);
	        }

	        return StringUtils.stripEnd(sb.toString(), null);
	    }

	    public static String decodeTipoTargetIdFromObjIdDeChiper(String value)
	    {
	        String valueInChiaro = pseudoDecipher(value);
	        String tipoTarget = "";
	        if(StringUtils.isNotEmpty(valueInChiaro))
	        {
	            String params[] = valueInChiaro.split("_");
	            if(params.length == 2)
	            {
	                tipoTarget = params[0];
	            }
	        }
	        return tipoTarget;
	    }

	    public static PropertyFilterType getPropertyFilterNone() {
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.NONE);
			return filter;
		}

		public static PropertyFilterType getPropertyFilterAll() {
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);
			return filter;
		}
		
		public static QueryConditionType[] getCriteria(EnumQueryOperator[] operator, String[] propertyName, String[] value)
		{
			QueryConditionType[] criteria = null;
			if(	(operator != null && operator.length > 0) && 
				(propertyName != null && propertyName.length > 0) && 
				(value != null && value.length > 0) &&
				(operator.length == propertyName.length && operator.length == value.length))
			{
				List<QueryConditionType> criteri = new ArrayList<QueryConditionType>();
				QueryConditionType criterio = null;
				for (int i = 0; i < propertyName.length; i++) 
				{
					criterio = new QueryConditionType();
					criterio.setOperator(operator[i]);
					criterio.setPropertyName(propertyName[i]);
					criterio.setValue(value[i]);
					criteri.add(criterio);
				}
				criteria =  criteri.toArray(new QueryConditionType[0]);
			}
			return criteria;
		}
		
		
	    public static QueryConditionType buildQueryCondition(String propertyName, EnumQueryOperator operator, String value) {
	        QueryConditionType qct = new QueryConditionType();
	        qct.setPropertyName(propertyName);
	        qct.setOperator(operator);
	        qct.setValue(value);
	        return qct;
	    }
	    

	    public static IdFormaDocumentariaType queryForFormaDocumentaria(String descrizioneFormaDocumentaria , ObjectServicePort objectServicePort, ObjectIdType repositoryId,
				PrincipalIdType userPrincipalId) throws Exception {
	        QueryableObjectType target = new QueryableObjectType();
	        target.setObject("FormaDocumentariaDecodifica");

	        PropertyFilterType filter = new PropertyFilterType();
	        filter.setFilterType(EnumPropertyFilter.LIST);

	        List<QueryNameType> richieste = new ArrayList<QueryNameType>();
	        QueryNameType richiesta = new QueryNameType();
	        richiesta.setClassName(target.getObject());
	        richiesta.setPropertyName("dbKey");
	        richieste.add(richiesta);
	        filter.setPropertyList(richieste.toArray(new QueryNameType[richieste.size()]));

	        List<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
	        QueryConditionType qct = new QueryConditionType();
	        qct.setPropertyName("descrizione");
	        qct.setOperator(EnumQueryOperator.EQUALS);
	        qct.setValue(descrizioneFormaDocumentaria);
	        criteria.add(qct);
	       

	        IdFormaDocumentariaType idFormaDocumentaria = null;

	        try {
	            PagingResponseType result = objectServicePort.query(repositoryId, userPrincipalId, target, filter,
	                criteria.toArray(new QueryConditionType[criteria.size()]), null, null, new Integer(0));

	            if (result == null) {
	                System.out.println("ERRORE output servizio query");
	                // return null;
	            } else if (result.getObjectsLength() == 0 || result.getObjectsLength() > 1) {
	                System.out.println("ERRORE recupero forma documentaria: restituiti un numero di risultati diverso da quello previsto. Atteso: 1, Ricevuti: " + result.getObjectsLength());
	                // return null;
	            } else if (result.getObjects(0) != null && result.getObjects(0).getPropertiesLength() > 0) {
	                for (PropertyType current : result.getObjects(0).getProperties()) {
	                    if ("dbKey".equals(current.getQueryName().getPropertyName()) && current.getValue() != null && current.getValue().getContentLength() == 1) {
	                        idFormaDocumentaria = new IdFormaDocumentariaType();
	                        idFormaDocumentaria.setValue(Long.parseLong(current.getValue().getContent(0)));
	                        System.out.println("Trovata forma documentaria " + descrizioneFormaDocumentaria + ", value " + idFormaDocumentaria.getValue());
	                        break;
	                    }
	                }
	            }

	        } catch (it.doqui.acta.acaris.objectservice.AcarisException acEx) {
	            System.err.println("errore nella ricerca della forma documentaria");
	            System.err.println("acEx.getMessage(): " + acEx.getMessage());
	            System.err.println("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
	            System.err.println("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
	            System.err.println("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
	            System.err.println("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
	            System.err.println("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
	            throw acEx;
	        } catch (Exception ex) {
	            System.err.println("errore nella ricerca della forma documentaria");
	            System.err.println("ex.getMessage() " + ex.getMessage());
	            throw ex;
	        }

	        return idFormaDocumentaria;

	    }
	    
}
