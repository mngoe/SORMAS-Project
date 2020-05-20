/*
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2020 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.symeda.sormas.api.utils.fieldaccess;

import de.symeda.sormas.api.utils.fieldvisibility.FieldVisibilityCheckers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldAccessCheckers {
	private List<Checker> checkers = new ArrayList<>();

	public boolean isAccessible(Class<?> parentType, String fieldName) {
		Field declaredField = getDeclaredField(parentType, fieldName);
		if (declaredField == null) {
			return true;
		}

		return isAccessible(declaredField);
	}

	public boolean isAccessible(Field field) {
		for (Checker checker : checkers) {
			if (!checker.isAccessible(field)) {
				return false;
			}
		}

		return true;
	}

	public boolean hasRigths() {
		for (Checker checker : checkers) {
			if (!checker.hasRight()) {
				return false;
			}
		}

		return true;
	}

	public FieldAccessCheckers add(Checker checker) {
		checkers.add(checker);

		return this;
	}

	private Field getDeclaredField(Class<?> parentType, String propertyId) {
		try {
			return parentType.getDeclaredField(propertyId);
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	public interface Checker {
		boolean isAccessible(Field field);

		boolean hasRight();
	}
}
