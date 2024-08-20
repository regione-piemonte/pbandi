/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import it.csi.pbandi.pbgestfinbo.util.BeanMapper;

public class BeanRowMapper<T> implements RowMapper {
	private Class className;

   public BeanRowMapper(final Class<T> className){
        this.className = className;
       }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	BeanMapper beanMapper = new BeanMapper();
        try {
        	T vo = (T) beanMapper.toBean(rs, className);
			return vo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
