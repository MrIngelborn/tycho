/*******************************************************************************
 * Copyright (c) 2012, 2016 SAP SE and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     SAP SE - initial API and implementation
 *******************************************************************************/

package org.eclipse.tycho.surefire.provider.spi;

import java.util.List;
import java.util.Properties;

import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;
import org.eclipse.tycho.ClasspathEntry;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Surefire provider adapter for tycho. Any plexus {@link Component} in the classpath of
 * tycho-surefire-plugin implementing this interface is registered as a tycho test framework
 * provider.
 */
public interface TestFrameworkProvider {

    /**
     * The test framework type, such as junit or testng
     */
    public String getType();

    /**
     * The test framework version. If several providers of the same type are enabled, the one with
     * the highest version wins.
     */
    public Version getVersion();

    /**
     * @return the supported range of versions this provider supports for the given type
     */
    public VersionRange getVersionRange();

    /**
     * Fully qualified class name of the surefire provider (must implement contract
     * https://maven.apache.org/plugins/maven-surefire-plugin/api.html ).
     */
    public String getSurefireProviderClassName();

    /**
     * Whether this provider should be enabled for the given test bundle classpath and surefire
     * properties.
     * 
     * @param project
     *            TODO
     * @param testBundleClassPath
     *            classpath of the test bundle
     * @param providerProperties
     *            surefire provider properties
     */
    public boolean isEnabled(MavenProject project, List<ClasspathEntry> testBundleClassPath,
            Properties providerProperties);

    /**
     * The list of OSGi bundles required by the test framework provider as maven artifacts. The
     * groupId, artifactId and optionally version (if != <code>null</code>) will be matched against
     * the plugin dependencies of tycho-surefire-plugin.
     */
    public List<Dependency> getRequiredArtifacts();

    /**
     * Provider specific properties that are added to the generic test properties. Implementations
     * must not return null.
     * 
     * @return
     */
    public Properties getProviderSpecificProperties();

}
