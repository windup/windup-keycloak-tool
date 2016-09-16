package org.jboss.windup.web.keycloaktool.commands;

import java.io.InputStream;
import java.util.logging.Logger;

import org.jboss.windup.web.keycloaktool.options.CreateWindupRealmOptions;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.util.JsonSerialization;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class ImportRealm
{
    private static Logger LOG = Logger.getLogger(ImportRealm.class.getName());

    public void execute(CreateWindupRealmOptions options)
    {
        LOG.info("Importing realm to: " + options.getKeycloakUrl() + " admin user: " + options.getAdminUser());
        Keycloak kc = Keycloak.getInstance(options.getKeycloakUrl(),
                    "master", // the realm to log in to
                    options.getAdminUser(), options.getAdminPassword(), // the user
                    "admin-cli");

        try (InputStream realmJsonIS = getClass().getResourceAsStream("/windup-realm/windup-realm.json"))
        {
            RealmRepresentation realmRepresentation = JsonSerialization.readValue(realmJsonIS, RealmRepresentation.class);
            kc.realms().create(realmRepresentation);
        }
        catch (Exception e)
        {
            LOG.severe("Failed to import realm due to: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
