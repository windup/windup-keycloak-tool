package org.jboss.windup.web.keycloaktool.commands;

import org.jboss.windup.web.keycloaktool.options.CreateWindupUserOptions;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class CreateWindupUser
{
    private static Logger LOG = Logger.getLogger(CreateWindupUser.class.getName());

    public static final String WINDUP_REALM_NAME = "windup";

    public void execute(CreateWindupUserOptions options)
    {
        LOG.info("Creating Windup user at: " + options.getKeycloakUrl() + " user: " + options.getNewUserID());
        Keycloak kc = Keycloak.getInstance(options.getKeycloakUrl(),
                "master", // the realm to log in to
                options.getAdminUser(), options.getAdminPassword(), // the user
                "admin-cli");

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(options.getNewUserPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(options.getNewUserID());
        user.setFirstName(options.getFirstName());
        user.setLastName(options.getLastName());
        user.setCredentials(Arrays.asList(credential));
        user.setEnabled(true);
        kc.realm(WINDUP_REALM_NAME).users().create(user);
    }
}
