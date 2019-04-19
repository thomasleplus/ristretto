package org.leplus.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * This class contains utility methods that generate deterministic UUIDs.
 * Meaning that given the same input, these methods will always return the
 * same UUID, as opposed to {@link java.util.UUID.randomUUID()}. The produced
 * UUIDs may not be universally unique (since other code using the same method
 * on the same input would have produce the same UUIDs) but it can still be
 * useful in situations where you need exactly that: be able to generate a UUID
 * and now that other parts of a system will be able to generate a matching UUID
 * for the same input.
 * 
 * For example if two parts of a system receive a file and need to independently
 * produce pieces of data that will later have to be reconciled. Then each piece
 * of data could have it's own unique UUID plus a reference UUID generated from
 * the file using one of the methods below. Then both part of the system will have
 * generated the same reference UUID allowing for easier reconciliation.
 * 
 * @author Thomas Leplus
 * @since 1.0.0
 */
public class DeterministicUUIDs {

	private static final String MD5 = "MD5";
	
	private static final int BUFFER_SIZE = 8192;

	private DeterministicUUIDs() {
		super();
	}

	/**
	 * Generates a UUID from the given String.
	 * The same String will always produce the same UUID.
	 * 
	 * @param s the input string.
	 * @return the resulting UUID.
	 */
	public static UUID fromString(final String s) {
		if (s == null) {
			return null;
		}
		return fromBytes(s.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Generates a UUID from the given bytes.
	 * The same bytes will always produce the same UUID.
	 * 
	 * @param bytes the input bytes.
	 * @return the resulting UUID.
	 */
	public static UUID fromBytes(final byte... bytes) {
		if (bytes == null) {
			return null;
		}
		final MessageDigest md = createDigest();
		md.update(bytes);
		return digest(md);
	}

	/**
	 * Generates a UUID from the given byte buffer.
	 * The same bytes will always produce the same UUID.
	 * 
	 * @param buffer the input byte buffer.
	 * @return the resulting UUID.
	 */
	public static UUID fromByteBuffer(final ByteBuffer buffer) {
		if (buffer == null) {
			return null;
		}
		final MessageDigest md = createDigest();
		md.update(buffer);
		return digest(md);
	}

	private static UUID digest(final MessageDigest md) {
		final byte[] md5Bytes = md.digest();
		// for compatibility with UUID.nameUUIDFromBytes(byte[]).
		md5Bytes[6] &= 0x0f;
		md5Bytes[6] |= 0x30;
		md5Bytes[8] &= 0x3f;
		md5Bytes[8] |= 0x80;
		return UUIDConvertor.toUUID(md5Bytes);
	}

	private static MessageDigest createDigest() {
		try {
			return MessageDigest.getInstance(MD5);
		} catch (final NoSuchAlgorithmException e) {
			throw new InternalError(MD5 + " not supported", e);
		}
	}

	/**
	 * Generates a UUID from the given input stream.
	 * The same bytes will always produce the same UUID.
	 * 
	 * @param input the input stream.
	 * @return the resulting UUID.
	 */
	public static UUID fromInputStream(final InputStream input) throws IOException {
		if (input == null) {
			return null;
		}
		final MessageDigest md = createDigest();
		try (final DigestInputStream dis = new DigestInputStream(input, md)) {
			final byte[] buffer = new byte[BUFFER_SIZE];
			while (dis.read(buffer) >= 0) {
			}
		}
		return digest(md);
	}

}
