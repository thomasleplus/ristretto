package org.leplus.ristretto.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.google.common.testing.EqualsTester;
import com.google.common.testing.SerializableTester;

/**
 * Tests for the {@link org.leplus.ristretto.util.IdentityEnum} enum.
 *
 * @author Thomas Leplus
 * @since 1.0.0
 */
public class TestIdentityEnum {

	/**
	 * Checks that {@link org.leplus.ristretto.util.IdentityEnum.IT} is equal (==) to a deep
	 * clone of itself.
	 */
	@Test
	public void testEqualDeepClone() {
		assertTrue(IdentityEnum.IT == SerializationUtils.clone(IdentityEnum.IT));
	}

	/**
	 * Checks that {@link org.leplus.ristretto.util.IdentityEnum.IT} equals() itself.
	 */
	@Test
	public void testEquals() {
		assertEquals(IdentityEnum.IT, IdentityEnum.IT);
		assertNotEquals(IdentityEnum.IT, new DuplicityObject());
	}

	/**
	 * Checks that {@link org.leplus.ristretto.util.IdentityEnum.IT} equals() a deep clone of
	 * itself.
	 */
	@Test
	public void testEqualsDeepClone() {
		assertSame(IdentityEnum.IT, SerializationUtils.clone(IdentityEnum.IT));
		assertSame(SerializationUtils.clone(IdentityEnum.IT), IdentityEnum.IT);
	}

	/**
	 * Checks that {@link org.leplus.ristretto.util.IdentityEnum.IT} passes guava's equality
	 * tests.
	 */
	@Test
	public void testEqualsGuava() {
		new EqualsTester().addEqualityGroup(IdentityEnum.IT, IdentityEnum.IT).testEquals();
	}

	/**
	 * Checks {@link org.leplus.ristretto.util.IdentityEnum.IT}'s hashcode remains constant.
	 */
	@Test
	public void testHashCode() {
		assertEquals(IdentityEnum.IT.hashCode(), IdentityEnum.IT.hashCode());
		assertEquals(IdentityEnum.IT.hashCode(), SerializationUtils.clone(IdentityEnum.IT).hashCode());
	}

	/**
	 * Checks that {@link org.leplus.ristretto.util.IdentityEnum.IT} passes guava's
	 * serialization tests.
	 */
	@Test
	public void testSerializeGuava() {
		SerializableTester.reserializeAndAssert(IdentityEnum.IT);
	}

}