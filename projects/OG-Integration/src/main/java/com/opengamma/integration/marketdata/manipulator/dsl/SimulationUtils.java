/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.marketdata.manipulator.dsl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;

import com.google.common.collect.ImmutableMap;
import com.opengamma.DataNotFoundException;
import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.config.impl.ConfigItem;
import com.opengamma.engine.view.ViewDefinition;
import com.opengamma.id.UniqueId;
import com.opengamma.id.VersionCorrection;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.transform.TimedInterrupt;

/**
 * Utilities for creating and running {@link Simulation}s and {@link Scenario}s.
 */
public class SimulationUtils {

  private SimulationUtils() {
  }

  /**
   * Returns the ID of the latest version of a view definition.
   * @param viewDefName The view definition name
   * @param configSource A source for looking up the view definition
   * @return The ID of the latest version of the named view definition, not null
   * @throws DataNotFoundException If no view definition is found with the specified name
   */
  public static UniqueId latestViewDefinitionId(String viewDefName, ConfigSource configSource) {
    Collection<ConfigItem<ViewDefinition>> viewDefs =
        configSource.get(ViewDefinition.class, viewDefName, VersionCorrection.LATEST);
    if (viewDefs.isEmpty()) {
      throw new DataNotFoundException("No view definition found with name '" + viewDefName + "'");
    }
    return viewDefs.iterator().next().getValue().getUniqueId();
  }

  /**
   * Runs a Groovy script that defines a {@link Simulation} using the DSL.
   * @param groovyScript The script location in the filesystem
   * @return The simulation defined by the script
   */
  public static Simulation createSimulationFromDsl(String groovyScript, Map<String, Object> parameters) {
    try {
      return runGroovyDslScript(new BufferedReader(new FileReader(groovyScript)), Simulation.class, parameters);
    } catch (FileNotFoundException e) {
      throw new OpenGammaRuntimeException("Failed to open script file", e);
    }
  }

  /**
   * Runs a Groovy script that defines a {@link Simulation} using the DSL.
   * @param groovyScript For reading the DSL script
   * @return The simulation defined by the script
   */
  public static Simulation createSimulationFromDsl(Reader groovyScript, Map<String, Object> parameters) {
    return runGroovyDslScript(groovyScript, Simulation.class, parameters);
  }

  /**
   * Runs a Groovy script that defines a {@link Scenario} using the DSL.
   * @param groovyScript The script location in the filesystem
   * @return The scenario defined by the script
   */
  public static Scenario createScenarioFromDsl(String groovyScript, Map<String, Object> parameters) {
    try {
      return runGroovyDslScript(new BufferedReader(new FileReader(groovyScript)), Scenario.class, parameters);
    } catch (FileNotFoundException e) {
      throw new OpenGammaRuntimeException("Failed to open script file", e);
    }
  }

  /**
   * Runs a Groovy script that defines a {@link Scenario} using the DSL.
   * @param groovyScript For reading the DSL script
   * @return The scenario defined by the script
   */
  public static Scenario createScenarioFromDsl(Reader groovyScript, Map<String, Object> parameters) {
    return runGroovyDslScript(groovyScript, Scenario.class, parameters);
  }

  /**
   * Runs a Groovy DSL script and returns the value returned by the script.
   * @param scriptReader For reading the script text
   * @param expectedType The expected type of the return value
   * @param parameters Parameters used by the script, null or empty if the script doesn't need any
   * @param <T> The expected type of the return value
   * @return The return value of the script, not null
   */
  private static <T> T runGroovyDslScript(Reader scriptReader, Class<T> expectedType, Map<String, Object> parameters) {
    Map<String, Object> timeoutArgs = ImmutableMap.<String, Object>of("value", 2);
    ASTTransformationCustomizer customizer = new ASTTransformationCustomizer(timeoutArgs, TimedInterrupt.class);
    CompilerConfiguration config = new CompilerConfiguration();
    config.addCompilationCustomizers(customizer);
    config.setScriptBaseClass(SimulationScript.class.getName());
    Binding binding = new Binding(parameters);
    GroovyShell shell = new GroovyShell(binding, config);
    Script script = shell.parse(scriptReader);
    Object scriptOutput = script.run();
    if (scriptOutput == null) {
      throw new IllegalArgumentException("Script " + scriptReader + " didn't return an object");
    }
    if (expectedType.isInstance(scriptOutput)) {
      return expectedType.cast(scriptOutput);
    } else {
      throw new IllegalArgumentException("Script '" + scriptReader + "' didn't create an object of the expected type. " +
                                             "expected type: " + expectedType.getName() + ", " +
                                             "actual type: " + scriptOutput.getClass().getName() + ", " +
                                             "actual value: " + scriptOutput);
    }
  }

  /**
   * Creates a regular expression pattern from a simple glob string. The special characters recognized in the glob
   * string are ? (match any character), * (match any number of characters) and % (same as *). The other characters
   * in the glob string are escaped before the pattern is created so it can safely contain regular expression
   * characters. Escaping is not supported in the glob string, i.e. there's no way to match any of the special
   * characters themselves.
   * @param glob The glob string
   * @return A pattern for matching the glob
   */
  /* package */ static Pattern patternForGlob(String glob) {
    Map<Character, String> replacements = ImmutableMap.of('?', ".", '*', ".*?", '%', ".*?");
    StringBuilder builder = new StringBuilder();
    StringBuilder tokenBuilder = new StringBuilder();
    for (int i = 0; i < glob.length(); i++) {
      char c = glob.charAt(i);
      if (!replacements.containsKey(c)) {
        tokenBuilder.append(c);
      } else {
        if (tokenBuilder.length() != 0) {
          String quotedToken = Pattern.quote(tokenBuilder.toString());
          builder.append(quotedToken);
          tokenBuilder.setLength(0);
        }
        builder.append(replacements.get(c));
      }
    }
    if (tokenBuilder.length() != 0) {
      builder.append(Pattern.quote(tokenBuilder.toString()));
    }
    return Pattern.compile(builder.toString());
  }
}
