package com.mb.jpush.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor {

	private Logger LOG = Logger.getLogger(CommonInterceptor.class);

	public CommonInterceptor() {
		// TODO Auto-generated constructor stub
	}

	/*private String mappingURL;// 利用正则映射到需要拦截的路径

	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}*/

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 
	 * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		LOG.info("==============执行顺序: 1、preHandle================");
		/*String url = request.getRequestURL().toString();
		if (mappingURL == null || url.matches(mappingURL)) {
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
			return false;
		}*/
		return true;
	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		LOG.info("==============执行顺序: 2、postHandle================");
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		LOG.info("==============执行顺序: 3、afterCompletion================");
	}

}
