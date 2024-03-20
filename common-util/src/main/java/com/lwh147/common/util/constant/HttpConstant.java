package com.lwh147.common.util.constant;

import java.nio.charset.StandardCharsets;

/**
 * web相关常量类
 *
 * @author lwh
 * @date 2021/11/17 14:57
 **/
public class HttpConstant {
    public static final String REQUEST_METHOD = "requestMethod";
    public static final String REQUEST_URL = "requestUrl";
    public static final String REQUEST_PARAM = "requestParam";
    public static final String REQUEST_BODY = "requestBody";
    public static final String RESPONSE_BODY = "responseBody";

    private HttpConstant() {}

    /**
     * 请求方式
     **/
    public static class Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";

        private Method() {}
    }

    /**
     * 请求、响应头部 key
     **/
    public static class Header {
        public static final String TOKEN = "Token";
        public static final String REFRESH_TOKEN = "Token-Refresh";
        public static final String ACCESS_TOKEN = "accessToken";
        public static final String TIMESTAMP = "Timestamp";
        public static final String VERSION = "Version";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String HOST = "Host";
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String CONNECTION = "Connection";

        private Header() {}
    }

    /**
     * 请求、响应体类型
     **/
    public static class ContentType {
        /**
         * 相比较其它类型的请求体，multipart 类型的请求体分为多个部分，而且每部分（part）
         * 都拥有单独的 Content-Type 属性，假如有两部分，一部分可以是二进制流格式数据，另一
         * 部分可以是普通的 key=value 格式的数据，也可以是其它媒体格式数据，还可以添加第三
         * 部分、第四部分等，是唯一比较特殊的请求体类型
         **/
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";

        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_XML = "application/xml";
        public static final String APPLICATION_JS = "application/javascript";
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        /**
         * 二进制流
         **/
        public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
        private static final String CHARSET_UTF_8 = ";charset=" + StandardCharsets.UTF_8;

        public static final String APPLICATION_JSON_CHARSET_UTF_8 = APPLICATION_JSON + CHARSET_UTF_8;
        public static final String APPLICATION_XML_CHARSET_UTF_8 = APPLICATION_XML + CHARSET_UTF_8;

        public static final String TEXT_PLAIN = "text/plain";
        public static final String TEXT_HTML = "text/html";

        public static final String IMAGE_JPG = "image/jpeg";
        public static final String IMAGE_PNG = "image/png";
        public static final String IMAGE_GIF = "image/gif";

        public static final String VIDEO_MP4 = "video/mpeg4";

        private ContentType() {}
    }
}