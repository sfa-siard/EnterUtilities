/*
Copyright  : 2008, Enter AG, Zurich, Switzerland
			 2024, Puzzle ITC GmbH, Switzerland
Created    : 15.02.2008, Hartwig Thomas
*/

package ch.enterag.utils;

import java.io.*;
import java.text.*;

/**
 * SU implements a number of often used string utilities.
 *
 * @author Hartwig Thomas
 */
public abstract class SU {
    public static final String sUTF8_CHARSET_NAME = "UTF-8";

    /**
     * @param inputString String to be tested.
     * @return true if string is not null and not empty.
     */
    public static boolean isNotEmpty(String inputString) {
        return (inputString != null) && (!inputString.trim().isEmpty());
    }

    /**
     * Replaces all occurrences of String find by String replace.
     *
     * @param inputString String to be transformed.
     * @param find        Substring to be found.
     * @param replace     Substring to insert in place.
     * @return Transformed String.
     * @deprecated Is available as a String method since JAVA 1.5.
     */
    @Deprecated
    public static String replace(String inputString, String find, String replace) {
        return inputString.replace(find, replace);
    }

    /**
     * Works like MessageFormat.format, but assumes that all single quotes not immediately preceding { or } are meant to represent a single quote.
     *
     * @param inputString String to be formatted.
     * @param arguments   Replacement arguments to be used.
     * @return Formatted string.
     */
    public static String format(String inputString, Object[] arguments) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            stringBuilder.append(c);
            if (c == '\'' && (i < inputString.length() - 1)) {
                char d = inputString.charAt(i + 1);
                if ((d != '{') && (d != '}'))
                    stringBuilder.append('\'');
            }
        }
        return MessageFormat.format(stringBuilder.toString(), arguments);
    }

    /**
     * Replaces {0} in the pattern by the given string.
     *
     * @param inputString String to be formatted.
     * @param replace     Replacement string to be used.
     * @return Formatted string.
     */
    public static String format(String inputString, String replace) {
        return format(inputString, new Object[]{replace});
    }

    /**
     * Returns a string of the given number of repetitions of a base string.
     *
     * @param length Length of returned String.
     * @param s      Base string to be used.
     * @return String of given length filled with desired character.
     */
    public static String repeat(int length, String s) {
        return repeatHelper(length, s);
    }

    /**
     * Returns a string of the given number of repetitions of a given character.
     *
     * @param length Length of returned string.
     * @param c      Character to be used.
     * @return String of given length filled with desired character.
     */
    public static String repeat(int length, char c) {
        return repeatHelper(length, String.valueOf(c));
    }

    /**
     * Helper method to create a repeated string.
     *
     * @param length Length of returned String.
     * @param s      Base string to be used.
     * @return Repeated string.
     */
    private static String repeatHelper(int length, String s) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(s);
        return sb.toString();
    }

    /**
     * Returns the next possible line breakpoint in the given range.
     *
     * @param s     String to be broken.
     * @param start Beginning of the line.
     * @param end   Maximum value of breakpoint.
     * @return Breakpoint.
     */
    public static int getBreakPoint(String s, int start, int end) {
        end = Math.min(end, s.length());
        int breakPoint = s.indexOf('\n', start) + 1;
        if ((breakPoint <= start) || (breakPoint > end)) {
            breakPoint = end;
            if ((end < s.length()) && (s.charAt(end) != ' ')) {
                for (int i = end - 1; i > start; i--) {
                    char c = s.charAt(i + 1);
                    if ((c == ' ') || (c == '-') || (c == '\n') || (c == '\r') || (c == '\t')) {
                        breakPoint = i + 1;
                        break;
                    }
                }
            }
        }
        return breakPoint;
    }

    /**
     * Converts a portion of a character array to an encoded byte buffer.
     *
     * @param chars    Character array to be converted.
     * @param offset   Offset in character array where encoding is to start.
     * @param length   Length of character array to be converted.
     * @param encoding Encoding to be used.
     * @return Encoded byte buffer.
     */
    private static byte[] putEncodedCharArray(char[] chars, int offset, int length, String encoding) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
             OutputStreamWriter osw = new OutputStreamWriter(baos, encoding)) {
            osw.write(chars, offset, length);
            osw.close();
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    /**
     * Converts a character array to a UTF8-encoded byte buffer.
     *
     * @param chars  Character array to be converted.
     * @param offset Offset in character array where encoding is to start.
     * @param length Length of character array to be converted.
     * @return Encoded byte buffer.
     */
    public static byte[] putUtf8CharArray(char[] chars, int offset, int length) {
        return putEncodedCharArray(chars, offset, length, SU.sUTF8_CHARSET_NAME);
    }

    /**
     * Converts a string to an encoded byte buffer.
     *
     * @param s        String to be converted.
     * @param encoding Encoding to be used.
     * @return Encoded byte buffer.
     */
    public static byte[] putEncodedString(String s, String encoding) {
        return putEncodedCharArray(s.toCharArray(), 0, s.length(), encoding);
    }

    /**
     * Converts a string to a UTF8-encoded byte buffer.
     *
     * @param s String to be converted.
     * @return Encoded byte buffer.
     */
    public static byte[] putUtf8String(String s) {
        return putEncodedCharArray(s.toCharArray(), 0, s.length(), SU.sUTF8_CHARSET_NAME);
    }

    /**
     * Converts a string to a Cp437-encoded byte buffer.
     *
     * @param s String to be converted.
     * @return Encoded byte buffer.
     */
    public static byte[] putCp437String(String s) {
        return putEncodedCharArray(s.toCharArray(), 0, s.length(), "Cp437");
    }

    /**
     * Converts a string to a ISO-8859-1-encoded byte buffer.
     *
     * @param s String to be converted.
     * @return Encoded byte buffer.
     */
    public static byte[] putIsoLatin1String(String s) {
        return putEncodedCharArray(s.toCharArray(), 0, s.length(), "ISO-8859-1");
    }

    /**
     * Converts a string to a Windows-1252-encoded byte buffer.
     *
     * @param s String to be converted.
     * @return Encoded byte buffer.
     */
    public static byte[] putWindows1252String(String s) {
        return putEncodedCharArray(s.toCharArray(), 0, s.length(), "Windows-1252");
    }


    /**
     * Converts an encoded byte buffer to a string.
     *
     * @param buf      Byte buffer.
     * @param encoding Encoding to be used.
     * @return Encoded string.
     */
    public static String getEncodedString(byte[] buf, String encoding) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buf);
             InputStreamReader isr = new InputStreamReader(bais, encoding)) {
            StringBuilder sb = new StringBuilder();
            for (int i = isr.read(); i != -1; i = isr.read()) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * Converts a UTF8-encoded byte buffer to a string.
     *
     * @param buf Byte buffer.
     * @return UTF8-encoded string.
     */
    public static String getUtf8String(byte[] buf) {
        return getEncodedString(buf, SU.sUTF8_CHARSET_NAME);
    }

    /**
     * Converts a Cp437-encoded byte buffer to a string.
     *
     * @param buf Byte buffer.
     * @return Cp437-encoded string.
     */
    public static String getCp437String(byte[] buf) {
        return getEncodedString(buf, "Cp437");
    }

    /**
     * Converts an ISO-8859-1-encoded byte buffer to a string.
     *
     * @param buf Byte buffer.
     * @return ISO-8859-1-encoded string.
     */
    public static String getIsoLatin1String(byte[] buf) {
        return getEncodedString(buf, "ISO-8859-1");
    }

    /**
     * Converts a Windows-1252-encoded byte buffer to a string.
     *
     * @param buf Byte buffer.
     * @return Windows-1252-encoded string.
     */
    public static String getWindows1252String(byte[] buf) {
        return getEncodedString(buf, "Windows-1252");
    }

    /**
     * Prepares string for a CSV cell. All special characters are replaced by escaping their hex value using the \x prefix.
     *
     * @param text String to be prepared.
     * @return Prepared string.
     */
    public static String toCsv(String text) {
        text = text.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
        for (int i = 0; i < 32; i++) {
            text = text.replace(String.valueOf((char) i), "\\x" + BU.toHex((byte) i));
        }
        return text;
    }

    /**
     * Prepares string for HTML by replacing all special characters.
     *
     * @param text String to be prepared.
     * @return Prepared string.
     */
    public static String toHtml(String text) {
        return text.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;")
                .replace("\"", "&quot;");
    }
}
