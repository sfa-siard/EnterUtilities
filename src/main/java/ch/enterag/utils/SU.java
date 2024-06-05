/*
Copyright  : 2008, Enter AG, Zurich, Switzerland
			 2024, Puzzle ITC GmbH, Switzerland
Created    : 15.02.2008, Hartwig Thomas
*/

package ch.enterag.utils;

import java.io.*;

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
        return (inputString != null) && (!inputString.isEmpty());
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
     * Converts an encoded byte buffer to a string.
     *
     * @param buf      Byte buffer.
     * @param encoding Encoding to be used.
     * @return Encoded string.
     */
    private static String getEncodedString(byte[] buf, String encoding) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buf);
             InputStreamReader isr = new InputStreamReader(bais, encoding)) {
            StringBuilder sb = new StringBuilder();
            for (int i = isr.read(); i != -1; i = isr.read()) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
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
     * Prepares string for HTML by replacing all special characters.
     *
     * @param text String to be prepared.
     * @return Prepared string.
     */
    public static String toHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
