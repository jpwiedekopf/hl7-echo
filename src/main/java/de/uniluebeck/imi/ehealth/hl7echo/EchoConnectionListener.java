package de.uniluebeck.imi.ehealth.hl7echo;

import ca.uhn.hl7v2.app.Connection;
import org.slf4j.Logger;

public class EchoConnectionListener implements ca.uhn.hl7v2.app.ConnectionListener {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger("Hl7Echo");
    @Override
    public void connectionReceived(Connection c) {
        logger.info("Connection received from %s".formatted(c.getRemoteAddress()));
    }

    @Override
    public void connectionDiscarded(Connection c) {
        // do nothing
    }
}
