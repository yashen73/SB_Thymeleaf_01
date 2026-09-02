package com.example.SB_Thymeleaf_01.Config;

import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Collections;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired(required = true)
    public JwtUtil jwtUtil;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //For sockJS
        registry.addEndpoint("/chat-websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();

        //For PostMan
        registry.addEndpoint("/chat-websocket-native")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

                //CONNECTION establishment steps
                if(StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String authHeader = accessor.getFirstNativeHeader("Authorization");
                    System.out.println("Authorization Header is : " + authHeader);

                    System.out.println("CONNECT frame Received");


                    if (authHeader != null) {

                        //Extracting username from AUTHHEADER . . . .
                        String userMail = jwtUtil.extractUsername(authHeader);
                        System.out.println("AuthHeader is Extracted and Mail is : " + userMail);

                        //Setting usermails in Session Attributes . . . .
                        accessor.getSessionAttributes().put("UserMail", userMail);
                        Principal principal = new UsernamePasswordAuthenticationToken(userMail, null, Collections.emptyList());
                        accessor.setUser(principal);
                        System.out.println("Set user : " + accessor.getUser());
                        System.out.println("set user name : " + accessor.getUser().getName());
                    } else {
                        throw new MessagingException("Invalid Token....");
                    }
                }

                //Disconnection Steps....
                if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    String userMail = (String) accessor.getSessionAttributes().get("userMail");

                    if(userMail != null) {
                        System.out.println("User Disconnected"+userMail);
                    }
                }
                System.out.println(
                        "COMMAND = " + accessor.getCommand()
                );

                System.out.println(
                        "USER = " + accessor.getUser()
                );
                return MessageBuilder
                        .withPayload(message.getPayload())
                        .setHeaders(accessor)
                        .build();
            }
        });
    }
}
