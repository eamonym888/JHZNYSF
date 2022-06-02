package com.bsoft.com.jhznysf.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class Ssh2Client {
	//protected static final Logger log = LoggerFactory.getLogger(Ssh2Client.class);
    private Ssh2Client() {}   
    private static volatile Client client;  

    public static Client getIstance(String url) { 
        if (client == null) {
            synchronized (Ssh2Client.class) {
                if (client == null) {
                	System.err.println("url==============================="+url);
            		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            		System.err.println("client======================="+client);
            		client=factory.createClient(url);
            		System.err.println("client======================="+client);
                }  
            }   
        }   
        return client;   
    }   
}
