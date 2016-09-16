Windup Keycloak Tool
======================================

Usage
-------------

 1) Create the initial admin user (server not running):

    ```java -jar target/windup-keycloak-tool.jar initialize-keycloak --username admin --password password --file /home/jsightler/project/migration/software/windup-web-distribution/target/wildfly-10.1.0.Final/standalone/configuration/keycloak-add-user.json```

 2) Import the windup realm (server must be running):

    ```java -jar target/windup-keycloak-tool.jar create-windup-realm```

 3) Create a new user in the windup realm (server must be running):

     ```java -jar target/windup-keycloak-tool.jar create-windup-user --newUserID windup --newUserPassword password --firstName Windup --lastName User```

 4) Prints a CLI script for adding the public key to the EAP/Wildfly system properties (server must be running):

     ```java -jar target/windup-keycloak-tool.jar print-windup-realm-public-key-script```
