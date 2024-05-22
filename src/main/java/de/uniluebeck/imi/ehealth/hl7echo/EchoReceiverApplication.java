package de.uniluebeck.imi.ehealth.hl7echo;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Application;
import ca.uhn.hl7v2.app.ApplicationException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.message.ACK;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.Receiver;
import java.io.IOException;
import java.util.Map;


public class EchoReceiverApplication implements ReceivingApplication<Message> {

    private final Logger logger = LoggerFactory.getLogger("Hl7Echo");

    @Override
    public Message processMessage(Message theMessage, Map<String, Object> theMetadata) throws ReceivingApplicationException, HL7Exception {
        try {
            logger.info("Received message:\n%s\n".formatted(theMessage.printStructure().indent(2)));
            try {
                ACK ack = (ACK) theMessage.generateACK();
                logger.info("Generated ACK:\n%s\n".formatted(ack.printStructure().indent(2)));
                return ack;
            } catch (HL7Exception e) {
                return theMessage.generateACK(AcknowledgmentCode.AR, e);
            }
        } catch (IOException | HL7Exception e) {
            logger.error("Error generating ACK", e);
            throw new ReceivingApplicationException("Error generating ACK", e);
        }
    }

    @Override
    public boolean canProcess(Message message) {
        return true;
    }
}
