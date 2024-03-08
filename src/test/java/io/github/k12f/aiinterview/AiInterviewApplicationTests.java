package io.github.k12f.aiinterview;

import io.github.k12f.aiinterview.domain.model.Resume;
import io.github.k12f.aiinterview.infra.constants.ResumeConstants;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;

@SpringBootTest
class AiInterviewApplicationTests {


    @Resource
    RedisTemplate<String, Resume> redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        Resume resume = (Resume) redisTemplate.opsForHash().get(ResumeConstants.RESUME_KEY, "81131ba195e143cf8e1a2e42f8f93794");


        System.out.println(resume);
    }

}

@Data
@AllArgsConstructor
class User implements Serializable {

    private String id;
    private String name;
    private Integer gender;
}
