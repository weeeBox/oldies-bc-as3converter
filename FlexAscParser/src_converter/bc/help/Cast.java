package bc.help;

public class Cast
{
	@SuppressWarnings("unchecked")
	public static <T> T tryCast(Object obj, Class<? extends T> cls)
	{
		return obj != null && cls.isInstance(obj) ? (T)obj : null;
	}
}
