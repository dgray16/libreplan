/*
 * This file is part of LibrePlan
 *
 * Copyright (C) 2016 LibrePlan
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

package org.libreplan.importers.notifications.realization;


import org.joda.time.LocalDate;
import org.libreplan.business.common.Configuration;
import org.libreplan.business.common.exceptions.InstanceNotFoundException;
import org.libreplan.business.email.entities.EmailNotification;
import org.libreplan.business.email.entities.EmailTemplateEnum;
import org.libreplan.business.planner.daos.ITaskElementDAO;
import org.libreplan.business.planner.entities.TaskElement;
import org.libreplan.business.users.daos.IUserDAO;
import org.libreplan.business.users.entities.User;
import org.libreplan.business.users.entities.UserRole;
import org.libreplan.importers.notifications.ComposeMessage;
import org.libreplan.importers.notifications.EmailConnectionValidator;
import org.libreplan.importers.notifications.IEmailNotificationJob;
import org.libreplan.web.email.IEmailNotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;


/**
 * Sends E-mail to manager user (it writes in responsible field in project properties)
 * with data that storing in notification_queue table
 * and that are treat to {@link EmailTemplateEnum.TEMPLATE_MILESTONE_REACHED}
 * Date will be send on current date equals to deadline date of {@link Milestone}
 * But it will be only send to Manager (you can assign him in project properties)
 *
 * Created by
 * @author Vova Perebykivskyi <vova@libreplan-enterprise.com>
 * on 20.01.2016.
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SendEmailOnMilestoneReached implements IEmailNotificationJob {

    @Autowired
    private IEmailNotificationModel emailNotificationModel;

    @Autowired
    private ITaskElementDAO taskElementDAO;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ComposeMessage composeMessage;

    @Autowired
    EmailConnectionValidator emailConnectionValidator;

    @Override
    public void sendEmail() {
        // Gathering data
        checkMilestoneDate();

        if ( Configuration.isEmailSendingEnabled() ){

            if ( emailConnectionValidator.isConnectionActivated() )

                if ( emailConnectionValidator.validConnection() ){

                List<EmailNotification> notifications = emailNotificationModel
                        .getAllByType(EmailTemplateEnum.TEMPLATE_MILESTONE_REACHED);

                for (int i = 0; i < notifications.size(); i++)
                    if ( composeMessageForUser(notifications.get(i)) )
                        deleteSingleNotification(notifications.get(i));
            }
        }
    }

    @Override
    public boolean composeMessageForUser(EmailNotification notification) {
        return composeMessage.composeMessageForUser(notification);
    }

    private void deleteSingleNotification(EmailNotification notification){
        emailNotificationModel.deleteById(notification);
    }

    private void sendEmailNotificationToManager(TaskElement item){
        emailNotificationModel.setNewObject();
        emailNotificationModel.setType(EmailTemplateEnum.TEMPLATE_MILESTONE_REACHED);
        emailNotificationModel.setUpdated(new Date());

        String responsible = "";
        if ( item.getParent().getOrderElement().getOrder().getResponsible() != null )
            responsible = item.getParent().getOrderElement().getOrder().getResponsible();

        User user = null;
        try {
            user = userDAO.findByLoginName(responsible);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }

        if ( user.getWorker() != null && user.isInRole(UserRole.ROLE_EMAIL_MILESTONE_REACHED) ) {
            emailNotificationModel.setResource(user.getWorker());
            emailNotificationModel.setTask(item);
            emailNotificationModel.setProject(item.getParent());
            emailNotificationModel.confirmSave();
        }
    }

    public void checkMilestoneDate() {
        List<TaskElement> list = taskElementDAO.getTaskElementsWithMilestones();

        LocalDate date = new LocalDate();
        int currentYear = date.getYear();
        int currentMonth = date.getMonthOfYear();
        int currentDay = date.getDayOfMonth();

        for (TaskElement item : list){
            if ( item.getDeadline() != null ){
                LocalDate deadline = item.getDeadline();
                int deadlineYear = deadline.getYear();
                int deadlineMonth = deadline.getMonthOfYear();
                int deadlineDay = deadline.getDayOfMonth();

                if (currentYear == deadlineYear &&
                        currentMonth == deadlineMonth &&
                        currentDay == deadlineDay)
                    sendEmailNotificationToManager(item);
            }
        }
    }

}
