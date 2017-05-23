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

        String filename;
        switch (options.getKeycloakVersion()) {
            case CreateWindupRealmOptions.VERSION_LATEST:
                filename = "/windup-realm/windup-realm.json";
                break;
            case CreateWindupRealmOptions.VERSION_70:
                filename = "/windup-realm/windup-realm-sso-70.json";
                break;
            default:
                throw new RuntimeException("Unrecognized keycloak version: " + options.getKeycloakVersion());
        }
        LOG.info("Using realm file: " + filename);

        try (InputStream realmJsonIS = getClass().getResourceAsStream(filename))
        {
            RealmRepresentation realmRepresentation = JsonSerialization.readValue(realmJsonIS, RealmRepresentation.class);
            kc.realms().create(realmRepresentation);
            LOG.info("Import complete!");
        }
        catch (Exception e)
        {
            LOG.severe("Failed to import realm due to: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
