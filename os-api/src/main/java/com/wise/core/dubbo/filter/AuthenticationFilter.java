package com.wise.core.dubbo.filter;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.wise.core.dubbo.RpcContextAttachment;

public class AuthenticationFilter implements Filter {

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		System.out.println(1111111);
		RpcContext.getContext().setAttachment(RpcContextAttachment.ATTR_NAME_USERID, "1");
		RpcContext.getContext().setAttachment(RpcContextAttachment.ATTR_NAME_USERNAME, "admin");
		Result result = invoker.invoke(invocation);
		return result;
	}

}
