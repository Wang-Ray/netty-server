package com.allinpay.osgi.netty.server;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class SingleConfigurator implements ManagedService {

	@Override
	public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
		System.out.println("properties updated.");
		if (null != properties) {
			System.out.println(properties);
		} else {
			System.out.println("no configuration from configuration admin or old configuration has been deleted");
		}

	}

}
