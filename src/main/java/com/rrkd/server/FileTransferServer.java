package com.rrkd.server;


import com.rrkd.util.FileTransferProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

public class FileTransferServer {

	private Logger log = Logger.getLogger(FileTransferServer.class);

	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();//boss线程池
		EventLoopGroup workerGroup = new NioEventLoopGroup();//worker线程池
		try {
			//ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024).childHandler(new FileChannelInitializer());
			log.info("bind port:"+port);
			//ServerBootstrap.bind(int)负责绑定端口，
			// 当这个方法执行后，ServerBootstrap就可以接受指定端口上的socket连接了。
			// 一个ServerBootstrap可以绑定多个端口。
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	public static void run1(){
		init();
		// 获取端口
		int port  = FileTransferProperties.getInt("port",8094);
		/*if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}*/
		try {
			new FileTransferServer().bind(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init(){
	//	try
	//	{
			//请把加载属性文件放在 加载日志配置的上面，因为读取日志输出的目录配置在 属性文件里面
			FileTransferProperties.load("classpath:systemConfig.properties");

			System.setProperty("WORKDIR", FileTransferProperties.getString("WORKDIR","/"));

	//		PropertyConfigurator.configure(new FileSystemResourceLoader().getResource("classpath:log4j.xml").getInputStream());

	/*	} catch (IOException e){
			e.printStackTrace();
		}*/
	}
}
