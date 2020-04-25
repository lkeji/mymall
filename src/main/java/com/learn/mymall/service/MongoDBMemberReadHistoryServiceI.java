package com.learn.mymall.service;

import com.learn.mymall.mongodb.document.MemberReadHistory;

import javax.swing.*;
import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-25 13:39
 * @description: 会员浏览记录管理Service
 * @program: mymall
 */
public interface MongoDBMemberReadHistoryServiceI {
    /*
    * 生成浏览记录
    * */
    int create(MemberReadHistory memberReadHistory);
    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);
    /*
    * 获取用户浏览历史记录
    * */
    List<MemberReadHistory> list(Long meberId);

}
