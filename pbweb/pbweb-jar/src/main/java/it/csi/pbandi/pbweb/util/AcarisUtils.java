/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.util;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;

public class AcarisUtils {

	
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
}
