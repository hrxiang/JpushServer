package com.mb.jpush.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mb.jpush.common.DwzAjaxDone;
import com.mb.jpush.model.Logger;
import com.mb.jpush.service.LoggerService;
import com.mb.jpush.util.DateFormatUtil;
import com.mb.jpush.util.StringUtil;

@Controller
@RequestMapping("/logger")
public class LoggerController extends DwzAjaxDone {
	@Autowired
	private LoggerService loggerService;

	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		try {
			Map<String, Object> map = new TreeMap<String, Object>();
			String pgNum = request.getParameter("pageNum");// 跳转页
			String orderField = request.getParameter("orderField");// 排序字段
			String orderDirection = request.getParameter("orderDirection");// 排序方式
			String numPerPage = request.getParameter("numPerPage");
			String recipient_type = request.getParameter("recipient_type");
			String logger_type = request.getParameter("logger_type");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
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

			if (!StringUtil.isNullOrEmpty(recipient_type)) {
				map.put("recipient_type", recipient_type);
				request.setAttribute("recipient_type", recipient_type);
			} else {// 默认检索条件
				map.put("recipient_type", 0);
				request.setAttribute("recipient_type", 0);
			}

			if (!StringUtil.isNullOrEmpty(logger_type)) {
				map.put("logger_type", logger_type);
				request.setAttribute("logger_type", logger_type);
			} else {// 默认检索条件
				map.put("logger_type", 0);
				request.setAttribute("logger_type", 0);
			}

			if (!StringUtil.isNullOrEmpty(start_time)) {
				map.put("start_time", start_time);
				request.setAttribute("start_time", start_time);
			} else {
				map.put("start_time", DateFormatUtil.getYMD(-2));
				request.setAttribute("start_time", DateFormatUtil.getYMD(-2));
			}

			if (!StringUtil.isNullOrEmpty(end_time)) {
				map.put("end_time", end_time + " 23:59:59");
				request.setAttribute("end_time", end_time);
			} else {
				map.put("end_time", DateFormatUtil.getYMD(0) + " 23:59:59");
				request.setAttribute("end_time", DateFormatUtil.getYMD(0));
			}

			int totalCount = loggerService.queryLoggersCount(map);

			if (totalCount > 0) {
				map.put("start", (pageNum - 1) * pageSize);
				map.put("limit", pageSize);
				List<Logger> loggers = loggerService.queryLoggers(map);
				model.addAttribute("loggers", loggers);
			}
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("totalCount", totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logger/list";
	}

	@RequestMapping("/delete")
	public String delete(String[] ids) {
		try {
			loggerService.deleteLogger(ids);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForwardError("删除失败！" + "reason: [" + e.getMessage()
					+ "]", null);
		}

		return ajaxForwardSuccess("删除成功！", null);
	}
}
