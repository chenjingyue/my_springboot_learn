package com.rocketmq;

import java.util.concurrent.CompletableFuture;

public class SyncRequest {

    private String status;
    private CompletableFuture<String> flushOKFuture = new CompletableFuture<>();

    public SyncRequest(String status){
        this.status = status;
    }



    public String getStatus() {
        return status;
    }

    public void wakeupCustomer(String status) {
        flushOKFuture.complete(status);
    }

    public CompletableFuture<String> future() {
        return flushOKFuture;
    }
}
