import command.CommandInvoker;
import config.ApplicationConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader bufferedReader;
        String inputFileName ="input/input1.txt";
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileName));
            String line = bufferedReader.readLine();
            while (line != null) {
                List<String> listOfTokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(listOfTokens.get(0), listOfTokens);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
