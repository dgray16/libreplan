/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2009-2010 Fundacion para o Fomento da Calidade Industrial e
 *                         Desenvolvemento Tecnoloxico de Galicia
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

package org.zkoss.ganttz.timetracker.zoom;


import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;

/**
 * Zoom level for days in the first level and hours in the second level.
 *
 * @author Oscar Gonzalez Fernandez <ogonzalez@igalia.com>
 * @author Lorenzo Tilve Alvaro <ltilve@igalia.com>
 * @author Vova Perebykivskyi <vova@libreplan-enterprise.com>
 */
public class DetailSixTimeTrackerState extends TimeTrackerStateWithSubintervalsFitting {

    private static final int NUMBER_OF_HOURS_MINIMUM = 168;

    /**
     * Size in pixels of first level stripe.
     */
    private static final int FIRST_LEVEL_SIZE = 2302;

    /**
     * Size in pixels of second level stripe.
     */
    private static final int SECOND_LEVEL_SIZE = 96;

    DetailSixTimeTrackerState(
            IDetailItemModificator firstLevelModificator, IDetailItemModificator secondLevelModificator) {

        super(firstLevelModificator, secondLevelModificator);
    }

    @Override
    public final double daysPerPixel() {
        return (double) 1 / SECOND_LEVEL_SIZE;
    }

    @Override
    protected IDetailItemCreator getDetailItemCreatorFirstLevel() {
        return dateTime -> new DetailItem(
                FIRST_LEVEL_SIZE,
                dateTime.getDayOfMonth() + dateTime.toString(", MMM YYYY"),
                dateTime,
                dateTime.plusDays(1));
    }

    @Override
    protected IDetailItemCreator getDetailItemCreatorSecondLevel() {
        return dateTime -> new DetailItem(
                SECOND_LEVEL_SIZE,
                dateTime.toString("HH"),
                dateTime,
                dateTime.plusHours(1));
    }

    @Override
    protected ReadablePeriod getPeriodFirstLevel() {
        return Days.days(1);
    }

    @Override
    protected ReadablePeriod getPeriodSecondLevel() {
        return Hours.hours(1);
    }

    @Override
    protected LocalDate round(LocalDate date, boolean down) {
        /*
         * In DetailFiveTimeTrackerState this method returns min interval : Monday - Saturday
         * What do return here, I do not know, maybe 00:00 - 24:00 ?
         */
        if (date.getDayOfWeek() == 1) {
            return date;
        }

        return down ? date.withDayOfWeek(1) : date.withDayOfWeek(1).plusWeeks(1);
    }

    @Override
    protected Period getMinimumPeriod() {
        return PeriodType.DAYS.amount(NUMBER_OF_HOURS_MINIMUM);
    }

    @Override
    protected ZoomLevel getZoomLevel() {
        return ZoomLevel.DETAIL_SIX;
    }

    @Override
    public int getSecondLevelSize() {
        return SECOND_LEVEL_SIZE;
    }

}