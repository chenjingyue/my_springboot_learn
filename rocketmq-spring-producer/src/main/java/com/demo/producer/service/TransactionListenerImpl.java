package com.demo.producer.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.util.Map;

@Slf4j
@RocketMQTransactionListener
class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return bollback, commit or unknown
        log.info("---开始执行本地事务---");
         Map<String, Object> payload = JSONObject.parseObject(new String((byte[])msg.getPayload()),Map.class) ;
         String id = (String) payload.get("id");
        if(StringUtils.equals(id, "100")){
            log.info("---事务回滚---");
            return RocketMQLocalTransactionState.ROLLBACK;
        } else if(StringUtils.equals(id, "200")) {
            log.info("---事务挂起---");
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // ... check transaction status and return bollback, commit or unknown
        log.info("---开始执行回调事务---");
        return RocketMQLocalTransactionState.COMMIT;
    }
}
