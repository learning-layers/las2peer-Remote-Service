package i5.las2peer.services.remoteService;

import i5.las2peer.api.Service;

public class RemoteService extends Service {

	public RemoteService() {

	}

	public String sayHello() {
		System.out.println("\033[31m Received request, sending response... \033[0m");
		return "I am a service running at RWTH Aachen!";
	}

}
