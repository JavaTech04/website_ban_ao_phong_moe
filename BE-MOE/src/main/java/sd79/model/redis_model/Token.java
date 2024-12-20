/*
 * Author: Nong Hoang Vu || JavaTech
 * Facebook:https://facebook.com/NongHoangVu04
 * Github: https://github.com/JavaTech04
 * Youtube: https://www.youtube.com/@javatech04/?sub_confirmation=1
 */
package sd79.model.redis_model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Token")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private String id;
    private String accessToken;
    private String refreshToken;
}
