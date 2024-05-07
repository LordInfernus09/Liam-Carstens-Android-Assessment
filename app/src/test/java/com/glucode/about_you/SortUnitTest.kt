package com.glucode.about_you

import com.glucode.about_you.engineers.EngineersFragment
import com.glucode.about_you.mockdata.MockData
import org.junit.Assert.assertEquals
import org.junit.Test


class EngineerSortTest {

    @Test
    fun testSortListByYears() {
        val frag = EngineersFragment()
        val engineers = MockData.engineers

        val sortedEngineers = frag.sortList(engineers, 0)
        assertEquals(sortedEngineers.map { it.quickStats.years }, listOf(6, 7, 9, 10, 14, 15))
    }

    @Test
    fun testSortListByCoffees() {
        val frag = EngineersFragment()
        val engineers = MockData.engineers

        val sortedEngineers = frag.sortList(engineers, 1)
        assertEquals(sortedEngineers.map { it.quickStats.coffees }, listOf(1000, 1800, 4000, 5400, 9000, 99999))
    }

    @Test
    fun testSortListByBugs() {
        val frag = EngineersFragment()
        val engineers = MockData.engineers

        val sortedEngineers = frag.sortList(engineers, 2)
        assertEquals(sortedEngineers.map { it.quickStats.bugs }, listOf(100, 700, 1000, 1800, 4000, 99999))
    }
}