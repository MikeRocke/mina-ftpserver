/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ftpserver;

import java.util.Map;

import org.apache.ftpserver.command.CommandFactory;
import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.ftpletcontainer.impl.DefaultFtpletContainer;
import org.apache.ftpserver.impl.DefaultFtpServerContext;
import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.message.MessageResource;

/**
 * This is the starting point of all the servers. Creates server instances based on 
 * the provided configuration. 
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 701885 $, $Date: 2008-10-05 22:51:37 +0200 (Sun, 05 Oct 2008) $
 */
public class FtpServerFactory {

    private DefaultFtpServerContext serverContext;

    /**
     * Creates a server with the default configuration
     * 
     * @throws Exception
     */
    public FtpServerFactory() {
        serverContext = new DefaultFtpServerContext();
    }

    /**
     * Create a {@link DefaultFtpServer} instance based
     *   on the provided configuration
     * @return The {@link DefaultFtpServer} instance
     */
    public FtpServer createServer() {
        return new DefaultFtpServer(serverContext);
    }
    
    /**
     * Get all listeners available one this server
     * 
     * @return The current listeners
     */
    public Map<String, Listener> getListeners() {
        return serverContext.getListeners();
    }

    /**
     * Get a specific {@link Listener} identified by its name
     * 
     * @param name
     *            The name of the listener
     * @return The {@link Listener} matching the provided name
     */
    public Listener getListener(final String name) {
        return serverContext.getListener(name);
    }

    /**
     * Add a {@link Listener} to this factory
     * @param name The name of the listener
     * @param listener The {@link Listener}
     */
    public void addListener(final String name, final Listener listener) {
        serverContext.addListener(name, listener);
    }

    /**
     * Set the listeners for this server, replaces existing listeners
     * 
     * @param listeners
     *            The listeners to use for this server with the name as the key
     *            and the listener as the value
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setListeners(final Map<String, Listener> listeners) {
        serverContext.setListeners(listeners);
    }

    /**
     * Get all {@link Ftplet}s registered at this server
     * 
     * @return All {@link Ftplet}s
     */
    public Map<String, Ftplet> getFtplets() {
        return serverContext.getFtpletContainer().getFtplets();
    }

    /**
     * Set the {@link Ftplet}s to be active for this server. Replaces existing
     * {@link Ftplet}s
     * 
     * @param ftplets
     *            Ftplets as a map with the name as the key and the Ftplet as
     *            the value
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setFtplets(final Map<String, Ftplet> ftplets) {
        serverContext.setFtpletContainer(new DefaultFtpletContainer(ftplets));
    }

    /**
     * Retrieve the user manager used with this server
     * 
     * @return The user manager
     */
    public UserManager getUserManager() {
        return serverContext.getUserManager();
    }

    /**
     * Set the user manager to be used for this server
     * 
     * @param userManager
     *            The {@link UserManager}
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setUserManager(final UserManager userManager) {
        serverContext.setUserManager(userManager);
    }

    /**
     * Retrieve the file system used with this server
     * 
     * @return The {@link FileSystemFactory}
     */
    public FileSystemFactory getFileSystem() {
        return serverContext.getFileSystemManager();
    }

    /**
     * Set the file system to be used for this server
     * 
     * @param fileSystem
     *            The {@link FileSystemFactory}
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setFileSystem(final FileSystemFactory fileSystem) {
        serverContext.setFileSystemManager(fileSystem);
    }

    /**
     * Retrieve the command factory used with this server
     * 
     * @return The {@link CommandFactory}
     */
    public CommandFactory getCommandFactory() {
        return serverContext.getCommandFactory();
    }

    /**
     * Set the command factory to be used for this server
     * 
     * @param commandFactory
     *            The {@link CommandFactory}
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setCommandFactory(final CommandFactory commandFactory) {
        serverContext.setCommandFactory(commandFactory);
    }

    /**
     * Retrieve the message resource used with this server
     * 
     * @return The {@link MessageResource}
     */
    public MessageResource getMessageResource() {
        return serverContext.getMessageResource();
    }

    /**
     * Set the message resource to be used with this server
     * 
     * @param messageResource
     *            The {@link MessageResource}
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setMessageResource(final MessageResource messageResource) {
        serverContext.setMessageResource(messageResource);
    }

    /**
     * Retrieve the connection configuration this server
     * 
     * @return The {@link MessageResource}
     */
    public ConnectionConfig getConnectionConfig() {
        return serverContext.getConnectionConfig();
    }

    /**
     * Set the message resource to be used with this server
     * 
     * @param messageResource
     *            The {@link MessageResource}
     * @throws IllegalStateException
     *             If a custom server context has been set
     */
    public void setConnectionConfig(final ConnectionConfig connectionConfig) {
        serverContext.setConnectionConfig(connectionConfig);
    }
}
