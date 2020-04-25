package com.learn.mymall.service.impl;

import com.learn.mymall.mongodb.document.MemberReadHistory;
import com.learn.mymall.mongodb.repository.MemberReadHistoryRepository;
import com.learn.mymall.service.MongoDBMemberReadHistoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: likj
 * @create: 2020-04-25 13:43
 * @description:
 * @program: mymall
 */
@Service
public class MongoDBMemberReadHistoryServiceImpl implements MongoDBMemberReadHistoryServiceI {


    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;



    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList=new ArrayList<>();
        for (String id :ids){
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryRepository.deleteAll(deleteList);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long meberId) {
        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(meberId);
    }
}
