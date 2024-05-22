package de.uniluebeck.imi.ehealth.hl7echo;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v22.datatype.CM_ELD;
import ca.uhn.hl7v2.model.v22.message.ACK;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EchoExceptionHandler implements ReceivingApplicationExceptionHandler {
    private final HapiContext hapiContext;

    private final Logger logger = LoggerFactory.getLogger("Hl7Echo");

    public EchoExceptionHandler(HapiContext hapiContext) {
        this.hapiContext = hapiContext;
    }

    @Override
    public String processException(String incomingMessage, Map<String, Object> incomingMetadata, String outgoingMessage, Exception e) {
        try {
            hapiContext.getPipeParser().parse(incomingMessage);
        } catch (HL7Exception ex) {
            logger.error("Error parsing received message:\n%s\ndue to this error:\n%s".formatted(incomingMessage.indent(2), ex.getMessage().indent(2)));
            try {
                ACK ack = (ACK) hapiContext.getPipeParser().parse(outgoingMessage);
                logger.error("Returning this NACK:\n%s".formatted(ack.printStructure()).indent(2));
            } catch (HL7Exception exc) {
                throw new RuntimeException(exc);
            }
        }
        return outgoingMessage;
    }
}
