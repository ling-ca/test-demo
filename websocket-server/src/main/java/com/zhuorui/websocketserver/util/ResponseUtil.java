package com.zhuorui.websocketserver.util;

import java.util.Random;

/**
 * 工具类：随机回复客户端消息
 *
 * @author qinlingling
 * @date 2020/1/8
 */
public class ResponseUtil {

    public static String randomResponse(){

        String[] res = {
        "今天天气不错啊",
        "中午吃啥",
        "周五了咩",
        "天上有那么多的星星",
        "惊喜不惊喜？！意外不意外？！",
        "收到收到！over over",
        "too tired",
        "hey guys~",
        "hello world",
        "hey guys~ welcome to my channel",
        "hey jude"
        };

        int rand = new Random().nextInt(res.length - 0 + 1) + 0;
        return res[rand];
    }
}
