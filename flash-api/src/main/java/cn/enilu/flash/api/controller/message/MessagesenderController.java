package cn.enilu.flash.api.controller.message;

import cn.enilu.flash.bean.entity.message.MessageSender;
import cn.enilu.flash.service.message.MessagesenderService;

import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.GunsException;
import cn.enilu.flash.bean.vo.front.Rets;

import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.ToolUtil;
import cn.enilu.flash.utils.factory.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message/sender")
public class MessagesenderController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessagesenderService messagesenderService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<MessageSender> page = new PageFactory<MessageSender>().defaultPage();
		page = messagesenderService.findPage(page, Maps.newHashMap());
		page.setRecords(page.getRecords());
		return Rets.success(page);
	}
	@RequestMapping(value = "/queryAll",method = RequestMethod.GET)
	public Object queryAll() {

		return Rets.success(messagesenderService.queryAll());
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑消息发送者", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute MessageSender tMessageSender){
		messagesenderService.save(tMessageSender);
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除消息发送者", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (ToolUtil.isEmpty(id)) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}

		Boolean result = messagesenderService.delete(id);
		if(result){
			return Rets.success();
		}else{
			return Rets.failure("有模板使用该发送器，无法删除");
		}

	}
}