/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2015 LibrePlan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.libreplan.importers.notifications.jobs;


import org.libreplan.importers.notifications.IEmailNotificationJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Sends E-mail to users with data that storing in notification_queue table
 * and that are treat to {@link EmailTemplateEnum.TEMPLATE_TASK_ASSIGNED_TO_RESOURCE}
 *
 * Created by
 * @author Vova Perebykivskyi <vova@libreplan-enterprise.com>
 * on 13.10.2015.
 *
 */

public class SendEmailOnTaskAssignedToResourceJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().
                getJobDataMap().get("applicationContext");

        IEmailNotificationJob taskAssignedToResource = (IEmailNotificationJob) applicationContext
                .getBean("SendEmailOnTaskAssignedToResource");

        taskAssignedToResource.sendEmail();
    }

}
