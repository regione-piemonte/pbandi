/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweberog.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author msibille
 * 
 * @param <T>
 */
public class BetweenCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	/**
	 * Attenzione! usa la trunc
	 * 
	 * @param fromVO
	 *            E' il limite inferiore del filtro
	 * @param toVO
	 *            E' il limite superiore del filtro Se entrambi i parametri sono
	 *            null non viene aggiunta alcuna clausola. Se presente solo il
	 *            limite minore viene aggiunta la clausola maggiore di. Se
	 *            presente solo il limite superiore viene aggiunta la clausola
	 *            minore di
	 */
	public BetweenCondition(T fromVO, T toVO) {
		this(fromVO, toVO, true);
	}

	/**
	 * Permette di scegliere se usare trunc
	 * 
	 * @param fromVO
	 *            E' il limite inferiore del filtro
	 * @param toVO
	 *            E' il limite superiore del filtro
	 * @param useTrunc
	 *            Effettua il confronto fra TRUNC se true Se entrambi i
	 *            parametri sono null non viene aggiunta alcuna clausola. Se
	 *            presente solo il limite minore viene aggiunta la clausola
	 *            maggiore di. Se presente solo il limite superiore viene
	 *            aggiunta la clausola minore di
	 */
	public BetweenCondition(T fromVO, T toVO, boolean useTrunc) {
		try {
			BetweenClosure c = new BetweenClosure(fromVO, toVO, useTrunc);
			forEachVOProperty(fromVO, c);

			initialize(fromVO, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class BetweenClosure extends AbstractFilterClosure {
		private T fromVO;
		private T toVO;
		private boolean useTrunc;

		public BetweenClosure(T fromVO, T toVO, boolean useTrunc) {
			this.fromVO = fromVO;
			this.toVO = toVO;
			this.useTrunc = useTrunc;
		}

		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			addBetweenCondition(fromVO, toVO, useTrunc, andConditionList, aPropertyName);
		}
	}

	private void addBetweenCondition(T fromVO, T toVO,
			boolean useTrunc, List<StringBuilder> andConditionList, String aPropertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Object fromPropertyValue = (Object) BeanUtil.getPropertyValueByName(
				fromVO, aPropertyName);
		Object toPropertyValue = (Object) BeanUtil.getPropertyValueByName(toVO,
				aPropertyName);
		if (!GenericDAO.isNull(fromPropertyValue)
				&& !GenericDAO.isNull(toPropertyValue)) {
			andConditionList.add(new StringBuilder(GenericDAO.betweenCondition(
					aPropertyName, useTrunc)));
			params.addValue("from_" + aPropertyName, fromPropertyValue);
			params.addValue("to_" + aPropertyName, toPropertyValue);
		} else if (!GenericDAO.isNull(fromPropertyValue)) { // Maggiore di
			andConditionList.add(new StringBuilder(GenericDAO
					.greaterThanOrEqualCondition(aPropertyName, useTrunc)));
			params.addValue(aPropertyName, fromPropertyValue);

		} else if (!GenericDAO.isNull(toPropertyValue)) { // Minore di
			andConditionList.add(new StringBuilder(GenericDAO
					.lessThanOrEqualCondition(aPropertyName, useTrunc)));
			params.addValue(aPropertyName, toPropertyValue);
		}
	}
}
