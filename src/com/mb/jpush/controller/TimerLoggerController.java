package com.mb.jpush.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mb.jpush.common.DwzAjaxDone;
import com.mb.jpush.common.Timers;
import com.mb.jpush.model.TimerLogger;
import com.mb.jpush.service.TimerLoggerService;
import com.mb.jpush.util.StringUtil;

@Controller
@RequestMapping("/timerlogger")
public class TimerLoggerController extends DwzAjaxDone {
	protected static final Logger LOG = LoggerFactory
			.getLogger(TimerLoggerController.class);
	@Autowired
	private TimerLoggerService timerLoggerService;

	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		try {
			Map<String, Object> map = new TreeMap<String, Object>();
			String pgNum = request.getParameter("pageNum");// 跳转页
			String orderField = request.getParameter("orderField");// 排序字段
			String orderDirection = request.getParameter("orderDirection");// 排序方式
			String numPerPage = request.getParameter("numPerPage");
			String logger_type = request.getParameter("logger_type");
			int pageNum = 1;
			int pageSize = 20;

			if (!StringUtil.isNullOrEmpty(pgNum)) {
				pageNum = Integer.parseInt(pgNum);
			}
			if (!StringUtil.isNullOrEmpty(numPerPage)) {
				pageSize = Integer.parseInt(numPerPage);
				request.setAttribute("numPerPage", pageSize);
			}
			if (!StringUtil.isNullOrEmpty(orderField)) {
				map.put("orderField", orderField);
				request.setAttribute("orderField", orderField);
			}
			if (!StringUtil.isNullOrEmpty(orderDirection)) {
				map.put("orderDirection", orderDirection);
				request.setAttribute("orderDirection", orderDirection);
			}

			if (!StringUtil.isNullOrEmpty(logger_type)) {
				map.put("logger_type", logger_type);
				request.setAttribute("logger_type", logger_type.trim());
			} else {// 默认检索条件
				map.put("logger_type", 0);
				request.setAttribute("logger_type", 0);
			}
			System.out.println("-------------------------------");
			System.out.println("logger_type: " + logger_type);
			System.out.println("-------------------------------");
			int totalCount = timerLoggerService.queryTimerLoggersCount(map);

			if (totalCount > 0) {
				map.put("start", (pageNum - 1) * pageSize);
				map.put("limit", pageSize);
				List<TimerLogger> loggers = timerLoggerService
						.queryTimerLoggers(map);
				model.addAttribute("loggers", loggers);
			}
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("totalCount", totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logger/timer_list";
	}

	@RequestMapping("/delete")
	public String delete(String[] time) {
		try {
			timerLoggerService.deleteTimerLogger(time);
			LOG.info("定时日志删除成功----->" + Arrays.toString(time));
			for (int i = 0; i < time.length; i++) {
				Timers.cancel(time[i]);
			}
			LOG.error("定时器集合：" + Timers.getTimers());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForwardError("删除失败！" + "reason: [" + e.getMessage()
					+ "]", null);
		}

		return ajaxForwardSuccess("删除成功！", null);
	}
}
