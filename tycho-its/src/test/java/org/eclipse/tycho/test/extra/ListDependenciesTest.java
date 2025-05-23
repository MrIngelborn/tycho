/*******************************************************************************
 * Copyright (c) 2019, 2024 Red Hat, Inc. and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.tycho.test.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;

import org.apache.maven.it.Verifier;
import org.eclipse.tycho.test.AbstractTychoIntegrationTest;
import org.junit.Test;

public class ListDependenciesTest extends AbstractTychoIntegrationTest {

	@Test
	public void testDependencyInReactor() throws Exception {
		Verifier verifier = getVerifier("extra/dependencyList/multi-modules", false);
		verifier.executeGoal("verify");
		verifier.verifyErrorFreeLog();
		File file = new File(verifier.getBasedir(), "dependent/target/dependencies-list.txt");
		assertTrue(file.exists());
		try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
			File dependency = new File(reader.readLine());
			assertTrue(dependency.exists());
			assertEquals("dependency-0.1.0-SNAPSHOT.jar", dependency.getName());
		}
	}

	@Test
	public void testDependencyWithNestedJar() throws Exception {
		Verifier verifier = getVerifier("extra/dependencyList/dependency-with-nested-jar");
		verifier.executeGoal("verify");
		verifier.verifyErrorFreeLog();
		File file = new File(verifier.getBasedir(), "target/dependencies-list.txt");
		assertTrue(file.exists());
		try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
			File dependency = new File(reader.readLine());
			assertTrue(dependency.exists());
			assertEquals("org.junit-4.13.2.v20240929-1000.jar", dependency.getName());
		}
	}
}
