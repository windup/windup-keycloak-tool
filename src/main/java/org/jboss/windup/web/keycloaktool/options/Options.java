package org.jboss.windup.web.keycloaktool.options;

import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Subparsers;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class Options
{
    @Arg
    private String command;

    private InitializeKeycloakOptions initializeKeycloakOptions;
    private CreateWindupRealmOptions createWindupRealmOptions;
    private PrintRealmPublicKeyScriptOptions printRealmPublicKeyScriptOptions;
    private CreateWindupUserOptions createWindupUserOptions;

    public Options(ArgumentParser parser)
    {
        Subparsers subparsers = parser.addSubparsers()
                    .title("Command")
                    .description("You must specify one of the following commands")
                    .metavar("COMMAND")
                    .help("Additional Help")
                    .dest("command");

        this.initializeKeycloakOptions = new InitializeKeycloakOptions(subparsers);
        this.createWindupRealmOptions = new CreateWindupRealmOptions(subparsers);
        this.printRealmPublicKeyScriptOptions = new PrintRealmPublicKeyScriptOptions(subparsers);
        this.createWindupUserOptions = new CreateWindupUserOptions(subparsers);
    }

    public String getCommand()
    {
        return command;
    }

    public InitializeKeycloakOptions getInitializeKeycloakOptions()
    {
        return initializeKeycloakOptions;
    }

    public CreateWindupRealmOptions getCreateWindupRealmOptions()
    {
        return createWindupRealmOptions;
    }

    public CreateWindupUserOptions getCreateWindupUserOptions()
    {
        return createWindupUserOptions;
    }

    public PrintRealmPublicKeyScriptOptions getPrintRealmPublicKeyScriptOptions()
    {
        return printRealmPublicKeyScriptOptions;
    }
}
