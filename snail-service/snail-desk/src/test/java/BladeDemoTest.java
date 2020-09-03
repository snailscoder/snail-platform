import org.junit.Test;
import org.junit.runner.RunWith;
import com.snailscoder.core.test.BladeBootTest;
import com.snailscoder.core.test.BladeSpringRunner;
import com.snailscoder.desk.DeskApplication;
import com.snailscoder.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Blade单元测试
 *
 * @author snailscoder
 */
@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = DeskApplication.class)
@BladeBootTest(appName = "blade-desk", profile = "test", enableLoader = true)
public class BladeDemoTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
