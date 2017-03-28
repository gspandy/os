# os
一个力求精简的 企业级应用框架 整合版

	此项目愿景是完成一个企业可用的分布式基础架构，主要目标完成业务分布式、数据流分布式
	
#框架组成
 
	Spring boot                     				 基础框架
	JPA								 数据模型的映射
	DUBBO								 后端业务分布式处理
	Spring WEB      						 通过json接口与其它前端框架整合提供数据
	双通道socket(IOSocket\WEBSocket)				       可以通过两种方式连接应用
	Protobuf   							 统一的数据流序列化方式

#目录说明

	os 
	|-os-api  通用的模型、接口声明 
	|-os-p    服务提供
	|-os-c    服务消费、WEB接口提供、socket接口提供


