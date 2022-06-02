package com.bsoft.com.jhznysf.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import javax.xml.namespace.QName;


public class CXFWebserviceUtil {
	//protected static final Logger log = LoggerFactory.getLogger(CXFWebserviceUtil.class);
    
	public static String[] invoke(String url,String operation,Object...parms){
//		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//		Client client = factory.createClient(url);
		Client client=null;
		System.err.println("url================="+url);
		if(url.contains("ssh2/webServices/cxfssh.ws?wsdl")){
			client=Ssh2Client.getIstance(url);
		}else if(url.contains("exam/webServices/cxfssh.ws?wsdl")){
			client=ExamClient.getIstance(url);
		}else if(url.contains("soap/BusService?wsdl")){
			System.err.println("url======11111111111111111111"+url);
			client=SoapClient.getIstance(url);
		}else{
			System.err.println("22222222222222222222222");
			client=JyrmyyClient.getIstance(url);
		}
		
		Endpoint endpoint = client.getEndpoint();
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), operation);
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		if (bindingInfo.getOperation(opName) == null) {
			for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
				if (operation.equals(operationInfo.getName().getLocalPart())) {
					opName = operationInfo.getName();
					break;
				}
			}
		}
		String parmsStr="";
				
		Object[] res=null;
		try {
			res = client.invoke(opName, parms);
		} catch (Exception e) {
			if(parms!=null){
				for (Object o:parms ) {
					if(o!=null){
						parmsStr+=("|"+o.toString()+"|");
					}
				}
			}
			//log.info(e.getLocalizedMessage());
			//log.info("#########url:"+url+"|operation:"+operation+"|parms:"+parmsStr);
			e.printStackTrace();
			return null;
		}
		//log.info("################");
		//log.info(res[0].toString());
		//log.info("################");
		return res[0].toString().split("\\|");
	}
}
