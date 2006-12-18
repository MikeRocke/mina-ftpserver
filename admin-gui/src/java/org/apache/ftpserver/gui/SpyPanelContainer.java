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

package org.apache.ftpserver.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.interfaces.Connection;
import org.apache.ftpserver.interfaces.FtpServerContext;

/**
 * This panel monitors user request/replies.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public 
class SpyPanelContainer extends PluginPanel {
    
    private static final long serialVersionUID = 7426681776615720958L;
    
    private JTabbedPane tabbedPane;
    private JLabel defaultComp;
    
    private FtpServerContext serverContext;
    
    
    /**
     * Constructor - create empty tabbed frame
     */
    public SpyPanelContainer(PluginPanelContainer container) {
        super(container);
        initComponents();
    }
    
    /**
     * Initialize UI components.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(470, 340));
        add(tabbedPane, BorderLayout.CENTER);
        
        // initialize component to be displayed if 
        // there is no currently monitored connection
        defaultComp = new JLabel("Please, select a connection.", JLabel.CENTER);
        defaultComp.setFont(new Font(null, Font.BOLD, 17));
        defaultComp.setForeground(Color.blue);
        defaultComp.setBackground(Color.white);
        defaultComp.setBorder(BorderFactory.createEtchedBorder());
        defaultComp.setOpaque(true);
        
        tabbedPane.addTab("Spy", defaultComp);
    }

    /**
     * Monitor connection.
     */
    public void monitorConnection(Connection con) {    
        String userName = getCaption(con); 
        
        // don't add another tab if already being monitored
        int tabCount = tabbedPane.getTabCount();
        for(int i=0; i<tabCount; i++) {
            Component selComp = tabbedPane.getComponentAt(i);
            if ( (selComp != null) && (selComp != defaultComp) ) {
                Connection tabcon = ((SpyPanel)selComp).getConnection();
                if (tabcon == con) {
                    tabbedPane.setTitleAt(i, userName);
                    tabbedPane.setSelectedIndex(i);
                    return;
                }
            }
        }
        
        // add new tab
        SpyPanel spyPane = new SpyPanel(serverContext, con, tabbedPane, defaultComp);
        tabbedPane.remove(defaultComp);
        tabbedPane.add(userName, spyPane);
        tabbedPane.setSelectedComponent(spyPane);
    } 
    
    /**
     * Get tab caption.
     */
    private String getCaption(Connection con) {
        User user = con.getRequest().getUser();
        String name = "UNKNOWN";
        if(user != null) {
            String tmp = user.getName();
            if(tmp != null) {
                name = tmp;
            }
        }
        return name;
    } 
        
    /** 
     * Refresh the ftp configuration
     */
    public void refresh(FtpServerContext serverContext) {
        this.serverContext = serverContext;
        int tabCount = tabbedPane.getTabCount();
        for(int i=tabCount; --i>=0; ) {
            Component tabComp = tabbedPane.getComponentAt(i);
            if ( (tabComp != null) && (tabComp != defaultComp) ) {
                ((SpyPanel)tabComp).closePane();
            }
        }
    }
    
    /** 
     * This can be displayed only when the server is running.
     */
    public boolean canBeDisplayed() {
        return (serverContext != null);
    }

    /**
     * Get the string representation.
     */
    public String toString() {
        return "Spy";
    }
}
