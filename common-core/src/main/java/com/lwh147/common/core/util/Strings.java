package com.lwh147.common.core.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 借用 Apache Commons Lang 的 String 工具类
 * <p>
 * 本框架作者比较喜欢该工具类的实现，简单小巧实用，但由于源工具类在 org.apache.logging.log4j 的 log4j-api 下，而本框架日志使
 * 用 Slf4j，所以直接拿来用了
 *
 * @author Apache
 * @date 2021/11/10 16:15
 * @see <a href="http://commons.apache.org/proper/commons-lang/">Apache Commons Lang</a>
 **/
public final class Strings {
    private static final ThreadLocal<StringBuilder> TEMP_STR = ThreadLocal.withInitial(StringBuilder::new);

    private Strings() {
    }

    /**
     * Returns a double quoted string.
     *
     * @param str a String
     * @return {@code "str"}
     */
    public static String dquote(final String str) {
        return "\"" + str + "\"";
    }

    /**
     * Checks if a String is blank. A blank string is one that is either
     * {@code null}, empty, or all characters are {@link Character#isWhitespace(char)}.
     *
     * @param s the String to check, may be {@code null}
     * @return {@code true} if the String is {@code null}, empty, or all characters are {@link Character#isWhitespace(char)}
     */
    public static boolean isBlank(final String s) {
        if (s != null && !s.isEmpty()) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (!Character.isWhitespace(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     *
     * <pre>
     * Strings.isEmpty(null)      = true
     * Strings.isEmpty("")        = true
     * Strings.isEmpty(" ")       = false
     * Strings.isEmpty("bob")     = false
     * Strings.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is
     * available in isBlank().
     * </p>
     *
     * <p>
     * Copied from Apache Commons Lang org.apache.commons.lang3.StringUtils.isEmpty(CharSequence)
     * </p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if a String is not blank. The opposite of {@link #isBlank(String)}.
     *
     * @param s the String to check, may be {@code null}
     * @return {@code true} if the String is non-{@code null} and has content after being trimmed.
     */
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty ("") and not null.
     * </p>
     *
     * <pre>
     * Strings.isNotEmpty(null)      = false
     * Strings.isNotEmpty("")        = false
     * Strings.isNotEmpty(" ")       = true
     * Strings.isNotEmpty("bob")     = true
     * Strings.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * <p>
     * Copied from Apache Commons Lang org.apache.commons.lang3.StringUtils.isNotEmpty(CharSequence)
     * </p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>Gets the leftmost {@code len} characters of a String.</p>
     *
     * <p>If {@code len} characters are not available, or the
     * String is {@code null}, the String will be returned without
     * an exception. An empty String is returned if len is negative.</p>
     *
     * <pre>
     * StringUtils.left(null, *)    = null
     * StringUtils.left(*, -ve)     = ""
     * StringUtils.left("", *)      = ""
     * StringUtils.left("abc", 0)   = ""
     * StringUtils.left("abc", 2)   = "ab"
     * StringUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * <p>
     * Copied from Apache Commons Lang org.apache.commons.lang3.StringUtils.
     * </p>
     *
     * @param str the String to get the leftmost characters from, may be null
     * @param len the length of the required String
     * @return the leftmost characters, {@code null} if null String input
     */
    public static String left(final String str, final int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    /**
     * Returns a quoted string.
     *
     * @param str a String
     * @return {@code 'str'}
     */
    public static String quote(final String str) {
        return "'" + str + "'";
    }

    /**
     * Shorthand for {@code str.toUpperCase(Locale.ROOT);}
     *
     * @param str The string to upper case.
     * @return a new string
     * @see String#toLowerCase(Locale)
     */
    public static String toRootUpperCase(final String str) {
        return str.toUpperCase(Locale.ROOT);
    }

    /**
     * <p>
     * Removes control characters (char &lt;= 32) from both ends of this String returning {@code null} if the String is
     * empty ("") after the trim or if it is {@code null}.
     *
     * <p>
     * The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;= 32.
     * </p>
     *
     * <pre>
     * Strings.trimToNull(null)          = null
     * Strings.trimToNull("")            = null
     * Strings.trimToNull("     ")       = null
     * Strings.trimToNull("abc")         = "abc"
     * Strings.trimToNull("    abc    ") = "abc"
     * </pre>
     *
     * <p>
     * Copied from Apache Commons Lang org.apache.commons.lang3.StringUtils.trimToNull(String)
     * </p>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, {@code null} if only chars &lt;= 32, empty or null String input
     */
    public static String trimToNull(final String str) {
        String ts = str == null ? null : str.trim();
        return isEmpty(ts) ? null : ts;
    }

    /**
     * Removes control characters from both ends of this String returning {@code Optional.empty()} if the String is
     * empty ("") after the trim or if it is {@code null}.
     *
     * @param str The String to trim.
     * @return An Optional containing the String.
     * @see #trimToNull(String)
     */
    public static Optional<String> trimToOptional(final String str) {
        return Optional.ofNullable(str).map(String::trim).filter((s) -> !s.isEmpty());
    }

    /**
     * <p>Joins the elements of the provided {@code Iterable} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    /**
     * <p>Joins the elements of the provided {@code Iterator} into
     * a single String containing the provided elements.</p>
     *
     * <p>No delimiter is added before or after the list. Null objects or empty
     * strings within the iteration are represented by empty strings.</p>
     *
     * @param iterator  the {@code Iterator} of values to join together, may be null
     * @param separator the separator character to use
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterator<?> iterator, final char separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return Objects.toString(first, "");
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    /**
     * Splits a comma separated list ignoring whitespace surrounding the list item.
     *
     * @param string The string to split.
     * @return An array of strings.
     */
    public static String[] splitList(final String string) {
        return string != null ? string.split("\\s*,\\s*") : new String[0];
    }

    /**
     * Concatenates 2 Strings without allocation.
     *
     * @param str1 the first string.
     * @param str2 the second string.
     * @return the concatenated String.
     */
    public static String concat(final String str1, final String str2) {
        if (isEmpty(str1)) {
            return str2;
        } else if (isEmpty(str2)) {
            return str1;
        } else {
            StringBuilder sb = TEMP_STR.get();

            String var3;
            try {
                var3 = sb.append(str1).append(str2).toString();
            } finally {
                sb.setLength(0);
            }

            return var3;
        }
    }

    /**
     * Creates a new string repeating given {@code str} {@code count} times.
     *
     * @param str   input string
     * @param count the repetition count
     * @return the new string
     * @throws IllegalArgumentException if either {@code str} is null or {@code count} is negative
     */
    public static String repeat(final String str, final int count) {
        Objects.requireNonNull(str, "str");
        if (count < 0) {
            throw new IllegalArgumentException("count");
        } else {
            StringBuilder sb = TEMP_STR.get();

            try {
                for (int index = 0; index < count; ++index) {
                    sb.append(str);
                }

                return sb.toString();
            } finally {
                sb.setLength(0);
            }
        }
    }
}