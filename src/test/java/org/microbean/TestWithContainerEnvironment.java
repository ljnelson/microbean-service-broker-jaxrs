/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2017 MicroBean.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.microbean;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;

import javax.enterprise.event.Observes;

import org.junit.Test;

import org.microbean.main.Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ApplicationScoped
public class TestWithContainerEnvironment {

  
  /*
   * Static fields.
   */

  
  /**
   * The number of instances of this class that have been created (in
   * the context of JUnit execution; any other usage is undefined).
   */
  private static int instanceCount;


  /*
   * Constructors.
   */

  
  public TestWithContainerEnvironment() {
    super();
    instanceCount++;
  }


  private final void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event) {
    assertNotNull(event);
  }
  

  @Test
  public void testContainerStartup() {
    final Logger logger = Logger.getLogger("org.glassfish.jersey");
    assertNotNull(logger);
    logger.setUseParentHandlers(false);
    final Handler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.FINER);
    logger.addHandler(consoleHandler);
    logger.setLevel(Level.FINE);
    System.setProperty("port", "9177");
    final int oldInstanceCount = instanceCount;
    assertEquals(1, oldInstanceCount);
    Main.main(null);
    assertEquals(oldInstanceCount + 1, instanceCount);
  }
  
}

