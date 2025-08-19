package cn.jiege.starGraph.core.advice;

import cn.jiege.starGraph.core.dto.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result commonException(Exception e) {
        log.error("[commonException]: ", e);
        return Result.error(e.getLocalizedMessage());
    }
}
