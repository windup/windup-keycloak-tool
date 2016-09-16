package org.jboss.windup.web.keycloaktool.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import org.keycloak.common.util.Base64;
import org.keycloak.hash.Pbkdf2PasswordHashProvider;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
public class AddUser {
    private static final int DEFAULT_HASH_ITERATIONS = 100000;

    public static void createUser(File addUserFile, String realmName, String userName, String password, String rolesString, int iterations) throws Exception {
        List<RealmRepresentation> realms;
        if (addUserFile.isFile()) {
            realms = JsonSerialization.readValue(new FileInputStream(addUserFile), new TypeReference<List<RealmRepresentation>>() {});
        } else {
            realms = new LinkedList<>();
        }

        if (realmName == null) {
            realmName = "master";
        }

        RealmRepresentation realm = null;
        for (RealmRepresentation r : realms) {
            if (r.getRealm().equals(realmName)) {
                realm = r;
            }
        }

        if (realm == null) {
            realm = new RealmRepresentation();
            realm.setRealm(realmName);
            realms.add(realm);
            realm.setUsers(new LinkedList<UserRepresentation>());
        }

        for (UserRepresentation u : realm.getUsers()) {
            if (u.getUsername().equals(userName)) {
                throw new Exception("User with username '" + userName + "' already added to '" + addUserFile + "'");
            }
        }

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userName);
        user.setCredentials(new LinkedList<CredentialRepresentation>());

        UserCredentialValueModel credentialValueModel = new Pbkdf2PasswordHashProvider().encode(password, iterations > 0 ? iterations : DEFAULT_HASH_ITERATIONS);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(credentialValueModel.getType());
        credentials.setAlgorithm(credentialValueModel.getAlgorithm());
        credentials.setHashIterations(credentialValueModel.getHashIterations());
        credentials.setSalt(Base64.encodeBytes(credentialValueModel.getSalt()));
        credentials.setHashedSaltedValue(credentialValueModel.getValue());

        user.getCredentials().add(credentials);

        String[] roles;
        if (rolesString != null) {
            roles = rolesString.split(",");
        } else {
            if (realmName.equals("master")) {
                roles = new String[] { "admin" };
            } else {
                roles = new String[] { "realm-management/realm-admin" };
            }
        }

        for (String r : roles) {
            if (r.indexOf('/') != -1) {
                String[] cr = r.split("/");
                String client = cr[0];
                String clientRole = cr[1];

                if (user.getClientRoles() == null) {
                    user.setClientRoles(new HashMap<String, List<String>>());
                }

                if (user.getClientRoles().get(client) == null) {
                    user.getClientRoles().put(client, new LinkedList<String>());
                }

                user.getClientRoles().get(client).add(clientRole);
            } else {
                if (user.getRealmRoles() == null) {
                    user.setRealmRoles(new LinkedList<String>());
                }
                user.getRealmRoles().add(r);
            }
        }

        realm.getUsers().add(user);

        JsonSerialization.writeValuePrettyToStream(new FileOutputStream(addUserFile), realms);
        System.out.println("Added '" + userName + "' to '" + addUserFile + "', restart server to load user");
    }
}
