package com.wjh.os;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OSUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getOSType() {
        OSTypeEnum osType = OSUtil.getOSType();
        Assert.assertNotNull(osType);
    }
}