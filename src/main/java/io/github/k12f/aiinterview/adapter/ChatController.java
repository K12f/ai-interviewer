package io.github.k12f.aiinterview.adapter;

import cn.hutool.core.date.DateUtil;
import io.github.k12f.aiinterview.client.api.ResumeService;
import io.github.k12f.aiinterview.domain.model.Resume;
import io.github.k12f.aiinterview.infra.constants.ResumeConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

// 通过 value 注解，指定 websocket 的路径
@Tag(name = "ai面试官聊天工具")
@ServerEndpoint(value = "/resume/chat/{uuid}")
@Slf4j
@Controller
public class ChatController {

    private static final ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();
    private static RedisTemplate<String, Object> redisTemplate;
    private static ResumeService resumeService;

    @Autowired
    public void init(RedisTemplate<String, Object> redisTemplate, ResumeService resumeService) {
        ChatController.redisTemplate = redisTemplate;
        ChatController.resumeService = resumeService;
    }

    // 收到消息
    @OnMessage
    public void onMessage(@PathParam("uuid") String uuid, String message) throws IOException {
        var session = webSocketMap.get(uuid);

        log.info("[websocket] 收到消息：id={}，message={}", session.getId(), message);
        if (message.equalsIgnoreCase("bye")) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));
            return;
        }

        var answer = resumeService.chat(message);

        session.getBasicRemote().sendText("[" + DateUtil.now() + "]");
        session.getBasicRemote().sendText("面试官: " + answer);
    }

    // 连接打开
    @OnOpen
    public void onOpen(Session session, @PathParam("uuid") String uuid) throws IOException {
        // 保存 session 到对象

        if (webSocketMap.containsKey(uuid)) {
            log.warn("客户端程序{}已有连接,无需建立连接", webSocketMap.get(uuid));
            return;
        }

        webSocketMap.put(uuid, session);
        // 存入redis中

        var resume = (Resume) redisTemplate.opsForHash().get(ResumeConstants.RESUME_KEY, uuid);
        var message = "请面试官查看我的简历: " + resume;
        var answer = resumeService.chat(message);

        log.info("[" + Instant.now().toEpochMilli() + "]" + "[websocket] 新的连接：id={}", session.getId());
        log.info(answer);
        session.getBasicRemote().sendText("[" + DateUtil.now() + "]");
        session.getBasicRemote().sendText("面试官: " + answer);

    }

    // 连接关闭
    @OnClose
    public void onClose(@PathParam("uuid") String uuid, CloseReason closeReason) {
        var session = webSocketMap.remove(uuid);
        log.info("[websocket] 连接断开：id={}，reason={}", session.getId(), closeReason);
    }

    // 连接异常
    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {

        log.info("[websocket] 连接异常：id={}，throwable={}", session.getId(), throwable.getMessage());

        session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }
}