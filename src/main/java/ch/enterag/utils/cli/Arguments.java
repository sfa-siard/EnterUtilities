/*== Arguments.java ====================================================
Trivial command-line parser
Version     : $Id: Arguments.java 539 2016-02-17 14:11:58Z hartwig $
Application : CLI Utilities
Description : The command-line is parsed into options (introduced by "-"
              or "/" (Windows only) identified by name and unnamed 
              arguments identified by position.
              Named options may occur anywhere. If they have a value,
              it must be separated from the option name by "=" or ":". 
              Unnamed arguments may not start with a "-" or "/" (on Windows). 
Platform    : JAVA SE 1.5 or higher  
------------------------------------------------------------------------
Copyright  : 2009, Enter AG, Zurich, Switzerland 
Created    : May 13, 2009, Hartwig Thomas
======================================================================*/
package ch.enterag.utils.cli;

import lombok.Getter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class parses the command line and makes the arguments
 * accessible as named options by name and unnamed arguments by
 * position.
 * In the name of simplicity this class does support switches that
 * indicate a boolean value just by their presence.
 *
 * @author Hartwig Thomas
 */
public class Arguments {
    /**
     * Container of named options.
     */
    private final Map<String, String> mapOptions = new HashMap<>();

    /**
     * Container of unnamed arguments.
     */
    private String[] arguments = null;

    @Getter
    private String errorString = null;

    /**
     * Constructor parses the command-line arguments
     *
     * @param cliArguments command-line arguments.
     */
    public Arguments(String[] cliArguments) {
        this.arguments = Arrays.stream(cliArguments).filter(arg -> !isOption(arg)).toArray(String[]::new);
        Arrays.stream(cliArguments).filter(Arguments::isOption).forEach(this::parseArguments);
    }

    private static boolean isOption(String arg) {
        return arg.startsWith("-") || (!File.separator.equals("/") && arg.startsWith("/"));
    }

    /**
     * Get value of parsed option.
     *
     * @param name Name of option.
     * @return Option value (null for missing option, "" for missing value).
     */
    public String getOptions(String name) {
        return mapOptions.get(name);
    }

    /**
     * Get value of unnamed argument.
     *
     * @param argPosition Position of argument (0 based).
     * @return Argument value.
     */
    public String getArgumentPosition(int argPosition) {
        return arguments[argPosition];
    }

    /**
     * @return Number of unnamed arguments.
     */
    public int getArgumentsNumber() {
        return arguments.length;
    }

    /**
     * Parses an argument to extract an option and its value.
     *
     * @param arg The command-line argument string to be parsed.
     */
    private void parseArguments(String arg) {
        int optPosition = getOptPosition(arg);
        if (optPosition > 1) {
            addArgumentsToOptions(arg, optPosition);
        } else
            errorString = "Empty option encountered!";
    }

    private void addArgumentsToOptions(String arg, int optPosition) {
        String optionName = arg.substring(1, optPosition);
        String optionValue = "";
        if (optPosition < arg.length()) {
            if ((arg.charAt(optPosition) == ':') ||
                    (arg.charAt(optPosition) == '='))
                optionValue = arg.substring(optPosition + 1);
            else
                errorString = "Option " + optionName + " must be terminated by colon, equals or blank!";
        }

        mapOptions.put(optionName, optionValue);
    }

    private static int getOptPosition(String arg) {
        int optPosition = 1;
        while (optPosition < arg.length() && Character.isLetterOrDigit(arg.charAt(optPosition))) {
            optPosition++;
        }
        return optPosition;
    }

    /**
     * @param args Command-line arguments.
     * @return Arguments Instance.
     * @deprecated Use {@link #Arguments} to create instances.
     */
    @Deprecated
    public static Arguments getInstance(String[] args) {
        return new Arguments(args);
    }

}
