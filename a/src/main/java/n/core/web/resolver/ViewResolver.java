package n.core.web.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ViewResolver {
	
	public static String getView(String path) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String theme = RequestContextUtils.getThemeResolver(request).resolveThemeName(request);
		//String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		//return theme + "/" + locale + "/" + MessageResolver.getMessage(name);
		return theme + "/" + path;
	}

}
