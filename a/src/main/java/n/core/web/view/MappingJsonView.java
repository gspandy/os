package n.core.web.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import n.core.web.resolver.MessageResolver;

/**
 * Ajax json数据异常处理类
 * @author jt_wangshuiping @date 2017年1月10日
 *
 */
public class MappingJsonView {

	private static final Logger log = LoggerFactory.getLogger(MappingJsonView.class);
	
	/**
	 * 返回表单绑定数据异常提示
	 * @param object
	 * @return
	 */
	public static Map<String, Object> bindMsg(Object object){
		Map<String, Object> map = Maps.newHashMap();
		if(object instanceof BindingResult){
			BindingResult br  = (BindingResult)object;
			Set<Map<String, Object>> errorList = Sets.newHashSet();
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError error : errors) {
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("field", error.getField());
				attr.put("message", error.getDefaultMessage() != null ? error.getDefaultMessage() : MessageResolver.getMessage(error.getCode()));
				errorList.add(attr);
			}
			map.put("errors", errorList);
			log.info("返回表单数据绑定异常提示:" + errorList);
		}
		return map;
	}
}
