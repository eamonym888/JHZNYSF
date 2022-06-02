package com.bsoft.com.jhznysf.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class JyrmyyClient {
	//protected static final Logger log = LoggerFactory.getLogger(JyrmyyClient.class);
    private JyrmyyClient() {}   
    private static volatile Client client;  

    public static Client getIstance(String url) { 
        if (client == null) {
            synchronized (Ssh2Client.class) {
                if (client == null) {
                	System.err.println("333333333333333");
                	//log.info("初始化JyrmyyClient....");
            		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            		client=factory.createClient(url);
                }  
            }   
        }   
        return client;   
    }   

}
