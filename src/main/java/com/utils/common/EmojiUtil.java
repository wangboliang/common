package com.utils.common;

import com.vdurmont.emoji.EmojiParser;

public abstract class EmojiUtil {

    /**
     * 转换emoji <br>
     * Example: <code>🍀</code> 将转变为
     * <code>&amp;#x1f340;</code><br>
     *
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String parseToHtmlHexadecimal(String emoji_str) {
        return EmojiParser.parseToHtmlHexadecimal(emoji_str);
    }

    /**
     * 转换emoji <br>
     * Example: <code>🍀</code> 将转变为
     * &lt;span class='emoji emoji1f340'&gt;&lt;/span&gt;<br>
     *
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String parseToHtmlTag(String emoji_str) {
        if (emoji_str != null) {
            String str = EmojiParser.parseToHtmlHexadecimal(emoji_str);
            return htmlHexadecimalToHtmlTag(str);
        }
        return null;
    }

    /**
     * 转换emoji <br>
     * Example: <code>🍀</code> 将转变为
     * :four_leaf_clover:<br>
     *
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String parseToAliases(String emoji_str) {
        return EmojiParser.parseToAliases(emoji_str);
    }

    /**
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String parseToHtmlDecimal(String emoji_str) {
        return EmojiParser.parseToHtmlDecimal(emoji_str);
    }

    /**
     * 纯文本 删除表情
     *
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String removeAllEmojis(String emoji_str) {
        return EmojiParser.removeAllEmojis(emoji_str);
    }

    /**
     * @param emoji_str emoji_str
     * @return emoji_result
     */
    public static String htmlHexadecimalToHtmlTag(String emoji_str) {
        if (emoji_str != null) {
            return emoji_str.replaceAll("&#x([^;]*);", "<span class='emoji emoji$1'></span>");
        }
        return null;
    }

    /**
     * @param str
     * @return emoji_str
     */
    public static String parseToUnicode(String str) {
        if (str != null) {
            return EmojiParser.parseToUnicode(str);
        }
        return null;
    }

}
