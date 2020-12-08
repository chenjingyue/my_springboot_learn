package com.thread;

public class ThreadTest {
	
	static{
		System.loadLibrary("ThreadTestNative");
	}

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        threadTest.start0();
    }
	
	public void run() {
		System.out.println("i am a java run");
	}

    private native void start0();

}
