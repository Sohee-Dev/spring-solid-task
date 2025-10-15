package com.puzzlix.solid_task._global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

// 메세지 브로커
// 메세지 ----> 브로커 ---> 메세지 보냄

/**
 * http 통신 -- 프로토콜 업그레이드 /ws-stomp (클라이언트가 보내면)
 * /topic/# 어떤 방으로 발송되어 오는 메세지만 받겠다
 * /app/# -> #번으로 메세지를 보내겠다
 * */

@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // 웹 소켓 지원 x (옛날 방식 (폴
    }

    // 메세지 브로커 설정
    // /topic으로 시작하는 경로는 이 메세지 브로커가 처리하도록 설정 한다
    // 브로커는 해당 경로를 구독하는 클라리언트 들에게 메세지를 전파(브로드캐스트)한다
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);

        // 해당 경로를 구독하는 클라이언트들에게 메세지 전파
        registry.enableSimpleBroker("/topic");

        // 클라이언트가 서버로 메세지를 보낼 때 사용하는 경로의 약속을 정의
        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {

        // 클라이언트 -> 서버로 보내는 단일 메세지의 최대 크기 제한 설정
        // 채팅 메세지, 파일 업로드 등의 단일 메세지 크기 제한
        registry.setMessageSizeLimit(1024 * 1024); // 1MB

        // 서버 --> 클라이언트로 보낼 때 사용하는 버퍼의 최대 크기
        registry.setSendBufferSizeLimit(1024 * 1024);

        // 대기 최대 시간 (20)
        registry.setSendTimeLimit(20000); // 20초
    }
}
