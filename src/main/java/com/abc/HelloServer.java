package com.abc;
 
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
 
/**
 * ========================================================
 * 日 期：2016年6月24日 上午11:15:55
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：服务端，发布（阻塞式IO + 多线程处理）服务。
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class HelloServer {
	
    public static final int SERVER_PORT = 8090;
 
    public void startServer() {
        try {
            // 设置传输通道
        	TServerTransport serverTransport = new TServerSocket(SERVER_PORT);
            
            // 设置协议
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            
            // 设置处理器
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
            
            // 创建服务器
            TServer server = new TThreadPoolServer(  
                new Args(serverTransport)  
                .protocolFactory(protocolFactory)  
                .processor(tprocessor)  
            ); 
            
            // 开启服务
            System.out.println("HelloWorld TSimpleServer start on port " + SERVER_PORT + "....");
            server.serve();
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        HelloServer server = new HelloServer();
        server.startServer();
    }
 
}

