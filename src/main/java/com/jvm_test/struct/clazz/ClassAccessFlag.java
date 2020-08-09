package com.jvm_test.struct.clazz;

import lombok.Data;

@Data
public class ClassAccessFlag {

    private int flag;
    private String name;
    private String flagName;
    private int order;

    public ClassAccessFlag(int flag,String flagName){
        this.flag = flag;
        this.flagName = flagName;
    }

    public ClassAccessFlag(int flag,String flagName,String name){
        this.flag = flag;
        this.flagName = flagName;
        this.name = name;
    }

    public ClassAccessFlag(int flag,String flagName,String name,int order){
        this.flag = flag;
        this.flagName = flagName;
        this.name = name;
        this.order = order;
    }

    @Override
    public String toString() {
        return "ClassAccessFlag{" +
                "name='" + name + '\'' +
                ", flagName='" + flagName + '\'' +
                ", order=" + order +
                '}';
    }
}
