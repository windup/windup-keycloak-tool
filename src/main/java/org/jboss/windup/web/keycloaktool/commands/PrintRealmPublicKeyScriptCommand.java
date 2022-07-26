package org.jboss.windup.web.keycloaktool.commands;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jboss.windup.web.keycloaktool.options.PrintRealmPublicKeyScriptOptions;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.KeysMetadataRepresentation;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class PrintRealmPublicKeyScriptCommand
{
    public void execute(PrintRealmPublicKeyScriptOptions options)
    {
        Keycloak kc = Keycloak.getInstance(options.getKeycloakUrl(),
                    "master", // the realm to log in to
                    options.getAdminUser(), options.getAdminPassword(), // the user
                    "admin-cli");

        List<KeysMetadataRepresentation.KeyMetadataRepresentation> keys = kc.realm("windup").keys().getKeyMetadata().getKeys().stream()
                .filter(keyMetadataRepresentation -> keyMetadataRepresentation.getPublicKey() != null).collect(Collectors.toList());
        if (keys.isEmpty())
            throw new RuntimeException("No public key found for realm!");

        if (keys.size() > 1)
            throw new RuntimeException("Found " + keys.size() + " keys for realm, unable to determine which one to use!");

        String publicKey = keys.get(0).getPublicKey();
        System.out.println("/system-property=keycloak.realm.public.key:add(value=\"" + publicKey + "\")");
    }
}
