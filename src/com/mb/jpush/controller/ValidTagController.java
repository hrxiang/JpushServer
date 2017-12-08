package com.mb.jpush.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mb.jpush.common.DwzAjaxDone;
import com.mb.jpush.model.Error;
import com.mb.jpush.model.Result;
import com.mb.jpush.model.Success;
import com.mb.jpush.model.Tags;
import com.mb.jpush.model.ValidTag;
import com.mb.jpush.service.ValidTagService;
import com.mb.jpush.util.GsonUtils;
import com.mb.jpush.util.StringUtil;

/**
 * 有效标签
 * */
@Controller
@RequestMapping("/vtag")
public class ValidTagController extends DwzAjaxDone {
	protected static final Logger LOG = LoggerFactory
			.getLogger(ValidTagController.class);
	@Autowired
	private ValidTagService validTagService;

	/**
	 * @param {"registration_id":"","tags":[]}
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/setTags", method = RequestMethod.POST)
	public Result setTags(HttpServletRequest request) {
		Result response = null;
		List<ValidTag> validTags = new ArrayList<ValidTag>();
		try {
			String jsonStr = StringUtil.convertStreamToString(request
					.getInputStream());
			if (StringUtil.isNullOrEmpty(jsonStr)) {
				response = new Error("参数错误,参数格式:{\"registration_id\":\"\",\"tags\":[]}");
				return response;
			}
			Tags tags = new GsonUtils<String, Tags>().JsonStrToObject(jsonStr,
					Tags.class);
			validTagService.deleteTagByRegistration_id(tags
					.getRegistration_id());
			for (String tag_ : tags.getTags()) {
				validTags.add(new ValidTag(tag_, tags.getRegistration_id()));
			}
			validTagService.addTags(validTags);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Error(e.getMessage());
			return response;
		}
		response = new Success("");
		return response;
	}

	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		try {
			Map<String, Object> map = new TreeMap<String, Object>();
			int pageNum = 1;// 页号
			int numPerPage = 20;// 每页显示数量
			if (!StringUtil.isNullOrEmpty(request.getParameter("pageNum"))) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}
			if (!StringUtil.isNullOrEmpty(request.getParameter("numPerPage"))) {
				numPerPage = Integer.parseInt(request
						.getParameter("numPerPage"));
			}
			int totalCount = validTagService.queryTag_sCount();

			if (totalCount > 0) {
				map.put("start", (pageNum - 1) * numPerPage);
				map.put("limit", numPerPage);
				List<String> tags = validTagService.queryTag_s(map);
				model.addAttribute("tags", tags);
			}
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("numPerPage", numPerPage);
			model.addAttribute("totalCount", totalCount);
			LOG.info("pageNo ---> " + pageNum);
			LOG.info("pageSize ---> " + numPerPage);
			LOG.info("totalCount ---> " + totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tag/valid_tags";
	}

	@RequestMapping("/delete")
	public String delete(String tag_) {
		try {
			validTagService.deleteTagByTag_(tag_);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tag/valid_tags";
	}
}
