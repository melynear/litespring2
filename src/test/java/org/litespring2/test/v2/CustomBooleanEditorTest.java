package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.propertyeditors.CustomBooleanEditor;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class CustomBooleanEditorTest {
    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);
        
        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        
        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        
        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        
        try {
            editor.setAsText("xxx");
        } catch (IllegalArgumentException e) {
            return;
        }
        
        Assert.fail();
    }
}
