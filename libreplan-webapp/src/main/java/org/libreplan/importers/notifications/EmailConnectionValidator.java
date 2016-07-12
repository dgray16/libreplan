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

package org.libreplan.importers.notifications;

import org.libreplan.business.common.daos.IConnectorDAO;
import org.libreplan.business.common.entities.Connector;
import org.libreplan.business.common.entities.ConnectorProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.List;
import java.util.Properties;

/**
 * Validate Email Connection properties
 *
 * Created by
 * @author Vova Perebykivskyi <vova@libreplan-enterprise.com>
 * on 20.01.2016.
 */

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmailConnectionValidator {

    @Autowired
    private IConnectorDAO connectorDAO;

    public boolean validConnection(){
        List<ConnectorProperty> emailConnectorProperties = getEmailConnectorProperties();

        String protocol = null;
        String host = null;
        String port = null;
        String usrnme = null;
        String psswrd = null;

        for (int i = 0; i < emailConnectorProperties.size(); i++){
            switch (i){
                case 1: {
                    protocol = emailConnectorProperties.get(1).getValue();
                    break;
                }
                case 2: {
                    host = emailConnectorProperties.get(2).getValue();
                    break;
                }
                case 3: {
                    port = emailConnectorProperties.get(3).getValue();
                    break;
                }
                case 5: {
                    usrnme = emailConnectorProperties.get(5).getValue();
                    break;
                }
                case 6: {
                    psswrd = emailConnectorProperties.get(6).getValue();
                    break;
                }
            }
        }

        // Set properties of connection
        Properties properties = new Properties();

        Transport transport = null;

        try {
            if (protocol.equals("SMTP")) {
                properties.setProperty("mail.smtp.port", port);
                properties.setProperty("mail.smtp.host", host);
                Session session = Session.getInstance(properties, null);

                transport = session.getTransport("smtp");
                if (usrnme.equals("") && psswrd.equals("")) transport.connect();
            } else if (protocol.equals("STARTTLS")) {
                properties.setProperty("mail.smtps.port", port);
                properties.setProperty("mail.smtps.host", host);
                Session session = Session.getInstance(properties, null);

                transport = session.getTransport("smtps");
                if (!usrnme.equals("") && psswrd != null) transport.connect(host, usrnme, psswrd);
            }
            if (transport != null && transport.isConnected()) return true;

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<ConnectorProperty> getEmailConnectorProperties() {

        Connector connector = connectorDAO.findUniqueByName("E-mail");

        return connector.getProperties();
    }

    public boolean isConnectionActivated(){
        List<ConnectorProperty> emailConnectorProperties = getEmailConnectorProperties();

        for (ConnectorProperty item : emailConnectorProperties){
            if ( item.getKey().equals("Activated") )
                if ( item.getValue().equals("Y") )
                    return true;
            else break;
        }
        return false;
    }

}
