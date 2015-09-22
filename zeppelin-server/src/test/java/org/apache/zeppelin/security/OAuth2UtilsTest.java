/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.zeppelin.security;

import junit.framework.Assert;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.zeppelin.conf.ZeppelinConfiguration;

import org.apache.zeppelin.utils.OAuth2Utils;
import org.junit.Test;


/**
 * Created by almacro on 9/21/2015.
 */
public class OAuth2UtilsTest {
    @Test
    public void isOAuth2EnabledFromConfig() {
      Assert.assertFalse(OAuth2Utils.isOAuth2Enabled(ZeppelinConfiguration.create()));
    }

    @Test
    public void isOAuth2EnabledFromPositiveMock() {
        // OAuth2 should be enabled on the "positive" mock
        Assert.assertTrue(OAuth2Utils.isOAuth2Enabled(getPositiveMockConfiguration()));
    }
    @Test
    public void isOAuth2EnabledFromNegativeMock() {
        // OAuth2 should be disabled on the "positive" mock
        Assert.assertFalse(OAuth2Utils.isOAuth2Enabled(getNegativeMockConfiguration()));
    }

    private ZeppelinConfiguration positiveMock;
    private ZeppelinConfiguration getPositiveMockConfiguration(){
      final String SITE_CONF = "/zeppelin-site_EnableOAuth2.xml";
        if(positiveMock == null) {
          try {
            positiveMock = new ZeppelinConfiguration(this.getClass().getResource(SITE_CONF));
          } catch(ConfigurationException e) {
              handleConfigException(SITE_CONF, e);
          }
            
        }
      return positiveMock;
    }
    
    private ZeppelinConfiguration negativeMock;
    private ZeppelinConfiguration getNegativeMockConfiguration() {
      final String SITE_CONF = "/zeppelin-site_DisableOAuth2.xml";
      if(negativeMock == null) {
        try {
          negativeMock = new ZeppelinConfiguration(this.getClass().getResource("/zeppelin-site_DisableOAuth2.xml"));
        } catch(ConfigurationException e) {
          handleConfigException(SITE_CONF, e);       
        }
      }
      return negativeMock;
    }

    private void handleConfigException(String configPath, ConfigurationException e) {
        throw new RuntimeException("got error creating conf for site file: %s".format(configPath), e);
    }

}
