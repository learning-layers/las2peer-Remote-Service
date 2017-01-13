package i5.las2peer.services.remoteService;

import static org.junit.Assert.assertTrue;
import i5.las2peer.execution.L2pServiceException;
import i5.las2peer.p2p.AgentNotKnownException;
import i5.las2peer.p2p.LocalNode;
import i5.las2peer.p2p.ServiceNameVersion;
import i5.las2peer.security.L2pSecurityException;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.testing.MockAgentFactory;

import java.io.Serializable;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoteServiceTest {

	private static LocalNode node;

	private static UserAgent testAgent;
	private static final String testPass = "adamspass";
	private static final ServiceNameVersion testService = ServiceNameVersion.fromString(RemoteService.class.getName()
			+ "@1.0");

	@BeforeClass
	public static void startServer() throws Exception {

		// start node
		node = LocalNode.newNode();
		testAgent = MockAgentFactory.getAdam();
		testAgent.unlockPrivateKey(testPass); // agent must be unlocked in order to be stored
		node.storeAgent(testAgent);
		node.launch();

		// during testing, the specified service version does not matter
		ServiceAgent testServiceAgent = ServiceAgent.createServiceAgent(testService, "a pass");
		testServiceAgent.unlockPrivateKey("a pass");

		node.registerReceiver(testServiceAgent);

	}

	@AfterClass
	public static void shutDownServer() throws Exception {
		node.shutDown();
		node = null;
		LocalNode.reset();
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws L2pSecurityException
	 * @throws L2pServiceException
	 * @throws AgentNotKnownException
	 * 
	 */
	@Test
	public void testSayHello() throws AgentNotKnownException, L2pServiceException, L2pSecurityException,
			InterruptedException {
		String result = (String) node.invoke(testAgent, testService, "sayHello", new Serializable[] {});

		assertTrue(result.contains("Aachen"));

	}

}
