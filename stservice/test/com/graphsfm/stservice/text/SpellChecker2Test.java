package com.graphsfm.stservice.text;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpellChecker2Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void findSequenceTest1() {
		SpellChecker2 s2 = new SpellChecker2();
		ArrayList<Long> l1 = new ArrayList<Long>(Arrays.asList(new Long[] {
				10L, 20L, 30L, 40L, 50L
		}));
		ArrayList<Long> l2 = new ArrayList<Long>(Arrays.asList(new Long[] {
				40L, 51L
		}));
		ArrayList<Long> l3 = new ArrayList<Long>(Arrays.asList(new Long[] {
				52L
		}));
		
		@SuppressWarnings("unchecked")
		int[] r = s2.findSeq(l1, l2, l3);
		assertNotNull(r);
		assertEquals(r[0], 4);
		assertEquals(r[1], 1);
		assertEquals(r[2], 0);
	}

	@Test
	public void findSequenceTest2() {
		SpellChecker2 s2 = new SpellChecker2();
		ArrayList<Long> l1 = new ArrayList<Long>(Arrays.asList(new Long[] {
				10L, 20L, 30L, 40L, 50L
		}));
		ArrayList<Long> l2 = new ArrayList<Long>(Arrays.asList(new Long[] {
				60L, 70L
		}));
		ArrayList<Long> l3 = new ArrayList<Long>(Arrays.asList(new Long[] {
				80L
		}));
		
		@SuppressWarnings("unchecked")
		int[] r = s2.findSeq(l1, l2, l3);
		assertNull(r);
	}
}
