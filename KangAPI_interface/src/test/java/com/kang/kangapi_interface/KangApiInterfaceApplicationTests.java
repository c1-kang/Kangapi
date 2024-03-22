package com.kang.kangapi_interface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kang.kangapi_interface.model.entity.Sentences;
import com.kang.kangapi_interface.model.test.temp;
import com.kang.kangapi_interface.service.SentencesService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class KangApiInterfaceApplicationTests {

    @Resource
    private SentencesService sentencesService;

    /**
     * 添加句子数据库
     */
    @Test
    void contextLoads() throws IOException {
        String path = "E:\\Code\\Project\\api开放平台\\API 数据\\sentences\\";
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l");
        list.forEach(a -> {
            // 读数据
            FileInputStream fis;
            String json;
            try {
                File file = new File(path + a + ".json");
                fis = new FileInputStream(file);
                FileChannel channel = fis.getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
                channel.read(byteBuffer);
                json = new String(byteBuffer.array());
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // GSON 解析
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<temp>>(){}.getType();
            List<temp> lists = gson.fromJson(json, type);

            for (temp i : lists) {
                Sentences sentences = new Sentences();
                BeanUtils.copyProperties(i, sentences);
                sentences.setSource(i.getFrom());
                sentencesService.save(sentences);
            }
            System.out.println("添加完毕");
        });
    }
}
