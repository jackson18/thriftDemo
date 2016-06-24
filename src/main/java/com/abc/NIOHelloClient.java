package com.abc;
 
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
 
/**
 * ========================================================
 * 日 期：2016年6月24日 上午11:16:51
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：调用非阻塞IO（NIO）服务的客户端
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class NIOHelloClient {
 
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;
 
    public void startClient(String userName) throws Exception {
        	// 设置传输通道
        	TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
        	
            // 设置协议（使用高密度二进制协议,要和服务端一致）
            TProtocol protocol = new TCompactProtocol(transport);
            
            // 创建client
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            
            // 开启通道
            transport.open();
            
            // 客户端调用
            String result = client.sayHello(userName);
            System.out.println("Thrify client result =: " + result);
            
            // 关闭通道
            transport.close();
    }
 
    public static void main(String[] args) {
        try {
			NIOHelloClient client = new NIOHelloClient();
			client.startClient("Michael");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
}

