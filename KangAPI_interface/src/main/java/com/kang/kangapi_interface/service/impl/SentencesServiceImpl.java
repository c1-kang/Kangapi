package com.kang.kangapi_interface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kangapi_interface.mapper.SentencesMapper;
import com.kang.kangapi_interface.model.entity.Sentences;
import com.kang.kangapi_interface.service.SentencesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 28356
 * @description 针对表【sentences】的数据库操作Service实现
 * @createDate 2024-03-19 20:33:42
 */
@Service
public class SentencesServiceImpl extends ServiceImpl<SentencesMapper, Sentences>
        implements SentencesService {

    // 类别白名单
    private static final List<String> WHITE_TYPES = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l");

    @Resource
    private SentencesMapper sentencesMapper;

    @Override
    public String getRandom(String queryString) {
        QueryWrapper<Sentences> wrapper = new QueryWrapper<>();
        if (queryString != null) {
            String[] split = queryString.split("&");
            ArrayList<String> params = new ArrayList<>();
            for (String s : split) {
                StringBuilder sub = new StringBuilder();
                for (int i = 0; i < s.length() - 1; i++) {
                    sub.append(s.charAt(i));
                }
                String s1 = sub.toString();
                if (s1.equals("type=")) {
                    String t = s.substring(s.length() - 1);
                    if (WHITE_TYPES.contains(t)) {
                        params.add(t);
                    }
                }
            }
            wrapper = getQueryWrapper(params);
        }

        Sentences sentence = sentencesMapper.getRandomByType(wrapper);
        return sentence.getHitokoto();
    }

    private QueryWrapper<Sentences> getQueryWrapper(ArrayList<String> params) {
        QueryWrapper<Sentences> q = new QueryWrapper<>();

        for (int i = 0; i < params.size() - 1; i++) {
            q.eq("type", params.get(i)).or();
        }
        q.eq("type", params.get(params.size() - 1));

        return q;
    }


}




