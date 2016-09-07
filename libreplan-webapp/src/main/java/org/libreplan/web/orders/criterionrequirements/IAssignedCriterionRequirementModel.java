/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2009-2010 Fundación para o Fomento da Calidade Industrial e
 *                         Desenvolvemento Tecnolóxico de Galicia
 * Copyright (C) 2010-2011 Igalia, S.L.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.libreplan.web.orders.criterionrequirements;

import java.util.List;
import java.util.Set;

import org.libreplan.business.common.exceptions.ValidationException;
import org.libreplan.business.resources.entities.CriterionType;
import org.libreplan.business.resources.entities.CriterionWithItsType;
import org.libreplan.web.orders.CriterionRequirementWrapper;
import org.libreplan.web.orders.HoursGroupWrapper;

/**
 * @author Susana Montes Pedreira <smontes@wirelessgalicia.com>
 * @author Diego Pino Garcia <dpino@igalia.com>
 */
public interface IAssignedCriterionRequirementModel<T, M> {

    void addCriterionToHoursGroupWrapper(HoursGroupWrapper hoursGroupWrapper);

    CriterionRequirementWrapper addExceptionToHoursGroupWrapper(HoursGroupWrapper hoursGroupWrapper);

    void addNewHoursGroupWrapper();

    void assignCriterionRequirementWrapper();

    void changeCriterionAndType(
            CriterionRequirementWrapper criterionRequirementWrapper, CriterionWithItsType newCriterionAndType);

    void confirm()throws ValidationException;

    void deleteCriterionRequirementWrapper(CriterionRequirementWrapper requirement);

    void deleteCriterionToHoursGroup(HoursGroupWrapper hoursGroupWrapper, CriterionRequirementWrapper requirementWrapper);

    void deleteHoursGroupWrapper(HoursGroupWrapper hoursGroupWrapper);

    List<CriterionRequirementWrapper> getCriterionRequirementWrappers();

    List<CriterionWithItsType> getCriterionWithItsTypes();

    T getElement();

    List<HoursGroupWrapper> getHoursGroupsWrappers();

    M getModel();

    Set<CriterionType> getTypes();

    void init(T element);

    boolean isCodeAutogenerated();

    void selectCriterionToHoursGroup(
            HoursGroupWrapper hoursGroupWrapper,
            CriterionRequirementWrapper exception,
            CriterionWithItsType criterionAndType);

    void setElement(T element);

    void setModel(M model);

    void setValidCriterionRequirementWrapper(CriterionRequirementWrapper requirement, boolean valid);

    void updateCriterionsWithDiferentResourceType(HoursGroupWrapper hoursGroupWrapper);

    CriterionRequirementWrapper validateHoursGroupWrappers();

    CriterionRequirementWrapper validateWrappers(List<CriterionRequirementWrapper> list);

}