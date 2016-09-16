package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class PrintRealmPublicKeyOptions
{
    public static final String PRINT_WINDUP_REALM_PUBLIC_KEY = "print-windup-realm-public-key";
    public static final String DEFAULT_KEYCLOAK_URL = "http://localhost:8080/auth";
    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_USERNAME = "admin";

    private String keycloakUrl = DEFAULT_KEYCLOAK_URL;
    private String adminUser = DEFAULT_USERNAME;
    private String adminPassword = DEFAULT_PASSWORD;

    public PrintRealmPublicKeyOptions(Subparsers subparsers)
    {
        Subparser subparser = subparsers.addParser(PRINT_WINDUP_REALM_PUBLIC_KEY)
                    .help("Gets the public key for the windup realm (" + PRINT_WINDUP_REALM_PUBLIC_KEY + " --help for more options)");
        subparser
                    .addArgument("--keycloakUrl")
                    .help("URL to use for Keycloak (default: " + DEFAULT_KEYCLOAK_URL + ")")
                    .action((StoreAction) (name, value) -> {
                        this.keycloakUrl = value.toString();
                    });
        subparser
                    .addArgument("--username")
                    .help("Keycloak Admin user name (default: " + DEFAULT_USERNAME + ").")
                    .action((StoreAction) (name, value) -> {
                        this.adminUser = value.toString();
                    });
        subparser
                    .addArgument("--password")
                    .help("Keycloak Admin user password (default: " + DEFAULT_PASSWORD + ").")
                    .action((StoreAction) (name, value) -> {
                        this.adminPassword = value.toString();
                    });
    }

    public String getKeycloakUrl()
    {
        return keycloakUrl;
    }

    public String getAdminUser()
    {
        return adminUser;
    }

    public String getAdminPassword()
    {
        return adminPassword;
    }
}