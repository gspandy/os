package n.core.entity.usertype;

import org.jadira.usertype.spi.shared.AbstractSingleColumnUserType;
import org.joda.time.DateTime;

/**
 * 
 * @author onsoul@qq.com
 * 2017年3月27日 下午4:46:22
 */
public class PersistentDateTimeAsMillisLong extends AbstractSingleColumnUserType<DateTime, Long, LongColumnDateTimeMapper> {

	private static final long serialVersionUID = -7473647080678292504L;

}
