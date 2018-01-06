package com.lvqingyang.iot.listener;

import com.lvqingyang.iot.bean.Reponse;

/**
 * 一句话功能描述
 * 功能详细描述
 *
 * @author Lv Qingyang
 * @date 2017/12/3
 * @email biloba12345@gamil.com
 * @github https://github.com/biloba123
 * @blog https://biloba123.github.io/
 */
public interface GetDataListener extends IotListener{
    void succ(Reponse r);
}
