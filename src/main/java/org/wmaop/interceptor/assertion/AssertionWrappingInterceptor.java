package org.wmaop.interceptor.assertion;

import org.wmaop.aop.chainprocessor.InterceptResult;
import org.wmaop.aop.chainprocessor.Interceptor;
import org.wmaop.aop.pipeline.FlowPosition;

import com.wm.data.IData;

public class AssertionWrappingInterceptor implements Interceptor, Assertable {

	private int invocationCount;
	private final Interceptor wrappedInterceptor;
	private String name;
	
	public AssertionWrappingInterceptor(Interceptor wrappedInterceptor, String name) {
		this.wrappedInterceptor =wrappedInterceptor;
		this.name = name;
	}
	
	@Override
	public InterceptResult intercept(FlowPosition flowPosition, IData idata) {
		InterceptResult ir = wrappedInterceptor.intercept(flowPosition, idata);
		invocationCount++;
		return ir;
	}

	@Override
	public int getInvokeCount() {
		return invocationCount;
	}

	@Override
	public String getName() {
		return name;
	}

}
