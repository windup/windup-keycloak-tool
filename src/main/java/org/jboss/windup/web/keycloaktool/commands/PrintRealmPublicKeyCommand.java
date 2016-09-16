package org.jboss.windup.web.keycloaktool.commands;

import java.util.logging.Logger;

import org.jboss.windup.web.keycloaktool.options.CreateWindupRealmOptions;
import org.jboss.windup.web.keycloaktool.options.PrintRealmPublicKeyOptions;
import org.keycloak.admin.client.Keycloak;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class PrintRealmPublicKeyCommand
{
    private static Logger LOG = Logger.getLogger(ImportRealm.class.getName());

    public void execute(PrintRealmPublicKeyOptions options)
    {
        Keycloak kc = Keycloak.getInstance(options.getKeycloakUrl(),
                    "master", // the realm to log in to
                    options.getAdminUser(), options.getAdminPassword(), // the user
                    "admin-cli");

        String publicKey = kc.realm("windup").toRepresentation().getPublicKey();
        System.out.println(publicKey);
    }
}
