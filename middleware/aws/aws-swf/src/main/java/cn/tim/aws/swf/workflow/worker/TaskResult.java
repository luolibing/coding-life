package cn.tim.aws.swf.workflow.worker;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResult<T> {

    private T data;
}
