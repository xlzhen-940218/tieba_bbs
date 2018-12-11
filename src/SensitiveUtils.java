
public class SensitiveUtils {
	// Sensitive 待过滤
	public static String start(String Sensitive) {
		if (Sensitive.contains("'"))
			Sensitive = Sensitive.replace("'", "\\'");

		// 使用默认单例（加载默认敏感词库）
		SensitiveFilter filter = SensitiveFilter.DEFAULT;
		// 进行过滤
		String filted = filter.filter(Sensitive, '*');

		// 如果未过滤，则返回输入的String引用
		if (Sensitive != filted) {
			// 句子中有敏感词
			System.out.println(filted);
		}

		return filted;
	}
}
