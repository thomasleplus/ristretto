package org.leplus.ristretto.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

import org.junit.Test;

public class TestReproducibleUUIDs {

	private static final int NUMBER_OF_BYTES = 1024;

	@Test
	public void testNulls() throws IOException {
		assertNull(ReproducibleUUIDs.fromBytes(null));
		assertNull(ReproducibleUUIDs.fromByteBuffer(null));
		assertNull(ReproducibleUUIDs.fromString(null));
		assertNull(ReproducibleUUIDs.fromInputStream(null));
	}

	@Test
	public void testBytes() {
		final SecureRandom random = new SecureRandom();
		final byte[] bytes = new byte[NUMBER_OF_BYTES];
		random.nextBytes(bytes);
		assertEquals(ReproducibleUUIDs.fromBytes(bytes),
				ReproducibleUUIDs.fromBytes(bytes));
		// Test compatibility with java.util.UUID.nameUUIDFromBytes(byte[])
		assertEquals(ReproducibleUUIDs.fromBytes(bytes),
				UUID.nameUUIDFromBytes(bytes));
	}

	@Test
	public void testByteBuffer() {
		final SecureRandom random = new SecureRandom();
		final byte[] bytes = new byte[NUMBER_OF_BYTES];
		random.nextBytes(bytes);
		assertEquals(ReproducibleUUIDs.fromByteBuffer(ByteBuffer.wrap(bytes)),
				ReproducibleUUIDs.fromByteBuffer(ByteBuffer.wrap(bytes)));
	}

	@Test
	public void testString() {
		final SecureRandom random = new SecureRandom();
		final byte[] bytes = new byte[NUMBER_OF_BYTES];
		random.nextBytes(bytes);
		assertEquals(ReproducibleUUIDs.fromString(new String(bytes)),
				ReproducibleUUIDs.fromString(new String(bytes)));
	}

	@Test
	public void testInputStream() throws IOException {
		final SecureRandom random = new SecureRandom();
		final byte[] bytes = new byte[NUMBER_OF_BYTES];
		random.nextBytes(bytes);
		assertEquals(ReproducibleUUIDs.fromInputStream(new ByteArrayInputStream(bytes)),
				ReproducibleUUIDs.fromInputStream(new ByteArrayInputStream(bytes)));
	}

	@Test
	public void testUUIDs() {
		final UUID[] uuids = new UUID[3];
		for (int i = 0; i < uuids.length; i++) {
			uuids[i] = UUID.randomUUID();
		}
		final UUID uuid = uuids[0];
		assertEquals(uuid,
				ReproducibleUUIDs.fromUUIDs(uuid));
		assertEquals(ReproducibleUUIDs.fromUUIDs(uuids),
				ReproducibleUUIDs.fromUUIDs(uuids));
		assertNull(ReproducibleUUIDs.fromUUIDs((UUID[]) null));
	}

}