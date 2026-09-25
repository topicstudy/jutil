package cn.topicstudy.jutil.basic.file;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class FileUtilTest {


    @Test
    public void delete() {
        //String path = "D:/tmp/gitstd3";
        // FileUtil.delete(path);
    }

    @Test
    public void copyFile() throws IOException {
        // FileUtil.copyFile("d:/tmp/gitstd/a.txt", "d:/p.txt");
    }

    @Test
    public void copyDir() throws IOException {
        // FileUtil.copyDir("d:/tmp/gitstd","d:/gitstdCopy");
    }


    @Test
    public void testReadLines() throws IOException {
        List<String> strings = FileUtil.readLines("D:\\code\\IdeaProjects\\open-source\\jutil\\src\\test\\java\\cn\\topicstudy\\jutil\\basic\\file\\Lines");
        Assert.assertNotNull(strings);
        Assert.assertEquals(strings.size(), 3);
        Assert.assertEquals(strings.get(1), "2222222222222222");
    }
    @Data
    class GreatChinese {
        String name;
        String country;
        Boolean great;
    }

    @Test
    public void testReadJSONObj() {

        GreatChinese greatChinese = FileUtil.readJSONObj("D:\\code\\IdeaProjects\\open-source\\jutil\\src\\test\\java\\cn\\topicstudy\\jutil\\basic\\file\\test.json", GreatChinese.class);
        Assert.assertNotNull(greatChinese);
        Assert.assertEquals("WuYiFan", greatChinese.name);
        Assert.assertEquals(Boolean.TRUE, greatChinese.great);
    }


    @Test
    public void testReadJSONArray(){
        List<GreatChinese> greatChineseList = FileUtil.readJSONArray("D:\\code\\IdeaProjects\\open-source\\jutil\\src\\test\\java\\cn\\topicstudy\\jutil\\basic\\file\\tetsArray.json", GreatChinese.class);
        Assert.assertNotNull(greatChineseList);
        Assert.assertEquals(greatChineseList.size(), 3);
        GreatChinese p1 = greatChineseList.get(0);
        GreatChinese p2 = greatChineseList.get(1);
        GreatChinese p3 = greatChineseList.get(2);
        Assert.assertNotNull(p1);
        Assert.assertNotNull(p2);
        Assert.assertNotNull(p3);

        Assert.assertEquals(p1.name, "WuYiFan");
        Assert.assertEquals(p1.great, true);

        Assert.assertEquals(p2.name, "XuJiaYin");
        Assert.assertEquals(p2.great, true);

        Assert.assertEquals(p3.name, "LeiJun");
        Assert.assertEquals(p3.great, false);
    }

}
