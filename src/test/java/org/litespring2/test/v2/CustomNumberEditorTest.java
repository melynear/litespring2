package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.propertyeditors.CustomNumberEditor;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class CustomNumberEditorTest {
    @Test
    public void testConvertStringToNumber() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object value = editor.getValue();
        
        Assert.assertTrue(value instanceof Integer);
        Assert.assertTrue(((Integer) value).intValue() == 3);
        
        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);
        
        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        
        Assert.fail();
    }
}
