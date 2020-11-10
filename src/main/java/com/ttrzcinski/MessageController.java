package com.ttrzcinski;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
//import java.util.UUID;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MessageController {

    // Starting value for IDs of messages
    private static final long COUNTER_BEGINNING = 2L;
    // Counted deadline of E-mail messages to send away
    private static final int DELTA_IN_MINUTES = 5;

    // Serves as sequence for IDs
    private static AtomicLong idCounter;
    // Keeps created objects as repo
    private static ConcurrentMap<Long, MessageSaved> instRepo;

    public MessageController() {
        // Init counter of id
        if (MessageController.idCounter == null) {
            MessageController.idCounter = new AtomicLong(COUNTER_BEGINNING);
        }
        // Init repo
        if (MessageController.instRepo == null) {
            MessageController.instRepo = new ConcurrentHashMap<>();
        }
    }

    @Post(uri = "/api/message")
    public MessageSaved save(@Valid @Body Message message) {
        // Book some empty ID
        MessageSaved messageSaved = null;
        for (int i = 0; i < 10; i++) {
            long theID = MessageController.idCounter.getAndIncrement();
            try {
                messageSaved = new MessageSaved(message, theID);
                //messageSaved.setMagicNumber(UUID.randomUUID().timestamp());
                MessageSaved oldValue = MessageController.instRepo.put(messageSaved.getID(), messageSaved);
                if (oldValue == null) {
                    break;
                }
            } catch (Exception exc_1) {
                System.err.println("Couldn't add new message.");
                break;
            }
        }
        return messageSaved;
    }

    @Get(uri = "/api/messages/{email}")
    public List<MessageSaved> readEmails(String email) {
        // Check, if given email has value
        List<MessageSaved> results = new ArrayList<>();
        if (email == null || email.trim().isEmpty()) {
            System.err.println("E-mail address was not provided."); // 204 ?
            return results;
        }
        // TODO CHECK EMAIL PATTERN
        if (email.contains("@") && email.length() < 3) {
            System.err.println("E-mail address is malformed."); // 204 ?
            return results;
        }
        // Prepare list of emails
        ConcurrentMap<Long, MessageSaved> messagesCopy = MessageController.instRepo;
        for (Entry<Long, MessageSaved> entry : messagesCopy.entrySet()) {
            if (entry.getValue().getEmail().equalsIgnoreCase(email)) {
                results.add(entry.getValue());
            }
        }
        return results;
    }

    @Post(uri = "/api/send")
    public void send() {
        // Send messages away
        ConcurrentMap<Long, MessageSaved> messagesCopy = MessageController.instRepo;
        messagesCopy.forEach((key, message) -> {
            message.setSent(true);
            messagesCopy.put(message.getID(), message);
        });
        // TODO MAYBE ADD HERE SOME RANDOM SLEEP TO BEHAVE AS WITH NETWORK LAG
        // Remove them after sending
        long counter = messagesCopy.keySet().stream()
                .map(messageSaved -> MessageController.instRepo.remove(messageSaved))
                .filter(Objects::nonNull)
                .count();
        System.out.printf("Sent %d messages.", counter);
        // Remove those older, than 5 minutes
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(MessageController.DELTA_IN_MINUTES);
        counter = instRepo.size();
        MessageController.instRepo.entrySet().stream()
                .filter(e -> (e.getValue().getCreatedDate().toEpochSecond(ZoneOffset.UTC) < deadline.toEpochSecond(ZoneOffset.UTC)))
                .forEach(e -> MessageController.instRepo.remove(e.getKey()));
        counter -= MessageController.instRepo.size();
        System.out.printf("Dropped %d messages.", counter);
    }
}
