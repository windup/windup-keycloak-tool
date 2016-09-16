package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class InitializeKeycloakOptions
{
    public static final String INITIALIZE_KEYCLOAK = "initialize-keycloak";

    private String filepath;
    private String username;
    private String password;

    public InitializeKeycloakOptions(Subparsers subparsers)
    {
        Subparser subparser = subparsers.addParser(INITIALIZE_KEYCLOAK).help("Initialize Keycloak (" + INITIALIZE_KEYCLOAK + " --help for more options)");
        subparser
                    .addArgument("--file")
                    .required(true)
                    .help("Filename to store (eg, /path/to/eap/standalone/configuration/keycloak-add-user.json)")
                    .action((StoreAction) (name, value) -> {
                        this.filepath = value.toString();
                    });
        subparser
                    .addArgument("--username")
                    .required(true)
                    .help("Name of the new admin user to create.")
                    .action((StoreAction) (name, value) -> {
                        this.username = value.toString();
                    });
        subparser
                    .addArgument("--password")
                    .required(true)
                    .help("The new user's password.")
                    .action((StoreAction) (name, value) -> {
                        this.password = value.toString();
                    });
    }

    public String getFilepath()
    {
        return filepath;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
