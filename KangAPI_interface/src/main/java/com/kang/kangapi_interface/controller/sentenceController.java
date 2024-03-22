package com.kang.kangapi_interface.controller;

import com.kang.kangapi_interface.service.SentencesService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 随机句子接口
 */
@RestController
@RequestMapping("/sentence")
public class sentenceController {

    @Resource
    private SentencesService sentencesService;

    // https://github.com/hitokoto-osc/sentences-bundle
    @GetMapping("/random")
    public String getRandomSentence(HttpServletRequest request) {
        // type=a&type=b
        String queryString = request.getQueryString();

        String result = sentencesService.getRandom(queryString);
        return result;
    }
}
