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

package org.libreplan.business.email.daos;

import org.libreplan.business.common.daos.GenericDAOHibernate;
import org.libreplan.business.email.entities.EmailTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link EmailTemplate}
 *
 *
 * @author Created by Vova Perebykivskyi <vova@libreplan-enterprise.com> on 24.09.2015.
 */
@Repository
public class EmailTemplateDAO extends GenericDAOHibernate<EmailTemplate, Long> implements IEmailTemplateDAO{

    @Override
    public List<EmailTemplate> getAll() {
        return list(EmailTemplate.class);
    }

    @Override
    public String getContentBySelectedLanguage(int languageOrdinal, int emailTemplateTypeOrdinal) {
        for (int i = 0; i < list(EmailTemplate.class).size(); i++)
            if ( list(EmailTemplate.class).get(i).getLanguage().ordinal() == languageOrdinal &&
                    list(EmailTemplate.class).get(i).getType().ordinal() == emailTemplateTypeOrdinal )
                return list(EmailTemplate.class).get(i).getContent();
        return "";
    }

    @Override
    public String getContentBySelectedTemplate(int emailTemplateTypeOrdinal, int languageOrdinal) {
        for (int i = 0; i < list(EmailTemplate.class).size(); i++)
            if ( list(EmailTemplate.class).get(i).getType().ordinal() == emailTemplateTypeOrdinal &&
                    list(EmailTemplate.class).get(i).getLanguage().ordinal() == languageOrdinal )
                return list(EmailTemplate.class).get(i).getContent();
        return "";
    }

    @Override
    public String getSubjectBySelectedLanguage(int languageOrdinal, int emailTemplateTypeOrdinal) {
        for (int i = 0; i < list(EmailTemplate.class).size(); i++)
            if ( list(EmailTemplate.class).get(i).getLanguage().ordinal() == languageOrdinal &&
                    list(EmailTemplate.class).get(i).getType().ordinal() == emailTemplateTypeOrdinal )
                return list(EmailTemplate.class).get(i).getSubject();
        return "";
    }

    @Override
    public String getSubjectBySelectedTemplate(int emailTemplateTypeOrdinal, int languageOrdinal) {
        for (int i = 0; i < list(EmailTemplate.class).size(); i++)
            if ( list(EmailTemplate.class).get(i).getType().ordinal() == emailTemplateTypeOrdinal &&
                    list(EmailTemplate.class).get(i).getLanguage().ordinal() == languageOrdinal )
                return list(EmailTemplate.class).get(i).getSubject();
        return "";
    }
}
