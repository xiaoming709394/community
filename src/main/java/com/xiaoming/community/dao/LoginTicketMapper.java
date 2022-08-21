package com.xiaoming.community.dao;

import com.xiaoming.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * 登录信息mapper接口
 *
 * @author 赵明城
 * @date 2022/8/20
 */
@Mapper
public interface LoginTicketMapper {

    /**
     * 插入登录信息
     *
     * @param loginTicket
     * @return
     */
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired)",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 通过登录凭证获取登录信息
     *
     * @param ticket
     * @return
     */
    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(@Param("ticket") String ticket);

    /**
     * 根据登录凭证更改登录状态
     * @param ticket
     * @param status
     * @return
     */
    @Update({
            "<script>",
            "update login_ticket set status = #{status} where ticket = #{ticket}",
            "<if test=\"ticket != null\">",
            "and 1=1",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
