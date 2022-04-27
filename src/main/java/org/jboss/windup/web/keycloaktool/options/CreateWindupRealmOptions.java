package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class CreateWindupRealmOptions
{
    public static final String CREATE_WINDUP_REALM = "create-windup-realm";
    public static final String DEFAULT_KEYCLOAK_URL = "http://localhost:8080/auth";
    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_REALM_NAME = "mta";
    public static final String DEFAULT_LOGIN_THEME = "mta";

    private String keycloakUrl = DEFAULT_KEYCLOAK_URL;
    private String adminUser = DEFAULT_USERNAME;
    private String adminPassword = DEFAULT_PASSWORD;
    private String realmName = DEFAULT_REALM_NAME;
    private String loginTheme = DEFAULT_LOGIN_THEME;

    public CreateWindupRealmOptions(Subparsers subparsers)
    {
        Subparser subparser = subparsers.addParser(CREATE_WINDUP_REALM).help("Create the Windup realm (" + CREATE_WINDUP_REALM + " --help for more options)");
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
        subparser
                .addArgument("--realmName")
                .help("Keycloak Realm's name (default: " + DEFAULT_REALM_NAME + ").")
                .action((StoreAction) (name, value) -> {
                    this.realmName = value.toString();
                });
        subparser
                .addArgument("--loginTheme")
                .help("Keycloak login theme (default: " + DEFAULT_LOGIN_THEME + ").")
                .action((StoreAction) (name, value) -> {
                    this.loginTheme = value.toString();
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

    public String getRealmName()
    {
        return realmName;
    }

    public String getLoginTheme()
    {
        return loginTheme;
    }
}
