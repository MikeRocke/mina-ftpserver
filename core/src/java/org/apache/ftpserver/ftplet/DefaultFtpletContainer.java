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

package org.apache.ftpserver.ftplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This ftplet calls other ftplet methods and returns appropriate return value.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public 
class DefaultFtpletContainer implements Component, FtpletContainer {
    
    private Log log;
    private List ftplets = new ArrayList();
    
    private static class FtpletEntry {
        public FtpletEntry(String name, Ftplet ftplet) {
            this.name = name;
            this.ftplet = ftplet;
        }
        final String name;
        final Ftplet ftplet;
    }
    
    public void configure(Configuration config) throws FtpException {
        // do nothing
    }

    public void dispose() {
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            try {
                ftpletEnt.ftplet.destroy();
            }
            catch(Exception ex) {
                log.error(ftpletEnt.name + " :: FtpletHandler.destroy()", ex);
            }
        }
        ftplets.clear();
        
    }

    public void setLogFactory(LogFactory logFact) {
        log = logFact.getInstance(DefaultFtpletContainer.class);
        
    }
    
    public void addFtplet(String name, Ftplet ftplet) {
        if(getFtplet(name) != null) {
            throw new IllegalArgumentException("Ftplet with name \"" + name + "\" already registred with container");
        }
        
        ftplets.add(new FtpletEntry(name, ftplet));
    }

    public Ftplet removeFtplet(String name) {
        int sz = ftplets.size();
        Ftplet removedFtplet = null;
        for (int i=0; i<sz; i++) {
            FtpletEntry entry = (FtpletEntry) ftplets.get(i);
            
            if(name.equals(entry.name)) {
                ftplets.remove(i);
                removedFtplet = entry.ftplet;
                break;
            }
            
        }
        
        return removedFtplet;
        
    }
    
    /**
     * Get Ftplet for the given name.
     */
    public Ftplet getFtplet(String name) {
        if(name == null) {
            return null;
        }
        
        Ftplet ftplet = null;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            if(ftpletEnt.name.equals(name)) {
                ftplet = ftpletEnt.ftplet;
                break;
            }
        }
        return ftplet;
    }
    
    /**
     * Destroy all ftplets.
     */
    public void destroy() {
        dispose();
    }
    
    /**
     * Call ftplet onConnect.
     */
    public FtpletEnum onConnect(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onConnect(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onDisconnect.
     */
    public FtpletEnum onDisconnect(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onDisconnect(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }
    
    /**
     * Call ftplet onLogin.
     */
    public FtpletEnum onLogin(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onLogin(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /** 
     * Call ftplet onDeleteStart.
     */
    public FtpletEnum onDeleteStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onDeleteStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    
    /**
     * Call ftplet onDeleteEnd.
     */
    public FtpletEnum onDeleteEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onDeleteEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onUploadStart.
     */
    public FtpletEnum onUploadStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onUploadStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onUploadEnd.
     */
    public FtpletEnum onUploadEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onUploadEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onDownloadStart.
     */
    public FtpletEnum onDownloadStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onDownloadStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onDownloadEnd.
     */
    public FtpletEnum onDownloadEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onDownloadEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onRmdirStart.
     */
    public FtpletEnum onRmdirStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onRmdirStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onRmdirEnd.
     */
    public FtpletEnum onRmdirEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onRmdirEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onMkdirStart.
     */
    public FtpletEnum onMkdirStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onMkdirStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /** 
     * Call ftplet onMkdirEnd.
     */
    public FtpletEnum onMkdirEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onMkdirEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }
    
    /**
     * Call ftplet onAppendStart.
     */
    public FtpletEnum onAppendStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onAppendStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onAppendEnd.
     */
    public FtpletEnum onAppendEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onAppendEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onUploadUniqueStart.
     */
    public FtpletEnum onUploadUniqueStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onUploadUniqueStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }
    
    /**
     * Call ftplet onUploadUniqueEnd.
     */
    public FtpletEnum onUploadUniqueEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onUploadUniqueEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    /**
     * Call ftplet onRenameStart.
     */
    public FtpletEnum onRenameStart(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onRenameStart(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }
    
    /**
     * Call ftplet onRenameEnd.
     */
    public FtpletEnum onRenameEnd(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onRenameEnd(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }
    
    /**
     * Call ftplet onSite.
     */
    public FtpletEnum onSite(FtpRequest request, FtpResponse response) throws FtpException, IOException {
        FtpletEnum retVal = FtpletEnum.RET_DEFAULT;
        int sz = ftplets.size();
        for(int i=0; i<sz; ++i) {
            FtpletEntry ftpletEnt = (FtpletEntry)ftplets.get(i);
            retVal = ftpletEnt.ftplet.onSite(request, response);
            if(retVal == null) {
                retVal = FtpletEnum.RET_DEFAULT;
            }
            
            // proceed only if the return value is FtpletEnum.RET_DEFAULT
            if(retVal != FtpletEnum.RET_DEFAULT) {
                break;
            }
        }
        return retVal;
    }

    public void init(FtpletContext ftpletContext, Configuration config) throws FtpException {
        // dummy, forced by Ftplet API       
    }


}
