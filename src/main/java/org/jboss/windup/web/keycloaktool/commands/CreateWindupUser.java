package org.jboss.windup.web.keycloaktool.commands;

import java.util.List;
import java.util.logging.Logger;

import org.jboss.windup.web.keycloaktool.options.CreateWindupUserOptions;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class CreateWindupUser
{
    public static final String WINDUP_REALM_NAME = "mta";
    private static Logger LOG = Logger.getLogger(CreateWindupUser.class.getName());

    public void execute(CreateWindupUserOptions options)
    {
        LOG.info("Creating Windup user at: " + options.getKeycloakUrl() + " user: " + options.getNewUserID() + " password: "
                    + options.getNewUserPassword());
        Keycloak kc = Keycloak.getInstance(options.getKeycloakUrl(),
                    "master", // the realm to log in to
                    options.getAdminUser(), options.getAdminPassword(), // the user
                    "admin-cli");

        UserRepresentation user = new UserRepresentation();
        user.setUsername(options.getNewUserID());
        user.setFirstName(options.getFirstName());
        user.setLastName(options.getLastName());
        user.setEnabled(true);

        kc.realm(WINDUP_REALM_NAME).users().create(user).getEntity();

        UsersResource usersResource = kc.realm(WINDUP_REALM_NAME).users();
        List<UserRepresentation> users = usersResource.search(user.getUsername(), null, null, null, 0, 100);
        if (users.isEmpty())
        {
            throw new RuntimeException("Error... no user present with username: " + user.getUsername());
        }
        else if (users.size() > 1)
        {
            throw new RuntimeException("Error... more than one user present with username: " + user.getUsername());
        }

        user = users.get(0);
        try
        {
            System.out.println("User debug: " + JsonSerialization.writeValueAsPrettyString(user));
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(options.getNewUserPassword());
        kc.realm(WINDUP_REALM_NAME).users().get(user.getId()).resetPassword(credential);

        LOG.info("User creation complete!");
    }
}
