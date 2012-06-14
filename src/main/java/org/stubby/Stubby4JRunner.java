package org.stubby;

import org.stubby.cli.CommandLineIntepreter;
import org.stubby.database.Repository;
import org.stubby.server.JettyOrchestrator;
import org.stubby.yaml.YamlConsumer;
import org.yaml.snakeyaml.nodes.Node;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public final class Stubby4JRunner {

   public static void main(String[] args) {
      if (CommandLineIntepreter.isHelp(args)) {
         CommandLineIntepreter.printDefaultCommandSample(Stubby4JRunner.class);
      } else {
         final String pathToYamlFile = args[0];
         final Repository repository = startDatabase(pathToYamlFile);
         try {
            JettyOrchestrator.startJetty(repository, args);
         } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
         }
      }
   }

   private static Repository startDatabase(final String yamlConfigFilename) {
      try {
         final Node yamlConfig = YamlConsumer.readYaml(yamlConfigFilename);
         final Repository repository = new Repository();
         return repository;
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
         System.exit(1);
      } catch (SQLException e) {
         e.printStackTrace();
         System.exit(1);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
         System.exit(1);
      }
      return null;
   }
}