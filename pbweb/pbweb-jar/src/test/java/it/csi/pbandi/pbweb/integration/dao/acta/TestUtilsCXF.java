package it.csi.pbandi.pbweb.integration.dao.acta;

import java.util.ArrayList;
import java.util.List;

import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;


public class TestUtilsCXF {


	/**********************************************************************************************************
	 * visualizzazione risultati
	 *********************************************************************************************************/

//	public static void stampa(PagingResponseType pagingResponseType) {
//		if (pagingResponseType == null) {
//			System.out.println("ATTENZIONE: recordset null");
//		} else {
//			int max = pagingResponseType.getObjectsLength();
//			for (int i = 0; i < max; i++) {
//				System.out.println("--------------" + (i + 1) + "--------------");
//				ObjectResponseType ort = pagingResponseType.getObjects(i);
//				for (int j = 0; j < ort.getPropertiesLength(); j++) {
//					PropertyType pt = ort.getProperties(j);
//					System.out.println(
//							pt.getQueryName().getClassName() + "." + pt.getQueryName().getPropertyName() + ": ");
//					for (int k = 0; k < pt.getValue().getContentLength(); k++) {
//						System.out.println("    " + pt.getValue().getContent(k));
//					}
//				}
//				System.out.println();
//			}
//		}
//	}
	
	/**********************************************************************************************************
	 * utilities
	 *********************************************************************************************************/
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

//	public static PropertyFilterType getPropertyFilterList(String[] className, String[] propertyName,
//			PropertyFilterType prevFilter) {
//		return getPropertyFilter(EnumPropertyFilter.LIST, className, propertyName, prevFilter);
//	}

	
//	private static PropertyFilterType getPropertyFilter(EnumPropertyFilter type, String[] className, String[] propertyName,
//			PropertyFilterType prevFilter) {
//		PropertyFilterType filter = null;
//		if (type != null) {
//			if (type.value().equals(EnumPropertyFilter.LIST.value())) {
//				filter = (prevFilter != null) ? prevFilter : new PropertyFilterType();
//				filter.setFilterType(type);
//				List<QueryNameType> properties = new ArrayList<QueryNameType>();
//				QueryNameType property = null;
//				if (className.length == propertyName.length) {
//					if (prevFilter != null && prevFilter.getFilterType().value().equals(EnumPropertyFilter.LIST.value())
//							//&& prevFilter.getPropertyListLength() > 0) {
//							&& (prevFilter.getPropertyList() != null && prevFilter.getPropertyList().size()>0)){							
//						for (int j = 0; j < prevFilter.getPropertyList().size(); j++) {
//							properties.add(prevFilter.getPropertyList().get(j));
//						}
//					}
//					for (int i = 0; i < propertyName.length; i++) {
//						property = new QueryNameType();
//						property.setClassName(className[i]);
//						property.setPropertyName(propertyName[i]);
//						properties.add(property);
//					}
//					//filter.setPropertyList(properties.toArray(new QueryNameType[0]));
//					((Object) filter).setPropertyList(properties.toArray(new QueryNameType[0]));
//				} else
//					return null;
//
//			} else {
//				filter = new PropertyFilterType();
//				filter.setFilterType(type);
//			}
//		}
//		return filter;
//	}

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
    
//    public static PropertyType buildPropertyType(EnumObjectType tipoOggetto, String nomeAttributo, Object valore) {
//        PropertyType propertyType = new PropertyType();
//        QueryNameType queryName = new QueryNameType();
//        queryName.setClassName(tipoOggetto.value());
//        queryName.setPropertyName(nomeAttributo);
//        propertyType.setQueryName(queryName);
//        ValueType value = new ValueType();
//        if (valore != null && valore.getClass().isArray()) {
//            Object[] array = (Object[]) ((Object[]) valore);
//            if (array != null && array.length > 0) {
//                String[] elementi = new String[array.length];
//
//                for (int j = 0; j < array.length; ++j) {
//                    elementi[j] = (String) array[j];
//                }
//
//                value.setContent(elementi);
//            }
//        } else {
//            String[] elementi = new String[]{value == null ? null : String.valueOf(valore)};
//            value.setContent(elementi);
//        }
//
//        propertyType.setValue(value);
//        return propertyType;
//    }
    
}
