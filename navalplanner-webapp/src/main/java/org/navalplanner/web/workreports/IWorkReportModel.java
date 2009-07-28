package org.navalplanner.web.workreports;

import org.navalplanner.business.common.exceptions.InstanceNotFoundException;
import org.navalplanner.business.common.exceptions.ValidationException;
import org.navalplanner.business.orders.entities.OrderElement;
import org.navalplanner.business.resources.entities.Resource;
import org.navalplanner.business.resources.entities.Worker;
import org.navalplanner.business.workreports.entities.WorkReport;
import org.navalplanner.business.workreports.entities.WorkReportType;

/**
 * Contract for {@link WorkRerportType}
 *
 * @author Diego Pino García <dpino@igalia.com>
 */
public interface IWorkReportModel {

    /**
     * Gets the current {@link WorkReport}.
     *
     * @return A {@link WorkReport}
     */
    WorkReport getWorkReport();

    /**
     * Stores the current {@link WorkReport}.
     *
     * @throws ValidationException
     *             If validation fails
     */
    void save() throws ValidationException;

    /**
     * Makes some operations needed before create a new {@link WorkReport}.
     */
    void prepareForCreate(WorkReportType workReportType);

    /**
     * Makes some operations needed before edit a {@link WorkReport}.
     *
     * @param workReport
     *            The object to be edited
     */
    void prepareEditFor(WorkReport workReport);

    /**
     * Finds an @{link OrdrElement} by code
     *
     * @param code
     * @return
     */
    OrderElement findOrderElement(String code);

    /**
     * Find a @{link Worker} by nif
     *
     * @param nif
     * @return
     * @throws InstanceNotFoundException
     */
    Worker findWorker(String nif) throws InstanceNotFoundException;

    /**
     * Converts @{link Resource} to @{link Worker}
     *
     * @param resource
     * @return
     * @throws InstanceNotFoundException
     */
    Worker asWorker(Resource resource) throws InstanceNotFoundException;

}
