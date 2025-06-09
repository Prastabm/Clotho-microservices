package com.clotho.microservices.service;

import com.clotho.microservices.events.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private JavaMailSender mailSender;

    @KafkaListener(topics="orderplaced")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        //log.info("Received order placed event" );
        //send email to customer
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("clotho@gmail.com");
            helper.setTo(orderPlacedEvent.getEmail().toString());
            helper.setSubject("Order Placed");
            helper.setText("Your order with number " + orderPlacedEvent.getOrderNumber() + " has been placed successfully.", true);

        };
        try {
            // Assuming you have a mailSender bean configured
            mailSender.send(messagePreparator);
            //log.info("Email sent successfully ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //log.error("Failed to send email  ");
        }

    }
}
