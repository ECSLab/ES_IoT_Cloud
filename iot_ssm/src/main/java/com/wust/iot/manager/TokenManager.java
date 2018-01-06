package com.wust.iot.manager;

import com.wust.iot.model.Token;

/**
 * 对Token进行操作的接口
 */
public interface TokenManager {


    /**
     * 生成一个Token
     * @param userId
     * @return
     */
    public Token createToken(int userId) throws Exception;

    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    public boolean checkToken(Token token);

    /**
     * 从字符串中解析Token
     * @param authentication
     * @return
     */
    public Token getToken(String authentication);

    /**
     * 清除Token
     * @param userId
     * @return
     */
    public int deleteToken(long userId);
}
