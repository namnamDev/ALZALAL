package com.ssafy.common.websocket;

import java.util.Objects;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.ssafy.common.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;

    //소켓통신이 연결되기전에 JWT토큰이 유효한지 확인하는 메소드
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
       log.info("message:" + message);
       log.info("헤더 : " + message.getHeaders());
       log.info("토큰" + accessor.getNativeHeader("Authorization")+"\n");

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
       	log.info("jwt "+accessor.getFirstNativeHeader("Authorization").substring(7));
       	log.info("jwt boolean "+tokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7)));
            
        	if(!tokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7))) {
                log.info("aaaaa토큰이 유효하지 않습니다")
        		throw new IllegalStateException("토큰이 유효하지 않습니다");        
        	}
        		
        }
        return message;
    }
}
