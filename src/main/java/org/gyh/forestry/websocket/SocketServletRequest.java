/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gyh.forestry.websocket;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.*;
import org.springframework.web.util.UrlPathHelper;

import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mock implementation of the {@link HttpServletRequest} interface.
 *
 * <p>The default, preferred {@link Locale} for the <em>server</em> mocked by this request
 * is {@link Locale#ENGLISH}. This value can be changed via {@link #addPreferredLocale}
 * or {@link #setPreferredLocales}.
 *
 * <p>As of Spring 6.0, this set of mocks is designed on a Servlet 6.0 baseline.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Rick Evans
 * @author Mark Fisher
 * @author Chris Beams
 * @author Sam Brannen
 * @author Brian Clozel
 * @since 1.0.2
 */
public class SocketServletRequest implements HttpServletRequest {

    private static final String HTTP = "http";

    private static final String HTTPS = "https";

    private static final String CHARSET_PREFIX = "charset=";

    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    /**
     * Date formats as specified in the HTTP RFC.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    private static final String[] DATE_FORMATS = new String[]{
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM dd HH:mm:ss yyyy"
    };


    // ---------------------------------------------------------------------
    // Public constants
    // ---------------------------------------------------------------------

    /**
     * The default protocol: 'HTTP/1.1'.
     *
     * @since 4.3.7
     */
    public static final String DEFAULT_PROTOCOL = "HTTP/1.1";

    /**
     * The default scheme: 'http'.
     *
     * @since 4.3.7
     */
    public static final String DEFAULT_SCHEME = HTTP;

    /**
     * The default server address: '127.0.0.1'.
     */
    public static final String DEFAULT_SERVER_ADDR = "127.0.0.1";

    /**
     * The default server name: 'localhost'.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";

    /**
     * The default server port: '80'.
     */
    public static final int DEFAULT_SERVER_PORT = 80;

    /**
     * The default remote address: '127.0.0.1'.
     */
    public static final String DEFAULT_REMOTE_ADDR = "127.0.0.1";

    /**
     * The default remote host: 'localhost'.
     */
    public static final String DEFAULT_REMOTE_HOST = "localhost";


    // ---------------------------------------------------------------------
    // Lifecycle properties
    // ---------------------------------------------------------------------

    private boolean active = true;


    // ---------------------------------------------------------------------
    // ServletRequest properties
    // ---------------------------------------------------------------------

    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @Nullable
    private String characterEncoding;

    @Nullable
    private byte[] content;

    @Nullable
    private String contentType;

    @Nullable
    private ServletInputStream inputStream;

    @Nullable
    private BufferedReader reader;

    private final Map<String, String[]> parameters = new LinkedHashMap<>(16);

    private String protocol = DEFAULT_PROTOCOL;

    private String scheme = DEFAULT_SCHEME;

    private String serverName = DEFAULT_SERVER_NAME;

    private int serverPort = DEFAULT_SERVER_PORT;

    private String remoteAddr = DEFAULT_REMOTE_ADDR;

    private String remoteHost = DEFAULT_REMOTE_HOST;

    /**
     * List of locales in descending order.
     */
    private final LinkedList<Locale> locales = new LinkedList<>();

    private boolean secure = false;

    private int remotePort = DEFAULT_SERVER_PORT;

    private String localName = DEFAULT_SERVER_NAME;

    private String localAddr = DEFAULT_SERVER_ADDR;

    private int localPort = DEFAULT_SERVER_PORT;


    private DispatcherType dispatcherType = DispatcherType.REQUEST;


    // ---------------------------------------------------------------------
    // HttpServletRequest properties
    // ---------------------------------------------------------------------

    @Nullable
    private String authType;

    private final Map<String, HeaderValueHolder> headers = new LinkedCaseInsensitiveMap<>();

    @Nullable
    private String method;

    private String contextPath = "";

    @Nullable
    private String remoteUser;

    private final Set<String> userRoles = new HashSet<>();

    @Nullable
    private Principal userPrincipal;

    @Nullable
    private String requestURI;

    private String servletPath = "";

    private boolean requestedSessionIdValid = true;

    private boolean requestedSessionIdFromCookie = true;

    private boolean requestedSessionIdFromURL = false;

    private final MultiValueMap<String, Part> parts = new LinkedMultiValueMap<>();


    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------


    /**
     * Create a new {@code MockHttpServletRequest} with a default
     * {@link MockServletContext}.
     *
     * @param method     the request method (may be {@code null})
     * @param requestURI the request URI (may be {@code null})
     * @see #setMethod
     * @see #SocketServletRequest(ServletContext, String, String)
     */
    public SocketServletRequest(@Nullable String method, @Nullable String requestURI) {
        this(null, method, requestURI);
    }

    /**
     * Create a new {@code MockHttpServletRequest} with the supplied {@link ServletContext},
     * {@code method}, and {@code requestURI}.
     * <p>The preferred locale will be set to {@link Locale#ENGLISH}.
     *
     * @param servletContext the ServletContext that the request runs in (may be
     *                       {@code null} to use a default {@link MockServletContext})
     * @param method         the request method (may be {@code null})
     * @param requestURI     the request URI (may be {@code null})
     * @see #setMethod
     * @see MockServletContext
     */
    public SocketServletRequest(@Nullable ServletContext servletContext, @Nullable String method, @Nullable String requestURI) {
        this.method = method;
        this.requestURI = requestURI;
        this.locales.add(Locale.CHINESE);
    }


    // ---------------------------------------------------------------------
    // Lifecycle methods
    // ---------------------------------------------------------------------

    /**
     * Return the ServletContext that this request is associated with. (Not
     * available in the standard HttpServletRequest interface for some reason.)
     */
    @Override
    public ServletContext getServletContext() {
        return null;
    }


    /**
     * Check whether this request is still active (that is, not completed yet),
     * throwing an IllegalStateException if not active anymore.
     */
    protected void checkActive() throws IllegalStateException {
        Assert.state(this.active, "Request is not active anymore");
    }


    // ---------------------------------------------------------------------
    // ServletRequest interface
    // ---------------------------------------------------------------------

    @Override
    @Nullable
    public Object getAttribute(String name) {
        checkActive();
        return this.attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        checkActive();
        return Collections.enumeration(new LinkedHashSet<>(this.attributes.keySet()));
    }

    @Override
    @Nullable
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public void setCharacterEncoding(@Nullable String characterEncoding) {
        this.characterEncoding = characterEncoding;
        updateContentTypeHeader();
    }

    private void updateContentTypeHeader() {
        if (StringUtils.hasLength(this.contentType)) {
            String value = this.contentType;
            if (StringUtils.hasLength(this.characterEncoding) && !this.contentType.toLowerCase().contains(CHARSET_PREFIX)) {
                value += ';' + CHARSET_PREFIX + this.characterEncoding;
            }
            doAddHeaderValue(HttpHeaders.CONTENT_TYPE, value, true);
        }
    }

    /**
     * Set the content of the request body as a byte array.
     * <p>If the supplied byte array represents text such as XML or JSON, the
     * {@link #setCharacterEncoding character encoding} should typically be
     * set as well.
     *
     * @see #setCharacterEncoding(String)
     */
    public void setContent(@Nullable byte[] content) {
        this.content = content;
        this.inputStream = null;
        this.reader = null;
    }

    @Override
    public int getContentLength() {
        return (this.content != null ? this.content.length : -1);
    }

    @Override
    public long getContentLengthLong() {
        return getContentLength();
    }

    public void setContentType(@Nullable String contentType) {
        this.contentType = contentType;
        if (contentType != null) {
            try {
                MediaType mediaType = MediaType.parseMediaType(contentType);
                if (mediaType.getCharset() != null) {
                    this.characterEncoding = mediaType.getCharset().name();
                }
            } catch (IllegalArgumentException ex) {
                // Try to get charset value anyway
                contentType = contentType.toLowerCase();
                int charsetIndex = contentType.indexOf(CHARSET_PREFIX);
                if (charsetIndex != -1) {
                    this.characterEncoding = contentType.substring(charsetIndex + CHARSET_PREFIX.length());
                }
            }
            updateContentTypeHeader();
        }
    }

    @Override
    @Nullable
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() {
        if (this.inputStream != null) {
            return this.inputStream;
        } else if (this.reader != null) {
            throw new IllegalStateException(
                    "Cannot call getInputStream() after getReader() has already been called for the current request");
        }

        this.inputStream = (this.content != null ?
                new DelegatingServletInputStream(new ByteArrayInputStream(this.content)) :
                new DelegatingServletInputStream(InputStream.nullInputStream()));
        return this.inputStream;
    }

    @Override
    @Nullable
    public String getParameter(String name) {
        Assert.notNull(name, "Parameter name must not be null");
        String[] arr = this.parameters.get(name);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    @Nullable
    public String[] getParameterValues(String name) {
        Assert.notNull(name, "Parameter name must not be null");
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.parameters);
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }


    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public String getServerName() {
        String rawHostHeader = getHeader(HttpHeaders.HOST);
        String host = rawHostHeader;
        if (host != null) {
            host = host.trim();
            if (host.startsWith("[")) {
                int indexOfClosingBracket = host.indexOf(']');
                Assert.state(indexOfClosingBracket > -1, () -> "Invalid Host header: " + rawHostHeader);
                host = host.substring(0, indexOfClosingBracket + 1);
            } else if (host.contains(":")) {
                host = host.substring(0, host.indexOf(':'));
            }
            return host;
        }

        // else
        return this.serverName;
    }

    @Override
    public int getServerPort() {
        String rawHostHeader = getHeader(HttpHeaders.HOST);
        String host = rawHostHeader;
        if (host != null) {
            host = host.trim();
            int idx;
            if (host.startsWith("[")) {
                int indexOfClosingBracket = host.indexOf(']');
                Assert.state(indexOfClosingBracket > -1, () -> "Invalid Host header: " + rawHostHeader);
                idx = host.indexOf(':', indexOfClosingBracket);
            } else {
                idx = host.indexOf(':');
            }
            if (idx != -1) {
                return Integer.parseInt(host, idx + 1, host.length(), 10);
            }
        }

        // else
        return this.serverPort;
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException {
        if (this.reader != null) {
            return this.reader;
        } else if (this.inputStream != null) {
            throw new IllegalStateException(
                    "Cannot call getReader() after getInputStream() has already been called for the current request");
        }

        if (this.content != null) {
            InputStream sourceStream = new ByteArrayInputStream(this.content);
            Reader sourceReader = (this.characterEncoding != null) ?
                    new InputStreamReader(sourceStream, this.characterEncoding) :
                    new InputStreamReader(sourceStream);
            this.reader = new BufferedReader(sourceReader);
        } else {
            this.reader = new BufferedReader(new StringReader(""));
        }
        return this.reader;
    }

    @Override
    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    @Override
    public void setAttribute(String name, @Nullable Object value) {
        checkActive();
        Assert.notNull(name, "Attribute name must not be null");
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            this.attributes.remove(name);
        }
    }

    @Override
    public void removeAttribute(String name) {
        checkActive();
        Assert.notNull(name, "Attribute name must not be null");
        this.attributes.remove(name);
    }


    /**
     * Return the first preferred {@linkplain Locale locale} configured
     * in this mock request.
     * <p>If no locales have been explicitly configured, the default,
     * preferred {@link Locale} for the <em>server</em> mocked by this
     * request is {@link Locale#ENGLISH}.
     * <p>In contrast to the Servlet specification, this mock implementation
     * does <strong>not</strong> take into consideration any locales
     * specified via the {@code Accept-Language} header.
     *
     * @see ServletRequest#getLocale()
     */
    @Override
    public Locale getLocale() {
        return this.locales.getFirst();
    }

    /**
     * Return an {@linkplain Enumeration enumeration} of the preferred
     * {@linkplain Locale locales} configured in this mock request.
     * <p>If no locales have been explicitly configured, the default,
     * preferred {@link Locale} for the <em>server</em> mocked by this
     * request is {@link Locale#ENGLISH}.
     * <p>In contrast to the Servlet specification, this mock implementation
     * does <strong>not</strong> take into consideration any locales
     * specified via the {@code Accept-Language} header.
     *
     * @see ServletRequest#getLocales()
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(this.locales);
    }


    /**
     * Return {@code true} if the {@link #setSecure secure} flag has been set
     * to {@code true} or if the {@link #getScheme scheme} is {@code https}.
     *
     * @see ServletRequest#isSecure()
     */
    @Override
    public boolean isSecure() {
        return (this.secure || HTTPS.equalsIgnoreCase(this.scheme));
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }


    @Override
    public int getRemotePort() {
        return this.remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    @Override
    public String getLocalName() {
        return this.localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String getLocalAddr() {
        return this.localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    @Override
    public int getLocalPort() {
        return this.localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public AsyncContext startAsync() {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest request, @Nullable ServletResponse response) {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }


    @Override
    public boolean isAsyncSupported() {
        return false;
    }


    @Override
    @Nullable
    public AsyncContext getAsyncContext() {
        return null;
    }


    @Override
    public DispatcherType getDispatcherType() {
        return this.dispatcherType;
    }

    @Override
    public String getRequestId() {
        return "";
    }

    @Override
    public String getProtocolRequestId() {
        return "";
    }

    @Override
    public ServletConnection getServletConnection() {
        return new ServletConnection() {
            @Override
            public String getConnectionId() {
                return SocketServletRequest.this.getRequestId();
            }

            @Override
            public String getProtocol() {
                return SocketServletRequest.this.getProtocol();
            }

            @Override
            public String getProtocolConnectionId() {
                return SocketServletRequest.this.getProtocolRequestId();
            }

            @Override
            public boolean isSecure() {
                return SocketServletRequest.this.isSecure();
            }
        };
    }


    // ---------------------------------------------------------------------
    // HttpServletRequest interface
    // ---------------------------------------------------------------------


    @Override
    @Nullable
    public String getAuthType() {
        return this.authType;
    }

    @Override
    @Nullable
    public Cookie[] getCookies() {
        return null;
    }


    private void doAddHeaderValue(String name, @Nullable Object value, boolean replace) {
        HeaderValueHolder header = this.headers.get(name);
        Assert.notNull(value, "Header value must not be null");
        if (header == null || replace) {
            header = new HeaderValueHolder();
            this.headers.put(name, header);
        }
        if (value instanceof Collection<?> collection) {
            header.addValues(collection);
        } else if (value.getClass().isArray()) {
            header.addValueArray(value);
        } else {
            header.addValue(value);
        }
    }


    /**
     * Return the long timestamp for the date header with the given {@code name}.
     * <p>If the internal value representation is a String, this method will try
     * to parse it as a date using the supported date formats:
     * <ul>
     * <li>"EEE, dd MMM yyyy HH:mm:ss zzz"</li>
     * <li>"EEE, dd-MMM-yy HH:mm:ss zzz"</li>
     * <li>"EEE MMM dd HH:mm:ss yyyy"</li>
     * </ul>
     *
     * @param name the header name
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    @Override
    public long getDateHeader(String name) {
        HeaderValueHolder header = this.headers.get(name);
        Object value = (header != null ? header.getValue() : null);
        if (value instanceof Date date) {
            return date.getTime();
        } else if (value instanceof Number number) {
            return number.longValue();
        } else if (value instanceof String str) {
            return parseDateHeader(name, str);
        } else if (value != null) {
            throw new IllegalArgumentException(
                    "Value for header '" + name + "' is not a Date, Number, or String: " + value);
        } else {
            return -1L;
        }
    }

    private long parseDateHeader(String name, String value) {
        for (String dateFormat : DATE_FORMATS) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
            simpleDateFormat.setTimeZone(GMT);
            try {
                return simpleDateFormat.parse(value).getTime();
            } catch (ParseException ex) {
                // ignore
            }
        }
        throw new IllegalArgumentException("Cannot parse date value '" + value + "' for '" + name + "' header");
    }

    @Override
    @Nullable
    public String getHeader(String name) {
        HeaderValueHolder header = this.headers.get(name);
        return (header != null ? header.getStringValue() : null);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        HeaderValueHolder header = this.headers.get(name);
        return (header != null ? Collections.enumeration(header.getStringValues()) :
                Collections.emptyEnumeration());
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {
        HeaderValueHolder header = this.headers.get(name);
        Object value = (header != null ? header.getValue() : null);
        if (value instanceof Number number) {
            return number.intValue();
        } else if (value instanceof String str) {
            return Integer.parseInt(str);
        } else if (value != null) {
            throw new NumberFormatException("Value for header '" + name + "' is not a Number: " + value);
        } else {
            return -1;
        }
    }

    public void setMethod(@Nullable String method) {
        this.method = method;
    }

    @Override
    @Nullable
    public String getMethod() {
        return this.method;
    }


    @Override
    @Nullable
    public String getPathInfo() {
        return null;
    }

    @Override
    @Nullable
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }


    @Override
    @Nullable
    public String getQueryString() {
        return null;
    }


    @Override
    @Nullable
    public String getRemoteUser() {
        return this.remoteUser;
    }


    @Override
    public boolean isUserInRole(String role) {
        return (this.userRoles.contains(role));
    }


    @Override
    @Nullable
    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public void setUserPrincipal(@Nullable Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    @Nullable
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    @Nullable
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        String scheme = getScheme();
        String server = getServerName();
        int port = getServerPort();
        String uri = getRequestURI();

        StringBuffer url = new StringBuffer(scheme).append("://").append(server);
        if (port > 0 && ((HTTP.equalsIgnoreCase(scheme) && port != 80) ||
                (HTTPS.equalsIgnoreCase(scheme) && port != 443))) {
            url.append(':').append(port);
        }
        if (StringUtils.hasText(uri)) {
            url.append(uri);
        }
        return url;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }


    @Override
    @Nullable
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    @Nullable
    public HttpSession getSession() {
        return getSession(true);
    }

    /**
     * The implementation of this (Servlet 3.1+) method calls
     * {@link MockHttpSession#changeSessionId()} if the session is a mock session.
     * Otherwise it simply returns the current session id.
     *
     * @since 4.0.3
     */
    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return this.requestedSessionIdValid;
    }


    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdFromCookie;
    }


    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdFromURL;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void login(String username, String password) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void logout() throws ServletException {
        this.userPrincipal = null;
        this.remoteUser = null;
        this.authType = null;
    }


    @Override
    @Nullable
    public Part getPart(String name) {
        return this.parts.getFirst(name);
    }

    @Override
    public Collection<Part> getParts() {
        List<Part> result = new LinkedList<>();
        for (List<Part> list : this.parts.values()) {
            result.addAll(list);
        }
        return result;
    }

    @Override
    public HttpServletMapping getHttpServletMapping() {
        return new MockHttpServletMapping("", "", "", determineMappingMatch());
    }

    /**
     * Best effort to detect a Servlet path mapping, e.g. {@code "/foo/*"}, by
     * checking whether the length of requestURI > contextPath + servletPath.
     * This helps {@link org.springframework.web.util.ServletRequestPathUtils}
     * to take into account the Servlet path when parsing the requestURI.
     */
    @Nullable
    private MappingMatch determineMappingMatch() {
        if (StringUtils.hasText(this.requestURI) && StringUtils.hasText(this.servletPath)) {
            String path = UrlPathHelper.defaultInstance.getRequestUri(this);
            String prefix = this.contextPath + this.servletPath;
            return (path.startsWith(prefix) && (path.length() > prefix.length()) ? MappingMatch.PATH : null);
        }
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

}
