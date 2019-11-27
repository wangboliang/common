package com.domain;

import com.annotation.EmojiParse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wangliang
 * @since 2019/11/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmojiBean {

    @EmojiParse(encode = true, decode = true)
    private String name;

    @EmojiParse
    private String emojiStr;
}
