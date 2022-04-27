package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class CreateWindupUserOptions
{
    public static final String CREATE_WINDUP_USER = "create-windup-user";
    public static final String DEFAULT_KEYCLOAK_URL = "http://localhost:8080/auth";
    public static final String DEFAULT_REALM_NAME = CreateWindupRealmOptions.DEFAULT_REALM_NAME;
    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_USERNAME = "admin";

    private String keycloakUrl = DEFAULT_KEYCLOAK_URL;
    private String realmName = DEFAULT_REALM_NAME;
    private String adminUser = DEFAULT_USERNAME;
    private String adminPassword = DEFAULT_PASSWORD;

    private String newUserID;
    private String newUserPassword;
    private String firstName;
    private String lastName;

    public CreateWindupUserOptions(Subparsers subparsers)
    {
        Subparser subparser = subparsers.addParser(CREATE_WINDUP_USER)
                    .help("Create the Windup realm (" + CREATE_WINDUP_USER + " --help for more options)");
        subparser
                    .addArgument("--keycloakUrl")
                    .help("URL to use for Keycloak (default: " + DEFAULT_KEYCLOAK_URL + ")")
                    .action((StoreAction) (name, value) -> {
                        this.keycloakUrl = value.toString();
                    });
        subparser
                .addArgument("--realmName")
                .help("Keycloak Realm's name (default: " + DEFAULT_REALM_NAME + ").")
                .action((StoreAction) (name, value) -> {
                    this.realmName = value.toString();
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
                    .addArgument("--newUserID")
                    .help("New user ID")
                    .required(true)
                    .action((StoreAction) (name, value) -> {
                        this.newUserID = value.toString();
                    });

        subparser
                    .addArgument("--newUserPassword")
                    .help("New user password")
                    .required(true)
                    .action((StoreAction) (name, value) -> {
                        this.newUserPassword = value.toString();
                    });

        subparser
                    .addArgument("--firstName")
                    .help("New user's first name")
                    .required(true)
                    .action((StoreAction) (name, value) -> {
                        this.firstName = value.toString();
                    });

        subparser
                    .addArgument("--lastName")
                    .help("New user's last name")
                    .required(true)
                    .action((StoreAction) (name, value) -> {
                        this.lastName = value.toString();
                    });
    }

    public String getKeycloakUrl()
    {
        return keycloakUrl;
    }

    public String getRealmName()
    {
        return realmName;
    }

    public String getAdminUser()
    {
        return adminUser;
    }

    public String getAdminPassword()
    {
        return adminPassword;
    }

    public String getNewUserID()
    {
        return newUserID;
    }

    public String getNewUserPassword()
    {
        return newUserPassword;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}
