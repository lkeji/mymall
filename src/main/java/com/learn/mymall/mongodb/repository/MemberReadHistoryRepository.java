package com.learn.mymall.mongodb.repository;

import com.learn.mymall.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-25 13:28
 * @description: 用户商品浏览历史记录
 * @program: mymall
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {

    /*
    * 根据会员ID按照时间倒叙获取浏览记录
    * */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
