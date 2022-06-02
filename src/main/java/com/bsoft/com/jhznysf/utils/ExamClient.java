package com.bsoft.com.jhznysf.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class ExamClient {
	//protected static final Logger log = LoggerFactory.getLogger(ExamClient.class);
    private ExamClient() {}   
    private static volatile Client client;  

    public static Client getIstance(String url) { 
        if (client == null) {
            synchronized (Ssh2Client.class) {
                if (client == null) {
                	//log.info("初始化ExamClient....");
            		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            		client=factory.createClient(url);
                }  
            }   
        }   
        return client;   
    }   
}
