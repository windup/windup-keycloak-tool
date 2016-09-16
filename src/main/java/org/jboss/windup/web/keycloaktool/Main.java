package org.jboss.windup.web.keycloaktool;

import java.io.File;

import org.jboss.windup.web.keycloaktool.commands.AddUser;
import org.jboss.windup.web.keycloaktool.commands.CreateWindupUser;
import org.jboss.windup.web.keycloaktool.commands.ImportRealm;
import org.jboss.windup.web.keycloaktool.commands.PrintRealmPublicKeyScriptCommand;
import org.jboss.windup.web.keycloaktool.options.CreateWindupRealmOptions;
import org.jboss.windup.web.keycloaktool.options.CreateWindupUserOptions;
import org.jboss.windup.web.keycloaktool.options.InitializeKeycloakOptions;
import org.jboss.windup.web.keycloaktool.options.Options;
import org.jboss.windup.web.keycloaktool.options.PrintRealmPublicKeyScriptOptions;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.internal.HelpScreenException;

/**
 * Provides the following functionality:
 *
 * <ul>
 * <li>Initialize Keycloak with an admin user and password</li>
 * <li>Create a Windup realm</li>
 * <li>Configure URLs for Windup</li>
 * <li>Get configuration parameters (public key) for windup realm</li>
 * <li>Create Windup user with password</li>
 * </ul>
 *
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class Main
{
    public static void main(String[] argv)
    {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("java -jar windup-keycloak-too.jar");
        try
        {
            Options options = new Options(parser);

            parser.parseArgs(argv, options);

            if (InitializeKeycloakOptions.INITIALIZE_KEYCLOAK.equals(options.getCommand()))
            {
                String filepath = options.getInitializeKeycloakOptions().getFilepath();
                String username = options.getInitializeKeycloakOptions().getUsername();
                String password = options.getInitializeKeycloakOptions().getPassword();
                AddUser.createUser(new File(filepath), null, username, password, null, 0);
            }
            else if (CreateWindupRealmOptions.CREATE_WINDUP_REALM.equals(options.getCommand()))
            {
                ImportRealm importRealm = new ImportRealm();
                importRealm.execute(options.getCreateWindupRealmOptions());
            }
            else if (CreateWindupUserOptions.CREATE_WINDUP_USER.equals(options.getCommand()))
            {
                CreateWindupUser createWindupUser = new CreateWindupUser();
                createWindupUser.execute(options.getCreateWindupUserOptions());
            }
            else if (PrintRealmPublicKeyScriptOptions.PRINT_WINDUP_REALM_PUBLIC_KEY.equals(options.getCommand()))
            {
                PrintRealmPublicKeyScriptCommand printRealmPublicKeyScriptCommand = new PrintRealmPublicKeyScriptCommand();
                printRealmPublicKeyScriptCommand.execute(options.getPrintRealmPublicKeyScriptOptions());
            }
            else
            {
                parser.printHelp();
            }
            System.exit(0);
        }
        catch (HelpScreenException e)
        {
            // ignore
        }
        catch (ArgumentParserException e)
        {
            System.err.println("ERROR: " + e.getMessage());
            if (e.getMessage().contains("too few arguments"))
            {
                parser.printHelp();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
