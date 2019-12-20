//참고: https://supawer0728.github.io/2019/04/04/spring-error-handling/
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    //WebRequest -> RequestAttributes
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        result.put("greeting", "Hello");
        return result;
    }
}

