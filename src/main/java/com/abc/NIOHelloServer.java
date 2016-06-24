package com.abc;
 
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 * ========================================================
 * 日 期：2016年6月24日 上午11:15:55
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：基于非阻塞IO（NIO）的服务端。
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class NIOHelloServer {
	
    public static final int SERVER_PORT = 8090;
 
    public void startServer() {
        try {
            // 设置传输通道
        	TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(SERVER_PORT);
        	
        	// 异步IO，需要使用TFramedTransport，它将分块缓存读取。  
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            
            // 设置协议(使用高密度二进制协议 )
            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
            
            // 设置处理器
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
            
            // 创建服务器
            TServer server = new TThreadedSelectorServer(
            		new Args(serverTransport)
            		.transportFactory(transportFactory)  
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
        NIOHelloServer server = new NIOHelloServer();
        server.startServer();
    }
 
}

